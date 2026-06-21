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

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.mgz.afp.base.annotations.AFPField;
import com.mgz.afp.exceptions.AFPParserException;
import com.mgz.afp.parser.AFPParserConfiguration;
import com.mgz.afp.parser.PTOCAControlSequenceParser;
import com.mgz.afp.ptoca.controlSequence.PTOCAControlSequence;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * IPDS Write Text (WT) Command (X'D62D').
 * Refer to IPDS-5-071.
 */
public class WT_WriteText extends IPDSCommand {

  @AFPField
  @JsonIgnore
  private List<PTOCAControlSequence> controlSequences;

  @JacksonXmlElementWrapper(useWrapping = false)
  @JacksonXmlProperty(localName = "controlSequences")
  public List<PTOCAControlSequence> getControlSequencesXml() {
    return controlSequences;
  }

  private volatile byte[] originalPayload;

  @Override
  public void reset() {
    super.reset();
    controlSequences = null;
    originalPayload = null;
  }

  @Override
  public void decodeAFP(byte[] sfData, int offset, int length, AFPParserConfiguration config)
      throws AFPParserException {
    int actualLength = getActualLength(sfData, offset, length);
    int consumed = decodeIPDSHeader(sfData, offset, actualLength);

    if (actualLength > consumed) {
      originalPayload = new byte[actualLength - consumed];
      System.arraycopy(sfData, offset + consumed, originalPayload, 0, actualLength - consumed);
      controlSequences = PTOCAControlSequenceParser.parseControlSequences(
          sfData, offset + consumed, actualLength - consumed, config);
    } else {
      originalPayload = null;
      controlSequences = null;
    }
  }

  @Override
  public void writeAFP(OutputStream os, AFPParserConfiguration config) throws IOException {
    if (controlSequences != null) {
      ByteArrayOutputStream baos = new ByteArrayOutputStream();
      writeIPDSHeader(baos);
      for (PTOCAControlSequence cs : controlSequences) {
        if (cs.getCsi() != null) {
          baos.write(cs.getCsi().toBytes());
        }
        cs.writeAFP(baos, config);
      }
      writeFullStructuredField(os, baos.toByteArray());
    } else if (originalPayload != null) {
      ByteArrayOutputStream baos = new ByteArrayOutputStream();
      writeIPDSHeader(baos);
      baos.write(originalPayload);
      writeFullStructuredField(os, baos.toByteArray());
    } else {
      ByteArrayOutputStream baos = new ByteArrayOutputStream();
      writeIPDSHeader(baos);
      writeFullStructuredField(os, baos.toByteArray());
    }
  }

  public List<PTOCAControlSequence> getControlSequences() {
    return controlSequences;
  }

  public void setControlSequences(List<PTOCAControlSequence> controlSequences) {
    this.controlSequences = controlSequences;
  }

  public void addControlSequence(PTOCAControlSequence cs) {
    if (cs == null) {
      return;
    }
    if (controlSequences == null) {
      controlSequences = new ArrayList<>();
    }
    controlSequences.add(cs);
  }

  @Override
  public void release() {
    if (controlSequences != null) {
      for (PTOCAControlSequence sequence : controlSequences) {
        sequence.release();
      }
      controlSequences = null;
    }
    super.release();
  }
}
