/*
Copyright 2026 Rudolf Fiala

This file is part of Alpheus AFP Parser.

Alpheus AFP Parser is free software: you can redistribute it and/or modify
it under the terms of the GNU General Public License as published by
the Free Software Foundation, either version 3 of the License, or
(at your option) any later version.

Alpheus AFP Parser is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License
along with Alpheus AFP Parser.  If not, see <http://www.gnu.org/licenses/>
*/

package com.mgz.xml;

import com.mgz.cli.Afp2Xml;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import java.io.File;
import java.io.FileInputStream;
import java.nio.file.Path;
import java.nio.file.Files;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * End-to-end test for AFP to XML conversion.
 * Verifies that the generated XML is well-formed and contains expected spatial metadata.
 */
public class XmlEndToEndTest {

  @TempDir
  Path tempDir;

  @ParameterizedTest
  @ValueSource(strings = {
      "src/test/resources/afp/minimal.afp",
      "src/test/resources/afp/perf_ptx.afp"
  })
  public void testAfpToXmlEndToEnd(String afpPath) throws Exception {
    File inputFile = new File(afpPath);
    assertTrue(inputFile.exists(), "Input AFP should exist: " + afpPath);

    File outputFile = tempDir.resolve(inputFile.getName() + ".xml").toFile();

    // Use CLI to convert AFP to XML
    String[] args = {
        inputFile.getAbsolutePath(),
        outputFile.getAbsolutePath()
    };

    int result = Afp2Xml.execute(args);
    assertEquals(0, result, "Conversion should succeed for " + afpPath);
    assertTrue(outputFile.exists(), "Output XML should exist");
    assertTrue(outputFile.length() > 0, "Output XML should not be empty");

    // Verify XML is well-formed
    verifyXmlWellFormed(outputFile);

    // For perf_ptx.afp, also verify presence of page, x, and y attributes
    if (afpPath.contains("perf_ptx.afp")) {
      String content = Files.readString(outputFile.toPath());
      assertTrue(content.contains("page=\""), "Output should contain 'page' attribute");
      assertTrue(content.contains("x=\""), "Output should contain 'x' attribute");
      assertTrue(content.contains("y=\""), "Output should contain 'y' attribute");
    }
  }

  private void verifyXmlWellFormed(File xmlFile) throws Exception {
    XMLInputFactory factory = XMLInputFactory.newInstance();
    // Disable DTD processing for security and speed
    factory.setProperty(XMLInputFactory.SUPPORT_DTD, false);
    factory.setProperty(XMLInputFactory.IS_SUPPORTING_EXTERNAL_ENTITIES, false);

    try (FileInputStream fis = new FileInputStream(xmlFile)) {
      XMLStreamReader reader = factory.createXMLStreamReader(fis);
      try {
        while (reader.hasNext()) {
          reader.next();
        }
      } catch (XMLStreamException e) {
        throw new Exception("XML is not well-formed: " + xmlFile.getAbsolutePath(), e);
      } finally {
        reader.close();
      }
    }
  }
}
