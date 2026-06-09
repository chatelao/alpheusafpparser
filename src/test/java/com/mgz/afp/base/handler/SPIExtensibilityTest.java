package com.mgz.afp.base.handler;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.mgz.cli.Afp2Xml;
import java.io.File;
import java.nio.file.Files;
import java.util.Set;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

public class SPIExtensibilityTest {

  @TempDir
  File tempDir;

  @Test
  public void testTestFormatDiscovery() {
    Set<String> formats = HandlerRegistry.getRegisteredFormats();
    assertTrue(formats.contains("testformat"), "Registry should contain 'testformat' discovered via SPI from test resources. Found: " + formats);

    HandlerFactory factory = HandlerRegistry.getFactory("testformat");
    assertNotNull(factory);
    assertTrue(factory instanceof com.mgz.testplugin.TestHandlerFactory);
  }

  @Test
  public void testCliWithTestFormat() throws Exception {
    File inputFile = new File("src/test/resources/afp/minimal.afp");
    File outputFile = new File(tempDir, "output.test");

    String[] args = {
        "-f", "testformat",
        inputFile.getAbsolutePath(),
        outputFile.getAbsolutePath()
    };

    int exitCode = Afp2Xml.execute(args);
    assertEquals(0, exitCode, "CLI should exit with 0 for 'testformat'");
    assertTrue(outputFile.exists(), "Output file should be created");

    String content = Files.readString(outputFile.toPath());
    assertTrue(content.contains("TEST: BDT"), "Output should contain BDT mnemonic in TEST format. Found: " + content);
  }
}
