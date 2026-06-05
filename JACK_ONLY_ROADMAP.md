# Jackson-Only Transformation Roadmap

This roadmap outlines the steps required to transform Alpheus into a "Jackson only, no JAXB" tool.

## Current State
- Jackson is the default and only XML generator (`AfpJacksonXmlWriter`).
- Models still contain JAXB annotations.
- Jackson currently relies on `JaxbAnnotationModule` to interpret JAXB annotations.
- JAXB dependencies are still present for model compatibility.

## Phase 1: Infrastructure Consolidation ✅
- [x] Remove JAXB dependencies from `build.gradle.kts`.
- [x] Delete JAXB-based writers:
    - `src/main/java/com/mgz/xml/Afp2XmlWriter.java`
    - `src/main/java/com/mgz/xml/AfpStreamingXmlWriter.java`
- [x] Remove JAXB Marshaller pooling and context caching.

## Phase 2: CLI and API Unification ✅
- [x] Update `Afp2Xml` CLI:
    - Remove `-j` / `--jackson` flag (Jackson is now the default).
    - Update usage instructions.
- [x] Update `ParallelAfpConverter` to use `AfpJacksonXmlWriter` exclusively.

## Phase 3: Model Refactoring ✅
- [x] Remove all `javax.xml.bind` imports and annotations from the codebase.
    - [x] Migrated `com.mgz.afp.ptoca`
    - [x] Migrated `com.mgz.afp.modca_L`
    - [x] Migrated `com.mgz.afp.moca`
    - [x] Migrated `com.mgz.afp.bcoca`
    - [x] Migrated `com.mgz.afp.cmoca`
    - [x] Migrated `com.mgz.afp.ioca`
    - [x] Migrated `com.mgz.afp.goca`
    - [x] Migrated `com.mgz.afp.triplets`
    - [x] Migrated `com.mgz.afp.lineData`
    - [x] Migrated `com.mgz.afp.base`
    - [x] Migrated `com.mgz.afp.foca`
    - [x] Migrated `com.mgz.afp.modca`
- [x] Standardize model properties for Jackson:
    - [x] Replace `@XmlTransient` with `@JsonIgnore`.
    - [x] Replace `@XmlElement(name = "...")` with `@JacksonXmlProperty(localName = "...")`.
    - [x] Replace `@XmlAnyElement(lax = true)` with `@JacksonXmlProperty` + custom serializers if needed for polymorphic lists.
- [x] Remove `JaxbAnnotationModule` from `JacksonXmlMapperProvider`.

## Phase 4: Test Suite Modernization ✅
- [x] Implement `Afp2XmlGoldStandardTest` to compare against "Gold Standard" files (replaces removed `Afp2XmlComparisonTest`).
- [x] Remove JAXB-specific tests (Completed).
- [x] Update `JacksonSerializationTest` and others to be the primary verification suite.

## Phase 5: Verification and Finalization ⏳
- [ ] Requirement Verification:
    - [ ] Run full 21,000+ requirement verification suite.
    - [ ] Verify PTOCA Chapter 6 requirements (PT1-PT4 subsets).
    - [ ] Verify GOCA drawing order mappings.
- [ ] Performance and Resource Profiling:
    - [x] Perform memory profiling to verify reduction in heap usage (no more JAXB contexts).
    - [x] Benchmark sequential vs parallel conversion throughput.
- [ ] Finalize `AfpJacksonXmlWriter` fast-paths:
    - [x] Implement fast-path for `MDR_MapDataResource`.
    - [x] Implement fast-path for `MGO_MapGraphicsObject`.
    - [x] Implement fast-path for `MPO_MapPageOverlay`.
    - [x] Implement fast-path for BCOCA fields (`BBC`, `EBC`, `BDD`, `BDA`).
        - [x] Implement fast-path for PTOCA fields (`BPT`, `EPT`, `PTD` Format 1 & 2).
        - [x] Implement fast-path for GOCA fields (`BGR`, `EGR`, `GDD`).
    - [x] Identify and implement fast-paths for other high-frequency structured fields (e.g., `MSU`, `MMC`, `BDI/EDI`, `BMO/EMO`, `BPS/EPS`, `BRG/ERG`, `MCD`).
        - [x] Implement fast-paths for common PTOCA control sequences (e.g., `RMI`, `RMB`, `SIM`, `SBI`, `BLN`, `BSU`, `ESU`, `STC`, `USC`, `TBM`, `OVS`, `ENC`, `GAR`, `GIR`, `GLC`, `GOR`, `SEA`, `SKI`, `UCT`).
        - [x] Implement fast-path for `MCC_MediumCopyCount`.
        - [x] Implement fast-path for `MCF_MapCodedFont_Format1`.
    - [x] Implement fast-paths for Begin/End fields:
        - [x] MO:DCA Begin/End fields (`BMM`, `EMM`, `BFM`, `EFM`, `BII`, `EII`, `BPF`, `EPF`, `BRS`, `ERS`).
        - [x] FOCA Begin/End fields (`BCF`, `ECF`, `BCP`, `ECP`, `BFN`, `EFN`).
    - [x] Implement manual fast-paths for all remaining MO:DCA triplets (✅ Completed).
        - [x] Implemented fast-paths for all 67 triplets including `MODCAFunctionSet`, `RenderingIntent`, `FinishingOperation`, and `ColorManagementResourceDescriptor`.

## Phase 6: Best Practices and Performance Optimization ⏳
- [x] Consolidate Sanitization:
    - [x] Remove explicit `sanitizeForXml` calls from domain class getters (e.g., `Triplet.Comment`, `PTOCAControlSequence.Undefined`).
    - [x] Remove `SanitizingStringSerializer` from `JacksonXmlMapperProvider`.
    - [x] Ensure `SanitizingXMLStreamWriter` is the single source of truth for sanitization.
- [x] Standardize Polymorphic Serialization:
    - [x] Migrated `PTOCAControlSequence` from `WRAPPER_OBJECT` to `EXISTING_PROPERTY` to avoid brittle `withRootName` logic and eliminate redundant nesting.
- [x] Performance Tuning:
    - [x] Disable `SerializationFeature.INDENT_OUTPUT` in `JacksonXmlMapperProvider` for production performance.
