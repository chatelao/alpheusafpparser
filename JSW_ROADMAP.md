# Jackson, StAX2 & Woodstox (JSW) Performance Roadmap

This document outlines the phased implementation of advanced performance patterns for the Jackson XML, StAX2, and Woodstox stack, as proposed in `JACKSON_STAX2_WOODWORX.md`.

## Status Summary
- **Phase 1: Jackson Infrastructure & Efficiency**: ✅ Complete
- **Phase 2: StAX2 Low-Level Optimizations**: ✅ Complete
- **Phase 3: Woodstox Specialized Tuning**: 🚧 In Progress
- **Phase 4: Serialization Consistency & Advanced Fast-Paths**: ✅ Complete

---

## Phase 1: Jackson Infrastructure & Efficiency ✅
Optimize the Jackson serialization layer for modern Java and reduced allocation.

- ✅ **Jackson Blackbird Module**: Integrate `jackson-module-blackbird` for reflection-free accessor generation (Java 21+).
    - **Reliability**: Very High.
    - **Collapse Condition**: Collapses on heavily locked-down JVM environments or with inaccessible module boundaries in Java 17+.
- ✅ **Direct Stream Writing**: Configure `AfpJacksonXmlWriter` to write directly to an `OutputStream` to avoid UTF-16 to UTF-8 encoding overhead.
    - **Reliability**: Absolute.
    - **Collapse Condition**: Collapses if the downstream consumer requires a different charset (e.g., UTF-16).
- ✅ **Disable Output Formatting**: Explicitly disable `SerializationFeature.INDENT_OUTPUT` to eliminate whitespace/indentation overhead and reduce payload size.
    - **Reliability**: Absolute.
    - **Collapse Condition**: Never technically collapses, but fails human readability requirements.
- ✅ **Pre-cached `ObjectWriter`**: Implement a `ConcurrentHashMap` of type-specific `ObjectWriter` instances in `JacksonXmlMapperProvider` to minimize configuration overhead.
- ✅ **Fast Double Writing**: Enable `StreamWriteFeature.USE_FAST_DOUBLE_WRITER` for optimized floating-point serialization.

## Phase 2: StAX2 Low-Level Optimizations ✅
Leverage the StAX2 Typed Access API to eliminate intermediate string allocations.

- ✅ **Typed Attribute Writing**: Update `AfpJacksonXmlWriter` fast-paths to use `writeIntAttribute` and `writeLongAttribute` instead of `String.valueOf()`.

## Phase 3: Woodstox Specialized Tuning 🚧
Fine-tune the Woodstox backend for maximum throughput and efficient memory usage.

- ✅ **Forced Woodstox Implementation**: Guarantee the use of Woodstox by explicitly passing `WstxOutputFactory` to the `XmlFactory` to avoid slower JDK default fallbacks.
    - **Reliability**: High.
    - **Collapse Condition**: Collapses if the Woodstox core dependency is missing from the classpath.
- 🚧 **Integrated Sanitization**: Move XML 1.0 sanitization directly into the Woodstox stream processing layer.
    - ⏳ **Sanitizing Escaping Writer**: Implement `EscapingWriterFactory` and a corresponding `Writer` that performs single-pass sanitization.
    - ⏳ **Integration & Bypass**: Register the factory in `JacksonXmlMapperProvider` and update `AfpJacksonXmlWriter` to bypass the `SanitizingXMLStreamWriter` decorator when Woodstox is active.
- ✅ **Property Tuning**:
    - ✅ **Disable Namespace Repairing**: Evaluation complete; `IS_REPAIRING_NAMESPACES` set to `false` to save CPU cycles.
        - **Reliability**: Medium.
        - **Collapse Condition**: Collapses into generating malformed XML if POJOs rely on Jackson to infer dynamic namespace prefixes.
    - ✅ **Structure Validation**: `P_OUTPUT_VALIDATE_STRUCTURE` set to `false` (via `checkStructure`).
    - ✅ **Empty Element Handling**: Set `P_ADD_SPACE_AFTER_EMPTY_EL` to `false` to reduce output size.
    - ✅ **XML Declaration Quotes**: Set `P_USE_DOUBLE_QUOTES_IN_XML_DECL` to `true` for standard compliance.
    - ✅ **Buffer Optimization**: Increase `P_OUTPUT_BUFFER_SIZE` to 64KB for high-volume I/O.
- ✅ **Direct Buffer Recycling**: Ensure optimal reuse of Woodstox internal buffers in parallel processing tasks by consolidating StAX factories and ensuring proper `close()` calls on stream writers.

## Phase 4: Serialization Consistency & Advanced Fast-Paths ✅
Standardize the serialization output and expand performance optimizations.

- ✅ **Polymorphic Serialization Standardization**: Ensured all high-frequency Structured Fields and PTOCA sequences follow consistent attribute-vs-element patterns (compact attribute formatting) to reduce XML verbosity.
- ✅ **Expanded BCOCA Fast-Paths**: Implemented manual StAX writing for missing BCOCA fields (`BBC`, `EBC`) to eliminate Jackson overhead in barcoded documents.

