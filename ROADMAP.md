# Roadmap: CI/CD Migration to GitHub Actions

This roadmap outlines the steps to migrate the Alpheus AFP Parser CI/CD pipeline from Travis CI to GitHub Actions.

## Goals
- Replace Travis CI with GitHub Actions for automated building and testing.
- Maintain compatibility with Java 8.
- Ensure all artifacts (JARs, POM) are correctly generated.

## Phase 1: Setup GitHub Actions Workflow
- [x] Create `.github/workflows/ci.yml`.
- [x] Define the workflow to trigger on `push` and `pull_request` to the `master` branch.
- [x] Set up a job running on `ubuntu-latest`.

## Phase 2: Build and Test Configuration
- [x] Configure Java 8 setup using `actions/setup-java`.
- [x] Grant execution permissions to `./gradlew`.
- [x] Implement a step to run `./gradlew build` and `./gradlew test`.
- [x] Ensure `checkstyle` runs as part of the build.

## Phase 3: Artifact Management
- [x] Configure steps to upload build artifacts (JARs) using `actions/upload-artifact`.
- [x] Verify that `javadocJar` and `sourcesJar` are correctly generated and included.

## Phase 4: Cleanup and Finalization
- [x] Remove `.travis.yml` from the repository.
- [x] Update `README.md` to replace the Travis CI build badge with the GitHub Actions badge.
- [x] Verify the entire pipeline.
    - [x] Manual workflow syntax and consistency check.
    - [x] Final verification via automated build.

## Phase 5: CI/CD Optimizations
- [x] Enable Gradle caching in GitHub Actions.
- [x] Add `workflow_dispatch` for manual workflow triggers.
- [x] Consolidate build and test steps.
- [x] Implement version-agnostic artifact verification.
- [x] Add Gradle Wrapper validation.

## Phase 6a: Testing and Quality Assurance (i)
- [ ] Restore and expand the test suite.
    - [x] Provide a set of sample AFP files in `src/test/resources/afp` to enable `AFPParserTest`.
    - [x] Establish a base for granular structure unit testing (round-trip verification).
    - [x] Implement round-trip unit tests for all MO:DCA Triplets.
        - [x] Mapping and Identification Triplets (e.g., FQN, Mapping Option, Comment).
        - [x] Font and Character Triplets (e.g., Font Descriptor, Character Rotation).
        - [x] Color and Presentation Triplets (e.g., Color Specification, Presentation Space Mixing).
        - [x] Measurement and Area Triplets (e.g., Measurement Units, Object Area Size, Image Resolution).
        - [x] Object and Resource Triplets (e.g., Object Classification, Resource Object Type).
        - [x] Positioning and Size Triplets (e.g., Object Offset, Object Byte Extent, Descriptor Position).
        - [x] Date and Control Triplets (e.g., Universal Date and Time Stamp, Presentation Control).
        - [x] Fidelity and Finishing Triplets (e.g., Text Fidelity, Finishing Operation).
    - [x] Implement round-trip unit tests for all PTOCA Control Sequences.
        - [x] Movement and Positioning Control Sequences (e.g., AMI, RMI, AMB, RMB).
        - [x] Appearance and Data Control Sequences (e.g., STC, SEC, TRN, RPS).
    - [ ] Implement round-trip unit tests for all Structured Fields.
        - [x] Document and Page Group Structured Fields (BDT, EDT, BPG, EPG, BNG, ENG, BDI, EDI).
        - [x] Page and Overlay Structured Fields.
            - [x] Begin/End Overlay (BMO, EMO).
            - [x] Begin/End Page Segment (BPS, EPS).
            - [x] Include Page/Overlay/Segment (IPG, IPO, IPS).
            - [x] Map Page/Overlay/Segment (MPG, MPO, MPS).
        - [x] Environment and Resource Group Structured Fields (BDG, EDG, BFM, EFM, BMM, EMM, BRG, ERG, BAG, EAG, BFG, EFG, BSG, ESG).
        - [ ] Object and Data Structured Fields.
            - [ ] Begin/End Object Container (BOC, EOC).
            - [ ] Begin/End Image/IM Image (BIM, EIM, BII, EII).
            - [ ] Include Object (IOB).
            - [ ] Object/Image Data (OCD, IRD).
