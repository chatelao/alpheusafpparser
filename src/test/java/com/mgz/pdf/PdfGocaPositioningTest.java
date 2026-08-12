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

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertEquals;

import com.mgz.afp.enums.AFPOrientation;
import com.mgz.afp.goca.BGR_BeginGraphicsObject;
import com.mgz.afp.goca.EGR_EndGraphicsObject;
import com.mgz.afp.goca.GDD_GraphicsDataDescriptor;
import com.mgz.afp.goca.GDD_Parameter;
import com.mgz.afp.goca.GAD_GraphicsData;
import com.mgz.afp.goca.GAD_DrawingOrder;
import com.mgz.afp.goca.GAD_DrawingOrder.GSCP_SetCurrentPosition;
import com.mgz.afp.goca.GAD_DrawingOrder.GSCOL_SetColor;
import com.mgz.afp.goca.GAD_DrawingOrder.GBSEG_BeginSegment;
import com.mgz.afp.goca.GAD_DrawingOrder.GSGCH_SegmentCharacteristics;
import com.mgz.afp.goca.GAD_DrawingOrder.GLINE_LineAtGivenPosition;
import com.mgz.afp.goca.GAD_DrawingOrder.GBOX_BoxAtGivenPosition;
import com.mgz.afp.goca.GAD_DrawingOrder.GCHST_CharacterStringAtGivenPosition;
import com.mgz.afp.goca.GAD_DrawingOrder.GOCA_Point;
import com.mgz.afp.modca.OBD_ObjectAreaDescriptor;
import com.mgz.afp.modca.OBP_ObjectAreaPosition;
import com.mgz.afp.modca.BPG_BeginPage;
import com.mgz.afp.modca.EPG_EndPage;
import com.mgz.afp.triplets.Triplet;
import java.io.ByteArrayOutputStream;
import java.util.List;
import org.junit.jupiter.api.Test;

/**
 * Verifies GOCA Object Area Positioning (OBP) and Graphics Data Descriptor (GDD) scaling.
 */
public class PdfGocaPositioningTest {

  private String getPdfContentString(ByteArrayOutputStream baos) throws Exception {
    byte[] pdfBytes = baos.toByteArray();
    com.itextpdf.kernel.pdf.PdfReader reader = new com.itextpdf.kernel.pdf.PdfReader(new java.io.ByteArrayInputStream(pdfBytes));
    com.itextpdf.kernel.pdf.PdfDocument pdfDoc = new com.itextpdf.kernel.pdf.PdfDocument(reader);
    com.itextpdf.kernel.pdf.PdfPage page = pdfDoc.getPage(1);
    byte[] contentBytes = page.getContentBytes();
    return new String(contentBytes, java.nio.charset.StandardCharsets.UTF_8);
  }

  private com.mgz.afp.base.StructuredFieldIntroducer createSfi(com.mgz.afp.enums.SFTypeID typeID) {
    com.mgz.afp.base.StructuredFieldIntroducer sfi = new com.mgz.afp.base.StructuredFieldIntroducer();
    sfi.setSFTypeID(typeID);
    sfi.setFlagByte(java.util.EnumSet.noneOf(com.mgz.afp.enums.SFFlag.class));
    return sfi;
  }

