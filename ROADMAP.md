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
- [ ] Verify the entire pipeline.
    - [x] Manual workflow syntax and consistency check.
    - [ ] Final verification via automated build.

## Phase 5: CI/CD Optimizations
- [x] Enable Gradle caching in GitHub Actions.
- [x] Add `workflow_dispatch` for manual workflow triggers.
- [x] Consolidate build and test steps.
- [x] Implement version-agnostic artifact verification.
- [x] Add Gradle Wrapper validation.

## Phase 6a: Testing and Quality Assurance (i)
- [ ] Restore and expand the test suite.
    - [ ] Provide a set of sample AFP files in `src/test/resources/afp` to enable `AFPParserTest`.
    - [ ] Implement unit tests for each Structured Field, Triplet, and Control Sequence.
- [ ] Migrate tests from JUnit 4 to JUnit 5 (Jupiter).

## Phase 6b: Complete AFP Syntax Implementation and Coverage
- [ ] Complete payload parsing for all Structured Fields.
    - [ ] Identify all Structured Fields currently using "shallow" implementations (e.g., those only inheriting from `StructuredFieldBaseNameAndTriplets` without overriding `decodeAFP` for their specific payload).
    - [ ] Implement full `decodeAFP` and `writeAFP` for these fields according to the AFP specifications.
- [ ] Enhance GOCA and IOCA support.
    - [ ] Improve `GAD_GraphicsData` to handle all GOCA drawing orders more robustly.
    - [ ] Improve `IPD_ImagePictureData` to handle all IOCA segments, reducing "Unknown" segments.
- [ ] Ensure 100% coverage of the AFP specification as claimed in the project description.

## Phase 6c: Testing and Quality Assurance (ii)
- [ ] Integrate JaCoCo for code coverage reporting.
- [ ] Add GitHub Actions job for CodeQL analysis.
- [ ] Update Checkstyle to a modern version and use standard Google or Sun style.

## Phase 7: Modernization of Build System and Language
- [ ] Upgrade Gradle to 8.x or latest version.
- [ ] Migrate build scripts from Groovy to Kotlin DSL (`build.gradle.kts`).
- [ ] Update Java version to 21 (LTS) or latest available.
- [ ] Refactor source code to use modern Java features (records, sealed classes, pattern matching, etc.).

## Phase 8: Modernized Publishing and Distribution
- [ ] Replace the deprecated 'maven' plugin with the 'maven-publish' plugin.
- [ ] Configure automated publishing to Maven Central via GitHub Actions.
- [ ] Set up automated release notes generation using GitHub Releases.

## Phase 9: Project Documentation and Maintenance
- [ ] Set up Dependabot or Renovate for automated dependency and Gradle updates.
- [ ] Migrate project documentation to MkDocs or Docusaurus, hosted on GitHub Pages.
- [ ] Add a comprehensive contributing guide (`CONTRIBUTING.md`).
- [ ] Implement a SECURITY.md policy.
