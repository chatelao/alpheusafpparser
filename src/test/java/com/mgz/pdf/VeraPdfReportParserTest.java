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

import org.junit.jupiter.api.Test;
import java.io.ByteArrayInputStream;
import java.nio.charset.StandardCharsets;
import static org.junit.jupiter.api.Assertions.*;

public class VeraPdfReportParserTest {

    @Test
    public void testParseCompliantReport() throws Exception {
        String xml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
                "<veraPdfReport xmlns=\"http://www.verapdf.org/ns/report/\">\n" +
                "  <validation profileName=\"PDF/VT-1\" isCompliant=\"true\" statement=\"PDF file is compliant with PDF/VT-1\" />\n" +
                "</veraPdfReport>";

        VeraPdfReportParser parser = new VeraPdfReportParser();
        VeraPdfReportParser.ValidationResult result = parser.parse(new ByteArrayInputStream(xml.getBytes(StandardCharsets.UTF_8)));

        assertTrue(result.isCompliant());
        assertEquals("PDF/VT-1", result.profileName());
        assertTrue(result.failedRules().isEmpty());
    }

    @Test
    public void testParseNonCompliantReport() throws Exception {
        String xml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
                "<veraPdfReport xmlns=\"http://www.verapdf.org/ns/report/\">\n" +
                "  <validation profileName=\"PDF/VT-1\" isCompliant=\"false\">\n" +
                "    <details>\n" +
                "      <rule status=\"failed\" specification=\"ISO 19005-1:2005\" clause=\"6.1.3\" testNumber=\"1\">\n" +
                "        <description>The file header must begin with %PDF-1.n</description>\n" +
                "      </rule>\n" +
                "    </details>\n" +
                "  </validation>\n" +
                "</veraPdfReport>";

        VeraPdfReportParser parser = new VeraPdfReportParser();
        VeraPdfReportParser.ValidationResult result = parser.parse(new ByteArrayInputStream(xml.getBytes(StandardCharsets.UTF_8)));

        assertFalse(result.isCompliant());
        assertEquals("PDF/VT-1", result.profileName());
        assertEquals(1, result.failedRules().size());
        assertEquals("ISO 19005-1:2005", result.failedRules().get(0));
    }
}
