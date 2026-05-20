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

package com.mgz.afp.triplets;

import com.mgz.afp.triplets.Triplet.TripletID;
import java.util.EnumMap;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * A pool for {@link Triplet} objects to reduce garbage collection overhead.
 */
public final class TripletPool {

  private static final EnumMap<TripletID, ConcurrentLinkedQueue<Triplet>> POOLS =
      new EnumMap<>(TripletID.class);

  static {
    for (TripletID id : TripletID.values()) {
      POOLS.put(id, new ConcurrentLinkedQueue<>());
    }
  }

  private TripletPool() {
    // Utility class
  }

  /**
   * Acquires a {@link Triplet} from the pool for the specified ID.
   *
   * @param tid the triplet ID
   * @return a {@link Triplet} instance, or null if the pool is empty
   */
  public static Triplet acquire(TripletID tid) {
    if (tid == null) {
      return null;
    }
    ConcurrentLinkedQueue<Triplet> pool = POOLS.get(tid);
    if (pool == null) {
      return null;
    }
    Triplet triplet = pool.poll();
    if (triplet != null) {
      triplet.reset();
      triplet.setTripletID(tid);
    }
    return triplet;
  }

  /**
   * Releases a {@link Triplet} back to the pool.
   *
   * @param triplet the instance to release
   */
  public static void release(Triplet triplet) {
    if (triplet != null && triplet.getTripletID() != null) {
      ConcurrentLinkedQueue<Triplet> pool = POOLS.get(triplet.getTripletID());
      if (pool != null) {
        pool.offer(triplet);
      }
    }
  }
}
