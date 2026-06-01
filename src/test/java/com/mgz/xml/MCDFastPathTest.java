package com.mgz.xml;

import com.mgz.afp.modca.MCD_MapContainerData;
import com.mgz.afp.triplets.Triplet;
import java.io.ByteArrayOutputStream;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class MCDFastPathTest {

    @Test
    public void testMcdFastPath() throws Exception {
        MCD_MapContainerData mcd = new MCD_MapContainerData();
        mcd.setLengthOfRepeatingGroup((short) 10);
        Triplet.Comment comment = new Triplet.Comment();
        comment.comment = "Test Comment";
        mcd.setTriplet(comment);

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try (AfpJacksonXmlWriter writer = new AfpJacksonXmlWriter(baos, null, true)) {
             writer.handle(mcd);
        }

        String xml = baos.toString();
        System.out.println("XML Output: " + xml);

        assertTrue(xml.contains("<MCD_MapContainerData>"), "Should contain MCD tag");
        assertTrue(xml.contains("<lengthOfRepeatingGroup>10</lengthOfRepeatingGroup>"), "Should contain lengthOfRepeatingGroup");
        assertTrue(xml.contains("<Comment>"), "Should contain Comment triplet");
        assertTrue(xml.contains("<comment>Test Comment</comment>"), "Should contain comment text");
    }
}
