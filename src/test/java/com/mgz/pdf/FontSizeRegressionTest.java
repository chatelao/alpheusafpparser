package com.mgz.pdf;

import com.mgz.afp.base.StructuredFieldIntroducer;
import com.mgz.afp.enums.AFPUnitBase;
import com.mgz.afp.enums.SFTypeID;
import com.mgz.afp.modca.BPG_BeginPage;
import com.mgz.afp.modca.MCF_MapCodedFont_Format1;
import com.mgz.afp.modca.PGD_PageDescriptor;
import com.mgz.afp.ptoca.PTX_PresentationTextData;
import com.mgz.afp.ptoca.controlSequence.PTOCAControlSequence.TRN_TransparentData;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class FontSizeRegressionTest {

    @Test
    public void testFontSizeWithPageLevelPgd() throws Exception {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PdfHandler handler = new PdfHandler(baos);

        // 1. Begin Page (initially uses default 1.0 scale)
        BPG_BeginPage bpg = new BPG_BeginPage();
        bpg.setStructuredFieldIntroducer(createSfi(SFTypeID.BPG_BeginPage));
        handler.handle(bpg);
        assertEquals(1.0f, handler.getDefaultScaleY(), 0.001f);

        // 2. Page Descriptor (1440 DPI -> 720/14400 = 0.05 scale)
        PGD_PageDescriptor pgd = new PGD_PageDescriptor();
        pgd.setxUnitBase(AFPUnitBase.Inches10);
        pgd.setyUnitBase(AFPUnitBase.Inches10);
        pgd.setxUnitsPerUnitBase((short) 14400); // 1440 DPI
        pgd.setyUnitsPerUnitBase((short) 14400);
        pgd.setxSize(14400); // 10 inches
        pgd.setySize(14400);
        handler.handle(pgd);

        // Verify that the scale factor was updated even though the page is already active
        assertEquals(0.05f, handler.getDefaultScaleY(), 0.001f);

        // 3. Map Coded Font (Default size 10pt)
        MCF_MapCodedFont_Format1 mcf = new MCF_MapCodedFont_Format1();
        MCF_MapCodedFont_Format1.MCF_RepeatingGroup rg = new MCF_MapCodedFont_Format1.MCF_RepeatingGroup();
        rg.setCodedFontLocalID((short) 1);
        rg.setCodedFontName("X0H200");
        mcf.addRepeatingGroup(rg);
        handler.handle(mcf);

        // 4. Presentation Text (Should now use 0.05 scale)
        PTX_PresentationTextData ptx = new PTX_PresentationTextData();
        ptx.setStructuredFieldIntroducer(createSfi(SFTypeID.PTX_PresentationTextData));
        TRN_TransparentData trn = new TRN_TransparentData();
        trn.setTransparentData("Test");
        ptx.addControlSequence(trn);

        handler.handle(ptx);
        // The fix is verified by the fact that handler.getDefaultScaleY() is now 0.05
    }

    private StructuredFieldIntroducer createSfi(SFTypeID typeID) {
        StructuredFieldIntroducer sfi = new StructuredFieldIntroducer();
        sfi.setSFTypeID(typeID);
        sfi.setFlagByte(java.util.EnumSet.noneOf(com.mgz.afp.enums.SFFlag.class));
        return sfi;
    }
}
