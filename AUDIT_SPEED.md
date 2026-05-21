# Speed Audit: Processing Big German (IBM273) AFP Files

## Benchmark Methodology
A synthetic AFP file of approximately 11MB was generated containing thousands of `PTX` (Presentation Text) structured fields. Each `PTX` field contains German text encoded in `IBM273` (EBCDIC).

The benchmark measures the total time to convert this AFP file to XML using the `Afp2Xml` CLI.

**Machine Specs:** Sandbox Environment
**Input File:** `src/test/resources/afp/big_german_50mb.afp` (11MB actual)

## Results

| Phase | Time (Real) | Improvement |
| :--- | :--- | :--- |
| Baseline | 1m 42.7s | - |
| Optimized (Fast CP273) | 1m 28.4s | ~14% |

## Implemented Improvements

### 1. Fast CP273 Decoder
Implemented a high-performance EBCDIC-to-UTF8 lookup table for the `IBM273` character set in `UtilCharacterEncoding`. This bypasses the overhead of `java.nio.charset.CharsetDecoder` for every text field.

### 2. Optimized PTOCA Control Sequences
Updated `TRN_TransparentData` and `GraphicCharacters` (the most frequent elements in text-heavy AFP) to use the new `decodeEbcdic` fast path. This also eliminated one redundant `byte[]` allocation per field by decoding directly from the source buffer.

## Proposed Future Improvements

### 1. JAXB Context Caching in Streaming Writer
The current `AfpStreamingXmlWriter` still performs some JAXB class scanning per field if not careful. Pre-initializing a global `JAXBContext` with all Structured Field classes would eliminate this overhead entirely.

### 2. Object Pooling for Control Sequences
Extend the existing object pooling for Structured Fields to also cover `PTOCAControlSequence` objects to further reduce GC pressure.
