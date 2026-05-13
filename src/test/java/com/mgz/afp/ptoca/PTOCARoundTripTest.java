package com.mgz.afp.ptoca;

import com.mgz.afp.RoundTripTestUtils;
import org.junit.jupiter.api.Test;

public class PTOCARoundTripTest {

    @Test
    public void testBPTRoundTrip() throws Exception {
        // BPT: D3A89B
        // Name (8): TEXTOBJ1 (E3 C5 E7 E3 D6 C2 D1 F1)
        // Triplet (6): Comment "TEST" (06 65 E3 C5 E2 E3)
        // Total Len: 1 + 8 + 14 = 23. SFLen = 22 (0x0016)
        byte[] data = new byte[] {
            0x5A, 0x00, 0x16, (byte) 0xD3, (byte) 0xA8, (byte) 0x9B, 0x00, 0x00, 0x00,
            (byte) 0xE3, (byte) 0xC5, (byte) 0xE7, (byte) 0xE3, (byte) 0xD6, (byte) 0xC2, (byte) 0xD1, (byte) 0xF1,
            0x06, 0x65, (byte) 0xE3, (byte) 0xC5, (byte) 0xE2, (byte) 0xE3
        };
        RoundTripTestUtils.assertRoundTrip(new BPT_BeginPresentationTextObject(), data);
    }

    @Test
    public void testEPTRoundTrip() throws Exception {
        // EPT: D3A99B
        // Name (8): TEXTOBJ1
        // Triplet (6): Comment "TEST" (06 65 E3 C5 E2 E3)
        // Total Len: 1 + 8 + 14 = 23. SFLen = 22 (0x0016)
        byte[] data = new byte[] {
            0x5A, 0x00, 0x16, (byte) 0xD3, (byte) 0xA9, (byte) 0x9B, 0x00, 0x00, 0x00,
            (byte) 0xE3, (byte) 0xC5, (byte) 0xE7, (byte) 0xE3, (byte) 0xD6, (byte) 0xC2, (byte) 0xD1, (byte) 0xF1,
            0x06, 0x65, (byte) 0xE3, (byte) 0xC5, (byte) 0xE2, (byte) 0xE3
        };
        RoundTripTestUtils.assertRoundTrip(new EPT_EndPresentationTextObject(), data);
    }

    @Test
    public void testPTXRoundTrip() throws Exception {
        // PTX: D3EE9B
        // Control Sequence: SCFL (Set Coded Font Local) 2B D3 03 F0 01
        // Prefix: 2B, Class: D3, Len: 03, Type: F0 (SCFL), Data: 01
        // Total Payload: 5 bytes
        // Total Len: 1 + 8 + 5 = 14. SFLen = 13 (0x000D)
        byte[] data = new byte[] {
            0x5A, 0x00, 0x0D, (byte) 0xD3, (byte) 0xEE, (byte) 0x9B, 0x00, 0x00, 0x00,
            0x2B, (byte) 0xD3, 0x03, (byte) 0xF0, 0x01
        };
        RoundTripTestUtils.assertRoundTrip(new PTX_PresentationTextData(), data);
    }
}
