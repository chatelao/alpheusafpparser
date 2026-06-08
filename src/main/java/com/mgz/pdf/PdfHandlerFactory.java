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

package com.mgz.pdf;

import com.mgz.afp.base.handler.HandlerFactory;
import com.mgz.afp.base.handler.StructuredFieldHandler;
import java.io.OutputStream;

/**
 * Concrete implementation of {@link HandlerFactory} for PDF output.
 * Currently returns a stub {@link PdfHandler}.
 */
public class PdfHandlerFactory implements HandlerFactory {

  /**
   * Creates a new {@link PdfHandler}.
   *
   * @param os           the output stream
   * @param fragmentMode currently unsupported and ignored for PDF output
   * @return a new PdfHandler
   * @throws Exception if creation fails
   */
  @Override
  public StructuredFieldHandler createHandler(OutputStream os, boolean fragmentMode) throws Exception {
    return new PdfHandler(os);
  }

  @Override
  public long estimateOutputSize(long inputSize) {
    // PDF output can be larger than AFP due to fonts and metadata, but often images are smaller.
    // Use a safe factor of 2.0.
    return inputSize * 2;
  }
}
