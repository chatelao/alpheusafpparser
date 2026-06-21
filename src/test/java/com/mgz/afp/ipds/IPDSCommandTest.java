/*
Copyright 2026 Alpheus AFP Parser Authors

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

package com.mgz.afp.ipds;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.mgz.afp.enums.AFPColorValue;
import com.mgz.afp.enums.AFPOrientation;
import com.mgz.afp.enums.AFPUnitBase;
import com.mgz.afp.parser.AFPParser;
import com.mgz.afp.parser.AFPParserConfiguration;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import org.junit.jupiter.api.Test;

/**
 * Tests for IPDS commands.
 */
public class IPDSCommandTest {

  @Test
  public void testSHSRoundTrip() throws Exception {
    // SHS: D697, ARQ=1, CID=0x1234
    // SFI length = 8 + 3 (payload) = 11 (0x0B)
    byte[] data = new byte[] {
        0x5A, 0x00, 0x0B, (byte) 0xD6, (byte) 0x97, 0x00, 0x00, 0x00, 0x00,
        (byte) 0x80, // Flag: ARQ
        0x12, 0x34   // CID
    };

    AFPParserConfiguration config = new AFPParserConfiguration();
    config.setInputStream(new ByteArrayInputStream(data));
    AFPParser parser = new AFPParser(config);
    SHS_SetHomeState shs = (SHS_SetHomeState) parser.parseNextSF();

    assertNotNull(shs);
    assertTrue(shs.isAcknowledgementRequired());
    assertEquals(0x1234, shs.getCorrelationId());

    ByteArrayOutputStream baos = new ByteArrayOutputStream();
    shs.writeAFP(baos, config);
    assertArrayEquals(data, baos.toByteArray());
  }

  @Test
  public void testNOPRoundTrip() throws Exception {
    // NOP: D603, ARQ=0, data: 11 22 33
    // SFI length = 8 + 4 (payload) = 12 (0x0C)
    byte[] data = new byte[] {
        0x5A, 0x00, 0x0C, (byte) 0xD6, (byte) 0x03, 0x00, 0x00, 0x00, 0x00,
        0x00, // Flag: no ARQ
        0x11, 0x22, 0x33
    };

    AFPParserConfiguration config = new AFPParserConfiguration();
    config.setInputStream(new ByteArrayInputStream(data));
    AFPParser parser = new AFPParser(config);
    NOP_NoOperation nop = (NOP_NoOperation) parser.parseNextSF();

    assertNotNull(nop);
    assertArrayEquals(new byte[] {0x11, 0x22, 0x33}, nop.getBinaryData());

    ByteArrayOutputStream baos = new ByteArrayOutputStream();
    nop.writeAFP(baos, config);
    assertArrayEquals(data, baos.toByteArray());
  }

  @Test
  public void testBPRoundTrip() throws Exception {
    // BP: D6AF, ARQ=1, CID=0x5678, PageID=0x00000001
    // SFI length = 8 + 3 (IPDS header) + 4 (PageID) = 15 (0x0F)
    byte[] data = new byte[] {
        0x5A, 0x00, 0x0F, (byte) 0xD6, (byte) 0xAF, 0x00, 0x00, 0x00, 0x00,
        (byte) 0x80, // Flag: ARQ
        0x56, 0x78,  // CID
        0x00, 0x00, 0x00, 0x01 // PageID
    };

    AFPParserConfiguration config = new AFPParserConfiguration();
    config.setInputStream(new ByteArrayInputStream(data));
    AFPParser parser = new AFPParser(config);
    BP_BeginPage bp = (BP_BeginPage) parser.parseNextSF();

    assertNotNull(bp);
    assertTrue(bp.isAcknowledgementRequired());
    assertEquals(1, bp.getPageId());

    ByteArrayOutputStream baos = new ByteArrayOutputStream();
    bp.writeAFP(baos, config);
    assertArrayEquals(data, baos.toByteArray());
  }

