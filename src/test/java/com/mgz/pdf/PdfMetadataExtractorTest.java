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

import com.itextpdf.kernel.pdf.PdfArray;
import com.itextpdf.kernel.pdf.PdfDictionary;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfName;
import com.itextpdf.kernel.pdf.PdfString;
import com.itextpdf.kernel.pdf.PdfWriter;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.Test;

public class PdfMetadataExtractorTest {

  @Test
  public void testExtractMetadataEmpty() throws IOException {
    try (ByteArrayOutputStream os = new ByteArrayOutputStream();
        PdfWriter writer = new PdfWriter(os);
        PdfDocument pdfDoc = new PdfDocument(writer)) {
      pdfDoc.addNewPage();
      pdfDoc.close();

      PdfMetadataExtractor extractor = new PdfMetadataExtractor();
      List<Map<String, String>> metadata = extractor.extractMetadata(new ByteArrayInputStream(os.toByteArray()));
      assertTrue(metadata.isEmpty());
    }
  }

  @Test
  public void testExtractMetadataWithDPart() throws IOException {
    try (ByteArrayOutputStream os = new ByteArrayOutputStream();
        PdfWriter writer = new PdfWriter(os);
        PdfDocument pdfDoc = new PdfDocument(writer)) {
      pdfDoc.addNewPage();

      PdfDictionary dpartRoot = new PdfDictionary();
      PdfDictionary dtree = new PdfDictionary();
      dpartRoot.put(new PdfName("DTree"), dtree);

      PdfDictionary property = new PdfDictionary();
      property.put(new PdfName("Customer"), new PdfString("Test Customer"));
      dtree.put(new PdfName("Property"), property);

      PdfArray dparts = new PdfArray();
      PdfDictionary leaf = new PdfDictionary();
      PdfDictionary leafProperty = new PdfDictionary();
      leafProperty.put(new PdfName("DocID"), new PdfString("12345"));
      leaf.put(new PdfName("Property"), leafProperty);
      dparts.add(leaf);
      dtree.put(new PdfName("DParts"), dparts);

      pdfDoc.getCatalog().put(new PdfName("DPartRoot"), dpartRoot);
      pdfDoc.close();

      PdfMetadataExtractor extractor = new PdfMetadataExtractor();
      List<Map<String, String>> metadata = extractor.extractMetadata(new ByteArrayInputStream(os.toByteArray()));

      assertEquals(1, metadata.size());
      Map<String, String> entry = metadata.get(0);
      assertEquals("Test Customer", entry.get("Customer"));
      assertEquals("12345", entry.get("DocID"));

      String json = extractor.toJson(metadata);
      assertNotNull(json);
      assertTrue(json.contains("Test Customer"));
      assertTrue(json.contains("12345"));
    }
  }
}
