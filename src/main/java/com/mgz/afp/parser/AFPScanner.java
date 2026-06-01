/*
Copyright 2024 Rudolf Fiala

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

package com.mgz.afp.parser;

import com.mgz.afp.enums.SFTypeID;
import com.mgz.util.UtilBinaryDecoding;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousFileChannel;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * High-performance scanner for identifying Structured Field offsets within a {@link ByteBuffer}
 * or an {@link AsynchronousFileChannel}.
 */
public class AFPScanner {

  private final ByteBuffer buffer;
  private final AsynchronousFileChannel asyncChannel;

  public AFPScanner(ByteBuffer buffer) {
    this.buffer = buffer;
    this.asyncChannel = null;
  }

  public AFPScanner(AsynchronousFileChannel asyncChannel) {
    this.buffer = null;
    this.asyncChannel = asyncChannel;
  }

  public List<Long> scanFor(SFTypeID typeID) {
    if (buffer != null) {
      return scanForBuffer(typeID, 0, buffer.limit());
    } else if (asyncChannel != null) {
      return scanForAsyncChannel(typeID);
    }
    return new ArrayList<>();
  }

  public List<Long> scanForParallel(SFTypeID typeID, int numThreads) {
    if (numThreads <= 1) {
      return scanFor(typeID);
    }

    if (buffer != null) {
      return scanForBufferParallel(typeID, numThreads);
    } else if (asyncChannel != null) {
      return scanForAsyncChannelParallel(typeID, numThreads);
    }
    return new ArrayList<>();
  }

  private List<Long> scanForBuffer(SFTypeID typeID, int start, int end) {
    List<Long> offsets = new ArrayList<>();
    int pos = Math.max(0, start);
    int actualLimit = buffer.limit();
    int searchLimit = Math.min(actualLimit, end);
    boolean synced = (pos == 0);

    while (pos < searchLimit) {
      if ((buffer.get(pos) & 0xFF) == 0x5A) {
        if (pos + 8 >= actualLimit) {
          pos++;
          synced = false;
          continue;
        }

        int sfLength;
        try {
          sfLength = UtilBinaryDecoding.parseInt(buffer, pos + 1, 2);
        } catch (Exception e) {
          pos++;
          synced = false;
          continue;
        }

        if (sfLength < 8) {
          pos++;
          synced = false;
          continue;
        }

        SFTypeID foundType = SFTypeID.parse(buffer, pos + 3);
        if (foundType != SFTypeID.Undefined) {
          if (foundType == typeID) {
            offsets.add((long) pos);
          }

          if (synced) {
            pos += sfLength + 1;
            continue;
          } else {
            // Try to sync: check if there's a 0x5A at the expected next record start
            if (pos + sfLength + 1 < actualLimit && (buffer.get(pos + sfLength + 1) & 0xFF) == 0x5A) {
              synced = true;
              pos += sfLength + 1;
              continue;
            }
          }
        }
      }
      pos++;
      synced = false;
    }
    return offsets;
  }

  private List<Long> scanForAsyncChannel(SFTypeID typeID) {
    List<Long> offsets = new ArrayList<>();
    try {
      long fileSize = asyncChannel.size();
      long currentFilePos = 0;
      ByteBuffer chunk = ByteBuffer.allocateDirect(1024 * 1024);
      int remainingSkip = 0;
      boolean synced = true; // Sequential scan starts at 0, assumed synced

      while (currentFilePos < fileSize) {
        chunk.clear();
        Future<Integer> future = asyncChannel.read(chunk, currentFilePos);
        int bytesRead = future.get();
        if (bytesRead <= 0) {
          break;
        }
        chunk.flip();

        int chunkPos = 0;
        if (remainingSkip > 0) {
          int skipInThisChunk = Math.min(remainingSkip, bytesRead);
          chunkPos += skipInThisChunk;
          remainingSkip -= skipInThisChunk;
          if (remainingSkip == 0) {
            synced = true;
          }
        }

        while (chunkPos < bytesRead) {
          if ((chunk.get(chunkPos) & 0xFF) == 0x5A) {
            if (chunkPos + 8 > bytesRead) {
              break;
            }

            int sfLength;
            try {
              sfLength = UtilBinaryDecoding.parseInt(chunk, chunkPos + 1, 2);
            } catch (Exception e) {
              chunkPos++;
              synced = false;
              continue;
            }

            if (sfLength < 8) {
              chunkPos++;
              synced = false;
              continue;
            }

            SFTypeID foundType = SFTypeID.parse(chunk, chunkPos + 3);
            if (foundType != SFTypeID.Undefined) {
              if (foundType == typeID) {
                offsets.add(currentFilePos + chunkPos);
              }

              int skipBytes = sfLength + 1;
              int availableInChunk = bytesRead - chunkPos;

              if (synced || (skipBytes < availableInChunk && (chunk.get(chunkPos + skipBytes) & 0xFF) == 0x5A)) {
                synced = true;
                if (skipBytes <= availableInChunk) {
                  chunkPos += skipBytes;
                } else {
                  remainingSkip = skipBytes - availableInChunk;
                  chunkPos = bytesRead;
                  synced = false; // Need to verify at start of next chunk
                }
                continue;
              }
            }
          }
          chunkPos++;
          synced = false;
        }
        currentFilePos += chunkPos;
      }
    } catch (Exception expected) {
      // ignored
    }
    return offsets;
  }

