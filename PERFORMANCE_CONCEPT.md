# Performance Concept: Alpheus AFP Parser

This document combines the high-level performance concepts, architectural decisions, audit results, and testing strategies for the Alpheus AFP Parser.

---

## 1. Initial Concept & Bottleneck Analysis

### A. In-Memory Document Model (`AFPDocument`)
Originally, `Afp2Xml` read the *entire* AFP data stream and populated an `AFPDocument` object tree. For large files (e.g., 50 MB), this consumed significant heap memory (500 MB - 1 GB).

### B. JAXB and DOM-based XPath Filtering
`Afp2XmlWriter` used JAXB and W3C DOM for XML serialization and filtering, which was memory-inefficient and slow for large trees.

### C. Sequential Parsing and I/O
The initial `AFPParser` used sequential `InputStream` reads, limiting throughput and failing to leverage multi-core systems.

### D. Reflection Overhead
Using `Class.forName()` and reflection for every Structured Field record added significant overhead when processing millions of fields.

---

## 2. Performance Architecture & Decisions

### Strategy 1: Streaming Architecture (Event-Driven)
Transitioned from a "Document-at-once" model to a "Streaming" model (O(1) memory footprint).
- **Streaming Parser**: Event-driven `AFPParser`.
- **Streaming XML Generation**: Replaced JAXB/DOM with **StAX (XMLStreamWriter)** and **Jackson XML**.

### Strategy 2: Memory-Efficient Object Model
- **Zero-Copy Parsing**: Utilized `java.nio.MappedByteBuffer` to reference data directly.
- **Object Pooling**: Reused `StructuredFieldIntroducer`, `IRepeatingGroup`, and other common objects to reduce GC pressure.

### Strategy 3: Parallel Processing
- **Parallel Page Parsing**: Master thread scans for page markers (`BPG`/`BGP`) and dispatches segments to worker pools.
- **Asynchronous I/O**: Overlapped I/O using `AsynchronousFileChannel`.

### Strategy 4: Optimization & Specialized Components
- **Pre-computed SF Mapping**: O(1) lookup via `Map<Integer, Supplier<StructuredField>>`.
- **Fast Charset Decoders**: Optimized EBCDIC-to-UTF8 decoders (CP500/CP273).
- **Manual StAX Fast-Paths**: Implemented for high-frequency fields (`NOP`, `PTX`) in Jackson writer to bypass reflective JAXB/Jackson overhead.

---

## 3. Performance Audits & Benchmark Results

### Audit 1: Core Optimizations (Phases 1-6)
Focused on JAXB pooling, encoding optimizations, and SFTypeID lookups.
- **Result**: 10MB conversion time dropped from ~14.5s to < 1s (~11.5 MB/s).

### Audit 2: Specialized PTOCA & Jackson Support
Analyzed PTX-heavy payloads and introduced Jackson-based streaming.
- **Finding**: JAXB recursive marshalling was the primary hotspot for complex fields.
- **Result**: Jackson identified as ~2x faster than JAXB for comprehensive field sets.

### Latest Benchmark Results (Current State)
*Executed via `PerformanceRegressionTest`*

| Test Case | Method | Execution Time | Re-measured | Re-measured 2 (Parallel) | Status |
| :--- | :--- | :--- | :--- | :--- | :--- |
| **10MB Synthetic AFP** | JAXB Streaming | ~734ms | 955ms | - | ✅ Passed (< 2s) |
| **10MB Synthetic AFP** | Jackson Streaming | ~683ms | 719ms | 574ms | ✅ Passed (< 2s) |
| **20MB Synthetic AFP** | JAXB Avg | ~7ms* | 7ms | - | ✅ |
| **20MB Synthetic AFP** | Jackson Avg | ~5ms* | 4ms | - | ✅ |
| **100MB Large AFP** | Jackson Overall | - | - | **458ms** | ✅ |
| **Comprehensive (All SFs)**| Overall | **Jackson 3.62x faster** | **Jackson 3.88x faster** | **Jackson Parallel 4.43x faster** | ✅ |

*\*Note: High variance in small synthetic tests due to JIT and buffer caching.*

#### Jackson Performance Highlights (Top Slowest Fields - ns total)
1. `FNC_FontControl`
2. `FND_FontDescriptor`
3. `BDD_BarCodeDataDescriptor`
4. `BCP_BeginCodePage`
5. `RCD_RecordDescriptor`

---

## 4. Mnemonic Performance Benchmarks (Structured Fields)

The following table shows the performance of various mnemonics (Structured Fields) comparing JAXB and Jackson streaming serialization. Results are based on 100 instances per type.

