/*
Copyright 2026 Alpheus AFP Parser Authors

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
along with Alpheus AFP Parser.  See <http://www.gnu.org/licenses/>
*/

package com.mgz.afp.ipds;

import com.mgz.afp.base.annotations.AFPField;
import com.mgz.afp.exceptions.AFPParserException;
import com.mgz.afp.parser.AFPParserConfiguration;
import com.mgz.util.UtilBinaryDecoding;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;

/**
 * IPDS Load Font Character Set Control (LFCSC) Command (X'D619').
 */
public class LFCSC_LoadFontCharacterSetControl extends IPDSCommand {

  @AFPField
  private int haid;
  @AFPField
  private byte patternTechnology;
  @AFPField
  private byte intendedUseFlags;
  @AFPField
  private long loadFontCount;
  @AFPField
  private long mapSize;
  @AFPField
  private int characterIdCount;
  @AFPField
  private Integer gcsgid;
  @AFPField
  private Integer fgid;

  @Override
  public void decodeAFP(byte[] sfData, int offset, int length, AFPParserConfiguration config)
      throws AFPParserException {
    int actualLength = getActualLength(sfData, offset, length);
    int consumed = decodeIPDSHeader(sfData, offset, actualLength);
    int current = offset + consumed;
    int remaining = actualLength - consumed;

    if (remaining >= 17) {
      this.haid = UtilBinaryDecoding.parseInt(sfData, current, 2);
      // bytes 2-3 reserved
      this.patternTechnology = sfData[current + 4];
      // byte 5 reserved
      this.intendedUseFlags = sfData[current + 6];
      this.loadFontCount = UtilBinaryDecoding.parseLong(sfData, current + 7, 4);
      this.mapSize = UtilBinaryDecoding.parseLong(sfData, current + 11, 4);
      this.characterIdCount = UtilBinaryDecoding.parseInt(sfData, current + 15, 2);

      if (remaining >= 21) {
        this.gcsgid = UtilBinaryDecoding.parseInt(sfData, current + 17, 2);
        this.fgid = UtilBinaryDecoding.parseInt(sfData, current + 19, 2);
      }
    }
  }

  @Override
  public void writeAFP(OutputStream os, AFPParserConfiguration config) throws IOException {
    ByteArrayOutputStream baos = new ByteArrayOutputStream();
    writeIPDSHeader(baos);
    baos.write(UtilBinaryDecoding.intToByteArray(haid, 2));
    baos.write(new byte[] {0, 0}); // Reserved
    baos.write(patternTechnology);
    baos.write(0); // Reserved
    baos.write(intendedUseFlags);
    baos.write(UtilBinaryDecoding.longToByteArray(loadFontCount, 4));
    baos.write(UtilBinaryDecoding.longToByteArray(mapSize, 4));
    baos.write(UtilBinaryDecoding.intToByteArray(characterIdCount, 2));

    if (gcsgid != null && fgid != null) {
      baos.write(UtilBinaryDecoding.intToByteArray(gcsgid, 2));
      baos.write(UtilBinaryDecoding.intToByteArray(fgid, 2));
    }

    writeFullStructuredField(os, baos.toByteArray());
  }

  public int getHaid() {
    return haid;
  }

  public void setHaid(int haid) {
    this.haid = haid;
  }

  public byte getPatternTechnology() {
    return patternTechnology;
  }

  public void setPatternTechnology(byte patternTechnology) {
    this.patternTechnology = patternTechnology;
  }

  public byte getIntendedUseFlags() {
    return intendedUseFlags;
  }

  public void setIntendedUseFlags(byte intendedUseFlags) {
    this.intendedUseFlags = intendedUseFlags;
  }

  public long getLoadFontCount() {
    return loadFontCount;
  }

  public void setLoadFontCount(long loadFontCount) {
    this.loadFontCount = loadFontCount;
  }

  public long getMapSize() {
    return mapSize;
  }

  public void setMapSize(long mapSize) {
    this.mapSize = mapSize;
  }

  public int getCharacterIdCount() {
    return characterIdCount;
  }

  public void setCharacterIdCount(int characterIdCount) {
    this.characterIdCount = characterIdCount;
  }

  public Integer getGcsgid() {
    return gcsgid;
  }

  public void setGcsgid(Integer gcsgid) {
    this.gcsgid = gcsgid;
  }

  public Integer getFgid() {
    return fgid;
  }

  public void setFgid(Integer fgid) {
    this.fgid = fgid;
  }
}
