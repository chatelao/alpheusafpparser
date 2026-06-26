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
 * IPDS Write Bar Code Control (WBCC) Command (X'D680').
 * Refer to IPDS-9-005.
 */
public class WBCC_WriteBarCodeControl extends IPDSCommand {

  public static class BCAP_BarCodeAreaPosition {
    private int xOffset;
    private int yOffset;
    private AFPOrientation orientation;
    private byte referenceSystem;

    public void decode(byte[] data, int offset, int length) throws AFPParserException {
      if (length < 11) {
        throw new AFPParserException("BCAP SDF too short");
      }
      this.xOffset = UtilBinaryDecoding.parseShort(data, offset + 4, 2);
      this.yOffset = UtilBinaryDecoding.parseShort(data, offset + 6, 2);
      this.orientation = AFPOrientation.valueOf(UtilBinaryDecoding.parseInt(data, offset + 8, 2));
      this.referenceSystem = data[offset + 10];
    }

    public void write(OutputStream os) throws IOException {
      os.write(UtilBinaryDecoding.intToByteArray(11, 2));
      os.write(new byte[] {(byte) 0xAC, 0x6B});
      os.write(UtilBinaryDecoding.shortToByteArray((short) xOffset, 2));
      os.write(UtilBinaryDecoding.shortToByteArray((short) yOffset, 2));
      os.write(orientation != null ? orientation.toBytes() : new byte[] {0, 0});
      os.write(referenceSystem);
    }

    public int getXOffset() { return xOffset; }
    public void setXOffset(int xOffset) { this.xOffset = xOffset; }
    public int getYOffset() { return yOffset; }
    public void setYOffset(int yOffset) { this.yOffset = yOffset; }
    public AFPOrientation getOrientation() { return orientation; }
    public void setOrientation(AFPOrientation orientation) { this.orientation = orientation; }
    public byte getReferenceSystem() { return referenceSystem; }
    public void setReferenceSystem(byte referenceSystem) { this.referenceSystem = referenceSystem; }
  }

  public static class BCOC_BarCodeOutputControl implements IHasTriplets {
    private AFPUnitBase unitBase;
    private int upub;
    private int xoaExtent;
    private int yoaExtent;
    private byte mappingOption;
    private int xoaOffset;
    private int yoaOffset;
    private List<Triplet> triplets;

    public void decode(byte[] data, int offset, int length, AFPParserConfiguration config) throws AFPParserException {
      if (length < 16) {
        throw new AFPParserException("BCOC SDF too short");
      }
      this.unitBase = AFPUnitBase.valueOf(data[offset + 4]);
      this.upub = UtilBinaryDecoding.parseInt(data, offset + 5, 2);
      this.xoaExtent = UtilBinaryDecoding.parseInt(data, offset + 7, 2);
      this.yoaExtent = UtilBinaryDecoding.parseInt(data, offset + 9, 2);
      this.mappingOption = data[offset + 11];
      this.xoaOffset = UtilBinaryDecoding.parseShort(data, offset + 12, 2);
      this.yoaOffset = UtilBinaryDecoding.parseShort(data, offset + 14, 2);

      if (length > 16) {
        this.triplets = com.mgz.afp.parser.TripletParser.parseTriplets(data, offset + 16, length - 16, config);
      }
    }

    public void write(OutputStream os, AFPParserConfiguration config) throws IOException {
      ByteArrayOutputStream baos = new ByteArrayOutputStream();
      baos.write(unitBase != null ? unitBase.toByte() : 0);
      baos.write(UtilBinaryDecoding.intToByteArray(upub, 2));
      baos.write(UtilBinaryDecoding.intToByteArray(xoaExtent, 2));
      baos.write(UtilBinaryDecoding.intToByteArray(yoaExtent, 2));
      baos.write(mappingOption);
      baos.write(UtilBinaryDecoding.shortToByteArray((short) xoaOffset, 2));
      baos.write(UtilBinaryDecoding.shortToByteArray((short) yoaOffset, 2));
      if (triplets != null) {
        for (Triplet t : triplets) {
          t.writeAFP(baos, config);
        }
      }
      byte[] payload = baos.toByteArray();
      os.write(UtilBinaryDecoding.intToByteArray(payload.length + 4, 2));
      os.write(new byte[] {(byte) 0xA6, 0x6B});
      os.write(payload);
    }

