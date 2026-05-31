package com.mgz.xml;

import com.mgz.afp.base.StructuredField;
import com.mgz.afp.parser.AFPParser;
import com.mgz.afp.parser.AFPParserConfiguration;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.Arrays;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Test to compare current XML output against "Gold Standard" reference files.
 */
public class Afp2XmlGoldStandardTest {

    private static final String AFP_DIR = "perf_test/";
    private static final String GOLD_DIR = "src/test/resources/gold_standards/";

    @Test
    public void testAgainstGoldStandards() throws Exception {
        File afpDir = new File(AFP_DIR);
        File goldDir = new File(GOLD_DIR);

        if (!goldDir.exists()) {
            goldDir.mkdirs();
        }

        File[] afpFiles = afpDir.listFiles((dir, name) -> name.endsWith(".afp"));
        if (afpFiles == null || afpFiles.length == 0) {
            System.err.println("Warning: No AFP files found in " + AFP_DIR);
            return;
        }

        Arrays.sort(afpFiles);

        for (File afpFile : afpFiles) {
            String baseName = afpFile.getName();
            File goldFile = new File(goldDir, baseName + ".xml");

            byte[] currentXml = convertToXml(afpFile);

            if (!goldFile.exists()) {
                // Initial generation of gold standards if they don't exist
                Files.write(goldFile.toPath(), currentXml);
                System.out.println("Generated initial gold standard: " + goldFile.getPath());
            } else {
                byte[] goldXml = Files.readAllBytes(goldFile.toPath());
                String goldStr = new String(goldXml, StandardCharsets.UTF_8).replace("\r\n", "\n");
                String currentStr = new String(currentXml, StandardCharsets.UTF_8).replace("\r\n", "\n");

                assertEquals(goldStr, currentStr, "XML output mismatch for " + baseName);
            }
        }
    }

    private byte[] convertToXml(File inputFile) throws Exception {
        AFPParserConfiguration config = new AFPParserConfiguration();
        config.setAFPFile(inputFile);
        AFPParser parser = new AFPParser(config);

        java.io.ByteArrayOutputStream baos = new java.io.ByteArrayOutputStream();
        try (AfpJacksonXmlWriter writer = new AfpJacksonXmlWriter(baos)) {
            StructuredField sf;
            while ((sf = parser.parseNextSF()) != null) {
                writer.handle(sf);
                sf.release();
            }
        }
        return baos.toByteArray();
    }
}
