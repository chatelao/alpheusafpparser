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

        // Tag 2: ID 0x0004 (Out of order!)
        cmrData[12] = 0x00;
        cmrData[13] = 0x04;
        cmrData[15] = 0x06; // Field type 6
        cmrData[19] = 0x04; // Count 4

        // Tag 3: ID 0xFFFF (End Data)
        cmrData[24] = (byte) 0xFF;
        cmrData[25] = (byte) 0xFF;

        assertThrows(Exception.class, () -> {
            CMRTag.parseTags(cmrData);
        });
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
    public void testTagStructuralValidation() throws Exception {
        // [CMOCA-5-039] Comment tag invalid Field Type
        byte[] cmrData = new byte[24];
        cmrData[1] = 0x04; // Tag X'0004'
        cmrData[3] = 0x01; // Invalid field type 1 (expected 6 or 7)
        cmrData[13] = (byte) 0xFF; // End Data
        cmrData[12] = (byte) 0xFF;

        assertThrows(Exception.class, () -> CMRTag.parseTags(cmrData));

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

        // Use tag IDs starting from 0x2000 to avoid special validation rules for 0x0004, 0x0008, 0x0011, 0x1011
        byte[] cmrData = new byte[types.length * 12 + 12];
        for (int i = 0; i < types.length; i++) {
            int offset = i * 12;
            int tagId = 0x2000 + i;
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
