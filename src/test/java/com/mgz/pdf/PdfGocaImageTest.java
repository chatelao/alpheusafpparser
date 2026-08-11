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

import static org.junit.jupiter.api.Assertions.assertTrue;

import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.kernel.pdf.canvas.PdfCanvas;
import com.mgz.afp.base.StructuredFieldIntroducer;
import com.mgz.afp.enums.SFTypeID;
import com.mgz.afp.enums.SFFlag;
import com.mgz.afp.goca.GAD_DrawingOrder;
import com.mgz.afp.goca.GAD_DrawingOrder.GBIMG_BeginImageAtGivenPosition;
import com.mgz.afp.goca.GAD_DrawingOrder.GEIMG_EndImage;
import com.mgz.afp.goca.GAD_DrawingOrder.GIMD_ImageData;
import com.mgz.afp.goca.GAD_DrawingOrder.GOCA_Point;
import org.junit.jupiter.api.Test;
import java.io.ByteArrayOutputStream;

public class PdfGocaImageTest {

  private StructuredFieldIntroducer createSfi(SFTypeID typeID) {
    StructuredFieldIntroducer sfi = new StructuredFieldIntroducer();
    sfi.setSFTypeID(typeID);
    sfi.setFlagByte(java.util.EnumSet.noneOf(SFFlag.class));
    return sfi;
  }

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
    bpg.setStructuredFieldIntroducer(createSfi(SFTypeID.BPG_BeginPage));
    bpg.setName("PAGE1");
    handler.handle(bpg);

    handler.handle(new com.mgz.afp.goca.GAD_GraphicsData() {
        @Override
        public java.util.List<GAD_DrawingOrder> getDrawingOrders() {
            return java.util.List.of(gbimg, gimd, geimg);
        }
    });

    com.mgz.afp.modca.EPG_EndPage epg = new com.mgz.afp.modca.EPG_EndPage();
    epg.setStructuredFieldIntroducer(createSfi(SFTypeID.EPG_EndPage));
    epg.setName("PAGE1");
    handler.handle(epg);

    handler.close();

    byte[] pdfBytes = baos.toByteArray();
    com.itextpdf.kernel.pdf.PdfReader reader = new com.itextpdf.kernel.pdf.PdfReader(new java.io.ByteArrayInputStream(pdfBytes));
    com.itextpdf.kernel.pdf.PdfDocument pdfDoc = new com.itextpdf.kernel.pdf.PdfDocument(reader);
    com.itextpdf.kernel.pdf.PdfPage page = pdfDoc.getPage(1);
    byte[] contentBytes = page.getContentBytes();
    String contentString = new String(contentBytes, java.nio.charset.StandardCharsets.UTF_8);

