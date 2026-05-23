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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertSame;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;

@Execution(ExecutionMode.SAME_THREAD)
public class SfiPoolTest {

  @Test
  public void testAcquireAndRelease() {
    int initialSize = SfiPool.size();

    StructuredFieldIntroducer sfi1 = SfiPool.acquire();
    assertNotNull(sfi1);
    assertEquals(initialSize, SfiPool.size()); // Should be same if pool was empty or 1 less if not

    sfi1.setSFLength(100);
    SfiPool.release(sfi1);
    assertEquals(initialSize + 1, SfiPool.size());

    StructuredFieldIntroducer sfi2 = SfiPool.acquire();
    assertSame(sfi1, sfi2);
    assertEquals(0, sfi2.getSFLength()); // Should be reset
    assertEquals(initialSize, SfiPool.size());
  }

  @Test
  public void testMultipleAcquire() {
    StructuredFieldIntroducer sfi1 = SfiPool.acquire();
    StructuredFieldIntroducer sfi2 = SfiPool.acquire();

    assertNotSame(sfi1, sfi2);

    SfiPool.release(sfi1);
    SfiPool.release(sfi2);

    StructuredFieldIntroducer sfi3 = SfiPool.acquire();
    StructuredFieldIntroducer sfi4 = SfiPool.acquire();

    // ConcurrentLinkedQueue is FIFO
    assertSame(sfi1, sfi3);
    assertSame(sfi2, sfi4);
  }
}
