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

import com.mgz.afp.base.StructuredField;
import com.mgz.afp.base.handler.StructuredFieldHandler;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Skeleton implementation of {@link StructuredFieldHandler} for PDF generation.
 * Initially used as a stub for performance benchmarking the parsing stage.
 */
public class PdfHandler implements StructuredFieldHandler {

  private final AtomicLong fieldCount = new AtomicLong(0);

  @Override
  public void handle(StructuredField sf) throws Exception {
    fieldCount.incrementAndGet();
    // TODO: Implement iText 9 based translation logic
  }

  @Override
  public void close() throws Exception {
    // No resources to release in the stub
  }

  /**
   * Returns the number of fields processed by this handler.
   *
   * @return the field count
   */
  public long getFieldCount() {
    return fieldCount.get();
  }
}
