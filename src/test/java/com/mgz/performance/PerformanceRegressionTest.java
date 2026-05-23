package com.mgz.performance;

import com.mgz.afp.base.StructuredField;
import com.mgz.afp.parser.AFPParser;
import com.mgz.afp.parser.AFPParserConfiguration;
import com.mgz.xml.AfpStreamingXmlWriter;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class PerformanceRegressionTest {

    @Test
    public void test10MBConversionPerformance() throws Exception {
        File tempAfp = File.createTempFile("perf_test", ".afp");
        tempAfp.deleteOnExit();

        generateLargeAfp(tempAfp, 10);

        long startTime = System.currentTimeMillis();

        AFPParserConfiguration config = new AFPParserConfiguration();
        config.setAFPFile(tempAfp);
        AFPParser parser = new AFPParser(config);

        // Use a null-like stream to avoid I/O bottlenecks and focus on processing
        ByteArrayOutputStream bos = new ByteArrayOutputStream(1024 * 1024);
        try (AfpStreamingXmlWriter writer = new AfpStreamingXmlWriter(bos, null)) {
            StructuredField sf;
            while ((sf = parser.parseNextSF()) != null) {
                writer.writeField(sf);
                sf.release();
            }
        } finally {
            parser.quitParsing();
        }

        long duration = System.currentTimeMillis() - startTime;
        System.out.println("10MB AFP to XML conversion took: " + duration + "ms");

        // Assert it takes less than 2 seconds (2000ms)
        assertTrue(duration < 2000, "Performance regression: 10MB conversion took too long: " + duration + "ms");
    }

    private void generateLargeAfp(File output, int sizeMb) throws IOException {
        byte[] nameEbcdic = {(byte)0xC4, (byte)0xD6, (byte)0xC3, (byte)0xF0, (byte)0xF0, (byte)0xF0, (byte)0xF0, (byte)0xF1};
        byte[] bdt = {0x5A, 0x00, 0x10, (byte)0xD3, (byte)0xA8, (byte)0xA8, 0x00, 0x00, 0x00};
        byte[] edt = {0x5A, 0x00, 0x10, (byte)0xD3, (byte)0xA9, (byte)0xA8, 0x00, 0x00, 0x00};

        // PTX with German text (CP273/IBM273)
        // "Hällö Wörld äöüßÄÖÜ"
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

            // Add some PTX fields first
            for (int i = 0; i < 100; i++) {
                fos.write(ptxHeader);
                fos.write(ptxPayload);
            }

            int nopSize = nopHeader.length + nopPayload.length;
            int remainingSize = (sizeMb * 1024 * 1024) - (int)fos.getChannel().position() - edt.length - nameEbcdic.length;
            int numNops = remainingSize / nopSize;
            for (int i = 0; i < numNops; i++) {
                fos.write(nopHeader);
                fos.write(nopPayload);
            }
            fos.write(edt);
            fos.write(nameEbcdic);
        }
    }
}
