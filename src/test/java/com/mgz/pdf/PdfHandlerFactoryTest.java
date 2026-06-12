/*
Copyright 2024 Rudolf Fiala

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
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.mgz.afp.base.handler.StructuredFieldHandler;
import java.io.ByteArrayOutputStream;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.Test;

/**
 * Unit tests for PdfHandlerFactory.
 */
public class PdfHandlerFactoryTest {

  @Test
  public void testBasicProperties() {
    PdfHandlerFactory factory = new PdfHandlerFactory();
    assertEquals("pdf", factory.getFormatName());
    assertFalse(factory.isParallelSupported());
    assertEquals(".pdf", factory.getDefaultExtension(null));
  }

  @Test
  public void testEstimateOutputSize() {
    PdfHandlerFactory factory = new PdfHandlerFactory();
    assertEquals(2000L, factory.estimateOutputSize(1000L));
  }

  @Test
  public void testCreateHandler() throws Exception {
    PdfHandlerFactory factory = new PdfHandlerFactory();
    ByteArrayOutputStream os = new ByteArrayOutputStream();
    StructuredFieldHandler handler = factory.createHandler(os, false);
    assertNotNull(handler);
    assertTrue(handler instanceof PdfHandler);
  }

  @Test
  public void testConfigure() throws Exception {
    PdfHandlerFactory factory = new PdfHandlerFactory();
    Map<String, String> options = new HashMap<>();
    options.put("icc-profile", "non-existent.icc");
    factory.configure(options);

    // Even with non-existent ICC, createHandler should succeed
    ByteArrayOutputStream os = new ByteArrayOutputStream();
    StructuredFieldHandler handler = factory.createHandler(os, false);
    assertNotNull(handler);
  }
}
