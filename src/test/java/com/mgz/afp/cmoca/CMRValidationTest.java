package com.mgz.afp.cmoca;

import com.mgz.afp.exceptions.AFPParserException;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

public class CMRValidationTest {

    private CMR_ColorManagementResource createCmr(String type, int subsetTagId, int subsetValue) {
        CMR_ColorManagementResource cmr = new CMR_ColorManagementResource();
        cmr.setType(type);
        List<CMRTag> tags = new ArrayList<>();
        if (subsetTagId != -1) {
            CMRTag subsetTag = new CMRTag(subsetTagId, 0x01, 1, 0);
            subsetTag.setData(new byte[]{(byte) subsetValue});
            tags.add(subsetTag);
        }
        // End Data tag [CMOCA-5-311]
        tags.add(new CMRTag(0xFFFF, 0x05, 0, 0));
        cmr.setTags(tags);
        return cmr;
    }

    private void addTag(CMR_ColorManagementResource cmr, int tagId) {
        cmr.getTags().add(0, new CMRTag(tagId, 0x01, 1, 0));
    }

    @Test
    public void testMissingEndData() {
        // [CMOCA-4-008] The End Data tag is required in all CMR objects.
        CMR_ColorManagementResource cmr = new CMR_ColorManagementResource();
        cmr.setType("HT");
        cmr.setTags(new ArrayList<>());
        assertThrows(AFPParserException.class, cmr::validate);

        cmr.getTags().add(new CMRTag(0x1011, 0x01, 1, 0));
        assertThrows(AFPParserException.class, cmr::validate);
    }

    @Test
    public void testHalftoneValidation() throws Exception {
        // [CMOCA-4-011] HT Subset X'01'
        CMR_ColorManagementResource cmr = createCmr("HT", 0x1011, 0x01);
        assertThrows(AFPParserException.class, cmr::validate);

        addTag(cmr, 0x1021); // Array Width
        addTag(cmr, 0x1025); // Array Height
        assertThrows(AFPParserException.class, cmr::validate);

        addTag(cmr, 0x1045); // Screen Data
        cmr.validate();

        // [CMOCA-4-013] HT Subset X'02'
        CMR_ColorManagementResource cmr2 = createCmr("HT", 0x1011, 0x02);
        addTag(cmr2, 0x1021);
        addTag(cmr2, 0x1025);
        addTag(cmr2, 0x1030);
        addTag(cmr2, 0x1035);
        addTag(cmr2, 0x1050);
        cmr2.validate();

        // [CMOCA-4-015] HT Subset X'03'
        CMR_ColorManagementResource cmr3 = createCmr("HT", 0x1011, 0x03);
        addTag(cmr3, 0x1021);
        addTag(cmr3, 0x1025);
        addTag(cmr3, 0x1055);
        addTag(cmr3, 0x1060);
        addTag(cmr3, 0x1065);
        addTag(cmr3, 0x1070);
        addTag(cmr3, 0x1075);
        cmr3.validate();

        // [CMOCA-4-017] HT Subset X'04'
        CMR_ColorManagementResource cmr4 = createCmr("HT", 0x1011, 0x04);
        addTag(cmr4, 0x1021);
        addTag(cmr4, 0x1025);
        addTag(cmr4, 0x1035);
        addTag(cmr4, 0x1055);
        addTag(cmr4, 0x1060);
        addTag(cmr4, 0x1065);
        addTag(cmr4, 0x1070);
        addTag(cmr4, 0x1080);
        cmr4.validate();
    }

    @Test
    public void testToneTransferCurveValidation() throws Exception {
        // [CMOCA-4-021] TC Subset X'01'
        CMR_ColorManagementResource cmr = createCmr("TC", 0x2004, 0x01);
        assertThrows(AFPParserException.class, cmr::validate);
        addTag(cmr, 0x2015);
        cmr.validate();

        // [CMOCA-4-024] TC Subset X'02'
        CMR_ColorManagementResource cmr2 = createCmr("TC", 0x2004, 0x02);
        cmr2.validate();
    }

    @Test
    public void testColorConversionValidation() throws Exception {
        // [CMOCA-4-046] CC Mandatory Tags
        CMR_ColorManagementResource cmr = createCmr("CC", -1, 0);
        assertThrows(AFPParserException.class, cmr::validate);

        addTag(cmr, 0x3011); // ICC Profile Subset
        addTag(cmr, 0x3015); // ICC Profile Data
        cmr.validate();
    }

    @Test
    public void testLinkValidation() throws Exception {
        // [CMOCA-4-114] LK Subset X'01'
        CMR_ColorManagementResource cmr = createCmr("LK", 0x4011, 0x01);
        assertThrows(AFPParserException.class, cmr::validate);
        addTag(cmr, 0x4015);
        addTag(cmr, 0x4020);
        addTag(cmr, 0x4025);
        addTag(cmr, 0x4030);
        addTag(cmr, 0x4035);
        addTag(cmr, 0x4040);
        cmr.validate();

        // [CMOCA-4-117] LK Subset X'02'
        CMR_ColorManagementResource cmr2 = createCmr("LK", 0x4011, 0x02);
        assertThrows(AFPParserException.class, cmr2::validate);
        addTag(cmr2, 0x4015);
        addTag(cmr2, 0x4020);
        addTag(cmr2, 0x4025);
        addTag(cmr2, 0x4030);
        cmr2.validate();

        // [CMOCA-4-119] LK Subset X'03'
        CMR_ColorManagementResource cmr3 = createCmr("LK", 0x4011, 0x03);
        assertThrows(AFPParserException.class, cmr3::validate);
        addTag(cmr3, 0x3015);
        cmr3.validate();
    }

    @Test
    public void testIndexedValidation() throws Exception {
        // [CMOCA-4-130] Indexed Mandatory Tags
        CMR_ColorManagementResource cmr = createCmr("IX", 0x5011, 0x01);
        assertThrows(AFPParserException.class, cmr::validate);

        addTag(cmr, 0x5020); // Gray Palette
        cmr.validate();
    }
}
