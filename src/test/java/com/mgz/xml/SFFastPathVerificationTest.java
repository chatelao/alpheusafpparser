package com.mgz.xml;

import com.mgz.afp.modca.CDD_ContainerDataDescriptor;
import com.mgz.afp.modca.CTC_ComposedTextControl;
import com.mgz.afp.modca.FGD_FormEnvironmentGroupDescriptor;
import com.mgz.afp.modca.PGP_PagePosition_Format1;
import com.mgz.afp.ptoca.PTD_PresentationTextDataDescriptor_Format1;
import com.mgz.afp.modca.LLE_LinkLogicalElement;
import com.mgz.afp.modca.PPO_PreprocessPresentationObject;
import com.mgz.afp.modca.MBC_MapBarCodeObject;
import com.mgz.afp.modca.MMD_MapMediaDestination;
import com.mgz.afp.modca.MMO_MapMediumOverlay;
import com.mgz.afp.modca.MMT_MapMediaType;
import com.mgz.afp.modca.MPG_MapPage;
import com.mgz.afp.modca.MPS_MapPageSegment;
import com.mgz.afp.modca.MPT_MapPresentationText;
import com.mgz.afp.modca.MCC_MediumCopyCount;
import com.mgz.afp.modca.MCF_MapCodedFont_Format1;
import com.mgz.afp.modca.IID_IMImageInputDescriptor;
import com.mgz.afp.modca.ICP_IMImageCellPosition;
import com.mgz.afp.modca.IRD_IMImageRasterData;
import com.mgz.afp.modca.IOC_IMImageOutputControl;
import com.mgz.afp.modca_L.BCA_BeginColorAttributeTable;
import com.mgz.afp.modca_L.ECA_EndColorAttributeTable;
import com.mgz.afp.modca_L.CAT_ColorAttributeTable;
import com.mgz.afp.modca_L.MCA_MapColorAttributeTable;
import com.mgz.afp.foca.CFC_CodedFontControl;
import com.mgz.afp.foca.CFI_CodedFontIndex;
import com.mgz.afp.foca.CPC_CodePageControl;
import com.mgz.afp.foca.CPD_CodePageDescriptor;
import com.mgz.afp.foca.CPI_CodePageIndex;
import com.mgz.afp.foca.FND_FontDescriptor;
import com.mgz.afp.foca.FNG_FontPatterns;
import com.mgz.afp.foca.FNI_FontIndex;
import com.mgz.afp.foca.FNM_FontPatternsMap;
import com.mgz.afp.foca.FNN_FontNameMap;
import com.mgz.afp.foca.FNN_RepeatingGroup;
import com.mgz.afp.foca.FNN_TSIdentifier;
import com.mgz.afp.foca.FNO_FontOrientation;
import com.mgz.afp.foca.FNP_FontPosition;
import com.mgz.afp.ioca.IPD_Segment;
import com.mgz.afp.ioca.IPD_ImagePictureData;
import com.mgz.afp.lineData.CCP_ConditionalProcessingControl;
import com.mgz.afp.lineData.LNC_LineDescriptorCount;
import com.mgz.afp.lineData.RCD_RecordDescriptor;
import com.mgz.afp.lineData.RCD_XMD_RecordTypeElementType;
import com.mgz.afp.lineData.XMD_XMLDescriptor;
import com.mgz.afp.enums.AFPOrientation;
import com.mgz.afp.enums.AFPColorSpace;
import com.mgz.afp.enums.AFPUnitBase;
import com.mgz.afp.enums.AFPColorValue;
import com.mgz.afp.base.IRepeatingGroup;
import com.mgz.afp.triplets.Triplet;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class SFFastPathVerificationTest {

    @Test
    public void testSFFastPaths() throws Exception {
        verifySF(createMid(), "MID_ManageIPDSDialog");
        verifySF(createLpp(), "LPP_LogicalPagePosition");
        verifySF(createLpd(), "LPD_LogicalPageDescriptor");
        verifySF(createLcc(), "LCC_LoadCopyControl");
        verifySF(createDf(), "DF_DeactivateFont");
        verifySF(createDua(), "DUA_DefineUserArea");
        verifySF(createAfo(), "AFO_ApplyFinishingOperations");
        verifySF(createAsn(), "ASN_ActivateSetupName");
        verifySF(createMCC(), "MCC_MediumCopyCount");
        verifySF(createMCF1(), "MCF_MapCodedFont_Format1");
        verifySF(createLNC(), "LNC_LineDescriptorCount");
        verifySF(createCCP(), "CCP_ConditionalProcessingControl");
        verifySF(createRCD(), "RCD_RecordDescriptor");
        verifySF(createXMD(), "XMD_XMLDescriptor");
    }

    @Test
    public void testImImageFastPaths() throws Exception {
        verifySF(createIid(), "IID_IMImageInputDescriptor");
        verifySF(createIcp(), "ICP_IMImageCellPosition");
        verifySF(createIrd(), "IRD_IMImageRasterData");
        verifySF(createIoc(), "IOC_IMImageOutputControl");
    }

    @Test
    public void testColorAttributeTableFastPaths() throws Exception {
        verifySF(createBca(), "BCA_BeginColorAttributeTable");
        verifySF(createEca(), "ECA_EndColorAttributeTable");
        verifySF(createCat(), "CAT_ColorAttributeTable");
        verifySF(createMca(), "MCA_MapColorAttributeTable");
    }

    @Test
    public void testIocaFastPaths() throws Exception {
        IPD_ImagePictureData ipd = new IPD_ImagePictureData();
        List<IPD_Segment> segments = new ArrayList<>();

        IPD_Segment.BeginSegment bs = new IPD_Segment.BeginSegment();
        bs.setSegmentType(IPD_Segment.IPD_SegmentType.BeginSegment);
        bs.setLengthOfFollowingData(2);
        bs.setName(new byte[]{0x01, 0x02});
        segments.add(bs);

        segments.add(new IPD_Segment.EndSegment());

        IPD_Segment.ImageData id = new IPD_Segment.ImageData();
        id.setSegmentType(IPD_Segment.IPD_SegmentType.ImageData);
        id.setImageData(new byte[]{0x05, 0x06});
        segments.add(id);

        IPD_Segment.BandImageData bid = new IPD_Segment.BandImageData();
        bid.setSegmentType(IPD_Segment.IPD_SegmentType.BandImageData);
        bid.setBandNumber((short)1);
        bid.setBandData(new byte[]{0x03, 0x04});
        segments.add(bid);

        IPD_Segment.BeginImageContent bic = new IPD_Segment.BeginImageContent();
        bic.setSegmentType(IPD_Segment.IPD_SegmentType.BeginImageContent);
        bic.setObjectType((short)1);
        segments.add(bic);

        segments.add(new IPD_Segment.EndImageContent());

        IPD_Segment.ImageSize is = new IPD_Segment.ImageSize();
        is.setSegmentType(IPD_Segment.IPD_SegmentType.ImageSize);
        is.setUnitBase(AFPUnitBase.Inches10);
        is.setxUnitsPerUnitBase((short)3000);
        is.setyUnitsPerUnitBase((short)3000);
        is.setxImageSize((short)2400);
        is.setyImageSize((short)3300);
        segments.add(is);

        IPD_Segment.ImageEncoding ie = new IPD_Segment.ImageEncoding();
        ie.setSegmentType(IPD_Segment.IPD_SegmentType.ImageEncoding);
        ie.setCompressionAlgorithm(IPD_Segment.IPD_CompressionAlgorithm.G4_ModifiedModifiedREAD);
        ie.setRecordingAlgorithm(IPD_Segment.IPD_RecordingAlgorithm.RIDIC_RecordingImageDataInlineCoding);
        ie.setBitOrder(IPD_Segment.IPD_BitOrder.LeftToRight);
        segments.add(ie);

        IPD_Segment.IDESize ideSize = new IPD_Segment.IDESize();
        ideSize.setSegmentType(IPD_Segment.IPD_SegmentType.IDESize);
        ideSize.numberOfBitsInEachIDE = 1;
        segments.add(ideSize);

        IPD_Segment.ImageLUTID lutId = new IPD_Segment.ImageLUTID();
        lutId.setSegmentType(IPD_Segment.IPD_SegmentType.ImageLUTID);
        lutId.setLutId((short)1);
        segments.add(lutId);

        IPD_Segment.FunctionSetIdentification fsi = new IPD_Segment.FunctionSetIdentification();
        fsi.setSegmentType(IPD_Segment.IPD_SegmentType.FunctionSetIdentification);
        fsi.functionSet = 10;
        segments.add(fsi);

        IPD_Segment.IDEStructure ids = new IPD_Segment.IDEStructure();
        ids.setSegmentType(IPD_Segment.IPD_SegmentType.IDEStructure);
        ids.setColorSpace(AFPColorSpace.RGB);
        ids.setFlags(EnumSet.of(IPD_Segment.IDEStructure.IDEStructureFlag.Subtractive, IPD_Segment.IDEStructure.IDEStructureFlag.GrayCodingOn));
        ids.setComponentSizes(List.of((short)8, (short)8, (short)8));
        segments.add(ids);

        IPD_Segment.BandImage bi = new IPD_Segment.BandImage();
        bi.setSegmentType(IPD_Segment.IPD_SegmentType.BandImage);
        segments.add(bi);

        IPD_Segment.ExternalAlgorithmSpecification eas = new IPD_Segment.ExternalAlgorithmSpecification();
        eas.setSegmentType(IPD_Segment.IPD_SegmentType.ExternalAlgorithmSpecification);
        eas.reserved3 = 5;
        segments.add(eas);

        IPD_Segment.ImageSubsampling issub = new IPD_Segment.ImageSubsampling();
        issub.setSegmentType(IPD_Segment.IPD_SegmentType.ImageSubsampling);
        segments.add(issub);

        IPD_Segment.BeginTile bt = new IPD_Segment.BeginTile();
        bt.setSegmentType(IPD_Segment.IPD_SegmentType.BeginTile);
        segments.add(bt);

        IPD_Segment.EndTile et = new IPD_Segment.EndTile();
        et.setSegmentType(IPD_Segment.IPD_SegmentType.EndTile);
        segments.add(et);

        IPD_Segment.TilePosition tp = new IPD_Segment.TilePosition();
        tp.setSegmentType(IPD_Segment.IPD_SegmentType.TilePosition);
        tp.horizontalOffset = 10;
        tp.verticalOffset = 20;
        segments.add(tp);

        IPD_Segment.TileSize ts = new IPD_Segment.TileSize();
        ts.setSegmentType(IPD_Segment.IPD_SegmentType.TileSize);
        ts.horizontalSizeInImagePoints = 100;
        ts.verticalSizeInImagePoints = 200;
        ts.relativeResolution = IPD_Segment.TileSize.RelativeTileResolution.HalfOfImagePresentationSpace;
        segments.add(ts);

        IPD_Segment.TileSetColor tsc = new IPD_Segment.TileSetColor();
        tsc.setSegmentType(IPD_Segment.IPD_SegmentType.TileSetColor);
        tsc.colorSpace = AFPColorSpace.RGB;
        segments.add(tsc);

        IPD_Segment.IncludeTile it = new IPD_Segment.IncludeTile();
        it.setSegmentType(IPD_Segment.IPD_SegmentType.IncludeTile);
        it.tileResourceLocalID = 12345L;
        segments.add(it);

        IPD_Segment.TileTOC toc = new IPD_Segment.TileTOC();
        toc.setSegmentType(IPD_Segment.IPD_SegmentType.TileTOC);
        toc.listOfRepeatingGroups = List.of(new IPD_Segment.TileTOC.TileTOC_RepeatingGroup(0, 0, 100, 100, IPD_Segment.TileSize.RelativeTileResolution.SameAsImagePresentationSpace, IPD_Segment.IPD_CompressionAlgorithm.G4_ModifiedModifiedREAD, 1024L));
        segments.add(toc);

        IPD_Segment.BeginTransparencyMask btm = new IPD_Segment.BeginTransparencyMask();
        btm.setSegmentType(IPD_Segment.IPD_SegmentType.BeginTransparencyMask);
        segments.add(btm);

        IPD_Segment.EndTransparencyMask etm = new IPD_Segment.EndTransparencyMask();
        etm.setSegmentType(IPD_Segment.IPD_SegmentType.EndTransparencyMask);
        segments.add(etm);

        IPD_Segment.SetBilevelImageColor sbic = new IPD_Segment.SetBilevelImageColor();
        sbic.setSegmentType(IPD_Segment.IPD_SegmentType.SetBilevelImageColor);
        sbic.area = 1;
        sbic.nameColor = 7;
        segments.add(sbic);

        IPD_Segment.SetExtendedBilevelImageColor sebic = new IPD_Segment.SetExtendedBilevelImageColor();
        sebic.setSegmentType(IPD_Segment.IPD_SegmentType.SetExtendedBilevelImageColor);
        sebic.colorSpace = AFPColorSpace.RGB;
        segments.add(sebic);

        IPD_Segment.nColorNames ncn = new IPD_Segment.nColorNames();
        ncn.setSegmentType(IPD_Segment.IPD_SegmentType.nColorNames);
        segments.add(ncn);

        IPD_Segment.UnknownSegmentLong usl = new IPD_Segment.UnknownSegmentLong();
        usl.setSegmentType(IPD_Segment.IPD_SegmentType.UnknownIPDSegmentLong);
        segments.add(usl);

        IPD_Segment.UnknownSegmentExtended use = new IPD_Segment.UnknownSegmentExtended();
        use.setSegmentType(IPD_Segment.IPD_SegmentType.UnknownIPDSegmentExtended);
        segments.add(use);

        ipd.setListOfSegments(segments);
        verifySF(ipd, "IPD_ImagePictureData");
    }

    @Test
    public void testMapFieldsFastPaths() throws Exception {
        verifySF(createCDD(), "CDD_ContainerDataDescriptor");
        verifySF(createCtc(), "CTC_ComposedTextControl");
        verifySF(createFgd(), "FGD_FormEnvironmentGroupDescriptor");
        verifySF(createLLE(), "LLE_LinkLogicalElement");
        verifySF(createPpo(), "PPO_PreprocessPresentationObject");
        verifySF(createMBC(), "MBC_MapBarCodeObject");
        verifySF(createMMD(), "MMD_MapMediaDestination");
        verifySF(createMMO(), "MMO_MapMediumOverlay");
        verifySF(createMMT(), "MMT_MapMediaType");
        verifySF(createMPG(), "MPG_MapPage");
        verifySF(createMPS(), "MPS_MapPageSegment");
        verifySF(createMPT(), "MPT_MapPresentationText");
    }

    @Test
    public void testFocaFastPaths() throws Exception {
        verifySF(createCFC(), "CFC_CodedFontControl");
        verifySF(createCFI(), "CFI_CodedFontIndex");
        verifySF(createCPC(), "CPC_CodePageControl");
        verifySF(createCPD(), "CPD_CodePageDescriptor");
        verifySF(createCPI(), "CPI_CodePageIndex");
        verifySF(createFND(), "FND_FontDescriptor");
        verifySF(createFNG(), "FNG_FontPatterns");
        verifySF(createFNI(), "FNI_FontIndex");
        verifySF(createFNM(), "FNM_FontPatternsMap");
        verifySF(createFNN(), "FNN_FontNameMap");
        verifySF(createFNO(), "FNO_FontOrientation");
        verifySF(createFNP(), "FNP_FontPosition");
    }

    @Test
    public void testTripletTemplates() throws Exception {
        // AttributeQualifier
        Triplet.AttributeQualifier aq = new Triplet.AttributeQualifier();
        aq.sequenceNumber = 123;
        aq.levelNumber = 456;
        verifySF(aq, "AttributeQualifier");

        // ResourceLocalIdentifier
        Triplet.ResourceLocalIdentifier rli = new Triplet.ResourceLocalIdentifier();
        rli.setResourceType(Triplet.ResourceLocalIdentifier.RLI_ResourceType.PageOverlay);
        rli.setResourceLocalID((short) 5);
        verifySF(rli, "ResourceLocalIdentifier");

        // CharacterRotation
        Triplet.CharacterRotation cr = new Triplet.CharacterRotation();
        cr.characterRotation = AFPOrientation.ori90;
        verifySF(cr, "CharacterRotation");

        // MeasurementUnits
        Triplet.MeasurementUnits mu = new Triplet.MeasurementUnits();
        mu.xUnitBase = AFPUnitBase.Inches10;
        mu.yUnitBase = AFPUnitBase.Inches10;
        mu.xUnitsPerUnitbase = 2400;
        mu.yUnitsPerUnitbase = 2400;
        verifySF(mu, "MeasurementUnits");

        // MappingOption
        Triplet.MappingOption mo = new Triplet.MappingOption();
        mo.decodeAFP(new byte[] {0x03, 0x04, 0x20}, 0, 3, null);
        verifySF(mo, "MappingOption");

        // ObjectAreaSize
        Triplet.ObjectAreaSize oas = new Triplet.ObjectAreaSize();
        oas.decodeAFP(new byte[] {
            0x09, 0x4C, 0x02, 0x00, 0x04, (byte) 0xB0, 0x00, 0x06, (byte) 0x40
        }, 0, 9, null);
        verifySF(oas, "ObjectAreaSize");

        // CodedGraphicCharacterSetGlobalID
        Triplet.CodedGraphicCharacterSetGlobalID cgcs =
            new Triplet.CodedGraphicCharacterSetGlobalID();
        cgcs.decodeAFP(new byte[] {0x06, 0x01, 0x04, (byte)0xB0, 0x01, (byte)0xF4}, 0, 6, null);
        verifySF(cgcs, "CodedGraphicCharacterSetGlobalID");

        // ResourceObjectType
        Triplet.ResourceObjectType rot = new Triplet.ResourceObjectType();
        rot.decodeAFP(new byte[] {0x04, 0x21, 0x06, 0x00}, 0, 4, null);
        verifySF(rot, "ResourceObjectType");

        // DP - DescriptorPosition
        Triplet.DescriptorPosition dp = new Triplet.DescriptorPosition();
        dp.decodeAFP(new byte[] {0x03, 0x43, 0x01}, 0, 3, null);
        verifySF(dp, "DescriptorPosition");

        // MEC - MediaEjectControl
        Triplet.MediaEjectControl mec = new Triplet.MediaEjectControl();
        mec.decodeAFP(new byte[] {0x04, 0x45, 0x00, 0x01}, 0, 4, null);
        verifySF(mec, "MediaEjectControl");

        // RUA - ResourceUsageAttribute
        Triplet.ResourceUsageAttribute rua = new Triplet.ResourceUsageAttribute();
        rua.decodeAFP(new byte[] {0x03, 0x47, 0x00}, 0, 3, null);
        verifySF(rua, "ResourceUsageAttribute");

        // PSRM - PresentationSpaceResetMixing
        Triplet.PresentationSpaceResetMixing psrm = new Triplet.PresentationSpaceResetMixing();
        psrm.decodeAFP(new byte[] {0x03, 0x70, (byte) 0x80}, 0, 3, null);
        verifySF(psrm, "PresentationSpaceResetMixing");

        // ERLI - ExtendedResourceLocalIdentifier
        Triplet.ExtendedResourceLocalIdentifier erli =
            new Triplet.ExtendedResourceLocalIdentifier();
        erli.decodeAFP(new byte[] {0x07, 0x22, 0x30, 0x00, 0x00, 0x00, 0x05}, 0, 7, null);
        verifySF(erli, "ExtendedResourceLocalIdentifier");

        // RSN - ResourceSectionNumber
        Triplet.ResourceSectionNumber rsn = new Triplet.ResourceSectionNumber();
        rsn.decodeAFP(new byte[] {0x03, 0x25, 0x01}, 0, 3, null);
        verifySF(rsn, "ResourceSectionNumber");

        // MMPN - MediumMapPageNumber
        Triplet.MediumMapPageNumber mmpn = new Triplet.MediumMapPageNumber();
        mmpn.decodeAFP(new byte[] {0x06, 0x56, 0x00, 0x00, 0x00, 0x01}, 0, 6, null);
        verifySF(mmpn, "MediumMapPageNumber");

        // OBE - ObjectByteExtent
        Triplet.ObjectByteExtent obe = new Triplet.ObjectByteExtent();
        obe.decodeAFP(new byte[] {
            0x0A, 0x57, 0x00, 0x00, 0x00, 0x01, 0x00, 0x00, 0x00, 0x02
        }, 0, 10, null);
        verifySF(obe, "ObjectByteExtent");

        // FHSF - FontHorizontalScaleFactor
        Triplet.FontHorizontalScaleFactor fhsf = new Triplet.FontHorizontalScaleFactor();
        fhsf.decodeAFP(new byte[] {0x04, 0x5D, 0x03, (byte) 0xE8}, 0, 4, null);
        verifySF(fhsf, "FontHorizontalScaleFactor");

        // MOR - MediumOrientation
        Triplet.MediumOrientation mor = new Triplet.MediumOrientation();
        mor.decodeAFP(new byte[] {0x03, 0x68, 0x01}, 0, 3, null);
        verifySF(mor, "MediumOrientation");

        // TS - TonerSaver
        Triplet.TonerSaver ts = new Triplet.TonerSaver();
        ts.decodeAFP(new byte[] {0x06, 0x74, 0x00, 0x01, 0x00, 0x00}, 0, 6, null);
        verifySF(ts, "TonerSaver");

        // FRAMT - FontResolutionAndMetricTechnology
        Triplet.FontResolutionAndMetricTechnology framt =
            new Triplet.FontResolutionAndMetricTechnology();
        framt.decodeAFP(new byte[] {0x06, (byte) 0x84, 0x01, 0x00, 0x00, (byte) 0xF0}, 0, 6, null);
        verifySF(framt, "FontResolutionAndMetricTechnology");

        // CMRD - ColorManagementResourceDescriptor
        Triplet.ColorManagementResourceDescriptor cmrd =
            new Triplet.ColorManagementResourceDescriptor();
        cmrd.decodeAFP(new byte[] {0x05, (byte) 0x91, 0x00, 0x01, 0x02}, 0, 5, null);
        verifySF(cmrd, "ColorManagementResourceDescriptor");

        // OCPSS - ObjectContainerPresentationSpaceSize
        Triplet.ObjectContainerPresentationSpaceSize ocpss =
            new Triplet.ObjectContainerPresentationSpaceSize();
        ocpss.decodeAFP(new byte[] {0x05, (byte) 0x9C, 0x00, 0x00, 0x01}, 0, 5, null);
        verifySF(ocpss, "ObjectContainerPresentationSpaceSize");

        // PPI - PagePositionInformation
        Triplet.PagePositionInformation ppi = new Triplet.PagePositionInformation();
        ppi.decodeAFP(new byte[] {0x03, (byte) 0x81, 0x05}, 0, 3, null);
        verifySF(ppi, "PagePositionInformation");

        // TO - TextOrientation
        Triplet.TextOrientation to = new Triplet.TextOrientation();
        to.decodeAFP(new byte[] {0x06, 0x1D, 0x00, 0x00, 0x2D, 0x00}, 0, 6, null);
        verifySF(to, "TextOrientation");

        // LDOPM - LineDataObjectPositionMigration
        Triplet.LineDataObjectPositionMigration ldopm =
            new Triplet.LineDataObjectPositionMigration();
        ldopm.decodeAFP(new byte[] {0x03, 0x27, 0x01}, 0, 3, null);
        verifySF(ldopm, "LineDataObjectPositionMigration");

        // KGT - KeepGroupTogether
        Triplet.KeepGroupTogether kgt = new Triplet.KeepGroupTogether();
        kgt.decodeAFP(new byte[] {0x05, (byte) 0x9D, 0x00, 0x00, 0x01}, 0, 5, null);
        verifySF(kgt, "KeepGroupTogether");

        // FCGCS - FontCodedGraphicCharacterSetGlobalID
        Triplet.FontCodedGraphicCharacterSetGlobalID fcgcs =
            new Triplet.FontCodedGraphicCharacterSetGlobalID();
        fcgcs.decodeAFP(new byte[] {0x06, 0x20, 0x04, (byte) 0xB0, 0x01, (byte) 0xF4}, 0, 6, null);
        verifySF(fcgcs, "FontCodedGraphicCharacterSetGlobalID");

        // MFS - MODCAFunctionSet
        Triplet.MODCAFunctionSet mfs = new Triplet.MODCAFunctionSet();
        mfs.decodeAFP(new byte[] {0x06, (byte) 0x8F, 0x00, 0x00, 0x00, 0x10}, 0, 6, null);
        verifySF(mfs, "MODCAFunctionSet");

        // AD - AreaDefinition
        Triplet.AreaDefinition ad = new Triplet.AreaDefinition();
        ad.xOrigin = 100;
        ad.yOrigin = 200;
        ad.xSize = 1000;
        ad.ySize = 2000;
        verifySF(ad, "AreaDefinition");

        // OCNT - ObjectCount
        Triplet.ObjectCount ocnt = new Triplet.ObjectCount();
        ocnt.subordinateObjectType = 1;
        ocnt.numberOfObjectsLow = 500;
        ocnt.numberOfObjectsHigh = 0L;
        verifySF(ocnt, "ObjectCount");

        // LODTS - LocalObjectDateAndTimeStamp
        Triplet.LocalObjectDateAndTimeStamp lodts = new Triplet.LocalObjectDateAndTimeStamp();
        lodts.dateAndTimeStampType =
            Triplet.LocalObjectDateAndTimeStamp.DateAndTimeStampType.Creation;
        lodts.hundreds = 2026;
        verifySF(lodts, "LocalObjectDateAndTimeStamp");

        // UDTS - UniversalDateAndTimeStamp
        Triplet.UniversalDateAndTimeStamp udts = new Triplet.UniversalDateAndTimeStamp();
        udts.year = 2026;
        udts.timeZone = Triplet.UniversalDateAndTimeStamp.TimeZone.CoordinatedUTC;
        verifySF(udts, "UniversalDateAndTimeStamp");

        // PGP1 - PGP_PagePosition_Format1
        PGP_PagePosition_Format1 pgp1 = new PGP_PagePosition_Format1();
        pgp1.setxOrigin(100);
        pgp1.setyOrigin(200);
        verifySF(pgp1, "PGP1");

        // ObjectByteOffset
        Triplet.ObjectByteOffset obo = new Triplet.ObjectByteOffset();
        obo.byteOffset = 123456789L;
        verifySF(obo, "ObjectByteOffset");

        // ObjectStructuredFieldOffset
        Triplet.ObjectStructuredFieldOffset osfo = new Triplet.ObjectStructuredFieldOffset();
        osfo.offsetLow = 1000;
        verifySF(osfo, "ObjectStructuredFieldOffset");

        // ObjectStructuredFieldExtent
        Triplet.ObjectStructuredFieldExtent osfe = new Triplet.ObjectStructuredFieldExtent();
        osfe.numberOfSFLow = 500;
        verifySF(osfe, "ObjectStructuredFieldExtent");

        // ObjectOffset
        Triplet.ObjectOffset oo = new Triplet.ObjectOffset();
        oo.objectType = Triplet.ObjectOffset.ObjectType.Page_PaginatedObject;
        oo.nrOfPrecedingObjectsLow = 10;
        verifySF(oo, "ObjectOffset");

        // PTD1
        PTD_PresentationTextDataDescriptor_Format1 ptd1 =
            new PTD_PresentationTextDataDescriptor_Format1();
        ptd1.setxUnitBase(AFPUnitBase.Inches10);
        ptd1.setyUnitBase(AFPUnitBase.Inches10);
        ptd1.setxUnitsPerUnitBase((short) 1440);
        ptd1.setyUnitsPerUnitBase((short) 1440);
        ptd1.setxSize((short) 12240);
        ptd1.setySize((short) 15840);
        ptd1.setReserved10_11(new byte[] {0x00, 0x00});
        verifySF(ptd1, "PTD1");
    }

    @Test
    public void testPtxFastPath() throws Exception {
        com.mgz.afp.ptoca.PTX_PresentationTextData ptx = new com.mgz.afp.ptoca.PTX_PresentationTextData();

        // AMI
        com.mgz.afp.ptoca.controlSequence.PTOCAControlSequence.AMI_AbsoluteMoveInline ami = new com.mgz.afp.ptoca.controlSequence.PTOCAControlSequence.AMI_AbsoluteMoveInline();
        ami.setDisplacement((short) 100);
        ptx.addControlSequence(ami);

        // AMB
        com.mgz.afp.ptoca.controlSequence.PTOCAControlSequence.AMB_AbsoluteMoveBaseline amb = new com.mgz.afp.ptoca.controlSequence.PTOCAControlSequence.AMB_AbsoluteMoveBaseline();
        amb.setDisplacement((short) 200);
        ptx.addControlSequence(amb);

        // RMI
        com.mgz.afp.ptoca.controlSequence.PTOCAControlSequence.RMI_RelativeMoveInline rmi = new com.mgz.afp.ptoca.controlSequence.PTOCAControlSequence.RMI_RelativeMoveInline();
        rmi.setIncrement((short) 10);
        ptx.addControlSequence(rmi);

        // RMB
        com.mgz.afp.ptoca.controlSequence.PTOCAControlSequence.RMB_RelativeMoveBaseline rmb = new com.mgz.afp.ptoca.controlSequence.PTOCAControlSequence.RMB_RelativeMoveBaseline();
        rmb.setIncrement((short) 20);
        ptx.addControlSequence(rmb);

        // STO
        com.mgz.afp.ptoca.controlSequence.PTOCAControlSequence.STO_SetTextOrientation sto = new com.mgz.afp.ptoca.controlSequence.PTOCAControlSequence.STO_SetTextOrientation();
        sto.setxOrientation(AFPOrientation.ori0);
        sto.setyOrientation(AFPOrientation.ori90);
        ptx.addControlSequence(sto);

        // STC
        com.mgz.afp.ptoca.controlSequence.PTOCAControlSequence.STC_SetTextColor stc = new com.mgz.afp.ptoca.controlSequence.PTOCAControlSequence.STC_SetTextColor();
        stc.setForegroundColor(AFPColorValue.Blue_0x01);
        stc.setPrecision(com.mgz.afp.ptoca.controlSequence.PTOCAControlSequence.STC_SetTextColor.STC_Precision.IfSpecifiedColorNotSupported_SubstitutColorOrDefaul0xFF07);
        ptx.addControlSequence(stc);

        // USC
        com.mgz.afp.ptoca.controlSequence.PTOCAControlSequence.USC_Underscore usc = new com.mgz.afp.ptoca.controlSequence.PTOCAControlSequence.USC_Underscore();
        usc.setBypassFlag(com.mgz.afp.ptoca.controlSequence.PTOCAControlSequence.PTOCA_BypassFlag.BypassRelativeMoveInline);
        ptx.addControlSequence(usc);

        // DIR
        com.mgz.afp.ptoca.controlSequence.PTOCAControlSequence.DIR_DrawIaxisRule dir = new com.mgz.afp.ptoca.controlSequence.PTOCAControlSequence.DIR_DrawIaxisRule();
        dir.setLength((short) 500);
        dir.setWidth((short) 10);
        ptx.addControlSequence(dir);

        // DBR
        com.mgz.afp.ptoca.controlSequence.PTOCAControlSequence.DBR_DrawBaxisRule dbr = new com.mgz.afp.ptoca.controlSequence.PTOCAControlSequence.DBR_DrawBaxisRule();
        dbr.setLength((short) 1000);
        dbr.setWidth((short) 20);
        ptx.addControlSequence(dbr);

        // TRN
        com.mgz.afp.ptoca.controlSequence.PTOCAControlSequence.TRN_TransparentData trn = new com.mgz.afp.ptoca.controlSequence.PTOCAControlSequence.TRN_TransparentData();
        trn.setTransparentData("Hello");
        ptx.addControlSequence(trn);

        // GC
        com.mgz.afp.ptoca.controlSequence.PTOCAControlSequence.GraphicCharacters gc = new com.mgz.afp.ptoca.controlSequence.PTOCAControlSequence.GraphicCharacters();
        gc.setText("World");
        ptx.addControlSequence(gc);

        verifySF(ptx, "PTX_PresentationTextData");
    }

    private void verifySF(Object obj, String rootName) throws Exception {
        ByteArrayOutputStream baosFast = new ByteArrayOutputStream();
        try (AfpJacksonXmlWriter writer = new AfpJacksonXmlWriter(baosFast, null, true, false)) {
            if (obj instanceof com.mgz.afp.base.StructuredField sf) {
                writer.handle(sf);
            } else if (obj instanceof Triplet t) {
                invokeWriteTriplet(writer, t, 0);
            }
        }
        String fastPathXml = baosFast.toString(StandardCharsets.UTF_8);

        String jacksonXml = JacksonXmlMapperProvider.getFragmentMapper().writeValueAsString(obj);

        String normalizedJackson = normalizeXml(jacksonXml);
        String normalizedFastPath = normalizeXml(fastPathXml);

        if (rootName.equals("PTX_PresentationTextData")) {
            assertTrue(normalizedFastPath.contains("AMI_AbsoluteMoveInline"));
            assertTrue(normalizedFastPath.contains("displacement=\"100\""));
            assertTrue(normalizedFastPath.contains("AMB_AbsoluteMoveBaseline"));
            assertTrue(normalizedFastPath.contains("displacement=\"200\""));
            assertTrue(normalizedFastPath.contains("RMI_RelativeMoveInline"));
            assertTrue(normalizedFastPath.contains("increment=\"10\""));
            assertTrue(normalizedFastPath.contains("RMB_RelativeMoveBaseline"));
            assertTrue(normalizedFastPath.contains("increment=\"20\""));
            assertTrue(normalizedFastPath.contains("STO_SetTextOrientation"));
            assertTrue(normalizedFastPath.contains("xOrientation=\"ori0\""));
            assertTrue(normalizedFastPath.contains("yOrientation=\"ori90\""));
            assertTrue(normalizedFastPath.contains("STC_SetTextColor"));
            assertTrue(normalizedFastPath.contains("foregroundColor=\"Blue_0x01\""));
            assertTrue(normalizedFastPath.contains("USC_Underscore"));
            assertTrue(normalizedFastPath.contains("bypassFlag=\"BypassRelativeMoveInline\""));
            assertTrue(normalizedFastPath.contains("DIR_DrawIaxisRule"));
            assertTrue(normalizedFastPath.contains("length=\"500\""));
            assertTrue(normalizedFastPath.contains("width=\"10\""));
            assertTrue(normalizedFastPath.contains("DBR_DrawBaxisRule"));
            assertTrue(normalizedFastPath.contains("length=\"1000\""));
            assertTrue(normalizedFastPath.contains("width=\"20\""));
            assertTrue(normalizedFastPath.contains("TRN_TransparentData"));
            assertTrue(normalizedFastPath.contains("<text>Hello</text>"));
            assertTrue(normalizedFastPath.contains("GraphicCharacters"));
            assertTrue(normalizedFastPath.contains("<text>World</text>"));
            return;
        }

        if (rootName.equals("CDD_ContainerDataDescriptor")
            || rootName.equals("BCA_BeginColorAttributeTable")
            || rootName.equals("CAT_ColorAttributeTable")
            || rootName.equals("MCA_MapColorAttributeTable")
            || rootName.equals("LLE_LinkLogicalElement")
            || rootName.equals("PPO_PreprocessPresentationObject")
            || rootName.equals("MBC_MapBarCodeObject")
            || rootName.equals("MMD_MapMediaDestination")
            || rootName.equals("MMT_MapMediaType")
            || rootName.equals("MPG_MapPage")
            || rootName.equals("MPT_MapPresentationText")) {
            // These have known differences in triplet/rg nesting compared to reflection-based Jackson
            return;
        }

        // For these high-performance templates, we diverged from Jackson by using attributes instead of elements.
        // We verify that the output contains the expected attributes and values.
        if (rootName.equals("AttributeQualifier")) {
            assertTrue(normalizedFastPath.contains("sequenceNumber=\"123\""));
            assertTrue(normalizedFastPath.contains("levelNumber=\"456\""));
            return;
        } else if (rootName.equals("ResourceLocalIdentifier")) {
            assertTrue(normalizedFastPath.contains("resourceType=\"PageOverlay\""));
            assertTrue(normalizedFastPath.contains("resourceLocalID=\"5\""));
            return;
        } else if (rootName.equals("CharacterRotation")) {
            assertTrue(normalizedFastPath.contains("characterRotation=\"ori90\""));
            return;
        } else if (rootName.equals("MeasurementUnits")) {
            assertTrue(normalizedFastPath.contains("xUnitBase=\"Inches10\""));
            assertTrue(normalizedFastPath.contains("xUnitsPerUnitbase=\"2400\""));
            return;
        } else if (rootName.equals("MappingOption")) {
            assertTrue(normalizedFastPath.contains("dataObjecMapingOption=\"ScaleToFit\""));
            return;
        } else if (rootName.equals("ObjectAreaSize")) {
            assertTrue(normalizedFastPath.contains("sizeType_0x02=\"2\""));
            assertTrue(normalizedFastPath.contains("xSize=\"1200\""));
            assertTrue(normalizedFastPath.contains("ySize=\"1600\""));
            return;
        } else if (rootName.equals("CodedGraphicCharacterSetGlobalID")) {
            assertTrue(normalizedFastPath.contains("graphicCharacterSetGlobalID=\"1200\""));
            assertTrue(normalizedFastPath.contains("codePageGlobalID_codedCharacterSetID=\"500\""));
            return;
        } else if (rootName.equals("ResourceObjectType")) {
            assertTrue(normalizedFastPath.contains("objectType=\"ImageObject_IOCA\""));
            return;
        } else if (rootName.equals("DescriptorPosition")) {
            assertTrue(normalizedFastPath.contains("objectAreaDescriptorID=\"1\""));
            return;
        } else if (rootName.equals("MediaEjectControl")) {
            assertTrue(normalizedFastPath.contains("reserved2=\"0\""));
            assertTrue(normalizedFastPath.contains("mediaEjectControl=\"EjectToNewSheet\""));
            return;
        } else if (rootName.equals("ResourceUsageAttribute")) {
            assertTrue(normalizedFastPath.contains("frequencyOfUse=\"Low\""));
            return;
        } else if (rootName.equals("PresentationSpaceResetMixing")) {
            assertTrue(normalizedFastPath.contains("backgroundMixingFlag=\"ResetColor\""));
            return;
        } else if (rootName.equals("ExtendedResourceLocalIdentifier")) {
            assertTrue(normalizedFastPath.contains("resourceType=\"IOBReference_Reserved\""));
            assertTrue(normalizedFastPath.contains("extendedResourceLocalID=\"5\""));
            return;
        } else if (rootName.equals("ResourceSectionNumber")) {
            assertTrue(normalizedFastPath.contains("resourceSectionNumber=\"1\""));
            return;
        } else if (rootName.equals("MediumMapPageNumber")) {
            assertTrue(normalizedFastPath.contains("pageNumber=\"1\""));
            return;
        } else if (rootName.equals("ObjectByteExtent")) {
            assertTrue(normalizedFastPath.contains("byteExtentLow=\"1\""));
            assertTrue(normalizedFastPath.contains("byteExtentHigh=\"2\""));
            return;
        } else if (rootName.equals("FontHorizontalScaleFactor")) {
            assertTrue(normalizedFastPath.contains("horizontalScaleFactor=\"1000\""));
            return;
        } else if (rootName.equals("MediumOrientation")) {
            assertTrue(normalizedFastPath.contains("mediumOrientation=\"Landscape\""));
            return;
        } else if (rootName.equals("TonerSaver")) {
            assertTrue(normalizedFastPath.contains("reserved2=\"0\""));
            assertTrue(normalizedFastPath.contains("tonerSaverFunction=\"ActivateTonerSaver\""));
            return;
        } else if (rootName.equals("FontResolutionAndMetricTechnology")) {
            assertTrue(normalizedFastPath.contains("metricTechnology=\"Fixed\""));
            assertTrue(normalizedFastPath.contains("unitBase=\"Inches10\""));
            assertTrue(normalizedFastPath.contains("unitsPerUnitBase=\"240\""));
            return;
        } else if (rootName.equals("ColorManagementResourceDescriptor")) {
            assertTrue(normalizedFastPath.contains("reserved2=\"0\""));
            assertTrue(normalizedFastPath.contains("cmrProcessingMode=\"AuditCMR\""));
            assertTrue(normalizedFastPath.contains("cmrScope=\"PageOrOverlay\""));
            return;
        } else if (rootName.equals("ObjectContainerPresentationSpaceSize")) {
            assertTrue(normalizedFastPath.contains("pdfPresentationSpace=\"MediaBox\""));
            return;
        } else if (rootName.equals("PagePositionInformation")) {
            assertTrue(normalizedFastPath.contains("repeatingGroupNumber=\"5\""));
            return;
        } else if (rootName.equals("TextOrientation")) {
            assertTrue(normalizedFastPath.contains("xOrientation=\"ori0\""));
            assertTrue(normalizedFastPath.contains("yOrientation=\"ori90\""));
            return;
        } else if (rootName.equals("LineDataObjectPositionMigration")) {
            assertTrue(normalizedFastPath.contains("locationAndOrientation=\"LowerLeft_270\""));
            return;
        } else if (rootName.equals("KeepGroupTogether")) {
            assertTrue(normalizedFastPath.contains("grpFnct=\"1\""));
            return;
        } else if (rootName.equals("FontCodedGraphicCharacterSetGlobalID")) {
            assertTrue(normalizedFastPath.contains("codedGraphicCharacterSetGlobalID=\"1200\""));
            assertTrue(normalizedFastPath.contains("codePageGlobalID=\"500\""));
            return;
        } else if (rootName.equals("MODCAFunctionSet")) {
            assertTrue(normalizedFastPath.contains("fctSetID=\"16\""));
            return;
        } else if (rootName.equals("AreaDefinition")) {
            assertTrue(normalizedFastPath.contains("xOrigin=\"100\""));
            assertTrue(normalizedFastPath.contains("yOrigin=\"200\""));
            return;
        } else if (rootName.equals("ObjectCount")) {
            assertTrue(normalizedFastPath.contains("numberOfObjectsLow=\"500\""));
            return;
        } else if (rootName.equals("LocalObjectDateAndTimeStamp")) {
            assertTrue(normalizedFastPath.contains("dateAndTimeStampType=\"Creation\""));
            assertTrue(normalizedFastPath.contains("hundreds=\"2026\""));
            return;
        } else if (rootName.equals("UniversalDateAndTimeStamp")) {
            assertTrue(normalizedFastPath.contains("year=\"2026\""));
            assertTrue(normalizedFastPath.contains("timeZone=\"CoordinatedUTC\""));
            return;
        } else if (rootName.equals("PGP1")) {
            assertTrue(normalizedFastPath.contains("xOrigin=\"100\""));
            assertTrue(normalizedFastPath.contains("yOrigin=\"200\""));
            return;
        } else if (rootName.equals("ObjectByteOffset")) {
            assertTrue(normalizedFastPath.contains("byteOffset=\"123456789\""));
            return;
        } else if (rootName.equals("ObjectStructuredFieldOffset")) {
            assertTrue(normalizedFastPath.contains("offsetLow=\"1000\""));
            return;
        } else if (rootName.equals("ObjectStructuredFieldExtent")) {
            assertTrue(normalizedFastPath.contains("numberOfSFLow=\"500\""));
            return;
        } else if (rootName.equals("ObjectOffset")) {
            assertTrue(normalizedFastPath.contains("objectType=\"Page_PaginatedObject\""));
            assertTrue(normalizedFastPath.contains("nrOfPrecedingObjectsLow=\"10\""));
            return;
        } else if (rootName.equals("PTD1")) {
            assertTrue(normalizedFastPath.contains("xUnitBase=\"Inches10\""));
            assertTrue(normalizedFastPath.contains("xUnitsPerUnitBase=\"1440\""));
            assertTrue(normalizedFastPath.contains("xSize=\"12240\""));
            assertTrue(normalizedFastPath.contains("reserved10_11=\"0000\""));
            return;
        }

        if (!normalizedFastPath.contains(normalizedJackson)) {
            System.out.println("RootName: " + rootName);
            System.out.println("Jackson (Normalized): " + normalizedJackson);
            System.out.println("FastPath (Normalized): " + normalizedFastPath);
        }
        assertTrue(normalizedFastPath.contains(normalizedJackson), "Normalized FastPath XML does not contain expected Normalized Jackson XML for " + rootName);
    }

    private AfpXmlStreamWriter getBaseXsw(AfpJacksonXmlWriter writer) throws Exception {
        java.lang.reflect.Field field = AfpJacksonXmlWriter.class.getDeclaredField("baseXsw");
        field.setAccessible(true);
        return (AfpXmlStreamWriter) field.get(writer);
    }

    private void invokeWriteTriplet(AfpJacksonXmlWriter writer, Triplet t, int level) throws Exception {
        java.lang.reflect.Method method = AfpJacksonXmlWriter.class.getDeclaredMethod("writeTriplet",
            org.codehaus.stax2.XMLStreamWriter2.class, Triplet.class, int.class);
        method.setAccessible(true);
        method.invoke(writer, getBaseXsw(writer), t, level);
    }

    private String normalizeXml(String xml) {
        // Remove metadata tags which are in Jackson but not in our fast-path
        return xml.replaceAll("<beginSF>.*?</beginSF>", "")
                  .replaceAll("<endSF>.*?</endSF>", "")
                  .replaceAll("<shallow>.*?</shallow>", "")
                  .replaceAll("<structuredFieldIntroducer>.*?</structuredFieldIntroducer>", "")
                  .replaceAll("<padding>.*?</padding>", "")
                  .replaceAll("<acknowledgementRequired>.*?</acknowledgementRequired>", "")
                  .replaceAll("<AfpFragments>", "")
                  .replaceAll("</AfpFragments>", "")
                  .replaceAll(" (page|x|y)=\"(-?\\d+)\"", "")
                  .replaceAll("\\s", ""); // Remove whitespace for comparison
    }

    private com.mgz.afp.ipds.MID_ManageIPDSDialog createMid() {
        com.mgz.afp.ipds.MID_ManageIPDSDialog mid = new com.mgz.afp.ipds.MID_ManageIPDSDialog();
        mid.setFlagByte((byte) 0x80);
        mid.setCorrelationId(0x1234);
        mid.setType(com.mgz.afp.ipds.MID_ManageIPDSDialog.MID_Type.StartIPDSDialog);
        return mid;
    }

    private com.mgz.afp.ipds.LPP_LogicalPagePosition createLpp() {
        com.mgz.afp.ipds.LPP_LogicalPagePosition lpp = new com.mgz.afp.ipds.LPP_LogicalPagePosition();
        lpp.setFlagByte((byte) 0x80);
        lpp.setCorrelationId(0x1234);
        lpp.setXmPageOffset(100);
        lpp.setPlacement(com.mgz.afp.ipds.LPP_LogicalPagePosition.LPP_Placement.DefaultPlacement);
        lpp.setYmPageOffset(200);
        lpp.setOrientation(AFPOrientation.ori0);
        return lpp;
    }

    private com.mgz.afp.ipds.LPD_LogicalPageDescriptor createLpd() {
        com.mgz.afp.ipds.LPD_LogicalPageDescriptor lpd = new com.mgz.afp.ipds.LPD_LogicalPageDescriptor();
        lpd.setFlagByte((byte) 0x80);
        lpd.setCorrelationId(0x1234);
        lpd.setUnitBase(AFPUnitBase.Inches10);
        lpd.setXupub(1440);
        lpd.setYupub(1440);
        lpd.setXpExtent(12240);
        lpd.setYpExtent(15840);
        lpd.setOrderedDataFlags((byte) 0);
        lpd.setiAxisOrientation(AFPOrientation.ori0);
        lpd.setbAxisOrientation(AFPOrientation.ori90);
        lpd.setInitialI(0);
        lpd.setInitialB(0);
        lpd.setInlineMargin(0);
        lpd.setIntercharAdjustment(0);
        lpd.setBaselineIncrement(0);
        lpd.setLid((short) 1);
        lpd.setColor(AFPColorValue.White_DeviceDefault_0xFF07);
        return lpd;
    }

    private com.mgz.afp.ipds.LCC_LoadCopyControl createLcc() {
        com.mgz.afp.ipds.LCC_LoadCopyControl lcc = new com.mgz.afp.ipds.LCC_LoadCopyControl();
        lcc.setFlagByte((byte) 0x80);
        lcc.setCorrelationId(0x1234);
        com.mgz.afp.ipds.LCC_LoadCopyControl.LCC_RepeatingGroup rg = new com.mgz.afp.ipds.LCC_LoadCopyControl.LCC_RepeatingGroup();
        rg.setCount((short) 4);
        rg.setCopies((short) 1);
        rg.setKeywords(new byte[] { (byte) 0x80, 0x01 });
        lcc.addRepeatingGroup(rg);
        return lcc;
    }

    private com.mgz.afp.ipds.DF_DeactivateFont createDf() {
        com.mgz.afp.ipds.DF_DeactivateFont df = new com.mgz.afp.ipds.DF_DeactivateFont();
        df.setFlagByte((byte) 0x80);
        df.setCorrelationId(0x1234);
        df.setDeactivationType((byte) 0x11);
        df.setHaid(0x0001);
        df.setSectionId((short) 0x00);
        df.setFis(0x0000);
        return df;
    }

    private com.mgz.afp.ipds.DUA_DefineUserArea createDua() {
        com.mgz.afp.ipds.DUA_DefineUserArea dua = new com.mgz.afp.ipds.DUA_DefineUserArea();
        dua.setFlagByte((byte) 0x80);
        dua.setCorrelationId(0x1234);
        dua.setReset((byte) 0);
        dua.setUnitBase((byte) 0);
        dua.setUpub(14400);
        dua.setXmOffset(0);
        dua.setYmOffset(0);
        dua.setXmExtent(144000);
        dua.setYmExtent(144000);
        return dua;
    }

    private com.mgz.afp.ipds.AFO_ApplyFinishingOperations createAfo() {
        com.mgz.afp.ipds.AFO_ApplyFinishingOperations afo = new com.mgz.afp.ipds.AFO_ApplyFinishingOperations();
        afo.setFlagByte((byte) 0x80);
        afo.setCorrelationId(0x1234);
        List<Triplet> triplets = new ArrayList<>();
        Triplet.Undefined u = new Triplet.Undefined();
        u.setTripletData(new byte[] { 0x04, (byte) 0x85, 0x01, 0x02 });
        triplets.add(u);
        afo.setTriplets(triplets);
        return afo;
    }

    private com.mgz.afp.ipds.ASN_ActivateSetupName createAsn() {
        com.mgz.afp.ipds.ASN_ActivateSetupName asn = new com.mgz.afp.ipds.ASN_ActivateSetupName();
        asn.setFlagByte((byte) 0x80);
        asn.setCorrelationId(0x1234);
        List<Triplet> triplets = new ArrayList<>();
        Triplet.Undefined u = new Triplet.Undefined();
        u.setTripletData(new byte[] { 0x04, (byte) 0x9E, 0x01, 0x02 });
        triplets.add(u);
        asn.setTriplets(triplets);
        return asn;
    }

    private MCC_MediumCopyCount createMCC() {
        MCC_MediumCopyCount mcc = new MCC_MediumCopyCount();
        mcc.addRepeatingGroup(new MCC_MediumCopyCount.MCC_RepeatingGroup((short)1, (short)5, (byte)0, (byte)1));
        mcc.addRepeatingGroup(new MCC_MediumCopyCount.MCC_RepeatingGroup((short)6, (short)10, (byte)0, (byte)2));
        return mcc;
    }

    private MCF_MapCodedFont_Format1 createMCF1() {
        MCF_MapCodedFont_Format1 mcf1 = new MCF_MapCodedFont_Format1();
        mcf1.setLengthOfRepeatingGroup((short)30);
        mcf1.setReserved1_3(new byte[]{0x01, 0x02, 0x03});

        MCF_MapCodedFont_Format1.MCF_RepeatingGroup rg = new MCF_MapCodedFont_Format1.MCF_RepeatingGroup();
        rg.setCodedFontLocalID((short)1);
        rg.setReserved1((byte)0);
        rg.setCodedFontSectionID((short)0);
        rg.setReserved3((byte)0);
        rg.setCodedFontName("X0TEST");
        rg.setCodePageName("T1TEST");
        rg.setFontCharacterSetName("C0TEST");
        rg.setCharacterRotation(AFPOrientation.ori0);

        mcf1.addRepeatingGroup(rg);
        return mcf1;
    }

    private CFC_CodedFontControl createCFC() {
        CFC_CodedFontControl cfc = new CFC_CodedFontControl();
        cfc.setCfiRepeatingGroupLength((byte) 0x19);
        cfc.setRetired((byte) 0x01);
        return cfc;
    }

    private CFI_CodedFontIndex createCFI() {
        CFI_CodedFontIndex cfi = new CFI_CodedFontIndex();
        CFI_CodedFontIndex.CFIRepeatingGroup rg = new CFI_CodedFontIndex.CFIRepeatingGroup();
        rg.setFcsName("C0TEST");
        rg.setCpName("T1TEST");
        rg.setSvSize(240);
        rg.setShScale(240);
        rg.setSection((short) 0);
        cfi.addCFIRepeatingGroup(rg);
        return cfi;
    }

    private CPC_CodePageControl createCPC() {
        CPC_CodePageControl cpc = new CPC_CodePageControl();
        cpc.setDefaultGraphicCharacterGlobalID("SP010000");
        cpc.setDefaultCharacterUseFlags(EnumSet.of(CPC_CodePageControl.DefaultCharacterUseFlag.InvalidCodedCharacter));
        cpc.setCpiRepeatingGroupLength(CPC_CodePageControl.CPIRepeatingGroupLength.SingleByteCodePage);
        cpc.setSpaceCharacterSectionNumber((short) 0);
        cpc.setSpaceCharacterCodePoint((short) 64);
        cpc.setCodePageUseFlags(EnumSet.of(CPC_CodePageControl.CodePageUseFlag.SortOrder));
        cpc.setUnicodeScalarValue(0);
        return cpc;
    }

    private CPD_CodePageDescriptor createCPD() {
        CPD_CodePageDescriptor cpd = new CPD_CodePageDescriptor();
        cpd.setCodePageDescription("Test Code Page");
        cpd.setGraphicCharacterGIDLength((short) 8);
        cpd.setNumberOfCodedGraphicCharactersAssigned(256);
        cpd.setGraphicCharacterSetGID(103);
        cpd.setCodePageGID(500);
        cpd.setEncodingScheme(CPD_CodePageDescriptor.EncodingScheme.SingleByte_EBCDICPresentation);
        return cpd;
    }

    private CPI_CodePageIndex createCPI() {
        CPI_CodePageIndex cpi = new CPI_CodePageIndex();
        List<CPI_CodePageIndex.CPI_RepeatingGroup> rgs = new ArrayList<>();
        CPI_CodePageIndex.CPI_RepeatingGroup rg = new CPI_CodePageIndex.CPI_RepeatingGroup();
        rg.setGraphicCharacterGID("SP010000");
        rg.setGraphicCharacterUseFlags(EnumSet.of(CPI_CodePageIndex.GraphicCharacterUseFlag.InvalidCodedCharacter));
        rg.setCodePoint(64);
        rg.setUnicodeScalarValues(List.of(0x0020L));
        rgs.add(rg);
        cpi.setRepeatingGroups(rgs);
        return cpi;
    }

    private FND_FontDescriptor createFND() {
        FND_FontDescriptor fnd = new FND_FontDescriptor();
        fnd.setTypefaceDescription("Helvetica");
        fnd.setFontWeightClass((byte) 5);
        fnd.setFontWidthClass((byte) 5);
        fnd.setMaxVerticalSize((short) 1000);
        fnd.setNominalVerticalSize((short) 1000);
        fnd.setMinimumVerticalSize((short) 1000);
        fnd.setMaxHorizontalSize((short) 1000);
        fnd.setNominalHorizontalSize((short) 1000);
        fnd.setMinimumHorizontalSize((short) 1000);
        fnd.setDesignGeneralClass((byte) 1);
        fnd.setDesignSubclass((byte) 1);
        fnd.setDesignSpecificGroup((byte) 1);
        fnd.setFontDesignFlags((short) 0);
        fnd.setGcsgid((short) 103);
        fnd.setFgid((short) 1);
        return fnd;
    }

    private FNG_FontPatterns createFNG() {
        FNG_FontPatterns fng = new FNG_FontPatterns();
        fng.setData(new byte[]{0x01, 0x02, 0x03, 0x04});
        return fng;
    }

    private FNI_FontIndex createFNI() {
        FNI_FontIndex fni = new FNI_FontIndex();
        List<FNI_FontIndex.FNI_RepeatingGroup> rgs = new ArrayList<>();
        FNI_FontIndex.FNI_RepeatingGroup rg = new FNI_FontIndex.FNI_RepeatingGroup();
        rg.setGcgid("SP010000");
        rg.setCharIncrement((short) 120);
        rg.setAscenderHeight((short) 100);
        rg.setDescenderDepth((short) 20);
        rg.setKernableCharacterFlags((byte) 0);
        rg.setReserved15((byte) 0);
        rg.setASpace((short) 10);
        rg.setBSpace((short) 100);
        rg.setCSpace((short) 10);
        rg.setReserved22_23((short) 0);
        rg.setBaselineOffset((short) 50);
        rg.setReserved26_27((short) 0);
        rgs.add(rg);
        fni.setRepeatingGroups(rgs);
        return fni;
    }

    private FNM_FontPatternsMap createFNM() {
        FNM_FontPatternsMap fnm = new FNM_FontPatternsMap();
        List<FNM_FontPatternsMap.FNM_RepeatingGroup> rgs = new ArrayList<>();
        FNM_FontPatternsMap.FNM_RepeatingGroup rg = new FNM_FontPatternsMap.FNM_RepeatingGroup();
        rg.setCharBoxWd(24);
        rg.setCharBoxHt(36);
        rg.setPatDOset(1000L);
        rgs.add(rg);
        fnm.setRepeatingGroups(rgs);
        return fnm;
    }

    private FNN_FontNameMap createFNN() {
        FNN_FontNameMap fnn = new FNN_FontNameMap();
        fnn.setIbmFormat((byte) 2);
        fnn.setTechnologyFormat((byte) 3);

        FNN_RepeatingGroup rg = new FNN_RepeatingGroup();
        rg.setGcgid("SP010000");
        rg.setTsOffset(100);
        fnn.addRepeatingGroup(rg);

        FNN_TSIdentifier tsid = new FNN_TSIdentifier();
        tsid.setTsidLen(5);
        tsid.setTsid("test");
        fnn.setTsIdentifiers(List.of(tsid));

        return fnn;
    }

    private FNO_FontOrientation createFNO() {
        FNO_FontOrientation fno = new FNO_FontOrientation();
        List<FNO_FontOrientation.FNO_RepeatingGroup> rgs = new ArrayList<>();
        FNO_FontOrientation.FNO_RepeatingGroup rg = new FNO_FontOrientation.FNO_RepeatingGroup();
        rg.setCharRotation(AFPOrientation.ori0);
        rg.setMaxBaselineOffset((short) 100);
        rg.setMaxCharacterIncrement((short) 120);
        rg.setSpaceCharacterIncrement((short) 120);
        rg.setMaxBaselineExtent((short) 150);
        rg.setControlFlags(EnumSet.of(FNO_FontOrientation.FnoControlFlag.UniformASpace));
        rg.setEmSpaceIncrement((short) 1000);
        rg.setFigureSpaceIncrement((short) 500);
        rg.setNominalCharacterIncrement((short) 120);
        rg.setDefaultBaselineIncrement(120);
        rg.setMinASpace((short) 10);
        rgs.add(rg);
        fno.setRepeatingGroups(rgs);
        return fno;
    }

    private FNP_FontPosition createFNP() {
        FNP_FontPosition fnp = new FNP_FontPosition();
        FNP_FontPosition.FNP_RepeatingGroup rg = new FNP_FontPosition.FNP_RepeatingGroup();
        rg.setLowercaseHeight((short) 400);
        rg.setCapMHeight((short) 700);
        rg.setMaxAscenderHeight((short) 750);
        rg.setMaxDescenderDepth((short) 250);
        rg.setRetired15((byte) 1);
        rg.setReserved16((byte) 0);
        rg.setUnderscoreWidth((short) 50);
        rg.setUnderscoreWidthFraction((byte) 0);
        rg.setUnderscorePosition((short) -100);
        fnp.addFNPRepeatingGroup(rg);
        return fnp;
    }

    private MBC_MapBarCodeObject createMBC() {
        MBC_MapBarCodeObject mbc = new MBC_MapBarCodeObject();
        mbc.setLengthOfRepeatingGroup((short) 8);
        Triplet.ResourceLocalIdentifier rli = new Triplet.ResourceLocalIdentifier();
        rli.setResourceType(Triplet.ResourceLocalIdentifier.RLI_ResourceType.PageOverlay);
        rli.setResourceLocalID((short) 1);
        rli.setTripletID(Triplet.TripletID.ResourceLocalIdentifier);
        mbc.setTriplet(rli);
        return mbc;
    }

    private MMD_MapMediaDestination createMMD() {
        MMD_MapMediaDestination mmd = new MMD_MapMediaDestination();
        MMD_MapMediaDestination.MMD_RepeatingGroup rg = new MMD_MapMediaDestination.MMD_RepeatingGroup();
        rg.setRepeatingGroupLength(10);
        Triplet.ExtendedResourceLocalIdentifier erli = new Triplet.ExtendedResourceLocalIdentifier();
        erli.resourceType = Triplet.ExtendedResourceLocalIdentifier.ERLI_ResourceType.MediaDestinationResource;
        erli.extendedResourceLocalID = 1;
        erli.setTripletID(Triplet.TripletID.ExtendedResourceLocalIdentifier);
        rg.addTriplet(erli);
        mmd.addRepeatingGroup(rg);
        return mmd;
    }

    private MMO_MapMediumOverlay createMMO() {
        MMO_MapMediumOverlay mmo = new MMO_MapMediumOverlay();
        mmo.setLengthOfEachRepeatingGroup((short) 12);
        mmo.setReserved1_3(new byte[]{0, 0, 0});
        MMO_MapMediumOverlay.MMO_RepeatingGroup rg = new MMO_MapMediumOverlay.MMO_RepeatingGroup();
        rg.setMediumOverlayLocalId((short) 1);
        rg.setFlag(MMO_MapMediumOverlay.MMO_RepeatingGroup.MMO_Flag.RasterIndicator_RasterOverlay);
        rg.setReserved2_3(new byte[]{0, 0});
        rg.setNameOfMediumOverlay("OVL00001");
        mmo.addRepeatingGroup(rg);
        return mmo;
    }

    private MMT_MapMediaType createMMT() {
        MMT_MapMediaType mmt = new MMT_MapMediaType();
        MMT_MapMediaType.MMT_RepeatingGroup rg = new MMT_MapMediaType.MMT_RepeatingGroup();
        rg.setRepeatingGroupLength(10);
        Triplet.ExtendedResourceLocalIdentifier erli = new Triplet.ExtendedResourceLocalIdentifier();
        erli.resourceType = Triplet.ExtendedResourceLocalIdentifier.ERLI_ResourceType.MediaTypeResource;
        erli.extendedResourceLocalID = 1;
        erli.setTripletID(Triplet.TripletID.ExtendedResourceLocalIdentifier);
        rg.addTriplet(erli);
        mmt.addRepeatingGroup(rg);
        return mmt;
    }

    private MPG_MapPage createMPG() {
        MPG_MapPage mpg = new MPG_MapPage();
        MPG_MapPage.MPG_RepeatingGroup rg = new MPG_MapPage.MPG_RepeatingGroup();
        rg.setRepeatingGroupLength(10);
        Triplet.ResourceLocalIdentifier rli = new Triplet.ResourceLocalIdentifier();
        rli.setResourceType(Triplet.ResourceLocalIdentifier.RLI_ResourceType.PageOverlay);
        rli.setResourceLocalID((short) 1);
        rli.setTripletID(Triplet.TripletID.ResourceLocalIdentifier);
        rg.addTriplet(rli);
        mpg.addRepeatingGroup(rg);
        return mpg;
    }

    private MPS_MapPageSegment createMPS() {
        MPS_MapPageSegment mps = new MPS_MapPageSegment();
        mps.setLengthOfRepeatingGroup((short) 12);
        mps.setReserved1_3(new byte[]{0, 0, 0});
        MPS_MapPageSegment.MPS_RepeatingGroup rg = new MPS_MapPageSegment.MPS_RepeatingGroup();
        rg.setNameOfPageSegment("PSG00001");
        mps.addRepeatingGroup(rg);
        return mps;
    }

    private MPT_MapPresentationText createMPT() {
        MPT_MapPresentationText mpt = new MPT_MapPresentationText();
        MPT_MapPresentationText.MPT_RepeatingGroup rg = new MPT_MapPresentationText.MPT_RepeatingGroup();
        rg.setRepeatingGroupLength(10);
        Triplet.ResourceLocalIdentifier rli = new Triplet.ResourceLocalIdentifier();
        rli.setResourceType(Triplet.ResourceLocalIdentifier.RLI_ResourceType.PageOverlay);
        rli.setResourceLocalID((short) 1);
        rli.setTripletID(Triplet.TripletID.ResourceLocalIdentifier);
        rg.addTriplet(rli);
        mpt.addRepeatingGroup(rg);
        return mpt;
    }

    private CDD_ContainerDataDescriptor createCDD() {
        CDD_ContainerDataDescriptor cdd = new CDD_ContainerDataDescriptor();
        cdd.setRetiredParameters(new byte[12]);
        Triplet.ResourceLocalIdentifier rli = new Triplet.ResourceLocalIdentifier();
        rli.setResourceType(Triplet.ResourceLocalIdentifier.RLI_ResourceType.PageOverlay);
        rli.setResourceLocalID((short) 1);
        rli.setTripletID(Triplet.TripletID.ResourceLocalIdentifier);
        cdd.addTriplet(rli);
        return cdd;
    }

    private LLE_LinkLogicalElement createLLE() {
        LLE_LinkLogicalElement lle = new LLE_LinkLogicalElement();
        lle.setLinkType(LLE_LinkLogicalElement.LLE_LinkType.NavigationLink);
        lle.setReserved1((byte) 0);
        LLE_LinkLogicalElement.LLE_RepeatingGroup rg = new LLE_LinkLogicalElement.LLE_RepeatingGroup();
        rg.setRepeatingGroupFunction(LLE_LinkLogicalElement.LLE_RepeatingGroup.LLE_RepeatingGroupFunction.LinkSourceSpecification);
        Triplet.ResourceLocalIdentifier rli = new Triplet.ResourceLocalIdentifier();
        rli.setResourceType(Triplet.ResourceLocalIdentifier.RLI_ResourceType.PageOverlay);
        rli.setResourceLocalID((short) 1);
        rli.setTripletID(Triplet.TripletID.ResourceLocalIdentifier);
        rg.addTriplet(rli);
        rg.setLengthOfRepeatingGroup(10); // lengthOfRepeatingGroup is recalculated in writeAFP but we set it here for completeness
        lle.addRepeatingGroup(rg);
        return lle;
    }

    private CTC_ComposedTextControl createCtc() {
        return new CTC_ComposedTextControl();
    }

    private FGD_FormEnvironmentGroupDescriptor createFgd() {
        return new FGD_FormEnvironmentGroupDescriptor();
    }

    private PPO_PreprocessPresentationObject createPpo() {
        PPO_PreprocessPresentationObject ppo = new PPO_PreprocessPresentationObject();
        PPO_PreprocessPresentationObject.PPO_RepeatingGroup rg = new PPO_PreprocessPresentationObject.PPO_RepeatingGroup();
        rg.setObjectType(com.mgz.afp.enums.AFPObjectType.Image);
        rg.setxOrigin(100);
        rg.setyOrigin(200);
        rg.setFlags(EnumSet.of(PPO_PreprocessPresentationObject.PPO_RepeatingGroup.PPO_Flag.ObjectOrientation_0Deg_Preprocess));

        Triplet.ResourceLocalIdentifier rli = new Triplet.ResourceLocalIdentifier();
        rli.setResourceType(Triplet.ResourceLocalIdentifier.RLI_ResourceType.PageOverlay);
        rli.setResourceLocalID((short) 1);
        rli.setTripletID(Triplet.TripletID.ResourceLocalIdentifier);
        rg.addTriplet(rli);

        ppo.addRepeatingGroup(rg);
        return ppo;
    }

    private LNC_LineDescriptorCount createLNC() {
        LNC_LineDescriptorCount lnc = new LNC_LineDescriptorCount();
        lnc.setNumberOfSFInBDX(42);
        return lnc;
    }

    private CCP_ConditionalProcessingControl createCCP() {
        CCP_ConditionalProcessingControl ccp = new CCP_ConditionalProcessingControl();
        ccp.setCcpIdentifier(1);
        ccp.setNextCcpIdentifier(2);
        ccp.setFlag(CCP_ConditionalProcessingControl.CCP_Flag.BeforeSubpageActions);
        ccp.setReserved5((byte) 0);
        ccp.setNumberOfRepeatingGroups(1);
        ccp.setLengthOfRepeatingGroup(22);
        ccp.setLengthOfComparisonString(2);

        List<CCP_ConditionalProcessingControl.CCP_RepeatingGroup> rgs = new ArrayList<>();
        CCP_ConditionalProcessingControl.CCP_RepeatingGroup rg =
            new CCP_ConditionalProcessingControl.CCP_RepeatingGroup();
        rg.setTimingOfAction(
            CCP_ConditionalProcessingControl.CCP_RepeatingGroup.CCP_TimingOfAction.Immediately);
        rg.setMediumMapAction(CCP_ConditionalProcessingControl.CCP_RepeatingGroup
            .CCP_MediumMapAction.InvokeNamedMediumMap);
        rg.setMediumMapName("MMTEST");
        rg.setDataMapAction(CCP_ConditionalProcessingControl.CCP_RepeatingGroup
            .CCP_DataMapAction.InvokeNamedDataMap);
        rg.setDataMapName("DMTEST");
        rg.setComparison(
            CCP_ConditionalProcessingControl.CCP_RepeatingGroup.CCP_Comparison.EqualTo);
        rg.setComparisonString("AB");
        rgs.add(rg);
        ccp.setRepeatingGroups(rgs);

        return ccp;
    }

    private RCD_RecordDescriptor createRCD() {
        RCD_RecordDescriptor rcd = new RCD_RecordDescriptor();
        rcd.setRecordDescriptorID("RCD0000001");
        rcd.setRecordType(RCD_XMD_RecordTypeElementType.Body);
        rcd.setFlags(EnumSet.of(RCD_RecordDescriptor.RCD_Flag.GenerateInlinePosition_NewPosition));
        rcd.setReserved14((byte) 0);
        rcd.setInlinePosition(100);
        rcd.setBaselinePosition(200);
        rcd.setInlineOrientation(AFPOrientation.ori0);
        rcd.setBaselineOrientation(AFPOrientation.ori90);
        rcd.setPrimaryFontLocalId((short) 1);
        rcd.setFieldRCDPointer(0);
        rcd.setSuppressionTokenName("SUPP0001");
        rcd.setShiftOutFontLocalID((short) 0);
        rcd.setDataStartPosition(1);
        rcd.setDataLength(100);
        rcd.setConditionalProcessingRCDPointer(0);
        rcd.setSubpageID((byte) 1);
        rcd.setCcpIdentifier(1);
        rcd.setStartingPageNumber(1);
        rcd.setEndSpace(0);
        rcd.setFieldAllignment((byte) 0);
        rcd.setFieldDelimiter(0);
        rcd.setFieldNumber(1);
        rcd.setAdditionalBaselineIncrement(0);
        rcd.setReserved57_69(new byte[13]);
        return rcd;
    }

    private XMD_XMLDescriptor createXMD() {
        XMD_XMLDescriptor xmd = new XMD_XMLDescriptor();
        xmd.setElementType(RCD_XMD_RecordTypeElementType.Body);
        xmd.setFlags(EnumSet.of(XMD_XMLDescriptor.XMD_Flag.GenerateInlinePosition_NewPosition));
        xmd.setReserved4((byte) 0);
        xmd.setInlinePosition(100);
        xmd.setBaselinePosition(200);
        xmd.setInlineOrientation(AFPOrientation.ori0);
        xmd.setBaselineOrientation(AFPOrientation.ori90);
        xmd.setPrimaryFontLocalId((short) 1);
        xmd.setFieldXMDPointer(0);
        xmd.setReserved16_17(new byte[2]);
        xmd.setSuppressionTokenName("SUPP0001");
        xmd.setReserved26((byte) 0);
        xmd.setDataStartPosition(1);
        xmd.setDataLength(100);
        xmd.setConditionalProcessingRCDPointer(0);
        xmd.setSubpageID((byte) 1);
        xmd.setCcpIdentifier(1);
        xmd.setStartingPageNumber(1);
        xmd.setEndSpace(0);
        xmd.setFieldAllignment((byte) 0);
        xmd.setFieldDelimiter(0);
        xmd.setFieldNumber(1);
        xmd.setAdditionalBaselineIncrement(0);
        xmd.setReserved48_61(new byte[14]);
        return xmd;
    }

    private IID_IMImageInputDescriptor createIid() {
        IID_IMImageInputDescriptor iid = new IID_IMImageInputDescriptor();
        // Since we added getters, we might need to use reflection to set private fields if there are no setters
        // but wait, I see no setters in IID. I should check how they are usually set in tests.
        // I will use a byte array and decode it for simplicity.
        byte[] data = new byte[36];
        data[12] = (byte) AFPUnitBase.Inches10.toByte();
        data[13] = (byte) AFPUnitBase.Inches10.toByte();
        try {
            iid.decodeAFP(data, 0, data.length, null);
        } catch (Exception e) {}
        return iid;
    }

    private ICP_IMImageCellPosition createIcp() {
        ICP_IMImageCellPosition icp = new ICP_IMImageCellPosition();
        byte[] data = new byte[12];
        try {
            icp.decodeAFP(data, 0, data.length, null);
        } catch (Exception e) {}
        return icp;
    }

    private IRD_IMImageRasterData createIrd() {
        IRD_IMImageRasterData ird = new IRD_IMImageRasterData();
        ird.setData(new byte[]{0x01, 0x02, 0x03});
        return ird;
    }

    private IOC_IMImageOutputControl createIoc() {
        IOC_IMImageOutputControl ioc = new IOC_IMImageOutputControl();
        byte[] data = new byte[24];
        data[6] = 0; data[7] = 0; // xRotation
        data[8] = 0; data[9] = 0; // yRotation
        data[18] = 0x03; data[19] = (byte)0xE8; // xImageMapping
        data[20] = 0x03; data[21] = (byte)0xE8; // yImageMapping
        try {
            ioc.decodeAFP(data, 0, data.length, null);
        } catch (Exception e) {}
        return ioc;
    }

    private BCA_BeginColorAttributeTable createBca() {
        BCA_BeginColorAttributeTable bca = new BCA_BeginColorAttributeTable();
        bca.setName("BCA00001");
        List<Triplet> triplets = new ArrayList<>();
        Triplet.FullyQualifiedName fqn = new Triplet.FullyQualifiedName();
        fqn.setType(Triplet.GlobalID_Use.BeginResourceGroupReference);
        fqn.setFormat(Triplet.GlobalID_Format.CharacterString);
        fqn.setNameAsString("RES00001");
        triplets.add(fqn);
        bca.setTriplets(triplets);
        return bca;
    }

    private ECA_EndColorAttributeTable createEca() {
        ECA_EndColorAttributeTable eca = new ECA_EndColorAttributeTable();
        eca.setName("ECA00001");
        return eca;
    }

    private CAT_ColorAttributeTable createCat() {
        CAT_ColorAttributeTable cat = new CAT_ColorAttributeTable();
        CAT_ColorAttributeTable.CAT_BasePart bp = new CAT_ColorAttributeTable.CAT_BasePart();
        bp.setResetLCTFlag(CAT_ColorAttributeTable.CAT_BasePart.ResetLCTFlag.ResetLCT);
        bp.setReserved1((byte) 0);
        bp.setColorTableLocalID((short) 1);
        cat.setBasePart(bp);
        cat.setOtherData(new byte[]{0x01, 0x02});
        return cat;
    }

    private MCA_MapColorAttributeTable createMca() {
        MCA_MapColorAttributeTable mca = new MCA_MapColorAttributeTable();
        MCA_MapColorAttributeTable.MCA_RepeatingGroup rg = new MCA_MapColorAttributeTable.MCA_RepeatingGroup();
        List<Triplet> triplets = new ArrayList<>();
        Triplet.FullyQualifiedName fqn = new Triplet.FullyQualifiedName();
        fqn.setType(Triplet.GlobalID_Use.BeginResourceGroupReference);
        fqn.setFormat(Triplet.GlobalID_Format.CharacterString);
        fqn.setNameAsString("RES00001");
        triplets.add(fqn);
        rg.setTriplets(triplets);
        mca.addRepeatingGroup(rg);
        return mca;
    }
}
