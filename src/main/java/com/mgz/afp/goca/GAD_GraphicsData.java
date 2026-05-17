/*
Copyright 2015 Rudolf Fiala

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
package com.mgz.afp.goca;
import javax.xml.bind.annotation.XmlRootElement;

import com.mgz.afp.base.StructuredField;
import com.mgz.afp.base.annotations.AFPField;
import com.mgz.afp.enums.IMutualExclusiveGroupedFlag;
import com.mgz.afp.enums.MutualExclusiveGroupedFlagHandler;
import com.mgz.afp.exceptions.AFPParserException;
import com.mgz.afp.exceptions.IAFPDecodeableWriteable;
import com.mgz.afp.goca.GAD_DrawingOrder.*;
import com.mgz.afp.parser.AFPParserConfiguration;
import com.mgz.util.Constants;
import com.mgz.util.UtilBinaryDecoding;
import com.mgz.util.UtilCharacterEncoding;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;

/**
 * GOCA, 164.<br><br>
 * <p>
 * The graphics segments for a graphics object are contained within one or more GAD structured
 * fields. Receipt of the first segment starts the drawing process. No restrictions exist on how
 * much or how little graphics data is specified in a single GAD, except for the length limit of the
 * structured field. A GAD, for example, can carry partial segments, full segments, multiple
 * segments, or any combination of these. The only requirement is that the data itself is ordered in
 * the sequence that is expected for immediate processing and that the last GAD completes the last
 * segment. Because this environment does not support the calling of segments, all segments should
 * be chained segments. Any unchained segments in the data are ignored. The GAD structured field is
 * optional in a MO:DCA graphics object and may be repeated multiple times.
 */
public class GAD_GraphicsData extends StructuredField {
  @AFPField
  private List<GAD_DrawingOrder> drawingOrders;

