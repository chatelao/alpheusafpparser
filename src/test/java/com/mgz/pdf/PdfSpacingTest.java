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

import com.mgz.afp.modca.BMO_BeginOverlay;
import com.mgz.afp.modca.EMO_EndOverlay;
import com.mgz.afp.modca.PGD_PageDescriptor;
import com.mgz.afp.ptoca.PTD_PresentationTextDataDescriptor_Format2;
import com.mgz.afp.ptoca.PTX_PresentationTextData;
import com.mgz.afp.ptoca.controlSequence.PTOCAControlSequence;
import com.mgz.afp.enums.AFPUnitBase;
import org.junit.jupiter.api.Test;
import java.io.ByteArrayOutputStream;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Verifies that text in Overlays advances horizontally and that SVI/SIA interaction is correct.
 */
public class PdfSpacingTest {

  @Test
  public void testTextAdvanceInOverlay() throws Exception {
    ByteArrayOutputStream baos = new ByteArrayOutputStream();
    PdfHandler handler = new PdfHandler(baos);

    // 1. Set Page Descriptor (1440 units per inch, 14400 per 10 inches)
    PGD_PageDescriptor pgd = new PGD_PageDescriptor();
    pgd.setxUnitBase(AFPUnitBase.Inches10);
    pgd.setyUnitBase(AFPUnitBase.Inches10);
    pgd.setxUnitsPerUnitBase((short) 14400);
    pgd.setyUnitsPerUnitBase((short) 14400);
    pgd.setxSize(14400); // 10 inches
    pgd.setySize(14400);
    handler.handle(pgd);

    // 2. Begin Overlay (defaultPageWidth will not be set for this context if bug exists)
    BMO_BeginOverlay bmo = new BMO_BeginOverlay();
    bmo.setName("OVL001");
    handler.handle(bmo);

    // 3. Render first text segment
    PTX_PresentationTextData ptx1 = new PTX_PresentationTextData();
    PTOCAControlSequence.GraphicCharacters gc1 = new PTOCAControlSequence.GraphicCharacters();
    gc1.setText("Hello");
    ptx1.addControlSequence(gc1);
    handler.handle(ptx1);

    int posAfterHello = handler.getTextState().getInlinePos();
    assertTrue(posAfterHello > 0, "Inline position should advance after 'Hello', even in Overlay");

    // 4. Render second text segment
    PTX_PresentationTextData ptx2 = new PTX_PresentationTextData();
    PTOCAControlSequence.GraphicCharacters gc2 = new PTOCAControlSequence.GraphicCharacters();
    gc2.setText("World");
    ptx2.addControlSequence(gc2);
    handler.handle(ptx2);

    int posAfterWorld = handler.getTextState().getInlinePos();
    assertTrue(posAfterWorld > posAfterHello, "Inline position should advance after 'World'");

    handler.handle(new EMO_EndOverlay());
    handler.close();
  }

  @Test
  public void testSviSiaInteraction() throws Exception {
    ByteArrayOutputStream baos = new ByteArrayOutputStream();
    PdfHandler handler = new PdfHandler(baos);

    // Set 1440 dpi
    PGD_PageDescriptor pgd = new PGD_PageDescriptor();
    pgd.setxUnitBase(AFPUnitBase.Inches10);
    pgd.setyUnitBase(AFPUnitBase.Inches10);
    pgd.setxUnitsPerUnitBase((short) 14400);
    pgd.setyUnitsPerUnitBase((short) 14400);
    handler.handle(pgd);

    // PTX with SVI and SIA
    PTX_PresentationTextData ptx = new PTX_PresentationTextData();

    // SIA = 100 units
    var sia = new PTOCAControlSequence.SIA_SetIntercharacterAdjustment();
    sia.setAdjustment((short) 100);
    ptx.addControlSequence(sia);

    // SVI = 500 units
    var svi = new PTOCAControlSequence.SVI_SetVariableSpaceCharacterIncrement();
    svi.setIncrement((short) 500);
    ptx.addControlSequence(svi);

    // Text " " (single space)
    PTOCAControlSequence.GraphicCharacters gc = new PTOCAControlSequence.GraphicCharacters();
    gc.setText(" ");
    ptx.addControlSequence(gc);

    handler.handle(ptx);

    // Space width should be SVI + SIA = 500 + 100 = 600
    assertEquals(600, handler.getTextState().getInlinePos(),
        "Space width should be SVI + SIA (600 units)");

    handler.close();
  }

  @Test
  public void testPtdResolutionScaling() throws Exception {
    ByteArrayOutputStream baos = new ByteArrayOutputStream();
    PdfHandler handler = new PdfHandler(baos);

    // 1. Set Page Descriptor at 1440 dpi (14400 per 10 inches)
    PGD_PageDescriptor pgd = new PGD_PageDescriptor();
    pgd.setxUnitBase(AFPUnitBase.Inches10);
    pgd.setyUnitBase(AFPUnitBase.Inches10);
    pgd.setxUnitsPerUnitBase((short) 14400);
    pgd.setyUnitsPerUnitBase((short) 14400);
    handler.handle(pgd);

    assertEquals(0.05f, handler.getDefaultScaleY(), 0.0001f);

    // 2. Set PTD at 2400 dpi (24000 per 10 inches)
    PTD_PresentationTextDataDescriptor_Format2 ptd =
        new PTD_PresentationTextDataDescriptor_Format2();
    ptd.setxUnitBase(AFPUnitBase.Inches10);
    ptd.setyUnitBase(AFPUnitBase.Inches10);
    ptd.setxUnitsPerUnitBase((short) 24000);
    ptd.setyUnitsPerUnitBase((short) 24000);
    handler.handle(ptd);

    // 720 / 24000 = 0.03
    assertEquals(720.0f / 24000.0f, handler.getDefaultScaleY(), 0.0001f);

    handler.close();
  }
}
