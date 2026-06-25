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
import com.mgz.afp.enums.AFPColorValue;
import com.mgz.afp.exceptions.AFPParserException;
import com.mgz.afp.parser.AFPParserConfiguration;
import com.mgz.util.UtilBinaryDecoding;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;

/**
 * IPDS Write Image Control (WIC) Command (X'D63D').
 * Refer to IPDS-6-028.
 */
public class WIC_WriteImageControl extends IPDSCommand {

  @AFPField
  private int ppslOutput;

  @AFPField
  private int nslOutput;

  @AFPField
  private int ppslInput;

  @AFPField
  private int nslInput;

  @AFPField
  private byte compress;

  @AFPField
  private byte bitsPerPel;

  @AFPField
  private byte pelMag;

  @AFPField
  private byte scanLineMag;

  @AFPField
  private int slDirection;

  @AFPField
  private int slsDirection;

  @AFPField
  private byte rcs;

  @AFPField
  private int xOffset;

  @AFPField
  private int yOffset;

  @AFPField(isOptional = true)
  private AFPColorValue color;

  @Override
  public void decodeAFP(byte[] sfData, int offset, int length, AFPParserConfiguration config) throws AFPParserException {
    int actualLength = getActualLength(sfData, offset, length);
    int consumed = decodeIPDSHeader(sfData, offset, actualLength);

    int remaining = actualLength - consumed;
    checkDataLength(sfData, offset + consumed, remaining, 24);
    int start = offset + consumed;

    this.ppslOutput = UtilBinaryDecoding.parseInt(sfData, start, 2);
    this.nslOutput = UtilBinaryDecoding.parseInt(sfData, start + 2, 2);
    this.ppslInput = UtilBinaryDecoding.parseInt(sfData, start + 4, 2);
    this.nslInput = UtilBinaryDecoding.parseInt(sfData, start + 6, 2);
    this.compress = sfData[start + 8];
    this.bitsPerPel = sfData[start + 9];
    this.pelMag = sfData[start + 10];
    this.scanLineMag = sfData[start + 11];
    this.slDirection = UtilBinaryDecoding.parseInt(sfData, start + 12, 2);
    this.slsDirection = UtilBinaryDecoding.parseInt(sfData, start + 14, 2);
    this.rcs = sfData[start + 16];
    this.xOffset = UtilBinaryDecoding.parseInt(sfData, start + 17, 3);
    // Byte 20 is reserved
    this.yOffset = UtilBinaryDecoding.parseInt(sfData, start + 21, 3);

    if (remaining >= 26) {
      this.color = AFPColorValue.valueOf(UtilBinaryDecoding.parseInt(sfData, start + 24, 2));
    }
  }

  @Override
  public void writeAFP(OutputStream os, AFPParserConfiguration config) throws IOException {
    ByteArrayOutputStream baos = new ByteArrayOutputStream();
    writeIPDSHeader(baos);
    baos.write(UtilBinaryDecoding.intToByteArray(ppslOutput, 2));
    baos.write(UtilBinaryDecoding.intToByteArray(nslOutput, 2));
    baos.write(UtilBinaryDecoding.intToByteArray(ppslInput, 2));
    baos.write(UtilBinaryDecoding.intToByteArray(nslInput, 2));
    baos.write(compress);
    baos.write(bitsPerPel);
    baos.write(pelMag);
    baos.write(scanLineMag);
    baos.write(UtilBinaryDecoding.intToByteArray(slDirection, 2));
    baos.write(UtilBinaryDecoding.intToByteArray(slsDirection, 2));
    baos.write(rcs);
    baos.write(UtilBinaryDecoding.intToByteArray(xOffset, 3));
    baos.write(0x00); // Reserved byte 20
    baos.write(UtilBinaryDecoding.intToByteArray(yOffset, 3));
    if (color != null) {
      baos.write(color.toByte2());
    }
    writeFullStructuredField(os, baos.toByteArray());
  }

  public int getPpslOutput() {
    return ppslOutput;
  }

  public void setPpslOutput(int ppslOutput) {
    this.ppslOutput = ppslOutput;
  }

  public int getNslOutput() {
    return nslOutput;
  }

  public void setNslOutput(int nslOutput) {
    this.nslOutput = nslOutput;
  }

  public int getPpslInput() {
    return ppslInput;
  }

  public void setPpslInput(int ppslInput) {
    this.ppslInput = ppslInput;
  }

  public int getNslInput() {
    return nslInput;
  }

  public void setNslInput(int nslInput) {
    this.nslInput = nslInput;
  }

  public byte getCompress() {
    return compress;
  }

  public void setCompress(byte compress) {
    this.compress = compress;
  }

  public byte getBitsPerPel() {
    return bitsPerPel;
  }

  public void setBitsPerPel(byte bitsPerPel) {
    this.bitsPerPel = bitsPerPel;
  }

  public byte getPelMag() {
    return pelMag;
  }

  public void setPelMag(byte pelMag) {
    this.pelMag = pelMag;
  }

  public byte getScanLineMag() {
    return scanLineMag;
  }

  public void setScanLineMag(byte scanLineMag) {
    this.scanLineMag = scanLineMag;
  }

  public int getSlDirection() {
    return slDirection;
  }

  public void setSlDirection(int slDirection) {
    this.slDirection = slDirection;
  }

  public int getSlsDirection() {
    return slsDirection;
  }

  public void setSlsDirection(int slsDirection) {
    this.slsDirection = slsDirection;
  }

  public byte getRcs() {
    return rcs;
  }

  public void setRcs(byte rcs) {
    this.rcs = rcs;
  }

  public int getXOffset() {
    return xOffset;
  }

  public void setXOffset(int xOffset) {
    this.xOffset = xOffset;
  }

  public int getYOffset() {
    return yOffset;
  }

  public void setYOffset(int yOffset) {
    this.yOffset = yOffset;
  }

  public AFPColorValue getColor() {
    return color;
  }

  public void setColor(AFPColorValue color) {
    this.color = color;
  }
}
