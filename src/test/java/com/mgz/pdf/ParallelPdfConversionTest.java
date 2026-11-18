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
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.mgz.afp.base.StructuredFieldIntroducer;
import com.mgz.afp.enums.AFPUnitBase;
import com.mgz.afp.enums.SFFlag;
import com.mgz.afp.enums.SFTypeID;
import com.mgz.afp.modca.BAG_BeginActiveEnvironmentGroup;
import com.mgz.afp.modca.BDT_BeginDocument;
import com.mgz.afp.modca.BMO_BeginOverlay;
import com.mgz.afp.modca.BNG_BeginNamedPageGroup;
import com.mgz.afp.modca.BPG_BeginPage;
import com.mgz.afp.modca.BPS_BeginPageSegment;
import com.mgz.afp.modca.EAG_EndActiveEnvironmentGroup;
import com.mgz.afp.modca.EDT_EndDocument;
import com.mgz.afp.modca.EMO_EndOverlay;
import com.mgz.afp.modca.ENG_EndNamedPageGroup;
import com.mgz.afp.modca.EPG_EndPage;
import com.mgz.afp.modca.EPS_EndPageSegment;
import com.mgz.afp.modca.IPO_IncludePageOverlay;
import com.mgz.afp.modca.IPS_IncludePageSegment;
import com.mgz.afp.modca.MCF_MapCodedFont_Format1;
import com.mgz.afp.modca.PGD_PageDescriptor;
import com.mgz.afp.modca.TLE_TagLogicalElement;
import com.mgz.afp.parser.AFPParserConfiguration;
import com.mgz.afp.parser.ParallelAfpConverter;
import com.mgz.afp.ptoca.PTD_PresentationTextDataDescriptor_Format1;
import com.mgz.afp.triplets.Triplet;
import com.mgz.cli.Afp2Xml;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;
import java.util.Map;
import org.apache.pdfbox.Loader;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.rendering.PDFRenderer;
import org.apache.pdfbox.text.PDFTextStripper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

/**
 * Verifies Phase 4 parallel page-batch PDF rendering across state inheritance, resources, and CLI.
 *
 * <ul>
 *   <li>Sequential vs parallel parity on real and expanded multi-page AFP files
 *   <li>Preamble PGD/MCF inheritance and multi-group DPart/TLE boundaries
 *   <li>Page-0 AEG PGD/PTD/MCF state inheritance when preamble lacks PGD
 *   <li>BMO/BPS resource replay parity via MappedByteBuffer and AsynchronousFileChannel
 *   <li>CLI decoration flags (-o, -w, -sp) across master-merged pages
 *   <li>1,000-page parallel PDF conversion integrity
 * </ul>
 */
public class ParallelPdfConversionTest {

  private static final Charset CP500 = Charset.forName("cp500");

  @TempDir
  Path tempDir;

  @Test
  public void testSequentialVsParallelPdfParityOnRealMultiPageFile() throws Exception {
    File realFontAfp = new File("src/test/resources/afp/font_test.afp");
    assertTrue(realFontAfp.exists(), "Test resource font_test.afp must exist");

    // 1. Direct real AFP single-page fallback parity (font_test.afp)
    File singleSeqPdf = tempDir.resolve("font_seq.pdf").toFile();
    File singleParPdf = tempDir.resolve("font_par.pdf").toFile();
    assertEquals(0, Afp2Xml.execute(new String[]{
        "-f", "pdf", realFontAfp.getAbsolutePath(), singleSeqPdf.getAbsolutePath()
    }));
    assertEquals(0, Afp2Xml.execute(new String[]{
        "-f", "pdf", "-p", "-t", "4", realFontAfp.getAbsolutePath(), singleParPdf.getAbsolutePath()
    }));
    assertPdfParity(singleSeqPdf, singleParPdf, "font_test.afp");

    // 2. Expand real font_test.afp page records into a 16-page real-content AFP file
    File multiRealAfp = tempDir.resolve("font_test_16p.afp").toFile();
    replicateRealAfpPages(realFontAfp, multiRealAfp, 16);

    File multiSeqPdf = tempDir.resolve("real16_seq.pdf").toFile();
    File multiParPdf = tempDir.resolve("real16_par.pdf").toFile();
    assertEquals(0, Afp2Xml.execute(new String[]{
        "-f", "pdf", multiRealAfp.getAbsolutePath(), multiSeqPdf.getAbsolutePath()
    }));
    assertEquals(0, Afp2Xml.execute(new String[]{
        "-f", "pdf", "-p", "-t", "4", multiRealAfp.getAbsolutePath(), multiParPdf.getAbsolutePath()
    }));
    assertPdfParity(multiSeqPdf, multiParPdf, "font_test_16p");
  }

