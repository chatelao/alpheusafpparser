package com.mgz.util;

import com.mgz.afp.parser.AFPParserConfiguration;
import org.junit.jupiter.api.Test;
import java.nio.charset.Charset;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class FastCp1141DecoderTest {

    @Test
    public void testDecodeCp1141() {
        String testString = "Hello World! @#$ 123 \u20ac"; // \u20ac is Euro sign
        Charset cp1141 = Charset.forName("Cp1141");
        byte[] ebcdicBytes = testString.getBytes(cp1141);

        AFPParserConfiguration config = new AFPParserConfiguration();
        config.setAfpCharSet(cp1141);

        String decoded = UtilCharacterEncoding.decodeEbcdic(ebcdicBytes, 0, ebcdicBytes.length, config);
        assertEquals(testString, decoded, "Fast decoder output should match standard Charset decoder output");
    }

    @Test
    public void testDecodeCp1141WithOffset() {
        String testString = "Prefix Hello World! Suffix";
        Charset cp1141 = Charset.forName("Cp1141");
        byte[] ebcdicBytes = testString.getBytes(cp1141);

        AFPParserConfiguration config = new AFPParserConfiguration();
        config.setAfpCharSet(cp1141);

        // Decode "Hello World!"
        String decoded = UtilCharacterEncoding.decodeEbcdic(ebcdicBytes, 7, 12, config);
        assertEquals("Hello World!", decoded);
    }
}
