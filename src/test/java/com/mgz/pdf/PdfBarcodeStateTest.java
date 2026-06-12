/*
Copyright 2024 Rudolf Fiala

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
import org.junit.jupiter.api.Test;

/**
 * Unit tests for PdfBarcodeState.
 */
public class PdfBarcodeStateTest {

  @Test
  public void testDefaultValues() {
    PdfBarcodeState state = new PdfBarcodeState();
    assertNull(state.getBarcodeType());
    assertEquals(0, state.getBarcodeModifier());
    assertEquals(0, state.getFontLocalIDForHRI());
    assertNull(state.getHriFont());
    assertEquals(0, state.getColor());
    assertEquals(0, state.getModuleWidthInMils());
    assertEquals(0, state.getElementHeight());
    assertEquals(0, state.getHeightMultiplier());
    assertEquals(0, state.getWideToNarrowRatio());
    assertNull(state.getUnitBase());
    assertEquals(0, state.getUnitsPerUnitBaseX());
    assertEquals(0, state.getUnitsPerUnitBaseY());
    assertTrue(state.getBarcodeData().isEmpty());
    assertFalse(state.isInBarcodeObject());
    assertEquals(0, state.getxOrigin());
    assertEquals(0, state.getyOrigin());
  }

  @Test
  public void testSettersAndGetters() {
    PdfBarcodeState state = new PdfBarcodeState();
    state.setBarcodeType(BarCodeType.Code39_3of9Code_AIM_USS_39);
    assertEquals(BarCodeType.Code39_3of9Code_AIM_USS_39, state.getBarcodeType());

    state.setBarcodeModifier((byte) 0x01);
    assertEquals((byte) 0x01, state.getBarcodeModifier());

    state.setFontLocalIDForHRI((short) 1);
    assertEquals((short) 1, state.getFontLocalIDForHRI());

    state.setColor(0xFF0000);
    assertEquals(0xFF0000, state.getColor());

    state.setModuleWidthInMils((short) 10);
    assertEquals((short) 10, state.getModuleWidthInMils());

    state.setElementHeight(100);
    assertEquals(100, state.getElementHeight());

    state.setHeightMultiplier((short) 2);
    assertEquals((short) 2, state.getHeightMultiplier());

    state.setWideToNarrowRatio(3);
    assertEquals(3, state.getWideToNarrowRatio());

    state.setUnitBase(AFPUnitBase.Inches10);
    assertEquals(AFPUnitBase.Inches10, state.getUnitBase());

    state.setUnitsPerUnitBaseX(1440);
    assertEquals(1440, state.getUnitsPerUnitBaseX());

    state.setUnitsPerUnitBaseY(1440);
    assertEquals(1440, state.getUnitsPerUnitBaseY());

    state.setInBarcodeObject(true);
    assertTrue(state.isInBarcodeObject());

    state.setxOrigin(50);
    assertEquals(50, state.getxOrigin());

    state.setyOrigin(60);
    assertEquals(60, state.getyOrigin());

    BDA_BarCodeData data = new BDA_BarCodeData();
    state.addBarcodeData(data);
    assertEquals(1, state.getBarcodeData().size());
    assertEquals(data, state.getBarcodeData().get(0));
  }

  @Test
  public void testReset() {
    PdfBarcodeState state = new PdfBarcodeState();
    state.setBarcodeType(BarCodeType.Code39_3of9Code_AIM_USS_39);
    state.setBarcodeModifier((byte) 0x01);
    state.setFontLocalIDForHRI((short) 1);
    state.setColor(0xFF0000);
    state.setModuleWidthInMils((short) 10);
    state.setElementHeight(100);
    state.setHeightMultiplier((short) 2);
    state.setWideToNarrowRatio(3);
    state.setUnitBase(AFPUnitBase.Inches10);
    state.setUnitsPerUnitBaseX(1440);
    state.setUnitsPerUnitBaseY(1440);
    state.addBarcodeData(new BDA_BarCodeData());
    state.setInBarcodeObject(true);
    state.setxOrigin(50);
    state.setyOrigin(60);

    state.reset();

    assertNull(state.getBarcodeType());
    assertEquals(0, state.getBarcodeModifier());
    assertEquals(0, state.getFontLocalIDForHRI());
    assertEquals(0, state.getColor());
    assertEquals(0, state.getModuleWidthInMils());
    assertEquals(0, state.getElementHeight());
    assertEquals(0, state.getHeightMultiplier());
    assertEquals(0, state.getWideToNarrowRatio());
    assertNull(state.getUnitBase());
    assertEquals(0, state.getUnitsPerUnitBaseX());
    assertEquals(0, state.getUnitsPerUnitBaseY());
    assertTrue(state.getBarcodeData().isEmpty());
    assertFalse(state.isInBarcodeObject());
    assertEquals(0, state.getxOrigin());
    assertEquals(0, state.getyOrigin());
  }

  @Test
  public void testStartNewBarcode() {
    PdfBarcodeState state = new PdfBarcodeState();
    state.addBarcodeData(new BDA_BarCodeData());
    state.setInBarcodeObject(false);
    state.setxOrigin(50);
    state.setyOrigin(60);

    state.startNewBarcode();

    assertTrue(state.getBarcodeData().isEmpty());
    assertTrue(state.isInBarcodeObject());
    assertEquals(0, state.getxOrigin());
    assertEquals(0, state.getyOrigin());
  }
}
