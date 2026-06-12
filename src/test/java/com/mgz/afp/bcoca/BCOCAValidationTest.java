/*
Copyright 2026 Alpheus AFP Parser Authors

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

/**
 * Tests for BCOCA Normative Validations (EC-xxxx).
 */
public class BCOCAValidationTest {

  @Test
  public void testEC0705_BDDExtents() {
    BDD_BarCodeDataDescriptor bdd = new BDD_BarCodeDataDescriptor();
    byte[] data = new byte[23];
    data[0] = 0x00; // Inches10
    data[2] = 0x38;
    data[3] = 0x40; // XUPUB
    data[4] = 0x38;
    data[5] = 0x40; // YUPUB
    data[6] = 0x00;
    data[7] = 0x00; // Width = 0 (Invalid)
    data[8] = 0x00;
    data[9] = 0x01; // Length = 1

    AFPParserException ex = assertThrows(AFPParserException.class,
        () -> bdd.decodeAFP(data, 0, 23, new AFPParserConfiguration()));
    assertTrue(ex.getMessage().contains("EC-0705"), "Expected EC-0705, got: " + ex.getMessage());
  }

  @Test
  public void testEC1200_Code128_Modifier02_MissingFNC1() {
    BDA_BarCodeData bda = new BDA_BarCodeData();
    byte[] data = new byte[] {0x00, 0x00, 0x00, 0x00, 0x00, (byte)'0', (byte)'1', (byte)'2'};

    AFPParserConfiguration config = new AFPParserConfiguration();
    BDD_BarCodeDataDescriptor bdd = new BDD_BarCodeDataDescriptor();
    bdd.setBarcodeType(BDD_BarCodeDataDescriptor.BarCodeType.Code_128__GS1_128__UCC_EAN_128__AIM_USS_128__IntelligentMail__ContainerBarcode);
    bdd.setBarcodeModifier((byte) 0x02);
    config.setCurrentBarCodeDataDescriptor(bdd);

    AFPParserException ex = assertThrows(AFPParserException.class,
        () -> bda.decodeAFP(data, 0, data.length, config));
    assertTrue(ex.getMessage().contains("EC-1200"), "Expected EC-1200, got: " + ex.getMessage());
  }

  @Test
  public void testEC0F01_DataMatrixSeqInd() {
    BDA_BarCodeData bda = new BDA_BarCodeData();
    byte[] data = new byte[20];
    data[10] = 17; // SeqInd = 17 (Invalid, max 16)
    data[11] = 16; // Total = 16

    AFPParserConfiguration config = new AFPParserConfiguration();
    BDD_BarCodeDataDescriptor bdd = new BDD_BarCodeDataDescriptor();
    bdd.setBarcodeType(BDD_BarCodeDataDescriptor.BarCodeType.DataMatrix_GS1DataMatrix_2D);
    config.setCurrentBarCodeDataDescriptor(bdd);

    AFPParserException ex = assertThrows(AFPParserException.class,
        () -> bda.decodeAFP(data, 0, 20, config));
    assertTrue(ex.getMessage().contains("EC-0F01"), "Expected EC-0F01, got: " + ex.getMessage());
  }

  @Test
  public void testEC0F02_DataMatrixSeqIndLargerThanTotal() {
    BDA_BarCodeData bda = new BDA_BarCodeData();
    byte[] data = new byte[20];
    data[10] = 5; // SeqInd = 5
    data[11] = 4; // Total = 4 (Invalid, SeqInd > Total)

    AFPParserConfiguration config = new AFPParserConfiguration();
    BDD_BarCodeDataDescriptor bdd = new BDD_BarCodeDataDescriptor();
    bdd.setBarcodeType(BDD_BarCodeDataDescriptor.BarCodeType.DataMatrix_GS1DataMatrix_2D);
    config.setCurrentBarCodeDataDescriptor(bdd);

    AFPParserException ex = assertThrows(AFPParserException.class,
        () -> bda.decodeAFP(data, 0, 20, config));
    assertTrue(ex.getMessage().contains("EC-0F02"), "Expected EC-0F02, got: " + ex.getMessage());
  }

