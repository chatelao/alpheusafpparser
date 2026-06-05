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

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

/**
 * Registry for XML byte templates.
 */
public class XmlTemplateRegistry {
  private static final Map<String, XmlTemplate> TEMPLATES = new HashMap<>();

  static {
    // PTOCA Control Sequences
    register("AMI", new String[] {"<AMI_AbsoluteMoveInline displacement=\"", "\"/>"});
    register("RMI", new String[] {"<RMI_RelativeMoveInline increment=\"", "\"/>"});
    register("AMB", new String[] {"<AMB_AbsoluteMoveBaseline displacement=\"", "\"/>"});
    register("RMB", new String[] {"<RMB_RelativeMoveBaseline increment=\"", "\"/>"});
    register("SCFL", new String[] {"<SCFL_SetCodedFontLocal codedFontLocalID=\"", "\"/>"});
    register("SBI", new String[] {"<SBI_SetBaselineIncrement increment=\"", "\"/>"});
    register("SIM", new String[] {"<SIM_SetInlineMargin displacement=\"", "\"/>"});
    register("BSU", new String[] {"<BSU_BeginSuppression suppressionID=\"", "\"/>"});
    register("ESU", new String[] {"<ESU_EndSuppression suppressionID=\"", "\"/>"});
    register("SVI", new String[] {"<SVI_SetVariableSpaceCharacterIncrement increment=\"", "\"/>"});
    register("BLN", new String[] {"<BLN_BeginLine/>"});
    register("SVI", new String[] {"<SVI_SetVariableSpaceCharacterIncrement increment=\"", "\"/>"});
  }

  private static void register(String mnemonic, String[] parts) {
    byte[][] fragments = new byte[parts.length][];
    for (int i = 0; i < parts.length; i++) {
      fragments[i] = parts[i].getBytes(StandardCharsets.UTF_8);
    }
    TEMPLATES.put(mnemonic, new XmlTemplate(fragments));
  }

  public static XmlTemplate getTemplate(String mnemonic) {
    return TEMPLATES.get(mnemonic);
  }
}
