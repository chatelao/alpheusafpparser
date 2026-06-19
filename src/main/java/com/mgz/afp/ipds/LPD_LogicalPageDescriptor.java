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

import com.mgz.afp.base.IHasTriplets;
import com.mgz.afp.base.annotations.AFPField;
import com.mgz.afp.enums.AFPColorValue;
import com.mgz.afp.enums.AFPOrientation;
import com.mgz.afp.enums.AFPUnitBase;
import com.mgz.afp.exceptions.AFPParserException;
import com.mgz.afp.parser.AFPParserConfiguration;
import com.mgz.afp.triplets.Triplet;
import com.mgz.util.UtilBinaryDecoding;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * IPDS Logical Page Descriptor (LPD) Command (X'D6CF').
 * Refer to IPDS-4-014, IPDS-4-506.
 */
public class LPD_LogicalPageDescriptor extends IPDSCommand implements IHasTriplets {

  @Override
  public void removeTriplet(Triplet triplet) {
    if (this.triplets != null) {
      this.triplets.remove(triplet);
    }
  }

  @AFPField
  private AFPUnitBase unitBase;

  @AFPField
  private int xupub;

  @AFPField
  private int yupub;

  @AFPField
  private int xpExtent;

  @AFPField
  private int ypExtent;

  @AFPField
  private byte orderedDataFlags;

  @AFPField
  private AFPOrientation iAxisOrientation;

  @AFPField
  private AFPOrientation bAxisOrientation;

  @AFPField
  private int initialI;

  @AFPField
  private int initialB;

  @AFPField
  private int inlineMargin;

  @AFPField
  private int intercharAdjustment;

  @AFPField
  private int baselineIncrement;

  @AFPField
  private short lid;

  @AFPField
  private AFPColorValue color;

  @AFPField(isOptional = true)
  private List<Triplet> triplets;

  @Override
  public void decodeAFP(byte[] sfData, int offset, int length, AFPParserConfiguration config) throws AFPParserException {
    int actualLength = getActualLength(sfData, offset, length);
    int consumed = decodeIPDSHeader(sfData, offset, actualLength);

    checkDataLength(sfData, offset + consumed, actualLength - consumed, 43);
    int start = offset + consumed;

    this.unitBase = AFPUnitBase.valueOf((byte) (sfData[start] & 0xFF));
    // Byte 1 is reserved
    this.xupub = UtilBinaryDecoding.parseInt(sfData, start + 2, 2);
    this.yupub = UtilBinaryDecoding.parseInt(sfData, start + 4, 2);
    // Byte 6 is reserved
    this.xpExtent = UtilBinaryDecoding.parseInt(sfData, start + 7, 3);
    // Byte 10 is reserved
    this.ypExtent = UtilBinaryDecoding.parseInt(sfData, start + 11, 3);
    // Byte 14 is reserved
    this.orderedDataFlags = sfData[start + 15];
    // Bytes 16-23 are reserved
    this.iAxisOrientation = AFPOrientation.valueOf(UtilBinaryDecoding.parseInt(sfData, start + 24, 2));
    this.bAxisOrientation = AFPOrientation.valueOf(UtilBinaryDecoding.parseInt(sfData, start + 26, 2));
    this.initialI = UtilBinaryDecoding.parseShort(sfData, start + 28, 2);
    this.initialB = UtilBinaryDecoding.parseShort(sfData, start + 30, 2);
    this.inlineMargin = UtilBinaryDecoding.parseInt(sfData, start + 32, 2);
    this.intercharAdjustment = UtilBinaryDecoding.parseInt(sfData, start + 34, 2);
    // Bytes 36-37 are reserved
    this.baselineIncrement = UtilBinaryDecoding.parseInt(sfData, start + 38, 2);
    this.lid = (short) (sfData[start + 40] & 0xFF);
    this.color = AFPColorValue.valueOf(UtilBinaryDecoding.parseInt(sfData, start + 41, 2));

    if (actualLength - consumed > 43) {
      this.triplets = com.mgz.afp.parser.TripletParser.parseTriplets(sfData, start + 43, actualLength - consumed - 43, config);
    }
  }

