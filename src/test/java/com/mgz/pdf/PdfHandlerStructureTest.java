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

import com.mgz.afp.base.StructuredField;
import com.mgz.afp.base.StructuredFieldIntroducer;
import com.mgz.afp.enums.AFPUnitBase;
import com.mgz.afp.enums.SFTypeID;
import com.mgz.afp.goca.GAD_DrawingOrder;
import com.mgz.afp.goca.GAD_DrawingOrder.GBAR_BeginArea;
import com.mgz.afp.goca.GAD_DrawingOrder.GCFARC_FullArcAtCurrentPosition;
import com.mgz.afp.goca.GAD_DrawingOrder.GEAR_EndArea;
import com.mgz.afp.goca.GAD_DrawingOrder.GFARC_FullArcAtGivenPosition;
import com.mgz.afp.goca.GAD_DrawingOrder.GCLINE_LineAtCurrentPosition;
import com.mgz.afp.goca.GAD_DrawingOrder.GBOX_BoxAtGivenPosition;
import com.mgz.afp.goca.GAD_DrawingOrder.GCBOX_BoxAtCurrentPosition;
import com.mgz.afp.goca.GAD_DrawingOrder.GCRLINE_RelativeLineAtCurrentPosition;
import com.mgz.afp.goca.GAD_DrawingOrder.GLINE_LineAtGivenPosition;
import com.mgz.afp.goca.GAD_DrawingOrder.GRLINE_RelativeLineAtGivenPosition;
import com.mgz.afp.goca.GAD_DrawingOrder.GSCOL_SetColor;
import com.mgz.afp.goca.GAD_DrawingOrder.GSCP_SetCurrentPosition;
import com.mgz.afp.goca.GAD_DrawingOrder.GSAP_SetArcParameters;
import com.mgz.afp.goca.GAD_DrawingOrder.GSPCOL_SetProcessColor;
import com.mgz.afp.goca.GAD_DrawingOrder.GSECOL_SetExtendedColor;
import com.mgz.afp.goca.GAD_DrawingOrder.GSLE_SetLineEnd;
import com.mgz.afp.goca.GAD_DrawingOrder.GSLJ_SetLineJoin;
import com.mgz.afp.goca.GAD_DrawingOrder.GSLT_SetLineType;
import com.mgz.afp.goca.GAD_DrawingOrder.GSLW_SetLineWidth;
import com.mgz.afp.goca.GAD_GraphicsData;
import com.mgz.afp.modca.BDT_BeginDocument;
import com.mgz.afp.modca.BPG_BeginPage;
import com.mgz.afp.modca.EDT_EndDocument;
import com.mgz.afp.modca.EPG_EndPage;
import com.mgz.afp.modca.MCF_MapCodedFont_Format1;
import com.mgz.afp.modca.MCF_MapCodedFont_Format2;
import com.mgz.afp.modca.MMO_MapMediumOverlay;
import com.mgz.afp.modca.MPS_MapPageSegment;
import com.mgz.afp.modca.PGD_PageDescriptor;
import com.mgz.afp.modca.TLE_TagLogicalElement;
import com.mgz.afp.ptoca.PTX_PresentationTextData;
import com.mgz.afp.ptoca.controlSequence.PTOCAControlSequence.AMB_AbsoluteMoveBaseline;
import com.mgz.afp.ptoca.controlSequence.PTOCAControlSequence.AMI_AbsoluteMoveInline;
import com.mgz.afp.ptoca.controlSequence.PTOCAControlSequence.RMB_RelativeMoveBaseline;
import com.mgz.afp.ptoca.controlSequence.PTOCAControlSequence.RMI_RelativeMoveInline;
import com.mgz.afp.ptoca.controlSequence.PTOCAControlSequence.SBI_SetBaselineIncrement;
import com.mgz.afp.ptoca.controlSequence.PTOCAControlSequence.SCFL_SetCodedFontLocal;
import com.mgz.afp.ptoca.controlSequence.PTOCAControlSequence.SIA_SetIntercharacterAdjustment;
import com.mgz.afp.ptoca.controlSequence.PTOCAControlSequence.SIM_SetInlineMargin;
import com.mgz.afp.ptoca.controlSequence.PTOCAControlSequence.STC_SetTextColor;
import com.mgz.afp.ptoca.controlSequence.PTOCAControlSequence.BLN_BeginLine;
import com.mgz.afp.ptoca.controlSequence.PTOCAControlSequence.STO_SetTextOrientation;
import com.mgz.afp.ptoca.controlSequence.PTOCAControlSequence.SVI_SetVariableSpaceCharacterIncrement;
import com.mgz.afp.ptoca.controlSequence.PTOCAControlSequence.TRN_TransparentData;
import com.mgz.afp.ptoca.controlSequence.PTOCAControlSequence.UCT_UnicodeComplexText;
import com.mgz.afp.ptoca.controlSequence.PTOCAControlSequence.GraphicCharacters;
import com.mgz.afp.ptoca.controlSequence.PTOCAControlSequence.ControlSequenceIntroducer;
import com.mgz.afp.ptoca.controlSequence.PTOCAControlSequence.ControlSequenceFunctionType;
import com.mgz.afp.parser.AFPParserConfiguration;
import com.mgz.afp.enums.AFPColorSpace;
import com.mgz.afp.enums.AFPColorValue;
import com.mgz.afp.enums.AFPOrientation;
import com.mgz.afp.triplets.Triplet;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Verifies that {@link PdfHandler} correctly tracks MO:DCA structural boundaries.
 */