  private List<Long> scanForBufferParallel(SFTypeID typeID, int numThreads) {
    List<Long> allOffsets = Collections.synchronizedList(new ArrayList<>());
    ExecutorService executor = Executors.newFixedThreadPool(numThreads);
    int limit = buffer.limit();
    int chunkSize = limit / numThreads;

    try {
      List<Future<Void>> futures = new ArrayList<>();
      for (int i = 0; i < numThreads; i++) {
        final int start = i * chunkSize;
        final int end = (i == numThreads - 1) ? limit : Math.min(limit, (i + 1) * chunkSize + 32768);
        futures.add(executor.submit(() -> {
          allOffsets.addAll(scanForBuffer(typeID, start, end));
          return null;
        }));
      }
      for (Future<Void> future : futures) {
        future.get();
      }
    } catch (Exception expected) {
      // ignored
    } finally {
      executor.shutdown();
    }

    List<Long> result = new ArrayList<>(allOffsets);
    Collections.sort(result);
    for (int i = 0; i < result.size() - 1; i++) {
      if (result.get(i).equals(result.get(i + 1))) {
        result.remove(i + 1);
        i--;
      }
    }
    return result;
  }

  private List<Long> scanForAsyncChannelParallel(SFTypeID typeID, int numThreads) {
    List<Long> allOffsets = Collections.synchronizedList(new ArrayList<>());
    ExecutorService executor = Executors.newFixedThreadPool(numThreads);

    try {
      long fileSize = asyncChannel.size();
      long chunkSize = fileSize / numThreads;

      List<Future<Void>> futures = new ArrayList<>();
      for (int i = 0; i < numThreads; i++) {
        final long start = i * chunkSize;
        final long end = (i == numThreads - 1) ? fileSize : Math.min(fileSize, (i + 1) * chunkSize + 32768);
        futures.add(executor.submit(() -> {
          allOffsets.addAll(scanForAsyncChannelPart(typeID, start, end));
          return null;
        }));
      }
      for (Future<Void> future : futures) {
        future.get();
      }
    } catch (Exception expected) {
      // ignored
    } finally {
      executor.shutdown();
    }

    List<Long> result = new ArrayList<>(allOffsets);
    Collections.sort(result);
    for (int i = 0; i < result.size() - 1; i++) {
      if (result.get(i).equals(result.get(i + 1))) {
        result.remove(i + 1);
        i--;
      }
    }
    return result;
  }

  private List<Long> scanForAsyncChannelPart(SFTypeID typeID, long start, long end) {
    List<Long> offsets = new ArrayList<>();
    try {
      long currentFilePos = start;
      long fileSize = asyncChannel.size();
      ByteBuffer chunk = ByteBuffer.allocateDirect(1024 * 1024);
      int remainingSkip = 0;
      boolean synced = (start == 0);

      while (currentFilePos < end) {
        chunk.clear();
        int toRead = (int) Math.min(chunk.capacity(), end - currentFilePos);
        if (toRead <= 0) break;

        chunk.limit(toRead);
        Future<Integer> future = asyncChannel.read(chunk, currentFilePos);
        int bytesRead = future.get();
        if (bytesRead <= 0) {
          break;
        }
        chunk.flip();

        int chunkPos = 0;
        if (remainingSkip > 0) {
          int skipInThisChunk = Math.min(remainingSkip, bytesRead);
          chunkPos += skipInThisChunk;
          remainingSkip -= skipInThisChunk;
          if (remainingSkip == 0) {
            synced = true;
          }
        }

        while (chunkPos < bytesRead) {
          if ((chunk.get(chunkPos) & 0xFF) == 0x5A) {
            if (chunkPos + 8 > bytesRead) {
              break;
            }
            int sfLength;
            try {
              sfLength = UtilBinaryDecoding.parseInt(chunk, chunkPos + 1, 2);
            } catch (Exception e) {
              chunkPos++;
              synced = false;
              continue;
            }
            if (sfLength < 8) {
              chunkPos++;
              synced = false;
              continue;
            }
            SFTypeID foundType = SFTypeID.parse(chunk, chunkPos + 3);
            if (foundType != SFTypeID.Undefined) {
              if (foundType == typeID) {
                offsets.add(currentFilePos + chunkPos);
              }

              int skipBytes = sfLength + 1;
              int availableInChunk = bytesRead - chunkPos;

              if (synced || (skipBytes < availableInChunk && (chunk.get(chunkPos + skipBytes) & 0xFF) == 0x5A)) {
                synced = true;
                if (skipBytes <= availableInChunk) {
                  chunkPos += skipBytes;
                } else {
                  remainingSkip = skipBytes - availableInChunk;
                  chunkPos = bytesRead;
                  synced = false; // Need to verify at start of next chunk
                }
                continue;
              }
            }
          }
          chunkPos++;
          synced = false;
        }
        currentFilePos += chunkPos;
      }
    } catch (Exception expected) {
      // ignored
    }
    return offsets;
  }

  public List<Long> findPageBoundaries() {
    return scanFor(SFTypeID.BPG_BeginPage);
  }

  public List<Long> findPageBoundariesParallel() {
    return scanForParallel(SFTypeID.BPG_BeginPage, Runtime.getRuntime().availableProcessors());
  }
}
