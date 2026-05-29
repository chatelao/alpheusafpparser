# Optimization Summary: Top 30 Techniques

This document summarizes the top 30 performance optimization techniques implemented in the Alpheus AFP Parser project, providing explanations and matching code samples.

## 1. Manual StAX Fast-Paths
**Explanation:** High-frequency AFP elements (like `PTX`, `TLE`, `NOP`) are written directly to the `XMLStreamWriter`. This avoids the overhead of Jackson's object-to-XML mapping for the majority of the data.
**Code Sample (`AfpJacksonXmlWriter.java`):**
```java
private void writeFieldDirectly(StructuredField sf) throws Exception {
    xsw.writeCharacters("  ");
    if (sf instanceof NOP_NoOperation nop) {
        writeNopDirectly(nop);
    } else if (sf instanceof TLE_TagLogicalElement tle) {
        writeTleDirectly(tle);
    } else if (sf instanceof PTX_PresentationTextData ptx) {
        writePtxDirectly(ptx);
    } else {
        // Fallback to Jackson only for rare fields
        String rootName = sf.getClass().getSimpleName();
        fragmentMapper.writer().withRootName(rootName).writeValue(getFragmentGenerator(), sf);
    }
    xsw.writeCharacters("\n");
}
```

## 2. Zero-Copy Parsing
**Explanation:** Large data payloads are managed using `ByteBuffer.slice()` instead of copying data into new `byte[]` arrays during parsing. This significantly reduces memory allocation and CPU cycles.
**Code Sample (`StructuredFieldBaseData.java`):**
```java
@Override
public void decodeAFP(java.nio.ByteBuffer buffer, int offset, int length, AFPParserConfiguration config) throws AFPParserException {
    int actualLength = length != -1 ? length : buffer.limit() - offset;
    if (actualLength > 0) {
        // ... text detection ...
        // Zero-copy slice of the original buffer
        payloadBuffer = buffer.slice(offset, actualLength).asReadOnlyBuffer();
        data = null;
    }
}
```

## 3. Object Pooling
**Explanation:** High-churn objects like Structured Field Introducers (SFI), Structured Fields, and Repeating Groups are pooled and reused to minimize Garbage Collection (GC) pressure.
**Code Sample (`SfiPool.java`):**
```java
public static StructuredFieldIntroducer acquire() {
    StructuredFieldIntroducer sfi = POOL.poll();
    if (sfi == null) {
        return new StructuredFieldIntroducer();
    }
    sfi.reset();
    return sfi;
}

public static void release(StructuredFieldIntroducer sfi) {
    if (sfi != null) {
        POOL.offer(sfi);
    }
}
```

## 4. Parallel Page Parsing
**Explanation:** Large AFP files are scanned for page boundaries (`BPG` fields), and individual pages are parsed in parallel across multiple CPU cores.
**Code Sample (`ParallelPageParser.java`):**
```java
for (int i = 0; i < pageOffsets.size(); i++) {
    long start = pageOffsets.get(i);
    long end = (i + 1 < pageOffsets.size()) ? pageOffsets.get(i + 1) : fileSize;

    AFPParserConfiguration taskConfig = config.clone();
    futures.add(executor.submit(new PageTask(taskConfig, start, end)));
}
```

## 5. Memory-Mapped I/O
**Explanation:** The parser leverages `MappedByteBuffer` to map the AFP file directly into the virtual memory space, allowing the OS to handle I/O efficiently via page faults.
**Code Sample (`AFPParserConfiguration.java`):**
```java
public ByteBuffer getByteBuffer() throws IOException {
    if (byteBuffer == null && afpFile != null) {
        try (RandomAccessFile raf = new RandomAccessFile(afpFile, "r");
             FileChannel fc = raf.getChannel()) {
            byteBuffer = fc.map(FileChannel.MapMode.READ_ONLY, 0, fc.size());
        }
    }
    return byteBuffer;
}
```

## 6. Asynchronous I/O
**Explanation:** `AsynchronousFileChannel` is used to overlap I/O operations with CPU-intensive parsing, especially useful when loading page data for parallel processing.
**Code Sample (`AFPScanner.java`):**
```java
private List<Long> scanForAsyncChannel(SFTypeID typeID) {
    // ...
    Future<Integer> future = asyncChannel.read(chunk, currentFilePos);
    int bytesRead = future.get(); // Overlap can be achieved by reading ahead
    // ...
}
```

