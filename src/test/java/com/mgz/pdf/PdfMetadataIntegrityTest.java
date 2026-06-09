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
import com.mgz.afp.modca.BNG_BeginNamedPageGroup;
import com.mgz.afp.modca.BPG_BeginPage;
import com.mgz.afp.modca.EDT_EndDocument;
import com.mgz.afp.modca.ENG_EndNamedPageGroup;
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
 * Verifies that PDF/VT DPart metadata (TLEs and Group Names) is correctly preserved
 * and accessible in the generated PDF's structure.
 */
public class PdfMetadataIntegrityTest {

  @TempDir
  Path tempDir;

  @Test
  public void testTleAndGroupNameIntegrity() throws Exception {
    ByteArrayOutputStream baos = new ByteArrayOutputStream();
    PdfHandler handler = new PdfHandler(baos);

    // 1. Begin Document "MYDOC"
    BDT_BeginDocument bdt = new BDT_BeginDocument();
    bdt.setStructuredFieldIntroducer(createSfi(SFTypeID.BDT_BeginDocument));
    bdt.setName("MYDOC");
    handler.handle(bdt);

    // 2. Document-level TLE
    handler.handle(createTle("CustomerID", "C-12345"));

    // 3. Begin Named Group "BATCH_A"
    BNG_BeginNamedPageGroup bng = new BNG_BeginNamedPageGroup();
    bng.setStructuredFieldIntroducer(createSfi(SFTypeID.BNG_BeginNamedPageGroup));
    bng.setName("BATCH_A");
    handler.handle(bng);

    // 4. Group-level TLE
    handler.handle(createTle("BatchID", "B-999"));

    // 5. Begin Page 1
    BPG_BeginPage bpg1 = new BPG_BeginPage();
    bpg1.setStructuredFieldIntroducer(createSfi(SFTypeID.BPG_BeginPage));
    bpg1.setName("PAGE_01");
    handler.handle(bpg1);

    // 6. Page 1 TLE
    handler.handle(createTle("InvoiceNr", "INV-001"));

    // 7. End Page 1
    EPG_EndPage epg1 = new EPG_EndPage();
    epg1.setStructuredFieldIntroducer(createSfi(SFTypeID.EPG_EndPage));
    handler.handle(epg1);

    // 8. End Named Group
    ENG_EndNamedPageGroup eng = new ENG_EndNamedPageGroup();
    eng.setStructuredFieldIntroducer(createSfi(SFTypeID.ENG_EndNamedPageGroup));
    handler.handle(eng);

    // 9. End Document
    EDT_EndDocument edt = new EDT_EndDocument();
    edt.setStructuredFieldIntroducer(createSfi(SFTypeID.EDT_EndDocument));
    handler.handle(edt);

    handler.close();

    // Extract metadata using PdfMetadataExtractor
    PdfMetadataExtractor extractor = new PdfMetadataExtractor();
    List<Map<String, String>> metadata = extractor.extractMetadata(new ByteArrayInputStream(baos.toByteArray()));

    // Verify Integrity
    assertEquals(1, metadata.size(), "Should have 1 leaf DPart node representing Page 1");

    Map<String, String> pageMetadata = metadata.get(0);

    // Debug info
    System.out.println("Extracted Metadata for Page 1: " + pageMetadata);

    // Check Group Names (mapped to GroupName property)
    // In PdfHandler.java, BDT, BNG, and BPG names are all mapped to the "GroupName" property.
    // PdfMetadataExtractor.traverseDPart inherits and overwrites properties by key.
    // Thus, the leaf BPG name "PAGE_01" will be the final value for "GroupName" in the inherited map.
    assertEquals("PAGE_01", pageMetadata.get("GroupName"), "Leaf GroupName (BPG) should be present");

    // Check TLEs
    assertEquals("C-12345", pageMetadata.get("CustomerID"), "Document-level TLE should be inherited");
    assertEquals("B-999", pageMetadata.get("BatchID"), "Group-level TLE should be inherited");
    assertEquals("INV-001", pageMetadata.get("InvoiceNr"), "Page-level TLE should be present");
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
