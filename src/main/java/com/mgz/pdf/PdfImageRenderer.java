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
import com.mgz.util.LzwDecoder;
import com.mgz.afp.ioca.IDD_ImageDataDescriptor;
import com.mgz.afp.ioca.IDD_SelfDefiningField;
import com.mgz.afp.ioca.IPD_ImagePictureData;
import com.mgz.afp.ioca.IPD_Segment;
import com.mgz.afp.ioca.IPD_Segment.BeginTile;
import com.mgz.afp.ioca.IPD_Segment.EndTile;
import com.mgz.afp.ioca.IPD_Segment.ImageData;
import com.mgz.afp.ioca.IPD_Segment.ImageEncoding;
import com.mgz.afp.ioca.IPD_Segment.IPD_CompressionAlgorithm;
import com.mgz.afp.ioca.IPD_Segment.TilePosition;
import com.mgz.afp.ioca.IPD_Segment.TileSize;
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

    boolean isTiled = false;
    for (IPD_ImagePictureData ipd : state.getImageSegments()) {
      if (ipd.getListOfSegments() != null) {
        for (IPD_Segment segment : ipd.getListOfSegments()) {
          if (segment instanceof BeginTile) {
            isTiled = true;
            break;
          }
        }
      }
      if (isTiled) break;
    }

    if (isTiled) {
      renderTiled(state, canvas, imageCache);
    } else {
      renderSimple(state, canvas, imageCache);
    }
  }

  private static void renderSimple(PdfImageState state, PdfCanvas canvas, Map<String, PdfImageXObject> imageCache) {
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

      PdfImageXObject imageXObject = getOrCreateImageXObject(data, compression, width, height, state, imageCache);
      if (imageXObject != null) {
        renderImage(imageXObject, width, height, state.getxOrigin(), state.getyOrigin() - height, canvas);
      }
    } catch (Exception e) {
      System.err.println("Error rendering IOCA image: " + e.getMessage());
    }
  }

  private static void renderTiled(PdfImageState state, PdfCanvas canvas, Map<String, PdfImageXObject> imageCache) {
    IPD_CompressionAlgorithm defaultCompression = IPD_CompressionAlgorithm.NoCompression;
    // Find default compression from ImageEncoding segments before tiles
    for (IPD_ImagePictureData ipd : state.getImageSegments()) {
      if (ipd.getListOfSegments() != null) {
        for (IPD_Segment segment : ipd.getListOfSegments()) {
          if (segment instanceof ImageEncoding ie) {
            defaultCompression = ie.getCompressionAlgorithm();
          } else if (segment instanceof BeginTile) {
            break;
          }
        }
      }
    }

    ByteArrayOutputStream tileBaos = null;
    int tileX = 0, tileY = 0, tileW = 0, tileH = 0;
    IPD_CompressionAlgorithm tileCompression = defaultCompression;

    for (IPD_ImagePictureData ipd : state.getImageSegments()) {
      if (ipd.getListOfSegments() != null) {
        for (IPD_Segment segment : ipd.getListOfSegments()) {
          if (segment instanceof BeginTile) {
            tileBaos = new ByteArrayOutputStream();
            tileCompression = defaultCompression;
          } else if (segment instanceof TilePosition tp) {
            tileX = tp.horizontalOffset;
            tileY = tp.verticalOffset;
          } else if (segment instanceof TileSize ts) {
            tileW = ts.horizontalSizeInImagePoints;
            tileH = ts.verticalSizeInImagePoints;
          } else if (segment instanceof ImageEncoding ie) {
            tileCompression = ie.getCompressionAlgorithm();
          } else if (segment instanceof ImageData id && tileBaos != null) {
            try {
              tileBaos.write(id.getImageData());
            } catch (Exception e) { /* Ignore */ }
          } else if (segment instanceof EndTile && tileBaos != null) {
            byte[] tileData = tileBaos.toByteArray();
            if (tileData.length > 0) {
              try {
                PdfImageXObject tileXObject = getOrCreateImageXObject(tileData, tileCompression, tileW, tileH, state, imageCache);
                if (tileXObject != null) {
                  renderImage(tileXObject, tileW, tileH, state.getxOrigin() + tileX, state.getyOrigin() - tileY - tileH, canvas);
                }
              } catch (Exception e) {
                System.err.println("Error rendering IOCA tile: " + e.getMessage());
              }
            }
            tileBaos = null;
          }
        }
      }
    }
  }

  private static PdfImageXObject getOrCreateImageXObject(byte[] data, IPD_CompressionAlgorithm compression, int width, int height, PdfImageState state, Map<String, PdfImageXObject> imageCache) throws Exception {
    String cacheKey = generateCacheKey(data, compression, width, height);
    PdfImageXObject imageXObject = (imageCache != null) ? imageCache.get(cacheKey) : null;

    if (imageXObject == null) {
      com.itextpdf.io.image.ImageData itextImageData = null;

      if (compression == IPD_CompressionAlgorithm.NoCompression) {
        ImageStructure structure = resolveImageStructure(state);
        itextImageData = ImageDataFactory.create(
            width, height, structure.components(), structure.bitsPerComponent(), data, null);
      } else if (compression == IPD_CompressionAlgorithm.JPEG) {
        itextImageData = ImageDataFactory.create(data);
      } else if (compression == IPD_CompressionAlgorithm.TIFF_LZW
          || compression == IPD_CompressionAlgorithm.TIFF_LZW_with_Differencing_Predictor) {

        byte[] decoded = LzwDecoder.decode(data);
        ImageStructure structure = resolveImageStructure(state);
        if (compression == IPD_CompressionAlgorithm.TIFF_LZW_with_Differencing_Predictor) {
          applyDifferencingPredictor(decoded, width, structure);
        }

        itextImageData = ImageDataFactory.create(width, height, structure.components(), structure.bitsPerComponent(), decoded, null);
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
    return imageXObject;
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

  private static ImageStructure resolveImageStructure(PdfImageState state) {
    int components = 1;
    int bitsPerComponent = 1;

    for (IPD_ImagePictureData ipd : state.getImageSegments()) {
      if (ipd.getListOfSegments() != null) {
        for (IPD_Segment segment : ipd.getListOfSegments()) {
          if (segment instanceof IPD_Segment.IDEStructure ide) {
            if (ide.getComponentSizes() != null && !ide.getComponentSizes().isEmpty()) {
              components = ide.getComponentSizes().size();
              bitsPerComponent = ide.getComponentSizes().get(0);
              return new ImageStructure(components, bitsPerComponent);
            }
          }
        }
      }
    }

    IDD_ImageDataDescriptor descriptor = state.getDescriptor();
    if (descriptor.getSelfDefiningFields() != null) {
      for (IDD_SelfDefiningField sdf : descriptor.getSelfDefiningFields()) {
        if (sdf instanceof IDD_SelfDefiningField.IDEStructure ide) {
          if (ide.getComponentSizes() != null && ide.getComponentSizes().length > 0) {
            components = ide.getComponentSizes().length;
            bitsPerComponent = ide.getComponentSizes()[0];
          }
        }
      }
    }
    return new ImageStructure(components, bitsPerComponent);
  }

  private static void applyDifferencingPredictor(byte[] data, int width, ImageStructure structure) {
    // Horizontal differencing predictor (Predictor 2)
    // For each row, pixel(i) = pixel(i) + pixel(i-1)
    if (structure.bitsPerComponent() != 8) {
      // Predictor is mostly used with 8-bit components.
      // Other bit depths would require bit manipulation.
      return;
    }

    int bytesPerPixel = structure.components();
    int rowStride = width * bytesPerPixel;

    for (int y = 0; y < data.length / rowStride; y++) {
      int rowStart = y * rowStride;
      for (int x = 1; x < width; x++) {
        for (int c = 0; c < bytesPerPixel; c++) {
          int idx = rowStart + x * bytesPerPixel + c;
          int prevIdx = rowStart + (x - 1) * bytesPerPixel + c;
          data[idx] = (byte) ((data[idx] & 0xFF) + (data[prevIdx] & 0xFF));
        }
      }
    }
  }

  private record ImageStructure(int components, int bitsPerComponent) {}

  private static void renderImage(PdfImageXObject imageXObject, int width, int height, int x, int y, PdfCanvas canvas) {
    // Placement at (x, y) with its own size in AFP units
    canvas.saveState();
    canvas.concatMatrix(width, 0, 0, height, x, y);
    canvas.addXObject(imageXObject);
    canvas.restoreState();
  }
}