  @Test
  public void testEC0F03_DataMatrixMismatchedAppend() {
    BDA_BarCodeData bda = new BDA_BarCodeData();
    byte[] data = new byte[20];
    data[10] = 0; // SeqInd = 0
    data[11] = 5; // Total = 5 (Invalid, one is zero the other not)

    AFPParserConfiguration config = new AFPParserConfiguration();
    BDD_BarCodeDataDescriptor bdd = new BDD_BarCodeDataDescriptor();
    bdd.setBarcodeType(BDD_BarCodeDataDescriptor.BarCodeType.DataMatrix_GS1DataMatrix_2D);
    config.setCurrentBarCodeDataDescriptor(bdd);

    AFPParserException ex = assertThrows(AFPParserException.class,
        () -> bda.decodeAFP(data, 0, 20, config));
    assertTrue(ex.getMessage().contains("EC-0F03"), "Expected EC-0F03, got: " + ex.getMessage());
  }

  @Test
  public void testEC0F04_DataMatrixInvalidTotalSymbols() {
    BDA_BarCodeData bda = new BDA_BarCodeData();
    byte[] data = new byte[20];
    data[10] = 1;
    data[11] = 1; // Total = 1 (Invalid, must be 2-16)

    AFPParserConfiguration config = new AFPParserConfiguration();
    BDD_BarCodeDataDescriptor bdd = new BDD_BarCodeDataDescriptor();
    bdd.setBarcodeType(BDD_BarCodeDataDescriptor.BarCodeType.DataMatrix_GS1DataMatrix_2D);
    config.setCurrentBarCodeDataDescriptor(bdd);

    AFPParserException ex = assertThrows(AFPParserException.class,
        () -> bda.decodeAFP(data, 0, 20, config));
    assertTrue(ex.getMessage().contains("EC-0F04"), "Expected EC-0F04, got: " + ex.getMessage());
  }

  @Test
  public void testEC0F0B_DataMatrixInvalidFileID() {
    BDA_BarCodeData bda = new BDA_BarCodeData();
    byte[] data = new byte[20];
    data[10] = 1; // SeqInd
    data[11] = 2; // Total
    data[12] = 0; // FileID1 = 0 (Invalid, 1-FE)
    data[13] = 1; // FileID2

    AFPParserConfiguration config = new AFPParserConfiguration();
    BDD_BarCodeDataDescriptor bdd = new BDD_BarCodeDataDescriptor();
    bdd.setBarcodeType(BDD_BarCodeDataDescriptor.BarCodeType.DataMatrix_GS1DataMatrix_2D);
    config.setCurrentBarCodeDataDescriptor(bdd);

    AFPParserException ex = assertThrows(AFPParserException.class,
        () -> bda.decodeAFP(data, 0, 20, config));
    assertTrue(ex.getMessage().contains("EC-0F0B"), "Expected EC-0F0B, got: " + ex.getMessage());
  }

  @Test
  public void testEC0F01_MaxiCodeSeqInd() {
    BDA_BarCodeData bda = new BDA_BarCodeData();
    byte[] data = new byte[20];
    data[6] = 2; // Symbol Mode 2 (Valid)
    data[7] = 9; // SeqInd = 9 (Invalid, max 8)
    data[8] = 8; // Total = 8

    AFPParserConfiguration config = new AFPParserConfiguration();
    BDD_BarCodeDataDescriptor bdd = new BDD_BarCodeDataDescriptor();
    bdd.setBarcodeType(BDD_BarCodeDataDescriptor.BarCodeType.MaxiCode_2D);
    config.setCurrentBarCodeDataDescriptor(bdd);

    AFPParserException ex = assertThrows(AFPParserException.class,
        () -> bda.decodeAFP(data, 0, 20, config));
    assertTrue(ex.getMessage().contains("EC-0F01"), "Expected EC-0F01, got: " + ex.getMessage());
  }

