package com.mgz.afp.cmoca;

import com.mgz.util.UtilBinaryDecoding;
import org.junit.jupiter.api.Test;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

public class CMRTagVerificationTest {

    @Test
    public void testTagOrdering() throws Exception {
        // [CMOCA-5-018] The tags in a CMR must be specified in increasing order by their TagIDs.
        byte[] cmrData = new byte[36];

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

        Exception e = assertThrows(Exception.class, () -> {
            CMRTag.parseTags(cmrData);
        });
        assertTrue(e.getMessage().contains("EC-00040F"), "Expected EC-00040F, got: " + e.getMessage());
    }

    @Test
    public void testTag1021Validation() throws Exception {
        // [CMOCA-5-101] TagID: X'1021' Array Width
        // [CMOCA-5-102] Field Type: X'01' or X'02'
        byte[] cmrData = new byte[24];
        cmrData[0] = 0x10;
        cmrData[1] = 0x21;
        cmrData[3] = 0x05; // Invalid Field Type (BYTE)
        cmrData[7] = 0x01; // Count 1 (Default numComponents is 1)
        cmrData[12] = (byte) 0xFF;
        cmrData[13] = (byte) 0xFF;

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
        byte[] cmrData = new byte[24];
        cmrData[0] = 0x10;
        cmrData[1] = 0x25;
        cmrData[3] = 0x04; // Invalid Field Type (4-byte UBIN)
        cmrData[7] = 0x01; // Count 1 (Default numComponents is 1)
        cmrData[12] = (byte) 0xFF;
        cmrData[13] = (byte) 0xFF;

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
        byte[] cmrData = new byte[24];
        cmrData[0] = 0x10;
        cmrData[1] = 0x65;
        cmrData[3] = 0x01; // Invalid Field Type (UBIN)
        cmrData[7] = 0x01; // Count 1 (Default numComponents is 1)
        cmrData[12] = (byte) 0xFF;
        cmrData[13] = (byte) 0xFF;

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
        byte[] cmrData = new byte[24];

        // Tag 1: ID 0x0004, Reserved = 0x01
        cmrData[0] = 0x00;
        cmrData[1] = 0x04;
        cmrData[2] = 0x01; // Invalid reserved byte
        cmrData[3] = 0x06;
        cmrData[7] = 0x04;

        // Tag 2: End Data
        cmrData[12] = (byte) 0xFF;
        cmrData[13] = (byte) 0xFF;

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

        byte[] cmrData = new byte[24];
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

        Exception e = assertThrows(Exception.class, () -> CMRTag.parseTags(cmrData));
        assertTrue(e.getMessage().contains("EC-000410"), "Expected EC-000410, got: " + e.getMessage());
    }

