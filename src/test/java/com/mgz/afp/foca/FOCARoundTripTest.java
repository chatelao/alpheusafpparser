package com.mgz.afp.foca;

import com.mgz.afp.RoundTripTestUtils;
import org.junit.Test;

public class FOCARoundTripTest {

    @Test
    public void testBCFRoundTrip() throws Exception {
        // BCF: D3A88A
        // Name (8): FONTNAME (C6 D6 D5 E3 D5 C1 D4 C5)
        // No triplets in this test
        // Total Len: 1 + 8 + 8 = 17. SFLen = 16 (0x0010)
        byte[] data = new byte[] {
            0x5A, 0x00, 0x10, (byte) 0xD3, (byte) 0xA8, (byte) 0x8A, 0x00, 0x00, 0x00,
            (byte) 0xC6, (byte) 0xD6, (byte) 0xD5, (byte) 0xE3, (byte) 0xD5, (byte) 0xC1, (byte) 0xD4, (byte) 0xC5
        };
        RoundTripTestUtils.assertRoundTrip(new BCF_BeginCodedFont(), data);
    }

    @Test
    public void testECFRoundTrip() throws Exception {
        // ECF: D3A98A
        // Name (8): FONTNAME
        byte[] data = new byte[] {
            0x5A, 0x00, 0x10, (byte) 0xD3, (byte) 0xA9, (byte) 0x8A, 0x00, 0x00, 0x00,
            (byte) 0xC6, (byte) 0xD6, (byte) 0xD5, (byte) 0xE3, (byte) 0xD5, (byte) 0xC1, (byte) 0xD4, (byte) 0xC5
        };
        RoundTripTestUtils.assertRoundTrip(new ECF_EndCodedFont(), data);
    }

    @Test
    public void testBCPRoundTrip() throws Exception {
        // BCP: D3A887
        // Name (8): CPNAME11 (C3 D7 D5 C1 D4 C5 F1 F1)
        // Triplet (6): Comment "TEST" (06 65 E3 C5 E2 E3)
        // Total Len: 1 + 8 + 8 + 6 = 23. SFLen = 22 (0x0016)
        byte[] data = new byte[] {
            0x5A, 0x00, 0x16, (byte) 0xD3, (byte) 0xA8, (byte) 0x87, 0x00, 0x00, 0x00,
            (byte) 0xC3, (byte) 0xD7, (byte) 0xD5, (byte) 0xC1, (byte) 0xD4, (byte) 0xC5, (byte) 0xF1, (byte) 0xF1,
            0x06, 0x65, (byte) 0xE3, (byte) 0xC5, (byte) 0xE2, (byte) 0xE3
        };
        RoundTripTestUtils.assertRoundTrip(new BCP_BeginCodePage(), data);
    }

    @Test
    public void testECPRoundTrip() throws Exception {
        // ECP: D3A987
        // Name (8): CPNAME11
        byte[] data = new byte[] {
            0x5A, 0x00, 0x10, (byte) 0xD3, (byte) 0xA9, (byte) 0x87, 0x00, 0x00, 0x00,
            (byte) 0xC3, (byte) 0xD7, (byte) 0xD5, (byte) 0xC1, (byte) 0xD4, (byte) 0xC5, (byte) 0xF1, (byte) 0xF1
        };
        RoundTripTestUtils.assertRoundTrip(new ECP_EndCodePage(), data);
    }

    @Test
    public void testBFNRoundTrip() throws Exception {
        // BFN: D3A889
        // Name (8): CSNAME11 (C3 E2 D5 C1 D4 C5 F1 F1)
        // Triplet (6): Comment "TEST" (06 65 E3 C5 E2 E3)
        byte[] data = new byte[] {
            0x5A, 0x00, 0x16, (byte) 0xD3, (byte) 0xA8, (byte) 0x89, 0x00, 0x00, 0x00,
            (byte) 0xC3, (byte) 0xE2, (byte) 0xD5, (byte) 0xC1, (byte) 0xD4, (byte) 0xC5, (byte) 0xF1, (byte) 0xF1,
            0x06, 0x65, (byte) 0xE3, (byte) 0xC5, (byte) 0xE2, (byte) 0xE3
        };
        RoundTripTestUtils.assertRoundTrip(new BFN_BeginFont(), data);
    }

    @Test
    public void testEFNRoundTrip() throws Exception {
        // EFN: D3A989
        // Name (8): CSNAME11
        byte[] data = new byte[] {
            0x5A, 0x00, 0x10, (byte) 0xD3, (byte) 0xA9, (byte) 0x89, 0x00, 0x00, 0x00,
            (byte) 0xC3, (byte) 0xE2, (byte) 0xD5, (byte) 0xC1, (byte) 0xD4, (byte) 0xC5, (byte) 0xF1, (byte) 0xF1
        };
        RoundTripTestUtils.assertRoundTrip(new EFN_EndFont(), data);
    }
}
