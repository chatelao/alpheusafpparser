package com.mgz.performance;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.mgz.afp.base.SfiPool;
import com.mgz.afp.base.StructuredField;
import com.mgz.afp.base.StructuredFieldIntroducer;
import com.mgz.afp.base.StructuredFieldPool;
import com.mgz.afp.base.handler.StructuredFieldHandler;
import com.mgz.afp.enums.SFTypeID;
import com.mgz.afp.modca.BPG_BeginPage;
import com.mgz.afp.triplets.Triplet;
import com.mgz.afp.triplets.TripletPool;
import com.mgz.pdf.PdfHandler;
import com.mgz.pdf.PdfHandlerFactory;
import com.mgz.xml.JacksonXmlMapperProvider;
import com.mgz.xml.XmlHandlerFactory;
import java.io.ByteArrayOutputStream;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Verification test for resource reuse across multiple parsing sessions.
 */
public class ResourceReuseTest {

  /**
   * Sets up the test environment by clearing pools.
   *
   * @throws Exception if reflection fails
   */
  @BeforeEach
  public void setUp() throws Exception {
    clearPools();
  }

  /**
   * Clears all pools to ensure test isolation.
   *
   * @throws Exception if reflection fails
   */
  @SuppressWarnings("unchecked")
  private void clearPools() throws Exception {
    // Clear StructuredFieldPool
    Field sfL1 = StructuredFieldPool.class.getDeclaredField("L1_POOLS");
    sfL1.setAccessible(true);
    ((ThreadLocal<?>) sfL1.get(null)).remove();
    Field sfL2 = StructuredFieldPool.class.getDeclaredField("L2_POOLS");
    sfL2.setAccessible(true);
    Map<?, Queue<?>> sfL2map = (Map<?, Queue<?>>) sfL2.get(null);
    if (sfL2map != null) {
      for (Queue<?> queue : sfL2map.values()) {
        if (queue != null) {
          queue.clear();
        }
      }
    }

    // Clear TripletPool
    Field tripletL1 = TripletPool.class.getDeclaredField("L1_POOLS");
    tripletL1.setAccessible(true);
    ((ThreadLocal<?>) tripletL1.get(null)).remove();
    Field tripletL2 = TripletPool.class.getDeclaredField("L2_POOLS");
    tripletL2.setAccessible(true);
    Map<?, Queue<?>> tl2map = (Map<?, Queue<?>>) tripletL2.get(null);
    if (tl2map != null) {
      for (Queue<?> queue : tl2map.values()) {
        if (queue != null) {
          queue.clear();
        }
      }
    }

    // Clear SfiPool
    Field sfiL1 = SfiPool.class.getDeclaredField("L1_POOL");
    sfiL1.setAccessible(true);
    ((ThreadLocal<?>) sfiL1.get(null)).remove();
    Field sfiL2 = SfiPool.class.getDeclaredField("L2_POOL");
    sfiL2.setAccessible(true);
    Queue<?> sfiQueue = (Queue<?>) sfiL2.get(null);
    if (sfiQueue != null) {
      sfiQueue.clear();
    }
  }

  /**
   * Tests reuse in StructuredFieldPool.
   */
  @Test
  public void testStructuredFieldPoolReuse() {
    BPG_BeginPage sf1 = (BPG_BeginPage) StructuredFieldPool.acquire(SFTypeID.BPG_BeginPage);
    if (sf1 == null) {
      sf1 = new BPG_BeginPage();
    }
    StructuredFieldIntroducer sfi = SfiPool.acquire();
    sfi.setSFTypeID(SFTypeID.BPG_BeginPage);
    sf1.setStructuredFieldIntroducer(sfi);

    sf1.release(); // Releases sf1 to StructuredFieldPool and its SFI to SfiPool

    BPG_BeginPage sf2 = (BPG_BeginPage) StructuredFieldPool.acquire(SFTypeID.BPG_BeginPage);
    assertSame(sf1, sf2, "Structured field should be reused from L1 pool");
  }

  /**
   * Tests reuse in TripletPool.
   */
  @Test
  public void testTripletPoolReuse() {
    Triplet.FullyQualifiedName t1 = (Triplet.FullyQualifiedName) TripletPool.acquire(
        Triplet.TripletID.FullyQualifiedName);
    if (t1 == null) {
      t1 = new Triplet.FullyQualifiedName();
      t1.setTripletID(Triplet.TripletID.FullyQualifiedName);
    }

    TripletPool.release(t1);

    Triplet t2 = TripletPool.acquire(Triplet.TripletID.FullyQualifiedName);
    assertSame(t1, t2, "Triplet should be reused from L1 pool");
  }

