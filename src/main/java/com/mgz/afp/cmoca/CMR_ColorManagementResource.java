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

package com.mgz.afp.cmoca;

import com.mgz.util.UtilCharacterEncoding;
import com.mgz.afp.base.StructuredField;
import com.mgz.afp.base.annotations.AFPField;
import com.mgz.afp.exceptions.AFPParserException;
import com.mgz.afp.parser.AFPParserConfiguration;
import com.mgz.util.Constants;
import com.mgz.util.UtilBinaryDecoding;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

/**
 * Color Management Resource (CMR).
 * CMOCA Reference, Chapter 3.
 */
public class CMR_ColorManagementResource extends StructuredField {

  @AFPField
  private long length; // 4 bytes
  @AFPField
  private String signature; // 4 bytes, "CMR9"
  @AFPField
  private int reserved1; // 2 bytes
  @AFPField
  private String alias; // 16 bytes
  @AFPField
  private String type; // 4 bytes
  @AFPField
  private String version; // 14 bytes
  @AFPField
  private String manufacturerName; // 10 bytes
  @AFPField
  private String deviceType; // 12 bytes
  @AFPField
  private String deviceModel; // 6 bytes
  @AFPField
  private String mediaBrightness; // 6 bytes
  @AFPField
  private String mediaColor; // 6 bytes
  @AFPField
  private String mediaFinish; // 4 bytes
  @AFPField
  private String mediaWeight; // 6 bytes
  @AFPField
  private String prop1; // 10 bytes
  @AFPField
  private String prop2; // 12 bytes
  @AFPField
  private String prop3; // 8 bytes
  @AFPField
  private String prop4; // 8 bytes
  @AFPField
  private String prop5; // 8 bytes
  @AFPField
  private String reserved2; // 16 bytes
  @AFPField
  private long reserved3; // 8 bytes
  @AFPField
  private byte[] cmrData;

  private List<CMRTag> tags;

  private byte[] rawCmrName;

  /**
   * Validates that all mandatory tags for the current CMR type and subset are present.
   * CMOCA Reference, Chapter 4.
   *
   * @throws AFPParserException if a mandatory tag is missing.
   */
  public void validate() throws AFPParserException {
    if (tags == null || tags.isEmpty()) {
      // End Data tag X'FFFF' is mandatory for all [CMOCA-4-008]
      throw new AFPParserException("CMR is missing mandatory End Data tag (X'FFFF')");
    }

    boolean hasEndData = tags.stream().anyMatch(t -> t.getTagId() == 0xFFFF);
    if (!hasEndData) {
      throw new AFPParserException("CMR is missing mandatory End Data tag (X'FFFF')");
    }

    if ("HT".equals(type)) {
      validateHalftone();
    } else if ("TC".equals(type)) {
      validateToneTransferCurve();
    } else if ("CC".equals(type)) {
      validateColorConversion();
    } else if ("LK".equals(type) || "DL".equals(type)) {
      validateLinkColorConversion();
    } else if ("IX".equals(type)) {
      validateIndexed();
    }
  }

  private void validateHalftone() throws AFPParserException {
    int subset = getSubset(0x1011);
    checkTag(0x1011, "Halftone Subset");
    checkTag(0x1021, "Array Width");
    checkTag(0x1025, "Array Height");

    switch (subset) {
      case 0x01: // Bilevel Point-Operation
        checkTag(0x1045, "Bilevel Point-Operation Screen Data");
        break;
      case 0x02: // Multilevel Point-Operation
        checkTag(0x1030, "Max Image Value");
        checkTag(0x1035, "Number of Device Levels");
        checkTag(0x1050, "Multilevel Point-Operation Screen Data");
        break;
      case 0x03: // Bilevel Error Diffusion
        checkTag(0x1055, "Error Diffusion Filter");
        checkTag(0x1060, "Location of Current Pixel");
        checkTag(0x1065, "Raster Direction");
        checkTag(0x1070, "Boundary Condition");
        checkTag(0x1075, "Threshold Value");
        break;
      case 0x04: // Multilevel Error Diffusion
        checkTag(0x1035, "Number of Device Levels");
        checkTag(0x1055, "Error Diffusion Filter");
        checkTag(0x1060, "Location of Current Pixel");
        checkTag(0x1065, "Raster Direction");
        checkTag(0x1070, "Boundary Condition");
        checkTag(0x1080, "Quantization Boundary Table");
        break;
      default:
        // Unknown subset, but basic tags checked
    }
  }

