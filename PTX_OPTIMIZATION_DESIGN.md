# PTX Optimization Design

This document details the technical implementation of performance optimizations for the `PTX_PresentationTextData` (PTX) structured field and PTOCA control sequences.

---

## 1. Manual StAX Fast-Paths

To bypass Jackson's reflective serialization overhead, manual StAX-based writing is implemented in `AfpJacksonXmlWriter` for high-frequency PTOCA control sequences.

### Implementation:
- **Detection**: In `writeFieldDirectly`, if the structured field is a `PTX_PresentationTextData`, `writePtxDirectly` is invoked.
- **PTOCA Dispatch**: `writeControlSequence` uses `instanceof` patterns to identify specific control sequences.
- **Supported Fast-Paths**:
    - `TRN_TransparentData`
    - `GraphicCharacters`
    - `AMI_AbsoluteMoveInline`
    - `AMB_AbsoluteMoveBaseline`
    - `SCFL_SetCodedFontLocal`
    - `STO_SetTextOrientation`
    - `SIA_SetIntercharacterAdjustment` (added in optimization phase)
    - `SVI_SetVariableSpaceCharacterIncrement` (added in optimization phase)
    - `SEC_SetExtendedTextColor` (added in optimization phase)
    - `DIR_DrawIaxisRule` (added in optimization phase)
    - `DBR_DrawBaxisRule` (added in optimization phase)
    - `NOP_NoOperation` (added in optimization phase)
    - `RPS_RepeatString` (added in optimization phase)

---

## 2. Selective Instrumentation

The `MnemonicXMLStreamWriter` decorator adds overhead for every element. For PTOCA, which can contain thousands of small elements, this cumulative overhead is significant.

### Implementation:
- `AfpJacksonXmlWriter` stores the underlying `XMLStreamWriter2` as `baseXsw`.
- When writing PTOCA sub-elements or indentation, `baseXsw` is used directly, bypassing `MnemonicXMLStreamWriter.writeStartElement` and its associated string parsing and thread-local lookups.
- Top-level `PTX` and individual `PTOCA` timings are still captured by the decorator for high-level telemetry.

---

## 3. Fragment Wrapping for Parallel conversion

StAX writers often enforce a single root element. In parallel conversion, each thread writes a "fragment" of the document.

### Implementation:
- **Wrapping**: `AfpJacksonXmlWriter` and `AfpStreamingXmlWriter` wrap fragments in a temporary `<AfpFragments>` element when `fragmentMode` is enabled.
- **Stripping**: `ParallelAfpConverter.PageConversionTask` strips the `<AfpFragments>` tags from the resulting `byte[]` buffer before sending it to the `OrderedResultCollector`.
- **Logic**: A high-performance byte-search algorithm identifies the start and end tags within the buffer, minimizing copy operations.

---

## 4. Enhanced Telemetry (Advanced Audit)

To support production debugging, `PTXPerformanceMonitor` provides granular insights.

### Metrics:
- **Max Write Time**: Tracks the single longest duration for each PTOCA function.
- **Slowest Payload capture**: When `--ptx-debug` is enabled, the actual hex payload of the slowest instance is captured.
- **Payload Correlation**: Reports the correlation between payload size and write time.

---

## 5. Round-Trip Buffer Optimization

Refactored `PTX_PresentationTextData.writeAFP` to reduce memory pressure during AFP generation.

### Implementation:
- Replaced multiple nested `ByteArrayOutputStream` instances with a single shared buffer for the entire PTX payload.
- Optimized debug path to capture payloads without triggering $O(N^2)$ memory copying.
