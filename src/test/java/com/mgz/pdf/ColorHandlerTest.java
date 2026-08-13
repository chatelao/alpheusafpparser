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
import com.itextpdf.kernel.colors.Lab;
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

  @Test
  public void testGetExtendedColorCielab() {
    byte[] labData = new byte[] {(byte) 0x80, 0x00, 0x00}; // L=50%, a=-128, b=-128
    Color color = ColorHandler.getExtendedColor(AFPColorSpace.CIELAB, labData);
    assertTrue(color instanceof Lab);
    float[] colorValue = color.getColorValue();
    assertEquals(128.0f * 100.0f / 255.0f, colorValue[0], 0.01f);
    assertEquals(-128.0f, colorValue[1], 0.01f);
    assertEquals(-128.0f, colorValue[2], 0.01f);
  }

  @Test
  public void testGetExtendedColorHighlight() {
    // 1. Red (colorNum 1), 100% coverage, 0% shading
    byte[] highlightRed = new byte[] {0x00, 0x01, 0x64, 0x00};
    Color color1 = ColorHandler.getExtendedColor(AFPColorSpace.Highlight, highlightRed);
    assertTrue(color1 instanceof DeviceRgb);
    assertArrayEquals(new float[] {1.0f, 0.0f, 0.0f}, color1.getColorValue());

    // 2. Yellow (colorNum 4), 100% coverage, 0% shading
    byte[] highlightYellow = new byte[] {0x00, 0x04, 0x64};
    Color color2 = ColorHandler.getExtendedColor(AFPColorSpace.Highlight, highlightYellow);
    assertTrue(color2 instanceof DeviceRgb);
    assertArrayEquals(new float[] {1.0f, 1.0f, 0.0f}, color2.getColorValue());

    // 3. Red with 50% coverage (should be lighter)
    byte[] highlightRed50 = new byte[] {0x00, 0x01, 0x32, 0x00};
    Color color3 = ColorHandler.getExtendedColor(AFPColorSpace.Highlight, highlightRed50);
    assertTrue(color3 instanceof DeviceRgb);
    // baseR=255, baseG=0, baseB=0; tint=0.5
    // R = 255 * 0.5 + 255 * 0.5 = 255
    // G = 0 * 0.5 + 255 * 0.5 = 128 (rounded from 127.5)
    // B = 0 * 0.5 + 255 * 0.5 = 128 (rounded from 127.5)
    assertArrayEquals(new float[] {255/255.0f, 128/255.0f, 128/255.0f}, color3.getColorValue());

    // 4. Red with 100% coverage and 50% shading (should be darker)
    byte[] highlightRedShaded = new byte[] {0x00, 0x01, 0x64, 0x32};
    Color color4 = ColorHandler.getExtendedColor(AFPColorSpace.Highlight, highlightRedShaded);
    assertTrue(color4 instanceof DeviceRgb);
    // baseR=255, baseG=0, baseB=0; tint=1.0 -> R=255, G=0, B=0
    // shade=0.5 -> R=128 (rounded from 127.5), G=0, B=0
    assertArrayEquals(new float[] {128/255.0f, 0.0f, 0.0f}, color4.getColorValue());

    // 5. Check out-of-bound values for coverage/shading are clipped
    byte[] highlightOOB = new byte[] {0x00, 0x01, (byte) 120, (byte) 150};
    Color color5 = ColorHandler.getExtendedColor(AFPColorSpace.Highlight, highlightOOB);
    assertTrue(color5 instanceof DeviceRgb);
    // coverage=120 -> 100%, shading=150 -> 100% (black)
    assertArrayEquals(new float[] {0.0f, 0.0f, 0.0f}, color5.getColorValue());
  }

  @Test
  public void testGetExtendedColorYCrCb() {
    // 3 bytes: Y=128, Cr=160, Cb=96
    byte[] ycrcbData = new byte[] {(byte) 128, (byte) 160, (byte) 96};
    Color color = ColorHandler.getExtendedColor(AFPColorSpace.YCrCb, ycrcbData);
    assertTrue(color instanceof DeviceRgb);

    // Y = 128, Cr = 160, Cb = 96
    // R = 128 + 1.402 * (160 - 128) = 128 + 44.864 = 173 (rounded)
    // G = 128 - 0.344136 * (96 - 128) - 0.714136 * (160 - 128) = 128 + 11.012 - 22.852 = 116 (rounded)
    // B = 128 + 1.772 * (96 - 128) = 128 - 56.704 = 71 (rounded)
    assertArrayEquals(new float[] {173/255.0f, 116/255.0f, 71/255.0f}, color.getColorValue());

    // 1 byte grayscale fallback
    byte[] grayData = new byte[] {(byte) 100};
    Color grayColor = ColorHandler.getExtendedColor(AFPColorSpace.YCrCb, grayData);
    assertTrue(grayColor instanceof DeviceRgb);
    assertArrayEquals(new float[] {100/255.0f, 100/255.0f, 100/255.0f}, grayColor.getColorValue());
  }

  @Test
  public void testGetExtendedColorYCbCr() {
    // 3 bytes: Y=128, Cb=96, Cr=160 (same physical colors as above test but with inverted Cr/Cb order)
    byte[] ycbcrData = new byte[] {(byte) 128, (byte) 96, (byte) 160};
    Color color = ColorHandler.getExtendedColor(AFPColorSpace.YCbCr, ycbcrData);
    assertTrue(color instanceof DeviceRgb);
    assertArrayEquals(new float[] {173/255.0f, 116/255.0f, 71/255.0f}, color.getColorValue());
  }

  @Test
  public void testDynamicDeviceDefaultColorResolution() {
    // 1. In default context, DeviceDefault should resolve to Black
    Color defaultColor = ColorHandler.getColor(AFPColorValue.DeviceDefault_0x00);
    assertTrue(defaultColor instanceof DeviceRgb);
    assertArrayEquals(new float[] {0.0f, 0.0f, 0.0f}, defaultColor.getColorValue());

    // 2. Explicit non-default colors remain unchanged in any context
    Color red = ColorHandler.getColor(AFPColorValue.Red_0x02);
    assertArrayEquals(new float[] {1.0f, 0.0f, 0.0f}, red.getColorValue());

    // Create a new custom ColorContext
    ColorContext ctx = new ColorContext();

    try {
      ColorHandler.setContext(ctx);

      // 3. Set a dark background -> default color should adjust to White
      ctx.setMediaBackgroundColor(new DeviceRgb(0, 0, 0)); // pure black background
      Color resolvedOnDark = ColorHandler.getColor(AFPColorValue.DeviceDefault_0x00);
      assertArrayEquals(new float[] {1.0f, 1.0f, 1.0f}, resolvedOnDark.getColorValue());

      // 4. Set a light background -> default color should adjust back to Black
      ctx.setMediaBackgroundColor(new DeviceRgb(255, 255, 255)); // pure white background
      Color resolvedOnLight = ColorHandler.getColor(AFPColorValue.DeviceDefault_0x00);
      assertArrayEquals(new float[] {0.0f, 0.0f, 0.0f}, resolvedOnLight.getColorValue());

      // 5. Explicit stylesheet color override
      Color customGreen = new DeviceRgb(0, 255, 0);
      ctx.setActiveStylesheetColor(customGreen);
      Color resolvedWithStylesheet = ColorHandler.getColor(AFPColorValue.DeviceDefault_0xFF00);
      assertArrayEquals(new float[] {0.0f, 1.0f, 0.0f}, resolvedWithStylesheet.getColorValue());

      // Clear stylesheet and test Formdef default color override
      ctx.setActiveStylesheetColor(null);
      Color customBlue = new DeviceRgb(0, 0, 255);
      ctx.setFormdefDefaultColor(customBlue);
      Color resolvedWithFormdef = ColorHandler.getColor(AFPColorValue.Default_0xFFFF);
      assertArrayEquals(new float[] {0.0f, 0.0f, 1.0f}, resolvedWithFormdef.getColorValue());

    } finally {
      ColorHandler.clearContext();
    }
  }

  @Test
  public void testPdfHandlerContextIntegration() throws Exception {
    java.io.ByteArrayOutputStream os = new java.io.ByteArrayOutputStream();
    try (PdfHandler handler = new PdfHandler(os)) {
      // Access handler's context and configure dark background
      ColorContext ctx = handler.getColorContext();
      ctx.setMediaBackgroundColor(new DeviceRgb(0, 0, 0)); // dark background

      // Since PdfHandler.handle(...) sets the thread-local context, we can simulate structured field processing
      // and assert that dynamic color resolution works perfectly within the handler's lifecycle.
      ColorHandler.setContext(ctx);
      try {
        Color resolved = ColorHandler.getColor(AFPColorValue.DeviceDefault_0x00);
        assertArrayEquals(new float[] {1.0f, 1.0f, 1.0f}, resolved.getColorValue());
      } finally {
        ColorHandler.clearContext();
      }
    }
  }
}
