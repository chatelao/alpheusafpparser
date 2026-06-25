package com.mgz.pdf;

import com.mgz.afp.base.StructuredFieldIntroducer;
import com.mgz.afp.enums.SFTypeID;
import com.mgz.afp.modca.BPG_BeginPage;
import com.mgz.afp.modca.EPG_EndPage;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.lang.reflect.Field;

import static org.junit.jupiter.api.Assertions.assertFalse;

public class PdfMultiPageTransformationTest {

    @Test
    public void testIsCanvasTransformedResetOnNewPage() throws Exception {
        PdfHandler handler = new PdfHandler(new ByteArrayOutputStream());
        Field isCanvasTransformedField = PdfHandler.class.getDeclaredField("isCanvasTransformed");
        isCanvasTransformedField.setAccessible(true);

        // 1. Start Page 1
        BPG_BeginPage bpg1 = new BPG_BeginPage();
        bpg1.setStructuredFieldIntroducer(createSfi(SFTypeID.BPG_BeginPage));
        handler.handle(bpg1);

        // 2. Manually set isCanvasTransformed to true to simulate it being set
        // (either by applyTransformation or leaked from previous operations)
        isCanvasTransformedField.set(handler, true);

        EPG_EndPage epg1 = new EPG_EndPage();
        epg1.setStructuredFieldIntroducer(createSfi(SFTypeID.EPG_EndPage));
        handler.handle(epg1);

        // 3. Start Page 2
        BPG_BeginPage bpg2 = new BPG_BeginPage();
        bpg2.setStructuredFieldIntroducer(createSfi(SFTypeID.BPG_BeginPage));
        handler.handle(bpg2);

        // 4. Verify it was reset to false in BPG_BeginPage handling
        assertFalse((boolean) isCanvasTransformedField.get(handler),
            "isCanvasTransformed should be reset to false when a new page starts");
    }

    private StructuredFieldIntroducer createSfi(SFTypeID typeID) {
        StructuredFieldIntroducer sfi = new StructuredFieldIntroducer();
        sfi.setSFTypeID(typeID);
        sfi.setFlagByte(java.util.EnumSet.noneOf(com.mgz.afp.enums.SFFlag.class));
        return sfi;
    }
}
