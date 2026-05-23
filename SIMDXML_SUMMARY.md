# Zusammenfassung: simdxml

`simdxml` ist ein SIMD-beschleunigter XML-Parser mit vollständiger XPath 1.0-Unterstützung. Er wurde entwickelt, um extreme Performance durch die Nutzung moderner CPU-Befehlssätze zu erreichen.

## Hauptmerkmale

*   **SIMD-Beschleunigung**: Nutzt SIMD-Instruktionen (Single Instruction, Multiple Data) für die schnelle Zeichenklassifizierung und das Maskieren von Anführungszeichen.
*   **Vollständige XPath 1.0-Unterstützung**: Unterstützt alle 13 Achsen, Prädikate, Funktionen, Operatoren und Unions.
*   **Flache Datenstrukturen**: Anstatt eines speicherintensiven DOM-Baums verwendet `simdxml` flache Arrays (Structural Indexing), was zu einer deutlich höheren Speichereffizienz führt (~16 Bytes pro Tag gegenüber ~35 Bytes pro DOM-Knoten).
*   **Parallele Verarbeitung**: Große Dateien werden automatisch parallel über mehrere CPU-Kerne hinweg analysiert.
*   **Speicherabbildung (mmap)**: Dateien werden per Speicherabbildung geladen, was den I/O-Overhead minimiert.

## Werkzeuge und Bibliotheken

*   **`sxq` (CLI-Tool)**: Ein schnelles Kommandozeilenwerkzeug für XML/XPath-Abfragen.
*   **Rust-Bibliothek**: `simdxml` ist als Rust-Crate verfügbar und kann einfach in andere Projekte integriert werden.

## Performance

`simdxml` zeigt seine größten Stärken bei attributlastigem XML und Multi-Gigabyte-Dateien. In Benchmarks ist es oft deutlich schneller als herkömmliche Parser wie `pugixml` oder `xmllint`:

| Benchmark | simdxml (sxq) | vs. pugixml | vs. xmllint |
| :--- | :--- | :--- | :--- |
| DBLP (5,1 GB) | 3,2s | 2,6x schneller | — |
| PubMed (195 MB) | 130ms | 1,3x schneller | 6,4x schneller |
| Attributlastig (10 MB) | 7,8ms | 1,7x schneller | 17,7x schneller |

## Architektur

Der Parser arbeitet in zwei Durchgängen:
1.  **Parse**: Scannen der XML-Bytes mit SIMD zur Erstellung der flachen Arrays (Offsets, Typen, Tiefen, Eltern).
2.  **Query**: Auswertung von XPath gegen diese Arrays unter Verwendung von CSR-Indizes und Pre/Post-Order-Nummerierung für O(1) Ahnen-Prüfungen.

## Plattformunterstützung

| Plattform | SIMD-Backend |
| :--- | :--- |
| **aarch64** (Apple Silicon, ARM) | NEON 128-bit |
| **x86_64** | AVX2 256-bit / SSE4.2 128-bit |
| **Andere** | Skalar (memchr-beschleunigt) |

## Lizenz

Das Projekt steht unter der **MIT-Lizenz** oder der **Apache-Lizenz 2.0**.
