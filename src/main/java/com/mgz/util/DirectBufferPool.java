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
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * A recycler for {@link ByteBuffer#allocateDirect(int)} to avoid allocation and GC overhead.
 * Uses a two-tier strategy: L1 (ThreadLocal) and L2 (Global Concurrent Queue).
 */
public final class DirectBufferPool {

  public static final int SIZE_8K = 8 * 1024;
  public static final int SIZE_64K = 64 * 1024;
  public static final int SIZE_1M = 1024 * 1024;

  private static final int L1_CAPACITY = 8; // buffers per size per thread

  private static final Pool L2_8K = new Pool(SIZE_8K);
  private static final Pool L2_64K = new Pool(SIZE_64K);
  private static final Pool L2_1M = new Pool(SIZE_1M);

  private static final ThreadLocal<LocalPools> L1_POOLS = ThreadLocal.withInitial(LocalPools::new);

  private DirectBufferPool() {
    // Utility class
  }

  /**
   * Acquires a direct ByteBuffer of at least the requested size.
   *
   * @param minSize the minimum required capacity
   * @return a direct ByteBuffer
   */
  public static ByteBuffer acquire(int minSize) {
    if (minSize <= SIZE_8K) {
      return L1_POOLS.get().pool8K.acquire(L2_8K);
    } else if (minSize <= SIZE_64K) {
      return L1_POOLS.get().pool64K.acquire(L2_64K);
    } else if (minSize <= SIZE_1M) {
      return L1_POOLS.get().pool1M.acquire(L2_1M);
    }
    // Fallback for larger sizes
    return ByteBuffer.allocateDirect(minSize);
  }

  /**
   * Releases a direct ByteBuffer back to the pool.
   *
   * @param buffer the buffer to release
   */
  public static void release(ByteBuffer buffer) {
    if (buffer == null || !buffer.isDirect()) {
      return;
    }

    int capacity = buffer.capacity();
    buffer.clear();

    if (capacity == SIZE_8K) {
      L1_POOLS.get().pool8K.release(buffer, L2_8K);
    } else if (capacity == SIZE_64K) {
      L1_POOLS.get().pool64K.release(buffer, L2_64K);
    } else if (capacity == SIZE_1M) {
      L1_POOLS.get().pool1M.release(buffer, L2_1M);
    }
    // Buffers of other sizes are left for GC
  }

  private static class LocalPools {
    final LocalPool pool8K = new LocalPool();
    final LocalPool pool64K = new LocalPool();
    final LocalPool pool1M = new LocalPool();
  }

  private static class LocalPool {
    private final Deque<ByteBuffer> queue = new ArrayDeque<>(L1_CAPACITY);

    ByteBuffer acquire(Pool l2) {
      ByteBuffer b = queue.pollFirst();
      if (b == null) {
        b = l2.queue.poll();
      }
      if (b == null) {
        b = ByteBuffer.allocateDirect(l2.size);
      }
      return b;
    }

    void release(ByteBuffer b, Pool l2) {
      if (queue.size() < L1_CAPACITY) {
        queue.addFirst(b);
      } else {
        l2.queue.offer(b);
      }
    }
  }

  private static class Pool {
    final int size;
    final ConcurrentLinkedQueue<ByteBuffer> queue = new ConcurrentLinkedQueue<>();

    Pool(int size) {
      this.size = size;
    }
  }
}
