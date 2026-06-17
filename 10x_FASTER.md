# Roadmap: 10x FASTER

This document outlines the architectural shift required to achieve an order-of-magnitude (10x) performance improvement in the Alpheus AFP Parser, targeting a throughput of ~150 MB/s and a sub-100ms execution time for small files.

## Strategy Summary (Risk vs. Gain)

| Strategy | Potential Gain | Implementation Risk | Status |
| :--- | :--- | :--- | :--- |
| **1. 100% Manual StAX Fast-Paths** | Very High | Low | 🚧 In Progress |
| **3. Zero-Allocation Encoding** | High | Medium | 🚧 In Progress |
| **4. Multi-Stage Parallel Pipeline** | High | Medium | 🚧 In Progress |
| **5. SIMD-Accelerated Scanning** | Medium | Medium | ⏳ Pending |
| **6. Template-Based Serialization** | Very High | High | 🛑 RETIRED |
| **7. Kernel-Level I/O & Zero-Copy** | Medium | High | ⏳ Pending |

---

## 1. 100% Manual StAX Fast-Paths
Eliminate `XmlMapper` and `ToXmlGenerator` from the hot path entirely.

- ✅ **Core MO:DCA Fast-Paths**: Implemented manual StAX writing for `MDR`, `MGO`, `MPO`, `MSU`, `MMC`, `PGP` (Formats 1 & 2), `MCD`, and `OCD`.
- ✅ **FOCA Fast-Paths**: Implemented manual writing for `CFC`, `CFI`, `CPC`, `CPD`, `CPI`, `FND`, `FNG`, `FNI`, `FNM`, `FNN`, `FNO`, and `FNP`.
- ✅ **BCOCA Fast-Paths**: Implemented manual writing for `BBC`, `EBC`, `BDD`, and `BDA`.
- ✅ **PTOCA SF Fast-Paths**: Implemented manual writing for `BPT`, `EPT`, and `PTD` (Format 1 & 2).
- ✅ **GOCA SF Fast-Paths**: Implemented manual writing for `BGR`, `EGR`, and `GDD`.
- ✅ **IOCA Segment Fast-Paths**: Implemented manual writing for all `IPD_Segment` types.
- ✅ **Triplets Fast-Paths**: Full coverage achieved for all 67+ standard triplets.
- ✅ **PTOCA CS Fast-Paths**: Implemented manual writing for `TRN`, `GC`, `AMI`, `AMB`, `RMI`, `RMB`, `SIM`, `SCFL`, `SBI`, `BLN`, `BSU`, `ESU`, `STO`, `STC`, `USC`, `SIA`, `SVI`, `SEC`, `DIR`, `DBR`, `NOP`, `TBM`, `OVS`, `RPS`, `UCT`, `GLC`, `ENC`, `SKI`, `SEA`, `GIR`, `GAR`, and `GOR`.
- 🚧 **Complete Coverage**: Implement manual `write` methods for every remaining Structured Field and Drawing Order.
- ⏳ **Annotation Removal**: Remove Jackson annotations from domain classes to prevent accidental slow-path invocation.
- ⏳ **Direct Woodstox Integration**: Use `WstxOutputFactory` directly to bypass the abstraction layer.

## 3. Zero-Allocation Encoding & String Handling
Zero `String` allocations during the primary serialization loop.

- ✅ **Typed Attribute Writing**: Use StAX2 typed methods (`writeInt`, `writeLong`) for all primary numeric attributes.
- ✅ **Direct Stream Encoding**: Implement a specialized EBCDIC-to-UTF-8 stream encoder that writes directly to the Woodstox buffer.
- ⏳ **Typed Binary Writing**: Use `writeBinary` for large data payloads to avoid hex/base64 string overhead.
- ✅ **Tag Templating**: Use pre-allocated `byte[]` or `ByteBuffer` templates for common XML tags.

## 4. Multi-Stage Parallel Pipeline
Maximize CPU utilization through stage-based pipelining.

- ✅ **Basic Parallelism**: Implement page-level parallel processing via the `-p` CLI flag.
- 🚧 **Stage 1 (I/O & Scan)**: Single-threaded scanner identifying record boundaries.
- ⏳ **Stage 2 (Parse)**: Parallel workers converting AFP bytes to a lightweight binary intermediate representation (IR).
- ⏳ **Stage 3 (Serialize)**: Parallel workers converting IR to XML fragments using templates.
- ⏳ **Stage 4 (Commit)**: Single-threaded vectorized writer for final output assembly.

## 5. SIMD-Accelerated Scanning
Utilize hardware-level parallelism for record-skipping and character validation.

- ⏳ **Vector API Integration**: Use the **Java Vector API** (Incubator) to scan for the `0x5A` prefix across 512-bit registers.
- ⏳ **Accelerated Sanitization**: Accelerate EBCDIC character validation and XML sanitization using SIMD instructions.

## 6. Template-Based Serialization (RETIRED)
Serialize a field in a single memory copy operation. See [TEMPLATE_BASED_ROADMAP.md](TEMPLATE_BASED_ROADMAP.md) and [REMOVE_PUNCHARD_CONCEPT.md](REMOVE_PUNCHARD_CONCEPT.md).

> [!CAUTION]
> This strategy is being retired in favor of **100% Manual StAX Fast-Paths** using StAX2 typed attributes, which provides equivalent performance with significantly lower maintenance overhead.

- ✅ **Infrastructure**: Implemented `XmlTagTemplates` and integrated `writeRawBytes` into `AfpXmlStreamWriter`.
- 🛑 **XML Byte Templates**: Generation of pre-computed XML byte templates is discontinued.
- 🛑 **Hole-Punching**: Dynamic injection into templates is discontinued in favor of StAX2 `writeIntAttribute`.

## 7. Kernel-Level I/O & Direct Buffer Orchestration
Zero-copy from disk to XML output.

- ✅ **Aggressive I/O**: Use `Mmap` for input files when the `-a` flag is active.
- ⏳ **Direct Buffer Usage**: Use `DirectByteBuffer` exclusively for both input and output.
- ⏳ **Syscall Optimization**: Use NIO `GatheringByteChannel` or Linux `splice` (via JNI/Panama) to flush the document.
