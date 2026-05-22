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
Focus on reducing the raw parsing overhead identified in the secondary audit.

- **Task 3.1: Precomputed `SFTypeID` Lookup**
  - Replace the linear search in `SFTypeID.parse` and `valueOf` with a static lookup table or `Map<Integer, SFTypeID>`.
  - *Verification*: `ProfileAfpParser.java` should show a measurable reduction in `Sequential (Stream)` and `Sequential (Buffer)` times.
- **Task 3.2: Zero-Copy Payload Processing**
  - Extend `IAFPDecodeableWriteable` and its implementations to support `ByteBuffer` directly, avoiding `byte[]` copies for decoding.
  - *Verification*: Reduced GC allocation rate in JFR profiles during large file processing.
- **Task 3.3: Optimize Object Pool Lookups**
  - Evaluate and optimize the lookup mechanism for `StructuredFieldIntroducer`, `Triplet`, and `StructuredField` pools.
  - *Verification*: Reduced CPU cycles in `acquire()`/`release()` calls.
- **Task 3.4: Consistent Fast EBCDIC Decoding**
  - Ensure `UtilCharacterEncoding.decodeCp273` and `decodeCp500` lookup tables are used everywhere character set resolution occurs.

## Phase 4: NIO & Zero-Copy Enhancements
Focus on maximizing I/O throughput for the CLI.

- **Task 4.1: Enforce `MappedByteBuffer` in CLI**
  - Ensure the `Afp2Xml` CLI always utilizes memory-mapped files via `AFPParserConfiguration`.
  - *Verification*: Reduced kernel-to-user space copying in system-level profiles.

## Phase 5: Benchmarking & Verification
- **Task 5.1: Automated Performance Regression Test**
  - Create a test case that processes a synthetic 10MB AFP file and asserts it completes within a specific time threshold (e.g., < 2s).
  - *Verification*: Continuous monitoring of performance gains.

---

## Status Tracking

| Phase | Description | Status |
| :--- | :--- | :---: |
| 1 | JAXB & Marshalling Optimizations | ✅ |
| 2 | Character Encoding & Text Detection | ✅ |
| 3 | Core Parser & Object Model Optimizations | ⏳ |
| 4 | NIO & Zero-Copy Enhancements | ⏳ |
| 5 | Benchmarking & Verification | ⏳ |