  @Test
  public void testEC0F01_QRCodeSeqInd() {
    BDA_BarCodeData bda = new BDA_BarCodeData();
    byte[] data = new byte[20];
    // QR: 5 (header) + 4 (common-qr part) = 9. SeqInd at offset 4 of common-qr => byte 9
    data[9] = 17; // SeqInd = 17 (Invalid, max 16)
    data[10] = 16; // Total = 16

    AFPParserConfiguration config = new AFPParserConfiguration();
    BDD_BarCodeDataDescriptor bdd = new BDD_BarCodeDataDescriptor();
    bdd.setBarcodeType(BDD_BarCodeDataDescriptor.BarCodeType.QRCode_2D);
    config.setCurrentBarCodeDataDescriptor(bdd);

    AFPParserException ex = assertThrows(AFPParserException.class,
        () -> bda.decodeAFP(data, 0, 20, config));
    assertTrue(ex.getMessage().contains("EC-0F01"), "Expected EC-0F01, got: " + ex.getMessage());
  }

  @Test
  public void testEC0F01_AztecCodeSeqInd() {
    BDA_BarCodeData bda = new BDA_BarCodeData();
    byte[] data = new byte[25];
    // Aztec parameters start at SF offset 5
    // Byte 0 (offset 5): Control flags
    // Byte 1 (offset 6): Reserved
    // Byte 2 (offset 7): Layers
    // Byte 3 (offset 8): EC level
    // Byte 4 (offset 9): Special flags
    // Byte 5 (offset 10): App indicator
    // Byte 6 (offset 11): SeqInd
    // Byte 7 (offset 12): Total

    data[7] = 0; // Layers
    data[8] = (byte) 0xFF; // EC level default
    data[11] = 27; // SeqInd = 27 (Invalid, max 26)
    data[12] = 26; // Total = 26

    AFPParserConfiguration config = new AFPParserConfiguration();
    BDD_BarCodeDataDescriptor bdd = new BDD_BarCodeDataDescriptor();
    bdd.setBarcodeType(BDD_BarCodeDataDescriptor.BarCodeType.AztecCode);
    config.setCurrentBarCodeDataDescriptor(bdd);

    AFPParserException ex = assertThrows(AFPParserException.class,
        () -> bda.decodeAFP(data, 0, 25, config));
    assertTrue(ex.getMessage().contains("EC-0F01"), "Expected EC-0F01, got: " + ex.getMessage());
  }

  @Test
  public void testEC2100_UPCA_InvalidData() {
    BDA_BarCodeData bda = new BDA_BarCodeData();
    byte[] data = new byte[] {0x00, 0x00, 0x00, 0x00, 0x00, (byte)'A', (byte)'1', (byte)'2'}; // Header + "A12"

    AFPParserConfiguration config = new AFPParserConfiguration();
    BDD_BarCodeDataDescriptor bdd = new BDD_BarCodeDataDescriptor();
    bdd.setBarcodeType(BDD_BarCodeDataDescriptor.BarCodeType.UPC_CGPC_VersionA);
    config.setCurrentBarCodeDataDescriptor(bdd);

    AFPParserException ex = assertThrows(AFPParserException.class,
        () -> bda.decodeAFP(data, 0, data.length, config));
    assertTrue(ex.getMessage().contains("EC-2100"), "Expected EC-2100, got: " + ex.getMessage());
  }

  @Test
  public void testEC2100_Interleaved2of5_InvalidData() {
    BDA_BarCodeData bda = new BDA_BarCodeData();
    byte[] data = new byte[] {0x00, 0x00, 0x00, 0x00, 0x00, (byte)'1', (byte)'X', (byte)'2'}; // "1X2"

    AFPParserConfiguration config = new AFPParserConfiguration();
    BDD_BarCodeDataDescriptor bdd = new BDD_BarCodeDataDescriptor();
    bdd.setBarcodeType(BDD_BarCodeDataDescriptor.BarCodeType.Interleaved_2of5__ITF14__AIM_USS_I_2of5);
    config.setCurrentBarCodeDataDescriptor(bdd);

    AFPParserException ex = assertThrows(AFPParserException.class,
        () -> bda.decodeAFP(data, 0, data.length, config));
    assertTrue(ex.getMessage().contains("EC-2100"), "Expected EC-2100, got: " + ex.getMessage());
  }

