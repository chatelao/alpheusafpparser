package com.mgz.performance;

import com.mgz.cli.Afp2Xml;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Path;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Stability and stress test for multi-threaded AFP conversion.
 */
public class Afp2XmlStabilityTest {

    @TempDir
    Path tempDir;

    @Test
    public void test1000PageParallelStability() throws Exception {
        File stressAfp = tempDir.resolve("stress_1000.afp").toFile();
        generateStressAfp(stressAfp, 1000);

        File outputXml = tempDir.resolve("stress_1000.xml").toFile();

        // Run conversion in parallel mode
        int result = Afp2Xml.execute(new String[]{"--parallel", stressAfp.getAbsolutePath(), outputXml.getAbsolutePath()});

        assertEquals(0, result, "Parallel conversion of 1,000 page AFP failed");
    }

    private void generateStressAfp(File output, int pageCount) throws IOException {
        byte[] nameEbcdic = {(byte)0xC4, (byte)0xD6, (byte)0xC3, (byte)0xF0, (byte)0xF0, (byte)0xF0, (byte)0xF0, (byte)0xF1};
        byte[] bdt = {0x5A, 0x00, 0x10, (byte)0xD3, (byte)0xA8, (byte)0xA8, 0x00, 0x00, 0x00};
        byte[] edt = {0x5A, 0x00, 0x10, (byte)0xD3, (byte)0xA9, (byte)0xA8, 0x00, 0x00, 0x00};
        byte[] bpg = {0x5A, 0x00, 0x10, (byte)0xD3, (byte)0xA8, (byte)0xAF, 0x00, 0x00, 0x00};
        byte[] epg = {0x5A, 0x00, 0x10, (byte)0xD3, (byte)0xA9, (byte)0xAF, 0x00, 0x00, 0x00};

        byte[] ptxPayload = {
            0x2B, (byte)0xD3, 0x15, (byte)0xDA,
            (byte)0xC8, (byte)0xC0, (byte)0x93, (byte)0x93, (byte)0x6A, 0x40,
            (byte)0xE6, (byte)0x6A, (byte)0x99, (byte)0x93, (byte)0x84, 0x40,
            (byte)0xC0, (byte)0x6A, (byte)0xD0, (byte)0xA1, (byte)0x4A, (byte)0xE0, (byte)0x5A
        };
        byte[] ptxHeader = {0x5A, 0x00, (byte)(ptxPayload.length + 8), (byte)0xD3, (byte)0xEE, (byte)0x9B, 0x00, 0x00, 0x00};

        try (FileOutputStream fos = new FileOutputStream(output)) {
            fos.write(bdt);
            fos.write(nameEbcdic);

            for (int i = 0; i < pageCount; i++) {
                fos.write(bpg);
                fos.write(nameEbcdic);

                for (int j = 0; j < 10; j++) {
                    fos.write(ptxHeader);
                    fos.write(ptxPayload);
                }

                fos.write(epg);
                fos.write(nameEbcdic);
            }

            fos.write(edt);
            fos.write(nameEbcdic);
        }
    }
}
