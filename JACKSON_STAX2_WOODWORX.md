# Advanced Performance Patterns: Jackson, StAX2 & Woodstox

This document proposes advanced performance patterns for the Jackson XML, StAX2, and Woodstox stack to further optimize the Alpheus AFP Parser.

## 1. Jackson Blackbird Module (Java 21+)
**Description:** The Blackbird module is the successor to Afterburner, designed for modern Java versions (8+). It uses `LambdaMetafactory` to generate high-performance reflection-free accessors for serialization and deserialization.
**Benefit:** Reduces the overhead of reflective field/method access in Jackson's fallback serialization path for complex or rare Structured Fields.
**Proposal:**
- Add `com.fasterxml.jackson.module:jackson-module-blackbird` to `build.gradle.kts`.
- Register the module in `JacksonXmlMapperProvider.java`.

```java
XML_MAPPER = XmlMapper.builder(new XmlFactory(new InputFactoryImpl(), new OutputFactoryImpl()))
    .addModule(new BlackbirdModule())
    .build();
```

## 2. Pre-cached Type-Specific `ObjectWriter`
**Description:** `XmlMapper` is a heavy-weight object. While `ObjectWriter` is light-weight, repeatedly calling `mapper.writer().withRootName(...)` involves configuration lookups and new object instantiation.
**Benefit:** Reusing pre-configured `ObjectWriter` instances for high-frequency objects (like common Triplets or PTOCA sequences not yet on the fast-path) minimizes allocation.
**Proposal:**
- Maintain a `ConcurrentHashMap<Class<?>, ObjectWriter>` in `JacksonXmlMapperProvider`.
- Use `writer.writeValue(generator, value)` to reuse the writer and generator.

## 3. StAX2 Typed Attribute Access
**Description:** StAX2 (supported by Woodstox and Aalto) provides a Typed Access API. While Alpheus already uses `writeInt` and `writeLong` for elements, it can further optimize compact attribute-based fast-paths.
**Benefit:** `writeIntAttribute` and `writeLongAttribute` avoid the creation of temporary `String` objects (e.g., `String.valueOf(int)`) when writing attributes for sequences like `AMI`, `RMI`, or `SCFL`.
**Proposal:**
- Update `AfpJacksonXmlWriter.java` fast-paths to use `XMLStreamWriter2` typed attribute methods.

```java
// Instead of:
baseXsw.writeAttribute("displacement", String.valueOf(ami.getDisplacement()));
// Use:
baseXsw.writeIntAttribute(null, null, "displacement", ami.getDisplacement());
```

## 4. Woodstox `EscapingWriterFactory`
**Description:** Woodstox allows providing a custom `EscapingWriterFactory` to the `WstxOutputFactory`.
**Benefit:** This moves the XML 1.0 character sanitization logic from a decorator (`SanitizingXMLStreamWriter`) directly into Woodstox's core stream processing. It eliminates one layer of delegation and allows Woodstox to perform sanitization more efficiently during its internal buffering.
**Proposal:**
- Implement a `WoodstoxSanitizingEscapingWriter` and register it via `WOODSTOX_XOF.setProperty(WstxConstants.P_OUTPUT_ESCAPING_WRITER_FACTORY, ... )`.

## 5. Optimized Woodstox Property Tuning
**Description:** Woodstox provides several specialized properties that can be tuned for high-throughput generation.
**Benefit:** Disabling unnecessary features or adjusting buffer sizes can yield measurable gains in massive AFP conversions.
**Proposal:**
- `P_OUTPUT_VALIDATE_STRUCTURE`: Ensure this is `false` in production (already handled via `checkStructure` for StAX2).
- `P_ADD_SPACE_AFTER_EMPTY_EL`: Set to `false` to reduce output size for empty elements (e.g., `<BLN_BeginLine/>`).
- `P_USE_DOUBLE_QUOTES_IN_XML_DECL`: Consistency with minimal overhead.
- `P_OUTPUT_BUFFER_SIZE`: Increase from default (8KB) to 32KB or 64KB for high-volume I/O.

## 6. Jackson `StreamWriteFeature.USE_FAST_DOUBLE_WRITER`
**Description:** Jackson 2.15+ introduced a fast double/float writer.
**Benefit:** If Alpheus ever serializes floating-point data (e.g., in advanced GOCA orders or future PDF mapping coordinates), this feature uses the highly optimized `Schubfach` algorithm for double-to-string conversion.
**Proposal:**
- Enable `StreamWriteFeature.USE_FAST_DOUBLE_WRITER` in `XmlMapper` configuration.

```java
XML_MAPPER.configure(StreamWriteFeature.USE_FAST_DOUBLE_WRITER, true);
```

## 7. Direct Buffer Recycling for Woodstox
**Description:** Woodstox uses internal buffers for its processing. In a parallel environment, these buffers can be recycled to reduce GC pressure.
**Benefit:** Leveraging `BufferRecycler` patterns ensured by Woodstox can improve performance in `ParallelPageParser` tasks.
**Proposal:**
- Ensure that the `XMLOutputFactory` is reused correctly (Woodstox factories are thread-safe and manage their own recycling) or use a `ThreadLocal` factory pool if contention is detected.
