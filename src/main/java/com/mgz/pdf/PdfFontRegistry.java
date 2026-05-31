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

import com.itextpdf.kernel.font.PdfFont;
import java.util.HashMap;
import java.util.Map;

/**
 * Registry for mapping AFP font names to iText {@link PdfFont} instances.
 */
public class PdfFontRegistry {

  private final Map<String, PdfFont> registry = new HashMap<>();

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
    return registry.get(fontName.trim());
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
