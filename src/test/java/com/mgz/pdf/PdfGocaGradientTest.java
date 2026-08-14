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

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;

import com.mgz.afp.enums.AFPColorSpace;
import com.mgz.afp.goca.GAD_DrawingOrder;
import com.mgz.afp.goca.GAD_DrawingOrder.GBAR_BeginArea;
import com.mgz.afp.goca.GAD_DrawingOrder.GEAR_EndArea;
import com.mgz.afp.goca.GAD_DrawingOrder.GLGD_LinearGradient;
import com.mgz.afp.goca.GAD_DrawingOrder.GRGD_RadialGradient;
import com.mgz.afp.goca.GAD_DrawingOrder.GSCP_SetCurrentPosition;
import com.mgz.afp.goca.GAD_DrawingOrder.GSPT_SetPatternSymbol;
import com.mgz.afp.goca.GAD_DrawingOrder.GSPS_SetPatternSet;
import com.mgz.afp.goca.GAD_DrawingOrder.ColorSpecification;
import com.mgz.afp.goca.GAD_DrawingOrder.GSCOL_SetColor;
import com.mgz.afp.goca.GAD_DrawingOrder.GSECOL_SetExtendedColor;
import com.mgz.afp.goca.GAD_DrawingOrder.GSPCOL_SetProcessColor;
import com.mgz.afp.modca.BPG_BeginPage;
import com.mgz.afp.modca.EPG_EndPage;
import java.io.ByteArrayOutputStream;
import org.junit.jupiter.api.Test;

/**
 * Verifies GOCA gradient rendering in PdfHandler.
 */
public class PdfGocaGradientTest {

  @Test
  public void testLinearGradientRendering() throws Exception {
    ByteArrayOutputStream baos = new ByteArrayOutputStream();
    PdfHandler handler = new PdfHandler(baos);

    // 1. Begin Page
    BPG_BeginPage bpg = new BPG_BeginPage();
    bpg.setName("P1");
    handler.handle(bpg);

    // 2. Define Linear Gradient (Pattern Set 1, Symbol 1)
    GLGD_LinearGradient glgd = new GLGD_LinearGradient();
    glgd.patternSet = 1;
    glgd.patternSymbol = 1;
    glgd.xStart = 0;
    glgd.yStart = 0;
    glgd.xEnd = 1000;
    glgd.yEnd = 1000;
    glgd.startColorSpec = new ColorSpecification();
    glgd.startColorSpec.colorSpace = AFPColorSpace.RGB;
    glgd.startColorSpec.colorValue = new byte[]{(byte) 255, 0, 0}; // Red
    glgd.endColorValue = new byte[]{0, 0, (byte) 255}; // Blue
    handler.handleDrawingOrder(glgd);

    // 3. Use Gradient
    GSPS_SetPatternSet gsps = new GSPS_SetPatternSet();
    gsps.setPatternLocalID((short) 1);
    handler.handleDrawingOrder(gsps);

    GSPT_SetPatternSymbol gspt = new GSPT_SetPatternSymbol();
    gspt.setPatternSymbolCodePoint((short) 1);
    handler.handleDrawingOrder(gspt);

    // 4. Draw Area
    GBAR_BeginArea gbar = new GBAR_BeginArea();
    handler.handleDrawingOrder(gbar);

    GSCP_SetCurrentPosition gscp = new GSCP_SetCurrentPosition();
    gscp.setCoordinateX((short) 0);
    gscp.setCoordinateY((short) 0);
    handler.handleDrawingOrder(gscp);

    // Add points for area... (simplified)

    GEAR_EndArea gear = new GEAR_EndArea();
    assertDoesNotThrow(() -> handler.handleDrawingOrder(gear));

    handler.handle(new EPG_EndPage());
    handler.close();
  }

