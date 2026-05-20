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

package com.mgz.afp.parser;

import com.mgz.afp.enums.SFTypeID;
import com.mgz.util.UtilBinaryDecoding;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

/**
 * High-performance scanner for AFP data streams.
 * Utilizes {@link ByteBuffer} to quickly scan for specific structured field markers
 * without full object instantiation.
 */
public class AFPScanner {

  /**
   * Scans the given buffer for structured fields of the specified type.
   *
   * @param buffer the buffer to scan
   * @param typeId the structured field type to search for
   * @return a list of offsets where the specified structured field begins (at the 0x5A byte)
   */
  public static List<Long> scanForType(ByteBuffer buffer, SFTypeID typeId) throws com.mgz.afp.exceptions.AFPParserException {
    List<Long> offsets = new ArrayList<>();
    int pos = 0;
    int limit = buffer.limit();

    byte classByte = (byte) typeId.getSfClass().toByte();
    byte typeByte = (byte) typeId.getSfType().toByte();
    byte categoryByte = (byte) typeId.getSfCategory().toByte();

    while (pos < limit) {
      // Find next 0x5A
      if ((buffer.get(pos) & 0xFF) == 0x5A) {
        if (pos + 8 < limit) {
          int sfLength = UtilBinaryDecoding.parseInt(buffer, pos + 1, 2);

          // Check if it matches the typeId
          if (buffer.get(pos + 3) == classByte &&
              buffer.get(pos + 4) == typeByte &&
              buffer.get(pos + 5) == categoryByte) {
            offsets.add((long) pos);
          }

          // Jump to next potential 0x5A
          pos += sfLength + 1;
        } else {
          break;
        }
      } else {
        pos++;
      }
    }

    return offsets;
  }

  /**
   * Scans the given buffer for multiple structured field types.
   *
   * @param buffer the buffer to scan
   * @param typeIds the structured field types to search for
   * @return a list of offsets where any of the specified structured fields begin
   */
  public static List<Long> scanForTypes(ByteBuffer buffer, List<SFTypeID> typeIds) throws com.mgz.afp.exceptions.AFPParserException {
    List<Long> offsets = new ArrayList<>();
    int pos = 0;
    int limit = buffer.limit();

    while (pos < limit) {
      if ((buffer.get(pos) & 0xFF) == 0x5A) {
        if (pos + 8 < limit) {
          int sfLength = UtilBinaryDecoding.parseInt(buffer, pos + 1, 2);

          SFTypeID currentType = SFTypeID.parse(buffer, pos + 3);
          if (currentType != null && typeIds.contains(currentType)) {
            offsets.add((long) pos);
          }

          pos += sfLength + 1;
        } else {
          break;
        }
      } else {
        pos++;
      }
    }

    return offsets;
  }
}