public class PdfHandlerStructureTest {

  @Test
  public void testStructureDepthTracking() throws Exception {
    PdfHandler handler = new PdfHandler(new java.io.ByteArrayOutputStream());

    BDT_BeginDocument bdt = new BDT_BeginDocument();
    bdt.setStructuredFieldIntroducer(createSfi(SFTypeID.BDT_BeginDocument));

    BPG_BeginPage bpg = new BPG_BeginPage();
    bpg.setStructuredFieldIntroducer(createSfi(SFTypeID.BPG_BeginPage));

    EPG_EndPage epg = new EPG_EndPage();
    epg.setStructuredFieldIntroducer(createSfi(SFTypeID.EPG_EndPage));

    EDT_EndDocument edt = new EDT_EndDocument();
    edt.setStructuredFieldIntroducer(createSfi(SFTypeID.EDT_EndDocument));

    assertEquals(0, handler.getStructureDepth());

    handler.handle(bdt);
    assertEquals(1, handler.getStructureDepth());

    handler.handle(bpg);
    assertEquals(2, handler.getStructureDepth());

    handler.handle(epg);
    assertEquals(1, handler.getStructureDepth());

    handler.handle(edt);
    assertEquals(0, handler.getStructureDepth());
  }

  @Test
  public void testTextRenderingState() throws Exception {
    PdfHandler handler = new PdfHandler(new java.io.ByteArrayOutputStream());

    BPG_BeginPage bpg = new BPG_BeginPage();
    bpg.setStructuredFieldIntroducer(createSfi(SFTypeID.BPG_BeginPage));
    handler.handle(bpg);

    PTX_PresentationTextData ptx = new PTX_PresentationTextData();
    ptx.setStructuredFieldIntroducer(createSfi(SFTypeID.PTX_PresentationTextData));

    // SIM: Set Inline Margin
    SIM_SetInlineMargin sim = new SIM_SetInlineMargin();
    sim.setDisplacement((short) 100);
    ptx.addControlSequence(sim);

    // SBI: Set Baseline Increment
    SBI_SetBaselineIncrement sbi = new SBI_SetBaselineIncrement();
    sbi.setIncrement((short) 200);
    ptx.addControlSequence(sbi);

    // TRN: Transparent Data
    TRN_TransparentData trn = new TRN_TransparentData();
    trn.setTransparentData("Hello AFP");
    ptx.addControlSequence(trn);

    // BLN: Begin Line
    BLN_BeginLine bln = new BLN_BeginLine();
    ptx.addControlSequence(bln);

    handler.handle(ptx);

    PdfTextState state = handler.getTextState();
    assertEquals(100, state.getInlinePos());
    assertEquals(200, state.getBaselinePos());
  }

