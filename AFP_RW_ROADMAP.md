# Alpheus AFP Read-Write (Modification) API Roadmap

This document outlines the phased implementation plan for developing a high-performance, high-fidelity Java API to load, modify, and write back AFP (MO:DCA) documents within the Alpheus framework, as defined in `AFP_RW_CONCEPT.md` and `AFP_RW_DESIGN.md`.

## Status Summary
- **Phase 1: Base Auto-Recalculation & Builders**: ⏳ Pending
- **Phase 2: Streaming Modification Pipeline**: ⏳ Pending
- **Phase 3: Structural DOM API**: ⏳ Pending

---

## Phase 1: Base Auto-Recalculation & Builders ⏳
Establish the foundation of the mutation engine by implementing automated length calculations and safe, validated field construction.

### 1.1. Memory and Pooling Lifecycle Integration
- [ ] **State Flags**: Add `boolean isPooled` status flag to `StructuredField` and `Triplet` base classes.
- [ ] **Pool Detachment**: Implement `setPooled(boolean)` to allow structured fields and triplets to be detached from object pools.
- [ ] **Safe Release**: Modify the `release()` method in pooling structures to act as a no-op if `isPooled == false`, preventing double-release or premature recycling of mutated fields.

### 1.2. Automated Field Length & Flag Adjustments
- [ ] **Introducer Length Calculation**: Enhance `writeFullStructuredField` within `StructuredField` to dynamically compute:
  $$\text{SFLength} = \text{Length of Introducer (8 or 12 bytes)} + \text{Length of Net Payload} + \text{Length of Padding}$$
- [ ] **Structured Padding**: Implement automated management of padding bytes when `SFFlag.isPadded` is set. Ensure the final padding byte represents the correct padding block length.
- [ ] **Dynamic Padding Recalculation**: Automatically recalculate or strip padding when the underlying field payload length is modified.

### 1.3. Triplet Length & Repeating Group Recalculation
- [ ] **Triplet Recalculation**: Ensure fields implementing `IHasTriplets` automatically compute and update the `TripletLength` (first byte) of each child triplet recursively during serialization.
- [ ] **Repeating Group Recalculation**: For fields containing repeating groups (such as `LFE_LoadFontEquivalence` or `LE_LoadEquivalence`), implement automated count and offset recalculations during serialization.

### 1.4. Raw Vendor/Custom Field Passthrough
- [ ] **Preservation of Undefined Fields**: Ensure any unrecognized structured field mapped to `Undefined` has its raw payload stored intact.
- [ ] **Fidelity Serialization**: Write back `Undefined` fields byte-for-byte, ensuring third-party or proprietary production metadata is not lost.

### 1.5. Fluent Builders for Core Fields
- [ ] **AfpFieldBuilder**: Define the base builder interface `AfpFieldBuilder<T extends StructuredField>`.
- [ ] **TleBuilder**: Implement a fluent builder for `TLE` (Tag Logical Element) structured fields.
  - [ ] Automatically generate Fully Qualified Name (FQN) triplets for Attribute Name (`0x02`) and optional Attribute Value.
  - [ ] Support character encoding validation for attribute values.
- [ ] **BngBuilder & EngBuilder**: Implement fluent builders for `BNG` (Begin Named Page Group) and `ENG` (End Named Page Group).
- [ ] **NopBuilder**: Implement a fluent builder for `NOP` (No Operation) fields.

---

## Phase 2: Streaming Modification Pipeline ⏳
Deliver a single-pass, callback-driven event engine capable of processing and modifying multi-gigabyte transactional print streams with $O(1)$ memory usage.

