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
along with Alpheus AFP Parser.  See <http://www.gnu.org/licenses/>
*/

package com.mgz.pdf;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.kernel.pdf.canvas.PdfCanvas;
import com.mgz.afp.goca.GAD_DrawingOrder;
import com.mgz.afp.goca.GAD_DrawingOrder.GBIMG_BeginImageAtGivenPosition;
import com.mgz.afp.goca.GAD_DrawingOrder.GEIMG_EndImage;
import com.mgz.afp.goca.GAD_DrawingOrder.GIMD_ImageData;
import com.mgz.afp.goca.GAD_DrawingOrder.GOCA_Point;
import org.junit.jupiter.api.Test;
import java.io.ByteArrayOutputStream;

public class PdfGocaImageTest {

  @Test
  public void testGocaImageRendering() throws Exception {
    ByteArrayOutputStream baos = new ByteArrayOutputStream();
    PdfHandler handler = new PdfHandler(baos);

    // Simulate GOCA stream
    GBIMG_BeginImageAtGivenPosition gbimg = new GBIMG_BeginImageAtGivenPosition();
    gbimg.setOrigin(new GOCA_Point((short) 100, (short) 100));
    gbimg.setWidthOfImageInImagePoints(8);
    gbimg.setHeightOfImageInImagePoints(8);

    GIMD_ImageData gimd = new GIMD_ImageData();
    gimd.setImageData(new byte[] {(byte) 0xAA, (byte) 0x55, (byte) 0xAA, (byte) 0x55, (byte) 0xAA, (byte) 0x55, (byte) 0xAA, (byte) 0x55});

    GEIMG_EndImage geimg = new GEIMG_EndImage();

    com.mgz.afp.modca.BPG_BeginPage bpg = new com.mgz.afp.modca.BPG_BeginPage();
    bpg.setName("PAGE1");
    handler.handle(bpg);

    handler.handle(new com.mgz.afp.goca.GAD_GraphicsData() {
        @Override
        public java.util.List<GAD_DrawingOrder> getDrawingOrders() {
            return java.util.List.of(gbimg, gimd, geimg);
        }
    });

    com.mgz.afp.modca.EPG_EndPage epg = new com.mgz.afp.modca.EPG_EndPage();
    epg.setName("PAGE1");
    handler.handle(epg);

    handler.close();

    // If it reached here without Exception, it's a good sign.
    // To really verify, we'd need to inspect the generated PDF or the canvas.
    // For now, this unit test ensures the flow is correct.
  }
}
