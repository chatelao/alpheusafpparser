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

package com.mgz.afp.base.handler;

import com.mgz.util.MappedBufferOutputStream;
import com.mgz.util.SegmentedMappedBufferOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.nio.channels.GatheringByteChannel;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Collects and writes XML fragments in the correct order.
 *
 * <p>Uses a sliding window ({@link TreeMap}) and lock-splitting drain-owner handoff so worker
 * threads depositing out-of-order or pipelined fragments never block on blocking disk I/O.
 */
public class OrderedResultCollector {

  private static final long DEFAULT_MAX_BUFFER_SIZE = 64L * 1024 * 1024; // 64MB

  private final OutputStream out;
  private final GatheringByteChannel channel;
  private final ReentrantLock queueLock = new ReentrantLock();
  private final Condition spaceAvailable = queueLock.newCondition();
  private final TreeMap<Integer, ByteBuffer> buffer = new TreeMap<>();
  private int nextSequence = 0;
  private long totalBufferedSize = 0;
  private final long maxBufferSize;
  private boolean draining = false;

  /**
   * Constructs an OrderedResultCollector.
   *
   * @param out the output stream to write ordered results to
   */
  public OrderedResultCollector(OutputStream out) {
    this(out, null, DEFAULT_MAX_BUFFER_SIZE);
  }

  /**
   * Constructs an OrderedResultCollector with an optional GatheringByteChannel.
   *
   * @param out the output stream to write ordered results to
   * @param channel the channel to use for vectorized writes (can be null)
   */
  public OrderedResultCollector(OutputStream out, GatheringByteChannel channel) {
    this(out, channel, DEFAULT_MAX_BUFFER_SIZE);
  }

  /**
   * Constructs an OrderedResultCollector with an explicit buffer limit.
   *
   * @param out the output stream to write ordered results to
   * @param channel the channel to use for vectorized writes (can be null)
   * @param maxBufferSize maximum out-of-order buffered bytes before applying backpressure
   */
  public OrderedResultCollector(
      OutputStream out, GatheringByteChannel channel, long maxBufferSize) {
    this.out = out;
    this.channel = channel;
    this.maxBufferSize = maxBufferSize;
  }

  /**
   * Adds a fragment to the collector.
   *
   * @param sequence the sequence number of the fragment
   * @param data the fragment data
   * @throws IOException if writing to the output stream fails
   */
  public void put(int sequence, byte[] data) throws IOException {
    put(sequence, ByteBuffer.wrap(data));
  }

  /**
   * Adds a fragment to the collector.
   *
   * <p>Enqueuing happens under {@code queueLock}. If another thread is already draining ready
   * fragments to the underlying sink, or if this fragment is out of order, the caller returns
   * immediately so it can resume compute work. The active drainer drains all contiguous
   * fragments outside {@code queueLock}.
   *
   * @param sequence the sequence number of the fragment
   * @param data the fragment data
   * @throws IOException if writing to the output stream fails
   */
  public void put(int sequence, ByteBuffer data) throws IOException {
    int len = data.remaining();
    boolean becameDrainer = false;

    queueLock.lock();
    try {
      while (totalBufferedSize + len > maxBufferSize && sequence != nextSequence) {
        try {
          spaceAvailable.await();
        } catch (InterruptedException e) {
          Thread.currentThread().interrupt();
          throw new IOException("Interrupted while waiting for buffer space", e);
        }
      }

      buffer.put(sequence, data);
      totalBufferedSize += len;

      if (!draining && !buffer.isEmpty() && buffer.firstKey() == nextSequence) {
        draining = true;
        becameDrainer = true;
      }
    } finally {
      queueLock.unlock();
    }

    if (!becameDrainer) {
      return;
    }

    try {
      while (true) {
        List<ByteBuffer> readyFragments;
        queueLock.lock();
        try {
          if (buffer.isEmpty() || buffer.firstKey() != nextSequence) {
            draining = false;
            spaceAvailable.signalAll();
            return;
          }
          readyFragments = new ArrayList<>();
          while (!buffer.isEmpty() && buffer.firstKey() == nextSequence) {
            ByteBuffer fragment = buffer.remove(nextSequence);
            readyFragments.add(fragment);
            totalBufferedSize -= fragment.remaining();
            nextSequence++;
          }
          spaceAvailable.signalAll();
        } finally {
          queueLock.unlock();
        }

        writeReadyFragments(readyFragments);
      }
    } catch (Throwable t) {
      queueLock.lock();
      try {
        draining = false;
        spaceAvailable.signalAll();
      } finally {
        queueLock.unlock();
      }
      throw t;
    }
  }

  private void writeReadyFragments(List<ByteBuffer> readyFragments) throws IOException {
    if (readyFragments.isEmpty()) {
      return;
    }
    if (channel != null) {
      ByteBuffer[] srcs = readyFragments.toArray(new ByteBuffer[0]);
      long totalBytes = 0;
      for (ByteBuffer b : srcs) {
        totalBytes += b.remaining();
      }
      long written = 0;
      while (written < totalBytes) {
        written += channel.write(srcs);
      }
    } else {
      for (ByteBuffer fragment : readyFragments) {
        if (out instanceof MappedBufferOutputStream mbos) {
          mbos.write(fragment);
        } else if (out instanceof SegmentedMappedBufferOutputStream smbos) {
          smbos.write(fragment);
        } else {
          int fragmentLen = fragment.remaining();
          if (fragment.hasArray()) {
            out.write(
                fragment.array(), fragment.arrayOffset() + fragment.position(), fragmentLen);
          } else {
            byte[] temp = new byte[fragmentLen];
            fragment.get(temp);
            out.write(temp);
          }
        }
      }
      out.flush();
    }
  }

  /**
   * Checks if all submitted fragments have been written.
   *
   * @return true if the buffer is empty and no drain is in flight, false otherwise
   */
  public boolean isComplete() {
    queueLock.lock();
    try {
      return !draining && buffer.isEmpty();
    } finally {
      queueLock.unlock();
    }
  }
}
