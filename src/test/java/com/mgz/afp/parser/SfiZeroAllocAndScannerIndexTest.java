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
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.mgz.afp.base.StructuredField;
import com.mgz.afp.base.StructuredFieldIntroducer;
import com.mgz.afp.enums.SFFlag;
import com.mgz.afp.enums.SFTypeID;
import com.mgz.afp.exceptions.AFPParserException;
import com.mgz.cli.Afp2Xml;
import com.mgz.xml.XmlHandlerFactory;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousFileChannel;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

/**
 * Verifies Phase 3 Step 3.2 optimizations:
 * - Zero-allocation SFI lazy rawSfTypeID and flagByte representation
 * - Single-pass ScanIndex (all SF offsets + BPG boundaries) parallel stitching
 * - False 0x5A decoy resistance in chunk boundaries
 * - Indexed AFPParser parity with sequential linear parsing
 */
public class SfiZeroAllocAndScannerIndexTest {

  @TempDir
  Path tempDir;

  @Test
  public void testSfiLazyRawTypeIdAndFlagsRoundTripAcrossAllParseModes() throws Exception {
    // Undefined SFTypeID (D3 FE FD) with extension + padded flags
    byte[] rawSfi = {
        0x00, 0x15,
        (byte) 0xD3, (byte) 0xFE, (byte) 0xFD,
        (byte) 0x88, // hasExtension | isPadded
        0x12, 0x34,  // reserved
        0x04, (byte) 0xAA, (byte) 0xBB, (byte) 0xCC
    };

    // 1. InputStream
    StructuredFieldIntroducer fromStream =
        StructuredFieldIntroducer.parse(new ByteArrayInputStream(rawSfi));
    assertSfiFidelity(fromStream, rawSfi);

    // 2. ByteBuffer at offset 3
    byte[] paddedBuf = new byte[3 + rawSfi.length];
    System.arraycopy(rawSfi, 0, paddedBuf, 3, rawSfi.length);
    ByteBuffer absBuf = ByteBuffer.wrap(paddedBuf);
    StructuredFieldIntroducer fromAbs = StructuredFieldIntroducer.parse(absBuf, 3);
    assertEquals(0, absBuf.position(), "Absolute parse must not mutate buffer position");
    assertSfiFidelity(fromAbs, rawSfi);

    // 3. Direct ByteBuffer sequential read
    ByteBuffer dirBuf = ByteBuffer.allocateDirect(rawSfi.length);
    dirBuf.put(rawSfi).flip();
    StructuredFieldIntroducer fromRel = StructuredFieldIntroducer.parse(dirBuf);
    assertSfiFidelity(fromRel, rawSfi);
  }

  private static void assertSfiFidelity(StructuredFieldIntroducer sfi, byte[] expected)
      throws Exception {
    assertEquals(SFTypeID.Undefined, sfi.getSFTypeID());
    assertTrue(sfi.isFlagSet(SFFlag.hasExtension));
    assertTrue(sfi.isFlagSet(SFFlag.isPadded));
    assertFalse(sfi.isFlagSet(SFFlag.isEncrypted));
    assertFalse(sfi.isFlagSet(SFFlag.isSegmented));
    assertEquals(0x1234, sfi.getReserved());

    // Round-trip BEFORE calling lazy getters
    assertArrayEquals(expected, sfi.toBytes());
    ByteArrayOutputStream writeOut = new ByteArrayOutputStream();
    sfi.write(writeOut);
    assertArrayEquals(expected, writeOut.toByteArray());

    // Lazy materialization of rawSfTypeID and flagByte
    assertArrayEquals(new byte[]{(byte) 0xD3, (byte) 0xFE, (byte) 0xFD}, sfi.getRawSfTypeID());
    EnumSet<SFFlag> flags = sfi.getFlagByte();
    assertNotNull(flags);
    assertEquals(EnumSet.of(SFFlag.hasExtension, SFFlag.isPadded), flags);

    // Mutate via returned EnumSet and verify isFlagSet reflects mutation
    flags.add(SFFlag.isSegmented);
    assertTrue(sfi.isFlagSet(SFFlag.isSegmented));
    sfi.removeFlag(SFFlag.isSegmented);
    assertFalse(sfi.isFlagSet(SFFlag.isSegmented));

    // Reset clears everything cleanly
    sfi.reset();
    assertNull(sfi.getRawSfTypeID());
    assertNull(sfi.getFlagByte());
    assertFalse(sfi.isFlagSet(SFFlag.hasExtension));
  }