  @Test
  public void testTleHandling() throws Exception {
    PdfHandler handler = new PdfHandler(new java.io.ByteArrayOutputStream());

    BDT_BeginDocument bdt = new BDT_BeginDocument();
    bdt.setStructuredFieldIntroducer(createSfi(SFTypeID.BDT_BeginDocument));
    handler.handle(bdt);

    TLE_TagLogicalElement tle = new TLE_TagLogicalElement();
    tle.setStructuredFieldIntroducer(createSfi(SFTypeID.TLE_TagLogicalElement));

    List<Triplet> triplets = new ArrayList<>();
    Triplet.FullyQualifiedName fqn = new Triplet.FullyQualifiedName();
    fqn.setType(Triplet.GlobalID_Use.AttributeGID);
    fqn.setNameAsString("Account");
    triplets.add(fqn);

    Triplet.AttributeValue av = new Triplet.AttributeValue();
    av.setAttributeValue("12345");
    triplets.add(av);

    tle.setTriplets(triplets);

    // This should not throw any exceptions and increments field count
    handler.handle(tle);
    assertEquals(2, handler.getFieldCount());
    assertEquals(1, handler.getStructureDepth()); // BDT still open
  }

  @Test
  public void testResourceTracking() throws Exception {
    PdfHandler handler = new PdfHandler(new java.io.ByteArrayOutputStream());

    // MMO Tracking
    MMO_MapMediumOverlay mmo = new MMO_MapMediumOverlay();
    MMO_MapMediumOverlay.MMO_RepeatingGroup mmorg = new MMO_MapMediumOverlay.MMO_RepeatingGroup();
    mmorg.setNameOfMediumOverlay("O1RESOUR");
    mmo.addRepeatingGroup(mmorg);
    handler.handle(mmo);

    Set<String> mmoResources = handler.getMmoResources();
    assertEquals(1, mmoResources.size());
    assertTrue(mmoResources.contains("O1RESOUR"));

    // MPS Tracking
    MPS_MapPageSegment mps = new MPS_MapPageSegment();
    MPS_MapPageSegment.MPS_RepeatingGroup mpsrg = new MPS_MapPageSegment.MPS_RepeatingGroup();
    mpsrg.setNameOfPageSegment("S1RESOUR");
    mps.addRepeatingGroup(mpsrg);
    handler.handle(mps);

    Set<String> mpsResources = handler.getMpsResources();
    assertEquals(1, mpsResources.size());
    assertTrue(mpsResources.contains("S1RESOUR"));
  }

  @Test
  public void testPgdHandling() throws Exception {
    PdfHandler handler = new PdfHandler(new java.io.ByteArrayOutputStream());

    BPG_BeginPage bpg = new BPG_BeginPage();
    bpg.setStructuredFieldIntroducer(createSfi(SFTypeID.BPG_BeginPage));
    handler.handle(bpg);

    PGD_PageDescriptor pgd = new PGD_PageDescriptor();
    pgd.setxUnitBase(AFPUnitBase.Inches10);
    pgd.setyUnitBase(AFPUnitBase.Inches10);
    pgd.setxUnitsPerUnitBase((short) 2400);
    pgd.setyUnitsPerUnitBase((short) 2400);
    pgd.setxSize(2400);
    pgd.setySize(2400);

    // Should not throw and processes field
    handler.handle(pgd);
    assertEquals(2, handler.getFieldCount());
  }

  @Test
  public void testDefaultPgdHandling() throws Exception {
    PdfHandler handler = new PdfHandler(new java.io.ByteArrayOutputStream());

    // PGD before any page (e.g. in Document preamble)
    PGD_PageDescriptor pgd = new PGD_PageDescriptor();
    pgd.setxUnitBase(AFPUnitBase.Inches10);
    pgd.setyUnitBase(AFPUnitBase.Inches10);
    pgd.setxUnitsPerUnitBase((short) 2400);
    pgd.setyUnitsPerUnitBase((short) 2400);
    pgd.setxSize(2400);
    pgd.setySize(2400);
    handler.handle(pgd);

    // Now start a page, it should pick up the default size
    BPG_BeginPage bpg = new BPG_BeginPage();
    bpg.setStructuredFieldIntroducer(createSfi(SFTypeID.BPG_BeginPage));
    handler.handle(bpg);

    assertEquals(2, handler.getFieldCount());
    // Verification of the actual PDF page size is harder without low-level iText access
    // but the code path is covered and no exception was thrown.
  }