  @Test
  public void testGspcolAndPrecedenceReset() throws Exception {
    ByteArrayOutputStream baos = new ByteArrayOutputStream();
    PdfHandler handler = new PdfHandler(baos);

    handler.handle(new BPG_BeginPage());

    // 1. Set process color
    GSPCOL_SetProcessColor gspcol = new GSPCOL_SetProcessColor();
    gspcol.colorSpace = AFPColorSpace.RGB;
    gspcol.nrOfBitsComponent1 = (byte) 8;
    gspcol.nrOfBitsComponent2 = (byte) 8;
    gspcol.nrOfBitsComponent3 = (byte) 8;
    gspcol.nrOfBitsComponent4 = (byte) 0;
    gspcol.colorValue = new byte[]{(byte) 255, 0, 0};
    handler.handleDrawingOrder(gspcol);

    PdfGraphicsState state = handler.getGraphicsState();
    assertEquals(AFPColorSpace.RGB, state.getProcessColorSpace());
    assertEquals((byte) 255, state.getProcessColorValue()[0]);

    // 2. Process GSCOL and verify reset
    GSCOL_SetColor gscol = new GSCOL_SetColor();
    gscol.setColor(com.mgz.afp.enums.AFPColorValue.Blue_0x01);
    handler.handleDrawingOrder(gscol);

    assertEquals(null, state.getProcessColorSpace());
    assertEquals(null, state.getProcessColorValue());

    // 3. Set process color again
    handler.handleDrawingOrder(gspcol);
    assertEquals(AFPColorSpace.RGB, state.getProcessColorSpace());

    // 4. Process GSECOL and verify reset
    GSECOL_SetExtendedColor gsecol = new GSECOL_SetExtendedColor();
    gsecol.setColor(com.mgz.afp.enums.AFPColorValue.Green_0x04);
    handler.handleDrawingOrder(gsecol);

    assertEquals(null, state.getProcessColorSpace());
    assertEquals(null, state.getProcessColorValue());

    handler.handle(new EPG_EndPage());
    handler.close();
  }

  @Test
  public void testGradientNullSafeness() throws Exception {
    ByteArrayOutputStream baos = new ByteArrayOutputStream();
    PdfHandler handler = new PdfHandler(baos);

    handler.handle(new BPG_BeginPage());

    // 1. Linear Gradient with null startColorSpec
    GLGD_LinearGradient glgdNull = new GLGD_LinearGradient();
    glgdNull.patternSet = 1;
    glgdNull.patternSymbol = 3;
    glgdNull.xStart = 0;
    glgdNull.yStart = 0;
    glgdNull.xEnd = 100;
    glgdNull.yEnd = 100;
    glgdNull.startColorSpec = null;
    glgdNull.endColorValue = new byte[]{0, 0, 0};
    handler.handleDrawingOrder(glgdNull);

    // 2. Radial Gradient with null startColorSpec
    GRGD_RadialGradient grgdNull = new GRGD_RadialGradient();
    grgdNull.patternSet = 1;
    grgdNull.patternSymbol = 4;
    grgdNull.xStart = 0;
    grgdNull.yStart = 0;
    grgdNull.xEnd = 100;
    grgdNull.yEnd = 100;
    grgdNull.startColorSpec = null;
    grgdNull.endColorValue = new byte[]{0, 0, 0};
    handler.handleDrawingOrder(grgdNull);

    // 3. Use Gradients and ensure applying them does not throw NPE
    GSPS_SetPatternSet gsps = new GSPS_SetPatternSet();
    gsps.setPatternLocalID((short) 1);
    handler.handleDrawingOrder(gsps);

    // We can wrap pattern symbol applications inside drawing area/boundary
    handler.handleDrawingOrder(new GBAR_BeginArea());

    GSPT_SetPatternSymbol gspt3 = new GSPT_SetPatternSymbol();
    gspt3.setPatternSymbolCodePoint((short) 3);
    assertDoesNotThrow(() -> handler.handleDrawingOrder(gspt3));

    GSPT_SetPatternSymbol gspt4 = new GSPT_SetPatternSymbol();
    gspt4.setPatternSymbolCodePoint((short) 4);
    assertDoesNotThrow(() -> handler.handleDrawingOrder(gspt4));

    handler.handleDrawingOrder(new GEAR_EndArea());

    handler.handle(new EPG_EndPage());
    handler.close();
  }

