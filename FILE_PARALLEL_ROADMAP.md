# File Parallel Implementation Roadmap

This document outlines the phased strategy and implementation steps for enabling high-throughput parallel conversion of single large AFP files in the Alpheus AFP Parser.

---

## Roadmap Phases

### Phase A: Parallel Scanning
- **Task A.1: Chunk-Based Scanning in `AFPScanner`**: Refactor the scanner to divide files into logical chunks and scan for `0x5A` markers in parallel.
- **Task A.2: Coordinate Boundary Merging**: Implement logic to merge and sort page boundaries found across parallel scanner threads.

### Phase B: Streaming Integration
- **Task B.1: Streaming `ParallelPageParser`**: Transition from returning a `List<StructuredField>` to a producer-consumer model.
- **Task B.2: Bounded Queue & Backpressure**: Implement a `BoundedBlockingQueue` to prevent memory exhaustion during high-speed parsing.

### Phase C: Ordered Buffering
- **Task C.1: Ordered Result Collector**: Implement a collector that ensures XML fragments from different threads are written to the output stream in the original file order.
- **Task C.2: Slotted Buffer Management**: Use a sliding window buffer to hold out-of-order page results until they are ready to be flushed.

### Phase D: CLI & User Control
- **Task D.1: Parallel Execution Flag**: Add the `--parallel` flag to the `Afp2Xml` CLI to toggle multi-threaded single-file conversion.
- **Task D.2: Automatic Core Scaling**: Default the parallelism level to the system's CPU count if not explicitly overridden.

---

## Status Tracking Summary

| Phase | Description | Status |
| :--- | :--- | :---: |
| A | Parallel Scanning | ✅ |
| B | Streaming Integration | ✅ |
| C | Ordered Buffering | ✅ |
| D | CLI & User Control | ✅ |

*Legend: ⏳ Planned, 🚧 In Progress, ✅ Complete*
