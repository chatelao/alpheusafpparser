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

import java.io.IOException;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;

/**
 * A {@link MappedBufferProvider} that uses a {@link FileChannel} to map segments.
 * It automatically grows the file if a segment is requested beyond the current file size.
 */
public class FileChannelMappedBufferProvider implements MappedBufferProvider {

  private final FileChannel channel;

  /**
   * Constructs a FileChannelMappedBufferProvider.
   *
   * @param channel the file channel to use for mapping
   */
  public FileChannelMappedBufferProvider(FileChannel channel) {
    this.channel = channel;
  }

  @Override
  public MappedByteBuffer nextSegment(long position, int requestedSize) throws IOException {
    long minSize = position + requestedSize;
    if (channel.size() < minSize) {
      // Force file growth by writing a zero byte at the end if necessary,
      // though map() on some OSes might grow it, it's safer to be explicit.
      // However, we use READ_WRITE mode which should work if the file is large enough.
      // We'll grow it explicitly here to ensure mapping succeeds.
      channel.position(minSize - 1);
      channel.write(java.nio.ByteBuffer.wrap(new byte[]{0}));
    }
    return channel.map(FileChannel.MapMode.READ_WRITE, position, requestedSize);
  }
}
