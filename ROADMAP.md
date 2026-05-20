# Alpheus AFP Parser Roadmap

This roadmap outlines the project's evolution, from CI/CD migration to full AFP specification compliance and modernization.

## Progress Overview

| Phase | Description | Status |
| :--- | :--- | :---: |
| 1 | Setup GitHub Actions Workflow | ✅ |
| 2 | Build and Test Configuration | ✅ |
| 3 | Artifact Management | ✅ |
| 4 | Cleanup and Finalization | ✅ |
| 5a | CI/CD Optimizations | ✅ |
| 5b | Specification Excellence | ✅ |
| 6a | Testing and Quality Assurance (i) | ✅ |
| 6b | Complete AFP Syntax Implementation | ✅ |
| 6c | Testing and Quality Assurance (ii) | ✅ |
| 7 | Modernization of Build System and Language | ✅ |
| 8 | Modernized Publishing and Distribution | 🚧 |
| 9 | Project Documentation and Maintenance | 🚧 |
| 10 | Performance Optimization | 🚧 |

## Granular Requirement Coverage Analysis
This section tracks the verification status of ~21,000 granular normative requirements extracted from the AFP specifications.
Verification is currently initialized to 0% and will progress as granular tests are implemented.

| Specification | Covered | Total | % |
| :--- | :---: | :---: | :---: |
| [BCOCA](TEST_COVERAGE_BCOCA.md) | 1237 | 1237 | 100.0% |
| [CMOCA](TEST_COVERAGE_CMOCA.md) | 1085 | 1085 | 100.0% |
| [DB2Z](TEST_COVERAGE_DB2Z.md) | 0 | 659 | 0.0% |
| [FOCA](TEST_COVERAGE_FOCA.md) | 1391 | 1391 | 100.0% |
| [GOCA](TEST_COVERAGE_GOCA.md) | 2144 | 2144 | 100.0% |
| [IOCA](TEST_COVERAGE_IOCA.md) | 1587 | 1587 | 100.0% |
| [IPDS](TEST_COVERAGE_IPDS.md) | 0 | 6286 | 0.0% |
| [LINEDATA](TEST_COVERAGE_LINEDATA.md) | 0 | 863 | 0.0% |
| [MOCA](TEST_COVERAGE_MOCA.md) | 173 | 173 | 100.0% |
| [MODCA](TEST_COVERAGE_MODCA.md) | 0 | 4139 | 0.0% |
| [PTOCA](TEST_COVERAGE_PTOCA.md) | 1488 | 1488 | 100.0% |
| **Total** | **8825** | **21052** | **41.9%** |

## Goals
- ✅ Ensure all artifacts (JARs, POM) are correctly generated.
- 🚧 Achieve 100% AFP specification coverage (MO:DCA, PTOCA, GOCA, etc.).
- 🚧 Implement robust XML extraction for all human-readable text.

---

## Phase 6b: Complete AFP Syntax Implementation and Coverage
- ✅ Address MO:DCA implementation gaps (see `GAP_MODCA.md`).
    - ✅ Fix the keyword loop in `MMC_MediumModificationControl.decodeAFP`.
    - ✅ Fix triplet offset bug in `MBC_MapBarCodeObject` and `MCD_MapContainerData`.
    - ✅ Fix reserved byte offset bug in `MMO_MapMediumOverlay` repeating groups.
    - ✅ Implement missing `MPT` (Map Presentation Text, X'D3AB9B') Structured Field.
    - ✅ Implement missing Triplets: `X'8F'` (Function Set), `X'9D'` (Keep Group Together), `X'9E'` (Setup Name).
    - ✅ Implement `TripletExtender` (X'FF') logic in `TripletParser`.
    - ✅ Complete payload parsing for all identified "shallow" Structured Fields.
    - ✅ Identify all Structured Fields currently using "shallow" implementations (see `SHALLOW_FIELDS_REPORT.md`).
        - ✅ Implement full `decodeAFP` and `writeAFP` for identified shallow fields:
        - ✅ MO:DCA Object Containers and Image Data (BII, EII, IRD, EIM, EOC, OCD).
        - ✅ MO:DCA Overlay and Page Segment.
            - ✅ End Overlay (EMO).
            - ✅ End Page Segment (EPS).
        - ✅ MO:DCA Other structures.
            - ✅ End Object Environment Group (EOG).
            - ✅ End Print File (EPF).
            - ✅ End Resource (ERS).
        - ✅ FOCA Coded Font, Code Page, and Font Character Set fields (BCF, ECF, ECP, EFN, FNN, FNG).
        - ✅ BCOCA End Bar Code Object (EBC).
        - ✅ PTOCA End Presentation Text Object (EPT).
        - ✅ CMOCA Color Management Resource (CMR).
        - ✅ MO:DCA Color Attribute Table (CAT).
        - ✅ GOCA Graphics Data (GAD) and End Graphics Object (EGR).
        - ✅ MO:DCA No Operation (NOP).
        - ✅ Line Data related fields (BDX, EDX, BPM, EPM, DXD, EDM, IDM).
