# Performance Slowdown Audit - Alpheus AFP Parser

This document identifies unnecessary code paths and architectural inefficiencies that negatively impact the performance of the Alpheus parser and its XML conversion pipeline.

## Summary of Findings

| ID | Issue | Impact | Category | Affected Component | Status |
| :--- | :--- | :---: | :--- | :--- | :--- |
| SLOW-01 | Regressed Record-Skipping in Scanner | 🚀🚀🚀🚀 | I/O & Scanning | `AFPScanner` | ✅ Resolved |
| SLOW-02 | Expensive Reflection in Hot Path | 🚀🚀🚀 | CPU (Hot Path) | `AfpJacksonXmlWriter` | ✅ Resolved |
| SLOW-03 | Redundant Sanitization of Indents | 🚀🚀 | CPU / Logic | `SanitizingXMLStreamWriter` | ✅ Resolved |
| SLOW-04 | Boxing in SF Identifier Resolution | 🚀🚀 | CPU / Memory | `SFTypeID` | ✅ Resolved |
| SLOW-05 | Redundant String Allocations in Heuristics | 🚀 | CPU (Hot Path) | `UtilCharacterEncoding` | ✅ Resolved |
| SLOW-06 | Triple Sanitization Overhead | 🚀🚀 | CPU (Hot Path) | `SanitizingXMLStreamWriter` | ✅ Resolved |

---

## [SLOW-01] Regressed Record-Skipping in Scanner
**Description:** The `AFPScanner` is designed to quickly find page boundaries (`BPG` fields) by jumping from one structured field to the next using the length byte in the SFI header. However, the current implementation increments `pos++` regardless of whether a record was found, effectively performing a byte-by-byte scan of the entire file.
**Impact:** 🚀🚀🚀🚀 (Critical). For large AFP files with many high-volume `IPD` (Image) or `GAD` (Graphics) fields, this turns an $O(N_{records})$ scan into an $O(N_{bytes})$ scan.
**Recommendation:** After parsing `sfLength`, the scanner should jump `pos += sfLength + 1`.
**Status: ✅ Resolved.** Fixed in `AFPScanner.java`. The scanner now correctly utilizes the `sfLength` to jump to the next potential structured field, significantly reducing I/O and CPU overhead during boundary discovery.

## [SLOW-02] Expensive Reflection in Hot Path
**Description:** `AfpJacksonXmlWriter` handles structured fields that do not have a manual fast-path by falling back to Jackson. In doing so, it calls `sf.getClass().getSimpleName()` and `MnemonicPerformanceMonitor.extractMnemonicFromString()`.
**Impact:** 🚀🚀🚀 (Major). These calls are performed even when performance instrumentation is disabled. `getSimpleName()` involves reflection, and mnemonic extraction involves string manipulation and cache lookups.
**Recommendation:** Guard these calls with `MnemonicPerformanceMonitor.isEnabled()`.
**Status: ✅ Resolved.** Fixed in `AfpJacksonXmlWriter.java`. All calls to `getSimpleName()` and mnemonic extraction in the hot path are now guarded by `MnemonicPerformanceMonitor.isEnabled()`. Additionally, manual fast-paths for GOCA and BCOCA have been optimized to further reduce reflection overhead.

## [SLOW-03] Redundant Sanitization of Indents
**Description:** All character data written to XML passes through `SanitizingXMLStreamWriter`. This includes indentation strings (e.g., `\n  `) which are pre-allocated in `XmlIndenter` and are guaranteed to be XML-safe.
**Impact:** 🚀🚀 (Moderate). While each check is fast, the sheer frequency of indentation writes in a pretty-printed document adds significant cumulative overhead.
**Recommendation:** Implement a mechanism to bypass sanitization for known-safe constant strings or add a `writeRawCharacters` method.
**Status: ✅ Resolved.** Fixed in `SanitizingXMLStreamWriter.java`. The `writeRaw` methods now delegate directly to the underlying `XMLStreamWriter2`, bypassing sanitization for known-safe content like indentation written via `XmlIndenter`.

## [SLOW-04] Boxing in SF Identifier Resolution
**Description:** `SFTypeID.parse()` uses a `HashMap<Integer, SFTypeID>`. Every lookup involves boxing the integer key, and every `VAL_MAP.get()` call performs an object comparison.
**Impact:** 🚀🚀 (Moderate). Since this happens for every structured field in the file, it increases GC pressure and CPU cycles in the primary parsing loop.
**Recommendation:** Use a primitive-optimized map or a sparse array (since SF Class is almost always `0xD3`).
**Status: ✅ Resolved.** Fixed in `SFTypeID.java`. Implemented a fast-path lookup using a sparse array (`D3_LOOKUP`) for the most common `0xD3` class, eliminating boxing and map overhead for the vast majority of structured fields.

## [SLOW-05] Redundant String Allocations in Heuristics
**Description:** `UtilCharacterEncoding.isHumanReadable` frequently converts byte arrays to temporary `String` objects just to perform a heuristic check for printable characters.
**Impact:** 🚀 (Minor). This increases allocation churn in text-heavy documents.
**Recommendation:** Perform the printable check directly on the `byte[]` or `ByteBuffer` using the existing optimized EBCDIC-to-UTF8 lookup tables without creating a intermediate `String`.
**Status: ✅ Resolved.** Fixed in `UtilCharacterEncoding.java`. Implemented `isHumanReadable` fast-paths for common EBCDIC charsets (CP500, CP273, CP1141) that perform the printable check directly on `ByteBuffer` or `byte[]` using static lookup tables.

## [SLOW-06] Triple Sanitization Overhead
**Description:** Strings written to XML were previously being sanitized for XML 1.0 compatibility multiple times: at the domain object getter level, via a custom Jackson serializer, and finally by the `SanitizingXMLStreamWriter` decorator.
**Impact:** 🚀🚀 (Moderate). Redundant scanning of large text payloads increases CPU usage in text-heavy AFP documents.
**Recommendation:** Consolidate sanitization into a single layer, preferably the StAX decorator (`SanitizingXMLStreamWriter`).
**Status: ✅ Resolved.** Sanitization has been consolidated into `SanitizingXMLStreamWriter`. Redundant sanitization calls were removed from domain-level getters and the custom Jackson serializer was decommissioned in favor of the streaming decorator.
