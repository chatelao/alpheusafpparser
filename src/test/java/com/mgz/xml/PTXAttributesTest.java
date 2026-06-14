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

import com.mgz.afp.modca.BPG_BeginPage;
import com.mgz.afp.ptoca.PTX_PresentationTextData;
import com.mgz.afp.ptoca.controlSequence.PTOCAControlSequence;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class PTXAttributesTest {

    @Test
    public void testPTXAttributes() throws Exception {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try (AfpJacksonXmlWriter writer = new AfpJacksonXmlWriter(baos)) {
            // Page 1
            BPG_BeginPage bpg1 = new BPG_BeginPage();
            writer.handle(bpg1);

            PTX_PresentationTextData ptx1 = new PTX_PresentationTextData();

            PTOCAControlSequence.AMI_AbsoluteMoveInline ami = new PTOCAControlSequence.AMI_AbsoluteMoveInline();
            ami.setDisplacement((short) 1000);
            ptx1.addControlSequence(ami);

            PTOCAControlSequence.AMB_AbsoluteMoveBaseline amb = new PTOCAControlSequence.AMB_AbsoluteMoveBaseline();
            amb.setDisplacement((short) 2000);
            ptx1.addControlSequence(amb);

            PTOCAControlSequence.TRN_TransparentData trn = new PTOCAControlSequence.TRN_TransparentData();
            trn.setTransparentData("Hello");
            ptx1.addControlSequence(trn);

            writer.handle(ptx1);

            // Page 2
            BPG_BeginPage bpg2 = new BPG_BeginPage();
            writer.handle(bpg2);

            PTX_PresentationTextData ptx2 = new PTX_PresentationTextData();
            PTOCAControlSequence.DIR_DrawIaxisRule dir = new PTOCAControlSequence.DIR_DrawIaxisRule();
            dir.setLength((short) 500);
            ptx2.addControlSequence(dir);
            writer.handle(ptx2);
        }

        String xml = baos.toString(StandardCharsets.UTF_8);
        System.out.println(xml);

        // PTX on page 1 should have starting coordinates (0,0) as move commands are inside
        assertTrue(xml.contains("<PTX_PresentationTextData page=\"1\" x=\"0\" y=\"0\">"), "PTX1 should have page 1 and (0,0)");

        // TRN should have coordinates after AMI(1000) and AMB(2000)
        // Default orientation is iOri=ori0 (cos=1, sin=0), bOri=ori90 (cos=0, sin=1)
        // X = iPos*1 + bPos*0 = 1000
        // Y = iPos*0 + bPos*1 = 2000
        assertTrue(xml.contains("<TRN_TransparentData page=\"1\" x=\"1000\" y=\"2000\">"), "TRN should have page 1 and (1000, 2000)");

        // Page 2 PTX
        assertTrue(xml.contains("<PTX_PresentationTextData page=\"2\" x=\"0\" y=\"0\">"), "PTX2 should have page 2 and (0,0)");
        assertTrue(xml.contains("<DIR_DrawIaxisRule page=\"2\" x=\"0\" y=\"0\""), "DIR should have page 2 and (0,0)");
    }
}
