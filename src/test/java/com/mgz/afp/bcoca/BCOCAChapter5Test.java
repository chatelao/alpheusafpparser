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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.mgz.afp.exceptions.AFPParserException;
import com.mgz.afp.parser.AFPParserConfiguration;
import org.junit.jupiter.api.Test;

/**
 * Tests for BCOCA Chapter 5: Exception Conditions.
 * Verifies that the parser correctly detects and reports exception conditions.
 */
public class BCOCAChapter5Test {

    @Test
    public void testBDDInvalidUnitBase() {
        // [BCOCA-4-008] EC-0505: The unit base specified in the BSD data structure is invalid or unsupported.
        BDD_BarCodeDataDescriptor bdd = new BDD_BarCodeDataDescriptor();
        byte[] data = new byte[23];
        data[0] = 0x03; // Invalid unit base (only 0x00 and 0x01 are valid)
        assertThrows(AFPParserException.class, () -> {
            bdd.decodeAFP(data, 0, 23, new AFPParserConfiguration());
        }, "Should throw EC-0505 for invalid unit base");
    }

    @Test
    public void testBDDInvalidLength() {
        // [BCOCA-5-002] Specification-Check Exceptions: Invalid data parameters or values.
        // Verifies that BDD_BarCodeDataDescriptor throws an exception if the payload is too short (less than 23 bytes).
        BDD_BarCodeDataDescriptor bdd = new BDD_BarCodeDataDescriptor();
        byte[] shortData = new byte[22]; // BDD requires 23 bytes of payload
        assertThrows(AFPParserException.class, () -> {
            bdd.decodeAFP(shortData, 0, 22, new AFPParserConfiguration());
        });
    }

    @Test
    public void testBDAInvalidLength() {
        // [BCOCA-5-002] Specification-Check Exceptions: Invalid data parameters or values.
        // Verifies that BDA_BarCodeData throws an exception if the payload is too short (less than 5 bytes).
        BDA_BarCodeData bda = new BDA_BarCodeData();
        byte[] shortData = new byte[4]; // BDA requires at least 5 bytes of payload
        assertThrows(AFPParserException.class, () -> {
            bda.decodeAFP(shortData, 0, 4, new AFPParserConfiguration());
        });
    }

    @Test
    public void testBDAParametersDataMatrixInvalidLength() {
        // [BCOCA-5-002] Specification-Check Exceptions: Invalid data parameters or values.
        // Verifies that Data Matrix parameters in BDA throw an exception if the payload is too short.
        BDA_BarCodeData bda = new BDA_BarCodeData();
        byte[] shortData = new byte[10]; // 5 bytes BDA header + 5 bytes (should be 10 for Data Matrix)
        System.arraycopy(new byte[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0}, 0, shortData, 0, 10);

        AFPParserConfiguration config = new AFPParserConfiguration();
        BDD_BarCodeDataDescriptor bdd = new BDD_BarCodeDataDescriptor();
        bdd.setBarcodeType(BDD_BarCodeDataDescriptor.BarCodeType.DataMatrix_GS1DataMatrix_2D);
        config.setCurrentBarCodeDataDescriptor(bdd);

        assertThrows(AFPParserException.class, () -> {
            bda.decodeAFP(shortData, 0, 10, config);
        });
    }

    @Test
    public void testBDAParametersMaxiCodeInvalidLength() {
        // [BCOCA-5-002] Specification-Check Exceptions: Invalid data parameters or values.
        BDA_BarCodeData bda = new BDA_BarCodeData();
        byte[] shortData = new byte[9]; // 5 bytes BDA header + 4 bytes (should be 5 for MaxiCode)

        AFPParserConfiguration config = new AFPParserConfiguration();
        BDD_BarCodeDataDescriptor bdd = new BDD_BarCodeDataDescriptor();
        bdd.setBarcodeType(BDD_BarCodeDataDescriptor.BarCodeType.MaxiCode_2D);
        config.setCurrentBarCodeDataDescriptor(bdd);

        assertThrows(AFPParserException.class, () -> {
            bda.decodeAFP(shortData, 0, 9, config);
        });
    }

