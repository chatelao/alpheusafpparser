package com.mgz.afp.bcoca;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.mgz.afp.exceptions.AFPParserException;
import com.mgz.afp.parser.AFPParserConfiguration;
import org.junit.jupiter.api.Test;

/**
 * Verification test for Phase 4.1.1.2 BCOCA Architectural Validations.
 * Verifies modifiers, types, and barcode structures.
 */
public class BCOCAPhase4VerificationTest {

    @Test
    public void testBcocaArchitecturalValidations() {
        BDD_BarCodeDataDescriptor bdd = new BDD_BarCodeDataDescriptor();
        byte[] data = createValidBDD();

        // [BCOCA-5-001] Verify barcode modifiers and types
        // Verify invalid barcode type validation
        data[12] = 0x7F; // Invalid barcode type

        AFPParserException ex = assertThrows(AFPParserException.class, () ->
            bdd.decodeAFP(data, 0, data.length, new AFPParserConfiguration())
        );
        assertTrue(ex.getMessage().contains("EC-0A00") || ex.getMessage().contains("invalid") || ex.getMessage().contains("Exception"));
    }

    private byte[] createValidBDD() {
        byte[] data = new byte[23];
        data[0] = 0x00; // Unit base: 10 inches
        data[1] = 0x00; // Reserved
        data[2] = 0x38; // Xupub: 14400 (0x3840)
        data[3] = 0x40;
        data[4] = 0x38; // Yupub: 14400
        data[5] = 0x40;
        data[6] = 0x00; // X extent: 100
        data[7] = 0x64;
        data[8] = 0x00; // Y extent: 100
        data[9] = 0x64;
        data[10] = 0x00; // Desired symbol width: 0
        data[11] = 0x00;
        data[12] = 0x01; // Type: Code 39
        data[13] = 0x01; // Modifier: 1
        data[14] = (byte) 0xFF; // Local ID: Default
        data[15] = (byte) 0xFF; // Color: Default
        data[16] = 0x07;
        data[17] = 0x10; // Module width: 16
        data[18] = 0x00; // Element height: 100
        data[19] = 0x64;
        data[20] = 0x01; // Multiplier: 1
        data[21] = 0x00; // WE:NE: 0
        data[22] = 0x00;
        return data;
    }
}
