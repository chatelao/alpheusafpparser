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

import static org.junit.jupiter.api.Assertions.assertTrue;

import com.mgz.afp.enums.AFPColorSpace;
import com.mgz.afp.enums.AFPUnitBase;
import com.mgz.afp.goca.BGR_BeginGraphicsObject;
import com.mgz.afp.goca.EGR_EndGraphicsObject;
import com.mgz.afp.goca.GAD_DrawingOrder.*;
import com.mgz.afp.modca.BPG_BeginPage;
import com.mgz.afp.modca.EPG_EndPage;
import com.mgz.afp.modca.PGD_PageDescriptor;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import org.junit.jupiter.api.Test;

/**
 * Test case for verifying GOCA color-filled, overlapping circles and squares transformed to PDF.
 */
public class PdfOverlappingShapesFillTest {

  @Test
  public void testOverlappingShapesFillAndRasterize() throws Exception {
    File pdfFile = new File("build/test_overlapping_shapes.pdf");
    pdfFile.getParentFile().mkdirs();

    try (FileOutputStream fos = new FileOutputStream(pdfFile)) {
      PdfHandler handler = new PdfHandler(fos);

      // Page Descriptor: 10 x 10 inches at 1440 dpi -> 14400 x 14400 units
      PGD_PageDescriptor pgd = new PGD_PageDescriptor();
      pgd.setxUnitBase(AFPUnitBase.Inches10);
      pgd.setyUnitBase(AFPUnitBase.Inches10);
      pgd.setxUnitsPerUnitBase((short) 14400);
      pgd.setyUnitsPerUnitBase((short) 14400);
      pgd.setxSize(14400);
      pgd.setySize(14400);
      handler.handle(pgd);

      handler.handle(new BPG_BeginPage());
      handler.handle(new BGR_BeginGraphicsObject());

      // Set Arc Parameters for full arcs (radius = 2000 units)
      GSAP_SetArcParameters gsap = new GSAP_SetArcParameters();
      gsap.setArcTransformP((short) 2000);
      gsap.setArcTransformQ((short) 2000);
      gsap.setArcTransformR((short) 0);
      gsap.setArcTransformS((short) 0);
      handler.handleDrawingOrder(gsap);

      // Set Line Width for outline boundaries
      GSLW_SetLineWidth gslw = new GSLW_SetLineWidth();
      gslw.setLineWidth((short) 3);
      handler.handleDrawingOrder(gslw);

      // -------------------------------------------------------------
      // Group A: Shapes inside explicit Area definitions (GBAR ... GEAR)
      // -------------------------------------------------------------

      // 1. Red filled circle centered at (4000, 4000)
      GSPCOL_SetProcessColor red = new GSPCOL_SetProcessColor();
      red.colorSpace = AFPColorSpace.RGB;
      red.colorValue = new byte[]{(byte) 255, 0, 0};
      handler.handleDrawingOrder(red);

      GBAR_BeginArea gbar1 = new GBAR_BeginArea();
      gbar1.setInternalFlags((short) 0x40); // draw boundary lines
      handler.handleDrawingOrder(gbar1);

      GFARC_FullArcAtGivenPosition gfarc1 = new GFARC_FullArcAtGivenPosition();
      gfarc1.setArcCenter(new GOCA_Point((short) 4000, (short) 4000));
      gfarc1.setMultiplierIntegerPortion((short) 1);
      gfarc1.setMultiplierFractionalPortion((short) 0);
      handler.handleDrawingOrder(gfarc1);

      handler.handleDrawingOrder(new GEAR_EndArea());

      // 2. Overlapping Blue filled square from (3000, 3000) to (7000, 7000)
      GSPCOL_SetProcessColor blue = new GSPCOL_SetProcessColor();
      blue.colorSpace = AFPColorSpace.RGB;
      blue.colorValue = new byte[]{0, 0, (byte) 255};
      handler.handleDrawingOrder(blue);

      GBAR_BeginArea gbar2 = new GBAR_BeginArea();
      gbar2.setInternalFlags((short) 0x40);
      handler.handleDrawingOrder(gbar2);

      GBOX_BoxAtGivenPosition gbox1 = new GBOX_BoxAtGivenPosition();
      gbox1.setFirstCorner(new GOCA_Point((short) 3000, (short) 3000));
      gbox1.setDiagonalCorner(new GOCA_Point((short) 7000, (short) 7000));
      handler.handleDrawingOrder(gbox1);

      handler.handleDrawingOrder(new GEAR_EndArea());

      // 3. Overlapping Green filled circle centered at (6000, 6000)
      GSPCOL_SetProcessColor green = new GSPCOL_SetProcessColor();
      green.colorSpace = AFPColorSpace.RGB;
      green.colorValue = new byte[]{0, (byte) 255, 0};
      handler.handleDrawingOrder(green);

      GBAR_BeginArea gbar3 = new GBAR_BeginArea();
      gbar3.setInternalFlags((short) 0x40);
      handler.handleDrawingOrder(gbar3);

      GFARC_FullArcAtGivenPosition gfarc2 = new GFARC_FullArcAtGivenPosition();
      gfarc2.setArcCenter(new GOCA_Point((short) 6000, (short) 6000));
      gfarc2.setMultiplierIntegerPortion((short) 1);
      gfarc2.setMultiplierFractionalPortion((short) 0);
      handler.handleDrawingOrder(gfarc2);

      handler.handleDrawingOrder(new GEAR_EndArea());

      // -------------------------------------------------------------
      // Group B: Standalone closed path shapes outside Area definitions
      // -------------------------------------------------------------

      // 4. Yellow standalone square from (8000, 8000) to (12000, 12000)
      GSPCOL_SetProcessColor yellow = new GSPCOL_SetProcessColor();
      yellow.colorSpace = AFPColorSpace.RGB;
      yellow.colorValue = new byte[]{(byte) 255, (byte) 255, 0};
      handler.handleDrawingOrder(yellow);

      GBOX_BoxAtGivenPosition gbox2 = new GBOX_BoxAtGivenPosition();
      gbox2.setFirstCorner(new GOCA_Point((short) 8000, (short) 8000));
      gbox2.setDiagonalCorner(new GOCA_Point((short) 12000, (short) 12000));
      handler.handleDrawingOrder(gbox2);

      // 5. Cyan standalone circle centered at (10000, 10000), overlapping Yellow square
      GSPCOL_SetProcessColor cyan = new GSPCOL_SetProcessColor();
      cyan.colorSpace = AFPColorSpace.RGB;
      cyan.colorValue = new byte[]{0, (byte) 255, (byte) 255};
      handler.handleDrawingOrder(cyan);

      GFARC_FullArcAtGivenPosition gfarc3 = new GFARC_FullArcAtGivenPosition();
      gfarc3.setArcCenter(new GOCA_Point((short) 10000, (short) 10000));
      gfarc3.setMultiplierIntegerPortion((short) 1);
      gfarc3.setMultiplierFractionalPortion((short) 0);
      handler.handleDrawingOrder(gfarc3);

      handler.handle(new EGR_EndGraphicsObject());
      handler.handle(new EPG_EndPage());
      handler.close();
    }

    // Rasterize PDF to technical screenshot PNG (150 DPI)
    BufferedImage img = PdfVerificationUtils.rasterize(pdfFile, 150);
    File imgFile = new File("build/test_overlapping_shapes.png");
    PdfVerificationUtils.saveImage(img, "png", imgFile);

    // Verify interior pixel colors to guarantee solid surface fills:
    // 1. Non-overlapping region of Red circle at (2800, 2800)
    Color cRed = getPixelColorInNormalizedCoords(img, 2800, 2800, 14400, 14400);
    assertTrue(cRed.getRed() > 200 && cRed.getGreen() < 50 && cRed.getBlue() < 50,
        "Red circle non-overlapping interior must be filled red, got: " + cRed);

    // 2. Overlapping region where Blue square was rendered over Red circle at (4000, 4000)
    Color cBlueOverRed = getPixelColorInNormalizedCoords(img, 4000, 4000, 14400, 14400);
    assertTrue(cBlueOverRed.getBlue() > 200 && cBlueOverRed.getRed() < 50 && cBlueOverRed.getGreen() < 50,
        "Blue square overlapping red circle must be filled blue, got: " + cBlueOverRed);

    // 3. Overlapping region where Green circle was rendered over Blue square at (6000, 6000)
    Color cGreenOverBlue = getPixelColorInNormalizedCoords(img, 6000, 6000, 14400, 14400);
    assertTrue(cGreenOverBlue.getGreen() > 200 && cGreenOverBlue.getRed() < 50 && cGreenOverBlue.getBlue() < 50,
        "Green circle overlapping blue square must be filled green, got: " + cGreenOverBlue);

    // 4. Non-overlapping region of Yellow square at (8500, 8500)
    Color cYellow = getPixelColorInNormalizedCoords(img, 8500, 8500, 14400, 14400);
    assertTrue(cYellow.getRed() > 200 && cYellow.getGreen() > 200 && cYellow.getBlue() < 50,
        "Yellow square non-overlapping interior must be filled yellow, got: " + cYellow);

    // 5. Overlapping region where Cyan circle was rendered over Yellow square at (10000, 10000)
    Color cCyanOverYellow = getPixelColorInNormalizedCoords(img, 10000, 10000, 14400, 14400);
    assertTrue(cCyanOverYellow.getGreen() > 200 && cCyanOverYellow.getBlue() > 200 && cCyanOverYellow.getRed() < 50,
        "Cyan circle overlapping yellow square must be filled cyan, got: " + cCyanOverYellow);
  }

  private Color getPixelColorInNormalizedCoords(BufferedImage img, int afpX, int afpY, int maxAfpX, int maxAfpY) {
    int px = Math.round(((float) afpX / maxAfpX) * img.getWidth());
    int py = Math.round(((float) afpY / maxAfpY) * img.getHeight());
    return new Color(img.getRGB(px, py));
  }
}
