package com.mgz.xml;

import com.mgz.afp.modca.BPG_BeginPage;
import com.mgz.afp.ptoca.PTX_PresentationTextData;
import com.mgz.afp.ptoca.controlSequence.PTOCAControlSequence;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class PTXAttributesTest {

    @Test
    public void testPTXAttributes() throws Exception {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try (AfpJacksonXmlWriter writer = new AfpJacksonXmlWriter(baos)) {
            // First page
            BPG_BeginPage bpg1 = new BPG_BeginPage();
            bpg1.setName("PAGE1");
            writer.handle(bpg1);

            PTX_PresentationTextData ptx1 = new PTX_PresentationTextData();

            // AMI 1440 (1 inch)
            PTOCAControlSequence.AMI_AbsoluteMoveInline ami = new PTOCAControlSequence.AMI_AbsoluteMoveInline();
            ami.setDisplacement((short)1440);
            ptx1.addControlSequence(ami);

            // TRN
            PTOCAControlSequence.TRN_TransparentData trn = new PTOCAControlSequence.TRN_TransparentData();
            trn.setTransparentData("Hello");
            ptx1.addControlSequence(trn);

            writer.handle(ptx1);

            // Second page
            BPG_BeginPage bpg2 = new BPG_BeginPage();
            bpg2.setName("PAGE2");
            writer.handle(bpg2);

            PTX_PresentationTextData ptx2 = new PTX_PresentationTextData();

            // AMB 2880 (2 inches)
            PTOCAControlSequence.AMB_AbsoluteMoveBaseline amb = new PTOCAControlSequence.AMB_AbsoluteMoveBaseline();
            amb.setDisplacement((short)2880);
            ptx2.addControlSequence(amb);

            // GC
            PTOCAControlSequence.GraphicCharacters gc = new PTOCAControlSequence.GraphicCharacters();
            gc.setData("World".getBytes(StandardCharsets.US_ASCII));
            ptx2.addControlSequence(gc);

            writer.handle(ptx2);
        }

        String xml = baos.toString(StandardCharsets.UTF_8);

        // Page 1 assertions
        assertTrue(xml.contains("<PTX_PresentationTextData page=\"1\" x=\"0\" y=\"0\">"), "PTX1 should be on page 1 at origin");
        assertTrue(xml.contains("<AMI_AbsoluteMoveInline page=\"1\" x=\"0\" y=\"0\" displacement=\"1440\"/>"), "AMI should be on page 1 at origin");
        assertTrue(xml.contains("<TRN_TransparentData page=\"1\" x=\"1440\" y=\"0\">"), "TRN should be on page 1 at x=1440");

        // Page 2 assertions
        assertTrue(xml.contains("<PTX_PresentationTextData page=\"2\" x=\"0\" y=\"0\">"), "PTX2 should be on page 2 at origin");
        assertTrue(xml.contains("<AMB_AbsoluteMoveBaseline page=\"2\" x=\"0\" y=\"0\" displacement=\"2880\"/>"), "AMB should be on page 2 at origin");
        assertTrue(xml.contains("<GraphicCharacters page=\"2\" x=\"0\" y=\"2880\">"), "GC should be on page 2 at y=2880");
    }
}
