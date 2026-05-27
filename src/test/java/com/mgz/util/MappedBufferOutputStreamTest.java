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
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.Files;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

class MappedBufferOutputStreamTest {

  @TempDir
  File tempDir;

  @Test
  void testBasicWrite() throws IOException {
    File file = new File(tempDir, "test.bin");
    try (RandomAccessFile raf = new RandomAccessFile(file, "rw")) {
      raf.setLength(10);
      MappedByteBuffer mbb = raf.getChannel().map(FileChannel.MapMode.READ_WRITE, 0, 10);
      try (MappedBufferOutputStream mbos = new MappedBufferOutputStream(mbb)) {
        mbos.write(1);
        mbos.write(2);
        mbos.write(new byte[]{3, 4, 5});
      }

      mbb.flip();
      assertEquals(1, mbb.get());
      assertEquals(2, mbb.get());
      assertEquals(3, mbb.get());
      assertEquals(4, mbb.get());
      assertEquals(5, mbb.get());
    }
  }

  @Test
  void testOverflow() throws IOException {
    File file = new File(tempDir, "overflow.bin");
    try (RandomAccessFile raf = new RandomAccessFile(file, "rw")) {
      raf.setLength(2);
      MappedByteBuffer mbb = raf.getChannel().map(FileChannel.MapMode.READ_WRITE, 0, 2);
      try (MappedBufferOutputStream mbos = new MappedBufferOutputStream(mbb)) {
        mbos.write(1);
        mbos.write(2);
        assertThrows(IOException.class, () -> mbos.write(3));
      }
    }
  }

  @Test
  void testArrayOverflow() throws IOException {
    File file = new File(tempDir, "array_overflow.bin");
    try (RandomAccessFile raf = new RandomAccessFile(file, "rw")) {
      raf.setLength(3);
      MappedByteBuffer mbb = raf.getChannel().map(FileChannel.MapMode.READ_WRITE, 0, 3);
      try (MappedBufferOutputStream mbos = new MappedBufferOutputStream(mbb)) {
        assertThrows(IOException.class, () -> mbos.write(new byte[]{1, 2, 3, 4}));
      }
    }
  }
}
