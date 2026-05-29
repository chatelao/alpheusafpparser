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

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.nio.ByteBuffer;
import java.nio.MappedByteBuffer;
import java.security.AccessController;
import java.security.PrivilegedAction;

/**
 * Utility for explicitly unmapping {@link MappedByteBuffer}s.
 * This is necessary on some operating systems (like Windows) to allow file operations
 * on the mapped file before the JVM's garbage collector has reclaimed the buffer,
 * and to prevent virtual address space exhaustion.
 */
public final class MMapUtil {

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
    if (buffer == null || !buffer.isDirect()) {
      return;
    }

    try {
      AccessController.doPrivileged((PrivilegedAction<Void>) () -> {
        try {
          unmapInternal(buffer);
        } catch (Exception e) {
          // Ignore, we tried our best
        }
        return null;
      });
    } catch (Exception e) {
      // Fallback: just let GC handle it
    }
  }

  private static void unmapInternal(ByteBuffer buffer) throws Exception {
    // Standard approach for Java 9+
    try {
      Class<?> unsafeClass = Class.forName("sun.misc.Unsafe");
      Field unsafeField = unsafeClass.getDeclaredField("theUnsafe");
      unsafeField.setAccessible(true);
      Object unsafe = unsafeField.get(null);
      Method invokeCleanerMethod = unsafeClass.getMethod("invokeCleaner", ByteBuffer.class);
      invokeCleanerMethod.invoke(unsafe, buffer);
    } catch (Exception e) {
      // Fallback for Java 8
      Method cleanerMethod = buffer.getClass().getMethod("cleaner");
      cleanerMethod.setAccessible(true);
      Object cleaner = cleanerMethod.invoke(buffer);
      if (cleaner != null) {
        Method cleanMethod = cleaner.getClass().getMethod("clean");
        cleanMethod.setAccessible(true);
        cleanMethod.invoke(cleaner);
      }
    }
  }
}
