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
 * IPDS Include Overlay (IO) Command (X'D67D').
 * Refer to IPDS-12-004, IPDS-12-020.
 */
public class IO_IncludeOverlay extends IPDSCommand {

  @AFPField
  private int overlayHaid;

  @AFPField
  private byte overlayType;

  @AFPField
  private int xpOffset;

  @AFPField
  private byte overlayUse;

  @AFPField
  private int ypOffset;

  @AFPField(isOptional = true)
  private AFPOrientation orientation;

  @Override
  public void decodeAFP(byte[] sfData, int offset, int length, AFPParserConfiguration config)
      throws AFPParserException {
    int actualLength = getActualLength(sfData, offset, length);
    int consumed = decodeIPDSHeader(sfData, offset, actualLength);

    checkDataLength(sfData, offset + consumed, actualLength - consumed, 10);
    this.overlayHaid = UtilBinaryDecoding.parseInt(sfData, offset + consumed, 2);
    this.overlayType = sfData[offset + consumed + 2];
    this.xpOffset = UtilBinaryDecoding.parseInt(sfData, offset + consumed + 3, 3);
    this.overlayUse = sfData[offset + consumed + 6];
    this.ypOffset = UtilBinaryDecoding.parseInt(sfData, offset + consumed + 7, 3);

    if (actualLength - consumed >= 12) {
      this.orientation = AFPOrientation.valueOf(UtilBinaryDecoding.parseInt(sfData, offset + consumed + 10, 2));
    } else {
      this.orientation = null;
    }
  }

  @Override
  public void writeAFP(OutputStream os, AFPParserConfiguration config) throws IOException {
    ByteArrayOutputStream baos = new ByteArrayOutputStream();
    writeIPDSHeader(baos);
    baos.write(UtilBinaryDecoding.intToByteArray(overlayHaid, 2));
    baos.write(overlayType);
    baos.write(UtilBinaryDecoding.intToByteArray(xpOffset, 3));
    baos.write(overlayUse);
    baos.write(UtilBinaryDecoding.intToByteArray(ypOffset, 3));
    if (orientation != null) {
      baos.write(UtilBinaryDecoding.intToByteArray(orientation.getCode(), 2));
    }
    writeFullStructuredField(os, baos.toByteArray());
  }

  public int getOverlayHaid() {
    return overlayHaid;
  }

  public void setOverlayHaid(int overlayHaid) {
    this.overlayHaid = overlayHaid;
  }

  public byte getOverlayType() {
    return overlayType;
  }

  public void setOverlayType(byte overlayType) {
    this.overlayType = overlayType;
  }

  public int getXpOffset() {
    return xpOffset;
  }

  public void setXpOffset(int xpOffset) {
    this.xpOffset = xpOffset;
  }

  public byte getOverlayUse() {
    return overlayUse;
  }

  public void setOverlayUse(byte overlayUse) {
    this.overlayUse = overlayUse;
  }

  public int getYpOffset() {
    return ypOffset;
  }

  public void setYpOffset(int ypOffset) {
    this.ypOffset = ypOffset;
  }

  public AFPOrientation getOrientation() {
    return orientation;
  }

  public void setOrientation(AFPOrientation orientation) {
    this.orientation = orientation;
  }
}