    @Test
    public void testTagStructuralValidation() throws Exception {
        // [CMOCA-5-039] Comment tag invalid Field Type
        byte[] cmrData = new byte[24];
        cmrData[1] = 0x04; // Tag X'0004'
        cmrData[3] = 0x01; // Invalid field type 1 (expected 6 or 7)
        cmrData[13] = (byte) 0xFF; // End Data
        cmrData[12] = (byte) 0xFF;

        assertThrows(Exception.class, () -> CMRTag.parseTags(cmrData));

        // [CMOCA-5-110] Max Image Value tag invalid Field Type
        byte[] cmrDataMV = new byte[24];
        cmrDataMV[0] = 0x10;
        cmrDataMV[1] = 0x30; // Tag X'1030'
        cmrDataMV[3] = 0x08; // Invalid field type 8 (expected 1, 2, or 4)
        cmrDataMV[13] = (byte) 0xFF;
        cmrDataMV[12] = (byte) 0xFF;
        assertThrows(Exception.class, () -> CMRTag.parseTags(cmrDataMV));

        // [CMOCA-5-114] Num Device Levels tag invalid Field Type
        byte[] cmrDataDL = new byte[24];
        cmrDataDL[0] = 0x10;
        cmrDataDL[1] = 0x35; // Tag X'1035'
        cmrDataDL[3] = 0x02; // Invalid field type 2 (expected 1)
        cmrDataDL[13] = (byte) 0xFF;
        cmrDataDL[12] = (byte) 0xFF;
        assertThrows(Exception.class, () -> CMRTag.parseTags(cmrDataDL));

        // [CMOCA-5-118] Offset Tiling tag invalid Field Type
        byte[] cmrDataOT = new byte[24];
        cmrDataOT[0] = 0x10;
        cmrDataOT[1] = 0x40; // Tag X'1040'
        cmrDataOT[3] = 0x04; // Invalid field type 4 (expected 1 or 2)
        cmrDataOT[13] = (byte) 0xFF;
        cmrDataOT[12] = (byte) 0xFF;
        assertThrows(Exception.class, () -> CMRTag.parseTags(cmrDataOT));

        // [CMOCA-5-146, 150] Boundary Condition tag
        byte[] cmrDataBC = new byte[24];
        cmrDataBC[0] = 0x10;
        cmrDataBC[1] = 0x70; // Tag X'1070'
        cmrDataBC[3] = 0x01; // Invalid field type (expected 8)
        cmrDataBC[13] = (byte) 0xFF;
        cmrDataBC[12] = (byte) 0xFF;
        assertThrows(Exception.class, () -> CMRTag.parseTags(cmrDataBC));

        cmrDataBC[3] = 0x08; // Valid type
        cmrDataBC[7] = 0x01; // Count 1
        cmrDataBC[11] = 0x05; // Invalid value 5 (expected 1-4)
        assertThrows(Exception.class, () -> CMRTag.parseTags(cmrDataBC));

        // [CMOCA-5-174, 176] TTC Length tag
        byte[] cmrDataTL = new byte[24];
        cmrDataTL[0] = 0x20;
        cmrDataTL[1] = 0x11; // Tag X'2011'
        cmrDataTL[3] = 0x08;
        cmrDataTL[7] = 0x01;
        cmrDataTL[11] = 0x03; // Invalid value 3 (expected 1-2)
        assertThrows(Exception.class, () -> CMRTag.parseTags(cmrDataTL));

        // [CMOCA-5-219] ICC Profile Filename tag
        byte[] cmrDataFN = new byte[24];
        cmrDataFN[0] = 0x30;
        cmrDataFN[1] = 0x25; // Tag X'3025'
        cmrDataFN[3] = 0x08; // Invalid field type 8 (expected 6 or 7)
        cmrDataFN[13] = (byte) 0xFF;
        cmrDataFN[12] = (byte) 0xFF;
        assertThrows(Exception.class, () -> CMRTag.parseTags(cmrDataFN));

        // [CMOCA-5-240, 241] Default Rendering Intent tag
        byte[] cmrDataRI = new byte[24];
        cmrDataRI[0] = 0x40;
        cmrDataRI[1] = 0x35; // Tag X'4035'
        cmrDataRI[3] = 0x08;
        cmrDataRI[7] = 0x01;
        cmrDataRI[8] = 0x04; // Invalid value 4 (expected 0-3), left-aligned inline
        cmrDataRI[13] = (byte) 0xFF;
        cmrDataRI[12] = (byte) 0xFF;
        assertThrows(Exception.class, () -> CMRTag.parseTags(cmrDataRI));

        // [CMOCA-5-270, 271] Num Named Colorants tag
        byte[] cmrDataNC = new byte[24];
        cmrDataNC[0] = 0x50;
        cmrDataNC[1] = 0x15; // Tag X'5015'
        cmrDataNC[3] = 0x01;
        cmrDataNC[7] = 0x02; // Invalid count 2 (expected 1)
        cmrDataNC[13] = (byte) 0xFF;
        cmrDataNC[12] = (byte) 0xFF;
        assertThrows(Exception.class, () -> CMRTag.parseTags(cmrDataNC));

        // [CMOCA-5-065] Date/Time tag invalid Count
        byte[] cmrData2 = new byte[24];
        cmrData2[1] = 0x08; // Tag X'0008'
        cmrData2[3] = 0x05; // Field Type 5
        cmrData2[7] = 0x09; // Invalid Count 9 (expected 10)
        cmrData2[13] = (byte) 0xFF;
        cmrData2[12] = (byte) 0xFF;

        assertThrows(Exception.class, () -> CMRTag.parseTags(cmrData2));

        // [CMOCA-5-088] Num Components tag invalid Count
        byte[] cmrData3 = new byte[24];
        cmrData3[1] = 0x11; // Tag X'0011'
        cmrData3[3] = 0x01; // Field Type 1
        cmrData3[7] = 0x02; // Invalid Count 2 (expected 1)
        cmrData3[13] = (byte) 0xFF;
        cmrData3[12] = (byte) 0xFF;

        assertThrows(Exception.class, () -> CMRTag.parseTags(cmrData3));

        // [CMOCA-5-091] Num Components invalid Value
        byte[] cmrData3v = new byte[24];
        cmrData3v[1] = 0x11; // Tag X'0011'
        cmrData3v[3] = 0x01; // Field Type 1
        cmrData3v[7] = 0x01; // Count 1
        cmrData3v[11] = 0x10; // Value 16 (invalid, expected 1-15)
        cmrData3v[13] = (byte) 0xFF;
        cmrData3v[12] = (byte) 0xFF;

        Exception e3 = assertThrows(Exception.class, () -> CMRTag.parseTags(cmrData3v));
        assertTrue(e3.getMessage().contains("EC-001110"), "Expected EC-001110, got: " + e3.getMessage());

        // [CMOCA-5-100] Halftone Subset invalid Value
        byte[] cmrData4 = new byte[24];
        cmrData4[0] = 0x10;
        cmrData4[1] = 0x11; // Tag X'1011'
        cmrData4[3] = 0x08; // Field Type 8
        cmrData4[7] = 0x01; // Count 1
        cmrData4[11] = 0x05; // Invalid Value 5 (expected 1-4)
        cmrData4[13] = (byte) 0xFF;
        cmrData4[12] = (byte) 0xFF;

        assertThrows(Exception.class, () -> CMRTag.parseTags(cmrData4));

        // TTC Subset [CMOCA-5-171/172]
        byte[] cmrData5 = new byte[24];
        cmrData5[0] = 0x20;
        cmrData5[1] = 0x04; // Tag X'2004'
        cmrData5[3] = 0x08;
        cmrData5[7] = 0x01;
        cmrData5[11] = 0x03; // Invalid Value 3 (expected 1-2)
        cmrData5[13] = (byte) 0xFF;
        cmrData5[12] = (byte) 0xFF;

        Exception e5 = assertThrows(Exception.class, () -> CMRTag.parseTags(cmrData5));
        assertTrue(e5.getMessage().contains("EC-200410"));

        // ICC Profile Subset [CMOCA-5-187..196]
        byte[] cmrData6 = new byte[24];
        cmrData6[0] = 0x30;
        cmrData6[1] = 0x11; // Tag X'3011'
        cmrData6[3] = 0x08;
        cmrData6[7] = 0x01;
        cmrData6[11] = 0x0B; // Invalid Value 11 (expected 1-10)
        cmrData6[13] = (byte) 0xFF;
        cmrData6[12] = (byte) 0xFF;

        Exception e6 = assertThrows(Exception.class, () -> CMRTag.parseTags(cmrData6));
        assertTrue(e6.getMessage().contains("EC-301110"));

        // Link Subset [CMOCA-5-224..226]
        byte[] cmrData7 = new byte[24];
        cmrData7[0] = 0x40;
        cmrData7[1] = 0x11; // Tag X'4011'
        cmrData7[3] = 0x08;
        cmrData7[7] = 0x01;
        cmrData7[11] = 0x04; // Invalid Value 4 (expected 1-3)
        cmrData7[13] = (byte) 0xFF;
        cmrData7[12] = (byte) 0xFF;

        Exception e7 = assertThrows(Exception.class, () -> CMRTag.parseTags(cmrData7));
        assertTrue(e7.getMessage().contains("EC-401110"));

        // Indexed Subset [CMOCA-5-268]
        byte[] cmrData8 = new byte[24];
        cmrData8[0] = 0x50;
        cmrData8[1] = 0x11; // Tag X'5011'
        cmrData8[3] = 0x08;
        cmrData8[7] = 0x01;
        cmrData8[11] = 0x02; // Invalid Value 2 (expected 1)
        cmrData8[13] = (byte) 0xFF;
        cmrData8[12] = (byte) 0xFF;

        Exception e8 = assertThrows(Exception.class, () -> CMRTag.parseTags(cmrData8));
        assertTrue(e8.getMessage().contains("EC-501110"));

        // Location of Current Pixel [CMOCA-5-135]
        byte[] cmrDataLP = new byte[24];
        cmrDataLP[0] = 0x10;
        cmrDataLP[1] = 0x60; // Tag X'1060'
        cmrDataLP[3] = 0x05; // Invalid Field Type (expected 0x01)
        cmrDataLP[13] = (byte) 0xFF;
        cmrDataLP[12] = (byte) 0xFF;
        assertThrows(Exception.class, () -> CMRTag.parseTags(cmrDataLP));

        // Threshold Value [CMOCA-5-152]
        byte[] cmrDataTV = new byte[24];
        cmrDataTV[0] = 0x10;
        cmrDataTV[1] = 0x75; // Tag X'1075'
        cmrDataTV[3] = 0x08; // Invalid Field Type (expected 0x01, 0x02, or 0x04)
        cmrDataTV[13] = (byte) 0xFF;
        cmrDataTV[12] = (byte) 0xFF;
        assertThrows(Exception.class, () -> CMRTag.parseTags(cmrDataTV));
    }

