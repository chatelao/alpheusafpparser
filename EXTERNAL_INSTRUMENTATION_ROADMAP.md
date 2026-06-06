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
- [ ] Erstellung von Differential Flame Graphs (v3.4 vs v15.6):
    - [x] Verbesserung der JFR-Datentreue (Nutzung von `settings=profile`). ✅
- [x] Export von JFR-Daten in Flame-Graph-kompatible Formate (via `tools/jfr_to_collapsed.py`). ✅
- [ ] Generierung der Differential Flame Graphs:
    - [x] Erzeugung von Collapsed Stacks für v3.4 und v15.6 (via `tools/aggregate_collapsed_stacks.py`). ✅
    - [x] Differenzbildung der aggregierten Stacks (Diff-Graph) via `tools/diff_collapsed_stacks.py`. ✅
- [ ] Quantifizierung des Overheads (v3.4 vs v15.6):
    - [x] Extraktion der I/O Metriken (jdk.FileWrite) via `tools/jfr_metrics.py`. ✅
    - [x] Analyse der Allocation-Raten (jdk.ObjectAllocationInNewTLAB). ✅
    - [x] Aggregation der JFR-Metriken in `perf_test/profiles/METRICS_SUMMARY.md`. ✅
    - [x] Vergleich der Jackson Fallback vs. Fast-Path Serialisierung via CPU-Samples (Automatisierung via `tools/analyze_hotspots.py`). ✅
    - [ ] Messung des Validierungs-Overheads in BCOCA/GOCA. ⌛
    - [ ] Bestimmung des Thread-Orchestrierungs-Overheads bei kleinen Dateien. ⌛
- [ ] Identifikation von "Infrastruktur-Drift" (JVM-Interaktionen, GC):
    - [x] Vergleich der GC-Pause-Zeiten (Young/Old Generation). ✅
    - [x] Analyse der Safepoint-Statistiken. ✅

## Phase 4: Integration & Documentation 🚧
- [ ] Zusammenfassung der Erkenntnisse in `PROFILE_PERF_TEST.md`:
    - [x] Dokumentation der initialen Allocation-Metriken (Trend v3.4 -> v15.6). ✅
    - [ ] Visualisierung der Hotspots aus Flame Graphs. ⌛
- [ ] Ableitung von konkreten Optimierungstickets aus den Profiling-Daten. ⌛
- [ ] Integration der Profiling-Schritte in den CI-Workflow für Performance-Regressionen. ⌛
- [ ] Abschlussbericht zur 10x10 Performance-Stabilisierung. ⌛
