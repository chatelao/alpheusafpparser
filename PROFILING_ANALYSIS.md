# Profiling Analysis: High-Volume Directory Processing

## 1. Profiling Setup
- **Workload**: 10 AFP files, each approximately 10 KB.
- **Content**: Repeated `NOP` (No Operation) structured fields.
- **Environment**: 4-core CPU, Java 21.
- **Command**: `java -jar alpheus-afp-parser-cli.jar -m --ptx-debug profile_dir/ profile_output/`

## 2. Bottleneck Analysis

### 2.1. Mnemonic Performance (NOP)
| Mnemonic | Count | Total (ms) | Parse (ms) | Write (ms) |
| :--- | ---: | ---: | ---: | ---: |
| NOP | 5,880 | 318 | 203 | 115 |

- **Parse vs. Write**: For simple fields like `NOP`, parsing takes longer than writing (~63% of total time). This is because the parser must identify the 0x5A marker and validate the SFI, while writing a simple `<NOP_NoOperation/>` tag is extremely fast.
- **Instrumentation Overhead**: The `MnemonicPerformanceMonitor` adds negligible overhead per call but accumulates over thousands of calls.

### 2.2. Directory Processing Logic (`Afp2Xml.java`)
- **Concurrency Strategy**: Uses a `FixedThreadPool` with a default size equal to available processors.
- **Thread Distribution**:
  - If `parallel` is OFF: One thread per file.
  - If `parallel` is ON: Threads are distributed among files (`threadsPerFile = Math.max(1, totalThreads / files.length)`).
- **Bottleneck (Stdout)**: When outputting to stdout (`-`), a `synchronized (System.out)` block is used. While this prevents interleaving, it serializes the final I/O step, creating a bottleneck if processing is faster than the console can consume.
- **Bottleneck (Memory)**: For directory-to-stdout mode, the entire XML output of each file is buffered in a `ByteArrayOutputStream` before being flushed. For multi-gigabyte files, this will trigger `OutOfMemoryError`.

### 2.3. SFI Parsing & Scanning
- **Robustness**: The parser correctly handles EOF during SFI parsing (as seen in the "Not enough bytes for SF introducer" errors when files were truncated).
- **Optimization**: The `AFPScanner` uses a sequential byte-by-byte search for the `0x5A` marker. While `scanForParallel` exists, it is only invoked when the `--parallel` flag is used for single-file conversion. Directory mode does not currently use parallel scanning *within* each file by default.

## 3. Recommended Improvements

1.  **Streaming Stdout**: Instead of `ByteArrayOutputStream`, use a synchronized `BufferedOutputStream` wrapper that allows page-level or chunk-level flushing to stdout while maintaining order (similar to the `OrderedResultCollector` used in single-file parallel mode).
2.  **SFI Lookahead**: Implement a "jump" logic in `AFPParser` using the SFI length to skip to the next `0x5A` instead of scanning byte-by-byte when the stream is known to be well-formed.
3.  **Thread Locals**: `MnemonicPerformanceMonitor` uses `ThreadLocal<Map>`. For very high thread counts or many short-lived tasks, this could lead to memory bloat or GC pressure if not cleared properly.

## 4. Conclusion
The current implementation is well-optimized for typical production workloads (large PTOCA-heavy files). For directory processing, the primary bottleneck shifted from writing (thanks to manual StAX fast-paths) to I/O synchronization and SFI validation overhead.
