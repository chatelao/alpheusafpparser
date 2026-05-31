# Audit: PDF Output & Verification Status

## 1. Executive Summary
The current "lack of visible tests" for PDF output is due to the project's phased implementation strategy. While the core conversion engine (`PdfHandler`) has significant internal logic for structural mapping and primitive rendering, the testing suite currently focuses on **internal state verification** rather than **visual or content-based validation**.

There are currently no integration tests that verify the actual bytes of the generated PDF or perform visual regression against expected output.

## 2. Implementation Status Audit

### 2.1. MO:DCA & Structural (Phase 1) - ✅ 90% Complete
- **DPart Hierarchy**: Correct mapping of `BDT`, `BNG`, and `BPG` to the PDF/VT Document Part tree.
- **Metadata**: `TLE` tags are successfully injected into `/Property` entries.
- **Output Intents**: Initialized in the Catalog, but ICC profile integration is still pending.

### 2.2. Coordinate Transformation - ✅ 100% Complete
- **Mapping**: Pel/1440-to-Points mapping is implemented in `CoordinateTransformer`.
- **Y-Axis Flip**: Standard PDF bottom-left origin is handled via CTM.

### 2.3. PTOCA (Text) - ⏳ 50% Complete
- **State Tracking**: Positioning (`AMI`, `RMI`, etc.) and orientation (`STO`) are tracked.
- **Rendering**: Implemented via `showText`, but currently restricted to a **standard Helvetica fallback font**.
- **Gap**: FOCA font resolution and subsetting/embedding (Phase 2) are not yet implemented. This prevents "real" documents from rendering correctly.

### 2.4. GOCA (Graphics) - ✅ 80% Complete
- **Primitives**: Lines, Boxes, Arcs (Full/Partial), and Fillets (approximated) are implemented.
- **Attributes**: Color, line width, line type, and area filling are tracked and mapped to iText operators.

### 2.5. BCOCA (Barcodes) - ⏳ 10% Complete
- **Status**: Descriptor (`BDD`) tracking is implemented.
- **Gap**: The actual rendering of barcode vectors is completely missing.

### 2.6. IOCA (Images) - ⏳ 0% Complete
- **Status**: Not yet implemented in `PdfHandler`.

## 3. Test Suite Audit

### 3.1. `PdfHandlerStructureTest`
- **Focus**: Verifies that `PdfHandler` correctly updates its internal `PdfTextState`, `PdfGraphicsState`, and `PdfBarcodeState` when processing fields.
- **Visibility**: These tests are "invisible" to the user because they don't produce or inspect PDF files; they only check Java object properties.

### 3.2. `PdfHandlerBenchmarkTest`
- **Focus**: Measures parsing throughput using a stubbed PDF flow.
- **Visibility**: Primarily a performance metric, not a functional verification.

### 3.3. Missing Verification Layers
1. **Visual Regression**: No tests currently render AFP to PDF and compare pixels or PDF operators against a "gold standard."
2. **Compliance Validation**: No automated checks for PDF/VT-1 or PDF/X-4 compliance (e.g., using VeraPDF).
3. **End-to-End Integration**: No tests verifying that the `Afp2Xml --format pdf` CLI produces a readable file for standard PDF viewers.

## 4. Recommendations & Next Steps

1. **Implement `Afp2PdfIntegrationTest`**:
   - Create a test that runs the converter on a small sample (e.g., `test_dir/Chapter_1.afp`) and verifies the resulting file is a valid PDF and contains expected text strings (using `PdfReader`).
2. **Prioritize Font Resolution**:
   - The inability to resolve and embed fonts is the primary blocker for creating visually meaningful PDF output.
3. **Introduce "Gold Standard" Baselines**:
   - Once font embedding is in place, establish a set of reference PDFs for regression testing.
4. **IOCA/BCOCA Implementation**:
   - Follow the roadmap to fill the gaps in image and barcode rendering.
