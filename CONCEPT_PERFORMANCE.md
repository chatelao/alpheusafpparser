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

## 3. Performance Targets

| Metric | Current (Estimated) | Target |
| :--- | :--- | :--- |
| **Memory Footprint** | ~10x-20x File Size | < 64 MB (Constant) |
| **Throughput (50MB File)** | ~2-5 MB/s | > 50 MB/s |
| **Scalability** | Limited by Heap | Linear with Disk I/O |

## 4. Implementation Priorities

1.  **StAX-based Streaming Writer**: Decouple XML generation from the `AFPDocument` list.
2.  **Event-based CLI**: Update `Afp2Xml` to process SFs in a loop: `parse` -> `write` -> `discard`.
3.  **Reflection Cache**: Optimize SF instantiation.
4.  **NIO Integration**: Switch to `FileChannel` for reading.
