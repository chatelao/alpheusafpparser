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
import com.mgz.afp.exceptions.AFPParserException;
import com.mgz.afp.parser.AFPParserConfiguration;
import com.mgz.util.Constants;
import com.mgz.util.UtilCharacterEncoding;

import javax.xml.bind.annotation.XmlElement;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.Charset;

/**
 * Base class for {@link StructuredField}s that consists only of opaque data.
 */
public class StructuredFieldBaseData extends StructuredField {
  @AFPField(maxSize = 32759)
  protected byte[] data;

  @XmlElement(name = "text")
  public String getText() {
    if (data == null || data.length == 0) {
      return null;
    }
    Charset charset = Constants.cpIBM500;
    if (getStructuredFieldIntroducer() != null && getStructuredFieldIntroducer().getActualConfig() != null) {
      charset = getStructuredFieldIntroducer().getActualConfig().getAfpCharSet();
    }
    if (UtilCharacterEncoding.isHumanReadable(data, charset)) {
      return new String(data, charset);
    }
    return null;
  }

  @Override
  public void decodeAFP(byte[] sfData, int offset, int length, AFPParserConfiguration config) throws AFPParserException {
    int actualLength = getActualLength(sfData, offset, length);
    if (actualLength > 0) {
      data = new byte[actualLength];
      System.arraycopy(sfData, offset, data, 0, actualLength);
    } else {
      data = null;
    }
  }


  @Override
  public void writeAFP(OutputStream os, AFPParserConfiguration config) throws IOException {
    writeFullStructuredField(os, data);
  }

  public byte[] getData() {
    return data;
  }

  public void setData(byte[] data) {
    this.data = data;
  }

}
