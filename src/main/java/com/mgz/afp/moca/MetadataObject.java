/*
Copyright 2024 Alpheus AFP Parser Authors

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

package com.mgz.afp.moca;

import com.mgz.afp.base.annotations.AFPField;
import com.mgz.afp.base.annotations.AFPType;
import com.mgz.afp.exceptions.AFPParserException;
import com.mgz.afp.parser.AFPParserConfiguration;
import com.mgz.afp.parser.TripletParser;
import com.mgz.util.UtilBinaryDecoding;
import com.mgz.util.UtilCharacterEncoding;
import java.io.ByteArrayInputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;
import javax.xml.parsers.DocumentBuilderFactory;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

/**
 * MOCA Chapter 4. Metadata Object (MO)
 */
@AFPType
public class MetadataObject {

  @AFPField
  private long moLength;

  @AFPField
  private int headerLength;

  @AFPField(serializedSize = 6)
  private String moType;

  @AFPField(serializedSize = 8)
  private String moFormat;

  @AFPField(serializedSize = 20)
  private String moCompression;

  @AFPField
  private int moNameLength;

  @AFPField
  private String moName;

  @AFPField
  private byte[] moData;

  public void decode(byte[] data) throws AFPParserException {
    decode(data, new AFPParserConfiguration());
  }

  public void decode(byte[] data, AFPParserConfiguration config) throws AFPParserException {
    if (data == null || data.length < 50) {
      // [MOCA-4-020] [MOCA-4-030]
      throw new AFPParserException("EC-0100 Invalid Length Value: Metadata Object data is too short, minimum length is 50 bytes.");
    }
    moLength = UtilBinaryDecoding.parseLong(data, 0, 4);
    if (moLength < 50 || moLength > data.length) {
      // [MOCA-4-020] [MOCA-4-030]
      throw new AFPParserException("EC-0100 Invalid Length Value: MOLength " + moLength + " is invalid.");
    }

    headerLength = UtilBinaryDecoding.parseInt(data, 4, 2);
    if (headerLength < 46) {
      // [MOCA-4-021] [MOCA-4-031]
      throw new AFPParserException("EC-0200 Invalid Field Value: HeaderLength " + headerLength + " is invalid, must be at least 46.");
    }

    moType = new String(data, 6, 6, StandardCharsets.UTF_16BE);
    String type = trimMoca(moType);
    if (!"DES".equals(type)) {
      // [MOCA-4-022] [MOCA-4-033]
      throw new AFPParserException("EC-0220 Invalid or Unsupported Field Value: MOType '" + type + "' is invalid or unsupported.");
    }

    moFormat = new String(data, 12, 8, StandardCharsets.UTF_16BE);
    String format = trimMoca(moFormat);
    if (!"AFPT".equals(format) && !"XMP".equals(format)) {
      // [MOCA-4-023] [MOCA-4-034]
      throw new AFPParserException("EC-0230 Invalid or Unsupported Field Value: MOFormat '" + format + "' is invalid or unsupported.");
    }

    moCompression = new String(data, 20, 20, StandardCharsets.UTF_16BE);
    String compression = trimMoca(moCompression);
    if (!"NONE".equals(compression) && !"GZIP".equals(compression) && !"EXI".equals(compression)) {
      // [MOCA-4-024] [MOCA-4-035]
      throw new AFPParserException("EC-0240 Invalid or Unsupported Field Value: MOCompression '" + compression + "' is invalid or unsupported.");
    }

    // Offset 40-47 Reserved (8 bytes)

    moNameLength = UtilBinaryDecoding.parseInt(data, 48, 2);
    if (moNameLength < 0 || moNameLength > 250 || (moNameLength % 2 != 0)) {
      // [MOCA-4-025] [MOCA-4-036]
      throw new AFPParserException("EC-0250 Invalid Field Value: MONameLength " + moNameLength + " is invalid, must be even and <= 250.");
    }

    int currentOffset = 50;
    if (moNameLength > 0) {
      if (currentOffset + moNameLength > 4 + headerLength) {
        // [MOCA-4-025] [MOCA-4-036]
        throw new AFPParserException("EC-0250 Invalid Field Value: MONameLength " + moNameLength + " exceeds header boundaries.");
      }
      // [MOCA-4-026] [MOCA-4-032] EC-0210: Validating UTF-16BE
      try {
        java.nio.charset.CharsetDecoder decoder = StandardCharsets.UTF_16BE.newDecoder()
            .onMalformedInput(java.nio.charset.CodingErrorAction.REPORT)
            .onUnmappableCharacter(java.nio.charset.CodingErrorAction.REPORT);
        java.nio.ByteBuffer buffer = java.nio.ByteBuffer.wrap(data, currentOffset, moNameLength);
        moName = decoder.decode(buffer).toString();
      } catch (java.nio.charset.CharacterCodingException e) {
        throw new AFPParserException("EC-0210 Invalid Field Value: MOName is not valid UTF-16BE data.");
      }
    }

    int dataStartOffset = 4 + headerLength;
    if (moLength > dataStartOffset) {
      int dataLength = (int) (moLength - dataStartOffset);
      moData = new byte[dataLength];
      System.arraycopy(data, dataStartOffset, moData, 0, dataLength);
    } else {
      moData = new byte[0];
    }

    validateMoData(config);
  }

