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

import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfArray;
import com.itextpdf.kernel.pdf.PdfDictionary;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfName;
import com.itextpdf.kernel.pdf.PdfOutputIntent;
import com.itextpdf.kernel.pdf.PdfPage;
import com.itextpdf.kernel.pdf.PdfString;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.kernel.pdf.canvas.PdfCanvas;
import com.itextpdf.kernel.pdf.xobject.PdfFormXObject;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import com.mgz.afp.base.IRepeatingGroup;
import com.mgz.afp.base.StructuredField;
import com.mgz.afp.base.handler.StructuredFieldHandler;
import com.mgz.afp.bcoca.BDA_BarCodeData;
import com.mgz.afp.bcoca.BDD_BarCodeDataDescriptor;
import com.mgz.afp.bcoca.BBC_BeginBarCodeObject;
import com.mgz.afp.bcoca.EBC_EndBarCodeObject;
import com.mgz.afp.goca.GAD_GraphicsData;
import com.mgz.afp.goca.GAD_DrawingOrder;
import com.mgz.afp.ioca.IDD_ImageDataDescriptor;
import com.mgz.afp.ioca.IPD_ImagePictureData;
import com.mgz.afp.ioca.BIM_BeginImageObject;
import com.mgz.afp.ioca.EIM_EndImageObject;
import com.mgz.afp.modca.BDT_BeginDocument;
import com.mgz.afp.modca.EDT_EndDocument;
import com.mgz.afp.modca.BNG_BeginNamedPageGroup;
import com.mgz.afp.modca.ENG_EndNamedPageGroup;
import com.mgz.afp.modca.BPG_BeginPage;
import com.mgz.afp.modca.EPG_EndPage;
import com.mgz.afp.modca.BMO_BeginOverlay;
import com.mgz.afp.modca.EMO_EndOverlay;
import com.mgz.afp.modca.BPS_BeginPageSegment;
import com.mgz.afp.modca.EPS_EndPageSegment;
import com.mgz.afp.modca.BRG_BeginResourceGroup;
import com.mgz.afp.modca.ERG_EndResourceGroup;
import com.mgz.afp.modca.MCF_MapCodedFont_Format1;
import com.mgz.afp.modca.MCF_MapCodedFont_Format2;
import com.mgz.afp.modca.MDR_MapDataResource;
import com.mgz.afp.modca.PGD_PageDescriptor;
import com.mgz.afp.modca.TLE_TagLogicalElement;
import com.mgz.afp.modca.OBP_ObjectAreaPosition;
import com.mgz.afp.ptoca.PTX_PresentationTextData;
import com.mgz.afp.ptoca.PTOCAControlSequence;
import com.mgz.afp.triplets.Triplet;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Deque;
import java.util.ArrayDeque;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Handles MO:DCA structured fields and converts them to PDF/VT-1 using iText 9.
 */
public class PdfHandler implements StructuredFieldHandler {

  private final PdfDocument pdfDoc;
  private final Document document;
  private final PdfFontRegistry fontRegistry;
  private final Deque<StructuredField> structureStack = new ArrayDeque<>();
  private final Deque<PdfDictionary> dpartStack = new ArrayDeque<>();
  private final PdfDictionary dpartRoot;
  private final AtomicLong fieldCount = new AtomicLong(0);

  private PdfPage currentPage;
  private PdfCanvas currentCanvas;
  private final PdfTextState textState;
  private final PdfGraphicsState graphicsState;
  private final PdfBarcodeState barcodeState;
  private final PdfImageState imageState;
  private final Map<String, PdfFormXObject> imageCache = new HashMap<>();
  private final Deque<Map<Short, FontResource>> fontMapStack = new ArrayDeque<>();

  private float scaleX = 1.0f;
  private float scaleY = 1.0f;
  private float defaultScaleY = 0.05f; // Standard 1/1440 inch units
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
    this.dpartRoot = new PdfDictionary();
    dpartRoot.put(PdfName.Type, new PdfName("DPartRoot"));
    pdfDoc.getCatalog().put(new PdfName("DPartRoot"), dpartRoot);

