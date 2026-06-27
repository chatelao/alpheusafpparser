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
 * IPDS Load Font Control (LFC) Command (X'D61F').
 * Refer to IPDS-14-178.
 */
public class LFC_LoadFontControl extends IPDSCommand implements IHasRepeatingGroups {

  public static class LFC_CharacterPatternDescriptor implements IRepeatingGroup {
    private int xSize;
    private int ySize;
    private long address;

    @Override
    public void reset() {
      xSize = 0;
      ySize = 0;
      address = 0;
    }

    @Override
    public void decodeAFP(byte[] sfData, int offset, int length, AFPParserConfiguration config)
        throws AFPParserException {
      // Not used individually
    }

    @Override
    public void writeAFP(OutputStream os, AFPParserConfiguration config) throws IOException {
      os.write(UtilBinaryDecoding.intToByteArray(xSize, 2));
      os.write(UtilBinaryDecoding.intToByteArray(ySize, 2));
      os.write(UtilBinaryDecoding.longToByteArray(address, 4));
    }

    public int getxSize() {
      return xSize;
    }

    public void setxSize(int xSize) {
      this.xSize = xSize;
    }

    public int getySize() {
      return ySize;
    }

    public void setySize(int ySize) {
      this.ySize = ySize;
    }

    public long getAddress() {
      return address;
    }

    public void setAddress(long address) {
      this.address = address;
    }
  }

  @AFPField
  private int haid;
  @AFPField
  private short sectionId;
  @AFPField
  private short format;
  @AFPField
  private short patternFormat;
  @AFPField
  private byte fontTypeFlags;
  @AFPField
  private int xSize;
  @AFPField
  private int ySize;
  @AFPField
  private short lUnitBase;
  @AFPField
  private int xUpub;
  @AFPField
  private int yUpub;
  @AFPField
  private int byteCount;
  @AFPField
  private short dataAlignment;
  @AFPField
  private int gcsgid;
  @AFPField
  private int cpgid;
  @AFPField
  private short pelUnitBase;
  @AFPField
  private int xPelUnits;
  @AFPField
  private int yPelUnits;
  @AFPField
  private int rmmf;
  @AFPField
  private int fgid;
  @AFPField
  private byte intendedUseFlags;
  @AFPField
  private int fw;

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
    this.format = (short) (sfData[current + 3] & 0xFF);
    this.patternFormat = (short) (sfData[current + 4] & 0xFF);
    this.fontTypeFlags = sfData[current + 5];
    this.xSize = UtilBinaryDecoding.parseInt(sfData, current + 6, 2);
    this.ySize = UtilBinaryDecoding.parseInt(sfData, current + 8, 2);
    this.lUnitBase = (short) (sfData[current + 10] & 0xFF);
    // byte 11 reserved
    this.xUpub = UtilBinaryDecoding.parseInt(sfData, current + 12, 2);
    this.yUpub = UtilBinaryDecoding.parseInt(sfData, current + 14, 2);
    // bytes 16-17 reserved
    this.byteCount = UtilBinaryDecoding.parseInt(sfData, current + 18, 3);
    this.dataAlignment = (short) (sfData[current + 21] & 0xFF);
    this.gcsgid = UtilBinaryDecoding.parseInt(sfData, current + 22, 2);
    this.cpgid = UtilBinaryDecoding.parseInt(sfData, current + 24, 2);
    this.pelUnitBase = (short) (sfData[current + 26] & 0xFF);
    // byte 27 reserved
    this.xPelUnits = UtilBinaryDecoding.parseInt(sfData, current + 28, 2);
    this.yPelUnits = UtilBinaryDecoding.parseInt(sfData, current + 30, 2);
    this.rmmf = UtilBinaryDecoding.parseInt(sfData, current + 32, 2);
    this.fgid = UtilBinaryDecoding.parseInt(sfData, current + 34, 2);
    // byte 36 reserved (0x01)
    this.intendedUseFlags = sfData[current + 37];
    this.fw = UtilBinaryDecoding.parseInt(sfData, current + 38, 2);