  @Test
  public void testEPRoundTrip() throws Exception {
    // EP: D6BF, ARQ=0, ignored data: AA BB
    // SFI length = 8 + 1 (IPDS header) + 2 (ignored data) = 11 (0x0B)
    byte[] data = new byte[] {
        0x5A, 0x00, 0x0B, (byte) 0xD6, (byte) 0xBF, 0x00, 0x00, 0x00, 0x00,
        0x00, // Flag: no ARQ
        (byte) 0xAA, (byte) 0xBB
    };

    AFPParserConfiguration config = new AFPParserConfiguration();
    config.setInputStream(new ByteArrayInputStream(data));
    AFPParser parser = new AFPParser(config);
    EP_EndPage ep = (EP_EndPage) parser.parseNextSF();

    assertNotNull(ep);
    assertArrayEquals(new byte[] {(byte) 0xAA, (byte) 0xBB}, ep.getIgnoredData());

    ByteArrayOutputStream baos = new ByteArrayOutputStream();
    ep.writeAFP(baos, config);
    assertArrayEquals(data, baos.toByteArray());
  }

  @Test
  public void testENDRoundTrip() throws Exception {
    // END: D65D, ARQ=0, ignored data: CC DD
    // SFI length = 8 + 1 (IPDS header) + 2 (ignored data) = 11 (0x0B)
    byte[] data = new byte[] {
        0x5A, 0x00, 0x0B, (byte) 0xD6, (byte) 0x5D, 0x00, 0x00, 0x00, 0x00,
        0x00, // Flag: no ARQ
        (byte) 0xCC, (byte) 0xDD
    };

    AFPParserConfiguration config = new AFPParserConfiguration();
    config.setInputStream(new ByteArrayInputStream(data));
    AFPParser parser = new AFPParser(config);
    END_End end = (END_End) parser.parseNextSF();

    assertNotNull(end);
    assertArrayEquals(new byte[] {(byte) 0xCC, (byte) 0xDD}, end.getIgnoredData());

    ByteArrayOutputStream baos = new ByteArrayOutputStream();
    end.writeAFP(baos, config);
    assertArrayEquals(data, baos.toByteArray());
  }

  @Test
  public void testSTMRoundTrip() throws Exception {
    // STM: D6E4, ARQ=1, CID=0xABCD
    // SFI length = 8 + 3 (IPDS header) = 11 (0x0B)
    byte[] data = new byte[] {
        0x5A, 0x00, 0x0B, (byte) 0xD6, (byte) 0xE4, 0x00, 0x00, 0x00, 0x00,
        (byte) 0x80, // Flag: ARQ
        (byte) 0xAB, (byte) 0xCD
    };

    AFPParserConfiguration config = new AFPParserConfiguration();
    config.setInputStream(new ByteArrayInputStream(data));
    AFPParser parser = new AFPParser(config);
    STM_SenseTypeAndModel stm = (STM_SenseTypeAndModel) parser.parseNextSF();

    assertNotNull(stm);
    assertTrue(stm.isAcknowledgementRequired());
    assertEquals(0xABCD, stm.getCorrelationId());

    ByteArrayOutputStream baos = new ByteArrayOutputStream();
    stm.writeAFP(baos, config);
    assertArrayEquals(data, baos.toByteArray());
  }

  @Test
  public void testMIDRoundTrip() throws Exception {
    // MID: D601, ARQ=0, type=StartIPDSDialog (0x00)
    // SFI length = 8 + 1 (IPDS header) + 1 (type) = 10 (0x0A)
    byte[] data = new byte[] {
        0x5A, 0x00, 0x0A, (byte) 0xD6, (byte) 0x01, 0x00, 0x00, 0x00, 0x00,
        0x00, // Flag: no ARQ
        0x00  // Type: StartIPDSDialog
    };

    AFPParserConfiguration config = new AFPParserConfiguration();
    config.setInputStream(new ByteArrayInputStream(data));
    AFPParser parser = new AFPParser(config);
    MID_ManageIPDSDialog mid = (MID_ManageIPDSDialog) parser.parseNextSF();

    assertNotNull(mid);
    assertEquals(MID_ManageIPDSDialog.MID_Type.StartIPDSDialog, mid.getType());

    ByteArrayOutputStream baos = new ByteArrayOutputStream();
    mid.writeAFP(baos, config);
    assertArrayEquals(data, baos.toByteArray());
  }