  @Override
  public void writeAFP(OutputStream os, AFPParserConfiguration config) throws IOException {
    ByteArrayOutputStream baos = new ByteArrayOutputStream();
    writeIPDSHeader(baos);
    baos.write(unitBase != null ? unitBase.toByte() : 0);
    baos.write(0x00);
    baos.write(UtilBinaryDecoding.intToByteArray(xupub, 2));
    baos.write(UtilBinaryDecoding.intToByteArray(yupub, 2));
    baos.write(0x00);
    baos.write(UtilBinaryDecoding.intToByteArray(xpExtent, 3));
    baos.write(0x00);
    baos.write(UtilBinaryDecoding.intToByteArray(ypExtent, 3));
    baos.write(0x00);
    baos.write(orderedDataFlags);
    baos.write(new byte[8]); // Reserved 16-23
    baos.write(iAxisOrientation != null ? iAxisOrientation.toBytes() : new byte[] {0, 0});
    baos.write(bAxisOrientation != null ? bAxisOrientation.toBytes() : new byte[] {0, 0});
    baos.write(UtilBinaryDecoding.intToByteArray(initialI, 2));
    baos.write(UtilBinaryDecoding.intToByteArray(initialB, 2));
    baos.write(UtilBinaryDecoding.intToByteArray(inlineMargin, 2));
    baos.write(UtilBinaryDecoding.intToByteArray(intercharAdjustment, 2));
    baos.write(new byte[2]); // Reserved 36-37
    baos.write(UtilBinaryDecoding.intToByteArray(baselineIncrement, 2));
    baos.write(lid & 0xFF);
    baos.write(color != null ? color.toByte2() : new byte[] {(byte) 0xFF, 0x07});

    if (triplets != null) {
      for (Triplet t : triplets) {
        t.writeAFP(baos, config);
      }
    }
    writeFullStructuredField(os, baos.toByteArray());
  }

  public AFPUnitBase getUnitBase() {
    return unitBase;
  }

  public void setUnitBase(AFPUnitBase unitBase) {
    this.unitBase = unitBase;
  }

  public int getXupub() {
    return xupub;
  }

  public void setXupub(int xupub) {
    this.xupub = xupub;
  }

  public int getYupub() {
    return yupub;
  }

  public void setYupub(int yupub) {
    this.yupub = yupub;
  }

  public int getXpExtent() {
    return xpExtent;
  }

  public void setXpExtent(int xpExtent) {
    this.xpExtent = xpExtent;
  }

  public int getYpExtent() {
    return ypExtent;
  }

  public void setYpExtent(int ypExtent) {
    this.ypExtent = ypExtent;
  }

  public byte getOrderedDataFlags() {
    return orderedDataFlags;
  }

  public void setOrderedDataFlags(byte orderedDataFlags) {
    this.orderedDataFlags = orderedDataFlags;
  }

  public AFPOrientation getiAxisOrientation() {
    return iAxisOrientation;
  }

  public void setiAxisOrientation(AFPOrientation iAxisOrientation) {
    this.iAxisOrientation = iAxisOrientation;
  }

  public AFPOrientation getbAxisOrientation() {
    return bAxisOrientation;
  }

  public void setbAxisOrientation(AFPOrientation bAxisOrientation) {
    this.bAxisOrientation = bAxisOrientation;
  }

  public int getInitialI() {
    return initialI;
  }

  public void setInitialI(int initialI) {
    this.initialI = initialI;
  }

  public int getInitialB() {
    return initialB;
  }

  public void setInitialB(int initialB) {
    this.initialB = initialB;
  }

  public int getInlineMargin() {
    return inlineMargin;
  }

  public void setInlineMargin(int inlineMargin) {
    this.inlineMargin = inlineMargin;
  }

  public int getIntercharAdjustment() {
    return intercharAdjustment;
  }

  public void setIntercharAdjustment(int intercharAdjustment) {
    this.intercharAdjustment = intercharAdjustment;
  }

  public int getBaselineIncrement() {
    return baselineIncrement;
  }

  public void setBaselineIncrement(int baselineIncrement) {
    this.baselineIncrement = baselineIncrement;
  }

  public short getLid() {
    return lid;
  }

  public void setLid(short lid) {
    this.lid = lid;
  }

  public AFPColorValue getColor() {
    return color;
  }

  public void setColor(AFPColorValue color) {
    this.color = color;
  }

  @Override
  public List<Triplet> getTriplets() {
    return triplets;
  }

  @Override
  public void setTriplets(List<Triplet> triplets) {
    this.triplets = triplets;
  }

  public void addTriplet(Triplet triplet) {
    if (this.triplets == null) {
      this.triplets = new ArrayList<>();
    }
    this.triplets.add(triplet);
  }
}
