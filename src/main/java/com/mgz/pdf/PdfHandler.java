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
import com.itextpdf.kernel.pdf.PdfString;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import com.mgz.afp.base.StructuredField;
import com.mgz.afp.base.handler.StructuredFieldHandler;
import com.mgz.afp.base.IRepeatingGroup;
import com.mgz.afp.enums.AFPUnitBase;
import com.mgz.afp.modca.BDT_BeginDocument;
import com.mgz.afp.modca.BNG_BeginNamedPageGroup;
import com.mgz.afp.modca.BPG_BeginPage;
import com.mgz.afp.modca.EDT_EndDocument;
import com.mgz.afp.modca.ENG_EndNamedPageGroup;
import com.mgz.afp.modca.EPG_EndPage;
import com.mgz.afp.modca.MMO_MapMediumOverlay;
import com.mgz.afp.modca.MPS_MapPageSegment;
import com.mgz.afp.modca.PGD_PageDescriptor;
import com.mgz.afp.modca.TLE_TagLogicalElement;
import com.mgz.afp.triplets.Triplet;
import java.io.OutputStream;
import java.util.ArrayDeque;
import java.util.Collections;
import java.util.Deque;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Skeleton implementation of {@link StructuredFieldHandler} for PDF generation.
 * Tracks MO:DCA structural boundaries to facilitate PDF/VT DPart mapping.
 */
public class PdfHandler implements StructuredFieldHandler {

  private final AtomicLong fieldCount = new AtomicLong(0);
  private final Deque<StructuredField> structureStack = new ArrayDeque<>();
  private final Deque<PdfDictionary> dpartStack = new ArrayDeque<>();
  private final Set<String> mmoResources = new HashSet<>();
  private final Set<String> mpsResources = new HashSet<>();
  private final PdfDocument pdfDoc;
  private final Document document;
  private final PdfDictionary dpartRoot;
  private PdfPage currentPage;

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
          this.currentPage = pdfDoc.addNewPage();
          currentPage.put(PdfName.DPart, dpart);
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
    } else if (sf instanceof TLE_TagLogicalElement tle) {
      if (!dpartStack.isEmpty()) {
        PdfDictionary dpart = dpartStack.peek();
        String key = null;
        String value = null;
        if (tle.getTriplets() != null) {
          for (Triplet triplet : tle.getTriplets()) {
            if (triplet instanceof Triplet.FullyQualifiedName fqn && fqn.getType() == Triplet.GlobalID_Use.AttributeGID) {
              key = fqn.getNameAsString();
            } else if (triplet instanceof Triplet.AttributeValue av) {
              value = av.getAttributeValue();
            }
          }
        }
        if (key != null && value != null) {
          PdfDictionary property = dpart.getAsDictionary(new PdfName("Property"));
          if (property == null) {
            property = new PdfDictionary();
            property.makeIndirect(pdfDoc);
            dpart.put(new PdfName("Property"), property);
          }
          property.put(new PdfName(key), new PdfString(value));
        }
      }
    } else if (sf instanceof MMO_MapMediumOverlay mmo) {
      if (mmo.getRepeatingGroups() != null) {
        for (IRepeatingGroup rg : mmo.getRepeatingGroups()) {
          if (rg instanceof MMO_MapMediumOverlay.MMO_RepeatingGroup mmorg) {
            mmoResources.add(mmorg.getNameOfMediumOverlay());
          }
        }
      }
    } else if (sf instanceof MPS_MapPageSegment mps) {
      if (mps.getRepeatingGroups() != null) {
        for (IRepeatingGroup rg : mps.getRepeatingGroups()) {
          if (rg instanceof MPS_MapPageSegment.MPS_RepeatingGroup mpsrg) {
            mpsResources.add(mpsrg.getNameOfPageSegment());
          }
        }
      }
    } else if (sf instanceof PGD_PageDescriptor pgd) {
      float widthPoints = convertToPoints(pgd.getxSize(), pgd.getxUnitBase(), pgd.getxUnitsPerUnitBase());
      float heightPoints = convertToPoints(pgd.getySize(), pgd.getyUnitBase(), pgd.getyUnitsPerUnitBase());

      if (currentPage != null) {
        currentPage.setMediaBox(new com.itextpdf.kernel.geom.Rectangle(widthPoints, heightPoints));
      }
    }

    // TODO: Implement iText 9 based translation logic
  }

  private float convertToPoints(int units, AFPUnitBase base, short unitsPerBase) {
    if (base == AFPUnitBase.Inches10) {
      return (units * 72.0f) / (unitsPerBase / 10.0f);
    } else if (base == AFPUnitBase.Centimeter10) {
      return (units * 72.0f) / (unitsPerBase / 10.0f * 2.54f);
    }
    return 0.0f;
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

  /**
   * Returns an unmodifiable set of the Medium Overlay names tracked by this handler.
   *
   * @return the set of MMO resource names
   */
  public Set<String> getMmoResources() {
    return Collections.unmodifiableSet(mmoResources);
  }

  /**
   * Returns an unmodifiable set of the Page Segment names tracked by this handler.
   *
   * @return the set of MPS resource names
   */
  public Set<String> getMpsResources() {
    return Collections.unmodifiableSet(mpsResources);
  }
}
