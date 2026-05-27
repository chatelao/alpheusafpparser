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
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.nio.ByteBuffer;
import org.junit.jupiter.api.Test;

public class DirectBufferOutputStreamTest {

    @Test
    public void testWriteByteBuffer() throws IOException {
        try (DirectBufferOutputStream dbos = new DirectBufferOutputStream(16)) {
            ByteBuffer src = ByteBuffer.wrap("Hello World".getBytes());
            dbos.write(src);

            ByteBuffer result = dbos.getBufferAndDetach();
            byte[] bytes = new byte[result.remaining()];
            result.get(bytes);
            assertArrayEquals("Hello World".getBytes(), bytes);
            DirectBufferPool.release(result);
        }
    }

    @Test
    public void testWriteByteBufferLarge() throws IOException {
        // Initial capacity 16, write more than that to trigger resize
        try (DirectBufferOutputStream dbos = new DirectBufferOutputStream(16)) {
            byte[] largeData = new byte[100];
            for (int i = 0; i < 100; i++) largeData[i] = (byte) i;

            ByteBuffer src = ByteBuffer.wrap(largeData);
            dbos.write(src);

            ByteBuffer result = dbos.getBufferAndDetach();
            assertEquals(100, result.remaining());
            byte[] bytes = new byte[result.remaining()];
            result.get(bytes);
            assertArrayEquals(largeData, bytes);
            DirectBufferPool.release(result);
        }
    }

    @Test
    public void testWriteByteBufferPartial() throws IOException {
        try (DirectBufferOutputStream dbos = new DirectBufferOutputStream(16)) {
            ByteBuffer src = ByteBuffer.wrap("0123456789ABCDEF".getBytes());
            src.position(5);
            src.limit(10); // "56789"

            dbos.write(src);

            ByteBuffer result = dbos.getBufferAndDetach();
            assertEquals(5, result.remaining());
            byte[] bytes = new byte[result.remaining()];
            result.get(bytes);
            assertArrayEquals("56789".getBytes(), bytes);
            DirectBufferPool.release(result);
        }
    }
}
