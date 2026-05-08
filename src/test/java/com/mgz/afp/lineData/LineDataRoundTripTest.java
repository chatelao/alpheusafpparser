package com.mgz.afp.lineData;

import com.mgz.afp.RoundTripTestUtils;
import org.junit.Test;

public class LineDataRoundTripTest {

    @Test
    public void testBDMRoundTrip() throws Exception {
        // BDM: D3A8CA
        // Name (8): DATAMAP1 (C4 C1 E3 C1 D4 C1 D7 F1)
        // DatFmt: 0x00
        // Triplet (6): Comment "TEST" (06 65 E3 C5 E2 E3)
        // Total Len: 1 + 8 + 9 + 6 = 24. SFLen = 23 (0x0017)
        byte[] data = new byte[] {
            0x5A, 0x00, 0x17, (byte) 0xD3, (byte) 0xA8, (byte) 0xCA, 0x00, 0x00, 0x00,
            (byte) 0xC4, (byte) 0xC1, (byte) 0xE3, (byte) 0xC1, (byte) 0xD4, (byte) 0xC1, (byte) 0xD7, (byte) 0xF1,
            0x00,
            0x06, 0x65, (byte) 0xE3, (byte) 0xC5, (byte) 0xE2, (byte) 0xE3
        };
        RoundTripTestUtils.assertRoundTrip(new BDM_BeginDataMap(), data);
    }

    @Test
    public void testBDXRoundTrip() throws Exception {
        // BDX: D3A8E3
        // Name (8): SUBCASE1 (E2 E4 C2 C3 C1 E2 C5 F1)
        // Total Len: 1 + 8 + 8 = 17. SFLen = 16 (0x0010)
        byte[] data = new byte[] {
            0x5A, 0x00, 0x10, (byte) 0xD3, (byte) 0xA8, (byte) 0xE3, 0x00, 0x00, 0x00,
            (byte) 0xE2, (byte) 0xE4, (byte) 0xC2, (byte) 0xC3, (byte) 0xC1, (byte) 0xE2, (byte) 0xC5, (byte) 0xF1
        };
        RoundTripTestUtils.assertRoundTrip(new BDX_BeginDataMapTransmitionSubcase(), data);
    }

    @Test
    public void testBPMRoundTrip() throws Exception {
        // BPM: D3A8CB
        // Name (8): PAGEMAP1 (D7 C1 C7 C5 D4 C1 D7 F1)
        // Total Len: 1 + 8 + 8 = 17. SFLen = 16 (0x0010)
        byte[] data = new byte[] {
            0x5A, 0x00, 0x10, (byte) 0xD3, (byte) 0xA8, (byte) 0xCB, 0x00, 0x00, 0x00,
            (byte) 0xD7, (byte) 0xC1, (byte) 0xC7, (byte) 0xC5, (byte) 0xD4, (byte) 0xC1, (byte) 0xD7, (byte) 0xF1
        };
        RoundTripTestUtils.assertRoundTrip(new BPM_BeginPageMap(), data);
    }

    @Test
    public void testCCPRoundTrip() throws Exception {
        // CCP: D3A7CA
        // CCPid (2): 0x0001
        // NxtCCPid (2): 0x0000
        // CCPFlgs (1): 0x80 (BeforeSubpageActions)
        // Res (1): 0x00
        // NumRGs (2): 0x0001
        // RGLgth (2): 0x0015 (21)
        // CSLgth (2): 0x0001
        // RG (21 bytes):
        //   Timing: 0x01
        //   MMAction: 0x00
        //   MMName (8): "        " (40 x 8)
        //   DMAction: 0x00
        //   DMName (8): "        " (40 x 8)
        //   Comparison: 0x01 (EqualTo)
        //   String: 0x41 ("A")
        // Total Payload: 12 + 21 = 33 bytes.
        // Total Len: 1 + 8 + 33 = 42. SFLen = 41 (0x0029)
        byte[] data = new byte[] {
            0x5A, 0x00, 0x29, (byte) 0xD3, (byte) 0xA7, (byte) 0xCA, 0x00, 0x00, 0x00,
            0x00, 0x01, 0x00, 0x00, (byte) 0x80, 0x00, 0x00, 0x01, 0x00, 0x15, 0x00, 0x01,
            0x01, 0x00, 0x40, 0x40, 0x40, 0x40, 0x40, 0x40, 0x40, 0x40, 0x00, 0x40, 0x40, 0x40, 0x40, 0x40, 0x40, 0x40, 0x40, 0x01, 0x41
        };
        RoundTripTestUtils.assertRoundTrip(new CCP_ConditionalProcessingControl(), data);
    }

    @Test
    public void testDXDRoundTrip() throws Exception {
        // DXD: D3A6E3
        // Constant Data: 0x00 01 00 FF
        // Total Len: 1 + 8 + 4 = 13. SFLen = 12 (0x000C)
        byte[] data = new byte[] {
            0x5A, 0x00, 0x0C, (byte) 0xD3, (byte) 0xA6, (byte) 0xE3, 0x00, 0x00, 0x00,
            0x00, 0x01, 0x00, (byte) 0xFF
        };
        RoundTripTestUtils.assertRoundTrip(new DXD_DataMapTransmitionSubcaseDescriptor(), data);
    }

