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

package com.mgz.xml;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.mgz.afp.base.StructuredField;
import com.mgz.afp.base.StructuredFieldIntroducer;
import com.mgz.afp.modca.BPG_BeginPage;
import com.mgz.afp.modca.EPG_EndPage;
import com.mgz.afp.modca.NOP_NoOperation;
import com.mgz.afp.parser.AFPParserConfiguration;
import com.mgz.util.ExposedByteArrayOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import javax.xml.parsers.DocumentBuilderFactory;
import org.junit.jupiter.api.Test;
import org.w3c.dom.Document;

/**
 * Verifies Phase 2 Step 2.1:
 * - Wrapper-free headless fragment output via Woodstox P_OUTPUT_VALIDATE_STRUCTURE=false
 * - Mixed fast-path and Jackson-fallback sibling elements in fragment mode
 * - ExposedByteArrayOutputStream zero-copy slicing
 * - XmlHandlerFactory.stripFragmentWrapper fast-path and legacy fallback
 * - XPath filtering without <AfpFragments> string substitution
 */
public class HeadlessFragmentWriterTest {

  @Test
  public void testFragmentModeEmitsHeadlessMultiRootXmlWithoutWrapper() throws Exception {
    ExposedByteArrayOutputStream out = new ExposedByteArrayOutputStream(256);
    try (AfpJacksonXmlWriter writer = new AfpJacksonXmlWriter(out, null, true, false)) {
      BPG_BeginPage bpg = new BPG_BeginPage();
      bpg.setName("P0000001");
      writer.handle(bpg);

      NOP_NoOperation nop = new NOP_NoOperation();
      nop.setStructuredFieldIntroducer(new StructuredFieldIntroducer());
      nop.setData(new byte[] {(byte) 0xC8, (byte) 0x89});
      writer.handle(nop);

      FallbackSf fallback = new FallbackSf();
      fallback.setStructuredFieldIntroducer(new StructuredFieldIntroducer());
      writer.handle(fallback);

      EPG_EndPage epg = new EPG_EndPage();
      epg.setName("P0000001");
      writer.handle(epg);
    }

    ByteBuffer bb = out.toByteBuffer();
    assertEquals(out.size(), bb.remaining());
    String fragment = StandardCharsets.UTF_8.decode(bb.duplicate()).toString();

    assertFalse(fragment.contains("<?xml"), "Fragment must not emit XML declaration");
    assertFalse(fragment.contains("<AfpFragments>"), "Fragment must not emit <AfpFragments>");
    assertFalse(fragment.contains("</AfpFragments>"), "Fragment must not emit </AfpFragments>");
    assertTrue(fragment.startsWith("<BPG_BeginPage "), "Fragment should start directly with first SF");
    assertTrue(fragment.contains("<NOP_NoOperation"));
    assertTrue(fragment.contains("<FallbackSf"));
    assertTrue(fragment.endsWith("</EPG_EndPage>"), "Fragment should end directly with last SF");

    // Must parse cleanly as XML children under a single root.
    String wrapped = "<AFPDocument>" + fragment + "</AFPDocument>";
    Document doc = DocumentBuilderFactory.newInstance().newDocumentBuilder()
        .parse(new ByteArrayInputStream(wrapped.getBytes(StandardCharsets.UTF_8)));
    assertEquals(4, doc.getDocumentElement().getChildNodes().getLength());
  }

  @Test
  public void testEmptyFragmentWriterEmitsZeroBytesAndClosesCleanly() throws Exception {
    ExposedByteArrayOutputStream out = new ExposedByteArrayOutputStream(64);
    try (AfpJacksonXmlWriter writer = new AfpJacksonXmlWriter(out, null, true, false)) {
      // No fields written
    }
    assertEquals(0, out.size());
    assertEquals(0, out.toByteBuffer().remaining());
  }

  @Test
  public void testStripFragmentWrapperFastPathAndLegacyFallback() {
    XmlHandlerFactory factory = new XmlHandlerFactory();

    byte[] headless = "<BPG_BeginPage/><EPG_EndPage/>".getBytes(StandardCharsets.UTF_8);
    ByteBuffer headlessBuf = ByteBuffer.wrap(headless);
    ByteBuffer resultHeadless = factory.stripFragmentWrapper(headlessBuf);
    assertSame(headlessBuf, resultHeadless);
    assertEquals("<BPG_BeginPage/><EPG_EndPage/>",
        StandardCharsets.UTF_8.decode(resultHeadless).toString());

    byte[] wrapped = "<AfpFragments><NOP_NoOperation/></AfpFragments>".getBytes(StandardCharsets.UTF_8);
    ByteBuffer resultWrapped = factory.stripFragmentWrapper(ByteBuffer.wrap(wrapped));
    assertEquals("<NOP_NoOperation/>",
        StandardCharsets.UTF_8.decode(resultWrapped).toString());
  }

  @Test
  public void testXpathFilteringWithHeadlessFragmentWriter() throws Exception {
    ByteArrayOutputStream out = new ByteArrayOutputStream();
    try (AfpJacksonXmlWriter writer = new AfpJacksonXmlWriter(out, "//name/text()")) {
      BPG_BeginPage bpg = new BPG_BeginPage();
      bpg.setName("PAGE_A");
      writer.handle(bpg);

      EPG_EndPage epg = new EPG_EndPage();
      epg.setName("PAGE_A");
      writer.handle(epg);
    }
    String result = out.toString(StandardCharsets.UTF_8).trim();
    assertEquals("PAGE_A\nPAGE_A", result.replace("\r\n", "\n"));
  }

  private static class FallbackSf extends StructuredField {
    @Override
    public void decodeAFP(byte[] sfData, int offset, int length, AFPParserConfiguration config) {
    }

    @Override
    public void writeAFP(OutputStream os, AFPParserConfiguration config) {
    }
  }
}
