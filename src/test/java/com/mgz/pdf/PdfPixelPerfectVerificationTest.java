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

import com.mgz.cli.Afp2Xml;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.awt.image.BufferedImage;
import java.io.File;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

/**
 * Performs pixel-perfect verification of PDF output against golden baselines.
 */
public class PdfPixelPerfectVerificationTest {

  @TempDir
  Path tempDir;

  private static final float DPI = 72.0f;
  private static final int TOLERANCE = 0;

  @ParameterizedTest
  @ValueSource(strings = {
      "minimal.afp",
      "perf_ptx.afp",
      "afp-goca-reference-03/Chapter_1.afp",
      "afp-goca-reference-03/Chapter_4.afp",
      "afp-goca-reference-03/Chapter_6.afp"
  })
  public void testPixelPerfectVerification(String afpFileName) throws Exception {
    File inputFile = new File("src/test/resources/afp/" + afpFileName);
    File outputFile = tempDir.resolve(afpFileName.replace('/', '_') + ".pdf").toFile();
    File baselineFile = new File("src/test/resources/baselines/pdf/" + afpFileName + ".png");

    assertTrue(inputFile.exists(), "Input AFP file should exist: " + inputFile.getAbsolutePath());

    // 1. Convert AFP to PDF
    String[] args = {
        "-f", "pdf",
        inputFile.getAbsolutePath(),
        outputFile.getAbsolutePath()
    };
    int result = Afp2Xml.execute(args);
    assertEquals(0, result, "Conversion should succeed for " + afpFileName);

    // 2. Rasterize PDF
    BufferedImage actualImage = PdfVerificationUtils.rasterize(outputFile, DPI);

    // If the baseline file does not exist, generate it and fail the test to prevent silent passing
    if (!baselineFile.exists()) {
        File parentDir = baselineFile.getParentFile();
        if (parentDir != null) {
            parentDir.mkdirs();
        }
        PdfVerificationUtils.saveImage(actualImage, "png", baselineFile);
        fail("Baseline image did not exist and was generated on-the-fly. Please review the generated image and commit it: " + baselineFile.getAbsolutePath());
    }

    // 3. Load Baseline
    BufferedImage baselineImage = javax.imageio.ImageIO.read(baselineFile);

    // 4. Compare
    boolean match = PdfVerificationUtils.compareImagesWithTolerance(baselineImage, actualImage, TOLERANCE);

    if (!match) {
        File diffFile = tempDir.resolve(afpFileName + "_failed.png").toFile();
        PdfVerificationUtils.saveImage(actualImage, "png", diffFile);
        System.err.println("Pixel-perfect mismatch for " + afpFileName + ". Actual output saved to: " + diffFile.getAbsolutePath());
    }

    assertTrue(match, "PDF output for " + afpFileName + " should match golden baseline.");
  }
}
