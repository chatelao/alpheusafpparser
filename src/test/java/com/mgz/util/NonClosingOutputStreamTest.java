package com.mgz.util;

import org.junit.jupiter.api.Test;
import java.io.IOException;
import java.io.OutputStream;
import java.util.concurrent.atomic.AtomicBoolean;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class NonClosingOutputStreamTest {

  @Test
  void testCloseDoesNotCloseUnderlyingStream() throws IOException {
    AtomicBoolean closed = new AtomicBoolean(false);
    OutputStream underlying = new OutputStream() {
      @Override
      public void write(int b) throws IOException {}
      @Override
      public void close() throws IOException {
        closed.set(true);
      }
    };

    NonClosingOutputStream ncos = new NonClosingOutputStream(underlying);
    ncos.close();

    assertFalse(closed.get(), "Underlying stream should NOT be closed");
  }

  @Test
  void testWriteDelegatesToUnderlyingStream() throws IOException {
    AtomicBoolean written = new AtomicBoolean(false);
    OutputStream underlying = new OutputStream() {
      @Override
      public void write(int b) throws IOException {
        written.set(true);
      }
    };

    NonClosingOutputStream ncos = new NonClosingOutputStream(underlying);
    ncos.write(42);

    assertTrue(written.get(), "Write should be delegated to underlying stream");
  }
}
