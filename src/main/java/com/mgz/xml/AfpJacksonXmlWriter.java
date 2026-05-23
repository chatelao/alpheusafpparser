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

import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.fasterxml.jackson.dataformat.xml.ser.ToXmlGenerator;
import com.mgz.afp.base.StructuredField;
import com.mgz.afp.base.StructuredFieldBaseData;
import com.mgz.afp.modca.NOP_NoOperation;
import com.mgz.util.MnemonicPerformanceMonitor;
import com.mgz.afp.modca.BAG_BeginActiveEnvironmentGroup;
import com.mgz.afp.modca.TLE_TagLogicalElement;
import com.mgz.afp.ptoca.PTX_PresentationTextData;
import com.mgz.afp.ptoca.controlSequence.PTOCAControlSequence;
import com.mgz.afp.triplets.Triplet;
import com.mgz.util.UtilCharacterEncoding;
import java.io.OutputStream;
import java.util.List;
import javax.xml.namespace.QName;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamWriter;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMResult;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathFactory;
import org.w3c.dom.Document;

/**
 * A streaming XML writer for AFP structured fields using Jackson XML.
 * It uses StAX for the document structure and Jackson for individual fields.
 */
public class AfpJacksonXmlWriter implements AutoCloseable {

  private static final XMLOutputFactory XOF = new com.fasterxml.aalto.stax.OutputFactoryImpl();
  private static final DocumentBuilderFactory DBF = DocumentBuilderFactory.newInstance();
  private static final XPathFactory XPF = XPathFactory.newInstance();
  private static final TransformerFactory TF = TransformerFactory.newInstance();

  private final XMLStreamWriter xsw;
  private final OutputStream os;
  private final String xpathExpression;
  private final XmlMapper mapper;
  private final XmlMapper fragmentMapper;

  private javax.xml.parsers.DocumentBuilder cachedDocumentBuilder;
  private javax.xml.xpath.XPath cachedXPath;
  private javax.xml.transform.Transformer cachedTransformer;

  /**
   * Constructor for AfpJacksonXmlWriter.
   *
   * @param os the output stream to write to
   * @throws Exception if initialization fails
   */
  public AfpJacksonXmlWriter(OutputStream os) throws Exception {
    this(os, null);
  }