  private void validateToneTransferCurve() throws AFPParserException {
    int subset = getSubset(0x2004);
    checkTag(0x2004, "Tone Transfer Curve Subset");
    if (subset == 0x01) {
      checkTag(0x2015, "Tone Transfer Curve Data");
    }
  }

  private void validateColorConversion() throws AFPParserException {
    checkTag(0x3011, "ICC Profile Subset");
    checkTag(0x3015, "ICC Profile Data");
  }

  private void validateLinkColorConversion() throws AFPParserException {
    int subset = getSubset(0x4011);
    checkTag(0x4011, "Link Color Conversion Subset");
    if (subset == 0x01 || subset == 0x02) {
      checkTag(0x4015, "Link Audit CMR OID");
      checkTag(0x4020, "Link Instruction CMR OID");
      checkTag(0x4025, "Link Audit CMR Name");
      checkTag(0x4030, "Link Instruction CMR Name");
      if (subset == 0x01) {
        checkTag(0x4035, "Default Rendering Intent");
        checkTag(0x4040, "Link LUT Perceptual");
      }
    } else if (subset == 0x03) {
      checkTag(0x3015, "ICC Profile Data");
    }
  }

  private void validateIndexed() throws AFPParserException {
    checkTag(0x5011, "Indexed Subset");
    boolean hasPalette = tags.stream().anyMatch(t ->
        t.getTagId() == 0x5020 || t.getTagId() == 0x5025 ||
        t.getTagId() == 0x5030 || t.getTagId() == 0x5035 ||
        t.getTagId() == 0x5040);
    if (!hasPalette) {
      // [CMOCA-4-133] EC-50400E Missing Required Tag: At least one Color Palette tag is required
      throw new AFPParserException("Indexed CMR missing mandatory Color Palette tag");
    }
  }

  private void checkTag(int tagId, String name) throws AFPParserException {
    if (tags.stream().noneMatch(t -> t.getTagId() == tagId)) {
      throw new AFPParserException("CMR type " + type + " missing mandatory tag: " + name + " (X'" + String.format("%04X", tagId) + "')");
    }
  }

  private int getSubset(int subsetTagId) {
    return tags.stream()
        .filter(t -> t.getTagId() == subsetTagId)
        .findFirst()
        .map(t -> {
          byte[] d = t.getData();
          return (d != null && d.length > 0) ? (d[0] & 0xFF) : -1;
        })
        .orElse(-1);
  }

