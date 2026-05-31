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

import com.mgz.afp.enums.AFPColorValue;

/**
 * Tracks the active GOCA graphics state for PDF conversion.
 * Stores attributes like color, line width, and line type.
 */
public class PdfGraphicsState {

  private AFPColorValue color = AFPColorValue.DeviceDefault_0x00;
  private short lineWidth = 0;
  private short lineType = 0;
  private int currentX = 0;
  private int currentY = 0;

  /**
   * Resets the graphics state to default values.
   */
  public void reset() {
    this.color = AFPColorValue.DeviceDefault_0x00;
    this.lineWidth = 0;
    this.lineType = 0;
    this.currentX = 0;
    this.currentY = 0;
  }

  public AFPColorValue getColor() {
    return color;
  }

  public void setColor(AFPColorValue color) {
    this.color = color;
  }

  public short getLineWidth() {
    return lineWidth;
  }

  public void setLineWidth(short lineWidth) {
    this.lineWidth = lineWidth;
  }

  public short getLineType() {
    return lineType;
  }

  public void setLineType(short lineType) {
    this.lineType = lineType;
  }

  public int getCurrentX() {
    return currentX;
  }

  public void setCurrentX(int currentX) {
    this.currentX = currentX;
  }

  public int getCurrentY() {
    return currentY;
  }

  public void setCurrentY(int currentY) {
    this.currentY = currentY;
  }
}
