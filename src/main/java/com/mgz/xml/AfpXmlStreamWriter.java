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
  private boolean needsFlush = false;

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

  @Override
  public void writeStartElement(String localName) throws XMLStreamException {
    needsFlush = true;
    super.writeStartElement(localName);
  }

  @Override
  public void writeStartElement(String ns, String localName) throws XMLStreamException {
    needsFlush = true;
    super.writeStartElement(ns, localName);
  }

  @Override
  public void writeStartElement(String prefix, String localName, String ns) throws XMLStreamException {
    needsFlush = true;
    super.writeStartElement(prefix, localName, ns);
  }

  @Override
  public void writeEmptyElement(String localName) throws XMLStreamException {
    needsFlush = true;
    super.writeEmptyElement(localName);
  }

  @Override
  public void writeEmptyElement(String ns, String localName) throws XMLStreamException {
    needsFlush = true;
    super.writeEmptyElement(ns, localName);
  }

  @Override
  public void writeEmptyElement(String prefix, String localName, String ns) throws XMLStreamException {
    needsFlush = true;
    super.writeEmptyElement(prefix, localName, ns);
  }

  @Override
  public void writeEndElement() throws XMLStreamException {
    needsFlush = true;
    super.writeEndElement();
  }

  @Override
  public void writeAttribute(String localName, String value) throws XMLStreamException {
    needsFlush = true;
    super.writeAttribute(localName, value);
  }

  @Override
  public void writeAttribute(String ns, String localName, String value) throws XMLStreamException {
    needsFlush = true;
    super.writeAttribute(ns, localName, value);
  }

  @Override
  public void writeAttribute(String prefix, String ns, String localName, String value) throws XMLStreamException {
    needsFlush = true;
    super.writeAttribute(prefix, ns, localName, value);
  }

  @Override
  public void writeCharacters(String text) throws XMLStreamException {
    needsFlush = true;
    super.writeCharacters(text);
  }

  @Override
  public void writeCharacters(char[] text, int start, int len) throws XMLStreamException {
    needsFlush = true;
    super.writeCharacters(text, start, len);
  }

  @Override
  public void writeCData(String data) throws XMLStreamException {
    needsFlush = true;
    super.writeCData(data);
  }

  @Override
  public void writeComment(String data) throws XMLStreamException {
    needsFlush = true;
    super.writeComment(data);
  }

  @Override
  public void writeRaw(String text) throws XMLStreamException {
    needsFlush = true;
    super.writeRaw(text);
  }

  @Override
  public void writeRaw(String text, int start, int offset) throws XMLStreamException {
    needsFlush = true;
    super.writeRaw(text, start, offset);
  }

  @Override
  public void writeRaw(char[] text, int offset, int length) throws XMLStreamException {
    needsFlush = true;
    super.writeRaw(text, offset, length);
  }

  @Override
  public void writeInt(int value) throws XMLStreamException {
    needsFlush = true;
    super.writeInt(value);
  }

  @Override
  public void writeLong(long value) throws XMLStreamException {
    needsFlush = true;
    super.writeLong(value);
  }

  @Override
  public void writeBoolean(boolean value) throws XMLStreamException {
    needsFlush = true;
    super.writeBoolean(value);
  }

  @Override
  public void writeFloat(float value) throws XMLStreamException {
    needsFlush = true;
    super.writeFloat(value);
  }

  @Override
  public void writeDouble(double value) throws XMLStreamException {
    needsFlush = true;
    super.writeDouble(value);
  }

  @Override
  public void writeBooleanAttribute(String prefix, String ns, String localName, boolean value) throws XMLStreamException {
    needsFlush = true;
    super.writeBooleanAttribute(prefix, ns, localName, value);
  }

  @Override
  public void writeIntAttribute(String prefix, String ns, String localName, int value) throws XMLStreamException {
    needsFlush = true;
    super.writeIntAttribute(prefix, ns, localName, value);
  }

  @Override
  public void writeLongAttribute(String prefix, String ns, String localName, long value) throws XMLStreamException {
    needsFlush = true;
    super.writeLongAttribute(prefix, ns, localName, value);
  }

  @Override
  public void writeFloatAttribute(String prefix, String ns, String localName, float value) throws XMLStreamException {
    needsFlush = true;
    super.writeFloatAttribute(prefix, ns, localName, value);
  }

  @Override
  public void writeDoubleAttribute(String prefix, String ns, String localName, double value) throws XMLStreamException {
    needsFlush = true;
    super.writeDoubleAttribute(prefix, ns, localName, value);
  }

  @Override
  public void writeBinaryAttribute(String prefix, String ns, String localName, byte[] value) throws XMLStreamException {
    needsFlush = true;
    super.writeBinaryAttribute(prefix, ns, localName, value);
  }

  /**
   * Writes raw bytes directly to the underlying output stream, flushing Woodstox first if necessary.
   *
   * @param bytes the bytes to write
   * @param offset the offset in the byte array
   * @param len the number of bytes to write
   * @throws XMLStreamException if writing fails
   */
  public void writeRawBytes(byte[] bytes, int offset, int len) throws XMLStreamException {
    if (len <= 0) {
      return;
    }
    try {
      if (needsFlush) {
        delegate.writeRaw(""); // Force closure of pending start tag
        delegate.flush();
        needsFlush = false;
      }
      OutputStream target = (woodstoxOs != null) ? woodstoxOs : fallbackOs;
      target.write(bytes, offset, len);
    } catch (IOException e) {
      throw new XMLStreamException("Failed to write raw bytes to stream", e);
    }
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
      if (needsFlush) {
        delegate.writeRaw(""); // Force closure of pending start tag
        delegate.flush();
        needsFlush = false;
      }

      OutputStream target = (woodstoxOs != null) ? woodstoxOs : fallbackOs;
      EbcdicToUtf8XmlEncoder.encodeAndWrite(data, offset, len, target, charset);
    } catch (IOException e) {
      throw new XMLStreamException("Failed to write direct EBCDIC stream", e);
    }
  }
}
