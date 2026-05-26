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

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import org.junit.jupiter.api.Test;

public class OrderedOutputOrchestratorTest {

  @Test
  public void testSingleStreamOrdering() throws IOException {
    ByteArrayOutputStream baos = new ByteArrayOutputStream();
    OrderedOutputOrchestrator orchestrator = new OrderedOutputOrchestrator(baos);

    int id = orchestrator.registerStream();
    orchestrator.put(id, 1, "B".getBytes());
    orchestrator.put(id, 0, "A".getBytes());
    orchestrator.put(id, 2, "C".getBytes());
    orchestrator.finishStream(id);

    assertEquals("ABC", baos.toString());
  }

  @Test
  public void testMultipleStreamOrdering() throws IOException {
    ByteArrayOutputStream baos = new ByteArrayOutputStream();
    OrderedOutputOrchestrator orchestrator = new OrderedOutputOrchestrator(baos);

    int id0 = orchestrator.registerStream();
    int id1 = orchestrator.registerStream();

    orchestrator.put(id1, 0, "2".getBytes());
    orchestrator.put(id0, 0, "1".getBytes());
    orchestrator.finishStream(id0);
    orchestrator.finishStream(id1);

    assertEquals("12", baos.toString());
  }

  @Test
  public void testComplexInterleavedOrdering() throws IOException {
    ByteArrayOutputStream baos = new ByteArrayOutputStream();
    OrderedOutputOrchestrator orchestrator = new OrderedOutputOrchestrator(baos);

    int id0 = orchestrator.registerStream();
    int id1 = orchestrator.registerStream();
    int id2 = orchestrator.registerStream();

    orchestrator.put(id1, 1, "1B".getBytes());
    orchestrator.put(id2, 0, "2A".getBytes());
    orchestrator.put(id0, 1, "0B".getBytes());
    orchestrator.put(id1, 0, "1A".getBytes());
    orchestrator.put(id0, 0, "0A".getBytes());

    // At this point, id0 is ready to flush 0A and 0B
    assertEquals("0A0B", baos.toString());

    orchestrator.finishStream(id0);
    // Now id1 should flush 1A and 1B
    assertEquals("0A0B1A1B", baos.toString());

    orchestrator.finishStream(id1);
    // Now id2 should flush 2A
    assertEquals("0A0B1A1B2A", baos.toString());

    orchestrator.finishStream(id2);
    assertEquals("0A0B1A1B2A", baos.toString());
  }

  @Test
  public void testEmptyStream() throws IOException {
    ByteArrayOutputStream baos = new ByteArrayOutputStream();
    OrderedOutputOrchestrator orchestrator = new OrderedOutputOrchestrator(baos);

    int id0 = orchestrator.registerStream();
    int id1 = orchestrator.registerStream();

    orchestrator.finishStream(id0);
    orchestrator.put(id1, 0, "X".getBytes());
    orchestrator.finishStream(id1);

    assertEquals("X", baos.toString());
  }
}
