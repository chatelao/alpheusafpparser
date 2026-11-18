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
import com.mgz.afp.modca.BMO_BeginOverlay;
import com.mgz.afp.modca.BPS_BeginPageSegment;
import com.mgz.afp.modca.EMO_EndOverlay;
import com.mgz.afp.modca.EPS_EndPageSegment;
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
 * Orchestrates high-throughput parallel conversion of single large AFP files to XML or PDF.
 */
public class ParallelAfpConverter {

  private static final int BATCH_MULTIPLIER = 4;
  private static final long MAX_ASYNC_BATCH_BYTES = 16L * 1024 * 1024;
  private static final long MAX_BINARY_BATCH_BYTES = 16L * 1024 * 1024;
  private static final long MAX_INITIAL_CAPACITY_BYTES = 4L * 1024 * 1024;

  private final AFPParserConfiguration config;
  private final int numThreads;
  private final HandlerFactory handlerFactory;

  /**
   * Contiguous page-range batch processed by a single worker task.
   */
  record PageBatch(int sequence, int startPageIndex, long startOffset, long endOffset) {}

  /**
   * Byte span of a top-level resource capture block ({@code BMO..EMO} or {@code BPS..EPS}) parsed
   * during the prefix so binary-merge workers can rebuild per-document XObjects.
   */
  record ResourceSpan(
      long startOffset,
      long endOffset,
      int startSfIdx,
      int endSfIdx,
      byte[] preloadedBytes,
      Object entryBatchState) {}

  record BinaryBatchResult(ByteBuffer pdfBytes, Object metadata) {}

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
   * Converts the AFP file to XML or PDF in parallel.
   *
   * @param out the output stream for the generated document
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
    AFPScanner.ScanIndex scanIndex = scanner.scanIndexParallel(numThreads);
    long[] pageOffsets = scanIndex.pageOffsets();

    long fileSize = (buffer != null) ? buffer.limit() : asyncChannel.size();
    long firstPageOffset = (pageOffsets.length == 0) ? fileSize : pageOffsets[0];

    boolean binaryMerge = handlerFactory.isBinaryDocumentMerge();
    OrderedResultCollector collector = null;
    if (!binaryMerge) {
      if (out instanceof FileOutputStream fos) {
        collector = new OrderedResultCollector(out, fos.getChannel());
      } else {
        collector = new OrderedResultCollector(out);
      }
    }

