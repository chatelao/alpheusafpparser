# Color Handling Gaps and Bugs Report

This document analyzes the current implementation of color handling in graphics (GOCA), images (IOCA/IM), and general color spaces within the Alpheus AFP-to-PDF conversion engine. It outlines critical bugs, functional gaps, and architectural limitations, referencing specific source files, methods, and line numbers.

---

## 1. GOCA (Graphics Object Content Architecture) Gaps & Bugs

### 1.1 GSPCOL (Set Process Color) Ignored in Rendering (Critical Bug)
*   **Affected Files:**
    *   `src/main/java/com/mgz/pdf/PdfHandler.java`
    *   `src/main/java/com/mgz/pdf/PdfGraphicsState.java`
*   **Detailed Findings:**
    *   In `PdfHandler.handleDrawingOrderInternal()`, when a `GSPCOL_SetProcessColor` drawing order (type `0x24`) is received, the parser updates `graphicsState` with the process color space, number of bits per component, and raw color value byte array (lines 730–736).
    *   However, `PdfHandler.applyGraphicsState()` (lines 1433–1440), which applies stroke and fill colors to the iText `currentCanvas` before drawing GOCA shapes, lines, arcs, beziers, boxes, etc., **completely ignores** the process color properties stored in `graphicsState`!
    *   `applyGraphicsState()` is hardcoded to retrieve colors only via `ColorHandler.getColor(graphicsState.getColor())`, which maps to the 1-byte legacy OCA color index.
    *   **Impact:** High-fidelity process colors (like custom RGB, CMYK, or CIELAB) defined in graphics objects are entirely ignored during GOCA rendering, falling back to standard legacy OCA colors (such as default black).

### 1.2 Lack of Precedence Reset between GSPCOL and GSCOL/GSECOL (Bug)
*   **Affected Files:**
    *   `src/main/java/com/mgz/pdf/PdfHandler.java`
*   **Detailed Findings:**
    *   According to GOCA architecture, when a legacy color command (`GSCOL_SetColor` or `GSECOL_SetExtendedColor`) is executed, any previously active process color state (set by `GSPCOL`) must be cleared/reset to ensure legacy colors take precedence.
    *   Currently, in `handleDrawingOrderInternal()` (lines 678–681), processing `GSCOL` or `GSECOL` simply updates the legacy color field via `graphicsState.setColor()`, but fails to clear/reset the active `processColorSpace` and `processColorValue` fields in `graphicsState`.
    *   **Impact:** If process color support is implemented in `applyGraphicsState()`, GSPCOL would permanently override subsequent GSCOL/GSECOL drawing orders within the same graphics segment, leading to color state leakage.

### 1.3 Linear and Radial Gradients Lack Color Validation (Bug/Fragility)
*   **Affected Files:**
    *   `src/main/java/com/mgz/pdf/PdfHandler.java`
*   **Detailed Findings:**
    *   In `PdfHandler.applyGradient()` (lines 1413–1430), linear and radial gradients (`GLGD` and `GRGD`) retrieve start/end colors using `ColorHandler.getExtendedColor()`.
    *   `ColorHandler.getExtendedColor` can return `null` if the specified color space is unsupported or the value array is malformed.
    *   The gradient renderer does not perform any null/validity check on these colors (`c1` and `c2`) before accessing their color space or color value via `c1.getColorSpace()` and `c1.getColorValue()`.
    *   **Impact:** Enclosing gradients with unsupported color spaces (e.g., Highlight, YCrCb) or invalid byte arrays will trigger immediate `NullPointerException` (NPE) and crash the PDF generation process.

---

## 2. IOCA (Image Object Content Architecture) Gaps & Bugs

### 2.1 No Colorization or Stencil Masking for Bi-level Images (Gap)
*   **Affected Files:**
    *   `src/main/java/com/mgz/pdf/PdfImageRenderer.java`
    *   `src/main/java/com/mgz/pdf/PdfHandler.java`
*   **Detailed Findings:**
    *   In IOCA, bi-level (1-bit) images often act as stencil masks. When stencil masking is enabled, 1-bits of the image should be painted with the active GOCA or structured field foreground color, while 0-bits are treated as transparent (or painted with a background color).
    *   In `PdfImageRenderer.renderSimple()`/`renderTiled()` and `PdfHandler.renderGocaImage()`, the code simply generates standard 1-bit grayscale images using:
        ```java
        com.itextpdf.io.image.ImageDataFactory.create(width, height, 1, 1, data, null);
        ```
    *   The current image renderer does not query the active graphics color state or set any image stencil/mask properties on the resulting `PdfImageXObject`.
    *   **Impact:** Bi-level images and graphics stamps are always rendered as raw black-and-white images. They cannot be colorized (tinted) with custom colors, violating high-fidelity AFP-to-PDF representation.

### 2.2 Lack of Color Management Resource (CMR) and Color Attribute Table (CAT) Integration (Gap)
*   **Affected Files:**
    *   `src/main/java/com/mgz/pdf/ColorHandler.java`
    *   `src/main/java/com/mgz/afp/cmoca/`
