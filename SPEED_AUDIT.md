# Performance Audit: Alpheus AFP Parser

This document details the performance measurement and analysis of the Alpheus AFP Parser, covering both core parsing logic and high-level conversion processes (e.g., AFP to XML).

## 1. Methodology & Test Environment

Measurements were conducted across two primary scenarios to isolate core parser overhead from serialization overhead.

### Scenario A: Full XML Conversion (CLI)
- **Tool**: `Afp2Xml` CLI.
- **Input**: 1MB and 10MB AFP files generated with `tools/generate_large_afp.py`.
- **Content**: ~9,700 (1MB) and ~97,000 (10MB) `NOP` (No Operation) Structured Fields with 100-byte payloads.
- **Goal**: Measure full pipeline overhead including parsing, JAXB serialization, and StAX streaming.
- **Profiling**: `time` utility for baseline, `Java Flight Recorder (JFR)`.

### Scenario B: Core Parser Overhead (Memory)
- **Tool**: `ProfileAfpParser.java` (custom profiler).
- **Input**: `big_ibm273_overhead.afp` (1.0 MB).
- **Content**: 1 BDT, ~32,000 NOPs (32-byte payload each), 1 EDT.
- **Encoding**: IBM273 (EBCDIC German).
- **Goal**: Isolate raw parsing throughput across different I/O modes.

## 2. Measurement Results

### Scenario A: Full XML Conversion (Output to `/dev/null`)

| File Size | Number of Fields | Baseline Execution Time (avg) | Throughput |
| :--- | :--- | :--- | :--- |
| 1 MB | ~9,700 | 5.2s | 0.19 MB/s |
| 10 MB | ~97,000 | 14.5s | 0.69 MB/s |

*Note: The higher throughput for 10MB is due to JVM warmup and JIT compilation.*

### Scenario B: Core Parser Throughput

| Mode | Average Time (10 runs) |
| :--- | :--- |
| Sequential (Stream) | 18.4 ms |
| Sequential (Buffer) | 14.9 ms |

*Note: Parsing is significantly faster than XML conversion, indicating that serialization is the primary bottleneck for end-to-end tasks.*

## 3. Bottleneck Analysis (Hotspots)

Profiling via JFR and custom benchmarks identified the following critical bottlenecks:

### High-Level Conversion (Afp2Xml)

1. **JAXB Marshaller Creation**: In `AfpStreamingXmlWriter`, a new `Marshaller` is created for *every* structured field. This is a heavyweight operation involving reflection and state initialization.
2. **Expensive "Human Readable" Check**: `StructuredFieldBaseData.decodeAFP` (used by `NOP`, `OCD`, etc.) performs `new String(data, charset)` to check for printable characters, causing massive allocation churn.
3. **JAXB Context Cache Key Generation**: Sorting class lists to generate cache keys in `Afp2XmlWriter.getCachedJaxbContext` adds measurable overhead per field.

### Core Parser (AFPParser)

1. **SFTypeID Lookup**: The original `SFTypeID.parse` used a loop over all enum values (~100 types), resulting in O(N*M) complexity.
2. **Memory Allocation (Payload Copies)**: Even when using `ByteBuffer`, payloads are often copied into `byte[]` for decoding, increasing GC pressure.
3. **Object Pool Lookups**: While object pooling (Phase 10) reduced GC pressure, the lookup mechanism for the pools themselves can be further optimized.

## 4. Proposed Improvements

### Phase 1: Serialization Optimizations (Afp2Xml)
- **Marshaller Pooling**: Implement a `ThreadLocal` or object pool for `Marshaller` instances.
- **Optimized `isHumanReadable`**: Perform checks directly on `byte[]` for common EBCDIC printable ranges (CP500/CP273) without creating `String` objects.
- **Direct JAXB Mapping**: Use a direct `Class -> JAXBContext` mapping to avoid list sorting.

### Phase 2: Core Parser Optimizations
- **Precomputed SFTypeID Lookup**: Replace the loop in `SFTypeID.parse` with a static lookup table or sparse array.
- **Zero-Copy Payload Processing**: Extend `IAFPDecodeableWriteable` to support `ByteBuffer` directly, eliminating intermediate `byte[]` allocations.
- **Fast EBCDIC Decoding**: Ensure lookup-table based decoders (like those for CP500/CP273) are used consistently across all components.
- **Introducer Reuse**: Deepen `AFPScanner` integration to reuse `StructuredFieldIntroducer` instances during scanning and parsing.

## 5. Summary
The Alpheus AFP Parser is highly efficient at raw parsing (sub-20ms for 1MB), but end-to-end XML conversion is currently CPU-bound by JAXB overhead and redundant character checks. Implementation of the proposed optimizations is expected to bring XML conversion throughput closer to raw parsing speeds.
