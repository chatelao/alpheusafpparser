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

import com.itextpdf.kernel.pdf.canvas.PdfCanvas;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.mgz.afp.bcoca.BDA_BarCodeData;
import com.mgz.afp.bcoca.BDD_BarCodeDataDescriptor.BarCodeType;
import org.junit.jupiter.api.Test;
import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

public class PdfBarcodeExtraTest {

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
  public void testAustraliaPostProprietary() {
    PdfBarcodeState state = new PdfBarcodeState();
    state.setBarcodeType(BarCodeType.AustraliaPostBarCode);
    state.setBarcodeModifier((byte) 0x04); // Proprietary

    BDA_BarCodeData bda = new BDA_BarCodeData() {
        @Override
        public String getText() {
            return "123456780123"; // 8 digits sorting + 4 digits bar states
        }
    };
    state.addBarcodeData(bda);

    PdfCanvasStub canvas = new PdfCanvasStub();
    PdfBarcodeRenderer.render(state, canvas);

    // Start (1) + 4 states + Stop (1) = 6 bars.
    assertEquals(6, canvas.rectangles.size());
  }

  @Test
  public void testCode128SubsetC() {
    PdfBarcodeState state = new PdfBarcodeState();
    state.setBarcodeType(BarCodeType.Code_128__GS1_128__UCC_EAN_128__AIM_USS_128__IntelligentMail__ContainerBarcode);
    state.setBarcodeModifier((byte) 0x02);

    BDA_BarCodeData bda = new BDA_BarCodeData() {
        @Override
        public String getText() {
            return "1234"; // Numeric, length 4 -> Subset C
        }
    };
    state.addBarcodeData(bda);

    PdfCanvasStub canvas = new PdfCanvasStub();
    PdfBarcodeRenderer.render(state, canvas);

    // Symbols: StartC (1) + "12" (1) + "34" (1) + Check (1) + Stop (1) = 5 symbols.
    // Each pattern in CODE128_PATTERNS has 6 elements (3 bars).
    // Total bars: 5 * 3 = 15. Plus the final 2-unit bar for Stop symbol = 16.
    assertEquals(16, canvas.rectangles.size());
  }

  @Test
  public void testCode128SubsetA() {
    PdfBarcodeState state = new PdfBarcodeState();
    state.setBarcodeType(BarCodeType.Code_128__GS1_128__UCC_EAN_128__AIM_USS_128__IntelligentMail__ContainerBarcode);
    state.setBarcodeModifier((byte) 0x02);

    BDA_BarCodeData bda = new BDA_BarCodeData() {
        @Override
        public String getText() {
            return "ABC\n"; // Contains LF (control) -> Subset A
        }
    };
    state.addBarcodeData(bda);

    PdfCanvasStub canvas = new PdfCanvasStub();
    PdfBarcodeRenderer.render(state, canvas);

    // Symbols: StartA (1) + 'A' (1) + 'B' (1) + 'C' (1) + LF (1) + Check (1) + Stop (1) = 7 symbols.
    // Total bars: 7 * 3 = 21. Plus the final 2-unit bar = 22.
    assertEquals(22, canvas.rectangles.size());
  }

  @Test
  public void testRM4SCC() {
    PdfBarcodeState state = new PdfBarcodeState();
    state.setBarcodeType(BarCodeType.RM4SCC_DutchKIX);
    state.setBarcodeModifier((byte) 0x00); // RM4SCC

    BDA_BarCodeData bda = new BDA_BarCodeData() {
        @Override
        public String getText() {
            return "SN34RD"; // 6 chars
        }
    };
    state.addBarcodeData(bda);

    PdfCanvasStub canvas = new PdfCanvasStub();
    PdfBarcodeRenderer.render(state, canvas);

    // RM4SCC: Start(1) + Data(6*4=24) + Check(4) + Stop(1) = 30 bars.
    assertEquals(30, canvas.rectangles.size());
  }

  @Test
  public void testDutchKIX() {
    PdfBarcodeState state = new PdfBarcodeState();
    state.setBarcodeType(BarCodeType.RM4SCC_DutchKIX);
    state.setBarcodeModifier((byte) 0x01); // Dutch KIX

    BDA_BarCodeData bda = new BDA_BarCodeData() {
        @Override
        public String getText() {
            return "123XYZ"; // 6 chars
        }
    };
    state.addBarcodeData(bda);

    PdfCanvasStub canvas = new PdfCanvasStub();
    PdfBarcodeRenderer.render(state, canvas);

    // KIX: Data(6*4=24) only.
    assertEquals(24, canvas.rectangles.size());
  }
}