    PdfDictionary dtreeRoot = new PdfDictionary();
    dtreeRoot.put(PdfName.Type, new PdfName("DTree"));
    dpartRoot.put(new PdfName("DTree"), dtreeRoot);
  }

  @Override
  public void handle(StructuredField sf) throws Exception {
    fieldCount.incrementAndGet();

    if (sf.isBeginSF()) {
      structureStack.push(sf);

      if (sf instanceof BDT_BeginDocument || sf instanceof BNG_BeginNamedPageGroup || sf instanceof BPG_BeginPage) {
        if (!(sf instanceof BDT_BeginDocument)) {
          fontMapStack.push(new HashMap<>(fontMapStack.peek()));
        }

        PdfDictionary dpart = new PdfDictionary();
        dpart.put(PdfName.Type, new PdfName("DPart"));
        dpart.makeIndirect(pdfDoc);

        if (dpartStack.isEmpty()) {
          PdfArray rootDParts = dpartRoot.getAsArray(new PdfName("DParts"));
          if (rootDParts == null) {
            rootDParts = new PdfArray();
            dpartRoot.put(new PdfName("DParts"), rootDParts);
          }
          rootDParts.add(dpart);
        } else {
          PdfDictionary parent = dpartStack.peek();
          PdfArray children = parent.getAsArray(new PdfName("DParts"));
          if (children == null) {
            children = new PdfArray();
            parent.put(new PdfName("DParts"), children);
          }
          children.add(dpart);
        }
        dpartStack.push(dpart);

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
          isCanvasTransformed = false;
        }
      } else if (sf instanceof BIM_BeginImageObject) {
        imageState.startNewImage();
      } else if (sf instanceof BBC_BeginBarCodeObject) {
        barcodeState.startNewBarcode();
      } else if (sf instanceof BMO_BeginOverlay || sf instanceof BPS_BeginPageSegment) {
        startResourceCapture();
      }
    } else if (sf.isEndSF()) {
      if (!structureStack.isEmpty()) {
        StructuredField begin = structureStack.pop();

        if (begin instanceof BDT_BeginDocument
            || begin instanceof BNG_BeginNamedPageGroup
            || begin instanceof BPG_BeginPage
            || begin instanceof BMO_BeginOverlay
            || begin instanceof BPS_BeginPageSegment
            || begin instanceof BIM_BeginImageObject
            || begin instanceof BBC_BeginBarCodeObject
            || begin instanceof BRG_BeginResourceGroup) {
          if (fontMapStack.size() > 1 && !(begin instanceof BRG_BeginResourceGroup)) {
            fontMapStack.pop();
          }
        }

        if (begin instanceof BDT_BeginDocument || begin instanceof BNG_BeginNamedPageGroup || begin instanceof BPG_BeginPage) {
          if (!dpartStack.isEmpty()) {
            dpartStack.pop();
          }
          if (begin instanceof BPG_BeginPage) {
              currentPage = null;
              currentCanvas = null;
              isCanvasTransformed = false;
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
          property.put(new PdfName(key.trim()), new PdfString(value.trim()));
        }
      }
    } else if (sf instanceof PGD_PageDescriptor pgd) {
      float widthPoints = pgd.getxPageSize() * defaultScaleY * (72.0f / 72.0f);
      float heightPoints = pgd.getyPageSize() * defaultScaleY * (72.0f / 72.0f);
      scaleX = 72.0f / (pgd.getUnitBase().toLpi() * (pgd.getxUnitsPerUnitBase() / (float) pgd.getUnitBase().toLpi()));
      scaleX = 1.0f / 20.0f; // Simplified for now
      scaleY = 1.0f / 20.0f;

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
                  size = fds.fontHeight * defaultScaleY;
                }
              }
            }
            if (lid != null && name != null) {
              if (size <= 0) {
                size = PdfFontRegistry.extractSizeFromName(name);
              }
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
                        if (t instanceof Triplet.ResourceLocalIdentifier rli) {
                             lid = rli.getResourceLocalID();
                        } else if (t instanceof Triplet.FullyQualifiedName fqn) {
                             name = fqn.getNameAsString();
                        } else if (t instanceof Triplet.FontDescriptorSpecification fds) {
                             if (fds.fontHeight > 0) {
                                 size = fds.fontHeight * defaultScaleY;
                             }
                        }
                    }
                    if (lid != null && name != null) {
                        if (size <= 0) size = PdfFontRegistry.extractSizeFromName(name);
                        fontMapStack.peek().put(lid, new FontResource(name, size));
                    }
                }
            }
        }
    }
  }

  private void applyTransformation(float heightPoints, float sx, float sy) {
    if (currentCanvas != null && !isCanvasTransformed) {
      currentCanvas.concatMatrix(sx, 0, 0, -sy, 0, heightPoints);
      isCanvasTransformed = true;
    }
  }

  private void handleControlSequence(PTOCAControlSequence cs) {
     // ... PTX implementation ...
  }

  private void handleDrawingOrder(GAD_DrawingOrder order) {
     // ... GOCA implementation ...
  }

  private void startResourceCapture() {
    // ... Resource management ...
  }

  private void endResourceCapture() {
    // ... Resource management ...
  }

  public void setOutputIntent(String subtype, String outputConditionIdentifier, String registryName, String info, InputStream destStream) throws Exception {
    PdfOutputIntent intent = new PdfOutputIntent(subtype, outputConditionIdentifier, registryName, info, destStream);
    pdfDoc.addOutputIntent(intent);
  }

  private PdfFont resolveFont(short lid) {
    FontResource resource = resolveFontResource(lid);
    return fontRegistry.getFontWithFallback(resource != null ? resource.name() : null);
  }

  private FontResource resolveFontResource(short lid) {
    for (Map<Short, FontResource> map : fontMapStack) {
      if (map.containsKey(lid)) {
        return map.get(lid);
      }
    }
    return null;
  }

  public Map<Short, FontResource> getFontMap() {
    return fontMapStack.peek();
  }

  public PdfFontRegistry getFontRegistry() {
    return fontRegistry;
  }

  public void setFallbackFont(PdfFont fallbackFont) {
    this.fontRegistry.setDefaultFont(fallbackFont);
  }

  public long getFieldCount() {
    return fieldCount.get();
  }

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

  public record FontResource(String name, float size) {}
}
