# Concept: ADF to PDF Conversion

This document outlines the conceptual architecture and implementation plan for converting the Alpheus AFP object model (ADF) to the Portable Document Format (PDF), with specific references to the **ISO 32000-1 (PDF 1.7)** specification.

## Architecture Overview

The conversion process follows a three-stage pipeline:

1.  **Object Model Traversal**: Recursive traversal of the Alpheus object tree (Documents, Pages, Overlays, Page Segments).
2.  **Resource Resolution**: Dynamic lookup and mapping of AFP resources (Fonts, Images, Form Maps) to their PDF equivalents in the `/Resources` dictionary.
3.  **Primitive Mapping**: Translation of AFP-specific content architectures (PTOCA, GOCA, IOCA, BCOCA) into PDF page content stream operators.

---

## Detailed Mapping & PDF Spec References

### 1. Document Structure & Coordinates
- **AFP**: MO:DCA coordinate system (origin top-left, typically 1440 or 240 pels/inch).
- **PDF (ISO 32000-1, §8.3)**: Default user space (origin bottom-left, 1/72 inch).
- **Transformation**:
    - Apply a global Coordinate Transformation Matrix (CTM) using the `cm` operator to flip the Y-axis and scale to points.
    - Page size mapping to `/MediaBox` and `/CropBox`.

### 2. Text Presentation (PTOCA to PDF Text)
- **AFP**: PTOCA Control Sequences (AMI, RMI, AMB, RMB).
- **PDF (ISO 32000-1, §9.2, §9.4)**:
    - **Text State**: Maintain `Tf` (Font and Size), `Tc` (Character Spacing), and `Tw` (Word Spacing).
    - **Text Objects**: Enclose text runs in `BT` (Begin Text) and `ET` (End Text) blocks.
    - **Positioning**:
        - Translate `AMI` and `AMB` to absolute coordinates using `Td` or `Tm` operators.
        - Translate `RMI` and `RMB` to relative shifts using `Td`.
    - **Rendering**: Use `Tj` (Show Text) for transparent data (`TRN`).
    - **Font Mapping**: Map FOCA Coded Fonts to PDF `/Font` resources. Handle EBCDIC-to-CID mapping using `/ToUnicode` CMap (§9.10).

### 3. Vector Graphics (GOCA to PDF Graphics)
- **AFP**: GOCA Drawing Orders (Lines, Arcs, Fillets, Areas).
- **PDF (ISO 32000-1, §8.5)**:
    - **Path Construction**:
        - `GSCP` (Set Current Position) -> `m` (MoveTo).
        - `GLINE` (Line) -> `l` (LineTo).
        - `GCBEZ` (Bezier Curve) -> `c`, `v`, or `y` (Curveto).
        - `GEAR` (End Area) -> `h` (ClosePath) followed by `f` (Fill) or `f*` (Even-Odd Fill).
    - **Path Painting**:
        - Use `S` (Stroke) for lines and `f` (Fill) for areas.
    - **Graphics State (§8.4)**:
        - `GSCOL` (Set Color) -> `rg`/`RG` (RGB) or `k`/`K` (CMYK).
        - `GSLW` (Set Line Width) -> `w`.
        - `GSLT` (Set Line Type) -> `d` (Dash pattern).

### 4. Image Data (IOCA to PDF Images)
- **AFP**: IOCA Image Content (Function Sets 10, 11, 45).
- **PDF (ISO 32000-1, §8.9)**:
    - **XObjects**: Encapsulate IOCA data as Image XObjects (`/Subtype /Image`).
    - **Dictionary Entries**:
        - `/Width` and `/Height` from IOCA descriptor.
        - `/ColorSpace` (§8.6) mapped from IOCA IDEs (DeviceGray, DeviceRGB, DeviceCMYK).
        - `/BitsPerComponent` mapped from IOCA bit depth.
    - **Compression**: Map IOCA compression (G4, JPEG, LZW) to PDF `/Filter` entries (`/CCITTFaxDecode`, `/DCTDecode`, `/LZWDecode`).

### 5. Resources & Complex Objects
- **Overlays & Page Segments**:
    - Map to PDF **Form XObjects** (§8.10) for efficient re-use.
    - An `IPO` (Include Page Overlay) becomes a `Do` operator invoking a named Form XObject.
- **Bar Codes (BCOCA)**:
    - Rendered as vector paths (GOCA-style) or referenced as a high-level PDF `/XObject` if generated externally.

---

## Implementation Task List

### Phase 1: Core Engine & Coordinate Mapping
- [ ] Implement `PDFStreamWriter` to generate raw PDF operator syntax.
- [ ] Implement MO:DCA Pel-to-Point coordinate transformation logic.
- [ ] Initialize PDF structural dictionaries (Catalog, Pages, Resources).

### Phase 2: Resource Resolution
- [ ] **FOCA Resolver**: Map AFP fonts to PDF Standard 14 fonts or embedded TrueType/CFF.
- [ ] **XObject Manager**: Convert Overlays (`BMO`/`EMO`) and Page Segments (`BPS`/`EPS`) into reusable `/Form` XObjects.
- [ ] **Image Processor**: Extract IOCA segments and wrap them in `/Image` XObjects.

### Phase 3: Content Architecture Implementation
- [ ] **PTOCA Driver**: Implement a state machine for PTOCA control sequences mapping to `BT`/`ET` operators.
- [ ] **GOCA Driver**: Implement path construction mapping (Move, Line, Curve, Close).
- [ ] **BCOCA Renderer**: Implement barcode drawing using PDF primitives (rectangles and text).

### Phase 4: Metadata & Navigation
- [ ] Map `TLE` (Tag Logical Element) to PDF PieceInfo or Document Metadata.
- [ ] Map `BNG` (Begin Named Group) to PDF Bookmarks (`/Outlines`).

### Phase 5: Validation
- [ ] Compare PDF output against IBM AFP Viewer screenshots.
- [ ] Validate generated PDF structure with `pdf-parser` or `preflight`.
