/*
Copyright 2015 Rudolf Fiala

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

package com.mgz.afp.parser;

import com.mgz.afp.base.StructuredField;
import com.mgz.afp.base.StructuredFieldBaseData;
import com.mgz.afp.base.StructuredFieldErrornouslyBuilt;
import com.mgz.afp.base.StructuredFieldIntroducer;
import com.mgz.afp.bcoca.BDD_BarCodeDataDescriptor;
import com.mgz.afp.enums.SFFlag;
import com.mgz.afp.exceptions.AFPParserException;
import com.mgz.afp.base.IRepeatingGroup;
import com.mgz.afp.base.IHasTriplets;
import com.mgz.afp.foca.CPC_CodePageControl;
import com.mgz.afp.foca.CPD_CodePageDescriptor;
import com.mgz.afp.foca.FNC_FontControl;
import com.mgz.afp.modca.MCF_MapCodedFont_Format1;
import com.mgz.afp.modca.MCF_MapCodedFont_Format2;
import com.mgz.afp.modca.MDR_MapDataResource;
import com.mgz.afp.enums.SFTypeID;
import com.mgz.afp.bcoca.BBC_BeginBarCodeObject;
import com.mgz.afp.bcoca.BDA_BarCodeData;
import com.mgz.afp.bcoca.BDD_BarCodeDataDescriptor;
import com.mgz.afp.bcoca.EBC_EndBarCodeObject;
import com.mgz.afp.cmoca.CMR_ColorManagementResource;
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
import com.mgz.afp.foca.FNO_FontOrientation;
import com.mgz.afp.foca.FNP_FontPosition;
import com.mgz.afp.goca.BGR_BeginGraphicsObject;
import com.mgz.afp.goca.EGR_EndGraphicsObject;
import com.mgz.afp.goca.GAD_GraphicsData;
import com.mgz.afp.goca.GDD_GraphicsDataDescriptor;
import com.mgz.afp.ioca.IDD_ImageDataDescriptor;
import com.mgz.afp.ioca.IPD_ImagePictureData;
import com.mgz.afp.lineData.BDM_BeginDataMap;
import com.mgz.afp.lineData.BDX_BeginDataMapTransmitionSubcase;
import com.mgz.afp.lineData.BPM_BeginPageMap;
import com.mgz.afp.lineData.CCP_ConditionalProcessingControl;
import com.mgz.afp.lineData.DXD_DataMapTransmitionSubcaseDescriptor;
import com.mgz.afp.lineData.EDM_EndDataMap;
import com.mgz.afp.lineData.EDX_EndDataMapTransmitionSubcase;
import com.mgz.afp.lineData.EPM_EndPageMap;
import com.mgz.afp.lineData.FDS_FixedDataSize;
import com.mgz.afp.lineData.FDX_FixedDataText;
import com.mgz.afp.lineData.IDM_InvokeDataMap;
import com.mgz.afp.lineData.LNC_LineDescriptorCount;
import com.mgz.afp.lineData.LND_LineDescriptor;
import com.mgz.afp.lineData.RCD_RecordDescriptor;
import com.mgz.afp.lineData.XMD_XMLDescriptor;
import com.mgz.afp.modca.BAG_BeginActiveEnvironmentGroup;
import com.mgz.afp.modca.BDG_BeginDocumentEnvironmentGroup;
import com.mgz.afp.modca.BDI_BeginDocumentIndex;
import com.mgz.afp.modca.BDT_BeginDocument;
import com.mgz.afp.modca.BFG_BeginFormEnvironmentGroup;
import com.mgz.afp.modca.BFM_BeginFormMap;
import com.mgz.afp.modca.BII_BeginIMImageObject;
import com.mgz.afp.modca.BIM_BeginImageObject;
import com.mgz.afp.modca.BMM_BeginMediumMap;
import com.mgz.afp.modca.BMO_BeginOverlay;
import com.mgz.afp.modca.BNG_BeginNamedPageGroup;
import com.mgz.afp.modca.BOC_BeginObjectContainer;
import com.mgz.afp.modca.BOG_BeginObjectEnvironmentGroup;
import com.mgz.afp.modca.BPF_BeginPrintFile;
import com.mgz.afp.modca.BPG_BeginPage;
import com.mgz.afp.modca.BPS_BeginPageSegment;
import com.mgz.afp.modca.BRG_BeginResourceGroup;
import com.mgz.afp.modca.BRS_BeginResource;
import com.mgz.afp.modca.BSG_BeginResourceEnvironmentGroup;
import com.mgz.afp.modca.CDD_ContainerDataDescriptor;
import com.mgz.afp.modca.CTC_ComposedTextControl;
import com.mgz.afp.modca.EAG_EndActiveEnvironmentGroup;
import com.mgz.afp.modca.EDG_EndDocumentEnvironmentGroup;
import com.mgz.afp.modca.EDI_EndDocumentIndex;
import com.mgz.afp.modca.EDT_EndDocument;
import com.mgz.afp.modca.EFG_EndFormEnvironmentGroup;
import com.mgz.afp.modca.EFM_EndFormMap;
import com.mgz.afp.modca.EII_EndIMImageObject;
import com.mgz.afp.modca.EIM_EndImageObject;
import com.mgz.afp.modca.EMM_EndMediumMap;
import com.mgz.afp.modca.EMO_EndOverlay;
import com.mgz.afp.modca.ENG_EndNamedPageGroup;
import com.mgz.afp.modca.EOC_EndObjectContainer;
import com.mgz.afp.modca.EOG_EndObjectEnvironmentGroup;
import com.mgz.afp.modca.EPF_EndPrintFile;
import com.mgz.afp.modca.EPG_EndPage;
import com.mgz.afp.modca.EPS_EndPageSegment;
import com.mgz.afp.modca.ERG_EndResourceGroup;
import com.mgz.afp.modca.ERS_EndResource;
import com.mgz.afp.modca.ESG_EndResourceEnvironmentGroup;
import com.mgz.afp.modca.FGD_FormEnvironmentGroupDescriptor;
import com.mgz.afp.modca.ICP_IMImageCellPosition;
import com.mgz.afp.modca.IEL_IndexElement;
import com.mgz.afp.modca.IID_IMImageInputDescriptor;
import com.mgz.afp.modca.IMM_InvokeMediumMap;
import com.mgz.afp.modca.IOB_IncludeObject;
import com.mgz.afp.modca.IOC_IMImageOutputControl;
import com.mgz.afp.modca.IPG_IncludePage;
import com.mgz.afp.modca.IPO_IncludePageOverlay;
import com.mgz.afp.modca.IPS_IncludePageSegment;
import com.mgz.afp.modca.IRD_IMImageRasterData;
import com.mgz.afp.modca.LLE_LinkLogicalElement;
import com.mgz.afp.modca.MBC_MapBarCodeObject;
import com.mgz.afp.modca.MCC_MediumCopyCount;
import com.mgz.afp.modca.MCD_MapContainerData;
import com.mgz.afp.modca.MCF_MapCodedFont_Format1;
import com.mgz.afp.modca.MCF_MapCodedFont_Format2;
import com.mgz.afp.modca.MDD_MediumDescriptor;
import com.mgz.afp.modca.MDR_MapDataResource;
import com.mgz.afp.modca.MFC_MediumFinishingControl;
import com.mgz.afp.modca.MGO_MapGraphicsObject;
import com.mgz.afp.modca.MIO_MapImageObject;
import com.mgz.afp.modca.MMC_MediumModificationControl;
import com.mgz.afp.modca.MMD_MapMediaDestination;
import com.mgz.afp.modca.MMO_MapMediumOverlay;
import com.mgz.afp.modca.MMT_MapMediaType;
import com.mgz.afp.modca.MPG_MapPage;
import com.mgz.afp.modca.MPO_MapPageOverlay;
import com.mgz.afp.modca.MPS_MapPageSegment;
import com.mgz.afp.modca.MPT_MapPresentationText;
import com.mgz.afp.modca.MSU_MapSuppression;
import com.mgz.afp.modca.NOP_NoOperation;
import com.mgz.afp.modca.OBD_ObjectAreaDescriptor;
import com.mgz.afp.modca.OBP_ObjectAreaPosition;
import com.mgz.afp.modca.OCD_ObjectContainerData;
import com.mgz.afp.modca.PEC_PresentationEnvironmentControl;
import com.mgz.afp.modca.PFC_PresentationFidelityControl;
import com.mgz.afp.modca.PGD_PageDescriptor;
import com.mgz.afp.modca.PGP_PagePosition_Format1;
import com.mgz.afp.modca.PGP_PagePosition_Format2;
import com.mgz.afp.modca.PMC_PageModificationControl;
import com.mgz.afp.modca.PPO_PreprocessPresentationObject;
import com.mgz.afp.modca.TLE_TagLogicalElement;
import com.mgz.afp.modca_L.BCA_BeginColorAttributeTable;
import com.mgz.afp.modca_L.CAT_ColorAttributeTable;
import com.mgz.afp.modca_L.ECA_EndColorAttributeTable;
import com.mgz.afp.modca_L.MCA_MapColorAttributeTable;
import com.mgz.afp.ptoca.BPT_BeginPresentationTextObject;
import com.mgz.afp.ptoca.EPT_EndPresentationTextObject;
import com.mgz.afp.ptoca.PTD_PresentationTextDataDescriptor_Format1;
import com.mgz.afp.ptoca.PTD_PresentationTextDataDescriptor_Format2;
import com.mgz.afp.ptoca.PTX_PresentationTextData;
import java.util.EnumMap;
import java.util.Map;
import java.util.function.Supplier;
import com.mgz.afp.triplets.Triplet;
import com.mgz.util.Constants;
import com.mgz.util.UtilCharacterEncoding;
import com.mgz.util.UtilBinaryDecoding;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * The AFPParser is the main class for parsing AFP data streams.
 * It reads structured fields from an input stream and builds corresponding
 * Java objects.
 */
