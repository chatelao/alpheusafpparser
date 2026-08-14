# Color Handling Remediation Roadmap

This document outlines the phased roadmap for resolving the color handling gaps and bugs identified in `COLOR_HANDLING_GAP.md`. This roadmap aims to achieve high-fidelity AFP-to-PDF conversion with complete compliance with the PDF/VT-1 (ISO 16612-2) standard, leveraging iText 9 color spaces, XObjects, and stencil masking.

---

## Phased Implementation Strategy

```
Phase 1: GOCA Core Process Color & Gradient Robustness (Immediate Priority) ✅
  ├── 1.1 Support GSPCOL in GOCA Rendering ✅
  ├── 1.2 Implement GSPCOL Precedence Reset on GSCOL/GSECOL ✅
  └── 1.3 Add Null-Safe Checks in GLGD and GRGD Gradients ✅

Phase 2: Expanded Color Space Support & Defaults ✅
  ├── 2.1 Implement Highlight Color Space (0x06) ✅
  ├── 2.2 Implement YCrCb (0x02) & YCbCr (0x12) Color Spaces ✅
  └── 2.3 Refactor Device-Default Colors to Resolve Dynamically ✅

Phase 3: Bi-level Image Colorization & IM Image Support ✅
  ├── 3.1 Implement Bi-level Image Colorization via iText Stencil Masks ✅
  └── 3.2 Add Rendering Hooks and Support for Legacy IM Images ✅

Phase 4: CMOCA (CMR / CAT) Integration & Advanced Color Management ⏳
  ├── 4.1 CAT (Color Attribute Table) Paletted Mapping ⏳
  │     ├── 4.1.1 Parse and Decode CAT Self-Defining Parameters (SDPs) ⏳
  │     ├── 4.1.2 Implement CAT/MCA Resource Registry in ColorContext ⏳
  │     ├── 4.1.3 Integrate Paletted Color Resolution in ColorHandler ⏳
  │     └── 4.1.4 Add Unit and Integration Tests for CAT Mapping ⏳
  └── 4.2 CMR (Color Management Resource) ICC Profile Embedding ⏳
        ├── 4.2.1 Extract Raw ICC Profile Data from CMOCA Resources ⏳
        ├── 4.2.2 Implement ICC-Based Color Spaces in ColorHandler ⏳
        ├── 4.2.3 Bind and Track CMR State in ColorContext and PdfHandler ⏳
        └── 4.2.4 Validate Conformance of ICC Profile Embedding ⏳
```

---

## Phase 1: GOCA Core Process Color & Gradient Robustness ✅

### Goal
Resolve high-impact bugs in GOCA rendering where process colors are ignored, color precedence is lost, and malformed gradient parameters can cause crashes.

### 1.1 Support GSPCOL in GOCA Rendering ✅
*   **Affected Files:** `PdfHandler.java`, `PdfGraphicsState.java`
*   **Proposed Solution:**
    *   Update `PdfHandler.applyGraphicsState()` to check whether `graphicsState.getProcessColorSpace()` is non-null.
    *   If active, call `ColorHandler.getExtendedColor(processColorSpace, processColorValue)` to obtain an iText `Color`.
    *   Apply this resolved color as both the stroke and fill color of `currentCanvas`.
    *   Fall back to standard index-based `ColorHandler.getColor(graphicsState.getColor())` if no process color space is active.

### 1.2 Implement GSPCOL Precedence Reset on GSCOL/GSECOL ✅
*   **Affected Files:** `PdfHandler.java`
*   **Proposed Solution:**
    *   In `PdfHandler.handleDrawingOrderInternal()`, locate the handlers for `GSCOL_SetColor` and `GSECOL_SetExtendedColor`.
    *   Ensure that when either command is processed, the active process color properties in the graphics state are reset. Specifically, call `graphicsState.setProcessColorSpace(null)` and clear the raw byte array.
    *   This ensures that any subsequent drawing orders in the same graphics segment correctly respect standard/extended legacy colors.

