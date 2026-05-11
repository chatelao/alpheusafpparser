# Alpheus AFP Parser Roadmap

This roadmap outlines the project's evolution, from CI/CD migration to full AFP specification compliance and modernization.

## Progress Overview

| Phase | Description | Status |
| :--- | :--- | :---: |
| 1 | Setup GitHub Actions Workflow | ✅ |
| 2 | Build and Test Configuration | ✅ |
| 3 | Artifact Management | ✅ |
| 4 | Cleanup and Finalization | ✅ |
| 5 | CI/CD Optimizations | ✅ |
| 6a | Testing and Quality Assurance (i) | 🚧 |
| 6b | Complete AFP Syntax Implementation | 🚧 |
| 6c | Testing and Quality Assurance (ii) | 🚧 |
| 7 | Modernization of Build System and Language | 🚧 |
| 8 | Modernized Publishing and Distribution | 🚧 |
| 9 | Project Documentation and Maintenance | ⏳ |
| 10 | Specification Excellence | 🚧 |

## Goals
- ✅ Ensure all artifacts (JARs, POM) are correctly generated.
- 🚧 Achieve 100% AFP specification coverage (MO:DCA, PTOCA, GOCA, etc.).
- 🚧 Implement robust XML extraction for all human-readable text.

---

## Phase 10: Specification Excellence
- 🚧 **IPDS (Critical)**:
    - ⏳ Purge physical page markers and recurring PDF headers/footers:
        - ⏳ Chapters 1–5.
        - ⏳ Chapters 6–10.
        - ⏳ Chapters 11–17.
        - ⏳ Appendices A–D.
    - ⏳ Fix OCR-induced broken paragraphs and split words:
        - ⏳ Chapters 1–10.
        - ⏳ Chapters 11–17 and Appendices.
    - ⏳ Remove physical page references ("on page XXX"; ~500 found).
    - ⏳ Convert command and syntax text dumps to Markdown tables:
        - ⏳ Device-Control Command Set (Chapter 4):
            - ✅ Tables 19 (Summary), 20 (Protocol), 21 (Ack Types).
            - ✅ Acknowledge Reply: Syntax, Flags, Data Formats, and Counters.
            - ✅ Activate Resource (AR): Entry Syntax and Tables 22–23.
            - ✅ Commands: ASN, AFO, BP, DF (Table 24), DUA.
            - ✅ Commands: END, EP, ISP, ICMR.
            - ✅ Commands: LCC (Tables 25–27), LFE, LPD, LPP.
            - ✅ Commands: MID, NOP, PFC, SHS, SPE.
            - ✅ Command: Rasterize Presentation Object (RPO):
                - ✅ Syntax, Object Types, and Cache attributes.
                - ✅ Tables 25–26 (Entry Syntax and Object-Type IDs).
                - ✅ Table 27 (Orientation IDs).
                - ✅ Tables 28–29 (Object-Area Size and Mapping-Control IDs).
            - ⏳ Command: Sense Type and Model (STM):
                - ✅ Syntax and Main Reply Table.
                - ✅ Device-Control Command-Set Vector.
                - ⏳ Presentation-Text (PTOCA) Command-Set Vector.
                - ⏳ IM-Image Command-Set Vector.
                - ⏳ IO-Image (IOCA) Command-Set Vector.
                - ⏳ Graphics (GOCA) Command-Set Vector.
                - ⏳ Bar Code (BCOCA) Command-Set Vector.
                - ⏳ Object Container Command-Set Vector.
                - ⏳ Overlay and Page-Segment Command-Set Vectors.
                - ⏳ Loaded-Font Command-Set Vectors.
            - ⏳ Execute Orders: XOA and XOH Order Summaries (Tables 28, 30).
            - ⏳ XOA orders: APA, AOS, CEM, DBD, DUP, MF, OAEI, RRL (Table 29), RSNL.
            - ⏳ XOH orders: DSPG, DGB (Tables 31–32), EFF, ERFD, ERPD.
            - ⏳ XOH Obtain Printer Characteristics (OPC): Table 33 and all SDFs (Tables 34–35).
            - ⏳ XOH orders: PCC, PBD, RSPG, SIMS, SMM, SCF, SMO, SMS.
            - ⏳ XOH Specify Group Operation (SGO).
            - ⏳ XOH Trace (TRC): Table 36 and all Trace Entry syntaxes.
        - ⏳ Text Command Set (Chapter 5).
        - ⏳ IM-Image and IO-Image Command Sets (Chapters 6–7).
        - ⏳ Graphics Command Set (Chapter 8).
        - ⏳ Bar Code Command Set (Chapter 9).
        - 🚧 Object Container Command Set (Chapter 10):
            - ✅ Tables 50 (Summary) and 51 (Secondary Resource Usage).
            - ✅ Commands: DORE and DORE2.
            - ⏳ Commands: DDOFC and DDOR.
            - ⏳ Command: IDO (Syntax and SDFs).
            - ⏳ Commands: RRR and RRRL (including ACK Reply).
            - ⏳ Commands: WOCC and WOC.
        - ⏳ Metadata, Overlay, and Page-Segment Command Sets (Chapters 11–13).
        - 🚧 Loaded-Font Command Set (Chapter 14):
            - ✅ Tables 55 (Summary) and 56 (Baseline Offset).
            - ✅ Commands: LCP and LCPC.
            - ✅ Commands: LF and LFCSC.
            - ⏳ Commands: LFC and LFI.
            - ⏳ Command: LSS.
        - 🚧 Triplets (Chapter 15):
            - ✅ Table 57 (Summary Table).
            - ✅ Triplets X'00' (Group ID), X'01' (Coded Graphic Character Set Global Identifier), and X'02' (Fully Qualified Name).
            - ⏳ Triplets X'4E'–X'6E' (Color Specification, Encoding Scheme ID, Object Offset, Local Date and Time Stamp, Group Information).
            - ⏳ Triplets X'70'–X'84' (Presentation Space Reset Mixing, Toner Saver, Color Fidelity, Metric Adjustment, Font Resolution and Metric Technology).
            - ⏳ Triplets X'85'–X'88' (Finishing Operation, Text Fidelity, Finishing Fidelity).
            - ⏳ Triplets X'8B'–X'92' (Data Object Font Descriptor, Linked Font, UP3I Finishing Operation, CMR Descriptor, Invoke CMR).
            - ⏳ Triplets X'95'–X'A2' (Rendering Intent, CMR Tag Fidelity, Device Appearance, Image Resolution, Object Container Presentation Space Size, Setup Name, Invoke Tertiary Resource).
        - ⏳ Exception Reporting (Chapter 16).
        - ⏳ Compliance and Appendices (Chapter 17 and Appendices A–D).
