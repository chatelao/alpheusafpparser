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
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.Test;

/**
 * Unit test for {@link PdfMetadataExtractor}.
 */
public class PdfMetadataExtractorTest {

  @Test
  public void testExtractMetadataEmpty() throws IOException {
    PdfMetadataExtractor extractor = new PdfMetadataExtractor();
    try (ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PdfWriter writer = new PdfWriter(baos);
        PdfDocument pdfDoc = new PdfDocument(writer)) {
      pdfDoc.addNewPage();
      pdfDoc.close();

      List<Map<String, String>> metadata = extractor.extractMetadata(new ByteArrayInputStream(baos.toByteArray()));
      assertTrue(metadata.isEmpty());
    }
  }

  @Test
  public void testToJson() throws IOException {
    PdfMetadataExtractor extractor = new PdfMetadataExtractor();
    List<Map<String, String>> metadata = new ArrayList<>();
    Map<String, String> entry = new HashMap<>();
    entry.put("Key", "Value");
    metadata.add(entry);

    String json = extractor.toJson(metadata);
    assertNotNull(json);
    assertTrue(json.contains("\"Key\" : \"Value\""));
  }
}
