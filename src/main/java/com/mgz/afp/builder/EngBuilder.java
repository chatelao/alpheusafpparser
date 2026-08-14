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
import com.mgz.afp.modca.ENG_EndNamedPageGroup;
import java.util.ArrayList;
import java.util.EnumSet;

/**
 * Fluent builder for End Named Page Group (ENG) structured fields.
 */
public class EngBuilder implements AfpFieldBuilder<ENG_EndNamedPageGroup> {
  private String name;

  public EngBuilder withName(String name) {
    if (name != null && name.length() > 8) {
      throw new IllegalArgumentException("Page group name cannot exceed 8 characters.");
    }
    this.name = name;
    return this;
  }

  @Override
  public ENG_EndNamedPageGroup build() {
    ENG_EndNamedPageGroup eng = new ENG_EndNamedPageGroup();

    StructuredFieldIntroducer sfi = new StructuredFieldIntroducer();
    sfi.setSFTypeID(SFTypeID.ENG_EndNamedPageGroup);
    sfi.setFlagByte(EnumSet.noneOf(SFFlag.class));
    sfi.setPooled(false);
    eng.setStructuredFieldIntroducer(sfi);

    if (name != null) {
      eng.setName(name);
    }
    eng.setTriplets(new ArrayList<>());
    eng.setPooled(false);

    return eng;
  }
}
