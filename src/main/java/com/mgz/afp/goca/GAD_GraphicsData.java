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
import com.mgz.afp.goca.GAD_DrawingOrder.GBAR_BeginArea;
import com.mgz.afp.goca.GAD_DrawingOrder.GBCP_BeginCustomPattern;
import com.mgz.afp.goca.GAD_DrawingOrder.GBIMG_BeginImageAtGivenPosition;
import com.mgz.afp.goca.GAD_DrawingOrder.GBOX_BoxAtGivenPosition;
import com.mgz.afp.goca.GAD_DrawingOrder.GBSEG_BeginSegment;
import com.mgz.afp.goca.GAD_DrawingOrder.GCBEZ_CubicBezierCurveAtGivenPosition;
import com.mgz.afp.goca.GAD_DrawingOrder.GCBIMG_BeginImageAtCurrentPosition;
import com.mgz.afp.goca.GAD_DrawingOrder.GCCBEZ_CubicBezierCurveAtCurrentPosition;
import com.mgz.afp.goca.GAD_DrawingOrder.GCBOX_BoxAtCurrentPosition;
import com.mgz.afp.goca.GAD_DrawingOrder.GCCHST_CharacterStringAtCurrentPosition;
import com.mgz.afp.goca.GAD_DrawingOrder.GCFARC_FullArcAtCurrentPosition;
import com.mgz.afp.goca.GAD_DrawingOrder.GCFLT_FilletAtCurrentPosition;
import com.mgz.afp.goca.GAD_DrawingOrder.GCHST_CharacterStringAtGivenPosition;
import com.mgz.afp.goca.GAD_DrawingOrder.GCLINE_LineAtCurrentPosition;
import com.mgz.afp.goca.GAD_DrawingOrder.GCMRK_MarkerAtCurrentPosition;
import com.mgz.afp.goca.GAD_DrawingOrder.GCOMT_Comment;
import com.mgz.afp.goca.GAD_DrawingOrder.GCPARC_PartialArcAtCurrentPosition;
import com.mgz.afp.goca.GAD_DrawingOrder.GCRLINE_RelativeLineAtCurrentPosition;
import com.mgz.afp.goca.GAD_DrawingOrder.GDPT_DeletePattern;
import com.mgz.afp.goca.GAD_DrawingOrder.GEAR_EndArea;
import com.mgz.afp.goca.GAD_DrawingOrder.GECP_EndCustomPattern;
import com.mgz.afp.goca.GAD_DrawingOrder.GEIMG_EndImage;
import com.mgz.afp.goca.GAD_DrawingOrder.GEPROL_EndProlog;
import com.mgz.afp.goca.GAD_DrawingOrder.GESEG_EndSegment;
import com.mgz.afp.goca.GAD_DrawingOrder.GEXO_ExtendedOrder;
import com.mgz.afp.goca.GAD_DrawingOrder.GFARC_FullArcAtGivenPosition;
import com.mgz.afp.goca.GAD_DrawingOrder.GFLT_FilletAtGivenPosition;
import com.mgz.afp.goca.GAD_DrawingOrder.GIMD_ImageData;
import com.mgz.afp.goca.GAD_DrawingOrder.GLGD_LinearGradient;
import com.mgz.afp.goca.GAD_DrawingOrder.GLINE_LineAtGivenPosition;
import com.mgz.afp.goca.GAD_DrawingOrder.GMRK_MarkerAtGivenPosition;
import com.mgz.afp.goca.GAD_DrawingOrder.GNOP1_NopOperation;
import com.mgz.afp.goca.GAD_DrawingOrder.GPARC_PartialArcAtGivenPosition;
import com.mgz.afp.goca.GAD_DrawingOrder.GRGD_RadialGradient;
import com.mgz.afp.goca.GAD_DrawingOrder.GRLINE_RelativeLineAtGivenPosition;
import com.mgz.afp.goca.GAD_DrawingOrder.GSAP_SetArcParameters;
import com.mgz.afp.goca.GAD_DrawingOrder.GSBMX_SetBackgroundMix;
import com.mgz.afp.goca.GAD_DrawingOrder.GSCA_SetCharacterAngle;
import com.mgz.afp.goca.GAD_DrawingOrder.GSCC_SetCharacterCell;
import com.mgz.afp.goca.GAD_DrawingOrder.GSCD_SetCharacterDirection;
import com.mgz.afp.goca.GAD_DrawingOrder.GSCH_SetCharacterShear;
import com.mgz.afp.goca.GAD_DrawingOrder.GSCLT_SetCustomLineType;
import com.mgz.afp.goca.GAD_DrawingOrder.GSCOL_SetColor;
import com.mgz.afp.goca.GAD_DrawingOrder.GSCP_SetCurrentPosition;
import com.mgz.afp.goca.GAD_DrawingOrder.GSCR_SetCharacterPrecision;
import com.mgz.afp.goca.GAD_DrawingOrder.GSCS_SetCharacterSet;
import com.mgz.afp.goca.GAD_DrawingOrder.GSECOL_SetExtendedColor;
import com.mgz.afp.goca.GAD_DrawingOrder.GSFLW_SetFractionLineWidth;
import com.mgz.afp.goca.GAD_DrawingOrder.GSGCH_SegmentCharacteristics;
import com.mgz.afp.goca.GAD_DrawingOrder.GSLE_SetLineEnd;
import com.mgz.afp.goca.GAD_DrawingOrder.GSLJ_SetLineJoin;
import com.mgz.afp.goca.GAD_DrawingOrder.GSLT_SetLineType;
import com.mgz.afp.goca.GAD_DrawingOrder.GSLW_SetLineWidth;
import com.mgz.afp.goca.GAD_DrawingOrder.GSMC_SetMarkerCell;
import com.mgz.afp.goca.GAD_DrawingOrder.GSMT_SetMarkerSymbol;
import com.mgz.afp.goca.GAD_DrawingOrder.GSMX_SetMix;
import com.mgz.afp.goca.GAD_DrawingOrder.GSMP_SetMarkerPrecision;
import com.mgz.afp.goca.GAD_DrawingOrder.GSMS_SetMarkerSet;
import com.mgz.afp.goca.GAD_DrawingOrder.GSPIK_SetPickIdentifier;
import com.mgz.afp.goca.GAD_DrawingOrder.GSPCOL_SetProcessColor;
import com.mgz.afp.goca.GAD_DrawingOrder.GSPRP_SetPatternReferencePoint;
import com.mgz.afp.goca.GAD_DrawingOrder.GSPS_SetPatternSet;
import com.mgz.afp.goca.GAD_DrawingOrder.GSPT_SetPatternSymbol;
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

      short drawingOrderCode = (short) (sfData[offset + pos] & 0xFF);
      drawingOrder = DrawingOrderPool.acquire(drawingOrderCode);

      switch (drawingOrderCode) {
        case 0x00: {
          if (drawingOrder == null) drawingOrder = new GNOP1_NopOperation();
          dotLength = 1;
        }
        break;
        case 0x01: {
          if (drawingOrder == null) drawingOrder = new GCOMT_Comment();
          dotLength = (sfData[offset + pos + 1] & 0xFF) + 2;
        }
        break;
        case 0x04: {
          if (drawingOrder == null) drawingOrder = new GSGCH_SegmentCharacteristics();
          dotLength = (sfData[offset + pos + 1] & 0xFF) + 2;
        }
        break;
        case 0x08: {
          if (drawingOrder == null) drawingOrder = new GSPS_SetPatternSet();
          dotLength = 2;
        }
        break;
        case 0x0a: {
          if (drawingOrder == null) drawingOrder = new GSCOL_SetColor();
          dotLength = 2;
        }
        break;
        case 0x0c: {
          if (drawingOrder == null) drawingOrder = new GSMX_SetMix();
          dotLength = 2;
        }
        break;
        case 0x0d: {
          if (drawingOrder == null) drawingOrder = new GSBMX_SetBackgroundMix();
          dotLength = 2;
        }
        break;
        case 0x11: {
          if (drawingOrder == null) drawingOrder = new GSFLW_SetFractionLineWidth();
          dotLength = (sfData[offset + pos + 1] & 0xFF) + 2;
        }
        break;
        case 0x18: {
          if (drawingOrder == null) drawingOrder = new GSLT_SetLineType();
          dotLength = 2;
        }
        break;
        case 0x19: {
          if (drawingOrder == null) drawingOrder = new GSLW_SetLineWidth();
          dotLength = 2;
        }
        break;
        case 0x1a: {
          if (drawingOrder == null) drawingOrder = new GSLE_SetLineEnd();
          dotLength = 2;
        }
        break;
        case 0x1b: {
          if (drawingOrder == null) drawingOrder = new GSLJ_SetLineJoin();
          dotLength = 2;
        }
        break;
        case 0x20: {
          if (drawingOrder == null) drawingOrder = new GSCLT_SetCustomLineType();
          dotLength = (sfData[offset + pos + 1] & 0xFF) + 2;
        }
        break;
        case 0x21: {
          if (drawingOrder == null) drawingOrder = new GSCP_SetCurrentPosition();
          dotLength = (sfData[offset + pos + 1] & 0xFF) + 2;
        }
        break;
        case 0x22: {
          if (drawingOrder == null) drawingOrder = new GSAP_SetArcParameters();
          dotLength = (sfData[offset + pos + 1] & 0xFF) + 2;
        }
        break;
        case 0x26: {
          if (drawingOrder == null) drawingOrder = new GSECOL_SetExtendedColor();
          dotLength = (sfData[offset + pos + 1] & 0xFF) + 2;
        }
        break;
        case 0x28: {
          if (drawingOrder == null) drawingOrder = new GSPT_SetPatternSymbol();
          dotLength = 2;
        }
        break;
        case 0x29: {
          if (drawingOrder == null) drawingOrder = new GSMT_SetMarkerSymbol();
          dotLength = 2;
        }
        break;
        case 0x33: {
          if (drawingOrder == null) drawingOrder = new GSCC_SetCharacterCell();
          dotLength = (sfData[offset + pos + 1] & 0xFF) + 2;
        }
        break;
        case 0x34: {
          if (drawingOrder == null) drawingOrder = new GSCA_SetCharacterAngle();
          dotLength = (sfData[offset + pos + 1] & 0xFF) + 2;
        }
        break;
        case 0x35: {
          if (drawingOrder == null) drawingOrder = new GSCH_SetCharacterShear();
          dotLength = (sfData[offset + pos + 1] & 0xFF) + 2;
        }
        break;
        case 0x37: {
          if (drawingOrder == null) drawingOrder = new GSMC_SetMarkerCell();
          dotLength = (sfData[offset + pos + 1] & 0xFF) + 2;
        }
        break;
        case 0x38: {
          if (drawingOrder == null) drawingOrder = new GSCS_SetCharacterSet();
          dotLength = 2;
        }
        break;
        case 0x39: {
          if (drawingOrder == null) drawingOrder = new GSCR_SetCharacterPrecision();
          dotLength = 2;
        }
        break;
        case 0x3a: {
          if (drawingOrder == null) drawingOrder = new GSCD_SetCharacterDirection();
          dotLength = 2;
        }
        break;
        case 0x3b: {
          if (drawingOrder == null) drawingOrder = new GSMP_SetMarkerPrecision();
          dotLength = 2;
        }
        break;
        case 0x3c: {
          if (drawingOrder == null) drawingOrder = new GSMS_SetMarkerSet();
          dotLength = 2;
        }
        break;
        case 0x3e: {
          if (drawingOrder == null) drawingOrder = new GEPROL_EndProlog();
          dotLength = 2;
        }
        break;
        case 0x43: {
          if (drawingOrder == null) drawingOrder = new GSPIK_SetPickIdentifier();
          dotLength = 2;
        }
        break;
        case 0x5e: {
          if (drawingOrder == null) drawingOrder = new GECP_EndCustomPattern();
          dotLength = 2;
        }
        break;
        case 0x60: {
          if (drawingOrder == null) drawingOrder = new GEAR_EndArea();
          dotLength = (sfData[offset + pos + 1] & 0xFF) + 2;
        }
        break;
        case 0x68: {
          if (drawingOrder == null) drawingOrder = new GBAR_BeginArea();
          dotLength = 2;
        }
        break;
        case 0x70: {
          if (drawingOrder == null) drawingOrder = new GBSEG_BeginSegment();
          int segDataLen = UtilBinaryDecoding.parseInt(sfData, offset + pos + 8, 2);
          dotLength = 14 + segDataLen;
        }
        break;
        case 0x71: {
          if (drawingOrder == null) drawingOrder = new GESEG_EndSegment();
          dotLength = 2;
        }
        break;
        case 0x80: {
          if (drawingOrder == null) drawingOrder = new GCBOX_BoxAtCurrentPosition();
          dotLength = (sfData[offset + pos + 1] & 0xFF) + 2;
        }
        break;
        case 0x81: {
          if (drawingOrder == null) drawingOrder = new GCLINE_LineAtCurrentPosition();
          dotLength = (sfData[offset + pos + 1] & 0xFF) + 2;
        }
        break;
        case 0x82: {
          if (drawingOrder == null) drawingOrder = new GCMRK_MarkerAtCurrentPosition();
          dotLength = (sfData[offset + pos + 1] & 0xFF) + 2;
        }
        break;
        case 0x83: {
          if (drawingOrder == null) drawingOrder = new GCCHST_CharacterStringAtCurrentPosition();
          dotLength = (sfData[offset + pos + 1] & 0xFF) + 2;
        }
        break;
        case 0x85: {
          if (drawingOrder == null) drawingOrder = new GCFLT_FilletAtCurrentPosition();
          dotLength = (sfData[offset + pos + 1] & 0xFF) + 2;
        }
        break;
        case 0x87: {
          if (drawingOrder == null) drawingOrder = new GCFARC_FullArcAtCurrentPosition();
          dotLength = (sfData[offset + pos + 1] & 0xFF) + 2;
        }
        break;
        case 0x91: {
          if (drawingOrder == null) drawingOrder = new GCBIMG_BeginImageAtCurrentPosition();
          dotLength = (sfData[offset + pos + 1] & 0xFF) + 2;
        }
        break;
        case 0x92: {
          if (drawingOrder == null) drawingOrder = new GIMD_ImageData();
          dotLength = (sfData[offset + pos + 1] & 0xFF) + 2;
        }
        break;
        case 0x93: {
          if (drawingOrder == null) drawingOrder = new GEIMG_EndImage();
          dotLength = (sfData[offset + pos + 1] & 0xFF) + 2;
        }
        break;
        case 0xa0: {
          if (drawingOrder == null) drawingOrder = new GSPRP_SetPatternReferencePoint();
          dotLength = (sfData[offset + pos + 1] & 0xFF) + 2;
        }
        break;
        case 0xa1: {
          if (drawingOrder == null) drawingOrder = new GCRLINE_RelativeLineAtCurrentPosition();
          dotLength = (sfData[offset + pos + 1] & 0xFF) + 2;
        }
        break;
        case 0xa3: {
          if (drawingOrder == null) drawingOrder = new GCPARC_PartialArcAtCurrentPosition();
          dotLength = (sfData[offset + pos + 1] & 0xFF) + 2;
        }
        break;
        case 0xa5: {
          if (drawingOrder == null) drawingOrder = new GCCBEZ_CubicBezierCurveAtCurrentPosition();
          dotLength = (sfData[offset + pos + 1] & 0xFF) + 2;
        }
        break;
        case 0xb2: {
          if (drawingOrder == null) drawingOrder = new GSPCOL_SetProcessColor();
          dotLength = (sfData[offset + pos + 1] & 0xFF) + 2;
        }
        break;
        case 0xc0: {
          if (drawingOrder == null) drawingOrder = new GBOX_BoxAtGivenPosition();
          dotLength = (sfData[offset + pos + 1] & 0xFF) + 2;
        }
        break;
        case 0xc1: {
          if (drawingOrder == null) drawingOrder = new GLINE_LineAtGivenPosition();
          dotLength = (sfData[offset + pos + 1] & 0xFF) + 2;
        }
        break;
        case 0xc2: {
          if (drawingOrder == null) drawingOrder = new GMRK_MarkerAtGivenPosition();
          dotLength = (sfData[offset + pos + 1] & 0xFF) + 2;
        }
        break;
        case 0xc3: {
          if (drawingOrder == null) drawingOrder = new GCHST_CharacterStringAtGivenPosition();
          dotLength = (sfData[offset + pos + 1] & 0xFF) + 2;
        }
        break;
        case 0xc5: {
          if (drawingOrder == null) drawingOrder = new GFLT_FilletAtGivenPosition();
          dotLength = (sfData[offset + pos + 1] & 0xFF) + 2;
        }
        break;
        case 0xc7: {
          if (drawingOrder == null) drawingOrder = new GFARC_FullArcAtGivenPosition();
          dotLength = (sfData[offset + pos + 1] & 0xFF) + 2;
        }
        break;
        case 0xd1: {
          if (drawingOrder == null) drawingOrder = new GBIMG_BeginImageAtGivenPosition();
          dotLength = (sfData[offset + pos + 1] & 0xFF) + 2;
        }
        break;
        case 0xe1: {
          if (drawingOrder == null) drawingOrder = new GRLINE_RelativeLineAtGivenPosition();
          dotLength = (sfData[offset + pos + 1] & 0xFF) + 2;
        }
        break;
        case 0xe3: {
          if (drawingOrder == null) drawingOrder = new GPARC_PartialArcAtGivenPosition();
          dotLength = (sfData[offset + pos + 1] & 0xFF) + 2;
        }
        break;
        case 0xde: {
          if (drawingOrder == null) drawingOrder = new GBCP_BeginCustomPattern();
          dotLength = (sfData[offset + pos + 1] & 0xFF) + 2;
        }
        break;
        case 0xdf: {
          if (drawingOrder == null) drawingOrder = new GDPT_DeletePattern();
          dotLength = (sfData[offset + pos + 1] & 0xFF) + 2;
        }
        break;
        case 0xe5: {
          if (drawingOrder == null) drawingOrder = new GCBEZ_CubicBezierCurveAtGivenPosition();
          dotLength = (sfData[offset + pos + 1] & 0xFF) + 2;
        }
        break;
        case 0xfe: {
          short qualifier = (short) (sfData[offset + pos + 1] & 0xFF);
          if (drawingOrder != null) {
            DrawingOrderPool.release(drawingOrder); // Return the generic 0xFE order
          }
          drawingOrder = DrawingOrderPool.acquire(drawingOrderCode, qualifier);

          if (qualifier == 0xDC) {
            if (drawingOrder == null) drawingOrder = new GLGD_LinearGradient();
          } else if (qualifier == 0xDD) {
            if (drawingOrder == null) drawingOrder = new GRGD_RadialGradient();
          } else {
            if (drawingOrder == null) drawingOrder = new GEXO_ExtendedOrder();
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

  @Override
  public void release() {
    if (drawingOrders != null) {
      for (GAD_DrawingOrder order : drawingOrders) {
        order.release();
      }
      drawingOrders = null;
    }
    super.release();
  }

}

