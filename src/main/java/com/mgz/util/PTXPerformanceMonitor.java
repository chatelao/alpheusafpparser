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

import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.LongAdder;

/**
 * Utility to track detailed PTX and PTOCA performance statistics.
 */
public class PTXPerformanceMonitor {

  private static volatile boolean enabled = false;

  private static final LongAdder totalPtxCount = new LongAdder();
  private static final LongAdder totalPtxParseTime = new LongAdder();
  private static final LongAdder totalPtxWriteTime = new LongAdder();
  private static final LongAdder totalPtxXmlSize = new LongAdder();
  private static final LongAdder totalPtxPayloadSize = new LongAdder();
  private static final LongAdder totalPtxControlSequences = new LongAdder();

  private static final Map<String, LongAdder> ptocaFunctionCounts = new ConcurrentHashMap<>();
  private static final Map<String, LongAdder> ptocaFunctionParseTimes = new ConcurrentHashMap<>();
  private static final Map<String, LongAdder> ptocaFunctionWriteTimes = new ConcurrentHashMap<>();
  private static final Map<String, LongAdder> ptocaFunctionMaxWriteTimes = new ConcurrentHashMap<>();
  private static final Map<String, String> ptocaFunctionMaxPayloads = new ConcurrentHashMap<>();
  private static final Map<String, LongAdder> ptocaFunctionPayloadSizes = new ConcurrentHashMap<>();
  private static final Map<String, LongAdder> ptocaFunctionXmlSizes = new ConcurrentHashMap<>();

  private static final ThreadLocal<LocalPtxStats> localStats = ThreadLocal.withInitial(LocalPtxStats::new);

  public static void setEnabled(boolean enabled) {
    PTXPerformanceMonitor.enabled = enabled;
  }

  public static void clear() {
    totalPtxCount.reset();
    totalPtxParseTime.reset();
    totalPtxWriteTime.reset();
    totalPtxXmlSize.reset();
    totalPtxPayloadSize.reset();
    totalPtxControlSequences.reset();
    ptocaFunctionCounts.clear();
    ptocaFunctionParseTimes.clear();
    ptocaFunctionWriteTimes.clear();
    ptocaFunctionMaxWriteTimes.clear();
    ptocaFunctionMaxPayloads.clear();
    ptocaFunctionPayloadSizes.clear();
    ptocaFunctionXmlSizes.clear();
    localStats.remove();
  }

  public static boolean isEnabled() {
    return enabled;
  }

  /**
   * Returns the global PTX expansion ratio (XML size / AFP payload size).
   *
   * @return the expansion ratio, or 0 if no data is available
   */
  public static double getGlobalPtxExpansionRatio() {
    long payload = totalPtxPayloadSize.sum();
    return payload > 0 ? (double) totalPtxXmlSize.sum() / payload : 0;
  }

  /**
   * Returns the global expansion ratio for a specific PTOCA control sequence.
   *
   * @param functionName the simple name of the control sequence class
   * @return the expansion ratio, or 0 if no data is available for this function
   */
  public static double getGlobalPtocaExpansionRatio(String functionName) {
    LongAdder payloadAdder = ptocaFunctionPayloadSizes.get(functionName);
    if (payloadAdder == null) return 0;
    long payload = payloadAdder.sum();
    if (payload <= 0) return 0;
    LongAdder xmlSizeAdder = ptocaFunctionXmlSizes.get(functionName);
    return xmlSizeAdder != null ? (double) xmlSizeAdder.sum() / payload : 0;
  }

  /**
   * Returns true if any PTX data has been collected.
   *
   * @return true if data is available
   */
  public static boolean hasData() {
    return totalPtxCount.sum() > 0;
  }

  public static void recordPtxParse(long durationNs, int payloadSize, int csCount) {
    LocalPtxStats local = localStats.get();
    local.totalPtxCount++;
    local.totalPtxParseTime += durationNs;
    local.totalPtxPayloadSize += payloadSize;
    local.totalPtxControlSequences += csCount;
  }

  public static void recordPtxWrite(long durationNs, long xmlSize) {
    LocalPtxStats local = localStats.get();
    local.totalPtxWriteTime += durationNs;
    local.totalPtxXmlSize += xmlSize;
  }

