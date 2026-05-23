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

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Utility to track parsing and writing performance per mnemonic.
 */
public class MnemonicPerformanceMonitor {

  private static volatile boolean enabled = false;

  private static final Map<String, MnemonicStats> statsMap = new ConcurrentHashMap<>();

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
    MnemonicStats stats = statsMap.computeIfAbsent(m.mnemonic, k -> new MnemonicStats());
    stats.parseTime.addAndGet(duration);
    stats.count.incrementAndGet();
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
    MnemonicStats stats = statsMap.computeIfAbsent(m.mnemonic, k -> new MnemonicStats());
    stats.writeTime.addAndGet(duration);
  }

  public static String extractMnemonic(Object obj) {
    if (obj == null) return null;
    if (obj instanceof String s) return extractMnemonicFromString(s);
    return extractMnemonicFromClassName(obj.getClass().getName());
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
      if (s.length() < 2 || s.length() > 5) return false;
      for (int i = 0; i < s.length(); i++) {
          char c = s.charAt(i);
          if (!Character.isUpperCase(c) && !Character.isDigit(c)) return false;
      }
      // Common false positives in XML output
      if ("AFPDocument".equals(s)) return false;
      return true;
  }

  public static void printSummary() {
    if (statsMap.isEmpty()) {
      System.out.println("No mnemonics measured.");
      return;
    }

    System.out.println("\nPerformance Summary per Mnemonic:");
    System.out.println(String.format("%-10s | %8s | %15s | %15s | %15s",
        "Mnemonic", "Count", "Total (ms)", "Parse (ms)", "Write (ms)"));
    System.out.println("-".repeat(73));

    new TreeMap<>(statsMap).forEach((mnemonic, stats) -> {
      long parseMs = stats.parseTime.get() / 1_000_000;
      long writeMs = stats.writeTime.get() / 1_000_000;
      long totalMs = parseMs + writeMs;
      System.out.println(String.format("%-10s | %8d | %15d | %15d | %15d",
          mnemonic, stats.count.get(), totalMs, parseMs, writeMs));
    });
    System.out.println();
  }

  private static class MnemonicStats {
    final AtomicLong parseTime = new AtomicLong();
    final AtomicLong writeTime = new AtomicLong();
    final AtomicLong count = new AtomicLong();
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
