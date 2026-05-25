# Decoupling AFP Parser from XML Generation - Roadmap

This roadmap outlines the serial implementation plan for decoupling the parser from XML generation.

## Progress Overview

| Phase | Description | Status |
| :--- | :--- | :---: |
| 1 | Abstraction Layer (Core Interface) | ⏳ |
| 2 | Refactoring Output Modules (XML) | ⏳ |
| 3 | Integration (Orchestrators & CLI) | ⏳ |
| 4 | Performance Validation & Regression | ⏳ |
| 5 | PDF Generator Foundation | ⏳ |

---

## Phase 1: Abstraction Layer (Core Interface) ⏳
**Goal:** Define the contracts for handlers and factories.

- ⏳ **Define `StructuredFieldHandler`:** Create the interface in `com.mgz.afp.base.handler`.
- ⏳ **Define `HandlerFactory`:** Create the interface for handler instantiation.
- ⏳ **Create `XmlHandlerFactory`:** A concrete implementation that encapsulates the logic for choosing between Streaming and Jackson XML writers.

## Phase 2: Refactoring Output Modules (XML) ⏳
**Goal:** Adapt existing XML writers to the new abstraction.

- ⏳ **Update `AfpStreamingXmlWriter`:** Implement `StructuredFieldHandler`.
- ⏳ **Update `AfpJacksonXmlWriter`:** Implement `StructuredFieldHandler`.
- ⏳ **Unify XML Writing Logic:** Ensure both writers use the same method signature for field processing.

## Phase 3: Integration (Orchestrators & CLI) ⏳
**Goal:** Update the parser's consumers to use the abstraction.

- ⏳ **Refactor `ParallelAfpConverter`:**
    - ⏳ Replace `useJackson` and `xpathExpression` flags with a `HandlerFactory` instance.
    - ⏳ Update `PageConversionTask` to use the factory.
- ⏳ **Refactor `Afp2Xml` CLI:**
    - ⏳ Update the `convertToXml` method to instantiate an `XmlHandlerFactory`.
    - ⏳ Adapt the sequential parsing loop to use the `StructuredFieldHandler` interface.

## Phase 4: Performance Validation & Regression ⏳
**Goal:** Ensure zero performance loss and bit-for-bit XML equality.

- ⏳ **Equality Check:** Run the `Afp2Xml` CLI on a set of reference AFP files and compare the output against previous versions to ensure no regressions in XML format.
- ⏳ **Performance Benchmarking:** Run `PerformanceRegressionTest` and manual benchmarks using the `-m` (measure) flag. Compare results with the "Re-measured" baselines in `PERFORMANCE_CONCEPT.md`.
- ⏳ **Concurrency Testing:** Verify that parallel conversion remains thread-safe and efficient with the new handler-per-thread model.

## Phase 5: PDF Generator Foundation ⏳
**Goal:** Prepare for high-performance PDF generation.

- ⏳ **Stub `PdfHandler`:** Create a skeleton implementation for performance testing (parsing without output).
- ⏳ **Benchmark PDF vs XML:** Measure the throughput of the parsing stage alone to establish a ceiling for PDF generation speed.
