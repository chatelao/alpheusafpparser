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

package com.mgz.afp.ptoca.controlSequence;

import com.mgz.afp.ptoca.controlSequence.PTOCAControlSequence.ControlSequenceFunctionType;
import java.util.EnumMap;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * A pool for {@link PTOCAControlSequence} objects to reduce garbage collection overhead.
 */
public final class ControlSequencePool {

  private static final EnumMap<ControlSequenceFunctionType, ConcurrentLinkedQueue<PTOCAControlSequence>> POOLS =
      new EnumMap<>(ControlSequenceFunctionType.class);

  static {
    for (ControlSequenceFunctionType type : ControlSequenceFunctionType.values()) {
      POOLS.put(type, new ConcurrentLinkedQueue<>());
    }
  }

  private ControlSequencePool() {
    // Utility class
  }

  /**
   * Acquires a {@link PTOCAControlSequence} from the pool for the specified function type.
   *
   * @param type the control sequence function type
   * @return a {@link PTOCAControlSequence} instance, or null if the pool is empty
   */
  public static PTOCAControlSequence acquire(ControlSequenceFunctionType type) {
    if (type == null) {
      return null;
    }
    ConcurrentLinkedQueue<PTOCAControlSequence> pool = POOLS.get(type);
    if (pool == null) {
      return null;
    }
    PTOCAControlSequence cs = pool.poll();
    if (cs != null) {
      cs.reset();
    }
    return cs;
  }

  /**
   * Releases a {@link PTOCAControlSequence} back to the pool.
   *
   * @param cs the instance to release
   */
  public static void release(PTOCAControlSequence cs) {
    if (cs != null && cs.getCsi() != null && cs.getCsi().getControlSequenceFunctionType() != null) {
      ConcurrentLinkedQueue<PTOCAControlSequence> pool = POOLS.get(cs.getCsi().getControlSequenceFunctionType());
      if (pool != null) {
        pool.offer(cs);
      }
    }
  }
}
