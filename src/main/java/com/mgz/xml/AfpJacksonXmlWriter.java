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
      this.baseXsw = new AfpXmlStreamWriter(rawXsw, cos);
      this.xsw = MnemonicPerformanceMonitor.isEnabled() ? new MnemonicXMLStreamWriter(this.baseXsw) : this.baseXsw;
      if (!fragmentMode) {
        this.xsw.writeStartDocument("UTF-8", "1.0");
        this.baseXsw.writeRaw("\n");
        this.xsw.setPrefix("xsi", "http://www.w3.org/2001/XMLSchema-instance");
        this.xsw.writeStartElement("AFPDocument");
        this.xsw.writeNamespace("xsi", "http://www.w3.org/2001/XMLSchema-instance");
        this.baseXsw.writeRaw("\n");
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
      baseXsw.writeRaw(XmlIndenter.LEVEL_1_PURE);
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
    } else if (sf instanceof OCD_ObjectContainerData ocd) {
      writeOcdDirectly(ocd);
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
      writeBbcDirectly(bbc);
    } else if (sf instanceof EBC_EndBarCodeObject ebc) {
      writeEbcDirectly(ebc);
    } else if (sf instanceof BDD_BarCodeDataDescriptor bdd) {
      writeBddDirectly(bdd);
    } else if (sf instanceof BDA_BarCodeData bda) {
      writeBdaDirectly(bda);
    } else {
      if (MnemonicPerformanceMonitor.isEnabled()) {
        String rootName = MnemonicPerformanceMonitor.getSimpleName(sf.getClass());
        String mnemonic = MnemonicPerformanceMonitor.extractMnemonicFromString(rootName);
        MnemonicPerformanceMonitor.startWriteWithMnemonic(mnemonic);
      }
      JacksonXmlMapperProvider.getCachedWriter(sf.getClass(), useWoodstox, true).writeValue(baseFragmentGenerator, sf);
      if (MnemonicPerformanceMonitor.isEnabled()) {
        MnemonicPerformanceMonitor.endWrite();
      }
    }

    if (!fragmentMode) {
      baseXsw.writeRaw("\n");
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
        baseXsw.writeRaw(indent2);
        writeTriplet(baseXsw, triplet, indent2);
      }
    }
    if (tle.getText() != null) {
      writeElement(baseXsw, indent2, "text", tle.getText());
    }
    baseXsw.writeRaw(indent1);
    baseXsw.writeEndElement();
    MnemonicPerformanceMonitor.endWrite();
  }

  private void writeMcdDirectly(MCD_MapContainerData mcd) throws Exception {
    MnemonicPerformanceMonitor.startWriteWithMnemonic("MCD");
    baseXsw.writeStartElement("MCD_MapContainerData");
    String indent2 = XmlIndenter.getIndent(2);
    writeElement(baseXsw, indent2, "lengthOfRepeatingGroup", mcd.getLengthOfRepeatingGroup());
    if (mcd.getTriplet() != null) {
      baseXsw.writeRaw(indent2);
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
          baseXsw.writeRaw(indent2);
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
            baseXsw.writeRaw(indent3);
            baseXsw.writeStartElement("flags");
            for (PGP_PagePosition_Format2.PGP_RepeatingGroup.PGP_RGFlag flag : pgpRg.getFlags()) {
              baseXsw.writeRaw(XmlIndenter.getIndent(4));
              baseXsw.writeStartElement("pgpRgFlag");
              baseXsw.writeCharacters(flag.name());
              baseXsw.writeEndElement();
            }
            baseXsw.writeRaw(indent3);
            baseXsw.writeEndElement();
          }
          if (pgpRg.getPageModififationControlID() != null) {
            writeElement(baseXsw, indent3, "pageModififationControlID", pgpRg.getPageModififationControlID());
          }
          baseXsw.writeRaw(indent2);
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
          baseXsw.writeRaw(indent2);
          baseXsw.writeStartElement("msuRepeatingGroup");
          writeElement(baseXsw, indent3, "nameOfTextSuppresstion", msuRg.getNameOfTextSuppresstion());
          writeElement(baseXsw, indent3, "reserved8", msuRg.getReserved8());
          writeElement(baseXsw, indent3, "localID", msuRg.getLocalID());
          baseXsw.writeRaw(indent2);
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
      baseXsw.writeRaw(indent2);
      baseXsw.writeStartElement("keywords");
      for (MMC_MediumModificationControl.MMC_KeyWord kw : mmc.getKeywords()) {
        baseXsw.writeRaw(indent3);
        baseXsw.writeStartElement("keyword");
        if (kw.keywordID() != null) {
          writeElement(baseXsw, indent4, "keywordID", kw.keywordID().name());
        }
        writeElement(baseXsw, indent4, "parameter", kw.parameter());
        baseXsw.writeRaw(indent3);
        baseXsw.writeEndElement();
      }
      baseXsw.writeRaw(indent2);
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
        baseXsw.writeRaw(indent);
        writeTriplet(writer, triplet, indent);
      }
    }
    if (text != null && !text.isEmpty()) {
      baseXsw.writeRaw(indent);
      baseXsw.writeStartElement("text");
      baseXsw.writeCharacters(text);
      baseXsw.writeEndElement();
    }
    baseXsw.writeRaw(closingIndent);
  }

  private void writeTriplet(XMLStreamWriter2 writer, Triplet triplet, String indent) throws Exception {
    int level = indent.length() / 2;
    if (indent.startsWith("\n")) {
      level = (indent.length() - 1) / 2;
    }
    String childIndent = XmlIndenter.getIndent(level + 1);
    if (triplet instanceof Triplet.FullyQualifiedName fqn) {
      baseXsw.writeStartElement("FullyQualifiedName");
      writeElement(baseXsw, childIndent, "type", fqn.getType().name());
      writeElement(baseXsw, childIndent, "format", fqn.getFormat().name());
      writeElement(baseXsw, childIndent, "nameAsString", fqn.getNameAsString());
      writeElement(baseXsw, childIndent, "text", fqn.getText());
      baseXsw.writeRaw(indent);
      baseXsw.writeEndElement();
    } else if (triplet instanceof Triplet.AttributeValue av) {
      baseXsw.writeStartElement("AttributeValue");
      writeElement(baseXsw, childIndent, "attributeValue", av.getAttributeValue());
      writeElement(baseXsw, childIndent, "text", av.getText());
      baseXsw.writeRaw(indent);
      baseXsw.writeEndElement();
    } else if (triplet instanceof Triplet.CodedGraphicCharacterSetGlobalID cgcs) {
      baseXsw.writeStartElement("CodedGraphicCharacterSetGlobalID");
      writeElement(baseXsw, childIndent, "graphicCharacterSetGlobalID", cgcs.getGraphicCharacterSetGlobalID());
      writeElement(baseXsw, childIndent, "codePageGlobalID_codedCharacterSetID", cgcs.getCodePageGlobalID_codedCharacterSetID());
      baseXsw.writeRaw(indent);
      baseXsw.writeEndElement();
    } else if (triplet instanceof Triplet.MappingOption mo) {
      baseXsw.writeStartElement("MappingOption");
      writeElement(baseXsw, childIndent, "dataObjecMapingOption", mo.getDataObjecMapingOption().name());
      baseXsw.writeRaw(indent);
      baseXsw.writeEndElement();
    } else if (triplet instanceof Triplet.AttributeQualifier aq) {
      baseXsw.writeStartElement("AttributeQualifier");
      writeElement(baseXsw, childIndent, "sequenceNumber", aq.sequenceNumber);
      writeElement(baseXsw, childIndent, "levelNumber", aq.levelNumber);
      baseXsw.writeRaw(indent);
      baseXsw.writeEndElement();
    } else if (triplet instanceof Triplet.Comment c) {
      baseXsw.writeStartElement("Comment");
      writeElement(baseXsw, childIndent, "comment", c.comment);
      writeElement(baseXsw, childIndent, "text", c.getText());
      baseXsw.writeRaw(indent);
      baseXsw.writeEndElement();
    } else if (triplet instanceof Triplet.ResourceLocalIdentifier rli) {
      baseXsw.writeStartElement("ResourceLocalIdentifier");
      if (rli.getResourceType() != null) {
        writeElement(baseXsw, childIndent, "resourceType", rli.getResourceType().name());
      }
      writeElement(baseXsw, childIndent, "resourceLocalID", rli.getResourceLocalID());
      baseXsw.writeRaw(indent);
      baseXsw.writeEndElement();
    } else if (triplet instanceof Triplet.ObjectClassification oc) {
      baseXsw.writeStartElement("ObjectClassification");
      writeElement(baseXsw, childIndent, "reserved2", oc.reserved2);
      if (oc.objectClass != null) {
        writeElement(baseXsw, childIndent, "objectClass", oc.objectClass.name());
      }
      if (oc.reserved4_5 != null) {
        writeElement(baseXsw, childIndent, "reserved4_5", UtilCharacterEncoding.bytesToHexString(oc.reserved4_5));
      }
      if (oc.structureFlags != null) {
        baseXsw.writeRaw(childIndent);
        baseXsw.writeStartElement("structureFlags");
        for (Triplet.ObjectClassification.StructureFlag flag : oc.structureFlags) {
          baseXsw.writeRaw(XmlIndenter.getIndent(level + 2));
          baseXsw.writeStartElement("structureFlag");
          baseXsw.writeCharacters(flag.name());
          baseXsw.writeEndElement();
        }
        baseXsw.writeRaw(childIndent);
        baseXsw.writeEndElement();
      }
      if (oc.registeredObjectID != null) {
        writeElement(baseXsw, childIndent, "registeredObjectID", UtilCharacterEncoding.bytesToHexString(oc.registeredObjectID));
      }
      writeElement(baseXsw, childIndent, "objectTypeName", oc.objectTypeName);
      writeElement(baseXsw, childIndent, "objectVersion", oc.objectVersion);
      writeElement(baseXsw, childIndent, "companyName", oc.companyName);
      baseXsw.writeRaw(indent);
      baseXsw.writeEndElement();
    } else if (triplet instanceof Triplet.MODCAInterchangeSet mis) {
      baseXsw.writeStartElement("MODCAInterchangeSet");
      if (mis.type != null) {
        writeElement(baseXsw, childIndent, "type", mis.type.name());
      }
      if (mis.identifier != null) {
        writeElement(baseXsw, childIndent, "identifier", mis.identifier.name());
      }
      baseXsw.writeRaw(indent);
      baseXsw.writeEndElement();
    } else if (triplet instanceof Triplet.CharacterRotation cr) {
      baseXsw.writeStartElement("CharacterRotation");
      if (cr.characterRotation != null) {
        writeElement(baseXsw, childIndent, "characterRotation", cr.characterRotation.name());
      }
      baseXsw.writeRaw(indent);
      baseXsw.writeEndElement();
    } else if (triplet instanceof Triplet.ObjectByteOffset obo) {
      baseXsw.writeStartElement("ObjectByteOffset");
      writeElement(baseXsw, childIndent, "byteOffset", obo.byteOffset);
      if (obo.byteOffsetHighOrder != null) {
        writeElement(baseXsw, childIndent, "byteOffsetHighOrder", obo.byteOffsetHighOrder);
      }
      baseXsw.writeRaw(indent);
      baseXsw.writeEndElement();
    } else if (triplet instanceof Triplet.MeasurementUnits mu) {
      baseXsw.writeStartElement("MeasurementUnits");
      if (mu.xUnitBase != null) {
        writeElement(baseXsw, childIndent, "xUnitBase", mu.xUnitBase.name());
      }
      if (mu.yUnitBase != null) {
        writeElement(baseXsw, childIndent, "yUnitBase", mu.yUnitBase.name());
      }
      writeElement(baseXsw, childIndent, "xUnitsPerUnitbase", mu.xUnitsPerUnitbase);
      writeElement(baseXsw, childIndent, "yUnitsPerUnitbase", mu.yUnitsPerUnitbase);
      baseXsw.writeRaw(indent);
      baseXsw.writeEndElement();
    } else if (triplet instanceof Triplet.ObjectAreaSize oas) {
      baseXsw.writeStartElement("ObjectAreaSize");
      writeElement(baseXsw, childIndent, "sizeType_0x02", oas.sizeType_0x02);
      writeElement(baseXsw, childIndent, "xSize", oas.xSize);
      writeElement(baseXsw, childIndent, "ySize", oas.ySize);
      baseXsw.writeRaw(indent);
      baseXsw.writeEndElement();
    } else if (triplet instanceof Triplet.AreaDefinition ad) {
      baseXsw.writeStartElement("AreaDefinition");
      writeElement(baseXsw, childIndent, "reserved2", ad.reserved2);
      writeElement(baseXsw, childIndent, "xOrigin", ad.xOrigin);
      writeElement(baseXsw, childIndent, "yOrigin", ad.yOrigin);
      writeElement(baseXsw, childIndent, "xSize", ad.xSize);
      writeElement(baseXsw, childIndent, "ySize", ad.ySize);
      baseXsw.writeRaw(indent);
      baseXsw.writeEndElement();
    } else if (triplet instanceof Triplet.ColorSpecification cs) {
      baseXsw.writeStartElement("ColorSpecification");
      writeElement(baseXsw, childIndent, "reserved2", cs.reserved2);
      if (cs.colorSpace != null) {
        writeElement(baseXsw, childIndent, "colorSpace", cs.colorSpace.name());
      }
      if (cs.reserved4_7 != null) {
        writeElement(baseXsw, childIndent, "reserved4_7", UtilCharacterEncoding.bytesToHexString(cs.reserved4_7));
      }
      writeElement(baseXsw, childIndent, "nrOfBitsComponent1", cs.nrOfBitsComponent1);
      writeElement(baseXsw, childIndent, "nrOfBitsComponent2", cs.nrOfBitsComponent2);
      writeElement(baseXsw, childIndent, "nrOfBitsComponent3", cs.nrOfBitsComponent3);
      writeElement(baseXsw, childIndent, "nrOfBitsComponent4", cs.nrOfBitsComponent4);
      if (cs.colorValue != null) {
        writeElement(baseXsw, childIndent, "colorValue", UtilCharacterEncoding.bytesToHexString(cs.colorValue));
      }
      baseXsw.writeRaw(indent);
      baseXsw.writeEndElement();
    } else if (triplet instanceof Triplet.EncodingSchemeID esi) {
      baseXsw.writeStartElement("EncodingSchemeID");
      if (esi.encodingSchemeForCodePage != null) {
        baseXsw.writeRaw(childIndent);
        baseXsw.writeStartElement("encodingSchemeForCodePage");
        for (Triplet.EncodingSchemeID.EncodingScheme es : esi.encodingSchemeForCodePage) {
          baseXsw.writeRaw(XmlIndenter.getIndent(level + 2));
          baseXsw.writeStartElement("encodingScheme");
          baseXsw.writeCharacters(es.name());
          baseXsw.writeEndElement();
        }
        baseXsw.writeRaw(childIndent);
        baseXsw.writeEndElement();
      }
      if (esi.encodingSchemeForUserData != null) {
        baseXsw.writeRaw(childIndent);
        baseXsw.writeStartElement("encodingSchemeForUserData");
        for (Triplet.EncodingSchemeID.EncodingScheme es : esi.encodingSchemeForUserData) {
          baseXsw.writeRaw(XmlIndenter.getIndent(level + 2));
          baseXsw.writeStartElement("encodingScheme");
          baseXsw.writeCharacters(es.name());
          baseXsw.writeEndElement();
        }
        baseXsw.writeRaw(childIndent);
        baseXsw.writeEndElement();
      }
      baseXsw.writeRaw(indent);
      baseXsw.writeEndElement();
    } else if (triplet instanceof Triplet.ObjectCount oc) {
      baseXsw.writeStartElement("ObjectCount");
      writeElement(baseXsw, childIndent, "subordinateObjectType", oc.subordinateObjectType);
      writeElement(baseXsw, childIndent, "reserved3", oc.reserved3);
      writeElement(baseXsw, childIndent, "numberOfObjectsLow", oc.numberOfObjectsLow);
      if (oc.numberOfObjectsHigh != null) {
        writeElement(baseXsw, childIndent, "numberOfObjectsHigh", oc.numberOfObjectsHigh);
      }
      baseXsw.writeRaw(indent);
      baseXsw.writeEndElement();
    } else if (triplet instanceof Triplet.LocalObjectDateAndTimeStamp lodts) {
      baseXsw.writeStartElement("LocalObjectDateAndTimeStamp");
      if (lodts.dateAndTimeStampType != null) {
        writeElement(baseXsw, childIndent, "dateAndTimeStampType", lodts.dateAndTimeStampType.name());
      }
      writeElement(baseXsw, childIndent, "hundreds", lodts.hundreds);
      writeElement(baseXsw, childIndent, "tens", lodts.tens);
      writeElement(baseXsw, childIndent, "dayOfYear", lodts.dayOfYear);
      writeElement(baseXsw, childIndent, "hourOfDay", lodts.hourOfDay);
      writeElement(baseXsw, childIndent, "minuteOfHour", lodts.minuteOfHour);
      writeElement(baseXsw, childIndent, "secondOfMinute", lodts.secondOfMinute);
      writeElement(baseXsw, childIndent, "hundredthOfSecond", lodts.hundredthOfSecond);
      baseXsw.writeRaw(indent);
      baseXsw.writeEndElement();
    } else if (triplet instanceof Triplet.UniversalDateAndTimeStamp udts) {
      baseXsw.writeStartElement("UniversalDateAndTimeStamp");
      writeElement(baseXsw, childIndent, "reserved2", udts.reserved2);
      writeElement(baseXsw, childIndent, "year", udts.year);
      writeElement(baseXsw, childIndent, "monthOfYear", udts.monthOfYear);
      writeElement(baseXsw, childIndent, "dayOfMonth", udts.dayOfMonth);
      writeElement(baseXsw, childIndent, "hourOfDay", udts.hourOfDay);
      writeElement(baseXsw, childIndent, "minuteOfHour", udts.minuteOfHour);
      writeElement(baseXsw, childIndent, "secondOfMinute", udts.secondOfMinute);
      if (udts.timeZone != null) {
        writeElement(baseXsw, childIndent, "timeZone", udts.timeZone.name());
      }
      writeElement(baseXsw, childIndent, "diffHours", udts.diffHours);
      writeElement(baseXsw, childIndent, "diffMinutes", udts.diffMinutes);
      baseXsw.writeRaw(indent);
      baseXsw.writeEndElement();
    } else if (triplet instanceof Triplet.FontDescriptorSpecification fds) {
      baseXsw.writeStartElement("FontDescriptorSpecification");
      if (fds.fontWeigthClass != null) {
        writeElement(baseXsw, childIndent, "fontWeigthClass", fds.fontWeigthClass.name());
      }
      if (fds.fontWidthClass != null) {
        writeElement(baseXsw, childIndent, "fontWidthClass", fds.fontWidthClass.name());
      }
      writeElement(baseXsw, childIndent, "fontHeight", fds.fontHeight);
      writeElement(baseXsw, childIndent, "fontWidth", fds.fontWidth);
      if (fds.fontDsFlags != null) {
        baseXsw.writeRaw(childIndent);
        baseXsw.writeStartElement("fontDsFlags");
        for (Triplet.FontDescriptorSpecification.FDS_FontDsFlag flag : fds.fontDsFlags) {
          baseXsw.writeRaw(XmlIndenter.getIndent(level + 2));
          baseXsw.writeStartElement("fdsFontDsFlag");
          baseXsw.writeCharacters(flag.name());
          baseXsw.writeEndElement();
        }
        baseXsw.writeRaw(childIndent);
        baseXsw.writeEndElement();
      }
      if (fds.reserved9_18 != null) {
        writeElement(baseXsw, childIndent, "reserved9_18", UtilCharacterEncoding.bytesToHexString(fds.reserved9_18));
      }
      if (fds.fontUsFlags != null) {
        baseXsw.writeRaw(childIndent);
        baseXsw.writeStartElement("fontUsFlags");
        for (Triplet.FontDescriptorSpecification.FDS_FontUsFlag flag : fds.fontUsFlags) {
          baseXsw.writeRaw(XmlIndenter.getIndent(level + 2));
          baseXsw.writeStartElement("fdsFontUsFlag");
          baseXsw.writeCharacters(flag.name());
          baseXsw.writeEndElement();
        }
        baseXsw.writeRaw(childIndent);
        baseXsw.writeEndElement();
      }
      baseXsw.writeRaw(indent);
      baseXsw.writeEndElement();
    } else if (triplet instanceof Triplet.ResourceObjectType rot) {
      baseXsw.writeStartElement("ResourceObjectType");
      if (rot.objectType != null) {
        writeElement(baseXsw, childIndent, "objectType", rot.objectType.name());
      }
      baseXsw.writeRaw(indent);
      baseXsw.writeEndElement();
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
          baseXsw.writeRaw(childIndent);
          writeTriplet(baseXsw, triplet, childIndent);
        }
      }
    }
    baseXsw.writeRaw(indent);
    baseXsw.writeEndElement();
  }

  private void writePtxDirectly(PTX_PresentationTextData ptx) throws Exception {
    MnemonicPerformanceMonitor.startWriteWithMnemonic("PTX");
    baseXsw.writeStartElement("PTX_PresentationTextData");
    List<PTOCAControlSequence> sequences = ptx.getControlSequences();
    if (sequences != null) {
      boolean ptxDebug = com.mgz.util.PTXPerformanceMonitor.isEnabled();
      for (PTOCAControlSequence cs : sequences) {
        baseXsw.writeRaw(XmlIndenter.getIndent(2));
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
              MnemonicPerformanceMonitor.getSimpleName(cs.getClass()), System.nanoTime() - csStart, payloadSize, cos.getCount() - csStartCount);
        }
      }
    }
    baseXsw.writeRaw(XmlIndenter.getIndent(1));
    baseXsw.writeEndElement();
    MnemonicPerformanceMonitor.endWrite();
  }

  private void writeControlSequence(PTOCAControlSequence cs, String indent) throws Exception {
    String childIndent = XmlIndenter.getIndent(3);
    if (cs instanceof PTOCAControlSequence.TRN_TransparentData trn) {
      baseXsw.writeStartElement("TRN_TransparentData");
      writeElement(baseXsw, childIndent, "transparentData", trn.getTransparentData());
      if (trn.getEncoding() != null && trn.getTransparentDataEBCDIC() != null) {
        baseXsw.writeRaw(childIndent);
        baseXsw.writeStartElement("text");
        baseXsw.writeEbcdic(trn.getTransparentDataEBCDIC(), 0, trn.getTransparentDataEBCDIC().length, trn.getEncoding());
        baseXsw.writeEndElement();
      } else {
        writeElement(baseXsw, childIndent, "text", trn.getText());
      }
      baseXsw.writeRaw(indent);
      baseXsw.writeEndElement();
    } else if (cs instanceof PTOCAControlSequence.GraphicCharacters gc) {
      baseXsw.writeStartElement("GraphicCharacters");
      if (gc.getEncoding() != null && gc.getData() != null) {
        baseXsw.writeRaw(childIndent);
        baseXsw.writeStartElement("text");
        baseXsw.writeEbcdic(gc.getData(), 0, gc.getData().length, gc.getEncoding());
        baseXsw.writeEndElement();
      } else {
        writeElement(baseXsw, childIndent, "text", gc.getText());
      }
      baseXsw.writeRaw(indent);
      baseXsw.writeEndElement();
    } else if (cs instanceof PTOCAControlSequence.AMI_AbsoluteMoveInline ami) {
      baseXsw.writeEmptyElement("AMI_AbsoluteMoveInline");
      baseXsw.writeIntAttribute(null, null, "displacement", ami.getDisplacement());
    } else if (cs instanceof PTOCAControlSequence.AMB_AbsoluteMoveBaseline amb) {
      baseXsw.writeEmptyElement("AMB_AbsoluteMoveBaseline");
      baseXsw.writeIntAttribute(null, null, "displacement", amb.getDisplacement());
    } else if (cs instanceof PTOCAControlSequence.RMI_RelativeMoveInline rmi) {
      baseXsw.writeEmptyElement("RMI_RelativeMoveInline");
      baseXsw.writeIntAttribute(null, null, "increment", rmi.getIncrement());
    } else if (cs instanceof PTOCAControlSequence.RMB_RelativeMoveBaseline rmb) {
      baseXsw.writeEmptyElement("RMB_RelativeMoveBaseline");
      baseXsw.writeIntAttribute(null, null, "increment", rmb.getIncrement());
    } else if (cs instanceof PTOCAControlSequence.SIM_SetInlineMargin sim) {
      baseXsw.writeEmptyElement("SIM_SetInlineMargin");
      baseXsw.writeIntAttribute(null, null, "displacement", sim.getDisplacement());
    } else if (cs instanceof PTOCAControlSequence.SCFL_SetCodedFontLocal scfl) {
      baseXsw.writeEmptyElement("SCFL_SetCodedFontLocal");
      baseXsw.writeIntAttribute(null, null, "codedFontLocalID", scfl.getCodedFontLocalID());
    } else if (cs instanceof PTOCAControlSequence.SBI_SetBaselineIncrement sbi) {
      baseXsw.writeEmptyElement("SBI_SetBaselineIncrement");
      baseXsw.writeIntAttribute(null, null, "increment", sbi.getIncrement());
    } else if (cs instanceof PTOCAControlSequence.BLN_BeginLine) {
      baseXsw.writeEmptyElement("BLN_BeginLine");
    } else if (cs instanceof PTOCAControlSequence.BSU_BeginSuppression bsu) {
      baseXsw.writeEmptyElement("BSU_BeginSuppression");
      baseXsw.writeIntAttribute(null, null, "suppressionID", bsu.getSuppressionID());
    } else if (cs instanceof PTOCAControlSequence.ESU_EndSuppression esu) {
      baseXsw.writeEmptyElement("ESU_EndSuppression");
      baseXsw.writeIntAttribute(null, null, "suppressionID", esu.getSuppressionID());
    } else if (cs instanceof PTOCAControlSequence.STO_SetTextOrientation sto) {
      baseXsw.writeEmptyElement("STO_SetTextOrientation");
      if (sto.getxOrientation() != null) {
        baseXsw.writeAttribute("xOrientation", sto.getxOrientation().name());
      }
      if (sto.getyOrientation() != null) {
        baseXsw.writeAttribute("yOrientation", sto.getyOrientation().name());
      }
    } else if (cs instanceof PTOCAControlSequence.STC_SetTextColor stc) {
      baseXsw.writeEmptyElement("STC_SetTextColor");
      if (stc.getForegroundColor() != null) {
        baseXsw.writeAttribute("foregroundColor", stc.getForegroundColor().name());
      }
      if (stc.getPrecision() != null) {
        baseXsw.writeAttribute("precision", stc.getPrecision().name());
      }
    } else if (cs instanceof PTOCAControlSequence.USC_Underscore usc) {
      baseXsw.writeEmptyElement("USC_Underscore");
      if (usc.getBypassFlag() != null) {
        baseXsw.writeAttribute("bypassFlag", usc.getBypassFlag().name());
      }
    } else if (cs instanceof PTOCAControlSequence.SIA_SetIntercharacterAdjustment sia) {
      baseXsw.writeEmptyElement("SIA_SetIntercharacterAdjustment");
      baseXsw.writeIntAttribute(null, null, "adjustment", sia.getAdjustment());
      if (sia.getDirection() != null) {
        baseXsw.writeAttribute("direction", sia.getDirection().name());
      }
    } else if (cs instanceof PTOCAControlSequence.SVI_SetVariableSpaceCharacterIncrement svi) {
      baseXsw.writeEmptyElement("SVI_SetVariableSpaceCharacterIncrement");
      baseXsw.writeIntAttribute(null, null, "increment", svi.getIncrement());
    } else if (cs instanceof PTOCAControlSequence.SEC_SetExtendedTextColor sec) {
      baseXsw.writeStartElement("SEC_SetExtendedTextColor");
      writeElement(baseXsw, childIndent, "colorSpace", sec.getColorSpace().name());
      writeElement(baseXsw, childIndent, "nrOfBitsComponent1", sec.getNrOfBitsComponent1());
      writeElement(baseXsw, childIndent, "nrOfBitsComponent2", sec.getNrOfBitsComponent2());
      writeElement(baseXsw, childIndent, "nrOfBitsComponent3", sec.getNrOfBitsComponent3());
      writeElement(baseXsw, childIndent, "nrOfBitsComponent4", sec.getNrOfBitsComponent4());
      if (sec.getColorValue() != null) {
        writeBinaryElement(baseXsw, childIndent, "colorValue", sec.getColorValue());
      }
      baseXsw.writeRaw(indent);
      baseXsw.writeEndElement();
    } else if (cs instanceof PTOCAControlSequence.DIR_DrawIaxisRule dir) {
      baseXsw.writeStartElement("DIR_DrawIaxisRule");
      writeElement(baseXsw, childIndent, "length", dir.getLength());
      if (dir.getWidth() != null) {
        writeElement(baseXsw, childIndent, "width", dir.getWidth());
      }
      if (dir.getWidthFraction() != null) {
        writeElement(baseXsw, childIndent, "widthFraction", dir.getWidthFraction());
      }
      baseXsw.writeRaw(indent);
      baseXsw.writeEndElement();
    } else if (cs instanceof PTOCAControlSequence.DBR_DrawBaxisRule dbr) {
      baseXsw.writeStartElement("DBR_DrawBaxisRule");
      writeElement(baseXsw, childIndent, "length", dbr.getLength());
      if (dbr.getWidth() != null) {
        writeElement(baseXsw, childIndent, "width", dbr.getWidth());
      }
      if (dbr.getWidthFraction() != null) {
        writeElement(baseXsw, childIndent, "widthFraction", dbr.getWidthFraction());
      }
      baseXsw.writeRaw(indent);
      baseXsw.writeEndElement();
    } else if (cs instanceof PTOCAControlSequence.NOP_NoOperation nop) {
      baseXsw.writeStartElement("NOP_NoOperation");
      if (nop.getText() != null) {
        writeElement(baseXsw, childIndent, "text", nop.getText());
      }
      if (nop.getIgnoredData() != null) {
        writeBinaryElement(baseXsw, childIndent, "ignoredData", nop.getIgnoredData());
      }
      baseXsw.writeRaw(indent);
      baseXsw.writeEndElement();
    } else if (cs instanceof PTOCAControlSequence.TBM_TemporaryBaselineMove tbm) {
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
      baseXsw.writeRaw(indent);
      baseXsw.writeEndElement();
    } else if (cs instanceof PTOCAControlSequence.OVS_Overstrike ovs) {
      baseXsw.writeStartElement("OVS_Overstrike");
      if (ovs.getBypassFlag() != null) {
        writeElement(baseXsw, childIndent, "bypassFlag", ovs.getBypassFlag().name());
      }
      writeElement(baseXsw, childIndent, "overStrikeCharacterCodePoint", ovs.getOverStrikeCharacterCodePoint());
      writeElement(baseXsw, childIndent, "text", ovs.getText());
      baseXsw.writeRaw(indent);
      baseXsw.writeEndElement();
    } else if (cs instanceof PTOCAControlSequence.RPS_RepeatString rps) {
      baseXsw.writeStartElement("RPS_RepeatString");
      writeElement(baseXsw, childIndent, "repeatLength", rps.getRepeatLength());
      if (rps.getText() != null) {
        writeElement(baseXsw, childIndent, "text", rps.getText());
      }
      if (rps.getRepeatData() != null) {
        writeBinaryElement(baseXsw, childIndent, "repeatData", rps.getRepeatData());
      }
      baseXsw.writeRaw(indent);
      baseXsw.writeEndElement();
    } else {
      JacksonXmlMapperProvider.getCachedWriter(cs.getClass(), useWoodstox, true).writeValue(baseFragmentGenerator, cs);
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
      baseXsw.writeRaw(indent);
      baseXsw.writeStartElement("fontUseFlags");
      for (FNC_FontControl.FncFontUseFlag flag : fnc.getFontUseFlags()) {
        baseXsw.writeRaw(XmlIndenter.getIndent(3));
        baseXsw.writeStartElement("fncFontUseFlag");
        baseXsw.writeCharacters(flag.name());
        baseXsw.writeEndElement();
      }
      baseXsw.writeRaw(indent);
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
        baseXsw.writeRaw(indent);
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
      baseXsw.writeRaw(indent);
      baseXsw.writeStartElement("flags");
      for (LND_LineDescriptor.LND_Flag flag : lnd.getFlags()) {
        baseXsw.writeRaw(XmlIndenter.getIndent(3));
        baseXsw.writeStartElement("lndFlag");
        baseXsw.writeCharacters(flag.name());
        baseXsw.writeEndElement();
      }
      baseXsw.writeRaw(indent);
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
        baseXsw.writeRaw(indent);
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
        baseXsw.writeRaw(indent);
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
      baseXsw.writeEmptyElement("GNOP1_NopOperation");
    } else if (order instanceof GAD_DrawingOrder.GCLINE_LineAtCurrentPosition gcline) {
      writeDrawingOrderWithPoints(writer, gcline, "GCLINE_LineAtCurrentPosition", indent, childIndent);
    } else if (order instanceof GAD_DrawingOrder.GLINE_LineAtGivenPosition gline) {
      writeDrawingOrderWithPoints(writer, gline, "GLINE_LineAtGivenPosition", indent, childIndent);
    } else if (order instanceof GAD_DrawingOrder.GCMRK_MarkerAtCurrentPosition gcmrk) {
      writeDrawingOrderWithPoints(writer, gcmrk, "GCMRK_MarkerAtCurrentPosition", indent, childIndent);
    } else if (order instanceof GAD_DrawingOrder.GMRK_MarkerAtGivenPosition gmrk) {
      writeDrawingOrderWithPoints(writer, gmrk, "GMRK_MarkerAtGivenPosition", indent, childIndent);
    } else if (order instanceof GAD_DrawingOrder.GCFLT_FilletAtCurrentPosition gcflt) {
      writeDrawingOrderWithPoints(writer, gcflt, "GCFLT_FilletAtCurrentPosition", indent, childIndent);
    } else if (order instanceof GAD_DrawingOrder.GFLT_FilletAtGivenPosition gflt) {
      writeDrawingOrderWithPoints(writer, gflt, "GFLT_FilletAtGivenPosition", indent, childIndent);
    } else if (order instanceof GAD_DrawingOrder.GCCBEZ_CubicBezierCurveAtCurrentPosition gccbez) {
      writeDrawingOrderWithPoints(writer, gccbez, "GCCBEZ_CubicBezierCurveAtCurrentPosition", indent, childIndent);
    } else if (order instanceof GAD_DrawingOrder.GCBEZ_CubicBezierCurveAtGivenPosition gcbez) {
      writeDrawingOrderWithPoints(writer, gcbez, "GCBEZ_CubicBezierCurveAtGivenPosition", indent, childIndent);
    } else if (order instanceof GAD_DrawingOrder.GCFARC_FullArcAtCurrentPosition gcfarc) {
      baseXsw.writeEmptyElement("GCFARC_FullArcAtCurrentPosition");
      baseXsw.writeIntAttribute(null, null, "multiplierIntegerPortion", gcfarc.getMultiplierIntegerPortion());
      baseXsw.writeIntAttribute(null, null, "multiplierFractionalPortion", gcfarc.getMultiplierFractionalPortion());
    } else if (order instanceof GAD_DrawingOrder.GCOMT_Comment gcomt) {
      baseXsw.writeStartElement("GCOMT_Comment");
      baseXsw.writeIntAttribute(null, null, "lengthOfFollowingData", gcomt.getLengthOfFollowingData());
      if (gcomt.getText() != null) {
        baseXsw.writeAttribute("text", gcomt.getText());
      }
      if (gcomt.comment != null) {
        writeBinaryElement(baseXsw, childIndent, "comment", gcomt.comment);
      }
      baseXsw.writeRaw(indent);
      baseXsw.writeEndElement();
    } else if (order instanceof GAD_DrawingOrder.GSCP_SetCurrentPosition gcp) {
      baseXsw.writeEmptyElement("GSCP_SetCurrentPosition");
      baseXsw.writeIntAttribute(null, null, "coordinateX", gcp.getCoordinateX());
      baseXsw.writeIntAttribute(null, null, "coordinateY", gcp.getCoordinateY());
    } else if (order instanceof GAD_DrawingOrder.GSCOL_SetColor gsc) {
      if (gsc.getColor() != null) {
        baseXsw.writeEmptyElement("GSCOL_SetColor");
        baseXsw.writeAttribute("color", gsc.getColor().name());
      } else {
        baseXsw.writeEmptyElement("GSCOL_SetColor");
      }
    } else if (order instanceof GAD_DrawingOrder.GSCS_SetCharacterSet gscs) {
      baseXsw.writeEmptyElement("GSCS_SetCharacterSet");
      baseXsw.writeIntAttribute(null, null, "characterSetLocalID", gscs.getCharacterSetLocalID());
    } else if (order instanceof GAD_DrawingOrder.GSPS_SetPatternSet gsps) {
      baseXsw.writeEmptyElement("GSPS_SetPatternSet");
      baseXsw.writeIntAttribute(null, null, "patternLocalID", gsps.patternLocalID);
    } else if (order instanceof GAD_DrawingOrder.GSMX_SetMix gsmx) {
      baseXsw.writeEmptyElement("GSMX_SetMix");
      baseXsw.writeIntAttribute(null, null, "mixMode", gsmx.mixMode);
    } else if (order instanceof GAD_DrawingOrder.GSBMX_SetBackgroundMix gsbmx) {
      baseXsw.writeEmptyElement("GSBMX_SetBackgroundMix");
      baseXsw.writeIntAttribute(null, null, "mixMode", gsbmx.mixMode);
    } else if (order instanceof GAD_DrawingOrder.GSFLW_SetFractionLineWidth gsflw) {
      baseXsw.writeEmptyElement("GSFLW_SetFractionLineWidth");
      baseXsw.writeIntAttribute(null, null, "integralMultiplier", gsflw.integralMultiplier);
      baseXsw.writeIntAttribute(null, null, "fractionalMultiplier", gsflw.fractionalMultiplier);
    } else if (order instanceof GAD_DrawingOrder.GSLT_SetLineType gslt) {
      baseXsw.writeEmptyElement("GSLT_SetLineType");
      baseXsw.writeIntAttribute(null, null, "lineType", gslt.lineType);
    } else if (order instanceof GAD_DrawingOrder.GSPIK_SetPickIdentifier gspik) {
      baseXsw.writeEmptyElement("GSPIK_SetPickIdentifier");
      baseXsw.writeIntAttribute(null, null, "pickIdentifier", gspik.pickIdentifier);
    } else if (order instanceof GAD_DrawingOrder.GSGCH_SegmentCharacteristics gsgch) {
      baseXsw.writeStartElement("GSGCH_SegmentCharacteristics");
      baseXsw.writeIntAttribute(null, null, "lengthOfFollowingData", gsgch.lengthOfFollowingData);
      baseXsw.writeIntAttribute(null, null, "identificationCode", gsgch.getIdentificationCode());
      if (gsgch.getParameters() != null) {
        writeBinaryElement(baseXsw, childIndent, "parameters", gsgch.getParameters());
      }
      baseXsw.writeRaw(indent);
      baseXsw.writeEndElement();
    } else if (order instanceof GAD_DrawingOrder.GSECOL_SetExtendedColor gsecol) {
      if (gsecol.getColor() != null) {
        baseXsw.writeEmptyElement("GSECOL_SetExtendedColor");
        baseXsw.writeAttribute("color", gsecol.getColor().name());
      } else {
        baseXsw.writeEmptyElement("GSECOL_SetExtendedColor");
      }
    } else if (order instanceof GAD_DrawingOrder.GSCC_SetCharacterCell gscc) {
      baseXsw.writeStartElement("GSCC_SetCharacterCell");
      writeElement(baseXsw, childIndent, "lengthOfFollowingData", gscc.lengthOfFollowingData);
      writeElement(baseXsw, childIndent, "widthOfCharacterCellIntegerPart", gscc.getWidthOfCharacterCellIntegerPart());
      writeElement(baseXsw, childIndent, "heightOfCharacterCellIntegerPart", gscc.getHeightOfCharacterCellIntegerPart());
      if (gscc.getWidthOfCharacterCellFractionalPart() != null) {
        writeElement(baseXsw, childIndent, "widthOfCharacterCellFractionalPart", gscc.getWidthOfCharacterCellFractionalPart());
      }
      if (gscc.getHeightOfCharacterCellFractionalPart() != null) {
        writeElement(baseXsw, childIndent, "heightOfCharacterCellFractionalPart", gscc.getHeightOfCharacterCellFractionalPart());
      }
      baseXsw.writeRaw(indent);
      baseXsw.writeEndElement();
    } else if (order instanceof GAD_DrawingOrder.GSCA_SetCharacterAngle gsca) {
      baseXsw.writeStartElement("GSCA_SetCharacterAngle");
      writeElement(baseXsw, childIndent, "lengthOfFollowingData", gsca.lengthOfFollowingData);
      if (gsca.getAnglePoint() != null) {
        baseXsw.writeRaw(childIndent);
        baseXsw.writeStartElement("anglePoint");
        writeElement(baseXsw, childIndent + "  ", "xCoordinate", gsca.getAnglePoint().xCoordinate());
        writeElement(baseXsw, childIndent + "  ", "yCoordinate", gsca.getAnglePoint().yCoordinate());
        baseXsw.writeRaw(childIndent);
        baseXsw.writeEndElement();
      }
      baseXsw.writeRaw(indent);
      baseXsw.writeEndElement();
    } else if (order instanceof GAD_DrawingOrder.GSCH_SetCharacterShear gsch) {
      baseXsw.writeStartElement("GSCH_SetCharacterShear");
      writeElement(baseXsw, childIndent, "lengthOfFollowingData", gsch.lengthOfFollowingData);
      writeElement(baseXsw, childIndent, "dividendOfShearRatio", gsch.getDividendOfShearRatio());
      writeElement(baseXsw, childIndent, "divisorOfShearRatio", gsch.getDivisorOfShearRatio());
      baseXsw.writeRaw(indent);
      baseXsw.writeEndElement();
    } else if (order instanceof GAD_DrawingOrder.GSMC_SetMarkerCell gsmc) {
      baseXsw.writeStartElement("GSMC_SetMarkerCell");
      writeElement(baseXsw, childIndent, "lengthOfFollowingData", gsmc.lengthOfFollowingData);
      writeElement(baseXsw, childIndent, "widthOfMarkerCell", gsmc.getWidthOfMarkerCell());
      writeElement(baseXsw, childIndent, "heightOfMarkerCell", gsmc.getHeightOfMarkerCell());
      baseXsw.writeRaw(indent);
      baseXsw.writeEndElement();
    } else if (order instanceof GAD_DrawingOrder.GSLW_SetLineWidth gslw) {
      baseXsw.writeEmptyElement("GSLW_SetLineWidth");
      baseXsw.writeIntAttribute(null, null, "lineWidth", gslw.lineWidth);
    } else if (order instanceof GAD_DrawingOrder.GSLE_SetLineEnd gsle) {
      if (gsle.lineEnd != null) {
        baseXsw.writeEmptyElement("GSLE_SetLineEnd");
        baseXsw.writeAttribute("lineEnd", gsle.lineEnd.name());
      } else {
        baseXsw.writeEmptyElement("GSLE_SetLineEnd");
      }
    } else if (order instanceof GAD_DrawingOrder.GSLJ_SetLineJoin gslj) {
      if (gslj.lineJoin != null) {
        baseXsw.writeEmptyElement("GSLJ_SetLineJoin");
        baseXsw.writeAttribute("lineJoin", gslj.lineJoin.name());
      } else {
        baseXsw.writeEmptyElement("GSLJ_SetLineJoin");
      }
    } else if (order instanceof GAD_DrawingOrder.GSPT_SetPatternSymbol gspt) {
      baseXsw.writeEmptyElement("GSPT_SetPatternSymbol");
      baseXsw.writeIntAttribute(null, null, "patternSymbolCodePoint", gspt.patternSymbolCodePoint);
    } else if (order instanceof GAD_DrawingOrder.GSMT_SetMarkerSymbol gsmt) {
      baseXsw.writeEmptyElement("GSMT_SetMarkerSymbol");
      baseXsw.writeIntAttribute(null, null, "markerSymbolCodePoint", gsmt.markerSymbolCodePoint);
    } else if (order instanceof GAD_DrawingOrder.GSCR_SetCharacterPrecision gscr) {
      baseXsw.writeEmptyElement("GSCR_SetCharacterPrecision");
      baseXsw.writeIntAttribute(null, null, "characterPrecision", gscr.characterPrecision);
    } else if (order instanceof GAD_DrawingOrder.GSCD_SetCharacterDirection gscd) {
      baseXsw.writeEmptyElement("GSCD_SetCharacterDirection");
      baseXsw.writeIntAttribute(null, null, "characterDirection", gscd.characterDirection);
    } else if (order instanceof GAD_DrawingOrder.GSMP_SetMarkerPrecision gsmp) {
      baseXsw.writeEmptyElement("GSMP_SetMarkerPrecision");
      baseXsw.writeIntAttribute(null, null, "markerPrecision", gsmp.markerPrecision);
    } else if (order instanceof GAD_DrawingOrder.GSMS_SetMarkerSet gsms) {
      baseXsw.writeEmptyElement("GSMS_SetMarkerSet");
      baseXsw.writeIntAttribute(null, null, "markerSetLocalID", gsms.markerSetLocalID);
    } else if (order instanceof GAD_DrawingOrder.GSCLT_SetCustomLineType gsclt) {
      baseXsw.writeStartElement("GSCLT_SetCustomLineType");
      writeElement(baseXsw, childIndent, "lengthOfFollowingData", gsclt.lengthOfFollowingData);
      if (gsclt.repeatingGroups != null) {
        baseXsw.writeRaw(childIndent);
        baseXsw.writeStartElement("repeatingGroups");
        String rgIndent = childIndent + "  ";
        for (GAD_DrawingOrder.GSCLT_SetCustomLineType.DashMoveRepeatingGroup rg : gsclt.repeatingGroups) {
          baseXsw.writeRaw(rgIndent);
          baseXsw.writeStartElement("DashMoveRepeatingGroup");
          writeElement(baseXsw, rgIndent + "  ", "dashInteger", rg.dashInteger());
          writeElement(baseXsw, rgIndent + "  ", "dashFractional", rg.dashFractional());
          writeElement(baseXsw, rgIndent + "  ", "moveInteger", rg.moveInteger());
          writeElement(baseXsw, rgIndent + "  ", "moveFractional", rg.moveFractional());
          baseXsw.writeRaw(rgIndent);
          baseXsw.writeEndElement();
        }
        baseXsw.writeRaw(childIndent);
        baseXsw.writeEndElement();
      }
      baseXsw.writeRaw(indent);
      baseXsw.writeEndElement();
    } else if (order instanceof GAD_DrawingOrder.GSAP_SetArcParameters gsap) {
      baseXsw.writeStartElement("GSAP_SetArcParameters");
      writeElement(baseXsw, childIndent, "lengthOfFollowingData", gsap.lengthOfFollowingData);
      writeElement(baseXsw, childIndent, "arcTransformP", gsap.getArcTransformP());
      writeElement(baseXsw, childIndent, "arcTransformQ", gsap.getArcTransformQ());
      writeElement(baseXsw, childIndent, "arcTransformR", gsap.getArcTransformR());
      writeElement(baseXsw, childIndent, "arcTransformS", gsap.getArcTransformS());
      baseXsw.writeRaw(indent);
      baseXsw.writeEndElement();
    } else if (order instanceof GAD_DrawingOrder.GSPRP_SetPatternReferencePoint gsprp) {
      baseXsw.writeStartElement("GSPRP_SetPatternReferencePoint");
      writeElement(baseXsw, childIndent, "lengthOfFollowingData", gsprp.lengthOfFollowingData);
      writeElement(baseXsw, childIndent, "flags", gsprp.getFlags());
      writeElement(baseXsw, childIndent, "reserved3", gsprp.getReserved3());
      writeElement(baseXsw, childIndent, "coordinateX", gsprp.getCoordinateX());
      writeElement(baseXsw, childIndent, "coordinateY", gsprp.getCoordinateY());
      baseXsw.writeRaw(indent);
      baseXsw.writeEndElement();
    } else if (order instanceof GAD_DrawingOrder.GCPARC_PartialArcAtCurrentPosition gcparc) {
      baseXsw.writeStartElement("GCPARC_PartialArcAtCurrentPosition");
      writeElement(baseXsw, childIndent, "lengthOfFollowingData", gcparc.lengthOfFollowingData);
      if (gcparc.getArcCenter() != null) {
        baseXsw.writeRaw(childIndent);
        baseXsw.writeStartElement("arcCenter");
        writeElement(baseXsw, childIndent + "  ", "xCoordinate", gcparc.getArcCenter().xCoordinate());
        writeElement(baseXsw, childIndent + "  ", "yCoordinate", gcparc.getArcCenter().yCoordinate());
        baseXsw.writeRaw(childIndent);
        baseXsw.writeEndElement();
      }
      writeElement(baseXsw, childIndent, "multiplierIntegerPortion", gcparc.getMultiplierIntegerPortion());
      writeElement(baseXsw, childIndent, "multiplierFractionalPortion", gcparc.getMultiplierFractionalPortion());
      writeElement(baseXsw, childIndent, "startAngle", gcparc.getStartAngle());
      writeElement(baseXsw, childIndent, "sweepAngle", gcparc.getSweepAngle());
      baseXsw.writeRaw(indent);
      baseXsw.writeEndElement();
    } else if (order instanceof GAD_DrawingOrder.GSPCOL_SetProcessColor gspcol) {
      baseXsw.writeStartElement("GSPCOL_SetProcessColor");
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
        writeBinaryElement(baseXsw, childIndent, "colorValue", gspcol.getColorValue());
      }
      baseXsw.writeRaw(indent);
      baseXsw.writeEndElement();
    } else if (order instanceof GAD_DrawingOrder.GFARC_FullArcAtGivenPosition gfarc) {
      baseXsw.writeStartElement("GFARC_FullArcAtGivenPosition");
      writeElement(baseXsw, childIndent, "lengthOfFollowingData", gfarc.lengthOfFollowingData);
      if (gfarc.getArcCenter() != null) {
        baseXsw.writeRaw(childIndent);
        baseXsw.writeStartElement("arcCenter");
        writeElement(baseXsw, childIndent + "  ", "xCoordinate", gfarc.getArcCenter().xCoordinate());
        writeElement(baseXsw, childIndent + "  ", "yCoordinate", gfarc.getArcCenter().yCoordinate());
        baseXsw.writeRaw(childIndent);
        baseXsw.writeEndElement();
      }
      writeElement(baseXsw, childIndent, "multiplierIntegerPortion", gfarc.getMultiplierIntegerPortion());
      writeElement(baseXsw, childIndent, "multiplierFractionalPortion", gfarc.getMultiplierFractionalPortion());
      baseXsw.writeRaw(indent);
      baseXsw.writeEndElement();
    } else if (order instanceof GAD_DrawingOrder.GCBIMG_BeginImageAtCurrentPosition gcbimg) {
      baseXsw.writeStartElement("GCBIMG_BeginImageAtCurrentPosition");
      writeElement(baseXsw, childIndent, "lengthOfFollowingData", gcbimg.lengthOfFollowingData);
      writeElement(baseXsw, childIndent, "formatOfImageData", gcbimg.getFormatOfImageData());
      writeElement(baseXsw, childIndent, "reserved3", gcbimg.getReserved3());
      writeElement(baseXsw, childIndent, "widthOfImageInImagePoints", gcbimg.getWidthOfImageInImagePoints());
      writeElement(baseXsw, childIndent, "heightOfImageInImagePoints", gcbimg.getHeightOfImageInImagePoints());
      baseXsw.writeRaw(indent);
      baseXsw.writeEndElement();
    } else if (order instanceof GAD_DrawingOrder.GBIMG_BeginImageAtGivenPosition gbimg) {
      baseXsw.writeStartElement("GBIMG_BeginImageAtGivenPosition");
      writeElement(baseXsw, childIndent, "lengthOfFollowingData", gbimg.lengthOfFollowingData);
      if (gbimg.getOrigin() != null) {
        baseXsw.writeRaw(childIndent);
        baseXsw.writeStartElement("origin");
        writeElement(baseXsw, childIndent + "  ", "xCoordinate", gbimg.getOrigin().xCoordinate());
        writeElement(baseXsw, childIndent + "  ", "yCoordinate", gbimg.getOrigin().yCoordinate());
        baseXsw.writeRaw(childIndent);
        baseXsw.writeEndElement();
      }
      writeElement(baseXsw, childIndent, "formatOfImageData", gbimg.getFormatOfImageData());
      writeElement(baseXsw, childIndent, "reserved3", gbimg.getReserved3());
      writeElement(baseXsw, childIndent, "widthOfImageInImagePoints", gbimg.getWidthOfImageInImagePoints());
      writeElement(baseXsw, childIndent, "heightOfImageInImagePoints", gbimg.getHeightOfImageInImagePoints());
      baseXsw.writeRaw(indent);
      baseXsw.writeEndElement();
    } else if (order instanceof GAD_DrawingOrder.GPARC_PartialArcAtGivenPosition gparc) {
      baseXsw.writeStartElement("GPARC_PartialArcAtGivenPosition");
      writeElement(baseXsw, childIndent, "lengthOfFollowingData", gparc.lengthOfFollowingData);
      if (gparc.getLineStartPoint() != null) {
        baseXsw.writeRaw(childIndent);
        baseXsw.writeStartElement("lineStartPoint");
        writeElement(baseXsw, childIndent + "  ", "xCoordinate", gparc.getLineStartPoint().xCoordinate());
        writeElement(baseXsw, childIndent + "  ", "yCoordinate", gparc.getLineStartPoint().yCoordinate());
        baseXsw.writeRaw(childIndent);
        baseXsw.writeEndElement();
      }
      if (gparc.getArcCenter() != null) {
        baseXsw.writeRaw(childIndent);
        baseXsw.writeStartElement("arcCenter");
        writeElement(baseXsw, childIndent + "  ", "xCoordinate", gparc.getArcCenter().xCoordinate());
        writeElement(baseXsw, childIndent + "  ", "yCoordinate", gparc.getArcCenter().yCoordinate());
        baseXsw.writeRaw(childIndent);
        baseXsw.writeEndElement();
      }
      writeElement(baseXsw, childIndent, "multiplierIntegerPortion", gparc.getMultiplierIntegerPortion());
      writeElement(baseXsw, childIndent, "multiplierFractionalPortion", gparc.getMultiplierFractionalPortion());
      writeElement(baseXsw, childIndent, "startAngle", gparc.getStartAngle());
      writeElement(baseXsw, childIndent, "sweepAngle", gparc.getSweepAngle());
      baseXsw.writeRaw(indent);
      baseXsw.writeEndElement();
    } else if (order instanceof GAD_DrawingOrder.GEXO_ExtendedOrder gexo) {
      baseXsw.writeStartElement("GEXO_ExtendedOrder");
      writeElement(baseXsw, childIndent, "qualifier", gexo.qualifier);
      writeElement(baseXsw, childIndent, "lengthOfFollowingData", gexo.lengthOfFollowingData);
      if (gexo.getExtendedData() != null) {
        writeBinaryElement(baseXsw, childIndent, "extendedData", gexo.getExtendedData());
      }
      baseXsw.writeRaw(indent);
      baseXsw.writeEndElement();
    } else if (order instanceof GAD_DrawingOrder.GCCHST_CharacterStringAtCurrentPosition gcchst) {
      baseXsw.writeStartElement("GCCHST_CharacterStringAtCurrentPosition");
      writeElement(baseXsw, childIndent, "text", gcchst.getText());
      baseXsw.writeRaw(indent);
      baseXsw.writeEndElement();
    } else if (order instanceof GAD_DrawingOrder.GCHST_CharacterStringAtGivenPosition gchst) {
      baseXsw.writeStartElement("GCHST_CharacterStringAtGivenPosition");
      if (gchst.getOriginPoint() != null) {
        baseXsw.writeRaw(childIndent);
        baseXsw.writeStartElement("originPoint");
        writeElement(baseXsw, childIndent + "  ", "xCoordinate", gchst.getOriginPoint().xCoordinate());
        writeElement(baseXsw, childIndent + "  ", "yCoordinate", gchst.getOriginPoint().yCoordinate());
        baseXsw.writeRaw(childIndent);
        baseXsw.writeEndElement();
      }
      writeElement(baseXsw, childIndent, "text", gchst.getText());
      baseXsw.writeRaw(indent);
      baseXsw.writeEndElement();
    } else if (order instanceof GAD_DrawingOrder.GESEG_EndSegment) {
      baseXsw.writeEmptyElement("GESEG_EndSegment");
    } else if (order instanceof GAD_DrawingOrder.GEPROL_EndProlog geprol) {
      baseXsw.writeEmptyElement("GEPROL_EndProlog");
      baseXsw.writeIntAttribute(null, null, "reserved0", geprol.getReserved0());
    } else if (order instanceof GAD_DrawingOrder.GBSEG_BeginSegment gbseg) {
      baseXsw.writeStartElement("GBSEG_BeginSegment");
      if (gbseg.getNameOfSegment() != null) {
        baseXsw.writeAttribute("nameOfSegment", gbseg.getNameOfSegment());
      }
      if (gbseg.getText() != null) {
        baseXsw.writeAttribute("text", gbseg.getText());
      }
      if (gbseg.getDrawingOrders() != null) {
        for (GAD_DrawingOrder childOrder : gbseg.getDrawingOrders()) {
          baseXsw.writeRaw(childIndent);
          writeDrawingOrderDirectly(writer, childOrder, childIndent);
        }
      }
      baseXsw.writeRaw(indent);
      baseXsw.writeEndElement();
    } else if (order instanceof GAD_DrawingOrder.GBCP_BeginCustomPattern gbcp) {
      baseXsw.writeStartElement("GBCP_BeginCustomPattern");
      writeElement(baseXsw, childIndent, "lengthOfFollowingData", gbcp.lengthOfFollowingData);
      writeElement(baseXsw, childIndent, "reserved2_3", gbcp.reserved2_3);
      writeElement(baseXsw, childIndent, "flags", gbcp.flags);
      writeElement(baseXsw, childIndent, "patternSet", gbcp.patternSet);
      writeElement(baseXsw, childIndent, "patternSymbol", gbcp.patternSymbol);
      writeElement(baseXsw, childIndent, "xLeftWindow", gbcp.xLeftWindow);
      writeElement(baseXsw, childIndent, "xRightWindow", gbcp.xRightWindow);
      writeElement(baseXsw, childIndent, "yBottomWindow", gbcp.yBottomWindow);
      writeElement(baseXsw, childIndent, "yTopWindow", gbcp.yTopWindow);
      baseXsw.writeRaw(indent);
      baseXsw.writeEndElement();
    } else if (order instanceof GAD_DrawingOrder.GDPT_DeletePattern gdpt) {
      baseXsw.writeStartElement("GDPT_DeletePattern");
      writeElement(baseXsw, childIndent, "lengthOfFollowingData", gdpt.lengthOfFollowingData);
      writeElement(baseXsw, childIndent, "reserved2_3", gdpt.reserved2_3);
      writeElement(baseXsw, childIndent, "patternSet", gdpt.patternSet);
      if (gdpt.patternSymbol != null) {
        writeElement(baseXsw, childIndent, "patternSymbol", gdpt.patternSymbol);
      }
      baseXsw.writeRaw(indent);
      baseXsw.writeEndElement();
    } else if (order instanceof GAD_DrawingOrder.GECP_EndCustomPattern gecp) {
      baseXsw.writeEmptyElement("GECP_EndCustomPattern");
      baseXsw.writeIntAttribute(null, null, "reserved0", gecp.getReserved0());
    } else if (order instanceof GAD_DrawingOrder.GBAR_BeginArea gbar) {
      baseXsw.writeEmptyElement("GBAR_BeginArea");
      baseXsw.writeIntAttribute(null, null, "internalFlags", gbar.getInternalFlags());
    } else if (order instanceof GAD_DrawingOrder.GEAR_EndArea gear) {
      String text = gear.getText();
      if (text != null && !text.isEmpty()) {
        baseXsw.writeEmptyElement("GEAR_EndArea");
        baseXsw.writeAttribute("text", text);
      } else {
        baseXsw.writeEmptyElement("GEAR_EndArea");
      }
    } else if (order instanceof GAD_DrawingOrder.GIMD_ImageData gimd) {
      baseXsw.writeStartElement("GIMD_ImageData");
      if (gimd.getImageData() != null) {
        writeBinaryElement(baseXsw, childIndent, "imageData", gimd.getImageData());
      }
      baseXsw.writeRaw(indent);
      baseXsw.writeEndElement();
    } else if (order instanceof GAD_DrawingOrder.GEIMG_EndImage geimg) {
      baseXsw.writeStartElement("GEIMG_EndImage");
      baseXsw.writeIntAttribute(null, null, "lengthOfFollowingData", geimg.getLengthOfFollowingData());
      if (geimg.reservedData != null) {
        writeBinaryElement(baseXsw, childIndent, "reservedData", geimg.reservedData);
      }
      baseXsw.writeRaw(indent);
      baseXsw.writeEndElement();
    } else if (order instanceof GAD_DrawingOrder.GCBOX_BoxAtCurrentPosition gcbox) {
      baseXsw.writeStartElement("GCBOX_BoxAtCurrentPosition");
      writeElement(baseXsw, childIndent, "reserved2_3", gcbox.getReserved2_3());
      if (gcbox.getDiagonalCorner() != null) {
        baseXsw.writeRaw(childIndent);
        baseXsw.writeStartElement("diagonalCorner");
        writeElement(baseXsw, childIndent + "  ", "xCoordinate", gcbox.getDiagonalCorner().xCoordinate());
        writeElement(baseXsw, childIndent + "  ", "yCoordinate", gcbox.getDiagonalCorner().yCoordinate());
        baseXsw.writeRaw(childIndent);
        baseXsw.writeEndElement();
      }
      if (gcbox.getxAxisLengthForRoundCorner() != null) {
        writeElement(baseXsw, childIndent, "xAxisLengthForRoundCorner", gcbox.getxAxisLengthForRoundCorner());
      }
      if (gcbox.getyAxisLengthForRoundCorner() != null) {
        writeElement(baseXsw, childIndent, "yAxisLengthForRoundCorner", gcbox.getyAxisLengthForRoundCorner());
      }
      baseXsw.writeRaw(indent);
      baseXsw.writeEndElement();
    } else if (order instanceof GAD_DrawingOrder.GBOX_BoxAtGivenPosition gbox) {
      baseXsw.writeStartElement("GBOX_BoxAtGivenPosition");
      writeElement(baseXsw, childIndent, "reserved2_3", gbox.getReserved2_3());
      if (gbox.getFirstCorner() != null) {
        baseXsw.writeRaw(childIndent);
        baseXsw.writeStartElement("firstCorner");
        writeElement(baseXsw, childIndent + "  ", "xCoordinate", gbox.getFirstCorner().xCoordinate());
        writeElement(baseXsw, childIndent + "  ", "yCoordinate", gbox.getFirstCorner().yCoordinate());
        baseXsw.writeRaw(childIndent);
        baseXsw.writeEndElement();
      }
      if (gbox.getDiagonalCorner() != null) {
        baseXsw.writeRaw(childIndent);
        baseXsw.writeStartElement("diagonalCorner");
        writeElement(baseXsw, childIndent + "  ", "xCoordinate", gbox.getDiagonalCorner().xCoordinate());
        writeElement(baseXsw, childIndent + "  ", "yCoordinate", gbox.getDiagonalCorner().yCoordinate());
        baseXsw.writeRaw(childIndent);
        baseXsw.writeEndElement();
      }
      if (gbox.getxAxisLengthForRoundCorner() != null) {
        writeElement(baseXsw, childIndent, "xAxisLengthForRoundCorner", gbox.getxAxisLengthForRoundCorner());
      }
      if (gbox.getyAxisLengthForRoundCorner() != null) {
        writeElement(baseXsw, childIndent, "yAxisLengthForRoundCorner", gbox.getyAxisLengthForRoundCorner());
      }
      baseXsw.writeRaw(indent);
      baseXsw.writeEndElement();
    } else if (order instanceof GAD_DrawingOrder.GCRLINE_RelativeLineAtCurrentPosition gcrline) {
      baseXsw.writeStartElement("GCRLINE_RelativeLineAtCurrentPosition");
      if (gcrline.relativeOffsets != null) {
        baseXsw.writeRaw(childIndent);
        baseXsw.writeStartElement("relativeOffsets");
        String offsetIndent = childIndent + "  ";
        for (GAD_DrawingOrder.GOCA_RelativePoint rp : gcrline.relativeOffsets) {
          baseXsw.writeRaw(offsetIndent);
          baseXsw.writeStartElement("GOCA_RelativePoint");
          writeElement(baseXsw, offsetIndent + "  ", "xOffset", rp.xOffset());
          writeElement(baseXsw, offsetIndent + "  ", "yOffset", rp.yOffset());
          baseXsw.writeRaw(offsetIndent);
          baseXsw.writeEndElement();
        }
        baseXsw.writeRaw(childIndent);
        baseXsw.writeEndElement();
      }
      baseXsw.writeRaw(indent);
      baseXsw.writeEndElement();
    } else if (order instanceof GAD_DrawingOrder.GRLINE_RelativeLineAtGivenPosition grline) {
      baseXsw.writeStartElement("GRLINE_RelativeLineAtGivenPosition");
      if (grline.startPoint != null) {
        baseXsw.writeRaw(childIndent);
        baseXsw.writeStartElement("startPoint");
        writeElement(baseXsw, childIndent + "  ", "xCoordinate", grline.startPoint.xCoordinate());
        writeElement(baseXsw, childIndent + "  ", "yCoordinate", grline.startPoint.yCoordinate());
        baseXsw.writeRaw(childIndent);
        baseXsw.writeEndElement();
      }
      if (grline.relativeOffsets != null) {
        baseXsw.writeRaw(childIndent);
        baseXsw.writeStartElement("relativeOffsets");
        String offsetIndent = childIndent + "  ";
        for (GAD_DrawingOrder.GOCA_RelativePoint rp : grline.relativeOffsets) {
          baseXsw.writeRaw(offsetIndent);
          baseXsw.writeStartElement("GOCA_RelativePoint");
          writeElement(baseXsw, offsetIndent + "  ", "xOffset", rp.xOffset());
          writeElement(baseXsw, offsetIndent + "  ", "yOffset", rp.yOffset());
          baseXsw.writeRaw(offsetIndent);
          baseXsw.writeEndElement();
        }
        baseXsw.writeRaw(childIndent);
        baseXsw.writeEndElement();
      }
      baseXsw.writeRaw(indent);
      baseXsw.writeEndElement();
    } else if (order instanceof GAD_DrawingOrder.GLGD_LinearGradient glgd) {
      baseXsw.writeStartElement("GLGD_LinearGradient");
      writeElement(baseXsw, childIndent, "qualifier", glgd.qualifier);
      writeElement(baseXsw, childIndent, "lengthOfFollowingData", glgd.lengthOfFollowingData);
      writeElement(baseXsw, childIndent, "reserved4_5", glgd.reserved4_5);
      writeElement(baseXsw, childIndent, "patternSet", glgd.patternSet);
      writeElement(baseXsw, childIndent, "patternSymbol", glgd.patternSymbol);
      writeElement(baseXsw, childIndent, "xStart", glgd.xStart);
      writeElement(baseXsw, childIndent, "yStart", glgd.yStart);
      writeElement(baseXsw, childIndent, "xEnd", glgd.xEnd);
      writeElement(baseXsw, childIndent, "yEnd", glgd.yEnd);
      if (glgd.startColorSpec != null) {
        baseXsw.writeRaw(childIndent);
        writeColorSpecification(baseXsw, glgd.startColorSpec, childIndent, "startColorSpec");
      }
      if (glgd.endColorValue != null) {
        writeBinaryElement(baseXsw, childIndent, "endColorValue", glgd.endColorValue);
      }
      writeElement(baseXsw, childIndent, "outsideStart", glgd.outsideStart);
      writeElement(baseXsw, childIndent, "outsideEnd", glgd.outsideEnd);
      if (glgd.colorStops != null) {
        baseXsw.writeRaw(childIndent);
        baseXsw.writeStartElement("colorStops");
        for (GAD_DrawingOrder.ColorStop stop : glgd.colorStops) {
          writeColorStop(baseXsw, stop, childIndent + "  ");
        }
        baseXsw.writeRaw(childIndent);
        baseXsw.writeEndElement();
      }
      baseXsw.writeRaw(indent);
      baseXsw.writeEndElement();
    } else if (order instanceof GAD_DrawingOrder.GRGD_RadialGradient grgd) {
      baseXsw.writeStartElement("GRGD_RadialGradient");
      writeElement(baseXsw, childIndent, "qualifier", grgd.qualifier);
      writeElement(baseXsw, childIndent, "lengthOfFollowingData", grgd.lengthOfFollowingData);
      writeElement(baseXsw, childIndent, "reserved4_5", grgd.reserved4_5);
      writeElement(baseXsw, childIndent, "patternSet", grgd.patternSet);
      writeElement(baseXsw, childIndent, "patternSymbol", grgd.patternSymbol);
      writeElement(baseXsw, childIndent, "xStart", grgd.xStart);
      writeElement(baseXsw, childIndent, "yStart", grgd.yStart);
      writeElement(baseXsw, childIndent, "mhStart", grgd.mhStart);
      writeElement(baseXsw, childIndent, "mfrStart", grgd.mfrStart);
      writeElement(baseXsw, childIndent, "xEnd", grgd.xEnd);
      writeElement(baseXsw, childIndent, "yEnd", grgd.yEnd);
      writeElement(baseXsw, childIndent, "mhEnd", grgd.mhEnd);
      writeElement(baseXsw, childIndent, "mfrEnd", grgd.mfrEnd);
      if (grgd.startColorSpec != null) {
        baseXsw.writeRaw(childIndent);
        writeColorSpecification(baseXsw, grgd.startColorSpec, childIndent, "startColorSpec");
      }
      if (grgd.endColorValue != null) {
        writeBinaryElement(baseXsw, childIndent, "endColorValue", grgd.endColorValue);
      }
      writeElement(baseXsw, childIndent, "outsideStart", grgd.outsideStart);
      writeElement(baseXsw, childIndent, "outsideEnd", grgd.outsideEnd);
      if (grgd.colorStops != null) {
        baseXsw.writeRaw(childIndent);
        baseXsw.writeStartElement("colorStops");
        for (GAD_DrawingOrder.ColorStop stop : grgd.colorStops) {
          writeColorStop(baseXsw, stop, childIndent + "  ");
        }
        baseXsw.writeRaw(childIndent);
        baseXsw.writeEndElement();
      }
      baseXsw.writeRaw(indent);
      baseXsw.writeEndElement();
    } else if (order instanceof GAD_DrawingOrder.DrawingOrder_HasPoints dohp) {
      String rootName = MnemonicPerformanceMonitor.getSimpleName(order.getClass());
      baseXsw.writeStartElement(rootName);
      if (dohp.getPoints() != null) {
        baseXsw.writeRaw(childIndent);
        baseXsw.writeStartElement("points");
        String pointIndent = childIndent + "  ";
        for (GAD_DrawingOrder.GOCA_Point p : dohp.getPoints()) {
          baseXsw.writeRaw(pointIndent);
          baseXsw.writeStartElement("GOCA_Point");
          writeElement(baseXsw, pointIndent + "  ", "xCoordinate", p.xCoordinate());
          writeElement(baseXsw, pointIndent + "  ", "yCoordinate", p.yCoordinate());
          baseXsw.writeRaw(pointIndent);
          baseXsw.writeEndElement();
        }
        baseXsw.writeRaw(childIndent);
        baseXsw.writeEndElement();
      }
      baseXsw.writeRaw(indent);
      baseXsw.writeEndElement();
    } else {
      JacksonXmlMapperProvider.getCachedWriter(order.getClass(), useWoodstox, true).writeValue(baseFragmentGenerator, order);
    }
  }

  private void writeIpdSegmentDirectly(XMLStreamWriter2 writer, IPD_Segment segment, String indent) throws Exception {
    String childIndent = indent + "  ";
    if (segment instanceof IPD_Segment.ImageData id) {
      baseXsw.writeStartElement("ImageData");
      if (id.getImageData() != null) {
        writeBinaryElement(baseXsw, childIndent, "imageData", id.getImageData());
      }
      baseXsw.writeRaw(indent);
      baseXsw.writeEndElement();
    } else if (segment instanceof IPD_Segment.BandImageData bid) {
      baseXsw.writeStartElement("BandImageData");
      writeElement(baseXsw, childIndent, "bandNumber", bid.getBandNumber());
      if (bid.getBandData() != null) {
        writeBinaryElement(baseXsw, childIndent, "bandData", bid.getBandData());
      }
      baseXsw.writeRaw(indent);
      baseXsw.writeEndElement();
    } else if (segment instanceof IPD_Segment.BeginImageContent bic) {
      baseXsw.writeStartElement("BeginImageContent");
      writeElement(baseXsw, childIndent, "objectType", bic.getObjectType());
      baseXsw.writeRaw(indent);
      baseXsw.writeEndElement();
    } else if (segment instanceof IPD_Segment.EndImageContent) {
      baseXsw.writeEmptyElement("EndImageContent");
    } else if (segment instanceof IPD_Segment.ImageSize is) {
      baseXsw.writeStartElement("ImageSize");
      if (is.getUnitBase() != null) {
        writeElement(baseXsw, childIndent, "unitBase", is.getUnitBase().name());
      }
      writeElement(baseXsw, childIndent, "xUnitsPerUnitBase", is.getxUnitsPerUnitBase());
      writeElement(baseXsw, childIndent, "yUnitsPerUnitBase", is.getyUnitsPerUnitBase());
      writeElement(baseXsw, childIndent, "xImageSize", is.getxImageSize());
      writeElement(baseXsw, childIndent, "yImageSize", is.getyImageSize());
      baseXsw.writeRaw(indent);
      baseXsw.writeEndElement();
    } else if (segment instanceof IPD_Segment.ImageEncoding ie) {
      baseXsw.writeStartElement("ImageEncoding");
      if (ie.getCompressionAlgorithm() != null) {
        writeElement(baseXsw, childIndent, "compressionAlgorithm", ie.getCompressionAlgorithm().name());
      }
      if (ie.getRecordingAlgorithm() != null) {
        writeElement(baseXsw, childIndent, "recordingAlgorithm", ie.getRecordingAlgorithm().name());
      }
      if (ie.getBitOrder() != null) {
        writeElement(baseXsw, childIndent, "bitOrder", ie.getBitOrder().name());
      }
      baseXsw.writeRaw(indent);
      baseXsw.writeEndElement();
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
        baseXsw.writeRaw(indent);
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
        baseXsw.writeRaw(indent);
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
      baseXsw.writeRaw(indent);
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
      baseXsw.writeRaw(indent);
      baseXsw.writeEndElement();
    }
    XmlIndenter.writeIndent(baseXsw, 1);
    baseXsw.writeEndElement();
    MnemonicPerformanceMonitor.endWrite();
  }

  private void writeIddSelfDefiningFieldDirectly(XMLStreamWriter2 writer, IDD_SelfDefiningField sdf, String indent) throws Exception {
    String childIndent = indent + "  ";
    if (sdf instanceof IDD_SelfDefiningField.SetBilevelImageColor sbic) {
      baseXsw.writeStartElement("SetBilevelImageColor");
      writeElement(baseXsw, childIndent, "applicabilityArea", sbic.getApplicabilityArea());
      if (sbic.getColor() != null) {
        writeElement(baseXsw, childIndent, "color", sbic.getColor().name());
      }
      baseXsw.writeRaw(indent);
      baseXsw.writeEndElement();
    } else if (sdf instanceof IDD_SelfDefiningField.SetExtendedBilevelImageColor sebic) {
      baseXsw.writeStartElement("SetExtendedBilevelImageColor");
      if (sebic.getColorSpace() != null) {
        writeElement(baseXsw, childIndent, "colorSpace", sebic.getColorSpace().name());
      }
      writeElement(baseXsw, childIndent, "nrOfBitsComponent1", sebic.getNrOfBitsComponent1());
      writeElement(baseXsw, childIndent, "nrOfBitsComponent2", sebic.getNrOfBitsComponent2());
      writeElement(baseXsw, childIndent, "nrOfBitsComponent3", sebic.getNrOfBitsComponent3());
      writeElement(baseXsw, childIndent, "nrOfBitsComponent4", sebic.getNrOfBitsComponent4());
      if (sebic.getColorValue() != null) {
        writeBinaryElement(baseXsw, childIndent, "colorValue", sebic.getColorValue());
      }
      baseXsw.writeRaw(indent);
      baseXsw.writeEndElement();
    } else if (sdf instanceof IDD_SelfDefiningField.IOCAFunctionSetIdentification fsi) {
      baseXsw.writeStartElement("IOCAFunctionSetIdentification");
      writeElement(baseXsw, childIndent, "functionSetCategory", fsi.getFunctionSetCategory());
      if (fsi.getFunctionSetIdentifier() != null) {
        writeElement(baseXsw, childIndent, "functionSetIdentifier", fsi.getFunctionSetIdentifier().name());
      }
      baseXsw.writeRaw(indent);
      baseXsw.writeEndElement();
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
        baseXsw.writeRaw(indent);
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
        baseXsw.writeRaw(indent);
        baseXsw.writeStartElement("mioRepeatingGroup");
        if (rg instanceof RepeatingGroupWithTriplets rgt) {
          if (rgt.getTriplets() != null) {
            for (Triplet t : rgt.getTriplets()) {
              baseXsw.writeRaw(XmlIndenter.getIndent(3));
              writeTriplet(baseXsw, t, XmlIndenter.getIndent(3));
            }
          }
        }
        baseXsw.writeRaw(indent);
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
        baseXsw.writeRaw(indent);
        baseXsw.writeStartElement("mdrRepeatingGroup");
        if (rg instanceof RepeatingGroupWithTriplets rgt) {
          if (rgt.getTriplets() != null) {
            for (Triplet t : rgt.getTriplets()) {
              baseXsw.writeRaw(XmlIndenter.getIndent(3));
              writeTriplet(baseXsw, t, XmlIndenter.getIndent(3));
            }
          }
        }
        baseXsw.writeRaw(indent);
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
        baseXsw.writeRaw(indent);
        baseXsw.writeStartElement("mgoRepeatingGroup");
        if (rg instanceof RepeatingGroupWithTriplets rgt) {
          if (rgt.getTriplets() != null) {
            for (Triplet t : rgt.getTriplets()) {
              baseXsw.writeRaw(XmlIndenter.getIndent(3));
              writeTriplet(baseXsw, t, XmlIndenter.getIndent(3));
            }
          }
        }
        baseXsw.writeRaw(indent);
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
        baseXsw.writeRaw(indent);
        baseXsw.writeStartElement("mpoRepeatingGroup");
        if (rg instanceof RepeatingGroupWithTriplets rgt) {
          if (rgt.getTriplets() != null) {
            for (Triplet t : rgt.getTriplets()) {
              baseXsw.writeRaw(XmlIndenter.getIndent(3));
              writeTriplet(baseXsw, t, XmlIndenter.getIndent(3));
            }
          }
        }
        baseXsw.writeRaw(indent);
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
      baseXsw.writeRaw(indent2);
      baseXsw.writeStartElement("triplets");
      for (Triplet triplet : bdt.getTriplets()) {
        baseXsw.writeRaw(XmlIndenter.getIndent(3));
        writeTriplet(baseXsw, triplet, XmlIndenter.getIndent(3));
      }
      baseXsw.writeRaw(indent2);
      baseXsw.writeEndElement();
    }
    if (bdt.reserved8_9 != null) {
      writeElement(baseXsw, indent2, "reserved8_9", com.mgz.util.UtilCharacterEncoding.bytesToHexString(bdt.reserved8_9));
    }
    if (bdt.getText() != null) {
      writeElement(baseXsw, indent2, "text", bdt.getText());
    }
    baseXsw.writeRaw(indent1);
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
        baseXsw.writeRaw(indent2);
        writeTriplet(baseXsw, triplet, indent2);
      }
    }
    if (bmo.getText() != null) {
      writeElement(baseXsw, indent2, "text", bmo.getText());
    }
    baseXsw.writeRaw(indent1);
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
        baseXsw.writeRaw(indent2);
        writeTriplet(baseXsw, triplet, indent2);
      }
    }
    if (bps.getText() != null) {
      writeElement(baseXsw, indent2, "text", bps.getText());
    }
    baseXsw.writeRaw(indent1);
    baseXsw.writeEndElement();
    MnemonicPerformanceMonitor.endWrite();
  }

  private void writeNameAndTripletsDirectly(com.mgz.afp.base.StructuredFieldBaseNameAndTriplets sf, String rootName) throws Exception {
    if (MnemonicPerformanceMonitor.isEnabled()) {
      String mnemonic = MnemonicPerformanceMonitor.extractMnemonicFromString(rootName);
      MnemonicPerformanceMonitor.startWriteWithMnemonic(mnemonic);
    }
    baseXsw.writeStartElement(rootName);
    String indent2 = XmlIndenter.getIndent(2);
    String indent1 = XmlIndenter.getIndent(1);
    writeElement(baseXsw, indent2, "name", sf.getName());
    if (sf.getTriplets() != null && !sf.getTriplets().isEmpty()) {
      // For SFs extending StructuredFieldBaseNameAndTriplets, Jackson output typically doesn't wrap triplets
      // but they are also often marked XmlTransient and only contribute to text.
      // To maintain legacy manual behavior where they WERE written:
      for (Triplet triplet : sf.getTriplets()) {
        baseXsw.writeRaw(indent2);
        writeTriplet(baseXsw, triplet, indent2);
      }
    }
    if (sf.getText() != null) {
      writeElement(baseXsw, indent2, "text", sf.getText());
    }
    baseXsw.writeRaw(indent1);
    baseXsw.writeEndElement();
    if (MnemonicPerformanceMonitor.isEnabled()) {
      MnemonicPerformanceMonitor.endWrite();
    }
  }

  private void writeTripletsAndTextDirectly(com.mgz.afp.base.StructuredFieldBaseTriplets sf, String rootName) throws Exception {
    if (MnemonicPerformanceMonitor.isEnabled()) {
      String mnemonic = MnemonicPerformanceMonitor.extractMnemonicFromString(rootName);
      MnemonicPerformanceMonitor.startWriteWithMnemonic(mnemonic);
    }
    baseXsw.writeStartElement(rootName);
    String indent2 = XmlIndenter.getIndent(2);
    String indent1 = XmlIndenter.getIndent(1);
    if (sf.getTriplets() != null && !sf.getTriplets().isEmpty()) {
      for (Triplet triplet : sf.getTriplets()) {
        baseXsw.writeRaw(indent2);
        writeTriplet(baseXsw, triplet, indent2);
      }
    }
    if (sf.getText() != null) {
      writeElement(baseXsw, indent2, "text", sf.getText());
    }
    baseXsw.writeRaw(indent1);
    baseXsw.writeEndElement();
    if (MnemonicPerformanceMonitor.isEnabled()) {
      MnemonicPerformanceMonitor.endWrite();
    }
  }

  private void writeNameDirectly(com.mgz.afp.base.StructuredFieldBaseName sf, String rootName) throws Exception {
    if (MnemonicPerformanceMonitor.isEnabled()) {
      String mnemonic = MnemonicPerformanceMonitor.extractMnemonicFromString(rootName);
      MnemonicPerformanceMonitor.startWriteWithMnemonic(mnemonic);
    }
    baseXsw.writeStartElement(rootName);
    String indent2 = XmlIndenter.getIndent(2);
    String indent1 = XmlIndenter.getIndent(1);
    writeElement(baseXsw, indent2, "name", sf.getName());
    if (sf.getText() != null) {
      writeElement(baseXsw, indent2, "text", sf.getText());
    }
    baseXsw.writeRaw(indent1);
    baseXsw.writeEndElement();
    if (MnemonicPerformanceMonitor.isEnabled()) {
      MnemonicPerformanceMonitor.endWrite();
    }
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
    baseXsw.writeStartElement(rootName);
    if (order.getPoints() != null) {
      baseXsw.writeRaw(childIndent);
      baseXsw.writeStartElement("points");
      for (GAD_DrawingOrder.GOCA_Point p : order.getPoints()) {
        baseXsw.writeRaw(childIndent + "  ");
        baseXsw.writeEmptyElement("GOCA_Point");
        baseXsw.writeIntAttribute(null, null, "xCoordinate", p.xCoordinate());
        baseXsw.writeIntAttribute(null, null, "yCoordinate", p.yCoordinate());
      }
      baseXsw.writeRaw(childIndent);
      baseXsw.writeEndElement();
    }
    baseXsw.writeRaw(indent);
    baseXsw.writeEndElement();
  }


  private void writeElement(XMLStreamWriter2 writer, String indent, String name, String value) throws Exception {
    if (value != null) {
      baseXsw.writeRaw(indent);
      baseXsw.writeStartElement(name);
      baseXsw.writeCharacters(value);
      baseXsw.writeEndElement();
    }
  }

  private void writeElement(XMLStreamWriter2 writer, String indent, String name, int value) throws Exception {
    baseXsw.writeRaw(indent);
    baseXsw.writeStartElement(name);
    baseXsw.writeInt(value);
    baseXsw.writeEndElement();
  }

  private void writeElement(XMLStreamWriter2 writer, String indent, String name, long value) throws Exception {
    baseXsw.writeRaw(indent);
    baseXsw.writeStartElement(name);
    baseXsw.writeLong(value);
    baseXsw.writeEndElement();
  }

  private void writeElement(String indent, String name, String value) throws Exception {
    writeElement(baseXsw, indent, name, value);
  }

  private void writeElement(String indent, String name, int value) throws Exception {
    writeElement(baseXsw, indent, name, value);
  }

  private void writeElement(String indent, String name, long value) throws Exception {
    writeElement(baseXsw, indent, name, value);
  }

  private void writeBinaryElement(XMLStreamWriter2 writer, String indent, String name, byte[] data) throws Exception {
    if (data != null) {
      baseXsw.writeRaw(indent);
      baseXsw.writeStartElement(name);
      baseXsw.writeBinary(data, 0, data.length);
      baseXsw.writeEndElement();
    }
  }

  private void writeBinaryElement(String indent, String name, byte[] data) throws Exception {
    writeBinaryElement(baseXsw, indent, name, data);
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
        baseXsw.writeRaw("\n");
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
    baseXsw.writeStartElement(rootName);
    writeElement(baseXsw, childIndent, "length", cs.length);
    writeElement(baseXsw, childIndent, "reserved", cs.reserved);
    if (cs.colorSpace != null) {
      writeElement(baseXsw, childIndent, "colorSpace", cs.colorSpace.name());
    }
    writeElement(baseXsw, childIndent, "reserved4_7", cs.reserved4_7);
    writeElement(baseXsw, childIndent, "nrOfBitsComponent1", cs.nrOfBitsComponent1);
    writeElement(baseXsw, childIndent, "nrOfBitsComponent2", cs.nrOfBitsComponent2);
    writeElement(baseXsw, childIndent, "nrOfBitsComponent3", cs.nrOfBitsComponent3);
    writeElement(baseXsw, childIndent, "nrOfBitsComponent4", cs.nrOfBitsComponent4);
    if (cs.colorValue != null) {
      writeBinaryElement(baseXsw, childIndent, "colorValue", cs.colorValue);
    }
    baseXsw.writeRaw(indent);
    baseXsw.writeEndElement();
  }

  private void writeColorStop(XMLStreamWriter2 writer, GAD_DrawingOrder.ColorStop stop, String indent) throws Exception {
    String childIndent = indent + "  ";
    baseXsw.writeStartElement("ColorStop");
    writeElement(baseXsw, childIndent, "offset", stop.offset);
    if (stop.colorValue != null) {
      writeBinaryElement(baseXsw, childIndent, "colorValue", stop.colorValue);
    }
    baseXsw.writeRaw(indent);
    baseXsw.writeEndElement();
  }

  private void writeBdaDirectly(BDA_BarCodeData bda) throws Exception {
    MnemonicPerformanceMonitor.startWriteWithMnemonic("BDA");
    baseXsw.writeStartElement("BDA_BarCodeData");
    String indent2 = XmlIndenter.getIndent(2);
    String indent1 = XmlIndenter.getIndent(1);

    if (bda.getBarCodeFlags() != null && !bda.getBarCodeFlags().isEmpty()) {
      baseXsw.writeRaw(indent2);
      baseXsw.writeStartElement("barCodeFlags");
      for (BDA_BarCodeData.BarCodeFlag flag : bda.getBarCodeFlags()) {
        writeElement(baseXsw, indent2 + "  ", "barCodeFlag", flag.name());
      }
      baseXsw.writeRaw(indent2);
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

    baseXsw.writeRaw(indent1);
    baseXsw.writeEndElement();
    MnemonicPerformanceMonitor.endWrite();
  }

  private void writeBdaParametersDataDirectly(XMLStreamWriter2 writer, BDA_BarCodeData.ParametersData pd, String indent) throws Exception {
    String indent3 = indent + "  ";
    baseXsw.writeRaw(indent);
    baseXsw.writeStartElement(MnemonicPerformanceMonitor.getSimpleName(pd.getClass()));

    if (pd.controlFlags != null && !pd.controlFlags.isEmpty()) {
      baseXsw.writeRaw(indent3);
      baseXsw.writeStartElement("controlFlags");
      for (BDA_BarCodeData.ParametersData.ControlFlag flag : pd.controlFlags) {
        writeElement(baseXsw, indent3 + "  ", "controlFlag", flag.name());
      }
      baseXsw.writeRaw(indent3);
      baseXsw.writeEndElement();
    }
    writeElement(baseXsw, indent3, "sequenceIndicator", pd.sequenceIndicator);
    writeElement(baseXsw, indent3, "totalNumberOfSymbols", pd.totalNumberOfSymbols);

    if (pd instanceof BDA_BarCodeData.ParametersDataMatrixBarcode m) {
      writeElement(baseXsw, indent3, "desiredRowSize", m.desiredRowSize);
      writeElement(baseXsw, indent3, "desiredNumberOfRows", m.desiredNumberOfRows);
      writeElement(baseXsw, indent3, "fileIDFirstByte", m.getFileIDFirstByte());
      writeElement(baseXsw, indent3, "fileIDSecondByte", m.getFileIDSecondByte());
      if (m.specialFunctionFlags != null && !m.specialFunctionFlags.isEmpty()) {
        baseXsw.writeRaw(indent3);
        baseXsw.writeStartElement("specialFunctionFlags");
        for (BDA_BarCodeData.ParametersDataMatrixBarcode.SpecialFunctionFlag flag : m.specialFunctionFlags) {
          writeElement(baseXsw, indent3 + "  ", "specialFunctionFlag", flag.name());
        }
        baseXsw.writeRaw(indent3);
        baseXsw.writeEndElement();
      }
    } else if (pd instanceof BDA_BarCodeData.ParametersDataMaxiCode_2D max) {
      writeElement(baseXsw, indent3, "symbolMode", max.symbolMode.name());
      writeElement(baseXsw, indent3, "specialFunctionFlag", max.specialFunctionFlag.name());
    } else if (pd instanceof BDA_BarCodeData.ParametersDataPDF417_2D pdf) {
      writeElement(baseXsw, indent3, "numberOfDataSymbolCharactersPerRow", pdf.numberOfDataSymbolCharactersPerRow);
      writeElement(baseXsw, indent3, "desiredNumberOfRows", pdf.desiredNumberOfRows);
      writeElement(baseXsw, indent3, "securityLevel", pdf.securityLevel);
      writeElement(baseXsw, indent3, "lengthOfMacroPDF417ControlBlock", pdf.lengthOfMacroPDF417ControlBlock);
      if (pdf.macroPDF417ControlBlock != null && pdf.macroPDF417ControlBlock.length > 0) {
        writeBinaryElement(baseXsw, indent3, "macroPDF417ControlBlock", pdf.macroPDF417ControlBlock);
      }
    } else if (pd instanceof BDA_BarCodeData.ParametersDataQRCode_2D qr) {
      writeElement(baseXsw, indent3, "conversion", qr.conversion.name());
      writeElement(baseXsw, indent3, "versionOfSymbol", qr.versionOfSymbol);
      writeElement(baseXsw, indent3, "errorCorrectionLevel", qr.errorCorrectionLevel.name());
      writeElement(baseXsw, indent3, "parityData", qr.parityData);
      writeElement(baseXsw, indent3, "qrCodeSpecialFunctionFlags", qr.qrCodeSpecialFunctionFlags.toString());
      writeElement(baseXsw, indent3, "applicationIndicator", qr.applicationIndicator);
      if (pd instanceof BDA_BarCodeData.ParametersDataQRCodeWithImage qri) {
        writeElement(baseXsw, indent3, "qrCodeWithImageFlags", qri.qrCodeWithImageFlags.toString());
        writeElement(baseXsw, indent3, "repeatingGroupsLength", qri.repeatingGroupsLength);
        for (BDA_BarCodeData.ParametersDataQRCodeWithImage.ImageInformationBlock block : qri.imageInformationBlocks) {
          baseXsw.writeRaw(indent3);
          baseXsw.writeStartElement("imageInformationBlock");
          String indent4 = XmlIndenter.getIndent(4);
          writeElement(baseXsw, indent4, "length", block.length);
          writeElement(baseXsw, indent4, "imageLocalId", block.imageLocalId);
          writeElement(baseXsw, indent4, "offsetUnitBase", block.offsetUnitBase);
          writeElement(baseXsw, indent4, "offsetUpub", block.offsetUpub);
          writeElement(baseXsw, indent4, "xOffset", block.xOffset);
          writeElement(baseXsw, indent4, "yOffset", block.yOffset);
          writeElement(baseXsw, indent4, "orientation", block.orientation);
          writeElement(baseXsw, indent4, "coordinateSystem", block.coordinateSystem);
          writeElement(baseXsw, indent4, "extentUnitBase", block.extentUnitBase);
          writeElement(baseXsw, indent4, "extentUpub", block.extentUpub);
          writeElement(baseXsw, indent4, "xExtent", block.xExtent);
          writeElement(baseXsw, indent4, "yExtent", block.yExtent);
          writeElement(baseXsw, indent4, "mappingOption", block.mappingOption);
          if (block.additionalData != null && block.additionalData.length > 0) {
            writeBinaryElement(baseXsw, indent4, "additionalData", block.additionalData);
          }
          baseXsw.writeRaw(indent3);
          baseXsw.writeEndElement();
        }
      }
    } else if (pd instanceof BDA_BarCodeData.ParametersDataAztecCode aztec) {
      writeElement(baseXsw, indent3, "desiredNumberOfLayers", aztec.desiredNumberOfLayers);
      writeElement(baseXsw, indent3, "levelOfErrorCorrection", aztec.levelOfErrorCorrection);
      writeElement(baseXsw, indent3, "aztecSpecialFunctionFlags", aztec.aztecSpecialFunctionFlags.toString());
      writeElement(baseXsw, indent3, "applicationIndicator", aztec.applicationIndicator);
      writeElement(baseXsw, indent3, "sequenceIndicator", aztec.getSequenceIndicator());
      writeElement(baseXsw, indent3, "totalNumberOfSymbols", aztec.getTotalNumberOfSymbols());
      writeElement(baseXsw, indent3, "structuredAppendIdLength", aztec.structuredAppendIdLength);
      if (aztec.structuredAppendId != null && aztec.structuredAppendId.length > 0) {
        writeBinaryElement(baseXsw, indent3, "structuredAppendId", aztec.structuredAppendId);
      }
      writeElement(baseXsw, indent3, "additionalParametersLength", aztec.additionalParametersLength);
      if (aztec.additionalParameters != null && aztec.additionalParameters.length > 0) {
        writeBinaryElement(baseXsw, indent3, "additionalParameters", aztec.additionalParameters);
      }
    } else if (pd instanceof BDA_BarCodeData.ParametersDataHanXinCode hanxin) {
      writeElement(baseXsw, indent3, "version", hanxin.version);
      writeElement(baseXsw, indent3, "errorCorrectionLevel", hanxin.errorCorrectionLevel);
      writeElement(baseXsw, indent3, "hanXinSpecialFunctionFlags", hanxin.hanXinSpecialFunctionFlags.toString());
      writeElement(baseXsw, indent3, "applicationIndicator", hanxin.applicationIndicator);
      writeElement(baseXsw, indent3, "additionalParametersLength", hanxin.additionalParametersLength);
      if (hanxin.additionalParameters != null && hanxin.additionalParameters.length > 0) {
        writeBinaryElement(baseXsw, indent3, "additionalParameters", hanxin.additionalParameters);
      }
    } else if (pd instanceof BDA_BarCodeData.ParametersDataIntelligentMailPackageBarcode imp) {
      writeElement(baseXsw, indent3, "intelligentMailPackageBarcodeFlags", imp.intelligentMailPackageBarcodeFlags.toString());
      writeElement(baseXsw, indent3, "bannerLength", imp.bannerLength);
      if (imp.bannerString != null && imp.bannerString.length > 0) {
        writeBinaryElement(baseXsw, indent3, "bannerString", imp.bannerString);
      }
    }

    baseXsw.writeRaw(indent);
    baseXsw.writeEndElement();
  }

  private void writeBbcDirectly(BBC_BeginBarCodeObject bbc) throws Exception {
    MnemonicPerformanceMonitor.startWriteWithMnemonic("BBC");
    baseXsw.writeStartElement("BBC_BeginBarCodeObject");
    String indent2 = XmlIndenter.getIndent(2);
    String indent1 = XmlIndenter.getIndent(1);
    if (bbc.getName() != null) {
      baseXsw.writeAttribute("name", bbc.getName());
    }
    if (bbc.getTriplets() != null && !bbc.getTriplets().isEmpty()) {
      for (Triplet triplet : bbc.getTriplets()) {
        baseXsw.writeRaw(indent2);
        writeTriplet(baseXsw, triplet, indent2);
      }
    }
    if (bbc.getText() != null) {
      writeElement(baseXsw, indent2, "text", bbc.getText());
    }
    baseXsw.writeRaw(indent1);
    baseXsw.writeEndElement();
    MnemonicPerformanceMonitor.endWrite();
  }

  private void writeEbcDirectly(EBC_EndBarCodeObject ebc) throws Exception {
    MnemonicPerformanceMonitor.startWriteWithMnemonic("EBC");
    baseXsw.writeStartElement("EBC_EndBarCodeObject");
    String indent2 = XmlIndenter.getIndent(2);
    String indent1 = XmlIndenter.getIndent(1);
    if (ebc.getName() != null) {
      baseXsw.writeAttribute("name", ebc.getName());
    }
    if (ebc.getTriplets() != null && !ebc.getTriplets().isEmpty()) {
      for (Triplet triplet : ebc.getTriplets()) {
        baseXsw.writeRaw(indent2);
        writeTriplet(baseXsw, triplet, indent2);
      }
    }
    if (ebc.getText() != null) {
      writeElement(baseXsw, indent2, "text", ebc.getText());
    }
    baseXsw.writeRaw(indent1);
    baseXsw.writeEndElement();
    MnemonicPerformanceMonitor.endWrite();
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

    baseXsw.writeRaw(indent1);
    baseXsw.writeEndElement();
    MnemonicPerformanceMonitor.endWrite();
  }

  private void writeOcdDirectly(OCD_ObjectContainerData ocd) throws Exception {
    MnemonicPerformanceMonitor.startWriteWithMnemonic("OCD");
    baseXsw.writeStartElement("OCD_ObjectContainerData");
    String indent2 = XmlIndenter.getIndent(2);

    if (ocd.getMetadataObject() != null) {
      writeMetadataObjectDirectly(ocd.getMetadataObject(), indent2);
    }
    if (ocd.getData() != null && ocd.getMetadataObject() == null) {
      writeBinaryElement(baseXsw, indent2, "data", ocd.getData());
    }
    if (ocd.getText() != null) {
      writeElement(baseXsw, indent2, "text", ocd.getText());
    }

    baseXsw.writeRaw(XmlIndenter.getIndent(1));
    baseXsw.writeEndElement();
    MnemonicPerformanceMonitor.endWrite();
  }

  private void writeMetadataObjectDirectly(MetadataObject mo, String indent) throws Exception {
    baseXsw.writeRaw(indent);
    baseXsw.writeStartElement("MetadataObject");
    String childIndent = indent + "  ";

    writeElement(baseXsw, childIndent, "MOLength", mo.getMoLength());
    writeElement(baseXsw, childIndent, "HeaderLength", mo.getHeaderLength());
    writeElement(baseXsw, childIndent, "MOType", mo.getMoType());
    writeElement(baseXsw, childIndent, "MOFormat", mo.getMoFormat());
    writeElement(baseXsw, childIndent, "MOCompression", mo.getMoCompression());
    writeElement(baseXsw, childIndent, "MONameLength", mo.getMoNameLength());
    if (mo.getMoName() != null) {
      writeElement(baseXsw, childIndent, "MOName", mo.getMoName());
    }
    if (mo.getMoData() != null) {
      writeBinaryElement(baseXsw, childIndent, "MOData", mo.getMoData());
    }
    if (mo.getMoDataText() != null) {
      writeElement(baseXsw, childIndent, "MODataText", mo.getMoDataText());
    }

    baseXsw.writeRaw(indent);
    baseXsw.writeEndElement();
  }
}
