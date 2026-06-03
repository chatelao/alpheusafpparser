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

import com.mgz.util.UtilCharacterEncoding;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents a pre-encoded XML byte template with "holes" for variable data.
 * It allows serializing a structured field with minimal memory copies.
 */
public class XmlTemplate {

  private final List<byte[]> segments = new ArrayList<>();
  private final List<PlaceholderType> placeholders = new ArrayList<>();

  public enum PlaceholderType {
    INTEGER,
    LONG,
    STRING_ESCAPED,
    STRING_RAW,
    BINARY_BASE64
  }

  private XmlTemplate(String template) {
    String[] parts = template.split("\\{\\}", -1);
    for (int i = 0; i < parts.length; i++) {
      segments.add(parts[i].getBytes(StandardCharsets.UTF_8));
    }
  }

  /**
   * Creates a new template from a string with {} placeholders.
   *
   * @param template the template string
   * @param types the types of each placeholder in order
   * @return the created template
   */
  public static XmlTemplate of(String template, PlaceholderType... types) {
    XmlTemplate t = new XmlTemplate(template);
    for (PlaceholderType type : types) {
      t.placeholders.add(type);
    }
    if (t.segments.size() != t.placeholders.size() + 1) {
      throw new IllegalArgumentException("Number of placeholders in string does not match number of types provided");
    }
    return t;
  }

  /**
   * Writes the template to the output stream, injecting the provided values.
   *
   * @param os the output stream
   * @param values the values to inject
   * @throws IOException if writing fails
   */
  public void write(OutputStream os, Object... values) throws IOException {
    if (values.length != placeholders.size()) {
      throw new IllegalArgumentException("Expected " + placeholders.size() + " values, but got " + values.length);
    }

    for (int i = 0; i < placeholders.size(); i++) {
      os.write(segments.get(i));
      Object val = values[i];
      PlaceholderType type = placeholders.get(i);

      switch (type) {
        case INTEGER:
          if (val instanceof Integer) {
            FastIntEncoder.encodeAndWrite((Integer) val, os);
          } else {
            FastIntEncoder.encodeAndWrite(Integer.parseInt(val.toString()), os);
          }
          break;
        case LONG:
          if (val instanceof Long) {
            FastIntEncoder.encodeAndWrite((Long) val, os);
          } else {
            FastIntEncoder.encodeAndWrite(Long.parseLong(val.toString()), os);
          }
          break;
        case STRING_ESCAPED:
          String s = (val == null) ? "" : val.toString();
          writeEscaped(s, os);
          break;
        case STRING_RAW:
          String sr = (val == null) ? "" : val.toString();
          os.write(sr.getBytes(StandardCharsets.UTF_8));
          break;
        case BINARY_BASE64:
          if (val instanceof byte[]) {
            byte[] data = (byte[]) val;
            os.write(java.util.Base64.getEncoder().encode(data));
          }
          break;
      }
    }
    os.write(segments.get(segments.size() - 1));
  }

  private static final byte[] LT = "&lt;".getBytes(StandardCharsets.UTF_8);
  private static final byte[] GT = "&gt;".getBytes(StandardCharsets.UTF_8);
  private static final byte[] AMP = "&amp;".getBytes(StandardCharsets.UTF_8);
  private static final byte[] QUOT = "&quot;".getBytes(StandardCharsets.UTF_8);
  private static final byte[] APOS = "&apos;".getBytes(StandardCharsets.UTF_8);

  private void writeEscaped(String s, OutputStream os) throws IOException {
    if (s == null) return;
    for (int i = 0; i < s.length(); i++) {
      char c = s.charAt(i);
      switch (c) {
        case '<': os.write(LT); break;
        case '>': os.write(GT); break;
        case '&': os.write(AMP); break;
        case '\"': os.write(QUOT); break;
        case '\'': os.write(APOS); break;
        default:
          if (UtilCharacterEncoding.isValidXml10CodePoint(c)) {
            if (c <= 127) {
              os.write(c);
            } else {
              os.write(String.valueOf(c).getBytes(StandardCharsets.UTF_8));
            }
          } else {
            os.write(' ');
          }
      }
    }
  }
}
