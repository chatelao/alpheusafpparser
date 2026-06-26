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

import com.mgz.afp.enums.AFPUnitBase;
import com.mgz.afp.ptoca.PTD_PresentationTextDataDescriptor_Format2;
import com.mgz.afp.ptoca.controlSequence.PTOCAControlSequence.SIA_SetIntercharacterAdjustment;
import com.mgz.afp.ptoca.controlSequence.PTOCAControlSequence.SVI_SetVariableSpaceCharacterIncrement;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.lang.reflect.Method;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PdfSpacingTest {

  @Test
  public void testPtdResolutionScaling() throws Exception {
    ByteArrayOutputStream baos = new ByteArrayOutputStream();
    PdfHandler handler = new PdfHandler(baos);

    // Initial scale is 0.05 (1/1440)
    assertEquals(0.05, handler.getDefaultScaleY(), 0.0001);

    // Process a PTD with 240 DPI resolution (units per 10 inches = 2400)
    PTD_PresentationTextDataDescriptor_Format2 ptd = new PTD_PresentationTextDataDescriptor_Format2();
    ptd.setxUnitBase(AFPUnitBase.Inches10);
    ptd.setyUnitBase(AFPUnitBase.Inches10);
    ptd.setxUnitsPerUnitBase((short) 2400);
    ptd.setyUnitsPerUnitBase((short) 2400);

    handler.handle(ptd);

    // Expected scale for 240 DPI: 72 / 240 = 0.3
    assertEquals(0.3, handler.getDefaultScaleY(), 0.0001);
  }

  @Test
  public void testSviSiaAdditiveInteraction() throws Exception {
    ByteArrayOutputStream baos = new ByteArrayOutputStream();
    PdfHandler handler = new PdfHandler(baos);
    PdfTextState state = handler.getTextState();

    Method renderText = PdfHandler.class.getDeclaredMethod("renderText", String.class);
    renderText.setAccessible(true);
    Method ensurePageExists = PdfHandler.class.getDeclaredMethod("ensurePageExists");
    ensurePageExists.setAccessible(true);
    ensurePageExists.invoke(handler);

    // Set SVI = 100, SIA = 20
    state.setVariableSpaceIncrement((short) 100);
    state.setIntercharacterAdjustment((short) 20);

    // Initial position
    state.setInlinePos(0);

    // Render a single space
    renderText.invoke(handler, " ");

    // font.getWidth(" ", fontSizeAfp) for Helvetica at 10pt (200 units at 1/1440) is ~55.55 units
    // With fix: totalWidthAfp = fontWidth + Tc + Tw = 55.55 + 20 + (100 - 55.55) = 120.
    // Basically SVI + SIA.
    assertEquals(120.0, state.getInlinePos(), 0.1);
  }
}
