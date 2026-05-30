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
import com.itextpdf.kernel.colors.DeviceGray;
import com.itextpdf.kernel.colors.DeviceRgb;
import com.mgz.afp.enums.AFPColorSpace;
import com.mgz.afp.enums.AFPColorValue;

/**
 * Utility for mapping AFP color values to iText PDF colors.
 */
public class ColorHandler {

  /**
   * Converts an {@link AFPColorValue} to an iText {@link Color}.
   *
   * @param afpColor the AFP color value
   * @return the iText color (DeviceRgb)
   */
  public static Color getColor(AFPColorValue afpColor) {
    if (afpColor == null) {
      return DeviceRgb.BLACK;
    }
    int[] rgb = afpColor.toRgb();
    return new DeviceRgb(rgb[0], rgb[1], rgb[2]);
  }

  /**
   * Converts extended color data to an iText {@link Color}.
   *
   * @param colorSpace the AFP color space
   * @param colorValue the raw color value bytes
   * @return the iText color, or null if mapping is not supported
   */
  public static Color getExtendedColor(AFPColorSpace colorSpace, byte[] colorValue) {
    if (colorSpace == null || colorValue == null || colorValue.length == 0) {
      return null;
    }

    switch (colorSpace) {
      case RGB:
        if (colorValue.length >= 3) {
          return new DeviceRgb(colorValue[0] & 0xFF, colorValue[1] & 0xFF, colorValue[2] & 0xFF);
        }
        break;
      case CMYK:
        if (colorValue.length >= 4) {
          return new DeviceCmyk(
              (colorValue[0] & 0xFF) / 255.0f,
              (colorValue[1] & 0xFF) / 255.0f,
              (colorValue[2] & 0xFF) / 255.0f,
              (colorValue[3] & 0xFF) / 255.0f);
        }
        break;
      case StandardOCA:
        // For StandardOCA, the first byte is often used as a color index similar to STC
        try {
          return getColor(AFPColorValue.valueOf(colorValue[0] & 0xFF));
        } catch (Exception e) {
          return DeviceRgb.BLACK;
        }
      default:
        // TODO: Implement other color spaces (CIELAB, etc.) if needed
        break;
    }
    return null;
  }
}
