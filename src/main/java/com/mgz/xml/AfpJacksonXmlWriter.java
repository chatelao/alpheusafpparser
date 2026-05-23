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
import com.mgz.afp.modca.BAG_BeginActiveEnvironmentGroup;
import com.mgz.afp.modca.NOP_NoOperation;
import com.mgz.afp.modca.TLE_TagLogicalElement;
import com.mgz.afp.ptoca.PTX_PresentationTextData;
import com.mgz.afp.ptoca.controlSequence.PTOCAControlSequence;
import com.mgz.afp.triplets.Triplet;
import com.mgz.util.MnemonicPerformanceMonitor;
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
  private ToXmlGenerator fragmentGenerator;

  private javax.xml.parsers.DocumentBuilder cachedDocumentBuilder;
  private javax.xml.xpath.XPath cachedXpath;
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
    boolean isPtx = sf instanceof PTX_PresentationTextData;
    long startTime = (isPtx && com.mgz.util.PTXPerformanceMonitor.isEnabled()) ? System.nanoTime() : 0;
    try {
      if (xpathExpression != null) {
        writeFieldWithXpath(sf);
      } else {
        writeFieldDirectly(sf);
      }
    } finally {
      if (startTime > 0) {
        com.mgz.util.PTXPerformanceMonitor.recordPtxWrite(System.nanoTime() - startTime);
      }
    }
  }

  private void writeFieldDirectly(StructuredField sf) throws Exception {
    xsw.writeCharacters("  ");
    if (sf instanceof NOP_NoOperation nop) {
      writeNopDirectly(nop);
    } else if (sf instanceof TLE_TagLogicalElement tle) {
      writeTleDirectly(tle);
    } else if (sf instanceof BAG_BeginActiveEnvironmentGroup bag) {
      writeBagDirectly(bag);
    } else if (sf instanceof PTX_PresentationTextData ptx) {
      writePtxDirectly(ptx);
    } else {
      String rootName = sf.getClass().getSimpleName();
      fragmentMapper.writer().withRootName(rootName).writeValue(getFragmentGenerator(), sf);
    }
    xsw.writeCharacters("\n");
  }

  private void writeNopDirectly(NOP_NoOperation nop) throws Exception {
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

  private void writeTleDirectly(TLE_TagLogicalElement tle) throws Exception {
    xsw.writeStartElement("TLE_TagLogicalElement");
    writeTripletsAndText(tle.getTriplets(), tle.getText());
    xsw.writeEndElement();
  }

  private void writeBagDirectly(BAG_BeginActiveEnvironmentGroup bag) throws Exception {
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
      fragmentMapper.writer().withRootName(triplet.getClass().getSimpleName()).writeValue(getFragmentGenerator(), triplet);
    }
  }

  private void writePtxDirectly(PTX_PresentationTextData ptx) throws Exception {
    xsw.writeStartElement("PTX_PresentationTextData");
    List<PTOCAControlSequence> sequences = ptx.getControlSequences();
    if (sequences != null) {
      boolean ptxDebug = com.mgz.util.PTXPerformanceMonitor.isEnabled();
      for (PTOCAControlSequence cs : sequences) {
        xsw.writeCharacters("\n    ");
        long csStart = ptxDebug ? System.nanoTime() : 0;
        writeControlSequence(cs);
        if (csStart > 0) {
          com.mgz.util.PTXPerformanceMonitor.recordPtocaWrite(cs.getClass().getSimpleName(), System.nanoTime() - csStart);
        }
      }
    }
    xsw.writeCharacters("\n  ");
    xsw.writeEndElement();
  }

  private void writeControlSequence(PTOCAControlSequence cs) throws Exception {
    if (cs instanceof PTOCAControlSequence.TRN_TransparentData trn) {
      xsw.writeStartElement("TRN_TransparentData");
      writeElement("\n      ", "text", trn.getText());
      xsw.writeCharacters("\n    ");
      xsw.writeEndElement();
    } else if (cs instanceof PTOCAControlSequence.GraphicCharacters gc) {
      xsw.writeStartElement("GraphicCharacters");
      writeElement("\n      ", "text", gc.getText());
      xsw.writeCharacters("\n    ");
      xsw.writeEndElement();
    } else if (cs instanceof PTOCAControlSequence.AMI_AbsoluteMoveInline ami) {
      xsw.writeStartElement("AMI_AbsoluteMoveInline");
      writeElement("\n      ", "displacement", String.valueOf(ami.getDisplacement()));
      xsw.writeCharacters("\n    ");
      xsw.writeEndElement();
    } else if (cs instanceof PTOCAControlSequence.AMB_AbsoluteMoveBaseline amb) {
      xsw.writeStartElement("AMB_AbsoluteMoveBaseline");
      writeElement("\n      ", "displacement", String.valueOf(amb.getDisplacement()));
      xsw.writeCharacters("\n    ");
      xsw.writeEndElement();
    } else if (cs instanceof PTOCAControlSequence.SCFL_SetCodedFontLocal scfl) {
      xsw.writeStartElement("SCFL_SetCodedFontLocal");
      writeElement("\n      ", "codedFontLocalID", String.valueOf(scfl.getCodedFontLocalID()));
      xsw.writeCharacters("\n    ");
      xsw.writeEndElement();
    } else if (cs instanceof PTOCAControlSequence.STO_SetTextOrientation sto) {
      xsw.writeStartElement("STO_SetTextOrientation");
      writeElement("\n      ", "xOrientation", sto.getxOrientation().name());
      writeElement("\n      ", "yOrientation", sto.getyOrientation().name());
      xsw.writeCharacters("\n    ");
      xsw.writeEndElement();
    } else {
      // Fallback to Jackson
      fragmentMapper.writer().withRootName(cs.getClass().getSimpleName()).writeValue(getFragmentGenerator(), cs);
    }
  }

  private ToXmlGenerator getFragmentGenerator() throws java.io.IOException {
    if (fragmentGenerator == null) {
      fragmentGenerator = (ToXmlGenerator) fragmentMapper.getFactory().createGenerator(xsw);
    }
    return fragmentGenerator;
  }

  private void writeElement(String indent, String name, String value) throws Exception {
    if (value != null) {
      xsw.writeCharacters(indent);
      xsw.writeStartElement(name);
      xsw.writeCharacters(value);
      xsw.writeEndElement();
    }
  }

  private void writeFieldWithXpath(StructuredField sf) throws Exception {
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

    if (cachedXpath == null) {
      cachedXpath = XPF.newXPath();
    }
    Object result = cachedXpath.evaluate(xpathExpression, doc, XPathConstants.NODESET);
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
    if (fragmentGenerator != null) {
      fragmentGenerator.flush();
    }
    if (xsw != null) {
      xsw.writeEndElement();
      xsw.writeCharacters("\n");
      xsw.writeEndDocument();
      xsw.flush();
      xsw.close();
    }
  }
}
