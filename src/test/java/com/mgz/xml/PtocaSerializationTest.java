package com.mgz.xml;

import com.mgz.afp.ptoca.controlSequence.PTOCAControlSequence;
import com.mgz.afp.parser.AFPParserConfiguration;
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
             // In actual use, we'd handle a PTX which contains the control sequences.
             // But we can test the internal dispatch if we can call it.
             // writeControlSequence is private, so we might need a PTX or use reflection for testing.
             // Or just use the fragmentMapper directly to see what it does.
             JacksonXmlMapperProvider.getFragmentMapper().writerFor(PTOCAControlSequence.class).writeValue(baos, rmi);
        }

        String xml = baos.toString();
        System.out.println("XML Output: " + xml);
        // Current behavior with WRAPPER_OBJECT:
        // <RMI_RelativeMoveInline><RMI_RelativeMoveInline increment="100"/></RMI_RelativeMoveInline>
        // (Wait, I need to check)
    }
}
