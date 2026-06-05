package com.mgz.xml;

import com.mgz.afp.goca.GAD_DrawingOrder;
import com.mgz.afp.goca.GAD_GraphicsData;
import com.mgz.afp.triplets.Triplet;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Test to verify that StAX2 typed attribute access is used for optimized fast-paths.
 */
public class TypedAttributeAccessTest {

  @Test
  public void testTripletAttributes() throws Exception {
    ByteArrayOutputStream baos = new ByteArrayOutputStream();
    try (AfpJacksonXmlWriter writer = new AfpJacksonXmlWriter(baos, null, true, true)) {
      // Test AttributeQualifier
      Triplet.AttributeQualifier aq = new Triplet.AttributeQualifier();
      aq.sequenceNumber = 12345;
      aq.levelNumber = 67890;

      // We need a structured field to hold the triplet for writer.handle,
      // or we can test writer.writeTriplet if it was public.
      // Since it's private, I'll use a TLE which is on fast-path and uses writeTriplet.
      com.mgz.afp.modca.TLE_TagLogicalElement tle = new com.mgz.afp.modca.TLE_TagLogicalElement();
      List<Triplet> triplets = new ArrayList<>();
      triplets.add(aq);

      // Test ObjectByteOffset (Long)
      Triplet.ObjectByteOffset obo = new Triplet.ObjectByteOffset();
      obo.byteOffset = 12345678901L;
      obo.byteOffsetHighOrder = 9876543210L;
      triplets.add(obo);

      tle.setTriplets(triplets);
      writer.handle(tle);
    }

    String xml = baos.toString(StandardCharsets.UTF_8);

    // Verify AttributeQualifier uses attributes
    assertTrue(xml.contains("<AttributeQualifier"), "Should contain AttributeQualifier element");
    assertTrue(xml.contains("sequenceNumber=\"12345\""), "Should contain sequenceNumber attribute");
    assertTrue(xml.contains("levelNumber=\"67890\""), "Should contain levelNumber attribute");
    assertTrue(xml.contains("/>"), "Should be empty element");

    // Verify ObjectByteOffset uses attributes and handles Long correctly
    assertTrue(xml.contains("<ObjectByteOffset"), "Should contain ObjectByteOffset element");
    assertTrue(xml.contains("byteOffset=\"12345678901\""), "Should contain byteOffset attribute");
    assertTrue(xml.contains("byteOffsetHighOrder=\"9876543210\""), "Should contain byteOffsetHighOrder attribute");
  }

  @Test
  public void testGocaAttributes() throws Exception {
    ByteArrayOutputStream baos = new ByteArrayOutputStream();
    try (AfpJacksonXmlWriter writer = new AfpJacksonXmlWriter(baos, null, true, true)) {
      GAD_GraphicsData gad = new GAD_GraphicsData();
      List<GAD_DrawingOrder> orders = new ArrayList<>();

      // Test GSCC_SetCharacterCell
      GAD_DrawingOrder.GSCC_SetCharacterCell gscc = new GAD_DrawingOrder.GSCC_SetCharacterCell();
      gscc.setWidthOfCharacterCellIntegerPart((short) 100);
      gscc.setHeightOfCharacterCellIntegerPart((short) 200);
      orders.add(gscc);

      // Test GSCH_SetCharacterShear
      GAD_DrawingOrder.GSCH_SetCharacterShear gsch = new GAD_DrawingOrder.GSCH_SetCharacterShear();
      gsch.setDividendOfShearRatio((short) 10);
      gsch.setDivisorOfShearRatio((short) 20);
      orders.add(gsch);

      gad.setDrawingOrders(orders);
      writer.handle(gad);
    }

    String xml = baos.toString(StandardCharsets.UTF_8);

    // Verify GSCC uses attributes
    assertTrue(xml.contains("<GSCC_SetCharacterCell"), "Should contain GSCC element");
    assertTrue(xml.contains("widthOfCharacterCellIntegerPart=\"100\""), "Should contain width attribute");
    assertTrue(xml.contains("heightOfCharacterCellIntegerPart=\"200\""), "Should contain height attribute");

    // Verify GSCH uses attributes
    assertTrue(xml.contains("<GSCH_SetCharacterShear"), "Should contain GSCH element");
    assertTrue(xml.contains("dividendOfShearRatio=\"10\""), "Should contain dividend attribute");
    assertTrue(xml.contains("divisorOfShearRatio=\"20\""), "Should contain divisor attribute");
  }
}
