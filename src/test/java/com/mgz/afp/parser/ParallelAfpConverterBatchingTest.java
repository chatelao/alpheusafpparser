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

package com.mgz.afp.parser;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.mgz.afp.parser.ParallelAfpConverter.PageBatch;
import com.mgz.cli.Afp2Xml;
import com.mgz.xml.XmlHandlerFactory;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

/**
 * Verifies Phase 2 Step 2.2:
 * - Byte-balanced contiguous PageBatch partitioning
 * - Exact bit-for-bit parity (including multi-page page="N" attributes) on 1,000-page AFP files
 * - Skewed page distribution and AsyncFileChannel batched conversion parity
 */
public class ParallelAfpConverterBatchingTest {

  @TempDir
  Path tempDir;

  @Test
  public void testCreatePageBatchesUniform1000Pages() {
    List<Long> offsets = new ArrayList<>(1000);
    for (int i = 0; i < 1000; i++) {
      offsets.add(100L + i * 1024L);
    }
    long fileSize = 100L + 1000 * 1024L + 50L;

    List<PageBatch> batches = ParallelAfpConverter.createPageBatches(offsets, fileSize, 4, 0L);

    assertEquals(16, batches.size(), "4 threads * 4 multiplier = 16 coarse batches");
    assertEquals(100L, batches.get(0).startOffset());
    assertEquals(0, batches.get(0).startPageIndex());
    assertEquals(fileSize, batches.get(batches.size() - 1).endOffset());

    for (int i = 0; i < batches.size(); i++) {
      PageBatch b = batches.get(i);
      assertEquals(i, b.sequence());
      assertTrue(b.endOffset() > b.startOffset());
      if (i > 0) {
        assertEquals(batches.get(i - 1).endOffset(), b.startOffset(), "Batches must be contiguous");
        assertTrue(b.startPageIndex() > batches.get(i - 1).startPageIndex());
      }
    }
  }

  @Test
  public void testCreatePageBatchesEdgeCasesAndAsyncLimit() {
    assertTrue(ParallelAfpConverter.createPageBatches(List.of(), 1000L, 4, 0L).isEmpty());

    List<PageBatch> single = ParallelAfpConverter.createPageBatches(List.of(25L), 500L, 4, 0L);
    assertEquals(1, single.size());
    assertEquals(new PageBatch(0, 0, 25L, 500L), single.get(0));

    // Skewed: page 0 is 10MB, pages 1..9 are 1KB each
    List<Long> skewed = new ArrayList<>();
    skewed.add(50L);
    long pos = 50L + 10L * 1024 * 1024;
    for (int i = 1; i < 10; i++) {
      skewed.add(pos);
      pos += 1024L;
    }
    List<PageBatch> skewedBatches = ParallelAfpConverter.createPageBatches(skewed, pos, 2, 0L);
    assertTrue(skewedBatches.size() >= 2, "Skewed pages should split across at least 2 batches");
    assertEquals(1, skewedBatches.get(1).startPageIndex(),
        "Huge first page should form its own single-page batch");

    // Async maxBatchBytes cap splits into multiple batches even if numThreads=1
    List<Long> smallPages = new ArrayList<>();
    for (int i = 0; i < 20; i++) {
      smallPages.add(i * 1000L);
    }
    List<PageBatch> asyncBatches =
        ParallelAfpConverter.createPageBatches(smallPages, 20000L, 1, 5000L);
    assertTrue(asyncBatches.size() >= 4, "Async cap should keep batches within byte budget");
    for (PageBatch b : asyncBatches) {
      assertTrue(b.endOffset() - b.startOffset() <= 5000L);
    }
  }

  @Test
  public void test1000PageSequentialVsParallelBitForBitParity() throws Exception {
    File afpFile = tempDir.resolve("thousand_pages.afp").toFile();
    generateMultiPageAfp(afpFile, 1000, false);

    Path seqXml = tempDir.resolve("thousand_seq.xml");
    Path parXml = tempDir.resolve("thousand_par.xml");

    assertEquals(0, Afp2Xml.execute(new String[]{
        afpFile.getAbsolutePath(), seqXml.toString()
    }));
    assertEquals(0, Afp2Xml.execute(new String[]{
        "--parallel", "-t", "4", afpFile.getAbsolutePath(), parXml.toString()
    }));

    byte[] seqBytes = Files.readAllBytes(seqXml);
    byte[] parBytes = Files.readAllBytes(parXml);
    assertArrayEquals(seqBytes, parBytes,
        "1000-page sequential and batched parallel XML output must match bit-for-bit");

    String xmlText = new String(parBytes, StandardCharsets.UTF_8);
    assertTrue(xmlText.contains("page=\"1\""), "Should contain page=1");
    assertTrue(xmlText.contains("page=\"500\""), "Should contain page=500");
    assertTrue(xmlText.contains("page=\"1000\""), "Should contain page=1000");
  }

