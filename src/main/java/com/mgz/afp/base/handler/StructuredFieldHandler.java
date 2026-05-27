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

package com.mgz.afp.base.handler;

import com.mgz.afp.base.StructuredField;

/**
 * Interface for handling AFP structured fields.
 * This abstraction decouples the parser from the output generation (XML, PDF, etc.).
 */
public interface StructuredFieldHandler extends AutoCloseable {

  /**
   * Processes a single structured field.
   *
   * @param sf the structured field to handle
   * @throws Exception if processing fails
   */
  void handle(StructuredField sf) throws Exception;

  /**
   * Closes the handler and releases any resources.
   *
   * @throws Exception if closing fails
   */
  @Override
  void close() throws Exception;
}
