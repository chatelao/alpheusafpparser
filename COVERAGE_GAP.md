# Alpheus AFP Parser Code Coverage Gap Analysis

## 1. Executive Summary
The target code coverage for the Alpheus AFP Parser is **85%**. The current verification shows that the project is significantly below this target.

| Metric | Current Coverage | Target | Gap |
| :--- | :--- | :--- | :--- |
| **Instruction** | 15.32% | 85% | 69.68% |
| **Branch** | 8.09% | 85% | 76.91% |
| **Line** | 10.09% | 85% | 74.91% |

While basic parsing and CLI functions are covered, the vast majority of the AFP specification-specific logic (Structured Fields, Triplets, and Control Sequences) remains untested in a way that exercises their internal decoding and validation logic.

## 2. Specification-Level Gaps

The following table summarizes coverage by AFP sub-specification, mapped from the Java package structure:

| Specification | Primary Package | Coverage | Status |
| :--- | :--- | :--- | :--- |
| **MO:DCA** | `com.mgz.afp.modca` | 1.12% | 🔴 Critical Gap |
| **GOCA** | `com.mgz.afp.goca` | 0.00% | 🔴 Critical Gap |
| **IOCA** | `com.mgz.afp.ioca` | 0.00% | 🔴 Critical Gap |
| **FOCA** | `com.mgz.afp.foca` | 0.00% | 🔴 Critical Gap |
| **Triplets** | `com.mgz.afp.triplets` | 4.16% | 🔴 Critical Gap |
| **PTOCA** | `com.mgz.afp.ptoca.*` | 17.58% | 🔴 Critical Gap |
| **Line Data** | `com.mgz.afp.lineData` | 0.00% | 🔴 Critical Gap |
| **CMOCA** | `com.mgz.afp.cmoca` | 0.00% | 🔴 Critical Gap |
| **BCOCA** | `com.mgz.afp.bcoca` | 48.40% | 🟡 Moderate Gap |
| **Metadata** | `com.mgz.afp.moca` | 0.00% | 🔴 Critical Gap |

## 3. Major Package Analysis

### 3.1. High-Impact Untested Areas
- **`com.mgz.afp.modca` (7,414 instructions):** This package contains the core Mixed Object Document Content Architecture structured fields. Only 83 instructions are covered.
- **`com.mgz.afp.goca` (10,586 instructions):** Graphics Object Content Architecture logic is entirely untested.
- **`com.mgz.afp.triplets` (11,960 instructions):** Triplets are the building blocks of AFP, yet they have extremely low coverage.
- **`com.mgz.afp.foca` (4,743 instructions):** Font Object Content Architecture is entirely untested.

### 3.2. Top 20 Uncovered Classes (Sample)
Many of these classes are PTOCA Control Sequences, complex SFs, or GOCA orders:
1. `com.mgz.xml.AfpJacksonXmlWriter` (946 instructions)
2. `com.mgz.afp.cmoca.CMR_ColorManagementResource` (676 instructions)
3. `com.mgz.afp.parser.AFPScanner` (658 instructions)
4. `com.mgz.afp.foca.FNC_FontControl` (638 instructions)
5. `com.mgz.afp.goca.GAD_GraphicsData` (630 instructions)
6. `com.mgz.afp.lineData.XMD_XMLDescriptor$XMD_Flag` (591 instructions)
7. `com.mgz.afp.lineData.RCD_RecordDescriptor` (587 instructions)
8. `com.mgz.afp.lineData.XMD_XMLDescriptor` (585 instructions)
9. `com.mgz.afp.lineData.RCD_RecordDescriptor$RCD_Flag` (521 instructions)
10. `com.mgz.afp.lineData.LND_LineDescriptor$LND_Flag` (489 instructions)
11. `com.mgz.afp.lineData.LND_LineDescriptor` (445 instructions)
12. `com.mgz.afp.foca.FND_FontDescriptor` (408 instructions)
13. `com.mgz.afp.goca.GAD_DrawingOrder$GRGD_RadialGradient` (397 instructions)
14. `com.mgz.afp.foca.FNO_FontOrientation` (395 instructions)
15. `com.mgz.afp.goca.GDD_Parameter$SetCurrentDefaultInstruction$CharacterAttributes` (359 instructions)
16. `com.mgz.afp.ioca.IPD_ImagePictureData` (353 instructions)
17. `com.mgz.afp.modca.MCF_MapCodedFont_Format1$MCF_RepeatingGroup` (344 instructions)
18. `com.mgz.afp.goca.GAD_DrawingOrder$GLGD_LinearGradient` (337 instructions)
19. `com.mgz.afp.goca.GAD_DrawingOrder$GBSEG_BeginSegment` (332 instructions)
20. `com.mgz.afp.triplets.Triplet$EncodingSchemeID$EncodingScheme` (323 instructions)

## 4. Root Causes of Low Coverage
1. **Shallow Parsing in Tests:** Many tests verify that a file can be parsed into a `StructuredField` but do not call methods that trigger the internal decoding of complex payloads.
2. **Missing Round-Trip Tests:** While `RoundTripTestUtils` exists, it hasn't been applied to the hundreds of individual SF and Triplet classes.
3. **Synthetic Data Scarcity:** The `FilesSuite` relies on available `.afp` files which may not exercise every possible SF type or parameter combination.

## 5. Path to 85% Coverage
To bridge the 69.68% instruction coverage gap, the following actions are required:
- **Systematic Round-Trip Testing:** Generate unit tests for every class in `com.mgz.afp.modca`, `goca`, `ioca`, and `triplets` using `RoundTripTestUtils`.
- **Expanded Fuzzing:** Use `AFPFuzzTest` with Jazzer to discover paths in the parser that are not covered by existing files.
- **Parameter Variation:** Create tests that specifically exercise optional triplets and SFI extensions (padding, segmentation).
