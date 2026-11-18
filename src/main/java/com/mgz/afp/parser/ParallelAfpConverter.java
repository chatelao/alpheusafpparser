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

import com.mgz.afp.base.StructuredField;
import com.mgz.afp.base.handler.HandlerFactory;
import com.mgz.afp.base.handler.OrderedResultCollector;
import com.mgz.afp.base.handler.StructuredFieldHandler;
import com.mgz.afp.exceptions.AFPParserException;
import com.mgz.util.ExposedByteArrayOutputStream;
import com.mgz.util.MnemonicPerformanceMonitor;
import com.mgz.util.PTXPerformanceMonitor;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousFileChannel;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

/**
 * Orchestrates high-throughput parallel conversion of single large AFP files to XML.
 */
public class ParallelAfpConverter {

  private static final int BATCH_MULTIPLIER = 4;
  private static final long MAX_ASYNC_BATCH_BYTES = 16L * 1024 * 1024;
  private static final long MAX_INITIAL_CAPACITY_BYTES = 4L * 1024 * 1024;

  private final AFPParserConfiguration config;
  private final int numThreads;
  private final HandlerFactory handlerFactory;

  /**
   * Contiguous page-range batch processed by a single worker task.
   */
  record PageBatch(int sequence, int startPageIndex, long startOffset, long endOffset) {}

  /**
   * Constructs a ParallelAfpConverter.
   *
   * @param config the parser configuration
   * @param numThreads number of threads to use
   * @param handlerFactory factory for creating field handlers
   */
  public ParallelAfpConverter(
      AFPParserConfiguration config, int numThreads, HandlerFactory handlerFactory) {
    this.config = config;
    this.numThreads =
        numThreads > 0 ? numThreads : Runtime.getRuntime().availableProcessors();
    this.handlerFactory = handlerFactory;
  }

  /**
   * Converts the AFP file to XML in parallel.
   *
   * @param out the output stream for the XML
   * @throws Exception if parsing or I/O fails
   */
  public void convert(OutputStream out) throws Exception {
    ByteBuffer buffer = config.getByteBuffer();
    AsynchronousFileChannel asyncChannel = null;
    if (buffer == null) {
      asyncChannel = config.getAsyncFileChannel();
    }

    if (buffer == null && asyncChannel == null) {
      throw new AFPParserException(
          "MappedByteBuffer or AsynchronousFileChannel is required for parallel parsing.");
    }

    AFPScanner scanner = (buffer != null) ? new AFPScanner(buffer) : new AFPScanner(asyncChannel);
    List<Long> pageOffsets = scanner.findPageBoundariesParallel();

    long fileSize = (buffer != null) ? buffer.limit() : asyncChannel.size();
    long firstPageOffset = pageOffsets.isEmpty() ? fileSize : pageOffsets.get(0);

    OrderedResultCollector collector;
    if (out instanceof FileOutputStream fos) {
      collector = new OrderedResultCollector(out, fos.getChannel());
    } else {
      collector = new OrderedResultCollector(out);
    }

    // Create a master handler that is NOT in fragment mode so it emits document open/close.
    // Page batches stream headless fragments directly through OrderedResultCollector.
    try (StructuredFieldHandler masterHandler = handlerFactory.createHandler(out, false)) {
      long maxBatchBytes = (buffer == null && asyncChannel != null) ? MAX_ASYNC_BATCH_BYTES : 0L;
      processPreambleAndPages(
          firstPageOffset, pageOffsets, fileSize, collector, masterHandler, maxBatchBytes);
    } finally {
      if (asyncChannel != null) {
        config.closeAsyncFileChannel();
      }
    }
  }

  private void processPreambleAndPages(
      long firstPageOffset,
      List<Long> pageOffsets,
      long fileSize,
      OrderedResultCollector collector,
      StructuredFieldHandler masterHandler,
      long maxBatchBytes)
      throws Exception {
    // 1. Parse and write Preamble sequentially
    if (firstPageOffset > 0) {
      AFPParser preambleParser = new AFPParser(config);
      while (preambleParser.getCountReadByte() < firstPageOffset) {
        StructuredField sf = preambleParser.parseNextSF();
        if (sf == null) {
          break;
        }
        masterHandler.handle(sf);
        sf.release();
      }
      // Ensure preamble and root element are flushed before parallel fragments are written.
      masterHandler.flush();
    }

    if (pageOffsets.isEmpty()) {
      return;
    }

    int preamblePageCount = masterHandler.getCurrentPageNumber();
    List<PageBatch> batches =
        createPageBatches(pageOffsets, fileSize, numThreads, maxBatchBytes);

    // 2. Parallel Coarse Page-Batch Processing
    ExecutorService executor = Executors.newFixedThreadPool(Math.min(numThreads, batches.size()));
    try {
      List<Future<Void>> futures = new ArrayList<>(batches.size());
      for (PageBatch batch : batches) {
        futures.add(
            executor.submit(
                new PageBatchTask(
                    config.clone(),
                    batch,
                    preamblePageCount,
                    collector,
                    handlerFactory)));
      }

      for (Future<Void> future : futures) {
        future.get();
      }
    } finally {
      executor.shutdown();
      executor.awaitTermination(1, TimeUnit.HOURS);
    }
  }

