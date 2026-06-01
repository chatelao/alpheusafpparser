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

package com.mgz.afp.bcoca;

import com.mgz.afp.base.StructuredField;
import com.mgz.afp.base.annotations.AFPField;
import com.mgz.afp.bcoca.BDD_BarCodeDataDescriptor.BarCodeType;
import com.mgz.afp.enums.SFFlag;
import com.mgz.afp.exceptions.AFPParserException;
import com.mgz.afp.parser.AFPParserConfiguration;
import com.mgz.util.Constants;
import com.mgz.util.UtilBinaryDecoding;
import com.mgz.util.UtilCharacterEncoding;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.EnumSet;

/**
 * Bar Code Data (BDA) The BDA structured field contains parameters to position a single bar code
 * symbol within a bar code presentation space, parameters to specify special functions for 2D bar
 * codes, flags to specify attributes specific to the symbol, and the data to be encoded. The data
 * is encoded according to the parameters specified in the Bar Code Data Descriptor (BDD) structured
 * field.
 */
public class BDA_BarCodeData extends StructuredField {
  @AFPField
  EnumSet<BarCodeFlag> barCodeFlags;
  @AFPField
  int xOffset;
  @AFPField
  int yOffset;
  @AFPField(isOptional = true)
  public ParametersData parametersData;
  @AFPField(isOptional = true, maxSize = 32759 - 5)
  public byte[] barCodeData;
  String text;

  public void decodeAFP(byte[] sfData, int offset, int length, AFPParserConfiguration config) throws AFPParserException {
    BDD_BarCodeDataDescriptor associatedBarCodeDataDescriptor = config.getCurrentBarCodeDataDescriptor();

    checkDataLength(sfData, offset, length, 5);

    barCodeFlags = BarCodeFlag.valueOf(sfData[offset]);
    xOffset = UtilBinaryDecoding.parseInt(sfData, offset + 1, 2);
    yOffset = UtilBinaryDecoding.parseInt(sfData, offset + 3, 2);

    int actualLength = length != -1 ? length : sfData.length - offset;

    int parameterDataLength = 0;
    if (actualLength > 5) {
      if (associatedBarCodeDataDescriptor.barcodeType == BarCodeType.DataMatrix_GS1DataMatrix_2D) {
        parametersData = new ParametersDataMatrixBarcode();
      } else if (associatedBarCodeDataDescriptor.barcodeType == BarCodeType.MaxiCode_2D) {
        parametersData = new ParametersDataMaxiCode_2D();
      } else if (associatedBarCodeDataDescriptor.barcodeType == BarCodeType.PDF417_2D) {
        parametersData = new ParametersDataPDF417_2D();
      } else if (associatedBarCodeDataDescriptor.barcodeType == BarCodeType.QRCode_2D) {
        if (associatedBarCodeDataDescriptor.barcodeModifier == 0x12) {
          parametersData = new ParametersDataQRCodeWithImage();
        } else {
          parametersData = new ParametersDataQRCode_2D();
        }
      } else if (associatedBarCodeDataDescriptor.barcodeType == BarCodeType.AztecCode) {
        parametersData = new ParametersDataAztecCode();
      } else if (associatedBarCodeDataDescriptor.barcodeType == BarCodeType.HanXinCode) {
        parametersData = new ParametersDataHanXinCode();
      } else if (associatedBarCodeDataDescriptor.barcodeType == BarCodeType.Code_128__GS1_128__UCC_EAN_128__AIM_USS_128__IntelligentMail__ContainerBarcode
          && associatedBarCodeDataDescriptor.barcodeModifier == 0x06) {
        parametersData = new ParametersDataIntelligentMailPackageBarcode();
      } else {
        parametersData = null;
      }
      if (parametersData != null) {
        parameterDataLength = parametersData.decodeAFP(sfData, offset + 5, actualLength - 5);
      }
    }

    if (actualLength > (5 + parameterDataLength)) {
      barCodeData = new byte[actualLength - (5 + parameterDataLength)];
      System.arraycopy(sfData, offset + (5 + parameterDataLength), barCodeData, 0, barCodeData.length);
      if (UtilCharacterEncoding.isHumanReadable(barCodeData, config)) {
        text = new String(barCodeData, config.getAfpCharSet());
      }
    } else {
      barCodeData = new byte[0];
      text = null;
    }
  }

  public static class ParametersDataIntelligentMailPackageBarcode extends ParametersData {
    public EnumSet<IntelligentMailPackageBarcodeFlag> intelligentMailPackageBarcodeFlags;
    public byte bannerLength;
    public byte[] bannerString;

    @Override
    public int decodeAFP(byte[] sfData, int offset, int length) throws AFPParserException {
      BDA_BarCodeData.checkDataLength(sfData, offset, length, 4); // minimum length up to offset 8
      // Byte 5 is Reserved
      intelligentMailPackageBarcodeFlags = IntelligentMailPackageBarcodeFlag.valueOf(sfData[offset + 1]);
      // Byte 7 is Reserved
      bannerLength = sfData[offset + 3];

      if (bannerLength > 0) {
        bannerString = new byte[bannerLength & 0xFF];
        System.arraycopy(sfData, offset + 4, bannerString, 0, bannerString.length);
      } else {
        bannerString = new byte[0];
      }

      return 4 + (bannerLength & 0xFF);
    }

