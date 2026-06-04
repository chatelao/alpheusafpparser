package com.mgz.afp.triplets;

import com.mgz.afp.parser.AFPParserConfiguration;
import com.mgz.afp.parser.TripletParser;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for Triplet Extender (0xFF) functionality.
 * Reference: MODCA-6-903, MODCA-6-904
 */
public class TripletExtenderTest {

    @Test
    public void testFullyQualifiedNameExtension() throws Exception {
        // [MODCA-6-903] [MODCA-6-904]
        AFPParserConfiguration config = new AFPParserConfiguration();

        // FQN: Length 8, ID 0x02, Type 0x01 (ReplaceFirstGIDName), Format 0x00 (CharacterString), Name "PART"
        byte[] fqn = new byte[] {
            0x08, 0x02, 0x01, 0x00, (byte) 0xD7, (byte) 0xC1, (byte) 0xD9, (byte) 0xE3 // "PART" in EBCDIC
        };

        // Extender: Length 8, ID 0xFF, Reserved 0x0000, Data "1234"
        byte[] extender = new byte[] {
            0x08, (byte) 0xFF, 0x00, 0x00, (byte) 0xF1, (byte) 0xF2, (byte) 0xF3, (byte) 0xF4 // "1234" in EBCDIC
        };

        byte[] data = new byte[fqn.length + extender.length];
        System.arraycopy(fqn, 0, data, 0, fqn.length);
        System.arraycopy(extender, 0, data, fqn.length, extender.length);

        List<Triplet> triplets = TripletParser.parseTriplets(data, 0, data.length, config);

        assertEquals(1, triplets.size(), "Triplet extender should be merged into the preceding triplet");
        assertTrue(triplets.get(0) instanceof Triplet.FullyQualifiedName);
        Triplet.FullyQualifiedName target = (Triplet.FullyQualifiedName) triplets.get(0);

        assertEquals("PART1234", target.getNameAsString());
    }

    @Test
    public void testAttributeValueExtension() throws Exception {
        // [MODCA-6-903] [MODCA-6-904]
        AFPParserConfiguration config = new AFPParserConfiguration();

        // AttributeValue: Length 8, ID 0x36, Reserved 0x0000, Value "VAL "
        byte[] av = new byte[] {
            0x08, 0x36, 0x00, 0x00, (byte) 0xE5, (byte) 0xC1, (byte) 0xD3, 0x40 // "VAL " in EBCDIC
        };

        // Extender: Length 8, ID 0xFF, Reserved 0x0000, Data "EXT "
        byte[] extender = new byte[] {
            0x08, (byte) 0xFF, 0x00, 0x00, (byte) 0xC5, (byte) 0xE7, (byte) 0xE3, 0x40 // "EXT " in EBCDIC
        };

        byte[] data = new byte[av.length + extender.length];
        System.arraycopy(av, 0, data, 0, av.length);
        System.arraycopy(extender, 0, data, av.length, extender.length);

        List<Triplet> triplets = TripletParser.parseTriplets(data, 0, data.length, config);

        assertEquals(1, triplets.size());
        assertTrue(triplets.get(0) instanceof Triplet.AttributeValue);
        Triplet.AttributeValue target = (Triplet.AttributeValue) triplets.get(0);

        assertEquals("VAL EXT ", target.getAttributeValue());
    }

    @Test
    public void testCommentExtension() throws Exception {
        // [MODCA-6-903] [MODCA-6-904]
        AFPParserConfiguration config = new AFPParserConfiguration();

        // Comment: Length 6, ID 0x65, Comment "ABC "
        byte[] comment = new byte[] {
            0x06, 0x65, (byte) 0xC1, (byte) 0xC2, (byte) 0xC3, 0x40 // "ABC " in EBCDIC
        };

        // Extender: Length 6, ID 0xFF, Reserved 0x0000, Data "DE"
        byte[] extender = new byte[] {
            0x06, (byte) 0xFF, 0x00, 0x00, (byte) 0xC4, (byte) 0xC5 // "DE" in EBCDIC
        };

        byte[] data = new byte[comment.length + extender.length];
        System.arraycopy(comment, 0, data, 0, comment.length);
        System.arraycopy(extender, 0, data, comment.length, extender.length);

        List<Triplet> triplets = TripletParser.parseTriplets(data, 0, data.length, config);

        assertEquals(1, triplets.size());
        assertTrue(triplets.get(0) instanceof Triplet.Comment);
        Triplet.Comment target = (Triplet.Comment) triplets.get(0);

        assertEquals("ABC DE", target.getText());
    }

    @Test
    public void testMultipleExtensions() throws Exception {
        // [MODCA-6-903] [MODCA-6-904]
        AFPParserConfiguration config = new AFPParserConfiguration();

        // Comment: Length 4, ID 0x65, Comment "A "
        byte[] comment = new byte[] { 0x04, 0x65, (byte) 0xC1, 0x40 };

        // Extender 1: Length 5, ID 0xFF, Reserved 0x0000, Data "B"
        byte[] ext1 = new byte[] { 0x05, (byte) 0xFF, 0x00, 0x00, (byte) 0xC2 };

        // Extender 2: Length 5, ID 0xFF, Reserved 0x0000, Data "C"
        byte[] ext2 = new byte[] { 0x05, (byte) 0xFF, 0x00, 0x00, (byte) 0xC3 };

        byte[] data = new byte[comment.length + ext1.length + ext2.length];
        System.arraycopy(comment, 0, data, 0, comment.length);
        System.arraycopy(ext1, 0, data, comment.length, ext1.length);
        System.arraycopy(ext2, 0, data, comment.length + ext1.length, ext2.length);

        List<Triplet> triplets = TripletParser.parseTriplets(data, 0, data.length, config);

        assertEquals(1, triplets.size());
        assertTrue(triplets.get(0) instanceof Triplet.Comment);
        Triplet.Comment target = (Triplet.Comment) triplets.get(0);

        assertEquals("A BC", target.getText());
    }
}
