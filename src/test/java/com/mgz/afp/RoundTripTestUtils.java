package com.mgz.afp;

import com.mgz.afp.base.StructuredField;
import com.mgz.afp.exceptions.IAFPDecodeableWriteable;
import com.mgz.afp.parser.AFPParser;
import com.mgz.afp.parser.AFPParserConfiguration;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertEquals;

public class RoundTripTestUtils {

    public static void assertRoundTrip(IAFPDecodeableWriteable component, byte[] originalData) throws Exception {
        assertRoundTrip(component, originalData, new AFPParserConfiguration());
    }

    public static void assertRoundTrip(IAFPDecodeableWriteable component, byte[] originalData, AFPParserConfiguration config) throws Exception {
        if (component instanceof StructuredField) {
            // For StructuredFields, originalData is expected to be the full SF (including 0x5A and SFI)
            config.setInputStream(new ByteArrayInputStream(originalData));
            AFPParser parser = new AFPParser(config);
            StructuredField parsedSf = parser.parseNextSF();

            assertNotNull("Parser returned null for StructuredField", parsedSf);
            assertEquals("Parsed SF class mismatch", component.getClass(), parsedSf.getClass());

            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            parsedSf.writeAFP(baos, config);
            byte[] serializedData = baos.toByteArray();

            assertArrayEquals("Serialized data does not match original data", originalData, serializedData);
        } else {
            // Triplets and Control Sequences
            // Decode
            component.decodeAFP(originalData, 0, originalData.length, config);

            // Write
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            component.writeAFP(baos, config);
            byte[] serializedData = baos.toByteArray();

            // Verify
            assertArrayEquals("Serialized data does not match original data", originalData, serializedData);
        }
    }
}
