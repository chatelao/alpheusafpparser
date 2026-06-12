package com.mgz.afp.cmoca;

import com.mgz.util.UtilBinaryDecoding;
import org.junit.jupiter.api.Test;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

public class CMRTagVerificationTest {

    @Test
    public void testTagOrdering() throws Exception {
        // [CMOCA-5-018] The tags in a CMR must be specified in increasing order by their TagIDs.
        byte[] cmrData = new byte[48];

        // Tag 1: ID 0x0011
        cmrData[0] = 0x00;
        cmrData[1] = 0x11;
        cmrData[3] = 0x01; // Field type 1
        cmrData[7] = 0x01; // Count 1
        cmrData[11] = 0x01; // Value 1

        // Tag 2: ID 0x0004 (Out of order!)
        cmrData[12] = 0x00;
        cmrData[13] = 0x04;
        cmrData[15] = 0x06; // Field type 6
        cmrData[19] = 0x04; // Count 4

        // Tag 3: ID 0xFFFF (End Data)
        cmrData[24] = (byte) 0xFF;
        cmrData[25] = (byte) 0xFF;
        cmrData[27] = 0x05;

        Exception e = assertThrows(Exception.class, () -> {
            CMRTag.parseTags(cmrData);
        });
        assertTrue(e.getMessage().contains("EC-00040F"), "Expected EC-00040F, got: " + e.getMessage());
    }

    @Test
    public void testTag1055Validation() throws Exception {
        // [CMOCA-5-130] Error Diffusion Filter
        byte[] cmrData = new byte[64];
        // NumComponents = 1 (default)
        // Array Width = 2
        cmrData[0] = 0x10;
        cmrData[1] = 0x21;
        cmrData[3] = 0x01;
        cmrData[7] = 0x01;
        cmrData[8] = 0x02;

        // Array Height = 3
        cmrData[12] = 0x10;
        cmrData[13] = 0x25;
        cmrData[15] = 0x01;
        cmrData[19] = 0x01;
        cmrData[20] = 0x03;

        // Error Diffusion Filter. Count should be 2 * 3 = 6
        cmrData[24] = 0x10;
        cmrData[25] = 0x55;
        cmrData[27] = 0x01;
        cmrData[31] = 0x06;
        cmrData[35] = 48; // offset to data

        cmrData[36] = (byte) 0xFF;
        cmrData[37] = (byte) 0xFF;
        cmrData[39] = 0x05;

        CMRTag.parseTags(cmrData);

        // Invalid Field Type
        cmrData[27] = 0x02; // expected 0x01
        assertThrows(Exception.class, () -> CMRTag.parseTags(cmrData));
    }

    @Test
    public void testTag1021Validation() throws Exception {
        // [CMOCA-5-101] TagID: X'1021' Array Width
        // [CMOCA-5-102] Field Type: X'01' or X'02'
        byte[] cmrData = new byte[48];
        cmrData[0] = 0x10;
        cmrData[1] = 0x21;
        cmrData[3] = 0x05; // Invalid Field Type (BYTE)
        cmrData[7] = 0x01; // Count 1 (Default numComponents is 1)

        cmrData[12] = (byte) 0xFF;
        cmrData[13] = (byte) 0xFF;
        cmrData[15] = 0x05;

        Exception e = assertThrows(Exception.class, () -> CMRTag.parseTags(cmrData));
        assertTrue(e.getMessage().contains("EC-102106"));

        cmrData[3] = 0x01; // Valid
        CMRTag.parseTags(cmrData);

        cmrData[3] = 0x02; // Valid
        CMRTag.parseTags(cmrData);
    }

    @Test
    public void testTag1025Validation() throws Exception {
        // [CMOCA-5-105] TagID: X'1025' Array Height
        // [CMOCA-5-106] Field Type: X'01' or X'02'
        byte[] cmrData = new byte[48];
        cmrData[0] = 0x10;
        cmrData[1] = 0x25;
        cmrData[3] = 0x04; // Invalid Field Type (4-byte UBIN)
        cmrData[7] = 0x01; // Count 1 (Default numComponents is 1)

        cmrData[12] = (byte) 0xFF;
        cmrData[13] = (byte) 0xFF;
        cmrData[15] = 0x05;

        Exception e = assertThrows(Exception.class, () -> CMRTag.parseTags(cmrData));
        assertTrue(e.getMessage().contains("EC-102506"));

        cmrData[3] = 0x01; // Valid
        CMRTag.parseTags(cmrData);

        cmrData[3] = 0x02; // Valid
        CMRTag.parseTags(cmrData);
    }

