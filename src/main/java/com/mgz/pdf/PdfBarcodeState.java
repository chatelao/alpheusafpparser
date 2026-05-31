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

import com.mgz.afp.bcoca.BDD_BarCodeDataDescriptor.BarCodeType;

/**
 * Tracks the active BCOCA barcode state for PDF conversion.
 */
public class PdfBarcodeState {

  private BarCodeType barcodeType;
  private byte barcodeModifier;
  private short fontLocalIDForHRI;
  private int color;
  private short moduleWidthInMils;
  private int elementHeight;
  private short heightMultiplier;
  private int wideToNarrowRatio;

  /**
   * Resets the barcode state to default values.
   */
  public void reset() {
    this.barcodeType = null;
    this.barcodeModifier = 0;
    this.fontLocalIDForHRI = 0;
    this.color = 0;
    this.moduleWidthInMils = 0;
    this.elementHeight = 0;
    this.heightMultiplier = 0;
    this.wideToNarrowRatio = 0;
  }

  public BarCodeType getBarcodeType() {
    return barcodeType;
  }

  public void setBarcodeType(BarCodeType barcodeType) {
    this.barcodeType = barcodeType;
  }

  public byte getBarcodeModifier() {
    return barcodeModifier;
  }

  public void setBarcodeModifier(byte barcodeModifier) {
    this.barcodeModifier = barcodeModifier;
  }

  public short getFontLocalIDForHRI() {
    return fontLocalIDForHRI;
  }

  public void setFontLocalIDForHRI(short fontLocalIDForHRI) {
    this.fontLocalIDForHRI = fontLocalIDForHRI;
  }

  public int getColor() {
    return color;
  }

  public void setColor(int color) {
    this.color = color;
  }

  public short getModuleWidthInMils() {
    return moduleWidthInMils;
  }

  public void setModuleWidthInMils(short moduleWidthInMils) {
    this.moduleWidthInMils = moduleWidthInMils;
  }

  public int getElementHeight() {
    return elementHeight;
  }

  public void setElementHeight(int elementHeight) {
    this.elementHeight = elementHeight;
  }

  public short getHeightMultiplier() {
    return heightMultiplier;
  }

  public void setHeightMultiplier(short heightMultiplier) {
    this.heightMultiplier = heightMultiplier;
  }

  public int getWideToNarrowRatio() {
    return wideToNarrowRatio;
  }

  public void setWideToNarrowRatio(int wideToNarrowRatio) {
    this.wideToNarrowRatio = wideToNarrowRatio;
  }
}