  @Test
  public void testSfiByteBufferTruncatedExtensionThrowsAfpParserException() {
    byte[] missingExtLen = {0x00, 0x09, (byte) 0xD3, (byte) 0xA8, (byte) 0xA8, (byte) 0x80, 0, 0};
    assertThrows(AFPParserException.class,
        () -> StructuredFieldIntroducer.parse(ByteBuffer.wrap(missingExtLen)));
    assertThrows(AFPParserException.class,
        () -> StructuredFieldIntroducer.parse(ByteBuffer.wrap(missingExtLen), 0));

    byte[] truncatedExt = {
        0x00, 0x0C, (byte) 0xD3, (byte) 0xA8, (byte) 0xA8, (byte) 0x80, 0, 0, 0x05, 0x01
    };
    assertThrows(AFPParserException.class,
        () -> StructuredFieldIntroducer.parse(ByteBuffer.wrap(truncatedExt)));
    assertThrows(AFPParserException.class,
        () -> StructuredFieldIntroducer.parse(ByteBuffer.wrap(truncatedExt), 0));
  }

  @Test
  public void testScanIndexParallelResistsDecoy5ABytesAndMatchesParser() throws Exception {
    // > 200KB synthetic AFP with decoy 0x5A BPG bytes inside NOP payloads across chunk splits
    ByteArrayOutputStream afpOut = new ByteArrayOutputStream();
    byte[] docName = {(byte) 0xC4, (byte) 0xD6, (byte) 0xC3, (byte) 0xF1, 0x40, 0x40, 0x40, 0x40};
    writeSimpleSf(afpOut, 0xD3, 0xA8, 0xA8, docName); // BDT

    List<Long> expectedSfOffsets = new ArrayList<>();
    expectedSfOffsets.add(0L);
    List<Long> expectedPageOffsets = new ArrayList<>();

    byte[] decoyPayload = new byte[2048];
    for (int i = 0; i < decoyPayload.length - 10; i += 16) {
      // Embed fake BPG inside NOP payload: 5A 00 10 D3 A8 AF
      decoyPayload[i] = 0x5A;
      decoyPayload[i + 1] = 0x00;
      decoyPayload[i + 2] = 0x10;
      decoyPayload[i + 3] = (byte) 0xD3;
      decoyPayload[i + 4] = (byte) 0xA8;
      decoyPayload[i + 5] = (byte) 0xAF;
    }

    for (int p = 0; p < 120; p++) {
      long bpgOff = afpOut.size();
      expectedSfOffsets.add(bpgOff);
      expectedPageOffsets.add(bpgOff);
      writeSimpleSf(afpOut, 0xD3, 0xA8, 0xAF, docName); // BPG

      expectedSfOffsets.add((long) afpOut.size());
      writeSimpleSf(afpOut, 0xD3, 0xEE, 0xEE, decoyPayload); // NOP full of decoys

      expectedSfOffsets.add((long) afpOut.size());
      writeSimpleSf(afpOut, 0xD3, 0xA9, 0xAF, docName); // EPG
    }
    expectedSfOffsets.add((long) afpOut.size());
    writeSimpleSf(afpOut, 0xD3, 0xA9, 0xA8, docName); // EDT

    byte[] bytes = afpOut.toByteArray();
    assertTrue(bytes.length > 200_000, "Must exceed 64KB parallel scanner threshold");

    // Heap ByteBuffer + Direct ByteBuffer
    for (ByteBuffer buf : new ByteBuffer[]{
        ByteBuffer.wrap(bytes),
        ByteBuffer.allocateDirect(bytes.length).put(bytes).flip()
    }) {
      AFPScanner scanner = new AFPScanner(buf);
      AFPScanner.ScanIndex seqIdx = scanner.scanIndex();
      AFPScanner.ScanIndex parIdx = scanner.scanIndexParallel(4);

      assertArrayEquals(seqIdx.sfOffsets(), parIdx.sfOffsets());
      assertArrayEquals(seqIdx.pageOffsets(), parIdx.pageOffsets());
      assertArrayEquals(seqIdx.pageSfIndices(), parIdx.pageSfIndices());

      assertEquals(expectedSfOffsets.size(), parIdx.sfOffsets().length);
      assertEquals(expectedPageOffsets.size(), parIdx.pageOffsets().length);
      for (int i = 0; i < expectedSfOffsets.size(); i++) {
        assertEquals(expectedSfOffsets.get(i).longValue(), parIdx.sfOffsets()[i]);
      }
      for (int i = 0; i < expectedPageOffsets.size(); i++) {
        assertEquals(expectedPageOffsets.get(i).longValue(), parIdx.pageOffsets()[i]);
        assertEquals(parIdx.pageOffsets()[i], parIdx.sfOffsets()[parIdx.pageSfIndices()[i]]);
      }

      // Indexed AFPParser produces identical sequence of SFs
      AFPParserConfiguration cfg = new AFPParserConfiguration();
      cfg.setByteBuffer(buf.duplicate());
      AFPParser indexedParser = new AFPParser(cfg);
      indexedParser.setPreScannedSfOffsets(parIdx.sfOffsets(), 0, parIdx.sfOffsets().length, 0L);

      int count = 0;
      StructuredField sf;
      while ((sf = indexedParser.parseNextSF()) != null) {
        assertEquals(expectedSfOffsets.get(count).longValue(),
            sf.getStructuredFieldIntroducer().getFileOffset());
        sf.release();
        count++;
      }
      assertEquals(expectedSfOffsets.size(), count);
    }

    // Also test AsyncFileChannel scanIndex + ParallelAfpConverter bit-for-bit XML parity on decoy file
    Path afpPath = tempDir.resolve("decoy.afp");
    Files.write(afpPath, bytes);
    try (AsynchronousFileChannel ch = AsynchronousFileChannel.open(afpPath, StandardOpenOption.READ)) {
      AFPScanner asyncScanner = new AFPScanner(ch);
      AFPScanner.ScanIndex asyncIdx = asyncScanner.scanIndexParallel(4);
      assertEquals(expectedSfOffsets.size(), asyncIdx.sfOffsets().length);
      assertEquals(expectedPageOffsets.size(), asyncIdx.pageOffsets().length);
    }

    Path seqXml = tempDir.resolve("decoy_seq.xml");
    assertEquals(0, Afp2Xml.execute(new String[]{afpPath.toString(), seqXml.toString()}));
    byte[] seqBytes = Files.readAllBytes(seqXml);

    AFPParserConfiguration parCfg = new AFPParserConfiguration();
    parCfg.setAFPFile(afpPath.toFile());
    ByteArrayOutputStream parXml = new ByteArrayOutputStream();
    new ParallelAfpConverter(parCfg, 4, new XmlHandlerFactory()).convert(parXml);
    assertArrayEquals(seqBytes, parXml.toByteArray(),
        "Indexed parallel converter with 0x5A decoys must match sequential XML bit-for-bit");
  }

