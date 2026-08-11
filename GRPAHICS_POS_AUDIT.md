# GOCA Graphics Position and Scaling Audit

This document provides a comprehensive audit of the position handling, scaling, and start-position tracking for GOCA (Graphics Object Content Architecture) vector graphics in the Alpheus AFP to PDF conversion engine. It compares the current codebase implementation with the official IBM MO:DCA and GOCA specifications to highlight structural alignments, quality of implementation, and critical architecture gaps.

---

## 1. Architectural Overview & Coordinate Spaces

To accurately map vector graphics from AFP to PDF, the engine must navigate three distinct coordinate spaces defined in the MO:DCA and GOCA specifications:

1. **Page Presentation Space (PPS) / Overlay Space**:
   - The master canvas coordinates (Xp, Yp), typically defined in 1/1440-inch units (logical coordinate system) by the Page Descriptor (`PGD`) or Presentation Text Descriptor (`PTD`).

2. **Object Area Presentation Space (OAPS)**:
   - The bounded region on the page where the graphics object resides, defined by the Object Area Position (`OBP`) and Object Area Descriptor (`OBD`) structured fields. The OBP specifies the origin and orientation of this space relative to the PPS.

3. **Graphics Presentation Space (GPS)**:
   - The internal coordinate system of the GOCA object, defined by the Graphics Data Descriptor (`GDD`).
   - GDD specifies the **GPS Window Specification** (Left, Right, Bottom, Top boundaries) and the coordinate resolution (unit base and units per unit base).
   - Graphic drawing orders specify coordinates within this GPS space.

### Expected Coordinate Mapping Pipeline (Spec):
$$\text{GPS Coordinates} \xrightarrow{\text{GDD Window \& Resolution}} \text{OAPS (Object Space)} \xrightarrow{\text{OBP Position \& Orientation}} \text{PPS (Page Space)} \xrightarrow{\text{AfpToPdfTransform}} \text{PDF Device Space}$$

---

## 2. Key Functions and Classes

The positioning, transformation, and drawing mechanics are distributed across several core classes in the codebase:

### A. Coordinate Transformation & Canvas Initialization
* **`CoordinateTransformer.getAfpToPdfTransform(float pageHeightPoints, double scaleX, double scaleY)`**:
  - Generates the standard affine transformation matrix converting page-level AFP coordinates (top-down, origin top-left) to PDF coordinates (bottom-up, origin bottom-left).
  - **Implementation**: `new AffineTransform(scaleX, 0, 0, -scaleY, 0, pageHeightPoints)`.
* **`PdfHandler.ensureCanvasTransformed()`**:
  - Verifies if the active page canvas has been transformed. If not, it lazily triggers `applyTransformation()` using the page dimensions and scaling factors.
* **`PdfHandler.applyTransformation(float heightPoints, double scaleX, double scaleY)`**:
  - Concatenates the AFP-to-PDF `AffineTransform` matrix directly onto the active `PdfCanvas`, establishing the global coordinate system for subsequent drawing commands.

### B. Graphics State Tracking
* **`PdfGraphicsState`**:
  - Tracks the current drawing context inside a GOCA object, including line attributes, colors, area flags, and arc transformations.
  - **`currentX` and `currentY`**: Stores the active graphics cursor coordinates (in integer GPS/AFP units).
  - **`arcTransformP`, `arcTransformQ`, `arcTransformR`, `arcTransformS`**: Stores the GOCA arc transformation matrix parameters $[P\ R;\ S\ Q]$ used to map circle drawing commands to arbitrary ellipses.

### C. Drawing Order Routing
* **`PdfHandler.handleDrawingOrder(GAD_DrawingOrder)`**:
  - Dispatches GOCA drawing orders. Wraps execution in performance monitors.
* **`PdfHandler.handleDrawingOrderInternal(GAD_DrawingOrder)`**:
  - The core router that maps GOCA drawing orders (e.g., `GSCP`, `GLINE`, `GBOX`, `GFARC`, `GBIMG`) to PDF operations and graphics state updates.

---

## 3. Drawing Order Position Tracking: Given Position vs. Current Position

GOCA drawing orders are strictly divided into operations that specify an explicit **Given Position** (where drawing starts) versus those drawing from the **Current Position** (inheriting the active cursor position from `PdfGraphicsState`).

