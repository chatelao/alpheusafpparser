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
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Unit test for {@link PdfImageState}.
 */
public class PdfImageStateTest {

  private PdfImageState imageState;

  @BeforeEach
  public void setUp() {
    imageState = new PdfImageState();
  }

  @Test
  public void testDefaultValues() {
    assertNull(imageState.getDescriptor());
    assertTrue(imageState.getImageSegments().isEmpty());
    assertFalse(imageState.isInImageObject());
    assertEquals(0, imageState.getxOrigin());
    assertEquals(0, imageState.getyOrigin());
  }

  @Test
  public void testSettersAndGetters() {
    IDD_ImageDataDescriptor descriptor = new IDD_ImageDataDescriptor();
    imageState.setDescriptor(descriptor);
    imageState.setInImageObject(true);
    imageState.setxOrigin(100);
    imageState.setyOrigin(200);

    IPD_ImagePictureData segment = new IPD_ImagePictureData();
    imageState.addImageSegment(segment);

    assertEquals(descriptor, imageState.getDescriptor());
    assertTrue(imageState.isInImageObject());
    assertEquals(100, imageState.getxOrigin());
    assertEquals(200, imageState.getyOrigin());
    assertEquals(1, imageState.getImageSegments().size());
    assertEquals(segment, imageState.getImageSegments().get(0));
  }

  @Test
  public void testReset() {
    imageState.setDescriptor(new IDD_ImageDataDescriptor());
    imageState.setInImageObject(true);
    imageState.setxOrigin(100);
    imageState.setyOrigin(200);
    imageState.addImageSegment(new IPD_ImagePictureData());

    imageState.reset();

    assertNull(imageState.getDescriptor());
    assertTrue(imageState.getImageSegments().isEmpty());
    assertFalse(imageState.isInImageObject());
    assertEquals(0, imageState.getxOrigin());
    assertEquals(0, imageState.getyOrigin());
  }

  @Test
  public void testStartNewImage() {
    imageState.setDescriptor(new IDD_ImageDataDescriptor());
    imageState.setInImageObject(false);
    imageState.setxOrigin(100);
    imageState.setyOrigin(200);
    imageState.addImageSegment(new IPD_ImagePictureData());

    imageState.startNewImage();

    assertNull(imageState.getDescriptor());
    assertTrue(imageState.getImageSegments().isEmpty());
    assertTrue(imageState.isInImageObject());
    assertEquals(0, imageState.getxOrigin());
    assertEquals(0, imageState.getyOrigin());
  }
}
