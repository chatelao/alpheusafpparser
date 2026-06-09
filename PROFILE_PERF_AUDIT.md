# Performance Audit Report: Alpheus CLI

## 1. Management Summary
Dieser Bericht fasst die Ergebnisse der umfassenden Performance-Analyse des Alpheus CLI zusammen. Ziel war es, die Performance-Regressionen zwischen der Baseline (v3.4) und der aktuellen Version (v15.6) zu quantifizieren und Ursachen zu identifizieren.

**Haupterkenntnisse:**
- Die **Thread-Allokation** ist stabil um **6,2%** gestiegen, was auf zusätzliche Validierungen und komplexere Feld-Strukturen zurückzuführen ist.
- Im **10x10 Szenario** (viele kleine Dateien) dominiert der JVM-Overhead, wobei v15.6 durch Fast-Path Serialisierung stabil bleibt.
- Im **Large-File Szenario** (20 Dateien, gesamt ~1.8 MB) wurde eine **3,4-fache Durchsatz-Regression** festgestellt, die primär durch erhöhte GC-Last und Komplexität der neuen Features getrieben wird.

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

| Metrik | 10x10 Szenario (Kleine Dateien) | Large-File Szenario (20 Dateien, gesamt ~1.8 MB) |
| :--- | :--- | :--- |
| **Dominanter Faktor** | JVM Initialisierung / Class Loading | Serialisierungslogik / GC |
| **v3.4 Avg Time** | ~20ms (pro Datei) | 712.6 ms (pro Run) |
| **v15.6 Avg Time** | ~25ms (pro Datei) | 2436.0 ms (pro Run) |
| **Regression** | ~1.25x | **~3.4x** |
| **GC Pausen (v15.6)** | ~6.96 ms | **26.84 ms** |

### Analyse:
Während die manuellen StAX Fast-Paths in v15.6 den Reflection-Overhead bei kleinen Dateien effektiv minimieren, zeigt das Large-File-Szenario, dass die kumulative Last der erweiterten MO:DCA Unterstützung (Validierungen, Triplet-Handling) den Gesamtdurchsatz bei großen Datenmengen spürbar reduziert.

**Hinweis zum Messverfahren:**
Im Large-File Szenario nutzt v15.6 den optimierten **Directory-Mode** (`-d`), bei dem die gesamte Menge von 1.8 MB in einer einzigen JVM-Instanz ohne erneuten Prozessstart verarbeitet wird. Dies isoliert die reine Verarbeitungsleistung von der JVM-Startzeit.

## 4. Hotspot-Analyse (Large-File Szenario v15.6)
Die automatisierte Hotspot-Analyse der aggregierten JFR-Profile für das Large-File Szenario zeigt die Verteilung der CPU-Zyklen über die funktionalen Bereiche:

| Bereich | Anteil (v15.6) | Beschreibung |
| :--- | :--- | :--- |
| **Jackson Fallback** | 27.52% | Generische Serialisierung via Reflection für nicht-optimierte Felder/Tripletts (siehe Liste unten). |
| **XML Library (Woodstox)** | 26.85% | Interner Overhead des StAX-Writers und Buffer-Management (siehe Details unten). |
| **Field Parsing** | 13.42% | Dekodierung der MO:DCA Feldstrukturen und Validierung. |
| **JVM & Infrastructure** | 10.07% | Class Loading, Method Handle Linking und JIT-Kompilierung. |
| **Fast-Path Serialization** | 6.71% | Optimierte, manuelle StAX-Schreibvorgänge. |
| **Thread Orchestration** | 4.03% | Overhead der parallelen Verarbeitung (ParallelAfpConverter). |
| **Encoding & Sanitization** | 2.68% | EBCDIC-zu-UTF8 Konvertierung und XML-Sanitierung. |
| **I/O & Scanning** | 1.34% | Dateizugriff und Record-Boundary Erkennung. |
| **Other** | 7.38% | Sonstige interne JVM-Funktionen und nicht zugeordnete Stacks. |

**Erkenntnis:**
Im Gegensatz zum 10x10 Szenario dominiert hier nicht die JVM-Infrastruktur, sondern die Serialisierungslogik. Über 50% der Zeit entfallen auf Jackson (Fallback) und die Woodstox XML-Bibliothek, was ein klares Optimierungspotenzial durch den Ausbau der Fast-Paths aufzeigt.

### Details zum Jackson Fallback
Der "Jackson Fallback" tritt immer dann auf, wenn für ein MO:DCA-Element kein manueller StAX-Fast-Path in `AfpJacksonXmlWriter` existiert. In diesen Fällen wird ein vorkonfigurierter `ObjectWriter` verwendet, der das Objekt via Reflection in XML transformiert.

