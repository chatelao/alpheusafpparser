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

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousFileChannel;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.Random;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

class AsynchronousBufferOutputStreamTest {

  @TempDir
  Path tempDir;

  @Test
  void testBasicWrite() throws IOException {
    Path file = tempDir.resolve("basic.txt");
    try (AsynchronousFileChannel channel = AsynchronousFileChannel.open(file,
        StandardOpenOption.CREATE, StandardOpenOption.WRITE, StandardOpenOption.READ)) {

      try (AsynchronousBufferOutputStream out = new AsynchronousBufferOutputStream(channel, 1024)) {
        out.write("Hello World".getBytes());
      }

      byte[] result = Files.readAllBytes(file);
      assertArrayEquals("Hello World".getBytes(), result);
    }
  }

  @Test
  void testLargeWriteTriggeringFlushes() throws IOException {
    Path file = tempDir.resolve("large.bin");
    int bufferSize = 4096;
    int dataSize = bufferSize * 10 + 123;
    byte[] data = new byte[dataSize];
    new Random(42).nextBytes(data);

    try (AsynchronousFileChannel channel = AsynchronousFileChannel.open(file,
        StandardOpenOption.CREATE, StandardOpenOption.WRITE, StandardOpenOption.READ)) {

      try (AsynchronousBufferOutputStream out = new AsynchronousBufferOutputStream(channel, bufferSize)) {
        out.write(data);
      }

      byte[] result = Files.readAllBytes(file);
      assertEquals(dataSize, result.length);
      assertArrayEquals(data, result);
    }
  }

  @Test
  void testBackPressure() throws IOException {
    // Test with 1 buffer to ensure serialization waits for completion
    Path file = tempDir.resolve("backpressure.bin");
    int bufferSize = 1024;
    int numBuffers = 1;
    byte[] chunk1 = new byte[bufferSize];
    byte[] chunk2 = new byte[bufferSize];
    new Random(1).nextBytes(chunk1);
    new Random(2).nextBytes(chunk2);

    try (AsynchronousFileChannel channel = AsynchronousFileChannel.open(file,
        StandardOpenOption.CREATE, StandardOpenOption.WRITE, StandardOpenOption.READ)) {

      try (AsynchronousBufferOutputStream out = new AsynchronousBufferOutputStream(channel, bufferSize, numBuffers)) {
        out.write(chunk1); // fills buffer, flushes immediately
        out.write(chunk2); // fills second buffer, flushes immediately
      }

      byte[] result = Files.readAllBytes(file);
      assertEquals(bufferSize * 2, result.length);

      ByteBuffer expected = ByteBuffer.allocate(bufferSize * 2);
      expected.put(chunk1).put(chunk2);
      assertArrayEquals(expected.array(), result);
    }
  }

  @Test
  void testFlushAndClose() throws IOException {
    Path file = tempDir.resolve("flush.txt");
    try (AsynchronousFileChannel channel = AsynchronousFileChannel.open(file,
        StandardOpenOption.CREATE, StandardOpenOption.WRITE, StandardOpenOption.READ)) {

      try (AsynchronousBufferOutputStream out = new AsynchronousBufferOutputStream(channel, 1024)) {
        out.write("Part 1".getBytes());
        out.flush();
        // At this point it should be writing/written
        out.write("Part 2".getBytes());
      }

      byte[] result = Files.readAllBytes(file);
      assertArrayEquals("Part 1Part 2".getBytes(), result);
    }
  }
}