  @Test
  public void testGocaPositioningRotation90() throws Exception {
    ByteArrayOutputStream baos = new ByteArrayOutputStream();
    PdfHandler handler = new PdfHandler(baos);

    // 1. Begin Page
    BPG_BeginPage bpg = new BPG_BeginPage();
    bpg.setStructuredFieldIntroducer(createSfi(com.mgz.afp.enums.SFTypeID.BPG_BeginPage));
    bpg.setName("P1");
    handler.handle(bpg);

    // 2. Begin Graphics Object
    BGR_BeginGraphicsObject bgr = new BGR_BeginGraphicsObject();
    bgr.setStructuredFieldIntroducer(createSfi(com.mgz.afp.enums.SFTypeID.BGR_BeginGraphicsObject));
    handler.handle(bgr);

    // 3. Object Area Descriptor
    OBD_ObjectAreaDescriptor obd = new OBD_ObjectAreaDescriptor();
    obd.setStructuredFieldIntroducer(createSfi(com.mgz.afp.enums.SFTypeID.OBD_ObjectAreaDescriptor));
    Triplet.ObjectAreaSize oas = new Triplet.ObjectAreaSize();
    oas.sizeType_0x02 = 0x02;
    oas.xSize = 1000;
    oas.ySize = 1000;
    obd.addTriplet(oas);
    handler.handle(obd);

    // 4. Object Area Position with 90 deg rotation
    OBP_ObjectAreaPosition obp = new OBP_ObjectAreaPosition();
    obp.setStructuredFieldIntroducer(createSfi(com.mgz.afp.enums.SFTypeID.OBP_ObjectAreaPosition));
    OBP_ObjectAreaPosition.OBP_RepeatingGroup rg = new OBP_ObjectAreaPosition.OBP_RepeatingGroup();
    rg.setxOrigin(1440);
    rg.setyOrigin(2880);
    rg.setxRotation(AFPOrientation.ori90);
    obp.setRepeatingGroup(rg);
    handler.handle(obp);

    // 5. Graphics Data
    GSCP_SetCurrentPosition gscp = new GSCP_SetCurrentPosition();
    gscp.setCoordinateX((short) 100);
    gscp.setCoordinateY((short) 200);

    handler.handle(new GAD_GraphicsData() {
      @Override
      public List<GAD_DrawingOrder> getDrawingOrders() {
        return List.of(gscp);
      }
    });

    // 6. End Graphics Object
    EGR_EndGraphicsObject egr = new EGR_EndGraphicsObject();
    egr.setStructuredFieldIntroducer(createSfi(com.mgz.afp.enums.SFTypeID.EGR_EndGraphicsObject));
    handler.handle(egr);

    // 7. End Page
    EPG_EndPage epg = new EPG_EndPage();
    epg.setStructuredFieldIntroducer(createSfi(com.mgz.afp.enums.SFTypeID.EPG_EndPage));
    epg.setName("P1");
    handler.handle(epg);

    handler.close();

    String contentString = getPdfContentString(baos);
    assertTrue(contentString.contains("1440 2880 cm"), "Content should contain the OBP origin translation: " + contentString);
    assertTrue(contentString.contains("1 -1"), "Content should contain rotation coefficients: " + contentString);
  }

  @Test
  public void testGocaPositioningRotation180() throws Exception {
    ByteArrayOutputStream baos = new ByteArrayOutputStream();
    PdfHandler handler = new PdfHandler(baos);

    BPG_BeginPage bpg = new BPG_BeginPage();
    bpg.setStructuredFieldIntroducer(createSfi(com.mgz.afp.enums.SFTypeID.BPG_BeginPage));
    bpg.setName("P1");
    handler.handle(bpg);

    BGR_BeginGraphicsObject bgr = new BGR_BeginGraphicsObject();
    bgr.setStructuredFieldIntroducer(createSfi(com.mgz.afp.enums.SFTypeID.BGR_BeginGraphicsObject));
    handler.handle(bgr);

    OBD_ObjectAreaDescriptor obd = new OBD_ObjectAreaDescriptor();
    obd.setStructuredFieldIntroducer(createSfi(com.mgz.afp.enums.SFTypeID.OBD_ObjectAreaDescriptor));
    Triplet.ObjectAreaSize oas = new Triplet.ObjectAreaSize();
    oas.sizeType_0x02 = 0x02;
    oas.xSize = 1000;
    oas.ySize = 1000;
    obd.addTriplet(oas);
    handler.handle(obd);

    OBP_ObjectAreaPosition obp = new OBP_ObjectAreaPosition();
    obp.setStructuredFieldIntroducer(createSfi(com.mgz.afp.enums.SFTypeID.OBP_ObjectAreaPosition));
    OBP_ObjectAreaPosition.OBP_RepeatingGroup rg = new OBP_ObjectAreaPosition.OBP_RepeatingGroup();
    rg.setxOrigin(1440);
    rg.setyOrigin(2880);
    rg.setxRotation(AFPOrientation.ori180);
    obp.setRepeatingGroup(rg);
    handler.handle(obp);

    GSCP_SetCurrentPosition gscp = new GSCP_SetCurrentPosition();
    gscp.setCoordinateX((short) 100);
    gscp.setCoordinateY((short) 200);

    handler.handle(new GAD_GraphicsData() {
      @Override
      public List<GAD_DrawingOrder> getDrawingOrders() {
        return List.of(gscp);
      }
    });

    EGR_EndGraphicsObject egr = new EGR_EndGraphicsObject();
    egr.setStructuredFieldIntroducer(createSfi(com.mgz.afp.enums.SFTypeID.EGR_EndGraphicsObject));
    handler.handle(egr);

    EPG_EndPage epg = new EPG_EndPage();
    epg.setStructuredFieldIntroducer(createSfi(com.mgz.afp.enums.SFTypeID.EPG_EndPage));
    epg.setName("P1");
    handler.handle(epg);

    handler.close();

    String contentString = getPdfContentString(baos);
    assertTrue(contentString.contains("-1 0 0 -1 1440 2880 cm"), "Content should contain the 180 deg rotated matrix: " + contentString);
  }

