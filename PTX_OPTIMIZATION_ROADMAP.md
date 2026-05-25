# PTX Optimization Roadmap

This document tracks the implementation status of performance optimizations for the `PTX_PresentationTextData` (PTX) structured field and PTOCA control sequences.

## Status Summary (May 2026)
- **Bottleneck Analysis:** ✅ Completed (Audit 3)
- **Regression Monitoring:** ✅ Completed (Audit 4 - 2108 PTX / 60877 CS)
- **High-Frequency Fast-Paths:** ✅ Completed
- **Instrumentation Optimization:** ✅ Completed
- **Parallel conversion Fix:** ✅ Completed
- **Advanced Telemetry:** ✅ Completed

---

## Phase 1: High-Frequency PTOCA Fast-Paths ✅
Bypass Jackson reflective serialization for the most common control sequences.

- ✅ Implement `SEC_SetExtendedTextColor` fast-path.
- ✅ Implement `SVI_SetVariableSpaceCharacterIncrement` fast-path.
- ✅ Implement `DIR_DrawIaxisRule` / `DBR_DrawBaxisRule` fast-paths.
- ✅ Implement `NOP_NoOperation` fast-path.
- ✅ Implement `RPS_RepeatString` fast-path.
- ✅ Implement `SIA_SetIntercharacterAdjustment` fast-path.

---

## Phase 2: Instrumentation & Telemetry ✅
Reduce the cost of monitoring and improve diagnostic quality.

- ✅ **Selective Instrumentation**: Bypass `MnemonicXMLStreamWriter` for PTOCA sub-elements.
- ✅ **Telemetry Guarding**: Ensure all performance monitor calls are wrapped in `isEnabled()` checks.
- ✅ **Slowest Instance Tracking**: Capture and report the hex payload of the slowest PTOCA instances.
- ✅ **Max Time Metrics**: Track maximum write times per PTOCA function.

---

## Phase 3: Architecture & Buffer Optimization ✅
Improve data handling for both sequential and parallel conversion.

- ✅ **Fragment Wrapping**: Use `<AfpFragments>` to allow multi-root parallel serialization in StAX.
- ✅ **Wrapper Stripping**: Efficiently remove fragment tags in `ParallelAfpConverter`.
- ✅ **Buffer Reuse**: Refactor `PTX_PresentationTextData.writeAFP` to use a single shared buffer.
- ✅ **$O(N^2)$ elimination**: Optimized debug-path payload capture to avoid redundant buffer copies.

---

## Phase 4: Future Work ⏳
Long-term performance goals.

- ⏳ **SIMD XML Generation**: Explore `simdxml` for high-speed PTOCA serialization.
- ⏳ **Custom TRN Encoder**: Implement a zero-allocation EBCDIC-to-UTF8 encoder for large `TransparentData` blocks.
- ⏳ **Vectorized Indentation**: Use pre-filled buffers for XML indentation to avoid redundant string creation.
