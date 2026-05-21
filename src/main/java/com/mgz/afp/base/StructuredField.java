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

package com.mgz.afp.base;

import com.mgz.afp.base.annotations.AFPField;
import com.mgz.afp.base.annotations.AFPType;
import com.mgz.afp.bcoca.BBC_BeginBarCodeObject;
import com.mgz.afp.bcoca.EBC_EndBarCodeObject;
import com.mgz.afp.enums.SFFlag;
import com.mgz.afp.enums.SFType;
import com.mgz.afp.exceptions.AFPParserException;
import com.mgz.afp.exceptions.IAFPDecodeableWriteable;
import com.mgz.afp.modca.BPG_BeginPage;
import com.mgz.afp.modca.EPG_EndPage;
import com.mgz.afp.parser.AFPParserConfiguration;
import com.mgz.afp.goca.DrawingOrderPool;
import com.mgz.afp.goca.GAD_DrawingOrder;
import com.mgz.afp.ioca.IPD_Segment;
import com.mgz.afp.ioca.IpdSegmentPool;
import com.mgz.afp.ptoca.controlSequence.ControlSequencePool;
import com.mgz.afp.ptoca.controlSequence.PTOCAControlSequence;
import com.mgz.afp.triplets.Triplet;
import com.mgz.afp.triplets.TripletPool;
import com.mgz.util.Constants;
import com.mgz.util.UtilBinaryDecoding;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;

/**
 * Base class for all {@link StructuredField}s.
 */
@AFPType
public abstract class StructuredField implements IAFPDecodeableWriteable {

  @AFPField
  StructuredFieldIntroducer structuredFieldIntroducer;
  /**
   * The structured field's padding data. Contains null if this structured field has no padding
   * data.
   */
  @AFPField(isOptional = true, maxSize = 32759)
  byte[] padding;

  /**
   * Resets the structured field to its initial state for reuse in an object pool.
   */
  public void reset() {
    structuredFieldIntroducer = null;
    padding = null;
  }

  public static void checkDataLength(byte[] sfData, int offset, int length, int minLength) throws AFPParserException {
    if (length == -1) {
      length = sfData.length - offset;
    }
    if (sfData == null || sfData.length == 0 || offset >= sfData.length) {
      throw new AFPParserException("Offset is greater than the size of the given data.");
    }
    if (length > 0 && offset + length > sfData.length) {
      throw new AFPParserException("The specified range is greater than the size of the given data.");
    }
    if (minLength > 0 && offset + minLength > sfData.length) {
      throw new AFPParserException("The given data array is to small to contain enough data for decoding.");
    }
    if (length >= 0 && minLength >= 0 && length < minLength) {
      throw new AFPParserException("The specified length of used data array is to small to contain enough data for decoding.");
    }
  }

  /**
   * Releases the {@link StructuredFieldIntroducer} and any associated {@link Triplet}s of this
   * structured field back to their respective pools. After calling this method, the
   * {@link StructuredFieldIntroducer} and triplet references of this structured field will be null.
   */
  public void release() {
    com.mgz.afp.enums.SFTypeID type =
        structuredFieldIntroducer != null ? structuredFieldIntroducer.getSFTypeID() : null;

    if (this instanceof IHasTriplets iHasTriplets) {
      java.util.List<Triplet> triplets = iHasTriplets.getTriplets();
      if (triplets != null) {
        for (Triplet triplet : triplets) {
          TripletPool.release(triplet);
        }
        iHasTriplets.setTriplets(null);
      }
    }

    if (this instanceof IHasRepeatingGroups iHasRepeatingGroups) {
      java.util.List<IRepeatingGroup> groups = iHasRepeatingGroups.getRepeatingGroups();
      if (groups != null) {
        for (IRepeatingGroup group : groups) {
          if (group instanceof IHasTriplets iHasTripletsGroup) {
            java.util.List<Triplet> triplets = iHasTripletsGroup.getTriplets();
            if (triplets != null) {
              for (Triplet triplet : triplets) {
                TripletPool.release(triplet);
              }
              iHasTripletsGroup.setTriplets(null);
            }
          }
        }
      }
    }

    if (this instanceof com.mgz.afp.modca.MCF_MapCodedFont_Format1 mcf1) {
      java.util.List<com.mgz.afp.modca.MCF_MapCodedFont_Format1.MCF_RepeatingGroup> groups = mcf1.getRepeatingGroups();
      if (groups != null) {
        groups.clear();
      }
    }

    if (this instanceof com.mgz.afp.modca.MCC_MediumCopyCount mcc) {
      java.util.List<com.mgz.afp.modca.MCC_MediumCopyCount.MCC_RepeatingGroup> groups = mcc.getRepeatingGroups();
      if (groups != null) {
        groups.clear();
      }
    }

    if (structuredFieldIntroducer != null) {
      SfiPool.release(structuredFieldIntroducer);
      structuredFieldIntroducer = null;
    }

    StructuredFieldPool.release(this, type);
  }

