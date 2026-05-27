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

import java.nio.charset.Charset;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.LongAdder;

/**
 * Utility to track parsing and writing performance per mnemonic.
 */
public class MnemonicPerformanceMonitor {

  private static volatile boolean enabled = false;

  private static final Map<String, MnemonicStats> globalStatsMap = new ConcurrentHashMap<>();
  private static final Map<String, LongAdder> globalCharsetMap = new ConcurrentHashMap<>();

  private static final Map<String, String> mnemonicCache = new ConcurrentHashMap<>();

  private static final ThreadLocal<Map<String, LocalStats>> localStatsMap =
      ThreadLocal.withInitial(HashMap::new);

  private static final ThreadLocal<Map<String, Long>> localCharsetMap =
      ThreadLocal.withInitial(HashMap::new);

  private static final ThreadLocal<Deque<Measurement>> activeMeasurements =
      ThreadLocal.withInitial(ArrayDeque::new);

  public static void setEnabled(boolean enabled) {
    MnemonicPerformanceMonitor.enabled = enabled;
  }

  public static boolean isEnabled() {
    return enabled;
  }

  public static void startParse(Object obj) {
    if (!enabled) return;
    String mnemonic = extractMnemonic(obj);
    if (mnemonic == null) return;
    activeMeasurements.get().push(new Measurement(mnemonic, System.nanoTime(), true));
  }

  public static void endParse() {
    if (!enabled) return;
    long endTime = System.nanoTime();
    Deque<Measurement> stack = activeMeasurements.get();
    if (stack.isEmpty()) return;
    Measurement m = stack.peek();
    if (!m.isParse) return;
    stack.pop();
    long duration = endTime - m.startTime;
    LocalStats stats = localStatsMap.get().computeIfAbsent(m.mnemonic, k -> new LocalStats());
    stats.parseTime += duration;
    stats.count++;
  }

  public static void startWrite(String name) {
    if (!enabled) return;
    String mnemonic = extractMnemonicFromString(name);
    if (mnemonic == null) return;
    activeMeasurements.get().push(new Measurement(mnemonic, System.nanoTime(), false));
  }

  public static void endWrite() {
    if (!enabled) return;
    long endTime = System.nanoTime();
    Deque<Measurement> stack = activeMeasurements.get();
    if (stack.isEmpty()) return;
    Measurement m = stack.peek();
    if (m.isParse) return;
    stack.pop();
    long duration = endTime - m.startTime;
    LocalStats stats = localStatsMap.get().computeIfAbsent(m.mnemonic, k -> new LocalStats());
    stats.writeTime += duration;
  }

  public static void recordCharset(Charset charset) {
    if (!enabled || charset == null) return;
    String name = charset.name();
    Map<String, Long> local = localCharsetMap.get();
    local.put(name, local.getOrDefault(name, 0L) + 1);
  }

  public static String extractMnemonic(Object obj) {
    if (obj == null) return null;
    if (obj instanceof String s) return extractMnemonicFromString(s);

    String className = obj.getClass().getName();
    String cached = mnemonicCache.get(className);
    if (cached != null) {
      return cached.isEmpty() ? null : cached;
    }

    String mnemonic = extractMnemonicFromClassName(className);
    mnemonicCache.put(className, mnemonic != null ? mnemonic : "");
    return mnemonic;
  }

  private static String extractMnemonicFromClassName(String className) {
    int lastDot = className.lastIndexOf('.');
    String simpleName = (lastDot != -1) ? className.substring(lastDot + 1) : className;
    int dollar = simpleName.lastIndexOf('$');
    if (dollar != -1) {
      simpleName = simpleName.substring(dollar + 1);
    }
    return extractMnemonicFromString(simpleName);
  }

  private static String extractMnemonicFromString(String name) {
    if (name == null || name.isEmpty()) return null;

    String cached = mnemonicCache.get(name);
    if (cached != null) {
      return cached.isEmpty() ? null : cached;
    }

    String mnemonic = doExtractMnemonicFromString(name);
    mnemonicCache.put(name, mnemonic != null ? mnemonic : "");
    return mnemonic;
  }

