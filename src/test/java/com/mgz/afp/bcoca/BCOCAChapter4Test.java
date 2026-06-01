package com.mgz.afp.bcoca;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.mgz.afp.RoundTripTestUtils;
import com.mgz.afp.parser.AFPParserConfiguration;
import org.junit.jupiter.api.Test;

/**
 * Tests for BCOCA Chapter 4: Data Structures.
 * Verifies BDD (BSD) and BDA (BSA) fields and subsetting.
 */
public class BCOCAChapter4Test {

    @Test
    public void testBarCodeTypeSubsetting() {
        // Verifies Table 9: Bar Code Types and their subset membership (BCD1/BCD2)
        // [BCOCA-4-032] Byte 12 (Type) indicates the type of bar code symbol to be generated.

        assertSubsets(BDD_BarCodeDataDescriptor.BarCodeType.Code39_3of9Code_AIM_USS_39, true, true); // [BCOCA-4-033]
        assertSubsets(BDD_BarCodeDataDescriptor.BarCodeType.MSI_MmodifiedPlesseyCode, true, true); // [BCOCA-4-034]
        assertSubsets(BDD_BarCodeDataDescriptor.BarCodeType.UPC_CGPC_VersionA, true, true); // [BCOCA-4-035]
        assertSubsets(BDD_BarCodeDataDescriptor.BarCodeType.UPC_CGPC_VersionE, true, true); // [BCOCA-4-036]
        assertSubsets(BDD_BarCodeDataDescriptor.BarCodeType.UPC_TwoDigit_Supplemental_Periodicals, true, true); // [BCOCA-4-037]
        assertSubsets(BDD_BarCodeDataDescriptor.BarCodeType.UPC_FiveDigit_Supplemental_Paperbacks, true, true); // [BCOCA-4-038]
        assertSubsets(BDD_BarCodeDataDescriptor.BarCodeType.EAN_8_includingJANShort, true, true); // [BCOCA-4-039]
        assertSubsets(BDD_BarCodeDataDescriptor.BarCodeType.EAN_13_includingJANStandard, true, true); // [BCOCA-4-040]
        assertSubsets(BDD_BarCodeDataDescriptor.BarCodeType.Industrial_2of5, false, false); // [BCOCA-4-041]
        assertSubsets(BDD_BarCodeDataDescriptor.BarCodeType.Matrix_2ofFive, false, false); // [BCOCA-4-042]
        assertSubsets(BDD_BarCodeDataDescriptor.BarCodeType.Interleaved_2of5__ITF14__AIM_USS_I_2of5, true, true); // [BCOCA-4-043]
        assertSubsets(BDD_BarCodeDataDescriptor.BarCodeType.Codabar_2of7_AIM_USS_Codabar, false, true); // [BCOCA-4-044]
        assertSubsets(BDD_BarCodeDataDescriptor.BarCodeType.Code_128__GS1_128__UCC_EAN_128__AIM_USS_128__IntelligentMail__ContainerBarcode, false, true); // [BCOCA-4-045]
        assertSubsets(BDD_BarCodeDataDescriptor.BarCodeType.EAN_TwoDigit_Supplemental, true, true); // [BCOCA-4-046]
        assertSubsets(BDD_BarCodeDataDescriptor.BarCodeType.EAN_FiveDigit_Supplemental, true, true); // [BCOCA-4-047]
        assertSubsets(BDD_BarCodeDataDescriptor.BarCodeType.POSTNET_PLANET, false, false); // [BCOCA-4-048]
        assertSubsets(BDD_BarCodeDataDescriptor.BarCodeType.RM4SCC_DutchKIX, false, true); // [BCOCA-4-049]
        assertSubsets(BDD_BarCodeDataDescriptor.BarCodeType.JapanPostalBarCode, false, true); // [BCOCA-4-050]
        assertSubsets(BDD_BarCodeDataDescriptor.BarCodeType.DataMatrix_GS1DataMatrix_2D, false, true); // [BCOCA-4-051]
        assertSubsets(BDD_BarCodeDataDescriptor.BarCodeType.MaxiCode_2D, false, true); // [BCOCA-4-052]
        assertSubsets(BDD_BarCodeDataDescriptor.BarCodeType.PDF417_2D, false, true); // [BCOCA-4-053]
        assertSubsets(BDD_BarCodeDataDescriptor.BarCodeType.AustraliaPostBarCode, false, true); // [BCOCA-4-054]
        assertSubsets(BDD_BarCodeDataDescriptor.BarCodeType.QRCode_2D, false, true); // [BCOCA-4-055]
        assertSubsets(BDD_BarCodeDataDescriptor.BarCodeType.Code93, false, true); // [BCOCA-4-056]
        assertSubsets(BDD_BarCodeDataDescriptor.BarCodeType.IntelligentMailBarcode, false, true); // [BCOCA-4-057]
        assertSubsets(BDD_BarCodeDataDescriptor.BarCodeType.RoyalMail_RED_TAG, false, false); // [BCOCA-4-058]
        assertSubsets(BDD_BarCodeDataDescriptor.BarCodeType.GS1_DataBar, false, false); // [BCOCA-4-059]
        assertSubsets(BDD_BarCodeDataDescriptor.BarCodeType.RoyalMail_Mailmark, false, false); // [BCOCA-4-060]
        assertSubsets(BDD_BarCodeDataDescriptor.BarCodeType.AztecCode, false, false); // [BCOCA-4-061]
        assertSubsets(BDD_BarCodeDataDescriptor.BarCodeType.HanXinCode, false, false); // [BCOCA-4-062]
    }