  @Test
  public void testISPRoundTrip() throws Exception {
    // ISP: D67E, ARQ=0, PageSequenceNumber=0x00000001
    // SFI length = 8 + 1 (IPDS header) + 4 (PageSequenceNumber) = 13 (0x0D)
    byte[] data = new byte[] {
        0x5A, 0x00, 0x0D, (byte) 0xD6, (byte) 0x7E, 0x00, 0x00, 0x00, 0x00,
        0x00, // Flag: no ARQ
        0x00, 0x00, 0x00, 0x01 // Page Sequence Number
    };

    AFPParserConfiguration config = new AFPParserConfiguration();
    config.setInputStream(new ByteArrayInputStream(data));
    AFPParser parser = new AFPParser(config);
    ISP_IncludeSavedPage isp = (ISP_IncludeSavedPage) parser.parseNextSF();

    assertNotNull(isp);
    assertEquals(1, isp.getPageSequenceNumber());

    ByteArrayOutputStream baos = new ByteArrayOutputStream();
    isp.writeAFP(baos, config);
    assertArrayEquals(data, baos.toByteArray());
  }

  @Test
  public void testICMRRoundTrip() throws Exception {
    // ICMR: D66B, ARQ=1, CID=0x9999, InvocationFlags=0x80 (Reset), HAIDs=0x1111, 0x2222
    // SFI length = 8 + 3 (IPDS header) + 1 (flags) + 4 (reserved) + 4 (2 HAIDs) = 20 (0x14)
    byte[] data = new byte[] {
        0x5A, 0x00, 0x14, (byte) 0xD6, (byte) 0x6B, 0x00, 0x00, 0x00, 0x00,
        (byte) 0x80, // Flag: ARQ
        (byte) 0x99, (byte) 0x99, // CID
        (byte) 0x80, // Invocation Flags: Reset
        0x00, 0x00, 0x00, 0x00, // Reserved
        0x11, 0x11, // HAID 1
        0x22, 0x22  // HAID 2
    };

    AFPParserConfiguration config = new AFPParserConfiguration();
    config.setInputStream(new ByteArrayInputStream(data));
    AFPParser parser = new AFPParser(config);
    ICMR_InvokeCMR icmr = (ICMR_InvokeCMR) parser.parseNextSF();

    assertNotNull(icmr);
    assertTrue(icmr.isAcknowledgementRequired());
    assertEquals(0x9999, icmr.getCorrelationId());
    assertTrue(icmr.isReset());
    assertEquals(2, icmr.getHaids().size());
    assertEquals(0x1111, icmr.getHaids().get(0));
    assertEquals(0x2222, icmr.getHaids().get(1));

    ByteArrayOutputStream baos = new ByteArrayOutputStream();
    icmr.writeAFP(baos, config);
    assertArrayEquals(data, baos.toByteArray());
  }

  @Test
  public void testASNRoundTrip() throws Exception {
    // ASN: D60A, ARQ=0, reserved=0000, no triplets
    // SFI length = 8 + 1 (IPDS header) + 2 (reserved) = 11 (0x0B)
    byte[] data = new byte[] {
        0x5A, 0x00, 0x0B, (byte) 0xD6, (byte) 0x0A, 0x00, 0x00, 0x00, 0x00,
        0x00, // Flag: no ARQ
        0x00, 0x00 // Reserved
    };

    AFPParserConfiguration config = new AFPParserConfiguration();
    config.setInputStream(new ByteArrayInputStream(data));
    AFPParser parser = new AFPParser(config);
    ASN_ActivateSetupName asn = (ASN_ActivateSetupName) parser.parseNextSF();

    assertNotNull(asn);

    ByteArrayOutputStream baos = new ByteArrayOutputStream();
    asn.writeAFP(baos, config);
    assertArrayEquals(data, baos.toByteArray());
  }

