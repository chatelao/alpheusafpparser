# Alpheus AFP Parser Code Coverage Gap Analysis

## 1. Executive Summary
The target code coverage for the Alpheus AFP Parser is **85%**. After reassessment, the project has made significant progress and is much closer to the target than previously reported.

| Metric | Current Coverage | Target | Gap |
| :--- | :--- | :--- | :--- |
| **Instruction** | 49% | 85% | 36% |
| **Branch** | 23% | 85% | 62% |
| **Line** | 42% | 85% | 43% |

*Note: These metrics reflect a full execution of all unit and round-trip tests.*

## 2. Specification-Level Gaps

The following table summarizes coverage by AFP sub-specification:

| Specification | Primary Package | Coverage | Status |
| :--- | :--- | :--- | :--- |
| **MO:DCA** | `com.mgz.afp.modca` | 78% | 🟢 Near Target |
| **GOCA** | `com.mgz.afp.goca` | 54% | 🟡 Moderate Gap |
| **IOCA** | `com.mgz.afp.ioca` | 67% | 🟡 Moderate Gap |
| **FOCA** | `com.mgz.afp.foca` | 71% | 🟢 Near Target |
| **Triplets** | `com.mgz.afp.triplets` | 74% | 🟢 Near Target |
| **PTOCA** | `com.mgz.afp.ptoca.*` | 64% | 🟡 Moderate Gap |
| **Line Data** | `com.mgz.afp.lineData` | 73% | 🟢 Near Target |
| **CMOCA** | `com.mgz.afp.cmoca` | 40% | 🔴 Critical Gap |
| **BCOCA** | `com.mgz.afp.bcoca` | 36% | 🔴 Critical Gap |
| **Metadata** | `com.mgz.afp.moca` | 43% | 🔴 Critical Gap |

## 3. Major Package Analysis

### 3.1. Improved Areas
- **`com.mgz.afp.modca`:** Coverage increased from ~1% to 78% via systematic round-trip testing.
- **`com.mgz.afp.triplets`:** Coverage increased from ~4% to 74%.
- **`com.mgz.afp.goca`, `ioca`, `foca`:** Previously untested areas now have 50-70% coverage.

### 3.2. Remaining Gaps
1. **`com.mgz.xml` (6%):** Manual StAX writing fast-paths need more comprehensive validation against generic paths.
2. **`com.mgz.pdf` (0%):** The new PDF generation backend remains entirely untested.
3. **`com.mgz.afp.bcoca` & `cmoca`:** While some round-trip tests exist, they only cover basic structures. 1,000+ normative requirements per spec still need verification.

## 4. Path to 85% Coverage
- **PDF Backend Testing:** Implement a new suite for `com.mgz.pdf` using visual comparison or PDF structure analysis.
- **Normative Requirement Verification:** Continue Phase 4 of the roadmap, mapping tests to individual requirement IDs in BCOCA and CMOCA.
- **Branch Coverage Focus:** Enhance round-trip tests with edge-case parameters to exercise more logic branches (currently only at 23%).
