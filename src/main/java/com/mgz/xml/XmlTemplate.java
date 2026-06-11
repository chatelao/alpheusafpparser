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
import java.nio.charset.StandardCharsets;
import javax.xml.stream.XMLStreamException;

/**
 * Represents a pre-computed XML byte template with "holes" for variable data.
 */
public class XmlTemplate {
  private static final int INITIAL_BUFFER_SIZE = 16384;
  private static final ThreadLocal<byte[]> BUFFER =
      ThreadLocal.withInitial(() -> new byte[INITIAL_BUFFER_SIZE]);
  private static final byte[] NULL_BYTES = "null".getBytes(StandardCharsets.UTF_8);

  private final byte[][] fragments;

  /**
   * Constructs an XML template with the given fragments.
   *
   * @param fragments the XML fragments
   */
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
      throw new IllegalArgumentException("Expected " + (fragments.length - 1)
          + " values, but got " + values.length);
    }

    try {
      xsw.ensureFlushed();
      OutputStream os = xsw.getUnderlyingOutputStream();
      byte[] buffer = BUFFER.get();
      int pos = 0;

      for (int i = 0; i < values.length; i++) {
        byte[] fragment = fragments[i];
        if (pos + fragment.length + 11 > buffer.length) {
          os.write(buffer, 0, pos);
          pos = 0;
          if (fragment.length > buffer.length) {
            os.write(fragment);
          } else {
            System.arraycopy(fragment, 0, buffer, pos, fragment.length);
            pos += fragment.length;
          }
        } else {
          System.arraycopy(fragment, 0, buffer, pos, fragment.length);
          pos += fragment.length;
        }

        if (pos + 11 > buffer.length) {
          os.write(buffer, 0, pos);
          pos = 0;
        }
        pos += FastIntConverter.intToUtf8(values[i], buffer, pos);
      }

      byte[] lastFragment = fragments[fragments.length - 1];
      if (pos + lastFragment.length > buffer.length) {
        os.write(buffer, 0, pos);
        os.write(lastFragment);
      } else {
        System.arraycopy(lastFragment, 0, buffer, pos, lastFragment.length);
        os.write(buffer, 0, pos + lastFragment.length);
      }
    } catch (IOException e) {
      throw new XMLStreamException("Failed to write template", e);
    }
  }

  /**
   * Writes the template to the provided stream, filling the holes with the given values.
   * Supports Integer, Long, String, and Enum.
   *
   * @param xsw the XML stream writer
   * @param values the values to inject into the holes
   * @throws XMLStreamException if writing fails
   */
  public void writeObjects(AfpXmlStreamWriter xsw, Object... values) throws XMLStreamException {
    if (values.length != fragments.length - 1) {
      throw new IllegalArgumentException("Expected " + (fragments.length - 1)
          + " values, but got " + values.length);
    }

    try {
      xsw.ensureFlushed();
      OutputStream os = xsw.getUnderlyingOutputStream();
      byte[] buffer = BUFFER.get();
      int pos = 0;

      for (int i = 0; i < values.length; i++) {
        byte[] fragment = fragments[i];
        // Ensure fragment fits or write current buffer
        if (pos + fragment.length > buffer.length) {
          os.write(buffer, 0, pos);
          pos = 0;
          if (fragment.length > buffer.length) {
            os.write(fragment);
          } else {
            System.arraycopy(fragment, 0, buffer, pos, fragment.length);
            pos += fragment.length;
          }
        } else {
          System.arraycopy(fragment, 0, buffer, pos, fragment.length);
          pos += fragment.length;
        }

        Object val = values[i];
        if (val instanceof Integer intVal) {
          if (pos + 11 > buffer.length) {
            os.write(buffer, 0, pos);
            pos = 0;
          }
          pos += FastIntConverter.intToUtf8(intVal, buffer, pos);
        } else if (val instanceof Long longVal) {
          if (pos + 20 > buffer.length) {
            os.write(buffer, 0, pos);
            pos = 0;
          }
          pos += FastIntConverter.longToUtf8(longVal, buffer, pos);
        } else if (val instanceof String strVal) {
          byte[] strBytes = strVal.getBytes(StandardCharsets.UTF_8);
          if (pos + strBytes.length > buffer.length) {
            os.write(buffer, 0, pos);
            pos = 0;
            if (strBytes.length > buffer.length) {
              os.write(strBytes);
            } else {
              System.arraycopy(strBytes, 0, buffer, pos, strBytes.length);
              pos += strBytes.length;
            }
          } else {
            System.arraycopy(strBytes, 0, buffer, pos, strBytes.length);
            pos += strBytes.length;
          }
        } else if (val instanceof Enum<?> enumVal) {
          byte[] enumBytes = enumVal.name().getBytes(StandardCharsets.UTF_8);
          if (pos + enumBytes.length > buffer.length) {
            os.write(buffer, 0, pos);
            pos = 0;
            if (enumBytes.length > buffer.length) {
              os.write(enumBytes);
            } else {
              System.arraycopy(enumBytes, 0, buffer, pos, enumBytes.length);
              pos += enumBytes.length;
            }
          } else {
            System.arraycopy(enumBytes, 0, buffer, pos, enumBytes.length);
            pos += enumBytes.length;
          }
        } else if (val == null) {
          if (pos + NULL_BYTES.length > buffer.length) {
            os.write(buffer, 0, pos);
            pos = 0;
          }
          System.arraycopy(NULL_BYTES, 0, buffer, pos, NULL_BYTES.length);
          pos += NULL_BYTES.length;
        } else {
          byte[] genericBytes = val.toString().getBytes(StandardCharsets.UTF_8);
          if (pos + genericBytes.length > buffer.length) {
            os.write(buffer, 0, pos);
            pos = 0;
            if (genericBytes.length > buffer.length) {
              os.write(genericBytes);
            } else {
              System.arraycopy(genericBytes, 0, buffer, pos, genericBytes.length);
              pos += genericBytes.length;
            }
          } else {
            System.arraycopy(genericBytes, 0, buffer, pos, genericBytes.length);
            pos += genericBytes.length;
          }
        }
      }

      byte[] lastFragment = fragments[fragments.length - 1];
      if (pos + lastFragment.length > buffer.length) {
        os.write(buffer, 0, pos);
        os.write(lastFragment);
      } else {
        System.arraycopy(lastFragment, 0, buffer, pos, lastFragment.length);
        os.write(buffer, 0, pos + lastFragment.length);
      }
    } catch (IOException e) {
      throw new XMLStreamException("Failed to write template", e);
    }
  }

  /**
   * Returns the number of holes in the template.
   *
   * @return the hole count
   */
  public int getHoleCount() {
    return fragments.length - 1;
  }
}
