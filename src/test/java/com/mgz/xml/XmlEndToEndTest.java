package com.mgz.xml;

import com.mgz.afp.base.StructuredField;
import com.mgz.afp.parser.AFPParser;
import com.mgz.afp.parser.AFPParserConfiguration;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.stream.Stream;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

/**
 * End-to-end test that verifies the validity of XML output for all sample AFP files.
 */
public class XmlEndToEndTest {

  /**
   * Test that verifies the XML validity for a single AFP file.
   *
   * @param afpPath The path to the AFP file.
   * @throws Exception if an error occurs.
   */
  @ParameterizedTest(name = "{index} {0}")
  @MethodSource("afpFiles")
  public void testAfpToXmlValidity(Path afpPath) throws Exception {
    File afpFile = afpPath.toFile();
    ByteArrayOutputStream out = new ByteArrayOutputStream();

    AFPParserConfiguration config = new AFPParserConfiguration();
    config.setAFPFile(afpFile);
    // Do not escalate parsing errors to allow checking XML validity of partially processed files
    config.setEscalateParsingErrors(false);

    AFPParser parser = new AFPParser(config);
    try (AfpJacksonXmlWriter writer = new AfpJacksonXmlWriter(out, null, false, false)) {
      StructuredField sf;
      while ((sf = parser.parseNextSF()) != null) {
        writer.handle(sf);
        sf.release();
      }
    } catch (Exception e) {
      // Some files might be so broken they fail even with escalateParsingErrors(false)
      // We log this but don't fail the test unless it produced malformed XML
      System.err.println("Note: Processing partially failed for " + afpPath + ": " + e.getMessage());
    } finally {
      parser.quitParsing();
    }

    byte[] xmlBytes = out.toByteArray();
    if (xmlBytes.length == 0) {
      return; // Skip if no output produced (e.g. empty file)
    }

    validateXmlWellFormedness(xmlBytes, afpPath);
  }

  private static Stream<Path> afpFiles() throws IOException {
    Path resources = Path.of("src/test/resources/afp");
    if (!Files.exists(resources)) {
      return Stream.empty();
    }
    return Files.walk(resources)
        .filter(p -> p.toString().endsWith(".afp"))
        .filter(Files::isRegularFile);
  }

  private void validateXmlWellFormedness(byte[] xmlBytes, Path afpPath) throws XMLStreamException {
    XMLInputFactory factory = XMLInputFactory.newInstance();
    factory.setProperty(XMLInputFactory.SUPPORT_DTD, false);

    XMLStreamReader reader = factory.createXMLStreamReader(new ByteArrayInputStream(xmlBytes));
    try {
      while (reader.hasNext()) {
        reader.next();
      }
    } catch (XMLStreamException e) {
      String xmlSnippet = new String(xmlBytes, 0, Math.min(xmlBytes.length, 1000),
          StandardCharsets.UTF_8);
      System.err.println("Invalid XML for " + afpPath + " at line "
          + e.getLocation().getLineNumber() + ", col " + e.getLocation().getColumnNumber()
          + ": " + e.getMessage());
      System.err.println("Snippet: " + xmlSnippet);
      throw e;
    } finally {
      reader.close();
    }
  }
}
