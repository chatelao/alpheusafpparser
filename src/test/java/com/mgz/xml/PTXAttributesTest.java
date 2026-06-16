package com.mgz.xml;

import com.mgz.afp.modca.BPG_BeginPage;
import com.mgz.afp.ptoca.PTX_PresentationTextData;
import com.mgz.afp.ptoca.controlSequence.PTOCAControlSequence;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class PTXAttributesTest {

    @Test
    public void testPTXSpatialAttributes() throws Exception {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try (AfpJacksonXmlWriter writer = new AfpJacksonXmlWriter(baos, null, true, true)) {
            // Page 1
            BPG_BeginPage bpg1 = new BPG_BeginPage();
            bpg1.setName("PAGE0001");
            writer.handle(bpg1);

            PTX_PresentationTextData ptx1 = new PTX_PresentationTextData();
            List<PTOCAControlSequence> cs1 = new ArrayList<>();

            PTOCAControlSequence.AMI_AbsoluteMoveInline ami1 = new PTOCAControlSequence.AMI_AbsoluteMoveInline();
            ami1.setDisplacement((short)1000);
            cs1.add(ami1);

            PTOCAControlSequence.AMB_AbsoluteMoveBaseline amb1 = new PTOCAControlSequence.AMB_AbsoluteMoveBaseline();
            amb1.setDisplacement((short)2000);
            cs1.add(amb1);

            PTOCAControlSequence.GraphicCharacters gc1 = new PTOCAControlSequence.GraphicCharacters();
            gc1.setData("Hello".getBytes(StandardCharsets.US_ASCII)); // Simplification
            cs1.add(gc1);

            ptx1.setControlSequences(cs1);
            writer.handle(ptx1);

            // Page 2
            BPG_BeginPage bpg2 = new BPG_BeginPage();
            bpg2.setName("PAGE0002");
            writer.handle(bpg2);

            PTX_PresentationTextData ptx2 = new PTX_PresentationTextData();
            List<PTOCAControlSequence> cs2 = new ArrayList<>();

            PTOCAControlSequence.AMI_AbsoluteMoveInline ami2 = new PTOCAControlSequence.AMI_AbsoluteMoveInline();
            ami2.setDisplacement((short)500);
            cs2.add(ami2);

            PTOCAControlSequence.AMB_AbsoluteMoveBaseline amb2 = new PTOCAControlSequence.AMB_AbsoluteMoveBaseline();
            amb2.setDisplacement((short)1500);
            cs2.add(amb2);

            PTOCAControlSequence.TRN_TransparentData trn2 = new PTOCAControlSequence.TRN_TransparentData();
            trn2.setTransparentData("World");
            cs2.add(trn2);

            ptx2.setControlSequences(cs2);
            writer.handle(ptx2);
        }

        String xml = baos.toString(StandardCharsets.UTF_8);
        System.out.println(xml);

        // Verify Page 1
        assertTrue(xml.contains("page=\"1\""), "Should contain page=\"1\"");
        assertTrue(xml.contains("<PTX_PresentationTextData page=\"1\" x=\"0\" y=\"0\">"), "PTX1 should be at (0,0) on page 1");
        assertTrue(xml.contains("<AMI_AbsoluteMoveInline page=\"1\" x=\"0\" y=\"0\" displacement=\"1000\"/>"), "AMI on page 1");
        assertTrue(xml.contains("<AMB_AbsoluteMoveBaseline page=\"1\" x=\"1000\" y=\"0\" displacement=\"2000\"/>"), "AMB on page 1");
        assertTrue(xml.contains("<GraphicCharacters page=\"1\" x=\"1000\" y=\"2000\">"), "GC on page 1");

        // Verify Page 2
        assertTrue(xml.contains("page=\"2\""), "Should contain page=\"2\"");
        assertTrue(xml.contains("<PTX_PresentationTextData page=\"2\" x=\"0\" y=\"0\">"), "PTX2 should be at (0,0) on page 2");
        assertTrue(xml.contains("<AMI_AbsoluteMoveInline page=\"2\" x=\"0\" y=\"0\" displacement=\"500\"/>"), "AMI on page 2");
        assertTrue(xml.contains("<AMB_AbsoluteMoveBaseline page=\"2\" x=\"500\" y=\"0\" displacement=\"1500\"/>"), "AMB on page 2");
        assertTrue(xml.contains("<TRN_TransparentData page=\"2\" x=\"500\" y=\"1500\">"), "TRN on page 2");
    }
}
