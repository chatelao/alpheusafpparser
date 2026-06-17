# Retirement of the "Punchard" Concept (Strategy 6)

This document describes the decision to retire the "Punchard" (Hole-Punching) template-based serialization strategy in the Alpheus AFP Parser.

## Overview

Strategy 6, as described in `10x_FASTER.md` and detailed in `TEMPLATE_BASED_ROADMAP.md`, proposed using pre-computed XML byte templates with "holes" for variable data to achieve ultra-high-speed serialization. This concept, internally referred to as "Punchard", is being officially retired in favor of **Strategy 1: 100% Manual StAX Fast-Paths**.

## Reasons for Retirement

1.  **Complexity vs. Gain**: While the "Punchard" strategy offered high performance, the complexity of managing byte-level templates and the `ThreadLocal` assembly buffers outweighed the marginal performance gains over highly optimized StAX2-based manual fast-paths.
2.  **Maintainability**: Manual StAX2 code is significantly easier to read, maintain, and debug compared to fragmented byte arrays in `XmlTemplateRegistry`.
3.  **StAX2 Capabilities**: The Woodstox StAX2 implementation provides `writeRaw` and typed writing methods (`writeInt`, `writeLong`) that, when used correctly, achieve near-native performance without the need for custom hole-punching logic.
4.  **Consistency**: Strategy 1 has already achieved extensive coverage across Structured Fields, PTOCA control sequences, and Triplets. Consolidating on a single high-performance strategy reduces architectural fragmentation.

## Impacted Components

The following components are marked for removal or refactoring:

*   `com.mgz.xml.XmlTemplate`: The core engine for hole-punching serialization.
*   `com.mgz.xml.XmlTemplateRegistry`: The registry containing byte fragments for various AFP structures.
*   `com.mgz.xml.AfpJacksonXmlWriter`: Usages of `XmlTemplateRegistry.getTemplate(...).write(...)` will be replaced with equivalent direct StAX2 fast-path method calls.

## Future Direction

All future performance optimizations will focus on Strategy 1 (Manual StAX Fast-Paths) and Strategy 3 (Zero-Allocation Encoding). The lessons learned from the Punchard experiment regarding fast integer-to-byte conversion (`FastIntConverter`) have already been integrated into the primary fast-path implementations.

## References

*   `10x_FASTER.md`: Strategy 6 marked as **RETIRED**.
*   `TEMPLATE_BASED_ROADMAP.md`: Phases 2-4 marked as **DISCONTINUED**.
