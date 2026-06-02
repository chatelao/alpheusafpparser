# Jackson, StAX2 & Woodstox (JSW) Performance Roadmap

This document outlines the phased implementation of advanced performance patterns for the Jackson XML, StAX2, and Woodstox stack, as proposed in `JACKSON_STAX2_WOODWORX.md`.

## Status Summary
- **Phase 1: Jackson Infrastructure & Efficiency**: ✅ Complete
- **Phase 2: StAX2 Low-Level Optimizations**: ✅ Complete
- **Phase 3: Woodstox Specialized Tuning**: 🚧 In Progress

---

## Phase 1: Jackson Infrastructure & Efficiency ✅
Optimize the Jackson serialization layer for modern Java and reduced allocation.

- ✅ **Jackson Blackbird Module**: Integrate `jackson-module-blackbird` for reflection-free accessor generation (Java 21+).
- ✅ **Pre-cached `ObjectWriter`**: Implement a `ConcurrentHashMap` of type-specific `ObjectWriter` instances in `JacksonXmlMapperProvider` to minimize configuration overhead.
- ✅ **Fast Double Writing**: Enable `StreamWriteFeature.USE_FAST_DOUBLE_WRITER` for optimized floating-point serialization.

## Phase 2: StAX2 Low-Level Optimizations ✅
Leverage the StAX2 Typed Access API to eliminate intermediate string allocations.

- ✅ **Typed Attribute Writing**: Update `AfpJacksonXmlWriter` fast-paths to use `writeIntAttribute` and `writeLongAttribute` instead of `String.valueOf()`.

## Phase 3: Woodstox Specialized Tuning 🚧
Fine-tune the Woodstox backend for maximum throughput and efficient memory usage.

- 🚧 **Integrated Sanitization**: Move XML 1.0 sanitization directly into the Woodstox stream processing layer.
    - ⏳ **Sanitizing Escaping Writer**: Implement `EscapingWriterFactory` and a corresponding `Writer` that performs single-pass sanitization.
    - ⏳ **Integration & Bypass**: Register the factory in `JacksonXmlMapperProvider` and update `AfpJacksonXmlWriter` to bypass the `SanitizingXMLStreamWriter` decorator when Woodstox is active.
- ✅ **Property Tuning**:
    - ✅ **Structure Validation**: `P_OUTPUT_VALIDATE_STRUCTURE` set to `false` (via `checkStructure`).
    - ✅ **Empty Element Handling**: Set `P_ADD_SPACE_AFTER_EMPTY_EL` to `false` to reduce output size.
    - ✅ **XML Declaration Quotes**: Set `P_USE_DOUBLE_QUOTES_IN_XML_DECL` to `true` for standard compliance.
    - ✅ **Buffer Optimization**: Increase `P_OUTPUT_BUFFER_SIZE` to 64KB for high-volume I/O.
- ✅ **Direct Buffer Recycling**: Ensure optimal reuse of Woodstox internal buffers in parallel processing tasks by consolidating StAX factories and ensuring proper `close()` calls on stream writers.

## Phase 4: Serialization Consistency & Advanced Fast-Paths ⏳
Standardize the serialization output and expand performance optimizations.

- ⏳ **Polymorphic Serialization Standardization**: Ensure all Structured Fields and PTOCA sequences follow consistent attribute-vs-element patterns (compact attribute formatting) to reduce XML verbosity.
- ⏳ **Expanded BCOCA Fast-Paths**: Implement manual StAX writing for missing BCOCA fields to eliminate Jackson overhead in barcoded documents.

---

## Core Performance & Reliability Matrix

| Core Technique & Assertion | Syntax / Configuration | Reliability | Collapse Condition |
| :--- | :--- | :--- | :--- |
| **1. Force Woodstox Implementation**: Jackson's default XML factory might fall back to the JDK's internal StAX writer, which is slower. Explicitly passing WstxOutputFactory guarantees Woodstox is used. | `WstxOutputFactory outFact = new WstxOutputFactory();`<br>`XmlMapper mapper = new XmlMapper(new XmlFactory(new WstxInputFactory(), outFact));` | High | Collapses if the Woodstox core dependency (com.fasterxml.woodstox:woodstox-core) is missing from the classpath, causing NoClassDefFoundError. |
| **2. Disable Namespace Repairing**: StAX2 attempts to automatically fix missing or broken XML namespaces. This requires maintaining state and checking prefixes. Disabling it saves CPU cycles. | `outFact.setProperty(XMLOutputFactory.IS_REPAIRING_NAMESPACES, false);` | Medium | Collapses into generating malformed XML if your POJOs rely on Jackson to infer complex, unmapped namespace prefixes dynamically. |
| **3. Bytecode Generation (Blackbird)**: Reflection is too slow for maximal performance. The Blackbird module (successor to Afterburner) uses MethodHandles and LambdaMetafactory to serialize POJOs at near-native speed. | `mapper.registerModule(new BlackbirdModule());` | Very High | Collapses on heavily locked-down JVM environments (like strict GraalVM native images without reflection config) or with inaccessible module boundaries in Java 17+. |
| **4. Direct Stream Writing**: Writing to a String or Writer forces the JVM to handle UTF-16 chars and later encode them to UTF-8 bytes. Writing directly to an OutputStream allows Woodstox to encode directly. | `mapper.writeValue(new BufferedOutputStream(targetStream), pojo);` | Absolute | Collapses if the downstream consumer requires a different charset (e.g., UTF-16), negating the byte-level optimizations. |
| **5. Disable Output Formatting**: Pretty-printing adds whitespace characters and carriage returns, forcing extra buffer flushes and increasing payload size. | `mapper.disable(SerializationFeature.INDENT_OUTPUT);` | Absolute | Never technically collapses, but fails human readability requirements. |