    this.repeatingGroups = new ArrayList<>();
    current += 40;
    while (current + 8 <= offset + actualLength) {
      LFC_CharacterPatternDescriptor rg = new LFC_CharacterPatternDescriptor();
      rg.xSize = UtilBinaryDecoding.parseInt(sfData, current, 2);
      rg.ySize = UtilBinaryDecoding.parseInt(sfData, current + 2, 2);
      rg.address = UtilBinaryDecoding.parseLong(sfData, current + 4, 4);
      repeatingGroups.add(rg);
      current += 8;
    }
  }

  @Override
  public void writeAFP(OutputStream os, AFPParserConfiguration config) throws IOException {
    ByteArrayOutputStream baos = new ByteArrayOutputStream();
    writeIPDSHeader(baos);
    baos.write(UtilBinaryDecoding.intToByteArray(haid, 2));
    baos.write(sectionId & 0xFF);
    baos.write(format & 0xFF);
    baos.write(patternFormat & 0xFF);
    baos.write(fontTypeFlags);
    baos.write(UtilBinaryDecoding.intToByteArray(xSize, 2));
    baos.write(UtilBinaryDecoding.intToByteArray(ySize, 2));
    baos.write(lUnitBase & 0xFF);
    baos.write(0); // byte 11 reserved
    baos.write(UtilBinaryDecoding.intToByteArray(xUpub, 2));
    baos.write(UtilBinaryDecoding.intToByteArray(yUpub, 2));
    baos.write(new byte[] {0, 0}); // bytes 16-17 reserved
    baos.write(UtilBinaryDecoding.intToByteArray(byteCount, 3));
    baos.write(dataAlignment & 0xFF);
    baos.write(UtilBinaryDecoding.intToByteArray(gcsgid, 2));
    baos.write(UtilBinaryDecoding.intToByteArray(cpgid, 2));
    baos.write(pelUnitBase & 0xFF);
    baos.write(0); // byte 27 reserved
    baos.write(UtilBinaryDecoding.intToByteArray(xPelUnits, 2));
    baos.write(UtilBinaryDecoding.intToByteArray(yPelUnits, 2));
    baos.write(UtilBinaryDecoding.intToByteArray(rmmf, 2));
    baos.write(UtilBinaryDecoding.intToByteArray(fgid, 2));
    baos.write(0x01); // byte 36 reserved (0x01)
    baos.write(intendedUseFlags);
    baos.write(UtilBinaryDecoding.intToByteArray(fw, 2));

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

  public short getFormat() {
    return format;
  }

  public void setFormat(short format) {
    this.format = format;
  }

  public short getPatternFormat() {
    return patternFormat;
  }

  public void setPatternFormat(short patternFormat) {
    this.patternFormat = patternFormat;
  }

  public byte getFontTypeFlags() {
    return fontTypeFlags;
  }

  public void setFontTypeFlags(byte fontTypeFlags) {
    this.fontTypeFlags = fontTypeFlags;
  }

  public int getxSize() {
    return xSize;
  }

  public void setxSize(int xSize) {
    this.xSize = xSize;
  }

  public int getySize() {
    return ySize;
  }

  public void setySize(int ySize) {
    this.ySize = ySize;
  }

  public short getlUnitBase() {
    return lUnitBase;
  }

  public void setlUnitBase(short lUnitBase) {
    this.lUnitBase = lUnitBase;
  }

  public int getxUpub() {
    return xUpub;
  }

  public void setxUpub(int xUpub) {
    this.xUpub = xUpub;
  }

  public int getyUpub() {
    return yUpub;
  }

  public void setyUpub(int yUpub) {
    this.yUpub = yUpub;
  }

  public int getByteCount() {
    return byteCount;
  }

  public void setByteCount(int byteCount) {
    this.byteCount = byteCount;
  }

  public short getDataAlignment() {
    return dataAlignment;
  }

  public void setDataAlignment(short dataAlignment) {
    this.dataAlignment = dataAlignment;
  }

  public int getGcsgid() {
    return gcsgid;
  }

  public void setGcsgid(int gcsgid) {
    this.gcsgid = gcsgid;
  }

  public int getCpgid() {
    return cpgid;
  }

  public void setCpgid(int cpgid) {
    this.cpgid = cpgid;
  }

  public short getPelUnitBase() {
    return pelUnitBase;
  }

  public void setPelUnitBase(short pelUnitBase) {
    this.pelUnitBase = pelUnitBase;
  }

  public int getxPelUnits() {
    return xPelUnits;
  }

  public void setxPelUnits(int xPelUnits) {
    this.xPelUnits = xPelUnits;
  }

  public int getyPelUnits() {
    return yPelUnits;
  }

  public void setyPelUnits(int yPelUnits) {
    this.yPelUnits = yPelUnits;
  }

  public int getRmmf() {
    return rmmf;
  }

  public void setRmmf(int rmmf) {
    this.rmmf = rmmf;
  }

  public int getFgid() {
    return fgid;
  }

  public void setFgid(int fgid) {
    this.fgid = fgid;
  }

  public byte getIntendedUseFlags() {
    return intendedUseFlags;
  }

  public void setIntendedUseFlags(byte intendedUseFlags) {
    this.intendedUseFlags = intendedUseFlags;
  }

  public int getFw() {
    return fw;
  }

  public void setFw(int fw) {
    this.fw = fw;
  }
}
