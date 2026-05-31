package com.mgz.xml;

import com.mgz.afp.modca.TLE_TagLogicalElement;
import com.mgz.afp.modca.BDT_BeginDocument;
import com.mgz.afp.triplets.Triplet;
import com.mgz.afp.parser.AFPParserConfiguration;
import org.junit.jupiter.api.Test;
import java.io.ByteArrayOutputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class ManualStaxFastPathTest {

    @Test
    public void testTleManualSerialization() throws Exception {
        TLE_TagLogicalElement tle = new TLE_TagLogicalElement();

        List<Triplet> triplets = new ArrayList<>();

        Triplet.FullyQualifiedName fqn = new Triplet.FullyQualifiedName();
        fqn.setType(Triplet.GlobalID_Use.AttributeGID);
        fqn.setFormat(Triplet.GlobalID_Format.CharacterString);
        fqn.setNameAsString("TestAttribute");
        triplets.add(fqn);

        Triplet.AttributeValue av = new Triplet.AttributeValue();
        av.setAttributeValue("TestValue");
        triplets.add(av);

        tle.setTriplets(triplets);

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        AfpJacksonXmlWriter writer = new AfpJacksonXmlWriter(baos);
        writer.handle(tle);
        writer.close();

        String xml = baos.toString(StandardCharsets.UTF_8);
        System.out.println("TLE Manual XML:");
        System.out.println(xml);

        assertTrue(xml.contains("<TLE_TagLogicalElement>"), "XML should contain TLE element");
        assertTrue(xml.contains("<FullyQualifiedName>"), "XML should contain FQN");
        assertTrue(xml.contains("<AttributeValue>"), "XML should contain AttributeValue");
        assertFalse(xml.contains("<triplets>"), "XML should NOT contain triplets wrapper for TLE to match Jackson");
        // text element content depends on Triplet.getText() and StructuredFieldBaseTriplets.getText()
        assertTrue(xml.contains("<text>TestAttribute TestValue</text>"), "XML should contain merged text for TLE");
    }

    @Test
    public void testBdtManualSerialization() throws Exception {
        BDT_BeginDocument bdt = new BDT_BeginDocument();
        bdt.setName("MYDOC");

        List<Triplet> triplets = new ArrayList<>();
        Triplet.Comment c = new Triplet.Comment();
        c.comment = "Hello";
        triplets.add(c);
        bdt.setTriplets(triplets);

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        AfpJacksonXmlWriter writer = new AfpJacksonXmlWriter(baos);
        writer.handle(bdt);
        writer.close();

        String xml = baos.toString(StandardCharsets.UTF_8);
        System.out.println("BDT Manual XML:");
        System.out.println(xml);

        assertTrue(xml.contains("<BDT_BeginDocument>"), "XML should contain BDT element");
        assertTrue(xml.contains("<name>MYDOC</name>"), "XML should contain name element");
        assertTrue(xml.contains("<Comment>"), "XML should contain Comment triplet");
        // BDT.getText() implementation in StructuredFieldBaseNameAndTriplets:
        // return sb.length() > 0 ? UtilCharacterEncoding.sanitizeForXml(sb.toString()) : null;
        // where sb includes name and triplet texts.
        assertTrue(xml.contains("MYDOC") && xml.contains("Hello"), "XML should contain name and triplet text");
    }
}
