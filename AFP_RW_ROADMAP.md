# Alpheus AFP Read-Write (Modification) API Roadmap

This document outlines the detailed phased implementation plan for developing a high-performance, high-fidelity Java API to load, modify, and write back AFP (MO:DCA) documents within the Alpheus framework, as defined in `AFP_RW_CONCEPT.md` and detailed in `AFP_RW_DESIGN.md`.

## Status Summary
- **Phase 1: Base Auto-Recalculation & Builders**: ⏳ In Progress (1.1, 1.4, 1.5 Completed)
- **Phase 2: Streaming Modification Pipeline**: ⏳ Pending
- **Phase 3: Structural DOM API**: ⏳ Pending

---

## Architectural Goals & Design Philosophy

The Alpheus AFP Read-Write API is designed to bridge the gap between low-overhead streaming processing and high-level object-oriented document manipulation.

1. **High Fidelity Write-Back**:
   Every parsed byte that is not modified must be written back exactly as it was read. This includes preserving exact SFI attributes, structural flags, extensions, custom/vendor proprietary structured fields (mapped to `Undefined`), and structured padding settings.
2. **Multi-tiered Access Layers**:
   - **Low-Level Raw API**: Direct byte/ByteBuffer and `StructuredField` manipulation for extreme-performance use cases.
   - **Streaming Filter API**: Callback-driven transformation engine with a strict $O(1)$ memory footprint for multi-gigabyte files.
   - **DOM-Like Tree API**: Object-oriented hierarchical representation mapping logical MO:DCA structures for pagination, composition, and page merging.
3. **Zero-Overhead Recalculation Engine**:
   Automating the calculation of Structured Field Introducer lengths, triplet lengths, and repeating group counts during serialization, shielding the developer from manual binary math.
4. **Seamless Integration with Alpheus Memory Management**:
   Define strict detachment and re-pooling lifecycles inside pre-allocated pools (`StructuredFieldPool`, `TripletPool`, `SfiPool`) to prevent memory leaks and premature object reuse.

---

## Phase 1: Base Auto-Recalculation & Builders ⏳
Establish the foundation of the mutation engine by implementing automated length calculations, state tracking, and safe, validated field construction.

### 1.1. Memory and Pooling Lifecycle Integration ✅
- [x] **State Flags & Property Additions**:
  - Add `boolean isPooled` status flag to `StructuredField` and `Triplet` base classes to track if an object is owned by the global pools.
- [x] **Pool Detachment Mechanic**:
  - Implement `setPooled(boolean)` to allow structured fields and triplets to be detached. When a node is detached, it will not be returned to pools when released or garbage collected.
- [x] **Safe Release & Recycler Logic**:
  - Update `release()` across `StructuredFieldPool`, `TripletPool`, and `SfiPool` to act as a safe no-op if `isPooled == false`. This prevents catastrophic double-releases or premature recycling of modified fields.

### 1.2. Automated Field Length & Flag Adjustments
- [ ] **Introducer Length Calculation**:
  - Enhance `writeFullStructuredField` within `StructuredField` to dynamically compute:
    $$\text{SFLength} = \text{Length of Introducer (8 or 12 bytes)} + \text{Length of Net Payload} + \text{Length of Padding}$$
  - Inject this computed value automatically into the first two bytes of the SFI.
- [ ] **Structured Padding**:
  - Implement automated management of padding bytes when `SFFlag.isPadded` is set.
  - Ensure the final padding byte represents the correct padding block length.
- [ ] **Dynamic Padding Recalculation**:
  - Automatically recalculate or strip padding when the underlying field payload length is modified by adding or removing triplets/repeating groups.

### 1.3. Triplet Length & Repeating Group Recalculation
- [ ] **Recursive Triplet Recalculation**:
  - Ensure fields implementing `IHasTriplets` automatically compute and update the `TripletLength` (first byte) of each child triplet recursively during serialization.
- [ ] **Repeating Group Recalculation**:
  - For fields containing repeating groups (such as `LFE_LoadFontEquivalence` or `LE_LoadEquivalence`), implement automated count and offset recalculations during serialization.

### 1.4. Raw Vendor/Custom Field Passthrough ✅
- [x] **Preservation of Undefined Fields**:
  - Ensure any unrecognized structured field mapped to `Undefined` has its raw payload stored intact.
- [x] **Fidelity Serialization**:
  - Write back `Undefined` fields byte-for-byte, ensuring third-party or proprietary production metadata is not lost.

