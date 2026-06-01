# Jackson, StAX2 & Woodstox (JSW) Performance Roadmap

This document outlines the phased implementation of advanced performance patterns for the Jackson XML, StAX2, and Woodstox stack, as proposed in `JACKSON_STAX2_WOODWORX.md`.

## Status Summary
- **Phase 1: Jackson Infrastructure & Efficiency**: ⏳ Pending
- **Phase 2: StAX2 Low-Level Optimizations**: ⏳ Pending
- **Phase 3: Woodstox Specialized Tuning**: 🚧 In Progress

---

## Phase 1: Jackson Infrastructure & Efficiency ⏳
Optimize the Jackson serialization layer for modern Java and reduced allocation.

- ⏳ **Jackson Blackbird Module**: Integrate `jackson-module-blackbird` for reflection-free accessor generation (Java 21+).
- ⏳ **Pre-cached `ObjectWriter`**: Implement a `ConcurrentHashMap` of type-specific `ObjectWriter` instances in `JacksonXmlMapperProvider` to minimize configuration overhead.
- ⏳ **Fast Double Writing**: Enable `StreamWriteFeature.USE_FAST_DOUBLE_WRITER` for optimized floating-point serialization.

## Phase 2: StAX2 Low-Level Optimizations ⏳
Leverage the StAX2 Typed Access API to eliminate intermediate string allocations.

- ⏳ **Typed Attribute Writing**: Update `AfpJacksonXmlWriter` fast-paths to use `writeIntAttribute` and `writeLongAttribute` instead of `String.valueOf()`.

## Phase 3: Woodstox Specialized Tuning 🚧
Fine-tune the Woodstox backend for maximum throughput and efficient memory usage.

- ⏳ **Integrated Sanitization**: Implement a custom `EscapingWriterFactory` to move XML 1.0 sanitization directly into the Woodstox stream processing layer.
- 🚧 **Property Tuning**:
    - ✅ **Structure Validation**: `P_OUTPUT_VALIDATE_STRUCTURE` set to `false` (via `checkStructure`).
    - ⏳ **Empty Element Handling**: Set `P_ADD_SPACE_AFTER_EMPTY_EL` to `false` to reduce output size.
    - ⏳ **Buffer Optimization**: Increase `P_OUTPUT_BUFFER_SIZE` to 32KB or 64KB for high-volume I/O.
- ⏳ **Direct Buffer Recycling**: Ensure optimal reuse of Woodstox internal buffers in parallel processing tasks.
