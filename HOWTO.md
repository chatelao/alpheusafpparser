# HOWTO: Use Alpheus AFP Parser

This guide provides a detailed overview of how to use the Alpheus AFP Parser library to read and process IBM AFP (Advanced Function Presentation) files.

## Installation

### Maven

Add the following dependency to your `pom.xml`:

```xml
<dependency>
  <groupId>com.github.afpdev</groupId>
  <artifactId>alpheusafpparser</artifactId>
  <version>0.2.2</version>
</dependency>
```

### Gradle

Add the following to your `build.gradle`:

```gradle
dependencies {
    compile 'com.github.afpdev:alpheusafpparser:0.2.2'
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

The Alpheus AFP Parser includes a CLI tool to convert AFP files to XML.

### Prerequisites

You must have Java 8 or higher installed.

### Using the CLI JAR

You can run the CLI tool using the following command:

```bash
java -jar alpheus-afp-parser-cli-<version>.jar <input-afp-file> [output-xml-file]
```

- `<input-afp-file>`: The path to the AFP file you want to convert.
- `[output-xml-file]`: (Optional) The path where the XML output should be saved. If omitted, the XML is printed to standard output.

### Examples

**Convert an AFP file and print XML to console:**

```bash
java -jar alpheus-afp-parser-cli-0.2.2.jar example.afp
```

**Convert an AFP file and save XML to a file:**

```bash
java -jar alpheus-afp-parser-cli-0.2.2.jar example.afp output.xml
```

## Error Handling

By default, the parser throws an `AFPParserException` when it encounters an error in the AFP stream. You can change this behavior to continue parsing even if errors occur.

```java
config.setEscalateParsingErrors(false);
```

When `setEscalateParsingErrors(false)` is set, `parseNextSF()` will return an instance of `StructuredFieldErrornouslyBuilt` instead of throwing an exception. You can inspect this object to get details about the error and the raw data of the problematic field.
