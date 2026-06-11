package com.mgz.xml;

import com.mgz.afp.triplets.Triplet;
import com.mgz.afp.ptoca.controlSequence.PTOCAControlSequence;
import com.mgz.afp.enums.AFPOrientation;
import com.mgz.afp.enums.AFPUnitBase;
import com.mgz.afp.enums.AFPColorValue;
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

        // MappingOption
        Triplet.MappingOption mo = new Triplet.MappingOption();
        mo.decodeAFP(new byte[] {0x03, 0x04, 0x20}, 0, 3, null);
        verifySF(mo, "MappingOption");

        // ObjectAreaSize
        Triplet.ObjectAreaSize oas = new Triplet.ObjectAreaSize();
        oas.decodeAFP(new byte[] {0x09, 0x4C, 0x02, 0x00, 0x04, (byte)0xB0, 0x00, 0x06, (byte)0x40}, 0, 9, null);
        verifySF(oas, "ObjectAreaSize");

        // CodedGraphicCharacterSetGlobalID
        Triplet.CodedGraphicCharacterSetGlobalID cgcs = new Triplet.CodedGraphicCharacterSetGlobalID();
        cgcs.decodeAFP(new byte[] {0x06, 0x01, 0x04, (byte)0xB0, 0x01, (byte)0xF4}, 0, 6, null);
        verifySF(cgcs, "CodedGraphicCharacterSetGlobalID");

        // ResourceObjectType
        Triplet.ResourceObjectType rot = new Triplet.ResourceObjectType();
        rot.decodeAFP(new byte[] {0x04, 0x21, 0x06, 0x00}, 0, 4, null);
        verifySF(rot, "ResourceObjectType");
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

        // STC
        PTOCAControlSequence.STC_SetTextColor stc = new PTOCAControlSequence.STC_SetTextColor();
        stc.setForegroundColor(AFPColorValue.Blue_0x01);
        stc.setPrecision(PTOCAControlSequence.STC_SetTextColor.STC_Precision.IfSpecifiedColorNotSupported_SubstitutColorOrDefaul0xFF07);
        verifySF(stc, "STC_SetTextColor");

        // USC
        PTOCAControlSequence.USC_Underscore usc = new PTOCAControlSequence.USC_Underscore();
        usc.setBypassFlag(PTOCAControlSequence.PTOCA_BypassFlag.BypassRelativeMoveInline);
        verifySF(usc, "USC_Underscore");
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
                } else if (t instanceof Triplet.MappingOption mo) {
                    template = XmlTemplateRegistry.getTemplate("MO");
                    values = new Object[]{mo.getDataObjecMapingOption()};
                } else if (t instanceof Triplet.ObjectAreaSize oas) {
                    template = XmlTemplateRegistry.getTemplate("OAS");
                    values = new Object[]{(int)oas.sizeType_0x02, oas.xSize, oas.ySize};
                } else if (t instanceof Triplet.CodedGraphicCharacterSetGlobalID cgcs) {
                    template = XmlTemplateRegistry.getTemplate("CGCS");
                    values = new Object[]{cgcs.getGraphicCharacterSetGlobalID(), cgcs.getCodePageGlobalID_codedCharacterSetID()};
                } else if (t instanceof Triplet.ResourceObjectType rot) {
                    template = XmlTemplateRegistry.getTemplate("ROT");
                    values = new Object[]{rot.objectType};
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
                 } else if (cs instanceof PTOCAControlSequence.STC_SetTextColor stc) {
                     template = XmlTemplateRegistry.getTemplate("STC");
                     values = new Object[]{stc.getForegroundColor(), stc.getPrecision()};
                 } else if (cs instanceof PTOCAControlSequence.USC_Underscore usc) {
                     template = XmlTemplateRegistry.getTemplate("USC");
                     values = new Object[]{usc.getBypassFlag()};
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
        } else if (rootName.equals("MappingOption")) {
            assertTrue(normalizedFastPath.contains("dataObjecMapingOption=\"ScaleToFit\""));
        } else if (rootName.equals("ObjectAreaSize")) {
            assertTrue(normalizedFastPath.contains("sizeType_0x02=\"2\""));
            assertTrue(normalizedFastPath.contains("xSize=\"1200\""));
            assertTrue(normalizedFastPath.contains("ySize=\"1600\""));
        } else if (rootName.equals("CodedGraphicCharacterSetGlobalID")) {
            assertTrue(normalizedFastPath.contains("graphicCharacterSetGlobalID=\"1200\""));
            assertTrue(normalizedFastPath.contains("codePageGlobalID_codedCharacterSetID=\"500\""));
        } else if (rootName.equals("ResourceObjectType")) {
            assertTrue(normalizedFastPath.contains("objectType=\"ImageObject_IOCA\""));
        } else if (rootName.equals("STO_SetTextOrientation")) {
            assertTrue(normalizedFastPath.contains("xOrientation=\"ori0\""));
            assertTrue(normalizedFastPath.contains("yOrientation=\"ori90\""));
        } else if (rootName.equals("SIA_SetIntercharacterAdjustment")) {
            assertTrue(normalizedFastPath.contains("adjustment=\"10\""));
            assertTrue(normalizedFastPath.contains("direction=\"NegativeIDirection\""));
        } else if (rootName.equals("STC_SetTextColor")) {
            assertTrue(normalizedFastPath.contains("foregroundColor=\"Blue_0x01\""));
            assertTrue(normalizedFastPath.contains("precision=\"IfSpecifiedColorNotSupported_SubstitutColorOrDefaul0xFF07\""));
        } else if (rootName.equals("USC_Underscore")) {
            assertTrue(normalizedFastPath.contains("bypassFlag=\"BypassRelativeMoveInline\""));
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
