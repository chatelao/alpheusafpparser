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

package com.mgz.xml;

/**
 * Utility for pre-allocated XML indentation strings to avoid redundant allocations.
 * It provides a cache of common indentation levels (newline + spaces).
 */
public class XmlIndenter {
  private static final String[] INDENTS;
  private static final int MAX_LEVEL = 32;

  static {
    INDENTS = new String[MAX_LEVEL];
    for (int i = 0; i < MAX_LEVEL; i++) {
      StringBuilder sb = new StringBuilder();
      sb.append("\n");
      for (int j = 0; j < i; j++) {
        sb.append("  ");
      }
      INDENTS[i] = sb.toString();
    }
  }

  /**
   * Returns a pre-allocated indentation string consisting of a newline and 2 * level spaces.
   *
   * @param level the indentation level (0 to 31)
   * @return the indentation string
   */
  public static String getIndent(int level) {
    if (level <= 0) {
      return INDENTS[0]; // "\n"
    }
    if (level >= MAX_LEVEL) {
      return INDENTS[MAX_LEVEL - 1];
    }
    return INDENTS[level];
  }

  /**
   * Pre-allocated level 1 indentation (2 spaces) without a leading newline.
   */
  public static final String LEVEL_1_PURE = "  ";

  /**
   * Pre-allocated level 2 indentation (4 spaces) without a leading newline.
   */
  public static final String LEVEL_2_PURE = "    ";

  /**
   * Pre-allocated level 3 indentation (6 spaces) without a leading newline.
   */
  public static final String LEVEL_3_PURE = "      ";

  /**
   * Writes the indentation for the given level to the provided writer.
   *
   * @param xsw the XML stream writer
   * @param level the indentation level
   * @throws Exception if writing fails
   */
  public static void writeIndent(org.codehaus.stax2.XMLStreamWriter2 xsw, int level) throws Exception {
    xsw.writeCharacters(getIndent(level));
  }
}
