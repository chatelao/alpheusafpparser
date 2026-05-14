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

    @Test
    public void testFNCRoundTrip() throws Exception {
        // FNC: D3A789
        // Payload (min): retired(1) | pti(1) | res(1) | flags(1) | xub(1) | yub(1) | xu(2) | yu(2) | maxw(2) | maxh(2) | fnoL(1) | fniL(1) | align(1) | count(3) | fnpL(1) | fnmL(1)
        // total 22 bytes. SFLen = 8 + 22 = 30 (0x1E)
        byte[] data = new byte[] {
            0x5A, 0x00, 0x1E, (byte) 0xD3, (byte) 0xA7, (byte) 0x89, 0x00, 0x00, 0x00,
            0x01, 0x05, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x1A, 0x00, 0x00, 0x00, 0x00, 0x00, 0x16, 0x00
        };
        RoundTripTestUtils.assertRoundTrip(new FNC_FontControl(), data);
    }

    @Test
    public void testFNDRoundTrip() throws Exception {
        // FND: D3A689
        // Payload (80 bytes minimum). SFLen = 8 + 80 = 88 (0x58)
        byte[] data = new byte[89];
        data[0] = 0x5A; data[1] = 0x00; data[2] = 0x58;
        data[3] = (byte) 0xD3; data[4] = (byte) 0xA6; data[5] = (byte) 0x89;

        // Typeface description (32 bytes)
        byte[] typeface = "HELVETICA                       ".getBytes("cp500");
        System.arraycopy(typeface, 0, data, 9, 32);

        // fontWeightClass (1) = 4 (Medium_Normal)
        data[41] = 0x04;
        // fontWidthClass (1) = 4 (Medium_Normal)
        data[42] = 0x04;

        RoundTripTestUtils.assertRoundTrip(new FND_FontDescriptor(), data);
    }

    @Test
    public void testFNGRoundTrip() throws Exception {
        // FNG: D3EE89
        // Payload 3 bytes. SFLen = 8 + 3 = 11 (0x0B)
        byte[] data = new byte[] {
            0x5A, 0x00, 0x0B, (byte) 0xD3, (byte) 0xEE, (byte) 0x89, 0x00, 0x00, 0x00,
            0x01, 0x02, 0x03
        };
        RoundTripTestUtils.assertRoundTrip(new FNG_FontPatterns(), data);
    }

    @Test
    public void testCFIRoundTrip() throws Exception {
        // CFI: D38C8A
        // RG: fcsName(8) | cpName(8) | svSize(2) | shScale(2) | res(4) | section(1) = 25 bytes.
        // SFLen = 8 + 25 = 33 (0x21)
        byte[] data = new byte[] {
            0x5A, 0x00, 0x21, (byte) 0xD3, (byte) 0x8C, (byte) 0x8A, 0x00, 0x00, 0x00,
            (byte) 0xC6, (byte) 0xD6, (byte) 0xD5, (byte) 0xE3, (byte) 0xD5, (byte) 0xC1, (byte) 0xD4, (byte) 0xC5, // FONTNAME
            (byte) 0xC3, (byte) 0xD7, (byte) 0xD5, (byte) 0xC1, (byte) 0xD4, (byte) 0xC5, (byte) 0xF1, (byte) 0xF1, // CPNAME11
            0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00
        };
        RoundTripTestUtils.assertRoundTrip(new CFI_CodedFontIndex(), data);
    }

    @Test
    public void testCPCRoundTrip() throws Exception {
        // CPC: D3A787
        // Payload (13 bytes minimum). SFLen = 8 + 13 = 21 (0x15)
        byte[] data = new byte[22];
        data[0] = 0x5A; data[1] = 0x00; data[2] = 0x15;
        data[3] = (byte) 0xD3; data[4] = (byte) 0xA7; data[5] = (byte) 0x87;
        // defaultGraphicCharacterGlobalID (8 bytes)
        byte[] gcgid = "        ".getBytes("cp500");
        System.arraycopy(gcgid, 0, data, 9, 8);
        data[17] = 0x00; // defaultCharacterUseFlags
        data[18] = (byte) 0x0A; // cpiRepeatingGroupLength (SingleByteCodePage)
        data[19] = 0x00; // spaceCharacterSectionNumber
        data[20] = 0x40; // spaceCharacterCodePoint (space)
        data[21] = 0x00; // codePageUseFlags

        RoundTripTestUtils.assertRoundTrip(new CPC_CodePageControl(), data);
    }

    @Test
    public void testCPIRoundTrip() throws Exception {
        // CPI: D38C87
        // RG: gcgid(8) | point(1) | res(1) = 10 bytes. SFLen = 8 + 10 = 18 (0x12)
        byte[] data = new byte[] {
            0x5A, 0x00, 0x12, (byte) 0xD3, (byte) 0x8C, (byte) 0x87, 0x00, 0x00, 0x00,
            (byte) 0xD3, (byte) 0xC1, (byte) 0xF0, (byte) 0xF1, (byte) 0xF0, (byte) 0xF0, (byte) 0xF0, (byte) 0xF0, // LA010000
            0x00, 0x00
        };
        com.mgz.afp.parser.AFPParserConfiguration config = new com.mgz.afp.parser.AFPParserConfiguration();
        CPC_CodePageControl cpc = new CPC_CodePageControl();
        cpc.setCpiRepeatingGroupLength(CPC_CodePageControl.CPIRepeatingGroupLength.SingleByteCodePage);
        config.setCurrentPageControl(cpc);
        RoundTripTestUtils.assertRoundTrip(new CPI_CodePageIndex(), data, config);
    }

    @Test
    public void testFNIRoundTrip() throws Exception {
        // FNI: D38C89
        // RG: depends on FNC.fniRGLL. default 28. SFLen = 8 + 28 = 36 (0x24)
        byte[] data = new byte[9 + 28];
        data[0] = 0x5A; data[1] = 0x00; data[2] = 0x24;
        data[3] = (byte) 0xD3; data[4] = (byte) 0x8C; data[5] = (byte) 0x89;

        com.mgz.afp.parser.AFPParserConfiguration config = new com.mgz.afp.parser.AFPParserConfiguration();
        FNC_FontControl fnc = new FNC_FontControl();
        fnc.setFniRepeatingGroupLength((short) 28);
        config.setCurrentFontControl(fnc);

        RoundTripTestUtils.assertRoundTrip(new FNI_FontIndex(), data, config);
    }

    @Test
    public void testFNMRoundTrip() throws Exception {
        // FNM: D3A289
        // RG default 8. SFLen = 8 + 8 = 16 (0x10)
        byte[] data = new byte[9 + 8];
        data[0] = 0x5A; data[1] = 0x00; data[2] = 0x10;
        data[3] = (byte) 0xD3; data[4] = (byte) 0xA2; data[5] = (byte) 0x89;

        com.mgz.afp.parser.AFPParserConfiguration config = new com.mgz.afp.parser.AFPParserConfiguration();
        FNC_FontControl fnc = new FNC_FontControl();
        fnc.setFnmRepeatinGroupLength((byte) 8);
        config.setCurrentFontControl(fnc);

        RoundTripTestUtils.assertRoundTrip(new FNM_FontPatternsMap(), data, config);
    }

    @Test
    public void testFNORoundTrip() throws Exception {
        // FNO: D3AE89
        // RG default 26. SFLen = 8 + 26 = 34 (0x22)
        byte[] data = new byte[9 + 26];
        data[0] = 0x5A; data[1] = 0x00; data[2] = 0x22;
        data[3] = (byte) 0xD3; data[4] = (byte) 0xAE; data[5] = (byte) 0x89;

        com.mgz.afp.parser.AFPParserConfiguration config = new com.mgz.afp.parser.AFPParserConfiguration();
        FNC_FontControl fnc = new FNC_FontControl();
        fnc.setFnoRepeatingGroupLength((byte) 26);
        config.setCurrentFontControl(fnc);

        RoundTripTestUtils.assertRoundTrip(new FNO_FontOrientation(), data, config);
    }

    @Test
    public void testFNPRoundTrip() throws Exception {
        // FNP: D3AC89
        // RG default 22. SFLen = 8 + 22 = 30 (0x1E)
        byte[] data = new byte[9 + 22];
        data[0] = 0x5A; data[1] = 0x00; data[2] = 0x1E;
        data[3] = (byte) 0xD3; data[4] = (byte) 0xAC; data[5] = (byte) 0x89;

        com.mgz.afp.parser.AFPParserConfiguration config = new com.mgz.afp.parser.AFPParserConfiguration();
        FNC_FontControl fnc = new FNC_FontControl();
        fnc.setFnpRepeatingGroupLength((byte) 22);
        config.setCurrentFontControl(fnc);

        RoundTripTestUtils.assertRoundTrip(new FNP_FontPosition(), data, config);
    }
}
