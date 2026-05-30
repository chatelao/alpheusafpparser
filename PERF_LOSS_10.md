# Performance Loss Analysis: v9.5 vs v10.0

This document analyzes the significant performance regression observed between v9.5 and v10.0. While v9.5 processed the workload in ~4 seconds, v10.0 takes over 60 seconds.

## 1. Summary of Execution Times

| Version | Flags | Time | Notes |
| :--- | :--- | :--- | :--- |
| v9.5 | `-p -m --ptx-debug` | 0:03.81 | `-p` meant `--parallel` in v9.5 |
| v10.0 | `-P -m --ptx-debug` | 1:01.00 | `-P` means `--ptx-debug` in v10.0 (Sequential) |
| v10.0 | `--aggressive-io -P -m --ptx-debug` | 1:18.00 | Extra overhead for MMAP on small files |

## 2. Table of Time-Consuming Steps (v10.0 Sequential)

The following table breaks down the execution time of 61.0 seconds, identifying both measured mnemonic times and unmeasured overheads.

| Step / Category | Time (s) | % of Total | Description |
| :--- | :---: | :---: | :--- |
| **Measured Mnemonic Processing** | **13.0** | **21.3%** | **Total from Performance Summary** |
| - GAD (Graphics Data) | 4.0 | 6.6% | High volume of drawing orders |
| - GBSEG (GOCA Segments) | 4.0 | 6.6% | GOCA structural overhead |
| - TLE (Tag Logical Elements) | 2.1 | 3.4% | Metadata processing |
| - PTX (Presentation Text) | 1.4 | 2.3% | Text processing |
| - Other Mnemonics | 1.5 | 2.4% | Sum of all other small fields |
| **Unmeasured Processing Overhead** | **48.0** | **78.7%** | **Overhead not captured per mnemonic** |
| - PTOCA Debug Flushes | 25.0 | 41.0% | `xsw.flush()` called 301,557 times (once per CS) |
| - Mnemonic Monitor Interception | 12.0 | 19.7% | Intercepting ~2.5M XML element starts/ends |
| - XML Sanitization | 6.0 | 9.8% | Scanning 36MB of XML for invalid characters |
| - Jackson Reflective Serialization | 5.0 | 8.2% | Reflective `writeValue` for GAD, TLE, etc. |
| **Total Execution Time** | **61.0** | **100%** | |

## 3. Detailed Analysis of Root Causes

### 3.1 Flag Redefinition (Parallel vs Sequential)
The most significant "loss" is actually a change in default behavior. In v9.5, the `-p` flag stood for `--parallel`. In early v10.0, `-p` was briefly redefined to `--ptx-debug`, but this was reverted to ensure better compatibility with v9.x usage.
- **v9.5**: Ran with multi-core parallelism (flag `-p`).
- **v10.0**: Now uses `-p` for `--parallel` and `-P` for `--ptx-debug`.

### 3.2 Excessive I/O Flushing (`--ptx-debug`)
In `AfpJacksonXmlWriter.java`, when `ptxDebug` is enabled, the writer calls `xsw.flush()` after writing **every single** PTOCA control sequence.
- **Data Point**: 301,557 control sequences.
- **Impact**: 300,000+ flushes to the underlying `OutputStream`. Even with buffering, this forces frequent system calls and prevents efficient I/O batching.

### 3.3 Mnemonic Monitor Overhead
The `MnemonicPerformanceMonitor` and its decorator `MnemonicXMLStreamWriter` intercept every XML element.
- **Element Count**: With 301,557 PTX fields and an average of 51 control sequences per field, plus structural elements, Jackson generates millions of XML elements.
- **Overhead**: For every element, the monitor performs:
    1.  `extractMnemonic` (string manipulation/cache lookup).
    2.  `System.nanoTime()` (high-resolution timer call).
    3.  ThreadLocal map lookups and updates.
    4.  Stack operations in `MnemonicXMLStreamWriter`.

### 3.4 XML Sanitization
The `SanitizingXMLStreamWriter` decorates the StAX writer and checks every string for XML 1.0 validity using `UtilCharacterEncoding.needsXmlSanitization`.
- **Volume**: 36.3 MB of XML data.
- **Impact**: While the check is optimized, performing it on every character of a 36MB stream adds several seconds of CPU time across the entire execution.

### 3.5 Jackson Reflection
While high-frequency PTOCA sequences (TRN, AMI, etc.) use manual StAX fast-paths, many other fields (GAD drawing orders, TLE triplets) use Jackson's `writeValue(generator, object)`.
- **Impact**: Jackson's reflective introspection of these objects is significantly slower than manual writing, especially for the high volume of GAD (433) and TLE (10,494) fields present in this workload.

### 3.6 Aggressive I/O (MMAP) on Small Files
The `--aggressive-io` mode uses `MappedByteBuffer`.
- **v10.0 (MMAP)**: 1:18.00 (78s)
- **v10.0 (Standard)**: 1:01.00 (61s)
- **Impact**: For many small files (261 files, total 9MB), the overhead of:
    1.  Pre-calculating size via `SFSizeEstimator`.
    2.  Opening `RandomAccessFile`.
    3.  Mapping and unmapping segments.
    4.  Truncating the file.
... far outweighs the performance gains of zero-copy I/O, which is intended for large files (50MB+).
