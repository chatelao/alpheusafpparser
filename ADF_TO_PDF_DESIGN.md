# Design: ADF to PDF/VT Conversion (iText 9 Integration)

This document specifies the technical design for converting the Alpheus AFP object model (ADF) to **PDF/VT-1 (ISO 16612-2)** using the **iText 9** library.

## 1. Technology Selection: iText 9

### 1.1. Rationale
**iText Core (v9.0+)** is chosen as the primary PDF engine for the following reasons:
-   **Native PDF/VT Support**: iText provides high-level APIs for managing the DPart (Document Part) hierarchy and metadata required by ISO 16612-2.
-   **Performance**: Optimized for high-throughput generation and parallel processing.
-   **Resource Optimization**: Mature support for Form XObjects and global resource sharing, which is critical for reducing the footprint of variable data streams.
-   **License Compatibility**: iText 9 is licensed under AGPLv3, which is compatible with the Alpheus AFP Parser's **GPLv3** license.

### 1.2. Alternatives Considered
-   **Apache PDFBox**: While mature and OSS (Apache License), it lacks high-level PDF/VT structural support and generally exhibits lower performance in high-volume generation compared to iText.

---

## 2. Component Architecture

The integration utilizes the decoupled `StructuredFieldHandler` architecture defined in `DECOUPLE_DESIGN.md`.

### 2.1. `PdfHandler` (StructuredFieldHandler)
The core component that consumes MO:DCA structured fields and translates them into iText calls.
-   **Internal State**: Maintains a `PdfDocument` (or a page fragment) and a `GraphicsStateStack`.
-   **Boundary Tracking**: Uses a `java.util.Deque` to track MO:DCA structural boundaries (`BDT`/`EDT`, `BNG`/`ENG`, `BPG`/`EPG`).

### 2.2. `PdfHandlerFactory` (HandlerFactory)
Responsible for instantiating `PdfHandler` for both the master (preamble) and worker threads in the `ParallelAfpConverter`.

### 2.3. `GraphicsStateStack`
Tracks active AFP attributes and maps them to iText's `Canvas` properties:
-   **Color**: Mapped to `DeviceRGB` or `DeviceCMYK`.
-   **Fonts**: Maps FOCA Resource Names to embedded `PdfFont` instances.
-   **Line Style**: Mapped to Line Width, Dash Patterns, and Join/Cap styles.

---

## 3. PDF/VT Structural Mapping

### 3.1. DPart Hierarchy (ISO 16612-2, §10)
-   **Root**: The `PdfCatalog` will contain a `/DPartRoot` entry.
-   **Mapping**:
    -   `BDT` (Begin Document) -> Creates a top-level `/DPart` node.
    -   `BNG` (Begin Named Group) -> Creates a nested `/DPart` node.
    -   `TLE` (Tag Logical Element) -> Attributes are added to the `/DPart` dictionary as metadata.
-   **Implementation**: Use `iText`'s `PdfStructTreeController` or low-level `PdfDictionary` manipulation to build the tree.

### 3.2. Output Intents
To comply with PDF/VT (and the base PDF/X-4), an `/OutputIntent` must be defined in the Catalog, usually specifying a CMYK profile (e.g., FOGRA39 or GRACoL).

---

## 4. Resource Management & Optimization

### 4.1. Global Resource Pool
AFP Overlays and Page Segments are shared resources. To minimize file size:
1.  **First Pass**: The master handler identifies all `Map Medium Overlay` (MMO) and `Map Page Segment` (MPS) fields.
2.  **Resource Conversion**: Overlays are converted once to `PdfFormXObject`.
3.  **Global Injection**: These XObjects are stored in the `/Resources` dictionary of the Page Tree root, allowing pages to reference them via the `Do` operator without duplication.

### 4.2. Font Embedding
All fonts used in the AFP stream (FOCA) must be fully embedded and subsetted to meet PDF/X and PDF/VT requirements. The `PdfHandler` will maintain a cache of `PdfFont` objects.

---

## 5. Content Translation Details

### 5.1. Coordinate Transformation
-   **AFP Space**: Origin typically at top-left, units in Pel or 1/1440 inch.
-   **PDF User Space**: Origin at bottom-left, units in Points (1/72 inch).
-   **Design**: A `CoordinateTransformer` applies a global CTM (Current Transformation Matrix) at the start of each page to flip the Y-axis and scale the units.

### 5.2. PTOCA (Text)
-   `AMIs` and `RMIs` are mapped to `Canvas.showText(text, x, y)` or low-level `Tm`/`Td` operators.
-   Support for EBCDIC to UTF-8/Unicode translation using the `UtilCharacterEncoding` registry.

### 5.3. GOCA (Graphics)
-   Drawing orders (Line, Fillet, Area) are mapped directly to `PdfCanvas` path operators (`lineTo`, `curveTo`, `fill`).

### 5.4. IOCA (Images)
-   IOCA segments are decoded and wrapped in `PdfImageXObject`.
-   Transparent images are handled using PDF Image Masks or Transparency Groups.

---

## 6. Parallel Assembly Design

To support Alpheus's parallel engine:
1.  **Worker Tasks**: Each thread generates a `PdfDocument` fragment containing its assigned pages.
2.  **Fragment Serialization**: Fragments are serialized to memory (as PDF objects).
3.  **Final Merge**: The `ParallelAfpConverter` uses `PdfDocument.copyPagesTo()` to merge fragments into the final output stream, while the `OrderedResultCollector` ensures the correct sequence of DPart nodes and pages.
