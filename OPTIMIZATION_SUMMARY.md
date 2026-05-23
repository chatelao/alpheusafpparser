# Optimization Summary: Top 10 Techniques

This document summarizes the top 10 performance optimization techniques implemented in the Alpheus AFP Parser project, providing explanations and matching code samples.

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
