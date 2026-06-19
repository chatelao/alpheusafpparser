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
import com.mgz.afp.enums.AFPOrientation;
import com.mgz.afp.exceptions.AFPParserException;
import com.mgz.afp.parser.AFPParserConfiguration;
import com.mgz.util.UtilBinaryDecoding;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;

/**
 * IPDS Logical Page Position (LPP) Command (X'D66D').
 * Refer to IPDS-4-015, IPDS-4-543.
 */
public class LPP_LogicalPagePosition extends IPDSCommand {

  public enum LPP_Placement {
    DefaultPlacement(0x00),
    Partition1_FrontSide(0x10),
    Partition1_BackSide(0x11),
    Partition2_FrontSide(0x20),
    Partition2_BackSide(0x21),
    Partition3_FrontSide(0x30),
    Partition3_BackSide(0x31),
    Partition4_FrontSide(0x40),
    Partition4_BackSide(0x41);

    private final int value;

    LPP_Placement(int value) {
      this.value = value;
    }

    public int getValue() {
      return value;
    }

    public static LPP_Placement valueOf(int value) {
      for (LPP_Placement p : values()) {
        if (p.value == value) {
          return p;
        }
      }
      return null;
    }
  }

  @AFPField
  private int xmPageOffset;

  @AFPField
  private LPP_Placement placement;

  @AFPField
  private int ymPageOffset;

  @AFPField
  private AFPOrientation orientation;

  @Override
  public void decodeAFP(byte[] sfData, int offset, int length, AFPParserConfiguration config) throws AFPParserException {
    int actualLength = getActualLength(sfData, offset, length);
    int consumed = decodeIPDSHeader(sfData, offset, actualLength);

    checkDataLength(sfData, offset + consumed, actualLength - consumed, 10);
    // Byte 0 is reserved
    this.xmPageOffset = UtilBinaryDecoding.parseInt(sfData, offset + consumed + 1, 3);
    this.placement = LPP_Placement.valueOf(sfData[offset + consumed + 4] & 0xFF);
    this.ymPageOffset = UtilBinaryDecoding.parseInt(sfData, offset + consumed + 5, 3);
    this.orientation = AFPOrientation.valueOf(UtilBinaryDecoding.parseInt(sfData, offset + consumed + 8, 2));
  }

  @Override
  public void writeAFP(OutputStream os, AFPParserConfiguration config) throws IOException {
    ByteArrayOutputStream baos = new ByteArrayOutputStream();
    writeIPDSHeader(baos);
    baos.write(0x00); // Reserved
    baos.write(UtilBinaryDecoding.intToByteArray(xmPageOffset, 3));
    baos.write(placement != null ? placement.getValue() : 0);
    baos.write(UtilBinaryDecoding.intToByteArray(ymPageOffset, 3));
    baos.write(orientation != null ? orientation.toBytes() : new byte[] {0, 0});
    writeFullStructuredField(os, baos.toByteArray());
  }

  public int getXmPageOffset() {
    return xmPageOffset;
  }

  public void setXmPageOffset(int xmPageOffset) {
    this.xmPageOffset = xmPageOffset;
  }

  public LPP_Placement getPlacement() {
    return placement;
  }

  public void setPlacement(LPP_Placement placement) {
    this.placement = placement;
  }

  public int getYmPageOffset() {
    return ymPageOffset;
  }

  public void setYmPageOffset(int ymPageOffset) {
    this.ymPageOffset = ymPageOffset;
  }

  public AFPOrientation getOrientation() {
    return orientation;
  }

  public void setOrientation(AFPOrientation orientation) {
    this.orientation = orientation;
  }
}