  @Test
  public void testAFORoundTrip() throws Exception {
    // AFO: D602, ARQ=0, no triplets
    // SFI length = 8 + 1 (IPDS header) = 9 (0x09)
    byte[] data = new byte[] {
        0x5A, 0x00, 0x09, (byte) 0xD6, (byte) 0x02, 0x00, 0x00, 0x00, 0x00,
        0x00 // Flag: no ARQ
    };

    AFPParserConfiguration config = new AFPParserConfiguration();
    config.setInputStream(new ByteArrayInputStream(data));
    AFPParser parser = new AFPParser(config);
    AFO_ApplyFinishingOperations afo = (AFO_ApplyFinishingOperations) parser.parseNextSF();

    assertNotNull(afo);

    ByteArrayOutputStream baos = new ByteArrayOutputStream();
    afo.writeAFP(baos, config);
    assertArrayEquals(data, baos.toByteArray());
  }

  @Test
  public void testDFRoundTrip() throws Exception {
    // DF: D64F, ARQ=0, deactivationType=0x11, HAID=0x1234, sectionId=0x41, FIS=0x2D00
    // SFI length = 8 + 1 (IPDS header) + 1 (type) + 2 (HAID) + 1 (section) + 2 (FIS) = 15 (0x0F)
    byte[] data = new byte[] {
        0x5A, 0x00, 0x0F, (byte) 0xD6, (byte) 0x4F, 0x00, 0x00, 0x00, 0x00,
        0x00, // Flag: no ARQ
        0x11, // deactivationType
        0x12, 0x34, // HAID
        0x41, // sectionId
        0x2D, 0x00 // FIS
    };

    AFPParserConfiguration config = new AFPParserConfiguration();
    config.setInputStream(new ByteArrayInputStream(data));
    AFPParser parser = new AFPParser(config);
    DF_DeactivateFont df = (DF_DeactivateFont) parser.parseNextSF();

    assertNotNull(df);
    assertEquals(0x11, df.getDeactivationType());
    assertEquals(0x1234, df.getHaid());
    assertEquals((short) 0x41, df.getSectionId());
    assertEquals(0x2D00, df.getFis());

    ByteArrayOutputStream baos = new ByteArrayOutputStream();
    df.writeAFP(baos, config);
    assertArrayEquals(data, baos.toByteArray());
  }

  @Test
  public void testDUARoundTrip() throws Exception {
    // DUA: D6CE, ARQ=0, reset=0, unitBase=0, upub=1440, xmOffset=0, ymOffset=0, xmExtent=1000, ymExtent=2000
    // SFI length = 8 + 1 (IPDS header) + 16 (payload) = 25 (0x19)
    byte[] data = new byte[] {
        0x5A, 0x00, 0x19, (byte) 0xD6, (byte) 0xCE, 0x00, 0x00, 0x00, 0x00,
        0x00, // Flag: no ARQ
        0x00, // reset
        0x00, // unitBase
        0x05, (byte) 0xA0, // upub (1440)
        0x00, 0x00, 0x00, // xmOffset
        0x00, 0x00, 0x00, // ymOffset
        0x00, 0x03, (byte) 0xE8, // xmExtent (1000)
        0x00, 0x07, (byte) 0xD0  // ymExtent (2000)
    };

    AFPParserConfiguration config = new AFPParserConfiguration();
    config.setInputStream(new ByteArrayInputStream(data));
    AFPParser parser = new AFPParser(config);
    DUA_DefineUserArea dua = (DUA_DefineUserArea) parser.parseNextSF();

    assertNotNull(dua);
    assertEquals(0, dua.getReset());
    assertEquals(0, dua.getUnitBase());
    assertEquals(1440, dua.getUpub());
    assertEquals(1000, dua.getXmExtent());

    ByteArrayOutputStream baos = new ByteArrayOutputStream();
    dua.writeAFP(baos, config);
    assertArrayEquals(data, baos.toByteArray());
  }

