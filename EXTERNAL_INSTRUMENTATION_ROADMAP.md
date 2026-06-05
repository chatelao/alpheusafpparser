# Roadmap: Externe Instrumentierung (Profiling)

Diese Roadmap beschreibt die schrittweise Umsetzung der externen Instrumentierung zur Performance-Analyse des Alpheus CLI JAR, basierend auf dem `EXTERNAL_INSTRUMENTATION_CONCEPT.md`.

## Phase 1: Infrastruktur & Tooling 🚧
- [x] Erstellung des Messkonzepts (`EXTERNAL_INSTRUMENTATION_CONCEPT.md`). ✅
- [ ] Bereitstellung von `async-profiler` in der Testumgebung. 🚧
- [ ] Erweiterung von `tools/benchmark_10x10.sh` um Support für den `-agentpath` Parameter. ⌛
- [ ] Implementierung eines Schalters zur automatischen JFR-Aufzeichnung in den Benchmarks. ⌛

## Phase 2: Datenerhebung & Profiling ⌛
- [ ] Erzeugung der Performance-Baseline für v3.4 (CPU & Allocation Profile). ⌛
- [ ] Erzeugung der Profile für v7.0 (Referenz für ersten Performance-Drop). ⌛
- [ ] Erzeugung der Profile für die aktuelle Version (v15.4). ⌛
- [ ] Archivierung der Rohdaten (`.jfr`, `.html`) im Verzeichnis `perf_test/profiles/`. ⌛

## Phase 3: Analyse & Hotspot-Identifikation ⌛
- [ ] Erstellung von Differential Flame Graphs (v3.4 vs v15.4). ⌛
- [ ] Quantifizierung des Overheads durch:
    - Thread-Orchestrierung in `ParallelAfpConverter`. ⌛
    - Jackson Fallback vs. Fast-Path Serialisierung. ⌛
    - Validierungslogik in BCOCA/GOCA. ⌛
- [ ] Identifikation von "Infrastruktur-Drift" (JVM-Interaktionen, GC). ⌛

## Phase 4: Integration & Dokumentation ⌛
- [ ] Zusammenfassung der Erkenntnisse in `PROFILE_PERF_TEST.md`. ⌛
- [ ] Ableitung von konkreten Optimierungstickets aus den Profiling-Daten. ⌛
- [ ] Integration der Profiling-Schritte in den CI-Workflow für Performance-Regressionen. ⌛
- [ ] Abschlussbericht zur 10x10 Performance-Stabilisierung. ⌛