    @Test
    public void testTag1065Validation() throws Exception {
        // [CMOCA-5-139] TagID: X'1065' Raster Direction
        // [CMOCA-5-140] Field Type: X'08' (CODE)
        byte[] cmrData = new byte[48];
        cmrData[0] = 0x10;
        cmrData[1] = 0x65;
        cmrData[3] = 0x01; // Invalid Field Type (UBIN)
        cmrData[7] = 0x01; // Count 1 (Default numComponents is 1)

        cmrData[12] = (byte) 0xFF;
        cmrData[13] = (byte) 0xFF;
        cmrData[15] = 0x05;

        Exception e = assertThrows(Exception.class, () -> CMRTag.parseTags(cmrData));
        assertTrue(e.getMessage().contains("EC-106506"));

        cmrData[3] = 0x08;
        cmrData[7] = 0x01;
        cmrData[8] = 0x03; // Invalid Value (expected 1 or 2), left-aligned inline
        Exception e2 = assertThrows(Exception.class, () -> CMRTag.parseTags(cmrData));
        assertTrue(e2.getMessage().contains("EC-106510"));

        cmrData[8] = 0x01; // Normal raster - Valid
        List<CMRTag> tags1 = CMRTag.parseTags(cmrData);
        assertEquals(1, tags1.get(0).getData()[0]);

        cmrData[8] = 0x02; // Serpentine raster - Valid
        List<CMRTag> tags2 = CMRTag.parseTags(cmrData);
        assertEquals(2, tags2.get(0).getData()[0]);
    }

    @Test
    public void testTagReservedByte() throws Exception {
        // [CMOCA-5-004] Reserved X'00' Should be set to zero
        byte[] cmrData = new byte[48];

        // Tag 1: ID 0x0004, Reserved = 0x01
        cmrData[0] = 0x00;
        cmrData[1] = 0x04;
        cmrData[2] = 0x01; // Invalid reserved byte
        cmrData[3] = 0x06;
        cmrData[7] = 0x04;

        // Tag 2: End Data
        cmrData[12] = (byte) 0xFF;
        cmrData[13] = (byte) 0xFF;
        cmrData[15] = 0x05;

        // Currently, we don't throw for non-zero reserved, but we can verify it parses correctly
        List<CMRTag> tags = CMRTag.parseTags(cmrData);
        assertEquals(2, tags.size());
        assertEquals(0x0004, tags.get(0).getTagId());
    }

    @Test
    public void testTagStructure() throws Exception {
        // [CMOCA-5-002] Each tag consists of 12 bytes
        // [CMOCA-5-003] 0–1 TagID
        // [CMOCA-5-005] 3 Field Type
        // [CMOCA-5-013] 4–7 Count
        // [CMOCA-5-014] 8–11 ValueOffset

        byte[] cmrData = new byte[48];
        int tagId = 0x1234;
        int fieldType = 0x04; // 4-byte UBIN
        long count = 1;
        long value = 0x11223344;

        System.arraycopy(UtilBinaryDecoding.intToByteArray(tagId, 2), 0, cmrData, 0, 2);
        cmrData[3] = (byte) fieldType;
        System.arraycopy(UtilBinaryDecoding.longToByteArray(count, 4), 0, cmrData, 4, 4);
        System.arraycopy(UtilBinaryDecoding.longToByteArray(value, 4), 0, cmrData, 8, 4);

        cmrData[12] = (byte) 0xFF;
        cmrData[13] = (byte) 0xFF;
        cmrData[15] = 0x05;

        List<CMRTag> tags = CMRTag.parseTags(cmrData);
        assertEquals(2, tags.size());
        CMRTag tag = tags.get(0);
        assertEquals(tagId, tag.getTagId());
        assertEquals(fieldType, tag.getFieldType());
        assertEquals(count, tag.getCount());
        assertArrayEquals(UtilBinaryDecoding.longToByteArray(value, 4), tag.getData());
    }

    @Test
    public void testTagOutOfBounds() throws Exception {
        // [CMOCA-5-028] EC-xxxx10 Invalid Value: offset causes data to be outside CMR
        byte[] cmrData = new byte[24];
        cmrData[1] = 0x04; // Tag X'0004' (Comment)
        cmrData[3] = 0x06; // Field Type 6 (ASCII)
        cmrData[7] = 0x05; // Count 5 ( > 4 bytes, so uses offset)
        cmrData[11] = 0x20; // Offset 32 (too large for 24-byte array)

        cmrData[13] = (byte) 0xFF;
        cmrData[12] = (byte) 0xFF;
        cmrData[15] = 0x05;

        Exception e = assertThrows(Exception.class, () -> CMRTag.parseTags(cmrData));
        assertTrue(e.getMessage().contains("EC-000410"), "Expected EC-000410, got: " + e.getMessage());
    }

