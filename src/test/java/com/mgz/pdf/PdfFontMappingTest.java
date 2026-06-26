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

import com.itextpdf.io.font.constants.StandardFonts;
import com.itextpdf.kernel.font.PdfFont;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Tests for font mapping in {@link PdfFontRegistry}.
 */
public class PdfFontMappingTest {

  @Test
  public void testOutlineMapping() {
    PdfFontRegistry registry = new PdfFontRegistry();

    // CZ Outline fonts: 2=Normal, 3=Italic, 4=Bold, 5=Bold Italic
    PdfFont fontPlain = registry.getFont("CZH282"); // Swiss Plain
    assertNotNull(fontPlain);
    assertTrue(fontPlain.getFontProgram().getFontNames().getFontName().contains("Helvetica"));

    PdfFont fontBold = registry.getFont("CZH483"); // Swiss Bold
    assertNotNull(fontBold);
    assertTrue(fontBold.getFontProgram().getFontNames().getFontName().contains("Helvetica-Bold"));

    PdfFont fontItalic = registry.getFont("CZH382"); // Swiss Italic
    assertNotNull(fontItalic);
    assertTrue(fontItalic.getFontProgram().getFontNames().getFontName().contains("Helvetica-Oblique"));

    PdfFont fontBoldItalic = registry.getFont("CZH583"); // Swiss Bold Italic
    assertNotNull(fontBoldItalic);
    assertTrue(fontBoldItalic.getFontProgram().getFontNames().getFontName().contains("Helvetica-BoldOblique"));
  }

  @Test
  public void testHintMapping() {
    PdfFontRegistry registry = new PdfFontRegistry();

    // Use a font name that would normally be plain
    PdfFont fontBoldHint = registry.getFont("C0H20010", true, false);
    assertNotNull(fontBoldHint);
    assertTrue(fontBoldHint.getFontProgram().getFontNames().getFontName().contains("Helvetica-Bold"));

    PdfFont fontItalicHint = registry.getFont("C0H20010", false, true);
    assertNotNull(fontItalicHint);
    assertTrue(fontItalicHint.getFontProgram().getFontNames().getFontName().contains("Helvetica-Oblique"));

    PdfFont fontBothHint = registry.getFont("C0H20010", true, true);
    assertNotNull(fontBothHint);
    assertTrue(fontBothHint.getFontProgram().getFontNames().getFontName().contains("Helvetica-BoldOblique"));

    // Verify caching with hints
    PdfFont fontPlain = registry.getFont("C0H20010");
    assertTrue(fontPlain.getFontProgram().getFontNames().getFontName().contains("Helvetica"));
    assertSame(fontBoldHint, registry.getFont("C0H20010", true, false));
  }

  @Test
  public void testHelveticaMapping() {
    PdfFontRegistry registry = new PdfFontRegistry();

    PdfFont fontPlain = registry.getFont("C0H20010");
    assertNotNull(fontPlain);
    assertTrue(fontPlain.getFontProgram().getFontNames().getFontName().contains("Helvetica"));

    PdfFont fontBold = registry.getFont("C0H30010");
    assertNotNull(fontBold);
    assertTrue(fontBold.getFontProgram().getFontNames().getFontName().contains("Helvetica-Bold"));

    PdfFont fontItalic = registry.getFont("C0H40010");
    assertNotNull(fontItalic);
    assertTrue(fontItalic.getFontProgram().getFontNames().getFontName().contains("Helvetica-Oblique"));

    PdfFont fontBoldItalic = registry.getFont("C0H50010");
    assertNotNull(fontBoldItalic);
    assertTrue(fontBoldItalic.getFontProgram().getFontNames().getFontName().contains("Helvetica-BoldOblique"));
  }

  @Test
  public void testX0PrefixMapping() {
    PdfFontRegistry registry = new PdfFontRegistry();

    PdfFont fontPlain = registry.getFont("X0H20010");
    assertNotNull(fontPlain);
    assertTrue(fontPlain.getFontProgram().getFontNames().getFontName().contains("Helvetica"));

    PdfFont fontBold = registry.getFont("X0H30010");
    assertNotNull(fontBold);
    assertTrue(fontBold.getFontProgram().getFontNames().getFontName().contains("Helvetica-Bold"));
  }

  @Test
  public void testSizeExtraction() {
    assertEquals(10.0f, PdfFontRegistry.extractSizeFromName("C0H20010"), 0.01f);
    assertEquals(12.0f, PdfFontRegistry.extractSizeFromName("C0H20012"), 0.01f);
    assertEquals(8.0f, PdfFontRegistry.extractSizeFromName("X0H20008"), 0.01f);
    assertEquals(144.0f, PdfFontRegistry.extractSizeFromName("C0H20144"), 0.01f);
    assertEquals(10.0f, PdfFontRegistry.extractSizeFromName("C0H20000"), 0.01f); // Invalid size
    assertEquals(10.0f, PdfFontRegistry.extractSizeFromName("C0H200XX"), 0.01f); // Not a number
    assertEquals(10.0f, PdfFontRegistry.extractSizeFromName(null), 0.01f);
    assertEquals(10.0f, PdfFontRegistry.extractSizeFromName(""), 0.01f);
  }

