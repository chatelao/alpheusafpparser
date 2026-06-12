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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.mgz.afp.ioca.IDD_ImageDataDescriptor;
import com.mgz.afp.ioca.IPD_ImagePictureData;
import org.junit.jupiter.api.Test;

public class PdfImageStateTest {

  @Test
  public void testDefaultValues() {
    PdfImageState state = new PdfImageState();
    assertNull(state.getDescriptor());
    assertTrue(state.getImageSegments().isEmpty());
    assertFalse(state.isInImageObject());
    assertEquals(0, state.getxOrigin());
    assertEquals(0, state.getyOrigin());
  }

  @Test
  public void testSettersAndGetters() {
    PdfImageState state = new PdfImageState();
    IDD_ImageDataDescriptor descriptor = new IDD_ImageDataDescriptor();
    state.setDescriptor(descriptor);
    assertEquals(descriptor, state.getDescriptor());

    state.setInImageObject(true);
    assertTrue(state.isInImageObject());

    state.setxOrigin(100);
    assertEquals(100, state.getxOrigin());

    state.setyOrigin(200);
    assertEquals(200, state.getyOrigin());

    IPD_ImagePictureData segment = new IPD_ImagePictureData();
    state.addImageSegment(segment);
    assertEquals(1, state.getImageSegments().size());
    assertEquals(segment, state.getImageSegments().get(0));
  }

  @Test
  public void testReset() {
    PdfImageState state = new PdfImageState();
    state.setDescriptor(new IDD_ImageDataDescriptor());
    state.addImageSegment(new IPD_ImagePictureData());
    state.setInImageObject(true);
    state.setxOrigin(100);
    state.setyOrigin(200);

    state.reset();

    assertNull(state.getDescriptor());
    assertTrue(state.getImageSegments().isEmpty());
    assertFalse(state.isInImageObject());
    assertEquals(0, state.getxOrigin());
    assertEquals(0, state.getyOrigin());
  }

  @Test
  public void testStartNewImage() {
    PdfImageState state = new PdfImageState();
    state.setDescriptor(new IDD_ImageDataDescriptor());
    state.addImageSegment(new IPD_ImagePictureData());
    state.setInImageObject(false);
    state.setxOrigin(100);
    state.setyOrigin(200);

    state.startNewImage();

    assertNull(state.getDescriptor());
    assertTrue(state.getImageSegments().isEmpty());
    assertTrue(state.isInImageObject());
    assertEquals(0, state.getxOrigin());
    assertEquals(0, state.getyOrigin());
  }
}
