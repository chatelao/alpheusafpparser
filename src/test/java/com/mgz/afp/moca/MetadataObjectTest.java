package com.mgz.afp.moca;

import com.mgz.afp.exceptions.AFPParserException;
import org.junit.jupiter.api.Test;
import java.nio.charset.StandardCharsets;
import static org.junit.jupiter.api.Assertions.*;

public class MetadataObjectTest {

    @Test
    public void testMetadataObjectDecode() throws Exception {
        // [MOCA-4-001] [MOCA-4-002] [MOCA-4-004] [MOCA-4-006] [MOCA-4-007] [MOCA-4-008] [MOCA-4-010] [MOCA-4-014] [MOCA-4-015] [MOCA-4-018]
        int headerBaseSize = 46;
        String name = "TEST NAME";
        byte[] nameBytes = name.getBytes(StandardCharsets.UTF_16BE);
        int nameLen = nameBytes.length;
        int headerTotalSize = headerBaseSize + nameLen;

        byte[] moData = createValidAfptData();
        int totalPayloadLen = 4 + headerTotalSize + moData.length;

        byte[] data = new byte[totalPayloadLen];

        // MOLength (4B)
        data[0] = (byte)((totalPayloadLen >> 24) & 0xFF);
        data[1] = (byte)((totalPayloadLen >> 16) & 0xFF);
        data[2] = (byte)((totalPayloadLen >> 8) & 0xFF);
        data[3] = (byte)(totalPayloadLen & 0xFF);

        // HeaderLength (2B)
        data[4] = (byte)((headerTotalSize >> 8) & 0xFF);
        data[5] = (byte)(headerTotalSize & 0xFF);

        // MOType (6B): "DES"
        System.arraycopy("DES".getBytes(StandardCharsets.UTF_16BE), 0, data, 6, 6);

        // MOFormat (8B): "AFPT" + "@@"
        System.arraycopy("AFPT".getBytes(StandardCharsets.UTF_16BE), 0, data, 12, 8);

        // MOCompression (20B): "NONE" + "@@@@@@@@"
        System.arraycopy("NONE@@@@@@@@".getBytes(StandardCharsets.UTF_16BE), 0, data, 20, 20);

        // Reserved (8B) - 40 to 47 already 0

        // MONameLength (2B)
        data[48] = (byte)((nameLen >> 8) & 0xFF);
        data[49] = (byte)(nameLen & 0xFF);

        // MOName
        System.arraycopy(nameBytes, 0, data, 50, nameLen);

        // MOData
        System.arraycopy(moData, 0, data, 4 + headerTotalSize, moData.length);

        MetadataObject mo = new MetadataObject();
        mo.decode(data);

        assertEquals(totalPayloadLen, mo.getMoLength());
        assertEquals(headerTotalSize, mo.getHeaderLength());
        assertEquals("DES", mo.getMoType());
        assertEquals("AFPT", mo.getMoFormat());
        assertEquals("NONE", mo.getMoCompression());
        assertEquals(nameLen, mo.getMoNameLength());
        assertEquals(name, mo.getMoName());
        assertArrayEquals(moData, mo.getMoData());
    }

    @Test
    public void testMetadataObjectShortData() {
        // [MOCA-4-020] [MOCA-4-030] EC-0100
        MetadataObject mo = new MetadataObject();
        AFPParserException ex = assertThrows(AFPParserException.class, () -> mo.decode(new byte[49]));
        assertTrue(ex.getMessage().contains("EC-0100"));
    }

    @Test
    public void testMetadataObjectInvalidMOLength() throws Exception {
        // [MOCA-4-020] [MOCA-4-030] EC-0100
        byte[] data = createValidMocaBytes("DES", "AFPT", "NONE", "NAME", createValidAfptData());
        data[3] = 0x31; // MOLength = 49
        MetadataObject mo = new MetadataObject();
        AFPParserException ex = assertThrows(AFPParserException.class, () -> mo.decode(data));
        assertTrue(ex.getMessage().contains("EC-0100"));
    }

