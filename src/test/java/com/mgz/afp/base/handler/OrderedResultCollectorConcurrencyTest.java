/*
Copyright 2026 Rudolf Fiala

This file is part of Alpheus AFP Parser.

Alpheus AFP Parser is free software: you can redistribute it and/or modify
it under the terms of the GNU General Public License as published by
the Free Software Foundation, either version 3 of the License, or
(at your option) any later version.

Alpheus AFP Parser is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License
along with Alpheus AFP Parser.  If not, see <http://www.gnu.org/licenses/>
*/

package com.mgz.afp.base.handler;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.mgz.util.SegmentedMappedBufferOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FilterOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

/**
 * Concurrency, lock-splitting non-blocking handoff, and backpressure tests for {@link
 * OrderedResultCollector} and {@link OrderedOutputOrchestrator}.
 */
public class OrderedResultCollectorConcurrencyTest {

  @TempDir
  File tempDir;

  @Test
  public void testConcurrentOutOfOrderOrderingAndCompleteness() throws Exception {
    final int fragmentCount = 400;
    ByteArrayOutputStream baos = new ByteArrayOutputStream();
    OrderedResultCollector collector = new OrderedResultCollector(baos);

    List<Integer> sequences = new ArrayList<>(fragmentCount);
    for (int i = 0; i < fragmentCount; i++) {
      sequences.add(i);
    }
    Collections.shuffle(sequences);

    ExecutorService pool = Executors.newFixedThreadPool(8);
    List<Future<?>> futures = new ArrayList<>();
    for (int seq : sequences) {
      futures.add(
          pool.submit(
              () -> {
                try {
                  collector.put(seq, ("F-" + seq + "\n").getBytes(StandardCharsets.UTF_8));
                } catch (IOException e) {
                  throw new RuntimeException(e);
                }
              }));
    }
    for (Future<?> f : futures) {
      f.get(30, TimeUnit.SECONDS);
    }
    pool.shutdown();
    assertTrue(pool.awaitTermination(10, TimeUnit.SECONDS));
    assertTrue(collector.isComplete());

    StringBuilder expected = new StringBuilder();
    for (int i = 0; i < fragmentCount; i++) {
      expected.append("F-").append(i).append('\n');
    }
    assertEquals(expected.toString(), baos.toString(StandardCharsets.UTF_8));
  }

  @Test
  public void testConcurrentFileChannelVectorizedWrites() throws Exception {
    final int fragmentCount = 250;
    File outFile = new File(tempDir, "concurrent_channel.xml");
    try (FileOutputStream fos = new FileOutputStream(outFile)) {
      OrderedResultCollector collector = new OrderedResultCollector(fos, fos.getChannel());
      List<Integer> sequences = new ArrayList<>(fragmentCount);
      for (int i = 0; i < fragmentCount; i++) {
        sequences.add(i);
      }
      Collections.shuffle(sequences);

      ExecutorService pool = Executors.newFixedThreadPool(8);
      List<Future<?>> futures = new ArrayList<>();
      for (int seq : sequences) {
        futures.add(
            pool.submit(
                () -> {
                  try {
                    collector.put(
                        seq,
                        ByteBuffer.wrap(("C-" + seq + ";").getBytes(StandardCharsets.UTF_8)));
                  } catch (IOException e) {
                    throw new RuntimeException(e);
                  }
                }));
      }
      for (Future<?> f : futures) {
        f.get(30, TimeUnit.SECONDS);
      }
      pool.shutdown();
      assertTrue(collector.isComplete());
    }

    StringBuilder expected = new StringBuilder();
    for (int i = 0; i < fragmentCount; i++) {
      expected.append("C-").append(i).append(';');
    }
    assertEquals(expected.toString(), Files.readString(outFile.toPath(), StandardCharsets.UTF_8));
  }

