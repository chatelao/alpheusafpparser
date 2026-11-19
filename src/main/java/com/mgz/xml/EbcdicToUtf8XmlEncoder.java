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
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CharsetEncoder;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.ConcurrentHashMap;

/**
 * A specialized EBCDIC-to-UTF-8 encoder that performs XML escaping and sanitization in a single
 * pass. It uses pre-computed lookup tables for all stateless single-byte charsets and batches
 * writes through a thread-local byte buffer to eliminate per-character virtual calls.
 */
public class EbcdicToUtf8XmlEncoder {

  private static final int BATCH_BUFFER_SIZE = 8192;
  private static final int FLUSH_THRESHOLD = BATCH_BUFFER_SIZE - 8;
  private static final ThreadLocal<byte[]> BATCH_BUFFER =
      ThreadLocal.withInitial(() -> new byte[BATCH_BUFFER_SIZE]);

  private static final EncodedTable NOT_SBCS = new EncodedTable(null, null);
  private static final ConcurrentHashMap<String, EncodedTable> TABLE_CACHE =
      new ConcurrentHashMap<>();

  private static final EncodedTable CP500_TABLE = createSbcsTable(Charset.forName("IBM500"));
  private static final EncodedTable CP273_TABLE = createSbcsTable(Charset.forName("IBM273"));
  private static final EncodedTable CP1141_TABLE = createSbcsTable(Charset.forName("IBM01141"));

  static {
    TABLE_CACHE.put("IBM500", CP500_TABLE);
    TABLE_CACHE.put("Cp500", CP500_TABLE);
    TABLE_CACHE.put("IBM273", CP273_TABLE);
    TABLE_CACHE.put("Cp273", CP273_TABLE);
    TABLE_CACHE.put("IBM01141", CP1141_TABLE);
    TABLE_CACHE.put("Cp1141", CP1141_TABLE);
  }

  private static final class EncodedTable {
    final byte[] fastSingleByte;
    final byte[][] entries;

    EncodedTable(byte[] fastSingleByte, byte[][] entries) {
      this.fastSingleByte = fastSingleByte;
      this.entries = entries;
    }
  }

  private static EncodedTable buildTableIfStatelessSbcs(Charset charset) {
    if (!charset.canEncode()) {
      return NOT_SBCS;
    }
    CharsetDecoder decoder = charset.newDecoder();
    CharsetEncoder encoder = charset.newEncoder();
    if (decoder.isAutoDetecting()
        || decoder.maxCharsPerByte() != 1.0f
        || encoder.maxBytesPerChar() != 1.0f) {
      return NOT_SBCS;
    }
    return createSbcsTable(charset);
  }

  private static EncodedTable createSbcsTable(Charset charset) {
    byte[] input = new byte[1];
    byte[] fastSingle = new byte[256];
    byte[][] entries = new byte[256][];
    for (int i = 0; i < 256; i++) {
      input[0] = (byte) i;
      String s = new String(input, charset);
      if (s.length() != 1) {
        return NOT_SBCS;
      }
      char c = s.charAt(0);
      if (Character.isSurrogate(c)) {
        return NOT_SBCS;
      }

      // XML 1.0 Sanitization (note: NUL 0x00 is replaced with space, so no valid output is 0x00)
      if (!UtilCharacterEncoding.isValidXml10CodePoint(c)) {
        c = ' ';
      }

      // XML Escaping & UTF-8 Encoding
      String escaped =
          switch (c) {
            case '&' -> "&amp;";
            case '<' -> "&lt;";
            case '>' -> "&gt;";
            case '\"' -> "&quot;";
            case '\'' -> "&apos;";
            default -> String.valueOf(c);
          };

      byte[] utf8 = escaped.getBytes(StandardCharsets.UTF_8);
      entries[i] = utf8;
      if (utf8.length == 1 && utf8[0] != 0) {
        fastSingle[i] = utf8[0];
      }
    }
    return new EncodedTable(fastSingle, entries);
  }

