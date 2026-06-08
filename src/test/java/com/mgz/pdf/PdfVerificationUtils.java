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

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import org.apache.pdfbox.Loader;
import org.apache.pdfbox.io.RandomAccessReadBuffer;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.rendering.PDFRenderer;

/**
 * Utility class for PDF verification, supporting rasterization and image comparison.
 */
public class PdfVerificationUtils {

  /**
   * Rasterizes the first page of a PDF file to a BufferedImage.
   *
   * @param pdfFile the PDF file
   * @param dpi the resolution in dots per inch
   * @return the rasterized image
   * @throws IOException if an error occurs during reading or rendering
   */
  public static BufferedImage rasterize(File pdfFile, float dpi) throws IOException {
    try (PDDocument document = Loader.loadPDF(pdfFile)) {
      PDFRenderer renderer = new PDFRenderer(document);
      return renderer.renderImageWithDPI(0, dpi);
    }
  }

  /**
   * Rasterizes the first page of a PDF input stream to a BufferedImage.
   *
   * @param is the input stream
   * @param dpi the resolution in dots per inch
   * @return the rasterized image
   * @throws IOException if an error occurs during reading or rendering
   */
  public static BufferedImage rasterize(InputStream is, float dpi) throws IOException {
    try (RandomAccessReadBuffer ra = new RandomAccessReadBuffer(is);
        PDDocument document = Loader.loadPDF(ra)) {
      PDFRenderer renderer = new PDFRenderer(document);
      return renderer.renderImageWithDPI(0, dpi);
    }
  }

  /**
   * Compares two BufferedImages pixel by pixel.
   *
   * @param img1 the first image
   * @param img2 the second image
   * @return true if the images are identical in dimensions and pixel values
   */
  public static boolean compareImages(BufferedImage img1, BufferedImage img2) {
    if (img1 == null || img2 == null) {
      return img1 == img2;
    }
    if (img1.getWidth() != img2.getWidth() || img1.getHeight() != img2.getHeight()) {
      return false;
    }
    for (int y = 0; y < img1.getHeight(); y++) {
      for (int x = 0; x < img1.getWidth(); x++) {
        if (img1.getRGB(x, y) != img2.getRGB(x, y)) {
          return false;
        }
      }
    }
    return true;
  }

  /**
   * Compares two BufferedImages pixel by pixel with a configurable tolerance.
   *
   * @param img1 the first image
   * @param img2 the second image
   * @param tolerance the maximum allowed difference for each color component (0-255)
   * @return true if the images are identical within the given tolerance
   */
  public static boolean compareImagesWithTolerance(BufferedImage img1, BufferedImage img2, int tolerance) {
    if (img1 == null || img2 == null) {
      return img1 == img2;
    }
    if (img1.getWidth() != img2.getWidth() || img1.getHeight() != img2.getHeight()) {
      return false;
    }
    for (int y = 0; y < img1.getHeight(); y++) {
      for (int x = 0; x < img1.getWidth(); x++) {
        int rgb1 = img1.getRGB(x, y);
        int rgb2 = img2.getRGB(x, y);

        int r1 = (rgb1 >> 16) & 0xFF;
        int g1 = (rgb1 >> 8) & 0xFF;
        int b1 = rgb1 & 0xFF;

        int r2 = (rgb2 >> 16) & 0xFF;
        int g2 = (rgb2 >> 8) & 0xFF;
        int b2 = rgb2 & 0xFF;

        if (Math.abs(r1 - r2) > tolerance || Math.abs(g1 - g2) > tolerance || Math.abs(b1 - b2) > tolerance) {
          return false;
        }
      }
    }
    return true;
  }
}