    @Test
    public void testBDAParametersAztecCodeInvalidLength() {
        // [BCOCA-5-002] Specification-Check Exceptions: Invalid data parameters or values.
        BDA_BarCodeData bda = new BDA_BarCodeData();
        byte[] shortData = new byte[13]; // 5 bytes BDA header + 8 bytes (should be at least 9 for Aztec)

        AFPParserConfiguration config = new AFPParserConfiguration();
        BDD_BarCodeDataDescriptor bdd = new BDD_BarCodeDataDescriptor();
        bdd.setBarcodeType(BDD_BarCodeDataDescriptor.BarCodeType.AztecCode);
        config.setCurrentBarCodeDataDescriptor(bdd);

        assertThrows(AFPParserException.class, () -> {
            bda.decodeAFP(shortData, 0, 13, config);
        });
    }

    @Test
    public void testBDAParametersHanXinCodeInvalidLength() {
        // [BCOCA-5-002] Specification-Check Exceptions: Invalid data parameters or values.
        BDA_BarCodeData bda = new BDA_BarCodeData();
        byte[] shortData = new byte[11]; // 5 bytes BDA header + 6 bytes (should be at least 7 for Han Xin)

        AFPParserConfiguration config = new AFPParserConfiguration();
        BDD_BarCodeDataDescriptor bdd = new BDD_BarCodeDataDescriptor();
        bdd.setBarcodeType(BDD_BarCodeDataDescriptor.BarCodeType.HanXinCode);
        config.setCurrentBarCodeDataDescriptor(bdd);

        assertThrows(AFPParserException.class, () -> {
            bda.decodeAFP(shortData, 0, 11, config);
        });
    }

    @Test
    public void testBDAParametersIntelligentMailPackageBarcodeInvalidLength() {
        // [BCOCA-5-002] Specification-Check Exceptions: Invalid data parameters or values.
        BDA_BarCodeData bda = new BDA_BarCodeData();
        byte[] shortData = new byte[8]; // 5 bytes BDA header + 3 bytes (should be at least 4 for IM Package)

        AFPParserConfiguration config = new AFPParserConfiguration();
        BDD_BarCodeDataDescriptor bdd = new BDD_BarCodeDataDescriptor();
        bdd.setBarcodeType(BDD_BarCodeDataDescriptor.BarCodeType.Code_128__GS1_128__UCC_EAN_128__AIM_USS_128__IntelligentMail__ContainerBarcode);
        bdd.setBarcodeModifier((byte) 0x06);
        config.setCurrentBarCodeDataDescriptor(bdd);

        assertThrows(AFPParserException.class, () -> {
            bda.decodeAFP(shortData, 0, 8, config);
        });
    }

    @Test
    public void testBDAParametersQRCodeInvalidLength() {
        // [BCOCA-5-002] Specification-Check Exceptions: Invalid data parameters or values.
        BDA_BarCodeData bda = new BDA_BarCodeData();
        byte[] shortData = new byte[13]; // 5 bytes BDA header + 8 bytes (should be at least 9 for QR)

        AFPParserConfiguration config = new AFPParserConfiguration();
        BDD_BarCodeDataDescriptor bdd = new BDD_BarCodeDataDescriptor();
        bdd.setBarcodeType(BDD_BarCodeDataDescriptor.BarCodeType.QRCode_2D);
        config.setCurrentBarCodeDataDescriptor(bdd);

        assertThrows(AFPParserException.class, () -> {
            bda.decodeAFP(shortData, 0, 13, config);
        });
    }

    @Test
    public void testBDAParametersQRCodeWithImageInvalidLength() {
        // [BCOCA-5-002] Specification-Check Exceptions: Invalid data parameters or values.
        BDA_BarCodeData bda = new BDA_BarCodeData();
        byte[] shortData = new byte[16]; // 5 bytes BDA header + 9 bytes (common QR) + 2 bytes (should be 3 more for Image part)

        AFPParserConfiguration config = new AFPParserConfiguration();
        BDD_BarCodeDataDescriptor bdd = new BDD_BarCodeDataDescriptor();
        bdd.setBarcodeType(BDD_BarCodeDataDescriptor.BarCodeType.QRCode_2D);
        bdd.setBarcodeModifier((byte) 0x12);
        config.setCurrentBarCodeDataDescriptor(bdd);

        assertThrows(AFPParserException.class, () -> {
            bda.decodeAFP(shortData, 0, 16, config);
        });
    }

