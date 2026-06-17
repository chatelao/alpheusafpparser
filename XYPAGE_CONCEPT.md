# Concept: Spatial and structural context in AFP XML ✅

## Goal ✅
To enrich the XML representation of AFP documents with spatial (X, Y coordinates) and structural (Page number) metadata. This allows downstream tools to process the text content with knowledge of its physical location on the page without needing a full AFP rendering engine.

## Metadata attributes ✅
The following attributes will be added to specific XML tags:
- `page`: The 1-based index of the current page (triggered by `BPG`).
- `x`: The absolute horizontal coordinate in AFP units (1/1440 inch by default).
- `y`: The absolute vertical coordinate in AFP units.

## Impacted tags
### Text and Data tags
- `<PTX_PresentationTextData>`: ✅ Attributes reflect the starting position of the text field.
- `<TRN_TransparentData>`: ✅ Attributes reflect the position of the specific text segment within a PTX.
- `<GraphicCharacters>`: ✅ Similar to TRN, reflecting the position before the characters are drawn.

### Structural fields
- `BPG_BeginPage`: ✅
- `EPG_EndPage`: ✅
- `TLE_TagLogicalElement`: ✅
- `IOB_IncludeObject`: ✅
- `BNG`, `ENG`, `IPS`, `IPO`, `BGR`, `EGR`, `BIM`, `EIM`, `BBC`, `EBC`, `BOC`, `EOC`, `IPG`: ⏳

## State tracking ✅
To calculate these values, the `AfpJacksonXmlWriter` must maintain a PTOCA state machine:

### 1. Structural state
- `currentPageNumber`: ✅ Incremented upon encountering a `BPG_BeginPage` structured field. Reset or managed at the start of the document.

### 2. PTOCA coordinate state (I, B system)
- `inlinePos`: ✅ The current position along the inline direction.
- `baselinePos`: ✅ The current position along the baseline direction.
- `inlineMargin`: ✅ The left margin for the inline direction (updated by `SIM`).
- `baselineIncrement`: ✅ The amount to move the baseline for a new line (updated by `SBI`).

### 3. Orientation state
- `inlineOri`: ✅ The orientation of the inline axis (default 0°).
- `baselineOri`: ✅ The orientation of the baseline axis (default 90°).

## Coordinate transformation ✅
Relative PTOCA (I, B) coordinates are converted to absolute AFP (X, Y) coordinates using the following formulas (implemented in `CoordinateTransformer`):

- `afpX = round(inlinePos * cos(iRad) + baselinePos * cos(bRad))`
- `afpY = round(inlinePos * sin(iRad) + baselinePos * sin(bRad))`

*Note: Radians are derived from AFP orientation codes (e.g., 0, 0x2D00, 0x5A00, 0x8700) divided by 128.0.*

## State lifecycle ✅
- **Reset**: ✅ The PTOCA state (positions and orientations) is reset to 0 upon encountering `BPG` (Page), `BMO` (Overlay), or `BPS` (Page Segment).
- **Updates**:
  - `AMI` (Absolute Move Inline): ✅ Sets `inlinePos`.
  - `RMI` (Relative Move Inline): ✅ Increments `inlinePos`.
  - `AMB` (Absolute Move Baseline): ✅ Sets `baselinePos`.
  - `RMB` (Relative Move Baseline): ✅ Increments `baselinePos`.
  - `STO` (Set Text Orientation): ✅ Updates `inlineOri` and `baselineOri`.
  - `SIM` (Set Inline Margin): ✅ Sets `inlineMargin`.
  - `SBI` (Set Baseline Increment): ✅ Sets `baselineIncrement`.
  - `BLN` (Begin Line): ✅ Sets `inlinePos = inlineMargin` and `baselinePos += baselineIncrement`.

## Limitations
- **Text Width**: The parser does not currently load font metrics. Therefore, it cannot calculate the cursor advancement after drawing text (`TRN` or `GC`). The `x` and `y` attributes on these tags reflect the position *before* the text is rendered.
- **Accuracy**: Absolute coordinates are accurate only if the text is preceded by explicit move commands within the `PTX` or at the start of the presentation space.