- ✅ Enhance GOCA and IOCA support.
    - ✅ Fix encoding and decoding bugs in GOCA drawing orders and IOCA segments.
    - ✅ Fix parameter length parsing bug in `GDD_GraphicsDataDescriptor`.
    - ✅ Implement full payload parsing for missing IOCA segments and GOCA drawing orders:
        - ✅ GOCA Drawing Orders:
            - ✅ Set Custom Line Type (GSCLT, X'20').
            - ✅ End Custom Pattern (GECP, X'5E').
            - ✅ Set Pattern Reference Point (GSPRP, X'A0').
            - ✅ Begin Custom Pattern (GBCP, X'DE').
            - ✅ Delete Pattern (GDPT, X'DF').
            - ✅ Linear Gradient (GLGD, X'FEDC').
            - ✅ Radial Gradient (GRGD, X'FEDD').
            - ✅ Legacy/No-Op: GSPIK (X'43'), End Segment (X'71').
        - ✅ IOCA Segments:
            - ✅ Set Extended Bilevel Image Color (X'F4').
            - ✅ Set Bilevel Image Color (X'F6').
            - ✅ IOCA Function Set Identification (X'F7').
            - ✅ nColor Names (X'FEB3').
    - ✅ Improve `GAD_GraphicsData` to handle all GOCA drawing orders more robustly.
    - ✅ Improve `IPD_ImagePictureData` to handle all IOCA segments, reducing "Unknown" segments.
- ✅ Stateful Encoding and Character Set Resolution:
    - ✅ Implement context in `AFPParser` to track stateful LID-to-Charset bindings.
    - ✅ Update `AFPParserConfiguration` to maintain a registry of active Coded Font Local Identifiers (LIDs).
    - ✅ Implement logic for `PTX` streams to dynamically switch active Charset upon `SCFL` (Set Coded Font Local) commands.
    - ✅ Fix hardcoded `cp500` defaults in `getText()` methods for GOCA, BCOCA, IOCA, and FOCA (PTOCA/PTX fixed).
- ✅ Complete PTOCA support based on PTOCA Reference (AFPC-0005-04).
    - ✅ Fix PTOCA Text Visibility Gaps (see `PTOCA_GAP.md`):
        - ✅ Support **free-standing graphic characters** interleaved with control sequences in `PTX`.
        - ✅ Implement missing PT4 Glyph Layout sequences: `GLC` (X'6D'), `GIR` (X'8B'), `GAR` (X'8C/8D'), `GOR` (X'8E/8F').
        - ✅ Support **concatenated payloads** for `SEA` (Alternate Text) and `SKI` (Key Info).
        - ✅ Expose `OVS` (Overstrike) characters as text in XML output.
    - ✅ Implement PTOCA Control Sequences:
        - ✅ Unicode Complex Text (UCT).
        - ✅ Encryption controls (SEA, SKI, ENC).
    - ✅ Implement XML text extraction (`getText()`) for identified human-readable text sources (see `PRINTED_TEXT_SOURCES.md`):
        - ✅ GOCA: `GCHST`, `GCCHST`, `GCOMT`, `BeginSegment`.
        - ✅ MO:DCA: `TLE`, Triplet `X'36'`, Triplet `X'65'`, `COMT`.
        - ✅ BCOCA: `BDA`.
        - ✅ IOCA: `nColorNames`, `BeginSegment`.
        - ✅ Line Data: `CCP`.
        - ✅ CMOCA: `CMR Header`.
        - ✅ FOCA: `CPDesc`, `TypeFcDesc`, `Resource Names`.
- ⏳ Ensure 100% coverage of the AFP specification.

