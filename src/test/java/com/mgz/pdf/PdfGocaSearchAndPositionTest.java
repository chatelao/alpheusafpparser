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

import com.mgz.cli.Afp2Xml;
import com.mgz.afp.base.StructuredField;
import com.mgz.afp.base.StructuredFieldIntroducer;
import com.mgz.afp.enums.SFTypeID;
import com.mgz.afp.enums.SFFlag;
import com.mgz.afp.enums.AFPOrientation;
import com.mgz.afp.enums.AFPUnitBase;
import com.mgz.afp.enums.AFPReferenceCoordinateSystem;
import com.mgz.afp.modca.BPG_BeginPage;
import com.mgz.afp.modca.EPG_EndPage;
import com.mgz.afp.modca.PGD_PageDescriptor;
import com.mgz.afp.modca.OBD_ObjectAreaDescriptor;
import com.mgz.afp.modca.OBP_ObjectAreaPosition;
import com.mgz.afp.goca.BGR_BeginGraphicsObject;
import com.mgz.afp.goca.EGR_EndGraphicsObject;
import com.mgz.afp.goca.GDD_GraphicsDataDescriptor;
import com.mgz.afp.goca.GDD_Parameter;
import com.mgz.afp.goca.GAD_GraphicsData;
import com.mgz.afp.goca.GAD_DrawingOrder;
import com.mgz.afp.goca.GAD_DrawingOrder.GSCP_SetCurrentPosition;
import com.mgz.afp.goca.GAD_DrawingOrder.GSCOL_SetColor;
import com.mgz.afp.goca.GAD_DrawingOrder.GCHST_CharacterStringAtGivenPosition;
import com.mgz.afp.goca.GAD_DrawingOrder.GOCA_Point;
import com.mgz.afp.triplets.Triplet;
import com.mgz.afp.parser.AFPParserConfiguration;

import org.apache.pdfbox.Loader;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.apache.pdfbox.text.TextPosition;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;

/**
 * Creates an AFP file with 5 graphics objects and 5 different text strings inside,
 * converts to XML and PDF, and verifies text readability, searchability, and positioning.
 */
public class PdfGocaSearchAndPositionTest {

  @TempDir
  Path tempDir;

  private StructuredFieldIntroducer createSfi(SFTypeID typeID) {
    StructuredFieldIntroducer sfi = new StructuredFieldIntroducer();
    sfi.setSFTypeID(typeID);
    sfi.setFlagByte(EnumSet.noneOf(SFFlag.class));
    return sfi;
  }

