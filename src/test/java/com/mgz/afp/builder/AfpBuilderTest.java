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

package com.mgz.afp.builder;

import com.mgz.afp.base.SfiPool;
import com.mgz.afp.base.StructuredFieldPool;
import com.mgz.afp.base.StructuredFieldBaseDataPool;
import com.mgz.afp.enums.SFTypeID;
import com.mgz.afp.modca.TLE_TagLogicalElement;
import com.mgz.afp.modca.BNG_BeginNamedPageGroup;
import com.mgz.afp.modca.ENG_EndNamedPageGroup;
import com.mgz.afp.modca.NOP_NoOperation;
import com.mgz.afp.parser.AFPParserConfiguration;
import com.mgz.afp.triplets.Triplet;
import com.mgz.afp.triplets.TripletPool;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class AfpBuilderTest {

    @Test
    public void testTleBuilderDetached() throws Exception {
        TLE_TagLogicalElement tle = new TleBuilder()
                .withAttribute("DocID", "DOC-999")
                .build();

        assertNotNull(tle);
        assertFalse(tle.isPooled(), "TLE should be detached (isPooled == false)");
        assertNotNull(tle.getStructuredFieldIntroducer());
        assertFalse(tle.getStructuredFieldIntroducer().isPooled(), "SFI should be detached");

        List<Triplet> triplets = tle.getTriplets();
        assertEquals(2, triplets.size());

        Triplet.FullyQualifiedName fqn = (Triplet.FullyQualifiedName) triplets.get(0);
        assertEquals(Triplet.GlobalID_Use.AttributeGID, fqn.getType());
        assertEquals("DocID", fqn.getNameAsString());
        assertFalse(fqn.isPooled(), "FQN triplet should be detached");

        Triplet.AttributeValue av = (Triplet.AttributeValue) triplets.get(1);
        assertEquals("DOC-999", av.getAttributeValue());
        assertFalse(av.isPooled(), "AttributeValue triplet should be detached");

        // Verify write/serialize
        AFPParserConfiguration config = new AFPParserConfiguration();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        tle.writeAFP(baos, config);
        byte[] bytes = baos.toByteArray();
        assertTrue(bytes.length > 0);
        assertEquals(0x5A, bytes[0] & 0xFF, "Should start with 0x5A");
    }

    @Test
    public void testBngBuilderDetached() throws Exception {
        BNG_BeginNamedPageGroup bng = new BngBuilder()
                .withName("GROUP_A")
                .build();

        assertNotNull(bng);
        assertFalse(bng.isPooled(), "BNG should be detached");
        assertEquals("GROUP_A", bng.getName());

        AFPParserConfiguration config = new AFPParserConfiguration();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bng.writeAFP(baos, config);
        byte[] bytes = baos.toByteArray();
        assertTrue(bytes.length > 0);
        assertEquals(0x5A, bytes[0] & 0xFF);
    }

    @Test
    public void testEngBuilderDetached() throws Exception {
        ENG_EndNamedPageGroup eng = new EngBuilder()
                .withName("GROUP_A")
                .build();

        assertNotNull(eng);
        assertFalse(eng.isPooled(), "ENG should be detached");
        assertEquals("GROUP_A", eng.getName());

        AFPParserConfiguration config = new AFPParserConfiguration();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        eng.writeAFP(baos, config);
        byte[] bytes = baos.toByteArray();
        assertTrue(bytes.length > 0);
        assertEquals(0x5A, bytes[0] & 0xFF);
    }

    @Test
    public void testNopBuilderDetached() throws Exception {
        NOP_NoOperation nop = new NopBuilder()
                .withComment("Comment NOP")
                .build();

        assertNotNull(nop);
        assertFalse(nop.isPooled(), "NOP should be detached");

        AFPParserConfiguration config = new AFPParserConfiguration();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        nop.writeAFP(baos, config);
        byte[] bytes = baos.toByteArray();
        assertTrue(bytes.length > 0);
        assertEquals(0x5A, bytes[0] & 0xFF);
    }

    @Test
    public void testSafeReleaseNoOp() {
        TLE_TagLogicalElement tle = new TleBuilder()
                .withAttribute("DocID", "DOC-999")
                .build();

        // Calling release should be safe and shouldn't change the pools
        int sfiPoolBefore = SfiPool.size();
        int basePoolBefore = StructuredFieldBaseDataPool.size();

        // Release the detached structured field
        tle.release();

        // Verify pool sizes did not change
        assertEquals(sfiPoolBefore, SfiPool.size(), "SfiPool size should remain unchanged");
        assertEquals(basePoolBefore, StructuredFieldBaseDataPool.size(), "StructuredFieldBaseDataPool size should remain unchanged");

        // Manually try releasing to pools directly
        StructuredFieldPool.release(tle, SFTypeID.TLE_TagLogicalElement);
        TripletPool.release(tle.getTriplets().get(0));
        SfiPool.release(tle.getStructuredFieldIntroducer());

        assertEquals(sfiPoolBefore, SfiPool.size(), "SfiPool size should remain unchanged after direct release");
    }
}
