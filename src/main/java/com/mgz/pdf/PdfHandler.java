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

import com.itextpdf.kernel.pdf.PdfArray;
import com.itextpdf.kernel.pdf.PdfDictionary;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfName;
import com.itextpdf.kernel.pdf.PdfPage;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import com.mgz.afp.base.StructuredField;
import com.mgz.afp.base.handler.StructuredFieldHandler;
import com.mgz.afp.modca.BDT_BeginDocument;
import com.mgz.afp.modca.BNG_BeginNamedPageGroup;
import com.mgz.afp.modca.BPG_BeginPage;
import com.mgz.afp.modca.EDT_EndDocument;
import com.mgz.afp.modca.ENG_EndNamedPageGroup;
import com.mgz.afp.modca.EPG_EndPage;
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
  private final Deque<PdfDictionary> dpartStack = new ArrayDeque<>();
  private final PdfDocument pdfDoc;
  private final Document document;
  private final PdfDictionary dpartRoot;

  public PdfHandler(OutputStream os) {
    this.pdfDoc = new PdfDocument(new PdfWriter(os));
    this.document = new Document(pdfDoc);

    // Initialize DPartRoot for PDF/VT compliance (ISO 16612-2)
    this.dpartRoot = new PdfDictionary();
    dpartRoot.put(PdfName.Type, new PdfName("DPartRoot"));
    pdfDoc.getCatalog().put(new PdfName("DPartRoot"), dpartRoot);

    // Initialize DTree root node
    PdfDictionary dtreeRoot = new PdfDictionary();
    dtreeRoot.put(PdfName.Type, new PdfName("DPart"));
    dpartRoot.put(new PdfName("DTree"), dtreeRoot);
  }

  @Override
  public void handle(StructuredField sf) throws Exception {
    fieldCount.incrementAndGet();

    if (sf.isBeginSF()) {
      structureStack.push(sf);
      if (sf instanceof BDT_BeginDocument || sf instanceof BNG_BeginNamedPageGroup || sf instanceof BPG_BeginPage) {
        PdfDictionary dpart = new PdfDictionary();
        dpart.makeIndirect(pdfDoc);
        dpart.put(PdfName.Type, PdfName.DPart);

        PdfDictionary parent = dpartStack.isEmpty() ? dpartRoot.getAsDictionary(new PdfName("DTree")) : dpartStack.peek();
        dpart.put(PdfName.Parent, parent);

        PdfArray dparts = parent.getAsArray(new PdfName("DParts"));
        if (dparts == null) {
          dparts = new PdfArray();
          dparts.makeIndirect(pdfDoc);
          parent.put(new PdfName("DParts"), dparts);
        }
        dparts.add(dpart);
        dpartStack.push(dpart);

        if (sf instanceof BPG_BeginPage) {
          PdfPage page = pdfDoc.addNewPage();
          page.put(PdfName.DPart, dpart);
        }
      }
    } else if (sf.isEndSF()) {
      if (!structureStack.isEmpty()) {
        StructuredField begin = structureStack.pop();
        if (begin instanceof BDT_BeginDocument || begin instanceof BNG_BeginNamedPageGroup || begin instanceof BPG_BeginPage) {
          if (!dpartStack.isEmpty()) {
            dpartStack.pop();
          }
        }
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
