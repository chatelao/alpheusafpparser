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

1.  **Equality Check (Regression Testing):**
  - ✅ **4.1.1 Identify Reference Suite:** Select a representative set of AFP files (PTOCA, GOCA, IOCA, BCOCA).
    - ⏳ **4.1.2 Archive Gold Standards:** Generate and store reference XML outputs from a known stable version.
  - ✅ **4.1.3 Automated Comparison:** Implement `Afp2XmlRegressionTest.java` for automated XML diffing.
  - ✅ **4.1.4 Mode Verification:** Verify bit-for-bit equality for both sequential and parallel (`-P`) modes.
2.  **Performance Benchmarking:**
    - ⏳ **4.2.1 Execution:** Run `PerformanceRegressionTest` to collect current throughput data.
    - ⏳ **4.2.2 Baseline Comparison:** Compare results against `PERFORMANCE_CONCEPT.md` baselines.
    - ⏳ **4.2.3 Abstraction Profiling:** Profile the handler-based execution path to identify potential overhead.
3.  **Concurrency & Stability:**
    - ⏳ **4.3.1 Stress Testing:** Run high-thread-count tests on multi-page files to ensure no race conditions.
    - ⏳ **4.3.2 Memory Stability:** Verify O(1) memory footprint during parallel conversion of 100MB+ files.

## Phase 5: PDF Generator Foundation
**Goal:** Prepare for high-performance PDF generation.

1.  ✅ **Stub `PdfHandler`:** Create a skeleton implementation for performance testing (parsing without output).
2.  ✅ **Benchmark PDF vs XML:** Measure the throughput of the parsing stage alone to establish a ceiling for PDF generation speed.
3.  ⏳ **Finalize iText 9-based integration design:** Document the mapping and parallel assembly strategy in [PDF_INTEGRATION_CONCEPT.md](PDF_INTEGRATION_CONCEPT.md).
    - ⏳ **5.3.1 Define Mapping Strategy:** Detail how PTOCA, GOCA, IOCA, and BCOCA map to iText low-level operators.
    - ⏳ **5.3.2 Design Coordinate Transformation:** Address Pel/1440 DPI to PDF User Space (Points) conversion and rotations.
    - ⏳ **5.3.3 Design Resource Handling:** Strategy for global resource caching (Fonts, Overlays) in PDF/VT.
    - ⏳ **5.3.4 Design Parallel Assembly:** ID remapping and XRef table construction for O(1) memory merging.
4.  ✅ **Decouple fragment assembly from XML-specific stripping:** Replace the manual XML tag stripping in `ParallelAfpConverter` with a generic assembly mechanism in `OrderedResultCollector` or via a specialized `FragmentAssembler`.
