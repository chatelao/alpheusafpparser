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
import java.nio.ByteBuffer;

/**
 * An OutputStream that writes directly into pooled direct ByteBuffers.
 * It automatically grows by acquiring larger buffers from the {@link DirectBufferPool}.
 */
public class DirectBufferOutputStream extends OutputStream {

  private ByteBuffer buffer;
  private boolean detached = false;

  /**
   * Constructs a DirectBufferOutputStream with an initial capacity.
   *
   * @param initialCapacity the initial capacity
   */
  public DirectBufferOutputStream(int initialCapacity) {
    this.buffer = DirectBufferPool.acquire(initialCapacity);
  }

  @Override
  public void write(int b) throws IOException {
    ensureCapacity(1);
    buffer.put((byte) b);
  }

  @Override
  public void write(byte[] b, int off, int len) throws IOException {
    if (len <= 0) return;
    ensureCapacity(len);
    buffer.put(b, off, len);
  }

  private void ensureCapacity(int needed) {
    if (buffer.remaining() < needed) {
      int newCapacity = (buffer.capacity() + needed) * 2;
      ByteBuffer newBuffer = DirectBufferPool.acquire(newCapacity);
      buffer.flip();
      newBuffer.put(buffer);
      DirectBufferPool.release(buffer);
      buffer = newBuffer;
    }
  }

  /**
   * Flips the buffer, detaches it from this stream, and returns it.
   * The caller is responsible for releasing the buffer back to the pool.
   * After calling this, the stream should no longer be used.
   *
   * @return the detached ByteBuffer
   */
  public ByteBuffer getBufferAndDetach() {
    detached = true;
    buffer.flip();
    return buffer;
  }

  @Override
  public void close() throws IOException {
    if (!detached && buffer != null) {
      DirectBufferPool.release(buffer);
      buffer = null;
    }
    super.close();
  }
}
