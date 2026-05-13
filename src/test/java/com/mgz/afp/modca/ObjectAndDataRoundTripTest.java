package com.mgz.afp.modca;

import com.mgz.afp.RoundTripTestUtils;
import org.junit.jupiter.api.Test;

public class ObjectAndDataRoundTripTest {

    @Test
    public void testBOCRoundTrip() throws Exception {
        // BOC: D3A892
        // Name (8): OBJCONT1 (D6 C2 D1 C3 D6 D5 E3 F1)
        // Triplet (6): Comment "TEST" (06 65 E3 C5 E2 E3)
        // Total Len: 1 + 8 + 14 = 23. SFLen = 22 (0x0016)
        byte[] data = new byte[] {
            0x5A, 0x00, 0x16, (byte) 0xD3, (byte) 0xA8, (byte) 0x92, 0x00, 0x00, 0x00,
            (byte) 0xD6, (byte) 0xC2, (byte) 0xD1, (byte) 0xC3, (byte) 0xD6, (byte) 0xD5, (byte) 0xE3, (byte) 0xF1,
            0x06, 0x65, (byte) 0xE3, (byte) 0xC5, (byte) 0xE2, (byte) 0xE3
        };
        RoundTripTestUtils.assertRoundTrip(new BOC_BeginObjectContainer(), data);
    }

    @Test
    public void testEOCRoundTrip() throws Exception {
        // EOC: D3A992
        // Name (8): OBJCONT1
        // Total Len: 1 + 8 + 8 = 17. SFLen = 16 (0x0010)
        byte[] data = new byte[] {
            0x5A, 0x00, 0x10, (byte) 0xD3, (byte) 0xA9, (byte) 0x92, 0x00, 0x00, 0x00,
            (byte) 0xD6, (byte) 0xC2, (byte) 0xD1, (byte) 0xC3, (byte) 0xD6, (byte) 0xD5, (byte) 0xE3, (byte) 0xF1
        };
        RoundTripTestUtils.assertRoundTrip(new EOC_EndObjectContainer(), data);
    }

    @Test
    public void testOCDRoundTrip() throws Exception {
        // OCD: D3EE92
        // Data: 0x01 02 03 04
        // Total Len: 1 + 8 + 4 = 13. SFLen = 12 (0x000C)
        byte[] data = new byte[] {
            0x5A, 0x00, 0x0C, (byte) 0xD3, (byte) 0xEE, (byte) 0x92, 0x00, 0x00, 0x00,
            0x01, 0x02, 0x03, 0x04
        };
        RoundTripTestUtils.assertRoundTrip(new OCD_ObjectContainerData(), data);
    }

    @Test
    public void testBIMRoundTrip() throws Exception {
        // BIM: D3A8FB
        // Name (8): IMAGE001 (C9 D4 C1 C7 C5 F0 F0 F1)
        // Total Len: 1 + 8 + 8 = 17. SFLen = 16 (0x0010)
        byte[] data = new byte[] {
            0x5A, 0x00, 0x10, (byte) 0xD3, (byte) 0xA8, (byte) 0xFB, 0x00, 0x00, 0x00,
            (byte) 0xC9, (byte) 0xD4, (byte) 0xC1, (byte) 0xC7, (byte) 0xC5, (byte) 0xF0, (byte) 0xF0, (byte) 0xF1
        };
        RoundTripTestUtils.assertRoundTrip(new BIM_BeginImageObject(), data);
    }

    @Test
    public void testEIMRoundTrip() throws Exception {
        // EIM: D3A9FB
        // Name (8): IMAGE001
        // Total Len: 1 + 8 + 8 = 17. SFLen = 16 (0x0010)
        byte[] data = new byte[] {
            0x5A, 0x00, 0x10, (byte) 0xD3, (byte) 0xA9, (byte) 0xFB, 0x00, 0x00, 0x00,
            (byte) 0xC9, (byte) 0xD4, (byte) 0xC1, (byte) 0xC7, (byte) 0xC5, (byte) 0xF0, (byte) 0xF0, (byte) 0xF1
        };
        RoundTripTestUtils.assertRoundTrip(new EIM_EndImageObject(), data);
    }