### 1.5. Fluent Builders for Core Fields ✅
- [x] **AfpFieldBuilder**:
  - Define the base builder interface `AfpFieldBuilder<T extends StructuredField>`.
- [x] **TleBuilder (Tag Logical Element)**:
  - Implement a fluent builder for `TLE` structured fields.
  - Automatically generate Fully Qualified Name (FQN) triplets for Attribute Name (`0x02`) and optional Attribute Value (`0x02` with type value).
  - Support character encoding validation for attribute values.
- [x] **BngBuilder & EngBuilder**:
  - Implement fluent builders for `BNG` (Begin Named Page Group) and `ENG` (End Named Page Group).
- [x] **NopBuilder**:
  - Implement a fluent builder for `NOP` (No Operation) fields.

---

## Phase 2: Streaming Modification Pipeline ⏳
Deliver a single-pass, callback-driven event engine capable of processing and modifying multi-gigabyte transactional print streams with $O(1)$ memory usage.

### 2.1. Stream Filter Infrastructure
- [ ] **AfpStreamFilter**:
  - Implement the core streaming parser pipeline that accepts an `InputStream` and writes to an `OutputStream` sequentially.
- [ ] **AfpFilterCallback**:
  - Define the interface with a `process(StructuredField sf)` method returning a `FilterAction` (`KEEP`, `DROP`, or `REPLACE`).
- [ ] **Action Handler Orchestration**:
  - `KEEP`: Directly write the unmodified field back to the output stream.
  - `DROP`: Do not write the field; perform parser/pool cleanup.
  - `REPLACE`: Replace the current field with a list of user-provided replacement fields (e.g. injecting a `TLE` after a `BPG`).

### 2.2. Thread-Safe Pool Detachment
- [ ] **Automatic Detachment**:
  - Ensure any replacement fields generated during filtering automatically have `isPooled` set to `false`.
- [ ] **Memory Integrity**:
  - Verify that original fields mapped to `DROP` or `REPLACE` are safely released back to their respective pools if appropriate.

### 2.3. Integration & Fidelity Testing
- [ ] **Parity Validation**:
  - Create an integration suite executing byte-by-byte roundtrips of unmodified files, ensuring a 100% hash match with the original input stream.
- [ ] **Streaming Validation**:
  - Test the metadata injection pipeline on large AFP streams (100MB+) under memory constraints to verify that heap footprint remains constant ($O(1)$ scaling).

---

## Phase 3: Structural DOM API ⏳
Develop the high-level hierarchical Document Object Model to facilitate complex document pagination, page merging, re-ordering, and indexing.

### 3.1. Node Hierarchy & Composition
- [ ] **AfpNode**:
  - Create the base tree node interface defining parent-child relationships (`getParent()`, `setParent()`, `getChildren()`, `addChild()`, `removeChild()`, and `getStructuredField()`).
- [ ] **AfpCompositeNode**:
  - Implement the abstract composite container mapping matching Begin/End boundaries (e.g., `BDT`/`EDT`, `BNG`/`ENG`).
- [ ] **AfpLeafNode**:
  - Implement a leaf container wrapping individual non-boundary structured fields (such as `TLE`, `NOP`, `PTX`).
- [ ] **Concrete Structural Nodes**:
  - `AfpDocumentNode`: Enclosed by `BDT`/`EDT`.
  - `AfpPageGroupNode`: Enclosed by `BNG`/`ENG`.
  - `AfpPageNode`: Enclosed by `BPG`/`EPG`.
  - `AfpActiveEnvironmentGroupNode`: Enclosed by `BAG`/`EAG`.
  - `AfpResourceGroupNode`: Enclosed by `BRG`/`ERG`.

### 3.2. DOM Construction & Serialization
- [ ] **DOM Parser**:
  - Implement tree-building logic using `AFPParser` to construct an `AfpDom` structure.
- [ ] **Automatic Pool Detachment on Insertion**:
  - Ensure DOM mutation methods (e.g. `addChild`) automatically detach added nodes (`setPooled(false)`).
- [ ] **AfpDom Serialization**:
  - Support writing the tree back to an output stream via `dom.writeTo(OutputStream os, AFPParserConfiguration config)`.

### 3.3. Document Composition Operations
- [ ] **Page Ordering/Sorting**:
  - Enable re-ordering or sorting of page nodes within the DOM.
