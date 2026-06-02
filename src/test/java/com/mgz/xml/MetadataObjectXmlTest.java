package com.mgz.xml;

import com.mgz.afp.moca.MetadataObject;
import com.mgz.afp.modca.OCD_ObjectContainerData;
import org.junit.jupiter.api.Test;
import java.io.ByteArrayOutputStream;
import java.nio.charset.StandardCharsets;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class MetadataObjectXmlTest {

    @Test
    public void testMetadataObjectSerialization() throws Exception {
        // [MOCA-4-004] [MOCA-4-006] [MOCA-4-007] [MOCA-4-008] [MOCA-4-010] [MOCA-4-014] [MOCA-4-015] [MOCA-4-018]
        MetadataObject mo = new MetadataObject();
        mo.setMoLength(100);
        mo.setHeaderLength(46);
        mo.setMoType("DES");
        mo.setMoFormat("AFPT");
        mo.setMoCompression("NONE");
        mo.setMoNameLength(8);
        mo.setMoName("TESTNAME");
        mo.setMoData("HELLO".getBytes(StandardCharsets.UTF_16BE));

        OCD_ObjectContainerData ocd = new OCD_ObjectContainerData();
        ocd.setMetadataObject(mo);

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        AfpJacksonXmlWriter writer = new AfpJacksonXmlWriter(baos);
        writer.handle(ocd);
        writer.close();

        String xml = baos.toString(StandardCharsets.UTF_8);
        System.out.println("OCD with MetadataObject XML:");
        System.out.println(xml);

        assertTrue(xml.contains("<OCD_ObjectContainerData>"), "Should contain OCD root");
        assertTrue(xml.contains("<MetadataObject>"), "Should contain MetadataObject element");
        assertTrue(xml.contains("<MOLength>100</MOLength>"), "Should contain MOLength");
        assertTrue(xml.contains("<HeaderLength>46</HeaderLength>"), "Should contain HeaderLength");
        assertTrue(xml.contains("<MOType>DES</MOType>"), "Should contain MOType");
        assertTrue(xml.contains("<MOFormat>AFPT</MOFormat>"), "Should contain MOFormat");
        assertTrue(xml.contains("<MOCompression>NONE</MOCompression>"), "Should contain MOCompression");
        assertTrue(xml.contains("<MONameLength>8</MONameLength>"), "Should contain MONameLength");
        assertTrue(xml.contains("<MOName>TESTNAME</MOName>"), "Should contain MOName");
        assertTrue(xml.contains("<MOData>"), "Should contain MOData");
        assertTrue(xml.contains("<MODataText>HELLO</MODataText>"), "Should contain MODataText");
    }
}
