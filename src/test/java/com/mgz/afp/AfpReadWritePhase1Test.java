package com.mgz.afp;

import com.mgz.afp.base.StructuredField;
import com.mgz.afp.base.StructuredFieldIntroducer;
import com.mgz.afp.base.Undefined;
import com.mgz.afp.builder.TleBuilder;
import com.mgz.afp.enums.SFFlag;
import com.mgz.afp.enums.SFTypeID;
import com.mgz.afp.exceptions.AFPParserException;
import com.mgz.afp.ipds.LE_LoadEquivalence;
import com.mgz.afp.ipds.LFE_LoadFontEquivalence;
import com.mgz.afp.modca.NOP_NoOperation;
import com.mgz.afp.modca.TLE_TagLogicalElement;
import com.mgz.afp.parser.AFPParser;
import com.mgz.afp.parser.AFPParserConfiguration;
import com.mgz.afp.triplets.Triplet;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class AfpReadWritePhase1Test {

  @Test
  public void testAutomatedSfiLengthAndPaddingRecalculation() throws IOException, AFPParserException {
    TLE_TagLogicalElement tle = new TleBuilder()
        .withAttribute("Customer", "Jane Doe")
        .build();

    // Attach custom 4-byte padding array without manually setting length byte or flag
    byte[] rawPadding = new byte[]{0x00, 0x00, 0x00, 0x00};
    tle.setPadding(rawPadding);

    // Padding setter and serializer should ensure trailing byte matches padding length and set isPadded flag
    ByteArrayOutputStream baos = new ByteArrayOutputStream();
    AFPParserConfiguration config = new AFPParserConfiguration();
    tle.writeAFP(baos, config);

    byte[] serialized = baos.toByteArray();
    assertEquals((byte) 0x5A, serialized[0]);

    // Parse back
    AFPParserConfiguration parseConfig = new AFPParserConfiguration();
    parseConfig.setInputStream(new ByteArrayInputStream(serialized));
    AFPParser parser = new AFPParser(parseConfig);

    StructuredField parsed = parser.parseNextSF();
    assertNotNull(parsed);
    assertTrue(parsed.getStructuredFieldIntroducer().isFlagSet(SFFlag.isPadded));

    int expectedTotalLen = serialized.length - 1; // excluding 0x5A
    assertEquals(expectedTotalLen, parsed.getStructuredFieldIntroducer().getSFLength());
  }

  @Test
  public void testTripletLengthRecalculation() throws IOException, AFPParserException {
    TleBuilder builder = new TleBuilder();
    TLE_TagLogicalElement tle = builder
        .withAttribute("AccountID", "12345678")
        .build();

    // Modify attribute value triplet to a longer string
    for (Triplet t : tle.getTriplets()) {
      if (t instanceof Triplet.AttributeValue av) {
        av.setAttributeValue("12345678-EXTENDED-9999");
      }
    }

    ByteArrayOutputStream baos = new ByteArrayOutputStream();
    AFPParserConfiguration config = new AFPParserConfiguration();
    tle.writeAFP(baos, config);

    byte[] serialized = baos.toByteArray();

    AFPParserConfiguration parseConfig = new AFPParserConfiguration();
    parseConfig.setInputStream(new ByteArrayInputStream(serialized));
    AFPParser parser = new AFPParser(parseConfig);

    StructuredField parsed = parser.parseNextSF();
    assertTrue(parsed instanceof TLE_TagLogicalElement);
    TLE_TagLogicalElement parsedTle = (TLE_TagLogicalElement) parsed;

    assertNotNull(parsedTle.getTriplets());
    assertEquals(2, parsedTle.getTriplets().size());

    // Verify each triplet length is updated
    for (Triplet t : parsedTle.getTriplets()) {
      assertTrue(t.getLength() > 2);
    }
  }

  @Test
  public void testRepeatingGroupRecalculation() throws IOException, AFPParserException {
    LE_LoadEquivalence le = new LE_LoadEquivalence();
    StructuredFieldIntroducer sfi = new StructuredFieldIntroducer();
    sfi.setSFTypeID(SFTypeID.LE_LoadEquivalence);
    le.setStructuredFieldIntroducer(sfi);
    le.setMappingType(1);

    LE_LoadEquivalence.LE_RepeatingGroup rg1 = new LE_LoadEquivalence.LE_RepeatingGroup();
    rg1.setInternal(10);
    rg1.setExternal(100);
    le.addRepeatingGroup(rg1);

    LE_LoadEquivalence.LE_RepeatingGroup rg2 = new LE_LoadEquivalence.LE_RepeatingGroup();
    rg2.setInternal(20);
    rg2.setExternal(200);
    le.addRepeatingGroup(rg2);

    ByteArrayOutputStream baos = new ByteArrayOutputStream();
    AFPParserConfiguration config = new AFPParserConfiguration();
    le.writeAFP(baos, config);

    byte[] serialized = baos.toByteArray();

    AFPParserConfiguration parseConfig = new AFPParserConfiguration();
    parseConfig.setInputStream(new ByteArrayInputStream(serialized));
    AFPParser parser = new AFPParser(parseConfig);

    StructuredField parsed = parser.parseNextSF();
    assertTrue(parsed instanceof LE_LoadEquivalence);
    LE_LoadEquivalence parsedLe = (LE_LoadEquivalence) parsed;

    assertEquals(1, parsedLe.getMappingType());
    assertNotNull(parsedLe.getRepeatingGroups());
    assertEquals(2, parsedLe.getRepeatingGroups().size());

    // Expected length: 8 (SFI) + 1 (flagByte) + 2 (mappingType) + 2 * 4 (repeating groups) = 19
    assertEquals(19, parsedLe.getStructuredFieldIntroducer().getSFLength());
  }

  @Test
  public void testUndefinedFieldFidelity() throws IOException, AFPParserException {
    // Custom/Vendor structured field bytes with unknown SFTypeID (e.g. 0xD3FFFF)
    byte[] rawPayload = new byte[]{0x01, 0x02, 0x03, 0x04, 0x05, 0x06, 0x07, 0x08};
    byte[] sfBytes = new byte[1 + 8 + rawPayload.length];
    sfBytes[0] = 0x5A;
    sfBytes[1] = 0x00;
    sfBytes[2] = (byte) (8 + rawPayload.length);
    sfBytes[3] = (byte) 0xD3;
    sfBytes[4] = (byte) 0xFF;
    sfBytes[5] = (byte) 0xFF;
    sfBytes[6] = 0x00;
    sfBytes[7] = 0x00;
    sfBytes[8] = 0x00;
    System.arraycopy(rawPayload, 0, sfBytes, 9, rawPayload.length);

    AFPParserConfiguration config = new AFPParserConfiguration();
    config.setInputStream(new ByteArrayInputStream(sfBytes));
    AFPParser parser = new AFPParser(config);

    StructuredField sf = parser.parseNextSF();
    assertNotNull(sf);
    assertTrue(sf instanceof Undefined);

    Undefined undefined = (Undefined) sf;
    assertArrayEquals(rawPayload, undefined.getPayload());

    ByteArrayOutputStream baos = new ByteArrayOutputStream();
    undefined.writeAFP(baos, config);
    assertArrayEquals(sfBytes, baos.toByteArray());
  }
}
