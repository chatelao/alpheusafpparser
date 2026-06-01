# Aalto to Woodstox Migration Roadmap

This document outlines the phased plan to migrate the StAX implementation from Aalto XML to Woodstox, as originally detailed in the migration guide.

## Status Summary
- **Phase 1: Dependency Migration**: ⏳ Pending
- **Phase 2: Core Provider Refactoring**: ⏳ Pending
- **Phase 3: Fast-Path Writer Refactoring**: ⏳ Pending
- **Phase 4: Configuration & Validation**: ⏳ Pending
- **Phase 5: Verification & Benchmarking**: ⏳ Pending

---

## Phase 1: Dependency Migration ⏳
Update the project's build configuration to swap the StAX backend.

- ⏳ **Gradle Update**: Replace Aalto with Woodstox in `build.gradle.kts`.
    - Change `implementation("com.fasterxml:aalto-xml:1.3.2")` to `implementation("com.fasterxml.woodstox:woodstox-core:7.0.0")`.

## Phase 2: Core Provider Refactoring ⏳
Update the Jackson provider to use Woodstox factories.

- ⏳ **JacksonXmlMapperProvider.java**:
    - Update imports from `com.fasterxml.aalto.stax.*` to `com.ctc.wstx.stax.*`.
    - Replace `InputFactoryImpl` with `WstxInputFactory`.
    - Replace `OutputFactoryImpl` with `WstxOutputFactory`.

## Phase 3: Fast-Path Writer Refactoring ⏳
Update the high-performance manual StAX writer.

- ⏳ **AfpJacksonXmlWriter.java**:
    - Update the static `XMLOutputFactory` initialization to use `WstxOutputFactory`.

## Phase 4: Configuration & Validation ⏳
Ensure structural validation settings are compatible.

- ⏳ **Property Verification**: Verify that Woodstox supports the `org.codehaus.stax2.validation.checkStructure` property used in `AfpJacksonXmlWriter`.
- ⏳ **Alternative Configuration**: Implement alternative structural checks if necessary for Woodstox.

## Phase 5: Verification & Benchmarking ⏳
Validate the migration against the test suite and evaluate performance impacts.

- ⏳ **Regression Testing**: Run the full suite (`Afp2XmlGoldStandardTest`, `JacksonSerializationTest`) to ensure XML output remains consistent.
- ⏳ **Performance Benchmarking**: Use `Afp2XmlBenchmarkTest` to measure the throughput impact.
    - **Note**: A slight decrease in throughput is expected as Woodstox prioritizes validation over raw speed.
- ⏳ **Async Support Impact Assessment**: Evaluate the impact on the asynchronous I/O roadmap, as Woodstox does not support non-blocking parsing.

---

## Migration Considerations
1. **No Async Support**: Unlike Aalto, Woodstox does not support non-blocking (async) parsing. This may impact future architectural goals described in `STAX2_ROADMAP.md`.
2. **Standardization**: Woodstox is often preferred in enterprise environments for its strict adherence to StAX/StAX2 standards and robust validation.
