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

import java.awt.image.BufferedImage;
import java.io.File;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Regression test for font scaling issue when redundant PGD fields are present.
 */
public class PdfFontScalingTest {

  @TempDir
  Path tempDir;

  @Test
  public void testRedundantPgdScaling() throws Exception {
    File baselineAfp = new File("src/test/resources/afp/baseline_scaling.afp");
    File redundantAfp = new File("src/test/resources/afp/redundant_scaling.afp");

    File baselinePdf = tempDir.resolve("baseline.pdf").toFile();
    File redundantPdf = tempDir.resolve("redundant.pdf").toFile();

    // Convert baseline
    Afp2Xml.execute(new String[]{"-f", "pdf", baselineAfp.getAbsolutePath(), baselinePdf.getAbsolutePath()});
    // Convert redundant
    Afp2Xml.execute(new String[]{"-f", "pdf", redundantAfp.getAbsolutePath(), redundantPdf.getAbsolutePath()});

    assertTrue(baselinePdf.exists());
    assertTrue(redundantPdf.exists());

    // Rasterize both at 72 DPI
    BufferedImage baselineImg = PdfVerificationUtils.rasterize(baselinePdf, 72.0f);
    BufferedImage redundantImg = PdfVerificationUtils.rasterize(redundantPdf, 72.0f);

    // Save for manual inspection if needed (optional)
    // PdfVerificationUtils.saveImage(baselineImg, "png", new File("baseline.png"));
    // PdfVerificationUtils.saveImage(redundantImg, "png", new File("redundant.png"));

    // They should be identical. If the bug is present, redundantImg will have smaller text.
    boolean identical = PdfVerificationUtils.compareImages(baselineImg, redundantImg);

    assertTrue(identical, "PDFs should be identical regardless of redundant PGD fields. " +
        "If this fails, check if redundant coordinate transformations are being applied.");
  }
}
