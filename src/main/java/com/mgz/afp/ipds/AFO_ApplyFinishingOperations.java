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
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * IPDS Apply Finishing Operations (AFO) Command (X'D602').
 * Refer to IPDS-4-004, IPDS-4-276.
 */
public class AFO_ApplyFinishingOperations extends IPDSCommand implements IHasTriplets {

  @AFPField(isOptional = true)
  private List<Triplet> triplets;

  @Override
  public void decodeAFP(byte[] sfData, int offset, int length, AFPParserConfiguration config)
      throws AFPParserException {
    int actualLength = getActualLength(sfData, offset, length);
    int consumed = decodeIPDSHeader(sfData, offset, actualLength);

    if (actualLength > consumed) {
      this.triplets = TripletParser.parseTriplets(sfData, offset + consumed, actualLength - consumed, config);
    } else {
      this.triplets = null;
    }
  }

  @Override
  public void writeAFP(OutputStream os, AFPParserConfiguration config) throws IOException {
    ByteArrayOutputStream baos = new ByteArrayOutputStream();
    writeIPDSHeader(baos);
    if (triplets != null) {
      for (Triplet triplet : triplets) {
        triplet.writeAFP(baos, config);
      }
    }
    writeFullStructuredField(os, baos.toByteArray());
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