    @Test
    public void testBDAParametersAztecIncompatibleFNC1() {
        // [BCOCA-5-009] EC-0F1A: GS1 FNC1 and industry FNC1
        BDA_BarCodeData bda = new BDA_BarCodeData();
        byte[] data = new byte[15];
        data[9] = (byte) 0xC0; // GS1FNC1 (0x80) + IndustryFNC1 (0x40)

        AFPParserConfiguration config = new AFPParserConfiguration();
        BDD_BarCodeDataDescriptor bdd = new BDD_BarCodeDataDescriptor();
        bdd.setBarcodeType(BDD_BarCodeDataDescriptor.BarCodeType.AztecCode);
        config.setCurrentBarCodeDataDescriptor(bdd);

        assertThrows(AFPParserException.class, () -> {
            bda.decodeAFP(data, 0, 15, config);
        });
    }

    @Test
    public void testBDAParametersQRIncompatibleFNC1() {
        // [BCOCA-5-009] EC-0F11: GS1 FNC1 and industry FNC1
        BDA_BarCodeData bda = new BDA_BarCodeData();
        byte[] data = new byte[15];
        data[12] = (byte) 0xC0; // UCC_EAN_FNC1 (0x80) + IndustryFNC1 (0x40)

        AFPParserConfiguration config = new AFPParserConfiguration();
        BDD_BarCodeDataDescriptor bdd = new BDD_BarCodeDataDescriptor();
        bdd.setBarcodeType(BDD_BarCodeDataDescriptor.BarCodeType.QRCode_2D);
        config.setCurrentBarCodeDataDescriptor(bdd);

        assertThrows(AFPParserException.class, () -> {
            bda.decodeAFP(data, 0, 15, config);
        });
    }

    @Test
    public void testBDAParametersPDF417InvalidLength() {
        // [BCOCA-5-002] Specification-Check Exceptions: Invalid data parameters or values.
        BDA_BarCodeData bda = new BDA_BarCodeData();
        byte[] shortData = new byte[10]; // 5 bytes BDA header + 5 bytes (should be at least 6 for PDF417)

        AFPParserConfiguration config = new AFPParserConfiguration();
        BDD_BarCodeDataDescriptor bdd = new BDD_BarCodeDataDescriptor();
        bdd.setBarcodeType(BDD_BarCodeDataDescriptor.BarCodeType.PDF417_2D);
        config.setCurrentBarCodeDataDescriptor(bdd);

        assertThrows(AFPParserException.class, () -> {
            bda.decodeAFP(shortData, 0, 10, config);
        });
    }

    @Test
    public void testBDAParametersDataMatrixIncompatibleSAandRP() {
        // [BCOCA-5-003] EC-0F0A: structured append with reader programming
        BDA_BarCodeData bda = new BDA_BarCodeData();
        byte[] data = new byte[15];
        data[10] = 0x01; // sequenceIndicator = 1 (Structured Append)
        data[14] = 0x20; // SymbolEncodesAMessage (Reader Programming)

        AFPParserConfiguration config = new AFPParserConfiguration();
        BDD_BarCodeDataDescriptor bdd = new BDD_BarCodeDataDescriptor();
        bdd.setBarcodeType(BDD_BarCodeDataDescriptor.BarCodeType.DataMatrix_GS1DataMatrix_2D);
        config.setCurrentBarCodeDataDescriptor(bdd);

        assertThrows(AFPParserException.class, () -> {
            bda.decodeAFP(data, 0, 15, config);
        });
    }

    @Test
    public void testBDAParametersDataMatrixIncompatibleGS1andMacro() {
        // [BCOCA-5-004] EC-0F0A: GS1 FNC1 with macro
        BDA_BarCodeData bda = new BDA_BarCodeData();
        byte[] data = new byte[15];
        data[14] = (byte) 0x88; // SymbolConfirmsToGS1Standard (0x80) + UseMacro05HeaderTrailer (0x08)

        AFPParserConfiguration config = new AFPParserConfiguration();
        BDD_BarCodeDataDescriptor bdd = new BDD_BarCodeDataDescriptor();
        bdd.setBarcodeType(BDD_BarCodeDataDescriptor.BarCodeType.DataMatrix_GS1DataMatrix_2D);
        config.setCurrentBarCodeDataDescriptor(bdd);

        assertThrows(AFPParserException.class, () -> {
            bda.decodeAFP(data, 0, 15, config);
        });
    }

