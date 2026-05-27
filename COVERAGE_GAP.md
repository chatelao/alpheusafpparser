# Alpheus AFP Parser Code Coverage Gap Analysis

## 1. Executive Summary
The target code coverage for the Alpheus AFP Parser is **85%**. The current verification shows significant progress, moving from ~15% to approximately **65%**, but remains below the target.

| Metric | Current Coverage | Target | Gap |
| :--- | :--- | :--- | :--- |
| **Instruction** | 64.87% | 85% | 20.13% |
| **Branch** | 38.28% | 85% | 46.72% |
| **Line** | 55.81% | 85% | 29.19% |

The systematic implementation of `RoundTripTest` classes across almost all sub-specifications has drastically increased coverage. However, complex internal parameter logic (especially in GOCA and BCOCA) and performance-optimized fast-paths in `AfpJacksonXmlWriter` remain primary areas for improvement.

## 2. Specification-Level Gaps

The following table summarizes coverage by AFP sub-specification, mapped from the Java package structure:

| Specification | Primary Package | Coverage | Status |
| :--- | :--- | :--- | :--- |
| **MO:DCA** | `com.mgz.afp.modca` | 79.24% | 🟡 Moderate Gap |
| **GOCA** | `com.mgz.afp.goca` | 55.29% | 🟡 Moderate Gap |
| **IOCA** | `com.mgz.afp.ioca` | 68.18% | 🟡 Moderate Gap |
| **FOCA** | `com.mgz.afp.foca` | 72.95% | 🟡 Moderate Gap |
| **Triplets** | `com.mgz.afp.triplets` | 74.47% | 🟡 Moderate Gap |
| **PTOCA** | `com.mgz.afp.ptoca.*` | 65.26% | 🟡 Moderate Gap |
| **Line Data** | `com.mgz.afp.lineData` | 73.32% | 🟡 Moderate Gap |
| **CMOCA** | `com.mgz.afp.cmoca` | 74.12% | 🟡 Moderate Gap |
| **BCOCA** | `com.mgz.afp.bcoca` | 48.48% | 🔴 Critical Gap |
| **Metadata** | `com.mgz.afp.moca` | 43.13% | 🔴 Critical Gap |

## 3. Major Package Analysis

### 3.1. High-Impact Untested Areas
- **`com.mgz.xml` (37.66%):** Contains the core XML generation logic. `AfpJacksonXmlWriter` has 1,771 uncovered instructions, mostly in specialized handlers and fast-paths.
- **`com.mgz.afp.bcoca` (48.48%):** While basic SFs are covered, many barcode-specific parameters (e.g., Data Matrix) are entirely untested.
- **`com.mgz.afp.goca` (55.29%):** Drawing orders have good coverage, but many internal parameter structures and edge cases in `GDD_Parameter` are not exercised.
- **`com.mgz.afp.parser` (49.29%):** Parallel processing logic (`ParallelAfpConverter`, `ParallelPageParser`) is largely untested due to environment/heap constraints during standard test runs.

### 3.2. Top 20 Uncovered Classes
1. `com.mgz.xml.AfpJacksonXmlWriter` (1,771 instructions)
2. `com.mgz.util.UtilCharacterEncoding` (1,178 instructions)
3. `com.mgz.afp.parser.AFPParser` (739 instructions)
4. `com.mgz.util.PTXPerformanceMonitor` (721 instructions)
5. `com.mgz.afp.parser.AFPScanner` (624 instructions)
6. `com.mgz.afp.base.StructuredFieldIntroducer` (439 instructions)
7. `com.mgz.afp.base.StructuredField` (409 instructions)
8. `com.mgz.goca.GDD_Parameter$SetCurrentDefaultInstruction$CharacterAttributes` (359 instructions)
9. `com.mgz.util.UtilBinaryDecoding` (306 instructions)
10. `com.mgz.goca.GDD_Parameter$WindowSpecification` (305 instructions)
11. `com.mgz.bcoca.BDA_BarCodeData$ParametersDataMatrixBarcode$SpecialFunctionFlag` (283 instructions)
12. `com.mgz.util.UtilReflection` (278 instructions)
13. `com.mgz.goca.GDD_Parameter$SetCurrentDefaultInstruction$ArcParameters` (276 instructions)
14. `com.mgz.goca.GDD_Parameter$SetCurrentDefaultInstruction$LineAttributes` (262 instructions)
15. `com.mgz.afp.parser.ParallelAfpConverter$PageConversionTask` (256 instructions)
16. `com.mgz.bcoca.BDA_BarCodeData$ParametersDataMatrixBarcode` (242 instructions)
17. `com.mgz.util.MnemonicPerformanceMonitor` (236 instructions)
18. `com.mgz.goca.GDD_Parameter$SetCurrentDefaultInstruction$ProcessColorAttributes` (222 instructions)
19. `com.mgz.afp.parser.ParallelAfpConverter` (219 instructions)
20. `com.mgz.afp.foca.FNC_FontControl` (217 instructions)

## 4. Root Causes of Low Coverage (Updated)
1. **Unexercised Fast-Paths:** High-performance code paths in `AfpJacksonXmlWriter` are skipped in favor of generic reflective paths in most tests.
2. **Environmental Limitations:** Parallel processing and large-file tests (100MB+) often trigger `OutOfMemoryError` or timeouts in constrained CI/CD environments, leading to them being skipped or failing.
3. **Complex Parameter Depth:** While `RoundTripTest` handles the serialization of major structures, it often uses simple default values for complex nested parameters (like BCOCA's Data Matrix flags).

## 5. Path to 85% Coverage
- **Fast-Path Verification:** Implement unit tests that explicitly force the use of fast-paths in `AfpJacksonXmlWriter` and compare the output to the standard path.
- **Deep Parameter Testing:** Enhance `RoundTripTest` classes to use "maximal" objects that exercise every single flag and optional field in complex structures like `GDD_Parameter` and `BDA_BarCodeData`.
- **Parser Robustness:** Implement tests for `AFPScanner` and improve coverage of `AFPParser` error recovery paths.
- **CI/CD Optimization:** Refactor `PerformanceRegressionTest` to use more memory-efficient data generation to allow execution in standard test runs.
