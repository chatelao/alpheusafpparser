package com.mgz.afp.goca;

import com.mgz.afp.RoundTripTestUtils;
import com.mgz.afp.goca.GAD_DrawingOrder.*;
import org.junit.jupiter.api.Test;

public class GOCADrawingOrderRoundTripTest {

    @Test
    public void testGSCOLRoundTrip() throws Exception {
        // GSCOL (0x0A) | Color (0x01 = Blue)
        byte[] data = new byte[] { 0x0A, 0x01 };
        RoundTripTestUtils.assertRoundTrip(new GSCOL_SetColor(), data);
    }

    @Test
    public void testGSMXRoundTrip() throws Exception {
        // GSMX (0x0C) | Mix (0x01 = Overpaint)
        byte[] data = new byte[] { 0x0C, 0x01 };
        RoundTripTestUtils.assertRoundTrip(new GSMX_SetMix(), data);
    }

    @Test
    public void testGSLWRoundTrip() throws Exception {
        // GSLW (0x19) | Width (0x02)
        byte[] data = new byte[] { 0x19, 0x02 };
        RoundTripTestUtils.assertRoundTrip(new GSLW_SetLineWidth(), data);
    }

    @Test
    public void testGSLERoundTrip() throws Exception {
        // GSLE (0x1A) | End (0x01 = Flat)
        byte[] data = new byte[] { 0x1A, 0x01 };
        RoundTripTestUtils.assertRoundTrip(new GSLE_SetLineEnd(), data);
    }

    @Test
    public void testGSLJRoundTrip() throws Exception {
        // GSLJ (0x1B) | Join (0x01 = Bevel)
        byte[] data = new byte[] { 0x1B, 0x01 };
        RoundTripTestUtils.assertRoundTrip(new GSLJ_SetLineJoin(), data);
    }

    @Test
    public void testGSCPRoundTrip() throws Exception {
        // GSCP (0x21) | Len (0x04) | X (0x0064) | Y (0x00C8)
        byte[] data = new byte[] { 0x21, 0x04, 0x00, 0x64, 0x00, (byte) 0xC8 };
        RoundTripTestUtils.assertRoundTrip(new GSCP_SetCurrentPosition(), data);
    }

    @Test
    public void testGSCARoundTrip() throws Exception {
        // GSCA (0x34) | Len (0x04) | X (0x0001) | Y (0x0000) (0 degrees)
        byte[] data = new byte[] { 0x34, 0x04, 0x00, 0x01, 0x00, 0x00 };
        RoundTripTestUtils.assertRoundTrip(new GSCA_SetCharacterAngle(), data);
    }

    @Test
    public void testGBARRoundTrip() throws Exception {
        // GBAR (0x68) | Flags (0x00)
        byte[] data = new byte[] { 0x68, 0x00 };
        RoundTripTestUtils.assertRoundTrip(new GBAR_BeginArea(), data);
    }

    @Test
    public void testGEARRoundTrip() throws Exception {
        // GEAR (0x60) | Len (0x04) | Data (0x01 02 03 04)
        byte[] data = new byte[] { 0x60, 0x04, 0x01, 0x02, 0x03, 0x04 };
        RoundTripTestUtils.assertRoundTrip(new GEAR_EndArea(), data);
    }

    @Test
    public void testGCBOXRoundTrip() throws Exception {
        // GCBOX (0x80) | Len (0x06) | Res (0x0000) | X (0x0100) | Y (0x0200)
        byte[] data = new byte[] { (byte) 0x80, 0x06, 0x00, 0x00, 0x01, 0x00, 0x02, 0x00 };
        RoundTripTestUtils.assertRoundTrip(new GCBOX_BoxAtCurrentPosition(), data);
    }

    @Test
    public void testGCCHSTRoundTrip() throws Exception {
        // GCCHST (0x83) | Len (0x04) | "TEST" in EBCDIC
        byte[] data = new byte[] { (byte) 0x83, 0x04, (byte) 0xE3, (byte) 0xC5, (byte) 0xE2, (byte) 0xE3 };
        RoundTripTestUtils.assertRoundTrip(new GCCHST_CharacterStringAtCurrentPosition(), data);
    }

