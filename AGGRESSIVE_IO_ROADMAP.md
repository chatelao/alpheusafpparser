# Aggressive I/O Roadmap

This document outlines the phased implementation plan for maximizing I/O throughput in the Alpheus AFP Parser, as defined in `AGGRESSIVE_IO_DESIGN.md`.

## Status Summary (June 2026)
- **Bottleneck Analysis:** ✅ Completed (Audit 3 & `AGGRESSIVE_IO_DESIGN.md`)
- **Directory Mode Optimization:** ✅ Completed
- **Vectorized Writes:** ✅ Completed
- **File-system Pre-allocation:** ✅ Completed
- **Asynchronous I/O:** ⏳ Pending

---

## Phase 1: Directory Mode Optimization (Strategy E) ✅
Address the memory and synchronization bottleneck when converting multiple files to stdout.

- ✅ **Implement `OrderedOutputOrchestrator`**:
  - ✅ Core logic and synchronization.
  - ✅ Unit verification.
  - ✅ CLI integration in `Afp2Xml`.
- ✅ **Replace `ByteArrayOutputStream`**: Refactor `Afp2Xml` directory mode to stream XML fragments to stdout instead of buffering entire files.
- ✅ **Page-Level Streaming**:
  - ✅ Ensure fragments are flushed at the page or structured field level to minimize memory footprint to $O(PageSize \times Threads)$.
  - ✅ **Implement back-pressure**: Added memory guardrails in `OrderedOutputOrchestrator` and `OrderedResultCollector` to prevent OOM during out-of-order buffering.

## Phase 2: Vectorized Writes & Buffer Optimization (Strategy C) 🚧
Enhance the efficiency of fragment flushing in both sequential and parallel modes.

- ✅ **Enhance `OrderedResultCollector`**:
  - ✅ **ByteBuffer-based API**: Refactor orchestrators to accept `ByteBuffer` instead of `byte[]`.
  - ✅ **Fragment Batching**: Logic to group consecutive ready fragments.
  - ✅ **Vectorized FileChannel Writes**: Use `write(ByteBuffer[])` for flushing batches.
- ✅ **Direct Buffer Integration**:
  - ✅ **Direct Buffer Pooling**: Implement a recycler for `DirectByteBuffer`s to avoid allocation overhead.
  - ✅ **Writers Integration**: Refactor `AfpJacksonXmlWriter` and orchestrators to use `DirectBufferOutputStream` and `ByteBuffer` for zero-copy transfers.
- ✅ **Typed Access API**: (From `STAX2_GAP.md`) Implement direct numeric writing in `AfpJacksonXmlWriter` to eliminate `String.valueOf()` overhead.
- ✅ **Optimized Sanitization**: Refactor `SanitizingXMLStreamWriter` to avoid redundant string allocations when no sanitization is required.
- ✅ **Vectorized Indentation**: (From `PTX_OPTIMIZATION_ROADMAP.md`) Use pre-filled buffers for XML indentation to avoid redundant string creation. (Implemented via `XmlIndenter`).

## Phase 3: Memory-Mapped I/O for Output (Strategy B) ⏳
Optimize large single-file conversions by mapping output files directly into memory.

- ⏳ **3.1. Output Mapping Prototype**: Implement a prototype for `MappedByteBuffer`-based output in `AfpJacksonXmlWriter`.
  - ⏳ **3.1.1. Single-segment Mapping**: Implement fixed-size mapping for medium files (< 2GB).
    - ✅ **3.1.1.1. Utility implementation**: Create `MappedBufferOutputStream` to wrap `MappedByteBuffer`.
    - ⏳ **3.1.1.2. Integration**: Integrate `MappedBufferOutputStream` into `Afp2Xml` and `ParallelAfpConverter`.
  - ⏳ **3.1.2. Size-aware re-mapping**: Logic to unmap and re-map with larger capacity when estimates are exceeded.
  - ⏳ **3.1.3. Benchmarking**: Comparative analysis of MMap vs. standard NIO on different SSD/HDD tiers.
- ⏳ **Size Estimation Logic**:
  - ✅ **Heuristic Analysis**: Analyze correlation between AFP structured field sizes (PTX, GAD, etc.) and their XML representation. (Integrated in `PTXPerformanceMonitor`).
  - ✅ **Static Estimator**: Implement a basic multiplier-based estimator for non-PTOCA fields. (See `SFSizeEstimator`).
  - ✅ **Dynamic PTOCA Estimator**: Leverage `PTXPerformanceMonitor` data to predict XML size for PTOCA sequences based on character count and control sequences. (Ratio-based Estimation implemented).
