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
 * IPDS Load Symbol Set (LSS) Command (X'D61E').
 */
public class LSS_LoadSymbolSet extends IPDSCommand {

  @AFPField
  private byte flags1;
  @AFPField
  private int scode;
  @AFPField
  private int additionalLength;
  @AFPField
  private byte flags2;
  @AFPField
  private int xBoxSize;
  @AFPField
  private int yBoxSize;
  @AFPField
  private short sectionId;
  @AFPField
  private int ecode;
  @AFPField
  private int haid;
  @AFPField
  private byte[] restOfHeader;
  @AFPField
  private byte[] rasterData;

  @Override
  public void decodeAFP(byte[] sfData, int offset, int length, AFPParserConfiguration config)
      throws AFPParserException {
    int actualLength = getActualLength(sfData, offset, length);
    int consumed = decodeIPDSHeader(sfData, offset, actualLength);
    int current = offset + consumed;
    int remaining = actualLength - consumed;

    if (remaining >= 17) {
      this.flags1 = sfData[current];
      // byte 1 retired
      this.scode = sfData[current + 2] & 0xFF;
      // byte 3 retired
      this.additionalLength = sfData[current + 4] & 0xFF;
      this.flags2 = sfData[current + 5];
      this.xBoxSize = sfData[current + 6] & 0xFF;
      this.yBoxSize = sfData[current + 7] & 0xFF;
      this.sectionId = (short) (sfData[current + 8] & 0xFF);
      // bytes 9-10 retired
      this.ecode = sfData[current + 11] & 0xFF;
      // bytes 12-14 retired
      this.haid = UtilBinaryDecoding.parseInt(sfData, current + 15, 2);

      int headerSize = Math.max(17, additionalLength + 4);
      if (headerSize > 17 && remaining >= headerSize) {
        this.restOfHeader = new byte[headerSize - 17];
        System.arraycopy(sfData, current + 17, this.restOfHeader, 0, headerSize - 17);
      }

      int rasterOffset = headerSize;
      if (remaining > rasterOffset) {
        this.rasterData = new byte[remaining - rasterOffset];
        System.arraycopy(sfData, current + rasterOffset, this.rasterData, 0, remaining - rasterOffset);
      }
    }
  }

  @Override
  public void writeAFP(OutputStream os, AFPParserConfiguration config) throws IOException {
    ByteArrayOutputStream baos = new ByteArrayOutputStream();
    writeIPDSHeader(baos);
    baos.write(flags1);
    baos.write(0); // Retired
    baos.write(scode);
    baos.write(0); // Retired
    baos.write(additionalLength);
    baos.write(flags2);
    baos.write(xBoxSize);
    baos.write(yBoxSize);
    baos.write(sectionId & 0xFF);
    baos.write(new byte[] {0, 0}); // Retired
    baos.write(ecode);
    baos.write(new byte[] {0, 0, 0}); // Retired
    baos.write(UtilBinaryDecoding.intToByteArray(haid, 2));

    if (restOfHeader != null) {
      baos.write(restOfHeader);
    }
    if (rasterData != null) {
      baos.write(rasterData);
    }

    writeFullStructuredField(os, baos.toByteArray());
  }

  public byte getFlags1() {
    return flags1;
  }

  public void setFlags1(byte flags1) {
    this.flags1 = flags1;
  }

  public int getScode() {
    return scode;
  }

  public void setScode(int scode) {
    this.scode = scode;
  }

  public int getAdditionalLength() {
    return additionalLength;
  }

  public void setAdditionalLength(int additionalLength) {
    this.additionalLength = additionalLength;
  }

  public byte getFlags2() {
    return flags2;
  }

  public void setFlags2(byte flags2) {
    this.flags2 = flags2;
  }

  public int getxBoxSize() {
    return xBoxSize;
  }

  public void setxBoxSize(int xBoxSize) {
    this.xBoxSize = xBoxSize;
  }

  public int getyBoxSize() {
    return yBoxSize;
  }

  public void setyBoxSize(int yBoxSize) {
    this.yBoxSize = yBoxSize;
  }

  public short getSectionId() {
    return sectionId;
  }

  public void setSectionId(short sectionId) {
    this.sectionId = sectionId;
  }

  public int getEcode() {
    return ecode;
  }

  public void setEcode(int ecode) {
    this.ecode = ecode;
  }

  public int getHaid() {
    return haid;
  }

  public void setHaid(int haid) {
    this.haid = haid;
  }

  public byte[] getRestOfHeader() {
    return restOfHeader;
  }

  public void setRestOfHeader(byte[] restOfHeader) {
    this.restOfHeader = restOfHeader;
  }

  public byte[] getRasterData() {
    return rasterData;
  }

  public void setRasterData(byte[] rasterData) {
    this.rasterData = rasterData;
  }
}
