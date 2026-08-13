package com.mgz.xml;

import com.mgz.afp.goca.GAD_DrawingOrder;
import com.mgz.afp.goca.GAD_DrawingOrder.GCCHST_CharacterStringAtCurrentPosition;
import com.mgz.afp.goca.GAD_DrawingOrder.GCHST_CharacterStringAtGivenPosition;
import com.mgz.afp.goca.GAD_DrawingOrder.GOCA_Point;
import com.mgz.afp.goca.GAD_GraphicsData;
import com.mgz.afp.parser.AFPParserConfiguration;
import org.junit.jupiter.api.Test;
import java.io.ByteArrayOutputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class GocaParenthesisTest {

    @Test
    public void testGocaParenthesisHumanReadable() throws Exception {
        // EBCDIC Cp500: "(" is 0x4D, ")" is 0x5D.
        // Let's create a human-readable GOCA string: "(OK)" in EBCDIC Cp500.
        // cp500: ( = 0x4D, O = 0xD6, K = 0xD2, ) = 0x5D
        byte[] bytes = new byte[] { 0x4D, (byte) 0xD6, (byte) 0xD2, 0x5D };

        GCCHST_CharacterStringAtCurrentPosition gcchst = new GCCHST_CharacterStringAtCurrentPosition();
        AFPParserConfiguration config = new AFPParserConfiguration();

        // Decode and serialize
        gcchst.decodeAFP(new byte[] { (byte) 0x83, 0x04, 0x4D, (byte) 0xD6, (byte) 0xD2, 0x5D }, 0, 6, config);

        // Ensure text was decoded
        assertNotNull(gcchst.getText());
        assertTrue(gcchst.getText().contains("(OK)"));

        GAD_GraphicsData gad = new GAD_GraphicsData();
        gad.setDrawingOrders(List.of(gcchst));

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try (AfpJacksonXmlWriter writer = new AfpJacksonXmlWriter(baos, null, true)) {
            writer.handle(gad);
        }

        String xml = baos.toString(StandardCharsets.UTF_8);
        System.out.println("Human Readable GOCA XML: " + xml);

        // Verify that <text> is present with parentheses
        assertTrue(xml.contains("<text>(OK)</text>"));
        // Verify that <codePoints> is present
        assertTrue(xml.contains("<codePoints>"));
    }

    @Test
    public void testGocaParenthesisNonHumanReadable() throws Exception {
        // Let's create a non-human-readable GOCA string: contains control characters (0x01, 0x02) and parentheses
        byte[] bytes = new byte[] { 0x01, 0x02, 0x4D, 0x5D, 0x00, 0x00 };

        GCCHST_CharacterStringAtCurrentPosition gcchst = new GCCHST_CharacterStringAtCurrentPosition();
        AFPParserConfiguration config = new AFPParserConfiguration();

        // Decode
        byte[] sfData = new byte[8];
        sfData[0] = (byte) 0x83; // Opcode
        sfData[1] = 0x06; // Length
        System.arraycopy(bytes, 0, sfData, 2, 6);
        gcchst.decodeAFP(sfData, 0, 8, config);

        // Due to heuristic, text should be null
        assertNull(gcchst.getText());

        GAD_GraphicsData gad = new GAD_GraphicsData();
        gad.setDrawingOrders(List.of(gcchst));

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try (AfpJacksonXmlWriter writer = new AfpJacksonXmlWriter(baos, null, true)) {
            writer.handle(gad);
        }

        String xml = baos.toString(StandardCharsets.UTF_8);
        System.out.println("Non-Human Readable GOCA XML: " + xml);

        // Verify that <text> is NOT present (suppressed)
        assertTrue(!xml.contains("<text>"));
        // Verify that <codePoints> IS present, ensuring the parentheses bytes (0x4D, 0x5D) are not lost!
        assertTrue(xml.contains("<codePoints>"));
    }

    @Test
    public void testGchstParenthesis() throws Exception {
        // Testing GCHST_CharacterStringAtGivenPosition (0xC3)
        // cp500: ( = 0x4D, ) = 0x5D
        byte[] bytes = new byte[] { 0x4D, 0x5D };

        GCHST_CharacterStringAtGivenPosition gchst = new GCHST_CharacterStringAtGivenPosition();
        AFPParserConfiguration config = new AFPParserConfiguration();

        // Decode. GCHST has originPoint (X, Y) before string data.
        // X = 100 (0x0064), Y = 200 (0x00C8)
        byte[] sfData = new byte[] {
            (byte) 0xC3, 0x06, // Opcode, Length (4 bytes for originPoint, 2 bytes for string)
            0x00, 0x64, 0x00, (byte) 0xC8, // Origin Point
            0x4D, 0x5D // String: "()"
        };
        gchst.decodeAFP(sfData, 0, 8, config);

        assertNotNull(gchst.getText());
        assertTrue(gchst.getText().contains("()"));

        GAD_GraphicsData gad = new GAD_GraphicsData();
        gad.setDrawingOrders(List.of(gchst));

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try (AfpJacksonXmlWriter writer = new AfpJacksonXmlWriter(baos, null, true)) {
            writer.handle(gad);
        }

        String xml = baos.toString(StandardCharsets.UTF_8);
        System.out.println("GCHST Parenthesis XML: " + xml);

        // Verify <text>, <originPoint>, and <codePoints>
        assertTrue(xml.contains("<xCoordinate>100</xCoordinate>"));
        assertTrue(xml.contains("<yCoordinate>200</yCoordinate>"));
        assertTrue(xml.contains("<text>()</text>"));
        assertTrue(xml.contains("<codePoints>"));
    }
}
