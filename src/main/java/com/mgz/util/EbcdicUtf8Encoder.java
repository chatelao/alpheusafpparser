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

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

/**
 * A zero-allocation EBCDIC-to-UTF8 encoder for high-performance TransparentData processing.
 * Supports IBM-500 and IBM-273 with integrated XML sanitization and escaping.
 */
public class EbcdicUtf8Encoder {

  private static final byte[][] CP500_RAW = new byte[256][];
  private static final byte[][] CP500_SAN = new byte[256][];
  private static final byte[][] CP273_RAW = new byte[256][];
  private static final byte[][] CP273_SAN = new byte[256][];

  private static final ThreadLocal<byte[]> BUFFER = ThreadLocal.withInitial(() -> new byte[8192]);

  static {
    for (int i = 0; i < 256; i++) {
      char c500 = UtilCharacterEncoding.EBCDIC_CP500_TO_UTF8[i];
      CP500_RAW[i] = escape(c500);
      CP500_SAN[i] = escape(sanitize(c500));

      char c273 = UtilCharacterEncoding.EBCDIC_CP273_TO_UTF8[i];
      CP273_RAW[i] = escape(c273);
      CP273_SAN[i] = escape(sanitize(c273));
    }
  }

  private static char sanitize(char c) {
    if (c == 0x9 || c == 0xA || c == 0xD) return c;
    if (c >= 0x20 && c <= 0xD7FF) return c;
    if (c >= 0xE000 && c <= 0xFFFD) return c;
    return ' ';
  }

  private static byte[] escape(char c) {
    if (c == '<') return "&lt;".getBytes(StandardCharsets.UTF_8);
    if (c == '>') return "&gt;".getBytes(StandardCharsets.UTF_8);
    if (c == '&') return "&amp;".getBytes(StandardCharsets.UTF_8);
    if (c == '"') return "&quot;".getBytes(StandardCharsets.UTF_8);
    if (c == '\'') return "&apos;".getBytes(StandardCharsets.UTF_8);
    return toUtf8(c);
  }

  private static byte[] toUtf8(char c) {
    if (c <= 0x7F) {
      return new byte[] {(byte) c};
    } else if (c <= 0x7FF) {
      return new byte[] {(byte) (0xC0 | (c >> 6)), (byte) (0x80 | (c & 0x3F))};
    } else {
      return new byte[] {
        (byte) (0xE0 | (c >> 12)), (byte) (0x80 | ((c >> 6) & 0x3F)), (byte) (0x80 | (c & 0x3F))
      };
    }
  }

  /**
   * Writes EBCDIC data to an OutputStream as UTF-8 XML text.
   *
   * @param os the output stream to write to
   * @param ebcdic the EBCDIC byte array
   * @param off the starting offset
   * @param len the number of bytes to encode
   * @param charset the EBCDIC charset
   * @param sanitize if true, invalid XML characters are replaced with spaces
   * @throws IOException if an I/O error occurs
   */
  public static void writeXmlText(
      OutputStream os, byte[] ebcdic, int off, int len, Charset charset, boolean sanitize)
      throws IOException {
    if (ebcdic == null || len <= 0) return;

    byte[][] table = getTable(charset, sanitize);
    if (table != null) {
      byte[] buffer = BUFFER.get();
      int bIdx = 0;
      for (int i = 0; i < len; i++) {
        byte[] utf8 = table[ebcdic[off + i] & 0xFF];
        if (bIdx + utf8.length > buffer.length) {
          os.write(buffer, 0, bIdx);
          bIdx = 0;
        }
        System.arraycopy(utf8, 0, buffer, bIdx, utf8.length);
        bIdx += utf8.length;
      }
      if (bIdx > 0) {
        os.write(buffer, 0, bIdx);
      }
    } else {
      // Fallback for unsupported EBCDIC charsets
      String s = new String(ebcdic, off, len, charset);
      if (sanitize) s = UtilCharacterEncoding.sanitizeForXml(s);
      String escaped =
          s.replace("&", "&amp;")
              .replace("<", "&lt;")
              .replace(">", "&gt;")
              .replace("\"", "&quot;")
              .replace("'", "&apos;");
      os.write(escaped.getBytes(StandardCharsets.UTF_8));
    }
  }

  private static byte[][] getTable(Charset charset, boolean sanitize) {
    if (charset == null) return sanitize ? CP500_SAN : CP500_RAW;
    String name = charset.name();
    if ("IBM500".equalsIgnoreCase(name) || "Cp500".equalsIgnoreCase(name) || "IBM-500".equalsIgnoreCase(name)) {
      return sanitize ? CP500_SAN : CP500_RAW;
    } else if ("IBM273".equalsIgnoreCase(name) || "Cp273".equalsIgnoreCase(name) || "IBM-273".equalsIgnoreCase(name)) {
      return sanitize ? CP273_SAN : CP273_RAW;
    }
    return null;
  }
}
