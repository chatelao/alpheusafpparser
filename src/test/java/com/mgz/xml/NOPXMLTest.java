package com.mgz.xml;

import com.mgz.afp.base.StructuredFieldIntroducer;
import com.mgz.afp.enums.SFTypeID;
import com.mgz.afp.modca.NOP_NoOperation;
import com.mgz.afp.parser.AFPParserConfiguration;
import org.junit.Test;

import javax.xml.bind.JAXBException;
import java.io.ByteArrayOutputStream;
import java.nio.charset.Charset;

public class NOPXMLTest {

    @Test
    public void testNOPXML() throws JAXBException {
        NOP_NoOperation nop = new NOP_NoOperation();
        StructuredFieldIntroducer sfi = new StructuredFieldIntroducer();
        sfi.setSFTypeID(SFTypeID.NOP_NoOperation);
        nop.setStructuredFieldIntroducer(sfi);

        AFPParserConfiguration config = new AFPParserConfiguration();
        Charset charset = config.getAfpCharSet(); // Default is cp500

        // Manually set charset as it would be during decodeAFP
        nop.setData("Hello World".getBytes(charset));
        try {
            java.lang.reflect.Field charsetField = com.mgz.afp.base.StructuredFieldBaseData.class.getDeclaredField("charset");
            charsetField.setAccessible(true);
            charsetField.set(nop, charset);
        } catch (Exception e) {
            e.printStackTrace();
        }

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        AFP2XMLWriter.writeXML(baos, nop, config);

        String xml = baos.toString();
        System.out.println(xml);

        if (!xml.contains("<ebcdic-unicode>Hello World</ebcdic-unicode>")) {
            throw new RuntimeException("XML does not contain expected ebcdic-unicode tag");
        }
    }
}
