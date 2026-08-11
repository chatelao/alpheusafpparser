# GOCA Graphics Position and Scaling Roadmap

This document outlines the detailed phased implementation plan to resolve the GOCA positioning, scaling, coordinate mapping, and transformation gaps identified in `GRPAHICS_POS_AUDIT.md`.

## Status Summary
- **Phase 1: Object Area Positioning (OBP) for GOCA Objects**: ✅ Completed
- **Phase 2: Graphics Data Descriptor (GDD) Scaling & Custom Coordinate Spaces**: ✅ Completed
- **Phase 3: Robust GOCA Image Alignment & Transform Pipeline**: ⏳ Pending
- **Phase 4: Segment Transformations & Characteristics**: ⏳ Pending
- **Phase 5: Verification, Conformance, & Regression Testing**: ⏳ Pending

---

## Phased Implementation Plan

### Phase 1: Object Area Positioning (OBP) for GOCA Objects ✅
Ensure GOCA vector graphics objects are correctly positioned on the page by respecting the origin offset and rotation defined in the `OBP` (Object Area Position) structured field.

- [x] **Parse & Track OBP for Graphics Objects**:
  - Update `PdfHandler.java` to extract `xOrigin`, `yOrigin`, and optional rotation parameters from the `OBP` structured field when processing GOCA objects (`BGR`/`EGR`).
- [x] **Coordinate Space Translation**:
  - Modify the initialization of the graphics context within GOCA objects. Prior to executing drawing orders, apply a translation matrix based on the parsed OBP origin values relative to the Page Presentation Space (PPS).
- [x] **Handle OBP Rotation**:
  - Implement rotation handling for the Object Area Presentation Space (OAPS) as specified in the `OBP` structured field (e.g., support $0^\circ$, $90^\circ$, $180^\circ$, and $270^\circ$ orientation offsets).
- [x] **Integration with PDF Canvas**:
  - Integrate OBP translation and rotation into `PdfHandler.ensureCanvasTransformed()` or as a standalone GOCA-specific canvas transformation block during `BGR` (Begin Graphics Object) handling.

---

### Phase 2: Graphics Data Descriptor (GDD) Scaling & Custom Coordinate Spaces ✅
Implement coordinate transformations derived from the `GDD` (Graphics Data Descriptor) window specification and coordinate resolution to establish the correct Graphics Presentation Space (GPS).

- [x] **Parse GDD Parameters**:
  - Update `PdfHandler.java` to extract the **GPS Window Specification** (Left, Right, Bottom, Top boundaries) and the GDD coordinate resolution (unit base and units per unit base) from `GDD_GraphicsDataDescriptor`.
- [x] **Calculate GOCA-to-OAPS Scale Factors**:
  - Formulate dynamic scaling ratios based on the boundaries of the GPS Window relative to the OAPS dimensions:
    $$\text{scaleX}_{\text{GOCA}} = \frac{\text{OAPS Width}}{\text{GPS Window Width}}$$
    $$\text{scaleY}_{\text{GOCA}} = \frac{\text{OAPS Height}}{\text{GPS Window Height}}$$
- [x] **Establish GDD Transformation Matrix**:
  - Create and concatenate a GDD-specific scale/translation matrix on entering the `BGR` block.
  - Correctly map left-handed or right-handed coordinate mapping specified by the GDD window boundaries (preventing horizontal or vertical mirroring).
- [x] **State Isolation**:
  - Ensure GDD coordinate transformations do not leak into page-level text/image operations by saving and restoring the PDF graphics state (using `saveState()` and `restoreState()` operations in `PdfCanvas`).

---

### Phase 3: Robust GOCA Image Alignment & Transform Pipeline ⏳
Replace hardcoded alignment assumptions and coordinate hacks with mathematically sound transformations derived from the OBP and GDD specs.

- [ ] **Remove Hardcoded Coordinate Alignment Hacks**:
  - Locate and eliminate the `gocaImageY - gocaImageHeight` offset hack inside GOCA Image rendering (`renderGocaImage()`).
- [ ] **Derive Image Bounds dynamically**:
  - Calculate GOCA image placement bounds and direction directly from GDD window mapping, applying correct top-down alignment within the bottom-up iText PDF coordinate space.
- [ ] **Support Rotated and Transformed GOCA Images**:
  - Ensure `renderGocaImage` functions correctly under non-standard coordinate systems, complex page rotations, or scaled views.

---

### Phase 4: Segment Transformations & Characteristics ⏳
Extend support to segment-level transformations and properties to ensure that reusable drawing elements render accurately.

- [ ] **Parse Segment Characteristics**:
  - Fully inspect and record characteristics defined in `GSGCH_SegmentCharacteristics` such as segment transform matrices (translations, scaling, rotation).
- [ ] **Implement Segment-Level Matrix Concatenation**:
  - Apply segment-specific transformation matrices to the PDF canvas during the execution of individual GOCA segments (`GBSEG`/`GESEG`).
- [ ] **Manage Segment State Scoping**:
  - Ensure segment transformations are correctly scoped, isolated, and popped from the graphics stack when a segment execution completes.

---

### Phase 5: Verification, Conformance, & Regression Testing ⏳
Establish rigorous automated validation and visual diff test suites to verify compliant rendering of GOCA vector graphics.

- [ ] **Add Unit Tests for Matrix Computations**:
  - Write dedicated unit tests targeting the dynamic scale and translation calculations derived from OBP and GDD parameters.
- [ ] **Create Visual Regression Test Cases**:
  - Introduce complex GOCA test files (including varying OBP offsets, GDD window coordinate specifications, and rotated segments).
- [ ] **Pixel-Perfect Validation**:
  - Update `PdfPixelPerfectVerificationTest` and `PdfBaselineGeneratorTool` to verify GOCA graphics layouts and prevent coordinate regression.
- [ ] **PDF/VT Profile Conformance**:
  - Ensure any structural GOCA transformation changes comply with the PDF/VT-1 (ISO 16612-2) standard by verifying output through the VeraPDF CLI wrapper.

---

## Architectural Guidelines

1. **State Isolation is Paramount**:
   All canvas transformations applied for GOCA objects, OBP, GDD, or GOCA segments must be tightly scoped using `currentCanvas.saveState()` and `currentCanvas.restoreState()`. They must never leak outside of the `BGR`/`EGR` or `GBSEG`/`GESEG` boundary contexts.
2. **Double-Precision Coordinate Precision**:
   Always perform OBP and GDD scale computations using double-precision floating-point numbers (`double`) to prevent precision loss and cumulative rounding errors, in alignment with `CoordinateTransformer` conventions.
3. **Graceful Fallbacks**:
   If a GOCA object lacks an `OBP` or `GDD` structured field, fall back to the Page Presentation Space (PPS) defaults gracefully without throwing exceptions.
