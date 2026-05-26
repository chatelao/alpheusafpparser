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

package com.mgz.xml;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Orchestrates ordered output from multiple concurrent streams.
 * Each stream (e.g., a file) consists of multiple fragments (e.g., pages).
 * It ensures that:
 * 1. Fragments within a stream are written in sequence.
 * 2. Streams are written in the order they were registered.
 * 3. Future streams are buffered until the current stream is completed.
 *
 * This addresses the memory bottleneck when converting multiple files to a single
 * output stream (like stdout), reducing memory pressure from O(FileSize) to
 * O(PageSize * Threads).
 */
public class OrderedOutputOrchestrator {

  private final OutputStream out;
  private final Map<Integer, StreamBuffer> streams = new TreeMap<>();
  private int activeStreamId = 0;
  private final AtomicInteger streamCounter = new AtomicInteger(0);

  /**
   * Constructs an OrderedOutputOrchestrator.
   *
   * @param out the shared output stream to write to
   */
  public OrderedOutputOrchestrator(OutputStream out) {
    this.out = out;
  }

  /**
   * Registers a new stream and returns its unique sequential ID.
   *
   * @return the stream ID
   */
  public int registerStream() {
    return streamCounter.getAndIncrement();
  }

  /**
   * Adds a fragment to a specific stream.
   *
   * @param streamId the ID returned by registerStream
   * @param sequence the sequence number of the fragment within the stream (starting at 0)
   * @param data the fragment data
   * @throws IOException if writing to the underlying output stream fails
   */
  public synchronized void put(int streamId, int sequence, byte[] data) throws IOException {
    StreamBuffer stream = streams.computeIfAbsent(streamId, id -> new StreamBuffer());
    stream.put(sequence, data);
    flushReady();
  }

  /**
   * Marks a stream as finished. No more fragments will be added to this stream.
   *
   * @param streamId the ID of the stream to finish
   * @throws IOException if writing to the underlying output stream fails
   */
  public synchronized void finishStream(int streamId) throws IOException {
    StreamBuffer stream = streams.computeIfAbsent(streamId, id -> new StreamBuffer());
    stream.setFinished();
    flushReady();
  }

  private void flushReady() throws IOException {
    while (streams.containsKey(activeStreamId)) {
      StreamBuffer stream = streams.get(activeStreamId);

      // Flush any ready fragments for the current active stream
      while (stream.hasReady()) {
        byte[] fragment = stream.popNext();
        if (fragment != null && fragment.length > 0) {
          out.write(fragment);
        }
      }

      if (stream.isFinished() && !stream.hasBufferedFragments()) {
        // Stream is fully consumed and marked as finished
        streams.remove(activeStreamId);
        activeStreamId++;
        out.flush();
      } else {
        // Active stream is either not yet finished or waiting for its next sequential fragment.
        // We cannot proceed to the next stream yet to preserve overall order.
        break;
      }
    }
  }

  /**
   * Internal buffer for a single stream's fragments.
   */
  private static class StreamBuffer {
    private final TreeMap<Integer, byte[]> fragments = new TreeMap<>();
    private int nextSequence = 0;
    private boolean finished = false;

    void put(int sequence, byte[] data) {
      fragments.put(sequence, data);
    }

    boolean hasReady() {
      return !fragments.isEmpty() && fragments.firstKey() == nextSequence;
    }

    byte[] popNext() {
      return fragments.remove(nextSequence++);
    }

    void setFinished() {
      this.finished = true;
    }

    boolean isFinished() {
      return finished;
    }

    boolean hasBufferedFragments() {
      return !fragments.isEmpty();
    }
  }
}
