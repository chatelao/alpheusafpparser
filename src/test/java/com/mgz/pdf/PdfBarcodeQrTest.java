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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.itextpdf.kernel.pdf.canvas.PdfCanvas;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.kernel.pdf.xobject.PdfXObject;
import com.mgz.afp.bcoca.BDA_BarCodeData;
import com.mgz.afp.bcoca.BDD_BarCodeDataDescriptor.BarCodeType;
import org.junit.jupiter.api.Test;
import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

public class PdfBarcodeQrTest {

  static class PdfCanvasStub extends PdfCanvas {
    List<PdfXObject> xObjects = new ArrayList<>();
    List<double[]> matrices = new ArrayList<>();

    public PdfCanvasStub() {
      super(new PdfDocument(new PdfWriter(new ByteArrayOutputStream())).addNewPage());
    }

    @Override
    public PdfCanvas addXObject(PdfXObject xObject) {
      xObjects.add(xObject);
      return this;
    }

    @Override
    public PdfCanvas saveState() { return this; }
    @Override
    public PdfCanvas restoreState() { return this; }
    @Override
    public PdfCanvas concatMatrix(double a, double b, double c, double d, double e, double f) {
      matrices.add(new double[]{a, b, c, d, e, f});
      return this;
    }
  }

  @Test
  public void testQrCodeRendering() {
    PdfBarcodeState state = new PdfBarcodeState();
    state.setBarcodeType(BarCodeType.QRCode_2D);
    state.setBarcodeModifier((byte) 0x02); // Model 2

    BDA_BarCodeData bda = new BDA_BarCodeData() {
        @Override
        public String getText() {
            return "ALPHF-TEST-QR";
        }
    };
    state.addBarcodeData(bda);

    PdfCanvasStub canvas = new PdfCanvasStub();
    PdfBarcodeRenderer.render(state, canvas);

    // Verify that at least one XObject (the QR code) was added to the canvas
    assertEquals(1, canvas.xObjects.size());

    // Verify transformation matrix
    // Expected: [moduleSize, 0, 0, -moduleSize, startX, startY]
    // Default moduleWidthInMils is 0, which leads to moduleSize = 20.0f
    // Default xOrigin, yOrigin are 0.
    double[] matrix = canvas.matrices.get(0);
    assertEquals(20.0, matrix[0], 0.01);
    assertEquals(0.0, matrix[1], 0.01);
    assertEquals(0.0, matrix[2], 0.01);
    assertEquals(-20.0, matrix[3], 0.01);
    assertEquals(0.0, matrix[4], 0.01);
    assertEquals(0.0, matrix[5], 0.01);
  }

  @Test
  public void testGs1QrCodeRendering() {
    PdfBarcodeState state = new PdfBarcodeState();
    state.setBarcodeType(BarCodeType.QRCode_2D);
    state.setBarcodeModifier((byte) 0x12); // GS1 QR Code

    BDA_BarCodeData bda = new BDA_BarCodeData() {
        @Override
        public String getText() {
            return "ALPHF-TEST-GS1-QR";
        }
    };
    state.addBarcodeData(bda);

    PdfCanvasStub canvas = new PdfCanvasStub();
    PdfBarcodeRenderer.render(state, canvas);

    // Verify that at least one XObject (the QR code) was added to the canvas
    assertEquals(1, canvas.xObjects.size());
  }
}
