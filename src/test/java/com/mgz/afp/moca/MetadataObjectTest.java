package com.mgz.afp.moca;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import com.mgz.afp.exceptions.AFPParserException;
import java.nio.charset.StandardCharsets;
import org.junit.jupiter.api.Test;

public class MetadataObjectTest {

  @Test
  public void testDecodeMoca() throws AFPParserException {
    // Construct a MOCA byte array
    // MOLength: 50 + 4 = 54 (0x00000036)
    // HeaderLength: 46 (0x002E)
    // MOType: DES (UTF-16BE)
    // MOFormat: AFPT (UTF-16BE)
    // MOCompression: NONE (UTF-16BE)
    // Reserved: 0 (8 bytes)
    // MONameLength: 4 (0x0004)
    // MOName: "test" (UTF-16BE)
    // MOData: "data" (UTF-16BE)

    byte[] data = new byte[54 + 4 + 8]; // Some extra for data
    // MOLength
    data[0] = 0x00; data[1] = 0x00; data[2] = 0x00; data[3] = 0x42; // total length 66
    // HeaderLength
    data[4] = 0x00; data[5] = 0x36; // 54 bytes
    // MOType "DES"
    byte[] des = "DES".getBytes(StandardCharsets.UTF_16BE);
    System.arraycopy(des, 0, data, 6, 6);
    // MOFormat "AFPT    "
    byte[] afpt = "AFPT".getBytes(StandardCharsets.UTF_16BE);
    System.arraycopy(afpt, 0, data, 12, 8);
    // MOCompression "NONE                "
    byte[] none = "NONE".getBytes(StandardCharsets.UTF_16BE);
    System.arraycopy(none, 0, data, 20, 8);
    for (int i=28; i<40; i++) data[i] = 0; // Padding for NONE
    // Reserved 40-47
    // MONameLength
    data[48] = 0x00; data[49] = 0x08; // 8 bytes for "test" in UTF-16BE
    // MOName "test"
    byte[] test = "test".getBytes(StandardCharsets.UTF_16BE);
    System.arraycopy(test, 0, data, 50, 8);
    // MOData "data" at offset 4 + 54 = 58
    byte[] payload = "data".getBytes(StandardCharsets.UTF_16BE);
    System.arraycopy(payload, 0, data, 58, 8);

    MetadataObject mo = new MetadataObject();
    mo.decode(data);

    assertEquals(0x42, mo.getMoLength());
    assertEquals(54, mo.getHeaderLength());
    assertEquals("DES", mo.getMoType());
    assertEquals("AFPT", mo.getMoFormat());
    assertEquals("NONE", mo.getMoCompression());
    assertEquals(8, mo.getMoNameLength());
    assertEquals("test", mo.getMoName());
    assertArrayEquals(payload, mo.getMoData());
    assertEquals("data", mo.getMoDataText());
  }
}
