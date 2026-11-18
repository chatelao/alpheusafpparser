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

import javax.management.MBeanServer;
import javax.management.ObjectName;
import java.lang.management.ManagementFactory;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 * Utility for programmatic heap histogram analysis. [DECOUPLE-4.3.2.1.3]
 */
public class HeapHistogramUtil {

    private static final String DIAGNOSTIC_COMMAND_MBEAN_NAME = "com.sun.management:type=DiagnosticCommand";

    /**
     * Captures a heap histogram as a raw string.
     *
     * @return the raw histogram string
     */
    public static String getRawHistogram() {
        try {
            MBeanServer server = ManagementFactory.getPlatformMBeanServer();
            ObjectName name = new ObjectName(DIAGNOSTIC_COMMAND_MBEAN_NAME);
            // gcClassHistogram takes an array of strings as arguments
            return (String) server.invoke(name, "gcClassHistogram", new Object[] {null}, new String[] {"[Ljava.lang.String;"});
        } catch (Exception e) {
            return "Error capturing histogram: " + e.getMessage();
        }
    }

    /**
     * Parses a raw histogram string into a map of class names to instance counts.
     *
     * @param histogram the raw histogram string
     * @return a map of class names to instance counts
     */
    public static Map<String, Long> parseHistogram(String histogram) {
        Map<String, Long> counts = new HashMap<>();
        try (Scanner scanner = new Scanner(histogram)) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine().trim();
                // Example line: "   1:         10717         554352  [B (java.base@21.0.10)"
                if (line.matches("^\\d+:.*")) {
                    String[] parts = line.split("\\s+");
                    if (parts.length >= 4) {
                        try {
                            long instances = Long.parseLong(parts[1]);
                            String className = parts[3];
                            counts.put(className, instances);
                        } catch (NumberFormatException ignored) {
                        }
                    }
                }
            }
        }
        return counts;
    }
}
