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
import com.mgz.afp.exceptions.AFPParserException;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * Implements parallel parsing of AFP pages.
 */
public class ParallelPageParser {

  private final AFPParserConfiguration config;

  /**
   * Constructs a ParallelPageParser with the given configuration.
   *
   * @param config the parser configuration
   */
  public ParallelPageParser(AFPParserConfiguration config) {
    this.config = config;
  }

  /**
   * Parses the AFP data in parallel, page by page.
   *
   * @return a list of all parsed structured fields in correct order
   * @throws AFPParserException if parsing fails
   * @throws IOException if I/O fails
   */
  public List<StructuredField> parseParallel() throws AFPParserException, IOException {
    ByteBuffer buffer = config.getByteBuffer();
    if (buffer == null) {
      throw new AFPParserException("MappedByteBuffer is required for parallel parsing.");
    }

    AFPScanner scanner = new AFPScanner(buffer);
    List<Long> pageOffsets = scanner.findPageBoundaries();

    List<StructuredField> allFields = new ArrayList<>();
    long firstPageOffset = pageOffsets.isEmpty() ? buffer.limit() : pageOffsets.get(0);

    // 1. Parse Preamble sequentially to establish global state (Resource Groups, etc.)
    if (firstPageOffset > 0) {
      AFPParser preambleParser = new AFPParser(config);
      while (preambleParser.getCountReadByte() < firstPageOffset) {
        StructuredField sf = preambleParser.parseNextSF();
        if (sf == null) {
          break;
        }
        allFields.add(sf);
      }
    }

    if (pageOffsets.isEmpty()) {
      return allFields;
    }

    // 2. Initialize Thread Pool
    int numThreads = Runtime.getRuntime().availableProcessors();
    ExecutorService executor = Executors.newFixedThreadPool(numThreads);
    List<Future<List<StructuredField>>> futures = new ArrayList<>();

    try {
      // 3. Dispatch Page Tasks
      for (int i = 0; i < pageOffsets.size(); i++) {
        long start = pageOffsets.get(i);
        long end = (i + 1 < pageOffsets.size()) ? pageOffsets.get(i + 1) : buffer.limit();

        // Clone config for each task to avoid interference on page-local state,
        // but shared state like codedFontLocalIdToCharsetMap (ConcurrentHashMap) remains shared.
        AFPParserConfiguration taskConfig = config.clone();
        futures.add(executor.submit(new PageTask(taskConfig, start, end)));
      }

      // 4. Collect results
      for (Future<List<StructuredField>> future : futures) {
        try {
          allFields.addAll(future.get());
        } catch (Exception e) {
          throw new AFPParserException("Error during parallel page parsing", e);
        }
      }
    } finally {
      executor.shutdown();
    }

    return allFields;
  }

  private static class PageTask implements Callable<List<StructuredField>> {
    private final AFPParserConfiguration taskConfig;
    private final long startOffset;
    private final long endOffset;

    PageTask(AFPParserConfiguration config, long startOffset, long endOffset) {
      this.taskConfig = config;
      this.startOffset = startOffset;
      this.endOffset = endOffset;
    }

    @Override
    public List<StructuredField> call() throws Exception {
      List<StructuredField> fields = new ArrayList<>();
      AFPParser parser = new AFPParser(taskConfig);
      parser.setNrOfBytesRead(startOffset);

      StructuredField sf;
      while ((sf = parser.parseNextSF()) != null) {
        fields.add(sf);
        if (parser.getCountReadByte() >= endOffset) {
          break;
        }
      }
      return fields;
    }
  }
}