    @Override
    public void writeAFP(OutputStream os, AFPParserConfiguration config) throws IOException {
      os.write(0x00); // Reserved Byte 5
      os.write(IntelligentMailPackageBarcodeFlag.toByte(intelligentMailPackageBarcodeFlags));
      os.write(0x00); // Reserved Byte 7
      os.write(bannerLength);
      if (bannerString != null) {
        os.write(bannerString);
      }
    }

    public enum IntelligentMailPackageBarcodeFlag {
      SuppressUSPS_ServiceBanner,
      SuppressIdentificationBars;

      public static EnumSet<IntelligentMailPackageBarcodeFlag> valueOf(int flagByte) {
        EnumSet<IntelligentMailPackageBarcodeFlag> result = EnumSet.noneOf(IntelligentMailPackageBarcodeFlag.class);
        if ((flagByte & 0x80) != 0) {
          result.add(SuppressUSPS_ServiceBanner);
        }
        if ((flagByte & 0x40) != 0) {
          result.add(SuppressIdentificationBars);
        }
        return result;
      }

      public static int toByte(EnumSet<IntelligentMailPackageBarcodeFlag> flags) {
        int result = 0;
        if (flags == null) return result;
        if (flags.contains(SuppressUSPS_ServiceBanner)) {
          result |= 0x80;
        }
        if (flags.contains(SuppressIdentificationBars)) {
          result |= 0x40;
        }
        return result;
      }
    }
  }

  public static class ParametersDataHanXinCode extends ParametersData {
    public byte version;
    public byte errorCorrectionLevel;
    public EnumSet<HanXinSpecialFunctionFlag> hanXinSpecialFunctionFlags;
    public byte applicationIndicator;
    public byte additionalParametersLength;
    public byte[] additionalParameters;

    @Override
    public int decodeAFP(byte[] sfData, int offset, int length) throws AFPParserException {
      BDA_BarCodeData.checkDataLength(sfData, offset, length, 7); // minimum length up to offset 11
      controlFlags = ControlFlag.valueOf(UtilBinaryDecoding.parseInt(sfData, offset, 1));
      version = sfData[offset + 2];
      errorCorrectionLevel = sfData[offset + 3];
      hanXinSpecialFunctionFlags = HanXinSpecialFunctionFlag.valueOf(sfData[offset + 4]);
      applicationIndicator = sfData[offset + 5];
      additionalParametersLength = sfData[offset + 6];

      if (additionalParametersLength > 0) {
        additionalParameters = new byte[additionalParametersLength & 0xFF];
        System.arraycopy(sfData, offset + 7, additionalParameters, 0, additionalParameters.length);
      } else {
        additionalParameters = new byte[0];
      }

      return 7 + (additionalParametersLength & 0xFF);
    }

    @Override
    public void writeAFP(OutputStream os, AFPParserConfiguration config) throws IOException {
      os.write(ControlFlag.toByte(controlFlags));
      os.write(0x00); // Reserved Byte 6
      os.write(version);
      os.write(errorCorrectionLevel);
      os.write(HanXinSpecialFunctionFlag.toByte(hanXinSpecialFunctionFlags));
      os.write(applicationIndicator);
      os.write(additionalParametersLength);
      if (additionalParameters != null) {
        os.write(additionalParameters);
      }
    }

    public enum HanXinSpecialFunctionFlag {
      GS1FNC1,
      IndustryFNC1;

      public static EnumSet<HanXinSpecialFunctionFlag> valueOf(int flagByte) {
        EnumSet<HanXinSpecialFunctionFlag> result = EnumSet.noneOf(HanXinSpecialFunctionFlag.class);
        if ((flagByte & 0x80) != 0) {
          result.add(GS1FNC1);
        }
        if ((flagByte & 0x40) != 0) {
          result.add(IndustryFNC1);
        }
        return result;
      }

      public static int toByte(EnumSet<HanXinSpecialFunctionFlag> flags) {
        int result = 0;
        if (flags == null) return result;
        if (flags.contains(GS1FNC1)) {
          result |= 0x80;
        }
        if (flags.contains(IndustryFNC1)) {
          result |= 0x40;
        }
        return result;
      }
    }
  }

  @Override
  public void writeAFP(OutputStream os, AFPParserConfiguration config) throws IOException {
    ByteArrayOutputStream baos = new ByteArrayOutputStream();

    baos.write(BarCodeFlag.toByte(barCodeFlags));
    baos.write(UtilBinaryDecoding.intToByteArray(xOffset, 2));
    baos.write(UtilBinaryDecoding.intToByteArray(yOffset, 2));
    if (parametersData != null) {
      parametersData.writeAFP(baos, config);
    }
    if (barCodeData != null) {
      baos.write(barCodeData);
    }

    writeFullStructuredField(os, baos.toByteArray());
  }

  public EnumSet<BarCodeFlag> getBarCodeFlags() {
    return barCodeFlags;
  }

  public void setBarCodeFlags(EnumSet<BarCodeFlag> barCodeFlags) {
    this.barCodeFlags = barCodeFlags;
  }

  /**
   * Adds the given {@link BarCodeFlag} to the set of {@link BarCodeFlag}. Takes care of mutual
   * exclusive {@link BarCodeFlag}.
   */
  public void addBarCodeFlag(BarCodeFlag barCodeFlag) {
    if (barCodeFlags == null) {
      barCodeFlags = EnumSet.noneOf(BarCodeFlag.class);
    }
    if (barCodeFlag == BarCodeFlag.PositionHRIBelow) {
      barCodeFlags.remove(BarCodeFlag.PositionHRIAbove);
    } else if (barCodeFlag == BarCodeFlag.PositionHRIAbove) {
      barCodeFlags.remove(BarCodeFlag.PositionHRIBelow);
    }
    barCodeFlags.add(barCodeFlag);
  }

