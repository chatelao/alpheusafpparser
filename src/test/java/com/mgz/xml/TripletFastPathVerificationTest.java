package com.mgz.xml;

import com.mgz.afp.triplets.Triplet;
import com.mgz.afp.enums.AFPUnitBase;
import com.mgz.afp.enums.AFPOrientation;
import com.mgz.afp.triplets.Triplet.RenderingIntent.Intent;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.nio.charset.StandardCharsets;
import java.util.EnumSet;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class TripletFastPathVerificationTest {

    @Test
    public void testTripletFastPaths() throws Exception {
        // Original 16
        verifyTriplet(createMODCAFunctionSet(), "MODCAFunctionSet");
        verifyTriplet(createFontCodedGraphicCharacterSetGlobalID(), "FontCodedGraphicCharacterSetGlobalID");
        verifyTriplet(createExtendedResourceLocalIdentifier(), "ExtendedResourceLocalIdentifier");
        verifyTriplet(createResourceSectionNumber(), "ResourceSectionNumber");
        verifyTriplet(createMediumMapPageNumber(), "MediumMapPageNumber");
        verifyTriplet(createObjectByteExtent(), "ObjectByteExtent");
        verifyTriplet(createObjectStructuredFieldOffset(), "ObjectStructuredFieldOffset");
        verifyTriplet(createObjectStructuredFieldExtent(), "ObjectStructuredFieldExtent");
        verifyTriplet(createObjectOffset(), "ObjectOffset");
        verifyTriplet(createFontHorizontalScaleFactor(), "FontHorizontalScaleFactor");
        verifyTriplet(createMediumOrientation(), "MediumOrientation");
        verifyTriplet(createTonerSaver(), "TonerSaver");
        verifyTriplet(createPresentationControl(), "PresentationControl");
        verifyTriplet(createFontResolutionAndMetricTechnology(), "FontResolutionAndMetricTechnology");
        verifyTriplet(createRenderingIntent(), "RenderingIntent");
        verifyTriplet(createImageResolution(), "ImageResolution");

        // New 12
        verifyTriplet(createColorManagementResourceDescriptor(), "ColorManagementResourceDescriptor");
        verifyTriplet(createFinishingOperation(), "FinishingOperation");
        verifyTriplet(createColorFidelity(), "ColorFidelity");
        verifyTriplet(createFontFidelity(), "FontFidelity");
        verifyTriplet(createTextFidelity(), "TextFidelity");
        verifyTriplet(createMediaFidelity(), "MediaFidelity");
        verifyTriplet(createFinishingFidelity(), "FinishingFidelity");
        verifyTriplet(createCMRTagFidelity(), "CMRTagFidelity");
        verifyTriplet(createPresentationSpaceResetMixing(), "PresentationSpaceResetMixing");
        verifyTriplet(createPresentationSpaceMixingRule(), "PresentationSpaceMixingRule");
        verifyTriplet(createObjectContainerPresentationSpaceSize(), "ObjectContainerPresentationSpaceSize");
        verifyTriplet(createDeviceAppearance(), "DeviceAppearance");

        // Remaining 18
        verifyTriplet(createObjectFunctionSetSpecification_Retired(), "ObjectFunctionSetSpecification_Retired");
        verifyTriplet(createDescriptorPosition(), "DescriptorPosition");
        verifyTriplet(createMediaEjectControl(), "MediaEjectControl");
        verifyTriplet(createPageOverlayConditionalProcessing(), "PageOverlayConditionalProcessing");
        verifyTriplet(createResourceUsageAttribute(), "ResourceUsageAttribute");
        verifyTriplet(createObjectChecksum(), "ObjectChecksum");
        verifyTriplet(createObjectOriginIdentifier(), "ObjectOriginIdentifier");
        verifyTriplet(createIMMInsertionTriplet(), "IMMInsertionTriplet");
        verifyTriplet(createTextOrientation(), "TextOrientation");
        verifyTriplet(createLineDataObjectPositionMigration(), "LineDataObjectPositionMigration");
        verifyTriplet(createResourceObjectInclude(), "ResourceObjectInclude");
        verifyTriplet(createPagePositionInformation(), "PagePositionInformation");
        verifyTriplet(createParameterValue(), "ParameterValue");
        verifyTriplet(createDataObjectFontDescriptor(), "DataObjectFontDescriptor");
        verifyTriplet(createLocaleSelector(), "LocaleSelector");
        verifyTriplet(createUP3iFinishingOperation(), "UP3iFinishingOperation");
        verifyTriplet(createKeepGroupTogether(), "KeepGroupTogether");
        verifyTriplet(createSetupName(), "SetupName");
    }

    private void verifyTriplet(Triplet triplet, String rootName) throws Exception {
        ByteArrayOutputStream baosFast = new ByteArrayOutputStream();
        try (AfpJacksonXmlWriter writer = new AfpJacksonXmlWriter(baosFast, null, true, false)) {
            com.mgz.afp.modca.TLE_TagLogicalElement tle = new com.mgz.afp.modca.TLE_TagLogicalElement();
            java.util.List<Triplet> triplets = new java.util.ArrayList<>();
            triplets.add(triplet);
            tle.setTriplets(triplets);
            writer.handle(tle);
        }
        String fastPathXml = baosFast.toString(StandardCharsets.UTF_8);

        String jacksonXml = JacksonXmlMapperProvider.getFragmentMapper().writeValueAsString(triplet);

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
        // Remove length and tripletID tags which are in Jackson but not in our "clean" fast-path
        return xml.replaceAll("<length>.*?</length>", "")
                  .replaceAll("<tripletID>.*?</tripletID>", "")
                  .replaceAll("\\s", ""); // Remove whitespace for comparison
    }

    private Triplet.MODCAFunctionSet createMODCAFunctionSet() {
        Triplet.MODCAFunctionSet mfs = new Triplet.MODCAFunctionSet();
        mfs.reserved2_3 = new byte[]{0x01, 0x02};
        mfs.fctSetID = 1234;
        return mfs;
    }

    private Triplet.FontCodedGraphicCharacterSetGlobalID createFontCodedGraphicCharacterSetGlobalID() {
        Triplet.FontCodedGraphicCharacterSetGlobalID fcgcs = new Triplet.FontCodedGraphicCharacterSetGlobalID();
        fcgcs.codedGraphicCharacterSetGlobalID = 567;
        fcgcs.codePageGlobalID = 890;
        return fcgcs;
    }

    private Triplet.ExtendedResourceLocalIdentifier createExtendedResourceLocalIdentifier() {
        Triplet.ExtendedResourceLocalIdentifier erli = new Triplet.ExtendedResourceLocalIdentifier();
        erli.resourceType = Triplet.ExtendedResourceLocalIdentifier.ERLI_ResourceType.MediaTypeResource;
        erli.extendedResourceLocalID = 0x12345678L;
        return erli;
    }

    private Triplet.ResourceSectionNumber createResourceSectionNumber() {
        Triplet.ResourceSectionNumber rsn = new Triplet.ResourceSectionNumber();
        rsn.resourceSectionNumber = 42;
        return rsn;
    }

    private Triplet.MediumMapPageNumber createMediumMapPageNumber() {
        Triplet.MediumMapPageNumber mmpn = new Triplet.MediumMapPageNumber();
        mmpn.pageNumber = 101;
        return mmpn;
    }

    private Triplet.ObjectByteExtent createObjectByteExtent() {
        Triplet.ObjectByteExtent obe = new Triplet.ObjectByteExtent();
        obe.byteExtentLow = 1000L;
        obe.byteExtentHigh = 2000L;
        return obe;
    }

    private Triplet.ObjectStructuredFieldOffset createObjectStructuredFieldOffset() {
        Triplet.ObjectStructuredFieldOffset osfo = new Triplet.ObjectStructuredFieldOffset();
        osfo.offsetLow = 3000L;
        osfo.offsetHigh = 4000L;
        return osfo;
    }

    private Triplet.ObjectStructuredFieldExtent createObjectStructuredFieldExtent() {
        Triplet.ObjectStructuredFieldExtent osfe = new Triplet.ObjectStructuredFieldExtent();
        osfe.numberOfSFLow = 50L;
        osfe.numberOfSFHigh = 60L;
        return osfe;
    }

    private Triplet.ObjectOffset createObjectOffset() {
        Triplet.ObjectOffset oo = new Triplet.ObjectOffset();
        oo.objectType = Triplet.ObjectOffset.ObjectType.Document;
        oo.reserved3 = 0x07;
        oo.nrOfPrecedingObjectsLow = 70L;
        oo.nrOfPrecedingObjectsHigh = 80L;
        return oo;
    }

    private Triplet.FontHorizontalScaleFactor createFontHorizontalScaleFactor() {
        Triplet.FontHorizontalScaleFactor fhsf = new Triplet.FontHorizontalScaleFactor();
        fhsf.horizontalScaleFactor = 1000;
        return fhsf;
    }

    private Triplet.MediumOrientation createMediumOrientation() {
        Triplet.MediumOrientation mo = new Triplet.MediumOrientation();
        mo.mediumOrientation = Triplet.MediumOrientation.MediumOrientationValue.Landscape;
        return mo;
    }

    private Triplet.TonerSaver createTonerSaver() {
        Triplet.TonerSaver ts = new Triplet.TonerSaver();
        ts.reserved2 = 0x00;
        ts.tonerSaverFunction = Triplet.TonerSaver.TonerSaverFunction.ActivateTonerSaver;
        ts.reserved4_5 = new byte[]{0x00, 0x00};
        return ts;
    }

    private Triplet.PresentationControl createPresentationControl() {
        Triplet.PresentationControl pc = new Triplet.PresentationControl();
        pc.setPresentationControlFlags(EnumSet.of(Triplet.PresentationControl.PresentationControlFlags.ViewControl_DoNotView));
        return pc;
    }

    private Triplet.FontResolutionAndMetricTechnology createFontResolutionAndMetricTechnology() {
        Triplet.FontResolutionAndMetricTechnology framt = new Triplet.FontResolutionAndMetricTechnology();
        framt.metricTechnology = Triplet.FontResolutionAndMetricTechnology.MetricTechnology.Fixed;
        framt.unitBase = AFPUnitBase.Inches10;
        framt.unitsPerUnitBase = 2400;
        return framt;
    }

    private Triplet.RenderingIntent createRenderingIntent() {
        Triplet.RenderingIntent ri = new Triplet.RenderingIntent();
        ri.reserved2_3 = new byte[]{0x00, 0x00};
        ri.intentForIOCA = Intent.Perceptual;
        ri.intentForContainerNonIOCA = Intent.Saturation;
        ri.intentForPTOCA = Intent.MediaRelativeColorimetric;
        ri.intentForGOCA = Intent.iccAbsoluteColorimetric;
        ri.reserved8_9 = new byte[]{0x00, 0x00};
        return ri;
    }

    private Triplet.ImageResolution createImageResolution() {
        Triplet.ImageResolution ir = new Triplet.ImageResolution();
        ir.reserved2_3 = new byte[]{0x00, 0x00};
        ir.xUnitBase = AFPUnitBase.Inches10;
        ir.yUnitBase = AFPUnitBase.Inches10;
        ir.xUnitsPerUnitBase = 600;
        ir.yUnitsPerUnitBase = 600;
        return ir;
    }

    private Triplet.ColorManagementResourceDescriptor createColorManagementResourceDescriptor() {
        Triplet.ColorManagementResourceDescriptor cmrd = new Triplet.ColorManagementResourceDescriptor();
        cmrd.reserved2 = 0x00;
        cmrd.cmrProcessingMode = Triplet.ColorManagementResourceDescriptor.CMRProcessingMode.AuditCMR;
        cmrd.cmrScope = Triplet.ColorManagementResourceDescriptor.CMRScope.Document;
        return cmrd;
    }

    private Triplet.FinishingOperation createFinishingOperation() {
        Triplet.FinishingOperation fo = new Triplet.FinishingOperation();
        fo.operationType = Triplet.FinishingOperation.OperationType.CornerStaple;
        fo.reserved3_4 = new byte[]{0x00, 0x00};
        fo.referenceCorner = Triplet.FinishingOperation.ReferenceCorner.TopLeftCorner_TopEdge;
        fo.operationCount = 1;
        fo.offsetOfOperation = 100;
        List<Short> positions = new ArrayList<>();
        positions.add((short) 10);
        positions.add((short) 20);
        fo.positions = positions;
        return fo;
    }

    private Triplet.ColorFidelity createColorFidelity() {
        Triplet.ColorFidelity cf = new Triplet.ColorFidelity();
        cf.exceptionContinuationRule = Triplet.ColorFidelity.ExceptionContinuationRule.Stop;
        cf.reserved3 = 0x00;
        cf.exceptionReportingRule = Triplet.ColorFidelity.ExceptionReportingRule.Report;
        cf.reserved5 = 0x00;
        cf.exceptionSubstitutionRule = Triplet.ColorFidelity.ExceptionSubstitutionRule.AnySubstitution_Default;
        cf.reserved7 = 0x00;
        return cf;
    }

    private Triplet.FontFidelity createFontFidelity() {
        Triplet.FontFidelity ff = new Triplet.FontFidelity();
        ff.exceptionContinuationRule = Triplet.ColorFidelity.ExceptionContinuationRule.DoNotStop;
        ff.reserved3_6 = new byte[]{0x00, 0x01, 0x02, 0x03};
        return ff;
    }

    private Triplet.TextFidelity createTextFidelity() {
        Triplet.TextFidelity tf = new Triplet.TextFidelity();
        tf.exceptionContinuationRule = Triplet.ColorFidelity.ExceptionContinuationRule.Stop;
        tf.reserved3 = 0x00;
        tf.exceptionReportingRule = Triplet.ColorFidelity.ExceptionReportingRule.DoNotReport;
        tf.reserved5_6 = new byte[]{0x00, 0x00};
        return tf;
    }

    private Triplet.MediaFidelity createMediaFidelity() {
        Triplet.MediaFidelity mf = new Triplet.MediaFidelity();
        mf.exceptionContinuationRule = Triplet.ColorFidelity.ExceptionContinuationRule.DoNotStop;
        mf.reserved3 = 0x00;
        mf.exceptionReportingRule = Triplet.ColorFidelity.ExceptionReportingRule.Report;
        mf.reserved5_6 = new byte[]{0x00, 0x00};
        return mf;
    }

    private Triplet.FinishingFidelity createFinishingFidelity() {
        Triplet.FinishingFidelity ff = new Triplet.FinishingFidelity();
        ff.exceptionContinuationRule = Triplet.ColorFidelity.ExceptionContinuationRule.Stop;
        ff.reserved3 = 0x00;
        ff.exceptionReportingRule = Triplet.ColorFidelity.ExceptionReportingRule.DoNotReport;
        ff.reserved5_6 = new byte[]{0x00, 0x00};
        return ff;
    }

    private Triplet.CMRTagFidelity createCMRTagFidelity() {
        Triplet.CMRTagFidelity ctf = new Triplet.CMRTagFidelity();
        ctf.exceptionContinuationRule = Triplet.ColorFidelity.ExceptionContinuationRule.DoNotStop;
        ctf.reserved3 = 0x00;
        ctf.exceptionReportingRule = Triplet.ColorFidelity.ExceptionReportingRule.Report;
        ctf.reserved5_6 = new byte[]{0x00, 0x00};
        return ctf;
    }

    private Triplet.PresentationSpaceResetMixing createPresentationSpaceResetMixing() {
        Triplet.PresentationSpaceResetMixing psrm = new Triplet.PresentationSpaceResetMixing();
        psrm.backgroundMixingFlag = Triplet.PresentationSpaceResetMixing.BackgroundMixingFlag.ResetColor;
        return psrm;
    }

    private Triplet.PresentationSpaceMixingRule createPresentationSpaceMixingRule() {
        Triplet.PresentationSpaceMixingRule psmr = new Triplet.PresentationSpaceMixingRule();
        Triplet.PresentationSpaceMixingRule.MixingKeywordAndRule mr = new Triplet.PresentationSpaceMixingRule.MixingKeywordAndRule();
        mr.setKeyword(Triplet.PresentationSpaceMixingRule.MixingKeyword.BackgroundOnForeground);
        mr.setRule(Triplet.PresentationSpaceMixingRule.MixingRule.Blend);
        psmr.addMixingRule(mr);
        return psmr;
    }

    private Triplet.ObjectContainerPresentationSpaceSize createObjectContainerPresentationSpaceSize() {
        Triplet.ObjectContainerPresentationSpaceSize ocpss = new Triplet.ObjectContainerPresentationSpaceSize();
        ocpss.reserved2_3 = new byte[]{0x00, 0x00};
        ocpss.pdfPresentationSpace = Triplet.ObjectContainerPresentationSpaceSize.PDFPresentationSpace.ArtBox;
        return ocpss;
    }

    private Triplet.DeviceAppearance createDeviceAppearance() {
        Triplet.DeviceAppearance da = new Triplet.DeviceAppearance();
        da.reserved2 = 0x00;
        da.appearance = Triplet.DeviceAppearance.Appearance.DeviceDefaultMonochrome;
        da.reserved5_6 = new byte[]{0x00, 0x00};
        return da;
    }

    private Triplet.ObjectFunctionSetSpecification_Retired createObjectFunctionSetSpecification_Retired() {
        Triplet.ObjectFunctionSetSpecification_Retired ofss = new Triplet.ObjectFunctionSetSpecification_Retired();
        ofss.objectType = Triplet.ResourceObjectType.ROT_ObjectType.PresentationText;
        ofss.ocaArchitectureLevel = 1;
        ofss.modcaFunctionSetIdentifier = 0x01;
        ofss.ocaFunctionSet = Triplet.ObjectFunctionSetSpecification_Retired.OCAFunctionSet.PTOCA_PT1_or_BCOCA_BCD1;
        ofss.reserved = new byte[]{0x00};
        return ofss;
    }

    private Triplet.DescriptorPosition createDescriptorPosition() {
        Triplet.DescriptorPosition dp = new Triplet.DescriptorPosition();
        dp.objectAreaDescriptorID = 1;
        return dp;
    }

    private Triplet.MediaEjectControl createMediaEjectControl() {
        Triplet.MediaEjectControl mec = new Triplet.MediaEjectControl();
        mec.reserved2 = 0x00;
        mec.mediaEjectControl = Triplet.MediaEjectControl.MediaEjectControlType.EjectToNewSheet;
        return mec;
    }

    private Triplet.PageOverlayConditionalProcessing createPageOverlayConditionalProcessing() {
        Triplet.PageOverlayConditionalProcessing pocp = new Triplet.PageOverlayConditionalProcessing();
        pocp.pageOverlayType = Triplet.PageOverlayConditionalProcessing.PageOverlayType.Normal;
        pocp.levelOfOverlay = 1;
        return pocp;
    }

    private Triplet.ResourceUsageAttribute createResourceUsageAttribute() {
        Triplet.ResourceUsageAttribute rua = new Triplet.ResourceUsageAttribute();
        rua.frequencyOfUse = Triplet.ResourceUsageAttribute.FrequencyOfUse.High;
        return rua;
    }

    private Triplet.ObjectChecksum createObjectChecksum() {
        Triplet.ObjectChecksum oc = new Triplet.ObjectChecksum();
        oc.checksumFormat = Triplet.ObjectChecksum.CheckSumFormat.ObjectCycleRedundancyCheck;
        oc.crcCheckSum = 0x1234;
        oc.objectCheckSumFlags = EnumSet.of(Triplet.ObjectChecksum.ChecksumFlag.UsageScope_PrivateLimited);
        return oc;
    }

    private Triplet.ObjectOriginIdentifier createObjectOriginIdentifier() {
        Triplet.ObjectOriginIdentifier ooi = new Triplet.ObjectOriginIdentifier();
        ooi.originationSystem = Triplet.ObjectOriginIdentifier.AFPSystem.MVS;
        ooi.systemIDSerialNumber = "12345678";
        ooi.storageMediaID = "ABCDEF";
        ooi.dataSetID = "DATA.SET.NAME";
        return ooi;
    }

    private Triplet.IMMInsertionTriplet createIMMInsertionTriplet() {
        Triplet.IMMInsertionTriplet imm = new Triplet.IMMInsertionTriplet();
        imm.reserved2_3 = new byte[]{0x00, 0x00};
        return imm;
    }

    private Triplet.TextOrientation createTextOrientation() {
        Triplet.TextOrientation to = new Triplet.TextOrientation();
        to.xOrientation = AFPOrientation.ori0;
        to.yOrientation = AFPOrientation.ori90;
        return to;
    }

    private Triplet.LineDataObjectPositionMigration createLineDataObjectPositionMigration() {
        Triplet.LineDataObjectPositionMigration ldopm = new Triplet.LineDataObjectPositionMigration();
        ldopm.locationAndOrientation = Triplet.LineDataObjectPositionMigration.LocationAndOrientation.Standard_0;
        return ldopm;
    }

    private Triplet.ResourceObjectInclude createResourceObjectInclude() {
        Triplet.ResourceObjectInclude roi = new Triplet.ResourceObjectInclude();
        roi.objectType = 0xDF;
        roi.objectName = "RESNAME";
        roi.xOrigin = 100;
        roi.yOrigin = 200;
        roi.orientation = AFPOrientation.ori0;
        return roi;
    }

    private Triplet.PagePositionInformation createPagePositionInformation() {
        Triplet.PagePositionInformation ppi = new Triplet.PagePositionInformation();
        ppi.repeatingGroupNumber = 1;
        return ppi;
    }

    private Triplet.ParameterValue createParameterValue() {
        Triplet.ParameterValue pv = new Triplet.ParameterValue();
        pv.reserved2 = 0x00;
        pv.parameterSyntax = Triplet.ParameterValue.ParameterSyntax.CharacterString;
        pv.parameterValue = "PARAMVAL".getBytes(StandardCharsets.US_ASCII);
        return pv;
    }

    private Triplet.DataObjectFontDescriptor createDataObjectFontDescriptor() {
        Triplet.DataObjectFontDescriptor dofd = new Triplet.DataObjectFontDescriptor();
        dofd.fontInformationFlags = EnumSet.of(Triplet.DataObjectFontDescriptor.FontInformationFlag.MICR_MICR);
        dofd.fontTechnology = 1;
        dofd.specifiedVerticalFontSize = 120;
        dofd.horizontalScaleFactor = 1000;
        dofd.characterOrientation = AFPOrientation.ori0;
        dofd.encodingEnvironment = 1;
        dofd.encodingIdentifier = 1;
        dofd.reserved14_15 = new byte[]{0x00, 0x00};
        return dofd;
    }

    private Triplet.LocaleSelector createLocaleSelector() {
        Triplet.LocaleSelector ls = new Triplet.LocaleSelector();
        ls.reserved2 = 0x00;
        ls.flags = EnumSet.of(Triplet.LocaleSelector.LocalSelectorFlag.LanguageCode_TwoBytes);
        ls.languageCode = "en";
        ls.scriptCode = "Latn";
        ls.regionCode = "US";
        ls.reserved28_35 = new byte[8];
        ls.variantCode = "VAR";
        return ls;
    }

    private Triplet.UP3iFinishingOperation createUP3iFinishingOperation() {
        Triplet.UP3iFinishingOperation up3i = new Triplet.UP3iFinishingOperation();
        up3i.sequenceNumber = 1;
        up3i.reserved3 = 0x00;
        up3i.up3iData = new byte[]{0x01, 0x02, 0x03};
        return up3i;
    }

    private Triplet.KeepGroupTogether createKeepGroupTogether() {
        Triplet.KeepGroupTogether kgt = new Triplet.KeepGroupTogether();
        kgt.reserved2_3 = new byte[]{0x00, 0x00};
        kgt.grpFnct = 1;
        return kgt;
    }

    private Triplet.SetupName createSetupName() {
        Triplet.SetupName sn = new Triplet.SetupName();
        sn.reserved2_3 = new byte[]{0x00, 0x00};
        sn.setupName = "SETUP";
        return sn;
    }
}
