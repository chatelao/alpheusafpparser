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
import com.itextpdf.kernel.colors.PatternColor;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.geom.AffineTransform;
import com.itextpdf.kernel.pdf.PdfArray;
import com.itextpdf.kernel.pdf.PdfDictionary;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfName;
import com.itextpdf.kernel.pdf.PdfStream;
import com.itextpdf.kernel.pdf.PdfOutputIntent;
import com.itextpdf.kernel.pdf.PdfPage;
import com.itextpdf.kernel.pdf.PdfString;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.kernel.pdf.canvas.PdfCanvas;
import com.itextpdf.kernel.pdf.colorspace.PdfPattern;
import com.itextpdf.kernel.pdf.xobject.PdfFormXObject;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import com.mgz.afp.base.IRepeatingGroup;
import com.mgz.afp.base.StructuredField;
import com.mgz.afp.base.handler.StructuredFieldHandler;
import com.mgz.afp.bcoca.BBC_BeginBarCodeObject;
import com.mgz.afp.bcoca.BDA_BarCodeData;
import com.mgz.afp.bcoca.BDD_BarCodeDataDescriptor;
import com.mgz.afp.bcoca.EBC_EndBarCodeObject;
import com.mgz.afp.enums.AFPOrientation;
import com.mgz.afp.enums.AFPUnitBase;
import com.mgz.afp.goca.GAD_DrawingOrder;
import com.mgz.afp.goca.GAD_DrawingOrder.GBAR_BeginArea;
import com.mgz.afp.goca.GAD_DrawingOrder.GBOX_BoxAtGivenPosition;
import com.mgz.afp.goca.GAD_DrawingOrder.GBSEG_BeginSegment;
import com.mgz.afp.goca.GAD_DrawingOrder.GCBEZ_CubicBezierCurveAtGivenPosition;
import com.mgz.afp.goca.GAD_DrawingOrder.GCBOX_BoxAtCurrentPosition;
import com.mgz.afp.goca.GAD_DrawingOrder.GCCBEZ_CubicBezierCurveAtCurrentPosition;
import com.mgz.afp.goca.GAD_DrawingOrder.GCFARC_FullArcAtCurrentPosition;
import com.mgz.afp.goca.GAD_DrawingOrder.GCFLT_FilletAtCurrentPosition;
import com.mgz.afp.goca.GAD_DrawingOrder.GCLINE_LineAtCurrentPosition;
import com.mgz.afp.goca.GAD_DrawingOrder.GCCHST_CharacterStringAtCurrentPosition;
import com.mgz.afp.goca.GAD_DrawingOrder.GCMRK_MarkerAtCurrentPosition;
import com.mgz.afp.goca.GAD_DrawingOrder.GCHST_CharacterStringAtGivenPosition;
import com.mgz.afp.goca.GAD_DrawingOrder.GCPARC_PartialArcAtCurrentPosition;
import com.mgz.afp.goca.GAD_DrawingOrder.GMRK_MarkerAtGivenPosition;
import com.mgz.afp.goca.GAD_DrawingOrder.GCOMT_Comment;
import com.mgz.afp.goca.GAD_DrawingOrder.GCRLINE_RelativeLineAtCurrentPosition;
import com.mgz.afp.goca.GAD_DrawingOrder.GEAR_EndArea;
import com.mgz.afp.goca.GAD_DrawingOrder.GESEG_EndSegment;
import com.mgz.afp.goca.GAD_DrawingOrder.GFARC_FullArcAtGivenPosition;
import com.mgz.afp.goca.GAD_DrawingOrder.GFLT_FilletAtGivenPosition;
import com.mgz.afp.goca.GAD_DrawingOrder.GLINE_LineAtGivenPosition;
import com.mgz.afp.goca.GAD_DrawingOrder.GNOP1_NopOperation;
import com.mgz.afp.goca.GAD_DrawingOrder.GPARC_PartialArcAtGivenPosition;
import com.mgz.afp.goca.GAD_DrawingOrder.GRLINE_RelativeLineAtGivenPosition;
import com.mgz.afp.goca.GAD_DrawingOrder.GSAP_SetArcParameters;
import com.mgz.afp.goca.GAD_DrawingOrder.GSBMX_SetBackgroundMix;
import com.mgz.afp.goca.GAD_DrawingOrder.GSCOL_SetColor;
import com.mgz.afp.goca.GAD_DrawingOrder.GSCP_SetCurrentPosition;
import com.mgz.afp.goca.GAD_DrawingOrder.GSECOL_SetExtendedColor;
import com.mgz.afp.goca.GAD_DrawingOrder.GSFLW_SetFractionLineWidth;
import com.mgz.afp.goca.GAD_DrawingOrder.GSLE_SetLineEnd;
import com.mgz.afp.goca.GAD_DrawingOrder.GSLJ_SetLineJoin;
import com.mgz.afp.goca.GAD_DrawingOrder.GSLT_SetLineType;
import com.mgz.afp.goca.GAD_DrawingOrder.GSLW_SetLineWidth;
import com.mgz.afp.goca.GAD_DrawingOrder.GSMP_SetMarkerPrecision;
import com.mgz.afp.goca.GAD_DrawingOrder.GSMS_SetMarkerSet;
import com.mgz.afp.goca.GAD_DrawingOrder.GSMT_SetMarkerSymbol;
import com.mgz.afp.goca.GAD_DrawingOrder.GSMX_SetMix;
import com.mgz.afp.goca.GAD_DrawingOrder.GSPCOL_SetProcessColor;
import com.mgz.afp.goca.GAD_DrawingOrder.GSPS_SetPatternSet;
import com.mgz.afp.goca.GAD_DrawingOrder.GSPT_SetPatternSymbol;
import com.mgz.afp.goca.GAD_DrawingOrder.GSCS_SetCharacterSet;
import com.mgz.afp.goca.GAD_GraphicsData;
import com.mgz.afp.ioca.IDD_ImageDataDescriptor;
import com.mgz.afp.ioca.IPD_ImagePictureData;
import com.mgz.afp.modca.BAG_BeginActiveEnvironmentGroup;
import com.mgz.afp.modca.BDT_BeginDocument;
import com.mgz.afp.modca.BIM_BeginImageObject;
import com.mgz.afp.modca.BMO_BeginOverlay;
import com.mgz.afp.modca.BNG_BeginNamedPageGroup;
import com.mgz.afp.modca.BOG_BeginObjectEnvironmentGroup;
import com.mgz.afp.modca.BPG_BeginPage;
import com.mgz.afp.modca.BPS_BeginPageSegment;
import com.mgz.afp.modca.BRG_BeginResourceGroup;
import com.mgz.afp.modca.BSG_BeginResourceEnvironmentGroup;
import com.mgz.afp.modca.EAG_EndActiveEnvironmentGroup;
import com.mgz.afp.modca.EDT_EndDocument;
import com.mgz.afp.modca.EIM_EndImageObject;
import com.mgz.afp.modca.EMO_EndOverlay;
import com.mgz.afp.modca.ENG_EndNamedPageGroup;
import com.mgz.afp.modca.EOG_EndObjectEnvironmentGroup;
import com.mgz.afp.modca.EPG_EndPage;
import com.mgz.afp.modca.EPS_EndPageSegment;
import com.mgz.afp.modca.ERG_EndResourceGroup;
import com.mgz.afp.modca.ESG_EndResourceEnvironmentGroup;
import com.mgz.afp.modca.IPO_IncludePageOverlay;
import com.mgz.afp.modca.IPS_IncludePageSegment;
import com.mgz.afp.modca.MCF_MapCodedFont_Format1;
import com.mgz.afp.modca.MCF_MapCodedFont_Format2;
import com.mgz.afp.modca.MDR_MapDataResource;
import com.mgz.afp.modca.MMO_MapMediumOverlay;
import com.mgz.afp.modca.MPS_MapPageSegment;
import com.mgz.afp.modca.OBD_ObjectAreaDescriptor;
import com.mgz.afp.modca.OBP_ObjectAreaPosition;
import com.mgz.afp.modca.PGD_PageDescriptor;
import com.mgz.afp.modca.TLE_TagLogicalElement;
import com.mgz.afp.ptoca.PTX_PresentationTextData;
import com.mgz.afp.ptoca.controlSequence.PTOCAControlSequence;
import com.mgz.afp.ptoca.controlSequence.PTOCAControlSequence.AMB_AbsoluteMoveBaseline;
import com.mgz.afp.ptoca.controlSequence.PTOCAControlSequence.AMI_AbsoluteMoveInline;
import com.mgz.afp.ptoca.controlSequence.PTOCAControlSequence.BLN_BeginLine;
import com.mgz.afp.ptoca.controlSequence.PTOCAControlSequence.BSU_BeginSuppression;
import com.mgz.afp.ptoca.controlSequence.PTOCAControlSequence.DBR_DrawBaxisRule;
import com.mgz.afp.ptoca.controlSequence.PTOCAControlSequence.DIR_DrawIaxisRule;
import com.mgz.afp.ptoca.controlSequence.PTOCAControlSequence.ESU_EndSuppression;
import com.mgz.afp.ptoca.controlSequence.PTOCAControlSequence.GraphicCharacters;
import com.mgz.afp.ptoca.controlSequence.PTOCAControlSequence.NOP_NoOperation;
import com.mgz.afp.ptoca.controlSequence.PTOCAControlSequence.OVS_Overstrike;
import com.mgz.afp.ptoca.controlSequence.PTOCAControlSequence.RMB_RelativeMoveBaseline;
import com.mgz.afp.ptoca.controlSequence.PTOCAControlSequence.RMI_RelativeMoveInline;
import com.mgz.afp.ptoca.controlSequence.PTOCAControlSequence.RPS_RepeatString;
import com.mgz.afp.ptoca.controlSequence.PTOCAControlSequence.USC_Underscore;
import com.mgz.afp.ptoca.controlSequence.PTOCAControlSequence.SBI_SetBaselineIncrement;
import com.mgz.afp.ptoca.controlSequence.PTOCAControlSequence.SCFL_SetCodedFontLocal;
import com.mgz.afp.ptoca.controlSequence.PTOCAControlSequence.SEC_SetExtendedTextColor;
import com.mgz.afp.ptoca.controlSequence.PTOCAControlSequence.SIA_SetIntercharacterAdjustment;
import com.mgz.afp.ptoca.controlSequence.PTOCAControlSequence.SIM_SetInlineMargin;
import com.mgz.afp.ptoca.controlSequence.PTOCAControlSequence.STC_SetTextColor;
import com.mgz.afp.ptoca.controlSequence.PTOCAControlSequence.STO_SetTextOrientation;
import com.mgz.afp.ptoca.controlSequence.PTOCAControlSequence.SVI_SetVariableSpaceCharacterIncrement;
import com.mgz.afp.ptoca.controlSequence.PTOCAControlSequence.TBM_TemporaryBaselineMove;
import com.mgz.afp.ptoca.controlSequence.PTOCAControlSequence.TRN_TransparentData;
import com.mgz.afp.ptoca.controlSequence.PTOCAControlSequence.UCT_UnicodeComplexText;
import com.mgz.afp.triplets.Triplet;
import com.mgz.util.MnemonicPerformanceMonitor;
import java.io.OutputStream;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Deque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Skeleton implementation of {@link StructuredFieldHandler} for PDF generation.
 * Tracks MO:DCA structural boundaries to facilitate PDF/VT DPart mapping.
 */
