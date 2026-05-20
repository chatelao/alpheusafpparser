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

package com.mgz.afp.base;

import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * A pool for {@link StructuredFieldIntroducer} objects to reduce garbage collection overhead.
 */
public final class SfiPool {

  private static final ConcurrentLinkedQueue<StructuredFieldIntroducer> POOL = new ConcurrentLinkedQueue<>();

  private SfiPool() {
    // Utility class
  }

  /**
   * Acquires a {@link StructuredFieldIntroducer} from the pool or creates a new one.
   *
   * @return a {@link StructuredFieldIntroducer} instance
   */
  public static StructuredFieldIntroducer acquire() {
    StructuredFieldIntroducer sfi = POOL.poll();
    if (sfi == null) {
      return new StructuredFieldIntroducer();
    }
    sfi.reset();
    return sfi;
  }

  /**
   * Releases a {@link StructuredFieldIntroducer} back to the pool.
   *
   * @param sfi the instance to release
   */
  public static void release(StructuredFieldIntroducer sfi) {
    if (sfi != null) {
      POOL.offer(sfi);
    }
  }

  /**
   * Returns the current size of the pool.
   *
   * @return the pool size
   */
  public static int size() {
    return POOL.size();
  }
}