## Phase 8: Modernized Publishing and Distribution
- ✅ Replace the deprecated 'maven' plugin with the 'maven-publish' plugin.
- ⏳ Configure automated publishing to Maven Central via GitHub Actions.
- ✅ Set up automated release notes and asset generation using GitHub Releases.

## Phase 9: Project Documentation and Maintenance
- 🚧 Improve all `specifications/markdown` formatting up to the 'Excellent' standard of neighbouring specifications (consistent with PTOCA).
    - 🚧 Improve all `specifications/markdown/modca-reference-10` chapters:
        - ✅ Chapters 1-3 complete.
        - ✅ Chapter 4 complete.
        - 🚧 Chapter 5 in-progress:
            - ✅ All 'Begin' fields complete (BAG-BSG).
            - ✅ All 'End' fields complete (EAG-ESG).
            - ✅ SF Groups A-D.
            - ✅ SF Groups G-I.
            - ✅ SF Groups J-O (LLE-MFC complete).
            - ✅ SF Groups P-T (PEC, PFC, PGD, PGP, PMC, PPO, PTD, PTX, TLE complete).
            - ✅ SF Groups U-Z (XMD moved to Line Data).
        - ⏳ Chapter 6 (Triplets).
        - ⏳ Chapter 7 (Interchange Sets).
        - ⏳ Chapter 8 (Function Sets).
        - ⏳ Appendix A (Color Resources).
        - ⏳ Appendix B (Standard OCA Color Value Table).
        - ⏳ Appendix C (MO:DCA Registry).
        - ⏳ Appendix D (MO:DCA Resource Search Order).
        - ⏳ Appendix E (Cross-Reference Tables).
        - ⏳ Appendix F (MO:DCA Summary Tables).
        - ⏳ Glossary, Index, Front Matter, and Notices.
    - 🚧 Improve all `specifications/markdown/linedata-reference-05` chapters:
        - ✅ Chapters 1-3 complete.
        - ✅ Chapter 4 complete.
        - 🚧 Chapter 5 in-progress:
            - ⏳ XMD (XML Descriptor).
        - ⏳ Appendices A-B and Front Matter.
    - ⏳ Verify all other files in `specifications/markdown` and subdirectories meet the standards (BCOCA, DB2Z, GOCA, IOCA, IPDS, MOCA, PTOCA).
    - 🚧 Improve all `specifications/markdown/cmoca-reference-02` chapters:
        - ✅ Chapters 1-3 complete.
        - ✅ Chapters 4-5 complete.
        - ⏳ Chapter 6.
        - ⏳ Appendices A-C.
    - 🚧 Improve all `specifications/markdown/foca-reference-06` chapters:
        - ⏳ Chapters 1-5.
        - ✅ Chapter 6 complete.
        - ⏳ Chapter 7.
        - ⏳ Appendices A-C, Glossary, and Front Matter.
- ✅ Analyzing and updating the `TEST_COVERAGE_PTOCA.md` in reasonable chunks.
- ✅ Analyzing and updating the `TEST_COVERAGE_MOCA.md` in reasonable chunks.
- ✅ Analyzing and updating the `TEST_COVERAGE_FOCA.md` complete.
- ✅ Analyzing and updating the `TEST_COVERAGE_GOCA.md` in reasonable chunks:
    - ✅ Chapters 1-3 complete.
    - ✅ Chapter 4 complete.
    - ✅ Chapters 5-6 complete.
    - ✅ Chapter 7 (Commands and Drawing Orders) complete.
    - ✅ Chapters 8-9 complete.
    - ✅ Appendices A-D complete.
- ✅ Analyzing and updating the `TEST_COVERAGE_IOCA.md` in reasonable chunks:
    - ✅ Chapters 1-6 complete.
    - ✅ Chapter 7 (Function Sets) complete.
    - ✅ Appendices A-G complete.
- ✅ Analyzing and updating the `TEST_COVERAGE_BCOCA.md` complete.
- ✅ Analyzing and updating the `TEST_COVERAGE_CMOCA.md` complete.
- ⏳ Improve Javadoc quality and compliance:
    - ✅ Add missing comments for classes, constructors, methods, and enums (Improved AFPDocument, AFPException, AFPColorSpace, AFPColorValue, AFPParser, AFPParserConfiguration, AFPParserException, AFPValidationException, AFPReferenceCoordinateSystem, AFPUnitBase, IAFPWriter, AFPWriterHumanReadable, BAG, BBC, BCA, BCF, BCP, UtilBinaryDecoding, UtilCharacterEncoding).
    - ✅ Add missing `@return` tags in annotations (Improved AFPField, IHasTriplets, StructuredField).
    - ✅ Fix malformed HTML and empty `<p>` tags (Fixed MCA).