- [ ] **Blank Page Pruning**:
  - Implement a helper utility to analyze page children (identifying the absence of GOCA, IOCA, or PTX) and safely prune blank pages from the DOM tree.
- [ ] **Metadata Propagation**:
  - Support copying or inserting TLE nodes dynamically across page groups.

### 3.4. Validation Suite & Preflight Testing
- [ ] **MODCA Structural Verification**:
  - Implement validation rules checking that mutated DOM structures comply with strict MO:DCA hierarchic rules prior to serialization.
- [ ] **RIP Preflighting**:
  - Validate serialized outputs against industry preflight utilities (e.g. Compart, Solimar, or Compart DocBridge) to confirm syntax compliance.

---

## Code Usage Examples

### Streaming Metadata Injection (TLE Injection)
```java
import com.mgz.afp.streaming.AfpStreamFilter;
import com.mgz.afp.streaming.AfpFilterCallback;
import com.mgz.afp.base.StructuredField;
import com.mgz.afp.builder.TleBuilder;
import com.mgz.afp.parser.AFPParserConfiguration;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Collections;
import java.util.List;

public class StreamingMetadataInjector {
  public void injectMetadata(String sourcePath, String destPath) throws Exception {
    try (FileInputStream fis = new FileInputStream(sourcePath);
         FileOutputStream fos = new FileOutputStream(destPath)) {

      AFPParserConfiguration config = new AFPParserConfiguration();
      AfpStreamFilter filter = new AfpStreamFilter(fis, fos, config);

      filter.filter(new AfpFilterCallback() {
        private final TleBuilder tleBuilder = new TleBuilder();
        private List<StructuredField> replacements = null;

        @Override
        public AfpStreamFilter.FilterAction process(StructuredField sf) throws Exception {
          // If we encounter a Begin Page field, append a custom index TLE right after it
          if (sf.getStructuredFieldIntroducer().getSFTypeID() == SFTypeID.BPG_BeginPage) {
            StructuredField tle = tleBuilder
                .withAttribute("CustomerName", "John Doe")
                .build();
            replacements = List.of(sf, tle);
            return AfpStreamFilter.FilterAction.REPLACE;
          }
          return AfpStreamFilter.FilterAction.KEEP;
        }

        @Override
        public List<StructuredField> getReplacements() {
          return replacements;
        }
      });
    }
  }
}
```

### High-Level DOM Manipulation (Page Stripping)
```java
import com.mgz.afp.dom.AfpDom;
import com.mgz.afp.dom.AfpNode;
import com.mgz.afp.dom.AfpPageNode;
import com.mgz.afp.parser.AFPParserConfiguration;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Iterator;

public class BlankPageStripper {
  public void stripBlankPages(String sourcePath, String destPath) throws Exception {
    AFPParserConfiguration config = new AFPParserConfiguration();

    // Load hierarchical DOM
    AfpDom dom = AfpDom.loadFrom(new FileInputStream(sourcePath), config);

    // Iterate through pages and prune blank ones
    Iterator<AfpNode> iterator = dom.getChildren().iterator();
    while (iterator.hasNext()) {
      AfpNode node = iterator.next();
      if (node instanceof AfpPageNode pageNode) {
        if (isPageBlank(pageNode)) {
          iterator.remove(); // Removes the page and its boundary elements from the DOM
        }
      }
    }

    // Write-back the mutated tree with automatic offset/length adjustments
    try (FileOutputStream fos = new FileOutputStream(destPath)) {
      dom.writeTo(fos, config);
    }
  }

  private boolean isPageBlank(AfpPageNode page) {
    // Custom check logic based on the absence of PTX, GAD, etc.
    return page.getChildren().stream()
        .noneMatch(n -> n.getStructuredField() instanceof com.mgz.afp.ptoca.PTX_PresentationTextData);
  }
}
```

---

## Implementation Strategy & Quality Control
1. **High Fidelity Above All**: If a structured field is not mutated, Alpheus must write it back byte-for-byte. We prioritize keeping the original content exactly intact over formatting.
2. **Zero-Overhead Math**: Developers should never have to manually edit structured field lengths, triplet counts, or repeating group offsets.
3. **Safety First**: Pooling lifecycle management must be bulletproof to prevent silent memory corruptions and double-free exceptions.
4. **Comprehensive Automated Verification**:
   - Ensure every implemented field builder features validation testing.
   - Run stream filter round-trips against a comprehensive suite of "golden" AFP test streams to verify bit-by-bit compatibility and memory leaks.
