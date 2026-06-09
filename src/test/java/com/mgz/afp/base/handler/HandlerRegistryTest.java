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

package com.mgz.afp.base.handler;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import org.junit.jupiter.api.Test;

public class HandlerRegistryTest {

  @Test
  public void testRegistryDiscovery() {
    Set<String> formats = HandlerRegistry.getRegisteredFormats();
    assertTrue(formats.contains("xml"), "Registry should contain 'xml' format");
    assertTrue(formats.contains("pdf"), "Registry should contain 'pdf' format");
  }

  @Test
  public void testGetFactory() {
    HandlerFactory xmlFactory = HandlerRegistry.getFactory("xml");
    assertNotNull(xmlFactory);
    assertEquals("xml", xmlFactory.getFormatName());

    HandlerFactory pdfFactory = HandlerRegistry.getFactory("PDF");
    assertNotNull(pdfFactory);
    assertEquals("pdf", pdfFactory.getFormatName());
  }

  @Test
  public void testXmlFactoryConfiguration() {
    HandlerFactory factory = HandlerRegistry.getFactory("xml");
    Map<String, String> options = new HashMap<>();
    options.put("xpath", "//text()");

    assertEquals(".txt", factory.getDefaultExtension(options));

    options.put("xpath", "//StructuredField");
    assertEquals(".xml", factory.getDefaultExtension(options));

    assertTrue(factory.isParallelSupported());
  }

  @Test
  public void testPdfFactoryConfiguration() {
    HandlerFactory factory = HandlerRegistry.getFactory("pdf");
    Map<String, String> options = new HashMap<>();

    assertEquals(".pdf", factory.getDefaultExtension(options));
    assertFalse(factory.isParallelSupported());
  }
}
