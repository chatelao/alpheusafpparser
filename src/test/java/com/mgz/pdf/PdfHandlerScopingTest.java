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

import com.mgz.afp.enums.SFTypeID;
import com.mgz.afp.modca.BDT_BeginDocument;
import com.mgz.afp.modca.EDT_EndDocument;
import com.mgz.afp.modca.BAG_BeginActiveEnvironmentGroup;
import com.mgz.afp.modca.EAG_EndActiveEnvironmentGroup;
import com.mgz.afp.modca.MCF_MapCodedFont_Format1;
import com.mgz.afp.base.StructuredFieldIntroducer;
import org.junit.jupiter.api.Test;
import java.io.ByteArrayOutputStream;
import java.util.Map;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class PdfHandlerScopingTest {

    @Test
    public void testFontInheritanceAndScoping() throws Exception {
        PdfHandler handler = new PdfHandler(new ByteArrayOutputStream());

        // 1. Define font 1 at document level
        BDT_BeginDocument bdt = new BDT_BeginDocument();
        bdt.setStructuredFieldIntroducer(createSfi(SFTypeID.BDT_BeginDocument));
        handler.handle(bdt);

        MCF_MapCodedFont_Format1 mcfDoc = new MCF_MapCodedFont_Format1();
        MCF_MapCodedFont_Format1.MCF_RepeatingGroup rg1 = new MCF_MapCodedFont_Format1.MCF_RepeatingGroup();
        rg1.setCodedFontLocalID((short) 1);
        rg1.setCodedFontName("FONT1DOC");
        mcfDoc.addRepeatingGroup(rg1);
        handler.handle(mcfDoc);

        assertEquals("FONT1DOC", handler.getFontMap().get((short) 1));

        // 2. Define font 2 in an Active Environment Group (BAG)
        BAG_BeginActiveEnvironmentGroup bag = new BAG_BeginActiveEnvironmentGroup();
        bag.setStructuredFieldIntroducer(createSfi(SFTypeID.BAG_BeginActiveEnvironmentGroup));
        handler.handle(bag);

        MCF_MapCodedFont_Format1 mcfBag = new MCF_MapCodedFont_Format1();
        MCF_MapCodedFont_Format1.MCF_RepeatingGroup rg2 = new MCF_MapCodedFont_Format1.MCF_RepeatingGroup();
        rg2.setCodedFontLocalID((short) 2);
        rg2.setCodedFontName("FONT2BAG");
        mcfBag.addRepeatingGroup(rg2);
        handler.handle(mcfBag);

        // Verify font 2 is visible and font 1 is still inherited
        assertEquals("FONT2BAG", handler.getFontMap().get((short) 2));
        assertEquals("FONT1DOC", handler.getFontMap().get((short) 1));

        // 3. Close BAG, font 2 should disappear, font 1 remains
        EAG_EndActiveEnvironmentGroup eag = new EAG_EndActiveEnvironmentGroup();
        eag.setStructuredFieldIntroducer(createSfi(SFTypeID.EAG_EndActiveEnvironmentGroup));
        handler.handle(eag);

        assertNull(handler.getFontMap().get((short) 2));
        assertEquals("FONT1DOC", handler.getFontMap().get((short) 1));

        // 4. Override font 1 in another BAG
        handler.handle(bag);
        MCF_MapCodedFont_Format1 mcfBagOverride = new MCF_MapCodedFont_Format1();
        MCF_MapCodedFont_Format1.MCF_RepeatingGroup rg1Override = new MCF_MapCodedFont_Format1.MCF_RepeatingGroup();
        rg1Override.setCodedFontLocalID((short) 1);
        rg1Override.setCodedFontName("FONT1OVER");
        mcfBagOverride.addRepeatingGroup(rg1Override);
        handler.handle(mcfBagOverride);

        assertEquals("FONT1OVER", handler.getFontMap().get((short) 1));

        handler.handle(eag);
        assertEquals("FONT1DOC", handler.getFontMap().get((short) 1));

        // 5. Close document
        EDT_EndDocument edt = new EDT_EndDocument();
        edt.setStructuredFieldIntroducer(createSfi(SFTypeID.EDT_EndDocument));
        handler.handle(edt);

        assertNull(handler.getFontMap().get((short) 1));
    }

    private StructuredFieldIntroducer createSfi(SFTypeID typeID) {
        StructuredFieldIntroducer sfi = new StructuredFieldIntroducer();
        sfi.setSFTypeID(typeID);
        sfi.setFlagByte(java.util.EnumSet.noneOf(com.mgz.afp.enums.SFFlag.class));
        return sfi;
    }
}
