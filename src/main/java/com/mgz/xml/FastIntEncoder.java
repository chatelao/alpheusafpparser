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

import java.io.IOException;
import java.io.OutputStream;

/**
 * A high-performance, allocation-free integer-to-UTF8 encoder.
 * It writes the numeric representation of an integer directly to an OutputStream.
 */
public class FastIntEncoder {

  private static final ThreadLocal<byte[]> BUFFER = ThreadLocal.withInitial(() -> new byte[24]);

  /**
   * Encodes an integer to UTF-8 and writes it directly to the output stream.
   *
   * @param value the integer to encode
   * @param os the output stream
   * @throws IOException if writing fails
   */
  public static void encodeAndWrite(int value, OutputStream os) throws IOException {
    if (value == 0) {
      os.write('0');
      return;
    }

    if (value == Integer.MIN_VALUE) {
      os.write("-2147483648".getBytes());
      return;
    }

    byte[] buf = BUFFER.get();
    int pos = 24;

    boolean negative = value < 0;
    if (negative) {
      value = -value;
    }

    while (value > 0) {
      buf[--pos] = (byte) ('0' + (value % 10));
      value /= 10;
    }

    if (negative) {
      buf[--pos] = '-';
    }

    os.write(buf, pos, 24 - pos);
  }

  /**
   * Encodes a long to UTF-8 and writes it directly to the output stream.
   *
   * @param value the long to encode
   * @param os the output stream
   * @throws IOException if writing fails
   */
  public static void encodeAndWrite(long value, OutputStream os) throws IOException {
    if (value == 0) {
      os.write('0');
      return;
    }

    if (value == Long.MIN_VALUE) {
      os.write("-9223372036854775808".getBytes());
      return;
    }

    byte[] buf = BUFFER.get();
    int pos = 24;

    boolean negative = value < 0;
    if (negative) {
      value = -value;
    }

    while (value > 0) {
      buf[--pos] = (byte) ('0' + (value % 10));
      value /= 10;
    }

    if (negative) {
      buf[--pos] = '-';
    }

    os.write(buf, pos, 24 - pos);
  }
}
