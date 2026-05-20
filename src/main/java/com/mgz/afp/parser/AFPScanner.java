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
 * High-performance scanner for identifying Structured Field offsets within a {@link ByteBuffer}.
 * It navigates the {@code 0x5A} record chain to avoid full object instantiation.
 */
public class AFPScanner {

  private final ByteBuffer buffer;

  /**
   * Constructs an {@code AFPScanner} for the given buffer.
   *
   * @param buffer the buffer to scan
   */
  public AFPScanner(ByteBuffer buffer) {
    this.buffer = buffer;
  }

  /**
   * Scans the buffer for all structured fields of the specified type.
   *
   * @param typeID the type of structured field to look for
   * @return a list of absolute offsets to the {@code 0x5A} marker of each matching field
   */
  public List<Long> scanFor(SFTypeID typeID) {
    if (buffer == null) {
      return new ArrayList<>();
    }
    List<Long> offsets = new ArrayList<>();
    int pos = buffer.position();
    int limit = buffer.limit();

    while (pos < limit) {
      // Find next 0x5A marker (MODCA structured field record marker)
      if ((buffer.get(pos) & 0xFF) == 0x5A) {
        if (pos + 8 >= limit) {
          break; // Incomplete SFI at the end of the buffer
        }

        int sfLength;
        try {
          // The 2 bytes at pos + 1 contain the length of the SF excluding the 0x5A marker
          sfLength = UtilBinaryDecoding.parseInt(buffer, pos + 1, 2);
        } catch (Exception e) {
          // Robustness fallback: if length parsing fails, move to next byte
          pos++;
          continue;
        }

        SFTypeID foundType = SFTypeID.parse(buffer, pos + 3);
        if (foundType == typeID) {
          offsets.add((long) pos);
        }

        if (sfLength < 8) {
          // SFI must be at least 8 bytes. If less, the marker might be just data.
          pos++;
        } else {
          // Total record size is 1 (marker) + length. Jump to the next potential record.
          pos += 1 + sfLength;
        }
      } else {
        pos++;
      }
    }
    return offsets;
  }

  /**
   * Specialized scan to find all {@code BPG_BeginPage} structured fields.
   * This is useful for identifying page boundaries for parallel processing.
   *
   * @return a list of absolute offsets to the {@code 0x5A} marker of each Begin Page field
   */
  public List<Long> findPageBoundaries() {
    return scanFor(SFTypeID.BPG_BeginPage);
  }
}
