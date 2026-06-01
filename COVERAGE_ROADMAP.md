# Alpheus AFP Parser Code Coverage Roadmap

This document outlines a phased, actionable plan to increase the code coverage of the Alpheus AFP Parser from its current ~49% to the target of **85%**.

## Status Summary (June 2026)
- **Current Coverage:** 49.00% (Instruction)
- **Target Coverage:** 85.00%
- **Gap:** 36.00%

---

## Phase 1: Foundation - MO:DCA & Triplets (Target: 35%) ✅
- ✅ **1.1. Systematic Round-Trip Testing for MO:DCA** (Completed: 78% coverage)
- ✅ **1.2. Triplet Coverage Expansion** (Completed: 74% coverage)

---

## Phase 2: Architectural Depth - GOCA, IOCA, FOCA (Target: 55%) 🟢
- ✅ **2.1. GOCA Drawing Orders** (Completed: 54% coverage)
- ✅ **2.2. IOCA Image Segments** (Completed: 67% coverage)
- ✅ **2.3. FOCA Font Logic** (Completed: 71% coverage)

---

## Phase 3: Robustness & Edge Cases (Target: 75%) 🚧
- ⏳ **3.1. Systematic Fuzzing**
- ✅ **3.2. SFI Extensions & Padding** (Verified in `SFIExtensionsAndPaddingTest.java`)
- ✅ **3.3. Error Handling Paths** (Verified in `SFIErrorHandlingTest.java`)

---

## Phase 4: Full Compliance & Optimization (Target: 85%+) 🚧
- 🚧 **4.1. Special Specifications**
    - 🚧 CMOCA, BCOCA, and MOCA verification (Currently 35-45% coverage).
    - ⏳ Implementation of remaining 1,000+ normative requirements per spec.
- ⏳ **4.2. Performance fast-paths**
    - Verification of `AfpJacksonXmlWriter` optimized paths.
- 🏗️ **4.3. PDF Renderer Verification**
    - Implement tests for `com.mgz.pdf`.

---

## Implementation Strategy
1. **Traceability:** Every new test must explicitly reference a Requirement ID from the `/specifications/markdown/` directory.
2. **Acceptance Integration:** Ensure all round-trip tests are integrated into the default Gradle test task.
3. **Continuous Monitoring:** Run `./gradlew jacocoTestReport` after every change.
