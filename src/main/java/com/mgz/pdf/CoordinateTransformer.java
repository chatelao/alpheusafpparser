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

import com.itextpdf.kernel.geom.AffineTransform;
import com.mgz.afp.enums.AFPUnitBase;

/**
 * Utility for mapping AFP coordinate space to PDF user space.
 * AFP origin: top-left.
 * PDF origin: bottom-left.
 */
public class CoordinateTransformer {

  /**
   * Calculates the scale factor to convert AFP units to PDF points.
   *
   * @param base the AFP unit base
   * @param unitsPerBase the number of units per base
   * @return the scale factor (points per AFP unit)
   */
  public static float getScaleFactor(AFPUnitBase base, short unitsPerBase) {
    if (unitsPerBase <= 0) {
      return 0.0f;
    }
    if (base == AFPUnitBase.Inches10) {
      return 720.0f / unitsPerBase;
    } else if (base == AFPUnitBase.Centimeter10) {
      return (720.0f / 2.54f) / unitsPerBase;
    }
    return 0.0f;
  }

  /**
   * Creates an affine transform to map AFP coordinates to PDF user space.
   * Scales the coordinates and flips the Y-axis.
   *
   * @param pageHeightPoints the height of the PDF page in points
   * @param scaleX the horizontal scale factor
   * @param scaleY the vertical scale factor
   * @return the affine transform
   */
  public static AffineTransform getAfpToPdfTransform(float pageHeightPoints, float scaleX, float scaleY) {
    // PDF: x' = x * scaleX
    //      y' = pageHeightPoints - (y * scaleY)
    // AffineTransform(a, b, c, d, e, f)
    // x' = a*x + c*y + e
    // y' = b*x + d*y + f
    return new AffineTransform(scaleX, 0, 0, -scaleY, 0, pageHeightPoints);
  }
}
