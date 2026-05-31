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
    - [ ] Perform memory profiling to verify reduction in heap usage (no more JAXB contexts).
    - [ ] Benchmark sequential vs parallel conversion throughput.
- [ ] Finalize `AfpJacksonXmlWriter` fast-paths:
    - [x] Implement fast-path for `MDR_MapDataResource`.
    - [x] Implement fast-path for `MGO_MapGraphicsObject`.
    - [x] Implement fast-path for `MPO_MapPageOverlay`.
    - [x] Implement fast-path for BCOCA fields (`BBC`, `EBC`, `BDD`, `BDA`).
    - [x] Identify and implement fast-paths for other high-frequency structured fields (e.g., `MSU`, `MMC`, `BDI/EDI`, `BMO/EMO`, `BPS/EPS`, `BRG/ERG`).
