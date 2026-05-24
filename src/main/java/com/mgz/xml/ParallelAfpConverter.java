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

package com.mgz.xml;

import com.mgz.afp.base.StructuredField;
import com.mgz.afp.parser.AFPParser;
import com.mgz.afp.parser.AFPParserConfiguration;
import com.mgz.afp.parser.AFPScanner;
import com.mgz.util.MnemonicPerformanceMonitor;
import com.mgz.util.PTXPerformanceMonitor;
import java.nio.charset.StandardCharsets;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousFileChannel;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

/**
 * Coords parallel conversion of a single AFP file to XML.
 */
public class ParallelAfpConverter {

  private final File inputFile;
  private final OutputStream outputStream;
  private final String xpathExpression;
  private final boolean useJackson;
  private final boolean ptxDebug;
  private final int threadCount;

  public ParallelAfpConverter(File inputFile, OutputStream outputStream, String xpathExpression,
      boolean useJackson, boolean ptxDebug, int threadCount) {
    this.inputFile = inputFile;
    this.outputStream = outputStream;
    this.xpathExpression = xpathExpression;
    this.useJackson = useJackson;
    this.ptxDebug = ptxDebug;
    this.threadCount = threadCount > 0 ? threadCount : Runtime.getRuntime().availableProcessors();
  }

  public void convert() throws Exception {
    AFPParserConfiguration config = new AFPParserConfiguration();
    config.setAFPFile(inputFile);
    config.setPtxDebug(ptxDebug);

    ByteBuffer buffer = config.getByteBuffer();
    AsynchronousFileChannel asyncChannel = (buffer == null) ? config.getAsyncFileChannel() : null;

    AFPScanner scanner = (buffer != null) ? new AFPScanner(buffer) : new AFPScanner(asyncChannel);
    List<Long> pageOffsets = scanner.findPageBoundariesParallel();

    long fileSize = (buffer != null) ? buffer.limit() : asyncChannel.size();
    long firstPageOffset = pageOffsets.isEmpty() ? fileSize : pageOffsets.get(0);

    // 1. Write XML Header and Start Root
    writeXmlHeader();

    // 2. Parse and write Preamble sequentially
    if (firstPageOffset > 0) {
      AFPParser preambleParser = new AFPParser(config);
      while (preambleParser.getCountReadByte() < firstPageOffset) {
        StructuredField sf = preambleParser.parseNextSF();
        if (sf == null) break;
        try (AutoCloseable writer = createWriter(outputStream, true)) {
          writeField(writer, sf);
        }
        sf.release();
      }
    }

    if (!pageOffsets.isEmpty()) {
      // 3. Dispatch Page Tasks
      OrderedResultCollector collector = new OrderedResultCollector(outputStream, threadCount * 2);
      ExecutorService executor = Executors.newFixedThreadPool(threadCount);
      AtomicReference<Throwable> error = new AtomicReference<>();

      try {
        for (int i = 0; i < pageOffsets.size(); i++) {
          final long start = pageOffsets.get(i);
          final long end = (i + 1 < pageOffsets.size()) ? pageOffsets.get(i + 1) : fileSize;
          final long seq = i;

          executor.submit(() -> {
            try {
              if (error.get() != null) return;
              byte[] xmlFragment = processPage(start, end, config);
              collector.addFragment(seq, xmlFragment);
            } catch (Throwable t) {
              error.compareAndSet(null, t);
            } finally {
              MnemonicPerformanceMonitor.merge();
              PTXPerformanceMonitor.merge();
            }
          });
        }
      } finally {
        executor.shutdown();
        if (!executor.awaitTermination(1, TimeUnit.HOURS)) {
          throw new IOException("Parallel processing timed out");
        }
      }

      if (error.get() != null) {
        throw new Exception("Error during parallel conversion", error.get());
      }
    }

    // 4. Write XML Footer
    writeXmlFooter();
  }

  private void writeXmlHeader() throws IOException {
    if (xpathExpression == null) {
      outputStream.write("<?xml version='1.0' encoding='UTF-8'?>\n".getBytes(StandardCharsets.UTF_8));
      outputStream.write("<AFPDocument xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\">\n".getBytes(StandardCharsets.UTF_8));
    }
  }

  private void writeXmlFooter() throws IOException {
    if (xpathExpression == null) {
      outputStream.write("</AFPDocument>\n".getBytes(StandardCharsets.UTF_8));
    }
    outputStream.flush();
  }

  private byte[] processPage(long start, long end, AFPParserConfiguration baseConfig) throws Exception {
    AFPParserConfiguration taskConfig = baseConfig.clone();
    int pageSize = (int) (end - start);

    // Use the same buffer if available, otherwise read part
    if (taskConfig.getByteBuffer() == null && taskConfig.getAsyncFileChannel() != null) {
      ByteBuffer pageData = ByteBuffer.allocateDirect(pageSize);
      taskConfig.getAsyncFileChannel().read(pageData, start).get();
      pageData.flip();
      taskConfig.setByteBuffer(pageData);
    }

    AFPParser parser = new AFPParser(taskConfig);
    parser.setNrOfBytesRead(start);

    ByteArrayOutputStream baos = new ByteArrayOutputStream();
    StructuredField sf;
    while ((sf = parser.parseNextSF()) != null) {
      try (AutoCloseable writer = createWriter(baos, true)) {
        writeField(writer, sf);
      }
      sf.release();
      if (parser.getCountReadByte() >= end) break;
    }
    return baos.toByteArray();
  }

  private AutoCloseable createWriter(OutputStream os, boolean fragmentMode) throws Exception {
    return useJackson
        ? new AfpJacksonXmlWriter(os, xpathExpression, fragmentMode)
        : new AfpStreamingXmlWriter(os, xpathExpression, fragmentMode);
  }

  private void writeField(AutoCloseable writer, StructuredField sf) throws Exception {
    if (writer instanceof AfpJacksonXmlWriter jw) {
      jw.writeField(sf);
    } else if (writer instanceof AfpStreamingXmlWriter sw) {
      sw.writeField(sf);
    }
  }
}
