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
import com.mgz.afp.exceptions.AFPParserException;
import com.mgz.afp.parser.AFPParserConfiguration;
import com.mgz.util.UtilBinaryDecoding;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;

/**
 * IPDS Deactivate Font (DF) Command (X'D64F').
 * Refer to IPDS-4-006, IPDS-4-287.
 */
public class DF_DeactivateFont extends IPDSCommand {

  @AFPField
  private byte deactivationType;

  @AFPField(isOptional = true)
  private Integer haid;

  @AFPField(isOptional = true)
  private Short sectionId;

  @AFPField(isOptional = true)
  private Integer fis;

  @Override
  public void decodeAFP(byte[] sfData, int offset, int length, AFPParserConfiguration config)
      throws AFPParserException {
    int actualLength = getActualLength(sfData, offset, length);
    int consumed = decodeIPDSHeader(sfData, offset, actualLength);

    if (actualLength > consumed) {
      deactivationType = sfData[offset + consumed];
      consumed++;

      if (actualLength >= consumed + 2) {
        haid = UtilBinaryDecoding.parseInt(sfData, offset + consumed, 2);
        consumed += 2;
      }
      if (actualLength >= consumed + 1) {
        sectionId = (short) (sfData[offset + consumed] & 0xFF);
        consumed++;
      }
      if (actualLength >= consumed + 2) {
        fis = UtilBinaryDecoding.parseInt(sfData, offset + consumed, 2);
        consumed += 2;
      }
    }
  }

  @Override
  public void writeAFP(OutputStream os, AFPParserConfiguration config) throws IOException {
    ByteArrayOutputStream baos = new ByteArrayOutputStream();
    writeIPDSHeader(baos);
    baos.write(deactivationType);
    if (haid != null) {
      baos.write(UtilBinaryDecoding.intToByteArray(haid, 2));
    }
    if (sectionId != null) {
      baos.write(sectionId & 0xFF);
    }
    if (fis != null) {
      baos.write(UtilBinaryDecoding.intToByteArray(fis, 2));
    }
    writeFullStructuredField(os, baos.toByteArray());
  }

  public byte getDeactivationType() {
    return deactivationType;
  }

  public void setDeactivationType(byte deactivationType) {
    this.deactivationType = deactivationType;
  }

  public Integer getHaid() {
    return haid;
  }

  public void setHaid(Integer haid) {
    this.haid = haid;
  }

  public Short getSectionId() {
    return sectionId;
  }

  public void setSectionId(Short sectionId) {
    this.sectionId = sectionId;
  }

  public Integer getFis() {
    return fis;
  }

  public void setFis(Integer fis) {
    this.fis = fis;
  }
}
