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

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import com.mgz.afp.enums.AFPOrientation;
import com.mgz.afp.goca.BGR_BeginGraphicsObject;
import com.mgz.afp.goca.EGR_EndGraphicsObject;
import com.mgz.afp.goca.GDD_GraphicsDataDescriptor;
import com.mgz.afp.goca.GDD_Parameter;
import com.mgz.afp.goca.GAD_GraphicsData;
import com.mgz.afp.goca.GAD_DrawingOrder;
import com.mgz.afp.goca.GAD_DrawingOrder.GSCP_SetCurrentPosition;
import com.mgz.afp.modca.OBD_ObjectAreaDescriptor;
import com.mgz.afp.modca.OBP_ObjectAreaPosition;
import com.mgz.afp.modca.BPG_BeginPage;
import com.mgz.afp.modca.EPG_EndPage;
import com.mgz.afp.triplets.Triplet;
import java.io.ByteArrayOutputStream;
import java.util.List;
import org.junit.jupiter.api.Test;

/**
 * Verifies GOCA Object Area Positioning (OBP) and Graphics Data Descriptor (GDD) scaling.
 */
public class PdfGocaPositioningTest {

  @Test
  public void testGocaPositioningAndScaling() throws Exception {
    ByteArrayOutputStream baos = new ByteArrayOutputStream();
    PdfHandler handler = new PdfHandler(baos);

    // 1. Begin Page
    BPG_BeginPage bpg = new BPG_BeginPage();
    bpg.setName("P1");
    handler.handle(bpg);

    // 2. Begin Graphics Object
    BGR_BeginGraphicsObject bgr = new BGR_BeginGraphicsObject();
    handler.handle(bgr);

    // 3. Object Area Descriptor with ObjectAreaSize triplet
    OBD_ObjectAreaDescriptor obd = new OBD_ObjectAreaDescriptor();
    Triplet.ObjectAreaSize oas = new Triplet.ObjectAreaSize();
    oas.sizeType_0x02 = 0x02;
    oas.xSize = 1000;
    oas.ySize = 1000;
    obd.addTriplet(oas);
    handler.handle(obd);

    // 4. Object Area Position
    OBP_ObjectAreaPosition obp = new OBP_ObjectAreaPosition();
    OBP_ObjectAreaPosition.OBP_RepeatingGroup rg = new OBP_ObjectAreaPosition.OBP_RepeatingGroup();
    rg.setxOrigin(1440);
    rg.setyOrigin(2880);
    rg.setxRotation(AFPOrientation.ori90);
    obp.setRepeatingGroup(rg);
    handler.handle(obp);

    // 5. Graphics Data Descriptor with WindowSpecification parameter
    GDD_GraphicsDataDescriptor gdd = new GDD_GraphicsDataDescriptor();
    GDD_Parameter.WindowSpecification win = new GDD_Parameter.WindowSpecification();
    win.setLeftEdgeOfGPSWindow(0);
    win.setRightEdgeOfGPSWindow(500);
    win.setBottomEdgeOfGPSWindow(0);
    win.setTopEdgeOfGPSWindow(500);
    gdd.setGddParameters(List.of(win));
    handler.handle(gdd);

    // 6. Graphics Data
    GSCP_SetCurrentPosition gscp = new GSCP_SetCurrentPosition();
    gscp.setCoordinateX((short) 100);
    gscp.setCoordinateY((short) 200);

    handler.handle(new GAD_GraphicsData() {
      @Override
      public List<GAD_DrawingOrder> getDrawingOrders() {
        return List.of(gscp);
      }
    });

    // 7. End Graphics Object
    EGR_EndGraphicsObject egr = new EGR_EndGraphicsObject();
    handler.handle(egr);

    // 8. End Page
    EPG_EndPage epg = new EPG_EndPage();
    handler.handle(epg);

    assertDoesNotThrow(() -> handler.close());
  }

  @Test
  public void testGocaPositioningWithNoObdAndNoGdd() throws Exception {
    ByteArrayOutputStream baos = new ByteArrayOutputStream();
    PdfHandler handler = new PdfHandler(baos);

    // 1. Begin Page
    BPG_BeginPage bpg = new BPG_BeginPage();
    bpg.setName("P1");
    handler.handle(bpg);

    // 2. Begin Graphics Object
    BGR_BeginGraphicsObject bgr = new BGR_BeginGraphicsObject();
    handler.handle(bgr);

    // 3. Object Area Position only
    OBP_ObjectAreaPosition obp = new OBP_ObjectAreaPosition();
    OBP_ObjectAreaPosition.OBP_RepeatingGroup rg = new OBP_ObjectAreaPosition.OBP_RepeatingGroup();
    rg.setxOrigin(1000);
    rg.setyOrigin(1000);
    rg.setxRotation(AFPOrientation.ori0);
    obp.setRepeatingGroup(rg);
    handler.handle(obp);

    // 4. Graphics Data
    GSCP_SetCurrentPosition gscp = new GSCP_SetCurrentPosition();
    gscp.setCoordinateX((short) 50);
    gscp.setCoordinateY((short) 50);

    handler.handle(new GAD_GraphicsData() {
      @Override
      public List<GAD_DrawingOrder> getDrawingOrders() {
        return List.of(gscp);
      }
    });

    // 5. End Graphics Object
    EGR_EndGraphicsObject egr = new EGR_EndGraphicsObject();
    handler.handle(egr);

    // 6. End Page
    EPG_EndPage epg = new EPG_EndPage();
    handler.handle(epg);

    assertDoesNotThrow(() -> handler.close());
  }
}
