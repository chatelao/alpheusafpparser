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
package com.mgz.afp.lineData;

import com.mgz.afp.base.StructuredFieldBaseData;
import com.mgz.afp.exceptions.AFPParserException;
import com.mgz.afp.parser.AFPParserConfiguration;

import java.io.IOException;
import java.io.OutputStream;

/**
 * Programming Guide and Line Data Reference (ha3l3r04.pdf), page 87<br> <br>
 */
public class DXD_DataMapTransmitionSubcaseDescriptor extends StructuredFieldBaseData {

  byte[] constantData = new byte[] {0x00, 0x01, 0x00, (byte) 0xFF};

  @Override
  public void decodeAFP(byte[] sfData, int offset, int length, AFPParserConfiguration config) throws AFPParserException {
    int actualLength = getActualLength(sfData, offset, length);
    if (actualLength >= 4) {
      constantData = new byte[4];
      System.arraycopy(sfData, offset, constantData, 0, 4);
    }
  }

  @Override
  public void writeAFP(OutputStream os, AFPParserConfiguration config) throws IOException {
    writeFullStructuredField(os, constantData);
  }

  public byte[] getConstantData() {
    return constantData;
  }

  public void setConstantData(byte[] constantData) {
    this.constantData = constantData;
  }
}
