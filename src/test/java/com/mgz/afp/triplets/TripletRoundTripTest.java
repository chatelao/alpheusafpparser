package com.mgz.afp.triplets;

import com.mgz.afp.RoundTripTestUtils;
import com.mgz.afp.triplets.Triplet.CodedGraphicCharacterSetGlobalID;
import com.mgz.afp.triplets.Triplet.FullyQualifiedName;
import com.mgz.afp.triplets.Triplet.GlobalID_Format;
import com.mgz.afp.triplets.Triplet.GlobalID_Use;
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
}
