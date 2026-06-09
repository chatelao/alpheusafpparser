package com.mgz.xml;

import com.mgz.afp.modca.MCC_MediumCopyCount;
import com.mgz.afp.modca.MCF_MapCodedFont_Format1;
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
import com.mgz.afp.enums.AFPOrientation;
import com.mgz.afp.base.IRepeatingGroup;
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
}
