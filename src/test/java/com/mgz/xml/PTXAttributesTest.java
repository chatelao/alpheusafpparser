package com.mgz.xml;

import com.mgz.afp.modca.BPG_BeginPage;
import com.mgz.afp.modca.EPG_EndPage;
import com.mgz.afp.modca.TLE_TagLogicalElement;
import com.mgz.afp.modca.IOB_IncludeObject;
import com.mgz.afp.modca.IPS_IncludePageSegment;
import com.mgz.afp.modca.IPO_IncludePageOverlay;
import com.mgz.afp.modca.BNG_BeginNamedPageGroup;
import com.mgz.afp.modca.ENG_EndNamedPageGroup;
import com.mgz.afp.bcoca.BBC_BeginBarCodeObject;
import com.mgz.afp.goca.BGR_BeginGraphicsObject;
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

            TLE_TagLogicalElement tle1 = new TLE_TagLogicalElement();
            // TLE text comes from triplets
            com.mgz.afp.triplets.Triplet.Comment comment = new com.mgz.afp.triplets.Triplet.Comment();
            comment.comment = "TLE1";
            tle1.addTriplet(comment);
            writer.handle(tle1);

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

            PTOCAControlSequence.UCT_UnicodeComplexText uct1 = new PTOCAControlSequence.UCT_UnicodeComplexText();
            uct1.setComplexText("UCT1".getBytes(StandardCharsets.UTF_16BE));
            cs1.add(uct1);

            ptx1.setControlSequences(cs1);
            writer.handle(ptx1);

            IOB_IncludeObject iob1 = new IOB_IncludeObject();
            iob1.setName("IOB1");
            writer.handle(iob1);

            IPS_IncludePageSegment ips1 = new IPS_IncludePageSegment();
            ips1.setPageSegmentName("S1SEG001");
            ips1.setxOrigin(100);
            ips1.setyOrigin(200);
            writer.handle(ips1);

            IPO_IncludePageOverlay ipo1 = new IPO_IncludePageOverlay();
            ipo1.setOverlayName("O1OVL001");
            ipo1.setxOrigin(300);
            ipo1.setyOrigin(400);
            writer.handle(ipo1);

            BNG_BeginNamedPageGroup bng1 = new BNG_BeginNamedPageGroup();
            bng1.setName("GRP001");
            writer.handle(bng1);

            BBC_BeginBarCodeObject bbc1 = new BBC_BeginBarCodeObject();
            bbc1.setName("BC001");
            writer.handle(bbc1);

            BGR_BeginGraphicsObject bgr1 = new BGR_BeginGraphicsObject();
            bgr1.setName("GR001");
            writer.handle(bgr1);

            ENG_EndNamedPageGroup eng1 = new ENG_EndNamedPageGroup();
            eng1.setName("GRP001");
            writer.handle(eng1);

            EPG_EndPage epg1 = new EPG_EndPage();
            epg1.setName("PAGE0001");
            writer.handle(epg1);

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
        assertTrue(xml.contains("<BPG_BeginPage page=\"1\">"), "BPG should contain page=\"1\"");
        assertTrue(xml.contains("<TLE_TagLogicalElement page=\"1\">"), "TLE should contain page=\"1\"");
        assertTrue(xml.contains("<PTX_PresentationTextData page=\"1\" x=\"0\" y=\"0\">"), "PTX1 should be at (0,0) on page 1");
        assertTrue(xml.contains("<AMI_AbsoluteMoveInline page=\"1\" x=\"0\" y=\"0\" displacement=\"1000\"/>"), "AMI on page 1");
        assertTrue(xml.contains("<AMB_AbsoluteMoveBaseline page=\"1\" x=\"1000\" y=\"0\" displacement=\"2000\"/>"), "AMB on page 1");
        assertTrue(xml.contains("<GraphicCharacters page=\"1\" x=\"1000\" y=\"2000\">"), "GC on page 1");
        assertTrue(xml.contains("<UCT_UnicodeComplexText page=\"1\" x=\"1000\" y=\"2000\">"), "UCT should contain spatial attributes");
        assertTrue(xml.contains("<IOB_IncludeObject page=\"1\">"), "IOB should contain page=\"1\"");
        assertTrue(xml.contains("<IPS_IncludePageSegment page=\"1\">"), "IPS should contain page=\"1\"");
        assertTrue(xml.contains("<pageSegmentName>S1SEG001</pageSegmentName>"), "IPS should contain pageSegmentName");
        assertTrue(xml.contains("<xOrigin>100</xOrigin>"), "IPS should contain xOrigin");
        assertTrue(xml.contains("<yOrigin>200</yOrigin>"), "IPS should contain yOrigin");
        assertTrue(xml.contains("<IPO_IncludePageOverlay page=\"1\">"), "IPO should contain page=\"1\"");
        assertTrue(xml.contains("<overlayName>O1OVL001</overlayName>"), "IPO should contain overlayName");
        assertTrue(xml.contains("<xOrigin>300</xOrigin>"), "IPO should contain xOrigin");
        assertTrue(xml.contains("<yOrigin>400</yOrigin>"), "IPO should contain yOrigin");
        assertTrue(xml.contains("<BNG_BeginNamedPageGroup page=\"1\">"), "BNG should contain page=\"1\"");
        assertTrue(xml.contains("<BBC_BeginBarCodeObject page=\"1\""), "BBC should contain page=\"1\"");
        assertTrue(xml.contains("<BGR_BeginGraphicsObject page=\"1\">"), "BGR should contain page=\"1\"");
        assertTrue(xml.contains("<ENG_EndNamedPageGroup page=\"1\">"), "ENG should contain page=\"1\"");
        assertTrue(xml.contains("<EPG_EndPage page=\"1\">"), "EPG should contain page=\"1\"");

        // Verify Page 2
        assertTrue(xml.contains("<BPG_BeginPage page=\"2\">"), "BPG should contain page=\"2\"");
        assertTrue(xml.contains("<PTX_PresentationTextData page=\"2\" x=\"0\" y=\"0\">"), "PTX2 should be at (0,0) on page 2");
        assertTrue(xml.contains("<AMI_AbsoluteMoveInline page=\"2\" x=\"0\" y=\"0\" displacement=\"500\"/>"), "AMI on page 2");
        assertTrue(xml.contains("<AMB_AbsoluteMoveBaseline page=\"2\" x=\"500\" y=\"0\" displacement=\"1500\"/>"), "AMB on page 2");
        assertTrue(xml.contains("<TRN_TransparentData page=\"2\" x=\"500\" y=\"1500\">"), "TRN on page 2");
    }
}
