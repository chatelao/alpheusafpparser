/*
Copyright 2015 Rudolf Fiala

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

package com.mgz.cli;

import com.mgz.afp.base.StructuredField;
import com.mgz.afp.parser.AFPParser;
import com.mgz.afp.parser.AFPParserConfiguration;
import com.mgz.xml.AfpStreamingXmlWriter;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.PrintStream;

public class Afp2Xml {
    public static void main(String[] args) {
        System.exit(execute(args));
    }

    public static int execute(String[] args) {
        if (args.length < 1) {
            printUsage(System.out);
            return 0;
        }

        var isDirectoryMode = false;
        String inputPath = null;
        String outputPath = null;
        String xpathExpression = null;

        for (var i = 0; i < args.length; i++) {
            var arg = args[i];
            switch (arg) {
                case "-h", "--help" -> {
                    printUsage(System.out);
                    return 0;
                }
                case "-d", "--directory" -> {
                    isDirectoryMode = true;
                    if (i + 1 < args.length && !args[i + 1].startsWith("-")) {
                        inputPath = args[++i];
                    }
                }
                case "-x", "--xpath" -> {
                    if (i + 1 < args.length) {
                        xpathExpression = args[++i];
                    } else {
                        System.err.println("Error: --xpath requires an expression.");
                        printUsage(System.err);
                        return 1;
                    }
                }
                default -> {
                    if (arg.startsWith("-")) {
                        System.err.println("Unknown option: " + arg);
                        printUsage(System.err);
                        return 1;
                    }
                    if (inputPath == null) {
                        inputPath = arg;
                    } else if (outputPath == null) {
                        outputPath = arg;
                    } else {
                        System.err.println("Too many arguments.");
                        printUsage(System.err);
                        return 1;
                    }
                }
            }
        }

        if (inputPath == null) {
            printUsage(System.err);
            return 1;
        }

        try {
            var input = new File(inputPath);
            if (!input.exists()) {
                System.err.println("Input not found: " + inputPath);
                return 1;
            }

            if (input.isDirectory()) {
                isDirectoryMode = true;
            }

            var extension = (xpathExpression != null && xpathExpression.endsWith("/text()")) ? ".txt" : ".xml";

            if (isDirectoryMode) {
                if (!input.isDirectory()) {
                    System.err.println("Input is not a directory: " + inputPath);
                    return 1;
                }

                var files = input.listFiles((dir, name) -> name.toLowerCase().endsWith(".afp"));
                var hasErrors = false;
                if (files != null) {
                    for (var f : files) {
                        var outputFile = new File(f.getAbsolutePath() + extension);
                        try {
                            convertToXml(f, outputFile, xpathExpression);
                        } catch (Exception e) {
                            System.err.println("Error processing file " + f.getName() + ": " + e.getMessage());
                            hasErrors = true;
                        }
                    }
                }
                return hasErrors ? 1 : 0;
            } else {
                var outputFile = (outputPath != null) ? new File(outputPath) : null;
                if (outputFile == null && xpathExpression != null && xpathExpression.endsWith("/text()")) {
                    outputFile = new File(inputPath + ".txt");
                }
                convertToXml(input, outputFile, xpathExpression);
                return 0;
            }
        } catch (Exception e) {
            System.err.println("Error during XML export: " + e.getMessage());
            e.printStackTrace();
            return 1;
        }
    }

    private static void printUsage(PrintStream out) {
        out.println("Usage: java -jar alpheus-afp-parser-cli.jar [-d|--directory <dir>] [-x|--xpath <expression>] <input-afp-file/dir> [output-xml-file]");
        out.println("Options:");
        out.println("  -d, --directory <dir>     Convert all .afp files in the specified directory to XML.");
        out.println("                            If a directory is provided as a positional argument, directory mode is enabled automatically.");
        out.println("  -x, --xpath <expression>  Filter the generated XML using an XPath expression.");
        out.println("  -h, --help                Show this help message.");
    }

    private static void convertToXml(File inputFile, File outputFile, String xpathExpression) throws Exception {
        try (var is = new BufferedInputStream(new FileInputStream(inputFile))) {
            var config = new AFPParserConfiguration();
            config.setInputStream(is);
            var parser = new AFPParser(config);

            if (outputFile != null) {
                try (var os = new FileOutputStream(outputFile);
                     var writer = new AfpStreamingXmlWriter(os, xpathExpression)) {
                    StructuredField sf;
                    while ((sf = parser.parseNextSF()) != null) {
                        writer.writeField(sf);
                    }
                }
                System.out.println("Export successful: " + outputFile.getPath());
            } else {
                try (var writer = new AfpStreamingXmlWriter(System.out, xpathExpression)) {
                    StructuredField sf;
                    while ((sf = parser.parseNextSF()) != null) {
                        writer.writeField(sf);
                    }
                }
            }
        }
    }
}