    @Test
    public void testBCD2AdditionalBarCodeTypes() {
        // [BCOCA-4-001] BCD2 subset includes additional bar code types
        assertTrue(BDD_BarCodeDataDescriptor.BarCodeType.AustraliaPostBarCode.isBCD2());
        assertTrue(BDD_BarCodeDataDescriptor.BarCodeType.Codabar_2of7_AIM_USS_Codabar.isBCD2());
        assertTrue(BDD_BarCodeDataDescriptor.BarCodeType.Code93.isBCD2());
        assertTrue(BDD_BarCodeDataDescriptor.BarCodeType.Code_128__GS1_128__UCC_EAN_128__AIM_USS_128__IntelligentMail__ContainerBarcode.isBCD2());
        assertTrue(BDD_BarCodeDataDescriptor.BarCodeType.DataMatrix_GS1DataMatrix_2D.isBCD2());
        assertTrue(BDD_BarCodeDataDescriptor.BarCodeType.IntelligentMailBarcode.isBCD2());
        assertTrue(BDD_BarCodeDataDescriptor.BarCodeType.JapanPostalBarCode.isBCD2());
        assertTrue(BDD_BarCodeDataDescriptor.BarCodeType.MaxiCode_2D.isBCD2());
        assertTrue(BDD_BarCodeDataDescriptor.BarCodeType.PDF417_2D.isBCD2());
        assertTrue(BDD_BarCodeDataDescriptor.BarCodeType.QRCode_2D.isBCD2());
        assertTrue(BDD_BarCodeDataDescriptor.BarCodeType.RM4SCC_DutchKIX.isBCD2());
    }

