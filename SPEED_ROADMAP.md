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

## Phase 3: NIO & Zero-Copy Enhancements
Focus on maximizing I/O throughput for the CLI.

- **Task 3.1: Enforce `MappedByteBuffer` in CLI**
  - Ensure the `Afp2Xml` CLI always utilizes memory-mapped files via `AFPParserConfiguration`.
  - *Verification*: Reduced kernel-to-user space copying in system-level profiles.

## Phase 4: Benchmarking & Verification
- **Task 4.1: Automated Performance Regression Test**
  - Create a test case that processes a synthetic 10MB AFP file and asserts it completes within a specific time threshold (e.g., < 2s).
  - *Verification*: Continuous monitoring of performance gains.

---

## Status Tracking

| Phase | Description | Status |
| :--- | :--- | :---: |
| 1 | JAXB & Marshalling Optimizations | ⏳ |
| 2 | Character Encoding & Text Detection | ⏳ |
| 3 | NIO & Zero-Copy Enhancements | ⏳ |
| 4 | Benchmarking & Verification | ⏳ |
