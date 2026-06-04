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
import com.mgz.afp.modca.MMC_MediumModificationControl;
import com.mgz.afp.modca.MCD_MapContainerData;
import com.mgz.afp.modca.MGO_MapGraphicsObject;
import com.mgz.afp.modca.MDR_MapDataResource;
import com.mgz.afp.modca.MIO_MapImageObject;
import com.mgz.afp.modca.BDI_BeginDocumentIndex;
import com.mgz.afp.modca.BMO_BeginOverlay;
import com.mgz.afp.modca.BPS_BeginPageSegment;
import com.mgz.afp.modca.BRG_BeginResourceGroup;
import com.mgz.afp.modca.EDI_EndDocumentIndex;
import com.mgz.afp.modca.EMO_EndOverlay;
import com.mgz.afp.modca.EPS_EndPageSegment;
import com.mgz.afp.modca.ERG_EndResourceGroup;
import com.mgz.afp.modca.MPO_MapPageOverlay;
import com.mgz.afp.modca.MSU_MapSuppression;
import com.mgz.afp.modca.NOP_NoOperation;
import com.mgz.afp.modca.OBD_ObjectAreaDescriptor;
import com.mgz.afp.modca.OBP_ObjectAreaPosition;
import com.mgz.afp.modca.PGP_PagePosition_Format1;
import com.mgz.afp.modca.PGP_PagePosition_Format2;
import com.mgz.afp.modca.TLE_TagLogicalElement;
import com.mgz.afp.moca.MetadataObject;
import com.mgz.afp.modca.OCD_ObjectContainerData;
import com.mgz.afp.ptoca.PTX_PresentationTextData;
import com.mgz.afp.ptoca.controlSequence.PTOCAControlSequence;
import com.mgz.afp.bcoca.BBC_BeginBarCodeObject;
import com.mgz.afp.bcoca.BDA_BarCodeData;
import com.mgz.afp.bcoca.BDD_BarCodeDataDescriptor;
import com.mgz.afp.bcoca.EBC_EndBarCodeObject;
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

  private static final DocumentBuilderFactory DBF = DocumentBuilderFactory.newInstance();
  private static final XPathFactory XPF = XPathFactory.newInstance();
  private static final TransformerFactory TF = TransformerFactory.newInstance();

  private final XMLStreamWriter2 xsw;
  private final AfpXmlStreamWriter baseXsw;
  private final OutputStream os;
  private final com.mgz.util.CountingOutputStream cos;
  private final String xpathExpression;
  private final boolean fragmentMode;
  private final XmlMapper mapper;
  private final XmlMapper fragmentMapper;
  private ToXmlGenerator baseFragmentGenerator;

  private javax.xml.parsers.DocumentBuilder cachedDocumentBuilder;
  private javax.xml.xpath.XPath cachedXpath;
  private javax.xml.transform.Transformer cachedTransformer;

  private final boolean indentEnabled;

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
    this(os, xpathExpression, fragmentMode, false);
  }

  /**
   * Constructor for AfpJacksonXmlWriter.
   * Constructor for AfpJacksonXmlWriter.
   *
   * @param os the output stream to write to
   * @param xpathExpression the XPath expression to filter fields
   * @param fragmentMode if true, skip XML declaration and root element
   * @param indent if true, enable indentation
   * @throws Exception if initialization fails
   */
  public AfpJacksonXmlWriter(OutputStream os, String xpathExpression, boolean fragmentMode, boolean indent)
      throws Exception {
    this.os = os;

    this.indentEnabled = indent;
    this.cos = new com.mgz.util.CountingOutputStream(new NonClosingOutputStream(os));
    this.xpathExpression = (xpathExpression == null || xpathExpression.isBlank()) ? null : xpathExpression;
    this.fragmentMode = fragmentMode;
    this.mapper = JacksonXmlMapperProvider.getMapper();
    // Fragment mapper to avoid repeated XML declarations
    this.fragmentMapper = JacksonXmlMapperProvider.getFragmentMapper();

    if (this.xpathExpression == null) {
      XMLOutputFactory xof = JacksonXmlMapperProvider.getOutputFactory();
      XMLStreamWriter2 rawXsw = (XMLStreamWriter2) xof.createXMLStreamWriter(cos, "UTF-8");
      this.baseXsw = new AfpXmlStreamWriter(rawXsw, cos);
      this.xsw = MnemonicPerformanceMonitor.isEnabled() ? new MnemonicXMLStreamWriter(this.baseXsw) : this.baseXsw;
      if (!fragmentMode) {
        this.xsw.writeStartDocument("UTF-8", "1.0");
        writeNewline();
        this.xsw.setPrefix("xsi", "http://www.w3.org/2001/XMLSchema-instance");
        this.xsw.writeStartElement("AFPDocument");
        this.xsw.writeNamespace("xsi", "http://www.w3.org/2001/XMLSchema-instance");
        writeNewline();
      } else {
        // In fragment mode, we need a root element for StAX validation and to avoid "Trying to output second root"
        this.xsw.writeStartElement("AfpFragments");
      }
    } else {
      this.xsw = null;
      this.baseXsw = null;
    }

    if (this.baseXsw != null) {
      this.baseFragmentGenerator = (ToXmlGenerator) fragmentMapper.getFactory().createGenerator(baseXsw);
    } else {
      this.baseFragmentGenerator = null;
    }
  }

  @Override
  public void flush() throws Exception {
    if (baseFragmentGenerator != null) {
      baseFragmentGenerator.flush();
    }
    if (xsw != null) {
      xsw.flush();
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

  private void writeFieldDirectly(StructuredField sf) throws Exception {
    int level = fragmentMode ? 0 : 1;
    if (!fragmentMode) {
      writePureIndent(level);
    }

    if (sf instanceof NOP_NoOperation nop) {
      writeNopDirectly(nop, level);
    } else if (sf instanceof TLE_TagLogicalElement tle) {
      writeTleDirectly(tle, level);
    } else if (sf instanceof BAG_BeginActiveEnvironmentGroup bag) {
      writeBagDirectly(bag, level);
    } else if (sf instanceof PTX_PresentationTextData ptx) {
      writePtxDirectly(ptx, level);
    } else if (sf instanceof MCF_MapCodedFont_Format2 mcf) {
      writeMcfDirectly(mcf, level);
    } else if (sf instanceof FNC_FontControl fnc) {
      writeFncDirectly(fnc, level);
    } else if (sf instanceof LND_LineDescriptor lnd) {
      writeLndDirectly(lnd, level);
    } else if (sf instanceof GAD_GraphicsData gad) {
      writeGadDirectly(gad, level);
    } else if (sf instanceof IPD_ImagePictureData ipd) {
      writeIpdDirectly(ipd, level);
    } else if (sf instanceof OBD_ObjectAreaDescriptor obd) {
      writeObdDirectly(obd, level);
    } else if (sf instanceof OBP_ObjectAreaPosition obp) {
      writeObpDirectly(obp, level);
    } else if (sf instanceof IDD_ImageDataDescriptor idd) {
      writeIddDirectly(idd, level);
    } else if (sf instanceof MIO_MapImageObject mio) {
      writeMioDirectly(mio, level);
    } else if (sf instanceof MCD_MapContainerData mcd) {
      writeMcdDirectly(mcd, level);
    } else if (sf instanceof MDR_MapDataResource mdr) {
      writeMdrDirectly(mdr, level);
    } else if (sf instanceof MSU_MapSuppression msu) {
      writeMsuDirectly(msu, level);
    } else if (sf instanceof MMC_MediumModificationControl mmc) {
      writeMmcDirectly(mmc, level);
    } else if (sf instanceof MGO_MapGraphicsObject mgo) {
      writeMgoDirectly(mgo, level);
    } else if (sf instanceof MPO_MapPageOverlay mpo) {
      writeMpoDirectly(mpo, level);
    } else if (sf instanceof com.mgz.afp.modca.BDT_BeginDocument bdt) {
      writeBdtDirectly(bdt, level);
    } else if (sf instanceof OCD_ObjectContainerData ocd) {
      writeOcdDirectly(ocd, level);
    } else if (sf instanceof BDI_BeginDocumentIndex bdi) {
      writeNameAndTripletsDirectly(bdi, "BDI_BeginDocumentIndex", level);
    } else if (sf instanceof BMO_BeginOverlay bmo) {
      writeBmoDirectly(bmo, level);
    } else if (sf instanceof BPS_BeginPageSegment bps) {
      writeBpsDirectly(bps, level);
    } else if (sf instanceof BRG_BeginResourceGroup brg) {
      writeNameAndTripletsDirectly(brg, "BRG_BeginResourceGroup", level);
    } else if (sf instanceof com.mgz.afp.modca.BNG_BeginNamedPageGroup bng) {
      writeNameAndTripletsDirectly(bng, "BNG_BeginNamedPageGroup", level);
    } else if (sf instanceof com.mgz.afp.modca.BPG_BeginPage bpg) {
      writeNameAndTripletsDirectly(bpg, "BPG_BeginPage", level);
    } else if (sf instanceof EDI_EndDocumentIndex edi) {
      writeNameDirectly(edi, "EDI_EndDocumentIndex", level);
    } else if (sf instanceof EMO_EndOverlay emo) {
      writeNameAndTripletsDirectly(emo, "EMO_EndOverlay", level);
    } else if (sf instanceof EPS_EndPageSegment eps) {
      writeNameDirectly(eps, "EPS_EndPageSegment", level);
    } else if (sf instanceof ERG_EndResourceGroup erg) {
      writeNameAndTripletsDirectly(erg, "ERG_EndResourceGroup", level);
    } else if (sf instanceof com.mgz.afp.modca.EDT_EndDocument edt) {
      writeNameAndTripletsDirectly(edt, "EDT_EndDocument", level);
    } else if (sf instanceof com.mgz.afp.modca.ENG_EndNamedPageGroup eng) {
      writeNameAndTripletsDirectly(eng, "ENG_EndNamedPageGroup", level);
    } else if (sf instanceof com.mgz.afp.modca.EPG_EndPage epg) {
      writeNameAndTripletsDirectly(epg, "EPG_EndPage", level);
    } else if (sf instanceof com.mgz.afp.modca.IEL_IndexElement iel) {
      writeTripletsAndTextDirectly(iel, "IEL_IndexElement", level);
    } else if (sf instanceof com.mgz.afp.modca.IPG_IncludePage ipg) {
      writeTripletsAndTextDirectly(ipg, "IPG_IncludePage", level);
    } else if (sf instanceof com.mgz.afp.modca.PGD_PageDescriptor pgd) {
      writePgdDirectly(pgd, level);
    } else if (sf instanceof PGP_PagePosition_Format1 pgp) {
      writePgpFormat1Directly(pgp, level);
    } else if (sf instanceof PGP_PagePosition_Format2 pgp) {
      writePgpFormat2Directly(pgp, level);
    } else if (sf instanceof com.mgz.afp.modca.MDD_MediumDescriptor mdd) {
      writeMddDirectly(mdd, level);
    } else if (sf instanceof com.mgz.afp.modca.IMM_InvokeMediumMap imm) {
      writeNameAndTripletsDirectly(imm, "IMM_InvokeMediumMap", level);
    } else if (sf instanceof com.mgz.afp.modca.BOC_BeginObjectContainer boc) {
      writeNameAndTripletsDirectly(boc, "BOC_BeginObjectContainer", level);
    } else if (sf instanceof com.mgz.afp.modca.EOC_EndObjectContainer eoc) {
      writeNameDirectly(eoc, "EOC_EndObjectContainer", level);
    } else if (sf instanceof com.mgz.afp.modca.BIM_BeginImageObject bim) {
      writeNameAndTripletsDirectly(bim, "BIM_BeginImageObject", level);
    } else if (sf instanceof com.mgz.afp.modca.EIM_EndImageObject eim) {
      writeNameDirectly(eim, "EIM_EndImageObject", level);
    } else if (sf instanceof com.mgz.afp.modca.PMC_PageModificationControl pmc) {
      writeTripletsAndTextDirectly(pmc, "PMC_PageModificationControl", level);
    } else if (sf instanceof com.mgz.afp.modca.PEC_PresentationEnvironmentControl pec) {
      writeTripletsAndTextDirectly(pec, "PEC_PresentationEnvironmentControl", level);
    } else if (sf instanceof com.mgz.afp.modca.IPS_IncludePageSegment ips) {
      writeTripletsAndTextDirectly(ips, "IPS_IncludePageSegment", level);
    } else if (sf instanceof com.mgz.afp.modca.IPO_IncludePageOverlay ipo) {
      writeTripletsAndTextDirectly(ipo, "IPO_IncludePageOverlay", level);
    } else if (sf instanceof com.mgz.afp.modca.MFC_MediumFinishingControl mfc) {
      writeTripletsAndTextDirectly(mfc, "MFC_MediumFinishingControl", level);
    } else if (sf instanceof com.mgz.afp.modca.PFC_PresentationFidelityControl pfc) {
      writeTripletsAndTextDirectly(pfc, "PFC_PresentationFidelityControl", level);
    } else if (sf instanceof BBC_BeginBarCodeObject bbc) {
      writeBbcDirectly(bbc, level);
    } else if (sf instanceof EBC_EndBarCodeObject ebc) {
      writeEbcDirectly(ebc, level);
    } else if (sf instanceof BDD_BarCodeDataDescriptor bdd) {
      writeBddDirectly(bdd, level);
    } else if (sf instanceof BDA_BarCodeData bda) {
      writeBdaDirectly(bda, level);
    } else {
      if (MnemonicPerformanceMonitor.isEnabled()) {
        String rootName = MnemonicPerformanceMonitor.getSimpleName(sf.getClass());
        String mnemonic = MnemonicPerformanceMonitor.extractMnemonicFromString(rootName);
        MnemonicPerformanceMonitor.startWriteWithMnemonic(mnemonic);
      }
      JacksonXmlMapperProvider.getCachedWriter(sf.getClass(), true, indentEnabled).writeValue(baseFragmentGenerator, sf);
      if (MnemonicPerformanceMonitor.isEnabled()) {
        MnemonicPerformanceMonitor.endWrite();
      }
    }

    if (!fragmentMode) {
      writeNewline();
    }
  }

  private void writeNopDirectly(NOP_NoOperation nop, int level) throws Exception {
    MnemonicPerformanceMonitor.startWriteWithMnemonic("NOP");
    String text = nop.getText();
    if (text != null && !text.isEmpty()) {
      baseXsw.writeStartElement("NOP_NoOperation");
      writeElement(baseXsw, level + 1, "text", text);
      writeIndent(baseXsw, level);
      baseXsw.writeEndElement();
    } else {
      byte[] data = nop.getData();
      if (data == null || data.length == 0) {
        baseXsw.writeEmptyElement("NOP_NoOperation");
      } else {
        baseXsw.writeStartElement("NOP_NoOperation");
        writeBinaryElement(baseXsw, level + 1, "binaryData", data);
        writeIndent(baseXsw, level);
        baseXsw.writeEndElement();
      }
    }
    MnemonicPerformanceMonitor.endWrite();
  }

  private void writeTleDirectly(TLE_TagLogicalElement tle, int level) throws Exception {
    MnemonicPerformanceMonitor.startWriteWithMnemonic("TLE");
    baseXsw.writeStartElement("TLE_TagLogicalElement");
    if (tle.getTriplets() != null && !tle.getTriplets().isEmpty()) {
      for (Triplet triplet : tle.getTriplets()) {
        writeTriplet(baseXsw, triplet, level + 1);
      }
    }
    if (tle.getText() != null) {
      writeElement(baseXsw, level + 1, "text", tle.getText());
    }
    writeIndent(baseXsw, level);
    baseXsw.writeEndElement();
    MnemonicPerformanceMonitor.endWrite();
  }

  private void writeMcdDirectly(MCD_MapContainerData mcd, int level) throws Exception {
    MnemonicPerformanceMonitor.startWriteWithMnemonic("MCD");
    baseXsw.writeStartElement("MCD_MapContainerData");
    writeElement(baseXsw, level + 1, "lengthOfRepeatingGroup", mcd.getLengthOfRepeatingGroup());
    if (mcd.getTriplet() != null) {
      writeTriplet(baseXsw, mcd.getTriplet(), level + 1);
    }
    writeIndent(baseXsw, level);
    baseXsw.writeEndElement();
    MnemonicPerformanceMonitor.endWrite();
  }

  private void writePgpFormat1Directly(PGP_PagePosition_Format1 pgp, int level) throws Exception {
    MnemonicPerformanceMonitor.startWriteWithMnemonic("PGP");
    baseXsw.writeStartElement("PGP_PagePosition_Format1");
    writeElement(baseXsw, level + 1, "xOrigin", pgp.getxOrigin());
    writeElement(baseXsw, level + 1, "yOrigin", pgp.getyOrigin());
    writeIndent(baseXsw, level);
    baseXsw.writeEndElement();
    MnemonicPerformanceMonitor.endWrite();
  }

  private void writePgpFormat2Directly(PGP_PagePosition_Format2 pgp, int level) throws Exception {
    MnemonicPerformanceMonitor.startWriteWithMnemonic("PGP");
    baseXsw.writeStartElement("PGP_PagePosition_Format2");
    int childLevel = level + 1;
    writeElement(baseXsw, childLevel, "constant0", pgp.getConstant0());
    if (pgp.getRepeatingGroups() != null) {
      for (IRepeatingGroup rg : pgp.getRepeatingGroups()) {
        if (rg instanceof PGP_PagePosition_Format2.PGP_RepeatingGroup pgpRg) {
          writeIndent(baseXsw, childLevel);
          baseXsw.writeStartElement("pgpRepeatingGroup");
          int rgLevel = childLevel + 1;
          writeElement(baseXsw, rgLevel, "repeatingGroupLength", pgpRg.getRepeatingGroupLength());
          writeElement(baseXsw, rgLevel, "xOrigin", pgpRg.getxOrigin());
          writeElement(baseXsw, rgLevel, "yOrigin", pgpRg.getyOrigin());
          if (pgpRg.getxRotation() != null) {
            writeElement(baseXsw, rgLevel, "xRotation", pgpRg.getxRotation().name());
          }
          if (pgpRg.getSheetSideAndPartitionSelection() != null) {
            writeElement(baseXsw, rgLevel, "sheetSideAndPartitionSelection", pgpRg.getSheetSideAndPartitionSelection().name());
          }
          if (pgpRg.getFlags() != null) {
            writeIndent(baseXsw, rgLevel);
            baseXsw.writeStartElement("flags");
            for (PGP_PagePosition_Format2.PGP_RepeatingGroup.PGP_RGFlag flag : pgpRg.getFlags()) {
              writeElement(baseXsw, rgLevel + 1, "pgpRgFlag", flag.name());
            }
            writeIndent(baseXsw, rgLevel);
            baseXsw.writeEndElement();
          }
          if (pgpRg.getPageModififationControlID() != null) {
            writeElement(baseXsw, rgLevel, "pageModififationControlID", pgpRg.getPageModififationControlID());
          }
          writeIndent(baseXsw, childLevel);
          baseXsw.writeEndElement();
        }
      }
    }
    writeIndent(baseXsw, level);
    baseXsw.writeEndElement();
    MnemonicPerformanceMonitor.endWrite();
  }

  private void writeMsuDirectly(MSU_MapSuppression msu, int level) throws Exception {
    MnemonicPerformanceMonitor.startWriteWithMnemonic("MSU");
    baseXsw.writeStartElement("MSU_MapSuppression");
    int childLevel = level + 1;
    if (msu.getRepeatingGroups() != null) {
      for (IRepeatingGroup rg : msu.getRepeatingGroups()) {
        if (rg instanceof MSU_MapSuppression.MSU_RepeatingGroup msuRg) {
          writeIndent(baseXsw, childLevel);
          baseXsw.writeStartElement("msuRepeatingGroup");
          int rgLevel = childLevel + 1;
          writeElement(baseXsw, rgLevel, "nameOfTextSuppresstion", msuRg.getNameOfTextSuppresstion());
          writeElement(baseXsw, rgLevel, "reserved8", msuRg.getReserved8());
          writeElement(baseXsw, rgLevel, "localID", msuRg.getLocalID());
          writeIndent(baseXsw, childLevel);
          baseXsw.writeEndElement();
        }
      }
    }
    writeIndent(baseXsw, level);
    baseXsw.writeEndElement();
    MnemonicPerformanceMonitor.endWrite();
  }

  private void writeMmcDirectly(MMC_MediumModificationControl mmc, int level) throws Exception {
    MnemonicPerformanceMonitor.startWriteWithMnemonic("MMC");
    baseXsw.writeStartElement("MMC_MediumModificationControl");
    int childLevel = level + 1;
    writeElement(baseXsw, childLevel, "mmcIdentifier", mmc.getMmcIdentifier());
    writeElement(baseXsw, childLevel, "constantData1", mmc.getConstantData1());
    if (mmc.getKeywords() != null && !mmc.getKeywords().isEmpty()) {
      writeIndent(baseXsw, childLevel);
      baseXsw.writeStartElement("keywords");
      int kwLevel = childLevel + 1;
      for (MMC_MediumModificationControl.MMC_KeyWord kw : mmc.getKeywords()) {
        writeIndent(baseXsw, kwLevel);
        baseXsw.writeStartElement("keyword");
        if (kw.keywordID() != null) {
          writeElement(baseXsw, kwLevel + 1, "keywordID", kw.keywordID().name());
        }
        writeElement(baseXsw, kwLevel + 1, "parameter", kw.parameter());
        writeIndent(baseXsw, kwLevel);
        baseXsw.writeEndElement();
      }
      writeIndent(baseXsw, childLevel);
      baseXsw.writeEndElement();
    }
    writeIndent(baseXsw, level);
    baseXsw.writeEndElement();
    MnemonicPerformanceMonitor.endWrite();
  }

  private void writeBagDirectly(BAG_BeginActiveEnvironmentGroup bag, int level) throws Exception {
    MnemonicPerformanceMonitor.startWriteWithMnemonic("BAG");
    baseXsw.writeStartElement("BAG_BeginActiveEnvironmentGroup");
    writeTripletsAndText(baseXsw, bag.getTriplets(), bag.getText(), level + 1, level);
    baseXsw.writeEndElement();
    MnemonicPerformanceMonitor.endWrite();
  }

  private void writeTripletsAndText(XMLStreamWriter2 writer, List<Triplet> triplets, String text, int level, int closingLevel) throws Exception {
    if (triplets != null) {
      for (Triplet triplet : triplets) {
        writeTriplet(writer, triplet, level);
      }
    }
    if (text != null && !text.isEmpty()) {
      writeElement(writer, level, "text", text);
    }
    writeIndent(writer, closingLevel);
  }

  private void writeTriplet(XMLStreamWriter2 writer, Triplet triplet, int level) throws Exception {
    int childLevel = level + 1;
    writeIndent(writer, level);
    if (triplet instanceof Triplet.FullyQualifiedName fqn) {
      writer.writeStartElement("FullyQualifiedName");
      writeElement(writer, childLevel, "type", fqn.getType().name());
      writeElement(writer, childLevel, "format", fqn.getFormat().name());
      writeElement(writer, childLevel, "nameAsString", fqn.getNameAsString());
      writeElement(writer, childLevel, "text", fqn.getText());
      writeIndent(writer, level);
      writer.writeEndElement();
    } else if (triplet instanceof Triplet.AttributeValue av) {
      writer.writeStartElement("AttributeValue");
      writeElement(writer, childLevel, "attributeValue", av.getAttributeValue());
      writeElement(writer, childLevel, "text", av.getText());
      writeIndent(writer, level);
      writer.writeEndElement();
    } else if (triplet instanceof Triplet.CodedGraphicCharacterSetGlobalID cgcs) {
      writer.writeStartElement("CodedGraphicCharacterSetGlobalID");
      writeElement(writer, childLevel, "graphicCharacterSetGlobalID", cgcs.getGraphicCharacterSetGlobalID());
      writeElement(writer, childLevel, "codePageGlobalID_codedCharacterSetID", cgcs.getCodePageGlobalID_codedCharacterSetID());
      writeIndent(writer, level);
      writer.writeEndElement();
    } else if (triplet instanceof Triplet.MappingOption mo) {
      writer.writeStartElement("MappingOption");
      writeElement(writer, childLevel, "dataObjecMapingOption", mo.getDataObjecMapingOption().name());
      writeIndent(writer, level);
      writer.writeEndElement();
    } else if (triplet instanceof Triplet.AttributeQualifier aq) {
      writer.writeStartElement("AttributeQualifier");
      writeElement(writer, childLevel, "sequenceNumber", aq.sequenceNumber);
      writeElement(writer, childLevel, "levelNumber", aq.levelNumber);
      writeIndent(writer, level);
      writer.writeEndElement();
    } else if (triplet instanceof Triplet.Comment c) {
      writer.writeStartElement("Comment");
      writeElement(writer, childLevel, "comment", c.comment);
      writeElement(writer, childLevel, "text", c.getText());
      writeIndent(writer, level);
      writer.writeEndElement();
    } else if (triplet instanceof Triplet.ResourceLocalIdentifier rli) {
      writer.writeStartElement("ResourceLocalIdentifier");
      if (rli.getResourceType() != null) {
        writeElement(writer, childLevel, "resourceType", rli.getResourceType().name());
      }
      writeElement(writer, childLevel, "resourceLocalID", rli.getResourceLocalID());
      writeIndent(writer, level);
      writer.writeEndElement();
    } else if (triplet instanceof Triplet.ObjectClassification oc) {
      writer.writeStartElement("ObjectClassification");
      writeElement(writer, childLevel, "reserved2", oc.reserved2);
      if (oc.objectClass != null) {
        writeElement(writer, childLevel, "objectClass", oc.objectClass.name());
      }
      if (oc.reserved4_5 != null) {
        writeElement(writer, childLevel, "reserved4_5", UtilCharacterEncoding.bytesToHexString(oc.reserved4_5));
      }
      if (oc.structureFlags != null) {
        writeIndent(writer, childLevel);
        writer.writeStartElement("structureFlags");
        for (Triplet.ObjectClassification.StructureFlag flag : oc.structureFlags) {
          writeElement(writer, childLevel + 1, "structureFlag", flag.name());
        }
        writeIndent(writer, childLevel);
        writer.writeEndElement();
      }
      if (oc.registeredObjectID != null) {
        writeElement(writer, childLevel, "registeredObjectID", UtilCharacterEncoding.bytesToHexString(oc.registeredObjectID));
      }
      writeElement(writer, childLevel, "objectTypeName", oc.objectTypeName);
      writeElement(writer, childLevel, "objectVersion", oc.objectVersion);
      writeElement(writer, childLevel, "companyName", oc.companyName);
      writeIndent(writer, level);
      writer.writeEndElement();
    } else if (triplet instanceof Triplet.MODCAInterchangeSet mis) {
      writer.writeStartElement("MODCAInterchangeSet");
      if (mis.type != null) {
        writeElement(writer, childLevel, "type", mis.type.name());
      }
      if (mis.identifier != null) {
        writeElement(writer, childLevel, "identifier", mis.identifier.name());
      }
      writeIndent(writer, level);
      writer.writeEndElement();
    } else if (triplet instanceof Triplet.CharacterRotation cr) {
      writer.writeStartElement("CharacterRotation");
      if (cr.characterRotation != null) {
        writeElement(writer, childLevel, "characterRotation", cr.characterRotation.name());
      }
      writeIndent(writer, level);
      writer.writeEndElement();
    } else if (triplet instanceof Triplet.ObjectByteOffset obo) {
      writer.writeStartElement("ObjectByteOffset");
      writeElement(writer, childLevel, "byteOffset", obo.byteOffset);
      if (obo.byteOffsetHighOrder != null) {
        writeElement(writer, childLevel, "byteOffsetHighOrder", obo.byteOffsetHighOrder);
      }
      writeIndent(writer, level);
      writer.writeEndElement();
    } else if (triplet instanceof Triplet.MeasurementUnits mu) {
      writer.writeStartElement("MeasurementUnits");
      if (mu.xUnitBase != null) {
        writeElement(writer, childLevel, "xUnitBase", mu.xUnitBase.name());
      }
      if (mu.yUnitBase != null) {
        writeElement(writer, childLevel, "yUnitBase", mu.yUnitBase.name());
      }
      writeElement(writer, childLevel, "xUnitsPerUnitbase", mu.xUnitsPerUnitbase);
      writeElement(writer, childLevel, "yUnitsPerUnitbase", mu.yUnitsPerUnitbase);
      writeIndent(writer, level);
      writer.writeEndElement();
    } else if (triplet instanceof Triplet.ObjectAreaSize oas) {
      writer.writeStartElement("ObjectAreaSize");
      writeElement(writer, childLevel, "sizeType_0x02", oas.sizeType_0x02);
      writeElement(writer, childLevel, "xSize", oas.xSize);
      writeElement(writer, childLevel, "ySize", oas.ySize);
      writeIndent(writer, level);
      writer.writeEndElement();
    } else if (triplet instanceof Triplet.AreaDefinition ad) {
      writer.writeStartElement("AreaDefinition");
      writeElement(writer, childLevel, "reserved2", ad.reserved2);
      writeElement(writer, childLevel, "xOrigin", ad.xOrigin);
      writeElement(writer, childLevel, "yOrigin", ad.yOrigin);
      writeElement(writer, childLevel, "xSize", ad.xSize);
      writeElement(writer, childLevel, "ySize", ad.ySize);
      writeIndent(writer, level);
      writer.writeEndElement();
    } else if (triplet instanceof Triplet.ColorSpecification cs) {
      writer.writeStartElement("ColorSpecification");
      writeElement(writer, childLevel, "reserved2", cs.reserved2);
      if (cs.colorSpace != null) {
        writeElement(writer, childLevel, "colorSpace", cs.colorSpace.name());
      }
      if (cs.reserved4_7 != null) {
        writeElement(writer, childLevel, "reserved4_7", UtilCharacterEncoding.bytesToHexString(cs.reserved4_7));
      }
      writeElement(writer, childLevel, "nrOfBitsComponent1", cs.nrOfBitsComponent1);
      writeElement(writer, childLevel, "nrOfBitsComponent2", cs.nrOfBitsComponent2);
      writeElement(writer, childLevel, "nrOfBitsComponent3", cs.nrOfBitsComponent3);
      writeElement(writer, childLevel, "nrOfBitsComponent4", cs.nrOfBitsComponent4);
      if (cs.colorValue != null) {
        writeElement(writer, childLevel, "colorValue", UtilCharacterEncoding.bytesToHexString(cs.colorValue));
      }
      writeIndent(writer, level);
      writer.writeEndElement();
    } else if (triplet instanceof Triplet.EncodingSchemeID esi) {
      writer.writeStartElement("EncodingSchemeID");
      if (esi.encodingSchemeForCodePage != null) {
        writeIndent(writer, childLevel);
        writer.writeStartElement("encodingSchemeForCodePage");
        for (Triplet.EncodingSchemeID.EncodingScheme es : esi.encodingSchemeForCodePage) {
          writeElement(writer, childLevel + 1, "encodingScheme", es.name());
        }
        writeIndent(writer, childLevel);
        writer.writeEndElement();
      }
      if (esi.encodingSchemeForUserData != null) {
        writeIndent(writer, childLevel);
        writer.writeStartElement("encodingSchemeForUserData");
        for (Triplet.EncodingSchemeID.EncodingScheme es : esi.encodingSchemeForUserData) {
          writeElement(writer, childLevel + 1, "encodingScheme", es.name());
        }
        writeIndent(writer, childLevel);
        writer.writeEndElement();
      }
      writeIndent(writer, level);
      writer.writeEndElement();
    } else if (triplet instanceof Triplet.ObjectCount oc) {
      writer.writeStartElement("ObjectCount");
      writeElement(writer, childLevel, "subordinateObjectType", oc.subordinateObjectType);
      writeElement(writer, childLevel, "reserved3", oc.reserved3);
      writeElement(writer, childLevel, "numberOfObjectsLow", oc.numberOfObjectsLow);
      if (oc.numberOfObjectsHigh != null) {
        writeElement(writer, childLevel, "numberOfObjectsHigh", oc.numberOfObjectsHigh);
      }
      writeIndent(writer, level);
      writer.writeEndElement();
    } else if (triplet instanceof Triplet.LocalObjectDateAndTimeStamp lodts) {
      writer.writeStartElement("LocalObjectDateAndTimeStamp");
      if (lodts.dateAndTimeStampType != null) {
        writeElement(writer, childLevel, "dateAndTimeStampType", lodts.dateAndTimeStampType.name());
      }
      writeElement(writer, childLevel, "hundreds", lodts.hundreds);
      writeElement(writer, childLevel, "tens", lodts.tens);
      writeElement(writer, childLevel, "dayOfYear", lodts.dayOfYear);
      writeElement(writer, childLevel, "hourOfDay", lodts.hourOfDay);
      writeElement(writer, childLevel, "minuteOfHour", lodts.minuteOfHour);
      writeElement(writer, childLevel, "secondOfMinute", lodts.secondOfMinute);
      writeElement(writer, childLevel, "hundredthOfSecond", lodts.hundredthOfSecond);
      writeIndent(writer, level);
      writer.writeEndElement();
    } else if (triplet instanceof Triplet.UniversalDateAndTimeStamp udts) {
      writer.writeStartElement("UniversalDateAndTimeStamp");
      writeElement(writer, childLevel, "reserved2", udts.reserved2);
      writeElement(writer, childLevel, "year", udts.year);
      writeElement(writer, childLevel, "monthOfYear", udts.monthOfYear);
      writeElement(writer, childLevel, "dayOfMonth", udts.dayOfMonth);
      writeElement(writer, childLevel, "hourOfDay", udts.hourOfDay);
      writeElement(writer, childLevel, "minuteOfHour", udts.minuteOfHour);
      writeElement(writer, childLevel, "secondOfMinute", udts.secondOfMinute);
      if (udts.timeZone != null) {
        writeElement(writer, childLevel, "timeZone", udts.timeZone.name());
      }
      writeElement(writer, childLevel, "diffHours", udts.diffHours);
      writeElement(writer, childLevel, "diffMinutes", udts.diffMinutes);
      writeIndent(writer, level);
      writer.writeEndElement();
    } else if (triplet instanceof Triplet.FontDescriptorSpecification fds) {
      writer.writeStartElement("FontDescriptorSpecification");
      if (fds.fontWeigthClass != null) {
        writeElement(writer, childLevel, "fontWeigthClass", fds.fontWeigthClass.name());
      }
      if (fds.fontWidthClass != null) {
        writeElement(writer, childLevel, "fontWidthClass", fds.fontWidthClass.name());
      }
      writeElement(writer, childLevel, "fontHeight", fds.fontHeight);
      writeElement(writer, childLevel, "fontWidth", fds.fontWidth);
      if (fds.fontDsFlags != null) {
        writeIndent(writer, childLevel);
        writer.writeStartElement("fontDsFlags");
        for (Triplet.FontDescriptorSpecification.FDS_FontDsFlag flag : fds.fontDsFlags) {
          writeElement(writer, childLevel + 1, "fdsFontDsFlag", flag.name());
        }
        writeIndent(writer, childLevel);
        writer.writeEndElement();
      }
      if (fds.reserved9_18 != null) {
        writeBinaryElement(writer, childLevel, "reserved9_18", fds.reserved9_18);
      }
      if (fds.fontUsFlags != null) {
        writeIndent(writer, childLevel);
        writer.writeStartElement("fontUsFlags");
        for (Triplet.FontDescriptorSpecification.FDS_FontUsFlag flag : fds.fontUsFlags) {
          writeElement(writer, childLevel + 1, "fdsFontUsFlag", flag.name());
        }
        writeIndent(writer, childLevel);
        writer.writeEndElement();
      }
      writeIndent(writer, level);
      writer.writeEndElement();
    } else if (triplet instanceof Triplet.ResourceObjectType rot) {
      writer.writeStartElement("ResourceObjectType");
      if (rot.objectType != null) {
        writeElement(writer, childLevel, "objectType", rot.objectType.name());
      }
      writeIndent(writer, level);
      writer.writeEndElement();
    } else {
      JacksonXmlMapperProvider.getCachedWriter(triplet.getClass(), true, indentEnabled).writeValue(baseFragmentGenerator, triplet);
    }
  }

  private void writeMcfDirectly(MCF_MapCodedFont_Format2 mcf, int level) throws Exception {
    MnemonicPerformanceMonitor.startWriteWithMnemonic("MCF");
    baseXsw.writeStartElement("MCF_MapCodedFont_Format2");
    List<IRepeatingGroup> repeatingGroups = mcf.getRepeatingGroups();
    if (repeatingGroups != null) {
      for (IRepeatingGroup rg : repeatingGroups) {
        writeIndent(baseXsw, level + 1);
        writeMcfRepeatingGroup(rg, level + 1);
      }
    }
    writeIndent(baseXsw, level);
    baseXsw.writeEndElement();
    MnemonicPerformanceMonitor.endWrite();
  }

  private void writeMcfRepeatingGroup(IRepeatingGroup rg, int level) throws Exception {
    baseXsw.writeStartElement("mcf2RepeatingGroup");
    if (rg instanceof RepeatingGroupWithTriplets rgt) {
      List<Triplet> triplets = rgt.getTriplets();
      if (triplets != null) {
        for (Triplet triplet : triplets) {
          writeTriplet(baseXsw, triplet, level + 1);
        }
      }
    }
    writeIndent(baseXsw, level);
    baseXsw.writeEndElement();
  }

  private void writePtxDirectly(PTX_PresentationTextData ptx, int level) throws Exception {
    MnemonicPerformanceMonitor.startWriteWithMnemonic("PTX");
    baseXsw.writeStartElement("PTX_PresentationTextData");
    List<PTOCAControlSequence> sequences = ptx.getControlSequences();
    if (sequences != null) {
      boolean ptxDebug = com.mgz.util.PTXPerformanceMonitor.isEnabled();
      for (PTOCAControlSequence cs : sequences) {
        writeIndent(baseXsw, level + 1);
        long csStart = ptxDebug ? System.nanoTime() : 0;
        long csStartCount = ptxDebug ? cos.getCount() : 0;
        writeControlSequence(cs, level + 1);
        if (csStart > 0) {
          int payloadSize = 0;
          if (cs instanceof PTOCAControlSequence.GraphicCharacters gc) {
            payloadSize = gc.getData() != null ? gc.getData().length : 0;
          } else if (cs.getCsi() != null) {
            payloadSize = Math.max(0, cs.getCsi().getLength() - 2);
          }
          com.mgz.util.PTXPerformanceMonitor.recordPtocaWrite(
              MnemonicPerformanceMonitor.getSimpleName(cs.getClass()), System.nanoTime() - csStart, payloadSize, cos.getCount() - csStartCount);
        }
      }
    }
    writeIndent(baseXsw, level);
    baseXsw.writeEndElement();
    MnemonicPerformanceMonitor.endWrite();
  }

  private void writeControlSequence(PTOCAControlSequence cs, int level) throws Exception {
    int childLevel = level + 1;
    if (cs instanceof PTOCAControlSequence.TRN_TransparentData trn) {
      MnemonicPerformanceMonitor.startWriteWithMnemonic("TRN");
      baseXsw.writeRawBytes(XmlTagTemplates.TRN_START, 0, XmlTagTemplates.TRN_START.length);
      writeElement(baseXsw, childLevel, "transparentData", trn.getTransparentData());
      if (trn.getEncoding() != null && trn.getTransparentDataEBCDIC() != null) {
        writeIndent(baseXsw, childLevel);
        baseXsw.writeRawBytes(XmlTagTemplates.TEXT_START, 0, XmlTagTemplates.TEXT_START.length);
        baseXsw.writeEbcdic(trn.getTransparentDataEBCDIC(), 0, trn.getTransparentDataEBCDIC().length, trn.getEncoding());
        baseXsw.writeRawBytes(XmlTagTemplates.TEXT_END, 0, XmlTagTemplates.TEXT_END.length);
      } else {
        writeElement(baseXsw, childLevel, "text", trn.getText());
      }
      writeIndent(baseXsw, level);
      baseXsw.writeRawBytes(XmlTagTemplates.TRN_END, 0, XmlTagTemplates.TRN_END.length);
      MnemonicPerformanceMonitor.endWrite();
    } else if (cs instanceof PTOCAControlSequence.GraphicCharacters gc) {
      MnemonicPerformanceMonitor.startWriteWithMnemonic("GraphicCharacters");
      baseXsw.writeRawBytes(XmlTagTemplates.GC_START, 0, XmlTagTemplates.GC_START.length);
      if (gc.getEncoding() != null && gc.getData() != null) {
        writeIndent(baseXsw, childLevel);
        baseXsw.writeRawBytes(XmlTagTemplates.TEXT_START, 0, XmlTagTemplates.TEXT_START.length);
        baseXsw.writeEbcdic(gc.getData(), 0, gc.getData().length, gc.getEncoding());
        baseXsw.writeRawBytes(XmlTagTemplates.TEXT_END, 0, XmlTagTemplates.TEXT_END.length);
      } else {
        writeElement(baseXsw, childLevel, "text", gc.getText());
      }
      writeIndent(baseXsw, level);
      baseXsw.writeRawBytes(XmlTagTemplates.GC_END, 0, XmlTagTemplates.GC_END.length);
      MnemonicPerformanceMonitor.endWrite();
    } else if (cs instanceof PTOCAControlSequence.AMI_AbsoluteMoveInline ami) {
      MnemonicPerformanceMonitor.startWriteWithMnemonic("AMI");
      baseXsw.writeEmptyElement("AMI_AbsoluteMoveInline");
      baseXsw.writeIntAttribute(null, null, "displacement", ami.getDisplacement());
      MnemonicPerformanceMonitor.endWrite();
    } else if (cs instanceof PTOCAControlSequence.AMB_AbsoluteMoveBaseline amb) {
      MnemonicPerformanceMonitor.startWriteWithMnemonic("AMB");
      baseXsw.writeEmptyElement("AMB_AbsoluteMoveBaseline");
      baseXsw.writeIntAttribute(null, null, "displacement", amb.getDisplacement());
      MnemonicPerformanceMonitor.endWrite();
    } else if (cs instanceof PTOCAControlSequence.RMI_RelativeMoveInline rmi) {
      MnemonicPerformanceMonitor.startWriteWithMnemonic("RMI");
      baseXsw.writeEmptyElement("RMI_RelativeMoveInline");
      baseXsw.writeIntAttribute(null, null, "increment", rmi.getIncrement());
      MnemonicPerformanceMonitor.endWrite();
    } else if (cs instanceof PTOCAControlSequence.RMB_RelativeMoveBaseline rmb) {
      MnemonicPerformanceMonitor.startWriteWithMnemonic("RMB");
      baseXsw.writeEmptyElement("RMB_RelativeMoveBaseline");
      baseXsw.writeIntAttribute(null, null, "increment", rmb.getIncrement());
      MnemonicPerformanceMonitor.endWrite();
    } else if (cs instanceof PTOCAControlSequence.SIM_SetInlineMargin sim) {
      MnemonicPerformanceMonitor.startWriteWithMnemonic("SIM");
      baseXsw.writeEmptyElement("SIM_SetInlineMargin");
      baseXsw.writeIntAttribute(null, null, "displacement", sim.getDisplacement());
      MnemonicPerformanceMonitor.endWrite();
    } else if (cs instanceof PTOCAControlSequence.SCFL_SetCodedFontLocal scfl) {
      MnemonicPerformanceMonitor.startWriteWithMnemonic("SCFL");
      baseXsw.writeEmptyElement("SCFL_SetCodedFontLocal");
      baseXsw.writeIntAttribute(null, null, "codedFontLocalID", scfl.getCodedFontLocalID());
      MnemonicPerformanceMonitor.endWrite();
    } else if (cs instanceof PTOCAControlSequence.SBI_SetBaselineIncrement sbi) {
      MnemonicPerformanceMonitor.startWriteWithMnemonic("SBI");
      baseXsw.writeEmptyElement("SBI_SetBaselineIncrement");
      baseXsw.writeIntAttribute(null, null, "increment", sbi.getIncrement());
      MnemonicPerformanceMonitor.endWrite();
    } else if (cs instanceof PTOCAControlSequence.BLN_BeginLine) {
      MnemonicPerformanceMonitor.startWriteWithMnemonic("BLN");
      baseXsw.writeEmptyElement("BLN_BeginLine");
      MnemonicPerformanceMonitor.endWrite();
    } else if (cs instanceof PTOCAControlSequence.BSU_BeginSuppression bsu) {
      MnemonicPerformanceMonitor.startWriteWithMnemonic("BSU");
      baseXsw.writeEmptyElement("BSU_BeginSuppression");
      baseXsw.writeIntAttribute(null, null, "suppressionID", bsu.getSuppressionID());
      MnemonicPerformanceMonitor.endWrite();
    } else if (cs instanceof PTOCAControlSequence.ESU_EndSuppression esu) {
      MnemonicPerformanceMonitor.startWriteWithMnemonic("ESU");
      baseXsw.writeEmptyElement("ESU_EndSuppression");
      baseXsw.writeIntAttribute(null, null, "suppressionID", esu.getSuppressionID());
      MnemonicPerformanceMonitor.endWrite();
    } else if (cs instanceof PTOCAControlSequence.STO_SetTextOrientation sto) {
      MnemonicPerformanceMonitor.startWriteWithMnemonic("STO");
      baseXsw.writeEmptyElement("STO_SetTextOrientation");
      if (sto.getxOrientation() != null) {
        baseXsw.writeAttribute("xOrientation", sto.getxOrientation().name());
      }
      if (sto.getyOrientation() != null) {
        baseXsw.writeAttribute("yOrientation", sto.getyOrientation().name());
      }
      MnemonicPerformanceMonitor.endWrite();
    } else if (cs instanceof PTOCAControlSequence.STC_SetTextColor stc) {
      MnemonicPerformanceMonitor.startWriteWithMnemonic("STC");
      baseXsw.writeEmptyElement("STC_SetTextColor");
      if (stc.getForegroundColor() != null) {
        baseXsw.writeAttribute("foregroundColor", stc.getForegroundColor().name());
      }
      if (stc.getPrecision() != null) {
        baseXsw.writeAttribute("precision", stc.getPrecision().name());
      }
      MnemonicPerformanceMonitor.endWrite();
    } else if (cs instanceof PTOCAControlSequence.USC_Underscore usc) {
      MnemonicPerformanceMonitor.startWriteWithMnemonic("USC");
      baseXsw.writeEmptyElement("USC_Underscore");
      if (usc.getBypassFlag() != null) {
        baseXsw.writeAttribute("bypassFlag", usc.getBypassFlag().name());
      }
      MnemonicPerformanceMonitor.endWrite();
    } else if (cs instanceof PTOCAControlSequence.SIA_SetIntercharacterAdjustment sia) {
      MnemonicPerformanceMonitor.startWriteWithMnemonic("SIA");
      baseXsw.writeEmptyElement("SIA_SetIntercharacterAdjustment");
      baseXsw.writeIntAttribute(null, null, "adjustment", sia.getAdjustment());
      if (sia.getDirection() != null) {
        baseXsw.writeAttribute("direction", sia.getDirection().name());
      }
      MnemonicPerformanceMonitor.endWrite();
    } else if (cs instanceof PTOCAControlSequence.SVI_SetVariableSpaceCharacterIncrement svi) {
      MnemonicPerformanceMonitor.startWriteWithMnemonic("SVI");
      baseXsw.writeEmptyElement("SVI_SetVariableSpaceCharacterIncrement");
      baseXsw.writeIntAttribute(null, null, "increment", svi.getIncrement());
      MnemonicPerformanceMonitor.endWrite();
    } else if (cs instanceof PTOCAControlSequence.SEC_SetExtendedTextColor sec) {
      MnemonicPerformanceMonitor.startWriteWithMnemonic("SEC");
      baseXsw.writeStartElement("SEC_SetExtendedTextColor");
      writeElement(baseXsw, childLevel, "colorSpace", sec.getColorSpace().name());
      writeElement(baseXsw, childLevel, "nrOfBitsComponent1", sec.getNrOfBitsComponent1());
      writeElement(baseXsw, childLevel, "nrOfBitsComponent2", sec.getNrOfBitsComponent2());
      writeElement(baseXsw, childLevel, "nrOfBitsComponent3", sec.getNrOfBitsComponent3());
      writeElement(baseXsw, childLevel, "nrOfBitsComponent4", sec.getNrOfBitsComponent4());
      if (sec.getColorValue() != null) {
        writeBinaryElement(baseXsw, childLevel, "colorValue", sec.getColorValue());
      }
      writeIndent(baseXsw, level);
      baseXsw.writeEndElement();
      MnemonicPerformanceMonitor.endWrite();
    } else if (cs instanceof PTOCAControlSequence.DIR_DrawIaxisRule dir) {
      MnemonicPerformanceMonitor.startWriteWithMnemonic("DIR");
      baseXsw.writeStartElement("DIR_DrawIaxisRule");
      writeElement(baseXsw, childLevel, "length", dir.getLength());
      if (dir.getWidth() != null) {
        writeElement(baseXsw, childLevel, "width", dir.getWidth());
      }
      if (dir.getWidthFraction() != null) {
        writeElement(baseXsw, childLevel, "widthFraction", dir.getWidthFraction());
      }
      writeIndent(baseXsw, level);
      baseXsw.writeEndElement();
      MnemonicPerformanceMonitor.endWrite();
    } else if (cs instanceof PTOCAControlSequence.DBR_DrawBaxisRule dbr) {
      MnemonicPerformanceMonitor.startWriteWithMnemonic("DBR");
      baseXsw.writeStartElement("DBR_DrawBaxisRule");
      writeElement(baseXsw, childLevel, "length", dbr.getLength());
      if (dbr.getWidth() != null) {
        writeElement(baseXsw, childLevel, "width", dbr.getWidth());
      }
      if (dbr.getWidthFraction() != null) {
        writeElement(baseXsw, childLevel, "widthFraction", dbr.getWidthFraction());
      }
      writeIndent(baseXsw, level);
      baseXsw.writeEndElement();
      MnemonicPerformanceMonitor.endWrite();
    } else if (cs instanceof PTOCAControlSequence.NOP_NoOperation nop) {
      MnemonicPerformanceMonitor.startWriteWithMnemonic("NOP");
      baseXsw.writeStartElement("NOP_NoOperation");
      if (nop.getText() != null) {
        writeElement(baseXsw, childLevel, "text", nop.getText());
      }
      if (nop.getIgnoredData() != null) {
        writeBinaryElement(baseXsw, childLevel, "ignoredData", nop.getIgnoredData());
      }
      writeIndent(baseXsw, level);
      baseXsw.writeEndElement();
      MnemonicPerformanceMonitor.endWrite();
    } else if (cs instanceof PTOCAControlSequence.TBM_TemporaryBaselineMove tbm) {
      MnemonicPerformanceMonitor.startWriteWithMnemonic("TBM");
      baseXsw.writeStartElement("TBM_TemporaryBaselineMove");
      if (tbm.getDirection() != null) {
        writeElement(baseXsw, childLevel, "direction", tbm.getDirection().name());
      }
      if (tbm.getPrecision() != null) {
        writeElement(baseXsw, childLevel, "precision", tbm.getPrecision().name());
      }
      if (tbm.getTemporaryBaselineIncrement() != null) {
        writeElement(baseXsw, childLevel, "temporaryBaselineIncrement", tbm.getTemporaryBaselineIncrement());
      }
      writeIndent(baseXsw, level);
      baseXsw.writeEndElement();
      MnemonicPerformanceMonitor.endWrite();
    } else if (cs instanceof PTOCAControlSequence.OVS_Overstrike ovs) {
      MnemonicPerformanceMonitor.startWriteWithMnemonic("OVS");
      baseXsw.writeStartElement("OVS_Overstrike");
      if (ovs.getBypassFlag() != null) {
        writeElement(baseXsw, childLevel, "bypassFlag", ovs.getBypassFlag().name());
      }
      writeElement(baseXsw, childLevel, "overStrikeCharacterCodePoint", ovs.getOverStrikeCharacterCodePoint());
      writeElement(baseXsw, childLevel, "text", ovs.getText());
      writeIndent(baseXsw, level);
      baseXsw.writeEndElement();
      MnemonicPerformanceMonitor.endWrite();
    } else if (cs instanceof PTOCAControlSequence.RPS_RepeatString rps) {
      MnemonicPerformanceMonitor.startWriteWithMnemonic("RPS");
      baseXsw.writeStartElement("RPS_RepeatString");
      writeElement(baseXsw, childLevel, "repeatLength", rps.getRepeatLength());
      if (rps.getText() != null) {
        writeElement(baseXsw, childLevel, "text", rps.getText());
      }
      if (rps.getRepeatData() != null) {
        writeBinaryElement(baseXsw, childLevel, "repeatData", rps.getRepeatData());
      }
      writeIndent(baseXsw, level);
      baseXsw.writeEndElement();
      MnemonicPerformanceMonitor.endWrite();
    } else {
      if (MnemonicPerformanceMonitor.isEnabled()) {
        String simpleName = MnemonicPerformanceMonitor.getSimpleName(cs.getClass());
        String mnemonic = MnemonicPerformanceMonitor.extractMnemonicFromString(simpleName);
        MnemonicPerformanceMonitor.startWriteWithMnemonic(mnemonic);
      }
      JacksonXmlMapperProvider.getCachedWriter(cs.getClass(), true, indentEnabled).writeValue(baseFragmentGenerator, cs);
      if (MnemonicPerformanceMonitor.isEnabled()) {
        MnemonicPerformanceMonitor.endWrite();
      }
    }
  }

  private void writeFncDirectly(FNC_FontControl fnc, int level) throws Exception {
    MnemonicPerformanceMonitor.startWriteWithMnemonic("FNC");
    baseXsw.writeStartElement("FNC_FontControl");
    int childLevel = level + 1;
    writeElement(baseXsw, childLevel, "retired0", fnc.getRetired0());
    if (fnc.getPatternTechnologyIdentifier() != null) {
      writeElement(baseXsw, childLevel, "patternTechnologyIdentifier", fnc.getPatternTechnologyIdentifier().name());
    }
    writeElement(baseXsw, childLevel, "reserved2", fnc.getReserved2());
    if (fnc.getFontUseFlags() != null) {
      writeIndent(baseXsw, childLevel);
      baseXsw.writeStartElement("fontUseFlags");
      for (FNC_FontControl.FncFontUseFlag flag : fnc.getFontUseFlags()) {
        writeElement(baseXsw, childLevel + 1, "fncFontUseFlag", flag.name());
      }
      writeIndent(baseXsw, childLevel);
      baseXsw.writeEndElement();
    }
    if (fnc.getxUnitBase() != null) {
      writeElement(baseXsw, childLevel, "xUnitBase", fnc.getxUnitBase().name());
    }
    if (fnc.getyUnitBase() != null) {
      writeElement(baseXsw, childLevel, "yUnitBase", fnc.getyUnitBase().name());
    }
    writeElement(baseXsw, childLevel, "xUnitsPerUnitBase", fnc.getxUnitsPerUnitBase());
    writeElement(baseXsw, childLevel, "yUnitsPerUnitBase", fnc.getyUnitsPerUnitBase());
    writeElement(baseXsw, childLevel, "maxCharacterBoxWidth", fnc.getMaxCharacterBoxWidth());
    writeElement(baseXsw, childLevel, "maxCharacterBoxHeight", fnc.getMaxCharacterBoxHeight());
    writeElement(baseXsw, childLevel, "fnoRepeatingGroupLength", fnc.getFnoRepeatingGroupLength());
    writeElement(baseXsw, childLevel, "fniRepeatingGroupLength", fnc.getFniRepeatingGroupLength());
    if (fnc.getRasterPatternDataAlignment() != null) {
      writeElement(baseXsw, childLevel, "rasterPatternDataAlignment", fnc.getRasterPatternDataAlignment().name());
    }
    writeElement(baseXsw, childLevel, "rasterPatternDataCount", fnc.getRasterPatternDataCount());
    writeElement(baseXsw, childLevel, "fnpRepeatingGroupLength", fnc.getFnpRepeatingGroupLength());
    writeElement(baseXsw, childLevel, "fnmRepeatingGroupLength", fnc.getFnmRepeatingGroupLength());
    writeElement(baseXsw, childLevel, "shapeResolutionXUnitBase10Inches", fnc.getShapeResolutionXUnitBase10Inches());
    writeElement(baseXsw, childLevel, "shapeResolutionYUnitBase10Inches", fnc.getShapeResolutionYUnitBase10Inches());
    writeElement(baseXsw, childLevel, "shapeResolutionXUnitsPerUnitBase", fnc.getShapeResolutionXUnitsPerUnitBase());
    writeElement(baseXsw, childLevel, "shapeResolutionYUnitsPerUnitBase", fnc.getShapeResolutionYUnitsPerUnitBase());
    writeElement(baseXsw, childLevel, "outlinePatternDataCount", fnc.getOutlinePatternDataCount());
    if (fnc.getReserved32_34() != null) {
      writeBinaryElement(baseXsw, childLevel, "reserved32_34", fnc.getReserved32_34());
    }
    writeElement(baseXsw, childLevel, "fnnRepeatingGroupLength", fnc.getFnnRepeatingGroupLength());
    writeElement(baseXsw, childLevel, "fnnDataCount", fnc.getFnnDataCount());
    writeElement(baseXsw, childLevel, "fnnIbmNameGcgidCount", fnc.getFnnIbmNameGcgidCount());

    if (fnc.getTriplets() != null) {
      for (Triplet triplet : fnc.getTriplets()) {
        writeTriplet(baseXsw, triplet, childLevel);
      }
    }
    writeIndent(baseXsw, level);
    baseXsw.writeEndElement();
    MnemonicPerformanceMonitor.endWrite();
  }

  private void writeLndDirectly(LND_LineDescriptor lnd, int level) throws Exception {
    MnemonicPerformanceMonitor.startWriteWithMnemonic("LND");
    baseXsw.writeStartElement("LND_LineDescriptor");
    int childLevel = level + 1;
    if (lnd.getFlags() != null) {
      writeIndent(baseXsw, childLevel);
      baseXsw.writeStartElement("flags");
      for (LND_LineDescriptor.LND_Flag flag : lnd.getFlags()) {
        writeElement(baseXsw, childLevel + 1, "lndFlag", flag.name());
      }
      writeIndent(baseXsw, childLevel);
      baseXsw.writeEndElement();
    }
    writeElement(baseXsw, childLevel, "inlinePosition", lnd.getInlinePosition());
    writeElement(baseXsw, childLevel, "baselinePosition", lnd.getBaselinePosition());
    if (lnd.getInlineOrientation() != null) {
      writeElement(baseXsw, childLevel, "inlineOrientation", lnd.getInlineOrientation().name());
    }
    if (lnd.getBaselineOrientation() != null) {
      writeElement(baseXsw, childLevel, "baselineOrientation", lnd.getBaselineOrientation().name());
    }
    writeElement(baseXsw, childLevel, "primaryFontLocalId", lnd.getPrimaryFontLocalId());
    writeElement(baseXsw, childLevel, "channelCode", lnd.getChannelCode());
    writeElement(baseXsw, childLevel, "nextLNDIfSkipping", lnd.getNextLNDIfSkipping());
    writeElement(baseXsw, childLevel, "nextLNDIfSpacing", lnd.getNextLNDIfSpacing());
    writeElement(baseXsw, childLevel, "nextLNDIfReusingData", lnd.getNextLNDIfReusingData());
    writeElement(baseXsw, childLevel, "suppressionTokenName", lnd.getSuppressionTokenName());
    writeElement(baseXsw, childLevel, "shiftOutLocalFontID", lnd.getShiftOutLocalFontID());
    writeElement(baseXsw, childLevel, "dataStartPosition", lnd.getDataStartPosition());
    writeElement(baseXsw, childLevel, "dataLength", lnd.getDataLength());
    if (lnd.getTextColor() != null) {
      writeElement(baseXsw, childLevel, "textColor", lnd.getTextColor().name());
    }
    writeElement(baseXsw, childLevel, "nextLNDIfConditionalProcessing", lnd.getNextLNDIfConditionalProcessing());
    writeElement(baseXsw, childLevel, "subpageID", lnd.getSubpageID());
    writeElement(baseXsw, childLevel, "ccpIdentifier", lnd.getCcpIdentifier());

    if (lnd.getTriplets() != null) {
      for (Triplet triplet : lnd.getTriplets()) {
        writeTriplet(baseXsw, triplet, childLevel);
      }
    }
    writeIndent(baseXsw, level);
    baseXsw.writeEndElement();
    MnemonicPerformanceMonitor.endWrite();
  }

  private void writeGadDirectly(GAD_GraphicsData gad, int level) throws Exception {
    MnemonicPerformanceMonitor.startWriteWithMnemonic("GAD");
    baseXsw.writeStartElement("GAD_GraphicsData");
    int childLevel = level + 1;
    List<GAD_DrawingOrder> orders = gad.getDrawingOrders();
    if (orders != null) {
      for (GAD_DrawingOrder order : orders) {
        writeDrawingOrderDirectly(baseXsw, order, childLevel);
      }
    }
    writeIndent(baseXsw, level);
    baseXsw.writeEndElement();
    MnemonicPerformanceMonitor.endWrite();
  }

  private void writeDrawingOrderDirectly(XMLStreamWriter2 writer, GAD_DrawingOrder order, int level) throws Exception {
    int childLevel = level + 1;
    writeIndent(writer, level);
    if (order instanceof GAD_DrawingOrder.GNOP1_NopOperation) {
      writer.writeEmptyElement("GNOP1_NopOperation");
    } else if (order instanceof GAD_DrawingOrder.GCLINE_LineAtCurrentPosition gcline) {
      MnemonicPerformanceMonitor.startWriteWithMnemonic("GCLINE");
      writeDrawingOrderWithPoints(writer, gcline, "GCLINE_LineAtCurrentPosition", level);
      MnemonicPerformanceMonitor.endWrite();
    } else if (order instanceof GAD_DrawingOrder.GLINE_LineAtGivenPosition gline) {
      MnemonicPerformanceMonitor.startWriteWithMnemonic("GLINE");
      writeDrawingOrderWithPoints(writer, gline, "GLINE_LineAtGivenPosition", level);
      MnemonicPerformanceMonitor.endWrite();
    } else if (order instanceof GAD_DrawingOrder.GCMRK_MarkerAtCurrentPosition gcmrk) {
      MnemonicPerformanceMonitor.startWriteWithMnemonic("GCMRK");
      writeDrawingOrderWithPoints(writer, gcmrk, "GCMRK_MarkerAtCurrentPosition", level);
      MnemonicPerformanceMonitor.endWrite();
    } else if (order instanceof GAD_DrawingOrder.GMRK_MarkerAtGivenPosition gmrk) {
      MnemonicPerformanceMonitor.startWriteWithMnemonic("GMRK");
      writeDrawingOrderWithPoints(writer, gmrk, "GMRK_MarkerAtGivenPosition", level);
      MnemonicPerformanceMonitor.endWrite();
    } else if (order instanceof GAD_DrawingOrder.GCFLT_FilletAtCurrentPosition gcflt) {
      MnemonicPerformanceMonitor.startWriteWithMnemonic("GCFLT");
      writeDrawingOrderWithPoints(writer, gcflt, "GCFLT_FilletAtCurrentPosition", level);
      MnemonicPerformanceMonitor.endWrite();
    } else if (order instanceof GAD_DrawingOrder.GFLT_FilletAtGivenPosition gflt) {
      MnemonicPerformanceMonitor.startWriteWithMnemonic("GFLT");
      writeDrawingOrderWithPoints(writer, gflt, "GFLT_FilletAtGivenPosition", level);
      MnemonicPerformanceMonitor.endWrite();
    } else if (order instanceof GAD_DrawingOrder.GCCBEZ_CubicBezierCurveAtCurrentPosition gccbez) {
      MnemonicPerformanceMonitor.startWriteWithMnemonic("GCCBEZ");
      writeDrawingOrderWithPoints(writer, gccbez, "GCCBEZ_CubicBezierCurveAtCurrentPosition", level);
      MnemonicPerformanceMonitor.endWrite();
    } else if (order instanceof GAD_DrawingOrder.GCBEZ_CubicBezierCurveAtGivenPosition gcbez) {
      MnemonicPerformanceMonitor.startWriteWithMnemonic("GCBEZ");
      writeDrawingOrderWithPoints(writer, gcbez, "GCBEZ_CubicBezierCurveAtGivenPosition", level);
      MnemonicPerformanceMonitor.endWrite();
    } else if (order instanceof GAD_DrawingOrder.GCFARC_FullArcAtCurrentPosition gcfarc) {
      MnemonicPerformanceMonitor.startWriteWithMnemonic("GCFARC");
      writer.writeEmptyElement("GCFARC_FullArcAtCurrentPosition");
      writer.writeIntAttribute(null, null, "multiplierIntegerPortion", gcfarc.getMultiplierIntegerPortion());
      writer.writeIntAttribute(null, null, "multiplierFractionalPortion", gcfarc.getMultiplierFractionalPortion());
      MnemonicPerformanceMonitor.endWrite();
    } else if (order instanceof GAD_DrawingOrder.GCOMT_Comment gcomt) {
      MnemonicPerformanceMonitor.startWriteWithMnemonic("GCOMT");
      writer.writeStartElement("GCOMT_Comment");
      writer.writeIntAttribute(null, null, "lengthOfFollowingData", gcomt.getLengthOfFollowingData());
      if (gcomt.getText() != null) {
        writer.writeAttribute("text", gcomt.getText());
      }
      if (gcomt.comment != null) {
        writeBinaryElement(writer, childLevel, "comment", gcomt.comment);
      }
      writeIndent(writer, level);
      writer.writeEndElement();
      MnemonicPerformanceMonitor.endWrite();
    } else if (order instanceof GAD_DrawingOrder.GSCP_SetCurrentPosition gcp) {
      MnemonicPerformanceMonitor.startWriteWithMnemonic("GSCP");
      writer.writeEmptyElement("GSCP_SetCurrentPosition");
      writer.writeIntAttribute(null, null, "coordinateX", gcp.getCoordinateX());
      writer.writeIntAttribute(null, null, "coordinateY", gcp.getCoordinateY());
      MnemonicPerformanceMonitor.endWrite();
    } else if (order instanceof GAD_DrawingOrder.GSCOL_SetColor gsc) {
      MnemonicPerformanceMonitor.startWriteWithMnemonic("GSCOL");
      if (gsc.getColor() != null) {
        writer.writeEmptyElement("GSCOL_SetColor");
        writer.writeAttribute("color", gsc.getColor().name());
      } else {
        writer.writeEmptyElement("GSCOL_SetColor");
      }
      MnemonicPerformanceMonitor.endWrite();
    } else if (order instanceof GAD_DrawingOrder.GSCS_SetCharacterSet gscs) {
      MnemonicPerformanceMonitor.startWriteWithMnemonic("GSCS");
      writer.writeEmptyElement("GSCS_SetCharacterSet");
      writer.writeIntAttribute(null, null, "characterSetLocalID", gscs.getCharacterSetLocalID());
      MnemonicPerformanceMonitor.endWrite();
    } else if (order instanceof GAD_DrawingOrder.GSPS_SetPatternSet gsps) {
      MnemonicPerformanceMonitor.startWriteWithMnemonic("GSPS");
      writer.writeEmptyElement("GSPS_SetPatternSet");
      writer.writeIntAttribute(null, null, "patternLocalID", gsps.patternLocalID);
      MnemonicPerformanceMonitor.endWrite();
    } else if (order instanceof GAD_DrawingOrder.GSMX_SetMix gsmx) {
      MnemonicPerformanceMonitor.startWriteWithMnemonic("GSMX");
      writer.writeEmptyElement("GSMX_SetMix");
      writer.writeIntAttribute(null, null, "mixMode", gsmx.mixMode);
      MnemonicPerformanceMonitor.endWrite();
    } else if (order instanceof GAD_DrawingOrder.GSBMX_SetBackgroundMix gsbmx) {
      MnemonicPerformanceMonitor.startWriteWithMnemonic("GSBMX");
      writer.writeEmptyElement("GSBMX_SetBackgroundMix");
      writer.writeIntAttribute(null, null, "mixMode", gsbmx.mixMode);
      MnemonicPerformanceMonitor.endWrite();
    } else if (order instanceof GAD_DrawingOrder.GSFLW_SetFractionLineWidth gsflw) {
      MnemonicPerformanceMonitor.startWriteWithMnemonic("GSFLW");
      writer.writeEmptyElement("GSFLW_SetFractionLineWidth");
      writer.writeIntAttribute(null, null, "integralMultiplier", gsflw.integralMultiplier);
      writer.writeIntAttribute(null, null, "fractionalMultiplier", gsflw.fractionalMultiplier);
      MnemonicPerformanceMonitor.endWrite();
    } else if (order instanceof GAD_DrawingOrder.GSLT_SetLineType gslt) {
      MnemonicPerformanceMonitor.startWriteWithMnemonic("GSLT");
      writer.writeEmptyElement("GSLT_SetLineType");
      writer.writeIntAttribute(null, null, "lineType", gslt.lineType);
      MnemonicPerformanceMonitor.endWrite();
    } else if (order instanceof GAD_DrawingOrder.GSPIK_SetPickIdentifier gspik) {
      MnemonicPerformanceMonitor.startWriteWithMnemonic("GSPIK");
      writer.writeEmptyElement("GSPIK_SetPickIdentifier");
      writer.writeIntAttribute(null, null, "pickIdentifier", gspik.pickIdentifier);
      MnemonicPerformanceMonitor.endWrite();
    } else if (order instanceof GAD_DrawingOrder.GSGCH_SegmentCharacteristics gsgch) {
      MnemonicPerformanceMonitor.startWriteWithMnemonic("GSGCH");
      writer.writeStartElement("GSGCH_SegmentCharacteristics");
      writer.writeIntAttribute(null, null, "lengthOfFollowingData", gsgch.lengthOfFollowingData);
      writer.writeIntAttribute(null, null, "identificationCode", gsgch.getIdentificationCode());
      if (gsgch.getParameters() != null) {
        writeBinaryElement(writer, childLevel, "parameters", gsgch.getParameters());
      }
      writeIndent(writer, level);
      writer.writeEndElement();
      MnemonicPerformanceMonitor.endWrite();
    } else if (order instanceof GAD_DrawingOrder.GSECOL_SetExtendedColor gsecol) {
      MnemonicPerformanceMonitor.startWriteWithMnemonic("GSECOL");
      if (gsecol.getColor() != null) {
        writer.writeEmptyElement("GSECOL_SetExtendedColor");
        writer.writeAttribute("color", gsecol.getColor().name());
      } else {
        writer.writeEmptyElement("GSECOL_SetExtendedColor");
      }
      MnemonicPerformanceMonitor.endWrite();
    } else if (order instanceof GAD_DrawingOrder.GSCC_SetCharacterCell gscc) {
      writer.writeStartElement("GSCC_SetCharacterCell");
      writeElement(writer, childLevel, "lengthOfFollowingData", gscc.lengthOfFollowingData);
      writeElement(writer, childLevel, "widthOfCharacterCellIntegerPart", gscc.getWidthOfCharacterCellIntegerPart());
      writeElement(writer, childLevel, "heightOfCharacterCellIntegerPart", gscc.getHeightOfCharacterCellIntegerPart());
      if (gscc.getWidthOfCharacterCellFractionalPart() != null) {
        writeElement(writer, childLevel, "widthOfCharacterCellFractionalPart", gscc.getWidthOfCharacterCellFractionalPart());
      }
      if (gscc.getHeightOfCharacterCellFractionalPart() != null) {
        writeElement(writer, childLevel, "heightOfCharacterCellFractionalPart", gscc.getHeightOfCharacterCellFractionalPart());
      }
      writeIndent(writer, level);
      writer.writeEndElement();
    } else if (order instanceof GAD_DrawingOrder.GSCA_SetCharacterAngle gsca) {
      writer.writeStartElement("GSCA_SetCharacterAngle");
      writeElement(writer, childLevel, "lengthOfFollowingData", gsca.lengthOfFollowingData);
      if (gsca.getAnglePoint() != null) {
        writeIndent(writer, childLevel);
        writer.writeStartElement("anglePoint");
        writeElement(writer, childLevel + 1, "xCoordinate", gsca.getAnglePoint().xCoordinate());
        writeElement(writer, childLevel + 1, "yCoordinate", gsca.getAnglePoint().yCoordinate());
        writeIndent(writer, childLevel);
        writer.writeEndElement();
      }
      writeIndent(writer, level);
      writer.writeEndElement();
    } else if (order instanceof GAD_DrawingOrder.GSCH_SetCharacterShear gsch) {
      writer.writeStartElement("GSCH_SetCharacterShear");
      writeElement(writer, childLevel, "lengthOfFollowingData", gsch.lengthOfFollowingData);
      writeElement(writer, childLevel, "dividendOfShearRatio", gsch.getDividendOfShearRatio());
      writeElement(writer, childLevel, "divisorOfShearRatio", gsch.getDivisorOfShearRatio());
      writeIndent(writer, level);
      writer.writeEndElement();
    } else if (order instanceof GAD_DrawingOrder.GSMC_SetMarkerCell gsmc) {
      writer.writeStartElement("GSMC_SetMarkerCell");
      writeElement(writer, childLevel, "lengthOfFollowingData", gsmc.lengthOfFollowingData);
      writeElement(writer, childLevel, "widthOfMarkerCell", gsmc.getWidthOfMarkerCell());
      writeElement(writer, childLevel, "heightOfMarkerCell", gsmc.getHeightOfMarkerCell());
      writeIndent(writer, level);
      writer.writeEndElement();
    } else if (order instanceof GAD_DrawingOrder.GSLW_SetLineWidth gslw) {
      MnemonicPerformanceMonitor.startWriteWithMnemonic("GSLW");
      writer.writeEmptyElement("GSLW_SetLineWidth");
      writer.writeIntAttribute(null, null, "lineWidth", gslw.lineWidth);
      MnemonicPerformanceMonitor.endWrite();
    } else if (order instanceof GAD_DrawingOrder.GSLE_SetLineEnd gsle) {
      MnemonicPerformanceMonitor.startWriteWithMnemonic("GSLE");
      if (gsle.lineEnd != null) {
        writer.writeEmptyElement("GSLE_SetLineEnd");
        writer.writeAttribute("lineEnd", gsle.lineEnd.name());
      } else {
        writer.writeEmptyElement("GSLE_SetLineEnd");
      }
      MnemonicPerformanceMonitor.endWrite();
    } else if (order instanceof GAD_DrawingOrder.GSLJ_SetLineJoin gslj) {
      MnemonicPerformanceMonitor.startWriteWithMnemonic("GSLJ");
      if (gslj.lineJoin != null) {
        writer.writeEmptyElement("GSLJ_SetLineJoin");
        writer.writeAttribute("lineJoin", gslj.lineJoin.name());
      } else {
        writer.writeEmptyElement("GSLJ_SetLineJoin");
      }
      MnemonicPerformanceMonitor.endWrite();
    } else if (order instanceof GAD_DrawingOrder.GSPT_SetPatternSymbol gspt) {
      MnemonicPerformanceMonitor.startWriteWithMnemonic("GSPT");
      writer.writeEmptyElement("GSPT_SetPatternSymbol");
      writer.writeIntAttribute(null, null, "patternSymbolCodePoint", gspt.patternSymbolCodePoint);
      MnemonicPerformanceMonitor.endWrite();
    } else if (order instanceof GAD_DrawingOrder.GSMT_SetMarkerSymbol gsmt) {
      MnemonicPerformanceMonitor.startWriteWithMnemonic("GSMT");
      writer.writeEmptyElement("GSMT_SetMarkerSymbol");
      writer.writeIntAttribute(null, null, "markerSymbolCodePoint", gsmt.markerSymbolCodePoint);
      MnemonicPerformanceMonitor.endWrite();
    } else if (order instanceof GAD_DrawingOrder.GSCR_SetCharacterPrecision gscr) {
      MnemonicPerformanceMonitor.startWriteWithMnemonic("GSCR");
      writer.writeEmptyElement("GSCR_SetCharacterPrecision");
      writer.writeIntAttribute(null, null, "characterPrecision", gscr.characterPrecision);
      MnemonicPerformanceMonitor.endWrite();
    } else if (order instanceof GAD_DrawingOrder.GSCD_SetCharacterDirection gscd) {
      MnemonicPerformanceMonitor.startWriteWithMnemonic("GSCD");
      writer.writeEmptyElement("GSCD_SetCharacterDirection");
      writer.writeIntAttribute(null, null, "characterDirection", gscd.characterDirection);
      MnemonicPerformanceMonitor.endWrite();
    } else if (order instanceof GAD_DrawingOrder.GSMP_SetMarkerPrecision gsmp) {
      MnemonicPerformanceMonitor.startWriteWithMnemonic("GSMP");
      writer.writeEmptyElement("GSMP_SetMarkerPrecision");
      writer.writeIntAttribute(null, null, "markerPrecision", gsmp.markerPrecision);
      MnemonicPerformanceMonitor.endWrite();
    } else if (order instanceof GAD_DrawingOrder.GSMS_SetMarkerSet gsms) {
      MnemonicPerformanceMonitor.startWriteWithMnemonic("GSMS");
      writer.writeEmptyElement("GSMS_SetMarkerSet");
      writer.writeIntAttribute(null, null, "markerSetLocalID", gsms.markerSetLocalID);
      MnemonicPerformanceMonitor.endWrite();
    } else if (order instanceof GAD_DrawingOrder.GSCLT_SetCustomLineType gsclt) {
      MnemonicPerformanceMonitor.startWriteWithMnemonic("GSCLT");
      writer.writeStartElement("GSCLT_SetCustomLineType");
      writeElement(writer, childLevel, "lengthOfFollowingData", gsclt.lengthOfFollowingData);
      if (gsclt.repeatingGroups != null) {
        writeIndent(writer, childLevel);
        writer.writeStartElement("repeatingGroups");
        for (GAD_DrawingOrder.GSCLT_SetCustomLineType.DashMoveRepeatingGroup rg : gsclt.repeatingGroups) {
          writeIndent(writer, childLevel + 1);
          writer.writeStartElement("DashMoveRepeatingGroup");
          writeElement(writer, childLevel + 2, "dashInteger", rg.dashInteger());
          writeElement(writer, childLevel + 2, "dashFractional", rg.dashFractional());
          writeElement(writer, childLevel + 2, "moveInteger", rg.moveInteger());
          writeElement(writer, childLevel + 2, "moveFractional", rg.moveFractional());
          writeIndent(writer, childLevel + 1);
          writer.writeEndElement();
        }
        writeIndent(writer, childLevel);
        writer.writeEndElement();
      }
      writeIndent(writer, level);
      writer.writeEndElement();
      MnemonicPerformanceMonitor.endWrite();
    } else if (order instanceof GAD_DrawingOrder.GSAP_SetArcParameters gsap) {
      writer.writeStartElement("GSAP_SetArcParameters");
      writeElement(writer, childLevel, "lengthOfFollowingData", gsap.lengthOfFollowingData);
      writeElement(writer, childLevel, "arcTransformP", gsap.getArcTransformP());
      writeElement(writer, childLevel, "arcTransformQ", gsap.getArcTransformQ());
      writeElement(writer, childLevel, "arcTransformR", gsap.getArcTransformR());
      writeElement(writer, childLevel, "arcTransformS", gsap.getArcTransformS());
      writeIndent(writer, level);
      writer.writeEndElement();
    } else if (order instanceof GAD_DrawingOrder.GSPRP_SetPatternReferencePoint gsprp) {
      writer.writeStartElement("GSPRP_SetPatternReferencePoint");
      writeElement(writer, childLevel, "lengthOfFollowingData", gsprp.lengthOfFollowingData);
      writeElement(writer, childLevel, "flags", gsprp.getFlags());
      writeElement(writer, childLevel, "reserved3", gsprp.getReserved3());
      writeElement(writer, childLevel, "coordinateX", gsprp.getCoordinateX());
      writeElement(writer, childLevel, "coordinateY", gsprp.getCoordinateY());
      writeIndent(writer, level);
      writer.writeEndElement();
    } else if (order instanceof GAD_DrawingOrder.GCPARC_PartialArcAtCurrentPosition gcparc) {
      writer.writeStartElement("GCPARC_PartialArcAtCurrentPosition");
      writeElement(writer, childLevel, "lengthOfFollowingData", gcparc.lengthOfFollowingData);
      if (gcparc.getArcCenter() != null) {
        writeIndent(writer, childLevel);
        writer.writeStartElement("arcCenter");
        writeElement(writer, childLevel + 1, "xCoordinate", gcparc.getArcCenter().xCoordinate());
        writeElement(writer, childLevel + 1, "yCoordinate", gcparc.getArcCenter().yCoordinate());
        writeIndent(writer, childLevel);
        writer.writeEndElement();
      }
      writeElement(writer, childLevel, "multiplierIntegerPortion", gcparc.getMultiplierIntegerPortion());
      writeElement(writer, childLevel, "multiplierFractionalPortion", gcparc.getMultiplierFractionalPortion());
      writeElement(writer, childLevel, "startAngle", gcparc.getStartAngle());
      writeElement(writer, childLevel, "sweepAngle", gcparc.getSweepAngle());
      writeIndent(writer, level);
      writer.writeEndElement();
    } else if (order instanceof GAD_DrawingOrder.GSPCOL_SetProcessColor gspcol) {
      MnemonicPerformanceMonitor.startWriteWithMnemonic("GSPCOL");
      writer.writeStartElement("GSPCOL_SetProcessColor");
      writeElement(writer, childLevel, "lengthOfFollowingData", gspcol.lengthOfFollowingData);
      writeElement(writer, childLevel, "reserved2", gspcol.getReserved2());
      if (gspcol.getColorSpace() != null) {
        writeElement(writer, childLevel, "colorSpace", gspcol.getColorSpace().name());
      }
      writeElement(writer, childLevel, "reserved4_7", gspcol.getReserved4_7());
      writeElement(writer, childLevel, "nrOfBitsComponent1", gspcol.getNrOfBitsComponent1());
      writeElement(writer, childLevel, "nrOfBitsComponent2", gspcol.getNrOfBitsComponent2());
      writeElement(writer, childLevel, "nrOfBitsComponent3", gspcol.getNrOfBitsComponent3());
      writeElement(writer, childLevel, "nrOfBitsComponent4", gspcol.getNrOfBitsComponent4());
      if (gspcol.getColorValue() != null) {
        writeBinaryElement(writer, childLevel, "colorValue", gspcol.getColorValue());
      }
      writeIndent(writer, level);
      writer.writeEndElement();
      MnemonicPerformanceMonitor.endWrite();
    } else if (order instanceof GAD_DrawingOrder.GFARC_FullArcAtGivenPosition gfarc) {
      writer.writeStartElement("GFARC_FullArcAtGivenPosition");
      writeElement(writer, childLevel, "lengthOfFollowingData", gfarc.lengthOfFollowingData);
      if (gfarc.getArcCenter() != null) {
        writeIndent(writer, childLevel);
        writer.writeStartElement("arcCenter");
        writeElement(writer, childLevel + 1, "xCoordinate", gfarc.getArcCenter().xCoordinate());
        writeElement(writer, childLevel + 1, "yCoordinate", gfarc.getArcCenter().yCoordinate());
        writeIndent(writer, childLevel);
        writer.writeEndElement();
      }
      writeElement(writer, childLevel, "multiplierIntegerPortion", gfarc.getMultiplierIntegerPortion());
      writeElement(writer, childLevel, "multiplierFractionalPortion", gfarc.getMultiplierFractionalPortion());
      writeIndent(writer, level);
      writer.writeEndElement();
    } else if (order instanceof GAD_DrawingOrder.GCBIMG_BeginImageAtCurrentPosition gcbimg) {
      writer.writeStartElement("GCBIMG_BeginImageAtCurrentPosition");
      writeElement(writer, childLevel, "lengthOfFollowingData", gcbimg.lengthOfFollowingData);
      writeElement(writer, childLevel, "formatOfImageData", gcbimg.getFormatOfImageData());
      writeElement(writer, childLevel, "reserved3", gcbimg.getReserved3());
      writeElement(writer, childLevel, "widthOfImageInImagePoints", gcbimg.getWidthOfImageInImagePoints());
      writeElement(writer, childLevel, "heightOfImageInImagePoints", gcbimg.getHeightOfImageInImagePoints());
      writeIndent(writer, level);
      writer.writeEndElement();
    } else if (order instanceof GAD_DrawingOrder.GBIMG_BeginImageAtGivenPosition gbimg) {
      writer.writeStartElement("GBIMG_BeginImageAtGivenPosition");
      writeElement(writer, childLevel, "lengthOfFollowingData", gbimg.lengthOfFollowingData);
      if (gbimg.getOrigin() != null) {
        writeIndent(writer, childLevel);
        writer.writeStartElement("origin");
        writeElement(writer, childLevel + 1, "xCoordinate", gbimg.getOrigin().xCoordinate());
        writeElement(writer, childLevel + 1, "yCoordinate", gbimg.getOrigin().yCoordinate());
        writeIndent(writer, childLevel);
        writer.writeEndElement();
      }
      writeElement(writer, childLevel, "formatOfImageData", gbimg.getFormatOfImageData());
      writeElement(writer, childLevel, "reserved3", gbimg.getReserved3());
      writeElement(writer, childLevel, "widthOfImageInImagePoints", gbimg.getWidthOfImageInImagePoints());
      writeElement(writer, childLevel, "heightOfImageInImagePoints", gbimg.getHeightOfImageInImagePoints());
      writeIndent(writer, level);
      writer.writeEndElement();
    } else if (order instanceof GAD_DrawingOrder.GPARC_PartialArcAtGivenPosition gparc) {
      writer.writeStartElement("GPARC_PartialArcAtGivenPosition");
      writeElement(writer, childLevel, "lengthOfFollowingData", gparc.lengthOfFollowingData);
      if (gparc.getLineStartPoint() != null) {
        writeIndent(writer, childLevel);
        writer.writeStartElement("lineStartPoint");
        writeElement(writer, childLevel + 1, "xCoordinate", gparc.getLineStartPoint().xCoordinate());
        writeElement(writer, childLevel + 1, "yCoordinate", gparc.getLineStartPoint().yCoordinate());
        writeIndent(writer, childLevel);
        writer.writeEndElement();
      }
      if (gparc.getArcCenter() != null) {
        writeIndent(writer, childLevel);
        writer.writeStartElement("arcCenter");
        writeElement(writer, childLevel + 1, "xCoordinate", gparc.getArcCenter().xCoordinate());
        writeElement(writer, childLevel + 1, "yCoordinate", gparc.getArcCenter().yCoordinate());
        writeIndent(writer, childLevel);
        writer.writeEndElement();
      }
      writeElement(writer, childLevel, "multiplierIntegerPortion", gparc.getMultiplierIntegerPortion());
      writeElement(writer, childLevel, "multiplierFractionalPortion", gparc.getMultiplierFractionalPortion());
      writeElement(writer, childLevel, "startAngle", gparc.getStartAngle());
      writeElement(writer, childLevel, "sweepAngle", gparc.getSweepAngle());
      writeIndent(writer, level);
      writer.writeEndElement();
    } else if (order instanceof GAD_DrawingOrder.GEXO_ExtendedOrder gexo) {
      writer.writeStartElement("GEXO_ExtendedOrder");
      writeElement(writer, childLevel, "qualifier", gexo.qualifier);
      writeElement(writer, childLevel, "lengthOfFollowingData", gexo.lengthOfFollowingData);
      if (gexo.getExtendedData() != null) {
        writeBinaryElement(writer, childLevel, "extendedData", gexo.getExtendedData());
      }
      writeIndent(writer, level);
      writer.writeEndElement();
    } else if (order instanceof GAD_DrawingOrder.GCCHST_CharacterStringAtCurrentPosition gcchst) {
      writer.writeStartElement("GCCHST_CharacterStringAtCurrentPosition");
      writeElement(writer, childLevel, "text", gcchst.getText());
      writeIndent(writer, level);
      writer.writeEndElement();
    } else if (order instanceof GAD_DrawingOrder.GCHST_CharacterStringAtGivenPosition gchst) {
      writer.writeStartElement("GCHST_CharacterStringAtGivenPosition");
      if (gchst.getOriginPoint() != null) {
        writeIndent(writer, childLevel);
        writer.writeStartElement("originPoint");
        writeElement(writer, childLevel + 1, "xCoordinate", gchst.getOriginPoint().xCoordinate());
        writeElement(writer, childLevel + 1, "yCoordinate", gchst.getOriginPoint().yCoordinate());
        writeIndent(writer, childLevel);
        writer.writeEndElement();
      }
      writeElement(writer, childLevel, "text", gchst.getText());
      writeIndent(writer, level);
      writer.writeEndElement();
    } else if (order instanceof GAD_DrawingOrder.GESEG_EndSegment) {
      writer.writeEmptyElement("GESEG_EndSegment");
    } else if (order instanceof GAD_DrawingOrder.GEPROL_EndProlog geprol) {
      MnemonicPerformanceMonitor.startWriteWithMnemonic("GEPROL");
      writer.writeEmptyElement("GEPROL_EndProlog");
      writer.writeIntAttribute(null, null, "reserved0", geprol.getReserved0());
      MnemonicPerformanceMonitor.endWrite();
    } else if (order instanceof GAD_DrawingOrder.GBSEG_BeginSegment gbseg) {
      MnemonicPerformanceMonitor.startWriteWithMnemonic("GBSEG");
      writer.writeStartElement("GBSEG_BeginSegment");
      if (gbseg.getNameOfSegment() != null) {
        writer.writeAttribute("nameOfSegment", gbseg.getNameOfSegment());
      }
      if (gbseg.getText() != null) {
        writer.writeAttribute("text", gbseg.getText());
      }
      if (gbseg.getDrawingOrders() != null) {
        for (GAD_DrawingOrder childOrder : gbseg.getDrawingOrders()) {
          writeDrawingOrderDirectly(writer, childOrder, childLevel);
        }
      }
      writeIndent(writer, level);
      writer.writeEndElement();
      MnemonicPerformanceMonitor.endWrite();
    } else if (order instanceof GAD_DrawingOrder.GBCP_BeginCustomPattern gbcp) {
      MnemonicPerformanceMonitor.startWriteWithMnemonic("GBCP");
      writer.writeStartElement("GBCP_BeginCustomPattern");
      writeElement(writer, childLevel, "lengthOfFollowingData", gbcp.lengthOfFollowingData);
      writeElement(writer, childLevel, "reserved2_3", gbcp.reserved2_3);
      writeElement(writer, childLevel, "flags", gbcp.flags);
      writeElement(writer, childLevel, "patternSet", gbcp.patternSet);
      writeElement(writer, childLevel, "patternSymbol", gbcp.patternSymbol);
      writeElement(writer, childLevel, "xLeftWindow", gbcp.xLeftWindow);
      writeElement(writer, childLevel, "xRightWindow", gbcp.xRightWindow);
      writeElement(writer, childLevel, "yBottomWindow", gbcp.yBottomWindow);
      writeElement(writer, childLevel, "yTopWindow", gbcp.yTopWindow);
      writeIndent(writer, level);
      writer.writeEndElement();
      MnemonicPerformanceMonitor.endWrite();
    } else if (order instanceof GAD_DrawingOrder.GDPT_DeletePattern gdpt) {
      MnemonicPerformanceMonitor.startWriteWithMnemonic("GDPT");
      writer.writeStartElement("GDPT_DeletePattern");
      writeElement(writer, childLevel, "lengthOfFollowingData", gdpt.lengthOfFollowingData);
      writeElement(writer, childLevel, "reserved2_3", gdpt.reserved2_3);
      writeElement(writer, childLevel, "patternSet", gdpt.patternSet);
      if (gdpt.patternSymbol != null) {
        writeElement(writer, childLevel, "patternSymbol", gdpt.patternSymbol);
      }
      writeIndent(writer, level);
      writer.writeEndElement();
      MnemonicPerformanceMonitor.endWrite();
    } else if (order instanceof GAD_DrawingOrder.GECP_EndCustomPattern gecp) {
      MnemonicPerformanceMonitor.startWriteWithMnemonic("GECP");
      writer.writeEmptyElement("GECP_EndCustomPattern");
      writer.writeIntAttribute(null, null, "reserved0", gecp.getReserved0());
      MnemonicPerformanceMonitor.endWrite();
    } else if (order instanceof GAD_DrawingOrder.GBAR_BeginArea gbar) {
      MnemonicPerformanceMonitor.startWriteWithMnemonic("GBAR");
      writer.writeEmptyElement("GBAR_BeginArea");
      writer.writeIntAttribute(null, null, "internalFlags", gbar.getInternalFlags());
      MnemonicPerformanceMonitor.endWrite();
    } else if (order instanceof GAD_DrawingOrder.GEAR_EndArea gear) {
      MnemonicPerformanceMonitor.startWriteWithMnemonic("GEAR");
      String text = gear.getText();
      if (text != null && !text.isEmpty()) {
        writer.writeEmptyElement("GEAR_EndArea");
        writer.writeAttribute("text", text);
      } else {
        writer.writeEmptyElement("GEAR_EndArea");
      }
      MnemonicPerformanceMonitor.endWrite();
    } else if (order instanceof GAD_DrawingOrder.GIMD_ImageData gimd) {
      writer.writeStartElement("GIMD_ImageData");
      if (gimd.getImageData() != null) {
        writeBinaryElement(writer, childLevel, "imageData", gimd.getImageData());
      }
      writeIndent(writer, level);
      writer.writeEndElement();
    } else if (order instanceof GAD_DrawingOrder.GEIMG_EndImage geimg) {
      MnemonicPerformanceMonitor.startWriteWithMnemonic("GEIMG");
      writer.writeStartElement("GEIMG_EndImage");
      writeElement(writer, childLevel, "lengthOfFollowingData", geimg.getLengthOfFollowingData());
      if (geimg.reservedData != null) {
        writeBinaryElement(writer, childLevel, "reservedData", geimg.reservedData);
      }
      writeIndent(writer, level);
      writer.writeEndElement();
      MnemonicPerformanceMonitor.endWrite();
    } else if (order instanceof GAD_DrawingOrder.GCBOX_BoxAtCurrentPosition gcbox) {
      writer.writeStartElement("GCBOX_BoxAtCurrentPosition");
      writeElement(writer, childLevel, "reserved2_3", gcbox.getReserved2_3());
      if (gcbox.getDiagonalCorner() != null) {
        writeIndent(writer, childLevel);
        writer.writeStartElement("diagonalCorner");
        writeElement(writer, childLevel + 1, "xCoordinate", gcbox.getDiagonalCorner().xCoordinate());
        writeElement(writer, childLevel + 1, "yCoordinate", gcbox.getDiagonalCorner().yCoordinate());
        writeIndent(writer, childLevel);
        writer.writeEndElement();
      }
      if (gcbox.getxAxisLengthForRoundCorner() != null) {
        writeElement(writer, childLevel, "xAxisLengthForRoundCorner", gcbox.getxAxisLengthForRoundCorner());
      }
      if (gcbox.getyAxisLengthForRoundCorner() != null) {
        writeElement(writer, childLevel, "yAxisLengthForRoundCorner", gcbox.getyAxisLengthForRoundCorner());
      }
      writeIndent(writer, level);
      writer.writeEndElement();
    } else if (order instanceof GAD_DrawingOrder.GBOX_BoxAtGivenPosition gbox) {
      writer.writeStartElement("GBOX_BoxAtGivenPosition");
      writeElement(writer, childLevel, "reserved2_3", gbox.getReserved2_3());
      if (gbox.getFirstCorner() != null) {
        writeIndent(writer, childLevel);
        writer.writeStartElement("firstCorner");
        writeElement(writer, childLevel + 1, "xCoordinate", gbox.getFirstCorner().xCoordinate());
        writeElement(writer, childLevel + 1, "yCoordinate", gbox.getFirstCorner().yCoordinate());
        writeIndent(writer, childLevel);
        writer.writeEndElement();
      }
      if (gbox.getDiagonalCorner() != null) {
        writeIndent(writer, childLevel);
        writer.writeStartElement("diagonalCorner");
        writeElement(writer, childLevel + 1, "xCoordinate", gbox.getDiagonalCorner().xCoordinate());
        writeElement(writer, childLevel + 1, "yCoordinate", gbox.getDiagonalCorner().yCoordinate());
        writeIndent(writer, childLevel);
        writer.writeEndElement();
      }
      if (gbox.getxAxisLengthForRoundCorner() != null) {
        writeElement(writer, childLevel, "xAxisLengthForRoundCorner", gbox.getxAxisLengthForRoundCorner());
      }
      if (gbox.getyAxisLengthForRoundCorner() != null) {
        writeElement(writer, childLevel, "yAxisLengthForRoundCorner", gbox.getyAxisLengthForRoundCorner());
      }
      writeIndent(writer, level);
      writer.writeEndElement();
    } else if (order instanceof GAD_DrawingOrder.GCRLINE_RelativeLineAtCurrentPosition gcrline) {
      MnemonicPerformanceMonitor.startWriteWithMnemonic("GCRLINE");
      writer.writeStartElement("GCRLINE_RelativeLineAtCurrentPosition");
      if (gcrline.relativeOffsets != null) {
        writeIndent(writer, childLevel);
        writer.writeStartElement("relativeOffsets");
        for (GAD_DrawingOrder.GOCA_RelativePoint rp : gcrline.relativeOffsets) {
          writeIndent(writer, childLevel + 1);
          writer.writeStartElement("GOCA_RelativePoint");
          writeElement(writer, childLevel + 2, "xOffset", rp.xOffset());
          writeElement(writer, childLevel + 2, "yOffset", rp.yOffset());
          writeIndent(writer, childLevel + 1);
          writer.writeEndElement();
        }
        writeIndent(writer, childLevel);
        writer.writeEndElement();
      }
      writeIndent(writer, level);
      writer.writeEndElement();
      MnemonicPerformanceMonitor.endWrite();
    } else if (order instanceof GAD_DrawingOrder.GRLINE_RelativeLineAtGivenPosition grline) {
      MnemonicPerformanceMonitor.startWriteWithMnemonic("GRLINE");
      writer.writeStartElement("GRLINE_RelativeLineAtGivenPosition");
      if (grline.startPoint != null) {
        writeIndent(writer, childLevel);
        writer.writeStartElement("startPoint");
        writeElement(writer, childLevel + 1, "xCoordinate", grline.startPoint.xCoordinate());
        writeElement(writer, childLevel + 1, "yCoordinate", grline.startPoint.yCoordinate());
        writeIndent(writer, childLevel);
        writer.writeEndElement();
      }
      if (grline.relativeOffsets != null) {
        writeIndent(writer, childLevel);
        writer.writeStartElement("relativeOffsets");
        for (GAD_DrawingOrder.GOCA_RelativePoint rp : grline.relativeOffsets) {
          writeIndent(writer, childLevel + 1);
          writer.writeStartElement("GOCA_RelativePoint");
          writeElement(writer, childLevel + 2, "xOffset", rp.xOffset());
          writeElement(writer, childLevel + 2, "yOffset", rp.yOffset());
          writeIndent(writer, childLevel + 1);
          writer.writeEndElement();
        }
        writeIndent(writer, childLevel);
        writer.writeEndElement();
      }
      writeIndent(writer, level);
      writer.writeEndElement();
      MnemonicPerformanceMonitor.endWrite();
    } else if (order instanceof GAD_DrawingOrder.GLGD_LinearGradient glgd) {
      MnemonicPerformanceMonitor.startWriteWithMnemonic("GLGD");
      writer.writeStartElement("GLGD_LinearGradient");
      writeElement(writer, childLevel, "qualifier", glgd.qualifier);
      writeElement(writer, childLevel, "lengthOfFollowingData", glgd.lengthOfFollowingData);
      writeElement(writer, childLevel, "reserved4_5", glgd.reserved4_5);
      writeElement(writer, childLevel, "patternSet", glgd.patternSet);
      writeElement(writer, childLevel, "patternSymbol", glgd.patternSymbol);
      writeElement(writer, childLevel, "xStart", glgd.xStart);
      writeElement(writer, childLevel, "yStart", glgd.yStart);
      writeElement(writer, childLevel, "xEnd", glgd.xEnd);
      writeElement(writer, childLevel, "yEnd", glgd.yEnd);
      if (glgd.startColorSpec != null) {
        writeColorSpecification(writer, glgd.startColorSpec, childLevel, "startColorSpec");
      }
      if (glgd.endColorValue != null) {
        writeBinaryElement(writer, childLevel, "endColorValue", glgd.endColorValue);
      }
      writeElement(writer, childLevel, "outsideStart", glgd.outsideStart);
      writeElement(writer, childLevel, "outsideEnd", glgd.outsideEnd);
      if (glgd.colorStops != null) {
        writeIndent(writer, childLevel);
        writer.writeStartElement("colorStops");
        for (GAD_DrawingOrder.ColorStop stop : glgd.colorStops) {
          writeColorStop(writer, stop, childLevel + 1);
        }
        writeIndent(writer, childLevel);
        writer.writeEndElement();
      }
      writeIndent(writer, level);
      writer.writeEndElement();
      MnemonicPerformanceMonitor.endWrite();
    } else if (order instanceof GAD_DrawingOrder.GRGD_RadialGradient grgd) {
      MnemonicPerformanceMonitor.startWriteWithMnemonic("GRGD");
      writer.writeStartElement("GRGD_RadialGradient");
      writeElement(writer, childLevel, "qualifier", grgd.qualifier);
      writeElement(writer, childLevel, "lengthOfFollowingData", grgd.lengthOfFollowingData);
      writeElement(writer, childLevel, "reserved4_5", grgd.reserved4_5);
      writeElement(writer, childLevel, "patternSet", grgd.patternSet);
      writeElement(writer, childLevel, "patternSymbol", grgd.patternSymbol);
      writeElement(writer, childLevel, "xStart", grgd.xStart);
      writeElement(writer, childLevel, "yStart", grgd.yStart);
      writeElement(writer, childLevel, "mhStart", grgd.mhStart);
      writeElement(writer, childLevel, "mfrStart", grgd.mfrStart);
      writeElement(writer, childLevel, "xEnd", grgd.xEnd);
      writeElement(writer, childLevel, "yEnd", grgd.yEnd);
      writeElement(writer, childLevel, "mhEnd", grgd.mhEnd);
      writeElement(writer, childLevel, "mfrEnd", grgd.mfrEnd);
      if (grgd.startColorSpec != null) {
        writeColorSpecification(writer, grgd.startColorSpec, childLevel, "startColorSpec");
      }
      if (grgd.endColorValue != null) {
        writeBinaryElement(writer, childLevel, "endColorValue", grgd.endColorValue);
      }
      writeElement(writer, childLevel, "outsideStart", grgd.outsideStart);
      writeElement(writer, childLevel, "outsideEnd", grgd.outsideEnd);
      if (grgd.colorStops != null) {
        writeIndent(writer, childLevel);
        writer.writeStartElement("colorStops");
        for (GAD_DrawingOrder.ColorStop stop : grgd.colorStops) {
          writeColorStop(writer, stop, childLevel + 1);
        }
        writeIndent(writer, childLevel);
        writer.writeEndElement();
      }
      writeIndent(writer, level);
      writer.writeEndElement();
      MnemonicPerformanceMonitor.endWrite();
    } else if (order instanceof GAD_DrawingOrder.DrawingOrder_HasPoints dohp) {
      String rootName = MnemonicPerformanceMonitor.getSimpleName(order.getClass());
      if (MnemonicPerformanceMonitor.isEnabled()) {
        MnemonicPerformanceMonitor.startWriteWithMnemonic(MnemonicPerformanceMonitor.extractMnemonicFromString(rootName));
      }
      writer.writeStartElement(rootName);
      if (dohp.getPoints() != null) {
        writeIndent(writer, childLevel);
        writer.writeStartElement("points");
        for (GAD_DrawingOrder.GOCA_Point p : dohp.getPoints()) {
          writeIndent(writer, childLevel + 1);
          writer.writeStartElement("GOCA_Point");
          writeElement(writer, childLevel + 2, "xCoordinate", p.xCoordinate());
          writeElement(writer, childLevel + 2, "yCoordinate", p.yCoordinate());
          writeIndent(writer, childLevel + 1);
          writer.writeEndElement();
        }
        writeIndent(writer, childLevel);
        writer.writeEndElement();
      }
      writeIndent(writer, level);
      writer.writeEndElement();
      if (MnemonicPerformanceMonitor.isEnabled()) {
        MnemonicPerformanceMonitor.endWrite();
      }
    } else {
      if (MnemonicPerformanceMonitor.isEnabled()) {
        String rootName = MnemonicPerformanceMonitor.getSimpleName(order.getClass());
        String mnemonic = MnemonicPerformanceMonitor.extractMnemonicFromString(rootName);
        MnemonicPerformanceMonitor.startWriteWithMnemonic(mnemonic);
      }
      JacksonXmlMapperProvider.getCachedWriter(order.getClass(), true, indentEnabled).writeValue(baseFragmentGenerator, order);
      if (MnemonicPerformanceMonitor.isEnabled()) {
        MnemonicPerformanceMonitor.endWrite();
      }
    }
  }

  private void writeIpdSegmentDirectly(XMLStreamWriter2 writer, IPD_Segment segment, int level) throws Exception {
    int childLevel = level + 1;
    if (segment instanceof IPD_Segment.ImageData id) {
      writer.writeStartElement("ImageData");
      if (id.getImageData() != null) {
        writeBinaryElement(writer, childLevel, "imageData", id.getImageData());
      }
      writeIndent(writer, level);
      writer.writeEndElement();
    } else if (segment instanceof IPD_Segment.BandImageData bid) {
      writer.writeStartElement("BandImageData");
      writeElement(writer, childLevel, "bandNumber", bid.getBandNumber());
      if (bid.getBandData() != null) {
        writeBinaryElement(writer, childLevel, "bandData", bid.getBandData());
      }
      writeIndent(writer, level);
      writer.writeEndElement();
    } else if (segment instanceof IPD_Segment.BeginImageContent bic) {
      writer.writeStartElement("BeginImageContent");
      writeElement(writer, childLevel, "objectType", bic.getObjectType());
      writeIndent(writer, level);
      writer.writeEndElement();
    } else if (segment instanceof IPD_Segment.EndImageContent) {
      writer.writeEmptyElement("EndImageContent");
    } else if (segment instanceof IPD_Segment.ImageSize is) {
      writer.writeStartElement("ImageSize");
      if (is.getUnitBase() != null) {
        writeElement(writer, childLevel, "unitBase", is.getUnitBase().name());
      }
      writeElement(writer, childLevel, "xUnitsPerUnitBase", is.getxUnitsPerUnitBase());
      writeElement(writer, childLevel, "yUnitsPerUnitBase", is.getyUnitsPerUnitBase());
      writeElement(writer, childLevel, "xImageSize", is.getxImageSize());
      writeElement(writer, childLevel, "yImageSize", is.getyImageSize());
      writeIndent(writer, level);
      writer.writeEndElement();
    } else if (segment instanceof IPD_Segment.ImageEncoding ie) {
      writer.writeStartElement("ImageEncoding");
      if (ie.getCompressionAlgorithm() != null) {
        writeElement(writer, childLevel, "compressionAlgorithm", ie.getCompressionAlgorithm().name());
      }
      if (ie.getRecordingAlgorithm() != null) {
        writeElement(writer, childLevel, "recordingAlgorithm", ie.getRecordingAlgorithm().name());
      }
      if (ie.getBitOrder() != null) {
        writeElement(writer, childLevel, "bitOrder", ie.getBitOrder().name());
      }
      writeIndent(writer, level);
      writer.writeEndElement();
    } else {
      JacksonXmlMapperProvider.getCachedWriter(segment.getClass(), true, indentEnabled).writeValue(baseFragmentGenerator, segment);
    }
  }

  private void writeIpdDirectly(IPD_ImagePictureData ipd, int level) throws Exception {
    MnemonicPerformanceMonitor.startWriteWithMnemonic("IPD");
    baseXsw.writeStartElement("IPD_ImagePictureData");
    int childLevel = level + 1;
    List<IPD_Segment> segments = ipd.getListOfSegments();
    if (segments != null) {
      for (IPD_Segment segment : segments) {
        writeIpdSegmentDirectly(baseXsw, segment, childLevel);
      }
    }
    writeIndent(baseXsw, level);
    baseXsw.writeEndElement();
    MnemonicPerformanceMonitor.endWrite();
  }

  private void writeObdDirectly(OBD_ObjectAreaDescriptor obd, int level) throws Exception {
    MnemonicPerformanceMonitor.startWriteWithMnemonic("OBD");
    baseXsw.writeStartElement("OBD_ObjectAreaDescriptor");
    int childLevel = level + 1;
    if (obd.getTriplets() != null) {
      for (Triplet triplet : obd.getTriplets()) {
        writeTriplet(baseXsw, triplet, childLevel);
      }
    }
    writeIndent(baseXsw, level);
    baseXsw.writeEndElement();
    MnemonicPerformanceMonitor.endWrite();
  }

  private void writeObpDirectly(OBP_ObjectAreaPosition obp, int level) throws Exception {
    MnemonicPerformanceMonitor.startWriteWithMnemonic("OBP");
    baseXsw.writeStartElement("OBP_ObjectAreaPosition");
    int childLevel = level + 1;
    writeElement(baseXsw, childLevel, "objectAreaPositionID", obp.getObjectAreaPositionID());
    if (obp.getRepeatingGroup() != null) {
      writeIndent(baseXsw, childLevel);
      baseXsw.writeStartElement("repeatingGroup");
      int rgLevel = childLevel + 1;
      OBP_ObjectAreaPosition.OBP_RepeatingGroup rg = obp.getRepeatingGroup();
      writeElement(baseXsw, rgLevel, "repeatingGroupLength", rg.getRepeatingGroupLength());
      writeElement(baseXsw, rgLevel, "xOrigin", rg.getxOrigin());
      writeElement(baseXsw, rgLevel, "yOrigin", rg.getyOrigin());
      if (rg.getxRotation() != null) {
        writeElement(baseXsw, rgLevel, "xRotation", rg.getxRotation().name());
      }
      if (rg.getyRotation() != null) {
        writeElement(baseXsw, rgLevel, "yRotation", rg.getyRotation().name());
      }
      writeElement(baseXsw, rgLevel, "reserved11", rg.getReserved11());
      writeElement(baseXsw, rgLevel, "xOriginOfContent", rg.getxOriginOfContent());
      writeElement(baseXsw, rgLevel, "yOriginOfContent", rg.getyOriginOfContent());
      if (rg.getxRotationOfContent() != null) {
        writeElement(baseXsw, rgLevel, "xRotationOfContent", rg.getxRotationOfContent().name());
      }
      if (rg.getyRotationOfContent() != null) {
        writeElement(baseXsw, rgLevel, "yRotationOfContent", rg.getyRotationOfContent().name());
      }
      if (rg.getReferenceCoordinateSystem() != null) {
        writeElement(baseXsw, rgLevel, "referenceCoordinateSystem", rg.getReferenceCoordinateSystem().name());
      }
      writeIndent(baseXsw, childLevel);
      baseXsw.writeEndElement();
    }
    writeIndent(baseXsw, level);
    baseXsw.writeEndElement();
    MnemonicPerformanceMonitor.endWrite();
  }

  private void writeIddSelfDefiningFieldDirectly(XMLStreamWriter2 writer, IDD_SelfDefiningField sdf, int level) throws Exception {
    int childLevel = level + 1;
    writeIndent(writer, level);
    if (sdf instanceof IDD_SelfDefiningField.SetBilevelImageColor sbic) {
      writer.writeStartElement("SetBilevelImageColor");
      writeElement(writer, childLevel, "applicabilityArea", sbic.getApplicabilityArea());
      if (sbic.getColor() != null) {
        writeElement(writer, childLevel, "color", sbic.getColor().name());
      }
      writeIndent(writer, level);
      writer.writeEndElement();
    } else if (sdf instanceof IDD_SelfDefiningField.SetExtendedBilevelImageColor sebic) {
      writer.writeStartElement("SetExtendedBilevelImageColor");
      if (sebic.getColorSpace() != null) {
        writeElement(writer, childLevel, "colorSpace", sebic.getColorSpace().name());
      }
      writeElement(writer, childLevel, "nrOfBitsComponent1", sebic.getNrOfBitsComponent1());
      writeElement(writer, childLevel, "nrOfBitsComponent2", sebic.getNrOfBitsComponent2());
      writeElement(writer, childLevel, "nrOfBitsComponent3", sebic.getNrOfBitsComponent3());
      writeElement(writer, childLevel, "nrOfBitsComponent4", sebic.getNrOfBitsComponent4());
      if (sebic.getColorValue() != null) {
        writeBinaryElement(writer, childLevel, "colorValue", sebic.getColorValue());
      }
      writeIndent(writer, level);
      writer.writeEndElement();
    } else if (sdf instanceof IDD_SelfDefiningField.IOCAFunctionSetIdentification fsi) {
      writer.writeStartElement("IOCAFunctionSetIdentification");
      writeElement(writer, childLevel, "functionSetCategory", fsi.getFunctionSetCategory());
      if (fsi.getFunctionSetIdentifier() != null) {
        writeElement(writer, childLevel, "functionSetIdentifier", fsi.getFunctionSetIdentifier().name());
      }
      writeIndent(writer, level);
      writer.writeEndElement();
    } else {
      JacksonXmlMapperProvider.getCachedWriter(sdf.getClass(), true, indentEnabled).writeValue(baseFragmentGenerator, sdf);
    }
  }

  private void writeIddDirectly(IDD_ImageDataDescriptor idd, int level) throws Exception {
    MnemonicPerformanceMonitor.startWriteWithMnemonic("IDD");
    baseXsw.writeStartElement("IDD_ImageDataDescriptor");
    int childLevel = level + 1;
    if (idd.getUnitBase() != null) {
      writeElement(baseXsw, childLevel, "unitBase", idd.getUnitBase().name());
    }
    writeElement(baseXsw, childLevel, "xImagePointsPerUnitBase", idd.getxImagePointsPerUnitBase());
    writeElement(baseXsw, childLevel, "yImagePointsPerUnitBase", idd.getyImagePointsPerUnitBase());
    writeElement(baseXsw, childLevel, "widthOfImageInImagePoints", idd.getWidthOfImageInImagePoints());
    writeElement(baseXsw, childLevel, "heightOfImageInImagePoints", idd.getHeightOfImageInImagePoints());

    if (idd.getSelfDefiningFields() != null) {
      for (IDD_SelfDefiningField sdf : idd.getSelfDefiningFields()) {
        writeIddSelfDefiningFieldDirectly(baseXsw, sdf, childLevel);
      }
    }
    writeIndent(baseXsw, level);
    baseXsw.writeEndElement();
    MnemonicPerformanceMonitor.endWrite();
  }

  private void writeMioDirectly(MIO_MapImageObject mio, int level) throws Exception {
    MnemonicPerformanceMonitor.startWriteWithMnemonic("MIO");
    baseXsw.writeStartElement("MIO_MapImageObject");
    int childLevel = level + 1;
    if (mio.getRepeatingGroups() != null) {
      for (IRepeatingGroup rg : mio.getRepeatingGroups()) {
        writeIndent(baseXsw, childLevel);
        baseXsw.writeStartElement("mioRepeatingGroup");
        if (rg instanceof RepeatingGroupWithTriplets rgt) {
          if (rgt.getTriplets() != null) {
            for (Triplet t : rgt.getTriplets()) {
              writeTriplet(baseXsw, t, childLevel + 1);
            }
          }
        }
        writeIndent(baseXsw, childLevel);
        baseXsw.writeEndElement();
      }
    }
    writeIndent(baseXsw, level);
    baseXsw.writeEndElement();
    MnemonicPerformanceMonitor.endWrite();
  }

  private void writeMdrDirectly(MDR_MapDataResource mdr, int level) throws Exception {
    MnemonicPerformanceMonitor.startWriteWithMnemonic("MDR");
    baseXsw.writeStartElement("MDR_MapDataResource");
    int childLevel = level + 1;
    if (mdr.getRepeatingGroups() != null) {
      for (IRepeatingGroup rg : mdr.getRepeatingGroups()) {
        writeIndent(baseXsw, childLevel);
        baseXsw.writeStartElement("mdrRepeatingGroup");
        if (rg instanceof RepeatingGroupWithTriplets rgt) {
          if (rgt.getTriplets() != null) {
            for (Triplet t : rgt.getTriplets()) {
              writeTriplet(baseXsw, t, childLevel + 1);
            }
          }
        }
        writeIndent(baseXsw, childLevel);
        baseXsw.writeEndElement();
      }
    }
    writeIndent(baseXsw, level);
    baseXsw.writeEndElement();
    MnemonicPerformanceMonitor.endWrite();
  }

  private void writeMgoDirectly(MGO_MapGraphicsObject mgo, int level) throws Exception {
    MnemonicPerformanceMonitor.startWriteWithMnemonic("MGO");
    baseXsw.writeStartElement("MGO_MapGraphicsObject");
    int childLevel = level + 1;
    if (mgo.getRepeatingGroups() != null) {
      for (IRepeatingGroup rg : mgo.getRepeatingGroups()) {
        writeIndent(baseXsw, childLevel);
        baseXsw.writeStartElement("mgoRepeatingGroup");
        if (rg instanceof RepeatingGroupWithTriplets rgt) {
          if (rgt.getTriplets() != null) {
            for (Triplet t : rgt.getTriplets()) {
              writeTriplet(baseXsw, t, childLevel + 1);
            }
          }
        }
        writeIndent(baseXsw, childLevel);
        baseXsw.writeEndElement();
      }
    }
    writeIndent(baseXsw, level);
    baseXsw.writeEndElement();
    MnemonicPerformanceMonitor.endWrite();
  }

  private void writeMpoDirectly(MPO_MapPageOverlay mpo, int level) throws Exception {
    MnemonicPerformanceMonitor.startWriteWithMnemonic("MPO");
    baseXsw.writeStartElement("MPO_MapPageOverlay");
    int childLevel = level + 1;
    if (mpo.getRepeatingGroups() != null) {
      for (IRepeatingGroup rg : mpo.getRepeatingGroups()) {
        writeIndent(baseXsw, childLevel);
        baseXsw.writeStartElement("mpoRepeatingGroup");
        if (rg instanceof RepeatingGroupWithTriplets rgt) {
          if (rgt.getTriplets() != null) {
            for (Triplet t : rgt.getTriplets()) {
              writeTriplet(baseXsw, t, childLevel + 1);
            }
          }
        }
        writeIndent(baseXsw, childLevel);
        baseXsw.writeEndElement();
      }
    }
    writeIndent(baseXsw, level);
    baseXsw.writeEndElement();
    MnemonicPerformanceMonitor.endWrite();
  }

  private void writeBdtDirectly(com.mgz.afp.modca.BDT_BeginDocument bdt, int level) throws Exception {
    MnemonicPerformanceMonitor.startWriteWithMnemonic("BDT");
    baseXsw.writeStartElement("BDT_BeginDocument");
    int childLevel = level + 1;
    writeElement(baseXsw, childLevel, "name", bdt.getName());
    if (bdt.getTriplets() != null && !bdt.getTriplets().isEmpty()) {
      writeIndent(baseXsw, childLevel);
      baseXsw.writeStartElement("triplets");
      for (Triplet triplet : bdt.getTriplets()) {
        writeTriplet(baseXsw, triplet, childLevel + 1);
      }
      writeIndent(baseXsw, childLevel);
      baseXsw.writeEndElement();
    }
    if (bdt.reserved8_9 != null) {
      writeElement(baseXsw, childLevel, "reserved8_9", com.mgz.util.UtilCharacterEncoding.bytesToHexString(bdt.reserved8_9));
    }
    if (bdt.getText() != null) {
      writeElement(baseXsw, childLevel, "text", bdt.getText());
    }
    writeIndent(baseXsw, level);
    baseXsw.writeEndElement();
    MnemonicPerformanceMonitor.endWrite();
  }

  private void writeBmoDirectly(BMO_BeginOverlay bmo, int level) throws Exception {
    MnemonicPerformanceMonitor.startWriteWithMnemonic("BMO");
    baseXsw.writeStartElement("BMO_BeginOverlay");
    int childLevel = level + 1;
    writeElement(baseXsw, childLevel, "name", bmo.getName());
    if (bmo.getTriplets() != null && !bmo.getTriplets().isEmpty()) {
      for (Triplet triplet : bmo.getTriplets()) {
        writeTriplet(baseXsw, triplet, childLevel);
      }
    }
    if (bmo.getText() != null) {
      writeElement(baseXsw, childLevel, "text", bmo.getText());
    }
    writeIndent(baseXsw, level);
    baseXsw.writeEndElement();
    MnemonicPerformanceMonitor.endWrite();
  }

  private void writeBpsDirectly(BPS_BeginPageSegment bps, int level) throws Exception {
    MnemonicPerformanceMonitor.startWriteWithMnemonic("BPS");
    baseXsw.writeStartElement("BPS_BeginPageSegment");
    int childLevel = level + 1;
    writeElement(baseXsw, childLevel, "name", bps.getName());
    if (bps.getTriplets() != null && !bps.getTriplets().isEmpty()) {
      for (Triplet triplet : bps.getTriplets()) {
        writeTriplet(baseXsw, triplet, childLevel);
      }
    }
    if (bps.getText() != null) {
      writeElement(baseXsw, childLevel, "text", bps.getText());
    }
    writeIndent(baseXsw, level);
    baseXsw.writeEndElement();
    MnemonicPerformanceMonitor.endWrite();
  }

  private void writeNameAndTripletsDirectly(com.mgz.afp.base.StructuredFieldBaseNameAndTriplets sf, String rootName, int level) throws Exception {
    if (MnemonicPerformanceMonitor.isEnabled()) {
      String mnemonic = MnemonicPerformanceMonitor.extractMnemonicFromString(rootName);
      MnemonicPerformanceMonitor.startWriteWithMnemonic(mnemonic);
    }
    baseXsw.writeStartElement(rootName);
    int childLevel = level + 1;
    writeElement(baseXsw, childLevel, "name", sf.getName());
    if (sf.getTriplets() != null && !sf.getTriplets().isEmpty()) {
      for (Triplet triplet : sf.getTriplets()) {
        writeTriplet(baseXsw, triplet, childLevel);
      }
    }
    if (sf.getText() != null) {
      writeElement(baseXsw, childLevel, "text", sf.getText());
    }
    writeIndent(baseXsw, level);
    baseXsw.writeEndElement();
    if (MnemonicPerformanceMonitor.isEnabled()) {
      MnemonicPerformanceMonitor.endWrite();
    }
  }

  private void writeTripletsAndTextDirectly(com.mgz.afp.base.StructuredFieldBaseTriplets sf, String rootName, int level) throws Exception {
    if (MnemonicPerformanceMonitor.isEnabled()) {
      String mnemonic = MnemonicPerformanceMonitor.extractMnemonicFromString(rootName);
      MnemonicPerformanceMonitor.startWriteWithMnemonic(mnemonic);
    }
    baseXsw.writeStartElement(rootName);
    int childLevel = level + 1;
    if (sf.getTriplets() != null && !sf.getTriplets().isEmpty()) {
      for (Triplet triplet : sf.getTriplets()) {
        writeTriplet(baseXsw, triplet, childLevel);
      }
    }
    if (sf.getText() != null) {
      writeElement(baseXsw, childLevel, "text", sf.getText());
    }
    writeIndent(baseXsw, level);
    baseXsw.writeEndElement();
    if (MnemonicPerformanceMonitor.isEnabled()) {
      MnemonicPerformanceMonitor.endWrite();
    }
  }

  private void writeNameDirectly(com.mgz.afp.base.StructuredFieldBaseName sf, String rootName, int level) throws Exception {
    if (MnemonicPerformanceMonitor.isEnabled()) {
      String mnemonic = MnemonicPerformanceMonitor.extractMnemonicFromString(rootName);
      MnemonicPerformanceMonitor.startWriteWithMnemonic(mnemonic);
    }
    baseXsw.writeStartElement(rootName);
    int childLevel = level + 1;
    writeElement(baseXsw, childLevel, "name", sf.getName());
    if (sf.getText() != null) {
      writeElement(baseXsw, childLevel, "text", sf.getText());
    }
    writeIndent(baseXsw, level);
    baseXsw.writeEndElement();
    if (MnemonicPerformanceMonitor.isEnabled()) {
      MnemonicPerformanceMonitor.endWrite();
    }
  }

  private void writePgdDirectly(com.mgz.afp.modca.PGD_PageDescriptor pgd, int level) throws Exception {
    MnemonicPerformanceMonitor.startWriteWithMnemonic("PGD");
    baseXsw.writeStartElement("PGD_PageDescriptor");
    int childLevel = level + 1;
    if (pgd.getxUnitBase() != null) {
      writeElement(baseXsw, childLevel, "xUnitBase", pgd.getxUnitBase().name());
    }
    if (pgd.getyUnitBase() != null) {
      writeElement(baseXsw, childLevel, "yUnitBase", pgd.getyUnitBase().name());
    }
    writeElement(baseXsw, childLevel, "xUnitsPerUnitBase", pgd.getxUnitsPerUnitBase());
    writeElement(baseXsw, childLevel, "yUnitsPerUnitBase", pgd.getyUnitsPerUnitBase());
    writeElement(baseXsw, childLevel, "xSize", pgd.getxSize());
    writeElement(baseXsw, childLevel, "ySize", pgd.getySize());
    if (pgd.getReserved12_14() != null) {
      writeElement(baseXsw, childLevel, "reserved12_14", UtilCharacterEncoding.bytesToHexString(pgd.getReserved12_14()));
    }
    writeTripletsAndText(baseXsw, pgd.getTriplets(), pgd.getText(), childLevel, level);
    baseXsw.writeEndElement();
    MnemonicPerformanceMonitor.endWrite();
  }

  private void writeMddDirectly(com.mgz.afp.modca.MDD_MediumDescriptor mdd, int level) throws Exception {
    MnemonicPerformanceMonitor.startWriteWithMnemonic("MDD");
    baseXsw.writeStartElement("MDD_MediumDescriptor");
    int childLevel = level + 1;
    if (mdd.getxUnitBase() != null) {
      writeElement(baseXsw, childLevel, "xUnitBase", mdd.getxUnitBase().name());
    }
    if (mdd.getyUnitBase() != null) {
      writeElement(baseXsw, childLevel, "yUnitBase", mdd.getyUnitBase().name());
    }
    writeElement(baseXsw, childLevel, "xUnitsPerUnitBase", mdd.getxUnitsPerUnitBase());
    writeElement(baseXsw, childLevel, "yUnitsPerUnitBase", mdd.getyUnitsPerUnitBase());
    writeElement(baseXsw, childLevel, "xMediumExtent", mdd.getxMediumExtent());
    writeElement(baseXsw, childLevel, "yMediumExtent", mdd.getyMediumExtent());
    if (mdd.getFlag() != null) {
      writeElement(baseXsw, childLevel, "flag", mdd.getFlag().name());
    }
    writeTripletsAndText(baseXsw, mdd.getTriplets(), mdd.getText(), childLevel, level);
    baseXsw.writeEndElement();
    MnemonicPerformanceMonitor.endWrite();
  }

  private void writeDrawingOrderWithPoints(XMLStreamWriter2 writer, GAD_DrawingOrder.DrawingOrder_HasPoints order, String rootName, int level) throws Exception {
    writer.writeStartElement(rootName);
    int childLevel = level + 1;
    if (order.getPoints() != null) {
      if (writer instanceof AfpXmlStreamWriter afpXsw) {
        writeIndent(afpXsw, childLevel);
        afpXsw.writeRawBytes(XmlTagTemplates.POINTS_START, 0, XmlTagTemplates.POINTS_START.length);
        for (GAD_DrawingOrder.GOCA_Point p : order.getPoints()) {
          writeIndent(afpXsw, childLevel + 1);
          afpXsw.writeEmptyElement("GOCA_Point");
          afpXsw.writeIntAttribute(null, null, "xCoordinate", p.xCoordinate());
          afpXsw.writeIntAttribute(null, null, "yCoordinate", p.yCoordinate());
        }
        writeIndent(afpXsw, childLevel);
        afpXsw.writeRawBytes(XmlTagTemplates.POINTS_END, 0, XmlTagTemplates.POINTS_END.length);
      } else {
        writeIndent(writer, childLevel);
        writer.writeStartElement("points");
        for (GAD_DrawingOrder.GOCA_Point p : order.getPoints()) {
          writeIndent(writer, childLevel + 1);
          writer.writeEmptyElement("GOCA_Point");
          writer.writeIntAttribute(null, null, "xCoordinate", p.xCoordinate());
          writer.writeIntAttribute(null, null, "yCoordinate", p.yCoordinate());
        }
        writeIndent(writer, childLevel);
        writer.writeEndElement();
      }
    }
    writeIndent(writer, level);
    writer.writeEndElement();
  }


  private void writeElement(XMLStreamWriter2 writer, int level, String name, String value) throws Exception {
    if (value != null) {
      writeIndent(writer, level);
      writer.writeStartElement(name);
      writer.writeCharacters(value);
      writer.writeEndElement();
    }
  }

  private void writeElement(XMLStreamWriter2 writer, int level, String name, int value) throws Exception {
    writeIndent(writer, level);
    writer.writeStartElement(name);
    writer.writeInt(value);
    writer.writeEndElement();
  }

  private void writeElement(XMLStreamWriter2 writer, int level, String name, long value) throws Exception {
    writeIndent(writer, level);
    writer.writeStartElement(name);
    writer.writeLong(value);
    writer.writeEndElement();
  }

  private void writeElement(int level, String name, String value) throws Exception {
    writeElement(xsw, level, name, value);
  }

  private void writeElement(int level, String name, int value) throws Exception {
    writeElement(xsw, level, name, value);
  }

  private void writeElement(int level, String name, long value) throws Exception {
    writeElement(xsw, level, name, value);
  }

  private void writeBinaryElement(XMLStreamWriter2 writer, int level, String name, byte[] data) throws Exception {
    if (data != null) {
      writeIndent(writer, level);
      writer.writeStartElement(name);
      writer.writeBinary(data, 0, data.length);
      writer.writeEndElement();
    }
  }

  private void writeBinaryElement(int level, String name, byte[] data) throws Exception {
    writeBinaryElement(xsw, level, name, data);
  }

  private void writeIndent(XMLStreamWriter2 writer, int level) throws Exception {
    if (indentEnabled) {
      if (writer instanceof AfpXmlStreamWriter afpXsw) {
        XmlIndenter.writeIndent(afpXsw, level);
      } else {
        writer.writeRaw(XmlIndenter.getIndent(level));
      }
    }
  }

  private void writePureIndent(XMLStreamWriter2 writer, int level) throws Exception {
    if (indentEnabled) {
      if (writer instanceof AfpXmlStreamWriter afpXsw) {
        XmlIndenter.writePureIndent(afpXsw, level);
      } else {
        String s = XmlIndenter.getIndent(level);
        if (s.startsWith("\n")) {
          writer.writeRaw(s.substring(1));
        } else {
          writer.writeRaw(s);
        }
      }
    }
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
    if (baseFragmentGenerator != null) {
      baseFragmentGenerator.flush();
    }
    if (xsw != null) {
      if (!fragmentMode) {
        xsw.writeEndElement();
        writeNewline();
        xsw.writeEndDocument();
      } else {
        xsw.writeEndElement();
      }
      xsw.flush();
      // Always close the writer to ensure that internal buffers are recycled by the factory.
      xsw.close();
    }
  }


  private void writeIndent(int level) throws Exception {
    writeIndent(baseXsw, level);
  }

  private void writePureIndent(int level) throws Exception {
    writePureIndent(baseXsw, level);
  }

  private void writeNewline() throws Exception {
    if (indentEnabled) {
      baseXsw.writeRawBytes(XmlIndenter.INDENT_BYTES[0], 0, 1);
    }
  }

  private void writeColorSpecification(XMLStreamWriter2 writer, GAD_DrawingOrder.ColorSpecification cs, int level, String rootName) throws Exception {
    int childLevel = level + 1;
    writer.writeStartElement(rootName);
    writeElement(writer, childLevel, "length", cs.length);
    writeElement(writer, childLevel, "reserved", cs.reserved);
    if (cs.colorSpace != null) {
      writeElement(writer, childLevel, "colorSpace", cs.colorSpace.name());
    }
    writeElement(writer, childLevel, "reserved4_7", cs.reserved4_7);
    writeElement(writer, childLevel, "nrOfBitsComponent1", cs.nrOfBitsComponent1);
    writeElement(writer, childLevel, "nrOfBitsComponent2", cs.nrOfBitsComponent2);
    writeElement(writer, childLevel, "nrOfBitsComponent3", cs.nrOfBitsComponent3);
    writeElement(writer, childLevel, "nrOfBitsComponent4", cs.nrOfBitsComponent4);
    if (cs.colorValue != null) {
      writeBinaryElement(writer, childLevel, "colorValue", cs.colorValue);
    }
    writeIndent(writer, level);
    writer.writeEndElement();
  }

  private void writeColorStop(XMLStreamWriter2 writer, GAD_DrawingOrder.ColorStop stop, int level) throws Exception {
    int childLevel = level + 1;
    writer.writeStartElement("ColorStop");
    writeElement(writer, childLevel, "offset", stop.offset);
    if (stop.colorValue != null) {
      writeBinaryElement(writer, childLevel, "colorValue", stop.colorValue);
    }
    writeIndent(writer, level);
    writer.writeEndElement();
  }

  private void writeBdaDirectly(BDA_BarCodeData bda, int level) throws Exception {
    MnemonicPerformanceMonitor.startWriteWithMnemonic("BDA");
    baseXsw.writeStartElement("BDA_BarCodeData");
    int childLevel = level + 1;

    if (bda.getBarCodeFlags() != null && !bda.getBarCodeFlags().isEmpty()) {
      writeIndent(baseXsw, childLevel);
      baseXsw.writeStartElement("barCodeFlags");
      for (BDA_BarCodeData.BarCodeFlag flag : bda.getBarCodeFlags()) {
        writeElement(baseXsw, childLevel + 1, "barCodeFlag", flag.name());
      }
      writeIndent(baseXsw, childLevel);
      baseXsw.writeEndElement();
    }
    writeElement(baseXsw, childLevel, "xOffset", bda.getxOffset());
    writeElement(baseXsw, childLevel, "yOffset", bda.getyOffset());

    if (bda.parametersData != null) {
      writeBdaParametersDataDirectly(baseXsw, bda.parametersData, childLevel);
    }

    if (bda.barCodeData != null && bda.barCodeData.length > 0) {
      writeBinaryElement(baseXsw, childLevel, "barCodeData", bda.barCodeData);
    }
    if (bda.getText() != null) {
      writeElement(baseXsw, childLevel, "text", bda.getText());
    }

    writeIndent(baseXsw, level);
    baseXsw.writeEndElement();
    MnemonicPerformanceMonitor.endWrite();
  }

  private void writeBdaParametersDataDirectly(XMLStreamWriter2 writer, BDA_BarCodeData.ParametersData pd, int level) throws Exception {
    int childLevel = level + 1;
    writeIndent(writer, level);
    writer.writeStartElement(MnemonicPerformanceMonitor.getSimpleName(pd.getClass()));

    if (pd.controlFlags != null && !pd.controlFlags.isEmpty()) {
      writeIndent(writer, childLevel);
      writer.writeStartElement("controlFlags");
      for (BDA_BarCodeData.ParametersData.ControlFlag flag : pd.controlFlags) {
        writeElement(writer, childLevel + 1, "controlFlag", flag.name());
      }
      writeIndent(writer, childLevel);
      writer.writeEndElement();
    }
    writeElement(writer, childLevel, "sequenceIndicator", pd.sequenceIndicator);
    writeElement(writer, childLevel, "totalNumberOfSymbols", pd.totalNumberOfSymbols);

    if (pd instanceof BDA_BarCodeData.ParametersDataMatrixBarcode m) {
      writeElement(writer, childLevel, "desiredRowSize", m.desiredRowSize);
      writeElement(writer, childLevel, "desiredNumberOfRows", m.desiredNumberOfRows);
      writeElement(writer, childLevel, "fileIDFirstByte", m.getFileIDFirstByte());
      writeElement(writer, childLevel, "fileIDSecondByte", m.getFileIDSecondByte());
      if (m.specialFunctionFlags != null && !m.specialFunctionFlags.isEmpty()) {
        writeIndent(writer, childLevel);
        writer.writeStartElement("specialFunctionFlags");
        for (BDA_BarCodeData.ParametersDataMatrixBarcode.SpecialFunctionFlag flag : m.specialFunctionFlags) {
          writeElement(writer, childLevel + 1, "specialFunctionFlag", flag.name());
        }
        writeIndent(writer, childLevel);
        writer.writeEndElement();
      }
    } else if (pd instanceof BDA_BarCodeData.ParametersDataMaxiCode_2D max) {
      writeElement(writer, childLevel, "symbolMode", max.symbolMode.name());
      writeElement(writer, childLevel, "specialFunctionFlag", max.specialFunctionFlag.name());
    } else if (pd instanceof BDA_BarCodeData.ParametersDataPDF417_2D pdf) {
      writeElement(writer, childLevel, "numberOfDataSymbolCharactersPerRow", pdf.numberOfDataSymbolCharactersPerRow);
      writeElement(writer, childLevel, "desiredNumberOfRows", pdf.desiredNumberOfRows);
      writeElement(writer, childLevel, "securityLevel", pdf.securityLevel);
      writeElement(writer, childLevel, "lengthOfMacroPDF417ControlBlock", pdf.lengthOfMacroPDF417ControlBlock);
      if (pdf.macroPDF417ControlBlock != null && pdf.macroPDF417ControlBlock.length > 0) {
        writeBinaryElement(writer, childLevel, "macroPDF417ControlBlock", pdf.macroPDF417ControlBlock);
      }
    } else if (pd instanceof BDA_BarCodeData.ParametersDataQRCode_2D qr) {
      writeElement(writer, childLevel, "conversion", qr.conversion.name());
      writeElement(writer, childLevel, "versionOfSymbol", qr.versionOfSymbol);
      writeElement(writer, childLevel, "errorCorrectionLevel", qr.errorCorrectionLevel.name());
      writeElement(writer, childLevel, "parityData", qr.parityData);
      writeElement(writer, childLevel, "qrCodeSpecialFunctionFlags", qr.qrCodeSpecialFunctionFlags.toString());
      writeElement(writer, childLevel, "applicationIndicator", qr.applicationIndicator);
      if (pd instanceof BDA_BarCodeData.ParametersDataQRCodeWithImage qri) {
        writeElement(writer, childLevel, "qrCodeWithImageFlags", qri.qrCodeWithImageFlags.toString());
        writeElement(writer, childLevel, "repeatingGroupsLength", qri.repeatingGroupsLength);
        for (BDA_BarCodeData.ParametersDataQRCodeWithImage.ImageInformationBlock block : qri.imageInformationBlocks) {
          writeIndent(writer, childLevel);
          writer.writeStartElement("imageInformationBlock");
          int blockLevel = childLevel + 1;
          writeElement(writer, blockLevel, "length", block.length);
          writeElement(writer, blockLevel, "imageLocalId", block.imageLocalId);
          writeElement(writer, blockLevel, "offsetUnitBase", block.offsetUnitBase);
          writeElement(writer, blockLevel, "offsetUpub", block.offsetUpub);
          writeElement(writer, blockLevel, "xOffset", block.xOffset);
          writeElement(writer, blockLevel, "yOffset", block.yOffset);
          writeElement(writer, blockLevel, "orientation", block.orientation);
          writeElement(writer, blockLevel, "coordinateSystem", block.coordinateSystem);
          writeElement(writer, blockLevel, "extentUnitBase", block.extentUnitBase);
          writeElement(writer, blockLevel, "extentUpub", block.extentUpub);
          writeElement(writer, blockLevel, "xExtent", block.xExtent);
          writeElement(writer, blockLevel, "yExtent", block.yExtent);
          writeElement(writer, blockLevel, "mappingOption", block.mappingOption);
          if (block.additionalData != null && block.additionalData.length > 0) {
            writeBinaryElement(writer, blockLevel, "additionalData", block.additionalData);
          }
          writeIndent(writer, childLevel);
          writer.writeEndElement();
        }
      }
    } else if (pd instanceof BDA_BarCodeData.ParametersDataAztecCode aztec) {
      writeElement(writer, childLevel, "desiredNumberOfLayers", aztec.desiredNumberOfLayers);
      writeElement(writer, childLevel, "levelOfErrorCorrection", aztec.levelOfErrorCorrection);
      writeElement(writer, childLevel, "aztecSpecialFunctionFlags", aztec.aztecSpecialFunctionFlags.toString());
      writeElement(writer, childLevel, "applicationIndicator", aztec.applicationIndicator);
      writeElement(writer, childLevel, "sequenceIndicator", aztec.getSequenceIndicator());
      writeElement(writer, childLevel, "totalNumberOfSymbols", aztec.getTotalNumberOfSymbols());
      writeElement(writer, childLevel, "structuredAppendIdLength", aztec.structuredAppendIdLength);
      if (aztec.structuredAppendId != null && aztec.structuredAppendId.length > 0) {
        writeBinaryElement(writer, childLevel, "structuredAppendId", aztec.structuredAppendId);
      }
      writeElement(writer, childLevel, "additionalParametersLength", aztec.additionalParametersLength);
      if (aztec.additionalParameters != null && aztec.additionalParameters.length > 0) {
        writeBinaryElement(writer, childLevel, "additionalParameters", aztec.additionalParameters);
      }
    } else if (pd instanceof BDA_BarCodeData.ParametersDataHanXinCode hanxin) {
      writeElement(writer, childLevel, "version", hanxin.version);
      writeElement(writer, childLevel, "errorCorrectionLevel", hanxin.errorCorrectionLevel);
      writeElement(writer, childLevel, "hanXinSpecialFunctionFlags", hanxin.hanXinSpecialFunctionFlags.toString());
      writeElement(writer, childLevel, "applicationIndicator", hanxin.applicationIndicator);
      writeElement(writer, childLevel, "additionalParametersLength", hanxin.additionalParametersLength);
      if (hanxin.additionalParameters != null && hanxin.additionalParameters.length > 0) {
        writeBinaryElement(writer, childLevel, "additionalParameters", hanxin.additionalParameters);
      }
    } else if (pd instanceof BDA_BarCodeData.ParametersDataIntelligentMailPackageBarcode imp) {
      writeElement(writer, childLevel, "intelligentMailPackageBarcodeFlags", imp.intelligentMailPackageBarcodeFlags.toString());
      writeElement(writer, childLevel, "bannerLength", imp.bannerLength);
      if (imp.bannerString != null && imp.bannerString.length > 0) {
        writeBinaryElement(writer, childLevel, "bannerString", imp.bannerString);
      }
    }

    writeIndent(writer, level);
    writer.writeEndElement();
  }

  private void writeBbcDirectly(BBC_BeginBarCodeObject bbc, int level) throws Exception {
    MnemonicPerformanceMonitor.startWriteWithMnemonic("BBC");
    baseXsw.writeStartElement("BBC_BeginBarCodeObject");
    int childLevel = level + 1;
    if (bbc.getName() != null) {
      baseXsw.writeAttribute("name", bbc.getName());
    }
    if (bbc.getTriplets() != null && !bbc.getTriplets().isEmpty()) {
      for (Triplet triplet : bbc.getTriplets()) {
        writeTriplet(baseXsw, triplet, childLevel);
      }
    }
    if (bbc.getText() != null) {
      writeElement(baseXsw, childLevel, "text", bbc.getText());
    }
    writeIndent(baseXsw, level);
    baseXsw.writeEndElement();
    MnemonicPerformanceMonitor.endWrite();
  }

  private void writeEbcDirectly(EBC_EndBarCodeObject ebc, int level) throws Exception {
    MnemonicPerformanceMonitor.startWriteWithMnemonic("EBC");
    baseXsw.writeStartElement("EBC_EndBarCodeObject");
    int childLevel = level + 1;
    if (ebc.getName() != null) {
      baseXsw.writeAttribute("name", ebc.getName());
    }
    if (ebc.getTriplets() != null && !ebc.getTriplets().isEmpty()) {
      for (Triplet triplet : ebc.getTriplets()) {
        writeTriplet(baseXsw, triplet, childLevel);
      }
    }
    if (ebc.getText() != null) {
      writeElement(baseXsw, childLevel, "text", ebc.getText());
    }
    writeIndent(baseXsw, level);
    baseXsw.writeEndElement();
    MnemonicPerformanceMonitor.endWrite();
  }

  private void writeBddDirectly(BDD_BarCodeDataDescriptor bdd, int level) throws Exception {
    MnemonicPerformanceMonitor.startWriteWithMnemonic("BDD");
    baseXsw.writeStartElement("BDD_BarCodeDataDescriptor");
    int childLevel = level + 1;

    writeElement(baseXsw, childLevel, "unitBase", bdd.getUnitBase().name());
    writeElement(baseXsw, childLevel, "unitsPerUnitBaseX", bdd.getUnitsPerUnitBaseX());
    writeElement(baseXsw, childLevel, "unitsPerUnitBaseY", bdd.getUnitsPerUnitBaseY());
    writeElement(baseXsw, childLevel, "presentationSpaceWidth", bdd.getPresentationSpaceWidth());
    writeElement(baseXsw, childLevel, "presentationSpaceLength", bdd.getPresentationSpaceLength());
    writeElement(baseXsw, childLevel, "desiredSymbolWidth", bdd.getDesiredSymbolWidth());
    writeElement(baseXsw, childLevel, "barcodeType", bdd.getBarcodeType().name());
    writeElement(baseXsw, childLevel, "barcodeModifier", bdd.getBarcodeModifier());
    writeElement(baseXsw, childLevel, "fontLocalIDForHRI", bdd.getFontLocalIDForHRI());
    writeElement(baseXsw, childLevel, "color", bdd.getColor());
    writeElement(baseXsw, childLevel, "moduleWidthInMils", bdd.getModuleWidthInMils());
    writeElement(baseXsw, childLevel, "elementHeight", bdd.getElementHeight());
    writeElement(baseXsw, childLevel, "heightMultiplier", bdd.getHeightMultiplier());
    writeElement(baseXsw, childLevel, "wideToNarrowRatio", bdd.getWideToNarrowRatio());

    writeIndent(baseXsw, level);
    baseXsw.writeEndElement();
    MnemonicPerformanceMonitor.endWrite();
  }

  private void writeOcdDirectly(OCD_ObjectContainerData ocd, int level) throws Exception {
    MnemonicPerformanceMonitor.startWriteWithMnemonic("OCD");
    baseXsw.writeStartElement("OCD_ObjectContainerData");
    int childLevel = level + 1;

    if (ocd.getMetadataObject() != null) {
      writeMetadataObjectDirectly(ocd.getMetadataObject(), childLevel);
    }
    if (ocd.getData() != null && ocd.getMetadataObject() == null) {
      writeBinaryElement(baseXsw, childLevel, "data", ocd.getData());
    }
    if (ocd.getText() != null) {
      writeElement(baseXsw, childLevel, "text", ocd.getText());
    }

    writeIndent(baseXsw, level);
    baseXsw.writeEndElement();
    MnemonicPerformanceMonitor.endWrite();
  }

  private void writeMetadataObjectDirectly(MetadataObject mo, int level) throws Exception {
    writeIndent(baseXsw, level);
    baseXsw.writeStartElement("MetadataObject");
    int childLevel = level + 1;

    writeElement(baseXsw, childLevel, "MOLength", mo.getMoLength());
    writeElement(baseXsw, childLevel, "HeaderLength", mo.getHeaderLength());
    writeElement(baseXsw, childLevel, "MOType", mo.getMoType());
    writeElement(baseXsw, childLevel, "MOFormat", mo.getMoFormat());
    writeElement(baseXsw, childLevel, "MOCompression", mo.getMoCompression());
    writeElement(baseXsw, childLevel, "MONameLength", mo.getMoNameLength());
    if (mo.getMoName() != null) {
      writeElement(baseXsw, childLevel, "MOName", mo.getMoName());
    }
    if (mo.getMoData() != null) {
      writeBinaryElement(baseXsw, childLevel, "MOData", mo.getMoData());
    }
    if (mo.getMoDataText() != null) {
      writeElement(baseXsw, childLevel, "MODataText", mo.getMoDataText());
    }

    writeIndent(baseXsw, level);
    baseXsw.writeEndElement();
  }
}
