package com.mgz.xml;

import com.mgz.afp.base.StructuredField;
import com.mgz.afp.parser.AFPParser;
import com.mgz.afp.parser.AFPParserConfiguration;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.InputStream;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class Afp2XmlComparisonTest {

    @ParameterizedTest
    @ValueSource(strings = {
            "src/test/resources/afp/afp-goca-reference-03/Chapter_4.afp",
            "src/test/resources/afp/afp-goca-reference-03/Chapter_5.afp",
            "src/test/resources/afp/afp-goca-reference-03/Chapter_6.afp",
            "src/test/resources/afp/cmoca-reference-02/Chapter_1.afp",
            "src/test/resources/afp/cmoca-reference-02/Chapter_4.afp"
    })
    public void testJacksonStreamingOutput(String afpPath) throws Exception {
        byte[] jacksonXml = convertWithJacksonStreaming(afpPath, null);
        String xml = new String(jacksonXml, "UTF-8");

        assertNotNull(xml);
        assertTrue(xml.contains("<AFPDocument"), "XML should contain AFPDocument root for " + afpPath);
        assertTrue(xml.endsWith("</AFPDocument>"), "XML should end with AFPDocument root for " + afpPath);
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "src/test/resources/afp/afp-goca-reference-03/Chapter_4.afp",
            "src/test/resources/afp/afp-goca-reference-03/Chapter_5.afp",
            "src/test/resources/afp/afp-goca-reference-03/Chapter_6.afp",
            "src/test/resources/afp/cmoca-reference-02/Chapter_1.afp",
            "src/test/resources/afp/cmoca-reference-02/Chapter_4.afp"
    })
    public void testJacksonStreamingOutputWithXPath(String afpPath) throws Exception {
        String xpath = "//BDT_BeginDocument";
        byte[] jacksonXml = convertWithJacksonStreaming(afpPath, xpath);
        String xml = new String(jacksonXml, "UTF-8");

        assertNotNull(xml);
        assertTrue(xml.contains("<BDT_BeginDocument"), "XML should contain BDT_BeginDocument for " + afpPath);
    }

    private byte[] convertWithJacksonStreaming(String afpPath, String xpath) throws Exception {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try (InputStream is = new BufferedInputStream(new FileInputStream(afpPath));
             AfpJacksonXmlWriter writer = new AfpJacksonXmlWriter(baos, xpath)) {
            AFPParserConfiguration config = new AFPParserConfiguration();
            config.setInputStream(is);
            AFPParser parser = new AFPParser(config);
            StructuredField sf;
            while ((sf = parser.parseNextSF()) != null) {
                writer.writeField(sf);
                sf.release();
            }
        }
        return baos.toByteArray();
    }
}
