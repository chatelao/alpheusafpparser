package com.mgz.afp.foca;

import com.mgz.afp.RoundTripTestUtils;
import com.mgz.afp.parser.AFPParserConfiguration;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;

public class FOCARoundTripTest {

    @Test
    public void testBCFRoundTrip() throws Exception {
        byte[] data = new byte[] {
            0x5A, 0x00, 0x10, (byte) 0xD3, (byte) 0xA8, (byte) 0x8A, 0x00, 0x00, 0x00,
            (byte) 0xC6, (byte) 0xD6, (byte) 0xD5, (byte) 0xE3, (byte) 0xD5, (byte) 0xC1, (byte) 0xD4, (byte) 0xC5
        };
        RoundTripTestUtils.assertRoundTrip(new BCF_BeginCodedFont(), data);
    }

    @Test
    public void testECFRoundTrip() throws Exception {
        byte[] data = new byte[] {
            0x5A, 0x00, 0x10, (byte) 0xD3, (byte) 0xA9, (byte) 0x8A, 0x00, 0x00, 0x00,
            (byte) 0xC6, (byte) 0xD6, (byte) 0xD5, (byte) 0xE3, (byte) 0xD5, (byte) 0xC1, (byte) 0xD4, (byte) 0xC5
        };
        RoundTripTestUtils.assertRoundTrip(new ECF_EndCodedFont(), data);
    }

    @Test
    public void testBCPRoundTrip() throws Exception {
        byte[] data = new byte[] {
            0x5A, 0x00, 0x16, (byte) 0xD3, (byte) 0xA8, (byte) 0x87, 0x00, 0x00, 0x00,
            (byte) 0xC3, (byte) 0xD7, (byte) 0xD5, (byte) 0xC1, (byte) 0xD4, (byte) 0xC5, (byte) 0xF1, (byte) 0xF1,
            0x06, 0x65, (byte) 0xE3, (byte) 0xC5, (byte) 0xE2, (byte) 0xE3
        };
        RoundTripTestUtils.assertRoundTrip(new BCP_BeginCodePage(), data);
    }

    @Test
    public void testECPRoundTrip() throws Exception {
        byte[] data = new byte[] {
            0x5A, 0x00, 0x10, (byte) 0xD3, (byte) 0xA9, (byte) 0x87, 0x00, 0x00, 0x00,
            (byte) 0xC3, (byte) 0xD7, (byte) 0xD5, (byte) 0xC1, (byte) 0xD4, (byte) 0xC5, (byte) 0xF1, (byte) 0xF1
        };
        RoundTripTestUtils.assertRoundTrip(new ECP_EndCodePage(), data);
    }

    @Test
    public void testBFNRoundTrip() throws Exception {
        byte[] data = new byte[] {
            0x5A, 0x00, 0x16, (byte) 0xD3, (byte) 0xA8, (byte) 0x89, 0x00, 0x00, 0x00,
            (byte) 0xC3, (byte) 0xE2, (byte) 0xD5, (byte) 0xC1, (byte) 0xD4, (byte) 0xC5, (byte) 0xF1, (byte) 0xF1,
            0x06, 0x65, (byte) 0xE3, (byte) 0xC5, (byte) 0xE2, (byte) 0xE3
        };
        RoundTripTestUtils.assertRoundTrip(new BFN_BeginFont(), data);
    }

    @Test
    public void testEFNRoundTrip() throws Exception {
        byte[] data = new byte[] {
            0x5A, 0x00, 0x10, (byte) 0xD3, (byte) 0xA9, (byte) 0x89, 0x00, 0x00, 0x00,
            (byte) 0xC3, (byte) 0xE2, (byte) 0xD5, (byte) 0xC1, (byte) 0xD4, (byte) 0xC5, (byte) 0xF1, (byte) 0xF1
        };
        RoundTripTestUtils.assertRoundTrip(new EFN_EndFont(), data);
    }

    @Test
    public void testFNNRoundTrip() throws Exception {
        byte[] data = new byte[] {
            0x5A, 0x00, 0x18, (byte) 0xD3, (byte) 0xAB, (byte) 0x89, 0x00, 0x00, 0x00,
            0x02, 0x03,
            (byte) 0xD3, (byte) 0xC1, (byte) 0xF0, (byte) 0xF1, (byte) 0xF0, (byte) 0xF0, (byte) 0xF0, (byte) 0xF0,
            0x00, 0x00, 0x00, 0x5F,
            0x02, 0x61
        };

        AFPParserConfiguration config = new AFPParserConfiguration();
        FNC_FontControl fnc = new FNC_FontControl();
        fnc.setFnnIbmNameGcgidCount(1);
        fnc.setFnnRepeatingGroupLength((byte) 12);
        config.setCurrentFontControl(fnc);

        RoundTripTestUtils.assertRoundTrip(new FNN_FontNameMap(), data, config);
    }

