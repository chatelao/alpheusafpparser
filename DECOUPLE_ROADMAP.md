# Decoupling AFP Parser from XML Generation - Roadmap

This roadmap outlines the serial implementation plan for decoupling the parser from XML generation.

## Phase 1: Abstraction Layer (Core Interface) ✅
**Goal:** Define the contracts for handlers and factories.

- ✅ **Define `StructuredFieldHandler`**: Create the interface in `com.mgz.afp.base.handler`.
- ✅ **Define `HandlerFactory`**: Create the interface for handler instantiation.
- ✅ **Create `XmlHandlerFactory`**: A concrete implementation that instantiates `AfpJacksonXmlWriter`.

## Phase 2: Refactoring Output Modules (XML) ✅
**Goal:** Adapt the Jackson XML writer to the new abstraction.

- ✅ **Update `AfpJacksonXmlWriter`**: Implement `StructuredFieldHandler`.
- ✅ **Unify XML Writing Logic**: Ensure the writer uses the `handle(sf)` method for field processing.

## Phase 3: Integration (Orchestrators & CLI) ⏳
**Goal:** Update the parser's consumers to use the abstraction.

- ⏳ **Refactor `ParallelAfpConverter`**:
    - ⏳ **Inject `HandlerFactory`**: Replace hardcoded writer instantiation with the factory.
    - ⏳ **Update `PageConversionTask`**: Use the factory to create thread-local handlers.
- ⏳ **Refactor `Afp2Xml` CLI**:
    - ⏳ **Unify `convertToXml`**: Instantiate `XmlHandlerFactory` and use the interface.
    - ⏳ **Sequential Path Modernization**: Update the main loop to prefer `StructuredFieldHandler`.

## Phase 4: Performance Validation & Regression ⏳
**Goal:** Ensure zero performance loss and bit-for-bit XML equality.

- ⏳ **Equality Check**: Compare `Afp2Xml` output against reference baselines.
- ⏳ **Performance Benchmarking**: Validate against `PERFORMANCE_CONCEPT.md` baselines.
- ⏳ **Concurrency Verification**: Ensure thread-safety of the handler-per-thread model.

## Phase 5: PDF Generator Foundation ⏳
**Goal:** Prepare for high-performance PDF generation.

- ⏳ **iText 9 Infrastructure**:
    - ⏳ **Define `PdfHandler`**: Skeleton implementation of `StructuredFieldHandler`.
    - ⏳ **Implement `PdfHandlerFactory`**: Factory for creating PDF handlers.
- ⏳ **Core PDF Components**:
    - ⏳ **`GraphicsStateStack`**: Implementation for tracking AFP attributes.
    - ⏳ **`CoordinateTransformer`**: Pel/1440 to PDF Points mapping.
- ⏳ **Baseline Benchmarking**:
    - ⏳ **Null-Output Benchmarking**: Measure parsing-only speed.
    - ⏳ **PDF vs XML Throughput**: Establish performance targets for PDF.
- ✅ **Finalize iText 9-based integration design**: Document strategy in [PDF_INTEGRATION_CONCEPT.md](PDF_INTEGRATION_CONCEPT.md).
