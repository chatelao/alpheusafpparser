package com.mgz.afp.ioca;

import com.mgz.afp.RoundTripTestUtils;
import com.mgz.afp.ioca.IPD_Segment.*;
import org.junit.jupiter.api.Test;

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

    @Test
    public void testBeginImageContentRoundTrip() throws Exception {
        // Type (0x91) | Len (0x01) | ObjType (0x01)
        byte[] data = new byte[] { (byte) 0x91, 0x01, 0x01 };
        RoundTripTestUtils.assertRoundTrip(new BeginImageContent(), data);
    }

    @Test
    public void testEndImageContentRoundTrip() throws Exception {
        // Type (0x93) | Len (0x00)
        byte[] data = new byte[] { (byte) 0x93, 0x00 };
        RoundTripTestUtils.assertRoundTrip(new EndImageContent(), data);
    }

    @Test
    public void testIDESizeRoundTrip() throws Exception {
        // Type (0x96) | Len (0x01) | Bits (0x08)
        byte[] data = new byte[] { (byte) 0x96, 0x01, 0x08 };
        RoundTripTestUtils.assertRoundTrip(new IDESize(), data);
    }

    @Test
    public void testIDEStructureRoundTrip() throws Exception {
        // Type (0x9B) | Len (0x08) | Flags (0x00) | ColorSpace (0x01 = RGB) | Res (00 00 00) | CompSizes (08 08 08)
        byte[] data = new byte[] { (byte) 0x9B, 0x08, 0x00, 0x01, 0x00, 0x00, 0x00, 0x08, 0x08, 0x08 };
        RoundTripTestUtils.assertRoundTrip(new IDEStructure(), data);
    }

    @Test
    public void testImageLUTIDRoundTrip() throws Exception {
        // Type (0x97) | Len (0x01) | LUTID (0x05)
        byte[] data = new byte[] { (byte) 0x97, 0x01, 0x05 };
        RoundTripTestUtils.assertRoundTrip(new ImageLUTID(), data);
    }

    @Test
    public void testFunctionSetIdentificationRoundTrip() throws Exception {
        // Type (0xF7) | Len (0x02) | Cat (0x01) | FctSet (0x0A)
        byte[] data = new byte[] { (byte) 0xF7, 0x02, 0x01, 0x0A };
        RoundTripTestUtils.assertRoundTrip(new FunctionSetIdentification(), data);
    }

    @Test
    public void testBeginSegmentRoundTrip() throws Exception {
        // Reference: specifications/markdown/ioca-reference-09/Chapter_4.md [IOCA-4-011]
        // Type (0x70) | Len (0x04) | Name (0x01 02 03 04)
        byte[] data = new byte[] { 0x70, 0x04, 0x01, 0x02, 0x03, 0x04 };
        RoundTripTestUtils.assertRoundTrip(new BeginSegment(), data);
    }

    @Test
    public void testEndSegmentRoundTrip() throws Exception {
        // Reference: specifications/markdown/ioca-reference-09/Chapter_4.md [IOCA-4-012]
        // Type (0x71) | Len (0x00)
        byte[] data = new byte[] { 0x71, 0x00 };
        RoundTripTestUtils.assertRoundTrip(new EndSegment(), data);
    }

    @Test
    public void testImageEncodingRoundTrip() throws Exception {
        // Reference: specifications/markdown/ioca-reference-09/Chapter_5.md [IOCA-5-066]
        // Type (0x95) | Len (0x02) | Comp (0x03) | Rec (0x01)
        byte[] data = new byte[] { (byte) 0x95, 0x02, 0x03, 0x01 };
        RoundTripTestUtils.assertRoundTrip(new ImageEncoding(), data);
    }
}
