# Jackson Fallback Removal Roadmap

This roadmap outlines the steps required to eliminate "Jackson Fallback" in `AfpJacksonXmlWriter`. Fallback occurs when a MO:DCA element lacks a manual StAX fast-path, triggering reflection-based serialization via `ObjectWriter`, which is significantly slower.

## Status Summary
- **Phase 1: FOCA Structured Fields**: ✅ Completed
- **Phase 2: Triplets**: ⏳ Pending
- **Phase 3: GOCA Drawing Orders**: ✅ Completed
- **Phase 4: IOCA, PTOCA & GDD Elements**: ✅ Completed
- **Phase 5: Remaining MO:DCA Structured Fields**: ⏳ In Progress
- **Phase 6: Verification & Full Coverage**: ⏳ Pending

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

## Phase 3: GOCA Drawing Orders ✅
Optimize remaining GOCA (Graphics Object Content Architecture) drawing orders.

- [x] **Identify Rare Orders**: Audit `writeDrawingOrderDirectly` for `else` block triggers. ✅
- [x] **Implement Fast-Paths**: Create manual StAX writers for all identified rare drawing orders. ✅

## Phase 4: IOCA, PTOCA & GDD Elements ✅
Complete the fast-path implementation for IOCA (Image), PTOCA (Text), and GDD (Graphics Data Descriptor) sub-elements.

- [x] **IOCA Self-Defining Fields**: Implement fast-paths in `writeIddSelfDefiningFieldDirectly`. ✅
- [x] **IOCA Segments**: Ensure all `IPD_Segment` types are handled in `writeIpdSegmentDirectly`. ✅
- [x] **PTOCA Control Sequences**: Audit `writeControlSequence` and implement missing sequences. ✅
- [x] **GDD Parameters**: Implement fast-paths for all `GDD_Parameter` types in `writeGddParameterDirectly`. ✅

## Phase 5: Remaining MO:DCA Structured Fields ⏳
Implement manual StAX fast-paths for remaining MO:DCA structured fields to eliminate reflection-based serialization.

- [ ] **Line Data Fields**:
    - [x] **LNC** (Line Descriptor Count) ✅
    - [x] **CCP** (Conditional Processing Control) ✅
    - [x] **RCD** (Record Descriptor) ✅
    - [x] **XMD** (XML Descriptor) ✅
- [x] **IM Image Fields** ✅:
    - [x] **IID** ✅ (IM Image Input Descriptor)
    - [x] **ICP** ✅ (IM Image Cell Position)
    - [x] **IRD** ✅ (IM Image RasterData)
    - [x] **IOC** ✅ (IM Image Output Control)
- [x] **Color Attribute Table Fields** ✅:
    - [x] **BCA** / **ECA** ✅ (Begin/End Color Attribute Table)
    - [x] **CAT** ✅ (Color Attribute Table)
    - [x] **MCA** ✅ (Map Color Attribute Table)
- [ ] **Map Fields**:
    - [ ] **MBC** (Map BarCode Object)
    - [ ] **MMD** (Map Media Destination)
    - [ ] **MMO** (Map Medium Overlay)
    - [ ] **MMT** (Map MediaType)
    - [ ] **MPG** (Map Page)
    - [ ] **MPS** (Map Page Segment)
    - [ ] **MPT** (Map PresentationText)

## Phase 6: Verification & Full Coverage ⏳
Ensure zero fallback usage and verify performance gains.

- [ ] **Zero-Fallback Audit**:
    - [ ] Instrument `JacksonXmlMapperProvider.getCachedWriter` to log or throw exceptions during test runs.
    - [ ] Run full test suite to ensure no elements trigger the fallback.
- [ ] **Performance Benchmarking**:
    - [ ] Run `tools/benchmark_large.sh` and compare results against the v15.6 baseline.
    - [ ] Verify reduction in CPU time for the "Jackson Fallback" hotspot in JFR profiles.
- [ ] **Generic Full Coverage**: Perform a final scan of the ADF model to ensure every `StructuredField` and sub-element has a corresponding manual StAX implementation.
