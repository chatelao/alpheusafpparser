# Speed Audit: AFP Processing (IBM273 / CP273)

This document details the performance measurement and analysis of the Alpheus AFP Parser after implementing Phases 1-6 of the `SPEED_ROADMAP.md` and adding specialized PTOCA support.

## 1. Test Environment & Methodology

- **Input File**: 10MB and 100MB (200x scale-up from 0.5MB baseline) AFP files generated with `tools/generate_large_afp.py`.
- **Content Types**:
    - **NOP-Heavy**: ~97,000 `NOP` fields (100-byte payloads).
    - **PTX-Heavy**: ~300 (for 10MB) to ~3,000 (for 100MB) `PTX` fields, each containing ~130 `TRN` (Transparent Data) control sequences with German text (CP273).
- **Task**: Full conversion to XML using `Afp2Xml` CLI.
- **Tools**: `time` utility, JFR (Java Flight Recorder) for hotspot analysis.

## 2. Measurement Results (Post-Optimization)

| Content Type | File Size | Baseline (Pre-Opt) | Current Execution Time (avg) | Throughput |
| :--- | :--- | :--- | :--- | :--- |
| NOP Fields | 10 MB | 14.5s | ~0.85s | ~11.8 MB/s |
| PTX Fields | 10 MB | N/A | ~6.40s | ~1.5 MB/s |
| PTX Fields | 100 MB | N/A | ~55.0s | ~1.8 MB/s |

*Note: Throughput improves slightly for larger files due to JIT optimization of the JAXB pathways.*

## 3. Bottleneck Analysis (Profiler Findings)

Using JFR on a 100MB PTX-heavy payload identified the following remaining hotspots:

### A. JAXB Recursive Marshalling (Primary Hotspot)
Even with `Marshaller` pooling, JAXB spends ~80% of CPU time in `com.sun.xml.bind.v2.runtime.XMLSerializer.childAsXsiType` and related methods. 
- **Cause**: Each `PTX` field contains hundreds of `PTOCAControlSequence` sub-objects. JAXB's reflective traversal of these lists is the dominant cost.
- **Evidence**: `jdk.ExecutionSample` shows heavy activity in `DatatypeConverterImpl._printBase64Binary` (for binary payloads) and `XMLStreamWriterOutput.text`.

### B. Character Encoding (Minor Hotspot)
- **Finding**: `UtilCharacterEncoding.decodeCp500` shows up in samples but accounts for < 5% of total time.
- **Observation**: German characters (äöüß) in `CP273` are handled correctly via the EBCDIC decoding path.

### C. JAXB Context Cache Key Generation
- **Status**: Resolved. The implementation of `SINGLE_CLASS_CONTEXT_CACHE` and specialized handling for `PTX` in `Afp2XmlWriter` has minimized this overhead.

## 4. Parser Robustness & CLI Improvements

- **Fixed**: A `BufferUnderflowException` in `AFPParser.parseNextSFFromBuffer` was resolved by adding bounds checks in the error recovery catch block.
- **Improved**: The `Afp2Xml` CLI now handles truncated records (common at the end of large files) by disabling strict error escalation by default.

## 5. Summary & Recommendation

The Alpheus AFP Parser is now extremely efficient at the core record-level. The primary performance limit for complex documents (like those rich in PTOCA/GOCA) is the XML serialization layer.

**Recommendation**:
To achieve further speedups for PTOCA/GOCA-heavy files, we should consider replacing JAXB with a manual StAX-based writer for high-frequency sub-elements, or leverage Phase 10's Parallel Page Parsing to mask the JAXB latency.
