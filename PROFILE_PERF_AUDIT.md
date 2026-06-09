# Performance Audit Report: Alpheus CLI

## 1. Management Summary
Dieser Bericht fasst die Ergebnisse der umfassenden Performance-Analyse des Alpheus CLI zusammen. Ziel war es, die Performance-Regressionen zwischen der Baseline (v3.4) und der aktuellen Version (v15.6) zu quantifizieren und Ursachen zu identifizieren.

**Haupterkenntnisse:**
- Die **Thread-Allokation** ist stabil um **6,2%** gestiegen, was auf zusätzliche Validierungen und komplexere Feld-Strukturen zurückzuführen ist.
- Im **10x10 Szenario** (viele kleine Dateien) dominiert der JVM-Overhead, wobei v15.6 durch Fast-Path Serialisierung stabil bleibt.
- Im **Large-File Szenario** (~100KB Dateien) wurde eine **3,4-fache Durchsatz-Regression** festgestellt, die primär durch erhöhte GC-Last und Komplexität der neuen Features getrieben wird.

## 2. Detaillierte Regressionsanalyse (v3.4 bis v15.6)

Die Analyse der Thread-Allokation über mehrere Versionen zeigt einen klaren Trend:

| Version | Thread Allocation (Main Thread) | Trend (vs v3.4) |
| :--- | :--- | :--- |
| **v3.4** | 21.8 MB | - |
| **v7.0** | 22.4 MB | +2.9% |
| **v12.1** | 23.2 MB | +6.3% |
| **v14.4** | 23.2 MB | +6.3% |
| **v15.6** | 23.2 MB | +6.2% |

Der signifikante Anstieg erfolgte zwischen v7.0 und v12.1. Seitdem ist die Allokationsrate stabil auf dem höheren Niveau.

## 3. Gegenüberstellung: 10x10 vs. Large-File Performance

Ein Vergleich der Performance-Charakteristik bei unterschiedlichen Dateigrößen offenbart grundlegende Unterschiede im Laufzeitverhalten:

| Metrik | 10x10 Szenario (Kleine Dateien) | Large-File Szenario (~100KB) |
| :--- | :--- | :--- |
| **Dominanter Faktor** | JVM Initialisierung / Class Loading | Serialisierungslogik / GC |
| **v3.4 Avg Time** | ~20ms (pro Datei) | 712.6 ms (pro Run) |
| **v15.6 Avg Time** | ~25ms (pro Datei) | 2436.0 ms (pro Run) |
| **Regression** | ~1.25x | **~3.4x** |
| **GC Pausen (v15.6)** | ~6.96 ms | **26.84 ms** |

### Analyse:
Während die manuellen StAX Fast-Paths in v15.6 den Reflection-Overhead bei kleinen Dateien effektiv minimieren, zeigt das Large-File-Szenario, dass die kumulative Last der erweiterten MO:DCA Unterstützung (Validierungen, Triplet-Handling) den Gesamtdurchsatz bei großen Datenmengen spürbar reduziert.

## 4. Hotspot-Visualisierung (Referenz)
*TBD in Phase 8.4*

## 5. Quick Wins & Optimierungen
*TBD in Phase 8.4*

## 6. Performance-Abnahme-Protokoll
*TBD in Phase 8.5*