  /**
   * Partitions page boundaries into contiguous byte-balanced batches.
   *
   * <p>For files with many small pages (e.g. 1,000 pages), grouping pages into {@code numThreads
   * * 4} batches eliminates per-page StAX/parser/collector overhead while preserving ordered output.
   */
  static List<PageBatch> createPageBatches(
      List<Long> pageOffsets, long fileSize, int numThreads, long maxBatchBytes) {
    int pageCount = pageOffsets.size();
    List<PageBatch> batches = new ArrayList<>();
    if (pageCount == 0) {
      return batches;
    }

    int targetBatches = Math.min(pageCount, Math.max(1, numThreads) * BATCH_MULTIPLIER);
    int batchSeq = 0;
    int i = 0;
    while (i < pageCount) {
      int startPageIdx = i;
      long startOffset = pageOffsets.get(startPageIdx);
      int remainingPages = pageCount - startPageIdx;
      int remainingBatches = Math.max(1, targetBatches - batchSeq);
      long remainingBytes = Math.max(1L, fileSize - startOffset);
      long desiredBatchBytes = (remainingBytes + remainingBatches - 1) / remainingBatches;
      if (maxBatchBytes > 0) {
        desiredBatchBytes = Math.min(desiredBatchBytes, maxBatchBytes);
      }

      int maxPagesInBatch =
          (remainingPages >= remainingBatches)
              ? Math.max(1, remainingPages - (remainingBatches - 1))
              : remainingPages;

      int endPageIdx = startPageIdx + 1;
      while (endPageIdx < pageCount && (endPageIdx - startPageIdx) < maxPagesInBatch) {
        long currentBytes = pageOffsets.get(endPageIdx) - startOffset;
        if (currentBytes >= desiredBatchBytes) {
          break;
        }
        long nextEndOffset =
            (endPageIdx + 1 < pageCount) ? pageOffsets.get(endPageIdx + 1) : fileSize;
        if (maxBatchBytes > 0 && (nextEndOffset - startOffset) > maxBatchBytes) {
          break;
        }
        endPageIdx++;
      }

      long endOffset = (endPageIdx < pageCount) ? pageOffsets.get(endPageIdx) : fileSize;
      batches.add(new PageBatch(batchSeq++, startPageIdx, startOffset, endOffset));
      i = endPageIdx;
    }
    return batches;
  }

  private static class PageBatchTask implements Callable<Void> {

    private final AFPParserConfiguration taskConfig;
    private final PageBatch batch;
    private final int preamblePageCount;
    private final OrderedResultCollector collector;
    private final HandlerFactory handlerFactory;

    PageBatchTask(
        AFPParserConfiguration config,
        PageBatch batch,
        int preamblePageCount,
        OrderedResultCollector collector,
        HandlerFactory handlerFactory) {
      this.taskConfig = config;
      this.batch = batch;
      this.preamblePageCount = preamblePageCount;
      this.collector = collector;
      this.handlerFactory = handlerFactory;
    }

    @Override
    public Void call() throws Exception {
      long startOffset = batch.startOffset();
      long endOffset = batch.endOffset();
      long estimated = handlerFactory.estimateOutputSize(endOffset - startOffset);
      int initialCapacity =
          (int) Math.max(1024L, Math.min(MAX_INITIAL_CAPACITY_BYTES, estimated));
      ExposedByteArrayOutputStream baos = new ExposedByteArrayOutputStream(initialCapacity);
      try {
        boolean isBatchSlice = false;
        if (taskConfig.getByteBuffer() == null && taskConfig.getAsyncFileChannel() != null) {
          long rangeSize = endOffset - startOffset;
          if (rangeSize > Integer.MAX_VALUE) {
            throw new IOException("Page batch exceeds 2GB direct buffer limit: " + rangeSize);
          }
          ByteBuffer batchData = ByteBuffer.allocateDirect((int) rangeSize);
          long pos = startOffset;
          AsynchronousFileChannel channel = taskConfig.getAsyncFileChannel();
          while (batchData.hasRemaining()) {
            int read = channel.read(batchData, pos).get();
            if (read <= 0) {
              break;
            }
            pos += read;
          }
          batchData.flip();
          taskConfig.setByteBuffer(batchData);
          isBatchSlice = true;
        }

        AFPParser parser = new AFPParser(taskConfig);
        if (!isBatchSlice && taskConfig.getByteBuffer() != null) {
          parser.setNrOfBytesRead(startOffset);
        }
        long parseLimit = isBatchSlice ? (endOffset - startOffset) : endOffset;

        try (StructuredFieldHandler handler = handlerFactory.createHandler(baos, true)) {
          handler.setCurrentPageNumber(preamblePageCount + batch.startPageIndex());
          StructuredField sf;
          while ((sf = parser.parseNextSF()) != null) {
            handler.handle(sf);
            sf.release();
            if (parser.getCountReadByte() >= parseLimit) {
              break;
            }
          }
        }
      } finally {
        MnemonicPerformanceMonitor.merge();
        PTXPerformanceMonitor.merge();
      }

      ByteBuffer fullData = baos.toByteBuffer();
      ByteBuffer stripped = handlerFactory.stripFragmentWrapper(fullData);
      collector.put(batch.sequence(), stripped);
      return null;
    }
  }
}
