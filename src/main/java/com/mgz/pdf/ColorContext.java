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
import com.itextpdf.kernel.colors.DeviceRgb;
import com.mgz.afp.enums.AFPColorValue;

/**
 * Manages the current color configuration context for dynamic/context-aware resolution.
 */
public class ColorContext {
  private Color defaultForegroundColor = DeviceRgb.BLACK;
  private Color defaultBackgroundColor = DeviceRgb.WHITE;
  private Color mediaBackgroundColor = DeviceRgb.WHITE;
  private Color activeStylesheetColor = null;
  private Color formdefDefaultColor = null;

  public Color getDefaultForegroundColor() {
    return defaultForegroundColor;
  }

  public void setDefaultForegroundColor(Color defaultForegroundColor) {
    this.defaultForegroundColor = defaultForegroundColor;
  }

  public Color getDefaultBackgroundColor() {
    return defaultBackgroundColor;
  }

  public void setDefaultBackgroundColor(Color defaultBackgroundColor) {
    this.defaultBackgroundColor = defaultBackgroundColor;
  }

  public Color getMediaBackgroundColor() {
    return mediaBackgroundColor;
  }

  public void setMediaBackgroundColor(Color mediaBackgroundColor) {
    this.mediaBackgroundColor = mediaBackgroundColor;
  }

  public Color getActiveStylesheetColor() {
    return activeStylesheetColor;
  }

  public void setActiveStylesheetColor(Color activeStylesheetColor) {
    this.activeStylesheetColor = activeStylesheetColor;
  }

  public Color getFormdefDefaultColor() {
    return formdefDefaultColor;
  }

  public void setFormdefDefaultColor(Color formdefDefaultColor) {
    this.formdefDefaultColor = formdefDefaultColor;
  }

  /**
   * Resolves default/device-default colors to prevent contrast collisions
   * based on the active background and configuration.
   *
   * @param afpColor the AFP color value
   * @return the resolved iText Color
   */
  public Color resolveDefaultColor(AFPColorValue afpColor) {
    // 1. Check if active stylesheet color is configured
    if (activeStylesheetColor != null) {
      return activeStylesheetColor;
    }
    // 2. Check if formdef default color is configured
    if (formdefDefaultColor != null) {
      return formdefDefaultColor;
    }

    // Determine active background color
    Color activeBg = mediaBackgroundColor != null ? mediaBackgroundColor : defaultBackgroundColor;
    if (activeBg instanceof DeviceRgb rgbBg) {
      float[] rgbValue = rgbBg.getColorValue();
      if (rgbValue != null && rgbValue.length >= 3) {
        float r = rgbValue[0];
        float g = rgbValue[1];
        float b = rgbValue[2];

        // Standard relative luminance formula
        double luminance = 0.2126 * r + 0.7152 * g + 0.0722 * b;

        if (luminance < 0.5) {
          // Dark background -> Resolve defaults to white to avoid contrast collision
          return DeviceRgb.WHITE;
        } else {
          // Light background -> Resolve to standard foreground color (usually black)
          return defaultForegroundColor != null ? defaultForegroundColor : DeviceRgb.BLACK;
        }
      }
    }

    return defaultForegroundColor != null ? defaultForegroundColor : DeviceRgb.BLACK;
  }
}
