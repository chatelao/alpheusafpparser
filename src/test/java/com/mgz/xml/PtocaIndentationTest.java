package com.mgz.xml;

import com.mgz.afp.ptoca.controlSequence.PTOCAControlSequence;
import com.mgz.afp.ptoca.PTX_PresentationTextData;
import java.io.ByteArrayOutputStream;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class PtocaIndentationTest {

    @Test
    public void testPtocaIndentation() throws Exception {
        PTOCAControlSequence.AMI_AbsoluteMoveInline ami = new PTOCAControlSequence.AMI_AbsoluteMoveInline();
        ami.setDisplacement((short) 1234);

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        // Enable indentation
        try (AfpJacksonXmlWriter writer = new AfpJacksonXmlWriter(baos, null, false, true)) {
             PTX_PresentationTextData ptx = new PTX_PresentationTextData();
             ptx.addControlSequence(ami);
             writer.handle(ptx);
        }

        String xml = baos.toString();
        System.out.println("Indented XML Output:\n" + xml);

        // Verify that we have indentation
        assertTrue(xml.contains("\n  <PTX_PresentationTextData>"), "Should have PTX indentation");
        assertTrue(xml.contains("\n    <AMI_AbsoluteMoveInline displacement=\"1234\"/>"), "Should have AMI indentation and correct attribute");
    }
}
