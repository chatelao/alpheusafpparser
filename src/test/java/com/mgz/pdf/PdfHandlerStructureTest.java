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
import com.mgz.afp.modca.BDT_BeginDocument;
import com.mgz.afp.modca.BPG_BeginPage;
import com.mgz.afp.modca.EDT_EndDocument;
import com.mgz.afp.modca.EPG_EndPage;
import com.mgz.afp.modca.MMO_MapMediumOverlay;
import com.mgz.afp.modca.MPS_MapPageSegment;
import com.mgz.afp.modca.PGD_PageDescriptor;
import com.mgz.afp.modca.TLE_TagLogicalElement;
import com.mgz.afp.ptoca.PTX_PresentationTextData;
import com.mgz.afp.ptoca.controlSequence.PTOCAControlSequence.AMB_AbsoluteMoveBaseline;
import com.mgz.afp.ptoca.controlSequence.PTOCAControlSequence.AMI_AbsoluteMoveInline;
import com.mgz.afp.ptoca.controlSequence.PTOCAControlSequence.RMB_RelativeMoveBaseline;
import com.mgz.afp.ptoca.controlSequence.PTOCAControlSequence.RMI_RelativeMoveInline;
import com.mgz.afp.ptoca.controlSequence.PTOCAControlSequence.SCFL_SetCodedFontLocal;
import com.mgz.afp.ptoca.controlSequence.PTOCAControlSequence.STC_SetTextColor;
import com.mgz.afp.ptoca.controlSequence.PTOCAControlSequence.STO_SetTextOrientation;
import com.mgz.afp.enums.AFPColorValue;
import com.mgz.afp.enums.AFPOrientation;
import com.mgz.afp.triplets.Triplet;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
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

  private StructuredFieldIntroducer createSfi(SFTypeID typeID) {
    StructuredFieldIntroducer sfi = new StructuredFieldIntroducer();
    sfi.setSFTypeID(typeID);
    return sfi;
  }
}
