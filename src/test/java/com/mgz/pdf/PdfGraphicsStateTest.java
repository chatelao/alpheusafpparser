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

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.mgz.afp.enums.AFPColorValue;
import com.mgz.afp.goca.GAD_DrawingOrder.GSLE_SetLineEnd.LineEnd;
import com.mgz.afp.goca.GAD_DrawingOrder.GSLJ_SetLineJoin.LineJoin;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Unit test for {@link PdfGraphicsState}.
 */
public class PdfGraphicsStateTest {

  private PdfGraphicsState graphicsState;

  @BeforeEach
  public void setUp() {
    graphicsState = new PdfGraphicsState();
  }

  @Test
  public void testDefaultValues() {
    assertEquals(AFPColorValue.DeviceDefault_0x00, graphicsState.getColor());
    assertEquals(0, graphicsState.getLineWidth());
    assertEquals(0, graphicsState.getLineType());
    assertEquals(LineEnd.Default, graphicsState.getLineEnd());
    assertEquals(LineJoin.Default, graphicsState.getLineJoin());
    assertEquals(0, graphicsState.getMixMode());
    assertEquals(0, graphicsState.getBackgroundMixMode());
    assertEquals(0, graphicsState.getCurrentX());
    assertEquals(0, graphicsState.getCurrentY());
  }

  @Test
  public void testSettersAndGetters() {
    graphicsState.setColor(AFPColorValue.Blue_0x01);
    graphicsState.setLineWidth((short) 5);
    graphicsState.setLineType((short) 2);
    graphicsState.setLineEnd(LineEnd.Round);
    graphicsState.setLineJoin(LineJoin.Bevel);
    graphicsState.setMixMode((short) 1);
    graphicsState.setBackgroundMixMode((short) 3);
    graphicsState.setCurrentX(100);
    graphicsState.setCurrentY(200);

    assertEquals(AFPColorValue.Blue_0x01, graphicsState.getColor());
    assertEquals(5, graphicsState.getLineWidth());
    assertEquals(2, graphicsState.getLineType());
    assertEquals(LineEnd.Round, graphicsState.getLineEnd());
    assertEquals(LineJoin.Bevel, graphicsState.getLineJoin());
    assertEquals(1, graphicsState.getMixMode());
    assertEquals(3, graphicsState.getBackgroundMixMode());
    assertEquals(100, graphicsState.getCurrentX());
    assertEquals(200, graphicsState.getCurrentY());
  }

  @Test
  public void testReset() {
    graphicsState.setColor(AFPColorValue.Red_0x02);
    graphicsState.setLineWidth((short) 10);
    graphicsState.setMixMode((short) 2);
    graphicsState.setBackgroundMixMode((short) 4);
    graphicsState.setCurrentX(500);

    graphicsState.reset();

    assertEquals(AFPColorValue.DeviceDefault_0x00, graphicsState.getColor());
    assertEquals(0, graphicsState.getLineWidth());
    assertEquals(0, graphicsState.getMixMode());
    assertEquals(0, graphicsState.getBackgroundMixMode());
    assertEquals(0, graphicsState.getCurrentX());
  }
}
