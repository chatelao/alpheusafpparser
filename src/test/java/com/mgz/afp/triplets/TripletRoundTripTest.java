package com.mgz.afp.triplets;

import com.mgz.afp.RoundTripTestUtils;
import com.mgz.afp.triplets.Triplet.CharacterRotation;
import com.mgz.afp.triplets.Triplet.CodedGraphicCharacterSetGlobalID;
import com.mgz.afp.triplets.Triplet.Comment;
import com.mgz.afp.triplets.Triplet.FontCodedGraphicCharacterSetGlobalID;
import com.mgz.afp.triplets.Triplet.FontDescriptorSpecification;
import com.mgz.afp.triplets.Triplet.FontHorizontalScaleFactor;
import com.mgz.afp.triplets.Triplet.FullyQualifiedName;
import com.mgz.afp.triplets.Triplet.GlobalID_Format;
import com.mgz.afp.triplets.Triplet.GlobalID_Use;
import com.mgz.afp.triplets.Triplet.MODCAInterchangeSet;
import com.mgz.afp.triplets.Triplet.MODCAInterchangeSet.MODCAInterchangeSet_Identifier;
import com.mgz.afp.triplets.Triplet.MODCAInterchangeSet.MODCAInterchangeSet_Type;
import com.mgz.afp.triplets.Triplet.MappingOption;
import com.mgz.afp.triplets.Triplet.AreaDefinition;
import com.mgz.afp.triplets.Triplet.CMRTagFidelity;
import com.mgz.afp.triplets.Triplet.ColorFidelity;
import com.mgz.afp.triplets.Triplet.ColorManagementResourceDescriptor;
import com.mgz.afp.triplets.Triplet.ColorSpecification;
import com.mgz.afp.triplets.Triplet.DeviceAppearance;
import com.mgz.afp.triplets.Triplet.MappingOption.DataObjecMapingOption;
import com.mgz.afp.triplets.Triplet.PresentationSpaceMixingRule;
import com.mgz.afp.triplets.Triplet.PresentationSpaceResetMixing;
import com.mgz.afp.triplets.Triplet.RenderingIntent;
import com.mgz.afp.triplets.Triplet.TonerSaver;
import com.mgz.afp.triplets.Triplet.TripletID;
import com.mgz.afp.triplets.Triplet.ImageResolution;
import com.mgz.afp.triplets.Triplet.MeasurementUnits;
import com.mgz.afp.triplets.Triplet.ObjectAreaSize;
import com.mgz.afp.triplets.Triplet.ObjectClassification;
import com.mgz.afp.triplets.Triplet.ResourceObjectType;
import com.mgz.afp.triplets.Triplet.ExtendedResourceLocalIdentifier;
import com.mgz.afp.triplets.Triplet.ResourceLocalIdentifier;
import com.mgz.afp.triplets.Triplet.ResourceSectionNumber;
import org.junit.Test;

public class TripletRoundTripTest {

    @Test
    public void testCodedGraphicCharacterSetGlobalIDRoundTrip() throws Exception {
        CodedGraphicCharacterSetGlobalID triplet = new CodedGraphicCharacterSetGlobalID();
        triplet.setTripletID(TripletID.CodedGraphicCharacterSetGlobalID);

        // Length(1) | ID(1) | GCSGID(2) | CPGID/CCSID(2)
        byte[] data = new byte[] {
            0x06, (byte) 0x01, 0x00, 0x01, 0x01, (byte) 0xF4 // Length 6, ID 0x01, GCSGID 1, CPGID 500
        };

        RoundTripTestUtils.assertRoundTrip(triplet, data);
    }

    @Test
    public void testFullyQualifiedNameRoundTrip() throws Exception {
        FullyQualifiedName triplet = new FullyQualifiedName();
        triplet.setTripletID(TripletID.FullyQualifiedName);

        // Length(1) | ID(1) | Type(1) | Format(1) | Name(variable)
        // Type 0x01 (ReplaceFirstGIDName), Format 0x00 (CharacterString), Name "TEST"
        byte[] data = new byte[] {
            0x08, 0x02, 0x01, 0x00, (byte) 0xE3, (byte) 0xC5, (byte) 0xE2, (byte) 0xE3 // "TEST" in EBCDIC
        };

        RoundTripTestUtils.assertRoundTrip(triplet, data);
    }

    @Test
    public void testMappingOptionRoundTrip() throws Exception {
        MappingOption triplet = new MappingOption();
        triplet.setTripletID(TripletID.MappingOption);

        // Length(1) | ID(1) | Option(1)
        byte[] data = new byte[] {
            0x03, 0x04, 0x20 // Length 3, ID 0x04, ScaleToFit 0x20
        };

        RoundTripTestUtils.assertRoundTrip(triplet, data);
    }

