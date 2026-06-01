package com.mgz.xml;

import com.mgz.afp.modca.BDT_BeginDocument;
import java.io.ByteArrayOutputStream;
import java.nio.charset.StandardCharsets;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Test to verify that Jackson can serialize AFP objects.
 */
public class JacksonSerializationTest {

  /**
   * Tests Jackson serialization.
   *
   * @throws Exception if serialization fails
   */
  @Test
  public void testJacksonSerialization() throws Exception {
    BDT_BeginDocument bdt = new BDT_BeginDocument();
    bdt.setName("DOC001");

    // Jackson Serialization
    String jacksonXml = JacksonXmlMapperProvider.getMapper().writeValueAsString(bdt);

    // Verify key content exists
    assertTrue(jacksonXml.contains("BDT_BeginDocument"), "Jackson XML should contain root element");
    assertTrue(jacksonXml.contains("DOC001"), "Jackson XML should contain document name");

    // Verify indentation is disabled (no newlines)
    assertFalse(jacksonXml.contains("\n"), "Jackson XML should not contain newline characters when indentation is disabled");
  }
}
