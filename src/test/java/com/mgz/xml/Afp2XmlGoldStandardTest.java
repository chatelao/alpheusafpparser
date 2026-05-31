package com.mgz.xml;

import com.mgz.afp.base.StructuredField;
import com.mgz.afp.parser.AFPParser;
import com.mgz.afp.parser.AFPParserConfiguration;
import org.junit.jupiter.api.Test;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class Afp2XmlGoldStandardTest {

    @Test
    void testMinimalAfp() throws Exception {
        runGoldStandardTest("src/test/resources/afp/minimal.afp", "src/test/resources/gold_standard/minimal.xml");
    }

    @Test
    void testChapter4Afp() throws Exception {
        runGoldStandardTest("src/test/resources/afp/afp-goca-reference-03/Chapter_4.afp", "src/test/resources/gold_standard/Chapter_4.xml");
    }

    @Test
    void testAfplibStartAfp() throws Exception {
        runGoldStandardTest("src/test/resources/afp/external/afplib_start.afp", "src/test/resources/gold_standard/afplib_start.xml");
    }

    private void runGoldStandardTest(String afpPath, String goldStandardPath) throws Exception {
        File afpFile = new File(afpPath);
        assertTrue(afpFile.exists(), "AFP file not found: " + afpPath);

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        AFPParserConfiguration config = new AFPParserConfiguration();
        config.setAFPFile(afpFile);
        AFPParser parser = new AFPParser(config);
        try (AfpJacksonXmlWriter writer = new AfpJacksonXmlWriter(out)) {
            StructuredField sf;
            while ((sf = parser.parseNextSF()) != null) {
                writer.writeField(sf);
                sf.release();
            }
        } finally {
            parser.quitParsing();
        }

        String actualXml = out.toString(StandardCharsets.UTF_8).trim();
        File goldFile = new File(goldStandardPath);

        if (!goldFile.exists()) {
            throw new RuntimeException("Gold standard file not found: " + goldStandardPath);
        }

        String expectedXml = Files.readString(goldFile.toPath(), StandardCharsets.UTF_8).trim();

        // Normalize line endings
        actualXml = actualXml.replace("\r\n", "\n");
        expectedXml = expectedXml.replace("\r\n", "\n");

        if (!expectedXml.equals(actualXml)) {
            // Write actual to a temp file for easier comparison
            File actualFile = new File(goldStandardPath + ".actual");
            Files.writeString(actualFile.toPath(), actualXml, StandardCharsets.UTF_8);
            System.err.println("Gold standard mismatch! Actual output written to: " + actualFile.getAbsolutePath());
        }
        assertEquals(expectedXml, actualXml, "XML output for " + afpPath + " does not match gold standard " + goldStandardPath);
    }
}