### 2.1. Stream Filter Infrastructure
- [ ] **AfpStreamFilter**: Implement the core streaming parser pipeline.
- [ ] **AfpFilterCallback**: Define the interface with a `process(StructuredField sf)` method returning a `FilterAction` (`KEEP`, `DROP`, or `REPLACE`).
- [ ] **Action Handler Orchestration**:
  - `KEEP`: Directly write the unmodified field back to the output stream.
  - `DROP`: Do not write the field; perform parser/pool cleanup.
  - `REPLACE`: Replace the current field with a list of user-provided replacement fields (e.g. injecting a `TLE` after a `BPG`).

### 2.2. Thread-Safe Pool Detachment
- [ ] **Automatic Detachment**: Ensure any replacement fields generated during filtering automatically have `isPooled` set to `false`.
- [ ] **Memory Integrity**: Verify that original fields mapped to `DROP` or `REPLACE` are safely released back to their respective pools if appropriate.

### 2.3. Integration & Fidelity Testing
- [ ] **Parity Validation**: Create an integration suite executing byte-by-byte roundtrips of unmodified files, ensuring a 100% hash match with the original input stream.
- [ ] **Streaming Validation**: Test the metadata injection pipeline on large AFP streams (100MB+) under memory constraints to verify that heap footprint remains constant ($O(1)$ scaling).

---

## Phase 3: Structural DOM API ⏳
Develop the high-level hierarchical Document Object Model to facilitate complex document pagination, page merging, re-ordering, and indexing.

### 3.1. Node Hierarchy & Composition
- [ ] **AfpNode**: Create the base tree node interface defining parent-child relationships.
- [ ] **AfpCompositeNode**: Implement the abstract composite container mapping matching Begin/End boundaries (e.g., `BDT`/`EDT`, `BNG`/`ENG`).
- [ ] **AfpLeafNode**: Implement a leaf container wrapping individual non-boundary structured fields (such as `TLE`, `NOP`, `PTX`).
- [ ] **Concrete Structural Nodes**:
  - `AfpDocumentNode`: Enclosed by `BDT`/`EDT`.
  - `AfpPageGroupNode`: Enclosed by `BNG`/`ENG`.
  - `AfpPageNode`: Enclosed by `BPG`/`EPG`.
  - `AfpActiveEnvironmentGroupNode`: Enclosed by `BAG`/`EAG`.
  - `AfpResourceGroupNode`: Enclosed by `BRG`/`ERG`.

### 3.2. DOM Construction & Serialization
- [ ] **DOM Parser**: Implement tree-building logic using `AFPParser` to construct an `AfpDom` structure.
- [ ] **Automatic Pool Detachment on Insertion**: Ensure DOM mutation methods (e.g. `addChild`) automatically detach added nodes (`setPooled(false)`).
- [ ] **AfpDom Serialization**: Support writing the tree back to an output stream via `dom.writeTo(OutputStream os, AFPParserConfiguration config)`.

### 3.3. Document Composition Operations
- [ ] **Page Ordering/Sorting**: Enable re-ordering or sorting of page nodes within the DOM.
- [ ] **Blank Page Pruning**: Implement a helper utility to analyze page children (identifying the absence of GOCA, IOCA, or PTX) and safely prune blank pages from the DOM tree.
- [ ] **Metadata Propagation**: Support copying or inserting TLE nodes dynamically across page groups.

### 3.4. Validation Suite & Preflight Testing
- [ ] **MODCA Structural Verification**: Implement validation rules checking that mutated DOM structures comply with strict MO:DCA hierarchic rules prior to serialization.
- [ ] **RIP Preflighting**: Validate serialized outputs against industry preflight utilities (e.g. Compart, Solimar, or Compart DocBridge) to confirm syntax compliance.

---

## Implementation Strategy
1. **High Fidelity Above All**: If a structured field is not mutated, Alpheus must write it back byte-for-byte. We prioritize keeping the original content exactly intact over formatting.
2. **Zero-Overhead Math**: Developers should never have to manually edit structured field lengths, triplet counts, or repeating group offsets.
3. **Safety First**: Pooling lifecycle management must be bulletproof to prevent silent memory corruptions and double-free exceptions.
