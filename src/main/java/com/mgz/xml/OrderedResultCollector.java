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
import java.util.TreeMap;

/**
 * Collects byte array fragments from parallel tasks and writes them to an output stream
 * in the original sequence order.
 */
public class OrderedResultCollector {

  private final OutputStream outputStream;
  private final TreeMap<Long, byte[]> buffer = new TreeMap<>();
  private long nextSequenceToDeliver = 0;
  private final int maxBufferSize;

  /**
   * Constructs an OrderedResultCollector.
   *
   * @param outputStream the stream to write results to
   * @param maxBufferSize the maximum number of fragments to buffer before blocking
   */
  public OrderedResultCollector(OutputStream outputStream, int maxBufferSize) {
    this.outputStream = outputStream;
    this.maxBufferSize = maxBufferSize;
  }

  /**
   * Adds a fragment to the collector. Blocks if the buffer is full.
   *
   * @param sequence the sequence number of the fragment
   * @param data the fragment data
   * @throws IOException if writing to the output stream fails
   */
  public synchronized void addFragment(long sequence, byte[] data) throws IOException {
    while (buffer.size() >= maxBufferSize && sequence > nextSequenceToDeliver) {
      try {
        wait();
      } catch (InterruptedException e) {
        Thread.currentThread().interrupt();
        throw new IOException("Interrupted while waiting for buffer space", e);
      }
    }

    buffer.put(sequence, data);
    deliver();
  }

  private void deliver() throws IOException {
    while (buffer.containsKey(nextSequenceToDeliver)) {
      byte[] data = buffer.remove(nextSequenceToDeliver);
      if (data != null && data.length > 0) {
        outputStream.write(data);
      }
      nextSequenceToDeliver++;
      notifyAll();
    }
  }

  /**
   * Flushes the output stream.
   *
   * @throws IOException if flushing fails
   */
  public void flush() throws IOException {
    outputStream.flush();
  }
}
