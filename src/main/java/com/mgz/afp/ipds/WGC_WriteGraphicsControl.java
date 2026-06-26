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
 * IPDS Write Graphics Control (WGC) Command (X'D684').
 * Refer to IPDS-8-015.
 */
public class WGC_WriteGraphicsControl extends IPDSCommand {

  public static class GAP_GraphicsAreaPosition {
    private int xOffset;
    private int yOffset;
    private AFPOrientation orientation;
    private byte coordinateSystem;

    public void decode(byte[] data, int offset, int length) throws AFPParserException {
      if (length < 11) {
        throw new AFPParserException("GAP SDF too short");
      }
      this.xOffset = UtilBinaryDecoding.parseShort(data, offset + 4, 2);
      this.yOffset = UtilBinaryDecoding.parseShort(data, offset + 6, 2);
      this.orientation = AFPOrientation.valueOf(UtilBinaryDecoding.parseInt(data, offset + 8, 2));
      this.coordinateSystem = data[offset + 10];
    }

    public void write(OutputStream os) throws IOException {
      os.write(UtilBinaryDecoding.intToByteArray(11, 2));
      os.write(new byte[] {(byte) 0xAC, 0x6B});
      os.write(UtilBinaryDecoding.shortToByteArray((short) xOffset, 2));
      os.write(UtilBinaryDecoding.shortToByteArray((short) yOffset, 2));
      os.write(orientation != null ? orientation.toBytes() : new byte[] {0, 0});
      os.write(coordinateSystem);
    }

    public int getXOffset() { return xOffset; }
    public void setXOffset(int xOffset) { this.xOffset = xOffset; }
    public int getYOffset() { return yOffset; }
    public void setYOffset(int yOffset) { this.yOffset = yOffset; }
    public AFPOrientation getOrientation() { return orientation; }
    public void setOrientation(AFPOrientation orientation) { this.orientation = orientation; }
    public byte getCoordinateSystem() { return coordinateSystem; }
    public void setCoordinateSystem(byte coordinateSystem) { this.coordinateSystem = coordinateSystem; }
  }

  public static class GOC_GraphicsOutputControl implements IHasTriplets {
    private AFPUnitBase unitBase;
    private int upub;
    private int xoaExtent;
    private int yoaExtent;
    private byte mappingControl;
    private int xoaOffset;
    private int yoaOffset;
    private List<Triplet> triplets;