  private void validateMoData(AFPParserConfiguration config) throws AFPParserException {
    if (moData == null || moData.length == 0) {
      return;
    }

    String format = getMoFormat();
    if ("XMP".equals(format)) {
      try {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        factory.setNamespaceAware(true);
        // Disable external entities to prevent XXE
        factory.setFeature("http://apache.org/xml/features/disallow-doctype-decl", true);
        factory.setFeature("http://xml.org/sax/features/external-general-entities", false);
        factory.setFeature("http://xml.org/sax/features/external-parameter-entities", false);
        factory.newDocumentBuilder().parse(new ByteArrayInputStream(moData));
      } catch (Exception e) {
        // [MOCA-4-027] [MOCA-4-037]
        throw new AFPParserException("EC-0300 Invalid MOData: XMP data is not valid XML. " + e.getMessage());
      }
    } else if ("AFPT".equals(format)) {
      List<com.mgz.afp.triplets.Triplet> triplets = TripletParser.parseTriplets(moData, 0, moData.length, config);
      for (com.mgz.afp.triplets.Triplet triplet : triplets) {
        if (triplet instanceof com.mgz.afp.triplets.Triplet.Undefined undef) {
          // [MOCA-4-027] [MOCA-4-037]
          throw new AFPParserException("EC-0300 Invalid MOData: AFPT data does not consist of valid triplets. " + undef.getParsingException().getMessage());
        }
      }
    }
  }

  @JacksonXmlProperty(localName = "MOLength")
  public long getMoLength() {
    return moLength;
  }

  public void setMoLength(long moLength) {
    this.moLength = moLength;
  }

  @JacksonXmlProperty(localName = "HeaderLength")
  public int getHeaderLength() {
    return headerLength;
  }

  public void setHeaderLength(int headerLength) {
    this.headerLength = headerLength;
  }

  @JacksonXmlProperty(localName = "MOType")
  public String getMoType() {
    return trimMoca(moType);
  }

  public void setMoType(String moType) {
    this.moType = moType;
  }

  @JacksonXmlProperty(localName = "MOFormat")
  public String getMoFormat() {
    return trimMoca(moFormat);
  }

  public void setMoFormat(String moFormat) {
    this.moFormat = moFormat;
  }

  @JacksonXmlProperty(localName = "MOCompression")
  public String getMoCompression() {
    return trimMoca(moCompression);
  }

  private String trimMoca(String s) {
    if (s == null) {
      return null;
    }
    int last = s.length();
    while (last > 0 && (s.charAt(last - 1) <= ' ' || s.charAt(last - 1) == '@')) {
      last--;
    }
    return s.substring(0, last);
  }

  public void setMoCompression(String moCompression) {
    this.moCompression = moCompression;
  }

  @JacksonXmlProperty(localName = "MONameLength")
  public int getMoNameLength() {
    return moNameLength;
  }

  public void setMoNameLength(int moNameLength) {
    this.moNameLength = moNameLength;
  }

  @JacksonXmlProperty(localName = "MOName")
  public String getMoName() {
    return moName;
  }

  public void setMoName(String moName) {
    this.moName = moName;
  }

  @JacksonXmlProperty(localName = "MOData")
  public byte[] getMoData() {
    return moData;
  }

  public void setMoData(byte[] moData) {
    this.moData = moData;
  }

  @JacksonXmlProperty(localName = "MODataText")
  public String getMoDataText() {
    if (moData == null) {
      return null;
    }
    // Most MOData is XML or text-based (XMP, AFPT, etc.)
    return new String(moData, StandardCharsets.UTF_16BE);
  }
}
