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

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * A pool for {@link IRepeatingGroup} objects to reduce garbage collection overhead.
 */
public final class RepeatingGroupPool {

  private static final ConcurrentHashMap<Class<? extends IRepeatingGroup>, ConcurrentLinkedQueue<IRepeatingGroup>> POOLS =
      new ConcurrentHashMap<>();

  private RepeatingGroupPool() {
    // Utility class
  }

  /**
   * Acquires an {@link IRepeatingGroup} from the pool for the specified class.
   *
   * @param clazz the repeating group class
   * @param <T>   the type of the repeating group
   * @return an {@link IRepeatingGroup} instance, or null if the pool is empty
   */
  @SuppressWarnings("unchecked")
  public static <T extends IRepeatingGroup> T acquire(Class<T> clazz) {
    if (clazz == null) {
      return null;
    }
    ConcurrentLinkedQueue<IRepeatingGroup> pool = POOLS.get(clazz);
    if (pool == null) {
      return null;
    }
    T rg = (T) pool.poll();
    if (rg != null) {
      rg.reset();
    }
    return rg;
  }

  /**
   * Releases an {@link IRepeatingGroup} back to the pool.
   *
   * @param rg the instance to release
   */
  public static void release(IRepeatingGroup rg) {
    if (rg != null) {
      POOLS.computeIfAbsent(rg.getClass(), k -> new ConcurrentLinkedQueue<>())
          .offer(rg);
    }
  }

  /**
   * Returns the current size of the pool for the specified class.
   *
   * @param clazz the repeating group class
   * @return the pool size
   */
  public static int size(Class<? extends IRepeatingGroup> clazz) {
    ConcurrentLinkedQueue<IRepeatingGroup> pool = POOLS.get(clazz);
    return pool != null ? pool.size() : 0;
  }
}
