package com.mgz.xml;

import com.mgz.afp.ioca.IDD_SelfDefiningField;
import com.mgz.afp.ptoca.controlSequence.PTOCAControlSequence;
import com.mgz.afp.enums.AFPColorSpace;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class AdditionalFastPathTest {

    @Test
    public void testIocaSdfFastPaths() throws Exception {
        IDD_SelfDefiningField.IDEStructure ides = new IDD_SelfDefiningField.IDEStructure();
        ides.setFlags((byte) 0x80);
        ides.setColorSpace(AFPColorSpace.RGB);
        ides.setComponentSizes(new byte[]{8, 8, 8});
        verifySDF(ides, "IDEStructure");

        IDD_SelfDefiningField.UnknownSelfDefiningField usdf = new IDD_SelfDefiningField.UnknownSelfDefiningField();
        usdf.setUnknownFieldType((short) 0x99);
        usdf.setData(new byte[]{1, 2, 3});
        verifySDF(usdf, "UnknownSelfDefiningField");
    }

    @Test
    public void testPtocaUndefinedFastPath() throws Exception {
        PTOCAControlSequence.Undefined u = new PTOCAControlSequence.Undefined();
        u.setUndefinedData(new byte[]{0x41, 0x42, 0x43});
        // We need to set text if we want it to match Jackson exactly,
        // but decodeAFP usually does it.
        // For testing we can just set it.
        // u.text = "ABC"; // It is package private or has no setter?
        // In PTOCAControlSequence.java: String text; (package private)
        // Wait, I didn't add a setter for text.
        verifyCS(u, "Undefined");
    }

    private void verifySDF(IDD_SelfDefiningField sdf, String rootName) throws Exception {
        ByteArrayOutputStream baosFast = new ByteArrayOutputStream();
        try (AfpJacksonXmlWriter writer = new AfpJacksonXmlWriter(baosFast, null, true, false)) {
            JacksonXmlMapperProvider.getCachedWriter(sdf.getClass(), true, true)
                .writeValue(writer.getBaseFragmentGenerator(), sdf);
        }
        String fastPathXml = baosFast.toString(StandardCharsets.UTF_8);
        String jacksonXml = JacksonXmlMapperProvider.getFragmentMapper().writeValueAsString(sdf);

        compareXml(jacksonXml, fastPathXml, rootName);
    }

    private void verifyCS(PTOCAControlSequence cs, String rootName) throws Exception {
        ByteArrayOutputStream baosFast = new ByteArrayOutputStream();
        try (AfpJacksonXmlWriter writer = new AfpJacksonXmlWriter(baosFast, null, true, false)) {
            JacksonXmlMapperProvider.getCachedWriter(cs.getClass(), true, true)
                .writeValue(writer.getBaseFragmentGenerator(), cs);
        }
        String fastPathXml = baosFast.toString(StandardCharsets.UTF_8);
        String jacksonXml = JacksonXmlMapperProvider.getFragmentMapper().writeValueAsString(cs);

        compareXml(jacksonXml, fastPathXml, rootName);
    }

    private void compareXml(String jacksonXml, String fastPathXml, String rootName) {
        String normalizedJackson = normalizeXml(jacksonXml);
        String normalizedFastPath = normalizeXml(fastPathXml);

        if (!normalizedFastPath.contains(normalizedJackson)) {
            System.out.println("RootName: " + rootName);
            System.out.println("Jackson (Normalized): " + normalizedJackson);
            System.out.println("FastPath (Normalized): " + normalizedFastPath);
        }
        assertTrue(normalizedFastPath.contains(normalizedJackson), "Normalized FastPath XML does not contain expected Normalized Jackson XML for " + rootName);
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
