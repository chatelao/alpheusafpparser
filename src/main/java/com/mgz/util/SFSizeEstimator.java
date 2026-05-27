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

package com.mgz.util;

import com.mgz.afp.base.StructuredField;
import com.mgz.afp.ptoca.PTX_PresentationTextData;

/**
 * Provides size estimation logic for AFP-to-XML conversion.
 * This is foundational for Memory-Mapped I/O and pre-allocation strategies.
 */
public class SFSizeEstimator {

  // Heuristic multipliers based on initial analysis of typical AFP structured fields
  private static final double DEFAULT_MULTIPLIER = 5.0;
  private static final double PTX_MULTIPLIER = 12.0; // PTX contains many control sequences
  private static final double GAD_MULTIPLIER = 8.0;  // Graphics data can be verbose in XML
  private static final double IOCA_MULTIPLIER = 4.0; // Image data is usually hex-encoded, ~2x + tags
  private static final double MCF_MULTIPLIER = 10.0; // Mapping fields have many sub-elements

  /**
   * Estimates the size of the XML representation of a structured field.
   *
   * @param sf the structured field to estimate
   * @return the estimated XML size in bytes
   */
  public static long estimateXmlSize(StructuredField sf) {
    if (sf == null || sf.getStructuredFieldIntroducer() == null) {
      return 0;
    }

    if (sf instanceof PTX_PresentationTextData ptx && PTXPerformanceMonitor.hasData()) {
      long dynamic = estimateDynamicPtxSize(ptx);
      if (dynamic > 0) return dynamic;
    }

    int afpSize = sf.getStructuredFieldIntroducer().getSFLength();
    double multiplier = DEFAULT_MULTIPLIER;

    if (sf instanceof PTX_PresentationTextData) {
      multiplier = PTXPerformanceMonitor.hasData() ?
          PTXPerformanceMonitor.getGlobalPtxExpansionRatio() : PTX_MULTIPLIER;
    } else if (sf.getClass().getSimpleName().startsWith("GAD_")) {
      multiplier = GAD_MULTIPLIER;
    } else if (sf.getClass().getSimpleName().startsWith("IPD_") ||
               sf.getClass().getSimpleName().startsWith("IDD_")) {
      multiplier = IOCA_MULTIPLIER;
    } else if (sf.getClass().getSimpleName().startsWith("MCF_") ||
               sf.getClass().getSimpleName().startsWith("MIO_")) {
      multiplier = MCF_MULTIPLIER;
    }

    return (long) (afpSize * multiplier);
  }

  private static long estimateDynamicPtxSize(PTX_PresentationTextData ptx) {
    var sequences = ptx.getControlSequences();
    if (sequences == null || sequences.isEmpty()) {
      return 0;
    }

    double totalEstimatedXmlSize = 0;
    double globalPtxRatio = PTXPerformanceMonitor.getGlobalPtxExpansionRatio();
    if (globalPtxRatio <= 0) globalPtxRatio = PTX_MULTIPLIER;

    for (var cs : sequences) {
      String name = cs.getClass().getSimpleName();
      double ratio = PTXPerformanceMonitor.getGlobalPtocaExpansionRatio(name);
      if (ratio <= 0) ratio = globalPtxRatio;

      int payloadSize = 0;
      if (cs instanceof com.mgz.afp.ptoca.controlSequence.PTOCAControlSequence.GraphicCharacters gc) {
        payloadSize = gc.getData() != null ? gc.getData().length : 0;
      } else if (cs.getCsi() != null) {
        payloadSize = Math.max(0, cs.getCsi().getLength() - 2);
      }
      totalEstimatedXmlSize += (payloadSize * ratio) + 100; // +100 for tags and indentation
    }

    return (long) totalEstimatedXmlSize;
  }

  /**
   * Estimates the size of the XML representation of a raw AFP payload.
   *
   * @param afpSize the size of the AFP payload in bytes
   * @return the estimated XML size in bytes
   */
  public static long estimateXmlSize(int afpSize) {
    return estimateXmlSize((long) afpSize);
  }

  /**
   * Estimates the size of the XML representation of a raw AFP payload.
   *
   * @param afpSize the size of the AFP payload in bytes
   * @return the estimated XML size in bytes
   */
  public static long estimateXmlSize(long afpSize) {
    // Use a slightly conservative average multiplier for unknown content
    return (long) (afpSize * 8.0);
  }
}
