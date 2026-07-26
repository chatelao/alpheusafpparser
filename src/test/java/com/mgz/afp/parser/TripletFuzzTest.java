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
import com.mgz.afp.exceptions.AFPParserException;
import com.mgz.afp.triplets.Triplet;

import java.util.List;

public class TripletFuzzTest {

    @FuzzTest(maxDuration = "5s")
    public void fuzzTriplets(byte[] data) {
        AFPParserConfiguration config = new AFPParserConfiguration();
        try {
            List<Triplet> triplets = TripletParser.parseTriplets(data, 0, data.length, config);
            for (Triplet t : triplets) {
                if (t != null) {
                    t.getClass().getSimpleName();
                }
            }
        } catch (Throwable t) {
            if (t instanceof AFPParserException || t.getCause() instanceof AFPParserException) {
                // Expected parser exception (direct or sneaky-thrown/wrapped)
                return;
            }
            throw t;
        }

        try {
            Triplet triplet = TripletParser.parseTriplet(data, 0, data.length, config);
            if (triplet != null) {
                triplet.getClass().getSimpleName();
            }
        } catch (AFPParserException e) {
            // Expected parsing error
        } catch (Throwable t) {
            // Unexpected crash, let Jazzer know
            throw t;
        }
    }
}
