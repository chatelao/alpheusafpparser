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

## Goals
- ✅ Replace Travis CI with GitHub Actions for automated building and testing.
- ✅ Maintain compatibility with Java 8 for the core library.
- ✅ Ensure all artifacts (JARs, POM) are correctly generated.
- 🚧 Achieve 100% AFP specification coverage (MO:DCA, PTOCA, GOCA, etc.).
- 🚧 Implement robust XML extraction for all human-readable text.

---

## Phase 1: Setup GitHub Actions Workflow
- ✅ Create `.github/workflows/ci.yml`.
- ✅ Define the workflow to trigger on `push` and `pull_request` to the `master` branch.
- ✅ Set up a job running on `ubuntu-latest`.

## Phase 2: Build and Test Configuration
- ✅ Configure Java 8 setup using `actions/setup-java`.
- ✅ Grant execution permissions to `./gradlew`.
- ✅ Implement a step to run `./gradlew build` and `./gradlew test`.
- ✅ Ensure `checkstyle` runs as part of the build.

## Phase 3: Artifact Management
- ✅ Configure steps to upload build artifacts (JARs) using `actions/upload-artifact`.
- ✅ Verify that `javadocJar` and `sourcesJar` are correctly generated and included.

## Phase 4: Cleanup and Finalization
- ✅ Remove `.travis.yml` from the repository.
- ✅ Update `README.md` to replace the Travis CI build badge with the GitHub Actions badge.
- ✅ Verify the entire pipeline.
    - ✅ Manual workflow syntax and consistency check.
    - ✅ Final verification via automated build.

## Phase 5: CI/CD Optimizations
- ✅ Enable Gradle caching in GitHub Actions.
- ✅ Add `workflow_dispatch` for manual workflow triggers.
- ✅ Consolidate build and test steps.
- ✅ Implement version-agnostic artifact verification.
- ✅ Add Gradle Wrapper validation.

## Phase 6a: Testing and Quality Assurance (i)
- ⏳ Restore and expand the test suite.
    - ✅ Provide a set of sample AFP files in `src/test/resources/afp` to enable `AFPParserTest`.
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
- 🚧 Complete payload parsing for all identified "shallow" Structured Fields.
    - ✅ Identify all Structured Fields currently using "shallow" implementations (see `SHALLOW_FIELDS_REPORT.md`).
    - 🚧 Implement full `decodeAFP` and `writeAFP` for identified shallow fields:
        - ✅ MO:DCA Object Containers and Image Data (BII, EII, IRD, EIM, EOC, OCD).
        - ⏳ MO:DCA Overlay and Page Segment.
            - ⏳ End Overlay (EMO).
            - ⏳ End Page Segment (EPS).
        - ⏳ MO:DCA Other structures.
            - ⏳ End Object Environment Group (EOG).
            - ⏳ End Print File (EPF).
            - ⏳ End Resource (ERS).
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
    - ⏳ Implement full payload parsing for missing IOCA segments and GOCA drawing orders.
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