  @Test
  public void testPtxStateTracking() throws Exception {
    PdfHandler handler = new PdfHandler(new java.io.ByteArrayOutputStream());

    BPG_BeginPage bpg = new BPG_BeginPage();
    bpg.setStructuredFieldIntroducer(createSfi(SFTypeID.BPG_BeginPage));
    handler.handle(bpg);

    PTX_PresentationTextData ptx = new PTX_PresentationTextData();
    ptx.setStructuredFieldIntroducer(createSfi(SFTypeID.PTX_PresentationTextData));

    // STO: Set Orientation
    STO_SetTextOrientation sto = new STO_SetTextOrientation();
    sto.setxOrientation(AFPOrientation.ori90);
    sto.setyOrientation(AFPOrientation.ori180);
    ptx.addControlSequence(sto);

    // AMI: Absolute Move Inline
    AMI_AbsoluteMoveInline ami = new AMI_AbsoluteMoveInline();
    ami.setDisplacement((short) 1000);
    ptx.addControlSequence(ami);

    // AMB: Absolute Move Baseline
    AMB_AbsoluteMoveBaseline amb = new AMB_AbsoluteMoveBaseline();
    amb.setDisplacement((short) 2000);
    ptx.addControlSequence(amb);

    // RMI: Relative Move Inline
    RMI_RelativeMoveInline rmi = new RMI_RelativeMoveInline();
    rmi.setIncrement((short) 500);
    ptx.addControlSequence(rmi);

    // RMB: Relative Move Baseline
    RMB_RelativeMoveBaseline rmb = new RMB_RelativeMoveBaseline();
    rmb.setIncrement((short) -200);
    ptx.addControlSequence(rmb);

    // SCFL: Set Coded Font Local
    SCFL_SetCodedFontLocal scfl = new SCFL_SetCodedFontLocal();
    scfl.setCodedFontLocalID((short) 5);
    ptx.addControlSequence(scfl);

    // STC: Set Text Color
    STC_SetTextColor stc = new STC_SetTextColor();
    stc.setForegroundColor(AFPColorValue.Red_0x02);
    ptx.addControlSequence(stc);

    handler.handle(ptx);

    PdfTextState state = handler.getTextState();
    assertEquals(AFPOrientation.ori90, state.getIOrientation());
    assertEquals(AFPOrientation.ori180, state.getBOrientation());
    assertEquals(1500, state.getInlinePos());
    assertEquals(1800, state.getBaselinePos());
    assertEquals(5, state.getFontLid());
    assertEquals(AFPColorValue.Red_0x02, state.getTextColor());
  }

  @Test
  public void testAdvancedPtxStateTracking() throws Exception {
    PdfHandler handler = new PdfHandler(new java.io.ByteArrayOutputStream());

    BPG_BeginPage bpg = new BPG_BeginPage();
    bpg.setStructuredFieldIntroducer(createSfi(SFTypeID.BPG_BeginPage));
    handler.handle(bpg);

    PTX_PresentationTextData ptx = new PTX_PresentationTextData();
    ptx.setStructuredFieldIntroducer(createSfi(SFTypeID.PTX_PresentationTextData));

    // SIA: Set Intercharacter Adjustment
    SIA_SetIntercharacterAdjustment sia = new SIA_SetIntercharacterAdjustment();
    sia.setAdjustment((short) 10);
    ptx.addControlSequence(sia);

    // SVI: Set Variable-space Character Increment
    SVI_SetVariableSpaceCharacterIncrement svi = new SVI_SetVariableSpaceCharacterIncrement();
    svi.setIncrement((short) 20);
    ptx.addControlSequence(svi);

    // SIM: Set Inline Margin
    SIM_SetInlineMargin sim = new SIM_SetInlineMargin();
    sim.setDisplacement((short) 30);
    ptx.addControlSequence(sim);

    // SBI: Set Baseline Increment
    SBI_SetBaselineIncrement sbi = new SBI_SetBaselineIncrement();
    sbi.setIncrement((short) 40);
    ptx.addControlSequence(sbi);

    handler.handle(ptx);

    PdfTextState state = handler.getTextState();
    assertEquals(10, state.getIntercharacterAdjustment());
    assertEquals(20, state.getVariableSpaceIncrement());
    assertEquals(30, state.getInlineMargin());
    assertEquals(40, state.getBaselineIncrement());
  }