  public int getxOffset() {
    return xOffset;
  }

  public void setxOffset(int xOffset) {
    this.xOffset = xOffset;
  }

  public int getyOffset() {
    return yOffset;
  }

  public void setyOffset(int yOffset) {
    this.yOffset = yOffset;
  }

  @JacksonXmlProperty(localName = "text")
  public String getText() {
    return text;
  }

  public enum BarCodeFlag {
    HRINotPresent, // bit 7
    // PositionDefault, // bit 6,5 B00
    PositionHRIBelow, // B01
    PositionHRIAbove, // B10
    // Reserved bit 4
    SSCASTAsteriskIsPresent, // bit 3

    SuppressBarCodeSymbol, // bit 2

    SuppressAndAdjustBlanks // bit 1
    // Reserved bit 0
    ;

    public static EnumSet<BarCodeFlag> valueOf(int flagByte) {
      EnumSet<BarCodeFlag> result = EnumSet.noneOf(BarCodeFlag.class);

      if ((flagByte & 0x80) != 0) {
        result.add(HRINotPresent);
      }
      if ((flagByte & 0x40) != 0) { // [BCOCA-4-310] Bits 1-2: Position B'10' = Above
        result.add(PositionHRIAbove);
      } else if ((flagByte & 0x20) != 0) { // [BCOCA-4-310] Bits 1-2: Position B'01' = Below
        result.add(PositionHRIBelow);
      }
      if ((flagByte & 0x10) != 0) { // [BCOCA-4-311] Bit 3: SSCAST
        result.add(SSCASTAsteriskIsPresent);
      }
      if ((flagByte & 0x04) != 0) { // [BCOCA-4-313] Bit 5: Suppress bar code symbol
        result.add(SuppressBarCodeSymbol);
      }
      if ((flagByte & 0x02) != 0) { // [BCOCA-4-314] Bit 6: Suppress trailing blanks
        result.add(SuppressAndAdjustBlanks);
      }

      return result;
    }

    /**
     * Converts the {@link BarCodeFlag} in given {@link EnumSet} to AFP SF FlagByte.
     */
    public static int toByte(EnumSet<BarCodeFlag> flags) {
      int result = 0;

      if (flags.contains(HRINotPresent)) {
        result += 0x80;
      }
      if (flags.contains(PositionHRIAbove)) {
        result += 0x40;
      } else if (flags.contains(PositionHRIBelow)) {
        result += 0x20;
      }
      if (flags.contains(SSCASTAsteriskIsPresent)) {
        result += 0x10;
      }
      if (flags.contains(SuppressBarCodeSymbol)) {
        result += 0x04;
      }
      if (flags.contains(SuppressAndAdjustBlanks)) {
        result += 0x02;
      }

      return result;
    }
  }

  // Base class for all "Special-Function Parameter Data".
  public static abstract class ParametersData {
    public EnumSet<ControlFlag> controlFlags;
    public byte sequenceIndicator;
    public byte totalNumberOfSymbols;

    public abstract int decodeAFP(byte[] sfData, int offset, int length) throws AFPParserException;

    public abstract void writeAFP(OutputStream os, AFPParserConfiguration config) throws IOException;

    public byte getSequenceIndicator() {
      return sequenceIndicator;
    }

    public void setSequenceIndicator(byte sequenceIndicator) {
      this.sequenceIndicator = sequenceIndicator;
    }

    public byte getTotalNumberOfSymbols() {
      return totalNumberOfSymbols;
    }

    public void setTotalNumberOfSymbols(byte totalNumberOfSymbols) {
      this.totalNumberOfSymbols = totalNumberOfSymbols;
    }

    public EnumSet<ControlFlag> getControlFlags() {
      return controlFlags;
    }

    public void setControlFlags(EnumSet<ControlFlag> controlFlags) {
      this.controlFlags = controlFlags;
    }

    public void addControlFlag(ControlFlag controlFlag) {
      if (this.controlFlags == null) {
        controlFlags = EnumSet.noneOf(ControlFlag.class);
      }
      controlFlags.add(controlFlag);
    }

    public void removeControlFlag(ControlFlag controlFlag) {
      if (this.controlFlags == null) {
        return;
      } else {
        this.controlFlags.remove(controlFlag);
      }
    }

    public enum ControlFlag {
      ConvertEBCDICToASCII, // bit 0
      IgnoreAllEscapeSequences, // bit 1
      TooMuchDataException; // bit 2

      public static EnumSet<ControlFlag> valueOf(int flagByte) {
        EnumSet<ControlFlag> result = EnumSet.noneOf(ControlFlag.class);
        if ((flagByte & 0x80) != 0) {
          result.add(ConvertEBCDICToASCII);
        }
        if ((flagByte & 0x40) != 0) {
          result.add(IgnoreAllEscapeSequences);
        }
        if ((flagByte & 0x20) != 0) {
          result.add(TooMuchDataException);
        }
        return result;
      }