    @Test
    public void testBDDFieldsRoundTrip() throws Exception {
        // [BCOCA-4-031] The module-width value is used when X'0000' is specified in the desired-symbol-width field.
        // [BCOCA-4-032] Byte 12 (Type) indicates the type of bar code symbol to be generated.
        // [BCOCA-4-008] to [BCOCA-4-022] (Table 8)

        // BDD: D3A6EB
        // Data (23 bytes):
        // UnitBase: 0x01 (10 centimeters) [BCOCA-4-008]
        // Reserved: 0x00 [BCOCA-4-009]
        // UnitsPerX: 0x2400 [BCOCA-4-010]
        // UnitsPerY: 0x2400 [BCOCA-4-011]
        // Width: 0x0100 [BCOCA-4-012]
        // Length: 0x0100 [BCOCA-4-013]
        // DesiredWidth: 0x0050 [BCOCA-4-014] [BCOCA-4-031]
        // Type: 0x1C (Data Matrix) [BCOCA-4-015] [BCOCA-4-051]
        // Modifier: 0x00 [BCOCA-4-016]
        // FontLocalID: 0x05 [BCOCA-4-017] [BCOCA-4-004]
        // Color: 0x0008 (Blue) [BCOCA-4-018]
        // ModuleWidth: 0x0F (15 mils) [BCOCA-4-019]
        // ElementHeight: 0x0020 [BCOCA-4-020]
        // HeightMultiplier: 0x01 [BCOCA-4-021]
        // Ratio: 0x0000 [BCOCA-4-022]

        byte[] data = new byte[] {
            0x5A, 0x00, 0x1F, (byte) 0xD3, (byte) 0xA6, (byte) 0xEB, 0x00, 0x00, 0x00,
            0x01, 0x00, 0x24, 0x00, 0x24, 0x00, 0x01, 0x00, 0x01, 0x00, 0x00, 0x50,
            0x1C, 0x00, 0x05, 0x00, 0x08, 0x0F, 0x00, 0x20, 0x01, 0x00, 0x00
        };

        RoundTripTestUtils.assertRoundTrip(new BDD_BarCodeDataDescriptor(), data);
    }

    @Test
    public void testBDAParametersDataMatrixRoundTrip() throws Exception {
        // [BCOCA-4-307] to [BCOCA-4-320] (Table 20)
        // [BCOCA-4-318] Special-function information

        // BDA: D3EEEB
        // Flags: 0x00
        // X Offset: 0x0100
        // Y Offset: 0x0200
        // Parameters (10 bytes):
        //   Control: 0x80 (Convert EBCDIC to ASCII)
        //   DesiredRowSize: 0x0020
        //   DesiredNumberOfRows: 0x0030
        //   SeqInd: 0x01
        //   TotalSymbols: 0x02
        //   FileID1: 0x03
        //   FileID2: 0x04
        //   SpecialFlags: 0x80 (GS1)
        // Data: 0x41 0x42 0x43 (ABC)

        byte[] data = new byte[] {
            0x5A, 0x00, 0x1B, (byte) 0xD3, (byte) 0xEE, (byte) 0xEB, 0x00, 0x00, 0x00,
            0x00, 0x01, 0x00, 0x02, 0x00,
            (byte) 0x80, 0x00, 0x20, 0x00, 0x30, 0x01, 0x02, 0x03, 0x04, (byte) 0x80,
            0x41, 0x42, 0x43
        };

        AFPParserConfiguration config = new AFPParserConfiguration();
        BDD_BarCodeDataDescriptor bdd = new BDD_BarCodeDataDescriptor();
        bdd.setBarcodeType(BDD_BarCodeDataDescriptor.BarCodeType.DataMatrix_GS1DataMatrix_2D);
        config.setCurrentBarCodeDataDescriptor(bdd);

        RoundTripTestUtils.assertRoundTrip(new BDA_BarCodeData(), data, config);
    }

    @Test
    public void testBDAParametersMaxiCodeRoundTrip() throws Exception {
        // [BCOCA-4-318] [BCOCA-4-546]
        // Parameters (5 bytes):
        //   Control: 0x40 (Ignore Escape Sequences)
        //   SymbolMode: 0x02
        //   SeqInd: 0x01
        //   TotalSymbols: 0x02
        //   SpecialFlags: 0x80 (Zipper)

        byte[] data = new byte[] {
            0x5A, 0x00, 0x16, (byte) 0xD3, (byte) 0xEE, (byte) 0xEB, 0x00, 0x00, 0x00,
            0x00, 0x00, 0x00, 0x00, 0x00,
            0x40, 0x02, 0x01, 0x02, (byte) 0x80,
            0x41, 0x42, 0x43
        };

        AFPParserConfiguration config = new AFPParserConfiguration();
        BDD_BarCodeDataDescriptor bdd = new BDD_BarCodeDataDescriptor();
        bdd.setBarcodeType(BDD_BarCodeDataDescriptor.BarCodeType.MaxiCode_2D);
        config.setCurrentBarCodeDataDescriptor(bdd);

        RoundTripTestUtils.assertRoundTrip(new BDA_BarCodeData(), data, config);
    }

