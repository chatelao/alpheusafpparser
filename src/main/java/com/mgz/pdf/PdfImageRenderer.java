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
import com.itextpdf.io.image.RawImageHelper;
import com.mgz.afp.ioca.IDD_ImageDataDescriptor;
import com.mgz.afp.ioca.IDD_SelfDefiningField;
import com.mgz.afp.ioca.IPD_ImagePictureData;
import com.mgz.afp.ioca.IPD_Segment;
import com.mgz.afp.ioca.IPD_Segment.ImageData;
import com.mgz.afp.ioca.IPD_Segment.ImageEncoding;
import com.mgz.afp.ioca.IPD_Segment.IPD_CompressionAlgorithm;
import java.io.ByteArrayOutputStream;
import java.security.MessageDigest;
import java.util.HexFormat;
import java.util.List;
import java.util.Map;

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
   * @param imageCache the image cache for optimization
   */
  public static void render(PdfImageState state, PdfCanvas canvas, Map<String, PdfImageXObject> imageCache) {
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

      String cacheKey = generateCacheKey(data, compression, width, height);
      PdfImageXObject imageXObject = (imageCache != null) ? imageCache.get(cacheKey) : null;

      if (imageXObject == null) {
        com.itextpdf.io.image.ImageData itextImageData = null;

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

          itextImageData = ImageDataFactory.create(
              width, height, components, bitsPerComponent, data, null);
        } else if (compression == IPD_CompressionAlgorithm.G3_ModifiedHuffman
            || compression == IPD_CompressionAlgorithm.G3_ModifiedREAD
            || compression == IPD_CompressionAlgorithm.G4_ModifiedModifiedREAD) {

          int k = 0;
          if (compression == IPD_CompressionAlgorithm.G4_ModifiedModifiedREAD) {
            k = -1;
          } else if (compression == IPD_CompressionAlgorithm.G3_ModifiedREAD) {
            k = 1;
          }

          itextImageData = ImageDataFactory.createRawImage(data);
          RawImageHelper.updateImageAttributes((com.itextpdf.io.image.RawImageData) itextImageData,
              java.util.Map.of(
                  "Width", width,
                  "Height", height,
                  "CCITT_K", k
              ));
        }

        if (itextImageData != null) {
          imageXObject = new PdfImageXObject(itextImageData);
          if (imageCache != null) {
            imageCache.put(cacheKey, imageXObject);
          }
        }
      }

      if (imageXObject != null) {
        renderImage(imageXObject, width, height, state, canvas);
      }
    } catch (Exception e) {
      System.err.println("Error rendering IOCA image: " + e.getMessage());
    }
  }

  private static String generateCacheKey(byte[] data, IPD_CompressionAlgorithm compression, int width, int height) {
    try {
      MessageDigest digest = MessageDigest.getInstance("SHA-256");
      digest.update(data);
      digest.update((byte) compression.ordinal());
      digest.update(java.nio.ByteBuffer.allocate(8).putInt(width).putInt(height).array());
      return HexFormat.of().formatHex(digest.digest());
    } catch (Exception e) {
      return String.valueOf(java.util.Arrays.hashCode(data)) + compression + width + height;
    }
  }

  private static void renderImage(PdfImageXObject imageXObject, int width, int height, PdfImageState state, PdfCanvas canvas) {
    // Placement at (xOrigin, yOrigin) with its own size in AFP units
    canvas.saveState();
    canvas.concatMatrix(width, 0, 0, height, state.getxOrigin(), state.getyOrigin() - height);
    canvas.addXObject(imageXObject);
    canvas.restoreState();
  }
}