  @Override
  public void decodeAFP(byte[] sfData, int offset, int length, AFPParserConfiguration config) throws AFPParserException {
    int actualLength = getActualLength(sfData, offset, length);
    checkDataLength(sfData, offset, actualLength, 164);

    this.length = UtilBinaryDecoding.parseLong(sfData, offset, 4);
    // [CMOCA-3-253] EC-EFF003 Invalid Length Value
    if (this.length < 164) {
      throw new AFPParserException("EC-EFF003: Invalid Length Value: " + this.length);
    }
    this.signature = new String(sfData, offset + 4, 4, Constants.cpIBM500);
    // [CMOCA-3-254] EC-EFF110 Invalid Field Value: The specified value for CMRSig is not X'434D5239'.
    if (!"CMR9".equals(this.signature)) {
      throw new AFPParserException("Invalid CMR signature: " + this.signature + " (expected 'CMR9')");
    }

    this.reserved1 = UtilBinaryDecoding.parseInt(sfData, offset + 8, 2);
    // [CMOCA-3-047] Reserved; should be set to zero
    if (reserved1 != 0) {
      // We don't throw exception for reserved fields unless mandated, but we can log or follow pattern
    }

    // CMR Name starts here (Bytes 10-155)
    this.alias = trimCmrString(new String(sfData, offset + 10, 16, Constants.utf16be));
    this.type = trimCmrString(new String(sfData, offset + 26, 4, Constants.utf16be));
    this.version = trimCmrString(new String(sfData, offset + 30, 14, Constants.utf16be));
    this.manufacturerName = trimCmrString(new String(sfData, offset + 44, 10, Constants.utf16be));
    this.deviceType = trimCmrString(new String(sfData, offset + 54, 12, Constants.utf16be));
    this.deviceModel = trimCmrString(new String(sfData, offset + 66, 6, Constants.utf16be));
    this.mediaBrightness = trimCmrString(new String(sfData, offset + 72, 6, Constants.utf16be));
    this.mediaColor = trimCmrString(new String(sfData, offset + 78, 6, Constants.utf16be));
    this.mediaFinish = trimCmrString(new String(sfData, offset + 84, 4, Constants.utf16be));
    this.mediaWeight = trimCmrString(new String(sfData, offset + 88, 6, Constants.utf16be));
    this.prop1 = trimCmrString(new String(sfData, offset + 94, 10, Constants.utf16be));
    this.prop2 = trimCmrString(new String(sfData, offset + 104, 12, Constants.utf16be));
    this.prop3 = trimCmrString(new String(sfData, offset + 116, 8, Constants.utf16be));
    this.prop4 = trimCmrString(new String(sfData, offset + 124, 8, Constants.utf16be));
    this.prop5 = trimCmrString(new String(sfData, offset + 132, 8, Constants.utf16be));
    this.reserved2 = trimCmrString(new String(sfData, offset + 140, 16, Constants.utf16be));

    // Store raw CMR name for architected name retrieval [CMOCA-3-042]
    this.rawCmrName = new byte[146];
    System.arraycopy(sfData, offset + 10, this.rawCmrName, 0, 146);

    // [CMOCA-3-255] EC-EFF210 Invalid Field Value: The specified CMRType is invalid.
    if (!isValidCmrType(this.type)) {
      throw new AFPParserException("Invalid CMRType: " + this.type);
    }

    this.reserved3 = UtilBinaryDecoding.parseLong(sfData, offset + 156, 8);
    // [CMOCA-3-101] Reserved; should be set to zero
    if (reserved3 != 0) {
      // informational/ignored
    }

    validateHeader();

    if (actualLength > 164) {
      this.cmrData = new byte[actualLength - 164];
      System.arraycopy(sfData, offset + 164, this.cmrData, 0, this.cmrData.length);
      this.tags = CMRTag.parseTags(this.cmrData);
    } else {
      this.cmrData = null;
      this.tags = null;
    }
  }

