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

---

## 6. Performance Benchmarks (Audit 4)

Latest measurements from high-volume regression suite (May 2026).

### PTX Debug Performance Summary

| Metric | Value |
| :--- | ---: |
| Total PTX Fields | 2108 |
| Total Parse Time | 225 ms |
| Total Write Time | 6547 ms |
| Total Payload Size | 400667 bytes |
| Total Ctrl Sequences | 60877 |
| Avg CS per PTX | 28.88 |
| Avg Payload per PTX | 190.07 bytes |

### PTOCA Function Breakdown

| Function | Count | Parse (ms) | Write (ms) | Max Write(ms) | Total Payload | Avg Payload |
| :--- | ---: | ---: | ---: | ---: | ---: | ---: |
| AMB_AbsoluteMoveBaseline | 15443 | 6 | 0 | 0 | 30886 | 2.00 |
| AMI_AbsoluteMoveInline | 15443 | 6 | 0 | 0 | 30886 | 2.00 |
| DBR_DrawBaxisRule | 204 | 0 | 0 | 0 | 1020 | 5.00 |
| DIR_DrawIaxisRule | 2069 | 1 | 0 | 0 | 10345 | 5.00 |
| NOP_NoOperation | 2108 | 2 | 0 | 0 | 0 | 0.00 |
| SCFL_SetCodedFontLocal | 5289 | 4 | 0 | 0 | 5289 | 1.00 |
| SEC_SetExtendedTextColor | 2715 | 4 | 0 | 0 | 35345 | 13.02 |
| SIA_SetIntercharacterAdjustment | 300 | 0 | 0 | 0 | 610 | 2.03 |
| STO_SetTextOrientation | 1053 | 3 | 0 | 0 | 4212 | 4.00 |
| SVI_SetVariableSpaceCharacterIncrement | 3083 | 1 | 0 | 0 | 6166 | 2.00 |
| TRN_TransparentData | 13170 | 29 | 0 | 0 | 149938 | 11.38 |

---

## 7. TRN_TransparentData Processing Analysis

`TRN_TransparentData` is a high-frequency control sequence that requires careful handling due to its payload nature (text that may contain invalid XML characters).

### Decoding Strategy
- **EBCDIC to UTF-16**: Uses `UtilCharacterEncoding.decodeEbcdic` to convert raw PTOCA bytes into Java Strings based on the current active coded font.
- **Payload Preservation**: Optionally stores raw EBCDIC bytes (`transparentDataEBCDIC`) if the `isUseEBCDICData` flag is enabled, allowing bit-faithful round-trips bypassing Unicode mapping issues.

### XML Serialization (Fast-Path)
In `AfpJacksonXmlWriter`, a manual fast-path is used to serialize `TRN` to avoid Jackson overhead:
1. **Raw String Output**: The `transparentData` field is written. Note that this might contain characters that are invalid in XML 1.0 (e.g., control characters).
2. **Sanitized Text Output**: The `text` element contains the result of `UtilCharacterEncoding.sanitizeForXml(transparentData)`.
    - **Sanitization Logic**: Replaces any code point not matching the XML 1.0 production `[2] Char` (e.g., `0x00`-`0x08`, `0x0B`-`0x0C`, `0x0E`-`0x1F`) with a standard space (`0x20`).
3. **Selective Instrumentation**: Uses `baseXsw` (direct StAX writer) for internal TRN elements to bypass `MnemonicXMLStreamWriter` per-element overhead, which is critical given the 13,000+ TRN instances in the benchmark.