public class AFPParser {

  private static final Map<SFTypeID, Supplier<StructuredField>> SF_SUPPLIERS = new EnumMap<>(SFTypeID.class);

  static {
    SF_SUPPLIERS.put(SFTypeID.BAG_BeginActiveEnvironmentGroup, BAG_BeginActiveEnvironmentGroup::new);
    SF_SUPPLIERS.put(SFTypeID.BBC_BeginBarCodeObject, BBC_BeginBarCodeObject::new);
    SF_SUPPLIERS.put(SFTypeID.BCA_BeginColorAttributeTable, BCA_BeginColorAttributeTable::new);
    SF_SUPPLIERS.put(SFTypeID.BCF_BeginCodedFont, BCF_BeginCodedFont::new);
    SF_SUPPLIERS.put(SFTypeID.BCP_BeginCodePage, BCP_BeginCodePage::new);
    SF_SUPPLIERS.put(SFTypeID.BDA_BarCodeData, BDA_BarCodeData::new);
    SF_SUPPLIERS.put(SFTypeID.BDD_BarCodeDataDescriptor, BDD_BarCodeDataDescriptor::new);
    SF_SUPPLIERS.put(SFTypeID.BDG_BeginDocumentEnvironmentGroup, BDG_BeginDocumentEnvironmentGroup::new);
    SF_SUPPLIERS.put(SFTypeID.BDI_BeginDocumentIndex, BDI_BeginDocumentIndex::new);
    SF_SUPPLIERS.put(SFTypeID.BDM_BeginDataMap, BDM_BeginDataMap::new);
    SF_SUPPLIERS.put(SFTypeID.BDT_BeginDocument, BDT_BeginDocument::new);
    SF_SUPPLIERS.put(SFTypeID.BDX_BeginDataMapTransmitionSubcase, BDX_BeginDataMapTransmitionSubcase::new);
    SF_SUPPLIERS.put(SFTypeID.BFG_BeginFormEnvironmentGroup, BFG_BeginFormEnvironmentGroup::new);
    SF_SUPPLIERS.put(SFTypeID.BFM_BeginFormMap, BFM_BeginFormMap::new);
    SF_SUPPLIERS.put(SFTypeID.BFN_BeginFont, BFN_BeginFont::new);
    SF_SUPPLIERS.put(SFTypeID.BGR_BeginGraphicsObject, BGR_BeginGraphicsObject::new);
    SF_SUPPLIERS.put(SFTypeID.BII_BeginIMImageObject, BII_BeginIMImageObject::new);
    SF_SUPPLIERS.put(SFTypeID.BIM_BeginImageObject, BIM_BeginImageObject::new);
    SF_SUPPLIERS.put(SFTypeID.BMM_BeginMediumMap, BMM_BeginMediumMap::new);
    SF_SUPPLIERS.put(SFTypeID.BMO_BeginOverlay, BMO_BeginOverlay::new);
    SF_SUPPLIERS.put(SFTypeID.BNG_BeginNamedPageGroup, BNG_BeginNamedPageGroup::new);
    SF_SUPPLIERS.put(SFTypeID.BOC_BeginObjectContainer, BOC_BeginObjectContainer::new);
    SF_SUPPLIERS.put(SFTypeID.BOG_BeginObjectEnvironmentGroup, BOG_BeginObjectEnvironmentGroup::new);
    SF_SUPPLIERS.put(SFTypeID.BPF_BeginPrintFile, BPF_BeginPrintFile::new);
    SF_SUPPLIERS.put(SFTypeID.BPG_BeginPage, BPG_BeginPage::new);
    SF_SUPPLIERS.put(SFTypeID.BPM_BeginPageMap, BPM_BeginPageMap::new);
    SF_SUPPLIERS.put(SFTypeID.BPS_BeginPageSegment, BPS_BeginPageSegment::new);
    SF_SUPPLIERS.put(SFTypeID.BPT_BeginPresentationTextObject, BPT_BeginPresentationTextObject::new);
    SF_SUPPLIERS.put(SFTypeID.BRG_BeginResourceGroup, BRG_BeginResourceGroup::new);
    SF_SUPPLIERS.put(SFTypeID.BRS_BeginResource, BRS_BeginResource::new);
    SF_SUPPLIERS.put(SFTypeID.BSG_BeginResourceEnvironmentGroup, BSG_BeginResourceEnvironmentGroup::new);
    SF_SUPPLIERS.put(SFTypeID.CAT_ColorAttributeTable, CAT_ColorAttributeTable::new);
    SF_SUPPLIERS.put(SFTypeID.CCP_ConditionalProcessingControl, CCP_ConditionalProcessingControl::new);
    SF_SUPPLIERS.put(SFTypeID.CDD_ContainerDataDescriptor, CDD_ContainerDataDescriptor::new);
    SF_SUPPLIERS.put(SFTypeID.CMR_ColorManagementResource, CMR_ColorManagementResource::new);
    SF_SUPPLIERS.put(SFTypeID.CFC_CodedFontControl, CFC_CodedFontControl::new);
    SF_SUPPLIERS.put(SFTypeID.CFI_CodedFontIndex, CFI_CodedFontIndex::new);
    SF_SUPPLIERS.put(SFTypeID.CPC_CodePageControl, CPC_CodePageControl::new);
    SF_SUPPLIERS.put(SFTypeID.CPD_CodePageDescriptor, CPD_CodePageDescriptor::new);
    SF_SUPPLIERS.put(SFTypeID.CPI_CodePageIndex, CPI_CodePageIndex::new);
    SF_SUPPLIERS.put(SFTypeID.CTC_ComposedTextControl, CTC_ComposedTextControl::new);
    SF_SUPPLIERS.put(SFTypeID.DXD_DataMapTransmitionSubcaseDescriptor, DXD_DataMapTransmitionSubcaseDescriptor::new);
    SF_SUPPLIERS.put(SFTypeID.EAG_EndActiveEnvironmentGroup, EAG_EndActiveEnvironmentGroup::new);
    SF_SUPPLIERS.put(SFTypeID.EBC_EndBarCodeObject, EBC_EndBarCodeObject::new);
    SF_SUPPLIERS.put(SFTypeID.ECA_EndColorAttributeTable, ECA_EndColorAttributeTable::new);
    SF_SUPPLIERS.put(SFTypeID.ECF_EndCodedFont, ECF_EndCodedFont::new);
    SF_SUPPLIERS.put(SFTypeID.ECP_EndCodePage, ECP_EndCodePage::new);
    SF_SUPPLIERS.put(SFTypeID.EDG_EndDocumentEnvironmentGroup, EDG_EndDocumentEnvironmentGroup::new);
    SF_SUPPLIERS.put(SFTypeID.EDI_EndDocumentIndex, EDI_EndDocumentIndex::new);
    SF_SUPPLIERS.put(SFTypeID.EDM_EndDataMap, EDM_EndDataMap::new);
    SF_SUPPLIERS.put(SFTypeID.EDT_EndDocument, EDT_EndDocument::new);
    SF_SUPPLIERS.put(SFTypeID.EFG_EndFormEnvironmentGroup, EFG_EndFormEnvironmentGroup::new);
    SF_SUPPLIERS.put(SFTypeID.EFM_EndFormMap, EFM_EndFormMap::new);
    SF_SUPPLIERS.put(SFTypeID.EFN_EndFont, EFN_EndFont::new);
    SF_SUPPLIERS.put(SFTypeID.EGR_EndGraphicsObject, EGR_EndGraphicsObject::new);
    SF_SUPPLIERS.put(SFTypeID.EDX_EndDataMapTransmitionSubcase, EDX_EndDataMapTransmitionSubcase::new);
    SF_SUPPLIERS.put(SFTypeID.EII_EndIMImageObject, EII_EndIMImageObject::new);
    SF_SUPPLIERS.put(SFTypeID.EIM_EndImageObject, EIM_EndImageObject::new);
    SF_SUPPLIERS.put(SFTypeID.EMM_EndMediumMap, EMM_EndMediumMap::new);
    SF_SUPPLIERS.put(SFTypeID.EMO_EndOverlay, EMO_EndOverlay::new);
    SF_SUPPLIERS.put(SFTypeID.ENG_EndNamedPageGroup, ENG_EndNamedPageGroup::new);
    SF_SUPPLIERS.put(SFTypeID.EOC_EndObjectContainer, EOC_EndObjectContainer::new);
    SF_SUPPLIERS.put(SFTypeID.EOG_EndObjectEnvironmentGroup, EOG_EndObjectEnvironmentGroup::new);
    SF_SUPPLIERS.put(SFTypeID.EPF_EndPrintFile, EPF_EndPrintFile::new);
    SF_SUPPLIERS.put(SFTypeID.EPG_EndPage, EPG_EndPage::new);
    SF_SUPPLIERS.put(SFTypeID.EPM_EndPageMap, EPM_EndPageMap::new);
    SF_SUPPLIERS.put(SFTypeID.EPS_EndPageSegment, EPS_EndPageSegment::new);
    SF_SUPPLIERS.put(SFTypeID.EPT_EndPresentationTextObject, EPT_EndPresentationTextObject::new);
    SF_SUPPLIERS.put(SFTypeID.ERG_EndResourceGroup, ERG_EndResourceGroup::new);
    SF_SUPPLIERS.put(SFTypeID.ERS_EndResource, ERS_EndResource::new);
    SF_SUPPLIERS.put(SFTypeID.ESG_EndResourceEnvironmentGroup, ESG_EndResourceEnvironmentGroup::new);
    SF_SUPPLIERS.put(SFTypeID.FDS_FixedDataSize, FDS_FixedDataSize::new);
    SF_SUPPLIERS.put(SFTypeID.FDX_FixedDataText, FDX_FixedDataText::new);
    SF_SUPPLIERS.put(SFTypeID.FGD_FormEnvironmentGroupDescriptor, FGD_FormEnvironmentGroupDescriptor::new);
    SF_SUPPLIERS.put(SFTypeID.FNC_FontControl, FNC_FontControl::new);
    SF_SUPPLIERS.put(SFTypeID.FND_FontDescriptor, FND_FontDescriptor::new);
    SF_SUPPLIERS.put(SFTypeID.FNG_FontPatterns, FNG_FontPatterns::new);
    SF_SUPPLIERS.put(SFTypeID.FNI_FontIndex, FNI_FontIndex::new);
    SF_SUPPLIERS.put(SFTypeID.FNM_FontPatternsMap, FNM_FontPatternsMap::new);
    SF_SUPPLIERS.put(SFTypeID.FNN_FontNameMap, FNN_FontNameMap::new);
    SF_SUPPLIERS.put(SFTypeID.FNO_FontOrientation, FNO_FontOrientation::new);
    SF_SUPPLIERS.put(SFTypeID.FNP_FontPosition, FNP_FontPosition::new);
    SF_SUPPLIERS.put(SFTypeID.GAD_GraphicsData, GAD_GraphicsData::new);
    SF_SUPPLIERS.put(SFTypeID.GDD_GraphicsDataDescriptor, GDD_GraphicsDataDescriptor::new);
    SF_SUPPLIERS.put(SFTypeID.ICP_IMImageCellPosition, ICP_IMImageCellPosition::new);
    SF_SUPPLIERS.put(SFTypeID.IDD_ImageDataDescriptor, IDD_ImageDataDescriptor::new);
    SF_SUPPLIERS.put(SFTypeID.IDM_InvokeDataMap, IDM_InvokeDataMap::new);
    SF_SUPPLIERS.put(SFTypeID.IEL_IndexElement, IEL_IndexElement::new);
    SF_SUPPLIERS.put(SFTypeID.IID_IMImageInputDescriptor, IID_IMImageInputDescriptor::new);
    SF_SUPPLIERS.put(SFTypeID.IMM_InvokeMediumMap, IMM_InvokeMediumMap::new);
    SF_SUPPLIERS.put(SFTypeID.IOB_IncludeObject, IOB_IncludeObject::new);
    SF_SUPPLIERS.put(SFTypeID.IOC_IMImageOutputControl, IOC_IMImageOutputControl::new);
    SF_SUPPLIERS.put(SFTypeID.IPD_ImagePictureData, IPD_ImagePictureData::new);
    SF_SUPPLIERS.put(SFTypeID.IPG_IncludePage, IPG_IncludePage::new);
    SF_SUPPLIERS.put(SFTypeID.IPO_IncludePageOverlay, IPO_IncludePageOverlay::new);
    SF_SUPPLIERS.put(SFTypeID.IPS_IncludePageSegment, IPS_IncludePageSegment::new);
    SF_SUPPLIERS.put(SFTypeID.IRD_IMImageRasterData, IRD_IMImageRasterData::new);
    SF_SUPPLIERS.put(SFTypeID.LLE_LinkLogicalElement, LLE_LinkLogicalElement::new);
    SF_SUPPLIERS.put(SFTypeID.LNC_LineDescriptorCount, LNC_LineDescriptorCount::new);
    SF_SUPPLIERS.put(SFTypeID.LND_LineDescriptor, LND_LineDescriptor::new);
    SF_SUPPLIERS.put(SFTypeID.MPT_MapPresentationText, MPT_MapPresentationText::new);
    SF_SUPPLIERS.put(SFTypeID.MBC_MapBarCodeObject, MBC_MapBarCodeObject::new);
    SF_SUPPLIERS.put(SFTypeID.MCA_MapColorAttributeTable, MCA_MapColorAttributeTable::new);
    SF_SUPPLIERS.put(SFTypeID.MCC_MediumCopyCount, MCC_MediumCopyCount::new);
    SF_SUPPLIERS.put(SFTypeID.MCD_MapContainerData, MCD_MapContainerData::new);
    SF_SUPPLIERS.put(SFTypeID.MCF_MapCodedFont_Format1, MCF_MapCodedFont_Format1::new);
    SF_SUPPLIERS.put(SFTypeID.MCF_MapCodedFont_Format2, MCF_MapCodedFont_Format2::new);
    SF_SUPPLIERS.put(SFTypeID.MDD_MediumDescriptor, MDD_MediumDescriptor::new);
    SF_SUPPLIERS.put(SFTypeID.MDR_MapDataResource, MDR_MapDataResource::new);
    SF_SUPPLIERS.put(SFTypeID.MFC_MediumFinishingControl, MFC_MediumFinishingControl::new);
    SF_SUPPLIERS.put(SFTypeID.MGO_MapGraphicsObject, MGO_MapGraphicsObject::new);
    SF_SUPPLIERS.put(SFTypeID.MIO_MapImageObject, MIO_MapImageObject::new);
    SF_SUPPLIERS.put(SFTypeID.MMC_MediumModificationControl, MMC_MediumModificationControl::new);
    SF_SUPPLIERS.put(SFTypeID.MMD_MapMediaDestination, MMD_MapMediaDestination::new);
    SF_SUPPLIERS.put(SFTypeID.MMO_MapMediumOverlay, MMO_MapMediumOverlay::new);
    SF_SUPPLIERS.put(SFTypeID.MMT_MapMediaType, MMT_MapMediaType::new);
    SF_SUPPLIERS.put(SFTypeID.MPG_MapPage, MPG_MapPage::new);
    SF_SUPPLIERS.put(SFTypeID.MPO_MapPageOverlay, MPO_MapPageOverlay::new);
    SF_SUPPLIERS.put(SFTypeID.MPS_MapPageSegment, MPS_MapPageSegment::new);
    SF_SUPPLIERS.put(SFTypeID.MSU_MapSuppression, MSU_MapSuppression::new);
    SF_SUPPLIERS.put(SFTypeID.NOP_NoOperation, NOP_NoOperation::new);
    SF_SUPPLIERS.put(SFTypeID.OBD_ObjectAreaDescriptor, OBD_ObjectAreaDescriptor::new);
    SF_SUPPLIERS.put(SFTypeID.OBP_ObjectAreaPosition, OBP_ObjectAreaPosition::new);
    SF_SUPPLIERS.put(SFTypeID.OCD_ObjectContainerData, OCD_ObjectContainerData::new);
    SF_SUPPLIERS.put(SFTypeID.PEC_PresentationEnvironmentControl, PEC_PresentationEnvironmentControl::new);
    SF_SUPPLIERS.put(SFTypeID.PFC_PresentationFidelityControl, PFC_PresentationFidelityControl::new);
    SF_SUPPLIERS.put(SFTypeID.PGD_PageDescriptor, PGD_PageDescriptor::new);
    SF_SUPPLIERS.put(SFTypeID.PGP_PagePosition_Format1, PGP_PagePosition_Format1::new);
    SF_SUPPLIERS.put(SFTypeID.PGP_PagePosition_Format2, PGP_PagePosition_Format2::new);
    SF_SUPPLIERS.put(SFTypeID.PMC_PageModificationControl, PMC_PageModificationControl::new);
    SF_SUPPLIERS.put(SFTypeID.PPO_PreprocessPresentationObject, PPO_PreprocessPresentationObject::new);
    SF_SUPPLIERS.put(SFTypeID.PTD_PresentationTextDataDescriptor_Format1, PTD_PresentationTextDataDescriptor_Format1::new);
    SF_SUPPLIERS.put(SFTypeID.PTD_PresentationTextDataDescriptor_Format2, PTD_PresentationTextDataDescriptor_Format2::new);
    SF_SUPPLIERS.put(SFTypeID.PTX_PresentationTextData, PTX_PresentationTextData::new);
    SF_SUPPLIERS.put(SFTypeID.RCD_RecordDescriptor, RCD_RecordDescriptor::new);
    SF_SUPPLIERS.put(SFTypeID.TLE_TagLogicalElement, TLE_TagLogicalElement::new);
    SF_SUPPLIERS.put(SFTypeID.XMD_XMLDescriptor, XMD_XMLDescriptor::new);
  }
  AFPParserConfiguration parserConf;
  long nrOfBytesRead;
  long nrOfSFBuilt;
  long nrOfErrSFBuilt;


