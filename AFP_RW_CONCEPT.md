# Concept: AFP Read-Write (Modification) API for Java

This document defines the architectural concept for providing a comprehensive Java API to load,
modify, and write back AFP (MO:DCA) documents using the Alpheus framework.

---

## 1. Architectural Goals

The main goals of the Alpheus AFP Read-Write API are:
1. **High Fidelity**: Retain all structured fields, triplets, padding, and vendor-specific data
   without corruption when parsing and re-writing.
2. **Flexible Abstraction Levels**:
   - **Low-Level API**: Access and manipulate structured fields and raw byte arrays directly.
   - **DOM-Like Tree API**: Navigate and modify logical MODCA hierarchies (Documents, Page Groups,
     Pages, Active Environment Groups, etc.).
   - **Streaming/Filter API**: Low-memory, single-pass modification of extremely large AFP
     files (O(1) memory footprint).
3. **Automatic Length & Offsets Recalculation**: Shield developers from having to manually compute
   Structured Field Introducer (SFI) lengths, triplet lengths, or offset tables.
4. **Integration with Pooling**: Respect and extend the existing Alpheus pool management to
   prevent memory leaks.

---

## 2. API Design & Core Components

```
                +-------------------+
                |   AFP Input File  |
                +---------+---------+
                          |
                          v
                +-------------------+
                |     AFPParser     |
                +---------+---------+
                          |
        +-----------------+-----------------+
        | (In-Memory DOM)                   | (Streaming Filter)
        v                                   v
+---------------+                   +---------------+
|    AfpDom     |                   |  AfpFilter    |
+-------+-------+                   +-------+-------+
        |                                   |
        v (User Modifications)              v (On-the-fly Edits)
+---------------+                   +---------------+
|   AfpWriter   |                   |   AfpWriter   |
+-------+-------+                   +-------+-------+
        |                                   |
        +-----------------+-----------------+
                          |
                          v
                +-------------------+
                |  AFP Output File  |
                +-------------------+
```

### 2.1. The Document Object Model (DOM) API

For moderate-sized documents, a hierarchical DOM is intuitive. The DOM mirrors the structural
rules defined by the MODCA specification:

```java
package com.mgz.afp.dom;

import com.mgz.afp.base.StructuredField;
import com.mgz.afp.modca.*;
import java.util.List;

public interface AfpNode {
  AfpNode getParent();
  List<AfpNode> getChildren();
  void addChild(AfpNode child);
  void removeChild(AfpNode child);
}

public class AfpDom implements AfpNode {
  private BDT_BeginDocument beginDocument;
  private EDT_EndDocument endDocument;
  private List<AfpNode> children;

  // Navigation & Querying
  public List<AfpPageNode> findPages();
  public List<AfpPageGroupNode> findPageGroups();

  // Serialization
  public void writeTo(java.io.OutputStream os, AFPParserConfiguration config) throws IOException;
}
```

### 2.2. Fluent Builder & Factory Pattern

Creating structured fields manually is error-prone. We propose a fluent builder API to safely
instantiate fields and triplets with validated parameters:

```java
package com.mgz.afp.builder;

import com.mgz.afp.modca.TLE_TagLogicalElement;
import com.mgz.afp.triplets.Triplet;

public class TleBuilder {
  private String attributeName;
  private String attributeValue;
  private int attributeId;

  public TleBuilder withAttribute(String name, String value) {
    this.attributeName = name;
    this.attributeValue = value;
    return this;
  }

  public TleBuilder withId(int id) {
    this.attributeId = id;
    return this;
  }

  public TLE_TagLogicalElement build() {
    TLE_TagLogicalElement tle = new TLE_TagLogicalElement();
    // Configure default SFI introducer and add FQN triplets
    // ...
    return tle;
  }
}
```

### 2.3. The Streaming / Filter API (O(1) Memory Footprint)

Large transactional AFP streams can exceed gigabytes. Loading them entirely into a DOM would cause
`OutOfMemoryError`s. A streaming event-based API processes and writes back fields on the fly:

```java
package com.mgz.afp.streaming;

import com.mgz.afp.base.StructuredField;
import com.mgz.afp.parser.AFPParser;
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

  public void filter(AfpFilterCallback callback) throws Exception {
    StructuredField sf;
    while ((sf = parser.parseNextSF()) != null) {
      // Allow user code to inspect, modify, or drop the SF
      boolean keep = callback.process(sf);
      if (keep) {
        sf.writeAFP(outputStream, config);
      }
      sf.release(); // Return to pools
    }
  }
}

public interface AfpFilterCallback {
  /**
   * Called for each Structured Field parsed.
   * @return true to write the field, false to skip/delete it.
   */
  boolean process(StructuredField sf) throws Exception;
}
```

---

## 3. High-Fidelity Write-back Mechanisms

To ensure generated files pass AFP validation tools, Alpheus must manage low-level concerns during
write-back:

### 3.1. Automatic Length and SFI Recalculation
- **SFLength Updates**: The existing `writeFullStructuredField(OutputStream os, byte[] netPayload)`
  and `writeFullStructuredField(OutputStream os, ByteBuffer netPayload)` in `StructuredField.java`
  already compute final SF lengths dynamically. This must be leveraged and strictly enforced.
- **Triplet Lengths**: When a triplet is mutated or added, its length byte must be updated prior to
  writing the parent structured field.

### 3.2. Preservation of Padding and Layout
- If the original SF was padded, the user can choose to maintain padding (`SFFlag.isPadded`) or strip
  padding to compress the output.
- Custom vendor structured fields (often mapped to `Undefined`) must be written back byte-for-byte to
  guarantee no proprietary workflow data is lost.

---

## 4. Resource Pooling Lifecycle

Alpheus uses optimized thread-local or global object pools (`StructuredFieldPool`, `TripletPool`) to
minimize GC overhead. When introducing modifications:
1. **Detachment**: When a parsed node is extracted from a stream for editing and persistent storage, it
   must be **detached** from the pooling system (or marked as `isPooled = false`) to prevent it from
   being recycled prematurely.
2. **Re-pooling**: Once written back to the output stream, nodes that are no longer referenced should
   be explicitly released back to their pools.

---

## 5. Implementation Roadmap

### Phase 1: Fluent Builders & Base Support
- Implement builders for common structured fields (`TLE`, `BNG`, `ENG`, `NOP`).
- Extend existing `writeAFP` implementations to automatically refresh dependencies.

### Phase 2: Streaming Filter API
- Create the `AfpStreamFilter` pipeline enabling light-weight modifications (e.g., stripping certain
  triplets or editing TLE indexing values).
- Add integration tests verifying byte-level roundtrips of modified files.

### Phase 3: High-Level DOM API
- Develop parent-child tree mapping for structured fields.
- Support deep validation of MODCA structure validity before serialization.
