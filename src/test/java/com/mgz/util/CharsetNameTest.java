package com.mgz.util;

import org.junit.Test;
import java.nio.charset.Charset;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertEquals;

public class CharsetNameTest {

    @Test
    public void testGetCharsetFromCodePageName() {
        // Test standard 8-byte EBCDIC names with trailing spaces
        Charset cs = UtilCharacterEncoding.getCharsetFromCodePageName("T10273  ");
        assertNotNull("Should resolve T10273 with spaces", cs);
        assertEquals("IBM273", cs.name());

        cs = UtilCharacterEncoding.getCharsetFromCodePageName("T10500  ");
        assertNotNull("Should resolve T10500 with spaces", cs);
        assertEquals("IBM500", cs.name());

        // Test names starting with T1V10
        cs = UtilCharacterEncoding.getCharsetFromCodePageName("T1V10273");
        assertNotNull("Should resolve T1V10273", cs);
        assertEquals("IBM273", cs.name());

        // Test names with fewer than 8 characters (if supported)
        cs = UtilCharacterEncoding.getCharsetFromCodePageName("T10273");
        assertNotNull("Should resolve T10273 (short)", cs);
        assertEquals("IBM273", cs.name());
    }
}
