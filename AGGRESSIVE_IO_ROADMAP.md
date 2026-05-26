# Aggressive I/O Roadmap

This document outlines the phased implementation plan for maximizing I/O throughput in the Alpheus AFP Parser, as defined in `AGGRESSIVE_IO_DESIGN.md`.

## Status Summary (May 2026)
- **Bottleneck Analysis:** ✅ Completed (Audit 3 & `AGGRESSIVE_IO_DESIGN.md`)
- **Directory Mode Optimization:** 🚧 In-Progress
- **Vectorized Writes:** ⏳ Pending
- **Asynchronous I/O:** ⏳ Pending

---

## Phase 1: Directory Mode Optimization (Strategy E) 🚧
Address the memory and synchronization bottleneck when converting multiple files to stdout.

- ✅ **Implement `OrderedOutputOrchestrator`**:
  - ✅ Core logic and synchronization.
  - ✅ Unit verification.
  - ✅ CLI integration in `Afp2Xml`.
- ✅ **Replace `ByteArrayOutputStream`**: Refactor `Afp2Xml` directory mode to stream XML fragments to stdout instead of buffering entire files.
- ⏳ **Page-Level Streaming**: Ensure fragments are flushed at the page or structured field level to minimize memory footprint to $O(PageSize \times Threads)$.

## Phase 2: Vectorized Writes & Buffer Optimization (Strategy C) ⏳
Enhance the efficiency of fragment flushing in both sequential and parallel modes.

- ⏳ **Enhance `OrderedResultCollector`**: Implement batching of consecutive fragments using `FileChannel.write(ByteBuffer[])`.
- ⏳ **Direct Buffer Integration**: Refactor writers to use `DirectByteBuffer` to enable zero-copy transfers to the kernel.
- ⏳ **Vectorized Indentation**: (From `PTX_OPTIMIZATION_ROADMAP.md`) Use pre-filled buffers for XML indentation to avoid redundant string creation.

## Phase 3: Memory-Mapped I/O for Output (Strategy B) ⏳
Optimize large single-file conversions by mapping output files directly into memory.

- ⏳ **Output Mapping Prototype**: Implement a prototype for `MappedByteBuffer`-based output in `AfpJacksonXmlWriter`.
- ⏳ **Size Estimation Logic**: Develop heuristics to estimate XML output size for efficient pre-allocation and mapping.
- ⏳ **Segmented Mapping**: Handle multi-gigabyte XML outputs by mapping the file in segments.

## Phase 4: Asynchronous & Non-Blocking I/O (Strategy A) ⏳
Decouple serialization from I/O to improve performance on high-latency storage (Cloud/NAS).

- ⏳ **NIO.2 Integration**: Transition `OrderedResultCollector` to use `AsynchronousFileChannel`.
- ⏳ **Completion Handlers**: Implement efficient buffer recycling using NIO.2 completion handlers.
- ⏳ **Pressure-Aware Serialization**: Implement back-pressure mechanisms to pause serialization when the I/O queue is full.

## Phase 5: Zero-Copy Ring-Buffer (Strategy D) ⏳
Extreme performance optimization for massive-scale conversion.

- ⏳ **Shared Ring-Buffer**: Implement a Disruptor-like ring buffer for page-aligned `DirectByteBuffer`s.
- ⏳ **Dedicated I/O Consumer**: Move all disk writes to a single dedicated thread pinned to a specific core to minimize context switches.

---

## Implementation Strategy
1. **Prioritization**: Focus on **Strategy E** (Directory Mode) first, as it addresses the most significant memory risk identified in `Afp2Xml`.
2. **Verification**: Every I/O optimization must be verified using `PerformanceRegressionTest` to ensure measurable gains without regressing XML validity.
3. **Guardrails**: Ensure that aggressive I/O strategies are optional and can be toggled via CLI flags (e.g., `--aggressive-io`).
