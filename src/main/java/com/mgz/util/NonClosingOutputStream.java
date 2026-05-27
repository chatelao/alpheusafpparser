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

import java.io.FilterOutputStream;
import java.io.IOException;
import java.io.OutputStream;

/**
 * An OutputStream that prevents the underlying stream from being closed.
 * Useful when wrapping shared streams (like System.out) or when post-processing
 * (like truncation) is needed after a writer is closed.
 */
public class NonClosingOutputStream extends FilterOutputStream {

  /**
   * Constructs a NonClosingOutputStream.
   *
   * @param out the underlying output stream
   */
  public NonClosingOutputStream(OutputStream out) {
    super(out);
  }

  @Override
  public void write(byte[] b, int off, int len) throws IOException {
    out.write(b, off, len);
  }

  @Override
  public void close() throws IOException {
    flush();
    // Do NOT close the underlying stream
  }
}