      public static int toByte(EnumSet<ControlFlag> controlFlags) {
        int result = 0;
        if (controlFlags == null) return result;
        if (controlFlags.contains(ConvertEBCDICToASCII)) {
          result |= 0x80;
        }
        if (controlFlags.contains(IgnoreAllEscapeSequences)) {
          result |= 0x40;
        }
        if (controlFlags.contains(TooMuchDataException)) {
          result |= 0x20;
        }
        return result;
      }
    }
  }

  public static class ParametersDataAztecCode extends ParametersData {
    public byte desiredNumberOfLayers;
    public byte levelOfErrorCorrection;
    public EnumSet<AztecSpecialFunctionFlag> aztecSpecialFunctionFlags;
    public byte applicationIndicator;
    public byte structuredAppendIdLength;
    public byte[] structuredAppendId;
    public byte additionalParametersLength;
    public byte[] additionalParameters;

    @Override
    public int decodeAFP(byte[] sfData, int offset, int length) throws AFPParserException {
      BDA_BarCodeData.checkDataLength(sfData, offset, length, 9);
      controlFlags = ControlFlag.valueOf(UtilBinaryDecoding.parseInt(sfData, offset, 1));

      desiredNumberOfLayers = sfData[offset + 2];
      levelOfErrorCorrection = sfData[offset + 3];
      aztecSpecialFunctionFlags = AztecSpecialFunctionFlag.valueOf(sfData[offset + 4]);
      applicationIndicator = sfData[offset + 5];
      sequenceIndicator = sfData[offset + 6];
      totalNumberOfSymbols = sfData[offset + 7];
      structuredAppendIdLength = sfData[offset + 8];

      int currentOffset = 9;
      if (structuredAppendIdLength > 0) {
        structuredAppendId = new byte[structuredAppendIdLength & 0xFF];
        System.arraycopy(sfData, offset + currentOffset, structuredAppendId, 0, structuredAppendId.length);
        currentOffset += structuredAppendId.length;
      } else {
        structuredAppendId = new byte[0];
      }

      additionalParametersLength = sfData[offset + currentOffset];
      currentOffset++;
      if (additionalParametersLength > 0) {
        additionalParameters = new byte[additionalParametersLength & 0xFF];
        System.arraycopy(sfData, offset + currentOffset, additionalParameters, 0, additionalParameters.length);
        currentOffset += additionalParameters.length;
      } else {
        additionalParameters = new byte[0];
      }

      return currentOffset;
    }

    @Override
    public void writeAFP(OutputStream os, AFPParserConfiguration config) throws IOException {
      os.write(ControlFlag.toByte(controlFlags));
      os.write(0x00); // Reserved Byte 6
      os.write(desiredNumberOfLayers);
      os.write(levelOfErrorCorrection);
      os.write(AztecSpecialFunctionFlag.toByte(aztecSpecialFunctionFlags));
      os.write(applicationIndicator);
      os.write(sequenceIndicator);
      os.write(totalNumberOfSymbols);
      os.write(structuredAppendIdLength);
      if (structuredAppendId != null) {
        os.write(structuredAppendId);
      }
      os.write(additionalParametersLength);
      if (additionalParameters != null) {
        os.write(additionalParameters);
      }
    }

    public enum AztecSpecialFunctionFlag {
      GS1FNC1,
      IndustryFNC1,
      ReaderInitialization;

      public static EnumSet<AztecSpecialFunctionFlag> valueOf(int flagByte) {
        EnumSet<AztecSpecialFunctionFlag> result = EnumSet.noneOf(AztecSpecialFunctionFlag.class);
        if ((flagByte & 0x80) != 0) {
          result.add(GS1FNC1);
        }
        if ((flagByte & 0x40) != 0) {
          result.add(IndustryFNC1);
        }
        if ((flagByte & 0x20) != 0) {
          result.add(ReaderInitialization);
        }
        return result;
      }

      public static int toByte(EnumSet<AztecSpecialFunctionFlag> flags) {
        int result = 0;
        if (flags == null) return result;
        if (flags.contains(GS1FNC1)) {
          result |= 0x80;
        }
        if (flags.contains(IndustryFNC1)) {
          result |= 0x40;
        }
        if (flags.contains(ReaderInitialization)) {
          result |= 0x20;
        }
        return result;
      }
    }
  }

  public static class ParametersDataMatrixBarcode extends ParametersData {
    public int desiredRowSize;
    public int desiredNumberOfRows;
    short fileIDFirstByte;
    short fileIDSecondByte;
    public EnumSet<SpecialFunctionFlag> specialFunctionFlags;

    @Override
    public int decodeAFP(byte[] sfData, int offset, int length) throws AFPParserException {
      BDA_BarCodeData.checkDataLength(sfData, offset, length, 10);
      controlFlags = ControlFlag.valueOf(UtilBinaryDecoding.parseInt(sfData, offset, 1));
      desiredRowSize = UtilBinaryDecoding.parseInt(sfData, offset + 1, 2);
      desiredNumberOfRows = UtilBinaryDecoding.parseInt(sfData, offset + 3, 2);
      sequenceIndicator = sfData[offset + 5];
      totalNumberOfSymbols = sfData[offset + 6];
      fileIDFirstByte = UtilBinaryDecoding.parseShort(sfData, offset + 7, 1);
      fileIDSecondByte = UtilBinaryDecoding.parseShort(sfData, offset + 8, 1);
      specialFunctionFlags = SpecialFunctionFlag.valueOf(UtilBinaryDecoding.parseInt(sfData, offset + 9, 1));

      return 10;
    }

