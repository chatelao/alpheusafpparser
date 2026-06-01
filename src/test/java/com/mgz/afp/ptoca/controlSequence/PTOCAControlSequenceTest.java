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
package com.mgz.afp.ptoca.controlSequence;

import com.mgz.afp.exceptions.AFPParserException;
import com.mgz.afp.parser.AFPParserConfiguration;
import com.mgz.afp.parser.PTOCAControlSequenceParser;
import com.mgz.afp.ptoca.controlSequence.PTOCAControlSequence.AMB_AbsoluteMoveBaseline;
import com.mgz.afp.ptoca.controlSequence.PTOCAControlSequence.ControlSequenceFunctionType;
import com.mgz.afp.ptoca.controlSequence.PTOCAControlSequence.ControlSequenceIntroducer;
import com.mgz.afp.ptoca.controlSequence.PTOCAControlSequence.RPS_RepeatString;

import org.junit.jupiter.api.Test;

import java.nio.charset.Charset;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;


public class PTOCAControlSequenceTest {

  @Test
  public void testInstantiationOfPTOCAControlSequences() throws AFPParserException {


    ControlSequenceIntroducer csi = new ControlSequenceIntroducer();
    for (ControlSequenceFunctionType csft : ControlSequenceFunctionType.values()) {
      csi.setControlSequenceFunctionType(csft);
      PTOCAControlSequence cs = PTOCAControlSequenceParser.createControlSequenceInstance(csi);
      assertEquals(csft.name(), cs.getClass().getSimpleName());
      assertFalse(csft != ControlSequenceFunctionType.Undefined && cs instanceof com.mgz.afp.ptoca.controlSequence.Undefined);

    }

  }

  @Test
  public void testUniqunessOfControlSequenceFunctionType() {
    for (ControlSequenceFunctionType csft : ControlSequenceFunctionType.values()) {
      for (ControlSequenceFunctionType otherCsft : ControlSequenceFunctionType.values()) {
        if (csft == otherCsft) {
          continue;
        }
        assertFalse(csft.toByte(true) == otherCsft.toByte(true));
        assertFalse(csft.toByte(false) == otherCsft.toByte(false));
      }

    }
  }

  @Test
  public void testConstructionOfControlSequenceFunctionType() throws AFPParserException {
    for (ControlSequenceFunctionType csft : ControlSequenceFunctionType.values()) {
      ControlSequenceFunctionType other = ControlSequenceFunctionType.valueOf((short) csft.toByte(false));
      assertTrue(csft == other);
    }
  }

  @Test
  public void testAMBValidation() {
    AMB_AbsoluteMoveBaseline amb = new AMB_AbsoluteMoveBaseline();
    AFPParserConfiguration config = new AFPParserConfiguration();
    byte[] validData = new byte[]{0x00, 0x64}; // 100
    byte[] invalidData = new byte[]{(byte) 0xFF, (byte) 0x9C}; // -100

    assertThrows(AFPParserException.class, () -> amb.decodeAFP(invalidData, 0, 2, config), "[PTOCA-4-132]");
  }

  @Test
  public void testRPSValidation() {
    RPS_RepeatString rps = new RPS_RepeatString();
    AFPParserConfiguration config = new AFPParserConfiguration();

    // Single-byte font (default CP500)
    byte[] validData = new byte[]{0x00, 0x01, 0x41}; // RLENGTH=1, RPTDATA="A"
    assertThrows(AFPParserException.class, () -> rps.decodeAFP(new byte[]{0x00, 0x01}, 0, 2, config), "[PTOCA-4-355]");

    // Double-byte font (UTF-16)
    config.setAfpCharSet(Charset.forName("UTF-16"));
    byte[] oddRLENGTH = new byte[]{0x00, 0x01, 0x00, 0x41}; // RLENGTH=1 (odd), RPTDATA="A"
    assertThrows(AFPParserException.class, () -> rps.decodeAFP(oddRLENGTH, 0, 4, config), "[PTOCA-4-352]");

    byte[] oddRPTDATA = new byte[]{0x00, 0x02, 0x41}; // RLENGTH=2, RPTDATA=0x41 (odd length)
    assertThrows(AFPParserException.class, () -> rps.decodeAFP(oddRPTDATA, 0, 3, config), "[PTOCA-4-350]");
  }

}
