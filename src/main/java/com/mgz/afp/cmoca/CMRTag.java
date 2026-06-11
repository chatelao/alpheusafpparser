/*
Copyright 2024 Rudolf Fiala

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

package com.mgz.afp.cmoca;

import com.mgz.afp.exceptions.AFPParserException;
import com.mgz.util.UtilBinaryDecoding;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a CMOCA CMR Data Tag as defined in CMOCA Reference, Chapter 5.
 */
public class CMRTag {
  private final int tagId;
  private final int fieldType;
  private final long count;
  private final long valueOffset;
  private byte[] data;

  public CMRTag(int tagId, int fieldType, long count, long valueOffset) {
    this.tagId = tagId;
    this.fieldType = fieldType;
    this.count = count;
    this.valueOffset = valueOffset;
  }

  public int getTagId() {
    return tagId;
  }

  public int getFieldType() {
    return fieldType;
  }

  public long getCount() {
    return count;
  }

  public long getValueOffset() {
    return valueOffset;
  }

  public byte[] getData() {
    return data;
  }

  public void setData(byte[] data) {
    this.data = data;
  }

  /**
   * Parses the CMRData into a list of CMRTags.
   *
   * @param cmrData the raw CMR data starting at byte 164 of the CMR resource.
   * @return a list of parsed tags.
   * @throws AFPParserException if parsing fails.
   */
  public static List<CMRTag> parseTags(byte[] cmrData) throws AFPParserException {
    List<CMRTag> tags = new ArrayList<>();
    if (cmrData == null || cmrData.length < 12) {
      return tags;
    }

    int offset = 0;
    int lastTagId = -1;
    while (offset + 12 <= cmrData.length) {
      int tagId = UtilBinaryDecoding.parseInt(cmrData, offset, 2);
      // [CMOCA-5-018] The tags in a CMR must be specified in increasing order by their TagIDs.
      if (tagId <= lastTagId && tagId != 0xFFFF) {
        throw new AFPParserException(String.format("EC-%04X0F: CMR tags must be in increasing order. Found 0x%04X after 0x%04X", tagId, tagId, lastTagId));
      }

      int reserved = cmrData[offset + 2] & 0xFF;
      // [CMOCA-5-004] Reserved X'00' Should be set to zero
      if (reserved != 0) {
        // Ignored or logged
      }

      int fieldType = cmrData[offset + 3] & 0xFF;
      long count = UtilBinaryDecoding.parseLong(cmrData, offset + 4, 4);
      long valueOffset = UtilBinaryDecoding.parseLong(cmrData, offset + 8, 4);

      CMRTag tag = new CMRTag(tagId, fieldType, count, valueOffset);
      tags.add(tag);

      lastTagId = tagId;
      offset += 12;
      if (tagId == 0xFFFF) {
        break;
      }
    }

    // Now resolve data for each tag
    int currentTagOffset = 0;
    for (CMRTag tag : tags) {
      if (tag.getTagId() == 0xFFFF) {
        currentTagOffset += 12;
        continue;
      }

      int dataLength = (int) (tag.getCount() * getFieldTypeSize(tag.getFieldType()));
      if (dataLength > 0) {
        byte[] tagData = new byte[dataLength];
        if (dataLength <= 4) {
          // Data is inline in ValueOffset field, left-aligned
          System.arraycopy(cmrData, currentTagOffset + 8, tagData, 0, dataLength);
        } else {
          // Data is at valueOffset
          // [CMOCA-5-028] EC-xxxx10 Invalid Value: offset causes data to be outside CMR (CMRData field)
          if (tag.getValueOffset() + dataLength > cmrData.length) {
            throw new AFPParserException(String.format("EC-%04X10: Tag 0x%04X data offset %d with length %d is outside CMRData (total length %d)",
                tag.getTagId(), tag.getTagId(), tag.getValueOffset(), dataLength, cmrData.length));
          }
          System.arraycopy(cmrData, (int) tag.getValueOffset(), tagData, 0, dataLength);
        }
        tag.setData(tagData);
      }
      tag.validate(tags);
      currentTagOffset += 12;
    }

    return tags;
  }