  @Override
  public void writeAFP(OutputStream os, AFPParserConfiguration config) throws IOException {
    int dataLen = (cmrData != null) ? cmrData.length : 0;
    byte[] payload = new byte[164 + dataLen];

    this.length = payload.length;
    System.arraycopy(UtilBinaryDecoding.longToByteArray(this.length, 4), 0, payload, 0, 4);

    byte[] sigBytes = (signature != null ? signature : "CMR9").getBytes(Constants.cpIBM500);
    System.arraycopy(sigBytes, 0, payload, 4, Math.min(sigBytes.length, 4));

    System.arraycopy(UtilBinaryDecoding.intToByteArray(reserved1, 2), 0, payload, 8, 2);

    writeUTF16Padded(payload, 10, alias, 16);
    writeUTF16Padded(payload, 26, type, 4);
    writeUTF16Padded(payload, 30, version, 14);
    writeUTF16Padded(payload, 44, manufacturerName, 10);
    writeUTF16Padded(payload, 54, deviceType, 12);
    writeUTF16Padded(payload, 66, deviceModel, 6);
    writeUTF16Padded(payload, 72, mediaBrightness, 6);
    writeUTF16Padded(payload, 78, mediaColor, 6);
    writeUTF16Padded(payload, 84, mediaFinish, 4);
    writeUTF16Padded(payload, 88, mediaWeight, 6);
    writeUTF16Padded(payload, 94, prop1, 10);
    writeUTF16Padded(payload, 104, prop2, 12);
    writeUTF16Padded(payload, 116, prop3, 8);
    writeUTF16Padded(payload, 124, prop4, 8);
    writeUTF16Padded(payload, 132, prop5, 8);
    writeUTF16Padded(payload, 140, reserved2, 16);

    System.arraycopy(UtilBinaryDecoding.longToByteArray(reserved3, 8), 0, payload, 156, 8);

    if (cmrData != null) {
      System.arraycopy(cmrData, 0, payload, 164, cmrData.length);
    }

    writeFullStructuredField(os, payload);
  }

  private String trimCmrString(String s) {
    if (s == null) {
      return null;
    }
    String result = s.replace('\u0000', ' ').replace('@', ' ').trim();
    return result;
  }

  private boolean isValidCmrType(String type) {
    return "CC".equals(type) || "DL".equals(type) || "HT".equals(type) ||
           "IX".equals(type) || "LK".equals(type) || "TC".equals(type);
  }

  private void validateHeader() throws AFPParserException {
    // [CMOCA-3-256] EC-EFF310 Invalid Field Value: The specified CMRVersion is invalid.
    if (version != null && !version.isEmpty()) {
      boolean validVersion = version.matches("\\d{1,3}\\.\\d{1,3}") ||
                             "generic".equals(version) ||
                             "pasthru".equals(version) ||
                             version.startsWith("AFP .");
      if (!validVersion) {
        throw new AFPParserException("EC-EFF310: Invalid CMRVersion: " + version);
      }
    }

    // [CMOCA-3-257] EC-EFF410 Invalid Field Value: The specified MediaBrightness is invalid.
    if (mediaBrightness != null && !mediaBrightness.isEmpty() && !"@@@".equals(mediaBrightness)) {
      if (mediaBrightness.startsWith("Z")) {
        // [CMOCA-3-064] Zxy For screen, a CIE illuminant
        if (mediaBrightness.length() != 3) {
          throw new AFPParserException("EC-EFF410: Invalid MediaBrightness (CIE illuminant): " + mediaBrightness);
        }
      } else {
        // [CMOCA-3-063] 0–100 For print media, percentage of light reflected
        try {
          int val = Integer.parseInt(mediaBrightness);
          if (val < 0 || val > 100) {
            throw new AFPParserException("EC-EFF410: Invalid MediaBrightness (0-100): " + mediaBrightness);
          }
        } catch (NumberFormatException e) {
          throw new AFPParserException("EC-EFF410: Invalid MediaBrightness (numeric expected): " + mediaBrightness);
        }
      }
    }

    // [CMOCA-3-260] EC-EFF710 Invalid Field Value: The specified MediaWeight is invalid.
    if (mediaWeight != null && !mediaWeight.isEmpty() && !"@@@".equals(mediaWeight)) {
      try {
        int val = Integer.parseInt(mediaWeight.trim());
        if (val < 1 || val > 999) {
          throw new AFPParserException("EC-EFF710: Invalid MediaWeight (1-999): " + mediaWeight);
        }
      } catch (NumberFormatException e) {
        throw new AFPParserException("EC-EFF710: Invalid MediaWeight (numeric expected): " + mediaWeight);
      }
    }
  }

