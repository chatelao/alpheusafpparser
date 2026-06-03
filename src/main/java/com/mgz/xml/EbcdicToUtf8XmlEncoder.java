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

import com.mgz.util.UtilCharacterEncoding;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

/**
 * A specialized EBCDIC-to-UTF-8 encoder that performs XML escaping and sanitization in a single pass.
 * It uses pre-computed lookup tables for maximum performance.
 */
public class EbcdicToUtf8XmlEncoder {

  private static final byte[][] CP500_TABLE = new byte[256][];
  private static final byte[][] CP273_TABLE = new byte[256][];
  private static final byte[][] CP1141_TABLE = new byte[256][];

  static {
    initTable(CP500_TABLE, "IBM500");
    initTable(CP273_TABLE, "IBM273");
    initTable(CP1141_TABLE, "IBM01141");
  }

  private static void initTable(byte[][] table, String charsetName) {
    Charset ebcdic = Charset.forName(charsetName);
    byte[] input = new byte[1];
    for (int i = 0; i < 256; i++) {
      input[0] = (byte) i;
      String s = new String(input, ebcdic);
      char c = s.charAt(0);

      // XML 1.0 Sanitization
      if (!UtilCharacterEncoding.isValidXml10CodePoint(c)) {
        c = ' ';
      }

      // XML Escaping & UTF-8 Encoding
      String escaped;
      switch (c) {
        case '&':  escaped = "&amp;"; break;
        case '<':  escaped = "&lt;"; break;
        case '>':  escaped = "&gt;"; break;
        case '\"': escaped = "&quot;"; break;
        case '\'': escaped = "&apos;"; break;
        default:   escaped = String.valueOf(c); break;
      }

      table[i] = escaped.getBytes(StandardCharsets.UTF_8);
    }
  }

  /**
   * Encodes EBCDIC data to UTF-8 and writes it to the output stream, performing XML escaping and sanitization.
   *
   * @param data the EBCDIC data
   * @param offset the offset in the data
   * @param len the length of the data
   * @param os the output stream to write to
   * @param charset the charset of the input data
   * @throws IOException if writing fails
   */
  public static void encodeAndWrite(byte[] data, int offset, int len, OutputStream os, Charset charset) throws IOException {
    byte[][] table = getTable(charset);
    if (table != null) {
      for (int i = 0; i < len; i++) {
        byte[] encoded = table[data[offset + i] & 0xFF];
        os.write(encoded);
      }
    } else {
      // Fallback for unsupported charsets
      String s = new String(data, offset, len, charset);
      os.write(UtilCharacterEncoding.sanitizeForXml(s).getBytes(StandardCharsets.UTF_8));
    }
  }

  private static byte[][] getTable(Charset charset) {
    String name = charset.name();
    if ("IBM500".equalsIgnoreCase(name) || "Cp500".equalsIgnoreCase(name)) {
      return CP500_TABLE;
    } else if ("IBM273".equalsIgnoreCase(name) || "Cp273".equalsIgnoreCase(name)) {
      return CP273_TABLE;
    } else if ("IBM01141".equalsIgnoreCase(name) || "Cp1141".equalsIgnoreCase(name)) {
      return CP1141_TABLE;
    }
    return null;
  }
}
