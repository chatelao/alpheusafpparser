package com.mgz.xml;

import com.mgz.afp.ptoca.controlSequence.PTOCAControlSequence;
import com.mgz.afp.ptoca.PTX_PresentationTextData;
import java.io.ByteArrayOutputStream;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class PtocaSerializationTest {

    @Test
    public void testPtocaPolymorphicSerialization() throws Exception {
        PTOCAControlSequence.RMI_RelativeMoveInline rmi = new PTOCAControlSequence.RMI_RelativeMoveInline();
        rmi.setIncrement((short) 100);

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try (AfpJacksonXmlWriter writer = new AfpJacksonXmlWriter(baos, null, true)) {
             PTX_PresentationTextData ptx = new PTX_PresentationTextData();
             ptx.addControlSequence(rmi);
             writer.handle(ptx);
        }

        String xml = baos.toString();
        System.out.println("XML Output: " + xml);

        // Verify that we DON'T have double nesting
        assertFalse(xml.contains("<RMI_RelativeMoveInline><RMI_RelativeMoveInline>"), "Should not have double nesting");
        assertTrue(xml.contains("<RMI_RelativeMoveInline><increment>100</increment></RMI_RelativeMoveInline>"), "Should have correct flattened nesting");
    }
}
