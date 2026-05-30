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

import com.itextpdf.kernel.colors.Color;
import com.itextpdf.kernel.colors.DeviceCmyk;
import com.itextpdf.kernel.colors.DeviceRgb;
import com.mgz.afp.enums.AFPColorSpace;
import com.mgz.afp.enums.AFPColorValue;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Verifies that {@link ColorHandler} correctly converts AFP colors to iText colors.
 */
public class ColorHandlerTest {

  @Test
  public void testGetColor() {
    Color red = ColorHandler.getColor(AFPColorValue.Red_0x02);
    assertTrue(red instanceof DeviceRgb);
    float[] expected = new float[] {1.0f, 0.0f, 0.0f};
    assertArrayEquals(expected, red.getColorValue());

    Color blue = ColorHandler.getColor(AFPColorValue.Blue_0x01);
    assertTrue(blue instanceof DeviceRgb);
    assertArrayEquals(new float[] {0.0f, 0.0f, 1.0f}, blue.getColorValue());
  }

  @Test
  public void testGetExtendedColorRgb() {
    byte[] rgbData = new byte[] {(byte) 0xFF, 0x7F, 0x00};
    Color color = ColorHandler.getExtendedColor(AFPColorSpace.RGB, rgbData);
    assertTrue(color instanceof DeviceRgb);
    assertArrayEquals(new float[] {1.0f, 127/255.0f, 0.0f}, color.getColorValue());
  }

  @Test
  public void testGetExtendedColorCmyk() {
    byte[] cmykData = new byte[] {(byte) 0xFF, 0x00, (byte) 0xFF, 0x00};
    Color color = ColorHandler.getExtendedColor(AFPColorSpace.CMYK, cmykData);
    assertTrue(color instanceof DeviceCmyk);
    assertArrayEquals(new float[] {1.0f, 0.0f, 1.0f, 0.0f}, color.getColorValue());
  }

  @Test
  public void testGetExtendedColorStandardOca() {
    byte[] ocaData = new byte[] {0x02}; // Red
    Color color = ColorHandler.getExtendedColor(AFPColorSpace.StandardOCA, ocaData);
    assertTrue(color instanceof DeviceRgb);
    assertArrayEquals(new float[] {1.0f, 0.0f, 0.0f}, color.getColorValue());
  }
}