  private int getNumberOfComponents(List<CMRTag> allTags) {
    for (CMRTag tag : allTags) {
      if (tag.getTagId() == 0x0011 && tag.getData() != null && tag.getData().length > 0) {
        return tag.getData()[0] & 0xFF;
      }
    }
    return 1; // Default [CMOCA-5-087]
  }

  private int[] getIntArrayFromTag(List<CMRTag> allTags, int targetTagId, int numComponents) {
    for (CMRTag tag : allTags) {
      if (tag.getTagId() == targetTagId && tag.getData() != null) {
        int[] values = new int[numComponents];
        int fieldSize = getFieldTypeSize(tag.getFieldType());
        for (int i = 0; i < numComponents && (i + 1) * fieldSize <= tag.getData().length; i++) {
          try {
            values[i] = (int) UtilBinaryDecoding.parseLong(tag.getData(), i * fieldSize, fieldSize);
          } catch (AFPParserException e) {
            // ignore
          }
        }
        return values;
      }
    }
    return null;
  }

  private static int getFieldTypeSize(int fieldType) {
    switch (fieldType) {
      case 0x01: // 1-byte UBIN
      case 0x05: // BYTE
      case 0x06: // ASCII
      case 0x08: // CODE
      case 0x09: // BITS
        return 1;
      case 0x02: // 2-byte UBIN
      case 0x07: // UTF16
        return 2;
      case 0x04: // 4-byte UBIN
        return 4;
      default:
        return 1;
    }
  }