  /**
   * Constructor.
   *
   * @param parserConfiguration see {@link AFPParserConfiguration}
   */
  public AFPParser(AFPParserConfiguration parserConfiguration) {
    nrOfBytesRead = 0;
    nrOfSFBuilt = 0;
    nrOfErrSFBuilt = 0;
    parserConf = parserConfiguration;
  }

  /**
   * Creates a new instance of a {@link StructuredField} based on the given introducer.
   *
   * @param sfi the structured field introducer
   * @return a new instance of the corresponding structured field, or an Undefined SF if not found
   */
  public static StructuredField createSFInstance(StructuredFieldIntroducer sfi) {
    var supplier = SF_SUPPLIERS.get(sfi.getSFTypeID());
    StructuredField sf = (supplier != null) ? supplier.get() : new com.mgz.afp.base.Undefined();
    sf.setStructuredFieldIntroducer(sfi);
    return sf;
  }
  public static void reload(StructuredField sf) throws AFPParserException {
    if (sf == null || sf.getStructuredFieldIntroducer() == null) {
      return;
    }

    StructuredFieldIntroducer sfi = sf.getStructuredFieldIntroducer();
    AFPParserConfiguration conf = sfi.getActualConfig();
    if (conf.getAFPFile() == null) {
      throw new AFPParserException("The file from whitch the structured field has been loaded is unknown.");
    }

    synchronized (conf) {
      InputStream is = null;
      try {
        conf.setInputStream(null);
        is = conf.getInputStream();
        long lenSFI = sfi.getFileOffset() + 1 + sfi.getLengthOfStructuredFieldIntroducerIncludingExtension();
        if (is.skip(lenSFI) < lenSFI) {
          throw new AFPParserException("Failed to skip over SF Introducer.");
        }

        int lenOfGrossPayload = sfi.getSFLength() - sfi.getLengthOfStructuredFieldIntroducerIncludingExtension();
        byte[] grossPayload = new byte[lenOfGrossPayload];
        byte[] sfData, padding;


        // Determine payload.
        if (lenOfGrossPayload > 0) {

          int read = 0;
          while (read < lenOfGrossPayload) {
            int len = is.read(grossPayload, read, lenOfGrossPayload - read);
            if (len == -1) {
              throw new AFPParserException("Reached end of file before end of structured field.");
            } else {
              read += len;
            }
          }

          // Determine net payload.
          if (sfi.isFlagSet(SFFlag.isPadded)) {
            int lenOfPadding = grossPayload[grossPayload.length - 1];
            if (lenOfPadding == 0) {
              lenOfPadding = UtilBinaryDecoding.parseInt(grossPayload, grossPayload.length - 3, 2);
            }

            int lenOfSFData = lenOfGrossPayload - lenOfPadding;

            sfData = new byte[lenOfSFData];
            padding = new byte[lenOfPadding];

            System.arraycopy(grossPayload, 0, sfData, 0, lenOfSFData);
            System.arraycopy(grossPayload, lenOfSFData, padding, 0, lenOfPadding);

          } else {
            sfData = grossPayload;
            padding = null;
          }

          sf.setPadding(padding);
          sf.decodeAFP(sfData, 0, -1, conf);
        }

      } catch (Throwable th) {
        if (th instanceof AFPParserException) {
          throw (AFPParserException) th;
        } else {
          throw new AFPParserException("Reload failed.", th);
        }
      } finally {
        if (is != null) {
          try {
            is.close();
            conf.setInputStream(null);
          } catch (IOException e) {
            throw new AFPParserException("Failed to close input stream.", e);
          }
        }
      }
    }
  }