  @Test
  public void testPreambleStateAndMultiGroupTleBoundaryParity() throws Exception {
    File afpFile = tempDir.resolve("preamble_multigroup.afp").toFile();
    writeSyntheticAfp(afpFile, 24, false, false, true);

    File seqPdf = tempDir.resolve("preamble_seq.pdf").toFile();
    File parPdf = tempDir.resolve("preamble_par.pdf").toFile();

    assertEquals(0, Afp2Xml.execute(new String[]{
        "-f", "pdf", afpFile.getAbsolutePath(), seqPdf.getAbsolutePath()
    }));
    assertEquals(0, Afp2Xml.execute(new String[]{
        "-f", "pdf", "-p", "-t", "4", afpFile.getAbsolutePath(), parPdf.getAbsolutePath()
    }));

    assertPdfParity(seqPdf, parPdf, "preamble_multigroup");

    try (PDDocument doc = Loader.loadPDF(parPdf)) {
      assertEquals(24, doc.getNumberOfPages());
      PDFTextStripper stripper = new PDFTextStripper();
      stripper.setStartPage(1);
      stripper.setEndPage(1);
      assertTrue(stripper.getText(doc).contains("PageContent 001"));
      stripper.setStartPage(24);
      stripper.setEndPage(24);
      assertTrue(stripper.getText(doc).contains("PageContent 024"));
    }

    PdfMetadataExtractor extractor = new PdfMetadataExtractor();
    try (var is = java.nio.file.Files.newInputStream(parPdf.toPath())) {
      List<Map<String, String>> meta = extractor.extractMetadata(is);
      assertEquals(24, meta.size());
      assertEquals("P001", meta.get(0).get("PageId"));
      assertEquals("GRP_0", meta.get(0).get("GroupCode"));
      assertEquals("P024", meta.get(23).get("PageId"));
      assertEquals("GRP_3", meta.get(23).get("GroupCode"));
    }
  }

  @Test
  public void testPage0EnvironmentStateInheritanceParity() throws Exception {
    File afpFile = tempDir.resolve("page0_aeg.afp").toFile();
    writeSyntheticAfp(afpFile, 24, true, false, false);

    File seqPdf = tempDir.resolve("page0_seq.pdf").toFile();
    File parPdf = tempDir.resolve("page0_par.pdf").toFile();

    assertEquals(0, Afp2Xml.execute(new String[]{
        "-f", "pdf", afpFile.getAbsolutePath(), seqPdf.getAbsolutePath()
    }));
    assertEquals(0, Afp2Xml.execute(new String[]{
        "-f", "pdf", "-p", "-t", "4", afpFile.getAbsolutePath(), parPdf.getAbsolutePath()
    }));

    assertPdfParity(seqPdf, parPdf, "page0_aeg");

    // Landscape 2640x2040 @ 240dpi = 792x612 pt (inherited from Page 0's BAG/PGD)
    try (PDDocument doc = Loader.loadPDF(parPdf)) {
      assertEquals(24, doc.getNumberOfPages());
      for (int i = 0; i < 24; i++) {
        PDRectangle box = doc.getPage(i).getMediaBox();
        assertEquals(792.0f, box.getWidth(), 1.0f, "Width mismatch on page " + (i + 1));
        assertEquals(612.0f, box.getHeight(), 1.0f, "Height mismatch on page " + (i + 1));
      }
    }
  }