  @Test
  public void testScanIndexParityOnExternalAfpFile() throws Exception {
    File afpFile = new File("src/test/resources/afp/external/afplib_start.afp");
    if (!afpFile.exists()) {
      return;
    }

    byte[] bytes = Files.readAllBytes(afpFile.toPath());
    AFPScanner scanner = new AFPScanner(ByteBuffer.wrap(bytes));
    AFPScanner.ScanIndex parIdx = scanner.scanIndexParallel(4);

    AFPParserConfiguration seqCfg = new AFPParserConfiguration();
    seqCfg.setByteBuffer(ByteBuffer.wrap(bytes));
    AFPParser seqParser = new AFPParser(seqCfg);

    List<Long> expectedSfs = new ArrayList<>();
    List<Long> expectedBpgs = new ArrayList<>();
    StructuredField sf;
    while ((sf = seqParser.parseNextSF()) != null) {
      long off = sf.getStructuredFieldIntroducer().getFileOffset();
      expectedSfs.add(off);
      if (sf.getStructuredFieldIntroducer().getSFTypeID() == SFTypeID.BPG_BeginPage) {
        expectedBpgs.add(off);
      }
      sf.release();
    }

    assertEquals(expectedSfs.size(), parIdx.sfOffsets().length);
    assertEquals(expectedBpgs.size(), parIdx.pageOffsets().length);
    for (int i = 0; i < expectedSfs.size(); i++) {
      assertEquals(expectedSfs.get(i).longValue(), parIdx.sfOffsets()[i]);
    }
    for (int i = 0; i < expectedBpgs.size(); i++) {
      assertEquals(expectedBpgs.get(i).longValue(), parIdx.pageOffsets()[i]);
    }
  }

  private static void writeSimpleSf(
      ByteArrayOutputStream out, int sfClass, int sfType, int sfCat, byte[] payload) {
    int totalLen = 8 + (payload != null ? payload.length : 0);
    out.write(0x5A);
    out.write((totalLen >>> 8) & 0xFF);
    out.write(totalLen & 0xFF);
    out.write(sfClass);
    out.write(sfType);
    out.write(sfCat);
    out.write(0x00);
    out.write(0x00);
    out.write(0x00);
    if (payload != null && payload.length > 0) {
      out.writeBytes(payload);
    }
  }
}
