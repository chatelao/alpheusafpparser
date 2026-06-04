package com.mgz.xml;

import com.mgz.afp.modca.BDT_BeginDocument;
import com.mgz.afp.modca.BPG_BeginPage;
import com.mgz.afp.ptoca.PTX_PresentationTextData;
import java.io.ByteArrayOutputStream;
import java.nio.charset.StandardCharsets;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Test to verify that XML indentation is correctly applied or suppressed.
 */
public class IndentationVerificationTest {

  @Test
  public void testCompactOutputByDefault() throws Exception {
    ByteArrayOutputStream baos = new ByteArrayOutputStream();
    try (AfpJacksonXmlWriter writer = new AfpJacksonXmlWriter(baos, null, false, false)) {
      BDT_BeginDocument bdt = new BDT_BeginDocument();
      bdt.setName("DOC001");
      writer.handle(bdt);
    }
    String xml = baos.toString(StandardCharsets.UTF_8);
    // Verify no newlines or indentation spaces between tags
    assertFalse(xml.contains("\n"), "Compact output should not contain newlines");
    // StructuredFieldBaseNameAndTriplets adds some fields by default (reserved8_9 and text)
    assertTrue(xml.contains("<BDT_BeginDocument><name>DOC001</name>"), "Tags should be adjacent in compact mode");
  }

  @Test
  public void testIndentedOutputWhenEnabled() throws Exception {
    ByteArrayOutputStream baos = new ByteArrayOutputStream();
    try (AfpJacksonXmlWriter writer = new AfpJacksonXmlWriter(baos, null, false, true)) {
      BDT_BeginDocument bdt = new BDT_BeginDocument();
      bdt.setName("DOC001");
      writer.handle(bdt);
    }
    String xml = baos.toString(StandardCharsets.UTF_8);
    // Verify presence of newlines and indentation
    assertTrue(xml.contains("\n"), "Indented output should contain newlines");
    assertTrue(xml.contains("\n  <BDT_BeginDocument>"), "Root element should be indented with 2 spaces");
    assertTrue(xml.contains("\n    <name>DOC001</name>"), "Child element should be indented with 4 spaces");
  }

  @Test
  public void testJacksonIndentation() throws Exception {
    ByteArrayOutputStream baos = new ByteArrayOutputStream();
    try (AfpJacksonXmlWriter writer = new AfpJacksonXmlWriter(baos, null, false, true)) {
      BPG_BeginPage bpg = new BPG_BeginPage();
      bpg.setName("PAGE001");
      writer.handle(bpg);
    }
    String xml = baos.toString(StandardCharsets.UTF_8);
    assertTrue(xml.contains("\n"), "Indented output should contain newlines");
    assertTrue(xml.contains("\n  <BPG_BeginPage>"), "Tags should have newlines and indents");
  }
}
