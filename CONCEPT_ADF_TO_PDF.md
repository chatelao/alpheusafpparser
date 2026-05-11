# Concept: ADF to PDF Conversion

This document outlines the conceptual architecture and implementation plan for converting the Alpheus AFP object model (ADF) to the Portable Document Format (PDF).

## Architecture Overview

The conversion process follows a three-stage pipeline:

1.  **Object Model Traversal**: Recursive traversal of the Alpheus object tree (Documents, Pages, Overlays, Page Segments).
2.  **Resource Resolution**: Dynamic lookup and mapping of AFP resources (Fonts, Images, Form Maps) to their PDF equivalents.
3.  **Primitive Mapping**: Translation of AFP-specific content architectures (PTOCA, GOCA, IOCA, BCOCA) into PDF operators.

---

## Implementation Task List

### 1. Core Framework & Document Assembly
- [ ] Initialize PDF Document Structure (Catalog, Pages, Resources).
- [ ] Implement `PDFConverter` engine to manage the Alpheus `StructuredField` object tree.
- [ ] Map MO:DCA Page/Overlay coordinate systems to PDF user space (MediaBox, CropBox).
- [ ] Handle Page Rotation and Scaling (PGP, MDD).

### 2. Resource Management (The "Resource Resolver")
- [ ] **Fonts (FOCA)**:
    - [ ] Map FOCA Coded Fonts to PDF Type 1 or TrueType fonts.
    - [ ] Handle EBCDIC-to-Unicode mapping using Alpheus encoding utilities.
    - [ ] Implement font embedding for custom AFP font character sets.
- [ ] **Overlays & Page Segments**:
    - [ ] Convert Overlays to PDF Form XObjects for efficient re-use.
    - [ ] Map `IPO` (Include Page Overlay) and `IPS` (Include Page Segment) to XObject invocations.
- [ ] **Images (IOCA)**:
    - [ ] Convert IOCA Function Sets (FS10, FS11, FS45) to PDF Image XObjects.
    - [ ] Support bilevel, grayscale, and CMYK color spaces.

### 3. Content Architecture Mapping
- [ ] **Text (PTOCA)**:
    - [ ] Map PTOCA Control Sequences (AMI, RMI, AMB, RMB) to PDF text positioning operators (`Td`, `Tm`).
    - [ ] Translate `TRN` (Transparent Data) and `USC` (Underscore) to PDF text and drawing operators.
    - [ ] Support complex text (UCT) and glyph layout (GLC).
- [ ] **Graphics (GOCA)**:
    - [ ] Map GOCA Drawing Orders (Lines, Arcs, Areas) to PDF path operators (`l`, `c`, `f`).
    - [ ] Translate GOCA attributes (Color, Line Type, Pattern) to PDF graphics state.
- [ ] **Bar Codes (BCOCA)**:
    - [ ] Integrate a barcode generation library (e.g., Barcode4J or Zxing) to render BCOCA data.
    - [ ] Map BCOCA placement and sizing to PDF coordinates.

### 4. Color & Metadata
- [ ] **Color Management (CMOCA)**:
    - [ ] Apply Color Management Resources (CMRs) to PDF color spaces.
    - [ ] Handle named colors and spot colors.
- [ ] **Metadata (MOCA)**:
    - [ ] Map `TLE` (Tag Logical Element) to PDF Document Info or XMP Metadata.
    - [ ] Support PDF Bookmarks based on Named Page Groups (BNG).

### 5. Verification & Tooling
- [ ] Implement `AFP2PDF` CLI command.
- [ ] Create a visual regression test suite (AFP vs. PDF screenshots).
- [ ] Support "Raw ADF" to "Annotated PDF" for debugging (showing SF boundaries).
