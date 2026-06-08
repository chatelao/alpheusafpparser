/*
Copyright 2015 Rudolf Fiala

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

import java.lang.management.ManagementFactory;
import java.lang.reflect.Method;

/**
 * Utility for programmatic heap dumps. [DECOUPLE-4.3.2.1.2.1]
 */
public class HeapDumpUtil {

    private static final String HOTSPOT_BEAN_NAME = "com.sun.management:type=HotSpotDiagnostic";

    private static volatile Object hotspotDiagnosticMXBean;

    /**
     * Dumps the heap to the specified file.
     *
     * @param filePath the path to the dump file
     * @param live if true, only live objects are dumped
     */
    public static void dumpHeap(String filePath, boolean live) {
        try {
            initHotspotDiagnosticMXBean();
            Method method = hotspotDiagnosticMXBean.getClass().getMethod("dumpHeap", String.class, boolean.class);
            method.invoke(hotspotDiagnosticMXBean, filePath, live);
        } catch (Exception e) {
            System.err.println("Failed to dump heap: " + e.getMessage());
        }
    }

    private static void initHotspotDiagnosticMXBean() throws Exception {
        if (hotspotDiagnosticMXBean == null) {
            synchronized (HeapDumpUtil.class) {
                if (hotspotDiagnosticMXBean == null) {
                    Class<?> clazz = Class.forName("com.sun.management.HotSpotDiagnosticMXBean");
                    hotspotDiagnosticMXBean = ManagementFactory.newPlatformMXBeanProxy(
                            ManagementFactory.getPlatformMBeanServer(), HOTSPOT_BEAN_NAME, clazz);
                }
            }
        }
    }
}
