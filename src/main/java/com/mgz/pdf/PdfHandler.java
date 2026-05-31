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

import com.itextpdf.io.font.constants.StandardFonts;
import com.itextpdf.kernel.colors.Color;
import com.itextpdf.kernel.colors.DeviceRgb;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.geom.AffineTransform;
import com.itextpdf.kernel.pdf.PdfArray;
import com.itextpdf.kernel.pdf.PdfDictionary;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfName;
import com.itextpdf.kernel.pdf.PdfPage;
import com.itextpdf.kernel.pdf.PdfString;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.kernel.pdf.canvas.PdfCanvas;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import com.mgz.afp.base.StructuredField;
import com.mgz.afp.base.handler.StructuredFieldHandler;
import com.mgz.afp.base.IRepeatingGroup;
import com.mgz.afp.bcoca.BDD_BarCodeDataDescriptor;
import com.mgz.afp.enums.AFPUnitBase;
import com.mgz.afp.goca.GAD_DrawingOrder;
import com.mgz.afp.goca.GAD_DrawingOrder.GBOX_BoxAtGivenPosition;
import com.mgz.afp.goca.GAD_DrawingOrder.GCBOX_BoxAtCurrentPosition;
import com.mgz.afp.goca.GAD_DrawingOrder.GCFLT_FilletAtCurrentPosition;
import com.mgz.afp.goca.GAD_DrawingOrder.GBAR_BeginArea;
import com.mgz.afp.goca.GAD_DrawingOrder.GCFARC_FullArcAtCurrentPosition;
import com.mgz.afp.goca.GAD_DrawingOrder.GCPARC_PartialArcAtCurrentPosition;
import com.mgz.afp.goca.GAD_DrawingOrder.GEAR_EndArea;
import com.mgz.afp.goca.GAD_DrawingOrder.GFLT_FilletAtGivenPosition;
import com.mgz.afp.goca.GAD_DrawingOrder.GFARC_FullArcAtGivenPosition;
import com.mgz.afp.goca.GAD_DrawingOrder.GPARC_PartialArcAtGivenPosition;
import com.mgz.afp.goca.GAD_DrawingOrder.GCLINE_LineAtCurrentPosition;
import com.mgz.afp.goca.GAD_DrawingOrder.GCRLINE_RelativeLineAtCurrentPosition;
import com.mgz.afp.goca.GAD_DrawingOrder.GLINE_LineAtGivenPosition;
import com.mgz.afp.goca.GAD_DrawingOrder.GRLINE_RelativeLineAtGivenPosition;
import com.mgz.afp.goca.GAD_DrawingOrder.GSAP_SetArcParameters;
import com.mgz.afp.goca.GAD_DrawingOrder.GSCOL_SetColor;
import com.mgz.afp.goca.GAD_DrawingOrder.GSCP_SetCurrentPosition;
import com.mgz.afp.goca.GAD_DrawingOrder.GSECOL_SetExtendedColor;
import com.mgz.afp.goca.GAD_DrawingOrder.GSLE_SetLineEnd;
import com.mgz.afp.goca.GAD_DrawingOrder.GSBMX_SetBackgroundMix;
import com.mgz.afp.goca.GAD_DrawingOrder.GSLJ_SetLineJoin;
import com.mgz.afp.goca.GAD_DrawingOrder.GSLT_SetLineType;
import com.mgz.afp.goca.GAD_DrawingOrder.GSMP_SetMarkerPrecision;
import com.mgz.afp.goca.GAD_DrawingOrder.GSMS_SetMarkerSet;
import com.mgz.afp.goca.GAD_DrawingOrder.GSMT_SetMarkerSymbol;
import com.mgz.afp.goca.GAD_DrawingOrder.GSPS_SetPatternSet;
import com.mgz.afp.goca.GAD_DrawingOrder.GSPT_SetPatternSymbol;
import com.mgz.afp.goca.GAD_DrawingOrder.GSLW_SetLineWidth;
import com.mgz.afp.goca.GAD_DrawingOrder.GSMX_SetMix;
import com.mgz.afp.goca.GAD_DrawingOrder.GSPCOL_SetProcessColor;
import com.mgz.afp.goca.GAD_GraphicsData;
import com.mgz.afp.modca.BDT_BeginDocument;
import com.mgz.afp.modca.BNG_BeginNamedPageGroup;
import com.mgz.afp.modca.BPG_BeginPage;
import com.mgz.afp.modca.EDT_EndDocument;
import com.mgz.afp.modca.ENG_EndNamedPageGroup;
import com.mgz.afp.modca.EPG_EndPage;
import com.mgz.afp.modca.MCF_MapCodedFont_Format1;
import com.mgz.afp.modca.MCF_MapCodedFont_Format2;
import com.mgz.afp.modca.MMO_MapMediumOverlay;
import com.mgz.afp.modca.MPS_MapPageSegment;
import com.mgz.afp.modca.PGD_PageDescriptor;
import com.mgz.afp.modca.TLE_TagLogicalElement;
import com.mgz.afp.ptoca.PTX_PresentationTextData;
import com.mgz.afp.ptoca.controlSequence.PTOCAControlSequence;
import com.mgz.afp.ptoca.controlSequence.PTOCAControlSequence.AMB_AbsoluteMoveBaseline;
import com.mgz.afp.ptoca.controlSequence.PTOCAControlSequence.AMI_AbsoluteMoveInline;
import com.mgz.afp.ptoca.controlSequence.PTOCAControlSequence.BLN_BeginLine;
import com.mgz.afp.ptoca.controlSequence.PTOCAControlSequence.GraphicCharacters;
import com.mgz.afp.ptoca.controlSequence.PTOCAControlSequence.RMB_RelativeMoveBaseline;
import com.mgz.afp.ptoca.controlSequence.PTOCAControlSequence.RMI_RelativeMoveInline;
import com.mgz.afp.ptoca.controlSequence.PTOCAControlSequence.SBI_SetBaselineIncrement;
import com.mgz.afp.ptoca.controlSequence.PTOCAControlSequence.SCFL_SetCodedFontLocal;
import com.mgz.afp.ptoca.controlSequence.PTOCAControlSequence.SEC_SetExtendedTextColor;
import com.mgz.afp.ptoca.controlSequence.PTOCAControlSequence.SIA_SetIntercharacterAdjustment;
import com.mgz.afp.ptoca.controlSequence.PTOCAControlSequence.SIM_SetInlineMargin;
import com.mgz.afp.ptoca.controlSequence.PTOCAControlSequence.STC_SetTextColor;
import com.mgz.afp.ptoca.controlSequence.PTOCAControlSequence.STO_SetTextOrientation;
import com.mgz.afp.ptoca.controlSequence.PTOCAControlSequence.SVI_SetVariableSpaceCharacterIncrement;
import com.mgz.afp.ptoca.controlSequence.PTOCAControlSequence.TRN_TransparentData;
import com.mgz.afp.ptoca.controlSequence.PTOCAControlSequence.UCT_UnicodeComplexText;
import com.mgz.afp.triplets.Triplet;
import java.io.OutputStream;
import java.util.List;
import java.util.ArrayDeque;
import java.util.Collections;
import java.util.Deque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
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
  private final Map<Short, String> fontMap = new HashMap<>();
  private final PdfDocument pdfDoc;
  private final Document document;
  private final PdfDictionary dpartRoot;
  private final PdfTextState textState;
  private final PdfGraphicsState graphicsState;
  private final PdfBarcodeState barcodeState;
  private PdfFont fallbackFont;
  private PdfPage currentPage;
  private PdfCanvas currentCanvas;
  private float defaultPageWidth = -1;
  private float defaultPageHeight = -1;
  private float defaultScaleX = 1.0f;
  private float defaultScaleY = 1.0f;

  public PdfHandler(OutputStream os) {
    this.pdfDoc = new PdfDocument(new PdfWriter(os));
    this.document = new Document(pdfDoc);
    this.textState = new PdfTextState();
    this.graphicsState = new PdfGraphicsState();
    this.barcodeState = new PdfBarcodeState();

    try {
      this.fallbackFont = PdfFontFactory.createFont(StandardFonts.HELVETICA);
    } catch (Exception e) {
      // Ignored for now, will fallback to iText default
    }

    // Initialize DPartRoot for PDF/VT compliance (ISO 16612-2)
    this.dpartRoot = new PdfDictionary();
    dpartRoot.put(PdfName.Type, new PdfName("DPartRoot"));
    pdfDoc.getCatalog().put(new PdfName("DPartRoot"), dpartRoot);

    // Initialize DTree root node
    PdfDictionary dtreeRoot = new PdfDictionary();
    dtreeRoot.put(PdfName.Type, new PdfName("DPart"));
    dpartRoot.put(new PdfName("DTree"), dtreeRoot);

    // Initialize OutputIntents array for PDF/X and PDF/VT compliance
    pdfDoc.getCatalog().put(PdfName.OutputIntents, new PdfArray());
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
          this.currentCanvas = new PdfCanvas(currentPage);
          currentPage.put(PdfName.DPart, dpart);
          textState.reset();
          graphicsState.reset();
          barcodeState.reset();
          currentCanvas.setFillColor(DeviceRgb.BLACK);

          // Apply default page size and transformation if defined (from PGD)
          if (defaultPageWidth > 0 && defaultPageHeight > 0) {
            currentPage.setMediaBox(new com.itextpdf.kernel.geom.Rectangle(defaultPageWidth, defaultPageHeight));
            applyTransformation(defaultPageHeight, defaultScaleX, defaultScaleY);
          }
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
    } else if (sf instanceof MCF_MapCodedFont_Format1 mcf1) {
      if (mcf1.getRepeatingGroups() != null) {
        for (MCF_MapCodedFont_Format1.MCF_RepeatingGroup rg : mcf1.getRepeatingGroups()) {
          fontMap.put(rg.getCodedFontLocalID(), rg.getCodedFontName());
        }
      }
    } else if (sf instanceof MCF_MapCodedFont_Format2 mcf2) {
      if (mcf2.getRepeatingGroups() != null) {
        for (IRepeatingGroup irg : mcf2.getRepeatingGroups()) {
          if (irg instanceof MCF_MapCodedFont_Format2.MCF_RepeatingGroup rg && rg.getTriplets() != null) {
            Short lid = null;
            String name = null;
            for (Triplet t : rg.getTriplets()) {
              if (t instanceof Triplet.ResourceLocalIdentifier rli
                  && rli.getResourceType() == Triplet.ResourceLocalIdentifier.RLI_ResourceType.CodedFont) {
                lid = rli.getResourceLocalID();
              } else if (t instanceof Triplet.FullyQualifiedName fqn
                  && fqn.getType() == Triplet.GlobalID_Use.CodedFontNameReference) {
                name = fqn.getNameAsString();
              }
            }
            if (lid != null && name != null) {
              fontMap.put(lid, name);
            }
          }
        }
      }
    } else if (sf instanceof PGD_PageDescriptor pgd) {
      float scaleX = CoordinateTransformer.getScaleFactor(pgd.getxUnitBase(), pgd.getxUnitsPerUnitBase());
      float scaleY = CoordinateTransformer.getScaleFactor(pgd.getyUnitBase(), pgd.getyUnitsPerUnitBase());
      float widthPoints = pgd.getxSize() * scaleX;
      float heightPoints = pgd.getySize() * scaleY;

      if (currentPage != null) {
        currentPage.setMediaBox(new com.itextpdf.kernel.geom.Rectangle(widthPoints, heightPoints));
        applyTransformation(heightPoints, scaleX, scaleY);
      } else {
        // Store as default if no page is active
        this.defaultPageWidth = widthPoints;
        this.defaultPageHeight = heightPoints;
        this.defaultScaleX = scaleX;
        this.defaultScaleY = scaleY;
      }
    } else if (sf instanceof PTX_PresentationTextData ptx) {
      if (ptx.getControlSequences() != null) {
        for (PTOCAControlSequence cs : ptx.getControlSequences()) {
          handleControlSequence(cs);
        }
      }
    } else if (sf instanceof GAD_GraphicsData gad) {
      if (gad.getDrawingOrders() != null) {
        for (GAD_DrawingOrder order : gad.getDrawingOrders()) {
          if (order != null) {
            handleDrawingOrder(order);
          }
        }
      }
    } else if (sf instanceof BDD_BarCodeDataDescriptor bdd) {
      barcodeState.setBarcodeType(bdd.getBarcodeType());
      barcodeState.setBarcodeModifier(bdd.getBarcodeModifier());
      barcodeState.setFontLocalIDForHRI(bdd.getFontLocalIDForHRI());
      barcodeState.setColor(bdd.getColor());
      barcodeState.setModuleWidthInMils(bdd.getModuleWidthInMils());
      barcodeState.setElementHeight(bdd.getElementHeight());
      barcodeState.setHeightMultiplier(bdd.getHeightMultiplier());
      barcodeState.setWideToNarrowRatio(bdd.getWideToNarrowRatio());
    }

    // TODO: Implement iText 9 based translation logic
  }

  private void handleDrawingOrder(GAD_DrawingOrder order) {
    if (order instanceof GSCOL_SetColor gscol) {
      graphicsState.setColor(gscol.getColor());
    } else if (order instanceof GSECOL_SetExtendedColor gsecol) {
      graphicsState.setColor(gsecol.getColor());
    } else if (order instanceof GSLW_SetLineWidth gslw) {
      graphicsState.setLineWidth(gslw.getLineWidth());
    } else if (order instanceof GSLT_SetLineType gslt) {
      graphicsState.setLineType(gslt.getLineType());
    } else if (order instanceof GSLE_SetLineEnd gsle) {
      graphicsState.setLineEnd(gsle.getLineEnd());
    } else if (order instanceof GSLJ_SetLineJoin gslj) {
      graphicsState.setLineJoin(gslj.getLineJoin());
    } else if (order instanceof GSMX_SetMix gsmx) {
      graphicsState.setMixMode(gsmx.getMixMode());
    } else if (order instanceof GSBMX_SetBackgroundMix gsbmx) {
      graphicsState.setBackgroundMixMode(gsbmx.getMixMode());
    } else if (order instanceof GSPS_SetPatternSet gsps) {
      graphicsState.setPatternSet(gsps.getPatternLocalID());
    } else if (order instanceof GSPT_SetPatternSymbol gspt) {
      graphicsState.setPatternSymbol(gspt.getPatternSymbolCodePoint());
    } else if (order instanceof GSMS_SetMarkerSet gsms) {
      graphicsState.setMarkerSet(gsms.getMarkerSetLocalID());
    } else if (order instanceof GSMT_SetMarkerSymbol gsmt) {
      graphicsState.setMarkerSymbol(gsmt.getMarkerSymbolCodePoint());
    } else if (order instanceof GSMP_SetMarkerPrecision gsmp) {
      graphicsState.setMarkerPrecision(gsmp.getMarkerPrecision());
    } else if (order instanceof GSAP_SetArcParameters gsap) {
      graphicsState.setArcTransformP(gsap.getArcTransformP());
      graphicsState.setArcTransformQ(gsap.getArcTransformQ());
      graphicsState.setArcTransformR(gsap.getArcTransformR());
      graphicsState.setArcTransformS(gsap.getArcTransformS());
    } else if (order instanceof GSPCOL_SetProcessColor gspcol) {
      graphicsState.setProcessColorSpace(gspcol.getColorSpace());
      graphicsState.setNrOfBitsComponent1(gspcol.getNrOfBitsComponent1());
      graphicsState.setNrOfBitsComponent2(gspcol.getNrOfBitsComponent2());
      graphicsState.setNrOfBitsComponent3(gspcol.getNrOfBitsComponent3());
      graphicsState.setNrOfBitsComponent4(gspcol.getNrOfBitsComponent4());
      graphicsState.setProcessColorValue(gspcol.getColorValue());
    } else if (order instanceof GSCP_SetCurrentPosition gscp) {
      graphicsState.setCurrentX(gscp.getCoordinateX());
      graphicsState.setCurrentY(gscp.getCoordinateY());
    } else if (order instanceof GBAR_BeginArea gbar) {
      graphicsState.setInArea(true);
      // Bit 1 (0x40): BOUNDARY indicator. B'1' = Draw boundary lines.
      graphicsState.setDrawAreaBoundary((gbar.getInternalFlags() & 0x40) != 0);
      // Bit 2 (0x20): INSIDE indicator. B'0' = Alternate (Even-Odd), B'1' = Nonzero Winding.
      graphicsState.setEvenOddRule((gbar.getInternalFlags() & 0x20) == 0);
    } else if (order instanceof GEAR_EndArea) {
      if (currentCanvas != null && graphicsState.isInArea()) {
        currentCanvas.closePath();
        if (graphicsState.isDrawAreaBoundary()) {
          if (graphicsState.isEvenOddRule()) {
            currentCanvas.eoFillStroke();
          } else {
            currentCanvas.fillStroke();
          }
        } else {
          if (graphicsState.isEvenOddRule()) {
            currentCanvas.eoFill();
          } else {
            currentCanvas.fill();
          }
        }
      }
      graphicsState.setInArea(false);
    } else if (order instanceof GAD_DrawingOrder.DrawingOrder_HasPoints line && (line instanceof GLINE_LineAtGivenPosition || line instanceof GCLINE_LineAtCurrentPosition)) {
      if (currentCanvas != null && line.getPoints() != null) {
        applyGraphicsState();
        if (line instanceof GLINE_LineAtGivenPosition) {
          boolean first = true;
          for (GAD_DrawingOrder.GOCA_Point p : line.getPoints()) {
            if (p != null) {
              if (first) {
                currentCanvas.moveTo(p.xCoordinate(), p.yCoordinate());
                first = false;
              } else {
                currentCanvas.lineTo(p.xCoordinate(), p.yCoordinate());
              }
              graphicsState.setCurrentX(p.xCoordinate());
              graphicsState.setCurrentY(p.yCoordinate());
            }
          }
        } else {
          currentCanvas.moveTo(graphicsState.getCurrentX(), graphicsState.getCurrentY());
          for (GAD_DrawingOrder.GOCA_Point p : line.getPoints()) {
            if (p != null) {
              currentCanvas.lineTo(p.xCoordinate(), p.yCoordinate());
              graphicsState.setCurrentX(p.xCoordinate());
              graphicsState.setCurrentY(p.yCoordinate());
            }
          }
        }
        if (!graphicsState.isInArea() && graphicsState.getLineType() != 8) {
          currentCanvas.stroke();
        }
      }
    } else if (order instanceof GRLINE_RelativeLineAtGivenPosition grline) {
      if (currentCanvas != null && grline.startPoint != null) {
        applyGraphicsState();
        int curX = grline.startPoint.xCoordinate();
        int curY = grline.startPoint.yCoordinate();
        currentCanvas.moveTo(curX, curY);
        if (grline.relativeOffsets != null) {
          for (GAD_DrawingOrder.GOCA_RelativePoint rp : grline.relativeOffsets) {
            if (rp != null) {
              curX += rp.xOffset();
              curY += rp.yOffset();
              currentCanvas.lineTo(curX, curY);
            }
          }
        }
        graphicsState.setCurrentX(curX);
        graphicsState.setCurrentY(curY);
        if (!graphicsState.isInArea() && graphicsState.getLineType() != 8) {
          currentCanvas.stroke();
        }
      }
    } else if (order instanceof GCRLINE_RelativeLineAtCurrentPosition gcrline) {
      if (currentCanvas != null) {
        applyGraphicsState();
        int curX = graphicsState.getCurrentX();
        int curY = graphicsState.getCurrentY();
        currentCanvas.moveTo(curX, curY);
        if (gcrline.relativeOffsets != null) {
          for (GAD_DrawingOrder.GOCA_RelativePoint rp : gcrline.relativeOffsets) {
            if (rp != null) {
              curX += rp.xOffset();
              curY += rp.yOffset();
              currentCanvas.lineTo(curX, curY);
            }
          }
        }
        graphicsState.setCurrentX(curX);
        graphicsState.setCurrentY(curY);
        if (!graphicsState.isInArea() && graphicsState.getLineType() != 8) {
          currentCanvas.stroke();
        }
      }
    } else if (order instanceof GBOX_BoxAtGivenPosition gbox) {
      if (currentCanvas != null && gbox.getFirstCorner() != null && gbox.getDiagonalCorner() != null) {
        applyGraphicsState();
        float x = Math.min(gbox.getFirstCorner().xCoordinate(), gbox.getDiagonalCorner().xCoordinate());
        float y = Math.min(gbox.getFirstCorner().yCoordinate(), gbox.getDiagonalCorner().yCoordinate());
        float width = Math.abs(gbox.getFirstCorner().xCoordinate() - gbox.getDiagonalCorner().xCoordinate());
        float height = Math.abs(gbox.getFirstCorner().yCoordinate() - gbox.getDiagonalCorner().yCoordinate());
        currentCanvas.rectangle(x, y, width, height);
        if (!graphicsState.isInArea() && graphicsState.getLineType() != 8) {
          currentCanvas.stroke();
        }
        graphicsState.setCurrentX(gbox.getDiagonalCorner().xCoordinate());
        graphicsState.setCurrentY(gbox.getDiagonalCorner().yCoordinate());
      }
    } else if (order instanceof GCBOX_BoxAtCurrentPosition gcbox) {
      if (currentCanvas != null && gcbox.getDiagonalCorner() != null) {
        applyGraphicsState();
        float x = Math.min(graphicsState.getCurrentX(), gcbox.getDiagonalCorner().xCoordinate());
        float y = Math.min(graphicsState.getCurrentY(), gcbox.getDiagonalCorner().yCoordinate());
        float width = Math.abs(graphicsState.getCurrentX() - gcbox.getDiagonalCorner().xCoordinate());
        float height = Math.abs(graphicsState.getCurrentY() - gcbox.getDiagonalCorner().yCoordinate());
        currentCanvas.rectangle(x, y, width, height);
        if (!graphicsState.isInArea() && graphicsState.getLineType() != 8) {
          currentCanvas.stroke();
        }
        graphicsState.setCurrentX(gcbox.getDiagonalCorner().xCoordinate());
        graphicsState.setCurrentY(gcbox.getDiagonalCorner().yCoordinate());
      }
    } else if (order instanceof GFARC_FullArcAtGivenPosition gfarc) {
      if (gfarc.getArcCenter() != null) {
        renderFullArc(gfarc.getArcCenter().xCoordinate(), gfarc.getArcCenter().yCoordinate(),
            gfarc.getMultiplierIntegerPortion(), gfarc.getMultiplierFractionalPortion());
      }
    } else if (order instanceof GCFARC_FullArcAtCurrentPosition gcfarc) {
      renderFullArc(graphicsState.getCurrentX(), graphicsState.getCurrentY(),
          gcfarc.getMultiplierIntegerPortion(), gcfarc.getMultiplierFractionalPortion());
    } else if (order instanceof GPARC_PartialArcAtGivenPosition gparc) {
      if (gparc.getLineStartPoint() != null && gparc.getArcCenter() != null) {
        renderPartialArc(gparc.getLineStartPoint().xCoordinate(), gparc.getLineStartPoint().yCoordinate(),
            gparc.getArcCenter().xCoordinate(), gparc.getArcCenter().yCoordinate(),
            gparc.getMultiplierIntegerPortion(), gparc.getMultiplierFractionalPortion(),
            gparc.getStartAngle(), gparc.getSweepAngle());
      }
    } else if (order instanceof GCPARC_PartialArcAtCurrentPosition gcparc) {
      if (gcparc.getArcCenter() != null) {
        renderPartialArc(graphicsState.getCurrentX(), graphicsState.getCurrentY(),
            gcparc.getArcCenter().xCoordinate(), gcparc.getArcCenter().yCoordinate(),
            gcparc.getMultiplierIntegerPortion(), gcparc.getMultiplierFractionalPortion(),
            gcparc.getStartAngle(), gcparc.getSweepAngle());
      }
    } else if (order instanceof GFLT_FilletAtGivenPosition gflt) {
      renderFillet(gflt.getPoints(), false);
    } else if (order instanceof GCFLT_FilletAtCurrentPosition gcflt) {
      renderFillet(gcflt.getPoints(), true);
    }
  }

  private void renderFillet(List<GAD_DrawingOrder.GOCA_Point> points, boolean atCurrentPosition) {
    if (currentCanvas == null || points == null || points.isEmpty()) {
      return;
    }
    if (atCurrentPosition && points.size() < 1) {
      return;
    }
    if (!atCurrentPosition && points.size() < 2) {
      return;
    }

    applyGraphicsState();
    float x0, y0;
    int startIdx;

    if (atCurrentPosition) {
      x0 = graphicsState.getCurrentX();
      y0 = graphicsState.getCurrentY();
      startIdx = 0;
    } else {
      x0 = points.get(0).xCoordinate();
      y0 = points.get(0).yCoordinate();
      startIdx = 1;
    }

    currentCanvas.moveTo(x0, y0);
    int n = points.size();

    // If only two points total (including current position if applicable), it's a straight line
    if ((atCurrentPosition && n == 1) || (!atCurrentPosition && n == 2)) {
      GAD_DrawingOrder.GOCA_Point pEnd = points.get(n - 1);
      currentCanvas.lineTo(pEnd.xCoordinate(), pEnd.yCoordinate());
      graphicsState.setCurrentX(pEnd.xCoordinate());
      graphicsState.setCurrentY(pEnd.yCoordinate());
    } else {
      // Fillet logic: sequence of quadratic Beziers
      float currX = x0;
      float currY = y0;

      for (int i = startIdx; i < n - 1; i++) {
        GAD_DrawingOrder.GOCA_Point pControl = points.get(i);
        GAD_DrawingOrder.GOCA_Point pNext = points.get(i + 1);

        float nextX, nextY;
        if (i == n - 2) {
          // Last segment ends at the last point
          nextX = pNext.xCoordinate();
          nextY = pNext.yCoordinate();
        } else {
          // Intermediate segments end at the midpoint of PiPi+1
          nextX = (pControl.xCoordinate() + pNext.xCoordinate()) / 2.0f;
          nextY = (pControl.yCoordinate() + pNext.yCoordinate()) / 2.0f;
        }

        // Convert quadratic to cubic Bezier
        // C1 = A + 2/3 * (C - A)
        // C2 = B + 2/3 * (C - B)
        float cx = pControl.xCoordinate();
        float cy = pControl.yCoordinate();
        float c1x = currX + 2.0f / 3.0f * (cx - currX);
        float c1y = currY + 2.0f / 3.0f * (cy - currY);
        float c2x = nextX + 2.0f / 3.0f * (cx - nextX);
        float c2y = nextY + 2.0f / 3.0f * (cy - nextY);

        currentCanvas.curveTo(c1x, c1y, c2x, c2y, nextX, nextY);

        currX = nextX;
        currY = nextY;
      }
      graphicsState.setCurrentX(Math.round(currX));
      graphicsState.setCurrentY(Math.round(currY));
    }

    if (!graphicsState.isInArea() && graphicsState.getLineType() != 8) {
      currentCanvas.stroke();
    }
  }

  private void renderPartialArc(int xStart, int yStart, int xc, int yc, short multiplierInt, short multiplierFrac, int startAngle, int sweepAngle) {
    if (currentCanvas != null) {
      applyGraphicsState();
      float m = multiplierInt + (multiplierFrac / 256.0f);
      float p = graphicsState.getArcTransformP();
      float q = graphicsState.getArcTransformQ();
      float r = graphicsState.getArcTransformR();
      float s = graphicsState.getArcTransformS();

      double startDeg = startAngle / 65536.0;
      double sweepDeg = sweepAngle / 65536.0;
      double endDeg = startDeg + sweepDeg;

      // Start point of the arc on the unit circle is (cos, sin)
      // Transformed start point: [P Q; R S] * M * [cos; sin] + [xc; yc]
      double startRad = Math.toRadians(startDeg);
      float arcStartX = xc + (p * m * (float) Math.cos(startRad)) + (r * m * (float) Math.sin(startRad));
      float arcStartY = yc + (q * m * (float) Math.cos(startRad)) + (s * m * (float) Math.sin(startRad));

      currentCanvas.moveTo(xStart, yStart);
      currentCanvas.lineTo(arcStartX, arcStartY);

      currentCanvas.saveState();
      currentCanvas.concatMatrix(p * m, q * m, r * m, s * m, xc, yc);
      // Draw partial arc of unit circle
      currentCanvas.arc(-1, -1, 1, 1, startDeg, sweepDeg);
      currentCanvas.restoreState();

      if (!graphicsState.isInArea() && graphicsState.getLineType() != 8) {
        currentCanvas.stroke();
      }

      // Update current position to the end of the arc
      double endRad = Math.toRadians(endDeg);
      float arcEndX = xc + (p * m * (float) Math.cos(endRad)) + (r * m * (float) Math.sin(endRad));
      float arcEndY = yc + (q * m * (float) Math.cos(endRad)) + (s * m * (float) Math.sin(endRad));
      graphicsState.setCurrentX(Math.round(arcEndX));
      graphicsState.setCurrentY(Math.round(arcEndY));
    }
  }

  private void renderFullArc(int xc, int yc, short multiplierInt, short multiplierFrac) {
    if (currentCanvas != null) {
      applyGraphicsState();
      float m = multiplierInt + (multiplierFrac / 256.0f);

      // Arc transformation matrix elements (P, Q, R, S)
      float p = graphicsState.getArcTransformP();
      float q = graphicsState.getArcTransformQ();
      float r = graphicsState.getArcTransformR();
      float s = graphicsState.getArcTransformS();

      // Effective transformation: T = [P Q; R S] * M
      // But we draw a unit circle at (0,0) and want it to be transformed to the ellipse.
      // So the matrix is [p*m q*m r*m s*m xc yc]
      currentCanvas.saveState();
      currentCanvas.concatMatrix(p * m, q * m, r * m, s * m, xc, yc);
      currentCanvas.circle(0, 0, 1);
      currentCanvas.restoreState();

      if (!graphicsState.isInArea() && graphicsState.getLineType() != 8) {
        currentCanvas.stroke();
      }
    }
  }

  private void applyGraphicsState() {
    if (currentCanvas != null) {
      Color color = ColorHandler.getColor(graphicsState.getColor());
      currentCanvas.setStrokeColor(color);
      currentCanvas.setFillColor(color);

      // Map GOCA line type to PDF dash pattern
      switch (graphicsState.getLineType()) {
        case 1 -> currentCanvas.setLineDash(new float[]{1, 2}, 0); // Dotted
        case 2 -> currentCanvas.setLineDash(new float[]{3, 2}, 0); // Short Dashed
        case 3 -> currentCanvas.setLineDash(new float[]{3, 2, 1, 2}, 0); // Dash Dot
        case 4 -> currentCanvas.setLineDash(new float[]{1, 2, 1, 2}, 0); // Double Dotted
        case 5 -> currentCanvas.setLineDash(new float[]{6, 2}, 0); // Long Dashed
        case 6 -> currentCanvas.setLineDash(new float[]{6, 2, 1, 2, 1, 2}, 0); // Dash Double Dot
        default -> {
          // In iText 9, passing null to setLineDash might throw NPE if not handled
          // Use an empty array or the specific method for solid line if available.
          // According to iText 7/8/9, setLineDash(new float[0], 0) is usually the way for solid.
          currentCanvas.setLineDash(new float[0], 0);
        }
      }

      currentCanvas.setLineWidth(graphicsState.getLineWidth() > 0 ? graphicsState.getLineWidth() : 1.0f);

      // Map GOCA line end to iText 9 LineCap style
      // 0: Default, 1: Flat, 2: Square, 3: Round
      switch (graphicsState.getLineEnd()) {
        case Flat -> currentCanvas.setLineCapStyle(0); // BUTT
        case Square -> currentCanvas.setLineCapStyle(2); // PROJECTING_SQUARE
        case Round, Default -> currentCanvas.setLineCapStyle(1); // ROUND (AFP standard default)
      }

      // Map GOCA line join to iText 9 LineJoin style
      // 0: Default, 1: Bevel, 2: Round, 3: Miter
      switch (graphicsState.getLineJoin()) {
        case Bevel -> currentCanvas.setLineJoinStyle(2); // BEVEL
        case Round, Default -> currentCanvas.setLineJoinStyle(1); // ROUND (AFP standard default)
        case Miter -> currentCanvas.setLineJoinStyle(0); // MITER
      }
    }
  }

  private void handleControlSequence(PTOCAControlSequence cs) {
    if (cs instanceof AMI_AbsoluteMoveInline ami) {
      textState.setInlinePos(ami.getDisplacement());
    } else if (cs instanceof RMI_RelativeMoveInline rmi) {
      textState.setInlinePos(textState.getInlinePos() + rmi.getIncrement());
    } else if (cs instanceof AMB_AbsoluteMoveBaseline amb) {
      textState.setBaselinePos(amb.getDisplacement());
    } else if (cs instanceof RMB_RelativeMoveBaseline rmb) {
      textState.setBaselinePos(textState.getBaselinePos() + rmb.getIncrement());
    } else if (cs instanceof STO_SetTextOrientation sto) {
      textState.setIOrientation(sto.getxOrientation());
      textState.setBOrientation(sto.getyOrientation());
    } else if (cs instanceof SCFL_SetCodedFontLocal scfl) {
      textState.setFontLid(scfl.getCodedFontLocalID());
    } else if (cs instanceof STC_SetTextColor stc) {
      textState.setTextColor(stc.getForegroundColor());
      if (currentCanvas != null) {
        currentCanvas.setFillColor(ColorHandler.getColor(stc.getForegroundColor()));
      }
    } else if (cs instanceof SEC_SetExtendedTextColor sec) {
      textState.setExtendedColorSpace(sec.getColorSpace());
      textState.setExtendedColorValue(sec.getColorValue());
      if (currentCanvas != null) {
        Color color = ColorHandler.getExtendedColor(sec.getColorSpace(), sec.getColorValue());
        if (color != null) {
          currentCanvas.setFillColor(color);
        }
      }
    } else if (cs instanceof SIA_SetIntercharacterAdjustment sia) {
      textState.setIntercharacterAdjustment(sia.getAdjustment());
    } else if (cs instanceof SVI_SetVariableSpaceCharacterIncrement svi) {
      textState.setVariableSpaceIncrement(svi.getIncrement());
    } else if (cs instanceof SIM_SetInlineMargin sim) {
      textState.setInlineMargin(sim.getDisplacement());
    } else if (cs instanceof SBI_SetBaselineIncrement sbi) {
      textState.setBaselineIncrement(sbi.getIncrement());
    } else if (cs instanceof BLN_BeginLine) {
      textState.setInlinePos(textState.getInlineMargin());
      textState.setBaselinePos(textState.getBaselinePos() + textState.getBaselineIncrement());
    } else if (cs instanceof TRN_TransparentData trn) {
      renderText(trn.getTransparentData());
    } else if (cs instanceof GraphicCharacters gc) {
      renderText(gc.getText());
    } else if (cs instanceof UCT_UnicodeComplexText uct) {
      renderText(uct.getText());
    }
  }

  private void renderText(String text) {
    if (text == null || text.isEmpty() || currentCanvas == null) {
      return;
    }

    try {
      PdfFont font = fallbackFont;
      if (font == null) {
        font = PdfFontFactory.createFont(StandardFonts.HELVETICA);
      }

      int afpX = CoordinateTransformer.getAfpX(textState.getInlinePos(), textState.getBaselinePos(),
          textState.getIOrientation(), textState.getBOrientation());
      int afpY = CoordinateTransformer.getAfpY(textState.getInlinePos(), textState.getBaselinePos(),
          textState.getIOrientation(), textState.getBOrientation());

      // AFP coordinates are already in 1/1440 or similar, but the CTM will scale them to points.
      // However, text must be drawn without being flipped upside down.
      // We use a text matrix that sets the position but compensates for Y-flipping.
      // Rotation is also applied to the text matrix.
      double rad = Math.toRadians(textState.getIOrientation().getCode() / 128.0);
      float cos = (float) Math.cos(rad);
      float sin = (float) Math.sin(rad);

      currentCanvas.beginText()
          .setFontAndSize(font, 10)
          .setTextMatrix(cos, sin, sin, -cos, afpX, afpY)
          .showText(text)
          .endText();

      // Update inline position based on text width (approximation in AFP units)
      float widthPoints = font.getWidth(text, 10);
      float scaleX = currentPage.getMediaBox().getWidth() / (defaultPageWidth / defaultScaleX); // Rough scale
      if (defaultPageWidth > 0) {
        textState.setInlinePos(textState.getInlinePos() + (int) (widthPoints / defaultScaleX));
      }
    } catch (Exception e) {
      System.err.println("Error rendering text: " + e.getMessage());
    }
  }

  private void applyTransformation(float heightPoints, float scaleX, float scaleY) {
    if (currentCanvas != null) {
      AffineTransform at = CoordinateTransformer.getAfpToPdfTransform(heightPoints, scaleX, scaleY);
      currentCanvas.concatMatrix(at);
    }
  }

  @Override
  public void close() throws Exception {
    if (fieldCount.get() > 0 && pdfDoc.getNumberOfPages() == 0) {
      document.add(new Paragraph("AFP to PDF conversion in progress..."));
    }
    document.close();
  }

  /**
   * Returns an unmodifiable map of the Coded Font Local IDs to font resource names.
   *
   * @return the font map
   */
  public Map<Short, String> getFontMap() {
    return Collections.unmodifiableMap(fontMap);
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

  /**
   * Returns the current PTOCA text state.
   *
   * @return the text state
   */
  public PdfTextState getTextState() {
    return textState;
  }

  /**
   * Returns the current GOCA graphics state.
   *
   * @return the graphics state
   */
  public PdfGraphicsState getGraphicsState() {
    return graphicsState;
  }

  /**
   * Returns the current BCOCA barcode state.
   *
   * @return the barcode state
   */
  public PdfBarcodeState getBarcodeState() {
    return barcodeState;
  }
}
