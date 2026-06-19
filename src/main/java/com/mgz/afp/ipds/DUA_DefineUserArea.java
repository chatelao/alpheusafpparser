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

import com.mgz.afp.base.annotations.AFPField;
import com.mgz.afp.exceptions.AFPParserException;
import com.mgz.afp.parser.AFPParserConfiguration;
import com.mgz.util.UtilBinaryDecoding;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;

/**
 * IPDS Define User Area (DUA) Command (X'D6CE').
 * Refer to IPDS-4-007, IPDS-4-303.
 */
public class DUA_DefineUserArea extends IPDSCommand {

  @AFPField
  private byte reset;
  @AFPField
  private byte unitBase;
  @AFPField
  private int upub;
  @AFPField
  private int xmOffset;
  @AFPField
  private int ymOffset;
  @AFPField
  private int xmExtent;
  @AFPField
  private int ymExtent;

  @Override
  public void decodeAFP(byte[] sfData, int offset, int length, AFPParserConfiguration config)
      throws AFPParserException {
    int actualLength = getActualLength(sfData, offset, length);
    int consumed = decodeIPDSHeader(sfData, offset, actualLength);

    checkDataLength(sfData, offset + consumed, actualLength - consumed, 16);
    reset = sfData[offset + consumed];
    unitBase = sfData[offset + consumed + 1];
    upub = UtilBinaryDecoding.parseInt(sfData, offset + consumed + 2, 2);
    xmOffset = UtilBinaryDecoding.parseInt(sfData, offset + consumed + 4, 3);
    ymOffset = UtilBinaryDecoding.parseInt(sfData, offset + consumed + 7, 3);
    xmExtent = UtilBinaryDecoding.parseInt(sfData, offset + consumed + 10, 3);
    ymExtent = UtilBinaryDecoding.parseInt(sfData, offset + consumed + 13, 3);
  }

  @Override
  public void writeAFP(OutputStream os, AFPParserConfiguration config) throws IOException {
    ByteArrayOutputStream baos = new ByteArrayOutputStream();
    writeIPDSHeader(baos);
    baos.write(reset);
    baos.write(unitBase);
    baos.write(UtilBinaryDecoding.intToByteArray(upub, 2));
    baos.write(UtilBinaryDecoding.intToByteArray(xmOffset, 3));
    baos.write(UtilBinaryDecoding.intToByteArray(ymOffset, 3));
    baos.write(UtilBinaryDecoding.intToByteArray(xmExtent, 3));
    baos.write(UtilBinaryDecoding.intToByteArray(ymExtent, 3));
    writeFullStructuredField(os, baos.toByteArray());
  }

  public byte getReset() {
    return reset;
  }

  public void setReset(byte reset) {
    this.reset = reset;
  }

  public byte getUnitBase() {
    return unitBase;
  }

  public void setUnitBase(byte unitBase) {
    this.unitBase = unitBase;
  }

  public int getUpub() {
    return upub;
  }

  public void setUpub(int upub) {
    this.upub = upub;
  }

  public int getXmOffset() {
    return xmOffset;
  }

  public void setXmOffset(int xmOffset) {
    this.xmOffset = xmOffset;
  }

  public int getYmOffset() {
    return ymOffset;
  }

  public void setYmOffset(int ymOffset) {
    this.ymOffset = ymOffset;
  }

  public int getXmExtent() {
    return xmExtent;
  }

  public void setXmExtent(int xmExtent) {
    this.xmExtent = xmExtent;
  }

  public int getYmExtent() {
    return ymExtent;
  }

  public void setYmExtent(int ymExtent) {
    this.ymExtent = ymExtent;
  }
}
