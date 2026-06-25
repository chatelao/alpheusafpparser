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
 * IPDS Write Metadata Control (WMC) Command (X'D68A').
 * Refer to IPDS-A-049.
 */
public class WMC_WriteMetadataControl extends IPDSCommand {

  @AFPField
  private byte[] metadataControlData;

  @Override
  public void decodeAFP(byte[] sfData, int offset, int length, AFPParserConfiguration config) throws AFPParserException {
    int actualLength = getActualLength(sfData, offset, length);
    int consumed = decodeIPDSHeader(sfData, offset, actualLength);

    int remaining = actualLength - consumed;
    if (remaining > 0) {
      this.metadataControlData = new byte[remaining];
      System.arraycopy(sfData, offset + consumed, this.metadataControlData, 0, remaining);
    }
  }

  @Override
  public void writeAFP(OutputStream os, AFPParserConfiguration config) throws IOException {
    ByteArrayOutputStream baos = new ByteArrayOutputStream();
    writeIPDSHeader(baos);
    if (metadataControlData != null) {
      baos.write(metadataControlData);
    }
    writeFullStructuredField(os, baos.toByteArray());
  }

  public byte[] getMetadataControlData() {
    return metadataControlData;
  }

  public void setMetadataControlData(byte[] metadataControlData) {
    this.metadataControlData = metadataControlData;
  }
}
