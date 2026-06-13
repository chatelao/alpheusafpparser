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
import com.mgz.afp.bcoca.BDA_BarCodeData.ParametersDataIntelligentMailPackageBarcode;
import com.mgz.afp.bcoca.BDD_BarCodeDataDescriptor.BarCodeType;
import java.io.ByteArrayOutputStream;
import java.util.EnumSet;
import org.junit.jupiter.api.Test;

/**
 * Tests for specialized Code 128 barcodes (USPS).
 */
public class PdfBarcodeCode128SpecializedTest {

  static class PdfCanvasStub extends PdfCanvas {
    int rectangleCount = 0;
    int textCount = 0;

    public PdfCanvasStub() {
      super(new PdfDocument(new PdfWriter(new ByteArrayOutputStream())).addNewPage());
    }

    @Override
    public PdfCanvas rectangle(double x, double y, double w, double h) {
      rectangleCount++;
      return super.rectangle(x, y, w, h);
    }

    @Override
    public PdfCanvas showText(String text) {
      textCount++;
      return super.showText(text);
    }
  }

  @Test
  public void testIntelligentMailContainerBarcode() {
    PdfBarcodeState state = new PdfBarcodeState();
    state.setBarcodeType(BarCodeType
        .Code_128__GS1_128__UCC_EAN_128__AIM_USS_128__IntelligentMail__ContainerBarcode);
    state.setBarcodeModifier((byte) 0x05);
    state.setHriFont(new PdfFontRegistry().getDefaultFont());

    BDA_BarCodeData bda = new BDA_BarCodeData();
    bda.setText("99M123456789012345678");
    state.addBarcodeData(bda);

    PdfCanvasStub canvas = new PdfCanvasStub();
    PdfBarcodeRenderer.render(state, canvas);

    // Code 128 has many rectangles. Plus Identification bars (2).
    assertTrue(canvas.rectangleCount > 10, "Should have rendered Code 128 bars");
    // HRI + Banner
    assertTrue(canvas.textCount >= 2, "Should have rendered HRI and Banner");
  }

  @Test
  public void testIntelligentMailPackageBarcode() {
    PdfBarcodeState state = new PdfBarcodeState();
    state.setBarcodeType(BarCodeType
        .Code_128__GS1_128__UCC_EAN_128__AIM_USS_128__IntelligentMail__ContainerBarcode);
    state.setBarcodeModifier((byte) 0x06);
    state.setHriFont(new PdfFontRegistry().getDefaultFont());

    BDA_BarCodeData bda = new BDA_BarCodeData();
    ParametersDataIntelligentMailPackageBarcode params =
        new ParametersDataIntelligentMailPackageBarcode();
    bda.parametersData = params;
    params.bannerString = "USPS TRACKING #".getBytes(java.nio.charset.StandardCharsets.UTF_16BE);
    params.bannerLength = (byte) params.bannerString.length;
    params.intelligentMailPackageBarcodeFlags =
        EnumSet.noneOf(ParametersDataIntelligentMailPackageBarcode
            .IntelligentMailPackageBarcodeFlag.class);

    BDA_BarCodeData dataBda = new BDA_BarCodeData();
    dataBda.setText("420123459200190123456789012345");
    state.addBarcodeData(dataBda);
    state.addBarcodeData(bda);

    PdfCanvasStub canvas = new PdfCanvasStub();
    PdfBarcodeRenderer.render(state, canvas);

    assertTrue(canvas.rectangleCount > 10, "Should have rendered Code 128 bars");
    assertTrue(canvas.textCount >= 2, "Should have rendered HRI and Banner");
  }
}
