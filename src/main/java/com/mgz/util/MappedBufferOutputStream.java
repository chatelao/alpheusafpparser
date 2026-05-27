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
import java.nio.BufferOverflowException;
import java.nio.MappedByteBuffer;

/**
 * An OutputStream that writes directly into a {@link MappedByteBuffer}.
 * This is useful for high-performance I/O when the output file size can be
 * estimated and pre-allocated.
 */
public class MappedBufferOutputStream extends OutputStream {

  private final MappedByteBuffer buffer;

  /**
   * Constructs a MappedBufferOutputStream.
   *
   * @param buffer the mapped byte buffer to write to
   */
  public MappedBufferOutputStream(MappedByteBuffer buffer) {
    this.buffer = buffer;
  }

  @Override
  public void write(int b) throws IOException {
    try {
      buffer.put((byte) b);
    } catch (BufferOverflowException e) {
      throw new IOException("Mapped buffer overflow", e);
    }
  }

  @Override
  public void write(byte[] b, int off, int len) throws IOException {
    if (len <= 0) {
      return;
    }
    try {
      buffer.put(b, off, len);
    } catch (BufferOverflowException e) {
      throw new IOException("Mapped buffer overflow", e);
    }
  }

  /**
   * Returns the underlying MappedByteBuffer.
   *
   * @return the buffer
   */
  public MappedByteBuffer getBuffer() {
    return buffer;
  }

  @Override
  public void flush() throws IOException {
    // MappedByteBuffer is flushed by the OS, but we can force it if needed.
    // For now, we rely on demand paging for performance.
  }

  @Override
  public void close() throws IOException {
    // No-op for MappedByteBuffer
  }
}
