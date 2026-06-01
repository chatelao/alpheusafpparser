# Jackson, StAX2 & Woodstox (JSW) Performance Roadmap

This document outlines the phased implementation of advanced performance patterns for the Jackson XML, StAX2, and Woodstox stack, as proposed in `JACKSON_STAX2_WOODWORX.md`.

## Status Summary
- **Phase 1: Jackson Infrastructure & Efficiency**: ⏳ Pending
- **Phase 2: StAX2 Low-Level Optimizations**: ⏳ Pending
- **Phase 3: Woodstox Specialized Tuning**: 🚧 In Progress

---

## Phase 1: Jackson Infrastructure & Efficiency ⏳
Optimize the Jackson serialization layer for modern Java and reduced allocation.

- ✅ **Jackson Blackbird Module**: Integrate `jackson-module-blackbird` for reflection-free accessor generation (Java 21+).
- ⏳ **Pre-cached `ObjectWriter`**: Implement a `ConcurrentHashMap` of type-specific `ObjectWriter` instances in `JacksonXmlMapperProvider` to minimize configuration overhead.
- ✅ **Fast Double Writing**: Enable `StreamWriteFeature.USE_FAST_DOUBLE_WRITER` for optimized floating-point serialization.

## Phase 2: StAX2 Low-Level Optimizations ✅
Leverage the StAX2 Typed Access API to eliminate intermediate string allocations.

- ✅ **Typed Attribute Writing**: Update `AfpJacksonXmlWriter` fast-paths to use `writeIntAttribute` and `writeLongAttribute` instead of `String.valueOf()`.

## Phase 3: Woodstox Specialized Tuning 🚧
Fine-tune the Woodstox backend for maximum throughput and efficient memory usage.

- ⏳ **Integrated Sanitization**: Implement a custom `EscapingWriterFactory` to move XML 1.0 sanitization directly into the Woodstox stream processing layer.
- ✅ **Property Tuning**:
    - ✅ **Structure Validation**: `P_OUTPUT_VALIDATE_STRUCTURE` set to `false` (via `checkStructure`).
    - ✅ **Empty Element Handling**: Set `P_ADD_SPACE_AFTER_EMPTY_EL` to `false` to reduce output size.
    - ✅ **Buffer Optimization**: Increase `P_OUTPUT_BUFFER_SIZE` to 32KB or 64KB for high-volume I/O.
- ⏳ **Direct Buffer Recycling**: Ensure optimal reuse of Woodstox internal buffers in parallel processing tasks.

---

## Core Performance & Reliability Matrix

| Core Technique & Assertion | Syntax / Configuration | Reliability | Collapse Condition |
| :--- | :--- | :--- | :--- |
| **1. Force Woodstox Implementation**: Jackson's default XML factory might fall back to the JDK's internal StAX writer, which is slower. Explicitly passing WstxOutputFactory guarantees Woodstox is used. | `WstxOutputFactory outFact = new WstxOutputFactory();`<br>`XmlMapper mapper = new XmlMapper(new XmlFactory(new WstxInputFactory(), outFact));` | High | Collapses if the Woodstox core dependency (com.fasterxml.woodstox:woodstox-core) is missing from the classpath, causing NoClassDefFoundError. |
| **2. Disable Namespace Repairing**: StAX2 attempts to automatically fix missing or broken XML namespaces. This requires maintaining state and checking prefixes. Disabling it saves CPU cycles. | `outFact.setProperty(XMLOutputFactory.IS_REPAIRING_NAMESPACES, false);` | Medium | Collapses into generating malformed XML if your POJOs rely on Jackson to infer complex, unmapped namespace prefixes dynamically. |
| **3. Bytecode Generation (Blackbird)**: Reflection is too slow for maximal performance. The Blackbird module (successor to Afterburner) uses MethodHandles and LambdaMetafactory to serialize POJOs at near-native speed. | `mapper.registerModule(new BlackbirdModule());` | Very High | Collapses on heavily locked-down JVM environments (like strict GraalVM native images without reflection config) or with inaccessible module boundaries in Java 17+. |
| **4. Direct Stream Writing**: Writing to a String or Writer forces the JVM to handle UTF-16 chars and later encode them to UTF-8 bytes. Writing directly to an OutputStream allows Woodstox to encode directly. | `mapper.writeValue(new BufferedOutputStream(targetStream), pojo);` | Absolute | Collapses if the downstream consumer requires a different charset (e.g., UTF-16), negating the byte-level optimizations. |
| **5. Disable Output Formatting**: Pretty-printing adds whitespace characters and carriage returns, forcing extra buffer flushes and increasing payload size. | `mapper.disable(SerializationFeature.INDENT_OUTPUT);` | Absolute | Never technically collapses, but fails human readability requirements. |
