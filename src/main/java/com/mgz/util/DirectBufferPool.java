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

import java.nio.ByteBuffer;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * A pool for DirectByteBuffers to reduce allocation overhead and GC pressure
 * when performing vectorized I/O.
 */
public final class DirectBufferPool {

  private static final int MIN_POWER = 13; // 8KB
  private static final int MAX_POWER = 22; // 4MB
  private static final int NUM_BUCKETS = MAX_POWER - MIN_POWER + 1;

  @SuppressWarnings("unchecked")
  private static final ConcurrentLinkedQueue<ByteBuffer>[] BUCKETS = new ConcurrentLinkedQueue[NUM_BUCKETS];

  static {
    for (int i = 0; i < NUM_BUCKETS; i++) {
      BUCKETS[i] = new ConcurrentLinkedQueue<>();
    }
  }

  private DirectBufferPool() {
    // Utility class
  }

  /**
   * Acquires a direct ByteBuffer of at least the requested capacity.
   *
   * @param capacity the required capacity
   * @return a direct ByteBuffer
   */
  public static ByteBuffer acquire(int capacity) {
    if (capacity <= 0) {
      return ByteBuffer.allocateDirect(0);
    }

    int bucketIdx = getBucketIndex(capacity);
    if (bucketIdx < 0 || bucketIdx >= NUM_BUCKETS) {
      // Too large to pool, just allocate
      return ByteBuffer.allocateDirect(capacity);
    }

    ByteBuffer buffer = BUCKETS[bucketIdx].poll();
    if (buffer == null) {
      int bucketCapacity = 1 << (bucketIdx + MIN_POWER);
      return ByteBuffer.allocateDirect(bucketCapacity);
    }

    buffer.clear();
    return buffer;
  }

  /**
   * Releases a direct ByteBuffer back to the pool.
   * Only direct buffers that match one of the pool's bucket sizes are accepted.
   *
   * @param buffer the buffer to release
   */
  public static void release(ByteBuffer buffer) {
    if (buffer == null || !buffer.isDirect()) {
      return;
    }

    int capacity = buffer.capacity();
    int bucketIdx = getBucketIndex(capacity);

    // Only pool if it's exactly the bucket size and within range
    if (bucketIdx >= 0 && bucketIdx < NUM_BUCKETS && capacity == (1 << (bucketIdx + MIN_POWER))) {
      // Optional: limit queue size to prevent excessive native memory usage
      if (BUCKETS[bucketIdx].size() < 128) {
        BUCKETS[bucketIdx].offer(buffer);
      }
    }
  }

  private static int getBucketIndex(int capacity) {
    if (capacity <= 0) return -1;
    // Find next power of two
    int power = 32 - Integer.numberOfLeadingZeros(capacity - 1);
    power = Math.max(power, MIN_POWER);
    return power - MIN_POWER;
  }

  /**
   * Prints statistics about the direct buffer pool to the specified stream.
   *
   * @param out the print stream to write to
   */
  public static void printPoolStats(java.io.PrintStream out) {
    out.println("### DirectBufferPool Statistics");
    out.println();
    out.println("| Bucket Size | Count | Total Capacity (MB) |");
    out.println("| :--- | ---: | ---: |");

    long grandTotalCapacity = 0;
    int grandTotalCount = 0;

    for (int i = 0; i < NUM_BUCKETS; i++) {
      int count = BUCKETS[i].size();
      if (count > 0) {
        long bucketSize = 1L << (i + MIN_POWER);
        long totalBucketCapacity = count * bucketSize;
        grandTotalCapacity += totalBucketCapacity;
        grandTotalCount += count;

        String sizeStr = bucketSize < 1024 * 1024
            ? (bucketSize / 1024) + " KB"
            : (bucketSize / (1024 * 1024)) + " MB";

        out.println(String.format("| %-11s | %5d | %19.2f |",
            sizeStr, count, (double) totalBucketCapacity / (1024 * 1024)));
      }
    }

    if (grandTotalCount == 0) {
      out.println("| Total       |     0 |                0.00 |");
    } else {
      out.println(String.format("| **Total**   | **%3d** | **%15.2f** |",
          grandTotalCount, (double) grandTotalCapacity / (1024 * 1024)));
    }
    out.println();
  }
}
