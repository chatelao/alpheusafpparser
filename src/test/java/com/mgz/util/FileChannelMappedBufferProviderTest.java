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
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

class FileChannelMappedBufferProviderTest {

  @TempDir
  File tempDir;

  @Test
  void testNextSegmentGrowsFile() throws IOException {
    File file = new File(tempDir, "provider_test.bin");
    try (RandomAccessFile raf = new RandomAccessFile(file, "rw")) {
      FileChannel channel = raf.getChannel();
      FileChannelMappedBufferProvider provider = new FileChannelMappedBufferProvider(channel);

      assertEquals(0, channel.size());

      MappedByteBuffer buffer = provider.nextSegment(0, 100);
      assertNotNull(buffer);
      assertEquals(100, buffer.capacity());
      assertEquals(100, channel.size());

      MappedByteBuffer secondBuffer = provider.nextSegment(100, 50);
      assertNotNull(secondBuffer);
      assertEquals(50, secondBuffer.capacity());
      assertEquals(150, channel.size());
    }
  }

  @Test
  void testIntegrationWithStream() throws IOException {
    File file = new File(tempDir, "integration_test.bin");
    try (RandomAccessFile raf = new RandomAccessFile(file, "rw")) {
      FileChannel channel = raf.getChannel();
      FileChannelMappedBufferProvider provider = new FileChannelMappedBufferProvider(channel);

      try (SegmentedMappedBufferOutputStream smbos = new SegmentedMappedBufferOutputStream(provider, 10)) {
        byte[] data = new byte[25];
        for (int i = 0; i < 25; i++) {
          data[i] = (byte) i;
        }
        smbos.write(data);
        assertEquals(25, smbos.getGlobalPosition());
      }

      assertTrue(channel.size() >= 25);
      MappedByteBuffer result = channel.map(FileChannel.MapMode.READ_ONLY, 0, 25);
      for (int i = 0; i < 25; i++) {
        assertEquals((byte) i, result.get());
      }
    }
  }
}
