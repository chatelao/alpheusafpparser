package com.mgz.xml;

import com.fasterxml.jackson.databind.ObjectWriter;
import com.mgz.afp.modca.BDT_BeginDocument;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Test to verify that Jackson ObjectWriter caching is working as expected.
 */
public class JacksonWriterCacheTest {

  @Test
  public void testWriterCaching() {
    Class<BDT_BeginDocument> clazz = BDT_BeginDocument.class;

    ObjectWriter writer1 = JacksonXmlMapperProvider.getCachedWriter(clazz, false, false);
    ObjectWriter writer2 = JacksonXmlMapperProvider.getCachedWriter(clazz, false, false);
    assertSame(writer1, writer2, "Should return the same writer instance for the same configuration");

    ObjectWriter writerFragment = JacksonXmlMapperProvider.getCachedWriter(clazz, true, false);
    assertNotSame(writer1, writerFragment, "Fragment writer should be different from non-fragment writer");

    ObjectWriter writerIndent = JacksonXmlMapperProvider.getCachedWriter(clazz, false, true);
    assertNotSame(writer1, writerIndent, "Indented writer should be different from non-indented writer");

    ObjectWriter writerFragmentIndent = JacksonXmlMapperProvider.getCachedWriter(clazz, true, true);
    assertNotSame(writerFragment, writerFragmentIndent, "Indented fragment writer should be different from non-indented fragment writer");
  }

  @Test
  public void testWriterSerialization() throws Exception {
    BDT_BeginDocument bdt = new BDT_BeginDocument();
    bdt.setName("TESTDOC");

    ObjectWriter writer = JacksonXmlMapperProvider.getCachedWriter(BDT_BeginDocument.class, true, false);
    String xml = writer.writeValueAsString(bdt);

    assertTrue(xml.contains("BDT_BeginDocument"), "Serialized XML should contain root name");
    assertTrue(xml.contains("TESTDOC"), "Serialized XML should contain property value");
  }
}