    @Test
    public void testBDAParametersDataMatrixIncompatibleIndustryandGS1() {
        // [BCOCA-5-005] EC-0F0A: industry FNC1 with GS1 FNC1
        BDA_BarCodeData bda = new BDA_BarCodeData();
        byte[] data = new byte[15];
        data[14] = (byte) 0xC0; // SymbolConfirmsToIndustryStandard (0x40) + SymbolConfirmsToGS1Standard (0x80)

        AFPParserConfiguration config = new AFPParserConfiguration();
        BDD_BarCodeDataDescriptor bdd = new BDD_BarCodeDataDescriptor();
        bdd.setBarcodeType(BDD_BarCodeDataDescriptor.BarCodeType.DataMatrix_GS1DataMatrix_2D);
        config.setCurrentBarCodeDataDescriptor(bdd);

        assertThrows(AFPParserException.class, () -> {
            bda.decodeAFP(data, 0, 15, config);
        });
    }

    @Test
    public void testBDAParametersDataMatrixIncompatibleRPandMacro() {
        // [BCOCA-5-006] EC-0F0A: reader programming with macro
        BDA_BarCodeData bda = new BDA_BarCodeData();
        byte[] data = new byte[15];
        data[14] = 0x30; // SymbolEncodesAMessage (0x20) + UseMacro06HeaderTrailer (0x10)

        AFPParserConfiguration config = new AFPParserConfiguration();
        BDD_BarCodeDataDescriptor bdd = new BDD_BarCodeDataDescriptor();
        bdd.setBarcodeType(BDD_BarCodeDataDescriptor.BarCodeType.DataMatrix_GS1DataMatrix_2D);
        config.setCurrentBarCodeDataDescriptor(bdd);

        assertThrows(AFPParserException.class, () -> {
            bda.decodeAFP(data, 0, 15, config);
        });
    }

    @Test
    public void testBDAParametersDataMatrixIncompatibleMacroandSA() {
        // [BCOCA-5-007] EC-0F0A: macro with structured append
        BDA_BarCodeData bda = new BDA_BarCodeData();
        byte[] data = new byte[15];
        data[10] = 0x02; // sequenceIndicator = 2 (Structured Append)
        data[14] = 0x08; // UseMacro05HeaderTrailer (0x08)

        AFPParserConfiguration config = new AFPParserConfiguration();
        BDD_BarCodeDataDescriptor bdd = new BDD_BarCodeDataDescriptor();
        bdd.setBarcodeType(BDD_BarCodeDataDescriptor.BarCodeType.DataMatrix_GS1DataMatrix_2D);
        config.setCurrentBarCodeDataDescriptor(bdd);

        assertThrows(AFPParserException.class, () -> {
            bda.decodeAFP(data, 0, 15, config);
        });
    }

    @Test
    public void testBDAParametersAztecInvalidLayers() {
        // [BCOCA-5-009] EC-0F18: Number of layers (0 to 32)
        BDA_BarCodeData bda = new BDA_BarCodeData();
        byte[] data = new byte[15];
        data[7] = 33; // Invalid (max 32)

        AFPParserConfiguration config = new AFPParserConfiguration();
        BDD_BarCodeDataDescriptor bdd = new BDD_BarCodeDataDescriptor();
        bdd.setBarcodeType(BDD_BarCodeDataDescriptor.BarCodeType.AztecCode);
        config.setCurrentBarCodeDataDescriptor(bdd);

        assertThrows(AFPParserException.class, () -> {
            bda.decodeAFP(data, 5, 10, config);
        });
    }

    @Test
    public void testBDAParametersAztecInvalidEC() {
        // [BCOCA-5-009] EC-0F19: Error correction level (5 to 95)
        BDA_BarCodeData bda = new BDA_BarCodeData();
        byte[] data = new byte[15];
        data[8] = 4; // Invalid (min 5)

        AFPParserConfiguration config = new AFPParserConfiguration();
        BDD_BarCodeDataDescriptor bdd = new BDD_BarCodeDataDescriptor();
        bdd.setBarcodeType(BDD_BarCodeDataDescriptor.BarCodeType.AztecCode);
        config.setCurrentBarCodeDataDescriptor(bdd);

        assertThrows(AFPParserException.class, () -> {
            bda.decodeAFP(data, 5, 10, config);
        });
    }

