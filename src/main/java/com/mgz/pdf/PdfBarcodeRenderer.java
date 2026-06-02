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

import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.pdf.canvas.PdfCanvas;
import com.mgz.afp.bcoca.BDA_BarCodeData;
import com.mgz.afp.bcoca.BDA_BarCodeData.BarCodeFlag;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

/**
 * Renders BCOCA barcodes to PDF using vector primitives.
 */
public class PdfBarcodeRenderer {

  private static final Map<Character, String> CODE39_MAP = new HashMap<>();
  private static final String[] ITF_PATTERNS = {
      "nnwwn", // 0
      "wnnnw", // 1
      "nwnnw", // 2
      "wwnnn", // 3
      "nnwnw", // 4
      "wnwnn", // 5
      "nwwnn", // 6
      "nnnww", // 7
      "wnnwn", // 8
      "nwnwn"  // 9
  };

  private static final String[] UPCA_L_PATTERNS = {
      "0001101", "0011001", "0010011", "0111101", "0100011",
      "0110001", "0101111", "0111011", "0110111", "0001011"
  };

  private static final String[] UPCA_R_PATTERNS = {
      "1110010", "1100110", "1101100", "1000010", "1011100",
      "1001110", "1010000", "1000100", "1001000", "1110100"
  };

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
    EnumSet<BarCodeFlag> flags = EnumSet.noneOf(BarCodeFlag.class);

    for (BDA_BarCodeData bda : state.getBarcodeData()) {
      if (bda.getText() != null) {
        data.append(bda.getText());
      }
      // Use the offset from the first BDA segment that has non-zero offsets
      if (xOffset == -1 || yOffset == -1) {
        xOffset = bda.getxOffset();
        yOffset = bda.getyOffset();
      }
      if (bda.getBarCodeFlags() != null) {
        flags.addAll(bda.getBarCodeFlags());
      }
    }

    String content = data.toString();
    if (content.isEmpty()) {
      return;
    }

    float totalWidth = 0;
    int startX = state.getxOrigin() + Math.max(0, xOffset);
    int startY = state.getyOrigin() + Math.max(0, yOffset);

    if (!flags.contains(BarCodeFlag.SuppressBarCodeSymbol)) {
      switch (state.getBarcodeType()) {
        case Code39_3of9Code_AIM_USS_39:
          totalWidth = renderCode39(content, startX, startY, state, canvas);
          break;
        case Interleaved_2of5__ITF14__AIM_USS_I_2of5:
          totalWidth = renderInterleaved2of5(content, startX, startY, state, canvas);
          break;
        case UPC_CGPC_VersionA:
          totalWidth = renderUpcA(content, startX, startY, state, canvas);
          break;
        default:
          // TODO: Implement other barcode types
          break;
      }
    }

