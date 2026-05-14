/*
Copyright 2015 Rudolf Fiala

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
package com.mgz.afp.foca;

import com.mgz.afp.base.StructuredField;
import com.mgz.afp.base.annotations.AFPField;
import com.mgz.afp.exceptions.AFPParserException;
import com.mgz.afp.parser.AFPParserConfiguration;
import com.mgz.afp.parser.TripletParser;
import com.mgz.afp.triplets.Triplet;
import com.mgz.util.UtilBinaryDecoding;
import com.mgz.util.UtilCharacterEncoding;

import javax.xml.bind.annotation.XmlRootElement;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

/**
 * Font Descriptor (FND).
 */
@XmlRootElement
public class FND_FontDescriptor extends StructuredField {

  @AFPField(size = 32)
  private String typefaceDescription;
  @AFPField
  private byte fontWeightClass;
  @AFPField
  private byte fontWidthClass;
  @AFPField
  private short maxBaselineExtent;
  @AFPField(size = 4)
  private byte[] reserved36_39 = new byte[4];
  @AFPField
  private short fontClassificationFlags;
  @AFPField
  private byte designRes;
  @AFPField(size = 2)
  private byte[] reserved43_44 = new byte[2];
  @AFPField
  private short capMHeight;
  @AFPField
  private short lowercaseHeight;
  @AFPField
  private short lowercaseAscenderHeight;
  @AFPField
  private short lowercaseDescenderDepth;
  @AFPField(size = 20)
  private byte[] reserved53_72 = new byte[20];
  @AFPField
  private short gcsgid;
  @AFPField
  private short fgid;
  @AFPField(size = 4)
  private byte[] reserved77_80 = new byte[4];
  @AFPField
  private byte reserved81 = 0x00;
  @AFPField
  private List<Triplet> triplets;

  @Override
  public void decodeAFP(byte[] sfData, int offset, int length, AFPParserConfiguration config) throws AFPParserException {
    var actualLength = getActualLength(sfData, offset, length);
    checkDataLength(sfData, offset, length, 81);

    typefaceDescription = new String(sfData, offset, 32, config.getAfpCharSet()).trim();
    fontWeightClass = sfData[offset + 32];
    fontWidthClass = sfData[offset + 33];
    maxBaselineExtent = UtilBinaryDecoding.parseShort(sfData, offset + 34, 2);
    System.arraycopy(sfData, offset + 36, reserved36_39, 0, 4);
    fontClassificationFlags = UtilBinaryDecoding.parseShort(sfData, offset + 40, 2);
    designRes = sfData[offset + 42];
    System.arraycopy(sfData, offset + 43, reserved43_44, 0, 2);
    capMHeight = UtilBinaryDecoding.parseShort(sfData, offset + 45, 2);
    lowercaseHeight = UtilBinaryDecoding.parseShort(sfData, offset + 47, 2);
    lowercaseAscenderHeight = UtilBinaryDecoding.parseShort(sfData, offset + 49, 2);
    lowercaseDescenderDepth = UtilBinaryDecoding.parseShort(sfData, offset + 51, 2);
    System.arraycopy(sfData, offset + 53, reserved53_72, 0, 20);
    gcsgid = UtilBinaryDecoding.parseShort(sfData, offset + 73, 2);
    fgid = UtilBinaryDecoding.parseShort(sfData, offset + 75, 2);
    System.arraycopy(sfData, offset + 77, reserved77_80, 0, 4);
    reserved81 = sfData[offset + 80];

    if (actualLength > 81) {
      triplets = TripletParser.parseTriplets(sfData, offset + 81, actualLength - 81, config);
    }
  }

  @Override
  public void writeAFP(OutputStream os, AFPParserConfiguration config) throws IOException {
    var baos = new ByteArrayOutputStream();
    baos.write(UtilCharacterEncoding.stringToByteArray(typefaceDescription, config.getAfpCharSet(), 32, (byte) 0x40));
    baos.write(fontWeightClass);
    baos.write(fontWidthClass);
    baos.write(UtilBinaryDecoding.shortToByteArray(maxBaselineExtent, 2));
    baos.write(reserved36_39);
    baos.write(UtilBinaryDecoding.shortToByteArray(fontClassificationFlags, 2));
    baos.write(designRes);
    baos.write(reserved43_44);
    baos.write(UtilBinaryDecoding.shortToByteArray(capMHeight, 2));
    baos.write(UtilBinaryDecoding.shortToByteArray(lowercaseHeight, 2));
    baos.write(UtilBinaryDecoding.shortToByteArray(lowercaseAscenderHeight, 2));
    baos.write(UtilBinaryDecoding.shortToByteArray(lowercaseDescenderDepth, 2));
    baos.write(reserved53_72);
    baos.write(UtilBinaryDecoding.shortToByteArray(gcsgid, 2));
    baos.write(UtilBinaryDecoding.shortToByteArray(fgid, 2));
    baos.write(reserved77_80);
    baos.write(reserved81);

    if (triplets != null) {
      for (var triplet : triplets) {
        triplet.writeAFP(baos, config);
      }
    }

    writeFullStructuredField(os, baos.toByteArray());
  }


  public String getText() {
    return typefaceDescription;
  }

  public String getTypefaceDescription() {
    return typefaceDescription;
  }

  public void setTypefaceDescription(String typefaceDescription) {
    this.typefaceDescription = typefaceDescription;
  }

  public byte getFontWeightClass() {
    return fontWeightClass;
  }

  public void setFontWeightClass(byte fontWeightClass) {
    this.fontWeightClass = fontWeightClass;
  }

  public byte getFontWidthClass() {
    return fontWidthClass;
  }

  public void setFontWidthClass(byte fontWidthClass) {
    this.fontWidthClass = fontWidthClass;
  }

  public short getMaxBaselineExtent() {
    return maxBaselineExtent;
  }

  public void setMaxBaselineExtent(short maxBaselineExtent) {
    this.maxBaselineExtent = maxBaselineExtent;
  }

  public short getFontClassificationFlags() {
    return fontClassificationFlags;
  }

  public void setFontClassificationFlags(short fontClassificationFlags) {
    this.fontClassificationFlags = fontClassificationFlags;
  }

  public byte getDesignRes() {
    return designRes;
  }

  public void setDesignRes(byte designRes) {
    this.designRes = designRes;
  }

  public short getCapMHeight() {
    return capMHeight;
  }

  public void setCapMHeight(short capMHeight) {
    this.capMHeight = capMHeight;
  }

  public short getLowercaseHeight() {
    return lowercaseHeight;
  }

  public void setLowercaseHeight(short lowercaseHeight) {
    this.lowercaseHeight = lowercaseHeight;
  }

  public short getLowercaseAscenderHeight() {
    return lowercaseAscenderHeight;
  }

  public void setLowercaseAscenderHeight(short lowercaseAscenderHeight) {
    this.lowercaseAscenderHeight = lowercaseAscenderHeight;
  }

  public short getLowercaseDescenderDepth() {
    return lowercaseDescenderDepth;
  }

  public void setLowercaseDescenderDepth(short lowercaseDescenderDepth) {
    this.lowercaseDescenderDepth = lowercaseDescenderDepth;
  }

  public short getGcsgid() {
    return gcsgid;
  }

  public void setGcsgid(short gcsgid) {
    this.gcsgid = gcsgid;
  }

  public short getFgid() {
    return fgid;
  }

  public void setFgid(short fgid) {
    this.fgid = fgid;
  }

  public List<Triplet> getTriplets() {
    return triplets;
  }

  public void setTriplets(List<Triplet> triplets) {
    this.triplets = triplets;
  }
}