    @Test
    public void testMetadataObjectInvalidHeaderLength() throws Exception {
        // [MOCA-4-021] [MOCA-4-031] EC-0200
        byte[] data = createValidMocaBytes("DES", "AFPT", "NONE", "NAME", createValidAfptData());
        data[5] = 0x2D; // HeaderLength = 45
        MetadataObject mo = new MetadataObject();
        AFPParserException ex = assertThrows(AFPParserException.class, () -> mo.decode(data));
        assertTrue(ex.getMessage().contains("EC-0200"));
    }

    @Test
    public void testMetadataObjectInvalidMOType() throws Exception {
        // [MOCA-4-022] [MOCA-4-033] EC-0220
        byte[] data = createValidMocaBytes("BAD", "AFPT", "NONE", "NAME", createValidAfptData());
        MetadataObject mo = new MetadataObject();
        AFPParserException ex = assertThrows(AFPParserException.class, () -> mo.decode(data));
        assertTrue(ex.getMessage().contains("EC-0220"));
    }

    @Test
    public void testMetadataObjectXMPFormat() throws Exception {
        // [MOCA-4-009] [MOCA-4-023]
        String xmp = "<x:xmpmeta xmlns:x='adobe:ns:meta/'><rdf:RDF xmlns:rdf='http://www.w3.org/1999/02/22-rdf-syntax-ns#'/></x:xmpmeta>";
        byte[] data = createValidMocaBytes("DES", "XMP", "NONE", "NAME", xmp.getBytes(StandardCharsets.UTF_8));
        MetadataObject mo = new MetadataObject();
        mo.decode(data);
        assertEquals("XMP", mo.getMoFormat());
    }

    @Test
    public void testMetadataObjectInvalidMOFormat() throws Exception {
        // [MOCA-4-023] [MOCA-4-034] EC-0230
        byte[] data = createValidMocaBytes("DES", "BADD", "NONE", "NAME", createValidAfptData());
        MetadataObject mo = new MetadataObject();
        AFPParserException ex = assertThrows(AFPParserException.class, () -> mo.decode(data));
        assertTrue(ex.getMessage().contains("EC-0230"));
    }

    @Test
    public void testMetadataObjectGZIPCompression() throws Exception {
        // [MOCA-4-011] [MOCA-4-024]
        byte[] data = createValidMocaBytes("DES", "AFPT", "GZIP", "NAME", createValidAfptData());
        MetadataObject mo = new MetadataObject();
        mo.decode(data);
        assertEquals("GZIP", mo.getMoCompression());
    }

    @Test
    public void testMetadataObjectEXICompression() throws Exception {
        // [MOCA-4-012] [MOCA-4-024]
        byte[] data = createValidMocaBytes("DES", "AFPT", "EXI", "NAME", createValidAfptData());
        MetadataObject mo = new MetadataObject();
        mo.decode(data);
        assertEquals("EXI", mo.getMoCompression());
    }

    @Test
    public void testMetadataObjectInvalidMOCompression() throws Exception {
        // [MOCA-4-024] [MOCA-4-035] EC-0240
        byte[] data = createValidMocaBytes("DES", "AFPT", "BADD", "NAME", createValidAfptData());
        MetadataObject mo = new MetadataObject();
        AFPParserException ex = assertThrows(AFPParserException.class, () -> mo.decode(data));
        assertTrue(ex.getMessage().contains("EC-0240"));
    }

    @Test
    public void testMetadataObjectInvalidMONameLength() throws Exception {
        // [MOCA-4-025] [MOCA-4-036] EC-0250
        byte[] data = createValidMocaBytes("DES", "AFPT", "NONE", "NAME", createValidAfptData());
        data[49] = 0x09; // MONameLength = 9 (odd)
        MetadataObject mo = new MetadataObject();
        AFPParserException ex = assertThrows(AFPParserException.class, () -> mo.decode(data));
        assertTrue(ex.getMessage().contains("EC-0250"));

        data[49] = (byte) 0xFE; // MONameLength = 254 (> 250)
        ex = assertThrows(AFPParserException.class, () -> mo.decode(data));
        assertTrue(ex.getMessage().contains("EC-0250"));
    }

