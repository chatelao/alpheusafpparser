package com.mgz.xml;

import com.mgz.afp.modca.NOP_NoOperation;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class BinaryEncodingTest {

    @Test
    public void testNOPBinaryEncoding() throws Exception {
        NOP_NoOperation nop = new NOP_NoOperation();
        byte[] data = new byte[] { 0x00, 0x01, 0x02, 0x03, 0x41, 0x42 }; // AAECA0FC in Base64
        nop.setData(data);

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try (AfpJacksonXmlWriter writer = new AfpJacksonXmlWriter(baos)) {
            writer.writeField(nop);
        }

        String xml = baos.toString();
        // Now it should be Base64 and in <binaryData> element
        assertTrue(xml.contains("<binaryData>AAECA0FC</binaryData>"), "XML should contain <binaryData> with Base64 values: " + xml);
    }
}
