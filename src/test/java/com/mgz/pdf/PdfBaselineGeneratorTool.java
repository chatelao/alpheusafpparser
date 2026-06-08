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

/**
 * Utility tool for generating "golden" baseline images from PDF files.
 */
public class PdfBaselineGeneratorTool {

  /**
   * Generates a baseline image from the first page of a PDF file.
   *
   * @param pdfFile the source PDF file
   * @param outputFile the target image file (PNG)
   * @param dpi the resolution for rasterization
   * @throws IOException if an error occurs during rasterization or saving
   */
  public static void generateBaseline(File pdfFile, File outputFile, float dpi) throws IOException {
    BufferedImage image = PdfVerificationUtils.rasterize(pdfFile, dpi);
    PdfVerificationUtils.saveImage(image, "png", outputFile);
    System.out.println("Baseline generated: " + outputFile.getAbsolutePath());
  }

  public static void main(String[] args) {
    if (args.length < 2) {
      System.out.println("Usage: PdfBaselineGeneratorTool <pdfFile> <outputImageFile> [dpi]");
      return;
    }

    File pdfFile = new File(args[0]);
    File outputFile = new File(args[1]);
    float dpi = args.length > 2 ? Float.parseFloat(args[2]) : 72.0f;

    try {
      generateBaseline(pdfFile, outputFile, dpi);
    } catch (IOException e) {
      System.err.println("Error generating baseline: " + e.getMessage());
      e.printStackTrace();
    }
  }
}
