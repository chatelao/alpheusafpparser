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
| Sequential (Stream) | 20.0 ms |

*Note: Baseline before optimization was ~36.5 ms.*

## Findings

1.  **SFTypeID Lookup Overhead**: The original implementation used a loop over all `SFTypeID` enum values for every structured field. With ~100 SF types defined, this resulted in O(N*M) complexity where N is the number of SFs and M is the number of SF types.
2.  **Memory Allocation**: Even when using `ByteBuffer`, the current implementation still performs significant `byte[]` allocations for SF payloads.

## Proposed Improvements

### 1. Precomputed SFTypeID Lookup (Implemented)
Replace the loop in `SFTypeID.parse` with a static `Map<Integer, SFTypeID>`.
- **Status**: Implemented.
- **Impact**: Significant reduction in CPU cycles per structured field (reduced processing time by ~45% in the high-overhead test case).

### 2. Zero-Copy Payload Processing
Currently, the payload is copied into a `byte[]` for decoding.
- **Proposal**: Extend all `IAFPDecodeableWriteable` implementations to support `ByteBuffer` directly without copying to `byte[]`.
- **Challenge**: Requires careful refactoring to ensure that structured fields that need to retain their raw data (like `NOP`) do so efficiently.

## Conclusion
The implemented `SFTypeID` lookup optimization provides a significant performance boost for AFP files with many structured fields. Further optimizations should focus on reducing memory allocations during payload processing.
