package com.mgz.acceptance;

import com.mgz.cli.Afp2Xml;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.PrintStream;
import java.nio.file.Files;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class CLITest {

    @Test
    public void testCLIDirectoryMode() throws Exception {
        File tempDir = new File("build/test-dir");
        if (tempDir.exists()) {
            File[] files = tempDir.listFiles();
            if (files != null) {
                for (File f : files) f.delete();
            }
            tempDir.delete();
        }
        tempDir.mkdirs();

        File afpFile1 = new File(tempDir, "test1.afp");
        File afpFile2 = new File(tempDir, "test2.AFP"); // test case insensitivity
        Files.copy(new File("src/test/resources/afp/minimal.afp").toPath(), afpFile1.toPath());
        Files.copy(new File("src/test/resources/afp/minimal.afp").toPath(), afpFile2.toPath());

        Afp2Xml.run(new String[]{"-d", tempDir.getAbsolutePath()}, System.out, System.err);

        File xmlFile1 = new File(tempDir, "test1.afp.xml");
        File xmlFile2 = new File(tempDir, "test2.AFP.xml");

        assertTrue(xmlFile1.exists(), "XML file 1 should exist");
        assertTrue(xmlFile2.exists(), "XML file 2 should exist");

        assertTrue(Files.readString(xmlFile1.toPath()).contains("<AFPDocument>"), "XML 1 should contain AFPDocument tag");
        assertTrue(Files.readString(xmlFile2.toPath()).contains("<AFPDocument>"), "XML 2 should contain AFPDocument tag");
    }

    @Test
    public void testCLIMain() throws Exception {
        File inputFile = new File("src/test/resources/afp/minimal.afp");
        File outputFile = new File("build/test-output.xml");
        outputFile.getParentFile().mkdirs();

        if (outputFile.exists()) {
            outputFile.delete();
        }

        Afp2Xml.run(new String[]{inputFile.getAbsolutePath(), outputFile.getAbsolutePath()}, System.out, System.err);

        assertTrue(outputFile.exists(), "Output XML file should exist");

        List<String> lines = Files.readAllLines(outputFile.toPath());
        boolean foundXmlHeader = false;
        boolean foundAfpDocTag = false;

        for (String line : lines) {
            if (line.contains("<?xml version=\"1.0\"")) {
                foundXmlHeader = true;
            }
            if (line.contains("<AFPDocument>")) {
                foundAfpDocTag = true;
            }
        }

        assertTrue(foundXmlHeader, "XML header not found in output");
        assertTrue(foundAfpDocTag, "AFPDocument tag not found in output");
    }

    @Test
    public void testCLIHelp() {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        ByteArrayOutputStream err = new ByteArrayOutputStream();
        PrintStream outPS = new PrintStream(out);
        PrintStream errPS = new PrintStream(err);

        int exitCode = Afp2Xml.run(new String[]{}, outPS, errPS);
        assertEquals(0, exitCode);
        assertTrue(out.toString().contains("Usage: java -jar alpheus-afp-parser-cli-<version>.jar"), "Usage message should be printed to out");

        out.reset();
        exitCode = Afp2Xml.run(new String[]{"-h"}, outPS, errPS);
        assertEquals(0, exitCode);
        assertTrue(out.toString().contains("Usage: java -jar alpheus-afp-parser-cli-<version>.jar"), "Usage message should be printed to out with -h");

        out.reset();
        exitCode = Afp2Xml.run(new String[]{"--help"}, outPS, errPS);
        assertEquals(0, exitCode);
        assertTrue(out.toString().contains("Usage: java -jar alpheus-afp-parser-cli-<version>.jar"), "Usage message should be printed to out with --help");
    }
}