    @Test
    public void testTag1060WithNumComponents() throws Exception {
        // [CMOCA-5-135..137] X'1060' Count must be 2 * numComponents
        byte[] cmrData = new byte[36];
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

        CMRTag.parseTags(cmrData);

        // Invalid Count
        cmrData[19] = 0x05; // Count = 5 (Incorrect)
        Exception e = assertThrows(Exception.class, () -> CMRTag.parseTags(cmrData));
        assertTrue(e.getMessage().contains("EC-106005"));
    }

    @Test
    public void testTag1075WithNumComponents() throws Exception {
        // [CMOCA-5-152..154] X'1075' Count must be numComponents
        byte[] cmrData = new byte[36];
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

        CMRTag.parseTags(cmrData);

        // Invalid Count
        cmrData[19] = 0x03; // Count = 3 (Incorrect)
        Exception e = assertThrows(Exception.class, () -> CMRTag.parseTags(cmrData));
        assertTrue(e.getMessage().contains("EC-107505"));
    }

    @Test
    public void testLinkOIDValidation() throws Exception {
        // [CMOCA-5-227, 230] X'4015', X'4020'
        byte[] cmrData = new byte[24];
        cmrData[0] = 0x40;
        cmrData[1] = 0x15; // Tag X'4015'
        cmrData[3] = 0x01; // Invalid Field Type (expected 0x05)
        cmrData[12] = (byte) 0xFF;
        cmrData[13] = (byte) 0xFF;
        assertThrows(Exception.class, () -> CMRTag.parseTags(cmrData));

        cmrData[1] = 0x20; // Tag X'4020'
        assertThrows(Exception.class, () -> CMRTag.parseTags(cmrData));
    }