  /**
   * Call this method to perform the parsing. Blocks until the parsing finished, either by end of
   * input stream or the occurrence of an {@link AFPParserException}.
   */
  public final StructuredField parseNextSF() throws AFPParserException {
    StructuredFieldIntroducer sfi = null;
    StructuredFieldErrornouslyBuilt errSf = null;
    try {
      var is = parserConf.getInputStream();

      int tmp;
      byte[] sfData;
      byte[] padding;
      do {
        tmp = is.read();
        if (tmp != -1) {
          nrOfBytesRead++;
        }
      }
      while (tmp != 0x5A && tmp != -1); // Move to the begin of next SF, or EOF.

      if (tmp != -1) {
        try {
          sfi = StructuredFieldIntroducer.parse(is);
        } catch (Throwable e) {
          if (errSf == null) {
            errSf = new StructuredFieldErrornouslyBuilt();
            errSf.setStructuredFieldIntroducer(new StructuredFieldIntroducer());
          }
          if (e instanceof AFPParserException) {
            ((AFPParserException) e).setErrornouslyBuiltStructuredField(errSf);
            try {
              error((AFPParserException) e);
            } catch (AFPParserException ex) {
              if (parserConf.isEscalateParsingErrors()) {
                throw ex;
              }
            }
          } else {
            AFPParserException afpex = new AFPParserException("An exception occured when parsing structured field introducer at file index position 0x" + Long.toHexString(nrOfBytesRead) + ".", e);
            afpex.setErrornouslyBuiltStructuredField(errSf);
            try {
              error(afpex);
            } catch (AFPParserException ex) {
              if (parserConf.isEscalateParsingErrors()) {
                throw ex;
              }
            }
          }
          return errSf;
        }
        sfi.setFileOffset(nrOfBytesRead - 1);

        StructuredField sf;
        if (parserConf.isParseToStructuredFieldsBaseData) {
          sf = new StructuredFieldBaseData();
          sf.setStructuredFieldIntroducer(sfi);
        } else {
          sf = createSFInstance(sfi);
        }

        var lenOfGrossPayload = sfi.getSFLength() - sfi.getLengthOfStructuredFieldIntroducerIncludingExtension();

        if (parserConf.isBuildShallow()) {
          var actualConf = parserConf.clone();
          actualConf.setInputStream(null);
          sfi.setActualConfig(actualConf);
          if (is.skip(lenOfGrossPayload) < lenOfGrossPayload) {
            throw new AFPParserException("Failed to skip payload while building shallow objects.");
          }

        } else {

          var grossPayload = new byte[lenOfGrossPayload];

          try {
            // Determine payload.
            if (lenOfGrossPayload > 0) {

              var read = 0;
              while (read < lenOfGrossPayload) {
                var len = is.read(grossPayload, read, lenOfGrossPayload - read);
                if (len == -1) {
                  throw new AFPParserException("Reached end of file before end of structured field.");
                } else {
                  read += len;
                }
              }

              // Determine net payload.
              if (sfi.isFlagSet(SFFlag.isPadded)) {
                int lenOfPadding = grossPayload[grossPayload.length - 1] & 0xFF;
                if (lenOfPadding == 0) {
                  lenOfPadding = UtilBinaryDecoding.parseInt(grossPayload, grossPayload.length - 3, 2);
                }

                var lenOfSFData = lenOfGrossPayload - lenOfPadding;

                sfData = new byte[lenOfSFData];
                padding = new byte[lenOfPadding];

                System.arraycopy(grossPayload, 0, sfData, 0, lenOfSFData);
                System.arraycopy(grossPayload, lenOfSFData, padding, 0, lenOfPadding);

              } else {
                sfData = grossPayload;
                padding = null;
              }

              sf.setPadding(padding);
              sf.decodeAFP(sfData, 0, -1, parserConf);
            }
          } catch (Throwable th) {
            sf = errSf = new StructuredFieldErrornouslyBuilt();
            errSf.setCausingException(th);
            errSf.setStructuredFieldIntroducer(sfi);
            errSf.setData(grossPayload);
            if (parserConf.isEscalateParsingErrors()) {
              throw th;
            }
          }

        }
        if (sf != null) {

          // Preserve certain SFs which maybe referenced by later SFs.
          switch (sf) {
            case FNC_FontControl fnc -> parserConf.setCurrentFontControl(fnc);
            case CPD_CodePageDescriptor cpd -> parserConf.setCurrentCodePageDescriptor(cpd);
            case CPC_CodePageControl cpc -> parserConf.setCurrentPageControl(cpc);
            case BDD_BarCodeDataDescriptor bdd -> parserConf.setCurrentBarCodeDataDescriptor(bdd);
            case MCF_MapCodedFont_Format1 mcf1 -> handleMCF1(mcf1);
            case MCF_MapCodedFont_Format2 mcf2 -> handleMCF2(mcf2);
            case MDR_MapDataResource mdr -> handleMDR(mdr);
            default -> { }
          }

          nrOfBytesRead += sf.getStructuredFieldIntroducer().getSFLength();
          nrOfSFBuilt++;
        }

        return sf;

      } else {
        return null;
      }

    } catch (Throwable e) {

      if (errSf == null) {
        errSf = new StructuredFieldErrornouslyBuilt();
        errSf.setStructuredFieldIntroducer(sfi);
      }

      nrOfBytesRead += errSf.getStructuredFieldIntroducer().getSFLength();
      nrOfSFBuilt++;
      nrOfErrSFBuilt++;

      // Call error() which may or may not re-throw the given exception
      if (e instanceof AFPParserException) {
        ((AFPParserException) e).setErrornouslyBuiltStructuredField(errSf);
        error((AFPParserException) e);
      } else {
        AFPParserException afpex = new AFPParserException("An exception occured when parsing structured field at file index position 0x" + Long.toHexString(nrOfBytesRead) + ".", e);
        afpex.setErrornouslyBuiltStructuredField(errSf);
        error(afpex);
      }

      return errSf;
    }
  }

