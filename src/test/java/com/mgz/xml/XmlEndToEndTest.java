package com.mgz.xml;

import com.mgz.cli.Afp2Xml;
import org.junit.jupiter.api.io.TempDir;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamReader;
import java.io.FileInputStream;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * End-to-end test that validates the XML output of the CLI for well-formedness.
 */
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
        Path xmlOutput = tempDir.resolve("output.xml");

        // Execute CLI
        int result = Afp2Xml.execute(new String[]{afpPath, xmlOutput.toString()});
        assertEquals(0, result, "CLI execution failed for " + afpPath);

        // Validate XML well-formedness using StAX
        XMLInputFactory factory = XMLInputFactory.newInstance();
        try (FileInputStream fis = new FileInputStream(xmlOutput.toFile())) {
            XMLStreamReader reader = factory.createXMLStreamReader(fis);
            boolean foundRoot = false;
            while (reader.hasNext()) {
                int event = reader.next();
                if (event == XMLStreamConstants.START_ELEMENT) {
                    if ("AFPDocument".equals(reader.getLocalName())) {
                        foundRoot = true;
                    }
                }
            }
            assertTrue(foundRoot, "Root element <AFPDocument> not found in " + xmlOutput);
        }
    }
}