### 1.3 Add Null-Safe Checks in GLGD and GRGD Gradients ✅
*   **Affected Files:** `PdfHandler.java`
*   **Proposed Solution:**
    *   In `PdfHandler.applyGradient()`, intercept the colors resolved via `ColorHandler.getExtendedColor()`.
    *   Add null checks for both start and end colors before querying their inner properties (such as color space or value arrays).
    *   If any resolved color is null, gracefully fall back to the active graphics state legacy color or standard `DeviceRgb.BLACK` instead of throwing a `NullPointerException`.

### Testing & Verification
*   Create GOCA-specific unit/integration tests with process colors to verify that colors map accurately to CMYK and RGB in the generated PDF.
*   Run rasterized pixel-perfect visual regression tests to verify that GSPCOL-filled areas are rendered with their respective rich colors rather than default black.

---

## Phase 2: Expanded Color Space Support & Defaults ✅

### Goal
Ensure all architected AFP color spaces map cleanly to PDF equivalents and that default presentation colors are resolved dynamically.

### 2.1 Implement Highlight Color Space (0x06) ✅
*   **Affected Files:** `ColorHandler.java`
*   **Proposed Solution:**
    *   In `ColorHandler.getExtendedColor()`, add case handling for `AFPColorSpace.Highlight` (code `0x06`).
    *   Map highlight colors based on standard highlight color rendering properties (such as mapping them to a bright spot color, standard RGB/CMYK tint, or a simulated custom color value).

### 2.2 Implement YCrCb (0x02) & YCbCr (0x12) Color Spaces ✅
*   **Affected Files:** `ColorHandler.java`
*   **Proposed Solution:**
    *   In `ColorHandler.getExtendedColor()`, implement conversion formulas or color space mappings for `YCbCr` / `YCrCb` to standard RGB/CMYK.
    *   Support unpacking of YCrCb/YCbCr color value byte arrays to ensure images (especially within complex DCT/JPEG streams) resolve to highly accurate colors.

### 2.3 Refactor Device-Default Colors to Resolve Dynamically ✅
*   **Affected Files:** `AFPColorValue.java`, `PdfHandler.java`
*   **Proposed Solution:**
    *   Decouple the hardcoded RGB values in `AFPColorValue` enum for `0x0000`/`0xFF00` (DeviceDefault) and `0x00FF`/`0xFFFF` (Default).
    *   Introduce context-aware resolution. When resolving these default values, query the current media configuration, active stylesheet/form definition, or a default presentation environment color.
    *   Prevent contrast collisions (e.g., black text on black background) by ensuring defaults adjust to light or dark backgrounds.

### Testing & Verification
*   Add unit tests in `ColorHandlerTest.java` targeting `Highlight` and `YCbCr` color spaces.
*   Assert that raw byte inputs for these color spaces yield correct, non-null iText `Color` representations.

---

## Phase 3: Bi-level Image Colorization & IM Image Support ✅

### Goal
Implement high-fidelity rendering for bi-level images acting as stencil masks and restore visual content for legacy IM Image objects.

### 3.1 Implement Bi-level Image Colorization via iText Stencil Masks ✅
*   **Affected Files:** `PdfImageRenderer.java`, `PdfHandler.java`
*   **Proposed Solution:**
    *   When rendering a 1-bit bi-level image, detect if it is configured to act as a stencil mask (often designated by specific structured field attributes or GOCA image properties).
    *   In iText, leverage `PdfImageXObject` with stencil masking enabled.
    *   Set the image dictionary's `/ImageMask` entry to `true`.
    *   Before rendering the image XObject onto the canvas, apply the current active text, graphics, or structured field color. This ensures the 1-bits are painted in the active color while 0-bits remain transparent.

### 3.2 Add Rendering Hooks and Support for Legacy IM Images ✅
*   **Affected Files:** `PdfHandler.java`, `com.mgz.afp.parser`
*   **Proposed Solution:**
    *   Add handling hooks in `PdfHandler` for `BII` (Begin IM Image), `EII` (End IM Image), `IID` (IM Image Input Descriptor), `ICP` (IM Image Cell Position), and `IRD` (IM Image Raster Data).
    *   Track active cell positioning coordinates (X-offset, Y-offset) and dimensions.
    *   Collect sequential `IRD` raster lines.
    *   Decode the compiled IM raster bytes (often bi-level) and render them using the newly implemented bi-level stencil/colorization routines to position them correctly in the PDF document.