  @Test
  public void testOverlayAndPageSegmentReplayMappedAndAsyncParity() throws Exception {
    File afpFile = tempDir.resolve("resources_replay.afp").toFile();
    writeSyntheticAfp(afpFile, 16, false, true, false);

    File seqPdf = tempDir.resolve("res_seq.pdf").toFile();
    File parPdf = tempDir.resolve("res_par.pdf").toFile();

    assertEquals(0, Afp2Xml.execute(new String[]{
        "-f", "pdf", afpFile.getAbsolutePath(), seqPdf.getAbsolutePath()
    }));
    assertEquals(0, Afp2Xml.execute(new String[]{
        "-f", "pdf", "-p", "-t", "4", afpFile.getAbsolutePath(), parPdf.getAbsolutePath()
    }));

    // Force AsynchronousFileChannel path by initially returning null for ByteBuffer.
    AFPParserConfiguration asyncConfig = new AFPParserConfiguration() {
      private boolean explicitBufferSet = false;

      @Override
      public ByteBuffer getByteBuffer() throws IOException {
        return explicitBufferSet ? super.getByteBuffer() : null;
      }

      @Override
      public void setByteBuffer(ByteBuffer buffer) {
        explicitBufferSet = true;
        super.setByteBuffer(buffer);
      }
    };
    asyncConfig.setAFPFile(afpFile);
    asyncConfig.setEscalateParsingErrors(false);
    final File asyncPdf = tempDir.resolve("res_async.pdf").toFile();
    try (OutputStream out = new FileOutputStream(asyncPdf)) {
      new ParallelAfpConverter(asyncConfig, 4, new PdfHandlerFactory()).convert(out);
    }

    assertPdfParity(seqPdf, parPdf, "resources_replay_mapped");
    assertPdfParity(seqPdf, asyncPdf, "resources_replay_async");

    try (PDDocument doc = Loader.loadPDF(asyncPdf)) {
      assertEquals(16, doc.getNumberOfPages());
      PDFTextStripper stripper = new PDFTextStripper();
      for (int p = 1; p <= 16; p++) {
        stripper.setStartPage(p);
        stripper.setEndPage(p);
        String text = stripper.getText(doc);
        assertTrue(text.contains("HEADER OVERLAY"), "Overlay missing on page " + p);
        assertTrue(text.contains("SEGMENT STAMP"), "Page segment missing on page " + p);
        assertTrue(text.contains(String.format("PageContent %03d", p)),
            "Body text missing on page " + p);
      }
    }
  }

  @Test
  public void testCliDecorationFlagsParallelParity() throws Exception {
    File afpFile = tempDir.resolve("decorations.afp").toFile();
    writeSyntheticAfp(afpFile, 8, false, false, false);

    File seqPdf = tempDir.resolve("deco_seq.pdf").toFile();
    File parPdf = tempDir.resolve("deco_par.pdf").toFile();

    assertEquals(0, Afp2Xml.execute(new String[]{
        "-f", "pdf", "-o", "-w", "-sp",
        afpFile.getAbsolutePath(), seqPdf.getAbsolutePath()
    }));
    assertEquals(0, Afp2Xml.execute(new String[]{
        "-f", "pdf", "-p", "-t", "3", "-o", "-w", "-sp",
        afpFile.getAbsolutePath(), parPdf.getAbsolutePath()
    }));

    assertPdfParity(seqPdf, parPdf, "cli_decorations");

    try (PDDocument parDoc = Loader.loadPDF(parPdf)) {
      assertEquals(8, parDoc.getNumberOfPages());
      PDFRenderer renderer = new PDFRenderer(parDoc);
      BufferedImage page1 = renderer.renderImageWithDPI(0, 72);
      BufferedImage page5 = renderer.renderImageWithDPI(4, 72);

      // Left overlay strip (#ADD8E6) must appear on both Page 1 and later merged pages.
      assertTrue(hasLightBlueStrip(page1), "Left overlay missing on Page 1");
      assertTrue(hasLightBlueStrip(page5), "Left overlay missing on Page 5");

      // Swiss Post yellow lines must appear on Page 1 only.
      assertTrue(hasYellowSwissPostPixel(page1), "Swiss Post lines missing on Page 1");
      assertFalse(hasYellowSwissPostPixel(page5), "Swiss Post lines must not draw on Page 5");
    }
  }

