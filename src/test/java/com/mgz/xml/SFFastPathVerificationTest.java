package com.mgz.xml;

import com.mgz.afp.modca.CDD_ContainerDataDescriptor;
import com.mgz.afp.modca.CTC_ComposedTextControl;
import com.mgz.afp.modca.FGD_FormEnvironmentGroupDescriptor;
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

    private void verifySF(com.mgz.afp.base.StructuredField sf, String rootName) throws Exception {
        ByteArrayOutputStream baosFast = new ByteArrayOutputStream();
        try (AfpJacksonXmlWriter writer = new AfpJacksonXmlWriter(baosFast, null, true, false)) {
            writer.handle(sf);
        }
        String fastPathXml = baosFast.toString(StandardCharsets.UTF_8);

        String jacksonXml = JacksonXmlMapperProvider.getFragmentMapper().writeValueAsString(sf);

        String normalizedJackson = normalizeXml(jacksonXml);
        String normalizedFastPath = normalizeXml(fastPathXml);

        if (!normalizedFastPath.contains(normalizedJackson)) {
            System.out.println("RootName: " + rootName);
            System.out.println("Jackson (Normalized): " + normalizedJackson);
            System.out.println("FastPath (Normalized): " + normalizedFastPath);
        }
        assertTrue(normalizedFastPath.contains(normalizedJackson), "Normalized FastPath XML does not contain expected Normalized Jackson XML for " + rootName);
    }

    private String normalizeXml(String xml) {
        // Remove metadata tags which are in Jackson but not in our fast-path
        return xml.replaceAll("<beginSF>.*?</beginSF>", "")
                  .replaceAll("<endSF>.*?</endSF>", "")
                  .replaceAll("<shallow>.*?</shallow>", "")
                  .replaceAll("<structuredFieldIntroducer>.*?</structuredFieldIntroducer>", "")
                  .replaceAll("<padding>.*?</padding>", "")
                  .replaceAll(" (page|x|y)=\"(-?\\d+)\"", "")
                  .replaceAll("\\s", ""); // Remove whitespace for comparison
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
        rg.setKernableCharacterFlags((short) 0);
        rg.setReserved15((byte) 0);
        rg.setASpace((short) 10);
        rg.setBSpace((short) 100);
        rg.setCSpace((short) 10);
        rg.setBaselineOffset((short) 50);
        rgs.add(rg);
        fni.setRepeatingGroups(rgs);
        return fni;
    }

    private FNM_FontPatternsMap createFNM() {
        FNM_FontPatternsMap fnm = new FNM_FontPatternsMap();
        List<FNM_FontPatternsMap.FNM_RepeatingGroup> rgs = new ArrayList<>();
        FNM_FontPatternsMap.FNM_RepeatingGroup rg = new FNM_FontPatternsMap.FNM_RepeatingGroup();
        rg.setCharDataOffset(1000);
        rg.setCharDataCount(500);
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