  @Test
  public void testSegmentedMappedBufferFastPath() throws Exception {
    File outFile = new File(tempDir, "segmented_mapped.bin");
    List<String> payloads = List.of("Alpha|", "Beta|", "Gamma|", "Delta|");
    byte[] expectedBytes = String.join("", payloads).getBytes(StandardCharsets.UTF_8);

    try (RandomAccessFile raf = new RandomAccessFile(outFile, "rw");
        FileChannel fc = raf.getChannel()) {
      SegmentedMappedBufferOutputStream smbos =
          new SegmentedMappedBufferOutputStream(
              (pos, requested) -> {
                long remaining = expectedBytes.length - pos;
                if (remaining <= 0) {
                  return null;
                }
                int size = (int) Math.min(8L, remaining);
                return fc.map(FileChannel.MapMode.READ_WRITE, pos, size);
              },
              8);

      OrderedResultCollector collector = new OrderedResultCollector(smbos);
      collector.put(2, ByteBuffer.wrap(payloads.get(2).getBytes(StandardCharsets.UTF_8)));
      collector.put(0, ByteBuffer.wrap(payloads.get(0).getBytes(StandardCharsets.UTF_8)));
      collector.put(3, ByteBuffer.wrap(payloads.get(3).getBytes(StandardCharsets.UTF_8)));
      collector.put(1, ByteBuffer.wrap(payloads.get(1).getBytes(StandardCharsets.UTF_8)));
      smbos.close();
      raf.setLength(expectedBytes.length);
      assertTrue(collector.isComplete());
    }

    assertEquals(
        new String(expectedBytes, StandardCharsets.UTF_8),
        Files.readString(outFile.toPath(), StandardCharsets.UTF_8));
  }

  @Test
  public void testOutOfOrderProducerDoesNotBlockOnSlowDrainer() throws Exception {
    ByteArrayOutputStream sink = new ByteArrayOutputStream();
    CountDownLatch drainerEnteredWrite = new CountDownLatch(1);
    CountDownLatch allowDrainerToFinish = new CountDownLatch(1);

    OutputStream slowSink =
        new FilterOutputStream(sink) {
          private final AtomicInteger calls = new AtomicInteger();

          @Override
          public void write(byte[] b, int off, int len) throws IOException {
            if (calls.getAndIncrement() == 0) {
              drainerEnteredWrite.countDown();
              try {
                assertTrue(allowDrainerToFinish.await(10, TimeUnit.SECONDS));
              } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                throw new IOException(e);
              }
            }
            out.write(b, off, len);
          }
        };

    OrderedResultCollector collector = new OrderedResultCollector(slowSink);
    Thread drainerThread =
        new Thread(
            () -> {
              try {
                collector.put(0, "ZERO|".getBytes(StandardCharsets.UTF_8));
              } catch (IOException e) {
                throw new RuntimeException(e);
              }
            });
    drainerThread.start();

    assertTrue(drainerEnteredWrite.await(5, TimeUnit.SECONDS));

    // While thread 0 is blocked inside slowSink.write for fragment 0, another worker
    // depositing fragment 1 (in-window) and fragment 2 (out-of-order) MUST return immediately.
    Thread producerThread =
        new Thread(
            () -> {
              try {
                collector.put(2, "TWO|".getBytes(StandardCharsets.UTF_8));
                collector.put(1, "ONE|".getBytes(StandardCharsets.UTF_8));
              } catch (IOException e) {
                throw new RuntimeException(e);
              }
            });
    producerThread.start();
    producerThread.join(5000);
    assertTrue(!producerThread.isAlive(), "Producer should return without blocking on slow sink");

