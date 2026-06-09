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

import com.itextpdf.barcodes.BarcodeDataMatrix;
import com.itextpdf.barcodes.BarcodePDF417;
import com.itextpdf.barcodes.BarcodeQRCode;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.pdf.canvas.PdfCanvas;
import com.itextpdf.kernel.pdf.xobject.PdfFormXObject;
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
  private static final Map<Character, String> CODABAR_MAP = new HashMap<>();
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

  private static final String[] UPCA_G_PATTERNS = {
      "0100111", "0110011", "0011011", "0100001", "0011101",
      "0111001", "0000101", "0010001", "0001001", "0010111"
  };

  private static final String[] EAN13_PARITY = {
      "LLLLLL", "LLGLGG", "LLGGLG", "LLGGGL", "LGLLGG",
      "LGGLLG", "LGGGLL", "LGLGLG", "LGLGGL", "LGGLGL"
  };

  private static final String[] UPCE_PARITY_0 = {
      "EEEOOO", "EEOEOO", "EEOOEO", "EEOOOE", "EOEEEO",
      "EOOEEE", "EOOOEE", "EOEOEE", "EOEOOE", "EOOEOE"
  };

  private static final String[] UPCE_PARITY_1 = {
      "OOOEEE", "OOEOEE", "OOEOOE", "OOEOOO", "OEOOEE",
      "OEEOOE", "OEEEOO", "OEOEOO", "OEOOOE", "OEEOOO"
  };

  private static final String[] CODE128_PATTERNS = {
      "212222", "222122", "222221", "121223", "121322", "131222", "122213", "122312", "132212", "221213",
      "221312", "231212", "112232", "122132", "122231", "113222", "123122", "123221", "223211", "221132",
      "221231", "213212", "223112", "312131", "311222", "321122", "321221", "312212", "322112", "322211",
      "212123", "212321", "232121", "111323", "131123", "131321", "112313", "132113", "132311", "211313",
      "231113", "231311", "112133", "112331", "132131", "113123", "113321", "133121", "313121", "211331",
      "231131", "213113", "213311", "213131", "311123", "311321", "331121", "312113", "312311", "332111",
      "314111", "221411", "431111", "111224", "111422", "121124", "121421", "141122", "141221", "112214",
      "112412", "122114", "122411", "142112", "142211", "241211", "221114", "413111", "241112", "134111",
      "111242", "121142", "121241", "114212", "124112", "124211", "411212", "421112", "421211", "212141",
      "214121", "412121", "111143", "111341", "131141", "114113", "114311", "411113", "411311", "113141",
      "114131", "311141", "411131", "211412", "211214", "211232", "233111"
  };

  private static final String[] POSTNET_PATTERNS = {
      "11000", // 0
      "00011", // 1
      "00101", // 2
      "00110", // 3
      "01001", // 4
      "01010", // 5
      "01100", // 6
      "10001", // 7
      "10010", // 8
      "10100"  // 9
  };

  private static final String[] JAPAN_POSTAL_PATTERNS = {
      "FFT", "FTF", "FTT", "TFF", "TFT", "TTF", "FFA", "FAF", "FAA", "AFF", // 0-9
      "AFA", // 10 (-)
      "AAF", "FFD", "FDF", "FDD", "DFF", "DFA", "DAF", "ADF"  // 11-18 (CC1-CC8)
  };

  private static final String RM4SCC_CHARS = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";
  private static final String[] RM4SCC_PATTERNS = {
      "1100", "1010", "1001", "0110", "0101", "0011"
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

    // Codabar patterns: 7 elements (4 bars, 3 spaces). 'w' for wide, 'n' for narrow.
    // Pattern order: Bar 1, Space 1, Bar 2, Space 2, Bar 3, Space 3, Bar 4
    CODABAR_MAP.put('0', "nnnnnww");
    CODABAR_MAP.put('1', "nnnnwwn");
    CODABAR_MAP.put('2', "nnnwnnw");
    CODABAR_MAP.put('3', "wwnnnnn");
    CODABAR_MAP.put('4', "nnwnnwn");
    CODABAR_MAP.put('5', "wnnnnwn");
    CODABAR_MAP.put('6', "nwnnnnw");
    CODABAR_MAP.put('7', "nwnnwnn");
    CODABAR_MAP.put('8', "nwwnnnn");
    CODABAR_MAP.put('9', "wnnwnnn");
    CODABAR_MAP.put('-', "nnnwwnn");
    CODABAR_MAP.put('$', "nnwwnnn");
    CODABAR_MAP.put(':', "wnnnwnw");
    CODABAR_MAP.put('/', "wnwnnnw");
    CODABAR_MAP.put('.', "wnwnwnn");
    CODABAR_MAP.put('+', "nnwnwnw");
    CODABAR_MAP.put('A', "nnwwnwn");
    CODABAR_MAP.put('B', "nwnwnnw");
    CODABAR_MAP.put('C', "nnnwnww");
    CODABAR_MAP.put('D', "nnnwwwn");
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
        case MSI_MmodifiedPlesseyCode:
          totalWidth = renderMsi(content, startX, startY, state, canvas);
          break;
        case Interleaved_2of5__ITF14__AIM_USS_I_2of5:
          totalWidth = renderInterleaved2of5(content, startX, startY, state, canvas);
          break;
        case UPC_CGPC_VersionA:
          totalWidth = renderUpcA(content, startX, startY, state, canvas);
          break;
        case UPC_CGPC_VersionE:
          totalWidth = renderUpcE(content, startX, startY, state, canvas);
          break;
        case EAN_8_includingJANShort:
          totalWidth = renderEan8(content, startX, startY, state, canvas);
          break;
        case EAN_13_includingJANStandard:
          totalWidth = renderEan13(content, startX, startY, state, canvas);
          break;
        case Code_128__GS1_128__UCC_EAN_128__AIM_USS_128__IntelligentMail__ContainerBarcode:
          totalWidth = renderCode128(content, startX, startY, state, canvas);
          break;
        case Codabar_2of7_AIM_USS_Codabar:
          totalWidth = renderCodabar(content, startX, startY, state, canvas);
          break;
        case Industrial_2of5:
          totalWidth = renderIndustrial2of5(content, startX, startY, state, canvas);
          break;
        case Matrix_2ofFive:
          totalWidth = renderMatrix2of5(content, startX, startY, state, canvas);
          break;
        case POSTNET_PLANET:
          totalWidth = renderPostnetPlanet(content, startX, startY, state, canvas);
          break;
        case JapanPostalBarCode:
          totalWidth = renderJapanPostal(content, startX, startY, state, canvas);
          break;
        case AustraliaPostBarCode:
          totalWidth = renderAustraliaPost(content, startX, startY, state, canvas);
          break;
        case RM4SCC_DutchKIX:
          totalWidth = renderRM4SCC(content, startX, startY, state, canvas);
          break;
        case IntelligentMailBarcode:
          totalWidth = renderIntelligentMailBarcode(content, startX, startY, state, canvas);
          break;
        case QRCode_2D:
          totalWidth = renderQrCode(content, startX, startY, state, canvas);
          break;
        case DataMatrix_GS1DataMatrix_2D:
          totalWidth = renderDataMatrix(content, startX, startY, state, canvas);
          break;
        case PDF417_2D:
          totalWidth = renderPdf417(content, startX, startY, state, canvas);
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

  private static float renderMsi(String content, int x, int y, PdfBarcodeState state, PdfCanvas canvas) {
    String digits = content.replaceAll("[^0-9]", "");
    if (state.getBarcodeModifier() == 0x02) {
      digits += calculateIbmModulo10(digits);
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

    // Start pattern: 110
    renderBitPattern("110", curX, curY, height, narrowWidth, canvas);
    curX += 3 * narrowWidth;

    for (int i = 0; i < digits.length(); i++) {
      int d = digits.charAt(i) - '0';
      // 4 bits per digit, BCD
      for (int bit = 3; bit >= 0; bit--) {
        boolean isOne = ((d >> bit) & 1) == 1;
        String pattern = isOne ? "110" : "100";
        renderBitPattern(pattern, curX, curY, height, narrowWidth, canvas);
        curX += 3 * narrowWidth;
      }
    }

    // Stop pattern: 1001
    renderBitPattern("1001", curX, curY, height, narrowWidth, canvas);
    curX += 4 * narrowWidth;

    canvas.restoreState();
    return curX - x;
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

  private static float renderCodabar(String content, int x, int y, PdfBarcodeState state, PdfCanvas canvas) {
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

    for (int i = 0; i < content.length(); i++) {
      char c = Character.toUpperCase(content.charAt(i));
      String pattern = CODABAR_MAP.get(c);
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

  private static float renderUpcE(String content, int x, int y, PdfBarcodeState state, PdfCanvas canvas) {
    String digits = content.replaceAll("[^0-9]", "");
    int ns = 0;
    String upce;

    if (digits.length() == 6) {
      upce = digits;
    } else if (digits.length() >= 11) {
      ns = digits.charAt(0) - '0';
      // Attempt zero suppression
      upce = suppressUpcA(digits.substring(0, 11));
      if (upce == null) {
        return 0; // Cannot be suppressed
      }
    } else {
      return 0;
    }

    // Expand to find check digit if needed
    String expanded = expandUpcE(ns, upce);
    int checkDigit = calculateModulo10_31(expanded);

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

    String parity = (ns == 0) ? UPCE_PARITY_0[checkDigit] : UPCE_PARITY_1[checkDigit];

    for (int i = 0; i < 6; i++) {
      char p = parity.charAt(i);
      String pattern = (p == 'O') ? UPCA_L_PATTERNS[upce.charAt(i) - '0'] : UPCA_G_PATTERNS[upce.charAt(i) - '0'];
      renderBitPattern(pattern, curX, curY, height, narrowWidth, canvas);
      curX += 7 * narrowWidth;
    }

    // Stop pattern: 010101
    renderBitPattern("010101", curX, curY, height, narrowWidth, canvas);
    curX += 6 * narrowWidth;

    canvas.restoreState();
    return curX - x;
  }

  private static String suppressUpcA(String upca11) {
    // upca11 is NS + 10 digits
    String d = upca11.substring(1); // 10 digits
    if (d.substring(2, 5).equals("000") || d.substring(2, 5).equals("100") || d.substring(2, 5).equals("200")) {
      return d.substring(0, 2) + d.substring(7, 10) + d.substring(2, 3);
    }
    if (d.substring(3, 6).equals("000")) {
      return d.substring(0, 3) + d.substring(8, 10) + "3";
    }
    if (d.substring(4, 7).equals("000")) {
      return d.substring(0, 4) + d.substring(9, 10) + "4";
    }
    if (d.substring(5, 9).equals("0000") && (d.charAt(9) >= '5' && d.charAt(9) <= '9')) {
      return d.substring(0, 5) + d.charAt(9);
    }
    return null;
  }

  private static String expandUpcE(int ns, String upce) {
    char d1 = upce.charAt(0);
    char d2 = upce.charAt(1);
    char d3 = upce.charAt(2);
    char d4 = upce.charAt(3);
    char d5 = upce.charAt(4);
    char d6 = upce.charAt(5);

    if (d6 == '0' || d6 == '1' || d6 == '2') {
      return "" + ns + d1 + d2 + d6 + "0000" + d3 + d4 + d5;
    } else if (d6 == '3') {
      return "" + ns + d1 + d2 + d3 + "00000" + d4 + d5;
    } else if (d6 == '4') {
      return "" + ns + d1 + d2 + d3 + d4 + "00000" + d5;
    } else {
      return "" + ns + d1 + d2 + d3 + d4 + d5 + "0000" + d6;
    }
  }

  private static int calculateModulo10_31(String digits) {
    int sum = 0;
    int len = digits.length();
    for (int i = 0; i < len; i++) {
      int d = digits.charAt(len - 1 - i) - '0';
      sum += (i % 2 == 0) ? d * 3 : d;
    }
    return (10 - (sum % 10)) % 10;
  }

  private static int calculateIbmModulo10(String digits) {
    int sum = 0;
    int len = digits.length();
    for (int i = 0; i < len; i++) {
      int d = digits.charAt(len - 1 - i) - '0';
      int weight = (i % 2 == 0) ? 2 : 1;
      int prod = d * weight;
      sum += (prod / 10) + (prod % 10);
    }
    return (10 - (sum % 10)) % 10;
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
      digits += calculateModulo10_31(digits);
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

  private static float renderEan8(String content, int x, int y, PdfBarcodeState state, PdfCanvas canvas) {
    String digits = content.replaceAll("[^0-9]", "");
    if (digits.length() < 7) {
      return 0;
    }
    if (digits.length() > 8) {
      digits = digits.substring(0, 8);
    } else if (digits.length() == 7) {
      digits += calculateModulo10_31(digits);
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

    // Left 4 digits (L-patterns)
    for (int i = 0; i < 4; i++) {
      String pattern = UPCA_L_PATTERNS[digits.charAt(i) - '0'];
      renderBitPattern(pattern, curX, curY, height, narrowWidth, canvas);
      curX += 7 * narrowWidth;
    }

    // Center guard: 01010
    renderBitPattern("01010", curX, curY, height, narrowWidth, canvas);
    curX += 5 * narrowWidth;

    // Right 4 digits (R-patterns)
    for (int i = 4; i < 8; i++) {
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

  private static float renderEan13(String content, int x, int y, PdfBarcodeState state, PdfCanvas canvas) {
    String digits = content.replaceAll("[^0-9]", "");
    if (digits.length() < 12) {
      return 0;
    }
    if (digits.length() > 13) {
      digits = digits.substring(0, 13);
    } else if (digits.length() == 12) {
      digits += calculateModulo10_31(digits);
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

    // The first digit determines the parity of the next 6 digits
    int firstDigit = digits.charAt(0) - '0';
    String parity = EAN13_PARITY[firstDigit];

    // Left 6 digits
    for (int i = 0; i < 6; i++) {
      char p = parity.charAt(i);
      String pattern = (p == 'L') ? UPCA_L_PATTERNS[digits.charAt(i + 1) - '0'] : UPCA_G_PATTERNS[digits.charAt(i + 1) - '0'];
      renderBitPattern(pattern, curX, curY, height, narrowWidth, canvas);
      curX += 7 * narrowWidth;
    }

    // Center guard: 01010
    renderBitPattern("01010", curX, curY, height, narrowWidth, canvas);
    curX += 5 * narrowWidth;

    // Right 6 digits (always R-patterns)
    for (int i = 7; i < 13; i++) {
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

  private static float renderIndustrial2of5(String content, int x, int y, PdfBarcodeState state, PdfCanvas canvas) {
    String digits = content.replaceAll("[^0-9]", "");
    if (state.getBarcodeModifier() == 0x02) {
      digits += calculateModulo10_31(digits);
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

    // Start pattern: W bar, W bar, N space
    canvas.rectangle(curX, curY - height, wideWidth, height).fill();
    curX += wideWidth + narrowWidth;
    canvas.rectangle(curX, curY - height, wideWidth, height).fill();
    curX += wideWidth + narrowWidth;

    for (int i = 0; i < digits.length(); i++) {
      String pattern = ITF_PATTERNS[digits.charAt(i) - '0'];
      for (int j = 0; j < 5; j++) {
        float w = (pattern.charAt(j) == 'w') ? wideWidth : narrowWidth;
        canvas.rectangle(curX, curY - height, w, height).fill();
        curX += w + narrowWidth;
      }
    }

    // Stop pattern: W bar, N space, W bar
    canvas.rectangle(curX, curY - height, wideWidth, height).fill();
    curX += wideWidth + narrowWidth;
    canvas.rectangle(curX, curY - height, wideWidth, height).fill();
    curX += wideWidth;

    canvas.restoreState();
    return curX - x;
  }

  private static float renderMatrix2of5(String content, int x, int y, PdfBarcodeState state, PdfCanvas canvas) {
    String digits = content.replaceAll("[^0-9]", "");
    if (state.getBarcodeModifier() == 0x02) {
      digits += calculateModulo10_31(digits);
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

    // Start pattern: W bar, N space, N bar, N space, N bar
    canvas.rectangle(curX, curY - height, wideWidth, height).fill();
    curX += wideWidth + narrowWidth;
    canvas.rectangle(curX, curY - height, narrowWidth, height).fill();
    curX += narrowWidth + narrowWidth;
    canvas.rectangle(curX, curY - height, narrowWidth, height).fill();
    curX += narrowWidth + narrowWidth;

    for (int i = 0; i < digits.length(); i++) {
      String pattern = ITF_PATTERNS[digits.charAt(i) - '0'];
      for (int j = 0; j < 5; j++) {
        float w = (pattern.charAt(j) == 'w') ? wideWidth : narrowWidth;
        boolean isBar = (j % 2 == 0);
        if (isBar) {
          canvas.rectangle(curX, curY - height, w, height).fill();
        }
        curX += w;
      }
      curX += narrowWidth; // inter-character gap
    }

    // Stop pattern: W bar, N space, N bar, N space, N bar
    canvas.rectangle(curX, curY - height, wideWidth, height).fill();
    curX += wideWidth + narrowWidth;
    canvas.rectangle(curX, curY - height, narrowWidth, height).fill();
    curX += narrowWidth + narrowWidth;
    canvas.rectangle(curX, curY - height, narrowWidth, height).fill();
    curX += narrowWidth;

    canvas.restoreState();
    return curX - x;
  }

  private static float renderCode128(String content, int x, int y, PdfBarcodeState state, PdfCanvas canvas) {
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

    // Initial subset selection
    int startCode;
    java.util.List<Integer> values = new java.util.ArrayList<>();

    // Heuristic: if numeric and length is even and at least 4, use Subset C.
    // If it has control characters (ASCII < 32), use Subset A.
    // Otherwise use Subset B.
    if (content.matches("^\\d+$") && content.length() >= 4 && content.length() % 2 == 0) {
      startCode = 105; // Start C
      for (int i = 0; i < content.length(); i += 2) {
        values.add(Integer.parseInt(content.substring(i, i + 2)));
      }
    } else {
      boolean hasControl = false;
      for (int i = 0; i < content.length(); i++) {
        if (content.charAt(i) < 32) {
          hasControl = true;
          break;
        }
      }

      if (hasControl) {
        startCode = 103; // Start A
        for (int i = 0; i < content.length(); i++) {
          char c = content.charAt(i);
          if (c < 32) values.add(c + 64);
          else if (c >= 32 && c <= 95) values.add(c - 32);
          else values.add(0); // Fallback for characters > 95 in Subset A
        }
      } else {
        startCode = 104; // Start B
        for (int i = 0; i < content.length(); i++) {
          char c = content.charAt(i);
          int val = c - 32;
          if (val >= 0 && val <= 102) values.add(val);
        }
      }
    }

    int checkSum = startCode;
    renderWidthPattern(CODE128_PATTERNS[startCode], curX, curY, height, narrowWidth, canvas);
    curX += 11 * narrowWidth;

    for (int i = 0; i < values.size(); i++) {
      int val = values.get(i);
      checkSum += val * (i + 1);
      renderWidthPattern(CODE128_PATTERNS[val], curX, curY, height, narrowWidth, canvas);
      curX += 11 * narrowWidth;
    }

    // Check digit
    int checkDigit = checkSum % 103;
    renderWidthPattern(CODE128_PATTERNS[checkDigit], curX, curY, height, narrowWidth, canvas);
    curX += 11 * narrowWidth;

    // Stop code (106) + final 2-unit bar
    renderWidthPattern(CODE128_PATTERNS[106], curX, curY, height, narrowWidth, canvas);
    curX += 11 * narrowWidth;
    canvas.rectangle(curX, curY - height, 2 * narrowWidth, height).fill();
    curX += 2 * narrowWidth;

    canvas.restoreState();
    return curX - x;
  }

  private static void renderWidthPattern(String widths, float x, float y, float height, float moduleWidth, PdfCanvas canvas) {
    float curX = x;
    for (int i = 0; i < widths.length(); i++) {
      int w = widths.charAt(i) - '0';
      boolean isBar = (i % 2 == 0);
      if (isBar) {
        canvas.rectangle(curX, y - height, w * moduleWidth, height).fill();
      }
      curX += w * moduleWidth;
    }
  }

  private static float renderPostnetPlanet(String content, int x, int y, PdfBarcodeState state, PdfCanvas canvas) {
    String digits = content.replaceAll("[^0-9]", "");
    if (digits.isEmpty()) {
      return 0;
    }

    boolean isPlanet = (state.getBarcodeModifier() == 0x04);

    // POSTNET/PLANET standard dimensions (Optimal)
    // Horizontal pitch: 22 bars per inch
    float pitch = 1440.0f / 22.0f; // ~65.45 AFP units
    float barWidth = 20.0f * 1.44f; // 28.8 AFP units
    float tallHeight = 125.0f * 1.44f; // 180 AFP units
    float shortHeight = 50.0f * 1.44f; // 72 AFP units

    canvas.saveState();
    float curX = x;
    float curY = y;

    // Start frame bar (always tall)
    canvas.rectangle(curX, curY - tallHeight, barWidth, tallHeight).fill();
    curX += pitch;

    int sum = 0;
    for (int i = 0; i < digits.length(); i++) {
      int d = digits.charAt(i) - '0';
      sum += d;
      curX = renderPostnetDigit(d, curX, curY, isPlanet, pitch, barWidth, tallHeight, shortHeight, canvas);
    }

    // Check digit
    int checkDigit = (10 - (sum % 10)) % 10;
    curX = renderPostnetDigit(checkDigit, curX, curY, isPlanet, pitch, barWidth, tallHeight, shortHeight, canvas);

    // Stop frame bar (always tall)
    canvas.rectangle(curX, curY - tallHeight, barWidth, tallHeight).fill();
    curX += pitch;

    canvas.restoreState();
    return curX - x;
  }

  private static float renderPostnetDigit(int digit, float curX, float curY, boolean isPlanet,
      float pitch, float barWidth, float tallHeight, float shortHeight, PdfCanvas canvas) {
    String pattern = POSTNET_PATTERNS[digit];
    for (int i = 0; i < pattern.length(); i++) {
      boolean isTall;
      if (isPlanet) {
        // PLANET: 0 is tall, 1 is short
        isTall = (pattern.charAt(i) == '0');
      } else {
        // POSTNET: 1 is tall, 0 is short
        isTall = (pattern.charAt(i) == '1');
      }
      float h = isTall ? tallHeight : shortHeight;
      canvas.rectangle(curX, curY - h, barWidth, h).fill();
      curX += pitch;
    }
    return curX;
  }

  private static float renderJapanPostal(String content, int x, int y, PdfBarcodeState state, PdfCanvas canvas) {
    java.util.List<Integer> values = new java.util.ArrayList<>();

    if (state.getBarcodeModifier() == 0x01) {
      // Direct input (EBCDIC-based code points)
      for (int i = 0; i < content.length(); i++) {
        char c = content.charAt(i);
        int val = switch (c) {
          case '0', '1', '2', '3', '4', '5', '6', '7', '8', '9' -> c - '0';
          case '-' -> 10;
          case 'A' -> 11; // CC1
          case 'B' -> 12; // CC2
          case 'C' -> 13; // CC3
          case 'D' -> 14; // CC4
          case 'E' -> 15; // CC5
          case 'F' -> 16; // CC6
          case 'G' -> 17; // CC7
          case 'H' -> 18; // CC8
          default -> -1;
        };
        if (val != -1) values.add(val);
      }
    } else {
      // Standard input: Postal code (7) + Address (up to 13)
      String cleaned = content.replaceAll("-", "");
      if (cleaned.length() < 7) return 0;

      // 1. Postal Code (7 digits)
      for (int i = 0; i < 7; i++) {
        values.add(cleaned.charAt(i) - '0');
      }

      // 2. Address Indication (expand A-Z)
      String address = cleaned.substring(7);
      java.util.List<Integer> addressValues = new java.util.ArrayList<>();
      for (int i = 0; i < address.length() && addressValues.size() < 13; i++) {
        char c = Character.toUpperCase(address.charAt(i));
        if (c >= '0' && c <= '9') {
          addressValues.add(c - '0');
        } else if (c == '-') {
          addressValues.add(10);
        } else if (c >= 'A' && c <= 'J') {
          addressValues.add(11); // CC1
          if (addressValues.size() < 13) addressValues.add(c - 'A');
        } else if (c >= 'K' && c <= 'T') {
          addressValues.add(12); // CC2
          if (addressValues.size() < 13) addressValues.add(c - 'K');
        } else if (c >= 'U' && c <= 'Z') {
          addressValues.add(13); // CC3
          if (addressValues.size() < 13) addressValues.add(c - 'U');
        }
      }

      // 3. Padding with CC4
      while (addressValues.size() < 13) {
        addressValues.add(14); // CC4
      }
      values.addAll(addressValues);

      // 4. Check Digit (Modulo 19)
      int sum = 0;
      for (int v : values) sum += v;
      values.add((19 - (sum % 19)) % 19);
    }

    // Standard dimensions (1440 units per inch base)
    float pitch = 1.3f * 1.44f * 20.0f; // ~37.44 AFP units
    float barWidth = 0.6f * 1.44f * 20.0f; // ~17.28 AFP units
    float height = state.getElementHeight() > 0 ? state.getElementHeight() : 500.0f;

    canvas.saveState();
    float curX = x;
    float curY = y;

    // Start bar: Ascender (A) + Full (F)? Actually Japanese Post says Start is specific.
    // Standard 4-state Japan Postal: Start=Full+Ascender (FA), Stop=Full+Ascender (AF) wait.
    // Patterns found: Start=FA, Stop=AF. Values: 0-18 are 3 bars each.
    renderJapanPostalBar('F', curX, curY, height, barWidth, canvas); curX += pitch;
    renderJapanPostalBar('A', curX, curY, height, barWidth, canvas); curX += pitch;

    for (int val : values) {
      if (val < 0 || val >= JAPAN_POSTAL_PATTERNS.length) continue;
      String pattern = JAPAN_POSTAL_PATTERNS[val];
      for (int i = 0; i < pattern.length(); i++) {
        renderJapanPostalBar(pattern.charAt(i), curX, curY, height, barWidth, canvas);
        curX += pitch;
      }
    }

    // Stop bar
    renderJapanPostalBar('A', curX, curY, height, barWidth, canvas); curX += pitch;
    renderJapanPostalBar('F', curX, curY, height, barWidth, canvas); curX += pitch;

    canvas.restoreState();
    return curX - x;
  }

  private static void renderFourStateBar(char state, float x, float y, float height, float width, float trackerHeight, PdfCanvas canvas) {
    float offset = (height - trackerHeight) / 2.0f;
    float top = y;
    float bottom = y - height;

    switch (state) {
      case 'T' -> { // Tracker / Timing
        top = y - offset;
        bottom = y - height + offset;
      }
      case 'A' -> { // Ascender
        bottom = y - height + offset;
      }
      case 'D' -> { // Descender
        top = y - offset;
      }
      // 'F' or other is Full, uses defaults
    }
    canvas.rectangle(x, bottom, width, top - bottom).fill();
  }

  private static void renderJapanPostalBar(char state, float x, float y, float height, float width, PdfCanvas canvas) {
    renderFourStateBar(state, x, y, height, width, height / 3.0f, canvas);
  }

  private static float renderIntelligentMailBarcode(String content, int x, int y, PdfBarcodeState state, PdfCanvas canvas) {
    // Modifier 0x01: Direct input of 'F', 'A', 'D', 'T' (65 characters)
    if (state.getBarcodeModifier() != 0x01) {
      // TODO: Implement other modifiers (requires complex encoding)
      return 0;
    }

    String bars = content.toUpperCase().replaceAll("[^FADT]", "");
    if (bars.length() < 65) {
      return 0;
    }
    if (bars.length() > 65) {
      bars = bars.substring(0, 65);
    }

    // USPS IMB Dimensions (Optimal in 1440 units)
    // Pitch: 0.045" -> 0.045 * 1440 = 64.8 units
    // Bar Width: 0.020" -> 0.020 * 1440 = 28.8 units
    // Full Height: 0.125" -> 0.125 * 1440 = 180 units
    // Tracker Height: 0.039" -> 0.039 * 1440 = 56.16 units

    float pitch = 64.8f;
    float barWidth = 28.8f;
    float height = state.getElementHeight() > 0 ? state.getElementHeight() : 180.0f;

    canvas.saveState();
    float curX = x;
    float curY = y;

    for (int i = 0; i < bars.length(); i++) {
      renderImbBar(bars.charAt(i), curX, curY, height, barWidth, canvas);
      curX += pitch;
    }

    canvas.restoreState();
    return curX - x;
  }

  private static void renderImbBar(char state, float x, float y, float height, float width, PdfCanvas canvas) {
    renderFourStateBar(state, x, y, height, width, height * 0.312f, canvas);
  }

  private static float renderQrCode(String content, int x, int y, PdfBarcodeState state, PdfCanvas canvas) {
    // Modifier 0x12 indicates GS1 compliance.
    // In many QR implementations, GS1 is indicated by a leading FNC1 character.
    if (state.getBarcodeModifier() == (byte) 0x12) {
      content = "\u00f1" + content;
    }

    // For now, we use the standard BarcodeQRCode which defaults to Model 2.
    BarcodeQRCode qrCode = new BarcodeQRCode(content);
    PdfFormXObject xObject = qrCode.createFormXObject(canvas.getDocument());

    // Scaling: 1 mil = 1/1000 inch.
    // AFP units (as configured in PdfHandler) are 1/1440 inch.
    // Therefore, 1 mil = 1.44 AFP units.
    // Since the canvas is already scaled to AFP units, we use 1.44f.
    float moduleSize = state.getModuleWidthInMils() * 1.44f;
    if (moduleSize <= 0) {
      moduleSize = 20.0f; // Default ~14 mils
    }

    float width = xObject.getWidth();

    canvas.saveState();
    // QR Code modules are 1x1 in iText FormXObject.
    // Scale by moduleSize and flip Y because AFP is top-down and our canvas CTM is already flipped.
    // We want the QR code to grow downwards from (x, y) in the AFP coordinate system.
    canvas.concatMatrix(moduleSize, 0, 0, -moduleSize, x, y);
    canvas.addXObject(xObject);
    canvas.restoreState();

    return width * moduleSize;
  }

  private static float renderDataMatrix(String content, int x, int y, PdfBarcodeState state, PdfCanvas canvas) {
    // Modifier 0x02 indicates GS1 compliance. Prepend FNC1.
    if (state.getBarcodeModifier() == (byte) 0x02) {
      content = "\u00f1" + content;
    }
    BarcodeDataMatrix dm = new BarcodeDataMatrix(content);
    PdfFormXObject xObject = dm.createFormXObject(canvas.getDocument());

    float moduleSize = state.getModuleWidthInMils() * 1.44f;
    if (moduleSize <= 0) {
      moduleSize = 20.0f;
    }

    float width = xObject.getWidth();

    canvas.saveState();
    canvas.concatMatrix(moduleSize, 0, 0, -moduleSize, x, y);
    canvas.addXObject(xObject);
    canvas.restoreState();

    return width * moduleSize;
  }

  private static float renderPdf417(String content, int x, int y, PdfBarcodeState state, PdfCanvas canvas) {
    BarcodePDF417 pdf = new BarcodePDF417();
    pdf.setCode(content);
    PdfFormXObject xObject = pdf.createFormXObject(canvas.getDocument());

    float moduleSize = state.getModuleWidthInMils() * 1.44f;
    if (moduleSize <= 0) {
      moduleSize = 20.0f;
    }

    float width = xObject.getWidth();

    canvas.saveState();
    canvas.concatMatrix(moduleSize, 0, 0, -moduleSize, x, y);
    canvas.addXObject(xObject);
    canvas.restoreState();

    return width * moduleSize;
  }

  private static float renderAustraliaPost(String content, int x, int y, PdfBarcodeState state, PdfCanvas canvas) {
    // This implementation focuses on Proprietary Encoding (Modifiers 0x04 and 0x07)
    // where input data digits 0-3 directly specify the 4 bar states.
    if (state.getBarcodeModifier() != 0x04 && state.getBarcodeModifier() != 0x07) {
      // TODO: Implement other modifiers (requires Reed-Solomon and Table N/C encoding)
      return 0;
    }

    // Extract sorting code (first 8 digits) - for Proprietary these are usually ignored or handled separately
    // The spec says: An 8 digit number representing the Sorting Code followed by up to 16/31 numeric digits (0–3)
    String cleaned = content.replaceAll("[^0-9]", "");
    if (cleaned.length() <= 8) {
      return 0;
    }

    String barStates = cleaned.substring(8);

    // Australia Post Dimensions (Optimal in 1440 units)
    // Pitch: 1440 / 23.5 ≈ 61.28 units
    // Bar Width: 20.0 * 1.44 = 28.8 units
    // Full Height: 197 mils * 1.44 ≈ 283.68 units
    float pitch = 61.28f;
    float barWidth = 28.8f;
    float height = state.getElementHeight() > 0 ? state.getElementHeight() : 284.0f;

    canvas.saveState();
    float curX = x;
    float curY = y;

    // Start bar (Proprietary doesn't specify if it's auto-generated, but BCOCA says printer generates start/stop)
    // For AusPost, start is usually a Full bar.
    renderAusPostBar('0', curX, curY, height, barWidth, canvas);
    curX += pitch;

    for (int i = 0; i < barStates.length(); i++) {
      renderAusPostBar(barStates.charAt(i), curX, curY, height, barWidth, canvas);
      curX += pitch;
    }

    // Stop bar
    renderAusPostBar('0', curX, curY, height, barWidth, canvas);
    curX += pitch;

    canvas.restoreState();
    return curX - x;
  }

  private static void renderAusPostBar(char state, float x, float y, float height, float width, PdfCanvas canvas) {
    char mappedState = switch (state) {
      case '1' -> 'A';
      case '2' -> 'D';
      case '3' -> 'T';
      default -> 'F';
    };
    renderFourStateBar(mappedState, x, y, height, width, height / 3.0f, canvas);
  }

  private static float renderRM4SCC(String content, int x, int y, PdfBarcodeState state, PdfCanvas canvas) {
    String cleaned = content.toUpperCase().replaceAll("[^0-9A-Z]", "");
    boolean isKix = (state.getBarcodeModifier() == 0x01);

    float pitch = 64.8f;
    float barWidth = 18.0f;
    float height = state.getElementHeight() > 0 ? state.getElementHeight() : 288.0f;
    float trackerHeight = height / 3.0f;

    canvas.saveState();
    float curX = x;
    float curY = y;

    if (!isKix) {
      renderFourStateBar('A', curX, curY, height, barWidth, trackerHeight, canvas);
      curX += pitch;
    }

    int sumRow = 0;
    int sumCol = 0;

    for (int i = 0; i < cleaned.length(); i++) {
      int idx = RM4SCC_CHARS.indexOf(cleaned.charAt(i));
      if (idx == -1) continue;
      int row = idx / 6;
      int col = idx % 6;
      sumRow += row;
      sumCol += col;
      curX = renderRM4SCCChar(row, col, curX, curY, height, barWidth, trackerHeight, pitch, canvas);
    }

    if (!isKix) {
      curX = renderRM4SCCChar(sumRow % 6, sumCol % 6, curX, curY, height, barWidth, trackerHeight, pitch, canvas);
      renderFourStateBar('F', curX, curY, height, barWidth, trackerHeight, canvas);
      curX += pitch;
    }

    canvas.restoreState();
    return curX - x;
  }

  private static float renderRM4SCCChar(int row, int col, float curX, float curY, float height, float barWidth, float trackerHeight, float pitch, PdfCanvas canvas) {
    String top = RM4SCC_PATTERNS[row];
    String bottom = RM4SCC_PATTERNS[col];
    for (int j = 0; j < 4; j++) {
      char state;
      boolean t = top.charAt(j) == '1';
      boolean b = bottom.charAt(j) == '1';
      if (t && b) state = 'F';
      else if (t) state = 'A';
      else if (b) state = 'D';
      else state = 'T';
      renderFourStateBar(state, curX, curY, height, barWidth, trackerHeight, canvas);
      curX += pitch;
    }
    return curX;
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
