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
along with Alpheus AFP Parser.  If not, see <http://www.gnu.org/licenses/>
*/

package com.mgz.afp.ipds;

import com.mgz.afp.base.IHasRepeatingGroups;
import com.mgz.afp.base.IRepeatingGroup;
import com.mgz.afp.base.annotations.AFPField;
import com.mgz.afp.exceptions.AFPParserException;
import com.mgz.afp.parser.AFPParserConfiguration;
import com.mgz.util.UtilBinaryDecoding;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * IPDS Load Font Index (LFI) Command (X'D60F').
 * Refer to IPDS-14-256.
 */
public class LFI_LoadFontIndex extends IPDSCommand implements IHasRepeatingGroups {

  public static class LFI_CharacterIndexEntry implements IRepeatingGroup {
    private int characterFlags;
    private int patternIndex;
    private int characterIncrement;
    private int aSpace;
    private int baselineOffset;

    @Override
    public void reset() {
      characterFlags = 0;
      patternIndex = 0;
      characterIncrement = 0;
      aSpace = 0;
      baselineOffset = 0;
    }

    @Override
    public void decodeAFP(byte[] sfData, int offset, int length, AFPParserConfiguration config)
        throws AFPParserException {
      // Not used individually
    }

    @Override
    public void writeAFP(OutputStream os, AFPParserConfiguration config) throws IOException {
      os.write(UtilBinaryDecoding.intToByteArray(characterFlags, 2));
      os.write(UtilBinaryDecoding.intToByteArray(patternIndex, 2));
      os.write(UtilBinaryDecoding.intToByteArray(characterIncrement, 2));
      os.write(UtilBinaryDecoding.intToByteArray(aSpace, 2));
      os.write(new byte[] {0, 0, 0, 0, 0, 0}); // bytes 8-13 reserved
      os.write(UtilBinaryDecoding.intToByteArray(baselineOffset, 2));
    }

    public int getCharacterFlags() {
      return characterFlags;
    }

    public void setCharacterFlags(int characterFlags) {
      this.characterFlags = characterFlags;
    }

    public int getPatternIndex() {
      return patternIndex;
    }

    public void setPatternIndex(int patternIndex) {
      this.patternIndex = patternIndex;
    }

    public int getCharacterIncrement() {
      return characterIncrement;
    }

    public void setCharacterIncrement(int characterIncrement) {
      this.characterIncrement = characterIncrement;
    }

    public int getaSpace() {
      return aSpace;
    }

    public void setaSpace(int aSpace) {
      this.aSpace = aSpace;
    }

    public int getBaselineOffset() {
      return baselineOffset;
    }

    public void setBaselineOffset(int baselineOffset) {
      this.baselineOffset = baselineOffset;
    }
  }

  @AFPField
  private int haid;
  @AFPField
  private short sectionId;
  @AFPField
  private byte spaceFlags;
  @AFPField
  private int fis;
  @AFPField
  private int baselineOffset;
  @AFPField
  private int characterIncrement;
  @AFPField
  private int maxExtent;
  @AFPField
  private byte orientationFlags;
  @AFPField
  private int aSpace;
  @AFPField
  private int vsp;
  @AFPField
  private int defaultVsi;
  @AFPField
  private int underscoreWidth;
  @AFPField
  private int underscorePosition;

  @AFPField
  private List<IRepeatingGroup> repeatingGroups;

  @Override
  public void decodeAFP(byte[] sfData, int offset, int length, AFPParserConfiguration config)
      throws AFPParserException {
    int actualLength = getActualLength(sfData, offset, length);
    int consumed = decodeIPDSHeader(sfData, offset, actualLength);
    int current = offset + consumed;

    this.haid = UtilBinaryDecoding.parseInt(sfData, current, 2);
    this.sectionId = (short) (sfData[current + 2] & 0xFF);
    this.spaceFlags = sfData[current + 3];
    this.fis = UtilBinaryDecoding.parseInt(sfData, current + 4, 2);
    // bytes 6-7 reserved
    this.baselineOffset = UtilBinaryDecoding.parseInt(sfData, current + 8, 2);
    this.characterIncrement = UtilBinaryDecoding.parseInt(sfData, current + 10, 2);
    // bytes 12-13 reserved
    this.maxExtent = UtilBinaryDecoding.parseInt(sfData, current + 14, 2);
    this.orientationFlags = sfData[current + 16];
    // byte 17 reserved
    this.aSpace = UtilBinaryDecoding.parseInt(sfData, current + 18, 2);
    this.vsp = UtilBinaryDecoding.parseInt(sfData, current + 20, 2);
    this.defaultVsi = UtilBinaryDecoding.parseInt(sfData, current + 22, 2);
    this.underscoreWidth = UtilBinaryDecoding.parseInt(sfData, current + 24, 2);
    this.underscorePosition = UtilBinaryDecoding.parseInt(sfData, current + 26, 2);
    // bytes 28-31 reserved

    this.repeatingGroups = new ArrayList<>();
    current += 32;
    while (current + 16 <= offset + actualLength) {
      LFI_CharacterIndexEntry rg = new LFI_CharacterIndexEntry();
      rg.characterFlags = UtilBinaryDecoding.parseInt(sfData, current, 2);
      rg.patternIndex = UtilBinaryDecoding.parseInt(sfData, current + 2, 2);
      rg.characterIncrement = UtilBinaryDecoding.parseInt(sfData, current + 4, 2);
      rg.aSpace = UtilBinaryDecoding.parseInt(sfData, current + 6, 2);
      // bytes 8-13 reserved
      rg.baselineOffset = UtilBinaryDecoding.parseInt(sfData, current + 14, 2);
      repeatingGroups.add(rg);
      current += 16;
    }
  }