| Mnemonic | Count | JAXB (ms total) | Jackson (ms total) | Jackson (Re-measured) | Parallel (Re-measured 2) | Speedup |
| :--- | :---: | :---: | :---: | :---: | :---: | :---: |
| BDA_BarCodeData | 100 | ~78.9ms | 1.2ms | 1.07ms | 0.95ms | 83.05x |
| RCD_RecordDescriptor | 100 | ~1.59ms | 1.8ms | 2.31ms | 2.10ms | 0.76x |
| BBC_BeginBarCodeObject | 100 | ~1.05ms | 1.3ms | 1.30ms | 0.03ms | 35.00x |
| XMD_XMLDescriptor | 100 | ~1.31ms | 1.8ms | 2.03ms | 1.95ms | 0.67x |
| LND_LineDescriptor | 100 | ~0.90ms | 1.5ms | 2.30ms | 1.88ms | 0.48x |
| FNC_FontControl | 100 | ~1.45ms | 2.6ms | 5.21ms | 5.05ms | 0.29x |
| BCP_BeginCodePage | 100 | ~1.08ms | 2.0ms | 0.40ms | 0.09ms | 12.00x |
| FND_FontDescriptor | 100 | ~1.23ms | 2.5ms | 2.99ms | 2.85ms | 0.43x |
| CMR_ColorManagementResource | 100 | ~0.60ms | 1.4ms | 1.44ms | 1.35ms | 0.44x |
| IID_IMImageInputDescriptor | 100 | ~0.48ms | 1.2ms | 1.12ms | 0.09ms | 5.33x |

*Overall, Jackson remains significantly faster for the total workload by leveraging manual StAX fast-paths for high-frequency fields and avoided JAXB initialization overhead, though some individual complex fields without specialized fast-paths may show higher per-instance overhead in Jackson's default reflective path compared to warm JAXB.*

### Real-world Profiling Results

| Mnemonic | Count | Total (ms) | Parse (ms) | Write (ms) |
|----------|-------|------------|------------|------------|
| BRS      | 2     | 37         | 35         | 2          |
| BDT      | 134   | 16         | 3          | 13         |
| EDT      | 133   | 14         | 2          | 12         |
| PTX      | 1     | 9          | 9          | 0          |
| MDD      | 4     | 4          | 0          | 4          |
| TRN      | 93    | 1          | 1          | 0          |

---

## 5. Summary of Resolved Bottlenecks

1.  **JAXB Marshaller Creation**: Resolved via `MarshallerPool`.
2.  **Human Readable Check**: Resolved via `ByteBuffer` fast-path and 1KB threshold.
3.  **SFTypeID Lookup**: Resolved via `HashMap` lookup.
4.  **Reflection Overhead**: Resolved via pre-computed `Supplier` maps.
5.  **Memory Pressure**: Resolved via Streaming Architecture and Object Pooling.
6.  **I/O Latency**: Resolved via NIO and Memory-Mapped Files.

---

## 6. Meta-Structure Hierarchy Performance Audit

The following table summarizes the performance of the core components defined in `specifications/AFP_META_STRUCTURE.md`. These components form the skeleton of the MO:DCA data stream. Measurement results are based on 100 instances per type, comparing JAXB and Jackson streaming.

| Component (Mnemonic) | JAXB (ms total) | Jackson (ms total) | Jackson (Re-measured) | Parallel (Re-measured 2) | Speedup | Status |
| :--- | :---: | :---: | :---: | :---: | :---: | :---: |
| **Print File (BPF/EPF)** | ~3.57ms | ~1.68ms | 0.47ms | 0.18ms | 19.83x | ✅ Measured |
| **Resource Group (BRG/ERG)** | ~5.65ms | ~1.13ms | 0.49ms | 0.18ms | 31.38x | ✅ Measured |
| **Document Index (BDI/EDI)** | ~1.17ms | ~3.83ms | 0.45ms | 0.18ms | 6.50x | ✅ Measured |
| **Document (BDT/EDT)** | ~2.25ms | ~5.12ms | 0.57ms | 0.20ms | 11.25x | ✅ Measured |
| **Page Group (BNG/ENG)** | ~2.21ms | ~8.97ms | 0.47ms | 0.18ms | 12.27x | ✅ Measured |
| **Page (BPG/EPG)** | ~4.17ms | ~1.57ms | 0.44ms | 0.23ms | 18.13x | ✅ Measured |
| **Active Env Group (BAG/EAG)** | ~1.33ms | ~0.80ms | 0.04ms | 0.33ms | 4.03x | ✅ Measured |
| **Object Env Group (BOG/EOG)** | ~1.54ms | ~2.77ms | 0.67ms | 0.18ms | 8.55x | ✅ Measured |
| **Resource (BRS/ERS)** | ~0.57ms | ~1.93ms | 0.84ms | 0.09ms | 6.33x | ✅ Measured |
| **Overlay (BMO/EMO)** | ~4.52ms | ~4.67ms | 0.61ms | 0.18ms | 25.11x | ✅ Measured |
| **Page Segment (BPS/EPS)** | ~1.89ms | ~2.01ms | 0.63ms | 0.19ms | 10.53x | ✅ Measured |
| **Form Map (BFM/EFM)** | ~4.64ms | ~6.98ms | 0.42ms | 0.19ms | 24.42x | ✅ Measured |
| **Tag Logical Element (TLE)** | ~0.15ms | ~0.04ms | 0.05ms | 0.08ms | 1.87x | ✅ Measured |
| **Presentation Text (PTX)** | ~1.96ms | ~0.05ms | 0.04ms | 0.03ms | 65.33x | ✅ Measured |
| **Bar Code Data (BDA)** | ~133.76ms | ~1.27ms | 1.07ms | 0.91ms | 147.01x | ✅ Measured |
| **Graphics Data (GAD)** | ~0.22ms | ~2.87ms | 0.54ms | 0.09ms | 2.44x | ✅ Measured |
| **Image Picture Data (IPD)** | ~0.48ms | ~0.53ms | 0.42ms | 0.09ms | 5.33x | ✅ Measured |
| **Object Container Data (OCD)**| ~0.13ms | ~0.56ms | 0.51ms | 0.10ms | 1.44x | ✅ Measured |

