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
import java.nio.charset.StandardCharsets;

/**
 * IPDS Load Code Page Control (LCPC) Command (X'D61A').
 */
public class LCPC_LoadCodePageControl extends IPDSCommand {

  @AFPField
  private int haid;
  @AFPField
  private int encodingScheme;
  @AFPField
  private long byteCount;
  @AFPField
  private byte extensionFlags;
  @AFPField
  private byte[] variableSpaceCodePoint;
  @AFPField
  private Integer gcsgid;
  @AFPField
  private Integer cpgid;
  @AFPField
  private String defaultGcgid;
  @AFPField
  private Byte defaultProcessingFlags;
  @AFPField
  private Long defaultUnicodeScalarValue;

  @Override
  public void decodeAFP(byte[] sfData, int offset, int length, AFPParserConfiguration config)
      throws AFPParserException {
    int actualLength = getActualLength(sfData, offset, length);
    int consumed = decodeIPDSHeader(sfData, offset, actualLength);
    int current = offset + consumed;
    int remaining = actualLength - consumed;

    if (remaining >= 10) {
      this.haid = UtilBinaryDecoding.parseInt(sfData, current, 2);
      this.encodingScheme = UtilBinaryDecoding.parseInt(sfData, current + 2, 2);
      this.byteCount = UtilBinaryDecoding.parseLong(sfData, current + 4, 4);
      this.extensionFlags = sfData[current + 8];
      // byte 9 reserved

      // Code point size is in bits 4-7 of the 16-bit encoding scheme (first byte)
      int vspSize = ((encodingScheme >> 8) & 0x0F) == 2 ? 2 : 1;
      if (remaining >= 10 + vspSize) {
        this.variableSpaceCodePoint = new byte[vspSize];
        System.arraycopy(sfData, current + 10, this.variableSpaceCodePoint, 0, vspSize);
        int n = 10 + vspSize - 1;

        // GRID information
        if (remaining >= n + 5) {
          this.gcsgid = UtilBinaryDecoding.parseInt(sfData, current + n + 1, 2);
          this.cpgid = UtilBinaryDecoding.parseInt(sfData, current + n + 3, 2);

          // Default-character information
          if (remaining >= n + 14) {
            this.defaultGcgid = new String(sfData, current + n + 5, 8, StandardCharsets.US_ASCII);
            this.defaultProcessingFlags = sfData[current + n + 13];

            // Unicode scalar value for default GCGID
            if ((extensionFlags & 0x80) != 0 && remaining >= n + 18) {
              this.defaultUnicodeScalarValue = UtilBinaryDecoding.parseLong(sfData, current + n + 14, 4);
            }
          }
        }
      }
    }
  }

  @Override
  public void writeAFP(OutputStream os, AFPParserConfiguration config) throws IOException {
    ByteArrayOutputStream baos = new ByteArrayOutputStream();
    writeIPDSHeader(baos);
    baos.write(UtilBinaryDecoding.intToByteArray(haid, 2));
    baos.write(UtilBinaryDecoding.intToByteArray(encodingScheme, 2));
    baos.write(UtilBinaryDecoding.longToByteArray(byteCount, 4));
    baos.write(extensionFlags);
    baos.write(0); // Reserved

    if (variableSpaceCodePoint != null) {
      baos.write(variableSpaceCodePoint);

      if (gcsgid != null && cpgid != null) {
        baos.write(UtilBinaryDecoding.intToByteArray(gcsgid, 2));
        baos.write(UtilBinaryDecoding.intToByteArray(cpgid, 2));

        if (defaultGcgid != null && defaultProcessingFlags != null) {
          byte[] gcgidBytes = defaultGcgid.getBytes(StandardCharsets.US_ASCII);
          byte[] paddedGcgid = new byte[8];
          for (int i = 0; i < 8; i++) {
            paddedGcgid[i] = (i < gcgidBytes.length) ? gcgidBytes[i] : (byte) 0x20; // Pad with space
          }
          baos.write(paddedGcgid);
          baos.write(defaultProcessingFlags);

          if (defaultUnicodeScalarValue != null && (extensionFlags & 0x80) != 0) {
            baos.write(UtilBinaryDecoding.longToByteArray(defaultUnicodeScalarValue, 4));
          }
        }
      }
    }

    writeFullStructuredField(os, baos.toByteArray());
  }

  public int getHaid() {
    return haid;
  }

  public void setHaid(int haid) {
    this.haid = haid;
  }

  public int getEncodingScheme() {
    return encodingScheme;
  }

  public void setEncodingScheme(int encodingScheme) {
    this.encodingScheme = encodingScheme;
  }

  public long getByteCount() {
    return byteCount;
  }

  public void setByteCount(long byteCount) {
    this.byteCount = byteCount;
  }

  public byte getExtensionFlags() {
    return extensionFlags;
  }

  public void setExtensionFlags(byte extensionFlags) {
    this.extensionFlags = extensionFlags;
  }

  public byte[] getVariableSpaceCodePoint() {
    return variableSpaceCodePoint;
  }

  public void setVariableSpaceCodePoint(byte[] variableSpaceCodePoint) {
    this.variableSpaceCodePoint = variableSpaceCodePoint;
  }

  public Integer getGcsgid() {
    return gcsgid;
  }

  public void setGcsgid(Integer gcsgid) {
    this.gcsgid = gcsgid;
  }

  public Integer getCpgid() {
    return cpgid;
  }

  public void setCpgid(Integer cpgid) {
    this.cpgid = cpgid;
  }

  public String getDefaultGcgid() {
    return defaultGcgid;
  }

  public void setDefaultGcgid(String defaultGcgid) {
    this.defaultGcgid = defaultGcgid;
  }

  public Byte getDefaultProcessingFlags() {
    return defaultProcessingFlags;
  }

  public void setDefaultProcessingFlags(Byte defaultProcessingFlags) {
    this.defaultProcessingFlags = defaultProcessingFlags;
  }

  public Long getDefaultUnicodeScalarValue() {
    return defaultUnicodeScalarValue;
  }

  public void setDefaultUnicodeScalarValue(Long defaultUnicodeScalarValue) {
    this.defaultUnicodeScalarValue = defaultUnicodeScalarValue;
  }
}
