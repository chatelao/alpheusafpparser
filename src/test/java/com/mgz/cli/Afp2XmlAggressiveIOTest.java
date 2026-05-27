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
    void testAggressiveIONonParallelConversion() throws Exception {
        File afpFile = new File("src/test/resources/afp/afp-goca-reference-03/Chapter_4.afp");
        if (!afpFile.exists()) return;

        Path aggOutput = tempDir.resolve("agg_seq.xml");

        // Aggressive I/O WITHOUT Parallel run
        int exitCode = Afp2Xml.execute(new String[]{"--aggressive-io", afpFile.getAbsolutePath(), aggOutput.toString()});

        assertEquals(0, exitCode, "Conversion should succeed");
        assertTrue(Files.exists(aggOutput), "Aggressive output should exist");
    }

    @Test
    void testAggressiveIODirectoryModeSequential() throws Exception {
        File afpDir = new File("src/test/resources/afp/afp-goca-reference-03/");
        if (!afpDir.exists() || !afpDir.isDirectory()) return;

        Path outDir = tempDir.resolve("out_dir_agg_seq");
        Files.createDirectories(outDir);

        // Aggressive I/O, Directory Mode, but NO Parallel
        int exitCode = Afp2Xml.execute(new String[]{"--aggressive-io", "-d", afpDir.getAbsolutePath(), outDir.toString()});

        assertEquals(0, exitCode, "Conversion should succeed");
        File[] files = outDir.toFile().listFiles((dir, name) -> name.endsWith(".xml"));
        assertTrue(files != null && files.length > 0, "Should have generated XML files");
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
