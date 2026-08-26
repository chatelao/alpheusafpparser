package com.mgz.pdf;

import com.mgz.afp.enums.AFPUnitBase;
import com.mgz.afp.modca.BPG_BeginPage;
import com.mgz.afp.modca.EPG_EndPage;
import com.mgz.afp.modca.PGD_PageDescriptor;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class PdfWindowOverlayTest {

  @TempDir
  Path tempDir;

  @Test
  public void testMeasureWindowOverlayOuterAndInnerDefaults() throws Exception {
    File pdfFile = tempDir.resolve("test_window_default.pdf").toFile();

    try (FileOutputStream fos = new FileOutputStream(pdfFile);
         PdfHandler handler = new PdfHandler(fos)) {
      handler.setDrawWindow(true);

      // Create an A4 page (210mm x 297mm)
      PGD_PageDescriptor pgd = new PGD_PageDescriptor();
      pgd.setxUnitBase(AFPUnitBase.Inches10);
      pgd.setyUnitBase(AFPUnitBase.Inches10);
      pgd.setxUnitsPerUnitBase((short) 1440);
      pgd.setyUnitsPerUnitBase((short) 1440);
      pgd.setxSize(11905); // ~210mm in 1/1440 inch units
      pgd.setySize(16838); // ~297mm in 1/1440 inch units

      BPG_BeginPage bpg = new BPG_BeginPage();
      bpg.setName("P0000001");
      EPG_EndPage epg = new EPG_EndPage();
      epg.setName("P0000001");

      handler.handle(bpg);
      handler.handle(pgd);
      handler.handle(epg);
    }

    // Rasterize at 254 DPI -> 10 pixels per mm
    float dpi = 254.0f;
    BufferedImage img = PdfVerificationUtils.rasterize(pdfFile, dpi);

    // Collect all red pixels
    List<int[]> redPixels = new ArrayList<>();
    for (int y = 0; y < img.getHeight(); y++) {
      for (int x = 0; x < img.getWidth(); x++) {
        int rgb = img.getRGB(x, y);
        int r = (rgb >> 16) & 0xFF;
        int g = (rgb >> 8) & 0xFF;
        int b = rgb & 0xFF;
        if (r > 200 && g < 50 && b < 50) {
          redPixels.add(new int[]{x, y});
        }
      }
    }

    assertTrue(!redPixels.isEmpty(), "Should find red pixels from window overlay");

    // Separate outer and inner rectangles
    int outerMinX = Integer.MAX_VALUE, outerMaxX = Integer.MIN_VALUE;
    int outerMinY = Integer.MAX_VALUE, outerMaxY = Integer.MIN_VALUE;
    for (int[] p : redPixels) {
      if (p[0] < outerMinX) outerMinX = p[0];
      if (p[0] > outerMaxX) outerMaxX = p[0];
      if (p[1] < outerMinY) outerMinY = p[1];
      if (p[1] > outerMaxY) outerMaxY = p[1];
    }

    double outerMinXmm = outerMinX / 10.0;
    double outerMaxXmm = outerMaxX / 10.0;
    double outerMinYmm = outerMinY / 10.0;
    double outerMaxYmm = outerMaxY / 10.0;
    double outerWidthMm = (outerMaxX - outerMinX) / 10.0;
    double outerHeightMm = (outerMaxY - outerMinY) / 10.0;

    // Assert Outer Window bounds
    assertEquals(20.0, outerMinXmm, 0.5, "Outer window left margin should be ~20.0 mm");
    assertEquals(139.0, outerMaxXmm, 0.5, "Outer window right position should be ~139.0 mm");
    assertEquals(50.0, outerMinYmm, 0.5, "Outer window top margin should be ~50.0 mm");
    assertEquals(114.0, outerMaxYmm, 0.5, "Outer window bottom position should be ~114.0 mm");
    assertEquals(119.0, outerWidthMm, 0.5, "Outer window width should be ~119.0 mm");
    assertEquals(64.0, outerHeightMm, 0.5, "Outer window height should be ~64.0 mm");

    // Inner rectangle red pixels lie strictly inside outer boundary
    int innerMinX = Integer.MAX_VALUE, innerMaxX = Integer.MIN_VALUE;
    int innerMinY = Integer.MAX_VALUE, innerMaxY = Integer.MIN_VALUE;
    int innerCount = 0;
    for (int[] p : redPixels) {
      if (p[0] > outerMinX + 15 && p[0] < outerMaxX - 15 &&
          p[1] > outerMinY + 15 && p[1] < outerMaxY - 15) {
        innerCount++;
        if (p[0] < innerMinX) innerMinX = p[0];
        if (p[0] > innerMaxX) innerMaxX = p[0];
        if (p[1] < innerMinY) innerMinY = p[1];
        if (p[1] > innerMaxY) innerMaxY = p[1];
      }
    }

    assertTrue(innerCount > 0, "Should detect inner window overlay red pixels");

    double innerWidthMm = (innerMaxX - innerMinX) / 10.0;
    double innerHeightMm = (innerMaxY - innerMinY) / 10.0;
    double gapLeft = (innerMinX - outerMinX) / 10.0;
    double gapRight = (outerMaxX - innerMaxX) / 10.0;
    double gapTop = (innerMinY - outerMinY) / 10.0;
    double gapBottom = (outerMaxY - innerMaxY) / 10.0;

    assertEquals(100.0, innerWidthMm, 0.5, "Inner window width should be ~100.0 mm");
    assertEquals(50.0, innerHeightMm, 0.5, "Inner window height should be ~50.0 mm");
    assertEquals(9.5, gapLeft, 0.5, "Left gap between outer and inner window should be ~9.5 mm");
    assertEquals(9.5, gapRight, 0.5, "Right gap between outer and inner window should be ~9.5 mm");
    assertEquals(7.0, gapTop, 0.5, "Top gap between outer and inner window should be ~7.0 mm");
    assertEquals(7.0, gapBottom, 0.5, "Bottom gap between outer and inner window should be ~7.0 mm");
  }
}
