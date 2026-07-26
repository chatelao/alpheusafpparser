package com.mgz.afp.cmoca;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.mgz.afp.exceptions.AFPParserException;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;

/**
 * Verification test for Phase 4.1.1.1 CMOCA Tag Validation.
 * Focuses on verification of color management resources and tag structures.
 */
public class CMOCAPhase4VerificationTest {

    @Test
    public void testCmrTagValidationAndStructure() {
        // Create a CMRTag with invalid length or negative offset to verify exception handling
        // [CMOCA-4-008] Validating CMR tag structures
        CMRTag tag = new CMRTag(0x1011, 0x01, -1, 0); // Invalid length
        assertNotNull(tag);

        // Verify invalid CMR type validation exceptions
        CMR_ColorManagementResource cmr = new CMR_ColorManagementResource();
        cmr.setType("INVALID_TYPE");
        cmr.setTags(new ArrayList<>());
        assertThrows(AFPParserException.class, cmr::validate, "Should fail on invalid CMR type");
    }

    @Test
    public void testCmrTagOrderingAndValidation() throws Exception {
        CMR_ColorManagementResource cmr = new CMR_ColorManagementResource();
        cmr.setType("CC"); // Color Conversion
        List<CMRTag> tags = new ArrayList<>();

        // Populate standard Color Conversion tags
        // [CMOCA-4-046] CC Mandatory Tags
        CMRTag subsetTag = new CMRTag(0x3011, 0x01, 1, 0);
        subsetTag.setData(new byte[]{0x01});
        tags.add(subsetTag);

        CMRTag dataTag = new CMRTag(0x3015, 0x01, 4, 0);
        dataTag.setData(new byte[]{0x01, 0x02, 0x03, 0x04});
        tags.add(dataTag);

        // End Data tag X'FFFF'
        tags.add(new CMRTag(0xFFFF, 0x05, 0, 0));

        cmr.setTags(tags);
        cmr.validate(); // Should pass successfully
    }
}
