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

package com.mgz.cli;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.PrintStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

/**
 * Verifies Phase 1 optimizations in {@link Afp2Xml} directory mode:
 * ThreadLocal direct ByteBuffer reuse, MMap unmapping, LPT scheduling, and stdout ordering.
 */
public class Afp2XmlDirectoryModeTest {

  @TempDir
  Path tempDir;

  @Test
  void testMixedSizeDirectoryConversionMatchesSingleFileXml() throws Exception {
    Path inputDir = tempDir.resolve("input");
    Path outputDir = tempDir.resolve("output");
    Path singleDir = tempDir.resolve("single");
    Files.createDirectories(inputDir);
    Files.createDirectories(outputDir);
    Files.createDirectories(singleDir);

    File smallAfp = new File("src/test/resources/afp/minimal.afp");
    File largeAfp = new File("src/test/resources/afp/external/afplib_start.afp");
    assertTrue(smallAfp.exists(), "minimal.afp must exist");
    assertTrue(largeAfp.exists(), "afplib_start.afp must exist");

    // Copy small files (<< 64KB direct buffer threshold) and >64KB files (MMap path)
    // Use 12 files across 2 threads so ThreadLocal direct buffer is reused across multiple files.
    for (int i = 0; i < 10; i++) {
      Files.copy(smallAfp.toPath(), inputDir.resolve(String.format("small_%02d.afp", i)),
          StandardCopyOption.REPLACE_EXISTING);
    }
    for (int i = 0; i < 2; i++) {
      Files.copy(largeAfp.toPath(), inputDir.resolve(String.format("large_%02d.afp", i)),
          StandardCopyOption.REPLACE_EXISTING);
    }

    int dirExit = Afp2Xml.execute(new String[]{
        "-t", "2", "-p", "-d", inputDir.toString(), outputDir.toString()
    });
    assertEquals(0, dirExit, "Directory mode XML conversion should succeed");

    Path expectedSmallXml = singleDir.resolve("expected_small.xml");
    assertEquals(0, Afp2Xml.execute(new String[]{
        smallAfp.getAbsolutePath(), expectedSmallXml.toString()
    }));
    byte[] expectedSmallBytes = Files.readAllBytes(expectedSmallXml);

    Path expectedLargeXml = singleDir.resolve("expected_large.xml");
    assertEquals(0, Afp2Xml.execute(new String[]{
        largeAfp.getAbsolutePath(), expectedLargeXml.toString()
    }));
    byte[] expectedLargeBytes = Files.readAllBytes(expectedLargeXml);

    for (int i = 0; i < 10; i++) {
      Path actual = outputDir.resolve(String.format("small_%02d.afp.xml", i));
      assertTrue(Files.exists(actual), "Missing output XML for small file " + i);
      assertArrayEquals(expectedSmallBytes, Files.readAllBytes(actual),
          "Small file XML mismatch at index " + i);
    }
    for (int i = 0; i < 2; i++) {
      Path actual = outputDir.resolve(String.format("large_%02d.afp.xml", i));
      assertTrue(Files.exists(actual), "Missing output XML for large file " + i);
      assertArrayEquals(expectedLargeBytes, Files.readAllBytes(actual),
          "Large file XML mismatch at index " + i);
    }
  }

  @Test
  void testDirectoryStdoutRemainsDeterministic() throws Exception {
    Path inputDir = tempDir.resolve("stdout_in");
    Files.createDirectories(inputDir);

    File smallAfp = new File("src/test/resources/afp/minimal.afp");
    File largeAfp = new File("src/test/resources/afp/external/afplib_start.afp");
    // Name smaller file "a_first.afp" and larger file "z_second.afp" to verify alphabetical stdout order
    Files.copy(smallAfp.toPath(), inputDir.resolve("a_first.afp"), StandardCopyOption.REPLACE_EXISTING);
    Files.copy(largeAfp.toPath(), inputDir.resolve("z_second.afp"), StandardCopyOption.REPLACE_EXISTING);

    PrintStream origOut = System.out;
    ByteArrayOutputStream captured = new ByteArrayOutputStream();
    try {
      System.setOut(new PrintStream(captured));
      int exit = Afp2Xml.execute(new String[]{"-t", "2", "-d", inputDir.toString(), "-"});
      assertEquals(0, exit, "Directory stdout conversion should succeed");
    } finally {
      System.setOut(origOut);
    }

    String xml = captured.toString();
    int firstEnd = xml.indexOf("</AFPDocument>");
    int secondStart = xml.lastIndexOf("<AFPDocument");
    assertTrue(firstEnd > 0 && secondStart > firstEnd, "Both documents should be emitted in order");
    // minimal.afp only has BDT/EDT (~2 fields), while afplib_start.afp is much larger
    assertTrue(firstEnd < xml.length() / 4,
        "Alphabetically first file (a_first.afp) must appear before larger z_second.afp");
  }

  @Test
  void testEmptyAfpFileDoesNotFail() throws Exception {
    Path emptyFile = tempDir.resolve("empty.afp");
    Path outXml = tempDir.resolve("empty.afp.xml");
    Files.write(emptyFile, new byte[0]);

    int exit = Afp2Xml.execute(new String[]{emptyFile.toString(), outXml.toString()});
    assertEquals(0, exit, "Empty AFP file conversion should complete cleanly");
    assertTrue(Files.exists(outXml));
    assertTrue(Files.readString(outXml).contains("<AFPDocument"), "Output should contain valid empty root");
  }
}
