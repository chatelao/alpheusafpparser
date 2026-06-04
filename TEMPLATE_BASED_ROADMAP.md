# Template-Based Serialization Roadmap

This document outlines the phased implementation of Strategy 6 from `10x_FASTER.md`: Template-Based Serialization. The goal is to serialize complex Structured Fields and PTOCA sequences in a single memory copy operation using pre-computed XML byte templates and "hole-punching" for variable data.

## Status Summary
- **Phase 1: Infrastructure & Primitive Templates**: ✅ Complete
- **Phase 2: Static Structured Field Templates**: ⏳ Pending
- **Phase 3: Dynamic Hole-Punching Engine**: ⏳ Pending
- **Phase 4: Vectorized Assembly & Output**: ⏳ Pending

---

## Phase 1: Infrastructure & Primitive Templates ✅
Establish the foundation for byte-level templating.

- ✅ **XmlTagTemplates**: Implemented pre-allocated `byte[]` for high-frequency tags (`<text>`, `<points>`, `TRN`, `GC`).
- ✅ **AfpXmlStreamWriter Integration**: Updated the stream writer to support direct `writeRawBytes` to bypass UTF-16 overhead.
- ✅ **XmlIndenter Integration**: Indentation logic optimized to use pre-allocated byte arrays.

## Phase 2: Static Structured Field Templates ⏳
Generate byte templates for fixed-structure fields.

- ⏳ **Template Generator**: Implement a utility to pre-calculate the XML structure for each Structured Field type.
- ⏳ **Mnemonic-Based Registry**: Map AFP mnemonics to their respective XML byte templates.
- ⏳ **Triplet Templating**: Extend templating to common triplets (e.g., FQN, ResourceLocalIdentifier).

## Phase 3: Dynamic Hole-Punching Engine ⏳
Inject variable data directly into the pre-computed byte buffers.

- ⏳ **Offset Mapping**: Calculate the byte offsets for variable data (e.g., coordinates, IDs, lengths) within the templates.
- ⏳ **Fast Integer-to-Byte Conversion**: Implement high-performance integer-to-UTF8-byte conversion that writes directly to the template "holes".
- ⏳ **Buffer Orchestration**: Manage a pool of "hot" templates that can be quickly filled and flushed.

## Phase 4: Vectorized Assembly & Output ⏳
Maximize throughput by minimizing individual syscalls and buffer copies.

- ⏳ **Gathering Byte Channels**: Use `GatheringByteChannel` to flush multiple template fragments in a single operation.
- ⏳ **SIMD Hole-Filling**: (Optional) Explore SIMD instructions for filling multiple holes in a single pass.
- ⏳ **End-to-End Performance Verification**: Benchmarking vs. manual StAX and Jackson fast-paths.
