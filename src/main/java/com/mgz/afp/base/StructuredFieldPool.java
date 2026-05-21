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

import com.mgz.afp.enums.SFTypeID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * A pool for {@link StructuredField} objects to reduce garbage collection overhead.
 */
public final class StructuredFieldPool {

  private static final ConcurrentHashMap<SFTypeID, ConcurrentLinkedQueue<StructuredField>> POOLS =
      new ConcurrentHashMap<>();

  private StructuredFieldPool() {
    // Utility class
  }

  /**
   * Acquires a {@link StructuredField} from the pool for the specified type.
   *
   * @param type the structured field type ID
   * @return a {@link StructuredField} instance, or null if the pool is empty
   */
  public static StructuredField acquire(SFTypeID type) {
    if (type == null) {
      return null;
    }
    ConcurrentLinkedQueue<StructuredField> pool = POOLS.get(type);
    if (pool == null) {
      return null;
    }
    StructuredField sf = pool.poll();
    if (sf != null) {
      sf.reset();
    }
    return sf;
  }

  /**
   * Releases a {@link StructuredField} back to the pool.
   *
   * @param sf   the instance to release
   * @param type the structured field type ID
   */
  public static void release(StructuredField sf, SFTypeID type) {
    if (sf != null && type != null) {
      POOLS.computeIfAbsent(type, k -> new ConcurrentLinkedQueue<>())
          .offer(sf);
    }
  }
}
