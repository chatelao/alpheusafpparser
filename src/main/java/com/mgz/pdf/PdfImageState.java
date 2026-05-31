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

import com.mgz.afp.ioca.IDD_ImageDataDescriptor;
import com.mgz.afp.ioca.IPD_ImagePictureData;
import java.util.ArrayList;
import java.util.List;

/**
 * Tracks the active IOCA image state for PDF conversion.
 * Stores the image descriptor and collected image picture data segments.
 */
public class PdfImageState {

  private IDD_ImageDataDescriptor descriptor;
  private final List<IPD_ImagePictureData> imageSegments = new ArrayList<>();
  private boolean inImageObject = false;

  /**
   * Resets the image state to default values.
   */
  public void reset() {
    this.descriptor = null;
    this.imageSegments.clear();
    this.inImageObject = false;
  }

  public IDD_ImageDataDescriptor getDescriptor() {
    return descriptor;
  }

  public void setDescriptor(IDD_ImageDataDescriptor descriptor) {
    this.descriptor = descriptor;
  }

  public List<IPD_ImagePictureData> getImageSegments() {
    return imageSegments;
  }

  public void addImageSegment(IPD_ImagePictureData segment) {
    this.imageSegments.add(segment);
  }

  public boolean isInImageObject() {
    return inImageObject;
  }

  public void setInImageObject(boolean inImageObject) {
    this.inImageObject = inImageObject;
    if (!inImageObject) {
      // Clear segments when leaving image object context if they were already processed
      // Or keep them until explicitly cleared? For tracking, we clear on BIM.
    }
  }

  /**
   * Clears collected segments and descriptor for a new image object.
   */
  public void startNewImage() {
    this.descriptor = null;
    this.imageSegments.clear();
    this.inImageObject = true;
  }
}
