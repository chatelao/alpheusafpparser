package com.mgz.afp.goca;

import com.mgz.afp.RoundTripTestUtils;
import org.junit.Test;

public class GOCARoundTripTest {

    @Test
    public void testBGRRoundTrip() throws Exception {
        // BGR: D3A8BB
        // Name (8): GRAPH001 (C7 D9 C1 D7 C8 F0 F0 F1)
        // Triplet (6): Comment "TEST" (06 65 E3 C5 E2 E3)
        // Total Len: 1 + 8 + 14 = 23. SFLen = 22 (0x0016)
        byte[] data = new byte[] {
            0x5A, 0x00, 0x16, (byte) 0xD3, (byte) 0xA8, (byte) 0xBB, 0x00, 0x00, 0x00,
            (byte) 0xC7, (byte) 0xD9, (byte) 0xC1, (byte) 0xD7, (byte) 0xC8, (byte) 0xF0, (byte) 0xF0, (byte) 0xF1,
            0x06, 0x65, (byte) 0xE3, (byte) 0xC5, (byte) 0xE2, (byte) 0xE3
        };
        RoundTripTestUtils.assertRoundTrip(new BGR_BeginGraphicsObject(), data);
    }

    @Test
    public void testEGRRoundTrip() throws Exception {
        // EGR: D3A9BB
        // Name (8): GRAPH001
        // Total Len: 1 + 8 + 8 = 17. SFLen = 16 (0x0010)
        byte[] data = new byte[] {
            0x5A, 0x00, 0x10, (byte) 0xD3, (byte) 0xA9, (byte) 0xBB, 0x00, 0x00, 0x00,
            (byte) 0xC7, (byte) 0xD9, (byte) 0xC1, (byte) 0xD7, (byte) 0xC8, (byte) 0xF0, (byte) 0xF0, (byte) 0xF1
        };
        RoundTripTestUtils.assertRoundTrip(new EGR_EndGraphicsObject(), data);
    }

    @Test
    public void testGADRoundTrip_Empty() throws Exception {
        // GAD: D3EEBB
        // Total Len: 1 + 8 = 9. SFLen = 8 (0x0008)
        byte[] data = new byte[] {
            0x5A, 0x00, 0x08, (byte) 0xD3, (byte) 0xEE, (byte) 0xBB, 0x00, 0x00, 0x00
        };
        RoundTripTestUtils.assertRoundTrip(new GAD_GraphicsData(), data);
    }

    @Test
    public void testGADRoundTrip_DrawingOrders() throws Exception {
        // GAD: D3EEBB
        // Drawing Orders:
        // 1. GSCP (0x21), Len 4, X=100 (0x0064), Y=200 (0x00C8) -> 21 04 00 64 00 C8
        // 2. GSCOL (0x0A), Color=0x01 (Blue) -> 0A 01
        // 3. GLINE (0xC1), Len 4, X=300 (0x012C), Y=400 (0x0190) -> C1 04 01 2C 01 90
        // Total Drawing Orders Len: 6 + 2 + 6 = 14
        // Total Len: 1 + 8 + 14 = 23. SFLen = 22 (0x0016)
        byte[] data = new byte[] {
            0x5A, 0x00, 0x16, (byte) 0xD3, (byte) 0xEE, (byte) 0xBB, 0x00, 0x00, 0x00,
            0x21, 0x04, 0x00, 0x64, 0x00, (byte) 0xC8,
            0x0A, 0x01,
            (byte) 0xC1, 0x04, 0x01, 0x2C, 0x01, (byte) 0x90
        };
        RoundTripTestUtils.assertRoundTrip(new GAD_GraphicsData(), data);
    }

    @Test
    public void testGADRoundTrip_MoreDrawingOrders() throws Exception {
        // GAD: D3EEBB
        // Drawing Orders:
        // 1. GNOP1 (0x00) -> 00
        // 2. GCOMT (0x01), Len 4, "TEST" (E3 C5 E2 E3) -> 01 04 E3 C5 E2 E3
        // 3. GSBMX (0x0D), MixMode 0x01 -> 0D 01
        // Total Len: 1 + 8 + 1 + 6 + 2 = 18. SFLen = 17 (0x0011)
        byte[] data = new byte[] {
            0x5A, 0x00, 0x11, (byte) 0xD3, (byte) 0xEE, (byte) 0xBB, 0x00, 0x00, 0x00,
            0x00,
            0x01, 0x04, (byte) 0xE3, (byte) 0xC5, (byte) 0xE2, (byte) 0xE3,
            0x0D, 0x01
        };
        RoundTripTestUtils.assertRoundTrip(new GAD_GraphicsData(), data);
    }

    @Test
    public void testGADRoundTrip_NewOrders() throws Exception {
        // GAD: D3EEBB
        // Drawing Orders:
        // 1. GSCLT (0x20), Len 4, Dash=1.5, Move=2.0 -> 20 04 01 80 02 00
        // 2. GBCP (0xDE), Len 13, Flags=0, PattSet=1, PattSym=1, Window(0,100,0,100)
        //    -> DE 0D 00 00 00 01 01 00 00 00 64 00 00 00 64
        // 3. GDPT (0xDF), Len 4, PattSet=1, PattSym=1 -> DF 04 00 00 01 01
        // 4. GSPIK (0x43), Len 2, Data=01 02 -> 43 02 01 02
        // 5. GENSEG (0x71), Reserved=0 -> 71 00
        // Total Drawing Orders Len: 6 + 15 + 6 + 4 + 2 = 33
        // Total Len: 1 + 8 + 33 = 42. SFLen = 41 (0x0029)
        byte[] data = new byte[] {
            0x5A, 0x00, 0x29, (byte) 0xD3, (byte) 0xEE, (byte) 0xBB, 0x00, 0x00, 0x00,
            0x20, 0x04, 0x01, (byte) 0x80, 0x02, 0x00,
            (byte) 0xDE, 0x0D, 0x00, 0x00, 0x00, 0x01, 0x01, 0x00, 0x00, 0x00, 0x64, 0x00, 0x00, 0x00, 0x64,
            (byte) 0xDF, 0x04, 0x00, 0x00, 0x01, 0x01,
            0x43, 0x02, 0x01, 0x02,
            0x71, 0x00
        };
        RoundTripTestUtils.assertRoundTrip(new GAD_GraphicsData(), data);
    }
}