    @Test
    public void testBIIRoundTrip() throws Exception {
        // BII: D3A87B
        // Name (8): IMIMAGE1 (C9 D4 C9 D4 C1 C7 C5 F1)
        // Total Len: 1 + 8 + 8 = 17. SFLen = 16 (0x0010)
        byte[] data = new byte[] {
            0x5A, 0x00, 0x10, (byte) 0xD3, (byte) 0xA8, (byte) 0x7B, 0x00, 0x00, 0x00,
            (byte) 0xC9, (byte) 0xD4, (byte) 0xC9, (byte) 0xD4, (byte) 0xC1, (byte) 0xC7, (byte) 0xC5, (byte) 0xF1
        };
        RoundTripTestUtils.assertRoundTrip(new BII_BeginIMImageObject(), data);
    }

    @Test
    public void testEIIRoundTrip() throws Exception {
        // EII: D3A97B
        // Name (8): IMIMAGE1
        // Total Len: 1 + 8 + 8 = 17. SFLen = 16 (0x0010)
        byte[] data = new byte[] {
            0x5A, 0x00, 0x10, (byte) 0xD3, (byte) 0xA9, (byte) 0x7B, 0x00, 0x00, 0x00,
            (byte) 0xC9, (byte) 0xD4, (byte) 0xC9, (byte) 0xD4, (byte) 0xC1, (byte) 0xC7, (byte) 0xC5, (byte) 0xF1
        };
        RoundTripTestUtils.assertRoundTrip(new EII_EndIMImageObject(), data);
    }

    @Test
    public void testIRDRoundTrip() throws Exception {
        // IRD: D3EE7B
        // Data: 0xFF 0x00 0xAA 0x55
        // Total Len: 1 + 8 + 4 = 13. SFLen = 12 (0x000C)
        byte[] data = new byte[] {
            0x5A, 0x00, 0x0C, (byte) 0xD3, (byte) 0xEE, (byte) 0x7B, 0x00, 0x00, 0x00,
            (byte) 0xFF, 0x00, (byte) 0xAA, 0x55
        };
        RoundTripTestUtils.assertRoundTrip(new IRD_IMImageRasterData(), data);
    }

    @Test
    public void testEOGRoundTrip() throws Exception {
        // EOG: D3A9C7
        // Name (8): OBJENVG1 (D6 C2 D1 C5 D5 E5 C7 F1)
        // Total Len: 1 + 8 + 8 = 17. SFLen = 16 (0x0010)
        byte[] data = new byte[] {
            0x5A, 0x00, 0x10, (byte) 0xD3, (byte) 0xA9, (byte) 0xC7, 0x00, 0x00, 0x00,
            (byte) 0xD6, (byte) 0xC2, (byte) 0xD1, (byte) 0xC5, (byte) 0xD5, (byte) 0xE5, (byte) 0xC7, (byte) 0xF1
        };
        RoundTripTestUtils.assertRoundTrip(new EOG_EndObjectEnvironmentGroup(), data);
    }

    @Test
    public void testIOBRoundTrip() throws Exception {
        // IOB: D3AFC3
        // Name (8): OBJ00001 (D6 C2 D1 F0 F0 F0 F0 F1)
        // Reserved (1): 00
        // ObjectType (1): 0x92 (OtherObjectData)
        // xOrigin (3): 0x000100
        // yOrigin (3): 0x000200
        // xRotation (2): 0x0000 (ori0)
        // yRotation (2): 0x2D00 (ori90)
        // xOriginOfContent (3): 0x000000
        // yOriginOfContent (3): 0x000000
        // RefCoordSystem (1): 0x01
        // Triplets (6): Comment "TEST" (06 65 E3 C5 E2 E3)
        // Total Payload Len: 8 + 1 + 1 + 3 + 3 + 2 + 2 + 3 + 3 + 1 + 6 = 33
        // Total Len: 1 + 8 + 33 = 42. SFLen = 41 (0x0029)
        byte[] data = new byte[] {
            0x5A, 0x00, 0x29, (byte) 0xD3, (byte) 0xAF, (byte) 0xC3, 0x00, 0x00, 0x00,
            (byte) 0xD6, (byte) 0xC2, (byte) 0xD1, (byte) 0xF0, (byte) 0xF0, (byte) 0xF0, (byte) 0xF0, (byte) 0xF1,
            0x00, (byte) 0x92, 0x00, 0x01, 0x00, 0x00, 0x02, 0x00, 0x00, 0x00, 0x2D, 0x00,
            0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x01,
            0x06, 0x65, (byte) 0xE3, (byte) 0xC5, (byte) 0xE2, (byte) 0xE3
        };
        RoundTripTestUtils.assertRoundTrip(new IOB_IncludeObject(), data);
    }
}
