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

public class PdfBarcodePostnetTest {

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
  public void testPostnetRendering() {
    PdfBarcodeState state = new PdfBarcodeState();
    state.setBarcodeType(BarCodeType.POSTNET_PLANET);
    state.setBarcodeModifier((byte) 0x00); // POSTNET ZIP Code (5 digits)

    BDA_BarCodeData bda = new BDA_BarCodeData() {
        @Override
        public String getText() {
            return "12345";
        }
    };

    state.addBarcodeData(bda);

    PdfCanvasStub canvas = new PdfCanvasStub();
    PdfBarcodeRenderer.render(state, canvas);

    assertEquals(32, canvas.rectangles.size());

    int tallCount = 0;
    int shortCount = 0;
    for (float[] rect : canvas.rectangles) {
        if (Math.abs(rect[3] - 180.0f) < 0.1f) {
            tallCount++;
        } else if (Math.abs(rect[3] - 72.0f) < 0.1f) {
            shortCount++;
        }
    }

    assertEquals(14, tallCount);
    assertEquals(32 - 14, shortCount);
  }

  @Test
  public void testPlanetRendering() {
    PdfBarcodeState state = new PdfBarcodeState();
    state.setBarcodeType(BarCodeType.POSTNET_PLANET);
    state.setBarcodeModifier((byte) 0x04); // PLANET

    BDA_BarCodeData bda = new BDA_BarCodeData() {
        @Override
        public String getText() {
            return "123";
        }
    };

    state.addBarcodeData(bda);

    PdfCanvasStub canvas = new PdfCanvasStub();
    PdfBarcodeRenderer.render(state, canvas);

    assertEquals(22, canvas.rectangles.size());

    int tallCount = 0;
    int shortCount = 0;
    for (float[] rect : canvas.rectangles) {
        if (Math.abs(rect[3] - 180.0f) < 0.1f) {
            tallCount++;
        } else if (Math.abs(rect[3] - 72.0f) < 0.1f) {
            shortCount++;
        }
    }

    assertEquals(14, tallCount);
    assertEquals(22 - 14, shortCount);
  }
}
