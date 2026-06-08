package com.mgz.performance;

import com.mgz.cli.Afp2Xml;
import com.mgz.util.HeapHistogramUtil;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import java.io.File;
import java.nio.file.Path;
import java.util.Map;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Leak analysis test for AFP to XML conversion. [DECOUPLE-4.3.2.1.3]
 */
public class Afp2XmlLeakTest {

    @TempDir
    Path tempDir;

    private static final int ITERATIONS = 5;
    private static final String LARGE_AFP = "src/test/resources/afp/large_ibm273.afp";

    @Test
    public void testHandlerLeakage() throws Exception {
        File afpFile = new File(LARGE_AFP);
        if (!afpFile.exists()) {
            return;
        }

        // 1. Initial State
        System.gc();
        Thread.sleep(100);
        Map<String, Long> initialCounts = HeapHistogramUtil.parseHistogram(HeapHistogramUtil.getRawHistogram());
        long initialHandlers = getHandlerCount(initialCounts);
        System.out.println("Initial StructuredFieldHandler instances: " + initialHandlers);

        // 2. Perform conversions
        String outputPath = tempDir.resolve("leak_test.xml").toString();
        for (int i = 0; i < ITERATIONS; i++) {
            Afp2Xml.execute(new String[]{afpFile.getAbsolutePath(), outputPath});
        }

        // 3. Post-conversion State (should still be around if not GCed)
        Map<String, Long> postRunCounts = HeapHistogramUtil.parseHistogram(HeapHistogramUtil.getRawHistogram());
        long postRunHandlers = getHandlerCount(postRunCounts);
        System.out.println("Post-run StructuredFieldHandler instances: " + postRunHandlers);

        // 4. Force GC and check for leaks
        System.gc();
        System.runFinalization();
        System.gc();
        Thread.sleep(200);

        Map<String, Long> finalCounts = HeapHistogramUtil.parseHistogram(HeapHistogramUtil.getRawHistogram());
        long finalHandlers = getHandlerCount(finalCounts);
        System.out.println("Final StructuredFieldHandler instances (after GC): " + finalHandlers);

        // We expect handlers and pooled objects to be eligible for GC after conversion.
        // Allowing a small buffer or same as initial.
        assertTrue(finalHandlers <= initialHandlers + 5,
            "Potential leak detected: tracked object instances increased from "
            + initialHandlers + " to " + finalHandlers);
    }

    private long getHandlerCount(Map<String, Long> counts) {
        long total = 0;
        // Check for known handler implementations
        total += counts.getOrDefault("com.mgz.xml.AfpJacksonXmlWriter", 0L);
        total += counts.getOrDefault("com.mgz.pdf.PdfHandler", 0L);

        // Check for core pooled objects [DECOUPLE-4.3.2.1.4]
        total += counts.getOrDefault("com.mgz.afp.base.StructuredFieldIntroducer", 0L);
        total += counts.getOrDefault("com.mgz.afp.base.StructuredFieldBaseData", 0L);
        return total;
    }
}
