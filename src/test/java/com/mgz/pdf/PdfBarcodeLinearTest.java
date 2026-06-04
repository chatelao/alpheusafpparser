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
import com.mgz.afp.bcoca.BDA_BarCodeData;
import com.mgz.afp.bcoca.BDD_BarCodeDataDescriptor.BarCodeType;
import org.junit.jupiter.api.Test;
import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

public class PdfBarcodeLinearTest {

  static class PdfCanvasStub extends PdfCanvas {
    List<float[]> rectangles = new ArrayList<>();

    public PdfCanvasStub() {
      super(new PdfDocument(new PdfWriter(new ByteArrayOutputStream())).addNewPage());
    }

    @Override
    public PdfCanvas rectangle(double x, double y, double w, double h) {
      rectangles.add(new float[]{(float)x, (float)y, (float)w, (float)h});
      return this;
    }

    @Override
    public PdfCanvas saveState() { return this; }
    @Override
    public PdfCanvas restoreState() { return this; }
    @Override
    public PdfCanvas fill() { return this; }
  }

  @Test
  public void testIndustrial2of5() {
    PdfBarcodeState state = new PdfBarcodeState();
    state.setBarcodeType(BarCodeType.Industrial_2of5);
    state.setBarcodeModifier((byte) 0x01); // No check digit

    BDA_BarCodeData bda = new BDA_BarCodeData() {
        @Override
        public String getText() {
            return "12";
        }
    };
    state.addBarcodeData(bda);

    PdfCanvasStub canvas = new PdfCanvasStub();
    PdfBarcodeRenderer.render(state, canvas);

    // Start: 2 bars.
    // "1": "wnnnw" -> 2 wide, 3 narrow. Industrial: wide bars, narrow spaces. Total 5 bars per digit.
    // Digit "1": 5 bars. Digit "2": 5 bars.
    // Stop: 2 bars.
    // Total bars: 2 + 5 + 5 + 2 = 14.
    assertEquals(14, canvas.rectangles.size());
  }

  @Test
  public void testMatrix2of5() {
    PdfBarcodeState state = new PdfBarcodeState();
    state.setBarcodeType(BarCodeType.Matrix_2ofFive);
    state.setBarcodeModifier((byte) 0x01); // No check digit

    BDA_BarCodeData bda = new BDA_BarCodeData() {
        @Override
        public String getText() {
            return "3";
        }
    };
    state.addBarcodeData(bda);

    PdfCanvasStub canvas = new PdfCanvasStub();
    PdfBarcodeRenderer.render(state, canvas);

    // Start: 3 bars
    // Digit "3": "wwnnn". Matrix 2-of-5 digit has 3 bars (indices 0, 2, 4).
    // "wwnnn": index 0 (w), 2 (n), 4 (n). 3 bars.
    // Stop: 3 bars.
    // Total bars: 3 + 3 + 3 = 9.
    assertEquals(9, canvas.rectangles.size());
  }
}