  public void validate(List<CMRTag> allTags) throws AFPParserException {
    int numComponents = -1; // Lazy load if needed

    switch (tagId) {
      case 0x0004: // Comment [CMOCA-5-035]
        // [CMOCA-5-039] EC-000406 Invalid Field Type
        if (fieldType != 0x06 && fieldType != 0x07) {
          throw new AFPParserException(String.format("EC-000406: Invalid Field Type 0x%02X for Comment tag", fieldType));
        }
        break;

      case 0x0008: // Date and Time Stamp [CMOCA-5-042]
        // [CMOCA-5-065] EC-000805 Invalid Count Value
        if (count != 10) {
          throw new AFPParserException(String.format("EC-000805: Invalid Count %d for Date/Time tag", count));
        }
        // [CMOCA-5-066] EC-000806 Invalid Field Type
        if (fieldType != 0x05) {
          throw new AFPParserException(String.format("EC-000806: Invalid Field Type 0x%02X for Date/Time tag", fieldType));
        }
        break;

      case 0x0011: // Number of Components [CMOCA-5-069]
        // [CMOCA-5-088] EC-001105 Invalid Count Value
        if (count != 1) {
          throw new AFPParserException(String.format("EC-001105: Invalid Count %d for Number of Components tag", count));
        }
        // [CMOCA-5-089] EC-001106 Invalid Field Type
        if (fieldType != 0x01) {
          throw new AFPParserException(String.format("EC-001106: Invalid Field Type 0x%02X for Number of Components tag", fieldType));
        }
        // [CMOCA-5-091] EC-001110 Invalid Value: number of components is zero or greater than 15.
        if (data != null && data.length > 0) {
          int components = data[0] & 0xFF;
          if (components == 0 || components > 15) {
            throw new AFPParserException(String.format("EC-001110: Invalid Number of Components value %d (expected 1-15)", components));
          }
        }
        break;

      case 0x5011: // Indexed Subset [CMOCA-5-265]
        if (count != 1) {
          throw new AFPParserException(String.format("EC-501105: Invalid Count %d for Indexed Subset tag", count));
        }
        if (fieldType != 0x08) {
          throw new AFPParserException(String.format("EC-501106: Invalid Field Type 0x%02X for Indexed Subset tag", fieldType));
        }
        // [CMOCA-5-268] EC-501110 Invalid Value
        if (data != null && data.length > 0) {
          int subset = data[0] & 0xFF;
          if (subset != 0x01) {
            throw new AFPParserException(String.format("EC-501110: Invalid Indexed Subset value 0x%02X (expected 0x01)", subset));
          }
        }
        break;

      case 0x4011: // Link Color Conversion Subset [CMOCA-5-221]
        if (count != 1) {
          throw new AFPParserException(String.format("EC-401105: Invalid Count %d for Link Color Conversion Subset tag", count));
        }
        if (fieldType != 0x08) {
          throw new AFPParserException(String.format("EC-401106: Invalid Field Type 0x%02X for Link Color Conversion Subset tag", fieldType));
        }
        // [CMOCA-5-224..226] EC-401110 Invalid Value
        if (data != null && data.length > 0) {
          int subset = data[0] & 0xFF;
          if (subset < 0x01 || subset > 0x03) {
            throw new AFPParserException(String.format("EC-401110: Invalid Link Color Conversion Subset value 0x%02X (expected 0x01 to 0x03)", subset));
          }
        }
        break;

      case 0x2004: // Tone Transfer Curve Subset [CMOCA-5-168]
        if (count != 1) {
          throw new AFPParserException(String.format("EC-200405: Invalid Count %d for Tone Transfer Curve Subset tag", count));
        }
        if (fieldType != 0x08) {
          throw new AFPParserException(String.format("EC-200406: Invalid Field Type 0x%02X for Tone Transfer Curve Subset tag", fieldType));
        }
        // [CMOCA-5-171/172] EC-200410 Invalid Value
        if (data != null && data.length > 0) {
          int subset = data[0] & 0xFF;
          if (subset != 0x01 && subset != 0x02) {
            throw new AFPParserException(String.format("EC-200410: Invalid Tone Transfer Curve Subset value 0x%02X (expected 0x01 or 0x02)", subset));
          }
        }
        break;

      case 0x3011: // ICC Profile Subset [CMOCA-5-184]
        if (count != 1) {
          throw new AFPParserException(String.format("EC-301105: Invalid Count %d for ICC Profile Subset tag", count));
        }
        if (fieldType != 0x08) {
          throw new AFPParserException(String.format("EC-301106: Invalid Field Type 0x%02X for ICC Profile Subset tag", fieldType));
        }
        // [CMOCA-5-187..196] EC-301110 Invalid Value
        if (data != null && data.length > 0) {
          int subset = data[0] & 0xFF;
          if (subset < 0x01 || subset > 0x0A) { // 0x01 to 0x0A (Retired item 3 is 0x0A)
            throw new AFPParserException(String.format("EC-301110: Invalid ICC Profile Subset value 0x%02X (expected 0x01 to 0x0A)", subset));
          }
        }
        break;

      case 0x1011: // Halftone Subset [CMOCA-5-092]
        if (count != 1) {
          throw new AFPParserException(String.format("EC-101105: Invalid Count %d for Halftone Subset tag", count));
        }
        if (fieldType != 0x08) {
          throw new AFPParserException(String.format("EC-101106: Invalid Field Type 0x%02X for Halftone Subset tag", fieldType));
        }
        // [CMOCA-5-100] EC-101110 Invalid Value
        if (data != null && data.length > 0) {
          int subset = data[0] & 0xFF;
          if (subset < 0x01 || subset > 0x04) {
            throw new AFPParserException(String.format("EC-101110: Invalid Halftone Subset value 0x%02X", subset));
          }
        }
        break;

      case 0x1030: // Max Image Value [CMOCA-5-109]
        // [CMOCA-5-110] Field Type: X'01', X'02', or X'04'
        if (fieldType != 0x01 && fieldType != 0x02 && fieldType != 0x04) {
          throw new AFPParserException(String.format("EC-103006: Invalid Field Type 0x%02X for Max Image Value tag", fieldType));
        }
        // [CMOCA-5-111] Count: Number of color components
        numComponents = getNumberOfComponents(allTags);
        if (count != numComponents) {
          throw new AFPParserException(String.format("EC-103005: Invalid Count %d for Max Image Value tag (expected %d)", count, numComponents));
        }
        break;

      case 0x1035: // Number of Device Levels [CMOCA-5-113]
        // [CMOCA-5-114] Field Type: X'01'
        if (fieldType != 0x01) {
          throw new AFPParserException(String.format("EC-103506: Invalid Field Type 0x%02X for Number of Device Levels tag", fieldType));
        }
        // [CMOCA-5-115] Count: Number of color components
        numComponents = getNumberOfComponents(allTags);
        if (count != numComponents) {
          throw new AFPParserException(String.format("EC-103505: Invalid Count %d for Number of Device Levels tag (expected %d)", count, numComponents));
        }
        break;

      case 0x1060: // Location of Current Pixel [CMOCA-5-135]
        // [CMOCA-5-136] Field Type: X'01' (1-byte UBIN)
        if (fieldType != 0x01) {
          throw new AFPParserException(String.format("EC-106006: Invalid Field Type 0x%02X for Location of Current Pixel tag", fieldType));
        }
        // [CMOCA-5-137] Count: 2 * number of color components
        numComponents = getNumberOfComponents(allTags);
        if (count != 2L * numComponents) {
          throw new AFPParserException(String.format("EC-106005: Invalid Count %d for Location of Current Pixel tag (expected %d)", count, 2 * numComponents));
        }
        break;

      case 0x1075: // Threshold Value [CMOCA-5-152]
        // [CMOCA-5-153] Field Type: X'01', X'02', or X'04'
        if (fieldType != 0x01 && fieldType != 0x02 && fieldType != 0x04) {
          throw new AFPParserException(String.format("EC-107506: Invalid Field Type 0x%02X for Threshold Value tag", fieldType));
        }
        // [CMOCA-5-154] Count: Number of color components
        numComponents = getNumberOfComponents(allTags);
        if (count != numComponents) {
          throw new AFPParserException(String.format("EC-107505: Invalid Count %d for Threshold Value tag (expected %d)", count, numComponents));
        }
        break;

      case 0x1040: // Offset Tiling [CMOCA-5-117]
        // [CMOCA-5-118] Field Type: X'01' or X'02'
        if (fieldType != 0x01 && fieldType != 0x02) {
          throw new AFPParserException(String.format("EC-104006: Invalid Field Type 0x%02X for Offset Tiling tag", fieldType));
        }
        // [CMOCA-5-119] Count: Number of color components
        numComponents = getNumberOfComponents(allTags);
        if (count != numComponents) {
          throw new AFPParserException(String.format("EC-104005: Invalid Count %d for Offset Tiling tag (expected %d)", count, numComponents));
        }
        break;

      case 0x1070: // Boundary Condition [CMOCA-5-145]
        // [CMOCA-5-146] Field Type: X'08' (CODE)
        if (fieldType != 0x08) {
          throw new AFPParserException(String.format("EC-107006: Invalid Field Type 0x%02X for Boundary Condition tag", fieldType));
        }
        // [CMOCA-5-147] Count: Number of color components
        numComponents = getNumberOfComponents(allTags);
        if (count != numComponents) {
          throw new AFPParserException(String.format("EC-107005: Invalid Count %d for Boundary Condition tag (expected %d)", count, numComponents));
        }
        // [CMOCA-5-148..151] X'01' to X'04'
        if (data != null && data.length > 0) {
          int bc = data[0] & 0xFF;
          if (bc < 0x01 || bc > 0x04) {
            throw new AFPParserException(String.format("EC-107010: Invalid Boundary Condition value 0x%02X", bc));
          }
        }
        break;

      case 0x1021: // Array Width [CMOCA-5-101]
      case 0x1025: // Array Height [CMOCA-5-105]
        // [CMOCA-5-102, 106] Field Type: X'01' (1-byte UBIN), X'02' (2-byte UBIN)
        if (fieldType != 0x01 && fieldType != 0x02) {
          throw new AFPParserException(String.format("EC-%04X06: Invalid Field Type 0x%02X for tag 0x%04X", tagId, fieldType, tagId));
        }
        // [CMOCA-5-103, 107] Count: Number of color components
        numComponents = getNumberOfComponents(allTags);
        if (count != numComponents) {
          throw new AFPParserException(String.format("EC-%04X05: Invalid Count %d for tag 0x%04X (expected %d)", tagId, count, tagId, numComponents));
        }
        break;

      case 0x1045: // Bilevel Point-Operation Screen Data [CMOCA-5-122]
      case 0x1055: // Error Diffusion Filter [CMOCA-5-130]
        if (tagId == 0x1045) {
          if (fieldType != 0x01 && fieldType != 0x02 && fieldType != 0x04) {
            throw new AFPParserException(String.format("EC-104506: Invalid Field Type 0x%02X for Bilevel Screen Data tag", fieldType));
          }
        } else { // 0x1055
          if (fieldType != 0x01) {
            throw new AFPParserException(String.format("EC-105506: Invalid Field Type 0x%02X for Error Diffusion Filter tag", fieldType));
          }
        }
        numComponents = getNumberOfComponents(allTags);
        int[] widths = getIntArrayFromTag(allTags, 0x1021, numComponents);
        int[] heights = getIntArrayFromTag(allTags, 0x1025, numComponents);
        if (widths != null && heights != null) {
          long expectedSum = 0;
          for (int i = 0; i < numComponents; i++) {
            expectedSum += (long) widths[i] * heights[i];
          }
          if (count != expectedSum) {
            throw new AFPParserException(String.format("EC-%04X05: Invalid Count %d for tag 0x%04X (expected %d)", tagId, count, tagId, expectedSum));
          }
        }
        break;

      case 0x1050: // Multilevel Point-Operation Screen Data [CMOCA-5-126]
        if (fieldType != 0x01) {
          throw new AFPParserException(String.format("EC-105006: Invalid Field Type 0x%02X for Multilevel Screen Data tag", fieldType));
        }
        numComponents = getNumberOfComponents(allTags);
        int[] w = getIntArrayFromTag(allTags, 0x1021, numComponents);
        int[] h = getIntArrayFromTag(allTags, 0x1025, numComponents);
        int[] m = getIntArrayFromTag(allTags, 0x1030, numComponents);
        if (w != null && h != null && m != null) {
          long expected = 0;
          for (int i = 0; i < numComponents; i++) {
            expected += (long) w[i] * h[i] * (m[i] + 1);
          }
          if (count != expected) {
            throw new AFPParserException(String.format("EC-105005: Invalid Count %d for Multilevel Screen Data tag (expected %d)", count, expected));
          }
        }
        break;

      case 0x1080: // Quantization Boundary Table [CMOCA-5-156]
        if (fieldType != 0x01 && fieldType != 0x02 && fieldType != 0x04) {
          throw new AFPParserException(String.format("EC-108006: Invalid Field Type 0x%02X for Quantization Boundary Table tag", fieldType));
        }
        numComponents = getNumberOfComponents(allTags);
        int[] levels = getIntArrayFromTag(allTags, 0x1035, numComponents);
        if (levels != null) {
          long expected = 0;
          for (int i = 0; i < numComponents; i++) {
            expected += (levels[i] - 1);
          }
          if (count != expected) {
            throw new AFPParserException(String.format("EC-108005: Invalid Count %d for Quantization Boundary Table tag (expected %d)", count, expected));
          }
        }
        break;

      case 0x1065: // Raster Direction [CMOCA-5-139]
        // [CMOCA-5-140] Field Type: X'08' (CODE)
        if (fieldType != 0x08) {
          throw new AFPParserException(String.format("EC-106506: Invalid Field Type 0x%02X for Raster Direction tag", fieldType));
        }
        // [CMOCA-5-141] Count: Number of color components
        numComponents = getNumberOfComponents(allTags);
        if (count != numComponents) {
          throw new AFPParserException(String.format("EC-106505: Invalid Count %d for Raster Direction tag (expected %d)", count, numComponents));
        }
        // [CMOCA-5-142, 143] X'01' Normal raster, X'02' Serpentine raster
        if (data != null && data.length > 0) {
          int dir = data[0] & 0xFF;
          if (dir != 0x01 && dir != 0x02) {
            throw new AFPParserException(String.format("EC-106510: Invalid Raster Direction value 0x%02X", dir));
          }
        }
        break;

      case 0x2011: // TTC Length [CMOCA-5-173]
        // [CMOCA-5-174] Field Type: X'08' (CODE)
        if (fieldType != 0x08) {
          throw new AFPParserException(String.format("EC-201106: Invalid Field Type 0x%02X for TTC Length tag", fieldType));
        }
        // [CMOCA-5-175] Count: Number of color components
        numComponents = getNumberOfComponents(allTags);
        if (count != numComponents) {
          throw new AFPParserException(String.format("EC-201105: Invalid Count %d for TTC Length tag (expected %d)", count, numComponents));
        }
        // [CMOCA-5-176, 177] X'01' or X'02'
        if (data != null && data.length > 0) {
          int ttcLen = data[0] & 0xFF;
          if (ttcLen != 0x01 && ttcLen != 0x02) {
            throw new AFPParserException(String.format("EC-201110: Invalid TTC Length value 0x%02X", ttcLen));
          }
        }
        break;

      case 0x3025: // ICC Profile Filename [CMOCA-5-218]
        // [CMOCA-5-219] Field Type: X'06' (ASCII) or X'07' (UTF16)
        if (fieldType != 0x06 && fieldType != 0x07) {
          throw new AFPParserException(String.format("EC-302506: Invalid Field Type 0x%02X for ICC Profile Filename tag", fieldType));
        }
        break;

      case 0x4015: // Link Audit CMR OID [CMOCA-5-227]
      case 0x4020: // Link Instruction CMR OID [CMOCA-5-230]
        // [CMOCA-5-228, 231] Field Type: X'05' (BYTE)
        if (fieldType != 0x05) {
          throw new AFPParserException(String.format("EC-%04X06: Invalid Field Type 0x%02X for tag 0x%04X", tagId, fieldType, tagId));
        }
        break;

      case 0x4025: // Link Audit CMR Name [CMOCA-5-233]
      case 0x4030: // Link Instruction CMR Name [CMOCA-5-236]
      case 0x4090: // Link CMRE Identifier [CMOCA-5-262]
        if (fieldType != 0x07) { // UTF-16
          throw new AFPParserException(String.format("EC-%04X06: Invalid Field Type 0x%02X for tag 0x%04X (expected 0x07)", tagId, fieldType, tagId));
        }
        break;

      case 0x4035: // Default Rendering Intent [CMOCA-5-239]
        // [CMOCA-5-240] Field Type: X'08' (CODE)
        if (fieldType != 0x08) {
          throw new AFPParserException(String.format("EC-403506: Invalid Field Type 0x%02X for Default Rendering Intent tag", fieldType));
        }
        // [CMOCA-5-241..245] X'00' to X'03'
        if (data != null && data.length > 0) {
          int intent = data[0] & 0xFF;
          if (intent > 0x03) {
            throw new AFPParserException(String.format("EC-403510: Invalid Default Rendering Intent value 0x%02X", intent));
          }
        }
        break;

      case 0x4040: // Link LUT Perceptual [CMOCA-5-246]
      case 0x4045: // Link LUT Media-Relative Colorimetric [CMOCA-5-256]
      case 0x4050: // Link LUT Saturation [CMOCA-5-258]
      case 0x4055: // Link LUT ICC-Absolute Colorimetric [CMOCA-5-260]
        // [CMOCA-5-247, 257, 259, 261] Field Type: X'05' (BYTE)
        if (fieldType != 0x05) {
          throw new AFPParserException(String.format("EC-%04X06: Invalid Field Type 0x%02X for tag 0x%04X", tagId, fieldType, tagId));
        }
        if (data != null && data.length >= 2) {
          // [CMOCA-5-249] Number of components of the input color space (1–15)
          int inputComponents = data[0] & 0xFF;
          if (inputComponents == 0 || inputComponents > 15) {
            throw new AFPParserException(String.format("EC-%04X10: Invalid number of input components %d (expected 1-15)", tagId, inputComponents));
          }
          // [CMOCA-5-250] Number of components of the output color space (1–15)
          int outputComponents = data[1] & 0xFF;
          if (outputComponents == 0 || outputComponents > 15) {
            throw new AFPParserException(String.format("EC-%04X10: Invalid number of output components %d (expected 1-15)", tagId, outputComponents));
          }
        }
        break;

      case 0x5015: // Number of Named Colorants [CMOCA-5-269]
        // [CMOCA-5-270] Field Type: X'01' (1-byte UBIN)
        if (fieldType != 0x01) {
          throw new AFPParserException(String.format("EC-501506: Invalid Field Type 0x%02X for Number of Named Colorants tag", fieldType));
        }
        // [CMOCA-5-271] Count: 1
        if (count != 1) {
          throw new AFPParserException(String.format("EC-501505: Invalid Count %d for Number of Named Colorants tag", count));
        }
        break;

      default:
        // No specific structural validation for other tags yet
        break;
    }
  }

  @Override
  public String toString() {
    return String.format("Tag[ID=0x%04X, Type=0x%02X, Count=%d, Offset=%d]", tagId, fieldType, count, valueOffset);
  }
}
