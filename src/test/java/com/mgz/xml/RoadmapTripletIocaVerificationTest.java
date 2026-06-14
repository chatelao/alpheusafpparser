package com.mgz.xml;

import com.mgz.afp.ioca.IPD_ImagePictureData;
import com.mgz.afp.ioca.IPD_Segment;
import com.mgz.afp.modca.TLE_TagLogicalElement;
import com.mgz.afp.triplets.Triplet;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class RoadmapTripletIocaVerificationTest {

    @Test
    public void testNewIocaSegments() throws Exception {
        IPD_ImagePictureData ipd = new IPD_ImagePictureData();
        List<IPD_Segment> segments = new ArrayList<>();

        IPD_Segment.BeginSegment bs = new IPD_Segment.BeginSegment();
        bs.setName(new byte[]{0x01, 0x02});
        // bs.text is set in decodeAFP if it's human readable, but we can set it via reflection if needed for testing,
        // or just rely on name. Actually it has no setter for text.
        segments.add(bs);

        segments.add(new IPD_Segment.EndSegment());

        IPD_Segment.IDESize ideSize = new IPD_Segment.IDESize();
        ideSize.numberOfBitsInEachIDE = 8;
        segments.add(ideSize);

        IPD_Segment.ImageLUTID lutId = new IPD_Segment.ImageLUTID();
        lutId.setLutId((short) 1);
        segments.add(lutId);

        IPD_Segment.FunctionSetIdentification fsi = new IPD_Segment.FunctionSetIdentification();
        fsi.functionSet = 10;
        segments.add(fsi);

        ipd.setListOfSegments(segments);

        verifySF(ipd, "IPD_ImagePictureData");
    }

    @Test
    public void testNewTriplets() throws Exception {
        TLE_TagLogicalElement tle = new TLE_TagLogicalElement();
        List<Triplet> triplets = new ArrayList<>();

        Triplet.Undefined undefined = new Triplet.Undefined();
        undefined.setTripletData(new byte[]{0x03, 0x00, 0x01, 0x02}); // length 3, ID 0, data 01 02
        triplets.add(undefined);

        Triplet.TripletExtender extender = new Triplet.TripletExtender();
        extender.setDatExt(new byte[]{0x0A, 0x0B});
        triplets.add(extender);

        tle.setTriplets(triplets);

        verifySF(tle, "TLE_TagLogicalElement");
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
        // 1. Convert attributes to pseudo-elements so their values are preserved
        java.util.regex.Matcher m = java.util.regex.Pattern.compile(" ([a-zA-Z0-9_]+)=\"([^\"]*)\"").matcher(xml);
        StringBuilder sb = new StringBuilder();
        int lastEnd = 0;
        while (m.find()) {
            sb.append(xml, lastEnd, m.start());
            String name = m.group(1);
            // Ignore metadata attributes
            if (!name.equals("page") && !name.equals("x") && !name.equals("y") && !name.equals("precision")) {
                sb.append("<").append(name).append(">").append(m.group(2)).append("</").append(name).append(">");
            }
            lastEnd = m.end();
        }
        sb.append(xml.substring(lastEnd));
        xml = sb.toString();

        // 2. Remove all structural metadata and reserved fields
        xml = xml.replaceAll("<beginSF>.*?</beginSF>", "")
                 .replaceAll("<endSF>.*?</endSF>", "")
                 .replaceAll("<shallow>.*?</shallow>", "")
                 .replaceAll("<structuredFieldIntroducer>.*?</structuredFieldIntroducer>", "")
                 .replaceAll("<padding>.*?</padding>", "")
                 .replaceAll("<length>[0-9]*</length>", "")
                 .replaceAll("<reserved[a-zA-Z0-9_]*>.*?</reserved[a-zA-Z0-9_]*>", "");

        // 3. Remove all tags and compare only the text content (values)
        xml = xml.replaceAll("<[^>]*>", "");

        // 4. Strip all whitespace
        return xml.replaceAll("\\s", "");
    }
}