## 7. Fast Mnemonic Lookup
**Explanation:** Structured Field types (SFTypeID) are resolved using a static `HashMap` with an integer key derived from the 3-byte SFI identifier, replacing expensive linear searches.
**Code Sample (`SFTypeID.java`):**
```java
private static final Map<Integer, SFTypeID> VAL_MAP = new HashMap<>();

static {
    for (SFTypeID type : values()) {
        VAL_MAP.put(calcKey(type.sfClass.val, type.sfType.val, type.sfCategory.val), type);
    }
}

private static int calcKey(int sfClass, int sfType, int sfCategory) {
    return ((sfClass & 0xFF) << 16) | ((sfType & 0xFF) << 8) | (sfCategory & 0xFF);
}
```

## 8. EBCDIC Fast-Path Decoding
**Explanation:** Standard EBCDIC to UTF-8 conversion is optimized using static lookup tables and dedicated methods for common code pages (CP500, CP273), avoiding the overhead of `CharsetDecoder`.
**Code Sample (`UtilCharacterEncoding.java`):**
```java
private static final char[] EBCDIC_CP500_TO_UTF8 = { ... };

public static String decodeCp500(ByteBuffer buffer, int offset, int length) {
    char[] out = new char[length];
    for (int i = 0; i < length; i++) {
        out[i] = EBCDIC_CP500_TO_UTF8[buffer.get(offset + i) & 0xFF];
    }
    return new String(out);
}
```

## 9. Lazy Payload Extraction
**Explanation:** Data payloads are kept in the zero-copy `ByteBuffer` and only converted to a `byte[]` if explicitly requested by the application, using thread-safe double-checked locking.
**Code Sample (`StructuredFieldBaseData.java`):**
```java
public byte[] getData() {
    if (data == null && payloadBuffer != null) {
        synchronized (this) {
            if (data == null) {
                data = new byte[payloadBuffer.remaining()];
                // ... extract from buffer ...
            }
        }
    }
    return data;
}
```

## 10. XML Infrastructure Caching
**Explanation:** Expensive XML objects like `DocumentBuilder`, `XPath`, and `Transformer` are cached at the instance level to avoid the high cost of instantiation for every filtered field.
**Code Sample (`AfpJacksonXmlWriter.java`):**
```java
private javax.xml.parsers.DocumentBuilder cachedDocumentBuilder;
private javax.xml.xpath.XPath cachedXpath;

private void writeFieldWithXpath(StructuredField sf) throws Exception {
    if (cachedDocumentBuilder == null) {
        cachedDocumentBuilder = DBF.newDocumentBuilder();
    }
    // ... use cached objects ...
}
```

## 11. O(1) Enum Resolution
**Explanation:** Byte-level AFP identifiers (Type, Category) are resolved to Enum constants using static lookup arrays (sparse arrays) instead of iterative searches.
**Code Sample (`SFType.java`):**
```java
private static final SFType[] VAL_MAP = new SFType[256];

static {
    for (SFType type : values()) {
        VAL_MAP[type.val & 0xFF] = type;
    }
}

public static SFType valueOf(int sfTypeByte) {
    SFType type = VAL_MAP[sfTypeByte & 0xFF];
    return type != null ? type : Undefined;
}
```

## 12. Aalto XML Integration
**Explanation:** The project uses Aalto XML, a ultra-high-performance StAX implementation, for all XML stream reading and writing, bypassing the slower default JDK implementations.
**Code Sample (`JacksonXmlMapperProvider.java`):**
```java
import com.fasterxml.aalto.stax.InputFactoryImpl;
import com.fasterxml.aalto.stax.OutputFactoryImpl;

static {
    XML_MAPPER = new XmlMapper(new XmlFactory(new InputFactoryImpl(), new OutputFactoryImpl()));
}
```

## 13. Jackson Generator Reuse
**Explanation:** `AfpJacksonXmlWriter` reuses a single `ToXmlGenerator` instance for fallback serialization, minimizing the significant allocation overhead of creating new generators for every field.
**Code Sample (`AfpJacksonXmlWriter.java`):**
```java
private ToXmlGenerator fragmentGenerator;

private ToXmlGenerator getFragmentGenerator() throws java.io.IOException {
    if (fragmentGenerator == null) {
        fragmentGenerator = (ToXmlGenerator) fragmentMapper.getFactory().createGenerator(xsw);
    }
    return fragmentGenerator;
}
```

## 14. Shallow Field Realization
**Explanation:** The parser can build "shallow" objects containing only the 8-byte introducer. Full field realization (parsing the payload) is deferred until explicitly requested via `AFPParser.reload()`.
**Code Sample (`AFPParser.java`):**
```java
if (parserConf.isBuildShallow() && !mustDecode) {
    sfi.setActualConfig(parserConf.clone());
    // Payload is skipped; only SFI is stored
} else {
    sf.decodeAFP(buffer, payloadStart, lenOfSFData, parserConf);
}
```

