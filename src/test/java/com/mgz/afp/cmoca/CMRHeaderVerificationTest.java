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

        // Set default type to CC to pass validation [CMOCA-3-255]
        data[35] = 0x00;
        data[36] = 0x43;
        data[37] = 0x00;
        data[38] = 0x43;

        return data;
    }

    @Test
    public void testAllCmrTypes() throws Exception {
        // [CMOCA-3-050] to [CMOCA-3-055]
        String[] types = {"CC", "DL", "HT", "IX", "LK", "TC"};
        for (String type : types) {
            byte[] data = createCmrHeaderShell();
            byte[] typeBytes = type.getBytes(Constants.utf16be);
            System.arraycopy(typeBytes, 0, data, 35, typeBytes.length);

            CMR_ColorManagementResource cmr = new CMR_ColorManagementResource();
            cmr.decodeAFP(data, 9, 164, new AFPParserConfiguration());
            assertEquals(type, cmr.getType());
        }
    }

    @Test
    public void testCmrAlias() throws Exception {
        // [CMOCA-3-049] CMRAlias (offset 10 in payload, 19 in SF)
        byte[] data = createCmrHeaderShell();
        String alias = "MYALIAS";
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
    public void testCmrVersion() throws Exception {
        // [CMOCA-3-056] CMRVersion (offset 30 in payload, 39 in SF)
        byte[] data = createCmrHeaderShell();
        String version = "001.000";
        byte[] versionBytes = version.getBytes(Constants.utf16be);
        System.arraycopy(versionBytes, 0, data, 39, versionBytes.length);

        CMR_ColorManagementResource cmr = new CMR_ColorManagementResource();
        cmr.decodeAFP(data, 9, 164, new AFPParserConfiguration());
        assertEquals(version, cmr.getVersion());
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
    public void testCmrDevice() throws Exception {
        // [CMOCA-3-061] DeviceType (offset 54 in payload, 63 in SF) - 12 bytes (6 chars)
        // [CMOCA-3-062] DeviceModel (offset 66 in payload, 75 in SF) - 6 bytes (3 chars)
        byte[] data = createCmrHeaderShell();
        String type = "4100";
        String model = "HD3";
        byte[] typeBytes = type.getBytes(Constants.utf16be);
        byte[] modelBytes = model.getBytes(Constants.utf16be);
        System.arraycopy(typeBytes, 0, data, 63, typeBytes.length);
        System.arraycopy(modelBytes, 0, data, 75, modelBytes.length);

        CMR_ColorManagementResource cmr = new CMR_ColorManagementResource();
        cmr.decodeAFP(data, 9, 164, new AFPParserConfiguration());
        assertEquals(type, cmr.getDeviceType());
        assertEquals(model, cmr.getDeviceModel());
    }

    @Test
    public void testMediaFields() throws Exception {
        // [CMOCA-3-063] MediaBrightness (offset 72 in payload, 81 in SF) - 6 bytes
        // [CMOCA-3-065] MediaColor (offset 78 in payload, 87 in SF) - 6 bytes
        // [CMOCA-3-080] MediaFinish (offset 84 in payload, 93 in SF) - 4 bytes
        // [CMOCA-3-093] MediaWeight (offset 88 in payload, 97 in SF) - 6 bytes
        byte[] data = createCmrHeaderShell();
        String brightness = "100";
        String color = "wht";
        String finish = "gl";
        String weight = "90";

        System.arraycopy(brightness.getBytes(Constants.utf16be), 0, data, 81, brightness.length() * 2);
        System.arraycopy(color.getBytes(Constants.utf16be), 0, data, 87, color.length() * 2);
        System.arraycopy(finish.getBytes(Constants.utf16be), 0, data, 93, finish.length() * 2);
        System.arraycopy(weight.getBytes(Constants.utf16be), 0, data, 97, weight.length() * 2);

        CMR_ColorManagementResource cmr = new CMR_ColorManagementResource();
        cmr.decodeAFP(data, 9, 164, new AFPParserConfiguration());
        assertEquals(brightness, cmr.getMediaBrightness());
        assertEquals(color, cmr.getMediaColor());
        assertEquals(finish, cmr.getMediaFinish());
        assertEquals(weight, cmr.getMediaWeight());
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
    public void testInvalidCmrType() throws Exception {
        // [CMOCA-3-255] EC-EFF210 Invalid Field Value: The specified CMRType is invalid.
        byte[] data = createCmrHeaderShell();
        String type = "XX";
        byte[] typeBytes = type.getBytes(Constants.utf16be);
        System.arraycopy(typeBytes, 0, data, 35, typeBytes.length);

        CMR_ColorManagementResource cmr = new CMR_ColorManagementResource();
        assertThrows(Exception.class, () -> {
            cmr.decodeAFP(data, 9, 164, new AFPParserConfiguration());
        });
    }

    @Test
    public void testInvalidVersion() throws Exception {
        // [CMOCA-3-256] EC-EFF310 Invalid Field Value: The specified CMRVersion is invalid.
        byte[] data = createCmrHeaderShell();
        String version = "invalid";
        byte[] versionBytes = version.getBytes(Constants.utf16be);
        System.arraycopy(versionBytes, 0, data, 39, versionBytes.length);

        CMR_ColorManagementResource cmr = new CMR_ColorManagementResource();
        assertThrows(Exception.class, () -> {
            cmr.decodeAFP(data, 9, 164, new AFPParserConfiguration());
        });
    }

    @Test
    public void testInvalidMediaBrightness() throws Exception {
        // [CMOCA-3-257] EC-EFF410 Invalid Field Value: The specified MediaBrightness is invalid.
        byte[] data = createCmrHeaderShell();
        String brightness = "101"; // > 100
        byte[] bytes = brightness.getBytes(Constants.utf16be);
        System.arraycopy(bytes, 0, data, 81, 6);

        CMR_ColorManagementResource cmr = new CMR_ColorManagementResource();
        assertThrows(Exception.class, () -> {
            cmr.decodeAFP(data, 9, 164, new AFPParserConfiguration());
        });
    }

    @Test
    public void testInvalidMediaWeight() throws Exception {
        // [CMOCA-3-260] EC-EFF710 Invalid Field Value: The specified MediaWeight is invalid.
        byte[] data = createCmrHeaderShell();
        String weight = "000"; // < 1
        byte[] bytes = weight.getBytes(Constants.utf16be);
        System.arraycopy(bytes, 0, data, 97, 6);

        CMR_ColorManagementResource cmr = new CMR_ColorManagementResource();
        assertThrows(Exception.class, () -> {
            cmr.decodeAFP(data, 9, 164, new AFPParserConfiguration());
        }, "Should throw for 000");

        // Test non-numeric
        byte[] data2 = createCmrHeaderShell();
        String weight2 = "abc";
        byte[] bytes2 = weight2.getBytes(Constants.utf16be);
        System.arraycopy(bytes2, 0, data2, 97, 6);
        assertThrows(Exception.class, () -> {
            new CMR_ColorManagementResource().decodeAFP(data2, 9, 164, new AFPParserConfiguration());
        }, "Should throw for abc");
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

    @Test
    public void testMediaRecommendedValues() throws Exception {
        // [CMOCA-3-066/068] MediaColor blu, gdr
        // [CMOCA-3-081/084] MediaFinish cm, hg
        String[] colors = {"blu", "buf", "gdr", "grn", "gry", "ivy", "noc", "org", "pnk", "red", "wht", "ylw"};
        String[] finishes = {"cm", "ct", "gl", "hg", "mt", "no", "np", "sg", "st", "tr"};

        for (String color : colors) {
            byte[] data = createCmrHeaderShell();
            System.arraycopy(color.getBytes(Constants.utf16be), 0, data, 87, color.length() * 2);
            CMR_ColorManagementResource cmr = new CMR_ColorManagementResource();
            cmr.decodeAFP(data, 9, 164, new AFPParserConfiguration());
            assertEquals(color, cmr.getMediaColor());
        }

        for (String finish : finishes) {
            byte[] data = createCmrHeaderShell();
            System.arraycopy(finish.getBytes(Constants.utf16be), 0, data, 93, finish.length() * 2);
            CMR_ColorManagementResource cmr = new CMR_ColorManagementResource();
            cmr.decodeAFP(data, 9, 164, new AFPParserConfiguration());
            assertEquals(finish, cmr.getMediaFinish());
        }
    }

    @Test
    public void testReservedFieldStructure() throws Exception {
        // [CMOCA-3-099] Bytes 140-155 @@@@@@@@ Reserved
        // [CMOCA-3-101] Bytes 156-163 X'00...00' Reserved
        byte[] data = createCmrHeaderShell();
        // createCmrHeaderShell already pads name area with @ [CMOCA-3-041]

        CMR_ColorManagementResource cmr = new CMR_ColorManagementResource();
        cmr.decodeAFP(data, 9, 164, new AFPParserConfiguration());

        // reserved2 is bytes 140-155 (offset 149 in SF)
        // Since it's padded with @ and trimCmrString removes them, it should be empty
        assertEquals("", cmr.getReserved2());

        // reserved3 is bytes 156-163 (offset 165 in SF)
        assertEquals(0, cmr.getReserved3());
    }

    @Test
    public void testArchitectedName() throws Exception {
        // [CMOCA-3-042] to [CMOCA-3-044]
        byte[] data = createCmrHeaderShell();
        // createCmrHeaderShell already sets Type to "CC" at 35-38

        // CMRAlias (10-25)
        System.arraycopy("JohnMay4".getBytes(Constants.utf16be), 0, data, 19, 16);
        // CMRType (26-29) is "HT" in example [CMOCA-3-043]
        System.arraycopy("HT".getBytes(Constants.utf16be), 0, data, 35, 4);
        // CMRVersion (30-43)
        System.arraycopy("1.2".getBytes(Constants.utf16be), 0, data, 39, 6);
        // ManufacturerName (44-53)
        System.arraycopy("IBM".getBytes(Constants.utf16be), 0, data, 53, 6);

        CMR_ColorManagementResource cmr = new CMR_ColorManagementResource();
        cmr.decodeAFP(data, 9, 164, new AFPParserConfiguration());

        String name = cmr.getArchitectedName();
        assertNotNull(name);
        assertTrue(name.startsWith("JohnMay4HT"));
        assertTrue(name.contains("1.2"));
        assertTrue(name.contains("IBM"));
        assertEquals(146/2, name.length()); // 146 bytes / 2
    }
}