    @Test
    public void testEDMRoundTrip() throws Exception {
        // EDM: D3A9CA
        // Name (8): DATAMAP1
        byte[] data = new byte[] {
            0x5A, 0x00, 0x10, (byte) 0xD3, (byte) 0xA9, (byte) 0xCA, 0x00, 0x00, 0x00,
            (byte) 0xC4, (byte) 0xC1, (byte) 0xE3, (byte) 0xC1, (byte) 0xD4, (byte) 0xC1, (byte) 0xD7, (byte) 0xF1
        };
        RoundTripTestUtils.assertRoundTrip(new EDM_EndDataMap(), data);
    }

    @Test
    public void testEDXRoundTrip() throws Exception {
        // EDX: D3A9E3
        // Name (8): SUBCASE1
        byte[] data = new byte[] {
            0x5A, 0x00, 0x10, (byte) 0xD3, (byte) 0xA9, (byte) 0xE3, 0x00, 0x00, 0x00,
            (byte) 0xE2, (byte) 0xE4, (byte) 0xC2, (byte) 0xC3, (byte) 0xC1, (byte) 0xE2, (byte) 0xC5, (byte) 0xF1
        };
        RoundTripTestUtils.assertRoundTrip(new EDX_EndDataMapTransmitionSubcase(), data);
    }

    @Test
    public void testEPMRoundTrip() throws Exception {
        // EPM: D3A9CB
        // Name (8): PAGEMAP1
        byte[] data = new byte[] {
            0x5A, 0x00, 0x10, (byte) 0xD3, (byte) 0xA9, (byte) 0xCB, 0x00, 0x00, 0x00,
            (byte) 0xD7, (byte) 0xC1, (byte) 0xC7, (byte) 0xC5, (byte) 0xD4, (byte) 0xC1, (byte) 0xD7, (byte) 0xF1
        };
        RoundTripTestUtils.assertRoundTrip(new EPM_EndPageMap(), data);
    }

    @Test
    public void testFDSRoundTrip() throws Exception {
        // FDS: D3AAEC
        // TxtLgth (2): 0x0004
        byte[] data = new byte[] {
            0x5A, 0x00, 0x0A, (byte) 0xD3, (byte) 0xAA, (byte) 0xEC, 0x00, 0x00, 0x00,
            0x00, 0x04
        };
        RoundTripTestUtils.assertRoundTrip(new FDS_FixedDataSize(), data);
    }

    @Test
    public void testFDXRoundTrip() throws Exception {
        // FDX: D3EEEC
        // Text: "TEST" (E3 C5 E2 E3 in CP500)
        // Total Len: 1 + 8 + 4 = 13. SFLen = 12 (0x000C)
        byte[] data = new byte[] {
            0x5A, 0x00, 0x0C, (byte) 0xD3, (byte) 0xEE, (byte) 0xEC, 0x00, 0x00, 0x00,
            (byte) 0xE3, (byte) 0xC5, (byte) 0xE2, (byte) 0xE3
        };
        RoundTripTestUtils.assertRoundTrip(new FDX_FixedDataText(), data);
    }

    @Test
    public void testIDMRoundTrip() throws Exception {
        // IDM: D3ABCA
        // Name (8): DATAMAP1
        byte[] data = new byte[] {
            0x5A, 0x00, 0x10, (byte) 0xD3, (byte) 0xAB, (byte) 0xCA, 0x00, 0x00, 0x00,
            (byte) 0xC4, (byte) 0xC1, (byte) 0xE3, (byte) 0xC1, (byte) 0xD4, (byte) 0xC1, (byte) 0xD7, (byte) 0xF1
        };
        RoundTripTestUtils.assertRoundTrip(new IDM_InvokeDataMap(), data);
    }

    @Test
    public void testLNCRoundTrip() throws Exception {
        // LNC: D3AAE7
        // NumDSC (2): 0x0005
        byte[] data = new byte[] {
            0x5A, 0x00, 0x0A, (byte) 0xD3, (byte) 0xAA, (byte) 0xE7, 0x00, 0x00, 0x00,
            0x00, 0x05
        };
        RoundTripTestUtils.assertRoundTrip(new LNC_LineDescriptorCount(), data);
    }

