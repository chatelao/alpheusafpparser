# Speed Optimization Design: Production-Scale XML Serialization

This document outlines the design for the next phase of performance optimizations, focusing on the bottlenecks identified in the Audit 3 (Real-World Production Data).

## 1. Fast-Path Expansion for High-Frequency Fields

The current `AfpJacksonXmlWriter` uses manual StAX fast-paths for `NOP`, `TLE`, `BAG`, and `PTX`. However, production data shows that `MCF` and additional PTOCA control sequences are significant hotspots.

### A. MCF (Map Coded Font) Fast-Path
`MCF_MapCodedFont_Format2` contains repeating groups with triplets. We will implement a `writeMCFDirectly` method that:
1. Iterates through the repeating groups.
2. For each repeating group, manually writes the `mcf2RepeatingGroup` start element.
3. Manually writes common triplets (like `FullyQualifiedName`) using the existing `writeTriplet` infrastructure.
4. Falls back to Jackson only for rare/unsupported triplets.

### B. PTOCA Movement Fast-Path
`AMI` (Absolute Move Inline) and `AMB` (Absolute Move Baseline) occur frequently in production PTX streams. We will:
1. Add `AMI_AbsoluteMoveInline` and `AMB_AbsoluteMoveBaseline` to the fast-path switch in `writeControlSequence`.
2. Implement manual serialization for these sequences, avoiding the recursive `fragmentMapper.writeValue(g, cs)` call.

### C. TLE (Tag Logical Element) Refinement
Ensure `writeTLEDirectly` handles all common triplets natively to minimize Jackson fallback.

---

## 2. Jackson `ToXmlGenerator` Reuse

Currently, `AfpJacksonXmlWriter` creates a new `ToXmlGenerator` for every field that falls back to Jackson serialization:

```java
ToXmlGenerator g = (ToXmlGenerator) fragmentMapper.getFactory().createGenerator(xsw);
fragmentMapper.writer().withRootName(rootName).writeValue(g, sf);
```

While `xsw` (the underlying `XMLStreamWriter`) is reused, the `ToXmlGenerator` wrapper is instantiated repeatedly, causing significant allocation pressure.

### Design: Cached Generator
We will introduce a `cachedGenerator` field in `AfpJacksonXmlWriter`.
1. The generator will be initialized lazily.
2. Since `ToXmlGenerator` from Jackson 2.x doesn't support a simple "reset" for a new `XMLStreamWriter` in the same way some other generators do, we will investigate if the `ObjectWriter` can be used more efficiently or if we can use a `ThreadLocal` pool of generators if the writer is used in a multi-threaded context (though the current writer is single-threaded per output stream).
3. **Primary Optimization**: Shift more fields to the manual fast-path, which avoids the generator altogether by writing directly to `xsw`.

---

## 3. PTOCA Recursion Reduction

For complex PTOCA fields, the current design often mixes manual StAX and Jackson:
1. `writePTXDirectly` starts the `PTX` element.
2. It loops through control sequences.
3. It calls `writeControlSequence`.
4. `writeControlSequence` either uses a fast-path or calls Jackson.

If Jackson is called, it might re-serialize the entire control sequence object. We will ensure that the fast-path is used for the most common 90% of control sequences found in production files to keep the generator inactive as much as possible.
