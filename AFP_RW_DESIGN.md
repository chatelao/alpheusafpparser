# Design: AFP Read-Write (Modification) API for Java

This document specifies the technical design for a high-performance, high-fidelity Java API to load, modify, and write back AFP (MO:DCA) documents within the Alpheus framework, elaborating on the core design principles defined in `AFP_RW_CONCEPT.md`.

---

## 1. Architectural Goals & Design Philosophy

The Alpheus AFP Read-Write API is designed to bridge the gap between low-overhead streaming processing and high-level object-oriented document manipulation.

### 1.1. High Fidelity Write-Back
Every parsed byte that is not modified must be written back exactly as it was read. This includes:
- **Exact Introducer Attributes**: Preserving structural field flags, extensions, and padding settings unless explicitly mutated.
- **Custom / Vendor Fields**: Preserving proprietary or undefined structured fields (mapped to `Undefined.java`) byte-for-byte to ensure third-party print management workflows are not broken.
- **Structured Padding**: Retaining original padding lengths or dynamically recalculating padding bytes according to user preferences.

### 1.2. Multi-tiered Access Layers
To handle diverse use-cases ranging from high-speed streaming metadata injection to complex document composition:
1. **Low-Level Raw API**: Direct byte/ByteBuffer and `StructuredField` manipulation. Useful for low-overhead, high-performance tasks.
2. **DOM-Like Tree API**: A structural hierarchy model mapping logical MO:DCA blocks. Ideal for document indexing, pagination, page merging, and interactive applications.
3. **Streaming Filter API**: A single-pass, callback-driven transformation engine with an $O(1)$ memory footprint. Ideal for multi-gigabyte transactional print streams.

### 1.3. Zero-Overhead Recalculation Engine
All structural and field-level recalculations (such as Structured Field Introducer lengths, triplet lengths, and repeating group counts) must happen automatically during serialization, shielding the developer from manual binary math.

### 1.4. Seamless Integration with Alpheus Memory Management
The framework utilizes pre-allocated object pools (`StructuredFieldPool`, `TripletPool`, `SfiPool`). The RW API must strictly define detachment and re-pooling lifecycles to prevent both memory leaks and premature object reuse.

---

## 2. Component Architecture & Interfaces

The Read-Write API comprises three primary paradigms: Low-Level, DOM, and Streaming.

```
                  +-----------------------------------+
                  |             AFP Stream            |
                  +-----------------+-----------------+
                                    |
                                    v
                  +-----------------------------------+
                  |             AFPParser             |
                  +-----------------+-----------------+
                                    |
            +-----------------------+-----------------------+
            | (DOM Paradigm)                                | (Streaming Paradigm)
            v                                               v
+-----------------------+                       +-----------------------+
|        AfpDom         |                       |   AfpStreamFilter     |
| (AfpDocumentNode,etc) |                       | (Callback-driven)     |
+-----------+-----------+                       +-----------+-----------+
            |                                               |
            | (Mutations / Builder Updates)                 | (On-the-fly mutations)
            v                                               v
+-----------------------+                       +-----------------------+
|       AfpWriter       |                       |       AfpWriter       |
+-----------+-----------+                       +-----------+-----------+
            |                                               |
            +-----------------------+-----------------------+
                                    |
                                    v
                  +-----------------------------------+
                  |          Mutated Stream           |
                  +-----------------------------------+
```

### 2.1. The DOM-Like Tree API
The DOM API represents a logical, structural tree mapping of the parsed AFP. It conforms to the MO:DCA hierarchic rules.

```java
package com.mgz.afp.dom;

import com.mgz.afp.base.StructuredField;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

/**
 * Base node in the AFP Document Object Model.
 */
public interface AfpNode {
  AfpNode getParent();
  void setParent(AfpNode parent);

  List<AfpNode> getChildren();
  void addChild(AfpNode child);
  void addChild(int index, AfpNode child);
  void removeChild(AfpNode child);

  /**
   * Returns the underlying MO:DCA StructuredField if this is a leaf node,
   * or the defining structured field (e.g., BDT) if this is a composite node.
   */
  StructuredField getStructuredField();
}
```

