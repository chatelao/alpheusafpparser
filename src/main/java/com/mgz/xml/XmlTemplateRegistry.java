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
    // PTOCA Control Sequences (Migrated to manual StAX2)

    // Triplets
    register("AQ", new String[] {
        "<AttributeQualifier sequenceNumber=\"", "\" levelNumber=\"", "\"/>"});
    register("RLI", new String[] {
        "<ResourceLocalIdentifier resourceType=\"", "\" resourceLocalID=\"", "\"/>"});
    register("CR", new String[] {"<CharacterRotation characterRotation=\"", "\"/>"});
    register("MU", new String[] {
        "<MeasurementUnits xUnitBase=\"", "\" yUnitBase=\"", "\" xUnitsPerUnitbase=\"",
        "\" yUnitsPerUnitbase=\"", "\"/>"});
    register("MO", new String[] {"<MappingOption dataObjecMapingOption=\"", "\"/>"});
    register("OAS", new String[] {
        "<ObjectAreaSize sizeType_0x02=\"", "\" xSize=\"", "\" ySize=\"", "\"/>"});
    register("CGCS", new String[] {
        "<CodedGraphicCharacterSetGlobalID graphicCharacterSetGlobalID=\"",
        "\" codePageGlobalID_codedCharacterSetID=\"", "\"/>"});
    register("ROT", new String[] {"<ResourceObjectType objectType=\"", "\"/>"});
    register("DP", new String[] {"<DescriptorPosition objectAreaDescriptorID=\"", "\"/>"});
    register("MEC", new String[] {
        "<MediaEjectControl reserved2=\"", "\" mediaEjectControl=\"", "\"/>"});
    register("RUA", new String[] {"<ResourceUsageAttribute frequencyOfUse=\"", "\"/>"});
    register("PSRM", new String[] {
        "<PresentationSpaceResetMixing backgroundMixingFlag=\"", "\"/>"});
    register("ERLI", new String[] {
        "<ExtendedResourceLocalIdentifier resourceType=\"", "\" extendedResourceLocalID=\"",
        "\"/>"});
    register("RSN", new String[] {"<ResourceSectionNumber resourceSectionNumber=\"", "\"/>"});
    register("MMPN", new String[] {"<MediumMapPageNumber pageNumber=\"", "\"/>"});
    register("OBE", new String[] {
        "<ObjectByteExtent byteExtentLow=\"", "\" byteExtentHigh=\"", "\"/>"});
    register("FHSF", new String[] {"<FontHorizontalScaleFactor horizontalScaleFactor=\"", "\"/>"});
    register("MOR", new String[] {"<MediumOrientation mediumOrientation=\"", "\"/>"});
    register("TS", new String[] {"<TonerSaver reserved2=\"", "\" tonerSaverFunction=\"", "\"/>"});
    register("FRAMT", new String[] {
        "<FontResolutionAndMetricTechnology metricTechnology=\"", "\" unitBase=\"",
        "\" unitsPerUnitBase=\"", "\"/>"});
    register("CMRD", new String[] {
        "<ColorManagementResourceDescriptor reserved2=\"", "\" cmrProcessingMode=\"",
        "\" cmrScope=\"", "\"/>"});
    register("OCPSS", new String[] {
        "<ObjectContainerPresentationSpaceSize pdfPresentationSpace=\"", "\"/>"});
    register("PPI", new String[] {"<PagePositionInformation repeatingGroupNumber=\"", "\"/>"});
    register("TO", new String[] {
        "<TextOrientation xOrientation=\"", "\" yOrientation=\"", "\"/>"});
    register("LDOPM", new String[] {"<LineDataObjectPositionMigration locationAndOrientation=\"", "\"/>"});
    register("KGT", new String[] {"<KeepGroupTogether grpFnct=\"", "\"/>"});
    register("FCGCS", new String[] {
        "<FontCodedGraphicCharacterSetGlobalID codedGraphicCharacterSetGlobalID=\"",
        "\" codePageGlobalID=\"", "\"/>"});
    register("MFS", new String[] {"<MODCAFunctionSet fctSetID=\"", "\"/>"});
    register("AD", new String[] {
        "<AreaDefinition reserved2=\"", "\" xOrigin=\"", "\" yOrigin=\"", "\" xSize=\"",
        "\" ySize=\"", "\"/>"});
    register("OCNT", new String[] {
        "<ObjectCount subordinateObjectType=\"", "\" reserved3=\"", "\" numberOfObjectsLow=\"",
        "\"", "/>"});
    register("LODTS", new String[] {
        "<LocalObjectDateAndTimeStamp dateAndTimeStampType=\"", "\" hundreds=\"", "\" tens=\"",
        "\" dayOfYear=\"", "\" hourOfDay=\"", "\" minuteOfHour=\"", "\" secondOfMinute=\"",
        "\" hundredthOfSecond=\"", "\"/>"});
    register("UDTS", new String[] {
        "<UniversalDateAndTimeStamp reserved2=\"", "\" year=\"", "\" monthOfYear=\"",
        "\" dayOfMonth=\"", "\" hourOfDay=\"", "\" minuteOfHour=\"", "\" secondOfMinute=\"",
        "\" timeZone=\"", "\" diffHours=\"", "\" diffMinutes=\"", "\"/>"});
    register("OBO", new String[] {"<ObjectByteOffset byteOffset=\"", "\"", "/>"});
    register("OSFO", new String[] {"<ObjectStructuredFieldOffset offsetLow=\"", "\"", "/>"});
    register("OSFE", new String[] {
        "<ObjectStructuredFieldExtent numberOfSFLow=\"", "\"", "/>"});
    register("OO", new String[] {
        "<ObjectOffset", " reserved3=\"", "\" nrOfPrecedingObjectsLow=\"", "\"", "/>"});

    // Structured Fields

    // PTOCA Control Sequences (Migrated to manual StAX2)
  }

  private static void register(String mnemonic, String[] parts) {
    byte[][] fragments = new byte[parts.length][];
    for (int i = 0; i < parts.length; i++) {
      fragments[i] = parts[i].getBytes(StandardCharsets.UTF_8);
    }
    TEMPLATES.put(mnemonic, new XmlTemplate(fragments));
  }

  public static XmlTemplate getTemplate(String mnemonic) {
    return TEMPLATES.get(mnemonic);
  }
}
