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

import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class PdfVerificationUtilsTest {

  @Test
  public void testRasterizeAndCompare() throws IOException {
    // 1. Create a simple PDF using iText
    ByteArrayOutputStream baos = new ByteArrayOutputStream();
    PdfWriter writer = new PdfWriter(baos);
    PdfDocument pdfDoc = new PdfDocument(writer);
    Document document = new Document(pdfDoc, PageSize.A4);
    document.add(new Paragraph("Hello PDF Verification!"));
    document.close();

    byte[] pdfBytes = baos.toByteArray();

    // 2. Rasterize the PDF
    BufferedImage img1 = PdfVerificationUtils.rasterize(new ByteArrayInputStream(pdfBytes), 72);
    Assertions.assertNotNull(img1);

    // A4 at 72 DPI is roughly 595x842
    Assertions.assertEquals(595, img1.getWidth());
    Assertions.assertEquals(842, img1.getHeight());

    // 3. Rasterize again and compare
    BufferedImage img2 = PdfVerificationUtils.rasterize(new ByteArrayInputStream(pdfBytes), 72);
    Assertions.assertTrue(PdfVerificationUtils.compareImages(img1, img2));

    // 4. Compare with tolerance
    Assertions.assertTrue(PdfVerificationUtils.compareImagesWithTolerance(img1, img2, 0));
    Assertions.assertTrue(PdfVerificationUtils.compareImagesWithTolerance(img1, img2, 5));

    // 5. Negative test (different DPI)
    BufferedImage img3 = PdfVerificationUtils.rasterize(new ByteArrayInputStream(pdfBytes), 144);
    Assertions.assertFalse(PdfVerificationUtils.compareImages(img1, img3));
  }
}
