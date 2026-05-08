package com.mgz.xml;

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
        AFPParserConfiguration config = new AFPParserConfiguration();
        Charset charset = config.getAfpCharSet(); // Default is cp500
        nop.setData("Hello World".getBytes(charset));

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        AFP2XMLWriter.writeXML(baos, nop, config);

        System.out.println(baos.toString());
    }
}
