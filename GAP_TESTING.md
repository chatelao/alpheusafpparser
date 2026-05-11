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
| **Encoding Coverage** | Poor | Hardcoded IBM-500 defaults; lack of LID-to-Charset dynamic resolution. |
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

## 3. Encoding and Character Set Coverage Gaps

A critical testing gap exists in the verification of text extraction when using non-standard or state-switched character sets. Currently, many human-readable text sources in the codebase hardcode EBCDIC International (IBM-500) or rely on a global configuration that does not respect the stateful LID-to-Charset mapping defined by the AFP architecture.

### 3.1. Hardcoded IBM-500 Defaults
The following classes hardcode `Constants.cpIBM500` in their `getText()` methods, making them likely to fail when processing data in other encodings (e.g., German IBM-273):
*   **GOCA**: `GCOMT_Comment`, `GCCHST_CharacterStringAtCurrentPosition`, `GCHST_CharacterStringAtGivenPosition`.
*   **BCOCA**: `BDA_BarCodeData` (human-readable portion).
*   **IOCA**: `UnknownSegmentLong`, `UnknownSegmentExtended`.
*   **FOCA**: `CPD_CodePageDescriptor`, `FND_FontDescriptor` (descriptors/descriptions).
*   **PTOCA**: `Undefined` control sequence, `NOP_NoOperation`.

### 3.2. Global vs. Stateful Charset Gaps
Many classes use `config.getAfpCharSet()`, which defaults to IBM-500. While this is configurable, the parser lacks the **"Blind Execution" logic** required to dynamically switch the active Charset based on encountering dynamic font activation commands.
*   **PTOCA Stateful Gaps**: `TRN_TransparentData` and `GraphicCharacters` currently use the global config. They must be tested with dynamic switching via `SCFL` (Set Coded Font Local) commands that point to `MCF` (Map Coded Font) declarations.
*   **MO:DCA Triplets**: `FullyQualifiedName`, `AttributeValue`, `Comment`, and `ObjectClassification` all rely on the global configuration.
*   **Line Data**: `FDX_FixedDataText` and `CCP_RepeatingGroup` (comparison strings) rely on the global configuration.
*   **Base Classes**: `StructuredFieldBaseName` (for all Named SFs like BPG, BNG, etc.) uses the global configuration.

### 3.3. Multi-Byte and UTF-16BE Verification
While `UCT_UnicodeComplexText` and `CMR_ColorManagementResource` use `UTF-16BE`, coverage for these is minimal.
*   **Gap**: No tests verify that mixed-encoding streams (e.g., EBCDIC PTX followed by UTF-16BE UCT) are extracted correctly into XML.

### 3.4. Recommendations for Encoding Testing
1.  **Cross-Encoding Test Suite**: Create a test suite that processes the same logical text (e.g., "München") encoded in multiple Code Pages (IBM-500, IBM-273, IBM-1141) and verifies the XML `<text>` output is identical and correctly decoded.
2.  **LID-to-Charset Round-Trip**: Implement a test that uses `MCF` to map LID 1 to IBM-273 and LID 2 to IBM-500, then verifies that `SCFL` switches in `PTX` result in correct character resolution (e.g., byte `X'C0'` resolving to `ä` or `{` respectively).
3.  **Heuristic Validation**: Test `UtilCharacterEncoding.isHumanReadable` against various non-English EBCDIC strings to ensure it doesn't incorrectly flag them as binary.

---

## 4. Implementation Depth & "Shallow" Fields

The parser uses a mix of "Deep" and "Shallow" support:
*   **Deep:** 95 classes override `decodeAFP` to handle specific binary structures.
*   **Shallow:** Many classes inherit from `StructuredFieldBaseTriplets` or `StructuredFieldBaseName`.

**Testing Gap:** Shallow fields are often tested only as part of general Triplet/Name tests. There is a lack of "container verification" tests that ensure these SFs correctly handle their specific architectural constraints (e.g., requiring specific triplets).

---

## 5. Stateful and Semantic Testing

### 5.1. Text Extraction Heuristics
The library recently added `MDRPTXXMLTest.java` to verify that `MDR` structured fields correctly influence the Charset used for subsequent `PTX` text extraction.
*   **Gap:** This stateful logic needs to be extended to support `SCFL` (Set Coded Font Local) commands within the `PTX` stream itself, which dynamically switches fonts/encodings.

### 5.2. Field Validation
The `@AFPField` annotation system provides metadata for encoding/decoding but lacks comprehensive tests for:
*   **Boundary Conditions:** Max/Min values for numeric fields.
*   **String Truncation:** Handling of strings longer than the `maxSize` attribute.
*   **Optional Fields:** Verifying that optional fields are correctly omitted when null.

---

## 6. Infrastructure and Process

### 6.1. Legacy JUnit 4
The entire test suite is written in JUnit 4 (`org.junit.Test`).
*   **Gap:** Missing modern features of JUnit 5 (Jupiter), such as `@ParameterizedTest`, `@Nested` tests, and improved assertion libraries.
*   **Recommendation:** Migrate the test suite to JUnit 5 as outlined in Phase 6a of the Roadmap.

### 6.2. Spec-to-Test Mapping
The project has successfully converted most specifications to Markdown in `specifications/markdown/`.
*   **Gap:** There is no 1:1 mapping between specification chapters and test files.
*   **Recommendation:** Create a "Spec Coverage Matrix" or use specialized test suites named after spec chapters to ensure every architectural feature is verified.

### 6.3. Error Handling and Fuzzing
*   **Gap:** Current tests focus on "Happy Path" round-trips. There are virtually no tests for:
    *   Malformed Structured Field Introducers (SFI).
    *   Inconsistent lengths (SF length vs. actual data).
    *   Invalid Triplet sequences or truncated Triplets.

## 7. Summary of Action Items

1.  **Granular Unit Tests:** Expand GOCA and IOCA round-trip tests to 100% drawing order/segment coverage.
2.  **JUnit 5 Migration:** Update `build.gradle` and refactor existing tests to Jupiter.
3.  **Error Injection:** Develop a suite of tests that use malformed AFP data to verify parser resilience and exception handling.
4.  **Stateful PTX Testing:** Implement tests for dynamic font/encoding switching via `SCFL`.
5.  **Encoding Coverage:** Implement the Cross-Encoding Test Suite and LID-to-Charset resolution tests.
