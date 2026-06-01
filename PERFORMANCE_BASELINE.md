# Performance Baseline - Alpheus AFP Parser

This document establishes the performance baseline for AFP to XML conversion as of June 2026.

## Current Benchmark Results (June 2026)

The following results were captured using `Afp2XmlBenchmarkTest` on `src/test/resources/afp/large_ibm273.afp` (9.98 MB).

| Mode | Avg. Duration (s) | Throughput (MB/s) | Peak Heap (MB) | Total Allocated (MB) |
| :--- | :---: | :---: | :---: | :---: |
| **Sequential** | 0.723 | 13.81 | 81.66 | 888.39 |
| **Parallel** | 0.844 | 11.82 | 87.11 | 708.99 |

*Note: Parallel mode performance may vary depending on the environment and number of available cores. In this specific test run, sequential mode was slightly faster due to the small size of the test file and overhead of thread management.*

## Historical Baselines

| Date | Milestone | Throughput (MB/s) | Notes |
| :--- | :--- | :---: | :--- |
| June 2026 | Optimization Post-Audit | ~15.00 | Measured on 9MB IBM273 file after SLOW-01 through SLOW-05 fixes. |
| June 2026 | Initial Benchmark | ~11.00 | Baseline before major decoupling and JSW optimizations. |

## Methodology
- **Warm-up:** 3 iterations to stabilize JIT.
- **Measurement:** 5 iterations averaged.
- **Environment:** Java 21, Aalto XML (default backend).
- **Allocation Tracking:** Performed via `com.sun.management.ThreadMXBean.getThreadAllocatedBytes()`.