#### Composite vs. Leaf Nodes
The DOM tree consists of **Composite Nodes** (which map matching Begin/End boundaries) and **Leaf Nodes** (which map individual non-boundary structured fields).

```java
package com.mgz.afp.dom;

import com.mgz.afp.base.StructuredField;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public abstract class AfpCompositeNode implements AfpNode {
  private AfpNode parent;
  private final List<AfpNode> children = new ArrayList<>();
  private final StructuredField beginField;
  private final StructuredField endField;

  protected AfpCompositeNode(StructuredField beginField, StructuredField endField) {
    this.beginField = beginField;
    this.endField = endField;
  }

  @Override
  public AfpNode getParent() { return parent; }

  @Override
  public void setParent(AfpNode parent) { this.parent = parent; }

  @Override
  public List<AfpNode> getChildren() { return Collections.unmodifiableList(children); }

  @Override
  public void addChild(AfpNode child) {
    children.add(child);
    child.setParent(this);
  }

  @Override
  public void addChild(int index, AfpNode child) {
    children.add(index, child);
    child.setParent(this);
  }

  @Override
  public void removeChild(AfpNode child) {
    if (children.remove(child)) {
      child.setParent(null);
    }
  }

  @Override
  public StructuredField getStructuredField() { return beginField; }
  public StructuredField getEndField() { return endField; }
}
```

Concrete implementations map the MO:DCA standard structures:
- `AfpDocumentNode`: Enclosed by `BDT` / `EDT`.
- `AfpPageGroupNode`: Enclosed by `BNG` / `ENG`.
- `AfpPageNode`: Enclosed by `BPG` / `EPG`.
- `AfpActiveEnvironmentGroupNode`: Enclosed by `BAG` / `EAG`.
- `AfpResourceGroupNode`: Enclosed by `BRG` / `ERG`.
- `AfpLeafNode`: Wraps single fields like `TLE`, `NOP`, `PTX`, etc.

### 2.2. The Streaming Filter API
For single-pass modifications on extremely large streams, the `AfpStreamFilter` executes without parsing the entire stream into memory.

```java
package com.mgz.afp.streaming;

import com.mgz.afp.base.StructuredField;
import com.mgz.afp.parser.AFPParser;
import com.mgz.afp.parser.AFPParserConfiguration;
import java.io.InputStream;
import java.io.OutputStream;

public class AfpStreamFilter {
  private final AFPParser parser;
  private final OutputStream outputStream;
  private final AFPParserConfiguration config;

  public AfpStreamFilter(InputStream is, OutputStream os, AFPParserConfiguration config) {
    this.parser = new AFPParser(config);
    this.outputStream = os;
    this.config = config;
  }

  /**
   * Reads, processes, and writes back the AFP stream sequentially.
   */
  public void filter(AfpFilterCallback callback) throws Exception {
    StructuredField sf;
    try {
      while ((sf = parser.parseNextSF()) != null) {
        // Callback returns action: KEEP, DROP, or REPLACE
        FilterAction action = callback.process(sf);
        switch (action) {
          case KEEP:
            sf.writeAFP(outputStream, config);
            break;
          case DROP:
            // Do not write, just allow parser/pool cleanup
            break;
          case REPLACE:
            for (StructuredField replacement : callback.getReplacements()) {
              replacement.writeAFP(outputStream, config);
            }
            break;
        }
        // Return structured field back to pool if it was not detached
        if (sf.isPooled()) {
          sf.release();
        }
      }
    } finally {
      parser.quitParsing();
    }
  }

  public enum FilterAction {
    KEEP, DROP, REPLACE
  }
}
```

```java
package com.mgz.afp.streaming;

import com.mgz.afp.base.StructuredField;
import java.util.List;

public interface AfpFilterCallback {
  AfpStreamFilter.FilterAction process(StructuredField sf) throws Exception;

  /**
   * Returns replacement fields if action is REPLACE.
   */
  List<StructuredField> getReplacements();
}
```

---

## 3. Fluent Builder API Design

Creating structured fields manually is simplified and validated through builders. Each builder validates parameters before returning a detached, write-ready object.

