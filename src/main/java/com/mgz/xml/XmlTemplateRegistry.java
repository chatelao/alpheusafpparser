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

package com.mgz.xml;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

/**
 * Registry for XML byte templates.
 */
public class XmlTemplateRegistry {
  private static final Map<String, XmlTemplate> TEMPLATES = new HashMap<>();

  static {
    // PTOCA Control Sequences
    register("AMI", new String[] {"<AMI_AbsoluteMoveInline displacement=\"", "\"/>"});
    register("RMI", new String[] {"<RMI_RelativeMoveInline increment=\"", "\"/>"});
    register("AMB", new String[] {"<AMB_AbsoluteMoveBaseline displacement=\"", "\"/>"});
    register("RMB", new String[] {"<RMB_RelativeMoveBaseline increment=\"", "\"/>"});
    register("SCFL", new String[] {"<SCFL_SetCodedFontLocal codedFontLocalID=\"", "\"/>"});
    register("SBI", new String[] {"<SBI_SetBaselineIncrement increment=\"", "\"/>"});
    register("SIM", new String[] {"<SIM_SetInlineMargin displacement=\"", "\"/>"});
    register("BSU", new String[] {"<BSU_BeginSuppression suppressionID=\"", "\"/>"});
    register("ESU", new String[] {"<ESU_EndSuppression suppressionID=\"", "\"/>"});
    register("SVI", new String[] {"<SVI_SetVariableSpaceCharacterIncrement increment=\"", "\"/>"});
    register("BLN", new String[] {"<BLN_BeginLine/>"});
    register("STO", new String[] {"<STO_SetTextOrientation xOrientation=\"", "\" yOrientation=\"",
        "\"/>"});
    register("SIA", new String[] {"<SIA_SetIntercharacterAdjustment adjustment=\"",
        "\" direction=\"", "\"/>"});
    register("STC", new String[] {"<STC_SetTextColor foregroundColor=\"", "\" precision=\"",
        "\"/>"});
    register("USC", new String[] {"<USC_Underscore bypassFlag=\"", "\"/>"});

    // Triplets
    register("AQ", new String[] {"<AttributeQualifier sequenceNumber=\"", "\" levelNumber=\"",
        "\"/>"});
    register("RLI", new String[] {"<ResourceLocalIdentifier resourceType=\"",
        "\" resourceLocalID=\"", "\"/>"});
    register("CR", new String[] {"<CharacterRotation characterRotation=\"", "\"/>"});
    register("MU", new String[] {"<MeasurementUnits xUnitBase=\"", "\" yUnitBase=\"",
        "\" xUnitsPerUnitbase=\"", "\" yUnitsPerUnitbase=\"", "\"/>"});
    register("OBO", new String[] {"<ObjectByteOffset byteOffset=\"", "\" byteOffsetHighOrder=\"",
        "\"/>"});
    register("MO", new String[] {"<MappingOption dataObjecMapingOption=\"", "\"/>"});
    register("OAS", new String[] {"<ObjectAreaSize sizeType_0x02=\"", "\" xSize=\"", "\" ySize=\"",
        "\"/>"});
    register("CGCS", new String[] {
        "<CodedGraphicCharacterSetGlobalID graphicCharacterSetGlobalID=\"",
        "\" codePageGlobalID_codedCharacterSetID=\"", "\"/>"});
    register("ROT", new String[] {"<ResourceObjectType objectType=\"", "\"/>"});
    register("DP", new String[] {"<DescriptorPosition objectAreaDescriptorID=\"", "\"/>"});
    register("MEC", new String[] {"<MediaEjectControl reserved2=\"", "\" mediaEjectControl=\"",
        "\"/>"});
    register("RUA", new String[] {"<ResourceUsageAttribute frequencyOfUse=\"", "\"/>"});
    register("PSRM", new String[] {"<PresentationSpaceResetMixing backgroundMixingFlag=\"",
        "\"/>"});
    register("ERLI", new String[] {"<ExtendedResourceLocalIdentifier resourceType=\"",
        "\" extendedResourceLocalID=\"", "\"/>"});
    register("RSN", new String[] {"<ResourceSectionNumber resourceSectionNumber=\"", "\"/>"});
    register("MMPN", new String[] {"<MediumMapPageNumber pageNumber=\"", "\"/>"});
    register("OBE", new String[] {"<ObjectByteExtent byteExtentLow=\"", "\" byteExtentHigh=\"",
        "\"/>"});
    register("OSFO", new String[] {"<ObjectStructuredFieldOffset offsetLow=\"",
        "\" offsetHigh=\"", "\"/>"});
    register("OSFE", new String[] {"<ObjectStructuredFieldExtent numberOfSFLow=\"",
        "\" numberOfSFHigh=\"", "\"/>"});
    register("OO", new String[] {"<ObjectOffset objectType=\"", "\" reserved3=\"",
        "\" nrOfPrecedingObjectsLow=\"", "\" nrOfPrecedingObjectsHigh=\"", "\"/>"});
    register("FHSF", new String[] {"<FontHorizontalScaleFactor horizontalScaleFactor=\"",
        "\"/>"});
    register("MOR", new String[] {"<MediumOrientation mediumOrientation=\"", "\"/>"});
    register("TS", new String[] {"<TonerSaver reserved2=\"", "\" tonerSaverFunction=\"", "\"/>"});
    register("FRAMT", new String[] {"<FontResolutionAndMetricTechnology metricTechnology=\"",
        "\" unitBase=\"", "\" unitsPerUnitBase=\"", "\"/>"});
    register("CMRD", new String[] {"<ColorManagementResourceDescriptor reserved2=\"",
        "\" cmrProcessingMode=\"", "\" cmrScope=\"", "\"/>"});
    register("OCPSS", new String[] {"<ObjectContainerPresentationSpaceSize pdfPresentationSpace=\"",
        "\"/>"});
    register("PPI", new String[] {"<PagePositionInformation repeatingGroupNumber=\"", "\"/>"});
    register("TO", new String[] {"<TextOrientation xOrientation=\"", "\" yOrientation=\"",
        "\"/>"});
    register("LDOPM", new String[] {"<LineDataObjectPositionMigration locationAndOrientation=\"",
        "\"/>"});
    register("KGT", new String[] {"<KeepGroupTogether grpFnct=\"", "\"/>"});
    register("FCGCS", new String[] {
        "<FontCodedGraphicCharacterSetGlobalID codedGraphicCharacterSetGlobalID=\"",
        "\" codePageGlobalID=\"", "\"/>"});
    register("MFS", new String[] {"<MODCAFunctionSet fctSetID=\"", "\"/>"});
    register("AD", new String[] {"<AreaDefinition reserved2=\"", "\" xOrigin=\"", "\" yOrigin=\"",
        "\" xSize=\"", "\" ySize=\"", "\"/>"});
    register("OCNT", new String[] {"<ObjectCount subordinateObjectType=\"", "\" reserved3=\"",
        "\" numberOfObjectsLow=\"", "\" numberOfObjectsHigh=\"", "\"/>"});
    register("LODTS", new String[] {"<LocalObjectDateAndTimeStamp dateAndTimeStampType=\"",
        "\" hundreds=\"", "\" tens=\"", "\" dayOfYear=\"", "\" hourOfDay=\"", "\" minuteOfHour=\"",
        "\" secondOfMinute=\"", "\" hundredthOfSecond=\"", "\"/>"});
    register("UDTS", new String[] {"<UniversalDateAndTimeStamp reserved2=\"", "\" year=\"",
        "\" monthOfYear=\"", "\" dayOfMonth=\"", "\" hourOfDay=\"", "\" minuteOfHour=\"",
        "\" secondOfMinute=\"", "\" timeZone=\"", "\" diffHours=\"", "\" diffMinutes=\"", "\"/>"});

    // Structured Fields
    register("PGP1", new String[] {"<PGP_PagePosition_Format1 xOrigin=\"", "\" yOrigin=\"",
        "\"/>"});
    register("PTD1", new String[] {
        "<PTD_PresentationTextDataDescriptor_Format1 xUnitBase=\"", "\" yUnitBase=\"",
        "\" xUnitsPerUnitBase=\"", "\" yUnitsPerUnitBase=\"", "\" xSize=\"", "\" ySize=\"",
        "\" reserved10_11=\"", "\"/>"});

    // PTOCA Control Sequences
    register("SEC", new String[] {"<SEC_SetExtendedTextColor colorSpace=\"",
        "\" nrOfBitsComponent1=\"", "\" nrOfBitsComponent2=\"", "\" nrOfBitsComponent3=\"",
        "\" nrOfBitsComponent4=\"", "\" colorValue=\"", "\"/>"});
  }

  /**
   * Registers a mnemonic and its XML fragments.
   *
   * @param mnemonic the mnemonic
   * @param parts the XML fragments
   */
  private static void register(String mnemonic, String[] parts) {
    byte[][] fragments = new byte[parts.length][];
    for (int i = 0; i < parts.length; i++) {
      fragments[i] = parts[i].getBytes(StandardCharsets.UTF_8);
    }
    TEMPLATES.put(mnemonic, new XmlTemplate(fragments));
  }

  /**
   * Returns the template for the given mnemonic.
   *
   * @param mnemonic the mnemonic
   * @return the template
   */
  public static XmlTemplate getTemplate(String mnemonic) {
    return TEMPLATES.get(mnemonic);
  }
}