    @Test
    public void testMetadataObjectMONameExceedsHeader() throws Exception {
        // [MOCA-4-025] [MOCA-4-036] EC-0250
        byte[] data = createValidMocaBytes("DES", "AFPT", "NONE", "NAME", createValidAfptData());
        data[49] = 10; // Name length 10
        // Total header is 46 + 8 = 54. 50 + 10 = 60 > 54.
        MetadataObject mo = new MetadataObject();
        AFPParserException ex = assertThrows(AFPParserException.class, () -> mo.decode(data));
        assertTrue(ex.getMessage().contains("EC-0250"));
    }

    @Test
    public void testMetadataObjectInvalidXmpData() throws Exception {
        // [MOCA-4-027] [MOCA-4-037] EC-0300
        byte[] data = createValidMocaBytes("DES", "XMP", "NONE", "NAME", "INVALID XML".getBytes(StandardCharsets.UTF_8));
        MetadataObject mo = new MetadataObject();
        AFPParserException ex = assertThrows(AFPParserException.class, () -> mo.decode(data));
        assertTrue(ex.getMessage().contains("EC-0300"));
        assertTrue(ex.getMessage().contains("XMP"));
    }

    @Test
    public void testMetadataObjectInvalidMONameUtf16() throws Exception {
        // [MOCA-4-026] [MOCA-4-032] EC-0210
        byte[] moData = createValidAfptData();
        byte[] data = createValidMocaBytes("DES", "AFPT", "NONE", "X", moData);
        // MONameLength is at offset 48-49. createValidMocaBytes sets it.
        // MOName starts at offset 50.
        data[50] = (byte) 0xD8;
        data[51] = 0x00; // Invalid UTF-16BE (lone surrogate)

        MetadataObject mo = new MetadataObject();
        AFPParserException ex = assertThrows(AFPParserException.class, () -> mo.decode(data));
        assertTrue(ex.getMessage().contains("EC-0210"));
    }

    @Test
    public void testMetadataObjectValidXmpData() throws Exception {
        // [MOCA-4-027] [MOCA-4-037]
        String xmp = "<x:xmpmeta xmlns:x='adobe:ns:meta/'><rdf:RDF xmlns:rdf='http://www.w3.org/1999/02/22-rdf-syntax-ns#'/></x:xmpmeta>";
        byte[] data = createValidMocaBytes("DES", "XMP", "NONE", "NAME", xmp.getBytes(StandardCharsets.UTF_8));
        MetadataObject mo = new MetadataObject();
        mo.decode(data);
        assertEquals("XMP", mo.getMoFormat());
    }

    @Test
    public void testMetadataObjectInvalidAfptData() throws Exception {
        // [MOCA-4-027] [MOCA-4-037] EC-0300
        byte[] data = createValidMocaBytes("DES", "AFPT", "NONE", "NAME", "NOT TRIPLETS".getBytes(StandardCharsets.UTF_8));
        MetadataObject mo = new MetadataObject();
        AFPParserException ex = assertThrows(AFPParserException.class, () -> mo.decode(data));
        assertTrue(ex.getMessage().contains("EC-0300"));
        assertTrue(ex.getMessage().contains("AFPT"));
    }

    @Test
    public void testMetadataObjectValidAfptData() throws Exception {
        // [MOCA-4-027] [MOCA-4-037]
        byte[] afptData = createValidAfptData();
        byte[] data = createValidMocaBytes("DES", "AFPT", "NONE", "NAME", afptData);
        MetadataObject mo = new MetadataObject();
        mo.decode(data);
        assertEquals("AFPT", mo.getMoFormat());
    }

    @Test
    public void testMetadataObjectEmptyData() throws Exception {
        byte[] data = createValidMocaBytes("DES", "AFPT", "NONE", "NAME", new byte[0]);
        MetadataObject mo = new MetadataObject();
        mo.decode(data);
        assertEquals(0, mo.getMoData().length);
    }