  @Test
  public void testLCCRoundTrip() throws Exception {
    // LCC: D69F, ARQ=0, RG: count=4, copies=2, keywords=C100
    // SFI length = 8 + 1 (IPDS header) + 4 (RG) = 13 (0x0D)
    byte[] data = new byte[] {
        0x5A, 0x00, 0x0D, (byte) 0xD6, (byte) 0x9F, 0x00, 0x00, 0x00, 0x00,
        0x00, // Flag: no ARQ
        0x04, // RG count
        0x02, // copies
        (byte) 0xC1, 0x00 // Simplex keyword
    };

    AFPParserConfiguration config = new AFPParserConfiguration();
    config.setInputStream(new ByteArrayInputStream(data));
    AFPParser parser = new AFPParser(config);
    LCC_LoadCopyControl lcc = (LCC_LoadCopyControl) parser.parseNextSF();

    assertNotNull(lcc);
    assertEquals(1, lcc.getRepeatingGroups().size());
    LCC_LoadCopyControl.LCC_RepeatingGroup rg = (LCC_LoadCopyControl.LCC_RepeatingGroup) lcc.getRepeatingGroups().get(0);
    assertEquals(4, rg.getCount());
    assertEquals(2, rg.getCopies());

    ByteArrayOutputStream baos = new ByteArrayOutputStream();
    lcc.writeAFP(baos, config);
    assertArrayEquals(data, baos.toByteArray());
  }

  @Test
  public void testLPDRoundTrip() throws Exception {
    // LPD: D6CF, ARQ=0, mandatory 43 bytes
    // SFI length = 8 + 1 (IPDS header) + 43 = 52 (0x34)
    byte[] payload = new byte[43];
    payload[0] = 0x00; // unitBase
    payload[2] = 0x38; payload[3] = 0x40; // xupub
    payload[4] = 0x38; payload[5] = 0x40; // yupub
    payload[7] = 0x00; payload[8] = 0x7F; payload[9] = (byte) 0xFF; // xpExtent
    payload[11] = 0x00; payload[12] = 0x7F; payload[13] = (byte) 0xFF; // ypExtent
    payload[41] = (byte) 0xFF; payload[42] = 0x07; // color

    byte[] data = new byte[52];
    System.arraycopy(new byte[] {0x5A, 0x00, 0x34, (byte) 0xD6, (byte) 0xCF, 0x00, 0x00, 0x00, 0x00, 0x00}, 0, data, 0, 10);
    System.arraycopy(payload, 0, data, 9, 43); // 9 is where flagByte is

    // Correction: flagByte is byte 9 of SF, then payload. SFI is 8 bytes, then IPDS header (flag + CID).
    // IPDSCommand.decodeIPDSHeader decodes flag and CID.
    // Length 52: 8 (SFI) + 1 (flag) + 43 (payload) = 52. CID is optional.
    data = new byte[] {
        0x5A, 0x00, 0x34, (byte) 0xD6, (byte) 0xCF, 0x00, 0x00, 0x00, 0x00,
        0x00, // flagByte
        0x00, // unitBase
        0x00, // reserved
        0x38, 0x40, // xupub
        0x38, 0x40, // yupub
        0x00, // reserved
        0x00, 0x03, (byte) 0xE8, // xpExtent (1000)
        0x00, // reserved
        0x00, 0x07, (byte) 0xD0, // ypExtent (2000)
        0x00, // reserved
        0x01, // orderedDataFlags
        0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, // reserved 16-23
        0x00, 0x00, // iAxisOrientation
        0x2D, 0x00, // bAxisOrientation
        0x00, 0x00, // initialI
        0x00, 0x00, // initialB
        0x00, 0x00, // inlineMargin
        0x00, 0x00, // intercharAdjustment
        0x00, 0x00, // reserved 36-37
        0x00, 0x00, // baselineIncrement
        0x01, // lid
        (byte) 0xFF, 0x07 // color
    };

    AFPParserConfiguration config = new AFPParserConfiguration();
    config.setInputStream(new ByteArrayInputStream(data));
    AFPParser parser = new AFPParser(config);
    LPD_LogicalPageDescriptor lpd = (LPD_LogicalPageDescriptor) parser.parseNextSF();

    assertNotNull(lpd);
    assertEquals(AFPUnitBase.Inches10, lpd.getUnitBase());
    assertEquals(1000, lpd.getXpExtent());
    assertEquals(2000, lpd.getYpExtent());
    assertEquals(AFPOrientation.ori0, lpd.getiAxisOrientation());
    assertEquals(AFPOrientation.ori90, lpd.getbAxisOrientation());
    assertEquals(1, lpd.getLid());
    assertEquals(AFPColorValue.White_DeviceDefault_0xFF07, lpd.getColor());

    ByteArrayOutputStream baos = new ByteArrayOutputStream();
    lpd.writeAFP(baos, config);
    assertArrayEquals(data, baos.toByteArray());
  }

