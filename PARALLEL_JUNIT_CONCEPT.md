# Konzept: Parallele JUnit-Tests

Dieses Dokument beschreibt die Strategien, Vor- und Nachteile sowie die empfohlenen Ansätze für die parallele Ausführung von JUnit-Tests im Alpheus AFP Parser Projekt.

## 1. Motivation
Mit über 350 Tests und dem Ziel einer hohen Performance bei der AFP-Verarbeitung ist eine effiziente Testsuite entscheidend. Parallele Tests verkürzen die Feedback-Zyklen in der Entwicklung und im CI/CD-Prozess (GitHub Actions).

## 2. Vor- und Nachteile der Parallelisierung

### Vorteile
- **Zeitersparnis**: Signifikante Reduktion der Gesamtlaufzeit der Testsuite.
- **Ressourcenauslastung**: Bessere Nutzung von Multi-Core-Prozessoren.
- **Früheres Feedback**: Entwickler erhalten schneller Rückmeldung über Regressionen.
- **Aufdeckung von Concurrency-Problemen**: Parallele Tests können implizit Race Conditions in der Anwendungslogik oder in den Tests selbst aufdecken.

### Nachteile
- **Flaky Tests**: Tests, die auf gemeinsame Ressourcen (statische Felder, Dateien, Ports) zugreifen, können instabil werden.
- **Komplexität**: Debugging von fehlgeschlagenen Tests in einer parallelen Umgebung ist schwieriger.
- **Ressourcen-Konflikte**: Erhöhter Speicherverbrauch durch parallele Instanzen.
- **Initialisierungsaufwand**: Manche Frameworks benötigen Zeit für das Setup der parallelen Infrastruktur.

## 3. Varianten der Parallelisierung

### Variante A: JUnit 5 Native Parallel Execution
Seit JUnit 5.3 gibt es eine integrierte Unterstützung für die parallele Ausführung.
- **Konfiguration**: Über `junit-platform.properties` oder System-Properties.
- **Modus**: `same_thread` vs. `concurrent` auf Klassen- und Methodenebene.
- **Vorteil**: Keine zusätzlichen Abhängigkeiten, nahtlose Integration.

### Variante B: Gradle Forking
Parallelisierung auf Ebene der JVM-Prozesse durch Gradle.
- **Konfiguration**: `maxParallelForks` im `test`-Task.
- **Vorteil**: Maximale Isolation, da jeder Fork eine eigene JVM hat. Keine Probleme mit statischen Feldern.
- **Nachteil**: Hoher Overhead beim Starten mehrerer JVMs.

### Variante C: TestNG
Alternative zu JUnit mit Fokus auf fortgeschrittene Parallelisierungsszenarien.
- **Vorteil**: Sehr feinmaschige Kontrolle über Thread-Pools und Abhängigkeiten.
- **Nachteil**: Migration der bestehenden JUnit-Tests erforderlich (hoher Aufwand).

## 4. Favorisierte Strategie für Alpheus

Der gewählte Ansatz ist eine Kombination aus **JUnit 5 Native Parallel Execution** und **Gradle Parallelism**.

### Favorit: JUnit 5 Native (Dynamic Strategy)
Wir setzen auf die native Parallelisierung von JUnit 5, da das Projekt bereits auf JUnit Jupiter migriert ist.

**Empfohlene Konfiguration (`src/test/resources/junit-platform.properties`):**
```properties
junit.jupiter.execution.parallel.enabled = true
junit.jupiter.execution.parallel.mode.default = concurrent
junit.jupiter.execution.parallel.mode.classes.default = concurrent
junit.jupiter.execution.parallel.config.strategy = dynamic
junit.jupiter.execution.parallel.config.dynamic.factor = 1
```

### Begründung
1. **Effizienz**: Geringerer Overhead im Vergleich zu Prozess-Forks.
2. **Kontrolle**: Über `@Execution(ExecutionMode.SAME_THREAD)` können einzelne problematische Testklassen von der Parallelisierung ausgeschlossen werden.
3. **Zukunftssicherheit**: Standardansatz im Java-Ökosystem.

## 5. Leitplanken für parallele Tests
- **Keine statischen Zustände**: Tests dürfen keine gemeinsamen statischen Variablen verändern.
- **Isolierte Dateisystem-Zugriffe**: Nutzung von `@TempDir` für temporäre AFP-Dateien.
- **Thread-Sicherheit**: Sicherstellen, dass die zu testenden Komponenten (insbesondere die neuen Pooling-Mechanismen) thread-safe sind.
