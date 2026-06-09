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

import static org.junit.jupiter.api.Assertions.assertTrue;

import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.kernel.pdf.canvas.PdfCanvas;
import com.mgz.afp.bcoca.BDA_BarCodeData;
import com.mgz.afp.bcoca.BDD_BarCodeDataDescriptor.BarCodeType;
import org.junit.jupiter.api.Test;
import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

public class PdfBarcode2DTest {

  static class PdfCanvasStub extends PdfCanvas {
    boolean xObjectAdded = false;

    public PdfCanvasStub() {
      super(new PdfDocument(new PdfWriter(new ByteArrayOutputStream())).addNewPage());
    }

    @Override
    public PdfCanvas addXObject(com.itextpdf.kernel.pdf.xobject.PdfXObject xObject) {
      xObjectAdded = true;
      return this;
    }

    @Override
    public PdfCanvas saveState() { return this; }
    @Override
    public PdfCanvas restoreState() { return this; }
    @Override
    public PdfCanvas concatMatrix(double a, double b, double c, double d, double e, double f) { return this; }
  }

  @Test
  public void testGs1DataMatrix() {
    PdfBarcodeState state = new PdfBarcodeState();
    state.setBarcodeType(BarCodeType.DataMatrix_GS1DataMatrix_2D);
    state.setBarcodeModifier((byte) 0x02);

    BDA_BarCodeData bda = new BDA_BarCodeData() {
        @Override
        public String getText() {
            return "0101234567890128";
        }
    };
    state.addBarcodeData(bda);

    PdfCanvasStub canvas = new PdfCanvasStub();
    PdfBarcodeRenderer.render(state, canvas);

    assertTrue(canvas.xObjectAdded, "XObject should have been added for GS1 Data Matrix");
  }

  @Test
  public void testDataMatrix() {
    PdfBarcodeState state = new PdfBarcodeState();
    state.setBarcodeType(BarCodeType.DataMatrix_GS1DataMatrix_2D);

    BDA_BarCodeData bda = new BDA_BarCodeData() {
        @Override
        public String getText() {
            return "DATA MATRIX TEST";
        }
    };
    state.addBarcodeData(bda);

    PdfCanvasStub canvas = new PdfCanvasStub();
    PdfBarcodeRenderer.render(state, canvas);

    assertTrue(canvas.xObjectAdded, "XObject should have been added for Data Matrix");
  }

  @Test
  public void testPdf417() {
    PdfBarcodeState state = new PdfBarcodeState();
    state.setBarcodeType(BarCodeType.PDF417_2D);

    BDA_BarCodeData bda = new BDA_BarCodeData() {
        @Override
        public String getText() {
            return "PDF417 TEST";
        }
    };
    state.addBarcodeData(bda);

    PdfCanvasStub canvas = new PdfCanvasStub();
    PdfBarcodeRenderer.render(state, canvas);

    assertTrue(canvas.xObjectAdded, "XObject should have been added for PDF417");
  }
}