    @Test
    public void testTagStructuralValidation() throws Exception {
        // [CMOCA-5-039] Comment tag invalid Field Type
        byte[] cmrData = new byte[48];
        cmrData[1] = 0x04; // Tag X'0004'
        cmrData[3] = 0x01; // Invalid field type 1 (expected 6 or 7)
        cmrData[13] = (byte) 0xFF; // End Data
        cmrData[12] = (byte) 0xFF;
        cmrData[15] = 0x05;

        assertThrows(Exception.class, () -> CMRTag.parseTags(cmrData));

        // [CMOCA-5-110] Max Image Value tag invalid Field Type
        byte[] cmrDataMV = new byte[48];
        cmrDataMV[0] = 0x10;
        cmrDataMV[1] = 0x30; // Tag X'1030'
        cmrDataMV[3] = 0x08; // Invalid field type 8 (expected 1, 2, or 4)
        cmrDataMV[13] = (byte) 0xFF;
        cmrDataMV[12] = (byte) 0xFF;
        cmrDataMV[15] = 0x05;
        assertThrows(Exception.class, () -> CMRTag.parseTags(cmrDataMV));

        // [CMOCA-5-114] Num Device Levels tag invalid Field Type
        byte[] cmrDataDL = new byte[48];
        cmrDataDL[0] = 0x10;
        cmrDataDL[1] = 0x35; // Tag X'1035'
        cmrDataDL[3] = 0x02; // Invalid field type 2 (expected 1)
        cmrDataDL[13] = (byte) 0xFF;
        cmrDataDL[12] = (byte) 0xFF;
        cmrDataDL[15] = 0x05;
        assertThrows(Exception.class, () -> CMRTag.parseTags(cmrDataDL));

        // [CMOCA-5-118] Offset Tiling tag invalid Field Type
        byte[] cmrDataOT = new byte[48];
        cmrDataOT[0] = 0x10;
        cmrDataOT[1] = 0x40; // Tag X'1040'
        cmrDataOT[3] = 0x04; // Invalid field type 4 (expected 1 or 2)
        cmrDataOT[13] = (byte) 0xFF;
        cmrDataOT[12] = (byte) 0xFF;
        cmrDataOT[15] = 0x05;
        assertThrows(Exception.class, () -> CMRTag.parseTags(cmrDataOT));

        // [CMOCA-5-146, 150] Boundary Condition tag
        byte[] cmrDataBC = new byte[48];
        cmrDataBC[0] = 0x10;
        cmrDataBC[1] = 0x70; // Tag X'1070'
        cmrDataBC[3] = 0x01; // Invalid field type (expected 8)
        cmrDataBC[13] = (byte) 0xFF;
        cmrDataBC[12] = (byte) 0xFF;
        cmrDataBC[15] = 0x05;
        assertThrows(Exception.class, () -> CMRTag.parseTags(cmrDataBC));

        cmrDataBC[3] = 0x08; // Valid type
        cmrDataBC[7] = 0x01; // Count 1
        cmrDataBC[11] = 0x05; // Invalid value 5 (expected 1-4)
        assertThrows(Exception.class, () -> CMRTag.parseTags(cmrDataBC));

        // [CMOCA-5-174, 176] TTC Length tag
        byte[] cmrDataTL = new byte[48];
        cmrDataTL[0] = 0x20;
        cmrDataTL[1] = 0x11; // Tag X'2011'
        cmrDataTL[3] = 0x08;
        cmrDataTL[7] = 0x01;
        cmrDataTL[11] = 0x03; // Invalid value 3 (expected 1-2)
        cmrDataTL[13] = (byte) 0xFF;
        cmrDataTL[12] = (byte) 0xFF;
        cmrDataTL[15] = 0x05;
        assertThrows(Exception.class, () -> CMRTag.parseTags(cmrDataTL));

        // [CMOCA-5-219] ICC Profile Filename tag
        byte[] cmrDataFN = new byte[48];
        cmrDataFN[0] = 0x30;
        cmrDataFN[1] = 0x25; // Tag X'3025'
        cmrDataFN[3] = 0x08; // Invalid field type 8 (expected 6 or 7)
        cmrDataFN[13] = (byte) 0xFF;
        cmrDataFN[12] = (byte) 0xFF;
        cmrDataFN[15] = 0x05;
        assertThrows(Exception.class, () -> CMRTag.parseTags(cmrDataFN));

        // [CMOCA-5-240, 241] Default Rendering Intent tag
        byte[] cmrDataRI = new byte[48];
        cmrDataRI[0] = 0x40;
        cmrDataRI[1] = 0x35; // Tag X'4035'
        cmrDataRI[3] = 0x08;
        cmrDataRI[7] = 0x01;
        cmrDataRI[8] = 0x04; // Invalid value 4 (expected 0-3), left-aligned inline
        cmrDataRI[13] = (byte) 0xFF;
        cmrDataRI[12] = (byte) 0xFF;
        cmrDataRI[15] = 0x05;
        assertThrows(Exception.class, () -> CMRTag.parseTags(cmrDataRI));

        // [CMOCA-5-270, 271] Num Named Colorants tag
        byte[] cmrDataNC = new byte[48];
        cmrDataNC[0] = 0x50;
        cmrDataNC[1] = 0x15; // Tag X'5015'
        cmrDataNC[3] = 0x01;
        cmrDataNC[7] = 0x02; // Invalid count 2 (expected 1)
        cmrDataNC[13] = (byte) 0xFF;
        cmrDataNC[12] = (byte) 0xFF;
        cmrDataNC[15] = 0x05;
        assertThrows(Exception.class, () -> CMRTag.parseTags(cmrDataNC));

        // [CMOCA-5-065] Date/Time tag invalid Count
        byte[] cmrData2 = new byte[48];
        cmrData2[1] = 0x08; // Tag X'0008'
        cmrData2[3] = 0x05; // Field Type 5
        cmrData2[7] = 0x09; // Invalid Count 9 (expected 10)
        cmrData2[13] = (byte) 0xFF;
        cmrData2[12] = (byte) 0xFF;
        cmrData2[15] = 0x05;

        assertThrows(Exception.class, () -> CMRTag.parseTags(cmrData2));

        // [CMOCA-5-088] Num Components tag invalid Count
        byte[] cmrData3 = new byte[48];
        cmrData3[1] = 0x11; // Tag X'0011'
        cmrData3[3] = 0x01; // Field Type 1
        cmrData3[7] = 0x02; // Invalid Count 2 (expected 1)
        cmrData3[13] = (byte) 0xFF;
        cmrData3[12] = (byte) 0xFF;
        cmrData3[15] = 0x05;

        assertThrows(Exception.class, () -> CMRTag.parseTags(cmrData3));

        // [CMOCA-5-091] Num Components invalid Value
        byte[] cmrData3v = new byte[48];
        cmrData3v[1] = 0x11; // Tag X'0011'
        cmrData3v[3] = 0x01; // Field Type 1
        cmrData3v[7] = 0x01; // Count 1
        cmrData3v[11] = 0x10; // Value 16 (invalid, expected 1-15)
        cmrData3v[13] = (byte) 0xFF;
        cmrData3v[12] = (byte) 0xFF;
        cmrData3v[15] = 0x05;

        Exception e3 = assertThrows(Exception.class, () -> CMRTag.parseTags(cmrData3v));
        assertTrue(e3.getMessage().contains("EC-001110"), "Expected EC-001110, got: " + e3.getMessage());

        // [CMOCA-5-100] Halftone Subset invalid Value
        byte[] cmrData4 = new byte[48];
        cmrData4[0] = 0x10;
        cmrData4[1] = 0x11; // Tag X'1011'
        cmrData4[3] = 0x08; // Field Type 8
        cmrData4[7] = 0x01; // Count 1
        cmrData4[11] = 0x05; // Invalid Value 5 (expected 1-4)
        cmrData4[13] = (byte) 0xFF;
        cmrData4[12] = (byte) 0xFF;
        cmrData4[15] = 0x05;

        assertThrows(Exception.class, () -> CMRTag.parseTags(cmrData4));

        // TTC Subset [CMOCA-5-171/172]
        byte[] cmrData5 = new byte[48];
        cmrData5[0] = 0x20;
        cmrData5[1] = 0x04; // Tag X'2004'
        cmrData5[3] = 0x08;
        cmrData5[7] = 0x01;
        cmrData5[11] = 0x03; // Invalid Value 3 (expected 1-2)
        cmrData5[13] = (byte) 0xFF;
        cmrData5[12] = (byte) 0xFF;
        cmrData5[15] = 0x05;

        Exception e5 = assertThrows(Exception.class, () -> CMRTag.parseTags(cmrData5));
        assertTrue(e5.getMessage().contains("EC-200410"));

        // ICC Profile Subset [CMOCA-5-187..196]
        byte[] cmrData6 = new byte[48];
        cmrData6[0] = 0x30;
        cmrData6[1] = 0x11; // Tag X'3011'
        cmrData6[3] = 0x08;
        cmrData6[7] = 0x01;
        cmrData6[11] = 0x0B; // Invalid Value 11 (expected 1-10)
        cmrData6[13] = (byte) 0xFF;
        cmrData6[12] = (byte) 0xFF;
        cmrData6[15] = 0x05;

        Exception e6 = assertThrows(Exception.class, () -> CMRTag.parseTags(cmrData6));
        assertTrue(e6.getMessage().contains("EC-301110"));

        // Link Subset [CMOCA-5-224..226]
        byte[] cmrData7 = new byte[48];
        cmrData7[0] = 0x40;
        cmrData7[1] = 0x11; // Tag X'4011'
        cmrData7[3] = 0x08;
        cmrData7[7] = 0x01;
        cmrData7[11] = 0x04; // Invalid Value 4 (expected 1-3)
        cmrData7[13] = (byte) 0xFF;
        cmrData7[12] = (byte) 0xFF;
        cmrData7[15] = 0x05;

        Exception e7 = assertThrows(Exception.class, () -> CMRTag.parseTags(cmrData7));
        assertTrue(e7.getMessage().contains("EC-401110"));

        // Indexed Subset [CMOCA-5-268]
        byte[] cmrData8 = new byte[48];
        cmrData8[0] = 0x50;
        cmrData8[1] = 0x11; // Tag X'5011'
        cmrData8[3] = 0x08;
        cmrData8[7] = 0x01;
        cmrData8[11] = 0x02; // Invalid Value 2 (expected 1)
        cmrData8[13] = (byte) 0xFF;
        cmrData8[12] = (byte) 0xFF;
        cmrData8[15] = 0x05;

        Exception e8 = assertThrows(Exception.class, () -> CMRTag.parseTags(cmrData8));
        assertTrue(e8.getMessage().contains("EC-501110"));

        // Location of Current Pixel [CMOCA-5-135]
        byte[] cmrDataLP = new byte[48];
        cmrDataLP[0] = 0x10;
        cmrDataLP[1] = 0x60; // Tag X'1060'
        cmrDataLP[3] = 0x05; // Invalid Field Type (expected 0x01)
        cmrDataLP[13] = (byte) 0xFF;
        cmrDataLP[12] = (byte) 0xFF;
        cmrDataLP[15] = 0x05;
        assertThrows(Exception.class, () -> CMRTag.parseTags(cmrDataLP));

        // Threshold Value [CMOCA-5-152]
        byte[] cmrDataTV = new byte[48];
        cmrDataTV[0] = 0x10;
        cmrDataTV[1] = 0x75; // Tag X'1075'
        cmrDataTV[3] = 0x08; // Invalid Field Type (expected 0x01, 0x02, or 0x04)
        cmrDataTV[13] = (byte) 0xFF;
        cmrDataTV[12] = (byte) 0xFF;
        cmrDataTV[15] = 0x05;
        assertThrows(Exception.class, () -> CMRTag.parseTags(cmrDataTV));
    }

