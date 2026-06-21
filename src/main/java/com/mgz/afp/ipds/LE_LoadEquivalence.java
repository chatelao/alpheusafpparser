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

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
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
 * IPDS Load Equivalence (LE) Command (X'D61D').
 * Refer to IPDS-5-003.
 */
public class LE_LoadEquivalence extends IPDSCommand implements IHasRepeatingGroups {

  @AFPField
  private int mappingType;

  private List<IRepeatingGroup> entries;

  @Override
  public void decodeAFP(byte[] sfData, int offset, int length, AFPParserConfiguration config)
      throws AFPParserException {
    int actualLength = getActualLength(sfData, offset, length);
    int consumed = decodeIPDSHeader(sfData, offset, actualLength);

    checkDataLength(sfData, offset + consumed, actualLength - consumed, 2);
    this.mappingType = UtilBinaryDecoding.parseInt(sfData, offset + consumed, 2);
    consumed += 2;

    this.entries = new ArrayList<>();
    while (consumed + 4 <= actualLength) {
      LE_RepeatingGroup entry = new LE_RepeatingGroup();
      entry.internal = UtilBinaryDecoding.parseInt(sfData, offset + consumed, 2);
      entry.external = UtilBinaryDecoding.parseInt(sfData, offset + consumed + 2, 2);
      entries.add(entry);
      consumed += 4;
    }
  }

  @Override
  public void writeAFP(OutputStream os, AFPParserConfiguration config) throws IOException {
    ByteArrayOutputStream baos = new ByteArrayOutputStream();
    writeIPDSHeader(baos);
    baos.write(UtilBinaryDecoding.intToByteArray(mappingType, 2));
    if (entries != null) {
      for (IRepeatingGroup rg : entries) {
        LE_RepeatingGroup entry = (LE_RepeatingGroup) rg;
        baos.write(UtilBinaryDecoding.intToByteArray(entry.internal, 2));
        baos.write(UtilBinaryDecoding.intToByteArray(entry.external, 2));
      }
    }
    writeFullStructuredField(os, baos.toByteArray());
  }

  public int getMappingType() {
    return mappingType;
  }

  public void setMappingType(int mappingType) {
    this.mappingType = mappingType;
  }

  @Override
  @JacksonXmlElementWrapper(useWrapping = false)
  @JacksonXmlProperty(localName = "entries")
  public List<IRepeatingGroup> getRepeatingGroups() {
    return entries;
  }

  @Override
  public void setRepeatingGroups(List<IRepeatingGroup> repeatingGroups) {
    this.entries = repeatingGroups;
  }

  @Override
  public void addRepeatingGroup(IRepeatingGroup repeatingGroup) {
    if (entries == null) {
      entries = new ArrayList<>();
    }
    entries.add(repeatingGroup);
  }

  @Override
  public void removeRepeatingGroup(IRepeatingGroup repeatingGroup) {
    if (entries != null) {
      entries.remove(repeatingGroup);
    }
  }

  public static class LE_RepeatingGroup implements IRepeatingGroup {
    @AFPField
    private int internal;
    @AFPField
    private int external;

    public int getInternal() {
      return internal;
    }

    public void setInternal(int internal) {
      this.internal = internal;
    }

    public int getExternal() {
      return external;
    }

    public void setExternal(int external) {
      this.external = external;
    }

    @Override
    public void reset() {
      internal = 0;
      external = 0;
    }

    @Override
    public void decodeAFP(byte[] sfData, int offset, int length, AFPParserConfiguration config)
        throws AFPParserException {
      internal = UtilBinaryDecoding.parseInt(sfData, offset, 2);
      external = UtilBinaryDecoding.parseInt(sfData, offset + 2, 2);
    }

    @Override
    public void writeAFP(OutputStream os, AFPParserConfiguration config) throws IOException {
      os.write(UtilBinaryDecoding.intToByteArray(internal, 2));
      os.write(UtilBinaryDecoding.intToByteArray(external, 2));
    }
  }
}
