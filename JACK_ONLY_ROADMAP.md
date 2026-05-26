# Jackson-Only Transformation Roadmap

This roadmap outlines the steps required to transform Alpheus into a "Jackson only, no JAXB" tool.

## Current State
- Jackson is the default and only XML generator (`AfpJacksonXmlWriter`).
- Models still contain JAXB annotations.
- Jackson currently relies on `JaxbAnnotationModule` to interpret JAXB annotations.
- JAXB dependencies are still present for model compatibility.

## Phase 1: Infrastructure Consolidation ✅
- [ ] Remove JAXB dependencies from `build.gradle.kts`. (Wait for Phase 3)
- [x] Delete JAXB-based writers:
    - `src/main/java/com/mgz/xml/Afp2XmlWriter.java`
    - `src/main/java/com/mgz/xml/AfpStreamingXmlWriter.java`
- [x] Remove JAXB Marshaller pooling and context caching.

## Phase 2: CLI and API Unification ✅
- [x] Update `Afp2Xml` CLI:
    - Remove `-j` / `--jackson` flag (Jackson is now the default).
    - Update usage instructions.
- [x] Update `ParallelAfpConverter` to use `AfpJacksonXmlWriter` exclusively.

## Phase 3: Model Refactoring ⏳
- [ ] Remove all `javax.xml.bind` imports and annotations from the codebase.
- [ ] Standardize model properties for Jackson:
    - Replace `@XmlTransient` with `@JsonIgnore`.
    - Replace `@XmlElement(name = "...")` with `@JacksonXmlProperty(localName = "...")`.
    - Replace `@XmlAnyElement(lax = true)` with `@JacksonXmlProperty` + custom serializers if needed for polymorphic lists.
- [ ] Remove `JaxbAnnotationModule` from `JacksonXmlMapperProvider`.

## Phase 4: Test Suite Modernization 🚧
- [ ] Refactor `Afp2XmlComparisonTest` to compare against "Gold Standard" files.
- [x] Remove JAXB-specific tests:
    - `src/test/java/com/mgz/xml/IllegalAnnotationExceptionTest.java`
    - `src/test/java/com/mgz/xml/AfpStreamingXmlWriterTest.java`
    - `src/test/java/com/mgz/xml/Afp2XmlWriterTest.java`
    - `src/test/java/com/mgz/xml/Afp2XmlComparisonTest.java`
- [x] Update `JacksonSerializationTest` and others to be the primary verification suite.

## Phase 5: Verification and Finalization ⏳
- [ ] Run full 21,000+ requirement verification suite.
- [ ] Perform memory profiling to verify reduction in heap usage (no more JAXB contexts).
- [ ] Finalize `AfpJacksonXmlWriter` fast-paths for any remaining slow structured fields.
