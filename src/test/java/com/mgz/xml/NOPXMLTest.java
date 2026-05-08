package com.mgz.xml;

import com.mgz.afp.modca.NOP_NoOperation;
import com.mgz.afp.parser.AFPParserConfiguration;
import org.junit.Test;

import javax.xml.bind.JAXBException;
import java.io.ByteArrayOutputStream;
import java.nio.charset.Charset;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;

public class NOPXMLTest {

    @Test
    public void testNOPXMLWithText() throws JAXBException {
        NOP_NoOperation nop = new NOP_NoOperation();
        String text = "Hello World";
        byte[] data = text.getBytes(Charset.forName("cp500"));
        nop.setData(data);

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        AFP2XMLWriter.writeXML(baos, nop, new AFPParserConfiguration());

        String xml = baos.toString();
        System.out.println(xml);
        assertTrue("XML should contain <text> node", xml.contains("<text>Hello World</text>"));
    }

    @Test
    public void testNOPXMLWithBinary() throws JAXBException {
        NOP_NoOperation nop = new NOP_NoOperation();
        byte[] data = new byte[] { 0x00, 0x01, 0x02, 0x03, 0x04, 0x05 };
        nop.setData(data);

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        AFP2XMLWriter.writeXML(baos, nop, new AFPParserConfiguration());

        String xml = baos.toString();
        System.out.println(xml);
        assertFalse("XML should NOT contain <text> node for binary data", xml.contains("<text>"));
    }
}