## 15. Indentation-Aware Fast-Paths
**Explanation:** Manual StAX fast-paths explicitly manage indentation strings to maintain visual consistency with pretty-printed Jackson output without the performance overhead of a `PrettyPrinter`.
**Code Sample (`AfpJacksonXmlWriter.java`):**
```java
private void writeElement(String indent, String name, String value) throws Exception {
    if (value != null) {
        xsw.writeCharacters(indent);
        xsw.writeStartElement(name);
        xsw.writeCharacters(value);
        xsw.writeEndElement();
    }
}
```

## 16. NIO-based Human-Readable Detection
**Explanation:** `UtilCharacterEncoding` performs heuristic "human-readable" text detection directly on `ByteBuffer` slices, avoiding the need to copy data to a `byte[]` just for analysis.
**Code Sample (`UtilCharacterEncoding.java`):**
```java
public static boolean isHumanReadable(ByteBuffer buffer, int offset, int length, Charset charset) {
    // ...
    for (int i = 0; i < length; i++) {
        char c = EBCDIC_CP500_TO_UTF8[buffer.get(offset + i) & 0xFF];
        if (!Character.isISOControl(c) || c == '\n' || c == '\r' || c == '\t' || c == '\u0085') {
            printableCount++;
        }
    }
    return (double) printableCount / length >= 0.9;
}
```

## 17. Record-Skipping Navigation
**Explanation:** `AFPScanner` navigates AFP files by reading record lengths and jumping to the next `0x5A` marker, effectively skipping large data payloads during page boundary discovery.
**Code Sample (`AFPScanner.java`):**
```java
while (pos < limit) {
    if ((buffer.get(pos) & 0xFF) == 0x5A) {
        int sfLength = UtilBinaryDecoding.parseInt(buffer, pos + 1, 2);
        // ... type check ...
        pos += 1 + sfLength; // Jump directly to the next record
    } else {
        pos++;
    }
}
```

## 18. Double-Checked Locking
**Explanation:** Lazy realization of field payloads uses double-checked locking to ensure thread safety while minimizing synchronization overhead after the initial realization.
**Code Sample (`StructuredFieldBaseData.java`):**
```java
public byte[] getData() {
    if (data == null && payloadBuffer != null) {
        synchronized (this) {
            if (data == null) {
                // ... realize data from buffer ...
            }
        }
    }
    return data;
}
```

## 19. Singleton XML Infrastructure
**Explanation:** A centralized `XmlMapper` is configured once and shared globally, ensuring that expensive Jackson initialization and module registration (like `JaxbAnnotationModule`) happen only once.
**Code Sample (`JacksonXmlMapperProvider.java`):**
```java
public class JacksonXmlMapperProvider {
  private static final XmlMapper XML_MAPPER;
  static {
      // Extensive configuration once...
  }
  public static XmlMapper getMapper() { return XML_MAPPER; }
}
```

## 20. Specialized Triplet & PTOCA Fast-Paths
**Explanation:** Beyond standard fields, high-frequency internal components like `FullyQualifiedName` triplets and `TRN_TransparentData` control sequences have dedicated manual StAX paths.
**Code Sample (`AfpJacksonXmlWriter.java`):**
```java
private void writeControlSequence(PTOCAControlSequence cs) throws Exception {
    if (cs instanceof PTOCAControlSequence.TRN_TransparentData trn) {
        xsw.writeStartElement("TRN_TransparentData");
        writeElement("\n      ", "text", trn.getText());
        xsw.writeEndElement();
    } else {
        // Fallback only for rare control sequences
    }
}
```

## 21. Vectorized Writes (NIO Gathering)
**Explanation:** The output orchestrators leverage `GatheringByteChannel.write(ByteBuffer[])` to perform vectorized I/O. This allows flushing multiple XML fragments (e.g., pages) in a single system call, significantly reducing kernel-mode transitions and improving throughput.
**Code Sample (`OrderedResultCollector.java`):**
```java
if (channel != null) {
    ByteBuffer[] srcs = readyFragments.toArray(new ByteBuffer[0]);
    long totalBytes = 0;
    for (ByteBuffer b : srcs) totalBytes += b.remaining();
    long written = 0;
    while (written < totalBytes) {
        written += channel.write(srcs);
    }
}
```

## 22. [REMOVED] Tiered Direct Buffer Pooling
**Explanation:** (This technique was removed in June 2026 to simplify the I/O pipeline). Previously, it used a tiered, thread-safe pool to minimize the cost of `DirectByteBuffer` allocation by bucketing buffers by powers of two (8KB to 4MB).

