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

import com.mgz.afp.modca.IID_IMImageInputDescriptor;
import com.mgz.afp.modca.IOC_IMImageOutputControl;
import com.mgz.afp.modca.ICP_IMImageCellPosition;
import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Tracks the active legacy IM Image state for PDF conversion.
 */
public class PdfImImageState {
  private boolean inImImageObject = false;
  private IOC_IMImageOutputControl outputControl;
  private IID_IMImageInputDescriptor descriptor;

  /**
   * Represents a single IM image cell.
   */
  public static class ImCell {
    private final ICP_IMImageCellPosition position;
    private final ByteArrayOutputStream rasterData = new ByteArrayOutputStream();

    public ImCell(ICP_IMImageCellPosition position) {
      this.position = position;
    }

    public ICP_IMImageCellPosition getPosition() {
      return position;
    }

    public byte[] getRasterData() {
      return rasterData.toByteArray();
    }

    public void addRasterData(byte[] data) {
      if (data != null) {
        try {
          rasterData.write(data);
        } catch (Exception e) {
          // Ignore
        }
      }
    }
  }

  private final List<ImCell> cells = new ArrayList<>();
  private ImCell currentCell;

  /**
   * Resets the state.
   */
  public void reset() {
    inImImageObject = false;
    outputControl = null;
    descriptor = null;
    cells.clear();
    currentCell = null;
  }

  public boolean isInImImageObject() {
    return inImImageObject;
  }

  public void setInImImageObject(boolean inImImageObject) {
    this.inImImageObject = inImImageObject;
  }

  public IOC_IMImageOutputControl getOutputControl() {
    return outputControl;
  }

  public void setOutputControl(IOC_IMImageOutputControl outputControl) {
    this.outputControl = outputControl;
  }

  public IID_IMImageInputDescriptor getDescriptor() {
    return descriptor;
  }

  public void setDescriptor(IID_IMImageInputDescriptor descriptor) {
    this.descriptor = descriptor;
  }

  public List<ImCell> getCells() {
    return cells;
  }

  /**
   * Starts a new image cell.
   *
   * @param position the cell position structured field
   */
  public void startNewCell(ICP_IMImageCellPosition position) {
    currentCell = new ImCell(position);
    cells.add(currentCell);
  }

  public ImCell getCurrentCell() {
    return currentCell;
  }

  /**
   * Starts a new IM image object.
   */
  public void startNewImImage() {
    reset();
    inImImageObject = true;
  }
}
