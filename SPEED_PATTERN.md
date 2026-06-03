# Speed Patterns - Alpheus AFP Parser

This document identifies and documents high-performance patterns reactivated in August 2026 to resolve performance regressions observed between versions.

## Summary of Reactivated Patterns

| Pattern | Component | Impact | Description |
| :--- | :--- | :---: | :--- |
| **Disabled Namespace Repairing** | `JacksonXmlMapperProvider` | 🚀🚀 | Disables costly StAX namespace checking when XML structure is known and safe. |
| **Raw Write Bypass** | `SanitizingXMLStreamWriter` | 🚀🚀🚀 | Allows pre-validated strings (like indents) to bypass XML 1.0 sanitization scans. |
| **Optimized Indentation** | `XmlIndenter` | 🚀🚀 | Uses `writeRaw` to leverage the sanitization bypass for high-frequency whitespace. |
| **Primitive-Optimized Lookup** | `SFTypeID` | 🚀🚀🚀 | Eliminates `Integer` boxing and `HashMap` overhead in the primary parsing loop. |
| **High-Volume Fast-Paths** | `AfpJacksonXmlWriter` | 🚀🚀🚀🚀 | Manual StAX writing for `OCD` and `MOCA` fields to bypass Jackson reflection. |
| **Record-Skipping Navigation** | `AFPScanner` | 🚀🚀🚀🚀 | Record-based jumping to avoid byte-by-byte scanning during page discovery. |

---

## 1. Disabled Namespace Repairing
**Reactivated:** August 2026
**Implementation:** `IS_REPAIRING_NAMESPACES` set to `false` in `JacksonXmlMapperProvider`.
**Benefit:** StAX2 attempts to automatically fix missing or broken XML namespaces by default. This requires maintaining internal state and checking prefixes for every element. Since Alpheus generates a well-defined XML structure, disabling this saves significant CPU cycles in the hot path.

## 2. Raw Write Bypass for Sanitization
**Reactivated:** August 2026
**Implementation:** `writeRaw` overrides in `SanitizingXMLStreamWriter`.
**Benefit:** Previously, every character written to the XML output was scanned for XML 1.0 invalid characters. By introducing a "raw" write path, we allow components that generate known-safe strings (like the indenter) to bypass this scan, reducing CPU usage in text-heavy documents.

## 3. Optimized Indentation
**Reactivated:** August 2026
**Implementation:** `XmlIndenter` uses `xsw.writeRaw`.
**Benefit:** Pretty-printing adds a large number of newline and space characters. Even though these characters are safe, scanning them millions of times adds up. Using the raw bypass eliminates this overhead entirely for indentation.

## 4. Primitive-Optimized SF Identifier Resolution
**Reactivated:** August 2026
**Implementation:** `SFTypeID.parse(InputStream)` uses `valueOf(int, int, int)`.
**Benefit:** Every structured field in an AFP file starts with a 3-byte identifier. Using a `HashMap<Integer, SFTypeID>` caused an `Integer` boxing operation for every lookup. The optimized path uses a pre-computed array lookup for the common `0xD3` class, performing resolution in $O(1)$ without allocations.

## 5. Manual StAX Fast-Paths for OCD and MOCA
**Reactivated:** August 2026
**Implementation:** `writeOcdDirectly` and `writeMetadataObjectDirectly` in `AfpJacksonXmlWriter`.
**Benefit:** Metadata Object (MOCA) data carried in Object Container Data (OCD) fields can be extremely verbose and frequent in modern AFP files. Moving these to manual StAX fast-paths with typed attribute writing eliminates Jackson's reflective overhead and intermediate string allocations for these high-volume components.

## 6. Record-Skipping Navigation
**Reactivated:** August 2026
**Implementation:** `pos += sfLength + 1` jump logic in `AFPScanner`.
**Benefit:** For large AFP files, this allows the scanner to jump over data payloads (like Image or Graphics data) once a record is synced. This transforms a byte-by-byte $O(N_{bytes})$ scan into a record-based $O(N_{records})$ scan, providing a critical performance boost during page boundary discovery.
