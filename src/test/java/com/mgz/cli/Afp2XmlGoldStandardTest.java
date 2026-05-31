package com.mgz.cli;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.api.io.TempDir;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.stream.Stream;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Gold standard regression test to ensure output matches established reference XMLs.
 */
public class Afp2XmlGoldStandardTest {

    @TempDir
    Path tempDir;

    private static Stream<Arguments> provideAfpGoldStandardPairs() {
        return Stream.of(
            Arguments.of("src/test/resources/afp/ptoca-reference-04/Chapter_1.afp", "src/test/resources/xml/gold/ptoca_ch1.xml"),
            Arguments.of("src/test/resources/afp/ptoca-reference-04/Chapter_6.afp", "src/test/resources/xml/gold/ptoca_ch6.xml"),
            Arguments.of("src/test/resources/afp/afp-goca-reference-03/Chapter_1.afp", "src/test/resources/xml/gold/goca_ch1.xml"),
            Arguments.of("src/test/resources/afp/afp-goca-reference-03/Chapter_6.afp", "src/test/resources/xml/gold/goca_ch6.xml"),
            Arguments.of("src/test/resources/afp/ioca-reference-09/Chapter_1.afp", "src/test/resources/xml/gold/ioca_ch1.xml"),
            Arguments.of("src/test/resources/afp/ioca-reference-09/Chapter_6.afp", "src/test/resources/xml/gold/ioca_ch6.xml"),
            Arguments.of("src/test/resources/afp/bcoca-reference-11/Chapter_1.afp", "src/test/resources/xml/gold/bcoca_ch1.xml"),
            Arguments.of("src/test/resources/afp/bcoca-reference-11/Chapter_6.afp", "src/test/resources/xml/gold/bcoca_ch6.xml"),
            Arguments.of("src/test/resources/afp/modca-reference-10/Chapter_1.afp", "src/test/resources/xml/gold/modca_ch1.xml"),
            Arguments.of("src/test/resources/afp/modca-reference-10/Chapter_6.afp", "src/test/resources/xml/gold/modca_ch6.xml"),
            Arguments.of("src/test/resources/afp/minimal.afp", "src/test/resources/xml/gold/minimal.xml")
        );
    }

    @ParameterizedTest(name = "Comparing {0} against {1}")
    @MethodSource("provideAfpGoldStandardPairs")
    void testOutputAgainstGoldStandard(String afpPath, String goldPath) throws Exception {
        File afpFile = new File(afpPath);
        File goldFile = new File(goldPath);
        assertTrue(afpFile.exists(), "AFP file should exist: " + afpPath);
        assertTrue(goldFile.exists(), "Gold standard file should exist: " + goldPath);

        byte[] goldBytes = Files.readAllBytes(goldFile.toPath());

        // Test Sequential Mode
        Path seqOutput = tempDir.resolve("seq_" + afpFile.getName() + ".xml");
        int seqResult = Afp2Xml.execute(new String[]{afpFile.getAbsolutePath(), seqOutput.toString()});
        assertEquals(0, seqResult, "Sequential conversion failed for " + afpPath);
        assertArrayEquals(goldBytes, Files.readAllBytes(seqOutput), "Sequential XML output mismatch for " + afpPath);

        // Test Parallel Mode
        Path parOutput = tempDir.resolve("par_" + afpFile.getName() + ".xml");
        int parResult = Afp2Xml.execute(new String[]{"--parallel", afpFile.getAbsolutePath(), parOutput.toString()});
        assertEquals(0, parResult, "Parallel conversion failed for " + afpPath);
        assertArrayEquals(goldBytes, Files.readAllBytes(parOutput), "Parallel XML output mismatch for " + afpPath);
    }
}
