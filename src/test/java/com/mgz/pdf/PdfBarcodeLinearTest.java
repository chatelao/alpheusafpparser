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

  @Test
  public void testMsiPlessey() {
    PdfBarcodeState state = new PdfBarcodeState();
    state.setBarcodeType(BarCodeType.MSI_MmodifiedPlesseyCode);
    state.setBarcodeModifier((byte) 0x01); // No check digit

    BDA_BarCodeData bda = new BDA_BarCodeData() {
        @Override
        public String getText() {
            return "1";
        }
    };
    state.addBarcodeData(bda);

    PdfCanvasStub canvas = new PdfCanvasStub();
    PdfBarcodeRenderer.render(state, canvas);

    // Start: 110 (2 bars)
    // Digit "1": BCD 0001
    //   '0': 100 (1 bar)
    //   '0': 100 (1 bar)
    //   '0': 100 (1 bar)
    //   '1': 110 (2 bars)
    // Stop: 1001 (2 bars)
    // Total bars: 2 + (1+1+1+2) + 2 = 9 bars.
    assertEquals(9, canvas.rectangles.size());
  }

  @Test
  public void testMsiPlesseyWithCheckDigit() {
    PdfBarcodeState state = new PdfBarcodeState();
    state.setBarcodeType(BarCodeType.MSI_MmodifiedPlesseyCode);
    state.setBarcodeModifier((byte) 0x02); // IBM Modulo 10

    BDA_BarCodeData bda = new BDA_BarCodeData() {
        @Override
        public String getText() {
            return "123";
        }
    };
    state.addBarcodeData(bda);

    PdfCanvasStub canvas = new PdfCanvasStub();
    PdfBarcodeRenderer.render(state, canvas);

    // Data "123"
    // IBM Modulo 10 for "123":
    //   Digits: 1, 2, 3
    //   Weights: 1, 2, 1 (from right: 3*2, 2*1, 1*2 -> no, weights 1, 2 from right: 3*2=6, 2*1=2, 1*2=2)
    //   Let's re-calculate manually:
    //   i=0 (digit '3'): weight=2, prod=6, sum=6
    //   i=1 (digit '2'): weight=1, prod=2, sum=6+2=8
    //   i=2 (digit '1'): weight=2, prod=2, sum=8+2=10
    //   (10 - (10 % 10)) % 10 = 0.
    // So "1230".
    // Start: 2 bars
    // 4 digits * 4 bits * bars:
    //   '1' (0001): 1+1+1+2 = 5 bars
    //   '2' (0010): 1+1+2+1 = 5 bars
    //   '3' (0011): 1+1+2+2 = 6 bars
    //   '0' (0000): 1+1+1+1 = 4 bars
    // Stop: 2 bars
    // Total: 2 + 5+5+6+4 + 2 = 24 bars.
    assertEquals(24, canvas.rectangles.size());
  }

  @Test
  public void testJapanPostalBarcode() {
    PdfBarcodeState state = new PdfBarcodeState();
    state.setBarcodeType(BarCodeType.JapanPostalBarCode);
    state.setBarcodeModifier((byte) 0x00);

    BDA_BarCodeData bda = new BDA_BarCodeData() {
        @Override
        public String getText() {
            return "123-4567"; // 7 digits
        }
    };
    state.addBarcodeData(bda);

    PdfCanvasStub canvas = new PdfCanvasStub();
    PdfBarcodeRenderer.render(state, canvas);

    // Modifier X'00' with "123-4567":
    // 1. Postal code: 1234567 (7 digits)
    // 2. Address: empty -> padded with 13 CC4 characters
    // 3. Check digit calculated for [1,2,3,4,5,6,7, 14,14,14,14,14,14,14,14,14,14,14,14,14]
    // Total code points: 7 + 13 + 1 = 21.
    // Bars: Start (2) + 21 * 3 + Stop (2) = 2 + 63 + 2 = 67.
    assertEquals(67, canvas.rectangles.size());
  }
}
