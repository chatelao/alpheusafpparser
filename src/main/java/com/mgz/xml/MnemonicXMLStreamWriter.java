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

import com.mgz.util.MnemonicPerformanceMonitor;
import javax.xml.namespace.NamespaceContext;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;
import java.util.ArrayDeque;
import java.util.Deque;

/**
 * A decorator for {@link XMLStreamWriter} that measures the time spent writing each element.
 */
public class MnemonicXMLStreamWriter implements XMLStreamWriter {

  private final XMLStreamWriter delegate;
  private final Deque<String> elementStack = new ArrayDeque<>();

  public MnemonicXMLStreamWriter(XMLStreamWriter delegate) {
    this.delegate = delegate;
  }

  @Override
  public void writeStartElement(String localName) throws XMLStreamException {
    String mnemonic = MnemonicPerformanceMonitor.extractMnemonic(localName);
    MnemonicPerformanceMonitor.startWrite(localName);
    elementStack.push(mnemonic != null ? mnemonic : "");
    delegate.writeStartElement(localName);
  }

  @Override
  public void writeStartElement(String namespaceURI, String localName) throws XMLStreamException {
    String mnemonic = MnemonicPerformanceMonitor.extractMnemonic(localName);
    MnemonicPerformanceMonitor.startWrite(localName);
    elementStack.push(mnemonic != null ? mnemonic : "");
    delegate.writeStartElement(namespaceURI, localName);
  }

  @Override
  public void writeStartElement(String prefix, String localName, String namespaceURI)
      throws XMLStreamException {
    String mnemonic = MnemonicPerformanceMonitor.extractMnemonic(localName);
    MnemonicPerformanceMonitor.startWrite(localName);
    elementStack.push(mnemonic != null ? mnemonic : "");
    delegate.writeStartElement(prefix, localName, namespaceURI);
  }

  @Override
  public void writeEmptyElement(String namespaceURI, String localName) throws XMLStreamException {
    MnemonicPerformanceMonitor.startWrite(localName);
    delegate.writeEmptyElement(namespaceURI, localName);
    MnemonicPerformanceMonitor.endWrite();
  }

  @Override
  public void writeEmptyElement(String prefix, String localName, String namespaceURI)
      throws XMLStreamException {
    MnemonicPerformanceMonitor.startWrite(localName);
    delegate.writeEmptyElement(prefix, localName, namespaceURI);
    MnemonicPerformanceMonitor.endWrite();
  }

  @Override
  public void writeEmptyElement(String localName) throws XMLStreamException {
    MnemonicPerformanceMonitor.startWrite(localName);
    delegate.writeEmptyElement(localName);
    MnemonicPerformanceMonitor.endWrite();
  }

  @Override
  public void writeEndElement() throws XMLStreamException {
    delegate.writeEndElement();
    if (!elementStack.isEmpty()) {
      String mnemonic = elementStack.pop();
      if (!mnemonic.isEmpty()) {
        MnemonicPerformanceMonitor.endWrite();
      }
    }
  }

  @Override
  public void writeEndDocument() throws XMLStreamException {
    delegate.writeEndDocument();
  }

  @Override
  public void close() throws XMLStreamException {
    delegate.close();
  }

  @Override
  public void flush() throws XMLStreamException {
    delegate.flush();
  }

  @Override
  public void writeAttribute(String localName, String value) throws XMLStreamException {
    delegate.writeAttribute(localName, value);
  }

  @Override
  public void writeAttribute(String prefix, String namespaceURI, String localName, String value)
      throws XMLStreamException {
    delegate.writeAttribute(prefix, namespaceURI, localName, value);
  }

  @Override
  public void writeAttribute(String namespaceURI, String localName, String value)
      throws XMLStreamException {
    delegate.writeAttribute(namespaceURI, localName, value);
  }

  @Override
  public void writeNamespace(String prefix, String namespaceURI) throws XMLStreamException {
    delegate.writeNamespace(prefix, namespaceURI);
  }

  @Override
  public void writeDefaultNamespace(String namespaceURI) throws XMLStreamException {
    delegate.writeDefaultNamespace(namespaceURI);
  }

  @Override
  public void writeComment(String data) throws XMLStreamException {
    delegate.writeComment(data);
  }

  @Override
  public void writeProcessingInstruction(String target) throws XMLStreamException {
    delegate.writeProcessingInstruction(target);
  }

  @Override
  public void writeProcessingInstruction(String target, String data) throws XMLStreamException {
    delegate.writeProcessingInstruction(target, data);
  }

  @Override
  public void writeCData(String data) throws XMLStreamException {
    delegate.writeCData(data);
  }

  @Override
  public void writeDTD(String dtd) throws XMLStreamException {
    delegate.writeDTD(dtd);
  }

  @Override
  public void writeEntityRef(String name) throws XMLStreamException {
    delegate.writeEntityRef(name);
  }

  @Override
  public void writeStartDocument() throws XMLStreamException {
    delegate.writeStartDocument();
  }

  @Override
  public void writeStartDocument(String version) throws XMLStreamException {
    delegate.writeStartDocument(version);
  }

  @Override
  public void writeStartDocument(String encoding, String version) throws XMLStreamException {
    delegate.writeStartDocument(encoding, version);
  }

  @Override
  public void writeCharacters(String text) throws XMLStreamException {
    delegate.writeCharacters(text);
  }

  @Override
  public void writeCharacters(char[] text, int start, int len) throws XMLStreamException {
    delegate.writeCharacters(text, start, len);
  }

  @Override
  public String getPrefix(String uri) throws XMLStreamException {
    return delegate.getPrefix(uri);
  }

  @Override
  public void setPrefix(String prefix, String uri) throws XMLStreamException {
    delegate.setPrefix(prefix, uri);
  }

  @Override
  public void setDefaultNamespace(String uri) throws XMLStreamException {
    delegate.setDefaultNamespace(uri);
  }

  @Override
  public void setNamespaceContext(NamespaceContext context) throws XMLStreamException {
    delegate.setNamespaceContext(context);
  }

  @Override
  public NamespaceContext getNamespaceContext() {
    return delegate.getNamespaceContext();
  }

  @Override
  public Object getProperty(String name) throws IllegalArgumentException {
    return delegate.getProperty(name);
  }
}
