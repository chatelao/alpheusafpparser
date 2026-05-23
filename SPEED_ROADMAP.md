# Performance Optimization Roadmap (Speed)

This roadmap outlines the implementation steps for the performance improvements proposed in `SPEED_AUDIT.md`.

## Phase 1: JAXB & Marshalling Optimizations ✅
Focus on reducing the overhead of XML serialization during streaming conversion.

- **Task 1.1: Implement Marshaller Pooling** ✅
  - Create a `MarshallerPool` or use a `ThreadLocal<Marshaller>` in `AfpStreamingXmlWriter`.
  - Reuse `Marshaller` instances for subsequent `writeField` calls.
  - *Verification*: JFR profiling should show significantly reduced time in `jaxbContext.createMarshaller()`.
- **Task 1.2: Streamline JAXB Context Lookup** ✅
  - Optimize `Afp2XmlWriter.getCachedJaxbContext` to avoid redundant sorting of class lists.
  - Implement a direct mapping for common Structured Field classes to their `JAXBContext`.
  - *Verification*: Reduced overhead in `getCachedJaxbContext`.

## Phase 2: Character Encoding & Text Detection ✅
Focus on optimizing the processing of high-frequency text-bearing fields.

- **Task 2.1: Optimized `isHumanReadable` Check** ✅
  - Refactor `UtilCharacterEncoding.isHumanReadable` to work directly on `byte[]`.
  - Implement fast-path checks for common EBCDIC printable ranges (CP500/CP273).
  - Avoid `new String()` calls until the data is confirmed to be human-readable.
  - *Verification*: Reduced CPU time in `StructuredFieldBaseData.decodeAFP`.
- **Task 2.2: Payload Size Threshold for Text Detection** ✅
  - Introduce a configurable size limit for automatic human-readable detection (e.g., skip for >1KB).
  - *Verification*: Faster processing of large opaque data fields (like `NOP` with large payloads or `IRD`).

## Phase 3: Core Parser & Object Model Optimizations ✅
Focus on reducing CPU cycles per structured field.

- **Task 3.1: Precomputed SFTypeID Lookup** ✅
  - Replace linear search in `SFTypeID.parse` and `valueOf` with a static `Map` lookup.
  - *Verification*: Significantly reduced time in `SFTypeID` resolution during profiling.
- **Task 3.2: O(1) SFType and SFCategory Lookups** ✅
  - Replace linear searches in `SFType.valueOf` and `SFCategory.valueOf` with sparse array lookups.
  - *Verification*: Immediate mapping from byte to enum constant.
- **Task 3.3: Implement Object Pooling** ✅
  - Reuse common objects (Introducers, Triplets, Structured Fields, Repeating Groups) to reduce GC pressure.
  - *Verification*: Reduced memory churn and GC pauses during high-volume parsing.

## Phase 4: NIO & CLI Enhancements ✅
Focus on maximizing I/O throughput and resource management.

- **Task 4.1: Enforce `MappedByteBuffer` in CLI** ✅
  - Ensure the `Afp2Xml` CLI always utilizes memory-mapped files via `AFPParserConfiguration`.
  - *Verification*: Reduced kernel-to-user space copying in system-level profiles.
- **Task 4.2: Robust Resource Management in CLI** ✅
  - Ensure `AFPParser.quitParsing()` is always called in the CLI tool.

## Phase 5: Benchmarking & Verification ✅
- **Task 5.1: Automated Performance Regression Test** ✅
  - Create a test case that processes a synthetic 10MB AFP file and asserts it completes within a specific time threshold (e.g., < 2s).
  - *Verification*: Continuous monitoring of performance gains via `PerformanceRegressionTest`.

## Phase 6: Advanced Performance Optimizations ✅
Focus on zero-copy parsing and reducing overhead in XML serialization.

- **Task 6.1: `ByteBuffer` support in `UtilCharacterEncoding`** ✅
  - Implement overloads for `isHumanReadable` and `decodeEbcdic` that accept `ByteBuffer`.
  - *Verification*: Direct parsing from buffers without intermediate array allocations.
- **Task 6.2: Zero-copy `decodeAFP` in `StructuredFieldBaseData` & `AFPParser`** ✅
  - Override `decodeAFP(ByteBuffer, ...)` to avoid intermediate byte array copies.
  - Refactor `AFPParser` to use the buffer-based decoding path.
  - *Verification*: Reduced memory churn during parsing of large opaque fields.
