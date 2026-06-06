# Roadmap: Externe Instrumentierung (Profiling)

Diese Roadmap beschreibt die schrittweise Umsetzung der externen Instrumentierung zur Performance-Analyse des Alpheus CLI JAR, basierend auf dem `EXTERNAL_INSTRUMENTATION_CONCEPT.md`.

## Phase 1: Infrastruktur & Tooling ✅
- [x] Erstellung des Messkonzepts (`EXTERNAL_INSTRUMENTATION_CONCEPT.md`). ✅
- [x] Bereitstellung von `async-profiler` in der Testumgebung (Voraussetzung: installierter Agent). ✅
- [x] Erweiterung von `tools/benchmark_10x10.sh` um Support für den `-agentpath` Parameter. ✅
- [x] Implementierung eines Schalters zur automatischen JFR-Aufzeichnung in den Benchmarks. ✅

## Phase 2: Datenerhebung & Profiling ✅
- [x] Erzeugung der Performance-Baseline für v3.4 (CPU & Allocation Profile). ✅
- [x] Erzeugung der Profile für v7.0 (Referenz für ersten Performance-Drop). ✅
- [x] Erzeugung der Profile für die aktuelle Version (v15.6). ✅
- [x] Archivierung der Rohdaten (`.jfr`, `.html`) im Verzeichnis `perf_test/profiles/`. ✅

## Phase 3: Analyse & Hotspot-Identifikation 🚧
- [ ] Erstellung von Differential Flame Graphs (v3.4 vs v15.6). ⌛
- [ ] Quantifizierung des Overheads (v3.4 vs v15.6):
    - [x] Extraktion der I/O Metriken (jdk.FileWrite) via `tools/jfr_metrics.py`. ✅
    - [x] Analyse der Allocation-Raten (jdk.ObjectAllocationInNewTLAB). ✅
    - [x] Aggregation der JFR-Metriken in `perf_test/profiles/METRICS_SUMMARY.md`. ✅
    - [ ] Vergleich der Jackson Fallback vs. Fast-Path Serialisierung via CPU-Samples. ⌛
    - [ ] Messung des Validierungs-Overheads in BCOCA/GOCA. ⌛
    - [ ] Bestimmung des Thread-Orchestrierungs-Overheads bei kleinen Dateien. ⌛
- [ ] Identifikation von "Infrastruktur-Drift" (JVM-Interaktionen, GC). ⌛

## Phase 4: Integration & Dokumentation ⌛
- [ ] Zusammenfassung der Erkenntnisse in `PROFILE_PERF_TEST.md`. ⌛
- [ ] Ableitung von konkreten Optimierungstickets aus den Profiling-Daten. ⌛
- [ ] Integration der Profiling-Schritte in den CI-Workflow für Performance-Regressionen. ⌛
- [ ] Abschlussbericht zur 10x10 Performance-Stabilisierung. ⌛
