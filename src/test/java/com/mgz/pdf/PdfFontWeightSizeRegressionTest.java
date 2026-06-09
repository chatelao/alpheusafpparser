package com.mgz.pdf;

import com.mgz.afp.modca.MCF_MapCodedFont_Format1;
import com.mgz.afp.modca.MCF_MapCodedFont_Format1.MCF_RepeatingGroup;
import com.mgz.afp.modca.BPG_BeginPage;
import com.mgz.afp.enums.SFTypeID;
import com.mgz.afp.base.StructuredFieldIntroducer;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PdfFontWeightSizeRegressionTest {

    @Test
    public void testFontWeightAndSizeInPdfHandler() throws Exception {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PdfHandler handler = new PdfHandler(baos);

        // Begin Page
        BPG_BeginPage bpg = new BPG_BeginPage();
        bpg.setStructuredFieldIntroducer(createSfi(SFTypeID.BPG_BeginPage));
        handler.handle(bpg);

        // Map Coded Font Format 1 with various sizes and weights
        MCF_MapCodedFont_Format1 mcf = new MCF_MapCodedFont_Format1();

        // Helvetica Plain 12pt
        MCF_RepeatingGroup rg1 = new MCF_RepeatingGroup();
        rg1.setCodedFontLocalID((short) 1);
        rg1.setCodedFontName("X0H20012");
        mcf.addRepeatingGroup(rg1);

        // Helvetica Bold 14pt
        MCF_RepeatingGroup rg2 = new MCF_RepeatingGroup();
        rg2.setCodedFontLocalID((short) 2);
        rg2.setCodedFontName("X0H30014");
        mcf.addRepeatingGroup(rg2);

        // Character Set name fallback 8pt
        MCF_RepeatingGroup rg3 = new MCF_RepeatingGroup();
        rg3.setCodedFontLocalID((short) 3);
        rg3.setCodedFontName("UNKNOWN");
        rg3.setFontCharacterSetName("C0S20008");
        mcf.addRepeatingGroup(rg3);

        handler.handle(mcf);

        Map<Short, PdfHandler.FontResource> fontMap = handler.getFontMap();

        assertEquals("X0H20012", fontMap.get((short) 1).name());
        assertEquals(12.0f, fontMap.get((short) 1).size(), 0.01f);

        assertEquals("X0H30014", fontMap.get((short) 2).name());
        assertEquals(14.0f, fontMap.get((short) 2).size(), 0.01f);

        assertEquals("UNKNOWN", fontMap.get((short) 3).name());
        assertEquals(8.0f, fontMap.get((short) 3).size(), 0.01f);
    }

    private StructuredFieldIntroducer createSfi(SFTypeID typeID) {
        StructuredFieldIntroducer sfi = new StructuredFieldIntroducer();
        sfi.setSFTypeID(typeID);
        sfi.setFlagByte(java.util.EnumSet.noneOf(com.mgz.afp.enums.SFFlag.class));
        return sfi;
    }
}
