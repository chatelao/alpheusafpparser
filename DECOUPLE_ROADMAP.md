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

## Phase 3: Integration (Orchestrators & CLI) ✅
**Goal:** Update the parser's consumers to use the abstraction.

1.  ✅ **Refactor `ParallelAfpConverter`:**
    - Replace `useJackson` and `xpathExpression` flags with a `HandlerFactory` instance.
    - Update `PageConversionTask` to use the factory.
2.  ✅ **Refactor `Afp2Xml` CLI:**
    - Update the `convertToXml` method to instantiate an `XmlHandlerFactory`.
    - Adapt the sequential parsing loop to use the `StructuredFieldHandler` interface.

## Phase 4: Performance Validation & Regression
**Goal:** Ensure zero performance loss and bit-for-bit XML equality.

1.  **Equality Check:** Run the `Afp2Xml` CLI on a set of reference AFP files and compare the output against previous versions to ensure no regressions in XML format.
2.  **Performance Benchmarking:** Run `PerformanceRegressionTest` and manual benchmarks using the `-m` (measure) flag. Compare results with the "Re-measured" baselines in `PERFORMANCE_CONCEPT.md`.
3.  **Concurrency Testing:** Verify that parallel conversion remains thread-safe and efficient with the new handler-per-thread model.

## Phase 5: PDF Generator Foundation
**Goal:** Prepare for high-performance PDF generation.

1.  ✅ **Stub `PdfHandler`:** Create a skeleton implementation for performance testing (parsing without output).
2.  ✅ **Benchmark PDF vs XML:** Measure the throughput of the parsing stage alone to establish a ceiling for PDF generation speed.
3.  **Finalize iText 9-based integration design:** Document the mapping and parallel assembly strategy in [PDF_INTEGRATION_CONCEPT.md](PDF_INTEGRATION_CONCEPT.md).
4.  **Decouple fragment assembly from XML-specific stripping:** Replace the manual XML tag stripping in `ParallelAfpConverter` with a generic assembly mechanism in `OrderedResultCollector` or via a specialized `FragmentAssembler`.
