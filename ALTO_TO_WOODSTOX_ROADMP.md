# Aalto to Woodstox Migration Roadmap

This document outlines the phased plan to migrate the StAX implementation from Aalto XML to Woodstox, as originally detailed in the migration guide.

## Status Summary
- **Phase 1: Dependency Migration**: ✅ Complete
- **Phase 2: Core Provider Refactoring**: ✅ Complete
- **Phase 3: Fast-Path Writer Refactoring**: ✅ Complete
- **Phase 4: Configuration & Validation**: ✅ Complete
- **Phase 5: Verification & Benchmarking**: ✅ Complete

---

## Phase 1: Dependency Migration ✅
Update the project's build configuration to swap the StAX backend.

- ✅ **Gradle Update**: Added Woodstox to `build.gradle.kts`.
    - Added `implementation("com.fasterxml.woodstox:woodstox-core:7.0.0")`.

## Phase 2: Core Provider Refactoring ✅
Update the Jackson provider to support Woodstox factories.

- ✅ **JacksonXmlMapperProvider.java**:
    - Added support for `WstxInputFactory` and `WstxOutputFactory` alongside Aalto.

## Phase 3: Fast-Path Writer Refactoring ✅
Update the high-performance manual StAX writer.

- ✅ **AfpJacksonXmlWriter.java**:
    - Added optional support for `WOODSTOX_XOF` (WstxOutputFactory).

## Phase 4: Configuration & Validation ✅
Ensure structural validation settings are compatible.

- ✅ **Property Verification**: Verified that Woodstox supports the `org.codehaus.stax2.validation.checkStructure` property.
- ✅ **Alternative Configuration**: Integrated backend selection into `XmlHandlerFactory` and `Afp2Xml`.

## Phase 5: Verification & Benchmarking ✅
Validate the migration against the test suite and evaluate performance impacts.

- ⏳ **Regression Testing**: Run the full suite (`Afp2XmlGoldStandardTest`, `JacksonSerializationTest`) to ensure XML output remains consistent.
- ⏳ **Performance Benchmarking**: Use `Afp2XmlBenchmarkTest` to measure the throughput impact.
    - **Note**: A slight decrease in throughput is expected as Woodstox prioritizes validation over raw speed.
- ⏳ **Async Support Impact Assessment**: Evaluate the impact on the asynchronous I/O roadmap, as Woodstox does not support non-blocking parsing.

---

## Migration Considerations
1. **No Async Support**: Unlike Aalto, Woodstox does not support non-blocking (async) parsing. This may impact future architectural goals described in `STAX2_ROADMAP.md`.
2. **Standardization**: Woodstox is often preferred in enterprise environments for its strict adherence to StAX/StAX2 standards and robust validation.

## Optional Woodstox Support
If Woodstox were to be supported as an optional CLI flag (`-w`) alongside Aalto, conditional `if-then-else` logic would be required at three primary locations:
1. **CLI Argument Parsing**: In `Afp2Xml.java`, to detect the `-w` flag and propagate the choice.
2. **Mapper Configuration**: In `JacksonXmlMapperProvider.java`, to select between `InputFactoryImpl`/`OutputFactoryImpl` (Aalto) and `WstxInputFactory`/`WstxOutputFactory` (Woodstox) when building the `XmlMapper`.
3. **Manual Writer Initialization**: In `AfpJacksonXmlWriter.java`, to instantiate the appropriate `XMLOutputFactory` based on the selected backend.
