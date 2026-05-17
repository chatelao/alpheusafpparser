package com.mgz.afp.goca;

import com.mgz.afp.RoundTripTestUtils;
import org.junit.jupiter.api.Test;

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
    public void testGADRoundTrip_Gradients() throws Exception {
        // GAD: D3EEBB
        // 1. GLGD (FE DC), Payload len 36 (0x0024). Total order len 40.
        // Payload:
        // 0-1: 00 00 (RES)
        // 2: 01 (PATTSET)
        // 3: 01 (PATTSYM)
        // 4-7: 00 00 00 00 (X_S, Y_S)
        // 8-11: 03 E8 03 E8 (X_E, Y_E = 1000, 1000)
        // 12: 0D (ColorSpec Len)
        // 13: 00 (RES)
        // 14: 01 (RGB)
        // 15-18: 00 00 00 00 (RES)
        // 19-22: 08 08 08 00 (COLSIZE)
        // 23-25: FF 00 00 (Start color: Red)
        // 26-28: 00 00 FF (End color: Blue)
        // 29-30: 01 01 (Outside modes: Pad)
        // 31-32: 13 88 (Offset: 5000)
        // 33-35: 00 FF 00 (Color stop: Green)

        byte[] glgd = new byte[] {
            (byte) 0xFE, (byte) 0xDC, 0x00, 0x24,
            0x00, 0x00, 0x01, 0x01,
            0x00, 0x00, 0x00, 0x00,
            0x03, (byte) 0xE8, 0x03, (byte) 0xE8,
            0x0D, 0x00, 0x01, 0x00, 0x00, 0x00, 0x00, 0x08, 0x08, 0x08, 0x00,
            (byte) 0xFF, 0x00, 0x00,
            0x00, 0x00, (byte) 0xFF,
            0x01, 0x01,
            0x13, (byte) 0x88, 0x00, (byte) 0xFF, 0x00
        };

        // 2. GRGD (FE DD), Payload len 40 (0x0028). Total order len 44.
        // Payload:
        // 0-1: 00 00 (RES)
        // 2: 01 (PATTSET)
        // 3: 01 (PATTSYM)
        // 4-7: 00 00 00 00 (X_S, Y_S)
        // 8-9: 01 00 (MH_S, MFR_S)
        // 10-13: 01 F4 01 F4 (X_E, Y_E = 500, 500)
        // 14-15: 02 00 (MH_E, MFR_E)
        // 16: 0D (ColorSpec Len)
        // ... (13 bytes color spec)
        // ... (3 bytes end color)
        // ... (2 bytes outside)
        // ... (5 bytes color stop)

        byte[] grgd = new byte[] {
            (byte) 0xFE, (byte) 0xDD, 0x00, 0x28,
            0x00, 0x00, 0x01, 0x01,
            0x00, 0x00, 0x00, 0x00, 0x01, 0x00,
            0x01, (byte) 0xF4, 0x01, (byte) 0xF4, 0x02, 0x00,
            0x0D, 0x00, 0x01, 0x00, 0x00, 0x00, 0x00, 0x08, 0x08, 0x08, 0x00,
            (byte) 0xFF, 0x00, 0x00,
            0x00, 0x00, (byte) 0xFF,
            0x01, 0x01,
            0x13, (byte) 0x88, 0x00, (byte) 0xFF, 0x00
        };

        int totalLen = 9 + glgd.length + grgd.length;
        byte[] data = new byte[totalLen];
        data[0] = 0x5A;
        data[1] = (byte) ((totalLen - 1) >> 8);
        data[2] = (byte) ((totalLen - 1) & 0xFF);
        data[3] = (byte) 0xD3;
        data[4] = (byte) 0xEE;
        data[5] = (byte) 0xBB;
        // 6-8: 00 00 00

        System.arraycopy(glgd, 0, data, 9, glgd.length);
        System.arraycopy(grgd, 0, data, 9 + glgd.length, grgd.length);

        RoundTripTestUtils.assertRoundTrip(new GAD_GraphicsData(), data);
    }

    @Test
    public void testGDDRoundTrip() throws Exception {
        // GDD: D3A6BB
        // 1. SetCurrentDefaultInstruction (0x21), Length (0x06)
        //    AttributeType (0x00 = Drawing), Mask (0x8000 = Color bit), Flag (0x8F = Use Specified)
        //    Color (0x0001 = Blue)
        //    Total param: 21 06 00 80 00 8F 00 01 (8 bytes)
        // Total Len: 1 + 8 + 8 = 17. SFI Len = 16 (0x0010)
        byte[] data = new byte[] {
            0x5A, 0x00, 0x10, (byte) 0xD3, (byte) 0xA6, (byte) 0xBB, 0x00, 0x00, 0x00,
            0x21, 0x06, 0x00, (byte) 0x80, 0x00, (byte) 0x8F, 0x00, 0x01
        };

        RoundTripTestUtils.assertRoundTrip(new GDD_GraphicsDataDescriptor(), data);
    }

    @Test
    public void testGADRoundTrip_BeginSegment() throws Exception {
        // GAD: D3EEBB
        // Drawing Orders:
        // 1. GBSEG (0x70), BSI (14 bytes):
        //    Len (0x0C), Name (4): "SEG1", Flag (0x00), Prop (0x00=Chained,NoProlog,New), DataLen (0x0006), Pred/Succ (4): "    "
        //    -> 70 0C E2 C5 C7 F1 00 00 00 06 40 40 40 40
        //    Nested Drawing Order (6 bytes):
        //    GSCP (0x21), Len 4, X=100 (0x0064), Y=200 (0x00C8) -> 21 04 00 64 00 C8
        // Total GAD Payload Len: 14 + 6 = 20. SFLen = introducer (8) + payload (20) = 28 (0x001C)

        byte[] data = new byte[] {
            0x5A, 0x00, 0x1C, (byte) 0xD3, (byte) 0xEE, (byte) 0xBB, 0x00, 0x00, 0x00,
            0x70, 0x0C, (byte) 0xE2, (byte) 0xC5, (byte) 0xC7, (byte) 0xF1, 0x00, 0x00, 0x00, 0x06, 0x40, 0x40, 0x40, 0x40,
            0x21, 0x04, 0x00, 0x64, 0x00, (byte) 0xC8
        };
        RoundTripTestUtils.assertRoundTrip(new GAD_GraphicsData(), data);
    }
}
