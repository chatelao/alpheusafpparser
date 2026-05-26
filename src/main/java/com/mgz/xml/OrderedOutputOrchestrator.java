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
import java.nio.ByteBuffer;
import java.nio.channels.GatheringByteChannel;
import java.util.ArrayList;
import java.util.List;
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
  private final GatheringByteChannel channel;
  private final Map<Integer, StreamBuffer> streams = new TreeMap<>();
  private int activeStreamId = 0;
  private final AtomicInteger streamCounter = new AtomicInteger(0);
  private long totalBufferedSize = 0;
  private final long maxBufferSize = 64 * 1024 * 1024; // 64MB

  /**
   * Constructs an OrderedOutputOrchestrator.
   *
   * @param out the shared output stream to write to
   */
  public OrderedOutputOrchestrator(OutputStream out) {
    this(out, null);
  }

  /**
   * Constructs an OrderedOutputOrchestrator with an optional GatheringByteChannel.
   *
   * @param out the shared output stream to write to
   * @param channel the channel to use for vectorized writes (can be null)
   */
  public OrderedOutputOrchestrator(OutputStream out, GatheringByteChannel channel) {
    this.out = out;
    this.channel = channel;
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
    put(streamId, sequence, ByteBuffer.wrap(data));
  }

  /**
   * Adds a fragment to a specific stream.
   *
   * @param streamId the ID returned by registerStream
   * @param sequence the sequence number of the fragment within the stream (starting at 0)
   * @param data the fragment data
   * @throws IOException if writing to the underlying output stream fails
   */
  public synchronized void put(int streamId, int sequence, ByteBuffer data) throws IOException {
    int len = data.remaining();
    while (totalBufferedSize + len > maxBufferSize && !isNext(streamId, sequence)) {
      try {
        wait();
      } catch (InterruptedException e) {
        Thread.currentThread().interrupt();
        throw new IOException("Interrupted while waiting for buffer space", e);
      }
    }

    StreamBuffer stream = streams.computeIfAbsent(streamId, id -> new StreamBuffer());
    stream.put(sequence, data);
    totalBufferedSize += len;
    flushReady();
  }

  private boolean isNext(int streamId, int sequence) {
    if (streamId != activeStreamId) {
      return false;
    }
    StreamBuffer stream = streams.get(streamId);
    return (stream == null) ? sequence == 0 : sequence == stream.nextSequence;
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

  /**
   * Creates an OutputStream that writes to a specific stream ID in this orchestrator.
   *
   * @param streamId the ID of the stream
   * @return an OutputStream that wraps orchestrator calls
   */
  public OutputStream createStreamOutputStream(int streamId) {
    return new OrchestratedOutputStream(streamId);
  }

  private void flushReady() throws IOException {
    List<ByteBuffer> readyFragments = new ArrayList<>();
    while (streams.containsKey(activeStreamId)) {
      StreamBuffer stream = streams.get(activeStreamId);

      // Collect any ready fragments for the current active stream
      while (stream.hasReady()) {
        ByteBuffer fragment = stream.popNext();
        if (fragment != null && fragment.hasRemaining()) {
          readyFragments.add(fragment);
          totalBufferedSize -= fragment.remaining();
        }
      }

      if (stream.isFinished() && !stream.hasBufferedFragments()) {
        // Stream is fully consumed and marked as finished
        streams.remove(activeStreamId);
        activeStreamId++;
      } else {
        // Active stream is either not yet finished or waiting for its next sequential fragment.
        // We cannot proceed to the next stream yet to preserve overall order.
        break;
      }
    }

    if (!readyFragments.isEmpty()) {
      if (channel != null) {
        ByteBuffer[] srcs = readyFragments.toArray(new ByteBuffer[0]);
        long totalBytes = 0;
        for (ByteBuffer b : srcs) totalBytes += b.remaining();
        long written = 0;
        while (written < totalBytes) {
          written += channel.write(srcs);
        }
      } else {
        for (ByteBuffer fragment : readyFragments) {
          int fragmentLen = fragment.remaining();
          if (fragment.hasArray()) {
            out.write(fragment.array(), fragment.arrayOffset() + fragment.position(), fragmentLen);
          } else {
            byte[] temp = new byte[fragmentLen];
            fragment.get(temp);
            out.write(temp);
          }
        }
        out.flush();
      }
      notifyAll();
    }
  }

  /**
   * OutputStream implementation that automatically puts fragments into the orchestrator.
   */
  private class OrchestratedOutputStream extends OutputStream {
    private final int streamId;
    private int nextSequence = 0;

    OrchestratedOutputStream(int streamId) {
      this.streamId = streamId;
    }

    @Override
    public void write(int b) throws IOException {
      write(new byte[]{(byte) b});
    }

    @Override
    public void write(byte[] b, int off, int len) throws IOException {
      if (len == 0) return;
      // We must copy the data because the caller might reuse the buffer (e.g. BufferedOutputStream)
      ByteBuffer data = ByteBuffer.allocate(len);
      data.put(b, off, len);
      data.flip();
      put(streamId, nextSequence++, data);
    }

    @Override
    public void close() throws IOException {
      finishStream(streamId);
    }
  }

  /**
   * Internal buffer for a single stream's fragments.
   */
  private static class StreamBuffer {
    private final TreeMap<Integer, ByteBuffer> fragments = new TreeMap<>();
    int nextSequence = 0;
    private boolean finished = false;

    void put(int sequence, ByteBuffer data) {
      fragments.put(sequence, data);
    }

    boolean hasReady() {
      return !fragments.isEmpty() && fragments.firstKey() == nextSequence;
    }

    ByteBuffer popNext() {
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
