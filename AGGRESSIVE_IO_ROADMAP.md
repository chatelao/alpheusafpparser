# Aggressive I/O Roadmap

This document outlines the phased implementation plan for maximizing I/O throughput in the Alpheus AFP Parser, as defined in `AGGRESSIVE_IO_DESIGN.md`.

## Status Summary (May 2026)
- **Bottleneck Analysis:** ✅ Completed (Audit 3 & `AGGRESSIVE_IO_DESIGN.md`)
- **Directory Mode Optimization:** ✅ Completed
- **Vectorized Writes:** ⏳ Pending
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

## Phase 2: Vectorized Writes & Buffer Optimization (Strategy C) ⏳
Enhance the efficiency of fragment flushing in both sequential and parallel modes.

- ⏳ **Enhance `OrderedResultCollector`**:
  - ✅ **ByteBuffer-based API**: Refactor orchestrators to accept `ByteBuffer` instead of `byte[]`.
  - ✅ **Fragment Batching**: Logic to group consecutive ready fragments.
  - ⏳ **Vectorized FileChannel Writes**: Use `write(ByteBuffer[])` for flushing batches.
- ⏳ **Direct Buffer Integration**:
  - ⏳ **Direct Buffer Pooling**: Implement a recycler for `DirectByteBuffer`s to avoid allocation overhead.
  - ⏳ Refactor writers to use `DirectByteBuffer` to enable zero-copy transfers to the kernel.
- ⏳ **Vectorized Indentation**: (From `PTX_OPTIMIZATION_ROADMAP.md`) Use pre-filled buffers for XML indentation to avoid redundant string creation.

## Phase 3: Memory-Mapped I/O for Output (Strategy B) ⏳
Optimize large single-file conversions by mapping output files directly into memory.

- ⏳ **Output Mapping Prototype**: Implement a prototype for `MappedByteBuffer`-based output in `AfpJacksonXmlWriter`.
- ⏳ **Size Estimation Logic**: Develop heuristics to estimate XML output size based on AFP structured field counts.
- ⏳ **Mapping Segment Manager**: Coordinate multiple `MappedByteBuffer` segments for files > 2GB.
- ⏳ **Atomic Pre-allocation**: Efficiently grow output files on the filesystem.

## Phase 4: Asynchronous & Non-Blocking I/O (Strategy A) ⏳
Decouple serialization from I/O to improve performance on high-latency storage (Cloud/NAS).

- ⏳ **NIO.2 Integration**: Transition `OrderedResultCollector` to use `AsynchronousFileChannel`.
- ⏳ **Completion Handlers**: Implement efficient buffer recycling using NIO.2 completion handlers.
- ⏳ **Pressure-Aware Serialization**: Implement back-pressure mechanisms to pause serialization when the I/O queue is full.

## Phase 5: Zero-Copy Ring-Buffer (Strategy D) ⏳
Extreme performance optimization for massive-scale conversion.

- ⏳ **Shared Ring-Buffer**: Implement a Disruptor-like ring buffer for page-aligned `DirectByteBuffer`s.
- ⏳ **Dedicated I/O Consumer**: Move all disk writes to a single dedicated thread pinned to a specific core to minimize context switches.

## Phase 6: StAX Writer Rework (Integration) ⏳
To support Zero-Copy strategies, the writers (specifically `AfpJacksonXmlWriter`) should be refactored to work directly with `ByteBuffer`s or `ByteBuf`s (similar to Netty) instead of `OutputStream`.

- ⏳ **ByteBuffer-Based Writing**: Refactor `AfpJacksonXmlWriter` and other StAX writers to work directly with `ByteBuffer`s or `ByteBuf`s (similar to Netty) instead of `OutputStream`.
- ⏳ **Zero-Copy Integration**: Support Zero-Copy strategies by allowing the StAX writer to write into memory that is already pre-mapped to the kernel.

---

## Implementation Strategy
1. **Prioritization**: Focus on **Strategy E** (Directory Mode) first, as it addresses the most significant memory risk identified in `Afp2Xml`.
2. **Verification**: Every I/O optimization must be verified using `PerformanceRegressionTest` to ensure measurable gains without regressing XML validity.
3. **Guardrails**: Ensure that aggressive I/O strategies are optional and can be toggled via CLI flags (e.g., `--aggressive-io`).