  @Test
  public void testSkewedAndAsyncChannelMultiPageBitForBitParity() throws Exception {
    File afpFile = tempDir.resolve("skewed_pages.afp").toFile();
    generateMultiPageAfp(afpFile, 60, true);

    Path seqXml = tempDir.resolve("skewed_seq.xml");
    assertEquals(0, Afp2Xml.execute(new String[]{
        afpFile.getAbsolutePath(), seqXml.toString()
    }));
    byte[] seqBytes = Files.readAllBytes(seqXml);

    // Parallel with mapped buffer
    AFPParserConfiguration parConfig = new AFPParserConfiguration();
    parConfig.setAFPFile(afpFile);
    parConfig.setEscalateParsingErrors(false);
    ByteArrayOutputStream parOut = new ByteArrayOutputStream();
    new ParallelAfpConverter(parConfig, 4, new XmlHandlerFactory()).convert(parOut);
    assertArrayEquals(seqBytes, parOut.toByteArray(),
        "Skewed multi-page parallel XML output must match sequential bit-for-bit");

    // Parallel forced through AsynchronousFileChannel (skip lazy file mmap, keep setByteBuffer slices)
    AFPParserConfiguration asyncConfig = new AFPParserConfiguration() {
      private boolean explicitBufferSet = false;

      @Override
      public java.nio.ByteBuffer getByteBuffer() throws IOException {
        return explicitBufferSet ? super.getByteBuffer() : null;
      }

      @Override
      public void setByteBuffer(java.nio.ByteBuffer buffer) {
        explicitBufferSet = true;
        super.setByteBuffer(buffer);
      }
    };
    asyncConfig.setAFPFile(afpFile);
    asyncConfig.setEscalateParsingErrors(false);
    ByteArrayOutputStream asyncOut = new ByteArrayOutputStream();
    new ParallelAfpConverter(asyncConfig, 4, new XmlHandlerFactory()).convert(asyncOut);
    assertArrayEquals(seqBytes, asyncOut.toByteArray(),
        "AsyncFileChannel batched parallel XML output must match sequential bit-for-bit");
  }

  private static void generateMultiPageAfp(File output, int pages, boolean skewFirstPage)
      throws IOException {
    byte[] docName = {
        (byte) 0xC4, (byte) 0xD6, (byte) 0xC3, (byte) 0xF0,
        (byte) 0xF0, (byte) 0xF0, (byte) 0xF0, (byte) 0xF1
    };
    byte[] bdt = {0x5A, 0x00, 0x10, (byte) 0xD3, (byte) 0xA8, (byte) 0xA8, 0x00, 0x00, 0x00};
    byte[] edt = {0x5A, 0x00, 0x10, (byte) 0xD3, (byte) 0xA9, (byte) 0xA8, 0x00, 0x00, 0x00};
    byte[] bpg = {0x5A, 0x00, 0x10, (byte) 0xD3, (byte) 0xA8, (byte) 0xAF, 0x00, 0x00, 0x00};
    byte[] epg = {0x5A, 0x00, 0x10, (byte) 0xD3, (byte) 0xA9, (byte) 0xAF, 0x00, 0x00, 0x00};

    // PGD: length = 22 (0x16), 8-byte header + 14-byte payload
    byte[] pgd = {
        0x5A, 0x00, 0x16, (byte) 0xD3, (byte) 0xA6, (byte) 0xAF, 0x00, 0x00, 0x00,
        0x00, 0x00, 0x09, 0x60, 0x09, 0x60, // base 10in + 2400 units/10in
        0x00, 0x07, (byte) 0xC0, // xSize = 1984
        0x00, 0x0B, 0x00, // ySize = 2816
        0x00, 0x00
    };

    byte[] ptxPayload = {
        0x2B, (byte) 0xD3, 0x0D, (byte) 0xDA,
        (byte) 0xC8, (byte) 0x85, (byte) 0x93, (byte) 0x93, (byte) 0x96, 0x40, // Hello
        (byte) 0xE6, (byte) 0x96, (byte) 0x99, (byte) 0x93, (byte) 0x84 // World
    };
    byte[] ptxHeader = {
        0x5A, 0x00, (byte) (ptxPayload.length + 8),
        (byte) 0xD3, (byte) 0xEE, (byte) 0x9B, 0x00, 0x00, 0x00
    };

    byte[] bigNopPayload = new byte[16384];
    for (int i = 0; i < bigNopPayload.length; i++) {
      bigNopPayload[i] = (byte) (0xC1 + (i % 9));
    }
    int bigNopLen = bigNopPayload.length + 8;
    byte[] bigNopHeader = {
        0x5A, (byte) ((bigNopLen >> 8) & 0xFF), (byte) (bigNopLen & 0xFF),
        (byte) 0xD3, (byte) 0xEE, (byte) 0xEE, 0x00, 0x00, 0x00
    };

    try (FileOutputStream fos = new FileOutputStream(output)) {
      fos.write(bdt);
      fos.write(docName);

      for (int p = 0; p < pages; p++) {
        fos.write(bpg);
        fos.write(docName);
        fos.write(pgd);
        if (skewFirstPage && p == 0) {
          for (int k = 0; k < 40; k++) {
            fos.write(bigNopHeader);
            fos.write(bigNopPayload);
          }
        }
        fos.write(ptxHeader);
        fos.write(ptxPayload);
        fos.write(epg);
        fos.write(docName);
      }

      fos.write(edt);
      fos.write(docName);
    }
  }
}