  @Test
  public void testRadialGradientRendering() throws Exception {
    ByteArrayOutputStream baos = new ByteArrayOutputStream();
    PdfHandler handler = new PdfHandler(baos);

    handler.handle(new BPG_BeginPage());

    // 1. Define Radial Gradient (Pattern Set 1, Symbol 2)
    GRGD_RadialGradient grgd = new GRGD_RadialGradient();
    grgd.patternSet = 1;
    grgd.patternSymbol = 2;
    grgd.xStart = 500;
    grgd.yStart = 500;
    grgd.mhStart = 0;
    grgd.mfrStart = 0;
    grgd.xEnd = 500;
    grgd.yEnd = 500;
    grgd.mhEnd = 100;
    grgd.mfrEnd = 0;
    grgd.startColorSpec = new ColorSpecification();
    grgd.startColorSpec.colorSpace = AFPColorSpace.RGB;
    grgd.startColorSpec.colorValue = new byte[]{(byte) 255, (byte) 255, 0}; // Yellow
    grgd.endColorValue = new byte[]{0, (byte) 255, 0}; // Green
    handler.handleDrawingOrder(grgd);

    // 2. Use Gradient
    GSPS_SetPatternSet gsps = new GSPS_SetPatternSet();
    gsps.setPatternLocalID((short) 1);
    handler.handleDrawingOrder(gsps);

    GSPT_SetPatternSymbol gspt = new GSPT_SetPatternSymbol();
    gspt.setPatternSymbolCodePoint((short) 2);
    handler.handleDrawingOrder(gspt);

    // 3. Draw Area
    handler.handleDrawingOrder(new GBAR_BeginArea());
    handler.handleDrawingOrder(new GSCP_SetCurrentPosition());
    assertDoesNotThrow(() -> handler.handleDrawingOrder(new GEAR_EndArea()));

    handler.handle(new EPG_EndPage());
    handler.close();
  }

  @Test
  public void testProcessColorAreaFilling() throws Exception {
    ByteArrayOutputStream baos = new ByteArrayOutputStream();
    PdfHandler handler = new PdfHandler(baos);

    handler.handle(new BPG_BeginPage());

    // 1. Set process color (RGB)
    GSPCOL_SetProcessColor gspcol = new GSPCOL_SetProcessColor();
    gspcol.colorSpace = AFPColorSpace.RGB;
    gspcol.nrOfBitsComponent1 = (byte) 8;
    gspcol.nrOfBitsComponent2 = (byte) 8;
    gspcol.nrOfBitsComponent3 = (byte) 8;
    gspcol.nrOfBitsComponent4 = (byte) 0;
    gspcol.colorValue = new byte[]{(byte) 100, (byte) 150, (byte) 200};
    handler.handleDrawingOrder(gspcol);

    // 2. Begin Area
    GBAR_BeginArea gbar = new GBAR_BeginArea();
    handler.handleDrawingOrder(gbar);

    // 3. Set Current Position
    GSCP_SetCurrentPosition gscp = new GSCP_SetCurrentPosition();
    gscp.setCoordinateX((short) 100);
    gscp.setCoordinateY((short) 100);
    handler.handleDrawingOrder(gscp);

    // 4. Draw boundary lines of the area
    GAD_DrawingOrder.GCLINE_LineAtCurrentPosition gcline = new GAD_DrawingOrder.GCLINE_LineAtCurrentPosition();
    gcline.setPoints(java.util.List.of(
        new GAD_DrawingOrder.GOCA_Point((short) 200, (short) 100),
        new GAD_DrawingOrder.GOCA_Point((short) 200, (short) 200),
        new GAD_DrawingOrder.GOCA_Point((short) 100, (short) 200)
    ));
    handler.handleDrawingOrder(gcline);

    // 5. End Area (triggers applyPattern, which should use the resolved RGB process color)
    GEAR_EndArea gear = new GEAR_EndArea();
    assertDoesNotThrow(() -> handler.handleDrawingOrder(gear));

    handler.handle(new EPG_EndPage());
    handler.close();
  }
}
