package com.mgz.afp.cmoca;

import com.mgz.afp.RoundTripTestUtils;
import org.junit.jupiter.api.Test;

public class CMRRoundTripTest {

    @Test
    public void testCMRRoundTrip() throws Exception {
        // CMR: D3A0A2
        // Length (4): 164
        // Signature (4): "CMR9" (C3 D4 D9 F9)
        // Reserved1 (2): 0x0000
        // CMR Name (146 bytes): Padded with 0x0040
        //   Alias (16): "ALIAS1" (00 41 00 4C 00 49 00 41 00 53 00 31 00 40...)
        // Reserved3 (8): 0x0000000000000000
        // Data (4): 0x01 02 03 04
        // Total Payload: 164 + 4 = 168
        // Total Len: 1 + 8 + 168 = 177. SFLen = 176 (0x00B0)

        byte[] data = new byte[177];
        data[0] = 0x5A;
        data[1] = 0x00;
        data[2] = (byte) 0xB0;
        data[3] = (byte) 0xD3;
        data[4] = (byte) 0xA0;
        data[5] = (byte) 0xA2;

        // Payload at offset 9
        // Length = 168 (0x000000A8)
        data[12] = (byte) 0xA8;
        // Signature "CMR9"
        data[13] = (byte) 0xC3;
        data[14] = (byte) 0xD4;
        data[15] = (byte) 0xD9;
        data[16] = (byte) 0xF9;

        // Padded CMR Name (Bytes 10-155 of payload, which is 19-164 of data)
        for (int i = 19; i < 165; i += 2) {
            data[i] = 0x00;
            data[i+1] = 0x40;
        }

        // Data
        data[173] = 0x01;
        data[174] = 0x02;
        data[175] = 0x03;
        data[176] = 0x04;

        RoundTripTestUtils.assertRoundTrip(new CMR_ColorManagementResource(), data);
    }
}
