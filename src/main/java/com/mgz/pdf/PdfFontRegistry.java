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
    // Add standard fonts to the provider to support basic resolution
    this.fontProvider.addStandardPdfFonts();

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
      afpNameCache.put(fontName.trim(), font);
    }
  }

  /**
   * Retrieves a font by its AFP font name.
   *
   * @param fontName the AFP font name
   * @return the {@link PdfFont}, or null if not registered
   */
  public PdfFont getFont(String fontName) {
    return getFont(fontName, false, false);
  }

  /**
   * Retrieves a font by its AFP font name, with style hints.
   *
   * @param fontName the AFP font name
   * @param bold whether the font should be bold
   * @param italic whether the font should be italic
   * @return the {@link PdfFont}, or null if not registered
   */
  public PdfFont getFont(String fontName, boolean bold, boolean italic) {
    if (fontName == null) {
      return null;
    }
    String trimmedName = fontName.trim();
    String cacheKey = trimmedName + (bold ? "_B" : "") + (italic ? "_I" : "");
    PdfFont font = afpNameCache.get(cacheKey);
    if (font == null) {
      font = mapToStandardFont(trimmedName, bold, italic);
      if (font != null) {
        afpNameCache.put(cacheKey, font);
      }
    }
    return font;
  }

  /**
   * Maps a standard AFP font name to a PDF standard font.
   * AFP font names often follow the pattern C0xxxxxx or X0xxxxxx.
   *
   * @param afpFontName the AFP font name
   * @param boldHint whether the font should be bold (overrides name mapping if set)
   * @param italicHint whether the font should be italic (overrides name mapping if set)
   * @return the mapped {@link PdfFont}, or null if no mapping found
   */
  private PdfFont mapToStandardFont(String afpFontName, boolean boldHint, boolean italicHint) {
    if (afpFontName.length() < 4) {
      return null;
    }

    String prefix = afpFontName.substring(0, 2);
    // Supported prefixes: C0-C6, X0-X6 (Raster), CZ, XZ (Outline)
    if (!prefix.matches("[CX][0-6Z]")) {
      return null;
    }

    boolean isOutline = prefix.charAt(1) == 'Z';

    // Standardize to C0 prefix for family mapping logic
    String familyPrefix = "C0" + afpFontName.substring(2, 3);
    char styleDigit = afpFontName.charAt(3);
    String standardFontName = null;

    boolean isBold = boldHint;
    boolean isItalic = italicHint;

    if (!boldHint && !italicHint) {
      // Extract from style digit if no hints provided
      if (isOutline) {
        // CZ/XZ Outline style mapping: 3=Italic, 4=Bold, 5=Bold Italic
        isBold = (styleDigit == '4' || styleDigit == '5');
        isItalic = (styleDigit == '3' || styleDigit == '5');
      } else {
        // Raster style mapping: 3=Bold, 4=Italic, 5=Bold Italic
        isBold = (styleDigit == '3' || styleDigit == '5');
        isItalic = (styleDigit == '4' || styleDigit == '5');
      }
    }

    if (familyPrefix.equals("C0H") || familyPrefix.equals("C0S")
        || familyPrefix.equals("C0A") || familyPrefix.equals("C0U")
        || familyPrefix.equals("C0G") || familyPrefix.equals("C0N")) {
      // Helvetica / Swiss / Arial / Univers / Gothic / Nimbus Sans
      if (isBold && isItalic) {
        standardFontName = StandardFonts.HELVETICA_BOLDOBLIQUE;
      } else if (isBold) {
        standardFontName = StandardFonts.HELVETICA_BOLD;
      } else if (isItalic) {
        standardFontName = StandardFonts.HELVETICA_OBLIQUE;
      } else {
        standardFontName = StandardFonts.HELVETICA;
      }
    } else if (familyPrefix.equals("C0D")
        || familyPrefix.equals("C0T")) { // Dutch / Times (Serif)
      if (isBold && isItalic) {
        standardFontName = StandardFonts.TIMES_BOLDITALIC;
      } else if (isBold) {
        standardFontName = StandardFonts.TIMES_BOLD;
      } else if (isItalic) {
        standardFontName = StandardFonts.TIMES_ITALIC;
      } else {
        standardFontName = StandardFonts.TIMES_ROMAN;
      }
    } else if (familyPrefix.equals("C04") || familyPrefix.equals("C06")
        || familyPrefix.equals("C0M") || familyPrefix.equals("C0C")) { // Courier / Modern / Courier
      if (isBold && isItalic) {
        standardFontName = StandardFonts.COURIER_BOLDOBLIQUE;
      } else if (isBold) {
        standardFontName = StandardFonts.COURIER_BOLD;
      } else if (isItalic) {
        standardFontName = StandardFonts.COURIER_OBLIQUE;
      } else {
        standardFontName = StandardFonts.COURIER;
      }
    }

    if (standardFontName != null) {
      final String finalStandardFontName = standardFontName;
      return standardFontCache.computeIfAbsent(finalStandardFontName, k -> {
        try {
          // For PDF/VT and PDF/X-4 compliance, we should ideally embed fonts.
          // iText 9 createFont with StandardFonts usually creates a non-embedded font.
          // Passing embedding = true and subset = true for compliance.
          return PdfFontFactory.createFont(finalStandardFontName, null, PdfFontFactory.EmbeddingStrategy.FORCE_EMBEDDED);
        } catch (Exception e) {
          // Fallback
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

  /**
   * Retrieves a font by its AFP font name, falling back to a default font if not found.
   *
   * @param fontName the AFP font name
   * @return the {@link PdfFont}, or the default font if not registered
   */
  public PdfFont getFontWithFallback(String fontName) {
    return getFontWithFallback(fontName, false, false);
  }

  /**
   * Retrieves a font by its AFP font name with style hints, falling back to a default font if not found.
   *
   * @param fontName the AFP font name
   * @param bold whether the font should be bold
   * @param italic whether the font should be italic
   * @return the {@link PdfFont}, or the default font if not registered
   */
  public PdfFont getFontWithFallback(String fontName, boolean bold, boolean italic) {
    PdfFont font = getFont(fontName, bold, italic);
    return (font != null) ? font : defaultFont;
  }

  /**
   * Extracts the point size from a standard AFP font name.
   * e.g., 'C0H20012' -> 12.0f, 'C0H200A0' -> 10.0f
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

    // AFP font names with size usually have at least 8 characters: C0H20012
    if (len < 6) {
      return 10.0f;
    }

    // Try to extract digits from the end, but only if they follow a common pattern.
    // Standard names are C0xxxxxx where the last 2 or 3 digits are size.
    // However, some might have A0 etc. which is NOT a size.

    // Look for last 3 digits
    for (int d = 3; d >= 2; d--) {
      if (len >= d) {
        String sub = trimmed.substring(len - d);
        if (sub.matches("\\d+")) {
          try {
            int size = Integer.parseInt(sub);
            // Reasonable font sizes are 4 to 144 points
            if (size >= 4 && size <= 144) {
              return (float) size;
            }
          } catch (NumberFormatException e) {
            // Should not happen
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
    return afpNameCache.containsKey(fontName.trim());
  }

  /**
   * Returns the font provider.
   *
   * @return the font provider
   */
  public FontProvider getFontProvider() {
    return fontProvider;
  }

  /**
   * Returns the font set.
   *
   * @return the font set
   */
  public FontSet getFontSet() {
    return fontSet;
  }

  /**
   * Clears all registered fonts.
   */
  public void clear() {
    afpNameCache.clear();
    standardFontCache.clear();
  }
}