- ⏳ **Mapping Segment Manager**: Coordinate multiple `MappedByteBuffer` segments for files > 2GB.
- ✅ **Atomic Pre-allocation**: Efficiently grow output files and in-memory buffers.
  - ✅ **In-memory Buffer Pre-allocation**: Use `SFSizeEstimator` to pre-size `ByteArrayOutputStream` in parallel and filtered paths.
  - ✅ **File-system Pre-allocation**: Use `SFSizeEstimator` to determine initial file size for physical disks. (Enabled via `--aggressive-io` CLI flag).
  - 🚧 **Allocation Strategy**: Evaluate `fallocate` (Linux) vs. zero-fill for efficient growth. (Initial zero-fill implemented in CLI).

## Phase 4: Asynchronous & Non-Blocking I/O (Strategy A) ⏳
Decouple serialization from I/O to improve performance on high-latency storage (Cloud/NAS).

- ⏳ **4.1. NIO.2 Integration**: Transition `OrderedResultCollector` to use `AsynchronousFileChannel`.
  - ⏳ **4.1.1. Buffer lifecycle**: Manage buffer ownership between worker threads and NIO.2 handlers.
  - ⏳ **4.1.2. Error handling**: Implement robust error propagation for async failures.
- ⏳ **4.2. Completion Handlers**: Implement efficient buffer recycling using NIO.2 completion handlers.
- ✅ **4.3. Async OutputStream Wrapper**: Create an `OutputStream` implementation that uses `AsynchronousFileChannel` for non-blocking background writes. (Implemented as `AsynchronousBufferOutputStream`).
- ✅ **4.4. Pressure-Aware Serialization**: Implement back-pressure mechanisms to pause serialization when the I/O queue is full. (Implemented via memory-aware sliding window in `OrderedResultCollector` and `OrderedOutputOrchestrator`).

## Phase 5: Zero-Copy Ring-Buffer (Strategy D) ⏳
Extreme performance optimization for massive-scale conversion.

- ⏳ **5.1. Shared Ring-Buffer Infrastructure**:
  - ⏳ **5.1.1. Ring-Buffer Interface**: Define the producer/consumer contract for `DirectByteBuffer` slots.
  - ⏳ **5.1.2. Contention Analysis**: Audit `DirectBufferPool` for lock contention under high-frequency ring-buffer usage.
  - ⏳ **5.1.3. Page-Aligned Allocator**: Ensure ring-buffer slots are aligned to physical memory pages (typically 4KB) for O_DIRECT compatibility.
- ⏳ **5.2. Dedicated I/O Consumer**: Move all disk writes to a single dedicated thread pinned to a specific core to minimize context switches.
- ⏳ **5.3. Wait-Free Synchronization**: Implement a wait-free sequence barrier for multi-producer coordination.

## Phase 6: StAX Writer Rework (Integration) 🚧
To support Zero-Copy strategies, the writers (specifically `AfpJacksonXmlWriter`) should be refactored to work directly with `ByteBuffer`s or `ByteBuf`s (similar to Netty) instead of `OutputStream`.

- ✅ **6.1. ByteBuffer-backed OutputStream**: Implement a pooled `OutputStream` that writes directly into `DirectByteBuffer`s from `DirectBufferPool`. (Implemented as `DirectBufferOutputStream`).
- ✅ **6.2. Streaming Vectorized Output**: Enhance `DirectBufferOutputStream` to support periodic flushes to a `GatheringByteChannel`, enabling high-performance sequential I/O with minimal copying.
- ⏳ **6.3. Async StAX Writer Research**: Evaluate Aalto's `AsyncXMLStreamWriter` or similar extensions for non-blocking `ByteBuffer` output.
- ⏳ **6.4. Zero-Copy Writer Implementation**: Prototype a version of `AfpJacksonXmlWriter` that eliminates `OutputStream` overhead by writing directly into memory that is already pre-mapped to the kernel.
  - ⏳ **6.4.1. Direct ByteBuffer access in AfpJacksonXmlWriter**: Expose methods to write structured fields directly to a provided `ByteBuffer`.
  - ⏳ **6.4.2. Structured Field fragment pooling**: Reuse pre-serialized XML fragments for repeating structured fields.
  - ⏳ **6.4.3. Zero-copy fragment merging**: Use `FileChannel.transferTo` or similar for merging massive XML fragments.

---

## Implementation Strategy
1. **Prioritization**: Focus on **Strategy E** (Directory Mode) first, as it addresses the most significant memory risk identified in `Afp2Xml`.
2. **Verification**: Every I/O optimization must be verified using `PerformanceRegressionTest` to ensure measurable gains without regressing XML validity.
3. **Guardrails**: Ensure that aggressive I/O strategies are optional and can be toggled via CLI flags (e.g., `--aggressive-io`).
