# Konzept: Messung externer Instrumentierung für den Alpheus CLI JAR

Dieses Dokument beschreibt das Konzept zur Messung der Performance des Alpheus AFP-zu-XML Parsers mittels externer Instrumentierung (Java Agents). Ziel ist es, basierend auf dem `10x10.md` Benchmark-Szenario detaillierte Einblicke in Zeitgewinne und -verluste in spezifischen Applikationsbereichen zu gewinnen.

## 1. Zielsetzung
Die rein zeitbasierte Messung in `10x10.md` liefert zwar Gesamtlaufzeiten, lässt aber keine Rückschlüsse darauf zu, *warum* neuere Versionen (v7.0+) langsamer sind oder wo genau Optimierungen (z.B. Fast-Paths) gegriffen haben. Die externe Instrumentierung soll:
- Den Overhead des Frameworks (Jackson, Logging, Thread-Orchestrierung) isolieren.
- Die Effizienz der manuellen StAX Fast-Paths gegenüber dem Jackson-Fallback quantifizieren.
- Hotspots in der EBCDIC-zu-UTF8 Konvertierung und XML-Sanitierung identifizieren.

## 2. Werkzeuge
Zur Messung werden Werkzeuge eingesetzt, die keine Code-Änderungen erfordern und direkt beim Aufruf des `.jar` eingebunden werden können:

- **async-profiler**: Ein Low-Overhead-Profiler für Java, der Stack-Traces sammelt, ohne das Safe-Point-Bias-Problem herkömmlicher Profiler.
  - *Vorteil*: Erzeugt Flame Graphs und unterstützt Differential Flame Graphs (Diff Graphs).
- **Java Flight Recorder (JFR)**: Ein in der JVM integriertes Tool zur Aufzeichnung von Events.
  - *Vorteil*: Extrem detaillierte Metriken zu GC, Allocation-Raten und I/O.

## 3. Detailbereiche der Applikation (Untersuchungsfokus)

Um Zeitgewinne und -verluste zu lokalisieren, werden folgende Bereiche definiert:

| Bereich | Betroffene Komponenten | Erwartete Erkenntnis |
| :--- | :--- | :--- |
| **I/O & Scanning** | `AFPScanner`, `Mmap` | Effizienz der Boundary-Erkennung und des Dateizugriffs. |
| **Field Parsing** | `AFPParser`, `SFTypeID` | Overhead der SF-Identifikation und Feld-Instanziierung. |
| **Fast-Path Serialization** | `AfpJacksonXmlWriter` (manuelle Methoden) | Zeitanteil der optimierten StAX-Schreibvorgänge. |
| **Jackson Fallback** | `XmlMapper`, Reflection | Kosten der generischen Serialisierung für nicht-optimierte Felder. |
| **Encoding & Sanitization** | `EbcdicToUtf8`, `SanitizingXMLStreamWriter` | Overhead der Zeichenkonvertierung und XML-Validierung. |
| **Parallel Overhead** | `ParallelAfpConverter`, ThreadPools | Kosten der Thread-Orchestrierung bei kleinen Dateien (v3.4 vs v15.4). |

## 4. Methodik & Durchführung

### 4.1. Baseline-Erstellung (v3.4)
Da v3.4 im `10x10.md` Benchmark als eine der schnellsten Versionen identifiziert wurde, dient sie als Referenz.
```bash
# Beispielaufruf mit async-profiler
java -agentpath:/path/to/libasyncProfiler.so=start,event=cpu,file=profile_v3.4.html \
     -jar build/libs/alpheus-v3.4.jar -p -c -d perf_test/ output/
```

### 4.2. Messung der Zielversion (v15.4)
Wiederholung der Messung mit der aktuellen Version unter identischen Bedingungen.
```bash
java -agentpath:/path/to/libasyncProfiler.so=start,event=cpu,file=profile_v15.4.html \
     -jar build/libs/alpheus-v15.4.jar -p -c -d perf_test/ output/
```

### 4.3. Differential-Analyse
Verwendung der Profiler-Daten zur Erstellung eines Diff-Flame-Graphs:
- **Rote Bereiche**: Hier verbringt v15.4 mehr Zeit als v3.4 (Verlust).
- **Blaue Bereiche**: Hier ist v15.4 schneller als v3.4 (Gewinn).

### 4.4. Integration in den 10x10 Benchmark
Der bestehende `benchmark_10x10.sh` wird um eine Profiling-Option erweitert, die automatisiert für ausgewählte Versionen Profile erstellt und im Ordner `perf_test/profiles/` ablegt.

## 5. Auswertung der Detailbereiche

Die Analyse konzentriert sich auf die Verschiebung der CPU-Zyklen:
1. **Infrastruktur-Drift**: Hat sich der Anteil von `java.util.concurrent` durch die neue Orchestrierung erhöht?
2. **Logic-Complexity**: Verbraucht die Validierung in `BDD_BarCodeDataDescriptor` signifikant Zeit im Vergleich zur alten, weniger validierenden Version?
3. **Serialization-Switch**: Wie viel Zeit wurde durch den Wechsel von Jackson-Reflection auf manuelle StAX-Methoden in `AfpJacksonXmlWriter` gewonnen?

## 6. Nächste Schritte
1. Erweiterung des `tools/benchmark_10x10.sh` Skripts um den `-agentpath` Support.
2. Durchführung einer Messreihe für v3.4, v7.0 und v15.4.
3. Dokumentation der Ergebnisse in `PROFILE_PERF_TEST.md`.
