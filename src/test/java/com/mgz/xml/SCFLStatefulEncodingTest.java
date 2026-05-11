package com.mgz.xml;

import com.mgz.afp.base.StructuredField;
import com.mgz.afp.parser.AFPParser;
import com.mgz.afp.parser.AFPParserConfiguration;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class SCFLStatefulEncodingTest {

    @Test
    public void testSCFLSwitchesCharsetInPTX() throws Exception {
        // MCF1 mapping LID 1 to T1V10273 (German) and LID 2 to T1V10500 (International)
        // SFI: 5A 00 3F D3 AB 8A 00 00 00 (Length 63+1=64, SF Type MCF1)
        // Payload:
        // 1C (RG Len 28)
        // 00 00 00 (Reserved)
        // RG1: 01 00 00 00 (LID 1)
        //      FF FF 00 00 00 00 00 00 (Coded Font Null Name)
        //      E3 F1 E5 F1 F0 F2 F7 F3 (T1V10273)
        //      FF FF 00 00 00 00 00 00 (Charset Null Name)
        // RG2: 02 00 00 00 (LID 2)
        //      FF FF 00 00 00 00 00 00 (Coded Font Null Name)
        //      E3 F1 E5 F1 F0 F5 F0 F0 (T1V10500)
        //      FF FF 00 00 00 00 00 00 (Charset Null Name)
        byte[] mcfBytes = new byte[] {
            0x5A, 0x00, 0x3F, (byte) 0xD3, (byte) 0xAB, (byte) 0x8A, 0x00, 0x00, 0x00,
            0x1C, 0x00, 0x00, 0x00,
            0x01, 0x00, 0x00, 0x00,
            (byte) 0xFF, (byte) 0xFF, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00,
            (byte) 0xE3, (byte) 0xF1, (byte) 0xE5, (byte) 0xF1, (byte) 0xF0, (byte) 0xF2, (byte) 0xF7, (byte) 0xF3,
            (byte) 0xFF, (byte) 0xFF, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00,
            0x02, 0x00, 0x00, 0x00,
            (byte) 0xFF, (byte) 0xFF, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00,
            (byte) 0xE3, (byte) 0xF1, (byte) 0xE5, (byte) 0xF1, (byte) 0xF0, (byte) 0xF5, (byte) 0xF0, (byte) 0xF0,
            (byte) 0xFF, (byte) 0xFF, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00
        };

        // PTX with SCFL(1) + TRN(0xC0) + SCFL(2) + TRN(0xC0)
        // SFI: 5A 00 1D D3 EE 9B 00 00 00 (Length 29+1=30)
        // Payload:
        // 2B D3 03 F0 01 (SCFL LID 1)
        // 2B D3 03 DA C0 (TRN 0xC0)
        // 2B D3 03 F0 02 (SCFL LID 2)
        // 2B D3 03 DA C0 (TRN 0xC0)
        byte[] ptxBytes = new byte[] {
            0x5A, 0x00, 0x1D, (byte) 0xD3, (byte) 0xEE, (byte) 0x9B, 0x00, 0x00, 0x00,
            0x2B, (byte) 0xD3, 0x03, (byte) 0xF0, 0x01,
            0x2B, (byte) 0xD3, 0x03, (byte) 0xDA, (byte) 0xC0,
            0x2B, (byte) 0xD3, 0x03, (byte) 0xF0, 0x02,
            0x2B, (byte) 0xD3, 0x03, (byte) 0xDA, (byte) 0xC0
        };

        byte[] afpData = new byte[mcfBytes.length + ptxBytes.length];
        System.arraycopy(mcfBytes, 0, afpData, 0, mcfBytes.length);
        System.arraycopy(ptxBytes, 0, afpData, mcfBytes.length, ptxBytes.length);

        AFPParserConfiguration config = new AFPParserConfiguration();
        config.setInputStream(new ByteArrayInputStream(afpData));
        AFPParser parser = new AFPParser(config);

        StructuredField mcf = parser.parseNextSF();
        assertEquals("MCF_MapCodedFont_Format1", mcf.getClass().getSimpleName());

        StructuredField ptx = parser.parseNextSF();
        assertEquals("PTX_PresentationTextData", ptx.getClass().getSimpleName());

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        AFP2XMLWriter.writeXML(baos, ptx, config);
        String xml = baos.toString();

        System.out.println("XML with SCFL switches:\n" + xml);
        // In CP273, 0xC0 should be 'ä'. In CP500, it should be '{'.
        assertTrue("XML should contain ä (from CP273 LID 1)", xml.contains("ä"));
        assertTrue("XML should contain { (from CP500 LID 2)", xml.contains("{"));
    }
}
