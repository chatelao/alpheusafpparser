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
import com.mgz.afp.ptoca.controlSequence.PTOCAControlSequence.PTOCA_BypassFlag;
import java.util.HashSet;
import java.util.Set;

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
  private double inlinePos = 0;
  private double baselinePos = 0;
  private double establishedBaselinePos = 0;
  private boolean hasEstablishedBaseline = false;
  private short intercharacterAdjustment = 0;
  private short variableSpaceIncrement = 0;
  private short inlineMargin = 0;
  private short baselineIncrement = 0;
  private PTOCA_BypassFlag underscoreMode = null;
  private PTOCA_BypassFlag overstrikeMode = null;
  private String overstrikeCharacter = null;
  private final Set<Short> activeSuppressionIDs = new HashSet<>();

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
    this.underscoreMode = null;
    this.overstrikeMode = null;
    this.overstrikeCharacter = null;
    this.activeSuppressionIDs.clear();
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

  public double getInlinePos() {
    return inlinePos;
  }

  public void setInlinePos(double inlinePos) {
    this.inlinePos = inlinePos;
  }

  public double getBaselinePos() {
    return baselinePos;
  }

  public void setBaselinePos(double baselinePos) {
    this.baselinePos = baselinePos;
  }

  public double getEstablishedBaselinePos() {
    return establishedBaselinePos;
  }

  public void setEstablishedBaselinePos(double establishedBaselinePos) {
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

  public void beginSuppression(short suppressionID) {
    activeSuppressionIDs.add(suppressionID);
  }

  public void endSuppression(short suppressionID) {
    activeSuppressionIDs.remove(suppressionID);
  }

  public boolean isSuppressed() {
    return !activeSuppressionIDs.isEmpty();
  }

  public Set<Short> getActiveSuppressionIDs() {
    return java.util.Collections.unmodifiableSet(activeSuppressionIDs);
  }

  public PTOCA_BypassFlag getUnderscoreMode() {
    return underscoreMode;
  }

  public void setUnderscoreMode(PTOCA_BypassFlag underscoreMode) {
    this.underscoreMode = underscoreMode;
  }

  public PTOCA_BypassFlag getOverstrikeMode() {
    return overstrikeMode;
  }

  public void setOverstrikeMode(PTOCA_BypassFlag overstrikeMode) {
    this.overstrikeMode = overstrikeMode;
  }

  public String getOverstrikeCharacter() {
    return overstrikeCharacter;
  }

  public void setOverstrikeCharacter(String overstrikeCharacter) {
    this.overstrikeCharacter = overstrikeCharacter;
  }
}
