package com.mgz.xml;

import com.mgz.afp.ptoca.PTX_PresentationTextData;
import com.mgz.afp.ptoca.controlSequence.PTOCAControlSequence;
import com.mgz.afp.modca.BPG_BeginPage;
import com.mgz.afp.parser.AFPParserConfiguration;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.nio.charset.Charset;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class PTXAttributesTest {

    @Test
    public void testPTXAttributes() throws Exception {
        AFPParserConfiguration config = new AFPParserConfiguration();
        Charset charset = config.getAfpCharSet();

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try (AfpJacksonXmlWriter writer = new AfpJacksonXmlWriter(baos)) {
            // First Page
            BPG_BeginPage bpg1 = new BPG_BeginPage();
            bpg1.setName("P1");
            writer.handle(bpg1);

            PTX_PresentationTextData ptx1 = new PTX_PresentationTextData();

            PTOCAControlSequence.AMI_AbsoluteMoveInline ami = new PTOCAControlSequence.AMI_AbsoluteMoveInline();
            ami.setDisplacement((short) 100);
            ptx1.addControlSequence(ami);

            PTOCAControlSequence.AMB_AbsoluteMoveBaseline amb = new PTOCAControlSequence.AMB_AbsoluteMoveBaseline();
            amb.setDisplacement((short) 200);
            ptx1.addControlSequence(amb);

            PTOCAControlSequence.TRN_TransparentData trn = new PTOCAControlSequence.TRN_TransparentData();
            trn.setTransparentData("Hello Page 1");
            trn.setTransparentDataEBCDIC("Hello Page 1".getBytes(charset));
            ptx1.addControlSequence(trn);

            writer.handle(ptx1);

            // Second Page
            BPG_BeginPage bpg2 = new BPG_BeginPage();
            bpg2.setName("P2");
            writer.handle(bpg2);

            PTX_PresentationTextData ptx2 = new PTX_PresentationTextData();

            PTOCAControlSequence.RMI_RelativeMoveInline rmi = new PTOCAControlSequence.RMI_RelativeMoveInline();
            rmi.setIncrement((short) 50);
            ptx2.addControlSequence(rmi);

            PTOCAControlSequence.TRN_TransparentData trn2 = new PTOCAControlSequence.TRN_TransparentData();
            trn2.setTransparentData("Hello Page 2");
            trn2.setTransparentDataEBCDIC("Hello Page 2".getBytes(charset));
            ptx2.addControlSequence(trn2);

            writer.handle(ptx2);
        }

        String xml = baos.toString();
        // Check PTX1 and TRN1
        assertTrue(xml.contains("<PTX_PresentationTextData page=\"1\" x=\"0\" y=\"0\""), "PTX1 should have page 1 and coords 0,0");
        assertTrue(xml.contains("<TRN_TransparentData page=\"1\" x=\"100\" y=\"200\""), "TRN1 should have page 1 and coords 100,200");

        // Check PTX2 and TRN2
        // After BPG2, state is reset. PTX2 is at 0,0. RMI adds 50. TRN2 is at 50,0.
        assertTrue(xml.contains("<PTX_PresentationTextData page=\"2\" x=\"0\" y=\"0\""), "PTX2 should have page 2 and coords 0,0");
        assertTrue(xml.contains("<TRN_TransparentData page=\"2\" x=\"50\" y=\"0\""), "TRN2 should have page 2 and coords 50,0");
    }

    @Test
    public void testRuleAndMoveAttributes() throws Exception {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try (AfpJacksonXmlWriter writer = new AfpJacksonXmlWriter(baos)) {
            BPG_BeginPage bpg = new BPG_BeginPage();
            writer.handle(bpg);

            PTX_PresentationTextData ptx = new PTX_PresentationTextData();

            // Move to 100, 100
            PTOCAControlSequence.AMI_AbsoluteMoveInline ami = new PTOCAControlSequence.AMI_AbsoluteMoveInline();
            ami.setDisplacement((short) 100);
            ptx.addControlSequence(ami);
            PTOCAControlSequence.AMB_AbsoluteMoveBaseline amb = new PTOCAControlSequence.AMB_AbsoluteMoveBaseline();
            amb.setDisplacement((short) 100);
            ptx.addControlSequence(amb);

            // Draw I-axis rule (at 100, 100)
            PTOCAControlSequence.DIR_DrawIaxisRule dir = new PTOCAControlSequence.DIR_DrawIaxisRule();
            dir.setLength((short) 500);
            dir.setWidth((short) 10);
            ptx.addControlSequence(dir);

            // BLN (reset inline to margin=0, increment baseline by say 20)
            PTOCAControlSequence.SBI_SetBaselineIncrement sbi = new PTOCAControlSequence.SBI_SetBaselineIncrement();
            sbi.setIncrement((short) 20);
            ptx.addControlSequence(sbi);
            ptx.addControlSequence(new PTOCAControlSequence.BLN_BeginLine());

            // TRN at (0, 120)
            PTOCAControlSequence.TRN_TransparentData trn = new PTOCAControlSequence.TRN_TransparentData();
            trn.setTransparentData("New Line");
            ptx.addControlSequence(trn);

            writer.handle(ptx);
        }

        String xml = baos.toString();
        assertTrue(xml.contains("<DIR_DrawIaxisRule page=\"1\" x=\"100\" y=\"100\" length=\"500\" width=\"10\"/>"), "DIR should have correct attributes and valid XML structure");
        assertTrue(xml.contains("<TRN_TransparentData page=\"1\" x=\"0\" y=\"120\""), "TRN after BLN should be at x=0, y=120");
    }
}
