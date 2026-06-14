package com.mgz.xml;

import com.mgz.afp.triplets.Triplet;
import com.mgz.afp.ptoca.controlSequence.PTOCAControlSequence;
import com.mgz.afp.modca.PGP_PagePosition_Format1;
import com.mgz.afp.ptoca.PTD_PresentationTextDataDescriptor_Format1;
import com.mgz.afp.enums.AFPColorSpace;
import com.mgz.afp.enums.AFPOrientation;
import com.mgz.afp.enums.AFPUnitBase;
import com.mgz.afp.enums.AFPColorValue;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class XmlTemplateVerificationTest {

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
        oas.decodeAFP(new byte[] {0x09, 0x4C, 0x02, 0x00, 0x04, (byte)0xB0, 0x00, 0x06, (byte)0x40}, 0, 9, null);
        verifySF(oas, "ObjectAreaSize");

        // CodedGraphicCharacterSetGlobalID
        Triplet.CodedGraphicCharacterSetGlobalID cgcs = new Triplet.CodedGraphicCharacterSetGlobalID();
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
        Triplet.ExtendedResourceLocalIdentifier erli = new Triplet.ExtendedResourceLocalIdentifier();
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
        obe.decodeAFP(new byte[] {0x0A, 0x57, 0x00, 0x00, 0x00, 0x01, 0x00, 0x00, 0x00, 0x02}, 0, 10, null);
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
        Triplet.FontResolutionAndMetricTechnology framt = new Triplet.FontResolutionAndMetricTechnology();
        framt.decodeAFP(new byte[] {0x06, (byte) 0x84, 0x01, 0x00, 0x00, (byte) 0xF0}, 0, 6, null);
        verifySF(framt, "FontResolutionAndMetricTechnology");

        // CMRD - ColorManagementResourceDescriptor
        Triplet.ColorManagementResourceDescriptor cmrd = new Triplet.ColorManagementResourceDescriptor();
        cmrd.decodeAFP(new byte[] {0x05, (byte) 0x91, 0x00, 0x01, 0x02}, 0, 5, null);
        verifySF(cmrd, "ColorManagementResourceDescriptor");

        // OCPSS - ObjectContainerPresentationSpaceSize
        Triplet.ObjectContainerPresentationSpaceSize ocpss = new Triplet.ObjectContainerPresentationSpaceSize();
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
        Triplet.LineDataObjectPositionMigration ldopm = new Triplet.LineDataObjectPositionMigration();
        ldopm.decodeAFP(new byte[] {0x03, 0x27, 0x01}, 0, 3, null);
        verifySF(ldopm, "LineDataObjectPositionMigration");

        // KGT - KeepGroupTogether
        Triplet.KeepGroupTogether kgt = new Triplet.KeepGroupTogether();
        kgt.decodeAFP(new byte[] {0x05, (byte) 0x9D, 0x00, 0x00, 0x01}, 0, 5, null);
        verifySF(kgt, "KeepGroupTogether");

        // FCGCS - FontCodedGraphicCharacterSetGlobalID
        Triplet.FontCodedGraphicCharacterSetGlobalID fcgcs = new Triplet.FontCodedGraphicCharacterSetGlobalID();
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
        verifySF(ocnt, "ObjectCount");

        // LODTS - LocalObjectDateAndTimeStamp
        Triplet.LocalObjectDateAndTimeStamp lodts = new Triplet.LocalObjectDateAndTimeStamp();
        lodts.dateAndTimeStampType = Triplet.LocalObjectDateAndTimeStamp.DateAndTimeStampType.Creation;
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
        PTD_PresentationTextDataDescriptor_Format1 ptd1 = new PTD_PresentationTextDataDescriptor_Format1();
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
    public void testPtocaTemplates() throws Exception {
        // STO
        PTOCAControlSequence.STO_SetTextOrientation sto = new PTOCAControlSequence.STO_SetTextOrientation();
        sto.setxOrientation(AFPOrientation.ori0);
        sto.setyOrientation(AFPOrientation.ori90);
        verifySF(sto, "STO_SetTextOrientation");

        // SIA
        PTOCAControlSequence.SIA_SetIntercharacterAdjustment sia = new PTOCAControlSequence.SIA_SetIntercharacterAdjustment();
        sia.setAdjustment((short) 10);
        sia.setDirection(PTOCAControlSequence.SIA_SetIntercharacterAdjustment.SIA_Direction.NegativeIDirection);
        verifySF(sia, "SIA_SetIntercharacterAdjustment");

        // STC
        PTOCAControlSequence.STC_SetTextColor stc = new PTOCAControlSequence.STC_SetTextColor();
        stc.setForegroundColor(AFPColorValue.Blue_0x01);
        stc.setPrecision(PTOCAControlSequence.STC_SetTextColor.STC_Precision.IfSpecifiedColorNotSupported_SubstitutColorOrDefaul0xFF07);
        verifySF(stc, "STC_SetTextColor");

        // USC
        PTOCAControlSequence.USC_Underscore usc = new PTOCAControlSequence.USC_Underscore();
        usc.setBypassFlag(PTOCAControlSequence.PTOCA_BypassFlag.BypassRelativeMoveInline);
        verifySF(usc, "USC_Underscore");

        // SEC
        PTOCAControlSequence.SEC_SetExtendedTextColor sec = new PTOCAControlSequence.SEC_SetExtendedTextColor();
        sec.setColorSpace(AFPColorSpace.RGB);
        sec.setNrOfBitsComponent1((byte) 8);
        sec.setNrOfBitsComponent2((byte) 8);
        sec.setNrOfBitsComponent3((byte) 8);
        sec.setColorValue(new byte[] {0x01, 0x02, 0x03});
        verifySF(sec, "SEC_SetExtendedTextColor");

        // DIR
        PTOCAControlSequence.DIR_DrawIaxisRule dir = new PTOCAControlSequence.DIR_DrawIaxisRule();
        dir.setLength((short) 500);
        dir.setWidth((short) 10);
        verifySF(dir, "DIR_DrawIaxisRule");

        // DBR
        PTOCAControlSequence.DBR_DrawBaxisRule dbr = new PTOCAControlSequence.DBR_DrawBaxisRule();
        dbr.setLength((short) 1000);
        dbr.setWidth((short) 20);
        verifySF(dbr, "DBR_DrawBaxisRule");
    }

    private void verifySF(Object obj, String rootName) throws Exception {
        ByteArrayOutputStream baosFast = new ByteArrayOutputStream();
        try (AfpJacksonXmlWriter writer = new AfpJacksonXmlWriter(baosFast, null, true, false)) {
            if (obj instanceof com.mgz.afp.base.StructuredField sf) {
                writer.handle(sf);
            } else if (obj instanceof Triplet t) {
                // We need to use reflection or a helper to call private writeTriplet in AfpJacksonXmlWriter
                // Or just test the template directly
                XmlTemplate template = null;
                Object[] values = null;
                if (t instanceof Triplet.AttributeQualifier aq) {
                    template = XmlTemplateRegistry.getTemplate("AQ");
                    values = new Object[]{aq.sequenceNumber, aq.levelNumber};
                } else if (t instanceof Triplet.ResourceLocalIdentifier rli) {
                    template = XmlTemplateRegistry.getTemplate("RLI");
                    values = new Object[]{rli.getResourceType(), (int)rli.getResourceLocalID()};
                } else if (t instanceof Triplet.CharacterRotation cr) {
                    template = XmlTemplateRegistry.getTemplate("CR");
                    values = new Object[]{cr.characterRotation};
                } else if (t instanceof Triplet.MeasurementUnits mu) {
                    template = XmlTemplateRegistry.getTemplate("MU");
                    values = new Object[]{mu.xUnitBase, mu.yUnitBase, (int)mu.xUnitsPerUnitbase, (int)mu.yUnitsPerUnitbase};
                } else if (t instanceof Triplet.MappingOption mo) {
                    template = XmlTemplateRegistry.getTemplate("MO");
                    values = new Object[]{mo.getDataObjecMapingOption()};
                } else if (t instanceof Triplet.ObjectAreaSize oas) {
                    template = XmlTemplateRegistry.getTemplate("OAS");
                    values = new Object[]{(int)oas.sizeType_0x02, oas.xSize, oas.ySize};
                } else if (t instanceof Triplet.CodedGraphicCharacterSetGlobalID cgcs) {
                    template = XmlTemplateRegistry.getTemplate("CGCS");
                    values = new Object[]{cgcs.getGraphicCharacterSetGlobalID(), cgcs.getCodePageGlobalID_codedCharacterSetID()};
                } else if (t instanceof Triplet.ResourceObjectType rot) {
                    template = XmlTemplateRegistry.getTemplate("ROT");
                    values = new Object[]{rot.objectType};
                } else if (t instanceof Triplet.DescriptorPosition dp) {
                    template = XmlTemplateRegistry.getTemplate("DP");
                    values = new Object[]{(int)dp.objectAreaDescriptorID};
                } else if (t instanceof Triplet.MediaEjectControl mec) {
                    template = XmlTemplateRegistry.getTemplate("MEC");
                    values = new Object[]{(int)mec.reserved2, mec.mediaEjectControl};
                } else if (t instanceof Triplet.ResourceUsageAttribute rua) {
                    template = XmlTemplateRegistry.getTemplate("RUA");
                    values = new Object[]{rua.frequencyOfUse};
                } else if (t instanceof Triplet.PresentationSpaceResetMixing psrm) {
                    template = XmlTemplateRegistry.getTemplate("PSRM");
                    values = new Object[]{psrm.backgroundMixingFlag};
                } else if (t instanceof Triplet.ExtendedResourceLocalIdentifier erli) {
                    template = XmlTemplateRegistry.getTemplate("ERLI");
                    values = new Object[]{erli.resourceType, erli.extendedResourceLocalID};
                } else if (t instanceof Triplet.ResourceSectionNumber rsn) {
                    template = XmlTemplateRegistry.getTemplate("RSN");
                    values = new Object[]{(int)rsn.resourceSectionNumber};
                } else if (t instanceof Triplet.MediumMapPageNumber mmpn) {
                    template = XmlTemplateRegistry.getTemplate("MMPN");
                    values = new Object[]{mmpn.pageNumber};
                } else if (t instanceof Triplet.ObjectByteExtent obe) {
                    template = XmlTemplateRegistry.getTemplate("OBE");
                    values = new Object[]{obe.byteExtentLow, obe.byteExtentHigh};
                } else if (t instanceof Triplet.FontHorizontalScaleFactor fhsf) {
                    template = XmlTemplateRegistry.getTemplate("FHSF");
                    values = new Object[]{(int)fhsf.horizontalScaleFactor};
                } else if (t instanceof Triplet.MediumOrientation mor) {
                    template = XmlTemplateRegistry.getTemplate("MOR");
                    values = new Object[]{mor.mediumOrientation};
                } else if (t instanceof Triplet.TonerSaver ts) {
                    template = XmlTemplateRegistry.getTemplate("TS");
                    values = new Object[]{(int)ts.reserved2, ts.tonerSaverFunction};
                } else if (t instanceof Triplet.FontResolutionAndMetricTechnology framt) {
                    template = XmlTemplateRegistry.getTemplate("FRAMT");
                    values = new Object[]{framt.metricTechnology, framt.unitBase, (int)framt.unitsPerUnitBase};
                } else if (t instanceof Triplet.ColorManagementResourceDescriptor cmrd) {
                    template = XmlTemplateRegistry.getTemplate("CMRD");
                    values = new Object[]{(int)cmrd.reserved2, cmrd.cmrProcessingMode, cmrd.cmrScope};
                } else if (t instanceof Triplet.ObjectContainerPresentationSpaceSize ocpss) {
                    template = XmlTemplateRegistry.getTemplate("OCPSS");
                    values = new Object[]{ocpss.pdfPresentationSpace};
                } else if (t instanceof Triplet.PagePositionInformation ppi) {
                    template = XmlTemplateRegistry.getTemplate("PPI");
                    values = new Object[]{(int)ppi.repeatingGroupNumber};
                } else if (t instanceof Triplet.TextOrientation to) {
                    template = XmlTemplateRegistry.getTemplate("TO");
                    values = new Object[]{to.xOrientation, to.yOrientation};
                } else if (t instanceof Triplet.LineDataObjectPositionMigration ldopm) {
                    template = XmlTemplateRegistry.getTemplate("LDOPM");
                    values = new Object[]{ldopm.locationAndOrientation};
                } else if (t instanceof Triplet.KeepGroupTogether kgt) {
                    template = XmlTemplateRegistry.getTemplate("KGT");
                    values = new Object[]{(int)kgt.grpFnct};
                } else if (t instanceof Triplet.FontCodedGraphicCharacterSetGlobalID fcgcs) {
                    template = XmlTemplateRegistry.getTemplate("FCGCS");
                    values = new Object[]{fcgcs.codedGraphicCharacterSetGlobalID, fcgcs.codePageGlobalID};
                } else if (t instanceof Triplet.MODCAFunctionSet mfs) {
                    template = XmlTemplateRegistry.getTemplate("MFS");
                    values = new Object[]{mfs.fctSetID};
                } else if (t instanceof Triplet.AreaDefinition ad) {
                    template = XmlTemplateRegistry.getTemplate("AD");
                    values = new Object[]{(int) ad.reserved2, ad.xOrigin, ad.yOrigin, ad.xSize, ad.ySize};
                } else if (t instanceof Triplet.ObjectCount oc) {
                    template = XmlTemplateRegistry.getTemplate("OCNT");
                    String extra = oc.numberOfObjectsHigh != null ? "\" numberOfObjectsHigh=\"" + oc.numberOfObjectsHigh + "\"" : "\"";
                    values = new Object[]{(int) oc.subordinateObjectType, (int) oc.reserved3, oc.numberOfObjectsLow + extra};
                } else if (t instanceof Triplet.LocalObjectDateAndTimeStamp lodts) {
                    template = XmlTemplateRegistry.getTemplate("LODTS");
                    values = new Object[]{lodts.dateAndTimeStampType, (int) lodts.hundreds, (int) lodts.tens, (int) lodts.dayOfYear, (int) lodts.hourOfDay, (int) lodts.minuteOfHour, (int) lodts.secondOfMinute, (int) lodts.hundredthOfSecond};
                } else if (t instanceof Triplet.UniversalDateAndTimeStamp udts) {
                    template = XmlTemplateRegistry.getTemplate("UDTS");
                    values = new Object[]{(int) udts.reserved2, (int) udts.year, (int) udts.monthOfYear, (int) udts.dayOfMonth, (int) udts.hourOfDay, (int) udts.minuteOfHour, (int) udts.secondOfMinute, udts.timeZone, (int) udts.diffHours, (int) udts.diffMinutes};
                } else if (t instanceof Triplet.ObjectByteOffset obo) {
                    template = XmlTemplateRegistry.getTemplate("OBO");
                    String extra = obo.byteOffsetHighOrder != null ? "\" byteOffsetHighOrder=\"" + obo.byteOffsetHighOrder + "\"" : "\"";
                    values = new Object[]{obo.byteOffset + extra};
                } else if (t instanceof Triplet.ObjectStructuredFieldOffset osfo) {
                    template = XmlTemplateRegistry.getTemplate("OSFO");
                    String extra = osfo.offsetHigh != null ? "\" offsetHigh=\"" + osfo.offsetHigh + "\"" : "\"";
                    values = new Object[]{osfo.offsetLow + extra};
                } else if (t instanceof Triplet.ObjectStructuredFieldExtent osfe) {
                    template = XmlTemplateRegistry.getTemplate("OSFE");
                    String extra = osfe.numberOfSFHigh != null ? "\" numberOfSFHigh=\"" + osfe.numberOfSFHigh + "\"" : "\"";
                    values = new Object[]{osfe.numberOfSFLow + extra};
                } else if (t instanceof Triplet.ObjectOffset oo) {
                    template = XmlTemplateRegistry.getTemplate("OO");
                    String typeAttr = oo.objectType != null ? " objectType=\"" + oo.objectType.name() + "\"" : "";
                    String extra = oo.nrOfPrecedingObjectsHigh != null ? "\" nrOfPrecedingObjectsHigh=\"" + oo.nrOfPrecedingObjectsHigh + "\"" : "\"";
                    values = new Object[]{typeAttr, (int) oo.reserved3, oo.nrOfPrecedingObjectsLow + extra};
                }

                if (template != null) {
                    AfpXmlStreamWriter baseXsw = getBaseXsw(writer);
                    template.writeObjects(baseXsw, values);
                    baseXsw.flush();
                }
            } else if (obj instanceof PTOCAControlSequence cs) {
                 XmlTemplate template = null;
                 Object[] values = null;
                 if (cs instanceof PTOCAControlSequence.STO_SetTextOrientation sto) {
                     template = XmlTemplateRegistry.getTemplate("STO");
                     values = new Object[]{sto.getxOrientation(), sto.getyOrientation()};
                 } else if (cs instanceof PTOCAControlSequence.SIA_SetIntercharacterAdjustment sia) {
                     template = XmlTemplateRegistry.getTemplate("SIA");
                     values = new Object[]{(int)sia.getAdjustment(), sia.getDirection()};
                 } else if (cs instanceof PTOCAControlSequence.STC_SetTextColor stc) {
                     template = XmlTemplateRegistry.getTemplate("STC");
                     values = new Object[]{stc.getForegroundColor(), stc.getPrecision()};
                 } else if (cs instanceof PTOCAControlSequence.USC_Underscore usc) {
                     template = XmlTemplateRegistry.getTemplate("USC");
                     values = new Object[]{usc.getBypassFlag()};
                 } else if (cs instanceof PTOCAControlSequence.SEC_SetExtendedTextColor sec) {
                     template = XmlTemplateRegistry.getTemplate("SEC");
                     values = new Object[]{sec.getColorSpace(), (int) sec.getNrOfBitsComponent1(), (int) sec.getNrOfBitsComponent2(), (int) sec.getNrOfBitsComponent3(), (int) sec.getNrOfBitsComponent4(), com.mgz.util.UtilCharacterEncoding.bytesToHexString(sec.getColorValue())};
                 } else if (cs instanceof PTOCAControlSequence.DIR_DrawIaxisRule dir) {
                     // DIR now uses StAX in AfpJacksonXmlWriter, so we just write it via writer.handle if it was a SF
                     // but here it's a PTOCAControlSequence. DIR is not a SF.
                     // In the test we still want to test the template if it exists.
                     template = XmlTemplateRegistry.getTemplate("DIR");
                     if (template != null) {
                         String extra = "\"";
                         if (dir.getWidth() != null) {
                             extra = "\" width=\"" + dir.getWidth();
                             if (dir.getWidthFraction() != null) {
                                 extra += "\" widthFraction=\"" + dir.getWidthFraction();
                             }
                             extra += "\"";
                         }
                         values = new Object[]{dir.getLength(), extra};
                     }
                 } else if (cs instanceof PTOCAControlSequence.DBR_DrawBaxisRule dbr) {
                     template = XmlTemplateRegistry.getTemplate("DBR");
                     if (template != null) {
                         String extra = "\"";
                         if (dbr.getWidth() != null) {
                             extra = "\" width=\"" + dbr.getWidth();
                             if (dbr.getWidthFraction() != null) {
                                 extra += "\" widthFraction=\"" + dbr.getWidthFraction();
                             }
                             extra += "\"";
                         }
                         values = new Object[]{dbr.getLength(), extra};
                     }
                 }

                 if (template != null) {
                     AfpXmlStreamWriter baseXsw = getBaseXsw(writer);
                     template.writeObjects(baseXsw, values);
                     baseXsw.flush();
                 }
            }
        }
        String fastPathXml = baosFast.toString(StandardCharsets.UTF_8);

        String normalizedFastPath = normalizeXml(fastPathXml);

        // For these high-performance templates, we diverged from Jackson by using attributes instead of elements.
        // We verify that the output contains the expected attributes and values.
        if (rootName.equals("AttributeQualifier")) {
            assertTrue(normalizedFastPath.contains("sequenceNumber=\"123\""));
            assertTrue(normalizedFastPath.contains("levelNumber=\"456\""));
        } else if (rootName.equals("ResourceLocalIdentifier")) {
            assertTrue(normalizedFastPath.contains("resourceType=\"PageOverlay\""));
            assertTrue(normalizedFastPath.contains("resourceLocalID=\"5\""));
        } else if (rootName.equals("CharacterRotation")) {
            assertTrue(normalizedFastPath.contains("characterRotation=\"ori90\""));
        } else if (rootName.equals("MeasurementUnits")) {
            assertTrue(normalizedFastPath.contains("xUnitBase=\"Inches10\""));
            assertTrue(normalizedFastPath.contains("xUnitsPerUnitbase=\"2400\""));
        } else if (rootName.equals("MappingOption")) {
            assertTrue(normalizedFastPath.contains("dataObjecMapingOption=\"ScaleToFit\""));
        } else if (rootName.equals("ObjectAreaSize")) {
            assertTrue(normalizedFastPath.contains("sizeType_0x02=\"2\""));
            assertTrue(normalizedFastPath.contains("xSize=\"1200\""));
            assertTrue(normalizedFastPath.contains("ySize=\"1600\""));
        } else if (rootName.equals("CodedGraphicCharacterSetGlobalID")) {
            assertTrue(normalizedFastPath.contains("graphicCharacterSetGlobalID=\"1200\""));
            assertTrue(normalizedFastPath.contains("codePageGlobalID_codedCharacterSetID=\"500\""));
        } else if (rootName.equals("ResourceObjectType")) {
            assertTrue(normalizedFastPath.contains("objectType=\"ImageObject_IOCA\""));
        } else if (rootName.equals("DescriptorPosition")) {
            assertTrue(normalizedFastPath.contains("objectAreaDescriptorID=\"1\""));
        } else if (rootName.equals("MediaEjectControl")) {
            assertTrue(normalizedFastPath.contains("reserved2=\"0\""));
            assertTrue(normalizedFastPath.contains("mediaEjectControl=\"EjectToNewSheet\""));
        } else if (rootName.equals("ResourceUsageAttribute")) {
            assertTrue(normalizedFastPath.contains("frequencyOfUse=\"Low\""));
        } else if (rootName.equals("PresentationSpaceResetMixing")) {
            assertTrue(normalizedFastPath.contains("backgroundMixingFlag=\"ResetColor\""));
        } else if (rootName.equals("ExtendedResourceLocalIdentifier")) {
            assertTrue(normalizedFastPath.contains("resourceType=\"IOBReference_Reserved\""));
            assertTrue(normalizedFastPath.contains("extendedResourceLocalID=\"5\""));
        } else if (rootName.equals("ResourceSectionNumber")) {
            assertTrue(normalizedFastPath.contains("resourceSectionNumber=\"1\""));
        } else if (rootName.equals("MediumMapPageNumber")) {
            assertTrue(normalizedFastPath.contains("pageNumber=\"1\""));
        } else if (rootName.equals("ObjectByteExtent")) {
            assertTrue(normalizedFastPath.contains("byteExtentLow=\"1\""));
            assertTrue(normalizedFastPath.contains("byteExtentHigh=\"2\""));
        } else if (rootName.equals("FontHorizontalScaleFactor")) {
            assertTrue(normalizedFastPath.contains("horizontalScaleFactor=\"1000\""));
        } else if (rootName.equals("MediumOrientation")) {
            assertTrue(normalizedFastPath.contains("mediumOrientation=\"Landscape\""));
        } else if (rootName.equals("TonerSaver")) {
            assertTrue(normalizedFastPath.contains("reserved2=\"0\""));
            assertTrue(normalizedFastPath.contains("tonerSaverFunction=\"ActivateTonerSaver\""));
        } else if (rootName.equals("FontResolutionAndMetricTechnology")) {
            assertTrue(normalizedFastPath.contains("metricTechnology=\"Fixed\""));
            assertTrue(normalizedFastPath.contains("unitBase=\"Inches10\""));
            assertTrue(normalizedFastPath.contains("unitsPerUnitBase=\"240\""));
        } else if (rootName.equals("ColorManagementResourceDescriptor")) {
            assertTrue(normalizedFastPath.contains("reserved2=\"0\""));
            assertTrue(normalizedFastPath.contains("cmrProcessingMode=\"AuditCMR\""));
            assertTrue(normalizedFastPath.contains("cmrScope=\"PageOrOverlay\""));
        } else if (rootName.equals("ObjectContainerPresentationSpaceSize")) {
            assertTrue(normalizedFastPath.contains("pdfPresentationSpace=\"MediaBox\""));
        } else if (rootName.equals("PagePositionInformation")) {
            assertTrue(normalizedFastPath.contains("repeatingGroupNumber=\"5\""));
        } else if (rootName.equals("TextOrientation")) {
            assertTrue(normalizedFastPath.contains("xOrientation=\"ori0\""));
            assertTrue(normalizedFastPath.contains("yOrientation=\"ori90\""));
        } else if (rootName.equals("LineDataObjectPositionMigration")) {
            assertTrue(normalizedFastPath.contains("locationAndOrientation=\"LowerLeft_270\""));
        } else if (rootName.equals("KeepGroupTogether")) {
            assertTrue(normalizedFastPath.contains("grpFnct=\"1\""));
        } else if (rootName.equals("FontCodedGraphicCharacterSetGlobalID")) {
            assertTrue(normalizedFastPath.contains("codedGraphicCharacterSetGlobalID=\"1200\""));
            assertTrue(normalizedFastPath.contains("codePageGlobalID=\"500\""));
        } else if (rootName.equals("MODCAFunctionSet")) {
            assertTrue(normalizedFastPath.contains("fctSetID=\"16\""));
        } else if (rootName.equals("AreaDefinition")) {
            assertTrue(normalizedFastPath.contains("xOrigin=\"100\""));
            assertTrue(normalizedFastPath.contains("yOrigin=\"200\""));
        } else if (rootName.equals("ObjectCount")) {
            assertTrue(normalizedFastPath.contains("numberOfObjectsLow=\"500\""));
        } else if (rootName.equals("LocalObjectDateAndTimeStamp")) {
            assertTrue(normalizedFastPath.contains("dateAndTimeStampType=\"Creation\""));
            assertTrue(normalizedFastPath.contains("hundreds=\"2026\""));
        } else if (rootName.equals("UniversalDateAndTimeStamp")) {
            assertTrue(normalizedFastPath.contains("year=\"2026\""));
            assertTrue(normalizedFastPath.contains("timeZone=\"CoordinatedUTC\""));
        } else if (rootName.equals("PGP1")) {
            assertTrue(normalizedFastPath.contains("xOrigin=\"100\""));
            assertTrue(normalizedFastPath.contains("yOrigin=\"200\""));
        } else if (rootName.equals("STO_SetTextOrientation")) {
            assertTrue(normalizedFastPath.contains("xOrientation=\"ori0\""));
            assertTrue(normalizedFastPath.contains("yOrientation=\"ori90\""));
        } else if (rootName.equals("SIA_SetIntercharacterAdjustment")) {
            assertTrue(normalizedFastPath.contains("adjustment=\"10\""));
            assertTrue(normalizedFastPath.contains("direction=\"NegativeIDirection\""));
        } else if (rootName.equals("STC_SetTextColor")) {
            assertTrue(normalizedFastPath.contains("foregroundColor=\"Blue_0x01\""));
            assertTrue(normalizedFastPath.contains("precision=\"IfSpecifiedColorNotSupported_SubstitutColorOrDefaul0xFF07\""));
        } else if (rootName.equals("USC_Underscore")) {
            assertTrue(normalizedFastPath.contains("bypassFlag=\"BypassRelativeMoveInline\""));
        } else if (rootName.equals("SEC_SetExtendedTextColor")) {
            assertTrue(normalizedFastPath.contains("colorSpace=\"RGB\""));
            assertTrue(normalizedFastPath.contains("nrOfBitsComponent1=\"8\""));
            assertTrue(normalizedFastPath.contains("colorValue=\"010203\""));
        } else if (rootName.equals("ObjectByteOffset")) {
            assertTrue(normalizedFastPath.contains("byteOffset=\"123456789\""));
        } else if (rootName.equals("ObjectStructuredFieldOffset")) {
            assertTrue(normalizedFastPath.contains("offsetLow=\"1000\""));
        } else if (rootName.equals("ObjectStructuredFieldExtent")) {
            assertTrue(normalizedFastPath.contains("numberOfSFLow=\"500\""));
        } else if (rootName.equals("ObjectOffset")) {
            assertTrue(normalizedFastPath.contains("objectType=\"Page_PaginatedObject\""));
            assertTrue(normalizedFastPath.contains("nrOfPrecedingObjectsLow=\"10\""));
        } else if (rootName.equals("PTD1")) {
            assertTrue(normalizedFastPath.contains("xUnitBase=\"Inches10\""));
            assertTrue(normalizedFastPath.contains("xUnitsPerUnitBase=\"1440\""));
            assertTrue(normalizedFastPath.contains("xSize=\"12240\""));
            assertTrue(normalizedFastPath.contains("reserved10_11=\"0000\""));
        } else if (rootName.equals("DIR_DrawIaxisRule")) {
            assertTrue(normalizedFastPath.contains("length=\"500\""));
            assertTrue(normalizedFastPath.contains("width=\"10\""));
        } else if (rootName.equals("DBR_DrawBaxisRule")) {
            assertTrue(normalizedFastPath.contains("length=\"1000\""));
            assertTrue(normalizedFastPath.contains("width=\"20\""));
        }
    }

    private AfpXmlStreamWriter getBaseXsw(AfpJacksonXmlWriter writer) throws Exception {
        java.lang.reflect.Field field = AfpJacksonXmlWriter.class.getDeclaredField("baseXsw");
        field.setAccessible(true);
        return (AfpXmlStreamWriter) field.get(writer);
    }

    private String normalizeXml(String xml) {
        xml = xml.replaceAll(" page=\"\\d+\"", "")
                 .replaceAll(" x=\"-?\\d+\"", "")
                 .replaceAll(" y=\"-?\\d+\"", "")
                 .replaceAll(" precision=\"\"", "")
                 .replaceAll("<beginSF>.*?</beginSF>", "")
                 .replaceAll("<endSF>.*?</endSF>", "")
                 .replaceAll("<shallow>.*?</shallow>", "")
                 .replaceAll("<structuredFieldIntroducer>.*?</structuredFieldIntroducer>", "")
                 .replaceAll("<padding>.*?</padding>", "")
                 .replaceAll("<tripletsXml>", "").replaceAll("</tripletsXml>", "")
                 .replaceAll("<triplets>", "").replaceAll("</triplets>", "")
                 .replaceAll("<repeatingGroupsXml>", "").replaceAll("</repeatingGroupsXml>", "")
                 .replaceAll("<repeatingGroups>", "").replaceAll("</repeatingGroups>", "")
                 .replaceAll("<length>0</length>", "");
        return xml.replaceAll("\\s", "");
    }
}
