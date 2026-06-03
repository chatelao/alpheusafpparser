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

import com.itextpdf.kernel.pdf.canvas.PdfCanvas;
import com.itextpdf.kernel.pdf.xobject.PdfImageXObject;
import com.itextpdf.io.image.ImageDataFactory;
import com.mgz.afp.ioca.IDD_ImageDataDescriptor;
import com.mgz.afp.ioca.IDD_SelfDefiningField;
import com.mgz.afp.ioca.IPD_ImagePictureData;
import com.mgz.afp.ioca.IPD_Segment;
import com.mgz.afp.ioca.IPD_Segment.ImageData;
import com.mgz.afp.ioca.IPD_Segment.ImageEncoding;
import com.mgz.afp.ioca.IPD_Segment.IPD_CompressionAlgorithm;
import java.io.ByteArrayOutputStream;
import java.util.List;

/**
 * Renders IOCA images to PDF.
 */
public class PdfImageRenderer {

  /**
   * Renders the image described by the given state onto the canvas.
   * Currently supports uncompressed bilevel (FS10) images.
   *
   * @param state  the image state
   * @param canvas the PDF canvas
   */
  public static void render(PdfImageState state, PdfCanvas canvas) {
    if (state == null || canvas == null || state.getDescriptor() == null) {
      return;
    }

    IDD_ImageDataDescriptor descriptor = state.getDescriptor();
    int width = descriptor.getWidthOfImageInImagePoints();
    int height = descriptor.getHeightOfImageInImagePoints();

    if (width <= 0 || height <= 0) {
      return;
    }

    // Collect all image data
    ByteArrayOutputStream baos = new ByteArrayOutputStream();
    for (IPD_ImagePictureData ipd : state.getImageSegments()) {
      if (ipd.getListOfSegments() != null) {
        for (IPD_Segment segment : ipd.getListOfSegments()) {
          if (segment instanceof ImageData id) {
            try {
              baos.write(id.getImageData());
            } catch (Exception e) {
              // Ignore
            }
          }
        }
      }
    }

    byte[] data = baos.toByteArray();
    if (data.length == 0) {
      return;
    }

    try {
      // Check encoding (default to no compression if missing)
      IPD_CompressionAlgorithm compression = IPD_CompressionAlgorithm.NoCompression;
      for (IPD_ImagePictureData ipd : state.getImageSegments()) {
        if (ipd.getListOfSegments() != null) {
          for (IPD_Segment segment : ipd.getListOfSegments()) {
            if (segment instanceof ImageEncoding ie) {
              compression = ie.getCompressionAlgorithm();
            }
          }
        }
      }

      if (compression == IPD_CompressionAlgorithm.NoCompression) {
        int components = 1;
        int bitsPerComponent = 1;

        // Try to get structure from IOCA segments first
        for (IPD_ImagePictureData ipd : state.getImageSegments()) {
          if (ipd.getListOfSegments() != null) {
            for (IPD_Segment segment : ipd.getListOfSegments()) {
              if (segment instanceof IPD_Segment.IDEStructure ide) {
                if (ide.getComponentSizes() != null && !ide.getComponentSizes().isEmpty()) {
                  components = ide.getComponentSizes().size();
                  bitsPerComponent = ide.getComponentSizes().get(0);
                }
              }
            }
          }
        }

        // Fallback to descriptor's self-defining fields if not in image segments
        if (components == 1 && bitsPerComponent == 1 && descriptor.getSelfDefiningFields() != null) {
          for (IDD_SelfDefiningField sdf : descriptor.getSelfDefiningFields()) {
            if (sdf instanceof IDD_SelfDefiningField.IDEStructure ide) {
              if (ide.getComponentSizes() != null && ide.getComponentSizes().length > 0) {
                components = ide.getComponentSizes().length;
                bitsPerComponent = ide.getComponentSizes()[0];
              }
            }
          }
        }

        com.itextpdf.io.image.ImageData itextImageData = ImageDataFactory.create(
            width, height, components, bitsPerComponent, data, null);
        PdfImageXObject imageXObject = new PdfImageXObject(itextImageData);

        // Placement at (xOrigin, yOrigin) with its own size in AFP units
        canvas.saveState();
        canvas.concatMatrix(width, 0, 0, height, state.getxOrigin(), state.getyOrigin() - height);
        canvas.addXObject(imageXObject);
        canvas.restoreState();
      }
    } catch (Exception e) {
      System.err.println("Error rendering IOCA image: " + e.getMessage());
    }
  }
}
