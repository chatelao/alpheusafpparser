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
import java.nio.ByteBuffer;

/**
 * A {@link ByteArrayOutputStream} that exposes its internal byte buffer as a read-only slice
 * without copying via {@link #toByteArray()}.
 */
public class ExposedByteArrayOutputStream extends ByteArrayOutputStream {

  /**
   * Creates a new stream with the default initial buffer capacity.
   */
  public ExposedByteArrayOutputStream() {
    super();
  }

  /**
   * Creates a new stream with the specified initial buffer capacity.
   *
   * @param size the initial buffer size in bytes
   */
  public ExposedByteArrayOutputStream(int size) {
    super(Math.max(32, size));
  }

  /**
   * Wraps the written bytes directly into a {@link ByteBuffer} without allocating or copying
   * when buffer utilization is reasonable. If the allocated array is significantly larger than
   * {@code count}, returns an exact-sized copy so oversized pre-allocations can be reclaimed.
   *
   * @return a ByteBuffer backed by the written bytes
   */
  public ByteBuffer toByteBuffer() {
    int waste = buf.length - count;
    if (waste > Math.max(65536, count >> 1)) {
      return ByteBuffer.wrap(toByteArray());
    }
    return ByteBuffer.wrap(buf, 0, count);
  }
}