## 23. Memory-Aware Back-pressure
**Explanation:** Parallel conversion of high-volume AFP data can easily overwhelm the output stream, especially when piping to stdout. A blocking back-pressure mechanism limits the total size of buffered fragments (default 64MB), pausing producers until the consumer catches up.
**Code Sample (`OrderedOutputOrchestrator.java`):**
```java
while (totalBufferedSize + len > maxBufferSize && !isNext(streamId, sequence)) {
    try {
        wait(); // Block until buffer space is released
    } catch (InterruptedException e) {
        // ...
    }
}
```

## 24. Heuristic XML Size Estimation
**Explanation:** Before allocating buffers or mapping memory segments, the system uses heuristic multipliers (e.g., 12.0x for PTX) to estimate the eventual XML size of an AFP structured field. This allows for more efficient pre-allocation and reduces re-allocations.
**Code Sample (`SFSizeEstimator.java`):**
```java
public static long estimateXmlSize(StructuredField sf) {
    int afpSize = sf.getStructuredFieldIntroducer().getSFLength();
    double multiplier = DEFAULT_MULTIPLIER;
    if (sf instanceof PTX_PresentationTextData) {
        multiplier = PTX_MULTIPLIER; // 12.0
    }
    // ... other multipliers ...
    return (long) (afpSize * multiplier);
}
```

## 25. Streaming Character Sanitization
**Explanation:** XML 1.0 does not allow certain control characters (like null bytes). Instead of pre-sanitizing strings (which creates copies), a decorator for `XMLStreamWriter2` handles sanitization on-the-fly during the write phase.
**Code Sample (`SanitizingXMLStreamWriter.java`):**
```java
@Override
public void writeCharacters(String text) throws XMLStreamException {
    super.writeCharacters(UtilCharacterEncoding.sanitizeForXml(text));
}
```

## 26. Jackson Sanitization Serializer
**Explanation:** For fields serialized via Jackson (the fallback path), a custom `JsonSerializer<String>` is registered globally. This ensures that even deeply nested or rare fields benefit from XML sanitization without manual intervention.
**Code Sample (`JacksonXmlMapperProvider.java`):**
```java
private static class SanitizingStringSerializer extends JsonSerializer<String> {
    @Override
    public void serialize(String value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        if (value != null) {
            gen.writeString(UtilCharacterEncoding.sanitizeForXml(value));
        }
    }
}
```

## 27. Deterministic Parallel Sequencing
**Explanation:** To maintain XML validity and deterministic output when parsing in parallel, the `OrderedResultCollector` uses a `TreeMap` to buffer out-of-order fragments. It only flushes to the output stream when the absolute next sequential fragment is available.
**Code Sample (`OrderedResultCollector.java`):**
```java
buffer.put(sequence, data);
// ...
while (!buffer.isEmpty() && buffer.firstKey() == nextSequence) {
    ByteBuffer fragment = buffer.remove(nextSequence);
    readyFragments.add(fragment);
    nextSequence++;
}
```

## 28. Disabling StAX Structure Validation
**Explanation:** In fragment mode (used during parallel assembly or XPath filtering), Aalto's strict XML structure validation is disabled. This avoids "second root" errors and reduces the overhead of tracking the document's tag stack for intermediate results.
**Code Sample (`AfpJacksonXmlWriter.java`):**
```java
static {
    XOF = new com.fasterxml.aalto.stax.OutputFactoryImpl();
    try {
        XOF.setProperty("org.codehaus.stax2.validation.checkStructure", false);
    } catch (Exception e) { /* ... */ }
}
```

## 29. Fragment Mapper Reuse
**Explanation:** Creating a new `XmlMapper` or `ToXmlGenerator` is expensive. The system reuses a dedicated `FRAGMENT_MAPPER` singleton that is pre-configured to omit XML declarations, significantly speeding up the generation of thousands of small XML fragments.
**Code Sample (`JacksonXmlMapperProvider.java`):**
```java
FRAGMENT_MAPPER = XML_MAPPER.copy();
FRAGMENT_MAPPER.configure(ToXmlGenerator.Feature.WRITE_XML_DECLARATION, false);
// ...
public static XmlMapper getFragmentMapper() { return FRAGMENT_MAPPER; }
```

## 30. Orchestrated Stream Serialization
**Explanation:** When converting an entire directory of AFP files to a single output (e.g., via the CLI), the `OrderedOutputOrchestrator` manages multiple logical streams. It ensures that while files are processed in parallel, their XML output remains contiguous and ordered by filename.
**Code Sample (`Afp2Xml.java`):**
```java
// Register each file as a logical stream to maintain order in stdout
final int streamId = orchestrator.registerStream();
executor.submit(() -> {
    try (OutputStream orchestratedOs = orchestrator.createStreamOutputStream(streamId)) {
        convertToXml(f, orchestratedOs, ...);
    }
});
```
