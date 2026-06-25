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

import org.junit.jupiter.api.Test;
import java.io.File;
import static org.junit.jupiter.api.Assertions.*;

public class VeraPdfCliWrapperTest {

  @Test
  public void testCommandStringConstruction() {
    VeraPdfCliWrapper wrapper = new VeraPdfCliWrapper("/path/to/verapdf");
    File pdfFile = new File("test.pdf");
    String profile = "PDF/VT-1";

    String command = wrapper.getCommandString(pdfFile, profile);

    assertTrue(command.contains("/path/to/verapdf"));
    assertTrue(command.contains("--profile PDF/VT-1"));
    assertTrue(command.contains("--format xml"));
    assertTrue(command.contains(pdfFile.getAbsolutePath()));
  }

  @Test
  public void testCommandStringWithoutProfile() {
    VeraPdfCliWrapper wrapper = new VeraPdfCliWrapper();
    File pdfFile = new File("test.pdf");

    String command = wrapper.getCommandString(pdfFile, null);

    assertTrue(command.contains("verapdf"));
    assertFalse(command.contains("--profile"));
    assertTrue(command.contains("--format xml"));
    assertTrue(command.contains(pdfFile.getAbsolutePath()));
  }
}
