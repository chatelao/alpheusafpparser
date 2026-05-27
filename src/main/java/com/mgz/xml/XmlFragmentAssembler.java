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

package com.mgz.xml;

import com.mgz.afp.base.handler.FragmentAssembler;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;

/**
 * Implementation of {@link FragmentAssembler} that strips the &lt;AfpFragments&gt; tags
 * from XML fragments.
 */
public class XmlFragmentAssembler implements FragmentAssembler {

  private static final byte[] START_TAG = "<AfpFragments>".getBytes(StandardCharsets.UTF_8);
  private static final byte[] END_TAG = "</AfpFragments>".getBytes(StandardCharsets.UTF_8);

  @Override
  public ByteBuffer assemble(ByteBuffer data) {
    if (data == null || !data.hasRemaining()) return data;

    int startIdx = -1;
    int len = data.remaining();
    int pos = data.position();

    // Search for start tag
    for (int i = 0; i <= len - START_TAG.length; i++) {
      boolean match = true;
      for (int j = 0; j < START_TAG.length; j++) {
        if (data.get(pos + i + j) != START_TAG[j]) {
          match = false;
          break;
        }
      }
      if (match) {
        startIdx = i + START_TAG.length;
        break;
      }
    }

    if (startIdx == -1) return data;

    int endIdx = -1;
    // Search for end tag from the end
    for (int i = len - END_TAG.length; i >= startIdx; i--) {
      boolean match = true;
      for (int j = 0; j < END_TAG.length; j++) {
        if (data.get(pos + i + j) != END_TAG[j]) {
          match = false;
          break;
        }
      }
      if (match) {
        endIdx = i;
        break;
      }
    }

    if (endIdx == -1) return data;

    data.position(pos + startIdx);
    data.limit(pos + endIdx);
    return data.slice();
  }
}
