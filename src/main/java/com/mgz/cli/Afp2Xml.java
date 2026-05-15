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

import com.mgz.afp.base.AFPDocument;
import com.mgz.afp.base.StructuredField;
import com.mgz.afp.parser.AFPParser;
import com.mgz.afp.parser.AFPParserConfiguration;
import com.mgz.xml.Afp2XmlWriter;

import javax.xml.bind.JAXBElement;
import javax.xml.namespace.QName;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.Arrays;

public class Afp2Xml {
    public static void main(String[] args) {
        System.exit(run(args, System.out, System.err));
    }

    public static int run(String[] args, PrintStream out, PrintStream err) {
        if (args.length < 1 || Arrays.asList(args).contains("-h") || Arrays.asList(args).contains("--help")) {
            printUsage(out);
            return 0;
        }

        var isDirectoryMode = false;
        String inputPath = null;
        String outputPath = null;

        for (var i = 0; i < args.length; i++) {
            if ("-d".equals(args[i]) || "--directory".equals(args[i])) {
                isDirectoryMode = true;
            } else if (inputPath == null) {
                inputPath = args[i];
            } else if (outputPath == null) {
                outputPath = args[i];
            }
        }

        if (inputPath == null) {
            printUsage(err);
            return 1;
        }

        try {
            var input = new File(inputPath);
            if (!input.exists()) {
                err.println("Input not found: " + inputPath);
                return 1;
            }

            if (isDirectoryMode) {
                if (!input.isDirectory()) {
                    err.println("Input is not a directory: " + inputPath);
                    return 1;
                }

                var files = input.listFiles((dir, name) -> name.toLowerCase().endsWith(".afp"));
                if (files != null) {
                    for (var f : files) {
                        var outputFile = new File(f.getAbsolutePath() + ".xml");
                        convertToXml(f, outputFile, out);
                    }
                }
            } else {
                var outputFile = (outputPath != null) ? new File(outputPath) : null;
                convertToXml(input, outputFile, out);
            }
        } catch (Exception e) {
            err.println("Error during XML export: " + e.getMessage());
            e.printStackTrace(err);
            return 1;
        }

        return 0;
    }

    @SuppressWarnings("unchecked")
    private static <T extends StructuredField> JAXBElement<T> createJAXBElement(T sf) {
        return new JAXBElement<>(
                new QName(sf.getClass().getSimpleName()),
                (Class<T>) sf.getClass(),
                sf);
    }

    private static void printUsage(PrintStream out) {
        out.println("Alpheus AFP to XML Converter");
        out.println();
        out.println("Usage: java -jar alpheus-afp-parser-cli-<version>.jar [-d|--directory] <input-afp-file/dir> [output-xml-file]");
        out.println();
        out.println("Options:");
        out.println("  -d, --directory    Convert all .afp files in the specified directory to XML.");
        out.println("                     In this mode, output files are created in the same directory");
        out.println("                     with '.xml' appended to the original filenames.");
        out.println("  -h, --help         Show this help message.");
        out.println();
        out.println("Examples:");
        out.println("  Convert an AFP file and view the XML in the console:");
        out.println("    java -jar alpheus-afp-parser-cli-<version>.jar my_document.afp");
        out.println();
        out.println("  Convert an AFP file and save the result to a file:");
        out.println("    java -jar alpheus-afp-parser-cli-<version>.jar my_document.afp my_document.xml");
        out.println();
        out.println("  Batch convert all AFP files in a directory:");
        out.println("    java -jar alpheus-afp-parser-cli-<version>.jar --directory ./afp_files/");
    }

    private static void convertToXml(File inputFile, File outputFile, PrintStream out) throws Exception {
        try (var is = new BufferedInputStream(new FileInputStream(inputFile))) {
            var config = new AFPParserConfiguration();
            config.setInputStream(is);
            var parser = new AFPParser(config);

            var doc = new AFPDocument();
            StructuredField sf;
            while ((sf = parser.parseNextSF()) != null) {
                doc.addStructuredField(createJAXBElement(sf));
            }

            if (outputFile != null) {
                try (var os = new FileOutputStream(outputFile)) {
                    Afp2XmlWriter.writeXML(os, doc);
                }
                out.println("XML export successful: " + outputFile.getPath());
            } else {
                Afp2XmlWriter.writeXML(out, doc);
            }
        }
    }
}