    @Test
    public void testMetadataObjectGetMoDataText() throws Exception {
        byte[] moData = createValidAfptData();
        byte[] data = createValidMocaBytes("DES", "AFPT", "NONE", "NAME", moData);
        MetadataObject mo = new MetadataObject();
        mo.decode(data);
        assertNotNull(mo.getMoDataText());

        mo.setMoData(null);
        assertNull(mo.getMoDataText());
    }

    @Test
    public void testMetadataObjectSetters() {
        MetadataObject mo = new MetadataObject();
        mo.setMoLength(100);
        assertEquals(100, mo.getMoLength());

        mo.setHeaderLength(60);
        assertEquals(60, mo.getHeaderLength());

        mo.setMoType("DES");
        assertEquals("DES", mo.getMoType());

        mo.setMoFormat("XMP");
        assertEquals("XMP", mo.getMoFormat());

        mo.setMoCompression("NONE");
        assertEquals("NONE", mo.getMoCompression());

        mo.setMoNameLength(10);
        assertEquals(10, mo.getMoNameLength());

        mo.setMoName("TEST");
        assertEquals("TEST", mo.getMoName());

        byte[] data = new byte[]{1, 2, 3};
        mo.setMoData(data);
        assertArrayEquals(data, mo.getMoData());
    }

    private byte[] createValidAfptData() {
        // Simple AttributeValue triplet (0x36)
        // Length(1), ID(1), Reserved(2), Value(...)
        byte[] value = "VAL".getBytes(StandardCharsets.US_ASCII);
        byte[] triplet = new byte[1 + 1 + 2 + value.length];
        triplet[0] = (byte) triplet.length;
        triplet[1] = 0x36;
        System.arraycopy(value, 0, triplet, 4, value.length);
        return triplet;
    }

    private byte[] createValidMocaBytes(String type, String format, String compression, String name, byte[] moData) throws Exception {
        byte[] nameBytes = name.getBytes(StandardCharsets.UTF_16BE);
        int nameLen = nameBytes.length;
        int headerTotalSize = 46 + nameLen;
        int totalPayloadLen = 4 + headerTotalSize + moData.length;

        byte[] data = new byte[totalPayloadLen];
        data[0] = (byte)((totalPayloadLen >> 24) & 0xFF);
        data[1] = (byte)((totalPayloadLen >> 16) & 0xFF);
        data[2] = (byte)((totalPayloadLen >> 8) & 0xFF);
        data[3] = (byte)(totalPayloadLen & 0xFF);

        data[4] = (byte)((headerTotalSize >> 8) & 0xFF);
        data[5] = (byte)(headerTotalSize & 0xFF);

        byte[] typeBytes = new byte[6];
        System.arraycopy(type.getBytes(StandardCharsets.UTF_16BE), 0, typeBytes, 0, type.length() * 2);
        for (int i = type.length() * 2; i < 6; i += 2) {
            typeBytes[i] = 0x00; typeBytes[i+1] = 0x40;
        }
        System.arraycopy(typeBytes, 0, data, 6, 6);

        byte[] formatBytes = new byte[8];
        System.arraycopy(format.getBytes(StandardCharsets.UTF_16BE), 0, formatBytes, 0, format.length() * 2);
        for (int i = format.length() * 2; i < 8; i += 2) {
            formatBytes[i] = 0x00; formatBytes[i+1] = 0x40;
        }
        System.arraycopy(formatBytes, 0, data, 12, 8);

        byte[] compBytes = new byte[20];
        System.arraycopy(compression.getBytes(StandardCharsets.UTF_16BE), 0, compBytes, 0, compression.length() * 2);
        for (int i = compression.length() * 2; i < 20; i += 2) {
            compBytes[i] = 0x00; compBytes[i+1] = 0x40;
        }
        System.arraycopy(compBytes, 0, data, 20, 20);

        data[48] = (byte)((nameLen >> 8) & 0xFF);
        data[49] = (byte)(nameLen & 0xFF);
        System.arraycopy(nameBytes, 0, data, 50, nameLen);
        System.arraycopy(moData, 0, data, 4 + headerTotalSize, moData.length);

        return data;
    }
}
