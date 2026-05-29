package com.mgz.afp.cmoca;

import com.mgz.afp.parser.AFPParserConfiguration;
import com.mgz.util.Constants;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class CMRHeaderVerificationTest {

    private byte[] createCmrHeaderShell() {
        byte[] data = new byte[173]; // 9 (introducer) + 164 (header)
        data[0] = 0x5A;
        data[1] = 0x00;
        data[2] = (byte) 172; // Length
        data[3] = (byte) 0xD3;
        data[4] = (byte) 0xA0;
        data[5] = (byte) 0xA2;

        // Header length field [CMOCA-3-045]
        data[12] = (byte) 0xA4;
        // Signature "CMR9" [CMOCA-3-046]
        data[13] = (byte) 0xC3;
        data[14] = (byte) 0xD4;
        data[15] = (byte) 0xD9;
        data[16] = (byte) 0xF9;

        // Pad name area with @ [CMOCA-3-041]
        for (int i = 19; i < 165; i += 2) {
            data[i] = 0x00;
            data[i+1] = 0x40;
        }
        return data;
    }

    @Test
    public void testCmrAlias() throws Exception {
        // [CMOCA-3-049] CMRAlias (offset 10 in payload, 19 in SF)
        byte[] data = createCmrHeaderShell();
        String alias = "MYALIAS";
        // To match .trim(), we shouldn't pad manually here if we want exact match,
        // but the decoder trims. If we want to test correct extraction:
        byte[] aliasBytes = alias.getBytes(Constants.utf16be);
        System.arraycopy(aliasBytes, 0, data, 19, aliasBytes.length);

        CMR_ColorManagementResource cmr = new CMR_ColorManagementResource();
        cmr.decodeAFP(data, 9, 164, new AFPParserConfiguration());
        assertEquals(alias, cmr.getAlias());
    }

    @Test
    public void testCmrType() throws Exception {
        // [CMOCA-3-050] CMRType (offset 26 in payload, 35 in SF)
        byte[] data = createCmrHeaderShell();
        String type = "CC";
        byte[] typeBytes = type.getBytes(Constants.utf16be);
        System.arraycopy(typeBytes, 0, data, 35, typeBytes.length);

        CMR_ColorManagementResource cmr = new CMR_ColorManagementResource();
        cmr.decodeAFP(data, 9, 164, new AFPParserConfiguration());
        assertEquals(type, cmr.getType());
    }

    @Test
    public void testCmrManufacturer() throws Exception {
        // [CMOCA-3-060] Manufacturer Name (offset 44 in payload, 53 in SF)
        byte[] data = createCmrHeaderShell();
        String man = "IBM";
        byte[] manBytes = man.getBytes(Constants.utf16be);
        System.arraycopy(manBytes, 0, data, 53, manBytes.length);

        CMR_ColorManagementResource cmr = new CMR_ColorManagementResource();
        cmr.decodeAFP(data, 9, 164, new AFPParserConfiguration());
        assertEquals(man, cmr.getManufacturerName());
    }

    @Test
    public void testInvalidSignature() {
        // [CMOCA-3-254] EC-EFF110 Invalid Field Value: The specified value for CMRSig is not X'434D5239'.
        byte[] data = createCmrHeaderShell();
        data[13] = 'B'; // Invalid signature "BMR9"

        CMR_ColorManagementResource cmr = new CMR_ColorManagementResource();
        assertThrows(Exception.class, () -> {
            cmr.decodeAFP(data, 9, 164, new AFPParserConfiguration());
        });
    }

    @Test
    public void testReservedFields() throws Exception {
        // [CMOCA-3-047] Reserved; should be set to zero
        // [CMOCA-3-101] Reserved; should be set to zero
        byte[] data = createCmrHeaderShell();
        data[18] = 0x01; // reserved1 (offset 9 in payload, but offset 8 is MSB of 2-byte int)
        data[172] = 0x01; // reserved3 (offset 163 in payload, but offset 156 is MSB of 8-byte long)

        CMR_ColorManagementResource cmr = new CMR_ColorManagementResource();
        // Should not throw, but we verify it can parse even if reserved are non-zero (robustness)
        cmr.decodeAFP(data, 9, 164, new AFPParserConfiguration());
        assertEquals(1, cmr.getReserved1());
        assertEquals(1, cmr.getReserved3());
    }
}
