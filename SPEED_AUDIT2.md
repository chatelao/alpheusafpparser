# Performance Audit - Alpheus AFP Parser

## Methodology
The performance was measured using a custom profiling tool `ProfileAfpParser.java` which parses a 1MB AFP file containing approximately 32,000 NOP structured fields. The measurements were taken on a Java 21 environment.

### Test Data
- **File**: `big_ibm273_overhead.afp`
- **Size**: 1.0 MB
- **Content**: 1 BDT, ~32,000 NOPs (32 bytes payload each), 1 EDT.
- **Encoding**: IBM273 (EBCDIC German)

## Results

| Mode | Average Time (10 runs) |
|------|-----------------------|
| Sequential (Stream) | 18.4 ms |
| Sequential (Buffer) | 14.9 ms |

*Note: Parallel parsing was not measured in this audit as the focus was on core parser overhead.*

## Findings

1.  **SFTypeID Lookup Overhead**: The original implementation used a loop over all `SFTypeID` enum values for every structured field. With ~100 SF types defined, this resulted in O(N*M) complexity where N is the number of SFs and M is the number of SF types.
2.  **Memory Allocation**: Parsing from a `ByteBuffer` is faster than `InputStream` due to reduced I/O overhead and better locality, but the current implementation still performs significant `byte[]` allocations for SF payloads.
3.  **Object Pooling**: The existing object pooling (implemented in Phase 10) significantly reduces GC pressure, but the lookup of the pool itself can be optimized.

## Proposed Improvements

### 1. Precomputed SFTypeID Lookup (Implemented)
Replace the loop in `SFTypeID.parse` with a static `Map<Integer, SFTypeID>` (or a sparse array).
- **Status**: Implemented.
- **Impact**: Significant reduction in CPU cycles per structured field.

### 2. Zero-Copy Payload Processing
Currently, even when using `ByteBuffer`, the payload is often copied into a `byte[]` for decoding.
- **Proposal**: Extend all `IAFPDecodeableWriteable` implementations to support `ByteBuffer` directly without copying to `byte[]`.
- **Challenge**: Many legacy EBCDIC decoding methods expect `byte[]`.

### 3. Fast EBCDIC Decoding for IBM273
Similar to the optimized CP500 decoder, a fast lookup-table based decoder should be used for CP273.
- **Status**: `UtilCharacterEncoding` already has `decodeCp273` with a table, but it's only used in `decodeEbcdic` if the charset matches exactly.

### 4. Introducer Reuse
Avoid re-parsing the SFI when scanning. The `AFPScanner` already does some of this, but it could be integrated deeper into the `AFPParser`.

## Conclusion
The parser is already quite efficient for 1MB files. The bottleneck for very large files or high-frequency processing is likely to be object instantiation and EBCDIC string decoding. The implemented `SFTypeID` optimization provides a solid baseline improvement.
