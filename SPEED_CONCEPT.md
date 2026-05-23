# Speed & Performance Concept: Alpheus AFP Parser

This document combines the initial performance concepts, architectural decisions, and subsequent audit results for the Alpheus AFP Parser.

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

## 2. Performance Roadmap & Architectural Decisions

### Strategy 1: Streaming Architecture (Event-Driven)
Transitioned from a "Document-at-once" model to a "Streaming" model (O(1) memory footprint).
- **Streaming Parser**: Event-driven `AFPParser`.
- **Streaming XML Generation**: Replaced JAXB/DOM with **StAX (XMLStreamWriter)** and **Jackson XML**.

### Strategy 2: Memory-Efficient Object Model
- **Zero-Copy Parsing**: Utilized `java.nio.MappedByteBuffer` to reference data directly.
- **Object Pooling**: Reused `StructuredFieldIntroducer`, `IRepeatingGroup`, and other common objects to reduce GC pressure.

### Strategy 3: Parallel Processing (Phase 10)
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

| Test Case | Method | Execution Time | Status |
| :--- | :--- | :--- | :--- |
| **10MB Synthetic AFP** | JAXB Streaming | ~1280ms | ✅ Passed (< 2s) |
| **10MB Synthetic AFP** | Jackson Streaming | ~830ms | ✅ Passed (< 2s) |
| **20MB Synthetic AFP** | JAXB Avg | ~34ms* | ✅ |
| **20MB Synthetic AFP** | Jackson Avg | ~19ms* | ✅ |
| **Comprehensive (All SFs)**| Overall | **Jackson 2.24x faster** | ✅ |

*\*Note: High variance in small synthetic tests due to JIT and buffer caching.*

#### Jackson Performance Highlights (Top Slowest Fields - ns total)
1. `BDD_BarCodeDataDescriptor`
2. `BDA_BarCodeData`
3. `FNC_FontControl`
4. `IID_IMImageInputDescriptor`
5. `CMR_ColorManagementResource`

---

## 4. Summary of Resolved Bottlenecks

1.  **JAXB Marshaller Creation**: Resolved via `MarshallerPool`.
2.  **Human Readable Check**: Resolved via `ByteBuffer` fast-path and 1KB threshold.
3.  **SFTypeID Lookup**: Resolved via `HashMap` lookup.
4.  **Reflection Overhead**: Resolved via pre-computed `Supplier` maps.
5.  **Memory Pressure**: Resolved via Streaming Architecture and Object Pooling.
6.  **I/O Latency**: Resolved via NIO and Memory-Mapped Files.

---

## 5. Future Work & Recommendations

- **Further GOCA/IOCA Optimization**: Extend manual StAX fast-paths to complex GOCA orders and IOCA segments if they become dominant hotspots.
- **Parallel Optimization**: Refine the `ParallelPageParser` for even higher core-count scalability.
- **SIMD Integration**: Explore `simdxml` concepts for even faster XML generation if required by future throughput targets.
