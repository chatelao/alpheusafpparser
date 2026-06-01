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
import com.mgz.afp.enums.AFPOrientation;

/**
 * Tracks the active PTOCA text state for PDF conversion.
 * Stores attributes like font LID, color, orientation, and position.
 */
public class PdfTextState {

  private short fontLid = -1;
  private AFPColorValue textColor = AFPColorValue.DeviceDefault_0x00;
  private boolean extendedColor = false;
  private AFPColorSpace extendedColorSpace = null;
  private byte[] extendedColorValue = null;
  private AFPOrientation iOrientation = AFPOrientation.ori0;
  private AFPOrientation bOrientation = AFPOrientation.ori90;
  private int inlinePos = 0;
  private int baselinePos = 0;
  private int establishedBaselinePos = 0;
  private boolean hasEstablishedBaseline = false;
  private short intercharacterAdjustment = 0;
  private short variableSpaceIncrement = 0;
  private short inlineMargin = 0;
  private short baselineIncrement = 0;

  /**
   * Resets the text state to default values.
   */
  public void reset() {
    this.fontLid = -1;
    this.textColor = AFPColorValue.DeviceDefault_0x00;
    this.extendedColor = false;
    this.extendedColorSpace = null;
    this.extendedColorValue = null;
    this.iOrientation = AFPOrientation.ori0;
    this.bOrientation = AFPOrientation.ori90;
    this.inlinePos = 0;
    this.baselinePos = 0;
    this.establishedBaselinePos = 0;
    this.hasEstablishedBaseline = false;
    this.intercharacterAdjustment = 0;
    this.variableSpaceIncrement = 0;
    this.inlineMargin = 0;
    this.baselineIncrement = 0;
  }

  public short getFontLid() {
    return fontLid;
  }

  public void setFontLid(short fontLid) {
    this.fontLid = fontLid;
  }

  public AFPColorValue getTextColor() {
    return textColor;
  }

  public void setTextColor(AFPColorValue textColor) {
    this.textColor = textColor;
    this.extendedColor = false;
  }

  public boolean isExtendedColor() {
    return extendedColor;
  }

  public void setExtendedColor(boolean extendedColor) {
    this.extendedColor = extendedColor;
  }

  public AFPColorSpace getExtendedColorSpace() {
    return extendedColorSpace;
  }

  public void setExtendedColorSpace(AFPColorSpace extendedColorSpace) {
    this.extendedColorSpace = extendedColorSpace;
    this.extendedColor = true;
  }

  public byte[] getExtendedColorValue() {
    return extendedColorValue;
  }

  public void setExtendedColorValue(byte[] extendedColorValue) {
    this.extendedColorValue = extendedColorValue;
    this.extendedColor = true;
  }

  public AFPOrientation getIOrientation() {
    return iOrientation;
  }

  public void setIOrientation(AFPOrientation iOrientation) {
    this.iOrientation = iOrientation;
  }

  public AFPOrientation getBOrientation() {
    return bOrientation;
  }

  public void setBOrientation(AFPOrientation bOrientation) {
    this.bOrientation = bOrientation;
  }

  public int getInlinePos() {
    return inlinePos;
  }

  public void setInlinePos(int inlinePos) {
    this.inlinePos = inlinePos;
  }

  public int getBaselinePos() {
    return baselinePos;
  }

  public void setBaselinePos(int baselinePos) {
    this.baselinePos = baselinePos;
  }

  public int getEstablishedBaselinePos() {
    return establishedBaselinePos;
  }

  public void setEstablishedBaselinePos(int establishedBaselinePos) {
    this.establishedBaselinePos = establishedBaselinePos;
  }

  public boolean isHasEstablishedBaseline() {
    return hasEstablishedBaseline;
  }

  public void setHasEstablishedBaseline(boolean hasEstablishedBaseline) {
    this.hasEstablishedBaseline = hasEstablishedBaseline;
  }

  public short getIntercharacterAdjustment() {
    return intercharacterAdjustment;
  }

  public void setIntercharacterAdjustment(short intercharacterAdjustment) {
    this.intercharacterAdjustment = intercharacterAdjustment;
  }

  public short getVariableSpaceIncrement() {
    return variableSpaceIncrement;
  }

  public void setVariableSpaceIncrement(short variableSpaceIncrement) {
    this.variableSpaceIncrement = variableSpaceIncrement;
  }

  public short getInlineMargin() {
    return inlineMargin;
  }

  public void setInlineMargin(short inlineMargin) {
    this.inlineMargin = inlineMargin;
  }

  public short getBaselineIncrement() {
    return baselineIncrement;
  }

  public void setBaselineIncrement(short baselineIncrement) {
    this.baselineIncrement = baselineIncrement;
  }
}
