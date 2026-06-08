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
  - ✅ **4.2.2 Baseline Comparison:** Compare results against `PERFORMANCE_BASELINE.md` and `OPTIMIZATION_SUMMARY.md` baselines.
  - ✅ **4.2.3.1 Hotspot Identification:**
    - ✅ **4.2.3.1.1 Profiler Integration:** Integrate JFR (Java Flight Recorder) or async-profiler into the benchmark runner.
    - ✅ **4.2.3.1.2 Bottleneck Analysis:** Identify bottlenecks in the handler interface path (e.g., reflection, mnemonic extraction).
    - ✅ **4.2.3.1.3 Optimization Cycle:** Apply targeted fixes and verify throughput gains.
  - ✅ **4.2.3.2 Allocation Tracking:**
    - ✅ **4.2.3.2.1 Automated Tracking:** Implement `ThreadMXBean` based allocation logging in benchmarks.
    - ✅ **4.2.3.2.2 Regression Verification:** Verify that the decoupled path does not introduce additional garbage collection pressure (>5% overhead).
  - ✅ **4.2.4 Cold-Start Performance Analysis:** Capture performance metrics for non-warmed-up JVM execution (critical for CLI).
3.  **Concurrency & Stability:**
  - ✅ **4.3.1.1 Multi-threaded Stress Test:**
    - ✅ **4.3.1.1.1 1,000 Page Benchmark:** Baseline stability test for medium documents.
    - ✅ **4.3.1.1.2 10,000 Page Stress Test:** High-concurrency validation.
    - ✅ **4.3.1.1.3 100,000 Page Endurance Test:** Verify long-running stability and GC behavior.
  - ✅ **4.3.1.2 Thread-Safety Audit:** Verify thread-safety of shared resource pools (SFI, Triplet, SF pools).
  - ✅ **4.3.1.3 Global State Consistency:**
    - ✅ **4.3.1.3.1 State Isolation:** Ensure `AFPParserConfiguration` clones perform deep copies of maps to prevent page-local state leakage.
    - ✅ **4.3.1.3.2 Resource Inheritance:** Verify that handlers correctly inherit preamble state in parallel mode.
  - ⏳ **4.3.2.1 Heap Dump Analysis:**
    - ✅ **4.3.2.1.1 100MB+ Scaling Test:** Execute conversion on synthetic 100MB+ files to stress memory management.
    - ✅ **4.3.2.1.2 Capture Dumps:**
      - ✅ **4.3.2.1.2.1: Implement programmatic heap dump utility.**
      - ✅ **4.3.2.1.2.2: Integrate utility into `Afp2XmlBenchmarkTest`.**
    - ✅ **4.3.2.1.3 Leak Analysis:**
      - ✅ **4.3.2.1.3.1:** Automated "Surviving Object" count for Handlers after GC.
      - ✅ **4.3.2.1.3.2:** Comparative analysis of heap histograms (Before vs. After).
    - ✅ **4.3.2.1.4 Verification:** Verify fix efficacy for any identified leaks. (Verified no leaks for Handlers, SFI, and SFBaseData).
  - ✅ **4.3.2.2 Native Memory Tracking:** Monitor DirectBuffer usage when `--aggressive-io` is enabled.
  - ✅ **4.3.3 Handler Lifecycle Verification:** Verify that all handler instances are correctly closed in both sequential and parallel modes.

## Phase 5: API Consolidation
**Goal:** Finalize the public and internal APIs after decoupling.

1.  **Standardize Handler Access:**
    - ✅ **4.4.1 Remove Deprecated Methods:** Remove `writeField` from `AfpJacksonXmlWriter`.
    - ✅ **4.4.2 Update Callers:** Standardize all unit tests and internal callers to use `handle(sf)`.