    @Test
    public void testBDAParametersPDF417RoundTrip() throws Exception {
        // [BCOCA-4-318] [BCOCA-4-548]
        // Parameters (6 + macro length bytes):
        //   Control: 0x00
        //   DataSymbols: 0x05
        //   Rows: 0x0A
        //   Security: 0x02
        //   MacroLength: 0x0002
        //   MacroData: 0xEE 0xFF

        byte[] data = new byte[] {
            0x5A, 0x00, 0x19, (byte) 0xD3, (byte) 0xEE, (byte) 0xEB, 0x00, 0x00, 0x00,
            0x00, 0x00, 0x00, 0x00, 0x00,
            0x00, 0x05, 0x0A, 0x02, 0x00, 0x02, (byte) 0xEE, (byte) 0xFF,
            0x41, 0x42, 0x43
        };

        AFPParserConfiguration config = new AFPParserConfiguration();
        BDD_BarCodeDataDescriptor bdd = new BDD_BarCodeDataDescriptor();
        bdd.setBarcodeType(BDD_BarCodeDataDescriptor.BarCodeType.PDF417_2D);
        config.setCurrentBarCodeDataDescriptor(bdd);

        RoundTripTestUtils.assertRoundTrip(new BDA_BarCodeData(), data, config);
    }

    @Test
    public void testBDAParametersQRCodeRoundTrip() throws Exception {
        // [BCOCA-4-318] [BCOCA-4-572] (Table 31)
        // Parameters (9 bytes):
        //   Control: 0x00
        //   Conversion: 0x01
        //   Version: 0x02
        //   ErrorCorrection: 0x03
        //   SeqInd: 0x04
        //   TotalSymbols: 0x05
        //   Parity: 0x06
        //   SpecialFlags: 0x80 (UCC_EAN_FNC1) [BCOCA-4-585]
        //   AppInd: 0x07

        byte[] data = new byte[] {
            0x5A, 0x00, 0x1A, (byte) 0xD3, (byte) 0xEE, (byte) 0xEB, 0x00, 0x00, 0x00,
            0x00, 0x00, 0x00, 0x00, 0x00,
            0x00, 0x01, 0x02, 0x03, 0x04, 0x05, 0x06, (byte) 0x80, 0x07,
            0x41, 0x42, 0x43
        };

        AFPParserConfiguration config = new AFPParserConfiguration();
        BDD_BarCodeDataDescriptor bdd = new BDD_BarCodeDataDescriptor();
        bdd.setBarcodeType(BDD_BarCodeDataDescriptor.BarCodeType.QRCode_2D);
        config.setCurrentBarCodeDataDescriptor(bdd);

        RoundTripTestUtils.assertRoundTrip(new BDA_BarCodeData(), data, config);
    }

