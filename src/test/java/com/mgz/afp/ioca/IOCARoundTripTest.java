package com.mgz.afp.ioca;

import com.mgz.afp.RoundTripTestUtils;
import com.mgz.afp.enums.AFPColorSpace;
import com.mgz.afp.enums.AFPUnitBase;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.EnumSet;

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
    public void testIPDRoundTrip_ComplexSegments() throws Exception {
        // 1. IDEStructure (0x9B), Len 6, Flags=0, ColorSpace=RGB(1), Reserved=0, Size1=8
        //    -> 9B 06 00 01 00 00 00 08
        // 2. TileSetColor (0xB7), Len 11, ColorSpace=RGB(1), Reserved=0, S1=8, S2=8, S3=8, S4=0, Color=FF0000
        //    -> B7 0B 01 00 00 00 08 08 08 00 FF 00 00
        // 3. NColorNames (0xFEB3), Len 10, Reserved=0, RG1(Res=0, Len=6, Name="RED" in UTF-16BE)
        //    "RED" in UTF-16BE: 00 52 00 45 00 44
        //    -> FE B3 00 0A 00 00 00 06 00 52 00 45 00 44
        // Payload sizes: IDEStructure: 8, TileSetColor: 13, NColorNames: 14. Total = 35.
        // SF Length = 8 + 35 = 43 (0x002B)
        byte[] data = new byte[] {
            0x5A, 0x00, 0x2B, (byte) 0xD3, (byte) 0xEE, (byte) 0xFB, 0x00, 0x00, 0x00,
            (byte) 0x9B, 0x06, 0x00, 0x01, 0x00, 0x00, 0x00, 0x08,
            (byte) 0xB7, 0x0B, 0x01, 0x00, 0x00, 0x00, 0x08, 0x08, 0x08, 0x00, (byte) 0xFF, 0x00, 0x00,
            (byte) 0xFE, (byte) 0xB3, 0x00, 0x0A, 0x00, 0x00, 0x00, 0x06, 0x00, 0x52, 0x00, 0x45, 0x00, 0x44
        };
        RoundTripTestUtils.assertRoundTrip(new IPD_ImagePictureData(), data);
    }

    @Test
    public void testIDDRoundTrip_SDFs() throws Exception {
        // IDD with SetExtendedBilevelImageColor and FunctionSetIdentification
        // UnitBase: 0x00, xUnits: 2400, yUnits: 2400, xSize: 1000, ySize: 2000
        // Header: 9 bytes
        // SDF1: SetExtendedBilevelImageColor (0xF4), Len 13, Res=0, CS=RGB(1), Res=00000000, S1=8, S2=8, S3=8, S4=0, Val=FF0000
        //      -> F4 0D 00 01 00 00 00 00 08 08 08 00 FF 00 00 (15 bytes)
        // SDF2: IOCAFunctionSetIdentification (0xF7), Len 2, Cat=1, FS=FS48(0x30)
        //      -> F7 02 01 30 (4 bytes)
        // Total Payload: 9 + 15 + 4 = 28 bytes.
        // SF Length = Introducer(8) + Payload(28) = 36 (0x0024)
        byte[] data = new byte[] {
            0x5A, 0x00, 0x24, (byte) 0xD3, (byte) 0xA6, (byte) 0xFB, 0x00, 0x00, 0x00,
            0x00, 0x09, 0x60, 0x09, 0x60, 0x03, (byte) 0xE8, 0x07, (byte) 0xD0,
            (byte) 0xF4, 0x0D, 0x00, 0x01, 0x00, 0x00, 0x00, 0x00, 0x08, 0x08, 0x08, 0x00, (byte) 0xFF, 0x00, 0x00,
            (byte) 0xF7, 0x02, 0x01, 0x30
        };
        RoundTripTestUtils.assertRoundTrip(new IDD_ImageDataDescriptor(), data);
    }
}
