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
package com.mgz.afp.parser;

import com.mgz.afp.base.StructuredField;
import com.mgz.afp.exceptions.AFPParserException;
import com.mgz.afp.ptoca.controlSequence.PTOCAControlSequence;
import com.mgz.afp.ptoca.controlSequence.PTOCAControlSequence.ControlSequenceIntroducer;
import com.mgz.afp.ptoca.controlSequence.PTOCAControlSequence.GraphicCharacters;
import com.mgz.afp.ptoca.controlSequence.PTOCAControlSequence.SEA_SetEncryptedAlternate;
import com.mgz.afp.ptoca.controlSequence.PTOCAControlSequence.SKI_SetKeyInformation;
import com.mgz.afp.ptoca.controlSequence.PTOCAControlSequence.UCT_UnicodeComplexText;
import java.util.ArrayList;
import java.util.List;

public class PTOCAControlSequenceParser {
  public static List<PTOCAControlSequence> parseControlSequences(byte[] sfData, int offset, int length, AFPParserConfiguration config) throws AFPParserException {

    List<PTOCAControlSequence> controlSequences = new ArrayList<PTOCAControlSequence>();

    int actualLength = StructuredField.getActualLength(sfData, offset, length);
    int pos = 0;
    boolean isChained = false;
    PTOCAControlSequence lastCS = null;
    while (pos < actualLength) {
      if (!isChained && (pos + 1 >= actualLength || sfData[offset + pos] != 0x2B || sfData[offset + pos + 1] != (byte) 0xD3)) {
        int runStart = pos;
        while (pos < actualLength) {
          if (pos + 1 < actualLength && sfData[offset + pos] == 0x2B && sfData[offset + pos + 1] == (byte) 0xD3) {
            break;
          }
          pos++;
        }
        GraphicCharacters gc = new GraphicCharacters();
        gc.decodeAFP(sfData, offset + runStart, pos - runStart, config);
        controlSequences.add(gc);
        continue;
      }

      ControlSequenceIntroducer csi = ControlSequenceIntroducer.parseCSI(isChained, sfData, offset + pos, -1, config);

      PTOCAControlSequence cs = createControlSequenceInstance(csi);
      // Move pos to begin of CS payload.
      if (isChained) {
        pos += 2;
      } else {
        pos += 4;
      }

      cs.decodeAFP(sfData, offset + pos, csi.getLength() - 2, config);

      if (cs instanceof UCT_UnicodeComplexText uct) {
        int ctLen = uct.getCtLength();
        if (ctLen > 0 && (pos + (csi.getLength() - 2) + ctLen) <= actualLength) {
          var text = new byte[ctLen];
          System.arraycopy(sfData, offset + pos + (csi.getLength() - 2), text, 0, ctLen);
          uct.setComplexText(text);
        }
        pos += ctLen;
        isChained = false; // The UCT control sequence always terminates chaining.
      } else {
        isChained = cs.getCsi().isChained();
      }

      // Concatenation support for SEA and SKI
      if (cs instanceof SEA_SetEncryptedAlternate currentSea && lastCS instanceof SEA_SetEncryptedAlternate lastSea) {
        if (currentSea.getAlternateText() == null) {
          // Reset
          lastSea.setAlternateText(null);
        } else {
          var oldText = lastSea.getAlternateText();
          if (oldText == null) {
            lastSea.setAlternateText(currentSea.getAlternateText());
          } else {
            var newText = new byte[oldText.length + currentSea.getAlternateText().length];
            System.arraycopy(oldText, 0, newText, 0, oldText.length);
            System.arraycopy(currentSea.getAlternateText(), 0, newText, oldText.length, currentSea.getAlternateText().length);
            lastSea.setAlternateText(newText);
          }
        }
      } else if (cs instanceof SKI_SetKeyInformation currentSki && lastCS instanceof SKI_SetKeyInformation lastSki) {
        if (currentSki.getKeyInfo() == null) {
          // Reset
          lastSki.setKeyInfo(null);
        } else {
          var oldInfo = lastSki.getKeyInfo();
          if (oldInfo == null) {
            lastSki.setKeyInfo(currentSki.getKeyInfo());
          } else {
            var newInfo = new byte[oldInfo.length + currentSki.getKeyInfo().length];
            System.arraycopy(oldInfo, 0, newInfo, 0, oldInfo.length);
            System.arraycopy(currentSki.getKeyInfo(), 0, newInfo, oldInfo.length, currentSki.getKeyInfo().length);
            lastSki.setKeyInfo(newInfo);
          }
        }
      } else {
        controlSequences.add(cs);
        lastCS = cs;
      }
      pos += csi.getLength() - 2;
    }

    return controlSequences;
  }

  public static final PTOCAControlSequence createControlSequenceInstance(ControlSequenceIntroducer csi) throws AFPParserException {
    PTOCAControlSequence cs = null;
    String className = null;
    try {
      className = com.mgz.afp.ptoca.controlSequence.PTOCAControlSequence.class.getName() + "$" + csi.getControlSequenceFunctionType().name();
      Class<?> clazz = Class.forName(className);
      cs = (PTOCAControlSequence) clazz.getDeclaredConstructor().newInstance();
    } catch (Throwable cnfex) {
      throw new AFPParserException(PTOCAControlSequence.class.getSimpleName() + ": failed to instantiate control sequence class '" + className + "'.");
    }

    if (cs == null) {
      cs = new com.mgz.afp.ptoca.controlSequence.Undefined();
    }
    cs.setCsi(csi);

    return cs;
  }

}
