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
- [ ] Verify that `javadocJar` and `sourcesJar` are correctly generated and included.

## Phase 4: Cleanup and Finalization
- [x] Remove `.travis.yml` from the repository.
- [x] Update `README.md` to replace the Travis CI build badge with the GitHub Actions badge.
- [ ] Verify the entire pipeline by running a test pull request.
