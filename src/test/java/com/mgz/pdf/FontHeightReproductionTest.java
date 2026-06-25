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

import com.mgz.afp.base.StructuredField;
import com.mgz.afp.base.StructuredFieldIntroducer;
import com.mgz.afp.enums.AFPUnitBase;
import com.mgz.afp.enums.SFTypeID;
import com.mgz.afp.modca.BAG_BeginActiveEnvironmentGroup;
import com.mgz.afp.modca.BPG_BeginPage;
import com.mgz.afp.modca.EAG_EndActiveEnvironmentGroup;
import com.mgz.afp.modca.MCF_MapCodedFont_Format2;
import com.mgz.afp.modca.PGD_PageDescriptor;
import com.mgz.afp.ptoca.PTX_PresentationTextData;
import com.mgz.afp.ptoca.controlSequence.PTOCAControlSequence;
import com.mgz.afp.ptoca.controlSequence.PTOCAControlSequence.SCFL_SetCodedFontLocal;
import com.mgz.afp.ptoca.controlSequence.PTOCAControlSequence.TRN_TransparentData;
import com.mgz.afp.triplets.Triplet;
import com.mgz.afp.triplets.Triplet.FontDescriptorSpecification;
import com.mgz.afp.triplets.Triplet.FullyQualifiedName;
import com.mgz.afp.triplets.Triplet.GlobalID_Use;
import com.mgz.afp.triplets.Triplet.ResourceLocalIdentifier;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class FontHeightReproductionTest {

    private void prepareSF(StructuredField sf, SFTypeID typeID) {
        StructuredFieldIntroducer sfi = new StructuredFieldIntroducer();
        sfi.setSFTypeID(typeID);
        sf.setStructuredFieldIntroducer(sfi);
    }

    @Test
    public void testFontHeightFromMcf2InAeg() throws Exception {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PdfHandler handler = new PdfHandler(baos);

        // 1. Begin Page
        BPG_BeginPage bpg = new BPG_BeginPage();
        bpg.setName("P001");
        prepareSF(bpg, SFTypeID.BPG_BeginPage);
        handler.handle(bpg);

        // 2. Begin AEG
        BAG_BeginActiveEnvironmentGroup bag = new BAG_BeginActiveEnvironmentGroup();
        prepareSF(bag, SFTypeID.BAG_BeginActiveEnvironmentGroup);
        handler.handle(bag);

        // 3. MCF2 with fontHeight 220 (11pt) for LID 2
        MCF_MapCodedFont_Format2 mcf2 = new MCF_MapCodedFont_Format2();
        prepareSF(mcf2, SFTypeID.MCF_MapCodedFont_Format2);
        List<com.mgz.afp.base.IRepeatingGroup> groups = new ArrayList<>();
        MCF_MapCodedFont_Format2.MCF_RepeatingGroup rg = new MCF_MapCodedFont_Format2.MCF_RepeatingGroup();
        List<Triplet> triplets = new ArrayList<>();

        ResourceLocalIdentifier rli = new ResourceLocalIdentifier();
        rli.setResourceType(ResourceLocalIdentifier.RLI_ResourceType.CodedFont);
        rli.setResourceLocalID((short) 2);
        triplets.add(rli);

        FullyQualifiedName fqn = new FullyQualifiedName();
        fqn.setType(GlobalID_Use.FontCharacterSetNameReference);
        fqn.setNameAsString("CZH483");
        triplets.add(fqn);

        FontDescriptorSpecification fds = new FontDescriptorSpecification();
        fds.fontHeight = 220; // 11pt with 0.05 scaling
        triplets.add(fds);

        rg.setTriplets(triplets);
        groups.add(rg);
        mcf2.setRepeatingGroups(groups);
        handler.handle(mcf2);

        // 4. End AEG
        EAG_EndActiveEnvironmentGroup eag = new EAG_EndActiveEnvironmentGroup();
        prepareSF(eag, SFTypeID.EAG_EndActiveEnvironmentGroup);
        handler.handle(eag);

        // After AEG, the font should STILL be available because BAG/EAG scoping was fixed
        assertNotNull(handler.getFontMap().get((short) 2), "Font should persist after EAG");
        assertEquals(11.0f, handler.getFontMap().get((short) 2).size(), 0.01f);
    }

    @Test
    public void testFontHeightWithCustomScalingFromPgd() throws Exception {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PdfHandler handler = new PdfHandler(baos);

        // 1. Begin Page
        BPG_BeginPage bpg = new BPG_BeginPage();
        prepareSF(bpg, SFTypeID.BPG_BeginPage);
        handler.handle(bpg);

        // 2. PGD with 300 units per inch (0.24 points per unit)
        PGD_PageDescriptor pgd = new PGD_PageDescriptor();
        prepareSF(pgd, SFTypeID.PGD_PageDescriptor);
        pgd.setxUnitBase(AFPUnitBase.Inches10);
        pgd.setyUnitBase(AFPUnitBase.Inches10);
        pgd.setxUnitsPerUnitBase((short) 3000);
        pgd.setyUnitsPerUnitBase((short) 3000);
        pgd.setxSize(2400);
        pgd.setySize(3300);
        handler.handle(pgd);

        // Check scaling: 72 points / (3000/10 units) = 72 / 300 = 0.24
        assertEquals(0.24f, handler.getDefaultScaleY(), 0.001f);

        // 3. MCF2 with fontHeight 240 (240 * 0.05 = 12pt)
        // Note: fontHeight in MCF is independent of PGD scale by default (fixed at 1/1440 inch)
        MCF_MapCodedFont_Format2 mcf2 = new MCF_MapCodedFont_Format2();
        prepareSF(mcf2, SFTypeID.MCF_MapCodedFont_Format2);
        List<com.mgz.afp.base.IRepeatingGroup> groups = new ArrayList<>();
        MCF_MapCodedFont_Format2.MCF_RepeatingGroup rg = new MCF_MapCodedFont_Format2.MCF_RepeatingGroup();
        List<Triplet> triplets = new ArrayList<>();

        ResourceLocalIdentifier rli = new ResourceLocalIdentifier();
        rli.setResourceType(ResourceLocalIdentifier.RLI_ResourceType.CodedFont);
        rli.setResourceLocalID((short) 1);
        triplets.add(rli);

        FontDescriptorSpecification fds = new FontDescriptorSpecification();
        fds.fontHeight = 240;
        triplets.add(fds);

        FullyQualifiedName fqn = new FullyQualifiedName();
        fqn.setType(GlobalID_Use.FontCharacterSetNameReference);
        fqn.setNameAsString("CZH483");
        triplets.add(fqn);

        rg.setTriplets(triplets);
        groups.add(rg);
        mcf2.setRepeatingGroups(groups);
        handler.handle(mcf2);

        assertEquals(12.0f, handler.getFontMap().get((short) 1).size(), 0.01f);
    }
}