The table below documents how the engine maps these orders and maintains the graphics cursor:

| Drawing Order Mnemonic | Type | Start Position Handling | Cursor/Position Update Post-Draw | Implementation Quality & Completeness |
| :--- | :--- | :--- | :--- | :--- |
| **`GSCP`** (Set Current Position) | State | Sets cursor directly to $(X, Y)$ | `graphicsState.setCurrentX/Y(x, y)` | **Complete**: Successfully updates state coordinates. |
| **`GLINE`** (Line Given) | Given | Starts line at first point in array | Updates cursor to the last point of the line | **Complete**: Loops through coordinates, handles start point, and advances cursor. |
| **`GCLINE`** (Line Current) | Current | Starts line at `graphicsState.getCurrentX/Y()` | Updates cursor to the last point of the line | **Complete**: Correctly performs `moveTo(currentX, currentY)` before iterating line segments. |
| **`GRLINE`** (Relative Line Given) | Given | Starts line at given start point | Updates cursor to the final accumulated point | **Complete**: Correctly accumulates relative offsets from start point. |
| **`GCRLINE`** (Relative Line Current)| Current | Starts line at `graphicsState.getCurrentX/Y()` | Updates cursor to the final accumulated point | **Complete**: Accumulates offsets beginning at current graphics state coordinates. |
| **`GBOX`** (Box Given) | Given | Evaluates bounding box from given corners | Updates cursor to the diagonal corner point | **Complete**: Positions box correctly and advances cursor to diagonal. |
| **`GCBOX`** (Box Current) | Current | Starts at `graphicsState` coordinates to diagonal | Updates cursor to the diagonal corner point | **Complete**: Maps starting coordinate to active graphics state coordinates. |
| **`GFARC`** (Full Arc Given) | Given | Center at given coordinate $(X_c, Y_c)$ | Does **NOT** update graphics cursor | **Gap**: According to the GOCA spec, Full Arc Given should update the cursor to the center point. |
| **`GCFARC`** (Full Arc Current) | Current | Center at `graphicsState.getCurrentX/Y()` | Does **NOT** update graphics cursor | **Gap**: GOCA spec states this should update/maintain the current position at the center point. |
| **`GPARC`** (Partial Arc Given) | Given | Connects start point $(X_s, Y_s)$ to arc start | Updates cursor to the arc end-point | **Complete**: Calculates end position using trig and updates cursor. |
| **`GCPARC`** (Partial Arc Current) | Current | Connects cursor to arc start | Updates cursor to the arc end-point | **Complete**: Leverages current cursor coordinate as start point. |
| **`GFLT`** (Fillet Given) | Given | Sequence of quadratic Beziers from first point | Updates cursor to the last point of fillet | **Complete**: Decodes control points and correctly updates cursor. |
| **`GCFLT`** (Fillet Current) | Current | Starts quadratic Beziers from cursor | Updates cursor to the last point of fillet | **Complete**: Begins curve path at active graphics state cursor. |
| **`GCBEZ`** (Cubic Bezier Given) | Given | Starts cubic Beziers at first coordinate | Updates cursor to the last coordinate | **Complete**: Correctly performs `moveTo` to start coordinate and advances cursor. |
| **`GCCBEZ`** (Cubic Bezier Current) | Current | Starts cubic Beziers at cursor | Updates cursor to the last coordinate | **Complete**: Moves to current coordinates and advances cursor. |
| **`GBIMG`** (Begin Image Given) | Given | Image begins at given origin point | Sets cursor to the image origin point | **Complete**: Forces current position to given origin before loading image. |
| **`GCBIMG`** (Begin Image Current) | Current | Image begins at cursor | Keeps cursor at current coordinates | **Complete**: Commences image layout at current position. |
| **`GCHST`** (Char String Given) | Given | Positioned at given origin $(X_o, Y_o)$ | Updates cursor based on string width & direction | **Complete**: Invokes text layout and correctly advances current coordinates. |
| **`GCCHST`** (Char String Current)| Current | Positioned at `graphicsState.getCurrentX/Y()` | Updates cursor based on string width & direction | **Complete**: Invokes text layout and correctly advances current coordinates. |