    @Test
    public void testTag1060WithNumComponents() throws Exception {
        // [CMOCA-5-135..137] X'1060' Count must be 2 * numComponents
        byte[] cmrData = new byte[48];
        // Tag 1: Number of Components = 3
        cmrData[0] = 0x00;
        cmrData[1] = 0x11;
        cmrData[3] = 0x01;
        cmrData[7] = 0x01;
        cmrData[8] = 0x03; // Value 3, left-aligned in ValueOffset

        // Tag 2: Location of Current Pixel
        cmrData[12] = 0x10;
        cmrData[13] = 0x60;
        cmrData[15] = 0x01;
        cmrData[19] = 0x06; // Count = 2 * 3 = 6 (Correct)

        cmrData[24] = (byte) 0xFF;
        cmrData[25] = (byte) 0xFF;
        cmrData[27] = 0x05;

        CMRTag.parseTags(cmrData);

        // Invalid Count
        cmrData[19] = 0x05; // Count = 5 (Incorrect)
        Exception e = assertThrows(Exception.class, () -> CMRTag.parseTags(cmrData));
        assertTrue(e.getMessage().contains("EC-106005"));
    }

    @Test
    public void testTag1075WithNumComponents() throws Exception {
        // [CMOCA-5-152..154] X'1075' Count must be numComponents
        byte[] cmrData = new byte[48];
        // Tag 1: Number of Components = 4
        cmrData[0] = 0x00;
        cmrData[1] = 0x11;
        cmrData[3] = 0x01;
        cmrData[7] = 0x01;
        cmrData[8] = 0x04; // Value 4, left-aligned in ValueOffset

        // Tag 2: Threshold Value
        cmrData[12] = 0x10;
        cmrData[13] = 0x75;
        cmrData[15] = 0x01;
        cmrData[19] = 0x04; // Count = 4 (Correct)

        cmrData[24] = (byte) 0xFF;
        cmrData[25] = (byte) 0xFF;
        cmrData[27] = 0x05;

        CMRTag.parseTags(cmrData);

        // Invalid Count
        cmrData[19] = 0x03; // Count = 3 (Incorrect)
        Exception e = assertThrows(Exception.class, () -> CMRTag.parseTags(cmrData));
        assertTrue(e.getMessage().contains("EC-107505"));
    }

