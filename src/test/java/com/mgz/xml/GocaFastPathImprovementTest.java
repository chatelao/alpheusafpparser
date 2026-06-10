package com.mgz.xml;

import com.mgz.afp.goca.GAD_DrawingOrder;
import com.mgz.afp.goca.GAD_GraphicsData;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class GocaFastPathImprovementTest {

    @Test
    public void testCompactPoints() throws Exception {
        GAD_GraphicsData gad = new GAD_GraphicsData();
        List<GAD_DrawingOrder> orders = new ArrayList<>();

        GAD_DrawingOrder.GCLINE_LineAtCurrentPosition gcline = new GAD_DrawingOrder.GCLINE_LineAtCurrentPosition();
        gcline.setPoints(List.of(
            new GAD_DrawingOrder.GOCA_Point((short) 100, (short) 200),
            new GAD_DrawingOrder.GOCA_Point((short) 110, (short) 210)
        ));
        orders.add(gcline);

        GAD_DrawingOrder.GCRLINE_RelativeLineAtCurrentPosition gcrline = new GAD_DrawingOrder.GCRLINE_RelativeLineAtCurrentPosition();
        gcrline.relativeOffsets = List.of(
            new GAD_DrawingOrder.GOCA_RelativePoint((byte) 10, (byte) 20),
            new GAD_DrawingOrder.GOCA_RelativePoint((byte) 5, (byte) -5)
        );
        orders.add(gcrline);

        gad.setDrawingOrders(orders);

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try (AfpJacksonXmlWriter writer = new AfpJacksonXmlWriter(baos, null, true, false)) {
            writer.handle(gad);
        }
        String xml = baos.toString(StandardCharsets.UTF_8);

        System.out.println("Generated GAD XML:\n" + xml);

        // Verify compact points for GCLINE
        assertTrue(xml.contains("<points>100 200 110 210</points>"), "Should contain compact points for GCLINE");
        // Verify compact relative offsets for GCRLINE
        assertTrue(xml.contains("<relativeOffsets>10 20 5 -5</relativeOffsets>"), "Should contain compact relative offsets for GCRLINE");
    }

    @Test
    public void testGbsegRecursion() throws Exception {
        GAD_DrawingOrder.GBSEG_BeginSegment gbseg = new GAD_DrawingOrder.GBSEG_BeginSegment();
        gbseg.setNameOfSegment("SEG1");

        List<GAD_DrawingOrder> nestedOrders = new ArrayList<>();
        GAD_DrawingOrder.GSCP_SetCurrentPosition gscp = new GAD_DrawingOrder.GSCP_SetCurrentPosition();
        gscp.setCoordinateX((short) 500);
        gscp.setCoordinateY((short) 600);
        nestedOrders.add(gscp);

        GAD_DrawingOrder.GCLINE_LineAtCurrentPosition gcline = new GAD_DrawingOrder.GCLINE_LineAtCurrentPosition();
        gcline.setPoints(List.of(new GAD_DrawingOrder.GOCA_Point((short) 700, (short) 800)));
        nestedOrders.add(gcline);

        gbseg.setDrawingOrders(nestedOrders);

        ByteArrayOutputStream baos = new ByteArrayOutputStream();

        // Wrap it in a GAD to trigger the fast-path as GBSEG is not a StructuredField
        GAD_GraphicsData gad = new GAD_GraphicsData();
        gad.setDrawingOrders(List.of(gbseg));

        baos = new ByteArrayOutputStream();
        try (AfpJacksonXmlWriter writer = new AfpJacksonXmlWriter(baos, null, true, false)) {
            writer.handle(gad);
        }
        String xml = baos.toString(StandardCharsets.UTF_8);

        System.out.println("Generated GBSEG XML:\n" + xml);

        assertTrue(xml.contains("<GBSEG_BeginSegment"), "Should contain GBSEG_BeginSegment");
        assertTrue(xml.contains("nameOfSegment=\"SEG1\""), "Should contain segment name attribute");
        assertTrue(xml.contains("<GSCP_SetCurrentPosition"), "Should contain nested GSCP");
        assertTrue(xml.contains("<points>700 800</points>"), "Should contain nested compact points");
    }
}
