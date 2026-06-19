package com.mgz.xml;

import com.mgz.afp.modca.BPG_BeginPage;
import com.mgz.afp.modca.PGD_PageDescriptor;
import com.mgz.afp.modca.EPG_EndPage;
import java.io.ByteArrayOutputStream;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class PageNumberingTest {

    @Test
    public void testPageNumberingWithPGDOnly() throws Exception {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        try (AfpJacksonXmlWriter writer = new AfpJacksonXmlWriter(out)) {
            for (int i = 1; i <= 3; i++) {
                PGD_PageDescriptor pgd = new PGD_PageDescriptor();
                writer.handle(pgd);
                // In some documents, other fields might follow PGD
            }
        }

        String xml = out.toString();
        System.out.println("XML with PGD only:\n" + xml);
        assertTrue(xml.contains("page=\"1\""), "Should contain page 1");
        assertTrue(xml.contains("page=\"2\""), "Should contain page 2");
        assertTrue(xml.contains("page=\"3\""), "Should contain page 3");
    }

    @Test
    public void testPageNumberingWithBPGAndPGD() throws Exception {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        try (AfpJacksonXmlWriter writer = new AfpJacksonXmlWriter(out)) {
            for (int i = 1; i <= 3; i++) {
                BPG_BeginPage bpg = new BPG_BeginPage();
                writer.handle(bpg);

                PGD_PageDescriptor pgd = new PGD_PageDescriptor();
                writer.handle(pgd);

                EPG_EndPage epg = new EPG_EndPage();
                writer.handle(epg);
            }
        }

        String xml = out.toString();
        System.out.println("XML with BPG and PGD:\n" + xml);
        assertTrue(xml.contains("<BPG_BeginPage page=\"1\""), "BPG 1 should have page 1");
        assertTrue(xml.contains("<PGD_PageDescriptor page=\"1\""), "PGD 1 should have page 1");
        assertTrue(xml.contains("<BPG_BeginPage page=\"2\""), "BPG 2 should have page 2");
        assertTrue(xml.contains("<PGD_PageDescriptor page=\"2\""), "PGD 2 should have page 2");
        assertTrue(xml.contains("<BPG_BeginPage page=\"3\""), "BPG 3 should have page 3");
        assertTrue(xml.contains("<PGD_PageDescriptor page=\"3\""), "PGD 3 should have page 3");
    }
}
