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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import com.mgz.afp.modca.BNG_BeginNamedPageGroup;
import com.mgz.afp.modca.ENG_EndNamedPageGroup;
import com.mgz.afp.modca.NOP_NoOperation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class PdfHandlerStructureTest {

  private PdfHandler handler;

  @BeforeEach
  public void setUp() {
    handler = new PdfHandler();
  }

  @Test
  public void testEmptyStack() {
    assertEquals(0, handler.getGroupDepth());
    assertNull(handler.getCurrentGroupName());
  }

  @Test
  public void testSingleGroupNesting() throws Exception {
    BNG_BeginNamedPageGroup bng = new BNG_BeginNamedPageGroup();
    bng.setName("GROUP1");

    handler.handle(bng);
    assertEquals(1, handler.getGroupDepth());
    assertEquals("GROUP1", handler.getCurrentGroupName());

    ENG_EndNamedPageGroup eng = new ENG_EndNamedPageGroup();
    handler.handle(eng);
    assertEquals(0, handler.getGroupDepth());
    assertNull(handler.getCurrentGroupName());
  }

  @Test
  public void testMultipleGroupNesting() throws Exception {
    BNG_BeginNamedPageGroup bng1 = new BNG_BeginNamedPageGroup();
    bng1.setName("OUTER");
    BNG_BeginNamedPageGroup bng2 = new BNG_BeginNamedPageGroup();
    bng2.setName("INNER");

    handler.handle(bng1);
    assertEquals(1, handler.getGroupDepth());
    assertEquals("OUTER", handler.getCurrentGroupName());

    handler.handle(bng2);
    assertEquals(2, handler.getGroupDepth());
    assertEquals("INNER", handler.getCurrentGroupName());

    handler.handle(new ENG_EndNamedPageGroup());
    assertEquals(1, handler.getGroupDepth());
    assertEquals("OUTER", handler.getCurrentGroupName());

    handler.handle(new ENG_EndNamedPageGroup());
    assertEquals(0, handler.getGroupDepth());
  }

  @Test
  public void testOtherFieldsDoNotAffectStack() throws Exception {
    BNG_BeginNamedPageGroup bng = new BNG_BeginNamedPageGroup();
    bng.setName("GROUP1");

    handler.handle(bng);
    handler.handle(new NOP_NoOperation());

    assertEquals(1, handler.getGroupDepth());
    assertEquals("GROUP1", handler.getCurrentGroupName());
    assertEquals(2, handler.getFieldCount());
  }

  @Test
  public void testUnbalancedEndGroup() throws Exception {
    handler.handle(new ENG_EndNamedPageGroup());
    assertEquals(0, handler.getGroupDepth());
  }
}
