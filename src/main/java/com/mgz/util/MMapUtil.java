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

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.reflect.Field;
import java.nio.ByteBuffer;
import java.nio.MappedByteBuffer;

/**
 * Utility for explicitly unmapping {@link MappedByteBuffer}s.
 * This is necessary on some operating systems (like Windows) to allow file operations
 * on the mapped file before the JVM's garbage collector has reclaimed the buffer,
 * and to prevent virtual address space exhaustion.
 */
public final class MMapUtil {

  private static final Object UNSAFE;
  private static final MethodHandle INVOKE_CLEANER;

  static {
    Object unsafe = null;
    MethodHandle invokeCleaner = null;
    try {
      Class<?> unsafeClass = Class.forName("sun.misc.Unsafe");
      Field unsafeField = unsafeClass.getDeclaredField("theUnsafe");
      unsafeField.setAccessible(true);
      unsafe = unsafeField.get(null);
      invokeCleaner = MethodHandles.publicLookup().findVirtual(
          unsafeClass, "invokeCleaner", MethodType.methodType(void.class, ByteBuffer.class));
    } catch (Throwable t) {
      // Fallback: let GC handle unmapping
    }
    UNSAFE = unsafe;
    INVOKE_CLEANER = invokeCleaner;
  }

  private MMapUtil() {
    // Utility class
  }

  /**
   * Explicitly unmaps a {@link MappedByteBuffer}.
   * This uses internal JVM APIs and should be used with caution.
   * After calling this, any access to the buffer will result in a JVM crash (Segmentation Fault).
   *
   * @param buffer the buffer to unmap
   */
  public static void unmap(ByteBuffer buffer) {
    if (buffer == null || !buffer.isDirect() || UNSAFE == null || INVOKE_CLEANER == null) {
      return;
    }

    try {
      INVOKE_CLEANER.invoke(UNSAFE, buffer);
    } catch (Throwable t) {
      // Fallback: just let GC handle it
    }
  }
}
