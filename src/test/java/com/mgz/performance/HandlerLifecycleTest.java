package com.mgz.performance;

import com.mgz.afp.base.StructuredField;
import com.mgz.afp.base.handler.HandlerFactory;
import com.mgz.afp.base.handler.StructuredFieldHandler;
import com.mgz.afp.parser.AFPParserConfiguration;
import com.mgz.afp.parser.ParallelAfpConverter;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.nio.file.Path;
import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Verifies that the handler lifecycle is correctly managed by the parser and orchestrators.
 * Specifically checks that every handler created is closed exactly once.
 */
public class HandlerLifecycleTest {

    @TempDir
    Path tempDir;

    private final AtomicInteger createdCount = new AtomicInteger(0);
    private final AtomicInteger closedCount = new AtomicInteger(0);

    private class MockHandler implements StructuredFieldHandler {
        private boolean closed = false;

        MockHandler() {
            createdCount.incrementAndGet();
        }

        @Override
        public void handle(StructuredField sf) throws Exception {
            // No-op
        }

        @Override
        public void close() throws Exception {
            if (!closed) {
                closed = true;
                closedCount.incrementAndGet();
            }
        }
    }

    private class MockHandlerFactory implements HandlerFactory {
        @Override
        public StructuredFieldHandler createHandler(OutputStream os, boolean fragmentMode) throws Exception {
            return new MockHandler();
        }

        @Override
        public ByteBuffer stripFragmentWrapper(ByteBuffer data) {
            return data;
        }
    }

    @Test
    public void testParallelHandlerLifecycle() throws Exception {
        createdCount.set(0);
        closedCount.set(0);

        File afpFile = tempDir.resolve("lifecycle_par.afp").toFile();
        generateMultiPageAfp(afpFile, 10);

        AFPParserConfiguration config = new AFPParserConfiguration();
        config.setAFPFile(afpFile);

        // Run with 4 threads
        ParallelAfpConverter converter = new ParallelAfpConverter(config, 4, new MockHandlerFactory());
        converter.convert(new ByteArrayOutputStream());

        // Expected handlers: 1 (preamble) + 10 (one per page task)
        int expected = 1 + 10;
        assertEquals(expected, createdCount.get(), "Unexpected number of handlers created (parallel)");
        assertEquals(expected, closedCount.get(), "Not all handlers were closed (parallel)");
    }

    @Test
    public void testSequentialHandlerLifecycle() throws Exception {
        createdCount.set(0);
        closedCount.set(0);

        File afpFile = tempDir.resolve("lifecycle_seq.afp").toFile();
        generateMultiPageAfp(afpFile, 10);

        AFPParserConfiguration config = new AFPParserConfiguration();
        config.setAFPFile(afpFile);

        ParallelAfpConverter converter = new ParallelAfpConverter(config, 1, new MockHandlerFactory());
        converter.convert(new ByteArrayOutputStream());

        int expected = 1 + 10;
        assertEquals(expected, createdCount.get(), "Unexpected number of handlers created (sequential)");
        assertEquals(expected, closedCount.get(), "Not all handlers were closed (sequential)");
    }

    private void generateMultiPageAfp(File output, int pageCount) throws IOException {
        byte[] nameEbcdic = {(byte)0xC4, (byte)0xD6, (byte)0xC3, (byte)0xF0, (byte)0xF0, (byte)0xF0, (byte)0xF0, (byte)0xF1};
        byte[] bdt = {0x5A, 0x00, 0x10, (byte)0xD3, (byte)0xA8, (byte)0xA8, 0x00, 0x00, 0x00};
        byte[] edt = {0x5A, 0x00, 0x10, (byte)0xD3, (byte)0xA9, (byte)0xA8, 0x00, 0x00, 0x00};
        byte[] bpg = {0x5A, 0x00, 0x10, (byte)0xD3, (byte)0xA8, (byte)0xAF, 0x00, 0x00, 0x00};
        byte[] epg = {0x5A, 0x00, 0x10, (byte)0xD3, (byte)0xA9, (byte)0xAF, 0x00, 0x00, 0x00};

        try (FileOutputStream fos = new FileOutputStream(output)) {
            fos.write(bdt);
            fos.write(nameEbcdic);

            for (int i = 0; i < pageCount; i++) {
                fos.write(bpg);
                fos.write(nameEbcdic);
                fos.write(epg);
                fos.write(nameEbcdic);
            }

            fos.write(edt);
            fos.write(nameEbcdic);
        }
    }
}