  @Test
  public void testEC2100_Code39_InvalidData() {
    BDA_BarCodeData bda = new BDA_BarCodeData();
    byte[] data = new byte[] {0x00, 0x00, 0x00, 0x00, 0x00, (byte)'A', (byte)'B', (byte)'c'}; // "ABc" (lowercase 'c' invalid)

    AFPParserConfiguration config = new AFPParserConfiguration();
    BDD_BarCodeDataDescriptor bdd = new BDD_BarCodeDataDescriptor();
    bdd.setBarcodeType(BDD_BarCodeDataDescriptor.BarCodeType.Code39_3of9Code_AIM_USS_39);
    config.setCurrentBarCodeDataDescriptor(bdd);

    AFPParserException ex = assertThrows(AFPParserException.class,
        () -> bda.decodeAFP(data, 0, data.length, config));
    assertTrue(ex.getMessage().contains("EC-2100"), "Expected EC-2100, got: " + ex.getMessage());
  }

  @Test
  public void testEC2100_EAN13_NonNumeric() {
    BDA_BarCodeData bda = new BDA_BarCodeData();
    byte[] data = new byte[] {0x00, 0x00, 0x00, 0x00, 0x00, (byte)'1', (byte)'2', (byte)'#'}; // "12#"

    AFPParserConfiguration config = new AFPParserConfiguration();
    BDD_BarCodeDataDescriptor bdd = new BDD_BarCodeDataDescriptor();
    bdd.setBarcodeType(BDD_BarCodeDataDescriptor.BarCodeType.EAN_13_includingJANStandard);
    config.setCurrentBarCodeDataDescriptor(bdd);

    AFPParserException ex = assertThrows(AFPParserException.class,
        () -> bda.decodeAFP(data, 0, data.length, config));
    assertTrue(ex.getMessage().contains("EC-2100"), "Expected EC-2100, got: " + ex.getMessage());
  }

  @Test
  public void testEC1200_Code128_MissingFNC1() {
    BDA_BarCodeData bda = new BDA_BarCodeData();
    // Header (5 bytes) + Data (not starting with 0x8F)
    byte[] data = new byte[] {0x00, 0x00, 0x00, 0x00, 0x00, (byte)'0', (byte)'1', (byte)'2'};

    AFPParserConfiguration config = new AFPParserConfiguration();
    BDD_BarCodeDataDescriptor bdd = new BDD_BarCodeDataDescriptor();
    bdd.setBarcodeType(BDD_BarCodeDataDescriptor.BarCodeType.Code_128__GS1_128__UCC_EAN_128__AIM_USS_128__IntelligentMail__ContainerBarcode);
    bdd.setBarcodeModifier((byte) 0x03);
    config.setCurrentBarCodeDataDescriptor(bdd);

    AFPParserException ex = assertThrows(AFPParserException.class,
        () -> bda.decodeAFP(data, 0, data.length, config));
    assertTrue(ex.getMessage().contains("EC-1200"), "Expected EC-1200, got: " + ex.getMessage());
  }

  @Test
  public void testEC1200_Code128_ValidFNC1() throws Exception {
    BDA_BarCodeData bda = new BDA_BarCodeData();
    // Header (5 bytes) + Data starting with 0x8F (FNC1)
    byte[] data = new byte[] {0x00, 0x00, 0x00, 0x00, 0x00, (byte) 0x8F, (byte)'0', (byte)'1'};

    AFPParserConfiguration config = new AFPParserConfiguration();
    BDD_BarCodeDataDescriptor bdd = new BDD_BarCodeDataDescriptor();
    bdd.setBarcodeType(BDD_BarCodeDataDescriptor.BarCodeType.Code_128__GS1_128__UCC_EAN_128__AIM_USS_128__IntelligentMail__ContainerBarcode);
    bdd.setBarcodeModifier((byte) 0x04);
    config.setCurrentBarCodeDataDescriptor(bdd);

    bda.decodeAFP(data, 0, data.length, config);
    // Should not throw exception
  }
}
