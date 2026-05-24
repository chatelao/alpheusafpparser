# Directory Parallel Implementation Roadmap

This document outlines the phased strategy and implementation steps for enabling high-throughput parallel directory processing in the Alpheus AFP Parser.

---

## Roadmap Phases

### Phase A: Thread-Safe Performance Monitoring
- **Task A.1: Refactor `MnemonicPerformanceMonitor`**: Transition from `AtomicLong` to `LongAdder` or `ThreadLocal` counters to reduce cache-line contention.
- **Task A.2: Thread-Local Aggregation**: Implement a mechanism to collect statistics per-thread and merge them into the global view at the end of execution.

### Phase B: Parallel Execution Engine
- **Task B.1: Refactor `Afp2Xml` Directory Mode**: Replace the sequential `for` loop in directory processing with a `java.util.concurrent.ExecutorService`.
- **Task B.2: Implement Task-Based Buffering**: Ensure that XML output for each file is buffered and written atomically to prevent interleaving.
- **Task B.3: Error Handling**: Enhance error reporting to include filename/thread context for concurrent failures.

### Phase C: Per-Thread Object Pooling
- **Task C.1: L1/L2 Pooling for `SfiPool`**: Implement `ThreadLocal` L1 pools for `StructuredFieldIntroducer` to eliminate global queue contention.
- **Task C.2: Expand Pooling to `StructuredFieldPool`**: Apply the same two-tier pooling strategy to all high-churn object pools.

### Phase D: CLI & User Control
- **Task D.1: Concurrency CLI Flags**: Add `-t` / `--threads` flags to the `Afp2Xml` CLI to allow user-defined parallelism.
- **Task D.2: Auto-Detection**: Implement automatic thread-count detection based on `availableProcessors()`.

---

## Status Tracking Summary

| Phase | Description | Status |
| :--- | :--- | :---: |
| A | Thread-Safe Performance Monitoring | ⏳ |
| B | Parallel Execution Engine | ⏳ |
| C | Per-Thread Object Pooling | ⏳ |
| D | CLI & User Control | ⏳ |

*Legend: ⏳ Planned, 🚧 In Progress, ✅ Complete*
