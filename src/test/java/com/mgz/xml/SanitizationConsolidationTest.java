package com.mgz.xml;

import com.mgz.afp.modca.BDT_BeginDocument;
import com.mgz.afp.triplets.Triplet;
import java.io.ByteArrayOutputStream;
import java.nio.charset.StandardCharsets;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Test to verify that sanitization works correctly through the AfpJacksonXmlWriter
 * even after removing redundant sanitization from getters and serializers.
 */
public class SanitizationConsolidationTest {

  @Test
  public void testSanitizationInAfpJacksonXmlWriter() throws Exception {
    // 0x08 is an invalid XML 1.0 character
    String invalidText = "Invalid" + (char)0x08 + "Char";

    BDT_BeginDocument bdt = new BDT_BeginDocument();
    bdt.setName("DOC1");
    // We'll use a comment triplet which NO LONGER uses sanitization in its getter
    Triplet.Comment comment = new Triplet.Comment();
    comment.setComment(invalidText);
    bdt.addTriplet(comment);

    ByteArrayOutputStream baos = new ByteArrayOutputStream();
    try (AfpJacksonXmlWriter writer = new AfpJacksonXmlWriter(baos)) {
      writer.handle(bdt);
    }

    String xml = baos.toString(StandardCharsets.UTF_8);

    // Verify it doesn't contain the invalid character
    assertFalse(xml.contains("\b"), "XML should not contain invalid character 0x08");
    // Sanitizer replaces with space
    assertTrue(xml.contains("Invalid Char"), "Invalid character should be replaced with space");
  }
}