- 🚧 **BCOCA**:
    - ✅ Purge physical page markers and PDF artifacts from Chapters 1–3.
    - ✅ Purge physical page markers and PDF artifacts from Chapter 4.
    - ✅ Purge physical page markers and PDF artifacts from Chapters 5 and 6.
    - ✅ Purge physical page markers and PDF artifacts from Appendices A–D.
    - ✅ Fix OCR-induced split words (e.g., "T erminate") in Chapters 5, 6, and Appendices.
    - ⏳ Remove physical page references ("on page XXX").
    - ⏳ Format syntax and registry tables:
        - ✅ Chapter 4: Table 8 (BSD), Table 9 (Types).
        - ✅ Chapter 4: Table 10 (Modifiers).
        - ✅ Chapter 4: Table 11 (Color), Table 12 (Sizing).
        - ✅ Chapter 4: Table 13 (Defaults), Table 19 (Check Digits), Table 20 (BSA).
        - ⏳ Chapter 4: Tables 21–33 (Special-Function Parameters).
        - ✅ Chapter 4: Table 34 (Code Pages and Type Styles).
        - ⏳ Chapter 4: Table 35 (Valid Characters and Data Lengths) - Part 1: Symbologies X'01'–X'11'.
        - ⏳ Chapter 4: Table 35 (Valid Characters and Data Lengths) - Part 2: Symbologies X'16'–X'27'.
        - ⏳ Chapter 4: Table 36 (Characters and Code Points).
        - ⏳ Appendices B (MO:DCA), C (IPDS).
