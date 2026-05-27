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
import java.nio.channels.AsynchronousFileChannel;
import java.nio.channels.CompletionHandler;
import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;

/**
 * An OutputStream that writes to an {@link AsynchronousFileChannel} using pooled direct buffers.
 * It implements double-buffering (by default) and provides back-pressure by blocking
 * the caller when all buffers are currently being written asynchronously.
 */
public class AsynchronousBufferOutputStream extends OutputStream {

  private final AsynchronousFileChannel channel;
  private final int requestedBufferSize;
  private final int numBuffers;
  private final Semaphore semaphore;
  private final AtomicLong filePosition = new AtomicLong(0);
  private final AtomicReference<Throwable> error = new AtomicReference<>();
  private ByteBuffer currentBuffer;

  /**
   * Constructs an AsynchronousBufferOutputStream.
   *
   * @param channel the target asynchronous file channel
   * @param bufferSize the size of each buffer
   */
  public AsynchronousBufferOutputStream(AsynchronousFileChannel channel, int bufferSize) {
    this(channel, bufferSize, 2);
  }

  /**
   * Constructs an AsynchronousBufferOutputStream with a custom number of buffers.
   *
   * @param channel the target asynchronous file channel
   * @param bufferSize the size of each buffer
   * @param numBuffers the number of buffers to use for overlapping I/O
   */
  public AsynchronousBufferOutputStream(AsynchronousFileChannel channel, int bufferSize, int numBuffers) {
    this.channel = channel;
    this.requestedBufferSize = bufferSize;
    this.numBuffers = numBuffers;
    this.semaphore = new Semaphore(numBuffers);
  }

  @Override
  public void write(int b) throws IOException {
    checkError();
    ensureBuffer();
    currentBuffer.put((byte) b);
    if (!currentBuffer.hasRemaining()) {
      flushBuffer();
    }
  }

  @Override
  public void write(byte[] b, int off, int len) throws IOException {
    if (len <= 0) {
      return;
    }
    checkError();
    int remaining = len;
    int currentOff = off;
    while (remaining > 0) {
      ensureBuffer();
      int toCopy = Math.min(remaining, currentBuffer.remaining());
      currentBuffer.put(b, currentOff, toCopy);
      currentOff += toCopy;
      remaining -= toCopy;
      if (!currentBuffer.hasRemaining()) {
        flushBuffer();
      }
    }
  }

  private void ensureBuffer() throws IOException {
    if (currentBuffer == null) {
      try {
        semaphore.acquire();
      } catch (InterruptedException e) {
        Thread.currentThread().interrupt();
        throw new IOException("Interrupted while waiting for free buffer", e);
      }
      currentBuffer = DirectBufferPool.acquire(requestedBufferSize);
    }
  }

  private void flushBuffer() throws IOException {
    if (currentBuffer == null || currentBuffer.position() == 0) {
      if (currentBuffer != null && currentBuffer.position() == 0) {
        DirectBufferPool.release(currentBuffer);
        semaphore.release();
        currentBuffer = null;
      }
      return;
    }

    currentBuffer.flip();
    long pos = filePosition.getAndAdd(currentBuffer.remaining());
    writeAsync(currentBuffer, pos);
    currentBuffer = null;
  }

  private void writeAsync(final ByteBuffer buffer, final long position) {
    channel.write(buffer, position, null, new CompletionHandler<Integer, Object>() {
      @Override
      public void completed(Integer result, Object attachment) {
        if (buffer.hasRemaining()) {
          // Partial write, continue at new position
          writeAsync(buffer, position + result);
        } else {
          DirectBufferPool.release(buffer);
          semaphore.release();
        }
      }

      @Override
      public void failed(Throwable exc, Object attachment) {
        error.compareAndSet(null, exc);
        DirectBufferPool.release(buffer);
        semaphore.release();
      }
    });
  }

  private void checkError() throws IOException {
    Throwable t = error.get();
    if (t != null) {
      throw new IOException("Asynchronous I/O operation failed", t);
    }
  }

  @Override
  public void flush() throws IOException {
    checkError();
    flushBuffer();
  }

  @Override
  public void close() throws IOException {
    try {
      flushBuffer();
      // Wait for all pending writes to complete by acquiring all permits
      try {
        semaphore.acquire(numBuffers);
      } catch (InterruptedException e) {
        Thread.currentThread().interrupt();
        throw new IOException("Interrupted while waiting for pending writes to finish", e);
      } finally {
        semaphore.release(numBuffers);
      }
      checkError();
    } finally {
      // Note: We do NOT close the channel here as we don't own it,
      // consistent with other Alpheus stream wrappers.
    }
  }
}
