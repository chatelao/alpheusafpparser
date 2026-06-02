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

import com.mgz.afp.exceptions.AFPParserException;
import com.mgz.afp.parser.AFPParserConfiguration;
import org.junit.jupiter.api.Test;

/**
 * Tests for BCOCA Chapter 5: Exception Conditions.
 * Verifies that the parser correctly detects and reports exception conditions.
 */
public class BCOCAChapter5Test {

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
}