- 🚧 **IOCA**:
    - ✅ Remove physical page references ("on page XXX"):
        - ✅ Chapter 3.
        - ✅ Chapter 5.
        - ✅ Chapter 6.
        - ✅ Chapter 7.
        - ✅ Appendices A, D, E, F, G.
    - 🚧 Convert structural descriptors to Markdown tables:
        - ✅ Chapter 4: Table 3 (Code Points).
        - ✅ Chapter 5: Self-Defining Field Syntax tables (Begin/End Segment, Begin/End Image Content).
        - ⏳ Chapter 7: Function Set tables (Tables 5–13).
- 🚧 **AFP GOCA**:
    - ⏳ Convert syntax and registry text dumps to Markdown tables.
    - ⏳ Remove physical page references ("on page XXX").
- 🚧 **Line Data**:
    - ✅ Purge physical page markers and recurring PDF artifacts from Chapter 3 and Appendices.
    - ✅ Fix OCR-induced split words and formatting artifacts across all files.
    - ⏳ Remove physical page references ("on page XXX").
    - ⏳ Convert text-based diagrams in Appendices to standardized formats.
- ✅ **MO:DCA**:
    - ✅ Purge physical page markers and fix split words.
    - ✅ Fix minor table fragmentation in Appendices.
    - ✅ Remove remaining physical page references (4 found).
    - ⏳ **Note**: The project uses AFPC-0004-10 (MO:DCA Reference 10) as the primary specification. The IBM MO:DCA-P Reference (SC31-6802) is a related/legacy IBM version of this architecture.
- 🚧 **FOCA**:
    - ⏳ Fix OCR-induced paragraph merging in technical sections.
    - ⏳ Remove physical page references ("on page XXX").
- 🚧 **PTOCA**:
    - ✅ Purge physical page markers and fix split words.
    - ⏳ Final cleanup of split-word remnants (e.g., "T ext", "T able", "Wr ite").
- ✅ **CMOCA**: Excellent quality; standardized registry and syntax tables.
- ✅ **MOCA**: Excellent quality; cleanest conversion.
- 🚧 **Db2 12 Internationalization Guide**:
    - ✅ Convert to Markdown:
        - ✅ Chapter 1 (Introduction to internationalization).
        - ✅ Chapter 2 (Unicode support).
        - ✅ Chapter 3 (Character conversion).
        - ✅ Chapter 4 (Sorting and collating).
        - ✅ Chapter 5 (Date and time formats).
        - ✅ Chapter 6 (Internationalization for applications).
        - ✅ Appendices A–F.
        - ✅ Glossary
    - ⏳ Purge physical page markers and PDF artifacts.
    - ⏳ Fix OCR-induced split words and formatting artifacts.
    - ⏳ Convert syntax and registry text dumps to Markdown tables.

