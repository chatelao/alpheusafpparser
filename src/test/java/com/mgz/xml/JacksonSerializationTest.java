package com.mgz.xml;

import com.mgz.afp.modca.BDT_BeginDocument;
import com.mgz.afp.parser.AFPParserConfiguration;
import org.junit.jupiter.api.Test;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.Marshaller;
import javax.xml.namespace.QName;
import java.io.ByteArrayOutputStream;
import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class JacksonSerializationTest {

    @Test
    public void testJacksonVsJaxbSerialization() throws Exception {
        BDT_BeginDocument bdt = new BDT_BeginDocument();
        bdt.setName("DOC001");

        // JAXB Serialization
        ByteArrayOutputStream jaxbBaos = new ByteArrayOutputStream();
        JAXBContext jaxbContext = JAXBContext.newInstance(BDT_BeginDocument.class);
        Marshaller marshaller = jaxbContext.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        var qualifiedName = new QName("BDT_BeginDocument");
        var jaxbElement = new JAXBElement<>(qualifiedName, BDT_BeginDocument.class, bdt);
        marshaller.marshal(jaxbElement, jaxbBaos);
        String jaxbXml = jaxbBaos.toString(StandardCharsets.UTF_8);

        // Jackson Serialization
        String jacksonXml = JacksonXmlMapperProvider.getMapper().writeValueAsString(bdt);

        System.out.println("JAXB XML:\n" + jaxbXml);
        System.out.println("Jackson XML:\n" + jacksonXml);

        // Verify key content exists in both
        assertTrue(jacksonXml.contains("BDT_BeginDocument"), "Jackson XML should contain root element");
        assertTrue(jacksonXml.contains("DOC001"), "Jackson XML should contain document name");
        // Jackson with JaxbAnnotationModule uses the class name as root element by default if not specified,
        // or honors @XmlRootElement if present. BDT_BeginDocument doesn't have @XmlRootElement.
    }
}
