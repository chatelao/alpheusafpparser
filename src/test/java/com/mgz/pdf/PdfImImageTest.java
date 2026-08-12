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

import static org.junit.jupiter.api.Assertions.assertTrue;

import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfReader;
import com.itextpdf.kernel.pdf.PdfPage;
import com.mgz.afp.base.StructuredFieldIntroducer;
import com.mgz.afp.enums.SFTypeID;
import com.mgz.afp.enums.SFFlag;
import com.mgz.afp.enums.AFPColorValue;
import com.mgz.afp.enums.AFPUnitBase;
import com.mgz.afp.modca.BPG_BeginPage;
import com.mgz.afp.modca.EPG_EndPage;
import com.mgz.afp.modca.BII_BeginIMImageObject;
import com.mgz.afp.modca.EII_EndIMImageObject;
import com.mgz.afp.modca.IID_IMImageInputDescriptor;
import com.mgz.afp.modca.ICP_IMImageCellPosition;
import com.mgz.afp.modca.IRD_IMImageRasterData;
import com.mgz.afp.modca.IOC_IMImageOutputControl;
import org.junit.jupiter.api.Test;
import java.io.ByteArrayOutputStream;
import java.util.EnumSet;

public class PdfImImageTest {

  private StructuredFieldIntroducer createSfi(SFTypeID typeID) {
    StructuredFieldIntroducer sfi = new StructuredFieldIntroducer();
    sfi.setSFTypeID(typeID);
    sfi.setFlagByte(EnumSet.noneOf(SFFlag.class));
    return sfi;
  }

  @Test
  public void testImImageRendering() throws Exception {
    ByteArrayOutputStream baos = new ByteArrayOutputStream();
    PdfHandler handler = new PdfHandler(baos);

    // 1. Begin Page
    BPG_BeginPage bpg = new BPG_BeginPage();
    bpg.setStructuredFieldIntroducer(createSfi(SFTypeID.BPG_BeginPage));
    bpg.setName("PAGE1");
    handler.handle(bpg);

    // 2. Begin IM Image
    BII_BeginIMImageObject bii = new BII_BeginIMImageObject();
    bii.setStructuredFieldIntroducer(createSfi(SFTypeID.BII_BeginIMImageObject));
    handler.handle(bii);

    // 3. IM Image Output Control (IOC)
    IOC_IMImageOutputControl ioc = new IOC_IMImageOutputControl();
    ioc.setStructuredFieldIntroducer(createSfi(SFTypeID.IOC_IMImageOutputControl));
    ioc.setxOrigin(1440); // 1 inch from left
    ioc.setyOrigin(1440); // 1 inch from top
    handler.handle(ioc);

    // 4. IM Image Input Descriptor (IID)
    IID_IMImageInputDescriptor iid = new IID_IMImageInputDescriptor() {
      @Override
      public AFPUnitBase getxUnitBase() { return AFPUnitBase.Inches10; }
      @Override
      public AFPUnitBase getyUnitBase() { return AFPUnitBase.Inches10; }
      @Override
      public short getxUnitsPerUnitBase() { return 2400; } // 240 dpi
      @Override
      public short getyUnitsPerUnitBase() { return 2400; } // 240 dpi
      @Override
      public AFPColorValue getColor() { return AFPColorValue.Red_0x02; }
    };
    iid.setStructuredFieldIntroducer(createSfi(SFTypeID.IID_IMImageInputDescriptor));
    handler.handle(iid);

    // 5. IM Image Cell Position (ICP)
    ICP_IMImageCellPosition icp = new ICP_IMImageCellPosition() {
      @Override
      public short getxOffset() { return 10; } // 10 image points offset
      @Override
      public short getyOffset() { return 20; } // 20 image points offset
      @Override
      public int getxSize() { return 8; } // width of cell is 8 image points
      @Override
      public int getySize() { return 8; } // height of cell is 8 image points
    };
    icp.setStructuredFieldIntroducer(createSfi(SFTypeID.ICP_IMImageCellPosition));
    handler.handle(icp);

    // 6. IM Image Raster Data (IRD)
    IRD_IMImageRasterData ird = new IRD_IMImageRasterData();
    ird.setStructuredFieldIntroducer(createSfi(SFTypeID.IRD_IMImageRasterData));
    ird.setData(new byte[] {(byte) 0xAA, (byte) 0x55, (byte) 0xAA, (byte) 0x55, (byte) 0xAA, (byte) 0x55, (byte) 0xAA, (byte) 0x55});
    handler.handle(ird);

    // 7. End IM Image
    EII_EndIMImageObject eii = new EII_EndIMImageObject();
    eii.setStructuredFieldIntroducer(createSfi(SFTypeID.EII_EndIMImageObject));
    handler.handle(eii);

    // 8. End Page
    EPG_EndPage epg = new EPG_EndPage();
    epg.setStructuredFieldIntroducer(createSfi(SFTypeID.EPG_EndPage));
    epg.setName("PAGE1");
    handler.handle(epg);

    handler.close();

    byte[] pdfBytes = baos.toByteArray();
    PdfReader reader = new PdfReader(new java.io.ByteArrayInputStream(pdfBytes));
    PdfDocument pdfDoc = new PdfDocument(reader);
    PdfPage page = pdfDoc.getPage(1);
    byte[] contentBytes = page.getContentBytes();
    String contentString = new String(contentBytes, java.nio.charset.StandardCharsets.UTF_8);

    // Verify matrix transformation for cell:
    // Scale factor: 1440 * 10 / 2400 = 6
    // cellX = xOrigin + (xOffset * scaleFactor) = 1440 + (10 * 6) = 1500
    // cellY = yOrigin + (yOffset * scaleFactor) = 1440 + (20 * 6) = 1560
    // cellWPres = cellW * scaleFactor = 8 * 6 = 48
    // cellHPres = cellH * scaleFactor = 8 * 6 = 48
    // posY = cellY - cellHPres = 1560 - 48 = 1512
    // So the transformation matrix should contain:
    // "48 0 0 48 1500 1512 cm"
    assertTrue(contentString.contains("48 0 0 48 1500 1512 cm"), "IM cell should be drawn with correct scale and translation matrix");

    // Red color fill: in iText mask drawing, it sets the fill color to Red:
    // Red color in RGB is [1, 0, 0] so "1 0 0 rg"
    assertTrue(contentString.contains("1 0 0 rg") || contentString.contains("1.0 0.0 0.0 rg"), "IM cell should use specified Red foreground color");
  }
}
