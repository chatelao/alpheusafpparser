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

import static org.junit.jupiter.api.Assertions.assertTrue;

import com.mgz.afp.modca.BPG_BeginPage;
import com.mgz.afp.ptoca.PTX_PresentationTextData;
import com.mgz.afp.ptoca.controlSequence.PTOCAControlSequence;
import java.io.ByteArrayOutputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;

public class PTXAttributesTest {

  @Test
  public void testPTXAttributes() throws Exception {
    ByteArrayOutputStream baos = new ByteArrayOutputStream();
    try (AfpJacksonXmlWriter writer = new AfpJacksonXmlWriter(baos, null, true, true)) {
      // 1. Begin Page
      BPG_BeginPage bpg = new BPG_BeginPage();
      bpg.setName("P1");
      writer.handle(bpg);

      // 2. PTX with some moves and TRN
      PTX_PresentationTextData ptx = new PTX_PresentationTextData();
      List<PTOCAControlSequence> csList = new ArrayList<>();

      PTOCAControlSequence.AMI_AbsoluteMoveInline ami = new PTOCAControlSequence.AMI_AbsoluteMoveInline();
      ami.setDisplacement((short) 1000);
      csList.add(ami);

      PTOCAControlSequence.AMB_AbsoluteMoveBaseline amb = new PTOCAControlSequence.AMB_AbsoluteMoveBaseline();
      amb.setDisplacement((short) 2000);
      csList.add(amb);

      PTOCAControlSequence.TRN_TransparentData trn = new PTOCAControlSequence.TRN_TransparentData();
      trn.setTransparentData("Hello");
      csList.add(trn);

      ptx.setControlSequences(csList);
      writer.handle(ptx);
    }

    String xml = baos.toString(StandardCharsets.UTF_8);
    System.out.println(xml);

    // Verify PTX has page/x/y. At start of PTX, inlinePos and baselinePos should be 0 (since it's a new page).
    // Page number should be 1.
    assertTrue(xml.contains("<PTX_PresentationTextData page=\"1\" x=\"0\" y=\"0\">"), "PTX should have correct page/x/y");

    // Verify AMI has x/y after it updated state.
    // wait, I call updatePtocaState(cs) BEFORE writeCommonAttributes() in my implementation.
    // So AMI displacement=1000 will result in x=1000.
    assertTrue(xml.contains("<AMI_AbsoluteMoveInline page=\"1\" x=\"1000\" y=\"0\" displacement=\"1000\"/>"), "AMI should have correct page/x/y");

    // Verify AMB has x/y after it updated state.
    // AMB displacement=2000. x was 1000 from previous AMI. y becomes 2000.
    // CoordinateTransformer.getAfpY(1000, 2000, ori0, ori90) -> 1000*sin(0) + 2000*sin(90) = 2000.
    // CoordinateTransformer.getAfpX(1000, 2000, ori0, ori90) -> 1000*cos(0) + 2000*cos(90) = 1000.
    assertTrue(xml.contains("<AMB_AbsoluteMoveBaseline page=\"1\" x=\"1000\" y=\"2000\" displacement=\"2000\"/>"), "AMB should have correct page/x/y");

    // Verify TRN has same x/y as AMB
    assertTrue(xml.contains("<TRN_TransparentData page=\"1\" x=\"1000\" y=\"2000\">"), "TRN should have correct page/x/y");
  }
}
