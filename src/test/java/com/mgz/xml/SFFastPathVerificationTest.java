package com.mgz.xml;

import com.mgz.afp.modca.MCC_MediumCopyCount;
import com.mgz.afp.modca.MCF_MapCodedFont_Format1;
import com.mgz.afp.enums.AFPOrientation;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class SFFastPathVerificationTest {

    @Test
    public void testSFFastPaths() throws Exception {
        verifySF(createMCC(), "MCC_MediumCopyCount");
        verifySF(createMCF1(), "MCF_MapCodedFont_Format1");
    }

    private void verifySF(com.mgz.afp.base.StructuredField sf, String rootName) throws Exception {
        ByteArrayOutputStream baosFast = new ByteArrayOutputStream();
        try (AfpJacksonXmlWriter writer = new AfpJacksonXmlWriter(baosFast, null, true, false)) {
            writer.handle(sf);
        }
        String fastPathXml = baosFast.toString(StandardCharsets.UTF_8);

        String jacksonXml = JacksonXmlMapperProvider.getFragmentMapper().writeValueAsString(sf);

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
        // Remove metadata tags which are in Jackson but not in our fast-path
        return xml.replaceAll("<beginSF>.*?</beginSF>", "")
                  .replaceAll("<endSF>.*?</endSF>", "")
                  .replaceAll("<shallow>.*?</shallow>", "")
                  .replaceAll("<structuredFieldIntroducer>.*?</structuredFieldIntroducer>", "")
                  .replaceAll("<padding>.*?</padding>", "")
                  .replaceAll("\\s", ""); // Remove whitespace for comparison
    }

    private MCC_MediumCopyCount createMCC() {
        MCC_MediumCopyCount mcc = new MCC_MediumCopyCount();
        mcc.addRepeatingGroup(new MCC_MediumCopyCount.MCC_RepeatingGroup((short)1, (short)5, (byte)0, (byte)1));
        mcc.addRepeatingGroup(new MCC_MediumCopyCount.MCC_RepeatingGroup((short)6, (short)10, (byte)0, (byte)2));
        return mcc;
    }

    private MCF_MapCodedFont_Format1 createMCF1() {
        MCF_MapCodedFont_Format1 mcf1 = new MCF_MapCodedFont_Format1();
        mcf1.setLengthOfRepeatingGroup((short)30);
        mcf1.setReserved1_3(new byte[]{0x01, 0x02, 0x03});

        MCF_MapCodedFont_Format1.MCF_RepeatingGroup rg = new MCF_MapCodedFont_Format1.MCF_RepeatingGroup();
        rg.setCodedFontLocalID((short)1);
        rg.setReserved1((byte)0);
        rg.setCodedFontSectionID((short)0);
        rg.setReserved3((byte)0);
        rg.setCodedFontName("X0TEST");
        rg.setCodePageName("T1TEST");
        rg.setFontCharacterSetName("C0TEST");
        rg.setCharacterRotation(AFPOrientation.ori0);

        mcf1.addRepeatingGroup(rg);
        return mcf1;
    }
}
