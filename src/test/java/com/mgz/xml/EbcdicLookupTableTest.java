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

package com.mgz.xml;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.mgz.afp.parser.AFPParserConfiguration;
import com.mgz.util.UtilCharacterEncoding;
import java.io.ByteArrayOutputStream;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.List;
import org.junit.jupiter.api.Test;

/**
 * Verifies single-pass SBCS lookup-table equivalence, multi-chunk batching, escaping/sanitizing,
 * and fallback paths for {@link EbcdicToUtf8XmlEncoder} and {@link UtilCharacterEncoding}.
 */
public class EbcdicLookupTableTest {

  private static final List<String> SBCS_CHARSETS =
      List.of(
          "IBM500",
          "IBM273",
          "IBM01141",
          "IBM037",
          "IBM1047",
          "IBM870",
          "US-ASCII",
          "ISO-8859-1");

  @Test
  public void testAll256ByteValuesMatchStandardXmlEscapedOutput() throws Exception {
    byte[] allBytes = new byte[256];
    for (int i = 0; i < 256; i++) {
      allBytes[i] = (byte) i;
    }

    for (String csName : SBCS_CHARSETS) {
      Charset cs = Charset.forName(csName);
      ByteArrayOutputStream baos = new ByteArrayOutputStream();
      EbcdicToUtf8XmlEncoder.encodeAndWrite(allBytes, 0, allBytes.length, baos, cs);
      String actual = baos.toString(StandardCharsets.UTF_8);

      String rawDecoded = new String(allBytes, cs);
      String expected =
          UtilCharacterEncoding.escapeXml(UtilCharacterEncoding.sanitizeForXml(rawDecoded));
      assertEquals(expected, actual, "Mismatch in EbcdicToUtf8XmlEncoder for charset " + csName);
    }
  }

  @Test
  public void testLargeChunkedBatchedWriteAcross8KbBoundaries() throws Exception {
    Charset cp1141 = Charset.forName("IBM01141");
    String unit = "Grüezi <World> & 'Quoted' \"Test\" € 1234567890!";
    StringBuilder sb = new StringBuilder();
    for (int i = 0; i < 600; i++) {
      sb.append(unit);
    }
    String rawText = sb.toString();
    byte[] inputBytes = rawText.getBytes(cp1141);
    assertTrue(inputBytes.length > 20000);

    // Also mix in non-zero offset slicing
    byte[] paddedInput = new byte[inputBytes.length + 17];
    System.arraycopy(inputBytes, 0, paddedInput, 7, inputBytes.length);

    ByteArrayOutputStream baos = new ByteArrayOutputStream();
    EbcdicToUtf8XmlEncoder.encodeAndWrite(paddedInput, 7, inputBytes.length, baos, cp1141);
    String expected = UtilCharacterEncoding.escapeXml(rawText);
    assertEquals(expected, baos.toString(StandardCharsets.UTF_8));
  }

  @Test
  public void testMultiByteCharsetFallbackEscapesAndSanitizes() throws Exception {
    String input = "UTF8: Grüße <&> \"quotes\"😀\u0000end";
    byte[] utf8Bytes = input.getBytes(StandardCharsets.UTF_8);
    ByteArrayOutputStream baos = new ByteArrayOutputStream();
    EbcdicToUtf8XmlEncoder.encodeAndWrite(
        utf8Bytes, 0, utf8Bytes.length, baos, StandardCharsets.UTF_8);

    String expected = UtilCharacterEncoding.escapeXml(UtilCharacterEncoding.sanitizeForXml(input));
    assertEquals(expected, baos.toString(StandardCharsets.UTF_8));
  }

  @Test
  public void testUtilCharacterEncodingByteArrayHeapAndDirectBuffersMatchJdkCharset() {
    byte[] allBytes = new byte[256];
    for (int i = 0; i < 256; i++) {
      allBytes[i] = (byte) i;
    }
    ByteBuffer heapBuf = ByteBuffer.wrap(allBytes);
    ByteBuffer directBuf = ByteBuffer.allocateDirect(256);
    directBuf.put(allBytes).flip();

    for (String csName : SBCS_CHARSETS) {
      Charset cs = Charset.forName(csName);
      AFPParserConfiguration cfg = new AFPParserConfiguration();
      cfg.setAfpCharSet(cs);

      String expected = new String(allBytes, cs);
      assertEquals(expected, UtilCharacterEncoding.decodeEbcdic(allBytes, 0, 256, cfg), csName);
      assertEquals(expected, UtilCharacterEncoding.decodeEbcdic(heapBuf, 0, 256, cfg), csName);
      assertEquals(expected, UtilCharacterEncoding.decodeEbcdic(directBuf, 0, 256, cfg), csName);
    }
  }
}