  /**
   * Constructor for AfpJacksonXmlWriter with XPath filtering.
   *
   * @param os the output stream to write to
   * @param xpathExpression the XPath expression to filter fields
   * @throws Exception if initialization fails
   */
  public AfpJacksonXmlWriter(OutputStream os, String xpathExpression) throws Exception {
    this.os = os;
    this.xpathExpression = (xpathExpression == null || xpathExpression.isBlank()) ? null : xpathExpression;
    this.mapper = JacksonXmlMapperProvider.getMapper();
    // Fragment mapper to avoid repeated XML declarations
    this.fragmentMapper = mapper.copy();
    this.fragmentMapper.configure(ToXmlGenerator.Feature.WRITE_XML_DECLARATION, false);

    if (this.xpathExpression == null) {
      XMLStreamWriter baseWriter = XOF.createXMLStreamWriter(os, "UTF-8");
      this.xsw = MnemonicPerformanceMonitor.isEnabled() ? new MnemonicXMLStreamWriter(baseWriter) : baseWriter;
      this.xsw.writeStartDocument("UTF-8", "1.0");
      this.xsw.writeCharacters("\n");
      this.xsw.setPrefix("xsi", "http://www.w3.org/2001/XMLSchema-instance");
      this.xsw.writeStartElement("AFPDocument");
      this.xsw.writeNamespace("xsi", "http://www.w3.org/2001/XMLSchema-instance");
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
    xsw.writeCharacters("  ");
    if (sf instanceof NOP_NoOperation nop) {
      writeNOPDirectly(nop);
    } else if (sf instanceof TLE_TagLogicalElement tle) {
      writeTLEDirectly(tle);
    } else if (sf instanceof BAG_BeginActiveEnvironmentGroup bag) {
      writeBAGDirectly(bag);
    } else if (sf instanceof PTX_PresentationTextData ptx) {
      writePTXDirectly(ptx);
    } else {
      String rootName = sf.getClass().getSimpleName();
      ToXmlGenerator g = (ToXmlGenerator) fragmentMapper.getFactory().createGenerator(xsw);
      fragmentMapper.writer().withRootName(rootName).writeValue(g, sf);
    }
    xsw.writeCharacters("\n");
  }

  private void writeNOPDirectly(NOP_NoOperation nop) throws Exception {
    String text = nop.getText();
    if (text == null || text.isEmpty()) {
      xsw.writeEmptyElement("NOP_NoOperation");
    } else {
      xsw.writeStartElement("NOP_NoOperation");
      xsw.writeCharacters("\n    ");
      xsw.writeStartElement("text");
      xsw.writeCharacters(text);
      xsw.writeEndElement();
      xsw.writeCharacters("\n  ");
      xsw.writeEndElement();
    }
  }

  private void writeTLEDirectly(TLE_TagLogicalElement tle) throws Exception {
    xsw.writeStartElement("TLE_TagLogicalElement");
    writeTripletsAndText(tle.getTriplets(), tle.getText());
    xsw.writeEndElement();
  }

  private void writeBAGDirectly(BAG_BeginActiveEnvironmentGroup bag) throws Exception {
    xsw.writeStartElement("BAG_BeginActiveEnvironmentGroup");
    writeTripletsAndText(bag.getTriplets(), bag.getText());
    xsw.writeEndElement();
  }

  private void writeTripletsAndText(List<Triplet> triplets, String text) throws Exception {
    if (triplets != null) {
      for (Triplet triplet : triplets) {
        xsw.writeCharacters("\n    ");
        writeTriplet(triplet);
      }
    }
    if (text != null && !text.isEmpty()) {
      xsw.writeCharacters("\n    ");
      xsw.writeStartElement("text");
      xsw.writeCharacters(text);
      xsw.writeEndElement();
    }
    xsw.writeCharacters("\n  ");
  }

  private void writeTriplet(Triplet triplet) throws Exception {
    if (triplet instanceof Triplet.FullyQualifiedName fqn) {
      xsw.writeStartElement("FullyQualifiedName");
      writeElement("\n      ", "type", fqn.getType().name());
      writeElement("\n      ", "format", fqn.getFormat().name());
      writeElement("\n      ", "nameAsString", fqn.getNameAsString());
      writeElement("\n      ", "text", fqn.getText());
      xsw.writeCharacters("\n    ");
      xsw.writeEndElement();
    } else if (triplet instanceof Triplet.AttributeValue av) {
      xsw.writeStartElement("AttributeValue");
      writeElement("\n      ", "attributeValue", av.getAttributeValue());
      writeElement("\n      ", "text", av.getText());
      xsw.writeCharacters("\n    ");
      xsw.writeEndElement();
    } else {
      // Fallback to Jackson for other triplets
      ToXmlGenerator g = (ToXmlGenerator) fragmentMapper.getFactory().createGenerator(xsw);
      fragmentMapper.writer().withRootName(triplet.getClass().getSimpleName()).writeValue(g, triplet);
    }
  }

  private void writePTXDirectly(PTX_PresentationTextData ptx) throws Exception {
    xsw.writeStartElement("PTX_PresentationTextData");
    List<PTOCAControlSequence> sequences = ptx.getControlSequences();
    if (sequences != null) {
      for (PTOCAControlSequence cs : sequences) {
        xsw.writeCharacters("\n    ");
        writeControlSequence(cs);
      }
    }
    xsw.writeCharacters("\n  ");
    xsw.writeEndElement();
  }

  private void writeControlSequence(PTOCAControlSequence cs) throws Exception {
    if (cs instanceof PTOCAControlSequence.TRN_TransparentData trn) {
      xsw.writeStartElement("TRN_TransparentData");
      writeElement("\n      ", "transparentData", trn.getTransparentData());
      writeElement("\n      ", "text", trn.getText());
      xsw.writeCharacters("\n    ");
      xsw.writeEndElement();
    } else if (cs instanceof PTOCAControlSequence.GraphicCharacters gc) {
      xsw.writeStartElement("GraphicCharacters");
      writeElement("\n      ", "text", gc.getText());
      xsw.writeCharacters("\n    ");
      xsw.writeEndElement();
    } else {
      // Fallback to Jackson
      ToXmlGenerator g = (ToXmlGenerator) fragmentMapper.getFactory().createGenerator(xsw);
      fragmentMapper.writer().withRootName(cs.getClass().getSimpleName()).writeValue(g, cs);
    }
  }

  private void writeElement(String indent, String name, String value) throws Exception {
    if (value != null) {
      xsw.writeCharacters(indent);
      xsw.writeStartElement(name);
      xsw.writeCharacters(value);
      xsw.writeEndElement();
    }
  }

  private void writeFieldWithXPath(StructuredField sf) throws Exception {
    if (cachedDocumentBuilder == null) {
      cachedDocumentBuilder = DBF.newDocumentBuilder();
    }
    Document doc = cachedDocumentBuilder.newDocument();

    var root = doc.createElement("AFPDocument");
    doc.appendChild(root);

    // Use DOMResult to bridge Jackson to DOM
    DOMResult resultDom = new DOMResult(root);
    XMLStreamWriter domXsw = XOF.createXMLStreamWriter(resultDom);
    ToXmlGenerator g = (ToXmlGenerator) fragmentMapper.getFactory().createGenerator(domXsw);
    fragmentMapper.writer().withRootName(sf.getClass().getSimpleName()).writeValue(g, sf);
    domXsw.close();

    if (cachedXPath == null) {
      cachedXPath = XPF.newXPath();
    }
    Object result = cachedXPath.evaluate(xpathExpression, doc, XPathConstants.NODESET);
    var nodes = (org.w3c.dom.NodeList) result;

    if (nodes.getLength() > 0) {
      if (cachedTransformer == null) {
        cachedTransformer = TF.newTransformer();
        cachedTransformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
        cachedTransformer.setOutputProperty(OutputKeys.INDENT, "yes");
      }

      for (int i = 0; i < nodes.getLength(); i++) {
        var node = nodes.item(i);
        cachedTransformer.transform(new DOMSource(node), new StreamResult(os));
        os.write('\n');
      }
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
