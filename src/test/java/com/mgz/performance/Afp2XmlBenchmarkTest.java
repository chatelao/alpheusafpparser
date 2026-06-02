package com.mgz.performance;

import com.mgz.cli.Afp2Xml;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.management.ManagementFactory;
import java.lang.management.MemoryPoolMXBean;
import java.lang.management.MemoryUsage;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * Throughput benchmark for AFP to XML conversion.
 * Includes allocation tracking [DECOUPLE-4.2.3.2.1] and JFR profiling [DECOUPLE-4.2.3.1.1].
 */
public class Afp2XmlBenchmarkTest {

    @TempDir
    Path tempDir;

    private static final int WARMUP_ITERATIONS = 3;
    private static final int MEASURE_ITERATIONS = 5;
    private static final String LARGE_AFP = "src/test/resources/afp/large_ibm273.afp";

    @Test
    public void verifyNoJaxbContext() {
        // Verify that JAXB is not present in the runtime environment [JACK-ONLY-ROADMAP]
        assertThrows(ClassNotFoundException.class, () -> {
            Class.forName("javax.xml.bind.JAXBContext");
        }, "JAXBContext should not be available in a Jackson-only environment");
    }

    @Test
    public void runBenchmark() throws Exception {
        File afpFile = new File(LARGE_AFP);
        if (!afpFile.exists()) {
            System.out.println("Skipping benchmark: " + LARGE_AFP + " not found.");
            return;
        }

        System.out.println("Starting Benchmark for " + LARGE_AFP + " (" + (afpFile.length() / 1024 / 1024) + " MB)");

        // 1. Sequential Mode
        runModeBenchmark("Sequential", new String[]{afpFile.getAbsolutePath(), tempDir.resolve("seq.xml").toString()}, afpFile.length(), false);

        // 2. Parallel Mode
        runModeBenchmark("Parallel", new String[]{"--parallel", afpFile.getAbsolutePath(), tempDir.resolve("par.xml").toString()}, afpFile.length(), true);
    }

    @Test
    public void runStressTest() throws Exception {
        File afpFile = new File(LARGE_AFP);
        if (!afpFile.exists()) {
            return;
        }

        // Create a ~100MB file by concatenating the 10MB file 10 times [DECOUPLE-4.3.2.1.1]
        Path stressFile = tempDir.resolve("stress_100mb.afp");
        try (FileOutputStream fos = new FileOutputStream(stressFile.toFile())) {
            byte[] content = Files.readAllBytes(afpFile.toPath());
            for (int i = 0; i < 10; i++) {
                fos.write(content);
            }
        }

        System.out.println("Starting Stress Test for " + stressFile + " (" + (Files.size(stressFile) / 1024 / 1024) + " MB)");

        // Run with JFR enabled for hotspot identification [DECOUPLE-4.2.3.1.1]
        runModeBenchmark("Stress-Parallel", new String[]{"--parallel", stressFile.toString(), tempDir.resolve("stress.xml").toString()}, Files.size(stressFile), true);
    }

    private void runModeBenchmark(String modeName, String[] args, long fileSize, boolean useJfr) throws Exception {
        // Reset peak memory usage before measurement
        List<MemoryPoolMXBean> pools = ManagementFactory.getMemoryPoolMXBeans();
        for (MemoryPoolMXBean pool : pools) {
            pool.resetPeakUsage();
        }

        long startAllocated = getAllocatedBytes();

        // Warm-up
        for (int i = 0; i < WARMUP_ITERATIONS; i++) {
            Afp2Xml.execute(args);
        }

        Object recording = null;
        if (useJfr) {
            recording = startJfr();
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

        if (useJfr && recording != null) {
            stopAndSaveJfr(recording, modeName);
        }

        double avgDurationSeconds = (totalDuration / (double) MEASURE_ITERATIONS) / 1_000_000_000.0;
        double throughputMBs = (fileSize / (1024.0 * 1024.0)) / avgDurationSeconds;

        // Allocation Tracking [DECOUPLE-4.2.3.2.1]
        long totalAllocated = getAllocatedBytes() - startAllocated;

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

        System.out.printf("%s mode average: %.3f seconds (%.2f MB/s), Peak Heap: %.2f MB, Total Allocated: %.2f MB%n",
            modeName, avgDurationSeconds, throughputMBs, peakHeapUsage / (1024.0 * 1024.0),
            totalAllocated / (1024.0 * 1024.0));
    }

    private Object startJfr() {
        try {
            Class<?> recordingClass = Class.forName("jdk.jfr.Recording");
            Object recording = recordingClass.getConstructor().newInstance();
            recordingClass.getMethod("start").invoke(recording);
            return recording;
        } catch (Exception e) {
            System.err.println("Failed to start JFR: " + e.getMessage());
            return null;
        }
    }

    private void stopAndSaveJfr(Object recording, String modeName) {
        try {
            Class<?> recordingClass = Class.forName("jdk.jfr.Recording");
            recordingClass.getMethod("stop").invoke(recording);
            Path reportDir = Path.of("build/reports/jfr");
            Files.createDirectories(reportDir);
            Path jfrFile = reportDir.resolve("benchmark-" + modeName.toLowerCase() + ".jfr");
            recordingClass.getMethod("dump", Path.class).invoke(recording, jfrFile);
            System.out.println("JFR profile saved to: " + jfrFile.toAbsolutePath());
        } catch (Exception e) {
            System.err.println("Failed to stop/save JFR: " + e.getMessage());
        }
    }

    private long getAllocatedBytes() {
        long totalAllocated = 0;
        java.lang.management.ThreadMXBean baseThreadBean = ManagementFactory.getThreadMXBean();
        try {
            // Use reflection to access com.sun.management.ThreadMXBean methods without direct dependency
            Class<?> sunBeanClass = Class.forName("com.sun.management.ThreadMXBean");
            if (sunBeanClass.isInstance(baseThreadBean)) {
                java.lang.reflect.Method isSupported = sunBeanClass.getMethod("isThreadAllocatedMemorySupported");
                if ((boolean) isSupported.invoke(baseThreadBean)) {
                    java.lang.reflect.Method setEnabled = sunBeanClass.getMethod("setThreadAllocatedMemoryEnabled", boolean.class);
                    setEnabled.invoke(baseThreadBean, true);
                    java.lang.reflect.Method getAllocated = sunBeanClass.getMethod("getThreadAllocatedBytes", long.class);
                    long[] threadIds = baseThreadBean.getAllThreadIds();
                    for (long id : threadIds) {
                        long bytes = (long) getAllocated.invoke(baseThreadBean, id);
                        if (bytes != -1) {
                            totalAllocated += bytes;
                        }
                    }
                }
            }
        } catch (Exception e) {
            // Ignore reflection errors and just report 0
        }
        return totalAllocated;
    }
}
