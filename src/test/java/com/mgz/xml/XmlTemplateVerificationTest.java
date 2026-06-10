package com.mgz.xml;

import com.mgz.afp.triplets.Triplet;
import com.mgz.afp.ptoca.controlSequence.PTOCAControlSequence;
import com.mgz.afp.enums.AFPOrientation;
import com.mgz.afp.enums.AFPUnitBase;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class XmlTemplateVerificationTest {

    @Test
    public void testTripletTemplates() throws Exception {
        // AttributeQualifier
        Triplet.AttributeQualifier aq = new Triplet.AttributeQualifier();
        aq.sequenceNumber = 123;
        aq.levelNumber = 456;
        verifySF(aq, "AttributeQualifier");

        // ResourceLocalIdentifier
        Triplet.ResourceLocalIdentifier rli = new Triplet.ResourceLocalIdentifier();
        rli.setResourceType(Triplet.ResourceLocalIdentifier.RLI_ResourceType.PageOverlay);
        rli.setResourceLocalID((short) 5);
        verifySF(rli, "ResourceLocalIdentifier");

        // CharacterRotation
        Triplet.CharacterRotation cr = new Triplet.CharacterRotation();
        cr.characterRotation = AFPOrientation.ori90;
        verifySF(cr, "CharacterRotation");

        // MeasurementUnits
        Triplet.MeasurementUnits mu = new Triplet.MeasurementUnits();
        mu.xUnitBase = AFPUnitBase.Inches10;
        mu.yUnitBase = AFPUnitBase.Inches10;
        mu.xUnitsPerUnitbase = 2400;
        mu.yUnitsPerUnitbase = 2400;
        verifySF(mu, "MeasurementUnits");
    }

    @Test
    public void testPtocaTemplates() throws Exception {
        // STO
        PTOCAControlSequence.STO_SetTextOrientation sto = new PTOCAControlSequence.STO_SetTextOrientation();
        sto.setxOrientation(AFPOrientation.ori0);
        sto.setyOrientation(AFPOrientation.ori90);
        verifySF(sto, "STO_SetTextOrientation");

        // SIA
        PTOCAControlSequence.SIA_SetIntercharacterAdjustment sia = new PTOCAControlSequence.SIA_SetIntercharacterAdjustment();
        sia.setAdjustment((short) 10);
        sia.setDirection(PTOCAControlSequence.SIA_SetIntercharacterAdjustment.SIA_Direction.NegativeIDirection);
        verifySF(sia, "SIA_SetIntercharacterAdjustment");
    }

    private void verifySF(Object obj, String rootName) throws Exception {
        ByteArrayOutputStream baosFast = new ByteArrayOutputStream();
        try (AfpJacksonXmlWriter writer = new AfpJacksonXmlWriter(baosFast, null, true, false)) {
            if (obj instanceof com.mgz.afp.base.StructuredField sf) {
                writer.handle(sf);
            } else if (obj instanceof Triplet t) {
                // We need to use reflection or a helper to call private writeTriplet in AfpJacksonXmlWriter
                // Or just test the template directly
                XmlTemplate template = null;
                Object[] values = null;
                if (t instanceof Triplet.AttributeQualifier aq) {
                    template = XmlTemplateRegistry.getTemplate("AQ");
                    values = new Object[]{aq.sequenceNumber, aq.levelNumber};
                } else if (t instanceof Triplet.ResourceLocalIdentifier rli) {
                    template = XmlTemplateRegistry.getTemplate("RLI");
                    values = new Object[]{rli.getResourceType(), (int)rli.getResourceLocalID()};
                } else if (t instanceof Triplet.CharacterRotation cr) {
                    template = XmlTemplateRegistry.getTemplate("CR");
                    values = new Object[]{cr.characterRotation};
                } else if (t instanceof Triplet.MeasurementUnits mu) {
                    template = XmlTemplateRegistry.getTemplate("MU");
                    values = new Object[]{mu.xUnitBase, mu.yUnitBase, (int)mu.xUnitsPerUnitbase, (int)mu.yUnitsPerUnitbase};
                }

                if (template != null) {
                    AfpXmlStreamWriter baseXsw = getBaseXsw(writer);
                    template.writeObjects(baseXsw, values);
                    baseXsw.flush();
                }
            } else if (obj instanceof PTOCAControlSequence cs) {
                 XmlTemplate template = null;
                 Object[] values = null;
                 if (cs instanceof PTOCAControlSequence.STO_SetTextOrientation sto) {
                     template = XmlTemplateRegistry.getTemplate("STO");
                     values = new Object[]{sto.getxOrientation(), sto.getyOrientation()};
                 } else if (cs instanceof PTOCAControlSequence.SIA_SetIntercharacterAdjustment sia) {
                     template = XmlTemplateRegistry.getTemplate("SIA");
                     values = new Object[]{(int)sia.getAdjustment(), sia.getDirection()};
                 }

                 if (template != null) {
                     AfpXmlStreamWriter baseXsw = getBaseXsw(writer);
                     template.writeObjects(baseXsw, values);
                     baseXsw.flush();
                 }
            }
        }
        String fastPathXml = baosFast.toString(StandardCharsets.UTF_8);

        String normalizedFastPath = normalizeXml(fastPathXml);

        // For these high-performance templates, we diverged from Jackson by using attributes instead of elements.
        // We verify that the output contains the expected attributes and values.
        if (rootName.equals("AttributeQualifier")) {
            assertTrue(normalizedFastPath.contains("sequenceNumber=\"123\""));
            assertTrue(normalizedFastPath.contains("levelNumber=\"456\""));
        } else if (rootName.equals("ResourceLocalIdentifier")) {
            assertTrue(normalizedFastPath.contains("resourceType=\"PageOverlay\""));
            assertTrue(normalizedFastPath.contains("resourceLocalID=\"5\""));
        } else if (rootName.equals("CharacterRotation")) {
            assertTrue(normalizedFastPath.contains("characterRotation=\"ori90\""));
        } else if (rootName.equals("MeasurementUnits")) {
            assertTrue(normalizedFastPath.contains("xUnitBase=\"Inches10\""));
            assertTrue(normalizedFastPath.contains("xUnitsPerUnitbase=\"2400\""));
        } else if (rootName.equals("STO_SetTextOrientation")) {
            assertTrue(normalizedFastPath.contains("xOrientation=\"ori0\""));
            assertTrue(normalizedFastPath.contains("yOrientation=\"ori90\""));
        } else if (rootName.equals("SIA_SetIntercharacterAdjustment")) {
            assertTrue(normalizedFastPath.contains("adjustment=\"10\""));
            assertTrue(normalizedFastPath.contains("direction=\"NegativeIDirection\""));
        }
    }

    private AfpXmlStreamWriter getBaseXsw(AfpJacksonXmlWriter writer) throws Exception {
        java.lang.reflect.Field field = AfpJacksonXmlWriter.class.getDeclaredField("baseXsw");
        field.setAccessible(true);
        return (AfpXmlStreamWriter) field.get(writer);
    }

    private String normalizeXml(String xml) {
        return xml.replaceAll("<beginSF>.*?</beginSF>", "")
                  .replaceAll("<endSF>.*?</endSF>", "")
                  .replaceAll("<shallow>.*?</shallow>", "")
                  .replaceAll("<structuredFieldIntroducer>.*?</structuredFieldIntroducer>", "")
                  .replaceAll("<padding>.*?</padding>", "")
                  .replaceAll("\\s", "");
    }
}
