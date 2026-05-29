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

package com.mgz.xml;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.nio.channels.GatheringByteChannel;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

/**
 * Collects and writes XML fragments in the correct order.
 * It uses a sliding window (TreeMap) to handle out-of-order results from parallel threads.
 */
public class OrderedResultCollector {

  private final OutputStream out;
  private final GatheringByteChannel channel;
  private final TreeMap<Integer, ByteBuffer> buffer = new TreeMap<>();
  private int nextSequence = 0;
  private long totalBufferedSize = 0;
  private final long maxBufferSize = 64 * 1024 * 1024; // 64MB

  /**
   * Constructs an OrderedResultCollector.
   *
   * @param out the output stream to write ordered results to
   */
  public OrderedResultCollector(OutputStream out) {
    this(out, null);
  }

  /**
   * Constructs an OrderedResultCollector with an optional GatheringByteChannel.
   *
   * @param out the output stream to write ordered results to
   * @param channel the channel to use for vectorized writes (can be null)
   */
  public OrderedResultCollector(OutputStream out, GatheringByteChannel channel) {
    this.out = out;
    this.channel = channel;
  }

  /**
   * Adds a fragment to the collector.
   *
   * @param sequence the sequence number of the fragment
   * @param data the fragment data
   * @throws IOException if writing to the output stream fails
   */
  public synchronized void put(int sequence, byte[] data) throws IOException {
    ByteBuffer bb = ByteBuffer.wrap(data);
    put(sequence, bb);
  }

  /**
   * Adds a fragment to the collector.
   *
   * @param sequence the sequence number of the fragment
   * @param data the fragment data
   * @throws IOException if writing to the output stream fails
   */
  public synchronized void put(int sequence, ByteBuffer data) throws IOException {
    int len = data.remaining();
    while (totalBufferedSize + len > maxBufferSize && sequence != nextSequence) {
      try {
        wait();
      } catch (InterruptedException e) {
        Thread.currentThread().interrupt();
        throw new IOException("Interrupted while waiting for buffer space", e);
      }
    }

    buffer.put(sequence, data);
    totalBufferedSize += len;

    List<ByteBuffer> readyFragments = new ArrayList<>();
    while (!buffer.isEmpty() && buffer.firstKey() == nextSequence) {
      ByteBuffer fragment = buffer.remove(nextSequence);
      readyFragments.add(fragment);
      totalBufferedSize -= fragment.remaining();
      nextSequence++;
    }

    if (!readyFragments.isEmpty()) {
      try {
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
            if (out instanceof com.mgz.util.MappedBufferOutputStream mbos) {
              mbos.write(fragment);
            } else {
              int fragmentLen = fragment.remaining();
              if (fragment.hasArray()) {
                out.write(fragment.array(), fragment.arrayOffset() + fragment.position(), fragmentLen);
              } else {
                byte[] temp = new byte[fragmentLen];
                fragment.get(temp);
                out.write(temp);
              }
            }
          }
          out.flush();
        }
      } finally {
        // No pooling needed for standard ByteBuffers
      }
      notifyAll();
    }
  }

  /**
   * Checks if all fragments have been written.
   *
   * @return true if the buffer is empty, false otherwise
   */
  public synchronized boolean isComplete() {
    return buffer.isEmpty();
  }
}
