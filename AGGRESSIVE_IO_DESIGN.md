# Aggressive I/O Strategies for High-Throughput AFP-to-XML Conversion

This document outlines architectural approaches to maximize I/O throughput during the write phase of AFP-to-XML conversion, particularly for high-volume PTOCA and directory processing.

## 1. Strategy A: Asynchronous Non-Blocking Writing (NIO.2)
**Concept**: Decouple the serialization logic from the actual disk I/O using `AsynchronousFileChannel`.

- **Implementation**:
  - Worker threads serialize XML fragments into `DirectByteBuffer` instead of `ByteArrayOutputStream`.
  - An `OrderedResultCollector` orchestrates `AsynchronousFileChannel.write()` calls.
  - Using a completion handler to recycle buffers back into a pool.
- **Pros**: Zero-copy (if using Direct Buffers), doesn't block worker threads on slow storage.
- **Cons**: Complexity in managing buffer life cycles and ordering.

## 2. Strategy B: Memory-Mapped I/O (MMap) for Output
**Concept**: Map the output file into the virtual memory space of the process.

- **Implementation**:
  - Pre-allocate the output file to an estimated size.
  - Map segments of the file as `MappedByteBuffer`.
  - Worker threads write their XML fragments directly into their assigned memory regions.
- **Pros**: Operating system handles flushing to disk efficiently via demand paging; extremely low latency for writes.
- **Cons**: Difficult to estimate XML size beforehand; mapping/unmapping overhead for many small files.

## 3. Strategy C: Vectorized Writes (Gathering I/O)
**Concept**: Use `FileChannel.write(ByteBuffer[])` to write multiple fragments in a single system call.

- **Implementation**:
  - Instead of writing one fragment at a time in `OrderedResultCollector.put()`, batch multiple consecutive fragments.
  - Pass an array of `ByteBuffer`s to the kernel.
- **Pros**: Reduces context switches between user and kernel space.
- **Cons**: Only beneficial when multiple fragments are ready simultaneously (high parallelism).

## 4. Strategy D: Shared Ring-Buffer with Zero-Copy Page Alignment
**Concept**: Use a high-performance Disruptor-like ring buffer for cross-thread communication.

- **Implementation**:
  - A fixed-size ring buffer of page-aligned `DirectByteBuffer`s.
  - Producers (parsers/writers) claim slots and fill them.
  - A single dedicated consumer thread flushes them using `FileChannel.write()`.
- **Pros**: Minimal synchronization overhead; cache-friendly.
- **Cons**: High implementation complexity.

## 5. Strategy E: Page-Level Ordered Streaming (Directory Mode)
**Concept**: Replace the `ByteArrayOutputStream` bottleneck in directory-to-stdout mode.

- **Implementation**:
  - Introduce an `OrderedOutputOrchestrator` that maintains a sliding window of open `BufferedOutputStream` fragments.
  - Threads write to their local buffer.
  - The orchestrator flushes to the shared `OutputStream` only when a complete file or page is ready, using `transferTo` if possible.
- **Pros**: Reduces memory footprint from O(FileSize) to O(PageSize * Threads).
- **Cons**: Requires synchronization on the final output stream.

## Comparison Table

| Strategy | Complexity | Potential Gain | Best Use Case |
| :--- | :---: | :---: | :--- |
| **Asynchronous NIO** | High | High | High-latency storage (Cloud/NAS) |
| **Memory-Mapped** | Medium | Extreme | Large single-file conversion |
| **Vectorized Writes**| Low | Medium | Multi-page PTX-heavy docs |
| **Ring-Buffer** | Extreme | High | Massive scale, ultra-low jitter |
| **Page-Level Stream**| Medium | High | Directory mode / Stdout mode |

## 6. Architectural Integration Review

### 6.1. Integrating with `OrderedResultCollector`
- **Strategy C (Vectorized Writes)** can be implemented by modifying `OrderedResultCollector` to batch multiple ready fragments into a `ByteBuffer[]` and using `FileChannel.write()`. This would reduce the overhead of repeated `out.write()` and `out.flush()` calls.
- **Strategy A (Asynchronous NIO)** would require `OrderedResultCollector` to transition from `OutputStream` to `AsynchronousFileChannel`.

### 6.2. Integrating with `Afp2Xml.java`
- **Strategy E (Page-Level Ordered Streaming)** is the most critical for Directory Mode. Currently, `Afp2Xml` uses a `ByteArrayOutputStream` to buffer the *entire* file's XML before writing to stdout.
- **Refinement**: Implement a `ThreadSafeOrderedWriter` that can accept fragments (pages) from multiple files and write them to stdout in a way that keeps files contiguous but streams the content, drastically reducing memory pressure.

### 6.3. Integrating with `AfpJacksonXmlWriter`
- To support Zero-Copy strategies, the writers should be refactored to work directly with `ByteBuffer`s or `ByteBuf`s (similar to Netty) instead of `OutputStream`. This would allow the StAX writer to write into memory that is already pre-mapped to the kernel.

## Recommendation
For the Alpheus AFP Parser, **Strategy E (Page-Level Ordered Streaming)** combined with **Strategy C (Vectorized Writes)** offers the best balance of performance gains and architectural maintainability.
