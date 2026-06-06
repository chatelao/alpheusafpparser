# Analysebericht: Externe Instrumentierung (Profiling)

Dieses Dokument fasst die Ergebnisse der externen Performance-Analyse des Alpheus CLI JAR zusammen.

## 1. Zusammenfassung der initialen Metriken (Baseline)

Basierend auf den JFR-Aufzeichnungen der Versionen v3.4, v7.0 und v15.6 wurden folgende durchschnittliche Metriken extrahiert:

| Version | Thread Allocation (Main Thread) | Trend (vs v3.4) |
| :--- | :--- | :--- |
| **v3.4** | 21.8 MB | - |
| **v7.0** | 22.4 MB | +2.7% |
| **v15.6** | 23.2 MB | +6.2% |

### Beobachtungen:
- **Speicherallokation**: Es ist ein kontinuierlicher Anstieg der Speicherallokation auf dem Main-Thread feststellbar. Von v3.4 bis v15.6 stieg die Allokationsrate um ca. 6,2%. Dies deutet auf eine erhöhte Objekt-Instanziierung hin (evtl. durch zusätzliche Validierungen oder komplexere Datenstrukturen).
- **I/O Metriken**: In den initialen Läufen wurden keine `jdk.FileWrite` Events erfasst. Dies liegt vermutlich an der Pufferung im Betriebssystem oder an der Kürze der Benchmark-Läufe (10x10).
- **Datenqualität**: Die initialen JFR-Aufzeichnungen enthielten keine CPU-Samples (`jdk.ExecutionSample`) oder TLAB-Metriken, da das Standard-Profil der JVM verwendet wurde.

## 2. Maßnahmen zur Datenverbesserung

Um detailliertere Einblicke (z.B. Flame Graphs) zu ermöglichen, wurden folgende Änderungen vorgenommen:
- **`tools/benchmark_10x10.sh`**: Der JFR-Aufruf wurde um `settings=profile` erweitert, um CPU-Sampling und TLAB-Events zu aktivieren.
- **Roadmap-Granularisierung**: Die Schritte zur Erstellung von Differential Flame Graphs wurden in `EXTERNAL_INSTRUMENTATION_ROADMAP.md` detailliert, um die technische Hürde (Export/Konvertierung) explizit abzubilden.

## 3. Nächste Schritte

1. Erneute Durchführung des Benchmarks mit dem `profile` Setting für v3.4 und v15.6.
2. Generierung von CPU-Profilen zur Identifikation der Hotspots in der Serialisierung (Jackson vs. Fast-Path).
3. Untersuchung der Ursache für den 6%igen Anstieg der Allokationsrate.