    public AFPUnitBase getUnitBase() { return unitBase; }
    public void setUnitBase(AFPUnitBase unitBase) { this.unitBase = unitBase; }
    public int getUpub() { return upub; }
    public void setUpub(int upub) { this.upub = upub; }
    public int getXoaExtent() { return xoaExtent; }
    public void setXoaExtent(int xoaExtent) { this.xoaExtent = xoaExtent; }
    public int getYoaExtent() { return yoaExtent; }
    public void setYoaExtent(int yoaExtent) { this.yoaExtent = yoaExtent; }
    public byte getMappingOption() { return mappingOption; }
    public void setMappingOption(byte mappingOption) { this.mappingOption = mappingOption; }
    public int getXoaOffset() { return xoaOffset; }
    public void setXoaOffset(int xoaOffset) { this.xoaOffset = xoaOffset; }
    public int getYoaOffset() { return yoaOffset; }
    public void setYoaOffset(int yoaOffset) { this.yoaOffset = yoaOffset; }
    @Override public List<Triplet> getTriplets() { return triplets; }
    @Override public void setTriplets(List<Triplet> triplets) { this.triplets = triplets; }
    @Override public void removeTriplet(Triplet triplet) { if (this.triplets != null) this.triplets.remove(triplet); }
    @Override
    public void addTriplet(Triplet triplet) {
      if (this.triplets == null) {
        this.triplets = new ArrayList<>();
      }
      this.triplets.add(triplet);
    }
  }

  public static class BCDD_BarCodeDataDescriptor implements IHasTriplets {
    private AFPUnitBase unitBase;
    private int xupub;
    private int yupub;
    private int xbcExtent;
    private int ybcExtent;
    private int symbolWidth;
    private byte type;
    private byte modifier;
    private int lid;
    private AFPColorValue color;
    private int moduleWidth;
    private int height;
    private int multiplier;
    private int wnRatio;
    private List<Triplet> triplets;

    public void decode(byte[] data, int offset, int length, AFPParserConfiguration config) throws AFPParserException {
      if (length < 27) {
        throw new AFPParserException("BCDD SDF too short");
      }
      this.unitBase = AFPUnitBase.valueOf(data[offset + 4]);
      // byte 5 reserved
      this.xupub = UtilBinaryDecoding.parseInt(data, offset + 6, 2);
      this.yupub = UtilBinaryDecoding.parseInt(data, offset + 8, 2);
      this.xbcExtent = UtilBinaryDecoding.parseInt(data, offset + 10, 2);
      this.ybcExtent = UtilBinaryDecoding.parseInt(data, offset + 12, 2);
      this.symbolWidth = UtilBinaryDecoding.parseInt(data, offset + 14, 2);
      this.type = data[offset + 16];
      this.modifier = data[offset + 17];
      this.lid = data[offset + 18] & 0xFF;
      this.color = AFPColorValue.valueOf(UtilBinaryDecoding.parseInt(data, offset + 19, 2));
      this.moduleWidth = data[offset + 21] & 0xFF;
      this.height = UtilBinaryDecoding.parseInt(data, offset + 22, 2);
      this.multiplier = data[offset + 24] & 0xFF;
      this.wnRatio = UtilBinaryDecoding.parseInt(data, offset + 25, 2);

      if (length > 27) {
        this.triplets = com.mgz.afp.parser.TripletParser.parseTriplets(data, offset + 27, length - 27, config);
      }
    }

    public void write(OutputStream os, AFPParserConfiguration config) throws IOException {
      ByteArrayOutputStream baos = new ByteArrayOutputStream();
      baos.write(unitBase != null ? unitBase.toByte() : 0);
      baos.write(0); // reserved byte 5
      baos.write(UtilBinaryDecoding.intToByteArray(xupub, 2));
      baos.write(UtilBinaryDecoding.intToByteArray(yupub, 2));
      baos.write(UtilBinaryDecoding.intToByteArray(xbcExtent, 2));
      baos.write(UtilBinaryDecoding.intToByteArray(ybcExtent, 2));
      baos.write(UtilBinaryDecoding.intToByteArray(symbolWidth, 2));
      baos.write(type);
      baos.write(modifier);
      baos.write(lid);
      baos.write(color != null ? color.toByte2() : new byte[] {(byte) 0xFF, (byte) 0xFF});
      baos.write(moduleWidth);
      baos.write(UtilBinaryDecoding.intToByteArray(height, 2));
      baos.write(multiplier);
      baos.write(UtilBinaryDecoding.intToByteArray(wnRatio, 2));
      if (triplets != null) {
        for (Triplet t : triplets) {
          t.writeAFP(baos, config);
        }
      }
      byte[] payload = baos.toByteArray();
      os.write(UtilBinaryDecoding.intToByteArray(payload.length + 4, 2));
      os.write(new byte[] {(byte) 0xA6, (byte) 0xEB});
      os.write(payload);
    }

