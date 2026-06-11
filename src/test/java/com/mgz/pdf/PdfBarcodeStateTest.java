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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.mgz.afp.bcoca.BDA_BarCodeData;
import com.mgz.afp.bcoca.BDD_BarCodeDataDescriptor.BarCodeType;
import com.mgz.afp.enums.AFPUnitBase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Unit test for {@link PdfBarcodeState}.
 */
public class PdfBarcodeStateTest {

  private PdfBarcodeState barcodeState;

  @BeforeEach
  public void setUp() {
    barcodeState = new PdfBarcodeState();
  }

  @Test
  public void testDefaultValues() {
    assertNull(barcodeState.getBarcodeType());
    assertEquals(0, barcodeState.getBarcodeModifier());
    assertEquals(0, barcodeState.getFontLocalIDForHRI());
    assertEquals(0, barcodeState.getColor());
    assertEquals(0, barcodeState.getModuleWidthInMils());
    assertEquals(0, barcodeState.getElementHeight());
    assertEquals(0, barcodeState.getHeightMultiplier());
    assertEquals(0, barcodeState.getWideToNarrowRatio());
    assertNull(barcodeState.getUnitBase());
    assertEquals(0, barcodeState.getUnitsPerUnitBaseX());
    assertEquals(0, barcodeState.getUnitsPerUnitBaseY());
    assertNull(barcodeState.getHriFont());
    assertTrue(barcodeState.getBarcodeData().isEmpty());
    assertFalse(barcodeState.isInBarcodeObject());
    assertEquals(0, barcodeState.getxOrigin());
    assertEquals(0, barcodeState.getyOrigin());
  }

  @Test
  public void testSettersAndGetters() {
    barcodeState.setBarcodeType(BarCodeType.Code39_3of9Code_AIM_USS_39);
    barcodeState.setBarcodeModifier((byte) 1);
    barcodeState.setFontLocalIDForHRI((short) 5);
    barcodeState.setColor(0xFF0000);
    barcodeState.setModuleWidthInMils((short) 10);
    barcodeState.setElementHeight(100);
    barcodeState.setHeightMultiplier((short) 2);
    barcodeState.setWideToNarrowRatio(3);
    barcodeState.setUnitBase(AFPUnitBase.Inches10);
    barcodeState.setUnitsPerUnitBaseX(1440);
    barcodeState.setUnitsPerUnitBaseY(1440);
    barcodeState.setInBarcodeObject(true);
    barcodeState.setxOrigin(50);
    barcodeState.setyOrigin(60);

    BDA_BarCodeData data = new BDA_BarCodeData();
    barcodeState.addBarcodeData(data);

    assertEquals(BarCodeType.Code39_3of9Code_AIM_USS_39, barcodeState.getBarcodeType());
    assertEquals(1, barcodeState.getBarcodeModifier());
    assertEquals(5, barcodeState.getFontLocalIDForHRI());
    assertEquals(0xFF0000, barcodeState.getColor());
    assertEquals(10, barcodeState.getModuleWidthInMils());
    assertEquals(100, barcodeState.getElementHeight());
    assertEquals(2, barcodeState.getHeightMultiplier());
    assertEquals(3, barcodeState.getWideToNarrowRatio());
    assertEquals(AFPUnitBase.Inches10, barcodeState.getUnitBase());
    assertEquals(1440, barcodeState.getUnitsPerUnitBaseX());
    assertEquals(1440, barcodeState.getUnitsPerUnitBaseY());
    assertTrue(barcodeState.isInBarcodeObject());
    assertEquals(50, barcodeState.getxOrigin());
    assertEquals(60, barcodeState.getyOrigin());
    assertEquals(1, barcodeState.getBarcodeData().size());
    assertEquals(data, barcodeState.getBarcodeData().get(0));
  }

  @Test
  public void testReset() {
    barcodeState.setBarcodeType(BarCodeType.Code39_3of9Code_AIM_USS_39);
    barcodeState.setInBarcodeObject(true);
    barcodeState.addBarcodeData(new BDA_BarCodeData());

    barcodeState.reset();

    assertNull(barcodeState.getBarcodeType());
    assertFalse(barcodeState.isInBarcodeObject());
    assertTrue(barcodeState.getBarcodeData().isEmpty());
  }

  @Test
  public void testStartNewBarcode() {
    barcodeState.setInBarcodeObject(false);
    barcodeState.addBarcodeData(new BDA_BarCodeData());
    barcodeState.setxOrigin(100);
    barcodeState.setyOrigin(100);

    barcodeState.startNewBarcode();

    assertTrue(barcodeState.isInBarcodeObject());
    assertTrue(barcodeState.getBarcodeData().isEmpty());
    assertEquals(0, barcodeState.getxOrigin());
    assertEquals(0, barcodeState.getyOrigin());
  }
}
