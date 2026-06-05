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
  private float lineWidth = 0.0f;
  private short lineType = 0;
  private LineEnd lineEnd = LineEnd.Default;
  private LineJoin lineJoin = LineJoin.Default;
  private short patternSet = 0;
  /** GSPT X'10' = Solid fill (Standard default for GOCA Pattern Set 0) */
  private short patternSymbol = 16;
  private short markerSet = 0;
  private short markerSymbol = 0;
  private short markerPrecision = 0;
  private short characterSet = 0;
  private float charCellWidth = 0.0f;
  private float charCellHeight = 0.0f;
  private short charAngleX = 1;
  private short charAngleY = 0;
  private short charShearDividend = 0;
  private short charShearDivisor = 1;
  private short charDirection = 1;
  private short charPrecision = 0;
  private short mixMode = 0;
  private short backgroundMixMode = 0;
  private int currentX = 0;
  private int currentY = 0;
  private boolean inArea = false;
  private boolean drawAreaBoundary = false;
  private boolean evenOddRule = true;

  private short arcTransformP = 1;
  private short arcTransformQ = 0;
  private short arcTransformR = 0;
  private short arcTransformS = 1;

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
    this.lineWidth = 0.0f;
    this.lineType = 0;
    this.lineEnd = LineEnd.Default;
    this.lineJoin = LineJoin.Default;
    this.patternSet = 0;
    this.patternSymbol = 16;
    this.markerSet = 0;
    this.markerSymbol = 0;
    this.markerPrecision = 0;
    this.characterSet = 0;
    this.charCellWidth = 0.0f;
    this.charCellHeight = 0.0f;
    this.charAngleX = 1;
    this.charAngleY = 0;
    this.charShearDividend = 0;
    this.charShearDivisor = 1;
    this.charDirection = 1;
    this.charPrecision = 0;
    this.mixMode = 0;
    this.backgroundMixMode = 0;
    this.currentX = 0;
    this.currentY = 0;
    this.inArea = false;
    this.drawAreaBoundary = false;
    this.evenOddRule = true;
    this.arcTransformP = 1;
    this.arcTransformQ = 0;
    this.arcTransformR = 0;
    this.arcTransformS = 1;
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

  public float getLineWidth() {
    return lineWidth;
  }

  public void setLineWidth(float lineWidth) {
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

  public short getPatternSet() {
    return patternSet;
  }

  public void setPatternSet(short patternSet) {
    this.patternSet = patternSet;
  }

  public short getPatternSymbol() {
    return patternSymbol;
  }

  public void setPatternSymbol(short patternSymbol) {
    this.patternSymbol = patternSymbol;
  }

  public short getMarkerSet() {
    return markerSet;
  }

  public void setMarkerSet(short markerSet) {
    this.markerSet = markerSet;
  }

  public short getMarkerSymbol() {
    return markerSymbol;
  }

  public void setMarkerSymbol(short markerSymbol) {
    this.markerSymbol = markerSymbol;
  }

  public short getMarkerPrecision() {
    return markerPrecision;
  }

  public void setMarkerPrecision(short markerPrecision) {
    this.markerPrecision = markerPrecision;
  }

  public short getCharacterSet() {
    return characterSet;
  }

  public void setCharacterSet(short characterSet) {
    this.characterSet = characterSet;
  }

  public float getCharCellWidth() {
    return charCellWidth;
  }

  public void setCharCellWidth(float charCellWidth) {
    this.charCellWidth = charCellWidth;
  }

  public float getCharCellHeight() {
    return charCellHeight;
  }

  public void setCharCellHeight(float charCellHeight) {
    this.charCellHeight = charCellHeight;
  }

  public short getCharAngleX() {
    return charAngleX;
  }

  public void setCharAngleX(short charAngleX) {
    this.charAngleX = charAngleX;
  }

  public short getCharAngleY() {
    return charAngleY;
  }

  public void setCharAngleY(short charAngleY) {
    this.charAngleY = charAngleY;
  }

  public short getCharShearDividend() {
    return charShearDividend;
  }

  public void setCharShearDividend(short charShearDividend) {
    this.charShearDividend = charShearDividend;
  }

  public short getCharShearDivisor() {
    return charShearDivisor;
  }

  public void setCharShearDivisor(short charShearDivisor) {
    this.charShearDivisor = charShearDivisor;
  }

  public short getCharDirection() {
    return charDirection;
  }

  public void setCharDirection(short charDirection) {
    this.charDirection = charDirection;
  }

  public short getCharPrecision() {
    return charPrecision;
  }

  public void setCharPrecision(short charPrecision) {
    this.charPrecision = charPrecision;
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
