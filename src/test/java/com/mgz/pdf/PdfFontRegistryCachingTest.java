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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.kernel.pdf.canvas.PdfCanvas;
import com.mgz.afp.base.StructuredField;
import com.mgz.afp.base.StructuredFieldIntroducer;
import com.mgz.afp.modca.NOP_NoOperation;
import com.mgz.xml.AfpJacksonXmlWriter;
import java.awt.color.ColorSpace;
import java.awt.color.ICC_Profile;
import java.io.ByteArrayOutputStream;
import java.lang.reflect.Field;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;
import org.apache.pdfbox.Loader;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

/**
 * Verifies shared caching and lazy initialization in Step 1.2:
 * - Static FontProgram reuse across independent PdfDocument instances
 * - Lazy FontProvider / FontSet initialization in PdfFontRegistry
 * - ICC profile byte caching in PdfHandlerFactory
 * - Lazy baseFragmentGenerator initialization in AfpJacksonXmlWriter
 */
public class PdfFontRegistryCachingTest {

  @Test
  public void testSharedFontProgramWorksAcrossMultipleClosedDocuments() throws Exception {
    PdfFontRegistry firstRegistry = new PdfFontRegistry();
    PdfFontRegistry secondRegistry = new PdfFontRegistry();

    PdfFont firstFont = firstRegistry.getFont("C0H20010");
    PdfFont secondFont = secondRegistry.getFont("C0H20010");
    assertNotNull(firstFont);
    assertNotNull(secondFont);
    assertNotSame(firstFont, secondFont, "PdfFont instances must remain per-registry/document");
    assertSame(firstFont.getFontProgram(), secondFont.getFontProgram(),
        "Underlying immutable FontProgram should be shared across registries");

    ByteArrayOutputStream out1 = new ByteArrayOutputStream();
    try (PdfDocument doc1 = new PdfDocument(new PdfWriter(out1))) {
      PdfCanvas canvas = new PdfCanvas(doc1.addNewPage());
      canvas.beginText().setFontAndSize(firstFont, 12).moveText(50, 700).showText("Doc1Text").endText();
    }

    ByteArrayOutputStream out2 = new ByteArrayOutputStream();
    try (PdfDocument doc2 = new PdfDocument(new PdfWriter(out2))) {
      PdfCanvas canvas = new PdfCanvas(doc2.addNewPage());
      canvas.beginText().setFontAndSize(secondFont, 12).moveText(50, 700).showText("Doc2Text").endText();
    }

    try (PDDocument pd1 = Loader.loadPDF(out1.toByteArray());
         PDDocument pd2 = Loader.loadPDF(out2.toByteArray())) {
      assertTrue(new PDFTextStripper().getText(pd1).contains("Doc1Text"));
      assertTrue(new PDFTextStripper().getText(pd2).contains("Doc2Text"));
    }
  }

  @Test
  public void testFontProviderAndFontSetAreLazilyInitialized() throws Exception {
    PdfFontRegistry registry = new PdfFontRegistry();
    Field fontProviderField = PdfFontRegistry.class.getDeclaredField("fontProvider");
    fontProviderField.setAccessible(true);
    Field fontSetField = PdfFontRegistry.class.getDeclaredField("fontSet");
    fontSetField.setAccessible(true);

    assertNull(fontProviderField.get(registry), "FontProvider should not be created eagerly");
    assertNull(fontSetField.get(registry), "FontSet should not be created eagerly");

    // Placeholder document creation via PdfHandler should not trigger FontProvider init either.
    ByteArrayOutputStream pdfOut = new ByteArrayOutputStream();
    try (PdfHandler handler = new PdfHandler(pdfOut, registry)) {
      NOP_NoOperation nop = new NOP_NoOperation();
      nop.setStructuredFieldIntroducer(new StructuredFieldIntroducer());
      nop.setData(new byte[] {0x40});
      handler.handle(nop);
    }
    assertNull(fontProviderField.get(registry), "PdfHandler must not eagerly initialize FontProvider");
    assertTrue(pdfOut.size() > 0);

    assertNotNull(registry.getFontProvider());
    assertNotNull(registry.getFontSet());
  }

  @Test
  public void testPdfHandlerFactoryCachesIccProfileBytesAcrossDocuments(@TempDir Path tempDir)
      throws Exception {
    Path iccFile = tempDir.resolve("srgb.icc");
    Files.write(iccFile, ICC_Profile.getInstance(ColorSpace.CS_sRGB).getData());

    PdfHandlerFactory factory = new PdfHandlerFactory();
    factory.configure(Map.of("icc-profile", iccFile.toString()));

    // Delete file after configure() to prove createHandler uses cached ICC bytes without disk I/O.
    Files.delete(iccFile);

    ByteArrayOutputStream pdf1 = new ByteArrayOutputStream();
    try (var handler1 = factory.createHandler(pdf1, false)) {
      NOP_NoOperation nop = new NOP_NoOperation();
      nop.setStructuredFieldIntroducer(new StructuredFieldIntroducer());
      nop.setData(new byte[0]);
      handler1.handle(nop);
    }

    ByteArrayOutputStream pdf2 = new ByteArrayOutputStream();
    try (var handler2 = factory.createHandler(pdf2, false)) {
      NOP_NoOperation nop = new NOP_NoOperation();
      nop.setStructuredFieldIntroducer(new StructuredFieldIntroducer());
      nop.setData(new byte[0]);
      handler2.handle(nop);
    }

    try (PDDocument pd1 = Loader.loadPDF(pdf1.toByteArray());
         PDDocument pd2 = Loader.loadPDF(pdf2.toByteArray())) {
      assertEquals(1, pd1.getDocumentCatalog().getOutputIntents().size());
      assertEquals(1, pd2.getDocumentCatalog().getOutputIntents().size());
    }
  }

  @Test
  public void testAfpJacksonXmlWriterBaseFragmentGeneratorIsLazy() throws Exception {
    ByteArrayOutputStream out = new ByteArrayOutputStream();
    Field genField = AfpJacksonXmlWriter.class.getDeclaredField("baseFragmentGenerator");
    genField.setAccessible(true);

    try (AfpJacksonXmlWriter writer = new AfpJacksonXmlWriter(out, null, false, false)) {
      assertNull(genField.get(writer), "Generator should remain null before fallback use");

      NOP_NoOperation nop = new NOP_NoOperation();
      nop.setStructuredFieldIntroducer(new StructuredFieldIntroducer());
      nop.setData(new byte[] {(byte) 0xC1});
      writer.handle(nop);
      assertNull(genField.get(writer), "Fast-path NOP should not instantiate ToXmlGenerator");

      StructuredField fallback = new CustomFallbackField();
      fallback.setStructuredFieldIntroducer(new StructuredFieldIntroducer());
      writer.handle(fallback);
      assertNotNull(genField.get(writer), "Fallback SF should lazily instantiate ToXmlGenerator");
    }

    String xml = out.toString(java.nio.charset.StandardCharsets.UTF_8);
    assertTrue(xml.contains("<NOP_NoOperation"));
    assertTrue(xml.contains("<CustomFallbackField"));
  }

  private static class CustomFallbackField extends StructuredField {
    @Override
    public void decodeAFP(byte[] sfData, int offset, int length,
        com.mgz.afp.parser.AFPParserConfiguration config) {
    }

    @Override
    public void writeAFP(java.io.OutputStream os,
        com.mgz.afp.parser.AFPParserConfiguration config) {
    }
  }
}
