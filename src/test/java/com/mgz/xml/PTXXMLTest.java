package com.mgz.xml;

import com.mgz.afp.ptoca.PTX_PresentationTextData;
import com.mgz.afp.ptoca.controlSequence.PTOCAControlSequence;
import com.mgz.afp.parser.AFPParserConfiguration;
import org.junit.Test;

import javax.xml.bind.JAXBException;
import java.io.ByteArrayOutputStream;
import java.nio.charset.Charset;

public class PTXXMLTest {

    @Test
    public void testPTXXML() throws JAXBException {
        PTX_PresentationTextData ptx = new PTX_PresentationTextData();
        AFPParserConfiguration config = new AFPParserConfiguration();
        Charset charset = config.getAfpCharSet();

        PTOCAControlSequence.TRN_TransparentData trn = new PTOCAControlSequence.TRN_TransparentData();
        trn.setTransparentData("Hello PTX");
        trn.setTransparentDataEBCDIC("Hello PTX".getBytes(charset));
        ptx.addControlSequence(trn);

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        AFP2XMLWriter.writeXML(baos, ptx, config);

        System.out.println(baos.toString());
    }
}
