# Roadmap: 10x FASTER

This document outlines the architectural shift required to achieve an order-of-magnitude (10x) performance improvement in the Alpheus AFP Parser, targeting a throughput of ~150 MB/s and a sub-100ms execution time for small files.

## 1. 100% Manual StAX Fast-Paths
**Current State:** ~90% of data passes through manual fast-paths; the remaining 10% falls back to Jackson reflection.
**10x Goal:** Eliminate `XmlMapper` and `ToXmlGenerator` from the hot path entirely.
**Implementation:**
- Implement manual `write` methods for every remaining Structured Field, Triplet, and Control Sequence.
- Remove Jackson annotations from domain classes to prevent accidental slow-path invocation.
- Use `WstxOutputFactory` directly to bypass the abstraction layer.

## 2. Zero-Allocation Encoding & String Handling
**Current State:** Frequent creation of `String` objects for numbers and EBCDIC-to-UTF8 conversion.
**10x Goal:** Zero `String` allocations during the primary serialization loop.
**Implementation:**
- Use StAX2 typed methods (`writeInt`, `writeLong`, `writeBinary`) for all attributes and elements.
- Implement a specialized EBCDIC-to-UTF-8 stream encoder that writes directly to the Woodstox/Aalto output buffer. ✅
- Use pre-allocated `byte[]` or `ByteBuffer` templates for common XML tags.

## 3. GraalVM Native Image
**Current State:** JVM startup and JIT warm-up take 500ms-800ms, dominating the time for small files (as seen in `10x10.md`).
**10x Goal:** Reduce "floor" execution time to <10ms.
**Implementation:**
- Configure the project for GraalVM Native Image compilation.
- Replace dynamic reflection and classpath scanning with static configuration.
- Eliminate JIT overhead by performing Ahead-of-Time (AOT) compilation.

## 4. SIMD-Accelerated Scanning
**Current State:** `AFPScanner` uses a scalar loop to find `0x5A` markers.
**10x Goal:** Utilize hardware-level parallelism for record-skipping.
**Implementation:**
- Use the **Java Vector API** (Incubator) to scan for the `0x5A` prefix across 256-bit or 512-bit registers.
- Accelerate EBCDIC character validation and sanitization using SIMD instructions.

## 5. Template-Based Serialization
**Current State:** Multiple method calls per field (`writeStartElement`, `writeAttribute`, `writeEndElement`).
**10x Goal:** Serialize a field in a single memory copy operation.
**Implementation:**
- Generate pre-computed XML byte templates for each Structured Field type.
- Use "hole-punching" to inject variable data (like coordinates or lengths) directly into the template.
- Use `Unsafe` or `Foreign Function & Memory API` (Project Panama) for ultra-fast memory copies.

## 6. Kernel-Level I/O & Direct Buffer Orchestration
**Current State:** Data is copied between heap and native memory during I/O.
**10x Goal:** Zero-copy from disk to XML output.
**Implementation:**
- Use `DirectByteBuffer` exclusively for both input (Mmap) and output.
- Orchestrate parallel page fragments such that they are generated directly into their final position in a massive pre-allocated direct buffer.
- Use NIO `GatheringByteChannel` to flush the entire document in a minimal number of syscalls.

## 7. Multi-Stage Parallel Pipeline
**Current State:** Parallelism is limited to page-level parsing and serialization in a single task.
**10x Goal:** Maximize CPU utilization through stage-based pipelining.
**Implementation:**
- **Stage 1 (I/O & Scan):** Single-threaded SIMD scanner identifying page boundaries.
- **Stage 2 (Parse):** Parallel workers converting AFP bytes to a lightweight binary intermediate representation (IR).
- **Stage 3 (Serialize):** Parallel workers converting IR to XML fragments using templates.
- **Stage 4 (Commit):** Single-threaded vectorized writer.