  @Test
  public void test1000PageParallelPdfConversion() throws Exception {
    File afpFile = tempDir.resolve("thousand_pages.afp").toFile();
    writeSyntheticAfp(afpFile, 1000, false, false, false);

    File parPdf = tempDir.resolve("thousand_par.pdf").toFile();
    assertEquals(0, Afp2Xml.execute(new String[]{
        "-f", "pdf", "-p", "-t", "4", afpFile.getAbsolutePath(), parPdf.getAbsolutePath()
    }));
    assertTrue(parPdf.exists() && parPdf.length() > 0);

    try (PDDocument doc = Loader.loadPDF(parPdf)) {
      assertEquals(1000, doc.getNumberOfPages(), "1000 AFP pages must produce 1000 PDF pages");

      PDFTextStripper stripper = new PDFTextStripper();
      for (int pageNum : new int[]{1, 250, 500, 750, 1000}) {
        stripper.setStartPage(pageNum);
        stripper.setEndPage(pageNum);
        String text = stripper.getText(doc);
        assertTrue(text.contains(String.format("PageContent %03d", pageNum)),
            "Expected body text on page " + pageNum);
      }
    }

    PdfMetadataExtractor extractor = new PdfMetadataExtractor();
    try (var is = java.nio.file.Files.newInputStream(parPdf.toPath())) {
      List<Map<String, String>> meta = extractor.extractMetadata(is);
      assertEquals(1000, meta.size(), "All 1000 leaf DPart entries must be preserved");
      assertEquals("P001", meta.get(0).get("PageId"));
      assertEquals("P500", meta.get(499).get("PageId"));
      assertEquals("P1000", meta.get(999).get("PageId"));
    }
  }

  private static void assertPdfParity(File seqPdf, File parPdf, String label) throws Exception {
    try (PDDocument seqDoc = Loader.loadPDF(seqPdf);
        PDDocument parDoc = Loader.loadPDF(parPdf)) {
      int pageCount = seqDoc.getNumberOfPages();
      assertEquals(pageCount, parDoc.getNumberOfPages(),
          label + ": page count mismatch");

      PDFTextStripper seqStripper = new PDFTextStripper();
      PDFTextStripper parStripper = new PDFTextStripper();
      for (int p = 1; p <= pageCount; p++) {
        PDRectangle seqBox = seqDoc.getPage(p - 1).getMediaBox();
        PDRectangle parBox = parDoc.getPage(p - 1).getMediaBox();
        assertEquals(seqBox.getWidth(), parBox.getWidth(), 0.1f,
            label + ": MediaBox width mismatch at page " + p);
        assertEquals(seqBox.getHeight(), parBox.getHeight(), 0.1f,
            label + ": MediaBox height mismatch at page " + p);

        seqStripper.setStartPage(p);
        seqStripper.setEndPage(p);
        parStripper.setStartPage(p);
        parStripper.setEndPage(p);
        assertEquals(seqStripper.getText(seqDoc), parStripper.getText(parDoc),
            label + ": extracted text mismatch at page " + p);
      }
    }

    PdfMetadataExtractor extractor = new PdfMetadataExtractor();
    List<Map<String, String>> seqMeta;
    List<Map<String, String>> parMeta;
    try (var seqIs = java.nio.file.Files.newInputStream(seqPdf.toPath());
        var parIs = java.nio.file.Files.newInputStream(parPdf.toPath())) {
      seqMeta = extractor.extractMetadata(seqIs);
      parMeta = extractor.extractMetadata(parIs);
    }
    assertEquals(seqMeta, parMeta, label + ": DPart metadata mismatch");
  }

  private static boolean hasLightBlueStrip(BufferedImage image) {
    int midY = image.getHeight() / 2;
    for (int x = 0; x <= 4 && x < image.getWidth(); x++) {
      int rgb = image.getRGB(x, midY);
      int r = (rgb >> 16) & 0xFF;
      int g = (rgb >> 8) & 0xFF;
      int b = rgb & 0xFF;
      if (r < 200 && g > 190 && b > 210) {
        return true;
      }
    }
    return false;
  }

  private static boolean hasYellowSwissPostPixel(BufferedImage image) {
    // Vertical line at 34mm from right border; horizontal at 59mm from top border.
    int targetX = Math.max(0, image.getWidth() - (int) Math.round(34.0 * 72.0 / 25.4));
    for (int dx = -4; dx <= 4; dx++) {
      int x = targetX + dx;
      if (x < 0 || x >= image.getWidth()) {
        continue;
      }
      for (int y = 0; y < image.getHeight(); y++) {
        int rgb = image.getRGB(x, y);
        int r = (rgb >> 16) & 0xFF;
        int g = (rgb >> 8) & 0xFF;
        int b = rgb & 0xFF;
        if (r > 230 && g > 180 && g < 230 && b < 40) {
          return true;
        }
      }
    }
    return false;
  }

