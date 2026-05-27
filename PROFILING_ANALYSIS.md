# Profiling Analysis: High-Volume Directory Processing

## 1. Profiling Setup
- **Workload 1 (Baseline)**: 10 AFP files, each ~10 KB, containing repeated `NOP` fields.
- **Workload 2 (High Volume PTOCA)**: 10 AFP files, each ~100 KB, containing `PTX` fields with `TRN` (Transparent Data) control sequences (~32KB payload per PTX).
- **Environment**: 4-core CPU, Java 21.
- **Commands**:
  - `java -jar alpheus-afp-parser-cli.jar -m --ptx-debug profile_dir/ profile_output/`
  - `java -jar alpheus-afp-parser-cli.jar -j -m --ptx-debug profile_dir/ profile_output/` (Jackson mode)

## 2. Bottleneck Analysis

### 2.1. Mnemonic Performance (NOP vs PTOCA)
#### Baseline (NOP-heavy, 10KB files)
| Mnemonic | Count | Total (ms) | Parse (ms) | Write (ms) |
| :--- | ---: | ---: | ---: | ---: |
| NOP | 5,880 | 318 | 203 | 115 |

- **Observation**: For simple fields like `NOP`, parsing takes longer than writing (~63% of total time) due to SFI validation overhead.

#### High-Volume PTOCA (PTX/TRN-heavy, 100KB files)
Using Jackson Writer (`-j`):
| Mnemonic | Count | Total (ms) | Parse (ms) | Write (ms) |
| :--- | ---: | ---: | ---: | ---: |
| PTX | 20 | 375 | 199 | 176 |
| TRN | 1,270 | 239 | 85 | 154 |

- **Observation**: With TRN-heavy workloads, writing becomes a significant part of the total time. The Jackson fast-paths for TRN are critical, as they avoid reflective overhead. Total write time for 320KB of payload was ~176ms.

### 2.2. PTOCA Debug Performance
| Metric | Value |
| :--- | ---: |
| Total PTX Fields | 10 |
| Total Parse Time | 197 ms |
| Total Write Time | 176 ms |
| Total Payload Size | 322,590 bytes |
| Total Ctrl Sequences | 1,280 |

#### PTOCA Function Breakdown (Jackson)
| Function | Count | Parse (ms) | Write (ms) | Avg Payload |
| :--- | ---: | ---: | ---: | ---: |
| TRN_TransparentData | 1,270 | 90 | 171 | 500.00 |

- **Write Bottleneck**: Even with fast-paths, the sheer volume of PTOCA control sequences (1,280 in 10 small files) makes writing the most expensive phase of PTX processing.
- **Jackson vs JAXB**: Switching to Jackson (`-j`) provides a significant speedup for PTX-heavy files compared to the default JAXB-based writer, while also enabling granular performance metrics.

### 2.3. Directory Processing Logic (`Afp2Xml.java`)
- **Concurrency Strategy**: Uses a `FixedThreadPool` with a default size equal to available processors.
- **Thread Distribution**:
  - If `parallel` is OFF: One thread per file.
  - If `parallel` is ON: Threads are distributed among files (`threadsPerFile = Math.max(1, totalThreads / files.length)`).
- **Bottleneck (Stdout)**: (Resolved May 2026) Previously, a `synchronized (System.out)` block serialized the final I/O step. This has been replaced by `OrderedOutputOrchestrator` and `OrchestratedOutputStream`, which allow concurrent serialization into independent buffers that are flushed in order, significantly reducing synchronization overhead.
- **Bottleneck (Memory)**: (Resolved May 2026) The `ByteArrayOutputStream` bottleneck has been eliminated. Directory-to-stdout mode now uses page-level streaming via the orchestration layer, and a 64MB back-pressure mechanism prevents `OutOfMemoryError` during high-volume processing.

### 2.4. SFI Parsing & Scanning
- **Robustness**: The parser correctly handles EOF during SFI parsing (as seen in the "Not enough bytes for SF introducer" errors when files were truncated).
- **Optimization**: The `AFPScanner` uses a sequential byte-by-byte search for the `0x5A` marker. While `scanForParallel` exists, it is only invoked when the `--parallel` flag is used for single-file conversion. Directory mode does not currently use parallel scanning *within* each file by default.

## 3. Recommended Improvements

1.  **Streaming Stdout**: (**COMPLETED May 2026**) Implemented `OrderedOutputOrchestrator` to enable non-blocking, memory-efficient streaming of XML fragments to stdout.
2.  **SFI Lookahead**: Implement a "jump" logic in `AFPParser` using the SFI length to skip to the next `0x5A` instead of scanning byte-by-byte when the stream is known to be well-formed.
3.  **Thread Locals**: `MnemonicPerformanceMonitor` uses `ThreadLocal<Map>`. For very high thread counts or many short-lived tasks, this could lead to memory bloat or GC pressure if not cleared properly.

## 4. Conclusion
The current implementation is well-optimized for typical production workloads (large PTOCA-heavy files). For directory processing, the primary bottleneck shifted from writing (thanks to manual StAX fast-paths) to I/O synchronization and SFI validation overhead.

## 5. Progress Log

### May 2026
- **Ordered Output Orchestration**: Implemented `OrderedOutputOrchestrator` and `OrchestratedOutputStream` to solve the directory-to-stdout synchronization bottleneck. This enables non-blocking serialization and page-level streaming.
- **Jackson-Only Architecture**: Completed the migration from JAXB to Jackson for all XML generation. Legacy JAXB-based writers have been removed, resulting in a more unified and performant codebase.
- **Vectorized I/O**: Integrated vectorized writes using `GatheringByteChannel.write(ByteBuffer[])` in the orchestration layer, reducing system call overhead during fragment flushing.
- **Back-pressure Mechanism**: Added a configurable memory-aware back-pressure limit (default 64MB) to `OrderedResultCollector` and `OrderedOutputOrchestrator` to prevent OOM errors when producers outpace the output channel.
- **Static Size Estimation**: Introduced `SFSizeEstimator` as part of Aggressive I/O Phase 3, providing heuristic-based XML size predictions for better memory management.
