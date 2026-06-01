# Decoupling AFP Parser from XML Generation - Roadmap

This roadmap outlines the serial implementation plan for decoupling the parser from XML generation.
## Phase 4: Performance Validation & Regression
**Goal:** Ensure zero performance loss and bit-for-bit XML equality.

1.  **Equality Check (Regression Testing):**
  - ✅ **4.1.1 Identify Reference Suite:** Select a representative set of AFP files (PTOCA, GOCA, IOCA, BCOCA).
  - ✅ **4.1.2 Archive Gold Standards:** Generate and store reference XML outputs from a known stable version.
  - ✅ **4.1.3 Automated Comparison:** Implement `Afp2XmlRegressionTest.java` for automated XML diffing.
  - ✅ **4.1.4 Mode Verification:** Verify bit-for-bit equality for both sequential and parallel (`-p`) modes.
2.  **Performance Benchmarking:**
  - ✅ **4.2.1.1 Warm-up and JIT Stabilization:** Implement a benchmark runner that performs adequate warm-up cycles.
  - ✅ **4.2.1.2 Throughput Measurement:** Capture MB/s and SF/s for sequential vs. parallel modes.
  - ✅ **4.2.1.3 Resource Usage Logging:** Record Peak Heap and CPU utilization during runs.
  - ⏳ **4.2.2 Baseline Comparison:** Compare results against `PERFORMANCE_CONCEPT.md` (or `OPTIMIZATION_SUMMARY.md`) baselines.
  - ⏳ **4.2.3.1 Hotspot Identification:** Use async-profiler to find bottlenecks in the handler interface path.
  - ⏳ **4.2.3.2 Allocation Tracking:**
    - ✅ **4.2.3.2.1 Automated Tracking:** Implement `ThreadMXBean` based allocation logging in benchmarks.
    - ⏳ **4.2.3.2.2 Regression Verification:** Verify that the decoupled path does not introduce additional garbage collection pressure (>5% overhead).
3.  **Concurrency & Stability:**
  - ✅ **4.3.1.1 Multi-threaded Stress Test:**
    - ✅ **4.3.1.1.1 1,000 Page Benchmark:** Baseline stability test for medium documents.
    - ✅ **4.3.1.1.2 10,000 Page Stress Test:** High-concurrency validation.
    - ✅ **4.3.1.1.3 100,000 Page Endurance Test:** Verify long-running stability and GC behavior.
  - ✅ **4.3.1.2 Thread-Safety Audit:** Verify thread-safety of shared resource pools (SFI, Triplet, SF pools).
  - ⏳ **4.3.1.3 Global State Consistency:**
    - ✅ **4.3.1.3.1 State Isolation:** Ensure `AFPParserConfiguration` clones perform deep copies of maps to prevent page-local state leakage.
    - ✅ **4.3.1.3.2 Resource Inheritance:** Verify that handlers correctly inherit preamble state in parallel mode.
  - ⏳ **4.3.2.1 Heap Dump Analysis:** Perform memory analysis on 100MB+ file conversions to ensure no leaks.
  - ✅ **4.3.2.2 Native Memory Tracking:** Monitor DirectBuffer usage when `--aggressive-io` is enabled.
