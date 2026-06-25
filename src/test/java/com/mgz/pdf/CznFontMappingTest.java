package com.mgz.pdf;

import com.itextpdf.kernel.font.PdfFont;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class CznFontMappingTest {
    @Test
    public void testCznMapping() {
        PdfFontRegistry registry = new PdfFontRegistry();

        // CZN is an outline font (CZ prefix) with family 'N'.
        // According to memory, CZN should be Sans-Serif (Helvetica).
        // C0N (raster) should be Serif (Times).

        PdfFont fontCzn = registry.getFont("CZN481");
        assertNotNull(fontCzn, "Font CZN481 should be found");
        String fontNameCzn = fontCzn.getFontProgram().getFontNames().getFontName();
        System.out.println("CZN481 maps to: " + fontNameCzn);

        PdfFont fontC0n = registry.getFont("C0N481");
        assertNotNull(fontC0n, "Font C0N481 should be found");
        String fontNameC0n = fontC0n.getFontProgram().getFontNames().getFontName();
        System.out.println("C0N481 maps to: " + fontNameC0n);

        assertTrue(fontNameCzn.contains("Helvetica"), "CZN should be Helvetica (Sans-Serif) but was " + fontNameCzn);
        assertTrue(fontNameC0n.contains("Times"), "C0N should be Times (Serif) but was " + fontNameC0n);

        PdfFont fontXzn = registry.getFont("XZN481");
        assertNotNull(fontXzn);
        assertTrue(fontXzn.getFontProgram().getFontNames().getFontName().contains("Helvetica"));

        PdfFont fontX0n = registry.getFont("X0N481");
        assertNotNull(fontX0n);
        assertTrue(fontX0n.getFontProgram().getFontNames().getFontName().contains("Times"));
    }
}
