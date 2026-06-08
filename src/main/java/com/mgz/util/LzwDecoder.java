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

import java.io.ByteArrayOutputStream;

/**
 * TIFF LZW decoder implementation based on TIFF 6.0 specification.
 */
public class LzwDecoder {

  private static final int CLEAR_CODE = 256;
  private static final int EOI_CODE = 257;
  private static final int FIRST_CODE = 258;
  private static final int MAX_CODE = 4095;

  /**
   * Decodes LZW compressed data.
   *
   * @param input the compressed byte array
   * @return the decompressed byte array
   */
  public static byte[] decode(byte[] input) {
    if (input == null || input.length == 0) {
      return new byte[0];
    }

    ByteArrayOutputStream out = new ByteArrayOutputStream();
    int[] prefix = new int[4096];
    byte[] suffix = new byte[4096];
    int nextCode = FIRST_CODE;
    int codeSize = 9;

    BitReader reader = new BitReader(input);
    int code;
    int oldCode = -1;
    byte[] decodeStack = new byte[4096];

    while ((code = reader.read(codeSize)) != -1) {
      if (code == EOI_CODE) {
        break;
      }
      if (code == CLEAR_CODE) {
        nextCode = FIRST_CODE;
        codeSize = 9;
        oldCode = -1;
        continue;
      }

      if (oldCode == -1) {
        out.write(code);
        oldCode = code;
        continue;
      }

      if (code < nextCode) {
        // Code is in the dictionary
        int firstByte = decodeAndWrite(code, prefix, suffix, decodeStack, out);
        if (nextCode <= MAX_CODE) {
          prefix[nextCode] = oldCode;
          suffix[nextCode] = (byte) firstByte;
          nextCode++;
        }
      } else {
        // Code is not in the dictionary (K+w+K case)
        int firstByte = decodeAndWrite(oldCode, prefix, suffix, decodeStack, out);
        out.write(firstByte);
        if (nextCode <= MAX_CODE) {
          prefix[nextCode] = oldCode;
          suffix[nextCode] = (byte) firstByte;
          nextCode++;
        }
      }

      oldCode = code;

      // TIFF 6.0 bit size transition:
      // "the transition from 9 to 10 bits occurs after code 510 is placed in the table,
      // so that the next code, 511, is written using 10 bits."
      if (nextCode == 511) {
        codeSize = 10;
      } else if (nextCode == 1023) {
        codeSize = 11;
      } else if (nextCode == 2047) {
        codeSize = 12;
      }
    }

    return out.toByteArray();
  }

  private static int decodeAndWrite(int code, int[] prefix, byte[] suffix, byte[] decodeStack, ByteArrayOutputStream out) {
    int tempPos = decodeStack.length;
    int c = code;
    while (c >= 256) {
      decodeStack[--tempPos] = suffix[c];
      c = prefix[c];
    }
    decodeStack[--tempPos] = (byte) c;
    out.write(decodeStack, tempPos, decodeStack.length - tempPos);
    return c & 0xFF;
  }

  private static class BitReader {
    private final byte[] data;
    private int bytePos = 0;
    private int bitPos = 0;

    public BitReader(byte[] data) {
      this.data = data;
    }

    public int read(int bits) {
      if (bytePos >= data.length) {
        return -1;
      }

      int val = 0;
      for (int i = 0; i < bits; i++) {
        int b = data[bytePos] & 0xFF;
        int bit = (b >> (7 - bitPos)) & 1;
        val = (val << 1) | bit;
        bitPos++;
        if (bitPos == 8) {
          bitPos = 0;
          bytePos++;
        }
        if (bytePos >= data.length && i < bits - 1) {
          return -1;
        }
      }
      return val;
    }
  }
}
