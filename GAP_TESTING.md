# Alpheus AFP Parser Testing Gap Analysis

This report evaluates the current testing state of the Alpheus AFP Parser, identifying gaps in architectural coverage, implementation depth, and infrastructure.

## 1. Executive Summary

The Alpheus AFP Parser has achieved a high level of testing maturity. The infrastructure has been modernized to JUnit 5, and architectural coverage is ensured via a 1:1 mapping between specification chapters and minimal test files. Round-trip unit testing covers almost all Structured Fields, Triplets, and Control Sequences.

| Category | Status | Key Gaps |
| :--- | :--- | :--- |
| **Structural Round-Trip** | ✅ Excellent | Comprehensive coverage of MO:DCA SFs, Triplets, and PTOCA Control Sequences. |
| **GOCA/IOCA Coverage** | ✅ Excellent | 100% architectural coverage via 1:1 spec-to-test mapping; expanded round-trip tests. |
| **Infrastructure** | ✅ Excellent | Fully migrated to JUnit 5 (Jupiter); 1:1 spec-to-test mapping implemented. |
| **Stateful Logic** | ✅ Good | `StatefulEncodingTest` and `CrossEncodingTest` verify dynamic font/encoding switching. |
| **Encoding Coverage** | ✅ Good | Verified via `CrossEncodingTest` and `TRNEncodingTest`. |
| **Implementation Depth** | ✅ Excellent | 0 shallow fields identified; all SFs provide full payload parsing. |
| **Error Handling** | 🚧 Poor | Minimal testing of malformed or malicious AFP data. |

---

## 2. Granular Coverage Gaps

### 2.1. GOCA (Graphics Object Content Architecture)
*   **Status:** ✅ Complete.
*   **Progress:** `GOCARoundTripTest.java` has been expanded to cover a wide range of drawing orders. Architectural coverage is guaranteed by 14 minimal `.afp` files in `src/test/resources/afp/afp-goca-reference-03/` mapping to each chapter and appendix.

### 2.2. IOCA (Image Object Content Architecture)
*   **Status:** ✅ Complete.
*   **Progress:** `IOCARoundTripTest.java` covers critical IDD and IPD segments. 1:1 architectural coverage is achieved through 17 minimal `.afp` files in `src/test/resources/afp/ioca-reference-09/`.

### 2.3. FOCA & BCOCA
*   **Status:** ✅ Complete.
*   **Progress:** Round-trip tests now cover a broad range of SFs for both architectures. Architectural coverage is verified via dedicated test files for every spec chapter.

---

## 3. Implementation Depth & "Shallow" Fields

*   **Status:** ✅ Complete.
*   **Progress:** According to `SHALLOW_FIELDS_REPORT.md`, there are **0 shallow fields**. Every Structured Field class in the codebase now implements `decodeAFP` to provide deep payload parsing, moving beyond simple triplet/name inheritance.

---

## 4. Stateful and Semantic Testing

*   **Status:** ✅ Good.
*   **Progress:**
    *   **Stateful PTX:** `StatefulEncodingTest.java` verifies that `SCFL` correctly switches charsets within a PTX stream.
    *   **Cross-Encoding:** `CrossEncodingTest.java` ensures correct handling of text in different code pages (e.g., IBM273, IBM500, IBM1141) within the same document.

---

## 5. Infrastructure and Process

### 5.1. JUnit 5 Migration
*   **Status:** ✅ Complete.
*   **Progress:** The entire test suite has been migrated to JUnit 5 (Jupiter). Legacy JUnit 4 dependencies and imports have been removed.

### 5.2. Spec-to-Test Mapping
*   **Status:** ✅ Complete.
*   **Progress:** The project has established a 1:1 mapping between specification chapters and test files. Over 122 minimal `.afp` files (BDT/EDT pairs) have been generated in `src/test/resources/afp/` to ensure every chapter of every specification is exercised.

### 5.3. Error Handling and Fuzzing
*   **Gap:** Current tests focus on "Happy Path" round-trips. There remains a lack of tests for:
    *   Malformed Structured Field Introducers (SFI).
    *   Inconsistent lengths (SF length vs. actual data).
    *   Invalid Triplet sequences or truncated Triplets.
*   **Recommendation:** Develop a dedicated suite for error injection and parser resilience.

## 6. Summary of Action Items

1.  **Error Injection:** (Ongoing) Develop a suite of tests that use malformed AFP data to verify parser resilience and exception handling.
2.  **Semantic Validation:** Expand tests for architectural constraints beyond simple binary round-trips (e.g., mandatory triplets).
