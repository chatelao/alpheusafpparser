package com.mgz.util;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import java.nio.file.Files;
import java.nio.file.Path;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class HeapDumpUtilTest {

    @TempDir
    Path tempDir;

    @Test
    public void testDumpHeap() throws Exception {
        Path dumpFile = tempDir.resolve("test.hprof");
        HeapDumpUtil.dumpHeap(dumpFile.toString(), true);

        assertTrue(Files.exists(dumpFile), "Heap dump file should exist");
        assertTrue(Files.size(dumpFile) > 0, "Heap dump file should not be empty");
    }
}
