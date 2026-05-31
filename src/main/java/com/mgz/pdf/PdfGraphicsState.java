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

import com.mgz.afp.enums.AFPColorSpace;
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
  private boolean inArea = false;
  private boolean drawAreaBoundary = false;
  private boolean evenOddRule = true;

  private short arcTransformP = 0;
  private short arcTransformQ = 0;
  private short arcTransformR = 0;
  private short arcTransformS = 0;

  private AFPColorSpace processColorSpace;
  private byte nrOfBitsComponent1;
  private byte nrOfBitsComponent2;
  private byte nrOfBitsComponent3;
  private byte nrOfBitsComponent4;
  private byte[] processColorValue;

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
    this.inArea = false;
    this.drawAreaBoundary = false;
    this.evenOddRule = true;
    this.arcTransformP = 0;
    this.arcTransformQ = 0;
    this.arcTransformR = 0;
    this.arcTransformS = 0;
    this.processColorSpace = null;
    this.nrOfBitsComponent1 = 0;
    this.nrOfBitsComponent2 = 0;
    this.nrOfBitsComponent3 = 0;
    this.nrOfBitsComponent4 = 0;
    this.processColorValue = null;
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

  public boolean isInArea() {
    return inArea;
  }

  public void setInArea(boolean inArea) {
    this.inArea = inArea;
  }

  public boolean isDrawAreaBoundary() {
    return drawAreaBoundary;
  }

  public void setDrawAreaBoundary(boolean drawAreaBoundary) {
    this.drawAreaBoundary = drawAreaBoundary;
  }

  public boolean isEvenOddRule() {
    return evenOddRule;
  }

  public void setEvenOddRule(boolean evenOddRule) {
    this.evenOddRule = evenOddRule;
  }

  public short getArcTransformP() {
    return arcTransformP;
  }

  public void setArcTransformP(short arcTransformP) {
    this.arcTransformP = arcTransformP;
  }

  public short getArcTransformQ() {
    return arcTransformQ;
  }

  public void setArcTransformQ(short arcTransformQ) {
    this.arcTransformQ = arcTransformQ;
  }

  public short getArcTransformR() {
    return arcTransformR;
  }

  public void setArcTransformR(short arcTransformR) {
    this.arcTransformR = arcTransformR;
  }

  public short getArcTransformS() {
    return arcTransformS;
  }

  public void setArcTransformS(short arcTransformS) {
    this.arcTransformS = arcTransformS;
  }

  public AFPColorSpace getProcessColorSpace() {
    return processColorSpace;
  }

  public void setProcessColorSpace(AFPColorSpace processColorSpace) {
    this.processColorSpace = processColorSpace;
  }

  public byte getNrOfBitsComponent1() {
    return nrOfBitsComponent1;
  }

  public void setNrOfBitsComponent1(byte nrOfBitsComponent1) {
    this.nrOfBitsComponent1 = nrOfBitsComponent1;
  }

  public byte getNrOfBitsComponent2() {
    return nrOfBitsComponent2;
  }

  public void setNrOfBitsComponent2(byte nrOfBitsComponent2) {
    this.nrOfBitsComponent2 = nrOfBitsComponent2;
  }

  public byte getNrOfBitsComponent3() {
    return nrOfBitsComponent3;
  }

  public void setNrOfBitsComponent3(byte nrOfBitsComponent3) {
    this.nrOfBitsComponent3 = nrOfBitsComponent3;
  }

  public byte getNrOfBitsComponent4() {
    return nrOfBitsComponent4;
  }

  public void setNrOfBitsComponent4(byte nrOfBitsComponent4) {
    this.nrOfBitsComponent4 = nrOfBitsComponent4;
  }

  public byte[] getProcessColorValue() {
    return processColorValue;
  }

  public void setProcessColorValue(byte[] processColorValue) {
    this.processColorValue = processColorValue;
  }
}
