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
import com.mgz.afp.goca.GAD_DrawingOrder.GSLE_SetLineEnd.LineEnd;
import com.mgz.afp.goca.GAD_DrawingOrder.GSLJ_SetLineJoin.LineJoin;

/**
 * Tracks the active GOCA graphics state for PDF conversion.
 * Stores attributes like color, line width, and line type.
 */
public class PdfGraphicsState {

  private AFPColorValue color = AFPColorValue.DeviceDefault_0x00;
  private short lineWidth = 0;
  private short lineType = 0;
  private LineEnd lineEnd = LineEnd.Default;
  private LineJoin lineJoin = LineJoin.Default;
  private short mixMode = 0;
  private short backgroundMixMode = 0;
  private int currentX = 0;
  private int currentY = 0;

  /**
   * Resets the graphics state to default values.
   */
  public void reset() {
    this.color = AFPColorValue.DeviceDefault_0x00;
    this.lineWidth = 0;
    this.lineType = 0;
    this.lineEnd = LineEnd.Default;
    this.lineJoin = LineJoin.Default;
    this.mixMode = 0;
    this.backgroundMixMode = 0;
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

  public LineEnd getLineEnd() {
    return lineEnd;
  }

  public void setLineEnd(LineEnd lineEnd) {
    this.lineEnd = lineEnd;
  }

  public LineJoin getLineJoin() {
    return lineJoin;
  }

  public void setLineJoin(LineJoin lineJoin) {
    this.lineJoin = lineJoin;
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

  public short getMixMode() {
    return mixMode;
  }

  public void setMixMode(short mixMode) {
    this.mixMode = mixMode;
  }

  public short getBackgroundMixMode() {
    return backgroundMixMode;
  }

  public void setBackgroundMixMode(short backgroundMixMode) {
    this.backgroundMixMode = backgroundMixMode;
  }
}