**Liste der betroffenen (nicht-optimierten) Elemente:**
- **FOCA Structured Fields:** CFC (Coded Font Control), CFI (Coded Font Index), CPC (Code Page Control), CPD (Code Page Descriptor), CPI (CodePageIndex), FND (Font Descriptor), FNG (Font Patterns), FNI (Font Index), FNM (Font Patterns Map), FNN (Font Name Map), FNO (Font Orientation), FNP (Font Position).
- **Tripletts:** Undefined (0x00), TripletExtender (0xFF).
- **Sonstige:** Seltene GOCA Drawing Orders, IOCA Self-Defining Fields und PTOCA Control Sequences, die nicht explizit in den `if-else` Blöcken des Writers behandelt werden.

Obwohl der `JacksonXmlMapperProvider` einen `WRITER_CACHE` (ConcurrentHashMap) verwendet, um den Overhead der Mapper-Initialisierung zu reduzieren, bleibt die eigentliche Serialisierung (Reflection und Baum-Traversierung) deutlich langsamer als die direkten StAX-Aufrufe.

### Interner Overhead und Buffer-Management (Woodstox)
Die Woodstox-Bibliothek stellt die StAX-Implementierung bereit. Der Anteil von 26.85% an der CPU-Zeit resultiert aus der Zeichen-Eskalierung, dem Attribut-Handling und dem Management interner Puffer.

**Konfigurationsdetails (JacksonXmlMapperProvider):**
- **Puffergröße:** `com.ctc.wstx.outputBufferSize` ist auf **64 KB** (65536 Bytes) optimiert, um die Anzahl der Systemaufrufe bei großen AFP-Konvertierungen zu minimieren.
- **Strukturprüfung:** `org.codehaus.stax2.validation.checkStructure` ist auf `false` gesetzt, um den Overhead der XML-Validierung während des Schreibens zu eliminieren.
- **Leerraum-Optimierung:** `com.ctc.wstx.addSpaceAfterEmptyElem` ist deaktiviert (`false`), um kompaktere XML-Tags (z.B. `<NOP/>`) zu erzeugen.

**Der "ToXmlGenerator" Abstraktionslayer:**
Ein signifikanter Teil des Overheads entsteht durch die Kapselung des Woodstox-Writers in Jacksons `ToXmlGenerator`. Jeder Aufruf von `writeStartElement` oder `writeAttribute` durchläuft mehrere Delegationsstufen. In zukünftigen Versionen könnte die direkte Nutzung der Woodstox `WstxOutputFactory` (unter Umgehung von Jackson für Fast-Paths) diesen Hotspot weiter reduzieren.

## 5. Quick Wins & Optimierungen
Basierend auf der Hotspot-Identifikation wurden folgende Maßnahmen mit hohem Performance-Hebel (Quick Wins) definiert:

1.  **Vollständige Fast-Path Abdeckung (Ziel: 0% Jackson Fallback)**:
    - Implementierung der verbleibenden Tripletts und selteneren Structured Fields in `AfpJacksonXmlWriter`.
    - **Impact**: Reduzierung der CPU-Last um bis zu 25% im Large-File Szenario durch Elimination von Reflection.
2.  **Optimierung des StructuredFieldFactory (Field Parsing)**:
    - Einführung eines Branch-Free Dispatching oder Map-basierten Lookups für die SF-Identifikation.
    - **Impact**: Beschleunigung des initialen Parsings um ca. 5-8%.
3.  **Direkte Woodstox-Integration**:
    - Umgehung des `ToXmlGenerator` Abstraktionslayers von Jackson und direkte Nutzung des `WstxOutputFactory`.
    - **Impact**: Reduzierung des Woodstox-Overheads und bessere Buffer-Kontrolle.

## 6. Performance-Abnahme-Protokoll
Um die langfristige Performance-Stabilität sicherzustellen, werden folgende Kriterien für die Abnahme neuer Features und Refactorings definiert:

| Kriterium | Schwellenwert | Verifizierungstool |
| :--- | :--- | :--- |
| **Allocation Drift** | Max. +6.5% vs v3.4 | `tools/perf_audit.sh` |
| **Throughput (Large)** | Keine Regression (> 5%) | `tools/benchmark_large.sh` |
| **Area Shift** | Max. 5.0% pro Hotspot | `tools/analyze_hotspots.py` |
| **Leak-Check** | 0 überlebende Handlers | `Afp2XmlLeakTest` |

**Prozess:**
- Jede Major-Release muss ein vollständiges JFR-Profiling für das 10x10 und Large-File Szenario durchlaufen.
- Die Ergebnisse sind in `METRICS_SUMMARY.md` zu dokumentieren und gegen die Baseline (v3.4) zu validieren.
