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

package com.mgz.afp.ioca;

import com.mgz.afp.base.StructuredField;
import com.mgz.afp.exceptions.AFPParserException;
import com.mgz.afp.ioca.IPD_Segment;
import com.mgz.afp.ioca.IPD_Segment.BandImage;
import com.mgz.afp.ioca.IPD_Segment.BandImageData;
import com.mgz.afp.ioca.IPD_Segment.BeginImageContent;
import com.mgz.afp.ioca.IPD_Segment.BeginSegment;
import com.mgz.afp.ioca.IPD_Segment.BeginTile;
import com.mgz.afp.ioca.IPD_Segment.BeginTransparencyMask;
import com.mgz.afp.ioca.IPD_Segment.EndImageContent;
import com.mgz.afp.ioca.IPD_Segment.EndSegment;
import com.mgz.afp.ioca.IPD_Segment.EndTile;
import com.mgz.afp.ioca.IPD_Segment.EndTransparencyMask;
import com.mgz.afp.ioca.IPD_Segment.ExternalAlgorithmSpecification;
import com.mgz.afp.ioca.IPD_Segment.FunctionSetIdentification;
import com.mgz.afp.ioca.IPD_Segment.IDESize;
import com.mgz.afp.ioca.IPD_Segment.IDEStructure;
import com.mgz.afp.ioca.IPD_Segment.IPD_SegmentExtended;
import com.mgz.afp.ioca.IPD_Segment.IPD_SegmentType;
import com.mgz.afp.ioca.IPD_Segment.ImageData;
import com.mgz.afp.ioca.IPD_Segment.ImageEncoding;
import com.mgz.afp.ioca.IPD_Segment.ImageLUTID;
import com.mgz.afp.ioca.IPD_Segment.ImageSize;
import com.mgz.afp.ioca.IPD_Segment.ImageSubsampling;
import com.mgz.afp.ioca.IPD_Segment.IncludeTile;
import com.mgz.afp.ioca.IPD_Segment.SetBilevelImageColor;
import com.mgz.afp.ioca.IPD_Segment.SetExtendedBilevelImageColor;
import com.mgz.afp.ioca.IPD_Segment.TilePosition;
import com.mgz.afp.ioca.IPD_Segment.TileSetColor;
import com.mgz.afp.ioca.IPD_Segment.TileSize;
import com.mgz.afp.ioca.IPD_Segment.TileTOC;
import com.mgz.afp.ioca.IPD_Segment.UnknownSegmentExtended;
import com.mgz.afp.ioca.IPD_Segment.UnknownSegmentLong;
import com.mgz.afp.ioca.IPD_Segment.nColorNames;
import com.mgz.afp.parser.AFPParserConfiguration;
import com.mgz.util.UtilBinaryDecoding;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

public class IPD_ImagePictureData extends StructuredField {
  private List<IPD_Segment> listOfSegments;

  public List<IPD_Segment> getListOfSegments() {
    return listOfSegments;
  }

  public void setListOfSegments(List<IPD_Segment> listOfSegments) {
    this.listOfSegments = listOfSegments;
  }

