package com.mgz.performance;

import com.mgz.afp.base.StructuredField;
import com.mgz.afp.base.StructuredFieldIntroducer;
import com.mgz.afp.base.StructuredFieldPool;
import com.mgz.afp.base.SfiPool;
import com.mgz.afp.base.handler.StructuredFieldHandler;
import com.mgz.afp.enums.SFTypeID;
import com.mgz.afp.triplets.Triplet;
import com.mgz.afp.triplets.TripletPool;
import com.mgz.pdf.PdfHandler;
import com.mgz.pdf.PdfHandlerFactory;
import com.mgz.xml.JacksonXmlMapperProvider;
import com.mgz.xml.XmlHandlerFactory;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Verification test for resource reuse across multiple parsing sessions.
 */
public class ResourceReuseTest {

    @Test
    public void testStructuredFieldPoolReuse() {
        com.mgz.afp.modca.BPG_BeginPage sf1 = (com.mgz.afp.modca.BPG_BeginPage) StructuredFieldPool.acquire(SFTypeID.BPG_BeginPage);
        if (sf1 == null) {
            sf1 = new com.mgz.afp.modca.BPG_BeginPage();
        }
        StructuredFieldIntroducer sfi = SfiPool.acquire();
        sfi.setSFTypeID(SFTypeID.BPG_BeginPage);
        sf1.setStructuredFieldIntroducer(sfi);

        sf1.release(); // Releases sf1 to StructuredFieldPool and its SFI to SfiPool

        com.mgz.afp.modca.BPG_BeginPage sf2 = (com.mgz.afp.modca.BPG_BeginPage) StructuredFieldPool.acquire(SFTypeID.BPG_BeginPage);
        assertSame(sf1, sf2, "Structured field should be reused from L1 pool");
    }

    @Test
    public void testTripletPoolReuse() {
        Triplet.FullyQualifiedName t1 = (Triplet.FullyQualifiedName) TripletPool.acquire(Triplet.TripletID.FullyQualifiedName);
        if (t1 == null) {
            t1 = new Triplet.FullyQualifiedName();
            t1.setTripletID(Triplet.TripletID.FullyQualifiedName);
        }

        TripletPool.release(t1);

        Triplet t2 = TripletPool.acquire(Triplet.TripletID.FullyQualifiedName);
        assertSame(t1, t2, "Triplet should be reused from L1 pool");
    }

    @Test
    public void testSfiPoolReuse() {
        StructuredFieldIntroducer sfi1 = SfiPool.acquire();
        SfiPool.release(sfi1);

        StructuredFieldIntroducer sfi2 = SfiPool.acquire();
        assertSame(sfi1, sfi2, "SFI should be reused from L1 pool");
    }

    @Test
    public void testCrossThreadResourceReuse() throws Exception {
        Set<Integer> threadAObjectIds = Collections.synchronizedSet(new HashSet<>());
        CountDownLatch releaseDone = new CountDownLatch(1);
        CountDownLatch acquireDone = new CountDownLatch(1);
        AtomicReference<StructuredField> threadBObject = new AtomicReference<>();

        Thread threadA = new Thread(() -> {
            List<StructuredField> objects = new ArrayList<>();
            // L1 capacity is 16 for SF pool, so 17th goes to L2
            for (int i = 0; i < 17; i++) {
                com.mgz.afp.modca.BPG_BeginPage sf = new com.mgz.afp.modca.BPG_BeginPage();
                StructuredFieldIntroducer sfi = new StructuredFieldIntroducer();
                sfi.setSFTypeID(SFTypeID.BPG_BeginPage);
                sf.setStructuredFieldIntroducer(sfi);
                objects.add(sf);
                threadAObjectIds.add(System.identityHashCode(sf));
            }
            for (StructuredField sf : objects) {
                sf.release();
            }
            releaseDone.countDown();
        }, "Thread-A");

        Thread threadB = new Thread(() -> {
            try {
                if (releaseDone.await(5, TimeUnit.SECONDS)) {
                    threadBObject.set(StructuredFieldPool.acquire(SFTypeID.BPG_BeginPage));
                }
                acquireDone.countDown();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }, "Thread-B");

        threadA.start();
        threadB.start();

        assertTrue(acquireDone.await(10, TimeUnit.SECONDS), "Thread B should have finished acquisition");
        assertNotNull(threadBObject.get(), "Thread B should have acquired an object");
        assertTrue(threadAObjectIds.contains(System.identityHashCode(threadBObject.get())),
            "Thread B should have acquired an object released by Thread A via L2 pool");
    }

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

    @SuppressWarnings("unchecked")
    private int getJacksonWriterCacheSize() throws Exception {
        Field field = JacksonXmlMapperProvider.class.getDeclaredField("WRITER_CACHE");
        field.setAccessible(true);
        Map<?, ?> cache = (Map<?, ?>) field.get(null);
        return cache.size();
    }
}
