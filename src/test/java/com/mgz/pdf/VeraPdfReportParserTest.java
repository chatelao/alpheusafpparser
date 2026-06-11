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

package com.mgz.pdf;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.ByteArrayInputStream;
import java.nio.charset.StandardCharsets;
import org.junit.jupiter.api.Test;

/**
 * Unit test for {@link VeraPdfReportParser}.
 */
public class VeraPdfReportParserTest {

  @Test
  public void testParseCompliant() throws Exception {
    String xml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n"
        + "<report xmlns=\"http://www.verapdf.org/ns/report\">\n"
        + "  <validation profileName=\"PDF/A-1b\" isCompliant=\"true\" statement=\"Document is compliant\" />\n"
        + "</report>";
    VeraPdfReportParser parser = new VeraPdfReportParser();
    VeraPdfReportParser.ValidationResult result = parser.parse(new ByteArrayInputStream(xml.getBytes(StandardCharsets.UTF_8)));

    assertTrue(result.isCompliant());
    assertEquals("PDF/A-1b", result.profileName());
    assertTrue(result.failedRules().isEmpty());
  }

  @Test
  public void testParseNonCompliant() throws Exception {
    String xml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n"
        + "<report xmlns=\"http://www.verapdf.org/ns/report\">\n"
        + "  <validation profileName=\"PDF/A-2u\" isCompliant=\"false\">\n"
        + "    <rule specification=\"ISO 19005-2:2011\" status=\"failed\">\n"
        + "      <description>Font not embedded</description>\n"
        + "    </rule>\n"
        + "  </validation>\n"
        + "</report>";
    VeraPdfReportParser parser = new VeraPdfReportParser();
    VeraPdfReportParser.ValidationResult result = parser.parse(new ByteArrayInputStream(xml.getBytes(StandardCharsets.UTF_8)));

    assertFalse(result.isCompliant());
    assertEquals("PDF/A-2u", result.profileName());
    assertEquals(1, result.failedRules().size());
    assertEquals("ISO 19005-2:2011", result.failedRules().get(0));
  }
}
