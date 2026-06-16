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

import java.nio.charset.StandardCharsets;

/**
 * Pre-allocated byte templates for common XML tags to avoid redundant String allocations and UTF-8 encoding.
 */
public class XmlTagTemplates {
  public static final byte[] TEXT_START = "<text>".getBytes(StandardCharsets.UTF_8);
  public static final byte[] TEXT_END = "</text>".getBytes(StandardCharsets.UTF_8);
  public static final byte[] POINTS_START = "<points>".getBytes(StandardCharsets.UTF_8);
  public static final byte[] POINTS_END = "</points>".getBytes(StandardCharsets.UTF_8);
  public static final byte[] TRN_END = "</TRN_TransparentData>".getBytes(StandardCharsets.UTF_8);
  public static final byte[] GC_END = "</GraphicCharacters>".getBytes(StandardCharsets.UTF_8);

  public static final byte[] TRN_DATA_START = "<transparentData>".getBytes(StandardCharsets.UTF_8);
  public static final byte[] TRN_DATA_END = "</transparentData>".getBytes(StandardCharsets.UTF_8);

  public static final byte[] RPS_END = "</RPS_RepeatString>".getBytes(StandardCharsets.UTF_8);
  public static final byte[] REPEAT_LENGTH_START =
      "<repeatLength>".getBytes(StandardCharsets.UTF_8);
  public static final byte[] REPEAT_LENGTH_END =
      "</repeatLength>".getBytes(StandardCharsets.UTF_8);
  public static final byte[] REPEAT_DATA_START =
      "<repeatData>".getBytes(StandardCharsets.UTF_8);
  public static final byte[] REPEAT_DATA_END =
      "</repeatData>".getBytes(StandardCharsets.UTF_8);
}
