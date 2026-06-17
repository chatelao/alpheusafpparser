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
 * IPDS End (END) Command (X'D65D').
 * Refer to IPDS-4-008, IPDS-4-312.
 */
public class END_End extends IPDSCommand {

  @AFPField(isOptional = true)
  private byte[] ignoredData;

  @Override
  public void decodeAFP(byte[] sfData, int offset, int length, AFPParserConfiguration config) throws AFPParserException {
    int actualLength = getActualLength(sfData, offset, length);
    int consumed = decodeIPDSHeader(sfData, offset, actualLength);

    if (actualLength > consumed) {
      ignoredData = new byte[actualLength - consumed];
      System.arraycopy(sfData, offset + consumed, ignoredData, 0, ignoredData.length);
    } else {
      ignoredData = null;
    }
  }

  @Override
  public void writeAFP(OutputStream os, AFPParserConfiguration config) throws IOException {
    ByteArrayOutputStream baos = new ByteArrayOutputStream();
    writeIPDSHeader(baos);
    if (ignoredData != null) {
      baos.write(ignoredData);
    }
    writeFullStructuredField(os, baos.toByteArray());
  }

  public byte[] getIgnoredData() {
    return ignoredData;
  }

  public void setIgnoredData(byte[] ignoredData) {
    this.ignoredData = ignoredData;
  }
}
