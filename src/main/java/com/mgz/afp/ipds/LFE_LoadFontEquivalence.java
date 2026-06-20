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
 * IPDS Load Font Equivalence (LFE) Command (X'D63F').
 * Refer to IPDS-4-013, IPDS-4-480.
 */
public class LFE_LoadFontEquivalence extends IPDSCommand implements IHasRepeatingGroups {

  public static class LFE_RepeatingGroup implements IRepeatingGroup {

    @Override
    public void reset() {
      lid = 0;
      haid = 0;
      fis = 0;
      gcsgid = 0;
      cpgid = 0;
      fgid = 0;
      fw = 0;
      flags = 0;
    }

    @Override
    public void decodeAFP(byte[] sfData, int offset, int length, AFPParserConfiguration config)
        throws AFPParserException {
      // Not used individually
    }

    @Override
    public void writeAFP(OutputStream os, AFPParserConfiguration config) throws IOException {
      os.write(lid & 0xFF);
      os.write(UtilBinaryDecoding.intToByteArray(haid, 2));
      os.write(UtilBinaryDecoding.intToByteArray(fis, 2));
      os.write(UtilBinaryDecoding.intToByteArray(gcsgid, 2));
      os.write(UtilBinaryDecoding.intToByteArray(cpgid, 2));
      os.write(UtilBinaryDecoding.intToByteArray(fgid, 2));
      os.write(UtilBinaryDecoding.intToByteArray(fw, 2));
      os.write(0); // Reserved
      os.write(flags & 0xFF);
      os.write(0); // Reserved
    }

    private short lid;
    private int haid;
    private int fis;
    private int gcsgid;
    private int cpgid;
    private int fgid;
    private int fw;
    private byte flags;

    public short getLid() {
      return lid;
    }

    public void setLid(short lid) {
      this.lid = lid;
    }

    public int getHaid() {
      return haid;
    }

    public void setHaid(int haid) {
      this.haid = haid;
    }

    public int getFis() {
      return fis;
    }

    public void setFis(int fis) {
      this.fis = fis;
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

    public int getFgid() {
      return fgid;
    }

    public void setFgid(int fgid) {
      this.fgid = fgid;
    }

    public int getFw() {
      return fw;
    }

    public void setFw(int fw) {
      this.fw = fw;
    }

    public byte getFlags() {
      return flags;
    }

    public void setFlags(byte flags) {
      this.flags = flags;
    }
  }

  @AFPField
  private List<IRepeatingGroup> repeatingGroups;

  @Override
  public void decodeAFP(byte[] sfData, int offset, int length, AFPParserConfiguration config)
      throws AFPParserException {
    int actualLength = getActualLength(sfData, offset, length);
    int consumed = decodeIPDSHeader(sfData, offset, actualLength);

    this.repeatingGroups = new ArrayList<>();
    int current = offset + consumed;
    while (current + 16 <= offset + actualLength) {
      LFE_RepeatingGroup rg = new LFE_RepeatingGroup();
      rg.lid = (short) (sfData[current] & 0xFF);
      rg.haid = UtilBinaryDecoding.parseInt(sfData, current + 1, 2);
      rg.fis = UtilBinaryDecoding.parseInt(sfData, current + 3, 2);
      rg.gcsgid = UtilBinaryDecoding.parseInt(sfData, current + 5, 2);
      rg.cpgid = UtilBinaryDecoding.parseInt(sfData, current + 7, 2);
      rg.fgid = UtilBinaryDecoding.parseInt(sfData, current + 9, 2);
      rg.fw = UtilBinaryDecoding.parseInt(sfData, current + 11, 2);
      rg.flags = sfData[current + 14];
      repeatingGroups.add(rg);
      current += 16;
    }
  }

  @Override
  public void writeAFP(OutputStream os, AFPParserConfiguration config) throws IOException {
    ByteArrayOutputStream baos = new ByteArrayOutputStream();
    writeIPDSHeader(baos);
    if (repeatingGroups != null) {
      for (IRepeatingGroup irg : repeatingGroups) {
        irg.writeAFP(baos, config);
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
}
