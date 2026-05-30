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

import com.mgz.afp.base.StructuredField;
import com.mgz.afp.base.StructuredFieldIntroducer;
import com.mgz.afp.enums.SFTypeID;
import com.mgz.afp.modca.BDT_BeginDocument;
import com.mgz.afp.modca.BPG_BeginPage;
import com.mgz.afp.modca.EDT_EndDocument;
import com.mgz.afp.modca.EPG_EndPage;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Verifies that {@link PdfHandler} correctly tracks MO:DCA structural boundaries.
 */
public class PdfHandlerStructureTest {

  @Test
  public void testStructureDepthTracking() throws Exception {
    PdfHandler handler = new PdfHandler(new java.io.ByteArrayOutputStream());

    BDT_BeginDocument bdt = new BDT_BeginDocument();
    bdt.setStructuredFieldIntroducer(createSfi(SFTypeID.BDT_BeginDocument));

    BPG_BeginPage bpg = new BPG_BeginPage();
    bpg.setStructuredFieldIntroducer(createSfi(SFTypeID.BPG_BeginPage));

    EPG_EndPage epg = new EPG_EndPage();
    epg.setStructuredFieldIntroducer(createSfi(SFTypeID.EPG_EndPage));

    EDT_EndDocument edt = new EDT_EndDocument();
    edt.setStructuredFieldIntroducer(createSfi(SFTypeID.EDT_EndDocument));

    assertEquals(0, handler.getStructureDepth());

    handler.handle(bdt);
    assertEquals(1, handler.getStructureDepth());

    handler.handle(bpg);
    assertEquals(2, handler.getStructureDepth());

    handler.handle(epg);
    assertEquals(1, handler.getStructureDepth());

    handler.handle(edt);
    assertEquals(0, handler.getStructureDepth());
  }

  private StructuredFieldIntroducer createSfi(SFTypeID typeID) {
    StructuredFieldIntroducer sfi = new StructuredFieldIntroducer();
    sfi.setSFTypeID(typeID);
    return sfi;
  }
}
