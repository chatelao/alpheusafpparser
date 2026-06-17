package com.mgz.xml;

import com.mgz.afp.ptoca.PTX_PresentationTextData;
import com.mgz.afp.ptoca.controlSequence.PTOCAControlSequence;
import com.mgz.afp.triplets.Triplet;
import com.mgz.afp.modca.BAG_BeginActiveEnvironmentGroup;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class DoubleQuoteBugTest {

    @Test
    public void testDirDoubleQuoteBug() throws Exception {
        PTX_PresentationTextData ptx = new PTX_PresentationTextData();
        PTOCAControlSequence.DIR_DrawIaxisRule dir = new PTOCAControlSequence.DIR_DrawIaxisRule();
        dir.setLength((short) 4252);
        dir.setWidth((short) 8);
        ptx.addControlSequence(dir);

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try (AfpJacksonXmlWriter writer = new AfpJacksonXmlWriter(baos, null, true, true)) {
            writer.handle(ptx);
        }
        String xml = baos.toString(StandardCharsets.UTF_8);

        System.out.println("XML: " + xml);
        // The bug looks like: length="4252"" width="8"
        assertFalse(xml.contains("\"\""), "XML should not contain double quotes: " + xml);
        assertTrue(xml.contains("length=\"4252\" width=\"8\""), "XML should contain correctly formatted attributes: " + xml);
    }

    @Test
    public void testOboDoubleQuoteBug() throws Exception {
        BAG_BeginActiveEnvironmentGroup bag = new BAG_BeginActiveEnvironmentGroup();
        Triplet.ObjectByteOffset obo = new Triplet.ObjectByteOffset();
        obo.byteOffset = 1000L;
        obo.byteOffsetHighOrder = 1L;
        bag.addTriplet(obo);

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try (AfpJacksonXmlWriter writer = new AfpJacksonXmlWriter(baos, null, true, true)) {
            writer.handle(bag);
        }
        String xml = baos.toString(StandardCharsets.UTF_8);

        System.out.println("XML OBO: " + xml);
        assertFalse(xml.contains("\"\""), "XML should not contain double quotes: " + xml);
        assertTrue(xml.contains("byteOffset=\"1000\" byteOffsetHighOrder=\"1\""), "XML should contain correctly formatted attributes: " + xml);
    }
}