  private static void writeSyntheticAfp(
      File output,
      int pages,
      boolean pgdInPage0Only,
      boolean includeResources,
      boolean shadowFontPerPage)
      throws Exception {
    AFPParserConfiguration cfg = new AFPParserConfiguration();
    cfg.setAfpCharSet(CP500);

    try (FileOutputStream fos = new FileOutputStream(output)) {
      if (includeResources) {
        BMO_BeginOverlay bmo = new BMO_BeginOverlay();
        bmo.setStructuredFieldIntroducer(createSfi(SFTypeID.BMO_BeginOverlay));
        bmo.setName("OVL00001");
        bmo.writeAFP(fos, cfg);

        writeSimplePtx(fos, 200, 100, "HEADER OVERLAY");

        EMO_EndOverlay emo = new EMO_EndOverlay();
        emo.setStructuredFieldIntroducer(createSfi(SFTypeID.EMO_EndOverlay));
        emo.setName("OVL00001");
        emo.writeAFP(fos, cfg);

        BPS_BeginPageSegment bps = new BPS_BeginPageSegment();
        bps.setStructuredFieldIntroducer(createSfi(SFTypeID.BPS_BeginPageSegment));
        bps.setName("SEG00001");
        bps.writeAFP(fos, cfg);

        writeSimplePtx(fos, 200, 150, "SEGMENT STAMP");

        EPS_EndPageSegment eps = new EPS_EndPageSegment();
        eps.setStructuredFieldIntroducer(createSfi(SFTypeID.EPS_EndPageSegment));
        eps.setName("SEG00001");
        eps.writeAFP(fos, cfg);
      }

      BDT_BeginDocument bdt = new BDT_BeginDocument();
      bdt.setStructuredFieldIntroducer(createSfi(SFTypeID.BDT_BeginDocument));
      bdt.setName("DOC00001");
      bdt.writeAFP(fos, cfg);

      if (!pgdInPage0Only) {
        writePgd(fos, cfg, 1984, 2816);
        writePtd(fos, cfg, (short) 1984, (short) 2816);
        writeMcf(fos, cfg, (short) 1, "X0HELV10");
      }

      int groupSize = Math.max(1, pages / 4);
      if (pages >= 100) {
        groupSize = 10;
      }

      for (int p = 0; p < pages; p++) {
        boolean startGroup = (p % groupSize == 0);
        final boolean endGroup = ((p + 1) % groupSize == 0 || p + 1 == pages);

        if (startGroup) {
          int groupIdx = p / groupSize;
          BNG_BeginNamedPageGroup bng = new BNG_BeginNamedPageGroup();
          bng.setStructuredFieldIntroducer(createSfi(SFTypeID.BNG_BeginNamedPageGroup));
          bng.setName("GRP_" + groupIdx);
          bng.writeAFP(fos, cfg);

          writeTle(fos, cfg, "GroupCode", "GRP_" + groupIdx);
        }

        String pageName = String.format("PG_%04d", p + 1);
        BPG_BeginPage bpg = new BPG_BeginPage();
        bpg.setStructuredFieldIntroducer(createSfi(SFTypeID.BPG_BeginPage));
        bpg.setName(pageName);
        bpg.writeAFP(fos, cfg);

        if (pgdInPage0Only && p == 0) {
          BAG_BeginActiveEnvironmentGroup bag = new BAG_BeginActiveEnvironmentGroup();
          bag.setStructuredFieldIntroducer(createSfi(SFTypeID.BAG_BeginActiveEnvironmentGroup));
          bag.setName("AEG00001");
          bag.writeAFP(fos, cfg);

          writePgd(fos, cfg, 2640, 2040);
          writePtd(fos, cfg, (short) 2640, (short) 2040);
          writeMcf(fos, cfg, (short) 1, "X0HELV10");

          EAG_EndActiveEnvironmentGroup eag = new EAG_EndActiveEnvironmentGroup();
          eag.setStructuredFieldIntroducer(createSfi(SFTypeID.EAG_EndActiveEnvironmentGroup));
          eag.setName("AEG00001");
          eag.writeAFP(fos, cfg);
        } else if (shadowFontPerPage && p % 3 == 1) {
          BAG_BeginActiveEnvironmentGroup bag = new BAG_BeginActiveEnvironmentGroup();
          bag.setStructuredFieldIntroducer(createSfi(SFTypeID.BAG_BeginActiveEnvironmentGroup));
          bag.setName("AEG_" + p);
          bag.writeAFP(fos, cfg);

          writeMcf(fos, cfg, (short) 1, "X0HELV16");

          EAG_EndActiveEnvironmentGroup eag = new EAG_EndActiveEnvironmentGroup();
          eag.setStructuredFieldIntroducer(createSfi(SFTypeID.EAG_EndActiveEnvironmentGroup));
          eag.setName("AEG_" + p);
          eag.writeAFP(fos, cfg);
        }

        writeTle(fos, cfg, "PageId", String.format("P%03d", p + 1));

        if (includeResources) {
          IPO_IncludePageOverlay ipo = new IPO_IncludePageOverlay();
          ipo.setStructuredFieldIntroducer(createSfi(SFTypeID.IPO_IncludePageOverlay));
          ipo.setOverlayName("OVL00001");
          ipo.setxOrigin(0);
          ipo.setyOrigin(0);
          ipo.writeAFP(fos, cfg);

          IPS_IncludePageSegment ips = new IPS_IncludePageSegment();
          ips.setStructuredFieldIntroducer(createSfi(SFTypeID.IPS_IncludePageSegment));
          ips.setPageSegmentName("SEG00001");
          ips.setxOrigin(0);
          ips.setyOrigin(0);
          ips.writeAFP(fos, cfg);
        }

        writeSimplePtx(fos, 200, 400, String.format("PageContent %03d", p + 1));

        EPG_EndPage epg = new EPG_EndPage();
        epg.setStructuredFieldIntroducer(createSfi(SFTypeID.EPG_EndPage));
        epg.setName(pageName);
        epg.writeAFP(fos, cfg);

        if (endGroup) {
          ENG_EndNamedPageGroup eng = new ENG_EndNamedPageGroup();
          eng.setStructuredFieldIntroducer(createSfi(SFTypeID.ENG_EndNamedPageGroup));
          eng.setName("GRP_" + (p / groupSize));
          eng.writeAFP(fos, cfg);
        }
      }

      EDT_EndDocument edt = new EDT_EndDocument();
      edt.setStructuredFieldIntroducer(createSfi(SFTypeID.EDT_EndDocument));
      edt.setName("DOC00001");
      edt.writeAFP(fos, cfg);
    }
  }

