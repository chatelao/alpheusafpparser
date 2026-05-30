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
import com.mgz.afp.base.handler.StructuredFieldHandler;
import com.mgz.afp.base.IHasTriplets;
import com.mgz.afp.base.IRepeatingGroup;
import com.mgz.afp.base.RepeatingGroupWithTriplets;
import com.mgz.afp.base.StructuredField;
import com.mgz.afp.base.StructuredFieldBaseData;
import com.mgz.afp.foca.FNC_FontControl;
import com.mgz.afp.goca.GAD_DrawingOrder;
import com.mgz.afp.goca.GAD_GraphicsData;
import com.mgz.afp.ioca.IDD_ImageDataDescriptor;
import com.mgz.afp.ioca.IDD_SelfDefiningField;
import com.mgz.afp.ioca.IPD_ImagePictureData;
import com.mgz.afp.ioca.IPD_Segment;
import com.mgz.afp.lineData.LND_LineDescriptor;
import com.mgz.afp.modca.BAG_BeginActiveEnvironmentGroup;
import com.mgz.afp.modca.MCF_MapCodedFont_Format2;
import com.mgz.afp.modca.MIO_MapImageObject;
import com.mgz.afp.modca.NOP_NoOperation;
import com.mgz.afp.modca.OBD_ObjectAreaDescriptor;
import com.mgz.afp.modca.OBP_ObjectAreaPosition;
import com.mgz.afp.modca.TLE_TagLogicalElement;
import com.mgz.afp.ptoca.PTX_PresentationTextData;
import com.mgz.afp.ptoca.controlSequence.PTOCAControlSequence;
import com.mgz.afp.triplets.Triplet;
import com.mgz.util.MnemonicPerformanceMonitor;
import com.mgz.util.NonClosingOutputStream;
import com.mgz.util.SFSizeEstimator;
import com.mgz.util.UtilCharacterEncoding;
import java.io.ByteArrayInputStream;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.util.List;
import javax.xml.namespace.QName;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.stream.XMLOutputFactory;
import org.codehaus.stax2.XMLStreamWriter2;
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
public class AfpJacksonXmlWriter implements StructuredFieldHandler {

  private static final XMLOutputFactory XOF;
  static {
    XOF = new com.fasterxml.aalto.stax.OutputFactoryImpl();
    XOF.setProperty(XMLOutputFactory.IS_REPAIRING_NAMESPACES, true);
    try {
      // Try to disable structure validation to allow multiple roots in fragment mode
      XOF.setProperty("org.codehaus.stax2.validation.checkStructure", false);
    } catch (Exception e) {
      // Ignore if not supported
    }
  }
  private static final DocumentBuilderFactory DBF = DocumentBuilderFactory.newInstance();
  private static final XPathFactory XPF = XPathFactory.newInstance();
  private static final TransformerFactory TF = TransformerFactory.newInstance();

