# Alpheus AFP Parser Testing Gap Analysis

This report evaluates the current testing state of the Alpheus AFP Parser, identifying gaps in architectural coverage, implementation depth, and infrastructure.

## 1. Executive Summary

The Alpheus AFP Parser features a robust foundation for round-trip testing and integration testing. However, significant gaps exist in granular unit testing for specific OCA drawing orders and segments, as well as in the modernization of the test infrastructure.

| Category | Status | Key Gaps |
| :--- | :--- | :--- |
| **Structural Round-Trip** | Good | Coverage of many MO:DCA SFs and Triplets. |
| **GOCA/IOCA Coverage** | Fair | Most drawing orders and segments lack individual round-trip tests. |
| **Infrastructure** | Poor | Legacy JUnit 4; no JUnit 5 migration; lack of 1:1 spec-to-test mapping. |
| **Stateful Logic** | Fair | Initial tests for Charset switching exist, but deep PTX state is untested. |
| **Error Handling** | Poor | Minimal testing of malformed or malicious AFP data. |

---

## 2. Granular Coverage Gaps

### 2.1. GOCA (Graphics Object Content Architecture)
While `GAD_DrawingOrder.java` implements dozens of drawing orders (e.g., `GSPS`, `GSCOL`, `GSMX`, `GSLT`, `GSCP`, `GSAP`, `GSLJ`), the current `GOCARoundTripTest.java` only explicitly exercises a small subset.
*   **Gap:** Missing round-trip verification for complex orders like Arcs (`GSAP`, `GFARC`, `GPARC`), Box/Fillet attributes, and Process Color (`GSPCOL`).
*   **Recommendation:** Expand `GOCARoundTripTest.java` to include a test case for every drawing order defined in `GAD_DrawingOrder.java`.

### 2.2. IOCA (Image Object Content Architecture)
Similarly, `IPD_Segment.java` contains implementations for many IOCA segments (e.g., `ImageSize`, `ImageEncoding`, `IDESize`, `TileSize`, `BandImage`), but `IOCARoundTripTest.java` only covers basic `IDD` and a few `IPD` segments.
*   **Gap:** Missing tests for Tiling (`BeginTile`, `EndTile`, `TilePosition`), Transparency Masks, and Banded Image Data.
*   **Recommendation:** Implement comprehensive round-trip tests for all segments defined in `IPD_Segment.java`.

### 2.3. FOCA & BCOCA
*   **Gap:** FOCA and BCOCA round-trip tests cover basic SFs (BBC, BDD, BDA for BCOCA; BCF, ECF, CPD for FOCA) but lack coverage for more specialized fields like Font Orientation (`FNO`) or Font Position (`FNP`).

---

## 3. Implementation Depth & "Shallow" Fields

The parser uses a mix of "Deep" and "Shallow" support:
*   **Deep:** 95 classes override `decodeAFP` to handle specific binary structures.
*   **Shallow:** Many classes inherit from `StructuredFieldBaseTriplets` or `StructuredFieldBaseName`.

**Testing Gap:** Shallow fields are often tested only as part of general Triplet/Name tests. There is a lack of "container verification" tests that ensure these SFs correctly handle their specific architectural constraints (e.g., requiring specific triplets).

---

## 4. Stateful and Semantic Testing

### 4.1. Text Extraction Heuristics
The library recently added `MDRPTXXMLTest.java` to verify that `MDR` structured fields correctly influence the Charset used for subsequent `PTX` text extraction.
*   **Gap:** This stateful logic needs to be extended to support `SCFL` (Set Coded Font Local) commands within the `PTX` stream itself, which dynamically switches fonts/encodings.

### 4.2. Field Validation
The `@AFPField` annotation system provides metadata for encoding/decoding but lacks comprehensive tests for:
*   **Boundary Conditions:** Max/Min values for numeric fields.
*   **String Truncation:** Handling of strings longer than the `maxSize` attribute.
*   **Optional Fields:** Verifying that optional fields are correctly omitted when null.

---

## 5. Infrastructure and Process

### 5.1. Legacy JUnit 4
The entire test suite is written in JUnit 4 (`org.junit.Test`).
*   **Gap:** Missing modern features of JUnit 5 (Jupiter), such as `@ParameterizedTest`, `@Nested` tests, and improved assertion libraries.
*   **Recommendation:** Migrate the test suite to JUnit 5 as outlined in Phase 6a of the Roadmap.

### 5.2. Spec-to-Test Mapping
The project has successfully converted most specifications to Markdown in `specifications/markdown/`.
*   **Gap:** There is no 1:1 mapping between specification chapters and test files.
*   **Recommendation:** Create a "Spec Coverage Matrix" or use specialized test suites named after spec chapters to ensure every architectural feature is verified.

### 5.3. Error Handling and Fuzzing
*   **Gap:** Current tests focus on "Happy Path" round-trips. There are virtually no tests for:
    *   Malformed Structured Field Introducers (SFI).
    *   Inconsistent lengths (SF length vs. actual data).
    *   Invalid Triplet sequences or truncated Triplets.

## 6. Summary of Action Items

1.  **Granular Unit Tests:** Expand GOCA and IOCA round-trip tests to 100% drawing order/segment coverage.
2.  **JUnit 5 Migration:** Update `build.gradle` and refactor existing tests to Jupiter.
3.  **Error Injection:** Develop a suite of tests that use malformed AFP data to verify parser resilience and exception handling.
4.  **Stateful PTX Testing:** Implement tests for dynamic font/encoding switching via `SCFL`.
