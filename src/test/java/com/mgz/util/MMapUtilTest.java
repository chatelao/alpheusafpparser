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

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import org.junit.jupiter.api.Test;
import java.nio.ByteBuffer;

class MMapUtilTest {

  @Test
  void testUnmapNull() {
    assertDoesNotThrow(() -> MMapUtil.unmap(null));
  }

  @Test
  void testUnmapNonDirect() {
    ByteBuffer buffer = ByteBuffer.allocate(10);
    assertDoesNotThrow(() -> MMapUtil.unmap(buffer));
  }

  @Test
  void testUnmapDirect() {
    ByteBuffer buffer = ByteBuffer.allocateDirect(10);
    // Note: unmapping a direct buffer that is NOT a MappedByteBuffer
    // might or might not do anything depending on the JVM version,
    // but it should not crash the test.
    assertDoesNotThrow(() -> MMapUtil.unmap(buffer));
  }
}
