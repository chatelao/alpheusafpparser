package com.mgz.performance;

import com.mgz.afp.modca.NOP_NoOperation;
import com.mgz.afp.base.SfiPool;
import com.mgz.afp.base.StructuredFieldBaseData;
import com.mgz.afp.base.StructuredFieldBaseDataPool;
import com.mgz.afp.base.StructuredFieldIntroducer;
import com.mgz.afp.base.StructuredFieldPool;
import com.mgz.afp.enums.SFTypeID;
import com.mgz.afp.parser.TripletParser;
import com.mgz.afp.triplets.Triplet;
import com.mgz.afp.triplets.TripletPool;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * Multi-threaded stress test for object pools to ensure thread-safety.
 */
public class PoolThreadSafetyTest {

    private static final int THREADS = 16;
    private static final int ITERATIONS = 50000;

    @Test
    public void testSfiPoolThreadSafety() throws Exception {
        runPoolTest(() -> {
            for (int i = 0; i < ITERATIONS; i++) {
                StructuredFieldIntroducer sfi = SfiPool.acquire();
                assertNotNull(sfi);
                SfiPool.release(sfi);
            }
            return null;
        });
    }

    @Test
    public void testTripletPoolThreadSafety() throws Exception {
        Triplet.TripletID[] ids = Triplet.TripletID.values();
        runPoolTest(() -> {
            for (int i = 0; i < ITERATIONS; i++) {
                Triplet.TripletID id = ids[i % ids.length];
                Triplet triplet = TripletParser.createTripletInstance(id);
                assertNotNull(triplet);
                TripletPool.release(triplet);
            }
            return null;
        });
    }

    @Test
    public void testStructuredFieldPoolThreadSafety() throws Exception {
        runPoolTest(() -> {
            for (int i = 0; i < ITERATIONS; i++) {
                SFTypeID type = SFTypeID.NOP_NoOperation;
                var sf = StructuredFieldPool.acquire(type);
                if (sf == null) {
                    sf = new NOP_NoOperation();
                }
                assertNotNull(sf);
                StructuredFieldPool.release(sf, type);
            }
            return null;
        });
    }

    @Test
    public void testStructuredFieldBaseDataPoolThreadSafety() throws Exception {
        runPoolTest(() -> {
            for (int i = 0; i < ITERATIONS; i++) {
                StructuredFieldBaseData data = StructuredFieldBaseDataPool.acquire();
                assertNotNull(data);
                StructuredFieldBaseDataPool.release(data);
            }
            return null;
        });
    }

    private void runPoolTest(Callable<Void> task) throws Exception {
        ExecutorService executor = Executors.newFixedThreadPool(THREADS);
        List<Future<Void>> futures = new ArrayList<>();
        for (int i = 0; i < THREADS; i++) {
            futures.add(executor.submit(task));
        }
        for (Future<Void> future : futures) {
            future.get(1, TimeUnit.MINUTES);
        }
        executor.shutdown();
        executor.awaitTermination(10, TimeUnit.SECONDS);
    }
}
