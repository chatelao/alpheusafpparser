package com.mgz.xml;

import com.mgz.afp.modca.NOP_NoOperation;
import com.mgz.afp.parser.AFPParserConfiguration;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

public class Afp2XmlInvalidCharTest {

    @Test
    public void testInvalidXmlChar() throws Exception {
        NOP_NoOperation nop = new NOP_NoOperation();
        // 0x1a is invalid in XML 1.0
        byte[] data = "Hello\u001aWorld".getBytes(StandardCharsets.US_ASCII);
        AFPParserConfiguration config = new AFPParserConfiguration();
        config.setAfpCharSet(StandardCharsets.US_ASCII);

        nop.decodeAFP(data, 0, data.length, config);

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try (AfpJacksonXmlWriter writer = new AfpJacksonXmlWriter(baos)) {
            assertDoesNotThrow(() -> {
                writer.writeField(nop);
            });
        }
    }

    @Test
    public void testInvalidXmlCharWithXPath() throws Exception {
        NOP_NoOperation nop = new NOP_NoOperation();
        byte[] data = "Hello\u001aWorld".getBytes(StandardCharsets.US_ASCII);
        AFPParserConfiguration config = new AFPParserConfiguration();
        config.setAfpCharSet(StandardCharsets.US_ASCII);
        nop.decodeAFP(data, 0, data.length, config);

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try (AfpJacksonXmlWriter writer = new AfpJacksonXmlWriter(baos, "//NOP_NoOperation/text")) {
            assertDoesNotThrow(() -> {
                writer.writeField(nop);
            });
        }
    }
}