    @Test
    public void testTag1045Validation() throws Exception {
        // [CMOCA-5-122] Bilevel Point-Operation Screen Data
        byte[] cmrData = new byte[64];
        // NumComponents = 1 (default)
        // Array Width = 2
        cmrData[0] = 0x10;
        cmrData[1] = 0x21;
        cmrData[3] = 0x01;
        cmrData[7] = 0x01;
        cmrData[8] = 0x02;

        // Array Height = 3
        cmrData[12] = 0x10;
        cmrData[13] = 0x25;
        cmrData[15] = 0x01;
        cmrData[19] = 0x01;
        cmrData[20] = 0x03;

        // Bilevel Screen Data. Count should be 2 * 3 = 6
        cmrData[24] = 0x10;
        cmrData[25] = 0x45;
        cmrData[27] = 0x01;
        cmrData[31] = 0x06;
        cmrData[35] = 48; // offset

        cmrData[36] = (byte) 0xFF;
        cmrData[37] = (byte) 0xFF;
        cmrData[39] = 0x05;

        CMRTag.parseTags(cmrData);

        // Invalid Field Type
        cmrData[27] = 0x08;
        assertThrows(Exception.class, () -> CMRTag.parseTags(cmrData));

        // Invalid Count
        cmrData[27] = 0x01;
        cmrData[31] = 0x05;
        Exception e = assertThrows(Exception.class, () -> CMRTag.parseTags(cmrData));
        assertTrue(e.getMessage().contains("EC-104505"));
    }

