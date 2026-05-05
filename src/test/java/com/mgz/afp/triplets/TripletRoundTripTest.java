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
import com.mgz.afp.triplets.Triplet.MappingOption.DataObjecMapingOption;
import com.mgz.afp.triplets.Triplet.TripletID;
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
}