public class PdfHandler implements StructuredFieldHandler {

  /**
   * Represents a font resource with its name and size.
   */
  public record FontResource(String name, float size) {}

  private final AtomicLong fieldCount = new AtomicLong(0);
  private final Deque<StructuredField> structureStack = new ArrayDeque<>();
  private final Deque<PdfDictionary> dpartStack = new ArrayDeque<>();
  private final Deque<PdfCanvas> canvasStack = new ArrayDeque<>();
  private final Map<String, PdfFormXObject> resourceCache = new HashMap<>();
  private final Map<String, com.itextpdf.kernel.pdf.colorspace.PdfPattern.Tiling> patternCache = new HashMap<>();
  private final Map<String, com.itextpdf.kernel.pdf.xobject.PdfImageXObject> imageCache = new HashMap<>();
  private final Set<String> mmoResources = new HashSet<>();
  private final Set<String> mpsResources = new HashSet<>();
  private final Deque<Map<Short, FontResource>> fontMapStack = new ArrayDeque<>();
  private final Deque<Boolean> canvasTransformedStack = new ArrayDeque<>();
  private final Set<Short> enabledSuppressionIDs = new HashSet<>();
  private final PdfFontRegistry fontRegistry;
  private final PdfDocument pdfDoc;
  private final Document document;
  private final PdfDictionary dpartRoot;
  private final PdfTextState textState;
  private final PdfGraphicsState graphicsState;
  private final PdfBarcodeState barcodeState;
  private final PdfImageState imageState;
  private PdfPage currentPage;
  private PdfCanvas currentCanvas;
  private float defaultPageWidth = -1;
  private float defaultPageHeight = -1;
  private float defaultScaleX = 1.0f;
  private float defaultScaleY = 1.0f;
  private boolean isCanvasTransformed = false;

  public PdfHandler(OutputStream os) {
    this(os, new PdfFontRegistry());
  }

  public PdfHandler(OutputStream os, PdfFontRegistry fontRegistry) {
    this.fontRegistry = fontRegistry;
    this.pdfDoc = new PdfDocument(new PdfWriter(os));
    this.textState = new PdfTextState();
    this.graphicsState = new PdfGraphicsState();
    this.barcodeState = new PdfBarcodeState();
    this.imageState = new PdfImageState();
    this.fontMapStack.push(new HashMap<>());

    this.document = new Document(pdfDoc);
    this.document.setFontProvider(fontRegistry.getFontProvider());
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

    if (MnemonicPerformanceMonitor.isEnabled()) {
      String rootName = MnemonicPerformanceMonitor.getSimpleName(sf.getClass());
      String mnemonic = MnemonicPerformanceMonitor.extractMnemonicFromString(rootName);
      MnemonicPerformanceMonitor.startWriteWithMnemonic(mnemonic);
    }

    try {
      handleInternal(sf);
    } finally {
      if (MnemonicPerformanceMonitor.isEnabled()) {
        MnemonicPerformanceMonitor.endWrite();
      }
    }
  }

