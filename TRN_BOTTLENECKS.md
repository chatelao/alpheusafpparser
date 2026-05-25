# TRN_TransparentData Processing: Specifications, Implementation, and Bottlenecks

## 1. Specifications (PTOCA Reference [PTOCA-4-589])

The **Transparent Data (TRN)** control sequence contains a sequence of code points that are presented without a scan for embedded control sequences.

### Syntax
| Offset | Type | Name | Range | Meaning |
| :--- | :--- | :--- | :--- | :--- |
| 0 | CODE | PREFIX | X'2B' | Control Sequence Prefix |
| 1 | CODE | CLASS | X'D3' | Control sequence class |
| 2 | UBIN | LENGTH | 2–255 | Control sequence length |
| 3 | CODE | TYPE | X'DA' – X'DB' | Control sequence function type |
| 4–256 | CHAR | TRNDATA | N/A | Transparent data |

### Semantics
- Specifies a string of code points to be processed as graphic characters.
- No code point within the data field is recognized as a Control Sequence Prefix (CSP).
- The current inline position is incremented for each graphic character.

### Pragmatics
- **Double-Byte Fonts**: Data length must be even. If odd, the last byte is skipped ([PTOCA-4-595]).
- **Unicode**: If encoding is Unicode, code points must be valid Unicode; otherwise, the remainder is skipped.
- **Object Space**: If TRN causes character box to exceed object space, exception EC-0103 exists.

---

## 2. Implementation Details

### Data Model (`PTOCAControlSequence.TRN_TransparentData`)
- **Fields**:
  - `String transparentData`: The decoded string representation.
  - `byte[] transparentDataEBCDIC`: Raw EBCDIC bytes (used if `isUseEBCDICData` is true).
- **Decoding (`decodeAFP`)**:
  - Uses `UtilCharacterEncoding.decodeEbcdic` to convert raw bytes to a Java String based on the configured `AfpCharSet`.
- **XML Output (`getText`)**:
  - Invokes `UtilCharacterEncoding.sanitizeForXml(transparentData)` to ensure the string is safe for XML 1.0.
- **Encoding (`writeAFP`)**:
  - Either writes `transparentDataEBCDIC` directly or encodes `transparentData` back to the configured `AfpCharSet`.

### Serialization (`AfpJacksonXmlWriter`)
- Implements a manual StAX-based writer for `TRN_TransparentData` to avoid Jackson reflection overhead.
- Directly writes `transparentData` and `text` (sanitized) elements to the `XMLStreamWriter`.

---

## 3. Speed Bottlenecks

Based on analysis and `PTX_OPTIMIZATION_REPORT.md`, the following bottlenecks impact TRN processing:

### 1. Cumulative Volume of Small Elements
PTX fields often contain a massive number of small TRN sequences (e.g., thousands per document). Even with "fast-paths", the sheer number of object allocations and method calls adds up.

### 2. XML Sanitization Overhead
The `UtilCharacterEncoding.sanitizeForXml` method iterates through every character of the `transparentData` string to check `isValidXml10CodePoint`. For large documents with high text density, this O(N) check per TRN sequence becomes significant.

### 3. Character Decoding (EBCDIC to UTF-16)
Converting EBCDIC bytes to Java Strings during `decodeAFP` involves charset lookups and buffer conversions. While optimized for common charsets (Cp500, Cp273), it remains a CPU-intensive task when multiplied by 100k+ instances.

### 4. Instrumentation Jitter
If `MnemonicPerformanceMonitor` is active, every `writeStartElement` and `writeEndElement` for TRN sub-elements triggers `System.nanoTime()` and thread-local map lookups. This can dwarf the actual serialization time for small TRN payloads.

### 5. Redundant Formatting and Indentation
Writing `\n    ` (indentation) for every TRN sequence and its sub-elements (`<text>`, etc.) increases the XML size and adds to the I/O load.

### 6. String Allocations
The round-trip from `byte[]` (AFP) -> `String` (Java) -> `String` (Sanitized) -> `byte[]` (XML) creates significant garbage collection pressure in high-throughput environments.
