/*
Copyright 2024 Rudolf Fiala

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

package com.mgz.util;

import org.junit.jupiter.api.Test;
import java.io.ByteArrayOutputStream;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class EbcdicUtf8EncoderTest {

    private static final Charset CP500 = Charset.forName("IBM500");
    private static final Charset CP273 = Charset.forName("IBM273");

    @Test
    public void testCp500Basic() throws Exception {
        String input = "Hello Alpheus! 123";
        byte[] ebcdic = input.getBytes(CP500);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        EbcdicUtf8Encoder.writeXmlText(baos, ebcdic, 0, ebcdic.length, CP500, false);
        assertEquals(input, baos.toString(StandardCharsets.UTF_8));
    }

    @Test
    public void testCp273Basic() throws Exception {
        String input = "Hello Alpheus! 123";
        byte[] ebcdic = input.getBytes(CP273);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        EbcdicUtf8Encoder.writeXmlText(baos, ebcdic, 0, ebcdic.length, CP273, false);
        assertEquals(input, baos.toString(StandardCharsets.UTF_8));
    }

    @Test
    public void testXmlEscaping() throws Exception {
        String input = "<tag attr=\"val\"> & '";
        byte[] ebcdic = input.getBytes(CP500);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        EbcdicUtf8Encoder.writeXmlText(baos, ebcdic, 0, ebcdic.length, CP500, false);
        String expected = "&lt;tag attr=&quot;val&quot;&gt; &amp; &apos;";
        assertEquals(expected, baos.toString(StandardCharsets.UTF_8));
    }

    @Test
    public void testXmlSanitization() throws Exception {
        // CP500 X'00' is NUL, which is invalid in XML 1.0. Should be replaced by space.
        byte[] ebcdic = new byte[] { (byte) 0xC1, 0x00, (byte) 0xC2 }; // 'A', NUL, 'B' in CP500
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        EbcdicUtf8Encoder.writeXmlText(baos, ebcdic, 0, ebcdic.length, CP500, true);
        assertEquals("A B", baos.toString(StandardCharsets.UTF_8));
    }

    @Test
    public void testLargeBlock() throws Exception {
        int size = 10000;
        byte[] ebcdic = new byte[size];
        Arrays.fill(ebcdic, (byte) 0x40); // EBCDIC Space
        ebcdic[0] = (byte) 0xC1; // 'A'
        ebcdic[size - 1] = (byte) 0xC2; // 'B'

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        EbcdicUtf8Encoder.writeXmlText(baos, ebcdic, 0, ebcdic.length, CP500, false);

        String result = baos.toString(StandardCharsets.UTF_8);
        assertEquals(size, result.length());
        assertEquals('A', result.charAt(0));
        assertEquals('B', result.charAt(size - 1));
        assertEquals(' ', result.charAt(5000));
    }

    @Test
    public void testSpecialCharsCp273() throws Exception {
        // German characters in CP273
        String input = "ÄÖÜäöüß";
        byte[] ebcdic = input.getBytes(CP273);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        EbcdicUtf8Encoder.writeXmlText(baos, ebcdic, 0, ebcdic.length, CP273, false);
        assertEquals(input, baos.toString(StandardCharsets.UTF_8));
    }
}
