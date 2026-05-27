package com.mgz.performance;

import com.mgz.afp.base.StructuredField;
import com.mgz.afp.enums.SFTypeID;
import com.mgz.afp.parser.AFPParser;
import com.mgz.afp.parser.AFPParserConfiguration;
import com.mgz.afp.parser.ParallelAfpConverter;
import com.mgz.util.MnemonicPerformanceMonitor;
import com.mgz.xml.AfpJacksonXmlWriter;
import com.mgz.xml.XmlHandlerFactory;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

import static org.junit.jupiter.api.Assertions.assertTrue;

@Execution(ExecutionMode.SAME_THREAD)
public class PerformanceRegressionTest {

    @Test
    public void testJackson10MBConversionPerformance() throws Exception {
        File tempAfp = File.createTempFile("perf_test_jackson", ".afp");
        tempAfp.deleteOnExit();

        generateLargeAfp(tempAfp, 10);

        long startTime = System.currentTimeMillis();

        AFPParserConfiguration config = new AFPParserConfiguration();
        config.setAFPFile(tempAfp);
        AFPParser parser = new AFPParser(config);

        ByteArrayOutputStream bos = new ByteArrayOutputStream(1024 * 1024);
        try (AfpJacksonXmlWriter writer = new AfpJacksonXmlWriter(bos, null)) {
            StructuredField sf;
            while ((sf = parser.parseNextSF()) != null) {
                writer.writeField(sf);
                sf.release();
            }
        } finally {
            parser.quitParsing();
        }

        long duration = System.currentTimeMillis() - startTime;
        System.out.println("10MB AFP to Jackson XML (Seq) took: " + duration + "ms");

        assertTrue(duration < 2000, "Performance regression: 10MB Jackson conversion took too long: " + duration + "ms");

        // Parallel 10MB
        long startPar = System.currentTimeMillis();
        AFPParserConfiguration parConfig = new AFPParserConfiguration();
        parConfig.setAFPFile(tempAfp);
        ParallelAfpConverter converter = new ParallelAfpConverter(parConfig, 0, new XmlHandlerFactory());
        converter.convert(new ByteArrayOutputStream());
        long durationPar = System.currentTimeMillis() - startPar;
        System.out.println("10MB AFP to Jackson XML (Parallel) took: " + durationPar + "ms");
    }

    @Test
    public void test100MBParallelPerformance() throws Exception {
        File tempAfp = File.createTempFile("perf_test_100mb", ".afp");
        tempAfp.deleteOnExit();

        System.out.println("Generating 100MB AFP...");
        generateLargeAfp(tempAfp, 100);

        // Warm up
        for (int i = 0; i < 2; i++) {
            runConversion(tempAfp);
            AFPParserConfiguration config = new AFPParserConfiguration();
            config.setAFPFile(tempAfp);
            ParallelAfpConverter converter = new ParallelAfpConverter(config, 0, new XmlHandlerFactory());
            converter.convert(new ByteArrayOutputStream(1024*1024));
        }

        // Sequential Jackson
        long startJackson = System.currentTimeMillis();
        runConversion(tempAfp);
        long durationJackson = System.currentTimeMillis() - startJackson;
        System.out.println("100MB AFP to Jackson (Seq) took: " + durationJackson + "ms");

        // Parallel Jackson
        long startParallel = System.currentTimeMillis();
        AFPParserConfiguration config = new AFPParserConfiguration();
        config.setAFPFile(tempAfp);
        ParallelAfpConverter converter = new ParallelAfpConverter(config, 0, new XmlHandlerFactory());
        ByteArrayOutputStream bos = new ByteArrayOutputStream(20 * 1024 * 1024);
        converter.convert(bos);
        long durationParallel = System.currentTimeMillis() - startParallel;
        System.out.println("100MB AFP to Jackson (Parallel) took: " + durationParallel + "ms");

        if (durationParallel < durationJackson) {
            System.out.printf("Parallel is %.2fx faster than Sequential Jackson\n", (double) durationJackson / durationParallel);
        } else {
            System.out.printf("Parallel is %.2fx slower than Sequential Jackson\n", (double) durationParallel / durationJackson);
        }
    }

    private static class ConversionResult {
        long totalTime;
        int fieldCount = 0;
        int errorCount = 0;
        Map<String, AtomicLong> fieldTimings = new HashMap<>();
    }