  @Test
  public void testFontTracking() throws Exception {
    PdfHandler handler = new PdfHandler(new java.io.ByteArrayOutputStream());

    // MCF Format 1
    MCF_MapCodedFont_Format1 mcf1 = new MCF_MapCodedFont_Format1();
    MCF_MapCodedFont_Format1.MCF_RepeatingGroup rg1 = new MCF_MapCodedFont_Format1.MCF_RepeatingGroup();
    rg1.setCodedFontLocalID((short) 1);
    rg1.setCodedFontName("X0H200  ");
    mcf1.addRepeatingGroup(rg1);
    handler.handle(mcf1);

    Map<Short, String> fontMap = handler.getFontMap();
    assertEquals(1, fontMap.size());
    assertEquals("X0H200  ", fontMap.get((short) 1));

    // MCF Format 2
    MCF_MapCodedFont_Format2 mcf2 = new MCF_MapCodedFont_Format2();
    MCF_MapCodedFont_Format2.MCF_RepeatingGroup rg2 = new MCF_MapCodedFont_Format2.MCF_RepeatingGroup();

    Triplet.ResourceLocalIdentifier rli = new Triplet.ResourceLocalIdentifier();
    rli.setTripletID(Triplet.TripletID.ResourceLocalIdentifier);
    rli.setResourceType(Triplet.ResourceLocalIdentifier.RLI_ResourceType.CodedFont);
    rli.setResourceLocalID((short) 2);
    rg2.addTriplet(rli);

    Triplet.FullyQualifiedName fqn = new Triplet.FullyQualifiedName();
    fqn.setTripletID(Triplet.TripletID.FullyQualifiedName);
    fqn.setType(Triplet.GlobalID_Use.CodedFontNameReference);
    fqn.setNameAsString("X0H300");
    rg2.addTriplet(fqn);

    mcf2.addRepeatingGroup(rg2);
    handler.handle(mcf2);

    fontMap = handler.getFontMap();
    assertEquals(2, fontMap.size());
    assertEquals("X0H300", fontMap.get((short) 2));
  }

  @Test
  public void testUctAndGraphicCharactersHandling() throws Exception {
    PdfHandler handler = new PdfHandler(new java.io.ByteArrayOutputStream());

    BPG_BeginPage bpg = new BPG_BeginPage();
    bpg.setStructuredFieldIntroducer(createSfi(SFTypeID.BPG_BeginPage));
    handler.handle(bpg);

    PTX_PresentationTextData ptx = new PTX_PresentationTextData();
    ptx.setStructuredFieldIntroducer(createSfi(SFTypeID.PTX_PresentationTextData));

    // UCT: Unicode Complex Text
    UCT_UnicodeComplexText uct = new UCT_UnicodeComplexText();
    ControlSequenceIntroducer uctCsi = new ControlSequenceIntroducer();
    uctCsi.setLength((short) 14); // Minimum length for UCT
    uctCsi.setControlSequenceFunctionType(ControlSequenceFunctionType.UCT_UnicodeComplexText);
    uct.setCsi(uctCsi);
    uct.setComplexText("Unicode".getBytes(java.nio.charset.StandardCharsets.UTF_16BE));
    ptx.addControlSequence(uct);

    // Graphic Characters
    GraphicCharacters gc = new GraphicCharacters();
    ControlSequenceIntroducer gcCsi = new ControlSequenceIntroducer();
    gcCsi.setLength((short) 4);
    gcCsi.setControlSequenceFunctionType(ControlSequenceFunctionType.GraphicCharacters);
    gc.setCsi(gcCsi);
    // GraphicCharacters.text is usually set during decodeAFP, but we can set it manually for testing
    // Since we want to verify it calls renderText, we need to ensure getText() returns something
    gc.decodeAFP(new byte[] { (byte)0xC1, (byte)0xC2 }, 0, 2, new AFPParserConfiguration()); // "AB" in EBCDIC (simplified)
    ptx.addControlSequence(gc);

    handler.handle(ptx);
    assertEquals(2, handler.getFieldCount());
  }