    allowDrainerToFinish.countDown();
    drainerThread.join(5000);
    assertTrue(!drainerThread.isAlive());
    assertTrue(collector.isComplete());
    assertEquals("ZERO|ONE|TWO|", sink.toString(StandardCharsets.UTF_8));
  }

  @Test
  public void testTightBackpressureWithoutDeadlock() throws Exception {
    ByteArrayOutputStream sink = new ByteArrayOutputStream();
    // Tiny maxBufferSize forcing frequent backpressure waits, including fragments larger than limit
    OrderedResultCollector collector = new OrderedResultCollector(sink, null, 24L);

    final int threads = 8;
    final int waves = 20;
    final int fragmentCount = threads * waves;
    ExecutorService pool = Executors.newFixedThreadPool(threads);

    // Run windowed waves across all 8 threads so every wave's sequences arrive in shuffled order,
    // saturating maxBufferSize and triggering backpressure without starving the in-window tasks.
    for (int wave = 0; wave < waves; wave++) {
      List<Integer> waveSeqs = new ArrayList<>(threads);
      for (int t = 0; t < threads; t++) {
        waveSeqs.add(wave * threads + t);
      }
      Collections.shuffle(waveSeqs);
      List<Future<?>> futures = new ArrayList<>(threads);
      for (int seq : waveSeqs) {
        futures.add(
            pool.submit(
                () -> {
                  try {
                    collector.put(
                        seq,
                        ("PAYLOAD-SEQ-" + seq + "|").getBytes(StandardCharsets.UTF_8));
                  } catch (IOException e) {
                    throw new RuntimeException(e);
                  }
                }));
      }
      for (Future<?> f : futures) {
        f.get(15, TimeUnit.SECONDS);
      }
    }

    // Also test a strict reverse-order storm across 12 dedicated threads so 11 out-of-order threads
    // hit backpressure before thread 0 arrives.
    int extraStart = fragmentCount;
    int stormThreads = 12;
    List<Thread> runners = new ArrayList<>(stormThreads);
    CountDownLatch highFragmentsStarted = new CountDownLatch(stormThreads - 1);
    for (int i = stormThreads - 1; i >= 0; i--) {
      final int seq = extraStart + i;
      final boolean isZero = (i == 0);
      Thread t =
          new Thread(
              () -> {
                try {
                  if (isZero) {
                    highFragmentsStarted.await(5, TimeUnit.SECONDS);
                    Thread.sleep(20);
                  } else {
                    highFragmentsStarted.countDown();
                  }
                  collector.put(
                      seq,
                      ("PAYLOAD-SEQ-" + seq + "|").getBytes(StandardCharsets.UTF_8));
                } catch (Exception e) {
                  throw new RuntimeException(e);
                }
              });
      runners.add(t);
      t.start();
    }
    for (Thread t : runners) {
      t.join(15000);
      assertTrue(!t.isAlive(), "Thread stalled in tight backpressure");
    }

    pool.shutdown();
    assertTrue(collector.isComplete());

    StringBuilder expected = new StringBuilder();
    for (int i = 0; i < fragmentCount + stormThreads; i++) {
      expected.append("PAYLOAD-SEQ-").append(i).append('|');
    }
    assertEquals(expected.toString(), sink.toString(StandardCharsets.UTF_8));
  }

  @Test
  public void testOrchestratorConcurrentStreamsWithBackpressure() throws Exception {
    ByteArrayOutputStream sink = new ByteArrayOutputStream();
    OrderedOutputOrchestrator orchestrator = new OrderedOutputOrchestrator(sink, null, 40L);

    final int streamCount = 10;
    final int fragmentsPerStream = 12;
    int[] streamIds = new int[streamCount];
    for (int s = 0; s < streamCount; s++) {
      streamIds[s] = orchestrator.registerStream();
    }

    // One dedicated thread per stream so stream 0 is never starved behind out-of-order streams
    // holding the tiny 40-byte backpressure limit.
    ExecutorService pool = Executors.newFixedThreadPool(streamCount);
    List<Future<?>> futures = new ArrayList<>();
    for (int s = streamCount - 1; s >= 0; s--) {
      final int sid = streamIds[s];
      final int sIdx = s;
      futures.add(
          pool.submit(
              () -> {
                try {
                  // Submit in-order within stream from this thread while all streams race across
                  // threads against tight global backpressure
                  for (int f = 0; f < fragmentsPerStream; f++) {
                    orchestrator.put(
                        sid, f, ("S" + sIdx + "F" + f + ";").getBytes(StandardCharsets.UTF_8));
                  }
                  orchestrator.finishStream(sid);
                } catch (IOException e) {
                  throw new RuntimeException(e);
                }
              }));
    }
    for (Future<?> f : futures) {
      f.get(15, TimeUnit.SECONDS);
    }
    pool.shutdown();

    StringBuilder expected = new StringBuilder();
    for (int s = 0; s < streamCount; s++) {
      for (int f = 0; f < fragmentsPerStream; f++) {
        expected.append("S").append(s).append("F").append(f).append(';');
      }
    }
    assertEquals(expected.toString(), sink.toString(StandardCharsets.UTF_8));
  }
}
