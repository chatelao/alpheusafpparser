package com.mgz.afp.ptoca.controlSequence;

import com.mgz.afp.parser.AFPParserConfiguration;
import com.mgz.afp.parser.PTOCAControlSequenceParser;
import com.mgz.afp.ptoca.controlSequence.PTOCAControlSequence.*;
import com.mgz.util.UtilBinaryDecoding;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class PTOCAControlSequenceRoundTripTest {

    private void assertCSRoundTrip(PTOCAControlSequence cs, byte[] payload, boolean isChained) throws Exception {
        ControlSequenceIntroducer csi = new ControlSequenceIntroducer();
        String className = cs.getClass().getSimpleName();
        ControlSequenceFunctionType type = ControlSequenceFunctionType.valueOf(className);
        csi.setControlSequenceFunctionType(type);
        csi.setChained(isChained);
        csi.setLength((short) (payload.length + 2));
        if (!isChained) {
            csi.setCsPrefix((short) 0x2B);
            csi.setCsClass((short) 0xD3);
        } else {
            csi.setCsPrefix((short) -1);
            csi.setCsClass((short) -1);
        }
        cs.setCsi(csi);

        AFPParserConfiguration config = new AFPParserConfiguration();
        cs.decodeAFP(payload, 0, payload.length, config);

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        cs.writeAFP(baos, config);
        assertArrayEquals(payload, baos.toByteArray(), "Payload round-trip failed for " + className);

        byte[] csiBytes = csi.toBytes();
        if (isChained) {
            assertArrayEquals(new byte[]{(byte) csi.getLength(), (byte) type.toByte(isChained)}, csiBytes);
        } else {
            assertArrayEquals(new byte[]{(byte) 0x2B, (byte) 0xD3, (byte) csi.getLength(), (byte) type.toByte(isChained)}, csiBytes);
        }
    }

    @Test
        // [PTOCA-3-133] [PTOCA-3-148] [PTOCA-3-178] [PTOCA-3-225] [PTOCA-4-123]
    public void testAMB_AbsoluteMoveBaselineRoundTrip() throws Exception {
        assertCSRoundTrip(new AMB_AbsoluteMoveBaseline(), new byte[]{0x00, 0x64}, false);
    }

    @Test
    public void testAMI_AbsoluteMoveInlineRoundTrip() throws Exception {
        // [PTOCA-3-134] [PTOCA-3-149] [PTOCA-3-174] [PTOCA-3-226] [PTOCA-4-133]
        // [PTOCA-5-001] [PTOCA-5-002] [PTOCA-5-003]
        assertCSRoundTrip(new AMI_AbsoluteMoveInline(), new byte[]{0x00, 0x64}, false);
    }

    @Test
        // [PTOCA-3-135] [PTOCA-3-180] [PTOCA-3-227] [PTOCA-4-143]
    public void testBLN_BeginLineRoundTrip() throws Exception {
        assertCSRoundTrip(new BLN_BeginLine(), new byte[]{}, false);
    }

    @Test
        // [PTOCA-3-136] [PTOCA-3-202] [PTOCA-3-228] [PTOCA-4-149]
    public void testBSU_BeginSuppressionRoundTrip() throws Exception {
        assertCSRoundTrip(new BSU_BeginSuppression(), new byte[]{0x01}, false);
    }

    @Test
        // [PTOCA-3-138] [PTOCA-3-195] [PTOCA-3-229] [PTOCA-4-166]
    public void testDBR_DrawBaxisRuleRoundTrip() throws Exception {
        assertCSRoundTrip(new DBR_DrawBaxisRule(), new byte[]{0x00, 0x64}, false);
        assertCSRoundTrip(new DBR_DrawBaxisRule(), new byte[]{0x00, 0x64, 0x00, 0x05, 0x00}, false);
    }

    @Test
        // [PTOCA-3-139] [PTOCA-3-194] [PTOCA-3-230] [PTOCA-4-183]
    public void testDIR_DrawIaxisRuleRoundTrip() throws Exception {
        assertCSRoundTrip(new DIR_DrawIaxisRule(), new byte[]{0x00, 0x64}, false);
        assertCSRoundTrip(new DIR_DrawIaxisRule(), new byte[]{0x00, 0x64, 0x00, 0x05, 0x00}, false);
    }

    @Test
        // [PTOCA-3-141] [PTOCA-3-203] [PTOCA-3-232] [PTOCA-4-221]
    public void testESU_EndSuppressionRoundTrip() throws Exception {
        assertCSRoundTrip(new ESU_EndSuppression(), new byte[]{0x01}, false);
    }

    @Test
        // [PTOCA-3-192] [PTOCA-3-238] [PTOCA-4-276]
    public void testNOP_NoOperationRoundTrip() throws Exception {
        assertCSRoundTrip(new NOP_NoOperation(), new byte[]{0x01, 0x02, 0x03}, false);
    }

    @Test
        // [PTOCA-3-147] [PTOCA-3-205] [PTOCA-3-257] [PTOCA-4-281]
    public void testOVS_OverstrikeRoundTrip() throws Exception {
        assertCSRoundTrip(new OVS_Overstrike(), new byte[]{0x01, 0x00, 0x64}, false);
        OVS_Overstrike ovs = new OVS_Overstrike();
        ovs.decodeAFP(new byte[]{0x01, 0x00, (byte) 0xC1}, 0, 3, new AFPParserConfiguration());
        assertEquals("A", ovs.getText());
    }

    @Test
        // [PTOCA-3-148] [PTOCA-3-179] [PTOCA-3-239] [PTOCA-4-324]
    public void testRMB_RelativeMoveBaselineRoundTrip() throws Exception {
        assertCSRoundTrip(new RMB_RelativeMoveBaseline(), new byte[]{0x00, 0x64}, false);
    }

    @Test
        // [PTOCA-3-149] [PTOCA-3-175] [PTOCA-3-240] [PTOCA-4-333]
    public void testRMI_RelativeMoveInlineRoundTrip() throws Exception {
        assertCSRoundTrip(new RMI_RelativeMoveInline(), new byte[]{0x00, 0x64}, false);
    }

    @Test
        // [PTOCA-3-150] [PTOCA-3-191] [PTOCA-3-241] [PTOCA-4-343]
    public void testRPS_RepeatStringRoundTrip() throws Exception {
        assertCSRoundTrip(new RPS_RepeatString(), new byte[]{0x00, 0x05, 0x01, 0x02}, false);
    }

    @Test
        // [PTOCA-3-151] [PTOCA-3-177] [PTOCA-3-245] [PTOCA-4-356]
    public void testSBI_SetBaselineIncrementRoundTrip() throws Exception {
        assertCSRoundTrip(new SBI_SetBaselineIncrement(), new byte[]{0x00, 0x64}, false);
    }

    @Test
        // [PTOCA-3-152] [PTOCA-3-201] [PTOCA-3-246] [PTOCA-4-365]
    public void testSCFL_SetCodedFontLocalRoundTrip() throws Exception {
        assertCSRoundTrip(new SCFL_SetCodedFontLocal(), new byte[]{0x01}, false);
    }

    @Test
        // [PTOCA-3-154] [PTOCA-3-198] [PTOCA-3-248] [PTOCA-4-393]
    public void testSEC_SetExtendedTextColorRoundTrip() throws Exception {
        byte[] payload = new byte[14];
        payload[1] = 0x01; // ColorSpace RGB
        payload[10] = (byte) 0xFF; // R
        assertCSRoundTrip(new SEC_SetExtendedTextColor(), payload, false);
    }

    @Test
        // [PTOCA-3-156] [PTOCA-3-172] [PTOCA-3-250] [PTOCA-4-430]
    public void testSIA_SetIntercharacterAdjustmentRoundTrip() throws Exception {
        assertCSRoundTrip(new SIA_SetIntercharacterAdjustment(), new byte[]{0x00, 0x05}, false);
        assertCSRoundTrip(new SIA_SetIntercharacterAdjustment(), new byte[]{0x00, 0x05, 0x01}, false);
    }

    @Test
        // [PTOCA-3-155] [PTOCA-3-171] [PTOCA-3-251] [PTOCA-4-457]
    public void testSIM_SetInlineMarginRoundTrip() throws Exception {
        assertCSRoundTrip(new SIM_SetInlineMargin(), new byte[]{0x00, 0x64}, false);
    }

    @Test
        // [PTOCA-3-158] [PTOCA-3-197] [PTOCA-3-253] [PTOCA-4-480]
    public void testSTC_SetTextColorRoundTrip() throws Exception {
        assertCSRoundTrip(new STC_SetTextColor(), new byte[]{0x00, 0x01}, false);
        assertCSRoundTrip(new STC_SetTextColor(), new byte[]{0x00, 0x01, 0x00}, false);
    }

    @Test
    public void testSTO_SetTextOrientationRoundTrip() throws Exception {
        // [PTOCA-3-159] [PTOCA-3-181] [PTOCA-3-254] [PTOCA-4-502]
        // [PTOCA-5-041]
        assertCSRoundTrip(new STO_SetTextOrientation(), new byte[]{0x00, 0x00, 0x2D, 0x00}, false);
    }

    @Test
        // [PTOCA-3-161] [PTOCA-3-173] [PTOCA-3-255] [PTOCA-4-534]
    public void testSVI_SetVariableSpaceCharacterIncrementRoundTrip() throws Exception {
        assertCSRoundTrip(new SVI_SetVariableSpaceCharacterIncrement(), new byte[]{0x00, 0x64}, false);
    }

    @Test
        // [PTOCA-3-164] [PTOCA-3-207] [PTOCA-3-259] [PTOCA-4-562]
    public void testTBM_TemporaryBaselineMoveRoundTrip() throws Exception {
        assertCSRoundTrip(new TBM_TemporaryBaselineMove(), new byte[]{0x01}, false);
        assertCSRoundTrip(new TBM_TemporaryBaselineMove(), new byte[]{0x02, 0x00}, false);
        assertCSRoundTrip(new TBM_TemporaryBaselineMove(), new byte[]{0x03, 0x01, 0x00, 0x64}, false);
    }

    @Test
        // [PTOCA-3-166] [PTOCA-3-190] [PTOCA-3-242] [PTOCA-4-589]
    public void testTRN_TransparentDataRoundTrip() throws Exception {
        assertCSRoundTrip(new TRN_TransparentData(), new byte[]{0x01, 0x02, 0x03}, false);
    }

    @Test
        // [PTOCA-3-167] [PTOCA-3-206] [PTOCA-3-258] [PTOCA-4-599]
    public void testUSC_UnderscoreRoundTrip() throws Exception {
        assertCSRoundTrip(new USC_Underscore(), new byte[]{0x01}, false);
    }

    @Test
        // [PTOCA-3-168] [PTOCA-3-183] [PTOCA-3-243] [PTOCA-4-632]
    public void testUCT_UnicodeComplexTextRoundTrip() throws Exception {
        UCT_UnicodeComplexText uct = new UCT_UnicodeComplexText();
        byte[] params = new byte[14];
        params[0] = 0x01; // UCTVERS
        params[2] = 0x00;
        params[3] = 0x04; // CTLNGTH = 4
        params[4] = 0x00; // CTFLGS
        params[6] = 0x02; // BIDICT
        params[7] = 0x01; // GLYPHCT
        params[12] = 0x05;
        params[13] = (byte) 0xA0; // ALTIPOS = 1440

        byte[] text = new byte[] {0x00, 0x41, 0x00, 0x42}; // "AB" in UTF-16BE

        ControlSequenceIntroducer csi = new ControlSequenceIntroducer();
        csi.setControlSequenceFunctionType(ControlSequenceFunctionType.UCT_UnicodeComplexText);
        csi.setLength((short) 16);
        csi.setCsPrefix((short) 0x2B);
        csi.setCsClass((short) 0xD3);
        uct.setCsi(csi);

        uct.decodeAFP(params, 0, params.length, new AFPParserConfiguration());
        uct.setComplexText(text);

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        uct.writeAFP(baos, new AFPParserConfiguration());

        byte[] expected = new byte[14 + 4];
        System.arraycopy(params, 0, expected, 0, 14);
        System.arraycopy(text, 0, expected, 14, 4);

        assertArrayEquals(expected, baos.toByteArray());
        assertEquals("AB", uct.getText());
    }

    @Test
    public void testUCTThroughParser() throws Exception {
        byte[] data = new byte[] {
            0x2B, (byte)0xD3, 0x10, 0x6A, // CSI: length 16, type UCT
            0x01, 0x00, 0x00, 0x04, // UCTVERS, Reserved, CTLNGTH=4
            0x00, 0x00, 0x02, 0x01, // CTFLGS, Reserved, BIDICT, GLYPHCT
            0x00, 0x00, 0x00, 0x00, // Reserved
            0x05, (byte)0xA0,        // ALTIPOS
            0x00, 0x41, 0x00, 0x42  // "AB"
        };

        List<PTOCAControlSequence> list = PTOCAControlSequenceParser.parseControlSequences(data, 0, data.length, new AFPParserConfiguration());
        assertEquals(1, list.size());
        assertTrue(list.get(0) instanceof UCT_UnicodeComplexText);
        UCT_UnicodeComplexText uct = (UCT_UnicodeComplexText) list.get(0);
        assertEquals(4, uct.getCtLength());
        assertArrayEquals(new byte[]{0x00, 0x41, 0x00, 0x42}, uct.getComplexText());
        assertEquals("AB", uct.getText());

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        baos.write(uct.getCsi().toBytes());
        uct.writeAFP(baos, new AFPParserConfiguration());
        assertArrayEquals(data, baos.toByteArray());
    }

    @Test
    public void testChainedControlSequence() throws Exception {
        assertCSRoundTrip(new AMI_AbsoluteMoveInline(), new byte[]{0x00, 0x64}, true);
    }

    @Test
        // [PTOCA-3-140] [PTOCA-3-188] [PTOCA-3-231] [PTOCA-4-202]
    public void testENC_EncryptedDataRoundTrip() throws Exception {
        assertCSRoundTrip(new ENC_EncryptedData(), new byte[]{0x00, 0x00, 0x00, 0x00, 0x01, 0x02, 0x03}, false);
    }

    @Test
        // [PTOCA-3-157] [PTOCA-3-200] [PTOCA-3-252] [PTOCA-4-465]
    public void testSKI_SetKeyInformationRoundTrip() throws Exception {
        assertCSRoundTrip(new SKI_SetKeyInformation(), new byte[]{0x00, 0x00, 0x00, 0x00, (byte) 0xAA, (byte) 0xBB, (byte) 0xCC}, false);
    }

    @Test
        // [PTOCA-3-153] [PTOCA-3-189] [PTOCA-3-247] [PTOCA-4-383]
    public void testSEA_SetEncryptedAlternateRoundTrip() throws Exception {
        assertCSRoundTrip(new SEA_SetEncryptedAlternate(), new byte[]{0x00, 0x00, 0x00, 0x00, (byte) 0xC1, (byte) 0xC2, (byte) 0xC3}, false);
    }

    @Test
        // [PTOCA-3-011] [PTOCA-3-061] [PTOCA-3-077] [PTOCA-4-004]
    public void testGraphicCharactersRoundTrip() throws Exception {
        GraphicCharacters gc = new GraphicCharacters();
        byte[] payload = new byte[]{(byte) 0xC1, (byte) 0xC2, (byte) 0xC3}; // "ABC" in CP500
        AFPParserConfiguration config = new AFPParserConfiguration();
        gc.decodeAFP(payload, 0, payload.length, config);

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        gc.writeAFP(baos, config);
        assertArrayEquals(payload, baos.toByteArray());
        assertEquals("ABC", gc.getText());
    }

    @Test
        // [PTOCA-3-144] [PTOCA-3-184] [PTOCA-3-236] [PTOCA-4-246]
    public void testGLC_GlyphLayoutControlRoundTrip() throws Exception {
        GLC_GlyphLayoutControl glc = new GLC_GlyphLayoutControl();
        byte[] payload = new byte[8 + 13 + 4]; // 8 reserved/metadata + 13 OID + 4 name
        payload[0] = 0x05; payload[1] = (byte) 0xA0; // iAdvance = 1440
        payload[2] = 13; // oidLgth
        payload[3] = 4; // ffnLgth
        byte[] oid = new byte[]{0x06, 0x0B, 0x2B, 0x06, 0x01, 0x04, 0x01, (byte)0x82, 0x37, 0x11, 0x02, 0x03, 0x02};
        System.arraycopy(oid, 0, payload, 8, 13);
        byte[] name = "AB".getBytes(java.nio.charset.StandardCharsets.UTF_16BE);
        System.arraycopy(name, 0, payload, 8 + 13, 4);

        assertCSRoundTrip(glc, payload, false);
        assertEquals("AB", glc.getText());
    }

    @Test
        // [PTOCA-3-143] [PTOCA-3-185] [PTOCA-3-235] [PTOCA-4-237]
    public void testGIR_GlyphIdRunRoundTrip() throws Exception {
        assertCSRoundTrip(new GIR_GlyphIdRun(), new byte[]{0x00, 0x00, 0x00, 0x01, 0x00, 0x02}, false);
    }

    @Test
        // [PTOCA-3-142] [PTOCA-3-186] [PTOCA-3-234] [PTOCA-4-229]
    public void testGAR_GlyphAdvanceRunRoundTrip() throws Exception {
        assertCSRoundTrip(new GAR_GlyphAdvanceRun(), new byte[]{0x00, 0x00, 0x00, 0x64, 0x00, (byte) 0xC8}, false);
    }

    @Test
        // [PTOCA-3-145] [PTOCA-3-187] [PTOCA-3-237] [PTOCA-4-268]
    public void testGOR_GlyphOffsetRunRoundTrip() throws Exception {
        assertCSRoundTrip(new GOR_GlyphOffsetRun(), new byte[]{0x00, 0x00, 0x00, 0x0A, (byte) 0xFF, (byte) 0xF6}, false);
    }

    @Test
    public void testInterleavedGraphicCharactersThroughParser() throws Exception {
        // "ABC" followed by AMI(100)
        // "ABC" = C1 C2 C3
        // AMI = 2B D3 04 C6 00 64
        byte[] data = new byte[]{
            (byte) 0xC1, (byte) 0xC2, (byte) 0xC3,
            0x2B, (byte) 0xD3, 0x04, (byte) 0xC6, 0x00, 0x64
        };

        List<PTOCAControlSequence> list = PTOCAControlSequenceParser.parseControlSequences(data, 0, data.length, new AFPParserConfiguration());
        assertEquals(2, list.size());
        assertTrue(list.get(0) instanceof GraphicCharacters);
        assertEquals("ABC", ((GraphicCharacters) list.get(0)).getText());
        assertTrue(list.get(1) instanceof AMI_AbsoluteMoveInline);
        assertEquals(100, ((AMI_AbsoluteMoveInline) list.get(1)).getDisplacement());

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        for (PTOCAControlSequence cs : list) {
            if (cs.getCsi() != null) {
                baos.write(cs.getCsi().toBytes());
            }
            cs.writeAFP(baos, new AFPParserConfiguration());
        }
        assertArrayEquals(data, baos.toByteArray());
    }
}
