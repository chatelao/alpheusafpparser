/*
Copyright 2026 Alpheus AFP Parser Authors

This file is part of Alpheus AFP Parser.

Alpheus AFP Parser is free software: you can redistribute it and/or modify
it under the terms of the GNU General Public License as published by
the Free Software Foundation, either version 3 of the License, or
(at your option) any later version.

Alpheus AFP Parser is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License
along with Alpheus AFP Parser.  If not, see <http://www.gnu.org/licenses/>
*/

package com.mgz.afp.base;

import com.mgz.afp.builder.TleBuilder;
import com.mgz.afp.enums.SFFlag;
import com.mgz.afp.ipds.LCC_LoadCopyControl;
import com.mgz.afp.modca.MCF_MapCodedFont_Format1;
import com.mgz.afp.modca.TLE_TagLogicalElement;
import com.mgz.afp.modca_L.MCA_MapColorAttributeTable;
import com.mgz.afp.parser.AFPParserConfiguration;
import com.mgz.afp.triplets.Triplet;
import com.mgz.util.UtilBinaryDecoding;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class AfpAutoRecalculationTest {

  @Test
  public void testSFLengthAndPaddingRecalculation() throws Exception {
    TleBuilder builder = new TleBuilder();
    TLE_TagLogicalElement tle = builder.withAttribute("AccountNum", "12345").build();
    AFPParserConfiguration config = new AFPParserConfiguration();

    ByteArrayOutputStream baos1 = new ByteArrayOutputStream();
    tle.writeAFP(baos1, config);
    byte[] data1 = baos1.toByteArray();

    int len1 = UtilBinaryDecoding.parseInt(data1, 1, 2);
    assertEquals(tle.getStructuredFieldIntroducer().getSFLength(), len1);
    assertFalse(tle.getStructuredFieldIntroducer().isFlagSet(SFFlag.isPadded));

    // Add padding to TLE
    byte[] pad = new byte[] {0x00, 0x00, 0x00}; // length 3, last byte will be updated
    tle.setPadding(pad);

    assertTrue(tle.getStructuredFieldIntroducer().isFlagSet(SFFlag.isPadded));
    assertEquals((byte) 3, pad[2]);

    ByteArrayOutputStream baos2 = new ByteArrayOutputStream();
    tle.writeAFP(baos2, config);
    byte[] data2 = baos2.toByteArray();

    int len2 = UtilBinaryDecoding.parseInt(data2, 1, 2);
    assertEquals(len1 + 3, len2);
    assertEquals(tle.getStructuredFieldIntroducer().getSFLength(), len2);
    assertEquals((byte) 3, data2[data2.length - 1]);

    // Strip padding
    tle.setPadding((byte[]) null);
    assertFalse(tle.getStructuredFieldIntroducer().isFlagSet(SFFlag.isPadded));

    ByteArrayOutputStream baos3 = new ByteArrayOutputStream();
    tle.writeAFP(baos3, config);
    byte[] data3 = baos3.toByteArray();
    assertEquals(len1, UtilBinaryDecoding.parseInt(data3, 1, 2));
  }

  @Test
  public void testTripletLengthRecalculation() throws Exception {
    Triplet.FullyQualifiedName fqn = new Triplet.FullyQualifiedName();
    fqn.setType(Triplet.GlobalID_Use.AttributeGID);
    fqn.setFormat(Triplet.GlobalID_Format.CharacterString);
    fqn.setNameAsString("TestAttribute");

    AFPParserConfiguration config = new AFPParserConfiguration();
    ByteArrayOutputStream baos = new ByteArrayOutputStream();
    fqn.writeAFP(baos, config);
    byte[] bytes = baos.toByteArray();

    assertEquals(bytes.length, fqn.getLength());
    assertEquals(bytes.length, bytes[0] & 0xFF);

    // Test MappingOption triplet
    Triplet.MappingOption mo = new Triplet.MappingOption();
    mo.setTripletID(Triplet.TripletID.MappingOption);
    baos.reset();
    mo.writeAFP(baos, config);
    byte[] moBytes = baos.toByteArray();

    assertEquals(3, mo.getLength());
    assertEquals(3, moBytes[0] & 0xFF);
  }

  @Test
  public void testRepeatingGroupWithTripletsRecalculation() throws Exception {
    MCA_MapColorAttributeTable.MCA_RepeatingGroup rg = new MCA_MapColorAttributeTable.MCA_RepeatingGroup();

    Triplet.ResourceLocalIdentifier rli = new Triplet.ResourceLocalIdentifier();
    rli.setTripletID(Triplet.TripletID.ResourceLocalIdentifier);
    rli.setResourceType(Triplet.ResourceLocalIdentifier.RLI_ResourceType.ColorAttributeTable);
    rli.setResourceLocalID((short) 5);
    rg.addTriplet(rli);

    AFPParserConfiguration config = new AFPParserConfiguration();
    ByteArrayOutputStream baos = new ByteArrayOutputStream();
    rg.writeAFP(baos, config);
    byte[] rgBytes = baos.toByteArray();

    int totalLen = UtilBinaryDecoding.parseInt(rgBytes, 0, 2);
    assertEquals(2 + 4, totalLen); // 2 bytes length header + 4 bytes RLI triplet
    assertEquals(totalLen, rg.getRepeatingGroupLength());

    // Add another triplet
    Triplet.Comment comment = new Triplet.Comment();
    comment.setTripletID(Triplet.TripletID.Comment);
    comment.setComment("Hello");
    rg.addTriplet(comment);

    baos.reset();
    rg.writeAFP(baos, config);
    byte[] rgBytes2 = baos.toByteArray();

    int totalLen2 = UtilBinaryDecoding.parseInt(rgBytes2, 0, 2);
    assertEquals(2 + 4 + 7, totalLen2); // 2 + 4 + (2 + 5)
    assertEquals(totalLen2, rg.getRepeatingGroupLength());
  }

  @Test
  public void testLccRepeatingGroupCountRecalculation() throws Exception {
    LCC_LoadCopyControl.LCC_RepeatingGroup rg = new LCC_LoadCopyControl.LCC_RepeatingGroup();
    rg.setCopies((short) 2);
    rg.setKeywords(new byte[] {0x01, 0x02, 0x03});

    AFPParserConfiguration config = new AFPParserConfiguration();
    ByteArrayOutputStream baos = new ByteArrayOutputStream();
    rg.writeAFP(baos, config);
    byte[] bytes = baos.toByteArray();

    assertEquals(5, rg.getCount()); // 2 + 3 keywords
    assertEquals(5, bytes[0] & 0xFF);
    assertEquals(2, bytes[1] & 0xFF);
  }

  @Test
  public void testMcf1RepeatingGroupLengthRecalculation() throws Exception {
    MCF_MapCodedFont_Format1 mcf = new MCF_MapCodedFont_Format1();

    StructuredFieldIntroducer sfi = new StructuredFieldIntroducer();
    sfi.setSFTypeID(com.mgz.afp.enums.SFTypeID.MCF_MapCodedFont_Format1);
    mcf.setStructuredFieldIntroducer(sfi);

    MCF_MapCodedFont_Format1.MCF_RepeatingGroup rg = new MCF_MapCodedFont_Format1.MCF_RepeatingGroup();
    rg.setCodedFontLocalID((short) 1);
    rg.setCodedFontName("C0S01000");
    rg.setCodePageName("T1V10500");
    rg.setFontCharacterSetName("C0S01000");
    mcf.addRepeatingGroup(rg);

    AFPParserConfiguration config = new AFPParserConfiguration();
    ByteArrayOutputStream baos = new ByteArrayOutputStream();
    mcf.writeAFP(baos, config);
    byte[] data = baos.toByteArray();

    assertEquals(28, mcf.getLengthOfRepeatingGroup());
    assertEquals(28, data[9] & 0xFF); // 1 (0x5A) + 8 (SFI) = offset 9 for lengthOfRepeatingGroup
  }
}
