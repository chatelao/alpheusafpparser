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

import com.mgz.afp.base.StructuredField;
import com.mgz.afp.base.annotations.AFPField;
import com.mgz.afp.exceptions.AFPParserException;
import com.mgz.util.UtilBinaryDecoding;
import java.io.IOException;
import java.io.OutputStream;

/**
 * Base class for all IPDS Commands.
 * IPDS commands are treated as structured fields in Alpheus.
 */
public abstract class IPDSCommand extends StructuredField {

  @AFPField
  protected byte flagByte;

  @AFPField(isOptional = true)
  protected Integer correlationId;

  public byte getFlagByte() {
    return flagByte;
  }

  public void setFlagByte(byte flagByte) {
    this.flagByte = flagByte;
  }

  public boolean isAcknowledgementRequired() {
    return (flagByte & 0x80) != 0;
  }

  public void setAcknowledgementRequired(boolean arq) {
    if (arq) {
      flagByte |= 0x80;
    } else {
      flagByte &= 0x7F;
    }
  }

  public Integer getCorrelationId() {
    return correlationId;
  }

  public void setCorrelationId(Integer correlationId) {
    this.correlationId = correlationId;
  }

  /**
   * Decodes the common IPDS header (Flag byte and optional CID).
   *
   * @param sfData the structured field data
   * @param offset the offset to the flag byte (usually SF data offset 0)
   * @param length the actual length of data available from offset
   * @return the number of header bytes consumed
   * @throws AFPParserException if data is too short for CID
   */
  protected int decodeIPDSHeader(byte[] sfData, int offset, int length) throws AFPParserException {
    this.flagByte = sfData[offset];
    int consumed = 1;
    if (isAcknowledgementRequired()) {
      checkDataLength(sfData, offset, length, 3);
      this.correlationId = UtilBinaryDecoding.parseInt(sfData, offset + 1, 2);
      consumed += 2;
    } else {
      this.correlationId = null;
    }
    return consumed;
  }

  protected void writeIPDSHeader(OutputStream os) throws IOException {
    os.write(flagByte);
    if (isAcknowledgementRequired() && correlationId != null) {
      os.write(UtilBinaryDecoding.intToByteArray(correlationId, 2));
    } else if (isAcknowledgementRequired()) {
      os.write(new byte[] {0, 0}); // Default CID if none provided but ARQ is set
    }
  }
}
