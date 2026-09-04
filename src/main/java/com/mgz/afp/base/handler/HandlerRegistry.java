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

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.ServiceLoader;
import java.util.Set;

/**
 * Registry for {@link HandlerFactory} implementations.
 * Uses Java SPI to discover available factories.
 */
public final class HandlerRegistry {

  private static final Map<String, Class<? extends HandlerFactory>> FACTORY_CLASSES;

  static {
    Map<String, Class<? extends HandlerFactory>> map = new HashMap<>();
    ServiceLoader<HandlerFactory> loader = ServiceLoader.load(HandlerFactory.class);
    for (HandlerFactory factory : loader) {
      map.put(factory.getFormatName().toLowerCase(), factory.getClass());
    }
    FACTORY_CLASSES = Collections.unmodifiableMap(map);
  }

  private HandlerRegistry() {
    // Utility class
  }

  /**
   * Returns a new factory instance for the specified format.
   *
   * @param format the format name (case-insensitive)
   * @return a new factory instance, or null if not found
   */
  public static HandlerFactory getFactory(String format) {
    if (format == null) {
      return null;
    }
    Class<? extends HandlerFactory> clazz = FACTORY_CLASSES.get(format.toLowerCase());
    if (clazz == null) {
      return null;
    }
    try {
      return clazz.getDeclaredConstructor().newInstance();
    } catch (Exception e) {
      throw new RuntimeException("Failed to instantiate HandlerFactory for format: " + format, e);
    }
  }

  /**
   * Returns the set of registered format names.
   *
   * @return the registered formats
   */
  public static Set<String> getRegisteredFormats() {
    return FACTORY_CLASSES.keySet();
  }
}