```
+--------------------+
|     TleBuilder     |
+--------------------+
| - attributeName    |
| - attributeValue   |
+--------------------+
          |
          v .build()
+---------------------------------+
|   TLE_TagLogicalElement (Model) |
+---------------------------------+
```

### 3.1. Builder Hierarchy and Interface
```java
package com.mgz.afp.builder;

import com.mgz.afp.base.StructuredField;

public interface AfpFieldBuilder<T extends StructuredField> {
  T build();
}
```

### 3.2. Detailed TLE Builder Implementation
The `TleBuilder` creates valid Tag Logical Elements containing Fully Qualified Name (FQN) triplets (Type `0x02` Attribute Name and Attribute Value).

```java
package com.mgz.afp.builder;

import com.mgz.afp.modca.TLE_TagLogicalElement;
import com.mgz.afp.triplets.Triplet;
import com.mgz.afp.triplets.Triplet.FullyQualifiedName;
import com.mgz.afp.base.StructuredFieldIntroducer;
import com.mgz.afp.enums.SFTypeID;
import java.util.ArrayList;
import java.util.List;

public class TleBuilder implements AfpFieldBuilder<TLE_TagLogicalElement> {
  private String attributeName;
  private String attributeValue;

  public TleBuilder withAttribute(String name, String value) {
    if (name == null || name.isEmpty()) {
      throw new IllegalArgumentException("Attribute name cannot be null or empty.");
    }
    this.attributeName = name;
    this.attributeValue = value;
    return this;
  }

  @Override
  public TLE_TagLogicalElement build() {
    TLE_TagLogicalElement tle = new TLE_TagLogicalElement();

    // Create Structured Field Introducer (SFI)
    StructuredFieldIntroducer sfi = new StructuredFieldIntroducer();
    sfi.setTypeID(SFTypeID.TLE_TagLogicalElement);
    tle.setStructuredFieldIntroducer(sfi);

    // Create FQN triplets
    List<Triplet> triplets = new ArrayList<>();

    // Triplet 1: Attribute Name
    FullyQualifiedName nameTriplet = new FullyQualifiedName();
    nameTriplet.setType(FullyQualifiedName.GlobalID_Use.AttributeName);
    nameTriplet.setName(attributeName);
    triplets.add(nameTriplet);

    // Triplet 2: Attribute Value (if exists)
    if (attributeValue != null) {
      FullyQualifiedName valueTriplet = new FullyQualifiedName();
      valueTriplet.setType(FullyQualifiedName.GlobalID_Use.AttributeValue);
      valueTriplet.setName(attributeValue);
      triplets.add(valueTriplet);
    }

    tle.setTriplets(triplets);
    tle.setPooled(false); // Detach from pooling mechanism

    return tle;
  }
}
```

Builders for `BNG` (Begin Named Page Group), `ENG` (End Named Page Group), and `NOP` (No Operation) will follow similar validation and initialization patterns.

---

## 4. High-Fidelity Write-Back & Recalculation Engine

Ensuring generated AFP passes strict industry validations (e.g., AFP Consortium standards, production RIP preflights) requires fully automated recalculation of field structure parameters.

### 4.1. Automatic Length and Flag Adjustment
Structured fields are serialized through the `writeFullStructuredField` methods defined in `StructuredField.java`. The modification engine enhances this logic with deep validation:

1. **Introducer Length Recalculation**:
   During serialization, `StructuredField` computes the sum of:
   $$\text{SFLength} = \text{Length of Introducer (8 or 12 bytes)} + \text{Length of Net Payload} + \text{Length of Padding}$$
   This value is automatically injected into the first two bytes of the SFI.

2. **Structured Padding Management**:
   If `SFFlag.isPadded` is active, the padding bytes are automatically appended. The last byte of the padding must represent the length of the padding block. If the developer mutates payload bytes, the padding block is dynamically recalculated or stripped to retain layout consistency.

### 4.2. Triplet Length Tracking
Triplets reside within structured fields implementing `IHasTriplets`. When a structured field is updated:
- The length of each triplet is recalculated recursively.
- The `TripletLength` (the first byte of the triplet) is dynamically written prior to serializing the parent structured field.