  @Test
  public void testGocaPositioningRotation270() throws Exception {
    ByteArrayOutputStream baos = new ByteArrayOutputStream();
    PdfHandler handler = new PdfHandler(baos);

    BPG_BeginPage bpg = new BPG_BeginPage();
    bpg.setStructuredFieldIntroducer(createSfi(com.mgz.afp.enums.SFTypeID.BPG_BeginPage));
    bpg.setName("P1");
    handler.handle(bpg);

    BGR_BeginGraphicsObject bgr = new BGR_BeginGraphicsObject();
    bgr.setStructuredFieldIntroducer(createSfi(com.mgz.afp.enums.SFTypeID.BGR_BeginGraphicsObject));
    handler.handle(bgr);

    OBD_ObjectAreaDescriptor obd = new OBD_ObjectAreaDescriptor();
    obd.setStructuredFieldIntroducer(createSfi(com.mgz.afp.enums.SFTypeID.OBD_ObjectAreaDescriptor));
    Triplet.ObjectAreaSize oas = new Triplet.ObjectAreaSize();
    oas.sizeType_0x02 = 0x02;
    oas.xSize = 1000;
    oas.ySize = 1000;
    obd.addTriplet(oas);
    handler.handle(obd);

    OBP_ObjectAreaPosition obp = new OBP_ObjectAreaPosition();
    obp.setStructuredFieldIntroducer(createSfi(com.mgz.afp.enums.SFTypeID.OBP_ObjectAreaPosition));
    OBP_ObjectAreaPosition.OBP_RepeatingGroup rg = new OBP_ObjectAreaPosition.OBP_RepeatingGroup();
    rg.setxOrigin(1440);
    rg.setyOrigin(2880);
    rg.setxRotation(AFPOrientation.ori270);
    obp.setRepeatingGroup(rg);
    handler.handle(obp);

    GSCP_SetCurrentPosition gscp = new GSCP_SetCurrentPosition();
    gscp.setCoordinateX((short) 100);
    gscp.setCoordinateY((short) 200);

    handler.handle(new GAD_GraphicsData() {
      @Override
      public List<GAD_DrawingOrder> getDrawingOrders() {
        return List.of(gscp);
      }
    });

    EGR_EndGraphicsObject egr = new EGR_EndGraphicsObject();
    egr.setStructuredFieldIntroducer(createSfi(com.mgz.afp.enums.SFTypeID.EGR_EndGraphicsObject));
    handler.handle(egr);

    EPG_EndPage epg = new EPG_EndPage();
    epg.setStructuredFieldIntroducer(createSfi(com.mgz.afp.enums.SFTypeID.EPG_EndPage));
    epg.setName("P1");
    handler.handle(epg);

    handler.close();

    String contentString = getPdfContentString(baos);
    assertTrue(contentString.contains("1440 2880 cm"), "Content should contain the OBP origin translation: " + contentString);
    assertTrue(contentString.contains("-1 1"), "Content should contain rotation coefficients: " + contentString);
  }

