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
import com.mgz.afp.bcoca.BDA_BarCodeData;
import java.util.HashMap;
import java.util.Map;

/**
 * Renders BCOCA barcodes to PDF using vector primitives.
 */
public class PdfBarcodeRenderer {

  private static final Map<Character, String> CODE39_MAP = new HashMap<>();

  static {
    // Code 39 pattern: 5 bars, 4 spaces. 'w' for wide, 'n' for narrow.
    // Total 9 elements, 3 of which are wide (2 bars, 1 space OR 1 bar, 2 spaces).
    // Pattern order: Bar 1, Space 1, Bar 2, Space 2, Bar 3, Space 3, Bar 4, Space 4, Bar 5
    CODE39_MAP.put('0', "nnnwwnwnn");
    CODE39_MAP.put('1', "wnnwnnnnw");
    CODE39_MAP.put('2', "nwnwnnnnw");
    CODE39_MAP.put('3', "wwnwnnnnn");
    CODE39_MAP.put('4', "nnwwnnnnw");
    CODE39_MAP.put('5', "wnwwnnnnn");
    CODE39_MAP.put('6', "nwwwnnnnn");
    CODE39_MAP.put('7', "nnnwwnnnw");
    CODE39_MAP.put('8', "wnnwwnnnn");
    CODE39_MAP.put('9', "nwnwwnnnn");
    CODE39_MAP.put('A', "wnnnnwnnw");
    CODE39_MAP.put('B', "nwnnnwnnw");
    CODE39_MAP.put('C', "wwnnnwnnn");
    CODE39_MAP.put('D', "nnwnnwnnw");
    CODE39_MAP.put('E', "wnwnnwnnn");
    CODE39_MAP.put('F', "nwnwnwnnn");
    CODE39_MAP.put('G', "nnnnwwnnw");
    CODE39_MAP.put('H', "wnnnwwnnn");
    CODE39_MAP.put('I', "nwnnwwnnn");
    CODE39_MAP.put('J', "nnwnwwnnn");
    CODE39_MAP.put('K', "wnnnnnnww");
    CODE39_MAP.put('L', "nwnnnnnww");
    CODE39_MAP.put('M', "wwnnnnnwn");
    CODE39_MAP.put('N', "nnwnnnnww");
    CODE39_MAP.put('O', "wnwnnnnwn");
    CODE39_MAP.put('P', "nwnwnnnwn");
    CODE39_MAP.put('Q', "nnnnnnwww");
    CODE39_MAP.put('R', "wnnnnnwwn");
    CODE39_MAP.put('S', "nwnnnnwwn");
    CODE39_MAP.put('T', "nnwnnnwwn");
    CODE39_MAP.put('U', "wwnnnnnnw");
    CODE39_MAP.put('V', "nwwnnnnnw");
    CODE39_MAP.put('W', "wwwnnnnnn");
    CODE39_MAP.put('X', "nwnnwnnnw");
    CODE39_MAP.put('Y', "wnnnwnnnw");
    CODE39_MAP.put('Z', "nwnnwnnwn");
    CODE39_MAP.put('-', "nwnnnnwnw");
    CODE39_MAP.put('.', "wwnnnnwnn");
    CODE39_MAP.put(' ', "nwwnnnwnn");
    CODE39_MAP.put('*', "nwnnwnwnn"); // Start/Stop
    CODE39_MAP.put('$', "nwnwnwnnn");
    CODE39_MAP.put('/', "nwnwnnnwn");
    CODE39_MAP.put('+', "nwnnnwnwn");
    CODE39_MAP.put('%', "nnnwnwnwn");
  }

  /**
   * Renders the barcode described by the given state onto the canvas.
   *
   * @param state  the barcode state
   * @param canvas the PDF canvas
   */
  public static void render(PdfBarcodeState state, PdfCanvas canvas) {
    if (state == null || canvas == null || state.getBarcodeType() == null) {
      return;
    }

    StringBuilder data = new StringBuilder();
    int xOffset = -1;
    int yOffset = -1;

    for (BDA_BarCodeData bda : state.getBarcodeData()) {
      if (bda.getText() != null) {
        data.append(bda.getText());
      }
      // Use the offset from the first BDA segment that has non-zero offsets
      if (xOffset == -1 || yOffset == -1) {
        xOffset = bda.getxOffset();
        yOffset = bda.getyOffset();
      }
    }

    String content = data.toString();
    if (content.isEmpty()) {
      return;
    }

    switch (state.getBarcodeType()) {
      case Code39_3of9Code_AIM_USS_39:
        renderCode39(content, Math.max(0, xOffset), Math.max(0, yOffset), state, canvas);
        break;
      default:
        // TODO: Implement other barcode types
        break;
    }
  }

  private static void renderCode39(String content, int x, int y, PdfBarcodeState state, PdfCanvas canvas) {
    // Basic Code 39 implementation
    // Ensure start/stop characters
    if (!content.startsWith("*")) {
      content = "*" + content;
    }
    if (!content.endsWith("*")) {
      content = content + "*";
    }

    float narrowWidth = state.getModuleWidthInMils() * 0.072f; // 1 mil = 0.072 points
    if (narrowWidth <= 0) {
      narrowWidth = 1.0f; // Default if not specified
    }
    float wideWidth = narrowWidth * (state.getWideToNarrowRatio() / 100.0f);
    if (wideWidth <= narrowWidth) {
      wideWidth = narrowWidth * 2.5f;
    }

    float scaleY = CoordinateTransformer.getScaleFactor(state.getUnitBase(), (short) state.getUnitsPerUnitBaseY());
    float height = state.getElementHeight() * scaleY;
    if (height <= 0) {
      height = 50.0f;
    }

    canvas.saveState();
    // Move to barcode start position (AFP coords, handled by CTM)
    float curX = x;
    float curY = y;

    for (int i = 0; i < content.length(); i++) {
      char c = Character.toUpperCase(content.charAt(i));
      String pattern = CODE39_MAP.get(c);
      if (pattern == null) {
        continue;
      }

      for (int j = 0; j < pattern.length(); j++) {
        boolean isBar = (j % 2 == 0);
        char type = pattern.charAt(j);
        float w = (type == 'w') ? wideWidth : narrowWidth;

        if (isBar) {
          canvas.rectangle(curX, curY - height, w, height).fill();
        }
        curX += w;
      }
      // Inter-character gap
      curX += narrowWidth;
    }
    canvas.restoreState();
  }
}