  /**
   * This method is called by the parser if an error condition is reached by the parser, e.g. the
   * AFP stream has errors. This method just throws the {@link AFPParserException}. Override this
   * method in order to handle/ignore exception by your application and continue parsing.
   */
  public void error(AFPParserException afpExc) throws AFPParserException {
    throw afpExc;
  }

  private void handleMCF1(MCF_MapCodedFont_Format1 mcf1) {
    if (mcf1.getRepeatingGroups() == null) {
      return;
    }
    mcf1.getRepeatingGroups().stream()
        .filter(rg -> rg.getCodePageName() != null)
        .forEach(rg -> {
          var cs = UtilCharacterEncoding.getCharsetFromCodePageName(rg.getCodePageName());
          if (cs != null) {
            parserConf.addCodedFontCharsetMapping(rg.getCodedFontLocalID(), cs);
          }
        });
  }

  private void handleMCF2(MCF_MapCodedFont_Format2 mcf2) {
    if (mcf2.getRepeatingGroups() == null) {
      return;
    }
    mcf2.getRepeatingGroups().stream()
        .filter(IHasTriplets.class::isInstance)
        .map(IHasTriplets.class::cast)
        .forEach(rg -> {
          List<Triplet> triplets = rg.getTriplets();
          Short lid = null;
          java.nio.charset.Charset cs = null;
          if (triplets != null) {
            for (Triplet t : triplets) {
              if (t instanceof Triplet.ResourceLocalIdentifier rli) {
                if (rli.getResourceType() == Triplet.ResourceLocalIdentifier.RLI_ResourceType.CodedFont) {
                  lid = rli.getResourceLocalID();
                }
              } else if (t instanceof Triplet.FullyQualifiedName fqn) {
                if (fqn.getType() == Triplet.GlobalID_Use.CodePageNameReference) {
                  cs = UtilCharacterEncoding.getCharsetFromCodePageName(fqn.getNameAsString());
                }
              }
            }
          }
          if (lid != null && cs != null) {
            parserConf.addCodedFontCharsetMapping(lid, cs);
          }
        });
  }

