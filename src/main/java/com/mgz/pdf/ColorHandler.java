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
import com.itextpdf.kernel.colors.Lab;
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
      case CIELAB:
        if (colorValue.length >= 3) {
          // Standard D65 white point
          float[] whitePoint = new float[] {0.95047f, 1.00000f, 1.08883f};
          // Map L, a, b from bytes
          float l = (colorValue[0] & 0xFF) * 100.0f / 255.0f;
          float a = (colorValue[1] & 0xFF) - 128.0f;
          float b = (colorValue[2] & 0xFF) - 128.0f;
          return new Lab(whitePoint, new float[] {l, a, b});
        }
        break;
      case Highlight:
        if (colorValue.length >= 2) {
          int colorNum = ((colorValue[0] & 0xFF) << 8) | (colorValue[1] & 0xFF);
          int coverage = 100;
          if (colorValue.length >= 3) {
            coverage = colorValue[2] & 0xFF;
            if (coverage > 100) {
              coverage = 100;
            }
          }
          int shading = 0;
          if (colorValue.length >= 4) {
            shading = colorValue[3] & 0xFF;
            if (shading > 100) {
              shading = 100;
            }
          }

          // Map highlight color number to a vibrant RGB base color
          int baseR, baseG, baseB;
          switch (colorNum) {
            case 0: // Presentation device default color -> default to black
              baseR = 0; baseG = 0; baseB = 0;
              break;
            case 1: // Red
              baseR = 255; baseG = 0; baseB = 0;
              break;
            case 2: // Pink / Magenta
              baseR = 255; baseG = 0; baseB = 255;
              break;
            case 3: // Green
              baseR = 0; baseG = 255; baseB = 0;
              break;
            case 4: // Yellow
              baseR = 255; baseG = 255; baseB = 0;
              break;
            case 5: // Blue
              baseR = 0; baseG = 0; baseB = 255;
              break;
            case 6: // Orange
              baseR = 255; baseG = 128; baseB = 0;
              break;
            case 7: // Cyan
              baseR = 0; baseG = 255; baseB = 255;
              break;
            default: // Map other numbers to Red as standard fallback
              baseR = 255; baseG = 0; baseB = 0;
              break;
          }

          // Apply percent coverage: adds (100 - coverage)% white
          float tint = coverage / 100.0f;
          int r = Math.round(baseR * tint + 255 * (1 - tint));
          int g = Math.round(baseG * tint + 255 * (1 - tint));
          int b = Math.round(baseB * tint + 255 * (1 - tint));

          // Apply percent shading: adds shading% black
          float shade = shading / 100.0f;
          r = Math.round(r * (1 - shade));
          g = Math.round(g * (1 - shade));
          b = Math.round(b * (1 - shade));

          // Clip to valid 0-255 range
          r = Math.max(0, Math.min(255, r));
          g = Math.max(0, Math.min(255, g));
          b = Math.max(0, Math.min(255, b));

          return new DeviceRgb(r, g, b);
        }
        break;
      case YCrCb:
      case YCbCr:
        if (colorValue.length >= 3) {
          double yVal = (colorValue[0] & 0xFF);
          double cbVal, crVal;
          if (colorSpace == AFPColorSpace.YCrCb) {
            crVal = (colorValue[1] & 0xFF);
            cbVal = (colorValue[2] & 0xFF);
          } else {
            cbVal = (colorValue[1] & 0xFF);
            crVal = (colorValue[2] & 0xFF);
          }

          double r = yVal + 1.402 * (crVal - 128.0);
          double g = yVal - 0.344136 * (cbVal - 128.0) - 0.714136 * (crVal - 128.0);
          double b = yVal + 1.772 * (cbVal - 128.0);

          int rInt = Math.max(0, Math.min(255, (int) Math.round(r)));
          int gInt = Math.max(0, Math.min(255, (int) Math.round(g)));
          int bInt = Math.max(0, Math.min(255, (int) Math.round(b)));

          return new DeviceRgb(rInt, gInt, bInt);
        } else if (colorValue.length > 0) {
          int yVal = colorValue[0] & 0xFF;
          return new DeviceRgb(yVal, yVal, yVal);
        }
        break;
      default:
        break;
    }
    return null;
  }
}