  public static void recordPtocaParse(String functionName, long durationNs, int payloadSize) {
    LocalPtocaStats stats = localStats.get().ptocaStats.computeIfAbsent(functionName, k -> new LocalPtocaStats());
    stats.count++;
    stats.parseTime += durationNs;
    stats.payloadSize += payloadSize;
  }

  public static void recordPtocaWrite(String functionName, long durationNs, int payloadSize) {
    recordPtocaWrite(functionName, durationNs, payloadSize, 0, null);
  }

  public static void recordPtocaWrite(String functionName, long durationNs, int payloadSize, long xmlSize) {
    recordPtocaWrite(functionName, durationNs, payloadSize, xmlSize, null);
  }

  public static void recordPtocaWrite(String functionName, long durationNs, int payloadSize, byte[] payload) {
    recordPtocaWrite(functionName, durationNs, payloadSize, 0, payload);
  }

  public static void recordPtocaWrite(String functionName, long durationNs, int payloadSize, long xmlSize, byte[] payload) {
    LocalPtocaStats stats = localStats.get().ptocaStats.computeIfAbsent(functionName, k -> new LocalPtocaStats());
    stats.writeTime += durationNs;
    stats.payloadSize += payloadSize;
    stats.xmlSize += xmlSize;
    if (durationNs > stats.maxWriteTime) {
      stats.maxWriteTime = durationNs;
      if (payload != null) {
        stats.maxPayload = UtilCharacterEncoding.bytesToHexString(payload);
      }
    }
  }

  /**
   * Merges local thread statistics into the global counters and clears local state.
   */
  public static void merge() {
    LocalPtxStats local = localStats.get();
    if (local.totalPtxCount == 0 && local.totalPtxWriteTime == 0 && local.ptocaStats.isEmpty()) {
      return;
    }

    totalPtxCount.add(local.totalPtxCount);
    totalPtxParseTime.add(local.totalPtxParseTime);
    totalPtxWriteTime.add(local.totalPtxWriteTime);
    totalPtxXmlSize.add(local.totalPtxXmlSize);
    totalPtxPayloadSize.add(local.totalPtxPayloadSize);
    totalPtxControlSequences.add(local.totalPtxControlSequences);

    local.ptocaStats.forEach((name, stats) -> {
      ptocaFunctionCounts.computeIfAbsent(name, k -> new LongAdder()).add(stats.count);
      ptocaFunctionParseTimes.computeIfAbsent(name, k -> new LongAdder()).add(stats.parseTime);
      ptocaFunctionWriteTimes.computeIfAbsent(name, k -> new LongAdder()).add(stats.writeTime);
      ptocaFunctionPayloadSizes.computeIfAbsent(name, k -> new LongAdder()).add(stats.payloadSize);
      ptocaFunctionXmlSizes.computeIfAbsent(name, k -> new LongAdder()).add(stats.xmlSize);

      ptocaFunctionMaxWriteTimes.compute(name, (k, v) -> {
        if (v == null) {
          v = new LongAdder();
          v.add(stats.maxWriteTime);
          if (stats.maxPayload != null) ptocaFunctionMaxPayloads.put(name, stats.maxPayload);
          return v;
        }
        if (stats.maxWriteTime > v.sum()) {
          v.reset();
          v.add(stats.maxWriteTime);
          if (stats.maxPayload != null) ptocaFunctionMaxPayloads.put(name, stats.maxPayload);
        }
        return v;
      });
    });

    local.reset();
  }