  public static final List<GAD_DrawingOrder> buildDrawingOrders(byte[] sfData, int offset, int length, AFPParserConfiguration config) throws AFPParserException {

    int actualLength = length != -1 ? length : sfData.length - offset;

    List<GAD_DrawingOrder> drawingOrders = new ArrayList<GAD_DrawingOrder>();

    int pos = 0;
    while (pos < actualLength) {

      int dotLength = 0;
      GAD_DrawingOrder drawingOrder = null;

      int drawingOrderCode = UtilBinaryDecoding.parseInt(sfData, offset + pos, 1);
      switch (drawingOrderCode) {
        case 0x00: {
          drawingOrder = new GNOP1_NopOperation();
          dotLength = 1;
        }
        break;
        case 0x01: {
          drawingOrder = new GCOMT_Comment();
          dotLength = (sfData[offset + pos + 1] & 0xFF) + 2;
        }
        break;
        case 0x04: {
          drawingOrder = new GSGCH_SegmentCharacteristics();
          dotLength = (sfData[offset + pos + 1] & 0xFF) + 2;
        }
        break;
        case 0x08: {
          drawingOrder = new GSPS_SetPatternSet();
          dotLength = 2;
        }
        break;
        case 0x0a: {
          drawingOrder = new GSCOL_SetColor();
          dotLength = 2;
        }
        break;
        case 0x0c: {
          drawingOrder = new GSMX_SetMix();
          dotLength = 2;
        }
        break;
        case 0x0d: {
          drawingOrder = new GSBMX_SetBackgroundMix();
          dotLength = 2;
        }
        break;
        case 0x11: {
          drawingOrder = new GSFLW_SetFractionLineWidth();
          dotLength = (sfData[offset + pos + 1] & 0xFF) + 2;
        }
        break;
        case 0x18: {
          drawingOrder = new GSLT_SetLineType();
          dotLength = 2;
        }
        break;
        case 0x19: {
          drawingOrder = new GSLW_SetLineWidth();
          dotLength = 2;
        }
        break;
        case 0x1a: {
          drawingOrder = new GSLE_SetLineEnd();
          dotLength = 2;
        }
        break;
        case 0x1b: {
          drawingOrder = new GSLJ_SetLineJoin();
          dotLength = 2;
        }
        break;
        case 0x20: {
          drawingOrder = new GSCLT_SetCustomLineType();
          dotLength = (sfData[offset + pos + 1] & 0xFF) + 2;
        }
        break;
        case 0x21: {
          drawingOrder = new GSCP_SetCurrentPosition();
          dotLength = (sfData[offset + pos + 1] & 0xFF) + 2;
        }
        break;
        case 0x22: {
          drawingOrder = new GSAP_SetArcParameters();
          dotLength = (sfData[offset + pos + 1] & 0xFF) + 2;
        }
        break;
        case 0x26: {
          drawingOrder = new GSECOL_SetExtendedColor();
          dotLength = (sfData[offset + pos + 1] & 0xFF) + 2;
        }
        break;
        case 0x28: {
          drawingOrder = new GSPT_SetPatternSymbol();
          dotLength = 2;
        }
        break;
        case 0x29: {
          drawingOrder = new GSMT_SetMarkerSymbol();
          dotLength = 2;
        }
        break;
        case 0x33: {
          drawingOrder = new GSCC_SetCharacterCell();
          dotLength = (sfData[offset + pos + 1] & 0xFF) + 2;
        }
        break;
        case 0x34: {
          drawingOrder = new GSCA_SetCharacterAngle();
          dotLength = (sfData[offset + pos + 1] & 0xFF) + 2;
        }
        break;
        case 0x35: {
          drawingOrder = new GSCH_SetCharacterShear();
          dotLength = (sfData[offset + pos + 1] & 0xFF) + 2;
        }
        break;
        case 0x37: {
          drawingOrder = new GSMC_SetMarkerCell();
          dotLength = (sfData[offset + pos + 1] & 0xFF) + 2;
        }
        break;
        case 0x38: {
          drawingOrder = new GSCS_SetCharacterSet();
          dotLength = 2;
        }
        break;
        case 0x39: {
          drawingOrder = new GSCR_SetCharacterPrecision();
          dotLength = 2;
        }
        break;
        case 0x3a: {
          drawingOrder = new GSCD_SetCharacterDirection();
          dotLength = 2;
        }
        break;
        case 0x3b: {
          drawingOrder = new GSMP_SetMarkerPrecision();
          dotLength = 2;
        }
        break;
        case 0x3c: {
          drawingOrder = new GSMS_SetMarkerSet();
          dotLength = 2;
        }
        break;
        case 0x3e: {
          drawingOrder = new GEPROL_EndProlog();
          dotLength = 2;
        }
        break;
        case 0x43: {
          drawingOrder = new GSPIK_SetPickIdentifier();
          dotLength = 2;
        }
        break;
        case 0x5e: {
          drawingOrder = new GECP_EndCustomPattern();
          dotLength = 2;
        }
        break;
        case 0x60: {
          drawingOrder = new GEAR_EndArea();
          dotLength = (sfData[offset + pos + 1] & 0xFF) + 2;
        }
        break;
        case 0x68: {
          drawingOrder = new GBAR_BeginArea();
          dotLength = 2;
        }
        break;
        case 0x70: {
          drawingOrder = new GBSEG_BeginSegment();
          int segDataLen = UtilBinaryDecoding.parseInt(sfData, offset + pos + 8, 2);
          dotLength = 14 + segDataLen;
        }
        break;
        case 0x71: {
          drawingOrder = new GESEG_EndSegment();
          dotLength = 2;
        }
        break;
        case 0x80: {
          drawingOrder = new GCBOX_BoxAtCurrentPosition();
          dotLength = (sfData[offset + pos + 1] & 0xFF) + 2;
        }
        break;
        case 0x81: {
          drawingOrder = new GCLINE_LineAtCurrentPosition();
          dotLength = (sfData[offset + pos + 1] & 0xFF) + 2;
        }
        break;
        case 0x82: {
          drawingOrder = new GCMRK_MarkerAtCurrentPosition();
          dotLength = (sfData[offset + pos + 1] & 0xFF) + 2;
        }
        break;
        case 0x83: {
          drawingOrder = new GCCHST_CharacterStringAtCurrentPosition();
          dotLength = (sfData[offset + pos + 1] & 0xFF) + 2;
        }
        break;
        case 0x85: {
          drawingOrder = new GCFLT_FilletAtCurrentPosition();
          dotLength = (sfData[offset + pos + 1] & 0xFF) + 2;
        }
        break;
        case 0x87: {
          drawingOrder = new GCFARC_FullArcAtCurrentPosition();
          dotLength = (sfData[offset + pos + 1] & 0xFF) + 2;
        }
        break;
        case 0x91: {
          drawingOrder = new GCBIMG_BeginImageAtCurrentPosition();
          dotLength = (sfData[offset + pos + 1] & 0xFF) + 2;
        }
        break;
        case 0x92: {
          drawingOrder = new GIMD_ImageData();
          dotLength = (sfData[offset + pos + 1] & 0xFF) + 2;
        }
        break;
        case 0x93: {
          drawingOrder = new GEIMG_EndImage();
          dotLength = (sfData[offset + pos + 1] & 0xFF) + 2;
        }
        break;
        case 0xa0: {
          drawingOrder = new GSPRP_SetPatternReferencePoint();
          dotLength = (sfData[offset + pos + 1] & 0xFF) + 2;
        }
        break;
        case 0xa1: {
          drawingOrder = new GCRLINE_RelativeLineAtCurrentPosition();
          dotLength = (sfData[offset + pos + 1] & 0xFF) + 2;
        }
        break;
        case 0xa3: {
          drawingOrder = new GCPARC_PartialArcAtCurrentPosition();
          dotLength = (sfData[offset + pos + 1] & 0xFF) + 2;
        }
        break;
        case 0xa5: {
          drawingOrder = new GCCBEZ_CubicBezierCurveAtCurrentPosition();
          dotLength = (sfData[offset + pos + 1] & 0xFF) + 2;
        }
        break;
        case 0xb2: {
          drawingOrder = new GSPCOL_SetProcessColor();
          dotLength = (sfData[offset + pos + 1] & 0xFF) + 2;
        }
        break;
        case 0xc0: {
          drawingOrder = new GBOX_BoxAtGivenPosition();
          dotLength = (sfData[offset + pos + 1] & 0xFF) + 2;
        }
        break;
        case 0xc1: {
          drawingOrder = new GLINE_LineAtGivenPosition();
          dotLength = (sfData[offset + pos + 1] & 0xFF) + 2;
        }
        break;
        case 0xc2: {
          drawingOrder = new GMRK_MarkerAtGivenPosition();
          dotLength = (sfData[offset + pos + 1] & 0xFF) + 2;
        }
        break;
        case 0xc3: {
          drawingOrder = new GCHST_CharacterStringAtGivenPosition();
          dotLength = (sfData[offset + pos + 1] & 0xFF) + 2;
        }
        break;
        case 0xc5: {
          drawingOrder = new GFLT_FilletAtGivenPosition();
          dotLength = (sfData[offset + pos + 1] & 0xFF) + 2;
        }
        break;
        case 0xc7: {
          drawingOrder = new GFARC_FullArcAtGivenPosition();
          dotLength = (sfData[offset + pos + 1] & 0xFF) + 2;
        }
        break;
        case 0xd1: {
          drawingOrder = new GBIMG_BeginImageAtGivenPosition();
          dotLength = (sfData[offset + pos + 1] & 0xFF) + 2;
        }
        break;
        case 0xe1: {
          drawingOrder = new GRLINE_RelativeLineAtGivenPosition();
          dotLength = (sfData[offset + pos + 1] & 0xFF) + 2;
        }
        break;
        case 0xe3: {
          drawingOrder = new GPARC_PartialArcAtGivenPosition();
          dotLength = (sfData[offset + pos + 1] & 0xFF) + 2;
        }
        break;
        case 0xde: {
          drawingOrder = new GBCP_BeginCustomPattern();
          dotLength = (sfData[offset + pos + 1] & 0xFF) + 2;
        }
        break;
        case 0xdf: {
          drawingOrder = new GDPT_DeletePattern();
          dotLength = (sfData[offset + pos + 1] & 0xFF) + 2;
        }
        break;
        case 0xe5: {
          drawingOrder = new GCBEZ_CubicBezierCurveAtGivenPosition();
          dotLength = (sfData[offset + pos + 1] & 0xFF) + 2;
        }
        break;
        case 0xfe: {
          int qualifier = (sfData[offset + pos + 1] & 0xFF);
          if (qualifier == 0xDC) {
            drawingOrder = new GLGD_LinearGradient();
          } else if (qualifier == 0xDD) {
            drawingOrder = new GRGD_RadialGradient();
          } else {
            drawingOrder = new GEXO_ExtendedOrder();
          }
          dotLength = UtilBinaryDecoding.parseInt(sfData, offset + pos + 2, 2) + 4;
        }
        break;
        default: {
          drawingOrder = null;
        }
        break;
      }

      if (drawingOrder == null) {
        throw new AFPParserException("The drawing order code 0x" + Integer.toHexString(drawingOrderCode) + "is unknown.");
      }

      drawingOrder.decodeAFP(sfData, offset + pos, dotLength, config);
      drawingOrders.add(drawingOrder);

      pos += dotLength;

    }

    return drawingOrders;
  }

  @Override
  public void decodeAFP(byte[] sfData, int offset, int length, AFPParserConfiguration config) throws AFPParserException {
    int actualLength = getActualLength(sfData, offset, length);
    if (actualLength > 0) {
      drawingOrders = buildDrawingOrders(sfData, offset, actualLength, config);
    } else {
      drawingOrders = null;
    }
  }

  @Override
  public void writeAFP(OutputStream os, AFPParserConfiguration config) throws IOException {
    if (drawingOrders != null && !drawingOrders.isEmpty()) {
      ByteArrayOutputStream baos = new ByteArrayOutputStream();
      for (GAD_DrawingOrder order : drawingOrders) {
        order.writeAFP(baos, config);
      }
      writeFullStructuredField(os, baos.toByteArray());
    } else {
      writeFullStructuredField(os, null);
    }
  }

  public List<GAD_DrawingOrder> getDrawingOrders() {
    return drawingOrders;
  }

  public void setDrawingOrders(List<GAD_DrawingOrder> drawingOrders) {
    this.drawingOrders = drawingOrders;
  }

}