  @Test
  public void testGocaGddScalingWithoutOapsSize() throws Exception {
    ByteArrayOutputStream baos = new ByteArrayOutputStream();
    PdfHandler handler = new PdfHandler(baos);

    BPG_BeginPage bpg = new BPG_BeginPage();
    bpg.setStructuredFieldIntroducer(createSfi(com.mgz.afp.enums.SFTypeID.BPG_BeginPage));
    bpg.setName("P1");
    handler.handle(bpg);

    BGR_BeginGraphicsObject bgr = new BGR_BeginGraphicsObject();
    bgr.setStructuredFieldIntroducer(createSfi(com.mgz.afp.enums.SFTypeID.BGR_BeginGraphicsObject));
    handler.handle(bgr);

    // OBP
    OBP_ObjectAreaPosition obp = new OBP_ObjectAreaPosition();
    obp.setStructuredFieldIntroducer(createSfi(com.mgz.afp.enums.SFTypeID.OBP_ObjectAreaPosition));
    OBP_ObjectAreaPosition.OBP_RepeatingGroup rg = new OBP_ObjectAreaPosition.OBP_RepeatingGroup();
    rg.setxOrigin(1000);
    rg.setyOrigin(2000);
    rg.setxRotation(AFPOrientation.ori0);
    obp.setRepeatingGroup(rg);
    handler.handle(obp);

    // GDD but NO OBD (so hasGocaOapsSize is false)
    GDD_GraphicsDataDescriptor gdd = new GDD_GraphicsDataDescriptor();
    gdd.setStructuredFieldIntroducer(createSfi(com.mgz.afp.enums.SFTypeID.GDD_GraphicsDataDescriptor));
    GDD_Parameter.WindowSpecification win = new GDD_Parameter.WindowSpecification();
    win.setLeftEdgeOfGPSWindow(100);
    win.setRightEdgeOfGPSWindow(600);
    win.setBottomEdgeOfGPSWindow(50);
    win.setTopEdgeOfGPSWindow(550);
    gdd.setGddParameters(List.of(win));
    handler.handle(gdd);

    GSCP_SetCurrentPosition gscp = new GSCP_SetCurrentPosition();
    gscp.setCoordinateX((short) 100);
    gscp.setCoordinateY((short) 200);

    handler.handle(new GAD_GraphicsData() {
      @Override
      public List<GAD_DrawingOrder> getDrawingOrders() {
        return List.of(gscp);
      }
    });

    EGR_EndGraphicsObject egr = new EGR_EndGraphicsObject();
    egr.setStructuredFieldIntroducer(createSfi(com.mgz.afp.enums.SFTypeID.EGR_EndGraphicsObject));
    handler.handle(egr);

    EPG_EndPage epg = new EPG_EndPage();
    epg.setStructuredFieldIntroducer(createSfi(com.mgz.afp.enums.SFTypeID.EPG_EndPage));
    epg.setName("P1");
    handler.handle(epg);

    handler.close();

    String contentString = getPdfContentString(baos);
    assertTrue(contentString.contains("1 0 0 -1 -100 550 cm"), "Content should contain scaling matrix: " + contentString);
  }

