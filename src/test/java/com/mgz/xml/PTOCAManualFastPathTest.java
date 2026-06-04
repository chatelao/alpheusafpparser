package com.mgz.xml;

import com.mgz.afp.ptoca.BPT_BeginPresentationTextObject;
import com.mgz.afp.ptoca.EPT_EndPresentationTextObject;
import com.mgz.afp.ptoca.PTD_PresentationTextDataDescriptor_Format1;
import com.mgz.afp.ptoca.PTD_PresentationTextDataDescriptor_Format2;
import com.mgz.afp.enums.AFPUnitBase;
import com.mgz.afp.ptoca.controlSequence.PTOCAControlSequence;
import org.junit.jupiter.api.Test;
import java.io.ByteArrayOutputStream;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class PTOCAManualFastPathTest {

    @Test
    public void testBptFastPath() throws Exception {
        BPT_BeginPresentationTextObject bpt = new BPT_BeginPresentationTextObject();
        bpt.setName("BPTNAME");

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try (AfpJacksonXmlWriter writer = new AfpJacksonXmlWriter(baos, null, true)) {
            writer.handle(bpt);
        }
        String xml = baos.toString();
        assertTrue(xml.contains("<BPT_BeginPresentationTextObject>"));
        assertTrue(xml.contains("<name>BPTNAME</name>"));
    }

    @Test
    public void testEptFastPath() throws Exception {
        EPT_EndPresentationTextObject ept = new EPT_EndPresentationTextObject();
        ept.setName("EPTNAME");

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try (AfpJacksonXmlWriter writer = new AfpJacksonXmlWriter(baos, null, true)) {
            writer.handle(ept);
        }
        String xml = baos.toString();
        assertTrue(xml.contains("<EPT_EndPresentationTextObject>"));
        assertTrue(xml.contains("<name>EPTNAME</name>"));
    }

    @Test
    public void testPtdFormat1FastPath() throws Exception {
        PTD_PresentationTextDataDescriptor_Format1 ptd = new PTD_PresentationTextDataDescriptor_Format1();
        ptd.setxUnitBase(AFPUnitBase.Inches10);
        ptd.setyUnitBase(AFPUnitBase.Inches10);
        ptd.setxUnitsPerUnitBase((short) 2400);
        ptd.setyUnitsPerUnitBase((short) 2400);
        ptd.setxSize((short) 1000);
        ptd.setySize((short) 2000);

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try (AfpJacksonXmlWriter writer = new AfpJacksonXmlWriter(baos, null, true)) {
            writer.handle(ptd);
        }
        String xml = baos.toString();
        assertTrue(xml.contains("<PTD_PresentationTextDataDescriptor_Format1"));
        assertTrue(xml.contains("xUnitBase=\"Inches10\""));
        assertTrue(xml.contains("xUnitsPerUnitBase=\"2400\""));
        assertTrue(xml.contains("xSize=\"1000\""));
    }

    @Test
    public void testPtdFormat2FastPath() throws Exception {
        PTD_PresentationTextDataDescriptor_Format2 ptd = new PTD_PresentationTextDataDescriptor_Format2();
        ptd.setxUnitBase(AFPUnitBase.Inches10);
        ptd.setyUnitBase(AFPUnitBase.Inches10);
        ptd.setxUnitsPerUnitBase((short) 2400);
        ptd.setyUnitsPerUnitBase((short) 2400);
        ptd.setxSize(1000);
        ptd.setySize(2000);

        PTOCAControlSequence.AMI_AbsoluteMoveInline ami = new PTOCAControlSequence.AMI_AbsoluteMoveInline();
        ami.setDisplacement((short) 500);
        ptd.addControlSequence(ami);

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try (AfpJacksonXmlWriter writer = new AfpJacksonXmlWriter(baos, null, true)) {
            writer.handle(ptd);
        }
        String xml = baos.toString();
        assertTrue(xml.contains("<PTD_PresentationTextDataDescriptor_Format2"));
        assertTrue(xml.contains("xUnitBase=\"Inches10\""));
        assertTrue(xml.contains("<AMI_AbsoluteMoveInline displacement=\"500\"/>"));
    }
}
