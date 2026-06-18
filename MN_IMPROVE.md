# Mnemonic Improvement Plan: GAD, GBSEG, GCLINE

This document analyzes the performance bottlenecks associated with the `GAD`, `GBSEG`, and `GCLINE` mnemonics and proposes strategies for optimization.

## Current Performance Bottlenecks

| Mnemonic | Category | Root Cause | Status |
| :--- | :--- | :--- | :--- |
| **GAD** | Container Overhead | `GAD_GraphicsData` contains numerous small `GAD_DrawingOrder` instances. Each order triggers expensive `MnemonicPerformanceMonitor` instrumentation and recursive XML writing. | ✅ |
| **GBSEG** | Nesting & Recursion | `GBSEG_BeginSegment` often wraps large sequences of drawing orders. The nested nature leads to redundant stack operations and repeated performance monitoring calls in high-frequency paths. | 🚧 |
| **GCLINE** | XML Verbosity | `GCLINE_LineAtCurrentPosition` (and other point-based orders) serializes each coordinate pair as a full XML element (`GOCA_Point`) with individual attributes, leading to massive XML bloat and I/O pressure. | ✅ |
| **IPD** | IOCA Coverage | Missing manual fast-path coverage for complex IOCA IPD segments like `ExternalAlgorithmSpecification`. | ⏳ |
| **GOCA** | Allocation Churn | `GAD_DrawingOrder.DrawingOrder_HasPoints.getPoints()` instantiates new `ArrayList` and `GOCA_Point` objects during serialization. | ⏳ |

### Detailed Analysis

1.  **Granular Instrumentation (GAD/GBSEG):**
    The `AfpJacksonXmlWriter` used to call `MnemonicPerformanceMonitor.startWriteWithMnemonic` for every single drawing order. While these calls were guarded by `isEnabled()`, the overhead of the method call and the lookup within the hot loop of a large `GAD` field was significant.

2.  **Point Cloud Verbosity (GCLINE):**
    A single `GCLINE` can contain thousands of points. The previous XML output looked like this:
    ```xml
    <points>
      <GOCA_Point xCoordinate="100" yCoordinate="200"/>
      <GOCA_Point xCoordinate="110" yCoordinate="210"/>
      ...
    </points>
    ```
    This tripled the size of the output and significantly increased the number of `writeStartElement` and `writeAttribute` calls.

3.  **Decorator Stack Pressure:**
    The `xsw` writer is often wrapped in multiple layers (`MnemonicXMLStreamWriter` -> `SanitizingXMLStreamWriter` -> `AfpXmlStreamWriter`). Each write operation must traverse this decorator chain, which adds up when processing millions of GOCA components.

## Proposed Improvements

### 1. Instrumentation Pruning ✅
*   **Strategy:** Disable granular monitoring for nested GOCA drawing orders.
*   **Action:** Modified `writeGadDirectly` and `writeDrawingOrderDirectly` to only record performance at the `GAD` level. Bypassing `MnemonicPerformanceMonitor` for internal orders reduced CPU cycles in the hottest loops.

### 2. Compact Point Representation ✅
*   **Strategy:** Use a more efficient XML format for coordinate lists.
*   **Action:** Replaced the individual `GOCA_Point` elements with a space-separated coordinate string:
    ```xml
    <points>100 200 110 210 120 220</points>
    ```
    This reduces the XML node count and leverages fast `writeCharacters` operations.

### 3. Direct Stream Access 🚧
*   **Strategy:** Bypass the decorator chain for known-safe numeric data.
*   **Action:** Use `baseXsw` (or even the underlying `woodstoxOs`) directly when writing numeric attributes and coordinate points. Since these values are guaranteed to be XML-safe, sanitization is redundant.

### 4. Segment Flattening 🚧
*   **Strategy:** Optimize `GBSEG` recursion.
*   **Action:** Implement a non-recursive path for writing segments or use a specialized fast-path that avoids the general `Jackson` serialization fallback for complex segment structures.

### 5. Zero-Allocation Point Access ⏳
*   **Strategy:** Avoid object allocation in hot loops.
*   **Action:** Modify `writeDrawingOrderWithPoints` to iterate over the raw `short[] pointsArray` directly instead of calling `getPoints()`, which allocates an `ArrayList` and multiple `GOCA_Point` objects.

### 6. IOCA Fast-Path Completion ⏳
*   **Strategy:** Eliminate Jackson fallback for IOCA.
*   **Action:** Implement manual StAX fast-paths for remaining IOCA segments, particularly `ExternalAlgorithmSpecification`, to ensure zero usage of reflection-based serialization in IPD fields.

---
*Documented on: September 2026*
*Updated on: October 2026*
