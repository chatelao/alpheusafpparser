/*
Copyright 2026 Alpheus AFP Parser Authors

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

package com.mgz.afp.ipds;

import com.mgz.afp.base.IHasTriplets;
import com.mgz.afp.base.annotations.AFPField;
import com.mgz.afp.exceptions.AFPParserException;
import com.mgz.afp.parser.AFPParserConfiguration;
import com.mgz.afp.parser.TripletParser;
import com.mgz.afp.triplets.Triplet;
import com.mgz.util.UtilBinaryDecoding;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * IPDS Include Saved Page (ISP) Command (X'D67E').
 * Refer to IPDS-4-010, IPDS-4-334.
 */
public class ISP_IncludeSavedPage extends IPDSCommand implements IHasTriplets {

  @AFPField
  private long pageSequenceNumber;

  @AFPField(isOptional = true)
  private List<Triplet> triplets;

  @Override
  public void decodeAFP(byte[] sfData, int offset, int length, AFPParserConfiguration config)
      throws AFPParserException {
    int actualLength = getActualLength(sfData, offset, length);
    int consumed = decodeIPDSHeader(sfData, offset, actualLength);

    checkDataLength(sfData, offset + consumed, actualLength - consumed, 4);
    int start = offset + consumed;

    this.pageSequenceNumber = UtilBinaryDecoding.parseLong(sfData, start, 4);

    if (actualLength - consumed > 4) {
      this.triplets = TripletParser.parseTriplets(sfData, start + 4, actualLength - consumed - 4, config);
    }
  }

  @Override
  public void writeAFP(OutputStream os, AFPParserConfiguration config) throws IOException {
    ByteArrayOutputStream baos = new ByteArrayOutputStream();
    writeIPDSHeader(baos);
    baos.write(UtilBinaryDecoding.longToByteArray(pageSequenceNumber, 4));
    if (triplets != null) {
      for (Triplet t : triplets) {
        t.writeAFP(baos, config);
      }
    }
    writeFullStructuredField(os, baos.toByteArray());
  }

  public long getPageSequenceNumber() {
    return pageSequenceNumber;
  }

  public void setPageSequenceNumber(long pageSequenceNumber) {
    this.pageSequenceNumber = pageSequenceNumber;
  }

  @Override
  public List<Triplet> getTriplets() {
    return triplets;
  }

  @Override
  public void setTriplets(List<Triplet> triplets) {
    this.triplets = triplets;
  }

  @Override
  public void addTriplet(Triplet triplet) {
    if (this.triplets == null) {
      this.triplets = new ArrayList<>();
    }
    this.triplets.add(triplet);
  }

  @Override
  public void removeTriplet(Triplet triplet) {
    if (this.triplets != null) {
      this.triplets.remove(triplet);
    }
  }
}
