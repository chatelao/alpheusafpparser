/*
Copyright 2024 Rudolf Fiala

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

import java.io.PrintWriter;
import java.io.Serializable;
import java.nio.charset.Charset;

/**
 * Utility class to log discarded or unparsed data during AFP processing.
 */
public class DiscardedDataLogger implements Serializable {
  private static final long serialVersionUID = 1L;
  private transient PrintWriter writer;

  public DiscardedDataLogger(PrintWriter writer) {
    this.writer = writer;
  }

  /**
   * Logs discarded data.
   *
   * @param offset  The file offset where the data was found.
   * @param message A descriptive message.
   * @param data    The raw discarded bytes.
   * @param charset The charset to use for a text representation (may be null).
   */
  public synchronized void log(long offset, String message, byte[] data, Charset charset) {
    if (writer == null) {
      return;
    }
    writer.printf("Offset: 0x%08X | %s%n", offset, message);
    if (data != null && data.length > 0) {
      writer.printf("  Hex: %s%n", UtilCharacterEncoding.bytesToHexString(data));
      if (charset != null) {
        String decoded = new String(data, charset);
        // Replace common control characters for a cleaner log
        String cleanDecoded = decoded.replace('\n', ' ').replace('\r', ' ').replace('\t', ' ');
        writer.printf("  Text (%s): %s%n", charset.name(), cleanDecoded);
      }
    }
    writer.println("--------------------------------------------------------------------------------");
    writer.flush();
  }

  /**
   * Closes the underlying writer.
   */
  public void close() {
    if (writer != null) {
      writer.close();
    }
  }
}
