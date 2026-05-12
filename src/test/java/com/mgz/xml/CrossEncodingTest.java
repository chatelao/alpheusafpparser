package com.mgz.xml;

import com.mgz.afp.base.StructuredField;
import com.mgz.afp.parser.AFPParser;
import com.mgz.afp.parser.AFPParserConfiguration;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.nio.charset.Charset;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class CrossEncodingTest {

    @Test
    public void testCrossEncodingExtraction() throws Exception {
        String logicalText = "München äöü";

        verifyEncoding("IBM500", logicalText, (short) 1);
        verifyEncoding("IBM273", logicalText, (short) 2);
        verifyEncoding("IBM1141", logicalText, (short) 3);
    }

    private void verifyEncoding(String charsetName, String logicalText, short lid) throws Exception {
        Charset charset = Charset.forName(charsetName);
        byte[] encodedText = logicalText.getBytes(charset);

        // Construct MCF2 for this LID
        // RG Len: 2 (for len) + 4 (T0x24) + (2 + nameLen) (T0x02)
        String cpName = getAfpCodePageName(charsetName);
        byte[] cpNameBytes = cpName.getBytes(Charset.forName("IBM500"));
        int mcf2RgLen = 2 + 4 + (4 + cpNameBytes.length);

        byte[] mcf2Bytes = new byte[9 + mcf2RgLen];
        mcf2Bytes[0] = 0x5A;
        mcf2Bytes[1] = (byte) ((mcf2Bytes.length - 1) >> 8);
        mcf2Bytes[2] = (byte) ((mcf2Bytes.length - 1) & 0xFF);
        mcf2Bytes[3] = (byte) 0xD3;
        mcf2Bytes[4] = (byte) 0xAB;
        mcf2Bytes[5] = (byte) 0x8A;

        // RG
        mcf2Bytes[9] = (byte) (mcf2RgLen >> 8);
        mcf2Bytes[10] = (byte) (mcf2RgLen & 0xFF);
        // Triplet 0x24 (LID)
        mcf2Bytes[11] = 0x04;
        mcf2Bytes[12] = 0x24;
        mcf2Bytes[13] = 0x05;
        mcf2Bytes[14] = (byte) lid;
        // Triplet 0x02 (CP Name)
        mcf2Bytes[15] = (byte) (4 + cpNameBytes.length);
        mcf2Bytes[16] = 0x02;
        mcf2Bytes[17] = (byte) 0x85;
        mcf2Bytes[18] = 0x00;
        System.arraycopy(cpNameBytes, 0, mcf2Bytes, 19, cpNameBytes.length);

        // Construct PTX with SCFL and TRN
        int ptxPayloadLen = 5 + (4 + encodedText.length);
        byte[] ptxBytes = new byte[9 + ptxPayloadLen];
        ptxBytes[0] = 0x5A;
        ptxBytes[1] = (byte) ((ptxBytes.length - 1) >> 8);
        ptxBytes[2] = (byte) ((ptxBytes.length - 1) & 0xFF);
        ptxBytes[3] = (byte) 0xD3;
        ptxBytes[4] = (byte) 0xEE;
        ptxBytes[5] = (byte) 0x9B;

        // SCFL
        ptxBytes[9] = 0x2B;
        ptxBytes[10] = (byte) 0xD3;
        ptxBytes[11] = 0x03;
        ptxBytes[12] = (byte) 0xF0;
        ptxBytes[13] = (byte) lid;

        // TRN
        ptxBytes[14] = 0x2B;
        ptxBytes[15] = (byte) 0xD3;
        ptxBytes[16] = (byte) (2 + encodedText.length);
        ptxBytes[17] = (byte) 0xDA;
        System.arraycopy(encodedText, 0, ptxBytes, 18, encodedText.length);

        byte[] afpData = new byte[mcf2Bytes.length + ptxBytes.length];
        System.arraycopy(mcf2Bytes, 0, afpData, 0, mcf2Bytes.length);
        System.arraycopy(ptxBytes, 0, afpData, mcf2Bytes.length, ptxBytes.length);

        AFPParserConfiguration config = new AFPParserConfiguration();
        config.setInputStream(new ByteArrayInputStream(afpData));
        AFPParser parser = new AFPParser(config);

        parser.parseNextSF(); // MCF
        StructuredField ptx = parser.parseNextSF(); // PTX

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        AFP2XMLWriter.writeXML(baos, ptx, config);
        String xml = baos.toString();

        assertTrue(xml.contains(logicalText),
            "XML for " + charsetName + " should contain '" + logicalText + "'. Got: " + xml);
    }

    private String getAfpCodePageName(String charsetName) {
        if ("IBM500".equals(charsetName)) return "T1V10500";
        if ("IBM273".equals(charsetName)) return "T1V10273";
        if ("IBM1141".equals(charsetName)) return "T1001141";
        return "T1V10500";
    }
}