  /**
   * Tests reuse in SfiPool.
   */
  @Test
  public void testSfiPoolReuse() {
    StructuredFieldIntroducer sfi1 = SfiPool.acquire();
    SfiPool.release(sfi1);

    StructuredFieldIntroducer sfi2 = SfiPool.acquire();
    assertSame(sfi1, sfi2, "SFI should be reused from L1 pool");
  }

  /**
   * Tests reuse across threads via L2 pool.
   *
   * @throws Exception if thread synchronization fails
   */
  @Test
  public void testCrossThreadResourceReuse() throws Exception {
    Set<Integer> threadAids = Collections.synchronizedSet(new HashSet<>());
    CountDownLatch releaseDone = new CountDownLatch(1);
    CountDownLatch acquireDone = new CountDownLatch(1);
    AtomicReference<StructuredField> threadBobj = new AtomicReference<>();

    Thread threadA = new Thread(() -> {
      List<StructuredField> objects = new ArrayList<>();
      // L1 capacity is 16 for SF pool, so 17th goes to L2
      for (int i = 0; i < 100; i++) {
        BPG_BeginPage sf = new BPG_BeginPage();
        StructuredFieldIntroducer sfi = new StructuredFieldIntroducer();
        sfi.setSFTypeID(SFTypeID.BPG_BeginPage);
        sf.setStructuredFieldIntroducer(sfi);
        objects.add(sf);
        threadAids.add(System.identityHashCode(sf));
      }
      for (StructuredField sf : objects) {
        sf.release();
      }
      releaseDone.countDown();
    }, "Thread-A");

    Thread threadB = new Thread(() -> {
      try {
        if (releaseDone.await(30, TimeUnit.SECONDS)) {
          // Acquire multiple to increase chance of finding one from A
          for (int i = 0; i < 50; i++) {
            StructuredField acq = StructuredFieldPool.acquire(SFTypeID.BPG_BeginPage);
            if (acq != null && threadAids.contains(System.identityHashCode(acq))) {
              threadBobj.set(acq);
              break;
            }
          }
        }
        acquireDone.countDown();
      } catch (InterruptedException e) {
        Thread.currentThread().interrupt();
      }
    }, "Thread-B");

    threadA.start();
    threadB.start();

    assertTrue(acquireDone.await(60, TimeUnit.SECONDS),
        "Thread B should have finished acquisition");
    assertNotNull(threadBobj.get(), "Thread B should have acquired an object from Thread A");
  }

  /**
   * Tests Jackson writer reuse.
   *
   * @throws Exception if handler creation fails
   */
  @Test
  public void testJacksonWriterReuse() throws Exception {
    XmlHandlerFactory factory = new XmlHandlerFactory();
    int initialSize = getJacksonWriterCacheSize();
    ByteArrayOutputStream baos = new ByteArrayOutputStream();
    try (StructuredFieldHandler handler = factory.createHandler(baos, false)) {
      // Exercise the cache with a real structured field
      BPG_BeginPage bpg = new BPG_BeginPage();
      handler.handle(bpg);
    }
    int afterFirst = getJacksonWriterCacheSize();
    assertTrue(afterFirst >= initialSize);
    try (StructuredFieldHandler handler = factory.createHandler(
        new ByteArrayOutputStream(), false)) {
      BPG_BeginPage bpg = new BPG_BeginPage();
      handler.handle(bpg);
    }
    int afterSecond = getJacksonWriterCacheSize();
    assertEquals(afterFirst, afterSecond,
        "Jackson writer cache size should not increase");
  }

  /**
   * Tests PDF font registry reuse.
   *
   * @throws Exception if handler creation fails
   */
  @Test
  public void testPdfFontRegistryReuse() throws Exception {
    PdfHandlerFactory factory = new PdfHandlerFactory();

    PdfHandler h1 = (PdfHandler) factory.createHandler(new ByteArrayOutputStream(), false);
    PdfHandler h2 = (PdfHandler) factory.createHandler(new ByteArrayOutputStream(), false);

    assertSame(h1.getFontRegistry(), h2.getFontRegistry(), "PDF registries should be reused");

    h1.close();
    h2.close();
  }

  /**
   * Gets the Jackson writer cache size via reflection.
   *
   * @return the cache size
   * @throws Exception if reflection fails
   */
  @SuppressWarnings("unchecked")
  private int getJacksonWriterCacheSize() throws Exception {
    Field field = JacksonXmlMapperProvider.class.getDeclaredField("WRITER_CACHE");
    field.setAccessible(true);
    Map<?, ?> cache = (Map<?, ?>) field.get(null);
    return cache.size();
  }
}