### 4.3. Raw Vendor/Custom Field Passthrough
For structured fields of unknown or proprietary type (mapped to `Undefined` class):
- The original byte payload is stored intact.
- Serialization is performed byte-for-byte, ensuring that proprietary production tracking codes, print-engine commands, or custom vendor indexes are fully preserved without regression.

---

## 5. Memory Management & Pooling Lifecycle

To avoid $GC$ pauses and memory fragmentation when converting large documents, Alpheus relies on strict object pooling. When introducing mutations, objects can transition between different pool-managed states.

### 5.1. Object State Lifecycle
```
+------------------+     Acquire     +------------------+
|                  | --------------> |                  |
|      Pooled      |                 |      Active      |
|                  | <-------------- |                  |
+------------------+     Release     +------------------+
                                              |
                                              | Detach (Mutable DOM/Builder)
                                              v
                                     +------------------+
                                     |     Detached     |
                                     | (isPooled=false) |
                                     +------------------+
                                              |
                                              | Serialized & Discarded
                                              v
                                     +------------------+
                                     |     Garbage      |
                                     |    Collected     |
                                     +------------------+
```

1. **Pooled State**: The object resides in `StructuredFieldPool` or `TripletPool`.
2. **Active State**: The object is borrowed by the parser.
3. **Detached State**: When a user extracts an object from the parser to add it to a DOM tree, or instantiates a new field via a Builder, the object is **detached** by calling `setPooled(false)`. This guarantees that the parser or stream manager will never return the object back to the global pool prematurely.
4. **Released State**: Objects that are not detached are returned to their pools immediately after being written back, preserving $O(1)$ memory usage.

### 5.2. Implementing Safe Pool Transfers
To protect against double-release and memory leakage:
- Every `StructuredField` must maintain an internal flag `boolean isPooled`.
- When `isPooled == false`, the `release()` method acts as a no-op, relying on standard Java GC once references are cleared.
- DOM modification methods (like `AfpNode.addChild()`) must automatically invoke `setPooled(false)` on the added node.

---

## 6. API Usage Code Examples

### 6.1. High-Performance Streaming Filter (TLE Injection)
Injecting metadata into huge transactional files on-the-fly:

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

### 6.2. High-Level DOM Manipulation (Page Ordering / Stripping)
Loading an AFP document into a tree, removing blank pages, and serializing the modified structure:

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

## 7. Implementation Roadmap & Testing Strategy

The development plan is divided into three sequential phases.

```
+-----------------------------------+
|              Phase 1              |
| - TleBuilder, BngBuilder          |
| - SFI/Triplet auto-recalc         |
+-----------------+-----------------+
                  |
                  v
+-----------------------------------+
|              Phase 2              |
| - AfpStreamFilter                 |
| - Thread-safe Pool Detachment     |
+-----------------+-----------------+
                  |
                  v
+-----------------------------------+
|              Phase 3              |
| - AfpDom Hierarchical Mapping     |
| - Validation Suite & RIP Testing  |
+-----------------------------------+
```

### Phase 1: Base Auto-Recalculation & Builders
- [ ] Implement `isPooled` lifecycle status flags across all `StructuredField` implementations.
- [ ] Enhance `writeFullStructuredField` methods to handle dynamic recalculations.
- [ ] Create fluent builders for `TLE`, `BNG`, `ENG`, and `NOP`.

### Phase 2: Streaming Modification Pipeline
- [ ] Implement `AfpStreamFilter` with the action callback orchestration.
- [ ] Write rigorous integration tests executing byte-by-byte roundtrips of unchanged files (must yield 100% hash parity).
- [ ] Verify streaming modification under memory-constrained environments to guarantee $O(1)$ scaling.

### Phase 3: Structural DOM API
- [ ] Develop parent-child tree mapping structures (`AfpCompositeNode`, `AfpLeafNode`).
- [ ] Implement tree traversal and structural parsing logic.
- [ ] Write test cases that load an AFP file, perform node-swapping/re-ordering, and output files to confirm correct validation with industry validation utilities.