  @Test
  public void testGocaFallbackBehavior() throws Exception {
    ByteArrayOutputStream baos = new ByteArrayOutputStream();
    PdfHandler handler = new PdfHandler(baos);

    BPG_BeginPage bpg = new BPG_BeginPage();
    bpg.setStructuredFieldIntroducer(createSfi(com.mgz.afp.enums.SFTypeID.BPG_BeginPage));
    bpg.setName("P1");
    handler.handle(bpg);

    BGR_BeginGraphicsObject bgr = new BGR_BeginGraphicsObject();
    bgr.setStructuredFieldIntroducer(createSfi(com.mgz.afp.enums.SFTypeID.BGR_BeginGraphicsObject));
    handler.handle(bgr);

    // No OBD, OBP, or GDD at all

    GSCP_SetCurrentPosition gscp = new GSCP_SetCurrentPosition();
    gscp.setCoordinateX((short) 100);
    gscp.setCoordinateY((short) 200);

    handler.handle(new GAD_GraphicsData() {
      @Override
      public List<GAD_DrawingOrder> getDrawingOrders() {
        return List.of(gscp);
      }
    });

    EGR_EndGraphicsObject egr = new EGR_EndGraphicsObject();
    egr.setStructuredFieldIntroducer(createSfi(com.mgz.afp.enums.SFTypeID.EGR_EndGraphicsObject));
    handler.handle(egr);

    EPG_EndPage epg = new EPG_EndPage();
    epg.setStructuredFieldIntroducer(createSfi(com.mgz.afp.enums.SFTypeID.EPG_EndPage));
    epg.setName("P1");
    handler.handle(epg);

    handler.close();

    String contentString = getPdfContentString(baos);
    assertTrue(contentString.contains("1 0 0 1 0 0 cm"), "Content should contain fallback translation of (0,0): " + contentString);
  }

  @Test
  public void testGocaPositioningAndScaling() throws Exception {
    ByteArrayOutputStream baos = new ByteArrayOutputStream();
    PdfHandler handler = new PdfHandler(baos);

    // 1. Begin Page
    BPG_BeginPage bpg = new BPG_BeginPage();
    bpg.setName("P1");
    handler.handle(bpg);

    // 2. Begin Graphics Object
    BGR_BeginGraphicsObject bgr = new BGR_BeginGraphicsObject();
    handler.handle(bgr);

    // 3. Object Area Descriptor with ObjectAreaSize triplet
    OBD_ObjectAreaDescriptor obd = new OBD_ObjectAreaDescriptor();
    Triplet.ObjectAreaSize oas = new Triplet.ObjectAreaSize();
    oas.sizeType_0x02 = 0x02;
    oas.xSize = 1000;
    oas.ySize = 1000;
    obd.addTriplet(oas);
    handler.handle(obd);

    // 4. Object Area Position
    OBP_ObjectAreaPosition obp = new OBP_ObjectAreaPosition();
    OBP_ObjectAreaPosition.OBP_RepeatingGroup rg = new OBP_ObjectAreaPosition.OBP_RepeatingGroup();
    rg.setxOrigin(1440);
    rg.setyOrigin(2880);
    rg.setxRotation(AFPOrientation.ori90);
    obp.setRepeatingGroup(rg);
    handler.handle(obp);

    // 5. Graphics Data Descriptor with WindowSpecification parameter
    GDD_GraphicsDataDescriptor gdd = new GDD_GraphicsDataDescriptor();
    GDD_Parameter.WindowSpecification win = new GDD_Parameter.WindowSpecification();
    win.setLeftEdgeOfGPSWindow(0);
    win.setRightEdgeOfGPSWindow(500);
    win.setBottomEdgeOfGPSWindow(0);
    win.setTopEdgeOfGPSWindow(500);
    gdd.setGddParameters(List.of(win));
    handler.handle(gdd);

    // 6. Graphics Data
    GSCP_SetCurrentPosition gscp = new GSCP_SetCurrentPosition();
    gscp.setCoordinateX((short) 100);
    gscp.setCoordinateY((short) 200);

    handler.handle(new GAD_GraphicsData() {
      @Override
      public List<GAD_DrawingOrder> getDrawingOrders() {
        return List.of(gscp);
      }
    });

    // 7. End Graphics Object
    EGR_EndGraphicsObject egr = new EGR_EndGraphicsObject();
    handler.handle(egr);

    // 8. End Page
    EPG_EndPage epg = new EPG_EndPage();
    handler.handle(epg);

    assertDoesNotThrow(() -> handler.close());
  }

