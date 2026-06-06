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

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.itextpdf.kernel.pdf.PdfArray;
import com.itextpdf.kernel.pdf.PdfDictionary;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfName;
import com.itextpdf.kernel.pdf.PdfObject;
import com.itextpdf.kernel.pdf.PdfReader;
import com.itextpdf.kernel.pdf.PdfString;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Utility for extracting PDF/VT DPart metadata from a PDF file.
 */
public class PdfMetadataExtractor {

  private static final PdfName DPART_ROOT = new PdfName("DPartRoot");
  private static final PdfName DTREE = new PdfName("DTree");
  private static final PdfName DPARTS = new PdfName("DParts");
  private static final PdfName PROPERTY = new PdfName("Property");

  /**
   * Extracts metadata from the given PDF input stream.
   *
   * @param is the PDF input stream
   * @return a list of maps, where each map represents a DPart node's properties
   * @throws IOException if an error occurs while reading the PDF
   */
  public List<Map<String, String>> extractMetadata(InputStream is) throws IOException {
    try (PdfReader reader = new PdfReader(is);
        PdfDocument pdfDoc = new PdfDocument(reader)) {
      return extractMetadata(pdfDoc);
    }
  }

  /**
   * Extracts metadata from the given PDF document.
   *
   * @param pdfDoc the PDF document
   * @return a list of maps, where each map represents a DPart node's properties
   */
  public List<Map<String, String>> extractMetadata(PdfDocument pdfDoc) {
    List<Map<String, String>> metadata = new ArrayList<>();
    PdfDictionary catalog = pdfDoc.getCatalog().getPdfObject();
    PdfDictionary dpartRoot = catalog.getAsDictionary(DPART_ROOT);

    if (dpartRoot != null) {
      PdfDictionary dtree = dpartRoot.getAsDictionary(DTREE);
      if (dtree != null) {
        traverseDPart(dtree, metadata, new LinkedHashMap<>());
      }
    }

    return metadata;
  }

  private void traverseDPart(PdfDictionary dpart, List<Map<String, String>> metadata, Map<String, String> inheritedProperties) {
    Map<String, String> currentProperties = new LinkedHashMap<>(inheritedProperties);

    PdfDictionary propertiesDict = dpart.getAsDictionary(PROPERTY);
    if (propertiesDict != null) {
      for (PdfName key : propertiesDict.keySet()) {
        PdfObject value = propertiesDict.get(key);
        if (value instanceof PdfString ps) {
          currentProperties.put(key.getValue(), ps.toUnicodeString());
        }
      }
    }

    PdfArray children = dpart.getAsArray(DPARTS);
    if (children != null) {
      for (int i = 0; i < children.size(); i++) {
        PdfDictionary child = children.getAsDictionary(i);
        if (child != null) {
          traverseDPart(child, metadata, currentProperties);
        }
      }
    } else {
      // Leaf node, add to list if it has properties or we want to represent all leaves
      metadata.add(currentProperties);
    }
  }

  /**
   * Serializes the extracted metadata to a JSON string.
   *
   * @param metadata the list of metadata maps
   * @return the JSON string
   * @throws IOException if an error occurs during serialization
   */
  public String toJson(List<Map<String, String>> metadata) throws IOException {
    ObjectMapper mapper = new ObjectMapper();
    mapper.enable(SerializationFeature.INDENT_OUTPUT);
    return mapper.writeValueAsString(metadata);
  }
}
