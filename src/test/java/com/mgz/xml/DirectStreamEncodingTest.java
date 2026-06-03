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

import com.mgz.util.Constants;
import org.codehaus.stax2.XMLStreamWriter2;
import org.junit.jupiter.api.Test;

import javax.xml.stream.XMLOutputFactory;
import java.io.ByteArrayOutputStream;
import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class DirectStreamEncodingTest {

  @Test
  public void testDirectEbcdicEncoding() throws Exception {
    ByteArrayOutputStream baos = new ByteArrayOutputStream();
    XMLOutputFactory xof = JacksonXmlMapperProvider.getOutputFactory(true); // Woodstox
    XMLStreamWriter2 rawXsw = (XMLStreamWriter2) xof.createXMLStreamWriter(baos, "UTF-8");

    AfpXmlStreamWriter afpXsw = new AfpXmlStreamWriter(rawXsw, baos);

    afpXsw.writeStartDocument();
    afpXsw.writeStartElement("root");

    // "ABC & < >" in EBCDIC CP500
    // A=C1, B=C2, C=C3, space=40, &=50, <=4C, >=6E
    byte[] ebcdicData = new byte[] {
        (byte) 0xC1, (byte) 0xC2, (byte) 0xC3, (byte) 0x40,
        (byte) 0x50, (byte) 0x40, (byte) 0x4C, (byte) 0x40, (byte) 0x6E
    };

    afpXsw.writeStartElement("text");
    afpXsw.writeEbcdic(ebcdicData, 0, ebcdicData.length, Constants.cpIBM500);
    afpXsw.writeEndElement();

    afpXsw.writeEndElement();
    afpXsw.writeEndDocument();
    afpXsw.flush();

    String output = baos.toString(StandardCharsets.UTF_8);
    System.out.println("Output: " + output);

    assertTrue(output.contains("<text>ABC &amp; &lt; &gt;</text>"), "Output should contain escaped EBCDIC text");
  }

  @Test
  public void testSanitization() throws Exception {
    ByteArrayOutputStream baos = new ByteArrayOutputStream();
    XMLOutputFactory xof = JacksonXmlMapperProvider.getOutputFactory(true);
    XMLStreamWriter2 rawXsw = (XMLStreamWriter2) xof.createXMLStreamWriter(baos, "UTF-8");

    AfpXmlStreamWriter afpXsw = new AfpXmlStreamWriter(rawXsw, baos);

    afpXsw.writeStartElement("root");

    // EBCDIC 0x00 is invalid in XML 1.0 (except in some contexts, but our encoder replaces it with space)
    byte[] ebcdicData = new byte[] { (byte) 0xC1, 0x00, (byte) 0xC2 };

    afpXsw.writeEbcdic(ebcdicData, 0, ebcdicData.length, Constants.cpIBM500);
    afpXsw.writeEndElement();
    afpXsw.flush();

    String output = baos.toString(StandardCharsets.UTF_8);
    assertTrue(output.contains("A B"), "Invalid character should be replaced by space");
  }
}