  @Test
  public void testLPPRoundTrip() throws Exception {
    // LPP: D66D, ARQ=0, xmOffset=1000, placement=Partition1_FrontSide, ymOffset=2000, orientation=Degree90
    // SFI length = 8 + 1 (IPDS header) + 10 (payload) = 19 (0x13)
    byte[] data = new byte[] {
        0x5A, 0x00, 0x13, (byte) 0xD6, (byte) 0x6D, 0x00, 0x00, 0x00, 0x00,
        0x00, // flagByte
        0x00, // reserved
        0x00, 0x03, (byte) 0xE8, // xmOffset (1000)
        0x10, // placement
        0x00, 0x07, (byte) 0xD0, // ymOffset (2000)
        0x2D, 0x00 // orientation (90)
    };

    AFPParserConfiguration config = new AFPParserConfiguration();
    config.setInputStream(new ByteArrayInputStream(data));
    AFPParser parser = new AFPParser(config);
    LPP_LogicalPagePosition lpp = (LPP_LogicalPagePosition) parser.parseNextSF();

    assertNotNull(lpp);
    assertEquals(1000, lpp.getXmPageOffset());
    assertEquals(LPP_LogicalPagePosition.LPP_Placement.Partition1_FrontSide, lpp.getPlacement());
    assertEquals(2000, lpp.getYmPageOffset());
    assertEquals(AFPOrientation.ori90, lpp.getOrientation());

    ByteArrayOutputStream baos = new ByteArrayOutputStream();
    lpp.writeAFP(baos, config);
    assertArrayEquals(data, baos.toByteArray());
  }

  @Test
  public void testLFERoundTrip() throws Exception {
    // LFE: D63F, ARQ=0, 1 RG (16 bytes)
    // SFI length = 8 + 1 (IPDS header) + 16 (RG) = 25 (0x19)
    byte[] data = new byte[] {
        0x5A, 0x00, 0x19, (byte) 0xD6, (byte) 0x3F, 0x00, 0x00, 0x00, 0x00,
        0x00, // flagByte
        0x01, // lid
        0x12, 0x34, // haid
        0x00, 0x00, // fis
        0x00, 0x01, // gcsgid
        0x01, (byte) 0xF4, // cpgid (500)
        0x12, 0x34, // fgid
        0x00, 0x64, // fw (100)
        0x00, // reserved
        (byte) 0x80, // flags (symbol set)
        0x00  // reserved
    };

    AFPParserConfiguration config = new AFPParserConfiguration();
    config.setInputStream(new ByteArrayInputStream(data));
    AFPParser parser = new AFPParser(config);
    LFE_LoadFontEquivalence lfe = (LFE_LoadFontEquivalence) parser.parseNextSF();

    assertNotNull(lfe);
    assertEquals(1, lfe.getRepeatingGroups().size());
    LFE_LoadFontEquivalence.LFE_RepeatingGroup rg = (LFE_LoadFontEquivalence.LFE_RepeatingGroup) lfe.getRepeatingGroups().get(0);
    assertEquals(1, rg.getLid());
    assertEquals(0x1234, rg.getHaid());
    assertEquals(500, rg.getCpgid());
    assertEquals((byte) 0x80, rg.getFlags());

    ByteArrayOutputStream baos = new ByteArrayOutputStream();
    lfe.writeAFP(baos, config);
    assertArrayEquals(data, baos.toByteArray());
  }