  /**
   * Encodes EBCDIC data to UTF-8 and writes it to the output stream, performing XML escaping and
   * sanitization.
   *
   * @param data the EBCDIC data
   * @param offset the offset in the data
   * @param len the length of the data
   * @param os the output stream to write to
   * @param charset the charset of the input data
   * @throws IOException if writing fails
   */
  public static void encodeAndWrite(
      byte[] data, int offset, int len, OutputStream os, Charset charset) throws IOException {
    if (data == null || len <= 0) {
      return;
    }
    EncodedTable table = resolveTable(charset);
    if (table != null) {
      byte[] out = BATCH_BUFFER.get();
      int outPos = 0;
      final byte[] fast = table.fastSingleByte;
      final byte[][] entries = table.entries;
      final int end = offset + len;
      for (int i = offset; i < end; i++) {
        int idx = data[i] & 0xFF;
        byte single = fast[idx];
        if (single != 0) {
          out[outPos++] = single;
          if (outPos >= FLUSH_THRESHOLD) {
            os.write(out, 0, outPos);
            outPos = 0;
          }
        } else {
          byte[] multi = entries[idx];
          int multiLen = multi.length;
          if (outPos + multiLen > out.length) {
            os.write(out, 0, outPos);
            outPos = 0;
          }
          System.arraycopy(multi, 0, out, outPos, multiLen);
          outPos += multiLen;
        }
      }
      if (outPos > 0) {
        os.write(out, 0, outPos);
      }
    } else {
      // Fallback for multi-byte or stateful charsets
      Charset effectiveCharset =
          (charset != null) ? charset : Charset.forName("IBM500");
      String s = new String(data, offset, len, effectiveCharset);
      String clean = UtilCharacterEncoding.escapeXml(UtilCharacterEncoding.sanitizeForXml(s));
      os.write(clean.getBytes(StandardCharsets.UTF_8));
    }
  }

  /**
   * Encodes EBCDIC data into the thread-local scratch buffer if it is a stateless single-byte
   * charset and guaranteed to fit within {@link #BATCH_BUFFER_SIZE}.
   *
   * @return the number of UTF-8 bytes written into {@link #getScratchBuffer()}, or {@code -1} if
   *     the caller should fall back to {@link #encodeAndWrite}.
   */
  public static int encodeToScratch(byte[] data, int offset, int len, Charset charset) {
    if (data == null || len <= 0 || len > BATCH_BUFFER_SIZE / 6) {
      return -1;
    }
    EncodedTable table = resolveTable(charset);
    if (table == null) {
      return -1;
    }
    byte[] out = BATCH_BUFFER.get();
    int outPos = 0;
    final byte[] fast = table.fastSingleByte;
    final byte[][] entries = table.entries;
    final int end = offset + len;
    for (int i = offset; i < end; i++) {
      int idx = data[i] & 0xFF;
      byte single = fast[idx];
      if (single != 0) {
        out[outPos++] = single;
      } else {
        byte[] multi = entries[idx];
        int multiLen = multi.length;
        System.arraycopy(multi, 0, out, outPos, multiLen);
        outPos += multiLen;
      }
    }
    return outPos;
  }

  public static byte[] getScratchBuffer() {
    return BATCH_BUFFER.get();
  }

  private static EncodedTable resolveTable(Charset charset) {
    if (charset == null) {
      return CP500_TABLE;
    }
    String name = charset.name();
    EncodedTable cached = TABLE_CACHE.get(name);
    if (cached != null) {
      return cached == NOT_SBCS ? null : cached;
    }
    EncodedTable built = buildTableIfStatelessSbcs(charset);
    EncodedTable existing = TABLE_CACHE.putIfAbsent(name, built);
    EncodedTable resolved = (existing != null) ? existing : built;
    return resolved == NOT_SBCS ? null : resolved;
  }
}