    @Test
    public void testGCHSTRoundTrip() throws Exception {
        // GCHST (0xC3) | Len (0x08) | X (0x0064) | Y (0x00C8) | "TEST" in EBCDIC
        byte[] data = new byte[] { (byte) 0xC3, 0x08, 0x00, 0x64, 0x00, (byte) 0xC8, (byte) 0xE3, (byte) 0xC5, (byte) 0xE2, (byte) 0xE3 };
        RoundTripTestUtils.assertRoundTrip(new GCHST_CharacterStringAtGivenPosition(), data);
    }

    @Test
    public void testGEXORoundTrip() throws Exception {
        // GEXO (0xFE) | Qual (0x01) | Len (0x0002) | Data (0xAABB)
        byte[] data = new byte[] { (byte) 0xFE, 0x01, 0x00, 0x02, (byte) 0xAA, (byte) 0xBB };
        RoundTripTestUtils.assertRoundTrip(new GEXO_ExtendedOrder(), data);
    }

    @Test
    public void testGNOP1RoundTrip() throws Exception {
        // Reference: GOCA Chapter 6 - GNOP1 (0x00)
        byte[] data = new byte[] { 0x00 };
        RoundTripTestUtils.assertRoundTrip(new GNOP1_NopOperation(), data);
    }

    @Test
    public void testGCOMTRoundTrip() throws Exception {
        // Reference: GOCA Chapter 6 - GCOMT (0x01)
        byte[] data = new byte[] { 0x01, 0x04, (byte) 0xE3, (byte) 0xC5, (byte) 0xE2, (byte) 0xE3 }; // "TEST"
        RoundTripTestUtils.assertRoundTrip(new GCOMT_Comment(), data);
    }

    @Test
    public void testGBSEGRoundTrip() throws Exception {
        // Reference: GOCA Chapter 6 - GBSEG (0x70)
        // commandCode(1) | length(1) | name(4) | flag(1) | props(1) | dataLen(2) | predName(4)
        byte[] data = new byte[] {
            0x70, 0x0C,
            (byte) 0xE2, (byte) 0xC5, (byte) 0xC7, 0x40, // "SEG "
            0x00, 0x00,
            0x00, 0x00,
            0x40, 0x40, 0x40, 0x40 // predName "    "
        };
        RoundTripTestUtils.assertRoundTrip(new GBSEG_BeginSegment(), data);
    }

    @Test
    public void testGSLTRoundTrip() throws Exception {
        // Reference: specifications/markdown/afp-goca-reference-03/Chapter_7.md [GOCA-7-871]
        // GSLT (0x18) | LineType (0x01 = Dotted)
        byte[] data = new byte[] { 0x18, 0x01 };
        RoundTripTestUtils.assertRoundTrip(new GSLT_SetLineType(), data);
    }

    @Test
    public void testGSBMXRoundTrip() throws Exception {
        // Reference: specifications/markdown/afp-goca-reference-03/Chapter_7.md [GOCA-7-677]
        // GSBMX (0x0D) | Mode (0x05 = Leave Alone)
        byte[] data = new byte[] { 0x0D, 0x05 };
        RoundTripTestUtils.assertRoundTrip(new GSBMX_SetBackgroundMix(), data);
    }

    @Test
    public void testGESEGRoundTrip() throws Exception {
        // Reference: specifications/markdown/afp-goca-reference-03/Chapter_7.md [GOCA-7-118]
        // GESEG (0x71) | Reserved (0x00)
        byte[] data = new byte[] { 0x71, 0x00 };
        RoundTripTestUtils.assertRoundTrip(new GESEG_EndSegment(), data);
    }

    @Test
    public void testGSPSRoundTrip() throws Exception {
        // Reference: specifications/markdown/afp-goca-reference-03/Chapter_7.md [GOCA-7-951]
        // GSPS (0x08) | LCID (0x01)
        byte[] data = new byte[] { 0x08, 0x01 };
        RoundTripTestUtils.assertRoundTrip(new GSPS_SetPatternSet(), data);
    }
}
