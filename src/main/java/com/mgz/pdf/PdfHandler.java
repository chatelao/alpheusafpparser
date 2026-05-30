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

import com.itextpdf.kernel.pdf.PdfDictionary;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfName;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import com.mgz.afp.base.StructuredField;
import com.mgz.afp.base.handler.StructuredFieldHandler;
import java.io.OutputStream;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Skeleton implementation of {@link StructuredFieldHandler} for PDF generation.
 * Tracks MO:DCA structural boundaries to facilitate PDF/VT DPart mapping.
 */
public class PdfHandler implements StructuredFieldHandler {

  private final AtomicLong fieldCount = new AtomicLong(0);
  private final Deque<StructuredField> structureStack = new ArrayDeque<>();
  private final PdfDocument pdfDoc;
  private final Document document;

  public PdfHandler(OutputStream os) {
    this.pdfDoc = new PdfDocument(new PdfWriter(os));
    this.document = new Document(pdfDoc);

    // Initialize DPartRoot for PDF/VT compliance (ISO 16612-2)
    // DPartRoot is an optional key in PDF 2.0 Catalog, but required for PDF/VT
    PdfDictionary dpartRoot = new PdfDictionary();
    dpartRoot.put(PdfName.Type, new PdfName("DPartRoot"));
    pdfDoc.getCatalog().put(new PdfName("DPartRoot"), dpartRoot);
  }

  @Override
  public void handle(StructuredField sf) throws Exception {
    fieldCount.incrementAndGet();

    if (sf.isBeginSF()) {
      structureStack.push(sf);
    } else if (sf.isEndSF()) {
      if (!structureStack.isEmpty()) {
        structureStack.pop();
      }
    }

    // TODO: Implement iText 9 based translation logic
  }

  @Override
  public void close() throws Exception {
    if (fieldCount.get() > 0 && pdfDoc.getNumberOfPages() == 0) {
      document.add(new Paragraph("AFP to PDF conversion in progress..."));
    }
    document.close();
  }

  /**
   * Returns the number of fields processed by this handler.
   *
   * @return the field count
   */
  public long getFieldCount() {
    return fieldCount.get();
  }

  /**
   * Returns the current depth of the MO:DCA structure stack.
   *
   * @return the structure depth
   */
  public int getStructureDepth() {
    return structureStack.size();
  }
}
