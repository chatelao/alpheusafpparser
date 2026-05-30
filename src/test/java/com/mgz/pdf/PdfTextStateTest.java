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

import com.mgz.afp.enums.AFPColorValue;
import com.mgz.afp.enums.AFPOrientation;
import com.mgz.afp.ptoca.controlSequence.PTOCAControlSequence.SIA_SetIntercharacterAdjustment.SIA_Direction;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Verifies that {@link PdfTextState} correctly tracks and resets PTOCA attributes.
 */
public class PdfTextStateTest {

  @Test
  public void testInitialState() {
    PdfTextState state = new PdfTextState();
    assertEquals(-1, state.getFontLid());
    assertEquals(AFPColorValue.DeviceDefault_0x00, state.getTextColor());
    assertEquals(AFPOrientation.ori0, state.getIOrientation());
    assertEquals(AFPOrientation.ori90, state.getBOrientation());
    assertEquals(0, state.getInlinePos());
    assertEquals(0, state.getBaselinePos());
    assertEquals(0, state.getIntercharacterAdjustment());
    assertEquals(SIA_Direction.PositiveIDirection, state.getIntercharacterAdjustmentDirection());
    assertEquals(0, state.getVariableSpaceCharacterIncrement());
    assertEquals(0, state.getInlineMargin());
  }

  @Test
  public void testStateModification() {
    PdfTextState state = new PdfTextState();

    state.setFontLid((short) 5);
    state.setTextColor(AFPColorValue.Blue_0x01);
    state.setIOrientation(AFPOrientation.ori180);
    state.setBOrientation(AFPOrientation.ori270);
    state.setInlinePos(1440);
    state.setBaselinePos(720);

    assertEquals(5, state.getFontLid());
    assertEquals(AFPColorValue.Blue_0x01, state.getTextColor());
    assertEquals(AFPOrientation.ori180, state.getIOrientation());
    assertEquals(AFPOrientation.ori270, state.getBOrientation());
    assertEquals(1440, state.getInlinePos());
    assertEquals(720, state.getBaselinePos());

    state.setIntercharacterAdjustment((short) 10);
    state.setIntercharacterAdjustmentDirection(SIA_Direction.NegativeIDirection);
    state.setVariableSpaceCharacterIncrement((short) 20);
    state.setInlineMargin((short) 30);

    assertEquals(10, state.getIntercharacterAdjustment());
    assertEquals(SIA_Direction.NegativeIDirection, state.getIntercharacterAdjustmentDirection());
    assertEquals(20, state.getVariableSpaceCharacterIncrement());
    assertEquals(30, state.getInlineMargin());
  }

  @Test
  public void testReset() {
    PdfTextState state = new PdfTextState();

    state.setFontLid((short) 5);
    state.setTextColor(AFPColorValue.Red_0x02);
    state.setInlinePos(100);
    state.setIntercharacterAdjustment((short) 10);
    state.setIntercharacterAdjustmentDirection(SIA_Direction.NegativeIDirection);
    state.setVariableSpaceCharacterIncrement((short) 20);
    state.setInlineMargin((short) 30);

    state.reset();

    assertEquals(-1, state.getFontLid());
    assertEquals(AFPColorValue.DeviceDefault_0x00, state.getTextColor());
    assertEquals(0, state.getInlinePos());
    assertEquals(AFPOrientation.ori0, state.getIOrientation());
    assertEquals(0, state.getIntercharacterAdjustment());
    assertEquals(SIA_Direction.PositiveIDirection, state.getIntercharacterAdjustmentDirection());
    assertEquals(0, state.getVariableSpaceCharacterIncrement());
    assertEquals(0, state.getInlineMargin());
  }
}
