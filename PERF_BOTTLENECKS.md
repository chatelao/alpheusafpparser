# Performance Bottleneck Analysis Report (June 2026)

## 1. Executive Summary
This report analyzes the performance bottlenecks in the Alpheus AFP Parser v10.0, specifically focusing on high-volume AFP-to-XML conversion. While recent optimizations have improved throughput, significant overhead remains in diagnostic paths and instrumentation.

## 2. Profiling Methodology
- **Dataset**: `test/async` (261 files, ~9MB synthetic workload with heavy PTOCA and GOCA content).
- **Tooling**: `async-profiler` (itimer mode) and built-in `MnemonicPerformanceMonitor`.
- **Flags**: `-p` (parallel), `--aggressive-io`, `-m` (measure), `-P` (ptx-debug).

## 3. Identified Hotspots

### 3.1. Excessive I/O Flushing (`--ptx-debug`)
In `AfpJacksonXmlWriter.java`, enabling PTX debugging historically triggered an explicit `xsw.flush()` after every PTOCA control sequence.
- **Impact**: In a high-volume dataset with ~300,000 control sequences, this resulted in an equivalent number of flushes, preventing efficient OS-level I/O buffering.
- **Status**: ✅ **Resolved** (June 2026). Flushes were removed to optimize performance, although individual `xmlSize` metrics in debug mode are now less granular.

### 3.2. Mnemonic Monitoring Instrumentation Jitter
The `MnemonicXMLStreamWriter` decorates the StAX writer to track per-mnemonic performance.
- **Impact**: Every XML element incurred string manipulation and `System.nanoTime()` calls.
- **Status**: ✅ **Resolved** (June 2026). `AfpJacksonXmlWriter` now bypasses the decorator using `baseXsw` for high-frequency, low-complexity sub-elements (like PTOCA sequences and GOCA internal orders).

### 3.3. Jackson Reflective Serialization
Many fields (GAD drawing orders, TLE triplets) previously relied on Jackson's reflective `writeValue`.
- **Impact**: Reflective introspection was significantly slower than manual writing for high-count fields.
- **Status**: ✅ **Resolved** (June 2026). Manual StAX fast-paths were implemented for nearly all GOCA drawing orders, IOCA segments, and common TLE triplets.

### 3.4. XML Sanitization
The `SanitizingXMLStreamWriter` checks every character for XML 1.0 validity.
- **Impact**: Scanning 36MB+ of XML adds ~10% overhead.
- **Status**: 🚧 **In Progress**. A pre-check using `needsXmlSanitization` was added to avoid re-scanning/allocating valid strings, but vectorized/SIMD sanitization remains a pending recommendation.

## 4. Recommendations

1. **Remove Diagnostic Flushes**: Modify `AfpJacksonXmlWriter` to only flush at Structured Field boundaries even when `ptxDebug` is enabled, or remove it entirely in favor of standard buffer management.
2. **Implement Additional StAX Fast-Paths**: Prioritize manual serialization for `GAD_DrawingOrder` subclasses and `TLE` triplets to eliminate Jackson reflection.
3. **Guard Instrumentation**: Introduce a threshold or selective bypass in `MnemonicXMLStreamWriter` for high-frequency low-complexity elements (like PTOCA sub-elements) to reduce timer overhead.
4. **Vectorized Sanitization**: Explore SIMD or vectorized approaches for XML character validation to reduce the cost of the sanitization pass.
