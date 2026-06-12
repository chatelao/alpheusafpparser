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
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.mgz.afp.RoundTripTestUtils;
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

    SHS_SetHomeState shs = new SHS_SetHomeState();
    RoundTripTestUtils.assertRoundTrip(shs, data);
    assertTrue(shs.isAcknowledgementRequired());
    assertArrayEquals(new byte[] {0x12, 0x34}, com.mgz.util.UtilBinaryDecoding.intToByteArray(shs.getCorrelationId(), 2));
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

    NOP_NoOperation nop = new NOP_NoOperation();
    RoundTripTestUtils.assertRoundTrip(nop, data);
    assertArrayEquals(new byte[] {0x11, 0x22, 0x33}, nop.getBinaryData());
  }
}
