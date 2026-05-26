# PDF Integration Concept: iText 9

This document defines the technical integration between the Alpheus AFP Parser and the [iText 9](https://github.com/itext/itext-java) PDF library. It leverages the decoupled `StructuredFieldHandler` architecture to enable high-performance, parallel PDF generation.

## Architectural Goal

The primary objective is to translate MO:DCA structured fields and their associated content architectures (PTOCA, GOCA, IOCA, BCOCA) into a high-fidelity PDF/VT-1 (ISO 16612-2) stream while maintaining the throughput advantages of Alpheus's parallel parsing engine.

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

## Parallel Assembly Strategy

To maintain O(1) memory footprint and high CPU utilization:
1. **Parallel Generation**: Worker threads produce "Object Fragments" (streams of serialized PDF objects with temporary IDs).
2. **Global Resource Scanning**: A fast preamble scan identifies shared resources (Fonts, Overlays) to be placed in the global PDF Resource dictionary.
3. **Merging**: The `ParallelAfpConverter` performs a single-pass merge:
   - Remaps temporary Object IDs to final, contiguous IDs.
   - Constructs the PDF Page Tree and XRef table.
   - Writes the final Cross-Reference Stream and Trailer.

## Compliance
- **Target**: PDF/VT-1 (ISO 16612-2).
- **Metadata**: MO:DCA `TLEs` (Tag Logical Elements) are mapped to PDF/VT DPart metadata, enabling record-level indexing and processing in production print environments.
