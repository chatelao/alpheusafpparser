package com.mgz.afp.lineData;

import com.mgz.afp.RoundTripTestUtils;
import org.junit.Test;

public class LineDataRoundTripTest {

    @Test
    public void testBDXRoundTrip() throws Exception {
        // BDX: D3A8E3
        // Name (8): DMX1     (C4 D4 E7 F1 40 40 40 40)
        // Total Len: 1 + 8 + 8 = 17. SFLen = 16 (0x0010)
        byte[] data = new byte[] {
            0x5A, 0x00, 0x10, (byte) 0xD3, (byte) 0xA8, (byte) 0xE3, 0x00, 0x00, 0x00,
            (byte) 0xC4, (byte) 0xD4, (byte) 0xE7, (byte) 0xF1, 0x40, 0x40, 0x40, 0x40
        };
        RoundTripTestUtils.assertRoundTrip(new BDX_BeginDataMapTransmitionSubcase(), data);
    }

    @Test
    public void testEDXRoundTrip() throws Exception {
        // EDX: D3A9E3
        // Name (8): DMX1     (C4 D4 E7 F1 40 40 40 40)
        // Total Len: 1 + 8 + 8 = 17. SFLen = 16 (0x0010)
        byte[] data = new byte[] {
            0x5A, 0x00, 0x10, (byte) 0xD3, (byte) 0xA9, (byte) 0xE3, 0x00, 0x00, 0x00,
            (byte) 0xC4, (byte) 0xD4, (byte) 0xE7, (byte) 0xF1, 0x40, 0x40, 0x40, 0x40
        };
        RoundTripTestUtils.assertRoundTrip(new EDX_EndDataMapTransmitionSubcase(), data);
    }

    @Test
    public void testBPMRoundTrip() throws Exception {
        // BPM: D3A8CB
        // Name (8): PAGEDEF1 (D7 C1 C7 C5 C4 C5 C6 F1)
        // Total Len: 1 + 8 + 8 = 17. SFLen = 16 (0x0010)
        byte[] data = new byte[] {
            0x5A, 0x00, 0x10, (byte) 0xD3, (byte) 0xA8, (byte) 0xCB, 0x00, 0x00, 0x00,
            (byte) 0xD7, (byte) 0xC1, (byte) 0xC7, (byte) 0xC5, (byte) 0xC4, (byte) 0xC5, (byte) 0xC6, (byte) 0xF1
        };
        RoundTripTestUtils.assertRoundTrip(new BPM_BeginPageMap(), data);
    }

    @Test
    public void testEPMRoundTrip() throws Exception {
        // EPM: D3A9CB
        // Name (8): PAGEDEF1
        // Total Len: 1 + 8 + 8 = 17. SFLen = 16 (0x0010)
        byte[] data = new byte[] {
            0x5A, 0x00, 0x10, (byte) 0xD3, (byte) 0xA9, (byte) 0xCB, 0x00, 0x00, 0x00,
            (byte) 0xD7, (byte) 0xC1, (byte) 0xC7, (byte) 0xC5, (byte) 0xC4, (byte) 0xC5, (byte) 0xC6, (byte) 0xF1
        };
        RoundTripTestUtils.assertRoundTrip(new EPM_EndPageMap(), data);
    }

    @Test
    public void testDXDRoundTrip() throws Exception {
        // DXD: D3A6E3
        // Data: 0x00 01 00 FF
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
        // Name (8): DATAMAP1 (C4 C1 E3 C1 D4 C1 D7 F1)
        // Total Len: 1 + 8 + 8 = 17. SFLen = 16 (0x0010)
        byte[] data = new byte[] {
            0x5A, 0x00, 0x10, (byte) 0xD3, (byte) 0xA9, (byte) 0xCA, 0x00, 0x00, 0x00,
            (byte) 0xC4, (byte) 0xC1, (byte) 0xE3, (byte) 0xC1, (byte) 0xD4, (byte) 0xC1, (byte) 0xD7, (byte) 0xF1
        };
        RoundTripTestUtils.assertRoundTrip(new EDM_EndDataMap(), data);
    }

    @Test
    public void testIDMRoundTrip() throws Exception {
        // IDM: D3ABCA
        // Name (8): DATAMAP1
        // Total Len: 1 + 8 + 8 = 17. SFLen = 16 (0x0010)
        byte[] data = new byte[] {
            0x5A, 0x00, 0x10, (byte) 0xD3, (byte) 0xAB, (byte) 0xCA, 0x00, 0x00, 0x00,
            (byte) 0xC4, (byte) 0xC1, (byte) 0xE3, (byte) 0xC1, (byte) 0xD4, (byte) 0xC1, (byte) 0xD7, (byte) 0xF1
        };
        RoundTripTestUtils.assertRoundTrip(new IDM_InvokeDataMap(), data);
    }
}
