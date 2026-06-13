package com.mgz.performance;

import com.mgz.afp.base.RepeatingGroupPool;
import com.mgz.afp.base.RepeatingGroupWithTriplets;
import com.mgz.afp.base.SfiPool;
import com.mgz.afp.base.StructuredField;
import com.mgz.afp.base.StructuredFieldBaseData;
import com.mgz.afp.base.StructuredFieldBaseDataPool;
import com.mgz.afp.base.StructuredFieldIntroducer;
import com.mgz.afp.base.StructuredFieldPool;
import com.mgz.afp.base.handler.StructuredFieldHandler;
import com.mgz.afp.enums.SFTypeID;
import com.mgz.afp.goca.DrawingOrderPool;
import com.mgz.afp.goca.GAD_DrawingOrder;
import com.mgz.afp.ioca.IpdSegmentPool;
import com.mgz.afp.ioca.IPD_Segment;
import com.mgz.afp.modca.BPG_BeginPage;
import com.mgz.afp.parser.AFPParser;
import com.mgz.afp.parser.AFPParserConfiguration;
import com.mgz.pdf.PdfHandler;
import com.mgz.pdf.PdfHandlerFactory;
import com.mgz.afp.ptoca.controlSequence.ControlSequencePool;
import com.mgz.afp.ptoca.controlSequence.PTOCAControlSequence;
import com.mgz.afp.triplets.Triplet;
import com.mgz.afp.triplets.TripletPool;
import com.mgz.xml.JacksonXmlMapperProvider;
import com.mgz.xml.XmlHandlerFactory;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Verification test for resource reuse across multiple parsing sessions.
 */
public class ResourceReuseTest {

    @Test
    public void testJacksonWriterReuse() throws Exception {
        XmlHandlerFactory factory = new XmlHandlerFactory();

        int initialCacheSize = getJacksonWriterCacheSize();

        try (StructuredFieldHandler handler = factory.createHandler(new ByteArrayOutputStream(), false)) {
            // Exercise the cache with a real structured field
            com.mgz.afp.modca.BPG_BeginPage bpg = new com.mgz.afp.modca.BPG_BeginPage();
            handler.handle(bpg);
        }

        int afterFirstHandler = getJacksonWriterCacheSize();
        assertTrue(afterFirstHandler >= initialCacheSize);

        try (StructuredFieldHandler handler = factory.createHandler(new ByteArrayOutputStream(), false)) {
            com.mgz.afp.modca.BPG_BeginPage bpg = new com.mgz.afp.modca.BPG_BeginPage();
            handler.handle(bpg);
        }

        int afterSecondHandler = getJacksonWriterCacheSize();
        assertEquals(afterFirstHandler, afterSecondHandler, "Jackson writer cache size should not increase after second handler uses same types");
    }

    @Test
    public void testPdfFontRegistryReuse() throws Exception {
        PdfHandlerFactory factory = new PdfHandlerFactory();

        PdfHandler handler1 = (PdfHandler) factory.createHandler(new ByteArrayOutputStream(), false);
        PdfHandler handler2 = (PdfHandler) factory.createHandler(new ByteArrayOutputStream(), false);

        assertSame(handler1.getFontRegistry(), handler2.getFontRegistry(), "PDF font registries should be reused");

        handler1.close();
        handler2.close();
    }

    @Test
    public void testSfiPoolReuse() {
        StructuredFieldIntroducer sfi1 = SfiPool.acquire();
        SfiPool.release(sfi1);
        StructuredFieldIntroducer sfi2 = SfiPool.acquire();
        assertSame(sfi1, sfi2, "SFI should be reused from pool");
    }

    @Test
    public void testStructuredFieldPoolReuse() {
        SFTypeID type = SFTypeID.BPG_BeginPage;
        StructuredField sf1 = StructuredFieldPool.acquire(type);
        if (sf1 == null) sf1 = new BPG_BeginPage();

        StructuredFieldPool.release(sf1, type);
        StructuredField sf2 = StructuredFieldPool.acquire(type);
        assertSame(sf1, sf2, "StructuredField should be reused from pool");
    }

    @Test
    public void testStructuredFieldBaseDataPoolReuse() {
        StructuredFieldBaseData sf1 = StructuredFieldBaseDataPool.acquire();
        StructuredFieldBaseDataPool.release(sf1);
        StructuredFieldBaseData sf2 = StructuredFieldBaseDataPool.acquire();
        assertSame(sf1, sf2, "StructuredFieldBaseData should be reused from pool");
    }