    // Verify correct placement without GDD (posY should default to gocaImageY = 100)
    assertTrue(contentString.contains("8 0 0 8 100 100 cm"), "Default rendering without GDD should position at gocaImageY");
  }

  @Test
  public void testGocaImageWithResolutionScaling() throws Exception {
    ByteArrayOutputStream baos = new ByteArrayOutputStream();
    PdfHandler handler = new PdfHandler(baos);

    com.mgz.afp.modca.BPG_BeginPage bpg = new com.mgz.afp.modca.BPG_BeginPage();
    bpg.setStructuredFieldIntroducer(createSfi(SFTypeID.BPG_BeginPage));
    bpg.setName("PAGE1");
    handler.handle(bpg);

    // BGR
    com.mgz.afp.goca.BGR_BeginGraphicsObject bgr = new com.mgz.afp.goca.BGR_BeginGraphicsObject();
    bgr.setStructuredFieldIntroducer(createSfi(SFTypeID.BGR_BeginGraphicsObject));
    handler.handle(bgr);

    // OBD
    com.mgz.afp.modca.OBD_ObjectAreaDescriptor obd = new com.mgz.afp.modca.OBD_ObjectAreaDescriptor();
    com.mgz.afp.triplets.Triplet.ObjectAreaSize oas = new com.mgz.afp.triplets.Triplet.ObjectAreaSize();
    oas.sizeType_0x02 = 0x02;
    oas.xSize = 1000;
    oas.ySize = 1000;
    obd.addTriplet(oas);
    handler.handle(obd);

    // OBP
    com.mgz.afp.modca.OBP_ObjectAreaPosition obp = new com.mgz.afp.modca.OBP_ObjectAreaPosition();
    com.mgz.afp.modca.OBP_ObjectAreaPosition.OBP_RepeatingGroup rg = new com.mgz.afp.modca.OBP_ObjectAreaPosition.OBP_RepeatingGroup();
    rg.setxOrigin(1440);
    rg.setyOrigin(2880);
    rg.setxRotation(com.mgz.afp.enums.AFPOrientation.ori0);
    obp.setRepeatingGroup(rg);
    handler.handle(obp);

    // GDD
    com.mgz.afp.goca.GDD_GraphicsDataDescriptor gdd = new com.mgz.afp.goca.GDD_GraphicsDataDescriptor();
    com.mgz.afp.goca.GDD_Parameter.WindowSpecification win = new com.mgz.afp.goca.GDD_Parameter.WindowSpecification();
    win.setLeftEdgeOfGPSWindow(0);
    win.setRightEdgeOfGPSWindow(500);
    win.setBottomEdgeOfGPSWindow(0);
    win.setTopEdgeOfGPSWindow(500);
    win.setUnitsPerUnitBaseX(2400);
    win.setUnitsPerUnitBaseY(2400);
    win.setImageResolutionXY(3000);
    gdd.setGddParameters(java.util.List.of(win));
    handler.handle(gdd);

    // Simulate GOCA image stream
    GBIMG_BeginImageAtGivenPosition gbimg = new GBIMG_BeginImageAtGivenPosition();
    gbimg.setOrigin(new GOCA_Point((short) 100, (short) 100));
    gbimg.setWidthOfImageInImagePoints(8);
    gbimg.setHeightOfImageInImagePoints(8);

    GIMD_ImageData gimd = new GIMD_ImageData();
    gimd.setImageData(new byte[] {(byte) 0xAA, (byte) 0x55, (byte) 0xAA, (byte) 0x55, (byte) 0xAA, (byte) 0x55, (byte) 0xAA, (byte) 0x55});

    GEIMG_EndImage geimg = new GEIMG_EndImage();

    handler.handle(new com.mgz.afp.goca.GAD_GraphicsData() {
        @Override
        public java.util.List<GAD_DrawingOrder> getDrawingOrders() {
            return java.util.List.of(gbimg, gimd, geimg);
        }
    });

    com.mgz.afp.goca.EGR_EndGraphicsObject egr = new com.mgz.afp.goca.EGR_EndGraphicsObject();
    egr.setStructuredFieldIntroducer(createSfi(SFTypeID.EGR_EndGraphicsObject));
    handler.handle(egr);

    com.mgz.afp.modca.EPG_EndPage epg = new com.mgz.afp.modca.EPG_EndPage();
    epg.setStructuredFieldIntroducer(createSfi(SFTypeID.EPG_EndPage));
    epg.setName("PAGE1");
    handler.handle(epg);

    handler.close();

    byte[] pdfBytes = baos.toByteArray();
    com.itextpdf.kernel.pdf.PdfReader reader = new com.itextpdf.kernel.pdf.PdfReader(new java.io.ByteArrayInputStream(pdfBytes));
    com.itextpdf.kernel.pdf.PdfDocument pdfDoc = new com.itextpdf.kernel.pdf.PdfDocument(reader);
    com.itextpdf.kernel.pdf.PdfPage page = pdfDoc.getPage(1);
    byte[] contentBytes = page.getContentBytes();
    String contentString = new String(contentBytes, java.nio.charset.StandardCharsets.UTF_8);

    // Verify matrix transforms and exact resolution scaled image matrix
    assertTrue(contentString.contains("1 0 0 1 1440 2880 cm"), "Should contain OBP translation matrix");
    assertTrue(contentString.contains("2 0 0 -2 0 1000 cm"), "Should contain GDD window scaling matrix");
    assertTrue(contentString.contains("6.4 0 0 6.4 100 93.6 cm"), "Should contain dynamic resolution scaling and bottom-up offset");
  }

  @Test
  public void testGocaImageWithGddNoResolution() throws Exception {
    ByteArrayOutputStream baos = new ByteArrayOutputStream();
    PdfHandler handler = new PdfHandler(baos);

    com.mgz.afp.modca.BPG_BeginPage bpg = new com.mgz.afp.modca.BPG_BeginPage();
    bpg.setStructuredFieldIntroducer(createSfi(SFTypeID.BPG_BeginPage));
    bpg.setName("PAGE1");
    handler.handle(bpg);

    // BGR
    com.mgz.afp.goca.BGR_BeginGraphicsObject bgr = new com.mgz.afp.goca.BGR_BeginGraphicsObject();
    bgr.setStructuredFieldIntroducer(createSfi(SFTypeID.BGR_BeginGraphicsObject));
    handler.handle(bgr);

    // GDD with WindowSpecification but no resolution/units
    com.mgz.afp.goca.GDD_GraphicsDataDescriptor gdd = new com.mgz.afp.goca.GDD_GraphicsDataDescriptor();
    com.mgz.afp.goca.GDD_Parameter.WindowSpecification win = new com.mgz.afp.goca.GDD_Parameter.WindowSpecification();
    win.setLeftEdgeOfGPSWindow(0);
    win.setRightEdgeOfGPSWindow(500);
    win.setBottomEdgeOfGPSWindow(0);
    win.setTopEdgeOfGPSWindow(500);
    gdd.setGddParameters(java.util.List.of(win));
    handler.handle(gdd);

    // Simulate GOCA image stream
    GBIMG_BeginImageAtGivenPosition gbimg = new GBIMG_BeginImageAtGivenPosition();
    gbimg.setOrigin(new GOCA_Point((short) 100, (short) 100));
    gbimg.setWidthOfImageInImagePoints(8);
    gbimg.setHeightOfImageInImagePoints(8);

    GIMD_ImageData gimd = new GIMD_ImageData();
    gimd.setImageData(new byte[] {(byte) 0xAA, (byte) 0x55, (byte) 0xAA, (byte) 0x55, (byte) 0xAA, (byte) 0x55, (byte) 0xAA, (byte) 0x55});

    GEIMG_EndImage geimg = new GEIMG_EndImage();

    handler.handle(new com.mgz.afp.goca.GAD_GraphicsData() {
        @Override
        public java.util.List<GAD_DrawingOrder> getDrawingOrders() {
            return java.util.List.of(gbimg, gimd, geimg);
        }
    });

    com.mgz.afp.goca.EGR_EndGraphicsObject egr = new com.mgz.afp.goca.EGR_EndGraphicsObject();
    egr.setStructuredFieldIntroducer(createSfi(SFTypeID.EGR_EndGraphicsObject));
    handler.handle(egr);

    com.mgz.afp.modca.EPG_EndPage epg = new com.mgz.afp.modca.EPG_EndPage();
    epg.setStructuredFieldIntroducer(createSfi(SFTypeID.EPG_EndPage));
    epg.setName("PAGE1");
    handler.handle(epg);

    handler.close();

    byte[] pdfBytes = baos.toByteArray();
    com.itextpdf.kernel.pdf.PdfReader reader = new com.itextpdf.kernel.pdf.PdfReader(new java.io.ByteArrayInputStream(pdfBytes));
    com.itextpdf.kernel.pdf.PdfDocument pdfDoc = new com.itextpdf.kernel.pdf.PdfDocument(reader);
    com.itextpdf.kernel.pdf.PdfPage page = pdfDoc.getPage(1);
    byte[] contentBytes = page.getContentBytes();
    String contentString = new String(contentBytes, java.nio.charset.StandardCharsets.UTF_8);

    // Verify matrix scaling with default dimensions (posY = gocaImageY - imgH_gps = 100 - 8 = 92)
    assertTrue(contentString.contains("8 0 0 8 100 92 cm"), "GDD scaling without explicit resolution should map 1-to-1 but preserve bottom-up direction");
  }

  @Test
  public void testGocaImageWithNoGdd() throws Exception {
    ByteArrayOutputStream baos = new ByteArrayOutputStream();
    PdfHandler handler = new PdfHandler(baos);

    com.mgz.afp.modca.BPG_BeginPage bpg = new com.mgz.afp.modca.BPG_BeginPage();
    bpg.setStructuredFieldIntroducer(createSfi(SFTypeID.BPG_BeginPage));
    bpg.setName("PAGE1");
    handler.handle(bpg);

    // BGR
    com.mgz.afp.goca.BGR_BeginGraphicsObject bgr = new com.mgz.afp.goca.BGR_BeginGraphicsObject();
    bgr.setStructuredFieldIntroducer(createSfi(SFTypeID.BGR_BeginGraphicsObject));
    handler.handle(bgr);

    // Simulate GOCA image stream with no GDD descriptor at all
    GBIMG_BeginImageAtGivenPosition gbimg = new GBIMG_BeginImageAtGivenPosition();
    gbimg.setOrigin(new GOCA_Point((short) 100, (short) 100));
    gbimg.setWidthOfImageInImagePoints(8);
    gbimg.setHeightOfImageInImagePoints(8);

    GIMD_ImageData gimd = new GIMD_ImageData();
    gimd.setImageData(new byte[] {(byte) 0xAA, (byte) 0x55, (byte) 0xAA, (byte) 0x55, (byte) 0xAA, (byte) 0x55, (byte) 0xAA, (byte) 0x55});

    GEIMG_EndImage geimg = new GEIMG_EndImage();

    handler.handle(new com.mgz.afp.goca.GAD_GraphicsData() {
        @Override
        public java.util.List<GAD_DrawingOrder> getDrawingOrders() {
            return java.util.List.of(gbimg, gimd, geimg);
        }
    });

    com.mgz.afp.goca.EGR_EndGraphicsObject egr = new com.mgz.afp.goca.EGR_EndGraphicsObject();
    egr.setStructuredFieldIntroducer(createSfi(SFTypeID.EGR_EndGraphicsObject));
    handler.handle(egr);

    com.mgz.afp.modca.EPG_EndPage epg = new com.mgz.afp.modca.EPG_EndPage();
    epg.setStructuredFieldIntroducer(createSfi(SFTypeID.EPG_EndPage));
    epg.setName("PAGE1");
    handler.handle(epg);

    handler.close();

    byte[] pdfBytes = baos.toByteArray();
    com.itextpdf.kernel.pdf.PdfReader reader = new com.itextpdf.kernel.pdf.PdfReader(new java.io.ByteArrayInputStream(pdfBytes));
    com.itextpdf.kernel.pdf.PdfDocument pdfDoc = new com.itextpdf.kernel.pdf.PdfDocument(reader);
    com.itextpdf.kernel.pdf.PdfPage page = pdfDoc.getPage(1);
    byte[] contentBytes = page.getContentBytes();
    String contentString = new String(contentBytes, java.nio.charset.StandardCharsets.UTF_8);

    // Verify matrix scaling with default dimensions (posY = gocaImageY = 100)
    assertTrue(contentString.contains("8 0 0 8 100 100 cm"), "No GDD should fallback to page-level top-down orientation and position at gocaImageY");
  }
}
