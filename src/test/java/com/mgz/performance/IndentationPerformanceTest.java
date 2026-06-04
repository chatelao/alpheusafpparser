package com.mgz.performance;

import com.mgz.afp.base.StructuredField;
import com.mgz.afp.parser.AFPParser;
import com.mgz.afp.parser.AFPParserConfiguration;
import com.mgz.xml.AfpJacksonXmlWriter;
import org.junit.jupiter.api.Test;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class IndentationPerformanceTest {

    @Test
    public void testIndentationPerformanceGain() throws Exception {
        File tempAfp = File.createTempFile("perf_test_indent", ".afp");
        tempAfp.deleteOnExit();

        int sizeMb = 10;
        generateLargeAfp(tempAfp, sizeMb);

        // Warm up
        for (int i = 0; i < 3; i++) {
            runConversion(tempAfp, false);
            runConversion(tempAfp, true);
        }

        long startCompact = System.currentTimeMillis();
        runConversion(tempAfp, false);
        long durationCompact = System.currentTimeMillis() - startCompact;
        System.out.println("Compact XML (10MB) took: " + durationCompact + "ms");

        long startIndented = System.currentTimeMillis();
        runConversion(tempAfp, true);
        long durationIndented = System.currentTimeMillis() - startIndented;
        System.out.println("Indented XML (10MB) took: " + durationIndented + "ms");

        System.out.printf("Compact is %.2fx faster than Indented XML\n", (double) durationIndented / durationCompact);

        // We expect compact to be faster or at least not significantly slower
        // Usually it should be faster because it avoids writing many spaces and newlines
        // and also avoids manual indentation logic.
    }

    private void runConversion(File afpFile, boolean indent) throws Exception {
        AFPParserConfiguration config = new AFPParserConfiguration();
        config.setAFPFile(afpFile);
        AFPParser parser = new AFPParser(config);

        // Use a NullOutputStream to focus on processing/writing overhead, not I/O
        OutputStream os = new OutputStream() {
            @Override
            public void write(int b) {}
            @Override
            public void write(byte[] b, int off, int len) {}
        };

        try (AfpJacksonXmlWriter writer = new AfpJacksonXmlWriter(os, null, false, false, indent)) {
            StructuredField sf;
            while ((sf = parser.parseNextSF()) != null) {
                writer.handle(sf);
                sf.release();
            }
        } finally {
            parser.quitParsing();
        }
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

                for (int j = 0; j < 30; j++) {
                    fos.write(nopHeader);
                    fos.write(nopPayload);
                    currentSize += nopHeader.length + nopPayload.length;
                }

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
