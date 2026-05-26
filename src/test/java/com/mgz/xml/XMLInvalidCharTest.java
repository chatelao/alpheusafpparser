package com.mgz.xml;

import com.mgz.afp.goca.GAD_DrawingOrder;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

public class XMLInvalidCharTest {

    @Test
    public void testGBSEGInvalidCharJackson() throws Exception {
        GAD_DrawingOrder.GBSEG_BeginSegment gbseg = new GAD_DrawingOrder.GBSEG_BeginSegment();
        // Simulate a name with a null character
        gbseg.setNameOfSegment("S\u0000EG");
        gbseg.setNameOfPredecessorSuccessorSegment("P\u0000RE");

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try (AfpJacksonXmlWriter writer = new AfpJacksonXmlWriter(baos)) {
            com.mgz.afp.goca.GAD_GraphicsData gad = new com.mgz.afp.goca.GAD_GraphicsData();
            gad.setDrawingOrders(java.util.List.of(gbseg));

            assertDoesNotThrow(() -> {
                writer.writeField(gad);
            });
        }
    }

    @Test
    public void testBDTNulCharJackson() throws Exception {
        com.mgz.afp.modca.BDT_BeginDocument bdt = new com.mgz.afp.modca.BDT_BeginDocument();
        bdt.setName("DOC\u0000001");
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try (AfpJacksonXmlWriter writer = new AfpJacksonXmlWriter(baos)) {
            assertDoesNotThrow(() -> {
                writer.writeField(bdt);
            });
        }
    }
}