    @Test
    public void testLinkLUTValidation() throws Exception {
        // [CMOCA-5-246, 256, 258, 260] X'4040', X'4045', X'4050', X'4055'
        byte[] cmrData = new byte[24];
        cmrData[0] = 0x40;
        cmrData[1] = 0x40; // Tag X'4040'
        cmrData[3] = 0x01; // Invalid Field Type (expected 0x05)
        cmrData[12] = (byte) 0xFF;
        cmrData[13] = (byte) 0xFF;
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
        byte[] cmrData = new byte[types.length * 12 + 12];
        for (int i = 0; i < types.length; i++) {
            int offset = i * 12;
            int tagId = 0x6000 + i;
            System.arraycopy(UtilBinaryDecoding.intToByteArray(tagId, 2), 0, cmrData, offset, 2);
            cmrData[offset + 3] = (byte) types[i];
            System.arraycopy(UtilBinaryDecoding.longToByteArray(1, 4), 0, cmrData, offset + 4, 4);
            // Just use inline data for simplicity where it fits
        }
        // End Data
        cmrData[types.length * 12] = (byte) 0xFF;
        cmrData[types.length * 12 + 1] = (byte) 0xFF;

        List<CMRTag> tags = CMRTag.parseTags(cmrData);
        assertEquals(types.length + 1, tags.size());

        for (int i = 0; i < types.length; i++) {
            assertEquals(types[i], tags.get(i).getFieldType());
            assertEquals(sizes[i], tags.get(i).getData().length);
        }
    }
}
