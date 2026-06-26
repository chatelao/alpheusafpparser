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
 * IPDS Write Object Container Control (WOCC) Command (X'D63C').
 * Refer to IPDS-10-194.
 */
public class WOCC_WriteObjectContainerControl extends IPDSCommand {

  public static class OCAP_ObjectContainerAreaPosition {
    private int xOffset;
    private int yOffset;
    private AFPOrientation orientation;
    private byte coordinateSystem;

    public void decode(byte[] data, int offset, int length) throws AFPParserException {
      if (length < 11) {
        throw new AFPParserException("OCAP SDF too short");
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

  public static class OCOC_ObjectContainerOutputControl implements IHasTriplets {
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
        throw new AFPParserException("OCOC SDF too short");
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

  public static class OCDD_ObjectContainerDataDescriptor implements IHasTriplets {
    private byte[] objectTypeOid;
    private int haid;
    private List<Triplet> triplets;

    public void decode(byte[] data, int offset, int length, AFPParserConfiguration config) throws AFPParserException {
      if (length < 22) {
        throw new AFPParserException("OCDD SDF too short");
      }
      this.objectTypeOid = new byte[16];
      System.arraycopy(data, offset + 4, this.objectTypeOid, 0, 16);
      this.haid = UtilBinaryDecoding.parseInt(data, offset + 20, 2);

      if (length > 22) {
        this.triplets = com.mgz.afp.parser.TripletParser.parseTriplets(data, offset + 22, length - 22, config);
      }
    }

    public void write(OutputStream os, AFPParserConfiguration config) throws IOException {
      ByteArrayOutputStream baos = new ByteArrayOutputStream();
      baos.write(objectTypeOid != null ? objectTypeOid : new byte[16]);
      baos.write(UtilBinaryDecoding.intToByteArray(haid, 2));
      if (triplets != null) {
        for (Triplet t : triplets) {
          t.writeAFP(baos, config);
        }
      }
      byte[] payload = baos.toByteArray();
      os.write(UtilBinaryDecoding.intToByteArray(payload.length + 4, 2));
      os.write(new byte[] {(byte) 0xA6, (byte) 0x92});
      os.write(payload);
    }

    public byte[] getObjectTypeOid() { return objectTypeOid; }
    public void setObjectTypeOid(byte[] objectTypeOid) { this.objectTypeOid = objectTypeOid; }
    public int getHaid() { return haid; }
    public void setHaid(int haid) { this.haid = haid; }
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

  @AFPField(isOptional = true)
  private OCAP_ObjectContainerAreaPosition ocap;

  @AFPField(isOptional = true)
  private OCOC_ObjectContainerOutputControl ococ;

  @AFPField
  private OCDD_ObjectContainerDataDescriptor ocdd;

  @Override
  public void decodeAFP(byte[] sfData, int offset, int length, AFPParserConfiguration config) throws AFPParserException {
    int actualLength = getActualLength(sfData, offset, length);
    int consumed = decodeIPDSHeader(sfData, offset, actualLength);

    int pos = offset + consumed;
    while (pos < offset + actualLength) {
      int sdfLen = UtilBinaryDecoding.parseInt(sfData, pos, 2);
      if (sdfLen < 4) {
        throw new AFPParserException("WOCC Self-Defining Field length must be at least 4");
      }
      if (pos + sdfLen > offset + actualLength) {
        throw new AFPParserException("WOCC Self-Defining Field exceeds structured field boundaries");
      }
      int sdfId = UtilBinaryDecoding.parseInt(sfData, pos + 2, 2);

      switch (sdfId & 0xFFFF) {
        case 0xAC6B:
          this.ocap = new OCAP_ObjectContainerAreaPosition();
          this.ocap.decode(sfData, pos, sdfLen);
          break;
        case 0xA66B:
          this.ococ = new OCOC_ObjectContainerOutputControl();
          this.ococ.decode(sfData, pos, sdfLen, config);
          break;
        case 0xA692:
          this.ocdd = new OCDD_ObjectContainerDataDescriptor();
          this.ocdd.decode(sfData, pos, sdfLen, config);
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
    if (ocap != null) ocap.write(baos);
    if (ococ != null) ococ.write(baos, config);
    if (ocdd != null) ocdd.write(baos, config);
    writeFullStructuredField(os, baos.toByteArray());
  }

  public OCAP_ObjectContainerAreaPosition getOcap() { return ocap; }
  public void setOcap(OCAP_ObjectContainerAreaPosition ocap) { this.ocap = ocap; }
  public OCOC_ObjectContainerOutputControl getOcoc() { return ococ; }
  public void setOcoc(OCOC_ObjectContainerOutputControl ococ) { this.ococ = ococ; }
  public OCDD_ObjectContainerDataDescriptor getOcdd() { return ocdd; }
  public void setOcdd(OCDD_ObjectContainerDataDescriptor ocdd) { this.ocdd = ocdd; }
}
