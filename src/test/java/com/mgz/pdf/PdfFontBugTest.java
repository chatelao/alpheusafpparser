package com.mgz.pdf;

import com.mgz.afp.enums.AFPUnitBase;
import com.mgz.afp.modca.MCF_MapCodedFont_Format2;
import com.mgz.afp.modca.PGD_PageDescriptor;
import com.mgz.afp.triplets.Triplet;
import com.mgz.afp.triplets.Triplet.FontDescriptorSpecification.FDS_FontDsFlag;
import com.mgz.afp.triplets.Triplet.FontDescriptorSpecification.FDS_FontWeigthClass;
import com.mgz.afp.triplets.Triplet.ResourceLocalIdentifier.RLI_ResourceType;
import com.itextpdf.kernel.font.PdfFont;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.util.EnumSet;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class PdfFontBugTest {

  @Test
  public void testFontSizeAndFamilyBugs() throws Exception {
    PdfHandler handler = new PdfHandler(new ByteArrayOutputStream());

    // 1. Set PGD to 600 DPI
    PGD_PageDescriptor pgd = new PGD_PageDescriptor();
    pgd.setxUnitBase(AFPUnitBase.Inches10);
    pgd.setyUnitBase(AFPUnitBase.Inches10);
    pgd.setxUnitsPerUnitBase((short) 6000);
    pgd.setyUnitsPerUnitBase((short) 6000);
    pgd.setxSize(7016);
    pgd.setySize(4961);
    handler.handle(pgd);

    // Verify defaultScaleY is 0.12 (720 / 6000)
    assertEquals(0.12f, handler.getDefaultScaleY(), 0.0001f);

    // 2. Map CZN481 (Nimbus Sans Bold) with height 200
    MCF_MapCodedFont_Format2 mcf2 = new MCF_MapCodedFont_Format2();
    MCF_MapCodedFont_Format2.MCF_RepeatingGroup rg = new MCF_MapCodedFont_Format2.MCF_RepeatingGroup();

    Triplet.FullyQualifiedName fqn = new Triplet.FullyQualifiedName();
    fqn.setType(Triplet.GlobalID_Use.FontCharacterSetNameReference);
    fqn.setNameAsString("CZN481  ");
    rg.addTriplet(fqn);

    Triplet.ResourceLocalIdentifier rli = new Triplet.ResourceLocalIdentifier();
    rli.setResourceType(RLI_ResourceType.CodedFont);
    rli.setResourceLocalID((short) 1);
    rg.addTriplet(rli);

    Triplet.FontDescriptorSpecification fds = new Triplet.FontDescriptorSpecification();
    fds.fontHeight = 200;
    // User XML says NotSpecified for weight, but name CZN481 usually means Bold (4)
    fds.fontWeigthClass = FDS_FontWeigthClass.NotSpecified;
    fds.fontDsFlags = EnumSet.of(FDS_FontDsFlag.NoItalicCharacters);
    rg.addTriplet(fds);

    mcf2.addRepeatingGroup(rg);
    handler.handle(mcf2);

    Map<Short, PdfHandler.FontResource> fontMap = handler.getFontMap();
    PdfHandler.FontResource res = fontMap.get((short) 1);

    // CURRENT BUG: Size is 24.0 (200 * 0.12)
    // EXPECTED: Size should be 10.0 (200 / 20)
    System.out.println("Resolved Font Size: " + res.size());

    // CURRENT BUG: CZN481 resolves to Times (Serif) because of 'N' family
    // EXPECTED: Should resolve to Helvetica (Sans-Serif)
    PdfFont font = handler.getFontRegistry().getFont(res.name(), res.bold(), res.italic());
    if (font == null) {
        font = handler.getFontRegistry().getFontWithFallback(res.name(), res.bold(), res.italic());
    }
    String fontName = font.getFontProgram().getFontNames().getFontName();
    System.out.println("Resolved Font Name: " + fontName);

    // Assertions representing the DESIRED state (will fail currently)
    assertEquals(10.0f, res.size(), "Font size should be 10.0 (200 / 20)");
    assertTrue(fontName.contains("Helvetica"), "Font should be Helvetica (Sans-Serif), but was " + fontName);
  }
}
