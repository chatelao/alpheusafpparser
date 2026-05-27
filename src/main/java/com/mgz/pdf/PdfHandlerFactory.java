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

import com.mgz.afp.base.handler.FragmentAssembler;
import com.mgz.afp.base.handler.HandlerFactory;
import com.mgz.afp.base.handler.IdentityFragmentAssembler;
import com.mgz.afp.base.handler.StructuredFieldHandler;
import java.io.OutputStream;

/**
 * Concrete implementation of {@link HandlerFactory} for PDF output.
 * Currently returns a stub {@link PdfHandler}.
 */
public class PdfHandlerFactory implements HandlerFactory {

  @Override
  public StructuredFieldHandler createHandler(OutputStream os, boolean fragmentMode) throws Exception {
    return new PdfHandler();
  }

  @Override
  public FragmentAssembler createAssembler() {
    return new IdentityFragmentAssembler();
  }
}
