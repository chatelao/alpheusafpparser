# Alpheus AFP Parser Code Coverage Gap Analysis

## 1. Executive Summary
The target code coverage for the Alpheus AFP Parser is **85%**. The current verification shows that the project is significantly below this target.

| Metric | Current Coverage | Target | Gap |
| :--- | :--- | :--- | :--- |
| **Instruction** | 15.09% | 85% | 69.91% |
| **Branch** | 7.98% | 85% | 77.02% |
| **Line** | 9.79% | 85% | 75.21% |

While basic parsing and CLI functions are covered, the vast majority of the AFP specification-specific logic (Structured Fields, Triplets, and Control Sequences) remains untested in a way that exercises their internal decoding and validation logic.

## 2. Specification-Level Gaps

The following table summarizes coverage by AFP sub-specification, mapped from the Java package structure:

| Specification | Primary Package | Coverage | Status |
| :--- | :--- | :--- | :--- |
| **MO:DCA** | `com.mgz.afp.modca` | 1.13% | 🔴 Critical Gap |
| **GOCA** | `com.mgz.afp.goca` | 0.00% | 🔴 Critical Gap |
| **IOCA** | `com.mgz.afp.ioca` | 0.00% | 🔴 Critical Gap |
| **FOCA** | `com.mgz.afp.foca` | 0.00% | 🔴 Critical Gap |
| **Triplets** | `com.mgz.afp.triplets` | 4.17% | 🔴 Critical Gap |
| **PTOCA** | `com.mgz.afp.ptoca.*` | ~16% | 🔴 Critical Gap |
| **Line Data** | `com.mgz.afp.lineData` | 0.00% | 🔴 Critical Gap |
| **CMOCA** | `com.mgz.afp.cmoca` | 0.00% | 🔴 Critical Gap |
| **BCOCA** | `com.mgz.afp.bcoca` | 48.40% | 🟡 Moderate Gap |
| **Metadata** | `com.mgz.afp.moca` | 0.00% | 🔴 Critical Gap |

## 3. Major Package Analysis

### 3.1. High-Impact Untested Areas
- **`com.mgz.afp.modca` (7,376 instructions):** This package contains the core Mixed Object Document Content Architecture structured fields. Only 83 instructions are covered.
- **`com.mgz.afp.goca` (10,541 instructions):** Graphics Object Content Architecture logic is entirely untested.
- **`com.mgz.afp.triplets` (11,910 instructions):** Triplets are the building blocks of AFP, yet they have extremely low coverage.
- **`com.mgz.afp.foca` (4,743 instructions):** Font Object Content Architecture is entirely untested.

### 3.2. Top 20 Uncovered Classes (Sample)
Many of these classes are PTOCA Control Sequences or specialized MO:DCA fields:
1. `PTOCAControlSequence$SIA_SetIntercharacterAdjustment`
2. `PTOCAControlSequence$GAR_GlyphAdvanceRun`
3. `PTOCAControlSequence$UCT_UnicodeComplexText`
4. `PTOCAControlSequence$SKI_SetKeyInformation`
5. `PTOCAControlSequence$STO_SetTextOrientation`
6. `PTOCAControlSequence$RMB_RelativeMoveBaseline`
7. `PTOCAControlSequence$DBR_DrawBaxisRule`
8. `PTOCAControlSequence$SBI_SetBaselineIncrement`
9. `PTOCAControlSequence$STC_SetTextColor`
10. `PTOCAControlSequence$DIR_DrawIaxisRule`

## 4. Root Causes of Low Coverage
1. **Shallow Parsing in Tests:** Many tests verify that a file can be parsed into a `StructuredField` but do not call methods that trigger the internal decoding of complex payloads.
2. **Missing Round-Trip Tests:** While `RoundTripTestUtils` exists, it hasn't been applied to the hundreds of individual SF and Triplet classes.
3. **Synthetic Data Scarcity:** The `FilesSuite` relies on available `.afp` files which may not exercise every possible SF type or parameter combination.

## 5. Path to 85% Coverage
To bridge the 69.91% instruction coverage gap, the following actions are required:
- **Systematic Round-Trip Testing:** Generate unit tests for every class in `com.mgz.afp.modca`, `goca`, `ioca`, and `triplets` using `RoundTripTestUtils`.
- **Expanded Fuzzing:** Use `AFPFuzzTest` with Jazzer to discover paths in the parser that are not covered by existing files.
- **Parameter Variation:** Create tests that specifically exercise optional triplets and SFI extensions (padding, segmentation).
