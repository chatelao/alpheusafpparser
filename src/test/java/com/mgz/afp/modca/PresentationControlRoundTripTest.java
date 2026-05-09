package com.mgz.afp.modca;

import com.mgz.afp.RoundTripTestUtils;
import org.junit.Test;

public class PresentationControlRoundTripTest {

    @Test
    public void testPECRoundTrip() throws Exception {
        // PEC: D3A7A8
        // Triplet (6): Comment "TEST"
        // Total Len: 1 + 8 + 6 = 15. SFLen = 14 (0x000E)
        byte[] data = new byte[] {
            0x5A, 0x00, 0x0E, (byte) 0xD3, (byte) 0xA7, (byte) 0xA8, 0x00, 0x00, 0x00,
            0x06, 0x65, (byte) 0xE3, (byte) 0xC5, (byte) 0xE2, (byte) 0xE3
        };
        RoundTripTestUtils.assertRoundTrip(new PEC_PresentationEnvironmentControl(), data);
    }

    @Test
    public void testPFCRoundTrip() throws Exception {
        // PFC: D3B288
        // Reserved0 (1): 0x00
        // Flag (1): 0x00 (ResetFidelityControlsToDefault)
        // Reserved2_3 (2): 0x00 00
        // Triplet (6): Comment "TEST"
        // Total Len: 1 + 8 + 4 + 6 = 19. SFLen = 18 (0x0012)
        byte[] data = new byte[] {
            0x5A, 0x00, 0x12, (byte) 0xD3, (byte) 0xB2, (byte) 0x88, 0x00, 0x00, 0x00,
            0x00, 0x00, 0x00, 0x00,
            0x06, 0x65, (byte) 0xE3, (byte) 0xC5, (byte) 0xE2, (byte) 0xE3
        };
        RoundTripTestUtils.assertRoundTrip(new PFC_PresentationFidelityControl(), data);
    }
}
