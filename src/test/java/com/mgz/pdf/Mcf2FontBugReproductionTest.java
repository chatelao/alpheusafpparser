package com.mgz.pdf;

import com.mgz.afp.enums.AFPUnitBase;
import com.mgz.afp.modca.MCF_MapCodedFont_Format2;
import com.mgz.afp.modca.PGD_PageDescriptor;
import com.mgz.afp.triplets.Triplet;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class Mcf2FontBugReproductionTest {

    @Test
    public void testMcf2FontBugs() throws Exception {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PdfHandler handler = new PdfHandler(baos);

        // 1. Page Descriptor (600 DPI -> 720/6000 = 0.12 scale)
        PGD_PageDescriptor pgd = new PGD_PageDescriptor();
        pgd.setxUnitBase(AFPUnitBase.Inches10);
        pgd.setyUnitBase(AFPUnitBase.Inches10);
        pgd.setxUnitsPerUnitBase((short) 6000);
        pgd.setyUnitsPerUnitBase((short) 6000);
        pgd.setxSize(7016);
        pgd.setySize(4961);
        handler.handle(pgd);

        assertEquals(0.12f, handler.getDefaultScaleY(), 0.001f);

        // 2. MCF Format 2 with FontDescriptorSpecification
        MCF_MapCodedFont_Format2 mcf2 = new MCF_MapCodedFont_Format2();
        MCF_MapCodedFont_Format2.MCF_RepeatingGroup rg = new MCF_MapCodedFont_Format2.MCF_RepeatingGroup();

        Triplet.ResourceLocalIdentifier rli = new Triplet.ResourceLocalIdentifier();
        rli.setResourceType(Triplet.ResourceLocalIdentifier.RLI_ResourceType.CodedFont);
        rli.setResourceLocalID((short) 1);
        rg.addTriplet(rli);

        Triplet.FullyQualifiedName fqn = new Triplet.FullyQualifiedName();
        fqn.setType(Triplet.GlobalID_Use.CodedFontNameReference);
        fqn.setNameAsString("CZN481");
        rg.addTriplet(fqn);

        Triplet.FontDescriptorSpecification fds = new Triplet.FontDescriptorSpecification();
        fds.fontHeight = 200;
        rg.addTriplet(fds);

        mcf2.addRepeatingGroup(rg);
        handler.handle(mcf2);

        Map<Short, PdfHandler.FontResource> fontMap = handler.getFontMap();
        PdfHandler.FontResource resource = fontMap.get((short) 1);

        // Verify font size is 10.0pt (200 * 0.05) regardless of PGD scale (which was 0.12)
        assertEquals(10.0f, resource.size(), 0.01f, "Font size should be calculated using fixed 1/1440 scale");

        // Verify CZN maps to Helvetica (Sans-Serif)
        com.itextpdf.kernel.font.PdfFont font = handler.getFontRegistry().getFontWithFallback(resource.name());
        String fontName = font.getFontProgram().getFontNames().getFontName();
        assertTrue(fontName.contains("Helvetica"), "CZN font should map to Helvetica, but was " + fontName);
    }
}
