package com.mgz.acceptance;

import com.mgz.cli.AFP2XML;
import org.junit.Test;

import java.io.File;
import java.nio.file.Files;
import java.util.List;

import static org.junit.Assert.assertTrue;

public class CLITest {

    @Test
    public void testCLIMain() throws Exception {
        File inputFile = new File("src/test/resources/afp/minimal.afp");
        File outputFile = new File("build/test-output.xml");
        outputFile.getParentFile().mkdirs();

        if (outputFile.exists()) {
            outputFile.delete();
        }

        AFP2XML.main(new String[]{inputFile.getAbsolutePath(), outputFile.getAbsolutePath()});

        assertTrue("Output XML file should exist", outputFile.exists());

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

        assertTrue("XML header not found in output", foundXmlHeader);
        assertTrue("AFPDocument tag not found in output", foundAfpDocTag);
    }
}
