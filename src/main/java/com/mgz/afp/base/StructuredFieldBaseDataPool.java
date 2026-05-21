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
 * A pool for {@link StructuredFieldBaseData} objects to reduce garbage collection overhead.
 */
public final class StructuredFieldBaseDataPool {

  private static final ConcurrentLinkedQueue<StructuredFieldBaseData> POOL = new ConcurrentLinkedQueue<>();

  private StructuredFieldBaseDataPool() {
    // Utility class
  }

  /**
   * Acquires a {@link StructuredFieldBaseData} from the pool or creates a new one.
   *
   * @return a {@link StructuredFieldBaseData} instance
   */
  public static StructuredFieldBaseData acquire() {
    StructuredFieldBaseData sf = POOL.poll();
    if (sf == null) {
      return new StructuredFieldBaseData();
    }
    sf.reset();
    return sf;
  }

  /**
   * Releases a {@link StructuredFieldBaseData} back to the pool.
   *
   * @param sf the instance to release
   */
  public static void release(StructuredFieldBaseData sf) {
    if (sf != null) {
      POOL.offer(sf);
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
