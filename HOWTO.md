# HOWTO: Use Alpheus AFP Parser

This guide provides a detailed overview of how to use the Alpheus AFP Parser library to read and process IBM AFP (Advanced Function Presentation) files.

## Installation

### Maven

Add the following dependency to your `pom.xml`:

```xml
<dependency>
  <groupId>com.github.chatelao</groupId>
  <artifactId>alpheusafpparser</artifactId>
  <version>${version}</version>
</dependency>
```

### Gradle

Add the following to your `build.gradle`:

```gradle
dependencies {
    compile 'com.github.chatelao:alpheusafpparser:${version}'
}
```

## Basic Usage

The core of the library revolves around the `AFPParser` and `AFPParserConfiguration` classes.

### Reading an AFP File

To parse an AFP file, you need to create an `AFPParserConfiguration`, set the input stream, and then use the `AFPParser` to iterate through the structured fields.

```java
import com.mgz.afp.base.StructuredField;
import com.mgz.afp.parser.AFPParser;
import com.mgz.afp.parser.AFPParserConfiguration;
import java.io.FileInputStream;

public class SimpleParser {
    public static void main(String[] args) throws Exception {
        AFPParserConfiguration config = new AFPParserConfiguration();
        try (FileInputStream fis = new FileInputStream("example.afp")) {
            config.setInputStream(fis);
            AFPParser parser = new AFPParser(config);

            StructuredField sf;
            while ((sf = parser.parseNextSF()) != null) {
                // Process the structured field
                System.out.println("Found Structured Field: " + sf.getStructuredFieldIntroducer().getSFTypeID());
                System.out.println("Length: " + sf.getStructuredFieldIntroducer().getSFLength());
            }
        }
    }
}
```

## Configuration Options

`AFPParserConfiguration` allows you to customize the parser's behavior.

### Character Set

Set the charset used to decode text within the AFP stream (e.g., in TLEs or NOPs). The default is `CP500`.

```java
config.setAfpCharSet(Charset.forName("Cp1141")); // Example: German with Euro
```

### Buffer Size

Set the internal buffer size for reading the input stream (in bytes). Default is 100KB.

```java
config.setBufferSize(512 * 1024); // 512KB
```

### Shallow Parsing

If memory is a concern, you can enable shallow parsing. In this mode, the parser only reads the `StructuredFieldIntroducer`. The full payload is only loaded when `AFPParser.reload(sf)` is called.

```java
config.setBuildShallow(true);
```

### Raw Parsing (BaseData mode)

If you are dealing with non-compliant AFP files or just need the raw data, you can set the parser to treat all fields as raw data blocks.

```java
config.setParseToStructuredFieldsBaseData(true);
```

## XML Export

Alpheus provides a utility to export individual structured fields to XML.

```java
import com.mgz.xml.AFP2XMLWriter;
// ...
AFP2XMLWriter.writeXML(System.out, sf, config);
```

## Command Line Interface (CLI)

The Alpheus AFP Parser includes a Command Line Interface (CLI) tool that allows you to easily convert IBM AFP files into a structured XML format. This is particularly useful for inspecting the contents of AFP files without writing any code.

### Obtaining the CLI Tool

The CLI tool is distributed as a "fat JAR" (a standalone executable JAR containing all necessary dependencies). You can obtain it in two ways:

1.  **Download from GitHub Releases:** Navigate to the [Releases](https://github.com/chatelao/alpheusafpparser/releases) page and download the `alpheus-afp-parser-cli-<version>.jar` asset from the latest release.
2.  **Build from Source:** If you have the source code, you can build the CLI JAR using Gradle:
    ```bash
    ./gradlew shadowJar
    ```
    The generated JAR will be located in `build/libs/alpheus-afp-parser-cli-<version>.jar`.

### Prerequisites

You must have Java 8 or higher installed on your system.

### Using the CLI JAR

Run the CLI tool using the following command:

```bash
java -jar alpheus-afp-parser-cli-<version>.jar <input-afp-file> [output-xml-file]
```

- **`<input-afp-file>`**: The path to the input AFP file you wish to process.
- **`[output-xml-file]`**: (Optional) The path where the generated XML output will be saved. If this argument is omitted, the XML content will be printed directly to the standard output (console).

### XML Output Format

The CLI tool generates an XML representation where:
- The root element is `<AFPDocument>`.
- Each AFP Structured Field is represented as a child element (e.g., `<BDT_BeginDocument>`, `<PGD_PageDescriptor>`).
- Fields within each Structured Field are mapped to XML elements.
- The `structuredFieldIntroducer` (containing length, type, and flags) is included for every field.

### Examples

**Convert an AFP file and view the XML in the console:**

```bash
java -jar alpheus-afp-parser-cli-<version>.jar my_document.afp
```

**Convert an AFP file and save the result to a file:**

```bash
java -jar alpheus-afp-parser-cli-<version>.jar my_document.afp my_document.xml
```

## Error Handling

By default, the parser throws an `AFPParserException` when it encounters an error in the AFP stream. You can change this behavior to continue parsing even if errors occur.

```java
config.setEscalateParsingErrors(false);
```

When `setEscalateParsingErrors(false)` is set, `parseNextSF()` will return an instance of `StructuredFieldErrornouslyBuilt` instead of throwing an exception. You can inspect this object to get details about the error and the raw data of the problematic field.
