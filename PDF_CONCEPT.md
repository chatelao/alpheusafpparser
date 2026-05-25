# High-Performance AFP to PDF Conversion - Concept

## Goal
The goal is to leverage the Alpheus AFP Parser's high-throughput, parallel architecture to generate PDF documents from AFP data streams with minimal latency and maximal core utilization.

## Architecture: `PdfHandler`

Based on the `StructuredFieldHandler` abstraction defined in `DECOUPLE_DESIGN.md`, PDF generation is implemented by a specialized `PdfHandler`.

### 1. `PdfHandler` Responsibility
- **Content Stream Generation:** Converts PTOCA (Text), GOCA (Graphics), and IOCA (Image) structured fields into PDF content stream operators.
- **Coordinate Transformation:** Maps the AFP coordinate system (typically top-left based, in Pel or 1/1440 inch) to the PDF coordinate system (typically bottom-left based, in Points).
- **State Tracking:** Maintains the current graphics state (color, font, line width) within a page.

### 2. Parallel Generation Strategy
Parallelizing PDF generation is more complex than XML because PDF is an object-based format with a global Cross-Reference (XRef) table.

- **Worker Threads (Parallel):** Each `PdfHandler` instance (created by `PdfHandlerFactory`) processes a range of AFP pages. It generates:
    - PDF **Page Objects**.
    - **Content Streams** (compressed via Flate).
    - **Resource Objects** local to the page (e.g., XObjects for images).
- **Orchestrator (Serial Assembly):** The `ParallelAfpConverter` collects these PDF object fragments.
    - It assigns global PDF Object IDs.
    - It constructs the global **Page Tree**.
    - It generates the final **XRef Table** and **Trailer**.

## Resource Mapping

### Fonts (FOCA)
- **Mapping:** AFP Coded Fonts are mapped to PDF fonts (Type 1, TrueType, or OpenType).
- **Embedding:** To ensure visual fidelity, font programs from the AFP stream (or external libraries) should be embedded/subsetted.
- **Encoding:** Handles the transition from EBCDIC-based AFP encodings to PDF encodings (Identity-H for Unicode or standard encodings).

### Images (IOCA)
- **Direct Conversion:** IOCA image segments are converted into PDF Image XObjects.
- **Optimization:** Frequently used images (e.g., logos in overlays) are identified during the preamble scan and defined once as shared XObjects to reduce file size.

### Graphics (GOCA)
- **Vector Mapping:** GOCA drawing orders (lines, arcs, boxes) are mapped directly to PDF path operators, maintaining vector quality.

## Performance Aspects

### 1. Concurrency
By using one `PdfHandler` per core, we can perform the expensive tasks of PTOCA-to-PDF string conversion and Flate compression in parallel. This eliminates the CPU bottleneck typical of single-threaded PDF generators.

### 2. Zero-Copy Data Passing
`PdfHandler` receives `StructuredField` objects that already contain the decoded data. It accesses the raw buffers directly for large payloads (like image data), avoiding redundant memory allocations.

### 3. Incremental Writing
The orchestrator can write page objects to the output stream as soon as a worker thread finishes, keeping memory usage low even for very large documents.

## Robustness of Decoupling
The `StructuredFieldHandler` design is robust enough for PDF because:
- **Preamble Support:** The `ParallelAfpConverter` processes the preamble sequentially, allowing a `PdfHandler` to collect all global resources (Form Maps, Overlays, Font Mappings) before parallel page processing begins.
- **Thread Isolation:** Each `PdfHandler` is independent, allowing for safe parallel execution.
- **Lifecycle Management:** The standard `close()` method on the handler allows for clean finalization of PDF structures.
