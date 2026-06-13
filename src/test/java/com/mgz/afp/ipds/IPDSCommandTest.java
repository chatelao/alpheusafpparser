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
}
