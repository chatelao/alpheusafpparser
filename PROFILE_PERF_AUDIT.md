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

Ein Vergleich der Performance-Charakteristik bei unterschiedlichen Dateigrößen offenbart grundlegende Unterschiede im Laufzeitverhalten (Daten für XML-Export):

| Metrik | 10x10 Szenario (Kleine Dateien) | Large-File Szenario (20 Dateien, gesamt ~1.8 MB) |
| :--- | :--- | :--- |
| **Dominanter Faktor** | JVM Initialisierung / Class Loading | Serialisierungslogik / GC |
| **v3.4 Avg Time** | ~20ms (pro Datei) | 712.6 ms (pro Run) |
| **v15.6 Avg Time** | ~25ms (pro Datei) | 2436.0 ms (pro Run) |
| **Regression** | ~1.25x | **~3.4x** |
| **GC Pausen (v15.6)** | ~6.96 ms | **26.84 ms** |

### Analyse:
Während die manuellen StAX Fast-Paths in v15.6 den Reflection-Overhead bei kleinen Dateien effektiv minimieren, zeigt das Large-File-Szenario, dass die kumulative Last der erweiterten MO:DCA Unterstützung (Validierungen, Triplet-Handling) den Gesamtdurchsatz bei großen Datenmengen spürbar reduziert.

## 4. PDF Performance Charakteristik (v15.6)
Mit der Einführung des PDF-Exports via iText 9 wurden eigene Performance-Benchmarks durchgeführt, um den Overhead der PDF-Generierung im Vergleich zum XML-Export zu bewerten.

| Metrik | XML (v15.6) | PDF (v15.6) | Delta |
| :--- | :--- | :--- | :--- |
| **Avg Time (10x10)** | ~250 ms (Gesamt) | 1329.5 ms (Gesamt) | +430% |
| **Avg Time (Large)** | 2436.0 ms | 1839.5 ms | **-24.5%** |
| **Thread Alloc (Main)** | 23.2 MB | 24.0 MB | +3.4% |
| **GC Pausen (Large)** | 26.84 ms | 7.69 ms | -71.3% |

### Analyse PDF vs. XML:
- **Kleine Dateien (10x10)**: Der PDF-Export ist signifikant langsamer (+430%). Dies liegt an der massiven Initialisierungszeit der iText-Bibliothek (Fonts, Ressourcen, PDF-Strukturen) pro Prozessstart oder bei der ersten Nutzung.
- **Große Dateien (Large)**: Überraschenderweise ist der PDF-Export bei größeren Dateien **24.5% schneller** als der XML-Export. Dies ist darauf zurückzuführen, dass der PDF-Handler direkt die iText-API bedient und somit den teuren Umweg über Jacksons Reflection-Fallback und Woodstox' XML-Serialisierung vermeidet. Zudem ist die GC-Last bei der PDF-Generierung in diesem Szenario deutlich geringer.

## 5. Hotspot-Analyse (Large-File Szenario v15.6)
Die automatisierte Hotspot-Analyse der aggregierten JFR-Profile für das Large-File Szenario zeigt die Verteilung der CPU-Zyklen:

| Bereich | Anteil (XML) | Anteil (PDF) | Beschreibung (XML) |
| :--- | :--- | :--- | :--- |
| **Jackson Fallback** | 27.52% | 0.00% | Generische Serialisierung via Reflection. |
| **XML Library (Woodstox)** | 26.85% | 0.00% | Interner Overhead des StAX-Writers. |
| **PDF Generation (iText)** | 0.00% | 38.49% | iText Layout-Engine, Font-Handling und Canvas-Operations. |
| **Field Parsing** | 13.42% | 12.95% | Dekodierung der MO:DCA Feldstrukturen. |
| **JVM & Infrastructure** | 10.07% | 22.66% | Class Loading, Method Handle Linking und JIT. |
| **Fast-Path Serialization** | 6.71% | 0.00% | Optimierte StAX-Schreibvorgänge. |
| **Thread Orchestration** | 4.03% | 5.76% | Overhead der parallelen Verarbeitung. |
| **Encoding & Sanitization** | 2.68% | 1.08% | EBCDIC-Konvertierung. |
| **Other** | 7.38% | 18.71% | Sonstige interne JVM-Funktionen. |

**Erkenntnis:**
Während beim XML-Export die Serialisierungs-Bibliotheken (Jackson/Woodstox) über 50% der CPU-Zeit binden, ist beim PDF-Export die iText-Bibliothek selbst der Hauptfaktor (38.5%). Die Effizienz von iText bei großen Dateien erklärt, warum der PDF-Export den XML-Export im Durchsatz schlägt, sobald die Initialisierungshürde genommen ist.

**Hinweis zum Messverfahren:**
Im Large-File Szenario nutzt v15.6 den optimierten **Directory-Mode** (`-d`), bei dem die gesamte Menge von 1.8 MB in einer einzigen JVM-Instanz ohne erneuten Prozessstart verarbeitet wird. Dies isoliert die reine Verarbeitungsleistung von der JVM-Startzeit.

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
Ein signifikanter Teil des Overheads entsteht durch die Kapselung des Woodstox-Writers in Jacksons `ToXmlGenerator`. Jeder Aufruf von `writeStartElement` oder `writeAttribute` durchläuft mehrere Delegationsstufen. In zukünftigen Versionen könnte die direkte Nutzung der Woodstox `WstxOutputFactory` (unter Umgehung von Jackson for Fast-Paths) diesen Hotspot weiter reduzieren.

## 6. Quick Wins & Optimierungen
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

## 7. Performance-Abnahme-Protokoll
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