    @Test
    public void testBDAParametersHanXinInvalidVersion() {
        // [BCOCA-5-009] EC-0F22: Version number (0 to 84)
        BDA_BarCodeData bda = new BDA_BarCodeData();
        byte[] data = new byte[15];
        data[7] = 85; // Invalid (max 84)

        AFPParserConfiguration config = new AFPParserConfiguration();
        BDD_BarCodeDataDescriptor bdd = new BDD_BarCodeDataDescriptor();
        bdd.setBarcodeType(BDD_BarCodeDataDescriptor.BarCodeType.HanXinCode);
        config.setCurrentBarCodeDataDescriptor(bdd);

        assertThrows(AFPParserException.class, () -> {
            bda.decodeAFP(data, 5, 10, config);
        });
    }

    @Test
    public void testBDAParametersHanXinInvalidAppInd() {
        // [BCOCA-5-009] EC-0F25: Han Xin application indicator (0-99)
        BDA_BarCodeData bda = new BDA_BarCodeData();
        byte[] data = new byte[15];
        data[9] = 0x40; // Industry FNC1
        data[10] = 100; // Invalid (max 99)

        AFPParserConfiguration config = new AFPParserConfiguration();
        BDD_BarCodeDataDescriptor bdd = new BDD_BarCodeDataDescriptor();
        bdd.setBarcodeType(BDD_BarCodeDataDescriptor.BarCodeType.HanXinCode);
        config.setCurrentBarCodeDataDescriptor(bdd);

        assertThrows(AFPParserException.class, () -> {
            bda.decodeAFP(data, 5, 10, config);
        });
    }

    @Test
    public void testBDAParametersAztecInvalidAppInd() {
        // [BCOCA-5-009] EC-0F1B: Aztec application indicator
        BDA_BarCodeData bda = new BDA_BarCodeData();
        byte[] data = new byte[15];
        data[9] = 0x40; // Industry FNC1
        data[10] = (byte) 0x99; // Invalid (153)

        AFPParserConfiguration config = new AFPParserConfiguration();
        BDD_BarCodeDataDescriptor bdd = new BDD_BarCodeDataDescriptor();
        bdd.setBarcodeType(BDD_BarCodeDataDescriptor.BarCodeType.AztecCode);
        config.setCurrentBarCodeDataDescriptor(bdd);

        assertThrows(AFPParserException.class, () -> {
            bda.decodeAFP(data, 5, 10, config);
        });
    }

    @Test
    public void testBDAParametersAztecInvalidAppendIDLen() {
        // [BCOCA-5-009] EC-0F1C: Append ID length must be 0 if not part of structured append
        BDA_BarCodeData bda = new BDA_BarCodeData();
        byte[] data = new byte[15];
        data[11] = 0x00; // Not part of structured append
        data[13] = 0x01; // Append ID length = 1 (Invalid)

        AFPParserConfiguration config = new AFPParserConfiguration();
        BDD_BarCodeDataDescriptor bdd = new BDD_BarCodeDataDescriptor();
        bdd.setBarcodeType(BDD_BarCodeDataDescriptor.BarCodeType.AztecCode);
        config.setCurrentBarCodeDataDescriptor(bdd);

        assertThrows(AFPParserException.class, () -> {
            bda.decodeAFP(data, 5, 10, config);
        });
    }

    @Test
    public void testBDAParametersHanXinInvalidEC() {
        // [BCOCA-5-009] EC-0F23: Error correction level (1 to 4)
        BDA_BarCodeData bda = new BDA_BarCodeData();
        byte[] data = new byte[15];
        data[8] = 5; // Invalid (max 4)

        AFPParserConfiguration config = new AFPParserConfiguration();
        BDD_BarCodeDataDescriptor bdd = new BDD_BarCodeDataDescriptor();
        bdd.setBarcodeType(BDD_BarCodeDataDescriptor.BarCodeType.HanXinCode);
        config.setCurrentBarCodeDataDescriptor(bdd);

        assertThrows(AFPParserException.class, () -> {
            bda.decodeAFP(data, 5, 10, config);
        });
    }

