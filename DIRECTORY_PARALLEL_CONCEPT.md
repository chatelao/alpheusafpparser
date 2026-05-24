# Directory Parallel Concept: High-Throughput Multi-File Conversion

This document explores strategies to optimize the Alpheus AFP Parser for multi-file conversion on multi-core systems, specifically focusing on directory mode (`-d`).

---

## 1. Current Weaknesses in Directory Processing

### Weakness 1: Sequential File Execution
**Description:** The `Afp2Xml` CLI currently iterates through files in a directory sequentially using a simple `for` loop. This leaves most CPU cores idle when processing a large number of files.
- **Solution A: File-Level Thread Pool (`ExecutorService`)**
  - Dispatch each file conversion task to a fixed-size thread pool.
- **Solution B: Parallel Streams**
  - Use `Arrays.stream(files).parallel().forEach(...)` for simplicity.
- **Solution C: Reactive/Event-Loop Processing**
  - Use a non-blocking framework (e.g., Project Loom or Vert.x) to handle file I/O and parsing.
- **Proposed Solution:** **Solution A (File-Level Thread Pool)**. It provides the best balance of control (throttling), simplicity, and compatibility with the existing blocking I/O architecture.

### Weakness 2: Shared Monitor Contention
**Description:** Global monitors like `MnemonicPerformanceMonitor` use `ConcurrentHashMap` and `AtomicLong`. While thread-safe, high-frequency updates from many threads on the same keys can lead to cache-line contention and reduced throughput.
- **Solution A: Partitioned Counters**
  - Use `LongAdder` instead of `AtomicLong` to reduce contention on the same counter.
- **Solution B: Thread-Local Aggregation**
  - Collect statistics in `ThreadLocal` buffers and merge them into the global map only at the end of each file's processing or at program termination.
- **Solution C: Sampling**
  - Only record performance metrics for a subset of files or structured fields.
- **Proposed Solution:** **Solution B (Thread-Local Aggregation)**. This eliminates contention entirely during the hot loop of parsing and writing.

### Weakness 3: Global Object Pool Contention
**Description:** Pools like `SfiPool` and `TripletPool` use a single `ConcurrentLinkedQueue`. Under high concurrency across many files, the overhead of `poll()` and `offer()` on a single queue can become a bottleneck.
- **Solution A: Per-Thread Pools (ThreadLocal)**
  - Give each thread its own small pool of objects, falling back to a global pool or new allocations only when empty.
- **Solution B: Lock-Free Array-Based Pools**
  - Use specialized high-performance concurrent collections (e.g., Agrona) instead of standard JDK queues.
- **Solution C: Distributed Pools (Work-Stealing)**
  - Implement a work-stealing pool architecture where threads can take from each other's local pools.
- **Proposed Solution:** **Solution A (Per-Thread Pools)**. It is highly effective for reducing synchronization overhead and is relatively simple to implement using `ThreadLocal`.

### Weakness 4: Output Interleaving & Logging
**Description:** Standard output (`System.out`) and error (`System.err`) are shared. Concurrent file processing leads to interleaved XML output (if writing to stdout) and garbled error logs.
- **Solution A: Synchronized Output Wrappers**
  - Wrap `System.out` in a synchronized decorator to ensure line-atomic writes.
- **Solution B: Task-Based Buffering**
  - Buffer the output/logs for a single file in memory or a temporary file, then flush it to the final destination in a single atomic operation once the task completes.
- **Solution C: Structured Logging**
  - Use a logging framework (like Log4j2) that handles thread IDs and ensures log integrity.
- **Proposed Solution:** **Solution B (Task-Based Buffering)**. This is essential for maintaining the integrity of the generated XML when multiple files are being processed, ensuring each file's output remains contiguous.

---

## 2. Summary of Proposed Architecture

| Component | Target Optimization | Proposed Technique |
| :--- | :--- | :--- |
| **Execution Model** | CPU Utilization | `FixedThreadPool` with configurable core count |
| **Statistics** | Monitor Throughput | `ThreadLocal` stats merging |
| **Object Reuse** | Allocation Overhead | `ThreadLocal` local pools + Global spill-over |
| **I/O & Logs** | Data Integrity | Buffered per-task output |

## 3. Implementation Roadmap Strategy

1.  **Phase A:** Introduce a thread-safe `MnemonicPerformanceMonitor` using `ThreadLocal` or `LongAdder`.
2.  **Phase B:** Refactor `Afp2Xml` to use an `ExecutorService` for directory mode.
3.  **Phase C:** Implement per-thread object pooling for `SfiPool`.
4.  **Phase D:** Add CLI flags (e.g., `-t` or `--threads`) to control concurrency.
