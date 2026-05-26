package com.mgz.util;

import com.mgz.afp.parser.AFPParserConfiguration;
import org.junit.jupiter.api.Test;
import java.nio.charset.Charset;
import static org.junit.jupiter.api.Assertions.*;

public class CharsetOptimizationFlagTest {

    @Test
    public void testDecodeEbcdicRespectsFlag() {
        AFPParserConfiguration config = new AFPParserConfiguration();
        config.setAfpCharSet(Charset.forName("Cp500"));

        byte[] data = new byte[] { (byte)0xC1, (byte)0xC2, (byte)0xC3 }; // "ABC" in CP500

        // Flag OFF: should use standard implementation
        config.setUseCharsetOptimizations(false);
        String decodedOff = UtilCharacterEncoding.decodeEbcdic(data, 0, data.length, config);
        assertEquals("ABC", decodedOff);

        // Flag ON: should use fast-path
        config.setUseCharsetOptimizations(true);
        String decodedOn = UtilCharacterEncoding.decodeEbcdic(data, 0, data.length, config);
        assertEquals("ABC", decodedOn);

        // We can't easily verify which path was taken without instrumentation or mocking,
        // but we can at least ensure both produce correct results.
    }

    @Test
    public void testIsHumanReadableRespectsFlag() {
        AFPParserConfiguration config = new AFPParserConfiguration();
        config.setAfpCharSet(Charset.forName("Cp500"));

        byte[] data = new byte[] { (byte)0xC1, (byte)0xC2, (byte)0xC3 }; // "ABC" in CP500

        config.setUseCharsetOptimizations(false);
        assertTrue(UtilCharacterEncoding.isHumanReadable(data, config));

        config.setUseCharsetOptimizations(true);
        assertTrue(UtilCharacterEncoding.isHumanReadable(data, config));
    }
}