    @Test
    public void testMODCAInterchangeSetRoundTrip() throws Exception {
        MODCAInterchangeSet triplet = new MODCAInterchangeSet();
        triplet.setTripletID(TripletID.MODCAInterchangeSet);

        // Length(1) | ID(1) | Type(1) | Identifier(2)
        byte[] data = new byte[] {
            0x05, 0x18, 0x01, 0x0D, 0x00 // Length 5, ID 0x18, Type Presentation 0x01, Identifier MODCA_IS3 0x0D00
        };

        RoundTripTestUtils.assertRoundTrip(triplet, data);
    }

    @Test
    public void testCommentRoundTrip() throws Exception {
        Comment triplet = new Comment();
        triplet.setTripletID(TripletID.Comment);

        // Length(1) | ID(1) | Comment(variable)
        // Comment "TEST" in EBCDIC: 0xE3, 0xC5, 0xE2, 0xE3
        byte[] data = new byte[] {
            0x06, 0x65, (byte) 0xE3, (byte) 0xC5, (byte) 0xE2, (byte) 0xE3
        };

        RoundTripTestUtils.assertRoundTrip(triplet, data);
    }

    @Test
    public void testFontDescriptorSpecificationRoundTrip() throws Exception {
        FontDescriptorSpecification triplet = new FontDescriptorSpecification();
        triplet.setTripletID(TripletID.FontDescriptorSpecification);

        // Length(1) | ID(1) | Weight(1) | Width(1) | Height(2) | Width(2) | Flags(1)
        byte[] data = new byte[] {
            0x09, 0x1F, 0x05, 0x05, 0x00, 0x78, 0x00, 0x48, 0x01
        };

        RoundTripTestUtils.assertRoundTrip(triplet, data);
    }

    @Test
    public void testFontCodedGraphicCharacterSetGlobalIDRoundTrip() throws Exception {
        FontCodedGraphicCharacterSetGlobalID triplet = new FontCodedGraphicCharacterSetGlobalID();
        triplet.setTripletID(TripletID.FontCodedGraphicCharacterSetGlobalID);

        // Length(1) | ID(1) | GCSGID(2) | CPGID(2)
        byte[] data = new byte[] {
            0x06, 0x20, 0x00, 0x01, 0x01, (byte) 0xF4 // GCSGID 1, CPGID 500
        };

        RoundTripTestUtils.assertRoundTrip(triplet, data);
    }

    @Test
    public void testCharacterRotationRoundTrip() throws Exception {
        CharacterRotation triplet = new CharacterRotation();
        triplet.setTripletID(TripletID.CharacterRotation);

        // Length(1) | ID(1) | Rotation(2)
        byte[] data = new byte[] {
            0x04, 0x26, 0x2D, 0x00 // 90 degrees
        };

        RoundTripTestUtils.assertRoundTrip(triplet, data);
    }

    @Test
    public void testFontHorizontalScaleFactorRoundTrip() throws Exception {
        FontHorizontalScaleFactor triplet = new FontHorizontalScaleFactor();
        triplet.setTripletID(TripletID.FontHorizontalScaleFactor);

        // Length(1) | ID(1) | Factor(2)
        byte[] data = new byte[] {
            0x04, 0x5D, 0x03, (byte) 0xE8 // 1000
        };

        RoundTripTestUtils.assertRoundTrip(triplet, data);
    }

    @Test
    public void testColorSpecificationRoundTrip() throws Exception {
        ColorSpecification triplet = new ColorSpecification();
        triplet.setTripletID(TripletID.ColorSpecification);

        // Length(1) | ID(1) | Reserved(1) | Space(1) | Reserved(4) | C1Size(1) | C2Size(1) | C3Size(1) | C4Size(1) | Value(3)
        byte[] data = new byte[] {
            0x0F, 0x4E, 0x00, 0x01, 0x00, 0x00, 0x00, 0x00, 0x08, 0x08, 0x08, 0x00, (byte) 0xFF, 0x00, 0x00 // RGB(1), 8-bit, Red
        };

        RoundTripTestUtils.assertRoundTrip(triplet, data);
    }

    @Test
    public void testPresentationSpaceResetMixingRoundTrip() throws Exception {
        PresentationSpaceResetMixing triplet = new PresentationSpaceResetMixing();
        triplet.setTripletID(TripletID.PresentationSpaceResetMixing);

        // Length(1) | ID(1) | Flag(1)
        byte[] data = new byte[] {
            0x03, 0x70, (byte) 0x80 // ResetColor
        };

        RoundTripTestUtils.assertRoundTrip(triplet, data);
    }

