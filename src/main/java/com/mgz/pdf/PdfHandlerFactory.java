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

import com.mgz.afp.base.handler.HandlerFactory;
import com.mgz.afp.base.handler.StructuredFieldHandler;
import java.io.OutputStream;
import java.util.Map;

/**
 * Concrete implementation of {@link HandlerFactory} for PDF output.
 * Currently returns a stub {@link PdfHandler}.
 */
public class PdfHandlerFactory implements HandlerFactory {

  private String iccProfilePath;
  private boolean drawWindow;
  private double windowLeft = 102.5;
  private double windowWidth = 95.0;
  private double windowTop = 42.0;
  private double windowHeight = 40.0;
  private boolean drawLeftOverlay;
  private double leftOverlayWidth = 2.0;
  private boolean drawSwissPostLines;

  @Override
  public void configure(Map<String, String> options) {
    // Reset state to defaults before applying new options
    this.iccProfilePath = null;
    this.drawWindow = false;
    this.windowLeft = 102.5;
    this.windowWidth = 95.0;
    this.windowTop = 42.0;
    this.windowHeight = 40.0;
    this.drawLeftOverlay = false;
    this.leftOverlayWidth = 2.0;
    this.drawSwissPostLines = false;

    if (options != null) {
      this.iccProfilePath = options.get("icc-profile");
      this.drawWindow = "true".equalsIgnoreCase(options.get("draw-window"))
          || "true".equalsIgnoreCase(options.get("window"))
          || options.containsKey("window-left")
          || options.containsKey("window-width")
          || options.containsKey("window-top")
          || options.containsKey("window-height");

      if (options.containsKey("window-left")) {
        try {
          this.windowLeft = Double.parseDouble(options.get("window-left"));
        } catch (NumberFormatException ignored) {}
      }
      if (options.containsKey("window-width")) {
        try {
          this.windowWidth = Double.parseDouble(options.get("window-width"));
        } catch (NumberFormatException ignored) {}
      }
      if (options.containsKey("window-top")) {
        try {
          this.windowTop = Double.parseDouble(options.get("window-top"));
        } catch (NumberFormatException ignored) {}
      }
      if (options.containsKey("window-height")) {
        try {
          this.windowHeight = Double.parseDouble(options.get("window-height"));
        } catch (NumberFormatException ignored) {}
      }

      this.drawLeftOverlay = "true".equalsIgnoreCase(options.get("draw-left-overlay"))
          || "true".equalsIgnoreCase(options.get("left-overlay"))
          || "true".equalsIgnoreCase(options.get("overlay"))
          || options.containsKey("left-overlay-width");

      if (options.containsKey("left-overlay-width")) {
        try {
          this.leftOverlayWidth = Double.parseDouble(options.get("left-overlay-width"));
        } catch (NumberFormatException ignored) {}
      }

      this.drawSwissPostLines = "true".equalsIgnoreCase(options.get("draw-swiss-post-lines"))
          || "true".equalsIgnoreCase(options.get("swiss-post-lines"))
          || "true".equalsIgnoreCase(options.get("swiss-post-yellow"))
          || "true".equalsIgnoreCase(options.get("swiss-post"));
    }
  }

  @Override
  public boolean isParallelSupported() {
    return false;
  }

  @Override
  public String getDefaultExtension(Map<String, String> options) {
    return ".pdf";
  }

  /**
   * Creates a new {@link PdfHandler}.
   *
   * @param os           the output stream
   * @param fragmentMode currently unsupported and ignored for PDF output
   * @return a new PdfHandler
   * @throws Exception if creation fails
   */
  @Override
  public StructuredFieldHandler createHandler(OutputStream os, boolean fragmentMode) throws Exception {
    PdfHandler handler = new PdfHandler(os, new PdfFontRegistry());
    handler.setDrawWindow(drawWindow);
    handler.setWindowLeft(windowLeft);
    handler.setWindowWidth(windowWidth);
    handler.setWindowTop(windowTop);
    handler.setWindowHeight(windowHeight);
    handler.setDrawLeftOverlay(drawLeftOverlay);
    handler.setLeftOverlayWidth(leftOverlayWidth);
    handler.setDrawSwissPostLines(drawSwissPostLines);
    if (iccProfilePath != null) {
      java.io.File iccFile = new java.io.File(iccProfilePath);
      if (iccFile.exists()) {
        try (java.io.InputStream is = new java.io.FileInputStream(iccFile)) {
          handler.setOutputIntent("Custom", "", "http://www.color.org", "Custom ICC Profile", is);
        }
      }
    }
    return handler;
  }

  @Override
  public long estimateOutputSize(long inputSize) {
    // PDF output can be larger than AFP due to fonts and metadata, but often images are smaller.
    // Use a safe factor of 2.0.
    return inputSize * 2;
  }

  @Override
  public String getFormatName() {
    return "pdf";
  }
}
