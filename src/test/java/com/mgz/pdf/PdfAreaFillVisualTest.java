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
 * Test case for verifying GOCA area filling of circles and squares in PDF output.
 */
public class PdfAreaFillVisualTest {

  @Test
  public void testFilledCirclesAndSquaresVisual() throws Exception {
    File pdfFile = new File("build/test_shapes.pdf");
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

      // Set arc parameters for full arc (P=1000, Q=1000, R=0, S=0) -> radius 1000 units
      GSAP_SetArcParameters gsap = new GSAP_SetArcParameters();
      gsap.setArcTransformP((short) 1000);
      gsap.setArcTransformQ((short) 1000);
      gsap.setArcTransformR((short) 0);
      gsap.setArcTransformS((short) 0);
      handler.handleDrawingOrder(gsap);

      // 1. Red filled circle inside GBAR
      GSPCOL_SetProcessColor red = new GSPCOL_SetProcessColor();
      red.colorSpace = AFPColorSpace.RGB;
      red.colorValue = new byte[]{(byte) 255, 0, 0};
      handler.handleDrawingOrder(red);

      GBAR_BeginArea gbar1 = new GBAR_BeginArea();
      gbar1.setInternalFlags((short) 0x40); // draw boundary
      handler.handleDrawingOrder(gbar1);

      GFARC_FullArcAtGivenPosition gfarc1 = new GFARC_FullArcAtGivenPosition();
      gfarc1.setArcCenter(new GOCA_Point((short) 2000, (short) 2000));
      gfarc1.setMultiplierIntegerPortion((short) 1);
      gfarc1.setMultiplierFractionalPortion((short) 0);
      handler.handleDrawingOrder(gfarc1);

      handler.handleDrawingOrder(new GEAR_EndArea());

      // 2. Blue filled square inside GBAR
      GSPCOL_SetProcessColor blue = new GSPCOL_SetProcessColor();
      blue.colorSpace = AFPColorSpace.RGB;
      blue.colorValue = new byte[]{0, 0, (byte) 255};
      handler.handleDrawingOrder(blue);

      GBAR_BeginArea gbar2 = new GBAR_BeginArea();
      gbar2.setInternalFlags((short) 0x40);
      handler.handleDrawingOrder(gbar2);

      GBOX_BoxAtGivenPosition gbox1 = new GBOX_BoxAtGivenPosition();
      gbox1.setFirstCorner(new GOCA_Point((short) 4000, (short) 4000));
      gbox1.setDiagonalCorner(new GOCA_Point((short) 7000, (short) 7000));
      handler.handleDrawingOrder(gbox1);

      handler.handleDrawingOrder(new GEAR_EndArea());

      // 3. Green standalone square (GBOX outside GBAR)
      GSPCOL_SetProcessColor green = new GSPCOL_SetProcessColor();
      green.colorSpace = AFPColorSpace.RGB;
      green.colorValue = new byte[]{0, (byte) 255, 0};
      handler.handleDrawingOrder(green);

      GBOX_BoxAtGivenPosition gbox2 = new GBOX_BoxAtGivenPosition();
      gbox2.setFirstCorner(new GOCA_Point((short) 8000, (short) 4000));
      gbox2.setDiagonalCorner(new GOCA_Point((short) 11000, (short) 7000));
      handler.handleDrawingOrder(gbox2);

      // 4. Yellow standalone circle (GFARC outside GBAR)
      GSPCOL_SetProcessColor yellow = new GSPCOL_SetProcessColor();
      yellow.colorSpace = AFPColorSpace.RGB;
      yellow.colorValue = new byte[]{(byte) 255, (byte) 255, 0};
      handler.handleDrawingOrder(yellow);

      GFARC_FullArcAtGivenPosition gfarc2 = new GFARC_FullArcAtGivenPosition();
      gfarc2.setArcCenter(new GOCA_Point((short) 9500, (short) 2000));
      gfarc2.setMultiplierIntegerPortion((short) 1);
      gfarc2.setMultiplierFractionalPortion((short) 0);
      handler.handleDrawingOrder(gfarc2);

      handler.handle(new EGR_EndGraphicsObject());
      handler.handle(new EPG_EndPage());
      handler.close();
    }

    BufferedImage img = PdfVerificationUtils.rasterize(pdfFile, 150);
    File imgFile = new File("build/test_shapes.png");
    PdfVerificationUtils.saveImage(img, "png", imgFile);

    // Verify center pixel colors of shapes to ensure solid interior fills:
    // Shape 1: Red circle centered at (2000, 2000)
    Color c1 = getPixelColorInNormalizedCoords(img, 2000, 2000, 14400, 14400);
    assertTrue(c1.getRed() > 200 && c1.getGreen() < 50 && c1.getBlue() < 50, "Red circle interior must be red, got: " + c1);

    // Shape 2: Blue square centered at (5500, 5500)
    Color c2 = getPixelColorInNormalizedCoords(img, 5500, 5500, 14400, 14400);
    assertTrue(c2.getBlue() > 200 && c2.getRed() < 50 && c2.getGreen() < 50, "Blue square interior must be blue, got: " + c2);

    // Shape 3: Green standalone square centered at (9500, 5500)
    Color c3 = getPixelColorInNormalizedCoords(img, 9500, 5500, 14400, 14400);
    assertTrue(c3.getGreen() > 200 && c3.getRed() < 50 && c3.getBlue() < 50, "Green square interior must be green, got: " + c3);

    // Shape 4: Yellow standalone circle centered at (9500, 2000)
    Color c4 = getPixelColorInNormalizedCoords(img, 9500, 2000, 14400, 14400);
    assertTrue(c4.getRed() > 200 && c4.getGreen() > 200 && c4.getBlue() < 50, "Yellow circle interior must be yellow, got: " + c4);
  }

  private Color getPixelColorInNormalizedCoords(BufferedImage img, int afpX, int afpY, int maxAfpX, int maxAfpY) {
    int px = Math.round(((float) afpX / maxAfpX) * img.getWidth());
    int py = Math.round(((float) afpY / maxAfpY) * img.getHeight());
    return new Color(img.getRGB(px, py));
  }
}
