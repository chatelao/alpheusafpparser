package com.mgz.xml;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.File;
import java.io.FileOutputStream;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

public class VectorizedWriteTest {

  @TempDir
  File tempDir;

  @Test
  public void testOrderedResultCollectorVectorized() throws Exception {
    File outputFile = new File(tempDir, "collector_vectorized.xml");
    try (FileOutputStream fos = new FileOutputStream(outputFile)) {
      OrderedResultCollector collector = new OrderedResultCollector(fos, fos.getChannel());

      collector.put(1, "Fragment 2\n".getBytes(StandardCharsets.UTF_8));
      collector.put(0, "Fragment 1\n".getBytes(StandardCharsets.UTF_8));
      collector.put(2, ByteBuffer.wrap("Fragment 3\n".getBytes(StandardCharsets.UTF_8)));
    }

    String content = Files.readString(outputFile.toPath());
    assertEquals("Fragment 1\nFragment 2\nFragment 3\n", content);
  }

  @Test
  public void testOrderedOutputOrchestratorVectorized() throws Exception {
    File outputFile = new File(tempDir, "orchestrator_vectorized.xml");
    try (FileOutputStream fos = new FileOutputStream(outputFile)) {
      OrderedOutputOrchestrator orchestrator = new OrderedOutputOrchestrator(fos, fos.getChannel());

      int stream1 = orchestrator.registerStream();
      int stream2 = orchestrator.registerStream();

      orchestrator.put(stream2, 0, "File 2 Page 1\n".getBytes(StandardCharsets.UTF_8));
      orchestrator.put(stream1, 1, "File 1 Page 2\n".getBytes(StandardCharsets.UTF_8));
      orchestrator.put(stream1, 0, "File 1 Page 1\n".getBytes(StandardCharsets.UTF_8));

      orchestrator.finishStream(stream1);
      orchestrator.finishStream(stream2);
    }

    String content = Files.readString(outputFile.toPath());
    assertEquals("File 1 Page 1\nFile 1 Page 2\nFile 2 Page 1\n", content);
  }
}
