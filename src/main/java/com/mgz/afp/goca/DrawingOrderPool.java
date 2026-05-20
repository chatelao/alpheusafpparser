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

package com.mgz.afp.goca;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * A pool for {@link GAD_DrawingOrder} objects to reduce garbage collection overhead.
 */
public final class DrawingOrderPool {

  private DrawingOrderPool() {
    // Utility class
  }

  /**
   * Acquires a {@link GAD_DrawingOrder} from the pool for the specified type.
   *
   * @param type the drawing order type code
   * @return a {@link GAD_DrawingOrder} instance, or null if the pool is empty
   */
  public static GAD_DrawingOrder acquire(short type) {
    return acquire(type, (short) -1);
  }

  /**
   * Acquires a {@link GAD_DrawingOrder} from the pool for the specified type and qualifier.
   *
   * @param type      the drawing order type code
   * @param qualifier the qualifier for extended orders, or -1
   * @return a {@link GAD_DrawingOrder} instance, or null if the pool is empty
   */
  public static GAD_DrawingOrder acquire(short type, short qualifier) {
    long key = ((long) type << 16) | (qualifier & 0xFFFFL);
    return acquireInternal(key);
  }

  private static final ConcurrentHashMap<Long, ConcurrentLinkedQueue<GAD_DrawingOrder>> POOLS_LONG =
      new ConcurrentHashMap<>();

  private static GAD_DrawingOrder acquireInternal(long key) {
    ConcurrentLinkedQueue<GAD_DrawingOrder> pool = POOLS_LONG.get(key);
    if (pool == null) {
      return null;
    }
    GAD_DrawingOrder order = pool.poll();
    if (order != null) {
      order.reset();
    }
    return order;
  }

  /**
   * Releases a {@link GAD_DrawingOrder} back to the pool.
   *
   * @param order the instance to release
   */
  public static void release(GAD_DrawingOrder order) {
    if (order != null) {
      short type = order.getDrawingOrderType();
      short qualifier = -1;
      if (type == (short) 0xFE) {
        if (order instanceof GAD_DrawingOrder.GLGD_LinearGradient) {
          qualifier = 0xDC;
        } else if (order instanceof GAD_DrawingOrder.GRGD_RadialGradient) {
          qualifier = 0xDD;
        } else if (order instanceof GAD_DrawingOrder.GEXO_ExtendedOrder e) {
          qualifier = e.getQualifier();
        }
      }
      long key = ((long) type << 16) | (qualifier & 0xFFFFL);
      POOLS_LONG.computeIfAbsent(key, k -> new ConcurrentLinkedQueue<>())
          .offer(order);
    }
  }
}