    @Test
    public void testTripletPoolReuse() {
        Triplet.TripletID tid = Triplet.TripletID.FullyQualifiedName;
        Triplet t1 = TripletPool.acquire(tid);
        if (t1 == null) {
            t1 = new Triplet.FullyQualifiedName();
            t1.setTripletID(tid);
        }

        TripletPool.release(t1);
        Triplet t2 = TripletPool.acquire(tid);
        assertSame(t1, t2, "Triplet should be reused from pool");
    }

    @Test
    public void testRepeatingGroupPoolReuse() {
        Class<RepeatingGroupWithTriplets> clazz = RepeatingGroupWithTriplets.class;
        RepeatingGroupWithTriplets rg1 = RepeatingGroupPool.acquire(clazz);
        if (rg1 == null) {
            rg1 = new RepeatingGroupWithTriplets();
        }

        RepeatingGroupPool.release(rg1);
        RepeatingGroupWithTriplets rg2 = RepeatingGroupPool.acquire(clazz);
        assertSame(rg1, rg2, "RepeatingGroup should be reused from pool");
    }

    @Test
    public void testControlSequencePoolReuse() {
        PTOCAControlSequence.ControlSequenceFunctionType type = PTOCAControlSequence.ControlSequenceFunctionType.AMI_AbsoluteMoveInline;
        PTOCAControlSequence cs1 = ControlSequencePool.acquire(type);
        if (cs1 == null) {
            cs1 = new PTOCAControlSequence.AMI_AbsoluteMoveInline();
            cs1.setCsi(new PTOCAControlSequence.ControlSequenceIntroducer());
            cs1.getCsi().setControlSequenceFunctionType(type);
        }

        ControlSequencePool.release(cs1);
        PTOCAControlSequence cs2 = ControlSequencePool.acquire(type);
        assertSame(cs1, cs2, "ControlSequence should be reused from pool");
    }

    @Test
    public void testDrawingOrderPoolReuse() {
        short type = 0x01; // Line at given position
        GAD_DrawingOrder o1 = DrawingOrderPool.acquire(type);
        if (o1 == null) {
            o1 = new GAD_DrawingOrder.GLINE_LineAtGivenPosition();
            o1.setDrawingOrderType(type);
        }

        DrawingOrderPool.release(o1);
        GAD_DrawingOrder o2 = DrawingOrderPool.acquire(type);
        assertSame(o1, o2, "DrawingOrder should be reused from pool");
    }

    @Test
    public void testIpdSegmentPoolReuse() {
        IPD_Segment.IPD_SegmentType type = IPD_Segment.IPD_SegmentType.ImageData;
        IPD_Segment s1 = IpdSegmentPool.acquire(type);
        if (s1 == null) {
            s1 = new IPD_Segment.ImageData();
            s1.setSegmentType(type);
        }

        IpdSegmentPool.release(s1);
        IPD_Segment s2 = IpdSegmentPool.acquire(type);
        assertSame(s1, s2, "IPD_Segment should be reused from pool");
    }

    @Test
    public void testParserResourceReuseAcrossSessions() throws Exception {
        // Create a simple BPG SF: 5A 00 10 D3 A8 AF ...
        byte[] afpData = {
            0x5A, 0x00, 0x10, (byte)0xD3, (byte)0xA8, (byte)0xAF, 0x00, 0x00, 0x00,
            0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00
        };

        List<Integer> instanceHashes = new ArrayList<>();

        // First session
        AFPParserConfiguration config1 = new AFPParserConfiguration();
        config1.setInputStream(new ByteArrayInputStream(afpData));
        AFPParser parser1 = new AFPParser(config1);
        StructuredField sf1 = parser1.parseNextSF();
        assertNotNull(sf1);
        instanceHashes.add(System.identityHashCode(sf1));
        instanceHashes.add(System.identityHashCode(sf1.getStructuredFieldIntroducer()));
        sf1.release();

        // Second session
        AFPParserConfiguration config2 = new AFPParserConfiguration();
        config2.setInputStream(new ByteArrayInputStream(afpData));
        AFPParser parser2 = new AFPParser(config2);
        StructuredField sf2 = parser2.parseNextSF();
        assertNotNull(sf2);

        assertEquals(instanceHashes.get(0).intValue(), System.identityHashCode(sf2), "StructuredField instance should be reused");
        assertEquals(instanceHashes.get(1).intValue(), System.identityHashCode(sf2.getStructuredFieldIntroducer()), "SFI instance should be reused");
        sf2.release();
    }

    @SuppressWarnings("unchecked")
    private int getJacksonWriterCacheSize() throws Exception {
        Field field = JacksonXmlMapperProvider.class.getDeclaredField("WRITER_CACHE");
        field.setAccessible(true);
        Map<?, ?> cache = (Map<?, ?>) field.get(null);
        return cache.size();
    }
}
