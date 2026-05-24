# Performance Optimization Roadmap

This document outlines the phased strategy and implementation steps for performance improvements in the Alpheus AFP Parser.

---

## Roadmap Phases

### Phase 1: JAXB & Marshalling Optimizations ✅
- **Task 1.1: Implement Marshaller Pooling**: Reuse `Marshaller` instances.
- **Task 1.2: Streamline JAXB Context Lookup**: Optimize `JAXBContext` caching.

### Phase 2: Character Encoding & Text Detection ✅
- **Task 2.1: Optimized `isHumanReadable` Check**: Work directly on `byte[]`.
- **Task 2.2: Payload Size Threshold**: Skip detection for large opaque data (>1KB).

### Phase 3: Core Parser & Object Model Optimizations ✅
- **Task 3.1: Precomputed SFTypeID Lookup**: O(1) map-based lookup.
- **Task 3.2: O(1) SFType and SFCategory Lookups**: Sparse array lookups.
- **Task 3.3: Implement Object Pooling**: Reuse SFI, Triplets, and Structured Fields.

### Phase 4: NIO & CLI Enhancements ✅
- **Task 4.1: Enforce `MappedByteBuffer` in CLI**: Direct file mapping.
- **Task 4.2: Robust Resource Management**: Ensure proper parser cleanup.

### Phase 5: Benchmarking & Verification ✅
- **Task 5.1: Automated Performance Regression Test**: Continuous monitoring via `PerformanceRegressionTest`.

### Phase 6: Advanced Performance Optimizations ✅
- **Task 6.1: `ByteBuffer` support in `UtilCharacterEncoding`**: Zero-copy analysis.
- **Task 6.2: Zero-copy `decodeAFP`**: Direct buffer-to-field decoding.
- **Task 6.3: Optimized JAXB & Class Discovery**: Fast-path for single-class lookups.
- **Task 6.4: Reuse XML Infrastructure**: Cache `DocumentBuilder`, `XPath`, and `Transformer`.

### Phase 7: JAXB to Jackson Migration & Async I/O ✅
- **Task 7.1: Jackson Infrastructure**: Introduce `XmlMapper` with JAXB compatibility.
- **Task 7.2: Jackson-Based Streaming Writer**: Implement `AfpJacksonXmlWriter`.
- **Task 7.3: Parallel Page Parsing**: Worker pool for concurrent processing.
- **Task 7.4: Asynchronous I/O**: Utilize `AsynchronousFileChannel`.

### Phase 8: Refinement & Specialized Optimizations ✅
- **Task 8.1: Off-heap buffer management**: Reduce GC pressure for large payloads.
- **Task 8.2: Manual StAX Fast-Paths**: Implement direct writing for `NOP`, `PTX`, `BAG`, `TLE`.

### Phase 9: Performance Benchmarking (Jackson vs JAXB) ✅
- Compare performance of the new Jackson-based pipeline against the original JAXB implementation.

### Phase 10: Specialized StAX + Aalto Optimizations ✅
- Enforce Aalto StAX for ultra-high-performance XML serialization.

### Phase 11: Production-Scale Optimizations ✅
- **Task 11.1: Fast-Path for MCF and Movement Controls**: Manual StAX for `MCF`, `AMI`, and `AMB`.
- **Task 11.2: Jackson Generator Reuse**: Minimize allocation overhead.
- **Task 11.3: Expanded Triplet Fast-Paths**: Native serialization for common triplets.

---

## Status Tracking Summary

| Phase | Description | Status |
| :--- | :--- | :---: |
| 1 | JAXB & Marshalling Optimizations | ✅ |
| 2 | Character Encoding & Text Detection | ✅ |
| 3 | Core Parser & Object Model Optimizations | ✅ |
| 4 | NIO & CLI Enhancements | ✅ |
| 5 | Benchmarking & Verification | ✅ |
| 6 | Advanced Performance Optimizations | ✅ |
| 7 | JAXB to Jackson Migration & Async I/O | ✅ |
| 8 | Refinement & Specialized Optimizations | ✅ |
| 9 | Performance Benchmarking (Jackson vs JAXB) | ✅ |
| 10 | Specialized StAX + Aalto Optimizations | ✅ |
| 11 | Production-Scale Optimizations | ✅ |