    @Test
    public void testTag1050Validation() throws Exception {
        // [CMOCA-5-126] Multilevel Point-Operation Screen Data
        byte[] cmrData = new byte[60];
        // NumComponents = 1 (default)
        // Array Width = 2
        cmrData[0] = 0x10;
        cmrData[1] = 0x21;
        cmrData[3] = 0x01;
        cmrData[7] = 0x01;
        cmrData[8] = 0x02;

        // Array Height = 2
        cmrData[12] = 0x10;
        cmrData[13] = 0x25;
        cmrData[15] = 0x01;
        cmrData[19] = 0x01;
        cmrData[20] = 0x02;

        // Max Image Value = 255
        cmrData[24] = 0x10;
        cmrData[25] = 0x30;
        cmrData[27] = 0x01;
        cmrData[31] = 0x01;
        cmrData[32] = (byte) 0xFF;

        // Multilevel Screen Data. Count should be 2 * 2 * (255 + 1) = 1024
        cmrData[36] = 0x10;
        cmrData[37] = 0x50;
        cmrData[39] = 0x01;
        cmrData[43] = 0x00; // Count 1024 (0x0400)
        cmrData[42] = 0x04;
        cmrData[47] = 0x44; // ValueOffset (offset to data 68)

        cmrData[48] = (byte) 0xFF;
        cmrData[49] = (byte) 0xFF;
        cmrData[51] = 0x05;

        // We need more space for data
        byte[] longCmrData = new byte[1024 + 68];
        System.arraycopy(cmrData, 0, longCmrData, 0, 52);
        // longCmrData[47] = 68; // Data at offset 68

        CMRTag.parseTags(longCmrData);

        // Invalid Count
        longCmrData[43] = 0x01; // Change count
        assertThrows(Exception.class, () -> CMRTag.parseTags(longCmrData));
    }

    @Test
    public void testTag1080Validation() throws Exception {
        // [CMOCA-5-156] Quantization Boundary Table
        byte[] cmrData = new byte[36];
        // NumComponents = 2
        cmrData[0] = 0x00;
        cmrData[1] = 0x11;
        cmrData[3] = 0x01;
        cmrData[7] = 0x01;
        cmrData[8] = 0x02;

        // Num Device Levels = {4, 8}
        cmrData[12] = 0x10;
        cmrData[13] = 0x35;
        cmrData[15] = 0x01;
        cmrData[19] = 0x02;
        cmrData[20] = 0x04;
        cmrData[21] = 0x08;

        // Quantization Boundary Table. Count should be (4-1) + (8-1) = 10
        cmrData[24] = 0x10;
        cmrData[25] = (byte) 0x80;
        cmrData[27] = 0x01;
        cmrData[31] = 0x0A;
        cmrData[35] = 40; // data offset

        // Need more space
        byte[] longCmrData = new byte[64];
        System.arraycopy(cmrData, 0, longCmrData, 0, 36);
        longCmrData[36] = (byte) 0xFF;
        longCmrData[37] = (byte) 0xFF;
        longCmrData[39] = 0x05;

        CMRTag.parseTags(longCmrData);

        // Invalid Count
        longCmrData[31] = 0x0B;
        assertThrows(Exception.class, () -> CMRTag.parseTags(longCmrData));
    }

    @Test
    public void testLinkNameValidation() throws Exception {
        // [CMOCA-5-233, 236, 262]
        byte[] cmrData = new byte[36];
        cmrData[0] = 0x40;
        cmrData[1] = 0x25; // Tag X'4025'
        cmrData[3] = 0x06; // Invalid Field Type (ASCII, expected UTF-16)

        cmrData[12] = (byte) 0xFF;
        cmrData[13] = (byte) 0xFF;
        cmrData[15] = 0x05;
        assertThrows(Exception.class, () -> CMRTag.parseTags(cmrData));

        cmrData[1] = 0x30; // Tag X'4030'
        assertThrows(Exception.class, () -> CMRTag.parseTags(cmrData));

        cmrData[1] = (byte) 0x90; // Tag X'4090'
        assertThrows(Exception.class, () -> CMRTag.parseTags(cmrData));

        cmrData[3] = 0x07; // Valid
        CMRTag.parseTags(cmrData);
    }

    @Test
    public void testLinkOIDValidation() throws Exception {
        // [CMOCA-5-227, 230] X'4015', X'4020'
        byte[] cmrData = new byte[36];
        cmrData[0] = 0x40;
        cmrData[1] = 0x15; // Tag X'4015'
        cmrData[3] = 0x01; // Invalid Field Type (expected 0x05)

        cmrData[12] = (byte) 0xFF;
        cmrData[13] = (byte) 0xFF;
        cmrData[15] = 0x05;
        assertThrows(Exception.class, () -> CMRTag.parseTags(cmrData));

        cmrData[1] = 0x20; // Tag X'4020'
        assertThrows(Exception.class, () -> CMRTag.parseTags(cmrData));
    }

    @Test
    public void testLinkLUTValidation() throws Exception {
        // [CMOCA-5-246, 256, 258, 260] X'4040', X'4045', X'4050', X'4055'
        byte[] cmrData = new byte[36];
        cmrData[0] = 0x40;
        cmrData[1] = 0x40; // Tag X'4040'
        cmrData[3] = 0x01; // Invalid Field Type (expected 0x05)

        cmrData[12] = (byte) 0xFF;
        cmrData[13] = (byte) 0xFF;
        cmrData[15] = 0x05;
        assertThrows(Exception.class, () -> CMRTag.parseTags(cmrData));

        cmrData[3] = 0x05;
        cmrData[7] = 0x02; // Count 2
        cmrData[8] = 0x00; // Invalid input components (0)
        cmrData[9] = 0x03;
        Exception e1 = assertThrows(Exception.class, () -> CMRTag.parseTags(cmrData));
        assertTrue(e1.getMessage().contains("EC-404010"));

        cmrData[8] = 0x03;
        cmrData[9] = 16; // Invalid output components (16)
        Exception e2 = assertThrows(Exception.class, () -> CMRTag.parseTags(cmrData));
        assertTrue(e2.getMessage().contains("EC-404010"));

        cmrData[9] = 0x04; // Valid
        CMRTag.parseTags(cmrData);
    }

