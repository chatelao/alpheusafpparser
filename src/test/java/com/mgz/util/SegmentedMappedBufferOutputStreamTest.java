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
import java.nio.ByteBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

class SegmentedMappedBufferOutputStreamTest {

  @TempDir
  File tempDir;

  @Test
  void testBasicWrite() throws IOException {
    File file = new File(tempDir, "segmented_test.bin");
    try (RandomAccessFile raf = new RandomAccessFile(file, "rw")) {
      raf.setLength(100);
      FileChannel channel = raf.getChannel();

      MappedBufferProvider provider = (pos, size) -> channel.map(FileChannel.MapMode.READ_WRITE, pos, size);

      try (SegmentedMappedBufferOutputStream smbos = new SegmentedMappedBufferOutputStream(provider, 10)) {
        smbos.write(1);
        smbos.write(new byte[]{2, 3, 4, 5});
        smbos.write(ByteBuffer.wrap(new byte[]{6, 7}));
        assertEquals(7, smbos.getGlobalPosition());
      }

      ByteBuffer result = channel.map(FileChannel.MapMode.READ_ONLY, 0, 7);
      assertEquals(1, result.get());
      assertEquals(2, result.get());
      assertEquals(3, result.get());
      assertEquals(4, result.get());
      assertEquals(5, result.get());
      assertEquals(6, result.get());
      assertEquals(7, result.get());
    }
  }

  @Test
  void testSegmentSwitching() throws IOException {
    File file = new File(tempDir, "switching_test.bin");
    try (RandomAccessFile raf = new RandomAccessFile(file, "rw")) {
      raf.setLength(20);
      FileChannel channel = raf.getChannel();

      List<MappedByteBuffer> providedBuffers = new ArrayList<>();
      MappedBufferProvider provider = (pos, size) -> {
        MappedByteBuffer mbb = channel.map(FileChannel.MapMode.READ_WRITE, pos, size);
        providedBuffers.add(mbb);
        return mbb;
      };

      try (SegmentedMappedBufferOutputStream smbos = new SegmentedMappedBufferOutputStream(provider, 5)) {
        // Write 12 bytes, should trigger 3 segments (5, 5, 2)
        byte[] data = new byte[12];
        for (int i = 0; i < 12; i++) data[i] = (byte) (i + 1);
        smbos.write(data);
        assertEquals(12, smbos.getGlobalPosition());
      }

      assertEquals(3, providedBuffers.size());
      assertEquals(5, providedBuffers.get(0).capacity());
      assertEquals(5, providedBuffers.get(1).capacity());
      assertEquals(5, providedBuffers.get(2).capacity());

      ByteBuffer result = channel.map(FileChannel.MapMode.READ_ONLY, 0, 12);
      for (int i = 0; i < 12; i++) {
        assertEquals((byte) (i + 1), result.get());
      }
    }
  }

  @Test
  void testLargeWriteCrossingMultipleBoundaries() throws IOException {
    File file = new File(tempDir, "large_crossing_test.bin");
    try (RandomAccessFile raf = new RandomAccessFile(file, "rw")) {
      raf.setLength(100);
      FileChannel channel = raf.getChannel();

      MappedBufferProvider provider = (pos, size) -> channel.map(FileChannel.MapMode.READ_WRITE, pos, size);

      try (SegmentedMappedBufferOutputStream smbos = new SegmentedMappedBufferOutputStream(provider, 10)) {
        // Write 25 bytes, crossing 2 boundaries (10, 10, 5)
        byte[] data = new byte[25];
        for (int i = 0; i < 25; i++) data[i] = (byte) i;
        smbos.write(data);
        assertEquals(25, smbos.getGlobalPosition());
      }

      ByteBuffer result = channel.map(FileChannel.MapMode.READ_ONLY, 0, 25);
      for (int i = 0; i < 25; i++) {
        assertEquals((byte) i, result.get());
      }
    }
  }

  @Test
  void testOverflow() throws IOException {
    File file = new File(tempDir, "overflow_test.bin");
    try (RandomAccessFile raf = new RandomAccessFile(file, "rw")) {
      raf.setLength(10);
      FileChannel channel = raf.getChannel();

      MappedBufferProvider provider = (pos, size) -> {
        if (pos >= 10) return null;
        return channel.map(FileChannel.MapMode.READ_WRITE, pos, Math.min(size, (int) (10 - pos)));
      };

      try (SegmentedMappedBufferOutputStream smbos = new SegmentedMappedBufferOutputStream(provider, 5)) {
        smbos.write(new byte[10]);
        assertThrows(IOException.class, () -> smbos.write(1));
      }
    }
  }
}