  private static void replicateRealAfpPages(File sourceAfp, File destAfp, int pageCopies)
      throws IOException {
    byte[] src = java.nio.file.Files.readAllBytes(sourceAfp.toPath());
    int bpgOffset = -1;
    int epgEndOffset = -1;
    int pos = 0;
    while (pos + 6 < src.length) {
      if ((src[pos] & 0xFF) == 0x5A) {
        int len = ((src[pos + 1] & 0xFF) << 8) | (src[pos + 2] & 0xFF);
        int sfClass = src[pos + 3] & 0xFF;
        int sfType = src[pos + 4] & 0xFF;
        int sfCat = src[pos + 5] & 0xFF;
        if (sfClass == 0xD3 && sfType == 0xA8 && sfCat == 0xAF && bpgOffset < 0) {
          bpgOffset = pos;
        }
        if (sfClass == 0xD3 && sfType == 0xA9 && sfCat == 0xAF) {
          epgEndOffset = pos + 1 + len;
        }
        pos += 1 + len;
      } else {
        pos++;
      }
    }
    assertTrue(bpgOffset >= 0 && epgEndOffset > bpgOffset, "Source AFP must contain BPG..EPG");
    try (FileOutputStream fos = new FileOutputStream(destAfp)) {
      fos.write(src, 0, bpgOffset);
      for (int i = 0; i < pageCopies; i++) {
        fos.write(src, bpgOffset, epgEndOffset - bpgOffset);
      }
      fos.write(src, epgEndOffset, src.length - epgEndOffset);
    }
  }

  private static StructuredFieldIntroducer createSfi(SFTypeID typeId) {
    StructuredFieldIntroducer sfi = new StructuredFieldIntroducer();
    sfi.setSFTypeID(typeId);
    sfi.setFlagByte(EnumSet.noneOf(SFFlag.class));
    return sfi;
  }

  private static void writePgd(
      OutputStream os, AFPParserConfiguration cfg, int width, int height) throws IOException {
    PGD_PageDescriptor pgd = new PGD_PageDescriptor();
    pgd.setStructuredFieldIntroducer(createSfi(SFTypeID.PGD_PageDescriptor));
    pgd.setxUnitBase(AFPUnitBase.Inches10);
    pgd.setyUnitBase(AFPUnitBase.Inches10);
    pgd.setxUnitsPerUnitBase((short) 2400);
    pgd.setyUnitsPerUnitBase((short) 2400);
    pgd.setxSize(width);
    pgd.setySize(height);
    pgd.setReserved12_14(new byte[3]);
    pgd.writeAFP(os, cfg);
  }