    @Test
    public void testPresentationSpaceMixingRuleRoundTrip() throws Exception {
        PresentationSpaceMixingRule triplet = new PresentationSpaceMixingRule();
        triplet.setTripletID(TripletID.PresentationSpaceMixingRule);

        // Length(1) | ID(1) | (Keyword(1) | Rule(1))*2
        byte[] data = new byte[] {
            0x06, 0x71, 0x70, 0x01, 0x71, 0x02 // BackOnBack Overpaint, BackOnFore Underpaint
        };

        RoundTripTestUtils.assertRoundTrip(triplet, data);
    }

    @Test
    public void testTonerSaverRoundTrip() throws Exception {
        TonerSaver triplet = new TonerSaver();
        triplet.setTripletID(TripletID.TonerSaver);

        // Length(1) | ID(1) | Reserved(1) | Function(1) | Reserved(2)
        byte[] data = new byte[] {
            0x06, 0x74, 0x00, 0x01, 0x00, 0x00 // Activate
        };

        RoundTripTestUtils.assertRoundTrip(triplet, data);
    }

    @Test
    public void testColorFidelityRoundTrip() throws Exception {
        ColorFidelity triplet = new ColorFidelity();
        triplet.setTripletID(TripletID.ColorFidelity);

        // Length(1) | ID(1) | Continuation(1) | Reserved(1) | Reporting(1) | Reserved(1) | Substitution(1) | Reserved(1)
        byte[] data = new byte[] {
            0x08, 0x75, 0x01, 0x00, 0x01, 0x00, 0x01, 0x00
        };

        RoundTripTestUtils.assertRoundTrip(triplet, data);
    }

    @Test
    public void testColorManagementResourceDescriptorRoundTrip() throws Exception {
        ColorManagementResourceDescriptor triplet = new ColorManagementResourceDescriptor();
        triplet.setTripletID(TripletID.ColorManagementResourceDescriptor);

        // Length(1) | ID(1) | Reserved(1) | Mode(1) | Scope(1)
        byte[] data = new byte[] {
            0x05, (byte) 0x91, 0x00, 0x01, 0x01 // Audit, DataObject
        };

        RoundTripTestUtils.assertRoundTrip(triplet, data);
    }

    @Test
    public void testRenderingIntentRoundTrip() throws Exception {
        RenderingIntent triplet = new RenderingIntent();
        triplet.setTripletID(TripletID.RenderingIntent);

        // Length(1) | ID(1) | Reserved(2) | IOCA(1) | Container(1) | PTOCA(1) | GOCA(1) | Reserved(2)
        byte[] data = new byte[] {
            0x0A, (byte) 0x95, 0x00, 0x00, 0x00, 0x01, 0x02, 0x03, 0x00, 0x00
        };

        RoundTripTestUtils.assertRoundTrip(triplet, data);
    }

    @Test
    public void testCMRTagFidelityRoundTrip() throws Exception {
        CMRTagFidelity triplet = new CMRTagFidelity();
        triplet.setTripletID(TripletID.CMRTagFidelity);

        // Length(1) | ID(1) | Continuation(1) | Reserved(1) | Reporting(1) | Reserved(2)
        byte[] data = new byte[] {
            0x07, (byte) 0x96, 0x01, 0x00, 0x01, 0x00, 0x00
        };

        RoundTripTestUtils.assertRoundTrip(triplet, data);
    }

    @Test
    public void testDeviceAppearanceRoundTrip() throws Exception {
        DeviceAppearance triplet = new DeviceAppearance();
        triplet.setTripletID(TripletID.DeviceAppearance);

        // Length(1) | ID(1) | Reserved(1) | Appearance(1) | Reserved(3)
        byte[] data = new byte[] {
            0x07, (byte) 0x97, 0x00, 0x01, 0x00, 0x00, 0x00
        };

        RoundTripTestUtils.assertRoundTrip(triplet, data);
    }

    @Test
    public void testMeasurementUnitsRoundTrip() throws Exception {
        MeasurementUnits triplet = new MeasurementUnits();
        triplet.setTripletID(TripletID.MeasurementUnits);

        // Length(1) | ID(1) | XBase(1) | YBase(1) | XUnits(2) | YUnits(2)
        byte[] data = new byte[] {
            0x08, 0x4B, 0x00, 0x00, 0x05, (byte) 0x78, 0x05, (byte) 0x78 // Inches10, 1440 units/base
        };

        RoundTripTestUtils.assertRoundTrip(triplet, data);
    }

