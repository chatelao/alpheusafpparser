package com.mgz.xml;

import com.mgz.afp.ptoca.PTX_PresentationTextData;
import com.mgz.afp.ptoca.controlSequence.PTOCAControlSequence;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class PTOCAOptimizationTest {

    @Test
    public void testPTOCAOptimization() throws Exception {
        PTX_PresentationTextData ptx = new PTX_PresentationTextData();

        PTOCAControlSequence.AMI_AbsoluteMoveInline ami = new PTOCAControlSequence.AMI_AbsoluteMoveInline();
        ami.setDisplacement((short) 123);
        ptx.addControlSequence(ami);

        PTOCAControlSequence.AMB_AbsoluteMoveBaseline amb = new PTOCAControlSequence.AMB_AbsoluteMoveBaseline();
        amb.setDisplacement((short) 456);
        ptx.addControlSequence(amb);

        PTOCAControlSequence.SCFL_SetCodedFontLocal scfl = new PTOCAControlSequence.SCFL_SetCodedFontLocal();
        scfl.setCodedFontLocalID((short) 1);
        ptx.addControlSequence(scfl);

        PTOCAControlSequence.SVI_SetVariableSpaceCharacterIncrement svi = new PTOCAControlSequence.SVI_SetVariableSpaceCharacterIncrement();
        svi.setIncrement((short) 5);
        ptx.addControlSequence(svi);

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try (AfpJacksonXmlWriter writer = new AfpJacksonXmlWriter(baos)) {
            writer.handle(ptx);
        }

        String xml = baos.toString();

        // Verify compact attribute-based format
        assertTrue(xml.contains("<AMI_AbsoluteMoveInline displacement=\"123\"/>"), "XML should contain AMI as empty element with attribute");
        assertTrue(xml.contains("<AMB_AbsoluteMoveBaseline displacement=\"456\"/>"), "XML should contain AMB as empty element with attribute");
        assertTrue(xml.contains("<SCFL_SetCodedFontLocal codedFontLocalID=\"1\"/>"), "XML should contain SCFL as empty element with attribute");
        assertTrue(xml.contains("<SVI_SetVariableSpaceCharacterIncrement increment=\"5\"/>"), "XML should contain SVI as empty element with attribute");
    }
}