  private void handleInternal(StructuredField sf) throws Exception {
    if (sf instanceof PTX_PresentationTextData
        || sf instanceof GAD_GraphicsData
        || sf instanceof BIM_BeginImageObject
        || sf instanceof BBC_BeginBarCodeObject
        || sf instanceof IPO_IncludePageOverlay
        || sf instanceof IPS_IncludePageSegment) {
      ensurePageExists();
    }

    if (sf.isBeginSF()) {
      structureStack.push(sf);

      if (sf instanceof BDT_BeginDocument
          || sf instanceof BPG_BeginPage
          || sf instanceof BMO_BeginOverlay
          || sf instanceof BPS_BeginPageSegment
          || sf instanceof BAG_BeginActiveEnvironmentGroup
          || sf instanceof BSG_BeginResourceEnvironmentGroup
          || sf instanceof BOG_BeginObjectEnvironmentGroup
          || sf instanceof BRG_BeginResourceGroup) {
        fontMapStack.push(new HashMap<>(fontMapStack.peek()));
      }

      if (sf instanceof BIM_BeginImageObject) {
        imageState.startNewImage();
      } else if (sf instanceof BBC_BeginBarCodeObject) {
        barcodeState.startNewBarcode();
      } else if (sf instanceof BMO_BeginOverlay bmo) {
        startResourceCapture(bmo.getName());
      } else if (sf instanceof BPS_BeginPageSegment bps) {
        startResourceCapture(bps.getName());
      } else if (sf instanceof BDT_BeginDocument || sf instanceof BNG_BeginNamedPageGroup || sf instanceof BPG_BeginPage) {
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

        // Map MO:DCA name to DPart metadata if available
        String name = null;
        if (sf instanceof BDT_BeginDocument bdt) {
          name = bdt.getName();
        } else if (sf instanceof BNG_BeginNamedPageGroup bng) {
          name = bng.getName();
        } else if (sf instanceof BPG_BeginPage bpg) {
          name = bpg.getName();
        }

        if (name != null && !name.trim().isEmpty()) {
          PdfDictionary property = new PdfDictionary();
          property.makeIndirect(pdfDoc);
          dpart.put(new PdfName("Property"), property);
          property.put(new PdfName("GroupName"), new PdfString(name.trim()));
        }

        if (sf instanceof BPG_BeginPage) {
          this.currentPage = pdfDoc.addNewPage();
          this.currentCanvas = new PdfCanvas(currentPage);
          currentPage.put(PdfName.DPart, dpart);
          textState.reset();
          graphicsState.reset();
          barcodeState.reset();
          imageState.reset();
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

        if (begin instanceof BDT_BeginDocument
            || begin instanceof BPG_BeginPage
            || begin instanceof BMO_BeginOverlay
            || begin instanceof BPS_BeginPageSegment
            || begin instanceof BAG_BeginActiveEnvironmentGroup
            || begin instanceof BSG_BeginResourceEnvironmentGroup
            || begin instanceof BOG_BeginObjectEnvironmentGroup
            || begin instanceof BRG_BeginResourceGroup) {
          if (fontMapStack.size() > 1) { // Never pop the base map
            fontMapStack.pop();
          }
        }

        if (begin instanceof BDT_BeginDocument || begin instanceof BNG_BeginNamedPageGroup || begin instanceof BPG_BeginPage) {
          if (!dpartStack.isEmpty()) {
            dpartStack.pop();
          }
        } else if (begin instanceof BMO_BeginOverlay || begin instanceof BPS_BeginPageSegment) {
          endResourceCapture();
        } else if (begin instanceof BIM_BeginImageObject) {
          imageState.setInImageObject(false);
          if (currentCanvas != null) {
            PdfImageRenderer.render(imageState, currentCanvas, imageCache);
          }
          imageState.reset();
        } else if (begin instanceof BBC_BeginBarCodeObject) {
          barcodeState.setInBarcodeObject(false);
          if (currentCanvas != null) {
            PdfBarcodeRenderer.render(barcodeState, currentCanvas);
          }
          barcodeState.reset();
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
        Map<Short, FontResource> currentFontMap = fontMapStack.peek();
        for (MCF_MapCodedFont_Format1.MCF_RepeatingGroup rg : mcf1.getRepeatingGroups()) {
          float size = PdfFontRegistry.extractSizeFromName(rg.getCodedFontName());
          if (size == 10.0f && rg.getFontCharacterSetName() != null) {
            size = PdfFontRegistry.extractSizeFromName(rg.getFontCharacterSetName());
          }
          currentFontMap.put(rg.getCodedFontLocalID(), new FontResource(rg.getCodedFontName(), size));
        }
      }
    } else if (sf instanceof MCF_MapCodedFont_Format2 mcf2) {
      if (mcf2.getRepeatingGroups() != null) {
        for (IRepeatingGroup irg : mcf2.getRepeatingGroups()) {
          if (irg instanceof MCF_MapCodedFont_Format2.MCF_RepeatingGroup rg && rg.getTriplets() != null) {
            Short lid = null;
            String name = null;
            float size = -1.0f;
            for (Triplet t : rg.getTriplets()) {
              if (t instanceof Triplet.ResourceLocalIdentifier rli
                  && rli.getResourceType() == Triplet.ResourceLocalIdentifier.RLI_ResourceType.CodedFont) {
                lid = rli.getResourceLocalID();
              } else if (t instanceof Triplet.FullyQualifiedName fqn) {
                if (fqn.getType() == Triplet.GlobalID_Use.CodedFontNameReference) {
                  name = fqn.getNameAsString();
                } else if (fqn.getType() == Triplet.GlobalID_Use.FontCharacterSetNameReference && name == null) {
                  name = fqn.getNameAsString();
                }
              } else if (t instanceof Triplet.FontDescriptorSpecification fds) {
                if (fds.fontHeight > 0) {
                  size = fds.fontHeight / 20.0f;
                }
              }
            }
            if (size <= 0 && name != null) {
              size = PdfFontRegistry.extractSizeFromName(name);
            }
            if (size <= 0) {
              size = 10.0f;
            }
            if (lid != null && name != null) {
              fontMapStack.peek().put(lid, new FontResource(name, size));
            }
          }
        }
      }
    } else if (sf instanceof MDR_MapDataResource mdr) {
      if (mdr.getRepeatingGroups() != null) {
        for (IRepeatingGroup irg : mdr.getRepeatingGroups()) {
          if (irg instanceof MDR_MapDataResource.MDR_RepeatingGroup rg && rg.getTriplets() != null) {
            Short lid = null;
            String name = null;
            float size = -1.0f;
            for (Triplet t : rg.getTriplets()) {
              if (t instanceof Triplet.ResourceLocalIdentifier rli
                  && rli.getResourceType() == Triplet.ResourceLocalIdentifier.RLI_ResourceType.CodedFont) {
                lid = rli.getResourceLocalID();
              } else if (t instanceof Triplet.FullyQualifiedName fqn) {
                if (fqn.getType() == Triplet.GlobalID_Use.CodedFontNameReference
                    || fqn.getType() == Triplet.GlobalID_Use.DataObjectExternalResourceReference) {
                  name = fqn.getNameAsString();
                } else if (fqn.getType() == Triplet.GlobalID_Use.FontCharacterSetNameReference && name == null) {
                  name = fqn.getNameAsString();
                }
              } else if (t instanceof Triplet.DataObjectFontDescriptor dofd) {
                if (dofd.specifiedVerticalFontSize > 0) {
                  size = dofd.specifiedVerticalFontSize / 20.0f;
                }
              }
            }
            if (size <= 0 && name != null) {
              size = PdfFontRegistry.extractSizeFromName(name);
            }
            if (size <= 0) {
              size = 10.0f;
            }
            if (lid != null && name != null) {
              fontMapStack.peek().put(lid, new FontResource(name, size));
            }
          }
        }
      }
    } else if (sf instanceof PGD_PageDescriptor pgd) {
      float scaleX = CoordinateTransformer.getScaleFactor(pgd.getxUnitBase(), pgd.getxUnitsPerUnitBase());
      float scaleY = CoordinateTransformer.getScaleFactor(pgd.getyUnitBase(), pgd.getyUnitsPerUnitBase());
      float widthPoints = pgd.getxSize() * scaleX;
      float heightPoints = pgd.getySize() * scaleY;

      this.defaultPageWidth = widthPoints;
      this.defaultPageHeight = heightPoints;
      this.defaultScaleX = scaleX;
      this.defaultScaleY = scaleY;

      if (currentPage != null) {
        currentPage.setMediaBox(new com.itextpdf.kernel.geom.Rectangle(widthPoints, heightPoints));
        applyTransformation(heightPoints, scaleX, scaleY);
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
      barcodeState.setHriFont(resolveFont(bdd.getFontLocalIDForHRI()));
      barcodeState.setColor(bdd.getColor());
      barcodeState.setModuleWidthInMils(bdd.getModuleWidthInMils());
      barcodeState.setElementHeight(bdd.getElementHeight());
      barcodeState.setHeightMultiplier(bdd.getHeightMultiplier());
      barcodeState.setWideToNarrowRatio(bdd.getWideToNarrowRatio());
      barcodeState.setUnitBase(bdd.getUnitBase());
      barcodeState.setUnitsPerUnitBaseX(bdd.getUnitsPerUnitBaseX());
      barcodeState.setUnitsPerUnitBaseY(bdd.getUnitsPerUnitBaseY());
    } else if (sf instanceof BDA_BarCodeData bda) {
      if (barcodeState.isInBarcodeObject()) {
        barcodeState.addBarcodeData(bda);
      }
    } else if (sf instanceof EBC_EndBarCodeObject) {
      // EBC is handled via structureStack.pop() in isEndSF() block,
      // but if there are multiple BDAs outside of a BBC/EBC (rare but possible),
      // or if EBC is used to trigger rendering without a BBC.
      // For now, BBC/EBC structure is the primary trigger.
    } else if (sf instanceof IDD_ImageDataDescriptor idd) {
      if (imageState.isInImageObject()) {
        imageState.setDescriptor(idd);
      }
    } else if (sf instanceof IPD_ImagePictureData ipd) {
      if (imageState.isInImageObject()) {
        imageState.addImageSegment(ipd);
      }
    } else if (sf instanceof OBP_ObjectAreaPosition obp) {
      if (imageState.isInImageObject()) {
        imageState.setxOrigin(obp.getRepeatingGroup().getxOrigin());
        imageState.setyOrigin(obp.getRepeatingGroup().getyOrigin());
      } else if (barcodeState.isInBarcodeObject()) {
        barcodeState.setxOrigin(obp.getRepeatingGroup().getxOrigin());
        barcodeState.setyOrigin(obp.getRepeatingGroup().getyOrigin());
      }
    } else if (sf instanceof OBD_ObjectAreaDescriptor obd) {
      // Currently sizes are mostly taken from image descriptor or BDD
    } else if (sf instanceof IPO_IncludePageOverlay ipo) {
      renderXObject(ipo.getOverlayName(), ipo.getxOrigin(), ipo.getyOrigin(), ipo.getxRotation());
    } else if (sf instanceof IPS_IncludePageSegment ips) {
      renderXObject(ips.getPageSegmentName(), ips.getxOrigin(), ips.getyOrigin(), null);
    }
  }

  private void renderXObject(String name, int x, int y, AFPOrientation rotation) {
    if (currentCanvas == null || name == null) {
      return;
    }
    PdfFormXObject xObject = resourceCache.get(name);
    if (xObject != null) {
      currentCanvas.saveState();
      // IPO/IPS origins are in the coordinate system of the including page/overlay.
      // Since we already applied the PGD-based transformation to currentCanvas,
      // we can just translate to (x, y).
      // However, PDF Y is bottom-up, and we want to position the top-left of the XObject at (x, y).
      // The XObject itself was also created with a coordinate system flip.

      float rotationDeg = 0;
      if (rotation != null && rotation != AFPOrientation.AsDefined) {
        rotationDeg = switch (rotation) {
          case ori90 -> 90;
          case ori180 -> 180;
          case ori270 -> 270;
          default -> 0;
        };
      }

      if (rotationDeg != 0) {
        double rad = Math.toRadians(rotationDeg);
        float cos = (float) Math.cos(rad);
        float sin = (float) Math.sin(rad);
        currentCanvas.concatMatrix(cos, sin, -sin, cos, x, y);
      } else {
        currentCanvas.concatMatrix(1, 0, 0, 1, x, y);
      }

      currentCanvas.addXObject(xObject);
      currentCanvas.restoreState();
    }
  }

  private void handleDrawingOrder(GAD_DrawingOrder order) {
    if (MnemonicPerformanceMonitor.isEnabled()) {
      String rootName = MnemonicPerformanceMonitor.getSimpleName(order.getClass());
      String mnemonic = MnemonicPerformanceMonitor.extractMnemonicFromString(rootName);
      MnemonicPerformanceMonitor.startWriteWithMnemonic(mnemonic);
    }

    try {
      handleDrawingOrderInternal(order);
    } finally {
      if (MnemonicPerformanceMonitor.isEnabled()) {
        MnemonicPerformanceMonitor.endWrite();
      }
    }
  }

  private void handleDrawingOrderInternal(GAD_DrawingOrder order) {
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
    } else if (order instanceof GSCS_SetCharacterSet gscs) {
      graphicsState.setCharacterSet(gscs.getCharacterSetLocalID());
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
    } else if (order instanceof GAD_DrawingOrder.GSCC_SetCharacterCell gscc) {
      float width = gscc.getWidthOfCharacterCellIntegerPart() + (gscc.getWidthOfCharacterCellFractionalPart() != null ? gscc.getWidthOfCharacterCellFractionalPart() / 65536.0f : 0.0f);
      float height = gscc.getHeightOfCharacterCellIntegerPart() + (gscc.getHeightOfCharacterCellFractionalPart() != null ? gscc.getHeightOfCharacterCellFractionalPart() / 65536.0f : 0.0f);
      graphicsState.setCharCellWidth(width);
      graphicsState.setCharCellHeight(height);
    } else if (order instanceof GAD_DrawingOrder.GSCA_SetCharacterAngle gsca) {
      if (gsca.getAnglePoint() != null) {
        graphicsState.setCharAngleX(gsca.getAnglePoint().xCoordinate());
        graphicsState.setCharAngleY(gsca.getAnglePoint().yCoordinate());
      }
    } else if (order instanceof GAD_DrawingOrder.GSCH_SetCharacterShear gsch) {
      graphicsState.setCharShearDividend(gsch.getDividendOfShearRatio());
      graphicsState.setCharShearDivisor(gsch.getDivisorOfShearRatio());
    } else if (order instanceof GAD_DrawingOrder.GSCD_SetCharacterDirection gscd) {
      graphicsState.setCharDirection(gscd.getCharacterDirection());
    } else if (order instanceof GAD_DrawingOrder.GSCR_SetCharacterPrecision gscr) {
      graphicsState.setCharPrecision(gscr.getCharacterPrecision());
    } else if (order instanceof GSFLW_SetFractionLineWidth gsflw) {
      graphicsState.setLineWidth(gsflw.getIntegralMultiplier() + (gsflw.getFractionalMultiplier() / 256.0f));
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
    } else if (order instanceof GAD_DrawingOrder.GBCP_BeginCustomPattern gbcp) {
      float width = Math.abs(gbcp.xRightWindow - gbcp.xLeftWindow);
      float height = Math.abs(gbcp.yTopWindow - gbcp.yBottomWindow);
      if (width <= 0) width = 100;
      if (height <= 0) height = 100;

      PdfPattern.Tiling tiling = new PdfPattern.Tiling(new com.itextpdf.kernel.geom.Rectangle(width, height));
      String key = "CUSTOM_" + gbcp.patternSet + "_" + gbcp.patternSymbol;
      patternCache.put(key, tiling);

      if (currentCanvas != null) {
        canvasStack.push(currentCanvas);
      }
      this.currentCanvas = new PdfCanvas((PdfStream) tiling.getPdfObject(), tiling.getResources(), pdfDoc);
      // We don't apply the global flip here because custom patterns are often defined in their own space
    } else if (order instanceof GAD_DrawingOrder.GECP_EndCustomPattern) {
      if (!canvasStack.isEmpty()) {
        this.currentCanvas = canvasStack.pop();
      }
    } else if (order instanceof GEAR_EndArea) {
      if (currentCanvas != null && graphicsState.isInArea()) {
        currentCanvas.closePath();
        short pattern = graphicsState.getPatternSymbol();
        short patternSet = graphicsState.getPatternSet();
        // X'0F' and X'40' are No Fill. X'00' is default (usually solid X'10').
        boolean noFill = (pattern == 0x0F || pattern == 0x40);

        if (!noFill) {
          applyPattern(patternSet, pattern);
        }

        if (graphicsState.isDrawAreaBoundary()) {
          if (noFill) {
            currentCanvas.stroke();
          } else if (graphicsState.isEvenOddRule()) {
            currentCanvas.eoFillStroke();
          } else {
            currentCanvas.fillStroke();
          }
        } else if (!noFill) {
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
        float rx = gbox.getxAxisLengthForRoundCorner() != null ? gbox.getxAxisLengthForRoundCorner() : 0;
        float ry = gbox.getyAxisLengthForRoundCorner() != null ? gbox.getyAxisLengthForRoundCorner() : rx;
        if (rx > 0 || ry > 0) {
          currentCanvas.roundRectangle(x, y, width, height, Math.max(rx, ry));
        } else {
          currentCanvas.rectangle(x, y, width, height);
        }
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
        float rx = gcbox.getxAxisLengthForRoundCorner() != null ? gcbox.getxAxisLengthForRoundCorner() : 0;
        float ry = gcbox.getyAxisLengthForRoundCorner() != null ? gcbox.getyAxisLengthForRoundCorner() : rx;
        if (rx > 0 || ry > 0) {
          currentCanvas.roundRectangle(x, y, width, height, Math.max(rx, ry));
        } else {
          currentCanvas.rectangle(x, y, width, height);
        }
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
    } else if (order instanceof GCCBEZ_CubicBezierCurveAtCurrentPosition gccbez) {
      if (currentCanvas != null && gccbez.getPoints() != null) {
        applyGraphicsState();
        currentCanvas.moveTo(graphicsState.getCurrentX(), graphicsState.getCurrentY());
        List<GAD_DrawingOrder.GOCA_Point> points = gccbez.getPoints();
        for (int i = 0; i < points.size() - 2; i += 3) {
          GAD_DrawingOrder.GOCA_Point p1 = points.get(i);
          GAD_DrawingOrder.GOCA_Point p2 = points.get(i + 1);
          GAD_DrawingOrder.GOCA_Point p3 = points.get(i + 2);
          currentCanvas.curveTo(p1.xCoordinate(), p1.yCoordinate(), p2.xCoordinate(), p2.yCoordinate(), p3.xCoordinate(), p3.yCoordinate());
          graphicsState.setCurrentX(p3.xCoordinate());
          graphicsState.setCurrentY(p3.yCoordinate());
        }
        if (!graphicsState.isInArea() && graphicsState.getLineType() != 8) {
          currentCanvas.stroke();
        }
      }
    } else if (order instanceof GCBEZ_CubicBezierCurveAtGivenPosition gcbez) {
      if (currentCanvas != null && gcbez.getPoints() != null && gcbez.getPoints().size() >= 4) {
        applyGraphicsState();
        List<GAD_DrawingOrder.GOCA_Point> points = gcbez.getPoints();
        GAD_DrawingOrder.GOCA_Point start = points.get(0);
        currentCanvas.moveTo(start.xCoordinate(), start.yCoordinate());
        for (int i = 1; i < points.size() - 2; i += 3) {
          GAD_DrawingOrder.GOCA_Point p1 = points.get(i);
          GAD_DrawingOrder.GOCA_Point p2 = points.get(i + 1);
          GAD_DrawingOrder.GOCA_Point p3 = points.get(i + 2);
          currentCanvas.curveTo(p1.xCoordinate(), p1.yCoordinate(), p2.xCoordinate(), p2.yCoordinate(), p3.xCoordinate(), p3.yCoordinate());
          graphicsState.setCurrentX(p3.xCoordinate());
          graphicsState.setCurrentY(p3.yCoordinate());
        }
        if (!graphicsState.isInArea() && graphicsState.getLineType() != 8) {
          currentCanvas.stroke();
        }
      }
    } else if (order instanceof GCHST_CharacterStringAtGivenPosition gchst) {
      if (gchst.getOriginPoint() != null) {
        renderGocaText(gchst.getText(), gchst.getOriginPoint().xCoordinate(), gchst.getOriginPoint().yCoordinate());
      }
    } else if (order instanceof GCCHST_CharacterStringAtCurrentPosition gcchst) {
      renderGocaText(gcchst.getText(), graphicsState.getCurrentX(), graphicsState.getCurrentY());
    } else if (order instanceof GAD_DrawingOrder.DrawingOrder_HasPoints marker && (marker instanceof GMRK_MarkerAtGivenPosition || marker instanceof GCMRK_MarkerAtCurrentPosition)) {
      renderMarkers(marker.getPoints(), marker instanceof GCMRK_MarkerAtCurrentPosition);
    } else if (order instanceof GBSEG_BeginSegment gbseg) {
      String name = gbseg.getNameOfSegment();
      if (!resourceCache.containsKey(name)) {
        startResourceCapture(name);
        if (gbseg.getDrawingOrders() != null) {
          for (GAD_DrawingOrder subOrder : gbseg.getDrawingOrders()) {
            handleDrawingOrder(subOrder);
          }
        }
        endResourceCapture();
      }
      renderXObject(name, graphicsState.getCurrentX(), graphicsState.getCurrentY(), null);
    } else if (order instanceof GESEG_EndSegment || order instanceof GNOP1_NopOperation || order instanceof GCOMT_Comment) {
      // No-op
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

  private void renderMarkers(List<GAD_DrawingOrder.GOCA_Point> points, boolean atCurrentPosition) {
    if (currentCanvas == null || (points == null && !atCurrentPosition)) {
      return;
    }
    List<GAD_DrawingOrder.GOCA_Point> markerPoints = points;
    if (atCurrentPosition) {
      markerPoints = new ArrayList<>();
      markerPoints.add(new GAD_DrawingOrder.GOCA_Point((short) graphicsState.getCurrentX(), (short) graphicsState.getCurrentY()));
      if (points != null) {
        markerPoints.addAll(points);
      }
    }

    applyGraphicsState();
    short symbol = graphicsState.getMarkerSymbol();
    float size = 100.0f; // Standard marker size in AFP units
    float half = size / 2.0f;

    for (GAD_DrawingOrder.GOCA_Point p : markerPoints) {
      if (p == null) continue;
      float px = p.xCoordinate();
      float py = p.yCoordinate();

      // Symbol Set X'00' (Standard GOCA markers)
      switch (symbol) {
        case 1 -> { // Cross (X)
          currentCanvas.moveTo(px - half, py - half).lineTo(px + half, py + half);
          currentCanvas.moveTo(px + half, py - half).lineTo(px - half, py + half);
          currentCanvas.stroke();
        }
        case 2 -> { // Plus (+)
          currentCanvas.moveTo(px - half, py).lineTo(px + half, py);
          currentCanvas.moveTo(px, py - half).lineTo(px, py + half);
          currentCanvas.stroke();
        }
        case 3 -> { // Diamond
          currentCanvas.moveTo(px, py - half).lineTo(px + half, py).lineTo(px, py + half).lineTo(px - half, py).closePath();
          currentCanvas.stroke();
        }
        case 4 -> { // Square
          currentCanvas.rectangle(px - half, py - half, size, size);
          currentCanvas.stroke();
        }
        case 5 -> { // Six-point star
          float r1 = half;
          float r2 = half * 0.4f;
          drawStar(currentCanvas, px, py, 6, r1, r2);
          currentCanvas.stroke();
        }
        case 6 -> { // Eight-point star
          float r1 = half;
          float r2 = half * 0.4f;
          drawStar(currentCanvas, px, py, 8, r1, r2);
          currentCanvas.stroke();
        }
        case 7 -> { // Solid diamond
          currentCanvas.moveTo(px, py - half).lineTo(px + half, py).lineTo(px, py + half).lineTo(px - half, py).closePath();
          currentCanvas.fill();
        }
        case 8 -> { // Solid square
          currentCanvas.rectangle(px - half, py - half, size, size);
          currentCanvas.fill();
        }
        case 9 -> { // Dot
          currentCanvas.circle(px, py, half / 2.0f);
          currentCanvas.fill();
        }
        case 10 -> { // Circle
          currentCanvas.circle(px, py, half);
          currentCanvas.stroke();
        }
        case 11 -> { // Triangle
          currentCanvas.moveTo(px, py - half).lineTo(px + half, py + half).lineTo(px - half, py + half).closePath();
          currentCanvas.stroke();
        }
        case 12 -> { // Solid triangle
          currentCanvas.moveTo(px, py - half).lineTo(px + half, py + half).lineTo(px - half, py + half).closePath();
          currentCanvas.fill();
        }
        default -> {
          // Fallback to Dot for other symbols for now
          currentCanvas.circle(px, py, 20);
          currentCanvas.fill();
        }
      }
      graphicsState.setCurrentX(p.xCoordinate());
      graphicsState.setCurrentY(p.yCoordinate());
    }
  }

  private void drawStar(PdfCanvas canvas, float x, float y, int points, float r1, float r2) {
    double angle = Math.PI / points;
    for (int i = 0; i < 2 * points; i++) {
      float r = (i % 2 == 0) ? r1 : r2;
      float px = (float) (x + r * Math.sin(i * angle));
      float py = (float) (y + r * Math.cos(i * angle));
      if (i == 0) {
        canvas.moveTo(px, py);
      } else {
        canvas.lineTo(px, py);
      }
    }
    canvas.closePath();
  }

  private void renderGocaText(String text, int x, int y) {
    if (text == null || text.isEmpty() || currentCanvas == null) {
      return;
    }
    applyGraphicsState();
    FontResource resource = resolveFontResource(graphicsState.getCharacterSet());
    PdfFont font = fontRegistry.getFontWithFallback(resource != null ? resource.name() : null);

    float fontSize = graphicsState.getCharCellHeight() > 0 ? graphicsState.getCharCellHeight() : (resource != null ? resource.size() / defaultScaleY : 100.0f);

    // Determine rotation from character angle point
    double angle = Math.atan2(graphicsState.getCharAngleY(), graphicsState.getCharAngleX());
    float cos = (float) Math.cos(angle);
    float sin = (float) Math.sin(angle);

    // Determine shear
    float shear = 0.0f;
    if (graphicsState.getCharShearDivisor() != 0) {
      shear = (float) graphicsState.getCharShearDividend() / graphicsState.getCharShearDivisor();
    }

    // Apply character direction (simplified)
    float directionScale = 1.0f;
    if (graphicsState.getCharDirection() == 3) { // Right to Left
      directionScale = -1.0f;
    }

    currentCanvas.beginText()
        .setFontAndSize(font, fontSize)
        // Matrix: [a b c d e f]
        // Incorporates rotation, Y-flip, and shear.
        .setTextMatrix(directionScale * (cos + shear * sin), directionScale * (sin - shear * cos), sin, -cos, x, y)
        .showText(text)
        .endText();

    // Update current position based on text width (approximation)
    float textWidth = font.getWidth(text, fontSize);
    if (graphicsState.getCharDirection() == 2) { // Top to Bottom
      graphicsState.setCurrentX(x);
      graphicsState.setCurrentY(y - Math.round(textWidth));
    } else if (graphicsState.getCharDirection() == 4) { // Bottom to Top
      graphicsState.setCurrentX(x);
      graphicsState.setCurrentY(y + Math.round(textWidth));
    } else {
      graphicsState.setCurrentX(x + Math.round(directionScale * textWidth * cos));
      graphicsState.setCurrentY(y + Math.round(directionScale * textWidth * sin));
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

  private void applyPattern(short set, short symbol) {
    if (set != 0) {
      String customKey = "CUSTOM_" + set + "_" + symbol;
      PdfPattern.Tiling tiling = patternCache.get(customKey);
      if (tiling != null) {
        currentCanvas.setFillColor(new PatternColor(tiling));
        return;
      }
    }

    if (symbol <= 0 || symbol >= 16 || symbol == 0x0F) {
      // Use current solid color
      Color color = ColorHandler.getColor(graphicsState.getColor());
      currentCanvas.setFillColor(color);
      return;
    }

    Color color = ColorHandler.getColor(graphicsState.getColor());
    String key = symbol + "_" + graphicsState.getColor().name();
    PdfPattern.Tiling tiling = patternCache.get(key);

    if (tiling == null) {
      float step = 100.0f; // ~7 points in 1440 units
      tiling = new PdfPattern.Tiling(new com.itextpdf.kernel.geom.Rectangle(step, step));
      PdfCanvas pCanvas = new PdfCanvas((PdfStream) tiling.getPdfObject(), tiling.getResources(), pdfDoc);
      pCanvas.setStrokeColor(color);
      pCanvas.setFillColor(color);
      pCanvas.setLineWidth(10.0f); // ~0.7 points

      if (symbol >= 1 && symbol <= 8) {
        // Dotted patterns (decreasing density)
        float dotSize = (9 - symbol) * 10.0f;
        pCanvas.circle(step / 2, step / 2, dotSize / 2).fill();
      } else {
        switch (symbol) {
          case 9 -> // Vertical lines
              pCanvas.moveTo(step / 2, 0).lineTo(step / 2, step).stroke();
          case 10 -> // Horizontal lines
              pCanvas.moveTo(0, step / 2).lineTo(step, step / 2).stroke();
          case 11, 12 -> // Diagonal lines 1 (bottom-left to top-right)
              pCanvas.moveTo(0, 0).lineTo(step, step).stroke();
          case 13, 14 -> // Diagonal lines 1 (top-left to bottom-right)
              pCanvas.moveTo(0, step).lineTo(step, 0).stroke();
        }
      }
      patternCache.put(key, tiling);
    }
    currentCanvas.setFillColor(new PatternColor(tiling));
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
      textState.setHasEstablishedBaseline(false);
    } else if (cs instanceof RMB_RelativeMoveBaseline rmb) {
      textState.setBaselinePos(textState.getBaselinePos() + rmb.getIncrement());
      textState.setHasEstablishedBaseline(false);
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
      textState.setHasEstablishedBaseline(false);
    } else if (cs instanceof TBM_TemporaryBaselineMove tbm) {
      if (tbm.getDirection() == TBM_TemporaryBaselineMove.TBM_Direction.MoveAwayFromIAxis
          || tbm.getDirection() == TBM_TemporaryBaselineMove.TBM_Direction.MoveTowardIAxis) {
        if (!textState.isHasEstablishedBaseline()) {
          textState.setEstablishedBaselinePos(textState.getBaselinePos());
          textState.setHasEstablishedBaseline(true);
        }
        int increment = (tbm.getTemporaryBaselineIncrement() != null)
            ? tbm.getTemporaryBaselineIncrement() : textState.getBaselineIncrement();
        if (tbm.getDirection() == TBM_TemporaryBaselineMove.TBM_Direction.MoveAwayFromIAxis) {
          textState.setBaselinePos(textState.getBaselinePos() + increment);
        } else {
          textState.setBaselinePos(textState.getBaselinePos() - increment);
        }
      } else if (tbm.getDirection() == TBM_TemporaryBaselineMove.TBM_Direction.ReturnToEstablishedBaseline) {
        if (textState.isHasEstablishedBaseline()) {
          textState.setBaselinePos(textState.getEstablishedBaselinePos());
          textState.setHasEstablishedBaseline(false);
        }
      }
    } else if (cs instanceof DIR_DrawIaxisRule dir) {
      renderRule(dir.getLength(), dir.getWidth() != null ? dir.getWidth() : 20, true);
    } else if (cs instanceof DBR_DrawBaxisRule dbr) {
      renderRule(dbr.getLength(), dbr.getWidth() != null ? dbr.getWidth() : 20, false);
    } else if (cs instanceof BSU_BeginSuppression bsu) {
      textState.beginSuppression(bsu.getSuppressionID());
    } else if (cs instanceof ESU_EndSuppression esu) {
      textState.endSuppression(esu.getSuppressionID());
    } else if (cs instanceof RPS_RepeatString rps) {
      handleRepeatString(rps);
    } else if (cs instanceof OVS_Overstrike ovs) {
      textState.setOverstrikeMode(ovs.getBypassFlag());
      textState.setOverstrikeCharacter(ovs.getText());
    } else if (cs instanceof USC_Underscore usc) {
      textState.setUnderscoreMode(usc.getBypassFlag());
    } else if (cs instanceof TRN_TransparentData trn) {
      renderText(trn.getTransparentData());
    } else if (cs instanceof GraphicCharacters gc) {
      renderText(gc.getText());
    } else if (cs instanceof UCT_UnicodeComplexText uct) {
      renderText(uct.getText());
    } else if (cs instanceof NOP_NoOperation) {
      // No-op
    }
  }

  private void handleRepeatString(RPS_RepeatString rps) {
    if (rps.getRepeatData() != null) {
      int totalLen = rps.getRepeatLength() & 0xFFFF;
      if (totalLen > 0 && rps.getRepeatData().length > 0) {
        byte[] fullData = new byte[totalLen];
        for (int i = 0; i < totalLen; i++) {
          fullData[i] = rps.getRepeatData()[i % rps.getRepeatData().length];
        }
        // In RPS, the text already decoded into rps.getText() matches fullData
        // if rps.decodeAFP was called.
        if (rps.getText() != null) {
          renderText(rps.getText());
        }
      }
    } else if (rps.getText() != null) {
      renderText(rps.getText());
    }
  }

  private void renderText(String text) {
    if (text == null || text.isEmpty() || currentCanvas == null) {
      return;
    }

    if (textState.isSuppressed()) {
      boolean actuallySuppressed = false;
      for (Short id : textState.getActiveSuppressionIDs()) {
        if (enabledSuppressionIDs.contains(id)) {
          actuallySuppressed = true;
          break;
        }
      }
      if (actuallySuppressed) {
        return;
      }
    }

    try {
      FontResource resource = resolveFontResource(textState.getFontLid());
      PdfFont font = fontRegistry.getFontWithFallback(resource != null ? resource.name() : null);
      float fontSizePoints = resource != null ? resource.size() : 10.0f;
      float fontSizeAfp = fontSizePoints / defaultScaleY;

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
          .setFontAndSize(font, fontSizeAfp);

      if (textState.getIntercharacterAdjustment() != 0) {
        currentCanvas.setCharacterSpacing((float) textState.getIntercharacterAdjustment());
      }

      int spaceCount = 0;
      if (textState.getVariableSpaceIncrement() != 0) {
        for (int i = 0; i < text.length(); i++) {
          if (text.charAt(i) == ' ') {
            spaceCount++;
          }
        }
        float fontSpaceWidthAfp = font.getWidth(" ", fontSizeAfp);
        currentCanvas.setWordSpacing((float) textState.getVariableSpaceIncrement() - fontSpaceWidthAfp - textState.getIntercharacterAdjustment());
      }

      currentCanvas.setTextMatrix(cos, sin, sin, -cos, afpX, afpY)
          .showText(text)
          .endText();

      // Reset spacings for next text objects
      if (textState.getIntercharacterAdjustment() != 0) {
        currentCanvas.setCharacterSpacing(0);
      }
      if (textState.getVariableSpaceIncrement() != 0) {
        currentCanvas.setWordSpacing(0);
      }

      // Update inline position based on text width (approximation in AFP units)
      float totalWidthAfp = font.getWidth(text, fontSizeAfp)
          + (textState.getIntercharacterAdjustment() * text.length());

      if (textState.getVariableSpaceIncrement() != 0) {
        totalWidthAfp += spaceCount * (textState.getVariableSpaceIncrement() - font.getWidth(" ", fontSizeAfp) - textState.getIntercharacterAdjustment());
      }

      if (textState.getUnderscoreMode() != null) {
        int ruleThickness = Math.max(1, Math.round(fontSizeAfp / 20.0f));
        renderRule((int) totalWidthAfp, ruleThickness, true);
      }

      if (textState.getOverstrikeMode() != null && textState.getOverstrikeCharacter() != null) {
        String ovsChar = textState.getOverstrikeCharacter();
        float ovsWidth = font.getWidth(ovsChar, fontSizeAfp);
        if (ovsWidth > 0) {
          int count = (int) (totalWidthAfp / ovsWidth);
          if (count > 0) {
            String ovsText = ovsChar.repeat(count);
            currentCanvas.beginText()
                .setFontAndSize(font, fontSizeAfp)
                .setTextMatrix(cos, sin, sin, -cos, afpX, afpY)
                .showText(ovsText)
                .endText();
          }
        }
      }

      if (defaultPageWidth > 0) {
        textState.setInlinePos(textState.getInlinePos() + (int) totalWidthAfp);
      }
    } catch (Exception e) {
      System.err.println("Error rendering text: " + e.getMessage());
    }
  }

  private void renderRule(int length, int width, boolean isIaxis) {
    if (currentCanvas == null) {
      return;
    }

    int afpX = CoordinateTransformer.getAfpX(textState.getInlinePos(), textState.getBaselinePos(),
        textState.getIOrientation(), textState.getBOrientation());
    int afpY = CoordinateTransformer.getAfpY(textState.getInlinePos(), textState.getBaselinePos(),
        textState.getIOrientation(), textState.getBOrientation());

    double iRad = Math.toRadians(textState.getIOrientation().getCode() / 128.0);
    double bRad = Math.toRadians(textState.getBOrientation().getCode() / 128.0);

    float cosI = (float) Math.cos(iRad);
    float sinI = (float) Math.sin(iRad);
    float cosB = (float) Math.cos(bRad);
    float sinB = (float) Math.sin(bRad);

    currentCanvas.saveState();
    // Orientation matrix: [cosI sinI; cosB sinB] at (afpX, afpY)
    currentCanvas.concatMatrix(cosI, sinI, cosB, sinB, afpX, afpY);

    if (isIaxis) {
      currentCanvas.rectangle(0, 0, length, width).fill();
    } else {
      currentCanvas.rectangle(0, 0, width, length).fill();
    }
    currentCanvas.restoreState();
  }

  private void startResourceCapture(String name) {
    // Default size for resources if not yet known, will be adjusted by PGD if present inside
    float width = defaultPageWidth > 0 ? defaultPageWidth : 1000;
    float height = defaultPageHeight > 0 ? defaultPageHeight : 1000;

    PdfFormXObject xObject = new PdfFormXObject(new com.itextpdf.kernel.geom.Rectangle(width, height));
    resourceCache.put(name, xObject);

    if (currentCanvas != null) {
      canvasStack.push(currentCanvas);
      canvasTransformedStack.push(isCanvasTransformed);
    }
    this.currentCanvas = new PdfCanvas(xObject, pdfDoc);
    this.isCanvasTransformed = false;

    // Apply transformation to the XObject canvas
    applyTransformation(height, defaultScaleX, defaultScaleY);
  }

  private void endResourceCapture() {
    if (!canvasStack.isEmpty()) {
      this.currentCanvas = canvasStack.pop();
      this.isCanvasTransformed = canvasTransformedStack.pop();
    } else {
      this.currentCanvas = null;
      this.isCanvasTransformed = false;
    }
  }

  private void applyTransformation(float heightPoints, float scaleX, float scaleY) {
    if (currentCanvas != null && !isCanvasTransformed) {
      AffineTransform at = CoordinateTransformer.getAfpToPdfTransform(heightPoints, scaleX, scaleY);
      currentCanvas.concatMatrix(at);
      this.isCanvasTransformed = true;
    }
  }

  private void ensurePageExists() {
    if (pdfDoc.getNumberOfPages() == 0) {
      this.currentPage = pdfDoc.addNewPage();
      this.currentCanvas = new PdfCanvas(currentPage);
      textState.reset();
      graphicsState.reset();
      barcodeState.reset();
      imageState.reset();
      currentCanvas.setFillColor(DeviceRgb.BLACK);
      this.isCanvasTransformed = false;

      // Apply default page size and transformation if defined (from PGD)
      if (defaultPageWidth > 0 && defaultPageHeight > 0) {
        currentPage.setMediaBox(new com.itextpdf.kernel.geom.Rectangle(defaultPageWidth, defaultPageHeight));
        applyTransformation(defaultPageHeight, defaultScaleX, defaultScaleY);
      }
    }
  }

  /**
   * Returns the number of pages in the PDF document.
   *
   * @return the page count
   */
  public int getPageCount() {
    return pdfDoc.getNumberOfPages();
  }

  @Override
  public void close() throws Exception {
    if (fieldCount.get() > 0 && pdfDoc.getNumberOfPages() == 0) {
      document.add(new Paragraph("AFP to PDF conversion in progress..."));
    }
    document.close();
  }

  /**
   * Resolves the font for the given Local ID (LID).
   *
   * @param lid the local ID
   * @return the resolved {@link PdfFont}
   */
  private PdfFont resolveFont(short lid) {
    FontResource resource = resolveFontResource(lid);
    return fontRegistry.getFontWithFallback(resource != null ? resource.name() : null);
  }

  /**
   * Resolves the font resource for the given Local ID (LID).
   *
   * @param lid the local ID
   * @return the resolved {@link FontResource}, or null if not found
   */
  private FontResource resolveFontResource(short lid) {
    for (Map<Short, FontResource> map : fontMapStack) {
      FontResource resource = map.get(lid);
      if (resource != null) {
        return resource;
      }
    }
    return null;
  }

  /**
   * Returns an unmodifiable map of the Coded Font Local IDs to font resource names.
   * This is a merged view of all active scopes.
   *
   * @return the font map
   */
  public Map<Short, FontResource> getFontMap() {
    Map<Short, FontResource> merged = new HashMap<>();
    List<Map<Short, FontResource>> list = new java.util.ArrayList<>(fontMapStack);
    Collections.reverse(list);
    for (Map<Short, FontResource> map : list) {
      merged.putAll(map);
    }
    return Collections.unmodifiableMap(merged);
  }

  /**
   * Returns the font registry.
   *
   * @return the font registry
   */
  public PdfFontRegistry getFontRegistry() {
    return fontRegistry;
  }

  /**
   * Sets the fallback font.
   *
   * @param fallbackFont the fallback font
   */
  public void setFallbackFont(PdfFont fallbackFont) {
    this.fontRegistry.setDefaultFont(fallbackFont);
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

  /**
   * Returns the current IOCA image state.
   *
   * @return the image state
   */
  public PdfImageState getImageState() {
    return imageState;
  }

  /**
   * Returns the current vertical scale factor (points per AFP unit).
   *
   * @return the scale factor
   */
  public float getDefaultScaleY() {
    return defaultScaleY;
  }

  /**
   * Returns the image cache.
   *
   * @return the image cache
   */
  public Map<String, com.itextpdf.kernel.pdf.xobject.PdfImageXObject> getImageCache() {
    return imageCache;
  }

  /**
   * Sets the output intent for the PDF document (e.g., for PDF/X or PDF/VT compliance).
   *
   * @param outputConditionIdentifier a string identifying the intended output condition
   * @param outputCondition           a string identifying the intended output condition (optional)
   * @param registryName              a string identifying the registry (optional)
   * @param info                      a human-readable string containing additional information (optional)
   * @param colorProfile              an InputStream to the ICC color profile
   * @throws java.io.IOException if an error occurs while reading the color profile
   */
  public void setOutputIntent(String outputConditionIdentifier, String outputCondition, String registryName, String info, java.io.InputStream colorProfile) throws java.io.IOException {
    PdfOutputIntent intent = new PdfOutputIntent(outputConditionIdentifier, outputCondition, registryName, info, colorProfile);
    pdfDoc.addOutputIntent(intent);
  }

  /**
   * Enables one or more suppression IDs. Text marked with these IDs via BSU/ESU will be suppressed.
   *
   * @param ids the suppression IDs to enable
   */
  public void enableSuppressionIDs(short... ids) {
    if (ids != null) {
      for (short id : ids) {
        enabledSuppressionIDs.add(id);
      }
    }
  }
}
