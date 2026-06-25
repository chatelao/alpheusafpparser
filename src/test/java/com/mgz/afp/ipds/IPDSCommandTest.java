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
import static org.junit.jupiter.api.Assertions.assertThrows;
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
  public void testWIC2InfiniteLoopFix() throws Exception {
    // WIC2 with an SDF of length 0 (would cause infinite loop before fix)
    byte[] data = new byte[] {
        0x5A, 0x00, 0x0D, (byte) 0xD6, (byte) 0x3E, 0x00, 0x00, 0x00, 0x00,
        0x00, // flagByte
        0x00, 0x00, 0x00, 0x00 // Length 0 SDF
    };

    AFPParserConfiguration config = new AFPParserConfiguration();
    config.setInputStream(new ByteArrayInputStream(data));
    AFPParser parser = new AFPParser(config);

    assertThrows(com.mgz.afp.exceptions.AFPParserException.class, () -> {
      parser.parseNextSF();
    });
  }

  @Test
  public void testWICRoundTrip() throws Exception {
    // WIC: D63D, ARQ=0, payload=24 bytes + 2 bytes color = 26. SFI(8) + 1 (flag) + 26 = 35 (0x23)
    byte[] data = new byte[] {
        0x5A, 0x00, 0x23, (byte) 0xD6, (byte) 0x3D, 0x00, 0x00, 0x00, 0x00,
        0x00, // flagByte
        0x03, (byte) 0xE8, // ppslOutput (1000)
        0x07, (byte) 0xD0, // nslOutput (2000)
        0x03, (byte) 0xE8, // ppslInput (1000)
        0x07, (byte) 0xD0, // nslInput (2000)
        0x00, // compress
        0x00, // bitsPerPel
        0x01, // pelMag
        0x01, // scanLineMag
        0x00, 0x00, // slDirection
        0x2D, 0x00, // slsDirection
        (byte) 0xA0, // rcs (Page Xp, Yp)
        0x00, 0x00, 0x64, // xOffset (100)
        0x00, // reserved
        0x00, 0x00, (byte) 0xC8, // yOffset (200)
        (byte) 0xFF, 0x07 // color
    };

    AFPParserConfiguration config = new AFPParserConfiguration();
    config.setInputStream(new ByteArrayInputStream(data));
    AFPParser parser = new AFPParser(config);
    WIC_WriteImageControl wic = (WIC_WriteImageControl) parser.parseNextSF();

    assertNotNull(wic);
    assertEquals(1000, wic.getPpslOutput());
    assertEquals(2000, wic.getNslOutput());
    assertEquals(100, wic.getXOffset());
    assertEquals(AFPColorValue.White_DeviceDefault_0xFF07, wic.getColor());

    ByteArrayOutputStream baos = new ByteArrayOutputStream();
    wic.writeAFP(baos, config);
    assertArrayEquals(data, baos.toByteArray());
  }

  @Test
  public void testWIRoundTrip() throws Exception {
    // WI: D64D, ARQ=0, data: 11 22 33 44
    byte[] data = new byte[] {
        0x5A, 0x00, 0x0D, (byte) 0xD6, (byte) 0x4D, 0x00, 0x00, 0x00, 0x00,
        0x00, // flagByte
        0x11, 0x22, 0x33, 0x44
    };

    AFPParserConfiguration config = new AFPParserConfiguration();
    config.setInputStream(new ByteArrayInputStream(data));
    AFPParser parser = new AFPParser(config);
    WI_WriteImage wi = (WI_WriteImage) parser.parseNextSF();

    assertNotNull(wi);
    assertArrayEquals(new byte[] {0x11, 0x22, 0x33, 0x44}, wi.getImageData());

    ByteArrayOutputStream baos = new ByteArrayOutputStream();
    wi.writeAFP(baos, config);
    assertArrayEquals(data, baos.toByteArray());
  }

  @Test
  public void testWIC2RoundTrip() throws Exception {
    // WIC2: D63E, ARQ=0, IAP + IDD
    // IAP: 00 0B AC 6B 00 64 00 C8 00 00 A0 (x=100, y=200, ori=0, rcs=A0)
    // IDD: 00 0F A6 FB 00 00 00 05 A0 05 A0 03 E8 07 D0 (haid=0, unit=0, res=1440, ext=1000x2000)
    byte[] data = new byte[] {
        0x5A, 0x00, 0x23, (byte) 0xD6, (byte) 0x3E, 0x00, 0x00, 0x00, 0x00,
        0x00, // flagByte
        0x00, 0x0B, (byte) 0xAC, 0x6B, 0x00, 0x64, 0x00, (byte) 0xC8, 0x00, 0x00, (byte) 0xA0, // IAP
        0x00, 0x0F, (byte) 0xA6, (byte) 0xFB, 0x00, 0x00, 0x00, 0x05, (byte) 0xA0, 0x05, (byte) 0xA0, 0x03, (byte) 0xE8, 0x07, (byte) 0xD0 // IDD
    };

    AFPParserConfiguration config = new AFPParserConfiguration();
    config.setInputStream(new ByteArrayInputStream(data));
    AFPParser parser = new AFPParser(config);
    WIC2_WriteImageControl2 wic2 = (WIC2_WriteImageControl2) parser.parseNextSF();

    assertNotNull(wic2);
    assertEquals(100, wic2.getIap().getXOffset());
    assertEquals(200, wic2.getIap().getYOffset());
    assertEquals(1000, wic2.getIdd().getXioExtent());
    assertEquals(2000, wic2.getIdd().getYioExtent());

    ByteArrayOutputStream baos = new ByteArrayOutputStream();
    wic2.writeAFP(baos, config);
    assertArrayEquals(data, baos.toByteArray());
  }

  @Test
  public void testWI2RoundTrip() throws Exception {
    // WI2: D64E, ARQ=0, data: 55 66 77 88
    byte[] data = new byte[] {
        0x5A, 0x00, 0x0D, (byte) 0xD6, (byte) 0x4E, 0x00, 0x00, 0x00, 0x00,
        0x00, // flagByte
        0x55, 0x66, 0x77, (byte) 0x88
    };

    AFPParserConfiguration config = new AFPParserConfiguration();
    config.setInputStream(new ByteArrayInputStream(data));
    AFPParser parser = new AFPParser(config);
    WI2_WriteImage2 wi2 = (WI2_WriteImage2) parser.parseNextSF();

    assertNotNull(wi2);
    assertArrayEquals(new byte[] {0x55, 0x66, 0x77, (byte) 0x88}, wi2.getIocaData());

    ByteArrayOutputStream baos = new ByteArrayOutputStream();
    wi2.writeAFP(baos, config);
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

  @Test
  public void testLERoundTrip() throws Exception {
    // LE: D61D, ARQ=0, mappingType=0x0100, 1 RG (0x0001, 0x0002)
    // SFI length = 8 + 1 (IPDS header) + 2 (type) + 4 (RG) = 15 (0x0F)
    byte[] data = new byte[] {
        0x5A, 0x00, 0x0F, (byte) 0xD6, (byte) 0x1D, 0x00, 0x00, 0x00, 0x00,
        0x00, // flagByte
        0x01, 0x00, // mappingType
        0x00, 0x01, // internal
        0x00, 0x02  // external
    };

    AFPParserConfiguration config = new AFPParserConfiguration();
    config.setInputStream(new ByteArrayInputStream(data));
    AFPParser parser = new AFPParser(config);
    LE_LoadEquivalence le = (LE_LoadEquivalence) parser.parseNextSF();

    assertNotNull(le);
    assertEquals(0x0100, le.getMappingType());
    assertEquals(1, le.getRepeatingGroups().size());
    LE_LoadEquivalence.LE_RepeatingGroup rg = (LE_LoadEquivalence.LE_RepeatingGroup) le.getRepeatingGroups().get(0);
    assertEquals(1, rg.getInternal());
    assertEquals(2, rg.getExternal());

    ByteArrayOutputStream baos = new ByteArrayOutputStream();
    le.writeAFP(baos, config);
    assertArrayEquals(data, baos.toByteArray());
  }

  @Test
  public void testWTRoundTrip() throws Exception {
    // WT: D62D, ARQ=0, PTOCA data: TRN "Hi" (2B D3 04 93 48 69) - Wait, TRN opcode is 0xDA/0xDB
    // TRN chained: 2B D3 04 DB C8 C9 (in EBCDIC C8 C9 is 'HI')
    // SFI length = 8 + 1 (IPDS header) + 6 (PTOCA) = 15 (0x0F)
    byte[] data = new byte[] {
        0x5A, 0x00, 0x0F, (byte) 0xD6, (byte) 0x2D, 0x00, 0x00, 0x00, 0x00,
        0x00, // flagByte
        0x2B, (byte) 0xD3, 0x04, (byte) 0xDB, (byte) 0xC8, (byte) 0xC9
    };

    AFPParserConfiguration config = new AFPParserConfiguration();
    config.setInputStream(new ByteArrayInputStream(data));
    AFPParser parser = new AFPParser(config);
    WT_WriteText wt = (WT_WriteText) parser.parseNextSF();

    assertNotNull(wt);
    assertEquals(1, wt.getControlSequences().size());
    assertTrue(wt.getControlSequences().get(0) instanceof com.mgz.afp.ptoca.controlSequence.PTOCAControlSequence.TRN_TransparentData);

    ByteArrayOutputStream baos = new ByteArrayOutputStream();
    wt.writeAFP(baos, config);
    assertArrayEquals(data, baos.toByteArray());
  }

  @Test
  public void testBORoundTrip() throws Exception {
    // BO: D6DF, ARQ=0, basic Overlay ID=0x01
    byte[] dataBasic = new byte[] {
        0x5A, 0x00, 0x0A, (byte) 0xD6, (byte) 0xDF, 0x00, 0x00, 0x00, 0x00,
        0x00, // flagByte
        0x01  // Overlay ID
    };

    AFPParserConfiguration config = new AFPParserConfiguration();
    config.setInputStream(new ByteArrayInputStream(dataBasic));
    AFPParser parser = new AFPParser(config);
    BO_BeginOverlay boBasic = (BO_BeginOverlay) parser.parseNextSF();
    assertNotNull(boBasic);
    assertEquals(1, boBasic.getOverlayHaid());

    ByteArrayOutputStream baos = new ByteArrayOutputStream();
    boBasic.writeAFP(baos, config);
    assertArrayEquals(dataBasic, baos.toByteArray());

    // BO: D6DF, ARQ=1, CID=0x1234, extended HAID=0x1234
    byte[] dataExtended = new byte[] {
        0x5A, 0x00, 0x0D, (byte) 0xD6, (byte) 0xDF, 0x00, 0x00, 0x00, 0x00,
        (byte) 0x80, // Flag: ARQ
        0x12, 0x34,  // CID
        0x12, 0x34   // HAID
    };
    config.setInputStream(new ByteArrayInputStream(dataExtended));
    parser = new AFPParser(config);
    BO_BeginOverlay boExtended = (BO_BeginOverlay) parser.parseNextSF();
    assertNotNull(boExtended);
    assertEquals(0x1234, boExtended.getOverlayHaid());

    baos = new ByteArrayOutputStream();
    boExtended.writeAFP(baos, config);
    assertArrayEquals(dataExtended, baos.toByteArray());
  }

  @Test
  public void testDORoundTrip() throws Exception {
    // DO: D6EF, ARQ=0, basic Overlay ID=0x00 (Deactivate All)
    byte[] dataBasic = new byte[] {
        0x5A, 0x00, 0x0A, (byte) 0xD6, (byte) 0xEF, 0x00, 0x00, 0x00, 0x00,
        0x00, // flagByte
        0x00  // Deactivate All
    };

    AFPParserConfiguration config = new AFPParserConfiguration();
    config.setInputStream(new ByteArrayInputStream(dataBasic));
    AFPParser parser = new AFPParser(config);
    DO_DeactivateOverlay doBasic = (DO_DeactivateOverlay) parser.parseNextSF();
    assertNotNull(doBasic);
    assertEquals(0, doBasic.getOverlayHaid());

    ByteArrayOutputStream baos = new ByteArrayOutputStream();
    doBasic.writeAFP(baos, config);
    assertArrayEquals(dataBasic, baos.toByteArray());

    // DO: D6EF, ARQ=1, CID=0x5555, extended HAID=0x7EFF
    byte[] dataExtended = new byte[] {
        0x5A, 0x00, 0x0D, (byte) 0xD6, (byte) 0xEF, 0x00, 0x00, 0x00, 0x00,
        (byte) 0x80, // Flag: ARQ
        0x55, 0x55,  // CID
        0x7E, (byte) 0xFF   // HAID
    };
    config.setInputStream(new ByteArrayInputStream(dataExtended));
    parser = new AFPParser(config);
    DO_DeactivateOverlay doExtended = (DO_DeactivateOverlay) parser.parseNextSF();
    assertNotNull(doExtended);
    assertEquals(0x7EFF, doExtended.getOverlayHaid());

    baos = new ByteArrayOutputStream();
    doExtended.writeAFP(baos, config);
    assertArrayEquals(dataExtended, baos.toByteArray());
  }

  @Test
  public void testIORoundTrip() throws Exception {
    // IO: D67D, ARQ=0, HAID=0x1234, type=0, Xp=0x112233, use=1, Yp=0x445566, Ori=0
    byte[] data = new byte[] {
        0x5A, 0x00, 0x15, (byte) 0xD6, (byte) 0x7D, 0x00, 0x00, 0x00, 0x00,
        0x00, // flagByte
        0x12, 0x34, // HAID
        0x00, // type
        0x11, 0x22, 0x33, // xpOffset
        0x01, // overlayUse
        0x44, 0x55, 0x66, // ypOffset
        0x00, 0x00 // orientation
    };

    AFPParserConfiguration config = new AFPParserConfiguration();
    config.setInputStream(new ByteArrayInputStream(data));
    AFPParser parser = new AFPParser(config);
    IO_IncludeOverlay io = (IO_IncludeOverlay) parser.parseNextSF();

    assertNotNull(io);
    assertEquals(0x1234, io.getOverlayHaid());
    assertEquals(0, io.getOverlayType());
    assertEquals(0x112233, io.getXpOffset());
    assertEquals(1, io.getOverlayUse());
    assertEquals(0x445566, io.getYpOffset());
    assertEquals(AFPOrientation.ori0, io.getOrientation());

    ByteArrayOutputStream baos = new ByteArrayOutputStream();
    io.writeAFP(baos, config);
    assertArrayEquals(data, baos.toByteArray());

    // Test without orientation
    byte[] dataShort = new byte[] {
        0x5A, 0x00, 0x13, (byte) 0xD6, (byte) 0x7D, 0x00, 0x00, 0x00, 0x00,
        0x00, // flagByte
        0x12, 0x34, // HAID
        0x00, // type
        0x11, 0x22, 0x33, // xpOffset
        0x01, // overlayUse
        0x44, 0x55, 0x66  // ypOffset
    };
    config.setInputStream(new ByteArrayInputStream(dataShort));
    parser = new AFPParser(config);
    io = (IO_IncludeOverlay) parser.parseNextSF();
    assertNotNull(io);
    assertTrue(io.getOrientation() == null);

    baos = new ByteArrayOutputStream();
    io.writeAFP(baos, config);
    assertArrayEquals(dataShort, baos.toByteArray());
  }

  @Test
  public void testBPSRoundTrip() throws Exception {
    // BPS: D65F, ARQ=0, HAID=0x1234
    byte[] data = new byte[] {
        0x5A, 0x00, 0x0B, (byte) 0xD6, (byte) 0x5F, 0x00, 0x00, 0x00, 0x00,
        0x00, // flagByte
        0x12, 0x34 // HAID
    };

    AFPParserConfiguration config = new AFPParserConfiguration();
    config.setInputStream(new ByteArrayInputStream(data));
    AFPParser parser = new AFPParser(config);
    BPS_BeginPageSegment bps = (BPS_BeginPageSegment) parser.parseNextSF();

    assertNotNull(bps);
    assertEquals(0x1234, bps.getHaid());

    ByteArrayOutputStream baos = new ByteArrayOutputStream();
    bps.writeAFP(baos, config);
    assertArrayEquals(data, baos.toByteArray());
  }

  @Test
  public void testDPSRoundTrip() throws Exception {
    // DPS: D66F, ARQ=1, CID=0xABCD, HAID=0x0000 (Deactivate All)
    byte[] data = new byte[] {
        0x5A, 0x00, 0x0D, (byte) 0xD6, (byte) 0x6F, 0x00, 0x00, 0x00, 0x00,
        (byte) 0x80, // flagByte: ARQ
        (byte) 0xAB, (byte) 0xCD, // CID
        0x00, 0x00 // HAID
    };

    AFPParserConfiguration config = new AFPParserConfiguration();
    config.setInputStream(new ByteArrayInputStream(data));
    AFPParser parser = new AFPParser(config);
    DPS_DeactivatePageSegment dps = (DPS_DeactivatePageSegment) parser.parseNextSF();

    assertNotNull(dps);
    assertEquals(0, dps.getHaid());
    assertEquals(0xABCD, dps.getCorrelationId());

    ByteArrayOutputStream baos = new ByteArrayOutputStream();
    dps.writeAFP(baos, config);
    assertArrayEquals(data, baos.toByteArray());
  }

  @Test
  public void testIPSRoundTrip() throws Exception {
    // IPS: D67F, ARQ=0, HAID=0x5555
    byte[] data = new byte[] {
        0x5A, 0x00, 0x0B, (byte) 0xD6, (byte) 0x7F, 0x00, 0x00, 0x00, 0x00,
        0x00, // flagByte
        0x55, 0x55 // HAID
    };

    AFPParserConfiguration config = new AFPParserConfiguration();
    config.setInputStream(new ByteArrayInputStream(data));
    AFPParser parser = new AFPParser(config);
    IPS_IncludePageSegment ips = (IPS_IncludePageSegment) parser.parseNextSF();

    assertNotNull(ips);
    assertEquals(0x5555, ips.getHaid());

    ByteArrayOutputStream baos = new ByteArrayOutputStream();
    ips.writeAFP(baos, config);
    assertArrayEquals(data, baos.toByteArray());
  }
}
