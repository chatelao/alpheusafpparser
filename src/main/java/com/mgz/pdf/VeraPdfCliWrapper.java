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

import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Wrapper for the VeraPDF CLI tool.
 */
public class VeraPdfCliWrapper {

  private final String verapdfPath;

  /**
   * Constructs a VeraPdfCliWrapper using the default 'verapdf' command.
   */
  public VeraPdfCliWrapper() {
    this("verapdf");
  }

  /**
   * Constructs a VeraPdfCliWrapper with a specific path to the verapdf executable.
   *
   * @param verapdfPath the path to the verapdf executable
   */
  public VeraPdfCliWrapper(String verapdfPath) {
    this.verapdfPath = verapdfPath;
  }

  /**
   * Validates a PDF file against a given profile using VeraPDF.
   *
   * @param pdfFile the PDF file to validate
   * @param profile the profile name or path (e.g., "PDF/VT-1")
   * @return the validation result
   * @throws Exception if an error occurs during execution or parsing
   */
  public VeraPdfReportParser.ValidationResult validate(File pdfFile, String profile) throws Exception {
    List<String> command = new ArrayList<>();
    command.add(verapdfPath);
    if (profile != null && !profile.isEmpty()) {
      command.add("--profile");
      command.add(profile);
    }
    command.add("--format");
    command.add("xml");
    command.add(pdfFile.getAbsolutePath());

    ProcessBuilder pb = new ProcessBuilder(command);
    Process process = pb.start();

    try (InputStream is = process.getInputStream()) {
      VeraPdfReportParser parser = new VeraPdfReportParser();
      return parser.parse(is);
    } finally {
      process.waitFor();
    }
  }

  /**
   * Returns the command-line string for debugging purposes.
   *
   * @param pdfFile the PDF file
   * @param profile the profile
   * @return the command string
   */
  public String getCommandString(File pdfFile, String profile) {
    StringBuilder sb = new StringBuilder(verapdfPath);
    if (profile != null && !profile.isEmpty()) {
      sb.append(" --profile ").append(profile);
    }
    sb.append(" --format xml ").append(pdfFile.getAbsolutePath());
    return sb.toString();
  }
}
