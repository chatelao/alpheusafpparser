package com.mgz.util;

import com.mgz.afp.parser.AFPParserConfiguration;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class MnemonicPerformanceMonitorCharsetTest {

    private final PrintStream originalOut = System.out;
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();

    @BeforeEach
    public void setUp() {
        System.setOut(new PrintStream(outContent));
        MnemonicPerformanceMonitor.clear();
        MnemonicPerformanceMonitor.setEnabled(true);
    }

    @AfterEach
    public void tearDown() {
        System.setOut(originalOut);
        MnemonicPerformanceMonitor.setEnabled(false);
        MnemonicPerformanceMonitor.clear();
    }

    @Test
    public void testCharsetProfiling() {
        AFPParserConfiguration config = new AFPParserConfiguration();

        // Record default (should be IBM500)
        config.getAfpCharSet();

        // Change and record
        config.setAfpCharSet(StandardCharsets.UTF_16BE);
        config.getAfpCharSet();

        // Change back
        config.setAfpCharSet(Charset.forName("Cp273"));
        config.getAfpCharSet();

        MnemonicPerformanceMonitor.printSummary();

        String output = outContent.toString();
        assertTrue(output.contains("### Charsets Used"), "Output should contain Charsets Used table");
        assertTrue(output.contains("IBM500"), "Output should contain IBM500");
        assertTrue(output.contains("UTF-16BE"), "Output should contain UTF-16BE");
        assertTrue(output.contains("IBM273") || output.contains("Cp273"), "Output should contain Cp273/IBM273");
    }
}
