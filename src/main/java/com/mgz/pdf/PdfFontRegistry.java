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
import com.itextpdf.kernel.font.PdfFontFactory;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Registry for mapping AFP font names to iText {@link PdfFont} instances.
 */
public class PdfFontRegistry {

  private final Map<String, PdfFont> registry = new ConcurrentHashMap<>();
  private PdfFont defaultFont;

  public PdfFontRegistry() {
    try {
      // PDF/A and PDF/X require fonts to be embedded.
      // StandardFonts are usually not embedded by default in iText.
      this.defaultFont = PdfFontFactory.createFont(StandardFonts.HELVETICA);
    } catch (Exception e) {
      // Should not happen with StandardFonts
    }
  }

  /**
   * Registers a font under the given AFP font name.
   *
   * @param fontName the AFP font name (e.g., from MCF or MDR)
   * @param font the iText {@link PdfFont} instance
   */
  public void registerFont(String fontName, PdfFont font) {
    if (fontName != null && font != null) {
      registry.put(fontName.trim(), font);
    }
  }

  /**
   * Retrieves a font by its AFP font name.
   *
   * @param fontName the AFP font name
   * @return the {@link PdfFont}, or null if not registered
   */
  public PdfFont getFont(String fontName) {
    if (fontName == null) {
      return null;
    }
    String trimmedName = fontName.trim();
    PdfFont font = registry.get(trimmedName);
    if (font == null) {
      font = mapToStandardFont(trimmedName);
      if (font != null) {
        registry.put(trimmedName, font);
      }
    }
    return font;
  }

  /**
   * Maps a standard AFP font name to a PDF standard font.
   * AFP font names often follow the pattern C0xxxxxx or X0xxxxxx.
   *
   * @param afpFontName the AFP font name
   * @return the mapped {@link PdfFont}, or null if no mapping found
   */
  private PdfFont mapToStandardFont(String afpFontName) {
    if (afpFontName.length() < 4 || (!afpFontName.startsWith("C0") && !afpFontName.startsWith("X0"))) {
      return null;
    }

    // Standardize to C0 prefix for mapping logic
    String mappingName = "C0" + afpFontName.substring(2);
    String prefix = mappingName.substring(0, 3);
    char style = mappingName.charAt(3);
    String standardFontName = null;

    if (prefix.equals("C0H") || prefix.equals("C0S")) { // Helvetica / Swiss
      standardFontName = switch (style) {
        case '3' -> StandardFonts.HELVETICA_BOLD;
        case '4' -> StandardFonts.HELVETICA_OBLIQUE;
        case '5' -> StandardFonts.HELVETICA_BOLDOBLIQUE;
        default -> StandardFonts.HELVETICA;
      };
    } else if (prefix.equals("C0N") || prefix.equals("C0D")) { // Times New Roman / Dutch
      standardFontName = switch (style) {
        case '3' -> StandardFonts.TIMES_BOLD;
        case '4' -> StandardFonts.TIMES_ITALIC;
        case '5' -> StandardFonts.TIMES_BOLDITALIC;
        default -> StandardFonts.TIMES_ROMAN;
      };
    } else if (prefix.equals("C04") || prefix.equals("C06")) { // Courier
      standardFontName = switch (style) {
        case '3' -> StandardFonts.COURIER_BOLD;
        case '4' -> StandardFonts.COURIER_OBLIQUE;
        case '5' -> StandardFonts.COURIER_BOLDOBLIQUE;
        default -> StandardFonts.COURIER;
      };
    }

    if (standardFontName != null) {
      try {
        // For PDF/VT and PDF/X-4 compliance, we should ideally embed fonts.
        // iText 9 createFont with StandardFonts usually creates a non-embedded font.
        return PdfFontFactory.createFont(standardFontName);
      } catch (Exception e) {
        // Fallback or log error
      }
    }

    return null;
  }

  /**
   * Retrieves a font by its AFP font name, falling back to a default font if not found.
   *
   * @param fontName the AFP font name
   * @return the {@link PdfFont}, or the default font if not registered
   */
  public PdfFont getFontWithFallback(String fontName) {
    PdfFont font = getFont(fontName);
    return (font != null) ? font : defaultFont;
  }

  /**
   * Extracts the point size from a standard AFP font name.
   * e.g., 'C0H20012' -> 12.0f
   *
   * @param afpFontName the font name
   * @return the extracted size, or 10.0f as default
   */
  public static float extractSizeFromName(String afpFontName) {
    if (afpFontName == null || afpFontName.isEmpty()) {
      return 10.0f;
    }

    String trimmed = afpFontName.trim();
    int len = trimmed.length();

    // Try to extract up to 3 digits from the end
    for (int d = 3; d >= 1; d--) {
      if (len >= d) {
        String sub = trimmed.substring(len - d);
        if (sub.matches("\\d+")) {
          try {
            int size = Integer.parseInt(sub);
            if (size > 0 && size <= 144) {
              return (float) size;
            }
          } catch (NumberFormatException e) {
            // Should not happen with matches("\\d+")
          }
        }
      }
    }
    return 10.0f;
  }

  /**
   * Returns the default fallback font.
   *
   * @return the default font
   */
  public PdfFont getDefaultFont() {
    return defaultFont;
  }

  /**
   * Sets the default fallback font.
   *
   * @param defaultFont the new default font
   */
  public void setDefaultFont(PdfFont defaultFont) {
    if (defaultFont != null) {
      this.defaultFont = defaultFont;
    }
  }

  /**
   * Checks if a font is registered.
   *
   * @param fontName the AFP font name
   * @return true if registered
   */
  public boolean hasFont(String fontName) {
    if (fontName == null) {
      return false;
    }
    return registry.containsKey(fontName.trim());
  }

  /**
   * Clears all registered fonts.
   */
  public void clear() {
    registry.clear();
  }
}
