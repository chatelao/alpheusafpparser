# Directory Parallel Design: High-Throughput Multi-File Conversion

This document details the technical design for optimizing the Alpheus AFP Parser's directory mode (`-d`) to leverage multi-core systems through parallel execution.

---

## 1. Execution Model: File-Level Parallelism

To maximize CPU utilization while maintaining control over system resources, the directory processing engine will transition from a sequential loop to a thread-pool-based execution model.

### Fixed-Size Thread Pool
- **Implementation**: Utilize `java.util.concurrent.ExecutorService` with a `FixedThreadPool`.
- **Concurrency Control**: A new CLI flag `-t` or `--threads` will allow users to specify the number of worker threads. If not specified, it will default to the number of available processors (`Runtime.getRuntime().availableProcessors()`).
- **Task Granularity**: Each file in the target directory is treated as a single, independent task submitted to the executor.

---

## 2. Contention Reduction Strategies

High concurrency introduces synchronization overhead on shared resources. The following strategies are designed to eliminate bottlenecks in the "hot loop" of parsing.

### A. Thread-Local Statistics Aggregation
Global monitors (e.g., `MnemonicPerformanceMonitor`) currently suffer from cache-line contention due to multiple threads updating shared atomic counters.
- **Design**: Each worker thread maintains its own `ThreadLocal` performance buffer.
- **Merge Logic**: Statistics are aggregated locally during the parsing of a file. Upon task completion, the local results are merged into the global monitor using a single synchronized operation or `LongAdder`.
- **Benefit**: Zero synchronization overhead during the parsing/writing phase.

### B. Per-Thread Object Pooling
The global `SfiPool` and `StructuredFieldPool` can become bottlenecks if many threads attempt to `poll()` and `offer()` simultaneously.
- **Design**: Implement a two-tier pooling strategy:
  1. **L1 (ThreadLocal)**: A small, private pool of objects (e.g., 32-64 SFIs) reserved for each thread.
  2. **L2 (Global)**: A larger shared pool that threads fall back to if their L1 pool is empty, or where they "spill over" if their L1 pool is full.
- **Benefit**: Dramatically reduces contention on the global concurrent queue.

---

## 3. Output & Data Integrity

Parallel execution must not compromise the integrity of the generated XML output or the readability of diagnostic logs.

### Task-Based Output Buffering
Standard output (`System.out`) is shared. Concurrent writes would lead to interleaved XML fragments, resulting in malformed files.
- **Design**: For each file conversion task, the `AfpJacksonXmlWriter` will write to an internal `ByteArrayOutputStream` or a temporary file.
- **Atomic Flush**: Once a file is fully processed, the entire buffered content is flushed to the final destination (file or stdout) in a single atomic operation.
- **Logging**: Error logs (`System.err`) will be prefixed with the thread ID or filename to ensure traceability in a multi-threaded environment.

---

## 4. Summary of Design Decisions

| Component | Design Pattern | Impact |
| :--- | :--- | :--- |
| **Scheduler** | `ExecutorService` (Fixed) | Predictable CPU/Memory usage. |
| **Metrics** | `ThreadLocal` + Global Merge | Lock-free performance monitoring. |
| **Memory** | Two-Tier Object Pooling | Reduced GC and pool contention. |
| **I/O** | Per-Task Buffering | Guaranteed XML structural integrity. |