## Phase 6a: Testing and Quality Assurance (i)
- ⏳ Restore and expand the test suite.
    - ✅ Provide a set of sample AFP files in `src/test/resources/afp` to enable `AFPParserTest`.
    - ⏳ Create a minimal ".afp" file for every chapter markdown file found in `specifications/markdown/` to ensure 1:1 architectural coverage.
    - ✅ Establish a base for granular structure unit testing (round-trip verification).
    - ✅ Implement round-trip unit tests for all MO:DCA Triplets.
        - ✅ Mapping and Identification Triplets (e.g., FQN, Mapping Option, Comment).
        - ✅ Font and Character Triplets (e.g., Font Descriptor, Character Rotation).
        - ✅ Color and Presentation Triplets (e.g., Color Specification, Presentation Space Mixing).
        - ✅ Measurement and Area Triplets (e.g., Measurement Units, Object Area Size, Image Resolution).
        - ✅ Object and Resource Triplets (e.g., Object Classification, Resource Object Type).
        - ✅ Positioning and Size Triplets (e.g., Object Offset, Object Byte Extent, Descriptor Position).
        - ✅ Date and Control Triplets (e.g., Universal Date and Time Stamp, Presentation Control).
        - ✅ Fidelity and Finishing Triplets (e.g., Text Fidelity, Finishing Operation).
    - ✅ Implement round-trip unit tests for all PTOCA Control Sequences.
        - ✅ Movement and Positioning Control Sequences (e.g., AMI, RMI, AMB, RMB).
        - ✅ Appearance and Data Control Sequences (e.g., STC, SEC, TRN, RPS).
    - ⏳ Implement round-trip unit tests for all Structured Fields.
    - ✅ GOCA Structured Fields (BGR, GAD, EGR).
    - ✅ IOCA Structured Fields (IDD, IPD).
    - ✅ Line Data Structured Fields (BDX, EDX, BPM, EPM, DXD, EDM, IDM).
    - ✅ Other Line Data Structured Fields (BDM, XMD, FDS, FDX, LND, LNC, CCP, RCD).
    - ✅ Medium Control Structured Fields (MCC, MMC, MMT, MFC).
    - ✅ CMOCA and MO:DCA-L Structured Fields (BCA, ECA, MCA, CAT, CMR).
    - ✅ Descriptor and Map Structured Fields (PGD, MDD, OBD, CDD, FGD, MDR).
    - ✅ Indexing and Tagging Structured Fields (TLE, IEL, LLE).
    - ✅ Presentation Control Structured Fields (PEC, PFC).
    - ✅ Document and Page Group Structured Fields (BDT, EDT, BPG, EPG, BNG, ENG, BDI, EDI, EPF).
        - ✅ Page and Overlay Structured Fields.
            - ✅ Begin/End Overlay (BMO, EMO).
            - ✅ Begin/End Page Segment (BPS, EPS).
            - ✅ Include Page/Overlay/Segment (IPG, IPO, IPS).
            - ✅ Map Page/Overlay/Segment (MPG, MPO, MPS).
    - ✅ Environment and Resource Group Structured Fields (BDG, EDG, BFM, EFM, BMM, EMM, BRG, ERG, BAG, EAG, BFG, EFG, BSG, ESG, ERS).
        - ✅ Object and Data Structured Fields.
            - ✅ Begin/End Object Container (BOC, EOC).
            - ✅ Begin/End Image/IM Image (BIM, EIM, BII, EII).
            - ✅ Include Object (IOB).
            - ✅ Object/Image Data (OCD, IRD).
    - ✅ Bar Code Object Content Architecture (BCOCA) SFs (BBC, EBC, BDA, BDD).
    - ✅ Presentation Text Object Content Architecture (PTOCA) SFs (BPT, EPT, PTX).
    - ✅ Font Object Content Architecture (FOCA) SFs (BCF, ECF, BCP, ECP, BFN, EFN, FNN, FNG).
- ⏳ Migrate tests from JUnit 4 to JUnit 5 (Jupiter).

## Phase 6b: Complete AFP Syntax Implementation and Coverage
- ✅ Address MO:DCA implementation gaps (see `GAP_MODCA.md`).
    - ✅ Fix the keyword loop in `MMC_MediumModificationControl.decodeAFP`.
    - ✅ Implement missing `MPT` (Map Presentation Text, X'D3AB9B') Structured Field.
    - ✅ Implement missing Triplets: `X'8F'` (Function Set), `X'9D'` (Keep Group Together), `X'9E'` (Setup Name).
    - ✅ Implement `TripletExtender` (X'FF') logic in `TripletParser`.
