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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.nio.ByteBuffer;
import org.junit.jupiter.api.Test;

public class DirectBufferPoolTest {

  @Test
  public void testAcquireAndRelease() {
    ByteBuffer b1 = DirectBufferPool.acquire(1024); // Should get 8KB
    assertTrue(b1.isDirect());
    assertEquals(8192, b1.capacity());

    DirectBufferPool.release(b1);

    ByteBuffer b2 = DirectBufferPool.acquire(5000); // Should get the same 8KB
    assertSame(b1, b2);
    assertEquals(8192, b2.capacity());
  }

  @Test
  public void testBucketSelection() {
    ByteBuffer b8k = DirectBufferPool.acquire(8192);
    assertEquals(8192, b8k.capacity());

    ByteBuffer b16k = DirectBufferPool.acquire(8193);
    assertEquals(16384, b16k.capacity());

    DirectBufferPool.release(b8k);
    DirectBufferPool.release(b16k);
  }

  @Test
  public void testOversizedBuffer() {
    int largeSize = 8 * 1024 * 1024; // 8MB
    ByteBuffer large = DirectBufferPool.acquire(largeSize);
    assertEquals(largeSize, large.capacity());
    assertTrue(large.isDirect());

    DirectBufferPool.release(large);
    ByteBuffer large2 = DirectBufferPool.acquire(largeSize);
    assertNotSame(large, large2); // Should not be pooled
  }

  @Test
  public void testNonDirectBuffer() {
    ByteBuffer heap = ByteBuffer.allocate(8192);
    DirectBufferPool.release(heap); // Should be ignored

    ByteBuffer pooled = DirectBufferPool.acquire(8192);
    assertNotSame(heap, pooled);
  }
}