    // Create a master handler that is NOT in fragment mode so it emits document open/close.
    try (StructuredFieldHandler masterHandler = handlerFactory.createHandler(out, false)) {
      if (binaryMerge) {
        processBinaryMergeConversion(firstPageOffset, scanIndex, fileSize, masterHandler);
      } else {
        long maxBatchBytes = (buffer == null && asyncChannel != null) ? MAX_ASYNC_BATCH_BYTES : 0L;
        processPreambleAndPages(
            firstPageOffset, scanIndex, fileSize, collector, masterHandler, maxBatchBytes);
      }
    } finally {
      if (asyncChannel != null) {
        config.closeAsyncFileChannel();
      }
    }
  }

  private void processBinaryMergeConversion(
      long firstPageOffset,
      AFPScanner.ScanIndex scanIndex,
      long fileSize,
      StructuredFieldHandler masterHandler)
      throws Exception {
    long[] sfOffsets = scanIndex.sfOffsets();
    long[] pageOffsets = scanIndex.pageOffsets();
    int[] pageSfIndices = scanIndex.pageSfIndices();
    List<ResourceSpan> resourceSpans = new ArrayList<>();

    // 1. Parse Preamble sequentially on masterHandler while capturing any BMO/BPS resource spans.
    if (firstPageOffset > 0) {
      int preambleEndSfIdx = (pageSfIndices.length > 0) ? pageSfIndices[0] : sfOffsets.length;
      parseSequentialRangeAndTrackResources(
          0L, firstPageOffset, 0, preambleEndSfIdx, sfOffsets, masterHandler, resourceSpans);
    }

    if (pageOffsets.length == 0) {
      return;
    }

    // Single-page files do not benefit from multi-document PDF splitting and merging.
    if (pageOffsets.length == 1) {
      parseSequentialRangeAndTrackResources(
          pageOffsets[0], fileSize, pageSfIndices[0], sfOffsets.length,
          sfOffsets, masterHandler, resourceSpans);
      return;
    }

    // 2. If PGD/PTD defaults live inside Page 0's AEG, parse Page 0 sequentially first.
    int batchStartPageIdx = 0;
    if (masterHandler.needsFirstPageForBatchState()) {
      int page0EndSfIdx = (pageSfIndices.length > 1) ? pageSfIndices[1] : sfOffsets.length;
      parseSequentialRangeAndTrackResources(
          pageOffsets[0], pageOffsets[1], pageSfIndices[0], page0EndSfIdx,
          sfOffsets, masterHandler, resourceSpans);
      batchStartPageIdx = 1;
    }

    // Preload any preamble resource spans once when reading via AsynchronousFileChannel.
    if (config.getByteBuffer() == null && config.getAsyncFileChannel() != null
        && !resourceSpans.isEmpty()) {
      AsynchronousFileChannel asyncChannel = config.getAsyncFileChannel();
      List<ResourceSpan> preloaded = new ArrayList<>(resourceSpans.size());
      for (ResourceSpan span : resourceSpans) {
        ByteBuffer slice = readAsyncRange(asyncChannel, span.startOffset(), span.endOffset());
        byte[] bytes = new byte[slice.remaining()];
        slice.get(bytes);
        preloaded.add(new ResourceSpan(
            span.startOffset(),
            span.endOffset(),
            span.startSfIdx(),
            span.endSfIdx(),
            bytes,
            span.entryBatchState()));
      }
      resourceSpans = preloaded;
    }

    // 3. Snapshot master state and launch parallel worker batches for remaining pages.
    Object batchState = masterHandler.exportBatchState();
    int preamblePageCount = masterHandler.getCurrentPageNumber();
    List<PageBatch> batches = createPageBatches(
        pageOffsets, batchStartPageIdx, fileSize, numThreads, MAX_BINARY_BATCH_BYTES);

    ExecutorService executor = Executors.newFixedThreadPool(Math.min(numThreads, batches.size()));
    try {
      int batchCount = batches.size();
      List<Future<BinaryBatchResult>> futures = new ArrayList<>(batchCount);
      for (int i = 0; i < batchCount; i++) {
        PageBatch batch = batches.get(i);
        int startSfIdx = pageSfIndices[batch.startPageIndex()];
        int endSfIdx = (i + 1 < batchCount)
            ? pageSfIndices[batches.get(i + 1).startPageIndex()]
            : sfOffsets.length;
        futures.add(
            executor.submit(
                new BinaryPageBatchTask(
                    config.clone(),
                    batch,
                    sfOffsets,
                    startSfIdx,
                    endSfIdx,
                    preamblePageCount,
                    batchState,
                    resourceSpans,
                    handlerFactory)));
      }

      for (int i = 0; i < batchCount; i++) {
        BinaryBatchResult result = futures.get(i).get();
        futures.set(i, null);
        masterHandler.mergeBatch(result.pdfBytes(), result.metadata());
      }
    } finally {
      executor.shutdown();
      executor.awaitTermination(1, TimeUnit.HOURS);
    }
  }

  private void parseSequentialRangeAndTrackResources(
      long startOffset,
      long endOffset,
      int startSfIdx,
      int endSfIdx,
      long[] sfOffsets,
      StructuredFieldHandler handler,
      List<ResourceSpan> resourceSpans)
      throws Exception {
    boolean useSlice = false;
    long baseOffset = 0L;
    long parseLimit = endOffset;
    if (config.getByteBuffer() == null && config.getAsyncFileChannel() != null && startOffset > 0) {
      ByteBuffer slice = readAsyncRange(config.getAsyncFileChannel(), startOffset, endOffset);
      config.setByteBuffer(slice);
      useSlice = true;
      baseOffset = startOffset;
      parseLimit = endOffset - startOffset;
    }

    try {
      AFPParser parser = new AFPParser(config);
      if (sfOffsets.length > startSfIdx && config.getByteBuffer() != null) {
        parser.setPreScannedSfOffsets(sfOffsets, startSfIdx, endSfIdx, useSlice ? startOffset : 0L);
      } else if (!useSlice && config.getByteBuffer() != null) {
        parser.setNrOfBytesRead(startOffset);
      }

      int depth = 0;
      long spanStartOffset = -1L;
      int spanStartSfIdx = -1;
      Object spanEntryState = null;
      int currentSfIdx = startSfIdx;

      StructuredField sf;
      while ((sf = parser.parseNextSF()) != null) {
        long sfStartOffset = sf.getStructuredFieldIntroducer().getFileOffset() + baseOffset;
        final long sfEndOffset = parser.getCountReadByte() + baseOffset;
        boolean isResBegin = (sf instanceof BMO_BeginOverlay || sf instanceof BPS_BeginPageSegment);
        final boolean isResEnd = (sf instanceof EMO_EndOverlay || sf instanceof EPS_EndPageSegment);

        if (isResBegin) {
          if (depth == 0) {
            spanStartOffset = sfStartOffset;
            spanStartSfIdx = currentSfIdx;
            spanEntryState = handler.exportBatchState();
          }
          depth++;
        }

        handler.handle(sf);
        sf.release();

        if (isResEnd && depth > 0) {
          depth--;
          if (depth == 0 && spanStartOffset >= 0) {
            resourceSpans.add(new ResourceSpan(
                spanStartOffset,
                sfEndOffset,
                spanStartSfIdx,
                currentSfIdx + 1,
                null,
                spanEntryState));
            spanStartOffset = -1L;
            spanStartSfIdx = -1;
            spanEntryState = null;
          }
        }

        currentSfIdx++;
        if (parser.getCountReadByte() >= parseLimit) {
          break;
        }
      }
      handler.flush();
    } finally {
      if (useSlice) {
        config.setByteBuffer(null);
      }
    }
  }

  private void processPreambleAndPages(
      long firstPageOffset,
      AFPScanner.ScanIndex scanIndex,
      long fileSize,
      OrderedResultCollector collector,
      StructuredFieldHandler masterHandler,
      long maxBatchBytes)
      throws Exception {
    long[] sfOffsets = scanIndex.sfOffsets();
    long[] pageOffsets = scanIndex.pageOffsets();
    int[] pageSfIndices = scanIndex.pageSfIndices();

    // 1. Parse and write Preamble sequentially
    if (firstPageOffset > 0) {
      AFPParser preambleParser = new AFPParser(config);
      int preambleEndSfIdx = (pageSfIndices.length > 0) ? pageSfIndices[0] : sfOffsets.length;
      if (sfOffsets.length > 0 && config.getByteBuffer() != null) {
        preambleParser.setPreScannedSfOffsets(sfOffsets, 0, preambleEndSfIdx, 0L);
      }
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

    if (pageOffsets.length == 0) {
      return;
    }

    int preamblePageCount = masterHandler.getCurrentPageNumber();
    List<PageBatch> batches =
        createPageBatches(pageOffsets, fileSize, numThreads, maxBatchBytes);

    // 2. Parallel Coarse Page-Batch Processing
    ExecutorService executor = Executors.newFixedThreadPool(Math.min(numThreads, batches.size()));
    try {
      int batchCount = batches.size();
      List<Future<Void>> futures = new ArrayList<>(batchCount);
      for (int i = 0; i < batchCount; i++) {
        PageBatch batch = batches.get(i);
        int startSfIdx = pageSfIndices[batch.startPageIndex()];
        int endSfIdx = (i + 1 < batchCount)
            ? pageSfIndices[batches.get(i + 1).startPageIndex()]
            : sfOffsets.length;
        futures.add(
            executor.submit(
                new PageBatchTask(
                    config.clone(),
                    batch,
                    sfOffsets,
                    startSfIdx,
                    endSfIdx,
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
   * <p>For files with many small pages (e.g. 1,000 pages), grouping pages into
   * {@code numThreads * 4} batches eliminates per-page StAX/parser/collector overhead while
   * preserving ordered output.
   */
  static List<PageBatch> createPageBatches(
      List<Long> pageOffsets, long fileSize, int numThreads, long maxBatchBytes) {
    if (pageOffsets == null || pageOffsets.isEmpty()) {
      return new ArrayList<>();
    }
    long[] arr = new long[pageOffsets.size()];
    for (int i = 0; i < arr.length; i++) {
      arr[i] = pageOffsets.get(i);
    }
    return createPageBatches(arr, 0, fileSize, numThreads, maxBatchBytes);
  }

  static List<PageBatch> createPageBatches(
      long[] pageOffsets, long fileSize, int numThreads, long maxBatchBytes) {
    return createPageBatches(pageOffsets, 0, fileSize, numThreads, maxBatchBytes);
  }

  static List<PageBatch> createPageBatches(
      long[] pageOffsets, int fromPageIndex, long fileSize, int numThreads, long maxBatchBytes) {
    int pageCount = pageOffsets.length;
    List<PageBatch> batches = new ArrayList<>();
    if (pageCount == 0 || fromPageIndex >= pageCount) {
      return batches;
    }

    int targetBatches =
        Math.min(pageCount - fromPageIndex, Math.max(1, numThreads) * BATCH_MULTIPLIER);
    int batchSeq = 0;
    int i = Math.max(0, fromPageIndex);
    while (i < pageCount) {
      int startPageIdx = i;
      long startOffset = pageOffsets[startPageIdx];
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
        long currentBytes = pageOffsets[endPageIdx] - startOffset;
        if (currentBytes >= desiredBatchBytes) {
          break;
        }
        long nextEndOffset =
            (endPageIdx + 1 < pageCount) ? pageOffsets[endPageIdx + 1] : fileSize;
        if (maxBatchBytes > 0 && (nextEndOffset - startOffset) > maxBatchBytes) {
          break;
        }
        endPageIdx++;
      }

      long endOffset = (endPageIdx < pageCount) ? pageOffsets[endPageIdx] : fileSize;
      batches.add(new PageBatch(batchSeq++, startPageIdx, startOffset, endOffset));
      i = endPageIdx;
    }
    return batches;
  }

  private static ByteBuffer readAsyncRange(
      AsynchronousFileChannel channel, long startOffset, long endOffset) throws Exception {
    long rangeSize = endOffset - startOffset;
    if (rangeSize > Integer.MAX_VALUE) {
      throw new IOException("Page batch exceeds 2GB direct buffer limit: " + rangeSize);
    }
    ByteBuffer batchData = ByteBuffer.allocateDirect((int) rangeSize);
    long pos = startOffset;
    while (batchData.hasRemaining()) {
      int read = channel.read(batchData, pos).get();
      if (read <= 0) {
        break;
      }
      pos += read;
    }
    batchData.flip();
    return batchData;
  }

  private static class PageBatchTask implements Callable<Void> {

    private final AFPParserConfiguration taskConfig;
    private final PageBatch batch;
    private final long[] sfOffsets;
    private final int startSfIdx;
    private final int endSfIdx;
    private final int preamblePageCount;
    private final OrderedResultCollector collector;
    private final HandlerFactory handlerFactory;

    PageBatchTask(
        AFPParserConfiguration config,
        PageBatch batch,
        long[] sfOffsets,
        int startSfIdx,
        int endSfIdx,
        int preamblePageCount,
        OrderedResultCollector collector,
        HandlerFactory handlerFactory) {
      this.taskConfig = config;
      this.batch = batch;
      this.sfOffsets = sfOffsets;
      this.startSfIdx = startSfIdx;
      this.endSfIdx = endSfIdx;
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
          taskConfig.setByteBuffer(
              readAsyncRange(taskConfig.getAsyncFileChannel(), startOffset, endOffset));
          isBatchSlice = true;
        }

        AFPParser parser = new AFPParser(taskConfig);
        if (sfOffsets.length > startSfIdx && taskConfig.getByteBuffer() != null) {
          long baseOffset = isBatchSlice ? startOffset : 0L;
          parser.setPreScannedSfOffsets(sfOffsets, startSfIdx, endSfIdx, baseOffset);
        } else if (!isBatchSlice && taskConfig.getByteBuffer() != null) {
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

  private static class BinaryPageBatchTask implements Callable<BinaryBatchResult> {

    private final AFPParserConfiguration taskConfig;
    private final PageBatch batch;
    private final long[] sfOffsets;
    private final int startSfIdx;
    private final int endSfIdx;
    private final int preamblePageCount;
    private final Object batchState;
    private final List<ResourceSpan> resourceSpans;
    private final HandlerFactory handlerFactory;

    BinaryPageBatchTask(
        AFPParserConfiguration config,
        PageBatch batch,
        long[] sfOffsets,
        int startSfIdx,
        int endSfIdx,
        int preamblePageCount,
        Object batchState,
        List<ResourceSpan> resourceSpans,
        HandlerFactory handlerFactory) {
      this.taskConfig = config;
      this.batch = batch;
      this.sfOffsets = sfOffsets;
      this.startSfIdx = startSfIdx;
      this.endSfIdx = endSfIdx;
      this.preamblePageCount = preamblePageCount;
      this.batchState = batchState;
      this.resourceSpans = resourceSpans;
      this.handlerFactory = handlerFactory;
    }

    @Override
    public BinaryBatchResult call() throws Exception {
      long startOffset = batch.startOffset();
      long endOffset = batch.endOffset();
      long estimated = handlerFactory.estimateOutputSize(endOffset - startOffset);
      int initialCapacity =
          (int) Math.max(4096L, Math.min(MAX_INITIAL_CAPACITY_BYTES, estimated));
      ExposedByteArrayOutputStream baos = new ExposedByteArrayOutputStream(initialCapacity);
      Object metadata;
      try {
        boolean isBatchSlice = false;
        if (taskConfig.getByteBuffer() == null && taskConfig.getAsyncFileChannel() != null) {
          taskConfig.setByteBuffer(
              readAsyncRange(taskConfig.getAsyncFileChannel(), startOffset, endOffset));
          isBatchSlice = true;
        }

        try (StructuredFieldHandler handler = handlerFactory.createHandler(baos, true)) {
          for (ResourceSpan span : resourceSpans) {
            if (span.entryBatchState() != null) {
              handler.importBatchState(span.entryBatchState());
            }
            replayResourceSpan(span, taskConfig, sfOffsets, handler);
          }
          if (batchState != null) {
            handler.importBatchState(batchState);
          }
          handler.setCurrentPageNumber(preamblePageCount + batch.startPageIndex());

          AFPParser parser = new AFPParser(taskConfig);
          if (sfOffsets.length > startSfIdx && taskConfig.getByteBuffer() != null) {
            long baseOffset = isBatchSlice ? startOffset : 0L;
            parser.setPreScannedSfOffsets(sfOffsets, startSfIdx, endSfIdx, baseOffset);
          } else if (!isBatchSlice && taskConfig.getByteBuffer() != null) {
            parser.setNrOfBytesRead(startOffset);
          }
          long parseLimit = isBatchSlice ? (endOffset - startOffset) : endOffset;

          StructuredField sf;
          while ((sf = parser.parseNextSF()) != null) {
            handler.handle(sf);
            sf.release();
            if (parser.getCountReadByte() >= parseLimit) {
              break;
            }
          }
          handler.flush();
          metadata = handler.exportBatchMetadata();
        }
      } finally {
        MnemonicPerformanceMonitor.merge();
        PTXPerformanceMonitor.merge();
      }
      return new BinaryBatchResult(baos.toByteBuffer(), metadata);
    }

    private static void replayResourceSpan(
        ResourceSpan span,
        AFPParserConfiguration taskConfig,
        long[] sfOffsets,
        StructuredFieldHandler handler)
        throws Exception {
      AFPParser parser;
      long parseLimit;
      if (span.preloadedBytes() != null) {
        AFPParserConfiguration spanConfig = taskConfig.clone();
        spanConfig.setByteBuffer(ByteBuffer.wrap(span.preloadedBytes()));
        parser = new AFPParser(spanConfig);
        parseLimit = span.preloadedBytes().length;
      } else {
        AFPParserConfiguration spanConfig = taskConfig.clone();
        parser = new AFPParser(spanConfig);
        if (sfOffsets.length > span.startSfIdx() && spanConfig.getByteBuffer() != null) {
          parser.setPreScannedSfOffsets(sfOffsets, span.startSfIdx(), span.endSfIdx(), 0L);
        } else if (spanConfig.getByteBuffer() != null) {
          parser.setNrOfBytesRead(span.startOffset());
        }
        parseLimit = span.endOffset();
      }

      StructuredField sf;
      while ((sf = parser.parseNextSF()) != null) {
        handler.handle(sf);
        sf.release();
        if (parser.getCountReadByte() >= parseLimit) {
          break;
        }
      }
    }
  }
}