  @Test
  public void testGocaPositioningWithNoObdAndNoGdd() throws Exception {
    ByteArrayOutputStream baos = new ByteArrayOutputStream();
    PdfHandler handler = new PdfHandler(baos);

    // 1. Begin Page
    BPG_BeginPage bpg = new BPG_BeginPage();
    bpg.setName("P1");
    handler.handle(bpg);

    // 2. Begin Graphics Object
    BGR_BeginGraphicsObject bgr = new BGR_BeginGraphicsObject();
    handler.handle(bgr);

    // 3. Object Area Position only
    OBP_ObjectAreaPosition obp = new OBP_ObjectAreaPosition();
    OBP_ObjectAreaPosition.OBP_RepeatingGroup rg = new OBP_ObjectAreaPosition.OBP_RepeatingGroup();
    rg.setxOrigin(1000);
    rg.setyOrigin(1000);
    rg.setxRotation(AFPOrientation.ori0);
    obp.setRepeatingGroup(rg);
    handler.handle(obp);

    // 4. Graphics Data
    GSCP_SetCurrentPosition gscp = new GSCP_SetCurrentPosition();
    gscp.setCoordinateX((short) 50);
    gscp.setCoordinateY((short) 50);

    handler.handle(new GAD_GraphicsData() {
      @Override
      public List<GAD_DrawingOrder> getDrawingOrders() {
        return List.of(gscp);
      }
    });

    // 5. End Graphics Object
    EGR_EndGraphicsObject egr = new EGR_EndGraphicsObject();
    handler.handle(egr);

    // 6. End Page
    EPG_EndPage epg = new EPG_EndPage();
    handler.handle(epg);

    assertDoesNotThrow(() -> handler.close());
  }

  @Test
  public void testGocaSegmentStateScopingAndGsgch() throws Exception {
    ByteArrayOutputStream baos = new ByteArrayOutputStream();
    PdfHandler handler = new PdfHandler(baos);

    // 1. Begin Page
    BPG_BeginPage bpg = new BPG_BeginPage();
    bpg.setName("P1");
    handler.handle(bpg);

    // 2. Begin Graphics Object
    BGR_BeginGraphicsObject bgr = new BGR_BeginGraphicsObject();
    handler.handle(bgr);

    // 3. Set outer color to Blue (0x01) and currentX/Y to 10, 20
    GSCP_SetCurrentPosition gscpOuter = new GSCP_SetCurrentPosition();
    gscpOuter.setCoordinateX((short) 10);
    gscpOuter.setCoordinateY((short) 20);
    handler.handleDrawingOrder(gscpOuter);

    GSCOL_SetColor gscolOuter = new GSCOL_SetColor();
    gscolOuter.setColor(com.mgz.afp.enums.AFPColorValue.Blue_0x01);
    handler.handleDrawingOrder(gscolOuter);

    // 4. Create segment with various nested orders (GSGCH, GSCOL red, GSCP 100, 200)
    GBSEG_BeginSegment gbseg = new GBSEG_BeginSegment();
    gbseg.setNameOfSegment("SEG1");

    GSGCH_SegmentCharacteristics gsgch = new GSGCH_SegmentCharacteristics();
    gsgch.setIdentificationCode((short) 0x00);
    gsgch.setParameters(new byte[]{0x01});

    GSCOL_SetColor gscolInner = new GSCOL_SetColor();
    gscolInner.setColor(com.mgz.afp.enums.AFPColorValue.Red_0x02);

    GSCP_SetCurrentPosition gscpInner = new GSCP_SetCurrentPosition();
    gscpInner.setCoordinateX((short) 100);
    gscpInner.setCoordinateY((short) 200);

    gbseg.setDrawingOrders(List.of(gsgch, gscolInner, gscpInner));

    // Handle segment
    assertDoesNotThrow(() -> handler.handleDrawingOrder(gbseg));

    // 5. Verify graphics state scoping:
    // Color should have been restored to Blue_0x01 (isolated)
    org.junit.jupiter.api.Assertions.assertEquals(com.mgz.afp.enums.AFPColorValue.Blue_0x01, handler.getGraphicsState().getColor());
    // Cursor position should have propagated to 100, 200 (chained coordinate update)
    org.junit.jupiter.api.Assertions.assertEquals(100, handler.getGraphicsState().getCurrentX());
    org.junit.jupiter.api.Assertions.assertEquals(200, handler.getGraphicsState().getCurrentY());

    // 6. End Graphics Object and Page
    EGR_EndGraphicsObject egr = new EGR_EndGraphicsObject();
    handler.handle(egr);
    EPG_EndPage epg = new EPG_EndPage();
    handler.handle(epg);
    assertDoesNotThrow(() -> handler.close());
  }

