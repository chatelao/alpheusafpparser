/*
Copyright 2015 Rudolf Fiala

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

import java.nio.charset.Charset;
import java.util.SortedMap;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;


public class UtilCharacterEncodingTest {

  @Test
  public void testGetCharsetFromCodePageName() {
    assertNotNull(UtilCharacterEncoding.getCharsetFromCodePageName("T1001141"), "T1001141 should return a charset");
    assertEquals(Charset.forName("IBM01141"), UtilCharacterEncoding.getCharsetFromCodePageName("T1001141"));
    assertEquals(Charset.forName("IBM500"), UtilCharacterEncoding.getCharsetFromCodePageName("T1V10500"));
    assertEquals(Charset.forName("IBM037"), UtilCharacterEncoding.getCharsetFromCodePageName("T1000037"));
  }

  @Test
  public void testIsHumanReadable() {
    Charset cp500 = Charset.forName("IBM500");

    // Plain EBCDIC text
    byte[] plainText = "Hello World".getBytes(cp500);
    assertTrue(UtilCharacterEncoding.isHumanReadable(plainText, cp500));

    // EBCDIC text with control characters (NEL, LF, CR, TAB)
    // NEL: 0x15 in IBM500 (maps to \u0085)
    // LF: 0x25 in IBM500 (maps to \n)
    // CR: 0x0D in IBM500 (maps to \r)
    // TAB: 0x05 in IBM500 (maps to \t)
    byte[] textWithControls = new byte[] {
        (byte) 0xC8, (byte) 0x85, (byte) 0x93, (byte) 0x93, (byte) 0x96, // Hello
        0x15, // NEL
        0x25, // LF
        0x0D, // CR
        0x05, // TAB
        (byte) 0xE6, (byte) 0x96, (byte) 0x99, (byte) 0x93, (byte) 0x84 // World
    };
    assertTrue(UtilCharacterEncoding.isHumanReadable(textWithControls, cp500));

    // Non-human readable binary data
    byte[] binaryData = new byte[] { 0x00, 0x01, 0x02, 0x03, 0x04, 0x06, 0x07, 0x08, 0x09 };
    assertFalse(UtilCharacterEncoding.isHumanReadable(binaryData, cp500));

    // Null and empty
    assertFalse(UtilCharacterEncoding.isHumanReadable(null, cp500));
    assertFalse(UtilCharacterEncoding.isHumanReadable(new byte[0], cp500));

    // Mixed data - threshold 0.9
    // 9 printable, 1 control -> 0.9 -> true
    byte[] mostlyPrintable = new byte[] {
        (byte) 0xC1, (byte) 0xC2, (byte) 0xC3, (byte) 0xC4, (byte) 0xC5,
        (byte) 0xC6, (byte) 0xC7, (byte) 0xC8, (byte) 0xC9,
        0x00 // Control
    };
    assertTrue(UtilCharacterEncoding.isHumanReadable(mostlyPrintable, cp500));

    // 8 printable, 2 control -> 0.8 -> false
    byte[] notMostlyPrintable = new byte[] {
        (byte) 0xC1, (byte) 0xC2, (byte) 0xC3, (byte) 0xC4, (byte) 0xC5,
        (byte) 0xC6, (byte) 0xC7, (byte) 0xC8,
        0x00, 0x01 // Controls
    };
    assertFalse(UtilCharacterEncoding.isHumanReadable(notMostlyPrintable, cp500));
  }

  @Test
  public void testStringToByteArray() {
    SortedMap<String, Charset> charsets = Charset.availableCharsets();

    String testStrings[] = new String[] {"hallo welt", "ÄÖßabc€!$!§Üäö \t\n\r", "", null};
    int[] lens = new int[] {0, 1, 3, 8, 100, 256, 1024};
    byte[] fillers = new byte[] {0x00, (byte) 0xff, 0x12, Constants.EBCDIC_ID_FILLER};

    for (Charset chs : charsets.values()) {
      if (!chs.canEncode()) {
        continue;
      }

      for (String str : testStrings) {
        for (int len : lens) {
          for (byte filler : fillers) {

            byte[] expected = str != null && str.length() > 0 ? str.getBytes(chs) : new byte[] {};

            byte[] result = UtilCharacterEncoding.stringToByteArray(str, chs, len, filler);

            for (int i = 0; i < expected.length && i < result.length; i++) {
              assertEquals(expected[i], result[i], "Resulting bytes are not as expected.");
            }

            assertEquals(len, result.length, "Length of resulting byte[] is not as expected.");
            if (len > expected.length) {
              for (int i = expected.length; i < result.length; i++) {
                assertEquals(filler, result[i], "Filler byte is not as expected.");
              }
            }
          }
        }
      }
    }
  }
}
