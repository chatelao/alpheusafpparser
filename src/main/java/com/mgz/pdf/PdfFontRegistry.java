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

import com.itextpdf.io.font.FontProgram;
import com.itextpdf.io.font.FontProgramFactory;
import com.itextpdf.io.font.constants.StandardFonts;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.layout.font.FontProvider;
import com.itextpdf.layout.font.FontSet;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Registry for mapping AFP font names to iText {@link PdfFont} instances.
 */
public class PdfFontRegistry {

  private final Map<String, PdfFont> afpNameCache = new ConcurrentHashMap<>();
  private final Map<String, PdfFont> standardFontCache = new ConcurrentHashMap<>();
  private final FontProvider fontProvider;
  private final FontSet fontSet;
  private PdfFont defaultFont;

  public PdfFontRegistry() {
    this.fontSet = new FontSet();
    this.fontProvider = new FontProvider(fontSet);
    this.fontProvider.addStandardPdfFonts();

    try {
      this.defaultFont = PdfFontFactory.createFont(StandardFonts.HELVETICA);
    } catch (Exception e) {
      // Should not happen
    }
  }

  public void registerFont(String fontName, PdfFont font) {
    if (fontName != null && font != null) {
      afpNameCache.put(fontName.trim(), font);
    }
  }

  public PdfFont getFont(String fontName) {
    if (fontName == null) {
      return null;
    }
    String trimmedName = fontName.trim();
    PdfFont font = afpNameCache.get(trimmedName);
    if (font == null) {
      font = mapToStandardFont(trimmedName);
      if (font != null) {
        afpNameCache.put(trimmedName, font);
      }
    }
    return font;
  }

  private PdfFont mapToStandardFont(String afpFontName) {
    if (afpFontName.length() < 4 || (!afpFontName.startsWith("C0") && !afpFontName.startsWith("X0"))) {
      return null;
    }

    String mappingName = "C0" + afpFontName.substring(2);
    String prefix = mappingName.substring(0, 3);
    char style = mappingName.charAt(3);
    String standardFontName = null;

    if (prefix.equals("C0H") || prefix.equals("C0S")) {
      standardFontName = switch (style) {
        case '3' -> StandardFonts.HELVETICA_BOLD;
        case '4' -> StandardFonts.HELVETICA_OBLIQUE;
        case '5' -> StandardFonts.HELVETICA_BOLDOBLIQUE;
        default -> StandardFonts.HELVETICA;
      };
    } else if (prefix.equals("C0N") || prefix.equals("C0D")) {
      standardFontName = switch (style) {
        case '3' -> StandardFonts.TIMES_BOLD;
        case '4' -> StandardFonts.TIMES_ITALIC;
        case '5' -> StandardFonts.TIMES_BOLDITALIC;
        default -> StandardFonts.TIMES_ROMAN;
      };
    } else if (prefix.equals("C04") || prefix.equals("C06")) {
      standardFontName = switch (style) {
        case '3' -> StandardFonts.COURIER_BOLD;
        case '4' -> StandardFonts.COURIER_OBLIQUE;
        case '5' -> StandardFonts.COURIER_BOLDOBLIQUE;
        default -> StandardFonts.COURIER;
      };
    }

    if (standardFontName != null) {
      final String finalStandardFontName = standardFontName;
      return standardFontCache.computeIfAbsent(finalStandardFontName, k -> {
        try {
          return PdfFontFactory.createFont(finalStandardFontName, null, PdfFontFactory.EmbeddingStrategy.FORCE_EMBEDDED);
        } catch (Exception e) {
          try {
            return PdfFontFactory.createFont(finalStandardFontName);
          } catch (Exception ex) {
            return null;
          }
        }
      });
    }

    return null;
  }

  public PdfFont getFontWithFallback(String fontName) {
    PdfFont font = getFont(fontName);
    return (font != null) ? font : defaultFont;
  }

  public static float extractSizeFromName(String afpFontName) {
    if (afpFontName == null || afpFontName.isEmpty()) {
      return 10.0f;
    }

    String trimmed = afpFontName.trim();
    int len = trimmed.length();
    if (len < 6) {
      return 10.0f;
    }

    for (int d = 3; d >= 2; d--) {
      if (len >= d) {
        String sub = trimmed.substring(len - d);
        if (sub.matches("\\d+")) {
          try {
            int size = Integer.parseInt(sub);
            if (size >= 4 && size <= 144) {
              return (float) size;
            }
          } catch (NumberFormatException e) {
            // Ignored
          }
        }
      }
    }
    return 10.0f;
  }

  public PdfFont getDefaultFont() {
    return defaultFont;
  }

  public void setDefaultFont(PdfFont defaultFont) {
    if (defaultFont != null) {
      this.defaultFont = defaultFont;
    }
  }

  public boolean hasFont(String fontName) {
    if (fontName == null) {
      return false;
    }
    return afpNameCache.containsKey(fontName.trim());
  }

  public FontProvider getFontProvider() {
    return fontProvider;
  }

  public FontSet getFontSet() {
    return fontSet;
  }

  public void clear() {
    afpNameCache.clear();
    standardFontCache.clear();
  }
}
