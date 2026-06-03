# Profile Performance Test Report - June 2026

This report documents the runtime performance of the Alpheus AFP Parser CLI when processing the standard performance test suite.

## Execution Environment
- **Java Version:** 21 (OpenJDK 64-Bit Server VM)
- **Tool Version:** alpheus-afp-parser-cli-0.2.2.jar
- **Operating System:** Linux (Sandbox Environment)
- **Thread Count:** 4 (Parallel mode)

## Test Configuration
- **Command:** `java -jar build/libs/alpheus-afp-parser-cli-0.2.2.jar -w -p -m --ptx-debug perf_test/ perf_out/`
- **Input Directory:** `perf_test/` (10 files, total ~110 KB)
- **Output Directory:** `perf_out/`
- **Flags:**
  - `-w`: Use Woodstox instead of Aalto.
  - `-p`: Enable parallel conversion.
  - `-m`: Measure and sum up mnemonic processing time.
  - `--ptx-debug`: Detailed PTX/PTOCA performance analysis.

## Performance Metrics (Best of 3 Runs)

### Performance Summary per Mnemonic

| Mnemonic | Count | Total (ms) | Parse (ms) | Write (ms) |
| :--- | ---: | ---: | ---: | ---: |
| BDT        |       10 |              11 |               3 |               8 |
| BPG        |       10 |               0 |               0 |               0 |
| EDT        |       10 |               0 |               0 |               0 |
| EPG        |       10 |               0 |               0 |               0 |
| GraphicCharacters |        0 |              35 |               0 |              35 |
| PTX        |      100 |             136 |              80 |              56 |

### Charsets Used

| Charset | Count |
| :--- | ---: |
| IBM500               |      240 |

### PTX Debug Performance Summary

| Metric | Value |
| :--- | ---: |
| Total PTX Fields | 100 |
| Total Parse Time | 82 ms |
| Total Write Time | 57 ms |
| Total Payload Size | 102400 bytes |
| Total XML Size | 218840 bytes |
| PTX Expansion Ratio | 2.14 |
| Total Ctrl Sequences | 100 |
| Avg CS per PTX | 1.00 |
| Avg Payload per PTX | 1024.00 bytes |

### PTOCA Function Breakdown

| Function                       |      Count |   Parse (ms) |   Write (ms) | Max Write(ms) |   Total Payload |     Avg Payload |      Ratio | Slowest Payload (Hex) |
| :--- | ---: | ---: | ---: | ---: | ---: | ---: | ---: | :--- |
| GraphicCharacters              |        100 |            9 |           45 |           10 |          204800 |         2048.00 |       1.04 | N/A |

## Analysis
- **Throughput:** The parser successfully processed 10 files in parallel.
- **Mnemonic Performance:** `PTX` processing remains the most significant contributor to runtime, with a total of 136ms across all files.
- **PTOCA Efficiency:** The PTX expansion ratio is 2.14, indicating a moderate increase in size when converting from raw PTOCA to XML.
- **Charset:** IBM500 is exclusively used in the test set.
- **Optimization:** Woodstox StAX backend and parallel processing were active.
