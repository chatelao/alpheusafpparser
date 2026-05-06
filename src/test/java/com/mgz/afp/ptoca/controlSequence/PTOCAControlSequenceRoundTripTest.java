package com.mgz.afp.ptoca.controlSequence;

import com.mgz.afp.parser.AFPParserConfiguration;
import com.mgz.afp.ptoca.controlSequence.PTOCAControlSequence.*;
import com.mgz.util.UtilBinaryDecoding;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import static org.junit.Assert.assertArrayEquals;

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
        assertArrayEquals("Payload round-trip failed for " + className, payload, baos.toByteArray());

        byte[] csiBytes = csi.toBytes();
        if (isChained) {
            assertArrayEquals(new byte[]{(byte) csi.getLength(), (byte) type.toByte(isChained)}, csiBytes);
        } else {
            assertArrayEquals(new byte[]{(byte) 0x2B, (byte) 0xD3, (byte) csi.getLength(), (byte) type.toByte(isChained)}, csiBytes);
        }
    }

    @Test
    public void testAMB_AbsoluteMoveBaselineRoundTrip() throws Exception {
        assertCSRoundTrip(new AMB_AbsoluteMoveBaseline(), new byte[]{0x00, 0x64}, false);
    }

    @Test
    public void testAMI_AbsoluteMoveInlineRoundTrip() throws Exception {
        assertCSRoundTrip(new AMI_AbsoluteMoveInline(), new byte[]{0x00, 0x64}, false);
    }

    @Test
    public void testBLN_BeginLineRoundTrip() throws Exception {
        assertCSRoundTrip(new BLN_BeginLine(), new byte[]{}, false);
    }

    @Test
    public void testBSU_BeginSuppressionRoundTrip() throws Exception {
        assertCSRoundTrip(new BSU_BeginSuppression(), new byte[]{0x01}, false);
    }

    @Test
    public void testDBR_DrawBaxisRuleRoundTrip() throws Exception {
        assertCSRoundTrip(new DBR_DrawBaxisRule(), new byte[]{0x00, 0x64}, false);
        assertCSRoundTrip(new DBR_DrawBaxisRule(), new byte[]{0x00, 0x64, 0x00, 0x05, 0x00}, false);
    }

    @Test
    public void testDIR_DrawIaxisRuleRoundTrip() throws Exception {
        assertCSRoundTrip(new DIR_DrawIaxisRule(), new byte[]{0x00, 0x64}, false);
        assertCSRoundTrip(new DIR_DrawIaxisRule(), new byte[]{0x00, 0x64, 0x00, 0x05, 0x00}, false);
    }

    @Test
    public void testESU_EndSuppressionRoundTrip() throws Exception {
        assertCSRoundTrip(new ESU_EndSuppression(), new byte[]{0x01}, false);
    }

    @Test
    public void testNOP_NoOperationRoundTrip() throws Exception {
        assertCSRoundTrip(new NOP_NoOperation(), new byte[]{0x01, 0x02, 0x03}, false);
    }

    @Test
    public void testOVS_OverstrikeRoundTrip() throws Exception {
        assertCSRoundTrip(new OVS_Overstrike(), new byte[]{0x01, 0x00, 0x64}, false);
    }

    @Test
    public void testRMB_RelativeMoveBaselineRoundTrip() throws Exception {
        assertCSRoundTrip(new RMB_RelativeMoveBaseline(), new byte[]{0x00, 0x64}, false);
    }

    @Test
    public void testRMI_RelativeMoveInlineRoundTrip() throws Exception {
        assertCSRoundTrip(new RMI_RelativeMoveInline(), new byte[]{0x00, 0x64}, false);
    }

    @Test
    public void testRPS_RepeatStringRoundTrip() throws Exception {
        assertCSRoundTrip(new RPS_RepeatString(), new byte[]{0x00, 0x05, 0x01, 0x02}, false);
    }

    @Test
    public void testSBI_SetBaselineIncrementRoundTrip() throws Exception {
        assertCSRoundTrip(new SBI_SetBaselineIncrement(), new byte[]{0x00, 0x64}, false);
    }

    @Test
    public void testSCFL_SetCodedFontLocalRoundTrip() throws Exception {
        assertCSRoundTrip(new SCFL_SetCodedFontLocal(), new byte[]{0x01}, false);
    }

    @Test
    public void testSEC_SetExtendedTextColorRoundTrip() throws Exception {
        byte[] payload = new byte[14];
        payload[1] = 0x01; // ColorSpace RGB
        payload[10] = (byte) 0xFF; // R
        assertCSRoundTrip(new SEC_SetExtendedTextColor(), payload, false);
    }

    @Test
    public void testSIA_SetIntercharacterAdjustmentRoundTrip() throws Exception {
        assertCSRoundTrip(new SIA_SetIntercharacterAdjustment(), new byte[]{0x00, 0x05}, false);
        assertCSRoundTrip(new SIA_SetIntercharacterAdjustment(), new byte[]{0x00, 0x05, 0x01}, false);
    }

    @Test
    public void testSIM_SetInlineMarginRoundTrip() throws Exception {
        assertCSRoundTrip(new SIM_SetInlineMargin(), new byte[]{0x00, 0x64}, false);
    }

    @Test
    public void testSTC_SetTextColorRoundTrip() throws Exception {
        assertCSRoundTrip(new STC_SetTextColor(), new byte[]{0x00, 0x01}, false);
        assertCSRoundTrip(new STC_SetTextColor(), new byte[]{0x00, 0x01, 0x00}, false);
    }

    @Test
    public void testSTO_SetTextOrientationRoundTrip() throws Exception {
        assertCSRoundTrip(new STO_SetTextOrientation(), new byte[]{0x00, 0x00, 0x2D, 0x00}, false);
    }

    @Test
    public void testSVI_SetVariableSpaceCharacterIncrementRoundTrip() throws Exception {
        assertCSRoundTrip(new SVI_SetVariableSpaceCharacterIncrement(), new byte[]{0x00, 0x64}, false);
    }

    @Test
    public void testTBM_TemporaryBaselineMoveRoundTrip() throws Exception {
        assertCSRoundTrip(new TBM_TemporaryBaselineMove(), new byte[]{0x01}, false);
        assertCSRoundTrip(new TBM_TemporaryBaselineMove(), new byte[]{0x02, 0x00}, false);
        assertCSRoundTrip(new TBM_TemporaryBaselineMove(), new byte[]{0x03, 0x01, 0x00, 0x64}, false);
    }

    @Test
    public void testTRN_TransparentDataRoundTrip() throws Exception {
        assertCSRoundTrip(new TRN_TransparentData(), new byte[]{0x01, 0x02, 0x03}, false);
    }

    @Test
    public void testUSC_UnderscoreRoundTrip() throws Exception {
        assertCSRoundTrip(new USC_Underscore(), new byte[]{0x01}, false);
    }

    @Test
    public void testChainedControlSequence() throws Exception {
        assertCSRoundTrip(new AMI_AbsoluteMoveInline(), new byte[]{0x00, 0x64}, true);
    }
}