- [ ] Migrate tests from JUnit 4 to JUnit 5 (Jupiter).

## Phase 6b: Complete AFP Syntax Implementation and Coverage
- [ ] Complete payload parsing for all identified "shallow" Structured Fields.
    - [x] Identify all Structured Fields currently using "shallow" implementations (see `SHALLOW_FIELDS_REPORT.md`).
    - [ ] Implement full `decodeAFP` and `writeAFP` for identified shallow fields:
        - [ ] MO:DCA Object Containers and Image Data.
            - [ ] Begin/End IM Image Object (BII, EII) and raster data (IRD).
            - [ ] End Image Object (EIM).
            - [ ] End Object Container (EOC) and data (OCD).
        - [ ] MO:DCA Overlay and Page Segment.
            - [x] End Overlay (EMO).
            - [x] End Page Segment (EPS).
        - [ ] MO:DCA Other structures.
            - [ ] End Object Environment Group (EOG).
            - [ ] End Print File (EPF).
            - [ ] End Resource (ERS).
        - [ ] FOCA Coded Font, Code Page, and Font Character Set fields.
            - [ ] Begin/End Coded Font (BCF, ECF).
            - [ ] End Code Page (ECP).
            - [ ] End Font (EFN).
            - [ ] Font Patterns (FNG) and Name Map (FNN).
        - [ ] BCOCA End Bar Code Object (EBC).
        - [ ] CMOCA Color Management Resource (CMR).
        - [ ] MO:DCA Color Attribute Table (CAT).
        - [ ] GOCA Graphics Data (GAD) and End Graphics Object (EGR).
        - [ ] Line Data related fields.
            - [ ] Begin/End Data Map Transmition Subcase (BDX, EDX).
            - [ ] Begin/End Page Map (BPM, EPM).
            - [ ] Data Map Transmition Subcase Descriptor (DXD).
            - [ ] End Data Map (EDM).
            - [ ] Invoke Data Map (IDM).
- [ ] Enhance GOCA and IOCA support.
    - [ ] Improve `GAD_GraphicsData` to handle all GOCA drawing orders more robustly.
    - [ ] Improve `IPD_ImagePictureData` to handle all IOCA segments, reducing "Unknown" segments.
- [ ] Ensure 100% coverage of the AFP specification as claimed in the project description.

## Phase 6c: Testing and Quality Assurance (ii)
- [ ] Integrate JaCoCo for code coverage reporting.
- [x] Add GitHub Actions job for CodeQL analysis.
    - [x] Create `.github/workflows/codeql.yml`.
- [ ] Update Checkstyle to a modern version and use standard Google or Sun style.

## Phase 7: Modernization of Build System and Language
- [x] Upgrade Gradle to 8.x or latest version.
- [ ] Migrate build scripts from Groovy to Kotlin DSL (`build.gradle.kts`).
- [ ] Update Java version to 21 (LTS) or latest available.
- [ ] Refactor source code to use modern Java features (records, sealed classes, pattern matching, etc.).

## Phase 8: Modernized Publishing and Distribution
- [x] Replace the deprecated 'maven' plugin with the 'maven-publish' plugin.
- [ ] Configure automated publishing to Maven Central via GitHub Actions.
- [x] Set up automated release notes and asset generation using GitHub Releases.

## Phase 9: Project Documentation and Maintenance
- [ ] Set up Dependabot or Renovate for automated dependency and Gradle updates.
- [ ] Migrate project documentation to MkDocs or Docusaurus, hosted on GitHub Pages.
- [ ] Add a comprehensive contributing guide (`CONTRIBUTING.md`).
- [ ] Implement a SECURITY.md policy.
