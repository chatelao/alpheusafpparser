# XML Indentation Roadmap

This document outlines the implementation plan for making XML indentation optional in the Alpheus AFP Parser.

## Status Summary
- **Phase 1: CLI and Factory Updates**: ✅ Complete
- **Phase 2: Writer Implementation**: ✅ Complete
- **Phase 3: Verification**: ✅ Complete

---

## Phase 1: CLI and Factory Updates ✅
Update the CLI and factory layers to support the new indentation flag.

- ✅ **CLI Argument Handling**: Add `-i` and `--indent` flags to `Afp2Xml`.
- ✅ **Usage Documentation**: Update `printUsage` in `Afp2Xml` to include the new flags.
- ✅ **Factory Integration**: Update `XmlHandlerFactory` to accept and propagate the `indent` flag.

## Phase 2: Writer Implementation ✅
Modify the XML writer to conditionally apply indentation.

- ✅ **AfpJacksonXmlWriter Refactoring**:
    - Add `indent` boolean field.
    - Guard manual `writeRaw("\n")` calls.
    - Guard `XmlIndenter.writeIndent` and `XmlIndenter.writePureIndent` calls.
- ✅ **Fragment Support**: Ensure indentation logic correctly handles fragment mode.

## Phase 3: Verification ✅
Ensure the new functionality works as expected and doesn't cause regressions.

- ✅ **Functional Testing**: Verify that output is compact by default.
- ✅ **Functional Testing**: Verify that `-i` or `--indent` produces formatted XML.
- ✅ **Regression Testing**: Run the existing test suite (`./gradlew test`).
- ✅ **Performance Verification**: Measured performance gains using `IndentationPerformanceTest`.
- ✅ **Optimization**:
    - `XmlIndenter` optimized to use `writeRawBytes` with pre-allocated `byte[]` arrays, eliminating `String` allocations during indentation.
    - `AfpJacksonXmlWriter` refactored to use `int` levels instead of `String` indentation internally, reducing overhead even when indentation is disabled.
