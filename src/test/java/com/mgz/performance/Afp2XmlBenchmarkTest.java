package com.mgz.performance;

import com.mgz.cli.Afp2Xml;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import java.io.File;
import java.lang.management.ManagementFactory;
import java.lang.management.MemoryMXBean;
import java.lang.management.MemoryPoolMXBean;
import java.lang.management.MemoryUsage;
import java.nio.file.Path;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Throughput benchmark for AFP to XML conversion.
 */
public class Afp2XmlBenchmarkTest {

    @TempDir
    Path tempDir;

    private static final int WARMUP_ITERATIONS = 3;
    private static final int MEASURE_ITERATIONS = 5;
    private static final String LARGE_AFP = "src/test/resources/afp/large_ibm273.afp";

    @Test
    public void runBenchmark() throws Exception {
        File afpFile = new File(LARGE_AFP);
        if (!afpFile.exists()) {
            System.out.println("Skipping benchmark: " + LARGE_AFP + " not found.");
            return;
        }

        System.out.println("Starting Benchmark for " + LARGE_AFP + " (" + (afpFile.length() / 1024 / 1024) + " MB)");

        // 1. Sequential Mode
        runModeBenchmark("Sequential", new String[]{afpFile.getAbsolutePath(), tempDir.resolve("seq.xml").toString()}, afpFile.length());

        // 2. Parallel Mode
        runModeBenchmark("Parallel", new String[]{"--parallel", afpFile.getAbsolutePath(), tempDir.resolve("par.xml").toString()}, afpFile.length());
    }

    private void runModeBenchmark(String modeName, String[] args, long fileSize) throws Exception {
        // Reset peak memory usage before measurement
        List<MemoryPoolMXBean> pools = ManagementFactory.getMemoryPoolMXBeans();
        for (MemoryPoolMXBean pool : pools) {
            pool.resetPeakUsage();
        }

        // Warm-up
        for (int i = 0; i < WARMUP_ITERATIONS; i++) {
            Afp2Xml.execute(args);
        }

        // Measure
        long totalDuration = 0;
        for (int i = 0; i < MEASURE_ITERATIONS; i++) {
            long startTime = System.nanoTime();
            int result = Afp2Xml.execute(args);
            long endTime = System.nanoTime();
            assertEquals(0, result, modeName + " conversion failed during benchmark");
            totalDuration += (endTime - startTime);
        }

        double avgDurationSeconds = (totalDuration / (double) MEASURE_ITERATIONS) / 1_000_000_000.0;
        double throughputMBs = (fileSize / (1024.0 * 1024.0)) / avgDurationSeconds;

        // Calculate peak heap usage
        long peakHeapUsage = 0;
        for (MemoryPoolMXBean pool : pools) {
            if (pool.getType() == java.lang.management.MemoryType.HEAP) {
                MemoryUsage usage = pool.getPeakUsage();
                if (usage != null) {
                    peakHeapUsage += usage.getUsed();
                }
            }
        }

        System.out.printf("%s mode average: %.3f seconds (%.2f MB/s), Peak Heap: %.2f MB%n",
            modeName, avgDurationSeconds, throughputMBs, peakHeapUsage / (1024.0 * 1024.0));
    }
}
