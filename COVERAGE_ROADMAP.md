# Alpheus AFP Parser Code Coverage Roadmap

This document outlines a phased, actionable plan to increase the code coverage of the Alpheus AFP Parser from its current ~15% to the target of **85%**.

## Status Summary (May 2026)
- **Current Coverage:** 15.09% (Instruction)
- **Target Coverage:** 85.00%
- **Gap:** 69.91%

---

## Phase 1: Foundation - MO:DCA & Triplets (Target: 35%)
The goal of this phase is to cover the most frequent building blocks of AFP documents.

### 1.1. Systematic Round-Trip Testing for MO:DCA
- Implement `RoundTripTest` classes for all 80+ Structured Fields in `com.mgz.afp.modca`.
- Use `RoundTripTestUtils` to verify that every SF can be decoded and encoded without data loss.
- **Priority:** `BDT`, `EDT`, `BPG`, `EPG`, `BAG`, `EAG`.

### 1.2. Triplet Coverage Expansion
- Implement unit tests for all 50+ Triplets in `com.mgz.afp.triplets`.
- Exercise all optional parameters and varying lengths for complex triplets like `FullyQualifiedName` (X'02') and `ColorSpecification` (X'4E').

---

## Phase 2: Architectural Depth - GOCA, IOCA, FOCA (Target: 55%)
This phase focuses on the data-heavy sub-specifications.

### 2.1. GOCA Drawing Orders [🏗️ Partially Complete]
- [x] Create a new suite for `com.mgz.afp.goca`: `GOCADrawingOrderRoundTripTest`.
- [ ] Test every Drawing Order (e.g., `Gline`, `GArc`, `GArea`) via round-trip logic.

### 2.2. IOCA Image Segments [🏗️ Partially Complete]
- [x] Exercise high-frequency image segments in `com.mgz.afp.ioca` (e.g., `IDESize`, `IDEStructure`, `FunctionSetIdentification`).
- [ ] Verify support for different compression types (MMR, JPEG, etc.) at the parsing level.

### 2.3. FOCA Font Logic
- Implement tests for Font Character Sets and Code Pages in `com.mgz.afp.foca`.

---

## Phase 3: Robustness & Edge Cases (Target: 75%)
Focus on branch coverage and defensive logic.

### 3.1. Systematic Fuzzing
- Integrate `AFPFuzzTest` (Jazzer) into the regular CI feedback loop.
- Use the discovered "crashers" and "new paths" to create regression tests for complex decoding logic.

### 3.2. SFI Extensions & Padding
- Create synthetic AFP streams that exercise Structured Field Introducer (SFI) extensions:
    - **Segmentation:** SFs split across multiple records.
    - **Padding:** SFs with trailing bytes.
    - **Encryption:** SFs with the encryption flag set.

### 3.3. Error Handling Paths
- Explicitly test every `AFPParserException` branch in `AFPParser` and `StructuredFieldIntroducer`.

---

## Phase 4: Full Compliance & Optimization (Target: 85%+)
Cover remaining specialized areas and refactored logic.

### 4.1. Special Specifications
- Implement tests for `com.mgz.afp.lineData` and `com.mgz.afp.cmoca`.
- Verify the 1:1 mapping of normative requirements in `TEST_COVERAGE_*.md` files.

### 4.2. Performance fast-paths
- Ensure that optimized fast-paths in `AfpJacksonXmlWriter` (e.g., for `PTX` and `TRN`) are verified against the default JAXB output.

---

## Implementation Strategy
1. **Tooling:** Use a code generation script to create boilerplate `RoundTripTest` classes for any `StructuredField` with 0% coverage.
2. **Acceptance Integration:** Add the synthetic files generated during unit testing to the `FilesSuite` to ensure they are also exercised in end-to-end CLI tests.
3. **Continuous Monitoring:** Update `COVERAGE_GAP.md` after every major PR to track progress against this roadmap.
