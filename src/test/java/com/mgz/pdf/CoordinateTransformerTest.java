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

import com.itextpdf.kernel.geom.AffineTransform;
import com.mgz.afp.enums.AFPOrientation;
import com.mgz.afp.enums.AFPUnitBase;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class CoordinateTransformerTest {

    @Test
    public void testGetScaleFactor() {
        // Inches10: 720 / 1440 = 0.5
        assertEquals(0.5f, CoordinateTransformer.getScaleFactor(AFPUnitBase.Inches10, (short) 1440), 0.001f);

        // Centimeter10: (720 / 2.54) / 567 = 0.5
        float expectedCm = (720.0f / 2.54f) / 567.0f;
        assertEquals(expectedCm, CoordinateTransformer.getScaleFactor(AFPUnitBase.Centimeter10, (short) 567), 0.001f);

        // Invalid cases
        assertEquals(0.0f, CoordinateTransformer.getScaleFactor(AFPUnitBase.Inches10, (short) 0));
        assertEquals(0.0f, CoordinateTransformer.getScaleFactor(AFPUnitBase.Inches10, (short) -1));
        assertEquals(0.0f, CoordinateTransformer.getScaleFactor(AFPUnitBase.Logical_ResolutionRatio, (short) 1000));
    }

    @Test
    public void testGetAfpToPdfTransform() {
        float pageHeight = 792.0f; // 11 inches
        float scaleX = 0.5f;
        float scaleY = 0.5f;
        AffineTransform at = CoordinateTransformer.getAfpToPdfTransform(pageHeight, scaleX, scaleY);

        double[] matrix = new double[6];
        at.getMatrix(matrix);

        // matrix: [scaleX, 0, 0, -scaleY, 0, pageHeight]
        assertEquals(scaleX, matrix[0]);
        assertEquals(0, matrix[1]);
        assertEquals(0, matrix[2]);
        assertEquals(-scaleY, matrix[3]);
        assertEquals(0, matrix[4]);
        assertEquals(pageHeight, matrix[5]);
    }

    @Test
    public void testGetAfpX() {
        // iOri=0, bOri=90 (standard)
        // x = iPos * cos(0) + bPos * cos(90) = iPos * 1 + bPos * 0 = iPos
        assertEquals(100, CoordinateTransformer.getAfpX(100, 200, AFPOrientation.ori0, AFPOrientation.ori90), 0.001);

        // iOri=90, bOri=180
        // x = iPos * cos(90) + bPos * cos(180) = iPos * 0 + bPos * -1 = -bPos
        assertEquals(-200, CoordinateTransformer.getAfpX(100, 200, AFPOrientation.ori90, AFPOrientation.ori180), 0.001);

        // iOri=180, bOri=270
        // x = iPos * cos(180) + bPos * cos(270) = -iPos
        assertEquals(-100, CoordinateTransformer.getAfpX(100, 200, AFPOrientation.ori180, AFPOrientation.ori270), 0.001);

        // iOri=270, bOri=0
        // x = iPos * cos(270) + bPos * cos(0) = bPos
        assertEquals(200, CoordinateTransformer.getAfpX(100, 200, AFPOrientation.ori270, AFPOrientation.ori0), 0.001);
    }

    @Test
    public void testGetAfpY() {
        // iOri=0, bOri=90 (standard)
        // y = iPos * sin(0) + bPos * sin(90) = iPos * 0 + bPos * 1 = bPos
        assertEquals(200, CoordinateTransformer.getAfpY(100, 200, AFPOrientation.ori0, AFPOrientation.ori90), 0.001);

        // iOri=90, bOri=180
        // y = iPos * sin(90) + bPos * sin(180) = iPos * 1 + bPos * 0 = iPos
        assertEquals(100, CoordinateTransformer.getAfpY(100, 200, AFPOrientation.ori90, AFPOrientation.ori180), 0.001);
    }
}
