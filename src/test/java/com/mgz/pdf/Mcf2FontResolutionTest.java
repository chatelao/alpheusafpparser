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

package com.mgz.pdf;

import com.mgz.afp.modca.MCF_MapCodedFont_Format2;
import com.mgz.afp.triplets.Triplet;
import com.mgz.afp.triplets.Triplet.FullyQualifiedName;
import com.mgz.afp.triplets.Triplet.FontDescriptorSpecification;
import com.mgz.afp.triplets.Triplet.ResourceLocalIdentifier;
import com.mgz.afp.triplets.Triplet.GlobalID_Use;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class Mcf2FontResolutionTest {

    @Test
    public void testMcf2FontResolutionWithCharacterSetName() throws Exception {
        PdfHandler handler = new PdfHandler(new ByteArrayOutputStream());

        MCF_MapCodedFont_Format2 mcf2 = new MCF_MapCodedFont_Format2();
        List<com.mgz.afp.base.IRepeatingGroup> groups = new ArrayList<>();

        MCF_MapCodedFont_Format2.MCF_RepeatingGroup rg = new MCF_MapCodedFont_Format2.MCF_RepeatingGroup();
        List<Triplet> triplets = new ArrayList<>();

        // Resource Local ID = 1
        ResourceLocalIdentifier rli = new ResourceLocalIdentifier();
        rli.setResourceType(ResourceLocalIdentifier.RLI_ResourceType.CodedFont);
        rli.setResourceLocalID((short) 1);
        triplets.add(rli);

        // Font Character Set Name = CZH483
        FullyQualifiedName fqn = new FullyQualifiedName();
        fqn.setType(GlobalID_Use.FontCharacterSetNameReference);
        fqn.setNameAsString("CZH483");
        triplets.add(fqn);

        // Font Height = 320 (16pt)
        FontDescriptorSpecification fds = new FontDescriptorSpecification();
        fds.fontHeight = 320;
        triplets.add(fds);

        rg.setTriplets(triplets);
        groups.add(rg);
        mcf2.setRepeatingGroups(groups);

        handler.handle(mcf2);

        Map<Short, PdfHandler.FontResource> fontMap = handler.getFontMap();
        assertNotNull(fontMap.get((short) 1));
        assertEquals("CZH483", fontMap.get((short) 1).name());
        assertEquals(16.0f, fontMap.get((short) 1).size(), 0.01f);
    }
}