  public static void printSummary() {
    merge();

    if (totalPtxCount.sum() == 0) {
      System.out.println("No PTX statistics collected.");
      return;
    }

    long count = totalPtxCount.sum();
    System.out.println("\n### PTX Debug Performance Summary");
    System.out.println();
    System.out.println("| Metric | Value |");
    System.out.println("| :--- | ---: |");
    System.out.println(String.format("| Total PTX Fields | %d |", count));
    System.out.println(String.format("| Total Parse Time | %d ms |", totalPtxParseTime.sum() / 1_000_000));
    System.out.println(String.format("| Total Write Time | %d ms |", totalPtxWriteTime.sum() / 1_000_000));
    System.out.println(String.format("| Total Payload Size | %d bytes |", totalPtxPayloadSize.sum()));
    System.out.println(String.format("| Total XML Size | %d bytes |", totalPtxXmlSize.sum()));
    double expansionRatio = totalPtxPayloadSize.sum() > 0 ? (double) totalPtxXmlSize.sum() / totalPtxPayloadSize.sum() : 0;
    System.out.println(String.format("| PTX Expansion Ratio | %.2f |", expansionRatio));
    System.out.println(String.format("| Total Ctrl Sequences | %d |", totalPtxControlSequences.sum()));
    System.out.println(String.format("| Avg CS per PTX | %.2f |", (double) totalPtxControlSequences.sum() / count));
    System.out.println(String.format("| Avg Payload per PTX | %.2f bytes |", (double) totalPtxPayloadSize.sum() / count));

    if (!ptocaFunctionCounts.isEmpty()) {
      System.out.println("\n#### PTOCA Function Breakdown");
      System.out.println();
      System.out.println(String.format("| %-30s | %10s | %12s | %12s | %12s | %15s | %15s | %10s | %s |", "Function", "Count", "Parse (ms)", "Write (ms)", "Max Write(ms)", "Total Payload", "Avg Payload", "Ratio", "Slowest Payload (Hex)"));
      System.out.println("| :--- | ---: | ---: | ---: | ---: | ---: | ---: | ---: | :--- |");
      new TreeMap<>(ptocaFunctionCounts).forEach((name, fCount) -> {
        long countValue = fCount.sum();
        long parseTime = ptocaFunctionParseTimes.getOrDefault(name, new LongAdder()).sum();
        long writeTime = ptocaFunctionWriteTimes.getOrDefault(name, new LongAdder()).sum();
        long maxWTime = ptocaFunctionMaxWriteTimes.getOrDefault(name, new LongAdder()).sum();
        long payload = ptocaFunctionPayloadSizes.getOrDefault(name, new LongAdder()).sum();
        long xmlSize = ptocaFunctionXmlSizes.getOrDefault(name, new LongAdder()).sum();
        String maxPayload = ptocaFunctionMaxPayloads.getOrDefault(name, "N/A");
        if (maxPayload.length() > 50) maxPayload = maxPayload.substring(0, 47) + "...";
        double avgPayload = countValue > 0 ? (double) payload / countValue : 0;
        double ratio = payload > 0 ? (double) xmlSize / payload : 0;
        System.out.println(String.format("| %-30s | %10d | %12d | %12d | %12d | %15d | %15.2f | %10.2f | %s |",
            name, countValue, parseTime / 1_000_000, writeTime / 1_000_000, maxWTime / 1_000_000, payload, avgPayload, ratio, maxPayload));
      });

      long totalPtocaWriteTime = ptocaFunctionWriteTimes.values().stream().mapToLong(LongAdder::sum).sum();
      if (totalPtocaWriteTime == 0 && totalPtxWriteTime.sum() > 0) {
        System.out.println("\nNote: Individual PTOCA write times are 0. Use Jackson writer (-j) for granular metrics.");
      }
    }
    System.out.println();
  }

  private static class LocalPtxStats {
    long totalPtxCount = 0;
    long totalPtxParseTime = 0;
    long totalPtxWriteTime = 0;
    long totalPtxXmlSize = 0;
    long totalPtxPayloadSize = 0;
    long totalPtxControlSequences = 0;
    final Map<String, LocalPtocaStats> ptocaStats = new HashMap<>();

    void reset() {
      totalPtxCount = 0;
      totalPtxParseTime = 0;
      totalPtxWriteTime = 0;
      totalPtxXmlSize = 0;
      totalPtxPayloadSize = 0;
      totalPtxControlSequences = 0;
      ptocaStats.clear();
    }
  }

  private static class LocalPtocaStats {
    long count = 0;
    long parseTime = 0;
    long writeTime = 0;
    long maxWriteTime = 0;
    String maxPayload = null;
    long payloadSize = 0;
    long xmlSize = 0;
  }
}