  @Test
  public void testGocaStateTracking() throws Exception {
    PdfHandler handler = new PdfHandler(new java.io.ByteArrayOutputStream());

    BPG_BeginPage bpg = new BPG_BeginPage();
    bpg.setStructuredFieldIntroducer(createSfi(SFTypeID.BPG_BeginPage));
    handler.handle(bpg);

    GAD_GraphicsData gad = new GAD_GraphicsData();
    gad.setStructuredFieldIntroducer(createSfi(SFTypeID.GAD_GraphicsData));
    List<GAD_DrawingOrder> orders = new ArrayList<>();

    // GSCOL: Set Color
    GSCOL_SetColor gscol = new GSCOL_SetColor();
    gscol.setColor(AFPColorValue.Blue_0x01);
    orders.add(gscol);

    // GSLW: Set Line Width
    GSLW_SetLineWidth gslw = new GSLW_SetLineWidth();
    gslw.setLineWidth((short) 3);
    orders.add(gslw);

    // GSLT: Set Line Type
    GSLT_SetLineType gslt = new GSLT_SetLineType();
    gslt.setLineType((short) 2);
    orders.add(gslt);

    // GSECOL: Set Extended Color
    GSECOL_SetExtendedColor gsecol = new GSECOL_SetExtendedColor();
    gsecol.setColor(AFPColorValue.Red_0x02);
    orders.add(gsecol);

    gad.setDrawingOrders(orders);
    handler.handle(gad);

    PdfGraphicsState state = handler.getGraphicsState();
    assertEquals(AFPColorValue.Red_0x02, state.getColor());
    assertEquals(3, state.getLineWidth());
    assertEquals(2, state.getLineType());

    // Reset on next page
    BPG_BeginPage bpg2 = new BPG_BeginPage();
    bpg2.setStructuredFieldIntroducer(createSfi(SFTypeID.BPG_BeginPage));
    handler.handle(bpg2);

    PdfGraphicsState state2 = handler.getGraphicsState();
    assertEquals(AFPColorValue.DeviceDefault_0x00, state2.getColor());
    assertEquals(0, state2.getLineWidth());
    assertEquals(0, state2.getLineType());
  }

  @Test
  public void testGocaLinePrimitives() throws Exception {
    PdfHandler handler = new PdfHandler(new java.io.ByteArrayOutputStream());

    BPG_BeginPage bpg = new BPG_BeginPage();
    bpg.setStructuredFieldIntroducer(createSfi(SFTypeID.BPG_BeginPage));
    handler.handle(bpg);

    GAD_GraphicsData gad = new GAD_GraphicsData();
    gad.setStructuredFieldIntroducer(createSfi(SFTypeID.GAD_GraphicsData));
    List<GAD_DrawingOrder> orders = new ArrayList<>();

    // GSCP: Set Current Position
    GSCP_SetCurrentPosition gscp = new GSCP_SetCurrentPosition();
    gscp.setLengthOfFollowingData((short) 4);
    gscp.setCoordinateX((short) 100);
    gscp.setCoordinateY((short) 200);
    orders.add(gscp);

    // GCLINE: Line at Current Position (to 300, 400)
    GCLINE_LineAtCurrentPosition gcline = new GCLINE_LineAtCurrentPosition();
    List<GAD_DrawingOrder.GOCA_Point> points = new ArrayList<>();
    points.add(new GAD_DrawingOrder.GOCA_Point((short) 300, (short) 400));
    gcline.setPoints(points);
    orders.add(gcline);

    // GCRLINE: Relative Line at Current Position (delta 50, 50 -> to 350, 450)
    GCRLINE_RelativeLineAtCurrentPosition gcrline = new GCRLINE_RelativeLineAtCurrentPosition();
    List<GAD_DrawingOrder.GOCA_RelativePoint> relOffsets = new ArrayList<>();
    relOffsets.add(new GAD_DrawingOrder.GOCA_RelativePoint((byte) 50, (byte) 50));
    gcrline.relativeOffsets = relOffsets;
    orders.add(gcrline);

    gad.setDrawingOrders(orders);
    handler.handle(gad);

    PdfGraphicsState state = handler.getGraphicsState();
    assertEquals(350, state.getCurrentX());
    assertEquals(450, state.getCurrentY());
  }

