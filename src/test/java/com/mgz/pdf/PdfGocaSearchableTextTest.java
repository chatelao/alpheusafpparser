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
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.mgz.afp.enums.AFPOrientation;
import com.mgz.afp.enums.AFPUnitBase;
import com.mgz.afp.enums.SFTypeID;
import com.mgz.afp.goca.BGR_BeginGraphicsObject;
import com.mgz.afp.goca.EGR_EndGraphicsObject;
import com.mgz.afp.goca.GDD_GraphicsDataDescriptor;
import com.mgz.afp.goca.GDD_Parameter;
import com.mgz.afp.goca.GAD_GraphicsData;
import com.mgz.afp.goca.GAD_DrawingOrder;
import com.mgz.afp.goca.GAD_DrawingOrder.GCHST_CharacterStringAtGivenPosition;
import com.mgz.afp.goca.GAD_DrawingOrder.GOCA_Point;
import com.mgz.afp.modca.OBD_ObjectAreaDescriptor;
import com.mgz.afp.modca.OBP_ObjectAreaPosition;
import com.mgz.afp.modca.BPG_BeginPage;
import com.mgz.afp.modca.EPG_EndPage;
import com.mgz.afp.modca.PGD_PageDescriptor;
import com.mgz.afp.triplets.Triplet;
import com.mgz.afp.base.StructuredFieldIntroducer;
import com.mgz.afp.parser.AFPParserConfiguration;
import com.mgz.cli.Afp2Xml;

import org.apache.pdfbox.Loader;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.File;
import java.io.FileOutputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.EnumSet;
import java.util.List;

/**
 * End-to-end test case creating an AFP file programmatically with 5 GOCA graphics,
 * each containing distinct searchable text. It then transforms the AFP file to XML and PDF,
 * and verifies both searchability of the text and correct positioning in the PDF.
 */
public class PdfGocaSearchableTextTest {

  @TempDir
  Path tempDir;

  private StructuredFieldIntroducer createSfi(SFTypeID typeID) {
    StructuredFieldIntroducer sfi = new StructuredFieldIntroducer();
    sfi.setSFTypeID(typeID);
    sfi.setFlagByte(EnumSet.noneOf(com.mgz.afp.enums.SFFlag.class));
    return sfi;
  }