### Testing & Verification
*   Develop end-to-end regression tests containing IM images and bi-level GOCA stamps.
*   Verify that the resulting PDF exhibits correct position, scaling, and colorization for both IM and IOCA bi-level images.

---

## Phase 4: CMOCA (CMR / CAT) Integration & Advanced Color Management ⏳

### Goal
Provide enterprise-grade color fidelity by mapping colors through parsed Color Management Resource (CMR) and Color Attribute Table (CAT) definitions.

### 4.1 CAT (Color Attribute Table) Paletted Mapping ⏳

#### 4.1.1 Parse and Decode CAT Self-Defining Parameters (SDPs) ⏳
*   **Affected Files:** `CAT_ColorAttributeTable.java`
*   **Proposed Solution:**
    *   Implement detailed parsing of Self-Defining Parameters (SDPs) inside the `otherData` byte array of `CAT_ColorAttributeTable.java`.
    *   Extract and decode Color Table Entry SDP blocks to build structured color mapping entries, mapping a 1-byte local color ID to target RGB, CMYK, or CIELAB values.

#### 4.1.2 Implement CAT/MCA Resource Registry in ColorContext ⏳
*   **Affected Files:** `ColorContext.java`, `PdfHandler.java`
*   **Proposed Solution:**
    *   Add registry structures in `ColorContext` to store active Color Attribute Tables (CAT) mapped by their Local ID.
    *   Implement processing of `BCA` (Begin Color Attribute Table) and `ECA` (End Color Attribute Table) brackets in `PdfHandler` to register CAT resources.
    *   Parse and map `MCA` (Map Color Attribute Table) repeating groups to associate Resource Local IDs with registered CAT resources.

#### 4.1.3 Integrate Paletted Color Resolution in ColorHandler ⏳
*   **Affected Files:** `ColorHandler.java`
*   **Proposed Solution:**
    *   Update `ColorHandler.getColor()` and `ColorHandler.getExtendedColor()` to check the active `ColorContext` for any mapped local color tables.
    *   If a matching local color table is found, map the input index/local ID to its resolved RGB, CMYK, or CIELAB color instead of using the standard OCA index.

#### 4.1.4 Add Unit and Integration Tests for CAT Mapping ⏳
*   **Affected Files:** `ColorHandlerTest.java`, new integration tests
*   **Proposed Solution:**
    *   Add unit tests in `ColorHandlerTest.java` targeting CAT SDP decoding and paletted color resolution.
    *   Verify that GOCA vector graphics and PTOCA text using paletted mapping are rendered with the overridden colors in the generated PDF.

### 4.2 CMR (Color Management Resource) ICC Profile Embedding ⏳

#### 4.2.1 Extract Raw ICC Profile Data from CMOCA Resources ⏳
*   **Affected Files:** `CMR_ColorManagementResource.java`
*   **Proposed Solution:**
    *   Add utility methods in `CMR_ColorManagementResource` to extract embedded ICC profiles from Color Conversion (CC) and Link (LK) CMR tags.

#### 4.2.2 Implement ICC-Based Color Spaces in ColorHandler ⏳
*   **Affected Files:** `ColorHandler.java`
*   **Proposed Solution:**
    *   Extend `ColorHandler.getExtendedColor()` to support creating and caching iText `PdfCnf` or `IccBased` color spaces using extracted raw ICC profiles.

#### 4.2.3 Bind and Track CMR State in ColorContext and PdfHandler ⏳
*   **Affected Files:** `ColorContext.java`, `PdfHandler.java`
*   **Proposed Solution:**
    *   Update `PdfHandler` to parse `ICMR_InvokeCMR` (Invoke CMR) commands and track active CMRs inside `ColorContext`.
    *   Bind active ICC color spaces to drawing elements (such as GOCA graphics and IOCA images) to ensure device-independent color rendering.

#### 4.2.4 Validate Conformance of ICC Profile Embedding ⏳
*   **Affected Files:** `VeraPdfCliWrapperTest.java`
*   **Proposed Solution:**
    *   Verify that generated PDFs contain valid `/ColorSpace` dictionaries with `/ICCBased` streams conforming to the PDF/VT-1 (ISO 16612-2) profile.
    *   Execute VeraPDF CLI tests to validate compliance and prevent regressions in profile embedding.
