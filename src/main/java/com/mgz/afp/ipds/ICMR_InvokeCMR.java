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
import java.util.ArrayList;
import java.util.List;

/**
 * IPDS Invoke CMR (ICMR) Command (X'D66B').
 * Refer to IPDS-4-011, IPDS-4-348.
 */
public class ICMR_InvokeCMR extends IPDSCommand {

  @AFPField
  private byte invocationFlags;

  @AFPField
  private List<Integer> haids;

  public boolean isReset() {
    return (invocationFlags & 0x80) != 0;
  }

  public void setReset(boolean reset) {
    if (reset) {
      invocationFlags |= 0x80;
    } else {
      invocationFlags &= 0x7F;
    }
  }

  @Override
  public void decodeAFP(byte[] sfData, int offset, int length, AFPParserConfiguration config)
      throws AFPParserException {
    int actualLength = getActualLength(sfData, offset, length);
    int consumed = decodeIPDSHeader(sfData, offset, actualLength);

    checkDataLength(sfData, offset + consumed, actualLength - consumed, 5);
    int start = offset + consumed;

    this.invocationFlags = sfData[start];
    // Bytes 1-4 are reserved

    this.haids = new ArrayList<>();
    int current = start + 5;
    while (current + 2 <= offset + actualLength) {
      this.haids.add(UtilBinaryDecoding.parseInt(sfData, current, 2));
      current += 2;
    }
  }

  @Override
  public void writeAFP(OutputStream os, AFPParserConfiguration config) throws IOException {
    ByteArrayOutputStream baos = new ByteArrayOutputStream();
    writeIPDSHeader(baos);
    baos.write(invocationFlags);
    baos.write(new byte[] {0, 0, 0, 0}); // Reserved
    if (haids != null) {
      for (Integer haid : haids) {
        baos.write(UtilBinaryDecoding.intToByteArray(haid, 2));
      }
    }
    writeFullStructuredField(os, baos.toByteArray());
  }

  public byte getInvocationFlags() {
    return invocationFlags;
  }

  public void setInvocationFlags(byte invocationFlags) {
    this.invocationFlags = invocationFlags;
  }

  public List<Integer> getHaids() {
    return haids;
  }

  public void setHaids(List<Integer> haids) {
    this.haids = haids;
  }

  public void addHaid(int haid) {
    if (this.haids == null) {
      this.haids = new ArrayList<>();
    }
    this.haids.add(haid);
  }
}
