package com.mgz.afp.foca;

import com.mgz.afp.RoundTripTestUtils;
import org.junit.jupiter.api.Test;

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

    @Test
    public void testFNNRoundTrip() throws Exception {
        // FNN: D3AB89
        // Section 1: 02 03
        // Section 2 (12 bytes): LA010000 (D3 C1 F0 F1 F0 F0 F0 F0) 00 00 00 5F
        // Section 3 (2 bytes): 02 61 ('a')
        // Total Len: 1 + 8 + 2 + 12 + 2 = 25. SFLen = 24 (0x0018)
        byte[] data = new byte[] {
            0x5A, 0x00, 0x18, (byte) 0xD3, (byte) 0xAB, (byte) 0x89, 0x00, 0x00, 0x00,
            0x02, 0x03,
            (byte) 0xD3, (byte) 0xC1, (byte) 0xF0, (byte) 0xF1, (byte) 0xF0, (byte) 0xF0, (byte) 0xF0, (byte) 0xF0,
            0x00, 0x00, 0x00, 0x5F,
            0x02, 0x61
        };

        // We need an AFPParserConfiguration with FNC set to parse Section 2
        com.mgz.afp.parser.AFPParserConfiguration config = new com.mgz.afp.parser.AFPParserConfiguration();
        FNC_FontControl fnc = new FNC_FontControl();
        fnc.setFnnIBMNameGCGIDCount(1);
        fnc.setFnnRepeatingGroupLength((byte) 12);
        config.setCurrentFontControl(fnc);

        RoundTripTestUtils.assertRoundTrip(new FNN_FontNameMap(), data, config);
    }

    @Test
    public void testCFCRoundTrip() throws Exception {
        // CFC: D3A78A
        // Payload: CFIRepeatingGroupLength(1) | Retired(1) | Triplet(6)
        // Total Len: 1 + 8 + 8 = 17. SFLen = 16 (0x0010)
        byte[] data = new byte[] {
            0x5A, 0x00, 0x10, (byte) 0xD3, (byte) 0xA7, (byte) 0x8A, 0x00, 0x00, 0x00,
            0x19, 0x01, 0x06, 0x65, (byte) 0xE3, (byte) 0xC5, (byte) 0xE2, (byte) 0xE3
        };
        RoundTripTestUtils.assertRoundTrip(new CFC_CodedFontControl(), data);
    }

    @Test
    public void testCPDRoundTrip() throws Exception {
        // CPD: D3A687
        // Payload: desc(32) | gcgidLen(2) | count(4) | gcsgid(2) | cpgid(2) | enc(2) -> 44 bytes
        // Total Len: 1 + 8 + 44 = 53. SFLen = 52 (0x0034)
        byte[] data = new byte[53];
        data[0] = 0x5A;
        data[1] = 0x00;
        data[2] = 0x34;
        data[3] = (byte) 0xD3;
        data[4] = (byte) 0xA6;
        data[5] = (byte) 0x87;
        // desc: "TEST CODE PAGE" in EBCDIC (CP500)
        byte[] desc = "TEST CODE PAGE                  ".getBytes("cp500");
        System.arraycopy(desc, 0, data, 9, 32);
        // gcgidLen = 8
        data[41] = 0x00; data[42] = 0x08;
        // count = 256 (00000100)
        data[43] = 0x00; data[44] = 0x00; data[45] = 0x01; data[46] = 0x00;
        // gcsgid = 697 (02B9)
        data[47] = 0x02; data[48] = (byte) 0xB9;
        // cpgid = 500 (01F4)
        data[49] = 0x01; data[50] = (byte) 0xF4;
        // enc = 0x6100 (SingleByte_EBCDICPresentation)
        data[51] = 0x61; data[52] = 0x00;

        RoundTripTestUtils.assertRoundTrip(new CPD_CodePageDescriptor(), data);
    }
}
