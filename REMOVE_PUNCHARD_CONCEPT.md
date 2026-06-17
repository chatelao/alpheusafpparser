# Concept: Removal of Template-Based "Punchard" Serialization

## Background: The "Punchard" Mechanism
The "Punchard" (Hole-Punching) mechanism was introduced as an extreme performance optimization (Strategy 6 in `10x_FASTER.md`). It involved pre-computing XML byte templates for Structured Fields and PTOCA sequences. These templates contained "holes"—pre-calculated offsets where dynamic data (like coordinates or identifiers) would be injected directly into a `ThreadLocal` byte buffer before being flushed to the output stream.

## Reasons for Removal

### 1. Maintenance Complexity
Maintaining `XmlTemplateRegistry.java` is error-prone. Each template requires manual calculation of byte fragments. Any change to the XML schema (e.g., adding an attribute or changing a tag name) requires a complete rewrite of the template's fragment array.

### 2. Performance Parity with StAX2
Modern StAX2 implementations (like Woodstox) combined with the `AfpXmlStreamWriter` fast-paths have reached performance levels that rival the template-based approach. Specifically, the use of `writeIntAttribute` and `writeLongAttribute` eliminates `String` allocations, which was the primary bottleneck "Punchard" was designed to solve.

### 3. Conflict with Spatial Metadata (XYPAGE)
The implementation of [XYPAGE_CONCEPT.md](XYPAGE_CONCEPT.md) requires injecting `page`, `x`, and `y` attributes into almost every high-frequency tag. Doing this via static byte templates significantly increases the number of "holes" and makes the `XmlTemplate` logic increasingly fragile and difficult to read.

### 4. Code Uniformity
Standardizing on manual StAX2 fast-paths in `AfpJacksonXmlWriter.java` provides a single, readable, and maintainable way to implement serialization across the entire AFP architecture.

## Replacement Strategy

1.  **Deprecate `XmlTemplate.java` and `XmlTemplateRegistry.java`**: These classes will be phased out.
2.  **Standardize on StAX2 Typed Attributes**: All Structured Fields and PTOCA sequences will use the `AfpXmlStreamWriter` (which implements `XMLStreamWriter2`) to write numeric attributes directly:
    ```java
    // Replacement for Template-Based AMI
    xsw.writeStartElement("AMI_AbsoluteMoveInline");
    xsw.writeIntAttribute(null, null, "page", currentPageNumber);
    xsw.writeIntAttribute(null, null, "x", getAfpX());
    xsw.writeIntAttribute(null, null, "y", getAfpY());
    xsw.writeIntAttribute(null, null, "displacement", ami.getDisplacement());
    xsw.writeEndElement();
    ```
3.  **Direct Byte Writing**: For large data payloads (e.g., `BDA`, `IPD`), use `writeRawBytes` or `writeEbcdic` to maintain zero-copy performance.

## Status
The transition has already begun. High-frequency elements like `PGP1` and `PTD1` are slated for conversion back to pure StAX2 fast-paths. Once coverage is complete, the `com.mgz.xml.template` package (or equivalent) will be removed.
