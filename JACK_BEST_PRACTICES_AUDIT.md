# Jackson Best Practices Audit - Alpheus AFP Parser

## 1. Introduction
This audit evaluates the implementation of Jackson XML serialization in the Alpheus AFP Parser project. The project recently migrated from JAXB to a Jackson-only architecture to improve performance and maintainability.

## 2. Current Implementation Overview
The core of the Jackson implementation resides in:
- `JacksonXmlMapperProvider`: Manages singleton `XmlMapper` instances.
- `AfpJacksonXmlWriter`: A hybrid streaming writer using StAX (`Aalto-XML`) for document structure and manual fast-paths, while delegating to Jackson for individual objects.
- Domain classes (`StructuredField`, `Triplet`, `PTOCAControlSequence`): Annotated with Jackson-specific annotations.

## 3. Best Practices Adherence

### 3.1. Mapper Management (Excellent)
- **Singleton Pattern**: `JacksonXmlMapperProvider` correctly implements a thread-safe singleton for `XmlMapper`.
- **High-Performance Backend**: Using `com.fasterxml.aalto.stax.InputFactoryImpl` and `OutputFactoryImpl` ensures the fastest possible StAX processing.
- **Mapper Reuse**: The project uses `mapper.copy()` to create variants (e.g., for fragments), which is a thread-safe and efficient way to manage different configurations.

### 3.2. Streaming Integration (Excellent)
- **StAX Integration**: `AfpJacksonXmlWriter` creates a `ToXmlGenerator` that wraps the existing `XMLStreamWriter2`. This allows Jackson to "participate" in an ongoing StAX stream without closing it or creating redundant buffers.
- **Manual Fast-Paths**: The implementation correctly recognizes that Jackson's reflective serialization has overhead. High-frequency fields like `TLE`, `NOP`, and `PTX` are written manually using StAX, while less frequent or more complex objects use Jackson.

### 3.3. Annotation Usage (Good)
- **Naming Control**: Widespread and correct use of `@JacksonXmlProperty` and `@JacksonXmlRootElement` to match the legacy JAXB/AFP schema.
- **Polymorphic Handling**: `PTOCAControlSequence` uses `@JsonTypeInfo` and `@JsonSubTypes` correctly.
- **Transient Fields**: `@JsonIgnore` is used appropriately on internal state or fields that shouldn't appear in the XML.

## 4. Identified Issues and Inefficiencies

### 4.1. The "Triple Sanitization" Overhead
The audit identified that many strings are scanned and sanitized for XML 1.0 safety up to three times:
1. **Getter Level**: Many `getText()` methods (e.g., in `Triplet.Comment`, `PTOCAControlSequence.Undefined`) explicitly call `UtilCharacterEncoding.sanitizeForXml()`.
2. **Serializer Level**: `JacksonXmlMapperProvider` registers a `SanitizingStringSerializer` which sanitizes every string serialized by Jackson.
3. **StAX Level**: `AfpJacksonXmlWriter` uses `SanitizingXMLStreamWriter`, a decorator that scans every string written via StAX.

**Impact**: While functionally safe, this adds measurable CPU overhead on text-heavy AFP files as the same string content is parsed for invalid characters multiple times.

### 4.2. Polymorphic Nesting Risks
In `PTOCAControlSequence`, the use of `JsonTypeInfo.As.WRAPPER_OBJECT` combined with manual `withRootName()` calls in `AfpJacksonXmlWriter.writeControlSequence` is complex. If a standard `mapper.writeValue()` were called on a list of sequences, it would produce different nesting than the manual writer. The current implementation is consistent but fragile.

### 4.3. Use of Deprecated `writeValue` on Mapper
`AfpJacksonXmlWriter` uses `fragmentMapper.writer().withRootName(rootName).writeValue(baseFragmentGenerator, sf)`. This is actually correct (using `ObjectWriter`), but some older test code might still use `mapper.writeValue()` directly on the `XmlMapper` instance, which is slightly less efficient as it involves more configuration lookup per call.

## 5. Recommendations

### 5.1. Consolidate Sanitization
- **Primary recommendation**: Rely on the `SanitizingXMLStreamWriter` (the StAX decorator) as the "single source of truth" for sanitization. It catches both manual StAX writes and Jackson-delegated writes.
- Remove explicit `sanitizeForXml` calls from domain class getters to keep the domain model "clean" and avoid double-scanning.
- The `SanitizingStringSerializer` in `JacksonXmlMapperProvider` could be removed if the StAX decorator is always guaranteed to be present.

### 5.2. Standardize Polymorphic Serialization
- Consider moving towards `JsonTypeInfo.As.EXISTING_PROPERTY` or `PROPERTY` if the schema allows, to avoid the brittle `WRAPPER_OBJECT` logic, or continue ensuring that `AfpJacksonXmlWriter` always uses `withRootName` to override default wrapping behavior.

### 5.3. Performance Tuning
- For high-throughput environments, ensure that `SerializationFeature.INDENT_OUTPUT` is disabled (it is currently enabled in `JacksonXmlMapperProvider`). Indentation significantly increases XML size and processing time.

## 6. Conclusion
The Alpheus project demonstrates a sophisticated and high-performance implementation of Jackson XML. By successfully hybridizing manual StAX for hot paths and Jackson for complexity, it achieves a balance of speed and developer productivity. The primary area for optimization is the consolidation of the redundant string sanitization layers.
