/*
Copyright 2026 Rudolf Fiala

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

package com.mgz.pdf;

import com.mgz.afp.enums.SFTypeID;
import com.mgz.afp.enums.SFFlag;
import com.mgz.afp.base.StructuredFieldIntroducer;
import com.mgz.afp.modca.BDT_BeginDocument;
import com.mgz.afp.modca.BPG_BeginPage;
import com.mgz.afp.modca.EDT_EndDocument;
import com.mgz.afp.modca.EPG_EndPage;
import com.mgz.afp.modca.TLE_TagLogicalElement;
import com.mgz.afp.triplets.Triplet;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Verifies that PDF/VT DPart metadata is correctly generated and can be extracted.
 */
public class PdfMetadataVerificationTest {

  @TempDir
  Path tempDir;

  @Test
  public void testMetadataExtraction() throws Exception {
    ByteArrayOutputStream baos = new ByteArrayOutputStream();
    PdfHandler handler = new PdfHandler(baos);

    // 1. Begin Document
    BDT_BeginDocument bdt = new BDT_BeginDocument();
    bdt.setStructuredFieldIntroducer(createSfi(SFTypeID.BDT_BeginDocument));
    handler.handle(bdt);

    // 2. Document-level TLE
    TLE_TagLogicalElement tleDoc = createTle("DocKey", "DocValue");
    handler.handle(tleDoc);

    // 3. Begin Page 1
    BPG_BeginPage bpg1 = new BPG_BeginPage();
    bpg1.setStructuredFieldIntroducer(createSfi(SFTypeID.BPG_BeginPage));
    handler.handle(bpg1);

    // 4. Page 1 TLE
    TLE_TagLogicalElement tlePage1 = createTle("PageKey", "Page1Value");
    handler.handle(tlePage1);

    // 5. End Page 1
    EPG_EndPage epg1 = new EPG_EndPage();
    epg1.setStructuredFieldIntroducer(createSfi(SFTypeID.EPG_EndPage));
    handler.handle(epg1);

    // 6. Begin Page 2
    BPG_BeginPage bpg2 = new BPG_BeginPage();
    bpg2.setStructuredFieldIntroducer(createSfi(SFTypeID.BPG_BeginPage));
    handler.handle(bpg2);

    // 7. Page 2 TLE
    TLE_TagLogicalElement tlePage2 = createTle("PageKey", "Page2Value");
    handler.handle(tlePage2);

    // 8. End Page 2
    EPG_EndPage epg2 = new EPG_EndPage();
    epg2.setStructuredFieldIntroducer(createSfi(SFTypeID.EPG_EndPage));
    handler.handle(epg2);

    // 9. End Document
    EDT_EndDocument edt = new EDT_EndDocument();
    edt.setStructuredFieldIntroducer(createSfi(SFTypeID.EDT_EndDocument));
    handler.handle(edt);

    handler.close();

    // Extract metadata
    PdfMetadataExtractor extractor = new PdfMetadataExtractor();
    List<Map<String, String>> metadata = extractor.extractMetadata(new ByteArrayInputStream(baos.toByteArray()));

    // Verify
    assertEquals(2, metadata.size(), "Should have 2 leaf DPart nodes (one per page)");

    Map<String, String> page1 = metadata.get(0);
    assertEquals("DocValue", page1.get("DocKey"));
    assertEquals("Page1Value", page1.get("PageKey"));

    Map<String, String> page2 = metadata.get(1);
    assertEquals("DocValue", page2.get("DocKey"));
    assertEquals("Page2Value", page2.get("PageKey"));
  }

  private TLE_TagLogicalElement createTle(String key, String value) {
    TLE_TagLogicalElement tle = new TLE_TagLogicalElement();
    tle.setStructuredFieldIntroducer(createSfi(SFTypeID.TLE_TagLogicalElement));

    List<Triplet> triplets = new ArrayList<>();
    Triplet.FullyQualifiedName fqn = new Triplet.FullyQualifiedName();
    fqn.setType(Triplet.GlobalID_Use.AttributeGID);
    fqn.setNameAsString(key);
    triplets.add(fqn);

    Triplet.AttributeValue av = new Triplet.AttributeValue();
    av.setAttributeValue(value);
    triplets.add(av);

    tle.setTriplets(triplets);
    return tle;
  }

  private StructuredFieldIntroducer createSfi(SFTypeID typeID) {
    StructuredFieldIntroducer sfi = new StructuredFieldIntroducer();
    sfi.setSFTypeID(typeID);
    sfi.setFlagByte(java.util.EnumSet.noneOf(SFFlag.class));
    return sfi;
  }
}