    @Override
    public void writeAFP(OutputStream os, AFPParserConfiguration config) throws IOException {
      os.write(ControlFlag.toByte(controlFlags));
      os.write(UtilBinaryDecoding.intToByteArray(desiredRowSize, 2));
      os.write(UtilBinaryDecoding.intToByteArray(desiredNumberOfRows, 2));
      os.write(sequenceIndicator);
      os.write(totalNumberOfSymbols);
      os.write(fileIDFirstByte);
      os.write(fileIDSecondByte);
      os.write(SpecialFunctionFlag.toByte(specialFunctionFlags));
    }

    public int getDesiredRowSize() {
      return desiredRowSize;
    }

    public void setDesiredRowSize(int desiredRowSize) {
      this.desiredRowSize = desiredRowSize;
    }

    public int getDesiredNumberOfRows() {
      return desiredNumberOfRows;
    }

    public void setDesiredNumberOfRows(int desiredNumberOfRows) {
      this.desiredNumberOfRows = desiredNumberOfRows;
    }

    public short getFileIDFirstByte() {
      return fileIDFirstByte;
    }

    public void setFileIDFirstByte(short fileIDFirstByte) {
      this.fileIDFirstByte = fileIDFirstByte;
    }

    public short getFileIDSecondByte() {
      return fileIDSecondByte;
    }

    public void setFileIDSecondByte(short fileIDSecondByte) {
      this.fileIDSecondByte = fileIDSecondByte;
    }

    public EnumSet<SpecialFunctionFlag> getSpecialFunctionFlags() {
      return specialFunctionFlags;
    }

    public void setSpecialFunctionFlags(EnumSet<SpecialFunctionFlag> specialFunctionFlags) {
      this.specialFunctionFlags = specialFunctionFlags;
    }

    /**
     * This method adds the given flag. It takes care of mutual exclusive flags.
     *
     * @param specialFunctionFlag flag to set.
     */
    public void addSpecialFunctionFlag(SpecialFunctionFlag specialFunctionFlag) {
      if (specialFunctionFlags == null) {
        specialFunctionFlags = EnumSet.noneOf(SpecialFunctionFlag.class);
      }

      if (specialFunctionFlag == SpecialFunctionFlag.UseMacro06HeaderTrailer) {
        specialFunctionFlags.remove(SpecialFunctionFlag.UseMacro05HeaderTrailer);
      } else if (specialFunctionFlag == SpecialFunctionFlag.UseMacro05HeaderTrailer) {
        specialFunctionFlags.remove(SpecialFunctionFlag.UseMacro06HeaderTrailer);
      }
      if (specialFunctionFlag == SpecialFunctionFlag.EncodationSchemeAutoEncoding
          || specialFunctionFlag == SpecialFunctionFlag.EncodationSchemeBase256
          || specialFunctionFlag == SpecialFunctionFlag.EncodationSchemeEDIFACT
          || specialFunctionFlag == SpecialFunctionFlag.EncodationSchemeX12
          || specialFunctionFlag == SpecialFunctionFlag.EncodationSchemeText
          || specialFunctionFlag == SpecialFunctionFlag.EncodationSchemeC40
          || specialFunctionFlag == SpecialFunctionFlag.EncodationSchemeASCII
          ) {
        specialFunctionFlags.remove(SpecialFunctionFlag.EncodationSchemeAutoEncoding);
        specialFunctionFlags.remove(SpecialFunctionFlag.EncodationSchemeBase256);
        specialFunctionFlags.remove(SpecialFunctionFlag.EncodationSchemeEDIFACT);
        specialFunctionFlags.remove(SpecialFunctionFlag.EncodationSchemeX12);
        specialFunctionFlags.remove(SpecialFunctionFlag.EncodationSchemeText);
        specialFunctionFlags.remove(SpecialFunctionFlag.EncodationSchemeC40);
        specialFunctionFlags.remove(SpecialFunctionFlag.EncodationSchemeASCII);
      }

      specialFunctionFlags.add(specialFunctionFlag);
    }

    public void removeSpecialFunctionFlag(SpecialFunctionFlag specialFunctionFlag) {
      if (specialFunctionFlags == null) {
        return;
      } else {
        specialFunctionFlags.remove(specialFunctionFlag);
      }
    }

    public enum SpecialFunctionFlag {
      SymbolConfirmsToGS1Standard,
      SymbolConfirmsToIndustryStandard,
      SymbolEncodesAMessage,
      UseMacro05HeaderTrailer,
      UseMacro06HeaderTrailer,
      EncodationSchemeASCII,
      EncodationSchemeC40,
      EncodationSchemeText,
      EncodationSchemeX12,
      EncodationSchemeEDIFACT,
      EncodationSchemeBase256,
      EncodationSchemeAutoEncoding;

      public static EnumSet<SpecialFunctionFlag> valueOf(int flagByte) {
        EnumSet<SpecialFunctionFlag> result = EnumSet.noneOf(SpecialFunctionFlag.class);
        if ((flagByte & 0x80) != 0) {
          result.add(SymbolConfirmsToGS1Standard);
        }
        if ((flagByte & 0x40) != 0) {
          result.add(SymbolConfirmsToIndustryStandard);
        }
        if ((flagByte & 0x20) != 0) {
          result.add(SymbolEncodesAMessage);
        }
        if ((flagByte & 0x10) != 0) {
          result.add(UseMacro06HeaderTrailer);
        }
        if ((flagByte & 0x08) != 0) {
          result.add(UseMacro05HeaderTrailer);
        }
        if ((flagByte & 0x07) != 0) {
          result.add(EncodationSchemeAutoEncoding);
        }
        if ((flagByte & 0x06) != 0) {
          result.add(EncodationSchemeBase256);
        }
        if ((flagByte & 0x05) != 0) {
          result.add(EncodationSchemeEDIFACT);
        }
        if ((flagByte & 0x04) != 0) {
          result.add(EncodationSchemeX12);
        }
        if ((flagByte & 0x03) != 0) {
          result.add(EncodationSchemeText);
        }
        if ((flagByte & 0x02) != 0) {
          result.add(EncodationSchemeC40);
        }
        if ((flagByte & 0x01) != 0) {
          result.add(EncodationSchemeASCII);
        }
        return result;
      }

