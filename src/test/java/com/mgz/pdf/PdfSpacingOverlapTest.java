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

import com.mgz.afp.ptoca.controlSequence.PTOCAControlSequence;
import com.mgz.afp.ptoca.controlSequence.PTOCAControlSequence.SIA_SetIntercharacterAdjustment;
import com.mgz.afp.ptoca.controlSequence.PTOCAControlSequence.SIA_SetIntercharacterAdjustment.SIA_Direction;
import com.mgz.afp.parser.AFPParserConfiguration;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.lang.reflect.Method;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class PdfSpacingOverlapTest {

  @Test
  public void testInlinePosAdvancementWithoutPgd() throws Exception {
    ByteArrayOutputStream baos = new ByteArrayOutputStream();
    PdfHandler handler = new PdfHandler(baos);
    PdfTextState state = handler.getTextState();

    // Initially inlinePos is 0
    assertEquals(0, state.getInlinePos(), 0.001);

    // Render some text
    Method renderText = PdfHandler.class.getDeclaredMethod("renderText", String.class);
    renderText.setAccessible(true);

    // We need a page and canvas
    Method ensurePageExists = PdfHandler.class.getDeclaredMethod("ensurePageExists");
    ensurePageExists.setAccessible(true);
    ensurePageExists.invoke(handler);

    renderText.invoke(handler, "Hello");

    // If bug exists, inlinePos is still 0 because defaultPageWidth is -1
    // We want it to be > 0
    assertNotEquals(0, state.getInlinePos(), "inlinePos should advance even if PGD was not yet processed");
  }

  @Test
  public void testSiaNegativeDirection() throws Exception {
    ByteArrayOutputStream baos = new ByteArrayOutputStream();
    PdfHandler handler = new PdfHandler(baos);
    PdfTextState state = handler.getTextState();

    Method handleCS = PdfHandler.class.getDeclaredMethod("handleControlSequence", PTOCAControlSequence.class);
    handleCS.setAccessible(true);

    SIA_SetIntercharacterAdjustment sia = new SIA_SetIntercharacterAdjustment();
    // Adjustment 10, Direction Negative (0x01)
    // SIA.decodeAFP expects 2 bytes for adjustment, 1 byte for direction
    byte[] data = new byte[] {0x00, 0x0A, 0x01};
    sia.decodeAFP(data, 0, 3, new AFPParserConfiguration());

    handleCS.invoke(handler, sia);

    // If bug exists, it will be 10. Expected is -10.
    assertEquals(-10, (int) state.getIntercharacterAdjustment(), "SIA with negative direction should result in negative adjustment value");
  }
}
