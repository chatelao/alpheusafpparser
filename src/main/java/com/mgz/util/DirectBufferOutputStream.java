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
import java.nio.channels.WritableByteChannel;

/**
 * An OutputStream that writes directly into pooled direct ByteBuffers.
 * It can operate in two modes:
 * 1. Buffered: Automatically grows by acquiring larger buffers from the {@link DirectBufferPool}.
 * 2. Streaming: Periodically flushes to a {@link WritableByteChannel} when the buffer is full.
 */
public class DirectBufferOutputStream extends OutputStream {

  private ByteBuffer buffer;
  private final WritableByteChannel channel;
  private boolean detached = false;

  /**
   * Constructs a DirectBufferOutputStream with an initial capacity.
   *
   * @param initialCapacity the initial capacity
   */
  public DirectBufferOutputStream(int initialCapacity) {
    this(initialCapacity, null);
  }

  /**
   * Constructs a DirectBufferOutputStream that flushes to a channel.
   *
   * @param initialCapacity the initial capacity
   * @param channel the channel to flush to (can be null for buffered mode)
   */
  public DirectBufferOutputStream(int initialCapacity, WritableByteChannel channel) {
    this.buffer = DirectBufferPool.acquire(initialCapacity);
    this.channel = channel;
  }

  @Override
  public void write(int b) throws IOException {
    ensureCapacity(1);
    buffer.put((byte) b);
  }

  @Override
  public void write(byte[] b, int off, int len) throws IOException {
    if (len <= 0) return;

    int remainingInB = len;
    int currentOff = off;
    while (remainingInB > 0) {
      if (!buffer.hasRemaining()) {
        ensureCapacity(1);
      }
      int toWrite = Math.min(remainingInB, buffer.remaining());
      buffer.put(b, currentOff, toWrite);
      currentOff += toWrite;
      remainingInB -= toWrite;
    }
  }

  private void ensureCapacity(int needed) throws IOException {
    if (buffer.remaining() < needed) {
      if (channel != null) {
        flush();
      }

      if (buffer.remaining() < needed) {
        int newCapacity = Math.max(buffer.capacity() * 2, buffer.capacity() + needed);
        ByteBuffer newBuffer = DirectBufferPool.acquire(newCapacity);
        buffer.flip();
        newBuffer.put(buffer);
        DirectBufferPool.release(buffer);
        buffer = newBuffer;
      }
    }
  }

  @Override
  public void flush() throws IOException {
    if (channel != null && buffer.position() > 0) {
      buffer.flip();
      while (buffer.hasRemaining()) {
        channel.write(buffer);
      }
      buffer.clear();
    }
    super.flush();
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
      if (channel != null) {
        flush();
      }
      DirectBufferPool.release(buffer);
      buffer = null;
    }
    super.close();
  }
}
