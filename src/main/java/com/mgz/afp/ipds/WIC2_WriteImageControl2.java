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
 * IPDS Write Image Control 2 (WIC2) Command (X'D63E').
 * Refer to IPDS-7-031.
 */
public class WIC2_WriteImageControl2 extends IPDSCommand {

  public static class IAP_ImageAreaPosition {
    private int xOffset;
    private int yOffset;
    private AFPOrientation orientation;
    private byte coordinateSystem;

    public void decode(byte[] data, int offset, int length) throws AFPParserException {
      if (length < 11) {
        throw new AFPParserException("IAP SDF too short");
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

  public static class IOC_ImageOutputControl implements IHasTriplets {
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
        throw new AFPParserException("IOC SDF too short");
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

  public static class IDD_ImageDataDescriptor {
    private int haid;
    private AFPUnitBase unitBase;
    private int xioResolution;
    private int yioResolution;
    private int xioExtent;
    private int yioExtent;
    private byte[] iocaSdfs;

    public void decode(byte[] data, int offset, int length) throws AFPParserException {
      if (length < 15) {
        throw new AFPParserException("IDD SDF too short");
      }
      this.haid = UtilBinaryDecoding.parseInt(data, offset + 4, 2);
      this.unitBase = AFPUnitBase.valueOf(data[offset + 6]);
      this.xioResolution = UtilBinaryDecoding.parseInt(data, offset + 7, 2);
      this.yioResolution = UtilBinaryDecoding.parseInt(data, offset + 9, 2);
      this.xioExtent = UtilBinaryDecoding.parseInt(data, offset + 11, 2);
      this.yioExtent = UtilBinaryDecoding.parseInt(data, offset + 13, 2);

      if (length > 15) {
        this.iocaSdfs = new byte[length - 15];
        System.arraycopy(data, offset + 15, this.iocaSdfs, 0, length - 15);
      }
    }

    public void write(OutputStream os) throws IOException {
      int len = 15 + (iocaSdfs != null ? iocaSdfs.length : 0);
      os.write(UtilBinaryDecoding.intToByteArray(len, 2));
      os.write(new byte[] {(byte) 0xA6, (byte) 0xFB});
      os.write(UtilBinaryDecoding.intToByteArray(haid, 2));
      os.write(unitBase != null ? unitBase.toByte() : 0);
      os.write(UtilBinaryDecoding.intToByteArray(xioResolution, 2));
      os.write(UtilBinaryDecoding.intToByteArray(yioResolution, 2));
      os.write(UtilBinaryDecoding.intToByteArray(xioExtent, 2));
      os.write(UtilBinaryDecoding.intToByteArray(yioExtent, 2));
      if (iocaSdfs != null) {
        os.write(iocaSdfs);
      }
    }

    public int getHaid() { return haid; }
    public void setHaid(int haid) { this.haid = haid; }
    public AFPUnitBase getUnitBase() { return unitBase; }
    public void setUnitBase(AFPUnitBase unitBase) { this.unitBase = unitBase; }
    public int getXioResolution() { return xioResolution; }
    public void setXioResolution(int xioResolution) { this.xioResolution = xioResolution; }
    public int getYioResolution() { return yioResolution; }
    public void setYioResolution(int yioResolution) { this.yioResolution = yioResolution; }
    public int getXioExtent() { return xioExtent; }
    public void setXioExtent(int xioExtent) { this.xioExtent = xioExtent; }
    public int getYioExtent() { return yioExtent; }
    public void setYioExtent(int yioExtent) { this.yioExtent = yioExtent; }
    public byte[] getIocaSdfs() { return iocaSdfs; }
    public void setIocaSdfs(byte[] iocaSdfs) { this.iocaSdfs = iocaSdfs; }
  }

  @AFPField
  private IAP_ImageAreaPosition iap;

  @AFPField(isOptional = true)
  private IOC_ImageOutputControl ioc;

  @AFPField
  private IDD_ImageDataDescriptor idd;

  @Override
  public void decodeAFP(byte[] sfData, int offset, int length, AFPParserConfiguration config) throws AFPParserException {
    int actualLength = getActualLength(sfData, offset, length);
    int consumed = decodeIPDSHeader(sfData, offset, actualLength);

    int pos = offset + consumed;
    while (pos < offset + actualLength) {
      int sdfLen = UtilBinaryDecoding.parseInt(sfData, pos, 2);
      int sdfId = UtilBinaryDecoding.parseInt(sfData, pos + 2, 2);

      switch (sdfId & 0xFFFF) {
        case 0xAC6B:
          this.iap = new IAP_ImageAreaPosition();
          this.iap.decode(sfData, pos, sdfLen);
          break;
        case 0xA66B:
          this.ioc = new IOC_ImageOutputControl();
          this.ioc.decode(sfData, pos, sdfLen, config);
          break;
        case 0xA6FB:
          this.idd = new IDD_ImageDataDescriptor();
          this.idd.decode(sfData, pos, sdfLen);
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
    if (iap != null) iap.write(baos);
    if (ioc != null) ioc.write(baos, config);
    if (idd != null) idd.write(baos);
    writeFullStructuredField(os, baos.toByteArray());
  }

  public IAP_ImageAreaPosition getIap() { return iap; }
  public void setIap(IAP_ImageAreaPosition iap) { this.iap = iap; }
  public IOC_ImageOutputControl getIoc() { return ioc; }
  public void setIoc(IOC_ImageOutputControl ioc) { this.ioc = ioc; }
  public IDD_ImageDataDescriptor getIdd() { return idd; }
  public void setIdd(IDD_ImageDataDescriptor idd) { this.idd = idd; }
}
