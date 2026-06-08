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
- [x] Erzeugung der Profile für v12.1 (Stabilitätscheck). ✅
- [x] Erzeugung der Profile für v14.4 (Stabilitätscheck). ✅
- [x] Erzeugung der Profile für die aktuelle Version (v15.6). ✅
- [x] Archivierung der Rohdaten (`.jfr`, `.html`) im Verzeichnis `perf_test/profiles/`. ✅

## Phase 3: Analyse & Hotspot-Identifikation ✅
- [x] Erstellung von Differential Flame Graphs (v3.4 vs v15.6): ✅
    - [x] Verbesserung der JFR-Datentreue (Nutzung von `settings=profile`). ✅
- [x] Export von JFR-Daten in Flame-Graph-kompatible Formate (via `tools/jfr_to_collapsed.py`). ✅
- [x] Generierung der Differential Flame Graphs: ✅
    - [x] Erzeugung von Collapsed Stacks für v3.4 und v15.6 (via `tools/aggregate_collapsed_stacks.py`). ✅
    - [x] Differenzbildung der aggregierten Stacks (Diff-Graph) via `tools/diff_collapsed_stacks.py`. ✅
    - [x] Automatisierte Analyse der differentiellen Hotspots (via `tools/analyze_hotspots.py`). ✅
- [x] Quantifizierung des Overheads (v3.4 vs v15.6): ✅
    - [x] Extraktion der I/O Metriken (jdk.FileWrite) via `tools/jfr_metrics.py`. ✅
    - [x] Analyse der Allocation-Raten (jdk.ObjectAllocationInNewTLAB). ✅
    - [x] Aggregation der JFR-Metriken in `perf_test/profiles/METRICS_SUMMARY.md`. ✅
    - [x] Vergleich der Jackson Fallback vs. Fast-Path Serialisierung via CPU-Samples (Automatisierung via `tools/analyze_hotspots.py`). ✅
    - [x] Messung des Validierungs-Overheads in BCOCA/GOCA (Automatisierung via `tools/analyze_hotspots.py`). ✅
    - [x] Bestimmung des Thread-Orchestrierungs-Overheads bei kleinen Dateien (Automatisierung via `tools/analyze_hotspots.py`). ✅
- [x] Identifikation von "Infrastruktur-Drift" (JVM-Interaktionen, GC): ✅
    - [x] Vergleich der GC-Pause-Zeiten (Young/Old Generation). ✅
    - [x] Analyse der Safepoint-Statistiken. ✅

## Phase 4: Integration & Documentation ✅
- [x] Zusammenfassung der Erkenntnisse in `PROFILE_PERF_TEST.md`:
    - [x] Dokumentation der initialen Allocation-Metriken (Trend v3.4 -> v15.6). ✅
    - [x] Visualisierung der Hotspots aus Flame Graphs (Textuelle Analyse). ✅
- [x] Ableitung von konkreten Optimierungstickets aus den Profiling-Daten. ✅
- [x] Integration der Profiling-Schritte in den CI-Workflow (via `tools/perf_audit.sh`). ✅
- [x] Abschlussbericht zur 10x10 Performance-Stabilisierung (in `PROFILE_PERF_TEST.md`). ✅

## Phase 5: Automated Performance Guarding ✅
- [x] Implementierung automatisierter Regressions-Checks für Key-Metriken (Allocation, GC). ✅
- [x] Integration von Schwellenwert-Warnungen in den `perf_audit.sh` Workflow. ✅
- [x] Erweiterung der Hotspot-Analyse zur Erkennung von "Area-Drift" (prozentuale Verschiebungen). ✅

## Phase 6: Messungen mit grösseren Dateien ✅
- [x] Auswahl repräsentativer Dateien aus `test/async/` (~100KB Bereich) in `perf_test_large/`. ✅
- [x] Erstellung eines spezialisierten Benchmark-Skripts `tools/benchmark_large.sh`. ✅
- [x] Durchführung der Benchmarks für Baseline (v3.4) und aktuelle Version. ✅
- [x] Dokumentation der Ergebnisse in `PROFILE_PERF_TEST.md`. ✅

## Phase 7: Verfeinerte Analyse & Allokations-Tracking ✅
- [x] Erweiterung von `tools/jfr_to_collapsed.py` um gewichtete Aggregation (z.B. nach Allokationsgröße). ✅
- [x] Automatisierung der Allokations-Hotspot-Analyse in `perf_audit.sh`. ✅
- [x] Erzeugung von Differential Allocation Flame Graphs (v3.4 vs v15.6) zur Identifikation des 6% Drifts. ✅
- [ ] Optimierung der Datenerhebung für extrem schnelle Läufe (Erhöhung der Sampling-Frequenz oder Iterationen).

## Phase 8: Erstelle einen umfassenden Bericht `PROFILE_PERF_AUDIT.md`
- [ ] Integriere graphische Darstellungen der Flame Graphs.
- [ ] Benenne die Verbesserungen / Verschlechterungen der in der Software.
- [ ] Benenne das mögliche Verbesserungspotential in der Software.
