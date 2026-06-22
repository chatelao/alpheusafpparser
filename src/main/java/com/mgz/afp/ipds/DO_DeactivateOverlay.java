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
 * IPDS Deactivate Overlay (DO) Command (X'D6EF').
 * Refer to IPDS-12-003, IPDS-12-013, IPDS-12-015.
 */
public class DO_DeactivateOverlay extends IPDSCommand {

  @AFPField
  private int overlayHaid;

  @Override
  public void decodeAFP(byte[] sfData, int offset, int length, AFPParserConfiguration config)
      throws AFPParserException {
    int actualLength = getActualLength(sfData, offset, length);
    int consumed = decodeIPDSHeader(sfData, offset, actualLength);

    int remaining = actualLength - consumed;
    if (remaining == 1) {
      this.overlayHaid = sfData[offset + consumed] & 0xFF;
    } else if (remaining >= 2) {
      this.overlayHaid = UtilBinaryDecoding.parseInt(sfData, offset + consumed, 2);
    } else {
      throw new AFPParserException("DO: Missing overlay ID/HAID");
    }
  }

  @Override
  public void writeAFP(OutputStream os, AFPParserConfiguration config) throws IOException {
    ByteArrayOutputStream baos = new ByteArrayOutputStream();
    writeIPDSHeader(baos);
    if (overlayHaid <= 0xFF) {
      baos.write(overlayHaid & 0xFF);
    } else {
      baos.write(UtilBinaryDecoding.intToByteArray(overlayHaid, 2));
    }
    writeFullStructuredField(os, baos.toByteArray());
  }

  public int getOverlayHaid() {
    return overlayHaid;
  }

  public void setOverlayHaid(int overlayHaid) {
    this.overlayHaid = overlayHaid;
  }
}
