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

import com.mgz.afp.enums.AFPUnitBase;
import com.mgz.afp.modca.BPG_BeginPage;
import com.mgz.afp.modca.EPG_EndPage;
import com.mgz.afp.modca.PGD_PageDescriptor;
import org.apache.pdfbox.Loader;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdfparser.PDFStreamParser;
import org.apache.pdfbox.contentstream.operator.Operator;
import org.apache.pdfbox.cos.COSNumber;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.ByteArrayOutputStream;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PdfWindowOverlayTest {

  @TempDir
  Path tempDir;

  @Test
  public void testWindowOverlayTopOffsetPositioning() throws Exception {
    ByteArrayOutputStream baos = new ByteArrayOutputStream();
    PdfHandler pdfHandler = new PdfHandler(baos);
    pdfHandler.setDrawWindow(true);
    pdfHandler.setWindowLeft(20.0);
    pdfHandler.setWindowTop(50.0);
    pdfHandler.setWindowWidth(119.0);
    pdfHandler.setWindowHeight(64.0);

    // Create a page with PGD (A4 size: 210mm x 297mm -> 11906 x 16838 in 1/1440 in)
    PGD_PageDescriptor pgd = new PGD_PageDescriptor();
    pgd.setxUnitBase(AFPUnitBase.Inches10);
    pgd.setyUnitBase(AFPUnitBase.Inches10);
    pgd.setxUnitsPerUnitBase((short) 14400);
    pgd.setyUnitsPerUnitBase((short) 14400);
    pgd.setxSize(11906);
    pgd.setySize(16838);

    BPG_BeginPage bpg = new BPG_BeginPage();
    bpg.setName("P0000001");
    EPG_EndPage epg = new EPG_EndPage();
    epg.setName("P0000001");

    pdfHandler.handle(pgd);
    pdfHandler.handle(bpg);
    pdfHandler.handle(epg);
    pdfHandler.close();

    byte[] pdfBytes = baos.toByteArray();
    double mmToPoints = 72.0 / 25.4;

    try (PDDocument document = Loader.loadPDF(pdfBytes)) {
      assertEquals(1, document.getNumberOfPages());
      float pageHeight = document.getPage(0).getMediaBox().getHeight(); // A4 height ~841.89 points

      // Calculate expected inner and outer window coordinates in points
      float expectedInnerW = (float) (100.0 * mmToPoints);
      float expectedInnerH = (float) (50.0 * mmToPoints);
      float expectedInnerY = (float) (pageHeight - (50.0 + 50.0) * mmToPoints); // top edge at windowTop=50mm

      float expectedOuterW = (float) (119.0 * mmToPoints);
      float expectedOuterH = (float) (64.0 * mmToPoints);
      float expectedOuterY = expectedInnerY - (expectedOuterH - expectedInnerH) / 2.0f;

      // Extract rectangle operators 're' from PDF page stream
      PDFStreamParser parser = new PDFStreamParser(document.getPage(0));
      List<float[]> rects = new ArrayList<>();
      List<Object> tokens = new ArrayList<>();
      Object token;
      while ((token = parser.parseNextToken()) != null) {
        if (token instanceof Operator op && op.getName().equals("re")) {
          int size = tokens.size();
          float x = ((COSNumber) tokens.get(size - 4)).floatValue();
          float y = ((COSNumber) tokens.get(size - 3)).floatValue();
          float w = ((COSNumber) tokens.get(size - 2)).floatValue();
          float h = ((COSNumber) tokens.get(size - 1)).floatValue();
          rects.add(new float[]{x, y, w, h});
        }
        tokens.add(token);
      }

      assertEquals(2, rects.size(), "Should have drawn 2 rectangles (outer and inner)");

      float[] outerRect = rects.get(0);
      float[] innerRect = rects.get(1);

      assertEquals(expectedOuterY, outerRect[1], 0.1, "Outer Y coordinate matching");
      assertEquals(expectedOuterW, outerRect[2], 0.1, "Outer width matching");
      assertEquals(expectedOuterH, outerRect[3], 0.1, "Outer height matching");

      assertEquals(expectedInnerY, innerRect[1], 0.1, "Inner Y coordinate matching");
      assertEquals(expectedInnerW, innerRect[2], 0.1, "Inner width matching");
      assertEquals(expectedInnerH, innerRect[3], 0.1, "Inner height matching");

      // Verify top edge of inner window from top of page is exactly 50mm
      float topOffsetInnerMm = (float) ((pageHeight - (innerRect[1] + innerRect[3])) / mmToPoints);
      assertEquals(50.0, topOffsetInnerMm, 0.1, "Inner window top offset should measure exactly 50mm from top of page");
    }
  }
}
