# SLOW_DOWN_FIX_ROADMAP.md

This document outlines the strategy for addressing the performance regressions and slow-downs identified in `SLOWDOWN_FINDINGS.md`.

## Goal
Improve the performance of Jackson-based XML streaming by implementing manual StAX fast-paths for complex hotspots and optimizing large payload handling.

## Phase 1: High-Priority Mnemonic Fast-Paths
Implement manual `XMLStreamWriter` paths in `AfpJacksonXmlWriter` to bypass Jackson's reflective serialization for wide/deep objects.

- **FNC_FontControl**: Manual serialization of all 20+ fields and optional triplets.
- **LND_LineDescriptor**: Manual serialization of line-data specific fields and triplets.
- **GAD_GraphicsData**: Manual serialization of nested GOCA drawing orders.

## Phase 2: Audit 3 Hotspot Fast-Paths
Extend fast-paths to the hotspots identified in real-world production data audits.

- **IPD_ImagePictureData**: Manual serialization of IOCA segment data.
- **OBD_ObjectAreaDescriptor**: Manual serialization of area descriptors.
- **OBP_ObjectAreaPosition**: Manual serialization of position parameters.
- **IDD_ImageDataDescriptor**: Manual serialization of image descriptor fields.
- **MIO_MapImageObject**: Manual serialization of mapping groups.

## Phase 3: NOP Performance Optimization
Optimize the handling of `NOP_NoOperation` fields, especially for large (up to 32KB) unarchitected data payloads.

- **Lazy/Hex Fallback**: If the payload exceeds the 1KB human-readable threshold, write the raw data as a hexadecimal string under a `<hexData>` element instead of attempting a full EBCDIC-to-String decoding.

## Phase 4: Verification & Regression Testing
Ensure that manual implementations are loss-less and provide the expected speedups.

- **Functional Verification**: Run the full test suite (`./gradlew test`) to ensure XML output remains consistent with the object model.
- **Performance Verification**: Re-run `PerformanceRegressionTest` to measure speedups against the "Jackson (Re-measured)" baseline.

---
*Note: Telemetry instrumentation in `MnemonicXMLStreamWriter` is preserved for now to maintain measurement capability.*
