package com.mgz.xml;

import com.mgz.afp.ptoca.PTX_PresentationTextData;
import com.mgz.afp.ptoca.controlSequence.PTOCAControlSequence;
import com.mgz.afp.modca.BPG_BeginPage;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class PTXAttributesTest {

    @Test
    public void testPTXCoordinates() throws Exception {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try (AfpJacksonXmlWriter writer = new AfpJacksonXmlWriter(baos, null, true)) {
            PTX_PresentationTextData ptx = new PTX_PresentationTextData();

            // Move to (100, 200)
            PTOCAControlSequence.AMI_AbsoluteMoveInline ami = new PTOCAControlSequence.AMI_AbsoluteMoveInline();
            ami.setDisplacement((short) 100);
            ptx.addControlSequence(ami);

            PTOCAControlSequence.AMB_AbsoluteMoveBaseline amb = new PTOCAControlSequence.AMB_AbsoluteMoveBaseline();
            amb.setDisplacement((short) 200);
            ptx.addControlSequence(amb);

            // Text at (100, 200)
            PTOCAControlSequence.TRN_TransparentData trn = new PTOCAControlSequence.TRN_TransparentData();
            trn.setTransparentData("Hello");
            ptx.addControlSequence(trn);

            writer.handle(ptx);
        }

        String xml = baos.toString(StandardCharsets.UTF_8);
        assertTrue(xml.contains("page=\"0\""), "XML should contain page=\"0\"");
        assertTrue(xml.contains("x=\"0\" y=\"0\""), "Initial PTX should be at (0,0)");
        assertTrue(xml.contains("<AMI_AbsoluteMoveInline page=\"0\" x=\"0\" y=\"0\" displacement=\"100\"/>"), "AMI should be at (0,0) and move to 100");
        assertTrue(xml.contains("<AMB_AbsoluteMoveBaseline page=\"0\" x=\"100\" y=\"0\" displacement=\"200\"/>"), "AMB should be at (100,0) and move to 200");
        assertTrue(xml.contains("<TRN_TransparentData page=\"0\" x=\"100\" y=\"200\">"), "Text should be at (100, 200)");
    }

    @Test
    public void testPageIncrement() throws Exception {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try (AfpJacksonXmlWriter writer = new AfpJacksonXmlWriter(baos, null, true)) {
            BPG_BeginPage bpg1 = new BPG_BeginPage();
            bpg1.setName("PAGE1");
            writer.handle(bpg1);

            PTX_PresentationTextData ptx1 = new PTX_PresentationTextData();
            writer.handle(ptx1);

            BPG_BeginPage bpg2 = new BPG_BeginPage();
            bpg2.setName("PAGE2");
            writer.handle(bpg2);

            PTX_PresentationTextData ptx2 = new PTX_PresentationTextData();
            writer.handle(ptx2);
        }

        String xml = baos.toString(StandardCharsets.UTF_8);
        assertTrue(xml.contains("<PTX_PresentationTextData page=\"1\" x=\"0\" y=\"0\""), "First PTX should be on page 1");
        assertTrue(xml.contains("<PTX_PresentationTextData page=\"2\" x=\"0\" y=\"0\""), "Second PTX should be on page 2");
    }
}
