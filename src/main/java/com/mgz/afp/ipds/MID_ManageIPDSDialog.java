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
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;

/**
 * IPDS Manage IPDS Dialog (MID) Command (X'D601').
 * Refer to IPDS-4-016, IPDS-4-551.
 */
public class MID_ManageIPDSDialog extends IPDSCommand {

  public enum MID_Type {
    StartIPDSDialog(0x00),
    EndIPDSDialog(0x01);

    private final int value;

    MID_Type(int value) {
      this.value = value;
    }

    public int getValue() {
      return value;
    }

    public static MID_Type valueOf(int value) {
      for (MID_Type type : values()) {
        if (type.value == value) {
          return type;
        }
      }
      return null;
    }
  }

  @AFPField
  private MID_Type type;

  @Override
  public void decodeAFP(byte[] sfData, int offset, int length, AFPParserConfiguration config)
      throws AFPParserException {
    int actualLength = getActualLength(sfData, offset, length);
    int consumed = decodeIPDSHeader(sfData, offset, actualLength);

    checkDataLength(sfData, offset + consumed, actualLength - consumed, 1);
    int typeVal = sfData[offset + consumed] & 0xFF;
    this.type = MID_Type.valueOf(typeVal);
  }

  @Override
  public void writeAFP(OutputStream os, AFPParserConfiguration config) throws IOException {
    ByteArrayOutputStream baos = new ByteArrayOutputStream();
    writeIPDSHeader(baos);
    baos.write(type != null ? type.getValue() : 0);
    writeFullStructuredField(os, baos.toByteArray());
  }

  public MID_Type getType() {
    return type;
  }

  public void setType(MID_Type type) {
    this.type = type;
  }
}
