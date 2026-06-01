package com.mgz.afp.bcoca;

import static org.junit.jupiter.api.Assertions.assertEquals;

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
    public void testBDDFieldsRoundTrip() throws Exception {
        // [BCOCA-4-031] The module-width value is used when X'0000' is specified in the desired-symbol-width field.
        // [BCOCA-4-032] Byte 12 (Type) indicates the type of bar code symbol to be generated.

        // BDD: D3A6EB
        // Data (23 bytes):
        // UnitBase: 0x01 (10 centimeters)
        // Reserved: 0x00
        // UnitsPerX: 0x2400 (5670 rounded or some custom value)
        // UnitsPerY: 0x2400
        // Width: 0x0100
        // Length: 0x0100
        // DesiredWidth: 0x0050 [BCOCA-4-031]
        // Type: 0x1C (Data Matrix) [BCOCA-4-051]
        // Modifier: 0x00
        // FontLocalID: 0x05 [BCOCA-4-004]
        // Color: 0x0008 (Blue)
        // ModuleWidth: 0x0F (15 mils)
        // ElementHeight: 0x0020
        // HeightMultiplier: 0x01
        // Ratio: 0x0000

        byte[] data = new byte[] {
            0x5A, 0x00, 0x1F, (byte) 0xD3, (byte) 0xA6, (byte) 0xEB, 0x00, 0x00, 0x00,
            0x01, 0x00, 0x24, 0x00, 0x24, 0x00, 0x01, 0x00, 0x01, 0x00, 0x00, 0x50,
            0x1C, 0x00, 0x05, 0x00, 0x08, 0x0F, 0x00, 0x20, 0x01, 0x00, 0x00
        };

        com.mgz.afp.RoundTripTestUtils.assertRoundTrip(new BDD_BarCodeDataDescriptor(), data);
    }

    private void assertSubsets(BDD_BarCodeDataDescriptor.BarCodeType type, boolean bcd1, boolean bcd2) {
        assertEquals(bcd1, type.isBCD1(), "Type " + type + " BCD1 mismatch");
        assertEquals(bcd2, type.isBCD2(), "Type " + type + " BCD2 mismatch");
    }
}