    @Test
    public void testObjectAreaSizeRoundTrip() throws Exception {
        ObjectAreaSize triplet = new ObjectAreaSize();
        triplet.setTripletID(TripletID.ObjectAreaSize);

        // Length(1) | ID(1) | Type(1) | XSize(3) | YSize(3)
        byte[] data = new byte[] {
            0x09, 0x4C, 0x02, 0x00, 0x20, 0x00, 0x00, 0x30, 0x00 // Type 2, XSize 8192, YSize 12288
        };

        RoundTripTestUtils.assertRoundTrip(triplet, data);
    }

    @Test
    public void testAreaDefinitionRoundTrip() throws Exception {
        AreaDefinition triplet = new AreaDefinition();
        triplet.setTripletID(TripletID.AreaDefinition);

        // Length(1) | ID(1) | Reserved(1) | XOrigin(3) | YOrigin(3) | XSize(3) | YSize(3)
        byte[] data = new byte[] {
            0x0F, 0x4D, 0x00, 0x00, 0x01, 0x00, 0x00, 0x02, 0x00, 0x00, 0x03, 0x00, 0x00, 0x04, 0x00
        };

        RoundTripTestUtils.assertRoundTrip(triplet, data);
    }

    @Test
    public void testImageResolutionRoundTrip() throws Exception {
        ImageResolution triplet = new ImageResolution();
        triplet.setTripletID(TripletID.ImageResolution);

        // Length(1) | ID(1) | Reserved(2) | XBase(1) | YBase(1) | XUnits(2) | YUnits(2)
        byte[] data = new byte[] {
            0x0A, (byte) 0x9A, 0x00, 0x00, 0x00, 0x00, 0x00, (byte) 0x90, 0x00, (byte) 0x90 // 144 PPI
        };

        RoundTripTestUtils.assertRoundTrip(triplet, data);
    }

    @Test
    public void testObjectClassificationRoundTrip() throws Exception {
        ObjectClassification triplet = new ObjectClassification();
        triplet.setTripletID(TripletID.ObjectClassification);

        // Length(1) | ID(1) | Reserved(1) | Class(1) | Reserved(2) | Flags(2) | RegObjID(16)
        byte[] data = new byte[] {
            0x18, 0x10, 0x00, 0x01, 0x00, 0x00, 0x00, 0x00,
            0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00,
            0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00
        };

        RoundTripTestUtils.assertRoundTrip(triplet, data);
    }

    @Test
    public void testResourceObjectTypeRoundTrip() throws Exception {
        ResourceObjectType triplet = new ResourceObjectType();
        triplet.setTripletID(TripletID.ResourceObjectType);

        // Length(1) | ID(1) | ObjType(1) | Constant(variable)
        byte[] data = new byte[] {
            0x04, 0x21, 0x02, 0x00
        };

        RoundTripTestUtils.assertRoundTrip(triplet, data);
    }

    @Test
    public void testExtendedResourceLocalIdentifierRoundTrip() throws Exception {
        ExtendedResourceLocalIdentifier triplet = new ExtendedResourceLocalIdentifier();
        triplet.setTripletID(TripletID.ExtendedResourceLocalIdentifier);

        // Length(1) | ID(1) | ResType(1) | LID(4)
        byte[] data = new byte[] {
            0x07, 0x22, 0x40, 0x00, 0x00, 0x00, 0x01 // MediaTypeResource, LID 1
        };

        RoundTripTestUtils.assertRoundTrip(triplet, data);
    }

    @Test
    public void testResourceLocalIdentifierRoundTrip() throws Exception {
        ResourceLocalIdentifier triplet = new ResourceLocalIdentifier();
        triplet.setTripletID(TripletID.ResourceLocalIdentifier);

        // Length(1) | ID(1) | ResType(1) | LID(1)
        byte[] data = new byte[] {
            0x04, 0x24, 0x02, 0x01 // PageOverlay, LID 1
        };

        RoundTripTestUtils.assertRoundTrip(triplet, data);
    }

    @Test
    public void testResourceSectionNumberRoundTrip() throws Exception {
        ResourceSectionNumber triplet = new ResourceSectionNumber();
        triplet.setTripletID(TripletID.ResourceSectionNumber);

        // Length(1) | ID(1) | RSN(1)
        byte[] data = new byte[] {
            0x03, 0x25, 0x01
        };

        RoundTripTestUtils.assertRoundTrip(triplet, data);
    }
}
