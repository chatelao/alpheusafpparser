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
import com.mgz.afp.enums.AFPOrientation;
import com.mgz.afp.foca.BCF_BeginCodedFont;
import com.mgz.afp.foca.BCP_BeginCodePage;
import com.mgz.afp.foca.BFN_BeginFont;
import com.mgz.afp.foca.CFC_CodedFontControl;
import com.mgz.afp.foca.CFI_CodedFontIndex;
import com.mgz.afp.foca.CPC_CodePageControl;
import com.mgz.afp.foca.CPD_CodePageDescriptor;
import com.mgz.afp.foca.CPI_CodePageIndex;
import com.mgz.afp.foca.ECF_EndCodedFont;
import com.mgz.afp.foca.ECP_EndCodePage;
import com.mgz.afp.foca.EFN_EndFont;
import com.mgz.afp.foca.FNC_FontControl;
import com.mgz.afp.foca.FND_FontDescriptor;
import com.mgz.afp.foca.FNG_FontPatterns;
import com.mgz.afp.foca.FNI_FontIndex;
import com.mgz.afp.foca.FNM_FontPatternsMap;
import com.mgz.afp.foca.FNN_FontNameMap;
import com.mgz.afp.foca.FNN_RepeatingGroup;
import com.mgz.afp.foca.FNN_TSIdentifier;
import com.mgz.afp.foca.FNO_FontOrientation;
import com.mgz.afp.foca.FNP_FontPosition;
import com.mgz.afp.goca.BGR_BeginGraphicsObject;
import com.mgz.afp.goca.EGR_EndGraphicsObject;
import com.mgz.afp.goca.GAD_DrawingOrder;
import com.mgz.afp.goca.GAD_GraphicsData;
import com.mgz.afp.goca.GDD_GraphicsDataDescriptor;
import com.mgz.afp.goca.GDD_Parameter;
import com.mgz.afp.ioca.IDD_ImageDataDescriptor;
import com.mgz.afp.ioca.IDD_SelfDefiningField;
import com.mgz.afp.ioca.IPD_ImagePictureData;
import com.mgz.afp.ioca.IPD_Segment;
import com.mgz.afp.lineData.CCP_ConditionalProcessingControl;
import com.mgz.afp.lineData.LNC_LineDescriptorCount;
import com.mgz.afp.lineData.LND_LineDescriptor;
import com.mgz.afp.lineData.RCD_RecordDescriptor;
import com.mgz.afp.lineData.XMD_XMLDescriptor;
import com.mgz.afp.modca.BAG_BeginActiveEnvironmentGroup;
import com.mgz.afp.modca.BDG_BeginDocumentEnvironmentGroup;
import com.mgz.afp.modca.BFG_BeginFormEnvironmentGroup;
import com.mgz.afp.modca.BFM_BeginFormMap;
import com.mgz.afp.modca.BII_BeginIMImageObject;
import com.mgz.afp.modca.BMM_BeginMediumMap;
import com.mgz.afp.modca.BOG_BeginObjectEnvironmentGroup;
import com.mgz.afp.modca.BPF_BeginPrintFile;
import com.mgz.afp.modca.BRS_BeginResource;
import com.mgz.afp.modca.BSG_BeginResourceEnvironmentGroup;
import com.mgz.afp.modca.EAG_EndActiveEnvironmentGroup;
import com.mgz.afp.modca.EDG_EndDocumentEnvironmentGroup;
import com.mgz.afp.modca.EFG_EndFormEnvironmentGroup;
import com.mgz.afp.modca.EFM_EndFormMap;
import com.mgz.afp.modca.EII_EndIMImageObject;
import com.mgz.afp.modca.EMM_EndMediumMap;
import com.mgz.afp.modca.EOG_EndObjectEnvironmentGroup;
import com.mgz.afp.modca.EPF_EndPrintFile;
import com.mgz.afp.modca.ERG_EndResourceGroup;
import com.mgz.afp.modca.ERS_EndResource;
import com.mgz.afp.modca.ESG_EndResourceEnvironmentGroup;
import com.mgz.afp.modca.IOB_IncludeObject;
import com.mgz.afp.modca.MCC_MediumCopyCount;
import com.mgz.afp.modca.MCF_MapCodedFont_Format1;
import com.mgz.afp.modca.MCF_MapCodedFont_Format2;
import com.mgz.afp.modca.MMC_MediumModificationControl;
import com.mgz.afp.modca.MCD_MapContainerData;
import com.mgz.afp.modca.MGO_MapGraphicsObject;
import com.mgz.afp.modca.MDR_MapDataResource;
import com.mgz.afp.modca.MIO_MapImageObject;
import com.mgz.afp.modca.BDI_BeginDocumentIndex;
import com.mgz.afp.modca.BMO_BeginOverlay;
import com.mgz.afp.modca.CDD_ContainerDataDescriptor;
import com.mgz.afp.modca.CTC_ComposedTextControl;
import com.mgz.afp.modca.BPS_BeginPageSegment;
import com.mgz.afp.modca.BRG_BeginResourceGroup;
import com.mgz.afp.modca.EDI_EndDocumentIndex;
import com.mgz.afp.modca.FGD_FormEnvironmentGroupDescriptor;
import com.mgz.afp.modca.EMO_EndOverlay;
import com.mgz.afp.modca.EPS_EndPageSegment;
import com.mgz.afp.modca.ERG_EndResourceGroup;
import com.mgz.afp.modca.LLE_LinkLogicalElement;
import com.mgz.afp.modca.MBC_MapBarCodeObject;
import com.mgz.afp.modca.MCD_MapContainerData;
import com.mgz.afp.modca.MMD_MapMediaDestination;
import com.mgz.afp.modca.MMO_MapMediumOverlay;
import com.mgz.afp.modca.MMT_MapMediaType;
import com.mgz.afp.modca.MPG_MapPage;
import com.mgz.afp.modca.MPS_MapPageSegment;
import com.mgz.afp.modca.MPT_MapPresentationText;
import com.mgz.afp.modca.MPO_MapPageOverlay;
import com.mgz.afp.modca.MSU_MapSuppression;
import com.mgz.afp.modca.NOP_NoOperation;
import com.mgz.afp.modca.OBD_ObjectAreaDescriptor;
import com.mgz.afp.modca.OBP_ObjectAreaPosition;
import com.mgz.afp.modca.PGP_PagePosition_Format1;
import com.mgz.afp.modca.PGP_PagePosition_Format2;
import com.mgz.afp.modca.PPO_PreprocessPresentationObject;
import com.mgz.afp.modca.TLE_TagLogicalElement;
import com.mgz.afp.moca.MetadataObject;
import com.mgz.afp.modca.OCD_ObjectContainerData;
import com.mgz.afp.modca.IID_IMImageInputDescriptor;
import com.mgz.afp.modca.ICP_IMImageCellPosition;
import com.mgz.afp.modca.IRD_IMImageRasterData;
import com.mgz.afp.modca.IOC_IMImageOutputControl;
import com.mgz.afp.modca_L.BCA_BeginColorAttributeTable;
import com.mgz.afp.modca_L.ECA_EndColorAttributeTable;
import com.mgz.afp.modca_L.CAT_ColorAttributeTable;
import com.mgz.afp.modca_L.MCA_MapColorAttributeTable;
import com.mgz.afp.ptoca.BPT_BeginPresentationTextObject;
import com.mgz.afp.ptoca.EPT_EndPresentationTextObject;
import com.mgz.afp.ptoca.PTD_PresentationTextDataDescriptor_Format1;
import com.mgz.afp.ptoca.PTD_PresentationTextDataDescriptor_Format2;
import com.mgz.afp.ptoca.PTX_PresentationTextData;
import com.mgz.afp.ptoca.controlSequence.PTOCAControlSequence;
import com.mgz.afp.bcoca.BBC_BeginBarCodeObject;
import com.mgz.afp.bcoca.BDA_BarCodeData;
import com.mgz.afp.bcoca.BDD_BarCodeDataDescriptor;
import com.mgz.afp.bcoca.EBC_EndBarCodeObject;
import com.mgz.afp.triplets.Triplet;
import com.mgz.pdf.CoordinateTransformer;
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

  private int currentPageNumber = 0;
  private int inlinePos = 0;
  private int baselinePos = 0;
  private AFPOrientation inlineOri = AFPOrientation.ori0;
  private AFPOrientation baselineOri = AFPOrientation.ori90;
  private int inlineMargin = 0;
  private int baselineIncrement = 0;

  public ToXmlGenerator getBaseFragmentGenerator() {
    return baseFragmentGenerator;
  }

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

  @Override
  public void handle(StructuredField sf) throws Exception {
    boolean isPtx = sf instanceof PTX_PresentationTextData;
    boolean ptxDebug = isPtx && com.mgz.util.PTXPerformanceMonitor.isEnabled();
    long startTime = ptxDebug ? System.nanoTime() : 0;
    long startCount = ptxDebug ? cos.getCount() : 0;
    if (ptxDebug) {
      com.mgz.util.PTXPerformanceMonitor.setInPtx(true);
    }
    try {
      if (xpathExpression != null) {
        writeFieldWithXpath(sf);
      } else {
        writeFieldDirectly(sf);
      }
    } finally {
      try {
        if (startTime > 0) {
          if (xsw != null) {
            xsw.flush();
          }
          com.mgz.util.PTXPerformanceMonitor.recordPtxWrite(System.nanoTime() - startTime, cos.getCount() - startCount);
        }
      } finally {
        if (ptxDebug) {
          com.mgz.util.PTXPerformanceMonitor.setInPtx(false);
        }
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
    } else if (sf instanceof BCF_BeginCodedFont bcf) {
      writeNameAndTripletsDirectly(bcf, "BCF_BeginCodedFont", level);
    } else if (sf instanceof BCP_BeginCodePage bcp) {
      writeNameAndTripletsDirectly(bcp, "BCP_BeginCodePage", level);
    } else if (sf instanceof BFN_BeginFont bfn) {
      writeNameAndTripletsDirectly(bfn, "BFN_BeginFont", level);
    } else if (sf instanceof BFM_BeginFormMap bfm) {
      writeNameAndTripletsDirectly(bfm, "BFM_BeginFormMap", level);
    } else if (sf instanceof BII_BeginIMImageObject bii) {
      writeNameAndTripletsDirectly(bii, "BII_BeginIMImageObject", level);
    } else if (sf instanceof BMM_BeginMediumMap bmm) {
      writeNameAndTripletsDirectly(bmm, "BMM_BeginMediumMap", level);
    } else if (sf instanceof BPF_BeginPrintFile bpf) {
      writeNameAndTripletsDirectly(bpf, "BPF_BeginPrintFile", level);
    } else if (sf instanceof BRS_BeginResource brs) {
      writeNameDirectly(brs, "BRS_BeginResource", level);
    } else if (sf instanceof BDG_BeginDocumentEnvironmentGroup bdg) {
      writeNameAndTripletsDirectly(bdg, "BDG_BeginDocumentEnvironmentGroup", level);
    } else if (sf instanceof BFG_BeginFormEnvironmentGroup bfg) {
      writeNameAndTripletsDirectly(bfg, "BFG_BeginFormEnvironmentGroup", level);
    } else if (sf instanceof BOG_BeginObjectEnvironmentGroup bog) {
      writeNameAndTripletsDirectly(bog, "BOG_BeginObjectEnvironmentGroup", level);
    } else if (sf instanceof BSG_BeginResourceEnvironmentGroup bsg) {
      writeNameAndTripletsDirectly(bsg, "BSG_BeginResourceEnvironmentGroup", level);
    } else if (sf instanceof EAG_EndActiveEnvironmentGroup eag) {
      writeNameDirectly(eag, "EAG_EndActiveEnvironmentGroup", level);
    } else if (sf instanceof ECF_EndCodedFont ecf) {
      writeNameAndTripletsDirectly(ecf, "ECF_EndCodedFont", level);
    } else if (sf instanceof ECP_EndCodePage ecp) {
      writeNameAndTripletsDirectly(ecp, "ECP_EndCodePage", level);
    } else if (sf instanceof EFN_EndFont efn) {
      writeNameAndTripletsDirectly(efn, "EFN_EndFont", level);
    } else if (sf instanceof EFM_EndFormMap efm) {
      writeNameDirectly(efm, "EFM_EndFormMap", level);
    } else if (sf instanceof EII_EndIMImageObject eii) {
      writeNameDirectly(eii, "EII_EndIMImageObject", level);
    } else if (sf instanceof EMM_EndMediumMap emm) {
      writeNameDirectly(emm, "EMM_EndMediumMap", level);
    } else if (sf instanceof EPF_EndPrintFile epf) {
      writeNameAndTripletsDirectly(epf, "EPF_EndPrintFile", level);
    } else if (sf instanceof ERS_EndResource ers) {
      writeNameDirectly(ers, "ERS_EndResource", level);
    } else if (sf instanceof EDG_EndDocumentEnvironmentGroup edg) {
      writeNameDirectly(edg, "EDG_EndDocumentEnvironmentGroup", level);
    } else if (sf instanceof EFG_EndFormEnvironmentGroup efg) {
      writeNameDirectly(efg, "EFG_EndFormEnvironmentGroup", level);
    } else if (sf instanceof EOG_EndObjectEnvironmentGroup eog) {
      writeNameDirectly(eog, "EOG_EndObjectEnvironmentGroup", level);
    } else if (sf instanceof ESG_EndResourceEnvironmentGroup esg) {
      writeNameDirectly(esg, "ESG_EndResourceEnvironmentGroup", level);
    } else if (sf instanceof IOB_IncludeObject iob) {
      writeIobDirectly(iob, level);
    } else if (sf instanceof PTX_PresentationTextData ptx) {
      writePtxDirectly(ptx, level);
    } else if (sf instanceof MCF_MapCodedFont_Format1 mcf) {
      writeMcf1Directly(mcf, level);
    } else if (sf instanceof MCF_MapCodedFont_Format2 mcf) {
      writeMcfDirectly(mcf, level);
    } else if (sf instanceof MCC_MediumCopyCount mcc) {
      writeMccDirectly(mcc, level);
    } else if (sf instanceof CFC_CodedFontControl cfc) {
      writeCfcDirectly(cfc, level);
    } else if (sf instanceof CFI_CodedFontIndex cfi) {
      writeCfiDirectly(cfi, level);
    } else if (sf instanceof CPC_CodePageControl cpc) {
      writeCpcDirectly(cpc, level);
    } else if (sf instanceof CPD_CodePageDescriptor cpd) {
      writeCpdDirectly(cpd, level);
    } else if (sf instanceof CPI_CodePageIndex cpi) {
      writeCpiDirectly(cpi, level);
    } else if (sf instanceof FND_FontDescriptor fnd) {
      writeFndDirectly(fnd, level);
    } else if (sf instanceof FNG_FontPatterns fng) {
      writeFngDirectly(fng, level);
    } else if (sf instanceof FNI_FontIndex fni) {
      writeFniDirectly(fni, level);
    } else if (sf instanceof FNM_FontPatternsMap fnm) {
      writeFnmDirectly(fnm, level);
    } else if (sf instanceof FNN_FontNameMap fnn) {
      writeFnnDirectly(fnn, level);
    } else if (sf instanceof FNO_FontOrientation fno) {
      writeFnoDirectly(fno, level);
    } else if (sf instanceof FNP_FontPosition fnp) {
      writeFnpDirectly(fnp, level);
    } else if (sf instanceof FNC_FontControl fnc) {
      writeFncDirectly(fnc, level);
    } else if (sf instanceof LND_LineDescriptor lnd) {
      writeLndDirectly(lnd, level);
    } else if (sf instanceof LNC_LineDescriptorCount lnc) {
      writeLncDirectly(lnc, level);
    } else if (sf instanceof CCP_ConditionalProcessingControl ccp) {
      writeCcpDirectly(ccp, level);
    } else if (sf instanceof RCD_RecordDescriptor rcd) {
      writeRcdDirectly(rcd, level);
    } else if (sf instanceof XMD_XMLDescriptor xmd) {
      writeXmdDirectly(xmd, level);
    } else if (sf instanceof BPT_BeginPresentationTextObject bpt) {
      writeNameAndTripletsDirectly(bpt, "BPT_BeginPresentationTextObject", level);
    } else if (sf instanceof EPT_EndPresentationTextObject ept) {
      writeNameAndTripletsDirectly(ept, "EPT_EndPresentationTextObject", level);
    } else if (sf instanceof PTD_PresentationTextDataDescriptor_Format1 ptd) {
      writePtdFormat1Directly(ptd, level);
    } else if (sf instanceof PTD_PresentationTextDataDescriptor_Format2 ptd) {
      writePtdFormat2Directly(ptd, level);
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
    } else if (sf instanceof MBC_MapBarCodeObject mbc) {
      writeMbcDirectly(mbc, level);
    } else if (sf instanceof MMD_MapMediaDestination mmd) {
      writeMmdDirectly(mmd, level);
    } else if (sf instanceof MMO_MapMediumOverlay mmo) {
      writeMmoDirectly(mmo, level);
    } else if (sf instanceof MMT_MapMediaType mmt) {
      writeMmtDirectly(mmt, level);
    } else if (sf instanceof MPG_MapPage mpg) {
      writeMpgDirectly(mpg, level);
    } else if (sf instanceof MPS_MapPageSegment mps) {
      writeMpsDirectly(mps, level);
    } else if (sf instanceof MPT_MapPresentationText mpt) {
      writeMptDirectly(mpt, level);
    } else if (sf instanceof com.mgz.afp.modca.BDT_BeginDocument bdt) {
      writeBdtDirectly(bdt, level);
    } else if (sf instanceof OCD_ObjectContainerData ocd) {
      writeOcdDirectly(ocd, level);
    } else if (sf instanceof BDI_BeginDocumentIndex bdi) {
      writeNameAndTripletsDirectly(bdi, "BDI_BeginDocumentIndex", level);
    } else if (sf instanceof BMO_BeginOverlay bmo) {
      resetPtocaState();
      writeBmoDirectly(bmo, level);
    } else if (sf instanceof BPS_BeginPageSegment bps) {
      resetPtocaState();
      writeBpsDirectly(bps, level);
    } else if (sf instanceof BRG_BeginResourceGroup brg) {
      writeNameAndTripletsDirectly(brg, "BRG_BeginResourceGroup", level);
    } else if (sf instanceof com.mgz.afp.modca.BNG_BeginNamedPageGroup bng) {
      writeNameAndTripletsDirectly(bng, "BNG_BeginNamedPageGroup", level);
    } else if (sf instanceof com.mgz.afp.modca.BPG_BeginPage bpg) {
      currentPageNumber++;
      resetPtocaState();
      writeBpgDirectly(bpg, level);
    } else if (sf instanceof BGR_BeginGraphicsObject bgr) {
      writeNameAndTripletsDirectly(bgr, "BGR_BeginGraphicsObject", level);
    } else if (sf instanceof EGR_EndGraphicsObject egr) {
      writeNameAndTripletsDirectly(egr, "EGR_EndGraphicsObject", level);
    } else if (sf instanceof GDD_GraphicsDataDescriptor gdd) {
      writeGddDirectly(gdd, level);
    } else if (sf instanceof CDD_ContainerDataDescriptor cdd) {
      writeCddDirectly(cdd, level);
    } else if (sf instanceof CTC_ComposedTextControl ctc) {
      writeCtcDirectly(ctc, level);
    } else if (sf instanceof FGD_FormEnvironmentGroupDescriptor fgd) {
      writeFgdDirectly(fgd, level);
    } else if (sf instanceof PPO_PreprocessPresentationObject ppo) {
      writePpoDirectly(ppo, level);
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
      writeEpgDirectly(epg, level);
    } else if (sf instanceof com.mgz.afp.modca.IEL_IndexElement iel) {
      writeTripletsAndTextDirectly(iel, "IEL_IndexElement", level);
    } else if (sf instanceof LLE_LinkLogicalElement lle) {
      writeLleDirectly(lle, level);
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
    } else if (sf instanceof IID_IMImageInputDescriptor iid) {
      writeIidDirectly(iid, level);
    } else if (sf instanceof ICP_IMImageCellPosition icp) {
      writeIcpDirectly(icp, level);
    } else if (sf instanceof IRD_IMImageRasterData ird) {
      writeIrdDirectly(ird, level);
    } else if (sf instanceof IOC_IMImageOutputControl ioc) {
      writeIocDirectly(ioc, level);
    } else if (sf instanceof BCA_BeginColorAttributeTable bca) {
      writeBcaDirectly(bca, level);
    } else if (sf instanceof ECA_EndColorAttributeTable eca) {
      writeEcaDirectly(eca, level);
    } else if (sf instanceof CAT_ColorAttributeTable cat) {
      writeCatDirectly(cat, level);
    } else if (sf instanceof MCA_MapColorAttributeTable mca) {
      writeMcaDirectly(mca, level);
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
    if (currentPageNumber > 0) {
      baseXsw.writeIntAttribute(null, null, "page", currentPageNumber);
    }
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

  private void writeCtcDirectly(CTC_ComposedTextControl ctc, int level) throws Exception {
    MnemonicPerformanceMonitor.startWriteWithMnemonic("CTC");
    baseXsw.writeStartElement("CTC_ComposedTextControl");
    int childLevel = level + 1;
    writeBinaryElement(baseXsw, childLevel, "constantData", ctc.getConstantData());
    writeIndent(baseXsw, level);
    baseXsw.writeEndElement();
    MnemonicPerformanceMonitor.endWrite();
  }

  private void writeFgdDirectly(FGD_FormEnvironmentGroupDescriptor fgd, int level) throws Exception {
    MnemonicPerformanceMonitor.startWriteWithMnemonic("FGD");
    baseXsw.writeStartElement("FGD_FormEnvironmentGroupDescriptor");
    int childLevel = level + 1;
    writeBinaryElement(baseXsw, childLevel, "constantData", fgd.getConstantData());
    writeIndent(baseXsw, level);
    baseXsw.writeEndElement();
    MnemonicPerformanceMonitor.endWrite();
  }

  private void writePpoDirectly(PPO_PreprocessPresentationObject ppo, int level) throws Exception {
    MnemonicPerformanceMonitor.startWriteWithMnemonic("PPO");
    baseXsw.writeStartElement("PPO_PreprocessPresentationObject");
    int childLevel = level + 1;
    if (ppo.getRepeatingGroups() != null) {
      writeIndent(baseXsw, childLevel);
      baseXsw.writeStartElement("repeatingGroupsXml");
      for (com.mgz.afp.base.IRepeatingGroup irg : ppo.getRepeatingGroups()) {
        PPO_PreprocessPresentationObject.PPO_RepeatingGroup rg = (PPO_PreprocessPresentationObject.PPO_RepeatingGroup) irg;
        writeIndent(baseXsw, childLevel + 1);
        baseXsw.writeStartElement("repeatingGroupsXml");
        int rgLevel = childLevel + 2;
        writeElement(baseXsw, rgLevel, "repeatingGroupLength", rg.getRepeatingGroupLength());
        if (rg.getObjectType() != null) {
          writeElement(baseXsw, rgLevel, "objectType", rg.getObjectType().name());
        }
        writeBinaryElement(baseXsw, rgLevel, "reserved3_4", rg.getReserved3_4());
        if (rg.getFlags() != null) {
          writeIndent(baseXsw, rgLevel);
          baseXsw.writeStartElement("flags");
          for (PPO_PreprocessPresentationObject.PPO_RepeatingGroup.PPO_Flag flag : rg.getFlags()) {
            writeElement(baseXsw, rgLevel + 1, "flags", flag.name());
          }
          writeIndent(baseXsw, rgLevel);
          baseXsw.writeEndElement();
        }
        writeElement(baseXsw, rgLevel, "xOrigin", rg.getxOrigin());
        writeElement(baseXsw, rgLevel, "yOrigin", rg.getyOrigin());
        if (rg.getTriplets() != null) {
          for (Triplet t : rg.getTriplets()) {
            writeTriplet(baseXsw, t, rgLevel);
          }
        }
        writeIndent(baseXsw, childLevel + 1);
        baseXsw.writeEndElement();
      }
      writeIndent(baseXsw, childLevel);
      baseXsw.writeEndElement();
    }
    writeIndent(baseXsw, level);
    baseXsw.writeEndElement();
    MnemonicPerformanceMonitor.endWrite();
  }

  private void writeLleDirectly(LLE_LinkLogicalElement lle, int level) throws Exception {
    MnemonicPerformanceMonitor.startWriteWithMnemonic("LLE");
    baseXsw.writeStartElement("LLE_LinkLogicalElement");
    int childLevel = level + 1;
    if (lle.getLinkType() != null) {
      writeElement(baseXsw, childLevel, "linkType", lle.getLinkType().name());
    }
    writeElement(baseXsw, childLevel, "reserved1", (int) lle.getReserved1());
    if (lle.getRepeatingGroups() != null) {
      for (LLE_LinkLogicalElement.LLE_RepeatingGroup rg : lle.getRepeatingGroups()) {
        writeIndent(baseXsw, childLevel);
        baseXsw.writeStartElement("repeatingGroups");
        int rgLevel = childLevel + 1;
        writeElement(baseXsw, rgLevel, "lengthOfRepeatingGroup", rg.getLengthOfRepeatingGroup());
        if (rg.getRepeatingGroupFunction() != null) {
          writeElement(baseXsw, rgLevel, "repeatingGroupFunction", rg.getRepeatingGroupFunction().name());
        }
        if (rg.getTriplets() != null) {
          for (Triplet t : rg.getTriplets()) {
            writeTriplet(baseXsw, t, rgLevel);
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

  private void writeCddDirectly(CDD_ContainerDataDescriptor cdd, int level) throws Exception {
    MnemonicPerformanceMonitor.startWriteWithMnemonic("CDD");
    baseXsw.writeStartElement("CDD_ContainerDataDescriptor");
    int childLevel = level + 1;
    writeBinaryElement(baseXsw, childLevel, "retiredParameters", cdd.getRetiredParameters());
    if (cdd.getTriplets() != null) {
      for (Triplet t : cdd.getTriplets()) {
        writeTriplet(baseXsw, t, childLevel);
      }
    }
    writeIndent(baseXsw, level);
    baseXsw.writeEndElement();
    MnemonicPerformanceMonitor.endWrite();
  }

  private void writeIidDirectly(IID_IMImageInputDescriptor iid, int level) throws Exception {
    MnemonicPerformanceMonitor.startWriteWithMnemonic("IID");
    baseXsw.writeStartElement("IID_IMImageInputDescriptor");
    int childLevel = level + 1;
    writeBinaryElement(baseXsw, childLevel, "constantData0_11", iid.getConstantData0_11());
    if (iid.getxUnitBase() != null) {
      writeElement(baseXsw, childLevel, "xUnitBase", iid.getxUnitBase().name());
    }
    if (iid.getyUnitBase() != null) {
      writeElement(baseXsw, childLevel, "yUnitBase", iid.getyUnitBase().name());
    }
    writeElement(baseXsw, childLevel, "xUnitsPerUnitBase", (int) iid.getxUnitsPerUnitBase());
    writeElement(baseXsw, childLevel, "yUnitsPerUnitBase", (int) iid.getyUnitsPerUnitBase());
    writeElement(baseXsw, childLevel, "xSize", (int) iid.getxSize());
    writeElement(baseXsw, childLevel, "ySize", (int) iid.getySize());
    writeBinaryElement(baseXsw, childLevel, "constantData22_27", iid.getConstantData22_27());
    writeElement(baseXsw, childLevel, "xDefaultCellSize", (int) iid.getxDefaultCellSize());
    writeElement(baseXsw, childLevel, "yDefaultCellSize", (int) iid.getyDefaultCellSize());
    writeBinaryElement(baseXsw, childLevel, "constantData32_33", iid.getConstantData32_33());
    if (iid.getColor() != null) {
      writeElement(baseXsw, childLevel, "color", iid.getColor().name());
    }
    writeIndent(baseXsw, level);
    baseXsw.writeEndElement();
    MnemonicPerformanceMonitor.endWrite();
  }

  private void writeIcpDirectly(ICP_IMImageCellPosition icp, int level) throws Exception {
    MnemonicPerformanceMonitor.startWriteWithMnemonic("ICP");
    baseXsw.writeStartElement("ICP_IMImageCellPosition");
    int childLevel = level + 1;
    writeElement(baseXsw, childLevel, "xOffset", (int) icp.getxOffset());
    writeElement(baseXsw, childLevel, "yOffset", (int) icp.getyOffset());
    writeElement(baseXsw, childLevel, "xSize", icp.getxSize());
    writeElement(baseXsw, childLevel, "ySize", icp.getySize());
    writeElement(baseXsw, childLevel, "xSizeOfFillRectangle", icp.getxSizeOfFillRectangle());
    writeElement(baseXsw, childLevel, "ySizeOfFillRectangle", icp.getySizeOfFillRectangle());
    writeIndent(baseXsw, level);
    baseXsw.writeEndElement();
    MnemonicPerformanceMonitor.endWrite();
  }

  private void writeIrdDirectly(IRD_IMImageRasterData ird, int level) throws Exception {
    MnemonicPerformanceMonitor.startWriteWithMnemonic("IRD");
    baseXsw.writeStartElement("IRD_IMImageRasterData");
    int childLevel = level + 1;
    if (ird.getData() != null) {
      writeBinaryElement(baseXsw, childLevel, "data", ird.getData());
    }
    if (ird.getText() != null) {
      writeElement(baseXsw, childLevel, "text", ird.getText());
    }
    writeIndent(baseXsw, level);
    baseXsw.writeEndElement();
    MnemonicPerformanceMonitor.endWrite();
  }

  private void writeIocDirectly(IOC_IMImageOutputControl ioc, int level) throws Exception {
    MnemonicPerformanceMonitor.startWriteWithMnemonic("IOC");
    baseXsw.writeStartElement("IOC_IMImageOutputControl");
    int childLevel = level + 1;
    writeElement(baseXsw, childLevel, "xOrigin", ioc.getxOrigin());
    writeElement(baseXsw, childLevel, "yOrigin", ioc.getyOrigin());
    if (ioc.getxRotation() != null) {
      writeElement(baseXsw, childLevel, "xRotation", ioc.getxRotation().name());
    }
    if (ioc.getyRotation() != null) {
      writeElement(baseXsw, childLevel, "yRotation", ioc.getyRotation().name());
    }
    writeBinaryElement(baseXsw, childLevel, "constantData10_17", ioc.getConstantData10_17());
    if (ioc.getxImageMapping() != null) {
      writeElement(baseXsw, childLevel, "xImageMapping", ioc.getxImageMapping().name());
    }
    if (ioc.getyImageMapping() != null) {
      writeElement(baseXsw, childLevel, "yImageMapping", ioc.getyImageMapping().name());
    }
    writeBinaryElement(baseXsw, childLevel, "constantData22_23", ioc.getConstantData22_23());
    writeIndent(baseXsw, level);
    baseXsw.writeEndElement();
    MnemonicPerformanceMonitor.endWrite();
  }

  private void writeBcaDirectly(BCA_BeginColorAttributeTable bca, int level) throws Exception {
    writeNameAndTripletsDirectly(bca, "BCA_BeginColorAttributeTable", level);
  }

  private void writeEcaDirectly(ECA_EndColorAttributeTable eca, int level) throws Exception {
    writeNameAndTripletsDirectly(eca, "ECA_EndColorAttributeTable", level);
  }

  private void writeCatDirectly(CAT_ColorAttributeTable cat, int level) throws Exception {
    MnemonicPerformanceMonitor.startWriteWithMnemonic("CAT");
    baseXsw.writeStartElement("CAT_ColorAttributeTable");
    int childLevel = level + 1;
    if (cat.getBasePart() != null) {
      writeIndent(baseXsw, childLevel);
      baseXsw.writeStartElement("basePart");
      int bpLevel = childLevel + 1;
      if (cat.getBasePart().getResetLCTFlag() != null) {
        writeElement(baseXsw, bpLevel, "resetLCTFlag", cat.getBasePart().getResetLCTFlag().name());
      }
      writeElement(baseXsw, bpLevel, "reserved1", (int) cat.getBasePart().getReserved1());
      writeElement(baseXsw, bpLevel, "colorTableLocalID", (int) cat.getBasePart().getColorTableLocalID());
      writeIndent(baseXsw, childLevel);
      baseXsw.writeEndElement();
    }
    writeBinaryElement(baseXsw, childLevel, "otherData", cat.getOtherData());
    writeIndent(baseXsw, level);
    baseXsw.writeEndElement();
    MnemonicPerformanceMonitor.endWrite();
  }

  private void writeMcaDirectly(MCA_MapColorAttributeTable mca, int level) throws Exception {
    MnemonicPerformanceMonitor.startWriteWithMnemonic("MCA");
    baseXsw.writeStartElement("MCA_MapColorAttributeTable");
    int childLevel = level + 1;
    if (mca.getRepeatingGroups() != null) {
      for (IRepeatingGroup rg : mca.getRepeatingGroups()) {
        writeIndent(baseXsw, childLevel);
        baseXsw.writeStartElement("repeatingGroups");
        if (rg instanceof RepeatingGroupWithTriplets rgt) {
          if (rgt.getTriplets() != null) {
            for (Triplet triplet : rgt.getTriplets()) {
              writeTriplet(baseXsw, triplet, childLevel + 1);
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

  private void writeRcdDirectly(RCD_RecordDescriptor rcd, int level) throws Exception {
    MnemonicPerformanceMonitor.startWriteWithMnemonic("RCD");
    baseXsw.writeStartElement("RCD_RecordDescriptor");
    int childLevel = level + 1;
    writeElement(baseXsw, childLevel, "recordDescriptorID", rcd.getRecordDescriptorID());
    if (rcd.getRecordType() != null) {
      writeElement(baseXsw, childLevel, "recordType", rcd.getRecordType().name());
    }
    if (rcd.getFlags() != null) {
      writeIndent(baseXsw, childLevel);
      baseXsw.writeStartElement("flags");
      for (RCD_RecordDescriptor.RCD_Flag flag : rcd.getFlags()) {
        writeElement(baseXsw, childLevel + 1, "flags", flag.name());
      }
      writeIndent(baseXsw, childLevel);
      baseXsw.writeEndElement();
    }
    writeElement(baseXsw, childLevel, "reserved14", (int) rcd.getReserved14());
    writeElement(baseXsw, childLevel, "inlinePosition", rcd.getInlinePosition());
    writeElement(baseXsw, childLevel, "baselinePosition", rcd.getBaselinePosition());
    if (rcd.getInlineOrientation() != null) {
      writeElement(baseXsw, childLevel, "inlineOrientation", rcd.getInlineOrientation().name());
    }
    if (rcd.getBaselineOrientation() != null) {
      writeElement(baseXsw, childLevel, "baselineOrientation", rcd.getBaselineOrientation().name());
    }
    writeElement(baseXsw, childLevel, "primaryFontLocalId", rcd.getPrimaryFontLocalId());
    writeElement(baseXsw, childLevel, "fieldRCDPointer", rcd.getFieldRCDPointer());
    writeElement(baseXsw, childLevel, "suppressionTokenName", rcd.getSuppressionTokenName());
    writeElement(baseXsw, childLevel, "shiftOutFontLocalID", rcd.getShiftOutFontLocalID());
    writeElement(baseXsw, childLevel, "dataStartPosition", rcd.getDataStartPosition());
    writeElement(baseXsw, childLevel, "dataLength", rcd.getDataLength());
    writeElement(baseXsw, childLevel, "conditionalProcessingRCDPointer", rcd.getConditionalProcessingRCDPointer());
    writeElement(baseXsw, childLevel, "subpageID", (int) rcd.getSubpageID());
    writeElement(baseXsw, childLevel, "ccpIdentifier", rcd.getCcpIdentifier());
    writeElement(baseXsw, childLevel, "startingPageNumber", rcd.getStartingPageNumber());
    writeElement(baseXsw, childLevel, "endSpace", rcd.getEndSpace());
    writeElement(baseXsw, childLevel, "fieldAllignment", (int) rcd.getFieldAllignment());
    writeElement(baseXsw, childLevel, "fieldDelimiter", rcd.getFieldDelimiter());
    writeElement(baseXsw, childLevel, "fieldNumber", rcd.getFieldNumber());
    writeElement(baseXsw, childLevel, "additionalBaselineIncrement", rcd.getAdditionalBaselineIncrement());
    if (rcd.getReserved57_69() != null) {
      writeBinaryElement(baseXsw, childLevel, "reserved57_69", rcd.getReserved57_69());
    }
    if (rcd.getTriplets() != null) {
      for (Triplet triplet : rcd.getTriplets()) {
        writeTriplet(baseXsw, triplet, childLevel);
      }
    }
    writeIndent(baseXsw, level);
    baseXsw.writeEndElement();
    MnemonicPerformanceMonitor.endWrite();
  }

  private void writeXmdDirectly(XMD_XMLDescriptor xmd, int level) throws Exception {
    MnemonicPerformanceMonitor.startWriteWithMnemonic("XMD");
    baseXsw.writeStartElement("XMD_XMLDescriptor");
    int childLevel = level + 1;
    if (xmd.getElementType() != null) {
      writeElement(baseXsw, childLevel, "elementType", xmd.getElementType().name());
    }
    if (xmd.getFlags() != null) {
      writeIndent(baseXsw, childLevel);
      baseXsw.writeStartElement("flags");
      for (XMD_XMLDescriptor.XMD_Flag flag : xmd.getFlags()) {
        writeElement(baseXsw, childLevel + 1, "flags", flag.name());
      }
      writeIndent(baseXsw, childLevel);
      baseXsw.writeEndElement();
    }
    writeElement(baseXsw, childLevel, "reserved4", (int) xmd.getReserved4());
    writeElement(baseXsw, childLevel, "inlinePosition", xmd.getInlinePosition());
    writeElement(baseXsw, childLevel, "baselinePosition", xmd.getBaselinePosition());
    if (xmd.getInlineOrientation() != null) {
      writeElement(baseXsw, childLevel, "inlineOrientation", xmd.getInlineOrientation().name());
    }
    if (xmd.getBaselineOrientation() != null) {
      writeElement(baseXsw, childLevel, "baselineOrientation", xmd.getBaselineOrientation().name());
    }
    writeElement(baseXsw, childLevel, "primaryFontLocalId", xmd.getPrimaryFontLocalId());
    writeElement(baseXsw, childLevel, "fieldXMDPointer", xmd.getFieldXMDPointer());
    if (xmd.getReserved16_17() != null) {
      writeBinaryElement(baseXsw, childLevel, "reserved16_17", xmd.getReserved16_17());
    }
    writeElement(baseXsw, childLevel, "suppressionTokenName", xmd.getSuppressionTokenName());
    writeElement(baseXsw, childLevel, "reserved26", (int) xmd.getReserved26());
    writeElement(baseXsw, childLevel, "dataStartPosition", xmd.getDataStartPosition());
    writeElement(baseXsw, childLevel, "dataLength", xmd.getDataLength());
    writeElement(baseXsw, childLevel, "conditionalProcessingRCDPointer", xmd.getConditionalProcessingRCDPointer());
    writeElement(baseXsw, childLevel, "subpageID", (int) xmd.getSubpageID());
    writeElement(baseXsw, childLevel, "ccpIdentifier", xmd.getCcpIdentifier());
    writeElement(baseXsw, childLevel, "startingPageNumber", xmd.getStartingPageNumber());
    writeElement(baseXsw, childLevel, "endSpace", xmd.getEndSpace());
    writeElement(baseXsw, childLevel, "fieldAllignment", (int) xmd.getFieldAllignment());
    writeElement(baseXsw, childLevel, "fieldDelimiter", xmd.getFieldDelimiter());
    writeElement(baseXsw, childLevel, "fieldNumber", xmd.getFieldNumber());
    writeElement(baseXsw, childLevel, "additionalBaselineIncrement", xmd.getAdditionalBaselineIncrement());
    if (xmd.getReserved48_61() != null) {
      writeBinaryElement(baseXsw, childLevel, "reserved48_61", xmd.getReserved48_61());
    }
    if (xmd.getTriplets() != null) {
      for (Triplet triplet : xmd.getTriplets()) {
        writeTriplet(baseXsw, triplet, childLevel);
      }
    }
    writeIndent(baseXsw, level);
    baseXsw.writeEndElement();
    MnemonicPerformanceMonitor.endWrite();
  }

  private void writeMbcDirectly(MBC_MapBarCodeObject mbc, int level) throws Exception {
    MnemonicPerformanceMonitor.startWriteWithMnemonic("MBC");
    baseXsw.writeStartElement("MBC_MapBarCodeObject");
    writeElement(baseXsw, level + 1, "lengthOfRepeatingGroup", mbc.getLengthOfRepeatingGroup());
    if (mbc.getTriplet() != null) {
      writeTriplet(baseXsw, mbc.getTriplet(), level + 1);
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

  private void writeMmdDirectly(MMD_MapMediaDestination mmd, int level) throws Exception {
    MnemonicPerformanceMonitor.startWriteWithMnemonic("MMD");
    baseXsw.writeStartElement("MMD_MapMediaDestination");
    writeRepeatingGroupsXml(mmd, level + 1);
    writeIndent(baseXsw, level);
    baseXsw.writeEndElement();
    MnemonicPerformanceMonitor.endWrite();
  }

  private void writeMmoDirectly(MMO_MapMediumOverlay mmo, int level) throws Exception {
    MnemonicPerformanceMonitor.startWriteWithMnemonic("MMO");
    baseXsw.writeStartElement("MMO_MapMediumOverlay");
    int childLevel = level + 1;
    writeElement(baseXsw, childLevel, "lengthOfEachRepeatingGroup", (int) mmo.getLengthOfEachRepeatingGroup());
    writeBinaryElement(baseXsw, childLevel, "reserved1_3", mmo.getReserved1_3());
    if (mmo.getRepeatingGroups() != null) {
      writeIndent(baseXsw, childLevel);
      baseXsw.writeStartElement("repeatingGroupsXml");
      for (IRepeatingGroup rg : mmo.getRepeatingGroups()) {
        if (rg instanceof MMO_MapMediumOverlay.MMO_RepeatingGroup mmorg) {
          writeIndent(baseXsw, childLevel + 1);
          baseXsw.writeStartElement("repeatingGroupsXml");
          int rgLevel = childLevel + 2;
          writeElement(baseXsw, rgLevel, "mediumOverlayLocalId", (int) mmorg.getMediumOverlayLocalId());
          if (mmorg.getFlag() != null) {
            writeElement(baseXsw, rgLevel, "flag", mmorg.getFlag().name());
          }
          writeBinaryElement(baseXsw, rgLevel, "reserved2_3", mmorg.getReserved2_3());
          writeElement(baseXsw, rgLevel, "nameOfMediumOverlay", mmorg.getNameOfMediumOverlay());
          writeIndent(baseXsw, childLevel + 1);
          baseXsw.writeEndElement();
        }
      }
      writeIndent(baseXsw, childLevel);
      baseXsw.writeEndElement();
    }
    writeIndent(baseXsw, level);
    baseXsw.writeEndElement();
    MnemonicPerformanceMonitor.endWrite();
  }

  private void writeMmtDirectly(MMT_MapMediaType mmt, int level) throws Exception {
    MnemonicPerformanceMonitor.startWriteWithMnemonic("MMT");
    baseXsw.writeStartElement("MMT_MapMediaType");
    writeRepeatingGroupsXml(mmt, level + 1);
    writeIndent(baseXsw, level);
    baseXsw.writeEndElement();
    MnemonicPerformanceMonitor.endWrite();
  }

  private void writeMpgDirectly(MPG_MapPage mpg, int level) throws Exception {
    MnemonicPerformanceMonitor.startWriteWithMnemonic("MPG");
    baseXsw.writeStartElement("MPG_MapPage");
    writeRepeatingGroupsXml(mpg, level + 1);
    writeIndent(baseXsw, level);
    baseXsw.writeEndElement();
    MnemonicPerformanceMonitor.endWrite();
  }

  private void writeMpsDirectly(MPS_MapPageSegment mps, int level) throws Exception {
    MnemonicPerformanceMonitor.startWriteWithMnemonic("MPS");
    baseXsw.writeStartElement("MPS_MapPageSegment");
    int childLevel = level + 1;
    writeElement(baseXsw, childLevel, "lengthOfRepeatingGroup", (int) mps.getLengthOfRepeatingGroup());
    writeBinaryElement(baseXsw, childLevel, "reserved1_3", mps.getReserved1_3());
    if (mps.getRepeatingGroups() != null) {
      writeIndent(baseXsw, childLevel);
      baseXsw.writeStartElement("repeatingGroupsXml");
      for (IRepeatingGroup rg : mps.getRepeatingGroups()) {
        if (rg instanceof MPS_MapPageSegment.MPS_RepeatingGroup mpsrg) {
          writeIndent(baseXsw, childLevel + 1);
          baseXsw.writeStartElement("repeatingGroupsXml");
          int rgLevel = childLevel + 2;
          writeBinaryElement(baseXsw, rgLevel, "reserved0_3", mpsrg.reserved0_3);
          writeElement(baseXsw, rgLevel, "nameOfPageSegment", mpsrg.getNameOfPageSegment());
          writeIndent(baseXsw, childLevel + 1);
          baseXsw.writeEndElement();
        }
      }
      writeIndent(baseXsw, childLevel);
      baseXsw.writeEndElement();
    }
    writeIndent(baseXsw, level);
    baseXsw.writeEndElement();
    MnemonicPerformanceMonitor.endWrite();
  }

  private void writeMptDirectly(MPT_MapPresentationText mpt, int level) throws Exception {
    MnemonicPerformanceMonitor.startWriteWithMnemonic("MPT");
    baseXsw.writeStartElement("MPT_MapPresentationText");
    writeRepeatingGroupsXml(mpt, level + 1);
    writeIndent(baseXsw, level);
    baseXsw.writeEndElement();
    MnemonicPerformanceMonitor.endWrite();
  }

  private void writeRepeatingGroupsXml(com.mgz.afp.base.IHasRepeatingGroups sf, int level) throws Exception {
    if (sf.getRepeatingGroups() != null) {
      writeIndent(baseXsw, level);
      baseXsw.writeStartElement("repeatingGroupsXml");
      for (IRepeatingGroup rg : sf.getRepeatingGroups()) {
        writeIndent(baseXsw, level + 1);
        if (rg instanceof RepeatingGroupWithTriplets rgt) {
          String rgName = MnemonicPerformanceMonitor.getSimpleName(rg.getClass());
          baseXsw.writeStartElement(rgName);
          if (rgt.getTriplets() != null) {
            for (Triplet t : rgt.getTriplets()) {
              writeTriplet(baseXsw, t, level + 2);
            }
          }
          writeIndent(baseXsw, level + 1);
          baseXsw.writeEndElement();
        } else {
          // Fallback for non-triplet RGs if needed, though most Map Fields use RepeatingGroupWithTriplets
          JacksonXmlMapperProvider.getCachedWriter(rg.getClass(), true, indentEnabled).writeValue(baseFragmentGenerator, rg);
        }
      }
      writeIndent(baseXsw, level);
      baseXsw.writeEndElement();
    }
  }

  private void writePgpFormat1Directly(PGP_PagePosition_Format1 pgp, int level) throws Exception {
    MnemonicPerformanceMonitor.startWriteWithMnemonic("PGP");
    baseXsw.writeEmptyElement("PGP_PagePosition_Format1");
    baseXsw.writeIntAttribute(null, null, "xOrigin", pgp.getxOrigin());
    baseXsw.writeIntAttribute(null, null, "yOrigin", pgp.getyOrigin());
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
    if (triplet instanceof Triplet.Undefined u) {
      writer.writeStartElement("tripletUndefined");
      if (u.getTripletData() != null) {
        writeBinaryElement(writer, childLevel, "tripletData", u.getTripletData());
      }
      writeIndent(writer, level);
      writer.writeEndElement();
    } else if (triplet instanceof Triplet.TripletExtender te) {
      writer.writeStartElement("TripletExtender");
      if (te.getDatExt() != null) {
        writeBinaryElement(writer, childLevel, "datExt", te.getDatExt());
      }
      writeIndent(writer, level);
      writer.writeEndElement();
    } else if (triplet instanceof Triplet.FullyQualifiedName fqn) {
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
      XmlTemplateRegistry.getTemplate("CGCS").writeObjects(baseXsw, cgcs.getGraphicCharacterSetGlobalID(), cgcs.getCodePageGlobalID_codedCharacterSetID());
    } else if (triplet instanceof Triplet.MappingOption mo) {
      baseXsw.writeEmptyElement("MappingOption");
      if (mo.getDataObjecMapingOption() != null) {
        baseXsw.writeAttribute("dataObjecMapingOption", mo.getDataObjecMapingOption().name());
      }
    } else if (triplet instanceof Triplet.AttributeQualifier aq) {
      baseXsw.writeEmptyElement("AttributeQualifier");
      baseXsw.writeIntAttribute(null, null, "sequenceNumber", aq.sequenceNumber);
      baseXsw.writeIntAttribute(null, null, "levelNumber", aq.levelNumber);
    } else if (triplet instanceof Triplet.Comment c) {
      writer.writeStartElement("Comment");
      writeElement(writer, childLevel, "comment", c.comment);
      writeElement(writer, childLevel, "text", c.getText());
      writeIndent(writer, level);
      writer.writeEndElement();
    } else if (triplet instanceof Triplet.ResourceLocalIdentifier rli) {
      baseXsw.writeEmptyElement("ResourceLocalIdentifier");
      if (rli.getResourceType() != null) {
        baseXsw.writeAttribute("resourceType", rli.getResourceType().name());
      }
      baseXsw.writeIntAttribute(null, null, "resourceLocalID", (int) rli.getResourceLocalID());
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
      baseXsw.writeEmptyElement("CharacterRotation");
      if (cr.characterRotation != null) {
        baseXsw.writeAttribute("characterRotation", cr.characterRotation.name());
      }
    } else if (triplet instanceof Triplet.ObjectByteOffset obo) {
      String extra = obo.byteOffsetHighOrder != null ? " byteOffsetHighOrder=\"" + obo.byteOffsetHighOrder + "\"" : "";
      XmlTemplateRegistry.getTemplate("OBO").writeObjects(baseXsw, obo.byteOffset, extra);
    } else if (triplet instanceof Triplet.MeasurementUnits mu) {
      baseXsw.writeEmptyElement("MeasurementUnits");
      if (mu.xUnitBase != null) {
        baseXsw.writeAttribute("xUnitBase", mu.xUnitBase.name());
      }
      if (mu.yUnitBase != null) {
        baseXsw.writeAttribute("yUnitBase", mu.yUnitBase.name());
      }
      baseXsw.writeIntAttribute(null, null, "xUnitsPerUnitbase", (int) mu.xUnitsPerUnitbase);
      baseXsw.writeIntAttribute(null, null, "yUnitsPerUnitbase", (int) mu.yUnitsPerUnitbase);
    } else if (triplet instanceof Triplet.ObjectAreaSize oas) {
      XmlTemplateRegistry.getTemplate("OAS").writeObjects(baseXsw, (int) oas.sizeType_0x02, oas.xSize, oas.ySize);
    } else if (triplet instanceof Triplet.AreaDefinition ad) {
      XmlTemplateRegistry.getTemplate("AD").writeObjects(baseXsw, (int) ad.reserved2, ad.xOrigin, ad.yOrigin, ad.xSize, ad.ySize);
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
      String extra = oc.numberOfObjectsHigh != null
          ? " numberOfObjectsHigh=\"" + oc.numberOfObjectsHigh + "\"" : "";
      XmlTemplateRegistry.getTemplate("OCNT").writeObjects(baseXsw,
          (int) oc.subordinateObjectType, (int) oc.reserved3, oc.numberOfObjectsLow, extra);
    } else if (triplet instanceof Triplet.LocalObjectDateAndTimeStamp lodts) {
      XmlTemplateRegistry.getTemplate("LODTS").writeObjects(baseXsw, lodts.dateAndTimeStampType, (int) lodts.hundreds, (int) lodts.tens, (int) lodts.dayOfYear, (int) lodts.hourOfDay, (int) lodts.minuteOfHour, (int) lodts.secondOfMinute, (int) lodts.hundredthOfSecond);
    } else if (triplet instanceof Triplet.UniversalDateAndTimeStamp udts) {
      XmlTemplateRegistry.getTemplate("UDTS").writeObjects(baseXsw, (int) udts.reserved2, (int) udts.year, (int) udts.monthOfYear, (int) udts.dayOfMonth, (int) udts.hourOfDay, (int) udts.minuteOfHour, (int) udts.secondOfMinute, udts.timeZone, (int) udts.diffHours, (int) udts.diffMinutes);
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
      XmlTemplateRegistry.getTemplate("ROT").writeObjects(baseXsw, rot.objectType);
    } else if (triplet instanceof Triplet.MODCAFunctionSet mfs) {
      XmlTemplateRegistry.getTemplate("MFS").write(baseXsw, mfs.fctSetID);
    } else if (triplet instanceof Triplet.FontCodedGraphicCharacterSetGlobalID fcgcs) {
      XmlTemplateRegistry.getTemplate("FCGCS").write(baseXsw, fcgcs.codedGraphicCharacterSetGlobalID, fcgcs.codePageGlobalID);
    } else if (triplet instanceof Triplet.ExtendedResourceLocalIdentifier erli) {
      XmlTemplateRegistry.getTemplate("ERLI").writeObjects(baseXsw, erli.resourceType, erli.extendedResourceLocalID);
    } else if (triplet instanceof Triplet.ResourceSectionNumber rsn) {
      XmlTemplateRegistry.getTemplate("RSN").write(baseXsw, (int) rsn.resourceSectionNumber);
    } else if (triplet instanceof Triplet.MediumMapPageNumber mmpn) {
      XmlTemplateRegistry.getTemplate("MMPN").write(baseXsw, mmpn.pageNumber);
    } else if (triplet instanceof Triplet.ObjectByteExtent obe) {
      XmlTemplateRegistry.getTemplate("OBE").writeObjects(baseXsw, obe.byteExtentLow, obe.byteExtentHigh);
    } else if (triplet instanceof Triplet.ObjectStructuredFieldOffset osfo) {
      String extra = osfo.offsetHigh != null ? " offsetHigh=\"" + osfo.offsetHigh + "\"" : "";
      XmlTemplateRegistry.getTemplate("OSFO").writeObjects(baseXsw, osfo.offsetLow, extra);
    } else if (triplet instanceof Triplet.ObjectStructuredFieldExtent osfe) {
      String extra = osfe.numberOfSFHigh != null ? " numberOfSFHigh=\"" + osfe.numberOfSFHigh + "\"" : "";
      XmlTemplateRegistry.getTemplate("OSFE").writeObjects(baseXsw, osfe.numberOfSFLow, extra);
    } else if (triplet instanceof Triplet.ObjectOffset oo) {
      String typeAttr = oo.objectType != null ? " objectType=\"" + oo.objectType.name() + "\"" : "";
      String extra = oo.nrOfPrecedingObjectsHigh != null
          ? " nrOfPrecedingObjectsHigh=\"" + oo.nrOfPrecedingObjectsHigh + "\"" : "";
      XmlTemplateRegistry.getTemplate("OO").writeObjects(baseXsw, typeAttr, (int) oo.reserved3,
          oo.nrOfPrecedingObjectsLow, extra);
    } else if (triplet instanceof Triplet.FontHorizontalScaleFactor fhsf) {
      XmlTemplateRegistry.getTemplate("FHSF").write(baseXsw, (int) fhsf.horizontalScaleFactor);
    } else if (triplet instanceof Triplet.MediumOrientation mo) {
      XmlTemplateRegistry.getTemplate("MOR").writeObjects(baseXsw, mo.mediumOrientation);
    } else if (triplet instanceof Triplet.TonerSaver ts) {
      XmlTemplateRegistry.getTemplate("TS").writeObjects(baseXsw, (int) ts.reserved2, ts.tonerSaverFunction);
    } else if (triplet instanceof Triplet.PresentationControl pc) {
      writer.writeStartElement("PresentationControl");
      if (pc.presentationControlFlags != null) {
        writeIndent(writer, childLevel);
        writer.writeStartElement("presentationControlFlags");
        for (Triplet.PresentationControl.PresentationControlFlags flag : pc.presentationControlFlags) {
          writeElement(writer, childLevel + 1, "presentationControlFlags", flag.name());
        }
        writeIndent(writer, childLevel);
        writer.writeEndElement();
      }
      writeIndent(writer, level);
      writer.writeEndElement();
    } else if (triplet instanceof Triplet.FontResolutionAndMetricTechnology framt) {
      XmlTemplateRegistry.getTemplate("FRAMT").writeObjects(baseXsw, framt.metricTechnology, framt.unitBase, (int) framt.unitsPerUnitBase);
    } else if (triplet instanceof Triplet.RenderingIntent ri) {
      writer.writeStartElement("RenderingIntent");
      if (ri.reserved2_3 != null) {
        writeBinaryElement(writer, childLevel, "reserved2_3", ri.reserved2_3);
      }
      if (ri.intentForIOCA != null) {
        writeElement(writer, childLevel, "intentForIOCA", ri.intentForIOCA.name());
      }
      if (ri.intentForContainerNonIOCA != null) {
        writeElement(writer, childLevel, "intentForContainerNonIOCA", ri.intentForContainerNonIOCA.name());
      }
      if (ri.intentForPTOCA != null) {
        writeElement(writer, childLevel, "intentForPTOCA", ri.intentForPTOCA.name());
      }
      if (ri.intentForGOCA != null) {
        writeElement(writer, childLevel, "intentForGOCA", ri.intentForGOCA.name());
      }
      if (ri.reserved8_9 != null) {
        writeBinaryElement(writer, childLevel, "reserved8_9", ri.reserved8_9);
      }
      writeIndent(writer, level);
      writer.writeEndElement();
    } else if (triplet instanceof Triplet.ImageResolution ir) {
      writer.writeStartElement("ImageResolution");
      if (ir.reserved2_3 != null) {
        writeBinaryElement(writer, childLevel, "reserved2_3", ir.reserved2_3);
      }
      if (ir.xUnitBase != null) {
        writeElement(writer, childLevel, "xUnitBase", ir.xUnitBase.name());
      }
      if (ir.yUnitBase != null) {
        writeElement(writer, childLevel, "yUnitBase", ir.yUnitBase.name());
      }
      writeElement(writer, childLevel, "xUnitsPerUnitBase", ir.xUnitsPerUnitBase);
      writeElement(writer, childLevel, "yUnitsPerUnitBase", ir.yUnitsPerUnitBase);
      writeIndent(writer, level);
      writer.writeEndElement();
    } else if (triplet instanceof Triplet.PresentationSpaceResetMixing psrm) {
      XmlTemplateRegistry.getTemplate("PSRM").writeObjects(baseXsw, psrm.backgroundMixingFlag);
    } else if (triplet instanceof Triplet.PresentationSpaceMixingRule psmr) {
      writer.writeStartElement("PresentationSpaceMixingRule");
      if (psmr.getMixingRules() != null) {
        writeIndent(writer, childLevel);
        writer.writeStartElement("mixingRules");
        for (Triplet.PresentationSpaceMixingRule.MixingKeywordAndRule mr : psmr.getMixingRules()) {
          writeIndent(writer, childLevel + 1);
          writer.writeStartElement("mixingRules");
          if (mr.getKeyword() != null) {
            writeElement(writer, childLevel + 2, "keyword", mr.getKeyword().name());
          }
          if (mr.getRule() != null) {
            writeElement(writer, childLevel + 2, "rule", mr.getRule().name());
          }
          writeIndent(writer, childLevel + 1);
          writer.writeEndElement();
        }
        writeIndent(writer, childLevel);
        writer.writeEndElement();
      }
      writeIndent(writer, level);
      writer.writeEndElement();
    } else if (triplet instanceof Triplet.ColorFidelity cf) {
      writer.writeStartElement("ColorFidelity");
      if (cf.exceptionContinuationRule != null) {
        writeElement(writer, childLevel, "exceptionContinuationRule", cf.exceptionContinuationRule.name());
      }
      writeElement(writer, childLevel, "reserved3", cf.reserved3);
      if (cf.exceptionReportingRule != null) {
        writeElement(writer, childLevel, "exceptionReportingRule", cf.exceptionReportingRule.name());
      }
      writeElement(writer, childLevel, "reserved5", cf.reserved5);
      if (cf.exceptionSubstitutionRule != null) {
        writeElement(writer, childLevel, "exceptionSubstitutionRule", cf.exceptionSubstitutionRule.name());
      }
      writeElement(writer, childLevel, "reserved7", cf.reserved7);
      writeIndent(writer, level);
      writer.writeEndElement();
    } else if (triplet instanceof Triplet.FontFidelity ff) {
      writer.writeStartElement("FontFidelity");
      if (ff.exceptionContinuationRule != null) {
        writeElement(writer, childLevel, "exceptionContinuationRule", ff.exceptionContinuationRule.name());
      }
      if (ff.reserved3_6 != null) {
        writeBinaryElement(writer, childLevel, "reserved3_6", ff.reserved3_6);
      }
      writeIndent(writer, level);
      writer.writeEndElement();
    } else if (triplet instanceof Triplet.FinishingOperation fo) {
      writer.writeStartElement("FinishingOperation");
      if (fo.operationType != null) {
        writeElement(writer, childLevel, "operationType", fo.operationType.name());
      }
      if (fo.reserved3_4 != null) {
        writeBinaryElement(writer, childLevel, "reserved3_4", fo.reserved3_4);
      }
      if (fo.referenceCorner != null) {
        writeElement(writer, childLevel, "referenceCorner", fo.referenceCorner.name());
      }
      writeElement(writer, childLevel, "operationCount", fo.operationCount);
      writeElement(writer, childLevel, "offsetOfOperation", fo.offsetOfOperation);
      if (fo.positions != null) {
        writeIndent(writer, childLevel);
        writer.writeStartElement("positions");
        for (Short pos : fo.positions) {
          writeElement(writer, childLevel + 1, "positions", (int) pos);
        }
        writeIndent(writer, childLevel);
        writer.writeEndElement();
      }
      writeIndent(writer, level);
      writer.writeEndElement();
    } else if (triplet instanceof Triplet.TextFidelity tf) {
      writer.writeStartElement("TextFidelity");
      if (tf.exceptionContinuationRule != null) {
        writeElement(writer, childLevel, "exceptionContinuationRule", tf.exceptionContinuationRule.name());
      }
      writeElement(writer, childLevel, "reserved3", tf.reserved3);
      if (tf.exceptionReportingRule != null) {
        writeElement(writer, childLevel, "exceptionReportingRule", tf.exceptionReportingRule.name());
      }
      if (tf.reserved5_6 != null) {
        writeBinaryElement(writer, childLevel, "reserved5_6", tf.reserved5_6);
      }
      writeIndent(writer, level);
      writer.writeEndElement();
    } else if (triplet instanceof Triplet.MediaFidelity mf) {
      writer.writeStartElement("MediaFidelity");
      if (mf.exceptionContinuationRule != null) {
        writeElement(writer, childLevel, "exceptionContinuationRule", mf.exceptionContinuationRule.name());
      }
      writeElement(writer, childLevel, "reserved3", mf.reserved3);
      if (mf.exceptionReportingRule != null) {
        writeElement(writer, childLevel, "exceptionReportingRule", mf.exceptionReportingRule.name());
      }
      if (mf.reserved5_6 != null) {
        writeBinaryElement(writer, childLevel, "reserved5_6", mf.reserved5_6);
      }
      writeIndent(writer, level);
      writer.writeEndElement();
    } else if (triplet instanceof Triplet.FinishingFidelity ffidelity) {
      writer.writeStartElement("FinishingFidelity");
      if (ffidelity.exceptionContinuationRule != null) {
        writeElement(writer, childLevel, "exceptionContinuationRule", ffidelity.exceptionContinuationRule.name());
      }
      writeElement(writer, childLevel, "reserved3", ffidelity.reserved3);
      if (ffidelity.exceptionReportingRule != null) {
        writeElement(writer, childLevel, "exceptionReportingRule", ffidelity.exceptionReportingRule.name());
      }
      if (ffidelity.reserved5_6 != null) {
        writeBinaryElement(writer, childLevel, "reserved5_6", ffidelity.reserved5_6);
      }
      writeIndent(writer, level);
      writer.writeEndElement();
    } else if (triplet instanceof Triplet.ColorManagementResourceDescriptor cmrd) {
      XmlTemplateRegistry.getTemplate("CMRD").writeObjects(baseXsw, (int) cmrd.reserved2, cmrd.cmrProcessingMode, cmrd.cmrScope);
    } else if (triplet instanceof Triplet.CMRTagFidelity ctf) {
      writer.writeStartElement("CMRTagFidelity");
      if (ctf.exceptionContinuationRule != null) {
        writeElement(writer, childLevel, "exceptionContinuationRule", ctf.exceptionContinuationRule.name());
      }
      writeElement(writer, childLevel, "reserved3", ctf.reserved3);
      if (ctf.exceptionReportingRule != null) {
        writeElement(writer, childLevel, "exceptionReportingRule", ctf.exceptionReportingRule.name());
      }
      if (ctf.reserved5_6 != null) {
        writeBinaryElement(writer, childLevel, "reserved5_6", ctf.reserved5_6);
      }
      writeIndent(writer, level);
      writer.writeEndElement();
    } else if (triplet instanceof Triplet.DeviceAppearance da) {
      writer.writeStartElement("DeviceAppearance");
      writeElement(writer, childLevel, "reserved2", da.reserved2);
      if (da.appearance != null) {
        writeElement(writer, childLevel, "appearance", da.appearance.name());
      }
      if (da.reserved5_6 != null) {
        writeBinaryElement(writer, childLevel, "reserved5_6", da.reserved5_6);
      }
      writeIndent(writer, level);
      writer.writeEndElement();
    } else if (triplet instanceof Triplet.ObjectContainerPresentationSpaceSize ocpss) {
      XmlTemplateRegistry.getTemplate("OCPSS").writeObjects(baseXsw, ocpss.pdfPresentationSpace);
    } else if (triplet instanceof Triplet.ObjectFunctionSetSpecification_Retired ofss) {
      writer.writeStartElement("ObjectFunctionSetSpecification_Retired");
      if (ofss.objectType != null) {
        writeElement(writer, childLevel, "objectType", ofss.objectType.name());
      }
      writeElement(writer, childLevel, "ocaArchitectureLevel", (int) ofss.ocaArchitectureLevel);
      writeElement(writer, childLevel, "modcaFunctionSetIdentifier", ofss.modcaFunctionSetIdentifier);
      if (ofss.ocaFunctionSet != null) {
        writeElement(writer, childLevel, "ocaFunctionSet", ofss.ocaFunctionSet.name());
      }
      if (ofss.reserved != null) {
        writeBinaryElement(writer, childLevel, "reserved", ofss.reserved);
      }
      writeIndent(writer, level);
      writer.writeEndElement();
    } else if (triplet instanceof Triplet.DescriptorPosition dp) {
      XmlTemplateRegistry.getTemplate("DP").write(baseXsw, (int) dp.objectAreaDescriptorID);
    } else if (triplet instanceof Triplet.MediaEjectControl mec) {
      XmlTemplateRegistry.getTemplate("MEC").writeObjects(baseXsw, (int) mec.reserved2, mec.mediaEjectControl);
    } else if (triplet instanceof Triplet.PageOverlayConditionalProcessing pocp) {
      writer.writeStartElement("PageOverlayConditionalProcessing");
      if (pocp.pageOverlayType != null) {
        writeElement(writer, childLevel, "pageOverlayType", pocp.pageOverlayType.name());
      }
      if (pocp.levelOfOverlay != null) {
        writeElement(writer, childLevel, "levelOfOverlay", (int) pocp.levelOfOverlay);
      }
      writeIndent(writer, level);
      writer.writeEndElement();
    } else if (triplet instanceof Triplet.ResourceUsageAttribute rua) {
      XmlTemplateRegistry.getTemplate("RUA").writeObjects(baseXsw, rua.frequencyOfUse);
    } else if (triplet instanceof Triplet.ObjectChecksum oc) {
      writer.writeStartElement("ObjectChecksum");
      if (oc.checksumFormat != null) {
        writeElement(writer, childLevel, "checksumFormat", oc.checksumFormat.name());
      }
      writeElement(writer, childLevel, "crcCheckSum", oc.crcCheckSum);
      if (oc.objectCheckSumFlags != null) {
        writeIndent(writer, childLevel);
        writer.writeStartElement("objectCheckSumFlags");
        for (Triplet.ObjectChecksum.ChecksumFlag flag : oc.objectCheckSumFlags) {
          writeElement(writer, childLevel + 1, "objectCheckSumFlags", flag.name());
        }
        writeIndent(writer, childLevel);
        writer.writeEndElement();
      }
      writeIndent(writer, level);
      writer.writeEndElement();
    } else if (triplet instanceof Triplet.ObjectOriginIdentifier ooi) {
      writer.writeStartElement("ObjectOriginIdentifier");
      if (ooi.originationSystem != null) {
        writeElement(writer, childLevel, "originationSystem", ooi.originationSystem.name());
      }
      writeElement(writer, childLevel, "systemIDSerialNumber", ooi.systemIDSerialNumber);
      writeElement(writer, childLevel, "storageMediaID", ooi.storageMediaID);
      writeElement(writer, childLevel, "dataSetID", ooi.dataSetID);
      writeIndent(writer, level);
      writer.writeEndElement();
    } else if (triplet instanceof Triplet.IMMInsertionTriplet imm) {
      writer.writeStartElement("IMMInsertionTriplet");
      if (imm.reserved2_3 != null) {
        writeBinaryElement(writer, childLevel, "reserved2_3", imm.reserved2_3);
      }
      writeIndent(writer, level);
      writer.writeEndElement();
    } else if (triplet instanceof Triplet.TextOrientation to) {
      XmlTemplateRegistry.getTemplate("TO").writeObjects(baseXsw, to.xOrientation, to.yOrientation);
    } else if (triplet instanceof Triplet.LineDataObjectPositionMigration ldopm) {
      XmlTemplateRegistry.getTemplate("LDOPM").writeObjects(baseXsw, ldopm.locationAndOrientation);
    } else if (triplet instanceof Triplet.ResourceObjectInclude roi) {
      writer.writeStartElement("ResourceObjectInclude");
      writeElement(writer, childLevel, "objectType", (int) roi.objectType);
      writeElement(writer, childLevel, "objectName", roi.objectName);
      writeElement(writer, childLevel, "xOrigin", roi.xOrigin);
      writeElement(writer, childLevel, "yOrigin", roi.yOrigin);
      if (roi.orientation != null) {
        writeElement(writer, childLevel, "orientation", roi.orientation.name());
      }
      writeIndent(writer, level);
      writer.writeEndElement();
    } else if (triplet instanceof Triplet.PagePositionInformation ppi) {
      XmlTemplateRegistry.getTemplate("PPI").write(baseXsw, (int) ppi.repeatingGroupNumber);
    } else if (triplet instanceof Triplet.ParameterValue pv) {
      writer.writeStartElement("ParameterValue");
      writeElement(writer, childLevel, "reserved2", (int) pv.reserved2);
      if (pv.parameterSyntax != null) {
        writeElement(writer, childLevel, "parameterSyntax", pv.parameterSyntax.name());
      }
      if (pv.parameterValue != null) {
        writeBinaryElement(writer, childLevel, "parameterValue", pv.parameterValue);
      }
      writeIndent(writer, level);
      writer.writeEndElement();
    } else if (triplet instanceof Triplet.DataObjectFontDescriptor dofd) {
      writer.writeStartElement("DataObjectFontDescriptor");
      if (dofd.fontInformationFlags != null) {
        writeIndent(writer, childLevel);
        writer.writeStartElement("fontInformationFlags");
        for (Triplet.DataObjectFontDescriptor.FontInformationFlag flag : dofd.fontInformationFlags) {
          writeElement(writer, childLevel + 1, "fontInformationFlags", flag.name());
        }
        writeIndent(writer, childLevel);
        writer.writeEndElement();
      }
      writeElement(writer, childLevel, "fontTechnology", (int) dofd.fontTechnology);
      writeElement(writer, childLevel, "specifiedVerticalFontSize", (int) dofd.specifiedVerticalFontSize);
      writeElement(writer, childLevel, "horizontalScaleFactor", (int) dofd.horizontalScaleFactor);
      if (dofd.characterOrientation != null) {
        writeElement(writer, childLevel, "characterOrientation", dofd.characterOrientation.name());
      }
      writeElement(writer, childLevel, "encodingEnvironment", (int) dofd.encodingEnvironment);
      writeElement(writer, childLevel, "encodingIdentifier", (int) dofd.encodingIdentifier);
      if (dofd.reserved14_15 != null) {
        writeBinaryElement(writer, childLevel, "reserved14_15", dofd.reserved14_15);
      }
      writeIndent(writer, level);
      writer.writeEndElement();
    } else if (triplet instanceof Triplet.LocaleSelector ls) {
      writer.writeStartElement("LocaleSelector");
      writeElement(writer, childLevel, "reserved2", (int) ls.reserved2);
      if (ls.flags != null) {
        writeIndent(writer, childLevel);
        writer.writeStartElement("flags");
        for (Triplet.LocaleSelector.LocalSelectorFlag flag : ls.flags) {
          writeElement(writer, childLevel + 1, "flags", flag.name());
        }
        writeIndent(writer, childLevel);
        writer.writeEndElement();
      }
      writeElement(writer, childLevel, "languageCode", ls.languageCode);
      writeElement(writer, childLevel, "scriptCode", ls.scriptCode);
      writeElement(writer, childLevel, "regionCode", ls.regionCode);
      if (ls.reserved28_35 != null) {
        writeBinaryElement(writer, childLevel, "reserved28_35", ls.reserved28_35);
      }
      writeElement(writer, childLevel, "variantCode", ls.variantCode);
      writeIndent(writer, level);
      writer.writeEndElement();
    } else if (triplet instanceof Triplet.UP3iFinishingOperation up3i) {
      writer.writeStartElement("UP3iFinishingOperation");
      writeElement(writer, childLevel, "sequenceNumber", (int) up3i.sequenceNumber);
      writeElement(writer, childLevel, "reserved3", (int) up3i.reserved3);
      if (up3i.up3iData != null) {
        writeBinaryElement(writer, childLevel, "up3iData", up3i.up3iData);
      }
      writeIndent(writer, level);
      writer.writeEndElement();
    } else if (triplet instanceof Triplet.KeepGroupTogether kgt) {
      XmlTemplateRegistry.getTemplate("KGT").write(baseXsw, (int) kgt.grpFnct);
    } else if (triplet instanceof Triplet.SetupName sn) {
      writer.writeStartElement("SetupName");
      if (sn.reserved2_3 != null) {
        writeBinaryElement(writer, childLevel, "reserved2_3", sn.reserved2_3);
      }
      writeElement(writer, childLevel, "setupName", sn.setupName);
      writeElement(writer, childLevel, "text", sn.setupName);
      writeIndent(writer, level);
      writer.writeEndElement();
    } else {
      JacksonXmlMapperProvider.getCachedWriter(triplet.getClass(), true, indentEnabled).writeValue(baseFragmentGenerator, triplet);
    }
  }

  private void writeMccDirectly(MCC_MediumCopyCount mcc, int level) throws Exception {
    MnemonicPerformanceMonitor.startWriteWithMnemonic("MCC");
    baseXsw.writeStartElement("MCC_MediumCopyCount");
    int childLevel = level + 1;
    if (mcc.getRepeatingGroups() != null) {
      writeIndent(baseXsw, childLevel);
      baseXsw.writeStartElement("repeatingGroups");
      for (MCC_MediumCopyCount.MCC_RepeatingGroup rg : mcc.getRepeatingGroups()) {
        writeIndent(baseXsw, childLevel + 1);
        baseXsw.writeStartElement("repeatingGroups");
        int rgLevel = childLevel + 2;
        writeElement(baseXsw, rgLevel, "startingCopyNumber", (int) rg.startingCopyNumber());
        writeElement(baseXsw, rgLevel, "endingCopyNumber", (int) rg.endingCopyNumber());
        writeElement(baseXsw, rgLevel, "reserved4", (int) rg.reserved4());
        writeElement(baseXsw, rgLevel, "mediumModificationControlIdentifier", (int) rg.mediumModificationControlIdentifier());
        writeIndent(baseXsw, childLevel + 1);
        baseXsw.writeEndElement();
      }
      writeIndent(baseXsw, childLevel);
      baseXsw.writeEndElement();
    }
    writeIndent(baseXsw, level);
    baseXsw.writeEndElement();
    MnemonicPerformanceMonitor.endWrite();
  }

  private void writeMcf1Directly(MCF_MapCodedFont_Format1 mcf, int level) throws Exception {
    MnemonicPerformanceMonitor.startWriteWithMnemonic("MCF");
    baseXsw.writeStartElement("MCF_MapCodedFont_Format1");
    int childLevel = level + 1;
    writeElement(baseXsw, childLevel, "lengthOfRepeatingGroup", (int) mcf.getLengthOfRepeatingGroup());
    if (mcf.getReserved1_3() != null) {
      writeBinaryElement(baseXsw, childLevel, "reserved1_3", mcf.getReserved1_3());
    }
    if (mcf.getRepeatingGroups() != null) {
      writeIndent(baseXsw, childLevel);
      baseXsw.writeStartElement("repeatingGroups");
      for (MCF_MapCodedFont_Format1.MCF_RepeatingGroup rg : mcf.getRepeatingGroups()) {
        writeMcf1RepeatingGroup(rg, childLevel + 1);
      }
      writeIndent(baseXsw, childLevel);
      baseXsw.writeEndElement();
    }
    writeIndent(baseXsw, level);
    baseXsw.writeEndElement();
    MnemonicPerformanceMonitor.endWrite();
  }

  private void writeMcf1RepeatingGroup(MCF_MapCodedFont_Format1.MCF_RepeatingGroup rg, int level) throws Exception {
    writeIndent(baseXsw, level);
    baseXsw.writeStartElement("repeatingGroups");
    int childLevel = level + 1;
    writeElement(baseXsw, childLevel, "codedFontLocalID", (int) rg.getCodedFontLocalID());
    writeElement(baseXsw, childLevel, "reserved1", (int) rg.getReserved1());
    writeElement(baseXsw, childLevel, "codedFontSectionID", (int) rg.getCodedFontSectionID());
    writeElement(baseXsw, childLevel, "reserved3", (int) rg.getReserved3());
    if (rg.getCodedFontName() != null) {
      writeElement(baseXsw, childLevel, "codedFontName", rg.getCodedFontName());
    }
    if (rg.getCodedFontNullName() != null) {
      writeBinaryElement(baseXsw, childLevel, "codedFontNullName", rg.getCodedFontNullName());
    }
    if (rg.getCodePageName() != null) {
      writeElement(baseXsw, childLevel, "codePageName", rg.getCodePageName());
    }
    if (rg.getCodePageNullName() != null) {
      writeBinaryElement(baseXsw, childLevel, "codePageNullName", rg.getCodePageNullName());
    }
    if (rg.getFontCharacterSetName() != null) {
      writeElement(baseXsw, childLevel, "fontCharacterSetName", rg.getFontCharacterSetName());
    }
    if (rg.getFontCharacterSetNullName() != null) {
      writeBinaryElement(baseXsw, childLevel, "fontCharacterSetNullName", rg.getFontCharacterSetNullName());
    }
    if (rg.getCharacterRotation() != null) {
      writeElement(baseXsw, childLevel, "characterRotation", rg.getCharacterRotation().name());
    }
    writeIndent(baseXsw, level);
    baseXsw.writeEndElement();
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
    baseXsw.writeIntAttribute(null, null, "page", currentPageNumber);
    baseXsw.writeIntAttribute(null, null, "x", getAfpX());
    baseXsw.writeIntAttribute(null, null, "y", getAfpY());
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
      baseXsw.writeStartElement("TRN_TransparentData");
      baseXsw.writeIntAttribute(null, null, "page", currentPageNumber);
      baseXsw.writeIntAttribute(null, null, "x", getAfpX());
      baseXsw.writeIntAttribute(null, null, "y", getAfpY());
      if (trn.getEncoding() != null && trn.getTransparentDataEBCDIC() != null) {
        writeIndent(baseXsw, childLevel);
        baseXsw.writeRawBytes(XmlTagTemplates.TRN_DATA_START, 0, XmlTagTemplates.TRN_DATA_START.length);
        baseXsw.writeEbcdic(trn.getTransparentDataEBCDIC(), 0, trn.getTransparentDataEBCDIC().length, trn.getEncoding());
        baseXsw.writeRawBytes(XmlTagTemplates.TRN_DATA_END, 0, XmlTagTemplates.TRN_DATA_END.length);

        writeIndent(baseXsw, childLevel);
        baseXsw.writeRawBytes(XmlTagTemplates.TEXT_START, 0, XmlTagTemplates.TEXT_START.length);
        baseXsw.writeEbcdic(trn.getTransparentDataEBCDIC(), 0, trn.getTransparentDataEBCDIC().length, trn.getEncoding());
        baseXsw.writeRawBytes(XmlTagTemplates.TEXT_END, 0, XmlTagTemplates.TEXT_END.length);
      } else {
        writeElement(baseXsw, childLevel, "transparentData", trn.getTransparentData());
        if (trn.getText() != null) {
          writeElement(baseXsw, childLevel, "text", trn.getText());
        }
      }
      writeIndent(baseXsw, level);
      baseXsw.writeEndElement();
      MnemonicPerformanceMonitor.endWrite();
    } else if (cs instanceof PTOCAControlSequence.GraphicCharacters gc) {
      MnemonicPerformanceMonitor.startWriteWithMnemonic("GraphicCharacters");
      baseXsw.writeStartElement("GraphicCharacters");
      baseXsw.writeIntAttribute(null, null, "page", currentPageNumber);
      baseXsw.writeIntAttribute(null, null, "x", getAfpX());
      baseXsw.writeIntAttribute(null, null, "y", getAfpY());
      if (gc.getEncoding() != null && gc.getData() != null) {
        writeIndent(baseXsw, childLevel);
        baseXsw.writeRawBytes(XmlTagTemplates.TEXT_START, 0, XmlTagTemplates.TEXT_START.length);
        baseXsw.writeEbcdic(gc.getData(), 0, gc.getData().length, gc.getEncoding());
        baseXsw.writeRawBytes(XmlTagTemplates.TEXT_END, 0, XmlTagTemplates.TEXT_END.length);
      } else {
        writeElement(baseXsw, childLevel, "text", gc.getText());
      }
      writeIndent(baseXsw, level);
      baseXsw.writeEndElement();
      MnemonicPerformanceMonitor.endWrite();
    } else if (cs instanceof PTOCAControlSequence.AMI_AbsoluteMoveInline ami) {
      int x = getAfpX();
      int y = getAfpY();
      this.inlinePos = ami.getDisplacement();
      MnemonicPerformanceMonitor.startWriteWithMnemonic("AMI");
      baseXsw.writeEmptyElement("AMI_AbsoluteMoveInline");
      baseXsw.writeIntAttribute(null, null, "page", currentPageNumber);
      baseXsw.writeIntAttribute(null, null, "x", x);
      baseXsw.writeIntAttribute(null, null, "y", y);
      baseXsw.writeIntAttribute(null, null, "displacement", ami.getDisplacement());
      MnemonicPerformanceMonitor.endWrite();
    } else if (cs instanceof PTOCAControlSequence.AMB_AbsoluteMoveBaseline amb) {
      int x = getAfpX();
      int y = getAfpY();
      this.baselinePos = amb.getDisplacement();
      MnemonicPerformanceMonitor.startWriteWithMnemonic("AMB");
      baseXsw.writeEmptyElement("AMB_AbsoluteMoveBaseline");
      baseXsw.writeIntAttribute(null, null, "page", currentPageNumber);
      baseXsw.writeIntAttribute(null, null, "x", x);
      baseXsw.writeIntAttribute(null, null, "y", y);
      baseXsw.writeIntAttribute(null, null, "displacement", amb.getDisplacement());
      MnemonicPerformanceMonitor.endWrite();
    } else if (cs instanceof PTOCAControlSequence.RMI_RelativeMoveInline rmi) {
      int x = getAfpX();
      int y = getAfpY();
      this.inlinePos += rmi.getIncrement();
      MnemonicPerformanceMonitor.startWriteWithMnemonic("RMI");
      baseXsw.writeEmptyElement("RMI_RelativeMoveInline");
      baseXsw.writeIntAttribute(null, null, "page", currentPageNumber);
      baseXsw.writeIntAttribute(null, null, "x", x);
      baseXsw.writeIntAttribute(null, null, "y", y);
      baseXsw.writeIntAttribute(null, null, "increment", rmi.getIncrement());
      MnemonicPerformanceMonitor.endWrite();
    } else if (cs instanceof PTOCAControlSequence.RMB_RelativeMoveBaseline rmb) {
      int x = getAfpX();
      int y = getAfpY();
      this.baselinePos += rmb.getIncrement();
      MnemonicPerformanceMonitor.startWriteWithMnemonic("RMB");
      baseXsw.writeEmptyElement("RMB_RelativeMoveBaseline");
      baseXsw.writeIntAttribute(null, null, "page", currentPageNumber);
      baseXsw.writeIntAttribute(null, null, "x", x);
      baseXsw.writeIntAttribute(null, null, "y", y);
      baseXsw.writeIntAttribute(null, null, "increment", rmb.getIncrement());
      MnemonicPerformanceMonitor.endWrite();
    } else if (cs instanceof PTOCAControlSequence.SIM_SetInlineMargin sim) {
      this.inlineMargin = sim.getDisplacement();
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
      this.baselineIncrement = sbi.getIncrement();
      MnemonicPerformanceMonitor.startWriteWithMnemonic("SBI");
      baseXsw.writeEmptyElement("SBI_SetBaselineIncrement");
      baseXsw.writeIntAttribute(null, null, "increment", sbi.getIncrement());
      MnemonicPerformanceMonitor.endWrite();
    } else if (cs instanceof PTOCAControlSequence.BLN_BeginLine) {
      int x = getAfpX();
      int y = getAfpY();
      this.inlinePos = this.inlineMargin;
      this.baselinePos += this.baselineIncrement;
      MnemonicPerformanceMonitor.startWriteWithMnemonic("BLN");
      baseXsw.writeEmptyElement("BLN_BeginLine");
      baseXsw.writeIntAttribute(null, null, "page", currentPageNumber);
      baseXsw.writeIntAttribute(null, null, "x", x);
      baseXsw.writeIntAttribute(null, null, "y", y);
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
      int x = getAfpX();
      int y = getAfpY();
      this.inlineOri = sto.getxOrientation();
      this.baselineOri = sto.getyOrientation();
      MnemonicPerformanceMonitor.startWriteWithMnemonic("STO");
      baseXsw.writeEmptyElement("STO_SetTextOrientation");
      baseXsw.writeIntAttribute(null, null, "page", currentPageNumber);
      baseXsw.writeIntAttribute(null, null, "x", x);
      baseXsw.writeIntAttribute(null, null, "y", y);
      if (sto.getxOrientation() != null) {
        baseXsw.writeAttribute("xOrientation", sto.getxOrientation().name());
      }
      if (sto.getyOrientation() != null) {
        baseXsw.writeAttribute("yOrientation", sto.getyOrientation().name());
      }
      MnemonicPerformanceMonitor.endWrite();
    } else if (cs instanceof PTOCAControlSequence.STC_SetTextColor stc) {
      MnemonicPerformanceMonitor.startWriteWithMnemonic("STC");
      baseXsw.writeStartElement("STC_SetTextColor");
      if (stc.getForegroundColor() != null) {
        baseXsw.writeAttribute("foregroundColor", stc.getForegroundColor().name());
      }
      if (stc.getPrecision() != null) {
        baseXsw.writeAttribute("precision", stc.getPrecision().name());
      }
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
      baseXsw.writeEmptyElement("SIA_SetIntercharacterAdjustment");
      baseXsw.writeIntAttribute(null, null, "adjustment", (int) sia.getAdjustment());
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
      baseXsw.writeEmptyElement("SEC_SetExtendedTextColor");
      baseXsw.writeAttribute("colorSpace", sec.getColorSpace().name());
      baseXsw.writeIntAttribute(null, null, "nrOfBitsComponent1", (int) sec.getNrOfBitsComponent1());
      baseXsw.writeIntAttribute(null, null, "nrOfBitsComponent2", (int) sec.getNrOfBitsComponent2());
      baseXsw.writeIntAttribute(null, null, "nrOfBitsComponent3", (int) sec.getNrOfBitsComponent3());
      baseXsw.writeIntAttribute(null, null, "nrOfBitsComponent4", (int) sec.getNrOfBitsComponent4());
      baseXsw.writeAttribute("colorValue", com.mgz.util.UtilCharacterEncoding.bytesToHexString(sec.getColorValue()));
      MnemonicPerformanceMonitor.endWrite();
    } else if (cs instanceof PTOCAControlSequence.DIR_DrawIaxisRule dir) {
      MnemonicPerformanceMonitor.startWriteWithMnemonic("DIR");
      baseXsw.writeEmptyElement("DIR_DrawIaxisRule");
      baseXsw.writeIntAttribute(null, null, "page", currentPageNumber);
      baseXsw.writeIntAttribute(null, null, "x", getAfpX());
      baseXsw.writeIntAttribute(null, null, "y", getAfpY());
      baseXsw.writeIntAttribute(null, null, "length", dir.getLength());
      if (dir.getWidth() != null) {
        baseXsw.writeIntAttribute(null, null, "width", dir.getWidth());
        if (dir.getWidthFraction() != null) {
          baseXsw.writeIntAttribute(null, null, "widthFraction", dir.getWidthFraction());
        }
      }
      MnemonicPerformanceMonitor.endWrite();
    } else if (cs instanceof PTOCAControlSequence.DBR_DrawBaxisRule dbr) {
      MnemonicPerformanceMonitor.startWriteWithMnemonic("DBR");
      baseXsw.writeEmptyElement("DBR_DrawBaxisRule");
      baseXsw.writeIntAttribute(null, null, "page", currentPageNumber);
      baseXsw.writeIntAttribute(null, null, "x", getAfpX());
      baseXsw.writeIntAttribute(null, null, "y", getAfpY());
      baseXsw.writeIntAttribute(null, null, "length", dbr.getLength());
      if (dbr.getWidth() != null) {
        baseXsw.writeIntAttribute(null, null, "width", dbr.getWidth());
        if (dbr.getWidthFraction() != null) {
          baseXsw.writeIntAttribute(null, null, "widthFraction", dbr.getWidthFraction());
        }
      }
      MnemonicPerformanceMonitor.endWrite();
    } else if (cs instanceof PTOCAControlSequence.NOP_NoOperation nop) {
      MnemonicPerformanceMonitor.startWriteWithMnemonic("NOP");
      baseXsw.writeStartElement("NOP_NoOperation");
      baseXsw.writeIntAttribute(null, null, "page", currentPageNumber);
      baseXsw.writeIntAttribute(null, null, "x", getAfpX());
      baseXsw.writeIntAttribute(null, null, "y", getAfpY());
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
      baseXsw.writeEmptyElement("TBM_TemporaryBaselineMove");
      baseXsw.writeIntAttribute(null, null, "page", currentPageNumber);
      baseXsw.writeIntAttribute(null, null, "x", getAfpX());
      baseXsw.writeIntAttribute(null, null, "y", getAfpY());
      if (tbm.getDirection() != null) {
        baseXsw.writeAttribute("direction", tbm.getDirection().name());
      }
      if (tbm.getPrecision() != null) {
        baseXsw.writeAttribute("precision", tbm.getPrecision().name());
      }
      if (tbm.getTemporaryBaselineIncrement() != null) {
        baseXsw.writeIntAttribute(null, null, "temporaryBaselineIncrement", tbm.getTemporaryBaselineIncrement());
      }
      MnemonicPerformanceMonitor.endWrite();
    } else if (cs instanceof PTOCAControlSequence.OVS_Overstrike ovs) {
      MnemonicPerformanceMonitor.startWriteWithMnemonic("OVS");
      baseXsw.writeEmptyElement("OVS_Overstrike");
      baseXsw.writeIntAttribute(null, null, "page", currentPageNumber);
      baseXsw.writeIntAttribute(null, null, "x", getAfpX());
      baseXsw.writeIntAttribute(null, null, "y", getAfpY());
      if (ovs.getBypassFlag() != null) {
        baseXsw.writeAttribute("bypassFlag", ovs.getBypassFlag().name());
      }
      baseXsw.writeIntAttribute(null, null, "overStrikeCharacterCodePoint", ovs.getOverStrikeCharacterCodePoint());
      if (ovs.getText() != null) {
        baseXsw.writeAttribute("text", ovs.getText());
      }
      MnemonicPerformanceMonitor.endWrite();
    } else if (cs instanceof PTOCAControlSequence.RPS_RepeatString rps) {
      MnemonicPerformanceMonitor.startWriteWithMnemonic("RPS");
      baseXsw.writeStartElement("RPS_RepeatString");
      baseXsw.writeIntAttribute(null, null, "page", currentPageNumber);
      baseXsw.writeIntAttribute(null, null, "x", getAfpX());
      baseXsw.writeIntAttribute(null, null, "y", getAfpY());
      writeIndent(baseXsw, childLevel);
      baseXsw.writeStartElement("repeatLength");
      baseXsw.writeInt(rps.getRepeatLength());
      baseXsw.writeEndElement();
      if (rps.getEncoding() != null && rps.getRepeatData() != null) {
        writeIndent(baseXsw, childLevel);
        baseXsw.writeRawBytes(XmlTagTemplates.TEXT_START, 0, XmlTagTemplates.TEXT_START.length);
        baseXsw.writeEbcdic(rps.getRepeatData(), 0, rps.getRepeatData().length, rps.getEncoding());
        baseXsw.writeRawBytes(XmlTagTemplates.TEXT_END, 0, XmlTagTemplates.TEXT_END.length);

        writeIndent(baseXsw, childLevel);
        baseXsw.writeRawBytes(XmlTagTemplates.REPEAT_DATA_START, 0, XmlTagTemplates.REPEAT_DATA_START.length);
        baseXsw.writeBinary(rps.getRepeatData(), 0, rps.getRepeatData().length);
        baseXsw.writeRawBytes(XmlTagTemplates.REPEAT_DATA_END, 0, XmlTagTemplates.REPEAT_DATA_END.length);
      } else {
        if (rps.getText() != null) {
          writeElement(baseXsw, childLevel, "text", rps.getText());
        }
        if (rps.getRepeatData() != null) {
          writeBinaryElement(baseXsw, childLevel, "repeatData", rps.getRepeatData());
        }
      }
      writeIndent(baseXsw, level);
      baseXsw.writeRawBytes(XmlTagTemplates.RPS_END, 0, XmlTagTemplates.RPS_END.length);
      MnemonicPerformanceMonitor.endWrite();
    } else if (cs instanceof PTOCAControlSequence.UCT_UnicodeComplexText uct) {
      MnemonicPerformanceMonitor.startWriteWithMnemonic("UCT");
      baseXsw.writeStartElement("UCT_UnicodeComplexText");
      baseXsw.writeIntAttribute(null, null, "page", currentPageNumber);
      baseXsw.writeIntAttribute(null, null, "x", getAfpX());
      baseXsw.writeIntAttribute(null, null, "y", getAfpY());
      writeElement(baseXsw, childLevel, "uctVers", uct.getUctVers());
      writeElement(baseXsw, childLevel, "ctLength", uct.getCtLength());
      writeElement(baseXsw, childLevel, "ctFlags", uct.getCtFlags());
      writeElement(baseXsw, childLevel, "bidiCt", uct.getBidiCt());
      writeElement(baseXsw, childLevel, "glyphCt", uct.getGlyphCt());
      writeElement(baseXsw, childLevel, "altiPos", uct.getAltiPos());
      if (uct.getComplexText() != null) {
        writeBinaryElement(baseXsw, childLevel, "complexText", uct.getComplexText());
      }
      if (uct.getText() != null) {
        writeElement(baseXsw, childLevel, "text", uct.getText());
      }
      writeIndent(baseXsw, level);
      baseXsw.writeEndElement();
      MnemonicPerformanceMonitor.endWrite();
    } else if (cs instanceof PTOCAControlSequence.GLC_GlyphLayoutControl glc) {
      MnemonicPerformanceMonitor.startWriteWithMnemonic("GLC");
      baseXsw.writeStartElement("GLC_GlyphLayoutControl");
      baseXsw.writeIntAttribute(null, null, "page", currentPageNumber);
      baseXsw.writeIntAttribute(null, null, "x", getAfpX());
      baseXsw.writeIntAttribute(null, null, "y", getAfpY());
      writeElement(baseXsw, childLevel, "iAdvance", glc.getIAdvance());
      writeElement(baseXsw, childLevel, "oidLgth", glc.getOidLgth());
      writeElement(baseXsw, childLevel, "ffnLgth", glc.getFfnLgth());
      if (glc.getFontOid() != null) {
        writeBinaryElement(baseXsw, childLevel, "fontOid", glc.getFontOid());
      }
      if (glc.getFfontName() != null) {
        writeElement(baseXsw, childLevel, "ffontName", glc.getFfontName());
      }
      if (glc.getText() != null) {
        writeElement(baseXsw, childLevel, "text", glc.getText());
      }
      writeIndent(baseXsw, level);
      baseXsw.writeEndElement();
      MnemonicPerformanceMonitor.endWrite();
    } else if (cs instanceof PTOCAControlSequence.ENC_EncryptedData enc) {
      MnemonicPerformanceMonitor.startWriteWithMnemonic("ENC");
      baseXsw.writeStartElement("ENC_EncryptedData");
      baseXsw.writeIntAttribute(null, null, "page", currentPageNumber);
      baseXsw.writeIntAttribute(null, null, "x", getAfpX());
      baseXsw.writeIntAttribute(null, null, "y", getAfpY());
      writeElement(baseXsw, childLevel, "reserved4_7", enc.getReserved4_7());
      if (enc.getEncryptedData() != null) {
        writeBinaryElement(baseXsw, childLevel, "encryptedData", enc.getEncryptedData());
      }
      writeIndent(baseXsw, level);
      baseXsw.writeEndElement();
      MnemonicPerformanceMonitor.endWrite();
    } else if (cs instanceof PTOCAControlSequence.SKI_SetKeyInformation ski) {
      MnemonicPerformanceMonitor.startWriteWithMnemonic("SKI");
      baseXsw.writeStartElement("SKI_SetKeyInformation");
      baseXsw.writeIntAttribute(null, null, "page", currentPageNumber);
      baseXsw.writeIntAttribute(null, null, "x", getAfpX());
      baseXsw.writeIntAttribute(null, null, "y", getAfpY());
      writeElement(baseXsw, childLevel, "reserved4_7", ski.getReserved4_7());
      if (ski.getKeyInfo() != null) {
        writeBinaryElement(baseXsw, childLevel, "keyInfo", ski.getKeyInfo());
      }
      writeIndent(baseXsw, level);
      baseXsw.writeEndElement();
      MnemonicPerformanceMonitor.endWrite();
    } else if (cs instanceof PTOCAControlSequence.SEA_SetEncryptedAlternate sea) {
      MnemonicPerformanceMonitor.startWriteWithMnemonic("SEA");
      baseXsw.writeStartElement("SEA_SetEncryptedAlternate");
      baseXsw.writeIntAttribute(null, null, "page", currentPageNumber);
      baseXsw.writeIntAttribute(null, null, "x", getAfpX());
      baseXsw.writeIntAttribute(null, null, "y", getAfpY());
      writeElement(baseXsw, childLevel, "reserved4_7", sea.getReserved4_7());
      if (sea.getAlternateText() != null) {
        writeBinaryElement(baseXsw, childLevel, "alternateText", sea.getAlternateText());
      }
      if (sea.getText() != null) {
        writeElement(baseXsw, childLevel, "text", sea.getText());
      }
      writeIndent(baseXsw, level);
      baseXsw.writeEndElement();
      MnemonicPerformanceMonitor.endWrite();
    } else if (cs instanceof PTOCAControlSequence.GIR_GlyphIdRun gir) {
      MnemonicPerformanceMonitor.startWriteWithMnemonic("GIR");
      baseXsw.writeStartElement("GIR_GlyphIdRun");
      baseXsw.writeIntAttribute(null, null, "page", currentPageNumber);
      baseXsw.writeIntAttribute(null, null, "x", getAfpX());
      baseXsw.writeIntAttribute(null, null, "y", getAfpY());
      if (gir.getGlyphIds() != null) {
        writeIndent(baseXsw, childLevel);
        baseXsw.writeStartElement("glyphIds");
        for (int id : gir.getGlyphIds()) {
          writeElement(baseXsw, childLevel + 1, "glyphId", id);
        }
        writeIndent(baseXsw, childLevel);
        baseXsw.writeEndElement();
      }
      writeIndent(baseXsw, level);
      baseXsw.writeEndElement();
      MnemonicPerformanceMonitor.endWrite();
    } else if (cs instanceof PTOCAControlSequence.GAR_GlyphAdvanceRun gar) {
      MnemonicPerformanceMonitor.startWriteWithMnemonic("GAR");
      baseXsw.writeStartElement("GAR_GlyphAdvanceRun");
      baseXsw.writeIntAttribute(null, null, "page", currentPageNumber);
      baseXsw.writeIntAttribute(null, null, "x", getAfpX());
      baseXsw.writeIntAttribute(null, null, "y", getAfpY());
      if (gar.getAdvances() != null) {
        writeIndent(baseXsw, childLevel);
        baseXsw.writeStartElement("advances");
        for (short adv : gar.getAdvances()) {
          writeElement(baseXsw, childLevel + 1, "advance", (int) adv);
        }
        writeIndent(baseXsw, childLevel);
        baseXsw.writeEndElement();
      }
      writeIndent(baseXsw, level);
      baseXsw.writeEndElement();
      MnemonicPerformanceMonitor.endWrite();
    } else if (cs instanceof PTOCAControlSequence.GOR_GlyphOffsetRun gor) {
      MnemonicPerformanceMonitor.startWriteWithMnemonic("GOR");
      baseXsw.writeStartElement("GOR_GlyphOffsetRun");
      baseXsw.writeIntAttribute(null, null, "page", currentPageNumber);
      baseXsw.writeIntAttribute(null, null, "x", getAfpX());
      baseXsw.writeIntAttribute(null, null, "y", getAfpY());
      if (gor.getOffsets() != null) {
        writeIndent(baseXsw, childLevel);
        baseXsw.writeStartElement("offsets");
        for (short off : gor.getOffsets()) {
          writeElement(baseXsw, childLevel + 1, "offset", (int) off);
        }
        writeIndent(baseXsw, childLevel);
        baseXsw.writeEndElement();
      }
      writeIndent(baseXsw, level);
      baseXsw.writeEndElement();
      MnemonicPerformanceMonitor.endWrite();
    } else if (cs instanceof PTOCAControlSequence.Undefined u) {
      baseXsw.writeStartElement("Undefined");
      baseXsw.writeIntAttribute(null, null, "page", currentPageNumber);
      baseXsw.writeIntAttribute(null, null, "x", getAfpX());
      baseXsw.writeIntAttribute(null, null, "y", getAfpY());
      if (u.getUndefinedData() != null) {
        writeBinaryElement(baseXsw, childLevel, "undefinedData", u.getUndefinedData());
      }
      if (u.getText() != null) {
        writeElement(baseXsw, childLevel, "text", u.getText());
      }
      writeIndent(baseXsw, level);
      baseXsw.writeEndElement();
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
      writeDrawingOrderWithPoints(writer, gcline, "GCLINE_LineAtCurrentPosition", level);
    } else if (order instanceof GAD_DrawingOrder.GLINE_LineAtGivenPosition gline) {
      writeDrawingOrderWithPoints(writer, gline, "GLINE_LineAtGivenPosition", level);
    } else if (order instanceof GAD_DrawingOrder.GCMRK_MarkerAtCurrentPosition gcmrk) {
      writeDrawingOrderWithPoints(writer, gcmrk, "GCMRK_MarkerAtCurrentPosition", level);
    } else if (order instanceof GAD_DrawingOrder.GMRK_MarkerAtGivenPosition gmrk) {
      writeDrawingOrderWithPoints(writer, gmrk, "GMRK_MarkerAtGivenPosition", level);
    } else if (order instanceof GAD_DrawingOrder.GCFLT_FilletAtCurrentPosition gcflt) {
      writeDrawingOrderWithPoints(writer, gcflt, "GCFLT_FilletAtCurrentPosition", level);
    } else if (order instanceof GAD_DrawingOrder.GFLT_FilletAtGivenPosition gflt) {
      writeDrawingOrderWithPoints(writer, gflt, "GFLT_FilletAtGivenPosition", level);
    } else if (order instanceof GAD_DrawingOrder.GCCBEZ_CubicBezierCurveAtCurrentPosition gccbez) {
      writeDrawingOrderWithPoints(writer, gccbez, "GCCBEZ_CubicBezierCurveAtCurrentPosition", level);
    } else if (order instanceof GAD_DrawingOrder.GCBEZ_CubicBezierCurveAtGivenPosition gcbez) {
      writeDrawingOrderWithPoints(writer, gcbez, "GCBEZ_CubicBezierCurveAtGivenPosition", level);
    } else if (order instanceof GAD_DrawingOrder.GCFARC_FullArcAtCurrentPosition gcfarc) {
      writer.writeEmptyElement("GCFARC_FullArcAtCurrentPosition");
      writer.writeIntAttribute(null, null, "multiplierIntegerPortion", gcfarc.getMultiplierIntegerPortion());
      writer.writeIntAttribute(null, null, "multiplierFractionalPortion", gcfarc.getMultiplierFractionalPortion());
    } else if (order instanceof GAD_DrawingOrder.GCOMT_Comment gcomt) {
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
    } else if (order instanceof GAD_DrawingOrder.GSCP_SetCurrentPosition gcp) {
      writer.writeEmptyElement("GSCP_SetCurrentPosition");
      writer.writeIntAttribute(null, null, "coordinateX", gcp.getCoordinateX());
      writer.writeIntAttribute(null, null, "coordinateY", gcp.getCoordinateY());
    } else if (order instanceof GAD_DrawingOrder.GSCOL_SetColor gsc) {
      if (gsc.getColor() != null) {
        writer.writeEmptyElement("GSCOL_SetColor");
        writer.writeAttribute("color", gsc.getColor().name());
      } else {
        writer.writeEmptyElement("GSCOL_SetColor");
      }
    } else if (order instanceof GAD_DrawingOrder.GSCS_SetCharacterSet gscs) {
      writer.writeEmptyElement("GSCS_SetCharacterSet");
      writer.writeIntAttribute(null, null, "characterSetLocalID", gscs.getCharacterSetLocalID());
    } else if (order instanceof GAD_DrawingOrder.GSPS_SetPatternSet gsps) {
      writer.writeEmptyElement("GSPS_SetPatternSet");
      writer.writeIntAttribute(null, null, "patternLocalID", gsps.patternLocalID);
    } else if (order instanceof GAD_DrawingOrder.GSMX_SetMix gsmx) {
      writer.writeEmptyElement("GSMX_SetMix");
      writer.writeIntAttribute(null, null, "mixMode", gsmx.mixMode);
    } else if (order instanceof GAD_DrawingOrder.GSBMX_SetBackgroundMix gsbmx) {
      writer.writeEmptyElement("GSBMX_SetBackgroundMix");
      writer.writeIntAttribute(null, null, "mixMode", gsbmx.mixMode);
    } else if (order instanceof GAD_DrawingOrder.GSFLW_SetFractionLineWidth gsflw) {
      writer.writeEmptyElement("GSFLW_SetFractionLineWidth");
      writer.writeIntAttribute(null, null, "integralMultiplier", gsflw.integralMultiplier);
      writer.writeIntAttribute(null, null, "fractionalMultiplier", gsflw.fractionalMultiplier);
    } else if (order instanceof GAD_DrawingOrder.GSLT_SetLineType gslt) {
      writer.writeEmptyElement("GSLT_SetLineType");
      writer.writeIntAttribute(null, null, "lineType", gslt.lineType);
    } else if (order instanceof GAD_DrawingOrder.GSPIK_SetPickIdentifier gspik) {
      writer.writeEmptyElement("GSPIK_SetPickIdentifier");
      writer.writeIntAttribute(null, null, "pickIdentifier", gspik.pickIdentifier);
    } else if (order instanceof GAD_DrawingOrder.GSGCH_SegmentCharacteristics gsgch) {
      writer.writeStartElement("GSGCH_SegmentCharacteristics");
      writer.writeIntAttribute(null, null, "lengthOfFollowingData", gsgch.lengthOfFollowingData);
      writer.writeIntAttribute(null, null, "identificationCode", gsgch.getIdentificationCode());
      if (gsgch.getParameters() != null) {
        writeBinaryElement(writer, childLevel, "parameters", gsgch.getParameters());
      }
      writeIndent(writer, level);
      writer.writeEndElement();
    } else if (order instanceof GAD_DrawingOrder.GSECOL_SetExtendedColor gsecol) {
      if (gsecol.getColor() != null) {
        writer.writeEmptyElement("GSECOL_SetExtendedColor");
        writer.writeAttribute("color", gsecol.getColor().name());
      } else {
        writer.writeEmptyElement("GSECOL_SetExtendedColor");
      }
    } else if (order instanceof GAD_DrawingOrder.GSCC_SetCharacterCell gscc) {
      writer.writeEmptyElement("GSCC_SetCharacterCell");
      writer.writeIntAttribute(null, null, "widthOfCharacterCellIntegerPart", gscc.getWidthOfCharacterCellIntegerPart());
      writer.writeIntAttribute(null, null, "heightOfCharacterCellIntegerPart", gscc.getHeightOfCharacterCellIntegerPart());
      if (gscc.getWidthOfCharacterCellFractionalPart() != null) {
        writer.writeIntAttribute(null, null, "widthOfCharacterCellFractionalPart", gscc.getWidthOfCharacterCellFractionalPart());
      }
      if (gscc.getHeightOfCharacterCellFractionalPart() != null) {
        writer.writeIntAttribute(null, null, "heightOfCharacterCellFractionalPart", gscc.getHeightOfCharacterCellFractionalPart());
      }
    } else if (order instanceof GAD_DrawingOrder.GSCA_SetCharacterAngle gsca) {
      writer.writeEmptyElement("GSCA_SetCharacterAngle");
      if (gsca.getAnglePoint() != null) {
        writer.writeIntAttribute(null, null, "xCoordinate", gsca.getAnglePoint().xCoordinate());
        writer.writeIntAttribute(null, null, "yCoordinate", gsca.getAnglePoint().yCoordinate());
      }
    } else if (order instanceof GAD_DrawingOrder.GSCH_SetCharacterShear gsch) {
      writer.writeEmptyElement("GSCH_SetCharacterShear");
      writer.writeIntAttribute(null, null, "dividendOfShearRatio", gsch.getDividendOfShearRatio());
      writer.writeIntAttribute(null, null, "divisorOfShearRatio", gsch.getDivisorOfShearRatio());
    } else if (order instanceof GAD_DrawingOrder.GSMC_SetMarkerCell gsmc) {
      writer.writeEmptyElement("GSMC_SetMarkerCell");
      writer.writeIntAttribute(null, null, "widthOfMarkerCell", gsmc.getWidthOfMarkerCell());
      writer.writeIntAttribute(null, null, "heightOfMarkerCell", gsmc.getHeightOfMarkerCell());
    } else if (order instanceof GAD_DrawingOrder.GSLW_SetLineWidth gslw) {
      writer.writeEmptyElement("GSLW_SetLineWidth");
      writer.writeIntAttribute(null, null, "lineWidth", gslw.lineWidth);
    } else if (order instanceof GAD_DrawingOrder.GSLE_SetLineEnd gsle) {
      if (gsle.lineEnd != null) {
        writer.writeEmptyElement("GSLE_SetLineEnd");
        writer.writeAttribute("lineEnd", gsle.lineEnd.name());
      } else {
        writer.writeEmptyElement("GSLE_SetLineEnd");
      }
    } else if (order instanceof GAD_DrawingOrder.GSLJ_SetLineJoin gslj) {
      if (gslj.lineJoin != null) {
        writer.writeEmptyElement("GSLJ_SetLineJoin");
        writer.writeAttribute("lineJoin", gslj.lineJoin.name());
      } else {
        writer.writeEmptyElement("GSLJ_SetLineJoin");
      }
    } else if (order instanceof GAD_DrawingOrder.GSPT_SetPatternSymbol gspt) {
      writer.writeEmptyElement("GSPT_SetPatternSymbol");
      writer.writeIntAttribute(null, null, "patternSymbolCodePoint", gspt.patternSymbolCodePoint);
    } else if (order instanceof GAD_DrawingOrder.GSMT_SetMarkerSymbol gsmt) {
      writer.writeEmptyElement("GSMT_SetMarkerSymbol");
      writer.writeIntAttribute(null, null, "markerSymbolCodePoint", gsmt.markerSymbolCodePoint);
    } else if (order instanceof GAD_DrawingOrder.GSCR_SetCharacterPrecision gscr) {
      writer.writeEmptyElement("GSCR_SetCharacterPrecision");
      writer.writeIntAttribute(null, null, "characterPrecision", gscr.characterPrecision);
    } else if (order instanceof GAD_DrawingOrder.GSCD_SetCharacterDirection gscd) {
      writer.writeEmptyElement("GSCD_SetCharacterDirection");
      writer.writeIntAttribute(null, null, "characterDirection", gscd.characterDirection);
    } else if (order instanceof GAD_DrawingOrder.GSMP_SetMarkerPrecision gsmp) {
      writer.writeEmptyElement("GSMP_SetMarkerPrecision");
      writer.writeIntAttribute(null, null, "markerPrecision", gsmp.markerPrecision);
    } else if (order instanceof GAD_DrawingOrder.GSMS_SetMarkerSet gsms) {
      writer.writeEmptyElement("GSMS_SetMarkerSet");
      writer.writeIntAttribute(null, null, "markerSetLocalID", gsms.markerSetLocalID);
    } else if (order instanceof GAD_DrawingOrder.GSCLT_SetCustomLineType gsclt) {
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
    } else if (order instanceof GAD_DrawingOrder.GSAP_SetArcParameters gsap) {
      writer.writeEmptyElement("GSAP_SetArcParameters");
      writer.writeIntAttribute(null, null, "arcTransformP", gsap.getArcTransformP());
      writer.writeIntAttribute(null, null, "arcTransformQ", gsap.getArcTransformQ());
      writer.writeIntAttribute(null, null, "arcTransformR", gsap.getArcTransformR());
      writer.writeIntAttribute(null, null, "arcTransformS", gsap.getArcTransformS());
    } else if (order instanceof GAD_DrawingOrder.GSPRP_SetPatternReferencePoint gsprp) {
      writer.writeEmptyElement("GSPRP_SetPatternReferencePoint");
      writer.writeIntAttribute(null, null, "flags", (int) gsprp.getFlags());
      writer.writeIntAttribute(null, null, "reserved3", (int) gsprp.getReserved3());
      writer.writeIntAttribute(null, null, "coordinateX", gsprp.getCoordinateX());
      writer.writeIntAttribute(null, null, "coordinateY", gsprp.getCoordinateY());
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
      writer.writeEmptyElement("GEPROL_EndProlog");
      writer.writeIntAttribute(null, null, "reserved0", geprol.getReserved0());
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
          writeDrawingOrderDirectly(baseXsw, childOrder, childLevel);
        }
      }
      writeIndent(baseXsw, level);
      baseXsw.writeEndElement();
    } else if (order instanceof GAD_DrawingOrder.GBCP_BeginCustomPattern gbcp) {
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
    } else if (order instanceof GAD_DrawingOrder.GDPT_DeletePattern gdpt) {
      writer.writeStartElement("GDPT_DeletePattern");
      writeElement(writer, childLevel, "lengthOfFollowingData", gdpt.lengthOfFollowingData);
      writeElement(writer, childLevel, "reserved2_3", gdpt.reserved2_3);
      writeElement(writer, childLevel, "patternSet", gdpt.patternSet);
      if (gdpt.patternSymbol != null) {
        writeElement(writer, childLevel, "patternSymbol", gdpt.patternSymbol);
      }
      writeIndent(writer, level);
      writer.writeEndElement();
    } else if (order instanceof GAD_DrawingOrder.GECP_EndCustomPattern gecp) {
      writer.writeEmptyElement("GECP_EndCustomPattern");
      writer.writeIntAttribute(null, null, "reserved0", gecp.getReserved0());
    } else if (order instanceof GAD_DrawingOrder.GBAR_BeginArea gbar) {
      writer.writeEmptyElement("GBAR_BeginArea");
      writer.writeIntAttribute(null, null, "internalFlags", gbar.getInternalFlags());
    } else if (order instanceof GAD_DrawingOrder.GEAR_EndArea gear) {
      String text = gear.getText();
      if (text != null && !text.isEmpty()) {
        writer.writeEmptyElement("GEAR_EndArea");
        writer.writeAttribute("text", text);
      } else {
        writer.writeEmptyElement("GEAR_EndArea");
      }
    } else if (order instanceof GAD_DrawingOrder.GIMD_ImageData gimd) {
      writer.writeStartElement("GIMD_ImageData");
      if (gimd.getImageData() != null) {
        writeBinaryElement(writer, childLevel, "imageData", gimd.getImageData());
      }
      writeIndent(writer, level);
      writer.writeEndElement();
    } else if (order instanceof GAD_DrawingOrder.GEIMG_EndImage geimg) {
      writer.writeStartElement("GEIMG_EndImage");
      writeElement(writer, childLevel, "lengthOfFollowingData", geimg.getLengthOfFollowingData());
      if (geimg.reservedData != null) {
        writeBinaryElement(writer, childLevel, "reservedData", geimg.reservedData);
      }
      writeIndent(writer, level);
      writer.writeEndElement();
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
      writer.writeStartElement("GCRLINE_RelativeLineAtCurrentPosition");
      if (gcrline.relativeOffsets != null && !gcrline.relativeOffsets.isEmpty()) {
        writeIndent(writer, childLevel);
        writer.writeStartElement("relativeOffsets");
        StringBuilder sb = new StringBuilder(gcrline.relativeOffsets.size() * 10);
        for (GAD_DrawingOrder.GOCA_RelativePoint rp : gcrline.relativeOffsets) {
          if (!sb.isEmpty()) {
            sb.append(' ');
          }
          sb.append((int) rp.xOffset()).append(' ').append((int) rp.yOffset());
        }
        writer.writeRaw(sb.toString());
        writer.writeEndElement();
      }
      writeIndent(writer, level);
      writer.writeEndElement();
    } else if (order instanceof GAD_DrawingOrder.GRLINE_RelativeLineAtGivenPosition grline) {
      writer.writeStartElement("GRLINE_RelativeLineAtGivenPosition");
      if (grline.startPoint != null) {
        writeIndent(writer, childLevel);
        writer.writeStartElement("startPoint");
        writeElement(writer, childLevel + 1, "xCoordinate", grline.startPoint.xCoordinate());
        writeElement(writer, childLevel + 1, "yCoordinate", grline.startPoint.yCoordinate());
        writeIndent(writer, childLevel);
        writer.writeEndElement();
      }
      if (grline.relativeOffsets != null && !grline.relativeOffsets.isEmpty()) {
        writeIndent(writer, childLevel);
        writer.writeStartElement("relativeOffsets");
        StringBuilder sb = new StringBuilder(grline.relativeOffsets.size() * 10);
        for (GAD_DrawingOrder.GOCA_RelativePoint rp : grline.relativeOffsets) {
          if (!sb.isEmpty()) {
            sb.append(' ');
          }
          sb.append((int) rp.xOffset()).append(' ').append((int) rp.yOffset());
        }
        writer.writeRaw(sb.toString());
        writer.writeEndElement();
      }
      writeIndent(writer, level);
      writer.writeEndElement();
    } else if (order instanceof GAD_DrawingOrder.GLGD_LinearGradient glgd) {
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
    } else if (order instanceof GAD_DrawingOrder.GRGD_RadialGradient grgd) {
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
    } else if (order instanceof GAD_DrawingOrder.DrawingOrder_HasPoints dohp) {
      String rootName = MnemonicPerformanceMonitor.getSimpleName(order.getClass());
      writer.writeStartElement(rootName);
      if (dohp.getPoints() != null && !dohp.getPoints().isEmpty()) {
        writeIndent(writer, childLevel);
        writer.writeStartElement("points");
        StringBuilder sb = new StringBuilder(dohp.getPoints().size() * 10);
        for (GAD_DrawingOrder.GOCA_Point p : dohp.getPoints()) {
          if (!sb.isEmpty()) {
            sb.append(' ');
          }
          sb.append(p.xCoordinate()).append(' ').append(p.yCoordinate());
        }
        writer.writeRaw(sb.toString());
        writer.writeEndElement();
      }
      writeIndent(writer, level);
      writer.writeEndElement();
    } else {
      JacksonXmlMapperProvider.getCachedWriter(order.getClass(), true, indentEnabled).writeValue(baseFragmentGenerator, order);
    }
  }

  private void writeIpdSegmentDirectly(XMLStreamWriter2 writer, IPD_Segment segment, int level) throws Exception {
    writeIndent(writer, level);
    int childLevel = level + 1;
    if (segment instanceof IPD_Segment.BeginSegment bs) {
      writer.writeStartElement("listOfSegments");
      if (segment.getSegmentType() != null) {
        writeElement(writer, childLevel, "segmentType", segment.getSegmentType().name());
      }
      writeElement(writer, childLevel, "lengthOfFollowingData", bs.getLengthOfFollowingData());
      if (bs.getName() != null) {
        writeBinaryElement(writer, childLevel, "name", bs.getName());
      }
      if (bs.getText() != null) {
        writeElement(writer, childLevel, "text", bs.getText());
      }
      writeIndent(writer, level);
      writer.writeEndElement();
    } else if (segment instanceof IPD_Segment.EndSegment) {
      writer.writeStartElement("listOfSegments");
      if (segment.getSegmentType() != null) {
        writeElement(writer, childLevel, "segmentType", segment.getSegmentType().name());
      }
      writeElement(writer, childLevel, "lengthOfFollowingData", segment.getLengthOfFollowingData());
      writeIndent(writer, level);
      writer.writeEndElement();
    } else if (segment instanceof IPD_Segment.ImageData id) {
      writer.writeStartElement("listOfSegments");
      if (segment.getSegmentType() != null) {
        writeElement(writer, childLevel, "segmentType", segment.getSegmentType().name());
      }
      writeElement(writer, childLevel, "lengthOfFollowingData", id.getLengthOfFollowingData());
      if (id.getImageData() != null) {
        writeBinaryElement(writer, childLevel, "imageData", id.getImageData());
      }
      writeIndent(writer, level);
      writer.writeEndElement();
    } else if (segment instanceof IPD_Segment.BandImageData bid) {
      writer.writeStartElement("listOfSegments");
      if (segment.getSegmentType() != null) {
        writeElement(writer, childLevel, "segmentType", segment.getSegmentType().name());
      }
      writeElement(writer, childLevel, "lengthOfFollowingData", bid.getLengthOfFollowingData());
      writeElement(writer, childLevel, "bandNumber", bid.getBandNumber());
      if (bid.getBandData() != null) {
        writeBinaryElement(writer, childLevel, "bandData", bid.getBandData());
      }
      writeIndent(writer, level);
      writer.writeEndElement();
    } else if (segment instanceof IPD_Segment.BeginImageContent bic) {
      writer.writeStartElement("listOfSegments");
      if (segment.getSegmentType() != null) {
        writeElement(writer, childLevel, "segmentType", segment.getSegmentType().name());
      }
      writeElement(writer, childLevel, "lengthOfFollowingData", bic.getLengthOfFollowingData());
      writeElement(writer, childLevel, "objectType", bic.getObjectType());
      writeIndent(writer, level);
      writer.writeEndElement();
    } else if (segment instanceof IPD_Segment.EndImageContent) {
      writer.writeStartElement("listOfSegments");
      if (segment.getSegmentType() != null) {
        writeElement(writer, childLevel, "segmentType", segment.getSegmentType().name());
      }
      writeElement(writer, childLevel, "lengthOfFollowingData", segment.getLengthOfFollowingData());
      writeIndent(writer, level);
      writer.writeEndElement();
    } else if (segment instanceof IPD_Segment.ImageSize is) {
      writer.writeStartElement("listOfSegments");
      if (segment.getSegmentType() != null) {
        writeElement(writer, childLevel, "segmentType", segment.getSegmentType().name());
      }
      writeElement(writer, childLevel, "lengthOfFollowingData", is.getLengthOfFollowingData());
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
      writer.writeStartElement("listOfSegments");
      if (segment.getSegmentType() != null) {
        writeElement(writer, childLevel, "segmentType", segment.getSegmentType().name());
      }
      writeElement(writer, childLevel, "lengthOfFollowingData", ie.getLengthOfFollowingData());
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
    } else if (segment instanceof IPD_Segment.IDESize ideSize) {
      writer.writeStartElement("listOfSegments");
      if (segment.getSegmentType() != null) {
        writeElement(writer, childLevel, "segmentType", segment.getSegmentType().name());
      }
      writeElement(writer, childLevel, "lengthOfFollowingData", ideSize.getLengthOfFollowingData());
      writeElement(writer, childLevel, "numberOfBitsInEachIDE", ideSize.numberOfBitsInEachIDE);
      writeIndent(writer, level);
      writer.writeEndElement();
    } else if (segment instanceof IPD_Segment.ImageLUTID lutId) {
      writer.writeStartElement("listOfSegments");
      if (segment.getSegmentType() != null) {
        writeElement(writer, childLevel, "segmentType", segment.getSegmentType().name());
      }
      writeElement(writer, childLevel, "lengthOfFollowingData", lutId.getLengthOfFollowingData());
      writeElement(writer, childLevel, "lutId", lutId.getLutId());
      writeIndent(writer, level);
      writer.writeEndElement();
    } else if (segment instanceof IPD_Segment.FunctionSetIdentification fsi) {
      writer.writeStartElement("listOfSegments");
      if (segment.getSegmentType() != null) {
        writeElement(writer, childLevel, "segmentType", segment.getSegmentType().name());
      }
      writeElement(writer, childLevel, "lengthOfFollowingData", fsi.getLengthOfFollowingData());
      writeElement(writer, childLevel, "category", fsi.category);
      writeElement(writer, childLevel, "functionSet", fsi.functionSet);
      writeIndent(writer, level);
      writer.writeEndElement();
    } else if (segment instanceof IPD_Segment.BeginTile) {
      writer.writeStartElement("listOfSegments");
      if (segment.getSegmentType() != null) {
        writeElement(writer, childLevel, "segmentType", segment.getSegmentType().name());
      }
      writeElement(writer, childLevel, "lengthOfFollowingData", segment.getLengthOfFollowingData());
      writeIndent(writer, level);
      writer.writeEndElement();
    } else if (segment instanceof IPD_Segment.EndTile) {
      writer.writeStartElement("listOfSegments");
      if (segment.getSegmentType() != null) {
        writeElement(writer, childLevel, "segmentType", segment.getSegmentType().name());
      }
      writeElement(writer, childLevel, "lengthOfFollowingData", segment.getLengthOfFollowingData());
      writeIndent(writer, level);
      writer.writeEndElement();
    } else if (segment instanceof IPD_Segment.TilePosition tp) {
      writer.writeStartElement("listOfSegments");
      if (segment.getSegmentType() != null) {
        writeElement(writer, childLevel, "segmentType", segment.getSegmentType().name());
      }
      writeElement(writer, childLevel, "lengthOfFollowingData", tp.getLengthOfFollowingData());
      writeElement(writer, childLevel, "horizontalOffset", tp.horizontalOffset);
      writeElement(writer, childLevel, "verticalOffset", tp.verticalOffset);
      writeIndent(writer, level);
      writer.writeEndElement();
    } else if (segment instanceof IPD_Segment.TileSize ts) {
      writer.writeStartElement("listOfSegments");
      if (segment.getSegmentType() != null) {
        writeElement(writer, childLevel, "segmentType", segment.getSegmentType().name());
      }
      writeElement(writer, childLevel, "lengthOfFollowingData", ts.getLengthOfFollowingData());
      writeElement(writer, childLevel, "horizontalSizeInImagePoints", ts.horizontalSizeInImagePoints);
      writeElement(writer, childLevel, "verticalSizeInImagePoints", ts.verticalSizeInImagePoints);
      if (ts.relativeResolution != null) {
        writeElement(writer, childLevel, "relativeResolution", ts.relativeResolution.name());
      }
      writeIndent(writer, level);
      writer.writeEndElement();
    } else if (segment instanceof IPD_Segment.TileSetColor tsc) {
      writer.writeStartElement("listOfSegments");
      if (segment.getSegmentType() != null) {
        writeElement(writer, childLevel, "segmentType", segment.getSegmentType().name());
      }
      writeElement(writer, childLevel, "lengthOfFollowingData", tsc.getLengthOfFollowingData());
      if (tsc.colorSpace != null) {
        writeElement(writer, childLevel, "colorSpace", tsc.colorSpace.name());
      }
      if (tsc.reserved3_5 != null) {
        writeBinaryElement(writer, childLevel, "reserved3_5", tsc.reserved3_5);
      }
      writeElement(writer, childLevel, "nrOfBitsIDEsComponent1", tsc.nrOfBitsIDEsComponent1);
      writeElement(writer, childLevel, "nrOfBitsIDEsComponent2", tsc.nrOfBitsIDEsComponent2);
      writeElement(writer, childLevel, "nrOfBitsIDEsComponent3", tsc.nrOfBitsIDEsComponent3);
      writeElement(writer, childLevel, "nrOfBitsIDEsComponent4", tsc.nrOfBitsIDEsComponent4);
      writeIndent(writer, level);
      writer.writeEndElement();
    } else if (segment instanceof IPD_Segment.IncludeTile it) {
      writer.writeStartElement("listOfSegments");
      if (segment.getSegmentType() != null) {
        writeElement(writer, childLevel, "segmentType", segment.getSegmentType().name());
      }
      writeElement(writer, childLevel, "lengthOfFollowingData", it.getLengthOfFollowingData());
      writeElement(writer, childLevel, "tileResourceLocalID", it.tileResourceLocalID);
      writeIndent(writer, level);
      writer.writeEndElement();
    } else if (segment instanceof IPD_Segment.TileTOC toc) {
      writer.writeStartElement("listOfSegments");
      if (segment.getSegmentType() != null) {
        writeElement(writer, childLevel, "segmentType", segment.getSegmentType().name());
      }
      writeElement(writer, childLevel, "lengthOfFollowingData", toc.getLengthOfFollowingData());
      if (toc.listOfRepeatingGroups != null) {
        writeIndent(writer, childLevel);
        writer.writeStartElement("listOfRepeatingGroups");
        for (IPD_Segment.TileTOC.TileTOC_RepeatingGroup rg : toc.listOfRepeatingGroups) {
          writeIndent(writer, childLevel + 1);
          writer.writeStartElement("listOfRepeatingGroups");
          int rgLevel = childLevel + 2;
          writeElement(writer, rgLevel, "horizontalOffset", rg.horizontalOffset());
          writeElement(writer, rgLevel, "verticalOffset", rg.verticalOffset());
          writeElement(writer, rgLevel, "horizontalSize", rg.horizontalSize());
          writeElement(writer, rgLevel, "verticalSize", rg.verticalSize());
          if (rg.relativeTileResolution() != null) {
            writeElement(writer, rgLevel, "relativeTileResolution", rg.relativeTileResolution().name());
          }
          if (rg.compressionAlgorithmID() != null) {
            writeElement(writer, rgLevel, "compressionAlgorithmID", rg.compressionAlgorithmID().name());
          }
          writeElement(writer, rgLevel, "offsetInBytesFromBeginSegmentToBeginTile", rg.offsetInBytesFromBeginSegmentToBeginTile());
          writeIndent(writer, childLevel + 1);
          writer.writeEndElement();
        }
        writeIndent(writer, childLevel);
        writer.writeEndElement();
      }
      writeIndent(writer, level);
      writer.writeEndElement();
    } else if (segment instanceof IPD_Segment.BeginTransparencyMask) {
      writer.writeStartElement("listOfSegments");
      if (segment.getSegmentType() != null) {
        writeElement(writer, childLevel, "segmentType", segment.getSegmentType().name());
      }
      writeElement(writer, childLevel, "lengthOfFollowingData", segment.getLengthOfFollowingData());
      writeIndent(writer, level);
      writer.writeEndElement();
    } else if (segment instanceof IPD_Segment.EndTransparencyMask) {
      writer.writeStartElement("listOfSegments");
      if (segment.getSegmentType() != null) {
        writeElement(writer, childLevel, "segmentType", segment.getSegmentType().name());
      }
      writeElement(writer, childLevel, "lengthOfFollowingData", segment.getLengthOfFollowingData());
      writeIndent(writer, level);
      writer.writeEndElement();
    } else if (segment instanceof IPD_Segment.SetBilevelImageColor sbic) {
      writer.writeStartElement("listOfSegments");
      if (segment.getSegmentType() != null) {
        writeElement(writer, childLevel, "segmentType", segment.getSegmentType().name());
      }
      writeElement(writer, childLevel, "lengthOfFollowingData", sbic.getLengthOfFollowingData());
      writeElement(writer, childLevel, "area", sbic.area);
      writeElement(writer, childLevel, "reserved3", sbic.reserved3);
      writeElement(writer, childLevel, "nameColor", sbic.nameColor);
      writeIndent(writer, level);
      writer.writeEndElement();
    } else if (segment instanceof IPD_Segment.SetExtendedBilevelImageColor sebic) {
      writer.writeStartElement("listOfSegments");
      if (segment.getSegmentType() != null) {
        writeElement(writer, childLevel, "segmentType", segment.getSegmentType().name());
      }
      writeElement(writer, childLevel, "lengthOfFollowingData", sebic.getLengthOfFollowingData());
      writeElement(writer, childLevel, "reserved2", sebic.reserved2);
      if (sebic.colorSpace != null) {
        writeElement(writer, childLevel, "colorSpace", sebic.colorSpace.name());
      }
      if (sebic.reserved4_7 != null) {
        writeBinaryElement(writer, childLevel, "reserved4_7", sebic.reserved4_7);
      }
      writeElement(writer, childLevel, "colSize1", sebic.colSize1);
      writeElement(writer, childLevel, "colSize2", sebic.colSize2);
      writeElement(writer, childLevel, "colSize3", sebic.colSize3);
      writeElement(writer, childLevel, "colSize4", sebic.colSize4);
      writeIndent(writer, level);
      writer.writeEndElement();
    } else if (segment instanceof IPD_Segment.IDEStructure ids) {
      writer.writeStartElement("listOfSegments");
      if (segment.getSegmentType() != null) {
        writeElement(writer, childLevel, "segmentType", segment.getSegmentType().name());
      }
      writeElement(writer, childLevel, "lengthOfFollowingData", ids.getLengthOfFollowingData());
      if (ids.flags != null) {
        writeIndent(writer, childLevel);
        writer.writeStartElement("flags");
        for (IPD_Segment.IDEStructure.IDEStructureFlag flag : ids.flags) {
          writeElement(writer, childLevel + 1, "flags", flag.name());
        }
        writeIndent(writer, childLevel);
        writer.writeEndElement();
      }
      if (ids.colorSpace != null) {
        writeElement(writer, childLevel, "colorSpace", ids.colorSpace.name());
      }
      if (ids.reserved4_6 != null) {
        writeBinaryElement(writer, childLevel, "reserved4_6", ids.reserved4_6);
      }
      if (ids.componentSizes != null) {
        writeIndent(writer, childLevel);
        writer.writeStartElement("componentSizes");
        for (Short s : ids.componentSizes) {
          writeElement(writer, childLevel + 1, "componentSizes", (int) s);
        }
        writeIndent(writer, childLevel);
        writer.writeEndElement();
      }
      writeIndent(writer, level);
      writer.writeEndElement();
    } else if (segment instanceof IPD_Segment.BandImage bi) {
      writer.writeStartElement("listOfSegments");
      if (segment.getSegmentType() != null) {
        writeElement(writer, childLevel, "segmentType", segment.getSegmentType().name());
      }
      writeElement(writer, childLevel, "lengthOfFollowingData", bi.getLengthOfFollowingData());
      writeIndent(writer, level);
      writer.writeEndElement();
    } else if (segment instanceof IPD_Segment.ExternalAlgorithmSpecification eas) {
      writer.writeStartElement("listOfSegments");
      if (segment.getSegmentType() != null) {
        writeElement(writer, childLevel, "segmentType", segment.getSegmentType().name());
      }
      writeElement(writer, childLevel, "lengthOfFollowingData", eas.getLengthOfFollowingData());
      writeElement(writer, childLevel, "reserved3", eas.reserved3);
      writeIndent(writer, level);
      writer.writeEndElement();
    } else if (segment instanceof IPD_Segment.ImageSubsampling issub) {
      writer.writeStartElement("listOfSegments");
      if (segment.getSegmentType() != null) {
        writeElement(writer, childLevel, "segmentType", segment.getSegmentType().name());
      }
      writeElement(writer, childLevel, "lengthOfFollowingData", issub.getLengthOfFollowingData());
      writeIndent(writer, level);
      writer.writeEndElement();
    } else if (segment instanceof IPD_Segment.nColorNames ncn) {
      writer.writeStartElement("listOfSegments");
      if (segment.getSegmentType() != null) {
        writeElement(writer, childLevel, "segmentType", segment.getSegmentType().name());
      }
      writeElement(writer, childLevel, "lengthOfFollowingData", ncn.getLengthOfFollowingData());
      if (ncn.getText() != null) {
        writeElement(writer, childLevel, "text", ncn.getText());
      }
      writeIndent(writer, level);
      writer.writeEndElement();
    } else if (segment instanceof IPD_Segment.UnknownSegmentLong usl) {
      writer.writeStartElement("listOfSegments");
      if (segment.getSegmentType() != null) {
        writeElement(writer, childLevel, "segmentType", segment.getSegmentType().name());
      }
      writeElement(writer, childLevel, "lengthOfFollowingData", usl.getLengthOfFollowingData());
      if (usl.getText() != null) {
        writeElement(writer, childLevel, "text", usl.getText());
      }
      writeIndent(writer, level);
      writer.writeEndElement();
    } else if (segment instanceof IPD_Segment.UnknownSegmentExtended use) {
      writer.writeStartElement("listOfSegments");
      if (segment.getSegmentType() != null) {
        writeElement(writer, childLevel, "segmentType", segment.getSegmentType().name());
      }
      writeElement(writer, childLevel, "lengthOfFollowingData", use.getLengthOfFollowingData());
      if (use.getText() != null) {
        writeElement(writer, childLevel, "text", use.getText());
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
    if (segments != null && !segments.isEmpty()) {
      writeIndent(baseXsw, childLevel);
      baseXsw.writeStartElement("listOfSegments");
      for (IPD_Segment segment : segments) {
        writeIpdSegmentDirectly(baseXsw, segment, childLevel + 1);
      }
      writeIndent(baseXsw, childLevel);
      baseXsw.writeEndElement();
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
        writeElement(writer, childLevel, "functionSetIdentifier",
            fsi.getFunctionSetIdentifier().name());
      }
      writeIndent(writer, level);
      writer.writeEndElement();
    } else if (sdf instanceof IDD_SelfDefiningField.IDEStructure ides) {
      writer.writeStartElement("IDEStructure");
      writeElement(writer, childLevel, "flags", (int) ides.getFlags());
      if (ides.getColorSpace() != null) {
        writeElement(writer, childLevel, "colorSpace", ides.getColorSpace().name());
      }
      if (ides.getComponentSizes() != null) {
        writeBinaryElement(writer, childLevel, "componentSizes", ides.getComponentSizes());
      }
      writeIndent(writer, level);
      writer.writeEndElement();
    } else if (sdf instanceof IDD_SelfDefiningField.UnknownSelfDefiningField usdf) {
      writer.writeStartElement("UnknownSelfDefiningField");
      writeElement(writer, childLevel, "unknownFieldType", (int) usdf.getUnknownFieldType());
      if (usdf.getData() != null) {
        writeBinaryElement(writer, childLevel, "data", usdf.getData());
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

  private void resetPtocaState() {
    this.inlinePos = 0;
    this.baselinePos = 0;
    this.inlineOri = AFPOrientation.ori0;
    this.baselineOri = AFPOrientation.ori90;
    this.inlineMargin = 0;
    this.baselineIncrement = 0;
  }

  private int getAfpX() {
    return CoordinateTransformer.getAfpX(inlinePos, baselinePos, inlineOri, baselineOri);
  }

  private int getAfpY() {
    return CoordinateTransformer.getAfpY(inlinePos, baselinePos, inlineOri, baselineOri);
  }

  private void writeBpgDirectly(com.mgz.afp.modca.BPG_BeginPage bpg, int level) throws Exception {
    MnemonicPerformanceMonitor.startWriteWithMnemonic("BPG");
    baseXsw.writeStartElement("BPG_BeginPage");
    baseXsw.writeIntAttribute(null, null, "page", currentPageNumber);
    int childLevel = level + 1;
    writeElement(baseXsw, childLevel, "name", bpg.getName());
    if (bpg.getTriplets() != null && !bpg.getTriplets().isEmpty()) {
      for (Triplet triplet : bpg.getTriplets()) {
        writeTriplet(baseXsw, triplet, childLevel);
      }
    }
    if (bpg.getText() != null) {
      writeElement(baseXsw, childLevel, "text", bpg.getText());
    }
    writeIndent(baseXsw, level);
    baseXsw.writeEndElement();
    MnemonicPerformanceMonitor.endWrite();
  }

  private void writeEpgDirectly(com.mgz.afp.modca.EPG_EndPage epg, int level) throws Exception {
    MnemonicPerformanceMonitor.startWriteWithMnemonic("EPG");
    baseXsw.writeStartElement("EPG_EndPage");
    baseXsw.writeIntAttribute(null, null, "page", currentPageNumber);
    int childLevel = level + 1;
    writeElement(baseXsw, childLevel, "name", epg.getName());
    if (epg.getTriplets() != null && !epg.getTriplets().isEmpty()) {
      for (Triplet triplet : epg.getTriplets()) {
        writeTriplet(baseXsw, triplet, childLevel);
      }
    }
    if (epg.getText() != null) {
      writeElement(baseXsw, childLevel, "text", epg.getText());
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

  private void writePtdFormat1Directly(PTD_PresentationTextDataDescriptor_Format1 ptd, int level)
      throws Exception {
    MnemonicPerformanceMonitor.startWriteWithMnemonic("PTD");
    baseXsw.writeEmptyElement("PTD_PresentationTextDataDescriptor_Format1");
    if (ptd.getxUnitBase() != null) {
      baseXsw.writeAttribute("xUnitBase", ptd.getxUnitBase().name());
    }
    if (ptd.getyUnitBase() != null) {
      baseXsw.writeAttribute("yUnitBase", ptd.getyUnitBase().name());
    }
    baseXsw.writeIntAttribute(null, null, "xUnitsPerUnitBase", (int) ptd.getxUnitsPerUnitBase());
    baseXsw.writeIntAttribute(null, null, "yUnitsPerUnitBase", (int) ptd.getyUnitsPerUnitBase());
    baseXsw.writeIntAttribute(null, null, "xSize", (int) ptd.getxSize());
    baseXsw.writeIntAttribute(null, null, "ySize", (int) ptd.getySize());
    if (ptd.getReserved10_11() != null) {
      baseXsw.writeAttribute("reserved10_11", UtilCharacterEncoding.bytesToHexString(ptd.getReserved10_11()));
    }
    MnemonicPerformanceMonitor.endWrite();
  }

  private void writePtdFormat2Directly(PTD_PresentationTextDataDescriptor_Format2 ptd, int level) throws Exception {
    MnemonicPerformanceMonitor.startWriteWithMnemonic("PTD");
    baseXsw.writeStartElement("PTD_PresentationTextDataDescriptor_Format2");
    int childLevel = level + 1;
    if (ptd.getxUnitBase() != null) {
      writeElement(baseXsw, childLevel, "xUnitBase", ptd.getxUnitBase().name());
    }
    if (ptd.getyUnitBase() != null) {
      writeElement(baseXsw, childLevel, "yUnitBase", ptd.getyUnitBase().name());
    }
    writeElement(baseXsw, childLevel, "xUnitsPerUnitBase", ptd.getxUnitsPerUnitBase());
    writeElement(baseXsw, childLevel, "yUnitsPerUnitBase", ptd.getyUnitsPerUnitBase());
    writeElement(baseXsw, childLevel, "xSize", ptd.getxSize());
    writeElement(baseXsw, childLevel, "ySize", ptd.getySize());
    if (ptd.getReserved12_13() != null) {
      writeBinaryElement(baseXsw, childLevel, "reserved12_13", ptd.getReserved12_13());
    }
    if (ptd.getControlSequences() != null) {
      for (PTOCAControlSequence cs : ptd.getControlSequences()) {
        writeIndent(baseXsw, childLevel);
        writeControlSequence(cs, childLevel);
      }
    }
    writeIndent(baseXsw, level);
    baseXsw.writeEndElement();
    MnemonicPerformanceMonitor.endWrite();
  }

  private void writeGddDirectly(GDD_GraphicsDataDescriptor gdd, int level) throws Exception {
    MnemonicPerformanceMonitor.startWriteWithMnemonic("GDD");
    baseXsw.writeStartElement("GDD_GraphicsDataDescriptor");
    int childLevel = level + 1;
    if (gdd.getGddParameters() != null) {
      for (GDD_Parameter param : gdd.getGddParameters()) {
        writeGddParameterDirectly(baseXsw, param, childLevel);
      }
    }
    writeIndent(baseXsw, level);
    baseXsw.writeEndElement();
    MnemonicPerformanceMonitor.endWrite();
  }

  private void writeGddParameterDirectly(XMLStreamWriter2 writer, GDD_Parameter param, int level) throws Exception {
    int childLevel = level + 1;
    writeIndent(writer, level);
    if (param instanceof GDD_Parameter.SetCurrentDefaultInstruction scd) {
      writer.writeStartElement(MnemonicPerformanceMonitor.getSimpleName(scd.getClass()));
      writeElement(writer, childLevel, "parameterType", scd.getParameterType());
      writeElement(writer, childLevel, "lengthOfFollowingData", scd.getLengthOfFollowingData());
      writeElement(writer, childLevel, "attributeType", scd.getAttributeType());
      if (scd.getMask() != null) {
        writeBinaryElement(writer, childLevel, "mask", com.mgz.util.UtilBinaryDecoding.bitSetToByteArray(scd.getMask(), 2));
      }
      if (scd.getFlag() != null) {
        writeElement(writer, childLevel, "flag", scd.getFlag().name());
      }

      if (scd instanceof GDD_Parameter.SetCurrentDefaultInstruction.DrawingAttributes da) {
        if (da.getColor() != null) {
          writeElement(writer, childLevel, "color", da.getColor().name());
        }
        if (da.getForegroundMix() != null) {
          writeElement(writer, childLevel, "foregroundMix", da.getForegroundMix().name());
        }
        if (da.getBackgroundMix() != null) {
          writeElement(writer, childLevel, "backgroundMix", da.getBackgroundMix().name());
        }
      } else if (scd instanceof GDD_Parameter.SetCurrentDefaultInstruction.LineAttributes la) {
        if (la.getLineType() != null) {
          writeElement(writer, childLevel, "lineType", la.getLineType().name());
        }
        if (la.getLineWidth() != null) {
          writeElement(writer, childLevel, "lineWidth", la.getLineWidth());
        }
        if (la.getLineEnd() != null) {
          writeElement(writer, childLevel, "lineEnd", la.getLineEnd().name());
        }
        if (la.getLineJoin() != null) {
          writeElement(writer, childLevel, "lineJoin", la.getLineJoin().name());
        }
      } else if (scd instanceof GDD_Parameter.SetCurrentDefaultInstruction.CharacterAttributes ca) {
        if (ca.getCharacterAngleXY() != null) {
          writeElement(writer, childLevel, "characterAngleXY", ca.getCharacterAngleXY());
        }
        if (ca.getCharacterCellSizeWH() != null) {
          writeElement(writer, childLevel, "characterCellSizeWH", ca.getCharacterCellSizeWH());
        }
        if (ca.getCharacterDirection() != null) {
          writeElement(writer, childLevel, "characterDirection", ca.getCharacterDirection());
        }
        if (ca.getCharacterPrecision() != null) {
          writeElement(writer, childLevel, "characterPrecision", ca.getCharacterPrecision());
        }
        if (ca.getCharacterSet() != null) {
          writeElement(writer, childLevel, "characterSet", ca.getCharacterSet());
        }
        if (ca.getCharacterShearXY() != null) {
          writeElement(writer, childLevel, "characterShearXY", ca.getCharacterShearXY());
        }
      } else if (scd instanceof GDD_Parameter.SetCurrentDefaultInstruction.MarkerAttributes ma) {
        if (ma.getMarkerPrecision() != null) {
          writeElement(writer, childLevel, "markerPrecision", ma.getMarkerPrecision());
        }
        if (ma.getMarkerSet() != null) {
          writeElement(writer, childLevel, "markerSet", ma.getMarkerSet());
        }
        if (ma.getMarkerSymbol() != null) {
          writeElement(writer, childLevel, "markerSymbol", ma.getMarkerSymbol());
        }
      } else if (scd instanceof GDD_Parameter.SetCurrentDefaultInstruction.PatternAttributes pa) {
        if (pa.getPatternSet() != null) {
          writeElement(writer, childLevel, "patternSet", pa.getPatternSet());
        }
        if (pa.getPatternSymbol() != null) {
          writeElement(writer, childLevel, "patternSymbol", pa.getPatternSymbol());
        }
      } else if (scd instanceof GDD_Parameter.SetCurrentDefaultInstruction.ArcParameters ap) {
        if (ap.getArcTransformP() != null) {
          writeElement(writer, childLevel, "arcTransformP", ap.getArcTransformP());
        }
        if (ap.getArcTransformQ() != null) {
          writeElement(writer, childLevel, "arcTransformQ", ap.getArcTransformQ());
        }
        if (ap.getArcTransformR() != null) {
          writeElement(writer, childLevel, "arcTransformR", ap.getArcTransformR());
        }
        if (ap.getArcTransformS() != null) {
          writeElement(writer, childLevel, "arcTransformS", ap.getArcTransformS());
        }
      } else if (scd instanceof GDD_Parameter.SetCurrentDefaultInstruction.ProcessColorAttributes pca) {
        if (pca.getForegroundMix() != null) {
          writeElement(writer, childLevel, "foregroundMix", pca.getForegroundMix().name());
        }
        if (pca.getBackgroundMix() != null) {
          writeElement(writer, childLevel, "backgroundMix", pca.getBackgroundMix().name());
        }
        if (pca.getProcessColor() != null) {
          writeBinaryElement(writer, childLevel, "processColor", pca.getProcessColor());
        }
      } else if (scd instanceof GDD_Parameter.SetCurrentDefaultInstruction.NormalLineWidthAttribute nlw) {
        if (nlw.getNormalLineWidth() != null) {
          writeElement(writer, childLevel, "normalLineWidth", nlw.getNormalLineWidth());
        }
      }

      writeIndent(writer, level);
      writer.writeEndElement();
    } else if (param instanceof GDD_Parameter.WindowSpecification ws) {
      writer.writeStartElement("WindowSpecification");
      writeElement(writer, childLevel, "parameterType", ws.getParameterType());
      writeElement(writer, childLevel, "lengthOfFollowingData", ws.getLengthOfFollowingData());
      if (ws.getFlags() != null) {
        writeElement(writer, childLevel, "flags", ws.getFlags().name());
      }
      writeElement(writer, childLevel, "reserved3", ws.getReserved3());
      writeElement(writer, childLevel, "geometricParameterFormat", ws.getGeometricParameterFormat());
      if (ws.getUnitBaseGPS() != null) {
        writeElement(writer, childLevel, "unitBaseGPS", ws.getUnitBaseGPS().name());
      }
      writeElement(writer, childLevel, "unitsPerUnitBaseX", ws.getUnitsPerUnitBaseX());
      writeElement(writer, childLevel, "unitsPerUnitBaseY", ws.getUnitsPerUnitBaseY());
      writeElement(writer, childLevel, "imageResolutionXY", ws.getImageResolutionXY());
      writeElement(writer, childLevel, "leftEdgeOfGPSWindow", ws.getLeftEdgeOfGPSWindow());
      writeElement(writer, childLevel, "rightEdgeOfGPSWindow", ws.getRightEdgeOfGPSWindow());
      writeElement(writer, childLevel, "bottomEdgeOfGPSWindow", ws.getBottomEdgeOfGPSWindow());
      writeElement(writer, childLevel, "topEdgeOfGPSWindow", ws.getTopEdgeOfGPSWindow());
      if (ws.getReservedBytesObsolete() != null) {
        writeBinaryElement(writer, childLevel, "reservedBytesObsolete", ws.getReservedBytesObsolete());
      }
      writeIndent(writer, level);
      writer.writeEndElement();
    } else if (param instanceof GDD_Parameter.DrawingOrderSubsetParameterRetired dosp) {
      writer.writeStartElement("DrawingOrderSubsetParameterRetired");
      writeElement(writer, childLevel, "parameterType", dosp.getParameterType());
      writeElement(writer, childLevel, "lengthOfFollowingData", dosp.getLengthOfFollowingData());
      writeElement(writer, childLevel, "drawingOrderSubset", dosp.drawingOrderSubset);
      if (dosp.reserved3_4 != null) {
        writeBinaryElement(writer, childLevel, "reserved3_4", dosp.reserved3_4);
      }
      writeElement(writer, childLevel, "subsetLevel", dosp.subsetLevel);
      writeElement(writer, childLevel, "version", dosp.version);
      writeElement(writer, childLevel, "lengthOfFollowingField", dosp.lengthOfFollowingField);
      writeElement(writer, childLevel, "coordinateFormat", dosp.coordinateFormat);
      writeIndent(writer, level);
      writer.writeEndElement();
    } else {
      JacksonXmlMapperProvider.getCachedWriter(param.getClass(), true, indentEnabled).writeValue(baseFragmentGenerator, param);
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
    if (order.getPoints() != null && !order.getPoints().isEmpty()) {
      writeIndent(writer, childLevel);
      writer.writeStartElement("points");
      StringBuilder sb = new StringBuilder(order.getPoints().size() * 10);
      for (GAD_DrawingOrder.GOCA_Point p : order.getPoints()) {
        if (!sb.isEmpty()) {
          sb.append(' ');
        }
        sb.append(p.xCoordinate()).append(' ').append(p.yCoordinate());
      }
      writer.writeRaw(sb.toString());
      writer.writeEndElement();
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
      long start = com.mgz.util.PTXPerformanceMonitor.isInPtx() ? System.nanoTime() : 0;
      if (writer instanceof AfpXmlStreamWriter afpXsw) {
        XmlIndenter.writeIndent(afpXsw, level);
      } else {
        writer.writeRaw(XmlIndenter.getIndent(level));
      }
      if (start > 0) {
        com.mgz.util.PTXPerformanceMonitor.recordPtxIndentation(System.nanoTime() - start);
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

  private void writeIobDirectly(IOB_IncludeObject iob, int level) throws Exception {
    MnemonicPerformanceMonitor.startWriteWithMnemonic("IOB");
    baseXsw.writeStartElement("IOB_IncludeObject");
    if (currentPageNumber > 0) {
      baseXsw.writeIntAttribute(null, null, "page", currentPageNumber);
    }
    int childLevel = level + 1;
    writeElement(baseXsw, childLevel, "name", iob.getName());
    writeElement(baseXsw, childLevel, "reserved8", (int) iob.getReserved8());
    if (iob.getObjectType() != null) {
      writeElement(baseXsw, childLevel, "objectType", iob.getObjectType().name());
    }
    writeElement(baseXsw, childLevel, "xOrigin", iob.getxOrigin());
    writeElement(baseXsw, childLevel, "yOrigin", iob.getyOrigin());
    if (iob.getxRotation() != null) {
      writeElement(baseXsw, childLevel, "xRotation", iob.getxRotation().name());
    }
    if (iob.getyRotation() != null) {
      writeElement(baseXsw, childLevel, "yRotation", iob.getyRotation().name());
    }
    writeElement(baseXsw, childLevel, "xOriginOfContent", iob.getxOriginOfContent());
    writeElement(baseXsw, childLevel, "yOriginOfContent", iob.getyOriginOfContent());
    writeElement(baseXsw, childLevel, "referenceCoordinateSystem", (int) iob.getReferenceCoordinateSystem());
    if (iob.getTriplets() != null && !iob.getTriplets().isEmpty()) {
      writeIndent(baseXsw, childLevel);
      baseXsw.writeStartElement("triplets");
      for (Triplet triplet : iob.getTriplets()) {
        writeTriplet(baseXsw, triplet, childLevel + 1);
      }
      writeIndent(baseXsw, childLevel);
      baseXsw.writeEndElement();
    }
    if (iob.getText() != null) {
      writeElement(baseXsw, childLevel, "text", iob.getText());
    }
    writeIndent(baseXsw, level);
    baseXsw.writeEndElement();
    MnemonicPerformanceMonitor.endWrite();
  }

  private void writeCfcDirectly(CFC_CodedFontControl cfc, int level) throws Exception {
    MnemonicPerformanceMonitor.startWriteWithMnemonic("CFC");
    baseXsw.writeStartElement("CFC_CodedFontControl");
    int childLevel = level + 1;
    writeElement(baseXsw, childLevel, "cfiRepeatingGroupLength", (int) cfc.getCfiRepeatingGroupLength());
    writeElement(baseXsw, childLevel, "retired", (int) cfc.getRetired());
    if (cfc.getTriplets() != null) {
      for (Triplet triplet : cfc.getTriplets()) {
        writeTriplet(baseXsw, triplet, childLevel);
      }
    }
    writeIndent(baseXsw, level);
    baseXsw.writeEndElement();
    MnemonicPerformanceMonitor.endWrite();
  }

  private void writeCfiDirectly(CFI_CodedFontIndex cfi, int level) throws Exception {
    MnemonicPerformanceMonitor.startWriteWithMnemonic("CFI");
    baseXsw.writeStartElement("CFI_CodedFontIndex");
    int childLevel = level + 1;
    if (cfi.getCfiRepeatingGroups() != null) {
      writeIndent(baseXsw, childLevel);
      baseXsw.writeStartElement("cfiRepeatingGroups");
      for (CFI_CodedFontIndex.CFIRepeatingGroup rg : cfi.getCfiRepeatingGroups()) {
        writeIndent(baseXsw, childLevel + 1);
        baseXsw.writeStartElement("cfiRepeatingGroups");
        int rgLevel = childLevel + 2;
        writeElement(baseXsw, rgLevel, "fcsName", rg.getFcsName());
        writeElement(baseXsw, rgLevel, "cpName", rg.getCpName());
        writeElement(baseXsw, rgLevel, "svSize", rg.getSvSize());
        writeElement(baseXsw, rgLevel, "shScale", rg.getShScale());
        writeElement(baseXsw, rgLevel, "section", (int) rg.getSection());
        writeIndent(baseXsw, childLevel + 1);
        baseXsw.writeEndElement();
      }
      writeIndent(baseXsw, childLevel);
      baseXsw.writeEndElement();
    }
    writeIndent(baseXsw, level);
    baseXsw.writeEndElement();
    MnemonicPerformanceMonitor.endWrite();
  }

  private void writeCpcDirectly(CPC_CodePageControl cpc, int level) throws Exception {
    MnemonicPerformanceMonitor.startWriteWithMnemonic("CPC");
    baseXsw.writeStartElement("CPC_CodePageControl");
    int childLevel = level + 1;
    writeElement(baseXsw, childLevel, "defaultGraphicCharacterGlobalID", cpc.getDefaultGraphicCharacterGlobalID());

    if (cpc.getDefaultCharacterUseFlags() != null) {
      writeIndent(baseXsw, childLevel);
      baseXsw.writeStartElement("defaultCharacterUseFlags");
      for (CPC_CodePageControl.DefaultCharacterUseFlag flag : cpc.getDefaultCharacterUseFlags()) {
        writeElement(baseXsw, childLevel + 1, "defaultCharacterUseFlags", flag.name());
      }
      writeIndent(baseXsw, childLevel);
      baseXsw.writeEndElement();
    }

    if (cpc.getCpiRepeatingGroupLength() != null) {
      writeElement(baseXsw, childLevel, "cpiRepeatingGroupLength", cpc.getCpiRepeatingGroupLength().name());
    }
    writeElement(baseXsw, childLevel, "spaceCharacterSectionNumber", (int) cpc.getSpaceCharacterSectionNumber());
    writeElement(baseXsw, childLevel, "spaceCharacterCodePoint", (int) cpc.getSpaceCharacterCodePoint());

    if (cpc.getCodePageUseFlags() != null) {
      writeIndent(baseXsw, childLevel);
      baseXsw.writeStartElement("codePageUseFlags");
      for (CPC_CodePageControl.CodePageUseFlag flag : cpc.getCodePageUseFlags()) {
        writeElement(baseXsw, childLevel + 1, "codePageUseFlags", flag.name());
      }
      writeIndent(baseXsw, childLevel);
      baseXsw.writeEndElement();
    }

    writeElement(baseXsw, childLevel, "unicodeScalarValue", cpc.getUnicodeScalarValue());

    writeIndent(baseXsw, level);
    baseXsw.writeEndElement();
    MnemonicPerformanceMonitor.endWrite();
  }

  private void writeCpiDirectly(CPI_CodePageIndex cpi, int level) throws Exception {
    MnemonicPerformanceMonitor.startWriteWithMnemonic("CPI");
    baseXsw.writeStartElement("CPI_CodePageIndex");
    int childLevel = level + 1;
    if (cpi.getRepeatingGroups() != null) {
      writeIndent(baseXsw, childLevel);
      baseXsw.writeStartElement("repeatingGroups");
      for (CPI_CodePageIndex.CPI_RepeatingGroup rg : cpi.getRepeatingGroups()) {
        writeIndent(baseXsw, childLevel + 1);
        baseXsw.writeStartElement("repeatingGroups");
        int rgLevel = childLevel + 2;
        writeElement(baseXsw, rgLevel, "graphicCharacterGID", rg.getGraphicCharacterGID());
        if (rg.getGraphicCharacterUseFlags() != null) {
          writeIndent(baseXsw, rgLevel);
          baseXsw.writeStartElement("graphicCharacterUseFlags");
          for (CPI_CodePageIndex.GraphicCharacterUseFlag flag : rg.getGraphicCharacterUseFlags()) {
            writeElement(baseXsw, rgLevel + 1, "graphicCharacterUseFlags", flag.name());
          }
          writeIndent(baseXsw, rgLevel);
          baseXsw.writeEndElement();
        }
        writeElement(baseXsw, rgLevel, "codePoint", rg.getCodePoint());
        if (rg.getUnicodeScalarValues() != null) {
          writeIndent(baseXsw, rgLevel);
          baseXsw.writeStartElement("unicodeScalarValues");
          for (Long usv : rg.getUnicodeScalarValues()) {
            writeElement(baseXsw, rgLevel + 1, "unicodeScalarValues", usv);
          }
          writeIndent(baseXsw, rgLevel);
          baseXsw.writeEndElement();
        }
        writeIndent(baseXsw, childLevel + 1);
        baseXsw.writeEndElement();
      }
      writeIndent(baseXsw, childLevel);
      baseXsw.writeEndElement();
    }
    writeIndent(baseXsw, level);
    baseXsw.writeEndElement();
    MnemonicPerformanceMonitor.endWrite();
  }

  private void writeFndDirectly(FND_FontDescriptor fnd, int level) throws Exception {
    MnemonicPerformanceMonitor.startWriteWithMnemonic("FND");
    baseXsw.writeStartElement("FND_FontDescriptor");
    int childLevel = level + 1;
    writeElement(baseXsw, childLevel, "typefaceDescription", fnd.getTypefaceDescription());
    writeElement(baseXsw, childLevel, "fontWeightClass", (int) fnd.getFontWeightClass());
    writeElement(baseXsw, childLevel, "fontWidthClass", (int) fnd.getFontWidthClass());
    writeElement(baseXsw, childLevel, "maxVerticalSize", (int) fnd.getMaxVerticalSize());
    writeElement(baseXsw, childLevel, "nominalVerticalSize", (int) fnd.getNominalVerticalSize());
    writeElement(baseXsw, childLevel, "minimumVerticalSize", (int) fnd.getMinimumVerticalSize());
    writeElement(baseXsw, childLevel, "maxHorizontalSize", (int) fnd.getMaxHorizontalSize());
    writeElement(baseXsw, childLevel, "nominalHorizontalSize", (int) fnd.getNominalHorizontalSize());
    writeElement(baseXsw, childLevel, "minimumHorizontalSize", (int) fnd.getMinimumHorizontalSize());
    writeElement(baseXsw, childLevel, "designGeneralClass", (int) fnd.getDesignGeneralClass());
    writeElement(baseXsw, childLevel, "designSubclass", (int) fnd.getDesignSubclass());
    writeElement(baseXsw, childLevel, "designSpecificGroup", (int) fnd.getDesignSpecificGroup());
    // FND doesn't have public getter for reserved49_63 or reserved66_75, and we don't usually serialize them if they are just reserved zero bytes
    writeElement(baseXsw, childLevel, "fontDesignFlags", (int) fnd.getFontDesignFlags());
    writeElement(baseXsw, childLevel, "gcsgid", (int) fnd.getGcsgid());
    writeElement(baseXsw, childLevel, "fgid", (int) fnd.getFgid());
    writeElement(baseXsw, childLevel, "text", fnd.getText());
    if (fnd.getTriplets() != null) {
      for (Triplet t : fnd.getTriplets()) {
        writeTriplet(baseXsw, t, childLevel);
      }
    }
    writeIndent(baseXsw, level);
    baseXsw.writeEndElement();
    MnemonicPerformanceMonitor.endWrite();
  }

  private void writeFniDirectly(FNI_FontIndex fni, int level) throws Exception {
    MnemonicPerformanceMonitor.startWriteWithMnemonic("FNI");
    baseXsw.writeStartElement("FNI_FontIndex");
    int childLevel = level + 1;
    if (fni.getRepeatingGroups() != null) {
      writeIndent(baseXsw, childLevel);
      baseXsw.writeStartElement("repeatingGroups");
      for (FNI_FontIndex.FNI_RepeatingGroup rg : fni.getRepeatingGroups()) {
        writeIndent(baseXsw, childLevel + 1);
        baseXsw.writeStartElement("repeatingGroups");
        int rgLevel = childLevel + 2;
        writeElement(baseXsw, rgLevel, "gcgid", rg.getGcgid());
        writeElement(baseXsw, rgLevel, "charIncrement", (int) rg.getCharIncrement());
        writeElement(baseXsw, rgLevel, "ascenderHeight", (int) rg.getAscenderHeight());
        writeElement(baseXsw, rgLevel, "descenderDepth", (int) rg.getDescenderDepth());
        writeElement(baseXsw, rgLevel, "kernableCharacterFlags", (int) rg.getKernableCharacterFlags());
        writeElement(baseXsw, rgLevel, "reserved15", (int) rg.getReserved15());
        writeElement(baseXsw, rgLevel, "baselineOffset", (int) rg.getBaselineOffset());
        writeElement(baseXsw, rgLevel, "aspace", (int) rg.getASpace());
        writeElement(baseXsw, rgLevel, "bspace", (int) rg.getBSpace());
        writeElement(baseXsw, rgLevel, "cspace", (int) rg.getCSpace());
        // reserved26_27 is not public
        writeIndent(baseXsw, childLevel + 1);
        baseXsw.writeEndElement();
      }
      writeIndent(baseXsw, childLevel);
      baseXsw.writeEndElement();
    }
    writeIndent(baseXsw, level);
    baseXsw.writeEndElement();
    MnemonicPerformanceMonitor.endWrite();
  }

  private void writeFnmDirectly(FNM_FontPatternsMap fnm, int level) throws Exception {
    MnemonicPerformanceMonitor.startWriteWithMnemonic("FNM");
    baseXsw.writeStartElement("FNM_FontPatternsMap");
    int childLevel = level + 1;
    if (fnm.getRepeatingGroups() != null) {
      writeIndent(baseXsw, childLevel);
      baseXsw.writeStartElement("repeatingGroups");
      for (FNM_FontPatternsMap.FNM_RepeatingGroup rg : fnm.getRepeatingGroups()) {
        writeIndent(baseXsw, childLevel + 1);
        baseXsw.writeStartElement("repeatingGroups");
        int rgLevel = childLevel + 2;
        writeElement(baseXsw, rgLevel, "charDataOffset", rg.getCharDataOffset());
        writeElement(baseXsw, rgLevel, "charDataCount", rg.getCharDataCount());
        writeIndent(baseXsw, childLevel + 1);
        baseXsw.writeEndElement();
      }
      writeIndent(baseXsw, childLevel);
      baseXsw.writeEndElement();
    }
    writeIndent(baseXsw, level);
    baseXsw.writeEndElement();
    MnemonicPerformanceMonitor.endWrite();
  }

  private void writeFnoDirectly(FNO_FontOrientation fno, int level) throws Exception {
    MnemonicPerformanceMonitor.startWriteWithMnemonic("FNO");
    baseXsw.writeStartElement("FNO_FontOrientation");
    int childLevel = level + 1;
    if (fno.getRepeatingGroups() != null) {
      writeIndent(baseXsw, childLevel);
      baseXsw.writeStartElement("repeatingGroups");
      for (FNO_FontOrientation.FNO_RepeatingGroup rg : fno.getRepeatingGroups()) {
        writeIndent(baseXsw, childLevel + 1);
        baseXsw.writeStartElement("repeatingGroups");
        int rgLevel = childLevel + 2;
        if (rg.getCharRotation() != null) {
          writeElement(baseXsw, rgLevel, "charRotation", rg.getCharRotation().name());
        }
        writeElement(baseXsw, rgLevel, "maxBaselineOffset", (int) rg.getMaxBaselineOffset());
        writeElement(baseXsw, rgLevel, "maxCharacterIncrement", (int) rg.getMaxCharacterIncrement());
        writeElement(baseXsw, rgLevel, "spaceCharacterIncrement", (int) rg.getSpaceCharacterIncrement());
        writeElement(baseXsw, rgLevel, "maxBaselineExtent", (int) rg.getMaxBaselineExtent());
        if (rg.getControlFlags() != null) {
          writeIndent(baseXsw, rgLevel);
          baseXsw.writeStartElement("controlFlags");
          for (FNO_FontOrientation.FnoControlFlag flag : rg.getControlFlags()) {
            writeElement(baseXsw, rgLevel + 1, "controlFlags", flag.name());
          }
          writeIndent(baseXsw, rgLevel);
          baseXsw.writeEndElement();
        }
        writeElement(baseXsw, rgLevel, "emSpaceIncrement", (int) rg.getEmSpaceIncrement());
        writeElement(baseXsw, rgLevel, "figureSpaceIncrement", (int) rg.getFigureSpaceIncrement());
        writeElement(baseXsw, rgLevel, "nominalCharacterIncrement", (int) rg.getNominalCharacterIncrement());
        writeElement(baseXsw, rgLevel, "defaultBaselineIncrement", rg.getDefaultBaselineIncrement());
        writeElement(baseXsw, rgLevel, "minASpace", (int) rg.getMinASpace());
        writeIndent(baseXsw, childLevel + 1);
        baseXsw.writeEndElement();
      }
      writeIndent(baseXsw, childLevel);
      baseXsw.writeEndElement();
    }
    writeIndent(baseXsw, level);
    baseXsw.writeEndElement();
    MnemonicPerformanceMonitor.endWrite();
  }

  private void writeFnpDirectly(FNP_FontPosition fnp, int level) throws Exception {
    MnemonicPerformanceMonitor.startWriteWithMnemonic("FNP");
    baseXsw.writeStartElement("FNP_FontPosition");
    int childLevel = level + 1;
    if (fnp.getRepeatingGroups() != null) {
      writeIndent(baseXsw, childLevel);
      baseXsw.writeStartElement("repeatingGroups");
      for (FNP_FontPosition.FNP_RepeatingGroup rg : fnp.getRepeatingGroups()) {
        writeIndent(baseXsw, childLevel + 1);
        baseXsw.writeStartElement("repeatingGroups");
        int rgLevel = childLevel + 2;
        if (rg.getReserved0_1() != null) {
          writeBinaryElement(baseXsw, rgLevel, "reserved0_1", rg.getReserved0_1());
        }
        writeElement(baseXsw, rgLevel, "lowercaseHeight", (int) rg.getLowercaseHeight());
        writeElement(baseXsw, rgLevel, "capMHeight", (int) rg.getCapMHeight());
        writeElement(baseXsw, rgLevel, "maxAscenderHeight", (int) rg.getMaxAscenderHeight());
        writeElement(baseXsw, rgLevel, "maxDescenderDepth", (int) rg.getMaxDescenderDepth());
        if (rg.getReserved10_14() != null) {
          writeBinaryElement(baseXsw, rgLevel, "reserved10_14", rg.getReserved10_14());
        }
        writeElement(baseXsw, rgLevel, "retired15", (int) rg.getRetired15());
        writeElement(baseXsw, rgLevel, "reserved16", (int) rg.getReserved16());
        writeElement(baseXsw, rgLevel, "underscoreWidth", (int) rg.getUnderscoreWidth());
        writeElement(baseXsw, rgLevel, "underscoreWidthFraction", (int) rg.getUnderscoreWidthFraction());
        writeElement(baseXsw, rgLevel, "underscorePosition", (int) rg.getUnderscorePosition());
        writeIndent(baseXsw, childLevel + 1);
        baseXsw.writeEndElement();
      }
      writeIndent(baseXsw, childLevel);
      baseXsw.writeEndElement();
    }
    writeIndent(baseXsw, level);
    baseXsw.writeEndElement();
    MnemonicPerformanceMonitor.endWrite();
  }

  private void writeFnnDirectly(FNN_FontNameMap fnn, int level) throws Exception {
    MnemonicPerformanceMonitor.startWriteWithMnemonic("FNN");
    baseXsw.writeStartElement("FNN_FontNameMap");
    int childLevel = level + 1;
    writeElement(baseXsw, childLevel, "ibmFormat", (int) fnn.getIbmFormat());
    writeElement(baseXsw, childLevel, "technologyFormat", (int) fnn.getTechnologyFormat());

    if (fnn.getRepeatingGroups() != null) {
      writeIndent(baseXsw, childLevel);
      baseXsw.writeStartElement("repeatingGroupsXml");
      for (IRepeatingGroup rg : fnn.getRepeatingGroups()) {
        if (rg instanceof FNN_RepeatingGroup fnnrg) {
          writeIndent(baseXsw, childLevel + 1);
          baseXsw.writeStartElement("repeatingGroupsXml");
          int rgLevel = childLevel + 2;
          writeElement(baseXsw, rgLevel, "gcgid", fnnrg.getGcgid());
          writeElement(baseXsw, rgLevel, "tsOffset", fnnrg.getTsOffset());
          writeIndent(baseXsw, childLevel + 1);
          baseXsw.writeEndElement();
        }
      }
      writeIndent(baseXsw, childLevel);
      baseXsw.writeEndElement();
    }

    if (fnn.getTsIdentifiers() != null) {
      writeIndent(baseXsw, childLevel);
      baseXsw.writeStartElement("tsIdentifier");
      for (FNN_TSIdentifier tsid : fnn.getTsIdentifiers()) {
        writeIndent(baseXsw, childLevel + 1);
        baseXsw.writeStartElement("tsIdentifier");
        int tsLevel = childLevel + 2;
        writeElement(baseXsw, tsLevel, "tsidLen", tsid.getTsidLen());
        writeElement(baseXsw, tsLevel, "tsid", tsid.getTsid());
        writeIndent(baseXsw, childLevel + 1);
        baseXsw.writeEndElement();
      }
      writeIndent(baseXsw, childLevel);
      baseXsw.writeEndElement();
    }

    writeIndent(baseXsw, level);
    baseXsw.writeEndElement();
    MnemonicPerformanceMonitor.endWrite();
  }

  private void writeFngDirectly(FNG_FontPatterns fng, int level) throws Exception {
    MnemonicPerformanceMonitor.startWriteWithMnemonic("FNG");
    baseXsw.writeStartElement("FNG_FontPatterns");
    int childLevel = level + 1;
    if (fng.getData() != null) {
      writeBinaryElement(baseXsw, childLevel, "data", fng.getData());
    }
    if (fng.getText() != null) {
      writeElement(baseXsw, childLevel, "text", fng.getText());
    }
    writeIndent(baseXsw, level);
    baseXsw.writeEndElement();
    MnemonicPerformanceMonitor.endWrite();
  }

  private void writeCpdDirectly(CPD_CodePageDescriptor cpd, int level) throws Exception {
    MnemonicPerformanceMonitor.startWriteWithMnemonic("CPD");
    baseXsw.writeStartElement("CPD_CodePageDescriptor");
    int childLevel = level + 1;
    writeElement(baseXsw, childLevel, "graphicCharacterGIDLength", (int) cpd.getGraphicCharacterGIDLength());
    writeElement(baseXsw, childLevel, "codePageDescription", cpd.getCodePageDescription());
    writeElement(baseXsw, childLevel, "numberOfCodedGraphicCharactersAssigned", cpd.getNumberOfCodedGraphicCharactersAssigned());
    writeElement(baseXsw, childLevel, "graphicCharacterSetGID", cpd.getGraphicCharacterSetGID());
    writeElement(baseXsw, childLevel, "codePageGID", cpd.getCodePageGID());
    if (cpd.getEncodingScheme() != null) {
      writeElement(baseXsw, childLevel, "encodingScheme", cpd.getEncodingScheme().name());
    }
    writeElement(baseXsw, childLevel, "text", cpd.getText());
    writeIndent(baseXsw, level);
    baseXsw.writeEndElement();
    MnemonicPerformanceMonitor.endWrite();
  }

  private void writeLncDirectly(LNC_LineDescriptorCount lnc, int level) throws Exception {
    MnemonicPerformanceMonitor.startWriteWithMnemonic("LNC");
    baseXsw.writeStartElement("LNC_LineDescriptorCount");
    writeElement(baseXsw, level + 1, "numberOfSFInBDX", lnc.getNumberOfSFInBDX());
    writeIndent(baseXsw, level);
    baseXsw.writeEndElement();
    MnemonicPerformanceMonitor.endWrite();
  }

  private void writeCcpDirectly(CCP_ConditionalProcessingControl ccp, int level) throws Exception {
    MnemonicPerformanceMonitor.startWriteWithMnemonic("CCP");
    baseXsw.writeStartElement("CCP_ConditionalProcessingControl");
    int childLevel = level + 1;
    writeElement(baseXsw, childLevel, "ccpIdentifier", ccp.getCcpIdentifier());
    writeElement(baseXsw, childLevel, "nextCcpIdentifier", ccp.getNextCcpIdentifier());
    if (ccp.getFlag() != null) {
      writeElement(baseXsw, childLevel, "flag", ccp.getFlag().name());
    }
    writeElement(baseXsw, childLevel, "reserved5", (int) ccp.getReserved5());
    writeElement(baseXsw, childLevel, "numberOfRepeatingGroups",
        ccp.getNumberOfRepeatingGroups());
    writeElement(baseXsw, childLevel, "lengthOfRepeatingGroup",
        ccp.getLengthOfRepeatingGroup());
    writeElement(baseXsw, childLevel, "lengthOfComparisonString",
        ccp.getLengthOfComparisonString());

    if (ccp.getRepeatingGroups() != null) {
      writeIndent(baseXsw, childLevel);
      baseXsw.writeStartElement("repeatingGroups");
      for (CCP_ConditionalProcessingControl.CCP_RepeatingGroup rg : ccp.getRepeatingGroups()) {
        writeIndent(baseXsw, childLevel + 1);
        baseXsw.writeStartElement("repeatingGroups");
        int rgLevel = childLevel + 2;
        if (rg.getTimingOfAction() != null) {
          writeElement(baseXsw, rgLevel, "timingOfAction", rg.getTimingOfAction().name());
        }
        if (rg.getMediumMapAction() != null) {
          writeElement(baseXsw, rgLevel, "mediumMapAction", rg.getMediumMapAction().name());
        }
        writeElement(baseXsw, rgLevel, "mediumMapName", rg.getMediumMapName());
        if (rg.getDataMapAction() != null) {
          writeElement(baseXsw, rgLevel, "dataMapAction", rg.getDataMapAction().name());
        }
        writeElement(baseXsw, rgLevel, "dataMapName", rg.getDataMapName());
        if (rg.getComparison() != null) {
          writeElement(baseXsw, rgLevel, "comparison", rg.getComparison().name());
        }
        writeElement(baseXsw, rgLevel, "comparisonString", rg.getComparisonString());
        writeElement(baseXsw, rgLevel, "text", rg.getText());
        writeIndent(baseXsw, childLevel + 1);
        baseXsw.writeEndElement();
      }
      writeIndent(baseXsw, childLevel);
      baseXsw.writeEndElement();
    }

    writeIndent(baseXsw, level);
    baseXsw.writeEndElement();
    MnemonicPerformanceMonitor.endWrite();
  }
}
