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

import com.mgz.afp.enums.AFPColorSpace;
import com.mgz.afp.enums.AFPColorValue;
import com.mgz.afp.enums.AFPOrientation;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Verifies that {@link PdfTextState} correctly tracks and resets PTOCA attributes.
 */
public class PdfTextStateTest {

  @Test
  public void testInitialState() {
    PdfTextState state = new PdfTextState();
    assertEquals(-1, state.getFontLid());
    assertEquals(AFPColorValue.DeviceDefault_0x00, state.getTextColor());
    assertFalse(state.isExtendedColor());
    assertNull(state.getExtendedColorSpace());
    assertNull(state.getExtendedColorValue());
    assertEquals(AFPOrientation.ori0, state.getIOrientation());
    assertEquals(AFPOrientation.ori90, state.getBOrientation());
    assertEquals(0, state.getInlinePos());
    assertEquals(0, state.getBaselinePos());
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
  }

  @Test
  public void testExtendedColor() {
    PdfTextState state = new PdfTextState();
    byte[] colorValue = new byte[] {0x00, 0x7F, (byte) 0xFF, 0x00};

    state.setExtendedColorSpace(AFPColorSpace.RGB);
    state.setExtendedColorValue(colorValue);

    assertTrue(state.isExtendedColor());
    assertEquals(AFPColorSpace.RGB, state.getExtendedColorSpace());
    assertArrayEquals(colorValue, state.getExtendedColorValue());

    // Setting standard color should disable extended color
    state.setTextColor(AFPColorValue.Blue_0x01);
    assertFalse(state.isExtendedColor());
    assertEquals(AFPColorValue.Blue_0x01, state.getTextColor());
  }

  @Test
  public void testReset() {
    PdfTextState state = new PdfTextState();

    state.setFontLid((short) 5);
    state.setTextColor(AFPColorValue.Red_0x02);
    state.setExtendedColorSpace(AFPColorSpace.CMYK);
    state.setInlinePos(100);

    state.reset();

    assertEquals(-1, state.getFontLid());
    assertEquals(AFPColorValue.DeviceDefault_0x00, state.getTextColor());
    assertFalse(state.isExtendedColor());
    assertNull(state.getExtendedColorSpace());
    assertEquals(0, state.getInlinePos());
    assertEquals(AFPOrientation.ori0, state.getIOrientation());
    assertFalse(state.isHasEstablishedBaseline());
    assertEquals(0, state.getEstablishedBaselinePos());
  }

  @Test
  public void testTbmState() {
    PdfTextState state = new PdfTextState();

    state.setBaselinePos(1000);
    state.setEstablishedBaselinePos(1000);
    state.setHasEstablishedBaseline(true);

    assertTrue(state.isHasEstablishedBaseline());
    assertEquals(1000, state.getEstablishedBaselinePos());

    state.reset();
    assertFalse(state.isHasEstablishedBaseline());
    assertEquals(0, state.getEstablishedBaselinePos());
  }
}
