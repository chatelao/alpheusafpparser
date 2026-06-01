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
  private final XMLStreamWriter2 baseXsw;
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

  private final boolean useWoodstox;

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
   *
   * @param os the output stream to write to
   * @param xpathExpression the XPath expression to filter fields
   * @param fragmentMode if true, skip XML declaration and root element
   * @param useWoodstox if true, use Woodstox instead of Aalto
   * @throws Exception if initialization fails
   */
  public AfpJacksonXmlWriter(OutputStream os, String xpathExpression, boolean fragmentMode, boolean useWoodstox)
      throws Exception {
    this.os = os;
    this.useWoodstox = useWoodstox;
    this.cos = new com.mgz.util.CountingOutputStream(new NonClosingOutputStream(os));
    this.xpathExpression = (xpathExpression == null || xpathExpression.isBlank()) ? null : xpathExpression;
    this.fragmentMode = fragmentMode;
    this.mapper = JacksonXmlMapperProvider.getMapper(useWoodstox);
    // Fragment mapper to avoid repeated XML declarations
    this.fragmentMapper = JacksonXmlMapperProvider.getFragmentMapper(useWoodstox);

    if (this.xpathExpression == null) {
      XMLOutputFactory xof = JacksonXmlMapperProvider.getOutputFactory(useWoodstox);
      XMLStreamWriter2 rawXsw = (XMLStreamWriter2) xof.createXMLStreamWriter(cos, "UTF-8");
      this.baseXsw = new SanitizingXMLStreamWriter(rawXsw);
      this.xsw = MnemonicPerformanceMonitor.isEnabled() ? new MnemonicXMLStreamWriter(this.baseXsw) : this.baseXsw;
      if (!fragmentMode) {
        this.xsw.writeStartDocument("UTF-8", "1.0");
        this.baseXsw.writeCharacters("\n");
        this.xsw.setPrefix("xsi", "http://www.w3.org/2001/XMLSchema-instance");
        this.xsw.writeStartElement("AFPDocument");
        this.xsw.writeNamespace("xsi", "http://www.w3.org/2001/XMLSchema-instance");
        this.baseXsw.writeCharacters("\n");
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
    if (!fragmentMode) {
      baseXsw.writeCharacters(XmlIndenter.LEVEL_1_PURE);
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
    } else if (sf instanceof MCD_MapContainerData mcd) {
      writeMcdDirectly(mcd);
    } else if (sf instanceof MDR_MapDataResource mdr) {
      writeMdrDirectly(mdr);
    } else if (sf instanceof MSU_MapSuppression msu) {
      writeMsuDirectly(msu);
    } else if (sf instanceof MMC_MediumModificationControl mmc) {
      writeMmcDirectly(mmc);
    } else if (sf instanceof MGO_MapGraphicsObject mgo) {
      writeMgoDirectly(mgo);
    } else if (sf instanceof MPO_MapPageOverlay mpo) {
      writeMpoDirectly(mpo);
    } else if (sf instanceof com.mgz.afp.modca.BDT_BeginDocument bdt) {
      writeBdtDirectly(bdt);
    } else if (sf instanceof BDI_BeginDocumentIndex bdi) {
      writeNameAndTripletsDirectly(bdi, "BDI_BeginDocumentIndex");
    } else if (sf instanceof BMO_BeginOverlay bmo) {
      writeBmoDirectly(bmo);
    } else if (sf instanceof BPS_BeginPageSegment bps) {
      writeBpsDirectly(bps);
    } else if (sf instanceof BRG_BeginResourceGroup brg) {
      writeNameAndTripletsDirectly(brg, "BRG_BeginResourceGroup");
    } else if (sf instanceof com.mgz.afp.modca.BNG_BeginNamedPageGroup bng) {
      writeNameAndTripletsDirectly(bng, "BNG_BeginNamedPageGroup");
    } else if (sf instanceof com.mgz.afp.modca.BPG_BeginPage bpg) {
      writeNameAndTripletsDirectly(bpg, "BPG_BeginPage");
    } else if (sf instanceof EDI_EndDocumentIndex edi) {
      writeNameDirectly(edi, "EDI_EndDocumentIndex");
    } else if (sf instanceof EMO_EndOverlay emo) {
      writeNameAndTripletsDirectly(emo, "EMO_EndOverlay");
    } else if (sf instanceof EPS_EndPageSegment eps) {
      writeNameDirectly(eps, "EPS_EndPageSegment");
    } else if (sf instanceof ERG_EndResourceGroup erg) {
      writeNameAndTripletsDirectly(erg, "ERG_EndResourceGroup");
    } else if (sf instanceof com.mgz.afp.modca.EDT_EndDocument edt) {
      writeNameAndTripletsDirectly(edt, "EDT_EndDocument");
    } else if (sf instanceof com.mgz.afp.modca.ENG_EndNamedPageGroup eng) {
      writeNameAndTripletsDirectly(eng, "ENG_EndNamedPageGroup");
    } else if (sf instanceof com.mgz.afp.modca.EPG_EndPage epg) {
      writeNameAndTripletsDirectly(epg, "EPG_EndPage");
    } else if (sf instanceof com.mgz.afp.modca.IEL_IndexElement iel) {
      writeTripletsAndTextDirectly(iel, "IEL_IndexElement");
    } else if (sf instanceof com.mgz.afp.modca.IPG_IncludePage ipg) {
      writeTripletsAndTextDirectly(ipg, "IPG_IncludePage");
    } else if (sf instanceof com.mgz.afp.modca.PGD_PageDescriptor pgd) {
      writePgdDirectly(pgd);
    } else if (sf instanceof PGP_PagePosition_Format1 pgp) {
      writePgpFormat1Directly(pgp);
    } else if (sf instanceof PGP_PagePosition_Format2 pgp) {
      writePgpFormat2Directly(pgp);
    } else if (sf instanceof com.mgz.afp.modca.MDD_MediumDescriptor mdd) {
      writeMddDirectly(mdd);
    } else if (sf instanceof com.mgz.afp.modca.IMM_InvokeMediumMap imm) {
      writeNameAndTripletsDirectly(imm, "IMM_InvokeMediumMap");
    } else if (sf instanceof com.mgz.afp.modca.BOC_BeginObjectContainer boc) {
      writeNameAndTripletsDirectly(boc, "BOC_BeginObjectContainer");
    } else if (sf instanceof com.mgz.afp.modca.EOC_EndObjectContainer eoc) {
      writeNameDirectly(eoc, "EOC_EndObjectContainer");
    } else if (sf instanceof com.mgz.afp.modca.BIM_BeginImageObject bim) {
      writeNameAndTripletsDirectly(bim, "BIM_BeginImageObject");
    } else if (sf instanceof com.mgz.afp.modca.EIM_EndImageObject eim) {
      writeNameDirectly(eim, "EIM_EndImageObject");
    } else if (sf instanceof com.mgz.afp.modca.PMC_PageModificationControl pmc) {
      writeTripletsAndTextDirectly(pmc, "PMC_PageModificationControl");
    } else if (sf instanceof com.mgz.afp.modca.PEC_PresentationEnvironmentControl pec) {
      writeTripletsAndTextDirectly(pec, "PEC_PresentationEnvironmentControl");
    } else if (sf instanceof com.mgz.afp.modca.IPS_IncludePageSegment ips) {
      writeTripletsAndTextDirectly(ips, "IPS_IncludePageSegment");
    } else if (sf instanceof com.mgz.afp.modca.IPO_IncludePageOverlay ipo) {
      writeTripletsAndTextDirectly(ipo, "IPO_IncludePageOverlay");
    } else if (sf instanceof com.mgz.afp.modca.MFC_MediumFinishingControl mfc) {
      writeTripletsAndTextDirectly(mfc, "MFC_MediumFinishingControl");
    } else if (sf instanceof com.mgz.afp.modca.PFC_PresentationFidelityControl pfc) {
      writeTripletsAndTextDirectly(pfc, "PFC_PresentationFidelityControl");
    } else if (sf instanceof BBC_BeginBarCodeObject bbc) {
      writeNameAndTripletsDirectly(bbc, "BBC_BeginBarCodeObject");
    } else if (sf instanceof EBC_EndBarCodeObject ebc) {
      writeNameAndTripletsDirectly(ebc, "EBC_EndBarCodeObject");
    } else if (sf instanceof BDD_BarCodeDataDescriptor bdd) {
      writeBddDirectly(bdd);
    } else if (sf instanceof BDA_BarCodeData bda) {
      writeBdaDirectly(bda);
    } else {
      String rootName = sf.getClass().getSimpleName();
      String mnemonic = MnemonicPerformanceMonitor.extractMnemonicFromString(rootName);
      MnemonicPerformanceMonitor.startWriteWithMnemonic(mnemonic);
      JacksonXmlMapperProvider.getCachedWriter(sf.getClass(), useWoodstox, true).writeValue(baseFragmentGenerator, sf);
      MnemonicPerformanceMonitor.endWrite();
    }

    if (!fragmentMode) {
      baseXsw.writeCharacters("\n");
    }
  }

  private void writeNopDirectly(NOP_NoOperation nop) throws Exception {
    MnemonicPerformanceMonitor.startWriteWithMnemonic("NOP");
    String text = nop.getText();
    if (text != null && !text.isEmpty()) {
      baseXsw.writeStartElement("NOP_NoOperation");
      XmlIndenter.writeIndent(baseXsw, 2);
      baseXsw.writeStartElement("text");
      baseXsw.writeCharacters(text);
      baseXsw.writeEndElement();
      XmlIndenter.writeIndent(baseXsw, 1);
      baseXsw.writeEndElement();
    } else {
      byte[] data = nop.getData();
      if (data == null || data.length == 0) {
        baseXsw.writeEmptyElement("NOP_NoOperation");
      } else {
        baseXsw.writeStartElement("NOP_NoOperation");
        writeBinaryElement(baseXsw, XmlIndenter.getIndent(2), "binaryData", data);
        XmlIndenter.writeIndent(baseXsw, 1);
        baseXsw.writeEndElement();
      }
    }
    MnemonicPerformanceMonitor.endWrite();
  }

  private void writeTleDirectly(TLE_TagLogicalElement tle) throws Exception {
    MnemonicPerformanceMonitor.startWriteWithMnemonic("TLE");
    baseXsw.writeStartElement("TLE_TagLogicalElement");
    String indent2 = XmlIndenter.getIndent(2);
    String indent1 = XmlIndenter.getIndent(1);
    if (tle.getTriplets() != null && !tle.getTriplets().isEmpty()) {
      for (Triplet triplet : tle.getTriplets()) {
        baseXsw.writeCharacters(indent2);
        writeTriplet(baseXsw, triplet, indent2);
      }
    }
    if (tle.getText() != null) {
      writeElement(baseXsw, indent2, "text", tle.getText());
    }
    baseXsw.writeCharacters(indent1);
    baseXsw.writeEndElement();
    MnemonicPerformanceMonitor.endWrite();
  }

  private void writeMcdDirectly(MCD_MapContainerData mcd) throws Exception {
    MnemonicPerformanceMonitor.startWriteWithMnemonic("MCD");
    baseXsw.writeStartElement("MCD_MapContainerData");
    String indent2 = XmlIndenter.getIndent(2);
    writeElement(baseXsw, indent2, "lengthOfRepeatingGroup", mcd.getLengthOfRepeatingGroup());
    if (mcd.getTriplet() != null) {
      baseXsw.writeCharacters(indent2);
      writeTriplet(baseXsw, mcd.getTriplet(), indent2);
    }
    XmlIndenter.writeIndent(baseXsw, 1);
    baseXsw.writeEndElement();
    MnemonicPerformanceMonitor.endWrite();
  }

  private void writePgpFormat1Directly(PGP_PagePosition_Format1 pgp) throws Exception {
    MnemonicPerformanceMonitor.startWriteWithMnemonic("PGP");
    baseXsw.writeStartElement("PGP_PagePosition_Format1");
    String indent = XmlIndenter.getIndent(2);
    writeElement(baseXsw, indent, "xOrigin", pgp.getxOrigin());
    writeElement(baseXsw, indent, "yOrigin", pgp.getyOrigin());
    XmlIndenter.writeIndent(baseXsw, 1);
    baseXsw.writeEndElement();
    MnemonicPerformanceMonitor.endWrite();
  }

  private void writePgpFormat2Directly(PGP_PagePosition_Format2 pgp) throws Exception {
    MnemonicPerformanceMonitor.startWriteWithMnemonic("PGP");
    baseXsw.writeStartElement("PGP_PagePosition_Format2");
    String indent2 = XmlIndenter.getIndent(2);
    String indent3 = XmlIndenter.getIndent(3);
    writeElement(baseXsw, indent2, "constant0", pgp.getConstant0());
    if (pgp.getRepeatingGroups() != null) {
      for (IRepeatingGroup rg : pgp.getRepeatingGroups()) {
        if (rg instanceof PGP_PagePosition_Format2.PGP_RepeatingGroup pgpRg) {
          baseXsw.writeCharacters(indent2);
          baseXsw.writeStartElement("pgpRepeatingGroup");
          writeElement(baseXsw, indent3, "repeatingGroupLength", pgpRg.getRepeatingGroupLength());
          writeElement(baseXsw, indent3, "xOrigin", pgpRg.getxOrigin());
          writeElement(baseXsw, indent3, "yOrigin", pgpRg.getyOrigin());
          if (pgpRg.getxRotation() != null) {
            writeElement(baseXsw, indent3, "xRotation", pgpRg.getxRotation().name());
          }
          if (pgpRg.getSheetSideAndPartitionSelection() != null) {
            writeElement(baseXsw, indent3, "sheetSideAndPartitionSelection", pgpRg.getSheetSideAndPartitionSelection().name());
          }
          if (pgpRg.getFlags() != null) {
            baseXsw.writeCharacters(indent3);
            baseXsw.writeStartElement("flags");
            for (PGP_PagePosition_Format2.PGP_RepeatingGroup.PGP_RGFlag flag : pgpRg.getFlags()) {
              baseXsw.writeCharacters(XmlIndenter.getIndent(4));
              baseXsw.writeStartElement("pgpRgFlag");
              baseXsw.writeCharacters(flag.name());
              baseXsw.writeEndElement();
            }
            baseXsw.writeCharacters(indent3);
            baseXsw.writeEndElement();
          }
          if (pgpRg.getPageModififationControlID() != null) {
            writeElement(baseXsw, indent3, "pageModififationControlID", pgpRg.getPageModififationControlID());
          }
          baseXsw.writeCharacters(indent2);
          baseXsw.writeEndElement();
        }
      }
    }
    XmlIndenter.writeIndent(baseXsw, 1);
    baseXsw.writeEndElement();
    MnemonicPerformanceMonitor.endWrite();
  }

  private void writeMsuDirectly(MSU_MapSuppression msu) throws Exception {
    MnemonicPerformanceMonitor.startWriteWithMnemonic("MSU");
    baseXsw.writeStartElement("MSU_MapSuppression");
    String indent2 = XmlIndenter.getIndent(2);
    String indent3 = XmlIndenter.getIndent(3);
    if (msu.getRepeatingGroups() != null) {
      for (IRepeatingGroup rg : msu.getRepeatingGroups()) {
        if (rg instanceof MSU_MapSuppression.MSU_RepeatingGroup msuRg) {
          baseXsw.writeCharacters(indent2);
          baseXsw.writeStartElement("msuRepeatingGroup");
          writeElement(baseXsw, indent3, "nameOfTextSuppresstion", msuRg.getNameOfTextSuppresstion());
          writeElement(baseXsw, indent3, "reserved8", msuRg.getReserved8());
          writeElement(baseXsw, indent3, "localID", msuRg.getLocalID());
          baseXsw.writeCharacters(indent2);
          baseXsw.writeEndElement();
        }
      }
    }
    XmlIndenter.writeIndent(baseXsw, 1);
    baseXsw.writeEndElement();
    MnemonicPerformanceMonitor.endWrite();
  }

  private void writeMmcDirectly(MMC_MediumModificationControl mmc) throws Exception {
    MnemonicPerformanceMonitor.startWriteWithMnemonic("MMC");
    baseXsw.writeStartElement("MMC_MediumModificationControl");
    String indent2 = XmlIndenter.getIndent(2);
    String indent3 = XmlIndenter.getIndent(3);
    String indent4 = XmlIndenter.getIndent(4);
    writeElement(baseXsw, indent2, "mmcIdentifier", mmc.getMmcIdentifier());
    writeElement(baseXsw, indent2, "constantData1", mmc.getConstantData1());
    if (mmc.getKeywords() != null && !mmc.getKeywords().isEmpty()) {
      baseXsw.writeCharacters(indent2);
      baseXsw.writeStartElement("keywords");
      for (MMC_MediumModificationControl.MMC_KeyWord kw : mmc.getKeywords()) {
        baseXsw.writeCharacters(indent3);
        baseXsw.writeStartElement("keyword");
        if (kw.keywordID() != null) {
          writeElement(baseXsw, indent4, "keywordID", kw.keywordID().name());
        }
        writeElement(baseXsw, indent4, "parameter", kw.parameter());
        baseXsw.writeCharacters(indent3);
        baseXsw.writeEndElement();
      }
      baseXsw.writeCharacters(indent2);
      baseXsw.writeEndElement();
    }
    XmlIndenter.writeIndent(baseXsw, 1);
    baseXsw.writeEndElement();
    MnemonicPerformanceMonitor.endWrite();
  }

  private void writeBagDirectly(BAG_BeginActiveEnvironmentGroup bag) throws Exception {
    MnemonicPerformanceMonitor.startWriteWithMnemonic("BAG");
    baseXsw.writeStartElement("BAG_BeginActiveEnvironmentGroup");
    writeTripletsAndText(baseXsw, bag.getTriplets(), bag.getText(), XmlIndenter.getIndent(2), XmlIndenter.getIndent(1));
    baseXsw.writeEndElement();
    MnemonicPerformanceMonitor.endWrite();
  }

  private void writeTripletsAndText(XMLStreamWriter2 writer, List<Triplet> triplets, String text, String indent, String closingIndent) throws Exception {
    if (triplets != null) {
      for (Triplet triplet : triplets) {
        writer.writeCharacters(indent);
        writeTriplet(writer, triplet, indent);
      }
    }
    if (text != null && !text.isEmpty()) {
      writer.writeCharacters(indent);
      writer.writeStartElement("text");
      writer.writeCharacters(text);
      writer.writeEndElement();
    }
    writer.writeCharacters(closingIndent);
  }

  private void writeTriplet(XMLStreamWriter2 writer, Triplet triplet, String indent) throws Exception {
    int level = indent.length() / 2;
    if (indent.startsWith("\n")) {
      level = (indent.length() - 1) / 2;
    }
    String childIndent = XmlIndenter.getIndent(level + 1);
    if (triplet instanceof Triplet.FullyQualifiedName fqn) {
      writer.writeStartElement("FullyQualifiedName");
      writeElement(writer, childIndent, "type", fqn.getType().name());
      writeElement(writer, childIndent, "format", fqn.getFormat().name());
      writeElement(writer, childIndent, "nameAsString", fqn.getNameAsString());
      writeElement(writer, childIndent, "text", fqn.getText());
      writer.writeCharacters(indent);
      writer.writeEndElement();
    } else if (triplet instanceof Triplet.AttributeValue av) {
      writer.writeStartElement("AttributeValue");
      writeElement(writer, childIndent, "attributeValue", av.getAttributeValue());
      writeElement(writer, childIndent, "text", av.getText());
      writer.writeCharacters(indent);
      writer.writeEndElement();
    } else if (triplet instanceof Triplet.CodedGraphicCharacterSetGlobalID cgcs) {
      writer.writeStartElement("CodedGraphicCharacterSetGlobalID");
      writeElement(writer, childIndent, "graphicCharacterSetGlobalID", cgcs.getGraphicCharacterSetGlobalID());
      writeElement(writer, childIndent, "codePageGlobalID_codedCharacterSetID", cgcs.getCodePageGlobalID_codedCharacterSetID());
      writer.writeCharacters(indent);
      writer.writeEndElement();
    } else if (triplet instanceof Triplet.MappingOption mo) {
      writer.writeStartElement("MappingOption");
      writeElement(writer, childIndent, "dataObjecMapingOption", mo.getDataObjecMapingOption().name());
      writer.writeCharacters(indent);
      writer.writeEndElement();
    } else if (triplet instanceof Triplet.AttributeQualifier aq) {
      writer.writeStartElement("AttributeQualifier");
      writeElement(writer, childIndent, "sequenceNumber", aq.sequenceNumber);
      writeElement(writer, childIndent, "levelNumber", aq.levelNumber);
      writer.writeCharacters(indent);
      writer.writeEndElement();
    } else if (triplet instanceof Triplet.Comment c) {
      writer.writeStartElement("Comment");
      writeElement(writer, childIndent, "comment", c.comment);
      writeElement(writer, childIndent, "text", c.getText());
      writer.writeCharacters(indent);
      writer.writeEndElement();
    } else if (triplet instanceof Triplet.ResourceLocalIdentifier rli) {
      writer.writeStartElement("ResourceLocalIdentifier");
      if (rli.getResourceType() != null) {
        writeElement(writer, childIndent, "resourceType", rli.getResourceType().name());
      }
      writeElement(writer, childIndent, "resourceLocalID", rli.getResourceLocalID());
      writer.writeCharacters(indent);
      writer.writeEndElement();
    } else if (triplet instanceof Triplet.ObjectClassification oc) {
      writer.writeStartElement("ObjectClassification");
      writeElement(writer, childIndent, "reserved2", oc.reserved2);
      if (oc.objectClass != null) {
        writeElement(writer, childIndent, "objectClass", oc.objectClass.name());
      }
      if (oc.reserved4_5 != null) {
        writeElement(writer, childIndent, "reserved4_5", UtilCharacterEncoding.bytesToHexString(oc.reserved4_5));
      }
      if (oc.structureFlags != null) {
        writer.writeCharacters(childIndent);
        writer.writeStartElement("structureFlags");
        for (Triplet.ObjectClassification.StructureFlag flag : oc.structureFlags) {
          writer.writeCharacters(XmlIndenter.getIndent(level + 2));
          writer.writeStartElement("structureFlag");
          writer.writeCharacters(flag.name());
          writer.writeEndElement();
        }
        writer.writeCharacters(childIndent);
        writer.writeEndElement();
      }
      if (oc.registeredObjectID != null) {
        writeElement(writer, childIndent, "registeredObjectID", UtilCharacterEncoding.bytesToHexString(oc.registeredObjectID));
      }
      writeElement(writer, childIndent, "objectTypeName", oc.objectTypeName);
      writeElement(writer, childIndent, "objectVersion", oc.objectVersion);
      writeElement(writer, childIndent, "companyName", oc.companyName);
      writer.writeCharacters(indent);
      writer.writeEndElement();
    } else if (triplet instanceof Triplet.MODCAInterchangeSet mis) {
      writer.writeStartElement("MODCAInterchangeSet");
      if (mis.type != null) {
        writeElement(writer, childIndent, "type", mis.type.name());
      }
      if (mis.identifier != null) {
        writeElement(writer, childIndent, "identifier", mis.identifier.name());
      }
      writer.writeCharacters(indent);
      writer.writeEndElement();
    } else if (triplet instanceof Triplet.CharacterRotation cr) {
      writer.writeStartElement("CharacterRotation");
      if (cr.characterRotation != null) {
        writeElement(writer, childIndent, "characterRotation", cr.characterRotation.name());
      }
      writer.writeCharacters(indent);
      writer.writeEndElement();
    } else if (triplet instanceof Triplet.ObjectByteOffset obo) {
      writer.writeStartElement("ObjectByteOffset");
      writeElement(writer, childIndent, "byteOffset", obo.byteOffset);
      if (obo.byteOffsetHighOrder != null) {
        writeElement(writer, childIndent, "byteOffsetHighOrder", obo.byteOffsetHighOrder);
      }
      writer.writeCharacters(indent);
      writer.writeEndElement();
    } else if (triplet instanceof Triplet.MeasurementUnits mu) {
      writer.writeStartElement("MeasurementUnits");
      if (mu.xUnitBase != null) {
        writeElement(writer, childIndent, "xUnitBase", mu.xUnitBase.name());
      }
      if (mu.yUnitBase != null) {
        writeElement(writer, childIndent, "yUnitBase", mu.yUnitBase.name());
      }
      writeElement(writer, childIndent, "xUnitsPerUnitbase", mu.xUnitsPerUnitbase);
      writeElement(writer, childIndent, "yUnitsPerUnitbase", mu.yUnitsPerUnitbase);
      writer.writeCharacters(indent);
      writer.writeEndElement();
    } else if (triplet instanceof Triplet.ObjectAreaSize oas) {
      writer.writeStartElement("ObjectAreaSize");
      writeElement(writer, childIndent, "sizeType_0x02", oas.sizeType_0x02);
      writeElement(writer, childIndent, "xSize", oas.xSize);
      writeElement(writer, childIndent, "ySize", oas.ySize);
      writer.writeCharacters(indent);
      writer.writeEndElement();
    } else if (triplet instanceof Triplet.AreaDefinition ad) {
      writer.writeStartElement("AreaDefinition");
      writeElement(writer, childIndent, "reserved2", ad.reserved2);
      writeElement(writer, childIndent, "xOrigin", ad.xOrigin);
      writeElement(writer, childIndent, "yOrigin", ad.yOrigin);
      writeElement(writer, childIndent, "xSize", ad.xSize);
      writeElement(writer, childIndent, "ySize", ad.ySize);
      writer.writeCharacters(indent);
      writer.writeEndElement();
    } else if (triplet instanceof Triplet.ColorSpecification cs) {
      writer.writeStartElement("ColorSpecification");
      writeElement(writer, childIndent, "reserved2", cs.reserved2);
      if (cs.colorSpace != null) {
        writeElement(writer, childIndent, "colorSpace", cs.colorSpace.name());
      }
      if (cs.reserved4_7 != null) {
        writeElement(writer, childIndent, "reserved4_7", UtilCharacterEncoding.bytesToHexString(cs.reserved4_7));
      }
      writeElement(writer, childIndent, "nrOfBitsComponent1", cs.nrOfBitsComponent1);
      writeElement(writer, childIndent, "nrOfBitsComponent2", cs.nrOfBitsComponent2);
      writeElement(writer, childIndent, "nrOfBitsComponent3", cs.nrOfBitsComponent3);
      writeElement(writer, childIndent, "nrOfBitsComponent4", cs.nrOfBitsComponent4);
      if (cs.colorValue != null) {
        writeElement(writer, childIndent, "colorValue", UtilCharacterEncoding.bytesToHexString(cs.colorValue));
      }
      writer.writeCharacters(indent);
      writer.writeEndElement();
    } else if (triplet instanceof Triplet.EncodingSchemeID esi) {
      writer.writeStartElement("EncodingSchemeID");
      if (esi.encodingSchemeForCodePage != null) {
        writer.writeCharacters(childIndent);
        writer.writeStartElement("encodingSchemeForCodePage");
        for (Triplet.EncodingSchemeID.EncodingScheme es : esi.encodingSchemeForCodePage) {
          writer.writeCharacters(XmlIndenter.getIndent(level + 2));
          writer.writeStartElement("encodingScheme");
          writer.writeCharacters(es.name());
          writer.writeEndElement();
        }
        writer.writeCharacters(childIndent);
        writer.writeEndElement();
      }
      if (esi.encodingSchemeForUserData != null) {
        writer.writeCharacters(childIndent);
        writer.writeStartElement("encodingSchemeForUserData");
        for (Triplet.EncodingSchemeID.EncodingScheme es : esi.encodingSchemeForUserData) {
          writer.writeCharacters(XmlIndenter.getIndent(level + 2));
          writer.writeStartElement("encodingScheme");
          writer.writeCharacters(es.name());
          writer.writeEndElement();
        }
        writer.writeCharacters(childIndent);
        writer.writeEndElement();
      }
      writer.writeCharacters(indent);
      writer.writeEndElement();
    } else if (triplet instanceof Triplet.ObjectCount oc) {
      writer.writeStartElement("ObjectCount");
      writeElement(writer, childIndent, "subordinateObjectType", oc.subordinateObjectType);
      writeElement(writer, childIndent, "reserved3", oc.reserved3);
      writeElement(writer, childIndent, "numberOfObjectsLow", oc.numberOfObjectsLow);
      if (oc.numberOfObjectsHigh != null) {
        writeElement(writer, childIndent, "numberOfObjectsHigh", oc.numberOfObjectsHigh);
      }
      writer.writeCharacters(indent);
      writer.writeEndElement();
    } else if (triplet instanceof Triplet.LocalObjectDateAndTimeStamp lodts) {
      writer.writeStartElement("LocalObjectDateAndTimeStamp");
      if (lodts.dateAndTimeStampType != null) {
        writeElement(writer, childIndent, "dateAndTimeStampType", lodts.dateAndTimeStampType.name());
      }
      writeElement(writer, childIndent, "hundreds", lodts.hundreds);
      writeElement(writer, childIndent, "tens", lodts.tens);
      writeElement(writer, childIndent, "dayOfYear", lodts.dayOfYear);
      writeElement(writer, childIndent, "hourOfDay", lodts.hourOfDay);
      writeElement(writer, childIndent, "minuteOfHour", lodts.minuteOfHour);
      writeElement(writer, childIndent, "secondOfMinute", lodts.secondOfMinute);
      writeElement(writer, childIndent, "hundredthOfSecond", lodts.hundredthOfSecond);
      writer.writeCharacters(indent);
      writer.writeEndElement();
    } else if (triplet instanceof Triplet.UniversalDateAndTimeStamp udts) {
      writer.writeStartElement("UniversalDateAndTimeStamp");
      writeElement(writer, childIndent, "reserved2", udts.reserved2);
      writeElement(writer, childIndent, "year", udts.year);
      writeElement(writer, childIndent, "monthOfYear", udts.monthOfYear);
      writeElement(writer, childIndent, "dayOfMonth", udts.dayOfMonth);
      writeElement(writer, childIndent, "hourOfDay", udts.hourOfDay);
      writeElement(writer, childIndent, "minuteOfHour", udts.minuteOfHour);
      writeElement(writer, childIndent, "secondOfMinute", udts.secondOfMinute);
      if (udts.timeZone != null) {
        writeElement(writer, childIndent, "timeZone", udts.timeZone.name());
      }
      writeElement(writer, childIndent, "diffHours", udts.diffHours);
      writeElement(writer, childIndent, "diffMinutes", udts.diffMinutes);
      writer.writeCharacters(indent);
      writer.writeEndElement();
    } else if (triplet instanceof Triplet.FontDescriptorSpecification fds) {
      writer.writeStartElement("FontDescriptorSpecification");
      if (fds.fontWeigthClass != null) {
        writeElement(writer, childIndent, "fontWeigthClass", fds.fontWeigthClass.name());
      }
      if (fds.fontWidthClass != null) {
        writeElement(writer, childIndent, "fontWidthClass", fds.fontWidthClass.name());
      }
      writeElement(writer, childIndent, "fontHeight", fds.fontHeight);
      writeElement(writer, childIndent, "fontWidth", fds.fontWidth);
      if (fds.fontDsFlags != null) {
        writer.writeCharacters(childIndent);
        writer.writeStartElement("fontDsFlags");
        for (Triplet.FontDescriptorSpecification.FDS_FontDsFlag flag : fds.fontDsFlags) {
          writer.writeCharacters(XmlIndenter.getIndent(level + 2));
          writer.writeStartElement("fdsFontDsFlag");
          writer.writeCharacters(flag.name());
          writer.writeEndElement();
        }
        writer.writeCharacters(childIndent);
        writer.writeEndElement();
      }
      if (fds.reserved9_18 != null) {
        writeElement(writer, childIndent, "reserved9_18", UtilCharacterEncoding.bytesToHexString(fds.reserved9_18));
      }
      if (fds.fontUsFlags != null) {
        writer.writeCharacters(childIndent);
        writer.writeStartElement("fontUsFlags");
        for (Triplet.FontDescriptorSpecification.FDS_FontUsFlag flag : fds.fontUsFlags) {
          writer.writeCharacters(XmlIndenter.getIndent(level + 2));
          writer.writeStartElement("fdsFontUsFlag");
          writer.writeCharacters(flag.name());
          writer.writeEndElement();
        }
        writer.writeCharacters(childIndent);
        writer.writeEndElement();
      }
      writer.writeCharacters(indent);
      writer.writeEndElement();
    } else if (triplet instanceof Triplet.ResourceObjectType rot) {
      writer.writeStartElement("ResourceObjectType");
      if (rot.objectType != null) {
        writeElement(writer, childIndent, "objectType", rot.objectType.name());
      }
      writer.writeCharacters(indent);
      writer.writeEndElement();
    } else {
      JacksonXmlMapperProvider.getCachedWriter(triplet.getClass(), useWoodstox, true).writeValue(baseFragmentGenerator, triplet);
    }
  }

  private void writeMcfDirectly(MCF_MapCodedFont_Format2 mcf) throws Exception {
    MnemonicPerformanceMonitor.startWriteWithMnemonic("MCF");
    baseXsw.writeStartElement("MCF_MapCodedFont_Format2");
    List<IRepeatingGroup> repeatingGroups = mcf.getRepeatingGroups();
    if (repeatingGroups != null) {
      for (IRepeatingGroup rg : repeatingGroups) {
        XmlIndenter.writeIndent(baseXsw, 2);
        writeMcfRepeatingGroup(rg, XmlIndenter.getIndent(2));
      }
    }
    XmlIndenter.writeIndent(baseXsw, 1);
    baseXsw.writeEndElement();
    MnemonicPerformanceMonitor.endWrite();
  }

  private void writeMcfRepeatingGroup(IRepeatingGroup rg, String indent) throws Exception {
    String childIndent = XmlIndenter.getIndent(3);
    baseXsw.writeStartElement("mcf2RepeatingGroup");
    if (rg instanceof RepeatingGroupWithTriplets rgt) {
      List<Triplet> triplets = rgt.getTriplets();
      if (triplets != null) {
        for (Triplet triplet : triplets) {
          baseXsw.writeCharacters(childIndent);
          writeTriplet(baseXsw, triplet, childIndent);
        }
      }
    }
    baseXsw.writeCharacters(indent);
    baseXsw.writeEndElement();
  }

  private void writePtxDirectly(PTX_PresentationTextData ptx) throws Exception {
    MnemonicPerformanceMonitor.startWriteWithMnemonic("PTX");
    baseXsw.writeStartElement("PTX_PresentationTextData");
    List<PTOCAControlSequence> sequences = ptx.getControlSequences();
    if (sequences != null) {
      boolean ptxDebug = com.mgz.util.PTXPerformanceMonitor.isEnabled();
      for (PTOCAControlSequence cs : sequences) {
        baseXsw.writeCharacters(XmlIndenter.getIndent(2));
        long csStart = ptxDebug ? System.nanoTime() : 0;
        long csStartCount = ptxDebug ? cos.getCount() : 0;
        writeControlSequence(cs, XmlIndenter.getIndent(2));
        if (csStart > 0) {
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
    baseXsw.writeEndElement();
    MnemonicPerformanceMonitor.endWrite();
  }

  private void writeControlSequence(PTOCAControlSequence cs, String indent) throws Exception {
    String childIndent = XmlIndenter.getIndent(3);
    if (cs instanceof PTOCAControlSequence.TRN_TransparentData trn) {
      MnemonicPerformanceMonitor.startWriteWithMnemonic("TRN");
      baseXsw.writeStartElement("TRN_TransparentData");
      writeElement(baseXsw, childIndent, "transparentData", trn.getTransparentData());
      writeElement(baseXsw, childIndent, "text", trn.getText());
      baseXsw.writeCharacters(indent);
      baseXsw.writeEndElement();
      MnemonicPerformanceMonitor.endWrite();
    } else if (cs instanceof PTOCAControlSequence.GraphicCharacters gc) {
      MnemonicPerformanceMonitor.startWriteWithMnemonic("GraphicCharacters");
      baseXsw.writeStartElement("GraphicCharacters");
      writeElement(baseXsw, childIndent, "text", gc.getText());
      baseXsw.writeCharacters(indent);
      baseXsw.writeEndElement();
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
      baseXsw.writeStartElement("STO_SetTextOrientation");
      if (sto.getxOrientation() != null) {
        writeElement(baseXsw, childIndent, "xOrientation", sto.getxOrientation().name());
      }
      if (sto.getyOrientation() != null) {
        writeElement(baseXsw, childIndent, "yOrientation", sto.getyOrientation().name());
      }
      baseXsw.writeCharacters(indent);
      baseXsw.writeEndElement();
      MnemonicPerformanceMonitor.endWrite();
    } else if (cs instanceof PTOCAControlSequence.STC_SetTextColor stc) {
      MnemonicPerformanceMonitor.startWriteWithMnemonic("STC");
      baseXsw.writeStartElement("STC_SetTextColor");
      if (stc.getForegroundColor() != null) {
        writeElement(baseXsw, childIndent, "foregroundColor", stc.getForegroundColor().name());
      }
      if (stc.getPrecision() != null) {
        writeElement(baseXsw, childIndent, "precision", stc.getPrecision().name());
      }
      baseXsw.writeCharacters(indent);
      baseXsw.writeEndElement();
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
      baseXsw.writeStartElement("SIA_SetIntercharacterAdjustment");
      writeElement(baseXsw, childIndent, "adjustment", sia.getAdjustment());
      if (sia.getDirection() != null) {
        writeElement(baseXsw, childIndent, "direction", sia.getDirection().name());
      }
      baseXsw.writeCharacters(indent);
      baseXsw.writeEndElement();
      MnemonicPerformanceMonitor.endWrite();
    } else if (cs instanceof PTOCAControlSequence.SVI_SetVariableSpaceCharacterIncrement svi) {
      MnemonicPerformanceMonitor.startWriteWithMnemonic("SVI");
      baseXsw.writeEmptyElement("SVI_SetVariableSpaceCharacterIncrement");
      baseXsw.writeIntAttribute(null, null, "increment", svi.getIncrement());
      MnemonicPerformanceMonitor.endWrite();
    } else if (cs instanceof PTOCAControlSequence.SEC_SetExtendedTextColor sec) {
      MnemonicPerformanceMonitor.startWriteWithMnemonic("SEC");
      baseXsw.writeStartElement("SEC_SetExtendedTextColor");
      writeElement(baseXsw, childIndent, "colorSpace", sec.getColorSpace().name());
      writeElement(baseXsw, childIndent, "nrOfBitsComponent1", sec.getNrOfBitsComponent1());
      writeElement(baseXsw, childIndent, "nrOfBitsComponent2", sec.getNrOfBitsComponent2());
      writeElement(baseXsw, childIndent, "nrOfBitsComponent3", sec.getNrOfBitsComponent3());
      writeElement(baseXsw, childIndent, "nrOfBitsComponent4", sec.getNrOfBitsComponent4());
      if (sec.getColorValue() != null) {
        writeBinaryElement(baseXsw, childIndent, "colorValue", sec.getColorValue());
      }
      baseXsw.writeCharacters(indent);
      baseXsw.writeEndElement();
      MnemonicPerformanceMonitor.endWrite();
    } else if (cs instanceof PTOCAControlSequence.DIR_DrawIaxisRule dir) {
      MnemonicPerformanceMonitor.startWriteWithMnemonic("DIR");
      baseXsw.writeStartElement("DIR_DrawIaxisRule");
      writeElement(baseXsw, childIndent, "length", dir.getLength());
      if (dir.getWidth() != null) {
        writeElement(baseXsw, childIndent, "width", dir.getWidth());
      }
      if (dir.getWidthFraction() != null) {
        writeElement(baseXsw, childIndent, "widthFraction", dir.getWidthFraction());
      }
      baseXsw.writeCharacters(indent);
      baseXsw.writeEndElement();
      MnemonicPerformanceMonitor.endWrite();
    } else if (cs instanceof PTOCAControlSequence.DBR_DrawBaxisRule dbr) {
      MnemonicPerformanceMonitor.startWriteWithMnemonic("DBR");
      baseXsw.writeStartElement("DBR_DrawBaxisRule");
      writeElement(baseXsw, childIndent, "length", dbr.getLength());
      if (dbr.getWidth() != null) {
        writeElement(baseXsw, childIndent, "width", dbr.getWidth());
      }
      if (dbr.getWidthFraction() != null) {
        writeElement(baseXsw, childIndent, "widthFraction", dbr.getWidthFraction());
      }
      baseXsw.writeCharacters(indent);
      baseXsw.writeEndElement();
      MnemonicPerformanceMonitor.endWrite();
    } else if (cs instanceof PTOCAControlSequence.NOP_NoOperation nop) {
      MnemonicPerformanceMonitor.startWriteWithMnemonic("NOP");
      baseXsw.writeStartElement("NOP_NoOperation");
      if (nop.getText() != null) {
        writeElement(baseXsw, childIndent, "text", nop.getText());
      }
      if (nop.getIgnoredData() != null) {
        writeBinaryElement(baseXsw, childIndent, "ignoredData", nop.getIgnoredData());
      }
      baseXsw.writeCharacters(indent);
      baseXsw.writeEndElement();
      MnemonicPerformanceMonitor.endWrite();
    } else if (cs instanceof PTOCAControlSequence.TBM_TemporaryBaselineMove tbm) {
      MnemonicPerformanceMonitor.startWriteWithMnemonic("TBM");
      baseXsw.writeStartElement("TBM_TemporaryBaselineMove");
      if (tbm.getDirection() != null) {
        writeElement(baseXsw, childIndent, "direction", tbm.getDirection().name());
      }
      if (tbm.getPrecision() != null) {
        writeElement(baseXsw, childIndent, "precision", tbm.getPrecision().name());
      }
      if (tbm.getTemporaryBaselineIncrement() != null) {
        writeElement(baseXsw, childIndent, "temporaryBaselineIncrement", tbm.getTemporaryBaselineIncrement());
      }
      baseXsw.writeCharacters(indent);
      baseXsw.writeEndElement();
      MnemonicPerformanceMonitor.endWrite();
    } else if (cs instanceof PTOCAControlSequence.OVS_Overstrike ovs) {
      MnemonicPerformanceMonitor.startWriteWithMnemonic("OVS");
      baseXsw.writeStartElement("OVS_Overstrike");
      if (ovs.getBypassFlag() != null) {
        writeElement(baseXsw, childIndent, "bypassFlag", ovs.getBypassFlag().name());
      }
      writeElement(baseXsw, childIndent, "overStrikeCharacterCodePoint", ovs.getOverStrikeCharacterCodePoint());
      writeElement(baseXsw, childIndent, "text", ovs.getText());
      baseXsw.writeCharacters(indent);
      baseXsw.writeEndElement();
      MnemonicPerformanceMonitor.endWrite();
    } else if (cs instanceof PTOCAControlSequence.RPS_RepeatString rps) {
      MnemonicPerformanceMonitor.startWriteWithMnemonic("RPS");
      baseXsw.writeStartElement("RPS_RepeatString");
      writeElement(baseXsw, childIndent, "repeatLength", rps.getRepeatLength());
      if (rps.getText() != null) {
        writeElement(baseXsw, childIndent, "text", rps.getText());
      }
      if (rps.getRepeatData() != null) {
        writeBinaryElement(baseXsw, childIndent, "repeatData", rps.getRepeatData());
      }
      baseXsw.writeCharacters(indent);
      baseXsw.writeEndElement();
      MnemonicPerformanceMonitor.endWrite();
    } else {
      String simpleName = cs.getClass().getSimpleName();
      String mnemonic = MnemonicPerformanceMonitor.extractMnemonicFromString(simpleName);
      MnemonicPerformanceMonitor.startWriteWithMnemonic(mnemonic);
      JacksonXmlMapperProvider.getCachedWriter(cs.getClass(), useWoodstox, true).writeValue(baseFragmentGenerator, cs);
      MnemonicPerformanceMonitor.endWrite();
    }
  }

  private void writeFncDirectly(FNC_FontControl fnc) throws Exception {
    MnemonicPerformanceMonitor.startWriteWithMnemonic("FNC");
    baseXsw.writeStartElement("FNC_FontControl");
    String indent = XmlIndenter.getIndent(2);
    writeElement(baseXsw, indent, "retired0", fnc.getRetired0());
    if (fnc.getPatternTechnologyIdentifier() != null) {
      writeElement(baseXsw, indent, "patternTechnologyIdentifier", fnc.getPatternTechnologyIdentifier().name());
    }
    writeElement(baseXsw, indent, "reserved2", fnc.getReserved2());
    if (fnc.getFontUseFlags() != null) {
      baseXsw.writeCharacters(indent);
      baseXsw.writeStartElement("fontUseFlags");
      for (FNC_FontControl.FncFontUseFlag flag : fnc.getFontUseFlags()) {
        baseXsw.writeCharacters(XmlIndenter.getIndent(3));
        baseXsw.writeStartElement("fncFontUseFlag");
        baseXsw.writeCharacters(flag.name());
        baseXsw.writeEndElement();
      }
      baseXsw.writeCharacters(indent);
      baseXsw.writeEndElement();
    }
    if (fnc.getxUnitBase() != null) {
      writeElement(baseXsw, indent, "xUnitBase", fnc.getxUnitBase().name());
    }
    if (fnc.getyUnitBase() != null) {
      writeElement(baseXsw, indent, "yUnitBase", fnc.getyUnitBase().name());
    }
    writeElement(baseXsw, indent, "xUnitsPerUnitBase", fnc.getxUnitsPerUnitBase());
    writeElement(baseXsw, indent, "yUnitsPerUnitBase", fnc.getyUnitsPerUnitBase());
    writeElement(baseXsw, indent, "maxCharacterBoxWidth", fnc.getMaxCharacterBoxWidth());
    writeElement(baseXsw, indent, "maxCharacterBoxHeight", fnc.getMaxCharacterBoxHeight());
    writeElement(baseXsw, indent, "fnoRepeatingGroupLength", fnc.getFnoRepeatingGroupLength());
    writeElement(baseXsw, indent, "fniRepeatingGroupLength", fnc.getFniRepeatingGroupLength());
    if (fnc.getRasterPatternDataAlignment() != null) {
      writeElement(baseXsw, indent, "rasterPatternDataAlignment", fnc.getRasterPatternDataAlignment().name());
    }
    writeElement(baseXsw, indent, "rasterPatternDataCount", fnc.getRasterPatternDataCount());
    writeElement(baseXsw, indent, "fnpRepeatingGroupLength", fnc.getFnpRepeatingGroupLength());
    writeElement(baseXsw, indent, "fnmRepeatingGroupLength", fnc.getFnmRepeatingGroupLength());
    writeElement(baseXsw, indent, "shapeResolutionXUnitBase10Inches", fnc.getShapeResolutionXUnitBase10Inches());
    writeElement(baseXsw, indent, "shapeResolutionYUnitBase10Inches", fnc.getShapeResolutionYUnitBase10Inches());
    writeElement(baseXsw, indent, "shapeResolutionXUnitsPerUnitBase", fnc.getShapeResolutionXUnitsPerUnitBase());
    writeElement(baseXsw, indent, "shapeResolutionYUnitsPerUnitBase", fnc.getShapeResolutionYUnitsPerUnitBase());
    writeElement(baseXsw, indent, "outlinePatternDataCount", fnc.getOutlinePatternDataCount());
    if (fnc.getReserved32_34() != null) {
      writeBinaryElement(baseXsw, indent, "reserved32_34", fnc.getReserved32_34());
    }
    writeElement(baseXsw, indent, "fnnRepeatingGroupLength", fnc.getFnnRepeatingGroupLength());
    writeElement(baseXsw, indent, "fnnDataCount", fnc.getFnnDataCount());
    writeElement(baseXsw, indent, "fnnIbmNameGcgidCount", fnc.getFnnIbmNameGcgidCount());

    if (fnc.getTriplets() != null) {
      for (Triplet triplet : fnc.getTriplets()) {
        baseXsw.writeCharacters(indent);
        writeTriplet(baseXsw, triplet, indent);
      }
    }
    XmlIndenter.writeIndent(baseXsw, 1);
    baseXsw.writeEndElement();
    MnemonicPerformanceMonitor.endWrite();
  }

  private void writeLndDirectly(LND_LineDescriptor lnd) throws Exception {
    MnemonicPerformanceMonitor.startWriteWithMnemonic("LND");
    baseXsw.writeStartElement("LND_LineDescriptor");
    String indent = XmlIndenter.getIndent(2);
    if (lnd.getFlags() != null) {
      baseXsw.writeCharacters(indent);
      baseXsw.writeStartElement("flags");
      for (LND_LineDescriptor.LND_Flag flag : lnd.getFlags()) {
        baseXsw.writeCharacters(XmlIndenter.getIndent(3));
        baseXsw.writeStartElement("lndFlag");
        baseXsw.writeCharacters(flag.name());
        baseXsw.writeEndElement();
      }
      baseXsw.writeCharacters(indent);
      baseXsw.writeEndElement();
    }
    writeElement(baseXsw, indent, "inlinePosition", lnd.getInlinePosition());
    writeElement(baseXsw, indent, "baselinePosition", lnd.getBaselinePosition());
    if (lnd.getInlineOrientation() != null) {
      writeElement(baseXsw, indent, "inlineOrientation", lnd.getInlineOrientation().name());
    }
    if (lnd.getBaselineOrientation() != null) {
      writeElement(baseXsw, indent, "baselineOrientation", lnd.getBaselineOrientation().name());
    }
    writeElement(baseXsw, indent, "primaryFontLocalId", lnd.getPrimaryFontLocalId());
    writeElement(baseXsw, indent, "channelCode", lnd.getChannelCode());
    writeElement(baseXsw, indent, "nextLNDIfSkipping", lnd.getNextLNDIfSkipping());
    writeElement(baseXsw, indent, "nextLNDIfSpacing", lnd.getNextLNDIfSpacing());
    writeElement(baseXsw, indent, "nextLNDIfReusingData", lnd.getNextLNDIfReusingData());
    writeElement(baseXsw, indent, "suppressionTokenName", lnd.getSuppressionTokenName());
    writeElement(baseXsw, indent, "shiftOutLocalFontID", lnd.getShiftOutLocalFontID());
    writeElement(baseXsw, indent, "dataStartPosition", lnd.getDataStartPosition());
    writeElement(baseXsw, indent, "dataLength", lnd.getDataLength());
    if (lnd.getTextColor() != null) {
      writeElement(baseXsw, indent, "textColor", lnd.getTextColor().name());
    }
    writeElement(baseXsw, indent, "nextLNDIfConditionalProcessing", lnd.getNextLNDIfConditionalProcessing());
    writeElement(baseXsw, indent, "subpageID", lnd.getSubpageID());
    writeElement(baseXsw, indent, "ccpIdentifier", lnd.getCcpIdentifier());

    if (lnd.getTriplets() != null) {
      for (Triplet triplet : lnd.getTriplets()) {
        baseXsw.writeCharacters(indent);
        writeTriplet(baseXsw, triplet, indent);
      }
    }
    XmlIndenter.writeIndent(baseXsw, 1);
    baseXsw.writeEndElement();
    MnemonicPerformanceMonitor.endWrite();
  }

  private void writeGadDirectly(GAD_GraphicsData gad) throws Exception {
    MnemonicPerformanceMonitor.startWriteWithMnemonic("GAD");
    baseXsw.writeStartElement("GAD_GraphicsData");
    String indent = XmlIndenter.getIndent(2);
    List<GAD_DrawingOrder> orders = gad.getDrawingOrders();
    if (orders != null) {
      for (GAD_DrawingOrder order : orders) {
        baseXsw.writeCharacters(indent);
        writeDrawingOrderDirectly(baseXsw, order, indent);
      }
    }
    XmlIndenter.writeIndent(baseXsw, 1);
    baseXsw.writeEndElement();
    MnemonicPerformanceMonitor.endWrite();
  }

  private void writeDrawingOrderDirectly(XMLStreamWriter2 writer, GAD_DrawingOrder order, String indent) throws Exception {
    String childIndent = indent + "  ";
    if (order instanceof GAD_DrawingOrder.GNOP1_NopOperation) {
      writer.writeEmptyElement("GNOP1_NopOperation");
    } else if (order instanceof GAD_DrawingOrder.GCLINE_LineAtCurrentPosition gcline) {
      MnemonicPerformanceMonitor.startWriteWithMnemonic("GCLINE");
      writeDrawingOrderWithPoints(writer, gcline, "GCLINE_LineAtCurrentPosition", indent, childIndent);
      MnemonicPerformanceMonitor.endWrite();
    } else if (order instanceof GAD_DrawingOrder.GLINE_LineAtGivenPosition gline) {
      MnemonicPerformanceMonitor.startWriteWithMnemonic("GLINE");
      writeDrawingOrderWithPoints(writer, gline, "GLINE_LineAtGivenPosition", indent, childIndent);
      MnemonicPerformanceMonitor.endWrite();
    } else if (order instanceof GAD_DrawingOrder.GCMRK_MarkerAtCurrentPosition gcmrk) {
      MnemonicPerformanceMonitor.startWriteWithMnemonic("GCMRK");
      writeDrawingOrderWithPoints(writer, gcmrk, "GCMRK_MarkerAtCurrentPosition", indent, childIndent);
      MnemonicPerformanceMonitor.endWrite();
    } else if (order instanceof GAD_DrawingOrder.GMRK_MarkerAtGivenPosition gmrk) {
      MnemonicPerformanceMonitor.startWriteWithMnemonic("GMRK");
      writeDrawingOrderWithPoints(writer, gmrk, "GMRK_MarkerAtGivenPosition", indent, childIndent);
      MnemonicPerformanceMonitor.endWrite();
    } else if (order instanceof GAD_DrawingOrder.GCFLT_FilletAtCurrentPosition gcflt) {
      MnemonicPerformanceMonitor.startWriteWithMnemonic("GCFLT");
      writeDrawingOrderWithPoints(writer, gcflt, "GCFLT_FilletAtCurrentPosition", indent, childIndent);
      MnemonicPerformanceMonitor.endWrite();
    } else if (order instanceof GAD_DrawingOrder.GFLT_FilletAtGivenPosition gflt) {
      MnemonicPerformanceMonitor.startWriteWithMnemonic("GFLT");
      writeDrawingOrderWithPoints(writer, gflt, "GFLT_FilletAtGivenPosition", indent, childIndent);
      MnemonicPerformanceMonitor.endWrite();
    } else if (order instanceof GAD_DrawingOrder.GCCBEZ_CubicBezierCurveAtCurrentPosition gccbez) {
      MnemonicPerformanceMonitor.startWriteWithMnemonic("GCCBEZ");
      writeDrawingOrderWithPoints(writer, gccbez, "GCCBEZ_CubicBezierCurveAtCurrentPosition", indent, childIndent);
      MnemonicPerformanceMonitor.endWrite();
    } else if (order instanceof GAD_DrawingOrder.GCBEZ_CubicBezierCurveAtGivenPosition gcbez) {
      MnemonicPerformanceMonitor.startWriteWithMnemonic("GCBEZ");
      writeDrawingOrderWithPoints(writer, gcbez, "GCBEZ_CubicBezierCurveAtGivenPosition", indent, childIndent);
      MnemonicPerformanceMonitor.endWrite();
    } else if (order instanceof GAD_DrawingOrder.GCFARC_FullArcAtCurrentPosition gcfarc) {
      MnemonicPerformanceMonitor.startWriteWithMnemonic("GCFARC");
      writer.writeEmptyElement("GCFARC_FullArcAtCurrentPosition");
      writer.writeAttribute("multiplierIntegerPortion", String.valueOf(gcfarc.getMultiplierIntegerPortion()));
      writer.writeAttribute("multiplierFractionalPortion", String.valueOf(gcfarc.getMultiplierFractionalPortion()));
      MnemonicPerformanceMonitor.endWrite();
    } else if (order instanceof GAD_DrawingOrder.GCOMT_Comment gcomt) {
      MnemonicPerformanceMonitor.startWriteWithMnemonic("GCOMT");
      writer.writeStartElement("GCOMT_Comment");
      writer.writeAttribute("lengthOfFollowingData", String.valueOf(gcomt.getLengthOfFollowingData()));
      if (gcomt.getText() != null) {
        writer.writeAttribute("text", gcomt.getText());
      }
      if (gcomt.comment != null) {
        writeBinaryElement(writer, childIndent, "comment", gcomt.comment);
      }
      writer.writeCharacters(indent);
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
      writer.writeAttribute("lengthOfFollowingData", String.valueOf(gsgch.lengthOfFollowingData));
      writer.writeAttribute("identificationCode", String.valueOf(gsgch.getIdentificationCode()));
      if (gsgch.getParameters() != null) {
        writeBinaryElement(writer, childIndent, "parameters", gsgch.getParameters());
      }
      writer.writeCharacters(indent);
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
      writeElement(writer, childIndent, "lengthOfFollowingData", gscc.lengthOfFollowingData);
      writeElement(writer, childIndent, "widthOfCharacterCellIntegerPart", gscc.getWidthOfCharacterCellIntegerPart());
      writeElement(writer, childIndent, "heightOfCharacterCellIntegerPart", gscc.getHeightOfCharacterCellIntegerPart());
      if (gscc.getWidthOfCharacterCellFractionalPart() != null) {
        writeElement(writer, childIndent, "widthOfCharacterCellFractionalPart", gscc.getWidthOfCharacterCellFractionalPart());
      }
      if (gscc.getHeightOfCharacterCellFractionalPart() != null) {
        writeElement(writer, childIndent, "heightOfCharacterCellFractionalPart", gscc.getHeightOfCharacterCellFractionalPart());
      }
      writer.writeCharacters(indent);
      writer.writeEndElement();
    } else if (order instanceof GAD_DrawingOrder.GSCA_SetCharacterAngle gsca) {
      writer.writeStartElement("GSCA_SetCharacterAngle");
      writeElement(writer, childIndent, "lengthOfFollowingData", gsca.lengthOfFollowingData);
      if (gsca.getAnglePoint() != null) {
        writer.writeCharacters(childIndent);
        writer.writeStartElement("anglePoint");
        writeElement(writer, childIndent + "  ", "xCoordinate", gsca.getAnglePoint().xCoordinate());
        writeElement(writer, childIndent + "  ", "yCoordinate", gsca.getAnglePoint().yCoordinate());
        writer.writeCharacters(childIndent);
        writer.writeEndElement();
      }
      writer.writeCharacters(indent);
      writer.writeEndElement();
    } else if (order instanceof GAD_DrawingOrder.GSCH_SetCharacterShear gsch) {
      writer.writeStartElement("GSCH_SetCharacterShear");
      writeElement(writer, childIndent, "lengthOfFollowingData", gsch.lengthOfFollowingData);
      writeElement(writer, childIndent, "dividendOfShearRatio", gsch.getDividendOfShearRatio());
      writeElement(writer, childIndent, "divisorOfShearRatio", gsch.getDivisorOfShearRatio());
      writer.writeCharacters(indent);
      writer.writeEndElement();
    } else if (order instanceof GAD_DrawingOrder.GSMC_SetMarkerCell gsmc) {
      writer.writeStartElement("GSMC_SetMarkerCell");
      writeElement(writer, childIndent, "lengthOfFollowingData", gsmc.lengthOfFollowingData);
      writeElement(writer, childIndent, "widthOfMarkerCell", gsmc.getWidthOfMarkerCell());
      writeElement(writer, childIndent, "heightOfMarkerCell", gsmc.getHeightOfMarkerCell());
      writer.writeCharacters(indent);
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
      writeElement(writer, childIndent, "lengthOfFollowingData", gsclt.lengthOfFollowingData);
      if (gsclt.repeatingGroups != null) {
        writer.writeCharacters(childIndent);
        writer.writeStartElement("repeatingGroups");
        String rgIndent = childIndent + "  ";
        for (GAD_DrawingOrder.GSCLT_SetCustomLineType.DashMoveRepeatingGroup rg : gsclt.repeatingGroups) {
          writer.writeCharacters(rgIndent);
          writer.writeStartElement("DashMoveRepeatingGroup");
          writeElement(writer, rgIndent + "  ", "dashInteger", rg.dashInteger());
          writeElement(writer, rgIndent + "  ", "dashFractional", rg.dashFractional());
          writeElement(writer, rgIndent + "  ", "moveInteger", rg.moveInteger());
          writeElement(writer, rgIndent + "  ", "moveFractional", rg.moveFractional());
          writer.writeCharacters(rgIndent);
          writer.writeEndElement();
        }
        writer.writeCharacters(childIndent);
        writer.writeEndElement();
      }
      writer.writeCharacters(indent);
      writer.writeEndElement();
      MnemonicPerformanceMonitor.endWrite();
    } else if (order instanceof GAD_DrawingOrder.GSAP_SetArcParameters gsap) {
      writer.writeStartElement("GSAP_SetArcParameters");
      writeElement(writer, childIndent, "lengthOfFollowingData", gsap.lengthOfFollowingData);
      writeElement(writer, childIndent, "arcTransformP", gsap.getArcTransformP());
      writeElement(writer, childIndent, "arcTransformQ", gsap.getArcTransformQ());
      writeElement(writer, childIndent, "arcTransformR", gsap.getArcTransformR());
      writeElement(writer, childIndent, "arcTransformS", gsap.getArcTransformS());
      writer.writeCharacters(indent);
      writer.writeEndElement();
    } else if (order instanceof GAD_DrawingOrder.GSPRP_SetPatternReferencePoint gsprp) {
      writer.writeStartElement("GSPRP_SetPatternReferencePoint");
      writeElement(writer, childIndent, "lengthOfFollowingData", gsprp.lengthOfFollowingData);
      writeElement(writer, childIndent, "flags", gsprp.getFlags());
      writeElement(writer, childIndent, "reserved3", gsprp.getReserved3());
      writeElement(writer, childIndent, "coordinateX", gsprp.getCoordinateX());
      writeElement(writer, childIndent, "coordinateY", gsprp.getCoordinateY());
      writer.writeCharacters(indent);
      writer.writeEndElement();
    } else if (order instanceof GAD_DrawingOrder.GCPARC_PartialArcAtCurrentPosition gcparc) {
      writer.writeStartElement("GCPARC_PartialArcAtCurrentPosition");
      writeElement(writer, childIndent, "lengthOfFollowingData", gcparc.lengthOfFollowingData);
      if (gcparc.getArcCenter() != null) {
        writer.writeCharacters(childIndent);
        writer.writeStartElement("arcCenter");
        writeElement(writer, childIndent + "  ", "xCoordinate", gcparc.getArcCenter().xCoordinate());
        writeElement(writer, childIndent + "  ", "yCoordinate", gcparc.getArcCenter().yCoordinate());
        writer.writeCharacters(childIndent);
        writer.writeEndElement();
      }
      writeElement(writer, childIndent, "multiplierIntegerPortion", gcparc.getMultiplierIntegerPortion());
      writeElement(writer, childIndent, "multiplierFractionalPortion", gcparc.getMultiplierFractionalPortion());
      writeElement(writer, childIndent, "startAngle", gcparc.getStartAngle());
      writeElement(writer, childIndent, "sweepAngle", gcparc.getSweepAngle());
      writer.writeCharacters(indent);
      writer.writeEndElement();
    } else if (order instanceof GAD_DrawingOrder.GSPCOL_SetProcessColor gspcol) {
      MnemonicPerformanceMonitor.startWriteWithMnemonic("GSPCOL");
      writer.writeStartElement("GSPCOL_SetProcessColor");
      writeElement(writer, childIndent, "lengthOfFollowingData", gspcol.lengthOfFollowingData);
      writeElement(writer, childIndent, "reserved2", gspcol.getReserved2());
      if (gspcol.getColorSpace() != null) {
        writeElement(writer, childIndent, "colorSpace", gspcol.getColorSpace().name());
      }
      writeElement(writer, childIndent, "reserved4_7", gspcol.getReserved4_7());
      writeElement(writer, childIndent, "nrOfBitsComponent1", gspcol.getNrOfBitsComponent1());
      writeElement(writer, childIndent, "nrOfBitsComponent2", gspcol.getNrOfBitsComponent2());
      writeElement(writer, childIndent, "nrOfBitsComponent3", gspcol.getNrOfBitsComponent3());
      writeElement(writer, childIndent, "nrOfBitsComponent4", gspcol.getNrOfBitsComponent4());
      if (gspcol.getColorValue() != null) {
        writeBinaryElement(writer, childIndent, "colorValue", gspcol.getColorValue());
      }
      writer.writeCharacters(indent);
      writer.writeEndElement();
      MnemonicPerformanceMonitor.endWrite();
    } else if (order instanceof GAD_DrawingOrder.GFARC_FullArcAtGivenPosition gfarc) {
      writer.writeStartElement("GFARC_FullArcAtGivenPosition");
      writeElement(writer, childIndent, "lengthOfFollowingData", gfarc.lengthOfFollowingData);
      if (gfarc.getArcCenter() != null) {
        writer.writeCharacters(childIndent);
        writer.writeStartElement("arcCenter");
        writeElement(writer, childIndent + "  ", "xCoordinate", gfarc.getArcCenter().xCoordinate());
        writeElement(writer, childIndent + "  ", "yCoordinate", gfarc.getArcCenter().yCoordinate());
        writer.writeCharacters(childIndent);
        writer.writeEndElement();
      }
      writeElement(writer, childIndent, "multiplierIntegerPortion", gfarc.getMultiplierIntegerPortion());
      writeElement(writer, childIndent, "multiplierFractionalPortion", gfarc.getMultiplierFractionalPortion());
      writer.writeCharacters(indent);
      writer.writeEndElement();
    } else if (order instanceof GAD_DrawingOrder.GCBIMG_BeginImageAtCurrentPosition gcbimg) {
      writer.writeStartElement("GCBIMG_BeginImageAtCurrentPosition");
      writeElement(writer, childIndent, "lengthOfFollowingData", gcbimg.lengthOfFollowingData);
      writeElement(writer, childIndent, "formatOfImageData", gcbimg.getFormatOfImageData());
      writeElement(writer, childIndent, "reserved3", gcbimg.getReserved3());
      writeElement(writer, childIndent, "widthOfImageInImagePoints", gcbimg.getWidthOfImageInImagePoints());
      writeElement(writer, childIndent, "heightOfImageInImagePoints", gcbimg.getHeightOfImageInImagePoints());
      writer.writeCharacters(indent);
      writer.writeEndElement();
    } else if (order instanceof GAD_DrawingOrder.GBIMG_BeginImageAtGivenPosition gbimg) {
      writer.writeStartElement("GBIMG_BeginImageAtGivenPosition");
      writeElement(writer, childIndent, "lengthOfFollowingData", gbimg.lengthOfFollowingData);
      if (gbimg.getOrigin() != null) {
        writer.writeCharacters(childIndent);
        writer.writeStartElement("origin");
        writeElement(writer, childIndent + "  ", "xCoordinate", gbimg.getOrigin().xCoordinate());
        writeElement(writer, childIndent + "  ", "yCoordinate", gbimg.getOrigin().yCoordinate());
        writer.writeCharacters(childIndent);
        writer.writeEndElement();
      }
      writeElement(writer, childIndent, "formatOfImageData", gbimg.getFormatOfImageData());
      writeElement(writer, childIndent, "reserved3", gbimg.getReserved3());
      writeElement(writer, childIndent, "widthOfImageInImagePoints", gbimg.getWidthOfImageInImagePoints());
      writeElement(writer, childIndent, "heightOfImageInImagePoints", gbimg.getHeightOfImageInImagePoints());
      writer.writeCharacters(indent);
      writer.writeEndElement();
    } else if (order instanceof GAD_DrawingOrder.GPARC_PartialArcAtGivenPosition gparc) {
      writer.writeStartElement("GPARC_PartialArcAtGivenPosition");
      writeElement(writer, childIndent, "lengthOfFollowingData", gparc.lengthOfFollowingData);
      if (gparc.getLineStartPoint() != null) {
        writer.writeCharacters(childIndent);
        writer.writeStartElement("lineStartPoint");
        writeElement(writer, childIndent + "  ", "xCoordinate", gparc.getLineStartPoint().xCoordinate());
        writeElement(writer, childIndent + "  ", "yCoordinate", gparc.getLineStartPoint().yCoordinate());
        writer.writeCharacters(childIndent);
        writer.writeEndElement();
      }
      if (gparc.getArcCenter() != null) {
        writer.writeCharacters(childIndent);
        writer.writeStartElement("arcCenter");
        writeElement(writer, childIndent + "  ", "xCoordinate", gparc.getArcCenter().xCoordinate());
        writeElement(writer, childIndent + "  ", "yCoordinate", gparc.getArcCenter().yCoordinate());
        writer.writeCharacters(childIndent);
        writer.writeEndElement();
      }
      writeElement(writer, childIndent, "multiplierIntegerPortion", gparc.getMultiplierIntegerPortion());
      writeElement(writer, childIndent, "multiplierFractionalPortion", gparc.getMultiplierFractionalPortion());
      writeElement(writer, childIndent, "startAngle", gparc.getStartAngle());
      writeElement(writer, childIndent, "sweepAngle", gparc.getSweepAngle());
      writer.writeCharacters(indent);
      writer.writeEndElement();
    } else if (order instanceof GAD_DrawingOrder.GEXO_ExtendedOrder gexo) {
      writer.writeStartElement("GEXO_ExtendedOrder");
      writeElement(writer, childIndent, "qualifier", gexo.qualifier);
      writeElement(writer, childIndent, "lengthOfFollowingData", gexo.lengthOfFollowingData);
      if (gexo.getExtendedData() != null) {
        writeBinaryElement(writer, childIndent, "extendedData", gexo.getExtendedData());
      }
      writer.writeCharacters(indent);
      writer.writeEndElement();
    } else if (order instanceof GAD_DrawingOrder.GCCHST_CharacterStringAtCurrentPosition gcchst) {
      writer.writeStartElement("GCCHST_CharacterStringAtCurrentPosition");
      writeElement(writer, childIndent, "text", gcchst.getText());
      writer.writeCharacters(indent);
      writer.writeEndElement();
    } else if (order instanceof GAD_DrawingOrder.GCHST_CharacterStringAtGivenPosition gchst) {
      writer.writeStartElement("GCHST_CharacterStringAtGivenPosition");
      if (gchst.getOriginPoint() != null) {
        writer.writeCharacters(childIndent);
        writer.writeStartElement("originPoint");
        writeElement(writer, childIndent + "  ", "xCoordinate", gchst.getOriginPoint().xCoordinate());
        writeElement(writer, childIndent + "  ", "yCoordinate", gchst.getOriginPoint().yCoordinate());
        writer.writeCharacters(childIndent);
        writer.writeEndElement();
      }
      writeElement(writer, childIndent, "text", gchst.getText());
      writer.writeCharacters(indent);
      writer.writeEndElement();
    } else if (order instanceof GAD_DrawingOrder.GESEG_EndSegment) {
      writer.writeEmptyElement("GESEG_EndSegment");
    } else if (order instanceof GAD_DrawingOrder.GEPROL_EndProlog geprol) {
      MnemonicPerformanceMonitor.startWriteWithMnemonic("GEPROL");
      writer.writeEmptyElement("GEPROL_EndProlog");
      writer.writeAttribute("reserved0", String.valueOf(geprol.getReserved0()));
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
          writer.writeCharacters(childIndent);
          writeDrawingOrderDirectly(writer, childOrder, childIndent);
        }
      }
      writer.writeCharacters(indent);
      writer.writeEndElement();
      MnemonicPerformanceMonitor.endWrite();
    } else if (order instanceof GAD_DrawingOrder.GBCP_BeginCustomPattern gbcp) {
      MnemonicPerformanceMonitor.startWriteWithMnemonic("GBCP");
      writer.writeStartElement("GBCP_BeginCustomPattern");
      writeElement(writer, childIndent, "lengthOfFollowingData", gbcp.lengthOfFollowingData);
      writeElement(writer, childIndent, "reserved2_3", gbcp.reserved2_3);
      writeElement(writer, childIndent, "flags", gbcp.flags);
      writeElement(writer, childIndent, "patternSet", gbcp.patternSet);
      writeElement(writer, childIndent, "patternSymbol", gbcp.patternSymbol);
      writeElement(writer, childIndent, "xLeftWindow", gbcp.xLeftWindow);
      writeElement(writer, childIndent, "xRightWindow", gbcp.xRightWindow);
      writeElement(writer, childIndent, "yBottomWindow", gbcp.yBottomWindow);
      writeElement(writer, childIndent, "yTopWindow", gbcp.yTopWindow);
      writer.writeCharacters(indent);
      writer.writeEndElement();
      MnemonicPerformanceMonitor.endWrite();
    } else if (order instanceof GAD_DrawingOrder.GDPT_DeletePattern gdpt) {
      MnemonicPerformanceMonitor.startWriteWithMnemonic("GDPT");
      writer.writeStartElement("GDPT_DeletePattern");
      writeElement(writer, childIndent, "lengthOfFollowingData", gdpt.lengthOfFollowingData);
      writeElement(writer, childIndent, "reserved2_3", gdpt.reserved2_3);
      writeElement(writer, childIndent, "patternSet", gdpt.patternSet);
      if (gdpt.patternSymbol != null) {
        writeElement(writer, childIndent, "patternSymbol", gdpt.patternSymbol);
      }
      writer.writeCharacters(indent);
      writer.writeEndElement();
      MnemonicPerformanceMonitor.endWrite();
    } else if (order instanceof GAD_DrawingOrder.GECP_EndCustomPattern gecp) {
      MnemonicPerformanceMonitor.startWriteWithMnemonic("GECP");
      writer.writeEmptyElement("GECP_EndCustomPattern");
      writer.writeAttribute("reserved0", String.valueOf(gecp.getReserved0()));
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
        writeBinaryElement(writer, childIndent, "imageData", gimd.getImageData());
      }
      writer.writeCharacters(indent);
      writer.writeEndElement();
    } else if (order instanceof GAD_DrawingOrder.GEIMG_EndImage geimg) {
      MnemonicPerformanceMonitor.startWriteWithMnemonic("GEIMG");
      writer.writeStartElement("GEIMG_EndImage");
      writeElement(writer, childIndent, "lengthOfFollowingData", geimg.getLengthOfFollowingData());
      if (geimg.reservedData != null) {
        writeBinaryElement(writer, childIndent, "reservedData", geimg.reservedData);
      }
      writer.writeCharacters(indent);
      writer.writeEndElement();
      MnemonicPerformanceMonitor.endWrite();
    } else if (order instanceof GAD_DrawingOrder.GCBOX_BoxAtCurrentPosition gcbox) {
      writer.writeStartElement("GCBOX_BoxAtCurrentPosition");
      writeElement(writer, childIndent, "reserved2_3", gcbox.getReserved2_3());
      if (gcbox.getDiagonalCorner() != null) {
        writer.writeCharacters(childIndent);
        writer.writeStartElement("diagonalCorner");
        writeElement(writer, childIndent + "  ", "xCoordinate", gcbox.getDiagonalCorner().xCoordinate());
        writeElement(writer, childIndent + "  ", "yCoordinate", gcbox.getDiagonalCorner().yCoordinate());
        writer.writeCharacters(childIndent);
        writer.writeEndElement();
      }
      if (gcbox.getxAxisLengthForRoundCorner() != null) {
        writeElement(writer, childIndent, "xAxisLengthForRoundCorner", gcbox.getxAxisLengthForRoundCorner());
      }
      if (gcbox.getyAxisLengthForRoundCorner() != null) {
        writeElement(writer, childIndent, "yAxisLengthForRoundCorner", gcbox.getyAxisLengthForRoundCorner());
      }
      writer.writeCharacters(indent);
      writer.writeEndElement();
    } else if (order instanceof GAD_DrawingOrder.GBOX_BoxAtGivenPosition gbox) {
      writer.writeStartElement("GBOX_BoxAtGivenPosition");
      writeElement(writer, childIndent, "reserved2_3", gbox.getReserved2_3());
      if (gbox.getFirstCorner() != null) {
        writer.writeCharacters(childIndent);
        writer.writeStartElement("firstCorner");
        writeElement(writer, childIndent + "  ", "xCoordinate", gbox.getFirstCorner().xCoordinate());
        writeElement(writer, childIndent + "  ", "yCoordinate", gbox.getFirstCorner().yCoordinate());
        writer.writeCharacters(childIndent);
        writer.writeEndElement();
      }
      if (gbox.getDiagonalCorner() != null) {
        writer.writeCharacters(childIndent);
        writer.writeStartElement("diagonalCorner");
        writeElement(writer, childIndent + "  ", "xCoordinate", gbox.getDiagonalCorner().xCoordinate());
        writeElement(writer, childIndent + "  ", "yCoordinate", gbox.getDiagonalCorner().yCoordinate());
        writer.writeCharacters(childIndent);
        writer.writeEndElement();
      }
      if (gbox.getxAxisLengthForRoundCorner() != null) {
        writeElement(writer, childIndent, "xAxisLengthForRoundCorner", gbox.getxAxisLengthForRoundCorner());
      }
      if (gbox.getyAxisLengthForRoundCorner() != null) {
        writeElement(writer, childIndent, "yAxisLengthForRoundCorner", gbox.getyAxisLengthForRoundCorner());
      }
      writer.writeCharacters(indent);
      writer.writeEndElement();
    } else if (order instanceof GAD_DrawingOrder.GCRLINE_RelativeLineAtCurrentPosition gcrline) {
      MnemonicPerformanceMonitor.startWriteWithMnemonic("GCRLINE");
      writer.writeStartElement("GCRLINE_RelativeLineAtCurrentPosition");
      if (gcrline.relativeOffsets != null) {
        writer.writeCharacters(childIndent);
        writer.writeStartElement("relativeOffsets");
        String offsetIndent = childIndent + "  ";
        for (GAD_DrawingOrder.GOCA_RelativePoint rp : gcrline.relativeOffsets) {
          writer.writeCharacters(offsetIndent);
          writer.writeStartElement("GOCA_RelativePoint");
          writeElement(writer, offsetIndent + "  ", "xOffset", rp.xOffset());
          writeElement(writer, offsetIndent + "  ", "yOffset", rp.yOffset());
          writer.writeCharacters(offsetIndent);
          writer.writeEndElement();
        }
        writer.writeCharacters(childIndent);
        writer.writeEndElement();
      }
      writer.writeCharacters(indent);
      writer.writeEndElement();
      MnemonicPerformanceMonitor.endWrite();
    } else if (order instanceof GAD_DrawingOrder.GRLINE_RelativeLineAtGivenPosition grline) {
      MnemonicPerformanceMonitor.startWriteWithMnemonic("GRLINE");
      writer.writeStartElement("GRLINE_RelativeLineAtGivenPosition");
      if (grline.startPoint != null) {
        writer.writeCharacters(childIndent);
        writer.writeStartElement("startPoint");
        writeElement(writer, childIndent + "  ", "xCoordinate", grline.startPoint.xCoordinate());
        writeElement(writer, childIndent + "  ", "yCoordinate", grline.startPoint.yCoordinate());
        writer.writeCharacters(childIndent);
        writer.writeEndElement();
      }
      if (grline.relativeOffsets != null) {
        writer.writeCharacters(childIndent);
        writer.writeStartElement("relativeOffsets");
        String offsetIndent = childIndent + "  ";
        for (GAD_DrawingOrder.GOCA_RelativePoint rp : grline.relativeOffsets) {
          writer.writeCharacters(offsetIndent);
          writer.writeStartElement("GOCA_RelativePoint");
          writeElement(writer, offsetIndent + "  ", "xOffset", rp.xOffset());
          writeElement(writer, offsetIndent + "  ", "yOffset", rp.yOffset());
          writer.writeCharacters(offsetIndent);
          writer.writeEndElement();
        }
        writer.writeCharacters(childIndent);
        writer.writeEndElement();
      }
      writer.writeCharacters(indent);
      writer.writeEndElement();
      MnemonicPerformanceMonitor.endWrite();
    } else if (order instanceof GAD_DrawingOrder.GLGD_LinearGradient glgd) {
      MnemonicPerformanceMonitor.startWriteWithMnemonic("GLGD");
      writer.writeStartElement("GLGD_LinearGradient");
      writeElement(writer, childIndent, "qualifier", glgd.qualifier);
      writeElement(writer, childIndent, "lengthOfFollowingData", glgd.lengthOfFollowingData);
      writeElement(writer, childIndent, "reserved4_5", glgd.reserved4_5);
      writeElement(writer, childIndent, "patternSet", glgd.patternSet);
      writeElement(writer, childIndent, "patternSymbol", glgd.patternSymbol);
      writeElement(writer, childIndent, "xStart", glgd.xStart);
      writeElement(writer, childIndent, "yStart", glgd.yStart);
      writeElement(writer, childIndent, "xEnd", glgd.xEnd);
      writeElement(writer, childIndent, "yEnd", glgd.yEnd);
      if (glgd.startColorSpec != null) {
        writer.writeCharacters(childIndent);
        writeColorSpecification(writer, glgd.startColorSpec, childIndent, "startColorSpec");
      }
      if (glgd.endColorValue != null) {
        writeBinaryElement(writer, childIndent, "endColorValue", glgd.endColorValue);
      }
      writeElement(writer, childIndent, "outsideStart", glgd.outsideStart);
      writeElement(writer, childIndent, "outsideEnd", glgd.outsideEnd);
      if (glgd.colorStops != null) {
        writer.writeCharacters(childIndent);
        writer.writeStartElement("colorStops");
        for (GAD_DrawingOrder.ColorStop stop : glgd.colorStops) {
          writeColorStop(writer, stop, childIndent + "  ");
        }
        writer.writeCharacters(childIndent);
        writer.writeEndElement();
      }
      writer.writeCharacters(indent);
      writer.writeEndElement();
      MnemonicPerformanceMonitor.endWrite();
    } else if (order instanceof GAD_DrawingOrder.GRGD_RadialGradient grgd) {
      MnemonicPerformanceMonitor.startWriteWithMnemonic("GRGD");
      writer.writeStartElement("GRGD_RadialGradient");
      writeElement(writer, childIndent, "qualifier", grgd.qualifier);
      writeElement(writer, childIndent, "lengthOfFollowingData", grgd.lengthOfFollowingData);
      writeElement(writer, childIndent, "reserved4_5", grgd.reserved4_5);
      writeElement(writer, childIndent, "patternSet", grgd.patternSet);
      writeElement(writer, childIndent, "patternSymbol", grgd.patternSymbol);
      writeElement(writer, childIndent, "xStart", grgd.xStart);
      writeElement(writer, childIndent, "yStart", grgd.yStart);
      writeElement(writer, childIndent, "mhStart", grgd.mhStart);
      writeElement(writer, childIndent, "mfrStart", grgd.mfrStart);
      writeElement(writer, childIndent, "xEnd", grgd.xEnd);
      writeElement(writer, childIndent, "yEnd", grgd.yEnd);
      writeElement(writer, childIndent, "mhEnd", grgd.mhEnd);
      writeElement(writer, childIndent, "mfrEnd", grgd.mfrEnd);
      if (grgd.startColorSpec != null) {
        writer.writeCharacters(childIndent);
        writeColorSpecification(writer, grgd.startColorSpec, childIndent, "startColorSpec");
      }
      if (grgd.endColorValue != null) {
        writeBinaryElement(writer, childIndent, "endColorValue", grgd.endColorValue);
      }
      writeElement(writer, childIndent, "outsideStart", grgd.outsideStart);
      writeElement(writer, childIndent, "outsideEnd", grgd.outsideEnd);
      if (grgd.colorStops != null) {
        writer.writeCharacters(childIndent);
        writer.writeStartElement("colorStops");
        for (GAD_DrawingOrder.ColorStop stop : grgd.colorStops) {
          writeColorStop(writer, stop, childIndent + "  ");
        }
        writer.writeCharacters(childIndent);
        writer.writeEndElement();
      }
      writer.writeCharacters(indent);
      writer.writeEndElement();
      MnemonicPerformanceMonitor.endWrite();
    } else if (order instanceof GAD_DrawingOrder.DrawingOrder_HasPoints dohp) {
      String rootName = order.getClass().getSimpleName();
      MnemonicPerformanceMonitor.startWriteWithMnemonic(MnemonicPerformanceMonitor.extractMnemonicFromString(rootName));
      writer.writeStartElement(rootName);
      if (dohp.getPoints() != null) {
        writer.writeCharacters(childIndent);
        writer.writeStartElement("points");
        String pointIndent = childIndent + "  ";
        for (GAD_DrawingOrder.GOCA_Point p : dohp.getPoints()) {
          writer.writeCharacters(pointIndent);
          writer.writeStartElement("GOCA_Point");
          writeElement(writer, pointIndent + "  ", "xCoordinate", p.xCoordinate());
          writeElement(writer, pointIndent + "  ", "yCoordinate", p.yCoordinate());
          writer.writeCharacters(pointIndent);
          writer.writeEndElement();
        }
        writer.writeCharacters(childIndent);
        writer.writeEndElement();
      }
      writer.writeCharacters(indent);
      writer.writeEndElement();
      MnemonicPerformanceMonitor.endWrite();
    } else {
      String rootName = order.getClass().getSimpleName();
      String mnemonic = MnemonicPerformanceMonitor.extractMnemonicFromString(rootName);
      MnemonicPerformanceMonitor.startWriteWithMnemonic(mnemonic);
      JacksonXmlMapperProvider.getCachedWriter(order.getClass(), useWoodstox, true).writeValue(baseFragmentGenerator, order);
      MnemonicPerformanceMonitor.endWrite();
    }
  }

  private void writeIpdSegmentDirectly(XMLStreamWriter2 writer, IPD_Segment segment, String indent) throws Exception {
    String childIndent = indent + "  ";
    if (segment instanceof IPD_Segment.ImageData id) {
      writer.writeStartElement("ImageData");
      if (id.getImageData() != null) {
        writeBinaryElement(writer, childIndent, "imageData", id.getImageData());
      }
      writer.writeCharacters(indent);
      writer.writeEndElement();
    } else if (segment instanceof IPD_Segment.BandImageData bid) {
      writer.writeStartElement("BandImageData");
      writeElement(writer, childIndent, "bandNumber", bid.getBandNumber());
      if (bid.getBandData() != null) {
        writeBinaryElement(writer, childIndent, "bandData", bid.getBandData());
      }
      writer.writeCharacters(indent);
      writer.writeEndElement();
    } else if (segment instanceof IPD_Segment.BeginImageContent bic) {
      writer.writeStartElement("BeginImageContent");
      writeElement(writer, childIndent, "objectType", bic.getObjectType());
      writer.writeCharacters(indent);
      writer.writeEndElement();
    } else if (segment instanceof IPD_Segment.EndImageContent) {
      writer.writeEmptyElement("EndImageContent");
    } else if (segment instanceof IPD_Segment.ImageSize is) {
      writer.writeStartElement("ImageSize");
      if (is.getUnitBase() != null) {
        writeElement(writer, childIndent, "unitBase", is.getUnitBase().name());
      }
      writeElement(writer, childIndent, "xUnitsPerUnitBase", is.getxUnitsPerUnitBase());
      writeElement(writer, childIndent, "yUnitsPerUnitBase", is.getyUnitsPerUnitBase());
      writeElement(writer, childIndent, "xImageSize", is.getxImageSize());
      writeElement(writer, childIndent, "yImageSize", is.getyImageSize());
      writer.writeCharacters(indent);
      writer.writeEndElement();
    } else if (segment instanceof IPD_Segment.ImageEncoding ie) {
      writer.writeStartElement("ImageEncoding");
      if (ie.getCompressionAlgorithm() != null) {
        writeElement(writer, childIndent, "compressionAlgorithm", ie.getCompressionAlgorithm().name());
      }
      if (ie.getRecordingAlgorithm() != null) {
        writeElement(writer, childIndent, "recordingAlgorithm", ie.getRecordingAlgorithm().name());
      }
      if (ie.getBitOrder() != null) {
        writeElement(writer, childIndent, "bitOrder", ie.getBitOrder().name());
      }
      writer.writeCharacters(indent);
      writer.writeEndElement();
    } else {
      JacksonXmlMapperProvider.getCachedWriter(segment.getClass(), useWoodstox, true).writeValue(baseFragmentGenerator, segment);
    }
  }

  private void writeIpdDirectly(IPD_ImagePictureData ipd) throws Exception {
    MnemonicPerformanceMonitor.startWriteWithMnemonic("IPD");
    baseXsw.writeStartElement("IPD_ImagePictureData");
    String indent = XmlIndenter.getIndent(2);
    List<IPD_Segment> segments = ipd.getListOfSegments();
    if (segments != null) {
      for (IPD_Segment segment : segments) {
        baseXsw.writeCharacters(indent);
        writeIpdSegmentDirectly(baseXsw, segment, indent);
      }
    }
    XmlIndenter.writeIndent(baseXsw, 1);
    baseXsw.writeEndElement();
    MnemonicPerformanceMonitor.endWrite();
  }

  private void writeObdDirectly(OBD_ObjectAreaDescriptor obd) throws Exception {
    MnemonicPerformanceMonitor.startWriteWithMnemonic("OBD");
    baseXsw.writeStartElement("OBD_ObjectAreaDescriptor");
    String indent = XmlIndenter.getIndent(2);
    if (obd.getTriplets() != null) {
      for (Triplet triplet : obd.getTriplets()) {
        baseXsw.writeCharacters(indent);
        writeTriplet(baseXsw, triplet, indent);
      }
    }
    XmlIndenter.writeIndent(baseXsw, 1);
    baseXsw.writeEndElement();
    MnemonicPerformanceMonitor.endWrite();
  }

  private void writeObpDirectly(OBP_ObjectAreaPosition obp) throws Exception {
    MnemonicPerformanceMonitor.startWriteWithMnemonic("OBP");
    baseXsw.writeStartElement("OBP_ObjectAreaPosition");
    String indent = XmlIndenter.getIndent(2);
    writeElement(baseXsw, indent, "objectAreaPositionID", obp.getObjectAreaPositionID());
    if (obp.getRepeatingGroup() != null) {
      baseXsw.writeCharacters(indent);
      baseXsw.writeStartElement("repeatingGroup");
      String childIndent = XmlIndenter.getIndent(3);
      OBP_ObjectAreaPosition.OBP_RepeatingGroup rg = obp.getRepeatingGroup();
      writeElement(baseXsw, childIndent, "repeatingGroupLength", rg.getRepeatingGroupLength());
      writeElement(baseXsw, childIndent, "xOrigin", rg.getxOrigin());
      writeElement(baseXsw, childIndent, "yOrigin", rg.getyOrigin());
      if (rg.getxRotation() != null) {
        writeElement(baseXsw, childIndent, "xRotation", rg.getxRotation().name());
      }
      if (rg.getyRotation() != null) {
        writeElement(baseXsw, childIndent, "yRotation", rg.getyRotation().name());
      }
      writeElement(baseXsw, childIndent, "reserved11", rg.getReserved11());
      writeElement(baseXsw, childIndent, "xOriginOfContent", rg.getxOriginOfContent());
      writeElement(baseXsw, childIndent, "yOriginOfContent", rg.getyOriginOfContent());
      if (rg.getxRotationOfContent() != null) {
        writeElement(baseXsw, childIndent, "xRotationOfContent", rg.getxRotationOfContent().name());
      }
      if (rg.getyRotationOfContent() != null) {
        writeElement(baseXsw, childIndent, "yRotationOfContent", rg.getyRotationOfContent().name());
      }
      if (rg.getReferenceCoordinateSystem() != null) {
        writeElement(baseXsw, childIndent, "referenceCoordinateSystem", rg.getReferenceCoordinateSystem().name());
      }
      baseXsw.writeCharacters(indent);
      baseXsw.writeEndElement();
    }
    XmlIndenter.writeIndent(baseXsw, 1);
    baseXsw.writeEndElement();
    MnemonicPerformanceMonitor.endWrite();
  }

  private void writeIddSelfDefiningFieldDirectly(XMLStreamWriter2 writer, IDD_SelfDefiningField sdf, String indent) throws Exception {
    String childIndent = indent + "  ";
    if (sdf instanceof IDD_SelfDefiningField.SetBilevelImageColor sbic) {
      writer.writeStartElement("SetBilevelImageColor");
      writeElement(writer, childIndent, "applicabilityArea", sbic.getApplicabilityArea());
      if (sbic.getColor() != null) {
        writeElement(writer, childIndent, "color", sbic.getColor().name());
      }
      writer.writeCharacters(indent);
      writer.writeEndElement();
    } else if (sdf instanceof IDD_SelfDefiningField.SetExtendedBilevelImageColor sebic) {
      writer.writeStartElement("SetExtendedBilevelImageColor");
      if (sebic.getColorSpace() != null) {
        writeElement(writer, childIndent, "colorSpace", sebic.getColorSpace().name());
      }
      writeElement(writer, childIndent, "nrOfBitsComponent1", sebic.getNrOfBitsComponent1());
      writeElement(writer, childIndent, "nrOfBitsComponent2", sebic.getNrOfBitsComponent2());
      writeElement(writer, childIndent, "nrOfBitsComponent3", sebic.getNrOfBitsComponent3());
      writeElement(writer, childIndent, "nrOfBitsComponent4", sebic.getNrOfBitsComponent4());
      if (sebic.getColorValue() != null) {
        writeBinaryElement(writer, childIndent, "colorValue", sebic.getColorValue());
      }
      writer.writeCharacters(indent);
      writer.writeEndElement();
    } else if (sdf instanceof IDD_SelfDefiningField.IOCAFunctionSetIdentification fsi) {
      writer.writeStartElement("IOCAFunctionSetIdentification");
      writeElement(writer, childIndent, "functionSetCategory", fsi.getFunctionSetCategory());
      if (fsi.getFunctionSetIdentifier() != null) {
        writeElement(writer, childIndent, "functionSetIdentifier", fsi.getFunctionSetIdentifier().name());
      }
      writer.writeCharacters(indent);
      writer.writeEndElement();
    } else {
      JacksonXmlMapperProvider.getCachedWriter(sdf.getClass(), useWoodstox, true).writeValue(baseFragmentGenerator, sdf);
    }
  }

  private void writeIddDirectly(IDD_ImageDataDescriptor idd) throws Exception {
    MnemonicPerformanceMonitor.startWriteWithMnemonic("IDD");
    baseXsw.writeStartElement("IDD_ImageDataDescriptor");
    String indent = XmlIndenter.getIndent(2);
    if (idd.getUnitBase() != null) {
      writeElement(baseXsw, indent, "unitBase", idd.getUnitBase().name());
    }
    writeElement(baseXsw, indent, "xImagePointsPerUnitBase", idd.getxImagePointsPerUnitBase());
    writeElement(baseXsw, indent, "yImagePointsPerUnitBase", idd.getyImagePointsPerUnitBase());
    writeElement(baseXsw, indent, "widthOfImageInImagePoints", idd.getWidthOfImageInImagePoints());
    writeElement(baseXsw, indent, "heightOfImageInImagePoints", idd.getHeightOfImageInImagePoints());

    if (idd.getSelfDefiningFields() != null) {
      for (IDD_SelfDefiningField sdf : idd.getSelfDefiningFields()) {
        baseXsw.writeCharacters(indent);
        writeIddSelfDefiningFieldDirectly(baseXsw, sdf, indent);
      }
    }
    XmlIndenter.writeIndent(baseXsw, 1);
    baseXsw.writeEndElement();
    MnemonicPerformanceMonitor.endWrite();
  }

  private void writeMioDirectly(MIO_MapImageObject mio) throws Exception {
    MnemonicPerformanceMonitor.startWriteWithMnemonic("MIO");
    baseXsw.writeStartElement("MIO_MapImageObject");
    String indent = XmlIndenter.getIndent(2);
    if (mio.getRepeatingGroups() != null) {
      for (IRepeatingGroup rg : mio.getRepeatingGroups()) {
        baseXsw.writeCharacters(indent);
        baseXsw.writeStartElement("mioRepeatingGroup");
        if (rg instanceof RepeatingGroupWithTriplets rgt) {
          if (rgt.getTriplets() != null) {
            for (Triplet t : rgt.getTriplets()) {
              baseXsw.writeCharacters(XmlIndenter.getIndent(3));
              writeTriplet(baseXsw, t, XmlIndenter.getIndent(3));
            }
          }
        }
        baseXsw.writeCharacters(indent);
        baseXsw.writeEndElement();
      }
    }
    XmlIndenter.writeIndent(baseXsw, 1);
    baseXsw.writeEndElement();
    MnemonicPerformanceMonitor.endWrite();
  }

  private void writeMdrDirectly(MDR_MapDataResource mdr) throws Exception {
    MnemonicPerformanceMonitor.startWriteWithMnemonic("MDR");
    baseXsw.writeStartElement("MDR_MapDataResource");
    String indent = XmlIndenter.getIndent(2);
    if (mdr.getRepeatingGroups() != null) {
      for (IRepeatingGroup rg : mdr.getRepeatingGroups()) {
        baseXsw.writeCharacters(indent);
        baseXsw.writeStartElement("mdrRepeatingGroup");
        if (rg instanceof RepeatingGroupWithTriplets rgt) {
          if (rgt.getTriplets() != null) {
            for (Triplet t : rgt.getTriplets()) {
              baseXsw.writeCharacters(XmlIndenter.getIndent(3));
              writeTriplet(baseXsw, t, XmlIndenter.getIndent(3));
            }
          }
        }
        baseXsw.writeCharacters(indent);
        baseXsw.writeEndElement();
      }
    }
    XmlIndenter.writeIndent(baseXsw, 1);
    baseXsw.writeEndElement();
    MnemonicPerformanceMonitor.endWrite();
  }

  private void writeMgoDirectly(MGO_MapGraphicsObject mgo) throws Exception {
    MnemonicPerformanceMonitor.startWriteWithMnemonic("MGO");
    baseXsw.writeStartElement("MGO_MapGraphicsObject");
    String indent = XmlIndenter.getIndent(2);
    if (mgo.getRepeatingGroups() != null) {
      for (IRepeatingGroup rg : mgo.getRepeatingGroups()) {
        baseXsw.writeCharacters(indent);
        baseXsw.writeStartElement("mgoRepeatingGroup");
        if (rg instanceof RepeatingGroupWithTriplets rgt) {
          if (rgt.getTriplets() != null) {
            for (Triplet t : rgt.getTriplets()) {
              baseXsw.writeCharacters(XmlIndenter.getIndent(3));
              writeTriplet(baseXsw, t, XmlIndenter.getIndent(3));
            }
          }
        }
        baseXsw.writeCharacters(indent);
        baseXsw.writeEndElement();
      }
    }
    XmlIndenter.writeIndent(baseXsw, 1);
    baseXsw.writeEndElement();
    MnemonicPerformanceMonitor.endWrite();
  }

  private void writeMpoDirectly(MPO_MapPageOverlay mpo) throws Exception {
    MnemonicPerformanceMonitor.startWriteWithMnemonic("MPO");
    baseXsw.writeStartElement("MPO_MapPageOverlay");
    String indent = XmlIndenter.getIndent(2);
    if (mpo.getRepeatingGroups() != null) {
      for (IRepeatingGroup rg : mpo.getRepeatingGroups()) {
        baseXsw.writeCharacters(indent);
        baseXsw.writeStartElement("mpoRepeatingGroup");
        if (rg instanceof RepeatingGroupWithTriplets rgt) {
          if (rgt.getTriplets() != null) {
            for (Triplet t : rgt.getTriplets()) {
              baseXsw.writeCharacters(XmlIndenter.getIndent(3));
              writeTriplet(baseXsw, t, XmlIndenter.getIndent(3));
            }
          }
        }
        baseXsw.writeCharacters(indent);
        baseXsw.writeEndElement();
      }
    }
    XmlIndenter.writeIndent(baseXsw, 1);
    baseXsw.writeEndElement();
    MnemonicPerformanceMonitor.endWrite();
  }

  private void writeBdtDirectly(com.mgz.afp.modca.BDT_BeginDocument bdt) throws Exception {
    MnemonicPerformanceMonitor.startWriteWithMnemonic("BDT");
    baseXsw.writeStartElement("BDT_BeginDocument");
    String indent2 = XmlIndenter.getIndent(2);
    String indent1 = XmlIndenter.getIndent(1);
    writeElement(baseXsw, indent2, "name", bdt.getName());
    if (bdt.getTriplets() != null && !bdt.getTriplets().isEmpty()) {
      baseXsw.writeCharacters(indent2);
      baseXsw.writeStartElement("triplets");
      for (Triplet triplet : bdt.getTriplets()) {
        baseXsw.writeCharacters(XmlIndenter.getIndent(3));
        writeTriplet(baseXsw, triplet, XmlIndenter.getIndent(3));
      }
      baseXsw.writeCharacters(indent2);
      baseXsw.writeEndElement();
    }
    if (bdt.reserved8_9 != null) {
      writeElement(baseXsw, indent2, "reserved8_9", com.mgz.util.UtilCharacterEncoding.bytesToHexString(bdt.reserved8_9));
    }
    if (bdt.getText() != null) {
      writeElement(baseXsw, indent2, "text", bdt.getText());
    }
    baseXsw.writeCharacters(indent1);
    baseXsw.writeEndElement();
    MnemonicPerformanceMonitor.endWrite();
  }

  private void writeBmoDirectly(BMO_BeginOverlay bmo) throws Exception {
    MnemonicPerformanceMonitor.startWriteWithMnemonic("BMO");
    baseXsw.writeStartElement("BMO_BeginOverlay");
    String indent2 = XmlIndenter.getIndent(2);
    String indent1 = XmlIndenter.getIndent(1);
    writeElement(baseXsw, indent2, "name", bmo.getName());
    if (bmo.getTriplets() != null && !bmo.getTriplets().isEmpty()) {
      for (Triplet triplet : bmo.getTriplets()) {
        baseXsw.writeCharacters(indent2);
        writeTriplet(baseXsw, triplet, indent2);
      }
    }
    if (bmo.getText() != null) {
      writeElement(baseXsw, indent2, "text", bmo.getText());
    }
    baseXsw.writeCharacters(indent1);
    baseXsw.writeEndElement();
    MnemonicPerformanceMonitor.endWrite();
  }

  private void writeBpsDirectly(BPS_BeginPageSegment bps) throws Exception {
    MnemonicPerformanceMonitor.startWriteWithMnemonic("BPS");
    baseXsw.writeStartElement("BPS_BeginPageSegment");
    String indent2 = XmlIndenter.getIndent(2);
    String indent1 = XmlIndenter.getIndent(1);
    writeElement(baseXsw, indent2, "name", bps.getName());
    if (bps.getTriplets() != null && !bps.getTriplets().isEmpty()) {
      for (Triplet triplet : bps.getTriplets()) {
        baseXsw.writeCharacters(indent2);
        writeTriplet(baseXsw, triplet, indent2);
      }
    }
    if (bps.getText() != null) {
      writeElement(baseXsw, indent2, "text", bps.getText());
    }
    baseXsw.writeCharacters(indent1);
    baseXsw.writeEndElement();
    MnemonicPerformanceMonitor.endWrite();
  }

  private void writeNameAndTripletsDirectly(com.mgz.afp.base.StructuredFieldBaseNameAndTriplets sf, String rootName) throws Exception {
    String mnemonic = MnemonicPerformanceMonitor.extractMnemonicFromString(rootName);
    MnemonicPerformanceMonitor.startWriteWithMnemonic(mnemonic);
    baseXsw.writeStartElement(rootName);
    String indent2 = XmlIndenter.getIndent(2);
    String indent1 = XmlIndenter.getIndent(1);
    writeElement(baseXsw, indent2, "name", sf.getName());
    if (sf.getTriplets() != null && !sf.getTriplets().isEmpty()) {
      // For SFs extending StructuredFieldBaseNameAndTriplets, Jackson output typically doesn't wrap triplets
      // but they are also often marked XmlTransient and only contribute to text.
      // To maintain legacy manual behavior where they WERE written:
      for (Triplet triplet : sf.getTriplets()) {
        baseXsw.writeCharacters(indent2);
        writeTriplet(baseXsw, triplet, indent2);
      }
    }
    if (sf.getText() != null) {
      writeElement(baseXsw, indent2, "text", sf.getText());
    }
    baseXsw.writeCharacters(indent1);
    baseXsw.writeEndElement();
    MnemonicPerformanceMonitor.endWrite();
  }

  private void writeTripletsAndTextDirectly(com.mgz.afp.base.StructuredFieldBaseTriplets sf, String rootName) throws Exception {
    String mnemonic = MnemonicPerformanceMonitor.extractMnemonicFromString(rootName);
    MnemonicPerformanceMonitor.startWriteWithMnemonic(mnemonic);
    baseXsw.writeStartElement(rootName);
    String indent2 = XmlIndenter.getIndent(2);
    String indent1 = XmlIndenter.getIndent(1);
    if (sf.getTriplets() != null && !sf.getTriplets().isEmpty()) {
      for (Triplet triplet : sf.getTriplets()) {
        baseXsw.writeCharacters(indent2);
        writeTriplet(baseXsw, triplet, indent2);
      }
    }
    if (sf.getText() != null) {
      writeElement(baseXsw, indent2, "text", sf.getText());
    }
    baseXsw.writeCharacters(indent1);
    baseXsw.writeEndElement();
    MnemonicPerformanceMonitor.endWrite();
  }

  private void writeNameDirectly(com.mgz.afp.base.StructuredFieldBaseName sf, String rootName) throws Exception {
    String mnemonic = MnemonicPerformanceMonitor.extractMnemonicFromString(rootName);
    MnemonicPerformanceMonitor.startWriteWithMnemonic(mnemonic);
    baseXsw.writeStartElement(rootName);
    String indent2 = XmlIndenter.getIndent(2);
    String indent1 = XmlIndenter.getIndent(1);
    writeElement(baseXsw, indent2, "name", sf.getName());
    if (sf.getText() != null) {
      writeElement(baseXsw, indent2, "text", sf.getText());
    }
    baseXsw.writeCharacters(indent1);
    baseXsw.writeEndElement();
    MnemonicPerformanceMonitor.endWrite();
  }

  private void writePgdDirectly(com.mgz.afp.modca.PGD_PageDescriptor pgd) throws Exception {
    MnemonicPerformanceMonitor.startWriteWithMnemonic("PGD");
    baseXsw.writeStartElement("PGD_PageDescriptor");
    String indent = XmlIndenter.getIndent(2);
    if (pgd.getxUnitBase() != null) {
      writeElement(baseXsw, indent, "xUnitBase", pgd.getxUnitBase().name());
    }
    if (pgd.getyUnitBase() != null) {
      writeElement(baseXsw, indent, "yUnitBase", pgd.getyUnitBase().name());
    }
    writeElement(baseXsw, indent, "xUnitsPerUnitBase", pgd.getxUnitsPerUnitBase());
    writeElement(baseXsw, indent, "yUnitsPerUnitBase", pgd.getyUnitsPerUnitBase());
    writeElement(baseXsw, indent, "xSize", pgd.getxSize());
    writeElement(baseXsw, indent, "ySize", pgd.getySize());
    if (pgd.getReserved12_14() != null) {
      writeElement(baseXsw, indent, "reserved12_14", UtilCharacterEncoding.bytesToHexString(pgd.getReserved12_14()));
    }
    writeTripletsAndText(baseXsw, pgd.getTriplets(), pgd.getText(), XmlIndenter.getIndent(2), XmlIndenter.getIndent(1));
    baseXsw.writeEndElement();
    MnemonicPerformanceMonitor.endWrite();
  }

  private void writeMddDirectly(com.mgz.afp.modca.MDD_MediumDescriptor mdd) throws Exception {
    MnemonicPerformanceMonitor.startWriteWithMnemonic("MDD");
    baseXsw.writeStartElement("MDD_MediumDescriptor");
    String indent = XmlIndenter.getIndent(2);
    if (mdd.getxUnitBase() != null) {
      writeElement(baseXsw, indent, "xUnitBase", mdd.getxUnitBase().name());
    }
    if (mdd.getyUnitBase() != null) {
      writeElement(baseXsw, indent, "yUnitBase", mdd.getyUnitBase().name());
    }
    writeElement(baseXsw, indent, "xUnitsPerUnitBase", mdd.getxUnitsPerUnitBase());
    writeElement(baseXsw, indent, "yUnitsPerUnitBase", mdd.getyUnitsPerUnitBase());
    writeElement(baseXsw, indent, "xMediumExtent", mdd.getxMediumExtent());
    writeElement(baseXsw, indent, "yMediumExtent", mdd.getyMediumExtent());
    if (mdd.getFlag() != null) {
      writeElement(baseXsw, indent, "flag", mdd.getFlag().name());
    }
    writeTripletsAndText(baseXsw, mdd.getTriplets(), mdd.getText(), XmlIndenter.getIndent(2), XmlIndenter.getIndent(1));
    baseXsw.writeEndElement();
    MnemonicPerformanceMonitor.endWrite();
  }

  private void writeDrawingOrderWithPoints(XMLStreamWriter2 writer, GAD_DrawingOrder.DrawingOrder_HasPoints order, String rootName, String indent, String childIndent) throws Exception {
    writer.writeStartElement(rootName);
    if (order.getPoints() != null) {
      writer.writeCharacters(childIndent);
      writer.writeStartElement("points");
      for (GAD_DrawingOrder.GOCA_Point p : order.getPoints()) {
        writer.writeCharacters(childIndent + "  ");
        writer.writeEmptyElement("GOCA_Point");
        writer.writeAttribute("xCoordinate", String.valueOf(p.xCoordinate()));
        writer.writeAttribute("yCoordinate", String.valueOf(p.yCoordinate()));
      }
      writer.writeCharacters(childIndent);
      writer.writeEndElement();
    }
    writer.writeCharacters(indent);
    writer.writeEndElement();
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

  private void writeBinaryElement(XMLStreamWriter2 writer, String indent, String name, byte[] data) throws Exception {
    if (data != null) {
      writer.writeCharacters(indent);
      writer.writeStartElement(name);
      writer.writeBinary(data, 0, data.length);
      writer.writeEndElement();
    }
  }

  private void writeBinaryElement(String indent, String name, byte[] data) throws Exception {
    writeBinaryElement(xsw, indent, name, data);
  }

  private void writeFieldWithXpath(StructuredField sf) throws Exception {
    if (cachedDocumentBuilder == null) {
      cachedDocumentBuilder = DBF.newDocumentBuilder();
    }

    int initialCapacity = (int) SFSizeEstimator.estimateXmlSize(sf);
    String xml;
    java.io.ByteArrayOutputStream baos = new java.io.ByteArrayOutputStream(initialCapacity);
    try (AfpJacksonXmlWriter tempWriter = new AfpJacksonXmlWriter(baos, null, true, useWoodstox)) {
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
        baseXsw.writeCharacters("\n");
        xsw.writeEndDocument();
      } else {
        xsw.writeEndElement();
      }
      xsw.flush();
      // Always close the writer to ensure that internal buffers are recycled by the factory.
      xsw.close();
    }
  }

  private void writeColorSpecification(XMLStreamWriter2 writer, GAD_DrawingOrder.ColorSpecification cs, String indent, String rootName) throws Exception {
    String childIndent = indent + "  ";
    writer.writeStartElement(rootName);
    writeElement(writer, childIndent, "length", cs.length);
    writeElement(writer, childIndent, "reserved", cs.reserved);
    if (cs.colorSpace != null) {
      writeElement(writer, childIndent, "colorSpace", cs.colorSpace.name());
    }
    writeElement(writer, childIndent, "reserved4_7", cs.reserved4_7);
    writeElement(writer, childIndent, "nrOfBitsComponent1", cs.nrOfBitsComponent1);
    writeElement(writer, childIndent, "nrOfBitsComponent2", cs.nrOfBitsComponent2);
    writeElement(writer, childIndent, "nrOfBitsComponent3", cs.nrOfBitsComponent3);
    writeElement(writer, childIndent, "nrOfBitsComponent4", cs.nrOfBitsComponent4);
    if (cs.colorValue != null) {
      writeBinaryElement(writer, childIndent, "colorValue", cs.colorValue);
    }
    writer.writeCharacters(indent);
    writer.writeEndElement();
  }

  private void writeColorStop(XMLStreamWriter2 writer, GAD_DrawingOrder.ColorStop stop, String indent) throws Exception {
    String childIndent = indent + "  ";
    writer.writeStartElement("ColorStop");
    writeElement(writer, childIndent, "offset", stop.offset);
    if (stop.colorValue != null) {
      writeBinaryElement(writer, childIndent, "colorValue", stop.colorValue);
    }
    writer.writeCharacters(indent);
    writer.writeEndElement();
  }

  private void writeBdaDirectly(BDA_BarCodeData bda) throws Exception {
    MnemonicPerformanceMonitor.startWriteWithMnemonic("BDA");
    baseXsw.writeStartElement("BDA_BarCodeData");
    String indent2 = XmlIndenter.getIndent(2);
    String indent1 = XmlIndenter.getIndent(1);

    if (bda.getBarCodeFlags() != null && !bda.getBarCodeFlags().isEmpty()) {
      baseXsw.writeCharacters(indent2);
      baseXsw.writeStartElement("barCodeFlags");
      for (BDA_BarCodeData.BarCodeFlag flag : bda.getBarCodeFlags()) {
        writeElement(baseXsw, indent2 + "  ", "barCodeFlag", flag.name());
      }
      baseXsw.writeCharacters(indent2);
      baseXsw.writeEndElement();
    }
    writeElement(baseXsw, indent2, "xOffset", bda.getxOffset());
    writeElement(baseXsw, indent2, "yOffset", bda.getyOffset());

    if (bda.parametersData != null) {
      writeBdaParametersDataDirectly(baseXsw, bda.parametersData, indent2);
    }

    if (bda.barCodeData != null && bda.barCodeData.length > 0) {
      writeBinaryElement(baseXsw, indent2, "barCodeData", bda.barCodeData);
    }
    if (bda.getText() != null) {
      writeElement(baseXsw, indent2, "text", bda.getText());
    }

    baseXsw.writeCharacters(indent1);
    baseXsw.writeEndElement();
    MnemonicPerformanceMonitor.endWrite();
  }

  private void writeBdaParametersDataDirectly(XMLStreamWriter2 writer, BDA_BarCodeData.ParametersData pd, String indent) throws Exception {
    String indent3 = indent + "  ";
    writer.writeCharacters(indent);
    String rootName = pd.getClass().getSimpleName();
    writer.writeStartElement(rootName);

    if (pd.controlFlags != null && !pd.controlFlags.isEmpty()) {
      writer.writeCharacters(indent3);
      writer.writeStartElement("controlFlags");
      for (BDA_BarCodeData.ParametersData.ControlFlag flag : pd.controlFlags) {
        writeElement(writer, indent3 + "  ", "controlFlag", flag.name());
      }
      writer.writeCharacters(indent3);
      writer.writeEndElement();
    }
    writeElement(writer, indent3, "sequenceIndicator", pd.sequenceIndicator);
    writeElement(writer, indent3, "totalNumberOfSymbols", pd.totalNumberOfSymbols);

    if (pd instanceof BDA_BarCodeData.ParametersDataMatrixBarcode m) {
      writeElement(writer, indent3, "desiredRowSize", m.desiredRowSize);
      writeElement(writer, indent3, "desiredNumberOfRows", m.desiredNumberOfRows);
      writeElement(writer, indent3, "fileIDFirstByte", m.getFileIDFirstByte());
      writeElement(writer, indent3, "fileIDSecondByte", m.getFileIDSecondByte());
      if (m.specialFunctionFlags != null && !m.specialFunctionFlags.isEmpty()) {
        writer.writeCharacters(indent3);
        writer.writeStartElement("specialFunctionFlags");
        for (BDA_BarCodeData.ParametersDataMatrixBarcode.SpecialFunctionFlag flag : m.specialFunctionFlags) {
          writeElement(writer, indent3 + "  ", "specialFunctionFlag", flag.name());
        }
        writer.writeCharacters(indent3);
        writer.writeEndElement();
      }
    } else if (pd instanceof BDA_BarCodeData.ParametersDataMaxiCode_2D max) {
      writeElement(writer, indent3, "symbolMode", max.symbolMode.name());
      writeElement(writer, indent3, "specialFunctionFlag", max.specialFunctionFlag.name());
    } else if (pd instanceof BDA_BarCodeData.ParametersDataPDF417_2D pdf) {
      writeElement(writer, indent3, "numberOfDataSymbolCharactersPerRow", pdf.numberOfDataSymbolCharactersPerRow);
      writeElement(writer, indent3, "desiredNumberOfRows", pdf.desiredNumberOfRows);
      writeElement(writer, indent3, "securityLevel", pdf.securityLevel);
      writeElement(writer, indent3, "lengthOfMacroPDF417ControlBlock", pdf.lengthOfMacroPDF417ControlBlock);
      if (pdf.macroPDF417ControlBlock != null && pdf.macroPDF417ControlBlock.length > 0) {
        writeBinaryElement(writer, indent3, "macroPDF417ControlBlock", pdf.macroPDF417ControlBlock);
      }
    } else if (pd instanceof BDA_BarCodeData.ParametersDataQRCode_2D qr) {
      writeElement(writer, indent3, "conversion", qr.conversion.name());
      writeElement(writer, indent3, "versionOfSymbol", qr.versionOfSymbol);
      writeElement(writer, indent3, "errorCorrectionLevel", qr.errorCorrectionLevel.name());
      writeElement(writer, indent3, "parityData", qr.parityData);
      writeElement(writer, indent3, "qrCodeSpecialFunctionFlags", qr.qrCodeSpecialFunctionFlags.toString());
      writeElement(writer, indent3, "applicationIndicator", qr.applicationIndicator);
      if (pd instanceof BDA_BarCodeData.ParametersDataQRCodeWithImage qri) {
        writeElement(writer, indent3, "qrCodeWithImageFlags", qri.qrCodeWithImageFlags.toString());
        writeElement(writer, indent3, "repeatingGroupsLength", qri.repeatingGroupsLength);
        for (BDA_BarCodeData.ParametersDataQRCodeWithImage.ImageInformationBlock block : qri.imageInformationBlocks) {
          writer.writeCharacters(indent3);
          writer.writeStartElement("imageInformationBlock");
          String indent4 = XmlIndenter.getIndent(4);
          writeElement(writer, indent4, "length", block.length);
          writeElement(writer, indent4, "imageLocalId", block.imageLocalId);
          writeElement(writer, indent4, "offsetUnitBase", block.offsetUnitBase);
          writeElement(writer, indent4, "offsetUpub", block.offsetUpub);
          writeElement(writer, indent4, "xOffset", block.xOffset);
          writeElement(writer, indent4, "yOffset", block.yOffset);
          writeElement(writer, indent4, "orientation", block.orientation);
          writeElement(writer, indent4, "coordinateSystem", block.coordinateSystem);
          writeElement(writer, indent4, "extentUnitBase", block.extentUnitBase);
          writeElement(writer, indent4, "extentUpub", block.extentUpub);
          writeElement(writer, indent4, "xExtent", block.xExtent);
          writeElement(writer, indent4, "yExtent", block.yExtent);
          writeElement(writer, indent4, "mappingOption", block.mappingOption);
          if (block.additionalData != null && block.additionalData.length > 0) {
            writeBinaryElement(writer, indent4, "additionalData", block.additionalData);
          }
          writer.writeCharacters(indent3);
          writer.writeEndElement();
        }
      }
    } else if (pd instanceof BDA_BarCodeData.ParametersDataAztecCode aztec) {
      writeElement(writer, indent3, "desiredNumberOfLayers", aztec.desiredNumberOfLayers);
      writeElement(writer, indent3, "levelOfErrorCorrection", aztec.levelOfErrorCorrection);
      writeElement(writer, indent3, "aztecSpecialFunctionFlags", aztec.aztecSpecialFunctionFlags.toString());
      writeElement(writer, indent3, "applicationIndicator", aztec.applicationIndicator);
      writeElement(writer, indent3, "sequenceIndicator", aztec.getSequenceIndicator());
      writeElement(writer, indent3, "totalNumberOfSymbols", aztec.getTotalNumberOfSymbols());
      writeElement(writer, indent3, "structuredAppendIdLength", aztec.structuredAppendIdLength);
      if (aztec.structuredAppendId != null && aztec.structuredAppendId.length > 0) {
        writeBinaryElement(writer, indent3, "structuredAppendId", aztec.structuredAppendId);
      }
      writeElement(writer, indent3, "additionalParametersLength", aztec.additionalParametersLength);
      if (aztec.additionalParameters != null && aztec.additionalParameters.length > 0) {
        writeBinaryElement(writer, indent3, "additionalParameters", aztec.additionalParameters);
      }
    } else if (pd instanceof BDA_BarCodeData.ParametersDataHanXinCode hanxin) {
      writeElement(writer, indent3, "version", hanxin.version);
      writeElement(writer, indent3, "errorCorrectionLevel", hanxin.errorCorrectionLevel);
      writeElement(writer, indent3, "hanXinSpecialFunctionFlags", hanxin.hanXinSpecialFunctionFlags.toString());
      writeElement(writer, indent3, "applicationIndicator", hanxin.applicationIndicator);
      writeElement(writer, indent3, "additionalParametersLength", hanxin.additionalParametersLength);
      if (hanxin.additionalParameters != null && hanxin.additionalParameters.length > 0) {
        writeBinaryElement(writer, indent3, "additionalParameters", hanxin.additionalParameters);
      }
    } else if (pd instanceof BDA_BarCodeData.ParametersDataIntelligentMailPackageBarcode imp) {
      writeElement(writer, indent3, "intelligentMailPackageBarcodeFlags", imp.intelligentMailPackageBarcodeFlags.toString());
      writeElement(writer, indent3, "bannerLength", imp.bannerLength);
      if (imp.bannerString != null && imp.bannerString.length > 0) {
        writeBinaryElement(writer, indent3, "bannerString", imp.bannerString);
      }
    }

    writer.writeCharacters(indent);
    writer.writeEndElement();
  }

  private void writeBddDirectly(BDD_BarCodeDataDescriptor bdd) throws Exception {
    MnemonicPerformanceMonitor.startWriteWithMnemonic("BDD");
    baseXsw.writeStartElement("BDD_BarCodeDataDescriptor");
    String indent2 = XmlIndenter.getIndent(2);
    String indent1 = XmlIndenter.getIndent(1);

    writeElement(baseXsw, indent2, "unitBase", bdd.getUnitBase().name());
    writeElement(baseXsw, indent2, "unitsPerUnitBaseX", bdd.getUnitsPerUnitBaseX());
    writeElement(baseXsw, indent2, "unitsPerUnitBaseY", bdd.getUnitsPerUnitBaseY());
    writeElement(baseXsw, indent2, "presentationSpaceWidth", bdd.getPresentationSpaceWidth());
    writeElement(baseXsw, indent2, "presentationSpaceLength", bdd.getPresentationSpaceLength());
    writeElement(baseXsw, indent2, "desiredSymbolWidth", bdd.getDesiredSymbolWidth());
    writeElement(baseXsw, indent2, "barcodeType", bdd.getBarcodeType().name());
    writeElement(baseXsw, indent2, "barcodeModifier", bdd.getBarcodeModifier());
    writeElement(baseXsw, indent2, "fontLocalIDForHRI", bdd.getFontLocalIDForHRI());
    writeElement(baseXsw, indent2, "color", bdd.getColor());
    writeElement(baseXsw, indent2, "moduleWidthInMils", bdd.getModuleWidthInMils());
    writeElement(baseXsw, indent2, "elementHeight", bdd.getElementHeight());
    writeElement(baseXsw, indent2, "heightMultiplier", bdd.getHeightMultiplier());
    writeElement(baseXsw, indent2, "wideToNarrowRatio", bdd.getWideToNarrowRatio());

    baseXsw.writeCharacters(indent1);
    baseXsw.writeEndElement();
    MnemonicPerformanceMonitor.endWrite();
  }
}
