package com.mgz.cli;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class Afp2XmlAggressiveIOTest {

    @TempDir
    Path tempDir;

    @Test
    void testAggressiveIOConversion() throws Exception {
        File afpFile = new File("src/test/resources/afp/afp-goca-reference-03/Chapter_4.afp");
        if (!afpFile.exists()) return;

        Path stdOutput = tempDir.resolve("std.xml");
        Path aggOutput = tempDir.resolve("agg.xml");

        // Standard run
        Afp2Xml.execute(new String[]{afpFile.getAbsolutePath(), stdOutput.toString()});

        // Aggressive I/O + Parallel run
        Afp2Xml.execute(new String[]{"--aggressive-io", "--parallel", afpFile.getAbsolutePath(), aggOutput.toString()});

        assertTrue(Files.exists(stdOutput), "Standard output should exist");
        assertTrue(Files.exists(aggOutput), "Aggressive output should exist");

        String stdXml = Files.readString(stdOutput).trim();
        String aggXml = Files.readString(aggOutput).trim();

        assertEquals(stdXml, aggXml, "XML output should be identical with aggressive I/O and parallel mode");
        assertEquals(Files.size(stdOutput), Files.size(aggOutput), "File sizes should be identical (truncation check)");
    }

    @Test
    void testAggressiveIODirectoryToStdout() throws Exception {
        File afpDir = new File("src/test/resources/afp/afp-goca-reference-03/");
        if (!afpDir.exists() || !afpDir.isDirectory()) return;

        PrintStream originalOut = System.out;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try {
            System.setOut(new PrintStream(baos));
            Afp2Xml.execute(new String[]{"--aggressive-io", "--directory", afpDir.getAbsolutePath(), "-"});
        } finally {
            System.setOut(originalOut);
        }

        String output = baos.toString();
        assertTrue(output.contains("<AFPDocument"), "Output should contain AFPDocument");
        assertTrue(output.contains("</AFPDocument>"), "Output should contain closing AFPDocument tag");
        assertTrue(output.length() > 1000, "Output should be of significant size");
    }
}
