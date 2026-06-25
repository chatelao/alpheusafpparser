package com.mgz.pdf;

import com.mgz.afp.enums.AFPUnitBase;
import com.mgz.afp.modca.BPG_BeginPage;
import com.mgz.afp.modca.EPG_EndPage;
import com.mgz.afp.modca.PGD_PageDescriptor;
import com.mgz.afp.ptoca.PTX_PresentationTextData;
import com.mgz.afp.ptoca.controlSequence.PTOCAControlSequence;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.lang.reflect.Field;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PdfRegressionTest {

    @Test
    public void testPageSizeChangeDoubleFontSize() throws Exception {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        PdfHandler handler = new PdfHandler(out);

        // Page 1: 1440 dpi
        BPG_BeginPage bpg1 = new BPG_BeginPage();
        bpg1.setName("PAGE1");
        handler.handle(bpg1);

        PGD_PageDescriptor pgd1 = new PGD_PageDescriptor();
        pgd1.setxUnitBase(AFPUnitBase.Inches10);
        pgd1.setyUnitBase(AFPUnitBase.Inches10);
        pgd1.setxUnitsPerUnitBase((short) 14400);
        pgd1.setyUnitsPerUnitBase((short) 14400);
        pgd1.setxSize(14400);
        pgd1.setySize(14400);
        handler.handle(pgd1);
        handler.handle(new EPG_EndPage());

        // Page 2: 720 dpi
        BPG_BeginPage bpg2 = new BPG_BeginPage();
        bpg2.setName("PAGE2");
        handler.handle(bpg2);

        PGD_PageDescriptor pgd2 = new PGD_PageDescriptor();
        pgd2.setxUnitBase(AFPUnitBase.Inches10);
        pgd2.setyUnitBase(AFPUnitBase.Inches10);
        pgd2.setxUnitsPerUnitBase((short) 7200);
        pgd2.setyUnitsPerUnitBase((short) 7200);
        pgd2.setxSize(7200);
        pgd2.setySize(7200);
        handler.handle(pgd2);

        // Check internal state
        Field defaultScaleYField = PdfHandler.class.getDeclaredField("defaultScaleY");
        defaultScaleYField.setAccessible(true);
        float scaleY = (float) defaultScaleYField.get(handler);
        assertEquals(0.1f, scaleY, 0.001f, "Scale should be 0.1 for 720 dpi");

        // Verify that isCanvasTransformed is true and it matches the 1440dpi scale from Page 1
        // (This is what I suspect is the bug: it should have been 0.1 but remains 0.05 from inherited BPG call)
        // Wait, PdfCanvas doesn't easily let us inspect the CTM.
        // But we can check if it was applied.
    }
}
