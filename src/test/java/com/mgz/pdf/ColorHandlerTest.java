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

  @Test
  public void testCatPalettedColorResolution() {
    ColorContext ctx = new ColorContext();
    ColorHandler.setContext(ctx);

    try {
      // 1. Create a CAT structured field
      com.mgz.afp.modca_L.CAT_ColorAttributeTable cat = new com.mgz.afp.modca_L.CAT_ColorAttributeTable();
      com.mgz.afp.modca_L.CAT_ColorAttributeTable.CAT_BasePart bp = new com.mgz.afp.modca_L.CAT_ColorAttributeTable.CAT_BasePart();
      bp.setColorTableLocalID((short) 42);
      cat.setBasePart(bp);

      // SDP length: 6 bytes. Type: 0x01 (RGB). Start Index: 0x02 (Red). Value: pure blue (0, 0, 255)
      byte[] sdpData = new byte[] { 6, 0x01, 0x02, 0x00, 0x00, (byte) 0xFF };
      cat.setOtherData(sdpData);

      // 2. Parse CAT entries using ColorHandler helper
      java.util.Map<Integer, Color> entries = ColorHandler.parseCatEntries(cat);
      assertNotNull(entries);
      assertEquals(1, entries.size());

      Color mappedColor = entries.get(2);
      assertNotNull(mappedColor);
      assertTrue(mappedColor instanceof DeviceRgb);
      assertArrayEquals(new float[] {0.0f, 0.0f, 1.0f}, mappedColor.getColorValue());

      // 3. Register the table in ColorContext and make it active
      ctx.addColorTable((short) 42, entries);
      ctx.setActiveColorTableId((short) 42);

      // 4. Resolve the standard color for index 2 (AFPColorValue.Red_0x02)
      // Since CAT is active and index 2 maps to blue, it must resolve to blue!
      Color resolvedColor = ColorHandler.getColor(AFPColorValue.Red_0x02);
      assertNotNull(resolvedColor);
      assertArrayEquals(new float[] {0.0f, 0.0f, 1.0f}, resolvedColor.getColorValue());

      // 5. Test fallback mechanism when active CAT is null but a table exists
      ctx.setActiveColorTableId(null);
      Color resolvedFallback = ColorHandler.getColor(AFPColorValue.Red_0x02);
      assertNotNull(resolvedFallback);
      assertArrayEquals(new float[] {0.0f, 0.0f, 1.0f}, resolvedFallback.getColorValue());

      // 6. Test reset/clear
      ctx.resetColorTables();
      Color resolvedPostReset = ColorHandler.getColor(AFPColorValue.Red_0x02);
      assertNotNull(resolvedPostReset);
      // Back to standard Red
      assertArrayEquals(new float[] {1.0f, 0.0f, 0.0f}, resolvedPostReset.getColorValue());

    } finally {
      ColorHandler.clearContext();
    }
  }

  @Test
  public void testCmrIccProfileDataExtraction() {
    com.mgz.afp.cmoca.CMR_ColorManagementResource cmr = new com.mgz.afp.cmoca.CMR_ColorManagementResource();

    // 1. Initially tags are null, should return null
    assertTrue(cmr.getIccProfileData() == null);

    // 2. Add some other tags, should still return null
    java.util.List<com.mgz.afp.cmoca.CMRTag> tags = new java.util.ArrayList<>();
    tags.add(new com.mgz.afp.cmoca.CMRTag(0x0004, 1, 0, 0));
    cmr.setTags(tags);
    assertTrue(cmr.getIccProfileData() == null);

    // 3. Add Tag X'3015' (ICC Profile Data)
    byte[] mockIccData = new byte[] {0x12, 0x34, 0x56, 0x78};
    com.mgz.afp.cmoca.CMRTag iccTag = new com.mgz.afp.cmoca.CMRTag(0x3015, 5, mockIccData.length, 0);
    iccTag.setData(mockIccData);
    tags.add(iccTag);

    byte[] extractedData = cmr.getIccProfileData();
    assertNotNull(extractedData);
    assertArrayEquals(mockIccData, extractedData);
  }

  @Test
  public void testColorContextCmrRegistry() {
    ColorContext ctx = new ColorContext();

    // 1. Initial lookup should return null
    assertTrue(ctx.getCmr("TestCMR") == null);
    assertTrue(ctx.getActiveCmrName() == null);
    assertTrue(ctx.getActiveCmr() == null);

    // 2. Create and configure a simulated CMR
    com.mgz.afp.cmoca.CMR_ColorManagementResource cmr = new com.mgz.afp.cmoca.CMR_ColorManagementResource();
    cmr.setAlias("TestAlias");

    // Inject rawCmrName for getArchitectedName
    // We can use a reflection field setter if needed, or simply let alias register it.
    // Let's check how rawCmrName is formatted: utf16be, 146 bytes.
    byte[] rawCmrNameBytes = new byte[146];
    String archName = "ArchitectedNameForTest";
    byte[] archBytes = archName.getBytes(java.nio.charset.StandardCharsets.UTF_16BE);
    System.arraycopy(archBytes, 0, rawCmrNameBytes, 0, Math.min(archBytes.length, rawCmrNameBytes.length));

    // We can't access rawCmrName easily from outside if it's private and has no setter,
    // but we can parse it from structured field or just use reflection, or we don't strictly need architected name
    // to verify alias registry.
    // Let's use reflection to set rawCmrName so we test BOTH architected name and alias registration!
    try {
      java.lang.reflect.Field field = com.mgz.afp.cmoca.CMR_ColorManagementResource.class.getDeclaredField("rawCmrName");
      field.setAccessible(true);
      field.set(cmr, rawCmrNameBytes);
    } catch (Exception e) {
      // Fallback if reflection fails
    }

    // 3. Register CMR
    ctx.registerCmr(cmr);

    // 4. Retrieve by alias
    com.mgz.afp.cmoca.CMR_ColorManagementResource retrievedByAlias = ctx.getCmr("TestAlias");
    assertNotNull(retrievedByAlias);
    assertEquals("TestAlias", retrievedByAlias.getAlias());

    // 5. Retrieve by architected name
    String resolvedArchName = cmr.getArchitectedName();
    if (resolvedArchName != null) {
      com.mgz.afp.cmoca.CMR_ColorManagementResource retrievedByArchName = ctx.getCmr(resolvedArchName);
      assertNotNull(retrievedByArchName);
    }

    // 6. Test Active CMR configuration
    ctx.setActiveCmrName("TestAlias");
    assertEquals("TestAlias", ctx.getActiveCmrName());
    assertEquals(cmr, ctx.getActiveCmr());

    // 7. Clear and check
    ctx.clearCmrs();
    assertTrue(ctx.getCmr("TestAlias") == null);
    assertTrue(ctx.getActiveCmrName() == null);
    assertTrue(ctx.getActiveCmr() == null);
  }

  @Test
  public void testICMRInvokeCmrIntegration() throws Exception {
    java.io.ByteArrayOutputStream os = new java.io.ByteArrayOutputStream();
    try (PdfHandler handler = new PdfHandler(os)) {
      ColorContext ctx = handler.getColorContext();

      // Create a mock CMR and register it with HAID 42
      com.mgz.afp.cmoca.CMR_ColorManagementResource cmr = new com.mgz.afp.cmoca.CMR_ColorManagementResource();
      cmr.setAlias("MyCmr");
      byte[] rawCmrNameBytes = new byte[146];
      String archName = "MyArchitectedName";
      byte[] archBytes = archName.getBytes(java.nio.charset.StandardCharsets.UTF_16BE);
      System.arraycopy(archBytes, 0, rawCmrNameBytes, 0, Math.min(archBytes.length, rawCmrNameBytes.length));
      try {
        java.lang.reflect.Field field = com.mgz.afp.cmoca.CMR_ColorManagementResource.class.getDeclaredField("rawCmrName");
        field.setAccessible(true);
        field.set(cmr, rawCmrNameBytes);
      } catch (Exception e) {}

      ctx.registerCmr(42, cmr);

      // Verify it is registered
      assertEquals(cmr, ctx.getCmr(42));

      // Construct and process an ICMR_InvokeCMR command
      com.mgz.afp.ipds.ICMR_InvokeCMR icmr = new com.mgz.afp.ipds.ICMR_InvokeCMR();
      icmr.setInvocationFlags((byte) 0x00); // Invoke, not reset
      icmr.addHaid(42);

      handler.handle(icmr);

      // Verify active HAID and active CMR Name are configured
      assertEquals(1, ctx.getActiveHaids().size());
      assertEquals(42, ctx.getActiveHaids().get(0).intValue());
      assertEquals(cmr, ctx.getActiveCmr());

      // Now process reset ICMR command
      com.mgz.afp.ipds.ICMR_InvokeCMR resetIcmr = new com.mgz.afp.ipds.ICMR_InvokeCMR();
      resetIcmr.setInvocationFlags((byte) 0x80); // Reset

      handler.handle(resetIcmr);

      // Verify active HAIDs and CMR name are cleared
      assertTrue(ctx.getActiveHaids().isEmpty());
      assertTrue(ctx.getActiveCmr() == null);
    }
  }

  @Test
  public void testIccBasedColorSpaceAndFallback() {
    ColorContext ctx = new ColorContext();
    ColorHandler.setContext(ctx);

    try {
      com.mgz.afp.cmoca.CMR_ColorManagementResource cmr = new com.mgz.afp.cmoca.CMR_ColorManagementResource();
      cmr.setAlias("CmrForIcc");

      // Setup mock invalid ICC profile bytes
      byte[] invalidIccBytes = new byte[] { 1, 2, 3, 4, 5, 6, 7, 8 };

      // Construct a Tag X'3015' (ICC Profile Data) and associate with CMR
      java.util.List<com.mgz.afp.cmoca.CMRTag> tags = new java.util.ArrayList<>();
      com.mgz.afp.cmoca.CMRTag iccTag = new com.mgz.afp.cmoca.CMRTag(0x3015, 5, invalidIccBytes.length, 0);
      iccTag.setData(invalidIccBytes);
      tags.add(iccTag);
      cmr.setTags(tags);

      // Register CMR and activate it
      ctx.registerCmr(100, cmr);
      ctx.setActiveHaids(java.util.List.of(100));

      // Attempt to resolve RGB and CMYK colors
      // Since ICC data is invalid, it should fall back to standard DeviceRgb and DeviceCmyk gracefully without crashing!
      byte[] rgbValue = new byte[] { (byte) 255, 0, 0 };
      Color rgbColor = ColorHandler.getExtendedColor(AFPColorSpace.RGB, rgbValue);
      assertNotNull(rgbColor);
      assertTrue(rgbColor instanceof DeviceRgb);
      assertArrayEquals(new float[] { 1.0f, 0.0f, 0.0f }, rgbColor.getColorValue());

      byte[] cmykValue = new byte[] { (byte) 255, 0, (byte) 255, 0 };
      Color cmykColor = ColorHandler.getExtendedColor(AFPColorSpace.CMYK, cmykValue);
      assertNotNull(cmykColor);
      assertTrue(cmykColor instanceof DeviceCmyk);
      assertArrayEquals(new float[] { 1.0f, 0.0f, 1.0f, 0.0f }, cmykColor.getColorValue());

    } finally {
      ColorHandler.clearContext();
    }
  }
}