  private final XMLStreamWriter2 xsw;
  private final XMLStreamWriter2 baseXsw;
  private final OutputStream os;
  private final com.mgz.util.CountingOutputStream cos;
  private final String xpathExpression;
  private final boolean fragmentMode;
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
    this(os, null, false);
  }

  /**
   * Constructor for AfpJacksonXmlWriter with XPath filtering.
   *
   * @param os the output stream to write to
   * @param xpathExpression the XPath expression to filter fields
   * @throws Exception if initialization fails
   */
  public AfpJacksonXmlWriter(OutputStream os, String xpathExpression) throws Exception {
    this(os, xpathExpression, false);
  }

  /**
   * Constructor for AfpJacksonXmlWriter.
   *
   * @param os the output stream to write to
   * @param xpathExpression the XPath expression to filter fields
   * @param fragmentMode if true, skip XML declaration and root element
   * @throws Exception if initialization fails
   */
  public AfpJacksonXmlWriter(OutputStream os, String xpathExpression, boolean fragmentMode)
      throws Exception {
    this.os = os;
    this.cos = new com.mgz.util.CountingOutputStream(new NonClosingOutputStream(os));
    this.xpathExpression = (xpathExpression == null || xpathExpression.isBlank()) ? null : xpathExpression;
    this.fragmentMode = fragmentMode;
    this.mapper = JacksonXmlMapperProvider.getMapper();
    // Fragment mapper to avoid repeated XML declarations
    this.fragmentMapper = JacksonXmlMapperProvider.getFragmentMapper();

    if (fragmentMode) {
      // Ensure Aalto doesn't complain about multiple roots when we are just writing fragments
      // But the generator is created from xsw, which is an Aalto XMLStreamWriter.
    }

    if (this.xpathExpression == null) {
      XMLStreamWriter2 rawXsw = (XMLStreamWriter2) XOF.createXMLStreamWriter(cos, "UTF-8");
      this.baseXsw = new SanitizingXMLStreamWriter(rawXsw);
      this.xsw = MnemonicPerformanceMonitor.isEnabled() ? new MnemonicXMLStreamWriter(this.baseXsw) : this.baseXsw;
      if (!fragmentMode) {
        this.xsw.writeStartDocument("UTF-8", "1.0");
        this.xsw.writeCharacters("\n");
        this.xsw.setPrefix("xsi", "http://www.w3.org/2001/XMLSchema-instance");
        this.xsw.writeStartElement("AFPDocument");
        this.xsw.writeNamespace("xsi", "http://www.w3.org/2001/XMLSchema-instance");
        this.xsw.writeCharacters("\n");
      } else {
        // In fragment mode, we need a root element for StAX validation and to avoid "Trying to output second root"
        this.xsw.writeStartElement("AfpFragments");
      }
    } else {
      this.xsw = null;
      this.baseXsw = null;
    }

    if (this.xsw != null) {
      this.fragmentGenerator = (ToXmlGenerator) fragmentMapper.getFactory().createGenerator(xsw);
    } else {
      this.fragmentGenerator = null;
    }
  }

  @Override
  public void handle(StructuredField sf) throws Exception {
    boolean isPtx = sf instanceof PTX_PresentationTextData;
    long startTime = (isPtx && com.mgz.util.PTXPerformanceMonitor.isEnabled()) ? System.nanoTime() : 0;
    long startCount = (isPtx && com.mgz.util.PTXPerformanceMonitor.isEnabled()) ? cos.getCount() : 0;
    try {
      if (xpathExpression != null) {
        writeFieldWithXpath(sf);
      } else {
        writeFieldDirectly(sf);
      }
    } finally {
      if (startTime > 0) {
        if (xsw != null) {
          xsw.flush();
        }
        com.mgz.util.PTXPerformanceMonitor.recordPtxWrite(System.nanoTime() - startTime, cos.getCount() - startCount);
      }
    }
  }

  /**
   * Writes a single structured field to the XML output.
   *
   * @param sf the structured field to write
   * @throws Exception if writing fails
   * @deprecated Use {@link #handle(StructuredField)} instead.
   */
  @Deprecated
  public void writeField(StructuredField sf) throws Exception {
    handle(sf);
  }

  private void writeFieldDirectly(StructuredField sf) throws Exception {
    if (!fragmentMode) {
      xsw.writeCharacters(XmlIndenter.LEVEL_1_PURE);
    }

    if (sf instanceof NOP_NoOperation nop) {
      writeNopDirectly(nop);
    } else if (sf instanceof TLE_TagLogicalElement tle) {
      writeTleDirectly(tle);
    } else if (sf instanceof BAG_BeginActiveEnvironmentGroup bag) {
      writeBagDirectly(bag);
    } else if (sf instanceof PTX_PresentationTextData ptx) {
      writePtxDirectly(ptx);
    } else if (sf instanceof MCF_MapCodedFont_Format2 mcf) {
      writeMcfDirectly(mcf);
    } else if (sf instanceof FNC_FontControl fnc) {
      writeFncDirectly(fnc);
    } else if (sf instanceof LND_LineDescriptor lnd) {
      writeLndDirectly(lnd);
    } else if (sf instanceof GAD_GraphicsData gad) {
      writeGadDirectly(gad);
    } else if (sf instanceof IPD_ImagePictureData ipd) {
      writeIpdDirectly(ipd);
    } else if (sf instanceof OBD_ObjectAreaDescriptor obd) {
      writeObdDirectly(obd);
    } else if (sf instanceof OBP_ObjectAreaPosition obp) {
      writeObpDirectly(obp);
    } else if (sf instanceof IDD_ImageDataDescriptor idd) {
      writeIddDirectly(idd);
    } else if (sf instanceof MIO_MapImageObject mio) {
      writeMioDirectly(mio);
    } else {
      String rootName = sf.getClass().getSimpleName();
      // Use Jackson to write the field.
      // Important: we must not close the generator because it would close our long-lived xsw.
      fragmentMapper.writer().withRootName(rootName).writeValue(fragmentGenerator, sf);
      // gen.flush() is called by writeValue, but it's safer to not close it.
    }

    if (!fragmentMode) {
      xsw.writeCharacters("\n");
    }
  }

  private void writeNopDirectly(NOP_NoOperation nop) throws Exception {
    String text = nop.getText();
    if (text != null && !text.isEmpty()) {
      xsw.writeStartElement("NOP_NoOperation");
      XmlIndenter.writeIndent(xsw, 2);
      xsw.writeStartElement("text");
      xsw.writeCharacters(text);
      xsw.writeEndElement();
      XmlIndenter.writeIndent(xsw, 1);
      xsw.writeEndElement();
    } else {
      byte[] data = nop.getData();
      if (data == null || data.length == 0) {
        xsw.writeEmptyElement("NOP_NoOperation");
      } else {
        xsw.writeStartElement("NOP_NoOperation");
        XmlIndenter.writeIndent(xsw, 2);
        xsw.writeStartElement("hexData");
        xsw.writeCharacters(com.mgz.util.UtilCharacterEncoding.bytesToHexString(data));
        xsw.writeEndElement();
        XmlIndenter.writeIndent(xsw, 1);
        xsw.writeEndElement();
      }
    }
  }

  private void writeTleDirectly(TLE_TagLogicalElement tle) throws Exception {
    xsw.writeStartElement("TLE_TagLogicalElement");
    writeTripletsAndText(tle.getTriplets(), tle.getText(), XmlIndenter.getIndent(2), XmlIndenter.getIndent(1));
    xsw.writeEndElement();
  }

  private void writeBagDirectly(BAG_BeginActiveEnvironmentGroup bag) throws Exception {
    xsw.writeStartElement("BAG_BeginActiveEnvironmentGroup");
    writeTripletsAndText(bag.getTriplets(), bag.getText(), XmlIndenter.getIndent(2), XmlIndenter.getIndent(1));
    xsw.writeEndElement();
  }

  private void writeTripletsAndText(List<Triplet> triplets, String text, String indent, String closingIndent) throws Exception {
    if (triplets != null) {
      for (Triplet triplet : triplets) {
        xsw.writeCharacters(indent);
        writeTriplet(triplet, indent);
      }
    }
    if (text != null && !text.isEmpty()) {
      xsw.writeCharacters(indent);
      xsw.writeStartElement("text");
      xsw.writeCharacters(text);
      xsw.writeEndElement();
    }
    xsw.writeCharacters(closingIndent);
  }

  private void writeTriplet(Triplet triplet, String indent) throws Exception {
    int level = indent.length() / 2; // Rough estimate of level if indent is spaces or \n + spaces
    if (indent.startsWith("\n")) {
      level = (indent.length() - 1) / 2;
    }
    String childIndent = XmlIndenter.getIndent(level + 1);
    if (triplet instanceof Triplet.FullyQualifiedName fqn) {
      xsw.writeStartElement("FullyQualifiedName");
      writeElement(childIndent, "type", fqn.getType().name());
      writeElement(childIndent, "format", fqn.getFormat().name());
      writeElement(childIndent, "nameAsString", fqn.getNameAsString());
      writeElement(childIndent, "text", fqn.getText());
      xsw.writeCharacters(indent);
      xsw.writeEndElement();
    } else if (triplet instanceof Triplet.AttributeValue av) {
      xsw.writeStartElement("AttributeValue");
      writeElement(childIndent, "attributeValue", av.getAttributeValue());
      writeElement(childIndent, "text", av.getText());
      xsw.writeCharacters(indent);
      xsw.writeEndElement();
    } else if (triplet instanceof Triplet.CodedGraphicCharacterSetGlobalID cgcs) {
      xsw.writeStartElement("CodedGraphicCharacterSetGlobalID");
      writeElement(childIndent, "graphicCharacterSetGlobalID", cgcs.getGraphicCharacterSetGlobalID());
      writeElement(childIndent, "codePageGlobalID_codedCharacterSetID", cgcs.getCodePageGlobalID_codedCharacterSetID());
      xsw.writeCharacters(indent);
      xsw.writeEndElement();
    } else if (triplet instanceof Triplet.MappingOption mo) {
      xsw.writeStartElement("MappingOption");
      writeElement(childIndent, "dataObjecMapingOption", mo.getDataObjecMapingOption().name());
      xsw.writeCharacters(indent);
      xsw.writeEndElement();
    } else {
      // Fallback to Jackson for other triplets
      fragmentMapper.writer().withRootName(triplet.getClass().getSimpleName()).writeValue(fragmentGenerator, triplet);
    }
  }

  private void writeMcfDirectly(MCF_MapCodedFont_Format2 mcf) throws Exception {
    xsw.writeStartElement("MCF_MapCodedFont_Format2");
    List<IRepeatingGroup> repeatingGroups = mcf.getRepeatingGroups();
    if (repeatingGroups != null) {
      for (IRepeatingGroup rg : repeatingGroups) {
        XmlIndenter.writeIndent(xsw, 2);
        writeMcfRepeatingGroup(rg, XmlIndenter.getIndent(2));
      }
    }
    XmlIndenter.writeIndent(xsw, 1);
    xsw.writeEndElement();
  }

  private void writeMcfRepeatingGroup(IRepeatingGroup rg, String indent) throws Exception {
    String childIndent = XmlIndenter.getIndent(3);
    xsw.writeStartElement("mcf2RepeatingGroup");
    if (rg instanceof RepeatingGroupWithTriplets rgt) {
      List<Triplet> triplets = rgt.getTriplets();
      if (triplets != null) {
        for (Triplet triplet : triplets) {
          xsw.writeCharacters(childIndent);
          writeTriplet(triplet, childIndent);
        }
      }
    }
    xsw.writeCharacters(indent);
    xsw.writeEndElement();
  }

  private void writePtxDirectly(PTX_PresentationTextData ptx) throws Exception {
    xsw.writeStartElement("PTX_PresentationTextData");
    List<PTOCAControlSequence> sequences = ptx.getControlSequences();
    if (sequences != null) {
      boolean ptxDebug = com.mgz.util.PTXPerformanceMonitor.isEnabled();
      for (PTOCAControlSequence cs : sequences) {
        baseXsw.writeCharacters(XmlIndenter.getIndent(2));
        long csStart = ptxDebug ? System.nanoTime() : 0;
        long csStartCount = ptxDebug ? cos.getCount() : 0;
        writeControlSequence(cs, XmlIndenter.getIndent(2));
        if (csStart > 0) {
          // Performance: We removed the xsw.flush() here. This significantly improves performance
          // but means individual PTOCA xmlSize metrics in -P mode will be inaccurate (often 0 due to buffering).
          // Aggregate PTX metrics remain accurate as handle() flushes at the end of the field.
          int payloadSize = 0;
          if (cs instanceof PTOCAControlSequence.GraphicCharacters gc) {
            payloadSize = gc.getData() != null ? gc.getData().length : 0;
          } else if (cs.getCsi() != null) {
            payloadSize = Math.max(0, cs.getCsi().getLength() - 2);
          }
          com.mgz.util.PTXPerformanceMonitor.recordPtocaWrite(
              cs.getClass().getSimpleName(), System.nanoTime() - csStart, payloadSize, cos.getCount() - csStartCount);
        }
      }
    }
    baseXsw.writeCharacters(XmlIndenter.getIndent(1));
    xsw.writeEndElement();
  }

  private void writeControlSequence(PTOCAControlSequence cs, String indent) throws Exception {
    String childIndent = XmlIndenter.getIndent(3);
    if (cs instanceof PTOCAControlSequence.TRN_TransparentData trn) {
      xsw.writeStartElement("TRN_TransparentData");
      writeElement(baseXsw, childIndent, "transparentData", trn.getTransparentData());
      writeElement(baseXsw, childIndent, "text", trn.getText());
      baseXsw.writeCharacters(indent);
      xsw.writeEndElement();
    } else if (cs instanceof PTOCAControlSequence.GraphicCharacters gc) {
      xsw.writeStartElement("GraphicCharacters");
      writeElement(baseXsw, childIndent, "text", gc.getText());
      baseXsw.writeCharacters(indent);
      xsw.writeEndElement();
    } else if (cs instanceof PTOCAControlSequence.AMI_AbsoluteMoveInline ami) {
      baseXsw.writeEmptyElement("AMI_AbsoluteMoveInline");
      baseXsw.writeAttribute("displacement", String.valueOf(ami.getDisplacement()));
    } else if (cs instanceof PTOCAControlSequence.AMB_AbsoluteMoveBaseline amb) {
      baseXsw.writeEmptyElement("AMB_AbsoluteMoveBaseline");
      baseXsw.writeAttribute("displacement", String.valueOf(amb.getDisplacement()));
    } else if (cs instanceof PTOCAControlSequence.SCFL_SetCodedFontLocal scfl) {
      baseXsw.writeEmptyElement("SCFL_SetCodedFontLocal");
      baseXsw.writeAttribute("codedFontLocalID", String.valueOf(scfl.getCodedFontLocalID()));
    } else if (cs instanceof PTOCAControlSequence.STO_SetTextOrientation sto) {
      xsw.writeStartElement("STO_SetTextOrientation");
      if (sto.getxOrientation() != null) {
        writeElement(baseXsw, childIndent, "xOrientation", sto.getxOrientation().name());
      }
      if (sto.getyOrientation() != null) {
        writeElement(baseXsw, childIndent, "yOrientation", sto.getyOrientation().name());
      }
      baseXsw.writeCharacters(indent);
      xsw.writeEndElement();
    } else if (cs instanceof PTOCAControlSequence.SIA_SetIntercharacterAdjustment sia) {
      xsw.writeStartElement("SIA_SetIntercharacterAdjustment");
      writeElement(baseXsw, childIndent, "adjustment", sia.getAdjustment());
      if (sia.getDirection() != null) {
        writeElement(baseXsw, childIndent, "direction", sia.getDirection().name());
      }
      baseXsw.writeCharacters(indent);
      xsw.writeEndElement();
    } else if (cs instanceof PTOCAControlSequence.SVI_SetVariableSpaceCharacterIncrement svi) {
      baseXsw.writeEmptyElement("SVI_SetVariableSpaceCharacterIncrement");
      baseXsw.writeAttribute("increment", String.valueOf(svi.getIncrement()));
    } else if (cs instanceof PTOCAControlSequence.SEC_SetExtendedTextColor sec) {
      xsw.writeStartElement("SEC_SetExtendedTextColor");
      writeElement(baseXsw, childIndent, "colorSpace", sec.getColorSpace().name());
      writeElement(baseXsw, childIndent, "nrOfBitsComponent1", sec.getNrOfBitsComponent1());
      writeElement(baseXsw, childIndent, "nrOfBitsComponent2", sec.getNrOfBitsComponent2());
      writeElement(baseXsw, childIndent, "nrOfBitsComponent3", sec.getNrOfBitsComponent3());
      writeElement(baseXsw, childIndent, "nrOfBitsComponent4", sec.getNrOfBitsComponent4());
      if (sec.getColorValue() != null) {
        writeElement(baseXsw, childIndent, "colorValue", UtilCharacterEncoding.bytesToHexString(sec.getColorValue()));
      }
      baseXsw.writeCharacters(indent);
      xsw.writeEndElement();
    } else if (cs instanceof PTOCAControlSequence.DIR_DrawIaxisRule dir) {
      xsw.writeStartElement("DIR_DrawIaxisRule");
      writeElement(baseXsw, childIndent, "length", dir.getLength());
      if (dir.getWidth() != null) {
        writeElement(baseXsw, childIndent, "width", dir.getWidth());
      }
      if (dir.getWidthFraction() != null) {
        writeElement(baseXsw, childIndent, "widthFraction", dir.getWidthFraction());
      }
      baseXsw.writeCharacters(indent);
      xsw.writeEndElement();
    } else if (cs instanceof PTOCAControlSequence.DBR_DrawBaxisRule dbr) {
      xsw.writeStartElement("DBR_DrawBaxisRule");
      writeElement(baseXsw, childIndent, "length", dbr.getLength());
      if (dbr.getWidth() != null) {
        writeElement(baseXsw, childIndent, "width", dbr.getWidth());
      }
      if (dbr.getWidthFraction() != null) {
        writeElement(baseXsw, childIndent, "widthFraction", dbr.getWidthFraction());
      }
      baseXsw.writeCharacters(indent);
      xsw.writeEndElement();
    } else if (cs instanceof PTOCAControlSequence.NOP_NoOperation nop) {
      xsw.writeStartElement("NOP_NoOperation");
      if (nop.getText() != null) {
        writeElement(baseXsw, childIndent, "text", nop.getText());
      }
      if (nop.getIgnoredData() != null) {
        writeElement(baseXsw, childIndent, "ignoredData", UtilCharacterEncoding.bytesToHexString(nop.getIgnoredData()));
      }
      baseXsw.writeCharacters(indent);
      xsw.writeEndElement();
    } else if (cs instanceof PTOCAControlSequence.RPS_RepeatString rps) {
      xsw.writeStartElement("RPS_RepeatString");
      writeElement(baseXsw, childIndent, "repeatLength", rps.getRepeatLength());
      if (rps.getText() != null) {
        writeElement(baseXsw, childIndent, "text", rps.getText());
      }
      if (rps.getRepeatData() != null) {
        writeElement(baseXsw, childIndent, "repeatData", UtilCharacterEncoding.bytesToHexString(rps.getRepeatData()));
      }
      baseXsw.writeCharacters(indent);
      xsw.writeEndElement();
    } else {
      // Fallback to Jackson
      fragmentMapper.writer().withRootName(cs.getClass().getSimpleName()).writeValue(fragmentGenerator, cs);
    }
  }

  private void writeFncDirectly(FNC_FontControl fnc) throws Exception {
    xsw.writeStartElement("FNC_FontControl");
    String indent = XmlIndenter.getIndent(2);
    writeElement(indent, "retired0", fnc.getRetired0());
    if (fnc.getPatternTechnologyIdentifier() != null) {
      writeElement(indent, "patternTechnologyIdentifier", fnc.getPatternTechnologyIdentifier().name());
    }
    writeElement(indent, "reserved2", fnc.getReserved2());
    if (fnc.getFontUseFlags() != null) {
      xsw.writeCharacters(indent);
      xsw.writeStartElement("fontUseFlags");
      for (FNC_FontControl.FncFontUseFlag flag : fnc.getFontUseFlags()) {
        xsw.writeCharacters(XmlIndenter.getIndent(3));
        xsw.writeStartElement("fncFontUseFlag");
        xsw.writeCharacters(flag.name());
        xsw.writeEndElement();
      }
      xsw.writeCharacters(indent);
      xsw.writeEndElement();
    }
    if (fnc.getxUnitBase() != null) {
      writeElement(indent, "xUnitBase", fnc.getxUnitBase().name());
    }
    if (fnc.getyUnitBase() != null) {
      writeElement(indent, "yUnitBase", fnc.getyUnitBase().name());
    }
    writeElement(indent, "xUnitsPerUnitBase", fnc.getxUnitsPerUnitBase());
    writeElement(indent, "yUnitsPerUnitBase", fnc.getyUnitsPerUnitBase());
    writeElement(indent, "maxCharacterBoxWidth", fnc.getMaxCharacterBoxWidth());
    writeElement(indent, "maxCharacterBoxHeight", fnc.getMaxCharacterBoxHeight());
    writeElement(indent, "fnoRepeatingGroupLength", fnc.getFnoRepeatingGroupLength());
    writeElement(indent, "fniRepeatingGroupLength", fnc.getFniRepeatingGroupLength());
    if (fnc.getRasterPatternDataAlignment() != null) {
      writeElement(indent, "rasterPatternDataAlignment", fnc.getRasterPatternDataAlignment().name());
    }
    writeElement(indent, "rasterPatternDataCount", fnc.getRasterPatternDataCount());
    writeElement(indent, "fnpRepeatingGroupLength", fnc.getFnpRepeatingGroupLength());
    writeElement(indent, "fnmRepeatingGroupLength", fnc.getFnmRepeatingGroupLength());
    writeElement(indent, "shapeResolutionXUnitBase10Inches", fnc.getShapeResolutionXUnitBase10Inches());
    writeElement(indent, "shapeResolutionYUnitBase10Inches", fnc.getShapeResolutionYUnitBase10Inches());
    writeElement(indent, "shapeResolutionXUnitsPerUnitBase", fnc.getShapeResolutionXUnitsPerUnitBase());
    writeElement(indent, "shapeResolutionYUnitsPerUnitBase", fnc.getShapeResolutionYUnitsPerUnitBase());
    writeElement(indent, "outlinePatternDataCount", fnc.getOutlinePatternDataCount());
    if (fnc.getReserved32_34() != null) {
      writeElement(indent, "reserved32_34", com.mgz.util.UtilCharacterEncoding.bytesToHexString(fnc.getReserved32_34()));
    }
    writeElement(indent, "fnnRepeatingGroupLength", fnc.getFnnRepeatingGroupLength());
    writeElement(indent, "fnnDataCount", fnc.getFnnDataCount());
    writeElement(indent, "fnnIbmNameGcgidCount", fnc.getFnnIbmNameGcgidCount());

    if (fnc.getTriplets() != null) {
      for (Triplet triplet : fnc.getTriplets()) {
        xsw.writeCharacters(indent);
        writeTriplet(triplet, indent);
      }
    }
    XmlIndenter.writeIndent(xsw, 1);
    xsw.writeEndElement();
  }

  private void writeLndDirectly(LND_LineDescriptor lnd) throws Exception {
    xsw.writeStartElement("LND_LineDescriptor");
    String indent = XmlIndenter.getIndent(2);
    if (lnd.getFlags() != null) {
      xsw.writeCharacters(indent);
      xsw.writeStartElement("flags");
      for (LND_LineDescriptor.LND_Flag flag : lnd.getFlags()) {
        xsw.writeCharacters(XmlIndenter.getIndent(3));
        xsw.writeStartElement("lndFlag");
        xsw.writeCharacters(flag.name());
        xsw.writeEndElement();
      }
      xsw.writeCharacters(indent);
      xsw.writeEndElement();
    }
    writeElement(indent, "inlinePosition", lnd.getInlinePosition());
    writeElement(indent, "baselinePosition", lnd.getBaselinePosition());
    if (lnd.getInlineOrientation() != null) {
      writeElement(indent, "inlineOrientation", lnd.getInlineOrientation().name());
    }
    if (lnd.getBaselineOrientation() != null) {
      writeElement(indent, "baselineOrientation", lnd.getBaselineOrientation().name());
    }
    writeElement(indent, "primaryFontLocalId", lnd.getPrimaryFontLocalId());
    writeElement(indent, "channelCode", lnd.getChannelCode());
    writeElement(indent, "nextLNDIfSkipping", lnd.getNextLNDIfSkipping());
    writeElement(indent, "nextLNDIfSpacing", lnd.getNextLNDIfSpacing());
    writeElement(indent, "nextLNDIfReusingData", lnd.getNextLNDIfReusingData());
    writeElement(indent, "suppressionTokenName", lnd.getSuppressionTokenName());
    writeElement(indent, "shiftOutLocalFontID", lnd.getShiftOutLocalFontID());
    writeElement(indent, "dataStartPosition", lnd.getDataStartPosition());
    writeElement(indent, "dataLength", lnd.getDataLength());
    if (lnd.getTextColor() != null) {
      writeElement(indent, "textColor", lnd.getTextColor().name());
    }
    writeElement(indent, "nextLNDIfConditionalProcessing", lnd.getNextLNDIfConditionalProcessing());
    writeElement(indent, "subpageID", lnd.getSubpageID());
    writeElement(indent, "ccpIdentifier", lnd.getCcpIdentifier());

    if (lnd.getTriplets() != null) {
      for (Triplet triplet : lnd.getTriplets()) {
        xsw.writeCharacters(indent);
        writeTriplet(triplet, indent);
      }
    }
    XmlIndenter.writeIndent(xsw, 1);
    xsw.writeEndElement();
  }

  private void writeGadDirectly(GAD_GraphicsData gad) throws Exception {
    xsw.writeStartElement("GAD_GraphicsData");
    String indent = XmlIndenter.getIndent(2);
    List<GAD_DrawingOrder> orders = gad.getDrawingOrders();
    if (orders != null) {
      for (GAD_DrawingOrder order : orders) {
        xsw.writeCharacters(indent);
        writeDrawingOrderDirectly(order, indent);
      }
    }
    XmlIndenter.writeIndent(xsw, 1);
    xsw.writeEndElement();
  }

  private void writeDrawingOrderDirectly(GAD_DrawingOrder order, String indent) throws Exception {
    String childIndent = indent + "  ";
    if (order instanceof GAD_DrawingOrder.GSCP_SetCurrentPosition gcp) {
      xsw.writeStartElement("GSCP_SetCurrentPosition");
      writeElement(baseXsw, childIndent, "coordinateX", gcp.getCoordinateX());
      writeElement(baseXsw, childIndent, "coordinateY", gcp.getCoordinateY());
      baseXsw.writeCharacters(indent);
      xsw.writeEndElement();
    } else if (order instanceof GAD_DrawingOrder.GSCOL_SetColor gsc) {
      xsw.writeStartElement("GSCOL_SetColor");
      if (gsc.getColor() != null) {
        writeElement(baseXsw, childIndent, "color", gsc.getColor().name());
      }
      baseXsw.writeCharacters(indent);
      xsw.writeEndElement();
    } else if (order instanceof GAD_DrawingOrder.GSCS_SetCharacterSet gscs) {
      xsw.writeStartElement("GSCS_SetCharacterSet");
      writeElement(baseXsw, childIndent, "characterSetLocalID", gscs.getCharacterSetLocalID());
      baseXsw.writeCharacters(indent);
      xsw.writeEndElement();
    } else if (order instanceof GAD_DrawingOrder.GSGCH_SegmentCharacteristics gsgch) {
      xsw.writeStartElement("GSGCH_SegmentCharacteristics");
      writeElement(baseXsw, childIndent, "lengthOfFollowingData", gsgch.lengthOfFollowingData);
      writeElement(baseXsw, childIndent, "identificationCode", gsgch.getIdentificationCode());
      if (gsgch.getParameters() != null) {
        writeElement(baseXsw, childIndent, "parameters", com.mgz.util.UtilCharacterEncoding.bytesToHexString(gsgch.getParameters()));
      }
      baseXsw.writeCharacters(indent);
      xsw.writeEndElement();
    } else if (order instanceof GAD_DrawingOrder.GSECOL_SetExtendedColor gsecol) {
      xsw.writeStartElement("GSECOL_SetExtendedColor");
      writeElement(baseXsw, childIndent, "lengthOfFollowingData", gsecol.lengthOfFollowingData);
      if (gsecol.getColor() != null) {
        writeElement(baseXsw, childIndent, "color", gsecol.getColor().name());
      }
      baseXsw.writeCharacters(indent);
      xsw.writeEndElement();
    } else if (order instanceof GAD_DrawingOrder.GSCC_SetCharacterCell gscc) {
      xsw.writeStartElement("GSCC_SetCharacterCell");
      writeElement(baseXsw, childIndent, "lengthOfFollowingData", gscc.lengthOfFollowingData);
      writeElement(baseXsw, childIndent, "widthOfCharacterCellIntegerPart", gscc.getWidthOfCharacterCellIntegerPart());
      writeElement(baseXsw, childIndent, "heightOfCharacterCellIntegerPart", gscc.getHeightOfCharacterCellIntegerPart());
      if (gscc.getWidthOfCharacterCellFractionalPart() != null) {
        writeElement(baseXsw, childIndent, "widthOfCharacterCellFractionalPart", gscc.getWidthOfCharacterCellFractionalPart());
      }
      if (gscc.getHeightOfCharacterCellFractionalPart() != null) {
        writeElement(baseXsw, childIndent, "heightOfCharacterCellFractionalPart", gscc.getHeightOfCharacterCellFractionalPart());
      }
      baseXsw.writeCharacters(indent);
      xsw.writeEndElement();
    } else if (order instanceof GAD_DrawingOrder.GSCA_SetCharacterAngle gsca) {
      xsw.writeStartElement("GSCA_SetCharacterAngle");
      writeElement(baseXsw, childIndent, "lengthOfFollowingData", gsca.lengthOfFollowingData);
      if (gsca.getAnglePoint() != null) {
        baseXsw.writeCharacters(childIndent);
        baseXsw.writeStartElement("anglePoint");
        writeElement(baseXsw, childIndent + "  ", "xCoordinate", gsca.getAnglePoint().xCoordinate());
        writeElement(baseXsw, childIndent + "  ", "yCoordinate", gsca.getAnglePoint().yCoordinate());
        baseXsw.writeCharacters(childIndent);
        baseXsw.writeEndElement();
      }
      baseXsw.writeCharacters(indent);
      xsw.writeEndElement();
    } else if (order instanceof GAD_DrawingOrder.GSCH_SetCharacterShear gsch) {
      xsw.writeStartElement("GSCH_SetCharacterShear");
      writeElement(baseXsw, childIndent, "lengthOfFollowingData", gsch.lengthOfFollowingData);
      writeElement(baseXsw, childIndent, "dividendOfShearRatio", gsch.getDividendOfShearRatio());
      writeElement(baseXsw, childIndent, "divisorOfShearRatio", gsch.getDivisorOfShearRatio());
      baseXsw.writeCharacters(indent);
      xsw.writeEndElement();
    } else if (order instanceof GAD_DrawingOrder.GSMC_SetMarkerCell gsmc) {
      xsw.writeStartElement("GSMC_SetMarkerCell");
      writeElement(baseXsw, childIndent, "lengthOfFollowingData", gsmc.lengthOfFollowingData);
      writeElement(baseXsw, childIndent, "widthOfMarkerCell", gsmc.getWidthOfMarkerCell());
      writeElement(baseXsw, childIndent, "heightOfMarkerCell", gsmc.getHeightOfMarkerCell());
      baseXsw.writeCharacters(indent);
      xsw.writeEndElement();
    } else if (order instanceof GAD_DrawingOrder.GSCLT_SetCustomLineType gsclt) {
      xsw.writeStartElement("GSCLT_SetCustomLineType");
      writeElement(baseXsw, childIndent, "lengthOfFollowingData", gsclt.lengthOfFollowingData);
      if (gsclt.repeatingGroups != null) {
        baseXsw.writeCharacters(childIndent);
        baseXsw.writeStartElement("repeatingGroups");
        String rgIndent = childIndent + "  ";
        for (GAD_DrawingOrder.GSCLT_SetCustomLineType.DashMoveRepeatingGroup rg : gsclt.repeatingGroups) {
          baseXsw.writeCharacters(rgIndent);
          baseXsw.writeStartElement("DashMoveRepeatingGroup");
          writeElement(baseXsw, rgIndent + "  ", "dashInteger", rg.dashInteger());
          writeElement(baseXsw, rgIndent + "  ", "dashFractional", rg.dashFractional());
          writeElement(baseXsw, rgIndent + "  ", "moveInteger", rg.moveInteger());
          writeElement(baseXsw, rgIndent + "  ", "moveFractional", rg.moveFractional());
          baseXsw.writeCharacters(rgIndent);
          baseXsw.writeEndElement();
        }
        baseXsw.writeCharacters(childIndent);
        baseXsw.writeEndElement();
      }
      baseXsw.writeCharacters(indent);
      xsw.writeEndElement();
    } else if (order instanceof GAD_DrawingOrder.GSAP_SetArcParameters gsap) {
      xsw.writeStartElement("GSAP_SetArcParameters");
      writeElement(baseXsw, childIndent, "lengthOfFollowingData", gsap.lengthOfFollowingData);
      writeElement(baseXsw, childIndent, "arcTransformP", gsap.getArcTransformP());
      writeElement(baseXsw, childIndent, "arcTransformQ", gsap.getArcTransformQ());
      writeElement(baseXsw, childIndent, "arcTransformR", gsap.getArcTransformR());
      writeElement(baseXsw, childIndent, "arcTransformS", gsap.getArcTransformS());
      baseXsw.writeCharacters(indent);
      xsw.writeEndElement();
    } else if (order instanceof GAD_DrawingOrder.GSPRP_SetPatternReferencePoint gsprp) {
      xsw.writeStartElement("GSPRP_SetPatternReferencePoint");
      writeElement(baseXsw, childIndent, "lengthOfFollowingData", gsprp.lengthOfFollowingData);
      writeElement(baseXsw, childIndent, "flags", gsprp.getFlags());
      writeElement(baseXsw, childIndent, "reserved3", gsprp.getReserved3());
      writeElement(baseXsw, childIndent, "coordinateX", gsprp.getCoordinateX());
      writeElement(baseXsw, childIndent, "coordinateY", gsprp.getCoordinateY());
      baseXsw.writeCharacters(indent);
      xsw.writeEndElement();
    } else if (order instanceof GAD_DrawingOrder.GCPARC_PartialArcAtCurrentPosition gcparc) {
      xsw.writeStartElement("GCPARC_PartialArcAtCurrentPosition");
      writeElement(baseXsw, childIndent, "lengthOfFollowingData", gcparc.lengthOfFollowingData);
      if (gcparc.getArcCenter() != null) {
        baseXsw.writeCharacters(childIndent);
        baseXsw.writeStartElement("arcCenter");
        writeElement(baseXsw, childIndent + "  ", "xCoordinate", gcparc.getArcCenter().xCoordinate());
        writeElement(baseXsw, childIndent + "  ", "yCoordinate", gcparc.getArcCenter().yCoordinate());
        baseXsw.writeCharacters(childIndent);
        baseXsw.writeEndElement();
      }
      writeElement(baseXsw, childIndent, "multiplierIntegerPortion", gcparc.getMultiplierIntegerPortion());
      writeElement(baseXsw, childIndent, "multiplierFractionalPortion", gcparc.getMultiplierFractionalPortion());
      writeElement(baseXsw, childIndent, "startAngle", gcparc.getStartAngle());
      writeElement(baseXsw, childIndent, "sweepAngle", gcparc.getSweepAngle());
      baseXsw.writeCharacters(indent);
      xsw.writeEndElement();
    } else if (order instanceof GAD_DrawingOrder.GSPCOL_SetProcessColor gspcol) {
      xsw.writeStartElement("GSPCOL_SetProcessColor");
      writeElement(baseXsw, childIndent, "lengthOfFollowingData", gspcol.lengthOfFollowingData);
      writeElement(baseXsw, childIndent, "reserved2", gspcol.getReserved2());
      if (gspcol.getColorSpace() != null) {
        writeElement(baseXsw, childIndent, "colorSpace", gspcol.getColorSpace().name());
      }
      writeElement(baseXsw, childIndent, "reserved4_7", gspcol.getReserved4_7());
      writeElement(baseXsw, childIndent, "nrOfBitsComponent1", gspcol.getNrOfBitsComponent1());
      writeElement(baseXsw, childIndent, "nrOfBitsComponent2", gspcol.getNrOfBitsComponent2());
      writeElement(baseXsw, childIndent, "nrOfBitsComponent3", gspcol.getNrOfBitsComponent3());
      writeElement(baseXsw, childIndent, "nrOfBitsComponent4", gspcol.getNrOfBitsComponent4());
      if (gspcol.getColorValue() != null) {
        writeElement(baseXsw, childIndent, "colorValue", com.mgz.util.UtilCharacterEncoding.bytesToHexString(gspcol.getColorValue()));
      }
      baseXsw.writeCharacters(indent);
      xsw.writeEndElement();
    } else if (order instanceof GAD_DrawingOrder.GFARC_FullArcAtGivenPosition gfarc) {
      xsw.writeStartElement("GFARC_FullArcAtGivenPosition");
      writeElement(baseXsw, childIndent, "lengthOfFollowingData", gfarc.lengthOfFollowingData);
      if (gfarc.getArcCenter() != null) {
        baseXsw.writeCharacters(childIndent);
        baseXsw.writeStartElement("arcCenter");
        writeElement(baseXsw, childIndent + "  ", "xCoordinate", gfarc.getArcCenter().xCoordinate());
        writeElement(baseXsw, childIndent + "  ", "yCoordinate", gfarc.getArcCenter().yCoordinate());
        baseXsw.writeCharacters(childIndent);
        baseXsw.writeEndElement();
      }
      writeElement(baseXsw, childIndent, "multiplierIntegerPortion", gfarc.getMultiplierIntegerPortion());
      writeElement(baseXsw, childIndent, "multiplierFractionalPortion", gfarc.getMultiplierFractionalPortion());
      baseXsw.writeCharacters(indent);
      xsw.writeEndElement();
    } else if (order instanceof GAD_DrawingOrder.GCBIMG_BeginImageAtCurrentPosition gcbimg) {
      xsw.writeStartElement("GCBIMG_BeginImageAtCurrentPosition");
      writeElement(baseXsw, childIndent, "lengthOfFollowingData", gcbimg.lengthOfFollowingData);
      writeElement(baseXsw, childIndent, "formatOfImageData", gcbimg.getFormatOfImageData());
      writeElement(baseXsw, childIndent, "reserved3", gcbimg.getReserved3());
      writeElement(baseXsw, childIndent, "widthOfImageInImagePoints", gcbimg.getWidthOfImageInImagePoints());
      writeElement(baseXsw, childIndent, "heightOfImageInImagePoints", gcbimg.getHeightOfImageInImagePoints());
      baseXsw.writeCharacters(indent);
      xsw.writeEndElement();
    } else if (order instanceof GAD_DrawingOrder.GBIMG_BeginImageAtGivenPosition gbimg) {
      xsw.writeStartElement("GBIMG_BeginImageAtGivenPosition");
      writeElement(baseXsw, childIndent, "lengthOfFollowingData", gbimg.lengthOfFollowingData);
      if (gbimg.getOrigin() != null) {
        baseXsw.writeCharacters(childIndent);
        baseXsw.writeStartElement("origin");
        writeElement(baseXsw, childIndent + "  ", "xCoordinate", gbimg.getOrigin().xCoordinate());
        writeElement(baseXsw, childIndent + "  ", "yCoordinate", gbimg.getOrigin().yCoordinate());
        baseXsw.writeCharacters(childIndent);
        baseXsw.writeEndElement();
      }
      writeElement(baseXsw, childIndent, "formatOfImageData", gbimg.getFormatOfImageData());
      writeElement(baseXsw, childIndent, "reserved3", gbimg.getReserved3());
      writeElement(baseXsw, childIndent, "widthOfImageInImagePoints", gbimg.getWidthOfImageInImagePoints());
      writeElement(baseXsw, childIndent, "heightOfImageInImagePoints", gbimg.getHeightOfImageInImagePoints());
      baseXsw.writeCharacters(indent);
      xsw.writeEndElement();
    } else if (order instanceof GAD_DrawingOrder.GPARC_PartialArcAtGivenPosition gparc) {
      xsw.writeStartElement("GPARC_PartialArcAtGivenPosition");
      writeElement(baseXsw, childIndent, "lengthOfFollowingData", gparc.lengthOfFollowingData);
      if (gparc.getLineStartPoint() != null) {
        baseXsw.writeCharacters(childIndent);
        baseXsw.writeStartElement("lineStartPoint");
        writeElement(baseXsw, childIndent + "  ", "xCoordinate", gparc.getLineStartPoint().xCoordinate());
        writeElement(baseXsw, childIndent + "  ", "yCoordinate", gparc.getLineStartPoint().yCoordinate());
        baseXsw.writeCharacters(childIndent);
        baseXsw.writeEndElement();
      }
      if (gparc.getArcCenter() != null) {
        baseXsw.writeCharacters(childIndent);
        baseXsw.writeStartElement("arcCenter");
        writeElement(baseXsw, childIndent + "  ", "xCoordinate", gparc.getArcCenter().xCoordinate());
        writeElement(baseXsw, childIndent + "  ", "yCoordinate", gparc.getArcCenter().yCoordinate());
        baseXsw.writeCharacters(childIndent);
        baseXsw.writeEndElement();
      }
      writeElement(baseXsw, childIndent, "multiplierIntegerPortion", gparc.getMultiplierIntegerPortion());
      writeElement(baseXsw, childIndent, "multiplierFractionalPortion", gparc.getMultiplierFractionalPortion());
      writeElement(baseXsw, childIndent, "startAngle", gparc.getStartAngle());
      writeElement(baseXsw, childIndent, "sweepAngle", gparc.getSweepAngle());
      baseXsw.writeCharacters(indent);
      xsw.writeEndElement();
    } else if (order instanceof GAD_DrawingOrder.GEXO_ExtendedOrder gexo) {
      xsw.writeStartElement("GEXO_ExtendedOrder");
      writeElement(baseXsw, childIndent, "qualifier", gexo.qualifier);
      writeElement(baseXsw, childIndent, "lengthOfFollowingData", gexo.lengthOfFollowingData);
      if (gexo.getExtendedData() != null) {
        writeElement(baseXsw, childIndent, "extendedData", com.mgz.util.UtilCharacterEncoding.bytesToHexString(gexo.getExtendedData()));
      }
      baseXsw.writeCharacters(indent);
      xsw.writeEndElement();
    } else if (order instanceof GAD_DrawingOrder.GCCHST_CharacterStringAtCurrentPosition gcchst) {
      xsw.writeStartElement("GCCHST_CharacterStringAtCurrentPosition");
      writeElement(baseXsw, childIndent, "text", gcchst.getText());
      baseXsw.writeCharacters(indent);
      xsw.writeEndElement();
    } else if (order instanceof GAD_DrawingOrder.GCHST_CharacterStringAtGivenPosition gchst) {
      xsw.writeStartElement("GCHST_CharacterStringAtGivenPosition");
      if (gchst.getOriginPoint() != null) {
        baseXsw.writeCharacters(childIndent);
        baseXsw.writeStartElement("originPoint");
        writeElement(baseXsw, childIndent + "  ", "xCoordinate", gchst.getOriginPoint().xCoordinate());
        writeElement(baseXsw, childIndent + "  ", "yCoordinate", gchst.getOriginPoint().yCoordinate());
        baseXsw.writeCharacters(childIndent);
        baseXsw.writeEndElement();
      }
      writeElement(baseXsw, childIndent, "text", gchst.getText());
      baseXsw.writeCharacters(indent);
      xsw.writeEndElement();
    } else if (order instanceof GAD_DrawingOrder.GESEG_EndSegment) {
      xsw.writeEmptyElement("GESEG_EndSegment");
    } else if (order instanceof GAD_DrawingOrder.GBSEG_BeginSegment gbseg) {
      xsw.writeStartElement("GBSEG_BeginSegment");
      writeElement(baseXsw, childIndent, "nameOfSegment", gbseg.getNameOfSegment());
      writeElement(baseXsw, childIndent, "text", gbseg.getText());
      if (gbseg.getDrawingOrders() != null) {
        for (GAD_DrawingOrder childOrder : gbseg.getDrawingOrders()) {
          baseXsw.writeCharacters(childIndent);
          writeDrawingOrderDirectly(childOrder, childIndent);
        }
      }
      baseXsw.writeCharacters(indent);
      xsw.writeEndElement();
    } else if (order instanceof GAD_DrawingOrder.GBAR_BeginArea gbar) {
      xsw.writeStartElement("GBAR_BeginArea");
      writeElement(baseXsw, childIndent, "internalFlags", gbar.getInternalFlags());
      baseXsw.writeCharacters(indent);
      xsw.writeEndElement();
    } else if (order instanceof GAD_DrawingOrder.GEAR_EndArea gear) {
      xsw.writeStartElement("GEAR_EndArea");
      writeElement(baseXsw, childIndent, "text", gear.getText());
      baseXsw.writeCharacters(indent);
      xsw.writeEndElement();
    } else if (order instanceof GAD_DrawingOrder.GIMD_ImageData gimd) {
      xsw.writeStartElement("GIMD_ImageData");
      if (gimd.getImageData() != null) {
        writeElement(baseXsw, childIndent, "imageData", com.mgz.util.UtilCharacterEncoding.bytesToHexString(gimd.getImageData()));
      }
      baseXsw.writeCharacters(indent);
      xsw.writeEndElement();
    } else if (order instanceof GAD_DrawingOrder.GCBOX_BoxAtCurrentPosition gcbox) {
      xsw.writeStartElement("GCBOX_BoxAtCurrentPosition");
      writeElement(baseXsw, childIndent, "reserved2_3", gcbox.getReserved2_3());
      if (gcbox.getDiagonalCorner() != null) {
        baseXsw.writeCharacters(childIndent);
        baseXsw.writeStartElement("diagonalCorner");
        writeElement(baseXsw, childIndent + "  ", "xCoordinate", gcbox.getDiagonalCorner().xCoordinate());
        writeElement(baseXsw, childIndent + "  ", "yCoordinate", gcbox.getDiagonalCorner().yCoordinate());
        baseXsw.writeCharacters(childIndent);
        baseXsw.writeEndElement();
      }
      if (gcbox.getxAxisLengthForRoundCorner() != null) {
        writeElement(baseXsw, childIndent, "xAxisLengthForRoundCorner", gcbox.getxAxisLengthForRoundCorner());
      }
      if (gcbox.getyAxisLengthForRoundCorner() != null) {
        writeElement(baseXsw, childIndent, "yAxisLengthForRoundCorner", gcbox.getyAxisLengthForRoundCorner());
      }
      baseXsw.writeCharacters(indent);
      xsw.writeEndElement();
    } else if (order instanceof GAD_DrawingOrder.GBOX_BoxAtGivenPosition gbox) {
      xsw.writeStartElement("GBOX_BoxAtGivenPosition");
      writeElement(baseXsw, childIndent, "reserved2_3", gbox.getReserved2_3());
      if (gbox.getFirstCorner() != null) {
        baseXsw.writeCharacters(childIndent);
        baseXsw.writeStartElement("firstCorner");
        writeElement(baseXsw, childIndent + "  ", "xCoordinate", gbox.getFirstCorner().xCoordinate());
        writeElement(baseXsw, childIndent + "  ", "yCoordinate", gbox.getFirstCorner().yCoordinate());
        baseXsw.writeCharacters(childIndent);
        baseXsw.writeEndElement();
      }
      if (gbox.getDiagonalCorner() != null) {
        baseXsw.writeCharacters(childIndent);
        baseXsw.writeStartElement("diagonalCorner");
        writeElement(baseXsw, childIndent + "  ", "xCoordinate", gbox.getDiagonalCorner().xCoordinate());
        writeElement(baseXsw, childIndent + "  ", "yCoordinate", gbox.getDiagonalCorner().yCoordinate());
        baseXsw.writeCharacters(childIndent);
        baseXsw.writeEndElement();
      }
      if (gbox.getxAxisLengthForRoundCorner() != null) {
        writeElement(baseXsw, childIndent, "xAxisLengthForRoundCorner", gbox.getxAxisLengthForRoundCorner());
      }
      if (gbox.getyAxisLengthForRoundCorner() != null) {
        writeElement(baseXsw, childIndent, "yAxisLengthForRoundCorner", gbox.getyAxisLengthForRoundCorner());
      }
      baseXsw.writeCharacters(indent);
      xsw.writeEndElement();
    } else if (order instanceof GAD_DrawingOrder.GCRLINE_RelativeLineAtCurrentPosition gcrline) {
      xsw.writeStartElement("GCRLINE_RelativeLineAtCurrentPosition");
      if (gcrline.relativeOffsets != null) {
        baseXsw.writeCharacters(childIndent);
        baseXsw.writeStartElement("relativeOffsets");
        String offsetIndent = childIndent + "  ";
        for (GAD_DrawingOrder.GOCA_RelativePoint rp : gcrline.relativeOffsets) {
          baseXsw.writeCharacters(offsetIndent);
          baseXsw.writeStartElement("GOCA_RelativePoint");
          writeElement(baseXsw, offsetIndent + "  ", "xOffset", rp.xOffset());
          writeElement(baseXsw, offsetIndent + "  ", "yOffset", rp.yOffset());
          baseXsw.writeCharacters(offsetIndent);
          baseXsw.writeEndElement();
        }
        baseXsw.writeCharacters(childIndent);
        baseXsw.writeEndElement();
      }
      baseXsw.writeCharacters(indent);
      xsw.writeEndElement();
    } else if (order instanceof GAD_DrawingOrder.GRLINE_RelativeLineAtGivenPosition grline) {
      xsw.writeStartElement("GRLINE_RelativeLineAtGivenPosition");
      if (grline.startPoint != null) {
        baseXsw.writeCharacters(childIndent);
        baseXsw.writeStartElement("startPoint");
        writeElement(baseXsw, childIndent + "  ", "xCoordinate", grline.startPoint.xCoordinate());
        writeElement(baseXsw, childIndent + "  ", "yCoordinate", grline.startPoint.yCoordinate());
        baseXsw.writeCharacters(childIndent);
        baseXsw.writeEndElement();
      }
      if (grline.relativeOffsets != null) {
        baseXsw.writeCharacters(childIndent);
        baseXsw.writeStartElement("relativeOffsets");
        String offsetIndent = childIndent + "  ";
        for (GAD_DrawingOrder.GOCA_RelativePoint rp : grline.relativeOffsets) {
          baseXsw.writeCharacters(offsetIndent);
          baseXsw.writeStartElement("GOCA_RelativePoint");
          writeElement(baseXsw, offsetIndent + "  ", "xOffset", rp.xOffset());
          writeElement(baseXsw, offsetIndent + "  ", "yOffset", rp.yOffset());
          baseXsw.writeCharacters(offsetIndent);
          baseXsw.writeEndElement();
        }
        baseXsw.writeCharacters(childIndent);
        baseXsw.writeEndElement();
      }
      baseXsw.writeCharacters(indent);
      xsw.writeEndElement();
    } else if (order instanceof GAD_DrawingOrder.DrawingOrder_HasPoints dohp) {
      xsw.writeStartElement(order.getClass().getSimpleName());
      if (dohp.getPoints() != null) {
        baseXsw.writeCharacters(childIndent);
        baseXsw.writeStartElement("points");
        String pointIndent = childIndent + "  ";
        for (GAD_DrawingOrder.GOCA_Point p : dohp.getPoints()) {
          baseXsw.writeCharacters(pointIndent);
          baseXsw.writeStartElement("GOCA_Point");
          writeElement(baseXsw, pointIndent + "  ", "xCoordinate", p.xCoordinate());
          writeElement(baseXsw, pointIndent + "  ", "yCoordinate", p.yCoordinate());
          baseXsw.writeCharacters(pointIndent);
          baseXsw.writeEndElement();
        }
        baseXsw.writeCharacters(childIndent);
        baseXsw.writeEndElement();
      }
      xsw.writeCharacters(indent);
      xsw.writeEndElement();
    } else {
      // Fallback to Jackson
      String rootName = order.getClass().getSimpleName();
      fragmentMapper.writer().withRootName(rootName).writeValue(fragmentGenerator, order);
    }
  }

  private void writeIpdSegmentDirectly(IPD_Segment segment, String indent) throws Exception {
    String childIndent = indent + "  ";
    if (segment instanceof IPD_Segment.ImageData id) {
      xsw.writeStartElement("ImageData");
      if (id.getImageData() != null) {
        writeElement(childIndent, "imageData", UtilCharacterEncoding.bytesToHexString(id.getImageData()));
      }
      xsw.writeCharacters(indent);
      xsw.writeEndElement();
    } else if (segment instanceof IPD_Segment.BandImageData bid) {
      xsw.writeStartElement("BandImageData");
      writeElement(childIndent, "bandNumber", bid.getBandNumber());
      if (bid.getBandData() != null) {
        writeElement(childIndent, "bandData", UtilCharacterEncoding.bytesToHexString(bid.getBandData()));
      }
      xsw.writeCharacters(indent);
      xsw.writeEndElement();
    } else if (segment instanceof IPD_Segment.BeginImageContent bic) {
      xsw.writeStartElement("BeginImageContent");
      writeElement(childIndent, "objectType", bic.getObjectType());
      xsw.writeCharacters(indent);
      xsw.writeEndElement();
    } else if (segment instanceof IPD_Segment.EndImageContent) {
      xsw.writeEmptyElement("EndImageContent");
    } else if (segment instanceof IPD_Segment.ImageSize is) {
      xsw.writeStartElement("ImageSize");
      if (is.getUnitBase() != null) {
        writeElement(childIndent, "unitBase", is.getUnitBase().name());
      }
      writeElement(childIndent, "xUnitsPerUnitBase", is.getxUnitsPerUnitBase());
      writeElement(childIndent, "yUnitsPerUnitBase", is.getyUnitsPerUnitBase());
      writeElement(childIndent, "xImageSize", is.getxImageSize());
      writeElement(childIndent, "yImageSize", is.getyImageSize());
      xsw.writeCharacters(indent);
      xsw.writeEndElement();
    } else if (segment instanceof IPD_Segment.ImageEncoding ie) {
      xsw.writeStartElement("ImageEncoding");
      if (ie.getCompressionAlgorithm() != null) {
        writeElement(childIndent, "compressionAlgorithm", ie.getCompressionAlgorithm().name());
      }
      if (ie.getRecordingAlgorithm() != null) {
        writeElement(childIndent, "recordingAlgorithm", ie.getRecordingAlgorithm().name());
      }
      if (ie.getBitOrder() != null) {
        writeElement(childIndent, "bitOrder", ie.getBitOrder().name());
      }
      xsw.writeCharacters(indent);
      xsw.writeEndElement();
    } else {
      // Fallback to Jackson
      String rootName = segment.getClass().getSimpleName();
      fragmentMapper.writer().withRootName(rootName).writeValue(fragmentGenerator, segment);
    }
  }

  private void writeIpdDirectly(IPD_ImagePictureData ipd) throws Exception {
    xsw.writeStartElement("IPD_ImagePictureData");
    String indent = XmlIndenter.getIndent(2);
    List<IPD_Segment> segments = ipd.getListOfSegments();
    if (segments != null) {
      for (IPD_Segment segment : segments) {
        xsw.writeCharacters(indent);
        writeIpdSegmentDirectly(segment, indent);
      }
    }
    XmlIndenter.writeIndent(xsw, 1);
    xsw.writeEndElement();
  }

  private void writeObdDirectly(OBD_ObjectAreaDescriptor obd) throws Exception {
    xsw.writeStartElement("OBD_ObjectAreaDescriptor");
    String indent = XmlIndenter.getIndent(2);
    if (obd.getTriplets() != null) {
      for (Triplet triplet : obd.getTriplets()) {
        xsw.writeCharacters(indent);
        writeTriplet(triplet, indent);
      }
    }
    XmlIndenter.writeIndent(xsw, 1);
    xsw.writeEndElement();
  }

  private void writeObpDirectly(OBP_ObjectAreaPosition obp) throws Exception {
    xsw.writeStartElement("OBP_ObjectAreaPosition");
    String indent = XmlIndenter.getIndent(2);
    writeElement(indent, "objectAreaPositionID", obp.getObjectAreaPositionID());
    if (obp.getRepeatingGroup() != null) {
      xsw.writeCharacters(indent);
      xsw.writeStartElement("repeatingGroup");
      String childIndent = XmlIndenter.getIndent(3);
      OBP_ObjectAreaPosition.OBP_RepeatingGroup rg = obp.getRepeatingGroup();
      writeElement(childIndent, "repeatingGroupLength", rg.getRepeatingGroupLength());
      writeElement(childIndent, "xOrigin", rg.getxOrigin());
      writeElement(childIndent, "yOrigin", rg.getyOrigin());
      if (rg.getxRotation() != null) {
        writeElement(childIndent, "xRotation", rg.getxRotation().name());
      }
      if (rg.getyRotation() != null) {
        writeElement(childIndent, "yRotation", rg.getyRotation().name());
      }
      writeElement(childIndent, "reserved11", rg.getReserved11());
      writeElement(childIndent, "xOriginOfContent", rg.getxOriginOfContent());
      writeElement(childIndent, "yOriginOfContent", rg.getyOriginOfContent());
      if (rg.getxRotationOfContent() != null) {
        writeElement(childIndent, "xRotationOfContent", rg.getxRotationOfContent().name());
      }
      if (rg.getyRotationOfContent() != null) {
        writeElement(childIndent, "yRotationOfContent", rg.getyRotationOfContent().name());
      }
      if (rg.getReferenceCoordinateSystem() != null) {
        writeElement(childIndent, "referenceCoordinateSystem", rg.getReferenceCoordinateSystem().name());
      }
      xsw.writeCharacters(indent);
      xsw.writeEndElement();
    }
    XmlIndenter.writeIndent(xsw, 1);
    xsw.writeEndElement();
  }

  private void writeIddSelfDefiningFieldDirectly(IDD_SelfDefiningField sdf, String indent) throws Exception {
    String childIndent = indent + "  ";
    if (sdf instanceof IDD_SelfDefiningField.SetBilevelImageColor sbic) {
      xsw.writeStartElement("SetBilevelImageColor");
      writeElement(childIndent, "applicabilityArea", sbic.getApplicabilityArea());
      if (sbic.getColor() != null) {
        writeElement(childIndent, "color", sbic.getColor().name());
      }
      xsw.writeCharacters(indent);
      xsw.writeEndElement();
    } else if (sdf instanceof IDD_SelfDefiningField.SetExtendedBilevelImageColor sebic) {
      xsw.writeStartElement("SetExtendedBilevelImageColor");
      if (sebic.getColorSpace() != null) {
        writeElement(childIndent, "colorSpace", sebic.getColorSpace().name());
      }
      writeElement(childIndent, "nrOfBitsComponent1", sebic.getNrOfBitsComponent1());
      writeElement(childIndent, "nrOfBitsComponent2", sebic.getNrOfBitsComponent2());
      writeElement(childIndent, "nrOfBitsComponent3", sebic.getNrOfBitsComponent3());
      writeElement(childIndent, "nrOfBitsComponent4", sebic.getNrOfBitsComponent4());
      if (sebic.getColorValue() != null) {
        writeElement(childIndent, "colorValue", UtilCharacterEncoding.bytesToHexString(sebic.getColorValue()));
      }
      xsw.writeCharacters(indent);
      xsw.writeEndElement();
    } else if (sdf instanceof IDD_SelfDefiningField.IOCAFunctionSetIdentification fsi) {
      xsw.writeStartElement("IOCAFunctionSetIdentification");
      writeElement(childIndent, "functionSetCategory", fsi.getFunctionSetCategory());
      if (fsi.getFunctionSetIdentifier() != null) {
        writeElement(childIndent, "functionSetIdentifier", fsi.getFunctionSetIdentifier().name());
      }
      xsw.writeCharacters(indent);
      xsw.writeEndElement();
    } else {
      // Fallback to Jackson
      String rootName = sdf.getClass().getSimpleName();
      fragmentMapper.writer().withRootName(rootName).writeValue(fragmentGenerator, sdf);
    }
  }

  private void writeIddDirectly(IDD_ImageDataDescriptor idd) throws Exception {
    xsw.writeStartElement("IDD_ImageDataDescriptor");
    String indent = XmlIndenter.getIndent(2);
    if (idd.getUnitBase() != null) {
      writeElement(indent, "unitBase", idd.getUnitBase().name());
    }
    writeElement(indent, "xImagePointsPerUnitBase", idd.getxImagePointsPerUnitBase());
    writeElement(indent, "yImagePointsPerUnitBase", idd.getyImagePointsPerUnitBase());
    writeElement(indent, "widthOfImageInImagePoints", idd.getWidthOfImageInImagePoints());
    writeElement(indent, "heightOfImageInImagePoints", idd.getHeightOfImageInImagePoints());

    if (idd.getSelfDefiningFields() != null) {
      for (IDD_SelfDefiningField sdf : idd.getSelfDefiningFields()) {
        xsw.writeCharacters(indent);
        writeIddSelfDefiningFieldDirectly(sdf, indent);
      }
    }
    XmlIndenter.writeIndent(xsw, 1);
    xsw.writeEndElement();
  }

  private void writeMioDirectly(MIO_MapImageObject mio) throws Exception {
    xsw.writeStartElement("MIO_MapImageObject");
    String indent = XmlIndenter.getIndent(2);
    if (mio.getRepeatingGroups() != null) {
      for (IRepeatingGroup rg : mio.getRepeatingGroups()) {
        xsw.writeCharacters(indent);
        xsw.writeStartElement("mioRepeatingGroup");
        if (rg instanceof RepeatingGroupWithTriplets rgt) {
          if (rgt.getTriplets() != null) {
            for (Triplet t : rgt.getTriplets()) {
              xsw.writeCharacters(XmlIndenter.getIndent(3));
              writeTriplet(t, XmlIndenter.getIndent(3));
            }
          }
        }
        xsw.writeCharacters(indent);
        xsw.writeEndElement();
      }
    }
    XmlIndenter.writeIndent(xsw, 1);
    xsw.writeEndElement();
  }


  private void writeElement(XMLStreamWriter2 writer, String indent, String name, String value) throws Exception {
    if (value != null) {
      writer.writeCharacters(indent);
      writer.writeStartElement(name);
      writer.writeCharacters(value);
      writer.writeEndElement();
    }
  }

  private void writeElement(XMLStreamWriter2 writer, String indent, String name, int value) throws Exception {
    writer.writeCharacters(indent);
    writer.writeStartElement(name);
    writer.writeInt(value);
    writer.writeEndElement();
  }

  private void writeElement(XMLStreamWriter2 writer, String indent, String name, long value) throws Exception {
    writer.writeCharacters(indent);
    writer.writeStartElement(name);
    writer.writeLong(value);
    writer.writeEndElement();
  }

  private void writeElement(String indent, String name, String value) throws Exception {
    writeElement(xsw, indent, name, value);
  }

  private void writeElement(String indent, String name, int value) throws Exception {
    writeElement(xsw, indent, name, value);
  }

  private void writeElement(String indent, String name, long value) throws Exception {
    writeElement(xsw, indent, name, value);
  }

  private void writeFieldWithXpath(StructuredField sf) throws Exception {
    if (cachedDocumentBuilder == null) {
      cachedDocumentBuilder = DBF.newDocumentBuilder();
    }

    int initialCapacity = (int) SFSizeEstimator.estimateXmlSize(sf);
    String xml;
    java.io.ByteArrayOutputStream baos = new java.io.ByteArrayOutputStream(initialCapacity);
    try (AfpJacksonXmlWriter tempWriter = new AfpJacksonXmlWriter(baos, null, true)) {
      tempWriter.handle(sf);
    }
    xml = baos.toString(StandardCharsets.UTF_8);

    xml = xml.replace("<AfpFragments>", "<AFPDocument>").replace("</AfpFragments>", "</AFPDocument>");

    Document doc = cachedDocumentBuilder.parse(
        new ByteArrayInputStream(xml.getBytes(StandardCharsets.UTF_8)));

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
      if (!fragmentMode) {
        xsw.writeEndElement();
        xsw.writeCharacters("\n");
        xsw.writeEndDocument();
      } else {
        xsw.writeEndElement(); // AfpFragments
      }
      xsw.flush();
      if (!fragmentMode) {
        xsw.close();
      }
    }
  }
}
