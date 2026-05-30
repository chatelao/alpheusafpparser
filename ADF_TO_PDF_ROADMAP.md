# ADF to PDF/VT Conversion Roadmap

This document outlines the phased implementation plan for converting the Alpheus AFP object model (ADF) to **PDF/VT (ISO 16612-2)**, as defined in `ADF_TO_PDF_CONCEPT.md` and `ADF_TO_PDF_DESIGN.md`.

## Status Summary
- **Phase 0: Design & Library Selection**: ✅ Complete
- **Phase 1: PDF/VT Structural Implementation**: ⏳ Pending
- **Phase 2: Resource Management & Optimization**: ⏳ Pending
- **Phase 3: Content Conversion (Base Operators)**: ⏳ Pending
- **Phase 4: Verification & Compliance**: ⏳ Pending

---

## Phase 0: Design & Library Selection ✅
Establish the technical foundation and select the primary PDF engine.

- ✅ **Library Selection**: Chose **iText 9** for native PDF/VT support and performance.
- ✅ **Architectural Design**: Defined `PdfHandler` and `PdfHandlerFactory` integration with the decoupled handler architecture.
- ✅ **Structural Mapping Strategy**: Defined MO:DCA to DPart hierarchy mapping.

## Phase 1: PDF/VT Structural Implementation ⏳
Initialize the core PDF/VT structure and map the MO:DCA document hierarchy using iText 9.

- ✅ **Initialize DPart Hierarchy**: Create `/DPartRoot` in the PDF Catalog.
- ⏳ **MO:DCA Boundary Mapping**:
    - ✅ **Structural Boundary Tracking**: Implement a stack-based mechanism to track nested MO:DCA groups (`BDT`/`EDT`, `BNG`/`ENG`, `BPG`/`EPG`).
    - ⏳ **DPart Node Creation**: Create iText 9 `/DPart` objects corresponding to tracked MO:DCA boundaries.
    - ⏳ **Page-to-DPart Assignment**: Connect PDF page objects to their respective leaf `/DPart` nodes.
- ✅ **CLI Integration**: Add `-f` / `--format` flag to `Afp2Xml` to enable PDF output and manual testing. (Standard flags `-p` for parallel and `-P` for PTX debug are supported).
- ⏳ **TLE Metadata Mapping**: Map `TLE` (Tag Logical Element) values to record-level metadata within the `/DPart` hierarchy.
- ⏳ **Output Intents**: Define `/OutputIntents` (e.g., FOGRA39) for PDF/X compliance as required by ISO 16612-2.

## Phase 2: Resource Management & Optimization ⏳
Optimize resource handling for high-performance variable data printing.

- ⏳ **Global Resource Manager**: Implement logic to move shared XObjects (Overlays, Page Segments) to global Page Tree resources.
    - ⏳ **Global Overlay Tracking (MMO)**: Identify and track Medium Overlays across the document.
    - ⏳ **Global Page Segment Tracking (MPS)**: Identify and track Page Segments across the document.
    - ⏳ **Resource conversion to PdfFormXObject**: Convert AFP resources to reusable PDF Form XObjects.
- ⏳ **FOCA to PDF/X-4 Font Embedding**: Ensure all fonts are fully embedded and subsetted per PDF/X-4 requirements.
- ⏳ **IOCA Image Optimizer**: Map repeated IOCA objects to a single Image XObject instance to reduce file size.

## Phase 3: Content Conversion (Base Operators) ⏳
Implement the drivers for converting AFP content architectures to PDF operators.

- ⏳ **Coordinate Transformation**: Implement Pel/1440-to-Points mapping and Y-axis flip.
- ⏳ **PTOCA Driver**: Map PTOCA control sequences to PDF Text Objects (`BT`/`ET`) and positioning operators (`Td`/`Tm`).
    - ⏳ **Font Mapping**: Resolve FOCA Local IDs (LID) to embedded `PdfFont` instances.
    - ⏳ **Basic Text Rendering**: Map `AMI` (Absolute Move Inline) and `RMI` (Relative Move Inline) to PDF positioning.
- ⏳ **GOCA Driver**: Map GOCA path drawing orders (Line, Arc, Area) to PDF path construction operators.
- ⏳ **BCOCA Renderer**: Implement barcode drawing using vector primitives for resolution independence.

## Phase 4: Verification & Compliance ⏳
Ensure the generated output meets the PDF/VT-1 standard and accurately reflects the source AFP.

- ⏳ **PDF/VT-1 Validation**: Validate generated files against PDF/VT-1 profiles using preflight tools.
- ⏳ **DPart Hierarchy Verification**: Verify navigation and structure in PDF/VT-aware viewers.
- ⏳ **Metadata Integrity**: Compare record-level extraction from PDF metadata against original AFP `TLE` values.

---

## Implementation Strategy
1. **Standards Compliance**: All generated PDF files must comply with **ISO 16612-2 (PDF/VT)** and its base standard **ISO 15930-7 (PDF/X-4)**.
2. **Performance Focus**: Prioritize the use of Form XObjects and Global Resources to ensure RIP-friendly output for high-volume production.
3. **Traceability**: Link implementation details back to specific sections of the ISO 16612-2 and ISO 32000-1/2 specifications.
