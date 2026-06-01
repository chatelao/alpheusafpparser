# Performance Slowdown Audit - Alpheus AFP Parser

This document identifies unnecessary code paths and architectural inefficiencies that negatively impact the performance of the Alpheus parser and its XML conversion pipeline.

## Summary of Findings

| ID | Issue | Impact | Category | Affected Component |
| :--- | :--- | :---: | :--- | :--- |
| SLOW-01 | Regressed Record-Skipping in Scanner | 🚀🚀🚀🚀 | I/O & Scanning | `AFPScanner` |
| SLOW-02 | Expensive Reflection in Hot Path | 🚀🚀🚀 | CPU (Hot Path) | `AfpJacksonXmlWriter` |
| SLOW-03 | Redundant Sanitization of Indents | 🚀🚀 | CPU / Logic | `SanitizingXMLStreamWriter` |
| SLOW-04 | Boxing in SF Identifier Resolution | 🚀🚀 | CPU / Memory | `SFTypeID` |
| SLOW-05 | Redundant String Allocations in Heuristics | 🚀 | CPU (Hot Path) | `UtilCharacterEncoding` |

---

## [SLOW-01] Regressed Record-Skipping in Scanner
**Description:** The `AFPScanner` is designed to quickly find page boundaries (`BPG` fields) by jumping from one structured field to the next using the length byte in the SFI header. However, the current implementation increments `pos++` regardless of whether a record was found, effectively performing a byte-by-byte scan of the entire file.
**Impact:** 🚀🚀🚀🚀 (Critical). For large AFP files with many high-volume `IPD` (Image) or `GAD` (Graphics) fields, this turns an $O(N_{records})$ scan into an $O(N_{bytes})$ scan.
**Recommendation:** After parsing `sfLength`, the scanner should jump `pos += sfLength + 1`.

## [SLOW-02] Expensive Reflection in Hot Path
**Description:** `AfpJacksonXmlWriter` handles structured fields that do not have a manual fast-path by falling back to Jackson. In doing so, it calls `sf.getClass().getSimpleName()` and `MnemonicPerformanceMonitor.extractMnemonicFromString()`.
**Impact:** 🚀🚀🚀 (Major). These calls are performed even when performance instrumentation is disabled. `getSimpleName()` involves reflection, and mnemonic extraction involves string manipulation and cache lookups.
**Recommendation:** Guard these calls with `MnemonicPerformanceMonitor.isEnabled()`.

## [SLOW-03] Redundant Sanitization of Indents
**Description:** All character data written to XML passes through `SanitizingXMLStreamWriter`. This includes indentation strings (e.g., `\n  `) which are pre-allocated in `XmlIndenter` and are guaranteed to be XML-safe.
**Impact:** 🚀🚀 (Moderate). While each check is fast, the sheer frequency of indentation writes in a pretty-printed document adds significant cumulative overhead.
**Recommendation:** Implement a mechanism to bypass sanitization for known-safe constant strings or add a `writeRawCharacters` method.

## [SLOW-04] Boxing in SF Identifier Resolution
**Description:** `SFTypeID.parse()` uses a `HashMap<Integer, SFTypeID>`. Every lookup involves boxing the integer key, and every `VAL_MAP.get()` call performs an object comparison.
**Impact:** 🚀🚀 (Moderate). Since this happens for every structured field in the file, it increases GC pressure and CPU cycles in the primary parsing loop.
**Recommendation:** Use a primitive-optimized map or a sparse array (since SF Class is almost always `0xD3`).

## [SLOW-05] Redundant String Allocations in Heuristics
**Description:** `UtilCharacterEncoding.isHumanReadable` frequently converts byte arrays to temporary `String` objects just to perform a heuristic check for printable characters.
**Impact:** 🚀 (Minor). This increases allocation churn in text-heavy documents.
**Recommendation:** Perform the printable check directly on the `byte[]` or `ByteBuffer` using the existing optimized EBCDIC-to-UTF8 lookup tables without creating a intermediate `String`.
