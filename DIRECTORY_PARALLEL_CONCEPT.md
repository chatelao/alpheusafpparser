# Directory Parallel Concept: High-Throughput Multi-File Conversion

This document describes the strategies implemented in the Alpheus AFP Parser to optimize multi-file conversion on multi-core systems, specifically focusing on directory mode (`-d`).

---

## 1. Implemented Optimization Techniques

### Technique 1: File-Level Thread Pool
**Description:** The `Afp2Xml` CLI utilizes a fixed-size thread pool to process multiple AFP files concurrently. This ensures maximum CPU utilization when converting large batches of documents.
- **Implementation:** `Executors.newFixedThreadPool(threadCount)` dispatches file conversion tasks.
- **Control:** The thread count can be explicitly set via the `-t` or `--threads` CLI flag, defaulting to the number of available processors.

### Technique 2: Thread-Local Performance Monitoring
**Description:** High-frequency performance monitoring for mnemonics and PTOCA control sequences is designed to avoid contention between threads.
- **Implementation:** `MnemonicPerformanceMonitor` and `PTXPerformanceMonitor` use `ThreadLocal` maps for zero-contention local statistics collection.
- **Aggregation:** Local statistics are merged into global `LongAdder` counters only at the end of each task or program execution via the `merge()` method, ensuring accurate results without compromising throughput.

### Technique 3: Two-Tier Object Pooling
**Description:** Allocation-heavy objects like Structured Field Introducers (SFIs) and Structured Fields themselves are managed via a two-tier pooling strategy to minimize garbage collection and lock contention.
- **L1 Pool (ThreadLocal):** Each thread maintains a small, private pool for immediate reuse without synchronization.
- **L2 Pool (Global):** A concurrent global pool (`ConcurrentLinkedQueue`) handles spill-over and sharing between threads when local pools are exhausted.
- **Applied to:** `SfiPool`, `StructuredFieldPool`, `TripletPool`, `RepeatingGroupPool`, `StructuredFieldBaseDataPool`, `DrawingOrderPool`, `IpdSegmentPool`, and `ControlSequencePool`.

### Technique 4: Atomic Task-Based Output
**Description:** To prevent interleaving of XML output and logging when multiple threads write to the same destination (e.g., `stdout`), the CLI implements task-based buffering.
- **Buffering:** When writing to stdout (`-`), each task buffers its entire XML output in a `ByteArrayOutputStream`.
- **Atomic Flush:** The buffer is flushed to `System.out` in a single synchronized block upon task completion.
- **Logging:** Error messages are synchronized on `System.err` and include thread context for traceability.

---

## 2. Summary of Implemented Architecture

| Component | Optimization | Technique |
| :--- | :--- | :--- |
| **Execution Model** | CPU Utilization | `FixedThreadPool` with configurable core count (`-t`) |
| **Statistics** | Monitor Throughput | `ThreadLocal` aggregation + `LongAdder` global counters |
| **Object Reuse** | Allocation Overhead | Two-tier `ThreadLocal` (L1) and Global (L2) pools |
| **I/O & Logs** | Data Integrity | Buffered per-task output with synchronized flushing |