    private ConversionResult runConversion(File afpFile) throws Exception {
        AFPParserConfiguration config = new AFPParserConfiguration();
        config.setAFPFile(afpFile);
        config.setEscalateParsingErrors(false);

        ConversionResult result = new ConversionResult();
        long startTime = System.currentTimeMillis();
        AFPParser parser = new AFPParser(config);
        ByteArrayOutputStream bos = new ByteArrayOutputStream(1024 * 1024);

        try (AfpJacksonXmlWriter writer = new AfpJacksonXmlWriter(bos, null)) {
            StructuredField sf;
            while ((sf = parser.parseNextSF()) != null) {
                result.fieldCount++;
                String className = sf.getClass().getSimpleName();
                long startField = System.nanoTime();
                try {
                    writer.writeField(sf);
                } catch (Throwable e) {
                    result.errorCount++;
                }
                long endField = System.nanoTime();
                result.fieldTimings.computeIfAbsent(className, k -> new AtomicLong()).addAndGet(endField - startField);
                sf.release();
            }
        } finally {
            parser.quitParsing();
        }
        result.totalTime = System.currentTimeMillis() - startTime;
        return result;
    }

    private void generateLargeAfp(File output, int sizeMb) throws IOException {
        byte[] nameEbcdic = {(byte)0xC4, (byte)0xD6, (byte)0xC3, (byte)0xF0, (byte)0xF0, (byte)0xF0, (byte)0xF0, (byte)0xF1};
        byte[] bdt = {0x5A, 0x00, 0x10, (byte)0xD3, (byte)0xA8, (byte)0xA8, 0x00, 0x00, 0x00};
        byte[] edt = {0x5A, 0x00, 0x10, (byte)0xD3, (byte)0xA9, (byte)0xA8, 0x00, 0x00, 0x00};
        byte[] bpg = {0x5A, 0x00, 0x10, (byte)0xD3, (byte)0xA8, (byte)0xAF, 0x00, 0x00, 0x00};
        byte[] epg = {0x5A, 0x00, 0x10, (byte)0xD3, (byte)0xA9, (byte)0xAF, 0x00, 0x00, 0x00};

        byte[] ptxPayload = {
            0x2B, (byte)0xD3, 0x15, (byte)0xDA, // TRN CS, length 21
            (byte)0xC8, (byte)0xC0, (byte)0x93, (byte)0x93, (byte)0x6A, 0x40, // Hällö
            (byte)0xE6, (byte)0x6A, (byte)0x99, (byte)0x93, (byte)0x84, 0x40, // Wörld
            (byte)0xC0, (byte)0x6A, (byte)0xD0, (byte)0xA1, (byte)0x4A, (byte)0xE0, (byte)0x5A // äöüßÄÖÜ
        };
        byte[] ptxHeader = {0x5A, 0x00, (byte)(ptxPayload.length + 8), (byte)0xD3, (byte)0xEE, (byte)0x9B, 0x00, 0x00, 0x00};

        byte[] basePayload = "This is a NOP field to fill space and test processing speed. ".getBytes();
        byte[] nopPayload = new byte[32000];
        for (int i = 0; i < nopPayload.length; i++) {
            nopPayload[i] = basePayload[i % basePayload.length];
        }
        byte[] nopHeader = {0x5A, 0x7D, 0x08, (byte)0xD3, (byte)0xEE, (byte)0xEE, 0x00, 0x00, 0x00};

        try (FileOutputStream fos = new FileOutputStream(output)) {
            fos.write(bdt);
            fos.write(nameEbcdic);

            int targetSize = sizeMb * 1024 * 1024;
            int currentSize = bdt.length + nameEbcdic.length;

            while (currentSize < targetSize - 2000000) {
                fos.write(bpg);
                fos.write(nameEbcdic);
                currentSize += bpg.length + nameEbcdic.length;

                // 30 NOPs of 32KB = ~1MB
                for (int j = 0; j < 30; j++) {
                    fos.write(nopHeader);
                    fos.write(nopPayload);
                    currentSize += nopHeader.length + nopPayload.length;
                }

                // Some PTX
                for (int j = 0; j < 100; j++) {
                    fos.write(ptxHeader);
                    fos.write(ptxPayload);
                    currentSize += ptxHeader.length + ptxPayload.length;
                }

                fos.write(epg);
                fos.write(nameEbcdic);
                currentSize += epg.length + nameEbcdic.length;
            }

            fos.write(edt);
            fos.write(nameEbcdic);
        }
    }
}
