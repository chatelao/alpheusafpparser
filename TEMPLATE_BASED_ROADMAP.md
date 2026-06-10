# Template-Based Serialization Roadmap

This document outlines the phased implementation of Strategy 6 from `10x_FASTER.md`: Template-Based Serialization. The goal is to serialize complex Structured Fields and PTOCA sequences in a single memory copy operation using pre-computed XML byte templates and "hole-punching" for variable data.

## Status Summary
- **Phase 1: Infrastructure & Primitive Templates**: ✅ Complete
- **Phase 2: Static Structured Field Templates**: 🚧 In Progress
- **Phase 3: Dynamic Hole-Punching Engine**: 🚧 In Progress
- **Phase 4: Vectorized Assembly & Output**: ⏳ Pending

---

## Phase 1: Infrastructure & Primitive Templates ✅
Establish the foundation for byte-level templating.

- ✅ **XmlTagTemplates**: Implemented pre-allocated `byte[]` for high-frequency tags (`<text>`, `<points>`, `TRN`, `GC`).
- ✅ **AfpXmlStreamWriter Integration**: Updated the stream writer to support direct `writeRawBytes` to bypass UTF-16 overhead.
- ✅ **XmlIndenter Integration**: Indentation logic optimized to use pre-allocated byte arrays.

## Phase 2: Static Structured Field Templates 🚧
Generate byte templates for fixed-structure fields.

- ✅ **Template Generator**: Implemented `TemplateGenerator` utility to generate fragments from XML snippets.
- ✅ **Mnemonic-Based Registry**: Implemented `XmlTemplateRegistry` to map AFP mnemonics to their respective XML byte templates.
- 🚧 **Triplet Templating**: Extended templating to common triplets (AQ, RLI, CR, MU).

## Phase 3: Dynamic Hole-Punching Engine ✅
Inject variable data directly into the pre-computed byte buffers.

- ✅ **Offset Mapping**: (Alternative) Implemented fragment-based `XmlTemplate` which effectively maps offsets for variable data.
- ✅ **Fast Integer-to-Byte Conversion**: Implemented `FastIntConverter` for high-performance integer-to-UTF8-byte conversion.
- ✅ **Buffer Orchestration**: Implemented `ThreadLocal` assembly buffer in `XmlTemplate` for single-copy flushed writing.

## Phase 4: Vectorized Assembly & Output ⏳
Maximize throughput by minimizing individual syscalls and buffer copies.

- ⏳ **Gathering Byte Channels**: Use `GatheringByteChannel` to flush multiple template fragments in a single operation.
- ⏳ **SIMD Hole-Filling**: (Optional) Explore SIMD instructions for filling multiple holes in a single pass.
- ⏳ **End-to-End Performance Verification**: Benchmarking vs. manual StAX and Jackson fast-paths.
