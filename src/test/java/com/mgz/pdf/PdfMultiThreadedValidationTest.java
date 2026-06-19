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

package com.mgz.pdf;

import com.mgz.cli.Afp2Xml;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import org.apache.pdfbox.Loader;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

/**
 * Validates that concurrent PDF generation produces valid, non-corrupt PDF files.
 */
public class PdfMultiThreadedValidationTest {

  @TempDir
  Path tempDir;

  @Test
  public void testParallelPdfConversion() throws Exception {
    File inputFile = new File("src/test/resources/afp/perf_ptx.afp");
    int numThreads = 8;
    int iterations = 20;
    ExecutorService executor = Executors.newFixedThreadPool(numThreads);
    List<Future<Void>> futures = new ArrayList<>();

    for (int i = 0; i < iterations; i++) {
      final int index = i;
      futures.add(executor.submit(() -> {
        File outputFile = tempDir.resolve("output_" + index + ".pdf").toFile();
        String[] args = {
            "-f", "pdf",
            inputFile.getAbsolutePath(),
            outputFile.getAbsolutePath()
        };
        int result = Afp2Xml.execute(args);
        if (result != 0) {
          throw new RuntimeException("Conversion failed for iteration " + index);
        }

        validatePdf(outputFile);
        return null;
      }));
    }

    try {
      for (Future<Void> future : futures) {
        future.get(1, TimeUnit.MINUTES);
      }
    } catch (Exception e) {
      fail("Parallel conversion failed or timed out: " + e.getMessage());
    } finally {
      executor.shutdownNow();
    }
  }

  private void validatePdf(File pdfFile) {
    try (PDDocument document = Loader.loadPDF(pdfFile)) {
      int pageCount = document.getNumberOfPages();
      if (pageCount <= 0) {
        fail("Generated PDF has no pages: " + pdfFile.getName());
      }
    } catch (IOException e) {
      fail("Generated PDF is corrupt: " + pdfFile.getName() + " - " + e.getMessage());
    }
  }
}