  @Test
  public void testSwissMapping() {
    PdfFontRegistry registry = new PdfFontRegistry();

    PdfFont fontPlain = registry.getFont("C0S20010");
    assertNotNull(fontPlain);
    assertTrue(fontPlain.getFontProgram().getFontNames().getFontName().contains("Helvetica"));

    PdfFont fontBold = registry.getFont("C0S30010");
    assertNotNull(fontBold);
    assertTrue(fontBold.getFontProgram().getFontNames().getFontName().contains("Helvetica-Bold"));
  }

  @Test
  public void testDutchMapping() {
    PdfFontRegistry registry = new PdfFontRegistry();

    PdfFont fontPlain = registry.getFont("C0D20010");
    assertNotNull(fontPlain);
    assertTrue(fontPlain.getFontProgram().getFontNames().getFontName().contains("Times-Roman"));

    PdfFont fontBold = registry.getFont("C0D30010");
    assertNotNull(fontBold);
    assertTrue(fontBold.getFontProgram().getFontNames().getFontName().contains("Times-Bold"));
  }


  @Test
  public void testCourierMapping() {
    PdfFontRegistry registry = new PdfFontRegistry();

    PdfFont fontPlain = registry.getFont("C0420010");
    assertNotNull(fontPlain);
    assertTrue(fontPlain.getFontProgram().getFontNames().getFontName().contains("Courier"));

    PdfFont fontBold = registry.getFont("C0630010");
    assertNotNull(fontBold);
    assertTrue(fontBold.getFontProgram().getFontNames().getFontName().contains("Courier-Bold"));
  }

  @Test
  public void testCaching() {
    PdfFontRegistry registry = new PdfFontRegistry();

    PdfFont font1 = registry.getFont("C0H20010");
    PdfFont font2 = registry.getFont("C0H20010");

    assertSame(font1, font2, "Should return the same instance from cache");

    // Test sharing between different AFP names mapping to same standard font
    PdfFont font3 = registry.getFont("C0H20012");
    assertSame(font1, font3, "Should share the same PdfFont instance for same standard font at different size");

    PdfFont font4 = registry.getFont("C0S20010");
    assertSame(font1, font4, "Should share the same PdfFont instance for Helvetica vs Swiss");
  }

  @Test
  public void testFallback() {
    PdfFontRegistry registry = new PdfFontRegistry();

    PdfFont defaultFont = registry.getDefaultFont();
    PdfFont font = registry.getFontWithFallback("UNKNOWN");

    assertSame(defaultFont, font, "Should fallback to default font");
  }

  @Test
  public void testNimbusSansMapping() {
    PdfFontRegistry registry = new PdfFontRegistry();
    // Nimbus Sans (Sans-Serif) should map to Helvetica, regardless of raster/outline
    PdfFont fontOutline = registry.getFont("CZN481");
    assertNotNull(fontOutline);
    assertTrue(fontOutline.getFontProgram().getFontNames().getFontName().contains("Helvetica"));

    PdfFont fontRaster = registry.getFont("C0N20010");
    assertNotNull(fontRaster);
    assertTrue(fontRaster.getFontProgram().getFontNames().getFontName().contains("Helvetica"));
  }

  @Test
  public void testNewPrefixesAndFamilies() {
    PdfFontRegistry registry = new PdfFontRegistry();

    // Test rotation prefixes (C0-CG)
    PdfFont fontRot90 = registry.getFont("C5H20010"); // Swiss rotation 90
    assertNotNull(fontRot90);
    assertTrue(fontRot90.getFontProgram().getFontNames().getFontName().contains("Helvetica"));

    PdfFont fontRot180 = registry.getFont("C9H20010"); // Swiss rotation 180
    assertNotNull(fontRot180);
    assertTrue(fontRot180.getFontProgram().getFontNames().getFontName().contains("Helvetica"));

    PdfFont fontRot270 = registry.getFont("CDH20010"); // Swiss rotation 270
    assertNotNull(fontRot270);
    assertTrue(fontRot270.getFontProgram().getFontNames().getFontName().contains("Helvetica"));

    // Test Avant Garde (V) -> Helvetica
    PdfFont fontAvant = registry.getFont("C0V20010");
    assertNotNull(fontAvant);
    assertTrue(fontAvant.getFontProgram().getFontNames().getFontName().contains("Helvetica"));

    // Test Palatino (P) -> Times
    PdfFont fontPalatino = registry.getFont("C0P20010");
    assertNotNull(fontPalatino);
    assertTrue(fontPalatino.getFontProgram().getFontNames().getFontName().contains("Times-Roman"));

    // Test Bookman (B) -> Times
    PdfFont fontBookman = registry.getFont("C0B20010");
    assertNotNull(fontBookman);
    assertTrue(fontBookman.getFontProgram().getFontNames().getFontName().contains("Times-Roman"));

    // Test Letter Gothic (L) -> Courier
    PdfFont fontLetterGothic = registry.getFont("C0L20010");
    assertNotNull(fontLetterGothic);
    assertTrue(fontLetterGothic.getFontProgram().getFontNames().getFontName().contains("Courier"));

    // Test OCR-A (K) -> Courier
    PdfFont fontOcrA = registry.getFont("C0K20010");
    assertNotNull(fontOcrA);
    assertTrue(fontOcrA.getFontProgram().getFontNames().getFontName().contains("Courier"));
  }
}