  private void handleMDR(MDR_MapDataResource mdr) {
    if (mdr.getRepeatingGroups() == null) {
      return;
    }
    mdr.getRepeatingGroups().stream()
        .filter(IHasTriplets.class::isInstance)
        .map(IHasTriplets.class::cast)
        .forEach(rg -> {
          List<Triplet> triplets = rg.getTriplets();
          Short lid = null;
          java.nio.charset.Charset cs = null;
          if (triplets != null) {
            for (Triplet t : triplets) {
              if (t instanceof Triplet.ResourceLocalIdentifier rli) {
                if (rli.getResourceType() == Triplet.ResourceLocalIdentifier.RLI_ResourceType.CodedFont) {
                  lid = rli.getResourceLocalID();
                }
              } else if (t instanceof Triplet.FullyQualifiedName fqn) {
                if (fqn.getType() == Triplet.GlobalID_Use.CodePageNameReference) {
                  cs = UtilCharacterEncoding.getCharsetFromCodePageName(fqn.getNameAsString());
                  if (cs != null) {
                    parserConf.setAfpCharSet(cs);
                  }
                }
              }
            }
          }
          if (lid != null && cs != null) {
            parserConf.addCodedFontCharsetMapping(lid, cs);
          }
        });
  }

  /**
   * Returns the total number of bytes read so far.
   *
   * @return the number of bytes read
   */
  public long getCountReadByte() {
    return nrOfBytesRead;
  }

  /**
   * Quits the parsing process and cleans up resources.
   *
   * @throws AFPParserException if closing the input stream fails
   */
  public void quitParsing() throws AFPParserException {
    parserConf.resetCurrentAFPObjects();

    if (parserConf.isParserOwnsInputStream && parserConf.inputStream != null) {
      try {
        parserConf.inputStream.close();
      } catch (IOException e) {
        throw new AFPParserException("Failed to close input stream.", e);
      }
    }
  }

  /**
   * Returns the total number of structured fields that has been built so far.
   *
   * @return the number of structured fields built
   */
  public long getNrOfSFBuilt() {
    return nrOfSFBuilt;
  }

  /**
   * Returns the number of structured fields that has been built with errors so far.
   *
   * @return the number of structured fields built with errors
   */
  public long getNrOfSFBuiltWithErrors() {
    return nrOfErrSFBuilt;
  }
}
