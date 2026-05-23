# PTX Analysis Concept

This document outlines the strategy for adding the `--ptx-debug` flag to Alpheus AFP Parser to identify bottlenecks within PTX (Presentation Text Data) processing.

## 1. CLI Integration (`--ptx-debug` flag)

### Alternatives
- **A: Standard Flag:** Add `--ptx-debug` to `Afp2Xml` CLI, similar to `--measure`.
- **B: Environment Variable:** Use an environment variable (e.g., `ALPHEUS_PTX_DEBUG=true`) to enable statistics.
- **C: Log Level:** Trigger statistics collection when log level is set to `DEBUG` for specific packages.

### Selection
**Alternative A** is chosen for consistency with existing CLI options and ease of use for the user.

---

## 2. Configuration Propagation

### Alternatives
- **A: Static Global State:** Use a global volatile boolean in a monitor class.
- **B: AFPParserConfiguration:** Add a `ptxDebug` field to the configuration object.
- **C: ThreadLocal:** Store the debug state in a `ThreadLocal` variable.

### Selection
**Alternative B** is chosen. Propagating via `AFPParserConfiguration` is the most architecturaly sound approach in this project, as it's already used for other parser settings.

---

## 3. Monitoring Utility

### Alternatives
- **A: Extend `MnemonicPerformanceMonitor`:** Add PTX-specific fields to the existing monitor.
- **B: Dedicated `PTXPerformanceMonitor`:** Create a new utility specifically for PTX metrics (chaining, payload sizes, Control Sequence types).
- **C: Third-party Profiler Integration:** Use a library like Micrometer or JMX.

### Selection
**Alternative B** is chosen. A dedicated monitor allows for highly granular PTX statistics (e.g., average length of chained sequences, frequency of specific PTOCA functions) without cluttering the general-purpose monitor.

---

## 4. Hook Placement

### Alternatives
- **A: Bytecode Manipulation:** Use AspectJ or another AOP framework to inject hooks.
- **B: Manual Hooks in Parser/Writer:** Explicitly call the monitor from `PTOCAControlSequenceParser` and `PTX_PresentationTextData`.
- **C: Hook in `AFPParser`:** Monitor PTX only from the top-level parser.

### Selection
**Alternative B** is chosen. It provides the best balance between precision and implementation simplicity, allowing us to measure internal PTOCA parsing loops which are the suspected bottlenecks.

---

## 5. Reporting

### Alternatives
- **A: File Output:** Write statistics to a `ptx_stats.csv` or `.json` file.
- **B: Standard Output Summary:** Print a formatted summary to `System.out` at the end of execution.
- **C: Real-time Logging:** Log every PTX field's performance individually.

### Selection
**Alternative B** is chosen. It matches the behavior of the `--measure` flag, providing an immediate overview to the user after the process completes.

---

## Final Strategy Summary
We will implement a `--ptx-debug` flag in `Afp2Xml`, propagate it via `AFPParserConfiguration`, and use a dedicated `PTXPerformanceMonitor` with manual hooks in the PTOCA parsing logic to collect and report detailed statistics on PTX processing.