      public static int toByte(EnumSet<SpecialFunctionFlag> specialFunctionFlags) {
        int result = 0;
        if (specialFunctionFlags.contains(SymbolConfirmsToGS1Standard)) {
          result |= 0x80;
        }
        if (specialFunctionFlags.contains(SymbolConfirmsToIndustryStandard)) {
          result |= 0x40;
        }
        if (specialFunctionFlags.contains(SymbolEncodesAMessage)) {
          result |= 0x20;
        }
        if (specialFunctionFlags.contains(UseMacro06HeaderTrailer)) {
          result |= 0x10;
        } else if (specialFunctionFlags.contains(UseMacro05HeaderTrailer)) {
          result |= 0x08;
        }
        if (specialFunctionFlags.contains(EncodationSchemeAutoEncoding)) {
          result |= 0x07;
        } else if (specialFunctionFlags.contains(EncodationSchemeBase256)) {
          result |= 0x06;
        } else if (specialFunctionFlags.contains(EncodationSchemeEDIFACT)) {
          result |= 0x05;
        } else if (specialFunctionFlags.contains(EncodationSchemeX12)) {
          result |= 0x04;
        } else if (specialFunctionFlags.contains(EncodationSchemeText)) {
          result |= 0x03;
        } else if (specialFunctionFlags.contains(EncodationSchemeC40)) {
          result |= 0x02;
        } else if (specialFunctionFlags.contains(EncodationSchemeASCII)) {
          result |= 0x01;
        }
        return result;
      }
    }
  }

  public static class ParametersDataMaxiCode_2D extends ParametersData {
    public SymbolMode symbolMode = SymbolMode.Mode2;
    public SpecialFunctionFlag specialFunctionFlag = SpecialFunctionFlag.NoZipperPattern;

    @Override
    public int decodeAFP(byte[] sfData, int offset, int length) throws AFPParserException {
      BDA_BarCodeData.checkDataLength(sfData, offset, length, 5);
      controlFlags = ControlFlag.valueOf(UtilBinaryDecoding.parseInt(sfData, offset, 1));
      symbolMode = SymbolMode.valueOf(sfData[offset + 1]);
      sequenceIndicator = sfData[offset + 2];
      totalNumberOfSymbols = sfData[offset + 3];
      specialFunctionFlag = SpecialFunctionFlag.valueOf(sfData[offset + 4]);
      return 5;
    }

    @Override
    public void writeAFP(OutputStream os, AFPParserConfiguration config) throws IOException {
      os.write(ControlFlag.toByte(controlFlags));
      os.write(symbolMode.toByte());
      os.write(sequenceIndicator);
      os.write(totalNumberOfSymbols);
      os.write(specialFunctionFlag.toByte());
    }

    public enum SymbolMode {
      Mode2, Mode3, Mode4, Mode5, Mode6;

      public static SymbolMode valueOf(byte symbolModeCode) {
        if (symbolModeCode == 0x02) {
          return Mode2;
        } else if (symbolModeCode == 0x03) {
          return Mode3;
        } else if (symbolModeCode == 0x04) {
          return Mode4;
        } else if (symbolModeCode == 0x05) {
          return Mode5;
        } else if (symbolModeCode == 0x06) {
          return Mode6;
        }
        return null;
      }

      public int toByte() {
        if (this == Mode2) {
          return 0x02;
        } else if (this == Mode3) {
          return 0x03;
        } else if (this == Mode4) {
          return 0x04;
        } else if (this == Mode5) {
          return 0x05;
        } else if (this == Mode6) {
          return 0x06;
        } else {
          return 0x00;
        }
      }
    }

    public enum SpecialFunctionFlag {
      NoZipperPattern,
      VerticalZipperPatternOnRight;

      public static SpecialFunctionFlag valueOf(byte code) {
        if (code == 0x00) {
          return NoZipperPattern;
        } else {
          return VerticalZipperPatternOnRight;
        }
      }

      public int toByte() {
        if (this == NoZipperPattern) {
          return 0x00;
        } else {
          return 0x80;
        }
      }
    }
  }

  public static class ParametersDataPDF417_2D extends ParametersData {
    public byte numberOfDataSymbolCharactersPerRow;
    public byte desiredNumberOfRows;
    public byte securityLevel;
    public short lengthOfMacroPDF417ControlBlock;
    public byte[] macroPDF417ControlBlock;

