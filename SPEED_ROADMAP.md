# Performance Optimization Roadmap (Speed)

This roadmap outlines the implementation steps for the performance improvements proposed in `SPEED_AUDIT.md`.

## Phase 1: JAXB & Marshalling Optimizations
Focus on reducing the overhead of XML serialization during streaming conversion.

- **Task 1.1: Implement Marshaller Pooling**
  - Create a `MarshallerPool` or use a `ThreadLocal<Marshaller>` in `AfpStreamingXmlWriter`.
  - Reuse `Marshaller` instances for subsequent `writeField` calls.
  - *Verification*: JFR profiling should show significantly reduced time in `jaxbContext.createMarshaller()`.
- **Task 1.2: Streamline JAXB Context Lookup**
  - Optimize `Afp2XmlWriter.getCachedJaxbContext` to avoid redundant sorting of class lists.
  - Implement a direct mapping for common Structured Field classes to their `JAXBContext`.
  - *Verification*: Reduced overhead in `getCachedJaxbContext`.

## Phase 2: Character Encoding & Text Detection
Focus on optimizing the processing of high-frequency text-bearing fields.

- **Task 2.1: Optimized `isHumanReadable` Check**
  - Refactor `UtilCharacterEncoding.isHumanReadable` to work directly on `byte[]`.
  - Implement fast-path checks for common EBCDIC printable ranges (CP500/CP273).
  - Avoid `new String()` calls until the data is confirmed to be human-readable.
  - *Verification*: Reduced CPU time in `StructuredFieldBaseData.decodeAFP`.
- **Task 2.2: Payload Size Threshold for Text Detection**
  - Introduce a configurable size limit for automatic human-readable detection (e.g., skip for >1KB).
  - *Verification*: Faster processing of large opaque data fields (like `NOP` with large payloads or `IRD`).

## Phase 3: Core Parser & Object Model Optimizations
Focus on reducing CPU cycles per structured field.

- **Task 3.1: Precomputed SFTypeID Lookup**
  - Replace linear search in `SFTypeID.parse` and `valueOf` with a static `Map` lookup.
  - *Verification*: Significantly reduced time in `SFTypeID` resolution during profiling.
- **Task 3.2: O(1) SFType and SFCategory Lookups**
  - Replace linear searches in `SFType.valueOf` and `SFCategory.valueOf` with sparse array lookups.
  - *Verification*: Immediate mapping from byte to enum constant.

## Phase 4: NIO & CLI Enhancements
Focus on maximizing I/O throughput and resource management.

- **Task 4.1: Enforce `MappedByteBuffer` in CLI**
  - Ensure the `Afp2Xml` CLI always utilizes memory-mapped files via `AFPParserConfiguration`.
  - *Verification*: Reduced kernel-to-user space copying in system-level profiles.
- **Task 4.2: Robust Resource Management in CLI**
  - Ensure `AFPParser.quitParsing()` is always called in the CLI tool.

## Phase 5: Benchmarking & Verification
- **Task 5.1: Automated Performance Regression Test**
  - Create a test case that processes a synthetic 10MB AFP file and asserts it completes within a specific time threshold (e.g., < 2s).
  - *Verification*: Continuous monitoring of performance gains via `PerformanceRegressionTest`.

## Phase 6: Advanced Performance Optimizations
Focus on zero-copy parsing and reducing overhead in XML serialization.

- **Task 6.1: `ByteBuffer` support in `UtilCharacterEncoding`**
  - Implement overloads for `isHumanReadable` and `decodeEbcdic` that accept `ByteBuffer`.
  - *Verification*: Direct parsing from buffers without intermediate array allocations.
- **Task 6.2: Zero-copy `decodeAFP` in `StructuredFieldBaseData` & `AFPParser`**
  - Override `decodeAFP(ByteBuffer, ...)` to avoid intermediate byte array copies.
  - Refactor `AFPParser` to use the buffer-based decoding path.
  - *Verification*: Reduced memory churn during parsing of large opaque fields.
- **Task 6.3: Optimized JAXB & Class Discovery**
  - Implement fast-path for single-class `JAXBContext` lookups in `Afp2XmlWriter`.
  - Refactor class discovery to use loops instead of streams.
  - *Verification*: Reduced per-field CPU overhead in conversion.
- **Task 6.4: Reuse XML Infrastructure**
  - Cache and reuse `DocumentBuilder`, `XPath`, and `Transformer` in `AfpStreamingXmlWriter`.
  - *Verification*: Faster XPath-filtered conversion.

---

## Status Tracking

| Phase | Description | Status |
| :--- | :--- | :---: |
| 1 | JAXB & Marshalling Optimizations | ✅ |
| 2 | Character Encoding & Text Detection | ✅ |
| 3 | Core Parser & Object Model Optimizations | ✅ |
| 4 | NIO & CLI Enhancements | ✅ |
| 5 | Benchmarking & Verification | ✅ |
| 6 | Advanced Performance Optimizations | 🚧 |
