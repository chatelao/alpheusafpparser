/*
Copyright 2026 Rudolf Fiala

This file is part of Alpheus AFP Parser.

Alpheus AFP Parser is free software: you can redistribute it and/or modify
it under the terms of the GNU General Public License as published by
the Free Software Foundation, either version 3 of the License, or
(at your option) any later version.

Alpheus AFP Parser is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License
along with Alpheus AFP Parser.  If not, see <http://www.gnu.org/licenses/>
*/

package com.mgz.afp.modca;

import com.mgz.afp.parser.AFPParserConfiguration;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Tests for Begin Medium Map (BMM) structured field.
 */
public class BMMTest {

    @Test
    public void testBMMNameDecoding() throws Exception {
        // [MODCA-5-149] 0–7 CHAR MMName Name of the medium map M X'06'
        // This test verifies that the MMName is correctly decoded from the first 8 bytes of the SF payload.
        BMM_BeginMediumMap bmm = new BMM_BeginMediumMap();

        // "MAP00001" in EBCDIC (CP500)
        byte[] data = new byte[] {
            (byte) 0xD4, (byte) 0xC1, (byte) 0xD7, (byte) 0xF0,
            (byte) 0xF0, (byte) 0xF0, (byte) 0xF0, (byte) 0xF1
        };

        AFPParserConfiguration config = new AFPParserConfiguration();
        // Default charset in config is CP500

        bmm.decodeAFP(data, 0, 8, config);

        assertEquals("MAP00001", bmm.getName(), "MMName should be correctly decoded from EBCDIC bytes");
    }
}
