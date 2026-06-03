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

import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.nio.charset.Charset;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.stream.XMLStreamException;
import org.codehaus.stax2.XMLStreamWriter2;

/**
 * An specialized XML stream writer for AFP that supports direct EBCDIC-to-UTF8 stream encoding.
 * It attempts to write directly to the underlying output stream of the Woodstox writer for maximum performance.
 */
public class AfpXmlStreamWriter extends SanitizingXMLStreamWriter {

  private static final Logger LOGGER = Logger.getLogger(AfpXmlStreamWriter.class.getName());

  private final XMLStreamWriter2 delegate;
  private final OutputStream fallbackOs;
  private OutputStream woodstoxOs;

  public AfpXmlStreamWriter(XMLStreamWriter2 delegate, OutputStream os) {
    super(delegate);
    this.delegate = delegate;
    this.fallbackOs = os;
    this.woodstoxOs = findWoodstoxOutputStream(delegate);
  }

  private static OutputStream findWoodstoxOutputStream(XMLStreamWriter2 sw) {
    try {
      // Woodstox hierarchy: RepairingNsStreamWriter -> (mWriter) BufferingXmlWriter -> (mUnderlyingStream) OutputStream
      Field mWriterField = findField(sw.getClass(), "mWriter");
      if (mWriterField != null) {
        mWriterField.setAccessible(true);
        Object mWriter = mWriterField.get(sw);
        if (mWriter != null) {
          Field mUnderlyingStreamField = findField(mWriter.getClass(), "mUnderlyingStream");
          if (mUnderlyingStreamField != null) {
            mUnderlyingStreamField.setAccessible(true);
            Object os = mUnderlyingStreamField.get(mWriter);
            if (os instanceof OutputStream) {
              return (OutputStream) os;
            }
          }
        }
      }
    } catch (Exception e) {
      LOGGER.log(Level.FINE, "Could not extract Woodstox OutputStream via reflection", e);
    }
    return null;
  }

  private static Field findField(Class<?> clazz, String fieldName) {
    Class<?> current = clazz;
    while (current != null) {
      try {
        return current.getDeclaredField(fieldName);
      } catch (NoSuchFieldException e) {
        current = current.getSuperclass();
      }
    }
    return null;
  }

  /**
   * Writes EBCDIC data directly to the stream after converting it to UTF-8 and performing XML escaping.
   *
   * @param data the EBCDIC data
   * @param offset offset in the data
   * @param len length of the data
   * @param charset the EBCDIC charset
   * @throws XMLStreamException if writing fails
   */
  public void writeEbcdic(byte[] data, int offset, int len, Charset charset) throws XMLStreamException {
    if (len <= 0) {
      return;
    }
    try {
      // Ensure any pending start tag is closed before writing raw bytes to the stream
      delegate.writeCharacters("");
      // Must flush delegate to ensure correct output order
      delegate.flush();

      OutputStream target = (woodstoxOs != null) ? woodstoxOs : fallbackOs;
      EbcdicToUtf8XmlEncoder.encodeAndWrite(data, offset, len, target, charset);
    } catch (IOException e) {
      throw new XMLStreamException("Failed to write direct EBCDIC stream", e);
    }
  }
}
