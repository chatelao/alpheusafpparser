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

import com.mgz.afp.base.StructuredFieldIntroducer;
import com.mgz.afp.enums.AFPUnitBase;
import com.mgz.afp.enums.SFTypeID;
import com.mgz.afp.modca.BPG_BeginPage;
import com.mgz.afp.modca.MCF_MapCodedFont_Format2;
import com.mgz.afp.modca.PGD_PageDescriptor;
import com.mgz.afp.triplets.Triplet;
import com.mgz.afp.triplets.Triplet.FontDescriptorSpecification;
import com.mgz.afp.triplets.Triplet.FullyQualifiedName;
import com.mgz.afp.triplets.Triplet.ResourceLocalIdentifier;
import com.mgz.afp.triplets.Triplet.ResourceLocalIdentifier.RLI_ResourceType;
import com.mgz.afp.ptoca.PTX_PresentationTextData;
import com.mgz.afp.ptoca.controlSequence.PTOCAControlSequence.SCFL_SetCodedFontLocal;
import com.mgz.afp.ptoca.controlSequence.PTOCAControlSequence.TRN_TransparentData;
import com.itextpdf.kernel.font.PdfFont;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class Mcf2FontResolutionTest {

    @Test
    public void testMcf2FontSizeAndMapping() throws Exception {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PdfHandler handler = new PdfHandler(baos);

        // 1. Begin Page
        BPG_BeginPage bpg = new BPG_BeginPage();
        bpg.setStructuredFieldIntroducer(createSfi(SFTypeID.BPG_BeginPage));
        handler.handle(bpg);

        // 2. PGD with 600 DPI (6000 units per 10 inches)
        PGD_PageDescriptor pgd = new PGD_PageDescriptor();
        pgd.setxUnitBase(AFPUnitBase.Inches10);
        pgd.setyUnitBase(AFPUnitBase.Inches10);
        pgd.setxUnitsPerUnitBase((short) 6000);
        pgd.setyUnitsPerUnitBase((short) 6000);
        pgd.setxSize(7016);
        pgd.setySize(4961);
        handler.handle(pgd);

        // Verify scale factor for 600 DPI (720 / 6000 = 0.12)
        assertEquals(0.12f, handler.getDefaultScaleY(), 0.001f);

        // 3. MCF Format 2 with CZN481 and fontHeight 200
        MCF_MapCodedFont_Format2 mcf2 = new MCF_MapCodedFont_Format2();
        MCF_MapCodedFont_Format2.MCF_RepeatingGroup rg = new MCF_MapCodedFont_Format2.MCF_RepeatingGroup();

        List<Triplet> triplets = new ArrayList<>();

        FullyQualifiedName fqn = new FullyQualifiedName();
        fqn.setType(Triplet.GlobalID_Use.CodedFontNameReference);
        fqn.setNameAsString("CZN481");
        triplets.add(fqn);

        ResourceLocalIdentifier rli = new ResourceLocalIdentifier();
        rli.setResourceType(RLI_ResourceType.CodedFont);
        rli.setResourceLocalID((short) 1);
        triplets.add(rli);

        FontDescriptorSpecification fds = new FontDescriptorSpecification();
        fds.fontHeight = 200; // 200 / 1440 inch = 10 points
        triplets.add(fds);

        rg.setTriplets(triplets);
        mcf2.addRepeatingGroup(rg);
        handler.handle(mcf2);

        // Check resolved font resource
        PdfHandler.FontResource resource = handler.getFontMap().get((short) 1);
        assertEquals("CZN481", resource.name());

        // BUG 1: Font size should be 10.0, but currently it might be 200 * 0.12 = 24.0
        assertEquals(10.0f, resource.size(), 0.001f, "Font size should be 10pt (200/1440 inch)");

        // 4. Presentation Text to trigger font loading
        PTX_PresentationTextData ptx = new PTX_PresentationTextData();
        ptx.setStructuredFieldIntroducer(createSfi(SFTypeID.PTX_PresentationTextData));

        SCFL_SetCodedFontLocal scfl = new SCFL_SetCodedFontLocal();
        scfl.setCodedFontLocalID((short) 1);
        ptx.addControlSequence(scfl);

        TRN_TransparentData trn = new TRN_TransparentData();
        trn.setTransparentData("Test");
        ptx.addControlSequence(trn);

        handler.handle(ptx);

        PdfFont font = handler.getFontRegistry().getFont("CZN481");
        // BUG 2: CZN should be Sans-serif (Helvetica), but currently it is Serif (Times)
        assertTrue(font.getFontProgram().getFontNames().getFontName().toLowerCase().contains("helvetica"),
            "Font CZN481 should be mapped to Helvetica (Sans-serif), but was: " + font.getFontProgram().getFontNames().getFontName());
    }

    private StructuredFieldIntroducer createSfi(SFTypeID typeID) {
        StructuredFieldIntroducer sfi = new StructuredFieldIntroducer();
        sfi.setSFTypeID(typeID);
        sfi.setFlagByte(java.util.EnumSet.noneOf(com.mgz.afp.enums.SFFlag.class));
        return sfi;
    }
}
