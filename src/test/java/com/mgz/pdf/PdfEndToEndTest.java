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
import org.apache.pdfbox.Loader;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.rendering.PDFRenderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.awt.image.BufferedImage;
import java.io.File;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * End-to-end test for AFP to PDF conversion.
 * Verifies that the generated PDF can be rendered to an image and contains content.
 */
public class PdfEndToEndTest {

  @TempDir
  Path tempDir;

  @Test
  public void testAfpToPdfEndToEnd() throws Exception {
    File inputFile = new File("src/test/resources/afp/perf_ptx.afp");
    File outputFile = tempDir.resolve("output.pdf").toFile();

    // Use CLI to convert AFP to PDF
    String[] args = {
        "-f", "pdf",
        inputFile.getAbsolutePath(),
        outputFile.getAbsolutePath()
    };

    int result = Afp2Xml.execute(args);
    assertEquals(0, result, "Conversion should succeed");
    assertTrue(outputFile.exists(), "Output PDF should exist");
    assertTrue(outputFile.length() > 0, "Output PDF should not be empty");

    // Load PDF and render to image using PDFBox
    try (PDDocument document = Loader.loadPDF(outputFile)) {
      assertTrue(document.getNumberOfPages() >= 1, "Should have at least one page");

      PDFRenderer renderer = new PDFRenderer(document);
      BufferedImage image = renderer.renderImage(0);

      assertNotNull(image, "Rendered image should not be null");
      assertTrue(image.getWidth() > 0, "Image width should be > 0");
      assertTrue(image.getHeight() > 0, "Image height should be > 0");

      // Verify that the page is not completely blank (white)
      boolean hasContent = false;
      for (int x = 0; x < image.getWidth(); x += 5) {
        for (int y = 0; y < image.getHeight(); y += 5) {
          int rgb = image.getRGB(x, y);
          // Check if pixel is not white (0xFFFFFF)
          if ((rgb & 0x00FFFFFF) != 0x00FFFFFF) {
            hasContent = true;
            break;
          }
        }
        if (hasContent) {
          break;
        }
      }
      assertTrue(hasContent, "Rendered image should contain non-white pixels (actual content)");
    }
  }
}
