# Concept: High-Performance AFP Parsing and Conversion

This document analyzes current performance bottlenecks when processing large AFP files (5 MB to 50 MB and beyond) and outlines a conceptual roadmap for optimizing the Alpheus AFP Parser for speed and memory efficiency.

## 1. Current Bottlenecks Analysis

### A. In-Memory Document Model (`AFPDocument`)
Currently, `Afp2Xml` reads the *entire* AFP data stream and populates an `AFPDocument` object with all Structured Fields (SFs) before starting the XML conversion. For a 50 MB AFP file, the resulting Java object tree can easily consume 500 MB to 1 GB of heap memory due to object overhead and JAXB annotations.

### B. JAXB and DOM-based XPath Filtering
`Afp2XmlWriter` utilizes JAXB for XML serialization. When an XPath filter is applied, it marshals the entire `AFPDocument` into a W3C DOM `Document`. DOM is notoriously memory-inefficient (often 10x the size of the XML source). Evaluating XPath on a large DOM tree significantly slows down processing and risks `OutOfMemoryError`.

### C. Sequential Parsing and I/O
The current `AFPParser` reads from an `InputStream` sequentially. While `BufferedInputStream` is used, the overhead of reading small byte arrays and the lack of parallel processing for independent document sections (like individual pages or page groups) limits throughput on multi-core systems.

### D. Reflection Overhead
The parser uses `Class.forName()` and reflection to instantiate Structured Field classes for every record. While JVM-optimized, this still adds overhead when processing millions of structured fields.

---

## 2. Proposed Performance Roadmap

### Phase 1: Streaming Architecture (Event-Driven)
Transition from a "Document-at-once" model to a "Streaming" model, similar to SAX or StAX.

- **Streaming Parser**: Enhance `AFPParser` to support a visitor pattern or an event listener interface.
- **Streaming XML Generation**: Replace `Afp2XmlWriter`'s JAXB/DOM approach with **StAX (XMLStreamWriter)**.
    - Serialize each `StructuredField` to XML immediately after parsing and then discard the object.
    - This keeps memory usage constant (O(1)) regardless of file size.
- **Streaming XPath**: Implement a basic streaming XPath filter that matches elements at the root or page level without building a DOM tree.

### Phase 2: Memory-Efficient Object Model
- **Flyweight/Shallow Objects**: Standardize the use of `isBuildShallow` in `AFPParserConfiguration`.
- **Zero-Copy Parsing**: Use `java.nio.MappedByteBuffer` to map the AFP file into memory, allowing the parser to reference data directly from the buffer without copying it into `byte[]` arrays for every field.
- **Pooled Objects**: Reuse `StructuredFieldIntroducer` and common payload objects to reduce GC pressure.

### Phase 3: Parallel Processing
AFP is inherently sequential, but large documents often contain independent **Page Groups** or **Pages**.

- **Parallel Page Parsing**: Implement a "seek-and-parse" strategy. A master thread scans for `BPG` (Begin Page Group) or `BGP` (Begin Page) markers and hands off the processing of that segment to a worker thread pool.
- **Asynchronous I/O**: Use `AsynchronousFileChannel` to overlap I/O operations with CPU-intensive parsing and XML formatting.

### Phase 4: Optimization & Specialized Components
- **Pre-computed SF Mapping**: Replace reflection-based class lookup in `AFPParser.createSFInstance` with a static `Map<Integer, Supplier<StructuredField>>` (using the 3-byte SF Type ID as a key).
- **Custom Fast Charset Decoders**: Implement optimized EBCDIC-to-UTF8 decoders for high-frequency fields like `PTX` (Presentation Text), bypassing the overhead of `java.nio.charset.CharsetDecoder` where possible.

---

## 3. Architectural Decisions & Alternatives Analysis

### Decision 1: Parsing & XML Generation Strategy
The parser must handle files exceeding available heap memory without sacrificing speed.

| Alternative | Evaluation |
| :--- | :--- |
| **A. Pure StAX/Event-Driven** | **Final Choice**. Provides O(1) memory footprint and maximum throughput. Complexity is high but justified by scalability. |
| **B. Chunked Parsing** | Lower complexity, reuses existing SF logic. However, memory usage still fluctuates (O(N)) and state management is brittle. |
| **C. Lazy-Loading JAXB** | Transparent to legacy code, but high object overhead and doesn't solve memory issues during full conversion. |

### Decision 2: Object Lifecycle & Memory Management
Reducing GC pressure is critical for sustained high-throughput processing.

| Alternative | Evaluation |
| :--- | :--- |
| **A. Flyweight & Zero-Copy** | **Final Choice**. Uses `MappedByteBuffer` to reference data without copying. Near-zero memory overhead for payloads. |
| **B. Optimized POJOs** | Reduces footprint via primitive fields, but still incurs object overhead for millions of SFs. Limited scalability. |
| **C. Off-heap Storage** | Eliminates heap pressure entirely, but extremely complex and carries high risk of memory leaks or crashes. |

### Decision 3: Concurrency & I/O Strategy
Leveraging multi-core systems is essential for processing large documents (> 100 MB).

| Alternative | Evaluation |
| :--- | :--- |
| **A. Seek-and-Parse Worker Pool** | **Final Choice**. Master thread finds page boundaries for parallel dispatch. Scalable for large docs with random-access I/O. |
| **B. Pipeline Parallelism** | Simple producer-consumer model (I/O -> Parse -> Write). Speed limited by slowest stage; doesn't scale beyond 3 cores. |
| **C. Map-Reduce Split** | Embarrassingly parallel, but very hard to handle global state (fonts, resource maps) and SFs spanning blocks. |

### Decision 4: Structured Field Instantiation
Reflection overhead becomes significant when processing millions of structured fields.

| Alternative | Evaluation |
| :--- | :--- |
| **A. Pre-computed Static Map** | **Final Choice**. Uses a static `Map<Integer, Supplier<SF>>` for O(1) lookup. Zero reflection overhead during parsing. |
| **B. Reflection Cache** | Caches `Constructor` objects. Faster than pure reflection but still incurs invocation overhead. |
| **C. Generated Switch Case** | Fastest possible performance, but extremely difficult to maintain across 250+ SF types. |

---

## 4. Performance Targets

| Metric | Current (Estimated) | Target |
| :--- | :--- | :--- |
| **Memory Footprint** | ~10x-20x File Size | < 64 MB (Constant) |
| **Throughput (50MB File)** | ~2-5 MB/s | > 50 MB/s |
| **Scalability** | Limited by Heap | Linear with Disk I/O |

## 5. Implementation Priorities

1.  **StAX-based Streaming Writer**: Decouple XML generation from the `AFPDocument` list.
2.  **Event-based CLI**: Update `Afp2Xml` to process SFs in a loop: `parse` -> `write` -> `discard`.
3.  **Reflection Cache**: Optimize SF instantiation.
4.  **NIO Integration**: Switch to `FileChannel` for reading.
