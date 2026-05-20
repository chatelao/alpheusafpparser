/*
Copyright 2024 Rudolf Fiala

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

import com.mgz.acceptance.FilesSuite;
import com.mgz.afp.enums.SFTypeID;
import com.mgz.afp.base.StructuredField;
import com.mgz.afp.modca.BPG_BeginPage;
import org.junit.jupiter.api.Test;
import java.io.File;
import java.nio.ByteBuffer;
import java.nio.file.Files;
import java.util.List;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class AFPScannerTest {

  @Test
  public void testScanForBPG() throws Exception {
    File[] afpFiles = FilesSuite.getAfpFiles();
    if (afpFiles == null || afpFiles.length == 0) {
      return;
    }

    for (File afpFile : afpFiles) {
      byte[] content = Files.readAllBytes(afpFile.toPath());
      ByteBuffer buffer = ByteBuffer.wrap(content);

      // Scan using AFPScanner
      List<Long> scannedOffsets = AFPScanner.scanForType(buffer, SFTypeID.BPG_BeginPage);

      // Verify using AFPParser
      List<Long> parserOffsets = new ArrayList<>();
      AFPParserConfiguration config = new AFPParserConfiguration();
      config.setInputStream(Files.newInputStream(afpFile.toPath()));
      AFPParser parser = new AFPParser(config);

      StructuredField sf;
      while ((sf = parser.parseNextSF()) != null) {
        if (sf instanceof BPG_BeginPage) {
          parserOffsets.add(sf.getStructuredFieldIntroducer().getFileOffset());
        }
      }

      assertEquals(parserOffsets.size(), scannedOffsets.size(), "Mismatch in BPG count for " + afpFile.getName());
      for (int i = 0; i < parserOffsets.size(); i++) {
        assertEquals(parserOffsets.get(i), scannedOffsets.get(i), "Offset mismatch at index " + i + " for " + afpFile.getName());
      }
    }
  }

  @Test
  public void testScanForMultipleTypes() throws Exception {
    File[] afpFiles = FilesSuite.getAfpFiles();
    if (afpFiles == null || afpFiles.length == 0) {
      return;
    }

    List<SFTypeID> searchTypes = List.of(SFTypeID.BPG_BeginPage, SFTypeID.TLE_TagLogicalElement);

    for (File afpFile : afpFiles) {
      byte[] content = Files.readAllBytes(afpFile.toPath());
      ByteBuffer buffer = ByteBuffer.wrap(content);

      List<Long> scannedOffsets = AFPScanner.scanForTypes(buffer, searchTypes);

      List<Long> parserOffsets = new ArrayList<>();
      AFPParserConfiguration config = new AFPParserConfiguration();
      config.setInputStream(Files.newInputStream(afpFile.toPath()));
      AFPParser parser = new AFPParser(config);

      StructuredField sf;
      while ((sf = parser.parseNextSF()) != null) {
        if (searchTypes.contains(sf.getStructuredFieldIntroducer().getSFTypeID())) {
          parserOffsets.add(sf.getStructuredFieldIntroducer().getFileOffset());
        }
      }

      assertEquals(parserOffsets.size(), scannedOffsets.size(), "Mismatch in combined count for " + afpFile.getName());
      for (int i = 0; i < parserOffsets.size(); i++) {
        assertEquals(parserOffsets.get(i), scannedOffsets.get(i), "Offset mismatch at index " + i + " for " + afpFile.getName());
      }
    }
  }
}
