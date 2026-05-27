package com.mgz.cli;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.junit.jupiter.api.io.TempDir;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Regression test to ensure bit-for-bit equality between sequential and parallel conversion modes.
 */
public class Afp2XmlRegressionTest {

    @TempDir
    Path tempDir;

    @ParameterizedTest(name = "Comparing {0}")
    @ValueSource(strings = {
        "src/test/resources/afp/ptoca-reference-04/Chapter_1.afp",
        "src/test/resources/afp/afp-goca-reference-03/Chapter_1.afp",
        "src/test/resources/afp/ioca-reference-09/Chapter_1.afp",
        "src/test/resources/afp/bcoca-reference-11/Chapter_1.afp"
    })
    void testSequentialVsParallelEquality(String afpPath) throws Exception {
        File afpFile = new File(afpPath);
        assertTrue(afpFile.exists(), "AFP file should exist: " + afpPath);

        Path seqOutput = tempDir.resolve("seq_" + afpFile.getName() + ".xml");
        Path parOutput = tempDir.resolve("par_" + afpFile.getName() + ".xml");

        // Run sequential mode
        int seqResult = Afp2Xml.execute(new String[]{afpFile.getAbsolutePath(), seqOutput.toString()});
        assertEquals(0, seqResult, "Sequential conversion failed for " + afpPath);

        // Run parallel mode
        int parResult = Afp2Xml.execute(new String[]{"--parallel", afpFile.getAbsolutePath(), parOutput.toString()});
        assertEquals(0, parResult, "Parallel conversion failed for " + afpPath);

        assertTrue(Files.exists(seqOutput), "Sequential output should exist");
        assertTrue(Files.exists(parOutput), "Parallel output should exist");

        // Bit-for-bit comparison
        byte[] seqBytes = Files.readAllBytes(seqOutput);
        byte[] parBytes = Files.readAllBytes(parOutput);

        assertArrayEquals(seqBytes, parBytes, "XML output mismatch for " + afpPath);
    }
}
