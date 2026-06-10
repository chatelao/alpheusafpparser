package com.mgz.xml;

import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.mgz.afp.ioca.IPD_Segment;
import com.mgz.afp.ioca.IPD_ImagePictureData;
import org.junit.jupiter.api.Test;
import java.util.List;

public class JacksonSpikeTest {
    @Test
    public void spike() throws Exception {
        XmlMapper mapper = JacksonXmlMapperProvider.getFragmentMapper();
        IPD_ImagePictureData ipd = new IPD_ImagePictureData();
        IPD_Segment.BeginSegment bs = new IPD_Segment.BeginSegment();
        bs.setSegmentType(IPD_Segment.IPD_SegmentType.BeginSegment);
        bs.setLengthOfFollowingData(2);
        bs.setName(new byte[]{0x01, 0x02});
        ipd.setListOfSegments(List.of(bs));
        System.out.println(mapper.writeValueAsString(ipd));
    }
}
