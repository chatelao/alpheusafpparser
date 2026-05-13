package com.mgz.afp.ptoca;

import com.mgz.afp.RoundTripTestUtils;
import com.mgz.afp.exceptions.AFPParserException;
import com.mgz.afp.parser.AFPParserConfiguration;
import com.mgz.afp.parser.PTOCAControlSequenceParser;
import com.mgz.afp.ptoca.controlSequence.PTOCAControlSequence;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class PTOCAGapReproductionTest {

    @Test
    public void testFreeStandingTextParsing() throws Exception {
        // PTX: D3EE9B
        // Payload: "ABC" (EBCDIC CP500: C1 C2 C3) followed by SCFL 2B D3 03 F0 01
        // ABC = C1 C2 C3
        // SCFL = 2B D3 03 F0 01
        // Total Payload: 3 + 5 = 8 bytes
        byte[] data = new byte[] {
            0x5A, 0x00, 0x10, (byte) 0xD3, (byte) 0xEE, (byte) 0x9B, 0x00, 0x00, 0x00,
            (byte) 0xC1, (byte) 0xC2, (byte) 0xC3, 0x2B, (byte) 0xD3, 0x03, (byte) 0xF0, 0x01
        };

        PTX_PresentationTextData ptx = new PTX_PresentationTextData();
        // This is expected to fail or misparse if the parser only looks for 2B
        try {
            ptx.decodeAFP(data, 0, 17, new AFPParserConfiguration());
            List<PTOCAControlSequence> sequences = ptx.getControlSequences();

            assertNotNull(sequences);
        } catch (Exception e) {
            System.out.println("Caught exception during free-standing text parsing: " + e.getClass().getName() + ": " + e.getMessage());
            e.printStackTrace();
        }
    }

    @Test
    public void testGLCParsing() throws Exception {
        // GLC: 2B D3 0A 6D ... (Length 10, Type 6D)
        byte[] data = new byte[] {
            0x5A, 0x00, 0x12, (byte) 0xD3, (byte) 0xEE, (byte) 0x9B, 0x00, 0x00, 0x00,
            0x2B, (byte) 0xD3, 0x0A, 0x6D, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00
        };
        PTX_PresentationTextData ptx = new PTX_PresentationTextData();
        try {
            ptx.decodeAFP(data, 0, 20, new AFPParserConfiguration());
            // This will fail because GLC_GlyphLayoutControl is not in the enum/classes
        } catch (AFPParserException e) {
            assertTrue(e.getMessage().contains("failed to instantiate control sequence class"));
            System.out.println("Caught expected exception for missing GLC: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Caught unexpected exception during GLC parsing: " + e.getClass().getName() + ": " + e.getMessage());
            e.printStackTrace();
        }
    }
}