  private static String doExtractMnemonicFromString(String name) {
    int underscorePos = name.indexOf('_');
    if (underscorePos != -1) {
      String prefix = name.substring(0, underscorePos);
      if (isLikelyMnemonic(prefix)) {
        return prefix;
      }
    }

    if (isLikelyMnemonic(name)) {
      return name;
    }

    return null;
  }

  private static boolean isLikelyMnemonic(String s) {
    if (s.length() < 2 || s.length() > 5) {
      return false;
    }
    for (int i = 0; i < s.length(); i++) {
      char c = s.charAt(i);
      if (!Character.isUpperCase(c) && !Character.isDigit(c)) {
        return false;
      }
    }
    // Common false positives in XML output
    if ("AFPDocument".equals(s)) {
      return false;
    }
    return true;
  }

  /**
   * Clears all global statistics.
   */
  public static void clear() {
    globalStatsMap.clear();
    globalCharsetMap.clear();
  }

  public static long getWriteTime(String name) {
    merge();
    String mnemonic = extractMnemonicFromString(name);
    if (mnemonic == null) return 0;
    MnemonicStats stats = globalStatsMap.get(mnemonic);
    return stats != null ? stats.writeTime.sum() : 0;
  }

  /**
   * Merges local thread statistics into the global statistics map and clears local state.
   */
  public static void merge() {
    Map<String, LocalStats> local = localStatsMap.get();
    if (!local.isEmpty()) {
      local.forEach((mnemonic, localStats) -> {
        MnemonicStats global = globalStatsMap.computeIfAbsent(mnemonic, k -> new MnemonicStats());
        global.parseTime.add(localStats.parseTime);
        global.writeTime.add(localStats.writeTime);
        global.count.add(localStats.count);
      });
      local.clear();
    }

    Map<String, Long> localCharsets = localCharsetMap.get();
    if (!localCharsets.isEmpty()) {
      localCharsets.forEach((name, count) -> {
        globalCharsetMap.computeIfAbsent(name, k -> new LongAdder()).add(count);
      });
      localCharsets.clear();
    }
  }

  public static void printSummary() {
    merge(); // Ensure all threads that called merge are included, but this only merges CURRENT thread.
    // In parallel mode, each thread should call merge() at the end of its task.

    if (globalStatsMap.isEmpty() && globalCharsetMap.isEmpty()) {
      System.out.println("No measurements collected.");
      return;
    }

    if (!globalStatsMap.isEmpty()) {
      System.out.println("\n### Performance Summary per Mnemonic");
      System.out.println();
      System.out.println("| Mnemonic | Count | Total (ms) | Parse (ms) | Write (ms) |");
      System.out.println("| :--- | ---: | ---: | ---: | ---: |");

      new TreeMap<>(globalStatsMap).forEach((mnemonic, stats) -> {
        long parseMs = stats.parseTime.sum() / 1_000_000;
        long writeMs = stats.writeTime.sum() / 1_000_000;
        long totalMs = parseMs + writeMs;
        System.out.println(String.format("| %-10s | %8d | %15d | %15d | %15d |",
            mnemonic, stats.count.sum(), totalMs, parseMs, writeMs));
      });
    }

    if (!globalCharsetMap.isEmpty()) {
      System.out.println("\n### Charsets Used");
      System.out.println();
      System.out.println("| Charset | Count |");
      System.out.println("| :--- | ---: |");
      new TreeMap<>(globalCharsetMap).forEach((name, count) -> {
        System.out.println(String.format("| %-20s | %8d |", name, count.sum()));
      });
    }

    System.out.println();
  }

  private static class MnemonicStats {
    final LongAdder parseTime = new LongAdder();
    final LongAdder writeTime = new LongAdder();
    final LongAdder count = new LongAdder();
  }

  private static class LocalStats {
    long parseTime = 0;
    long writeTime = 0;
    long count = 0;
  }

  private static class Measurement {
    final String mnemonic;
    final long startTime;
    final boolean isParse;

    Measurement(String mnemonic, long startTime, boolean isParse) {
      this.mnemonic = mnemonic;
      this.startTime = startTime;
      this.isParse = isParse;
    }
  }
}
