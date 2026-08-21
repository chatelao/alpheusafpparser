/*
Copyright 2026 Rudolf Fiala

This file is part of Alpheus AFP Parser.

Alpheus AFP Parser is free software: you can redistribute it and/or modify
it under the terms of the GNU General Public License as published by
the Free Software Foundation, either version 3 of the License, or
(at your option) any later version.

Alpheus AFP Parser is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License
along with Alpheus AFP Parser.  If not, see <http://www.gnu.org/licenses/>
*/

package com.mgz.afp.base;

import com.mgz.afp.enums.SFFlag;
import com.mgz.afp.enums.SFTypeID;
import com.mgz.afp.parser.AFPParser;
import com.mgz.afp.parser.AFPParserConfiguration;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.EnumSet;

import static org.junit.jupiter.api.Assertions.*;

public class UndefinedPassthroughTest {

    @Test
    public void testUndefinedParsingAndFidelityWriteback() throws Exception {
        // Construct a raw byte stream representing an unrecognized vendor-proprietary SF
        // SF Length: 8 (SFI) + 10 (payload) = 18 (0x0012)
        // SF Type: 0xD3 0xEE 0xFF (Vendor Proprietary / Unrecognized SF Type ID)
        // Flag: 0x00, Reserved: 0x0000
        byte[] originalData = new byte[] {
            0x5A,                          // AFP Begin Byte
            0x00, 0x12,                    // Length = 18
            (byte) 0xD3, (byte) 0xEE, (byte) 0xFF, // SFTypeID (Undefined)
            0x00,                          // SFFlag
            0x00, 0x00,                    // Reserved
            0x01, 0x02, 0x03, 0x04, 0x05, 0x06, 0x07, 0x08, 0x09, 0x0A // 10 bytes payload
        };

        AFPParserConfiguration config = new AFPParserConfiguration();
        try (ByteArrayInputStream bais = new ByteArrayInputStream(originalData)) {
            config.setInputStream(bais);
            AFPParser parser = new AFPParser(config);
            StructuredField sf = parser.parseNextSF();

            assertNotNull(sf);
            assertTrue(sf instanceof Undefined, "Unrecognized SF should parse as Undefined instance");
            Undefined undef = (Undefined) sf;

            assertEquals(SFTypeID.Undefined, undef.getStructuredFieldIntroducer().getSFTypeID());
            assertNotNull(undef.getPayload());
            assertArrayEquals(new byte[] {0x01, 0x02, 0x03, 0x04, 0x05, 0x06, 0x07, 0x08, 0x09, 0x0A}, undef.getPayload());

            // Write back and check 100% byte-for-byte exact match
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            undef.writeAFP(baos, config);
            byte[] writtenBytes = baos.toByteArray();

            assertArrayEquals(originalData, writtenBytes, "Writeback serialization must produce byte-for-byte exact match");
        }
    }

    @Test
    public void testUndefinedWithSfiExtensionAndPaddingFidelity() throws Exception {
        // Construct an Undefined SF with SFI extension and padding
        // SFI length = 8 + 3 (ext: length byte 0x03 + 2 bytes data) = 11
        // Net payload = 4 bytes
        // Padding = 3 bytes (last byte indicates 3 bytes padding)
        // Total SF Length = 11 + 4 + 3 = 18 (0x0012)
        // Flag: 0x80 (hasExtension) | 0x08 (isPadded) = 0x88
        byte[] originalData = new byte[] {
            0x5A,                          // AFP Begin Byte
            0x00, 0x12,                    // Length = 18
            (byte) 0xD3, (byte) 0xFE, (byte) 0xFD, // SFTypeID
            (byte) 0x88,                  // SFFlag (hasExtension | isPadded)
            0x00, 0x00,                    // Reserved
            0x03, (byte) 0xAA, (byte) 0xBB, // SFI Extension (length 3, data 0xAA, 0xBB)
            0x11, 0x22, 0x33, 0x44,        // Net Payload (4 bytes)
            0x00, 0x00, 0x03               // Padding (3 bytes, ending in length 3)
        };

        AFPParserConfiguration config = new AFPParserConfiguration();
        try (ByteArrayInputStream bais = new ByteArrayInputStream(originalData)) {
            config.setInputStream(bais);
            AFPParser parser = new AFPParser(config);
            StructuredField sf = parser.parseNextSF();

            assertNotNull(sf);
            assertTrue(sf instanceof Undefined);
            Undefined undef = (Undefined) sf;

            assertArrayEquals(new byte[] {0x11, 0x22, 0x33, 0x44}, undef.getPayload());
            assertNotNull(undef.getPadding());
            assertArrayEquals(new byte[] {0x00, 0x00, 0x03}, undef.getPadding());

            // Write back and check exact byte match
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            undef.writeAFP(baos, config);
            byte[] writtenBytes = baos.toByteArray();

            assertArrayEquals(originalData, writtenBytes, "Writeback serialization with extension & padding must match");
        }
    }

    @Test
    public void testUndefinedResetClearsPayload() {
        Undefined undef = new Undefined();
        undef.setPayload(new byte[] {0x01, 0x02, 0x03});
        StructuredFieldIntroducer sfi = new StructuredFieldIntroducer();
        sfi.setSFTypeID(SFTypeID.Undefined);
        sfi.setFlagByte(EnumSet.of(SFFlag.hasExtension));
        undef.setStructuredFieldIntroducer(sfi);

        assertNotNull(undef.getPayload());
        assertNotNull(undef.getStructuredFieldIntroducer());

        undef.reset();

        assertNull(undef.getPayload(), "Payload should be null after reset()");
        assertNull(undef.getStructuredFieldIntroducer(), "SFI should be null after reset()");
        assertTrue(undef.isPooled(), "isPooled should reset to true");
    }

    @Test
    public void testDirectCreationAndSerialization() throws Exception {
        Undefined undef = new Undefined();
        undef.setPooled(false);

        StructuredFieldIntroducer sfi = new StructuredFieldIntroducer();
        sfi.setPooled(false);
        sfi.setSFTypeID(SFTypeID.Undefined);
        undef.setStructuredFieldIntroducer(sfi);

        byte[] customPayload = new byte[] {(byte) 0xDE, (byte) 0xAD, (byte) 0xBE, (byte) 0xEF};
        undef.setPayload(customPayload);

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        AFPParserConfiguration config = new AFPParserConfiguration();
        undef.writeAFP(baos, config);

        byte[] output = baos.toByteArray();
        assertEquals(0x5A, output[0] & 0xFF);
        // Total SF Length = 8 (SFI) + 4 (payload) = 12
        assertEquals(12, output[2] & 0xFF);
        // SFI type bytes: Undefined (0x00, 0x00, 0x00)
        assertEquals(0x00, output[3] & 0xFF);
        assertEquals(0x00, output[4] & 0xFF);
        assertEquals(0x00, output[5] & 0xFF);
        // Payload starting at index 9
        assertEquals((byte) 0xDE, output[9]);
        assertEquals((byte) 0xAD, output[10]);
        assertEquals((byte) 0xBE, output[11]);
        assertEquals((byte) 0xEF, output[12]);
    }
}
