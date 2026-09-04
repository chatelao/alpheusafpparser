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

      // Verify presence of red/blue/green pixels and precise position of window borders
      // Default rendering in PDFBox renderImage(0) uses 72 DPI (1 point = 1 pixel).
      // Blue window (95x40mm): left = 102.5mm, top = 42.0mm
      // Red window (114x54mm): centered around blue box (left = 93.0mm, top = 35.0mm)
      // Green window (76x26mm): centered inside blue box (left = 112.0mm, top = 49.0mm)
      double mmToPx = 72.0 / 25.4;
      int expectedOuterLeftPx = (int) Math.round(93.0 * mmToPx);

      double inner1LeftMm = 102.5;
      double inner1TopMm = 42.0;
      int expectedInner1LeftPx = (int) Math.round(inner1LeftMm * mmToPx);
      int expectedInner1TopPx = (int) Math.round(inner1TopMm * mmToPx);

      // Concentric alignment relative to blue box center (150mm, 62mm):
      double outerTopMm = 35.0;
      int expectedOuterTopPxNew = (int) Math.round(outerTopMm * mmToPx);

      double inner2LeftMm = 112.0;
      double inner2TopMm = 49.0;
      int expectedInner2LeftPx = (int) Math.round(inner2LeftMm * mmToPx);
      int expectedInner2TopPx = (int) Math.round(inner2TopMm * mmToPx);

      boolean hasOuterTopBorderRed = isRedPixelNear(image, expectedOuterLeftPx + 20, expectedOuterTopPxNew, 5);
      boolean hasOuterLeftBorderRed = isRedPixelNear(image, expectedOuterLeftPx, expectedOuterTopPxNew + 20, 5);
      boolean hasInner1TopBorderBlue = isBluePixelNear(image, expectedInner1LeftPx + 20, expectedInner1TopPx, 5);
      boolean hasInner1LeftBorderBlue = isBluePixelNear(image, expectedInner1LeftPx, expectedInner1TopPx + 20, 5);
      boolean hasInner2TopBorderGreen = isGreenPixelNear(image, expectedInner2LeftPx + 20, expectedInner2TopPx, 5);
      boolean hasInner2LeftBorderGreen = isGreenPixelNear(image, expectedInner2LeftPx, expectedInner2TopPx + 20, 5);

      // Pink outer box (119x72mm): left = 96.0mm, top = 24.0mm (shifted 5.5mm right, 2.0mm up; 13.5mm above Mauve top)
      int expectedPinkLeftPx = (int) Math.round(96.0 * mmToPx);
      int expectedPinkTopPx = (int) Math.round(24.0 * mmToPx);

      // Mauve middle box (100x45mm): left = 105.5mm, top = 37.5mm (shifted 5.5mm right, 2.0mm up)
      int expectedMauveLeftPx = (int) Math.round(105.5 * mmToPx);
      int expectedMauveTopPx = (int) Math.round(37.5 * mmToPx);

      // Purple inner box (81x18mm): left = 115.0mm, top = 51.0mm (shifted 5.5mm right, 2.0mm up; 13.5mm below Mauve top)
      int expectedPurpleLeftPx = (int) Math.round(115.0 * mmToPx);
      int expectedPurpleTopPx = (int) Math.round(51.0 * mmToPx);

      boolean hasPinkOuterTopBorder = isPinkPixelNear(image, expectedPinkLeftPx + 20, expectedPinkTopPx, 5);
      boolean hasMauveMiddleTopBorder = isMauvePixelNear(image, expectedMauveLeftPx + 20, expectedMauveTopPx, 5);
      boolean hasPurpleInnerTopBorder = isPurplePixelNear(image, expectedPurpleLeftPx + 20, expectedPurpleTopPx, 5);

      assertTrue(hasOuterTopBorderRed, "Outer window top border should have red pixels at ~35mm top offset");
      assertTrue(hasOuterLeftBorderRed, "Outer window left border should have red pixels at ~93.0mm left position");
      assertTrue(hasInner1TopBorderBlue, "Inner 95x40mm window top border should have blue pixels");
      assertTrue(hasInner1LeftBorderBlue, "Inner 95x40mm window left border should have blue pixels");
      assertTrue(hasInner2TopBorderGreen, "Inner 76x26mm window top border should have green pixels centered in blue window");
      assertTrue(hasInner2LeftBorderGreen, "Inner 76x26mm window left border should have green pixels centered in blue window");
      assertTrue(hasPinkOuterTopBorder, "Pink outer window border should have pink pixels");
      assertTrue(hasMauveMiddleTopBorder, "Mauve middle 100x45mm window border should have mauve pixels");
      assertTrue(hasPurpleInnerTopBorder, "Purple inner window border should have purple pixels");
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
          if (red > 140 && (red - green) > 25 && (red - blue) > 25) {
            return true;
          }
        }
      }
    }
    return false;
  }

  private boolean isPinkPixelNear(BufferedImage image, int targetX, int targetY, int tolerance) {
    for (int dx = -tolerance; dx <= tolerance; dx++) {
      for (int dy = -tolerance; dy <= tolerance; dy++) {
        int x = targetX + dx;
        int y = targetY + dy;
        if (x >= 0 && x < image.getWidth() && y >= 0 && y < image.getHeight()) {
          int rgb = image.getRGB(x, y);
          int red = (rgb >> 16) & 0xFF;
          int green = (rgb >> 8) & 0xFF;
          int blue = rgb & 0xFF;
          if (red > 200 && green > 150 && blue > 170) {
            return true;
          }
        }
      }
    }
    return false;
  }

  private boolean isMauvePixelNear(BufferedImage image, int targetX, int targetY, int tolerance) {
    for (int dx = -tolerance; dx <= tolerance; dx++) {
      for (int dy = -tolerance; dy <= tolerance; dy++) {
        int x = targetX + dx;
        int y = targetY + dy;
        if (x >= 0 && x < image.getWidth() && y >= 0 && y < image.getHeight()) {
          int rgb = image.getRGB(x, y);
          int red = (rgb >> 16) & 0xFF;
          int green = (rgb >> 8) & 0xFF;
          int blue = rgb & 0xFF;
          if (red > 180 && green > 130 && blue > 200) {
            return true;
          }
        }
      }
    }
    return false;
  }

  private boolean isPurplePixelNear(BufferedImage image, int targetX, int targetY, int tolerance) {
    for (int dx = -tolerance; dx <= tolerance; dx++) {
      for (int dy = -tolerance; dy <= tolerance; dy++) {
        int x = targetX + dx;
        int y = targetY + dy;
        if (x >= 0 && x < image.getWidth() && y >= 0 && y < image.getHeight()) {
          int rgb = image.getRGB(x, y);
          int red = (rgb >> 16) & 0xFF;
          int green = (rgb >> 8) & 0xFF;
          int blue = rgb & 0xFF;
          if (red > 80 && green < 60 && blue > 80) {
            return true;
          }
        }
      }
    }
    return false;
  }

  private boolean isBluePixelNear(BufferedImage image, int targetX, int targetY, int tolerance) {
    for (int dx = -tolerance; dx <= tolerance; dx++) {
      for (int dy = -tolerance; dy <= tolerance; dy++) {
        int x = targetX + dx;
        int y = targetY + dy;
        if (x >= 0 && x < image.getWidth() && y >= 0 && y < image.getHeight()) {
          int rgb = image.getRGB(x, y);
          int red = (rgb >> 16) & 0xFF;
          int green = (rgb >> 8) & 0xFF;
          int blue = rgb & 0xFF;
          if (blue > 140 && (blue - red) > 25 && (blue - green) > 25) {
            return true;
          }
        }
      }
    }
    return false;
  }

  private boolean isGreenPixelNear(BufferedImage image, int targetX, int targetY, int tolerance) {
    for (int dx = -tolerance; dx <= tolerance; dx++) {
      for (int dy = -tolerance; dy <= tolerance; dy++) {
        int x = targetX + dx;
        int y = targetY + dy;
        if (x >= 0 && x < image.getWidth() && y >= 0 && y < image.getHeight()) {
          int rgb = image.getRGB(x, y);
          int red = (rgb >> 16) & 0xFF;
          int green = (rgb >> 8) & 0xFF;
          int blue = rgb & 0xFF;
          if (green > 140 && (green - red) > 25 && (green - blue) > 25) {
            return true;
          }
        }
      }
    }
    return false;
  }

  @Test
  public void testWindowOverlayOnlyOnFirstPageMultiPage() throws Exception {
    File outputFile = tempDir.resolve("output_multipage_window.pdf").toFile();

    try (java.io.FileOutputStream fos = new java.io.FileOutputStream(outputFile);
         PdfHandler handler = new PdfHandler(fos)) {
      handler.setDrawWindow(true);

      com.mgz.afp.modca.BPG_BeginPage bpg1 = new com.mgz.afp.modca.BPG_BeginPage();
      bpg1.setStructuredFieldIntroducer(createSfi(com.mgz.afp.enums.SFTypeID.BPG_BeginPage));
      handler.handle(bpg1);

      com.mgz.afp.modca.EPG_EndPage epg1 = new com.mgz.afp.modca.EPG_EndPage();
      epg1.setStructuredFieldIntroducer(createSfi(com.mgz.afp.enums.SFTypeID.EPG_EndPage));
      handler.handle(epg1);

      com.mgz.afp.modca.BPG_BeginPage bpg2 = new com.mgz.afp.modca.BPG_BeginPage();
      bpg2.setStructuredFieldIntroducer(createSfi(com.mgz.afp.enums.SFTypeID.BPG_BeginPage));
      handler.handle(bpg2);

      com.mgz.afp.modca.EPG_EndPage epg2 = new com.mgz.afp.modca.EPG_EndPage();
      epg2.setStructuredFieldIntroducer(createSfi(com.mgz.afp.enums.SFTypeID.EPG_EndPage));
      handler.handle(epg2);
    }

    try (PDDocument document = Loader.loadPDF(outputFile)) {
      assertEquals(2, document.getNumberOfPages(), "PDF should contain 2 pages");

      PDFRenderer renderer = new PDFRenderer(document);
      BufferedImage page1Image = renderer.renderImage(0);
      BufferedImage page2Image = renderer.renderImage(1);

      double mmToPx = 72.0 / 25.4;
      int expectedOuterLeftPx = (int) Math.round(93.0 * mmToPx);
      int expectedOuterTopPx = (int) Math.round(35.0 * mmToPx);

      // Verify page 1 contains window overlay
      assertTrue(isRedPixelNear(page1Image, expectedOuterLeftPx + 20, expectedOuterTopPx, 5),
          "Page 1 should contain red window overlay pixels");

      // Verify page 2 does NOT contain window overlay
      org.junit.jupiter.api.Assertions.assertFalse(isRedPixelNear(page2Image, expectedOuterLeftPx + 20, expectedOuterTopPx, 5),
          "Page 2 should NOT contain red window overlay pixels");
    }
  }

  private com.mgz.afp.base.StructuredFieldIntroducer createSfi(com.mgz.afp.enums.SFTypeID typeID) {
    com.mgz.afp.base.StructuredFieldIntroducer sfi = new com.mgz.afp.base.StructuredFieldIntroducer();
    sfi.setSFTypeID(typeID);
    sfi.setFlagByte(java.util.EnumSet.noneOf(com.mgz.afp.enums.SFFlag.class));
    return sfi;
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
      assertEquals(102.5, pdfHandler.getWindowLeft(), 0.001);
      assertEquals(95.0, pdfHandler.getWindowWidth(), 0.001);
      assertEquals(42.0, pdfHandler.getWindowTop(), 0.001);
      assertEquals(40.0, pdfHandler.getWindowHeight(), 0.001);
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

  @Test
  public void testAfpToPdfWithLeftOverlayOption() throws Exception {
    File inputFile = new File("src/test/resources/afp/perf_ptx.afp");
    File outputFile = tempDir.resolve("output_overlay.pdf").toFile();

    String[] args = {
        "-f", "pdf",
        "-o", "3.0",
        inputFile.getAbsolutePath(),
        outputFile.getAbsolutePath()
    };

    int result = Afp2Xml.execute(args);
    assertEquals(0, result, "Conversion with -o 3.0 option should succeed");
    assertTrue(outputFile.exists(), "Output PDF with left overlay should exist");

    try (PDDocument document = Loader.loadPDF(outputFile)) {
      assertTrue(document.getNumberOfPages() >= 1, "Should have at least one page");

      PDFRenderer renderer = new PDFRenderer(document);
      BufferedImage image = renderer.renderImage(0);
      assertNotNull(image, "Rendered image should not be null");

      // Verify light blue pixel near left margin
      // 3.0mm * (72/25.4) ~ 8.5 pixels. Check at x = 4, y = 100.
      double mmToPx = 72.0 / 25.4;
      int testX = (int) Math.round(1.5 * mmToPx);
      int testY = 100;

      boolean hasLightBlue = isLightBluePixelNear(image, testX, testY, 3);
      assertTrue(hasLightBlue, "Left overlay strip should have light blue pixels near left edge");

      // Verify orange DataMatrix code at 60mm from top, 19mm from right border
      int dmCenterX = (int) Math.round(image.getWidth() - (19.0 + 6.0) * mmToPx);
      int dmCenterY = (int) Math.round((60.0 + 6.0) * mmToPx);

      boolean hasOrange = isOrangePixelNear(image, dmCenterX, dmCenterY, 15);
      assertTrue(hasOrange, "Overlay should render orange DataMatrix code at 60mm top, 19mm right");
    }
  }

  private boolean isOrangePixelNear(BufferedImage image, int targetX, int targetY, int tolerance) {
    for (int dx = -tolerance; dx <= tolerance; dx++) {
      for (int dy = -tolerance; dy <= tolerance; dy++) {
        int x = targetX + dx;
        int y = targetY + dy;
        if (x >= 0 && x < image.getWidth() && y >= 0 && y < image.getHeight()) {
          int rgb = image.getRGB(x, y);
          int red = (rgb >> 16) & 0xFF;
          int green = (rgb >> 8) & 0xFF;
          int blue = rgb & 0xFF;
          // Orange: RGB (255, 140, 0)
          if (red > 200 && green > 100 && green < 180 && blue < 50) {
            return true;
          }
        }
      }
    }
    return false;
  }

  private boolean isLightBluePixelNear(BufferedImage image, int targetX, int targetY, int tolerance) {
    for (int dx = -tolerance; dx <= tolerance; dx++) {
      for (int dy = -tolerance; dy <= tolerance; dy++) {
        int x = targetX + dx;
        int y = targetY + dy;
        if (x >= 0 && x < image.getWidth() && y >= 0 && y < image.getHeight()) {
          int rgb = image.getRGB(x, y);
          int red = (rgb >> 16) & 0xFF;
          int green = (rgb >> 8) & 0xFF;
          int blue = rgb & 0xFF;
          // Light Blue: RGB (173, 216, 230)
          if (red > 150 && green > 190 && blue > 210) {
            return true;
          }
        }
      }
    }
    return false;
  }

  @Test
  public void testPdfHandlerFactoryDefaultLeftOverlayOptions() throws Exception {
    PdfHandlerFactory factory = new PdfHandlerFactory();
    factory.configure(Map.of("overlay", "true"));

    ByteArrayOutputStream baos = new ByteArrayOutputStream();
    try (com.mgz.afp.base.handler.StructuredFieldHandler handler = factory.createHandler(baos, false)) {
      assertTrue(handler instanceof PdfHandler, "Handler should be instance of PdfHandler");
      PdfHandler pdfHandler = (PdfHandler) handler;
      assertTrue(pdfHandler.isDrawLeftOverlay(), "drawLeftOverlay should be enabled");
      assertEquals(2.0, pdfHandler.getLeftOverlayWidth(), 0.001);
    }
  }

  @Test
  public void testPdfHandlerFactoryCustomLeftOverlayOptions() throws Exception {
    PdfHandlerFactory factory = new PdfHandlerFactory();
    factory.configure(Map.of("left-overlay-width", "5.5"));

    ByteArrayOutputStream baos = new ByteArrayOutputStream();
    try (com.mgz.afp.base.handler.StructuredFieldHandler handler = factory.createHandler(baos, false)) {
      assertTrue(handler instanceof PdfHandler, "Handler should be instance of PdfHandler");
      PdfHandler pdfHandler = (PdfHandler) handler;
      assertTrue(pdfHandler.isDrawLeftOverlay(), "drawLeftOverlay should be enabled");
      assertEquals(5.5, pdfHandler.getLeftOverlayWidth(), 0.001);
    }
  }
}