  /**
   * Returns the actual length of data to process contained in sfData.
   *
   * @param sfData the data array
   * @param offset the offset in the array
   * @param length the specified length, or -1 to use remaining data
   * @return the actual length to process
   */
  public static int getActualLength(byte[] sfData, int offset, int length) {
    return length != -1 ? length : sfData.length - offset;
  }

  public abstract void decodeAFP(byte[] sfData, int offset, int length, AFPParserConfiguration config) throws AFPParserException;

  public abstract void writeAFP(OutputStream os, AFPParserConfiguration config) throws IOException;

  /**
   * Returns the {@link StructuredFieldIntroducer} of this structured field.
   *
   * @return the {@link StructuredFieldIntroducer} of this structured field.
   */
  public StructuredFieldIntroducer getStructuredFieldIntroducer() {
    return structuredFieldIntroducer;
  }

  /**
   * Sets the {@link StructuredFieldIntroducer} of this structured field.
   *
   * @param structuredFieldIntroducer the {@link StructuredFieldIntroducer} of this structured
   *                                  field.
   */
  public void setStructuredFieldIntroducer(
      StructuredFieldIntroducer structuredFieldIntroducer) {
    this.structuredFieldIntroducer = structuredFieldIntroducer;
  }

  /**
   * Returns the padding bytes of this structured field, or null if this structured field has no
   * padding bytes.
   *
   * @return padding bytes of this structured field, or null if this structured field has no padding
   * bytes.
   */
  public byte[] getPadding() {
    return padding;
  }

  /**
   * Sets the padding bytes of this structured field and set the padding flag.
   * <p>
   * If the given padding is null the padding flag is revoked.
   *
   * @param padding the padding bytes of this structured field, may be null.
   */
  public void setPadding(byte[] padding) {
    this.padding = padding;
    if (padding != null && padding.length == 0) {
      padding = null;
    }
    if (padding != null) {
      structuredFieldIntroducer.setFlag(SFFlag.isPadded);
    } else {
      structuredFieldIntroducer.removeFlag(SFFlag.isPadded);
    }
  }

  /**
   * Writes out the SFI, the given net payload, and padding data.
   * <p>
   * Sets the length byte[0,1] of resulting SF Data and updates the {@link
   * StructuredFieldIntroducer#sfLength}.
   *
   * @param os                             {@link OutputStream} to write to.
   * @param netPayloadWithoutSFIandPadding the net payload without SFI and padding data. May be null
   *                                       or empty array.
   * @throws IOException if writing to the given {@link OutputStream} fails.
   */
  protected void writeFullStructuredField(OutputStream os, byte[] netPayloadWithoutSFIandPadding) throws IOException {
    ByteArrayOutputStream baos = new ByteArrayOutputStream();

    baos.write(this.structuredFieldIntroducer.toBytes());

    if (netPayloadWithoutSFIandPadding != null && netPayloadWithoutSFIandPadding.length > 0) {
      baos.write(netPayloadWithoutSFIandPadding);
    }

    if (padding != null) {
      baos.write(padding);
    }

    byte[] sfData = baos.toByteArray();
    byte[] lenBytes = UtilBinaryDecoding.intToByteArray(sfData.length, 2);
    for (int i = 0; i < lenBytes.length; i++) {
      sfData[i] = lenBytes[i];
    }
    structuredFieldIntroducer.setSFLength(sfData.length);

    os.write(Constants.AFPBeginByte_0xA5);
    os.write(sfData);

  }

  /**
   * Returns true, if this structured field indicates the begin a complex structured field that may
   * contain other structured fields. Returns false, if this structured field is not the begin a
   * complex structured field.
   * <p>
   * Examples: {@link BPG_BeginPage}, {@link
   * BBC_BeginBarCodeObject},
   *
   * @return true, if this structured field indicates the begin a complex structured field, false
   * otherwise.
   */
  public boolean isBeginSF() {
    if (structuredFieldIntroducer == null || structuredFieldIntroducer.getSFTypeID() == null || structuredFieldIntroducer.getSFTypeID().getSfType() == null) {
      return false;
    } else {
      return structuredFieldIntroducer.getSFTypeID().getSfType() == SFType.Begin;
    }
  }

  /**
   * Returns true, if this structured field indicates the end of a complex structured field that may
   * contain other structured fields. Returns false, if this structured field is not the end a
   * complex structured field.
   * <p>
   * Examples: {@link EPG_EndPage}, {@link EBC_EndBarCodeObject},
   *
   * @return true, if this structured field indicates the end of a complex structured field, false
   * otherwise.
   */
  public boolean isEndSF() {
    if (structuredFieldIntroducer == null || structuredFieldIntroducer.getSFTypeID() == null || structuredFieldIntroducer.getSFTypeID().getSfType() == null) {
      return false;
    } else {
      return structuredFieldIntroducer.getSFTypeID().getSfType() == SFType.End;
    }
  }

  public boolean isShallow() {
    if (structuredFieldIntroducer == null || structuredFieldIntroducer.actualConfig == null) {
      return false;
    } else {
      return structuredFieldIntroducer.actualConfig.isBuildShallow();
    }
  }

  @Override
  public String toString() {
    return "StructuredField{" + structuredFieldIntroducer + '}';
  }
}