    @Test
    public void testAllFieldTypes() throws Exception {
        // [CMOCA-5-005] X'01' 1-byte UBIN
        // [CMOCA-5-006] X'02' 2-byte UBIN
        // [CMOCA-5-007] X'04' 4-byte UBIN
        // [CMOCA-5-008] X'05' BYTE
        // [CMOCA-5-009] X'06' ASCII
        // [CMOCA-5-010] X'07' UTF16
        // [CMOCA-5-011] X'08' CODE
        // [CMOCA-5-012] X'09' BITS

        int[] types = {0x01, 0x02, 0x04, 0x05, 0x06, 0x07, 0x08, 0x09};
        int[] sizes = {1, 2, 4, 1, 1, 2, 1, 1};

        // Use tag IDs starting from 0x6000 to avoid special validation rules for architected tags
        byte[] cmrData = new byte[types.length * 12 + 24];
        for (int i = 0; i < types.length; i++) {
            int offset = i * 12;
            int tagId = 0x6000 + i;
            System.arraycopy(UtilBinaryDecoding.intToByteArray(tagId, 2), 0, cmrData, offset, 2);
            cmrData[offset + 3] = (byte) types[i];
            System.arraycopy(UtilBinaryDecoding.longToByteArray(1, 4), 0, cmrData, offset + 4, 4);
            // Just use inline data for simplicity where it fits
        }
        // End Data
        int endOffset = types.length * 12;
        cmrData[endOffset] = (byte) 0xFF;
        cmrData[endOffset + 1] = (byte) 0xFF;
        cmrData[endOffset + 3] = 0x05;

        List<CMRTag> tags = CMRTag.parseTags(cmrData);
        assertEquals(types.length + 1, tags.size());

        for (int i = 0; i < types.length; i++) {
            assertEquals(types[i], tags.get(i).getFieldType());
            assertEquals(sizes[i], tags.get(i).getData().length);
        }
    }

    @Test
    public void testTag2015Validation() throws Exception {
        // [CMOCA-5-178] Tone Transfer Curve Data
        byte[] cmrData = new byte[64];
        // NumComponents = 1 (default)
        // TTC Length = 0x01 (256 entries)
        cmrData[0] = 0x20;
        cmrData[1] = 0x11;
        cmrData[3] = 0x08;
        cmrData[7] = 0x01;
        cmrData[8] = 0x01;

        // TTC Data. Count should be 256
        cmrData[12] = 0x20;
        cmrData[13] = 0x15;
        cmrData[15] = 0x05;
        cmrData[18] = 0x01; // Count 256 (0x00000100)
        cmrData[19] = 0x00;
        cmrData[23] = 36; // offset

        cmrData[24] = (byte) 0xFF;
        cmrData[25] = (byte) 0xFF;
        cmrData[27] = 0x05;

        byte[] longData = new byte[256 + 36];
        System.arraycopy(cmrData, 0, longData, 0, 28);

        CMRTag.parseTags(longData);

        // Invalid Count
        longData[19] = 0x01; // 257
        assertThrows(Exception.class, () -> CMRTag.parseTags(longData));
    }

    @Test
    public void testTag3015Validation() throws Exception {
        // [CMOCA-5-197] ICC Profile Data
        byte[] cmrData = new byte[48];
        cmrData[0] = 0x30;
        cmrData[1] = 0x15;
        cmrData[3] = 0x05;
        cmrData[7] = 0x08; // Count 8
        cmrData[11] = 24; // offset

        cmrData[12] = (byte) 0xFF;
        cmrData[13] = (byte) 0xFF;
        cmrData[15] = 0x05;

        // Profile header (first 4 bytes is size)
        cmrData[24] = 0x00;
        cmrData[25] = 0x00;
        cmrData[26] = 0x00;
        cmrData[27] = 0x08; // Size 8

        CMRTag.parseTags(cmrData);

        // Size mismatch
        cmrData[27] = 0x09;
        assertThrows(Exception.class, () -> CMRTag.parseTags(cmrData));
    }

    @Test
    public void testPaletteTagsValidation() throws Exception {
        // X'5020' (9), X'5025' (12), X'5030' (11), X'5035' (8)
        int[] tagIds = {0x5020, 0x5025, 0x5030, 0x5035};
        int[] entrySizes = {9, 12, 11, 8};

        for (int i = 0; i < tagIds.length; i++) {
            byte[] cmrData = new byte[48];
            cmrData[0] = (byte) (tagIds[i] >> 8);
            cmrData[1] = (byte) (tagIds[i] & 0xFF);
            cmrData[3] = 0x05;
            cmrData[7] = (byte) entrySizes[i]; // Correct count (1 entry)

            cmrData[12] = (byte) 0xFF;
            cmrData[13] = (byte) 0xFF;
            cmrData[15] = 0x05;

            CMRTag.parseTags(cmrData);

            // Invalid count (not a multiple)
            cmrData[7] = (byte) (entrySizes[i] - 1);
            assertThrows(Exception.class, () -> CMRTag.parseTags(cmrData));
        }
    }

