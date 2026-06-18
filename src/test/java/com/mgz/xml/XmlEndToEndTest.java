package com.mgz.xml;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.mgz.cli.Afp2Xml;
import java.io.File;
import java.io.FileInputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamReader;
import org.junit.jupiter.api.io.TempDir;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

/**
 * End-to-end test that validates the XML output of the CLI for well-formedness.
 */
@Execution(ExecutionMode.SAME_THREAD)
public class XmlEndToEndTest {

  @TempDir
  Path tempDir;

  @ParameterizedTest(name = "Validating XML output for {0}")
  @ValueSource(strings = {
      "src/test/resources/afp/minimal.afp",
      "src/test/resources/afp/afp-goca-reference-03/Chapter_1.afp",
      "src/test/resources/afp/bcoca-reference-11/Chapter_1.afp",
      "src/test/resources/afp/ioca-reference-09/Chapter_1.afp",
      "src/test/resources/afp/modca-reference-10/Chapter_1.afp",
      "src/test/resources/afp/ptoca-reference-04/Chapter_1.afp"
  })
  void testXmlWellFormedness(String afpPath) throws Exception {
    File afpFile = new File(afpPath);
    assertTrue(afpFile.exists(), "AFP test file not found: " + afpPath);

    String safeName = afpFile.getName().replace('.', '_');
    Path xmlOutput = tempDir.resolve("output_" + safeName + ".xml");

    try {
      // Execute CLI
      int result = Afp2Xml.execute(new String[]{afpFile.getAbsolutePath(), xmlOutput.toString()});
      assertEquals(0, result, "CLI execution failed for " + afpPath);

      assertTrue(Files.exists(xmlOutput), "XML output file was not created: " + xmlOutput);

      // Validate XML well-formedness using StAX
      XMLInputFactory factory = XMLInputFactory.newInstance();
      try (FileInputStream fis = new FileInputStream(xmlOutput.toFile())) {
        XMLStreamReader reader = factory.createXMLStreamReader(fis);
        boolean foundRoot = false;
        try {
          while (reader.hasNext()) {
            int event = reader.next();
            if (event == XMLStreamConstants.START_ELEMENT) {
              if ("AFPDocument".equals(reader.getLocalName())) {
                foundRoot = true;
              }
            }
          }
        } finally {
          reader.close();
        }
        assertTrue(foundRoot, "Root element <AFPDocument> not found in " + xmlOutput);
      }
    } catch (Throwable t) {
      System.err.println("E2E Test failure for " + afpPath);
      if (Files.exists(xmlOutput)) {
        String content = Files.readString(xmlOutput);
        int len = Math.min(content.length(), 1000);
        System.err.println("Partial output:\n" + content.substring(0, len));
      }
      throw t;
    }
  }
}
