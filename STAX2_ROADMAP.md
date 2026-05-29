# StAX2 Modernization Roadmap

This roadmap outlines the phased implementation of advanced StAX2 features to optimize XML generation performance and memory efficiency, as identified in `STAX2_GAP.md`.

## Status Summary (June 2026)
- **Typed Access API**: ✅ Completed
- **Optimized Sanitization**: ✅ Completed
- **Native Binary Encoding**: ⏳ Pending
- **ByteBuffer Integration**: 🚧 Work-in-Progress (Aligned with `AGGRESSIVE_IO_ROADMAP.md`)

---

## Phase 1: Foundation & Primitive Fast-Paths ✅
Eliminate `String` allocation overhead for high-frequency numeric data.

- ✅ **Typed Access API (Fast-Path for Numbers)**:
  - ✅ Implement `writeInt()` and `writeLong()` overloads in `AfpJacksonXmlWriter`.
  - ✅ Transition all numeric fields to use Typed Access API.
- ✅ **Optimized Sanitization Decorator**:
  - ✅ Refactor `SanitizingXMLStreamWriter` to use `UtilCharacterEncoding.needsXmlSanitization`.
  - ✅ Avoid redundant `String` and `char[]` allocations when input is already XML-valid.

---

## Phase 2: Native Binary & Bulk Data Handling ⏳
Optimize the serialization of raw byte arrays by leveraging StAX2 binary streaming.

- ⏳ **Native Binary Encoding**:
  - ⏳ Transition `SEC_SetExtendedTextColor.colorValue` to `writeBinary()`.
  - ⏳ Transition `NOP_NoOperation.ignoredData` to `writeBinary()`.
  - ⏳ Transition `RPS_RepeatString.repeatData` to `writeBinary()`.
- ⏳ **Parity Verification**: Ensure `writeBinary()` output (typically Base64) is acceptable or configured for Hex parity where required.
- ⏳ **Hex-to-Binary Refactoring**: Replace manual `bytesToHexString` conversions with StAX-managed binary streaming.

---

## Phase 3: Zero-Copy & ByteBuffer Integration 🚧
Align XML generation with the project's aggressive I/O goals by bypassing `OutputStream` overhead.

- ❌ **6.1. ByteBuffer-backed OutputStream**: (Removed) `DirectBufferOutputStream` was removed.
- ⏳ **6.2. Async StAX Writer Research**: Evaluate Aalto's `AsyncXMLStreamWriter` for non-blocking `ByteBuffer` output.
- ⏳ **6.3. Zero-Copy Writer Implementation**: Prototype a version of `AfpJacksonXmlWriter` that writes directly into memory-mapped regions.

---

## Phase 4: Async Parsing & Reactive Integration ⏳
Prepare the pipeline for non-blocking environments.

- ⏳ **Async Parsing (Aalto Integration)**: Research usage of `AsyncXMLStreamReader` for the AFP-to-XML pipeline.
- ⏳ **Reactive Stream Integration**: Explore integration with Vert.x or Project Reactor for high-scale microservices.

---

## Strategic Goals
1. **Zero-Allocation Primitives**: Ensure no `String.valueOf()` calls remain in high-frequency paths.
2. **Memory Pressure Reduction**: Offload binary-to-text conversion to the StAX provider's internal buffers.
3. **I/O Alignment**: Maintain strict parity with `AGGRESSIVE_IO_ROADMAP.md` for zero-copy data transfer.