  @Override
  public void writeAFP(OutputStream os, AFPParserConfiguration config) throws IOException {
    ByteArrayOutputStream baos = new ByteArrayOutputStream();
    writeIPDSHeader(baos);
    baos.write(UtilBinaryDecoding.intToByteArray(haid, 2));
    baos.write(sectionId & 0xFF);
    baos.write(spaceFlags);
    baos.write(UtilBinaryDecoding.intToByteArray(fis, 2));
    baos.write(new byte[] {0, 0}); // bytes 6-7 reserved
    baos.write(UtilBinaryDecoding.intToByteArray(baselineOffset, 2));
    baos.write(UtilBinaryDecoding.intToByteArray(characterIncrement, 2));
    baos.write(new byte[] {0, 0}); // bytes 12-13 reserved
    baos.write(UtilBinaryDecoding.intToByteArray(maxExtent, 2));
    baos.write(orientationFlags);
    baos.write(0); // byte 17 reserved
    baos.write(UtilBinaryDecoding.intToByteArray(aSpace, 2));
    baos.write(UtilBinaryDecoding.intToByteArray(vsp, 2));
    baos.write(UtilBinaryDecoding.intToByteArray(defaultVsi, 2));
    baos.write(UtilBinaryDecoding.intToByteArray(underscoreWidth, 2));
    baos.write(UtilBinaryDecoding.intToByteArray(underscorePosition, 2));
    baos.write(new byte[] {0, 0, 0, 0}); // bytes 28-31 reserved

    if (repeatingGroups != null) {
      for (IRepeatingGroup rg : repeatingGroups) {
        rg.writeAFP(baos, config);
      }
    }
    writeFullStructuredField(os, baos.toByteArray());
  }

  @Override
  public List<IRepeatingGroup> getRepeatingGroups() {
    return repeatingGroups;
  }

  @Override
  public void setRepeatingGroups(List<IRepeatingGroup> repeatingGroups) {
    this.repeatingGroups = repeatingGroups;
  }

  @Override
  public void addRepeatingGroup(IRepeatingGroup rg) {
    if (this.repeatingGroups == null) {
      this.repeatingGroups = new ArrayList<>();
    }
    this.repeatingGroups.add(rg);
  }

  @Override
  public void removeRepeatingGroup(IRepeatingGroup rg) {
    if (this.repeatingGroups != null) {
      this.repeatingGroups.remove(rg);
    }
  }

  public int getHaid() {
    return haid;
  }

  public void setHaid(int haid) {
    this.haid = haid;
  }

  public short getSectionId() {
    return sectionId;
  }

  public void setSectionId(short sectionId) {
    this.sectionId = sectionId;
  }

  public byte getSpaceFlags() {
    return spaceFlags;
  }

  public void setSpaceFlags(byte spaceFlags) {
    this.spaceFlags = spaceFlags;
  }

  public int getFis() {
    return fis;
  }

  public void setFis(int fis) {
    this.fis = fis;
  }

  public int getBaselineOffset() {
    return baselineOffset;
  }

  public void setBaselineOffset(int baselineOffset) {
    this.baselineOffset = baselineOffset;
  }

  public int getCharacterIncrement() {
    return characterIncrement;
  }

  public void setCharacterIncrement(int characterIncrement) {
    this.characterIncrement = characterIncrement;
  }

  public int getMaxExtent() {
    return maxExtent;
  }

  public void setMaxExtent(int maxExtent) {
    this.maxExtent = maxExtent;
  }

  public byte getOrientationFlags() {
    return orientationFlags;
  }

  public void setOrientationFlags(byte orientationFlags) {
    this.orientationFlags = orientationFlags;
  }

  public int getaSpace() {
    return aSpace;
  }

  public void setaSpace(int aSpace) {
    this.aSpace = aSpace;
  }

  public int getVsp() {
    return vsp;
  }

  public void setVsp(int vsp) {
    this.vsp = vsp;
  }

  public int getDefaultVsi() {
    return defaultVsi;
  }

  public void setDefaultVsi(int defaultVsi) {
    this.defaultVsi = defaultVsi;
  }

  public int getUnderscoreWidth() {
    return underscoreWidth;
  }

  public void setUnderscoreWidth(int underscoreWidth) {
    this.underscoreWidth = underscoreWidth;
  }

  public int getUnderscorePosition() {
    return underscorePosition;
  }

  public void setUnderscorePosition(int underscorePosition) {
    this.underscorePosition = underscorePosition;
  }
}
