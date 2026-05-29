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
}
