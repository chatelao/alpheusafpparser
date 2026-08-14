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
import com.mgz.afp.modca.NOP_NoOperation;
import com.mgz.util.Constants;
import java.util.EnumSet;

/**
 * Fluent builder for No Operation (NOP) structured fields.
 */
public class NopBuilder implements AfpFieldBuilder<NOP_NoOperation> {
  private byte[] data;

  public NopBuilder withData(byte[] data) {
    this.data = data;
    return this;
  }

  public NopBuilder withComment(String comment) {
    if (comment != null) {
      this.data = comment.getBytes(Constants.cpIBM500);
    } else {
      this.data = null;
    }
    return this;
  }

  @Override
  public NOP_NoOperation build() {
    NOP_NoOperation nop = new NOP_NoOperation();

    StructuredFieldIntroducer sfi = new StructuredFieldIntroducer();
    sfi.setSFTypeID(SFTypeID.NOP_NoOperation);
    sfi.setFlagByte(EnumSet.noneOf(SFFlag.class));
    sfi.setPooled(false);
    nop.setStructuredFieldIntroducer(sfi);

    if (data != null) {
      nop.setData(data);
    }
    nop.setPooled(false);

    return nop;
  }
}
