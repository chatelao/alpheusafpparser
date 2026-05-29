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
import java.nio.ByteBuffer;
import java.nio.MappedByteBuffer;

/**
 * An OutputStream that writes into multiple {@link MappedByteBuffer} segments.
 * This handles cases where the output exceeds the 2GB limit of a single MappedByteBuffer
 * or allows for incremental mapping of a large file.
 */
public class SegmentedMappedBufferOutputStream extends OutputStream {

  private final MappedBufferProvider provider;
  private final int requestedSegmentSize;
  private MappedByteBuffer currentBuffer;
  private long globalPosition = 0;

  /**
   * Constructs a SegmentedMappedBufferOutputStream.
   *
   * @param provider the provider of mapped segments
   * @param segmentSize the requested size for each segment
   */
  public SegmentedMappedBufferOutputStream(MappedBufferProvider provider, int segmentSize) {
    this.provider = provider;
    this.requestedSegmentSize = segmentSize;
  }

  private void ensureBuffer() throws IOException {
    if (currentBuffer == null || !currentBuffer.hasRemaining()) {
      if (currentBuffer != null) {
        MMapUtil.unmap(currentBuffer);
      }
      currentBuffer = provider.nextSegment(globalPosition, requestedSegmentSize);
      if (currentBuffer == null) {
        throw new IOException("No more mapped segments available at position " + globalPosition);
      }
    }
  }

  @Override
  public void write(int b) throws IOException {
    ensureBuffer();
    try {
      currentBuffer.put((byte) b);
      globalPosition++;
    } catch (BufferOverflowException e) {
      // Force next segment if somehow ensureBuffer wasn't enough
      currentBuffer = null;
      write(b);
    }
  }

  @Override
  public void write(byte[] b, int off, int len) throws IOException {
    if (len <= 0) {
      return;
    }
    int remaining = len;
    int currentOff = off;
    while (remaining > 0) {
      ensureBuffer();
      int toCopy = Math.min(remaining, currentBuffer.remaining());
      try {
        currentBuffer.put(b, currentOff, toCopy);
        currentOff += toCopy;
        remaining -= toCopy;
        globalPosition += toCopy;
      } catch (BufferOverflowException e) {
        currentBuffer = null;
      }
    }
  }

  /**
   * Writes the content of the provided ByteBuffer to this stream.
   *
   * @param src the source buffer
   * @throws IOException if writing fails
   */
  public void write(ByteBuffer src) throws IOException {
    if (src == null || !src.hasRemaining()) {
      return;
    }
    while (src.hasRemaining()) {
      ensureBuffer();
      int toCopy = Math.min(src.remaining(), currentBuffer.remaining());
      int transferred;
      if (toCopy == src.remaining()) {
        transferred = src.remaining();
        currentBuffer.put(src);
      } else {
        int oldLimit = src.limit();
        src.limit(src.position() + toCopy);
        currentBuffer.put(src);
        src.limit(oldLimit);
        transferred = toCopy;
      }
      globalPosition += transferred;
    }
  }

  @Override
  public void flush() throws IOException {
    // MappedByteBuffer is flushed by the OS.
    // For extreme reliability, one could call currentBuffer.force() here,
    // but for performance we rely on demand paging.
  }

  @Override
  public void close() throws IOException {
    if (currentBuffer != null) {
      MMapUtil.unmap(currentBuffer);
      currentBuffer = null;
    }
  }

  /**
   * Returns the total number of bytes written to this stream.
   *
   * @return the global position
   */
  public long getGlobalPosition() {
    return globalPosition;
  }
}
