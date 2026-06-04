/*
Copyright 2026 Rudolf Fiala

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

package com.mgz.xml;

import com.mgz.util.FastIntConverter;
import java.io.IOException;
import java.io.OutputStream;
import javax.xml.stream.XMLStreamException;

/**
 * Represents a pre-computed XML byte template with "holes" for variable data.
 */
public class XmlTemplate {
  private final byte[][] fragments;

  public XmlTemplate(byte[][] fragments) {
    this.fragments = fragments;
  }

  /**
   * Writes the template to the provided stream, filling the holes with the given values.
   *
   * @param xsw the XML stream writer
   * @param values the integer values to inject into the holes
   * @throws XMLStreamException if writing fails
   */
  public void write(AfpXmlStreamWriter xsw, int... values) throws XMLStreamException {
    if (values.length != fragments.length - 1) {
      throw new IllegalArgumentException("Expected " + (fragments.length - 1) + " values, but got " + values.length);
    }

    try {
      xsw.ensureFlushed();
      OutputStream os = xsw.getUnderlyingOutputStream();
      for (int i = 0; i < values.length; i++) {
        os.write(fragments[i]);
        FastIntConverter.writeInt(values[i], os);
      }
      os.write(fragments[fragments.length - 1]);
    } catch (IOException e) {
      throw new XMLStreamException("Failed to write template", e);
    }
  }

  public int getHoleCount() {
    return fragments.length - 1;
  }
}