  private void writeUTF16Padded(byte[] target, int offset, String value, int lengthInBytes) {
    byte[] bytes = (value != null ? value : "").getBytes(Constants.utf16be);
    int toCopy = Math.min(bytes.length, lengthInBytes);
    System.arraycopy(bytes, 0, target, offset, toCopy);
    // Pad with UTF-16 '@' (X'0040')
    for (int i = toCopy; i < lengthInBytes; i += 2) {
      if (i + 1 < lengthInBytes) {
        target[offset + i] = 0x00;
        target[offset + i + 1] = 0x40;
      } else {
        target[offset + i] = 0x00; // Should not happen with even lengthInBytes
      }
    }
  }

  // Getters and Setters
  public long getLength() {
    return length;
  }

  public void setLength(long length) {
    this.length = length;
  }

  public String getSignature() {
    return signature;
  }

  public void setSignature(String signature) {
    this.signature = signature;
  }

  public int getReserved1() {
    return reserved1;
  }

  public void setReserved1(int reserved1) {
    this.reserved1 = reserved1;
  }

  public String getAlias() {
    return alias;
  }

  @JacksonXmlProperty(localName = "text")
  public String getText() {
    return UtilCharacterEncoding.sanitizeForXml(alias);
  }

  public void setAlias(String alias) {
    this.alias = alias;
  }

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }

  public String getVersion() {
    return version;
  }

  public void setVersion(String version) {
    this.version = version;
  }

  public String getManufacturerName() {
    return manufacturerName;
  }

  public void setManufacturerName(String manufacturerName) {
    this.manufacturerName = manufacturerName;
  }

  public String getDeviceType() {
    return deviceType;
  }

  public void setDeviceType(String deviceType) {
    this.deviceType = deviceType;
  }

  public String getDeviceModel() {
    return deviceModel;
  }

  public void setDeviceModel(String deviceModel) {
    this.deviceModel = deviceModel;
  }

  public String getMediaBrightness() {
    return mediaBrightness;
  }

  public void setMediaBrightness(String mediaBrightness) {
    this.mediaBrightness = mediaBrightness;
  }

  public String getMediaColor() {
    return mediaColor;
  }

  public void setMediaColor(String mediaColor) {
    this.mediaColor = mediaColor;
  }

  public String getMediaFinish() {
    return mediaFinish;
  }

  public void setMediaFinish(String mediaFinish) {
    this.mediaFinish = mediaFinish;
  }

  public String getMediaWeight() {
    return mediaWeight;
  }

  public void setMediaWeight(String mediaWeight) {
    this.mediaWeight = mediaWeight;
  }

  public String getProp1() {
    return prop1;
  }

  public void setProp1(String prop1) {
    this.prop1 = prop1;
  }

  public String getProp2() {
    return prop2;
  }

  public void setProp2(String prop2) {
    this.prop2 = prop2;
  }

  public String getProp3() {
    return prop3;
  }

  public void setProp3(String prop3) {
    this.prop3 = prop3;
  }

  public String getProp4() {
    return prop4;
  }

  public void setProp4(String prop4) {
    this.prop4 = prop4;
  }

  public String getProp5() {
    return prop5;
  }

  public void setProp5(String prop5) {
    this.prop5 = prop5;
  }

  public String getReserved2() {
    return reserved2;
  }

  public void setReserved2(String reserved2) {
    this.reserved2 = reserved2;
  }

  public long getReserved3() {
    return reserved3;
  }

  public void setReserved3(long reserved3) {
    this.reserved3 = reserved3;
  }

  public byte[] getCmrData() {
    return cmrData;
  }

  public void setCmrData(byte[] cmrData) {
    this.cmrData = cmrData;
  }

  public List<CMRTag> getTags() {
    return tags;
  }

  public void setTags(List<CMRTag> tags) {
    this.tags = tags;
  }

  /**
   * Returns the architected CMR name as defined in CMOCA-3-042.
   * It is the concatenated fields in bytes 10–155, exactly in the order specified.
   *
   * @return the architected CMR name string.
   */
  public String getArchitectedName() {
    if (rawCmrName == null) {
      return null;
    }
    return new String(rawCmrName, Constants.utf16be);
  }
}