    @Test
    public void testBDAParametersQRCodeWithImageRoundTrip() throws Exception {
        // [BCOCA-4-318] [BCOCA-4-633] (Table 33)
        // Common QR Parameters (9 bytes)
        // Plus QR with Image Parameters (3 bytes)
        // Plus 1 Image Block (23 bytes)
        // Total Params: 9 + 3 + 23 = 35 bytes
        // BDA overhead: 5 bytes
        // Data: 3 bytes (ABC)
        // Total SF Length: 5 + 35 + 3 = 43 (0x2B)

        byte[] data = new byte[] {
            0x5A, 0x00, 0x2B, (byte) 0xD3, (byte) 0xEE, (byte) 0xEB, 0x00, 0x00, 0x00,
            0x00, 0x00, 0x00, 0x00, 0x00,
            // Common QR (9 bytes)
            0x00, 0x01, 0x02, 0x03, 0x04, 0x05, 0x06, (byte) 0x80, 0x07,
            // QR with Image (3 bytes)
            (byte) 0xC0, 0x00, 0x17,
            // Image Information Block (23 bytes)
            0x16, 0x00, 0x00, 0x00, 0x01, 0x64, 0x00, 0x64, 0x00, 0x2A, 0x00, 0x2A, 0x00, 0x00, (byte) 0xF0, 0x64, 0x00, 0x64, 0x00, 0x14, 0x00, 0x14, 0x10,
            // Barcode data
            0x41, 0x42, 0x43
        };

        AFPParserConfiguration config = new AFPParserConfiguration();
        BDD_BarCodeDataDescriptor bdd = new BDD_BarCodeDataDescriptor();
        bdd.setBarcodeType(BDD_BarCodeDataDescriptor.BarCodeType.QRCode_2D);
        bdd.setBarcodeModifier((byte) 0x12);
        config.setCurrentBarCodeDataDescriptor(bdd);

        RoundTripTestUtils.assertRoundTrip(new BDA_BarCodeData(), data, config);
    }

    @Test
    public void testBDAParametersAztecCodeRoundTrip() throws Exception {
        // [BCOCA-4-318] [BCOCA-4-354] (Table 21)
        // Parameters: 10 fixed + 2 ID + 1 length + 2 Addl
        // Total Params: 15 bytes
        // Total SF: 5 + 15 + 3 = 23 (0x17)

        byte[] data = new byte[] {
            0x5A, 0x00, 0x17, (byte) 0xD3, (byte) 0xEE, (byte) 0xEB, 0x00, 0x00, 0x00,
            0x00, 0x00, 0x00, 0x00, 0x00,
            // Aztec (15 bytes)
            (byte) 0xA0, 0x00, 0x05, 0x17, (byte) 0xC0, 0x01, 0x02, 0x03, 0x02, 0x58, 0x59, 0x02, 0x11, 0x22,
            // Barcode data
            0x41, 0x42, 0x43
        };

        AFPParserConfiguration config = new AFPParserConfiguration();
        BDD_BarCodeDataDescriptor bdd = new BDD_BarCodeDataDescriptor();
        bdd.setBarcodeType(BDD_BarCodeDataDescriptor.BarCodeType.AztecCode);
        config.setCurrentBarCodeDataDescriptor(bdd);

        RoundTripTestUtils.assertRoundTrip(new BDA_BarCodeData(), data, config);
    }

    @Test
    public void testBDAParametersHanXinCodeRoundTrip() throws Exception {
        // [BCOCA-4-318] [BCOCA-4-474] (Table 26)
        // Parameters: 7 fixed + 2 Addl
        // Total Params: 9 bytes
        // Total SF: 5 + 9 + 3 = 17 (0x11)

        byte[] data = new byte[] {
            0x5A, 0x00, 0x11, (byte) 0xD3, (byte) 0xEE, (byte) 0xEB, 0x00, 0x00, 0x00,
            0x00, 0x00, 0x00, 0x00, 0x00,
            // Han Xin (9 bytes)
            (byte) 0x80, 0x00, 0x0A, 0x02, (byte) 0x80, 0x01, 0x02, 0x11, 0x22,
            // Barcode data
            0x41, 0x42, 0x43
        };

        AFPParserConfiguration config = new AFPParserConfiguration();
        BDD_BarCodeDataDescriptor bdd = new BDD_BarCodeDataDescriptor();
        bdd.setBarcodeType(BDD_BarCodeDataDescriptor.BarCodeType.HanXinCode);
        config.setCurrentBarCodeDataDescriptor(bdd);

        RoundTripTestUtils.assertRoundTrip(new BDA_BarCodeData(), data, config);
    }

