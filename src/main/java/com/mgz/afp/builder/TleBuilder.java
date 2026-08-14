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

package com.mgz.afp.builder;

import com.mgz.afp.base.StructuredFieldIntroducer;
import com.mgz.afp.enums.SFFlag;
import com.mgz.afp.enums.SFTypeID;
import com.mgz.afp.modca.TLE_TagLogicalElement;
import com.mgz.afp.triplets.Triplet;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;

/**
 * Fluent builder for Tag Logical Element (TLE) structured fields.
 */
public class TleBuilder implements AfpFieldBuilder<TLE_TagLogicalElement> {
  private String attributeName;
  private String attributeValue;

  public TleBuilder withAttribute(String name, String value) {
    if (name == null || name.isEmpty()) {
      throw new IllegalArgumentException("Attribute name cannot be null or empty.");
    }
    this.attributeName = name;
    this.attributeValue = value;
    return this;
  }

  @Override
  public TLE_TagLogicalElement build() {
    TLE_TagLogicalElement tle = new TLE_TagLogicalElement();

    StructuredFieldIntroducer sfi = new StructuredFieldIntroducer();
    sfi.setSFTypeID(SFTypeID.TLE_TagLogicalElement);
    sfi.setFlagByte(EnumSet.noneOf(SFFlag.class));
    sfi.setPooled(false);
    tle.setStructuredFieldIntroducer(sfi);

    List<Triplet> triplets = new ArrayList<>();

    // Attribute Name Triplet (AttributeGID FQN)
    Triplet.FullyQualifiedName nameTriplet = new Triplet.FullyQualifiedName();
    nameTriplet.setTripletID(Triplet.TripletID.FullyQualifiedName);
    nameTriplet.setType(Triplet.GlobalID_Use.AttributeGID);
    nameTriplet.setNameAsString(attributeName);
    nameTriplet.setFormat(Triplet.GlobalID_Format.CharacterString);
    nameTriplet.setPooled(false);
    triplets.add(nameTriplet);

    // Attribute Value Triplet
    if (attributeValue != null) {
      Triplet.AttributeValue valueTriplet = new Triplet.AttributeValue();
      valueTriplet.setTripletID(Triplet.TripletID.AttributeValue);
      valueTriplet.setAttributeValue(attributeValue);
      valueTriplet.setPooled(false);
      triplets.add(valueTriplet);
    }

    tle.setTriplets(triplets);
    tle.setPooled(false);

    return tle;
  }
}
