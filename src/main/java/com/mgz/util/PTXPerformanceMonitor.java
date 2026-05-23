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

package com.mgz.util;

import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Utility to track detailed PTX and PTOCA performance statistics.
 */
public class PTXPerformanceMonitor {

  private static volatile boolean enabled = false;

  private static final AtomicLong totalPtxCount = new AtomicLong();
  private static final AtomicLong totalPtxParseTime = new AtomicLong();
  private static final AtomicLong totalPtxWriteTime = new AtomicLong();
  private static final AtomicLong totalPtxPayloadSize = new AtomicLong();
  private static final AtomicLong totalPtxControlSequences = new AtomicLong();

  private static final Map<String, AtomicLong> ptocaFunctionCounts = new ConcurrentHashMap<>();
  private static final Map<String, AtomicLong> ptocaFunctionParseTimes = new ConcurrentHashMap<>();
  private static final Map<String, AtomicLong> ptocaFunctionWriteTimes = new ConcurrentHashMap<>();

  public static void setEnabled(boolean enabled) {
    PTXPerformanceMonitor.enabled = enabled;
  }

  public static boolean isEnabled() {
    return enabled;
  }

  public static void recordPtxParse(long durationNs, int payloadSize, int csCount) {
    if (!enabled) return;
    totalPtxCount.incrementAndGet();
    totalPtxParseTime.addAndGet(durationNs);
    totalPtxPayloadSize.addAndGet(payloadSize);
    totalPtxControlSequences.addAndGet(csCount);
  }

  public static void recordPtxWrite(long durationNs) {
    if (!enabled) return;
    totalPtxWriteTime.addAndGet(durationNs);
  }

  public static void recordPtocaParse(String functionName, long durationNs) {
    if (!enabled) return;
    ptocaFunctionCounts.computeIfAbsent(functionName, k -> new AtomicLong()).incrementAndGet();
    ptocaFunctionParseTimes.computeIfAbsent(functionName, k -> new AtomicLong()).addAndGet(durationNs);
  }

  public static void recordPtocaWrite(String functionName, long durationNs) {
    if (!enabled) return;
    ptocaFunctionWriteTimes.computeIfAbsent(functionName, k -> new AtomicLong()).addAndGet(durationNs);
  }

  public static void printSummary() {
    if (totalPtxCount.get() == 0) {
      System.out.println("No PTX statistics collected.");
      return;
    }

    long count = totalPtxCount.get();
    System.out.println("\n### PTX Debug Performance Summary");
    System.out.println();
    System.out.println("| Metric | Value |");
    System.out.println("| :--- | ---: |");
    System.out.println(String.format("| Total PTX Fields | %d |", count));
    System.out.println(String.format("| Total Parse Time | %d ms |", totalPtxParseTime.get() / 1_000_000));
    System.out.println(String.format("| Total Write Time | %d ms |", totalPtxWriteTime.get() / 1_000_000));
    System.out.println(String.format("| Total Payload Size | %d bytes |", totalPtxPayloadSize.get()));
    System.out.println(String.format("| Total Ctrl Sequences | %d |", totalPtxControlSequences.get()));
    System.out.println(String.format("| Avg CS per PTX | %.2f |", (double) totalPtxControlSequences.get() / count));
    System.out.println(String.format("| Avg Payload per PTX | %.2f bytes |", (double) totalPtxPayloadSize.get() / count));

    if (!ptocaFunctionCounts.isEmpty()) {
      System.out.println("\n#### PTOCA Function Breakdown");
      System.out.println();
      System.out.println(String.format("| %-40s | %10s | %15s | %15s |", "Function", "Count", "Parse (ms)", "Write (ms)"));
      System.out.println("| :--- | ---: | ---: | ---: |");
      new TreeMap<>(ptocaFunctionCounts).forEach((name, fCount) -> {
        long pTime = ptocaFunctionParseTimes.getOrDefault(name, new AtomicLong()).get();
        long wTime = ptocaFunctionWriteTimes.getOrDefault(name, new AtomicLong()).get();
        System.out.println(String.format("| %-40s | %10d | %15d | %15d |", name, fCount.get(), pTime / 1_000_000, wTime / 1_000_000));
      });
    }
    System.out.println();
  }
}