  @Test
  public void testGocaBoxPrimitives() throws Exception {
    PdfHandler handler = new PdfHandler(new java.io.ByteArrayOutputStream());

    BPG_BeginPage bpg = new BPG_BeginPage();
    bpg.setStructuredFieldIntroducer(createSfi(SFTypeID.BPG_BeginPage));
    handler.handle(bpg);

    GAD_GraphicsData gad = new GAD_GraphicsData();
    gad.setStructuredFieldIntroducer(createSfi(SFTypeID.GAD_GraphicsData));
    List<GAD_DrawingOrder> orders = new ArrayList<>();

    // GBOX: Box at Given Position
    GBOX_BoxAtGivenPosition gbox = new GBOX_BoxAtGivenPosition();
    gbox.setLengthOfFollowingData((short) 10);
    gbox.setFirstCorner(new GAD_DrawingOrder.GOCA_Point((short) 100, (short) 100));
    gbox.setDiagonalCorner(new GAD_DrawingOrder.GOCA_Point((short) 200, (short) 200));
    orders.add(gbox);

    // GCBOX: Box at Current Position
    GCBOX_BoxAtCurrentPosition gcbox = new GCBOX_BoxAtCurrentPosition();
    gcbox.setLengthOfFollowingData((short) 6);
    gcbox.setDiagonalCorner(new GAD_DrawingOrder.GOCA_Point((short) 300, (short) 300));
    orders.add(gcbox);

    gad.setDrawingOrders(orders);
    handler.handle(gad);

    PdfGraphicsState state = handler.getGraphicsState();
    assertEquals(300, state.getCurrentX());
    assertEquals(300, state.getCurrentY());
  }

  @Test
  public void testGocaAdvancedAttributes() throws Exception {
    PdfHandler handler = new PdfHandler(new java.io.ByteArrayOutputStream());

    BPG_BeginPage bpg = new BPG_BeginPage();
    bpg.setStructuredFieldIntroducer(createSfi(SFTypeID.BPG_BeginPage));
    handler.handle(bpg);

    GAD_GraphicsData gad = new GAD_GraphicsData();
    gad.setStructuredFieldIntroducer(createSfi(SFTypeID.GAD_GraphicsData));
    List<GAD_DrawingOrder> orders = new ArrayList<>();

    // GSLE: Set Line End
    GSLE_SetLineEnd gsle = new GSLE_SetLineEnd();
    gsle.setLineEnd(GSLE_SetLineEnd.LineEnd.Round);
    orders.add(gsle);

    // GSLJ: Set Line Join
    GSLJ_SetLineJoin gslj = new GSLJ_SetLineJoin();
    gslj.setLineJoin(GSLJ_SetLineJoin.LineJoin.Bevel);
    orders.add(gslj);

    gad.setDrawingOrders(orders);
    handler.handle(gad);

    PdfGraphicsState state = handler.getGraphicsState();
    assertEquals(GSLE_SetLineEnd.LineEnd.Round, state.getLineEnd());
    assertEquals(GSLJ_SetLineJoin.LineJoin.Bevel, state.getLineJoin());
  }

  @Test
  public void testGocaAreaHandling() throws Exception {
    PdfHandler handler = new PdfHandler(new java.io.ByteArrayOutputStream());

    BPG_BeginPage bpg = new BPG_BeginPage();
    bpg.setStructuredFieldIntroducer(createSfi(SFTypeID.BPG_BeginPage));
    handler.handle(bpg);

    GAD_GraphicsData gad = new GAD_GraphicsData();
    gad.setStructuredFieldIntroducer(createSfi(SFTypeID.GAD_GraphicsData));
    List<GAD_DrawingOrder> orders = new ArrayList<>();

    // GBAR: Begin Area (Flags 0x40 -> Draw Boundary, 0x00 -> Even-Odd)
    GBAR_BeginArea gbar = new GBAR_BeginArea();
    gbar.setInternalFlags((short) 0x40);
    orders.add(gbar);

    gad.setDrawingOrders(orders);
    handler.handle(gad);

    PdfGraphicsState state = handler.getGraphicsState();
    assertTrue(state.isInArea());
    assertTrue(state.isDrawAreaBoundary());
    assertTrue(state.isEvenOddRule());

    // GEAR: End Area
    orders.clear();
    orders.add(new GEAR_EndArea());
    gad.setDrawingOrders(orders);
    handler.handle(gad);

    state = handler.getGraphicsState();
    assertTrue(!state.isInArea());
  }

