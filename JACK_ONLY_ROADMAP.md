# Jackson-Only Transformation Roadmap

This roadmap outlines the steps required to transform Alpheus into a "Jackson only, no JAXB" tool.

## Current State
- JAXB is used for standard XML generation (`AfpStreamingXmlWriter`, `Afp2XmlWriter`).
- Jackson is used for high-performance "experimental" generation (`AfpJacksonXmlWriter`).
- Models contain mixed annotations (JAXB and Jackson).
- Jackson currently relies on `JaxbAnnotationModule` to interpret JAXB annotations.

## Phase 1: Infrastructure Consolidation ✅
- [ ] Remove JAXB dependencies from `build.gradle.kts`.
- [ ] Delete JAXB-based writers:
    - `src/main/java/com/mgz/xml/Afp2XmlWriter.java`
    - `src/main/java/com/mgz/xml/AfpStreamingXmlWriter.java`
- [ ] Remove JAXB Marshaller pooling and context caching.

## Phase 2: CLI and API Unification 🚧
- [ ] Update `Afp2Xml` CLI:
    - Remove `-j` / `--jackson` flag (Jackson is now the default).
    - Update usage instructions.
- [ ] Update `ParallelAfpConverter` to use `AfpJacksonXmlWriter` exclusively.

## Phase 3: Model Refactoring ⏳
- [ ] Remove all `javax.xml.bind` imports and annotations from the codebase.
- [ ] Standardize model properties for Jackson:
    - Replace `@XmlTransient` with `@JsonIgnore`.
    - Replace `@XmlElement(name = "...")` with `@JacksonXmlProperty(localName = "...")`.
    - Replace `@XmlAnyElement(lax = true)` with `@JacksonXmlProperty` + custom serializers if needed for polymorphic lists.
- [ ] Remove `JaxbAnnotationModule` from `JacksonXmlMapperProvider`.

## Phase 4: Test Suite Modernization ⏳
- [ ] Refactor `Afp2XmlComparisonTest` to compare against "Gold Standard" files.
- [ ] Remove JAXB-specific tests:
    - `src/test/java/com/mgz/xml/IllegalAnnotationExceptionTest.java` (no longer relevant).
- [ ] Update `JacksonSerializationTest` and others to be the primary verification suite.

## Phase 5: Verification and Finalization ⏳
- [ ] Run full 21,000+ requirement verification suite.
- [ ] Perform memory profiling to verify reduction in heap usage (no more JAXB contexts).
- [ ] Finalize `AfpJacksonXmlWriter` fast-paths for any remaining slow structured fields.