    @Test
    public void testCFCRoundTrip() throws Exception {
        byte[] data = new byte[] {
            0x5A, 0x00, 0x10, (byte) 0xD3, (byte) 0xA7, (byte) 0x8A, 0x00, 0x00, 0x00,
            0x19, 0x01, 0x06, 0x65, (byte) 0xE3, (byte) 0xC5, (byte) 0xE2, (byte) 0xE3
        };
        RoundTripTestUtils.assertRoundTrip(new CFC_CodedFontControl(), data);
    }

    @Test
    public void testCPDRoundTrip() throws Exception {
        byte[] data = new byte[53];
        data[0] = 0x5A;
        data[1] = 0x00;
        data[2] = 0x34;
        data[3] = (byte) 0xD3;
        data[4] = (byte) 0xA6;
        data[5] = (byte) 0x87;
        byte[] desc = "TEST CODE PAGE                  ".getBytes("cp500");
        System.arraycopy(desc, 0, data, 9, 32);
        data[41] = 0x00; data[42] = 0x08;
        data[43] = 0x00; data[44] = 0x00; data[45] = 0x01; data[46] = 0x00;
        data[47] = 0x02; data[48] = (byte) 0xB9;
        data[49] = 0x01; data[50] = (byte) 0xF4;
        data[51] = 0x61; data[52] = 0x00;

        RoundTripTestUtils.assertRoundTrip(new CPD_CodePageDescriptor(), data);
    }

    @Test
    public void testFNCRoundTrip() throws Exception {
        byte[] data = new byte[] {
            0x5A, 0x00, 0x1E, (byte) 0xD3, (byte) 0xA7, (byte) 0x89, 0x00, 0x00, 0x00,
            0x01, 0x05, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x1A, 0x00, 0x00, 0x00, 0x00, 0x00, 0x16, 0x00
        };
        RoundTripTestUtils.assertRoundTrip(new FNC_FontControl(), data);
    }

    @Test
    public void testFNDRoundTrip() throws Exception {
        byte[] data = new byte[91];
        data[0] = 0x5A; data[1] = 0x00; data[2] = 0x5A;
        data[3] = (byte) 0xD3; data[4] = (byte) 0xA6; data[5] = (byte) 0x89;
        // Typeface (9-40) - 32 bytes
        for (int i = 9; i < 41; i++) data[i] = 0x40; // EBCDIC spaces
        byte[] typeface = "HELVETICA".getBytes("cp500");
        System.arraycopy(typeface, 0, data, 9, typeface.length);

        data[41] = 0x04; // fontWeightClass (at payload offset 32)
        data[42] = 0x04; // fontWidthClass (at payload offset 33)

        RoundTripTestUtils.assertRoundTrip(new FND_FontDescriptor(), data);
    }

    @Test
    public void testFNGRoundTrip() throws Exception {
        byte[] data = new byte[] {
            0x5A, 0x00, 0x0B, (byte) 0xD3, (byte) 0xEE, (byte) 0x89, 0x00, 0x00, 0x00,
            0x01, 0x02, 0x03
        };
        RoundTripTestUtils.assertRoundTrip(new FNG_FontPatterns(), data);
    }

    @Test
    public void testCFIRoundTrip() throws Exception {
        byte[] data = new byte[] {
            0x5A, 0x00, 0x21, (byte) 0xD3, (byte) 0x8C, (byte) 0x8A, 0x00, 0x00, 0x00,
            (byte) 0xC6, (byte) 0xD6, (byte) 0xD5, (byte) 0xE3, (byte) 0xD5, (byte) 0xC1, (byte) 0xD4, (byte) 0xC5,
            (byte) 0xC3, (byte) 0xD7, (byte) 0xD5, (byte) 0xC1, (byte) 0xD4, (byte) 0xC5, (byte) 0xF1, (byte) 0xF1,
            0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00
        };
        RoundTripTestUtils.assertRoundTrip(new CFI_CodedFontIndex(), data);
    }

    @Test
    public void testCPCRoundTrip() throws Exception {
        byte[] data = new byte[22];
        data[0] = 0x5A; data[1] = 0x00; data[2] = 0x15;
        data[3] = (byte) 0xD3; data[4] = (byte) 0xA7; data[5] = (byte) 0x87;
        byte[] gcgid = "        ".getBytes("cp500");
        System.arraycopy(gcgid, 0, data, 9, 8);
        data[17] = 0x00;
        data[18] = (byte) 0x0A;
        data[19] = 0x00;
        data[20] = 0x40;
        data[21] = 0x00;

        RoundTripTestUtils.assertRoundTrip(new CPC_CodePageControl(), data);
    }

    @Test
    public void testCPIRoundTrip() throws Exception {
        byte[] data = new byte[] {
            0x5A, 0x00, 0x12, (byte) 0xD3, (byte) 0x8C, (byte) 0x87, 0x00, 0x00, 0x00,
            (byte) 0xD3, (byte) 0xC1, (byte) 0xF0, (byte) 0xF1, (byte) 0xF0, (byte) 0xF0, (byte) 0xF0, (byte) 0xF0,
            0x00, 0x00
        };
        AFPParserConfiguration config = new AFPParserConfiguration();
        CPC_CodePageControl cpc = new CPC_CodePageControl();
        cpc.setCpiRepeatingGroupLength(CPC_CodePageControl.CPIRepeatingGroupLength.SingleByteCodePage);
        config.setCurrentPageControl(cpc);
        RoundTripTestUtils.assertRoundTrip(new CPI_CodePageIndex(), data, config);
    }

