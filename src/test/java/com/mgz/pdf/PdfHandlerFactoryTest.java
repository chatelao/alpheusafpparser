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
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import com.mgz.afp.base.handler.StructuredFieldHandler;
import java.io.ByteArrayOutputStream;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Unit test for {@link PdfHandlerFactory}.
 */
public class PdfHandlerFactoryTest {

  private PdfHandlerFactory factory;

  @BeforeEach
  public void setUp() {
    factory = new PdfHandlerFactory();
  }

  @Test
  public void testGetFormatName() {
    assertEquals("pdf", factory.getFormatName());
  }

  @Test
  public void testGetDefaultExtension() {
    assertEquals(".pdf", factory.getDefaultExtension(null));
    assertEquals(".pdf", factory.getDefaultExtension(new HashMap<>()));
  }

  @Test
  public void testIsParallelSupported() {
    assertFalse(factory.isParallelSupported());
  }

  @Test
  public void testEstimateOutputSize() {
    assertEquals(200, factory.estimateOutputSize(100));
  }

  @Test
  public void testCreateHandler() throws Exception {
    try (ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
      StructuredFieldHandler handler = factory.createHandler(baos, false);
      assertNotNull(handler);
      assertEquals(PdfHandler.class, handler.getClass());
    }
  }

  @Test
  public void testConfigureAndCreateHandler() throws Exception {
    Map<String, String> options = new HashMap<>();
    options.put("icc-profile", "non-existent-file.icc");
    factory.configure(options);

    try (ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
      StructuredFieldHandler handler = factory.createHandler(baos, false);
      assertNotNull(handler);
    }
  }
}
