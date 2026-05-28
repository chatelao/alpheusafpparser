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
import java.nio.MappedByteBuffer;

/**
 * Provides MappedByteBuffer segments for a segmented output stream.
 */
public interface MappedBufferProvider {

  /**
   * Provides a MappedByteBuffer for the specified position and requested size.
   *
   * @param position the start position in the underlying resource
   * @param requestedSize the requested size of the segment
   * @return a MappedByteBuffer, or null if no more segments can be provided
   * @throws IOException if the buffer cannot be provided due to I/O error
   */
  MappedByteBuffer nextSegment(long position, int requestedSize) throws IOException;
}
