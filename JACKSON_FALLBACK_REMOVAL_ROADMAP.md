# Jackson Fallback Removal Roadmap

This roadmap outlines the steps required to eliminate "Jackson Fallback" in `AfpJacksonXmlWriter`. Fallback occurs when a MO:DCA element lacks a manual StAX fast-path, triggering reflection-based serialization via `ObjectWriter`, which is significantly slower.

## Status Summary
- **Phase 1: FOCA Structured Fields**: ✅ Completed
- **Phase 2: Triplets**: ⏳ Pending
- **Phase 3: GOCA Drawing Orders**: ⏳ Pending
- **Phase 4: IOCA, PTOCA & GDD Elements**: ⏳ Pending
- **Phase 5: Verification & Full Coverage**: ⏳ Pending

---

## Phase 1: FOCA Structured Fields ✅
Implement manual StAX fast-paths for all FOCA (Font Object Content Architecture) structured fields to eliminate reflection-based serialization.

- [x] **CFC** (Coded Font Control) ✅
- [x] **CFI** (Coded Font Index) ✅
- [x] **CPC** (Code Page Control) ✅
- [x] **CPD** (Code Page Descriptor) ✅
- [x] **CPI** (Code Page Index) ✅
- [x] **FND** (Font Descriptor) ✅
- [x] **FNG** (Font Patterns) ✅
- [x] **FNI** (Font Index) ✅
- [x] **FNM** (Font Patterns Map) ✅
- [x] **FNN** (Font Name Map) ✅
- [x] **FNO** (Font Orientation) ✅
- [x] **FNP** (Font Position) ✅

## Phase 2: Triplets ✅
Eliminate fallback for remaining rare or special-purpose MO:DCA triplets.

- [x] **Undefined Triplet** (0x00) ✅
- [x] **Triplet Extender** (0xFF) ✅
- [x] **Full Coverage Check**: Verify that all 67+ standard triplets use `writeTriplet` fast-paths. ✅

## Phase 3: GOCA Drawing Orders ⏳
Optimize remaining GOCA (Graphics Object Content Architecture) drawing orders.

- [ ] **Identify Rare Orders**: Audit `writeDrawingOrderDirectly` for `else` block triggers.
- [ ] **Implement Fast-Paths**: Create manual StAX writers for all identified rare drawing orders.

## Phase 4: IOCA, PTOCA & GDD Elements ⏳
Complete the fast-path implementation for IOCA (Image), PTOCA (Text), and GDD (Graphics Data Descriptor) sub-elements.

- [x] **IOCA Self-Defining Fields**: Implement fast-paths in `writeIddSelfDefiningFieldDirectly`. ✅
- [x] **IOCA Segments**: Ensure all `IPD_Segment` types are handled in `writeIpdSegmentDirectly`. ✅
- [x] **PTOCA Control Sequences**: Audit `writeControlSequence` and implement missing sequences. ✅
- [x] **GDD Parameters**: Implement fast-paths for all `GDD_Parameter` types in `writeGddParameterDirectly`. ✅

## Phase 5: Verification & Full Coverage ⏳
Ensure zero fallback usage and verify performance gains.

- [ ] **Zero-Fallback Audit**:
    - [ ] Instrument `JacksonXmlMapperProvider.getCachedWriter` to log or throw exceptions during test runs.
    - [ ] Run full test suite to ensure no elements trigger the fallback.
- [ ] **Performance Benchmarking**:
    - [ ] Run `tools/benchmark_large.sh` and compare results against the v15.6 baseline.
    - [ ] Verify reduction in CPU time for the "Jackson Fallback" hotspot in JFR profiles.
- [ ] **Generic Full Coverage**: Perform a final scan of the ADF model to ensure every `StructuredField` and sub-element has a corresponding manual StAX implementation.
