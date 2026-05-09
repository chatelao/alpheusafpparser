package com.mgz.afp.ioca;

import com.mgz.afp.RoundTripTestUtils;
import org.junit.Test;

public class IOCARoundTripTest {

    @Test
    public void testIDDRoundTrip_Basic() throws Exception {
        // IDD: D3A6FB
        // UnitBase: 0x00 (10 inches)
        // xUnits: 2400 (0x0960)
        // yUnits: 2400 (0x0960)
        // xSize: 1000 (0x03E8)
        // ySize: 2000 (0x07D0)
        // Total Payload Len: 9
        // Total Len: 1 + 8 + 9 = 18. SFLen = 17 (0x0011)
        byte[] data = new byte[] {
            0x5A, 0x00, 0x11, (byte) 0xD3, (byte) 0xA6, (byte) 0xFB, 0x00, 0x00, 0x00,
            0x00, 0x09, 0x60, 0x09, 0x60, 0x03, (byte) 0xE8, 0x07, (byte) 0xD0
        };
        RoundTripTestUtils.assertRoundTrip(new IDD_ImageDataDescriptor(), data);
    }

    @Test
    public void testIPDRoundTrip_Segments() throws Exception {
        // IPD: D3EEFB
        // Segments:
        // 1. ImageSize (0x94), Len 9, UnitBase=0, xUnits=2400, yUnits=2400, xSize=1000, ySize=2000
        //    -> 94 09 00 09 60 09 60 03 E8 07 D0
        // 2. ImageEncoding (0x95), Len 2, Comp=0x03 (NoComp), Rec=0x01 (RIDIC)
        //    -> 95 02 03 01
        // 3. ImageData (0xFE92), Len 4, Data=01 02 03 04
        //    -> FE 92 00 04 01 02 03 04
        // Total Segments Len: 11 + 4 + 8 = 23
        // Total Len: 1 + 8 + 23 = 32. SFLen = 31 (0x001F)
        byte[] data = new byte[] {
            0x5A, 0x00, 0x1F, (byte) 0xD3, (byte) 0xEE, (byte) 0xFB, 0x00, 0x00, 0x00,
            (byte) 0x94, 0x09, 0x00, 0x09, 0x60, 0x09, 0x60, 0x03, (byte) 0xE8, 0x07, (byte) 0xD0,
            (byte) 0x95, 0x02, 0x03, 0x01,
            (byte) 0xFE, (byte) 0x92, 0x00, 0x04, 0x01, 0x02, 0x03, 0x04
        };
        RoundTripTestUtils.assertRoundTrip(new IPD_ImagePictureData(), data);
    }
}