  @Test
  public void testMultipleGocaSegmentsWithDuplicateNamesDoNotCollide() throws Exception {
    ByteArrayOutputStream baos = new ByteArrayOutputStream();
    PdfHandler handler = new PdfHandler(baos);

    // 1. Begin Page
    BPG_BeginPage bpg = new BPG_BeginPage();
    bpg.setName("P1");
    handler.handle(bpg);

    // 2. Begin Graphics Object
    BGR_BeginGraphicsObject bgr = new BGR_BeginGraphicsObject();
    handler.handle(bgr);

    // First Segment: name "DUPLICATE", draws a line
    GBSEG_BeginSegment gbseg1 = new GBSEG_BeginSegment();
    gbseg1.setNameOfSegment("DUPLICATE");
    GLINE_LineAtGivenPosition line = new GLINE_LineAtGivenPosition();
    line.setPoints(List.of(new GOCA_Point((short)10, (short)10), new GOCA_Point((short)100, (short)100)));
    gbseg1.setDrawingOrders(List.of(line));
    handler.handleDrawingOrder(gbseg1);

    // Second Segment: SAME name "DUPLICATE", draws a box
    GBSEG_BeginSegment gbseg2 = new GBSEG_BeginSegment();
    gbseg2.setNameOfSegment("DUPLICATE");
    GBOX_BoxAtGivenPosition box = new GBOX_BoxAtGivenPosition();
    box.setFirstCorner(new GOCA_Point((short)20, (short)20));
    box.setDiagonalCorner(new GOCA_Point((short)50, (short)50));
    gbseg2.setDrawingOrders(List.of(box));
    handler.handleDrawingOrder(gbseg2);

    // Third Segment: null name, draws a character string
    GBSEG_BeginSegment gbseg3 = new GBSEG_BeginSegment();
    gbseg3.setNameOfSegment(null);
    GCHST_CharacterStringAtGivenPosition text = new GCHST_CharacterStringAtGivenPosition();
    text.setOriginPoint(new GOCA_Point((short)30, (short)30));
    text.setText("TEST_TEXT");
    gbseg3.setDrawingOrders(List.of(text));
    handler.handleDrawingOrder(gbseg3);

    // End Graphics Object and Page
    EGR_EndGraphicsObject egr = new EGR_EndGraphicsObject();
    handler.handle(egr);
    EPG_EndPage epg = new EPG_EndPage();
    handler.handle(epg);
    handler.close();

    byte[] pdfBytes = baos.toByteArray();
    com.itextpdf.kernel.pdf.PdfReader reader = new com.itextpdf.kernel.pdf.PdfReader(new java.io.ByteArrayInputStream(pdfBytes));
    com.itextpdf.kernel.pdf.PdfDocument pdfDoc = new com.itextpdf.kernel.pdf.PdfDocument(reader);
    com.itextpdf.kernel.pdf.PdfPage page = pdfDoc.getPage(1);
    byte[] contentBytes = page.getContentBytes();
    String contentString = new String(contentBytes, java.nio.charset.StandardCharsets.UTF_8);

    // Verify both different drawing contents are present in the PDF content stream
    // 10 10 m and 100 100 l for line
    assertTrue(contentString.contains("10 10 m"), "Should contain line moveTo");
    assertTrue(contentString.contains("100 100 l"), "Should contain line lineTo");
    // 20 20 30 30 re ... for box
    assertTrue(contentString.contains("20 20 30 30 re"), "Should contain box rectangle");
    // Text string "TEST_TEXT"
    assertTrue(contentString.contains("TEST_TEXT"), "Should contain character string text");

    pdfDoc.close();
  }
}
