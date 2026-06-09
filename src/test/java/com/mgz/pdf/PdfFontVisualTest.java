package com.mgz.pdf;

import com.mgz.cli.Afp2Xml;
import org.junit.jupiter.api.Test;
import java.awt.image.BufferedImage;
import java.io.File;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class PdfFontVisualTest {

    @Test
    public void testFontVariations() throws Exception {
        File inputAfp = new File("src/test/resources/afp/font_test.afp");
        File outputPdf = new File("test_output/font_test.pdf");
        File outputPng = new File("test_output/font_test.png");

        outputPdf.getParentFile().mkdirs();

        // Convert AFP to PDF
        Afp2Xml.execute(new String[]{"-f", "pdf", inputAfp.getAbsolutePath(), outputPdf.getAbsolutePath()});

        assertTrue(outputPdf.exists(), "PDF should have been created");

        // Rasterize to PNG - using lower DPI to avoid OOM
        BufferedImage img = PdfVerificationUtils.rasterize(outputPdf, 72.0f);
        PdfVerificationUtils.saveImage(img, "png", outputPng);

        assertTrue(outputPng.exists(), "PNG should have been created");
    }
}
