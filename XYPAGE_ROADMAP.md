# Roadmap: Implementation of spatial metadata in AFP XML

## Phase 1: Basic infrastructure
- [x] Add state variables to `AfpJacksonXmlWriter`: `currentPageNumber`, `inlinePos`, `baselinePos`, `inlineOri`, `baselineOri`, `inlineMargin`, `baselineIncrement`.
- [x] Implement `page` tracking: increment `currentPageNumber` on `BPG` structured fields.
- [x] Implement state reset logic on `BPG`, `BMO`, and `BPS` (reset positions and orientations to default).

## Phase 2: PTOCA state machine
- [x] Implement move sequence handling in `writeControlSequence`:
  - `AMI`, `RMI` (Inline moves).
  - `AMB`, `RMB` (Baseline moves).
- [x] Implement orientation and margin handling:
  - `STO` (Text orientation).
  - `SIM` (Inline margin).
  - `SBI` (Baseline increment).
  - `BLN` (Begin line transition: `inlinePos = inlineMargin`, `baselinePos += baselineIncrement`).

## Phase 3: Coordinate integration
- [x] Integrate `CoordinateTransformer` into `AfpJacksonXmlWriter`.
- [x] Calculate absolute `afpX` and `afpY` before writing `PTX` or drawable control sequences.

## Phase 4: XML attribute injection
- [x] Modify `AfpXmlStreamWriter` or use `XMLStreamWriter2` attributes to inject `x`, `y`, and `page` into:
  - `<PTX_PresentationTextData>`
  - `<TRN_TransparentData>`
  - `<GraphicCharacters>`
- [x] Update manual StAX fast-paths in `XmlTemplateRegistry` templates (e.g., `AMI`, `AMB`, `RMI`, `RMB`, `STO`) and `AfpJacksonXmlWriter` to support dynamic attributes without redundant allocations.

## Phase 5: Verification and testing
- [x] Create `PTXAttributesTest.java` to verify coordinate calculations against known AFP samples.
- [x] Update `SFFastPathVerificationTest.java` to ensure fast-path XML matches Jackson's output with attributes.
- [x] Verify that `page` attributes correctly increment across multiple `BPG` fields.