    @Test
    public void testFNIRoundTrip() throws Exception {
        byte[] data = new byte[9 + 28];
        data[0] = 0x5A; data[1] = 0x00; data[2] = 0x24;
        data[3] = (byte) 0xD3; data[4] = (byte) 0x8C; data[5] = (byte) 0x89;
        byte[] gcgid = "LA010000".getBytes("cp500");
        System.arraycopy(gcgid, 0, data, 9, 8);

        AFPParserConfiguration config = new AFPParserConfiguration();
        FNC_FontControl fnc = new FNC_FontControl();
        fnc.setFniRepeatingGroupLength((short) 28);
        config.setCurrentFontControl(fnc);

        RoundTripTestUtils.assertRoundTrip(new FNI_FontIndex(), data, config);
    }

    @Test
    public void testFNMRoundTrip() throws Exception {
        byte[] data = new byte[9 + 8];
        data[0] = 0x5A; data[1] = 0x00; data[2] = 0x10;
        data[3] = (byte) 0xD3; data[4] = (byte) 0xA2; data[5] = (byte) 0x89;

        AFPParserConfiguration config = new AFPParserConfiguration();
        FNC_FontControl fnc = new FNC_FontControl();
        fnc.setFnmRepeatingGroupLength((byte) 8);
        config.setCurrentFontControl(fnc);

        RoundTripTestUtils.assertRoundTrip(new FNM_FontPatternsMap(), data, config);
    }

    @Test
    public void testFNORoundTrip() throws Exception {
        byte[] data = new byte[9 + 26];
        data[0] = 0x5A; data[1] = 0x00; data[2] = 0x22;
        data[3] = (byte) 0xD3; data[4] = (byte) 0xAE; data[5] = (byte) 0x89;

        AFPParserConfiguration config = new AFPParserConfiguration();
        FNC_FontControl fnc = new FNC_FontControl();
        fnc.setFnoRepeatingGroupLength((byte) 26);
        config.setCurrentFontControl(fnc);

        RoundTripTestUtils.assertRoundTrip(new FNO_FontOrientation(), data, config);
    }

    @Test
    public void testFNPRoundTrip() throws Exception {
        byte[] data = new byte[9 + 22];
        data[0] = 0x5A; data[1] = 0x00; data[2] = 0x1E;
        data[3] = (byte) 0xD3; data[4] = (byte) 0xAC; data[5] = (byte) 0x89;

        AFPParserConfiguration config = new AFPParserConfiguration();
        FNC_FontControl fnc = new FNC_FontControl();
        fnc.setFnpRepeatingGroupLength((byte) 22);
        config.setCurrentFontControl(fnc);

        RoundTripTestUtils.assertRoundTrip(new FNP_FontPosition(), data, config);
    }

    @Test
    public void testCPIUnicodeScalarValuesRoundTrip() throws Exception {
        byte[] data = new byte[] {
            0x5A, 0x00, 0x17, (byte) 0xD3, (byte) 0x8C, (byte) 0x87, 0x00, 0x00, 0x00,
            (byte) 0xD3, (byte) 0xC1, (byte) 0xF0, (byte) 0xF1, (byte) 0xF0, (byte) 0xF0, (byte) 0xF0, (byte) 0xF0,
            0x00, 0x61, 0x01, 0x00, 0x00, 0x00, 0x61
        };
        AFPParserConfiguration config = new AFPParserConfiguration();
        CPC_CodePageControl cpc = new CPC_CodePageControl();
        cpc.setCpiRepeatingGroupLength(CPC_CodePageControl.CPIRepeatingGroupLength.SingleByteCodePageUnicodeScalarValues);
        config.setCurrentPageControl(cpc);
        RoundTripTestUtils.assertRoundTrip(new CPI_CodePageIndex(), data, config);
    }

    @Test
    public void testCFIMultipleRGSRoundTrip() throws Exception {
        byte[] data = new byte[9 + 50];
        data[0] = 0x5A; data[1] = 0x00; data[2] = 0x3A;
        data[3] = (byte) 0xD3; data[4] = (byte) 0x8C; data[5] = (byte) 0x8A;
        System.arraycopy("FCSNAME1".getBytes("cp500"), 0, data, 9, 8);
        System.arraycopy("CPNAME1 ".getBytes("cp500"), 0, data, 17, 8);
        System.arraycopy("FCSNAME2".getBytes("cp500"), 0, data, 34, 8);
        System.arraycopy("CPNAME2 ".getBytes("cp500"), 0, data, 42, 8);

        RoundTripTestUtils.assertRoundTrip(new CFI_CodedFontIndex(), data);
    }
}