  @Test
  public void testGocaSearchAndPosition() throws Exception {
    File afpFile = tempDir.resolve("graphics_searchable.afp").toFile();
    File pdfFile = tempDir.resolve("graphics_searchable.pdf").toFile();
    File xmlFile = tempDir.resolve("graphics_searchable.xml").toFile();

    AFPParserConfiguration config = new AFPParserConfiguration();

    // 1. Programmatically assemble structured fields for AFP Document
    List<StructuredField> fields = new ArrayList<>();

    // Begin Page
    BPG_BeginPage bpg = new BPG_BeginPage();
    bpg.setStructuredFieldIntroducer(createSfi(SFTypeID.BPG_BeginPage));
    bpg.setName("P1");
    fields.add(bpg);

    // Page Descriptor
    PGD_PageDescriptor pgd = new PGD_PageDescriptor();
    pgd.setStructuredFieldIntroducer(createSfi(SFTypeID.PGD_PageDescriptor));
    pgd.setxUnitBase(AFPUnitBase.Inches10);
    pgd.setyUnitBase(AFPUnitBase.Inches10);
    pgd.setxUnitsPerUnitBase((short) 1440);
    pgd.setyUnitsPerUnitBase((short) 1440);
    pgd.setxSize(11520); // 8 inches
    pgd.setySize(15840); // 11 inches
    pgd.setReserved12_14(new byte[3]);
    fields.add(pgd);

    // Add 5 Graphics Objects
    for (int i = 1; i <= 5; i++) {
      int yOrigin = 2000 * i; // Spaced out vertically

      // Begin Graphics
      BGR_BeginGraphicsObject bgr = new BGR_BeginGraphicsObject();
      bgr.setStructuredFieldIntroducer(createSfi(SFTypeID.BGR_BeginGraphicsObject));
      bgr.setName("GRAP" + i);
      fields.add(bgr);

      // Object Area Descriptor
      OBD_ObjectAreaDescriptor obd = new OBD_ObjectAreaDescriptor();
      obd.setStructuredFieldIntroducer(createSfi(SFTypeID.OBD_ObjectAreaDescriptor));
      Triplet.ObjectAreaSize oas = new Triplet.ObjectAreaSize();
      oas.setTripletID(Triplet.TripletID.ObjectAreaSize);
      oas.sizeType_0x02 = 0x02;
      oas.xSize = 1000;
      oas.ySize = 1000;
      obd.addTriplet(oas);
      fields.add(obd);

      // Object Area Position
      OBP_ObjectAreaPosition obp = new OBP_ObjectAreaPosition();
      obp.setStructuredFieldIntroducer(createSfi(SFTypeID.OBP_ObjectAreaPosition));
      obp.setObjectAreaPositionID((byte) 1);
      OBP_ObjectAreaPosition.OBP_RepeatingGroup rg = new OBP_ObjectAreaPosition.OBP_RepeatingGroup();
      rg.setRepeatingGroupLength((byte) 23);
      rg.setxOrigin(1000);
      rg.setyOrigin(yOrigin);
      rg.setxRotation(AFPOrientation.ori0);
      rg.setyRotation(AFPOrientation.ori90);
      rg.setReserved11((byte) 0);
      rg.setxOriginOfContent(0);
      rg.setyOriginOfContent(0);
      rg.setxRotationOfContent(AFPOrientation.ori0);
      rg.setyRotationOfContent(AFPOrientation.ori90);
      rg.setReferenceCoordinateSystem(AFPReferenceCoordinateSystem.Standard);
      obp.setRepeatingGroup(rg);
      fields.add(obp);

      // Graphics Data Descriptor
      GDD_GraphicsDataDescriptor gdd = new GDD_GraphicsDataDescriptor();
      gdd.setStructuredFieldIntroducer(createSfi(SFTypeID.GDD_GraphicsDataDescriptor));
      GDD_Parameter.WindowSpecification win = new GDD_Parameter.WindowSpecification();
      win.setUnitBaseGPS(AFPUnitBase.Inches10);
      win.setUnitsPerUnitBaseX(14400);
      win.setUnitsPerUnitBaseY(14400);
      win.setImageResolutionXY(0);
      win.setLeftEdgeOfGPSWindow(0);
      win.setRightEdgeOfGPSWindow(1000);
      win.setBottomEdgeOfGPSWindow(0);
      win.setTopEdgeOfGPSWindow(1000);
      gdd.setGddParameters(List.of(win));
      fields.add(gdd);

      // Graphics Data with unique text drawing order
      GAD_GraphicsData gad = new GAD_GraphicsData();
      gad.setStructuredFieldIntroducer(createSfi(SFTypeID.GAD_GraphicsData));

      List<GAD_DrawingOrder> orders = new ArrayList<>();

      // Set color (e.g. Blue)
      GSCOL_SetColor col = new GSCOL_SetColor();
      col.setColor(com.mgz.afp.enums.AFPColorValue.Blue_0x01);
      orders.add(col);

      // Set current position to (10, 10)
      GSCP_SetCurrentPosition pos = new GSCP_SetCurrentPosition();
      pos.setCoordinateX((short) 10);
      pos.setCoordinateY((short) 10);
      orders.add(pos);

      // Draw Character String at relative (100, 100)
      GCHST_CharacterStringAtGivenPosition textOrder = new GCHST_CharacterStringAtGivenPosition();
      textOrder.setDrawingOrderType((short) 0xC3);
      textOrder.setOriginPoint(new GOCA_Point((short) 100, (short) 100));
      String stringToDraw = "GraphicText" + i;
      byte[] textBytes = stringToDraw.getBytes(config.getAfpCharSet());
      textOrder.setCodePoints(textBytes);
      textOrder.setText(stringToDraw);
      orders.add(textOrder);

      gad.setDrawingOrders(orders);
      fields.add(gad);

      // End Graphics
      EGR_EndGraphicsObject egr = new EGR_EndGraphicsObject();
      egr.setStructuredFieldIntroducer(createSfi(SFTypeID.EGR_EndGraphicsObject));
      egr.setName("GRAP" + i);
      fields.add(egr);
    }

    // End Page
    EPG_EndPage epg = new EPG_EndPage();
    epg.setStructuredFieldIntroducer(createSfi(SFTypeID.EPG_EndPage));
    epg.setName("P1");
    fields.add(epg);

    // Write structured fields to binary AFP file
    try (FileOutputStream fos = new FileOutputStream(afpFile)) {
      for (StructuredField sf : fields) {
        sf.writeAFP(fos, config);
      }
    }

    assertTrue(afpFile.exists(), "AFP file should exist");
    assertTrue(afpFile.length() > 0, "AFP file should not be empty");

    // 2. Transform the AFP file to PDF
    String[] pdfArgs = {
        "-f", "pdf",
        afpFile.getAbsolutePath(),
        pdfFile.getAbsolutePath()
    };
    int pdfResult = Afp2Xml.execute(pdfArgs);
    assertEquals(0, pdfResult, "AFP to PDF conversion should succeed");
    assertTrue(pdfFile.exists(), "PDF file should exist");
    assertTrue(pdfFile.length() > 0, "PDF file should not be empty");

    // 3. Transform the AFP file to XML
    String[] xmlArgs = {
        "-f", "xml",
        afpFile.getAbsolutePath(),
        xmlFile.getAbsolutePath()
    };
    int xmlResult = Afp2Xml.execute(xmlArgs);
    assertEquals(0, xmlResult, "AFP to XML conversion should succeed");
    assertTrue(xmlFile.exists(), "XML file should exist");
    assertTrue(xmlFile.length() > 0, "XML file should not be empty");

    // 4. Validate XML content
    String xmlContent = Files.readString(xmlFile.toPath());
    System.out.println("XML CONTENT:\n" + xmlContent);
    for (int i = 1; i <= 5; i++) {
      assertTrue(xmlContent.contains("GRAP" + i), "XML should contain graphic object GRAP" + i);
      assertTrue(xmlContent.contains("GraphicText" + i), "XML should contain GraphicText" + i);
    }

    // 5. Validate PDF content searchability and positioning
    try (PDDocument document = Loader.loadPDF(pdfFile)) {
      assertEquals(1, document.getNumberOfPages(), "PDF should have exactly 1 page");

      PositionTrackingTextStripper stripper = new PositionTrackingTextStripper();
      stripper.getText(document);
      List<MatchedText> matches = stripper.getMatches();

      // We expect to find all 5 texts: GraphicText1 to GraphicText5
      assertEquals(5, matches.size(), "Should find exactly 5 text elements in PDF");

      for (int i = 0; i < 5; i++) {
        int expectedIndex = i + 1;
        MatchedText match = matches.get(i);
        assertEquals("GraphicText" + expectedIndex, match.text, "Extracted text mismatch at index " + i);

        // Horizontal coordinate (X) should be roughly the same for all as we set same xOrigin and same relative x
        assertTrue(match.x > 0, "Text X coordinate should be positive");

        // Vertical coordinates (Y) should be sequentially placed down the page
        if (i > 0) {
          MatchedText prev = matches.get(i - 1);
          // Since yOrigin increases (2000 * i), the vertical coordinates in PDF space should also increase or decrease sequentially.
          // Let's assert that the Y coordinates are strictly monotonic (distinct and different)
          assertTrue(match.y != prev.y, "Adjacent texts should have different Y coordinates");
          assertTrue(match.y > prev.y, "Y coordinates should be increasing due to sequential yOrigins");
        }
      }
    }
  }

  private static class PositionTrackingTextStripper extends PDFTextStripper {
    private final List<MatchedText> matches = new ArrayList<>();

    public PositionTrackingTextStripper() throws IOException {
      super();
    }

    @Override
    protected void writeString(String string, List<TextPosition> textPositions) throws IOException {
      if (string != null && !string.trim().isEmpty()) {
        TextPosition first = textPositions.get(0);
        matches.add(new MatchedText(string.trim(), first.getXDirAdj(), first.getYDirAdj()));
      }
      super.writeString(string, textPositions);
    }

    public List<MatchedText> getMatches() {
      return matches;
    }
  }

  private static class MatchedText {
    String text;
    float x;
    float y;

    public MatchedText(String text, float x, float y) {
      this.text = text;
      this.x = x;
      this.y = y;
    }
  }
}