    if (!flags.contains(BarCodeFlag.HRINotPresent)) {
      renderHRI(content, startX, startY, totalWidth, flags, state, canvas);
    }
  }

  private static float renderCode39(String content, int x, int y, PdfBarcodeState state, PdfCanvas canvas) {
    // Ensure start/stop characters
    if (!content.startsWith("*")) {
      content = "*" + content;
    }
    if (!content.endsWith("*")) {
      content = content + "*";
    }

    // Since the canvas is already scaled by the CTM, we work in AFP units.
    // Module width is in mils (1/1000 inch).
    // Base unit of BCOCA is usually 1/1440 inch.
    // 1 mil = 1.44 AFP units.
    float narrowWidth = state.getModuleWidthInMils() * 1.44f;
    if (narrowWidth <= 0) {
      narrowWidth = 20.0f; // Default ~14 mils
    }
    float wideWidth = narrowWidth * (state.getWideToNarrowRatio() / 100.0f);
    if (wideWidth <= narrowWidth) {
      wideWidth = narrowWidth * 2.5f;
    }

    float height = state.getElementHeight();
    if (height <= 0) {
      height = 500.0f; // ~1/3 inch
    }

    canvas.saveState();
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
      if (i < content.length() - 1) {
        curX += narrowWidth;
      }
    }
    canvas.restoreState();
    return curX - x;
  }

  private static float renderUpcA(String content, int x, int y, PdfBarcodeState state, PdfCanvas canvas) {
    // Digits only, typically 11 or 12 digits. If 11, check digit is calculated?
    // BCOCA usually provides the full data including check digit if required.
    String digits = content.replaceAll("[^0-9]", "");
    if (digits.length() < 11) {
      return 0;
    }
    if (digits.length() > 12) {
      digits = digits.substring(0, 12);
    } else if (digits.length() == 11) {
      // Calculate check digit (Standard UPC-A)
      int sum = 0;
      for (int i = 0; i < 11; i++) {
        int d = digits.charAt(i) - '0';
        sum += (i % 2 == 0) ? d * 3 : d;
      }
      int check = (10 - (sum % 10)) % 10;
      digits += check;
    }

    float narrowWidth = state.getModuleWidthInMils() * 1.44f;
    if (narrowWidth <= 0) {
      narrowWidth = 20.0f;
    }

    float height = state.getElementHeight();
    if (height <= 0) {
      height = 500.0f;
    }

    canvas.saveState();
    float curX = x;
    float curY = y;

    // Start pattern: 101
    renderBitPattern("101", curX, curY, height, narrowWidth, canvas);
    curX += 3 * narrowWidth;

    // First 6 digits (L-patterns)
    for (int i = 0; i < 6; i++) {
      String pattern = UPCA_L_PATTERNS[digits.charAt(i) - '0'];
      renderBitPattern(pattern, curX, curY, height, narrowWidth, canvas);
      curX += 7 * narrowWidth;
    }

    // Center guard: 01010
    renderBitPattern("01010", curX, curY, height, narrowWidth, canvas);
    curX += 5 * narrowWidth;

    // Last 6 digits (R-patterns)
    for (int i = 6; i < 12; i++) {
      String pattern = UPCA_R_PATTERNS[digits.charAt(i) - '0'];
      renderBitPattern(pattern, curX, curY, height, narrowWidth, canvas);
      curX += 7 * narrowWidth;
    }

    // Stop pattern: 101
    renderBitPattern("101", curX, curY, height, narrowWidth, canvas);
    curX += 3 * narrowWidth;

    canvas.restoreState();
    return curX - x;
  }

  private static void renderBitPattern(String pattern, float x, float y, float height, float moduleWidth, PdfCanvas canvas) {
    for (int i = 0; i < pattern.length(); i++) {
      if (pattern.charAt(i) == '1') {
        canvas.rectangle(x + i * moduleWidth, y - height, moduleWidth, height).fill();
      }
    }
  }

  private static float renderInterleaved2of5(String content, int x, int y, PdfBarcodeState state, PdfCanvas canvas) {
    // Only digits allowed, must be even length
    String digits = content.replaceAll("[^0-9]", "");
    if (digits.length() % 2 != 0) {
      digits = "0" + digits;
    }

    float narrowWidth = state.getModuleWidthInMils() * 1.44f;
    if (narrowWidth <= 0) {
      narrowWidth = 20.0f;
    }
    float wideWidth = narrowWidth * (state.getWideToNarrowRatio() / 100.0f);
    if (wideWidth <= narrowWidth) {
      wideWidth = narrowWidth * 2.5f;
    }

    float height = state.getElementHeight();
    if (height <= 0) {
      height = 500.0f;
    }

    canvas.saveState();
    float curX = x;
    float curY = y;

    // Start pattern: N bar, N space, N bar, N space
    canvas.rectangle(curX, curY - height, narrowWidth, height).fill();
    curX += narrowWidth;
    curX += narrowWidth; // space
    canvas.rectangle(curX, curY - height, narrowWidth, height).fill();
    curX += narrowWidth;
    curX += narrowWidth; // space

    for (int i = 0; i < digits.length(); i += 2) {
      String barPattern = ITF_PATTERNS[digits.charAt(i) - '0'];
      String spacePattern = ITF_PATTERNS[digits.charAt(i + 1) - '0'];

      for (int j = 0; j < 5; j++) {
        float bw = (barPattern.charAt(j) == 'w') ? wideWidth : narrowWidth;
        float sw = (spacePattern.charAt(j) == 'w') ? wideWidth : narrowWidth;

        canvas.rectangle(curX, curY - height, bw, height).fill();
        curX += bw;
        curX += sw;
      }
    }

    // Stop pattern: W bar, N space, N bar
    canvas.rectangle(curX, curY - height, wideWidth, height).fill();
    curX += wideWidth;
    curX += narrowWidth; // space
    canvas.rectangle(curX, curY - height, narrowWidth, height).fill();
    curX += narrowWidth;

    canvas.restoreState();
    return curX - x;
  }

  private static void renderHRI(String content, int x, int y, float barcodeWidth, EnumSet<BarCodeFlag> flags, PdfBarcodeState state, PdfCanvas canvas) {
    PdfFont font = state.getHriFont();
    if (font == null) {
      return;
    }

    // HRI positioning in AFP is typically centered under/above the barcode.
    // If width is 0 (suppressed), we center at (x,y)
    float fontSize = 100.0f; // ~5 points in 1440 units
    float textWidth = font.getWidth(content, fontSize);
    float textX = x + (barcodeWidth - textWidth) / 2.0f;
    float textY;

    float height = state.getElementHeight();
    if (height <= 0) {
      height = 500.0f;
    }

    if (flags.contains(BarCodeFlag.PositionHRIAbove)) {
      textY = y + fontSize * 0.2f; // Slight margin above
    } else {
      // Default or PositionHRIBelow
      textY = y - height - fontSize * 1.1f; // Margin below
    }

    canvas.beginText()
        .setFontAndSize(font, fontSize)
        .setTextMatrix(1, 0, 0, -1, textX, textY) // Note: -1 for Y as canvas is flipped
        .showText(content)
        .endText();
  }
}
