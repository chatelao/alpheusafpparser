# Concept: ADF to PDF/VT Conversion (iText 9)

This document outlines the conceptual architecture and implementation plan for converting the Alpheus AFP object model (ADF) to **PDF/VT (ISO 16612-2)** using the [iText 9](https://github.com/itext/itext-java) PDF library. PDF/VT is specifically designed for Variable Data and Transactional printing, making it the ideal target for AFP streams.

## Architectural Goal

The primary objective is to translate MO:DCA structured fields and their associated content architectures (PTOCA, GOCA, IOCA, BCOCA) into a high-fidelity PDF/VT-1 (ISO 16612-2) stream while maintaining the throughput advantages of Alpheus's parallel parsing engine.

## Architecture Overview

The conversion process follows a three-stage pipeline, optimized for PDF/VT requirements:

1.  **Object Model Traversal**: Recursive traversal of the Alpheus object tree, mapping MO:DCA document structure to the **PDF/VT Document Part (DPart) Hierarchy**.
2.  **Resource Resolution**: Dynamic lookup and mapping of AFP resources (Fonts, Images, Form Maps) to **Global Resources** in the PDF/VT structure.
3.  **Primitive Mapping**: Translation of AFP content architectures (PTOCA, GOCA, IOCA, BCOCA) into PDF operators, utilizing PDF/X-4/5 base standards.

## Core Mapping Strategy

The integration centers on the `PdfHandler`, which implements the `StructuredFieldHandler` interface.

### 1. Structure and Orchestration
- **`PdfHandler`**: Manages the lifecycle of a PDF document fragment (e.g., a single page or a page group). It uses an iText `PdfDocument` and `PdfWriter` internally.
- **`ParallelAfpConverter`**: Coordinates multiple `PdfHandler` instances across worker threads. Each thread generates a serialized stream of PDF objects for its assigned pages.

### 2. Content Translation (AFP to iText)
- **Text (PTOCA)**: Translated using `PdfCanvas` low-level text operators. AFP `AMIs` and `RMIs` (Absolute/Relative Move Inline) are mapped to `Tm` (Text Matrix) or `Td` (Move Text) operators.
- **Graphics (GOCA)**: Mapped to `PdfCanvas` path construction and painting operators (e.g., `moveTo`, `lineTo`, `curveTo`, `stroke`, `fill`).
- **Images (IOCA)**: Wrapped into `PdfImageXObject` instances. For efficiency, repeated images are stored as global resources.
- **Overlays/Page Segments**: Mapped to `PdfFormXObject`. This is critical for PDF/VT performance, as it allows the RIP to cache and reuse the background content.

## Technical Components

### `GraphicsStateStack`
Tracks the active AFP presentation state, including:
- Current Color (mapped to PDF DeviceRGB, DeviceCMYK, or Spot Colors).
- Active Font (mapped to embedded and subsetted PDF fonts).
- Line Attributes (Width, Type, Join/Cap styles).
- Mix Modes (Overstrike mapped to PDF Transparency Groups where necessary).

### `CoordinateTransformer`
AFP uses a variety of coordinate systems (usually Pel or 1440 DPI, with different origins and orientations). The `CoordinateTransformer` provides:
- Translation of AFP logical coordinates to PDF User Space (Points, 72 DPI).
- Handling of Page/Overlay rotations (0°, 90°, 180°, 270°).

### `PdfContentStream`
An abstraction layer that sits between the `PdfHandler` and `PdfCanvas`. It handles the buffering and serialization of PDF operators, ensuring that the generated fragments are valid for later assembly by the orchestrator.

---

## PDF/VT Specific Mappings (ISO 16612-2)

### 1. Document Structure (MO:DCA to DPart Hierarchy)
- **AFP**: MO:DCA `BNG`/`ENG` (Named Page Groups) and `BDT`/`EDT` (Documents).
- **PDF/VT (ISO 16612-2, §10)**:
    - **DPartRoot**: The root of the Document Part Hierarchy.
    - **DPart Hierarchy**: Map MO:DCA nested Page Groups to a multi-level `/DPart` tree.
    - **DPart Metadata**:
        - Translate `TLE` (Tag Logical Element) triplets into entries in the `/DPart` dictionary's metadata or `/Property` entries.
        - This enables high-speed indexing and record-level processing in production RIPs.

### 2. Variable Data Optimization (Overlays to Form XObjects)
- **AFP**: `BMO`/`EMO` (Overlays) and `BPS`/`EPS` (Page Segments).
- **PDF/VT Strategy**:
    - Overlays are mapped to **Form XObjects** (ISO 32000-1, §8.10).
    - To comply with PDF/VT performance goals, these XObjects are stored in the **Global Resources** of the PDF/VT file.
    - Repeated use of an overlay across thousands of pages uses a single `Do` operator reference, minimizing file size and RIP time.

### 3. Coordinate Transformation & Graphics State
- **Transformation**: Apply CTM (`cm` operator) to map AFP coordinate systems to PDF User Space (§8.3), respecting PDF/VT's requirement for PDF/X-4/5 compliance (e.g., proper `/MediaBox` and `/TrimBox` definitions).
- **Transparency**: Utilize PDF transparency groups (§11) where AFP uses mixing rules (e.g., Overstrike, GOCA Mix), as permitted by PDF/X-4 (the base for PDF/VT-1).

### 4. Content Architecture Mapping
- **Text (PTOCA)**: Map to PDF Text Objects (§9) using embedded fonts. Utilize PDF/VT's character-level positioning for precise PTOCA `AMI`/`RMI` reproduction.
- **Graphics (GOCA)**: Map to PDF path construction operators (§8.5).
- **Images (IOCA)**: Wrap in Image XObjects (§8.9).

## Parallel Assembly Strategy

To maintain O(1) memory footprint and high CPU utilization:
1. **Parallel Generation**: Worker threads produce "Object Fragments" (streams of serialized PDF objects with temporary IDs).
2. **Global Resource Scanning**: A fast preamble scan identifies shared resources (Fonts, Overlays) to be placed in the global PDF Resource dictionary.
3. **Merging**: The `ParallelAfpConverter` performs a single-pass merge:
   - Remaps temporary Object IDs to final, contiguous IDs.
   - Constructs the PDF Page Tree and XRef table.
   - Writes the final Cross-Reference Stream and Trailer.

---

## Detailed Implementation Task List

### Phase 1: PDF/VT Structural Implementation
- [ ] Initialize **DPart Hierarchy** (`/DPartRoot`) in the PDF Catalog.
- [ ] Implement MO:DCA `BNG` to PDF `/DPart` recursive mapping.
- [ ] Map `TLE` values to Record-level metadata.
- [ ] Define `/OutputIntents` (PDF/X compliance) as required by ISO 16612-2.
- [ ] **CLI Integration**: Add `-f` / `--format` flag to the `Afp2Xml` utility to support PDF output.

### Phase 2: Resource Management & Optimization
- [ ] **Global Resource Manager**: Implement logic to move shared XObjects (Overlays, Page Segments) to the global Page Tree resources.
- [ ] **FOCA to PDF/X-4 Font Embedding**: Ensure all fonts are fully embedded and subsetted as per PDF/X-4 requirements.
- [ ] **IOCA Image Optimizer**: Map repeated IOCA objects to a single Image XObject instance.

### Phase 3: Content Conversion (Base Operators)
- [ ] **PTOCA Driver**: Map control sequences to `BT`/`ET` and `Td`/`Tm` operators.
- [ ] **GOCA Driver**: Map path drawing orders (Line, Arc, Area) to PDF paths.
- [ ] **BCOCA Renderer**: Draw barcodes using vector primitives to ensure resolution independence.

### Phase 4: Verification & Compliance
- [ ] Validate generated files against **PDF/VT-1** profiles using preflight tools (e.g., Callas pdfToolbox or Adobe Acrobat Pro).
- [ ] Verify DPart hierarchy navigation in PDF/VT-aware viewers.
- [ ] Compare record-level extraction from PDF metadata against original AFP `TLE` values.
