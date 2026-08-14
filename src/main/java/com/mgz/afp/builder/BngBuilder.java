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
import com.mgz.afp.modca.BNG_BeginNamedPageGroup;
import java.util.ArrayList;
import java.util.EnumSet;

/**
 * Fluent builder for Begin Named Page Group (BNG) structured fields.
 */
public class BngBuilder implements AfpFieldBuilder<BNG_BeginNamedPageGroup> {
  private String name;

  public BngBuilder withName(String name) {
    if (name == null || name.isEmpty()) {
      throw new IllegalArgumentException("Page group name cannot be null or empty.");
    }
    if (name.length() > 8) {
      throw new IllegalArgumentException("Page group name cannot exceed 8 characters.");
    }
    this.name = name;
    return this;
  }

  @Override
  public BNG_BeginNamedPageGroup build() {
    BNG_BeginNamedPageGroup bng = new BNG_BeginNamedPageGroup();

    StructuredFieldIntroducer sfi = new StructuredFieldIntroducer();
    sfi.setSFTypeID(SFTypeID.BNG_BeginNamedPageGroup);
    sfi.setFlagByte(EnumSet.noneOf(SFFlag.class));
    sfi.setPooled(false);
    bng.setStructuredFieldIntroducer(sfi);

    bng.setName(name);
    bng.setTriplets(new ArrayList<>());
    bng.setPooled(false);

    return bng;
  }
}
