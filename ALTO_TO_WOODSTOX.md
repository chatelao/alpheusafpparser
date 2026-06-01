# Migration von Aalto XML zu Woodstox

Dieses Dokument beschreibt die notwendigen Schritte, um die StAX-Implementierung von Aalto XML auf Woodstox umzustellen.

## 1. Abhängigkeiten anpassen
In der Datei `build.gradle.kts` muss die Aalto-Dependency durch Woodstox ersetzt werden.

**Vorher:**
```kotlin
implementation("com.fasterxml:aalto-xml:1.3.2")
```

**Nachher:**
```kotlin
implementation("com.fasterxml.woodstox:woodstox-core:7.0.0")
```

## 2. Code-Änderungen
Die Aalto-spezifischen Factory-Implementierungen müssen durch Woodstox-Äquivalente ersetzt werden.

### JacksonXmlMapperProvider.java
Die Importe und die Initialisierung des `XmlMapper` müssen angepasst werden.

**Vorher:**
```java
import com.fasterxml.aalto.stax.InputFactoryImpl;
import com.fasterxml.aalto.stax.OutputFactoryImpl;
// ...
XML_MAPPER = XmlMapper.builder(new XmlFactory(new InputFactoryImpl(), new OutputFactoryImpl()))
```

**Nachher:**
```java
import com.ctc.wstx.stax.WstxInputFactory;
import com.ctc.wstx.stax.WstxOutputFactory;
// ...
XML_MAPPER = XmlMapper.builder(new XmlFactory(new WstxInputFactory(), new WstxOutputFactory()))
```

### AfpJacksonXmlWriter.java
Die Initialisierung der `XMLOutputFactory` muss angepasst werden.

**Vorher:**
```java
XOF = new com.fasterxml.aalto.stax.OutputFactoryImpl();
```

**Nachher:**
```java
XOF = new com.ctc.wstx.stax.WstxOutputFactory();
```

## 3. Konfigurations-Eigenschaften
In `AfpJacksonXmlWriter.java` wird derzeit die Eigenschaft `org.codehaus.stax2.validation.checkStructure` gesetzt:

```java
XOF.setProperty("org.codehaus.stax2.validation.checkStructure", false);
```

Es muss verifiziert werden, ob Woodstox diese Eigenschaft unterstützt oder ob eine alternative Konfiguration notwendig ist, um die Strukturprüfung in bestimmten Modi zu deaktivieren.

## 4. Bekannte Einschränkungen
*   **Kein Async-Support:** Woodstox unterstützt im Gegensatz zu Aalto kein non-blocking Parsing. Die Roadmap für asynchrones I/O (siehe `STAX2_ROADMAP.md`) müsste bei einer dauerhaften Umstellung angepasst werden.
*   **Performance:** Es wird erwartet, dass der Durchsatz bei Massendaten-Konvertierungen leicht sinkt, da Woodstox mehr Fokus auf Validierung als auf reine Geschwindigkeit legt.

## 5. Optionale Unterstützung beider Implementierungen (-w Flag)
Sollte Woodstox nicht als vollständiger Ersatz, sondern als optionale Alternative (via CLI-Flag `-w`) implementiert werden, wären an **drei** zentralen Stellen `if-then-else`-Entscheidungen notwendig:

1.  **`Afp2Xml.java` (CLI Parsing):** In der `execute`-Methode muss ein neuer Case für `-w` bzw. `--woodstox` hinzugefügt werden, um die Wahl des Benutzers zu erfassen.
2.  **`JacksonXmlMapperProvider.java` (Mapper-Initialisierung):** Im statischen Initialisierungsblock muss bei der Erzeugung des `XmlMapper` zwischen `InputFactoryImpl/OutputFactoryImpl` (Aalto) und `WstxInputFactory/WstxOutputFactory` (Woodstox) unterschieden werden.
3.  **`AfpJacksonXmlWriter.java` (StAX-Factory):** Die Initialisierung der statischen `XOF` (XMLOutputFactory) Variable muss basierend auf dem gewählten Modus erfolgen.

**Hinweis:** Da die Fabriken in `JacksonXmlMapperProvider` und `AfpJacksonXmlWriter` derzeit als `static final` Felder beim Laden der Klasse initialisiert werden, müsste entweder sichergestellt werden, dass das Flag gesetzt wird, bevor diese Klassen geladen werden, oder die Architektur müsste von statischen Singletons auf eine instanzbasierte Konfiguration umgestellt werden (z. B. via `AFPParserConfiguration`).
