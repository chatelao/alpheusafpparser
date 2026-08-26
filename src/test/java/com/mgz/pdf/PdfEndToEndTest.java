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
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.nio.file.Path;
import java.util.Map;

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
      // PDFBox loadPDF already performs basic structural validation.
      // If it returns without exception, the PDF is considered valid enough to be loaded.
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

  @Test
  public void testAfpToPdfWithWindowOption() throws Exception {
    File inputFile = new File("src/test/resources/afp/perf_ptx.afp");
    File outputFile = tempDir.resolve("output_window.pdf").toFile();

    String[] args = {
        "-f", "pdf",
        "-w",
        inputFile.getAbsolutePath(),
        outputFile.getAbsolutePath()
    };

    int result = Afp2Xml.execute(args);
    assertEquals(0, result, "Conversion with -w option should succeed");
    assertTrue(outputFile.exists(), "Output PDF with window overlay should exist");

    try (PDDocument document = Loader.loadPDF(outputFile)) {
      assertTrue(document.getNumberOfPages() >= 1, "Should have at least one page");

      PDFRenderer renderer = new PDFRenderer(document);
      BufferedImage image = renderer.renderImage(0);
      assertNotNull(image, "Rendered image should not be null");

      // Verify presence of red pixels (window border) in rendered image
      boolean hasRedPixel = false;
      for (int x = 0; x < image.getWidth(); x++) {
        for (int y = 0; y < image.getHeight(); y++) {
          int rgb = image.getRGB(x, y);
          int red = (rgb >> 16) & 0xFF;
          int green = (rgb >> 8) & 0xFF;
          int blue = rgb & 0xFF;
          // Red pixels should have high red component and low green/blue
          if (red > 200 && green < 50 && blue < 50) {
            hasRedPixel = true;
            break;
          }
        }
        if (hasRedPixel) {
          break;
        }
      }
      assertTrue(hasRedPixel, "Rendered PDF image should contain red pixels from window overlay");
    }
  }

  @Test
  public void testPdfHandlerFactoryConfigurableWindowOptions() throws Exception {
    PdfHandlerFactory factory = new PdfHandlerFactory();
    factory.configure(Map.of(
        "window-left", "25.5",
        "window-width", "110.0",
        "window-top", "40.0",
        "window-height", "60.0"
    ));

    ByteArrayOutputStream baos = new ByteArrayOutputStream();
    try (com.mgz.afp.base.handler.StructuredFieldHandler handler = factory.createHandler(baos, false)) {
      assertTrue(handler instanceof PdfHandler, "Handler should be instance of PdfHandler");
      PdfHandler pdfHandler = (PdfHandler) handler;
      assertTrue(pdfHandler.isDrawWindow(), "drawWindow should be enabled");
      assertEquals(25.5, pdfHandler.getWindowLeft(), 0.001);
      assertEquals(110.0, pdfHandler.getWindowWidth(), 0.001);
      assertEquals(40.0, pdfHandler.getWindowTop(), 0.001);
      assertEquals(60.0, pdfHandler.getWindowHeight(), 0.001);
    }
  }
}