    public void decode(byte[] data, int offset, int length, AFPParserConfiguration config) throws AFPParserException {
      if (length < 16) {
        throw new AFPParserException("GOC SDF too short");
      }
      this.unitBase = AFPUnitBase.valueOf(data[offset + 4]);
      this.upub = UtilBinaryDecoding.parseInt(data, offset + 5, 2);
      this.xoaExtent = UtilBinaryDecoding.parseInt(data, offset + 7, 2);
      this.yoaExtent = UtilBinaryDecoding.parseInt(data, offset + 9, 2);
      this.mappingControl = data[offset + 11];
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
      baos.write(mappingControl);
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
    public byte getMappingControl() { return mappingControl; }
    public void setMappingControl(byte mappingControl) { this.mappingControl = mappingControl; }
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

  public static class GDD_GraphicsDataDescriptor {
    private AFPUnitBase unitBase;
    private int xupub;
    private int yupub;
    private int xires;
    private int yires;
    private int xgLeftLimit;
    private int xgRightLimit;
    private int ygTopLimit;
    private int ygBottomLimit;
    private byte[] defaults;

    public void decode(byte[] data, int offset, int length) throws AFPParserException {
      if (length < 28) {
        throw new AFPParserException("GDD SDF too short");
      }
      this.unitBase = AFPUnitBase.valueOf(data[offset + 4]);
      // byte 5 reserved
      this.xupub = UtilBinaryDecoding.parseInt(data, offset + 6, 2);
      this.yupub = UtilBinaryDecoding.parseInt(data, offset + 8, 2);
      this.xires = UtilBinaryDecoding.parseInt(data, offset + 10, 2);
      this.yires = UtilBinaryDecoding.parseInt(data, offset + 12, 2);
      this.xgLeftLimit = UtilBinaryDecoding.parseShort(data, offset + 14, 2);
      this.xgRightLimit = UtilBinaryDecoding.parseShort(data, offset + 16, 2);
      this.ygTopLimit = UtilBinaryDecoding.parseShort(data, offset + 18, 2);
      this.ygBottomLimit = UtilBinaryDecoding.parseShort(data, offset + 20, 2);
      // bytes 22-27 reserved

      if (length > 28) {
        this.defaults = new byte[length - 28];
        System.arraycopy(data, offset + 28, this.defaults, 0, length - 28);
      }
    }

    public void write(OutputStream os) throws IOException {
      int len = 28 + (defaults != null ? defaults.length : 0);
      os.write(UtilBinaryDecoding.intToByteArray(len, 2));
      os.write(new byte[] {(byte) 0xA6, (byte) 0xBB});
      os.write(unitBase != null ? unitBase.toByte() : 0);
      os.write(0); // reserved byte 5
      os.write(UtilBinaryDecoding.intToByteArray(xupub, 2));
      os.write(UtilBinaryDecoding.intToByteArray(yupub, 2));
      os.write(UtilBinaryDecoding.intToByteArray(xires, 2));
      os.write(UtilBinaryDecoding.intToByteArray(yires, 2));
      os.write(UtilBinaryDecoding.shortToByteArray((short) xgLeftLimit, 2));
      os.write(UtilBinaryDecoding.shortToByteArray((short) xgRightLimit, 2));
      os.write(UtilBinaryDecoding.shortToByteArray((short) ygTopLimit, 2));
      os.write(UtilBinaryDecoding.shortToByteArray((short) ygBottomLimit, 2));
      os.write(new byte[] {0, 0, 0, 0, 0, 0}); // reserved 22-27
      if (defaults != null) {
        os.write(defaults);
      }
    }

    public AFPUnitBase getUnitBase() { return unitBase; }
    public void setUnitBase(AFPUnitBase unitBase) { this.unitBase = unitBase; }
    public int getXupub() { return xupub; }
    public void setXupub(int xupub) { this.xupub = xupub; }
    public int getYupub() { return yupub; }
    public void setYupub(int yupub) { this.yupub = yupub; }
    public int getXires() { return xires; }
    public void setXires(int xires) { this.xires = xires; }
    public int getYires() { return yires; }
    public void setYires(int yires) { this.yires = yires; }
    public int getXgLeftLimit() { return xgLeftLimit; }
    public void setXgLeftLimit(int xgLeftLimit) { this.xgLeftLimit = xgLeftLimit; }
    public int getXgRightLimit() { return xgRightLimit; }
    public void setXgRightLimit(int xgRightLimit) { this.xgRightLimit = xgRightLimit; }
    public int getYgTopLimit() { return ygTopLimit; }
    public void setYgTopLimit(int ygTopLimit) { this.ygTopLimit = ygTopLimit; }
    public int getYgBottomLimit() { return ygBottomLimit; }
    public void setYgBottomLimit(int ygBottomLimit) { this.ygBottomLimit = ygBottomLimit; }
    public byte[] getDefaults() { return defaults; }
    public void setDefaults(byte[] defaults) { this.defaults = defaults; }
  }

  @AFPField
  private GAP_GraphicsAreaPosition gap;

  @AFPField(isOptional = true)
  private GOC_GraphicsOutputControl goc;

  @AFPField
  private GDD_GraphicsDataDescriptor gdd;

  @Override
  public void decodeAFP(byte[] sfData, int offset, int length, AFPParserConfiguration config) throws AFPParserException {
    int actualLength = getActualLength(sfData, offset, length);
    int consumed = decodeIPDSHeader(sfData, offset, actualLength);

    int pos = offset + consumed;
    while (pos < offset + actualLength) {
      int sdfLen = UtilBinaryDecoding.parseInt(sfData, pos, 2);
      if (sdfLen < 4) {
        throw new AFPParserException("WGC Self-Defining Field length must be at least 4");
      }
      if (pos + sdfLen > offset + actualLength) {
        throw new AFPParserException("WGC Self-Defining Field exceeds structured field boundaries");
      }
      int sdfId = UtilBinaryDecoding.parseInt(sfData, pos + 2, 2);

      switch (sdfId & 0xFFFF) {
        case 0xAC6B:
          this.gap = new GAP_GraphicsAreaPosition();
          this.gap.decode(sfData, pos, sdfLen);
          break;
        case 0xA66B:
          this.goc = new GOC_GraphicsOutputControl();
          this.goc.decode(sfData, pos, sdfLen, config);
          break;
        case 0xA6BB:
          this.gdd = new GDD_GraphicsDataDescriptor();
          this.gdd.decode(sfData, pos, sdfLen);
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
    if (gap != null) gap.write(baos);
    if (goc != null) goc.write(baos, config);
    if (gdd != null) gdd.write(baos);
    writeFullStructuredField(os, baos.toByteArray());
  }

  public GAP_GraphicsAreaPosition getGap() { return gap; }
  public void setGap(GAP_GraphicsAreaPosition gap) { this.gap = gap; }
  public GOC_GraphicsOutputControl getGoc() { return goc; }
  public void setGoc(GOC_GraphicsOutputControl goc) { this.goc = goc; }
  public GDD_GraphicsDataDescriptor getGdd() { return gdd; }
  public void setGdd(GDD_GraphicsDataDescriptor gdd) { this.gdd = gdd; }
}