  @Override
  public void decodeAFP(byte[] sfData, int offset, int length, AFPParserConfiguration config) throws AFPParserException {
    int actualLength = super.getActualLength(sfData, offset, length);

    listOfSegments = new ArrayList<IPD_Segment>();
    int pos = 0;
    while (pos < actualLength) {
      int segmentTypeCode = sfData[offset + pos] & 0xFF;
      if (segmentTypeCode == 0xFE) {
        segmentTypeCode = UtilBinaryDecoding.parseInt(sfData, offset + pos, 2);
      }
      IPD_SegmentType segmentType = IPD_SegmentType.valueOf(segmentTypeCode);

      IPD_Segment ipdSegment = IpdSegmentPool.acquire(segmentType);
      switch (segmentType) {
        case BeginSegment: {
          if (ipdSegment == null) ipdSegment = new BeginSegment();
        }
        break;
        case EndSegment: {
          if (ipdSegment == null) ipdSegment = new EndSegment();
        }
        break;
        case BeginImageContent: {
          if (ipdSegment == null) ipdSegment = new BeginImageContent();
        }
        break;
        case EndImageContent: {
          if (ipdSegment == null) ipdSegment = new EndImageContent();
        }
        break;
        case ImageSize: {
          if (ipdSegment == null) ipdSegment = new ImageSize();
        }
        break;
        case ImageEncoding: {
          if (ipdSegment == null) ipdSegment = new ImageEncoding();
        }
        break;
        case IDESize: {
          if (ipdSegment == null) ipdSegment = new IDESize();
        }
        break;
        case ImageLUTID: {
          if (ipdSegment == null) ipdSegment = new ImageLUTID();
        }
        break;
        case BandImage: {
          if (ipdSegment == null) ipdSegment = new BandImage();
        }
        break;
        case IDEStructure: {
          if (ipdSegment == null) ipdSegment = new IDEStructure();
        }
        break;
        case ExternalAlgorithmSpecification: {
          if (ipdSegment == null) ipdSegment = new ExternalAlgorithmSpecification();
        }
        break;
        case ImageSubsampling: {
          if (ipdSegment == null) ipdSegment = new ImageSubsampling();
        }
        break;
        case BeginTile: {
          if (ipdSegment == null) ipdSegment = new BeginTile();
        }
        break;
        case EndTile: {
          if (ipdSegment == null) ipdSegment = new EndTile();
        }
        break;
        case TilePosition: {
          if (ipdSegment == null) ipdSegment = new TilePosition();
        }
        break;
        case TileSize: {
          if (ipdSegment == null) ipdSegment = new TileSize();
        }
        break;
        case TileSetColor: {
          if (ipdSegment == null) ipdSegment = new TileSetColor();
        }
        break;
        case SetExtendedBilevelImageColor: {
          if (ipdSegment == null) ipdSegment = new SetExtendedBilevelImageColor();
        }
        break;
        case SetBilevelImageColor: {
          if (ipdSegment == null) ipdSegment = new SetBilevelImageColor();
        }
        break;
        case FunctionSetIdentification: {
          if (ipdSegment == null) ipdSegment = new FunctionSetIdentification();
        }
        break;
        case IncludeTile: {
          if (ipdSegment == null) ipdSegment = new IncludeTile();
        }
        break;
        case TileTOC: {
          if (ipdSegment == null) ipdSegment = new TileTOC();
        }
        break;
        case BeginTransparencyMask: {
          if (ipdSegment == null) ipdSegment = new BeginTransparencyMask();
        }
        break;
        case EndTransparencyMask: {
          if (ipdSegment == null) ipdSegment = new EndTransparencyMask();
        }
        break;
        case ImageData: {
          if (ipdSegment == null) ipdSegment = new ImageData();
        }
        break;
        case BandImageData: {
          if (ipdSegment == null) ipdSegment = new BandImageData();
        }
        break;
        case nColorNames: {
          if (ipdSegment == null) ipdSegment = new nColorNames();
        }
        break;
        case UnknownIPDSegmentLong: {
          if (ipdSegment == null) ipdSegment = new UnknownSegmentLong();
        }
        break;
        case UnknownIPDSegmentExtended: {
          if (ipdSegment == null) ipdSegment = new UnknownSegmentExtended();
        }
        break;
      }

      ipdSegment.decodeAFP(sfData, offset + pos, actualLength - pos, config);
      listOfSegments.add(ipdSegment);

      if (ipdSegment instanceof IPD_SegmentExtended) {
        pos += (4 + ipdSegment.lengthOfFollowingData);
      } else {
        pos += (2 + ipdSegment.lengthOfFollowingData);
      }
    }
  }

  @Override
  public void release() {
    if (listOfSegments != null) {
      for (IPD_Segment segment : listOfSegments) {
        segment.release();
      }
      listOfSegments = null;
    }
    super.release();
  }

  @Override
  public void writeAFP(OutputStream os, AFPParserConfiguration config) throws IOException {
    if (listOfSegments != null && !listOfSegments.isEmpty()) {
      ByteArrayOutputStream baos = new ByteArrayOutputStream();
      for (IPD_Segment segment : listOfSegments) {
        segment.writeAFP(baos, config);
      }
      writeFullStructuredField(os, baos.toByteArray());
    } else {
      writeFullStructuredField(os, null);
    }
  }
}