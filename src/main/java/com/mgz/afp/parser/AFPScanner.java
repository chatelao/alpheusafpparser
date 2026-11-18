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

import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousFileChannel;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * High-performance scanner for identifying Structured Field offsets within a {@link ByteBuffer}
 * or an {@link AsynchronousFileChannel}.
 */
public class AFPScanner {

  private static final int PARALLEL_THRESHOLD_BYTES = 64 * 1024;
  private static final int CHUNK_OVERLAP_BYTES = 32768;
  private static final int ASYNC_BUFFER_BYTES = 1024 * 1024;

  /**
   * Result of a single-pass scan containing all structured field offsets and page boundary
   * ({@code BPG}) offsets in ascending order, plus the SF index of each page start.
   */
  public record ScanIndex(long[] sfOffsets, long[] pageOffsets, int[] pageSfIndices) {
    /** Returns {@code true} if no pages were found. */
    public boolean isEmpty() {
      return pageOffsets.length == 0;
    }
  }

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
    if (typeID == null) {
      return new ArrayList<>();
    }
    if (buffer != null) {
      return scanForBuffer(typeID, 0, buffer.limit());
    } else if (asyncChannel != null) {
      return scanForAsyncChannel(typeID);
    }
    return new ArrayList<>();
  }

  public List<Long> scanForParallel(SFTypeID typeID, int numThreads) {
    if (typeID == null) {
      return new ArrayList<>();
    }
    if (numThreads <= 1) {
      return scanFor(typeID);
    }

    if (buffer != null) {
      if (buffer.limit() < PARALLEL_THRESHOLD_BYTES) {
        return scanFor(typeID);
      }
      return scanForBufferParallel(typeID, numThreads);
    } else if (asyncChannel != null) {
      try {
        if (asyncChannel.size() < PARALLEL_THRESHOLD_BYTES) {
          return scanFor(typeID);
        }
      } catch (Exception ignored) {
        return new ArrayList<>();
      }
      return scanForAsyncChannelParallel(typeID, numThreads);
    }
    return new ArrayList<>();
  }

  /**
   * Performs a single-pass scan collecting both all structured field offsets and {@code BPG}
   * page boundary offsets.
   */
  public ScanIndex scanIndex() {
    if (buffer != null) {
      return scanIndexBufferSequential();
    } else if (asyncChannel != null) {
      return scanIndexAsyncChannelSequential();
    }
    return new ScanIndex(new long[0], new long[0], new int[0]);
  }

  /**
   * Performs a parallel single-pass scan collecting both all structured field offsets and
   * {@code BPG} page boundary offsets using verified frame-chain stitching.
   */
  public ScanIndex scanIndexParallel(int numThreads) {
    int threads = numThreads > 0 ? numThreads : Runtime.getRuntime().availableProcessors();
    if (buffer != null) {
      if (threads <= 1 || buffer.limit() < PARALLEL_THRESHOLD_BYTES) {
        return scanIndexBufferSequential();
      }
      return scanIndexBufferParallel(threads);
    } else if (asyncChannel != null) {
      return scanIndexAsyncChannelSequential();
    }
    return new ScanIndex(new long[0], new long[0], new int[0]);
  }

  public ScanIndex scanIndexParallel() {
    return scanIndexParallel(Runtime.getRuntime().availableProcessors());
  }

  private ScanIndex scanIndexBufferSequential() {
    int limit = buffer.limit();
    LongArrayBuilder sfOffsets = new LongArrayBuilder(Math.max(16, limit / 64));
    LongArrayBuilder pageOffsets = new LongArrayBuilder(64);
    IntArrayBuilder pageSfIndices = new IntArrayBuilder(64);
    scanBufferSyncedRange(buffer, limit, 0, limit, sfOffsets, pageOffsets, pageSfIndices);
    return new ScanIndex(sfOffsets.toArray(), pageOffsets.toArray(), pageSfIndices.toArray());
  }

  private ScanIndex scanIndexBufferParallel(int numThreads) {
    int limit = buffer.limit();
    int workers = Math.min(numThreads, Math.max(1, limit / (PARALLEL_THRESHOLD_BYTES / 2)));
    if (workers <= 1) {
      return scanIndexBufferSequential();
    }

    int chunkSize = limit / workers;
    ExecutorService executor = Executors.newFixedThreadPool(workers);
    List<Future<ChunkScanResult>> futures = new ArrayList<>(workers);
    try {
      for (int i = 0; i < workers; i++) {
        final int idx = i;
        final int start = i * chunkSize;
        final int end = (i == workers - 1) ? limit : (i + 1) * chunkSize;
        futures.add(executor.submit(() -> scanBufferChunk(buffer, limit, start, end, idx == 0)));
      }

      ChunkScanResult[] chunks = new ChunkScanResult[workers];
      for (int i = 0; i < workers; i++) {
        chunks[i] = futures.get(i).get();
      }
      return stitchChunks(buffer, limit, chunks, chunkSize, workers);
    } catch (Exception ignored) {
      return scanIndexBufferSequential();
    } finally {
      executor.shutdown();
    }
  }

  private static ChunkScanResult scanBufferChunk(
      ByteBuffer buf, int limit, int start, int chunkEnd, boolean isFirstChunk) {
    LongArrayBuilder sfOffsets = new LongArrayBuilder(Math.max(16, (chunkEnd - start) / 64));
    LongArrayBuilder pageOffsets = new LongArrayBuilder(16);
    IntArrayBuilder pageSfIndices = new IntArrayBuilder(16);

    int pos;
    if (isFirstChunk) {
      pos = 0;
    } else {
      pos = findVerifiedSyncPoint(buf, limit, start, chunkEnd);
      if (pos < 0) {
        return new ChunkScanResult(start, chunkEnd, -1, -1,
            sfOffsets.toArray(), pageOffsets.toArray(), pageSfIndices.toArray());
      }
    }

    int endPos = scanBufferSyncedRange(
        buf, limit, pos, chunkEnd, sfOffsets, pageOffsets, pageSfIndices);
    long firstPos = sfOffsets.size() > 0 ? sfOffsets.get(0) : -1L;
    return new ChunkScanResult(start, chunkEnd, firstPos, endPos,
        sfOffsets.toArray(), pageOffsets.toArray(), pageSfIndices.toArray());
  }

  /**
   * Finds the first offset in {@code [from, searchEnd)} starting a chain of valid structured
   * fields (three {@code 0x5A} markers or two reaching EOF), preventing false positives inside data.
   */
  private static int findVerifiedSyncPoint(ByteBuffer buf, int limit, int from, int searchEnd) {
    int maxStart = Math.min(searchEnd, limit - 9);
    for (int pos = Math.max(0, from); pos < maxStart; pos++) {
      if ((buf.get(pos) & 0xFF) != 0x5A) {
        continue;
      }
      int len0 = ((buf.get(pos + 1) & 0xFF) << 8) | (buf.get(pos + 2) & 0xFF);
      if (len0 < 8) {
        continue;
      }
      int p1 = pos + 1 + len0;
      if (p1 + 8 >= limit || (buf.get(p1) & 0xFF) != 0x5A) {
        continue;
      }
      int len1 = ((buf.get(p1 + 1) & 0xFF) << 8) | (buf.get(p1 + 2) & 0xFF);
      if (len1 < 8) {
        continue;
      }
      int p2 = p1 + 1 + len1;
      if (p2 == limit) {
        return pos;
      }
      if (p2 + 8 < limit && (buf.get(p2) & 0xFF) == 0x5A) {
        int len2 = ((buf.get(p2 + 1) & 0xFF) << 8) | (buf.get(p2 + 2) & 0xFF);
        if (len2 >= 8) {
          return pos;
        }
      }
    }
    return -1;
  }

  private static int scanBufferSyncedRange(
      ByteBuffer buf,
      int limit,
      int startPos,
      int rangeEnd,
      LongArrayBuilder sfOffsets,
      LongArrayBuilder pageOffsets,
      IntArrayBuilder pageSfIndices) {
    int pos = Math.max(0, startPos);
    int effectiveEnd = Math.min(limit, rangeEnd);

    if (buf.hasArray()) {
      byte[] arr = buf.array();
      int base = buf.arrayOffset();
      while (pos < effectiveEnd) {
        if ((arr[base + pos] & 0xFF) == 0x5A) {
          if (pos + 8 >= limit) {
            break;
          }
          int sfLength = ((arr[base + pos + 1] & 0xFF) << 8) | (arr[base + pos + 2] & 0xFF);
          if (sfLength < 8) {
            pos++;
            continue;
          }
          int sfIdx = sfOffsets.size();
          sfOffsets.add(pos);
          if ((arr[base + pos + 3] & 0xFF) == 0xD3
              && (arr[base + pos + 4] & 0xFF) == 0xA8
              && (arr[base + pos + 5] & 0xFF) == 0xAF) {
            pageOffsets.add(pos);
            pageSfIndices.add(sfIdx);
          }
          pos += sfLength + 1;
          continue;
        }
        pos++;
      }
      return pos;
    }

    while (pos < effectiveEnd) {
      if ((buf.get(pos) & 0xFF) == 0x5A) {
        if (pos + 8 >= limit) {
          break;
        }
        int sfLength = ((buf.get(pos + 1) & 0xFF) << 8) | (buf.get(pos + 2) & 0xFF);
        if (sfLength < 8) {
          pos++;
          continue;
        }
        int sfIdx = sfOffsets.size();
        sfOffsets.add(pos);
        if ((buf.get(pos + 3) & 0xFF) == 0xD3
            && (buf.get(pos + 4) & 0xFF) == 0xA8
            && (buf.get(pos + 5) & 0xFF) == 0xAF) {
          pageOffsets.add(pos);
          pageSfIndices.add(sfIdx);
        }
        pos += sfLength + 1;
        continue;
      }
      pos++;
    }
    return pos;
  }

  private static ScanIndex stitchChunks(
      ByteBuffer buf, int limit, ChunkScanResult[] chunks, int chunkSize, int workers) {
    int estSfs = 0;
    int estPages = 0;
    for (ChunkScanResult c : chunks) {
      estSfs += c.sfOffsets.length;
      estPages += c.pageOffsets.length;
    }

    LongArrayBuilder allSfs = new LongArrayBuilder(Math.max(16, estSfs + 16));
    LongArrayBuilder allPages = new LongArrayBuilder(Math.max(16, estPages + 4));
    IntArrayBuilder allPageIndices = new IntArrayBuilder(Math.max(16, estPages + 4));

    int cursor = 0;
    for (int i = 0; i < workers; i++) {
      ChunkScanResult chunk = chunks[i];
      int nextBoundary = (i == workers - 1) ? limit : (i + 1) * chunkSize;

      if (cursor >= limit) {
        break;
      }

      if (chunk.firstPos >= 0 && chunk.firstPos == cursor) {
        int baseSfIdx = allSfs.size();
        allSfs.addAll(chunk.sfOffsets, 0, chunk.sfOffsets.length);
        for (int p = 0; p < chunk.pageOffsets.length; p++) {
          allPages.add(chunk.pageOffsets[p]);
          allPageIndices.add(baseSfIdx + chunk.pageSfIndices[p]);
        }
        cursor = (int) chunk.endPos;
      } else if (chunk.firstPos >= 0 && chunk.firstPos > cursor) {
        cursor = scanBufferSyncedRange(
            buf, limit, cursor, (int) chunk.firstPos, allSfs, allPages, allPageIndices);
        if (cursor == (int) chunk.firstPos) {
          int baseSfIdx = allSfs.size();
          allSfs.addAll(chunk.sfOffsets, 0, chunk.sfOffsets.length);
          for (int p = 0; p < chunk.pageOffsets.length; p++) {
            allPages.add(chunk.pageOffsets[p]);
            allPageIndices.add(baseSfIdx + chunk.pageSfIndices[p]);
          }
          cursor = (int) chunk.endPos;
        } else if (cursor < nextBoundary) {
          cursor = scanBufferSyncedRange(
              buf, limit, cursor, nextBoundary, allSfs, allPages, allPageIndices);
        }
      } else if (chunk.firstPos >= 0 && chunk.firstPos < cursor && chunk.endPos > cursor) {
        int matchIdx = Arrays.binarySearch(chunk.sfOffsets, (long) cursor);
        if (matchIdx >= 0) {
          int baseSfIdx = allSfs.size() - matchIdx;
          allSfs.addAll(chunk.sfOffsets, matchIdx, chunk.sfOffsets.length - matchIdx);
          for (int p = 0; p < chunk.pageOffsets.length; p++) {
            if (chunk.pageSfIndices[p] >= matchIdx) {
              allPages.add(chunk.pageOffsets[p]);
              allPageIndices.add(baseSfIdx + chunk.pageSfIndices[p]);
            }
          }
          cursor = (int) chunk.endPos;
        } else {
          cursor = scanBufferSyncedRange(
              buf, limit, cursor, nextBoundary, allSfs, allPages, allPageIndices);
        }
      } else if (cursor < nextBoundary) {
        cursor = scanBufferSyncedRange(
            buf, limit, cursor, nextBoundary, allSfs, allPages, allPageIndices);
      }
    }

    if (cursor < limit) {
      scanBufferSyncedRange(buf, limit, cursor, limit, allSfs, allPages, allPageIndices);
    }

    return new ScanIndex(allSfs.toArray(), allPages.toArray(), allPageIndices.toArray());
  }

  private ScanIndex scanIndexAsyncChannelSequential() {
    LongArrayBuilder sfOffsets = new LongArrayBuilder(256);
    LongArrayBuilder pageOffsets = new LongArrayBuilder(64);
    IntArrayBuilder pageSfIndices = new IntArrayBuilder(64);

    try {
      long fileSize = asyncChannel.size();
      long currentFilePos = 0;
      ByteBuffer chunk = ByteBuffer.allocateDirect(ASYNC_BUFFER_BYTES);
      int remainingSkip = 0;

      while (currentFilePos < fileSize) {
        chunk.clear();
        int bytesRead = asyncChannel.read(chunk, currentFilePos).get();
        if (bytesRead <= 0) {
          break;
        }
        chunk.flip();

        int chunkPos = 0;
        if (remainingSkip > 0) {
          int skipInThisChunk = Math.min(remainingSkip, bytesRead);
          chunkPos += skipInThisChunk;
          remainingSkip -= skipInThisChunk;
        }

        while (chunkPos < bytesRead) {
          if ((chunk.get(chunkPos) & 0xFF) == 0x5A) {
            if (chunkPos + 8 >= bytesRead) {
              if (currentFilePos + chunkPos + 9 > fileSize) {
                chunkPos = bytesRead;
              }
              break;
            }
            int sfLength = ((chunk.get(chunkPos + 1) & 0xFF) << 8)
                | (chunk.get(chunkPos + 2) & 0xFF);
            if (sfLength < 8) {
              chunkPos++;
              continue;
            }
            long absPos = currentFilePos + chunkPos;
            int sfIdx = sfOffsets.size();
            sfOffsets.add(absPos);
            if ((chunk.get(chunkPos + 3) & 0xFF) == 0xD3
                && (chunk.get(chunkPos + 4) & 0xFF) == 0xA8
                && (chunk.get(chunkPos + 5) & 0xFF) == 0xAF) {
              pageOffsets.add(absPos);
              pageSfIndices.add(sfIdx);
            }
            int skipBytes = sfLength + 1;
            int availableInChunk = bytesRead - chunkPos;
            if (skipBytes <= availableInChunk) {
              chunkPos += skipBytes;
            } else {
              remainingSkip = skipBytes - availableInChunk;
              chunkPos = bytesRead;
            }
            continue;
          }
          chunkPos++;
        }
        if (chunkPos == 0) {
          break;
        }
        currentFilePos += chunkPos;
      }
    } catch (Exception ignored) {
      // ignored
    }

    return new ScanIndex(sfOffsets.toArray(), pageOffsets.toArray(), pageSfIndices.toArray());
  }

  private List<Long> scanForBuffer(SFTypeID typeID, int start, int end) {
    final int targetClass = typeID.getSfClass().toByte();
    final int targetType = typeID.getSfType().toByte();
    final int targetCategory = typeID.getSfCategory().toByte();

    List<Long> offsets = new ArrayList<>();
    int pos = Math.max(0, start);
    int actualLimit = buffer.limit();
    int searchLimit = Math.min(actualLimit, end);
    boolean synced = (pos == 0);

    if (buffer.hasArray()) {
      byte[] arr = buffer.array();
      int base = buffer.arrayOffset();
      while (pos < searchLimit) {
        if ((arr[base + pos] & 0xFF) == 0x5A) {
          if (pos + 8 >= actualLimit) {
            pos++;
            synced = false;
            continue;
          }
          int sfLength = ((arr[base + pos + 1] & 0xFF) << 8)
              | (arr[base + pos + 2] & 0xFF);
          if (sfLength < 8) {
            pos++;
            synced = false;
            continue;
          }
          if ((arr[base + pos + 3] & 0xFF) == targetClass
              && (arr[base + pos + 4] & 0xFF) == targetType
              && (arr[base + pos + 5] & 0xFF) == targetCategory) {
            offsets.add((long) pos);
          }
          if (synced) {
            pos += sfLength + 1;
            continue;
          }
          int nextPos = pos + sfLength + 1;
          if (nextPos < actualLimit && (arr[base + nextPos] & 0xFF) == 0x5A) {
            synced = true;
            pos = nextPos;
            continue;
          }
        }
        pos++;
        synced = false;
      }
      return offsets;
    }

    while (pos < searchLimit) {
      if ((buffer.get(pos) & 0xFF) == 0x5A) {
        if (pos + 8 >= actualLimit) {
          pos++;
          synced = false;
          continue;
        }
        int sfLength = ((buffer.get(pos + 1) & 0xFF) << 8)
            | (buffer.get(pos + 2) & 0xFF);
        if (sfLength < 8) {
          pos++;
          synced = false;
          continue;
        }
        if ((buffer.get(pos + 3) & 0xFF) == targetClass
            && (buffer.get(pos + 4) & 0xFF) == targetType
            && (buffer.get(pos + 5) & 0xFF) == targetCategory) {
          offsets.add((long) pos);
        }
        if (synced) {
          pos += sfLength + 1;
          continue;
        }
        int nextPos = pos + sfLength + 1;
        if (nextPos < actualLimit && (buffer.get(nextPos) & 0xFF) == 0x5A) {
          synced = true;
          pos = nextPos;
          continue;
        }
      }
      pos++;
      synced = false;
    }
    return offsets;
  }

  private List<Long> scanForAsyncChannel(SFTypeID typeID) {
    try {
      return scanForAsyncChannelPart(typeID, 0, asyncChannel.size());
    } catch (Exception expected) {
      return new ArrayList<>();
    }
  }

  private List<Long> scanForBufferParallel(SFTypeID typeID, int numThreads) {
    ExecutorService executor = Executors.newFixedThreadPool(numThreads);
    int limit = buffer.limit();
    int chunkSize = limit / numThreads;
    List<Long> allOffsets = new ArrayList<>();

    try {
      List<Future<List<Long>>> futures = new ArrayList<>(numThreads);
      for (int i = 0; i < numThreads; i++) {
        final int start = i * chunkSize;
        final int end = (i == numThreads - 1)
            ? limit : Math.min(limit, (i + 1) * chunkSize + CHUNK_OVERLAP_BYTES);
        futures.add(executor.submit(() -> scanForBuffer(typeID, start, end)));
      }
      for (Future<List<Long>> future : futures) {
        allOffsets.addAll(future.get());
      }
    } catch (Exception expected) {
      // ignored
    } finally {
      executor.shutdown();
    }

    return sortAndDeduplicate(allOffsets);
  }

  private List<Long> scanForAsyncChannelParallel(SFTypeID typeID, int numThreads) {
    ExecutorService executor = Executors.newFixedThreadPool(numThreads);
    List<Long> allOffsets = new ArrayList<>();

    try {
      long fileSize = asyncChannel.size();
      long chunkSize = fileSize / numThreads;

      List<Future<List<Long>>> futures = new ArrayList<>(numThreads);
      for (int i = 0; i < numThreads; i++) {
        final long start = i * chunkSize;
        final long end = (i == numThreads - 1)
            ? fileSize : Math.min(fileSize, (i + 1) * chunkSize + CHUNK_OVERLAP_BYTES);
        futures.add(executor.submit(() -> scanForAsyncChannelPart(typeID, start, end)));
      }
      for (Future<List<Long>> future : futures) {
        allOffsets.addAll(future.get());
      }
    } catch (Exception expected) {
      // ignored
    } finally {
      executor.shutdown();
    }

    return sortAndDeduplicate(allOffsets);
  }

  private List<Long> scanForAsyncChannelPart(SFTypeID typeID, long start, long end) {
    final int targetClass = typeID.getSfClass().toByte();
    final int targetType = typeID.getSfType().toByte();
    final int targetCategory = typeID.getSfCategory().toByte();

    List<Long> offsets = new ArrayList<>();
    try {
      long fileSize = asyncChannel.size();
      long effectiveEnd = Math.min(end, fileSize);
      long currentFilePos = start;
      ByteBuffer chunk = ByteBuffer.allocateDirect(ASYNC_BUFFER_BYTES);
      int remainingSkip = 0;
      boolean synced = (start == 0);

      while (currentFilePos < effectiveEnd) {
        chunk.clear();
        int toRead = (int) Math.min(chunk.capacity(), effectiveEnd - currentFilePos);
        if (toRead <= 0) {
          break;
        }
        chunk.limit(toRead);
        int bytesRead = asyncChannel.read(chunk, currentFilePos).get();
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
            if (chunkPos + 8 >= bytesRead) {
              if (currentFilePos + chunkPos + 9 > effectiveEnd) {
                chunkPos = bytesRead;
              }
              break;
            }
            int sfLength = ((chunk.get(chunkPos + 1) & 0xFF) << 8)
                | (chunk.get(chunkPos + 2) & 0xFF);
            if (sfLength < 8) {
              chunkPos++;
              synced = false;
              continue;
            }
            if ((chunk.get(chunkPos + 3) & 0xFF) == targetClass
                && (chunk.get(chunkPos + 4) & 0xFF) == targetType
                && (chunk.get(chunkPos + 5) & 0xFF) == targetCategory) {
              offsets.add(currentFilePos + chunkPos);
            }

            int skipBytes = sfLength + 1;
            int availableInChunk = bytesRead - chunkPos;

            if (synced || (skipBytes < availableInChunk
                && (chunk.get(chunkPos + skipBytes) & 0xFF) == 0x5A)) {
              synced = true;
              if (skipBytes <= availableInChunk) {
                chunkPos += skipBytes;
              } else {
                remainingSkip = skipBytes - availableInChunk;
                chunkPos = bytesRead;
                synced = false;
              }
              continue;
            }
          }
          chunkPos++;
          synced = false;
        }
        if (chunkPos == 0) {
          break;
        }
        currentFilePos += chunkPos;
      }
    } catch (Exception expected) {
      // ignored
    }
    return offsets;
  }

  private static List<Long> sortAndDeduplicate(List<Long> offsets) {
    if (offsets.size() <= 1) {
      return offsets;
    }
    Collections.sort(offsets);
    int writeIdx = 1;
    long prev = offsets.get(0);
    for (int readIdx = 1; readIdx < offsets.size(); readIdx++) {
      long cur = offsets.get(readIdx);
      if (cur != prev) {
        offsets.set(writeIdx++, cur);
        prev = cur;
      }
    }
    if (writeIdx < offsets.size()) {
      offsets.subList(writeIdx, offsets.size()).clear();
    }
    return offsets;
  }

  public List<Long> findPageBoundaries() {
    return scanFor(SFTypeID.BPG_BeginPage);
  }

  public List<Long> findPageBoundariesParallel() {
    return scanForParallel(SFTypeID.BPG_BeginPage, Runtime.getRuntime().availableProcessors());
  }

  private record ChunkScanResult(
      long start,
      long end,
      long firstPos,
      long endPos,
      long[] sfOffsets,
      long[] pageOffsets,
      int[] pageSfIndices) {}

  private static final class LongArrayBuilder {
    private long[] data;
    private int size;

    LongArrayBuilder(int initialCapacity) {
      this.data = new long[Math.max(16, initialCapacity)];
    }

    void add(long val) {
      if (size == data.length) {
        data = Arrays.copyOf(data, data.length << 1);
      }
      data[size++] = val;
    }

    void addAll(long[] src, int off, int len) {
      if (len <= 0) {
        return;
      }
      if (size + len > data.length) {
        int newCap = Math.max(data.length << 1, size + len);
        data = Arrays.copyOf(data, newCap);
      }
      System.arraycopy(src, off, data, size, len);
      size += len;
    }

    long get(int idx) {
      return data[idx];
    }

    int size() {
      return size;
    }

    long[] toArray() {
      return Arrays.copyOf(data, size);
    }
  }

  private static final class IntArrayBuilder {
    private int[] data;
    private int size;

    IntArrayBuilder(int initialCapacity) {
      this.data = new int[Math.max(16, initialCapacity)];
    }

    void add(int val) {
      if (size == data.length) {
        data = Arrays.copyOf(data, data.length << 1);
      }
      data[size++] = val;
    }

    int[] toArray() {
      return Arrays.copyOf(data, size);
    }
  }
}