    @Override
    public int decodeAFP(byte[] sfData, int offset, int length) throws AFPParserException {
      checkDataLength(sfData, offset, length, 6);
      controlFlags = ControlFlag.valueOf(UtilBinaryDecoding.parseInt(sfData, offset, 1));
      numberOfDataSymbolCharactersPerRow = sfData[offset + 1];
      desiredNumberOfRows = sfData[offset + 2];
      securityLevel = sfData[offset + 3];
      lengthOfMacroPDF417ControlBlock = UtilBinaryDecoding.parseShort(sfData, offset + 4, 2);
      macroPDF417ControlBlock = new byte[lengthOfMacroPDF417ControlBlock];
      if (lengthOfMacroPDF417ControlBlock > 0) {
        System.arraycopy(sfData, offset + 6, macroPDF417ControlBlock, 0, lengthOfMacroPDF417ControlBlock);
      }
      return 6 + lengthOfMacroPDF417ControlBlock;
    }

    @Override
    public void writeAFP(OutputStream os, AFPParserConfiguration config) throws IOException {
      os.write(ControlFlag.toByte(controlFlags));
      os.write(numberOfDataSymbolCharactersPerRow);
      os.write(desiredNumberOfRows);
      os.write(securityLevel);
      os.write(UtilBinaryDecoding.shortToByteArray(lengthOfMacroPDF417ControlBlock, 2));
      os.write(macroPDF417ControlBlock);
    }
  }

  public static class ParametersDataQRCode_2D extends ParametersData {
    public Conversion conversion;
    public byte versionOfSymbol;
    public ErrorCorrectionLevel errorCorrectionLevel;
    public byte parityData;
    public EnumSet<QRCodeSpecialFunctionFlag> qrCodeSpecialFunctionFlags;
    public byte applicationIndicator;

    @Override
    public int decodeAFP(byte[] sfData, int offset, int length) throws AFPParserException {
      return decodeCommonQRCode(sfData, offset, length);
    }

    protected int decodeCommonQRCode(byte[] sfData, int offset, int length) throws AFPParserException {
      checkDataLength(sfData, offset, length, 9);
      controlFlags = ControlFlag.valueOf(UtilBinaryDecoding.parseInt(sfData, offset, 1));
      conversion = Conversion.valueOf(sfData[offset + 1]);
      versionOfSymbol = sfData[offset + 2];
      errorCorrectionLevel = ErrorCorrectionLevel.valueOf(sfData[offset + 3]);
      sequenceIndicator = sfData[offset + 4];
      totalNumberOfSymbols = sfData[offset + 5];
      parityData = sfData[offset + 6];
      qrCodeSpecialFunctionFlags = QRCodeSpecialFunctionFlag.valueOf(UtilBinaryDecoding.parseInt(sfData, offset + 7, 1));
      applicationIndicator = sfData[offset + 8];
      return 9;
    }

    @Override
    public void writeAFP(OutputStream os, AFPParserConfiguration config) throws IOException {
      writeCommonQRCode(os);
    }

    protected void writeCommonQRCode(OutputStream os) throws IOException {
      os.write(ControlFlag.toByte(controlFlags));
      os.write(conversion.toByte());
      os.write(versionOfSymbol);
      os.write(errorCorrectionLevel.toByte());
      os.write(sequenceIndicator);
      os.write(totalNumberOfSymbols);
      os.write(parityData);
      os.write(QRCodeSpecialFunctionFlag.toByte(qrCodeSpecialFunctionFlags));
      os.write(applicationIndicator);
    }

    public enum Conversion {
      NoConversion(0x00),
      SBCS_EBCDIC_CodePage500(0x01),
      SBCS_EBCDIC_CodePage290(0x02),
      SBCS_EBCDIC_CodePage1027(0x03),
      AFPLineDataSOSIData_CCSID1390_CCSID943(0x04),
      AFPLineDataSOSIData_CCSID1399_CCSID943(0x05),
      AFPLineDataSOSIData_CCSID1390_CCSID932(0x06),
      AFPLineDataSOSIData_CCSID1399_CCSID932(0x07),
      AFPLineDataSOSIData_CCSID1390_CCSID942(0x08),
      AFPLineDataSOSIData_CCSID1399_CCSID942(0x09);
      int code;

      Conversion(int conversionCode) {
        this.code = conversionCode;
      }

      public static Conversion valueOf(byte conversionCode) {
        for (Conversion conversion : values()) {
          if (conversion.code == (conversionCode & 0xFF)) {
            return conversion;
          }
        }
        return NoConversion;
      }

      public int toByte() {
        return code;
      }
    }

    public enum ErrorCorrectionLevel {
      LevelL, LevelM, LevelQ, LevelH;

      public static ErrorCorrectionLevel valueOf(byte leveCode) {
        for (ErrorCorrectionLevel level : values()) {
          if (level.ordinal() == (leveCode & 0xFF)) {
            return level;
          }
        }
        return null;
      }

      public int toByte() {
        return this.ordinal();
      }
    }

    public enum QRCodeSpecialFunctionFlag {
      UCC_EAN_FNC1,
      IndustryFNC1;

      public static EnumSet<QRCodeSpecialFunctionFlag> valueOf(int flagByte) {
        EnumSet<QRCodeSpecialFunctionFlag> result = EnumSet.noneOf(QRCodeSpecialFunctionFlag.class);
        if ((flagByte & 0x80) != 0) {
          result.add(UCC_EAN_FNC1);
        }
        if ((flagByte & 0x40) != 0) {
          result.add(IndustryFNC1);
        }
        return result;
      }

      public static int toByte(EnumSet<QRCodeSpecialFunctionFlag> flags) {
        int result = 0;
        if (flags == null) return result;
        if (flags.contains(UCC_EAN_FNC1)) {
          result |= 0x80;
        }
        if (flags.contains(IndustryFNC1)) {
          result |= 0x40;
        }
        return result;
      }
    }
  }

