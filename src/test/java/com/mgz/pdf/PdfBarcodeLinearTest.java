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

import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.kernel.pdf.canvas.PdfCanvas;
import com.mgz.afp.bcoca.BDA_BarCodeData;
import com.mgz.afp.bcoca.BDD_BarCodeDataDescriptor.BarCodeType;
import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;

/**
 * Tests for linear barcode rendering.
 */
public class PdfBarcodeLinearTest {

  /**
   * Stub for PdfCanvas to capture drawing operations.
   */
  static class PdfCanvasStub extends PdfCanvas {
    List<float[]> rectangles = new ArrayList<>();

    public PdfCanvasStub() {
      super(new PdfDocument(new PdfWriter(new ByteArrayOutputStream())).addNewPage());
    }

    @Override
    public PdfCanvas rectangle(double x, double y, double w, double h) {
      rectangles.add(new float[] {(float) x, (float) y, (float) w, (float) h});
      return this;
    }

    @Override
    public PdfCanvas saveState() {
      return this;
    }

    @Override
    public PdfCanvas restoreState() {
      return this;
    }

    @Override
    public PdfCanvas fill() {
      return this;
    }
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
    // "1": "wnnnw" -> 2 wide, 3 narrow. Industrial: wide bars, narrow spaces.
    // Total 5 bars per digit.
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
    //   Weights: 1, 2, 1 (from right: 3*2, 2*1, 1*2 -> no, weights 1, 2 from right:
    //   3*2=6, 2*1=2, 1*2=2)
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

  @Test
  public void testCode93() {
    PdfBarcodeState state = new PdfBarcodeState();
    state.setBarcodeType(BarCodeType.Code93);
    state.setBarcodeModifier((byte) 0x00);

    BDA_BarCodeData bda = new BDA_BarCodeData() {
      @Override
      public String getText() {
        return "12";
      }
    };
    state.addBarcodeData(bda);

    PdfCanvasStub canvas = new PdfCanvasStub();
    PdfBarcodeRenderer.render(state, canvas);

    // Data "12" -> values {1, 2}
    // Check C for {1, 2}: 2*1 + 1*2 = 4. values {1, 2, 4}
    // Check K for {1, 2, 4}: 4*1 + 2*2 + 1*3 = 11. values {1, 2, 4, 11}
    // Patterns:
    // Start: 47 (6 bars)
    // '1': index 1 ("101001000") -> 3 bars
    // '2': index 2 ("101000100") -> 3 bars
    // '4': index 4 ("100101000") -> 3 bars
    // '11': index 11 ("110100100") -> 4 bars
    // Stop: index 47 ("101011110") -> 6 bars
    // Termination: 1 bar
    // Total: 6 + 3 + 3 + 3 + 4 + 6 + 1 = 26 bars.
    assertEquals(26, canvas.rectangles.size(),
        "Expected 26 bars but got " + canvas.rectangles.size());
  }

  @Test
  public void testUpcSupplemental2() {
    PdfBarcodeState state = new PdfBarcodeState();
    state.setBarcodeType(BarCodeType.UPC_TwoDigit_Supplemental_Periodicals);

    BDA_BarCodeData bda = new BDA_BarCodeData() {
      @Override
      public String getText() {
        return "12";
      }
    };
    state.addBarcodeData(bda);

    PdfCanvasStub canvas = new PdfCanvasStub();
    PdfBarcodeRenderer.render(state, canvas);

    // Start: 1011 (3 bars)
    // Digit 1: 7 modules (some number of bars)
    // Separator: 01 (1 bar)
    // Digit 2: 7 modules (some number of bars)
    // Let's see: digits "1" and "2".
    // 2-digit parity for "12": val=12, val%4 = 0 -> "LL"
    // Digit "1" (L): "0001101" -> 2 bars
    // Digit "2" (L): "0011001" -> 2 bars
    // Total bars: 3 (start) + 3 (digit 1: '1' L-parity) + 1 (separator) + 3 (digit 2: '2' L-parity) = 10 bars.
    // Parity for 12: val%4 = 0 -> "LL"
    // '1' (L): "0001101" -> 3 ones (0001101 -> wait, 11 and 1. 2 segments!)
    // Let's re-count "0001101": 000 (space), 11 (bar), 0 (space), 1 (bar). Correct, 2 bars.
    // What is `renderBitPattern` doing?
    // It iterates through the string and for each '1' it calls canvas.rectangle().
    // "1011" -> '1', '0', '1', '1' -> 3 bars.
    // Digit "1" (L): "0001101" -> '1' at index 3, 4, 6. 3 bars.
    // Digit "2" (L): "0011001" -> '1' at index 2, 3, 6. 3 bars.
    // Total bars: 3 (start) + 3 (d1) + 1 (sep "01") + 3 (d2) = 10 bars.
    assertEquals(10, canvas.rectangles.size(), "Actual bars: " + canvas.rectangles.size());
  }

  @Test
  public void testUpcSupplemental5() {
    PdfBarcodeState state = new PdfBarcodeState();
    state.setBarcodeType(BarCodeType.UPC_FiveDigit_Supplemental_Paperbacks);

    BDA_BarCodeData bda = new BDA_BarCodeData() {
      @Override
      public String getText() {
        return "12345";
      }
    };
    state.addBarcodeData(bda);

    PdfCanvasStub canvas = new PdfCanvasStub();
    PdfBarcodeRenderer.render(state, canvas);

    // Start: 1011 (3 bars)
    // 5 digits, each 7 modules + 4 separators of 2 modules (01)
    // Total bars = 3 + bars(d1) + 1 + bars(d2) + 1 + bars(d3) + 1 + bars(d4) + 1 + bars(d5)
    // Parity for "12345":
    // sum = (1*3 + 2*9 + 3*3 + 4*9 + 5*3) = 3 + 18 + 9 + 36 + 15 = 81
    // sum % 10 = 1 -> "GLGLL"
    // Digit 1 (G) '1': "0110011" -> 2 bars
    // Digit 2 (L) '2': "0011001" -> 2 bars
    // Digit 3 (G) '3': "0100001" -> 2 bars
    // Digit 4 (L) '4': "0100011" -> 2 bars
    // Digit 5 (L) '5': "0110001" -> 2 bars
    // Total bars:
    // Start "1011": 3 bars
    // Digit 1 (G) '1': "0110011" -> 4 bars
    // Sep "01": 1 bar
    // Digit 2 (L) '2': "0011001" -> 3 bars
    // Sep "01": 1 bar
    // Digit 3 (G) '3': "0100001" -> 2 bars
    // Sep "01": 1 bar
    // Digit 4 (L) '4': "0100011" -> 3 bars
    // Sep "01": 1 bar
    // Digit 5 (L) '5': "0110001" -> 3 bars
    // Total: 3 + 4 + 1 + 3 + 1 + 2 + 1 + 3 + 1 + 3 = 22 bars.
    assertEquals(22, canvas.rectangles.size(), "Actual bars: " + canvas.rectangles.size());
  }
}
