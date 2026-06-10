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

package com.mgz.pdf;

import com.mgz.afp.ptoca.controlSequence.PTOCAControlSequence;
import com.mgz.afp.ptoca.controlSequence.PTOCAControlSequence.BSU_BeginSuppression;
import com.mgz.afp.ptoca.controlSequence.PTOCAControlSequence.ESU_EndSuppression;
import com.mgz.afp.ptoca.controlSequence.PTOCAControlSequence.RPS_RepeatString;
import com.mgz.afp.ptoca.controlSequence.PTOCAControlSequence.TRN_TransparentData;
import com.mgz.afp.parser.AFPParserConfiguration;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.lang.reflect.Method;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Tests for extra PTOCA control sequences in {@link PdfHandler}.
 */
public class PdfPtocaExtraTest {

  @Test
  public void testSuppressionLogic() throws Exception {
    PdfHandler handler = new PdfHandler(new ByteArrayOutputStream());
    PdfTextState state = handler.getTextState();

    assertFalse(state.isSuppressed());

    // BSU(1)
    BSU_BeginSuppression bsu1 = new BSU_BeginSuppression();
    bsu1.decodeAFP(new byte[] {0x01}, 0, 1, new AFPParserConfiguration());

    // We need to call handleControlSequence which is private.
    // We can use handle(StructuredField) but these are ControlSequences,
    // which are handled inside handleInternal(PTX).
    // Let's use reflection to test the private method handleControlSequence for simplicity in unit test.
    Method m = PdfHandler.class.getDeclaredMethod("handleControlSequence", PTOCAControlSequence.class);
    m.setAccessible(true);

    m.invoke(handler, bsu1);
    assertTrue(state.isSuppressed());
    assertTrue(state.getActiveSuppressionIDs().contains((short) 1));

    // BSU(2)
    BSU_BeginSuppression bsu2 = new BSU_BeginSuppression();
    bsu2.decodeAFP(new byte[] {0x02}, 0, 1, new AFPParserConfiguration());
    m.invoke(handler, bsu2);
    assertTrue(state.isSuppressed());
    assertTrue(state.getActiveSuppressionIDs().contains((short) 2));

    // Test enabling suppression
    handler.enableSuppressionIDs((short) 1);
    // This is hard to test without capturing output, but we can verify state

    // ESU(1)
    ESU_EndSuppression esu1 = new ESU_EndSuppression();
    esu1.decodeAFP(new byte[] {0x01}, 0, 1, new AFPParserConfiguration());
    m.invoke(handler, esu1);
    assertTrue(state.isSuppressed());
    assertFalse(state.getActiveSuppressionIDs().contains((short) 1));

    // ESU(2)
    ESU_EndSuppression esu2 = new ESU_EndSuppression();
    esu2.decodeAFP(new byte[] {0x02}, 0, 1, new AFPParserConfiguration());
    m.invoke(handler, esu2);
    assertFalse(state.isSuppressed());
  }

  @Test
  public void testRepeatString() throws Exception {
    PdfHandler handler = new PdfHandler(new ByteArrayOutputStream());

    RPS_RepeatString rps = new RPS_RepeatString();
    // Repeat 'A' (0xC1 in Cp500) 5 times
    byte[] data = new byte[] {0x00, 0x05, (byte) 0xC1};
    rps.decodeAFP(data, 0, 3, new AFPParserConfiguration());

    Method m = PdfHandler.class.getDeclaredMethod("handleControlSequence", PTOCAControlSequence.class);
    m.setAccessible(true);

    // This should not crash and should call renderText
    m.invoke(handler, rps);
  }
}
