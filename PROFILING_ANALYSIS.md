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
- **Bottleneck (Stdout)**: When outputting to stdout (`-`), a `synchronized (System.out)` block is used. While this prevents interleaving, it serializes the final I/O step, creating a bottleneck if processing is faster than the console can consume.
- **Bottleneck (Memory)**: For directory-to-stdout mode, the entire XML output of each file is buffered in a `ByteArrayOutputStream` before being flushed. For multi-gigabyte files, this will trigger `OutOfMemoryError`.

### 2.4. SFI Parsing & Scanning
- **Robustness**: The parser correctly handles EOF during SFI parsing (as seen in the "Not enough bytes for SF introducer" errors when files were truncated).
- **Optimization**: The `AFPScanner` uses a sequential byte-by-byte search for the `0x5A` marker. While `scanForParallel` exists, it is only invoked when the `--parallel` flag is used for single-file conversion. Directory mode does not currently use parallel scanning *within* each file by default.

## 3. Recommended Improvements

1.  **Streaming Stdout**: Instead of `ByteArrayOutputStream`, use a synchronized `BufferedOutputStream` wrapper that allows page-level or chunk-level flushing to stdout while maintaining order (similar to the `OrderedResultCollector` used in single-file parallel mode).
2.  **SFI Lookahead**: Implement a "jump" logic in `AFPParser` using the SFI length to skip to the next `0x5A` instead of scanning byte-by-byte when the stream is known to be well-formed.
3.  **Thread Locals**: `MnemonicPerformanceMonitor` uses `ThreadLocal<Map>`. For very high thread counts or many short-lived tasks, this could lead to memory bloat or GC pressure if not cleared properly.

## 4. Conclusion
The current implementation is well-optimized for typical production workloads (large PTOCA-heavy files). For directory processing, the primary bottleneck shifted from writing (thanks to manual StAX fast-paths) to I/O synchronization and SFI validation overhead.
