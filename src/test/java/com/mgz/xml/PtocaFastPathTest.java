package com.mgz.xml;

import com.mgz.afp.ptoca.PTX_PresentationTextData;
import com.mgz.afp.ptoca.controlSequence.PTOCAControlSequence;
import com.mgz.afp.enums.AFPColorValue;
import org.junit.jupiter.api.Test;
import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class PtocaFastPathTest {

    @Test
    public void testPtocaFastPaths() throws Exception {
        PTX_PresentationTextData ptx = new PTX_PresentationTextData();
        List<PTOCAControlSequence> sequences = new ArrayList<>();

        // RMI
        PTOCAControlSequence.RMI_RelativeMoveInline rmi = new PTOCAControlSequence.RMI_RelativeMoveInline();
        rmi.setIncrement((short) 100);
        sequences.add(rmi);

        // RMB
        PTOCAControlSequence.RMB_RelativeMoveBaseline rmb = new PTOCAControlSequence.RMB_RelativeMoveBaseline();
        rmb.setIncrement((short) 50);
        sequences.add(rmb);

        // SIM
        PTOCAControlSequence.SIM_SetInlineMargin sim = new PTOCAControlSequence.SIM_SetInlineMargin();
        sim.setDisplacement((short) 200);
        sequences.add(sim);

        // SBI
        PTOCAControlSequence.SBI_SetBaselineIncrement sbi = new PTOCAControlSequence.SBI_SetBaselineIncrement();
        sbi.setIncrement((short) 12);
        sequences.add(sbi);

        // BLN
        sequences.add(new PTOCAControlSequence.BLN_BeginLine());

        // BSU
        PTOCAControlSequence.BSU_BeginSuppression bsu = new PTOCAControlSequence.BSU_BeginSuppression();
        bsu.setSuppressionID((short) 1);
        sequences.add(bsu);

        // ESU
        PTOCAControlSequence.ESU_EndSuppression esu = new PTOCAControlSequence.ESU_EndSuppression();
        esu.setSuppressionID((short) 1);
        sequences.add(esu);

        // STC
        PTOCAControlSequence.STC_SetTextColor stc = new PTOCAControlSequence.STC_SetTextColor();
        stc.setForegroundColor(AFPColorValue.Blue_0x01);
        stc.setPrecision(PTOCAControlSequence.STC_SetTextColor.STC_Precision.IfSpecifiedColorNotSupported_SubstitutColorOrDefaul0xFF07);
        sequences.add(stc);

        // USC
        PTOCAControlSequence.USC_Underscore usc = new PTOCAControlSequence.USC_Underscore();
        usc.setBypassFlag(PTOCAControlSequence.PTOCA_BypassFlag.NoBypass);
        sequences.add(usc);

        // TBM
        PTOCAControlSequence.TBM_TemporaryBaselineMove tbm = new PTOCAControlSequence.TBM_TemporaryBaselineMove();
        tbm.setDirection(PTOCAControlSequence.TBM_TemporaryBaselineMove.TBM_Direction.MoveAwayFromIAxis);
        tbm.setPrecision(PTOCAControlSequence.TBM_TemporaryBaselineMove.TBM_Precision.AccuratelyPlaced);
        tbm.setTemporaryBaselineIncrement((short) 20);
        sequences.add(tbm);

        // OVS
        PTOCAControlSequence.OVS_Overstrike ovs = new PTOCAControlSequence.OVS_Overstrike();
        ovs.setBypassFlag(PTOCAControlSequence.PTOCA_BypassFlag.NoBypass);
        ovs.setOverStrikeCharacterCodePoint(0x60);
        sequences.add(ovs);

        ptx.setControlSequences(sequences);

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try (AfpJacksonXmlWriter writer = new AfpJacksonXmlWriter(baos, null, true)) {
            writer.handle(ptx);
        }

        String xml = baos.toString();
        System.out.println("XML Output:\n" + xml);

        assertTrue(xml.contains("<RMI_RelativeMoveInline increment=\"100\"/>"));
        assertTrue(xml.contains("<RMB_RelativeMoveBaseline increment=\"50\"/>"));
        assertTrue(xml.contains("<SIM_SetInlineMargin displacement=\"200\"/>"));
        assertTrue(xml.contains("<SBI_SetBaselineIncrement increment=\"12\"/>"));
        assertTrue(xml.contains("<BLN_BeginLine/>"));
        assertTrue(xml.contains("<BSU_BeginSuppression suppressionID=\"1\"/>"));
        assertTrue(xml.contains("<ESU_EndSuppression suppressionID=\"1\"/>"));
        assertTrue(xml.contains("<STC_SetTextColor>"));
        assertTrue(xml.contains("<foregroundColor>Blue_0x01</foregroundColor>"));
        assertTrue(xml.contains("<precision>IfSpecifiedColorNotSupported_SubstitutColorOrDefaul0xFF07</precision>"));
        assertTrue(xml.contains("<USC_Underscore bypassFlag=\"NoBypass\"/>"));
        assertTrue(xml.contains("<TBM_TemporaryBaselineMove>"));
        assertTrue(xml.contains("<direction>MoveAwayFromIAxis</direction>"));
        assertTrue(xml.contains("<precision>AccuratelyPlaced</precision>"));
        assertTrue(xml.contains("<temporaryBaselineIncrement>20</temporaryBaselineIncrement>"));
        assertTrue(xml.contains("<OVS_Overstrike>"));
        assertTrue(xml.contains("<bypassFlag>NoBypass</bypassFlag>"));
        assertTrue(xml.contains("<overStrikeCharacterCodePoint>96</overStrikeCharacterCodePoint>"));
    }
}
