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
import com.mgz.afp.ioca.IDD_ImageDataDescriptor;
import com.mgz.afp.ioca.IPD_ImagePictureData;
import com.mgz.afp.ioca.IPD_Segment;
import org.junit.jupiter.api.Test;
import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

public class PdfImageRendererTest {

  static class PdfCanvasStub extends PdfCanvas {
    int xObjectsAdded = 0;

    public PdfCanvasStub() {
      super(new PdfDocument(new PdfWriter(new ByteArrayOutputStream())).addNewPage());
    }

    @Override
    public PdfCanvas addXObject(com.itextpdf.kernel.pdf.xobject.PdfXObject xObject) {
      xObjectsAdded++;
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
  public void testJpegRendering() {
    PdfImageState state = new PdfImageState();
    IDD_ImageDataDescriptor descriptor = new IDD_ImageDataDescriptor();
    // Set minimal valid descriptor fields
    descriptor.setWidthOfImageInImagePoints((short)100);
    descriptor.setHeightOfImageInImagePoints((short)100);
    state.setDescriptor(descriptor);

    // Create a dummy JPEG segment
    IPD_ImagePictureData ipd = new IPD_ImagePictureData();
    List<IPD_Segment> segments = new ArrayList<>();

    // Image Encoding Segment for JPEG (0x83)
    IPD_Segment.ImageEncoding encoding = new IPD_Segment.ImageEncoding();
    encoding.setCompressionAlgorithm(IPD_Segment.IPD_CompressionAlgorithm.JPEG);
    segments.add(encoding);

    // Image Data Segment with a tiny 1x1 black JPEG (minimal valid JPEG)
    byte[] tinyJpeg = {
        (byte)0xff, (byte)0xd8, (byte)0xff, (byte)0xdb, (byte)0x00, (byte)0x43, (byte)0x00, (byte)0x08, (byte)0x06, (byte)0x06, (byte)0x07, (byte)0x06, (byte)0x05, (byte)0x08, (byte)0x07, (byte)0x07, (byte)0x07, (byte)0x09, (byte)0x09, (byte)0x08, (byte)0x0a, (byte)0x0c, (byte)0x14, (byte)0x0d, (byte)0x0c, (byte)0x0b, (byte)0x0b, (byte)0x0c, (byte)0x19, (byte)0x12, (byte)0x13, (byte)0x0f, (byte)0x14, (byte)0x1d, (byte)0x1a, (byte)0x1f, (byte)0x1e, (byte)0x1d, (byte)0x1a, (byte)0x1c, (byte)0x1c, (byte)0x20, (byte)0x24, (byte)0x2e, (byte)0x27, (byte)0x20, (byte)0x22, (byte)0x2c, (byte)0x23, (byte)0x1c, (byte)0x1c, (byte)0x28, (byte)0x37, (byte)0x29, (byte)0x2c, (byte)0x30, (byte)0x31, (byte)0x34, (byte)0x34, (byte)0x34, (byte)0x1f, (byte)0x27, (byte)0x39, (byte)0x3d, (byte)0x38, (byte)0x32, (byte)0x3c, (byte)0x2e, (byte)0x33, (byte)0x34, (byte)0x32, (byte)0xff, (byte)0xc0, (byte)0x00, (byte)0x0b, (byte)0x08, (byte)0x00, (byte)0x01, (byte)0x00, (byte)0x01, (byte)0x01, (byte)0x01, (byte)0x11, (byte)0x00, (byte)0xff, (byte)0xc4, (byte)0x00, (byte)0x14, (byte)0x00, (byte)0x01, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x09, (byte)0xff, (byte)0xda, (byte)0x00, (byte)0x08, (byte)0x01, (byte)0x01, (byte)0x00, (byte)0x00, (byte)0x3f, (byte)0x00, (byte)0xaf, (byte)0xff, (byte)0xd9
    };
    IPD_Segment.ImageData imageData = new IPD_Segment.ImageData();
    imageData.setImageData(tinyJpeg);
    segments.add(imageData);

    ipd.setListOfSegments(segments);
    state.addImageSegment(ipd);

    PdfCanvasStub canvas = new PdfCanvasStub();
    PdfImageRenderer.render(state, canvas, null);

    assertEquals(1, canvas.xObjectsAdded);
  }
}
