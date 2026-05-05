package com.mgz.afp;

import com.mgz.afp.exceptions.IAFPDecodeableWriteable;
import com.mgz.afp.parser.AFPParserConfiguration;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import static org.junit.Assert.assertArrayEquals;

public class RoundTripTestUtils {

    public static void assertRoundTrip(IAFPDecodeableWriteable component, byte[] originalData) throws Exception {
        assertRoundTrip(component, originalData, new AFPParserConfiguration());
    }

    public static void assertRoundTrip(IAFPDecodeableWriteable component, byte[] originalData, AFPParserConfiguration config) throws Exception {
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