  public static class ParametersDataQRCodeWithImage extends ParametersDataQRCode_2D {
    public EnumSet<QRCodeWithImageFlag> qrCodeWithImageFlags;
    public int repeatingGroupsLength;
    public java.util.List<ImageInformationBlock> imageInformationBlocks = new java.util.ArrayList<>();

    @Override
    public int decodeAFP(byte[] sfData, int offset, int length) throws AFPParserException {
      int currentOffset = decodeCommonQRCode(sfData, offset, length);
      BDA_BarCodeData.checkDataLength(sfData, offset + currentOffset, length - currentOffset, 3);
      qrCodeWithImageFlags = QRCodeWithImageFlag.valueOf(sfData[offset + currentOffset]);
      repeatingGroupsLength = UtilBinaryDecoding.parseInt(sfData, offset + currentOffset + 1, 2);
      currentOffset += 3;

      int remainingRepGroupsLength = repeatingGroupsLength;
      while (remainingRepGroupsLength > 0) {
        ImageInformationBlock block = new ImageInformationBlock();
        int blockLength = block.decode(sfData, offset + currentOffset, remainingRepGroupsLength);
        imageInformationBlocks.add(block);
        currentOffset += blockLength;
        remainingRepGroupsLength -= (blockLength);
      }
      return currentOffset;
    }

    @Override
    public void writeAFP(OutputStream os, AFPParserConfiguration config) throws IOException {
      writeCommonQRCode(os);
      os.write(QRCodeWithImageFlag.toByte(qrCodeWithImageFlags));
      os.write(UtilBinaryDecoding.intToByteArray(repeatingGroupsLength, 2));
      for (ImageInformationBlock block : imageInformationBlocks) {
        block.write(os);
      }
    }

    public enum QRCodeWithImageFlag {
      PresentImagesFirst,
      PresentOnlyImages;

      public static EnumSet<QRCodeWithImageFlag> valueOf(int flagByte) {
        EnumSet<QRCodeWithImageFlag> result = EnumSet.noneOf(QRCodeWithImageFlag.class);
        if ((flagByte & 0x80) != 0) {
          result.add(PresentImagesFirst);
        }
        if ((flagByte & 0x40) != 0) {
          result.add(PresentOnlyImages);
        }
        return result;
      }

      public static int toByte(EnumSet<QRCodeWithImageFlag> flags) {
        int result = 0;
        if (flags == null) return result;
        if (flags.contains(PresentImagesFirst)) {
          result |= 0x80;
        }
        if (flags.contains(PresentOnlyImages)) {
          result |= 0x40;
        }
        return result;
      }
    }

    public static class ImageInformationBlock {
      public byte length;
      public short imageLocalId;
      public byte offsetUnitBase;
      public short offsetUpub;
      public short xOffset;
      public short yOffset;
      public short orientation;
      public byte coordinateSystem;
      public byte extentUnitBase;
      public short extentUpub;
      public short xExtent;
      public short yExtent;
      public byte mappingOption;
      public byte[] additionalData;

      public int decode(byte[] data, int offset, int maxLength) throws AFPParserException {
        length = data[offset];
        imageLocalId = UtilBinaryDecoding.parseShort(data, offset + 3, 2);
        offsetUnitBase = data[offset + 5];
        offsetUpub = UtilBinaryDecoding.parseShort(data, offset + 6, 2);
        xOffset = UtilBinaryDecoding.parseShort(data, offset + 8, 2);
        yOffset = UtilBinaryDecoding.parseShort(data, offset + 10, 2);
        orientation = UtilBinaryDecoding.parseShort(data, offset + 12, 2);
        coordinateSystem = data[offset + 14];
        extentUnitBase = data[offset + 15];
        extentUpub = UtilBinaryDecoding.parseShort(data, offset + 16, 2);
        xExtent = UtilBinaryDecoding.parseShort(data, offset + 18, 2);
        yExtent = UtilBinaryDecoding.parseShort(data, offset + 20, 2);
        mappingOption = data[offset + 22];
        if ((length & 0xFF) > 22) {
          additionalData = new byte[(length & 0xFF) - 22];
          System.arraycopy(data, offset + 23, additionalData, 0, additionalData.length);
        }
        return (length & 0xFF) + 1;
      }

      public void write(OutputStream os) throws IOException {
        os.write(length);
        os.write(new byte[] {0, 0});
        os.write(UtilBinaryDecoding.shortToByteArray(imageLocalId, 2));
        os.write(offsetUnitBase);
        os.write(UtilBinaryDecoding.shortToByteArray(offsetUpub, 2));
        os.write(UtilBinaryDecoding.shortToByteArray(xOffset, 2));
        os.write(UtilBinaryDecoding.shortToByteArray(yOffset, 2));
        os.write(UtilBinaryDecoding.shortToByteArray(orientation, 2));
        os.write(coordinateSystem);
        os.write(extentUnitBase);
        os.write(UtilBinaryDecoding.shortToByteArray(extentUpub, 2));
        os.write(UtilBinaryDecoding.shortToByteArray(xExtent, 2));
        os.write(UtilBinaryDecoding.shortToByteArray(yExtent, 2));
        os.write(mappingOption);
        if (additionalData != null) {
          os.write(additionalData);
        }
      }
    }
  }
}