    @Test
    public void testBDAParametersHanXinIncompatibleFNC1() {
        // [BCOCA-5-009] EC-0F24: mutually exclusive FNC1 flags
        BDA_BarCodeData bda = new BDA_BarCodeData();
        byte[] data = new byte[15];
        data[9] = (byte) 0xC0; // GS1FNC1 (0x80) + IndustryFNC1 (0x40)

        AFPParserConfiguration config = new AFPParserConfiguration();
        BDD_BarCodeDataDescriptor bdd = new BDD_BarCodeDataDescriptor();
        bdd.setBarcodeType(BDD_BarCodeDataDescriptor.BarCodeType.HanXinCode);
        config.setCurrentBarCodeDataDescriptor(bdd);

        assertThrows(AFPParserException.class, () -> {
            bda.decodeAFP(data, 5, 10, config);
        });
    }

    @Test
    public void testBDDInvalidBarcodeType() {
        // [BCOCA-4-032] EC-0300: Invalid bar code type
        BDD_BarCodeDataDescriptor bdd = new BDD_BarCodeDataDescriptor();
        byte[] data = new byte[23];
        data[0] = 0x00; // Inches10
        data[12] = (byte) 0xFF; // Invalid BarCodeType
        assertThrows(AFPParserException.class, () -> {
            bdd.decodeAFP(data, 0, 23, new AFPParserConfiguration());
        }, "Should throw EC-0300 for invalid barcode type");
    }

    @Test
    public void testBDDInvalidBarcodeModifier() {
        // [BCOCA-4-016] EC-0B00: Invalid bar code modifier
        BDD_BarCodeDataDescriptor bdd = new BDD_BarCodeDataDescriptor();
        byte[] data = new byte[23];
        data[0] = 0x00; // Inches10
        data[12] = 0x03; // UPC_CGPC_VersionA
        data[13] = 0x01; // Invalid modifier for UPC-A (only 0x00 is valid)
        assertThrows(AFPParserException.class, () -> {
            bdd.decodeAFP(data, 0, 23, new AFPParserConfiguration());
        }, "Should throw EC-0B00 for invalid barcode modifier");
    }

    @Test
    public void testBDDIncompatibleUnits() {
        // [BCOCA-4-011] EC-0605: Mismatched units per unit base
        BDD_BarCodeDataDescriptor bdd = new BDD_BarCodeDataDescriptor();
        byte[] data = new byte[23];
        data[0] = 0x00; // Inches10
        data[2] = 0x05;
        data[3] = (byte) 0xA0; // X = 1440
        data[4] = 0x00;
        data[5] = 0x01; // Y = 1 (Invalid, must be 1440)
        assertThrows(AFPParserException.class, () -> {
            bdd.decodeAFP(data, 0, 23, new AFPParserConfiguration());
        }, "Should throw EC-0605 for mismatched units");
    }

    @Test
    public void testBDAIncompatibleHRIPosition() {
        // [BCOCA-4-310] EC-1000: HRI position bits both B'1'
        BDA_BarCodeData bda = new BDA_BarCodeData();
        byte[] data = new byte[5];
        data[0] = 0x60; // Bits 1 and 2 are set (Above and Below)
        assertThrows(AFPParserException.class, () -> {
            bda.decodeAFP(data, 0, 5, new AFPParserConfiguration());
        }, "Should throw EC-1000 for incompatible HRI position bits");
    }

    @Test
    public void testBDAParametersQRInvalidVersionStandardAction() throws AFPParserException {
        // [BCOCA-5-010] EC-0F0F: QR Code invalid version. Standard Action: use 0.
        BDA_BarCodeData bda = new BDA_BarCodeData();
        byte[] data = new byte[14];
        data[7] = 41; // Invalid version (max 40)

        AFPParserConfiguration config = new AFPParserConfiguration();
        BDD_BarCodeDataDescriptor bdd = new BDD_BarCodeDataDescriptor();
        bdd.setBarcodeType(BDD_BarCodeDataDescriptor.BarCodeType.QRCode_2D);
        config.setCurrentBarCodeDataDescriptor(bdd);

        bda.decodeAFP(data, 0, 14, config);
        assertNotNull(bda.parametersData);
        assertEquals(0, ((BDA_BarCodeData.ParametersDataQRCode_2D) bda.parametersData).versionOfSymbol);
    }

