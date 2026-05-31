package com.mgz.xml;

import com.mgz.afp.modca.MDR_MapDataResource;
import com.mgz.afp.triplets.Triplet;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class MDRFastPathTest {

    @Test
    public void testMDRFastPathOutput() throws Exception {
        MDR_MapDataResource mdr = new MDR_MapDataResource();
        List<com.mgz.afp.base.IRepeatingGroup> rgs = new ArrayList<>();

        MDR_MapDataResource.MDR_RepeatingGroup rg = new MDR_MapDataResource.MDR_RepeatingGroup();
        List<Triplet> triplets = new ArrayList<>();

        Triplet.Comment comment = new Triplet.Comment();
        comment.setComment("Test MDR FastPath");
        triplets.add(comment);

        rg.setTriplets(triplets);
        rgs.add(rg);
        mdr.setRepeatingGroups(rgs);

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try (AfpJacksonXmlWriter writer = new AfpJacksonXmlWriter(baos)) {
            writer.handle(mdr);
        }

        String xml = baos.toString(StandardCharsets.UTF_8);
        System.out.println("MDR FastPath XML:\n" + xml);

        assertTrue(xml.contains("<MDR_MapDataResource>"), "Should contain root element");
        assertTrue(xml.contains("<mdrRepeatingGroup>"), "Should contain repeating group element");
        assertTrue(xml.contains("<Comment>"), "Should contain triplet element");
        assertTrue(xml.contains("Test MDR FastPath"), "Should contain comment text");
    }
}
