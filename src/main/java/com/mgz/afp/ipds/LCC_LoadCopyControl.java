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
 * IPDS Load Copy Control (LCC) Command (X'D69F').
 * Refer to IPDS-4-012, IPDS-4-361.
 */
public class LCC_LoadCopyControl extends IPDSCommand implements IHasRepeatingGroups {

  @Override
  public void removeRepeatingGroup(IRepeatingGroup rg) {
    if (this.repeatingGroups != null) {
      this.repeatingGroups.remove(rg);
    }
  }

  @Override
  public void addRepeatingGroup(IRepeatingGroup rg) {
    if (this.repeatingGroups == null) {
      this.repeatingGroups = new ArrayList<>();
    }
    this.repeatingGroups.add(rg);
  }

  public static class LCC_RepeatingGroup implements IRepeatingGroup {

    @Override
    public void reset() {
      count = 0;
      copies = 0;
      keywords = null;
    }

    @Override
    public void decodeAFP(byte[] sfData, int offset, int length, AFPParserConfiguration config) throws AFPParserException {
      // Not used individually in LCC
    }

    @Override
    public void writeAFP(OutputStream os, AFPParserConfiguration config) throws IOException {
      os.write(count & 0xFF);
      os.write(copies & 0xFF);
      if (keywords != null) {
        os.write(keywords);
      }
    }

    private short count;
    private short copies;
    private byte[] keywords;

    public short getCount() {
      return count;
    }

    public void setCount(short count) {
      this.count = count;
    }

    public short getCopies() {
      return copies;
    }

    public void setCopies(short copies) {
      this.copies = copies;
    }

    public byte[] getKeywords() {
      return keywords;
    }

    public void setKeywords(byte[] keywords) {
      this.keywords = keywords;
    }
  }

  @AFPField
  private List<IRepeatingGroup> repeatingGroups;

  @Override
  public void decodeAFP(byte[] sfData, int offset, int length, AFPParserConfiguration config) throws AFPParserException {
    int actualLength = getActualLength(sfData, offset, length);
    int consumed = decodeIPDSHeader(sfData, offset, actualLength);

    this.repeatingGroups = new ArrayList<>();
    int current = offset + consumed;
    while (current < offset + actualLength) {
      LCC_RepeatingGroup rg = new LCC_RepeatingGroup();
      rg.count = (short) (sfData[current] & 0xFF);
      if (rg.count < 2) {
        throw new AFPParserException("LCC Repeating Group count must be at least 2");
      }
      rg.copies = (short) (sfData[current + 1] & 0xFF);
      if (rg.count > 2) {
        rg.keywords = new byte[rg.count - 2];
        System.arraycopy(sfData, current + 2, rg.keywords, 0, rg.count - 2);
      }
      this.repeatingGroups.add(rg);
      current += rg.count;
    }
  }

  @Override
  public void writeAFP(OutputStream os, AFPParserConfiguration config) throws IOException {
    ByteArrayOutputStream baos = new ByteArrayOutputStream();
    writeIPDSHeader(baos);
    if (repeatingGroups != null) {
      for (IRepeatingGroup irg : repeatingGroups) {
        LCC_RepeatingGroup rg = (LCC_RepeatingGroup) irg;
        baos.write(rg.count & 0xFF);
        baos.write(rg.copies & 0xFF);
        if (rg.keywords != null) {
          baos.write(rg.keywords);
        }
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

  public void addRepeatingGroup(LCC_RepeatingGroup rg) {
    if (this.repeatingGroups == null) {
      this.repeatingGroups = new ArrayList<>();
    }
    this.repeatingGroups.add(rg);
  }
}
