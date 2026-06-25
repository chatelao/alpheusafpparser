package com.mgz.pdf;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.mgz.afp.base.StructuredFieldIntroducer;
import com.mgz.afp.enums.AFPColorSpace;
import com.mgz.afp.enums.AFPUnitBase;
import com.mgz.afp.enums.SFTypeID;
import com.mgz.afp.goca.BGR_BeginGraphicsObject;
import com.mgz.afp.goca.EGR_EndGraphicsObject;
import com.mgz.afp.goca.GAD_DrawingOrder;
import com.mgz.afp.goca.GAD_DrawingOrder.GSPCOL_SetProcessColor;
import com.mgz.afp.goca.GDD_GraphicsDataDescriptor;
import com.mgz.afp.goca.GDD_Parameter;
import com.mgz.afp.modca.OBP_ObjectAreaPosition;
import com.mgz.afp.modca.PGD_PageDescriptor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.lang.reflect.Field;
import java.util.ArrayList;

public class GocaPositioningColorTest {

  private PdfHandler handler;
  private ByteArrayOutputStream os;

  @BeforeEach
  public void setUp() {
    os = new ByteArrayOutputStream();
    handler = new PdfHandler(os);
  }

  @Test
  public void testGocaPositioningAndColor() throws Exception {
    // 1. Set up page descriptor (1440 units per inch)
    PGD_PageDescriptor pgd = new PGD_PageDescriptor();
    setPrivateField(pgd, "xUnitBase", AFPUnitBase.Inches10);
    setPrivateField(pgd, "yUnitBase", AFPUnitBase.Inches10);
    setPrivateField(pgd, "xUnitsPerUnitBase", (short) 1440);
    setPrivateField(pgd, "yUnitsPerUnitBase", (short) 1440);
    setPrivateField(pgd, "xSize", 8 * 1440);
    setPrivateField(pgd, "ySize", 11 * 1440);
    handler.handle(pgd);

    // 2. Start GOCA object
    BGR_BeginGraphicsObject bgr = new BGR_BeginGraphicsObject();
    StructuredFieldIntroducer bgrSfi = new StructuredFieldIntroducer();
    setPrivateField(bgrSfi, "sfTypeID", SFTypeID.BGR_BeginGraphicsObject);
    setPrivateField(bgr, "structuredFieldIntroducer", bgrSfi);
    handler.handle(bgr);
    assertTrue(handler.getGraphicsState().isInGraphicsObject());

    // 3. Set GOCA position (e.g., at 2 inches, 3 inches)
    OBP_ObjectAreaPosition obp = new OBP_ObjectAreaPosition();
    OBP_ObjectAreaPosition.OBP_RepeatingGroup rg = new OBP_ObjectAreaPosition.OBP_RepeatingGroup();
    setPrivateField(rg, "xOrigin", 2 * 1440);
    setPrivateField(rg, "yOrigin", 3 * 1440);
    setPrivateField(obp, "repeatingGroup", rg);
    handler.handle(obp);
    assertEquals(2 * 1440, handler.getGraphicsState().getxOrigin());
    assertEquals(3 * 1440, handler.getGraphicsState().getyOrigin());

    // 4. Set GOCA resolution (e.g., 2880 units per inch - double density)
    GDD_GraphicsDataDescriptor gdd = new GDD_GraphicsDataDescriptor();
    GDD_Parameter.WindowSpecification ws = new GDD_Parameter.WindowSpecification();
    setPrivateField(ws, "unitBaseGPS", AFPUnitBase.Inches10);
    setPrivateField(ws, "unitsPerUnitBaseX", 2880);
    setPrivateField(ws, "unitsPerUnitBaseY", 2880);
    ArrayList<GDD_Parameter> params = new ArrayList<>();
    params.add(ws);
    setPrivateField(gdd, "gddParameters", params);
    handler.handle(gdd);

    // Scale factor for 2880 units/inch is 720/2880 = 0.25 points per unit
    assertEquals(0.25f, handler.getGraphicsState().getxScale(), 0.001);

    // 5. Set Process Color (RGB Blue)
    GSPCOL_SetProcessColor gspcol = new GSPCOL_SetProcessColor();
    gspcol.colorSpace = AFPColorSpace.RGB;
    gspcol.colorValue = new byte[] {0, 0, (byte) 255};
    handler.handleDrawingOrder(gspcol);
    assertEquals(AFPColorSpace.RGB, handler.getGraphicsState().getProcessColorSpace());

    // 6. End GOCA object
    EGR_EndGraphicsObject egr = new EGR_EndGraphicsObject();
    StructuredFieldIntroducer egrSfi = new StructuredFieldIntroducer();
    setPrivateField(egrSfi, "sfTypeID", SFTypeID.EGR_EndGraphicsObject);
    setPrivateField(egr, "structuredFieldIntroducer", egrSfi);
    handler.handle(egr);
    assertTrue(!handler.getGraphicsState().isInGraphicsObject());
  }

  private void setPrivateField(Object obj, String fieldName, Object value) throws Exception {
    Field field = null;
    Class<?> clazz = obj.getClass();
    while (clazz != null && field == null) {
      try {
        field = clazz.getDeclaredField(fieldName);
      } catch (NoSuchFieldException e) {
        clazz = clazz.getSuperclass();
      }
    }
    if (field == null) throw new NoSuchFieldException(fieldName);
    field.setAccessible(true);
    field.set(obj, value);
  }
}
