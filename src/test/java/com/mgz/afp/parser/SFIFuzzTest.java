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

package com.mgz.afp.parser;

import com.code_intelligence.jazzer.junit.FuzzTest;
import com.mgz.afp.base.StructuredFieldIntroducer;
import com.mgz.afp.exceptions.AFPParserException;

import java.io.ByteArrayInputStream;
import java.nio.ByteBuffer;

public class SFIFuzzTest {

    @FuzzTest(maxDuration = "5s")
    public void fuzzSFI(byte[] data) {
        // Test parsing via InputStream
        try {
            ByteArrayInputStream bais = new ByteArrayInputStream(data);
            StructuredFieldIntroducer sfi = StructuredFieldIntroducer.parse(bais);
            if (sfi != null) {
                sfi.getClass().getSimpleName();
            }
        } catch (AFPParserException e) {
            // Expected parsing error
        } catch (Throwable t) {
            throw t;
        }

        // Test parsing via ByteBuffer
        try {
            ByteBuffer buffer = ByteBuffer.wrap(data);
            StructuredFieldIntroducer sfi = StructuredFieldIntroducer.parse(buffer);
            if (sfi != null) {
                sfi.getClass().getSimpleName();
            }
        } catch (AFPParserException e) {
            // Expected parsing error
        } catch (Throwable t) {
            throw t;
        }

        // Test parsing via ByteBuffer at offset 0
        try {
            ByteBuffer buffer = ByteBuffer.wrap(data);
            StructuredFieldIntroducer sfi = StructuredFieldIntroducer.parse(buffer, 0);
            if (sfi != null) {
                sfi.getClass().getSimpleName();
            }
        } catch (AFPParserException e) {
            // Expected parsing error
        } catch (Throwable t) {
            throw t;
        }
    }
}
