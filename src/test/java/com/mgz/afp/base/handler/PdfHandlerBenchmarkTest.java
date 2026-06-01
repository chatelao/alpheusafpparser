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

package com.mgz.afp.base.handler;

import com.mgz.afp.parser.AFPParserConfiguration;
import com.mgz.afp.parser.ParallelAfpConverter;
import com.mgz.pdf.PdfHandlerFactory;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Benchmark test for the parsing stage using a stub {@link com.mgz.pdf.PdfHandler}.
 */
public class PdfHandlerBenchmarkTest {

  @TempDir
  Path tempDir;

  @Test
  public void testPdfParsingThroughput() throws Exception {
    File afpFile = new File("test/dir/Chapter_1.afp");
    if (!afpFile.exists()) {
      System.out.println("Skipping benchmark: test/dir/Chapter_1.afp not found.");
      return;
    }

    Path outputPath = tempDir.resolve("output.pdf");
    HandlerFactory factory = new PdfHandlerFactory();

    AFPParserConfiguration config = new AFPParserConfiguration();
    config.setAFPFile(afpFile);

    long startTime = System.nanoTime();
    try (OutputStream os = new FileOutputStream(outputPath.toFile())) {
      ParallelAfpConverter converter = new ParallelAfpConverter(config, 0, factory);
      converter.convert(os);
    }
    long endTime = System.nanoTime();

    double durationSeconds = (endTime - startTime) / 1_000_000_000.0;
    long fileSize = afpFile.length();
    double throughputMBs = (fileSize / (1024.0 * 1024.0)) / durationSeconds;

    System.out.printf("Parsing benchmark (Stub PDF): %.3f seconds, %.2f MB/s%n", durationSeconds, throughputMBs);
    assertTrue(Files.exists(outputPath), "Output file should be created (even if empty)");
  }
}