- 🚧 Complete payload parsing for all identified "shallow" Structured Fields.
    - ✅ Identify all Structured Fields currently using "shallow" implementations (see `SHALLOW_FIELDS_REPORT.md`).
    - 🚧 Implement full `decodeAFP` and `writeAFP` for identified shallow fields:
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
- 🚧 Enhance GOCA and IOCA support.
    - ✅ Fix encoding and decoding bugs in GOCA drawing orders and IOCA segments.
    - ⏳ Implement full payload parsing for missing IOCA segments and GOCA drawing orders:
        - ⏳ GOCA Drawing Orders:
            - ⏳ Set Custom Line Type (GSCLT, X'20').
            - ✅ End Custom Pattern (GECP, X'5E').
            - ✅ Set Pattern Reference Point (GSPRP, X'A0').
            - ⏳ Begin Custom Pattern (GBCP, X'DE').
            - ⏳ Delete Pattern (GDPT, X'DF').
            - ⏳ Linear Gradient (GLGD, X'FEDC').
            - ⏳ Radial Gradient (GRGD, X'FEDD').
            - ⏳ Legacy/No-Op: GSPIK (X'43'), End Segment (X'71').
        - ⏳ IOCA Segments:
            - ⏳ Set Extended Bilevel Image Color (X'F4').
            - ⏳ Set Bilevel Image Color (X'F6').
            - ⏳ IOCA Function Set Identification (X'F7').
            - ⏳ nColor Names (X'FEB3').
    - ⏳ Improve `GAD_GraphicsData` to handle all GOCA drawing orders more robustly.
    - ⏳ Improve `IPD_ImagePictureData` to handle all IOCA segments, reducing "Unknown" segments.
- 🚧 Complete PTOCA support based on PTOCA Reference (AFPC-0005-04).
    - 🚧 Fix PTOCA Text Visibility Gaps (see `PTOCA_GAP.md`):
        - ✅ Support **free-standing graphic characters** interleaved with control sequences in `PTX`.
        - ✅ Implement missing PT4 Glyph Layout sequences: `GLC` (X'6D'), `GIR` (X'8B'), `GAR` (X'8C/8D'), `GOR` (X'8E/8F').
        - ⏳ Support **concatenated payloads** for `SEA` (Alternate Text) and `SKI` (Key Info).
        - ⏳ Improve text extraction heuristics to use the **active font's encoding** instead of hardcoded CP500.
        - ✅ Expose `OVS` (Overstrike) characters as text in XML output.
    - ✅ Implement PTOCA Control Sequences:
        - ✅ Unicode Complex Text (UCT).
        - ✅ Encryption controls (SEA, SKI, ENC).
    - ✅ Implement XML text extraction (`getText()`) for identified human-readable text sources (see `PRINTED_TEXT_SOURCES.md`):
        - ✅ GOCA: `GCHST`, `GCCHST`, `GCOMT`.
        - ✅ MO:DCA: `TLE`, Triplet `X'36'`, Triplet `X'65'`, `COMT`.
        - ✅ BCOCA: `BDA`.
        - ✅ Line Data: `CCP`.
        - ✅ CMOCA: `CMR Header`.
        - ✅ FOCA: `CPDesc`, `TypeFcDesc`, `Resource Names`.
- ⏳ Ensure 100% coverage of the AFP specification.

## Phase 6c: Testing and Quality Assurance (ii)
- ✅ Integrate JaCoCo for code coverage reporting.
- ✅ Add GitHub Actions job for CodeQL analysis.
- ⏳ Update Checkstyle to a modern version and use standard Google or Sun style.

## Phase 7: Modernization of Build System and Language
- ✅ Upgrade Gradle to 8.x.
- ⏳ Migrate build scripts from Groovy to Kotlin DSL (`build.gradle.kts`).
- ⏳ Update Java version to 21 (LTS).
- ⏳ Refactor source code to use modern Java features.

## Phase 8: Modernized Publishing and Distribution
- ✅ Replace the deprecated 'maven' plugin with the 'maven-publish' plugin.
- ⏳ Configure automated publishing to Maven Central via GitHub Actions.
- ✅ Set up automated release notes and asset generation using GitHub Releases.

## Phase 9: Project Documentation and Maintenance
- ⏳ Set up Dependabot or Renovate for automated dependency and Gradle updates.
- ⏳ Migrate project documentation to MkDocs or Docusaurus.
- ⏳ Add a comprehensive contributing guide (`CONTRIBUTING.md`).
- ⏳ Implement a SECURITY.md policy.

