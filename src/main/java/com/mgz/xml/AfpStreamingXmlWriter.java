/*
Copyright 2024 Rudolf Fiala

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

import com.mgz.afp.base.StructuredField;
import java.io.OutputStream;
import java.util.ArrayList;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.Marshaller;
import javax.xml.namespace.QName;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamWriter;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathFactory;
import org.w3c.dom.Document;

/**
 * A streaming XML writer for AFP structured fields.
 * It uses StAX for the document structure and JAXB for individual fields.
 */
public class AfpStreamingXmlWriter implements AutoCloseable {

  private static final XMLOutputFactory XOF = XMLOutputFactory.newInstance();
  private static final DocumentBuilderFactory DBF = DocumentBuilderFactory.newInstance();
  private static final XPathFactory XPF = XPathFactory.newInstance();
  private static final TransformerFactory TF = TransformerFactory.newInstance();

  private final XMLStreamWriter xsw;
  private final OutputStream os;
  private final String xpathExpression;

  /**
   * Constructor for AfpStreamingXmlWriter.
   *
   * @param os the output stream to write to
   * @throws Exception if initialization fails
   */
  public AfpStreamingXmlWriter(OutputStream os) throws Exception {
    this(os, null);
  }

  /**
   * Constructor for AfpStreamingXmlWriter with XPath filtering.
   *
   * @param os the output stream to write to
   * @param xpathExpression the XPath expression to filter fields
   * @throws Exception if initialization fails
   */
  public AfpStreamingXmlWriter(OutputStream os, String xpathExpression) throws Exception {
    this.os = os;
    this.xpathExpression = (xpathExpression == null || xpathExpression.isBlank()) ? null : xpathExpression;
    if (this.xpathExpression == null) {
      this.xsw = XOF.createXMLStreamWriter(os, "UTF-8");
      this.xsw.writeStartDocument("UTF-8", "1.0");
      this.xsw.writeCharacters("\n");
      this.xsw.writeStartElement("AFPDocument");
      this.xsw.writeCharacters("\n");
    } else {
      this.xsw = null;
    }
  }

  /**
   * Writes a single structured field to the XML output.
   *
   * @param sf the structured field to write
   * @throws Exception if writing fails
   */
  public void writeField(StructuredField sf) throws Exception {
    if (xpathExpression != null) {
      writeFieldWithXPath(sf);
    } else {
      writeFieldDirectly(sf);
    }
  }

  private void writeFieldDirectly(StructuredField sf) throws Exception {
    var classes = new ArrayList<Class<?>>();
    classes.add(sf.getClass());
    Afp2XmlWriter.addClassesFromSF(classes, sf);

    JAXBContext jaxbContext = Afp2XmlWriter.getCachedJaxbContext(classes);
    Marshaller marshaller = Afp2XmlWriter.acquireMarshaller(jaxbContext);
    try {
      marshaller.setProperty(Marshaller.JAXB_FRAGMENT, true);
      marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

      var qualifiedName = new QName(sf.getClass().getSimpleName());
      var root = new JAXBElement<>(qualifiedName, Object.class, sf);

      xsw.writeCharacters("  ");
      marshaller.marshal(root, xsw);
      xsw.writeCharacters("\n");
    } finally {
      Afp2XmlWriter.releaseMarshaller(jaxbContext, marshaller);
    }
  }

  @SuppressWarnings("unchecked")
  private void writeFieldWithXPath(StructuredField sf) throws Exception {
    var classes = new ArrayList<Class<?>>();
    classes.add(com.mgz.afp.base.AFPDocument.class);
    classes.add(sf.getClass());
    Afp2XmlWriter.addClassesFromSF(classes, sf);

    JAXBContext jaxbContext = Afp2XmlWriter.getCachedJaxbContext(classes);
    Marshaller marshaller = Afp2XmlWriter.acquireMarshaller(jaxbContext);

    try {
      Document doc = DBF.newDocumentBuilder().newDocument();
      com.mgz.afp.base.AFPDocument afpDoc = new com.mgz.afp.base.AFPDocument();
      var qualifiedName = new QName(sf.getClass().getSimpleName());
      var element = new JAXBElement<>(qualifiedName, (Class<StructuredField>) sf.getClass(), sf);
      afpDoc.addStructuredField(element);
      marshaller.marshal(afpDoc, doc);

      var xpath = XPF.newXPath();
      // We evaluate the XPath against a temporary AFPDocument containing only the current field.
      // This allows absolute paths like /AFPDocument/TLE to work as they did in non-streaming mode.
      Object result = xpath.evaluate(xpathExpression, doc, XPathConstants.NODESET);
      var nodes = (org.w3c.dom.NodeList) result;

      if (nodes.getLength() > 0) {
        var transformer = TF.newTransformer();
        transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");

        for (int i = 0; i < nodes.getLength(); i++) {
          var node = nodes.item(i);
          transformer.transform(new DOMSource(node), new StreamResult(os));
          os.write('\n');
        }
      }
    } finally {
      Afp2XmlWriter.releaseMarshaller(jaxbContext, marshaller);
    }
  }

  @Override
  public void close() throws Exception {
    if (xsw != null) {
      xsw.writeEndElement();
      xsw.writeCharacters("\n");
      xsw.writeEndDocument();
      xsw.flush();
      xsw.close();
    }
  }
}