    @Test
    public void testBDAParametersIntelligentMailPackageBarcodeRoundTrip() throws Exception {
        // [BCOCA-4-318] [BCOCA-4-521] (Table 28)
        // Parameters: 4 fixed + 4 Banner
        // Total Params: 8 bytes
        // Total SF: 5 + 8 + 3 = 16 (0x10)

        byte[] data = new byte[] {
            0x5A, 0x00, 0x10, (byte) 0xD3, (byte) 0xEE, (byte) 0xEB, 0x00, 0x00, 0x00,
            0x00, 0x00, 0x00, 0x00, 0x00,
            // IM Package (8 bytes)
            0x00, (byte) 0x80, 0x00, 0x04, 0x11, 0x22, 0x33, 0x44,
            // Barcode data
            0x41, 0x42, 0x43
        };

        AFPParserConfiguration config = new AFPParserConfiguration();
        BDD_BarCodeDataDescriptor bdd = new BDD_BarCodeDataDescriptor();
        bdd.setBarcodeType(BDD_BarCodeDataDescriptor.BarCodeType.Code_128__GS1_128__UCC_EAN_128__AIM_USS_128__IntelligentMail__ContainerBarcode);
        bdd.setBarcodeModifier((byte) 0x06);
        config.setCurrentBarCodeDataDescriptor(bdd);

        RoundTripTestUtils.assertRoundTrip(new BDA_BarCodeData(), data, config);
    }

    @Test
    public void testBarCodeFlagsMapping() throws Exception {
        // [BCOCA-4-308] to [BCOCA-4-315]
        // Flags (Byte 0):
        //   Bit 0 (0x80): HRI not presented [BCOCA-4-309]
        //   Bit 1 (0x40): HRI above [BCOCA-4-310]
        //   Bit 2 (0x20): HRI below [BCOCA-4-310]
        //   Bit 3 (0x10): SSCAST [BCOCA-4-311]
        //   Bit 5 (0x04): Suppress symbol [BCOCA-4-313]
        //   Bit 6 (0x02): Suppress trailing blanks [BCOCA-4-314]

        byte flagByte = (byte) (0x80 | 0x40 | 0x10 | 0x04 | 0x02);
        byte[] data = new byte[] {
            0x5A, 0x00, 0x08, (byte) 0xD3, (byte) 0xEE, (byte) 0xEB, 0x00, 0x00, 0x00,
            flagByte, 0x00, 0x00, 0x00, 0x00
        };

        BDA_BarCodeData bda = new BDA_BarCodeData();
        bda.decodeAFP(data, 6, 8, new AFPParserConfiguration());

        assertTrue(bda.getBarCodeFlags().contains(BDA_BarCodeData.BarCodeFlag.HRINotPresent));
        assertTrue(bda.getBarCodeFlags().contains(BDA_BarCodeData.BarCodeFlag.PositionHRIAbove));
        assertTrue(bda.getBarCodeFlags().contains(BDA_BarCodeData.BarCodeFlag.SSCASTAsteriskIsPresent));
        assertTrue(bda.getBarCodeFlags().contains(BDA_BarCodeData.BarCodeFlag.SuppressBarCodeSymbol));
        assertTrue(bda.getBarCodeFlags().contains(BDA_BarCodeData.BarCodeFlag.SuppressAndAdjustBlanks));

        RoundTripTestUtils.assertRoundTrip(new BDA_BarCodeData(), data);

        // Test the other position bit
        data[9] = 0x20; // HRI below
        bda = new BDA_BarCodeData();
        bda.decodeAFP(data, 6, 8, new AFPParserConfiguration());
        assertTrue(bda.getBarCodeFlags().contains(BDA_BarCodeData.BarCodeFlag.PositionHRIBelow));
        RoundTripTestUtils.assertRoundTrip(new BDA_BarCodeData(), data);
    }

    private void assertSubsets(BDD_BarCodeDataDescriptor.BarCodeType type, boolean bcd1, boolean bcd2) {
        assertEquals(bcd1, type.isBCD1(), "Type " + type + " BCD1 mismatch");
        assertEquals(bcd2, type.isBCD2(), "Type " + type + " BCD2 mismatch");
    }
}
