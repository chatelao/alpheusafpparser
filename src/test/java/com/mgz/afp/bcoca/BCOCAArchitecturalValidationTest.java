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

package com.mgz.afp.bcoca;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.mgz.afp.exceptions.AFPParserException;
import com.mgz.afp.parser.AFPParserConfiguration;
import org.junit.jupiter.api.Test;

public class BCOCAArchitecturalValidationTest {

  @Test
  public void testEC0610InvalidDesiredSymbolWidth() {
    BDD_BarCodeDataDescriptor bdd = new BDD_BarCodeDataDescriptor();
    byte[] data = createValidBDD();
    data[10] = (byte) 0x80; // 0x8000 is invalid (> 0x7FFF)
    data[11] = 0x00;

    AFPParserException ex = assertThrows(AFPParserException.class, () -> bdd.decodeAFP(data, 0, data.length, new AFPParserConfiguration()));
    assertTrue(ex.getMessage().contains("EC-0610"));
  }

  @Test
  public void testEC0600InvalidModuleWidth() {
    BDD_BarCodeDataDescriptor bdd = new BDD_BarCodeDataDescriptor();
    byte[] data = createValidBDD();
    data[17] = 0x00; // 0 is invalid

    AFPParserException ex = assertThrows(AFPParserException.class, () -> bdd.decodeAFP(data, 0, data.length, new AFPParserConfiguration()));
    assertTrue(ex.getMessage().contains("EC-0600"));
  }

  @Test
  public void testEC0700InvalidElementHeight() {
    BDD_BarCodeDataDescriptor bdd = new BDD_BarCodeDataDescriptor();
    byte[] data = createValidBDD();
    data[18] = (byte) 0x80; // 0x8000 is invalid (> 0x7FFF and != 0xFFFF)
    data[19] = 0x00;

    AFPParserException ex = assertThrows(AFPParserException.class, () -> bdd.decodeAFP(data, 0, data.length, new AFPParserConfiguration()));
    assertTrue(ex.getMessage().contains("EC-0700"));
  }

  @Test
  public void testEC0800InvalidHeightMultiplier() {
    BDD_BarCodeDataDescriptor bdd = new BDD_BarCodeDataDescriptor();
    byte[] data = createValidBDD();
    data[20] = 0x00; // 0 is invalid

    AFPParserException ex = assertThrows(AFPParserException.class, () -> bdd.decodeAFP(data, 0, data.length, new AFPParserConfiguration()));
    assertTrue(ex.getMessage().contains("EC-0800"));
  }

  @Test
  public void testEC0900InvalidWideToNarrowRatio() {
    BDD_BarCodeDataDescriptor bdd = new BDD_BarCodeDataDescriptor();
    byte[] data = createValidBDD();
    data[21] = (byte) 0x80; // 0x8000 is invalid (> 0x7FFF and != 0xFFFF)
    data[22] = 0x00;

    AFPParserException ex = assertThrows(AFPParserException.class, () -> bdd.decodeAFP(data, 0, data.length, new AFPParserConfiguration()));
    assertTrue(ex.getMessage().contains("EC-0900"));
  }

  private byte[] createValidBDD() {
    byte[] data = new byte[23];
    data[0] = 0x00; // Unit base: 10 inches
    data[1] = 0x00; // Reserved
    data[2] = 0x38; // Xupub: 14400 (0x3840)
    data[3] = 0x40;
    data[4] = 0x38; // Yupub: 14400
    data[5] = 0x40;
    data[6] = 0x00; // X extent: 100
    data[7] = 0x64;
    data[8] = 0x00; // Y extent: 100
    data[9] = 0x64;
    data[10] = 0x00; // Desired symbol width: 0
    data[11] = 0x00;
    data[12] = 0x01; // Type: Code 39
    data[13] = 0x01; // Modifier: 1
    data[14] = (byte) 0xFF; // Local ID: Default
    data[15] = (byte) 0xFF; // Color: Default
    data[16] = 0x07;
    data[17] = 0x10; // Module width: 16
    data[18] = 0x00; // Element height: 100
    data[19] = 0x64;
    data[20] = 0x01; // Multiplier: 1
    data[21] = 0x00; // WE:NE: 0
    data[22] = 0x00;
    return data;
  }
}
