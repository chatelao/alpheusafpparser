package com.mgz.afp.modca;

import com.mgz.afp.RoundTripTestUtils;
import org.junit.jupiter.api.Test;

public class DescriptorAndMapRoundTripTest {

    @Test
    public void testPGDRoundTrip() throws Exception {
        // PGD: D3A6AF
        // XUnitBase (1): 0x00 (Inches)
        // YUnitBase (1): 0x00 (Inches)
        // XUnits (2): 0x05A0 (1440)
        // YUnits (2): 0x05A0 (1440)
        // XSize (3): 0x002E70 (11888)
        // YSize (3): 0x0041B0 (16816)
        // Reserved (3): 0x00 00 00
        // Triplet (6): Comment "TEST" (06 65 E3 C5 E2 E3)
        // Total Len: 1 + 8 + 15 + 6 = 30. SFLen = 29 (0x001D)
        byte[] data = new byte[] {
            0x5A, 0x00, 0x1D, (byte) 0xD3, (byte) 0xA6, (byte) 0xAF, 0x00, 0x00, 0x00,
            0x00, 0x00, 0x05, (byte) 0xA0, 0x05, (byte) 0xA0, 0x00, 0x2E, 0x70, 0x00, 0x41, (byte) 0xB0,
            0x00, 0x00, 0x00,
            0x06, 0x65, (byte) 0xE3, (byte) 0xC5, (byte) 0xE2, (byte) 0xE3
        };
        RoundTripTestUtils.assertRoundTrip(new PGD_PageDescriptor(), data);
    }

    @Test
    public void testMDDRoundTrip() throws Exception {
        // MDD: D3A688
        // XUnitBase (1): 0x00
        // YUnitBase (1): 0x00
        // XUnits (2): 0x05A0
        // YUnits (2): 0x05A0
        // XMediumExtent (3): 0x002E70
        // YMediumExtent (3): 0x0041B0
        // Flag (1): 0x00
        // Triplet (6): Comment "TEST"
        // Total Len: 1 + 8 + 13 + 6 = 28. SFLen = 27 (0x001B)
        byte[] data = new byte[] {
            0x5A, 0x00, 0x1B, (byte) 0xD3, (byte) 0xA6, (byte) 0x88, 0x00, 0x00, 0x00,
            0x00, 0x00, 0x05, (byte) 0xA0, 0x05, (byte) 0xA0, 0x00, 0x2E, 0x70, 0x00, 0x41, (byte) 0xB0,
            0x00,
            0x06, 0x65, (byte) 0xE3, (byte) 0xC5, (byte) 0xE2, (byte) 0xE3
        };
        RoundTripTestUtils.assertRoundTrip(new MDD_MediumDescriptor(), data);
    }

    @Test
    public void testOBDRoundTrip() throws Exception {
        // OBD: D3A66B
        // OBD extends StructuredFieldBaseTriplets which just parses triplets from offset 0
        // Triplet (6): Comment "TEST"
        // Total Len: 1 + 8 + 6 = 15. SFLen = 14 (0x000E)
        byte[] data = new byte[] {
            0x5A, 0x00, 0x0E, (byte) 0xD3, (byte) 0xA6, 0x6B, 0x00, 0x00, 0x00,
            0x06, 0x65, (byte) 0xE3, (byte) 0xC5, (byte) 0xE2, (byte) 0xE3
        };
        RoundTripTestUtils.assertRoundTrip(new OBD_ObjectAreaDescriptor(), data);
    }

    @Test
    public void testCDDRoundTrip() throws Exception {
        // CDD: D3A692
        // RetiredParameters (12): All 0
        // Triplet (6): Comment "TEST"
        // Total Len: 1 + 8 + 12 + 6 = 27. SFLen = 26 (0x001A)
        byte[] data = new byte[] {
            0x5A, 0x00, 0x1A, (byte) 0xD3, (byte) 0xA6, (byte) 0x92, 0x00, 0x00, 0x00,
            0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00,
            0x06, 0x65, (byte) 0xE3, (byte) 0xC5, (byte) 0xE2, (byte) 0xE3
        };
        RoundTripTestUtils.assertRoundTrip(new CDD_ContainerDataDescriptor(), data);
    }

    @Test
    public void testFGDRoundTrip() throws Exception {
        // FGD: D3A6C5
        // Constant Data (4): 0x00 01 00 FF
        // Total Len: 1 + 8 + 4 = 13. SFLen = 12 (0x000C)
        byte[] data = new byte[] {
            0x5A, 0x00, 0x0C, (byte) 0xD3, (byte) 0xA6, (byte) 0xC5, 0x00, 0x00, 0x00,
            0x00, 0x01, 0x00, (byte) 0xFF
        };
        RoundTripTestUtils.assertRoundTrip(new FGD_FormEnvironmentGroupDescriptor(), data);
    }

    @Test
    public void testMDRRoundTrip() throws Exception {
        // MDR: D3ABC3
        // Repeating Group: Len(2) | Triplet(6)
        // Total Len: 1 + 8 + 8 = 17. SFLen = 16 (0x0010)
        byte[] data = new byte[] {
            0x5A, 0x00, 0x10, (byte) 0xD3, (byte) 0xAB, (byte) 0xC3, 0x00, 0x00, 0x00,
            0x00, 0x08, 0x06, 0x65, (byte) 0xE3, (byte) 0xC5, (byte) 0xE2, (byte) 0xE3
        };
        RoundTripTestUtils.assertRoundTrip(new MDR_MapDataResource(), data);
    }
}
