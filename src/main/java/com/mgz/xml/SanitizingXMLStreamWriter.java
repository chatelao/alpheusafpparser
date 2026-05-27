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

import com.mgz.util.UtilCharacterEncoding;
import javax.xml.stream.XMLStreamException;
import org.codehaus.stax2.XMLStreamWriter2;
import org.codehaus.stax2.util.StreamWriter2Delegate;

/**
 * A decorator for {@link XMLStreamWriter2} that sanitizes characters for XML 1.0.
 */
public class SanitizingXMLStreamWriter extends StreamWriter2Delegate {

  public SanitizingXMLStreamWriter(XMLStreamWriter2 delegate) {
    super(delegate);
    this.mDelegate2 = delegate;
  }

  @Override
  public void writeCharacters(String text) throws XMLStreamException {
    if (UtilCharacterEncoding.needsXmlSanitization(text)) {
      super.writeCharacters(UtilCharacterEncoding.sanitizeForXml(text));
    } else {
      super.writeCharacters(text);
    }
  }

  @Override
  public void writeCharacters(char[] text, int start, int len) throws XMLStreamException {
    if (text == null || !UtilCharacterEncoding.needsXmlSanitization(text, start, len)) {
      super.writeCharacters(text, start, len);
    } else {
      char[] sanitized = UtilCharacterEncoding.sanitizeForXml(text, start, len);
      super.writeCharacters(sanitized, 0, sanitized.length);
    }
  }

  @Override
  public void writeAttribute(String localName, String value) throws XMLStreamException {
    if (UtilCharacterEncoding.needsXmlSanitization(value)) {
      super.writeAttribute(localName, UtilCharacterEncoding.sanitizeForXml(value));
    } else {
      super.writeAttribute(localName, value);
    }
  }

  @Override
  public void writeAttribute(String namespaceURI, String localName, String value) throws XMLStreamException {
    if (UtilCharacterEncoding.needsXmlSanitization(value)) {
      super.writeAttribute(namespaceURI, localName, UtilCharacterEncoding.sanitizeForXml(value));
    } else {
      super.writeAttribute(namespaceURI, localName, value);
    }
  }

  @Override
  public void writeAttribute(String prefix, String namespaceURI, String localName, String value) throws XMLStreamException {
    if (UtilCharacterEncoding.needsXmlSanitization(value)) {
      super.writeAttribute(prefix, namespaceURI, localName, UtilCharacterEncoding.sanitizeForXml(value));
    } else {
      super.writeAttribute(prefix, namespaceURI, localName, value);
    }
  }

  @Override
  public void writeCData(String data) throws XMLStreamException {
    if (UtilCharacterEncoding.needsXmlSanitization(data)) {
      super.writeCData(UtilCharacterEncoding.sanitizeForXml(data));
    } else {
      super.writeCData(data);
    }
  }
}