---

## 7. Audit 3: Real-World Production Data Audit (High Volume)

A performance audit was conducted on a production-scale dataset consisting of multiple large AFP files. The audit utilized the `MnemonicPerformanceMonitor` to aggregate parse and write timings.

### Production Dataset Metrics (Aggregated)

| Mnemonic | Count | Total (ms) | Parse (ms) | Write (ms) |
| :--- | :---: | :---: | :---: | :---: |
| **PTX** | 2,434 | 10,607 | 91 | 10,516 |
| **MCF** | 361 | 764 | 21 | 743 |
| **TLE** | 3,255 | 760 | 46 | 714 |
| **IPD** | 1,953 | 237 | 45 | 192 |
| **OBD** | 662 | 165 | 10 | 155 |
| **OBP** | 662 | 113 | 3 | 110 |
| **IDD** | 651 | 109 | 6 | 103 |
| **MIO** | 651 | 85 | 4 | 81 |

### Key Observations & Bottlenecks

1.  **PTX Write Hotspot**: Despite existing fast-paths for some control sequences, `PTX_PresentationTextData` remains the single largest bottleneck in the writing phase, accounting for over 10 seconds of processing time.
2.  **MCF and TLE Overhead**: `MCF` (Map Coded Font) and `TLE` (Tag Logical Element) show significant write overhead.
3.  **Object/Data Descriptors**: `OBD`, `OBP`, `IDD`, and `MIO` represent a secondary tier of bottlenecks, likely due to repeated JAXB/Jackson reflective serialization.
4.  **Parse vs. Write Disparity**: In all cases, the Parse phase is significantly faster than the Write phase, confirming that XML serialization is the current primary performance bottleneck.

---

## 8. Parallel JUnit Execution Concept

This section describes the strategy for parallel execution of JUnit tests in the Alpheus AFP Parser project.

### Motivation
With over 350 tests and the goal of high performance, an efficient test suite is crucial. Parallel tests shorten feedback cycles during development and in CI/CD (GitHub Actions).

### Pros and Cons of Parallelization

#### Pros
- **Time Savings**: Significant reduction in overall test suite duration.
- **Resource Utilization**: Better use of multi-core processors.
- **Earlier Feedback**: Faster regressions alerts for developers.
- **Uncovering Concurrency Issues**: Parallel tests can implicitly reveal race conditions in the application logic.

#### Cons
- **Flaky Tests**: Tests accessing shared resources (static fields, files, ports) can become unstable.
- **Complexity**: Debugging failed tests in a parallel environment is more challenging.
- **Resource Conflicts**: Increased memory consumption due to parallel instances.

### Favored Strategy for Alpheus
We use **JUnit 5 Native Parallel Execution** (Dynamic Strategy).

1.  **Efficiency**: Lower overhead compared to process forks.
2.  **Control**: Individual problematic test classes can be excluded via `@Execution(ExecutionMode.SAME_THREAD)`.
3.  **Standardized**: Standard approach in the Java ecosystem.

#### Guidelines for Parallel Tests
- **No Static States**: Tests must not modify shared static variables.
- **Isolated File System Access**: Use `@TempDir` for temporary AFP files.
- **Thread Safety**: Ensure components (especially pooling mechanisms) are thread-safe.

---

## 9. Future Work & Recommendations

- **Further GOCA/IOCA Optimization**: Extend manual StAX fast-paths to complex GOCA orders and IOCA segments if they become dominant hotspots.
- **Parallel Optimization**: Refine the `ParallelPageParser` for even higher core-count scalability.
- **SIMD Integration**: Explore `simdxml` concepts for even faster XML generation if required by future throughput targets.
