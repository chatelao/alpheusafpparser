# Performance Bottleneck Analysis Report (June 2026)

## 1. Executive Summary
This report analyzes the performance bottlenecks in the Alpheus AFP Parser v10.0, specifically focusing on high-volume AFP-to-XML conversion. While recent optimizations have improved throughput, significant overhead remains in diagnostic paths and instrumentation.

## 2. Profiling Methodology
- **Dataset**: `test/async` (261 files, ~9MB synthetic workload with heavy PTOCA and GOCA content).
- **Tooling**: `async-profiler` (itimer mode) and built-in `MnemonicPerformanceMonitor`.
- **Flags**: `-p` (parallel), `--aggressive-io`, `-m` (measure), `-P` (ptx-debug).

## 3. Identified Hotspots

### 3.1. Excessive I/O Flushing (`--ptx-debug`) ✅
In `AfpJacksonXmlWriter.java`, enabling PTX debugging triggers an explicit `xsw.flush()` after every PTOCA control sequence.
- **Impact**: In a high-volume dataset with ~300,000 control sequences, this results in an equivalent number of flushes, preventing efficient OS-level I/O buffering and increasing system call overhead significantly (~40% of total time in sequential mode).
- **Status**: Resolved in June 2026 by removing per-sequence flushes in favor of standard buffer management.

### 3.2. Mnemonic Monitoring Instrumentation Jitter ✅
The `MnemonicXMLStreamWriter` decorates the StAX writer to track per-mnemonic performance.
- **Impact**: Every XML element (millions in large documents) incurs string manipulation, cache lookups, and `System.nanoTime()` calls. This accounts for ~20% of unmeasured overhead.
- **Root Cause**: Excessive granularity in monitoring PTOCA sub-elements.
- **Status**: Resolved. High-frequency sub-elements now bypass the decorator via direct `baseXsw` calls, and mnemonic extraction has been optimized with fast-path detection.

### 3.3. Jackson Reflective Serialization ✅
While high-frequency sequences (TRN, AMI) use manual StAX fast-paths, many others (GAD drawing orders, TLE triplets) still rely on Jackson's reflective `writeValue`.
- **Impact**: Reflective introspection is significantly slower than manual writing for high-count fields like TLE (10,000+ instances).
- **Status**: Resolved. Manual StAX-based serialization has been implemented for all GOCA drawing orders, IOCA segments, and TLE triplets.

### 3.4. XML Sanitization 🚧
The `SanitizingXMLStreamWriter` checks every character for XML 1.0 validity.
- **Impact**: Scanning 36MB+ of XML adds ~10% overhead. While necessary for robustness, it is a significant CPU consumer.
- **Status**: In progress. Basic sanitization is implemented, but high-performance vectorized scanning is still pending.

## 4. Recommendations

1. ✅ **Remove Diagnostic Flushes**: Modify `AfpJacksonXmlWriter` to only flush at Structured Field boundaries even when `ptxDebug` is enabled, or remove it entirely in favor of standard buffer management. (Completed June 2026)
2. ✅ **Implement Additional StAX Fast-Paths**: Prioritize manual serialization for `GAD_DrawingOrder` subclasses and `TLE` triplets to eliminate Jackson reflection. (Completed June 2026)
3. ✅ **Guard Instrumentation**: Introduce a threshold or selective bypass in `MnemonicXMLStreamWriter` for high-frequency low-complexity elements (like PTOCA sub-elements) to reduce timer overhead. (Completed June 2026)
4. 🚧 **Vectorized Sanitization**: Explore SIMD or vectorized approaches for XML character validation to reduce the cost of the sanitization pass.