  @Test
  public void testPFCRoundTrip() throws Exception {
    // PFC: D634, ARQ=1, CID=0x1122, flags=0x80, no triplets
    // SFI length = 8 + 3 (IPDS header) + 4 (payload) = 15 (0x0F)
    byte[] data = new byte[] {
        0x5A, 0x00, 0x0F, (byte) 0xD6, (byte) 0x34, 0x00, 0x00, 0x00, 0x00,
        (byte) 0x80, // Flag: ARQ
        0x11, 0x22,  // CID
        0x00, // reserved
        (byte) 0x80, // flags
        0x00, 0x00 // reserved
    };

    AFPParserConfiguration config = new AFPParserConfiguration();
    config.setInputStream(new ByteArrayInputStream(data));
    AFPParser parser = new AFPParser(config);
    PFC_PresentationFidelityControl pfc = (PFC_PresentationFidelityControl) parser.parseNextSF();

    assertNotNull(pfc);
    assertTrue(pfc.isAcknowledgementRequired());
    assertEquals(0x1122, pfc.getCorrelationId());
    assertEquals((byte) 0x80, pfc.getFidelityControlFlags());

    ByteArrayOutputStream baos = new ByteArrayOutputStream();
    pfc.writeAFP(baos, config);
    assertArrayEquals(data, baos.toByteArray());
  }

  @Test
  public void testSPERoundTrip() throws Exception {
    // SPE: D608, ARQ=1, CID=0x1234, Reserved=0000, 1 Triplet (Device Appearance 0x97)
    // Device Appearance: 07 97 00 01 00 00 00 -> Length=7, ID=97, Reserved=00, Appearance=01 (DeviceDefaultMonochrome), Reserved=00, Reserved=0000
    // SFI length = 8 + 3 (IPDS header) + 2 (reserved) + 7 (triplet) = 20 (0x14)
    byte[] data = new byte[] {
        0x5A, 0x00, 0x14, (byte) 0xD6, (byte) 0x08, 0x00, 0x00, 0x00, 0x00,
        (byte) 0x80, // Flag: ARQ
        0x12, 0x34,  // CID
        0x00, 0x00,  // Reserved
        0x07, (byte) 0x97, 0x00, 0x01, 0x00, 0x00, 0x00 // Device Appearance Triplet
    };

    AFPParserConfiguration config = new AFPParserConfiguration();
    config.setInputStream(new ByteArrayInputStream(data));
    AFPParser parser = new AFPParser(config);
    SPE_SetPresentationEnvironment spe = (SPE_SetPresentationEnvironment) parser.parseNextSF();

    assertNotNull(spe);
    assertTrue(spe.isAcknowledgementRequired());
    assertEquals(0x1234, spe.getCorrelationId());
    assertEquals(1, spe.getTriplets().size());
    assertTrue(spe.getTriplets().get(0) instanceof com.mgz.afp.triplets.Triplet.DeviceAppearance);

    ByteArrayOutputStream baos = new ByteArrayOutputStream();
    spe.writeAFP(baos, config);
    assertArrayEquals(data, baos.toByteArray());
  }
}
