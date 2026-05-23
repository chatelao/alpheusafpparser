# Upgrade Path: JAXB to Jackson XML

This document outlines the phased strategy for migrating the Alpheus AFP Parser from JAXB (legacy `javax.xml.bind`) to **Jackson XML**, with targeted optimizations using **StAX + Aalto** where necessary.

## 1. Rationale
While Jakarta XML Binding is the standard successor to JAXB, Jackson XML offers:
- **Higher Throughput**: Jackson's streaming model and lower reflection overhead provide better performance.
- **Unified Ecosystem**: Jackson is the industry standard for data binding in modern Java.
- **Flexibility**: Easier integration with non-XML formats (JSON, YAML) if needed in the future.
- **Resource Efficiency**: Lower memory churn during high-volume serialization.

---

## 2. Phased Upgrade Path

### Phase 1: Jackson Infrastructure & JAXB Compatibility (DONE ✅)
The goal of this phase is to introduce Jackson without rewriting the existing 130+ annotated classes.

1.  **Add Dependencies**: (DONE)
    - `com.fasterxml.jackson.dataformat:jackson-dataformat-xml`
    - `com.fasterxml.jackson.module:jackson-module-jaxb-annotations`
2.  **Configure `XmlMapper`**: (DONE)
    - Enable `JaxbAnnotationModule` to honor existing `@XmlRootElement` and `@XmlElement` annotations.
    - Configure indentation and header generation to match current output.
    - Implemented in `JacksonXmlMapperProvider`.
3.  **Verification**: (DONE) Ensure `XmlMapper` can serialize a representative sample of Structured Fields identically to the current JAXB implementation.
    - Verified in `JacksonSerializationTest`.

### Phase 2: Jackson-Based Streaming Writer (DONE ✅)
Replace the JAXB fragment marshalling in `AfpStreamingXmlWriter` with Jackson's `ToXmlGenerator`.

1.  **Implement `AfpJacksonXmlWriter`**: (DONE)
    - Use `XmlMapper.createGenerator(OutputStream)` for high-performance streaming.
    - Replace `JAXBContext` lookups with a single, thread-safe `XmlMapper` instance.
2.  **Refactor `Afp2Xml` CLI**: (DONE)
    - Add a toggle or configuration to switch between the JAXB-based and Jackson-based streaming writers.
3.  **Verification**: (DONE) Run `Afp2XmlComparisonTest` to ensure output parity.

### Phase 3: Performance Benchmarking & Optimization (DONE ✅)
Audit the performance gains and identify remaining bottlenecks.

1.  **Benchmark**: Compare Jackson vs. JAXB using `PerformanceRegressionTest`. (DONE)
2.  **Profile**: Use JFR (Java Flight Recorder) to identify any overhead in Jackson's handling of specific complex fields (e.g., nested PTOCA sequences). (DONE)

### Phase 4: Targeted StAX + Aalto Optimization (DONE ✅)
For "sensitive" or high-frequency fields that Jackson handles inefficiently, implement manual StAX serialization using the Aalto implementation.

1.  **Identify Sensitive Fields**: Focus on fields like `NOP` (high frequency), `TLE`, `BAG` or `GAD`/`IPD` (massive payloads). (DONE)
2.  **Implement Manual Serialization**: (DONE)
    - Use Aalto's `XMLStreamWriter2` for ultra-fast, zero-copy text writing.
    - Integrate these manual paths into the `AfpJacksonXmlWriter`.

### Phase 5: Complete JAXB Removal
Once Jackson is stable and performing well, remove the legacy JAXB dependency.

1.  **Native Jackson Annotations**: (Optional) Migrate `@XmlElement` to `@JacksonXmlProperty` for finer control.
2.  **Cleanup**: Remove `jaxb-api` and `jaxb-runtime` from `build.gradle.kts`.
3.  **Final Verification**: Ensure the entire test suite passes without any JAXB classes on the classpath.

---

## 3. Risk Mitigation
- **Feature Parity**: Jackson's XML support has known differences regarding list wrapping. Use `SerializationFeature.WRITE_XML_ROOT_ELEMENT` and `@JacksonXmlElementWrapper` as needed.
- **XPath Compatibility**: Ensure that Jackson-generated XML remains compatible with the streaming XPath filtering logic.