    @Test
    public void testNamedColorantValidation() throws Exception {
        // [CMOCA-5-300] Color Palette Named Colorants (8 + n)
        // [CMOCA-5-306] Colorant Identification List
        byte[] cmrData = new byte[64];
        // Num Named Colorants = 2
        cmrData[0] = 0x50;
        cmrData[1] = 0x15;
        cmrData[3] = 0x01;
        cmrData[7] = 0x01;
        cmrData[8] = 0x02;

        // Palette Named Colorants. Count (8 + 2) * 1 = 10
        cmrData[12] = 0x50;
        cmrData[13] = 0x40;
        cmrData[15] = 0x05;
        cmrData[19] = 0x0A;

        // Identification List. 2 entries.
        // Entry 1: len 4, Entry 2: len 6. Total 10.
        cmrData[24] = 0x50;
        cmrData[25] = 0x45;
        cmrData[27] = 0x05;
        cmrData[31] = 0x0A;
        cmrData[35] = 48; // offset

        cmrData[36] = (byte) 0xFF;
        cmrData[37] = (byte) 0xFF;
        cmrData[39] = 0x05;

        byte[] longData = new byte[64];
        System.arraycopy(cmrData, 0, longData, 0, 40);
        longData[48] = 0x04; // Entry 1 length
        longData[52] = 0x06; // Entry 2 length

        CMRTag.parseTags(longData);

        // Palette Count mismatch
        longData[19] = 0x09;
        assertThrows(Exception.class, () -> CMRTag.parseTags(longData));
        longData[19] = 0x0A;

        // Identification List - wrong number of entries
        longData[52] = 0x0A; // One entry of length 10 instead of two
        assertThrows(Exception.class, () -> CMRTag.parseTags(longData));
    }

    @Test
    public void testTag0008ValueValidation() throws Exception {
        // [CMOCA-5-046..054] valid date/time values
        byte[] cmrData = new byte[36];
        cmrData[1] = 0x08; // Tag 0x0008
        cmrData[3] = 0x05; // Field Type 5
        cmrData[7] = 10; // Count 10
        cmrData[11] = 24; // offset

        cmrData[12] = (byte) 0xFF;
        cmrData[13] = (byte) 0xFF;
        cmrData[15] = 0x05;

        // Valid data
        cmrData[24] = 0x07; cmrData[25] = (byte) 0xD0; // 2000
        cmrData[26] = 0x01; // Jan
        cmrData[27] = 0x01; // 1st
        cmrData[28] = 0x00; // 0h
        cmrData[29] = 0x00; // 0m
        cmrData[30] = 0x00; // 0s
        cmrData[31] = 0x00; // UTC
        cmrData[32] = 0x00;
        cmrData[33] = 0x00;

        CMRTag.parseTags(cmrData);

        // Invalid Month
        cmrData[26] = 13;
        Exception e = assertThrows(Exception.class, () -> CMRTag.parseTags(cmrData));
        assertTrue(e.getMessage().contains("EC-000810"));
        cmrData[26] = 1;

        // Invalid Day
        cmrData[27] = 32;
        e = assertThrows(Exception.class, () -> CMRTag.parseTags(cmrData));
        assertTrue(e.getMessage().contains("EC-000810"));
        cmrData[27] = 1;

        // Invalid Hour
        cmrData[28] = 24;
        e = assertThrows(Exception.class, () -> CMRTag.parseTags(cmrData));
        assertTrue(e.getMessage().contains("EC-000810"));
        cmrData[28] = 0;

        // Invalid Minute
        cmrData[29] = 60;
        e = assertThrows(Exception.class, () -> CMRTag.parseTags(cmrData));
        assertTrue(e.getMessage().contains("EC-000810"));
        cmrData[29] = 0;

        // Invalid Second
        cmrData[30] = 60;
        e = assertThrows(Exception.class, () -> CMRTag.parseTags(cmrData));
        assertTrue(e.getMessage().contains("EC-000810"));
        cmrData[30] = 0;

        // Invalid TimeZone
        cmrData[31] = 3;
        e = assertThrows(Exception.class, () -> CMRTag.parseTags(cmrData));
        assertTrue(e.getMessage().contains("EC-000810"));
        cmrData[31] = 0;

        // Invalid UTCDiffH
        cmrData[32] = 24;
        e = assertThrows(Exception.class, () -> CMRTag.parseTags(cmrData));
        assertTrue(e.getMessage().contains("EC-000810"));
        cmrData[32] = 0;

        // Invalid UTCDiffM
        cmrData[33] = 60;
        e = assertThrows(Exception.class, () -> CMRTag.parseTags(cmrData));
        assertTrue(e.getMessage().contains("EC-000810"));
    }

    @Test
    public void testTagFFFFValidation() throws Exception {
        // [CMOCA-5-311] End Data
        byte[] cmrData = new byte[24];
        cmrData[0] = (byte) 0xFF;
        cmrData[1] = (byte) 0xFF;
        cmrData[3] = 0x05;
        cmrData[7] = 0x00; // Count 0 (Valid)

        CMRTag.parseTags(cmrData);

        // Invalid Count
        cmrData[7] = 0x01;
        assertThrows(Exception.class, () -> CMRTag.parseTags(cmrData));
    }
}