---

## 4. Evaluation of Specification Compliance & Quality Gaps

While the GOCA drawing orders are largely complete and functional for individual shapes, several severe architectural gaps exist when evaluated against GOCA and MO:DCA coordinate mapping specifications:

### Gap 1: OBP (Object Area Position) is Ignored for GOCA Objects
* **The Specification**:
  - The `OBP` structured field determines the offset (origin) and rotation of the Object Area Presentation Space relative to the page.
* **The Codebase**:
  - In `PdfHandler.java`, `OBP` coordinates are parsed and applied to **Image Objects** (`BIM`/`EIM`) and **Barcode Objects** (`BBC`/`EBC`).
  - However, `OBP` is **completely ignored for Graphics Objects** (`BGR`/`EGR`).
* **Impact**:
  - Any GOCA vector graphics object that is positioned on a page with a non-zero origin offset in its `OBP` will render incorrectly at `(0, 0)` of the page rather than at its designated object coordinate origin.

### Gap 2: GDD (Graphics Data Descriptor) is Ignored
* **The Specification**:
  - `GDD` specifies the **Window Specification** (extent of GPS) and coordinate resolution (unit base, units per unit base) for translating internal coordinates to the object area.
* **The Codebase**:
  - GDD structured fields are parsed during decode (`GDD_GraphicsDataDescriptor.java`) but are **never processed** within `PdfHandler.java`.
* **Impact**:
  - GOCA drawing order coordinates are rendered directly onto the page using the global `defaultScaleX`/`defaultScaleY` from the page descriptor (`PTD` or `PGD`).
  - If a GOCA object defines a coordinate resolution different from the page descriptor, or uses a custom GPS Window (e.g., coordinates from $-32768$ to $32767$ mapping to a small box), the graphics will be scaled wildly out of bounds or collapsed.
  - GOCA's default left-handed or right-handed coordinate mapping specified via the window edges is completely bypassed, which can lead to vertical or horizontal mirroring of vectors.

### Gap 3: GOCA Image Alignment Hacks
* **The Codebase**:
  - In GOCA Image rendering (`renderGocaImage()`):
    ```java
    // Position at (x, y-height) because PDF is bottom-up and GOCA Image is top-down from origin
    currentCanvas.concatMatrix(gocaImageWidth, 0, 0, gocaImageHeight, gocaImageX,
        gocaImageY - gocaImageHeight);
    ```
  - This utilizes a hardcoded offset `gocaImageY - gocaImageHeight` to align the top-down GOCA image in PDF space.
* **Impact**:
  - This assumes that GOCA space coordinates are isomorphic and aligned with PDF coordinate axes. Under complex page rotations, scales, or GDD window settings, this hardcoded alignment calculation fails.

### Gap 4: Segment Transformations & Characteristics Ignored
* **The Specification**:
  - GOCA segments can possess their own transformation characteristics (e.g., translation, scaling, or rotation) defined via segment properties.
* **The Codebase**:
  - `GSGCH_SegmentCharacteristics` is decoded, but segment-level transformations are ignored. All segments are rendered directly onto the primary canvas matrix.

---

## 5. Summary Recommendations

To achieve full GOCA positioning and scaling compliance with the AFP specification, the following enhancements should be introduced:

1. **Incorporate GDD Scaling**:
   - Parse `GDD_GraphicsDataDescriptor` inside `PdfHandler.java`.
   - Calculate custom scale factors for the GOCA object:
     $$\text{scaleX}_{\text{GOCA}} = \frac{\text{OAPS Width}}{\text{GPS Window Width}}$$
     $$\text{scaleY}_{\text{GOCA}} = \frac{\text{OAPS Height}}{\text{GPS Window Height}}$$
   - Concatenate these scale factors onto the canvas matrix when entering a graphics object (`BGR`).

2. **Respect OBP Origins**:
   - Extract the `xOrigin` and `yOrigin` from `OBP` for graphics objects and translate the graphics coordinate system accordingly before executing drawing orders.

3. **Align GOCA Image Scaling**:
   - Replace the `gocaImageY - gocaImageHeight` alignment hack with coordinate transformations derived directly from GDD's window mapping and OBP metrics.
