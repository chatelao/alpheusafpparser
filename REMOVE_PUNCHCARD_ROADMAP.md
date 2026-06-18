# Roadmap: Removal of Template-Based "Punchard" Serialization

This document outlines the phased decommissioning of the "Punchard" (Hole-Punching) serialization mechanism (Strategy 6 in `10x_FASTER.md`) in favor of standardized StAX2-based manual fast-paths.

## Background: The "Punchard" Mechanism
The "Punchard" mechanism involved pre-computing XML byte templates for Structured Fields and PTOCA sequences. Dynamic data was "punched" into these templates at pre-calculated offsets within a `ThreadLocal` byte buffer. While extremely fast, this approach proved too rigid for the evolving requirements of the Alpheus project.

## Objectives for Removal
- **Reduce Maintenance Burden**: Eliminate the need for manual byte-offset calculations in `XmlTemplateRegistry.java`.
- **Enable Spatial Metadata**: Support the injection of dynamic `page`, `x`, and `y` attributes (as defined in [XYPAGE_CONCEPT.md](XYPAGE_CONCEPT.md)), which is difficult to achieve with static byte templates.
- **Unified Serialization**: Standardize on `AfpXmlStreamWriter` fast-paths, which provide comparable performance using StAX2 typed attributes (`writeIntAttribute`, etc.) without the fragility of templates.
- **Code Clarity**: Simplify the codebase by removing the complex `com.mgz.xml.template` infrastructure.

## Replacement Strategy (StAX2 Fast-Paths)
The project is transitioning back to pure StAX2-based manual fast-paths in `AfpJacksonXmlWriter.java`. This strategy leverages:
1. **`AfpXmlStreamWriter`**: Utilizing `writeIntAttribute`, `writeLongAttribute`, and `writeRawBytes` to minimize `String` allocations and maintain high throughput.
2. **Unified State Management**: Using a single PTOCA state machine to handle both content and spatial metadata.
3. **Zero-Copy Data Handling**: Continuing to use `writeRawBytes` for large EBCDIC or binary payloads (`BDA`, `IPD`).

## Phased Roadmap

### Phase 1: Deprecation and Documentation 🚧
Establish the plan and communicate the architectural shift.
- ✅ Create `REMOVE_PUNCHCARD_CONCEPT.md`.
- 🚧 Create `REMOVE_PUNCHCARD_ROADMAP.md`.
- ✅ Mark Strategy 6 in `10x_FASTER.md` as RETIRED.
- ✅ Mark pending phases in `TEMPLATE_BASED_ROADMAP.md` as DISCONTINUED.

### Phase 2: Conversion of Structured Fields 🚧
Migrate high-frequency Structured Fields from templates to StAX2.
- ✅ Re-implement `PGP1` and `PTD1` manual fast-paths in `AfpJacksonXmlWriter`.
- ✅ Convert remaining SF templates in `XmlTemplateRegistry` to manual `write` methods.
- ⏳ Verify output parity using `SFFastPathVerificationTest`.

### Phase 3: Conversion of PTOCA Control Sequences 🚧
Migrate PTOCA serialization to the unified spatial-aware driver.
- ✅ Integrate `AMI`, `AMB`, `RMI`, `RMB`, and `TRN`/`GC` into the `writeControlSequence` manual fast-path.
- ✅ Integrate `SCFL`, `SBI`, `SIM`, `SVI`, `STC`, `USC`, `SIA`, `BLN`, `STO`, `BSU`, `ESU`, `SEC`, `DIR`, `DBR`, `TBM`, `OVS`, `RPS` into `writeControlSequence`.
- ✅ Ensure all PTOCA sequences utilize the `CoordinateTransformer` for `afpX`/`afpY` calculation.
- ⏳ Verify output parity using `XmlTemplateVerificationTest` (with coordinate normalization).

### Phase 4: Verification and Performance Parity ⏳
Ensure the new approach meets or exceeds "Punchard" performance.
- ⏳ Run end-to-end benchmarks comparing "Punchard" (v0.8) vs. StAX2 Fast-Paths (v0.9).
- ⏳ Validate XML well-formedness for all migrated fields using `XmlEndToEndTest`.

### Phase 5: Cleanup and Removal ⏳
Final removal of the template infrastructure.
- ⏳ Delete `src/main/java/com/mgz/xml/XmlTemplate.java`.
- ⏳ Delete `src/main/java/com/mgz/xml/XmlTemplateRegistry.java`.
- ⏳ Remove `XmlTemplateVerificationTest.java`.
- ⏳ Prune `XmlTagTemplates.java` to only include basic tag fragments if necessary.
