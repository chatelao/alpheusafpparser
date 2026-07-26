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

## Phase 2: Architectural Depth - GOCA, IOCA, FOCA, PTOCA, Line Data (Target: 55%) 🟢
- ✅ **2.1. GOCA Drawing Orders** (Completed: 54% coverage)
- ✅ **2.2. IOCA Image Segments** (Completed: 67% coverage)
- ✅ **2.3. FOCA Font Logic** (Completed: 71% coverage)
- ✅ **2.4. PTOCA Control Sequences** (Completed: 64% coverage)
- ✅ **2.5. Line Data Structured Fields** (Completed: 73% coverage)

---

## Phase 3: Robustness & Edge Cases (Target: 75%) 🚧
- ✅ **3.1. Systematic Fuzzing**
    - ✅ **3.1.1. PTOCA Control Sequence Fuzzing**: Seed-based fuzzing of PTOCA control sequences to test parsing resilience (Verified in `PTOCAFuzzTest.java`).
    - ✅ **3.1.2. Structured Field Introducer (SFI) Fuzzing**: Validate SFI padding, segmentation, and boundary limits against malformed headers (Verified in `SFIFuzzTest.java`).
    - ✅ **3.1.3. Triplet Parsing Fuzzing**: Verify resilience when parsing variable-length triplets to prevent out-of-bounds or negative length exceptions (Verified in `TripletFuzzTest.java`).
- ✅ **3.2. SFI Extensions & Padding** (Verified in `SFIExtensionsAndPaddingTest.java`)
- ✅ **3.3. Error Handling Paths** (Verified in `SFIErrorHandlingTest.java`)

---

## Phase 4: Full Compliance & Optimization (Target: 85%+) 🚧
- 🚧 **4.1. Special Specifications**
    - ✅ **4.1.1. CMOCA, BCOCA, and MOCA Verification**
        - ✅ **4.1.1.1. CMOCA Tag Validation**: Focus on verification of color management resources and tag structures (Completed).
        - ✅ **4.1.1.2. BCOCA Architectural Validations**: Verify modifiers, types, and HRI text placement (Completed).
        - ✅ **4.1.1.3. MOCA Metadata Integrity**: Complete tests for Metadata Object syntax (Completed).
    - 🚧 **4.1.2. IPDS Verification** (Currently ~1% coverage)
        - 🚧 **4.1.2.1. Device-Control command set**: Complete tests for command sequence and acknowledgement protocol (MID, SHS, NOP, BP, EP, END, STM, LPP, LPD, LCC, DF, DUA, AFO, ASN, ISP, ICMR, LFE, PFC).
        - 🚧 **4.1.2.2. Text command set**: Validate PTOCA integration and coordinate state tracking within WT commands.
        - 🚧 **4.1.2.3. Resource activation & equivalents**: Add test suites for LE, LFE and deactivation mappings.
        - 🚧 **4.1.2.4. IM/IO-Image & Graphics command sets**: Verify raster images and vector GOCA drawing orders under IPDS context.
        - 🚧 **4.1.2.5. Barcode, Loaded-Font & Container command sets**: Validate 2D barcodes, font mapping (LFC/LFI/LCPC), and object container processing.
    - 🚧 **4.1.3. DB2Z Verification**
        - 🚧 **4.1.3.1. DB2Z Character Encoding Verification**: Validate CDRA character set mappings and EBCDIC/ASCII/Unicode conversion edge cases.
        - 🚧 **4.1.3.2. DB2Z Variant Characters & SQLDA Verification**: Verify DPart TLE and metadata mappings for invariant vs variant characters.
        - 🚧 **4.1.3.3. DB2Z Collation & Locale Services**: Expand tests for COLLATION_KEY and z/OS Unicode Services case/collation mapping.
    - 🚧 **4.1.4. Implementation of remaining 1,000+ normative requirements per spec** (Progress: EC-0210, EC-0505, EC-0600, EC-0610, EC-0700, EC-0705, EC-0800, EC-0900, EC-0A00, EC-0F01-04, EC-0F05, EC-0F06, EC-0F07, EC-0F09, EC-0F0B, EC-0F0C, EC-0F13, EC-0F14, EC-0F15, EC-0F18-25, EC-1200, EC-2100 validated. CMOCA tags 0x0008, 0x0011, 0x1011, 0x1021, 0x1025, 0x1030, 0x1035, 0x1040, 0x1045, 0x1050, 0x1055, 0x1060, 0x1065, 0x1070, 0x1075, 0x1080, 0x2004, 0x2011, 0x2015, 0x2020, 0x3011, 0x3015, 0x3025, 0x4011, 0x4015, 0x4020, 0x4025, 0x4030, 0x4035, 0x4040, 0x4045, 0x4050, 0x4055, 0x4090, 0x5011, 0x5015, 0x5020, 0x5025, 0x5030, 0x5035, 0x5040, 0x5045, 0xFFFF validated.).
- 🚧 **4.2. Performance fast-paths**
    - ✅ Verification of `AfpJacksonXmlWriter` optimized paths (MetadataObject fast-path implemented).
- 🏗️ **4.3. PDF Renderer Verification**
    - ✅ Implement tests for `com.mgz.pdf` (CoordinateTransformer unit tests implemented).

---

## Implementation Strategy
1. **Traceability:** Every new test must explicitly reference a Requirement ID from the `/specifications/markdown/` directory.
2. **Acceptance Integration:** Ensure all round-trip tests are integrated into the default Gradle test task.
3. **Always Verify Your Work.** After every action that modifies the state of the codebase, you **must** use a read-only tool to confirm that the action was executed successfully and had the intended effect. Do not mark a plan step as complete until you have verified the outcome.
4. **Continuous Monitoring:** Run `./gradlew jacocoTestReport` after every change.
