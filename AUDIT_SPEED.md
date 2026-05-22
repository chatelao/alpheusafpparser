# Speed Audit: AFP Processing (IBM273 / CP273)

This document details the performance measurement and analysis of the Alpheus AFP Parser when processing 1MB to 10MB AFP files encoded in IBM273 (EBCDIC German).

## 1. Test Environment & Methodology

- **Input File**: 1MB and 10MB AFP files generated with `tools/generate_large_afp.py`.
- **Content**: ~9,700 (for 1MB) and ~97,000 (for 10MB) `NOP` (No Operation) Structured Fields with 100-byte payloads.
- **Task**: Full conversion to XML using `Afp2Xml` CLI, redirecting output to `/dev/null` to isolate parsing and serialization overhead from disk I/O.
- **Tools**: `time` utility for baseline, `Java Flight Recorder (JFR)` for profiling.

## 2. Measurement Results

| File Size | Number of Fields | Baseline Execution Time (avg) | Throughput |
| :--- | :--- | :--- | :--- |
| 1 MB | ~9,700 | 5.2s | 0.19 MB/s |
| 10 MB | ~97,000 | 14.5s | 0.69 MB/s |

*Note: The higher throughput for 10MB is due to JVM warmup and JIT compilation.*

## 3. Bottleneck Analysis (Hotspots)

Profiling with JFR identified the following critical bottlenecks:

### A. JAXB Marshaller Creation (High Frequency)
In `AfpStreamingXmlWriter.writeFieldDirectly`, a new `Marshaller` is created for *every* structured field.
```java
JAXBContext jaxbContext = Afp2XmlWriter.getCachedJaxbContext(classes);
Marshaller marshaller = jaxbContext.createMarshaller(); // <--- Bottleneck
```
Creating a Marshaller is a heavyweight operation involving internal state initialization and reflection. For a 10MB file with 97,000 fields, this happens 97,000 times.

### B. Expensive "Human Readable" Check
`StructuredFieldBaseData.decodeAFP` (used by `NOP`, `OCD`, etc.) attempts to determine if binary data is text to provide a `<text>` element in the XML.
```java
if (UtilCharacterEncoding.isHumanReadable(data, config.getAfpCharSet())) {
  text = new String(data, config.getAfpCharSet());
}
```
`isHumanReadable` performs a `new String(data, charset)` and then iterates over all characters. This is done for every `NOP` field, even if it contains pure binary data.

### C. JAXB Context Cache Key Generation
`Afp2XmlWriter.getCachedJaxbContext` sorts a list of classes to generate a cache key.
```java
var sortedClasses = new ArrayList<>(classes);
sortedClasses.sort(Comparator.comparing(Class::getName)); // <--- Overhead
```
While cached, the overhead of creating the list and sorting it for every field adds up.

## 4. Proposed Improvements

### 1. Pool or Reuse JAXB Marshallers
Implement a thread-local pool for `Marshaller` instances in `AfpStreamingXmlWriter`. Reusing Marshallers instead of re-creating them will significantly reduce CPU usage and object allocation.

### 2. Optimize `isHumanReadable` Check
- Implement a faster `isHumanReadable` check that works directly on `byte[]` without creating a `String` object.
- For EBCDIC (CP500/CP273), check for common printable byte ranges (e.g., 0x40 for space, 0xC1-0xC9 for A-I) directly.
- Add a size limit: skip the check if the payload is larger than a certain threshold (e.g., 1KB) unless specifically requested.

### 3. Streamline JAXB Context Lookup
In streaming mode, the number of distinct SF classes is small. We can cache the `JAXBContext` (and ideally the `Marshaller`) based on the `sf.getClass()` directly if it's a standard SF without complex nested dependencies.

### 4. NIO Zero-Copy Enhancements
While the parser already supports `ByteBuffer`, ensuring the CLI uses `MappedByteBuffer` for all operations will reduce kernel-to-user space copying.

## 5. Summary
The current streaming implementation is memory-efficient (O(1) footprint) but CPU-bound due to the overhead of per-field JAXB marshalling and redundant character encoding checks. Implementing the proposed optimizations is expected to increase throughput by at least 5x-10x.
