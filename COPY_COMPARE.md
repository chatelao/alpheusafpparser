# Comparative Analysis: AFP-to-XML Conversion vs. Pure Parallel File Copy

This document compares the current directory-mode processing in `Afp2Xml` with a theoretical pure parallel file copy in Java, identifying the primary time-consuming elements.

## 1. Data Volume and I/O Expansion
A pure file copy has a 1:1 I/O ratio (Input Size == Output Size). In contrast, AFP-to-XML conversion involves significant data expansion:
- **Expansion Ratio:** Typically **4x to 12x**, as defined in `SFSizeEstimator.java`.
  - Simple fields have a ~5x multiplier.
  - PTOCA (Presentation Text) often reaches **12x** due to verbose XML tags for every control sequence and character.
- **Impact:** The conversion process must write significantly more data than a file copy, making it I/O-bound on the output side much earlier than a simple copy.

## 2. CPU Overhead (Processing vs. Moving)
A file copy (especially using `FileChannel.transferTo`) can often be performed with minimal CPU involvement (zero-copy). Conversion requires:
- **Parsing:** Decoding the binary AFP Structured Field Introducers (SFI) and triplets.
- **PTOCA/GOCA Decoding:** High-frequency control sequences (like `AMI`, `RMI`) must be parsed and mapped to XML attributes or elements.
- **XML Serialization:** Using Jackson/StAX2 (Aalto or Woodstox) to generate well-formed XML, which involves string encoding, escaping, and buffering.
- **Sanitization:** The `SanitizingXMLStreamWriter` checks for invalid XML characters in every text segment.

## 3. Memory Allocation and Garbage Collection
- **File Copy:** Uses small, reusable buffers or direct memory mapping, leading to negligible GC pressure.
- **AFP Conversion:**
  - Every Structured Field and PTOCA sequence is instantiated as a Java object.
  - Frequent string allocations for XML tag names and attribute values.
  - `AfpJacksonXmlWriter` uses manual StAX writing to mitigate some Jackson overhead, but the allocation rate remains high compared to a byte-level copy.

## 4. Synchronization and Orchestration
- **Parallel File Copy:** Individual files can be copied independently with zero inter-thread synchronization.
- **Current Directory Mode:**
  - If outputting to a **directory**, files are processed in parallel via an `ExecutorService`.
  - If outputting to **stdout (`-`)**, the `OrderedOutputOrchestrator` introduces synchronization. It must buffer fragments and ensure sequential output order, leading to `wait()`/`notifyAll()` overhead and potential head-of-line blocking if one file's processing is slower than others.

## 5. Metadata and File System Operations
- `Afp2Xml` performs directory listing, file sorting, and per-file output stream creation, similar to a parallel copy, but the overhead is dominated by the content conversion rather than these metadata operations.

## Summary Table
| Feature | Pure Parallel File Copy | AFP-to-XML (Afp2Xml) |
| :--- | :--- | :--- |
| **I/O Ratio** | 1:1 | 1:4 to 1:12 |
| **CPU Usage** | Near-zero (DMA/Zero-copy) | High (Decoding + XML Gen) |
| **Memory Pressure**| Low (Buffered/Mapped) | High (Object churn + Strings) |
| **Bottleneck** | Disk I/O / Bus Bandwidth | CPU / XML Expansion I/O |
| **Scaling** | Linear with Disk Throughput | Limited by CPU Cores and I/O Expansion |