    public AFPUnitBase getUnitBase() { return unitBase; }
    public void setUnitBase(AFPUnitBase unitBase) { this.unitBase = unitBase; }
    public int getXupub() { return xupub; }
    public void setXupub(int xupub) { this.xupub = xupub; }
    public int getYupub() { return yupub; }
    public void setYupub(int yupub) { this.yupub = yupub; }
    public int getXbcExtent() { return xbcExtent; }
    public void setXbcExtent(int xbcExtent) { this.xbcExtent = xbcExtent; }
    public int getYbcExtent() { return ybcExtent; }
    public void setYbcExtent(int ybcExtent) { this.ybcExtent = ybcExtent; }
    public int getSymbolWidth() { return symbolWidth; }
    public void setSymbolWidth(int symbolWidth) { this.symbolWidth = symbolWidth; }
    public byte getType() { return type; }
    public void setType(byte type) { this.type = type; }
    public byte getModifier() { return modifier; }
    public void setModifier(byte modifier) { this.modifier = modifier; }
    public int getLid() { return lid; }
    public void setLid(int lid) { this.lid = lid; }
    public AFPColorValue getColor() { return color; }
    public void setColor(AFPColorValue color) { this.color = color; }
    public int getModuleWidth() { return moduleWidth; }
    public void setModuleWidth(int moduleWidth) { this.moduleWidth = moduleWidth; }
    public int getHeight() { return height; }
    public void setHeight(int height) { this.height = height; }
    public int getMultiplier() { return multiplier; }
    public void setMultiplier(int multiplier) { this.multiplier = multiplier; }
    public int getWnRatio() { return wnRatio; }
    public void setWnRatio(int wnRatio) { this.wnRatio = wnRatio; }
    @Override public List<Triplet> getTriplets() { return triplets; }
    @Override public void setTriplets(List<Triplet> triplets) { this.triplets = triplets; }
    @Override public void removeTriplet(Triplet triplet) { if (this.triplets != null) this.triplets.remove(triplet); }
    @Override
    public void addTriplet(Triplet triplet) {
      if (this.triplets == null) {
        this.triplets = new ArrayList<>();
      }
      this.triplets.add(triplet);
    }
  }

  @AFPField
  private BCAP_BarCodeAreaPosition bcap;

  @AFPField(isOptional = true)
  private BCOC_BarCodeOutputControl bcoc;

  @AFPField
  private BCDD_BarCodeDataDescriptor bcdd;

  @Override
  public void decodeAFP(byte[] sfData, int offset, int length, AFPParserConfiguration config) throws AFPParserException {
    int actualLength = getActualLength(sfData, offset, length);
    int consumed = decodeIPDSHeader(sfData, offset, actualLength);

    int pos = offset + consumed;
    while (pos < offset + actualLength) {
      int sdfLen = UtilBinaryDecoding.parseInt(sfData, pos, 2);
      if (sdfLen < 4) {
        throw new AFPParserException("WBCC Self-Defining Field length must be at least 4");
      }
      if (pos + sdfLen > offset + actualLength) {
        throw new AFPParserException("WBCC Self-Defining Field exceeds structured field boundaries");
      }
      int sdfId = UtilBinaryDecoding.parseInt(sfData, pos + 2, 2);

      switch (sdfId & 0xFFFF) {
        case 0xAC6B:
          this.bcap = new BCAP_BarCodeAreaPosition();
          this.bcap.decode(sfData, pos, sdfLen);
          break;
        case 0xA66B:
          this.bcoc = new BCOC_BarCodeOutputControl();
          this.bcoc.decode(sfData, pos, sdfLen, config);
          break;
        case 0xA6EB:
          this.bcdd = new BCDD_BarCodeDataDescriptor();
          this.bcdd.decode(sfData, pos, sdfLen, config);
          break;
        default:
          // Ignore unknown SDFs
          break;
      }
      pos += sdfLen;
    }
  }

  @Override
  public void writeAFP(OutputStream os, AFPParserConfiguration config) throws IOException {
    ByteArrayOutputStream baos = new ByteArrayOutputStream();
    writeIPDSHeader(baos);
    if (bcap != null) bcap.write(baos);
    if (bcoc != null) bcoc.write(baos, config);
    if (bcdd != null) bcdd.write(baos, config);
    writeFullStructuredField(os, baos.toByteArray());
  }

  public BCAP_BarCodeAreaPosition getBcap() { return bcap; }
  public void setBcap(BCAP_BarCodeAreaPosition bcap) { this.bcap = bcap; }
  public BCOC_BarCodeOutputControl getBcoc() { return bcoc; }
  public void setBcoc(BCOC_BarCodeOutputControl bcoc) { this.bcoc = bcoc; }
  public BCDD_BarCodeDataDescriptor getBcdd() { return bcdd; }
  public void setBcdd(BCDD_BarCodeDataDescriptor bcdd) { this.bcdd = bcdd; }
}
