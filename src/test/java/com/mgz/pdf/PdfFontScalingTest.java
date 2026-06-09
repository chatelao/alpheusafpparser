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

import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfReader;
import com.mgz.afp.enums.AFPUnitBase;
import com.mgz.afp.enums.SFTypeID;
import com.mgz.afp.base.StructuredFieldIntroducer;
import com.mgz.afp.modca.BDT_BeginDocument;
import com.mgz.afp.modca.BPG_BeginPage;
import com.mgz.afp.modca.EMO_EndOverlay;
import com.mgz.afp.modca.EDT_EndDocument;
import com.mgz.afp.modca.EPG_EndPage;
import com.mgz.afp.modca.PGD_PageDescriptor;
import com.mgz.afp.goca.GAD_GraphicsData;
import com.mgz.afp.goca.GAD_DrawingOrder;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PdfFontScalingTest {

  private static void setSFType(com.mgz.afp.base.StructuredField sf, SFTypeID typeId) {
    StructuredFieldIntroducer intro = new StructuredFieldIntroducer();
    intro.setSFTypeID(typeId);
    sf.setStructuredFieldIntroducer(intro);
  }

  @Test
  public void testDoubleScalingReproduction() throws Exception {
    ByteArrayOutputStream baos = new ByteArrayOutputStream();
    PdfHandler handler = new PdfHandler(baos);

    // Document start
    BDT_BeginDocument bdt = new BDT_BeginDocument();
    bdt.setName("DOC1");
    setSFType(bdt, SFTypeID.BDT_BeginDocument);
    handler.handle(bdt);

    // 1. Document-level PGD: 1000 units per 10 inches -> scale 0.72
    PGD_PageDescriptor docPgd = new PGD_PageDescriptor();
    docPgd.setxUnitBase(AFPUnitBase.Inches10);
    docPgd.setyUnitBase(AFPUnitBase.Inches10);
    docPgd.setxUnitsPerUnitBase((short) 1000);
    docPgd.setyUnitsPerUnitBase((short) 1000);
    docPgd.setxSize(1000);
    docPgd.setySize(1000);
    handler.handle(docPgd);

    // Page start
    BPG_BeginPage bpg = new BPG_BeginPage();
    bpg.setName("PAGE1");
    setSFType(bpg, SFTypeID.BPG_BeginPage);
    handler.handle(bpg);

    // 2. Page-level PGD: 2000 units per 10 inches -> scale 0.36
    PGD_PageDescriptor pagePgd = new PGD_PageDescriptor();
    pagePgd.setxUnitBase(AFPUnitBase.Inches10);
    pagePgd.setyUnitBase(AFPUnitBase.Inches10);
    pagePgd.setxUnitsPerUnitBase((short) 2000);
    pagePgd.setyUnitsPerUnitBase((short) 2000);
    pagePgd.setxSize(2000);
    pagePgd.setySize(2000);
    handler.handle(pagePgd);

    // 3. GAD with a line
    GAD_GraphicsData gad = new GAD_GraphicsData() {
        @Override
        public java.util.List<GAD_DrawingOrder> getDrawingOrders() {
            GAD_DrawingOrder.GLINE_LineAtGivenPosition line = new GAD_DrawingOrder.GLINE_LineAtGivenPosition();
            return Collections.singletonList(line);
        }
    };
    handler.handle(gad);

    handler.handle(new EPG_EndPage());
    handler.handle(new EDT_EndDocument());
    handler.close();

    // Verify the scale in the resulting PDF
    byte[] pdfBytes = baos.toByteArray();
    try (PdfDocument pdfDoc = new PdfDocument(new PdfReader(new ByteArrayInputStream(pdfBytes)))) {
      StringBuilder fullContent = new StringBuilder();
      int streamCount = pdfDoc.getPage(1).getContentStreamCount();
      for (int i = 0; i < streamCount; i++) {
          fullContent.append(new String(pdfDoc.getPage(1).getContentStream(i).getBytes()));
          fullContent.append("\n---\n");
      }
      String content = fullContent.toString();
      System.out.println("Page Content Streams (" + streamCount + "):\n" + content);

      int cmCount = 0;
      int index = 0;
      while ((index = content.indexOf(" cm", index)) != -1) {
        cmCount++;
        index += 3;
      }

      // If the bug exists, we expect cmCount to be 2.
      // We want it to be 1.
      assertEquals(1, cmCount, "Should exactly have one 'cm' transformation operator per page");
    }
  }
}
