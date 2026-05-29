# ADF to PDF/VT Conversion Roadmap

This document outlines the phased implementation plan for converting the Alpheus AFP object model (ADF) to **PDF/VT (ISO 16612-2)**, as defined in `ADF_TO_PDF_CONCEPT.md`.

## Status Summary
- **PDF/VT Structural Implementation**: 🚧 In Progress
- **Resource Management & Optimization**: ⏳ Pending
- **Content Conversion (Base Operators)**: ⏳ Pending
- **Verification & Compliance**: ⏳ Pending

---

## Phase 1: PDF/VT Structural Implementation 🚧
Initialize the core PDF/VT structure and map the MO:DCA document hierarchy.

- 🚧 **1.1 Structural State Tracking**:
    - ✅ **1.1.1 BNG/ENG Stack**: Implement internal stack-based tracking of Named Page Groups in `PdfHandler`.
    - ⏳ **1.1.2 TLE Accumulation**: Buffer TLE triplets at the current group level for later metadata mapping.
- ⏳ **1.2 PDF/VT Catalog & Root**:
    - ⏳ **1.2.1 DPartRoot Initialization**: Create `/DPartRoot` and `/DPartMetadata` dictionaries in the PDF Catalog.
    - ⏳ **1.2.2 OutputIntents**: Define `/OutputIntents` for PDF/X-4 compliance (ISO 15930-7).
- ⏳ **1.3 MO:DCA to DPart Mapping**:
    - ⏳ **1.3.1 DPart Node Creation**: Map MO:DCA `BNG` nodes to PDF `/DPart` dictionary objects.
    - ⏳ **1.3.2 Metadata Injection**: Map accumulated TLE values to `/Property` entries within the corresponding `/DPart`.
    - ⏳ **1.3.3 DPart Hierarchy Verification**: Ensure correct parent-child relationships in the multi-level `/DPart` tree.

## Phase 2: Resource Management & Optimization ⏳
Optimize resource handling for high-performance variable data printing.

- ⏳ **Global Resource Manager**: Implement logic to move shared XObjects (Overlays, Page Segments) to global Page Tree resources.
- ⏳ **FOCA to PDF/X-4 Font Embedding**: Ensure all fonts are fully embedded and subsetted per PDF/X-4 requirements.
- ⏳ **IOCA Image Optimizer**: Map repeated IOCA objects to a single Image XObject instance to reduce file size.

## Phase 3: Content Conversion (Base Operators) ⏳
Implement the drivers for converting AFP content architectures to PDF operators.

- ⏳ **PTOCA Driver**: Map PTOCA control sequences to PDF Text Objects (`BT`/`ET`) and positioning operators (`Td`/`Tm`).
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