*   **Detailed Findings:**
    *   The repository parses and validates CMOCA (Color Management Object Content Architecture) resources (e.g. `CMR_ColorManagementResource`, `CAT_ColorAttributeTable`).
    *   However, during PDF rendering, there is absolutely zero integration with these resources. Custom color mapping tables, ICC profiles within Color Conversion (CC) or Link (LK/DL) CMRs, and indexed palettes are completely bypassed.
    *   **Impact:** Color-managed environments that rely on ICC profiles or custom palettes to map IOCA/GOCA colors will suffer from inaccurate color rendering in the generated PDF.

---

## 3. IM Image Object Content Architecture Gaps

### 3.1 IM Images Completely Omitted in Rendering (Functional Gap)
*   **Affected Files:**
    *   `src/main/java/com/mgz/pdf/PdfHandler.java`
*   **Detailed Findings:**
    *   IM Image structured fields (such as `BII_BeginIMImageObject`, `EII_EndIMImageObject`, `IRD_IMImageRasterData`, `IID_IMImageInputDescriptor`, and `ICP_IMImageCellPosition`) are parsed and serialized to XML, but `PdfHandler.java` does not process them at all.
    *   There are no hooks in the PDF handler to initialize, collect, position, or render legacy IM images.
    *   **Impact:** Documents containing legacy IM images will have completely blank areas in the PDF output, losing all associated visual content, placement, and bi-level colorization.

---

## 4. General Color Space & Fallback Gaps & Bugs

### 4.1 Unsupported Highlight Color Space (Gap)
*   **Affected Files:**
    *   `src/main/java/com/mgz/pdf/ColorHandler.java`
*   **Detailed Findings:**
    *   The standard AFP `Highlight` color space (code `0x06`) is parsed via the `AFPColorSpace` enum but is missing from `ColorHandler.getExtendedColor()`.
    *   When encountered, `getExtendedColor()` falls through to the `default` case and returns `null`.
    *   **Impact:** Highlight colors (which are common in transactional documents for emphasis) are completely unsupported and fail to render.

### 4.2 Unsupported YCrCb / YCbCr Color Spaces (Gap)
*   **Affected Files:**
    *   `src/main/java/com/mgz/pdf/ColorHandler.java`
*   **Detailed Findings:**
    *   The color spaces `YCrCb` (code `0x02`) and `YCbCr` (code `0x12`) are often utilized in image blocks (particularly JPEG data).
    *   These color spaces are missing case handling in `ColorHandler.getExtendedColor()`, falling back to `null`.
    *   **Impact:** Failure to decode or correctly map colors for images utilizing these native color spaces.

### 4.3 Device-Default Colors Arbitrarily Hardcoded to RGB Black (Bug)
*   **Affected Files:**
    *   `src/main/java/com/mgz/afp/enums/AFPColorValue.java`
*   **Detailed Findings:**
    *   The default color values `0x0000`/`0xFF00` (DeviceDefault) and `0x00FF`/`0xFFFF` (Default) are defined with RGB `0, 0, 0` (Black) in the `AFPColorValue` enum.
    *   In standard AFP presentation, default colors should resolve dynamically to the default foreground color of the presentation device or respect environmental configurations.
    *   **Impact:** Hardcoding default values to black prevents supporting colored media defaults (e.g., printing white text or background default configurations), which can result in contrast collisions or invisible content on dark backgrounds.

---

## 5. Summary & Remediation Roadmap

To achieve absolute parity and high-fidelity output during PDF generation, the following remediation steps are recommended:

1.  **Remediate `applyGraphicsState()`**:
    *   Update GOCA drawing state application in `PdfHandler.java` to check for active process colors:
        ```java
        Color color;
        if (graphicsState.getProcessColorSpace() != null) {
            color = ColorHandler.getExtendedColor(graphicsState.getProcessColorSpace(), graphicsState.getProcessColorValue());
        } else {
            color = ColorHandler.getColor(graphicsState.getColor());
        }
        if (color != null) {
            currentCanvas.setStrokeColor(color);
            currentCanvas.setFillColor(color);
        }
        ```
2.  **Enforce GSPCOL Precedence Reset**:
    *   Update `GSCOL` and `GSECOL` handler branches in `PdfHandler.java` to set the process color space to `null`.
3.  **Null-safe Gradient Rendering**:
    *   Add null checks in `applyGradient()` to ensure `c1` and `c2` are valid before rendering shading, falling back gracefully to `graphicsState.getColor()` if they are null.
4.  **Implement Bi-level Image Colorization**:
    *   Update `PdfImageRenderer.java` and GOCA image rendering to utilize iText stencil masks / raw image rendering options when painting 1-bit mask data with current colors.
5.  **Support IM Image Objects**:
    *   Add hooks to `PdfHandler.java` for `BII`/`EII`/`IRD` to render legacy IM raster objects.
