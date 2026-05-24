# Analysis of Performance Slow-downs in Alpheus AFP Parser

This document analyzes the performance regressions and slow-downs documented in `PERFORMANCE_CONCEPT.md`. Verification via `PerformanceRegressionTest` and source code analysis identifies two primary causes for these measurements.

## Moment 1: Initial JAXB-to-Jackson Transition (Reflective Fallback)

When the project transitioned from JAXB to Jackson-based streaming, significant speedups (up to 125x) were achieved for "high-frequency" fields. However, complex fields showed a performance regression (Speedup < 1.0x) compared to the original JAXB writer.

### The Cause: Lack of Manual StAX Fast-Paths
The `AfpJacksonXmlWriter` implements high-performance StAX "fast-paths" for high-frequency fields like `PTX`, `NOP`, `TLE`, and `BAG`. However, complex fields such as **`FNC_FontControl` (0.56x)**, **`LND_LineDescriptor` (0.60x)**, and **`GAD_GraphicsData` (0.41x)** lack these optimizations.

### Reflective Overhead vs. Warm JAXB
Objects without fast-paths fall back to Jackson's default reflective path (`fragmentMapper.writer().writeValue(...)`). For "deep" or "wide" objects (like `FNC` with over 40 fields), Jackson's reflective XML serialization is significantly slower than the project's original JAXB writer, which benefits from a pre-initialized `MarshallerPool`.

---

## Moment 2: Re-measured State (Instrumentation Overhead)

The "Jackson (Re-measured)" column in `PERFORMANCE_CONCEPT.md` shows a secondary regression where Jackson's performance regressed compared to its own initial benchmarks (e.g., `FNC_FontControl` slowed from **2.6ms to 5.21ms**).

### The Cause: MnemonicPerformanceMonitor Instrumentation
The re-measurements were conducted with performance monitoring enabled. This is implemented via `MnemonicXMLStreamWriter`, which intercepts every `writeStartElement` and `writeEndElement` call to record telemetry.

### Cumulative "Telemetry Tax"
For every sub-element in an AFP field (every triplet, flag, and coordinate), the monitor performs:
1.  `System.nanoTime()` calls.
2.  `ThreadLocal.get()` lookups for local statistics.
3.  `Deque.push/pop` operations for mnemonic tracking.
4.  Heuristic string checks to identify mnemonics.

This instrumentation overhead is cumulative. "Wide" objects that generate many sub-elements and lack a fast-path (thus triggering the standard element-writing cycle for every property) suffer a significant "telemetry tax."

---

## Summary of Findings

| Moment | Primary Cause | Impacted Mnemonics |
| :--- | :--- | :--- |
| **JAXB → Jackson** | Jackson's reflective XML path is slower than warm JAXB for complex trees. | `FNC`, `LND`, `GAD`, `BRG`, `BDI` |
| **Jackson → Re-measured** | Cumulative overhead of telemetry (nanoTime, ThreadLocal) per XML element. | Complex fields with high sub-element counts. |
| **NOP Regression** | The `NOP` fast-path performs full EBCDIC-to-String decoding of large payloads. | Synthetic tests with large (32KB) NOPs. |

### Recommendations
1.  **Extend Fast-Paths:** Implement manual StAX paths for `FNC`, `LND`, and other hotspot mnemonics identified in Audit 3.
2.  **Optimize Telemetry:** Reduce the frequency of `System.nanoTime()` or simplify the mnemonic extraction logic in `MnemonicXMLStreamWriter`.
3.  **Lazy NOP Decoding:** Modify the `NOP` fast-path to handle large payloads as raw bytes or only decode on-demand.