  @Test
  public void testGocaArcRendering() throws Exception {
    PdfHandler handler = new PdfHandler(new java.io.ByteArrayOutputStream());

    BPG_BeginPage bpg = new BPG_BeginPage();
    bpg.setStructuredFieldIntroducer(createSfi(SFTypeID.BPG_BeginPage));
    handler.handle(bpg);

    GAD_GraphicsData gad = new GAD_GraphicsData();
    gad.setStructuredFieldIntroducer(createSfi(SFTypeID.GAD_GraphicsData));
    List<GAD_DrawingOrder> orders = new ArrayList<>();

    // GFARC: Full Arc at Given Position
    GFARC_FullArcAtGivenPosition gfarc = new GFARC_FullArcAtGivenPosition();
    gfarc.setArcCenter(new GAD_DrawingOrder.GOCA_Point((short) 500, (short) 500));
    gfarc.setMultiplierIntegerPortion((short) 100);
    orders.add(gfarc);

    // GCFARC: Full Arc at Current Position
    GCFARC_FullArcAtCurrentPosition gcfarc = new GCFARC_FullArcAtCurrentPosition();
    gcfarc.setMultiplierIntegerPortion((short) 50);
    orders.add(gcfarc);

    gad.setDrawingOrders(orders);
    handler.handle(gad);

    assertEquals(2, handler.getFieldCount());
  }

  @Test
  public void testGocaAdvancedStateTracking() throws Exception {
    PdfHandler handler = new PdfHandler(new java.io.ByteArrayOutputStream());

    BPG_BeginPage bpg = new BPG_BeginPage();
    bpg.setStructuredFieldIntroducer(createSfi(SFTypeID.BPG_BeginPage));
    handler.handle(bpg);

    GAD_GraphicsData gad = new GAD_GraphicsData();
    gad.setStructuredFieldIntroducer(createSfi(SFTypeID.GAD_GraphicsData));
    List<GAD_DrawingOrder> orders = new ArrayList<>();

    // GSAP: Set Arc Parameters
    GSAP_SetArcParameters gsap = new GSAP_SetArcParameters();
    gsap.setArcTransformP((short) 1);
    gsap.setArcTransformQ((short) 2);
    gsap.setArcTransformR((short) 3);
    gsap.setArcTransformS((short) 4);
    orders.add(gsap);

    // GSPCOL: Set Process Color
    GSPCOL_SetProcessColor gspcol = new GSPCOL_SetProcessColor();
    gspcol.setColorSpace(AFPColorSpace.RGB);
    gspcol.setNrOfBitsComponent1((byte) 8);
    gspcol.setNrOfBitsComponent2((byte) 8);
    gspcol.setNrOfBitsComponent3((byte) 8);
    gspcol.setColorValue(new byte[] { (byte) 0xFF, 0x00, 0x00 });
    orders.add(gspcol);

    gad.setDrawingOrders(orders);
    handler.handle(gad);

    PdfGraphicsState state = handler.getGraphicsState();
    assertEquals(1, state.getArcTransformP());
    assertEquals(2, state.getArcTransformQ());
    assertEquals(3, state.getArcTransformR());
    assertEquals(4, state.getArcTransformS());
    assertEquals(AFPColorSpace.RGB, state.getProcessColorSpace());
    assertEquals(8, state.getNrOfBitsComponent1());
    assertEquals((byte) 0xFF, state.getProcessColorValue()[0]);
  }

  private StructuredFieldIntroducer createSfi(SFTypeID typeID) {
    StructuredFieldIntroducer sfi = new StructuredFieldIntroducer();
    sfi.setSFTypeID(typeID);
    return sfi;
  }
}