- ⏳ Resolve Checkstyle violations (~4,400 warnings):
    - ⏳ Fix Javadoc-related violations (~1,000 warnings):
        - ⏳ `MissingJavadocMethod` (~530).
        - ⏳ `MissingJavadocType` (~380).
        - ⏳ `JavadocParagraph` (~64).
        - ✅ `SummaryJavadoc`.
    - 🚧 Fix Naming and Abbreviation violations (~1,270 warnings):
        - ⏳ `AbbreviationAsWordInName` (~715).
        - ⏳ `TypeName` (~318).
        - ⏳ `GoogleNonConstantFieldName` (~124).
        - ⏳ `ParameterName` (~107).
        ✅ `LocalVariableName`.
    - ✅ Fix `AvoidStarImport` violations.
    - 🚧 Fix Formatting and Layout violations (~2,000 warnings):
        - ⏳ `LineLength` (~700).
        - ⏳ `CustomImportOrder` (~528).
        - ✅ `EmptyLineSeparator`.
        - ⏳ `Indentation` (~182).
        - ⏳ `LeftCurly` / `RightCurly` (~180).
    - ⏳ Address remaining miscellaneous violations (~130 warnings):
        - ✅ `FileTabCharacter` resolved.
        - ✅ `CommentsIndentation`.
        - ✅ `AnnotationLocation`.
        - ✅ Miscellaneous low-count violations (`EmptyCatchBlock`, `ArrayTypeStyle`, `MultipleVariableDeclarations`).
- ⏳ Set up Dependabot or Renovate for automated dependency and Gradle updates.
- ✅ Add a comprehensive contributing guide (`CONTRIBUTING.md`).
- ✅ Implement a SECURITY.md policy.

## Phase 10: Performance Optimization
This phase focuses on the architectural improvements outlined in `CONCEPT_PERFORMANCE.md` to support high-performance parsing and conversion of large AFP files (5 MB to 50 MB and beyond).

- ✅ Streaming Architecture (Event-Driven):
    - ✅ Implement StAX-based Streaming Writer to decouple XML generation from the `AFPDocument` list.
    - ✅ Update `Afp2Xml` CLI to process SFs in a loop (`parse` -> `write` -> `discard`) for O(1) memory footprint.
    - ✅ Implement a basic streaming XPath filter. **Note:** The XPath engine functionality must be preserved, though it may be limited to XPath 1.0 in streaming mode.
- 🚧 Memory-Efficient Object Model:
    - ✅ Standardize the use of `isBuildShallow` in `AFPParser` to ensure state-relevant fields (MCF, MDR, FNC, etc.) are always decoded.
    - ✅ Integrate `java.nio.MappedByteBuffer` for zero-copy parsing:
        - ✅ Update `StructuredFieldIntroducer` and `StructuredField` to support reading payloads from `ByteBuffer`.
        - ✅ Implement AFP file mapping using `FileChannel.map`.
        - ✅ Adapt `AFPParser` to utilize `MappedByteBuffer` for parsing.
    - 🚧 Implement object pooling:
        - ✅ `StructuredFieldIntroducer` pooling.
        - ⏳ common payload objects pooling.
- ⏳ Parallel Processing:
    - ⏳ Implement "seek-and-parse" worker pool strategy for parallel page parsing:
        - ⏳ Implement page boundary discovery by scanning for `BPG_BeginPage` markers.
        - ⏳ Create a worker pool for processing identified page segments in parallel.
        - ⏳ Implement logic to merge global state updates from parallel workers into `AFPParserConfiguration`.
    - ⏳ Utilize `AsynchronousFileChannel` for overlapping I/O and processing.
- 🚧 Specialized Optimizations:
    - ✅ Replace reflection-based class lookup in `AFPParser` with a pre-computed static mapping (Supplier-based).
    - ✅ Implement custom fast CP500 EBCDIC-to-UTF8 decoder for high-frequency fields.