  private static void writePtd(
      OutputStream os, AFPParserConfiguration cfg, short width, short height) throws IOException {
    PTD_PresentationTextDataDescriptor_Format1 ptd =
        new PTD_PresentationTextDataDescriptor_Format1();
    ptd.setStructuredFieldIntroducer(
        createSfi(SFTypeID.PTD_PresentationTextDataDescriptor_Format1));
    ptd.setxUnitBase(AFPUnitBase.Inches10);
    ptd.setyUnitBase(AFPUnitBase.Inches10);
    ptd.setxUnitsPerUnitBase((short) 2400);
    ptd.setyUnitsPerUnitBase((short) 2400);
    ptd.setxSize(width);
    ptd.setySize(height);
    ptd.writeAFP(os, cfg);
  }

  private static void writeMcf(
      OutputStream os, AFPParserConfiguration cfg, short lid, String fontName)
      throws IOException {
    MCF_MapCodedFont_Format1 mcf = new MCF_MapCodedFont_Format1();
    mcf.setStructuredFieldIntroducer(createSfi(SFTypeID.MCF_MapCodedFont_Format1));
    MCF_MapCodedFont_Format1.MCF_RepeatingGroup rg =
        new MCF_MapCodedFont_Format1.MCF_RepeatingGroup();
    rg.setCodedFontLocalID(lid);
    rg.setCodedFontName(fontName);
    rg.setCodePageName("T1V10500");
    rg.setFontCharacterSetName(fontName);
    mcf.addRepeatingGroup(rg);
    mcf.writeAFP(os, cfg);
  }

  private static void writeTle(
      OutputStream os, AFPParserConfiguration cfg, String key, String value) throws IOException {
    Triplet.FullyQualifiedName fqn = new Triplet.FullyQualifiedName();
    fqn.setTripletID(Triplet.TripletID.FullyQualifiedName);
    fqn.setType(Triplet.GlobalID_Use.AttributeGID);
    fqn.setFormat(Triplet.GlobalID_Format.CharacterString);
    fqn.setNameAsString(key);

    Triplet.AttributeValue av = new Triplet.AttributeValue();
    av.setTripletID(Triplet.TripletID.AttributeValue);
    av.setAttributeValue(value);

    List<Triplet> triplets = new ArrayList<>();
    triplets.add(fqn);
    triplets.add(av);

    TLE_TagLogicalElement tle = new TLE_TagLogicalElement();
    tle.setStructuredFieldIntroducer(createSfi(SFTypeID.TLE_TagLogicalElement));
    tle.setTriplets(triplets);
    tle.writeAFP(os, cfg);
  }

  private static void writeSimplePtx(OutputStream os, int x, int y, String text)
      throws IOException {
    byte[] textBytes = text.getBytes(CP500);
    ByteArrayOutputStream payload = new ByteArrayOutputStream();
    // PTOCA escape prefix 0x2B 0xD3
    payload.write(0x2B);
    payload.write(0xD3);
    // SCFL (chained = 0xF1), len=3, lid=1
    payload.write(3);
    payload.write(0xF1);
    payload.write(1);
    // AMI (chained = 0xC7), len=4, x (2 bytes)
    payload.write(4);
    payload.write(0xC7);
    payload.write((x >> 8) & 0xFF);
    payload.write(x & 0xFF);
    // AMB (chained = 0xD3), len=4, y (2 bytes)
    payload.write(4);
    payload.write(0xD3);
    payload.write((y >> 8) & 0xFF);
    payload.write(y & 0xFF);
    // TRN (unchained = 0xDA), len = 2 + textBytes.length
    payload.write(2 + textBytes.length);
    payload.write(0xDA);
    payload.write(textBytes);

    byte[] ptxBytes = payload.toByteArray();
    int totalLen = 8 + ptxBytes.length;
    os.write(0x5A);
    os.write((totalLen >> 8) & 0xFF);
    os.write(totalLen & 0xFF);
    os.write(0xD3);
    os.write(0xEE);
    os.write(0x9B);
    os.write(0x00);
    os.write(0x00);
    os.write(0x00);
    os.write(ptxBytes);
  }
}
