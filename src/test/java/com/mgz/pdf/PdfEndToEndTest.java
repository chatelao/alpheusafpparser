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

      // Verify presence of red pixels and precise position of window borders
      // Default rendering in PDFBox renderImage(0) uses 72 DPI (1 point = 1 pixel).
      // Outer window: left = 97.5mm, top = 50.0mm, width = 119.0mm, height = 64.0mm
      // Inner window: width = 95.0mm, height = 40.0mm, centered in outer window
      double mmToPx = 72.0 / 25.4;
      int expectedOuterLeftPx = (int) Math.round(97.5 * mmToPx);
      int expectedOuterTopPx = (int) Math.round(50.0 * mmToPx);
      int expectedOuterRightPx = (int) Math.round((97.5 + 119.0) * mmToPx);
      int expectedOuterBottomPx = (int) Math.round((50.0 + 64.0) * mmToPx);

      double innerLeftMm = 97.5 + (119.0 - 95.0) / 2.0; // 109.5mm
      double innerTopMm = 50.0 + (64.0 - 40.0) / 2.0;   // 62.0mm
      int expectedInnerLeftPx = (int) Math.round(innerLeftMm * mmToPx);
      int expectedInnerTopPx = (int) Math.round(innerTopMm * mmToPx);

      // Print debug info if failed
      int checkX = expectedOuterLeftPx + 20;
      int checkY = expectedOuterTopPx;
      boolean hasOuterTopBorderRed = isRedPixelNear(image, checkX, checkY, 5);
      boolean hasOuterLeftBorderRed = isRedPixelNear(image, expectedOuterLeftPx, expectedOuterTopPx + 20, 5);
      boolean hasInnerTopBorderRed = isRedPixelNear(image, expectedInnerLeftPx + 20, expectedInnerTopPx, 5);
      boolean hasInnerLeftBorderRed = isRedPixelNear(image, expectedInnerLeftPx, expectedInnerTopPx + 20, 5);

      if (!hasOuterTopBorderRed) {
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("Failed outer top border check at x=%d, y=%d (mmToPx=%.4f). Sample pixels nearby:\n", checkX, checkY, mmToPx));
        for (int dy = -5; dy <= 5; dy++) {
          for (int dx = -5; dx <= 5; dx++) {
            int px = checkX + dx;
            int py = checkY + dy;
            if (px >= 0 && px < image.getWidth() && py >= 0 && py < image.getHeight()) {
              int rgb = image.getRGB(px, py);
              sb.append(String.format("[%d,%d: R=%d G=%d B=%d] ", dx, dy, (rgb>>16)&0xFF, (rgb>>8)&0xFF, rgb&0xFF));
            }
          }
          sb.append("\n");
        }
        System.err.println(sb.toString());
      }

      assertTrue(hasOuterTopBorderRed, "Outer window top border should have red pixels at ~50mm top offset");
      assertTrue(hasOuterLeftBorderRed, "Outer window left border should have red pixels at ~97.5mm left position");
      assertTrue(hasInnerTopBorderRed, "Inner 95x40mm window top border should have red pixels centered in outer window");
      assertTrue(hasInnerLeftBorderRed, "Inner 95x40mm window left border should have red pixels centered in outer window");
    }
  }

  private boolean isRedPixelNear(BufferedImage image, int targetX, int targetY, int tolerance) {
    for (int dx = -tolerance; dx <= tolerance; dx++) {
      for (int dy = -tolerance; dy <= tolerance; dy++) {
        int x = targetX + dx;
        int y = targetY + dy;
        if (x >= 0 && x < image.getWidth() && y >= 0 && y < image.getHeight()) {
          int rgb = image.getRGB(x, y);
          int red = (rgb >> 16) & 0xFF;
          int green = (rgb >> 8) & 0xFF;
          int blue = rgb & 0xFF;
          // Red window lines in PDFBox rendering under antialiasing or varying headless font/color profiles
          if (red > 140 && (red - green) > 25 && (red - blue) > 25) {
            return true;
          }
        }
      }
    }
    return false;
  }

  @Test
  public void testPdfHandlerFactoryDefaultWindowOptions() throws Exception {
    PdfHandlerFactory factory = new PdfHandlerFactory();
    factory.configure(Map.of("window", "true"));

    ByteArrayOutputStream baos = new ByteArrayOutputStream();
    try (com.mgz.afp.base.handler.StructuredFieldHandler handler = factory.createHandler(baos, false)) {
      assertTrue(handler instanceof PdfHandler, "Handler should be instance of PdfHandler");
      PdfHandler pdfHandler = (PdfHandler) handler;
      assertTrue(pdfHandler.isDrawWindow(), "drawWindow should be enabled");
      assertEquals(97.5, pdfHandler.getWindowLeft(), 0.001);
      assertEquals(119.0, pdfHandler.getWindowWidth(), 0.001);
      assertEquals(50.0, pdfHandler.getWindowTop(), 0.001);
      assertEquals(64.0, pdfHandler.getWindowHeight(), 0.001);
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