    @Test
    public void testBDAParametersQRInvalidECStandardAction() throws AFPParserException {
        // [BCOCA-5-010] EC-0F10: QR Code invalid EC. Standard Action: use 3 (LevelH).
        BDA_BarCodeData bda = new BDA_BarCodeData();
        byte[] data = new byte[14];
        data[8] = 4; // Invalid EC level (0-3)

        AFPParserConfiguration config = new AFPParserConfiguration();
        BDD_BarCodeDataDescriptor bdd = new BDD_BarCodeDataDescriptor();
        bdd.setBarcodeType(BDD_BarCodeDataDescriptor.BarCodeType.QRCode_2D);
        config.setCurrentBarCodeDataDescriptor(bdd);

        bda.decodeAFP(data, 0, 14, config);
        assertNotNull(bda.parametersData);
        assertEquals(BDA_BarCodeData.ParametersDataQRCode_2D.ErrorCorrectionLevel.LevelH, ((BDA_BarCodeData.ParametersDataQRCode_2D) bda.parametersData).errorCorrectionLevel);
    }

    @Test
    public void testBDAParametersMaxiCodeInvalidSymbolMode() {
        // [BCOCA-4-545] EC-0F05: Invalid symbol-mode
        BDA_BarCodeData bda = new BDA_BarCodeData();
        byte[] data = new byte[10];
        data[6] = 0x07; // Invalid (2-6)

        AFPParserConfiguration config = new AFPParserConfiguration();
        BDD_BarCodeDataDescriptor bdd = new BDD_BarCodeDataDescriptor();
        bdd.setBarcodeType(BDD_BarCodeDataDescriptor.BarCodeType.MaxiCode_2D);
        config.setCurrentBarCodeDataDescriptor(bdd);

        AFPParserException ex = assertThrows(AFPParserException.class, () -> bda.decodeAFP(data, 0, 10, config));
        assertTrue(ex.getMessage().contains("EC-0F05"));
    }

    @Test
    public void testBDAParametersPDF417InvalidDataSymbols() {
        // [BCOCA-4-561] EC-0F06: Invalid data symbol characters per row
        BDA_BarCodeData bda = new BDA_BarCodeData();
        byte[] data = new byte[11];
        data[6] = 31; // Invalid (max 30)

        AFPParserConfiguration config = new AFPParserConfiguration();
        BDD_BarCodeDataDescriptor bdd = new BDD_BarCodeDataDescriptor();
        bdd.setBarcodeType(BDD_BarCodeDataDescriptor.BarCodeType.PDF417_2D);
        config.setCurrentBarCodeDataDescriptor(bdd);

        AFPParserException ex = assertThrows(AFPParserException.class, () -> bda.decodeAFP(data, 0, 11, config));
        assertTrue(ex.getMessage().contains("EC-0F06"));
    }

    @Test
    public void testBDAParametersPDF417InvalidRows() {
        // [BCOCA-4-561] EC-0F07: Invalid number of rows
        BDA_BarCodeData bda = new BDA_BarCodeData();
        byte[] data = new byte[11];
        data[6] = 10; // valid chars per row
        data[7] = 2; // Invalid (min 3)

        AFPParserConfiguration config = new AFPParserConfiguration();
        BDD_BarCodeDataDescriptor bdd = new BDD_BarCodeDataDescriptor();
        bdd.setBarcodeType(BDD_BarCodeDataDescriptor.BarCodeType.PDF417_2D);
        config.setCurrentBarCodeDataDescriptor(bdd);

        AFPParserException ex = assertThrows(AFPParserException.class, () -> bda.decodeAFP(data, 0, 11, config));
        assertTrue(ex.getMessage().contains("EC-0F07"));
    }

    @Test
    public void testBDAParametersPDF417InvalidSecurityLevel() {
        // [BCOCA-4-562] EC-0F09: Invalid security level
        BDA_BarCodeData bda = new BDA_BarCodeData();
        byte[] data = new byte[11];
        data[6] = 10; // valid chars per row
        data[7] = 10; // valid rows
        data[8] = 9; // Invalid (max 8)

        AFPParserConfiguration config = new AFPParserConfiguration();
        BDD_BarCodeDataDescriptor bdd = new BDD_BarCodeDataDescriptor();
        bdd.setBarcodeType(BDD_BarCodeDataDescriptor.BarCodeType.PDF417_2D);
        config.setCurrentBarCodeDataDescriptor(bdd);

        AFPParserException ex = assertThrows(AFPParserException.class, () -> bda.decodeAFP(data, 0, 11, config));
        assertTrue(ex.getMessage().contains("EC-0F09"));
    }