- **Task 6.3: Optimized JAXB & Class Discovery** ✅
  - Implement fast-path for single-class `JAXBContext` lookups in `Afp2XmlWriter`.
  - Refactor class discovery to use loops instead of streams.
  - *Verification*: Reduced per-field CPU overhead in conversion.
- **Task 6.4: Reuse XML Infrastructure** ✅
  - Cache and reuse `DocumentBuilder`, `XPath`, and `Transformer` in `AfpStreamingXmlWriter`.
  - *Verification*: Faster XPath-filtered conversion.
- **Task 6.5: Manual StAX Fast-Paths for High-Frequency Fields** ✅
  - Implement manual StAX serialization for `NOP`, `PTX`, `BAG`, `TLE`, etc., in the Jackson writer.
  - *Verification*: Significant speedup in Jackson streaming for text-heavy and high-frequency fields.

## Phase 7: Parallel Processing & Async I/O ✅
Focus on leveraging multi-core systems and overlapping I/O.

- **Task 7.1: Parallel Page Parsing** ✅
  - Implement a worker pool to process page segments (`BPG` to `EPG`) in parallel.
  - *Verification*: Improved throughput on multi-core systems for large multi-page AFP files.
- **Task 7.2: Asynchronous I/O** ✅
  - Utilize `AsynchronousFileChannel` for overlapping I/O and processing.
  - *Verification*: Reduced idle CPU time during I/O-heavy parsing.

## Phase 8: Refinement & Specialized Optimizations ✅
Focus on further reducing overhead and modernizing the stack.

- **Task 8.1: Off-heap buffer management** ✅
  - Explore off-heap storage for large payloads to further reduce GC pressure.
- **Task 8.2: Complete JAXB Removal** ✅
  - Replace all remaining JAXB annotations with Jackson native annotations and remove JAXB dependencies.

## Phase 9: Experimental & Future Enhancements ✅
Focus on cutting-edge performance improvements.

- **Task 9.1: SIMD Integration** ✅
  - Explore the use of SIMD (e.g., via `simdxml` concepts) for even faster XML generation.
- **Task 9.2: Further GOCA/IOCA Optimization** ✅
  - Extend manual StAX fast-paths to complex GOCA drawing orders and IOCA segments.

## Phase 11: Production-Scale Optimizations ✅
Focus on bottlenecks identified during high-volume production data audits.

- **Task 11.1: Fast-Path for MCF and Movement Controls** ✅
  - Implement manual StAX serialization for `MCF_MapCodedFont_Format2` in `AfpJacksonXmlWriter`.
  - Implement manual StAX serialization for `AMI` and `AMB` control sequences.
  - *Verification*: Reduced `Write (ms)` for MCF, AMI, and AMB in `MnemonicPerformanceMonitor` output.
- **Task 11.2: Jackson Generator Reuse & Allocation Reduction** ✅
  - Refactor `AfpJacksonXmlWriter` to reuse `ToXmlGenerator` or minimize its creation for fallback fields.
  - *Verification*: Reduced GC overhead and improved throughput in large file conversions.
- **Task 11.3: Expanded Triplet Fast-Paths** ✅
  - Implement more common triplets in `writeTriplet` to further reduce Jackson fallback.
  - *Verification*: Higher percentage of manual serialization in profiles.

---

## Status Tracking

| Phase | Description | Status |
| :--- | :--- | :---: |
| 1 | JAXB & Marshalling Optimizations | ✅ |
| 2 | Character Encoding & Text Detection | ✅ |
| 3 | Core Parser & Object Model Optimizations | ✅ |
| 4 | NIO & CLI Enhancements | ✅ |
| 5 | Benchmarking & Verification | ✅ |
| 6 | Advanced Performance Optimizations | ✅ |
| 7 | JAXB to Jackson Infrastructure | ✅ |
| 8 | Jackson-Based Streaming Writer | ✅ |
| 9 | Performance Benchmarking (Jackson vs JAXB) | ✅ |
| 10 | Specialized StAX + Aalto Optimizations | ✅ |
| 11 | Production-Scale Optimizations | ✅ |
