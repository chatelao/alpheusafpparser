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

package com.mgz.util;

import java.io.IOException;
import java.io.OutputStream;

/**
 * High-performance integer to UTF-8 byte conversion that avoids String allocations.
 */
public class FastIntConverter {
  private static final byte[] MIN_VALUE_BYTES = "-2147483648".getBytes();
  private static final byte[][] SMALL_INTS = new byte[100][];

  static {
    for (int i = 0; i < 100; i++) {
      SMALL_INTS[i] = Integer.toString(i).getBytes();
    }
  }

  /**
   * Writes an integer to the output stream as UTF-8 bytes.
   *
   * @param value the integer value
   * @param os the output stream
   * @throws IOException if writing fails
   */
  public static void writeInt(int value, OutputStream os) throws IOException {
    if (value >= 0 && value < 100) {
      os.write(SMALL_INTS[value]);
      return;
    }
    if (value == Integer.MIN_VALUE) {
      os.write(MIN_VALUE_BYTES);
      return;
    }

    if (value < 0) {
      os.write('-');
      value = -value;
    }

    // Use a small stack-allocated-like buffer for the digits
    byte[] buf = new byte[11];
    int pos = 11;
    do {
      buf[--pos] = (byte) ('0' + (value % 10));
      value /= 10;
    } while (value > 0);

    os.write(buf, pos, 11 - pos);
  }

  /**
   * Writes a long to the output stream as UTF-8 bytes.
   *
   * @param value the long value
   * @param os the output stream
   * @throws IOException if writing fails
   */
  public static void writeLong(long value, OutputStream os) throws IOException {
    if (value >= 0 && value < 100) {
      os.write(SMALL_INTS[(int) value]);
      return;
    }
    if (value == Long.MIN_VALUE) {
      os.write("-9223372036854775808".getBytes());
      return;
    }

    if (value < 0) {
      os.write('-');
      value = -value;
    }

    byte[] buf = new byte[20];
    int pos = 20;
    do {
      buf[--pos] = (byte) ('0' + (value % 10));
      value /= 10;
    } while (value > 0);

    os.write(buf, pos, 20 - pos);
  }

  /**
   * Converts an integer to UTF-8 bytes and writes them into the provided buffer.
   *
   * @param value the integer value
   * @param buffer the target buffer
   * @param offset the start offset
   * @return the number of bytes written
   */
  public static int intToUtf8(int value, byte[] buffer, int offset) {
    if (value >= 0 && value < 100) {
      byte[] small = SMALL_INTS[value];
      System.arraycopy(small, 0, buffer, offset, small.length);
      return small.length;
    }
    if (value == Integer.MIN_VALUE) {
      System.arraycopy(MIN_VALUE_BYTES, 0, buffer, offset, MIN_VALUE_BYTES.length);
      return MIN_VALUE_BYTES.length;
    }

    int pos = offset;
    if (value < 0) {
      buffer[pos++] = '-';
      value = -value;
    }

    int start = pos;
    do {
      buffer[pos++] = (byte) ('0' + (value % 10));
      value /= 10;
    } while (value > 0);

    int end = pos - 1;
    int written = pos - offset;
    while (start < end) {
      byte tmp = buffer[start];
      buffer[start] = buffer[end];
      buffer[end] = tmp;
      start++;
      end--;
    }
    return written;
  }

  /**
   * Converts a long to UTF-8 bytes and writes them into the provided buffer.
   *
   * @param value the long value
   * @param buffer the target buffer
   * @param offset the start offset
   * @return the number of bytes written
   */
  public static int longToUtf8(long value, byte[] buffer, int offset) {
    if (value >= 0 && value < 100) {
      byte[] small = SMALL_INTS[(int) value];
      System.arraycopy(small, 0, buffer, offset, small.length);
      return small.length;
    }
    if (value == Long.MIN_VALUE) {
      byte[] minBytes = "-9223372036854775808".getBytes();
      System.arraycopy(minBytes, 0, buffer, offset, minBytes.length);
      return minBytes.length;
    }

    int pos = offset;
    if (value < 0) {
      buffer[pos++] = '-';
      value = -value;
    }

    int start = pos;
    do {
      buffer[pos++] = (byte) ('0' + (value % 10));
      value /= 10;
    } while (value > 0);

    int end = pos - 1;
    int written = pos - offset;
    while (start < end) {
      byte tmp = buffer[start];
      buffer[start] = buffer[end];
      buffer[end] = tmp;
      start++;
      end--;
    }
    return written;
  }
}