    @Test
    public void testBDAParametersPDF417InvalidMacroLength() {
        // [BCOCA-4-567] EC-0F0C: Invalid macro length
        BDA_BarCodeData bda = new BDA_BarCodeData();
        byte[] data = new byte[11];
        data[6] = 10; // valid chars per row
        data[7] = 10; // valid rows
        data[8] = 0; // valid security
        data[9] = (byte) 0xFF;
        data[10] = (byte) 0xFF; // length > 0x7FED

        AFPParserConfiguration config = new AFPParserConfiguration();
        BDD_BarCodeDataDescriptor bdd = new BDD_BarCodeDataDescriptor();
        bdd.setBarcodeType(BDD_BarCodeDataDescriptor.BarCodeType.PDF417_2D);
        config.setCurrentBarCodeDataDescriptor(bdd);

        AFPParserException ex = assertThrows(AFPParserException.class, () -> bda.decodeAFP(data, 0, 11, config));
        assertTrue(ex.getMessage().contains("EC-0F0C"));
    }

    @Test
    public void testBDAParametersIMPackageInvalidBannerLength() {
        // [BCOCA-4-530] EC-0F15: Banner length must be even
        BDA_BarCodeData bda = new BDA_BarCodeData();
        byte[] data = new byte[15];
        data[8] = 0x01; // Invalid (odd)

        AFPParserConfiguration config = new AFPParserConfiguration();
        BDD_BarCodeDataDescriptor bdd = new BDD_BarCodeDataDescriptor();
        bdd.setBarcodeType(BDD_BarCodeDataDescriptor.BarCodeType.Code_128__GS1_128__UCC_EAN_128__AIM_USS_128__IntelligentMail__ContainerBarcode);
        bdd.setBarcodeModifier((byte) 0x06);
        config.setCurrentBarCodeDataDescriptor(bdd);

        AFPParserException ex = assertThrows(AFPParserException.class, () -> bda.decodeAFP(data, 0, 15, config));
        assertTrue(ex.getMessage().contains("EC-0F15"));
    }

    @Test
    public void testBDAParametersIMPackageEmptyBanner() {
        // [BCOCA-4-531] EC-0F14: Banner length 0 but not suppressed
        BDA_BarCodeData bda = new BDA_BarCodeData();
        byte[] data = new byte[15];
        data[6] = 0x00; // Not suppressed
        data[8] = 0x00; // Empty

        AFPParserConfiguration config = new AFPParserConfiguration();
        BDD_BarCodeDataDescriptor bdd = new BDD_BarCodeDataDescriptor();
        bdd.setBarcodeType(BDD_BarCodeDataDescriptor.BarCodeType.Code_128__GS1_128__UCC_EAN_128__AIM_USS_128__IntelligentMail__ContainerBarcode);
        bdd.setBarcodeModifier((byte) 0x06);
        config.setCurrentBarCodeDataDescriptor(bdd);

        AFPParserException ex = assertThrows(AFPParserException.class, () -> bda.decodeAFP(data, 0, 15, config));
        assertTrue(ex.getMessage().contains("EC-0F14"));
    }

    @Test
    public void testBDAParametersIMPackageInvalidUTF16() {
        // [BCOCA-4-531] EC-0F13: Invalid UTF-16BE
        BDA_BarCodeData bda = new BDA_BarCodeData();
        byte[] data = new byte[15];
        data[6] = (byte) 0x80; // SuppressUSPS_ServiceBanner bit IS SET, so it WON'T check for empty.
        // BUT wait, I want to test invalid UTF-16BE, so I shouldn't suppress it.
        data[6] = 0x00; // Not suppressed
        data[8] = 0x02; // length 2
        data[9] = (byte) 0xD8;
        data[10] = 0x00; // Invalid UTF-16 (lone high surrogate)

        AFPParserConfiguration config = new AFPParserConfiguration();
        BDD_BarCodeDataDescriptor bdd = new BDD_BarCodeDataDescriptor();
        bdd.setBarcodeType(BDD_BarCodeDataDescriptor.BarCodeType.Code_128__GS1_128__UCC_EAN_128__AIM_USS_128__IntelligentMail__ContainerBarcode);
        bdd.setBarcodeModifier((byte) 0x06);
        config.setCurrentBarCodeDataDescriptor(bdd);

        AFPParserException ex = assertThrows(AFPParserException.class, () -> bda.decodeAFP(data, 0, 15, config));
        assertTrue(ex.getMessage().contains("EC-0F13"));
    }
}
