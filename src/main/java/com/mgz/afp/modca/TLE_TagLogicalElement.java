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
package com.mgz.afp.modca;

import com.mgz.afp.base.StructuredFieldBaseTriplets;
import com.mgz.afp.triplets.Triplet;

import javax.xml.bind.annotation.XmlElement;

public class TLE_TagLogicalElement extends StructuredFieldBaseTriplets {

  @XmlElement(name = "text")
  public String getText() {
    if (triplets != null) {
      for (Triplet triplet : triplets) {
        if (triplet instanceof Triplet.AttributeValue) {
          return ((Triplet.AttributeValue) triplet).getAttributeValue();
        }
      }
    }
    return null;
  }
}