    @Test
    public void testLNDRoundTrip() throws Exception {
        // LND: D3A6E7
        // Flags (2): 0x2014 (GenerateInlinePosition_NewPosition, ConditionalProcessing_DoPerformCP, RelativeBaselinePosition_RelativePosition)
        // IPos (2): 0x0000
        // BPos (2): 0x0000
        // Iorient (2): 0x0000 (0 deg)
        // Borient (2): 0x2D00 (90 deg)
        // FntLID (1): 0x01
        // ChnlCde (1): 0x00
        // NLNDskp (2): 0x0001
        // NLNDsp (2): 0x0001
        // NLNDreu (2): 0x0001
        // SupName (8): "        "
        // SOLid (1): 0x00
        // DataStrt (4): 0x00000000
        // DataLgth (2): 0x0000
        // TxtColor (2): 0x0008 (Black)
        // NLNDccp (2): 0x0000
        // SubpgID (1): 0x00
        // CCPID (2): 0x0000
        // Total Payload: 40 bytes
        // Total Len: 1 + 8 + 40 = 49. SFLen = 48 (0x0030)
        byte[] data = new byte[] {
            0x5A, 0x00, 0x30, (byte) 0xD3, (byte) 0xA6, (byte) 0xE7, 0x00, 0x00, 0x00,
            0x20, 0x14, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x2D, 0x00, 0x01, 0x00, 0x00, 0x01, 0x00, 0x01, 0x00, 0x01,
            0x40, 0x40, 0x40, 0x40, 0x40, 0x40, 0x40, 0x40, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x08, 0x00, 0x00, 0x00, 0x00, 0x00
        };
        RoundTripTestUtils.assertRoundTrip(new LND_LineDescriptor(), data);
    }

    @Test
    public void testRCDRoundTrip() throws Exception {
        // RCD: D3A68D
        // RecID (10): "RECORD1   " (D9 C5 C3 D6 D9 C4 F1 40 40 40)
        // RecType (1): 0x00 (Body)
        // Flags (3): 0x201400 (GenerateInlinePosition_NewPosition, ConditionalProcessing_DoPerformCP, RelativeBaselinePosition_RelativePosition)
        // Res (1): 0x00
        // IPos (2): 0x0000
        // BPos (2): 0x0000
        // Iorient (2): 0x0000
        // Borient (2): 0x2D00
        // FntLID (1): 0x01
        // FLDrcd (2): 0x0000
        // SupName (8): "        "
        // SOLid (1): 0x00
        // DataStrt (4): 0x00000000
        // DataLgth (2): 0x0000
        // CONDrcd (2): 0x0000
        // SubpgID (1): 0x00
        // CCPID (2): 0x0000
        // Pgno (2): 0x0000
        // ESpac (2): 0x0000
        // Align (1): 0x00
        // FldDelim (2): 0x0000
        // Fldno (2): 0x0000
        // AdBIncr (2): 0x0000
        // Res (13): 00 x 13
        // Total Payload: 70 bytes
        // Total Len: 1 + 8 + 70 = 79. SFLen = 78 (0x004E)
        byte[] data = new byte[] {
            0x5A, 0x00, 0x4E, (byte) 0xD3, (byte) 0xA6, (byte) 0x8D, 0x00, 0x00, 0x00,
            (byte) 0xD9, (byte) 0xC5, (byte) 0xC3, (byte) 0xD6, (byte) 0xD9, (byte) 0xC4, (byte) 0xF1, 0x40, 0x40, 0x40,
            0x00, 0x20, 0x14, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x2D, 0x00, 0x01, 0x00, 0x00,
            0x40, 0x40, 0x40, 0x40, 0x40, 0x40, 0x40, 0x40, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00,
            0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00
        };
        RoundTripTestUtils.assertRoundTrip(new RCD_RecordDescriptor(), data);
    }

    @Test
    public void testXMDRoundTrip() throws Exception {
        // XMD: D3A68E
        // ElmType (1): 0x00
        // Flags (3): 0x201400
        // Res (1): 0x00
        // IPos (2): 0x0000
        // BPos (2): 0x0000
        // Iorient (2): 0x0000
        // Borient (2): 0x2D00
        // FntLID (1): 0x01
        // FLDxmd (2): 0x0000
        // Res (2): 0x0000
        // SupName (8): "        "
        // Res (1): 0x00
        // DataStrt (4): 0x00000000
        // DataLgth (2): 0x0000
        // CONDxmd (2): 0x0000
        // SubpgID (1): 0x00
        // CCPID (2): 0x0000
        // Pgno (2): 0x0000
        // ESpac (2): 0x0000
        // Align (1): 0x00
        // FldDelim (2): 0x0000
        // Fldno (2): 0x0000
        // AdBIncr (2): 0x0000
        // Res (13): 00 x 13
        // Total Payload: 62 bytes
        // Total Len: 1 + 8 + 62 = 71. SFLen = 70 (0x0046)
        byte[] data = new byte[] {
            0x5A, 0x00, 0x46, (byte) 0xD3, (byte) 0xA6, (byte) 0x8E, 0x00, 0x00, 0x00,
            0x00, 0x20, 0x14, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x2D, 0x00, 0x01, 0x00, 0x00, 0x00, 0x00,
            0x40, 0x40, 0x40, 0x40, 0x40, 0x40, 0x40, 0x40, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00,
            0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00
        };
        RoundTripTestUtils.assertRoundTrip(new XMD_XMLDescriptor(), data);
    }
}
