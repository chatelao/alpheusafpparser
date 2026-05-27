package com.mgz.afp.cmoca;

import com.mgz.afp.parser.AFPParserConfiguration;
import com.mgz.util.Constants;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class CMRPropertyVerificationTest {

    private byte[] createCmrHeaderShell() {
        byte[] data = new byte[173]; // 9 (introducer) + 164 (header)
        data[0] = 0x5A;
        data[1] = 0x00;
        data[2] = (byte) 172;
        data[3] = (byte) 0xD3;
        data[4] = (byte) 0xA0;
        data[5] = (byte) 0xA2;

        data[12] = (byte) 0xA4;
        data[13] = (byte) 0xC3;
        data[14] = (byte) 0xD4;
        data[15] = (byte) 0xD9;
        data[16] = (byte) 0xF9;

        for (int i = 19; i < 165; i += 2) {
            data[i] = 0x00;
            data[i+1] = 0x40;
        }
        return data;
    }

    @Test
    public void testHalftoneProperties() throws Exception {
        byte[] data = createCmrHeaderShell();
        // Set type to HT
        byte[] typeBytes = "HT".getBytes(Constants.utf16be);
        System.arraycopy(typeBytes, 0, data, 35, typeBytes.length);

        // [CMOCA-3-129] Prop1: Number of Device Levels (offset 94 in payload, 103 in SF)
        String prop1 = "2";
        byte[] p1Bytes = prop1.getBytes(Constants.utf16be);
        System.arraycopy(p1Bytes, 0, data, 103, p1Bytes.length);

        // [CMOCA-3-130] Prop2: Halftone Type (offset 104 in payload, 113 in SF)
        String prop2 = "rnd";
        byte[] p2Bytes = prop2.getBytes(Constants.utf16be);
        System.arraycopy(p2Bytes, 0, data, 113, p2Bytes.length);

        CMR_ColorManagementResource cmr = new CMR_ColorManagementResource();
        cmr.decodeAFP(data, 9, 164, new AFPParserConfiguration());
        assertEquals(prop1, cmr.getProp1());
        assertEquals(prop2, cmr.getProp2());
    }

    @Test
    public void testTtcProperties() throws Exception {
        byte[] data = createCmrHeaderShell();
        // Set type to TC
        byte[] typeBytes = "TC".getBytes(Constants.utf16be);
        System.arraycopy(typeBytes, 0, data, 35, typeBytes.length);

        // [CMOCA-3-158] Prop1: Profile/Device Class Signature (offset 94 in payload, 103 in SF)
        String prop1 = "prtr";
        byte[] p1Bytes = prop1.getBytes(Constants.utf16be);
        System.arraycopy(p1Bytes, 0, data, 103, p1Bytes.length);

        CMR_ColorManagementResource cmr = new CMR_ColorManagementResource();
        cmr.decodeAFP(data, 9, 164, new AFPParserConfiguration());
        assertEquals(prop1, cmr.getProp1());
    }

    @Test
    public void testCcProperties() throws Exception {
        byte[] data = createCmrHeaderShell();
        // Set type to CC
        byte[] typeBytes = "CC".getBytes(Constants.utf16be);
        System.arraycopy(typeBytes, 0, data, 35, typeBytes.length);

        // [CMOCA-3-176] Prop1: Profile/Device Class Signature (offset 94 in payload, 103 in SF)
        String prop1 = "scnr";
        byte[] p1Bytes = prop1.getBytes(Constants.utf16be);
        System.arraycopy(p1Bytes, 0, data, 103, p1Bytes.length);

        // [CMOCA-3-185] Prop4: Color Space of Data (offset 124 in payload, 133 in SF)
        String prop4 = "RGB";
        byte[] p4Bytes = prop4.getBytes(Constants.utf16be);
        System.arraycopy(p4Bytes, 0, data, 133, p4Bytes.length);

        CMR_ColorManagementResource cmr = new CMR_ColorManagementResource();
        cmr.decodeAFP(data, 9, 164, new AFPParserConfiguration());
        assertEquals(prop1, cmr.getProp1());
        assertEquals(prop4, cmr.getProp4());
    }
}