  @Test
  public void testFiveGocaGraphicsSearchableText() throws Exception {
    File afpFile = tempDir.resolve("goca_5text_test.afp").toFile();
    AFPParserConfiguration config = new AFPParserConfiguration();

    // Distinct text and positions
    String[] expectedTexts = {
        "GraphicOneText",
        "GraphicTwoText",
        "GraphicThreeText",
        "GraphicFourText",
        "GraphicFiveText"
    };

    int[] xOrigins = { 1000, 2500, 4000, 5500, 7000 };
    int[] yOrigins = { 1500, 3000, 4500, 6000, 7500 };

    try (FileOutputStream fos = new FileOutputStream(afpFile)) {
      // 1. Begin Page
      BPG_BeginPage bpg = new BPG_BeginPage();
      bpg.setStructuredFieldIntroducer(createSfi(SFTypeID.BPG_BeginPage));
      bpg.setName("P1");
      bpg.writeAFP(fos, config);

      // 2. Page Descriptor (PGD)
      PGD_PageDescriptor pgd = new PGD_PageDescriptor();
      pgd.setStructuredFieldIntroducer(createSfi(SFTypeID.PGD_PageDescriptor));
      pgd.setxUnitBase(AFPUnitBase.Inches10);
      pgd.setyUnitBase(AFPUnitBase.Inches10);
      pgd.setxUnitsPerUnitBase((short) 14400);
      pgd.setyUnitsPerUnitBase((short) 14400);
      pgd.setxSize(14400);
      pgd.setySize(14400);
      pgd.setReserved12_14(new byte[3]); // Avoid NullPointerException in writeAFP
      pgd.writeAFP(fos, config);

      for (int i = 0; i < 5; i++) {
        // BGR - Begin Graphics Object
        BGR_BeginGraphicsObject bgr = new BGR_BeginGraphicsObject();
        bgr.setStructuredFieldIntroducer(createSfi(SFTypeID.BGR_BeginGraphicsObject));
        bgr.setName("GR" + i);
        bgr.writeAFP(fos, config);

        // OBD - Object Area Descriptor (OAPS Size)
        OBD_ObjectAreaDescriptor obd = new OBD_ObjectAreaDescriptor();
        obd.setStructuredFieldIntroducer(createSfi(SFTypeID.OBD_ObjectAreaDescriptor));
        Triplet.ObjectAreaSize oas = new Triplet.ObjectAreaSize();
        oas.setTripletID(Triplet.TripletID.ObjectAreaSize); // Set ID to avoid NullPointerException in writeAFP
        oas.sizeType_0x02 = 0x02;
        oas.xSize = 1000;
        oas.ySize = 1000;
        obd.addTriplet(oas);
        obd.writeAFP(fos, config);

        // OBP - Object Area Position (OBP Origins)
        OBP_ObjectAreaPosition obp = new OBP_ObjectAreaPosition();
        obp.setStructuredFieldIntroducer(createSfi(SFTypeID.OBP_ObjectAreaPosition));
        OBP_ObjectAreaPosition.OBP_RepeatingGroup rg = new OBP_ObjectAreaPosition.OBP_RepeatingGroup();
        rg.setxOrigin(xOrigins[i]);
        rg.setyOrigin(yOrigins[i]);
        rg.setxRotation(AFPOrientation.ori0);
        obp.setRepeatingGroup(rg);
        obp.writeAFP(fos, config);

        // GDD - Graphics Data Descriptor (Window specification)
        GDD_GraphicsDataDescriptor gdd = new GDD_GraphicsDataDescriptor();
        gdd.setStructuredFieldIntroducer(createSfi(SFTypeID.GDD_GraphicsDataDescriptor));
        GDD_Parameter.WindowSpecification win = new GDD_Parameter.WindowSpecification();
        win.setLeftEdgeOfGPSWindow(0);
        win.setRightEdgeOfGPSWindow(1000);
        win.setBottomEdgeOfGPSWindow(0);
        win.setTopEdgeOfGPSWindow(1000);
        gdd.setGddParameters(List.of(win));
        gdd.writeAFP(fos, config);

        // GAD - Graphics Data with drawing order (GCHST)
        GAD_GraphicsData gad = new GAD_GraphicsData();
        gad.setStructuredFieldIntroducer(createSfi(SFTypeID.GAD_GraphicsData));
        GCHST_CharacterStringAtGivenPosition gchst = new GCHST_CharacterStringAtGivenPosition();
        gchst.setDrawingOrderType((short) 0xC3);
        gchst.setOriginPoint(new GOCA_Point((short) 100, (short) 200));
        gchst.setCodePoints(expectedTexts[i].getBytes("Cp500"));
        gchst.setText(expectedTexts[i]);
        gad.setDrawingOrders(List.of(gchst));
        gad.writeAFP(fos, config);

        // EGR - End Graphics Object
        EGR_EndGraphicsObject egr = new EGR_EndGraphicsObject();
        egr.setStructuredFieldIntroducer(createSfi(SFTypeID.EGR_EndGraphicsObject));
        egr.setName("GR" + i);
        egr.writeAFP(fos, config);
      }

      // EPG - End Page
      EPG_EndPage epg = new EPG_EndPage();
      epg.setStructuredFieldIntroducer(createSfi(SFTypeID.EPG_EndPage));
      epg.setName("P1");
      epg.writeAFP(fos, config);
    }

    // Transform to XML
    File xmlFile = tempDir.resolve("output.xml").toFile();
    String[] xmlArgs = {
        "-f", "xml",
        afpFile.getAbsolutePath(),
        xmlFile.getAbsolutePath()
    };
    int xmlResult = Afp2Xml.execute(xmlArgs);
    assertEquals(0, xmlResult, "XML Conversion should succeed");
    assertTrue(xmlFile.exists(), "XML Output should exist");

    // Verify XML contents
    String xmlContent = Files.readString(xmlFile.toPath());
    for (String expectedText : expectedTexts) {
      assertTrue(xmlContent.contains(expectedText), "XML should contain searchable text: " + expectedText);
    }
    assertTrue(xmlContent.contains("GCHST_CharacterStringAtGivenPosition"), "XML should contain GCHST tag");

    // Transform to PDF
    File pdfFile = tempDir.resolve("output.pdf").toFile();
    String[] pdfArgs = {
        "-f", "pdf",
        afpFile.getAbsolutePath(),
        pdfFile.getAbsolutePath()
    };
    int pdfResult = Afp2Xml.execute(pdfArgs);
    assertEquals(0, pdfResult, "PDF Conversion should succeed");
    assertTrue(pdfFile.exists(), "PDF Output should exist");

    // Verify PDF searchable text using PDFBox
    try (PDDocument document = Loader.loadPDF(pdfFile)) {
      PDFTextStripper stripper = new PDFTextStripper();
      String pdfText = stripper.getText(document);
      for (String expectedText : expectedTexts) {
        assertTrue(pdfText.contains(expectedText), "PDF should contain searchable text: " + expectedText);
      }
    }

    // Verify positioning of the graphics inside the PDF as expected using iText
    com.itextpdf.kernel.pdf.PdfReader reader = new com.itextpdf.kernel.pdf.PdfReader(
        new java.io.ByteArrayInputStream(Files.readAllBytes(pdfFile.toPath()))
    );
    com.itextpdf.kernel.pdf.PdfDocument pdfDoc = new com.itextpdf.kernel.pdf.PdfDocument(reader);
    com.itextpdf.kernel.pdf.PdfPage page = pdfDoc.getPage(1);
    byte[] contentBytes = page.getContentBytes();
    String contentString = new String(contentBytes, StandardCharsets.UTF_8);
    pdfDoc.close();

    // Verify each graphic's OBP origin is correctly applied via matrix transformation (cm operator)
    for (int i = 0; i < 5; i++) {
      String expectedCm = "1 0 0 1 " + xOrigins[i] + " " + yOrigins[i] + " cm";
      assertTrue(contentString.contains(expectedCm), "PDF content stream should contain translation matrix: " + expectedCm);
    }
  }
}
