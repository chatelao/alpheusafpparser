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
  public void testTimesMapping() {
    PdfFontRegistry registry = new PdfFontRegistry();

    PdfFont fontPlain = registry.getFont("C0N20010");
    assertNotNull(fontPlain);
    assertTrue(fontPlain.getFontProgram().getFontNames().getFontName().contains("Times-Roman"));

    PdfFont fontBold = registry.getFont("C0N30010");
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
  }

  @Test
  public void testFallback() {
    PdfFontRegistry registry = new PdfFontRegistry();

    PdfFont defaultFont = registry.getDefaultFont();
    PdfFont font = registry.getFontWithFallback("UNKNOWN");

    assertSame(defaultFont, font, "Should fallback to default font");
  }
}
